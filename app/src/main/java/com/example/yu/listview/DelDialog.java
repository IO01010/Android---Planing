package com.example.yu.listview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

public class DelDialog extends Dialog
        implements View.OnClickListener {

    private Button yesButton,noButton;
    private OnSelfDialogListener mListener;

    public DelDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deldialog);

        yesButton = (Button) findViewById(R.id.buttonyes);
        yesButton.setOnClickListener(this);

        noButton = (Button) findViewById(R.id.buttonno);
        noButton.setOnClickListener(this);
    }
    public interface OnSelfDialogListener{
        //自定义接口
        void setYesOnClick();
        void setNoOnClick();
    }
    public void setOnSelfDialogListener(OnSelfDialogListener listener){
        //设置监听器
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonyes:
                mListener.setYesOnClick();
                break;
            case R.id.buttonno:
                mListener.setNoOnClick();
                break;
        }

    }
}
