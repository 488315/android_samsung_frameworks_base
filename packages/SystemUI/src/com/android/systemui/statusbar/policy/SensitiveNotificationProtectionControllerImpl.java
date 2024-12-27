package com.android.systemui.statusbar.policy;

import android.app.IActivityManager;
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
import com.android.server.notification.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Random;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SensitiveNotificationProtectionControllerImpl implements SensitiveNotificationProtectionController {
    public SensitiveNotificatioMediaProjectionSession mActiveMediaProjectionSession;
    public final SensitiveNotificationProtectionControllerLogger mLogger;
    final MediaProjectionManager.Callback mMediaProjectionCallback;
    public final PackageManager mPackageManager;
    public volatile MediaProjectionInfo mProjection;
    public final ArraySet mSessionProtectionExemptPackages = new ArraySet();
    public final ArraySet mNotificationProtectionExemptPackages = new ArraySet();
    public final ListenerSet mListeners = new ListenerSet();
    public boolean mDisableScreenShareProtections = false;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$2, reason: invalid class name */
    public final class AnonymousClass2 extends ExecutorContentObserver {
        public final /* synthetic */ Handler val$mainHandler;
        public final /* synthetic */ GlobalSettings val$settings;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Executor executor, GlobalSettings globalSettings, Handler handler) {
            super(executor);
            this.val$settings = globalSettings;
            this.val$mainHandler = handler;
        }

        public final void onChange(boolean z) {
            super.onChange(z);
            final boolean z2 = this.val$settings.getInt("disable_screen_share_protections_for_apps_and_notifications", 0) != 0;
            this.val$mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SensitiveNotificationProtectionControllerImpl.this.mDisableScreenShareProtections = z2;
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SensitiveNotificatioMediaProjectionSession {
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
    /* JADX WARN: Type inference failed for: r2v6, types: [android.database.ContentObserver, com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$2] */
    public SensitiveNotificationProtectionControllerImpl(final Context context, GlobalSettings globalSettings, MediaProjectionManager mediaProjectionManager, final IActivityManager iActivityManager, PackageManager packageManager, final TelephonyManager telephonyManager, final Handler handler, Executor executor, SensitiveNotificationProtectionControllerLogger sensitiveNotificationProtectionControllerLogger) {
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
                    SensitiveNotificationProtectionControllerLogger$logProjectionStart$2 sensitiveNotificationProtectionControllerLogger$logProjectionStart$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerLogger$logProjectionStart$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return "Projection started - protection enabled:" + logMessage.getBool1() + ", pkg=" + logMessage.getStr1();
                        }
                    };
                    LogBuffer logBuffer = sensitiveNotificationProtectionControllerLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("SNPC", logLevel, sensitiveNotificationProtectionControllerLogger$logProjectionStart$2, null);
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
                    SensitiveNotificationProtectionControllerLogger$logProjectionStop$2 sensitiveNotificationProtectionControllerLogger$logProjectionStop$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerLogger$logProjectionStop$2
                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return "Projection ended - protection disabled";
                        }
                    };
                    LogBuffer logBuffer = sensitiveNotificationProtectionControllerLogger2.buffer;
                    logBuffer.commit(logBuffer.obtain("SNPC", logLevel, sensitiveNotificationProtectionControllerLogger$logProjectionStop$2, null));
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
        this.mLogger = sensitiveNotificationProtectionControllerLogger;
        this.mPackageManager = packageManager;
        Flags.screenshareNotificationHiding();
        final ?? anonymousClass2 = new AnonymousClass2(executor, globalSettings, handler);
        globalSettings.registerContentObserverSync("disable_screen_share_protections_for_apps_and_notifications", (ContentObserver) anonymousClass2);
        executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                anonymousClass2.onChange(true);
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
                sensitiveNotificationProtectionControllerImpl.getClass();
                final ArraySet arraySet = new ArraySet();
                arraySet.add(context2.getPackageName());
                try {
                    arraySet.addAll(iActivityManager2.getBugreportWhitelistedPackages());
                } catch (RemoteException e) {
                    Log.w("SNPC", "Error adding bug report handlers to exemption, continuing without", e);
                }
                final ArraySet arraySet2 = new ArraySet();
                com.android.systemui.Flags.FEATURE_FLAGS.getClass();
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
                            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
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
    }

    public final boolean isSensitiveStateActive() {
        return this.mProjection != null;
    }

    public final boolean shouldProtectNotification(NotificationEntry notificationEntry) {
        MediaProjectionInfo mediaProjectionInfo;
        if (!isSensitiveStateActive() || (mediaProjectionInfo = this.mProjection) == null) {
            return false;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (statusBarNotification.getNotification().isFgsOrUij() && statusBarNotification.getPackageName().equals(mediaProjectionInfo.getPackageName())) {
            return false;
        }
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        if (UserHandle.isCore(statusBarNotification.getUid())) {
            return false;
        }
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        if (this.mNotificationProtectionExemptPackages.contains(statusBarNotification.getPackageName())) {
            return false;
        }
        return (notificationEntry.mSbn.getNotification().visibility == 0) || (notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().getLockscreenVisibility() == 0);
    }

    public final void updateProjectionStateAndNotifyListeners(MediaProjectionInfo mediaProjectionInfo) {
        Assert.isMainThread();
        boolean isSensitiveStateActive = isSensitiveStateActive();
        if (this.mDisableScreenShareProtections) {
            Log.w("SNPC", "Screen share protections disabled");
        } else {
            if (mediaProjectionInfo == null || !this.mSessionProtectionExemptPackages.contains(mediaProjectionInfo.getPackageName())) {
                if (mediaProjectionInfo != null) {
                    String packageName = mediaProjectionInfo.getPackageName();
                    if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.sensitiveNotificationAppProtection() && this.mPackageManager.checkPermission("android.permission.RECORD_SENSITIVE_CONTENT", packageName) == 0) {
                        Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName() + " via permission");
                    }
                }
                if (mediaProjectionInfo != null && mediaProjectionInfo.getLaunchCookie() != null) {
                    Log.w("SNPC", "Screen share protections exempt for single app screenshare");
                }
                this.mProjection = mediaProjectionInfo;
                if (!isSensitiveStateActive || isSensitiveStateActive()) {
                    this.mListeners.forEach(new AppLockNotificationControllerImpl$$ExternalSyntheticLambda1());
                }
                return;
            }
            Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName());
        }
        mediaProjectionInfo = null;
        this.mProjection = mediaProjectionInfo;
        if (isSensitiveStateActive) {
        }
        this.mListeners.forEach(new AppLockNotificationControllerImpl$$ExternalSyntheticLambda1());
    }
}
