package com.android.systemui.statusbar.policy;

import android.app.IActivityManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.ExecutorContentObserver;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyManager;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SensitiveNotificationProtectionControllerImpl implements SensitiveNotificationProtectionController {
    public SensitiveNotificatioMediaProjectionSession mActiveMediaProjectionSession;
    public final SensitiveNotificationProtectionControllerLogger mLogger;
    final MediaProjectionManager.Callback mMediaProjectionCallback;
    public final PackageManager mPackageManager;
    public volatile MediaProjectionInfo mProjection;
    final OnRoleHoldersChangedListener mRoleHoldersChangedListener;
    public final RoleManager mRoleManager;
    public final ArraySet mSessionProtectionExemptPackages = new ArraySet();
    public final ArraySet mNotificationProtectionExemptPackages = new ArraySet();
    public final ListenerSet mListeners = new ListenerSet();
    public ArraySet mNotificationProtectionExemptByRolePackages = new ArraySet();
    public boolean mDisableScreenShareProtections = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$3, reason: invalid class name */
    public class AnonymousClass3 extends ExecutorContentObserver {
        public final /* synthetic */ Handler val$mainHandler;
        public final /* synthetic */ GlobalSettings val$settings;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Executor executor, GlobalSettings globalSettings, Handler handler) {
            super(executor);
            this.val$settings = globalSettings;
            this.val$mainHandler = handler;
        }

        public final void onChange(boolean z) {
            super.onChange(z);
            final boolean z2 = this.val$settings.getInt("disable_screen_share_protections_for_apps_and_notifications", 0) != 0;
            this.val$mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SensitiveNotificationProtectionControllerImpl.this.mDisableScreenShareProtections = z2;
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RoleHolder {
        public final String mPackageName;
        public final UserHandle mUserHandle;

        public RoleHolder(String str, UserHandle userHandle) {
            this.mPackageName = str;
            this.mUserHandle = userHandle;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof RoleHolder) {
                RoleHolder roleHolder = (RoleHolder) obj;
                if (Objects.equals(this.mPackageName, roleHolder.mPackageName) && Objects.equals(this.mUserHandle, roleHolder.mUserHandle)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hash(this.mPackageName, this.mUserHandle);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SensitiveNotificatioMediaProjectionSession {
        public final boolean mExempt;
        public final int mProjectionAppUid;
        public final long mSessionId;

        public SensitiveNotificatioMediaProjectionSession(long j, int i, boolean z) {
            this.mSessionId = j;
            this.mProjectionAppUid = i;
            this.mExempt = z;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1, types: [android.database.ContentObserver, com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$3] */
    public SensitiveNotificationProtectionControllerImpl(final Context context, GlobalSettings globalSettings, MediaProjectionManager mediaProjectionManager, final IActivityManager iActivityManager, PackageManager packageManager, final TelephonyManager telephonyManager, RoleManager roleManager, final Handler handler, Executor executor, SensitiveNotificationProtectionControllerLogger sensitiveNotificationProtectionControllerLogger) {
        MediaProjectionManager.Callback callback = new MediaProjectionManager.Callback() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl.1
            public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
                int i;
                Trace.beginSection("SNPC.onProjectionStart");
                try {
                    SensitiveNotificationProtectionControllerImpl.this.updateProjectionStateAndNotifyListeners(mediaProjectionInfo);
                    SensitiveNotificationProtectionControllerImpl sensitiveNotificationProtectionControllerImpl = SensitiveNotificationProtectionControllerImpl.this;
                    SensitiveNotificationProtectionControllerLogger sensitiveNotificationProtectionControllerLogger2 = sensitiveNotificationProtectionControllerImpl.mLogger;
                    boolean isSensitiveStateActive = sensitiveNotificationProtectionControllerImpl.isSensitiveStateActive();
                    String packageName = mediaProjectionInfo.getPackageName();
                    sensitiveNotificationProtectionControllerLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    SensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0 sensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0 = new SensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0(1);
                    LogBuffer logBuffer = sensitiveNotificationProtectionControllerLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("SNPC", logLevel, sensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) obtain).bool1 = isSensitiveStateActive;
                    ((LogMessageImpl) obtain).str1 = packageName;
                    logBuffer.commit(obtain);
                    try {
                        i = SensitiveNotificationProtectionControllerImpl.this.mPackageManager.getPackageUidAsUser(mediaProjectionInfo.getPackageName(), mediaProjectionInfo.getUserHandle().getIdentifier());
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.w("SNPC", "Package " + mediaProjectionInfo.getPackageName() + " not found");
                        i = -1;
                    }
                    SensitiveNotificationProtectionControllerImpl sensitiveNotificationProtectionControllerImpl2 = SensitiveNotificationProtectionControllerImpl.this;
                    long nextLong = new Random().nextLong();
                    boolean z = !SensitiveNotificationProtectionControllerImpl.this.isSensitiveStateActive();
                    sensitiveNotificationProtectionControllerImpl2.getClass();
                    SensitiveNotificatioMediaProjectionSession sensitiveNotificatioMediaProjectionSession = new SensitiveNotificatioMediaProjectionSession(nextLong, i, z);
                    sensitiveNotificationProtectionControllerImpl2.mActiveMediaProjectionSession = sensitiveNotificatioMediaProjectionSession;
                    FrameworkStatsLog.write(830, sensitiveNotificatioMediaProjectionSession.mSessionId, sensitiveNotificatioMediaProjectionSession.mProjectionAppUid, sensitiveNotificatioMediaProjectionSession.mExempt, 1, 1);
                } finally {
                    Trace.endSection();
                }
            }

            public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
                Trace.beginSection("SNPC.onProjectionStop");
                try {
                    SensitiveNotificationProtectionControllerLogger sensitiveNotificationProtectionControllerLogger2 = SensitiveNotificationProtectionControllerImpl.this.mLogger;
                    sensitiveNotificationProtectionControllerLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    SensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0 sensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0 = new SensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0(0);
                    LogBuffer logBuffer = sensitiveNotificationProtectionControllerLogger2.buffer;
                    logBuffer.commit(logBuffer.obtain("SNPC", logLevel, sensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0, null));
                    SensitiveNotificationProtectionControllerImpl sensitiveNotificationProtectionControllerImpl = SensitiveNotificationProtectionControllerImpl.this;
                    SensitiveNotificatioMediaProjectionSession sensitiveNotificatioMediaProjectionSession = sensitiveNotificationProtectionControllerImpl.mActiveMediaProjectionSession;
                    if (sensitiveNotificatioMediaProjectionSession != null) {
                        FrameworkStatsLog.write(830, sensitiveNotificatioMediaProjectionSession.mSessionId, sensitiveNotificatioMediaProjectionSession.mProjectionAppUid, sensitiveNotificatioMediaProjectionSession.mExempt, 2, 1);
                        sensitiveNotificationProtectionControllerImpl.mActiveMediaProjectionSession = null;
                    }
                    SensitiveNotificationProtectionControllerImpl.this.updateProjectionStateAndNotifyListeners(null);
                } finally {
                    Trace.endSection();
                }
            }
        };
        this.mMediaProjectionCallback = callback;
        OnRoleHoldersChangedListener onRoleHoldersChangedListener = new OnRoleHoldersChangedListener() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl.2
            public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                if (str.equals("android.app.role.COMPANION_DEVICE_APP_STREAMING")) {
                    List roleHoldersAsUser = SensitiveNotificationProtectionControllerImpl.this.mRoleManager.getRoleHoldersAsUser(str, userHandle);
                    ArraySet arraySet = new ArraySet();
                    Iterator it = roleHoldersAsUser.iterator();
                    while (it.hasNext()) {
                        arraySet.add(new RoleHolder((String) it.next(), userHandle));
                    }
                    SensitiveNotificationProtectionControllerImpl.this.mNotificationProtectionExemptByRolePackages = arraySet;
                }
            }
        };
        this.mRoleHoldersChangedListener = onRoleHoldersChangedListener;
        this.mLogger = sensitiveNotificationProtectionControllerLogger;
        this.mPackageManager = packageManager;
        this.mRoleManager = roleManager;
        final ?? anonymousClass3 = new AnonymousClass3(executor, globalSettings, handler);
        globalSettings.registerContentObserverSync("disable_screen_share_protections_for_apps_and_notifications", (ContentObserver) anonymousClass3);
        executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SensitiveNotificationProtectionControllerImpl.AnonymousClass3.this.onChange(true);
            }
        });
        executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                final SensitiveNotificationProtectionControllerImpl sensitiveNotificationProtectionControllerImpl = SensitiveNotificationProtectionControllerImpl.this;
                Context context2 = context;
                IActivityManager iActivityManager2 = iActivityManager;
                TelephonyManager telephonyManager2 = telephonyManager;
                Handler handler2 = handler;
                final ArraySet arraySet = new ArraySet();
                arraySet.add(context2.getPackageName());
                try {
                    arraySet.addAll(iActivityManager2.getBugreportWhitelistedPackages());
                } catch (RemoteException e) {
                    Log.w("SNPC", "Error adding bug report handlers to exemption, continuing without", e);
                }
                final ArraySet arraySet2 = new ArraySet();
                try {
                    String emergencyAssistancePackageName = telephonyManager2.getEmergencyAssistancePackageName();
                    if (emergencyAssistancePackageName != null) {
                        arraySet2.add(emergencyAssistancePackageName);
                    }
                } catch (IllegalStateException e2) {
                    Log.w("SNPC", "Error adding emergency assistance package to exemption", e2);
                }
                handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SensitiveNotificationProtectionControllerImpl sensitiveNotificationProtectionControllerImpl2 = SensitiveNotificationProtectionControllerImpl.this;
                        ArraySet arraySet3 = arraySet;
                        ArraySet arraySet4 = arraySet2;
                        sensitiveNotificationProtectionControllerImpl2.getClass();
                        Trace.beginSection("SNPC.exemptPackagesUpdated");
                        try {
                            Assert.isMainThread();
                            sensitiveNotificationProtectionControllerImpl2.mSessionProtectionExemptPackages.addAll(arraySet3);
                            sensitiveNotificationProtectionControllerImpl2.mNotificationProtectionExemptPackages.addAll(arraySet4);
                            if (sensitiveNotificationProtectionControllerImpl2.mProjection != null) {
                                sensitiveNotificationProtectionControllerImpl2.updateProjectionStateAndNotifyListeners(sensitiveNotificationProtectionControllerImpl2.mProjection);
                            }
                        } finally {
                            Trace.endSection();
                        }
                    }
                });
            }
        });
        mediaProjectionManager.addCallback(callback, handler);
        roleManager.addOnRoleHoldersChangedListenerAsUser(executor, onRoleHoldersChangedListener, UserHandle.ALL);
    }

    public final boolean isSensitiveStateActive() {
        return this.mProjection != null;
    }

    public final boolean shouldProtectNotification(NotificationEntry notificationEntry) {
        MediaProjectionInfo mediaProjectionInfo;
        if (isSensitiveStateActive() && (mediaProjectionInfo = this.mProjection) != null) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if ((!statusBarNotification.getNotification().isFgsOrUij() || !statusBarNotification.getPackageName().equals(mediaProjectionInfo.getPackageName())) && !UserHandle.isCore(statusBarNotification.getUid()) && !this.mNotificationProtectionExemptPackages.contains(statusBarNotification.getPackageName())) {
                boolean z = notificationEntry.mSbn.getNotification().visibility == 0;
                boolean z2 = notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().getLockscreenVisibility() == 0;
                if (z || z2) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void updateProjectionStateAndNotifyListeners(MediaProjectionInfo mediaProjectionInfo) {
        Assert.isMainThread();
        boolean isSensitiveStateActive = isSensitiveStateActive();
        if (this.mDisableScreenShareProtections) {
            Log.w("SNPC", "Screen share protections disabled");
        } else if (mediaProjectionInfo == null || !this.mSessionProtectionExemptPackages.contains(mediaProjectionInfo.getPackageName())) {
            if (mediaProjectionInfo != null) {
                if (this.mPackageManager.checkPermission("android.permission.RECORD_SENSITIVE_CONTENT", mediaProjectionInfo.getPackageName()) == 0) {
                    Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName() + " via permission");
                }
            }
            if (mediaProjectionInfo == null || !this.mNotificationProtectionExemptByRolePackages.contains(new RoleHolder(mediaProjectionInfo.getPackageName(), mediaProjectionInfo.getUserHandle()))) {
                if (mediaProjectionInfo != null && mediaProjectionInfo.getLaunchCookie() != null) {
                    Log.w("SNPC", "Screen share protections exempt for single app screenshare");
                }
                this.mProjection = mediaProjectionInfo;
                if (!isSensitiveStateActive || isSensitiveStateActive()) {
                    this.mListeners.forEach(new AppLockNotificationControllerImpl$$ExternalSyntheticLambda1());
                }
                return;
            }
            Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName() + " via role(s) held");
        } else {
            Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName());
        }
        mediaProjectionInfo = null;
        this.mProjection = mediaProjectionInfo;
        if (isSensitiveStateActive) {
        }
        this.mListeners.forEach(new AppLockNotificationControllerImpl$$ExternalSyntheticLambda1());
    }
}
