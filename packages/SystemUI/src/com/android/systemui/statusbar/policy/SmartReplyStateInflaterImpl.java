package com.android.systemui.statusbar.policy;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;

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

    /* JADX WARN: Removed duplicated region for block: B:106:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x013d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0185  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyState inflateSmartReplyState(com.android.systemui.statusbar.notification.collection.NotificationEntry r18) {
        /*
            Method dump skipped, instructions count: 452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyState(com.android.systemui.statusbar.notification.collection.NotificationEntry):com.android.systemui.statusbar.policy.InflatedSmartReplyState");
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x012f, code lost:
    
        if (r0 != false) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x012c A[LOOP:0: B:66:0x008f->B:80:0x012c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x008b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(android.content.Context r17, android.content.Context r18, final com.android.systemui.statusbar.notification.collection.NotificationEntry r19, com.android.systemui.statusbar.policy.InflatedSmartReplyState r20, com.android.systemui.statusbar.policy.InflatedSmartReplyState r21) {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyViewHolder(android.content.Context, android.content.Context, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.policy.InflatedSmartReplyState, com.android.systemui.statusbar.policy.InflatedSmartReplyState):com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder");
    }
}
