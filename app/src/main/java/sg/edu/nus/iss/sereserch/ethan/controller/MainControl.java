package sg.edu.nus.iss.sereserch.ethan.controller;

import android.content.Intent;


import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;

/**
 * Created by Varshad on 28/5/2015.
 */
public class MainControl {

    public MainControl() {
    }

    public void onClickImageView() {
        AnnotateImageControl ivc = ControlFactory.getAnnotateImageControl();
        ivc.startImageView();
    }

    public void onClickSmartSearch() {
        SmartSearchControl ssc = ControlFactory.getSmartSearchViewControl();
        ssc.startSmartSearchView();
    }
    public void onClickSetPath(){
        SetPathControl setPathControl=ControlFactory.getInstance().getSetPathControl();
        setPathControl.startSetPath();
    }


    public void displayScreen(Intent intent) {
        AppProperties.getContext().startActivity(intent);
    }
}
