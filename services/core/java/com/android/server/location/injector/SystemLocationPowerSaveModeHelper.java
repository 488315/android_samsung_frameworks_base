package com.android.server.location.injector;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerSaveState;
import android.util.Log;

import com.android.server.location.LocationServiceThread;
import com.android.server.location.eventlog.LocationEventLog;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemLocationPowerSaveModeHelper implements Consumer {
    public final Context mContext;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public volatile int mLocationPowerSaveMode;
    public boolean mReady;

    public SystemLocationPowerSaveModeHelper(Context context) {
        this.mContext = context;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PowerSaveState powerSaveState = (PowerSaveState) obj;
        final int i = !powerSaveState.batterySaverEnabled ? 0 : powerSaveState.locationMode;
        if (i == this.mLocationPowerSaveMode) {
            return;
        }
        this.mLocationPowerSaveMode = i;
        LocationServiceThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.location.injector.SystemLocationPowerSaveModeHelper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemLocationPowerSaveModeHelper
                                        systemLocationPowerSaveModeHelper =
                                                SystemLocationPowerSaveModeHelper.this;
                                int i2 = i;
                                systemLocationPowerSaveModeHelper.getClass();
                                Log.d(
                                        "LocationManagerService",
                                        "location power save mode is now "
                                                + PowerManager.locationPowerSaveModeToString(i2));
                                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                                locationEventLog.getClass();
                                locationEventLog.addLog$1(
                                        new LocationEventLog.LocationPowerSaveModeEvent(i2));
                                Iterator it =
                                        systemLocationPowerSaveModeHelper.mListeners.iterator();
                                while (it.hasNext()) {
                                    ((LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener)
                                                    it.next())
                                            .onLocationPowerSaveModeChanged(i2);
                                }
                            }
                        });
    }
}
