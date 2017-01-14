package com.example.happy.acmpdfconvertor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Happy on 11/2/2016.
 */

public class sub_main extends Fragment {

    private EditText filedirectory;
    private Button file_dir_btn;
    private EditText file_name;
    private RelativeLayout relativeLayout;
    View vieww;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_main, container, false);
        findviewid(view);
        vieww = view;
        return view;
    }
    public class loading extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            // relativeLayout.setVisibility(View.VISIBLE);
            return null;
        }

        @Override
        protected void onPreExecute() {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        relativeLayout.setVisibility(View.GONE);
    }

    public void findviewid(View view){
        filedirectory = (EditText) view.findViewById(R.id.pdf_file_dir);
        file_dir_btn = (Button) view.findViewById(R.id.pdf_file_btn);
        file_name = (EditText) view.findViewById(R.id.pdf_file_name);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.pdf_file_loading);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                final Uri currFileURI = data.getData();
                System.out.println("the value of data is"+data.getData().toString()+" and "+data.getPackage());
                final String path=currFileURI.getPath();
                findviewid(vieww);
                filedirectory.setText(path);
                System.out.println("the value of pdf is = "+path);
                System.out.println("the value of pdf is = "+path.substring(path.length()-3,path.length()));

                file_dir_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Document document = new Document();
                            String pdfFileName = null;
                            if(".pdf".equalsIgnoreCase(file_name.getText().toString().substring(file_name.getText().toString().length()-4,file_name.getText().toString().length()))){
                                pdfFileName = file_name.getText().toString().substring(0,file_name.getText().toString().length()-4);
                                System.out.println("the padFilename is ="+pdfFileName);
                            }else if(!".pdf".equalsIgnoreCase(file_name.getText().toString().substring(file_name.getText().toString().length()-4,file_name.getText().toString().length()))){
                                pdfFileName = file_name.getText().toString();
                                System.out.println("the padFilename is ="+pdfFileName);
                            }
                            final String[] imageUri = {path,"/storage/sdcard0/"+file_name.getText().toString()+".pdf"};
                            File file = new File("/storage/sdcard0/"+pdfFileName+".pdf");
                            file.createNewFile();
                            OutputStream output = new FileOutputStream(new File(new String(file.toString())));

                            PdfWriter pwriter = PdfWriter.getInstance(document, output);
                            document.open();

                            //imageUri[0] = path;
                            //imageUri[1] = ;
                            System.out.println("the vlalue of path[0]"+imageUri[0]);
                            System.out.println("the vlalue of path[1]"+imageUri[1]);
                            if("jpg".equalsIgnoreCase(path.substring(path.length()-3,path.length()))) {
                                Image image = Image.getInstance(path);
                                // float scalar = ((document.getPageSize().getWidth()-document.getPageSize().getLeft()-document.getPageSize().getRight())/image.getWidth())*100;
                                float width = document.getPageSize().getWidth();
                                float height = document.getPageSize().getHeight();
                                image.scaleToFit(width, height);

                                document.add(image);
                            }else if("txt".equalsIgnoreCase(path.substring(path.length()-3,path.length()))){
                                File file1 = null;
                                DataInputStream data = new DataInputStream(new FileInputStream(file1 = new File(path)));
                                BufferedInputStream buffered = new BufferedInputStream(data);
                                byte[] buffer = new byte[(int)file1.length()];
                                buffered.read(buffer,0,buffer.length);
                                for(int x: buffer){
                                    System.out.print((char)x);
                                }
                                document.add(new Paragraph(new String(buffer)));
                            }else if("doc".equalsIgnoreCase(path.substring(path.length()-3,path.length()))){
                                try{
                                    File file1 = null;
                                    //    FileInputStream fs=new FileInputStream(src);
                                    //create document object to wrap the file inputstream object
                                    XWPFDocument doc=new XWPFDocument(new FileInputStream(file1 = new File(path)));
                                    //72 units=1 inch
                                    //Document pdfdoc=new Document(PageSize.A4,72,72,72,72);
                                    //create a pdf writer object to write text to mypdf.pdf file
                                    //PdfWriter pwriter=PdfWriter.getInstance(pdfdoc, new FileOutputStream(desc));
                                    //specify the vertical space between the lines of text
                                    pwriter.setInitialLeading(20);
                                    //get all paragraphs from word docx
                                    List<XWPFParagraph> plist=doc.getParagraphs();

                                    //open pdf document for writing
                                    //pdfdoc.open();
                                    for (int i = 0; i < plist.size(); i++) {
                                        //read through the list of paragraphs
                                        XWPFParagraph pa = plist.get(i);
                                        //get all run objects from each paragraph
                                        List<XWPFRun> runs = pa.getRuns();
                                        //read through the run objects
                                        for (int j = 0; j < runs.size(); j++) {
                                            XWPFRun run=runs.get(j);
                                            //get pictures from the run and add them to the pdf document
                                            List<XWPFPicture> piclist=run.getEmbeddedPictures();
                                            //traverse through the list and write each image to a file
                                            Iterator<XWPFPicture> iterator=piclist.iterator();
                                            while(iterator.hasNext()){
                                                XWPFPicture pic=iterator.next();
                                                XWPFPictureData picdata=pic.getPictureData();
                                                byte[] bytepic=picdata.getData();
                                                Image imag=Image.getInstance(bytepic);
                                                document.add(imag);

                                            }
                                            //get color code
                                            int color=getCode(run.getColor());
                                            //construct font object
                                            Font f=null;
                                            if(run.isBold() && run.isItalic())
                                                f= FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLDITALIC, new BaseColor(color));
                                            else if(run.isBold())
                                                f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLD, new BaseColor(color));
                                            else if(run.isItalic())
                                                f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.ITALIC, new BaseColor(color));
                                            else if(run.isStrike())
                                                f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.STRIKETHRU, new BaseColor(color));
                                            else
                                                f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.NORMAL, new BaseColor(color));
                                            //construct unicode string
                                            String text=run.getText(-1);
                                            byte[] bs;
                                            if (text!=null){
                                                bs=text.getBytes();
                                                String str=new String(bs,"UTF-8");
                                                //add string to the pdf document
                                                Chunk chObj1=new Chunk(str,f);
                                                document.add(chObj1);
                                            }

                                        }
                                        //output new line
                                        document.add(new Chunk(Chunk.NEWLINE));
                                    }
                                    //close pdf document
                                    document.close();
                                }catch(Exception e){e.printStackTrace();}

                            }else{
                                ProgressDialog dialog = new ProgressDialog(getContext());
                                dialog.setMessage("please inset valid files");
                                dialog.show();
                            }
                            document.close();

                            output.flush();
                            output.close();

                           // new MainActivity.loading().execute();
                            Intent intent = new Intent(getContext(), acm_pdf_converted.class);
                            intent.putExtra("name", imageUri);
                            startActivity(intent);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e1){
                            e1.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void convertWordToPdf(String src, Document pdfdoc, PdfWriter pwriter){
        // try{
        //create file inputstream object to read data from file

    }
    public static int getCode(String code){
        int colorCode;
        if(code!=null)
            colorCode=Long.decode("0x"+code).intValue();
        else
            colorCode=Long.decode("0x000000").intValue();
        return colorCode;
    }
}
