package com.samsung.android.biometrics.app.setting;

import android.app.Application;

import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class BssApplication extends Application {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        if (ActivityMonitor.sInstance == null) {
            synchronized (ActivityMonitor.class) {
                try {
                    if (ActivityMonitor.sInstance == null) {
                        ActivityMonitor.sInstance = new ActivityMonitor();
                    }
                } finally {
                }
            }
        }
        ActivityMonitor activityMonitor = ActivityMonitor.sInstance;
        Supplier supplier =
                new Supplier() { // from class:
                                 // com.samsung.android.biometrics.app.setting.BssApplication$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        BssApplication bssApplication = BssApplication.this;
                        int i = BssApplication.$r8$clinit;
                        bssApplication.getClass();
                        return bssApplication;
                    }
                };
        activityMonitor.getClass();
        ((Application) supplier.get()).registerActivityLifecycleCallbacks(activityMonitor);
    }
}
