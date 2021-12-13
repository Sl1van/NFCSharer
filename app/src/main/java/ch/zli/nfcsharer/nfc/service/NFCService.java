package ch.zli.nfcsharer.nfc.service;

        import android.content.Intent;
        import android.nfc.cardemulation.HostApduService;
        import android.os.Bundle;
        import android.os.IBinder;

public class NFCService extends HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        //...
        return null;
    }

    @Override
    public void onDeactivated(int reason) {
        //...
    }
}
