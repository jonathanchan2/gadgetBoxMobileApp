package com.roboticslearningtool.Fragments;

import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.roboticslearningtool.Classes.RoboArrow;
import com.roboticslearningtool.Classes.RoboBlock;
import com.roboticslearningtool.R;
import com.roboticslearningtool.Views.BlockView;
import com.roboticslearningtool.Views.DrawingView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class BlockViewFrg extends Fragment{

    Button selectFile, sendFile;

    public BlockViewFrg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.block_view_frag, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View blockview = view.findViewById(R.id.block_view_layout);
        final RelativeLayout rl = (RelativeLayout) blockview;

        String roboCode = "SS;CI(=1)Y;CS0301(=Y);CK(=4);BO(=2=100);BL(=2=100);CQ(=2);CT(=2);CG(=2);CL(=4);AC01;$";
        final List<RoboBlock> blockList = new ArrayList<>();
        final List<RoboArrow> arrowList = new ArrayList<>();
        String[] data = roboCode.split(Pattern.quote(";"));
        int lastID = 0;
        // building up array of blocks
        for (int i = 0; i < data.length-1; i++) {
            if (!data[i].substring(0,2).equals("AC")){
                blockList.add(new RoboBlock(data[i], getActivity().getApplicationContext()));
            }

        }

        //building up array of arrows

        for (int i = 0; i < data.length-1; i++) {
            if (data[i].substring(0,2).equals("AC")){
                 int end = Integer.parseInt(data[i].substring(2,data[i].length()));
                arrowList.add(new RoboArrow(i-1,end));
            }
            else if (data[i].substring(0,2).equals("CS")){
                //Positive path
                int end = end = Integer.parseInt(data[i].substring(2,4));

                arrowList.add(new RoboArrow(i,end));

                //Negative path
                 end = Integer.parseInt(data[i].substring(4,6));
                arrowList.add(new RoboArrow(i,end));
            }
            else{


            }

        }

        //Displaying blocks from blocklist
        for (int i = 0; i < blockList.size(); i++) {
            RoboBlock nextBlock = blockList.get(i);


            BlockView nextBlockView = nextBlock.blockView;

            RelativeLayout.LayoutParams blockViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            blockViewParams.addRule(RelativeLayout.END_OF, lastID);
            blockViewParams.setMarginEnd(40);
            nextBlockView.setLayoutParams(blockViewParams);
            if(nextBlock.hasvalues) {
                nextBlock.setBlockValues();
            }
            rl.addView(nextBlockView);

            lastID = nextBlockView.getId();



//            if(i != blockList.size()-1) {
//                ImageView arrowImage = new ImageView(getActivity().getApplicationContext());
//                int arrowID = View.generateViewId();
//                arrowImage.setId(arrowID);
//                arrowImage.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.arrowvector));
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 50);
//                lp.addRule(RelativeLayout.END_OF, lastID);
//                arrowImage.setLayoutParams(lp);
//                rl.addView(arrowImage);
//                lastID = arrowID;
//            }

        }

//Calculate Arrows coordinates and Display Arrows from arrowlist
        blockview.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        blockview.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                        for (int i = 0; i < arrowList.size(); i++) {
                            RoboArrow nextarrow = arrowList.get(i);
                            nextarrow.setStartBlock(blockList.get(nextarrow.getStartblockNum()));
                            nextarrow.setEndBlock(blockList.get(nextarrow.getEndblockNum()));
                            nextarrow.calculate();

                        }

                        DrawingView drawView = (DrawingView) blockview;
                        drawView.setArrowList(arrowList);
                        drawView.setupArrows();

                    }
                }
        );





    }


}