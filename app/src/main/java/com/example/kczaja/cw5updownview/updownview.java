package com.example.kczaja.cw5updownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by kczaja on 05.04.2017.
 */

public class updownview extends LinearLayout{
    private ImageButton mUpButton;
    private EditText mEditText;
    private ImageButton mDownButton;
    private int mButtonOrientation;
    private float value;
    private float maxValue;
    private float minValue;

    public updownview(Context context) {
        super(context);

    }

    public updownview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,-1);
    }

    public updownview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public updownview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(mButtonOrientation == 0)
        inflater.inflate(R.layout.updownview, this, true);
        if(mButtonOrientation == 1)
            inflater.inflate(R.layout.updownview, this, true);

        final TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.UpDownView, defStyle, 0);
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);

        try {
            mUpButton = (ImageButton) linearLayout.getChildAt(0);
            mEditText = (EditText) linearLayout.getChildAt(1);
            mDownButton = (ImageButton) linearLayout.getChildAt(2);
            mButtonOrientation = a.getInteger(
                    R.styleable.UpDownView_btnOrientation, 0);

            final float step = (float)R.styleable.UpDownView_step;
             value = Float.parseFloat(mEditText.getText().toString());
            maxValue =(float)R.styleable.UpDownView_max;// Todo max pobrać z attrs a nie tak jak teraz
            minValue =(float)R.styleable.UpDownView_min;// Todo max pobrać z attrs a nie tak jak teraz
            if (a.hasValue(R.styleable.UpDownView_btnDown)) {
                mDownButton.setImageDrawable(
                        a.getDrawable(R.styleable.UpDownView_btnDown));
            }

            value = Float.parseFloat(mEditText.getText().toString());


            mUpButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(value+step <= maxValue) // Todo max pobrać z attrs a nie tak jak teraz
                    {
                        value+=step;
                        updateTextEdit();
                        refreshDrawableState();
                    }
                }
            });

            mDownButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(value-step >= maxValue)
                    {
                        value-=step;
                        updateTextEdit();
                        refreshDrawableState();
                    }

                }
            });

            mUpButton.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });

            mDownButton.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });

        }
        finally {
            a.recycle();
        }
    }

    private void updateTextEdit(){
        mEditText.setText(String.valueOf(value));
    }
}
