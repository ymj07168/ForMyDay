package smu_it.formyday;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class YearlyAdapter extends BaseAdapter {
    private Context m_context;
    View memoView;
    TextView memoItem;
    EditText yearlyItem;

    //private ArrayList<GridViewItem> gridViewItems = new ArrayList<GridViewItem>();
    private ArrayList<String> text = new ArrayList<String>(){{
        add("1"); add("2"); add("3"); add(""); add(""); add("");
        add(""); add(""); add(""); add(""); add(""); add("");
    }};
    public YearlyAdapter(Context c) {
        m_context = c;
    }

    @Override
    public int getCount() {
        return text.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        memoItem = new TextView(m_context);
        memoItem.setLayoutParams(new ViewGroup.LayoutParams(400,600));
        memoItem.setBackgroundColor(Color.WHITE);
        memoItem.setTextColor(Color.BLACK);
        memoItem.setText(text.get(position));
        final int pos = position;
        memoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memoView = (View) View.inflate(
                       m_context, R.layout.dialog, null);

                AlertDialog.Builder dig = new AlertDialog.Builder(m_context);
                yearlyItem = (EditText) memoView.findViewById(R.id.et_yearlyItem);
                yearlyItem.setText(text.get(position));
                dig.setTitle("메모장");
                dig.setIcon(R.drawable.memo);
                dig.setView(memoView);
                dig.setNegativeButton("닫기", null);
                dig.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text.set(position,yearlyItem.getText().toString());
                        notifyDataSetChanged();
                        //memoItem.setText(text.get(position));
                    }
                });
                dig.show();
            }
        });
        return memoItem;
    }
}
