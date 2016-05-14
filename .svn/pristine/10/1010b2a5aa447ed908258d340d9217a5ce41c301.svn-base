package sg.edu.nus.iss.sereserch.ethan.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;
import sg.edu.nus.iss.sereserch.ethan.view.SetPathActivity;

/**
 * Created by A0136363H on 8/14/2015.
 */
public class SetPathControl {

    private MainControl mc;
    public static final String PREFS = "MyPath";

    public void startSetPath() {
        Intent setPathIntent = new Intent(AppProperties.getContext(), SetPathActivity.class);
        mc = ControlFactory.getMainControl();
        mc.displayScreen(setPathIntent);
    }

   public void settingPreference(String newPath) {

        SharedPreferences settings = AppProperties.getContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS, newPath);
        editor.apply();

    }


}
