package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.server.notification.Flags;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class StackCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final ActiveNotificationsInteractor activeNotificationsInteractor;
    private final GroupExpansionManagerImpl groupExpansionManagerImpl;
    private final NotificationIconAreaController notificationIconAreaController;
    private final RenderNotificationListInteractor renderListInteractor;
    private final SensitiveNotificationProtectionController sensitiveNotificationProtectionController;

    public StackCoordinator(GroupExpansionManagerImpl groupExpansionManagerImpl, NotificationIconAreaController notificationIconAreaController, RenderNotificationListInteractor renderNotificationListInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        this.groupExpansionManagerImpl = groupExpansionManagerImpl;
        this.notificationIconAreaController = notificationIconAreaController;
        this.renderListInteractor = renderNotificationListInteractor;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.sensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
    }

    private final NotifStats calculateNotifStats(List<? extends ListEntry> list) {
        Flags.screenshareNotificationHiding();
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).isSensitiveStateActive();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (ListEntry listEntry : list) {
            NotifSection section = listEntry.getSection();
            if (section == null) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Null section for ", listEntry.getKey()).toString());
            }
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Null notif entry for ", listEntry.getKey()).toString());
            }
            boolean z5 = section.bucket == 13;
            boolean z6 = !isSensitiveStateActive && representativeEntry.isClearable();
            if (z5 && z6) {
                z4 = true;
            } else if (z5 && !z6) {
                z3 = true;
            } else if (!z5 && z6) {
                z2 = true;
            } else if (!z5 && !z6) {
                z = true;
            }
        }
        return new NotifStats(list.size(), z, z2, z3, z4);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<? extends ListEntry> list, NotifStackController notifStackController) {
                StackCoordinator.this.onAfterRenderList(list, notifStackController);
            }
        });
        GroupExpansionManagerImpl groupExpansionManagerImpl = this.groupExpansionManagerImpl;
        groupExpansionManagerImpl.mDumpManager.registerDumpable(groupExpansionManagerImpl);
        notifPipeline.addOnBeforeRenderListListener(groupExpansionManagerImpl.mNotifTracker);
    }

    public final void onAfterRenderList(List<? extends ListEntry> list, NotifStackController notifStackController) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("StackCoordinator.onAfterRenderList");
        }
        try {
            NotifStats calculateNotifStats = calculateNotifStats(list);
            com.android.systemui.Flags.notificationsFooterViewRefactor();
            ((NotificationStackScrollLayoutController.NotifStackControllerImpl) notifStackController).setNotifStats(calculateNotifStats);
            com.android.systemui.Flags.notificationsFooterViewRefactor();
            this.notificationIconAreaController.updateNotificationIcons(list);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
