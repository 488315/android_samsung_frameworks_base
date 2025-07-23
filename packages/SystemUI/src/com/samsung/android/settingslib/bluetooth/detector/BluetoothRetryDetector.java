package com.samsung.android.settingslib.bluetooth.detector;

import android.content.Intent;
import android.util.Log;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BluetoothRetryDetector {
    public FailCase mFailCase;
    public final boolean mIsForRestored;
    public final HashMap mRestoredDeviceList;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum FailCase {
        /* JADX INFO: Fake field, exist only in values array */
        SCANNING_FAILURE(2),
        CONNECTION_FAILURE(1),
        PAIRING_FAILURE(1),
        CONNECTION_FAILURE_HOGP(1),
        CONNECTION_FAILURE_LE(1),
        CONNECTION_FAILURE_WATCH(1),
        CONNECTION_FAILURE_RING(1);

        private final int maxCount;

        FailCase(int i) {
            this.maxCount = i;
        }
    }

    public BluetoothRetryDetector(boolean z) {
        new Intent().setClassName("com.samsung.android.net.wifi.wifiguider", "com.samsung.android.net.wifi.wifiguider.activity.bluetooth.BluetoothGuideActivity");
        this.mIsForRestored = z;
        if (z) {
            this.mRestoredDeviceList = new HashMap();
        }
    }

    public final void setFailCase(FailCase failCase) {
        Log.d("BluetoothRetryDetector", "Setting failcase:" + this.mFailCase.name());
        this.mFailCase = failCase;
    }

    public BluetoothRetryDetector(FailCase failCase, boolean z) {
        new Intent().setClassName("com.samsung.android.net.wifi.wifiguider", "com.samsung.android.net.wifi.wifiguider.activity.bluetooth.BluetoothGuideActivity");
        this.mIsForRestored = z;
        if (z) {
            this.mRestoredDeviceList = new HashMap();
        }
        this.mFailCase = failCase;
        failCase.getClass();
    }
}
