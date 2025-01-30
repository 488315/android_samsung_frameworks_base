package com.android.systemui.statusbar.notification;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotificationFadeAware {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FadeOptimizedNotification extends NotificationFadeAware {
    }

    static void setLayerTypeForFaded(View view, boolean z) {
        if (view != null) {
            view.setLayerType(z ? 2 : 0, null);
        }
    }
}
