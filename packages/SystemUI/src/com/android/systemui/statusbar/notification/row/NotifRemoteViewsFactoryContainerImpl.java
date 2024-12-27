package com.android.systemui.statusbar.notification.row;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import javax.inject.Provider;
import kotlin.collections.builders.SetBuilder;

public final class NotifRemoteViewsFactoryContainerImpl implements NotifRemoteViewsFactoryContainer {
    public final SetBuilder factories;

    public NotifRemoteViewsFactoryContainerImpl(FeatureFlags featureFlags, PrecomputedTextViewFactory precomputedTextViewFactory, BigPictureLayoutInflaterFactory bigPictureLayoutInflaterFactory, NotificationOptimizedLinearLayoutFactory notificationOptimizedLinearLayoutFactory, Provider provider) {
        SetBuilder setBuilder = new SetBuilder();
        setBuilder.add(precomputedTextViewFactory);
        Flags flags = Flags.INSTANCE;
        if (android.widget.flags.Flags.notifLinearlayoutOptimized()) {
            setBuilder.add(notificationOptimizedLinearLayoutFactory);
        }
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        setBuilder.add(provider.get());
        this.factories = setBuilder.build();
    }
}
