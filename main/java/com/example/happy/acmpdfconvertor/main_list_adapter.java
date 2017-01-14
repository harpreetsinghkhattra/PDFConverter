package com.example.happy.acmpdfconvertor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Happy on 11/2/2016.
 */

public class main_list_adapter extends BaseAdapter {
    Context context;
    List<String> files = new ArrayList<>();
    int[] images;
    LayoutInflater inflater = null;
    String filename = null;

    public main_list_adapter(){

    }
    public main_list_adapter(Context context, List<String> files,int[] images){
        this.context = context;
        this.files = files;
        this.images = images;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_view, null);
        holder.tv=(TextView) rowView.findViewById(R.id.list_view_text_view);
        holder.img=(ImageView) rowView.findViewById(R.id.list_view_image_view);
        holder.tv.setText(files.get(i));
            holder.img.setImageResource(images[i]);
        return rowView;
    }
}
