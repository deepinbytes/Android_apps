package sg.edu.nus.iss.sereserch.ethan.application;

import android.content.Context;

/**
 * Created by Varshad on 29/5/2015.
 */
public class AppProperties {
    private static Context context;
    public static Context getContext(){
        return context;
    }
    public static void setContext(Context context){
        AppProperties.context = context;
    }
}
