package sg.edu.nus.iss.sereserch.ethan.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sg.edu.nus.iss.sereserch.ethan.model.CoordinateModel;
import sg.edu.nus.iss.sereserch.ethan.view.AnnotateDiagramActivity;


/**
 * Created by Ajay on 04-Jun-15.
 */
public class AnnotationDBManager {
    SQLiteDatabase db;
    SQLiteDatabase.CursorFactory factory;


    public AnnotationDBManager() {

        db = SQLiteDatabase.openOrCreateDatabase(AnnotateDiagramActivity.appPath + DAOConstants.dBName, factory, null);
        db.execSQL(DAOConstants.QUERY_CREATE_ANNOTATIONS_TABLE);
    }

    public void saveAnnotations(String filename, ArrayList<CoordinateModel> AnnotationDatalist) {
        db.execSQL(DAOConstants.QUERY_DELETE_ANNOTATIONS_TABLE + "'" + filename + "'");

        for (CoordinateModel item : AnnotationDatalist) {
            db.execSQL(DAOConstants.QUERY_INSERT_ANNOTATIONS_TABLE + "'" + filename + "','" + item.getX() +
                    "','" + item.getY() + "','" +item.getlabel()+"','"+item.getString() +"','"+item.getColor() +"','"+item.getSize() +"','"+item.getRotation() +"');");
        }
    }

    public ArrayList<CoordinateModel> restoreAnnotations(String filename) {
        ArrayList<CoordinateModel> result = new ArrayList<CoordinateModel>();

        Cursor c = db.rawQuery(DAOConstants.QUERY_SELECT_ANNOTATIONS_TABLE + "'" + filename.trim() + "'", null);
        if (c.moveToFirst()) {
            do {
                CoordinateModel obj = new CoordinateModel();
                obj.setX(c.getFloat(c.getColumnIndex("xCoordinate")));
                obj.setY(c.getFloat(c.getColumnIndex("yCoordinate")));
                obj.setlabel(c.getString(c.getColumnIndex("label")));
                obj.setName((c.getString(c.getColumnIndex("name"))));
                obj.setColor((c.getString(c.getColumnIndex("color"))));
                obj.setSize(c.getInt(c.getColumnIndex("size")));
                obj.setRotation(c.getInt(c.getColumnIndex("rotation")));
                result.add(obj);
            } while (c.moveToNext());
            c.close();
            db.close();

        }
        return result;
    }
}
