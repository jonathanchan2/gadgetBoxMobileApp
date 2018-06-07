package com.roboticslearningtool.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.roboticslearningtool.R;

public class RoboArrow {
    private RoboBlock startBlock;
    private RoboBlock endBlock;
    private int startblockNum;
    private int endblockNum;
    private int arrowColor;
    private Path arrowPath;
    private Paint arrowPaint;
    private float arrowStartx;
    private float arrowStarty;
    private float arrowEndx;
    private float arrowEndy;
    private float arrowMidx;
    private float arrowMidy;
    private float arrowHeadx;
    private float arrowHeady;
    Context appcontext;

    public float getArrowStartx() {
        return arrowStartx;
    }

    public float getArrowStarty() {
        return arrowStarty;
    }

    public float getArrowMidx() {
        return arrowMidx;
    }

    public float getArrowMidy() {
        return arrowMidy;
    }

    public float getArrowEndy() {
        return arrowEndy;
    }

    public float getArrowEndx() {
        return arrowEndx;
    }

    public float getArrowHeadx() {
        return arrowHeadx;
    }

    public float getArrowHeady() {
        return arrowHeady;
    }


    private int arrowtype;// 1 for connector, 2 for top loop ,3 for bottom loop
    private Drawable drawable;

    public Bitmap getArrowhead() {
        return arrowhead;
    }

    private Bitmap arrowhead; //rotated


    public int getStartblockNum() {
        return startblockNum;
    }

    public void setStartblockNum(int startblockNum) {
        this.startblockNum = startblockNum;
    }

    public int getEndblockNum() {
        return endblockNum;
    }

    public void setEndblockNum(int endblockNum) {
        this.endblockNum = endblockNum;
    }

    public RoboBlock getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(RoboBlock startBlock) {
        this.startBlock = startBlock;
    }

    public RoboBlock getEndBlock() {
        return endBlock;
    }

    public void setEndBlock(RoboBlock endBlock) {
        this.endBlock = endBlock;
    }

    public RoboArrow(Context context,int start, int end, int type) {
        startblockNum = start;
        endblockNum = end;
        arrowtype = type;
        appcontext = context;
        drawable =  ContextCompat.getDrawable(context, R.drawable.arrowhead);
        drawable.mutate();


    }

    public Path getArrowPath() {
        return arrowPath;
    }

    public Paint getArrowPaint() {
        return arrowPaint;
    }


    public int getArrowtype() {
        return arrowtype;
    }

    public void calculate() {


        if (arrowtype == 1) {  //Straight line connector
            arrowColor = ContextCompat.getColor(appcontext, R.color.black);
            //Define Arrow coordinates
            arrowStartx = startBlock.getBlockView().getRight();
            arrowStarty = (startBlock.getBlockView().getTop() + startBlock.getBlockView().getBottom()) / 2;

            arrowEndx = endBlock.getBlockView().getLeft();
            arrowEndy = (endBlock.getBlockView().getTop() + endBlock.getBlockView().getBottom()) / 2;

            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.lineTo(arrowEndx, arrowEndy);




            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(arrowColor);
            arrowPaint.setStrokeWidth(12);

           //Define Arrow head coordinates and look
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setTint(arrowColor);
            }


            Bitmap bMap = Bitmap.createScaledBitmap(drawableToBitmap(drawable), 50, 50, false);
            Matrix mat = new Matrix();
            mat.postRotate(90);
            this.arrowhead = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
            this.arrowHeadx = arrowEndx - 45;
            this.arrowHeady = arrowEndy - 25;


        } else if (arrowtype == 2) {   //Positive loopback arrow
            arrowColor = ContextCompat.getColor(appcontext, R.color.greenBlock);
            //Define Arrow coordinates
            arrowStartx = startBlock.getBlockView().getRight();
            arrowStarty = startBlock.getBlockView().getTop() + 35;

            arrowMidx = (startBlock.getBlockView().getRight() + endBlock.getBlockView().getLeft()) / 2;
            arrowMidy = endBlock.getBlockView().getTop() / 1.5f;

            arrowEndx = (endBlock.getBlockView().getLeft() + endBlock.getBlockView().getRight()) / 2;
            arrowEndy = endBlock.getBlockView().getTop();



            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.cubicTo(arrowStartx + 65, arrowStarty, arrowMidx, arrowMidy, arrowEndx, arrowEndy);


            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(arrowColor);
            arrowPaint.setStrokeWidth(12);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setTint(arrowColor);
            }


            Bitmap bMap = Bitmap.createScaledBitmap(drawableToBitmap(drawable), 50, 50, false);
            Matrix mat = new Matrix();
            mat.postRotate(120);
            this.arrowhead = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
            this.arrowHeadx = arrowEndx - 50;
            this.arrowHeady = arrowEndy - 48;
        } else if (arrowtype == 3) {  //Negative loopback arrow

            arrowColor = ContextCompat.getColor(appcontext, R.color.redBlock);

            arrowStartx = startBlock.getBlockView().getRight();
            arrowStarty = startBlock.getBlockView().getBottom() - 35;

            arrowEndx = (endBlock.getBlockView().getLeft() + endBlock.getBlockView().getRight()) / 2;
            arrowEndy = endBlock.getBlockView().getBottom();

            arrowMidx = (startBlock.getBlockView().getRight() + endBlock.getBlockView().getLeft()) / 2;
            arrowMidy = endBlock.getBlockView().getBottom() * 1.5f;

            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.cubicTo(arrowStartx + 120, arrowStarty + 80, arrowMidx, arrowMidy, arrowEndx, arrowEndy);


            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(arrowColor);
            arrowPaint.setStrokeWidth(12);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                drawable.setTint(arrowColor);

            }


            Bitmap bMap = Bitmap.createScaledBitmap(drawableToBitmap(drawable), 50, 50, false);
            Matrix mat = new Matrix();
            mat.postRotate(320);
            this.arrowhead = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
            this.arrowHeadx = arrowEndx -24  ;
            this.arrowHeady = arrowEndy  - 20;


        } else if (arrowtype == 4) { // Long arrow type for LOOPBACK arrow
            arrowColor = ContextCompat.getColor(appcontext, R.color.redBlock);
            arrowStarty = startBlock.getBlockView().getTop();
            arrowStartx = (startBlock.getBlockView().getLeft() + startBlock.getBlockView().getRight()) / 2;

            arrowEndy = endBlock.getBlockView().getTop();
            arrowEndx = (endBlock.getBlockView().getLeft() + endBlock.getBlockView().getRight()) / 2;

            arrowMidy = endBlock.getBlockView().getTop() / 16;
            arrowMidx = (startBlock.getBlockView().getRight() + endBlock.getBlockView().getLeft()) / 2;


            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.quadTo(arrowMidx, arrowMidy, arrowEndx, arrowEndy);


            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(arrowColor);
            arrowPaint.setStrokeWidth(12);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setTint(arrowColor);
            }


            Bitmap bMap = Bitmap.createScaledBitmap(drawableToBitmap(drawable), 50, 50, false);
            Matrix mat = new Matrix();
            mat.postRotate(240);
            this.arrowhead = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
            this.arrowHeadx = arrowEndx - 34  ;
            this.arrowHeady = arrowEndy  - 40;

        }


    }

    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}