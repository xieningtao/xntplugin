package com.sf.test.aspectjx;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * Created by G8876 on 2017/9/5.
 */

@Aspect
public class AspectJxTest {
    private final String TAG=getClass().getSimpleName();
    @Before("execution(* android.app.Activity.on**(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.d(TAG, "onActivityMethodBefore: " + key);
    }

    @Before("execution(void com.sf.test.aspectjx.AspectJxBean.print**(..))")
    public void onPrintBeanInfoBefore(JoinPoint joinPoint) throws Throwable{
        String key = joinPoint.getSignature().toString();
        Log.d(TAG, "onActivityMethodBefore: " + key);
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        String[] params=signature.getParameterNames();

        Object args[]=joinPoint.getArgs();
        if(args==null||args.length==0){
            Log.e(TAG,"no args");
            return;
        }
        for(int i=0;i<args.length;i++){
            Log.d(TAG,params[i]+":"+args[i]);
        }
    }
}
