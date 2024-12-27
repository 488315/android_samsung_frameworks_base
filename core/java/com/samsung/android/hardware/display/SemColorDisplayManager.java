package com.samsung.android.hardware.display;

import android.content.Context;
import android.hardware.display.IColorDisplayManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public class SemColorDisplayManager {
    private static final String TAG = SemColorDisplayManager.class.getSimpleName();
    private static SemColorDisplayManager sInstance;
    private final IColorDisplayManager mColorDisplayManager =
            IColorDisplayManager.Stub.asInterface(
                    ServiceManager.getService(Context.COLOR_DISPLAY_SERVICE));

    private SemColorDisplayManager() {}

    public static synchronized SemColorDisplayManager getInstance() {
        SemColorDisplayManager semColorDisplayManager;
        synchronized (SemColorDisplayManager.class) {
            if (sInstance == null) {
                sInstance = new SemColorDisplayManager();
            }
            semColorDisplayManager = sInstance;
        }
        return semColorDisplayManager;
    }

    public boolean setSaturationLevel(int saturationLevel) {
        if (saturationLevel < 0 || saturationLevel > 100) {
            throw new IllegalArgumentException("Saturation level must be between 0 and 100");
        }
        try {
            return this.mColorDisplayManager.setSaturationLevel(saturationLevel);
        } catch (RemoteException e) {
            Log.e(TAG, "setSaturationLevel failed.", e);
            return false;
        }
    }
}
