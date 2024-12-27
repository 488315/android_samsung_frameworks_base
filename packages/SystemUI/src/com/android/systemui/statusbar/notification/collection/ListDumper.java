package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
        sb.append(" isReaded=");
        sb.append(representativeEntry2.mIsReaded ? "true" : "false");
        NotificationEntry representativeEntry3 = listEntry.getRepresentativeEntry();
        Objects.requireNonNull(representativeEntry3);
        StringBuilder sb2 = new StringBuilder();
        if (!((ArrayList) representativeEntry3.mLifetimeExtenders).isEmpty()) {
            int size = ((ArrayList) representativeEntry3.mLifetimeExtenders).size();
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = ((NotifLifetimeExtender) ((ArrayList) representativeEntry3.mLifetimeExtenders).get(i)).getName();
            }
            sb2.append("lifetimeExtenders=");
            sb2.append(Arrays.toString(strArr));
            sb2.append(" ");
        }
        if (!((ArrayList) representativeEntry3.mDismissInterceptors).isEmpty()) {
            int size2 = ((ArrayList) representativeEntry3.mDismissInterceptors).size();
            String[] strArr2 = new String[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                strArr2[i2] = ((NotifDismissInterceptor) ((ArrayList) representativeEntry3.mDismissInterceptors).get(i2)).getName();
            }
            sb2.append("dismissInterceptors=");
            sb2.append(Arrays.toString(strArr2));
            sb2.append(" ");
        }
        ListAttachState listAttachState = representativeEntry3.mAttachState;
        ListEntry.checkNull(listAttachState);
        if (listAttachState.excludingFilter != null) {
            sb2.append("filter=");
            ListAttachState listAttachState2 = representativeEntry3.mAttachState;
            ListEntry.checkNull(listAttachState2);
            sb2.append(listAttachState2.excludingFilter.getName());
            sb2.append(" ");
        }
        ListAttachState listAttachState3 = representativeEntry3.mAttachState;
        ListEntry.checkNull(listAttachState3);
        if (listAttachState3.promoter != null) {
            sb2.append("promoter=");
            ListAttachState listAttachState4 = representativeEntry3.mAttachState;
            ListEntry.checkNull(listAttachState4);
            sb2.append(listAttachState4.promoter.getName());
            sb2.append(" ");
        }
        if (representativeEntry3.mCancellationReason != -1) {
            sb2.append("cancellationReason=");
            sb2.append(representativeEntry3.mCancellationReason);
            sb2.append(" ");
        }
        if (representativeEntry3.mDismissState != NotificationEntry.DismissState.NOT_DISMISSED) {
            sb2.append("dismissState=");
            sb2.append(representativeEntry3.mDismissState);
            sb2.append(" ");
        }
        ListAttachState listAttachState5 = representativeEntry3.mAttachState;
        ListEntry.checkNull(listAttachState5);
        if (listAttachState5.suppressedChanges.parent != null) {
            sb2.append("suppressedParent=");
            sb2.append(NotificationUtils.logKey(listAttachState5.suppressedChanges.parent));
            sb2.append(" ");
        }
        if (listAttachState5.suppressedChanges.section != null) {
            sb2.append("suppressedSection=");
            sb2.append(listAttachState5.suppressedChanges.section.label);
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
