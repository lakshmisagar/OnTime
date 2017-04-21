package com.amko0l.ontime.ontime.ui;

/**
 * Created by amko0l on 4/18/2017.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.amko0l.ontime.ontime.R;
import com.amko0l.ontime.ontime.database.OnTimeDB;
import com.amko0l.ontime.ontime.helper.EventsListAdapter;

import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> items = null;
    Button stopButton;
    SQLiteDatabase sqLiteDatabase;
    OnTimeDB onTimeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        onTimeDB = new OnTimeDB(this, "OnTimeDB", null, 1);
        sqLiteDatabase = onTimeDB.getWritableDatabase();
        items = onTimeDB.getAllEventsTitle(sqLiteDatabase);
        if(items == null){
            items = new ArrayList<String>();
        }
        if(items.size() == 0 ){
            items.add("Add Events by pressing Start Button");
        }
        mAdapter = new EventsListAdapter(items);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        items.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        stopButton = (Button)findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewEvent();
            }
        });

        onTimeDB = new OnTimeDB(this, "OnTimeDB", null, 1);
        sqLiteDatabase = onTimeDB.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        items = OnTimeDB.getAllEventsTitle(sqLiteDatabase);
        if(items == null){
            items = new ArrayList<String>();
        }
        if(items.size() == 0 ){
            items.add("Add Events by pressing Start Button");
        }
        mAdapter = new EventsListAdapter(items);
        mAdapter.notifyDataSetChanged();
    }

    private void createNewEvent()
    {
        Intent intent = new Intent(this,AlarmActivity.class);
        startActivity(intent);
    }

}
