package smu_it.formyday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlyActivity extends AppCompatActivity {

    CalendarView monthlyCalendar;
    private MyFragmentAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);
        setTitle("Monthly");

        monthlyCalendar = (CalendarView) findViewById(R.id.calendarView);
        viewPager2 = findViewById(R.id.vpContainer);
        tabLayout = findViewById(R.id.tablayout);
        adapter = new MyFragmentAdapter(this);

        setMonthlyCalendar();

        viewPager2.setAdapter(adapter);
        adapter.addFragment(new Fragment1());
        adapter.addFragment(new Fragment2());

        String[] tabTitles = {"월별 통계", "월별 목표"};
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView((MonthlyActivity.this));
                textView.setText(tabTitles[position]);
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);
                tab.setCustomView(textView);
            }
        }).attach();

        this.settingSideNavBar();
    }

    private void setMonthlyCalendar() {

        monthlyCalendar.setCalendarDayLayout(R.layout.day_layout);

        Calendar calendarMin = Calendar.getInstance();
        Calendar calendarMax = Calendar.getInstance();
        calendarMin.add(Calendar.DAY_OF_MONTH, -360);
        calendarMax.add(Calendar.DAY_OF_MONTH, +360);

        monthlyCalendar.setMinimumDate(calendarMin);
        monthlyCalendar.setMaximumDate(calendarMax);

        List<EventDay> events = new ArrayList<>();

        monthlyCalendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                View dialogView = (View) View.inflate(context, R.layout.monthly_clicked_dialog, null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("메모");
                dialog.setView(dialogView);
                dialog.setNegativeButton("닫기", null);
                Calendar clickedDayCalendar = eventDay.getCalendar();
                dialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView dialogMemo = (TextView) dialogView.findViewById(R.id.tv_dialogMemo);
                        String memo = dialogMemo.getText().toString();
                        //events.add(new EventDay(clickedDayCalendar, R.drawable.myinfo));
                        events.add(new EventDay(clickedDayCalendar, getCircleDrawableWithText(context, memo)));
                        monthlyCalendar.setEvents(events);
                    }
                });
                dialog.show();

//                Calendar clickedDayCalendar = eventDay.getCalendar();
//                Button dialogButton = (Button) dialogView.findViewById(R.id.btn_dialogSave);
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TextView dialogMemo = (TextView) dialogView.findViewById(R.id.tv_dialogMemo);
//                        String memo = dialogMemo.getText().toString();
//                        //events.add(new EventDay(clickedDayCalendar, R.drawable.myinfo));
//                        events.add(new EventDay(clickedDayCalendar, getCircleDrawableWithText(context, memo)));
//                        monthlyCalendar.setEvents(events);
//                    }
//                });
            }
        });
    }

    private Drawable getCircleDrawableWithText(Context context, String string) {
        // Drawable background = ContextCompat.getDrawable(context, R.drawable.circle_background);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.black, 8);
        //text.setBounds(0, 0, 50, 50);
        Drawable[] layers = {text};
        return new LayerDrawable(layers);
    }

    public void settingSideNavBar() {
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
                MonthlyActivity.this,
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

                if (id == R.id.menu_item1) {
                    Intent intent = new Intent(MonthlyActivity.this, DailyActivity.class);
                    startActivity(intent);
                } else if (id == R.id.menu_item2) {
                    Toast.makeText(getApplicationContext(), "Monthly", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_item3) {
                    Intent intent = new Intent(MonthlyActivity.this, YearlyActivity.class);
                    startActivity(intent);
                } else if (id == R.id.menu_item4) {
                    Intent intent = new Intent(MonthlyActivity.this, GradeActivity.class);
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