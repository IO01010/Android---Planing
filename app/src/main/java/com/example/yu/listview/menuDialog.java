package com.example.yu.listview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

public class menuDialog extends Dialog
        implements View.OnClickListener {

    private Button add_button1,add_button2,add_button3,add_button4;
    private onSelfMenuDialog menuListener;

    public menuDialog(@NonNull Context context,int themeResId) {
        super(context,themeResId);
    }
    public menuDialog(Context context){
        super(context);
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        add_button1 = (Button) findViewById(R.id.button1);
        add_button1.setOnClickListener(this);
        add_button2 = (Button) findViewById(R.id.button2);
        add_button2.setOnClickListener(this);
        add_button3 = (Button) findViewById(R.id.button3);
        add_button3.setOnClickListener(this);
        add_button4 = (Button) findViewById(R.id.button4);
        add_button4.setOnClickListener(this);
    }

    public interface onSelfMenuDialog{
        //自定义接口
        void button1Click();
        void button2Click();
        void button3Click();
        void button4Click();
    }
    public void setOnselfMenuDialogListener(onSelfMenuDialog listener){
        //设置监听器
        menuListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                menuListener.button1Click();
                break;
            case R.id.button2:
                menuListener.button2Click();
                break;
            case R.id.button3:
                menuListener.button3Click();
                break;
            case R.id.button4:
                menuListener.button4Click();
                break;
        }
    }

}
