package com.android.systemui.demomode;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.util.settings.GlobalSettings;

public abstract class DemoModeAvailabilityTracker {
    public final DemoModeAvailabilityTracker$allowedObserver$1 allowedObserver;
    public final Context context;
    public final GlobalSettings globalSettings;
    public boolean isDemoModeAvailable;
    public boolean isInDemoMode;
    public final DemoModeAvailabilityTracker$onObserver$1 onObserver;

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.demomode.DemoModeAvailabilityTracker$allowedObserver$1] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.demomode.DemoModeAvailabilityTracker$onObserver$1] */
    public DemoModeAvailabilityTracker(Context context, GlobalSettings globalSettings) {
        this.context = context;
        this.globalSettings = globalSettings;
        this.isInDemoMode = globalSettings.getInt("sysui_tuner_demo_on", 0) != 0;
        this.isDemoModeAvailable = globalSettings.getInt("sysui_demo_allowed", 0) != 0;
        final Handler handler = new Handler(Looper.getMainLooper());
        this.allowedObserver = new ContentObserver(handler) { // from class: com.android.systemui.demomode.DemoModeAvailabilityTracker$allowedObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                boolean z2 = DemoModeAvailabilityTracker.this.globalSettings.getInt("sysui_demo_allowed", 0) != 0;
                DemoModeAvailabilityTracker demoModeAvailabilityTracker = DemoModeAvailabilityTracker.this;
                if (demoModeAvailabilityTracker.isDemoModeAvailable == z2) {
                    return;
                }
                demoModeAvailabilityTracker.isDemoModeAvailable = z2;
                demoModeAvailabilityTracker.onDemoModeAvailabilityChanged();
            }
        };
        final Handler handler2 = new Handler(Looper.getMainLooper());
        this.onObserver = new ContentObserver(handler2) { // from class: com.android.systemui.demomode.DemoModeAvailabilityTracker$onObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                boolean z2 = DemoModeAvailabilityTracker.this.globalSettings.getInt("sysui_tuner_demo_on", 0) != 0;
                DemoModeAvailabilityTracker demoModeAvailabilityTracker = DemoModeAvailabilityTracker.this;
                if (demoModeAvailabilityTracker.isInDemoMode == z2) {
                    return;
                }
                demoModeAvailabilityTracker.isInDemoMode = z2;
                if (z2) {
                    demoModeAvailabilityTracker.onDemoModeStarted();
                } else {
                    demoModeAvailabilityTracker.onDemoModeFinished();
                }
            }
        };
    }

    public abstract void onDemoModeAvailabilityChanged();

    public abstract void onDemoModeFinished();

    public abstract void onDemoModeStarted();

    public final void startTracking() {
        ContentResolver contentResolver = this.context.getContentResolver();
        GlobalSettings globalSettings = this.globalSettings;
        contentResolver.registerContentObserver(globalSettings.getUriFor("sysui_demo_allowed"), false, this.allowedObserver);
        contentResolver.registerContentObserver(globalSettings.getUriFor("sysui_tuner_demo_on"), false, this.onObserver);
    }
}
