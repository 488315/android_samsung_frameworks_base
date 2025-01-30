package com.samsung.accessory.manager;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
public class DetachCheck {
    public boolean mAuthStarted;
    public final Context mContext;
    public final Object mLock = new Object();

    public DetachCheck(Context context) {
        this.mContext = context;
    }

    public int isAuthChipExistBySensor() {
        byte b;
        byte[] requestStartTypeS = requestStartTypeS();
        if (requestStartTypeS != null) {
            Log.i("SAccessoryManager_DetachCheck", "isAuthChipExistBySensor length=" + requestStartTypeS.length + " response=" + ((int) requestStartTypeS[0]));
            if (requestStartTypeS.length == 1 && ((b = requestStartTypeS[0]) == -32 || b == -9 || b == -15 || b == -11 || b == 30)) {
                Log.i("SAccessoryManager_DetachCheck", "NFC Transaction ongoing, response=" + String.format("0x%02x", Byte.valueOf(requestStartTypeS[0])));
                requestStopTypeS();
                return 2;
            }
            if (requestStartTypeS.length == 16) {
                requestStopTypeS();
                return 1;
            }
        }
        requestStopTypeS();
        return 0;
    }

    public boolean isAuthChipExist() {
        byte[] requestStartTypeS = requestStartTypeS();
        if (requestStartTypeS != null && requestStartTypeS.length == 16) {
            requestStopTypeS();
            return true;
        }
        requestStopTypeS();
        return false;
    }

    public final byte[] requestStartTypeS() {
        Log.i("SAccessoryManager_DetachCheck", "Reqs");
        synchronized (this.mLock) {
            this.mAuthStarted = true;
        }
        NfcAdapter nfcAdapter = getNfcAdapter();
        if (nfcAdapter == null) {
            return null;
        }
        try {
            return nfcAdapter.startCoverAuth();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void requestStopTypeS() {
        Log.i("SAccessoryManager_DetachCheck", "stopAuth");
        synchronized (this.mLock) {
            if (!this.mAuthStarted) {
                Log.w("SAccessoryManager_DetachCheck", "Do not call stopAuth because startAuth is not executed");
                return;
            }
            this.mAuthStarted = false;
            NfcAdapter nfcAdapter = getNfcAdapter();
            if (nfcAdapter != null) {
                try {
                    nfcAdapter.stopCoverAuth();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final NfcAdapter getNfcAdapter() {
        NfcAdapter nfcAdapter = null;
        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
            if (nfcAdapter == null) {
                Log.e("SAccessoryManager_DetachCheck", "NfcAdapter.getDefaultAdapter returns null");
                nfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
                if (nfcAdapter == null) {
                    Log.e("SAccessoryManager_DetachCheck", "retry, NfcAdapter.getDefaultAdapter returns null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nfcAdapter;
    }
}
