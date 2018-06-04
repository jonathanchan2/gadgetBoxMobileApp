package com.roboticslearningtool.Views;

import android.annotation.TargetApi;
import android.content.Context;
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
    Path path;

    public void setArrowList(List<RoboArrow> arrowList) {
        this.arrowList = arrowList;
    }
    List<Path> arrowPathList;
    List<RoboArrow> arrowList ;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
        init();
    }

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){
        pathPaint = new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setColor(Color.RED);
        pathPaint.setStrokeWidth(12);
        arrowPathList= new ArrayList<>();

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.RED);
        textPaint.setStrokeWidth(2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<arrowPathList.size();i++){
            Path path = arrowPathList.get(i);
            canvas.drawPath(path, pathPaint);


        }
    }

    public void setupArrows(){

        for(int i=0;i<arrowList.size();i++){
            path = new Path();
            RoboArrow arrow = arrowList.get(i);
            path.moveTo(arrow.arrowStartx,arrow.arrowStarty);
            path.quadTo(arrow.arrowMidx, arrow.arrowMidy, arrow.arrowEndx, arrow.arrowEndy);


            arrowPathList.add(path);



        }

    }


}