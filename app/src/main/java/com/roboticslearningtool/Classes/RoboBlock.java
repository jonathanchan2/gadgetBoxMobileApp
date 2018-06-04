package com.roboticslearningtool.Classes;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

import com.roboticslearningtool.R;
import com.roboticslearningtool.Views.BlockView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class RoboBlock {


    public BlockView blockView;
    private int blockID,val1,val2 ;
    private String blocktype;
    private boolean y;
    private boolean dblblock ;
    public boolean hasvalues = false;

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    int top,bottom,mid;



    public RoboBlock(String roboCode , Context mContext ) {



        Set<String> purpleblocks = new HashSet<>(Arrays.asList("BA","BB","BC","BD","BE","BF","BG","BH","CB","CC","BI","CD","CE","CF","AA"));
        Set<String> redblocks = new HashSet<>(Arrays.asList("CG","CT","CJ","CL","CH"));
        Set<String> blueblocks = new HashSet<>(Arrays.asList("CI","BO","BL"));
        Set<String> yellowblocks = new HashSet<>(Arrays.asList("CS","CQ","BN"));
        Set<String> greenblocks = new HashSet<>(Arrays.asList("CK","CO","CU","CA","BJ","BK"));


        if (roboCode.length()<= 2){
            blocktype = roboCode;
        }
        else{
            blocktype = roboCode.substring(0,2);
        }



        // HERE STORES THE VALUE OF THE BLOCKS AND THE BLOCK TYPE(One or two block)
        //IT ALSO WRITES THE DATA INSDE THE BLOCKS!
        if(roboCode.contains("(") && !(roboCode.substring(0,2).equalsIgnoreCase("CS")) &&!(roboCode.substring(0,2).equalsIgnoreCase("BN"))) {
            hasvalues = true;
            String values = roboCode.substring(roboCode.indexOf("(") + 1, roboCode.indexOf(")"));
            String[] data = values.split(Pattern.quote("="));
            if (data.length > 2) {
                val1 = Integer.parseInt(data[1]);
                val2 = Integer.parseInt(data[2]);
                dblblock = true;
                blockView = new BlockView(mContext,2);

            } else {
                val1 = Integer.parseInt(data[1]);
                dblblock = false;
                blockView = new BlockView(mContext,1);
                blockView.getPortField().setText(data[1]);
            }
            if (!roboCode.endsWith(")") && roboCode.contains(")")) {
              y=true;
            }
        }

        else if(blocktype.contains("SS")){
            blockView = new BlockView(mContext,3);

        }

        else if(blocktype.contains("XX")){
            blockView = new BlockView(mContext,4);

        }

        else{
            blockView = new BlockView(mContext,5);

        }

        //HERE DEFINES THE BLOCKS COLOUR
        if( !(blocktype.contains("SS") || blocktype.contains("XX") || blocktype.contains("CS") )   ) {

            if (blueblocks.contains(blocktype)) {
                int blue = ContextCompat.getColor(mContext, R.color.blueBlock);
                GradientDrawable background = (GradientDrawable) blockView.getBackground();
                background = (GradientDrawable) background.mutate();
                background.setColor(blue);
                background.setStroke(1, blue);

            } else if (yellowblocks.contains(blocktype)) {
                int yellow = ContextCompat.getColor(mContext, R.color.yellowBlock);
                GradientDrawable background = (GradientDrawable) blockView.getBackground();
                background = (GradientDrawable) background.mutate();
                background.setColor(yellow);
                background.setStroke(1, yellow);

            } else if (redblocks.contains(blocktype)) {
                int red = ContextCompat.getColor(mContext, R.color.redBlock);
                GradientDrawable background = (GradientDrawable) blockView.getBackground();
                background = (GradientDrawable) background.mutate();
                background.setColor(red);
                background.setStroke(1, red);

            } else if (greenblocks.contains(blocktype)) {
                int green = ContextCompat.getColor(mContext, R.color.greenBlock);
                GradientDrawable background = (GradientDrawable) blockView.getBackground();
                background = (GradientDrawable) background.mutate();
                background.setColor(green);
                background.setStroke(1, green);

            } else {
                int purple = ContextCompat.getColor(mContext, R.color.purpleBlock);
                GradientDrawable background = (GradientDrawable) blockView.getBackground();
                background = (GradientDrawable) background.mutate();
                background.setColor(purple);
                background.setStroke(1, purple);

            }

        }
        //HERE DEFINES THE BLOCK ICON


        switch(blocktype){
            case("CI"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.buttonpushed));
                break;
            case("CK"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.lighton));
                break;
            case("BO"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.motorstart));
                break;
            case("BL"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.motorstart));
                break;
            case("CQ"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.stopwatch));
                break;
            case("CT"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.motorstop));
                break;
            case("CG"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.motorstop));
                break;
            case("CL"):
                blockView.getIcon().setImageDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.lightoff));
                break;
            default:

        }

    }



    public int getBlockID() {
        return blockID;
    }


    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public int getVal1() {
        return val1;
    }

    public void setVal1(int val1) {
        this.val1 = val1;
    }

    public int getVal2() {
        return val2;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }

    public boolean isY() {
        return y;
    }

    public void setY(boolean y) {
        this.y = y;
    }

    public BlockView getBlockView() {
        return blockView;
    }

    public void setBlockValues() {
        blockView.getPortField().setText(String.valueOf(val1));

        if(dblblock ) {
            blockView.getValueField().setText(String.valueOf(val2));
        }
    }
}