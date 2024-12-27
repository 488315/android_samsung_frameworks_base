package com.android.systemui.statusbar.notification;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface NotificationFadeAware {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface FadeOptimizedNotification extends NotificationFadeAware {
    }

    static void setLayerTypeForFaded(View view, boolean z) {
        if (view != null) {
            view.setLayerType(z ? 2 : 0, null);
        }
    }
}
