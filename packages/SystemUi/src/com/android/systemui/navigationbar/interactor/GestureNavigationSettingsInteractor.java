package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GestureNavigationSettingsInteractor {
    public int bottomInsets;
    public Consumer bottomSensitivityCallback;
    public boolean buttonForcedVisible;
    public final Context context;
    public Consumer forcedVisibleCallback;
    public final GestureNavigationSettingsObserver observer;

    public GestureNavigationSettingsInteractor(Context context) {
        this.context = context;
        GestureNavigationSettingsObserver gestureNavigationSettingsObserver = new GestureNavigationSettingsObserver(context.getMainThreadHandler(), context, new Runnable() { // from class: com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor$observer$1
            @Override // java.lang.Runnable
            public final void run() {
                GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = GestureNavigationSettingsInteractor.this;
                GestureNavigationSettingsObserver gestureNavigationSettingsObserver2 = gestureNavigationSettingsInteractor.observer;
                boolean areNavigationButtonForcedVisible = gestureNavigationSettingsObserver2.areNavigationButtonForcedVisible();
                if (gestureNavigationSettingsInteractor.buttonForcedVisible != areNavigationButtonForcedVisible) {
                    gestureNavigationSettingsInteractor.buttonForcedVisible = areNavigationButtonForcedVisible;
                    Consumer consumer = gestureNavigationSettingsInteractor.forcedVisibleCallback;
                    if (consumer != null) {
                        consumer.accept(Boolean.valueOf(areNavigationButtonForcedVisible));
                    }
                }
                int bottomSensitivity = gestureNavigationSettingsObserver2.getBottomSensitivity(gestureNavigationSettingsInteractor.context.getResources());
                if (gestureNavigationSettingsInteractor.bottomInsets != bottomSensitivity) {
                    gestureNavigationSettingsInteractor.bottomInsets = bottomSensitivity;
                    Consumer consumer2 = gestureNavigationSettingsInteractor.bottomSensitivityCallback;
                    if (consumer2 != null) {
                        consumer2.accept(Integer.valueOf(bottomSensitivity));
                    }
                }
            }
        });
        this.observer = gestureNavigationSettingsObserver;
        this.bottomInsets = gestureNavigationSettingsObserver.getBottomSensitivity(context.getResources());
    }
}
