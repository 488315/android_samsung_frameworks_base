package com.android.systemui.shade;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeModule_Companion_ProvidesNotificationShadeWindowViewFactory implements Provider {
    public final Provider layoutInflaterProvider;

    public ShadeModule_Companion_ProvidesNotificationShadeWindowViewFactory(Provider provider) {
        this.layoutInflaterProvider = provider;
    }

    public static NotificationShadeWindowView providesNotificationShadeWindowView(LayoutInflater layoutInflater) {
        ShadeModule.Companion.getClass();
        NotificationShadeWindowView notificationShadeWindowView = (NotificationShadeWindowView) layoutInflater.inflate(R.layout.super_notification_shade, (ViewGroup) null);
        if (notificationShadeWindowView != null) {
            return notificationShadeWindowView;
        }
        throw new IllegalStateException("R.layout.super_notification_shade could not be properly inflated");
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesNotificationShadeWindowView((LayoutInflater) this.layoutInflaterProvider.get());
    }
}
