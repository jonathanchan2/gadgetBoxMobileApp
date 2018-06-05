package com.roboticslearningtool.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class RoboArrow {
    private RoboBlock startBlock;
    private RoboBlock endBlock;
    private int startblockNum;
    private int endblockNum;
    private Path arrowPath;
    private Path arrowHeadPath;
    private Paint arrowPaint;


    private int arrowtype;// 1 for connector, 2 for top loop ,3 for bottom loop


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

    public RoboArrow(int start,int end,int type) {
        startblockNum = start;
        endblockNum = end;
        arrowtype = type;


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
        float arrowStartx;
        float arrowStarty;
        float arrowEndx;
        float arrowEndy;
        float arrowMidx;
        float arrowMidy;



        if (arrowtype == 1) {
            //Define Arrow coordinates
            arrowStartx = startBlock.getBlockView().getRight();
            arrowStarty = (startBlock.getBlockView().getTop()+startBlock.getBlockView().getBottom())/2;

            arrowEndx =  endBlock.getBlockView().getLeft();
            arrowEndy = (endBlock.getBlockView().getTop()+endBlock.getBlockView().getBottom())/2;

            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.lineTo(arrowEndx, arrowEndy);



//            //Define Arrow head coordinates
//            arrowStartx =  startBlock.getBlockView().getRight();
//            arrowStarty = startBlock.getBlockView().getTop();
//
//            arrowEndx = (endBlock.getBlockView().getLeft() + endBlock.getBlockView().getRight()) / 2;
//            arrowEndy = endBlock.getBlockView().getTop();
//
//            arrowMidx = (startBlock.getBlockView().getRight() + endBlock.getBlockView().getLeft()) / 2;
//            arrowMidy = endBlock.getBlockView().getTop() / 1.5f;
//
//            //Use Arrow head Coordinates to plan path
//            arrowPath = new Path();
//            arrowPath.moveTo(arrowStartx, arrowStarty);
//            arrowPath.quadTo(arrowMidx, arrowMidy, arrowEndx, arrowEndy);

            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(Color.BLACK);
            arrowPaint.setStrokeWidth(12);



        }
        else if(arrowtype == 2){

            //Define Arrow coordinates
            arrowStartx =  startBlock.getBlockView().getRight();
            arrowStarty = startBlock.getBlockView().getTop();

            arrowEndx = (endBlock.getBlockView().getLeft() + endBlock.getBlockView().getRight()) / 2;
            arrowEndy = endBlock.getBlockView().getTop();

            arrowMidx = (startBlock.getBlockView().getRight() + endBlock.getBlockView().getLeft()) / 2;
            arrowMidy = endBlock.getBlockView().getTop() / 1.5f;

            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.quadTo(arrowMidx, arrowMidy, arrowEndx, arrowEndy);




            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(Color.GREEN);
            arrowPaint.setStrokeWidth(12);
        }

        else if(arrowtype == 3){
            arrowStartx =  startBlock.getBlockView().getRight();
            arrowStarty = startBlock.getBlockView().getBottom();

            arrowEndx = (endBlock.getBlockView().getLeft() + endBlock.getBlockView().getRight()) / 2;
            arrowEndy = endBlock.getBlockView().getBottom();

            arrowMidx = (startBlock.getBlockView().getRight() + endBlock.getBlockView().getLeft()) / 2;
            arrowMidy = endBlock.getBlockView().getBottom() * 1.5f;

            //Use Arrow Coordinates to plan path
            arrowPath = new Path();
            arrowPath.moveTo(arrowStartx, arrowStarty);
            arrowPath.quadTo(arrowMidx, arrowMidy, arrowEndx, arrowEndy);


            //Define How arrow looks
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.STROKE);
            arrowPaint.setStrokeCap(Paint.Cap.ROUND);
            arrowPaint.setColor(Color.RED);
            arrowPaint.setStrokeWidth(12);
        }


        else if(arrowtype == 4){
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
            arrowPaint.setColor(Color.RED);
            arrowPaint.setStrokeWidth(12);

        }




    }



}