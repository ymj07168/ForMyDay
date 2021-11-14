package smu_it.formyday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyActivity extends AppCompatActivity {

    TextView dateText;
    ListView checkList;
    CheckListAdapter adapter;
    ArrayList<String> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        dateText = findViewById(R.id.tv_date);
        dateText.setText(getTime());

        listItem = new ArrayList<String>();
//        listItem.add("일정 1");
//        listItem.add("일정 2");
//        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_item, listItem);
        adapter = new CheckListAdapter();
        checkList = (ListView) findViewById(R.id.lv_check);
//        checkList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        checkList.setAdapter(adapter);
        adapter.addItem("일정 1");


    }

    // 현재 날짜 가져오는 함수
    private String getTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}