package android.service.notification;

import android.annotation.SystemApi;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.Person;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.security.keystore.KeyProperties;
import android.service.notification.INotificationListener;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.internal.os.SomeArgs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class NotificationListenerService extends Service {
    public static final String ACTION_SETTINGS_HOME = "android.service.notification.action.SETTINGS_HOME";
    public static final int FLAG_FILTER_TYPE_ALERTING = 2;
    public static final int FLAG_FILTER_TYPE_CONVERSATIONS = 1;
    public static final int FLAG_FILTER_TYPE_ONGOING = 8;
    public static final int FLAG_FILTER_TYPE_SILENT = 4;
    public static final int HINT_HOST_DISABLE_CALL_EFFECTS = 4;
    public static final int HINT_HOST_DISABLE_EFFECTS = 1;
    public static final int HINT_HOST_DISABLE_NOTIFICATION_EFFECTS = 2;
    public static final int INTERRUPTION_FILTER_ALARMS = 4;
    public static final int INTERRUPTION_FILTER_ALL = 1;
    public static final int INTERRUPTION_FILTER_NONE = 3;
    public static final int INTERRUPTION_FILTER_PRIORITY = 2;
    public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
    public static final String META_DATA_DEFAULT_AUTOBIND = "android.service.notification.default_autobind_listenerservice";
    public static final String META_DATA_DEFAULT_FILTER_TYPES = "android.service.notification.default_filter_types";
    public static final String META_DATA_DISABLED_FILTER_TYPES = "android.service.notification.disabled_filter_types";
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_ADDED = 1;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_DELETED = 3;
    public static final int NOTIFICATION_CHANNEL_OR_GROUP_UPDATED = 2;
    public static final int REASON_APP_CANCEL = 8;
    public static final int REASON_APP_CANCEL_ALL = 9;
    public static final int REASON_ASSISTANT_CANCEL = 22;
    public static final int REASON_CANCEL = 2;
    public static final int REASON_CANCEL_ALL = 3;
    public static final int REASON_CHANNEL_BANNED = 17;
    public static final int REASON_CHANNEL_REMOVED = 20;
    public static final int REASON_CLEAR_DATA = 21;
    public static final int REASON_CLICK = 1;
    public static final int REASON_ERROR = 4;
    public static final int REASON_GROUP_CHILD_CANCELED = 24;
    public static final int REASON_GROUP_OPTIMIZATION = 13;
    public static final int REASON_GROUP_SUMMARY_CANCELED = 12;
    public static final int REASON_LISTENER_CANCEL = 10;
    public static final int REASON_LISTENER_CANCEL_ALL = 11;
    public static final int REASON_LOCKDOWN = 23;
    public static final int REASON_MAX_NOTIFICATIONS = 25;
    public static final int REASON_PACKAGE_BANNED = 7;
    public static final int REASON_PACKAGE_CHANGED = 5;
    public static final int REASON_PACKAGE_SUSPENDED = 14;
    public static final int REASON_PROFILE_TURNED_OFF = 15;
    public static final int REASON_SNOOZED = 18;
    public static final int REASON_TIMEOUT = 19;
    public static final int REASON_UNAUTOBUNDLED = 16;
    public static final int REASON_USER_STOPPED = 6;
    public static final String SERVICE_INTERFACE = "android.service.notification.NotificationListenerService";

    @Deprecated
    public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;

    @Deprecated
    public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;

    @SystemApi
    public static final int TRIM_FULL = 0;

    @SystemApi
    public static final int TRIM_LIGHT = 1;
    protected int mCurrentUser;
    private Handler mHandler;
    protected INotificationManager mNoMan;
    private RankingMap mRankingMap;
    protected Context mSystemContext;
    private final String TAG = getClass().getSimpleName();
    private final Object mLock = new Object();
    protected NotificationListenerWrapper mWrapper = null;
    private boolean isConnected = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChannelOrGroupModificationTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationCancelReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationFilterTypes {
    }

    public void onEdgeNotificationPosted(String str, int i, Bundle bundle) {
    }

    public void onEdgeNotificationRemoved(String str, int i, Bundle bundle) {
    }

    public void onInterruptionFilterChanged(int i) {
    }

    public void onListenerConnected() {
    }

    public void onListenerDisconnected() {
    }

    public void onListenerHintsChanged(int i) {
    }

    public void onNotificationChannelGroupModified(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) {
    }

    public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
    }

    public void onNotificationRankingUpdate(RankingMap rankingMap) {
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    }

    public void onSilentStatusBarIconsVisibilityChanged(boolean z) {
    }

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new MyHandler(getMainLooper());
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification, RankingMap rankingMap) {
        onNotificationPosted(statusBarNotification);
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification, RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification);
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification, RankingMap rankingMap, int i) {
        onNotificationRemoved(statusBarNotification, rankingMap);
    }

    @SystemApi
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, RankingMap rankingMap, NotificationStats notificationStats, int i) {
        onNotificationRemoved(statusBarNotification, rankingMap, i);
    }

    protected final INotificationManager getNotificationInterface() {
        if (this.mNoMan == null) {
            this.mNoMan = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        }
        return this.mNoMan;
    }

    @Deprecated
    public final void cancelNotification(String str, String str2, int i) {
        if (isBound()) {
            try {
                getNotificationInterface().cancelNotificationFromListener(this.mWrapper, str, str2, i);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void cancelNotification(String str) {
        if (isBound()) {
            try {
                getNotificationInterface().cancelNotificationsFromListener(this.mWrapper, new String[]{str});
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void cancelAllNotifications() {
        cancelNotifications(null);
    }

    public final void cancelNotifications(String[] strArr) {
        if (isBound()) {
            try {
                getNotificationInterface().cancelNotificationsFromListener(this.mWrapper, strArr);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    @SystemApi
    public final void snoozeNotification(String str, String str2) {
        if (isBound()) {
            try {
                getNotificationInterface().snoozeNotificationUntilContextFromListener(this.mWrapper, str, str2);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void snoozeNotification(String str, long j) {
        if (isBound()) {
            try {
                getNotificationInterface().snoozeNotificationUntilFromListener(this.mWrapper, str, j);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void migrateNotificationFilter(int i, List<String> list) {
        if (isBound()) {
            try {
                getNotificationInterface().migrateNotificationFilter(this.mWrapper, i, list);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void setNotificationsShown(String[] strArr) {
        if (isBound()) {
            try {
                getNotificationInterface().setNotificationsShownFromListener(this.mWrapper, strArr);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final NotificationChannel createConversationNotificationChannelForPackage(String str, UserHandle userHandle, String str2, String str3) {
        if (!isBound()) {
            return null;
        }
        try {
            return getNotificationInterface().createConversationNotificationChannelForPackageFromPrivilegedListener(this.mWrapper, str, userHandle, str2, str3);
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public final void updateNotificationChannel(String str, UserHandle userHandle, NotificationChannel notificationChannel) {
        if (isBound()) {
            try {
                getNotificationInterface().updateNotificationChannelFromPrivilegedListener(this.mWrapper, str, userHandle, notificationChannel);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final List<NotificationChannel> getNotificationChannels(String str, UserHandle userHandle) {
        if (!isBound()) {
            return null;
        }
        try {
            return getNotificationInterface().getNotificationChannelsFromPrivilegedListener(this.mWrapper, str, userHandle).getList();
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public final List<NotificationChannelGroup> getNotificationChannelGroups(String str, UserHandle userHandle) {
        if (!isBound()) {
            return null;
        }
        try {
            return getNotificationInterface().getNotificationChannelGroupsFromPrivilegedListener(this.mWrapper, str, userHandle).getList();
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public final void setOnNotificationPostedTrim(int i) {
        if (isBound()) {
            try {
                getNotificationInterface().setOnNotificationPostedTrimFromListener(this.mWrapper, i);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public StatusBarNotification[] getActiveNotifications() {
        StatusBarNotification[] activeNotifications = getActiveNotifications(null, 0);
        return activeNotifications != null ? activeNotifications : new StatusBarNotification[0];
    }

    public final StatusBarNotification[] getSnoozedNotifications() {
        try {
            return cleanUpNotificationList(getNotificationInterface().getSnoozedNotificationsFromListener(this.mWrapper, 0));
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            return null;
        }
    }

    @SystemApi
    public StatusBarNotification[] getActiveNotifications(int i) {
        StatusBarNotification[] activeNotifications = getActiveNotifications(null, i);
        return activeNotifications != null ? activeNotifications : new StatusBarNotification[0];
    }

    public StatusBarNotification[] getActiveNotifications(String[] strArr) {
        StatusBarNotification[] activeNotifications = getActiveNotifications(strArr, 0);
        return activeNotifications != null ? activeNotifications : new StatusBarNotification[0];
    }

    @SystemApi
    public StatusBarNotification[] getActiveNotifications(String[] strArr, int i) {
        if (!isBound()) {
            return null;
        }
        try {
            return cleanUpNotificationList(getNotificationInterface().getActiveNotificationsFromListener(this.mWrapper, strArr, i));
        } catch (BadParcelableException | RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            return null;
        }
    }

    private StatusBarNotification[] cleanUpNotificationList(ParceledListSlice<StatusBarNotification> parceledListSlice) {
        if (parceledListSlice == null || parceledListSlice.getList() == null) {
            return new StatusBarNotification[0];
        }
        List list = parceledListSlice.getList();
        int size = list.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            StatusBarNotification statusBarNotification = (StatusBarNotification) list.get(i);
            Notification notification = statusBarNotification.getNotification();
            try {
                createLegacyIconExtras(notification);
                maybePopulateRemoteViews(notification);
                maybePopulatePeople(notification);
            } catch (IllegalArgumentException unused) {
                if (arrayList == null) {
                    arrayList = new ArrayList(size);
                }
                arrayList.add(statusBarNotification);
                Log.w(this.TAG, "get(Active/Snoozed)Notifications: can't rebuild notification from " + statusBarNotification.getPackageName());
            }
        }
        if (arrayList != null) {
            list.removeAll(arrayList);
        }
        return (StatusBarNotification[]) list.toArray(new StatusBarNotification[list.size()]);
    }

    public final int getCurrentListenerHints() {
        if (!isBound()) {
            return 0;
        }
        try {
            return getNotificationInterface().getHintsFromListener(this.mWrapper);
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            return 0;
        }
    }

    public final int getCurrentInterruptionFilter() {
        if (!isBound()) {
            return 0;
        }
        try {
            return getNotificationInterface().getInterruptionFilterFromListener(this.mWrapper);
        } catch (RemoteException e) {
            Log.v(this.TAG, "Unable to contact notification manager", e);
            return 0;
        }
    }

    public final void clearRequestedListenerHints() {
        if (isBound()) {
            try {
                getNotificationInterface().clearRequestedListenerHints(this.mWrapper);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void requestListenerHints(int i) {
        if (isBound()) {
            try {
                getNotificationInterface().requestHintsFromListener(this.mWrapper, i);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void requestInterruptionFilter(int i) {
        if (isBound()) {
            try {
                getNotificationInterface().requestInterruptionFilterFromListener(this.mWrapper, i);
            } catch (RemoteException e) {
                Log.v(this.TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public RankingMap getCurrentRanking() {
        RankingMap rankingMap;
        synchronized (this.mLock) {
            rankingMap = this.mRankingMap;
        }
        return rankingMap;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new NotificationListenerWrapper();
        }
        return this.mWrapper;
    }

    protected boolean isBound() {
        if (this.mWrapper != null) {
            return true;
        }
        Log.w(this.TAG, "Notification listener service not yet bound.");
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        onListenerDisconnected();
        super.onDestroy();
    }

    @SystemApi
    public void registerAsSystemService(Context context, ComponentName componentName, int i) throws RemoteException {
        if (this.mWrapper == null) {
            this.mWrapper = new NotificationListenerWrapper();
        }
        this.mSystemContext = context;
        INotificationManager notificationInterface = getNotificationInterface();
        this.mHandler = new MyHandler(context.getMainLooper());
        this.mCurrentUser = i;
        notificationInterface.registerListener(this.mWrapper, componentName, i);
    }

    @SystemApi
    public void unregisterAsSystemService() throws RemoteException {
        if (this.mWrapper != null) {
            getNotificationInterface().unregisterListener(this.mWrapper, this.mCurrentUser);
        }
    }

    public static void requestRebind(ComponentName componentName) {
        try {
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification")).requestBindListener(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void requestUnbind(ComponentName componentName) {
        try {
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification")).requestUnbindListenerComponent(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void requestUnbind() {
        if (this.mWrapper != null) {
            try {
                getNotificationInterface().requestUnbindListener(this.mWrapper);
                this.isConnected = false;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void createLegacyIconExtras(Notification notification) {
        Drawable drawableLoadDrawable;
        if (getContext().getApplicationInfo().targetSdkVersion < 23) {
            Icon smallIcon = notification.getSmallIcon();
            Icon largeIcon = notification.getLargeIcon();
            if (smallIcon != null && smallIcon.getType() == 2) {
                notification.extras.putInt(Notification.EXTRA_SMALL_ICON, smallIcon.getResId());
                notification.icon = smallIcon.getResId();
            }
            if (largeIcon == null || (drawableLoadDrawable = largeIcon.loadDrawable(getContext())) == null || !(drawableLoadDrawable instanceof BitmapDrawable)) {
                return;
            }
            Bitmap bitmap = ((BitmapDrawable) drawableLoadDrawable).getBitmap();
            notification.extras.putParcelable(Notification.EXTRA_LARGE_ICON, bitmap);
            notification.largeIcon = bitmap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybePopulateRemoteViews(Notification notification) {
        if (getContext().getApplicationInfo().targetSdkVersion < 24) {
            Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(getContext(), notification);
            RemoteViews remoteViewsCreateContentView = builderRecoverBuilder.createContentView();
            RemoteViews remoteViewsCreateBigContentView = builderRecoverBuilder.createBigContentView();
            RemoteViews remoteViewsCreateHeadsUpContentView = builderRecoverBuilder.createHeadsUpContentView();
            notification.contentView = remoteViewsCreateContentView;
            notification.bigContentView = remoteViewsCreateBigContentView;
            notification.headsUpContentView = remoteViewsCreateHeadsUpContentView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybePopulatePeople(Notification notification) {
        ArrayList parcelableArrayList;
        if (getContext().getApplicationInfo().targetSdkVersion >= 28 || (parcelableArrayList = notification.extras.getParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, Person.class)) == null || parcelableArrayList.isEmpty()) {
            return;
        }
        int size = parcelableArrayList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = ((Person) parcelableArrayList.get(i)).resolveToLegacyUri();
        }
        notification.extras.putStringArray(Notification.EXTRA_PEOPLE, strArr);
    }

    protected class NotificationListenerWrapper extends INotificationListener.Stub {
        public void onActionClicked(String str, Notification.Action action, int i) {
        }

        public void onAllowedAdjustmentsChanged() {
        }

        public void onNotificationClicked(String str) {
        }

        public void onNotificationDirectReply(String str) {
        }

        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        public void onNotificationEnqueuedWithChannelFull(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
        }

        public void onNotificationExpansionChanged(String str, boolean z, boolean z2) {
        }

        public void onNotificationFeedbackReceived(String str, NotificationRankingUpdate notificationRankingUpdate, Bundle bundle) {
        }

        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String str) throws RemoteException {
        }

        public void onNotificationSnoozedUntilContextFull(StatusBarNotification statusBarNotification, String str) throws RemoteException {
        }

        public void onNotificationVisibilityChanged(String str, boolean z) {
        }

        public void onNotificationsSeen(List<String> list) throws RemoteException {
        }

        public void onPanelHidden() throws RemoteException {
        }

        public void onPanelRevealed(int i) throws RemoteException {
        }

        public void onSuggestedReplySent(String str, CharSequence charSequence, int i) {
        }

        protected NotificationListenerWrapper() {
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationPosted(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate) {
            try {
                StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    Log.w(NotificationListenerService.this.TAG, "onNotificationPosted: Error receiving StatusBarNotification");
                } else {
                    onNotificationPostedFull(statusBarNotification, notificationRankingUpdate);
                }
            } catch (RemoteException e) {
                Log.w(NotificationListenerService.this.TAG, "onNotificationPosted: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationPostedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate) {
            try {
                NotificationListenerService.this.createLegacyIconExtras(statusBarNotification.getNotification());
                NotificationListenerService.this.maybePopulateRemoteViews(statusBarNotification.getNotification());
                NotificationListenerService.this.maybePopulatePeople(statusBarNotification.getNotification());
            } catch (IllegalArgumentException unused) {
                Log.w(NotificationListenerService.this.TAG, "onNotificationPosted: can't rebuild notification from " + statusBarNotification.getPackageName());
                statusBarNotification = null;
            }
            synchronized (NotificationListenerService.this.mLock) {
                NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                if (statusBarNotification != null) {
                    SomeArgs someArgsObtain = SomeArgs.obtain();
                    someArgsObtain.arg1 = statusBarNotification;
                    someArgsObtain.arg2 = NotificationListenerService.this.mRankingMap;
                    NotificationListenerService.this.mHandler.obtainMessage(1, someArgsObtain).sendToTarget();
                } else {
                    NotificationListenerService.this.mHandler.obtainMessage(4, NotificationListenerService.this.mRankingMap).sendToTarget();
                }
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRemoved(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) {
            try {
                onNotificationRemovedFull(iStatusBarNotificationHolder.get(), notificationRankingUpdate, notificationStats, i);
            } catch (RemoteException e) {
                Log.w(NotificationListenerService.this.TAG, "onNotificationRemoved: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRemovedFull(StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) {
            if (statusBarNotification == null) {
                Log.w(NotificationListenerService.this.TAG, "onNotificationRemoved: Error receiving StatusBarNotification");
                return;
            }
            synchronized (NotificationListenerService.this.mLock) {
                NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = statusBarNotification;
                someArgsObtain.arg2 = NotificationListenerService.this.mRankingMap;
                someArgsObtain.arg3 = Integer.valueOf(i);
                someArgsObtain.arg4 = notificationStats;
                NotificationListenerService.this.mHandler.obtainMessage(2, someArgsObtain).sendToTarget();
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onEdgeNotificationPosted(String str, int i, Bundle bundle) {
            try {
                NotificationListenerService.this.onEdgeNotificationPosted(str, i, bundle);
            } catch (Throwable th) {
                Log.w(NotificationListenerService.this.TAG, "Error running onInterruptionFilterChanged", th);
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onEdgeNotificationRemoved(String str, int i, Bundle bundle) {
            try {
                NotificationListenerService.this.onEdgeNotificationRemoved(str, i, bundle);
            } catch (Throwable th) {
                Log.w(NotificationListenerService.this.TAG, "Error running onInterruptionFilterChanged", th);
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerConnected(NotificationRankingUpdate notificationRankingUpdate) {
            synchronized (NotificationListenerService.this.mLock) {
                NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
            }
            NotificationListenerService.this.isConnected = true;
            NotificationListenerService.this.mHandler.obtainMessage(3).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationRankingUpdate(NotificationRankingUpdate notificationRankingUpdate) throws RemoteException {
            synchronized (NotificationListenerService.this.mLock) {
                NotificationListenerService.this.applyUpdateLocked(notificationRankingUpdate);
                NotificationListenerService.this.mHandler.obtainMessage(4, NotificationListenerService.this.mRankingMap).sendToTarget();
            }
        }

        @Override // android.service.notification.INotificationListener
        public void onListenerHintsChanged(int i) throws RemoteException {
            NotificationListenerService.this.mHandler.obtainMessage(5, i, 0).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onInterruptionFilterChanged(int i) throws RemoteException {
            NotificationListenerService.this.mHandler.obtainMessage(6, i, 0).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelModification(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = userHandle;
            someArgsObtain.arg3 = notificationChannel;
            someArgsObtain.arg4 = Integer.valueOf(i);
            NotificationListenerService.this.mHandler.obtainMessage(7, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onNotificationChannelGroupModification(String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = userHandle;
            someArgsObtain.arg3 = notificationChannelGroup;
            someArgsObtain.arg4 = Integer.valueOf(i);
            NotificationListenerService.this.mHandler.obtainMessage(8, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.INotificationListener
        public void onStatusBarIconsBehaviorChanged(boolean z) {
            NotificationListenerService.this.mHandler.obtainMessage(9, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void applyUpdateLocked(NotificationRankingUpdate notificationRankingUpdate) {
        this.mRankingMap = notificationRankingUpdate.getRankingMap();
    }

    protected Context getContext() {
        Context context = this.mSystemContext;
        return context != null ? context : this;
    }

    public static class Ranking {
        private static final int PARCEL_VERSION = 2;
        public static final int RANKING_DEMOTED = -1;
        public static final int RANKING_PROMOTED = 1;
        public static final int RANKING_UNCHANGED = 0;
        public static final int USER_SENTIMENT_NEGATIVE = -1;
        public static final int USER_SENTIMENT_NEUTRAL = 0;
        public static final int USER_SENTIMENT_POSITIVE = 1;
        public static final int VISIBILITY_NO_OVERRIDE = -1000;
        private boolean mCanBubble;
        private NotificationChannel mChannel;
        private boolean mHidden;
        private int mImportance;
        private CharSequence mImportanceExplanation;
        private boolean mIsAmbient;
        private boolean mIsBubble;
        private boolean mIsConversation;
        private boolean mIsOngoingActivityTurnedOn;
        private boolean mIsTextChanged;
        private String mKey;
        private long mLastAudiblyAlertedMs;
        private boolean mMatchesInterruptionFilter;
        private boolean mNoisy;
        private String mOverrideGroupKey;
        private ArrayList<String> mOverridePeople;
        private int mProposedImportance;
        private int mRank;
        private int mRankingAdjustment;
        private float mRankingScore;
        private boolean mSensitiveContent;
        private ShortcutInfo mShortcutInfo;
        private boolean mShowBadge;
        private ArrayList<Notification.Action> mSmartActions;
        private ArrayList<CharSequence> mSmartReplies;
        private ArrayList<SnoozeCriterion> mSnoozeCriteria;
        private String mSummarization;
        private int mSuppressedVisualEffects;
        private int mUserSentiment;
        private int mVisibilityOverride;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RankingAdjustment {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface UserSentiment {
        }

        public Ranking() {
            this.mRank = -1;
            this.mUserSentiment = 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.dataPosition();
            parcel.writeInt(2);
            parcel.writeString(this.mKey);
            parcel.writeInt(this.mRank);
            parcel.writeBoolean(this.mIsAmbient);
            parcel.writeBoolean(this.mMatchesInterruptionFilter);
            parcel.writeInt(this.mVisibilityOverride);
            parcel.writeInt(this.mSuppressedVisualEffects);
            parcel.writeInt(this.mImportance);
            parcel.writeCharSequence(this.mImportanceExplanation);
            parcel.writeFloat(this.mRankingScore);
            parcel.writeString(this.mOverrideGroupKey);
            parcel.writeParcelable(this.mChannel, i);
            parcel.writeStringList(this.mOverridePeople);
            parcel.writeTypedList(this.mSnoozeCriteria, i);
            parcel.writeBoolean(this.mShowBadge);
            parcel.writeInt(this.mUserSentiment);
            parcel.writeBoolean(this.mHidden);
            parcel.writeLong(this.mLastAudiblyAlertedMs);
            parcel.writeBoolean(this.mNoisy);
            parcel.writeTypedList(this.mSmartActions, i);
            parcel.writeCharSequenceList(this.mSmartReplies);
            parcel.writeBoolean(this.mCanBubble);
            parcel.writeBoolean(this.mIsTextChanged);
            parcel.writeBoolean(this.mIsConversation);
            parcel.writeParcelable(this.mShortcutInfo, i);
            parcel.writeInt(this.mRankingAdjustment);
            parcel.writeBoolean(this.mIsBubble);
            parcel.writeInt(this.mProposedImportance);
            parcel.writeBoolean(this.mSensitiveContent);
            parcel.writeString(this.mSummarization);
            parcel.writeBoolean(this.mIsOngoingActivityTurnedOn);
        }

        public Ranking(Parcel parcel) {
            this.mRank = -1;
            this.mUserSentiment = 0;
            ClassLoader classLoader = getClass().getClassLoader();
            int i = parcel.readInt();
            if (i != 2) {
                throw new IllegalArgumentException("malformed Ranking parcel: " + parcel + " version " + i + ", expected 2");
            }
            this.mKey = parcel.readString();
            this.mRank = parcel.readInt();
            this.mIsAmbient = parcel.readBoolean();
            this.mMatchesInterruptionFilter = parcel.readBoolean();
            this.mVisibilityOverride = parcel.readInt();
            this.mSuppressedVisualEffects = parcel.readInt();
            this.mImportance = parcel.readInt();
            this.mImportanceExplanation = parcel.readCharSequence();
            this.mRankingScore = parcel.readFloat();
            this.mOverrideGroupKey = parcel.readString();
            this.mChannel = (NotificationChannel) parcel.readParcelable(classLoader, NotificationChannel.class);
            this.mOverridePeople = parcel.createStringArrayList();
            this.mSnoozeCriteria = parcel.createTypedArrayList(SnoozeCriterion.CREATOR);
            this.mShowBadge = parcel.readBoolean();
            this.mUserSentiment = parcel.readInt();
            this.mHidden = parcel.readBoolean();
            this.mLastAudiblyAlertedMs = parcel.readLong();
            this.mNoisy = parcel.readBoolean();
            this.mSmartActions = parcel.createTypedArrayList(Notification.Action.CREATOR);
            this.mSmartReplies = parcel.readCharSequenceList();
            this.mCanBubble = parcel.readBoolean();
            this.mIsTextChanged = parcel.readBoolean();
            this.mIsConversation = parcel.readBoolean();
            this.mShortcutInfo = (ShortcutInfo) parcel.readParcelable(classLoader, ShortcutInfo.class);
            this.mRankingAdjustment = parcel.readInt();
            this.mIsBubble = parcel.readBoolean();
            this.mProposedImportance = parcel.readInt();
            this.mSensitiveContent = parcel.readBoolean();
            this.mSummarization = parcel.readString();
            this.mIsOngoingActivityTurnedOn = parcel.readBoolean();
        }

        public String getKey() {
            return this.mKey;
        }

        public int getRank() {
            return this.mRank;
        }

        public boolean isAmbient() {
            return this.mIsAmbient;
        }

        public int getLockscreenVisibilityOverride() {
            return this.mVisibilityOverride;
        }

        public int getSuppressedVisualEffects() {
            return this.mSuppressedVisualEffects;
        }

        public boolean matchesInterruptionFilter() {
            return this.mMatchesInterruptionFilter;
        }

        public int getImportance() {
            return this.mImportance;
        }

        public CharSequence getImportanceExplanation() {
            return this.mImportanceExplanation;
        }

        public float getRankingScore() {
            return this.mRankingScore;
        }

        @SystemApi
        public int getProposedImportance() {
            return this.mProposedImportance;
        }

        @SystemApi
        public boolean hasSensitiveContent() {
            return this.mSensitiveContent;
        }

        public boolean isOngoingActivityTurnedOn() {
            return this.mIsOngoingActivityTurnedOn;
        }

        public String getOverrideGroupKey() {
            return this.mOverrideGroupKey;
        }

        public NotificationChannel getChannel() {
            return this.mChannel;
        }

        public int getUserSentiment() {
            return this.mUserSentiment;
        }

        @SystemApi
        public List<String> getAdditionalPeople() {
            return this.mOverridePeople;
        }

        @SystemApi
        public List<SnoozeCriterion> getSnoozeCriteria() {
            return this.mSnoozeCriteria;
        }

        public List<Notification.Action> getSmartActions() {
            ArrayList<Notification.Action> arrayList = this.mSmartActions;
            return arrayList == null ? Collections.EMPTY_LIST : arrayList;
        }

        public void setSmartActions(ArrayList<Notification.Action> arrayList) {
            this.mSmartActions = arrayList;
        }

        public List<CharSequence> getSmartReplies() {
            ArrayList<CharSequence> arrayList = this.mSmartReplies;
            return arrayList == null ? Collections.EMPTY_LIST : arrayList;
        }

        public boolean canShowBadge() {
            return this.mShowBadge;
        }

        public boolean isSuspended() {
            return this.mHidden;
        }

        public long getLastAudiblyAlertedMillis() {
            return this.mLastAudiblyAlertedMs;
        }

        public boolean canBubble() {
            return this.mCanBubble;
        }

        public boolean isTextChanged() {
            return this.mIsTextChanged;
        }

        public boolean isNoisy() {
            return this.mNoisy;
        }

        public boolean isConversation() {
            return this.mIsConversation;
        }

        public boolean isBubble() {
            return this.mIsBubble;
        }

        public ShortcutInfo getConversationShortcutInfo() {
            return this.mShortcutInfo;
        }

        public String getSummarization() {
            return this.mSummarization;
        }

        public int getRankingAdjustment() {
            return this.mRankingAdjustment;
        }

        public void populate(String str, int i, boolean z, int i2, int i3, int i4, CharSequence charSequence, String str2, NotificationChannel notificationChannel, ArrayList<String> arrayList, ArrayList<SnoozeCriterion> arrayList2, boolean z2, int i5, boolean z3, long j, boolean z4, ArrayList<Notification.Action> arrayList3, ArrayList<CharSequence> arrayList4, boolean z5, boolean z6, boolean z7, ShortcutInfo shortcutInfo, int i6, boolean z8, int i7, boolean z9, String str3) {
            this.mKey = str;
            this.mRank = i;
            this.mIsAmbient = i4 < 2;
            this.mMatchesInterruptionFilter = z;
            this.mVisibilityOverride = i2;
            this.mSuppressedVisualEffects = i3;
            this.mImportance = i4;
            this.mImportanceExplanation = charSequence;
            this.mOverrideGroupKey = str2;
            this.mChannel = notificationChannel;
            this.mOverridePeople = arrayList;
            this.mSnoozeCriteria = arrayList2;
            this.mShowBadge = z2;
            this.mUserSentiment = i5;
            this.mHidden = z3;
            this.mLastAudiblyAlertedMs = j;
            this.mNoisy = z4;
            this.mSmartActions = arrayList3;
            this.mSmartReplies = arrayList4;
            this.mCanBubble = z5;
            this.mIsTextChanged = z6;
            this.mIsConversation = z7;
            this.mShortcutInfo = shortcutInfo;
            this.mRankingAdjustment = i6;
            this.mIsBubble = z8;
            this.mProposedImportance = i7;
            this.mSensitiveContent = z9;
            this.mSummarization = TextUtils.nullIfEmpty(str3);
        }

        public void populate(String str, int i, boolean z, int i2, int i3, int i4, CharSequence charSequence, String str2, NotificationChannel notificationChannel, ArrayList<String> arrayList, ArrayList<SnoozeCriterion> arrayList2, boolean z2, int i5, boolean z3, long j, boolean z4, ArrayList<Notification.Action> arrayList3, ArrayList<CharSequence> arrayList4, boolean z5, boolean z6, boolean z7, ShortcutInfo shortcutInfo, int i6, boolean z8, int i7, boolean z9, String str3, boolean z10) {
            this.mKey = str;
            this.mRank = i;
            this.mIsAmbient = i4 < 2;
            this.mMatchesInterruptionFilter = z;
            this.mVisibilityOverride = i2;
            this.mSuppressedVisualEffects = i3;
            this.mImportance = i4;
            this.mImportanceExplanation = charSequence;
            this.mOverrideGroupKey = str2;
            this.mChannel = notificationChannel;
            this.mOverridePeople = arrayList;
            this.mSnoozeCriteria = arrayList2;
            this.mShowBadge = z2;
            this.mUserSentiment = i5;
            this.mHidden = z3;
            this.mLastAudiblyAlertedMs = j;
            this.mNoisy = z4;
            this.mSmartActions = arrayList3;
            this.mSmartReplies = arrayList4;
            this.mCanBubble = z5;
            this.mIsTextChanged = z6;
            this.mIsConversation = z7;
            this.mShortcutInfo = shortcutInfo;
            this.mRankingAdjustment = i6;
            this.mIsBubble = z8;
            this.mProposedImportance = i7;
            this.mSensitiveContent = z9;
            this.mSummarization = TextUtils.nullIfEmpty(str3);
            this.mIsOngoingActivityTurnedOn = z10;
        }

        public Ranking withAudiblyAlertedInfo(Ranking ranking) {
            if (ranking != null) {
                long j = ranking.mLastAudiblyAlertedMs;
                if (j > 0 && this.mLastAudiblyAlertedMs <= 0) {
                    this.mLastAudiblyAlertedMs = j;
                }
            }
            return this;
        }

        public void populate(Ranking ranking) {
            populate(ranking.mKey, ranking.mRank, ranking.mMatchesInterruptionFilter, ranking.mVisibilityOverride, ranking.mSuppressedVisualEffects, ranking.mImportance, ranking.mImportanceExplanation, ranking.mOverrideGroupKey, ranking.mChannel, ranking.mOverridePeople, ranking.mSnoozeCriteria, ranking.mShowBadge, ranking.mUserSentiment, ranking.mHidden, ranking.mLastAudiblyAlertedMs, ranking.mNoisy, ranking.mSmartActions, ranking.mSmartReplies, ranking.mCanBubble, ranking.mIsTextChanged, ranking.mIsConversation, ranking.mShortcutInfo, ranking.mRankingAdjustment, ranking.mIsBubble, ranking.mProposedImportance, ranking.mSensitiveContent, ranking.mSummarization, ranking.mIsOngoingActivityTurnedOn);
        }

        public static String importanceToString(int i) {
            if (i == -1000) {
                return "UNSPECIFIED";
            }
            if (i == 0) {
                return KeyProperties.DIGEST_NONE;
            }
            if (i == 1) {
                return "MIN";
            }
            if (i == 2) {
                return "LOW";
            }
            if (i == 3) {
                return "DEFAULT";
            }
            if (i == 4 || i == 5) {
                return "HIGH";
            }
            return "UNKNOWN(" + String.valueOf(i) + NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Ranking ranking = (Ranking) obj;
                if (Objects.equals(this.mKey, ranking.mKey) && Objects.equals(Integer.valueOf(this.mRank), Integer.valueOf(ranking.mRank)) && Objects.equals(Boolean.valueOf(this.mMatchesInterruptionFilter), Boolean.valueOf(ranking.mMatchesInterruptionFilter)) && Objects.equals(Integer.valueOf(this.mVisibilityOverride), Integer.valueOf(ranking.mVisibilityOverride)) && Objects.equals(Integer.valueOf(this.mSuppressedVisualEffects), Integer.valueOf(ranking.mSuppressedVisualEffects)) && Objects.equals(Integer.valueOf(this.mImportance), Integer.valueOf(ranking.mImportance)) && Objects.equals(this.mImportanceExplanation, ranking.mImportanceExplanation) && Objects.equals(this.mOverrideGroupKey, ranking.mOverrideGroupKey) && Objects.equals(this.mChannel, ranking.mChannel) && Objects.equals(this.mOverridePeople, ranking.mOverridePeople) && Objects.equals(this.mSnoozeCriteria, ranking.mSnoozeCriteria) && Objects.equals(Boolean.valueOf(this.mShowBadge), Boolean.valueOf(ranking.mShowBadge)) && Objects.equals(Integer.valueOf(this.mUserSentiment), Integer.valueOf(ranking.mUserSentiment)) && Objects.equals(Boolean.valueOf(this.mHidden), Boolean.valueOf(ranking.mHidden)) && Objects.equals(Long.valueOf(this.mLastAudiblyAlertedMs), Long.valueOf(ranking.mLastAudiblyAlertedMs)) && Objects.equals(Boolean.valueOf(this.mNoisy), Boolean.valueOf(ranking.mNoisy))) {
                    ArrayList<Notification.Action> arrayList = this.mSmartActions;
                    int size = arrayList == null ? 0 : arrayList.size();
                    ArrayList<Notification.Action> arrayList2 = ranking.mSmartActions;
                    if (size == (arrayList2 == null ? 0 : arrayList2.size()) && Objects.equals(this.mSmartReplies, ranking.mSmartReplies) && Objects.equals(Boolean.valueOf(this.mCanBubble), Boolean.valueOf(ranking.mCanBubble)) && Objects.equals(Boolean.valueOf(this.mIsTextChanged), Boolean.valueOf(ranking.mIsTextChanged)) && Objects.equals(Boolean.valueOf(this.mIsConversation), Boolean.valueOf(ranking.mIsConversation))) {
                        ShortcutInfo shortcutInfo = this.mShortcutInfo;
                        Object id = shortcutInfo == null ? 0 : shortcutInfo.getId();
                        ShortcutInfo shortcutInfo2 = ranking.mShortcutInfo;
                        if (Objects.equals(id, shortcutInfo2 == null ? 0 : shortcutInfo2.getId()) && Objects.equals(Integer.valueOf(this.mRankingAdjustment), Integer.valueOf(ranking.mRankingAdjustment)) && Objects.equals(Boolean.valueOf(this.mIsBubble), Boolean.valueOf(ranking.mIsBubble)) && Objects.equals(Integer.valueOf(this.mProposedImportance), Integer.valueOf(ranking.mProposedImportance)) && Objects.equals(Boolean.valueOf(this.mSensitiveContent), Boolean.valueOf(ranking.mSensitiveContent)) && Objects.equals(this.mSummarization, ranking.mSummarization) && Objects.equals(Boolean.valueOf(this.mIsOngoingActivityTurnedOn), Boolean.valueOf(ranking.mIsOngoingActivityTurnedOn))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    public static class RankingMap implements Parcelable {
        public static final Parcelable.Creator<RankingMap> CREATOR = new Parcelable.Creator<RankingMap>() { // from class: android.service.notification.NotificationListenerService.RankingMap.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RankingMap createFromParcel(Parcel parcel) {
                return new RankingMap(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RankingMap[] newArray(int i) {
                return new RankingMap[i];
            }
        };
        private ArrayList<String> mOrderedKeys;
        private ArrayMap<String, Ranking> mRankings;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RankingMap(Ranking[] rankingArr) {
            this.mOrderedKeys = new ArrayList<>();
            this.mRankings = new ArrayMap<>();
            for (int i = 0; i < rankingArr.length; i++) {
                String key = rankingArr[i].getKey();
                this.mOrderedKeys.add(key);
                this.mRankings.put(key, rankingArr[i]);
            }
        }

        private RankingMap(Parcel parcel) {
            this.mOrderedKeys = new ArrayList<>();
            this.mRankings = new ArrayMap<>();
            int i = parcel.readInt();
            this.mOrderedKeys.ensureCapacity(i);
            this.mRankings.ensureCapacity(i);
            for (int i2 = 0; i2 < i; i2++) {
                Ranking ranking = new Ranking(parcel);
                String key = ranking.getKey();
                this.mOrderedKeys.add(key);
                this.mRankings.put(key, ranking);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                RankingMap rankingMap = (RankingMap) obj;
                if (this.mOrderedKeys.equals(rankingMap.mOrderedKeys) && this.mRankings.equals(rankingMap.mRankings)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int size = this.mOrderedKeys.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                this.mRankings.get(this.mOrderedKeys.get(i2)).writeToParcel(parcel, i);
            }
        }

        public String[] getOrderedKeys() {
            return (String[]) this.mOrderedKeys.toArray(new String[0]);
        }

        public boolean getRanking(String str, Ranking ranking) {
            if (!this.mRankings.containsKey(str)) {
                return false;
            }
            ranking.populate(this.mRankings.get(str));
            return true;
        }

        public Ranking getRawRankingObject(String str) {
            return this.mRankings.get(str);
        }
    }

    private final class MyHandler extends Handler {
        public static final int MSG_ON_INTERRUPTION_FILTER_CHANGED = 6;
        public static final int MSG_ON_LISTENER_CONNECTED = 3;
        public static final int MSG_ON_LISTENER_HINTS_CHANGED = 5;
        public static final int MSG_ON_NOTIFICATION_CHANNEL_GROUP_MODIFIED = 8;
        public static final int MSG_ON_NOTIFICATION_CHANNEL_MODIFIED = 7;
        public static final int MSG_ON_NOTIFICATION_POSTED = 1;
        public static final int MSG_ON_NOTIFICATION_RANKING_UPDATE = 4;
        public static final int MSG_ON_NOTIFICATION_REMOVED = 2;
        public static final int MSG_ON_STATUS_BAR_ICON_BEHAVIOR_CHANGED = 9;

        public MyHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (NotificationListenerService.this.isConnected) {
                switch (message.what) {
                    case 1:
                        SomeArgs someArgs = (SomeArgs) message.obj;
                        StatusBarNotification statusBarNotification = (StatusBarNotification) someArgs.arg1;
                        RankingMap rankingMap = (RankingMap) someArgs.arg2;
                        someArgs.recycle();
                        NotificationListenerService.this.onNotificationPosted(statusBarNotification, rankingMap);
                        break;
                    case 2:
                        SomeArgs someArgs2 = (SomeArgs) message.obj;
                        StatusBarNotification statusBarNotification2 = (StatusBarNotification) someArgs2.arg1;
                        RankingMap rankingMap2 = (RankingMap) someArgs2.arg2;
                        int iIntValue = ((Integer) someArgs2.arg3).intValue();
                        NotificationStats notificationStats = (NotificationStats) someArgs2.arg4;
                        someArgs2.recycle();
                        NotificationListenerService.this.onNotificationRemoved(statusBarNotification2, rankingMap2, notificationStats, iIntValue);
                        break;
                    case 3:
                        NotificationListenerService.this.onListenerConnected();
                        break;
                    case 4:
                        NotificationListenerService.this.onNotificationRankingUpdate((RankingMap) message.obj);
                        break;
                    case 5:
                        NotificationListenerService.this.onListenerHintsChanged(message.arg1);
                        break;
                    case 6:
                        NotificationListenerService.this.onInterruptionFilterChanged(message.arg1);
                        break;
                    case 7:
                        SomeArgs someArgs3 = (SomeArgs) message.obj;
                        String str = (String) someArgs3.arg1;
                        UserHandle userHandle = (UserHandle) someArgs3.arg2;
                        NotificationChannel notificationChannel = (NotificationChannel) someArgs3.arg3;
                        int iIntValue2 = ((Integer) someArgs3.arg4).intValue();
                        someArgs3.recycle();
                        NotificationListenerService.this.onNotificationChannelModified(str, userHandle, notificationChannel, iIntValue2);
                        break;
                    case 8:
                        SomeArgs someArgs4 = (SomeArgs) message.obj;
                        String str2 = (String) someArgs4.arg1;
                        UserHandle userHandle2 = (UserHandle) someArgs4.arg2;
                        NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) someArgs4.arg3;
                        int iIntValue3 = ((Integer) someArgs4.arg4).intValue();
                        someArgs4.recycle();
                        NotificationListenerService.this.onNotificationChannelGroupModified(str2, userHandle2, notificationChannelGroup, iIntValue3);
                        break;
                    case 9:
                        NotificationListenerService.this.onSilentStatusBarIconsVisibilityChanged(((Boolean) message.obj).booleanValue());
                        break;
                }
            }
        }
    }
}
