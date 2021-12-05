package smu_it.formyday;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TimeTableAdapter extends BaseAdapter {
    Context context;
    //ArrayList<TextView> blocks = new ArrayList<>();
    ArrayList<Boolean> touched = new ArrayList<Boolean>();

    TimeTableAdapter(Context c) {
        context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView block = new TextView(context);
        block.setLayoutParams(new ViewGroup.LayoutParams(56, 56));
        block.setBackgroundColor(Color.WHITE);
        touched.add(false);


        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Thouched", Toast.LENGTH_SHORT).show();
                if (touched.get(position) == false) {
                    block.setBackgroundColor(Color.rgb(255, 255, 213));
                    touched.set(position, true);
                } else {
                    block.setBackgroundColor(Color.WHITE);
                    touched.set(position, false);
                }
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
