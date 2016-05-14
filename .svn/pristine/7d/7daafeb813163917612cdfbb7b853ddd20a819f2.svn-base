package sg.edu.nus.iss.sereserch.ethan.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;

import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;
import sg.edu.nus.iss.sereserch.ethan.view.ImageViewActivity;

/**
 * Created by Varshad on 28/5/2015.
 */
public class AnnotateImageControl {
    private MainControl mc;
    public String path = "/mnt/sdcard/DCIM/Camera/";
    private static final int REQUEST_CODE = 1234;
    public static final String PREFS = "MyPath";
    public String changedPath = null;

    public String getChangedPath(){
        return  changedPath;
    }

    public void startImageView() {
        Intent imageViewIntent = new Intent(AppProperties.getContext(), ImageViewActivity.class);
        mc = ControlFactory.getMainControl();
        mc.displayScreen(imageViewIntent);
    }
    public void gettingPreference() {

        SharedPreferences getting = AppProperties.getContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        String differentPath = getting.getString(PREFS, null);
        if (differentPath != path) {

            changedPath = differentPath;

        }
        else if(differentPath != null){

            changedPath = path;
        }
        else {

            changedPath = path;
        }

    }

    public File openInGallery(String imageId) {
        String ext = ".jpg";
        File file=new File(changedPath +"/"+ imageId + ext);
        return file;
    }

    public void voiceSearch(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the image name");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        activity.startActivityForResult(intent, REQUEST_CODE);

    }
}
