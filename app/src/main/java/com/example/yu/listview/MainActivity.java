package com.example.yu.listview;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.icu.text.SymbolTable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener,View.OnClickListener {

    ArrayList<String> lis1,lis2,lis3,lis4;
    ArrayAdapter<String> adapter1,adapter2,adapter3,adapter4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lis1 = new ArrayList<>();
        String lis1json = getJson(MainActivity.this);
        lis1 = analysis(lis1json,"listjson1");
        adapter1 = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,lis1);

        lis2 = new ArrayList<>();
        String lis2json = getJson(MainActivity.this);
        lis2 = analysis(lis2json,"listjson2");
        adapter2 = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,lis2);

        lis3 = new ArrayList<>();
        String lis3json = getJson(MainActivity.this);
        lis3 = analysis(lis3json,"listjson3");
        adapter3 = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,lis3);

        lis4 = new ArrayList<String>();
        String lis4json = getJson(MainActivity.this);
        lis4 = analysis(lis4json,"listjson4");
        adapter4 = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,lis4);


        ListView listview1 = (ListView) findViewById(R.id.listview1);
        listview1.setAdapter(adapter1);
        listview1.setOnItemClickListener(this);
        listview1.setOnItemLongClickListener(this);
        ListView listview2 = (ListView) findViewById(R.id.listview2);
        listview2.setAdapter(adapter2);
        listview2.setOnItemClickListener(this);
        listview2.setOnItemLongClickListener(this);
        ListView listview3 = (ListView) findViewById(R.id.listview3);
        listview3.setAdapter(adapter3);
        listview3.setOnItemClickListener(this);
        listview3.setOnItemLongClickListener(this);
        ListView listview4 = (ListView) findViewById(R.id.listview4);
        listview4.setAdapter(adapter4);
        listview4.setOnItemClickListener(this);
        listview4.setOnItemLongClickListener(this);

        TextView text_add = (TextView) findViewById(R.id.addtitle);
        text_add.setOnClickListener(this);

        TextView DataView = (TextView) findViewById(R.id.texttitle);
        getData(DataView);



    }
    protected void onPause(){
        super.onPause();
        Log.d("Fof","onPause被调用");
        saveJson(MainActivity.this);
        Log.d("Fof","saveJson 被调用");
        //saveJson("assets/thing.json",MainActivity.this,jsonobject);

    }
    //获得年月日，并绑定到TextView上面
    private  void getData(TextView view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH) +1;
        int day = calendar.get(calendar.DAY_OF_MONTH);
        String data_str = String.valueOf(year) + " - " + String.valueOf(month) + " - "
                + String.valueOf(day);
        view.setText(data_str);
    }

    //读取json文件,
    public String getJson(Context mContext){
        BufferedReader reader = null;
        StringBuilder strBuilder = new StringBuilder();
        try {
            //防止初次没有thing.json文件
            OutputStream out = mContext.openFileOutput("thing.json",Context.MODE_APPEND);
            Writer writer = new OutputStreamWriter(out);
            writer.write("");

            InputStream in = mContext.openFileInput("thing.json");
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null){
                strBuilder.append(line);
            }
            if(reader != null){
                reader.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("fff","读出的字符串是"+strBuilder.toString());
        return strBuilder.toString();
    }

    //解析json文件,根据key，将值转化为ArrayList<String>
    public ArrayList<String> analysis(String Strjson,String listname){
        ArrayList<String> strArrayList = new ArrayList<>();
        try {

            JSONObject objec = new JSONObject(Strjson);
            Log.d("fio","转化后的" + objec.toString());
            JSONArray jsonArray = objec.getJSONArray(listname);

            for(int i = 0;i<jsonArray.length();i++){
                String str = jsonArray.getString(i);
                strArrayList.add(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("fio","解析生成的List" + strArrayList.toString());
        return strArrayList;
    }

    //创建json object
    public String produceJson(String[] listview,ArrayList<ArrayList<String>> list){
        JSONObject jsonObject = new JSONObject();
        for(int i = 0; i < listview.length; i++){
            try {
                JSONArray jsonA = new JSONArray(list.get(i));
                jsonObject.put(listview[i],jsonA);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("fid","之后生成的json" + jsonObject.toString());
        return jsonObject.toString();
    }

    //将生成的JSON object对象写入json文件
    public void saveJson(Context mContext){
        String[] listview ={"listjson1","listjson2","listjson3","listjson4"};
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        list.add(lis1);
        list.add(lis2);
        list.add(lis3);
        list.add(lis4);
        String jsonobject = produceJson(listview,list);
        Log.d("Fob","得到的JSON字符串" + jsonobject);

        Writer writer = null;

        try {
            OutputStream out = mContext.openFileOutput("thing.json",Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonobject);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //ListView的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        switch (parent.getId()){
            case R.id.listview1:
                Dialog_Click(lis1,position,R.style.MyDialog1,adapter1);
                break;
            case R.id.listview2:
                Dialog_Click(lis2,position,R.style.MyDialog2,adapter2);
                break;
            case R.id.listview3:
                Dialog_Click(lis3,position,R.style.MyDialog3,adapter3);
                break;
            case R.id.listview4:
                Dialog_Click(lis4,position,R.style.MyDialog4,adapter4);
                break;
        }

    }

    //ListView的长按事件
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.listview1:
                DialogLongClick(lis1,position,R.style.DelDialog,adapter1);
                break;
            case R.id.listview2:
                DialogLongClick(lis2,position,R.style.DelDialog,adapter2);
                break;
            case R.id.listview3:
                DialogLongClick(lis3,position,R.style.DelDialog,adapter3);
                break;
            case R.id.listview4:
                DialogLongClick(lis4,position,R.style.DelDialog,adapter4);
                break;
        }
        return true;
    }
    protected void DialogLongClick(final ArrayList<String> lis,final int position,int MyDialog,final ArrayAdapter<String> adapter){
        final DelDialog delDialog = new DelDialog(this,MyDialog);
        delDialog.show();
        delDialog.setOnSelfDialogListener(new DelDialog.OnSelfDialogListener() {
            @Override
            public void setYesOnClick() {
                lis.remove(position);
                adapter.notifyDataSetChanged();
                delDialog.dismiss();
            }
            @Override
            public void setNoOnClick() {
                delDialog.dismiss();
            }
        });
    }

    //ListView内部Item单击事件
    protected void Dialog_Click(final ArrayList<String> lis,final int position,int MyDialog,final ArrayAdapter<String> adapter){
        final SelfDialog Dialog = new SelfDialog(MainActivity.this,MyDialog);
        final String str = lis.get(position).toString();
        Dialog.setEditmessage(str);
        Dialog.show();
        Dialog.setOnselfDialogListener(new SelfDialog.OnSelfDialogListener() {
            @Override
            public void setYesOnClick() {
                String str_changed = Dialog.sendEditmessage();
                lis.set(position,str_changed);
                adapter.notifyDataSetChanged();
                Dialog.dismiss();
            }
            @Override
            public void setNoOnClick() {
                Dialog.dismiss();
            }
        });
    }

    //新建事项
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addtitle:
                final menuDialog menu = new menuDialog(this,R.style.Menu);
                menu.show();
                menu.setOnselfMenuDialogListener(new menuDialog.onSelfMenuDialog() {
                    @Override
                    public void button1Click() {
                        menu.dismiss();
                        Menu_Click(lis1,R.style.MyDialog1,adapter1);
                    }
                    @Override
                    public void button2Click() {
                        menu.dismiss();
                        Menu_Click(lis2,R.style.MyDialog2,adapter2);
                    }
                    @Override
                    public void button3Click() {
                        menu.dismiss();
                        Menu_Click(lis3,R.style.MyDialog3,adapter3);
                    }
                    @Override
                    public void button4Click() {
                        menu.dismiss();
                        Menu_Click(lis4,R.style.MyDialog4,adapter4);
                    }
                });
                break;
        }
    }

    protected void Menu_Click(final ArrayList<String> lis,int MyDialog,final ArrayAdapter<String> adapter){
        final SelfDialog Dialog = new SelfDialog(MainActivity.this,MyDialog);
        Dialog.show();
        Dialog.setOnselfDialogListener(new SelfDialog.OnSelfDialogListener() {
            @Override
            public void setYesOnClick() {
                String str_added = Dialog.sendEditmessage();
                lis.add(str_added);
                adapter.notifyDataSetChanged();
                Dialog.dismiss();
            }
            @Override
            public void setNoOnClick() {
                Dialog.dismiss();
            }
        });
    }
}
