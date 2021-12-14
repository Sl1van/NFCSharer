package ch.zli.nfcsharer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import ch.zli.nfcsharer.R;
import ch.zli.nfcsharer.domain.NFCShareItem;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ch.zli.nfcsharer.activities.NFCShareItemAdapter NFCShareItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        ArrayList<NFCShareItem> items = new ArrayList<>();
        items.add(new NFCShareItem("URI1","http://google.com", true));
        items.add(new NFCShareItem("URI2","http://example.org",false));
        items.add(new NFCShareItem("URI3","http://youtube.com",false));
        items.add(new NFCShareItem("URI3","http://youtube.com",false));

        NFCShareItemAdapter = new NFCShareItemAdapter(items);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(NFCShareItemAdapter);
    }
}