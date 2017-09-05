package com.sf.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sf.test.aspectjx.AspectJxBean;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test_aop_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Student().printInfo("hello",100,"xie ning tao");
            }
        });
        AspectJxBean bean=new AspectJxBean();
        bean.setName("xie ning tao");
        bean.printBeanInfo("hello");
    }
}
