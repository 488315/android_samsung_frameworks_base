package com.android.systemui.statusbar.notification.shared;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActiveNotificationModel extends ActiveNotificationEntryModel {
    public final Icon aodIcon;
    public final String appName;
    public final int bucket;
    public final int callChipColor;
    public final CallType callType;
    public final PendingIntent contentIntent;
    public final int extraVisibleFlag;
    public final String groupKey;
    public final InstanceId instanceId;
    public final boolean isAmbient;
    public final boolean isCallChipNotNeeded;
    public final boolean isForegroundService;
    public final boolean isGroupSummary;
    public final boolean isLastMessageFromReply;
    public final boolean isOngoingEvent;
    public final boolean isPulsing;
    public final boolean isRowDismissed;
    public final boolean isSilent;
    public final boolean isSuppressedFromStatusBar;
    public final String key;
    public final String packageName;
    public final PromotedNotificationContentModels promotedContent;
    public final Icon shelfIcon;
    public final StatusBarIconView statusBarChipIconView;
    public final Icon statusBarIcon;
    public final int uid;
    public final long whenTime;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ActiveNotificationModel(String str, String str2, long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, Icon icon, Icon icon2, Icon icon3, StatusBarIconView statusBarIconView, int i, String str3, String str4, PendingIntent pendingIntent, InstanceId instanceId, boolean z9, int i2, CallType callType, PromotedNotificationContentModels promotedNotificationContentModels, int i3, int i4, boolean z10) {
        super(null);
        this.key = str;
        this.groupKey = str2;
        this.whenTime = j;
        this.isForegroundService = z;
        this.isOngoingEvent = z2;
        this.isAmbient = z3;
        this.isRowDismissed = z4;
        this.isSilent = z5;
        this.isLastMessageFromReply = z6;
        this.isSuppressedFromStatusBar = z7;
        this.isPulsing = z8;
        this.aodIcon = icon;
        this.shelfIcon = icon2;
        this.statusBarIcon = icon3;
        this.statusBarChipIconView = statusBarIconView;
        this.uid = i;
        this.packageName = str3;
        this.appName = str4;
        this.contentIntent = pendingIntent;
        this.instanceId = instanceId;
        this.isGroupSummary = z9;
        this.bucket = i2;
        this.callType = callType;
        this.promotedContent = promotedNotificationContentModels;
        this.callChipColor = i3;
        this.extraVisibleFlag = i4;
        this.isCallChipNotNeeded = z10;
        PromotedNotificationContentModel.Companion.getClass();
        if (promotedNotificationContentModels != null) {
            Log.e("ActiveNotificationEntryModel", "passing non-null promoted content without feature flag enabled");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationModel)) {
            return false;
        }
        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj;
        return Intrinsics.areEqual(this.key, activeNotificationModel.key) && Intrinsics.areEqual(this.groupKey, activeNotificationModel.groupKey) && this.whenTime == activeNotificationModel.whenTime && this.isForegroundService == activeNotificationModel.isForegroundService && this.isOngoingEvent == activeNotificationModel.isOngoingEvent && this.isAmbient == activeNotificationModel.isAmbient && this.isRowDismissed == activeNotificationModel.isRowDismissed && this.isSilent == activeNotificationModel.isSilent && this.isLastMessageFromReply == activeNotificationModel.isLastMessageFromReply && this.isSuppressedFromStatusBar == activeNotificationModel.isSuppressedFromStatusBar && this.isPulsing == activeNotificationModel.isPulsing && Intrinsics.areEqual(this.aodIcon, activeNotificationModel.aodIcon) && Intrinsics.areEqual(this.shelfIcon, activeNotificationModel.shelfIcon) && Intrinsics.areEqual(this.statusBarIcon, activeNotificationModel.statusBarIcon) && Intrinsics.areEqual(this.statusBarChipIconView, activeNotificationModel.statusBarChipIconView) && this.uid == activeNotificationModel.uid && Intrinsics.areEqual(this.packageName, activeNotificationModel.packageName) && Intrinsics.areEqual(this.appName, activeNotificationModel.appName) && Intrinsics.areEqual(this.contentIntent, activeNotificationModel.contentIntent) && Intrinsics.areEqual(this.instanceId, activeNotificationModel.instanceId) && this.isGroupSummary == activeNotificationModel.isGroupSummary && this.bucket == activeNotificationModel.bucket && this.callType == activeNotificationModel.callType && Intrinsics.areEqual(this.promotedContent, activeNotificationModel.promotedContent) && this.callChipColor == activeNotificationModel.callChipColor && this.extraVisibleFlag == activeNotificationModel.extraVisibleFlag && this.isCallChipNotNeeded == activeNotificationModel.isCallChipNotNeeded;
    }

    public final int hashCode() {
        int hashCode = this.key.hashCode() * 31;
        String str = this.groupKey;
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m((hashCode + (str == null ? 0 : str.hashCode())) * 31, 31, this.whenTime), 31, this.isForegroundService), 31, this.isOngoingEvent), 31, this.isAmbient), 31, this.isRowDismissed), 31, this.isSilent), 31, this.isLastMessageFromReply), 31, this.isSuppressedFromStatusBar), 31, this.isPulsing);
        Icon icon = this.aodIcon;
        int hashCode2 = (m + (icon == null ? 0 : icon.hashCode())) * 31;
        Icon icon2 = this.shelfIcon;
        int hashCode3 = (hashCode2 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        Icon icon3 = this.statusBarIcon;
        int hashCode4 = (hashCode3 + (icon3 == null ? 0 : icon3.hashCode())) * 31;
        StatusBarIconView statusBarIconView = this.statusBarChipIconView;
        int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.uid, (hashCode4 + (statusBarIconView == null ? 0 : statusBarIconView.hashCode())) * 31, 31), 31, this.packageName), 31, this.appName);
        PendingIntent pendingIntent = this.contentIntent;
        int hashCode5 = (m2 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        InstanceId instanceId = this.instanceId;
        int hashCode6 = (this.callType.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.bucket, TransitionData$$ExternalSyntheticOutline0.m((hashCode5 + (instanceId == null ? 0 : instanceId.hashCode())) * 31, 31, this.isGroupSummary), 31)) * 31;
        PromotedNotificationContentModels promotedNotificationContentModels = this.promotedContent;
        return Boolean.hashCode(this.isCallChipNotNeeded) + ReorderTile$$ExternalSyntheticOutline0.m(this.extraVisibleFlag, ReorderTile$$ExternalSyntheticOutline0.m(this.callChipColor, (hashCode6 + (promotedNotificationContentModels != null ? promotedNotificationContentModels.hashCode() : 0)) * 31, 31), 31);
    }

    public final String toString() {
        Icon icon = this.aodIcon;
        Icon icon2 = this.shelfIcon;
        Icon icon3 = this.statusBarIcon;
        PendingIntent pendingIntent = this.contentIntent;
        InstanceId instanceId = this.instanceId;
        StringBuilder sb = new StringBuilder("ActiveNotificationModel(key=");
        sb.append(this.key);
        sb.append(", groupKey=");
        sb.append(this.groupKey);
        sb.append(", whenTime=");
        sb.append(this.whenTime);
        sb.append(", isForegroundService=");
        sb.append(this.isForegroundService);
        sb.append(", isOngoingEvent=");
        sb.append(this.isOngoingEvent);
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
        sb.append(", statusBarChipIconView=");
        sb.append(this.statusBarChipIconView);
        sb.append(", uid=");
        sb.append(this.uid);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", appName=");
        sb.append(this.appName);
        sb.append(", contentIntent=");
        sb.append(pendingIntent);
        sb.append(", instanceId=");
        sb.append(instanceId);
        sb.append(", isGroupSummary=");
        sb.append(this.isGroupSummary);
        sb.append(", bucket=");
        sb.append(this.bucket);
        sb.append(", callType=");
        sb.append(this.callType);
        sb.append(", promotedContent=");
        sb.append(this.promotedContent);
        sb.append(", callChipColor=");
        sb.append(this.callChipColor);
        sb.append(", extraVisibleFlag=");
        sb.append(this.extraVisibleFlag);
        sb.append(", isCallChipNotNeeded=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCallChipNotNeeded, ")");
    }
}
