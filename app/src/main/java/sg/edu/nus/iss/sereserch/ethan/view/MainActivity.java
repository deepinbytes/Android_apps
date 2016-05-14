package sg.edu.nus.iss.sereserch.ethan.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.radaee.reader.R;
import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;
import sg.edu.nus.iss.sereserch.ethan.controller.ControlFactory;
import sg.edu.nus.iss.sereserch.ethan.controller.MainControl;


public class MainActivity extends AppCompatActivity {
    private Button btnAnnotateImage;
    private Button btnSmartSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppProperties.setContext(this);
        setContentView(R.layout.activity_main);
        setActionBar();
        btnAnnotateImage=(Button) findViewById(R.id.btnAnnotateImage);
        btnAnnotateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callImageView();
            }
        });
        btnSmartSearch=(Button)findViewById(R.id.btnSmartSearch);
        btnSmartSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                callSmartSearch();
               /* Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.radee.reader","com.radaee.reader.MainActivity"));
                startActivity(intent);*/
              /*  Intent i;
                PackageManager manager = getPackageManager();
                try {
                    i = manager.getLaunchIntentForPackage("com.radee.reader");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {

                }*/
            }
        });



    }

    public void testrotardknob()
    {
        Singleton m_Inst = Singleton.getInstance();
        m_Inst.InitGUIFrame(this);

        RelativeLayout panel = new RelativeLayout(this);
        setContentView(panel);








}

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
      /* if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_imageview) {
            callImageView();
            return true;
        }*/
        if (id == R.id.action_setPath) {
            callSetPathView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ethanlogo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    public void callImageView() {
        MainControl mc = ControlFactory.getMainControl();
        mc.onClickImageView();
    }
    public static void callSetPathView() {
        MainControl mc = ControlFactory.getInstance().getMainControl();
        mc.onClickSetPath();
    }


    public void callSmartSearch() {
        MainControl mc = ControlFactory.getMainControl();
        mc.onClickSmartSearch();
    }
}
