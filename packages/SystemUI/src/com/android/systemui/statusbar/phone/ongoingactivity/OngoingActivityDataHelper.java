package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.samsung.android.knox.custom.IKnoxCustomManager;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:119:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x047f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x04c5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x04fd  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x053e  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x024e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void createOngoingView(android.content.Context r31, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData r32) {
        /*
            Method dump skipped, instructions count: 1390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.createOngoingView(android.content.Context, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData):void");
    }

    public static void dataDump(OngoingActivityData ongoingActivityData, PrintWriter printWriter) {
        Integer num;
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
            num = 0;
        } else {
            ArrayList arrayList2 = ongoingActivityData.mActions;
            num = arrayList2 != null ? Integer.valueOf(arrayList2.size()) : null;
        }
        printWriter.println("        action : " + num);
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
        String str;
        String packageName = notificationEntry.mSbn.getPackageName();
        int id = notificationEntry.mSbn.getId();
        NotificationChannel channel = notificationEntry.mRanking.getChannel();
        if (channel == null || (str = channel.getId()) == null) {
            str = "null";
        }
        return "com.google.android.googlequicksearchbox".equals(packageName) && 100 == id && "convmode_notification_channel_id".equals(str);
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
            boolean isPromotedState = notificationEntry.isPromotedState();
            boolean booleanValue = notificationEntry.mIsLockscreenSecret.booleanValue();
            StringBuilder m = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("notify ongoing ranking changed ", str, " : promoted=", ", isLockscreenSecret=", isPromotedState);
            m.append(booleanValue);
            Log.d(TAG, m.toString());
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
            ((IOngoingObserver) obj).update$8();
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
                int indexOf = copyOnWriteArrayList.indexOf(ongoingActivityData3);
                if (indexOf != -1) {
                    copyOnWriteArrayList.remove(indexOf);
                    copyOnWriteArrayList.add(indexOf, ongoingActivityData);
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
                    int indexOf2 = copyOnWriteArrayList2.indexOf(ongoingActivityData2);
                    if (indexOf2 != -1) {
                        copyOnWriteArrayList2.remove(indexOf2);
                        copyOnWriteArrayList2.add(indexOf2, ongoingActivityData);
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

    public static void reinflateOngoingActivity() {
        OngoingActivityDataHelper ongoingActivityDataHelper;
        Iterator it = onlyShownNowbarItemMap.values().iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            ongoingActivityDataHelper = INSTANCE;
            if (!hasNext) {
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
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("findOngoingActivityDataIndex() noti id = ", str);
        String str2 = TAG;
        Log.i(str2, m);
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
            ArrayList arrayList = (ArrayList) observers;
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002b, code lost:
    
        if (r0.length() != 0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:
    
        if (r13.mChipBackground != 0) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldHide(com.android.systemui.statusbar.NotificationLockscreenUserManager r12, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData r13) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.shouldHide(com.android.systemui.statusbar.NotificationLockscreenUserManager, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData):boolean");
    }

    public static void updateMediaProgressAndMarqueeStateIfNeeded(Boolean bool, Boolean bool2) {
        boolean z;
        boolean z2;
        CopyOnWriteArrayList copyOnWriteArrayList = mOngoingActivityLists;
        int i = 0;
        if (copyOnWriteArrayList.size() > 0) {
            int min = Math.min(copyOnWriteArrayList.size(), 2);
            z = false;
            z2 = false;
            for (int i2 = 0; i2 < min; i2++) {
                if (((OngoingActivityData) mOngoingActivityLists.get(i2)).mIsMediaOngoingData) {
                    z = true;
                    z2 = true;
                }
            }
        } else {
            z = false;
            z2 = false;
        }
        if (bool != null) {
            z = bool.booleanValue();
        }
        if (bool2 != null) {
            z2 = bool2.booleanValue();
        }
        ArrayList arrayList = (ArrayList) mediaOngoingActivityObserver;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            SecMediaControlPanel.AnonymousClass2 anonymousClass2 = (SecMediaControlPanel.AnonymousClass2) obj;
            SecSeekBarViewModel secSeekBarViewModel = SecMediaControlPanel.this.mSeekBarViewModel;
            secSeekBarViewModel.getClass();
            secSeekBarViewModel.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel, z));
            SecMediaControlPanel.this.setTitleAndArtistMarquee(z2);
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
                        boolean isBottomBtnActionStyle = OngoingActivityLayoutCreatorImpl.isBottomBtnActionStyle(context);
                        ArrayList arrayList2 = ongoingActivityData.mActions;
                        arrayList2.getClass();
                        ongoingActivityLayoutCreatorImpl.updateIconButtonRemoteView(arrayList2, remoteViews, isBottomBtnActionStyle);
                        ongoingActivityLayoutCreatorImpl.updateNotiExpandButtonView(ongoingActivityData, remoteViews, isBottomBtnActionStyle);
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
                            remoteViews3.setTextColor(16909967, context.getColor(R.color.ongoing_activity_expand_header_text_color));
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
                    if (!TextUtils.isEmpty(str)) {
                        Log.i("FaceWidgetNotificationControllerWrapper", "updateTopIndex " + str + " / notificationController = " + faceWidgetNotificationControllerWrapper.mNotificationController);
                        if (faceWidgetNotificationControllerWrapper.mNotificationController != null) {
                            NowBarItem nowBarItem = new NowBarItem();
                            nowBarItem.setNotiID(str);
                            faceWidgetNotificationControllerWrapper.mNotificationController.onTopNowBarItemChanged(nowBarItem);
                        }
                        Log.d(TAG, " updateTOPIndex - " + ongoingActivityData.mPrimaryInfo);
                    }
                }
                Log.e("FaceWidgetNotificationControllerWrapper", "updateTopIndex id is null or empty");
                Log.d(TAG, " updateTOPIndex - " + ongoingActivityData.mPrimaryInfo);
            }
        }
        if (cardIsShown) {
            updateMediaProgressAndMarqueeStateIfNeeded(null, null);
        }
    }
}
