package com.android.server;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Log;
import android.view.ISensitiveContentProtectionManager;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.wm.SensitiveContentPackages;
import com.android.server.wm.WindowManagerInternal;

import java.util.Objects;
import java.util.Set;

public final class SensitiveContentProtectionManagerService extends SystemService {
    public ArraySet mExemptedPackages;
    public MediaProjectionSession mMediaProjectionSession;
    NotificationListener mNotificationListener;
    public final SensitiveContentProtectionManagerService$$ExternalSyntheticLambda0
            mOnWindowRemovedListener;
    public PackageManagerInternal mPackageManagerInternal;
    public final ArraySet mPackagesShowingSensitiveContent;
    public boolean mProjectionActive;
    public final AnonymousClass1 mProjectionCallback;
    public MediaProjectionManager mProjectionManager;
    public final Object mSensitiveContentProtectionLock;
    public WindowManagerInternal mWindowManager;

    public final class MediaProjectionSession {
        public final boolean mIsExempted;
        public final long mSessionId;
        public final int mUid;
        public final ArraySet mAllSeenNotificationKeys = new ArraySet();
        public final ArraySet mSeenOtpNotificationKeys = new ArraySet();

        public MediaProjectionSession(int i, long j, boolean z) {
            this.mUid = i;
            this.mIsExempted = z;
            this.mSessionId = j;
        }
    }

    class NotificationListener extends NotificationListenerService {
        public NotificationListener() {}

