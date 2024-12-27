package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.widget.RemoteViews;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class OngoingActivityData {
    public final ArrayList mActionBgColors;
    public final int mActionType;
    public final ArrayList mActions;
    public final Icon mAppIcon;
    public final String mAppName;
    public final Icon mCardIcon;
    public final Integer mCardIconBg;
    public final int mChipBackground;
    public final Icon mChipIcon;
    public final int mChronometerPosition;
    public final String mChronometerTag;
    public final RemoteViews mChronometerView;
    public final RemoteViews mCustomExpandedCardView;
    public final RemoteViews mCustomNowBarView;
    public final String mDescription;
    public final CharSequence mExpandedChipText;
    public final RemoteViews mExpandedChipView;
    public final Icon mFirstIcon;
    public Boolean mIsNew;
    public final String mNotiID;
    public final NotificationEntry mNotificationEntry;
    public final int mNowbarChronometerPosition;
    public final Icon mNowbarIcon;
    public final String mNowbarPrimaryInfo;
    public final String mNowbarSecondaryInfo;
    public RemoteViews mOngoingCollapsedView;
    public RemoteViews mOngoingExpandView;
    public RemoteViews mOngoingNowbarView;
    public final String mPackageName;
    public final PendingIntent mPendingIntent;
    public final int mPrimaryActionIndex;
    public final String mPrimaryInfo;
    public final int mProgress;
    public final int mProgressColor;
    public final boolean mProgressIndeterminate;
    public final int mProgressMax;
    public final Icon mProgressSegmentIcon;
    public final Parcelable[] mProgressSegments;
    public final Icon mSecondIcon;
    public final String mSecondaryInfo;
    public final Icon mSecondaryInfoIcon;
    public final int mStyle;
    public final int mUserId;
    public final Long mWhen;

    public OngoingActivityData(String str, PendingIntent pendingIntent, Icon icon, RemoteViews remoteViews, int i, int i2, int i3, ArrayList<Notification.Action> arrayList, ArrayList<Integer> arrayList2, Icon icon2, Integer num, String str2, String str3, RemoteViews remoteViews2, Boolean bool, CharSequence charSequence, RemoteViews remoteViews3, int i4, String str4, int i5, int i6, boolean z, Icon icon3, Icon icon4, RemoteViews remoteViews4, RemoteViews remoteViews5, RemoteViews remoteViews6, RemoteViews remoteViews7, Parcelable[] parcelableArr, Icon icon5, int i7, String str5, Icon icon6, Icon icon7, String str6, String str7, int i8, String str8, Long l, Icon icon8, String str9, NotificationEntry notificationEntry, int i9, int i10) {
        this.mNotiID = str;
        this.mPendingIntent = pendingIntent;
        this.mChipIcon = icon;
        this.mExpandedChipView = remoteViews;
        this.mChipBackground = i;
        this.mPrimaryActionIndex = i2;
        this.mActionType = i3;
        this.mActions = arrayList;
        this.mActionBgColors = arrayList2;
        this.mCardIcon = icon2;
        this.mCardIconBg = num;
        this.mPrimaryInfo = str2;
        this.mSecondaryInfo = str3;
        this.mCustomExpandedCardView = remoteViews2;
        this.mIsNew = bool;
        this.mExpandedChipText = charSequence;
        this.mChronometerView = remoteViews3;
        this.mChronometerPosition = i4;
        this.mChronometerTag = str4;
        this.mProgress = i5;
        this.mProgressMax = i6;
        this.mProgressIndeterminate = z;
        this.mFirstIcon = icon3;
        this.mSecondIcon = icon4;
        this.mOngoingCollapsedView = remoteViews4;
        this.mOngoingExpandView = remoteViews5;
        this.mOngoingNowbarView = remoteViews6;
        this.mCustomNowBarView = remoteViews7;
        this.mProgressSegments = parcelableArr;
        this.mProgressSegmentIcon = icon5;
        this.mProgressColor = i7;
        this.mDescription = str5;
        this.mSecondaryInfoIcon = icon6;
        this.mNowbarIcon = icon7;
        this.mNowbarPrimaryInfo = str6;
        this.mNowbarSecondaryInfo = str7;
        this.mUserId = i8;
        this.mAppName = str8;
        this.mWhen = l;
        this.mAppIcon = icon8;
        this.mPackageName = str9;
        this.mNotificationEntry = notificationEntry;
        this.mStyle = i9;
        this.mNowbarChronometerPosition = i10;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OngoingActivityData)) {
            return false;
        }
        OngoingActivityData ongoingActivityData = (OngoingActivityData) obj;
        return Intrinsics.areEqual(this.mNotiID, ongoingActivityData.mNotiID) && Intrinsics.areEqual(this.mPendingIntent, ongoingActivityData.mPendingIntent) && Intrinsics.areEqual(this.mChipIcon, ongoingActivityData.mChipIcon) && Intrinsics.areEqual(this.mExpandedChipView, ongoingActivityData.mExpandedChipView) && this.mChipBackground == ongoingActivityData.mChipBackground && this.mPrimaryActionIndex == ongoingActivityData.mPrimaryActionIndex && this.mActionType == ongoingActivityData.mActionType && Intrinsics.areEqual(this.mActions, ongoingActivityData.mActions) && Intrinsics.areEqual(this.mActionBgColors, ongoingActivityData.mActionBgColors) && Intrinsics.areEqual(this.mCardIcon, ongoingActivityData.mCardIcon) && Intrinsics.areEqual(this.mCardIconBg, ongoingActivityData.mCardIconBg) && Intrinsics.areEqual(this.mPrimaryInfo, ongoingActivityData.mPrimaryInfo) && Intrinsics.areEqual(this.mSecondaryInfo, ongoingActivityData.mSecondaryInfo) && Intrinsics.areEqual(this.mCustomExpandedCardView, ongoingActivityData.mCustomExpandedCardView) && Intrinsics.areEqual(this.mIsNew, ongoingActivityData.mIsNew) && Intrinsics.areEqual(this.mExpandedChipText, ongoingActivityData.mExpandedChipText) && Intrinsics.areEqual(this.mChronometerView, ongoingActivityData.mChronometerView) && this.mChronometerPosition == ongoingActivityData.mChronometerPosition && Intrinsics.areEqual(this.mChronometerTag, ongoingActivityData.mChronometerTag) && this.mProgress == ongoingActivityData.mProgress && this.mProgressMax == ongoingActivityData.mProgressMax && this.mProgressIndeterminate == ongoingActivityData.mProgressIndeterminate && Intrinsics.areEqual(this.mFirstIcon, ongoingActivityData.mFirstIcon) && Intrinsics.areEqual(this.mSecondIcon, ongoingActivityData.mSecondIcon) && Intrinsics.areEqual(this.mOngoingCollapsedView, ongoingActivityData.mOngoingCollapsedView) && Intrinsics.areEqual(this.mOngoingExpandView, ongoingActivityData.mOngoingExpandView) && Intrinsics.areEqual(this.mOngoingNowbarView, ongoingActivityData.mOngoingNowbarView) && Intrinsics.areEqual(this.mCustomNowBarView, ongoingActivityData.mCustomNowBarView) && Intrinsics.areEqual(this.mProgressSegments, ongoingActivityData.mProgressSegments) && Intrinsics.areEqual(this.mProgressSegmentIcon, ongoingActivityData.mProgressSegmentIcon) && this.mProgressColor == ongoingActivityData.mProgressColor && Intrinsics.areEqual(this.mDescription, ongoingActivityData.mDescription) && Intrinsics.areEqual(this.mSecondaryInfoIcon, ongoingActivityData.mSecondaryInfoIcon) && Intrinsics.areEqual(this.mNowbarIcon, ongoingActivityData.mNowbarIcon) && Intrinsics.areEqual(this.mNowbarPrimaryInfo, ongoingActivityData.mNowbarPrimaryInfo) && Intrinsics.areEqual(this.mNowbarSecondaryInfo, ongoingActivityData.mNowbarSecondaryInfo) && this.mUserId == ongoingActivityData.mUserId && Intrinsics.areEqual(this.mAppName, ongoingActivityData.mAppName) && Intrinsics.areEqual(this.mWhen, ongoingActivityData.mWhen) && Intrinsics.areEqual(this.mAppIcon, ongoingActivityData.mAppIcon) && Intrinsics.areEqual(this.mPackageName, ongoingActivityData.mPackageName) && Intrinsics.areEqual(this.mNotificationEntry, ongoingActivityData.mNotificationEntry) && this.mStyle == ongoingActivityData.mStyle && this.mNowbarChronometerPosition == ongoingActivityData.mNowbarChronometerPosition;
    }

    public final int hashCode() {
        int hashCode = this.mNotiID.hashCode() * 31;
        PendingIntent pendingIntent = this.mPendingIntent;
        int hashCode2 = (hashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        Icon icon = this.mChipIcon;
        int hashCode3 = (hashCode2 + (icon == null ? 0 : icon.hashCode())) * 31;
        RemoteViews remoteViews = this.mExpandedChipView;
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mActionType, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mPrimaryActionIndex, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mChipBackground, (hashCode3 + (remoteViews == null ? 0 : remoteViews.hashCode())) * 31, 31), 31), 31);
        ArrayList arrayList = this.mActions;
        int hashCode4 = (m + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        ArrayList arrayList2 = this.mActionBgColors;
        int hashCode5 = (hashCode4 + (arrayList2 == null ? 0 : arrayList2.hashCode())) * 31;
        Icon icon2 = this.mCardIcon;
        int hashCode6 = (hashCode5 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        Integer num = this.mCardIconBg;
        int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode6 + (num == null ? 0 : num.hashCode())) * 31, 31, this.mPrimaryInfo), 31, this.mSecondaryInfo);
        RemoteViews remoteViews2 = this.mCustomExpandedCardView;
        int hashCode7 = (m2 + (remoteViews2 == null ? 0 : remoteViews2.hashCode())) * 31;
        Boolean bool = this.mIsNew;
        int hashCode8 = (hashCode7 + (bool == null ? 0 : bool.hashCode())) * 31;
        CharSequence charSequence = this.mExpandedChipText;
        int hashCode9 = (hashCode8 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        RemoteViews remoteViews3 = this.mChronometerView;
        int m3 = TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mProgressMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mProgress, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mChronometerPosition, (hashCode9 + (remoteViews3 == null ? 0 : remoteViews3.hashCode())) * 31, 31), 31, this.mChronometerTag), 31), 31), 31, this.mProgressIndeterminate);
        Icon icon3 = this.mFirstIcon;
        int hashCode10 = (m3 + (icon3 == null ? 0 : icon3.hashCode())) * 31;
        Icon icon4 = this.mSecondIcon;
        int hashCode11 = (hashCode10 + (icon4 == null ? 0 : icon4.hashCode())) * 31;
        RemoteViews remoteViews4 = this.mOngoingCollapsedView;
        int hashCode12 = (hashCode11 + (remoteViews4 == null ? 0 : remoteViews4.hashCode())) * 31;
        RemoteViews remoteViews5 = this.mOngoingExpandView;
        int hashCode13 = (hashCode12 + (remoteViews5 == null ? 0 : remoteViews5.hashCode())) * 31;
        RemoteViews remoteViews6 = this.mOngoingNowbarView;
        int hashCode14 = (hashCode13 + (remoteViews6 == null ? 0 : remoteViews6.hashCode())) * 31;
        RemoteViews remoteViews7 = this.mCustomNowBarView;
        int hashCode15 = (((hashCode14 + (remoteViews7 == null ? 0 : remoteViews7.hashCode())) * 31) + Arrays.hashCode(this.mProgressSegments)) * 31;
        Icon icon5 = this.mProgressSegmentIcon;
        int m4 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mProgressColor, (hashCode15 + (icon5 == null ? 0 : icon5.hashCode())) * 31, 31), 31, this.mDescription);
        Icon icon6 = this.mSecondaryInfoIcon;
        int hashCode16 = (m4 + (icon6 == null ? 0 : icon6.hashCode())) * 31;
        Icon icon7 = this.mNowbarIcon;
        int hashCode17 = (hashCode16 + (icon7 == null ? 0 : icon7.hashCode())) * 31;
        String str = this.mNowbarPrimaryInfo;
        int hashCode18 = (hashCode17 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mNowbarSecondaryInfo;
        int m5 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mUserId, (hashCode18 + (str2 == null ? 0 : str2.hashCode())) * 31, 31), 31, this.mAppName);
        Long l = this.mWhen;
        int hashCode19 = (m5 + (l == null ? 0 : l.hashCode())) * 31;
        Icon icon8 = this.mAppIcon;
        return Integer.hashCode(this.mNowbarChronometerPosition) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mStyle, (this.mNotificationEntry.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode19 + (icon8 != null ? icon8.hashCode() : 0)) * 31, 31, this.mPackageName)) * 31, 31);
    }

    public final String toString() {
        PendingIntent pendingIntent = this.mPendingIntent;
        Icon icon = this.mChipIcon;
        RemoteViews remoteViews = this.mExpandedChipView;
        ArrayList arrayList = this.mActions;
        ArrayList arrayList2 = this.mActionBgColors;
        Icon icon2 = this.mCardIcon;
        RemoteViews remoteViews2 = this.mCustomExpandedCardView;
        Boolean bool = this.mIsNew;
        CharSequence charSequence = this.mExpandedChipText;
        RemoteViews remoteViews3 = this.mChronometerView;
        Icon icon3 = this.mFirstIcon;
        Icon icon4 = this.mSecondIcon;
        RemoteViews remoteViews4 = this.mOngoingCollapsedView;
        RemoteViews remoteViews5 = this.mOngoingExpandView;
        RemoteViews remoteViews6 = this.mOngoingNowbarView;
        RemoteViews remoteViews7 = this.mCustomNowBarView;
        String arrays = Arrays.toString(this.mProgressSegments);
        Icon icon5 = this.mProgressSegmentIcon;
        Icon icon6 = this.mSecondaryInfoIcon;
        Icon icon7 = this.mNowbarIcon;
        Icon icon8 = this.mAppIcon;
        StringBuilder sb = new StringBuilder("OngoingActivityData(mNotiID=");
        sb.append(this.mNotiID);
        sb.append(", mPendingIntent=");
        sb.append(pendingIntent);
        sb.append(", mChipIcon=");
        sb.append(icon);
        sb.append(", mExpandedChipView=");
        sb.append(remoteViews);
        sb.append(", mChipBackground=");
        sb.append(this.mChipBackground);
        sb.append(", mPrimaryActionIndex=");
        sb.append(this.mPrimaryActionIndex);
        sb.append(", mActionType=");
        sb.append(this.mActionType);
        sb.append(", mActions=");
        sb.append(arrayList);
        sb.append(", mActionBgColors=");
        sb.append(arrayList2);
        sb.append(", mCardIcon=");
        sb.append(icon2);
        sb.append(", mCardIconBg=");
        sb.append(this.mCardIconBg);
        sb.append(", mPrimaryInfo=");
        sb.append(this.mPrimaryInfo);
        sb.append(", mSecondaryInfo=");
        sb.append(this.mSecondaryInfo);
        sb.append(", mCustomExpandedCardView=");
        sb.append(remoteViews2);
        sb.append(", mIsNew=");
        sb.append(bool);
        sb.append(", mExpandedChipText=");
        sb.append((Object) charSequence);
        sb.append(", mChronometerView=");
        sb.append(remoteViews3);
        sb.append(", mChronometerPosition=");
        sb.append(this.mChronometerPosition);
        sb.append(", mChronometerTag=");
        sb.append(this.mChronometerTag);
        sb.append(", mProgress=");
        sb.append(this.mProgress);
        sb.append(", mProgressMax=");
        sb.append(this.mProgressMax);
        sb.append(", mProgressIndeterminate=");
        sb.append(this.mProgressIndeterminate);
        sb.append(", mFirstIcon=");
        sb.append(icon3);
        sb.append(", mSecondIcon=");
        sb.append(icon4);
        sb.append(", mOngoingCollapsedView=");
        sb.append(remoteViews4);
        sb.append(", mOngoingExpandView=");
        sb.append(remoteViews5);
        sb.append(", mOngoingNowbarView=");
        sb.append(remoteViews6);
        sb.append(", mCustomNowBarView=");
        sb.append(remoteViews7);
        sb.append(", mProgressSegments=");
        sb.append(arrays);
        sb.append(", mProgressSegmentIcon=");
        sb.append(icon5);
        sb.append(", mProgressColor=");
        sb.append(this.mProgressColor);
        sb.append(", mDescription=");
        sb.append(this.mDescription);
        sb.append(", mSecondaryInfoIcon=");
        sb.append(icon6);
        sb.append(", mNowbarIcon=");
        sb.append(icon7);
        sb.append(", mNowbarPrimaryInfo=");
        sb.append(this.mNowbarPrimaryInfo);
        sb.append(", mNowbarSecondaryInfo=");
        sb.append(this.mNowbarSecondaryInfo);
        sb.append(", mUserId=");
        sb.append(this.mUserId);
        sb.append(", mAppName=");
        sb.append(this.mAppName);
        sb.append(", mWhen=");
        sb.append(this.mWhen);
        sb.append(", mAppIcon=");
        sb.append(icon8);
        sb.append(", mPackageName=");
        sb.append(this.mPackageName);
        sb.append(", mNotificationEntry=");
        sb.append(this.mNotificationEntry);
        sb.append(", mStyle=");
        sb.append(this.mStyle);
        sb.append(", mNowbarChronometerPosition=");
        return Anchor$$ExternalSyntheticOutline0.m(this.mNowbarChronometerPosition, ")", sb);
    }

    public /* synthetic */ OngoingActivityData(String str, PendingIntent pendingIntent, Icon icon, RemoteViews remoteViews, int i, int i2, int i3, ArrayList arrayList, ArrayList arrayList2, Icon icon2, Integer num, String str2, String str3, RemoteViews remoteViews2, Boolean bool, CharSequence charSequence, RemoteViews remoteViews3, int i4, String str4, int i5, int i6, boolean z, Icon icon3, Icon icon4, RemoteViews remoteViews4, RemoteViews remoteViews5, RemoteViews remoteViews6, RemoteViews remoteViews7, Parcelable[] parcelableArr, Icon icon5, int i7, String str5, Icon icon6, Icon icon7, String str6, String str7, int i8, String str8, Long l, Icon icon8, String str9, NotificationEntry notificationEntry, int i9, int i10, int i11, int i12, DefaultConstructorMarker defaultConstructorMarker) {
        this((i11 & 1) != 0 ? "" : str, pendingIntent, icon, remoteViews, (i11 & 16) != 0 ? 0 : i, (i11 & 32) != 0 ? -1 : i2, (i11 & 64) != 0 ? -1 : i3, arrayList, arrayList2, icon2, num, str2, str3, remoteViews2, (i11 & 16384) != 0 ? Boolean.TRUE : bool, charSequence, remoteViews3, (131072 & i11) != 0 ? 0 : i4, str4, i5, i6, (i11 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? false : z, icon3, icon4, remoteViews4, remoteViews5, remoteViews6, remoteViews7, parcelableArr, icon5, i7, str5, icon6, icon7, str6, str7, i8, str8, l, icon8, str9, notificationEntry, i9, i10);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0194  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OngoingActivityData(com.android.systemui.statusbar.notification.collection.NotificationEntry r50, android.service.notification.StatusBarNotification r51, android.content.Context r52) {
        /*
            Method dump skipped, instructions count: 625
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(com.android.systemui.statusbar.notification.collection.NotificationEntry, android.service.notification.StatusBarNotification, android.content.Context):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OngoingActivityData(com.android.systemui.statusbar.notification.collection.NotificationEntry r46, android.content.Context r47, int r48, int r49, android.graphics.drawable.Icon r50) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(com.android.systemui.statusbar.notification.collection.NotificationEntry, android.content.Context, int, int, android.graphics.drawable.Icon):void");
    }
}
