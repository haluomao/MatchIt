package com.atongmu.matchit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.atongmu.matchit.activity.GameActivity;
import com.atongmu.matchit.android.DrawSurfaceView;
import com.atongmu.matchit.android.DrawView;
import com.atongmu.matchit.android.MapView;
import com.atongmu.matchit.entity.Account;
import com.atongmu.matchit.entity.UserMission;
import com.atongmu.matchit.util.Dao;
import com.atongmu.matchit.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> userList;
    private ArrayAdapter adapter;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //read user profile here
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.profile_list);
        init();
    }

    private void init(){
        Button btnBegin = (Button) findViewById(R.id.btn_begin);
        btnBegin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(userList==null || userList.size()==0)
                    return;
                saveProfile();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("user", userList.get(0));
                startActivity(intent);
            }
        });
        userList = getData();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                userList
                );

        Button btnNew = (Button) findViewById(R.id.btn_new);
        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
                final EditText inputServer = new EditText(MainActivity.this);
                inputServer.setFocusable(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.new_profile)).setIcon(
                        R.drawable.go).setView(inputServer).setNegativeButton(
                        getString(R.string.cancel), null);
                builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(userList.size()<10 && !userList.contains(inputServer.getText().toString())) {
                                    userList.add(inputServer.getText().toString());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                builder.show();
            }
        });

        listView.setAdapter(adapter);

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

    private void saveProfile(){
        Dao dao = new Dao(MainActivity.this, UserMission.PROFILE);
        String str = Util.turnStr(userList);
        dao.put(UserMission.USERS,str);
    }

    private List<String> getData(){
        List<String> res = new ArrayList<>();
        Dao dao = new Dao(MainActivity.this, UserMission.PROFILE);
        String users = dao.getString(UserMission.USERS);
        if("".equals(users)){
            return res;
        }else{
            return Util.parseList(users);
        }
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
