package com.samsung.accessory.manager;

import android.content.Context;
import android.util.Log;

import com.samsung.android.nfc.adapter.SamsungNfcAdapter;

import java.io.IOException;

public final class DetachCheck {
    public boolean mAuthStarted;
    public final Context mContext;
    public final Object mLock = new Object();

    public DetachCheck(Context context) {
        this.mContext = context;
    }

    public final SamsungNfcAdapter getSamsungNfcAdapter() {
        SamsungNfcAdapter samsungNfcAdapter = null;
        try {
            samsungNfcAdapter = SamsungNfcAdapter.getDefaultAdapter(this.mContext);
            if (samsungNfcAdapter == null) {
                Log.e(
                        "SAccessoryManager_DetachCheck",
                        "SamsungNfcAdapter.getDefaultAdapter returns null");
                samsungNfcAdapter = SamsungNfcAdapter.getDefaultAdapter(this.mContext);
                if (samsungNfcAdapter == null) {
                    Log.e(
                            "SAccessoryManager_DetachCheck",
                            "retry, SamsungNfcAdapter.getDefaultAdapter returns null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return samsungNfcAdapter;
    }

    public final byte[] requestStartTypeS() {
        Log.i("SAccessoryManager_DetachCheck", "Reqs");
        synchronized (this.mLock) {
            this.mAuthStarted = true;
        }
        if (getSamsungNfcAdapter() == null) {
            return null;
        }
        try {
            return SamsungNfcAdapter.startCoverAuth();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final void requestStopTypeS() {
        Log.i("SAccessoryManager_DetachCheck", "stopAuth");
        synchronized (this.mLock) {
            try {
                if (!this.mAuthStarted) {
                    Log.w(
                            "SAccessoryManager_DetachCheck",
                            "Do not call stopAuth because startAuth is not executed");
                    return;
                }
                this.mAuthStarted = false;
                if (getSamsungNfcAdapter() != null) {
                    try {
                        SamsungNfcAdapter.stopCoverAuth();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
