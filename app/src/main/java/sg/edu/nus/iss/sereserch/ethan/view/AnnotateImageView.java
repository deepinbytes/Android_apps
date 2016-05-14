package sg.edu.nus.iss.sereserch.ethan.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.radaee.reader.R;

import sg.edu.nus.iss.sereserch.ethan.application.AppProperties;
import sg.edu.nus.iss.sereserch.ethan.controller.AnnotateImageControl;
import sg.edu.nus.iss.sereserch.ethan.controller.ControlFactory;
import sg.edu.nus.iss.sereserch.ethan.dao.AnnotationDBManager;
import sg.edu.nus.iss.sereserch.ethan.model.CoordinateModel;
import sg.edu.nus.iss.sereserch.ethan.utils.MediaStorageUtils;

import static com.radaee.reader.R.id.resultListView;
import static com.radaee.reader.R.id.textView2;


public class AnnotateImageView  extends ZoomImageView implements WheelModel.Listener{

    private static final String LOG_TAG = AnnotateImageView.class.getName();
    public static final String BLACK = "BLACK";
    public static final String WHITE = "WHITE";
    public static final String RED = "RED";
    public static final String YELLOW = "YELLOW";
    public static final String BLUE = "BLUE";
    public static final String GREEN = "GREEN";
    public static final int FONTSMALL = 40;
    public static final int FONTMEDIUM = 50;
    public static final int FONTLARGE = 60;
private RoundKnobButton rv;
    public static String selectedColor;
    public static int selectedFontSize;
    public static int textRotation;
    public static ArrayList<CoordinateModel> recentAnotationCordinates;
    public static String filename;
    private Paint mPaint;
    private GestureDetector mTapDetector;
    private boolean AnnotateOn;
    SimpleCursorAdapter mAdapter;
    private ListView lvxs;
    Point p;
    CoordinateModel LastCoordinate;
    private float LastCoordinateX;
    private float LastCoordinateY;
    AnnotationDBManager Amgr;
    private int selectedLabelIndex;
    static EditText txtBox;
    public static String selectedFromList;
    private int screenWidth;
    private int screenHeight;
    static int listcounter;
    int halfRectangleWidth;
    int halfRectangleHeight;
    private Context context;
    private Activity Appcontext;
    Button fontMedium = null;
    ImageButton blackButton = null;
    private static ListView voiceList;

