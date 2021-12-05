package smu_it.formyday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YearlyActivity extends AppCompatActivity {

    TextView dateText;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly);
        setTitle("Yearly");

        dateText = findViewById(R.id.tv_date);
        dateText.setText(getTime()+" 돌아보기");

        gridView = (GridView) findViewById(R.id.gv_yearly);
        YearlyAdapter yearlyAdapter = new YearlyAdapter(this);
        gridView.setAdapter(yearlyAdapter);

        this.settingSideNavBar();
    }

    // 현재 날짜 가져오는 함수
    private String getTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년");
        String getTime = dateFormat.format(date);

        return getTime;
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
                YearlyActivity.this,
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
                    Intent intent = new Intent(YearlyActivity.this, DailyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item2){
                    Toast.makeText(getApplicationContext(), "Monthly", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(YearlyActivity.this, MonthlyActivity.class);
                    startActivity(intent);
                }else if(id == R.id.menu_item3){
                    Toast.makeText(getApplicationContext(), "Yearly", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.menu_item4){
                    Toast.makeText(getApplicationContext(), "Grade", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(YearlyActivity.this, GradeActivity.class);
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