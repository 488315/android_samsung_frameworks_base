package com.samsung.android.server.battery;

import android.app.AlarmManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class ModernWatchBatteryManager implements WatchBatteryManagerInterface {
    public AlarmManager mAlarmManager;
    public Context mContext;
    public Handler mHandler;
    public boolean mRegistered = false;
    public boolean mScreenOn = false;
    public int mSyncStopOffset = 60;
    public int mSyncState = 0;
    public boolean mConnected = false;
    public int mAodShowState = 0;
    public HashMap mWatchPackageMap = new HashMap();
    public HashMap mProviderUriMap = new HashMap();
    public ScreenOffAlarmListener mAlarmListener = null;
    public boolean mAlarmRegistered = false;

    public ModernWatchBatteryManager(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void systemServicesReady() {
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        this.mAlarmListener = new ScreenOffAlarmListener();
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void displayStateChanged(boolean z) {
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
            this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + (this.mSyncStopOffset * 60 * 1000), "WatchBatteryManagerAlarm", this.mAlarmListener, this.mHandler);
            this.mAlarmRegistered = true;
        }
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void aodShowStateChanged(int i) {
        this.mAodShowState = i;
        if (i == 0 && !this.mScreenOn) {
            if (this.mSyncState == 1) {
                this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + (this.mSyncStopOffset * 60 * 1000), "WatchBatteryManagerAlarm", this.mAlarmListener, this.mHandler);
                this.mAlarmRegistered = true;
                return;
            }
            return;
        }
        if (this.mAlarmRegistered) {
            this.mAlarmManager.cancel(this.mAlarmListener);
            this.mAlarmRegistered = false;
        }
        checkSyncStart();
    }

    public final void checkSyncStart() {
        Slog.i("ModernWatchBatteryManager", "checkSyncStart() / mSyncState:" + this.mSyncState + " / mRegistered: " + this.mRegistered + " / mScreenOn:" + this.mScreenOn + " / mAodShowState:" + this.mAodShowState + " / mConnected:" + this.mConnected);
        if (this.mRegistered) {
            if ((this.mScreenOn || this.mAodShowState == 1) && this.mConnected && this.mSyncState == 0) {
                requestBatteryDataSync(1);
                this.mSyncState = 1;
            }
        }
    }

    public final void checkSyncStop() {
        Slog.i("ModernWatchBatteryManager", "checkSyncStop() / mSyncState:" + this.mSyncState + " / mRegistered: " + this.mRegistered + " / mScreenOn:" + this.mScreenOn + " / mAodShowState:" + this.mAodShowState + " / mConnected:" + this.mConnected);
        if (this.mSyncState == 1) {
            requestBatteryDataSync(0);
            this.mSyncState = 0;
        }
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void notifyPackageRegistered(boolean z) {
        Slog.i("ModernWatchBatteryManager", "isRegistered: " + z + "/ mSyncState:" + this.mSyncState + "/ mScreenOn:" + this.mScreenOn);
        if (z) {
            this.mRegistered = true;
            checkSyncStart();
        } else {
            this.mRegistered = false;
            checkSyncStop();
        }
    }

    public final void requestBatteryDataSync(final int i) {
        Slog.i("ModernWatchBatteryManager", "requestBatteryDataSync syncData: " + i);
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.ModernWatchBatteryManager.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("sync_battery_data", i);
                    Iterator it = ModernWatchBatteryManager.this.mProviderUriMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Uri uri = (Uri) ((Map.Entry) it.next()).getValue();
                        Slog.i("ModernWatchBatteryManager", "requestBatteryDataSync : " + uri.toString());
                        try {
                            Slog.i("ModernWatchBatteryManager", "requestBatteryDataSync : " + ModernWatchBatteryManager.this.mContext.getContentResolver().call(uri, "sync_request", (String) null, bundle).getInt(KnoxCustomManagerService.SPCM_KEY_RESULT));
                        } catch (IllegalArgumentException e) {
                            Slog.e("ModernWatchBatteryManager", "requestBatteryDataSync - IllegalArgumentException : " + e);
                        } catch (IllegalStateException e2) {
                            Slog.e("ModernWatchBatteryManager", "requestBatteryDataSync - IllegalStateException : " + e2);
                        }
                    }
                } catch (Exception e3) {
                    Slog.e("ModernWatchBatteryManager", "Exception occurred : " + e3);
                }
            }
        });
    }

    public class ScreenOffAlarmListener implements AlarmManager.OnAlarmListener {
        public ScreenOffAlarmListener() {
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            Slog.i("ModernWatchBatteryManager", "ScreenOffAlarmListener onAlarm() ");
            ModernWatchBatteryManager.this.checkSyncStop();
        }
    }

    public void addWatchPackageInfo(String str, String str2) {
        synchronized (this.mWatchPackageMap) {
            if (!this.mWatchPackageMap.containsKey(str)) {
                this.mWatchPackageMap.put(str, str2);
            }
        }
        synchronized (this.mProviderUriMap) {
            if (!this.mProviderUriMap.containsKey(str)) {
                this.mProviderUriMap.put(str, Uri.parse("content://" + str + ".BatteryInfoProvider"));
            }
        }
        this.mConnected = true;
        if (this.mRegistered) {
            if (this.mScreenOn || this.mAodShowState == 1) {
                requestBatteryDataSync(1);
                this.mSyncState = 1;
            }
        }
    }

    public void removeWatchPackageInfo(String str) {
        synchronized (this.mWatchPackageMap) {
            if (this.mWatchPackageMap.containsKey(str)) {
                this.mWatchPackageMap.remove(str);
            }
        }
        synchronized (this.mProviderUriMap) {
            if (this.mProviderUriMap.containsKey(str)) {
                this.mProviderUriMap.remove(str);
            }
        }
        if (this.mWatchPackageMap.size() == 0) {
            this.mConnected = false;
            this.mSyncState = 0;
        }
    }
}
