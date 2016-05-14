package sg.edu.nus.iss.sereserch.ethan.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.radaee.reader.R;

import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;

public class AnnotateDiagramActivity extends Activity {

    private static final String LOG_TAG = AnnotateDiagramActivity.class.getSimpleName();
    private static String filename = "";
    //public ArrayList<String> matches;
    private static final int REQUEST_CODE = 1234;
    // Views
    private AnnotateImageView annotateImageView;
    private Button btnClear, btnSave, btnUpdate, btnUndo;
    private ToggleButton toggle;
    private ListView labellist;
    public static String appPath = null;
    Singleton m_Inst = Singleton.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_Inst.InitGUIFrame(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        labellist = (ListView) findViewById(R.id.labelList);
        appPath = getApplicationInfo().dataDir;
        filename = getIntent().getStringExtra("filepath");
      /*  AnnotateImageView.filename = filename;
        setContentView(R.layout.annotate_diagram);*/
        //filename = getIntent().getStringExtra("filepath");
        File file = new File(filename);

        if (file.exists()) {
            AnnotateImageView.filename = filename;
            setContentView(R.layout.annotate_diagram);
            initUI();
            System.out.print("Resume");
        }else
        {
            finish();
        }



    }

    /*@Override
    *//*protected void onResume() {
      //  initUI();
      // System.out.print("Resume");
        super.onResume();
    }*/


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = intent.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
           /* labellist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));

            labellist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                    String selectedFromList = (String) (labellist.getItemAtPosition(myItemInt));
                    AnnotateImageView.resultList(selectedFromList);
                }
            });*/
            AnnotateImageView.resultList(matches);
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void initUI() {

        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(mViewClickListener);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(mViewClickListener);


        btnUndo = (Button) findViewById(R.id.btn_undo);
        btnUndo.setOnClickListener(mViewClickListener);

        annotateImageView = (AnnotateImageView) findViewById(R.id.image_editor_view);

        //final int orientation = annotateImageView.getImageWidth() > annotateImageView.getImageHeight()  ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        annotateImageView.setFilename(filename);
        annotateImageView.setListViewObj(labellist);
        annotateImageView.setAppcontext(AnnotateDiagramActivity.this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

   /*public ListView getListObject()
    {
        return labellist;
    }*/


    private final OnClickListener mViewClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {


            switch (view.getId()) {


                case R.id.btn_clear:
                    annotateImageView.clear();
                    // annotateImageView.PopulateLabelList();
                    break;

                case R.id.btn_save:
                    try {
                        annotateImageView.saveAnnotationstoDB();

                    } catch (IOException iOException) {
                        Log.e(LOG_TAG, "Exception while saving file" + " : " + iOException);
                    }
                    break;
                case R.id.btn_undo:
                    annotateImageView.undo();
                    break;

                default:
                    break;
            }

        }
    };
}
