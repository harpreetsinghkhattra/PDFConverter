package com.example.happy.acmpdfconvertor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Happy on 11/2/2016.
 */

public class recent extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent, container, false);
        ListView listView= (ListView) view.findViewById(R.id.locale_list_view);
        File file = new File("/storage/sdcard0/");
        String string[] = file.list();
        final List<String> files = new ArrayList<>();
        for(int i =0; i<string.length; i++){
            if(string[i].substring(string[i].length()-3, string[i].length()).equalsIgnoreCase("pdf")){
                files.add(string[i]);
            }

        }
        int[] images = {R.drawable.pdfconverter};
        listView.setAdapter(new localelistbaseadapter(getContext(), files, images));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent = new Intent(getContext(), pdfViewrjar.class);
                String path = "/storage/sdcard0/"+files.get(i);
                intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
                startActivity(intent);

            }
        });

        for(String x: files){
            System.out.println("the vlue of file name is--------------------------------------------------"+x);
        }

        return view;
    }
}
