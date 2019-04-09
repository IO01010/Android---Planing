package com.example.yu.listview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SelfDialog extends Dialog
        implements View.OnClickListener {
    private Button yesButton, noButton;
    private OnSelfDialogListener mListener;

    public EditText editmessage;


    public SelfDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    private static final int MESSAGE_GET = 0x00;
    private static final int MESSAGE_SEND = 0x03;

    private Handler mHandler = new Handler(){

        //定义消息处理的方法
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MESSAGE_GET:
                     editmessage.setText((String) msg.obj);
                    //从ListView中得到string
                    break;
                //case MESSAGE_SEND:
                  //  editmessage.getText();
                    //从dialog中发送到list
                    //break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog1); //??????????

        yesButton = (Button) findViewById(R.id.yes);
        yesButton.setOnClickListener(this);

        noButton = (Button) findViewById(R.id.no);
        noButton.setOnClickListener(this);

        editmessage = (EditText) findViewById(R.id.editdialog);

    }

    public void setOnselfDialogListener(OnSelfDialogListener listener) {
        //设置监听器
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                mListener.setYesOnClick();
                break;
            case R.id.no:
                mListener.setNoOnClick();
                break;
        }

    }

    public interface OnSelfDialogListener {
        //自定义接口，确定、取消按钮
        void setYesOnClick();
        void setNoOnClick();
    }
    public void setEditmessage(String str){
        Message msg = mHandler.obtainMessage();
        msg.what = MESSAGE_GET;
        msg.obj = str;
        mHandler.sendMessage(msg);
    }
    public String sendEditmessage(){
        return editmessage.getText().toString();
    }
}

