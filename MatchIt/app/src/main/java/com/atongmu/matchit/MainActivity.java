package com.atongmu.matchit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.atongmu.matchit.activity.GameActivity;
import com.atongmu.matchit.android.DrawSurfaceView;
import com.atongmu.matchit.android.DrawView;
import com.atongmu.matchit.android.MapView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //read user profile here
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        Button btnBegin = (Button) findViewById(R.id.btn_begin);
        btnBegin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
//        LinearLayout layout=(LinearLayout) findViewById(R.id.root);
//        //final DrawView view=new DrawView(this);
////        final DrawView view=new DrawView(this);
//        final DrawSurfaceView view = new DrawSurfaceView(this);
//        view.setMinimumHeight(1200);
//        view.setMinimumWidth(500);
//        view.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        //通知view组件重绘
//        view.invalidate();
//        layout.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
