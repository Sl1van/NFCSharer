package ch.zli.nfcsharer.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.zli.nfcsharer.R;
import ch.zli.nfcsharer.domain.NFCShareItem;
import ch.zli.nfcsharer.service.StorageService;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;

    private NFCShareItemAdapter nFCShareItemAdapter;

    private StorageService storageService;
    private boolean storageServiceBound = false;

    private ServiceConnection storageServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            StorageService.LocalBinder binder = (StorageService.LocalBinder) service;
            storageService = binder.getService();

// uncomment this code to generate test data
//            storageService.clearStorage();
//            storageService.addItem(new NFCShareItem("Google","http://google.com", true));
//            storageService.addItem(new NFCShareItem("Tel","tel:+358-9-123-45678",false));
//            storageService.addItem(new NFCShareItem("Youtube","http://youtube.com",false));
//            storageService.addItem(new NFCShareItem("Netflix","http://youtube.com",false));
//            storageService.addItem(new NFCShareItem("Email","mailto:zac@gmail.com",false));
            nFCShareItemAdapter = new NFCShareItemAdapter(storageService.getAll(), MainActivity.this);
            if (recyclerView != null) {
                recyclerView.setAdapter(nFCShareItemAdapter);
            }
            storageServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            storageServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        addButton = findViewById(R.id.createButton);
        addButton.setOnClickListener(listener -> {
            addItem();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, StorageService.class);
        bindService(intent, storageServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(storageServiceConnection);
        storageServiceBound = false;
    }

    public void reloadRecyclerViewAdapter() {
        if (storageServiceBound && recyclerView != null) {
            nFCShareItemAdapter = new NFCShareItemAdapter(storageService.getAll(), this);
            recyclerView.setAdapter(nFCShareItemAdapter);
        }
    }

    public StorageService getStorageService() {
        if (storageServiceBound) {
            return storageService;
        } else {
            throw new RuntimeException("StorageService not yet bound");
        }
    }

    public void editItem(NFCShareItem item) {
        Intent resultIntent = new Intent(this, EditActivity.class);
        resultIntent.putExtra("item", item);
        startActivity(resultIntent);
    }

    public void addItem() {
        Intent resultIntent = new Intent(this, AddActivity.class);
        startActivity(resultIntent);
    }
}