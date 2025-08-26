package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

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
    public boolean mDismissRequested;
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

    public OngoingActivityData(String str, PendingIntent pendingIntent, PendingIntent pendingIntent2, Icon icon, RemoteViews remoteViews, int i, int i2, int i3, ArrayList<Notification.Action> arrayList, ArrayList<Integer> arrayList2, Icon icon2, Integer num, String str2, String str3, RemoteViews remoteViews2, RemoteViews remoteViews3, Boolean bool, CharSequence charSequence, RemoteViews remoteViews4, int i4, String str4, int i5, int i6, boolean z, Icon icon3, Icon icon4, RemoteViews remoteViews5, RemoteViews remoteViews6, RemoteViews remoteViews7, RemoteViews remoteViews8, RemoteViews remoteViews9, RemoteViews remoteViews10, RemoteViews remoteViews11, RemoteViews remoteViews12, Parcelable[] parcelableArr, Icon icon5, int i7, String str5, Icon icon6, Icon icon7, String str6, String str7, int i8, String str8, Long l, Icon icon8, String str9, NotificationEntry notificationEntry, int i9, int i10, boolean z2, String str10, Icon icon9, PendingIntent pendingIntent3, Bundle bundle, boolean z3) {
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
        this.mDismissRequested = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OngoingActivityData)) {
            return false;
        }
        OngoingActivityData ongoingActivityData = (OngoingActivityData) obj;
        return Intrinsics.areEqual(this.mNotiID, ongoingActivityData.mNotiID) && Intrinsics.areEqual(this.mPendingIntent, ongoingActivityData.mPendingIntent) && Intrinsics.areEqual(this.mNowbarPendingIntentOnSubScreen, ongoingActivityData.mNowbarPendingIntentOnSubScreen) && Intrinsics.areEqual(this.mChipIcon, ongoingActivityData.mChipIcon) && Intrinsics.areEqual(this.mExpandedChipView, ongoingActivityData.mExpandedChipView) && this.mChipBackground == ongoingActivityData.mChipBackground && this.mPrimaryActionIndex == ongoingActivityData.mPrimaryActionIndex && this.mActionType == ongoingActivityData.mActionType && Intrinsics.areEqual(this.mActions, ongoingActivityData.mActions) && Intrinsics.areEqual(this.mActionBgColors, ongoingActivityData.mActionBgColors) && Intrinsics.areEqual(this.mCardIcon, ongoingActivityData.mCardIcon) && Intrinsics.areEqual(this.mCardIconBg, ongoingActivityData.mCardIconBg) && Intrinsics.areEqual(this.mPrimaryInfo, ongoingActivityData.mPrimaryInfo) && Intrinsics.areEqual(this.mSecondaryInfo, ongoingActivityData.mSecondaryInfo) && Intrinsics.areEqual(this.mCustomExpandedCardView, ongoingActivityData.mCustomExpandedCardView) && Intrinsics.areEqual(this.mSubScreenCustomExpandedCardView, ongoingActivityData.mSubScreenCustomExpandedCardView) && Intrinsics.areEqual(this.mNeedMarquee, ongoingActivityData.mNeedMarquee) && Intrinsics.areEqual(this.mExpandedChipText, ongoingActivityData.mExpandedChipText) && Intrinsics.areEqual(this.mChronometerView, ongoingActivityData.mChronometerView) && this.mChronometerPosition == ongoingActivityData.mChronometerPosition && Intrinsics.areEqual(this.mChronometerTag, ongoingActivityData.mChronometerTag) && this.mProgress == ongoingActivityData.mProgress && this.mProgressMax == ongoingActivityData.mProgressMax && this.mProgressIndeterminate == ongoingActivityData.mProgressIndeterminate && Intrinsics.areEqual(this.mFirstIcon, ongoingActivityData.mFirstIcon) && Intrinsics.areEqual(this.mSecondIcon, ongoingActivityData.mSecondIcon) && Intrinsics.areEqual(this.mOngoingCollapsedView, ongoingActivityData.mOngoingCollapsedView) && Intrinsics.areEqual(this.mOngoingOAExpandView, ongoingActivityData.mOngoingOAExpandView) && Intrinsics.areEqual(this.mOngoingENRExpandView, ongoingActivityData.mOngoingENRExpandView) && Intrinsics.areEqual(this.mOngoingSubScreenExpandView, ongoingActivityData.mOngoingSubScreenExpandView) && Intrinsics.areEqual(this.mOngoingNowbarView, ongoingActivityData.mOngoingNowbarView) && Intrinsics.areEqual(this.mOngoingSubScreenNowbarView, ongoingActivityData.mOngoingSubScreenNowbarView) && Intrinsics.areEqual(this.mCustomNowBarView, ongoingActivityData.mCustomNowBarView) && Intrinsics.areEqual(this.mSubScreenCustomNowBarView, ongoingActivityData.mSubScreenCustomNowBarView) && Intrinsics.areEqual(this.mProgressSegments, ongoingActivityData.mProgressSegments) && Intrinsics.areEqual(this.mProgressSegmentIcon, ongoingActivityData.mProgressSegmentIcon) && this.mProgressColor == ongoingActivityData.mProgressColor && Intrinsics.areEqual(this.mDescription, ongoingActivityData.mDescription) && Intrinsics.areEqual(this.mSecondaryInfoIcon, ongoingActivityData.mSecondaryInfoIcon) && Intrinsics.areEqual(this.mNowbarIcon, ongoingActivityData.mNowbarIcon) && Intrinsics.areEqual(this.mNowbarPrimaryInfo, ongoingActivityData.mNowbarPrimaryInfo) && Intrinsics.areEqual(this.mNowbarSecondaryInfo, ongoingActivityData.mNowbarSecondaryInfo) && this.mUserId == ongoingActivityData.mUserId && Intrinsics.areEqual(this.mAppName, ongoingActivityData.mAppName) && Intrinsics.areEqual(this.mWhen, ongoingActivityData.mWhen) && Intrinsics.areEqual(this.mAppIcon, ongoingActivityData.mAppIcon) && Intrinsics.areEqual(this.mPackageName, ongoingActivityData.mPackageName) && Intrinsics.areEqual(this.mNotificationEntry, ongoingActivityData.mNotificationEntry) && this.mStyle == ongoingActivityData.mStyle && this.mNowbarChronometerPosition == ongoingActivityData.mNowbarChronometerPosition && this.mIsMediaOngoingData == ongoingActivityData.mIsMediaOngoingData && Intrinsics.areEqual(this.mAodRemoteAppName, ongoingActivityData.mAodRemoteAppName) && Intrinsics.areEqual(this.mAodRemoteAppIcon, ongoingActivityData.mAodRemoteAppIcon) && Intrinsics.areEqual(this.mAodRemoteAppPendingIntent, ongoingActivityData.mAodRemoteAppPendingIntent) && Intrinsics.areEqual(this.mNowbarExtraData, ongoingActivityData.mNowbarExtraData) && this.mDismissRequested == ongoingActivityData.mDismissRequested;
    }

    public final int hashCode() {
        int iHashCode = this.mNotiID.hashCode() * 31;
        PendingIntent pendingIntent = this.mPendingIntent;
        int iHashCode2 = (iHashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        PendingIntent pendingIntent2 = this.mNowbarPendingIntentOnSubScreen;
        int iHashCode3 = (iHashCode2 + (pendingIntent2 == null ? 0 : pendingIntent2.hashCode())) * 31;
        Icon icon = this.mChipIcon;
        int iHashCode4 = (iHashCode3 + (icon == null ? 0 : icon.hashCode())) * 31;
        RemoteViews remoteViews = this.mExpandedChipView;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.mActionType, ReorderTile$$ExternalSyntheticOutline0.m(this.mPrimaryActionIndex, ReorderTile$$ExternalSyntheticOutline0.m(this.mChipBackground, (iHashCode4 + (remoteViews == null ? 0 : remoteViews.hashCode())) * 31, 31), 31), 31);
        ArrayList arrayList = this.mActions;
        int iHashCode5 = (iM + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        ArrayList arrayList2 = this.mActionBgColors;
        int iHashCode6 = (iHashCode5 + (arrayList2 == null ? 0 : arrayList2.hashCode())) * 31;
        Icon icon2 = this.mCardIcon;
        int iHashCode7 = (iHashCode6 + (icon2 == null ? 0 : icon2.hashCode())) * 31;
        Integer num = this.mCardIconBg;
        int iM2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode7 + (num == null ? 0 : num.hashCode())) * 31, 31, this.mPrimaryInfo), 31, this.mSecondaryInfo);
        RemoteViews remoteViews2 = this.mCustomExpandedCardView;
        int iHashCode8 = (iM2 + (remoteViews2 == null ? 0 : remoteViews2.hashCode())) * 31;
        RemoteViews remoteViews3 = this.mSubScreenCustomExpandedCardView;
        int iHashCode9 = (iHashCode8 + (remoteViews3 == null ? 0 : remoteViews3.hashCode())) * 31;
        Boolean bool = this.mNeedMarquee;
        int iHashCode10 = (iHashCode9 + (bool == null ? 0 : bool.hashCode())) * 31;
        CharSequence charSequence = this.mExpandedChipText;
        int iHashCode11 = (iHashCode10 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        RemoteViews remoteViews4 = this.mChronometerView;
        int iM3 = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mProgressMax, ReorderTile$$ExternalSyntheticOutline0.m(this.mProgress, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mChronometerPosition, (iHashCode11 + (remoteViews4 == null ? 0 : remoteViews4.hashCode())) * 31, 31), 31, this.mChronometerTag), 31), 31), 31, this.mProgressIndeterminate);
        Icon icon3 = this.mFirstIcon;
        int iHashCode12 = (iM3 + (icon3 == null ? 0 : icon3.hashCode())) * 31;
        Icon icon4 = this.mSecondIcon;
        int iHashCode13 = (iHashCode12 + (icon4 == null ? 0 : icon4.hashCode())) * 31;
        RemoteViews remoteViews5 = this.mOngoingCollapsedView;
        int iHashCode14 = (iHashCode13 + (remoteViews5 == null ? 0 : remoteViews5.hashCode())) * 31;
        RemoteViews remoteViews6 = this.mOngoingOAExpandView;
        int iHashCode15 = (iHashCode14 + (remoteViews6 == null ? 0 : remoteViews6.hashCode())) * 31;
        RemoteViews remoteViews7 = this.mOngoingENRExpandView;
        int iHashCode16 = (iHashCode15 + (remoteViews7 == null ? 0 : remoteViews7.hashCode())) * 31;
        RemoteViews remoteViews8 = this.mOngoingSubScreenExpandView;
        int iHashCode17 = (iHashCode16 + (remoteViews8 == null ? 0 : remoteViews8.hashCode())) * 31;
        RemoteViews remoteViews9 = this.mOngoingNowbarView;
        int iHashCode18 = (iHashCode17 + (remoteViews9 == null ? 0 : remoteViews9.hashCode())) * 31;
        RemoteViews remoteViews10 = this.mOngoingSubScreenNowbarView;
        int iHashCode19 = (iHashCode18 + (remoteViews10 == null ? 0 : remoteViews10.hashCode())) * 31;
        RemoteViews remoteViews11 = this.mCustomNowBarView;
        int iHashCode20 = (iHashCode19 + (remoteViews11 == null ? 0 : remoteViews11.hashCode())) * 31;
        RemoteViews remoteViews12 = this.mSubScreenCustomNowBarView;
        int iHashCode21 = (((iHashCode20 + (remoteViews12 == null ? 0 : remoteViews12.hashCode())) * 31) + Arrays.hashCode(this.mProgressSegments)) * 31;
        Icon icon5 = this.mProgressSegmentIcon;
        int iM4 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mProgressColor, (iHashCode21 + (icon5 == null ? 0 : icon5.hashCode())) * 31, 31), 31, this.mDescription);
        Icon icon6 = this.mSecondaryInfoIcon;
        int iHashCode22 = (iM4 + (icon6 == null ? 0 : icon6.hashCode())) * 31;
        Icon icon7 = this.mNowbarIcon;
        int iHashCode23 = (iHashCode22 + (icon7 == null ? 0 : icon7.hashCode())) * 31;
        String str = this.mNowbarPrimaryInfo;
        int iHashCode24 = (iHashCode23 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mNowbarSecondaryInfo;
        int iM5 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mUserId, (iHashCode24 + (str2 == null ? 0 : str2.hashCode())) * 31, 31), 31, this.mAppName);
        Long l = this.mWhen;
        int iHashCode25 = (iM5 + (l == null ? 0 : l.hashCode())) * 31;
        Icon icon8 = this.mAppIcon;
        int iM6 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.mNowbarChronometerPosition, ReorderTile$$ExternalSyntheticOutline0.m(this.mStyle, (this.mNotificationEntry.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((iHashCode25 + (icon8 == null ? 0 : icon8.hashCode())) * 31, 31, this.mPackageName)) * 31, 31), 31), 31, this.mIsMediaOngoingData), 31, this.mAodRemoteAppName);
        Icon icon9 = this.mAodRemoteAppIcon;
        int iHashCode26 = (iM6 + (icon9 == null ? 0 : icon9.hashCode())) * 31;
        PendingIntent pendingIntent3 = this.mAodRemoteAppPendingIntent;
        int iHashCode27 = (iHashCode26 + (pendingIntent3 == null ? 0 : pendingIntent3.hashCode())) * 31;
        Bundle bundle = this.mNowbarExtraData;
        return Boolean.hashCode(this.mDismissRequested) + ((iHashCode27 + (bundle != null ? bundle.hashCode() : 0)) * 31);
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
        RemoteViews remoteViews4 = this.mChronometerView;
        Icon icon3 = this.mFirstIcon;
        Icon icon4 = this.mSecondIcon;
        RemoteViews remoteViews5 = this.mOngoingCollapsedView;
        RemoteViews remoteViews6 = this.mOngoingOAExpandView;
        RemoteViews remoteViews7 = this.mOngoingENRExpandView;
        RemoteViews remoteViews8 = this.mOngoingSubScreenExpandView;
        RemoteViews remoteViews9 = this.mOngoingNowbarView;
        RemoteViews remoteViews10 = this.mOngoingSubScreenNowbarView;
        RemoteViews remoteViews11 = this.mCustomNowBarView;
        RemoteViews remoteViews12 = this.mSubScreenCustomNowBarView;
        String string = Arrays.toString(this.mProgressSegments);
        Icon icon5 = this.mProgressSegmentIcon;
        Icon icon6 = this.mSecondaryInfoIcon;
        Icon icon7 = this.mNowbarIcon;
        String str = this.mNowbarPrimaryInfo;
        String str2 = this.mNowbarSecondaryInfo;
        Icon icon8 = this.mAppIcon;
        Icon icon9 = this.mAodRemoteAppIcon;
        PendingIntent pendingIntent3 = this.mAodRemoteAppPendingIntent;
        Bundle bundle = this.mNowbarExtraData;
        boolean z = this.mDismissRequested;
        StringBuilder sb = new StringBuilder("OngoingActivityData(mNotiID=");
        sb.append(this.mNotiID);
        sb.append(", mPendingIntent=");
        sb.append(pendingIntent);
        sb.append(", mNowbarPendingIntentOnSubScreen=");
        sb.append(pendingIntent2);
        sb.append(", mChipIcon=");
        sb.append(icon);
        sb.append(", mExpandedChipView=");
        sb.append(remoteViews);
        sb.append(", mChipBackground=");
        sb.append(i);
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
        sb.append(", mSubScreenCustomExpandedCardView=");
        sb.append(remoteViews3);
        sb.append(", mNeedMarquee=");
        sb.append(bool);
        sb.append(", mExpandedChipText=");
        sb.append((Object) charSequence);
        sb.append(", mChronometerView=");
        sb.append(remoteViews4);
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
        sb.append(remoteViews5);
        sb.append(", mOngoingOAExpandView=");
        sb.append(remoteViews6);
        sb.append(", mOngoingENRExpandView=");
        sb.append(remoteViews7);
        sb.append(", mOngoingSubScreenExpandView=");
        sb.append(remoteViews8);
        sb.append(", mOngoingNowbarView=");
        sb.append(remoteViews9);
        sb.append(", mOngoingSubScreenNowbarView=");
        sb.append(remoteViews10);
        sb.append(", mCustomNowBarView=");
        sb.append(remoteViews11);
        sb.append(", mSubScreenCustomNowBarView=");
        sb.append(remoteViews12);
        sb.append(", mProgressSegments=");
        sb.append(string);
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
        sb.append(str);
        sb.append(", mNowbarSecondaryInfo=");
        sb.append(str2);
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
        sb.append(this.mNowbarChronometerPosition);
        sb.append(", mIsMediaOngoingData=");
        sb.append(this.mIsMediaOngoingData);
        sb.append(", mAodRemoteAppName=");
        sb.append(this.mAodRemoteAppName);
        sb.append(", mAodRemoteAppIcon=");
        sb.append(icon9);
        sb.append(", mAodRemoteAppPendingIntent=");
        sb.append(pendingIntent3);
        sb.append(", mNowbarExtraData=");
        sb.append(bundle);
        sb.append(", mDismissRequested=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, z, ")");
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        */
    public /* synthetic */ OngoingActivityData(java.lang.String r60, android.app.PendingIntent r61, android.app.PendingIntent r62, android.graphics.drawable.Icon r63, android.widget.RemoteViews r64, int r65, int r66, int r67, java.util.ArrayList r68, java.util.ArrayList r69, android.graphics.drawable.Icon r70, java.lang.Integer r71, java.lang.String r72, java.lang.String r73, android.widget.RemoteViews r74, android.widget.RemoteViews r75, java.lang.Boolean r76, java.lang.CharSequence r77, android.widget.RemoteViews r78, int r79, java.lang.String r80, int r81, int r82, boolean r83, android.graphics.drawable.Icon r84, android.graphics.drawable.Icon r85, android.widget.RemoteViews r86, android.widget.RemoteViews r87, android.widget.RemoteViews r88, android.widget.RemoteViews r89, android.widget.RemoteViews r90, android.widget.RemoteViews r91, android.widget.RemoteViews r92, android.widget.RemoteViews r93, android.os.Parcelable[] r94, android.graphics.drawable.Icon r95, int r96, java.lang.String r97, android.graphics.drawable.Icon r98, android.graphics.drawable.Icon r99, java.lang.String r100, java.lang.String r101, int r102, java.lang.String r103, java.lang.Long r104, android.graphics.drawable.Icon r105, java.lang.String r106, com.android.systemui.statusbar.notification.collection.NotificationEntry r107, int r108, int r109, boolean r110, java.lang.String r111, android.graphics.drawable.Icon r112, android.app.PendingIntent r113, android.os.Bundle r114, boolean r115, int r116, int r117, kotlin.jvm.internal.DefaultConstructorMarker r118) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData.<init>(java.lang.String, android.app.PendingIntent, android.app.PendingIntent, android.graphics.drawable.Icon, android.widget.RemoteViews, int, int, int, java.util.ArrayList, java.util.ArrayList, android.graphics.drawable.Icon, java.lang.Integer, java.lang.String, java.lang.String, android.widget.RemoteViews, android.widget.RemoteViews, java.lang.Boolean, java.lang.CharSequence, android.widget.RemoteViews, int, java.lang.String, int, int, boolean, android.graphics.drawable.Icon, android.graphics.drawable.Icon, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.widget.RemoteViews, android.os.Parcelable[], android.graphics.drawable.Icon, int, java.lang.String, android.graphics.drawable.Icon, android.graphics.drawable.Icon, java.lang.String, java.lang.String, int, java.lang.String, java.lang.Long, android.graphics.drawable.Icon, java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry, int, int, boolean, java.lang.String, android.graphics.drawable.Icon, android.app.PendingIntent, android.os.Bundle, boolean, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0324  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public OngoingActivityData(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification, Context context) {
        ArrayList arrayList;
        String string;
        String str;
        String string2;
        Icon icon;
        Icon iconCreateWithResource;
        String string3;
        String key = statusBarNotification.getKey();
        PendingIntent pendingIntent = statusBarNotification.getNotification().contentIntent;
        PendingIntent pendingIntent2 = (PendingIntent) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.nowbarPendingIntentOnSubScreen", PendingIntent.class);
        Icon icon2 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.chipIcon", Icon.class);
        RemoteViews remoteViews = (RemoteViews) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.chipExpandedView", RemoteViews.class);
        int i = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.chipBgColor");
        int i2 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.actionPrimarySet", -1);
        int i3 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.actionType", -1);
        Notification.Action[] actionArr = statusBarNotification.getNotification().actions;
        if (actionArr != null) {
            arrayList = new ArrayList();
            ArraysKt___ArraysKt.toCollection(arrayList, actionArr);
        } else {
            arrayList = null;
        }
        ArrayList<Integer> integerArrayList = statusBarNotification.getNotification().extras.getIntegerArrayList("android.ongoingActivityNoti.actionBgColor");
        Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
        Integer numValueOf = Integer.valueOf(statusBarNotification.getNotification().color);
        CharSequence charSequence = statusBarNotification.getNotification().extras.getCharSequence("android.ongoingActivityNoti.primaryInfo");
        if (charSequence == null || (string = charSequence.toString()) == null) {
            CharSequence charSequence2 = statusBarNotification.getNotification().extras.getCharSequence("android.title");
            string = charSequence2 != null ? charSequence2.toString() : "No primary info";
        }
        CharSequence charSequence3 = statusBarNotification.getNotification().extras.getCharSequence("android.ongoingActivityNoti.secondaryInfo");
        if (charSequence3 == null || (string2 = charSequence3.toString()) == null) {
            str = key;
            CharSequence charSequence4 = statusBarNotification.getNotification().extras.getCharSequence("android.text");
            string2 = charSequence4 != null ? charSequence4.toString() : "";
        } else {
            str = key;
        }
        RemoteViews remoteViews2 = (RemoteViews) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.expandedRemoteView", RemoteViews.class);
        RemoteViews remoteViews3 = (RemoteViews) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.subScreenExpandedRemoteView", RemoteViews.class);
        Boolean bool = Boolean.TRUE;
        CharSequence charSequence5 = statusBarNotification.getNotification().extras.getCharSequence("android.ongoingActivityNoti.chipExpandedText");
        RemoteViews remoteViews4 = (RemoteViews) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.chronometerRemoteView", RemoteViews.class);
        int i4 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.chronometerRemoteViewPosition", 0);
        String string4 = statusBarNotification.getNotification().extras.getString("android.ongoingActivityNoti.chronometerRemoteViewTag", "");
        int i5 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.progress", -1);
        int i6 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.progressMax", 0);
        boolean z = statusBarNotification.getNotification().extras.getBoolean("android.ongoingActivityNoti.progressIndeterminate");
        Icon icon3 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.firstIcon", Icon.class);
        Icon icon4 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.secondIcon", Icon.class);
        RemoteViews remoteViews5 = (RemoteViews) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.nowbarRemoteView", RemoteViews.class);
        RemoteViews remoteViews6 = (RemoteViews) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.subScreenNowbarRemoteView", RemoteViews.class);
        Parcelable[] parcelableArray = statusBarNotification.getNotification().extras.getParcelableArray("android.ongoingActivityNoti.progressSegments");
        Parcelable[] parcelableArr = parcelableArray == null ? new Parcelable[0] : parcelableArray;
        Icon icon5 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.progressSegments.icon", Icon.class);
        int i7 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.progressSegments.progressColor", 0);
        String string5 = statusBarNotification.getNotification().extras.getString("android.ongoingActivityNoti.description", "");
        Icon icon6 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.secondaryInfoIcon", Icon.class);
        Icon icon7 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.nowbarIcon", Icon.class);
        String string6 = statusBarNotification.getNotification().extras.getString("android.ongoingActivityNoti.nowbarPrimaryInfo");
        String string7 = statusBarNotification.getNotification().extras.getString("android.ongoingActivityNoti.nowbarSecondaryInfo");
        int identifier = statusBarNotification.getUser().getIdentifier();
        String strLoadHeaderAppName = statusBarNotification.getNotification().loadHeaderAppName(context);
        Long lValueOf = Long.valueOf(statusBarNotification.getNotification().when);
        try {
            icon = icon2;
            try {
                iconCreateWithResource = Icon.createWithResource(statusBarNotification.getPackageName(), context.getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 0, statusBarNotification.getUser().getIdentifier()).icon);
            } catch (Exception unused) {
                iconCreateWithResource = null;
                String packageName = statusBarNotification.getPackageName();
                int i8 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0);
                int i9 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.nowbarChronometerPosition", 0);
                CharSequence charSequence6 = statusBarNotification.getNotification().extras.getCharSequence("android.ongoingActivityNoti.aodRemoteAppName");
                this(str, pendingIntent, pendingIntent2, icon, remoteViews, i, i2, i3, arrayList, integerArrayList, smallIcon, numValueOf, string, string2, remoteViews2, remoteViews3, bool, charSequence5, remoteViews4, i4, string4, i5, i6, z, icon3, icon4, null, null, null, null, null, null, remoteViews5, remoteViews6, parcelableArr, icon5, i7, string5, icon6, icon7, string6, string7, identifier, strLoadHeaderAppName, lValueOf, iconCreateWithResource, packageName, notificationEntry, i8, i9, false, (charSequence6 != null || (string3 = charSequence6.toString()) == null) ? "" : string3, (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.aodRemoteAppIcon", Icon.class), (PendingIntent) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.aodRemoteAppPendingIntent", PendingIntent.class), statusBarNotification.getNotification().extras.getBundle("android.ongoingActivityNoti.nowbarExtraData"), false);
                if (this.mExpandedChipView != null) {
                }
                if (this.mAodRemoteAppName.length() <= 0) {
                }
            }
        } catch (Exception unused2) {
            icon = icon2;
        }
        String packageName2 = statusBarNotification.getPackageName();
        int i82 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0);
        int i92 = statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.nowbarChronometerPosition", 0);
        CharSequence charSequence62 = statusBarNotification.getNotification().extras.getCharSequence("android.ongoingActivityNoti.aodRemoteAppName");
        this(str, pendingIntent, pendingIntent2, icon, remoteViews, i, i2, i3, arrayList, integerArrayList, smallIcon, numValueOf, string, string2, remoteViews2, remoteViews3, bool, charSequence5, remoteViews4, i4, string4, i5, i6, z, icon3, icon4, null, null, null, null, null, null, remoteViews5, remoteViews6, parcelableArr, icon5, i7, string5, icon6, icon7, string6, string7, identifier, strLoadHeaderAppName, lValueOf, iconCreateWithResource, packageName2, notificationEntry, i82, i92, false, (charSequence62 != null || (string3 = charSequence62.toString()) == null) ? "" : string3, (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.aodRemoteAppIcon", Icon.class), (PendingIntent) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.aodRemoteAppPendingIntent", PendingIntent.class), statusBarNotification.getNotification().extras.getBundle("android.ongoingActivityNoti.nowbarExtraData"), false);
        if (this.mExpandedChipView != null) {
            this.mChipBackground = context.getColor(R.color.ongoing_activity_custom_chip_bg_color);
        }
        if (this.mAodRemoteAppName.length() <= 0) {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("aodRemoteAppName:", this.mAodRemoteAppName, "{OngoingActivityData}");
            Log.i("{OngoingActivityData}", "aodRemoteAppIcon:" + this.mAodRemoteAppIcon);
            Log.i("{OngoingActivityData}", "aodRemoteAppPendingIntent:" + this.mAodRemoteAppPendingIntent);
        }
    }

    public OngoingActivityData(NotificationEntry notificationEntry, Context context, int i, int i2, Icon icon) {
        ArrayList arrayList;
        String string;
        String string2;
        String string3;
        String str = notificationEntry.mKey;
        PendingIntent pendingIntent = notificationEntry.mSbn.getNotification().contentIntent;
        PendingIntent pendingIntent2 = (PendingIntent) notificationEntry.mSbn.getNotification().extras.getParcelable("android.ongoingActivityNoti.nowbarPendingIntentOnSubScreen", PendingIntent.class);
        Icon smallIcon = notificationEntry.mSbn.getNotification().getSmallIcon();
        Notification.Action[] actionArr = notificationEntry.mSbn.getNotification().actions;
        if (actionArr != null) {
            arrayList = new ArrayList();
            ArraysKt___ArraysKt.toCollection(arrayList, actionArr);
        } else {
            arrayList = null;
        }
        ArrayList arrayList2 = arrayList;
        Icon smallIcon2 = notificationEntry.mSbn.getNotification().getSmallIcon();
        Integer numValueOf = Integer.valueOf(i2);
        CharSequence charSequence = notificationEntry.mSbn.getNotification().extras.getCharSequence("android.text");
        String str2 = (charSequence == null || (string3 = charSequence.toString()) == null) ? "No primary info" : string3;
        CharSequence charSequence2 = notificationEntry.mSbn.getNotification().extras.getCharSequence("android.title");
        String str3 = (charSequence2 == null || (string2 = charSequence2.toString()) == null) ? "No secondary info" : string2;
        Boolean bool = Boolean.TRUE;
        CharSequence charSequence3 = notificationEntry.mSbn.getNotification().extras.getCharSequence("android.text");
        this(str, pendingIntent, pendingIntent2, smallIcon, null, i2, i, 0, arrayList2, null, smallIcon2, numValueOf, str2, str3, null, null, bool, (charSequence3 == null || (string = charSequence3.toString()) == null) ? "No primary info" : string, null, 0, "", -1, 0, false, null, null, null, null, null, null, null, null, null, null, new Parcelable[0], null, notificationEntry.mSbn.getNotification().extras.getInt("android.ongoingActivityNoti.progressSegments.progressColor", 0), "", null, null, null, null, notificationEntry.mSbn.getUser().getIdentifier(), notificationEntry.mSbn.getNotification().loadHeaderAppName(context), Long.valueOf(notificationEntry.mSbn.getNotification().when), icon, notificationEntry.mSbn.getPackageName(), notificationEntry, 1, 0, false, "", null, null, null, false);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public OngoingActivityData(MediaOngoingActivityInfo mediaOngoingActivityInfo, NotificationEntry notificationEntry) {
        String creatorPackage;
        String str = notificationEntry.mKey;
        PendingIntent pendingIntent = mediaOngoingActivityInfo.clickIntent;
        PendingIntent pendingIntent2 = (PendingIntent) notificationEntry.mSbn.getNotification().extras.getParcelable("android.ongoingActivityNoti.nowbarPendingIntentOnSubScreen", PendingIntent.class);
        Icon icon = mediaOngoingActivityInfo.appIcon;
        int i = mediaOngoingActivityInfo.bgColor;
        Integer numValueOf = Integer.valueOf(i);
        Boolean bool = Boolean.TRUE;
        Parcelable[] parcelableArr = new Parcelable[0];
        int identifier = notificationEntry.mSbn.getUser().getIdentifier();
        String str2 = mediaOngoingActivityInfo.songTitle;
        String str3 = str2 == null ? "" : str2;
        Long lValueOf = Long.valueOf(notificationEntry.mSbn.getNotification().when);
        PendingIntent pendingIntent3 = mediaOngoingActivityInfo.clickIntent;
        this(str, pendingIntent, pendingIntent2, icon, null, i, -1, -1, null, null, icon, numValueOf, "", "", null, null, bool, mediaOngoingActivityInfo.songTitle, null, 0, "", -1, 0, false, null, null, null, null, null, null, null, null, null, null, parcelableArr, null, 0, "", null, null, null, null, identifier, str3, lValueOf, null, (pendingIntent3 == null || (creatorPackage = pendingIntent3.getCreatorPackage()) == null) ? "" : creatorPackage, notificationEntry, 1, 0, true, "", null, null, null, false);
    }
}
