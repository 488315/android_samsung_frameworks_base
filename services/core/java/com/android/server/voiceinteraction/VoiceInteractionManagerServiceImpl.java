package com.android.server.voiceinteraction;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.ApplicationExitInfo;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.ProfilerInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.voice.IVoiceInteractionService;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice.VisibleActivityInfo;
import android.service.voice.VoiceInteractionServiceInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.view.IWindowManager;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.AssistDataRequester;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerService;
import com.android.server.power.LowPowerStandbyController;
import com.android.server.voiceinteraction.HotwordDetectionConnection;
import com.android.server.voiceinteraction.VoiceInteractionManagerService;
import com.android.server.voiceinteraction.VoiceInteractionSessionConnection;
import com.android.server.voiceinteraction.VoiceInteractionSessionConnection.PowerBoostSetter;
import com.android.server.wm.ActivityAssistInfo;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.rune.InputRune;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VoiceInteractionManagerServiceImpl implements VoiceInteractionSessionConnection.Callback {
    public static final boolean SYSPROP_VISUAL_QUERY_SERVICE_ENABLED = SystemProperties.getBoolean("ro.hotword.visual_query_service_enabled", false);
    public final ArrayList mAccessibilitySettingsListeners;
    public VoiceInteractionSessionConnection mActiveSession;
    public final IActivityManager mAm;
    public final IActivityTaskManager mAtm;
    public boolean mBound = false;
    public final AnonymousClass1 mBroadcastReceiver;
    public final ComponentName mComponent;
    public final AnonymousClass2 mConnection;
    public final Context mContext;
    public final Handler mDirectActionsHandler;
    public int mDisabledShowContext;
    public final Handler mHandler;
    public final ComponentName mHotwordDetectionComponentName;
    public volatile HotwordDetectionConnection mHotwordDetectionConnection;
    public final VoiceInteractionServiceInfo mInfo;
    public final PackageManagerInternal mPackageManagerInternal;
    public IVoiceInteractionService mService;
    public final VoiceInteractionManagerService.VoiceInteractionManagerServiceStub mServiceStub;
    public final ComponentName mSessionComponentName;
    public final int mUser;
    public final boolean mValid;
    public final ComponentName mVisualQueryDetectionComponentName;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilitySettingsContentObserver extends ContentObserver {
        public final Uri mAccessibilitySettingsEnabledUri;

        public AccessibilitySettingsContentObserver() {
            super(null);
            this.mAccessibilitySettingsEnabledUri = Settings.Secure.getUriFor("visual_query_accessibility_detection_enabled");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            Slog.i("VoiceInteractionServiceManager", "OnChange called with uri:" + uri);
            if (this.mAccessibilitySettingsEnabledUri.equals(uri)) {
                final boolean z2 = Settings.Secure.getIntForUser(VoiceInteractionManagerServiceImpl.this.mContext.getContentResolver(), "visual_query_accessibility_detection_enabled", 0, VoiceInteractionManagerServiceImpl.this.mUser) == 1;
                Slog.i("VoiceInteractionServiceManager", "Notifying listeners with Accessibility setting set to " + z2);
                VoiceInteractionManagerServiceImpl.this.mAccessibilitySettingsListeners.forEach(new Consumer() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl$AccessibilitySettingsContentObserver$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        try {
                            ((IVoiceInteractionAccessibilitySettingsListener) obj).onAccessibilityDetectionChanged(z2);
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl$2] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl$1] */
    public VoiceInteractionManagerServiceImpl(Context context, Handler handler, VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub, int i, ComponentName componentName) {
        ?? r3 = new BroadcastReceiver() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                IVoiceInteractionSession iVoiceInteractionSession;
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra("reason");
                    if ("voiceinteraction".equals(stringExtra) || TextUtils.equals("dream", stringExtra) || "assist".equals(stringExtra)) {
                        return;
                    }
                    synchronized (VoiceInteractionManagerServiceImpl.this.mServiceStub) {
                        VoiceInteractionSessionConnection voiceInteractionSessionConnection = VoiceInteractionManagerServiceImpl.this.mActiveSession;
                        if (voiceInteractionSessionConnection != null && (iVoiceInteractionSession = voiceInteractionSessionConnection.mSession) != null) {
                            try {
                                iVoiceInteractionSession.closeSystemDialogs();
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                }
            }
        };
        this.mBroadcastReceiver = r3;
        this.mConnection = new ServiceConnection() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.2
            @Override // android.content.ServiceConnection
            public final void onBindingDied(ComponentName componentName2) {
                ParceledListSlice parceledListSlice;
                Slog.d("VoiceInteractionServiceManager", "onBindingDied to " + componentName2);
                String packageName = componentName2.getPackageName();
                try {
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = VoiceInteractionManagerServiceImpl.this;
                    parceledListSlice = voiceInteractionManagerServiceImpl.mAm.getHistoricalProcessExitReasons(packageName, 0, 1, voiceInteractionManagerServiceImpl.mUser);
                } catch (RemoteException unused) {
                    parceledListSlice = null;
                }
                if (parceledListSlice == null) {
                    return;
                }
                List list = parceledListSlice.getList();
                if (list.isEmpty()) {
                    return;
                }
                ApplicationExitInfo applicationExitInfo = (ApplicationExitInfo) list.get(0);
                if (applicationExitInfo.getReason() == 10 && applicationExitInfo.getSubReason() == 23) {
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl2 = VoiceInteractionManagerServiceImpl.this;
                    VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub2 = voiceInteractionManagerServiceImpl2.mServiceStub;
                    int i2 = voiceInteractionManagerServiceImpl2.mUser;
                    synchronized (voiceInteractionManagerServiceStub2) {
                        try {
                            ComponentName curInteractor = voiceInteractionManagerServiceStub2.getCurInteractor(i2);
                            if (curInteractor != null && packageName.equals(curInteractor.getPackageName())) {
                                Slog.d("VoiceInteractionManager", "switchImplementation for user stop.");
                                voiceInteractionManagerServiceStub2.switchImplementationIfNeededLocked(true);
                            }
                        } finally {
                        }
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName2, IBinder iBinder) {
                synchronized (VoiceInteractionManagerServiceImpl.this.mServiceStub) {
                    VoiceInteractionManagerServiceImpl.this.mService = IVoiceInteractionService.Stub.asInterface(iBinder);
                    try {
                        VoiceInteractionManagerServiceImpl.this.mService.ready();
                    } catch (RemoteException unused) {
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName2) {
                synchronized (VoiceInteractionManagerServiceImpl.this.mServiceStub) {
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = VoiceInteractionManagerServiceImpl.this;
                    voiceInteractionManagerServiceImpl.mService = null;
                    if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection != null) {
                        voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.cancelLocked();
                        voiceInteractionManagerServiceImpl.mAccessibilitySettingsListeners.remove(voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.mAccessibilitySettingsListener);
                        voiceInteractionManagerServiceImpl.mHotwordDetectionConnection = null;
                    }
                }
            }
        };
        this.mAccessibilitySettingsListeners = new ArrayList();
        this.mContext = context;
        this.mHandler = handler;
        this.mDirectActionsHandler = new Handler(true);
        this.mServiceStub = voiceInteractionManagerServiceStub;
        this.mUser = i;
        this.mComponent = componentName;
        this.mAm = ActivityManager.getService();
        this.mAtm = ActivityTaskManager.getService();
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Objects.requireNonNull(packageManagerInternal);
        this.mPackageManagerInternal = packageManagerInternal;
        try {
            VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(context.getPackageManager(), componentName, i);
            this.mInfo = voiceInteractionServiceInfo;
            if (voiceInteractionServiceInfo.getParseError() != null) {
                Slog.w("VoiceInteractionServiceManager", "Bad voice interaction service: " + voiceInteractionServiceInfo.getParseError());
                this.mSessionComponentName = null;
                this.mHotwordDetectionComponentName = null;
                this.mVisualQueryDetectionComponentName = null;
                this.mValid = false;
                return;
            }
            this.mValid = true;
            this.mSessionComponentName = new ComponentName(componentName.getPackageName(), voiceInteractionServiceInfo.getSessionService());
            String hotwordDetectionService = voiceInteractionServiceInfo.getHotwordDetectionService();
            this.mHotwordDetectionComponentName = hotwordDetectionService != null ? new ComponentName(componentName.getPackageName(), hotwordDetectionService) : null;
            String visualQueryDetectionService = voiceInteractionServiceInfo.getVisualQueryDetectionService();
            this.mVisualQueryDetectionComponentName = visualQueryDetectionService != null ? new ComponentName(componentName.getPackageName(), visualQueryDetectionService) : null;
            IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            context.registerReceiver(r3, intentFilter, null, handler, 2);
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver = new AccessibilitySettingsContentObserver();
            context.getContentResolver().registerContentObserver(accessibilitySettingsContentObserver.mAccessibilitySettingsEnabledUri, false, accessibilitySettingsContentObserver, -1);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.w("VoiceInteractionServiceManager", "Voice interaction service not found: " + componentName, e);
            this.mInfo = null;
            this.mSessionComponentName = null;
            this.mHotwordDetectionComponentName = null;
            this.mVisualQueryDetectionComponentName = null;
            this.mValid = false;
        }
    }

    public static ServiceInfo getServiceInfoLocked(int i, ComponentName componentName) {
        try {
            return AppGlobals.getPackageManager().getServiceInfo(componentName, 786560L, i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public static void logDetectorCreateEventIfNeeded(IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, boolean z, int i2) {
        if (iHotwordRecognitionStatusCallback != null) {
            int i3 = 1;
            if (i != 1) {
                i3 = 2;
                if (i != 2) {
                    i3 = 0;
                }
            }
            FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTOR_CREATE_REQUESTED, i3, z, i2);
        }
    }

    public final boolean deliverNewSessionLocked(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) {
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        if (voiceInteractionSessionConnection == null || iBinder != voiceInteractionSessionConnection.mToken) {
            Slog.w("VoiceInteractionServiceManager", "deliverNewSession does not match active session");
            return false;
        }
        voiceInteractionSessionConnection.mSession = iVoiceInteractionSession;
        voiceInteractionSessionConnection.mInteractor = iVoiceInteractor;
        if (!voiceInteractionSessionConnection.mShown) {
            return true;
        }
        try {
            iVoiceInteractionSession.show(voiceInteractionSessionConnection.mShowArgs, voiceInteractionSessionConnection.mShowFlags, voiceInteractionSessionConnection.mShowCallback);
            voiceInteractionSessionConnection.mShowArgs = null;
            voiceInteractionSessionConnection.mShowFlags = 0;
        } catch (RemoteException unused) {
        }
        AssistDataRequester assistDataRequester = voiceInteractionSessionConnection.mAssistDataRequester;
        assistDataRequester.flushPendingAssistData();
        assistDataRequester.tryDispatchRequestComplete();
        if (voiceInteractionSessionConnection.mPendingHandleAssistWithoutData.isEmpty()) {
            return true;
        }
        voiceInteractionSessionConnection.doHandleAssistWithoutData(voiceInteractionSessionConnection.mPendingHandleAssistWithoutData);
        voiceInteractionSessionConnection.mPendingHandleAssistWithoutData.clear();
        return true;
    }

    public final void dumpLocked(final PrintWriter printWriter) {
        HotwordDetectionConnection.ServiceConnection serviceConnection;
        if (!this.mValid) {
            printWriter.print("  NOT VALID: ");
            VoiceInteractionServiceInfo voiceInteractionServiceInfo = this.mInfo;
            if (voiceInteractionServiceInfo == null) {
                printWriter.println("no info");
                return;
            } else {
                printWriter.println(voiceInteractionServiceInfo.getParseError());
                return;
            }
        }
        printWriter.print("  mUser=");
        printWriter.println(this.mUser);
        printWriter.print("  mComponent=");
        printWriter.println(this.mComponent.flattenToShortString());
        printWriter.print("  Session service=");
        printWriter.println(this.mInfo.getSessionService());
        printWriter.println("  Service info:");
        this.mInfo.getServiceInfo().dump(new PrintWriterPrinter(printWriter), "    ");
        printWriter.print("  Recognition service=");
        printWriter.println(this.mInfo.getRecognitionService());
        printWriter.print("  Hotword detection service=");
        printWriter.println(this.mInfo.getHotwordDetectionService());
        printWriter.print("  Settings activity=");
        printWriter.println(this.mInfo.getSettingsActivity());
        printWriter.print("  Supports assist=");
        printWriter.println(this.mInfo.getSupportsAssist());
        printWriter.print("  Supports launch from keyguard=");
        printWriter.println(this.mInfo.getSupportsLaunchFromKeyguard());
        if (this.mDisabledShowContext != 0) {
            printWriter.print("  mDisabledShowContext=");
            printWriter.println(Integer.toHexString(this.mDisabledShowContext));
        }
        printWriter.print("  mBound=");
        printWriter.print(this.mBound);
        printWriter.print(" mService=");
        printWriter.println(this.mService);
        if (this.mHotwordDetectionConnection != null) {
            printWriter.println("  Hotword detection connection:");
            HotwordDetectionConnection hotwordDetectionConnection = this.mHotwordDetectionConnection;
            synchronized (hotwordDetectionConnection.mLock) {
                try {
                    printWriter.print("    ");
                    printWriter.print("mReStartPeriodSeconds=");
                    printWriter.println(hotwordDetectionConnection.mReStartPeriodSeconds);
                    printWriter.print("    ");
                    printWriter.print("bound for HotwordDetectionService=");
                    HotwordDetectionConnection.ServiceConnection serviceConnection2 = hotwordDetectionConnection.mRemoteHotwordDetectionService;
                    boolean z = false;
                    printWriter.println(serviceConnection2 != null && serviceConnection2.isBound());
                    printWriter.print("    ");
                    printWriter.print("bound for VisualQueryDetectionService=");
                    if (hotwordDetectionConnection.mRemoteVisualQueryDetectionService != null && (serviceConnection = hotwordDetectionConnection.mRemoteHotwordDetectionService) != null && serviceConnection.isBound()) {
                        z = true;
                    }
                    printWriter.println(z);
                    printWriter.print("    ");
                    printWriter.print("mRestartCount=");
                    printWriter.println(hotwordDetectionConnection.mRestartCount);
                    printWriter.print("    ");
                    printWriter.print("mLastRestartInstant=");
                    printWriter.println(hotwordDetectionConnection.mLastRestartInstant);
                    printWriter.print("    ");
                    printWriter.println("DetectorSession(s):");
                    printWriter.print("    ");
                    printWriter.print("Num of DetectorSession(s)=");
                    printWriter.println(hotwordDetectionConnection.mDetectorSessions.size());
                    hotwordDetectionConnection.runForEachDetectorSessionLocked(new Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda6
                        public final /* synthetic */ String f$0;

                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((DetectorSession) obj).dumpLocked(printWriter);
                        }
                    });
                } finally {
                }
            }
        } else {
            printWriter.println("  No Hotword detection connection");
        }
        if (this.mActiveSession != null) {
            printWriter.println("  Active session:");
            VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
            voiceInteractionSessionConnection.getClass();
            printWriter.print("    ");
            printWriter.print("mToken=");
            printWriter.println(voiceInteractionSessionConnection.mToken);
            printWriter.print("    ");
            printWriter.print("mShown=");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mShowArgs=", voiceInteractionSessionConnection.mShown);
            printWriter.println(voiceInteractionSessionConnection.mShowArgs);
            printWriter.print("    ");
            printWriter.print("mShowFlags=0x");
            printWriter.println(Integer.toHexString(voiceInteractionSessionConnection.mShowFlags));
            printWriter.print("    ");
            printWriter.print("mBound=");
            printWriter.println(voiceInteractionSessionConnection.mBound);
            if (voiceInteractionSessionConnection.mBound) {
                printWriter.print("    ");
                printWriter.print("mService=");
                printWriter.println(voiceInteractionSessionConnection.mService);
                printWriter.print("    ");
                printWriter.print("mSession=");
                printWriter.println(voiceInteractionSessionConnection.mSession);
                printWriter.print("    ");
                printWriter.print("mInteractor=");
                printWriter.println(voiceInteractionSessionConnection.mInteractor);
            }
            AssistDataRequester assistDataRequester = voiceInteractionSessionConnection.mAssistDataRequester;
            assistDataRequester.getClass();
            printWriter.print("    ");
            printWriter.print("mPendingDataCount=");
            BroadcastStats$$ExternalSyntheticOutline0.m(assistDataRequester.mPendingDataCount, printWriter, "    ", "mAssistData=");
            printWriter.println(assistDataRequester.mAssistData);
            printWriter.print("    ");
            printWriter.print("mPendingScreenshotCount=");
            BroadcastStats$$ExternalSyntheticOutline0.m(assistDataRequester.mPendingScreenshotCount, printWriter, "    ", "mAssistScreenshot=");
            printWriter.println(assistDataRequester.mAssistScreenshot);
        }
    }

    public final void finishLocked(IBinder iBinder, boolean z) {
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        if (voiceInteractionSessionConnection == null || !(z || iBinder == voiceInteractionSessionConnection.mToken)) {
            Slog.w("VoiceInteractionServiceManager", "finish does not match active session");
        } else {
            voiceInteractionSessionConnection.cancelLocked(z);
            this.mActiveSession = null;
        }
    }

    public final void grantImplicitAccessLocked(int i, Intent intent) {
        int appId = UserHandle.getAppId(i);
        ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).grantImplicitAccess(UserHandle.getUserId(i), intent, appId, this.mInfo.getServiceInfo().applicationInfo.uid, true, false);
    }

    public final void notifySoundModelsChangedLocked() {
        IVoiceInteractionService iVoiceInteractionService = this.mService;
        if (iVoiceInteractionService == null) {
            Slog.w("VoiceInteractionServiceManager", "Not bound to voice interaction service " + this.mComponent);
        } else {
            try {
                iVoiceInteractionService.soundModelsChanged();
            } catch (RemoteException e) {
                Slog.w("VoiceInteractionServiceManager", "RemoteException while calling soundModelsChanged", e);
            }
        }
    }

    public final void performDirectActionLocked(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        if (voiceInteractionSessionConnection == null || iBinder != voiceInteractionSessionConnection.mToken) {
            Slog.w("VoiceInteractionServiceManager", "performDirectActionLocked does not match active session");
            remoteCallback2.sendResult((Bundle) null);
            return;
        }
        ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getAttachedNonFinishingActivityForTask(i, null);
        if (attachedNonFinishingActivityForTask == null || attachedNonFinishingActivityForTask.mAssistToken != iBinder2) {
            Slog.w("VoiceInteractionServiceManager", "Unknown activity to perform a direct action");
            remoteCallback2.sendResult((Bundle) null);
            return;
        }
        try {
            attachedNonFinishingActivityForTask.mAppThread.performDirectAction(attachedNonFinishingActivityForTask.mActivityToken, str, bundle, remoteCallback, remoteCallback2);
        } catch (RemoteException e) {
            Slog.w("Unexpected remote error", e);
            remoteCallback2.sendResult((Bundle) null);
        }
    }

    public final void requestDirectActionsLocked(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        if (voiceInteractionSessionConnection == null || iBinder != voiceInteractionSessionConnection.mToken) {
            Slog.w("VoiceInteractionServiceManager", "requestDirectActionsLocked does not match active session");
            remoteCallback2.sendResult((Bundle) null);
            return;
        }
        ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getAttachedNonFinishingActivityForTask(i, null);
        if (attachedNonFinishingActivityForTask == null || attachedNonFinishingActivityForTask.mAssistToken != iBinder2) {
            Slog.w("VoiceInteractionServiceManager", "Unknown activity to query for direct actions");
            this.mDirectActionsHandler.sendMessageDelayed(PooledLambda.obtainMessage(new VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda0(), this, iBinder, Integer.valueOf(i), iBinder2, remoteCallback, remoteCallback2), 200L);
            return;
        }
        grantImplicitAccessLocked(attachedNonFinishingActivityForTask.mUid, null);
        try {
            attachedNonFinishingActivityForTask.mAppThread.requestDirectActions(attachedNonFinishingActivityForTask.mActivityToken, this.mActiveSession.mInteractor, remoteCallback, remoteCallback2);
        } catch (RemoteException e) {
            Slog.w("Unexpected remote error", e);
            remoteCallback2.sendResult((Bundle) null);
        }
    }

    public final void setDebugHotwordLoggingLocked(final boolean z) {
        if (this.mHotwordDetectionConnection == null) {
            Slog.w("VoiceInteractionServiceManager", "Failed to set temporary debug logging: no hotword detection active");
            return;
        }
        HotwordDetectionConnection hotwordDetectionConnection = this.mHotwordDetectionConnection;
        hotwordDetectionConnection.getClass();
        Slog.v("HotwordDetectionConnection", "setDebugHotwordLoggingLocked: " + z);
        ScheduledFuture scheduledFuture = hotwordDetectionConnection.mDebugHotwordLoggingTimeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            hotwordDetectionConnection.mDebugHotwordLoggingTimeoutFuture = null;
        }
        hotwordDetectionConnection.mDebugHotwordLogging = z;
        hotwordDetectionConnection.runForEachDetectorSessionLocked(new Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z2 = z;
                DetectorSession detectorSession = (DetectorSession) obj;
                detectorSession.getClass();
                Slog.v("DetectorSession", "setDebugHotwordLoggingLocked: " + z2);
                detectorSession.mDebugHotwordLogging = z2;
            }
        });
        if (z) {
            hotwordDetectionConnection.mDebugHotwordLoggingTimeoutFuture = hotwordDetectionConnection.mScheduledExecutorService.schedule(new HotwordDetectionConnection$$ExternalSyntheticLambda3(hotwordDetectionConnection, 0), ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        }
    }

    public final boolean showSessionLocked(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) {
        int i2;
        Bundle bundle2;
        int i3;
        boolean z;
        int i4;
        VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = this.mServiceStub;
        synchronized (voiceInteractionManagerServiceStub) {
            try {
                if (voiceInteractionManagerServiceStub.mShowSessionId == 2147483646) {
                    voiceInteractionManagerServiceStub.mShowSessionId = 0;
                }
                i2 = voiceInteractionManagerServiceStub.mShowSessionId + 1;
                voiceInteractionManagerServiceStub.mShowSessionId = i2;
            } finally {
            }
        }
        Bundle bundle3 = bundle == null ? new Bundle() : bundle;
        bundle3.putInt("android.service.voice.SHOW_SESSION_ID", i2);
        try {
            IVoiceInteractionService iVoiceInteractionService = this.mService;
            if (iVoiceInteractionService != null) {
                iVoiceInteractionService.prepareToShowSession(bundle3, i);
            }
        } catch (RemoteException e) {
            Slog.w("VoiceInteractionServiceManager", "RemoteException while calling prepareToShowSession", e);
        }
        if (this.mActiveSession == null) {
            bundle2 = bundle3;
            this.mActiveSession = new VoiceInteractionSessionConnection(this.mServiceStub, this.mSessionComponentName, this.mUser, this.mContext, this, this.mInfo.getServiceInfo().applicationInfo.uid, this.mHandler);
        } else {
            bundle2 = bundle3;
        }
        if (!this.mActiveSession.mBound) {
            try {
                if (this.mService != null) {
                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("android.service.voice.SHOW_SESSION_ID", i2);
                    this.mService.showSessionFailed(bundle4);
                }
            } catch (RemoteException e2) {
                Slog.w("VoiceInteractionServiceManager", "RemoteException while calling showSessionFailed", e2);
            }
        }
        List topVisibleActivities = ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getTopVisibleActivities();
        if (iBinder != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) topVisibleActivities;
            int size = arrayList2.size();
            int i5 = 0;
            while (true) {
                if (i5 >= size) {
                    break;
                }
                ActivityAssistInfo activityAssistInfo = (ActivityAssistInfo) arrayList2.get(i5);
                if (activityAssistInfo.mActivityToken == iBinder) {
                    arrayList.add(activityAssistInfo);
                    break;
                }
                i5++;
            }
            topVisibleActivities = arrayList;
        }
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        int i6 = this.mDisabledShowContext;
        if (!voiceInteractionSessionConnection.mBound) {
            if (iVoiceInteractionSessionShowCallback != null) {
                try {
                    iVoiceInteractionSessionShowCallback.onFailed();
                } catch (RemoteException unused) {
                }
            }
            return false;
        }
        if (!voiceInteractionSessionConnection.mFullyBound) {
            voiceInteractionSessionConnection.mFullyBound = voiceInteractionSessionConnection.mContext.bindServiceAsUser(voiceInteractionSessionConnection.mBindIntent, voiceInteractionSessionConnection.mFullConnection, 404226049, new UserHandle(voiceInteractionSessionConnection.mUser));
        }
        voiceInteractionSessionConnection.mShown = true;
        voiceInteractionSessionConnection.mShowArgs = bundle2;
        voiceInteractionSessionConnection.mShowFlags = i;
        int userDisabledShowContextLocked = i6 | voiceInteractionSessionConnection.getUserDisabledShowContextLocked();
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        boolean z4 = z2 || z3;
        if (z4) {
            int size2 = topVisibleActivities.size();
            ArrayList arrayList3 = new ArrayList(size2);
            for (int i7 = 0; i7 < size2; i7++) {
                arrayList3.add(((ActivityAssistInfo) topVisibleActivities.get(i7)).mActivityToken);
            }
            boolean z5 = (userDisabledShowContextLocked & 1) == 0;
            try {
                z = voiceInteractionSessionConnection.mActivityTaskManager.isAssistDataAllowed();
            } catch (RemoteException unused2) {
                z = false;
            }
            if (z5 && z) {
                ArrayList<? extends Parcelable> arrayList4 = new ArrayList<>(size2);
                for (int i8 = 0; i8 < size2; i8++) {
                    arrayList4.add(((ActivityAssistInfo) topVisibleActivities.get(i8)).mComponentName);
                }
                voiceInteractionSessionConnection.mShowArgs.putParcelableArrayList("android.service.voice.FOREGROUND_ACTIVITIES", arrayList4);
            }
            voiceInteractionSessionConnection.mAssistDataRequester.requestData(arrayList3, z2, z3, true, z5, (userDisabledShowContextLocked & 2) == 0, false, voiceInteractionSessionConnection.mCallingUid, voiceInteractionSessionConnection.mSessionComponentName.getPackageName(), str, InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE && ((i4 = voiceInteractionSessionConnection.mShowArgs.getInt("omni.entry_point")) == 65537 || i4 == 65538));
            AssistDataRequester assistDataRequester = voiceInteractionSessionConnection.mAssistDataRequester;
            if ((assistDataRequester.mPendingDataCount > 0 || assistDataRequester.mPendingScreenshotCount > 0) && AssistUtils.shouldDisclose(voiceInteractionSessionConnection.mContext, voiceInteractionSessionConnection.mSessionComponentName)) {
                voiceInteractionSessionConnection.mHandler.post(voiceInteractionSessionConnection.mShowAssistDisclosureRunnable);
            }
        }
        IVoiceInteractionSession iVoiceInteractionSession = voiceInteractionSessionConnection.mSession;
        if (iVoiceInteractionSession != null) {
            try {
                iVoiceInteractionSession.show(voiceInteractionSessionConnection.mShowArgs, voiceInteractionSessionConnection.mShowFlags, iVoiceInteractionSessionShowCallback);
                voiceInteractionSessionConnection.mShowArgs = null;
                i3 = 0;
                try {
                    voiceInteractionSessionConnection.mShowFlags = 0;
                } catch (RemoteException unused3) {
                }
            } catch (RemoteException unused4) {
                i3 = 0;
            }
            if (z4) {
                AssistDataRequester assistDataRequester2 = voiceInteractionSessionConnection.mAssistDataRequester;
                assistDataRequester2.flushPendingAssistData();
                assistDataRequester2.tryDispatchRequestComplete();
            } else {
                voiceInteractionSessionConnection.doHandleAssistWithoutData(topVisibleActivities);
            }
        } else {
            i3 = 0;
            if (iVoiceInteractionSessionShowCallback != null) {
                voiceInteractionSessionConnection.mPendingShowCallbacks.add(iVoiceInteractionSessionShowCallback);
            }
            if (!z4) {
                voiceInteractionSessionConnection.mPendingHandleAssistWithoutData = topVisibleActivities;
            }
        }
        VoiceInteractionSessionConnection.PowerBoostSetter powerBoostSetter = voiceInteractionSessionConnection.mSetPowerBoostRunnable;
        if (powerBoostSetter != null) {
            synchronized (VoiceInteractionSessionConnection.this.mLock) {
                powerBoostSetter.mCanceled = true;
            }
        }
        VoiceInteractionSessionConnection.PowerBoostSetter powerBoostSetter2 = voiceInteractionSessionConnection.new PowerBoostSetter(Instant.now().plusMillis(10000L));
        voiceInteractionSessionConnection.mSetPowerBoostRunnable = powerBoostSetter2;
        voiceInteractionSessionConnection.mFgHandler.post(powerBoostSetter2);
        LowPowerStandbyController.LocalService localService = voiceInteractionSessionConnection.mLowPowerStandbyControllerInternal;
        if (localService != null) {
            LowPowerStandbyController.m792$$Nest$maddToAllowlistInternal(LowPowerStandbyController.this, voiceInteractionSessionConnection.mCallingUid, 1);
            voiceInteractionSessionConnection.mLowPowerStandbyAllowlisted = true;
            voiceInteractionSessionConnection.mFgHandler.removeCallbacks(voiceInteractionSessionConnection.mRemoveFromLowPowerStandbyAllowlistRunnable);
            voiceInteractionSessionConnection.mFgHandler.postDelayed(voiceInteractionSessionConnection.mRemoveFromLowPowerStandbyAllowlistRunnable, 120000L);
        }
        VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub2 = ((VoiceInteractionManagerServiceImpl) voiceInteractionSessionConnection.mCallback).mServiceStub;
        synchronized (voiceInteractionManagerServiceStub2) {
            int beginBroadcast = VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.beginBroadcast();
            for (int i9 = i3; i9 < beginBroadcast; i9++) {
                try {
                    VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.getBroadcastItem(i9).onVoiceSessionShown();
                } catch (RemoteException e3) {
                    Slog.e("VoiceInteractionManager", "Error delivering voice interaction open event.", e3);
                }
            }
            VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.finishBroadcast();
        }
        return true;
    }

    public final void shutdownLocked() {
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        if (voiceInteractionSessionConnection != null) {
            voiceInteractionSessionConnection.cancelLocked(false);
            this.mActiveSession = null;
        }
        try {
            IVoiceInteractionService iVoiceInteractionService = this.mService;
            if (iVoiceInteractionService != null) {
                iVoiceInteractionService.shutdown();
            }
        } catch (RemoteException e) {
            Slog.w("VoiceInteractionServiceManager", "RemoteException in shutdown", e);
        }
        if (this.mHotwordDetectionConnection != null) {
            this.mHotwordDetectionConnection.cancelLocked();
            this.mAccessibilitySettingsListeners.remove(this.mHotwordDetectionConnection.mAccessibilitySettingsListener);
            this.mHotwordDetectionConnection = null;
        }
        if (this.mBound) {
            this.mContext.unbindService(this.mConnection);
            this.mBound = false;
        }
        if (this.mValid) {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        }
    }

    public final int startAssistantActivityLocked(String str, int i, int i2, IBinder iBinder, Intent intent, String str2, Bundle bundle) {
        try {
            VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
            if (voiceInteractionSessionConnection != null && iBinder == voiceInteractionSessionConnection.mToken) {
                if (!voiceInteractionSessionConnection.mShown) {
                    Slog.w("VoiceInteractionServiceManager", "startAssistantActivity not allowed on hidden session");
                    return -90;
                }
                Intent intent2 = new Intent(intent);
                intent2.addFlags(268435456);
                bundle.putInt("android.activity.activityType", 4);
                return this.mAtm.startAssistantActivity(this.mComponent.getPackageName(), str, i, i2, intent2, str2, bundle, this.mUser);
            }
            Slog.w("VoiceInteractionServiceManager", "startAssistantActivity does not match active session");
            return -89;
        } catch (RemoteException e) {
            throw new IllegalStateException("Unexpected remote error", e);
        }
    }

    public final void startListeningVisibleActivityChangedLocked(IBinder iBinder) {
        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
        if (voiceInteractionSessionConnection == null || iBinder != voiceInteractionSessionConnection.mToken) {
            Slog.w("VoiceInteractionServiceManager", "startListeningVisibleActivityChangedLocked does not match active session");
            return;
        }
        if (!voiceInteractionSessionConnection.mShown || voiceInteractionSessionConnection.mCanceled || voiceInteractionSessionConnection.mSession == null) {
            return;
        }
        voiceInteractionSessionConnection.mListeningVisibleActivity = true;
        voiceInteractionSessionConnection.mVisibleActivityInfoForToken.clear();
        ArrayMap topVisibleActivityInfosLocked = VoiceInteractionSessionConnection.getTopVisibleActivityInfosLocked();
        if (topVisibleActivityInfosLocked == null || topVisibleActivityInfosLocked.isEmpty()) {
            return;
        }
        if (!topVisibleActivityInfosLocked.isEmpty() && voiceInteractionSessionConnection.mSession != null) {
            for (int i = 0; i < topVisibleActivityInfosLocked.size(); i++) {
                try {
                    voiceInteractionSessionConnection.mSession.notifyVisibleActivityInfoChanged((VisibleActivityInfo) topVisibleActivityInfosLocked.valueAt(i), 1);
                } catch (RemoteException unused) {
                }
            }
        }
        voiceInteractionSessionConnection.mVisibleActivityInfoForToken.putAll(topVisibleActivityInfosLocked);
    }

    public final int startVoiceActivityLocked(String str, int i, int i2, IBinder iBinder, Intent intent, String str2) {
        try {
            VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mActiveSession;
            if (voiceInteractionSessionConnection != null && iBinder == voiceInteractionSessionConnection.mToken) {
                if (!voiceInteractionSessionConnection.mShown) {
                    Slog.w("VoiceInteractionServiceManager", "startVoiceActivity not allowed on hidden session");
                    return -100;
                }
                Intent intent2 = new Intent(intent);
                intent2.addCategory("android.intent.category.VOICE");
                intent2.addFlags(402653184);
                IActivityTaskManager iActivityTaskManager = this.mAtm;
                String packageName = this.mComponent.getPackageName();
                VoiceInteractionSessionConnection voiceInteractionSessionConnection2 = this.mActiveSession;
                return iActivityTaskManager.startVoiceActivity(packageName, str, i, i2, intent2, str2, voiceInteractionSessionConnection2.mSession, voiceInteractionSessionConnection2.mInteractor, 0, (ProfilerInfo) null, (Bundle) null, this.mUser);
            }
            Slog.w("VoiceInteractionServiceManager", "startVoiceActivity does not match active session");
            return -99;
        } catch (RemoteException e) {
            throw new IllegalStateException("Unexpected remote error", e);
        }
    }

    public final void stopListeningFromMicLocked() {
        SoftwareTrustedHotwordDetectorSession softwareTrustedHotwordDetectorSession;
        if (this.mHotwordDetectionConnection == null) {
            Slog.w("VoiceInteractionServiceManager", "stopListeningFromMicLocked() called but connection isn't established");
            return;
        }
        DetectorSession detectorSession = (DetectorSession) this.mHotwordDetectionConnection.mDetectorSessions.get(2);
        if (detectorSession == null || detectorSession.isDestroyed()) {
            Slog.v("HotwordDetectionConnection", "Not found the software detector");
            softwareTrustedHotwordDetectorSession = null;
        } else {
            softwareTrustedHotwordDetectorSession = (SoftwareTrustedHotwordDetectorSession) detectorSession;
        }
        if (softwareTrustedHotwordDetectorSession == null) {
            return;
        }
        if (!softwareTrustedHotwordDetectorSession.mPerformingSoftwareHotwordDetection) {
            Slog.i("SoftwareTrustedHotwordDetectorSession", "Hotword detection is not running");
            return;
        }
        softwareTrustedHotwordDetectorSession.mPerformingSoftwareHotwordDetection = false;
        softwareTrustedHotwordDetectorSession.mRemoteDetectionService.run(new SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda0());
        softwareTrustedHotwordDetectorSession.closeExternalAudioStreamLocked("stopping requested");
    }
}
