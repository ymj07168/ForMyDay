package smu_it.formyday;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    ListView monthlyCheckList;
    EditText addEditText;
    Button addListButton;
    CheckListAdapter adapter;
    ArrayList<String> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        addEditText = (EditText) view.findViewById(R.id.et_listAdd);

        adapter = new CheckListAdapter();
        monthlyCheckList = (ListView) view.findViewById(R.id.lv_monthlyCheck);
        monthlyCheckList.setAdapter(adapter);

        arrayList = new ArrayList<String>();

        addListButton = (Button) view.findViewById(R.id.btn_listAdd);
        addListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(addEditText.getText().toString());
                adapter.notifyDataSetChanged();
                addEditText.setText("");
            }
        });

        registerForContextMenu(monthlyCheckList);
        // Inflate the layout for this fragment
        return view;
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
                adapter.deleteItem(index);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}