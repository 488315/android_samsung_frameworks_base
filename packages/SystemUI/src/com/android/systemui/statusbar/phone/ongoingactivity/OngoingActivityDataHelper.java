package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingActivityDataHelper {
    public static ComponentName baseActivityComponentName;
    public static boolean cardIsShown;
    public static final OngoingActivityDataHelper INSTANCE = new OngoingActivityDataHelper();
    public static final LinkedList mOngoingActivityLists = new LinkedList();
    public static final List observers = new ArrayList();
    public static final String TAG = "{OngoingActivityDataHelper}";
    public static final List nowbarObservers = new ArrayList();
    public static final LinkedList hiddenOngoingActivityDataList = new LinkedList();
    public static final OngoingActivityDataHelper$nowbarWatcher$1 nowbarWatcher = new Object() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper$nowbarWatcher$1
    };

    private OngoingActivityDataHelper() {
    }

    public static NowBarItem convertOngoingActivityData(OngoingActivityData ongoingActivityData) {
        NowBarItem nowBarItem = new NowBarItem();
        nowBarItem.setNotiID(ongoingActivityData.mNotiID);
        nowBarItem.setPendingIntent(ongoingActivityData.mPendingIntent);
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
        nowBarItem.setCardIconBg(num != null ? num.intValue() : 1);
        nowBarItem.setCustomExpandedCardView(ongoingActivityData.mCustomExpandedCardView);
        nowBarItem.setExpandedChipText(ongoingActivityData.mExpandedChipText);
        nowBarItem.setChronometerView(ongoingActivityData.mChronometerView);
        nowBarItem.setChronometerTag(ongoingActivityData.mChronometerTag);
        nowBarItem.setOngoingExpandView(ongoingActivityData.mOngoingExpandView);
        nowBarItem.setOngoingNowbarView(ongoingActivityData.mOngoingNowbarView);
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
        Bundle bundle = new Bundle();
        Icon icon = ongoingActivityData.mAppIcon;
        if (icon != null) {
            bundle.putParcelable("nowbar_key_app_icon", icon);
        }
        int i = ongoingActivityData.mChronometerPosition;
        if (i != 0) {
            bundle.putInt("nowbar_key_chronometerRemoteViewPosition", i);
        }
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        if (notificationEntry != null) {
            bundle.putBoolean("nowbar_key_need_to_blockcard", true ^ notificationEntry.isPromotedState());
        }
        int i2 = ongoingActivityData.mNowbarChronometerPosition;
        if (i2 != 0) {
            bundle.putInt("nowbar_key_chronometerPosition", i2);
        }
        nowBarItem.setExtraData(bundle);
        return nowBarItem;
    }

    public static OngoingActivityData getDataByIndex(int i) {
        return (OngoingActivityData) mOngoingActivityLists.get(i);
    }

    public static OngoingActivityData getOngoingActivityDataByKey(String str) {
        for (OngoingActivityData ongoingActivityData : mOngoingActivityLists) {
            if (ongoingActivityData.mNotiID.equals(str)) {
                return ongoingActivityData;
            }
        }
        for (OngoingActivityData ongoingActivityData2 : hiddenOngoingActivityDataList) {
            if (ongoingActivityData2.mNotiID.equals(str)) {
                return ongoingActivityData2;
            }
        }
        return null;
    }

    public static boolean isExceptionalOngoingActivity(NotificationEntry notificationEntry) {
        String packageName = notificationEntry.mSbn.getPackageName();
        int id = notificationEntry.mSbn.getId();
        NotificationChannel channel = notificationEntry.mRanking.getChannel();
        String id2 = channel != null ? channel.getId() : null;
        if (id2 == null) {
            id2 = "null";
        }
        return "com.google.android.googlequicksearchbox".equals(packageName) && 100 == id && "convmode_notification_channel_id".equals(id2);
    }

    public static void notifyUpdateItemNowbarObservers(NowBarItem nowBarItem) {
        ArrayList arrayList = (ArrayList) nowbarObservers;
        Log.i(TAG, "notifyUpdateItemNowbarObservers " + nowBarItem + " size = " + arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((FaceWidgetNotificationControllerWrapper) it.next()).updateItem(nowBarItem);
        }
    }

    public static void notifyUpdateObservers() {
        Iterator it = ((ArrayList) observers).iterator();
        while (it.hasNext()) {
            ((IOngoingObserver) it.next()).update$6();
        }
    }

    public static void removeOngoingActivityByKey(String str) {
        String concat = "findOngoingActivityDataIndex() noti id = ".concat(str);
        String str2 = TAG;
        Log.i(str2, concat);
        Iterator it = mOngoingActivityLists.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (((OngoingActivityData) it.next()).mNotiID.equals(str)) {
                break;
            } else {
                i++;
            }
        }
        Log.i(str2, "removeOngoingActivity() size = " + mOngoingActivityLists.size());
        Log.i(str2, "removeOngoingActivity() key = ".concat(str));
        ArrayList arrayList = (ArrayList) nowbarObservers;
        Log.i(str2, "notifyRemoveItemNowbarObservers " + str + " size = " + arrayList.size());
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) it2.next();
            NowBarItem nowBarItem = new NowBarItem();
            nowBarItem.setNotiID(str);
            faceWidgetNotificationControllerWrapper.removeItem(nowBarItem);
        }
        LinkedList<OngoingActivityData> linkedList = hiddenOngoingActivityDataList;
        if (linkedList != null) {
            for (OngoingActivityData ongoingActivityData : linkedList) {
                if (str.equals(ongoingActivityData.mNotiID)) {
                    hiddenOngoingActivityDataList.remove(ongoingActivityData);
                    return;
                }
            }
        }
        if (i >= 0) {
            mOngoingActivityLists.remove(i);
            Iterator it3 = ((ArrayList) observers).iterator();
            while (it3.hasNext()) {
                ((IOngoingObserver) it3.next()).remove(i);
            }
        }
    }

    public static void setChronometerWidth(View view, String str) {
        Chronometer chronometer;
        if (!(view.findViewWithTag(str) instanceof Chronometer) || (chronometer = (Chronometer) view.findViewWithTag(str)) == null) {
            return;
        }
        chronometer.getLayoutParams().width = -1;
        chronometer.setSingleLine(true);
        chronometer.setEllipsize(TextUtils.TruncateAt.END);
    }

    public static boolean shouldHide(NotificationLockscreenUserManager notificationLockscreenUserManager, OngoingActivityData ongoingActivityData) {
        Intent intent;
        Intent intent2;
        Intent intent3;
        ComponentName componentName = baseActivityComponentName;
        PendingIntent pendingIntent = ongoingActivityData.mPendingIntent;
        ComponentName componentName2 = null;
        ComponentName component = (pendingIntent == null || (intent3 = pendingIntent.getIntent()) == null) ? null : intent3.getComponent();
        boolean areEqual = (componentName == null || component == null) ? false : Intrinsics.areEqual(componentName.getPackageName(), component.getPackageName());
        String str = TAG;
        if (areEqual || !((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isCurrentProfile(ongoingActivityData.mUserId) || ongoingActivityData.mStyle == 2 || !ongoingActivityData.mNotificationEntry.isPromotedState()) {
            ComponentName componentName3 = baseActivityComponentName;
            PendingIntent pendingIntent2 = ongoingActivityData.mPendingIntent;
            if (pendingIntent2 != null && (intent = pendingIntent2.getIntent()) != null) {
                componentName2 = intent.getComponent();
            }
            Log.d(str, "shouldHide() return true. baseActivityComponentName:" + componentName3 + ", intent.component:" + componentName2);
            return true;
        }
        ComponentName componentName4 = baseActivityComponentName;
        PendingIntent pendingIntent3 = ongoingActivityData.mPendingIntent;
        if (pendingIntent3 != null && (intent2 = pendingIntent3.getIntent()) != null) {
            componentName2 = intent2.getComponent();
        }
        Log.d(str, "shouldHide() return false. baseActivityComponentName:" + componentName4 + ", intent.component:" + componentName2);
        return false;
    }

    public static void updateOngoingActivityViews(Context context, LinkedList linkedList, boolean z, boolean z2) {
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        ArrayList arrayList;
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            OngoingActivityData ongoingActivityData = (OngoingActivityData) linkedList.get(i);
            if (ongoingActivityData.mCustomExpandedCardView == null && ongoingActivityData.mOngoingExpandView != null) {
                OngoingActivityLayoutCreatorImpl ongoingActivityLayoutCreatorImpl = new OngoingActivityLayoutCreatorImpl(context);
                int i2 = ongoingActivityData.mActionType;
                if (z && (remoteViews2 = ongoingActivityData.mOngoingExpandView) != null) {
                    if (i2 == 1) {
                        remoteViews2.removeAllViews(R.id.ongoing_activity_expand_buttons);
                        ArrayList arrayList2 = ongoingActivityData.mActions;
                        if (arrayList2 != null) {
                            Iterator it = arrayList2.iterator();
                            while (it.hasNext()) {
                                remoteViews2.addView(R.id.ongoing_activity_expand_buttons, ongoingActivityLayoutCreatorImpl.makeTextButtonRemoteView((Notification.Action) it.next()));
                            }
                        }
                        ongoingActivityData.mOngoingExpandView = remoteViews2;
                    }
                    ongoingActivityLayoutCreatorImpl.updateExpandViewFontSize(remoteViews2, ongoingActivityData.mNotificationEntry.isPromotedState());
                    if (i2 == 0 && (arrayList = ongoingActivityData.mActions) != null) {
                        ongoingActivityLayoutCreatorImpl.updateIconButtonRemoteView(arrayList, remoteViews2);
                    }
                }
                if (z2 && (remoteViews = ongoingActivityData.mOngoingExpandView) != null) {
                    remoteViews.setTextColor(android.R.id.authtoken_type, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_expand_header_text_color));
                    remoteViews.setTextColor(16909905, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_expand_header_text_color));
                    if (i2 == 0 && ongoingActivityData.mActions != null) {
                        remoteViews.setTextColor(R.id.ongoing_activity_expand_secondary_text, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                        RemoteViews remoteViews3 = ongoingActivityData.mOngoingCollapsedView;
                        if (remoteViews3 != null) {
                            remoteViews3.setTextColor(R.id.ongoing_activity_collapsed_secondary_text, ongoingActivityLayoutCreatorImpl.context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                        }
                    }
                }
                linkedList.set(i, ongoingActivityData);
            }
        }
    }

    public static void updateOngoingChronometerText(View view) {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        ViewGroup viewGroup3;
        ViewGroup viewGroup4;
        boolean z = view.findViewById(R.id.ongoing_activity_expand_primary_container) instanceof ViewGroup;
        OngoingActivityDataHelper ongoingActivityDataHelper = INSTANCE;
        if (z && (viewGroup4 = (ViewGroup) view.findViewById(R.id.ongoing_activity_expand_primary_container)) != null) {
            int childCount = viewGroup4.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup4.getChildAt(i);
                if (childAt instanceof Chronometer) {
                    Chronometer chronometer = (Chronometer) childAt;
                    chronometer.setId(R.id.tag_for_ongoing_chronometer_expand_primary);
                    Context context = viewGroup4.getContext();
                    ongoingActivityDataHelper.getClass();
                    updatePrimaryChronometer(context, chronometer);
                    updateSecondaryBelowChronometer(view, R.id.ongoing_activity_expand_secondary_text);
                }
            }
        }
        if ((view.findViewById(R.id.ongoing_activity_expand_secondary_container) instanceof ViewGroup) && (viewGroup3 = (ViewGroup) view.findViewById(R.id.ongoing_activity_expand_secondary_container)) != null) {
            int childCount2 = viewGroup3.getChildCount();
            for (int i2 = 0; i2 < childCount2; i2++) {
                View childAt2 = viewGroup3.getChildAt(i2);
                if (childAt2 instanceof Chronometer) {
                    Chronometer chronometer2 = (Chronometer) childAt2;
                    chronometer2.setId(R.id.tag_for_ongoing_chronometer_expand_secondary);
                    Context context2 = viewGroup3.getContext();
                    ongoingActivityDataHelper.getClass();
                    updateSecondaryChronometer(context2, chronometer2);
                    if (view.findViewById(R.id.ongoing_activity_expand_icon_buttons) != null) {
                        chronometer2.setTextColor(viewGroup3.getContext().getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                    } else {
                        chronometer2.setTextColor(viewGroup3.getContext().getColor(R.color.ongoing_activity_card_item_sub_text_color));
                    }
                }
            }
        }
        if ((view.findViewById(R.id.ongoing_activity_collapsed_primary_container) instanceof ViewGroup) && (viewGroup2 = (ViewGroup) view.findViewById(R.id.ongoing_activity_collapsed_primary_container)) != null) {
            int childCount3 = viewGroup2.getChildCount();
            for (int i3 = 0; i3 < childCount3; i3++) {
                View childAt3 = viewGroup2.getChildAt(i3);
                if (childAt3 instanceof Chronometer) {
                    Chronometer chronometer3 = (Chronometer) childAt3;
                    chronometer3.setId(R.id.tag_for_ongoing_chronometer_collapsed_primary);
                    Context context3 = viewGroup2.getContext();
                    ongoingActivityDataHelper.getClass();
                    updatePrimaryChronometer(context3, chronometer3);
                    updateSecondaryBelowChronometer(view, R.id.ongoing_activity_collapsed_secondary_text);
                }
            }
        }
        if (!(view.findViewById(R.id.ongoing_activity_collapsed_secondary_container) instanceof ViewGroup) || (viewGroup = (ViewGroup) view.findViewById(R.id.ongoing_activity_collapsed_secondary_container)) == null) {
            return;
        }
        int childCount4 = viewGroup.getChildCount();
        for (int i4 = 0; i4 < childCount4; i4++) {
            View childAt4 = viewGroup.getChildAt(i4);
            if (childAt4 instanceof Chronometer) {
                Chronometer chronometer4 = (Chronometer) childAt4;
                chronometer4.setId(R.id.tag_for_ongoing_chronometer_collapsed_secondary);
                Context context4 = viewGroup.getContext();
                ongoingActivityDataHelper.getClass();
                updateSecondaryChronometer(context4, chronometer4);
                if (view.findViewById(R.id.ongoing_action_button) != null) {
                    chronometer4.setTextColor(viewGroup.getContext().getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                } else {
                    chronometer4.setTextColor(viewGroup.getContext().getColor(R.color.ongoing_activity_card_item_sub_text_color));
                }
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
            boolean hasNext = it.hasNext();
            ongoingActivityDataHelper = INSTANCE;
            if (!hasNext) {
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

    public static void updatePrimaryChronometer(Context context, Chronometer chronometer) {
        chronometer.setIncludeFontPadding(false);
        chronometer.setTextSize(0, context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_main_chronometer_text_size));
        chronometer.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
    }

    public static void updateSecondaryBelowChronometer(View view, int i) {
        TextView textView;
        if (!(view.findViewById(i) instanceof TextView) || (textView = (TextView) view.findViewById(i)) == null) {
            return;
        }
        textView.setTextSize(0, textView.getContext().getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_header_app_label_text_size));
        textView.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
        textView.setTextColor(textView.getContext().getColor(R.color.ongoing_activity_expand_header_text_color));
    }

    public static void updateSecondaryChronometer(Context context, Chronometer chronometer) {
        chronometer.setIncludeFontPadding(false);
        chronometer.setTextSize(0, context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_sub_text_size));
        chronometer.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
    }

    public static void updateTopIndex() {
        Iterator it = ((ArrayList) nowbarObservers).iterator();
        while (it.hasNext()) {
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) it.next();
            LinkedList linkedList = mOngoingActivityLists;
            if (linkedList.size() > 0) {
                String str = ((OngoingActivityData) linkedList.get(0)).mNotiID;
                faceWidgetNotificationControllerWrapper.getClass();
                if (str != null) {
                    try {
                    } catch (Exception e) {
                        EmergencyButton$$ExternalSyntheticOutline0.m("updateTopIndex exception ", e, "FaceWidgetNotificationControllerWrapper");
                    }
                    if (!TextUtils.isEmpty(str)) {
                        Log.i("FaceWidgetNotificationControllerWrapper", "updateTopIndex " + str + " / notificationController = " + faceWidgetNotificationControllerWrapper.mNotificationController);
                        if (faceWidgetNotificationControllerWrapper.mNotificationController != null) {
                            NowBarItem nowBarItem = new NowBarItem();
                            nowBarItem.setNotiID(str);
                            faceWidgetNotificationControllerWrapper.mNotificationController.onTopNowBarItemChanged(nowBarItem);
                        }
                        Log.d(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" updateTOPIndex - ", ((OngoingActivityData) mOngoingActivityLists.get(0)).mPrimaryInfo));
                    }
                }
                Log.e("FaceWidgetNotificationControllerWrapper", "updateTopIndex id is null or empty");
                Log.d(TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(" updateTOPIndex - ", ((OngoingActivityData) mOngoingActivityLists.get(0)).mPrimaryInfo));
            }
        }
    }
}
