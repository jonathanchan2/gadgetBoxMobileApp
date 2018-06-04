package com.roboticslearningtool.Classes;

public class RoboArrow {
    RoboBlock startBlock;
    RoboBlock endBlock;
    int startblockNum;
    int endblockNum;
    public float arrowStartx;
    public float arrowStarty;
    public float arrowEndx;
    public float arrowEndy;
    public float arrowMidx;
    public float arrowMidy;

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

    public RoboArrow(int start,int end) {
        startblockNum = start;
        endblockNum = end;


    }

    public void calculate() {
        arrowStarty = startBlock.getBlockView().getTop();
        arrowStartx = (startBlock.getBlockView().getLeft()+startBlock.getBlockView().getRight())/2;

        arrowEndy = endBlock.getBlockView().getTop();
        arrowEndx = (endBlock.getBlockView().getLeft()+endBlock.getBlockView().getRight())/2;

        arrowMidy = endBlock.getBlockView().getTop()/3;
        arrowMidx = (startBlock.getBlockView().getRight()+endBlock.getBlockView().getLeft())/2;
    }
}