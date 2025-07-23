package com.android.systemui.statusbar.policy;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SmartReplyStateInflaterImpl implements SmartReplyStateInflater {
    public final ActivityManagerWrapper activityManagerWrapper;
    public final SmartReplyConstants constants;
    public final DevicePolicyManagerWrapper devicePolicyManagerWrapper;
    public final PackageManagerWrapper packageManagerWrapper;
    public final SmartActionInflater smartActionsInflater;
    public final SmartReplyInflater smartRepliesInflater;

    public SmartReplyStateInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityManagerWrapper activityManagerWrapper, PackageManagerWrapper packageManagerWrapper, DevicePolicyManagerWrapper devicePolicyManagerWrapper, SmartReplyInflater smartReplyInflater, SmartActionInflater smartActionInflater) {
        this.constants = smartReplyConstants;
        this.activityManagerWrapper = activityManagerWrapper;
        this.packageManagerWrapper = packageManagerWrapper;
        this.devicePolicyManagerWrapper = devicePolicyManagerWrapper;
        this.smartRepliesInflater = smartReplyInflater;
        this.smartActionsInflater = smartActionInflater;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0193 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x011c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00e7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0164  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyState inflateSmartReplyState(com.android.systemui.statusbar.notification.collection.NotificationEntry r12) {
        /*
            Method dump skipped, instructions count: 418
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyState(com.android.systemui.statusbar.notification.collection.NotificationEntry):com.android.systemui.statusbar.policy.InflatedSmartReplyState");
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0127, code lost:
    
        if (r0 != false) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0124 A[LOOP:0: B:61:0x0087->B:75:0x0124, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0083 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(android.content.Context r17, android.content.Context r18, final com.android.systemui.statusbar.notification.collection.NotificationEntry r19, com.android.systemui.statusbar.policy.InflatedSmartReplyState r20, com.android.systemui.statusbar.policy.InflatedSmartReplyState r21) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyViewHolder(android.content.Context, android.content.Context, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.policy.InflatedSmartReplyState, com.android.systemui.statusbar.policy.InflatedSmartReplyState):com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder");
    }
}
