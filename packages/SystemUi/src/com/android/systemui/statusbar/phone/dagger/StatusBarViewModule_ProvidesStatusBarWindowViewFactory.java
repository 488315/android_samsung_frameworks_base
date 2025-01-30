package com.android.systemui.statusbar.phone.dagger;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.LegacyNotificationShelfControllerImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.notification.row.dagger.NotificationShelfComponent;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarViewModule_ProvidesStatusBarWindowViewFactory implements Provider {
    public final Provider featureFlagsProvider;
    public final Provider newImplProvider;
    public final Provider notificationShelfComponentBuilderProvider;
    public final Provider notificationShelfProvider;

    public StatusBarViewModule_ProvidesStatusBarWindowViewFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.featureFlagsProvider = provider;
        this.newImplProvider = provider2;
        this.notificationShelfComponentBuilderProvider = provider3;
        this.notificationShelfProvider = provider4;
    }

    public static LegacyNotificationShelfControllerImpl providesStatusBarWindowView(FeatureFlags featureFlags, NotificationShelfComponent.Builder builder, NotificationShelf notificationShelf) {
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        LegacyNotificationShelfControllerImpl notificationShelfController = builder.notificationShelf(notificationShelf).build().getNotificationShelfController();
        notificationShelfController.mActivatableNotificationViewController.init();
        NotificationShelf notificationShelf2 = notificationShelfController.mView;
        if (notificationShelf2.mShelfRefactorFlagEnabled) {
            NotificationShelfController.Companion.getClass();
            throw new IllegalStateException("Code path not supported when Flags.NOTIFICATION_SHELF_REFACTOR is ".concat("enabled").toString());
        }
        notificationShelf2.mController = notificationShelfController;
        LegacyNotificationShelfControllerImpl.ViewOnAttachStateChangeListenerC25621 viewOnAttachStateChangeListenerC25621 = notificationShelfController.mOnAttachStateChangeListener;
        notificationShelf2.addOnAttachStateChangeListener(viewOnAttachStateChangeListenerC25621);
        if (notificationShelf2.isAttachedToWindow()) {
            viewOnAttachStateChangeListenerC25621.onViewAttachedToWindow(notificationShelf2);
        }
        return notificationShelfController;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesStatusBarWindowView((FeatureFlags) this.featureFlagsProvider.get(), (NotificationShelfComponent.Builder) this.notificationShelfComponentBuilderProvider.get(), (NotificationShelf) this.notificationShelfProvider.get());
    }
}
