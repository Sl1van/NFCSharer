package ch.zli.nfcsharer.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
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
        int index = items.indexOf(originalItem);
        items.set(index, item);
        setAll(items);
    }

    public void removeItem(NFCShareItem item){
        List<NFCShareItem> items = getAll();
        items.remove(item);
        setAll(items);
    }

    public List<NFCShareItem> getAll(){
        Gson gson = new Gson();
        List<NFCShareItem> list = gson.fromJson(sharedPreferences.getString("items", ""), new TypeToken<List<NFCShareItem>>(){}.getType());
        return list != null ? list : new ArrayList<>();
    }

    private void setAll(List<NFCShareItem> items){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("items", json);
        editor.apply();
    }

    public void clearStorage(){
        setAll(new ArrayList<>());
    }

}