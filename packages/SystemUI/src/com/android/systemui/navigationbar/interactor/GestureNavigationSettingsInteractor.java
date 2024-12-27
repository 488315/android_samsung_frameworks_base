package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GestureNavigationSettingsInteractor {
    public int bottomInsets;
    public Consumer bottomSensitivityCallback;
    public boolean buttonForcedVisible;
    public final Context context;
    public Consumer forcedVisibleCallback;
    public final GestureNavigationSettingsObserver observer;

    public GestureNavigationSettingsInteractor(Context context, Handler handler) {
        this.context = context;
        GestureNavigationSettingsObserver gestureNavigationSettingsObserver = new GestureNavigationSettingsObserver(context.getMainThreadHandler(), handler, context, new Runnable() { // from class: com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor$observer$1
            @Override // java.lang.Runnable
            public final void run() {
                GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = GestureNavigationSettingsInteractor.this;
                boolean areNavigationButtonForcedVisible = gestureNavigationSettingsInteractor.observer.areNavigationButtonForcedVisible();
                Log.i("GestureNavigationSettingsInteractor", "onNavigationSettingsChanged buttonForcedVisible " + gestureNavigationSettingsInteractor.buttonForcedVisible + " -> " + areNavigationButtonForcedVisible);
                gestureNavigationSettingsInteractor.buttonForcedVisible = areNavigationButtonForcedVisible;
                Consumer consumer = gestureNavigationSettingsInteractor.forcedVisibleCallback;
                if (consumer != null) {
                    consumer.accept(Boolean.valueOf(!areNavigationButtonForcedVisible));
                }
                int bottomSensitivity = gestureNavigationSettingsInteractor.observer.getBottomSensitivity(gestureNavigationSettingsInteractor.context.getResources());
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
        this.buttonForcedVisible = gestureNavigationSettingsObserver.areNavigationButtonForcedVisible();
        this.bottomInsets = gestureNavigationSettingsObserver.getBottomSensitivity(context.getResources());
    }
}
