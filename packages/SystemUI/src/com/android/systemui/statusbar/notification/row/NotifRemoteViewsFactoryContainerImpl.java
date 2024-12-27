package com.android.systemui.statusbar.notification.row;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import javax.inject.Provider;
import kotlin.collections.builders.SetBuilder;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
