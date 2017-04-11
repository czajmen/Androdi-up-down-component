package com.example.kczaja.cw5updownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import static com.example.kczaja.cw5updownview.R.attr.step;

/**
 * Created by kczaja on 05.04.2017.
 */

public class updownview extends LinearLayout {
    private ImageButton mUpButton;
    private EditText mEditText;
    private ImageButton mDownButton;
    private int mButtonOrientation;
    private float value;
    private float maxValue;
    private float minValue;
    private float step;
    private boolean mPressed;
    private boolean canceled;

    public updownview(Context context) {
        super(context);

    }

    public updownview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    public updownview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public updownview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.UpDownView, defStyle, 0);

        mButtonOrientation = a.getInteger(
                R.styleable.UpDownView_btnOrientation, 0);

       // if (mButtonOrientation == 0)
            inflater.inflate(R.layout.updownview, this, true);
//        if (mButtonOrientation == 1)
        //   inflater.inflate(R.layout.updownviewland, this, true);


        LinearLayout linearLayout = (LinearLayout) getChildAt(0);

        try {
            mUpButton = (ImageButton) linearLayout.getChildAt(0);
            mEditText = (EditText) linearLayout.getChildAt(1);
            mDownButton = (ImageButton) linearLayout.getChildAt(2);


            step = (float) a.getFloat(R.styleable.UpDownView_step, 0);
            value = Float.parseFloat(mEditText.getText().toString());

            maxValue = (float) a.getFloat(R.styleable.UpDownView_max, 0);
            minValue = (float) a.getFloat(R.styleable.UpDownView_min, 0);


            if (a.hasValue(R.styleable.UpDownView_btnDown)) {
                mDownButton.setImageDrawable(
                        a.getDrawable(R.styleable.UpDownView_btnDown));
            }

            value = Float.parseFloat(mEditText.getText().toString());


            mUpButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (value + step <= maxValue) {
                        value += step;
                        updateTextEdit();
                        refreshDrawableState();
                    }
                }
            });

            mDownButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (-1 * value - step < minValue) {
                        value -= step;
                        updateTextEdit();
                        refreshDrawableState();
                    }

                }
            });

            mUpButton.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    handler.postDelayed(mLongPressed, 100);
                    canceled = false;

                    return false;
                }
            });

            mDownButton.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    handler.postDelayed(mLongPressedminus, 100);
                    canceled = false;

                    return false;
                }
            });

            mUpButton.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if ((motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)) {
                       canceled = true;
                    }
                    return false;

                }
            });

            mDownButton.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if ((motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL)) {
                        canceled = true;
                    }
                    return false;

                }
            });

        } finally {
            a.recycle();
        }
    }

    MotionEvent myEvent;
    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            if (value + step <= maxValue  && !canceled) {
                value += step;
                updateTextEdit();
                refreshDrawableState();
                handler.postDelayed(mLongPressed, 100);
            }
        }
    };

    Runnable mLongPressedminus = new Runnable() {
        public void run() {
            if (-1 * value - step < minValue  && !canceled ) {
                value -= step;
                updateTextEdit();
                refreshDrawableState();
                handler.postDelayed(mLongPressedminus, 100);
            }
        }
    };


    private void updateTextEdit() {
        mEditText.setText(String.valueOf(value));
    }
}
