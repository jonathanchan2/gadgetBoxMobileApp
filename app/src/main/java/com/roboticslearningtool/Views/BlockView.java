package com.roboticslearningtool.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.roboticslearningtool.R;

/**
 * Created by Dan on 20/05/2018.
 */

public class BlockView extends LinearLayout {
    View rootView;
    EditText portField;
    EditText valueField;
    ImageView icon;


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
        } else if (type==4) {
            rootView = inflate(context, R.layout.stopblock, this);
            setGravity(17);
        }
        else{
            rootView = inflate(context, R.layout.ifblock, this);
            setBackgroundResource(R.drawable.ifblock);
            setGravity(17);


        }


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
