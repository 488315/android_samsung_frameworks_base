package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class KeyguardCoordinator implements Coordinator {
    private static final String TAG = "KeyguardCoordinator";
    private final AmbientState ambientState;
    private final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    private final NotifFilter notifFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$notifFilter$1
        {
            super("KeyguardCoordinator");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
            keyguardNotificationVisibilityProvider = KeyguardCoordinator.this.keyguardNotificationVisibilityProvider;
            return ((KeyguardNotificationVisibilityProviderImpl) keyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry);
        }
    };
    private final SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider;
    private final StatusBarStateController statusBarStateController;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public KeyguardCoordinator(KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, StatusBarStateController statusBarStateController, AmbientState ambientState) {
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.sectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
        this.statusBarStateController = statusBarStateController;
        this.ambientState = ambientState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void invalidateListFromFilter(String str) {
        updateSectionHeadersVisibility();
        this.notifFilter.invalidateList(str);
    }

    private final void updateSectionHeadersVisibility() {
        boolean z = false;
        boolean z2 = this.statusBarStateController.getState() == 1;
        boolean z3 = this.sectionHeaderVisibilityProvider.neverShowSectionHeaders;
        if ((!z2 || this.ambientState.isNeedsToExpandLocksNoti()) && !z3) {
            z = true;
        }
        this.sectionHeaderVisibilityProvider.sectionHeadersVisible = z;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        setupInvalidateNotifListCallbacks();
        notifPipeline.addFinalizeFilter(this.notifFilter);
        KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider = this.keyguardNotificationVisibilityProvider;
        ((KeyguardNotificationVisibilityProviderImpl) keyguardNotificationVisibilityProvider).onStateChangedListeners.addIfAbsent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$attach$1
            @Override // java.util.function.Consumer
            public final void accept(String str) {
                KeyguardCoordinator.this.invalidateListFromFilter(str);
            }
        });
        updateSectionHeadersVisibility();
    }

    private final void setupInvalidateNotifListCallbacks() {
    }
}
