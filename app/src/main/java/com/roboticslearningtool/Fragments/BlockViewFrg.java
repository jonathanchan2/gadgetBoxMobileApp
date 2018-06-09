package com.roboticslearningtool.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.Toast;

import com.roboticslearningtool.Classes.RoboArrow;
import com.roboticslearningtool.Classes.RoboBlock;
import com.roboticslearningtool.Classes.RoboCodeEvent;
import com.roboticslearningtool.R;
import com.roboticslearningtool.Views.BlockView;
import com.roboticslearningtool.Views.DrawingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class BlockViewFrg extends Fragment {

    Button selectFile, sendFile;

    public BlockViewFrg() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

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



    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onRoboCodeEvent(RoboCodeEvent event) {


        final DrawingView drawingView = (DrawingView) getView().findViewById(R.id.block_view_layout);
        drawingView.removeAllViews();
        String roboCode = event.roboCode;
        final List<RoboBlock> blockList = new ArrayList<>();
        final List<RoboArrow> arrowList = new ArrayList<>();
        String[] data = roboCode.split(Pattern.quote(";"));
        int lastID = 0;
        // building up array of blocks
        for (int i = 0; i < data.length - 1; i++) {
            if (!data[i].substring(0, 2).equals("AC")) {
                blockList.add(new RoboBlock(data[i], getActivity().getApplicationContext()));
            }

        }

        //building up array of arrows

        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].substring(0, 2).equals("AC")) {
                int end = Integer.parseInt(data[i].substring(2, data[i].length()));
                arrowList.add(new RoboArrow(getActivity(), i - 1, end, 4));
            } else if (data[i].substring(0, 2).equals("CS")) {
                //Positive path
                int end = Integer.parseInt(data[i].substring(2, 4));

                arrowList.add(new RoboArrow(getActivity(), i, end, 2));

                //Negative path
                end = Integer.parseInt(data[i].substring(4, 6));
                arrowList.add(new RoboArrow(getActivity(), i, end, 3));
            } else {
                if (i < blockList.size() - 1) {
                    arrowList.add(new RoboArrow(getActivity(), i, i + 1, 1));
                }


            }

        }

        //Displaying blocks from blocklist
        for (int i = 0; i < blockList.size(); i++) {
            RoboBlock nextBlock = blockList.get(i);


            BlockView nextBlockView = nextBlock.blockView;

            RelativeLayout.LayoutParams blockViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            blockViewParams.addRule(RelativeLayout.END_OF, lastID);

            blockViewParams.setMarginEnd(150);

            nextBlockView.setLayoutParams(blockViewParams);
            if (nextBlock.hasvalues) {
                nextBlock.setBlockValues();
            }
            drawingView.addView(nextBlockView);

            lastID = nextBlockView.getId();


        }


        //Calculate Arrows coordinates and Display Arrows from arrowlist
        drawingView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onGlobalLayout() {
                        drawingView.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                        for (int i = 0; i < arrowList.size(); i++) {
                            RoboArrow nextarrow = arrowList.get(i);
                            nextarrow.setStartBlock(blockList.get(nextarrow.getStartblockNum()));
                            nextarrow.setEndBlock(blockList.get(nextarrow.getEndblockNum()));
                            nextarrow.calculate();

                        }

                        DrawingView drawView = (DrawingView) drawingView;
                        drawView.setArrowList(arrowList);


                    }
                }
        );

    }


}

