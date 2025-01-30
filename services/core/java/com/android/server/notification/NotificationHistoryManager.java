package com.android.server.notification;

import android.app.NotificationHistory;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.util.FunctionalUtils;
import com.android.server.IoThread;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class NotificationHistoryManager {
    public static final boolean DEBUG = NotificationManagerService.DBG;
    static final String DIRECTORY_PER_USER = "notification_history";
    public final Context mContext;
    final SettingsObserver mSettingsObserver;
    public final UserManager mUserManager;
    public final Object mLock = new Object();
    public final SparseArray mUserState = new SparseArray();
    public final SparseBooleanArray mUserUnlockedStates = new SparseBooleanArray();
    public final SparseArray mUserPendingPackageRemovals = new SparseArray();
    public final SparseBooleanArray mHistoryEnabled = new SparseBooleanArray();
    public final SparseBooleanArray mUserPendingHistoryDisables = new SparseBooleanArray();

    public NotificationHistoryManager(Context context, Handler handler) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mSettingsObserver = new SettingsObserver(handler);
    }

    public void onDestroy() {
        this.mSettingsObserver.stopObserving();
    }

    public void onBootPhaseAppsCanStart() {
        try {
            NotificationHistoryJobService.scheduleJob(this.mContext);
        } catch (Throwable th) {
            Slog.e("NotificationHistory", "Failed to schedule cleanup job", th);
        }
        this.mSettingsObserver.observe();
    }

    public void onUserUnlocked(int i) {
        synchronized (this.mLock) {
            this.mUserUnlockedStates.put(i, true);
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.i("NotificationHistory", "Attempted to unlock gone/disabled user " + i);
                return;
            }
            List list = (List) this.mUserPendingPackageRemovals.get(i);
            if (list != null) {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    userHistoryAndInitializeIfNeededLocked.onPackageRemoved((String) list.get(i2));
                }
                this.mUserPendingPackageRemovals.put(i, null);
            }
            if (this.mUserPendingHistoryDisables.get(i)) {
                disableHistory(userHistoryAndInitializeIfNeededLocked, i);
            }
        }
    }

    public void onUserStopped(int i) {
        synchronized (this.mLock) {
            this.mUserUnlockedStates.put(i, false);
            this.mUserState.put(i, null);
        }
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUserPendingPackageRemovals.put(i, null);
            this.mHistoryEnabled.put(i, false);
            this.mUserPendingHistoryDisables.put(i, false);
            onUserStopped(i);
        }
    }

    public void onPackageRemoved(int i, String str) {
        synchronized (this.mLock) {
            if (!this.mUserUnlockedStates.get(i, false)) {
                if (this.mHistoryEnabled.get(i, false)) {
                    List list = (List) this.mUserPendingPackageRemovals.get(i, new ArrayList());
                    list.add(str);
                    this.mUserPendingPackageRemovals.put(i, list);
                }
                return;
            }
            NotificationHistoryDatabase notificationHistoryDatabase = (NotificationHistoryDatabase) this.mUserState.get(i);
            if (notificationHistoryDatabase == null) {
                return;
            }
            notificationHistoryDatabase.onPackageRemoved(str);
        }
    }

    public void cleanupHistoryFiles() {
        NotificationHistoryDatabase notificationHistoryDatabase;
        synchronized (this.mLock) {
            int size = this.mUserUnlockedStates.size();
            for (int i = 0; i < size; i++) {
                if (this.mUserUnlockedStates.valueAt(i) && (notificationHistoryDatabase = (NotificationHistoryDatabase) this.mUserState.get(this.mUserUnlockedStates.keyAt(i))) != null) {
                    notificationHistoryDatabase.prune();
                }
            }
        }
    }

    public void deleteNotificationHistoryItem(String str, int i, long j) {
        synchronized (this.mLock) {
            int userId = UserHandle.getUserId(i);
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(userId);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.w("NotificationHistory", "Attempted to remove notif for locked/gone/disabled user " + userId);
                return;
            }
            userHistoryAndInitializeIfNeededLocked.deleteNotificationHistoryItem(str, j);
        }
    }

    public void deleteConversations(String str, int i, Set set) {
        synchronized (this.mLock) {
            int userId = UserHandle.getUserId(i);
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(userId);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.w("NotificationHistory", "Attempted to remove conversation for locked/gone/disabled user " + userId);
                return;
            }
            userHistoryAndInitializeIfNeededLocked.deleteConversations(str, set);
        }
    }

    public void deleteNotificationChannel(String str, int i, String str2) {
        synchronized (this.mLock) {
            int userId = UserHandle.getUserId(i);
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(userId);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.w("NotificationHistory", "Attempted to remove channel for locked/gone/disabled user " + userId);
                return;
            }
            userHistoryAndInitializeIfNeededLocked.deleteNotificationChannel(str, str2);
        }
    }

    public void triggerWriteToDisk() {
        NotificationHistoryDatabase notificationHistoryDatabase;
        synchronized (this.mLock) {
            int size = this.mUserState.size();
            for (int i = 0; i < size; i++) {
                int keyAt = this.mUserState.keyAt(i);
                if (this.mUserUnlockedStates.get(keyAt) && (notificationHistoryDatabase = (NotificationHistoryDatabase) this.mUserState.get(keyAt)) != null) {
                    notificationHistoryDatabase.forceWriteToDisk();
                }
            }
        }
    }

    public void addNotification(final NotificationHistory.HistoricalNotification historicalNotification) {
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationHistoryManager$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                NotificationHistoryManager.this.lambda$addNotification$0(historicalNotification);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addNotification$0(NotificationHistory.HistoricalNotification historicalNotification) {
        synchronized (this.mLock) {
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(historicalNotification.getUserId());
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.w("NotificationHistory", "Attempted to add notif for locked/gone/disabled user " + historicalNotification.getUserId());
                return;
            }
            userHistoryAndInitializeIfNeededLocked.addNotification(historicalNotification);
        }
    }

    public NotificationHistory readNotificationHistory(int[] iArr) {
        synchronized (this.mLock) {
            NotificationHistory notificationHistory = new NotificationHistory();
            if (iArr == null) {
                return notificationHistory;
            }
            for (int i : iArr) {
                NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    Slog.i("NotificationHistory", "Attempted to read history for locked/gone/disabled user " + i);
                } else {
                    notificationHistory.addNotificationsToWrite(userHistoryAndInitializeIfNeededLocked.readNotificationHistory());
                }
            }
            return notificationHistory;
        }
    }

    public void updateNotificationItems(int i, int i2, NotificationHistory.HistoricalNotification historicalNotification) {
        synchronized (this.mLock) {
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i2);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.i("NotificationHistory", "Attempted to update history for locked/gone/disabled user " + i2);
                return;
            }
            userHistoryAndInitializeIfNeededLocked.updateNotificationItems(i, historicalNotification);
        }
    }

    public void updateCancelEvent(int i, String str, boolean z) {
        synchronized (this.mLock) {
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.i("NotificationHistory", "Attempted to update history for locked/gone/disabled user " + i);
                return;
            }
            userHistoryAndInitializeIfNeededLocked.updateCancelEvent(str, z);
        }
    }

    public NotificationHistory readFilteredNotificationHistoryForPackage(int i, String str, String str2, int i2) {
        synchronized (this.mLock) {
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.i("NotificationHistory", "Attempted to read history for locked/gone/disabled user " + i);
                return new NotificationHistory();
            }
            NotificationHistory readNotificationHistoryForPackage = userHistoryAndInitializeIfNeededLocked.readNotificationHistoryForPackage(str, str2, i2);
            for (NotificationHistory.HistoricalNotification historicalNotification : readNotificationHistoryForPackage.getNotificationsToWrite()) {
                if (historicalNotification.getIcon() == null) {
                    Slog.e("NotificationHistory", "check notification to delete : " + historicalNotification);
                    userHistoryAndInitializeIfNeededLocked.deleteNotificationHistoryItem(historicalNotification.getPackage(), historicalNotification.getPostedTimeMs());
                }
            }
            return readNotificationHistoryForPackage;
        }
    }

    public NotificationHistory readFilteredNotificationHistoryForDump(int i, String str, int i2) {
        synchronized (this.mLock) {
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                Slog.i("NotificationHistory", "Attempted to read history(for dump) for locked/gone/disabled user " + i);
                return new NotificationHistory();
            }
            return userHistoryAndInitializeIfNeededLocked.readNotificationHistoryForDump(str, i2);
        }
    }

    public void onUserAdded(int i) {
        synchronized (this.mLock) {
            this.mUserUnlockedStates.put(i, true);
            this.mHistoryEnabled.put(i, true);
        }
    }

    public void onHistoryEnabledChanged(int i, boolean z) {
        synchronized (this.mLock) {
            if (z) {
                this.mHistoryEnabled.put(i, z);
            }
            NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked = getUserHistoryAndInitializeIfNeededLocked(i);
            if (userHistoryAndInitializeIfNeededLocked == null) {
                this.mUserPendingHistoryDisables.put(i, !z);
            } else if (!z) {
                disableHistory(userHistoryAndInitializeIfNeededLocked, i);
            }
        }
    }

    public final void disableHistory(NotificationHistoryDatabase notificationHistoryDatabase, int i) {
        notificationHistoryDatabase.disableHistory(this.mContext);
        this.mUserPendingHistoryDisables.put(i, false);
        this.mHistoryEnabled.put(i, false);
        this.mUserState.put(i, null);
    }

    public final NotificationHistoryDatabase getUserHistoryAndInitializeIfNeededLocked(int i) {
        if (!this.mHistoryEnabled.get(i)) {
            if (DEBUG) {
                Slog.i("NotificationHistory", "History disabled for user " + i);
            }
            this.mUserState.put(i, null);
            return null;
        }
        NotificationHistoryDatabase notificationHistoryDatabase = (NotificationHistoryDatabase) this.mUserState.get(i);
        if (notificationHistoryDatabase == null) {
            notificationHistoryDatabase = NotificationHistoryDatabaseFactory.create(this.mContext, IoThread.getHandler(), new File(Environment.getDataSystemCeDirectory(i), DIRECTORY_PER_USER));
            if (this.mUserUnlockedStates.get(i)) {
                try {
                    notificationHistoryDatabase.init();
                    this.mUserState.put(i, notificationHistoryDatabase);
                } catch (Exception e) {
                    if (this.mUserManager.isUserUnlocked(i)) {
                        throw e;
                    }
                    Slog.w("NotificationHistory", "Attempted to initialize service for stopped or removed user " + i);
                    return null;
                }
            } else {
                Slog.w("NotificationHistory", "Attempted to initialize service for stopped or removed user " + i);
                return null;
            }
        }
        return notificationHistoryDatabase;
    }

    public boolean isUserUnlocked(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserUnlockedStates.get(i);
        }
        return z;
    }

    public boolean doesHistoryExistForUser(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserState.get(i) != null;
        }
        return z;
    }

    public void replaceNotificationHistoryDatabase(int i, NotificationHistoryDatabase notificationHistoryDatabase) {
        synchronized (this.mLock) {
            if (this.mUserState.get(i) != null) {
                this.mUserState.put(i, notificationHistoryDatabase);
            }
        }
    }

    public List getPendingPackageRemovalsForUser(int i) {
        List list;
        synchronized (this.mLock) {
            list = (List) this.mUserPendingPackageRemovals.get(i);
        }
        return list;
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri NOTIFICATION_HISTORY_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.NOTIFICATION_HISTORY_URI = Settings.Secure.getUriFor("notification_history_enabled");
        }

        public void observe() {
            NotificationHistoryManager.this.mContext.getContentResolver().registerContentObserver(this.NOTIFICATION_HISTORY_URI, false, this, -1);
            synchronized (NotificationHistoryManager.this.mLock) {
                for (UserInfo userInfo : NotificationHistoryManager.this.mUserManager.getUsers()) {
                    if (!userInfo.isProfile()) {
                        update(null, userInfo.id);
                    }
                }
            }
        }

        public void stopObserving() {
            NotificationHistoryManager.this.mContext.getContentResolver().unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            update(uri, i);
        }

        public void update(Uri uri, int i) {
            ContentResolver contentResolver = NotificationHistoryManager.this.mContext.getContentResolver();
            if (uri == null || this.NOTIFICATION_HISTORY_URI.equals(uri)) {
                int intForUser = Settings.Secure.getIntForUser(contentResolver, "notification_history_enabled", 1, i);
                boolean z = intForUser != 0;
                for (int i2 : NotificationHistoryManager.this.mUserManager.getProfileIds(i, true)) {
                    NotificationHistoryManager.this.onHistoryEnabledChanged(i2, z);
                }
            }
        }
    }
}
