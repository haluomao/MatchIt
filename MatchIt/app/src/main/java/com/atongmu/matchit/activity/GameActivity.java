package com.atongmu.matchit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.atongmu.matchit.R;
import com.atongmu.matchit.android.DrawSurfaceView;

/**
 * Created by mfg on 16/07/05.
 */
public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
    }

    private void init() {
        LinearLayout layout=(LinearLayout) findViewById(R.id.layout_game);

        final DrawSurfaceView view = new DrawSurfaceView(this);
        view.setMinimumHeight(1200);
        view.setMinimumWidth(500);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }
}
