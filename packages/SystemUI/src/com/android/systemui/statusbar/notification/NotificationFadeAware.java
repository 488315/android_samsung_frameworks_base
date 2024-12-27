package com.android.systemui.statusbar.notification;

import android.view.View;

public interface NotificationFadeAware {

    public interface FadeOptimizedNotification extends NotificationFadeAware {
    }

    static void setLayerTypeForFaded(View view, boolean z) {
        if (view != null) {
            view.setLayerType(z ? 2 : 0, null);
        }
    }
}
