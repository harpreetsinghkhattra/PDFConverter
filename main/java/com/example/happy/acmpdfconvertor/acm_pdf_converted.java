package com.example.happy.acmpdfconvertor;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Happy on 9/12/2016.
 */
public class acm_pdf_converted extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acm_pdf_converted);


        toolbar = (Toolbar) findViewById(R.id.acm_pdf_converted_toolbar);
        imageView = (ImageView) findViewById(R.id.acm_pdf_converted_imageview);
        setSupportActionBar(toolbar);
        final String path[] = getIntent().getStringArrayExtra("name");
        toolbar.setTitle(path[1]);

        textView = (TextView) findViewById(R.id.acm_pdf_textview);

        File file = new File(path[1]);
        try {
            Document document =new Document();
            InputStream input = new FileInputStream(file);
            PdfReader reader = new PdfReader(path[1]);
           // String image = PdfTextExtractor.getTextFromPage(reader,1);
            if(path[1].substring(path[1].length()-3,path[1].length()).equalsIgnoreCase("pdf")) {
                int noOfPages = reader.getNumberOfPages();
                System.out.println("the no of pages is"+noOfPages);
               // for(int i = 0; i<noOfPages;i++) {
                    textView.setText(PdfTextExtractor.getTextFromPage(reader,1)+".......");
               // }
            }else if(path[0].substring(path[0].length()-3,path[0].length()).equalsIgnoreCase("jpg") || path[0].substring(path[0].length()-3,path[0].length()).equalsIgnoreCase("png") || path[1].substring(path[1].length()-4,path[1].length()).equalsIgnoreCase("jpeg")) {
                imageView.setImageURI(Uri.parse(path[0]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
