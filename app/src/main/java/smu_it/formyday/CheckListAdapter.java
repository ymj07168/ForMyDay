package smu_it.formyday;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckListAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

    public CheckListAdapter(){
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.tv_task);
        ListViewItem listViewItem = listViewItems.get(position);
        textView.setText(listViewItem.getText());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    public void addItem(String text){
        ListViewItem item = new ListViewItem();
        item.setText(text);
        listViewItems.add(item);
    }

    public void deleteItem(int position){
        listViewItems.remove(position);
    }
}
