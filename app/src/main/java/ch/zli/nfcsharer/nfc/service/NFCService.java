package ch.zli.nfcsharer.nfc.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import ch.zli.nfcsharer.service.StorageService;

public class NFCService extends Service {
    private NFCService.LocalBinder binder = new NFCService.LocalBinder();


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public NFCService getService() {
            return NFCService.this;
        }
    }

    public void createUriTag(String uri){

    }

}