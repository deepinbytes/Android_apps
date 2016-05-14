package sg.edu.nus.iss.sereserch.ethan.controller;

import android.content.Intent;

import com.radaee.reader.PDFNavAct;


import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;

/**
 * Created by Ajay on 05-Jun-15.
 */
public class SmartSearchControl {
    private MainControl mc;
    private static final int REQUEST_CODE = 1234;

    public void startSmartSearchView() {
        Intent smartSearchIntent = new Intent(AppProperties.getContext(), PDFNavAct.class);
        mc = ControlFactory.getMainControl();
        mc.displayScreen(smartSearchIntent);
    }

}
