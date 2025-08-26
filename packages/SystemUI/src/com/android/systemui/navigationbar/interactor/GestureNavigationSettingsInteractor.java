package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$5;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$6;

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
                this.$tmp0.onNavigationSettingsChanged();
            }
        });
        this.observer = gestureNavigationSettingsObserver;
        this.buttonForcedVisible = gestureNavigationSettingsObserver.areNavigationButtonForcedVisible();
        this.bottomInsets = gestureNavigationSettingsObserver.getBottomSensitivity(context.getResources());
    }

    public final void onNavigationSettingsChanged() {
        boolean zAreNavigationButtonForcedVisible = this.observer.areNavigationButtonForcedVisible();
        Log.i("GestureNavigationSettingsInteractor", "onNavigationSettingsChanged buttonForcedVisible " + this.buttonForcedVisible + " -> " + zAreNavigationButtonForcedVisible);
        this.buttonForcedVisible = zAreNavigationButtonForcedVisible;
        NavBarStoreImpl$initInteractor$5 navBarStoreImpl$initInteractor$5 = this.forcedVisibleCallback;
        if (navBarStoreImpl$initInteractor$5 != null) {
            navBarStoreImpl$initInteractor$5.accept(Boolean.valueOf(!zAreNavigationButtonForcedVisible));
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
