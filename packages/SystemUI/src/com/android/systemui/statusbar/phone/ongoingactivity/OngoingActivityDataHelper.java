package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.widget.RemoteViews;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.aiagent.AiAgentEffect;
import com.android.systemui.aiagent.AiAgentEffect$geminiStateObserver$1;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.media.SecMediaControlPanel;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.SecSeekBarViewModel$listening$1;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class OngoingActivityDataHelper {
    public static ComponentName baseActivityComponentName;
    public static boolean cardIsShown;
    public static boolean isUpdateNotAllowed;
    public static OngoingActivityData mediaOngoingData;
    public static final OngoingActivityDataHelper INSTANCE = new OngoingActivityDataHelper();
    public static final CopyOnWriteArrayList mOngoingActivityLists = new CopyOnWriteArrayList();
    public static final List observers = new ArrayList();
    public static final List geminiStateObservers = new ArrayList();
    public static final String TAG = "{OngoingActivityDataHelper}";
    public static final List nowbarObservers = new ArrayList();
    public static final ConcurrentHashMap onlyShownNowbarItemMap = new ConcurrentHashMap();
    public static final CopyOnWriteArrayList hiddenOngoingActivityDataList = new CopyOnWriteArrayList();
    public static final CopyOnWriteArrayList pipEnabledComponentNameList = new CopyOnWriteArrayList();
    public static final ConcurrentHashMap pendingOngoingActivityDataList = new ConcurrentHashMap();
    public static final List mediaOngoingActivityObserver = new ArrayList();
    public static final OngoingActivityDataHelper$nowbarWatcher$1 nowbarWatcher = new Object() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper$nowbarWatcher$1
    };
    public static final OngoingActivityDataHelper$mOnHideRawValueChangedListener$1 mOnHideRawValueChangedListener = OngoingActivityDataHelper$mOnHideRawValueChangedListener$1.INSTANCE;

    private OngoingActivityDataHelper() {
    }

    public static NowBarItem convertOngoingActivityData(OngoingActivityData ongoingActivityData) {
        Long l;
        NowBarItem nowBarItem = new NowBarItem();
        nowBarItem.setNotiID(ongoingActivityData.mNotiID);
        nowBarItem.setPendingIntent(ongoingActivityData.mPendingIntent);
        nowBarItem.setPendingIntentOnSubScreen(ongoingActivityData.mNowbarPendingIntentOnSubScreen);
        nowBarItem.setChipIcon(ongoingActivityData.mChipIcon);
        nowBarItem.setExpandedChipView(ongoingActivityData.mExpandedChipView);
        nowBarItem.setChipBackground(ongoingActivityData.mChipBackground);
        nowBarItem.setPrimaryActionNum(ongoingActivityData.mPrimaryActionIndex);
        nowBarItem.setActions(ongoingActivityData.mActions);
        nowBarItem.setActionBgColors(ongoingActivityData.mActionBgColors);
        nowBarItem.setPrimaryInfo(ongoingActivityData.mPrimaryInfo);
        nowBarItem.setSecondaryInfo(ongoingActivityData.mSecondaryInfo);
        nowBarItem.setCardIcon(ongoingActivityData.mCardIcon);
        Integer num = ongoingActivityData.mCardIconBg;
        boolean z = true;
        nowBarItem.setCardIconBg(num != null ? num.intValue() : 1);
        nowBarItem.setCustomExpandedCardView(ongoingActivityData.mCustomExpandedCardView);
        nowBarItem.setExpandedChipText(ongoingActivityData.mExpandedChipText);
        nowBarItem.setChronometerView(ongoingActivityData.mChronometerView);
        nowBarItem.setChronometerTag(ongoingActivityData.mChronometerTag);
        nowBarItem.setOngoingExpandView(ongoingActivityData.mOngoingOAExpandView);
        nowBarItem.setOngoingExpandViewOnSubScreen(ongoingActivityData.mOngoingSubScreenExpandView);
        nowBarItem.setOngoingNowbarView(ongoingActivityData.mOngoingNowbarView);
        nowBarItem.setOngoingNowbarViewOnSubScreen(ongoingActivityData.mOngoingSubScreenNowbarView);
        nowBarItem.setNowbarIcon(ongoingActivityData.mNowbarIcon);
        String str = ongoingActivityData.mNowbarPrimaryInfo;
        if (str == null) {
            str = "";
        }
        nowBarItem.setNowbarPrimaryInfo(str);
        String str2 = ongoingActivityData.mNowbarSecondaryInfo;
        nowBarItem.setNowbarSecondaryInfo(str2 != null ? str2 : "");
        nowBarItem.setUserId(ongoingActivityData.mUserId);
        nowBarItem.setNowBarPackage(ongoingActivityData.mAppName);
        Bundle bundle = ongoingActivityData.mNowbarExtraData;
        if (bundle == null) {
            bundle = new Bundle();
        }
        Icon icon = ongoingActivityData.mAppIcon;
        if (icon != null) {
            bundle.putParcelable("nowbar_key_app_icon", icon);
        }
        int i = ongoingActivityData.mChronometerPosition;
        if (i != 0) {
            bundle.putInt("nowbar_key_chronometerRemoteViewPosition", i);
        }
        OngoingActivityDataHelper ongoingActivityDataHelper = INSTANCE;
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        if (notificationEntry != null) {
            ongoingActivityDataHelper.getClass();
            if (isOnlyShownNowbar(notificationEntry) || (notificationEntry.isPromotedState() && !notificationEntry.mIsLockscreenSecret.booleanValue() && !ongoingActivityData.mIsMediaOngoingData && !notificationEntry.mIsBindCutOff.booleanValue())) {
                z = false;
            }
            bundle.putBoolean("nowbar_key_need_to_blockcard", z);
            bundle.putBoolean("nowbar_key_only_small_icon", notificationEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon"));
            bundle.putBoolean("nowbar_key_need_to_hide_content", notificationEntry.mRawValueHide);
            bundle.putBoolean("nowbar_key_is_group_summary", notificationEntry.mSbn.getNotification().isGroupSummary());
            if (isOnlyShownNowbar(notificationEntry)) {
                bundle.putInt("nowbar_key_call_type", notificationEntry.mSbn.getNotification().extras.getInt("android.callType"));
                bundle.putInt("nowbar_key_call_chip_bg", notificationEntry.mSbn.getNotification().extras.getInt("android.callChipBg"));
            }
        }
        int i2 = ongoingActivityData.mNowbarChronometerPosition;
        if (i2 != 0) {
            bundle.putInt("nowbar_key_chronometerPosition", i2);
        }
        ongoingActivityDataHelper.getClass();
        if (isOnlyShownNowbar(notificationEntry) && (l = ongoingActivityData.mWhen) != null) {
            bundle.putLong("nowbar_key_when", l.longValue());
        }
        if (LsRune.SUBSCREEN_WATCHFACE) {
            bundle.putInt("nowbar_key_screen_type", 3);
        }
        Icon icon2 = ongoingActivityData.mSecondaryInfoIcon;
        if (icon2 != null) {
            bundle.putParcelable("nowbar_key_second_info_icon", icon2);
        }
        nowBarItem.setExtraData(bundle);
        return nowBarItem;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x047f  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x04c5  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x04fd  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x053e  */
    /* JADX WARN: Removed duplicated region for block: B:172:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void createOngoingView(Context context, OngoingActivityData ongoingActivityData) throws Resources.NotFoundException {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Icon icon;
        int i6;
        boolean z;
        String str2;
        CharSequence charSequence;
        String str3;
        String str4;
        int i7;
        Context context2;
        int i8;
        String str5;
        RemoteViews remoteViews;
        int i9;
        int i10;
        ArrayList arrayList;
        boolean z2;
        RemoteViews remoteViews2;
        int i11;
        RemoteViews remoteViews3;
        String str6;
        String str7;
        int i12;
        int i13;
        ArrayList arrayList2;
        OngoingActivityLayoutCreatorImpl ongoingActivityLayoutCreatorImpl = new OngoingActivityLayoutCreatorImpl(context);
        RemoteViews remoteViewsCreateExpandView = ongoingActivityLayoutCreatorImpl.createExpandView(ongoingActivityData, OngoingType.OA);
        remoteViewsCreateExpandView.addFlags(1);
        ongoingActivityData.mOngoingOAExpandView = remoteViewsCreateExpandView;
        RemoteViews remoteViewsCreateExpandView2 = ongoingActivityLayoutCreatorImpl.createExpandView(ongoingActivityData, OngoingType.ENR);
        remoteViewsCreateExpandView2.addFlags(1);
        ongoingActivityData.mOngoingENRExpandView = remoteViewsCreateExpandView2;
        RemoteViews remoteViews4 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_cover_view);
        Display[] displays = ((DisplayManager) ongoingActivityLayoutCreatorImpl.context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        Context contextCreateDisplayContext = displays.length > 1 ? ongoingActivityLayoutCreatorImpl.context.createDisplayContext(displays[1]) : ongoingActivityLayoutCreatorImpl.context;
        RemoteViews remoteViews5 = ongoingActivityData.mSubScreenCustomExpandedCardView;
        CharSequence charSequence2 = ongoingActivityData.mSecondaryInfo;
        Long l = ongoingActivityData.mWhen;
        CharSequence charSequence3 = ongoingActivityData.mDescription;
        String str8 = ongoingActivityData.mPrimaryInfo;
        int i14 = ongoingActivityData.mUserId;
        String str9 = ongoingActivityData.mNotiID;
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        if (remoteViews5 != null) {
            remoteViews4.addView(R.id.ongoing_activity_expand_custom_content, remoteViews5);
            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_custom_content, 0);
            Log.i("{OngoingActivityLayoutCreator}", "createSubScreenExpandView: use full custom view (" + str9 + ")");
            remoteViews4.removeAllViewsExceptId(R.id.ongoing_activity_expand_view, R.id.ongoing_activity_expand_custom_content);
        } else {
            RemoteViews remoteViews6 = ongoingActivityData.mCustomExpandedCardView;
            if (remoteViews6 != null) {
                remoteViews4.addView(R.id.ongoing_activity_expand_custom_content, remoteViews6);
                remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_custom_content, 0);
                Log.i("{OngoingActivityLayoutCreator}", "mSubScreenCustomExpandedCardView is null - createSubScreenExpandView: use full custom view (" + str9 + ")");
                remoteViews4.removeAllViewsExceptId(R.id.ongoing_activity_expand_view, R.id.ongoing_activity_expand_custom_content);
            } else {
                OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
                Context context3 = ongoingActivityLayoutCreatorImpl.context;
                ongoingActivityLayoutUtil.getClass();
                OngoingActivityLayoutUtil.setOngoingNotificationIcon(ongoingActivityData, remoteViews4, R.id.ongoing_activity_expand_left_image, context3);
                boolean zIsPromotedState = notificationEntry.isPromotedState();
                boolean zIsActionStyle = OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData);
                boolean z3 = ongoingActivityData.mActions != null;
                int i15 = ongoingActivityData.mProgress;
                boolean z4 = i15 > -1;
                boolean zIsPrimaryChronometer = OngoingActivityLayoutUtil.isPrimaryChronometer(ongoingActivityData);
                boolean z5 = z4;
                boolean zIsManagedProfile = OngoingActivityLayoutUtil.isManagedProfile(notificationEntry.mSbn, ongoingActivityLayoutCreatorImpl.context, i14);
                if (!zIsActionStyle) {
                    str = ")";
                    remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_content_horizontal_root, 5, R.dimen.ongoing_activity_expanded_view_content_end_margin);
                    if (z3 || z5) {
                        remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_left_image, 3, 0);
                    }
                    if (zIsManagedProfile) {
                        i = R.id.ongoing_activity_expand_primary;
                        i2 = 0;
                    } else if (zIsPromotedState) {
                        i2 = R.dimen.oa_expanded_view_first_icon_margin;
                        i = R.id.ongoing_activity_expand_primary;
                    } else {
                        i = R.id.ongoing_activity_expand_primary;
                        i2 = R.dimen.oa_expanded_view_content_vertical_margin;
                    }
                    remoteViews4.setViewLayoutMarginDimen(i, 1, i2);
                    remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_contents_vertical_margin_parent, 3, (ongoingActivityData.mActions == null && zIsPromotedState) ? R.dimen.oa_expanded_view_no_action_content_bottom_margin : R.dimen.oa_expanded_view_content_vertical_margin);
                    if (zIsManagedProfile) {
                        remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_contents_vertical_margin_parent, 1, R.dimen.oa_expanded_view_content_vertical_margin);
                        i3 = 0;
                        remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_right_image, 1, 0);
                    }
                    float dimension = contextCreateDisplayContext.getResources().getDimension(R.dimen.ongoing_activity_sub_screen_card_item_header_app_label_text_size);
                    remoteViews4.setTextViewText(android.R.id.beforeDescendants, ongoingActivityData.mAppName);
                    remoteViews4.setTextViewTextSize(android.R.id.beforeDescendants, i3, dimension);
                    remoteViews4.setTextColor(android.R.id.beforeDescendants, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_expand_header_text_color));
                    ongoingActivityLayoutCreatorImpl.bindTime(remoteViews4, l);
                    if (str8.length() <= 0) {
                        remoteViews4.setTextViewText(R.id.ongoing_activity_expand_primary, str8);
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_primary, i3);
                    }
                    if (StringsKt__StringsKt.trim(charSequence2).toString().length() <= 0) {
                        if (OngoingActivityLayoutUtil.isPrimaryChronometer(ongoingActivityData) && OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData) && notificationEntry.isPromotedState()) {
                            remoteViews4.removeAllViewsExceptId(R.id.ongoing_activity_expand_secondary_text_container, R.id.ongoing_activity_expand_secondary_text_below_chronometer);
                            remoteViews4.setTextViewText(R.id.ongoing_activity_expand_secondary_text_below_chronometer, charSequence2);
                            i4 = 0;
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_secondary_text_below_chronometer, 0);
                        } else {
                            i4 = 0;
                            remoteViews4.removeAllViewsExceptId(R.id.ongoing_activity_expand_secondary_text_container, R.id.ongoing_activity_expand_secondary_text);
                            remoteViews4.setTextViewText(R.id.ongoing_activity_expand_secondary_text, charSequence2);
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_secondary_text, 0);
                        }
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_secondary_container, i4);
                    } else {
                        i4 = 0;
                    }
                    if (StringsKt__StringsKt.trim(charSequence3).toString().length() <= 0) {
                        remoteViews4.setTextViewText(R.id.ongoing_activity_expand_description, charSequence3);
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_description, i4);
                        i5 = 8;
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_secondary_container, 8);
                    } else {
                        i5 = 8;
                    }
                    if (OngoingActivityLayoutCreatorImpl.canShowSecondaryIcon(ongoingActivityData)) {
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_secondary_info_icon, i5);
                    } else {
                        OngoingActivityLayoutCreatorImpl.bindSecondaryInfoIcon(remoteViews4, ongoingActivityData.mSecondaryInfoIcon);
                    }
                    icon = ongoingActivityData.mSecondIcon;
                    if (icon == null) {
                        remoteViews4.setImageViewIcon(R.id.ongoing_activity_expand_right_image, icon);
                        i6 = 0;
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_right_image, 0);
                    } else {
                        i6 = 0;
                    }
                    if (i15 <= -1) {
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_upper_progress_container, i6);
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_progress_right_icon, 8);
                        Parcelable[] parcelableArr = ongoingActivityData.mProgressSegments;
                        int length = parcelableArr.length;
                        int i16 = ongoingActivityData.mProgressMax;
                        if (length > 0) {
                            float f = i15;
                            if (i16 > 0) {
                                f = (f * 100) / i16;
                            }
                            str2 = "{OngoingActivityLayoutCreator}";
                            charSequence = charSequence3;
                            str4 = str;
                            i7 = -1;
                            context2 = contextCreateDisplayContext;
                            remoteViews4.setImageViewBitmap(R.id.ongoing_activity_expand_tinted_upper_progress, new OngoingSeekBarCreator(contextCreateDisplayContext, parcelableArr, f, ongoingActivityData.mProgressSegmentIcon, ongoingActivityData.mProgressColor).makeImage(OngoingType.SUB));
                            z = 0;
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_tinted_upper_progress, 0);
                            str3 = str9;
                        } else {
                            str2 = "{OngoingActivityLayoutCreator}";
                            charSequence = charSequence3;
                            str3 = str9;
                            str4 = str;
                            context2 = contextCreateDisplayContext;
                            z = 0;
                            i7 = -1;
                            if (i16 == 0) {
                                i16 = 100;
                            }
                            remoteViews4.setProgressBar(R.id.ongoing_activity_expand_upper_progress, i16, i15, ongoingActivityData.mProgressIndeterminate);
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_upper_progress, 0);
                        }
                    } else {
                        z = i6;
                        str2 = "{OngoingActivityLayoutCreator}";
                        charSequence = charSequence3;
                        str3 = str9;
                        str4 = str;
                        i7 = -1;
                        context2 = contextCreateDisplayContext;
                    }
                    i8 = ongoingActivityData.mActionType;
                    if (i8 != i7 || (arrayList = ongoingActivityData.mActions) == null) {
                        str5 = "setMaxLines";
                    } else {
                        if (i8 == 0) {
                            ongoingActivityLayoutCreatorImpl.updateIconButtonRemoteView(arrayList, remoteViews4, z);
                            remoteViews4.setTextColor(R.id.ongoing_activity_expand_secondary_text, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                            str5 = "setMaxLines";
                            z2 = true;
                            remoteViews4.setInt(R.id.ongoing_activity_expand_primary, str5, 1);
                            remoteViews4.setInt(R.id.ongoing_activity_expand_secondary_text, str5, 1);
                        } else {
                            str5 = "setMaxLines";
                            z2 = true;
                        }
                        if (i8 == z2) {
                            int size = arrayList.size();
                            int i17 = 0;
                            while (i17 < size) {
                                remoteViews4.addView(R.id.ongoing_activity_expand_buttons, ongoingActivityLayoutCreatorImpl.makeTextButtonRemoteView((Notification.Action) arrayList.get(i17), context2, z2));
                                i17++;
                                size = size;
                                z2 = true;
                            }
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_buttons, 0);
                        }
                    }
                    if (zIsManagedProfile) {
                        remoteViews4.setViewVisibility(R.id.ongoing_activity_expanded_header_container, 8);
                    } else {
                        ongoingActivityLayoutCreatorImpl.bindProfileBadge(remoteViews4, i14);
                    }
                    boolean zIsPromotedState2 = notificationEntry.isPromotedState();
                    float fUpToLargeFontSize = OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.oa_cover_view_info_chip_size));
                    remoteViews4.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize, 0);
                    remoteViews4.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize, 0);
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_primary, 0, OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(!zIsPromotedState2 ? R.dimen.ongoing_activity_sub_screen_card_item_promoted_main_text_size : R.dimen.ongoing_activity_sub_screen_card_item_main_text_size)));
                    float fUpToLargeFontSize2 = OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_sub_screen_card_item_sub_text_size));
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_secondary_text, 0, fUpToLargeFontSize2);
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_description, 0, fUpToLargeFontSize2);
                    float fUpToLargeFontSize3 = OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_sub_screen_card_item_header_app_label_text_size));
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_secondary_text_below_chronometer, 0, fUpToLargeFontSize3);
                    remoteViews4.setTextViewTextSize(android.R.id.beforeDescendants, 0, fUpToLargeFontSize3);
                    remoteViews4.setTextViewTextSize(16909968, 0, OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_sub_screen_card_item_header_time_text_size)));
                    remoteViews = ongoingActivityData.mChronometerView;
                    if (remoteViews != null) {
                        int i18 = ongoingActivityData.mChronometerPosition;
                        if (i18 == 1) {
                            i10 = 8;
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_primary, 8);
                            remoteViews4.addView(R.id.ongoing_activity_expand_primary_container, remoteViews);
                        } else {
                            i10 = 8;
                        }
                        if (i18 == 2) {
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_secondary_container, 0);
                            remoteViews4.removeAllViews(R.id.ongoing_activity_expand_secondary_text_container);
                            remoteViews4.setViewVisibility(R.id.ongoing_activity_expand_secondary_text_container, i10);
                            remoteViews4.addView(R.id.ongoing_activity_expand_secondary_container, remoteViews);
                        }
                    }
                    i9 = 1;
                    remoteViews4.addFlags(i9);
                    ongoingActivityData.mOngoingSubScreenExpandView = remoteViews4;
                    remoteViews2 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_collapsed_view);
                    OngoingActivityLayoutUtil ongoingActivityLayoutUtil2 = OngoingActivityLayoutUtil.INSTANCE;
                    Context context4 = ongoingActivityLayoutCreatorImpl.context;
                    ongoingActivityLayoutUtil2.getClass();
                    OngoingActivityLayoutUtil.setOngoingNotificationIcon(ongoingActivityData, remoteViews2, R.id.ongoing_activity_collapsed_icon, context4);
                    if (str8.length() > 0) {
                        remoteViews2.setTextViewText(android.R.id.title, str8);
                        ongoingActivityLayoutCreatorImpl.bindTime(remoteViews2, l);
                        if (OngoingActivityLayoutUtil.isManagedProfile(notificationEntry.mSbn, ongoingActivityLayoutCreatorImpl.context, i14)) {
                            ongoingActivityLayoutCreatorImpl.bindProfileBadge(remoteViews2, i14);
                        }
                    }
                    if (StringsKt__StringsKt.trim(charSequence2).toString().length() > 0) {
                        remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_secondary_container, 0);
                        remoteViews2.setTextViewText(R.id.ongoing_activity_collapsed_secondary_text, charSequence2);
                        remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_secondary_text, 0);
                    }
                    if (StringsKt__StringsKt.trim(charSequence).toString().length() > 0) {
                        remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_secondary_container, 0);
                        remoteViews2.setTextViewText(R.id.ongoing_activity_collapsed_secondary_text, charSequence);
                        remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_secondary_text, 0);
                    }
                    if (OngoingActivityLayoutCreatorImpl.canShowSecondaryIcon(ongoingActivityData)) {
                        OngoingActivityLayoutCreatorImpl.bindSecondaryInfoIcon(remoteViews2, ongoingActivityData.mSecondaryInfoIcon);
                    } else {
                        remoteViews2.setViewVisibility(R.id.ongoing_activity_secondary_info_icon, 8);
                    }
                    i11 = ongoingActivityData.mActionType;
                    if (i11 != -1 && (arrayList2 = ongoingActivityData.mActions) != null && i11 == 0) {
                        remoteViews2.addView(R.id.ongoing_activity_collapsed_icon_buttons_end, ongoingActivityLayoutCreatorImpl.makeIconButtons(arrayList2));
                        remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_icon_buttons_end, 0);
                        remoteViews2.setViewVisibility(16909968, 8);
                        remoteViews2.setViewVisibility(android.R.id.smallIcon, 8);
                        remoteViews2.setTextColor(R.id.ongoing_activity_collapsed_secondary_text, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                        remoteViews2.setInt(R.id.ongoing_activity_collapsed_secondary_text, str5, 1);
                    }
                    remoteViews2.setBoolean(android.R.id.flagRetrieveInteractiveWindows, "setExpanded", false);
                    OngoingActivityLayoutUtil.setExpandButtonColor(remoteViews2);
                    ongoingActivityLayoutCreatorImpl.updateCollapsedViewFontSize(remoteViews2);
                    remoteViews3 = ongoingActivityData.mChronometerView;
                    if (remoteViews3 != null) {
                        int i19 = ongoingActivityData.mChronometerPosition;
                        if (i19 == 1) {
                            i12 = 8;
                            remoteViews2.setViewVisibility(android.R.id.resolver_list, 8);
                            i13 = 0;
                            remoteViews2.addView(R.id.ongoing_activity_collapsed_primary_container, remoteViews3, 0);
                        } else {
                            i12 = 8;
                            i13 = 0;
                        }
                        if (i19 == 2) {
                            remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_secondary_container, i13);
                            remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_secondary_text, i12);
                            remoteViews2.addView(R.id.ongoing_activity_collapsed_secondary_container, remoteViews3);
                        }
                    }
                    remoteViews2.addFlags(1);
                    ongoingActivityData.mOngoingCollapsedView = remoteViews2;
                    if (ongoingActivityData.mCustomNowBarView != null) {
                        RemoteViews remoteViews7 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_nowbar_view);
                        RemoteViews remoteViews8 = ongoingActivityData.mCustomNowBarView;
                        if (remoteViews8 != null) {
                            remoteViews7.addView(R.id.ongoing_activity_nowbar_custom_content, remoteViews8);
                            remoteViews7.setViewVisibility(R.id.ongoing_activity_nowbar_custom_content, 0);
                            StringBuilder sb = new StringBuilder("createNowBarView: use full custom view (");
                            sb.append(str3);
                            str7 = str4;
                            sb.append(str7);
                            str6 = str2;
                            Log.i(str6, sb.toString());
                            remoteViews7.removeAllViewsExceptId(R.id.ongoing_activity_nowbar_view, R.id.ongoing_activity_nowbar_custom_content);
                        } else {
                            str6 = str2;
                            str7 = str4;
                        }
                        ongoingActivityData.mOngoingNowbarView = remoteViews7;
                    } else {
                        str6 = str2;
                        str7 = str4;
                    }
                    if (ongoingActivityData.mSubScreenCustomNowBarView != null) {
                        RemoteViews remoteViews9 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_nowbar_view);
                        RemoteViews remoteViews10 = ongoingActivityData.mSubScreenCustomNowBarView;
                        if (remoteViews10 != null) {
                            remoteViews9.addView(R.id.ongoing_activity_nowbar_custom_content, remoteViews10);
                            remoteViews9.setViewVisibility(R.id.ongoing_activity_nowbar_custom_content, 0);
                            Log.i(str6, "createSubScreenNowBarView: use full custom view (" + str3 + str7);
                            remoteViews9.removeAllViewsExceptId(R.id.ongoing_activity_nowbar_view, R.id.ongoing_activity_nowbar_custom_content);
                        }
                        ongoingActivityData.mOngoingSubScreenNowbarView = remoteViews9;
                        return;
                    }
                    return;
                }
                remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_content_horizontal_root, 5, R.dimen.ongoing_activity_expanded_cover_action_style_end_margin);
                remoteViews4.setViewLayoutMarginDimen(R.id.ongoing_activity_contents_vertical_margin_parent, 1, (zIsPromotedState && zIsPrimaryChronometer) ? R.dimen.oa_cover_view_action_style_chronometer_primary_top_margin_promoted : zIsPromotedState ? R.dimen.oa_cover_view_action_style_primary_top_margin_promoted : R.dimen.oa_cover_view_action_style_primary_top_margin_not_promoted);
                str = ")";
                i3 = 0;
                float dimension2 = contextCreateDisplayContext.getResources().getDimension(R.dimen.ongoing_activity_sub_screen_card_item_header_app_label_text_size);
                remoteViews4.setTextViewText(android.R.id.beforeDescendants, ongoingActivityData.mAppName);
                remoteViews4.setTextViewTextSize(android.R.id.beforeDescendants, i3, dimension2);
                remoteViews4.setTextColor(android.R.id.beforeDescendants, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_expand_header_text_color));
                ongoingActivityLayoutCreatorImpl.bindTime(remoteViews4, l);
                if (str8.length() <= 0) {
                }
                if (StringsKt__StringsKt.trim(charSequence2).toString().length() <= 0) {
                }
                if (StringsKt__StringsKt.trim(charSequence3).toString().length() <= 0) {
                }
                if (OngoingActivityLayoutCreatorImpl.canShowSecondaryIcon(ongoingActivityData)) {
                }
                icon = ongoingActivityData.mSecondIcon;
                if (icon == null) {
                }
                if (i15 <= -1) {
                }
                i8 = ongoingActivityData.mActionType;
                if (i8 != i7) {
                    str5 = "setMaxLines";
                    if (zIsManagedProfile) {
                    }
                    boolean zIsPromotedState22 = notificationEntry.isPromotedState();
                    float fUpToLargeFontSize4 = OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.oa_cover_view_info_chip_size));
                    remoteViews4.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize4, 0);
                    remoteViews4.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize4, 0);
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_primary, 0, OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(!zIsPromotedState22 ? R.dimen.ongoing_activity_sub_screen_card_item_promoted_main_text_size : R.dimen.ongoing_activity_sub_screen_card_item_main_text_size)));
                    float fUpToLargeFontSize22 = OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_sub_screen_card_item_sub_text_size));
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_secondary_text, 0, fUpToLargeFontSize22);
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_description, 0, fUpToLargeFontSize22);
                    float fUpToLargeFontSize32 = OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_sub_screen_card_item_header_app_label_text_size));
                    remoteViews4.setTextViewTextSize(R.id.ongoing_activity_expand_secondary_text_below_chronometer, 0, fUpToLargeFontSize32);
                    remoteViews4.setTextViewTextSize(android.R.id.beforeDescendants, 0, fUpToLargeFontSize32);
                    remoteViews4.setTextViewTextSize(16909968, 0, OngoingActivityLayoutCreatorImpl.upToLargeFontSize(context2.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_sub_screen_card_item_header_time_text_size)));
                    remoteViews = ongoingActivityData.mChronometerView;
                    if (remoteViews != null) {
                    }
                    i9 = 1;
                }
                remoteViews4.addFlags(i9);
                ongoingActivityData.mOngoingSubScreenExpandView = remoteViews4;
                remoteViews2 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_collapsed_view);
                OngoingActivityLayoutUtil ongoingActivityLayoutUtil22 = OngoingActivityLayoutUtil.INSTANCE;
                Context context42 = ongoingActivityLayoutCreatorImpl.context;
                ongoingActivityLayoutUtil22.getClass();
                OngoingActivityLayoutUtil.setOngoingNotificationIcon(ongoingActivityData, remoteViews2, R.id.ongoing_activity_collapsed_icon, context42);
                if (str8.length() > 0) {
                }
                if (StringsKt__StringsKt.trim(charSequence2).toString().length() > 0) {
                }
                if (StringsKt__StringsKt.trim(charSequence).toString().length() > 0) {
                }
                if (OngoingActivityLayoutCreatorImpl.canShowSecondaryIcon(ongoingActivityData)) {
                }
                i11 = ongoingActivityData.mActionType;
                if (i11 != -1) {
                    remoteViews2.addView(R.id.ongoing_activity_collapsed_icon_buttons_end, ongoingActivityLayoutCreatorImpl.makeIconButtons(arrayList2));
                    remoteViews2.setViewVisibility(R.id.ongoing_activity_collapsed_icon_buttons_end, 0);
                    remoteViews2.setViewVisibility(16909968, 8);
                    remoteViews2.setViewVisibility(android.R.id.smallIcon, 8);
                    remoteViews2.setTextColor(R.id.ongoing_activity_collapsed_secondary_text, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                    remoteViews2.setInt(R.id.ongoing_activity_collapsed_secondary_text, str5, 1);
                }
                remoteViews2.setBoolean(android.R.id.flagRetrieveInteractiveWindows, "setExpanded", false);
                OngoingActivityLayoutUtil.setExpandButtonColor(remoteViews2);
                ongoingActivityLayoutCreatorImpl.updateCollapsedViewFontSize(remoteViews2);
                remoteViews3 = ongoingActivityData.mChronometerView;
                if (remoteViews3 != null) {
                }
                remoteViews2.addFlags(1);
                ongoingActivityData.mOngoingCollapsedView = remoteViews2;
                if (ongoingActivityData.mCustomNowBarView != null) {
                }
                if (ongoingActivityData.mSubScreenCustomNowBarView != null) {
                }
            }
        }
        str4 = ")";
        str2 = "{OngoingActivityLayoutCreator}";
        charSequence = charSequence3;
        str5 = "setMaxLines";
        i9 = 1;
        str3 = str9;
        remoteViews4.addFlags(i9);
        ongoingActivityData.mOngoingSubScreenExpandView = remoteViews4;
        remoteViews2 = new RemoteViews(ongoingActivityLayoutCreatorImpl.context.getPackageName(), R.layout.ongoing_collapsed_view);
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil222 = OngoingActivityLayoutUtil.INSTANCE;
        Context context422 = ongoingActivityLayoutCreatorImpl.context;
        ongoingActivityLayoutUtil222.getClass();
        OngoingActivityLayoutUtil.setOngoingNotificationIcon(ongoingActivityData, remoteViews2, R.id.ongoing_activity_collapsed_icon, context422);
        if (str8.length() > 0) {
        }
        if (StringsKt__StringsKt.trim(charSequence2).toString().length() > 0) {
        }
        if (StringsKt__StringsKt.trim(charSequence).toString().length() > 0) {
        }
        if (OngoingActivityLayoutCreatorImpl.canShowSecondaryIcon(ongoingActivityData)) {
        }
        i11 = ongoingActivityData.mActionType;
        if (i11 != -1) {
        }
        remoteViews2.setBoolean(android.R.id.flagRetrieveInteractiveWindows, "setExpanded", false);
        OngoingActivityLayoutUtil.setExpandButtonColor(remoteViews2);
        ongoingActivityLayoutCreatorImpl.updateCollapsedViewFontSize(remoteViews2);
        remoteViews3 = ongoingActivityData.mChronometerView;
        if (remoteViews3 != null) {
        }
        remoteViews2.addFlags(1);
        ongoingActivityData.mOngoingCollapsedView = remoteViews2;
        if (ongoingActivityData.mCustomNowBarView != null) {
        }
        if (ongoingActivityData.mSubScreenCustomNowBarView != null) {
        }
    }

    public static void dataDump(OngoingActivityData ongoingActivityData, PrintWriter printWriter) {
        Integer numValueOf;
        printWriter.println("        appName : " + ongoingActivityData.mAppName);
        printWriter.println("        key : " + ongoingActivityData.mNotiID);
        MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("        style : "), ongoingActivityData.mStyle, printWriter, "        userId : "), ongoingActivityData.mUserId, printWriter);
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        promoted : ", notificationEntry.isPromotedState());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        custom : ", ongoingActivityData.mCustomExpandedCardView != null);
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "        chipBackground : ", Integer.toHexString(ongoingActivityData.mChipBackground));
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasChipText : ", ongoingActivityData.mExpandedChipText != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasChronometer : ", ongoingActivityData.mChronometerView != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasSegmentProgress : ", ongoingActivityData.mProgressSegments != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasNowBarIcon : ", ongoingActivityData.mNowbarIcon != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasNowBarPrimary : ", ongoingActivityData.mNowbarPrimaryInfo != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasNowBarSecondary : ", ongoingActivityData.mNowbarSecondaryInfo != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasRightIcon : ", ongoingActivityData.mSecondIcon != null);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        hasDescription : ", ongoingActivityData.mDescription != null);
        ArrayList arrayList = ongoingActivityData.mActions;
        if (arrayList == null || arrayList.size() <= 0) {
            numValueOf = 0;
        } else {
            ArrayList arrayList2 = ongoingActivityData.mActions;
            numValueOf = arrayList2 != null ? Integer.valueOf(arrayList2.size()) : null;
        }
        printWriter.println("        action : " + numValueOf);
        printWriter.println("        needMarquee : " + ongoingActivityData.mNeedMarquee);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "        useSmallIcon : ", notificationEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon"));
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("        isSensitive : ", notificationEntry.mSensitive.getValue(), printWriter);
        printWriter.println("        isSummary : " + notificationEntry.mSbn.getNotification().isGroupSummary());
        printWriter.println("        ");
    }

    public static void geminiPlayStateChanged(boolean z) {
        boolean z2;
        ArrayList arrayList = (ArrayList) geminiStateObservers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            AiAgentEffect aiAgentEffect = ((AiAgentEffect$geminiStateObserver$1) obj).this$0;
            synchronized (aiAgentEffect.lock) {
                z2 = z;
                aiAgentEffect.setState(AiAgentEffect.State.copy$default(aiAgentEffect.state, false, false, z2, false, false, false, false, false, IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut));
                Unit unit = Unit.INSTANCE;
            }
            z = z2;
        }
    }

    public static OngoingActivityData getDataByIndex(int i) {
        return (OngoingActivityData) mOngoingActivityLists.get(i);
    }

    public static OngoingActivityData getMediaData() {
        Iterator it = mOngoingActivityLists.iterator();
        while (it.hasNext()) {
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it.next();
            if (ongoingActivityData.mIsMediaOngoingData) {
                return ongoingActivityData;
            }
        }
        Iterator it2 = hiddenOngoingActivityDataList.iterator();
        while (it2.hasNext()) {
            OngoingActivityData ongoingActivityData2 = (OngoingActivityData) it2.next();
            if (ongoingActivityData2.mIsMediaOngoingData) {
                return ongoingActivityData2;
            }
        }
        return null;
    }

    public static OngoingActivityData getOngoingActivityDataByKey(String str) {
        Iterator it = mOngoingActivityLists.iterator();
        while (it.hasNext()) {
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it.next();
            if (ongoingActivityData.mNotiID.equals(str)) {
                return ongoingActivityData;
            }
        }
        Iterator it2 = hiddenOngoingActivityDataList.iterator();
        while (it2.hasNext()) {
            OngoingActivityData ongoingActivityData2 = (OngoingActivityData) it2.next();
            if (ongoingActivityData2.mNotiID.equals(str)) {
                return ongoingActivityData2;
            }
        }
        return null;
    }

    public static OngoingActivityData getPendingOngoingActivityData(String str) {
        return (OngoingActivityData) pendingOngoingActivityDataList.get(str);
    }

    public static boolean isExceptionalOngoingActivity(NotificationEntry notificationEntry) {
        String id;
        String packageName = notificationEntry.mSbn.getPackageName();
        int id2 = notificationEntry.mSbn.getId();
        NotificationChannel channel = notificationEntry.mRanking.getChannel();
        if (channel == null || (id = channel.getId()) == null) {
            id = "null";
        }
        return "com.google.android.googlequicksearchbox".equals(packageName) && 100 == id2 && "convmode_notification_channel_id".equals(id);
    }

    public static boolean isOnlyShownNowbar(NotificationEntry notificationEntry) {
        if (isExceptionalOngoingActivity(notificationEntry)) {
            return false;
        }
        return Intrinsics.areEqual(notificationEntry.mSbn.getTag(), "noti_DoNotDisturb") || notificationEntry.mSbn.getNotification().isStyle(Notification.CallStyle.class);
    }

    public static void notifyRankingStateChanged(String str) {
        OngoingActivityData ongoingActivityDataByKey = getOngoingActivityDataByKey(str);
        if (ongoingActivityDataByKey != null) {
            pendingOngoingActivityDataList.put(ongoingActivityDataByKey.mNotiID, ongoingActivityDataByKey);
            NotificationEntry notificationEntry = ongoingActivityDataByKey.mNotificationEntry;
            boolean zIsPromotedState = notificationEntry.isPromotedState();
            boolean zBooleanValue = notificationEntry.mIsLockscreenSecret.booleanValue();
            StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("notify ongoing ranking changed ", str, " : promoted=", ", isLockscreenSecret=", zIsPromotedState);
            sbM.append(zBooleanValue);
            Log.d(TAG, sbM.toString());
            INSTANCE.getClass();
            notifyUpdateItemNowbarObservers(convertOngoingActivityData(ongoingActivityDataByKey));
        }
    }

    public static void notifyRemoveItemNowbarObservers(String str) {
        ArrayList arrayList = (ArrayList) nowbarObservers;
        Log.i(TAG, CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(arrayList.size(), "notifyRemoveItemNowbarObservers ", str, " size = "));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) obj;
            NowBarItem nowBarItem = new NowBarItem();
            nowBarItem.setNotiID(str);
            if (LsRune.SUBSCREEN_WATCHFACE) {
                Bundle bundle = new Bundle();
                bundle.putInt("nowbar_key_screen_type", 3);
                nowBarItem.setExtraData(bundle);
            }
            faceWidgetNotificationControllerWrapper.removeItem(nowBarItem);
        }
    }

    public static void notifyUpdateItemNowbarObservers(NowBarItem nowBarItem) {
        ArrayList arrayList = (ArrayList) nowbarObservers;
        Log.i(TAG, "notifyUpdateItemNowbarObservers " + nowBarItem + " size = " + arrayList.size());
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((FaceWidgetNotificationControllerWrapper) obj).updateItem(nowBarItem);
        }
    }

    public static void notifyUpdateObservers() {
        ArrayList arrayList = (ArrayList) observers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((IOngoingObserver) obj).update$1$1();
        }
    }

    public static boolean onAsyncInflationFinished(NotificationEntry notificationEntry, NotificationLockscreenUserManager notificationLockscreenUserManager) {
        Pair pair;
        OngoingActivityData ongoingActivityData = (OngoingActivityData) pendingOngoingActivityDataList.remove(notificationEntry.mKey);
        boolean z = false;
        int i = 0;
        int i2 = 0;
        if (ongoingActivityData != null) {
            String str = TAG;
            Log.d(str, "onAsyncInflationFinished. oa DATA: " + ongoingActivityData);
            INSTANCE.getClass();
            int size = mOngoingActivityLists.size();
            ComponentName componentName = baseActivityComponentName;
            StringBuilder sb = new StringBuilder("addOrUpdateOngoingActivityInternal() mNotiID = ");
            String str2 = ongoingActivityData.mNotiID;
            sb.append(str2);
            sb.append(", oa size = ");
            sb.append(size);
            sb.append(", baseActivityComponentName = ");
            sb.append(componentName);
            Log.i(str, sb.toString());
            Iterator it = hiddenOngoingActivityDataList.iterator();
            OngoingActivityData ongoingActivityData2 = null;
            OngoingActivityData ongoingActivityData3 = null;
            while (it.hasNext()) {
                OngoingActivityData ongoingActivityData4 = (OngoingActivityData) it.next();
                if (ongoingActivityData4.mNotiID.equals(str2)) {
                    ongoingActivityData3 = ongoingActivityData4;
                }
            }
            if (ongoingActivityData3 != null) {
                CopyOnWriteArrayList copyOnWriteArrayList = hiddenOngoingActivityDataList;
                int iIndexOf = copyOnWriteArrayList.indexOf(ongoingActivityData3);
                if (iIndexOf != -1) {
                    copyOnWriteArrayList.remove(iIndexOf);
                    copyOnWriteArrayList.add(iIndexOf, ongoingActivityData);
                } else {
                    Log.e(str, "hiddenOngoingActivityDataList get index fail");
                }
                Boolean bool = Boolean.FALSE;
                pair = new Pair(bool, bool);
            } else {
                Iterator it2 = mOngoingActivityLists.iterator();
                while (it2.hasNext()) {
                    OngoingActivityData ongoingActivityData5 = (OngoingActivityData) it2.next();
                    if (ongoingActivityData5.mNotiID.equals(str2)) {
                        ongoingActivityData2 = ongoingActivityData5;
                    }
                }
                if (ongoingActivityData2 != null) {
                    CopyOnWriteArrayList copyOnWriteArrayList2 = mOngoingActivityLists;
                    int iIndexOf2 = copyOnWriteArrayList2.indexOf(ongoingActivityData2);
                    if (iIndexOf2 != -1) {
                        copyOnWriteArrayList2.remove(iIndexOf2);
                        copyOnWriteArrayList2.add(iIndexOf2, ongoingActivityData);
                        pair = new Pair(Boolean.TRUE, Boolean.FALSE);
                    } else {
                        Log.e(str, "mOngoingActivityLists get index fail");
                        Boolean bool2 = Boolean.FALSE;
                        pair = new Pair(bool2, bool2);
                    }
                } else if (shouldHide(notificationLockscreenUserManager, ongoingActivityData)) {
                    hiddenOngoingActivityDataList.add(ongoingActivityData);
                    Boolean bool3 = Boolean.FALSE;
                    pair = new Pair(bool3, bool3);
                } else {
                    mOngoingActivityLists.addFirst(ongoingActivityData);
                    Boolean bool4 = Boolean.TRUE;
                    pair = new Pair(bool4, bool4);
                }
            }
            if (((Boolean) pair.getFirst()).booleanValue() && !isUpdateNotAllowed) {
                if (((Boolean) pair.getSecond()).booleanValue()) {
                    ArrayList arrayList = (ArrayList) observers;
                    int size2 = arrayList.size();
                    while (i < size2) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((IOngoingObserver) obj).add$1();
                    }
                } else {
                    ArrayList arrayList2 = (ArrayList) observers;
                    int size3 = arrayList2.size();
                    while (i2 < size3) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        ((IOngoingObserver) obj2).update(str2);
                    }
                }
            }
            notifyUpdateItemNowbarObservers(convertOngoingActivityData(ongoingActivityData));
            z = true;
            z = true;
            if (ongoingActivityData.mIsMediaOngoingData) {
                mediaOngoingData = ongoingActivityData;
            }
        }
        return z;
    }

    public static void recreateOngoingActivity(Context context, NotificationEntry notificationEntry) {
        OngoingActivityData ongoingActivityDataByKey = getOngoingActivityDataByKey(notificationEntry.mKey);
        if (ongoingActivityDataByKey != null) {
            INSTANCE.getClass();
            createOngoingView(context, ongoingActivityDataByKey);
        }
    }

    public static void reinflateOngoingActivity() throws Resources.NotFoundException {
        OngoingActivityDataHelper ongoingActivityDataHelper;
        Iterator it = onlyShownNowbarItemMap.values().iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            ongoingActivityDataHelper = INSTANCE;
            if (!zHasNext) {
                break;
            }
            ExpandableNotificationRow expandableNotificationRow = ((OngoingActivityData) it.next()).mNotificationEntry.row;
            if (expandableNotificationRow != null) {
                Context context = expandableNotificationRow.getContext();
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                ongoingActivityDataHelper.getClass();
                recreateOngoingActivity(context, notificationEntry);
                Context context2 = expandableNotificationRow.getContext();
                NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
                OngoingActivityData ongoingActivityData = new OngoingActivityData(notificationEntry2, notificationEntry2.mSbn, context2);
                onlyShownNowbarItemMap.put(notificationEntry2.mKey, ongoingActivityData);
                ongoingActivityDataHelper.getClass();
                createOngoingView(context2, ongoingActivityData);
                notifyUpdateItemNowbarObservers(convertOngoingActivityData(ongoingActivityData));
            }
        }
        LinkedList<OngoingActivityData> linkedList = new LinkedList();
        linkedList.addAll(mOngoingActivityLists);
        linkedList.addAll(hiddenOngoingActivityDataList);
        for (OngoingActivityData ongoingActivityData2 : linkedList) {
            ExpandableNotificationRow expandableNotificationRow2 = ongoingActivityData2.mNotificationEntry.row;
            if (expandableNotificationRow2 != null) {
                Context context3 = expandableNotificationRow2.getContext();
                NotificationEntry notificationEntry3 = expandableNotificationRow2.mEntry;
                ongoingActivityDataHelper.getClass();
                recreateOngoingActivity(context3, notificationEntry3);
            }
            pendingOngoingActivityDataList.put(ongoingActivityData2.mNotiID, ongoingActivityData2);
            ongoingActivityDataHelper.getClass();
            notifyUpdateItemNowbarObservers(convertOngoingActivityData(ongoingActivityData2));
        }
    }

    public static void removeOngoingActivityByKey(String str) {
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("findOngoingActivityDataIndex() noti id = ", str);
        String str2 = TAG;
        Log.i(str2, strM);
        Iterator it = mOngoingActivityLists.iterator();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else if (((OngoingActivityData) it.next()).mNotiID.equals(str)) {
                break;
            } else {
                i2++;
            }
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(mOngoingActivityLists.size(), "removeOngoingActivity() size = ", str2);
        CopyOnWriteArrayList copyOnWriteArrayList = hiddenOngoingActivityDataList;
        Log.i(str2, "removeOngoingActivity() hidden size = " + copyOnWriteArrayList.size());
        Log.i(str2, "removeOngoingActivity() key = " + str);
        pendingOngoingActivityDataList.remove(str);
        notifyRemoveItemNowbarObservers(str);
        OngoingActivityData ongoingActivityDataByKey = getOngoingActivityDataByKey(str);
        if (ongoingActivityDataByKey != null) {
            INSTANCE.getClass();
            ongoingActivityDataByKey.mNotificationEntry.mOnHideRawValueChangedListeners.remove(mOnHideRawValueChangedListener);
        }
        Iterator it2 = copyOnWriteArrayList.iterator();
        while (it2.hasNext()) {
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it2.next();
            if (str.equals(ongoingActivityData.mNotiID)) {
                CopyOnWriteArrayList copyOnWriteArrayList2 = hiddenOngoingActivityDataList;
                copyOnWriteArrayList2.remove(ongoingActivityData);
                ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(copyOnWriteArrayList2.size(), "removeOngoingActivity() fianl hidden size = ", str2);
                return;
            }
        }
        if (i2 >= 0) {
            mOngoingActivityLists.remove(i2);
            ArrayList arrayList = new ArrayList(observers);
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((IOngoingObserver) obj).remove(i2);
            }
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(mOngoingActivityLists.size(), "removeOngoingActivity() fianl size = ", str2);
    }

    public static void setBaseActivityComponentName(ComponentName componentName, NotificationLockscreenUserManager notificationLockscreenUserManager) {
        if (componentName.equals(baseActivityComponentName)) {
            return;
        }
        Log.i(TAG, "setBaseActivityComponentName:" + componentName);
        baseActivityComponentName = componentName;
        updateOngoingList(notificationLockscreenUserManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean shouldHide(NotificationLockscreenUserManager notificationLockscreenUserManager, OngoingActivityData ongoingActivityData) {
        String packageName;
        Intent intent;
        Intent intent2;
        Intent intent3;
        Intent intent4;
        CharSequence charSequence;
        boolean z = ongoingActivityData.mIsMediaOngoingData;
        String str = TAG;
        String str2 = ongoingActivityData.mPackageName;
        if (z) {
            ComponentName componentName = baseActivityComponentName;
            if (!StringsKt__StringsJVMKt.equals(componentName != null ? componentName.getPackageName() : null, str2, false) && (((charSequence = ongoingActivityData.mExpandedChipText) != null && charSequence.length() != 0) || ongoingActivityData.mChipBackground != 0)) {
            }
        } else {
            ComponentName componentName2 = baseActivityComponentName;
            PendingIntent pendingIntent = ongoingActivityData.mPendingIntent;
            ComponentName component = (pendingIntent == null || (intent3 = pendingIntent.getIntent()) == null) ? null : intent3.getComponent();
            if (!((componentName2 == null || component == null) ? false : Intrinsics.areEqual(componentName2.getPackageName(), component.getPackageName())) && ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isCurrentProfile(ongoingActivityData.mUserId) && ongoingActivityData.mStyle <= 1) {
                NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
                if (notificationEntry.isPromotedState() && !notificationEntry.mSbn.getNotification().isGroupSummary() && !notificationEntry.mIsBindCutOff.booleanValue()) {
                    PendingIntent pendingIntent2 = ongoingActivityData.mPendingIntent;
                    ComponentName component2 = (pendingIntent2 == null || (intent2 = pendingIntent2.getIntent()) == null) ? null : intent2.getComponent();
                    Iterator it = pipEnabledComponentNameList.iterator();
                    while (it.hasNext()) {
                        ComponentName componentName3 = (ComponentName) it.next();
                        if ((componentName3 == null || component2 == null) ? false : Intrinsics.areEqual(componentName3.getPackageName(), component2.getPackageName())) {
                        }
                    }
                    ComponentName componentName4 = baseActivityComponentName;
                    PendingIntent pendingIntent3 = ongoingActivityData.mPendingIntent;
                    ComponentName component3 = (pendingIntent3 == null || (intent = pendingIntent3.getIntent()) == null) ? null : intent.getComponent();
                    ComponentName componentName5 = baseActivityComponentName;
                    packageName = componentName5 != null ? componentName5.getPackageName() : null;
                    CopyOnWriteArrayList copyOnWriteArrayList = pipEnabledComponentNameList;
                    StringBuilder sb = new StringBuilder("shouldHide() return false. baseActivityComponentName:");
                    sb.append(componentName4);
                    sb.append(", intent.component:");
                    sb.append(component3);
                    sb.append(", basePackageName:");
                    MoveResult$$ExternalSyntheticOutline0.m(sb, packageName, ", intent.creatorPackage:", str2, ", pipEnabledComponentNameList:");
                    sb.append(copyOnWriteArrayList);
                    Log.d(str, sb.toString());
                    return false;
                }
            }
        }
        ComponentName componentName6 = baseActivityComponentName;
        PendingIntent pendingIntent4 = ongoingActivityData.mPendingIntent;
        ComponentName component4 = (pendingIntent4 == null || (intent4 = pendingIntent4.getIntent()) == null) ? null : intent4.getComponent();
        ComponentName componentName7 = baseActivityComponentName;
        packageName = componentName7 != null ? componentName7.getPackageName() : null;
        CopyOnWriteArrayList copyOnWriteArrayList2 = pipEnabledComponentNameList;
        StringBuilder sb2 = new StringBuilder("shouldHide() return true. baseActivityComponentName:");
        sb2.append(componentName6);
        sb2.append(", intent.component:");
        sb2.append(component4);
        sb2.append(", basePackageName:");
        MoveResult$$ExternalSyntheticOutline0.m(sb2, packageName, ", intent.creatorPackage:", str2, ", pipEnabledComponentNameList:");
        sb2.append(copyOnWriteArrayList2);
        Log.d(str, sb2.toString());
        return true;
    }

    public static void updateMediaProgressAndMarqueeStateIfNeeded(Boolean bool, Boolean bool2) {
        boolean zBooleanValue;
        boolean zBooleanValue2;
        CopyOnWriteArrayList copyOnWriteArrayList = mOngoingActivityLists;
        int i = 0;
        if (copyOnWriteArrayList.size() > 0) {
            int iMin = Math.min(copyOnWriteArrayList.size(), 2);
            zBooleanValue = false;
            zBooleanValue2 = false;
            for (int i2 = 0; i2 < iMin; i2++) {
                if (((OngoingActivityData) mOngoingActivityLists.get(i2)).mIsMediaOngoingData) {
                    zBooleanValue = true;
                    zBooleanValue2 = true;
                }
            }
        } else {
            zBooleanValue = false;
            zBooleanValue2 = false;
        }
        if (bool != null) {
            zBooleanValue = bool.booleanValue();
        }
        if (bool2 != null) {
            zBooleanValue2 = bool2.booleanValue();
        }
        ArrayList arrayList = (ArrayList) mediaOngoingActivityObserver;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            SecMediaControlPanel.AnonymousClass2 anonymousClass2 = (SecMediaControlPanel.AnonymousClass2) obj;
            SecSeekBarViewModel secSeekBarViewModel = SecMediaControlPanel.this.mSeekBarViewModel;
            secSeekBarViewModel.getClass();
            secSeekBarViewModel.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel, zBooleanValue));
            SecMediaControlPanel.this.setTitleAndArtistMarquee(zBooleanValue2);
        }
    }

    public static void updateOngoingActivityViews(Context context, boolean z, boolean z2) {
        RemoteViews remoteViews;
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(mOngoingActivityLists);
        linkedList.addAll(hiddenOngoingActivityDataList);
        ongoingActivityLayoutUtil.getClass();
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            OngoingActivityData ongoingActivityData = (OngoingActivityData) linkedList.get(i);
            if (ongoingActivityData.mCustomExpandedCardView == null && ongoingActivityData.mOngoingOAExpandView != null) {
                OngoingActivityLayoutCreatorImpl ongoingActivityLayoutCreatorImpl = new OngoingActivityLayoutCreatorImpl(context);
                OngoingActivityLayoutUtil ongoingActivityLayoutUtil2 = OngoingActivityLayoutUtil.INSTANCE;
                if (z && (remoteViews = ongoingActivityData.mOngoingOAExpandView) != null) {
                    if (ongoingActivityData.mActionType == 1) {
                        remoteViews.removeAllViews(R.id.ongoing_activity_expand_buttons);
                        ArrayList arrayList = ongoingActivityData.mActions;
                        if (arrayList != null) {
                            int size2 = arrayList.size();
                            int i2 = 0;
                            while (i2 < size2) {
                                Object obj = arrayList.get(i2);
                                i2++;
                                remoteViews.addView(R.id.ongoing_activity_expand_buttons, ongoingActivityLayoutCreatorImpl.makeTextButtonRemoteView((Notification.Action) obj, ongoingActivityLayoutCreatorImpl.context, false));
                            }
                        }
                    }
                    ongoingActivityLayoutUtil2.getClass();
                    if (OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData)) {
                        boolean zIsBottomBtnActionStyle = OngoingActivityLayoutCreatorImpl.isBottomBtnActionStyle(context);
                        ArrayList arrayList2 = ongoingActivityData.mActions;
                        arrayList2.getClass();
                        ongoingActivityLayoutCreatorImpl.updateIconButtonRemoteView(arrayList2, remoteViews, zIsBottomBtnActionStyle);
                        ongoingActivityLayoutCreatorImpl.updateNotiExpandButtonView(ongoingActivityData, remoteViews, zIsBottomBtnActionStyle);
                    }
                    OngoingActivityLayoutCreatorImpl.updateExpandViewFontSize$default(ongoingActivityLayoutCreatorImpl, remoteViews, ongoingActivityData.mNotificationEntry.isPromotedState());
                    RemoteViews remoteViews2 = ongoingActivityData.mOngoingCollapsedView;
                    if (remoteViews2 != null) {
                        ongoingActivityLayoutCreatorImpl.updateCollapsedViewFontSize(remoteViews2);
                        ongoingActivityData.mOngoingOAExpandView = remoteViews;
                    }
                }
                if (z2) {
                    for (RemoteViews remoteViews3 : Arrays.asList(ongoingActivityData.mOngoingCollapsedView, ongoingActivityData.mOngoingOAExpandView)) {
                        if (remoteViews3 != null) {
                            remoteViews3.setTextColor(android.R.id.beforeDescendants, context.getColor(R.color.ongoing_activity_expand_header_text_color));
                        }
                        if (remoteViews3 != null) {
                            remoteViews3.setTextColor(16909968, context.getColor(R.color.ongoing_activity_expand_header_text_color));
                        }
                        ongoingActivityLayoutUtil2.getClass();
                        OngoingActivityLayoutUtil.setExpandButtonColor(remoteViews3);
                    }
                    if (OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData)) {
                        RemoteViews remoteViews4 = ongoingActivityData.mOngoingOAExpandView;
                        if (remoteViews4 != null) {
                            remoteViews4.setTextColor(R.id.ongoing_activity_expand_secondary_text, context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                        }
                        RemoteViews remoteViews5 = ongoingActivityData.mOngoingCollapsedView;
                        if (remoteViews5 != null) {
                            remoteViews5.setTextColor(R.id.ongoing_activity_collapsed_secondary_text, context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                        }
                    }
                }
                linkedList.set(i, ongoingActivityData);
            }
        }
    }

    public static void updateOngoingList(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        OngoingActivityDataHelper ongoingActivityDataHelper;
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(mOngoingActivityLists);
        Iterator it = linkedList.iterator();
        boolean z = false;
        while (true) {
            boolean zHasNext = it.hasNext();
            ongoingActivityDataHelper = INSTANCE;
            if (!zHasNext) {
                break;
            }
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it.next();
            ongoingActivityDataHelper.getClass();
            if (shouldHide(notificationLockscreenUserManager, ongoingActivityData)) {
                mOngoingActivityLists.remove(ongoingActivityData);
                Iterator it2 = hiddenOngoingActivityDataList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((OngoingActivityData) it2.next()).mNotiID.equals(ongoingActivityData.mNotiID)) {
                            break;
                        }
                    } else {
                        hiddenOngoingActivityDataList.addFirst(ongoingActivityData);
                        z = true;
                        break;
                    }
                }
            }
        }
        if (cardIsShown) {
            Log.i(TAG, "cardIsShown:true. skip hiddenOngoingActivityDataList recovery");
        } else {
            LinkedList<OngoingActivityData> linkedList2 = new LinkedList();
            linkedList2.addAll(hiddenOngoingActivityDataList);
            for (OngoingActivityData ongoingActivityData2 : linkedList2) {
                ongoingActivityDataHelper.getClass();
                if (!shouldHide(notificationLockscreenUserManager, ongoingActivityData2)) {
                    hiddenOngoingActivityDataList.remove(ongoingActivityData2);
                    String str = ongoingActivityData2.mNotiID;
                    Iterator it3 = mOngoingActivityLists.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            if (((OngoingActivityData) it3.next()).mNotiID.equals(str)) {
                                break;
                            }
                        } else {
                            mOngoingActivityLists.addFirst(ongoingActivityData2);
                            z = true;
                            break;
                        }
                    }
                }
            }
        }
        if (z) {
            notifyUpdateObservers();
            updateTopIndex();
        }
    }

    public static void updateTopIndex() {
        ArrayList arrayList = (ArrayList) nowbarObservers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) obj;
            CopyOnWriteArrayList copyOnWriteArrayList = mOngoingActivityLists;
            if (copyOnWriteArrayList.size() > 0) {
                OngoingActivityData ongoingActivityData = (OngoingActivityData) copyOnWriteArrayList.get(0);
                String str = ongoingActivityData.mIsMediaOngoingData ? "MEDIA_NOWBAR" : ((OngoingActivityData) copyOnWriteArrayList.get(0)).mNotiID;
                faceWidgetNotificationControllerWrapper.getClass();
                if (str != null) {
                    try {
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("updateTopIndex exception ", e, "FaceWidgetNotificationControllerWrapper");
                    }
                    if (TextUtils.isEmpty(str)) {
                        Log.e("FaceWidgetNotificationControllerWrapper", "updateTopIndex id is null or empty");
                        Log.d(TAG, " updateTOPIndex - " + ongoingActivityData.mPrimaryInfo);
                    } else {
                        Log.i("FaceWidgetNotificationControllerWrapper", "updateTopIndex " + str + " / notificationController = " + faceWidgetNotificationControllerWrapper.mNotificationController);
                        if (faceWidgetNotificationControllerWrapper.mNotificationController != null) {
                            NowBarItem nowBarItem = new NowBarItem();
                            nowBarItem.setNotiID(str);
                            faceWidgetNotificationControllerWrapper.mNotificationController.onTopNowBarItemChanged(nowBarItem);
                        }
                        Log.d(TAG, " updateTOPIndex - " + ongoingActivityData.mPrimaryInfo);
                    }
                } else {
                    Log.e("FaceWidgetNotificationControllerWrapper", "updateTopIndex id is null or empty");
                    Log.d(TAG, " updateTOPIndex - " + ongoingActivityData.mPrimaryInfo);
                }
            }
        }
        if (cardIsShown) {
            updateMediaProgressAndMarqueeStateIfNeeded(null, null);
        }
    }
}
