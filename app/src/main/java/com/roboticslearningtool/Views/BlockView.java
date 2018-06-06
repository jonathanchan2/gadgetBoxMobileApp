package com.roboticslearningtool.Views;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.roboticslearningtool.R;

/**
 * Created by Dan on 20/05/2018.
 */

public class BlockView extends LinearLayout {
    View rootView;
    EditText portField;
    EditText valueField;
    ImageView icon;
    String msg = "Mydebug";
    RelativeLayout.LayoutParams layoutParams;

    public BlockView(Context context, int type) {
        super(context);
        init(context, type);
        this.setId(View.generateViewId());
    }

    public BlockView(Context context, AttributeSet attrs, int type) {
        super(context, attrs);
        init(context, type);
    }

    private void init(Context context, int type) {
        if (type == 1) {
            rootView = inflate(context, R.layout.oneblock, this);
            setBackgroundResource(R.drawable.smallrectangle);
            setGravity(Gravity.CENTER_VERTICAL);
            portField = (EditText) rootView.findViewById(R.id.onlyValue);
            portField.setFocusableInTouchMode(true);
            icon = (ImageView) rootView.findViewById(R.id.blockImageSingle);
        } else if (type == 2) {
            rootView = inflate(context, R.layout.twoblock, this);
            setBackgroundResource(R.drawable.blockbackground);
            setGravity(17);
            portField = (EditText) rootView.findViewById(R.id.RightValue);
            portField.setFocusableInTouchMode(true);
            valueField = (EditText) rootView.findViewById(R.id.LeftValue);
            valueField.setFocusableInTouchMode(true);
            icon = (ImageView) rootView.findViewById(R.id.blockImageDbl);

        } else if (type == 3) {
            rootView = inflate(context, R.layout.startblock, this);
            setGravity(17);
        } else if (type == 4) {
            rootView = inflate(context, R.layout.stopblock, this);
            setGravity(17);
        } else {
            rootView = inflate(context, R.layout.ifblock, this);
            setBackgroundResource(R.drawable.ifblock);
            setGravity(17);


        }
        rootView.setClickable(true);
        rootView.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");

                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        layoutParams.leftMargin = x_cord;
                        layoutParams.topMargin = y_cord;
                        v.setLayoutParams(layoutParams);
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                        v.setVisibility(View.VISIBLE);


                        // Do nothing
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d(msg, "ACTION_DROP event");

                        ClipData.Item item = event.getClipData().getItemAt(0);

                        // Gets the text data from the item.
                        String dragData = item.getText().toString();


                        // Turns off any color tints
                        v.getBackground().clearColorFilter();

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        View v2 = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) v2.getParent();
                        owner.removeView(v2);//remove the dragged view
                        DrawingView container = (DrawingView) v;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                        container.addView(v2);//Add the dragged view
                        v2.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                        break;
                    default: break;
                }
                return true;
            }
        });
        rootView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("Generic Label", "text");
                    DragShadowBuilder shadowBuilder = new DragShadowBuilder(rootView);

                    if (Build.VERSION.SDK_INT >= 24) {
                        v.startDragAndDrop(data, shadowBuilder, rootView, 0);
                    }
                    else{
                        v.startDrag(data, shadowBuilder, rootView, 0);

                    }
                    rootView.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }

            }
        });
        rootView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
                DragShadowBuilder myShadow = new DragShadowBuilder(rootView);

                if (Build.VERSION.SDK_INT >= 24) {
                    v.startDragAndDrop(dragData, myShadow, null, 0);
                }
                else{

                    v.startDrag(dragData, myShadow, null, 0);
                }
                return true;
            }
        });

    }

    public EditText getPortField() {
        return portField;
    }

    public void setPortField(EditText portField) {
        this.portField = portField;
    }

    public EditText getValueField() {
        return valueField;
    }

    public void setValueField(EditText valueField) {
        this.valueField = valueField;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }



}
