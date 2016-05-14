package sg.edu.nus.iss.sereserch.ethan.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import com.radaee.reader.R;
import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;
import sg.edu.nus.iss.sereserch.ethan.controller.ControlFactory;
import sg.edu.nus.iss.sereserch.ethan.controller.AnnotateImageControl;

public class ImageViewActivity extends Activity {

    ImageButton voiceSearchButton;
    Button fileSearchButton;
    EditText searchText;
    ListView resultListView;
    private static final int REQUEST_CODE = 1234;
    Intent intent;
    TextView pathDisplay;
   private  File file;
    private MainActivity mainActivity = new MainActivity();
private String imgname=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        final Context appContext=this;
        //setActionBar();
        pathDisplay = (TextView) findViewById(R.id.pathLabel);
        ControlFactory.getInstance().getAnnotateImageControl().gettingPreference();
        pathDisplay.setText(ControlFactory.getInstance().getAnnotateImageControl().getChangedPath());
        voiceSearchButton = (ImageButton) findViewById(R.id.voiceSearchButton);
        fileSearchButton = (Button) findViewById(R.id.fileSearchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        resultListView = (ListView) findViewById(R.id.resultListView);
        AppProperties.setContext(this);
        final Activity activity = this;
        final AnnotateImageControl annotateImageControl = ControlFactory.getAnnotateImageControl();
        fileSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 file = annotateImageControl.openInGallery(searchText.getText().toString());


      /*  AnnotateImageView.filename = filename;
        setContentView(R.layout.annotate_diagram);*/
                //filename = getIntent().getStringExtra("filepath");

                intent = new Intent(activity,AnnotateDiagramActivity.class );
                intent.putExtra("filepath",file.getAbsolutePath());
                imgname= file.getAbsolutePath();
                startActivity(intent);
                File imgfile=new File(imgname);
                if (!imgfile.exists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
                    builder.setTitle("Error!");
                    TextView Error_msg = new TextView(appContext);
                    Error_msg.setText("File not found");
                    builder.setView(Error_msg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //System.out.print("xxx");
                            // ControlFactory.getImageViewControl().startImageView();
                            searchText.setText(null);
                            //finish();
                        }
                    });
                    builder.show();}

            }
        });
        voiceSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annotateImageControl.voiceSearch(activity);
            }
        });
        ImageButton folderButton = (ImageButton) findViewById(R.id.folderButton);
        folderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.callSetPathView();
            }
        });



    }
 /* @Override
  protected void onStop(){
        intent=null;
        super.onStop();

  }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_imageview) {
            return true;
        }*/
        if (id == R.id.action_setPath) {
            MainActivity.callSetPathView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = intent.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            resultListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));

            resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                    String selectedFromList = (String) (resultListView.getItemAtPosition(myItemInt));
                    searchText.setText(selectedFromList);

                }
            });
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }
/*
    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ethanlogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }*/
}
