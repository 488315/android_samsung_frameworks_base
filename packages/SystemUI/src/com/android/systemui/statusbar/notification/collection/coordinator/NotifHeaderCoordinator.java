package com.android.systemui.statusbar.notification.collection.coordinator;

import android.R;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifHeaderCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final NotificationLockscreenUserManager lockscreenUserManager;

    public NotifHeaderCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence extractAllRepresentativeEntries(List<? extends PipelineEntry> list) {
        return SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new NotifHeaderCoordinator$extractAllRepresentativeEntries$1(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderListListener(List<? extends PipelineEntry> list) {
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(extractAllRepresentativeEntries(list), new NotifHeaderCoordinator$$ExternalSyntheticLambda0()));
        while (filteringSequence$iterator$1.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) filteringSequence$iterator$1.next();
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            boolean isLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            expandableNotificationRow.getClass();
            NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
            ArrayIterator arrayIterator = new ArrayIterator((NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length));
            while (arrayIterator.hasNext()) {
                NotificationContentView notificationContentView = (NotificationContentView) arrayIterator.next();
                if (notificationContentView.mIsContractedHeaderContainAtMark) {
                    View view = notificationContentView.mContractedChild;
                    TextView textView = view != null ? (TextView) view.findViewById(R.id.inter_word) : null;
                    if (textView != null) {
                        textView.setVisibility(isLockscreenPublicMode ? 8 : 0);
                    }
                }
                if (notificationContentView.mIsExpandedHeaderContainAtMark) {
                    View view2 = notificationContentView.mExpandedChild;
                    TextView textView2 = view2 != null ? (TextView) view2.findViewById(R.id.inter_word) : null;
                    if (textView2 != null) {
                        textView2.setVisibility(isLockscreenPublicMode ? 8 : 0);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List<? extends PipelineEntry> list) {
                NotifHeaderCoordinator.this.onBeforeRenderListListener(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Sequence extractAllRepresentativeEntries(PipelineEntry pipelineEntry) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new NotifHeaderCoordinator$extractAllRepresentativeEntries$2(pipelineEntry, this, null));
    }
}
