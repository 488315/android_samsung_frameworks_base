package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.data.model.NotifStats;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
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

    private final NotifStats calculateNotifStats(List<? extends PipelineEntry> list) {
        boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).isSensitiveStateActive();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (PipelineEntry pipelineEntry : list) {
            NotifSection notifSection = pipelineEntry.mAttachState.section;
            if (notifSection == null) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Null section for ", pipelineEntry.getKey()).toString());
            }
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Null notif entry for ", pipelineEntry.getKey()).toString());
            }
            boolean z5 = notifSection.bucket == 20;
            boolean z6 = !isSensitiveStateActive && representativeEntry.isClearable();
            if (z5 && z6) {
                z4 = true;
            } else if (z5 && !z6) {
                z3 = true;
            } else if (z5 || !z6) {
                z = true;
            } else {
                z2 = true;
            }
        }
        return new NotifStats(z, z2, z3, z4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderList(List<? extends PipelineEntry> list) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("StackCoordinator.onAfterRenderList");
        }
        try {
            this.activeNotificationsInteractor.repository.notifStats.setValue(calculateNotifStats(list));
            this.renderListInteractor.setRenderedList(list);
            this.notificationIconAreaController.updateNotificationIcons(list);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<? extends PipelineEntry> list) {
                StackCoordinator.this.onAfterRenderList(list);
            }
        });
        GroupExpansionManagerImpl groupExpansionManagerImpl = this.groupExpansionManagerImpl;
        groupExpansionManagerImpl.mDumpManager.registerDumpable(groupExpansionManagerImpl);
        notifPipeline.addOnBeforeRenderListListener(groupExpansionManagerImpl.mNotifTracker);
    }
}
