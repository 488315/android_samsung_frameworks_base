package com.android.systemui.statusbar.notification.shared;

import android.graphics.drawable.Icon;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class ActiveNotificationModel extends ActiveNotificationEntryModel {
    public final Icon aodIcon;
    public final int bucket;
    public final String groupKey;
    public final Integer instanceId;
    public final boolean isAmbient;
    public final boolean isGroupSummary;
    public final boolean isLastMessageFromReply;
    public final boolean isPulsing;
    public final boolean isRowDismissed;
    public final boolean isSilent;
    public final boolean isSuppressedFromStatusBar;
    public final String key;
    public final String packageName;
    public final Icon shelfIcon;
    public final Icon statusBarIcon;
    public final int uid;

    public ActiveNotificationModel(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Icon icon, Icon icon2, Icon icon3, int i, String str3, Integer num, boolean z7, int i2) {
        super(null);
        this.key = str;
        this.groupKey = str2;
        this.isAmbient = z;
        this.isRowDismissed = z2;
        this.isSilent = z3;
        this.isLastMessageFromReply = z4;
        this.isSuppressedFromStatusBar = z5;
        this.isPulsing = z6;
        this.aodIcon = icon;
        this.shelfIcon = icon2;
        this.statusBarIcon = icon3;
        this.uid = i;
        this.packageName = str3;
        this.instanceId = num;
        this.isGroupSummary = z7;
        this.bucket = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationModel)) {
            return false;
        }
        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj;
        return Intrinsics.areEqual(this.key, activeNotificationModel.key) && Intrinsics.areEqual(this.groupKey, activeNotificationModel.groupKey) && this.isAmbient == activeNotificationModel.isAmbient && this.isRowDismissed == activeNotificationModel.isRowDismissed && this.isSilent == activeNotificationModel.isSilent && this.isLastMessageFromReply == activeNotificationModel.isLastMessageFromReply && this.isSuppressedFromStatusBar == activeNotificationModel.isSuppressedFromStatusBar && this.isPulsing == activeNotificationModel.isPulsing && Intrinsics.areEqual(this.aodIcon, activeNotificationModel.aodIcon) && Intrinsics.areEqual(this.shelfIcon, activeNotificationModel.shelfIcon) && Intrinsics.areEqual(this.statusBarIcon, activeNotificationModel.statusBarIcon) && this.uid == activeNotificationModel.uid && Intrinsics.areEqual(this.packageName, activeNotificationModel.packageName) && Intrinsics.areEqual(this.instanceId, activeNotificationModel.instanceId) && this.isGroupSummary == activeNotificationModel.isGroupSummary && this.bucket == activeNotificationModel.bucket;
    }

    public final int hashCode() {
        int hashCode = this.key.hashCode() * 31;
        String str = this.groupKey;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode + (str == null ? 0 : str.hashCode())) * 31, 31, this.isAmbient), 31, this.isRowDismissed), 31, this.isSilent), 31, this.isLastMessageFromReply), 31, this.isSuppressedFromStatusBar), 31, this.isPulsing);
        Icon icon = this.aodIcon;
        int hashCode2 = (m + (icon == null ? 0 : icon.hashCode())) * 31;
        Icon icon2 = this.shelfIcon;
        int hashCode3 = (hashCode2 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        Icon icon3 = this.statusBarIcon;
        int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.uid, (hashCode3 + (icon3 == null ? 0 : icon3.hashCode())) * 31, 31), 31, this.packageName);
        Integer num = this.instanceId;
        return Integer.hashCode(this.bucket) + TransitionData$$ExternalSyntheticOutline0.m((m2 + (num != null ? num.hashCode() : 0)) * 31, 31, this.isGroupSummary);
    }

    public final String toString() {
        Icon icon = this.aodIcon;
        Icon icon2 = this.shelfIcon;
        Icon icon3 = this.statusBarIcon;
        StringBuilder sb = new StringBuilder("ActiveNotificationModel(key=");
        sb.append(this.key);
        sb.append(", groupKey=");
        sb.append(this.groupKey);
        sb.append(", isAmbient=");
        sb.append(this.isAmbient);
        sb.append(", isRowDismissed=");
        sb.append(this.isRowDismissed);
        sb.append(", isSilent=");
        sb.append(this.isSilent);
        sb.append(", isLastMessageFromReply=");
        sb.append(this.isLastMessageFromReply);
        sb.append(", isSuppressedFromStatusBar=");
        sb.append(this.isSuppressedFromStatusBar);
        sb.append(", isPulsing=");
        sb.append(this.isPulsing);
        sb.append(", aodIcon=");
        sb.append(icon);
        sb.append(", shelfIcon=");
        sb.append(icon2);
        sb.append(", statusBarIcon=");
        sb.append(icon3);
        sb.append(", uid=");
        sb.append(this.uid);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", instanceId=");
        sb.append(this.instanceId);
        sb.append(", isGroupSummary=");
        sb.append(this.isGroupSummary);
        sb.append(", bucket=");
        return Anchor$$ExternalSyntheticOutline0.m(this.bucket, ")", sb);
    }
}
