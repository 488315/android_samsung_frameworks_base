package com.android.server.knox.dar.ddar;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public final class DualDarDoPolicyChecker {
    public static Context sContext;
    public static DualDarDoPolicyChecker sInstance;
    public List skippedPackages;

    public static synchronized DualDarDoPolicyChecker getInstance(Context context) {
        DualDarDoPolicyChecker dualDarDoPolicyChecker;
        synchronized (DualDarDoPolicyChecker.class) {
            try {
                sContext = context;
                if (sInstance == null) {
                    DualDarDoPolicyChecker dualDarDoPolicyChecker2 = new DualDarDoPolicyChecker();
                    dualDarDoPolicyChecker2.skippedPackages = new ArrayList();
                    sInstance = dualDarDoPolicyChecker2;
                }
                dualDarDoPolicyChecker = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dualDarDoPolicyChecker;
    }
}
