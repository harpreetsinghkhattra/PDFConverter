package com.example.happy.acmpdfconvertor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.content.Context;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Happy on 9/11/2016.
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText filedirectory;
    private Button file_dir_btn ;
    private EditText file_name;
    private RelativeLayout relativeLayout;

    main_list_adapter list_adapter;
    List<String> list;
    ActionBarDrawerToggle toggle;
    DrawerLayout layout;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        toolbar = (Toolbar) findViewById(R.id.main_pdftoolbar);

        relativeLayout = (RelativeLayout) findViewById(R.id.pdf_file_loading);
        relativeLayout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        toolbar.setTitle("PDFConvertor");

        Intent intent = new Intent(Intent.ACTION_VIEW);

        findviewid();

        // drawable

        //layout  = (LinearLayout)findViewById(R.id.ui_linear);
        list = genrate();
        layout = (DrawerLayout) findViewById(R.id.ui_draw);
        listView = (ListView) findViewById(R.id.ui_list);
        int[] images = {R.drawable.list_house, R.drawable.list_view, R.drawable.createpdf, R.drawable.list_help, R.drawable.list_comment};
        listView.setAdapter(new main_list_adapter(getApplicationContext(), list, images));
        toggle = new ActionBarDrawerToggle(MainActivity.this, layout, toolbar, R.string.user_name, R.string.user_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1;
                switch (i){
                    case 0:
                        intent1 = new Intent(getApplicationContext(), home.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        intent1 = new Intent(getApplicationContext(), pdfViewrjar.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        break;
                    case 4:
                        intent1 = new Intent(getApplicationContext(), help.class);
                        startActivity(intent1);
                        break;
                    case 5:
                        intent1 = new Intent(getApplicationContext(), home.class);
                        startActivity(intent1);
                        break;

                }
            }
        });
        layout.setDrawerListener(toggle);

        if(savedInstanceState == null){
            setFragmentation(0, sub_main.class);
        }
    }

    public List<String> genrate(){
        List<String> list  = new ArrayList<>();
        String string[] = {"Home", "View", "Create Pdf", "Help", "Comment"};
        for(int i =0;i<string.length; i++){
            list.add(string[i]);
        }
        return  list;
    }
    @Override
    public void onBackPressed() {
        if(layout.isDrawerOpen(listView)){
            layout.closeDrawer(listView);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    public void setFragmentation(int position, Class<? extends Fragment> fragmClass){

        try {
            Fragment fragment = fragmClass.newInstance();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.ui_frame, fragment, fragmClass.getSimpleName());
            transaction.commit();

            listView.setItemChecked(position, true);
            layout.closeDrawer(listView);
            listView.invalidateViews();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public class loading extends AsyncTask{
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
    protected void onResume() {
        super.onResume();
        relativeLayout.setVisibility(View.GONE);

    }

    public void findviewid(){
        filedirectory = (EditText) findViewById(R.id.pdf_file_dir);
        file_dir_btn = (Button) findViewById(R.id.pdf_file_btn);
        file_name = (EditText) findViewById(R.id.pdf_file_name);
        relativeLayout = (RelativeLayout) findViewById(R.id.pdf_file_loading);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                final Uri currFileURI = data.getData();
                System.out.println("the value of data is"+data.getData().toString()+" and "+data.getPackage());
                final String path=currFileURI.getPath();
                findviewid();
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
                            }else if("docx".equalsIgnoreCase(path.substring(path.length()-4,path.length()))){
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
                                ProgressDialog dialog = new ProgressDialog(getApplicationContext());
                                dialog.setMessage("please inset valid files");
                                dialog.show();
                            }
                            document.close();

                            output.flush();
                            output.close();

                            new loading().execute();
                            Intent intent = new Intent(getApplicationContext(), acm_pdf_converted.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_attach:
                intent = new Intent();
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Set your required file type
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "DEMO"),1);
                break;
            case R.id.menu_random:
                intent = new Intent(getApplicationContext(), help.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
