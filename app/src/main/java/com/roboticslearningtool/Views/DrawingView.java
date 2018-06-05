package com.roboticslearningtool.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.roboticslearningtool.Classes.RoboArrow;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends RelativeLayout {


    Paint textPaint;
    Paint pathPaint;
    List<RoboArrow> arrowList ;
    Path path;
    private Bitmap bitmap;

    public void setArrowList(List<RoboArrow> arrowList) {
        this.arrowList = arrowList;
    }



    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public DrawingView(Context context) {
        super(context);

    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<arrowList.size();i++){

            canvas.drawPath(arrowList.get(i).getArrowPath(), arrowList.get(i).getArrowPaint());


        }
//        canvas.drawBitmap(bitmap,700,900,arrowList.get(2).getArrowPaint());
    }


    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap,50,50,false);
    }
}