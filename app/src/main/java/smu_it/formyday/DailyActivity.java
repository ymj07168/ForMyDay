package smu_it.formyday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ServiceCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;

public class DailyActivity extends AppCompatActivity {

    TextView dateText;
    ListView checkList;
    GridView gridViewTime;
    TimeTableAdapter timeTableAdapter;
    CheckListAdapter adapter;
    ArrayList<String> listItem;
    EditText addText;
    Button addButton;
    TextView stopWatch;
    Button btnStart, btnRecord;
    String recordTime;
    String nameStr;
    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;

    int cur_Status = Init;
    long myBaseTime, myPauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        setTitle("Daily");

        Intent intent2 = getIntent();
        nameStr = intent2.getStringExtra("userName");

        // 날짜
        dateText = findViewById(R.id.tv_date);
        dateText.setText(getTime());

        // 체크리스트
        listItem = new ArrayList<String>();
        adapter = new CheckListAdapter();
        checkList = (ListView) findViewById(R.id.lv_check);
        checkList.setAdapter(adapter);

        // 일정 항목 추가, 삭제
        addText = (EditText) findViewById(R.id.et_add);
        addButton = (Button) findViewById(R.id.bt_add);
        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItem(addText.getText().toString());
                adapter.notifyDataSetChanged();
                addText.setText("");
            }
        });
        registerForContextMenu(checkList);

        stopWatch = (TextView) findViewById(R.id.tv_stopWatch);
        btnStart = (Button) findViewById(R.id.bt_start);
        btnRecord = (Button) findViewById(R.id.bt_record);

        gridViewTime = (GridView) findViewById(R.id.gv_TimeTable);
        timeTableAdapter = new TimeTableAdapter(this);
        gridViewTime.setAdapter(timeTableAdapter);

        this.settingSideNavBar();
    }

    // 현재 날짜 가져오는 함수
    private String getTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일");
        String getTime = dateFormat.format(date);

        return getTime;
    }

    // 스톱워치 기능 구현 함수
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void myOnClick(View view){
        switch(view.getId()){
            case R.id.bt_start:
                switch(cur_Status){
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        myTimer.sendEmptyMessage(0);
                        btnStart.setText("STOP");
                        btnRecord.setEnabled(true);
                        cur_Status = Run;
                        break;
                    case Run:
                        myTimer.removeMessages(0);
                        myPauseTime = SystemClock.elapsedRealtime();
                        btnStart.setText("START");
                        btnRecord.setText("RECORD");
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now-myPauseTime);
                        btnStart.setText("STOP");
                        btnRecord.setText("RECORD");
                        cur_Status = Run;
                        break;
                }
                break;
            case R.id.bt_record:
                recordTime = stopWatch.getText().toString();
                SharedPreferences pref = getSharedPreferences("mine", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("recordTime", recordTime);
                editor.commit();
                break;
        }
    }

    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            stopWatch.setText(getTimeOut());
            myTimer.sendEmptyMessage(0);
        }
    };

    String getTimeOut() {
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d",
                (outTime / 1000) / 60 / 60, (outTime / 1000) / 60, (outTime / 1000) % 60);
        return easy_outTime;
    }

    // checkList 항목 컨텍스트 메뉴
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"삭제");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;
        switch(item.getItemId()){
            case 0:
                Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show();
                adapter.deleteItem(index);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    // 체크 달성률 계산하는 함수
    float getCheckRate() {
        float totalNum = adapter.getCount();
        Toast.makeText(getApplicationContext(), "total"+totalNum, Toast.LENGTH_SHORT).show();
        if (totalNum == 0) return 0;
        SparseBooleanArray checkedItems = checkList.getCheckedItemPositions();
        float checkNum = 0;
        for (int i = 0; i < totalNum; i++){
            if (checkedItems.get(i)){
                checkNum++;
            }
        }
        float achieveRate = checkNum / totalNum * 100;
        return achieveRate;
    }

    public void settingSideNavBar()
    {
        // 툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 사이드 메뉴를 오픈하기위한 아이콘 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        // 사이드 네브바 구현
        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                DailyActivity.this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed
        );

        // 사이드 네브바 클릭 리스너
        drawLayout.addDrawerListener(actionBarDrawerToggle);

        // -> 사이드 네브바 아이템 클릭 이벤트 설정
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.menu_item1){
                }else if(id == R.id.menu_item2){
                    Intent intent = new Intent(DailyActivity.this, MonthlyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item3){
                    Intent intent = new Intent(DailyActivity.this, YearlyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item4){
                    SharedPreferences pref = getSharedPreferences("mine", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("achieveRate", (int)getCheckRate());
                    editor.commit();
                    Intent intent = new Intent(DailyActivity.this, GradeActivity.class);
                    startActivity(intent);
                }


                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    /***
     *  -> 뒤로가기시, 사이드 네브바 닫는 기능
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}