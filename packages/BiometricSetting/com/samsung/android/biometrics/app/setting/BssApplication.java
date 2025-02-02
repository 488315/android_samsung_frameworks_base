package com.samsung.android.biometrics.app.setting;

import android.app.Application;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class BssApplication extends Application {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        ActivityMonitor activityMonitor = ActivityMonitor.get();
        Supplier supplier = new Supplier() { // from class: com.samsung.android.biometrics.app.setting.BssApplication$$ExternalSyntheticLambda0
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
