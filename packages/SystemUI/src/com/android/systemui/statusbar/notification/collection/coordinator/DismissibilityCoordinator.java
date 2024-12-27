package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class DismissibilityCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final KeyguardStateController keyguardStateController;
    private final NotificationDismissibilityProviderImpl provider;

    public DismissibilityCoordinator(KeyguardStateController keyguardStateController, NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl) {
        this.keyguardStateController = keyguardStateController;
        this.provider = notificationDismissibilityProviderImpl;
    }

    private final boolean markNonDismissibleEntries(Set<String> set, List<? extends ListEntry> list, boolean z) {
        boolean z2 = false;
        for (ListEntry listEntry : list) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null && (representativeEntry.mSbn.isNonDismissable() || (representativeEntry.mSbn.isOngoing() && z))) {
                set.add(representativeEntry.mKey);
                z2 = true;
            }
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                if (markNonDismissibleEntries(set, groupEntry.mUnmodifiableChildren, z)) {
                    NotificationEntry notificationEntry = groupEntry.mSummary;
                    if (notificationEntry != null) {
                        set.add(notificationEntry.mKey);
                    }
                    z2 = true;
                }
            }
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderListListener(List<? extends ListEntry> list) {
        boolean z = !this.keyguardStateController.isUnlocked();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        markNonDismissibleEntries(linkedHashSet, list, z);
        NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl = this.provider;
        synchronized (notificationDismissibilityProviderImpl) {
            notificationDismissibilityProviderImpl.nonDismissableEntryKeys = CollectionsKt___CollectionsKt.toSet(linkedHashSet);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DismissibilityCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List<? extends ListEntry> list) {
                DismissibilityCoordinator.this.onBeforeRenderListListener(list);
            }
        });
    }
}
