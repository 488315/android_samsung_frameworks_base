package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.fingerprint.IFingerprintClientActiveCallback;
import android.os.RemoteException;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GestureAvailabilityDispatcher {
    public boolean mIsActive;
    public final CopyOnWriteArrayList mClientActiveCallbacks = new CopyOnWriteArrayList();
    public final Map mActiveSensors = new HashMap();

    public final void markSensorActive(int i, boolean z) {
        boolean z2;
        ((HashMap) this.mActiveSensors).put(Integer.valueOf(i), Boolean.valueOf(z));
        boolean z3 = this.mIsActive;
        Iterator it = ((HashMap) this.mActiveSensors).values().iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            } else if (((Boolean) it.next()).booleanValue()) {
                z2 = true;
                break;
            }
        }
        if (z3 != z2) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "GestureAvailabilityTracker",
                    new StringBuilder("Notifying gesture availability, active="),
                    this.mIsActive);
            this.mIsActive = z2;
            Iterator it2 = this.mClientActiveCallbacks.iterator();
            while (it2.hasNext()) {
                IFingerprintClientActiveCallback iFingerprintClientActiveCallback =
                        (IFingerprintClientActiveCallback) it2.next();
                try {
                    iFingerprintClientActiveCallback.onClientActiveChanged(z2);
                } catch (RemoteException unused) {
                    this.mClientActiveCallbacks.remove(iFingerprintClientActiveCallback);
                }
            }
        }
    }
}
