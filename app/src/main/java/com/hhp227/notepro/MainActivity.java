package com.hhp227.notepro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ///
    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_SET = 200;
    Button addButton;
    ListView listView;
    List<String> array;
    ArrayAdapter<String> arrayAdapter;
    int pos;
    MyDB myDB;
    List<String> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = (Button) findViewById(R.id.addButton);
        listView = (ListView) findViewById(R.id.listView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                intent.putExtra("code", REQUEST_CODE_ADD);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
        /*array = new ArrayList<>();
        array.add("리스트 아이템1");
        array.add("리스트 아이템2");
        array.add("리스트 아이템3");
        array.add("리스트 아이템4");*/
        myDB = new MyDB(getBaseContext());
        notes = myDB.selectMemo();

        //arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String memo = array.get(position);
                String memo = notes.get(position);
                pos = position;
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("code", REQUEST_CODE_SET);
                intent.putExtra("memo", memo);
                startActivityForResult(intent, REQUEST_CODE_SET);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            String memo = data.getStringExtra("memo");
            Toast.makeText(getApplicationContext(), memo, Toast.LENGTH_LONG);
            //array.add(memo);
            notes.add(memo);
            myDB.insertMemo(memo);
        } else if(requestCode == REQUEST_CODE_SET && resultCode == RESULT_OK) {
            String memo = data.getStringExtra("memo");
            //array.set(pos, memo);
            notes.set(pos, memo);
        } else if(requestCode == REQUEST_CODE_SET && resultCode == RESULT_FIRST_USER) {
            //array.remove(pos);
            notes.remove(pos);
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
