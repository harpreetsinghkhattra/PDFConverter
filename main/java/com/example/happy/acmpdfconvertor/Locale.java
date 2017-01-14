package com.example.happy.acmpdfconvertor;

import android.content.Context;
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
 * Created by Happy on 10/31/2016.
 */

public class Locale extends Fragment {

    /**
     * Created by Happy on 11/1/2016.
     *
     *
     * GoogleApiClient googleApiClient ;
     boolean fileOperations = false;
     final int REQUEST_CODE_RESOLUTION = 1;
     final int REQUEST_CODE_OPNER= 2;
     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.uploadfileongoogledrive, container, false);
     return view;
     }

     @Override
     public void onResume() {
     super.onResume();
     if(googleApiClient == null){
     googleApiClient = new GoogleApiClient.Builder(getContext())
     .addApi(Drive.API)
     .addScope(Drive.SCOPE_FILE)
     .addConnectionCallbacks(this)
     .build();
     }
     googleApiClient.connect();
     }

     @Override
     public void onStop() {
     super.onStop();
     if(googleApiClient==null){
     googleApiClient.disconnect();
     }
     super.onPause();
     }

     @Override
     public void onConnected(@Nullable Bundle bundle) {

     Toast.makeText(getContext(), "Connected", Toast.LENGTH_SHORT).show();
     }

     @Override
     public void onConnectionSuspended(int i) {

     }

     //public void onClickCreateView(){
     //    fileOperations = true;
     //    Drive.DriveApi.newDriveContents(googleApiClient)
     //            .setResultCallback();
     //}

     public void openFileFromGoogleDrive(){
     IntentSender intent = Drive.DriveApi.newOpenFileActivityBuilder().setMimeType(new String[]{"text/plain", "text/pdf"}).build(googleApiClient);

     try {
     startIntentSenderForResult(intent, REQUEST_CODE_OPNER, null, 0, 0, 0, null);
     }catch (IntentSender.SendIntentException e){
     e.printStackTrace();
     }
     }

     final ResultCallback<DriveApi.DriveContentsResult> driveContentsResultResultCallback = new ResultCallback<DriveApi.DriveContentsResult>() {
     @Override
     public void onResult(@NonNull DriveApi.DriveContentsResult driveContentsResult) {
     if(driveContentsResult.getStatus().isSuccess()){
     if(fileOperations == true){

     }else{
     openFileFromGoogleDrive();
     }
     }
     }
     };


     */

    static Context cotext;

    public static void getContextt(Context cont){
        cotext = cont;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locale, container, false);
        ListView listView= (ListView) view.findViewById(R.id.locale_list_view);
        StringBuilder builder = new StringBuilder("/storage/sdcard0/");
        File file = new File(builder.toString());
        String string[] = file.list();
        final List<String> files = new ArrayList<>();
        for(int i =0; i<string.length; i++){
            File filename = new File(string[i]);
            if(string[i].substring(string[i].length()-3, string[i].length()).equalsIgnoreCase("pdf")){
                files.add(string[i]);
            }
            if(filename.isDirectory()){
                builder.append(string[i].toString());
                File filee = new File(builder.toString());
                String stringg[] = filee.list();
                for(int j =0; j<stringg.length; j++) {
                    File filenamee = new File(string[j]);
                    if (string[i].substring(string[j].length() - 3, string[j].length()).equalsIgnoreCase("pdf")) {
                        files.add(string[i]);
                    }if(filenamee.isDirectory()){
                        generatelist(builder);
                        System.out.println("the vlue of dir name is--------------------------------------------------"+builder);
                    }

                }
                builder.delete(0, builder.length()-1);
                builder = new StringBuilder("/storage/sdcard0/");
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
    public void generatelist(StringBuilder builder){
        File file = new File(builder.toString());
        String string[] = file.list();
        final List<String> files = new ArrayList<>();
        for(int i =0; i<string.length; i++){
            File filename = new File(string[i]);
            if(string[i].substring(string[i].length()-3, string[i].length()).equalsIgnoreCase("pdf")){
                files.add(string[i]);
            }
            if(filename.isDirectory()){
                builder.append(string[i].toString());
                File filee = new File(builder.toString());
                String stringg[] = filee.list();
                for(int j =0; j<stringg.length; j++) {
                    File filenamee = new File(string[j]);
                    if (string[i].substring(string[j].length() - 3, string[j].length()).equalsIgnoreCase("pdf")) {
                        files.add(string[i]);
                    }if(filenamee.isDirectory()){

                    }

                }
            }
        }
    }
}
