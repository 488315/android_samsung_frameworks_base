package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.location.LocationManager;
import android.os.UserHandle;
import com.sec.ims.gls.GlsIntent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LocationManagerWrapper {
    private static final LocationManagerWrapper sInstance = new LocationManagerWrapper();
    private static final LocationManager mLocationManager = (LocationManager) AppGlobals.getInitialApplication().getSystemService(GlsIntent.Extras.EXTRA_LOCATION);

    private LocationManagerWrapper() {
    }

    public static LocationManagerWrapper getInstance() {
        return sInstance;
    }

    public void setLocationEnabledForUser(boolean z, int i) {
        mLocationManager.setLocationEnabledForUser(z, UserHandle.semOf(i));
    }
}
