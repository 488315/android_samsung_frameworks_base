package com.android.server.notification;

import android.app.NotificationHistory;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Environment;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.notification.NotificationHistoryDatabase.RemoveImageRunnable;
import com.android.server.notification.NotificationHistoryDatabase.RemovePackageRunnable;

import com.samsung.android.server.notification.NotificationHistoryImageProvider;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationHistoryManager {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri NOTIFICATION_HISTORY_URI;

        public SettingsObserver(NotificationManagerService.WorkerHandler workerHandler) {
            super(workerHandler);
            this.NOTIFICATION_HISTORY_URI =
                    Settings.Secure.getUriFor("notification_history_enabled");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            update(i, uri);
        }

        public final void update(int i, Uri uri) {
            ContentResolver contentResolver =
                    NotificationHistoryManager.this.mContext.getContentResolver();
            if (uri == null || this.NOTIFICATION_HISTORY_URI.equals(uri)) {
                boolean z =
                        Settings.Secure.getIntForUser(
                                        contentResolver, "notification_history_enabled", 1, i)
                                != 0;
                NotificationHistoryManager notificationHistoryManager =
                        NotificationHistoryManager.this;
                synchronized (notificationHistoryManager.mLock) {
                    if (z) {
                        try {
                            notificationHistoryManager.mHistoryEnabled.put(i, z);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked =
                            notificationHistoryManager.getUserHistoryAndInitializeIfNeededLocked(i);
                    if (userHistoryAndInitializeIfNeededLocked == null) {
                        notificationHistoryManager.mUserPendingHistoryDisables.put(i, !z);
                    } else if (!z) {
                        notificationHistoryManager.disableHistory(
                                i, userHistoryAndInitializeIfNeededLocked);
                    }
                }
            }
        }
    }

    public NotificationHistoryManager(
            Context context, NotificationManagerService.WorkerHandler workerHandler) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mSettingsObserver = new SettingsObserver(workerHandler);
    }

    public final void disableHistory(
            int i, NotificationHistoryDatabase notificationHistoryDatabase) {
        Context context = this.mContext;
        synchronized (notificationHistoryDatabase.mLock) {
            try {
                Iterator it = notificationHistoryDatabase.mHistoryFiles.iterator();
                while (it.hasNext()) {
                    ((AtomicFile) it.next()).delete();
                }
                notificationHistoryDatabase.mHistoryDir.delete();
                notificationHistoryDatabase.mHistoryFiles.clear();
                context.getContentResolver()
                        .delete(NotificationHistoryImageProvider.CONTENT_URI, null, null);
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mUserPendingHistoryDisables.put(i, false);
        this.mHistoryEnabled.put(i, false);
        this.mUserState.put(i, null);
    }

    public boolean doesHistoryExistForUser(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserState.get(i) != null;
        }
        return z;
    }

    public List getPendingPackageRemovalsForUser(int i) {
        List list;
        synchronized (this.mLock) {
            list = (List) this.mUserPendingPackageRemovals.get(i);
        }
        return list;
    }

    public final NotificationHistoryDatabase getUserHistoryAndInitializeIfNeededLocked(int i) {
        if (!this.mHistoryEnabled.get(i)) {
            if (DEBUG) {
                HermesService$3$$ExternalSyntheticOutline0.m(
                        i, "History disabled for user ", "NotificationHistory");
            }
            this.mUserState.put(i, null);
            return null;
        }
        NotificationHistoryDatabase notificationHistoryDatabase =
                (NotificationHistoryDatabase) this.mUserState.get(i);
        if (notificationHistoryDatabase != null) {
            return notificationHistoryDatabase;
        }
        NotificationHistoryDatabase notificationHistoryDatabase2 =
                new NotificationHistoryDatabase(
                        IoThread.getHandler(),
                        new File(Environment.getDataSystemCeDirectory(i), DIRECTORY_PER_USER));
        if (!this.mUserUnlockedStates.get(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i,
                    "Attempted to initialize service for stopped or removed user ",
                    "NotificationHistory");
            return null;
        }
        try {
            notificationHistoryDatabase2.init();
            this.mUserState.put(i, notificationHistoryDatabase2);
            return notificationHistoryDatabase2;
        } catch (Exception e) {
            if (this.mUserManager.isUserUnlocked(i)) {
                throw e;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i,
                    "Attempted to initialize service for stopped or removed user ",
                    "NotificationHistory");
            return null;
        }
    }

    public boolean isUserUnlocked(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserUnlockedStates.get(i);
        }
        return z;
    }

    public void onDestroy() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        NotificationHistoryManager.this
                .mContext
                .getContentResolver()
                .unregisterContentObserver(settingsObserver);
    }

    public final void onUserStopped(int i) {
        synchronized (this.mLock) {
            this.mUserUnlockedStates.put(i, false);
            this.mUserState.put(i, null);
        }
    }

    public final void onUserUnlocked(int i) {
        synchronized (this.mLock) {
            try {
                this.mUserUnlockedStates.put(i, true);
                NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked =
                        getUserHistoryAndInitializeIfNeededLocked(i);
                if (userHistoryAndInitializeIfNeededLocked == null) {
                    Slog.i("NotificationHistory", "Attempted to unlock gone/disabled user " + i);
                    return;
                }
                List list = (List) this.mUserPendingPackageRemovals.get(i);
                if (list != null) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        userHistoryAndInitializeIfNeededLocked.mFileWriteHandler.post(
                                userHistoryAndInitializeIfNeededLocked
                                .new RemovePackageRunnable((String) list.get(i2)));
                    }
                    this.mUserPendingPackageRemovals.remove(i);
                }
                if (this.mUserPendingHistoryDisables.get(i)) {
                    disableHistory(i, userHistoryAndInitializeIfNeededLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0045 A[Catch: all -> 0x0023, TryCatch #0 {all -> 0x0023, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0021, B:11:0x0027, B:14:0x002e, B:15:0x0037, B:16:0x003f, B:18:0x0045, B:21:0x0051, B:26:0x007a, B:28:0x0033), top: B:3:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.NotificationHistory readFilteredNotificationHistoryForPackage(
            int r5, int r6, java.lang.String r7, java.lang.String r8) {
        /*
            r4 = this;
            java.lang.String r0 = "Attempted to read history for locked/gone/disabled user "
            java.lang.Object r1 = r4.mLock
            monitor-enter(r1)
            com.android.server.notification.NotificationHistoryDatabase r4 = r4.getUserHistoryAndInitializeIfNeededLocked(r5)     // Catch: java.lang.Throwable -> L23
            if (r4 != 0) goto L25
            java.lang.String r4 = "NotificationHistory"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L23
            r6.append(r5)     // Catch: java.lang.Throwable -> L23
            java.lang.String r5 = r6.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Slog.i(r4, r5)     // Catch: java.lang.Throwable -> L23
            android.app.NotificationHistory r4 = new android.app.NotificationHistory     // Catch: java.lang.Throwable -> L23
            r4.<init>()     // Catch: java.lang.Throwable -> L23
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L23
            return r4
        L23:
            r4 = move-exception
            goto L7c
        L25:
            if (r8 == 0) goto L33
            boolean r5 = r8.isEmpty()     // Catch: java.lang.Throwable -> L23
            if (r5 == 0) goto L2e
            goto L33
        L2e:
            android.app.NotificationHistory r5 = r4.readNotificationHistoryForPackage(r6, r7, r8)     // Catch: java.lang.Throwable -> L23
            goto L37
        L33:
            android.app.NotificationHistory r5 = r4.readNotificationHistoryWithNullKeyHandling(r6, r7)     // Catch: java.lang.Throwable -> L23
        L37:
            java.util.List r6 = r5.getNotificationsToWrite()     // Catch: java.lang.Throwable -> L23
            java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Throwable -> L23
        L3f:
            boolean r7 = r6.hasNext()     // Catch: java.lang.Throwable -> L23
            if (r7 == 0) goto L7a
            java.lang.Object r7 = r6.next()     // Catch: java.lang.Throwable -> L23
            android.app.NotificationHistory$HistoricalNotification r7 = (android.app.NotificationHistory.HistoricalNotification) r7     // Catch: java.lang.Throwable -> L23
            android.graphics.drawable.Icon r8 = r7.getIcon()     // Catch: java.lang.Throwable -> L23
            if (r8 != 0) goto L3f
            java.lang.String r8 = "NotificationHistory"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L23
            r0.<init>()     // Catch: java.lang.Throwable -> L23
            java.lang.String r2 = "check notification to delete : "
            r0.append(r2)     // Catch: java.lang.Throwable -> L23
            r0.append(r7)     // Catch: java.lang.Throwable -> L23
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L23
            android.util.Slog.e(r8, r0)     // Catch: java.lang.Throwable -> L23
            java.lang.String r8 = r7.getPackage()     // Catch: java.lang.Throwable -> L23
            long r2 = r7.getPostedTimeMs()     // Catch: java.lang.Throwable -> L23
            com.android.server.notification.NotificationHistoryDatabase$RemoveNotificationRunnable r7 = new com.android.server.notification.NotificationHistoryDatabase$RemoveNotificationRunnable     // Catch: java.lang.Throwable -> L23
            r7.<init>(r8, r2)     // Catch: java.lang.Throwable -> L23
            android.os.Handler r8 = r4.mFileWriteHandler     // Catch: java.lang.Throwable -> L23
            r8.post(r7)     // Catch: java.lang.Throwable -> L23
            goto L3f
        L7a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L23
            return r5
        L7c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L23
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.NotificationHistoryManager.readFilteredNotificationHistoryForPackage(int,"
                    + " int, java.lang.String, java.lang.String):android.app.NotificationHistory");
    }

    public final NotificationHistory readNotificationHistory(int[] iArr) {
        synchronized (this.mLock) {
            try {
                NotificationHistory notificationHistory = new NotificationHistory();
                if (iArr == null) {
                    return notificationHistory;
                }
                for (int i : iArr) {
                    NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked =
                            getUserHistoryAndInitializeIfNeededLocked(i);
                    if (userHistoryAndInitializeIfNeededLocked == null) {
                        Slog.i(
                                "NotificationHistory",
                                "Attempted to read history for locked/gone/disabled user " + i);
                    } else {
                        notificationHistory.addNotificationsToWrite(
                                userHistoryAndInitializeIfNeededLocked.readNotificationHistory());
                    }
                }
                return notificationHistory;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void replaceNotificationHistoryDatabase(
            int i, NotificationHistoryDatabase notificationHistoryDatabase) {
        synchronized (this.mLock) {
            try {
                if (this.mUserState.get(i) != null) {
                    this.mUserState.put(i, notificationHistoryDatabase);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateCancelEvent(int i, String str, boolean z) {
        synchronized (this.mLock) {
            try {
                NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked =
                        getUserHistoryAndInitializeIfNeededLocked(i);
                if (userHistoryAndInitializeIfNeededLocked != null) {
                    userHistoryAndInitializeIfNeededLocked.updateCancelEvent(str, z);
                    return;
                }
                Slog.i(
                        "NotificationHistory",
                        "Attempted to update history for locked/gone/disabled user " + i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateNotificationItems(
            int i, NotificationHistory.HistoricalNotification historicalNotification) {
        synchronized (this.mLock) {
            try {
                NotificationHistoryDatabase userHistoryAndInitializeIfNeededLocked =
                        getUserHistoryAndInitializeIfNeededLocked(i);
                if (userHistoryAndInitializeIfNeededLocked != null) {
                    userHistoryAndInitializeIfNeededLocked.mFileWriteHandler.post(
                            userHistoryAndInitializeIfNeededLocked
                            .new RemoveImageRunnable(
                                    historicalNotification.getSbnKey(),
                                    historicalNotification.getText(),
                                    historicalNotification.getUri()));
                } else {
                    Slog.i(
                            "NotificationHistory",
                            "Attempted to update history for locked/gone/disabled user " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