    public AnnotateImageView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public AnnotateImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            System.out.print("init");
        }
        listcounter = 0;
        selectedLabelIndex = -1;
        AnnotateOn = false;
        this.context = context;
        initCoordinates();
        getScreenDimensions();
        //recentAnotationCordinates = new Stack<CoordinateModel>();
        recentAnotationCordinates = new ArrayList<CoordinateModel>();
        //    lvx=(ListView)super.findViewById(R.id.listViewlabel);
        setImageResource(filename);

        Amgr = new AnnotationDBManager();
        ArrayList<CoordinateModel> temp = Amgr.restoreAnnotations(filename);
        if (temp.size() > 0) {
            recentAnotationCordinates = temp;
            LastCoordinate = new CoordinateModel();
            LastCoordinate.setX(0f);
            LastCoordinate.setY(0f);
        }


    }

    public AnnotateImageView(Context context) {
        this(context, null, 0);
    }

    private void initCoordinates() {
        mTapDetector = new GestureDetector(getContext(), new TapDetector());
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void getScreenDimensions() {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        } else {
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        }

        // set Rectangle dimensions
        halfRectangleWidth = Math.min(screenWidth, screenHeight) / 50;
        halfRectangleHeight = halfRectangleWidth * 4 / 5;

        // reduce footer height
        screenHeight = (int) (screenHeight - getResources().getDimension(R.dimen.footer_height));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTapDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void onDialPositionChanged(WheelModel sender, int nicksChanged) {
       // TextView text = (TextView) findViewById(R.id.text);
       // text.setText(sender.getCurrentNick() + "");
    }
    @Override
    protected void drawView(Canvas canvas) {
        super.drawView(canvas);

        // Draw coordinates.
        Iterator<CoordinateModel> iterator = recentAnotationCordinates.iterator();
        while (iterator.hasNext()) {
            CoordinateModel cordinate = iterator.next();

            /*if(cordinate.getlabel()==recentAnotationCordinates.get(selectedLabelIndex).getlabel())
            {

                mPaint.setColor(Color.GREEN);
                canvas.drawRect(cordinate.getX() - halfRectangleWidth, cordinate.getY() - halfRectangleHeight,
                        cordinate.getX() + halfRectangleWidth, cordinate.getY() + halfRectangleHeight, mPaint);
                mPaint.setTextSize(20);
                mPaint.setColor(Color.BLACK);

                mPaint.setColor(Color.RED);
            }*/
            if (AnnotateOn == false) {
                if (checkTolerance(cordinate, LastCoordinate)) {
                    mPaint.setColor(Color.GREEN);
                    canvas.drawRect(cordinate.getX() - halfRectangleWidth, cordinate.getY() - halfRectangleHeight,
                            cordinate.getX() + halfRectangleWidth, cordinate.getY() + halfRectangleHeight, mPaint);
                    mPaint.setTextSize(cordinate.getSize());
                    mPaint.setColor(intColor(cordinate.getColor()));
                    //canvas.rotate((float)cordinate.getRotation(),cordinate.getX(),cordinate.getY());
                    canvas.rotate(-(float) cordinate.getRotation(), cordinate.getX(), cordinate.getY());
                    canvas.drawText(cordinate.getString(), cordinate.getX(), cordinate.getY(), mPaint);
                    //  gestureImageViewTouchListener.handleScale((float) 1.22, cordinate.getX(), cordinate.getY());
                    canvas.rotate((float) cordinate.getRotation(), cordinate.getX(), cordinate.getY());

                    mPaint.setColor(Color.RED);
                } else {
                    canvas.drawRect(cordinate.getX() - halfRectangleWidth, cordinate.getY() - halfRectangleHeight,
                            cordinate.getX() + halfRectangleWidth, cordinate.getY() + halfRectangleHeight, mPaint);

                }
            } else {
                canvas.drawRect(cordinate.getX() - halfRectangleWidth, cordinate.getY() - halfRectangleHeight,
                        cordinate.getX() + halfRectangleWidth, cordinate.getY() + halfRectangleHeight, mPaint);

                /*
                p=new Point();
                p.x= ((int) cordinate.getX());p.y= ((int) cordinate.getY());
                showPopup(Appcontext);
                */
                //PopulateLabelList();
            }

        }

    }

    private int intColor(String color) {
        int intColor;
        switch (color) {
            case AnnotateImageView.BLACK:
                intColor = Color.BLACK;
                break;
            case AnnotateImageView.BLUE:
                intColor = Color.BLUE;
                break;
            case AnnotateImageView.WHITE:
                intColor = Color.WHITE;
                break;
            case AnnotateImageView.RED:
                intColor = Color.RED;
                break;
            case AnnotateImageView.GREEN:
                intColor = Color.GREEN;
                break;
            case AnnotateImageView.YELLOW:
                intColor = Color.YELLOW;
                break;
            default:
                intColor = Color.BLACK;
        }

        return intColor;
    }

    private boolean checkTolerance(CoordinateModel original, CoordinateModel received) {
        float toleranceValue = (float)29.0;

        System.out.println(original.getX() + "  " + original.getY());
        System.out.println(received.getX() + "  " + received.getY());

        boolean status = (received.getX() - original.getX() < toleranceValue && received.getX() - original.getX() > -toleranceValue)
                && (received.getY() - original.getY() < toleranceValue
                && received.getY() - original.getY() > -toleranceValue);

        return status;
    }

    public void reset() {
        super.reset();
        invalidate();
        if (gestureImageViewTouchListener != null) {
            gestureImageViewTouchListener.handleUp();
        }
    }

    public void move(int id) {
        if (!recentAnotationCordinates.isEmpty()) {
            selectedLabelIndex = id;
            invalidate();
        }
    }

    /**
     * User click detector
     */
    private class TapDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent event) {
            AnnotateOn = true;

            if (AnnotateOn == true) {

                Log.d(LOG_TAG, event.getX() + " " + event.getY());
                float cordinateX = (event.getX() - x) / scaleAdjust;
                float cordinateY = (event.getY() - y) / scaleAdjust;

                final float left = cordinateX - halfRectangleWidth;
                final float top = cordinateY - halfRectangleHeight;
                final float right = cordinateX + halfRectangleWidth;
                final float bottom = cordinateY + halfRectangleHeight;

                // Draw only when coordinate is within image bounds.
                Rect imageBounds = drawable.getBounds();
                if (left >= imageBounds.left && top >= imageBounds.top && right <= imageBounds.right
                        && bottom <= imageBounds.bottom) {
                    // Draw point

                    CoordinateModel cordinate = new CoordinateModel();
                    cordinate.setX(cordinateX);
                    cordinate.setY(cordinateY);

                    cordinate.setlabel("" + listcounter);

                    listcounter++;

                    recentAnotationCordinates.add(cordinate);
                    p = new Point();
                    p.x = ((int) cordinate.getX());
                    p.y = ((int) cordinate.getY());
                    showPopup(Appcontext);

                    invalidate();
                }
            }

        }


        public boolean onSingleTapUp(MotionEvent event) {
            AnnotateOn = false;


            if (AnnotateOn == false) {

                Log.d(LOG_TAG, event.getX() + " " + event.getY());

                LastCoordinate = new CoordinateModel();
                LastCoordinate.setX((event.getX() - x) / scaleAdjust);
                LastCoordinate.setY((event.getY() - y) / scaleAdjust);
                invalidate();


            }
            return super.onSingleTapUp(event);
        }


    }

    public void clear() {
        recentAnotationCordinates.clear();
        reset();
    }

    public void setAnnotateFlag(boolean val) {
        AnnotateOn = val;
    }

    public void undo() {
        if (!recentAnotationCordinates.isEmpty()) {
            recentAnotationCordinates.remove(recentAnotationCordinates.size() - 1);
            invalidate();
        }
    }

    public void getElement(int id) {
        if (!recentAnotationCordinates.isEmpty()) {
            recentAnotationCordinates.get(id);
            invalidate();
        }
    }


    public void remove(int id) {
        if (!recentAnotationCordinates.isEmpty()) {
            recentAnotationCordinates.remove(id);
            invalidate();
        }
    }

    public void setFilename(String fname) {
        filename = fname;
    }
   /* public ArrayList<CoordinateModel> getlist()
    {
        return recentAnotationCordinates;

    }
    public void PopulateLabelList()
    {
        ArrayAdapter<CoordinateModel> myad=  new ArrayAdapter<CoordinateModel>(super.getContext(),R.layout.listitem, recentAnotationCordinates);
        lvxs.setAdapter(myad);
    }*/

    public void setAppcontext(Activity c) {
        Appcontext = c;
    }

    public void setListViewObj(ListView test) {
        lvxs = test;
    }

    private void showPopup(final Activity context) {

        int popupWidth = screenWidth; //600;
        int popupHeight = screenHeight * 9 / 20;//400;


        Singleton m_Inst = Singleton.getInstance();
        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popuplay, viewGroup);
        fontMedium = (Button) layout.findViewById(R.id.font_medium);
        blackButton = (ImageButton) layout.findViewById(R.id.blackButton);
        fontMedium.performClick();
        blackButton.performClick();
        //rv = (RoundKnobButton) layout.findViewById(R.id.rotator);
        //rv = new RoundKnobButton(viewGroup.getContext(), );


      //  rv.setRotorPercentage(100);
       /* rv.SetListener(new RoundKnobButton.RoundKnobButtonListener() {
            public void onStateChange(boolean newstate) {
               //Toast.makeText(this, "New state:" + newstate, Toast.LENGTH_SHORT).show();
            }

            public void onRotate(final int percentage) {
                /*tv2.post(new Runnable() {
                    public void run() {
                        tv2.setText("\n" + percentage + "%\n");

                }});
            }
        });
        */
        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
        popup.setOutsideTouchable(false);
        // Getting a reference to Close button, and close the popup when clicked.
        txtBox = (EditText) layout.findViewById(textView2);
        Button close = (Button) layout.findViewById(R.id.close);
        getColor(layout);
        getSize(layout);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                popup.dismiss();
                undo();
                //PopulateLabelList();

            }
        });
        ImageButton voiceButton = (ImageButton) layout.findViewById(R.id.voiceButton);
        final AnnotateImageControl annotateImageControl = ControlFactory.getAnnotateImageControl();
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                annotateImageControl.voiceSearch(context);
            }
        });
        voiceList = (ListView) layout.findViewById(R.id.labelList);
       // SeekBar rotateSeekBar = (SeekBar) layout.findViewById(R.id.rotateSeekBar);
        final TextView rotateText = (TextView) layout.findViewById(R.id.rotateText);
        //rotateSeekBar.setProgress(90);
       // AnnotateImageView.textRotation = rotateSeekBar.getProgress() - 90;
      //  rotateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {





        Button okay = (Button) layout.findViewById(R.id.ok);
        okay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                popup.dismiss();
                recentAnotationCordinates.get(recentAnotationCordinates.size() - 1).setName(txtBox.getText().toString());
                recentAnotationCordinates.get(recentAnotationCordinates.size() - 1).setColor(AnnotateImageView.selectedColor);
                recentAnotationCordinates.get(recentAnotationCordinates.size() - 1).setSize(AnnotateImageView.selectedFontSize);
                recentAnotationCordinates.get(recentAnotationCordinates.size() - 1).setRotation(AnnotateImageView.textRotation);

                //PopulateLabelList();

            }
        });
    }

    protected static void resultList(ArrayList<String> matches) {
        //selectedFromList = matches;
        voiceList.setAdapter(new ArrayAdapter<String>(AppProperties.getContext(), android.R.layout.simple_list_item_1,
                matches));

        voiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedFromList = (String) (voiceList.getItemAtPosition(myItemInt));
                txtBox.setText(selectedFromList);
                voiceList.setVisibility(View.GONE);
            }
        });

    }

    public void getSize(View layout) {
        final Button fontSmall = (Button) layout.findViewById(R.id.font_small);
        final Button fontLarge = (Button) layout.findViewById(R.id.font_large);
        final Button[] fontSizeButtons = {fontSmall, fontMedium, fontLarge};
        fontMedium.performClick();
        AnnotateImageView.selectedFontSize = AnnotateImageView.FONTMEDIUM;
        fontSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFontButtons(fontSizeButtons);
                fontSmall.setBackgroundColor(Color.parseColor("#ff9b93"));
                AnnotateImageView.selectedFontSize = AnnotateImageView.FONTSMALL;
            }
        });
        fontMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFontButtons(fontSizeButtons);
                fontMedium.setBackgroundColor(Color.parseColor("#ff9b93"));
                AnnotateImageView.selectedFontSize = AnnotateImageView.FONTMEDIUM;
            }
        });
        fontLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFontButtons(fontSizeButtons);
                fontLarge.setBackgroundColor(Color.parseColor("#ff9b93"));
                AnnotateImageView.selectedFontSize = AnnotateImageView.FONTLARGE;
            }
        });


    }

    public void resetFontButtons(Button[] fontSizeButtons) {
        for (int i = 0; i < fontSizeButtons.length; i++) {
            fontSizeButtons[i].setBackgroundColor(Color.WHITE);
        }
    }

    public void saveAnnotationstoDB() throws IOException {
        Amgr = new AnnotationDBManager();
        Amgr.saveAnnotations(filename, recentAnotationCordinates);
    }

    /**
     * Save/Replace annotated diagram into memory.
     *
     * @return true if successfully saved, false otherwise
     * @throws IOException
     */
    public void save() throws IOException {
        reset();
        invalidate();
        gestureImageViewTouchListener.handleUp();
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        Bitmap bitmap = getDrawingCache();
        if (bitmap != null) {

            Rect imageBounds = drawable.getBounds();

            int bitmapWidth = imageBounds.right - imageBounds.left;
            int bitmapHeight = imageBounds.bottom - imageBounds.top;

            bitmapHeight = bitmapHeight * screenWidth / bitmapWidth;
            if (bitmapHeight > screenHeight) {
                bitmapWidth = bitmapWidth * screenHeight / bitmapHeight;
                bitmapHeight = screenHeight;
            }
            try {
                bitmap = Bitmap.createBitmap(bitmap, 0, (screenHeight - bitmapHeight) / 2, screenWidth, bitmapHeight);
            } catch (Exception exception) {
                Log.e(LOG_TAG, "Exception while resizing bitmap " + exception);
            }
            MediaStorageUtils.saveToStorage(context, bitmap);
        }
        setDrawingCacheEnabled(false);
    }

    public void getColor(View layout) {

        final ImageButton whiteButton = (ImageButton) layout.findViewById(R.id.whiteButton);
        final ImageButton blueButton = (ImageButton) layout.findViewById(R.id.blueButton);
        final ImageButton greenButton = (ImageButton) layout.findViewById(R.id.greenButton);
        final ImageButton yellowButton = (ImageButton) layout.findViewById(R.id.yellowButton);
        final ImageButton redButton = (ImageButton) layout.findViewById(R.id.redButton);
        blackButton.performClick();
        AnnotateImageView.selectedColor = AnnotateImageView.BLACK;
        final ImageButton[] colorButtons = {blackButton, whiteButton, redButton, greenButton, blueButton, yellowButton,};
        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColourButtons(colorButtons);
                blackButton.setPadding(4, 4, 4, 4);
                AnnotateImageView.selectedColor = AnnotateImageView.BLACK;
            }
        });
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColourButtons(colorButtons);
                whiteButton.setPadding(4, 4, 4, 4);
                AnnotateImageView.selectedColor = AnnotateImageView.WHITE;
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColourButtons(colorButtons);
                redButton.setPadding(4, 4, 4, 4);
                AnnotateImageView.selectedColor = AnnotateImageView.RED;
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColourButtons(colorButtons);
                greenButton.setPadding(4, 4, 4, 4);
                AnnotateImageView.selectedColor = AnnotateImageView.GREEN;
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColourButtons(colorButtons);
                blueButton.setPadding(4, 4, 4, 4);
                AnnotateImageView.selectedColor = AnnotateImageView.BLUE;
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColourButtons(colorButtons);
                yellowButton.setPadding(4, 4, 4, 4);
                AnnotateImageView.selectedColor = AnnotateImageView.YELLOW;
            }
        });
    }


    public void resetColourButtons(ImageButton[] colorButtons) {
        for (int i = 0; i < 6; i++) {
            colorButtons[i].setPadding(1, 1, 1, 1);
        }


    }
}
