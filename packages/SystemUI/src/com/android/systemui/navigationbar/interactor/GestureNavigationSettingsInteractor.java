package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$5;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GestureNavigationSettingsInteractor {
    public int bottomInsets;
    public NavBarStoreImpl$initInteractor$6 bottomSensitivityCallback;
    public boolean buttonForcedVisible;
    public final Context context;
    public NavBarStoreImpl$initInteractor$5 forcedVisibleCallback;
    public final GestureNavigationSettingsObserver observer;

    public GestureNavigationSettingsInteractor(Context context, Handler handler) {
        this.context = context;
        GestureNavigationSettingsObserver gestureNavigationSettingsObserver = new GestureNavigationSettingsObserver(context.getMainThreadHandler(), handler, context, new Runnable() { // from class: com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor$observer$1
            @Override // java.lang.Runnable
            public final void run() {
                GestureNavigationSettingsInteractor.this.onNavigationSettingsChanged();
            }
        });
        this.observer = gestureNavigationSettingsObserver;
        this.buttonForcedVisible = gestureNavigationSettingsObserver.areNavigationButtonForcedVisible();
        this.bottomInsets = gestureNavigationSettingsObserver.getBottomSensitivity(context.getResources());
    }

    public final void onNavigationSettingsChanged() {
        boolean areNavigationButtonForcedVisible = this.observer.areNavigationButtonForcedVisible();
        Log.i("GestureNavigationSettingsInteractor", "onNavigationSettingsChanged buttonForcedVisible " + this.buttonForcedVisible + " -> " + areNavigationButtonForcedVisible);
        this.buttonForcedVisible = areNavigationButtonForcedVisible;
        NavBarStoreImpl$initInteractor$5 navBarStoreImpl$initInteractor$5 = this.forcedVisibleCallback;
        if (navBarStoreImpl$initInteractor$5 != null) {
            navBarStoreImpl$initInteractor$5.accept(Boolean.valueOf(!areNavigationButtonForcedVisible));
        }
        int bottomSensitivity = this.observer.getBottomSensitivity(this.context.getResources());
        if (this.bottomInsets != bottomSensitivity) {
            this.bottomInsets = bottomSensitivity;
            NavBarStoreImpl$initInteractor$6 navBarStoreImpl$initInteractor$6 = this.bottomSensitivityCallback;
            if (navBarStoreImpl$initInteractor$6 != null) {
                navBarStoreImpl$initInteractor$6.accept(Integer.valueOf(bottomSensitivity));
            }
        }
    }
}
