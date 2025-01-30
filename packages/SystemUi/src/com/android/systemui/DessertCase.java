package com.android.systemui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.android.systemui.DessertCaseView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DessertCase extends Activity {
    public DessertCaseView mView;

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        DessertCaseView dessertCaseView = this.mView;
        dessertCaseView.mStarted = false;
        dessertCaseView.mHandler.removeCallbacks(dessertCaseView.mJuggle);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mView.postDelayed(new Runnable() { // from class: com.android.systemui.DessertCase.1
            @Override // java.lang.Runnable
            public final void run() {
                DessertCase.this.mView.start();
            }
        }, 1000L);
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        PackageManager packageManager = getPackageManager();
        ComponentName componentName = new ComponentName(this, (Class<?>) DessertCaseDream.class);
        if (packageManager.getComponentEnabledSetting(componentName) != 1) {
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
        }
        this.mView = new DessertCaseView(this);
        DessertCaseView.RescalingContainer rescalingContainer = new DessertCaseView.RescalingContainer(this);
        DessertCaseView dessertCaseView = this.mView;
        rescalingContainer.addView(dessertCaseView);
        rescalingContainer.mView = dessertCaseView;
        setContentView(rescalingContainer);
    }
}
