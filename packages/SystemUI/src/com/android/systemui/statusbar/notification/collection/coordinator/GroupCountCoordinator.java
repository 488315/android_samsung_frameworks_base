package com.android.systemui.statusbar.notification.collection.coordinator;

import android.R;
import android.util.ArrayMap;
import android.util.Log;
import android.view.NotificationHeaderView;
import android.widget.TextView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda3;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class GroupCountCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final ArrayMap<GroupEntry, Integer> untruncatedChildCounts = new ArrayMap<>();

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
        Integer num = this.untruncatedChildCounts.get(groupEntry);
        if (num == null) {
            throw new IllegalStateException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No untruncated child count for group: ", groupEntry.mKey).toString());
        }
        int intValue = num.intValue();
        ExpandableNotificationRow expandableNotificationRow = ((ExpandableNotificationRowController) notifGroupController).mView;
        if (!expandableNotificationRow.mIsSummaryWithChildren) {
            Log.w("NotifRowController", "Called setUntruncatedChildCount(" + intValue + ") on a leaf row");
            return;
        }
        if (expandableNotificationRow.mChildrenContainer == null) {
            expandableNotificationRow.mChildrenContainerStub.inflate();
        }
        final NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        notificationChildrenContainer.mUntruncatedChildCount = intValue;
        notificationChildrenContainer.updateGroupOverflow();
        notificationChildrenContainer.mChildrenCountViews.stream().filter(new NotificationChildrenContainer$$ExternalSyntheticLambda3()).forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationChildrenContainer notificationChildrenContainer2 = NotificationChildrenContainer.this;
                SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationChildrenContainer.FROM_PARENT;
                notificationChildrenContainer2.getClass();
                ((TextView) obj).setText(String.format(Locale.getDefault(), "%d", Integer.valueOf(notificationChildrenContainer2.mUntruncatedChildCount)));
            }
        });
        NotificationHeaderView notificationHeaderView = notificationChildrenContainer.mMinimizedGroupHeader;
        if (notificationHeaderView != null) {
            ((TextView) notificationHeaderView.findViewById(R.id.find)).setText(String.format(Locale.getDefault(), "%d", Integer.valueOf(notificationChildrenContainer.mUntruncatedChildCount)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilter(List<? extends ListEntry> list) {
        this.untruncatedChildCounts.clear();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$onBeforeFinalizeFilter$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) filteringSequence$iterator$1.next();
            this.untruncatedChildCounts.put(groupEntry, Integer.valueOf(groupEntry.mUnmodifiableChildren.size()));
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends ListEntry> list) {
                GroupCountCoordinator.this.onBeforeFinalizeFilter(list);
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderGroupListeners).add(new OnAfterRenderGroupListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener
            public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
                GroupCountCoordinator.this.onAfterRenderGroup(groupEntry, notifGroupController);
            }
        });
    }
}
