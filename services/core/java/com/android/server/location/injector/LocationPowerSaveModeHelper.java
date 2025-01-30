package com.android.server.location.injector;

import android.os.PowerManager;
import android.util.Log;
import com.android.server.location.eventlog.LocationEventLog;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class LocationPowerSaveModeHelper {
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public interface LocationPowerSaveModeChangedListener {
        void onLocationPowerSaveModeChanged(int i);
    }

    public abstract int getLocationPowerSaveMode();

    public final void addListener(LocationPowerSaveModeChangedListener locationPowerSaveModeChangedListener) {
        this.mListeners.add(locationPowerSaveModeChangedListener);
    }

    public final void removeListener(LocationPowerSaveModeChangedListener locationPowerSaveModeChangedListener) {
        this.mListeners.remove(locationPowerSaveModeChangedListener);
    }

    public final void notifyLocationPowerSaveModeChanged(int i) {
        Log.d("LocationManagerService", "location power save mode is now " + PowerManager.locationPowerSaveModeToString(i));
        LocationEventLog.EVENT_LOG.logLocationPowerSaveMode(i);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LocationPowerSaveModeChangedListener) it.next()).onLocationPowerSaveModeChanged(i);
        }
    }
}
