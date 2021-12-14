package ch.zli.nfcsharer.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.List;

import ch.zli.nfcsharer.domain.NFCShareItem;

public class StorageService extends Service {
    private LocalBinder binder = new LocalBinder();

    private SharedPreferences sharedPreferences;

    public StorageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return binder;
    }

    public class LocalBinder extends Binder {
        public StorageService getService() {
            return StorageService.this;
        }
    }

    public void addItem(NFCShareItem item){
        List<NFCShareItem> items = getAll();
        items.add(item);
        setAll(items);
    }

    public void editItem(NFCShareItem originalItem, NFCShareItem item){
        List<NFCShareItem> items = getAll();
        items.forEach(item1 ->{
            if(item1.equals(originalItem)){
                item1 = item;
            }
        });
        setAll(items);
    }

    public void removeItem(NFCShareItem item){
        List<NFCShareItem> items = getAll();
        items.remove(item);
        setAll(items);
    }

    public List<NFCShareItem> getAll(){
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString("items", ""), List.class);
    }

    private void setAll(List<NFCShareItem> items){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("items", json);
        editor.apply();
    }
}