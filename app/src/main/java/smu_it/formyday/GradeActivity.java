package smu_it.formyday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class GradeActivity extends AppCompatActivity {

    TextView nameText;
    TextView totalTime;
    TextView gradeText;
    ImageView gradeImg;
    TextView rateText;
    Integer calTime[] = {0,0,0};
    String time[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        setTitle("Grade");

        Intent intent = getIntent();
        //String nameStr = intent.getStringExtra("userName");
        SharedPreferences pref = this.getSharedPreferences("mine", MODE_PRIVATE);
        String prefData = pref.getString("Name", "none");
        String studyTime = pref.getString("recordTime","00:00:00");
        int achieveRate = pref.getInt("achieveRate", 0);
        //String studyTime = intent.getStringExtra("studyTime");

        time = studyTime.split(":");
        calTime[0] = Integer.parseInt(time[0]);
        calTime[1] = Integer.parseInt(time[1]);
        calTime[2] = Integer.parseInt(time[2]);
        calStudyTime();
        Toast.makeText(this, "time: " + calTime[0] + "h " + calTime[1] + "m " + calTime[2] + " s", Toast.LENGTH_SHORT).show();

        String grade = "씨앗";
        gradeImg = (ImageView) findViewById(R.id.iv_grade);
        if (calTime[0] < 2){
            grade = "씨앗";
            gradeImg.setImageResource(R.drawable.grade1);
        }
        else if (calTime[0] < 4){
            grade = "새싹";
            gradeImg.setImageResource(R.drawable.grade2);
        }
        else if (calTime[0] < 6){
            grade = "아기나무";
            gradeImg.setImageResource(R.drawable.grade3);
        }
        else if (calTime[0] < 8){
            grade = "어린나무";
            gradeImg.setImageResource(R.drawable.grade4);
        }
        else{
            grade = "큰나무";
            gradeImg.setImageResource(R.drawable.grade5);
        }

        nameText = (TextView)findViewById(R.id.tv_name);
        nameText.setText(prefData + "님");
        nameText.setTextColor(Color.BLACK);
        gradeText = (TextView) findViewById(R.id.tv_grade);
        //gradeText.setText("오늘의 등급은 "+grade+"입니다.");
        gradeText.setText(" "+grade+" ");
        gradeText.setTextColor(Color.parseColor("#9575CD"));
        gradeText.setTextSize(30);
        totalTime = (TextView) findViewById(R.id.tv_studyTime);
        totalTime.setText("총 공부 시간은 " + studyTime +" 이고,");
        rateText = (TextView) findViewById(R.id.tv_achieveRate);
        rateText.setText("계획 달성률은 "+achieveRate+"% 입니다.");


        this.settingSideNavBar();
    }

    void calStudyTime() {
        if (calTime[2] > 60) {
            calTime[1] += (calTime[2] / 60);
            calTime[2] %= 60;
        }
        if (calTime[1] > 60) {
            calTime[0] += (calTime[1] / 60);
            calTime[1] %= 60;
        }
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
                GradeActivity.this,
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
                    Toast.makeText(getApplicationContext(), "Daily", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GradeActivity.this, DailyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item2){
                    Toast.makeText(getApplicationContext(), "Monthly", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GradeActivity.this, MonthlyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item3){
                    Toast.makeText(getApplicationContext(), "Yearly", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GradeActivity.this, YearlyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item4){
                    Toast.makeText(getApplicationContext(), "Grade", Toast.LENGTH_SHORT).show();
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