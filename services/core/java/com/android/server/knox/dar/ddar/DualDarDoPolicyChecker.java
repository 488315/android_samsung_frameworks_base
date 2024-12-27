package com.android.server.knox.dar.ddar;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
