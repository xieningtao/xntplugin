package com.xnt.plugin.aop

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.CtNewMethod
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import org.gradle.api.Project
import javassist.Modifier

import java.lang.annotation.Annotation
/**
 * Created by G8876 on 2017/9/1.
 */
public class InjectTime {
    private ClassPool sPool = ClassPool.getDefault();
    private final String TIME_ANNOTATION = "com.sf.test.aop.AopTime";

    public void injectTimeCode(String path, String packageName, Project project) {
        sPool.appendClassPath(path)
        File dir = new File(path);
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.path;

                if (filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('$')
                        && !filePath.contains('R.class') && !filePath.contains('BuildConfig.class')) {
                    project.logger.error("filePath: " + filePath);
                    if (isMyPackage(filePath, packageName)) {
                        String newFilePath = filePath.replace("\\", ".")
                        int start = newFilePath.indexOf(packageName);
                        int end = filePath.indexOf(".class");
                        String className = filePath.substring(start, end).replace("\\", ".").replace("/", ".");
                        project.logger.error("className: " + className);
                        CtClass ctClass = sPool.getCtClass(className);
                        if (ctClass.isFrozen()) {
                            ctClass.defrost()
                        }
                        boolean modifyMethod = false;
                        for (CtMethod ctMethod : ctClass.getMethods()) {
                            if(ctMethod==null){
                                break;
                            }
                            String methodName = ctMethod.getName();
                            methodName = methodName.substring(methodName.lastIndexOf(".") + 1, methodName.length())
                            project.logger.error("methodName: " + methodName)


                            for (Annotation annotation : ctMethod.getAnnotations()) {
                                if(annotation==null){
                                    break;
                                }
                                project.logger.error("start --->annotation");
                                String canonicalName = annotation.annotationType().getCanonicalName();
                                project.logger.error("canonicalName: " + canonicalName);
                                if (canonicalName.equals(TIME_ANNOTATION)) {
                                    project.logger.error("=============start to modify method: " + methodName);

                                    //添加打印方法参数的
                                    CtClass[] params = new CtClass[ctMethod.getParameterTypes().length];
                                    for (int i = 0; i < ctMethod.getParameterTypes().length; i++) {
                                        params[i] = sPool.getCtClass(ctMethod.getParameterTypes()[i].getName());
                                    }

                                    MethodInfo methodInfo = ctMethod.getMethodInfo();
                                    CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                                    LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                                            .getAttribute(LocalVariableAttribute.tag);
                                    int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                                    String[] paramNames = new String[ctMethod.getParameterTypes().length];
                                    StringBuilder paramsStr=new StringBuilder();
                                    if (paramNames.length != 0) {
                                        paramsStr.append("\nSystem.out.println(");

                                    for (int i = 0; i < paramNames.length; i++) {
                                        paramNames[i] = attr.variableName(i + pos);
                                        if(i==0) {
                                            paramsStr.append("\"").append(paramNames[i]).append(":\"").append("+")
                                                    .append(paramNames[i])
                                        }else {
                                            paramsStr.append("+").append("\"\t").append(paramNames[i])
                                                    .append(":\"").append("+").append(paramNames[i])
                                        }
                                    }
                                    paramsStr.append(");\n");
                                    }

                                    //=======================

                                    project.logger.error("params: "+paramsStr.toString());
                                    String newMethodName = methodName + '$' + "Impl";
                                    ctMethod.setName(newMethodName);//旧的方法换一个名字，但是还是原来的实现
                                    ctMethod.insertBefore(paramsStr.toString());
                                    project.logger.error("newMethodName: " + newMethodName);
                                    CtMethod newMethod = CtNewMethod.copy(ctMethod, methodName, ctClass, null);
                                    project.logger.error("copy method");
                                    //新的方法用旧的名字，但是确是新的实现方式
                                    StringBuilder bodyStr = new StringBuilder();
                                    String prefixStatement = "\nlong startTime=System.currentTimeMillis();";
                                    String postfixStatement = "\nlong endTime=System.currentTimeMillis();";


                                    String outputStr = "\nSystem.out.println(\"this method" + className + "." + methodName + " cost:\"+(endTime-startTime)+\".ms\");";
                                    bodyStr.append("{");
//                                    bodyStr.append(paramsStr);
                                    bodyStr.append(prefixStatement);
                                    bodyStr.append(newMethodName + "(" + '$$' + ");\n");
                                    bodyStr.append(postfixStatement);
                                    bodyStr.append(outputStr);
                                    bodyStr.append("}");
                                    newMethod.setBody(bodyStr.toString());
                                    ctClass.addMethod(newMethod);
                                    project.logger.error("=============end to modify method: " + methodName);
                                    modifyMethod = true;
                                }
                            }
                        }
                        ctClass.writeFile(path);
                        ctClass.detach();
                    }


                }

            }
        }
    }

    private boolean isMyPackage(String path, String packageName) {
        if (isStrEmpty(packageName) || isStrEmpty(path)) {
            return false
        }
        String newPath = path.replace("\\", ".")
        println("originPackage: " + packageName + " path: " + path + " newPath:" + newPath);

        int b = "D:.gitRepository.tools.xntplugin.aoptest.build.intermediates.classes.debug.com.sf.test.MainActivity.class".indexOf("com.sf.test");
        println("b: " + b);
        int index = newPath.indexOf(packageName);
        println("index: " + index);
        return index != -1;
    }

    private static isStrEmpty(String content) {
        return content == null || content.equals("");
    }
}