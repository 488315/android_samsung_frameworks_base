package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingActivityData {
    public final ArrayList mActionBgColors;
    public final int mActionType;
    public final ArrayList mActions;
    public final Icon mAodRemoteAppIcon;
    public final String mAodRemoteAppName;
    public final PendingIntent mAodRemoteAppPendingIntent;
    public final Icon mAppIcon;
    public final String mAppName;
    public final Icon mCardIcon;
    public final Integer mCardIconBg;
    public int mChipBackground;
    public Icon mChipIcon;
    public final int mChronometerPosition;
    public final String mChronometerTag;
    public final RemoteViews mChronometerView;
    public final RemoteViews mCustomExpandedCardView;
    public final RemoteViews mCustomNowBarView;
    public final String mDescription;
    public CharSequence mExpandedChipText;
    public final RemoteViews mExpandedChipView;
    public final Icon mFirstIcon;
    public final boolean mIsMediaOngoingData;
    public Boolean mNeedMarquee;
    public final String mNotiID;
    public final NotificationEntry mNotificationEntry;
    public final int mNowbarChronometerPosition;
    public final Bundle mNowbarExtraData;
    public Icon mNowbarIcon;
    public final PendingIntent mNowbarPendingIntentOnSubScreen;
    public String mNowbarPrimaryInfo;
    public String mNowbarSecondaryInfo;
    public RemoteViews mOngoingCollapsedView;
    public RemoteViews mOngoingENRExpandView;
    public RemoteViews mOngoingNowbarView;
    public RemoteViews mOngoingOAExpandView;
    public RemoteViews mOngoingSubScreenExpandView;
    public RemoteViews mOngoingSubScreenNowbarView;
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
    public final RemoteViews mSubScreenCustomExpandedCardView;
    public final RemoteViews mSubScreenCustomNowBarView;
    public final int mUserId;
    public final Long mWhen;

    public OngoingActivityData(String str, PendingIntent pendingIntent, PendingIntent pendingIntent2, Icon icon, RemoteViews remoteViews, int i, int i2, int i3, ArrayList<Notification.Action> arrayList, ArrayList<Integer> arrayList2, Icon icon2, Integer num, String str2, String str3, RemoteViews remoteViews2, RemoteViews remoteViews3, Boolean bool, CharSequence charSequence, RemoteViews remoteViews4, int i4, String str4, int i5, int i6, boolean z, Icon icon3, Icon icon4, RemoteViews remoteViews5, RemoteViews remoteViews6, RemoteViews remoteViews7, RemoteViews remoteViews8, RemoteViews remoteViews9, RemoteViews remoteViews10, RemoteViews remoteViews11, RemoteViews remoteViews12, Parcelable[] parcelableArr, Icon icon5, int i7, String str5, Icon icon6, Icon icon7, String str6, String str7, int i8, String str8, Long l, Icon icon8, String str9, NotificationEntry notificationEntry, int i9, int i10, boolean z2, String str10, Icon icon9, PendingIntent pendingIntent3, Bundle bundle) {
        this.mNotiID = str;
        this.mPendingIntent = pendingIntent;
        this.mNowbarPendingIntentOnSubScreen = pendingIntent2;
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
        this.mSubScreenCustomExpandedCardView = remoteViews3;
        this.mNeedMarquee = bool;
        this.mExpandedChipText = charSequence;
        this.mChronometerView = remoteViews4;
        this.mChronometerPosition = i4;
        this.mChronometerTag = str4;
        this.mProgress = i5;
        this.mProgressMax = i6;
        this.mProgressIndeterminate = z;
        this.mFirstIcon = icon3;
        this.mSecondIcon = icon4;
        this.mOngoingCollapsedView = remoteViews5;
        this.mOngoingOAExpandView = remoteViews6;
        this.mOngoingENRExpandView = remoteViews7;
        this.mOngoingSubScreenExpandView = remoteViews8;
        this.mOngoingNowbarView = remoteViews9;
        this.mOngoingSubScreenNowbarView = remoteViews10;
        this.mCustomNowBarView = remoteViews11;
        this.mSubScreenCustomNowBarView = remoteViews12;
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
        this.mIsMediaOngoingData = z2;
        this.mAodRemoteAppName = str10;
        this.mAodRemoteAppIcon = icon9;
        this.mAodRemoteAppPendingIntent = pendingIntent3;
        this.mNowbarExtraData = bundle;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OngoingActivityData)) {
            return false;
        }
        OngoingActivityData ongoingActivityData = (OngoingActivityData) obj;
        return Intrinsics.areEqual(this.mNotiID, ongoingActivityData.mNotiID) && Intrinsics.areEqual(this.mPendingIntent, ongoingActivityData.mPendingIntent) && Intrinsics.areEqual(this.mNowbarPendingIntentOnSubScreen, ongoingActivityData.mNowbarPendingIntentOnSubScreen) && Intrinsics.areEqual(this.mChipIcon, ongoingActivityData.mChipIcon) && Intrinsics.areEqual(this.mExpandedChipView, ongoingActivityData.mExpandedChipView) && this.mChipBackground == ongoingActivityData.mChipBackground && this.mPrimaryActionIndex == ongoingActivityData.mPrimaryActionIndex && this.mActionType == ongoingActivityData.mActionType && Intrinsics.areEqual(this.mActions, ongoingActivityData.mActions) && Intrinsics.areEqual(this.mActionBgColors, ongoingActivityData.mActionBgColors) && Intrinsics.areEqual(this.mCardIcon, ongoingActivityData.mCardIcon) && Intrinsics.areEqual(this.mCardIconBg, ongoingActivityData.mCardIconBg) && Intrinsics.areEqual(this.mPrimaryInfo, ongoingActivityData.mPrimaryInfo) && Intrinsics.areEqual(this.mSecondaryInfo, ongoingActivityData.mSecondaryInfo) && Intrinsics.areEqual(this.mCustomExpandedCardView, ongoingActivityData.mCustomExpandedCardView) && Intrinsics.areEqual(this.mSubScreenCustomExpandedCardView, ongoingActivityData.mSubScreenCustomExpandedCardView) && Intrinsics.areEqual(this.mNeedMarquee, ongoingActivityData.mNeedMarquee) && Intrinsics.areEqual(this.mExpandedChipText, ongoingActivityData.mExpandedChipText) && Intrinsics.areEqual(this.mChronometerView, ongoingActivityData.mChronometerView) && this.mChronometerPosition == ongoingActivityData.mChronometerPosition && Intrinsics.areEqual(this.mChronometerTag, ongoingActivityData.mChronometerTag) && this.mProgress == ongoingActivityData.mProgress && this.mProgressMax == ongoingActivityData.mProgressMax && this.mProgressIndeterminate == ongoingActivityData.mProgressIndeterminate && Intrinsics.areEqual(this.mFirstIcon, ongoingActivityData.mFirstIcon) && Intrinsics.areEqual(this.mSecondIcon, ongoingActivityData.mSecondIcon) && Intrinsics.areEqual(this.mOngoingCollapsedView, ongoingActivityData.mOngoingCollapsedView) && Intrinsics.areEqual(this.mOngoingOAExpandView, ongoingActivityData.mOngoingOAExpandView) && Intrinsics.areEqual(this.mOngoingENRExpandView, ongoingActivityData.mOngoingENRExpandView) && Intrinsics.areEqual(this.mOngoingSubScreenExpandView, ongoingActivityData.mOngoingSubScreenExpandView) && Intrinsics.areEqual(this.mOngoingNowbarView, ongoingActivityData.mOngoingNowbarView) && Intrinsics.areEqual(this.mOngoingSubScreenNowbarView, ongoingActivityData.mOngoingSubScreenNowbarView) && Intrinsics.areEqual(this.mCustomNowBarView, ongoingActivityData.mCustomNowBarView) && Intrinsics.areEqual(this.mSubScreenCustomNowBarView, ongoingActivityData.mSubScreenCustomNowBarView) && Intrinsics.areEqual(this.mProgressSegments, ongoingActivityData.mProgressSegments) && Intrinsics.areEqual(this.mProgressSegmentIcon, ongoingActivityData.mProgressSegmentIcon) && this.mProgressColor == ongoingActivityData.mProgressColor && Intrinsics.areEqual(this.mDescription, ongoingActivityData.mDescription) && Intrinsics.areEqual(this.mSecondaryInfoIcon, ongoingActivityData.mSecondaryInfoIcon) && Intrinsics.areEqual(this.mNowbarIcon, ongoingActivityData.mNowbarIcon) && Intrinsics.areEqual(this.mNowbarPrimaryInfo, ongoingActivityData.mNowbarPrimaryInfo) && Intrinsics.areEqual(this.mNowbarSecondaryInfo, ongoingActivityData.mNowbarSecondaryInfo) && this.mUserId == ongoingActivityData.mUserId && Intrinsics.areEqual(this.mAppName, ongoingActivityData.mAppName) && Intrinsics.areEqual(this.mWhen, ongoingActivityData.mWhen) && Intrinsics.areEqual(this.mAppIcon, ongoingActivityData.mAppIcon) && Intrinsics.areEqual(this.mPackageName, ongoingActivityData.mPackageName) && Intrinsics.areEqual(this.mNotificationEntry, ongoingActivityData.mNotificationEntry) && this.mStyle == ongoingActivityData.mStyle && this.mNowbarChronometerPosition == ongoingActivityData.mNowbarChronometerPosition && this.mIsMediaOngoingData == ongoingActivityData.mIsMediaOngoingData && Intrinsics.areEqual(this.mAodRemoteAppName, ongoingActivityData.mAodRemoteAppName) && Intrinsics.areEqual(this.mAodRemoteAppIcon, ongoingActivityData.mAodRemoteAppIcon) && Intrinsics.areEqual(this.mAodRemoteAppPendingIntent, ongoingActivityData.mAodRemoteAppPendingIntent) && Intrinsics.areEqual(this.mNowbarExtraData, ongoingActivityData.mNowbarExtraData);
    }

    public final int hashCode() {
        int hashCode = this.mNotiID.hashCode() * 31;
        PendingIntent pendingIntent = this.mPendingIntent;
        int hashCode2 = (hashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        PendingIntent pendingIntent2 = this.mNowbarPendingIntentOnSubScreen;
        int hashCode3 = (hashCode2 + (pendingIntent2 == null ? 0 : pendingIntent2.hashCode())) * 31;
        Icon icon = this.mChipIcon;
        int hashCode4 = (hashCode3 + (icon == null ? 0 : icon.hashCode())) * 31;
        RemoteViews remoteViews = this.mExpandedChipView;
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.mActionType, ReorderTile$$ExternalSyntheticOutline0.m(this.mPrimaryActionIndex, ReorderTile$$ExternalSyntheticOutline0.m(this.mChipBackground, (hashCode4 + (remoteViews == null ? 0 : remoteViews.hashCode())) * 31, 31), 31), 31);
        ArrayList arrayList = this.mActions;
        int hashCode5 = (m + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        ArrayList arrayList2 = this.mActionBgColors;
        int hashCode6 = (hashCode5 + (arrayList2 == null ? 0 : arrayList2.hashCode())) * 31;
        Icon icon2 = this.mCardIcon;
        int hashCode7 = (hashCode6 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        Integer num = this.mCardIconBg;
        int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode7 + (num == null ? 0 : num.hashCode())) * 31, 31, this.mPrimaryInfo), 31, this.mSecondaryInfo);
        RemoteViews remoteViews2 = this.mCustomExpandedCardView;
        int hashCode8 = (m2 + (remoteViews2 == null ? 0 : remoteViews2.hashCode())) * 31;
        RemoteViews remoteViews3 = this.mSubScreenCustomExpandedCardView;
        int hashCode9 = (hashCode8 + (remoteViews3 == null ? 0 : remoteViews3.hashCode())) * 31;
        Boolean bool = this.mNeedMarquee;
        int hashCode10 = (hashCode9 + (bool == null ? 0 : bool.hashCode())) * 31;
        CharSequence charSequence = this.mExpandedChipText;
        int hashCode11 = (hashCode10 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        RemoteViews remoteViews4 = this.mChronometerView;
        int m3 = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mProgressMax, ReorderTile$$ExternalSyntheticOutline0.m(this.mProgress, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mChronometerPosition, (hashCode11 + (remoteViews4 == null ? 0 : remoteViews4.hashCode())) * 31, 31), 31, this.mChronometerTag), 31), 31), 31, this.mProgressIndeterminate);
        Icon icon3 = this.mFirstIcon;
        int hashCode12 = (m3 + (icon3 == null ? 0 : icon3.hashCode())) * 31;
        Icon icon4 = this.mSecondIcon;
        int hashCode13 = (hashCode12 + (icon4 == null ? 0 : icon4.hashCode())) * 31;
        RemoteViews remoteViews5 = this.mOngoingCollapsedView;
        int hashCode14 = (hashCode13 + (remoteViews5 == null ? 0 : remoteViews5.hashCode())) * 31;
        RemoteViews remoteViews6 = this.mOngoingOAExpandView;
        int hashCode15 = (hashCode14 + (remoteViews6 == null ? 0 : remoteViews6.hashCode())) * 31;
        RemoteViews remoteViews7 = this.mOngoingENRExpandView;
        int hashCode16 = (hashCode15 + (remoteViews7 == null ? 0 : remoteViews7.hashCode())) * 31;
        RemoteViews remoteViews8 = this.mOngoingSubScreenExpandView;
        int hashCode17 = (hashCode16 + (remoteViews8 == null ? 0 : remoteViews8.hashCode())) * 31;
        RemoteViews remoteViews9 = this.mOngoingNowbarView;
        int hashCode18 = (hashCode17 + (remoteViews9 == null ? 0 : remoteViews9.hashCode())) * 31;
        RemoteViews remoteViews10 = this.mOngoingSubScreenNowbarView;
        int hashCode19 = (hashCode18 + (remoteViews10 == null ? 0 : remoteViews10.hashCode())) * 31;
        RemoteViews remoteViews11 = this.mCustomNowBarView;
        int hashCode20 = (hashCode19 + (remoteViews11 == null ? 0 : remoteViews11.hashCode())) * 31;
        RemoteViews remoteViews12 = this.mSubScreenCustomNowBarView;
        int hashCode21 = (((hashCode20 + (remoteViews12 == null ? 0 : remoteViews12.hashCode())) * 31) + Arrays.hashCode(this.mProgressSegments)) * 31;
        Icon icon5 = this.mProgressSegmentIcon;
        int m4 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mProgressColor, (hashCode21 + (icon5 == null ? 0 : icon5.hashCode())) * 31, 31), 31, this.mDescription);
        Icon icon6 = this.mSecondaryInfoIcon;
        int hashCode22 = (m4 + (icon6 == null ? 0 : icon6.hashCode())) * 31;
        Icon icon7 = this.mNowbarIcon;
        int hashCode23 = (hashCode22 + (icon7 == null ? 0 : icon7.hashCode())) * 31;
        String str = this.mNowbarPrimaryInfo;
        int hashCode24 = (hashCode23 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mNowbarSecondaryInfo;
        int m5 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mUserId, (hashCode24 + (str2 == null ? 0 : str2.hashCode())) * 31, 31), 31, this.mAppName);
        Long l = this.mWhen;
        int hashCode25 = (m5 + (l == null ? 0 : l.hashCode())) * 31;
        Icon icon8 = this.mAppIcon;
        int m6 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mNowbarChronometerPosition, ReorderTile$$ExternalSyntheticOutline0.m(this.mStyle, (this.mNotificationEntry.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((hashCode25 + (icon8 == null ? 0 : icon8.hashCode())) * 31, 31, this.mPackageName)) * 31, 31), 31), 31, this.mIsMediaOngoingData), 31, this.mAodRemoteAppName);
        Icon icon9 = this.mAodRemoteAppIcon;
        int hashCode26 = (m6 + (icon9 == null ? 0 : icon9.hashCode())) * 31;
        PendingIntent pendingIntent3 = this.mAodRemoteAppPendingIntent;
        int hashCode27 = (hashCode26 + (pendingIntent3 == null ? 0 : pendingIntent3.hashCode())) * 31;
        Bundle bundle = this.mNowbarExtraData;
        return hashCode27 + (bundle != null ? bundle.hashCode() : 0);
    }

    public final String toString() {
        PendingIntent pendingIntent = this.mPendingIntent;
        PendingIntent pendingIntent2 = this.mNowbarPendingIntentOnSubScreen;
        Icon icon = this.mChipIcon;
        RemoteViews remoteViews = this.mExpandedChipView;
        int i = this.mChipBackground;
        ArrayList arrayList = this.mActions;
        ArrayList arrayList2 = this.mActionBgColors;
        Icon icon2 = this.mCardIcon;
        RemoteViews remoteViews2 = this.mCustomExpandedCardView;
        RemoteViews remoteViews3 = this.mSubScreenCustomExpandedCardView;
        Boolean bool = this.mNeedMarquee;
        CharSequence charSequence = this.mExpandedChipText;
        return "OngoingActivityData(mNotiID=" + this.mNotiID + ", mPendingIntent=" + pendingIntent + ", mNowbarPendingIntentOnSubScreen=" + pendingIntent2 + ", mChipIcon=" + icon + ", mExpandedChipView=" + remoteViews + ", mChipBackground=" + i + ", mPrimaryActionIndex=" + this.mPrimaryActionIndex + ", mActionType=" + this.mActionType + ", mActions=" + arrayList + ", mActionBgColors=" + arrayList2 + ", mCardIcon=" + icon2 + ", mCardIconBg=" + this.mCardIconBg + ", mPrimaryInfo=" + this.mPrimaryInfo + ", mSecondaryInfo=" + this.mSecondaryInfo + ", mCustomExpandedCardView=" + remoteViews2 + ", mSubScreenCustomExpandedCardView=" + remoteViews3 + ", mNeedMarquee=" + bool + ", mExpandedChipText=" + ((Object) charSequence) + ", mChronometerView=" + this.mChronometerView + ", mChronometerPosition=" + this.mChronometerPosition + ", mChronometerTag=" + this.mChronometerTag + ", mProgress=" + this.mProgress + ", mProgressMax=" + this.mProgressMax + ", mProgressIndeterminate=" + this.mProgressIndeterminate + ", mFirstIcon=" + this.mFirstIcon + ", mSecondIcon=" + this.mSecondIcon + ", mOngoingCollapsedView=" + this.mOngoingCollapsedView + ", mOngoingOAExpandView=" + this.mOngoingOAExpandView + ", mOngoingENRExpandView=" + this.mOngoingENRExpandView + ", mOngoingSubScreenExpandView=" + this.mOngoingSubScreenExpandView + ", mOngoingNowbarView=" + this.mOngoingNowbarView + ", mOngoingSubScreenNowbarView=" + this.mOngoingSubScreenNowbarView + ", mCustomNowBarView=" + this.mCustomNowBarView + ", mSubScreenCustomNowBarView=" + this.mSubScreenCustomNowBarView + ", mProgressSegments=" + Arrays.toString(this.mProgressSegments) + ", mProgressSegmentIcon=" + this.mProgressSegmentIcon + ", mProgressColor=" + this.mProgressColor + ", mDescription=" + this.mDescription + ", mSecondaryInfoIcon=" + this.mSecondaryInfoIcon + ", mNowbarIcon=" + this.mNowbarIcon + ", mNowbarPrimaryInfo=" + this.mNowbarPrimaryInfo + ", mNowbarSecondaryInfo=" + this.mNowbarSecondaryInfo + ", mUserId=" + this.mUserId + ", mAppName=" + this.mAppName + ", mWhen=" + this.mWhen + ", mAppIcon=" + this.mAppIcon + ", mPackageName=" + this.mPackageName + ", mNotificationEntry=" + this.mNotificationEntry + ", mStyle=" + this.mStyle + ", mNowbarChronometerPosition=" + this.mNowbarChronometerPosition + ", mIsMediaOngoingData=" + this.mIsMediaOngoingData + ", mAodRemoteAppName=" + this.mAodRemoteAppName + ", mAodRemoteAppIcon=" + this.mAodRemoteAppIcon + ", mAodRemoteAppPendingIntent=" + this.mAodRemoteAppPendingIntent + ", mNowbarExtraData=" + this.mNowbarExtraData + ")";
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        */
    public /* synthetic */ OngoingActivityData(java.lang.String r59, android.app.PendingIntent r60, android.app.PendingIntent r61, android.graphics.drawable.Icon r62, android.widget.RemoteViews r63, int r64, int r65, int r66, java.util.ArrayList r67, java.util.ArrayList r68, android.graphics.drawable.Icon r69, java.lang.Integer r70, java.lang.String r71, java.lang.String r72, android.widget.RemoteViews r73, android.widget.RemoteViews r74, java.lang.Boolean r75, java.lang.CharSequence r76, android.widget.RemoteViews r77, int r78, java.lang.String r79, int r80, int r81, boolean r82, android.graphics.drawable.Icon r83, android.graphics.drawable.Icon r84, android.widget.RemoteViews r85, android.widget.RemoteViews r86, android.widget.RemoteViews r87, android.widget.RemoteViews r88, android.widget.RemoteViews r89, android.widget.RemoteViews r90, android.widget.RemoteViews r91, android.widget.RemoteViews r92, android.os.Parcelable[] r93, android.graphics.drawable.Icon r94, int r95, java.lang.String r96, android.graphics.drawable.Icon r97, android.graphics.drawable.Icon r98, java.lang.String r99, java.lang.String r100, int r101, java.lang.String r102, java.lang.Long r103, android.graphics.drawable.Icon r104, java.lang.String r105, com.android.systemui.statusbar.notification.collection.NotificationEntry r106, int r107, int r108, boolean r109, java.lang.String r110, android.graphics.drawable.Icon r111, android.app.PendingIntent r112, android.os.Bundle r113, int r114, int r115, kotlin.jvm.internal.DefaultConstructorMarker r116) {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(java.lang.String, android.app.PendingIntent, android.app.PendingIntent, android.graphics.drawable.Icon, android.widget.RemoteViews, int, int, int, java.util.ArrayList, java.util.ArrayList, android.graphics.drawable.Icon, java.lang.Integer, java.lang.String, java.lang.String, android.widget.RemoteViews, android.widget.RemoteViews, java.lang.Boolean, java.lang.CharSequence, android.widget.RemoteViews, int, java.lang.String, int, int, boolean, android.graphics.drawable.Icon, android.graphics.drawable.Icon, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.os.Parcelable[], android.graphics.drawable.Icon, int, java.lang.String, android.graphics.drawable.Icon, android.graphics.drawable.Icon, java.lang.String, java.lang.String, int, java.lang.String, java.lang.Long, android.graphics.drawable.Icon, java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry, int, int, boolean, java.lang.String, android.graphics.drawable.Icon, android.app.PendingIntent, android.os.Bundle, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OngoingActivityData(com.android.systemui.statusbar.notification.collection.NotificationEntry r57, android.service.notification.StatusBarNotification r58, android.content.Context r59) {
        /*
            Method dump skipped, instructions count: 870
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(com.android.systemui.statusbar.notification.collection.NotificationEntry, android.service.notification.StatusBarNotification, android.content.Context):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OngoingActivityData(com.android.systemui.statusbar.notification.collection.NotificationEntry r57, android.content.Context r58, int r59, int r60, android.graphics.drawable.Icon r61) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(com.android.systemui.statusbar.notification.collection.NotificationEntry, android.content.Context, int, int, android.graphics.drawable.Icon):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OngoingActivityData(com.android.systemui.statusbar.phone.ongoingactivity.MediaOngoingActivityInfo r57, com.android.systemui.statusbar.notification.collection.NotificationEntry r58) {
        /*
            r56 = this;
            r0 = r57
            r1 = r58
            java.lang.String r2 = r1.mKey
            r3 = r2
            android.app.PendingIntent r2 = r0.clickIntent
            android.service.notification.StatusBarNotification r4 = r1.mSbn
            android.app.Notification r4 = r4.getNotification()
            android.os.Bundle r4 = r4.extras
            java.lang.String r5 = "android.ongoingActivityNoti.nowbarPendingIntentOnSubScreen"
            java.lang.Class<android.app.PendingIntent> r6 = android.app.PendingIntent.class
            java.lang.Object r4 = r4.getParcelable(r5, r6)
            android.app.PendingIntent r4 = (android.app.PendingIntent) r4
            r5 = r3
            r3 = r4
            android.graphics.drawable.Icon r4 = r0.appIcon
            int r6 = r0.bgColor
            java.lang.Integer r12 = java.lang.Integer.valueOf(r6)
            java.lang.Boolean r17 = java.lang.Boolean.TRUE
            r7 = 0
            android.os.Parcelable[] r7 = new android.os.Parcelable[r7]
            android.service.notification.StatusBarNotification r8 = r1.mSbn
            android.os.UserHandle r8 = r8.getUser()
            int r43 = r8.getIdentifier()
            java.lang.String r8 = ""
            java.lang.String r9 = r0.songTitle
            if (r9 != 0) goto L3d
            r44 = r8
            goto L3f
        L3d:
            r44 = r9
        L3f:
            android.service.notification.StatusBarNotification r9 = r1.mSbn
            android.app.Notification r9 = r9.getNotification()
            long r9 = r9.when
            java.lang.Long r45 = java.lang.Long.valueOf(r9)
            android.app.PendingIntent r9 = r0.clickIntent
            if (r9 == 0) goto L59
            java.lang.String r9 = r9.getCreatorPackage()
            if (r9 != 0) goto L56
            goto L59
        L56:
            r47 = r9
            goto L5b
        L59:
            r47 = r8
        L5b:
            r54 = 0
            r55 = 0
            r1 = r5
            r5 = 0
            r35 = r7
            r7 = -1
            r8 = -1
            r9 = 0
            r10 = 0
            java.lang.String r13 = ""
            java.lang.String r14 = ""
            r15 = 0
            r16 = 0
            java.lang.String r0 = r0.songTitle
            r19 = 0
            r20 = 0
            java.lang.String r21 = ""
            r22 = -1
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r30 = 0
            r31 = 0
            r32 = 0
            r33 = 0
            r34 = 0
            r36 = 0
            r37 = 0
            java.lang.String r38 = ""
            r39 = 0
            r40 = 0
            r41 = 0
            r42 = 0
            r46 = 0
            r49 = 1
            r50 = 0
            r51 = 1
            java.lang.String r52 = ""
            r53 = 0
            r11 = r4
            r48 = r58
            r18 = r0
            r0 = r56
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(com.android.systemui.statusbar.phone.ongoingactivity.MediaOngoingActivityInfo, com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
    }
}
