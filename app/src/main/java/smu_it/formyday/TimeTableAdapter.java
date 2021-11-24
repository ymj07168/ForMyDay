package smu_it.formyday;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimeTableAdapter extends BaseAdapter {
    Context context;
    Boolean touched = false;

    Integer[] blocks = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    TimeTableAdapter(Context c) {
        context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView block = new TextView(context);
        block.setLayoutParams(new ViewGroup.LayoutParams(60, 60));
        block.setBackgroundColor(Color.WHITE);

        block.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (touched == false) {
                    block.setBackgroundColor(Color.rgb(255, 255, 213));
                    touched = true;
                } else {
                    block.setBackgroundColor(Color.WHITE);
                    touched = false;
                }
                return false;
            }
        });
        return block;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 144;
    }
}
