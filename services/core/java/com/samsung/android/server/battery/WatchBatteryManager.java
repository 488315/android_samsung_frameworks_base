package com.samsung.android.server.battery;

import android.app.AlarmManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WatchBatteryManager {
    public ScreenOffAlarmListener mAlarmListener;
    public AlarmManager mAlarmManager;
    public boolean mAlarmRegistered;
    public int mAodShowState;
    public boolean mConnected;
    public Context mContext;
    public Handler mHandler;
    public HashMap mProviderUriMap;
    public boolean mRegistered;
    public boolean mScreenOn;
    public int mSyncState;
    public HashMap mWatchPackageMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenOffAlarmListener implements AlarmManager.OnAlarmListener {
        public ScreenOffAlarmListener() {}

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            Slog.i("WatchBatteryManager", "ScreenOffAlarmListener onAlarm() ");
            WatchBatteryManager.this.checkSyncStop();
        }
    }

    public final void aodShowStateChanged(int i) {
        this.mAodShowState = i;
        if (i != 0 || this.mScreenOn) {
            if (this.mAlarmRegistered) {
                this.mAlarmManager.cancel(this.mAlarmListener);
                this.mAlarmRegistered = false;
            }
            checkSyncStart();
            return;
        }
        if (this.mSyncState == 1) {
            this.mAlarmManager.setExact(
                    2,
                    (60 * 60000) + SystemClock.elapsedRealtime(),
                    "WatchBatteryManagerAlarm",
                    this.mAlarmListener,
                    this.mHandler);
            this.mAlarmRegistered = true;
        }
    }

    public final void checkSyncStart() {
        StringBuilder sb = new StringBuilder("checkSyncStart() / mSyncState:");
        sb.append(this.mSyncState);
        sb.append(" / mRegistered: ");
        sb.append(this.mRegistered);
        sb.append(" / mScreenOn:");
        sb.append(this.mScreenOn);
        sb.append(" / mAodShowState:");
        sb.append(this.mAodShowState);
        sb.append(" / mConnected:");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("WatchBatteryManager", sb, this.mConnected);
        if (this.mRegistered) {
            if ((this.mScreenOn || this.mAodShowState == 1)
                    && this.mConnected
                    && this.mSyncState == 0) {
                requestBatteryDataSync(1);
                this.mSyncState = 1;
            }
        }
    }

    public final void checkSyncStop() {
        StringBuilder sb = new StringBuilder("checkSyncStop() / mSyncState:");
        sb.append(this.mSyncState);
        sb.append(" / mRegistered: ");
        sb.append(this.mRegistered);
        sb.append(" / mScreenOn:");
        sb.append(this.mScreenOn);
        sb.append(" / mAodShowState:");
        sb.append(this.mAodShowState);
        sb.append(" / mConnected:");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("WatchBatteryManager", sb, this.mConnected);
        if (this.mSyncState == 1) {
            requestBatteryDataSync(0);
            this.mSyncState = 0;
        }
    }

    public final void displayStateChanged(boolean z) {
        if (z) {
            this.mScreenOn = true;
            if (this.mAlarmRegistered) {
                this.mAlarmManager.cancel(this.mAlarmListener);
                this.mAlarmRegistered = false;
            }
            checkSyncStart();
            return;
        }
        this.mScreenOn = false;
        if (this.mAodShowState == 0 && this.mSyncState == 1) {
            this.mAlarmManager.setExact(
                    2,
                    (60 * 60000) + SystemClock.elapsedRealtime(),
                    "WatchBatteryManagerAlarm",
                    this.mAlarmListener,
                    this.mHandler);
            this.mAlarmRegistered = true;
        }
    }

    public final void notifyPackageRegistered(boolean z) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m("isRegistered: ", "/ mSyncState:", z);
        m.append(this.mSyncState);
        m.append("/ mScreenOn:");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("WatchBatteryManager", m, this.mScreenOn);
        if (z) {
            this.mRegistered = true;
            checkSyncStart();
        } else {
            this.mRegistered = false;
            checkSyncStop();
        }
    }

    public final void removeWatchPackageInfo(String str) {
        synchronized (this.mWatchPackageMap) {
            try {
                if (this.mWatchPackageMap.containsKey(str)) {
                    this.mWatchPackageMap.remove(str);
                }
            } finally {
            }
        }
        synchronized (this.mProviderUriMap) {
            try {
                if (this.mProviderUriMap.containsKey(str)) {
                    this.mProviderUriMap.remove(str);
                }
            } finally {
            }
        }
        if (this.mWatchPackageMap.size() == 0) {
            this.mConnected = false;
            this.mSyncState = 0;
        }
    }

    public final void requestBatteryDataSync(final int i) {
        Slog.i("WatchBatteryManager", "requestBatteryDataSync syncData: " + i);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.server.battery.WatchBatteryManager.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putInt("sync_battery_data", i);
                            Iterator it =
                                    WatchBatteryManager.this.mProviderUriMap.entrySet().iterator();
                            while (it.hasNext()) {
                                Uri uri = (Uri) ((Map.Entry) it.next()).getValue();
                                Slog.i(
                                        "WatchBatteryManager",
                                        "requestBatteryDataSync : " + uri.toString());
                                try {
                                    Slog.i(
                                            "WatchBatteryManager",
                                            "requestBatteryDataSync : "
                                                    + WatchBatteryManager.this
                                                            .mContext
                                                            .getContentResolver()
                                                            .call(
                                                                    uri,
                                                                    "sync_request",
                                                                    (String) null,
                                                                    bundle)
                                                            .getInt(
                                                                    KnoxCustomManagerService
                                                                            .SPCM_KEY_RESULT));
                                } catch (IllegalArgumentException e) {
                                    Slog.e(
                                            "WatchBatteryManager",
                                            "requestBatteryDataSync - IllegalArgumentException : "
                                                    + e);
                                } catch (IllegalStateException e2) {
                                    Slog.e(
                                            "WatchBatteryManager",
                                            "requestBatteryDataSync - IllegalStateException : "
                                                    + e2);
                                }
                            }
                        } catch (Exception e3) {
                            BootReceiver$$ExternalSyntheticOutline0.m(
                                    e3, "Exception occurred : ", "WatchBatteryManager");
                        }
                    }
                });
    }
}
