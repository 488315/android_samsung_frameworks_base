package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RowAppearanceCoordinator implements Coordinator {
    public NotificationEntry entryToExpand;
    public final boolean mAlwaysExpandNonGroupedNotification;
    public final AssistantFeedbackController mAssistantFeedbackController;
    public final SectionStyleProvider mSectionStyleProvider;

    public RowAppearanceCoordinator(Context context, AssistantFeedbackController assistantFeedbackController, SectionStyleProvider sectionStyleProvider) {
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mSectionStyleProvider = sectionStyleProvider;
        this.mAlwaysExpandNonGroupedNotification = context.getResources().getBoolean(R.bool.config_alwaysExpandNonGroupedNotifications);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                NotificationEntry representativeEntry;
                RowAppearanceCoordinator rowAppearanceCoordinator = RowAppearanceCoordinator.this;
                rowAppearanceCoordinator.getClass();
                ListEntry listEntry = (ListEntry) CollectionsKt___CollectionsKt.firstOrNull(list);
                NotificationEntry notificationEntry = null;
                if (listEntry != null && (representativeEntry = listEntry.getRepresentativeEntry()) != null) {
                    Intrinsics.checkNotNull(representativeEntry.getSection());
                    Set set = rowAppearanceCoordinator.mSectionStyleProvider.lowPrioritySections;
                    if (set == null) {
                        set = null;
                    }
                    if (!set.contains(r1.sectioner)) {
                        notificationEntry = representativeEntry;
                    }
                }
                rowAppearanceCoordinator.entryToExpand = notificationEntry;
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderEntryListeners).add(new RowAppearanceCoordinator$attach$2(this));
    }
}
