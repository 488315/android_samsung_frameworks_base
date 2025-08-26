package com.android.systemui.statusbar.notification.row;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.notification.row.icon.NotificationRowIconViewInflaterFactory;
import javax.inject.Provider;
import kotlin.collections.builders.SetBuilder;

/* loaded from: classes3.dex */
public final class NotifRemoteViewsFactoryContainerImpl implements NotifRemoteViewsFactoryContainer {
    public final SetBuilder factories;

    public NotifRemoteViewsFactoryContainerImpl(FeatureFlags featureFlags, PrecomputedTextViewFactory precomputedTextViewFactory, BigPictureLayoutInflaterFactory bigPictureLayoutInflaterFactory, NotificationOptimizedLinearLayoutFactory notificationOptimizedLinearLayoutFactory, Provider provider, NotificationRowIconViewInflaterFactory notificationRowIconViewInflaterFactory) {
        SetBuilder setBuilder = new SetBuilder();
        setBuilder.add(precomputedTextViewFactory);
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        setBuilder.add(notificationOptimizedLinearLayoutFactory);
        setBuilder.add(provider.get());
        this.factories = setBuilder.build();
    }
}
