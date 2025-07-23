package com.android.systemui.statusbar.notification.collection.coordinator;

import android.R;
import android.util.ArrayMap;
import android.util.Log;
import android.view.NotificationHeaderView;
import android.widget.TextView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.BundleEntry;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.sequences.FlatteningSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
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
        notificationChildrenContainer.mChildrenCountViews.stream().filter(new NotificationChildrenContainer$$ExternalSyntheticLambda4()).forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda5
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
            ((TextView) notificationHeaderView.findViewById(R.id.floating_popup_container)).setText(String.format(Locale.getDefault(), "%d", Integer.valueOf(notificationChildrenContainer.mUntruncatedChildCount)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilter(List<? extends PipelineEntry> list) {
        this.untruncatedChildCounts.clear();
        FlatteningSequence$iterator$1 flatteningSequence$iterator$1 = new FlatteningSequence$iterator$1(SequencesKt___SequencesKt.flatMapIterable(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new GroupCountCoordinator$$ExternalSyntheticLambda0()));
        while (flatteningSequence$iterator$1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) flatteningSequence$iterator$1.next();
            this.untruncatedChildCounts.put(groupEntry, Integer.valueOf(groupEntry.mUnmodifiableChildren.size()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable onBeforeFinalizeFilter$lambda$0(PipelineEntry pipelineEntry) {
        Iterable iterable;
        if (pipelineEntry instanceof GroupEntry) {
            iterable = Collections.singletonList(pipelineEntry);
        } else if (pipelineEntry instanceof BundleEntry) {
            List list = ((BundleEntry) pipelineEntry).children;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (obj instanceof GroupEntry) {
                    arrayList.add(obj);
                }
            }
            iterable = arrayList;
        } else {
            iterable = EmptyList.INSTANCE;
        }
        return iterable;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends PipelineEntry> list) {
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