        @Override // android.service.notification.NotificationListenerService
        public final void onListenerConnected() {
            super.onListenerConnected();
            Trace.traceBegin(
                    524288L, "SensitiveContentProtectionManagerService.onListenerConnected");
            try {
                synchronized (
                        SensitiveContentProtectionManagerService.this
                                .mSensitiveContentProtectionLock) {
                    SensitiveContentProtectionManagerService
                            sensitiveContentProtectionManagerService =
                                    SensitiveContentProtectionManagerService.this;
                    if (sensitiveContentProtectionManagerService.mProjectionActive) {
                        sensitiveContentProtectionManagerService
                                .updateAppsThatShouldBlockScreenCapture();
                    }
                }
            } finally {
                Trace.traceEnd(524288L);
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationPosted(
                StatusBarNotification statusBarNotification,
                NotificationListenerService.RankingMap rankingMap) {
            MediaProjectionSession mediaProjectionSession;
            super.onNotificationPosted(statusBarNotification, rankingMap);
            Trace.traceBegin(
                    524288L, "SensitiveContentProtectionManagerService.onNotificationPosted");
            try {
                if (statusBarNotification == null) {
                    Log.w("SensitiveContentProtect", "Unable to parse null notification");
                    return;
                }
                if (rankingMap == null) {
                    Log.w("SensitiveContentProtect", "Ranking map not initialized.");
                    return;
                }
                synchronized (
                        SensitiveContentProtectionManagerService.this
                                .mSensitiveContentProtectionLock) {
                    if (SensitiveContentProtectionManagerService.this.mProjectionActive) {
                        NotificationListenerService.Ranking rawRankingObject =
                                rankingMap.getRawRankingObject(statusBarNotification.getKey());
                        SensitiveContentPackages.PackageInfo packageInfo =
                                rawRankingObject != null && rawRankingObject.hasSensitiveContent()
                                        ? new SensitiveContentPackages.PackageInfo(
                                                statusBarNotification.getUid(),
                                                null,
                                                statusBarNotification.getPackageName())
                                        : null;
                        if (packageInfo != null) {
                            SensitiveContentProtectionManagerService.this.mWindowManager
                                    .addBlockScreenCaptureForApps(
                                            new ArraySet(Set.of(packageInfo)));
                        }
                        if (Flags.sensitiveContentImprovements()
                                && (mediaProjectionSession =
                                                SensitiveContentProtectionManagerService.this
                                                        .mMediaProjectionSession)
                                        != null) {
                            if (packageInfo != null) {
                                String key = statusBarNotification.getKey();
                                mediaProjectionSession.mAllSeenNotificationKeys.add(key);
                                mediaProjectionSession.mSeenOtpNotificationKeys.add(key);
                            } else {
                                mediaProjectionSession.mAllSeenNotificationKeys.add(
                                        statusBarNotification.getKey());
                            }
                        }
                    }
                }
            } finally {
                Trace.traceEnd(524288L);
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationRankingUpdate(
                NotificationListenerService.RankingMap rankingMap) {
            super.onNotificationRankingUpdate(rankingMap);
            Trace.traceBegin(
                    524288L,
                    "SensitiveContentProtectionManagerService.onNotificationRankingUpdate");
            try {
                if (rankingMap == null) {
                    Log.w("SensitiveContentProtect", "Ranking map not initialized.");
                    return;
                }
                synchronized (
                        SensitiveContentProtectionManagerService.this
                                .mSensitiveContentProtectionLock) {
                    SensitiveContentProtectionManagerService
                            sensitiveContentProtectionManagerService =
                                    SensitiveContentProtectionManagerService.this;
                    if (sensitiveContentProtectionManagerService.mProjectionActive) {
                        sensitiveContentProtectionManagerService
                                .updateAppsThatShouldBlockScreenCapture(rankingMap);
                    }
                }
            } finally {
                Trace.traceEnd(524288L);
            }
        }
    }

    public final class SensitiveContentProtectionManagerServiceBinder
            extends ISensitiveContentProtectionManager.Stub {
        public SensitiveContentProtectionManagerServiceBinder() {}

        public final void setSensitiveContentProtection(IBinder iBinder, String str, boolean z) {
            Trace.traceBegin(
                    524288L,
                    "SensitiveContentProtectionManagerService.setSensitiveContentProtection");
            try {
                int callingUid = Binder.getCallingUid();
                if (SensitiveContentProtectionManagerService.this.mPackageManagerInternal
                                .getPackageUid(str, 0L, UserHandle.getUserId(callingUid))
                        != callingUid) {
                    throw new SecurityException(
                            SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                                    .m(
                                            callingUid,
                                            "Specified calling package [",
                                            str,
                                            "] does not match the calling uid "));
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (z) {
                    try {
                        if (SensitiveContentProtectionManagerService.this.mWindowManager
                                        .getWindowName(iBinder)
                                == null) {
                            Log.e(
                                    "SensitiveContentProtect",
                                    "window token is not know to WMS, can't apply protection,"
                                        + " token: "
                                            + iBinder
                                            + ", package: "
                                            + str);
                            return;
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                SensitiveContentProtectionManagerService.this.setSensitiveContentProtection(
                        iBinder, str, callingUid, z);
            } finally {
                Trace.traceEnd(524288L);
            }
        }
    }

    /* renamed from: -$$Nest$monProjectionStart, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m85$$Nest$monProjectionStart(
            com.android.server.SensitiveContentProtectionManagerService r18,
            android.media.projection.MediaProjectionInfo r19) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.SensitiveContentProtectionManagerService.m85$$Nest$monProjectionStart(com.android.server.SensitiveContentProtectionManagerService,"
                    + " android.media.projection.MediaProjectionInfo):void");
    }

    public SensitiveContentProtectionManagerService(Context context) {
        super(context);
        this.mExemptedPackages = null;
        this.mSensitiveContentProtectionLock = new Object();
        this.mPackagesShowingSensitiveContent = new ArraySet();
        this.mProjectionActive = false;
        this.mProjectionCallback =
                new MediaProjectionManager
                        .Callback() { // from class:
                                      // com.android.server.SensitiveContentProtectionManagerService.1
                    public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
                        Trace.traceBegin(
                                524288L,
                                "SensitiveContentProtectionManagerService.onProjectionStart");
                        try {
                            SensitiveContentProtectionManagerService.m85$$Nest$monProjectionStart(
                                    SensitiveContentProtectionManagerService.this,
                                    mediaProjectionInfo);
                        } finally {
                            Trace.traceEnd(524288L);
                        }
                    }

                    public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
                        Trace.traceBegin(
                                524288L,
                                "SensitiveContentProtectionManagerService.onProjectionStop");
                        try {
                            SensitiveContentProtectionManagerService.this.onProjectionEnd();
                        } finally {
                            Trace.traceEnd(524288L);
                        }
                    }
                };
        this.mOnWindowRemovedListener =
                new SensitiveContentProtectionManagerService$$ExternalSyntheticLambda0(this);
        if (Flags.sensitiveNotificationAppProtection()) {
            this.mNotificationListener = new NotificationListener();
        }
    }

    public void init(
            MediaProjectionManager mediaProjectionManager,
            WindowManagerInternal windowManagerInternal,
            PackageManagerInternal packageManagerInternal,
            ArraySet arraySet) {
        Objects.requireNonNull(mediaProjectionManager);
        Objects.requireNonNull(windowManagerInternal);
        this.mProjectionManager = mediaProjectionManager;
        this.mWindowManager = windowManagerInternal;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mExemptedPackages = arraySet;
        mediaProjectionManager.addCallback(
                this.mProjectionCallback, getContext().getMainThreadHandler());
        if (Flags.sensitiveNotificationAppProtection()) {
            try {
                this.mNotificationListener.registerAsSystemService(
                        getContext(),
                        new ComponentName(getContext(), (Class<?>) NotificationListener.class),
                        -1);
            } catch (RemoteException unused) {
            }
        }
        if (android.view.flags.Flags.sensitiveContentAppProtection()) {
            this.mWindowManager.registerOnWindowRemovedListener(this.mOnWindowRemovedListener);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i != 1000) {
            return;
        }
        init(
                (MediaProjectionManager)
                        getContext().getSystemService(MediaProjectionManager.class),
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class),
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class),
                SystemConfig.getInstance().mBugreportWhitelistedPackages);
        if (android.view.flags.Flags.sensitiveContentAppProtection()) {
            publishBinderService(
                    "sensitive_content_protection_service",
                    new SensitiveContentProtectionManagerServiceBinder());
        }
    }

    public void onDestroy() {
        MediaProjectionManager mediaProjectionManager = this.mProjectionManager;
        if (mediaProjectionManager != null) {
            mediaProjectionManager.removeCallback(this.mProjectionCallback);
        }
        if (Flags.sensitiveNotificationAppProtection()) {
            try {
                this.mNotificationListener.unregisterAsSystemService();
            } catch (RemoteException unused) {
            }
        }
        if (this.mWindowManager != null) {
            onProjectionEnd();
        }
    }

    public final void onProjectionEnd() {
        synchronized (this.mSensitiveContentProtectionLock) {
            try {
                this.mProjectionActive = false;
                MediaProjectionSession mediaProjectionSession = this.mMediaProjectionSession;
                if (mediaProjectionSession != null) {
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.SENSITIVE_CONTENT_MEDIA_PROJECTION_SESSION,
                            mediaProjectionSession.mSessionId,
                            mediaProjectionSession.mUid,
                            mediaProjectionSession.mIsExempted,
                            2,
                            2);
                    if (Flags.sensitiveContentImprovements()) {
                        MediaProjectionSession mediaProjectionSession2 =
                                this.mMediaProjectionSession;
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_SESSION,
                                mediaProjectionSession2.mSessionId,
                                mediaProjectionSession2.mAllSeenNotificationKeys.size(),
                                mediaProjectionSession2.mSeenOtpNotificationKeys.size());
                    }
                    this.mMediaProjectionSession = null;
                }
                this.mWindowManager.clearBlockedApps();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {}

    public void setSensitiveContentProtection(IBinder iBinder, String str, int i, boolean z) {
        synchronized (this.mSensitiveContentProtectionLock) {
            try {
                SensitiveContentPackages.PackageInfo packageInfo =
                        new SensitiveContentPackages.PackageInfo(i, iBinder, str);
                if (z) {
                    this.mPackagesShowingSensitiveContent.add(packageInfo);
                    if (this.mPackagesShowingSensitiveContent.size() > 100) {
                        Log.w(
                                "SensitiveContentProtect",
                                "Unexpectedly large number of sensitive windows, count: "
                                        + this.mPackagesShowingSensitiveContent.size());
                    }
                } else {
                    this.mPackagesShowingSensitiveContent.remove(packageInfo);
                }
                if (this.mProjectionActive) {
                    ArraySet arraySet = new ArraySet();
                    arraySet.add(packageInfo);
                    if (z) {
                        this.mWindowManager.addBlockScreenCaptureForApps(arraySet);
                        MediaProjectionSession mediaProjectionSession =
                                this.mMediaProjectionSession;
                        if (mediaProjectionSession != null) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.SENSITIVE_CONTENT_APP_PROTECTION,
                                    mediaProjectionSession.mSessionId,
                                    i,
                                    mediaProjectionSession.mUid,
                                    1);
                        }
                    } else {
                        this.mWindowManager.removeBlockScreenCaptureForApps(arraySet);
                        MediaProjectionSession mediaProjectionSession2 =
                                this.mMediaProjectionSession;
                        if (mediaProjectionSession2 != null) {
                            FrameworkStatsLog.write(
                                    FrameworkStatsLog.SENSITIVE_CONTENT_APP_PROTECTION,
                                    mediaProjectionSession2.mSessionId,
                                    i,
                                    mediaProjectionSession2.mUid,
                                    2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAppsThatShouldBlockScreenCapture() {
        NotificationListenerService.RankingMap rankingMap;
        try {
            rankingMap = this.mNotificationListener.getCurrentRanking();
        } catch (SecurityException e) {
            Log.e(
                    "SensitiveContentProtect",
                    "SensitiveContentProtectionManagerService doesn't have access.",
                    e);
            rankingMap = null;
        }
        if (rankingMap == null) {
            Log.w("SensitiveContentProtect", "Ranking map not initialized.");
        } else {
            updateAppsThatShouldBlockScreenCapture(rankingMap);
        }
    }

    public final void updateAppsThatShouldBlockScreenCapture(
            NotificationListenerService.RankingMap rankingMap) {
        StatusBarNotification[] statusBarNotificationArr;
        MediaProjectionSession mediaProjectionSession;
        try {
            statusBarNotificationArr = this.mNotificationListener.getActiveNotifications();
        } catch (SecurityException e) {
            Log.e(
                    "SensitiveContentProtect",
                    "SensitiveContentProtectionManagerService doesn't have access.",
                    e);
            statusBarNotificationArr = new StatusBarNotification[0];
        }
        if (Flags.sensitiveContentImprovements()
                && (mediaProjectionSession = this.mMediaProjectionSession) != null) {
            for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
                if (statusBarNotification == null) {
                    Log.w("SensitiveContentProtect", "Unable to parse null notification");
                } else {
                    NotificationListenerService.Ranking rawRankingObject =
                            rankingMap.getRawRankingObject(statusBarNotification.getKey());
                    if (rawRankingObject == null || !rawRankingObject.hasSensitiveContent()) {
                        mediaProjectionSession.mAllSeenNotificationKeys.add(
                                statusBarNotification.getKey());
                    } else {
                        String key = statusBarNotification.getKey();
                        mediaProjectionSession.mAllSeenNotificationKeys.add(key);
                        mediaProjectionSession.mSeenOtpNotificationKeys.add(key);
                    }
                }
            }
        }
        ArraySet arraySet = new ArraySet();
        for (StatusBarNotification statusBarNotification2 : statusBarNotificationArr) {
            if (statusBarNotification2 == null) {
                Log.w("SensitiveContentProtect", "Unable to parse null notification");
            } else {
                NotificationListenerService.Ranking rawRankingObject2 =
                        rankingMap.getRawRankingObject(statusBarNotification2.getKey());
                SensitiveContentPackages.PackageInfo packageInfo =
                        rawRankingObject2 != null && rawRankingObject2.hasSensitiveContent()
                                ? new SensitiveContentPackages.PackageInfo(
                                        statusBarNotification2.getUid(),
                                        null,
                                        statusBarNotification2.getPackageName())
                                : null;
                if (packageInfo != null) {
                    arraySet.add(packageInfo);
                }
            }
        }
        if (arraySet.size() > 0) {
            this.mWindowManager.addBlockScreenCaptureForApps(arraySet);
        }
    }
}
