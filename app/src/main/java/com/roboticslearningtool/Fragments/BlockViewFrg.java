package com.roboticslearningtool.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.roboticslearningtool.Classes.Fab;
import com.roboticslearningtool.Classes.FileNameEvent;
import com.roboticslearningtool.Classes.RoboArrow;
import com.roboticslearningtool.Classes.RoboBlock;
import com.roboticslearningtool.Classes.RoboCodeEvent;
import com.roboticslearningtool.R;
import com.roboticslearningtool.Views.BlockView;
import com.roboticslearningtool.Views.DrawingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class BlockViewFrg extends Fragment {

    private static final int FILE_SAVE_CODE = 22 ;
    private static final int PICKFILE_REQUEST_CODE = 55;
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

        Fab fab = (Fab) view.findViewById(R.id.fab);
        View sheetView =  view.findViewById(R.id.fab_sheet);
        View overlay =  view.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.roboAppBase);
        int fabColor = getResources().getColor(R.color.greenBlock);
        TextView save = (TextView) view.findViewById(R.id.fab_save);
        TextView load = (TextView) view.findViewById(R.id.fab_load);

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.setType("application/octet-stream");


                startActivityForResult(intent, FILE_SAVE_CODE);

            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/octet-stream");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        // Initialize material sheet FAB
        MaterialSheetFab materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String roboCommand = "";
                try {
                    roboCommand = readTextFromUri(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity().getApplicationContext(), "Command Loaded!",
                        Toast.LENGTH_LONG).show();
                EventBus.getDefault().postSticky(new RoboCodeEvent(roboCommand));
                EventBus.getDefault().postSticky(new FileNameEvent(getFileName(uri)));
            }
        }

        else if (requestCode == FILE_SAVE_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            uri = resultData.getData();
            generateFile("Test","SS;CI(=1)Y;CS0301(=Y);CK(=4);BO(=2=75);BL(=2=75);CQ(=2);CT(=2);CG(=2);CL(=4);AC01;$");


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onRoboCodeEvent(RoboCodeEvent event) {


        final DrawingView drawingView = (DrawingView) getView().findViewById(R.id.block_view_layout);
//        drawingView.removeAllViews();
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
    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        return stringBuilder.toString();
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void generateFile( String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName+".gbx");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

