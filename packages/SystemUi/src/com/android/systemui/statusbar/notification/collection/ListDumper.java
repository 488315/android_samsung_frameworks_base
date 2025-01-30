package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ListDumper {
    public static void dumpEntry(ListEntry listEntry, String str, String str2, StringBuilder sb, boolean z, boolean z2) {
        sb.append(str2);
        sb.append("[");
        sb.append(str);
        sb.append("] ");
        sb.append(str.length() == 1 ? " " : "");
        sb.append(NotificationUtils.logKey(listEntry));
        if (z) {
            sb.append(" (parent=");
            sb.append(NotificationUtils.logKey(listEntry.getParent()));
            sb.append(")");
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                sb.append(" rank=");
                sb.append(representativeEntry.mRanking.getRank());
            }
        }
        if (listEntry.getSection() != null) {
            sb.append(" section=");
            sb.append(listEntry.getSection().label);
        }
        NotificationEntry representativeEntry2 = listEntry.getRepresentativeEntry();
        Objects.requireNonNull(representativeEntry2);
        StringBuilder sb2 = new StringBuilder();
        List list = representativeEntry2.mLifetimeExtenders;
        if (!((ArrayList) list).isEmpty()) {
            ArrayList arrayList = (ArrayList) list;
            int size = arrayList.size();
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = ((NotifLifetimeExtender) arrayList.get(i)).getName();
            }
            sb2.append("lifetimeExtenders=");
            sb2.append(Arrays.toString(strArr));
            sb2.append(" ");
        }
        ArrayList arrayList2 = (ArrayList) representativeEntry2.mDismissInterceptors;
        if (!arrayList2.isEmpty()) {
            int size2 = arrayList2.size();
            String[] strArr2 = new String[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                ((BubbleCoordinator.C27992) arrayList2.get(i2)).getClass();
                strArr2[i2] = "BubbleCoordinator";
            }
            sb2.append("dismissInterceptors=");
            sb2.append(Arrays.toString(strArr2));
            sb2.append(" ");
        }
        ListAttachState listAttachState = representativeEntry2.mAttachState;
        ListEntry.checkNull(listAttachState);
        if (listAttachState.excludingFilter != null) {
            sb2.append("filter=");
            ListEntry.checkNull(listAttachState);
            sb2.append(listAttachState.excludingFilter.mName);
            sb2.append(" ");
        }
        ListEntry.checkNull(listAttachState);
        if (listAttachState.promoter != null) {
            sb2.append("promoter=");
            ListEntry.checkNull(listAttachState);
            sb2.append(listAttachState.promoter.mName);
            sb2.append(" ");
        }
        if (representativeEntry2.mCancellationReason != -1) {
            sb2.append("cancellationReason=");
            sb2.append(representativeEntry2.mCancellationReason);
            sb2.append(" ");
        }
        if (representativeEntry2.mDismissState != NotificationEntry.DismissState.NOT_DISMISSED) {
            sb2.append("dismissState=");
            sb2.append(representativeEntry2.mDismissState);
            sb2.append(" ");
        }
        ListEntry.checkNull(listAttachState);
        GroupEntry groupEntry = listAttachState.suppressedChanges.parent;
        SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
        if (groupEntry != null) {
            sb2.append("suppressedParent=");
            sb2.append(NotificationUtils.logKey(suppressedAttachState.parent));
            sb2.append(" ");
        }
        if (suppressedAttachState.section != null) {
            sb2.append("suppressedSection=");
            sb2.append(suppressedAttachState.section.label);
            sb2.append(" ");
        }
        if (z2) {
            sb2.append("interacted=yes ");
        }
        String sb3 = sb2.toString();
        if (!sb3.isEmpty()) {
            sb.append("\n\t");
            sb.append(str2);
            sb.append(sb3);
        }
        sb.append("\n");
    }
}
