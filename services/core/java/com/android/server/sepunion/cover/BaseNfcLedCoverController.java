package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import com.samsung.android.nfc.adapter.SamsungNfcAdapter;
import com.samsung.android.sepunion.Log;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class BaseNfcLedCoverController {
    public final Context mContext;
    public boolean mIsLedCoverAttached = false;
    public NfcAdapter mNfcAdapter;
    public final PowerManager mPowerManager;
    public SamsungNfcAdapter mSamsungNfcAdapter;

    public BaseNfcLedCoverController(Context context) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        this.mSamsungNfcAdapter = SamsungNfcAdapter.getDefaultAdapter(context);
    }

    public static void acquireWakeLockWithPermission(PowerManager.WakeLock wakeLock) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!wakeLock.isHeld()) {
                wakeLock.acquire();
            }
        } catch (IllegalStateException e) {
            Log.e("CoverManager_BaseNfcLedCoverController", "Shouldn't happen", e);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public static String getByteDataString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void releaseWakeLockWithPermission(PowerManager.WakeLock wakeLock) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }
        } catch (IllegalStateException e) {
            Log.e("CoverManager_BaseNfcLedCoverController", "Shouldn't happen", e);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void addLedNotification(Bundle bundle) {
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println(" Current NfcLedCoverController state:");
        printWriter.print("  mIsLEDCoverAttached=");
        printWriter.println(this.mIsLedCoverAttached);
        printWriter.println("  ");
    }

    public final SamsungNfcAdapter getSamsungNfcAdapter() {
        if (this.mSamsungNfcAdapter == null) {
            this.mSamsungNfcAdapter = SamsungNfcAdapter.getDefaultAdapter(this.mContext);
        }
        if (this.mSamsungNfcAdapter == null) {
            Log.e("CoverManager_BaseNfcLedCoverController", "Could not get SamsungNfcAdapter");
        }
        return this.mSamsungNfcAdapter;
    }

    public void notifyAuthenticationResponse() {
    }

    public void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) {
    }

    public void removeLedNotification(Bundle bundle) {
    }

    public boolean requestCoverAuthentication(long j, IBinder iBinder, ComponentName componentName) {
        return false;
    }

    public void sendDataToNfcLedCover(int i, byte[] bArr) {
    }

    public void sendPowerKeyToCover() {
    }

    public void sendSystemEvent(Bundle bundle) {
    }

    public boolean setFotaInProgress(boolean z, IBinder iBinder, ComponentName componentName) {
        return false;
    }

    public boolean unRegisterNfcTouchListenerCallback(IBinder iBinder) {
        return false;
    }

    public void updateNfcLedCoverAttachStateLocked(int i, boolean z) {
    }
}
