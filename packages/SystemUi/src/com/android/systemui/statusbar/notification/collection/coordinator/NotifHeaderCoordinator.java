package com.android.systemui.statusbar.notification.collection.coordinator;

import android.R;
import android.view.NotificationHeaderView;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifHeaderCoordinator implements Coordinator {
    public final NotificationLockscreenUserManager lockscreenUserManager;

    public NotifHeaderCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.lockscreenUserManager = notificationLockscreenUserManager;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                NotificationHeaderView notificationHeaderView;
                NotifHeaderCoordinator notifHeaderCoordinator = NotifHeaderCoordinator.this;
                notifHeaderCoordinator.getClass();
                FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new NotifHeaderCoordinator$extractAllRepresentativeEntries$1(notifHeaderCoordinator)), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifHeaderCoordinator$onBeforeRenderListListener$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(((NotificationEntry) obj).rowExists());
                    }
                }));
                while (filteringSequence$iterator$1.hasNext()) {
                    NotificationEntry notificationEntry = (NotificationEntry) filteringSequence$iterator$1.next();
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) notifHeaderCoordinator.lockscreenUserManager;
                    boolean isLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(notificationLockscreenUserManagerImpl.mCurrentUserId);
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                    if (expandableNotificationRow.mIsGroupHeaderContainAtMark) {
                        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                        TextView textView = (notificationChildrenContainer == null || (notificationHeaderView = notificationChildrenContainer.mNotificationHeader) == null) ? null : (TextView) notificationHeaderView.findViewById(R.id.image);
                        if (textView != null) {
                            textView.setVisibility(isLockscreenPublicMode ? 8 : 0);
                        }
                    }
                    NotificationContentView[] notificationContentViewArr = expandableNotificationRow.mLayouts;
                    for (NotificationContentView notificationContentView : (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length)) {
                        if (notificationContentView.mIsContractedHeaderContainAtMark) {
                            View view = notificationContentView.mContractedChild;
                            TextView textView2 = view != null ? (TextView) view.findViewById(R.id.image) : null;
                            if (textView2 != null) {
                                textView2.setVisibility(isLockscreenPublicMode ? 8 : 0);
                            }
                        }
                        if (notificationContentView.mIsExpandedHeaderContainAtMark) {
                            View view2 = notificationContentView.mExpandedChild;
                            TextView textView3 = view2 != null ? (TextView) view2.findViewById(R.id.image) : null;
                            if (textView3 != null) {
                                textView3.setVisibility(isLockscreenPublicMode ? 8 : 0);
                            }
                        }
                    }
                }
            }
        });
    }
}
