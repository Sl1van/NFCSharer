package ch.zli.nfcsharer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import ch.zli.nfcsharer.R;
import ch.zli.nfcsharer.domain.NFCShareItem;
import ch.zli.nfcsharer.service.StorageService;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    NFCShareItemAdapter nFCShareItemAdapter;

    private StorageService storageService;
    private boolean storageServiceBound = false;

    private ServiceConnection storageServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            StorageService.LocalBinder binder = (StorageService.LocalBinder) service;
            storageService = binder.getService();

            storageService.clearStorage();
            storageService.addItem(new NFCShareItem("URI1","http://google.com", true));
            storageService.addItem(new NFCShareItem("URI2","http://example.org",false));
            storageService.addItem(new NFCShareItem("URI3","http://youtube.com",false));
            storageService.addItem(new NFCShareItem("URI3","http://youtube.com",false));
            nFCShareItemAdapter = new NFCShareItemAdapter(storageService.getAll());
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


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
}