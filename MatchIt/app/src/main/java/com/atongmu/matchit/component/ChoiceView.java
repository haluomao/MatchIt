package com.atongmu.matchit.component;


import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.atongmu.matchit.R;

/**
 * Created by mfg on 16/07/30.
 */
public class ChoiceView extends FrameLayout implements Checkable{

    private TextView mTextView;
    private RadioButton mRadioButton;

    public ChoiceView(Context context) {
        super(context);
        View.inflate(context, R.layout.listview_layout, this);
        mTextView = (TextView) findViewById(R.id.lvText);
        mRadioButton = (RadioButton) findViewById(R.id.checkedView);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mRadioButton.isChecked();
    }

    @Override
    public void toggle() {
        mRadioButton.toggle();
    }
}
