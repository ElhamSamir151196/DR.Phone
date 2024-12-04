package com.example.metr.doctor_phone;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class client_post_item_temp extends ArrayAdapter<String>{
    private String[] Name_Date_text;
    private String[] description_text;
    private String[] title_text;
    private Integer[] Image_id;
   // private Button[] btn_comment;
    private ListView Comments_Array;
    public  Activity context;


    public client_post_item_temp(Activity context, String[] Name_Date_text,String[] description_text,String[] title_text,Integer[] Image_id) {
        super(context, R.layout.client_post_item,Name_Date_text);

        this.context=context;
        this.Name_Date_text=Name_Date_text;
        this.description_text=description_text;
        this.title_text=title_text;
        this.Image_id=Image_id;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.client_post_item,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) r.getTag();
        }
        viewHolder.ivw.setImageResource(Image_id[position]);
        viewHolder.txv1.setText(Name_Date_text[position]);
        viewHolder.txv2.setText(description_text[position]);
        viewHolder.txv3.setText(title_text[position]);


        return r;
    }

    class ViewHolder
    {
        TextView txv1;
        TextView txv2;
        TextView txv3;
        ImageView ivw;
        ViewHolder(View v){
            txv1=(TextView) v.findViewById(R.id.text_date_name);
            txv2=(TextView) v.findViewById(R.id.text_describtion);//text_title
            txv3=(TextView) v.findViewById(R.id.text_title);

            ivw=(ImageView) v.findViewById(R.id.image_id);

        }
    }
}
