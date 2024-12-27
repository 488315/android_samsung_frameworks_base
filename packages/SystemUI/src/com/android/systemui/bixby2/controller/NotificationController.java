package com.android.systemui.bixby2.controller;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.INotificationManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.annotations.SerializedName;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationController {
    static final String NOTI_APPNAME = "appName";
    static final String NOTI_FGS = "fgs";
    static final String NOTI_ID = "notiID";
    static final String NOTI_ITEM_COUNT = "itemCount";
    static final String NOTI_LIST = "notificationList";
    static final String NOTI_ONGOING = "ongoing";
    static final String NOTI_REPLY = "canReply";
    static final String NOTI_RESULT = "result";
    static final String NOTI_TEXT = "notiText";
    static final String NOTI_TITLE = "notiTitle";
    static final String NOTI_WHEN = "when";
    public static final String PARAMETER_INCLUDE_ONGOING = "includeOngoing";
    public static final int RESULT_ALL_IS_NON_DISMISSABLE = 7;
    public static final int RESULT_ALL_IS_ONGOING_NOTI = 4;
    public static final int RESULT_FAIL = 0;
    public static final int RESULT_NONBLOCKABLE_PACKAGE = 8;
    public static final int RESULT_NOTIFICATION_ALREADY_TURNED_OFF = 9;
    public static final int RESULT_NO_MATCHED_APP_NAME = 3;
    public static final int RESULT_NO_NOTIFICATION_EXIST = 2;
    public static final int RESULT_READOUT_ALL_WITH_ID = 5;
    public static final int RESULT_READOUT_APP_WITH_ID = 6;
    public static final int RESULT_SUCCESS = 1;
    static final String TAG = "NotificationController";
    private final Context mContext;
    private final DesktopManager mDesktopManager;
    private StringBuffer mDisplayDescription;
    private final DisplayLifecycle mDisplayLifecycle;
    private final KeyguardManager mKeyguardManager;
    private final NotifCollection mNotifCollection;
    private INotificationManager mNotifManager;
    private final NotifPipeline mNotifPipeline;
    private List<ListEntry> mEntries = List.of();
    private Handler mUiHandler = new Handler(Looper.getMainLooper());
    private int mItemCount = 0;
    RemoteInput mRemoteInput = null;
    RemoteInput[] mRemoteInputs = null;
    PendingIntent mRemoteInputIntent = null;
    String mRemoteInputId = "-1";

    public class DisplayContentObject {

        @SerializedName(NotificationController.NOTI_APPNAME)
        String appName;

        @SerializedName("notiCount")
        String notiCount;

        public DisplayContentObject(String str, String str2) {
            this.appName = str;
            this.notiCount = str2;
        }

        public String getAppName() {
            return this.appName;
        }

        public String getNotiCount() {
            return this.notiCount;
        }

        public void setAppName(String str) {
            this.appName = str;
        }

        public void setNotiCount(String str) {
            this.notiCount = str;
        }
    }

    public class ReadOutNotificationData {
        StringBuffer contentDescription;
        int count;
    }

    public NotificationController(Context context, NotifPipeline notifPipeline, NotifCollection notifCollection, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, KeyguardManager keyguardManager) {
        this.mContext = context;
        this.mNotifPipeline = notifPipeline;
        this.mNotifCollection = notifCollection;
        this.mDesktopManager = desktopManager;
        this.mDisplayLifecycle = displayLifecycle;
        this.mKeyguardManager = keyguardManager;
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
            this.mNotifManager = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        }
    }

    private boolean checkDismissableNotification(List<NotificationEntry> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).row.canViewBeDismissed$1()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNotificatoins(int i) {
        return i != 0;
    }

    private boolean checkOngoingNotification(List<NotificationEntry> list) {
        int size = list.size();
        Log.d(TAG, "checkOngoingNotification() size = " + size + ",  activeNotifications = " + list);
        for (int i = 0; i < size; i++) {
            ExpandableNotificationRow expandableNotificationRow = list.get(i).row;
            if (expandableNotificationRow.mEntry.isClearable() && !(expandableNotificationRow.shouldShowPublic() && expandableNotificationRow.mSensitiveHiddenInGeneral)) {
                return true;
            }
        }
        return false;
    }

    private int deleteAllNotifications(List<NotificationEntry> list) {
        if (!checkOngoingNotification(list)) {
            return 4;
        }
        this.mUiHandler.post(new NotificationController$$ExternalSyntheticLambda0(this, 0));
        return 1;
    }

    private int deleteAllNotificationsDismissable(List<NotificationEntry> list) {
        if (!checkDismissableNotification(list)) {
            return 7;
        }
        this.mUiHandler.post(new NotificationController$$ExternalSyntheticLambda0(this, 1));
        return 1;
    }

    private int deleteAppNotifications(String str, List<NotificationEntry> list) {
        int size = list.size();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            NotificationEntry notificationEntry = list.get(i);
            if (notificationEntry.mSbn.getPackageName().equals(str)) {
                if (notificationEntry.row.canViewBeDismissed$1()) {
                    arrayList.add(notificationEntry);
                    z = true;
                    z2 = true;
                } else {
                    z = true;
                }
            }
        }
        int size2 = arrayList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            final NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(i2);
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationController.this.lambda$deleteAppNotifications$3(notificationEntry2);
                }
            });
        }
        if (z) {
            return !z2 ? 4 : 1;
        }
        return 3;
    }

    private ActivityTaskManager.RootTaskInfo getFocusedStack() {
        Log.d(TAG, "getFocusedPackageName() ");
        try {
            return ActivityManager.getService().getFocusedRootTaskInfo();
        } catch (RemoteException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private List<NotificationEntry> getVisibleNotifications() {
        final ArrayList arrayList = new ArrayList();
        this.mEntries.stream().forEach(new Consumer() { // from class: com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationController.lambda$getVisibleNotifications$0(arrayList, (ListEntry) obj);
            }
        });
        return arrayList;
    }

    private boolean isFolderClosed() {
        return !this.mDisplayLifecycle.mIsFolderOpened;
    }

    private boolean isRemoteInputNotification(NotificationEntry notificationEntry) {
        int length;
        RemoteInput[] remoteInputs;
        PendingIntent pendingIntent;
        Notification notification2 = notificationEntry.mSbn.getNotification();
        this.mRemoteInputs = null;
        this.mRemoteInput = null;
        this.mRemoteInputIntent = null;
        this.mRemoteInputId = "-1";
        Notification.Action[] actionArr = notification2.actions;
        if (actionArr == null || (length = actionArr.length) == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            Notification.Action action = notification2.actions[i];
            if (action != null && (remoteInputs = action.getRemoteInputs()) != null) {
                for (RemoteInput remoteInput : remoteInputs) {
                    if (remoteInput.getAllowFreeFormInput() && (pendingIntent = action.actionIntent) != null) {
                        this.mRemoteInputs = remoteInputs;
                        this.mRemoteInput = remoteInput;
                        this.mRemoteInputIntent = pendingIntent;
                        this.mRemoteInputId = notificationEntry.mKey;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void lambda$deleteAllNotifications$1() {
        Log.d(TAG, "clear Notifiations call by bixby");
        this.mNotifCollection.dismissAllNotifications(this.mContext.getUserId(), false);
    }

    public /* synthetic */ void lambda$deleteAllNotificationsDismissable$2() {
        Log.d(TAG, "clear Notifiations call by bixby");
        this.mNotifCollection.dismissAllNotifications(this.mContext.getUserId(), true);
    }

    public void lambda$deleteAppNotifications$3(NotificationEntry notificationEntry) {
        this.mNotifCollection.dismissNotification(notificationEntry, new DismissedByUserStats(3, 1, NotificationVisibility.obtain(notificationEntry.mKey, notificationEntry.mRanking.getRank(), this.mEntries.size(), true, NotificationLogger.getNotificationLocation(notificationEntry))));
    }

    public static void lambda$getVisibleNotifications$0(List list, ListEntry listEntry) {
        list.add(listEntry.getRepresentativeEntry());
        if (listEntry instanceof GroupEntry) {
            list.addAll(((GroupEntry) listEntry).mUnmodifiableChildren);
        }
    }

    private void readAllNotification(List<NotificationEntry> list) {
        int size = list.size();
        ArrayMap arrayMap = new ArrayMap();
        ReadOutNotificationData readOutNotificationData = new ReadOutNotificationData();
        readOutNotificationData.contentDescription = new StringBuffer();
        this.mDisplayDescription = new StringBuffer();
        for (int i = 0; i < size; i++) {
            ExpandableNotificationRow expandableNotificationRow = list.get(i).row;
            String str = expandableNotificationRow.mAppName;
            if (!filterReadOutNotification(expandableNotificationRow) && str != null) {
                if (arrayMap.containsKey(str)) {
                    arrayMap.put(str, Integer.valueOf(((Integer) arrayMap.get(str)).intValue() + 1));
                } else {
                    arrayMap.put(str, 1);
                }
            }
        }
        if (arrayMap.size() == 0) {
            return;
        }
        if (size != 0) {
            readOutNotificationData.contentDescription.append("[");
        }
        for (int i2 = 0; i2 < size; i2++) {
            String str2 = list.get(i2).row.mAppName;
            if (str2 != null && arrayMap.containsKey(str2)) {
                int intValue = ((Integer) arrayMap.get(str2)).intValue();
                String stringValidater = stringValidater(str2);
                readOutNotificationData.contentDescription.append("{\"appName\":\"" + stringValidater + "\", \"notiCount\":\"" + intValue + "\"}");
                arrayMap.remove(stringValidater);
                if (arrayMap.size() != 0) {
                    readOutNotificationData.contentDescription.append(",");
                } else {
                    readOutNotificationData.contentDescription.append("]");
                }
            }
        }
        this.mDisplayDescription = readOutNotificationData.contentDescription;
    }

    private Bundle readAppNotificationWithID(String str, List<NotificationEntry> list) {
        int i;
        int i2;
        Bundle bundle;
        int i3;
        int i4;
        String str2;
        String charSequence;
        String str3;
        NotificationController notificationController = this;
        Bundle bundle2 = new Bundle();
        int size = list.size();
        int i5 = (str == null || !str.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL)) ? 6 : 5;
        StringBuffer stringBuffer = new StringBuffer();
        String str4 = "result";
        if (size == 0) {
            bundle2.putInt("result", 2);
            return bundle2;
        }
        stringBuffer.append("[");
        int i6 = 0;
        int i7 = 0;
        while (i6 < size) {
            NotificationEntry notificationEntry = list.get(i6);
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            Notification notification2 = statusBarNotification.getNotification();
            if (str == null || (!(str.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL) || statusBarNotification.getPackageName().equals(str)) || notificationController.filterReadOutNotification(notificationEntry.row))) {
                bundle = bundle2;
                i3 = size;
                i4 = i5;
                str2 = str4;
            } else {
                String key = statusBarNotification.getKey();
                i3 = size;
                if (expandableNotificationRow.mIsCustomNotification) {
                    i4 = i5;
                    StringBuffer searchForTextView = notificationController.searchForTextView(expandableNotificationRow.mPrivateLayout.mContractedChild, new StringBuffer());
                    charSequence = searchForTextView == null ? "NULL" : searchForTextView.toString();
                    str3 = "NULL";
                } else {
                    i4 = i5;
                    if (Notification.MessagingStyle.class.equals(notification2.getNotificationStyle())) {
                        Notification.MessagingStyle.Message findLatestIncomingMessage = Notification.MessagingStyle.findLatestIncomingMessage(Notification.MessagingStyle.Message.getMessagesFromBundleArray((Parcelable[]) notification2.extras.get("android.messages")));
                        CharSequence sender = findLatestIncomingMessage.getSender();
                        str3 = sender == null ? "NULL" : sender.toString();
                        CharSequence text = findLatestIncomingMessage.getText();
                        charSequence = text != null ? text.toString() : "NULL";
                    } else {
                        CharSequence charSequence2 = notification2.extras.getCharSequence("android.title");
                        String charSequence3 = charSequence2 == null ? "NULL" : charSequence2.toString();
                        CharSequence charSequence4 = notification2.extras.getCharSequence("android.text");
                        charSequence = charSequence4 != null ? charSequence4.toString() : "NULL";
                        str3 = charSequence3;
                    }
                }
                String stringValidater = notificationController.stringValidater(str3);
                String stringValidater2 = notificationController.stringValidater(charSequence);
                String str5 = notificationController.isRemoteInputNotification(notificationEntry) ? "TRUE" : "FALSE";
                String valueOf = String.valueOf(statusBarNotification.getPostTime());
                int i8 = notification2.flags;
                String str6 = (i8 & 64) != 0 ? "TRUE" : "FALSE";
                String str7 = (i8 & 2) != 0 ? "TRUE" : "FALSE";
                String stringValidater3 = notificationController.stringValidater(expandableNotificationRow.mAppName);
                bundle = bundle2;
                str2 = str4;
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("{\"notiID\":\"", key, "\", \"notiTitle\":\"", stringValidater, "\", \"notiText\":\"");
                ConstraintWidget$$ExternalSyntheticOutline0.m(m, stringValidater2, "\", \"canReply\":\"", str5, "\", \"when\":\"");
                ConstraintWidget$$ExternalSyntheticOutline0.m(m, valueOf, "\", \"fgs\":\"", str6, "\", \"ongoing\":\"");
                m.append(str7);
                m.append("\", \"appName\":\"");
                m.append(stringValidater3);
                m.append("\"},");
                stringBuffer.append(m.toString());
                i7++;
            }
            i6++;
            notificationController = this;
            size = i3;
            bundle2 = bundle;
            i5 = i4;
            str4 = str2;
        }
        Bundle bundle3 = bundle2;
        int i9 = i5;
        String str8 = str4;
        int length = stringBuffer.length();
        if (length > 1) {
            stringBuffer.setLength(length - 1);
            stringBuffer.append("]");
            i2 = i7;
            i = i9;
        } else {
            i = str.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL) ? 2 : 3;
            stringBuffer = new StringBuffer();
            i2 = 0;
        }
        StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "result: ", " notiList:");
        m2.append(stringBuffer.length());
        m2.append(" itemCount:");
        m2.append(i2);
        Log.d(TAG, m2.toString());
        bundle3.putInt(str8, i);
        bundle3.putString(NOTI_LIST, stringBuffer.toString());
        bundle3.putInt(NOTI_ITEM_COUNT, i2);
        return bundle3;
    }

    private StringBuffer searchForTextView(View view, StringBuffer stringBuffer) {
        if (view instanceof TextView) {
            stringBuffer.append(((TextView) view).getText().toString());
            return stringBuffer;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                searchForTextView(viewGroup.getChildAt(i), stringBuffer);
            }
        }
        return stringBuffer;
    }

    private String stringValidater(String str) {
        return str.length() > 0 ? str.replaceAll("\"", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n\r", "") : str;
    }

    public int checkNotificationSoundStatus(String str) {
        try {
            int notificationSoundStatus = this.mNotifManager.getNotificationSoundStatus(str);
            Log.d(TAG, "checkNotificationSoundStatus : pkg = " + str + " ret = 0x" + Integer.toHexString(notificationSoundStatus));
            return notificationSoundStatus;
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m("fail to checkNotificationSoundStatus e = ", e, TAG);
            return 0;
        }
    }

    public int checkNotificationStatusForPackage(String str) {
        if (str == null) {
            return 0;
        }
        try {
            int isNotificationTurnedOff = this.mNotifManager.isNotificationTurnedOff(str, this.mContext.getPackageManager().getPackageUid(str, 0));
            Log.d(TAG, "checkNotificationStatusForPackage : 0x" + Integer.toHexString(isNotificationTurnedOff));
            return isNotificationTurnedOff;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "NameNotFoundException" + e);
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public int deleteNotification(String str) {
        int i;
        List<NotificationEntry> visibleNotifications = getVisibleNotifications();
        if (!checkNotificatoins(visibleNotifications.size())) {
            i = 2;
        } else if (str == null) {
            Log.d(TAG, "deleteAllNotification ");
            i = deleteAllNotifications(visibleNotifications);
        } else if (str.equals(PARAMETER_INCLUDE_ONGOING)) {
            Log.d(TAG, "deleteAllNotification with ongoing");
            i = deleteNotificationAllDismissable();
        } else {
            Log.d(TAG, "deleteNotification ".concat(str));
            i = deleteAppNotifications(str, visibleNotifications);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, " deleteNotification : ", TAG);
        return i;
    }

    public int deleteNotificationAllDismissable() {
        int i;
        List<NotificationEntry> visibleNotifications = getVisibleNotifications();
        if (checkNotificatoins(visibleNotifications.size())) {
            Log.d(TAG, "deleteAllNotificationAllDismissable ");
            i = deleteAllNotificationsDismissable(visibleNotifications);
        } else {
            i = 2;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, " deleteNotificationAllDismissable : ", TAG);
        return i;
    }

    public boolean filterReadOutNotification(ExpandableNotificationRow expandableNotificationRow) {
        return expandableNotificationRow.mIsSummaryWithChildren;
    }

    public int getAppNotificationSettingStatus(String str) {
        try {
            int appNotificationSettingStatus = this.mNotifManager.getAppNotificationSettingStatus(str);
            Log.d(TAG, "getAppNotificationSettingStatus : 0x" + Integer.toHexString(appNotificationSettingStatus));
            return appNotificationSettingStatus;
        } catch (Exception unused) {
            return 0;
        }
    }

    public StringBuffer getDisplayDescription() {
        return this.mDisplayDescription;
    }

    public String getFocusedPackageName() {
        ActivityTaskManager.RootTaskInfo focusedStack = getFocusedStack();
        if (focusedStack == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("topActivity = ");
        sb.append(focusedStack.topActivity);
        sb.append(", visible = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, focusedStack.visible, TAG);
        if (focusedStack.topActivity == null || !focusedStack.visible) {
            return null;
        }
        Log.d(TAG, "focusedPackageName = " + focusedStack.topActivity.getPackageName());
        return focusedStack.topActivity.getPackageName();
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public int getNotificationSettingStatus(boolean z) {
        try {
            int notificationSettingStatus = this.mNotifManager.getNotificationSettingStatus(z);
            Log.d(TAG, "getNotificationSettingStatus : 0x" + Integer.toHexString(notificationSettingStatus));
            return notificationSettingStatus;
        } catch (Exception unused) {
            return 0;
        }
    }

    public boolean goToNotiSettings(Context context) {
        Intent intent = new Intent("android.settings.NOTIFICATION_POPUP_STYLE_SETTINGS", (Uri) null);
        intent.addFlags(268468224);
        if (BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG && isFolderClosed()) {
            Intent intent2 = new Intent();
            intent2.putExtra("showCoverToast", true);
            intent2.putExtra("ignoreKeyguardState", true);
            this.mKeyguardManager.semSetPendingIntentAfterUnlock(PendingIntent.getActivityAsUser(context, 0, intent, 201326592, null, UserHandle.CURRENT_OR_SELF), intent2);
        } else {
            context.startActivity(intent);
        }
        return true;
    }

    public void openNotificationPanel() {
        try {
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
                (subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null).notifyNotificationSubRoomRequest();
                return;
            }
            int i = this.mDesktopManager.getSemDesktopModeState().getEnabled() == 4 ? 1 : 0;
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
            if (asInterface != null) {
                asInterface.expandNotificationsPanelToType(i);
            }
        } catch (RemoteException unused) {
            Log.d(TAG, "error while expandNotificationsPanel");
        }
    }

    public Bundle readNotificationWithID(String str) {
        Bundle readAppNotificationWithID;
        List<NotificationEntry> visibleNotifications = getVisibleNotifications();
        if (str == null) {
            Log.d(TAG, "readAllNotificationWithID   activeNotifications = " + visibleNotifications);
            readAppNotificationWithID = readAppNotificationWithID(SystemUIAnalytics.QPNE_VID_COVER_ALL, visibleNotifications);
            readAllNotification(visibleNotifications);
        } else {
            Log.d(TAG, "readAppNotificationWithID ".concat(str));
            readAppNotificationWithID = readAppNotificationWithID(str, visibleNotifications);
        }
        Log.d(TAG, "readNotification = " + readAppNotificationWithID.getInt("result"));
        return readAppNotificationWithID;
    }

    public int replyNotification(String str, String str2) {
        NotificationEntry entry = this.mNotifPipeline.mNotifCollection.getEntry(str);
        if (entry == null) {
            Log.i(TAG, "Unable to send remote input result, entry is null : ".concat(str));
            return 0;
        }
        if (!isRemoteInputNotification(entry)) {
            Log.i(TAG, "Unable to send remote input result, not remote input : ".concat(str));
            return 0;
        }
        Bundle bundle = new Bundle();
        bundle.putString(this.mRemoteInput.getResultKey(), str2);
        Intent addFlags = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        RemoteInput.addResultsToIntent(this.mRemoteInputs, addFlags, bundle);
        entry.remoteInputText = str2;
        entry.remoteInputUri = null;
        entry.remoteInputMimeType = null;
        RemoteInput.setResultsSource(addFlags, 0);
        try {
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
                this.mNotifManager.addReplyHistory(1, entry.mKey, entry.mSbn.getPackageName(), entry.mSbn.getUser().getIdentifier(), "NOUI_2023", str2);
            }
            this.mRemoteInputIntent.send(this.mContext, 0, addFlags);
            Log.i(TAG, "send remote input result by others : ".concat(str));
            return 1;
        } catch (PendingIntent.CanceledException e) {
            Log.i(TAG, "Unable to send remote input result", e);
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public void setNotificationEntries(List<ListEntry> list) {
        this.mEntries = list;
    }

    public int setNotificationTurnOffForPackage(String str) {
        if (str == null) {
            return 0;
        }
        if (!SecNotificationBlockManager.isBlockablePackage(this.mContext, str)) {
            Log.d(TAG, "Non-blockable package : ".concat(str));
            return 8;
        }
        try {
            if (this.mNotifManager.setNotificationTurnOff(str, this.mContext.getPackageManager().getPackageUid(str, 0))) {
                return 1;
            }
            Log.d(TAG, "Package (" + str + ") notification is already turned off");
            return 9;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "NameNotFoundException" + e);
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }
}
