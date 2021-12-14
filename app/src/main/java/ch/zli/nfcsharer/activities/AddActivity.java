package ch.zli.nfcsharer.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import ch.zli.nfcsharer.R;
import ch.zli.nfcsharer.domain.NFCShareItem;
import ch.zli.nfcsharer.service.StorageService;

public class AddActivity extends AppCompatActivity {


    private Button addButton;
    private TextInputEditText uriInput;
    private String uri;
    private TextInputEditText nameInput;
    private String name;

    private StorageService storageService;
    private boolean storageServiceBound = false;

    private ServiceConnection storageServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            StorageService.LocalBinder binder = (StorageService.LocalBinder) service;
            storageService = binder.getService();

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
        setContentView(R.layout.activity_add);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(listener -> {
            getStorageService().addItem(new NFCShareItem(name, uri, false));
            Intent resultIntent = new Intent(this, MainActivity.class);
            startActivity(resultIntent);
        });

        uriInput = findViewById(R.id.uriInput);
        uriInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable e) {
                uri = e.toString();
            }
        });

        nameInput = findViewById(R.id.nameInput);
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable e) {
                name = e.toString();
            }
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

    public StorageService getStorageService() {
        if (storageServiceBound) {
            return storageService;
        } else {
            throw new RuntimeException("StorageService not yet bound");
        }
    }
}