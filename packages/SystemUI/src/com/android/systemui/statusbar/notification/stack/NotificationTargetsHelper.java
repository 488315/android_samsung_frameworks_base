package com.android.systemui.statusbar.notification.stack;

import androidx.core.view.ViewGroupKt$children$1;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class NotificationTargetsHelper {
    public static RoundableTargets findRoundableTargets(ExpandableNotificationRow expandableNotificationRow, NotificationStackScrollLayout notificationStackScrollLayout, NotificationSectionsManager notificationSectionsManager) {
        Roundable roundable;
        ExpandableView expandableView;
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
        ExpandableView expandableView2 = null;
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2 != null ? expandableNotificationRow2.mChildrenContainer : null;
        List list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new ViewGroupKt$children$1(notificationStackScrollLayout), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper$findRoundableTargets$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(obj instanceof ExpandableView);
            }
        }), new NotificationTargetsHelper$$ExternalSyntheticLambda0(1)));
        if (expandableNotificationRow2 == null || notificationChildrenContainer == null) {
            int iIndexOf = list.indexOf(expandableNotificationRow);
            ExpandableView expandableView3 = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(iIndexOf - 1, list);
            roundable = (expandableView3 == null || notificationSectionsManager.beginsSection(expandableNotificationRow, expandableView3)) ? null : expandableView3;
            ExpandableView expandableView4 = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(iIndexOf + 1, list);
            if (expandableView4 != null && !notificationSectionsManager.beginsSection(expandableView4, expandableNotificationRow)) {
                expandableView2 = expandableView4;
            }
            expandableView = expandableView2;
        } else {
            List list2 = notificationChildrenContainer.mAttachedChildren;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list2;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) obj;
                expandableNotificationRow3.getClass();
                if (expandableNotificationRow3.getVisibility() == 0) {
                    arrayList.add(obj);
                }
            }
            int iIndexOf2 = arrayList.indexOf(expandableNotificationRow);
            roundable = (ExpandableNotificationRow) CollectionsKt___CollectionsKt.getOrNull(iIndexOf2 - 1, arrayList);
            if (roundable == null) {
                roundable = notificationChildrenContainer.mGroupHeaderWrapper;
            }
            expandableView = (ExpandableNotificationRow) CollectionsKt___CollectionsKt.getOrNull(iIndexOf2 + 1, arrayList);
            if (expandableView == null) {
                expandableView = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(list.indexOf(expandableNotificationRow2) + 1, list);
            }
        }
        return new RoundableTargets(roundable, expandableNotificationRow, expandableView);
    }

    public static boolean isValidMagneticBoundary(ExpandableView expandableView) {
        return (expandableView instanceof NotificationShelf) || (expandableView instanceof SectionHeaderView);
    }
}
