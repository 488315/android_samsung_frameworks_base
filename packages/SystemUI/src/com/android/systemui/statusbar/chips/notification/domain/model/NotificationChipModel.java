package com.android.systemui.statusbar.chips.notification.domain.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationChipModel {
    public final String appName;
    public final long creationTime;
    public final InstanceId instanceId;
    public final boolean isAppVisible;
    public final String key;
    public final Long lastAppVisibleTime;
    public final PromotedNotificationContentModels promotedContent;
    public final StatusBarIconView statusBarChipIconView;

    public NotificationChipModel(String str, String str2, StatusBarIconView statusBarIconView, PromotedNotificationContentModels promotedNotificationContentModels, long j, boolean z, Long l, InstanceId instanceId) {
        this.key = str;
        this.appName = str2;
        this.statusBarChipIconView = statusBarIconView;
        this.promotedContent = promotedNotificationContentModels;
        this.creationTime = j;
        this.isAppVisible = z;
        this.lastAppVisibleTime = l;
        this.instanceId = instanceId;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationChipModel)) {
            return false;
        }
        NotificationChipModel notificationChipModel = (NotificationChipModel) obj;
        return Intrinsics.areEqual(this.key, notificationChipModel.key) && Intrinsics.areEqual(this.appName, notificationChipModel.appName) && Intrinsics.areEqual(this.statusBarChipIconView, notificationChipModel.statusBarChipIconView) && Intrinsics.areEqual(this.promotedContent, notificationChipModel.promotedContent) && this.creationTime == notificationChipModel.creationTime && this.isAppVisible == notificationChipModel.isAppVisible && Intrinsics.areEqual(this.lastAppVisibleTime, notificationChipModel.lastAppVisibleTime) && Intrinsics.areEqual(this.instanceId, notificationChipModel.instanceId);
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.appName);
        StatusBarIconView statusBarIconView = this.statusBarChipIconView;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m((this.promotedContent.hashCode() + ((m + (statusBarIconView == null ? 0 : statusBarIconView.hashCode())) * 31)) * 31, 31, this.creationTime), 31, this.isAppVisible);
        Long l = this.lastAppVisibleTime;
        int hashCode = (m2 + (l == null ? 0 : l.hashCode())) * 31;
        InstanceId instanceId = this.instanceId;
        return hashCode + (instanceId != null ? instanceId.hashCode() : 0);
    }

    public final String toString() {
        return "NotificationChipModel(key=" + this.key + ", appName=" + this.appName + ", statusBarChipIconView=" + this.statusBarChipIconView + ", promotedContent=" + this.promotedContent + ", creationTime=" + this.creationTime + ", isAppVisible=" + this.isAppVisible + ", lastAppVisibleTime=" + this.lastAppVisibleTime + ", instanceId=" + this.instanceId + ")";
    }
}
