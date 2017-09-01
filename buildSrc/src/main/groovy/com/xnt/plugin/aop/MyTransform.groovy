package com.xnt.plugin.aop

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by G8876 on 2017/8/31.
 */

public class MyTransform extends Transform {
    private Project mPoject;

    public MyTransform(Project project){
        mPoject=project;
    }

    @Override
    public String getName() {
        return MyTransform.class.getSimpleName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        println("==============start transform============")
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        InjectTime injectTime=new InjectTime();
        inputs.each { TransformInput input ->

            input.directoryInputs.each { DirectoryInput directoryInput ->

                injectTime.injectTimeCode(directoryInput.file.path,"com.sf.test",mPoject)
                def dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                println("name: "+directoryInput.name+" contentTypes: "+directoryInput.contentTypes+" scopes: "+directoryInput.scopes)
                println("file path: "+directoryInput.file.path)
                println("dest file path: "+dest.path)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { JarInput jarInput ->


                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                println("jar path: "+jarInput.file.path)
                println("jar name: "+jarName)
                def dest = transformInvocation.getOutputProvider().getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, dest)

            }
        }
        println("==============end transform============")
    }
}
