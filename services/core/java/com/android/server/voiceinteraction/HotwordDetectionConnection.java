package com.android.server.voiceinteraction;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.Identity;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SharedMemory;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.service.voice.HotwordDetectedResult;
import android.service.voice.HotwordDetectionServiceFailure;
import android.service.voice.IDetectorSessionStorageService;
import android.service.voice.ISandboxedDetectionService;
import android.service.voice.SoundTriggerFailure;
import android.service.voice.VisualQueryDetectionServiceFailure;
import android.service.voice.VoiceInteractionManagerInternal;
import android.speech.IRecognitionServiceManager;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.contentcapture.IContentCaptureManager;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.pm.permission.PermissionManagerService;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HotwordDetectionConnection {
    public static final boolean SYSPROP_VISUAL_QUERY_SERVICE_ENABLED = SystemProperties.getBoolean("ro.hotword.visual_query_service_enabled", false);
    public final AccessibilitySettingsListener mAccessibilitySettingsListener;
    public IBinder mAudioFlinger;
    public final HotwordDetectionConnection$$ExternalSyntheticLambda4 mAudioServerDeathRecipient;
    public final ScheduledFuture mCancellationTaskFuture;
    public final Context mContext;
    public boolean mDebugHotwordLogging;
    public ScheduledFuture mDebugHotwordLoggingTimeoutFuture;
    public final SparseArray mDetectorSessions;
    public int mDetectorType;
    public final ComponentName mHotwordDetectionComponentName;
    public final ServiceConnectionFactory mHotwordDetectionServiceConnectionFactory;
    public IHotwordRecognitionStatusCallback mHotwordRecognitionCallback;
    public volatile VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity mIdentity;
    public Instant mLastRestartInstant;
    public final Object mLock;
    public final AnonymousClass1 mOnOpChangedListener;
    public final int mReStartPeriodSeconds;
    public VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 mRemoteExceptionListener;
    public ServiceConnection mRemoteHotwordDetectionService;
    public ServiceConnection mRemoteVisualQueryDetectionService;
    public int mRestartCount;
    public final ScheduledThreadPoolExecutor mScheduledExecutorService;
    public final int mUserId;
    public final ComponentName mVisualQueryDetectionComponentName;
    public final ServiceConnectionFactory mVisualQueryDetectionServiceConnectionFactory;
    public final int mVoiceInteractionServiceUid;
    public final Identity mVoiceInteractorIdentity;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilitySettingsListener extends IVoiceInteractionAccessibilitySettingsListener.Stub {
        public AccessibilitySettingsListener() {
        }

        public final void onAccessibilityDetectionChanged(boolean z) {
            synchronized (HotwordDetectionConnection.this.mLock) {
                VisualQueryDetectorSession visualQueryDetectorSessionLocked = HotwordDetectionConnection.this.getVisualQueryDetectorSessionLocked();
                if (visualQueryDetectorSessionLocked != null) {
                    visualQueryDetectorSessionLocked.mEnableAccessibilityDataEgress = z;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class ServiceConnection extends ServiceConnector.Impl {
        private final int mBindingFlags;
        private final int mDetectionServiceType;
        private final int mInstanceNumber;
        private final Intent mIntent;
        private boolean mIsBound;
        private boolean mIsLoggedFirstConnect;
        private final Object mLock;
        private boolean mRespectServiceConnectionStatusChanged;

        public static void $r8$lambda$E52jDMeAIM1fqsgeezf_xP2TouE(ServiceConnection serviceConnection, DetectorSession detectorSession) {
            int i = serviceConnection.mDetectionServiceType;
            if (i == 1 && ((detectorSession instanceof DspTrustedHotwordDetectorSession) || (detectorSession instanceof SoftwareTrustedHotwordDetectorSession))) {
                detectorSession.reportErrorLocked(new HotwordDetectionServiceFailure(2, "Detection service is dead."));
            } else if (i == 2 && (detectorSession instanceof VisualQueryDetectorSession)) {
                detectorSession.reportErrorLocked(new VisualQueryDetectionServiceFailure(2, "Detection service is dead."));
            } else {
                detectorSession.reportErrorLocked("Detection service is dead with unknown detection service type.");
            }
        }

        public static void $r8$lambda$soeRIWrLUaITqTFZZ9ww9BHBGAM(ServiceConnection serviceConnection, DetectorSession detectorSession) {
            int i = serviceConnection.mDetectionServiceType;
            if (i == 1 && ((detectorSession instanceof DspTrustedHotwordDetectorSession) || (detectorSession instanceof SoftwareTrustedHotwordDetectorSession))) {
                detectorSession.reportErrorLocked(new HotwordDetectionServiceFailure(1, "Bind detection service failure."));
            } else if (i == 2 && (detectorSession instanceof VisualQueryDetectorSession)) {
                detectorSession.reportErrorLocked(new VisualQueryDetectionServiceFailure(1, "Bind detection service failure."));
            } else {
                detectorSession.reportErrorLocked("Bind detection service failure with unknown detection service type.");
            }
        }

        public ServiceConnection(Context context, Intent intent, int i, int i2, HotwordDetectionConnection$ServiceConnectionFactory$$ExternalSyntheticLambda0 hotwordDetectionConnection$ServiceConnectionFactory$$ExternalSyntheticLambda0, int i3, int i4) {
            super(context, intent, i, i2, hotwordDetectionConnection$ServiceConnectionFactory$$ExternalSyntheticLambda0);
            this.mLock = new Object();
            this.mRespectServiceConnectionStatusChanged = true;
            this.mIsBound = false;
            this.mIsLoggedFirstConnect = false;
            this.mIntent = intent;
            this.mBindingFlags = i;
            this.mInstanceNumber = i3;
            this.mDetectionServiceType = i4;
        }

        public final boolean bindService(android.content.ServiceConnection serviceConnection) {
            try {
                if (this.mDetectionServiceType != 2) {
                    HotwordDetectionConnection hotwordDetectionConnection = HotwordDetectionConnection.this;
                    HotwordMetricsLogger.writeDetectorEvent(hotwordDetectionConnection.mDetectorType, 1, hotwordDetectionConnection.mVoiceInteractionServiceUid);
                }
                boolean bindIsolatedService = ((ServiceConnector.Impl) this).mContext.bindIsolatedService(this.mIntent, this.mBindingFlags | 67108865, "hotword_detector_" + this.mInstanceNumber, ((ServiceConnector.Impl) this).mExecutor, serviceConnection);
                if (!bindIsolatedService) {
                    Slog.w("HotwordDetectionConnection", "bindService failure mDetectionServiceType = " + this.mDetectionServiceType);
                    synchronized (HotwordDetectionConnection.this.mLock) {
                        HotwordDetectionConnection.this.runForEachDetectorSessionLocked(new HotwordDetectionConnection$$ExternalSyntheticLambda0(1, this));
                    }
                    if (this.mDetectionServiceType != 2) {
                        HotwordDetectionConnection hotwordDetectionConnection2 = HotwordDetectionConnection.this;
                        HotwordMetricsLogger.writeDetectorEvent(hotwordDetectionConnection2.mDetectorType, 3, hotwordDetectionConnection2.mVoiceInteractionServiceUid);
                    }
                }
                return bindIsolatedService;
            } catch (IllegalArgumentException e) {
                if (this.mDetectionServiceType != 2) {
                    HotwordDetectionConnection hotwordDetectionConnection3 = HotwordDetectionConnection.this;
                    HotwordMetricsLogger.writeDetectorEvent(hotwordDetectionConnection3.mDetectorType, 3, hotwordDetectionConnection3.mVoiceInteractionServiceUid);
                }
                Slog.wtf("HotwordDetectionConnection", "Can't bind to the hotword detection service!", e);
                return false;
            }
        }

        public final void binderDied() {
            super.binderDied();
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("binderDied mDetectionServiceType = "), this.mDetectionServiceType, "HotwordDetectionConnection");
            synchronized (this.mLock) {
                try {
                    if (!this.mRespectServiceConnectionStatusChanged) {
                        Slog.v("HotwordDetectionConnection", "Ignored #binderDied event");
                        return;
                    }
                    synchronized (HotwordDetectionConnection.this.mLock) {
                        HotwordDetectionConnection.this.runForEachDetectorSessionLocked(new HotwordDetectionConnection$$ExternalSyntheticLambda0(2, this));
                    }
                    if (this.mDetectionServiceType != 2) {
                        HotwordDetectionConnection hotwordDetectionConnection = HotwordDetectionConnection.this;
                        HotwordMetricsLogger.writeKeyphraseTriggerEvent(hotwordDetectionConnection.mDetectorType, 4, hotwordDetectionConnection.mVoiceInteractionServiceUid);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final long getAutoDisconnectTimeoutMs() {
            return -1L;
        }

        public final void ignoreConnectionStatusEvents() {
            synchronized (this.mLock) {
                this.mRespectServiceConnectionStatusChanged = false;
            }
        }

        public final boolean isBound() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mIsBound;
            }
            return z;
        }

        public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
            synchronized (this.mLock) {
                try {
                    if (!this.mRespectServiceConnectionStatusChanged) {
                        Slog.v("HotwordDetectionConnection", "Ignored onServiceConnectionStatusChanged event");
                        return;
                    }
                    this.mIsBound = z;
                    if (z) {
                        if (!this.mIsLoggedFirstConnect) {
                            this.mIsLoggedFirstConnect = true;
                            if (this.mDetectionServiceType != 2) {
                                HotwordDetectionConnection hotwordDetectionConnection = HotwordDetectionConnection.this;
                                HotwordMetricsLogger.writeDetectorEvent(hotwordDetectionConnection.mDetectorType, 2, hotwordDetectionConnection.mVoiceInteractionServiceUid);
                            }
                        }
                    } else if (this.mDetectionServiceType != 2) {
                        HotwordDetectionConnection hotwordDetectionConnection2 = HotwordDetectionConnection.this;
                        HotwordMetricsLogger.writeDetectorEvent(hotwordDetectionConnection2.mDetectorType, 7, hotwordDetectionConnection2.mVoiceInteractionServiceUid);
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceConnectionFactory {
        public final int mBindingFlags;
        public final int mDetectionServiceType;
        public final Intent mIntent;

        public ServiceConnectionFactory(Intent intent, int i) {
            this.mIntent = intent;
            this.mDetectionServiceType = i;
            this.mBindingFlags = (!HotwordDetectionConnection.SYSPROP_VISUAL_QUERY_SERVICE_ENABLED || HotwordDetectionConnection.this.mVisualQueryDetectionComponentName == null || HotwordDetectionConnection.this.mHotwordDetectionComponentName == null) ? 0 : 8192;
        }

        public final ServiceConnection createLocked() {
            final HotwordDetectionConnection hotwordDetectionConnection = HotwordDetectionConnection.this;
            ServiceConnection serviceConnection = hotwordDetectionConnection.new ServiceConnection(hotwordDetectionConnection.mContext, this.mIntent, this.mBindingFlags, hotwordDetectionConnection.mUserId, new HotwordDetectionConnection$ServiceConnectionFactory$$ExternalSyntheticLambda0(), hotwordDetectionConnection.mRestartCount % 10, this.mDetectionServiceType);
            serviceConnection.connect();
            final IBinder iBinder = hotwordDetectionConnection.mAudioFlinger;
            final int i = 1;
            serviceConnection.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda9
                public final void runNoResult(Object obj) {
                    int i2 = i;
                    Object obj2 = iBinder;
                    switch (i2) {
                        case 0:
                            ((ISandboxedDetectionService) obj).updateContentCaptureManager((IContentCaptureManager) obj2, new ContentCaptureOptions((ArraySet) null));
                            break;
                        case 1:
                            ((ISandboxedDetectionService) obj).updateAudioFlinger((IBinder) obj2);
                            break;
                        default:
                            ((ISandboxedDetectionService) obj).updateRecognitionServiceManager((IRecognitionServiceManager) obj2);
                            break;
                    }
                }
            });
            final IContentCaptureManager asInterface = IContentCaptureManager.Stub.asInterface(ServiceManager.getService("content_capture"));
            final int i2 = 0;
            serviceConnection.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda9
                public final void runNoResult(Object obj) {
                    int i22 = i2;
                    Object obj2 = asInterface;
                    switch (i22) {
                        case 0:
                            ((ISandboxedDetectionService) obj).updateContentCaptureManager((IContentCaptureManager) obj2, new ContentCaptureOptions((ArraySet) null));
                            break;
                        case 1:
                            ((ISandboxedDetectionService) obj).updateAudioFlinger((IBinder) obj2);
                            break;
                        default:
                            ((ISandboxedDetectionService) obj).updateRecognitionServiceManager((IRecognitionServiceManager) obj2);
                            break;
                    }
                }
            });
            final IRecognitionServiceManager asInterface2 = IRecognitionServiceManager.Stub.asInterface(ServiceManager.getService("speech_recognition"));
            final int i3 = 2;
            serviceConnection.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda9
                public final void runNoResult(Object obj) {
                    int i22 = i3;
                    Object obj2 = asInterface2;
                    switch (i22) {
                        case 0:
                            ((ISandboxedDetectionService) obj).updateContentCaptureManager((IContentCaptureManager) obj2, new ContentCaptureOptions((ArraySet) null));
                            break;
                        case 1:
                            ((ISandboxedDetectionService) obj).updateAudioFlinger((IBinder) obj2);
                            break;
                        default:
                            ((ISandboxedDetectionService) obj).updateRecognitionServiceManager((IRecognitionServiceManager) obj2);
                            break;
                    }
                }
            });
            final int i4 = 1;
            serviceConnection.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda10
                public final void runNoResult(Object obj) {
                    int i5 = i4;
                    final HotwordDetectionConnection hotwordDetectionConnection2 = hotwordDetectionConnection;
                    ISandboxedDetectionService iSandboxedDetectionService = (ISandboxedDetectionService) obj;
                    hotwordDetectionConnection2.getClass();
                    switch (i5) {
                        case 0:
                            iSandboxedDetectionService.registerRemoteStorageService(new IDetectorSessionStorageService.Stub() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.3
                                public final void openFile(String str, AndroidFuture androidFuture) {
                                    Slog.v("HotwordDetectionConnection", "BinderCallback#onFileOpen");
                                    try {
                                        HotwordDetectionConnection.this.mHotwordRecognitionCallback.onOpenFile(str, androidFuture);
                                    } catch (RemoteException e) {
                                        e.rethrowFromSystemServer();
                                    }
                                }
                            });
                            break;
                        default:
                            iSandboxedDetectionService.ping(new IRemoteCallback.Stub() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.2
                                public final void sendResult(Bundle bundle) {
                                    int callingUid = Binder.getCallingUid();
                                    PermissionManagerService.this.mHotwordDetectionServiceProvider = new HotwordDetectionConnection$2$$ExternalSyntheticLambda0(callingUid);
                                    HotwordDetectionConnection.this.mIdentity = new VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity(callingUid, HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                                    HotwordDetectionConnection.this.mScheduledExecutorService.execute(new HotwordDetectionConnection$$ExternalSyntheticLambda7(callingUid, 1));
                                }
                            });
                            break;
                    }
                }
            });
            final int i5 = 0;
            serviceConnection.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda10
                public final void runNoResult(Object obj) {
                    int i52 = i5;
                    final HotwordDetectionConnection hotwordDetectionConnection2 = hotwordDetectionConnection;
                    ISandboxedDetectionService iSandboxedDetectionService = (ISandboxedDetectionService) obj;
                    hotwordDetectionConnection2.getClass();
                    switch (i52) {
                        case 0:
                            iSandboxedDetectionService.registerRemoteStorageService(new IDetectorSessionStorageService.Stub() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.3
                                public final void openFile(String str, AndroidFuture androidFuture) {
                                    Slog.v("HotwordDetectionConnection", "BinderCallback#onFileOpen");
                                    try {
                                        HotwordDetectionConnection.this.mHotwordRecognitionCallback.onOpenFile(str, androidFuture);
                                    } catch (RemoteException e) {
                                        e.rethrowFromSystemServer();
                                    }
                                }
                            });
                            break;
                        default:
                            iSandboxedDetectionService.ping(new IRemoteCallback.Stub() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.2
                                public final void sendResult(Bundle bundle) {
                                    int callingUid = Binder.getCallingUid();
                                    PermissionManagerService.this.mHotwordDetectionServiceProvider = new HotwordDetectionConnection$2$$ExternalSyntheticLambda0(callingUid);
                                    HotwordDetectionConnection.this.mIdentity = new VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity(callingUid, HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                                    HotwordDetectionConnection.this.mScheduledExecutorService.execute(new HotwordDetectionConnection$$ExternalSyntheticLambda7(callingUid, 1));
                                }
                            });
                            break;
                    }
                }
            });
            return serviceConnection;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundTriggerCallback extends IRecognitionStatusCallback.Stub {
        public final Context mContext;
        public final IHotwordRecognitionStatusCallback mExternalCallback;
        public final HotwordDetectionConnection mHotwordDetectionConnection;
        public final Identity mVoiceInteractorIdentity;

        public SoundTriggerCallback(Context context, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, HotwordDetectionConnection hotwordDetectionConnection, Identity identity) {
            this.mContext = context;
            this.mHotwordDetectionConnection = hotwordDetectionConnection;
            this.mExternalCallback = iHotwordRecognitionStatusCallback;
            this.mVoiceInteractorIdentity = identity;
        }

        public final void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            this.mExternalCallback.onGenericSoundTriggerDetected(genericRecognitionEvent);
        }

        public final void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
            if (this.mHotwordDetectionConnection != null) {
                FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTOR_KEYPHRASE_TRIGGERED, 1, 0, this.mVoiceInteractorIdentity.uid);
                HotwordDetectionConnection hotwordDetectionConnection = this.mHotwordDetectionConnection;
                IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback = this.mExternalCallback;
                boolean z = HotwordDetectionConnection.SYSPROP_VISUAL_QUERY_SERVICE_ENABLED;
                hotwordDetectionConnection.detectFromDspSource(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallback);
                return;
            }
            AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
            Identity identity = this.mVoiceInteractorIdentity;
            int noteOpNoThrow = appOpsManager.noteOpNoThrow(102, identity.uid, identity.packageName, identity.attributionTag, "Non-HDS keyphrase recognition to VoiceInteractionService");
            if (noteOpNoThrow != 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(noteOpNoThrow, "onKeyphraseDetected suppressed, permission check returned: ", "HotwordDetectionConnection");
                this.mExternalCallback.onRecognitionPaused();
            } else {
                HotwordMetricsLogger.writeKeyphraseTriggerEvent(0, 0, this.mVoiceInteractorIdentity.uid);
                this.mExternalCallback.onKeyphraseDetected(keyphraseRecognitionEvent, (HotwordDetectedResult) null);
            }
        }

        public final void onModuleDied() {
            this.mExternalCallback.onSoundTriggerFailure(new SoundTriggerFailure(1, "STHAL died"));
        }

        public final void onPauseFailed(int i) {
            this.mExternalCallback.onSoundTriggerFailure(new SoundTriggerFailure(2, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "STService recognition pause failed with: ")));
        }

        public final void onPreempted() {
            this.mExternalCallback.onSoundTriggerFailure(new SoundTriggerFailure(3, "Unexpected startRecognition on already started ST session"));
        }

        public final void onRecognitionPaused() {
            this.mExternalCallback.onRecognitionPaused();
        }

        public final void onRecognitionResumed() {
            this.mExternalCallback.onRecognitionResumed();
        }

        public final void onResumeFailed(int i) {
            this.mExternalCallback.onSoundTriggerFailure(new SoundTriggerFailure(2, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "STService recognition resume failed with: ")));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.app.AppOpsManager$OnOpChangedListener, com.android.server.voiceinteraction.HotwordDetectionConnection$1] */
    public HotwordDetectionConnection(Object obj, Context context, int i, Identity identity, ComponentName componentName, ComponentName componentName2, int i2, int i3, VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        this.mScheduledExecutorService = scheduledThreadPoolExecutor;
        this.mAudioServerDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda4
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                int i4;
                HotwordDetectionConnection hotwordDetectionConnection = HotwordDetectionConnection.this;
                hotwordDetectionConnection.getClass();
                Slog.w("HotwordDetectionConnection", "Audio server died; restarting the HotwordDetectionService.");
                hotwordDetectionConnection.initAudioFlinger();
                synchronized (hotwordDetectionConnection.mLock) {
                    try {
                        hotwordDetectionConnection.restartProcessLocked();
                        int i5 = hotwordDetectionConnection.mDetectorType;
                        if (i5 != 3) {
                            int i6 = hotwordDetectionConnection.mVoiceInteractionServiceUid;
                            if (i5 != 1) {
                                i4 = 2;
                                if (i5 != 2) {
                                    i4 = 0;
                                }
                            } else {
                                i4 = 1;
                            }
                            FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, i4, 1, i6);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mDebugHotwordLoggingTimeoutFuture = null;
        this.mRestartCount = 0;
        this.mDebugHotwordLogging = false;
        this.mDetectorSessions = new SparseArray();
        ?? r4 = new AppOpsManager.OnOpChangedListener() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.1
            @Override // android.app.AppOpsManager.OnOpChangedListener
            public final void onOpChanged(String str, String str2) {
                if (str.equals("android:receive_sandbox_trigger_audio")) {
                    AppOpsManager appOpsManager = (AppOpsManager) HotwordDetectionConnection.this.mContext.getSystemService(AppOpsManager.class);
                    synchronized (HotwordDetectionConnection.this.mLock) {
                        try {
                            Identity identity2 = HotwordDetectionConnection.this.mVoiceInteractorIdentity;
                            if (appOpsManager.unsafeCheckOpNoThrow("android:receive_sandbox_trigger_audio", identity2.uid, identity2.packageName) == 2) {
                                Slog.i("HotwordDetectionConnection", "Shutdown hotword detection service on voice activation op disabled.");
                                HotwordDetectionConnection.this.safelyShutdownHotwordDetectionOnVoiceActivationDisabledLocked();
                            }
                        } finally {
                        }
                    }
                }
            }
        };
        this.mOnOpChangedListener = r4;
        this.mLock = obj;
        this.mContext = context;
        this.mVoiceInteractionServiceUid = i;
        this.mVoiceInteractorIdentity = identity;
        this.mHotwordDetectionComponentName = componentName;
        this.mVisualQueryDetectionComponentName = componentName2;
        this.mUserId = i2;
        this.mDetectorType = i3;
        this.mRemoteExceptionListener = voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1;
        int i4 = DeviceConfig.getInt("voice_interaction", "restart_period_in_seconds", 0);
        this.mReStartPeriodSeconds = i4;
        this.mAccessibilitySettingsListener = new AccessibilitySettingsListener();
        Intent intent = new Intent("android.service.voice.HotwordDetectionService");
        intent.setComponent(componentName);
        Intent intent2 = new Intent("android.service.voice.VisualQueryDetectionService");
        intent2.setComponent(componentName2);
        initAudioFlinger();
        this.mHotwordDetectionServiceConnectionFactory = new ServiceConnectionFactory(intent, 1);
        this.mVisualQueryDetectionServiceConnectionFactory = new ServiceConnectionFactory(intent2, 2);
        this.mLastRestartInstant = Instant.now();
        ((AppOpsManager) context.getSystemService(AppOpsManager.class)).startWatchingMode(136, identity.packageName, (AppOpsManager.OnOpChangedListener) r4);
        if (i4 <= 0) {
            this.mCancellationTaskFuture = null;
            return;
        }
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        long j = i4;
        this.mCancellationTaskFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(new HotwordDetectionConnection$$ExternalSyntheticLambda3(this, 1), j, j, TimeUnit.SECONDS);
    }

    public final void cancelLocked() {
        Slog.v("HotwordDetectionConnection", "cancelLocked");
        ScheduledFuture scheduledFuture = this.mDebugHotwordLoggingTimeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.mDebugHotwordLoggingTimeoutFuture = null;
        }
        this.mRemoteExceptionListener = null;
        runForEachDetectorSessionLocked(new HotwordDetectionConnection$$ExternalSyntheticLambda1(0));
        this.mDetectorSessions.clear();
        this.mDebugHotwordLogging = false;
        ServiceConnection serviceConnection = this.mRemoteVisualQueryDetectionService;
        if (serviceConnection != null) {
            serviceConnection.unbind();
            this.mRemoteVisualQueryDetectionService = null;
        }
        resetDetectionProcessIdentityIfEmptyLocked();
        ServiceConnection serviceConnection2 = this.mRemoteHotwordDetectionService;
        if (serviceConnection2 != null) {
            serviceConnection2.unbind();
            this.mRemoteHotwordDetectionService = null;
        }
        resetDetectionProcessIdentityIfEmptyLocked();
        ScheduledFuture scheduledFuture2 = this.mCancellationTaskFuture;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
        }
        IBinder iBinder = this.mAudioFlinger;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this.mAudioServerDeathRecipient, 0);
            this.mAudioFlinger = null;
        }
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).stopWatchingMode(this.mOnOpChangedListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0024 A[Catch: all -> 0x005e, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x000f, B:9:0x0016, B:11:0x0024, B:12:0x0026, B:19:0x0041, B:20:0x005c, B:29:0x0061, B:30:0x0062, B:31:0x0069, B:33:0x001a, B:16:0x002a, B:24:0x002f, B:25:0x003d), top: B:3:0x0003, inners: #0 }] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void detectFromDspSource(final android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent r11, final com.android.internal.app.IHotwordRecognitionStatusCallback r12) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            android.util.SparseArray r10 = r10.mDetectorSessions     // Catch: java.lang.Throwable -> L5e
            r1 = 1
            java.lang.Object r10 = r10.get(r1)     // Catch: java.lang.Throwable -> L5e
            com.android.server.voiceinteraction.DetectorSession r10 = (com.android.server.voiceinteraction.DetectorSession) r10     // Catch: java.lang.Throwable -> L5e
            r2 = 0
            if (r10 == 0) goto L1a
            boolean r3 = r10.isDestroyed()     // Catch: java.lang.Throwable -> L5e
            if (r3 == 0) goto L16
            goto L1a
        L16:
            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession r10 = (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession) r10     // Catch: java.lang.Throwable -> L5e
            r5 = r10
            goto L22
        L1a:
            java.lang.String r10 = "HotwordDetectionConnection"
            java.lang.String r3 = "Not found the Dsp detector"
            android.util.Slog.v(r10, r3)     // Catch: java.lang.Throwable -> L5e
            r5 = r2
        L22:
            if (r5 == 0) goto L62
            java.lang.Object r10 = r5.mLock     // Catch: java.lang.Throwable -> L5e
            monitor-enter(r10)     // Catch: java.lang.Throwable -> L5e
            r3 = 0
            if (r12 != 0) goto L2f
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L2d
            r4 = r3
            goto L3e
        L2d:
            r11 = move-exception
            goto L60
        L2f:
            com.android.internal.app.IHotwordRecognitionStatusCallback r4 = r5.mCallback     // Catch: java.lang.Throwable -> L2d
            android.os.IBinder r4 = r4.asBinder()     // Catch: java.lang.Throwable -> L2d
            android.os.IBinder r6 = r12.asBinder()     // Catch: java.lang.Throwable -> L2d
            boolean r4 = r4.equals(r6)     // Catch: java.lang.Throwable -> L2d
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L2d
        L3e:
            if (r4 != 0) goto L41
            goto L62
        L41:
            java.util.concurrent.atomic.AtomicBoolean r6 = new java.util.concurrent.atomic.AtomicBoolean     // Catch: java.lang.Throwable -> L5e
            r6.<init>(r3)     // Catch: java.lang.Throwable -> L5e
            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$1 r9 = new com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$1     // Catch: java.lang.Throwable -> L5e
            r9.<init>()     // Catch: java.lang.Throwable -> L5e
            r5.mValidatingDspTrigger = r1     // Catch: java.lang.Throwable -> L5e
            r5.mLastHotwordRejectedResult = r2     // Catch: java.lang.Throwable -> L5e
            com.android.server.voiceinteraction.HotwordDetectionConnection$ServiceConnection r10 = r5.mRemoteDetectionService     // Catch: java.lang.Throwable -> L5e
            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$$ExternalSyntheticLambda0 r1 = new com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L5e
            r4 = r1
            r7 = r12
            r8 = r11
            r4.<init>()     // Catch: java.lang.Throwable -> L5e
            r10.run(r1)     // Catch: java.lang.Throwable -> L5e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            return
        L5e:
            r10 = move-exception
            goto L6b
        L60:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L2d
            throw r11     // Catch: java.lang.Throwable -> L5e
        L62:
            java.lang.String r10 = "HotwordDetectionConnection"
            java.lang.String r11 = "Not found the Dsp detector by callback"
            android.util.Slog.v(r10, r11)     // Catch: java.lang.Throwable -> L5e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            return
        L6b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.voiceinteraction.HotwordDetectionConnection.detectFromDspSource(android.hardware.soundtrigger.SoundTrigger$KeyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback):void");
    }

    public final DetectorSession getDetectorSessionByTokenLocked(IBinder iBinder) {
        boolean z;
        if (iBinder == null) {
            return null;
        }
        for (int i = 0; i < this.mDetectorSessions.size(); i++) {
            DetectorSession detectorSession = (DetectorSession) this.mDetectorSessions.valueAt(i);
            if (!detectorSession.isDestroyed()) {
                synchronized (detectorSession.mLock) {
                    z = detectorSession.mToken == iBinder;
                }
                if (z) {
                    return detectorSession;
                }
            }
        }
        return null;
    }

    public final VisualQueryDetectorSession getVisualQueryDetectorSessionLocked() {
        DetectorSession detectorSession = (DetectorSession) this.mDetectorSessions.get(3);
        if (detectorSession != null && !detectorSession.isDestroyed()) {
            return (VisualQueryDetectorSession) detectorSession;
        }
        Slog.v("HotwordDetectionConnection", "Not found the visual query detector");
        return null;
    }

    public final void initAudioFlinger() {
        IBinder waitForService = ServiceManager.waitForService("media.audio_flinger");
        if (waitForService == null) {
            synchronized (this.mLock) {
                this.mAudioFlinger = null;
            }
            throw new IllegalStateException("Service media.audio_flinger wasn't found.");
        }
        try {
            waitForService.linkToDeath(this.mAudioServerDeathRecipient, 0);
            synchronized (this.mLock) {
                this.mAudioFlinger = waitForService;
            }
        } catch (RemoteException e) {
            Slog.w("HotwordDetectionConnection", "Audio server died before we registered a DeathRecipient; retrying init.", e);
            initAudioFlinger();
        }
    }

    public final void resetDetectionProcessIdentityIfEmptyLocked() {
        if (this.mRemoteHotwordDetectionService == null && this.mRemoteVisualQueryDetectionService == null) {
            PermissionManagerService.this.mHotwordDetectionServiceProvider = null;
            if (this.mIdentity != null) {
                this.mScheduledExecutorService.execute(new HotwordDetectionConnection$$ExternalSyntheticLambda7(this.mIdentity.getIsolatedUid(), 0));
            }
            this.mIdentity = null;
        }
    }

    public final void restartProcessLocked() {
        Slog.v("HotwordDetectionConnection", "Restarting hotword detection process");
        ServiceConnection serviceConnection = this.mRemoteHotwordDetectionService;
        ServiceConnection serviceConnection2 = this.mRemoteVisualQueryDetectionService;
        VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = this.mIdentity;
        this.mLastRestartInstant = Instant.now();
        this.mRestartCount++;
        if (serviceConnection != null) {
            this.mRemoteHotwordDetectionService = this.mHotwordDetectionServiceConnectionFactory.createLocked();
        }
        if (serviceConnection2 != null) {
            this.mRemoteVisualQueryDetectionService = this.mVisualQueryDetectionServiceConnectionFactory.createLocked();
        }
        Slog.v("HotwordDetectionConnection", "Started the new process, dispatching processRestarted to detector");
        runForEachDetectorSessionLocked(new HotwordDetectionConnection$$ExternalSyntheticLambda0(0, this));
        if (serviceConnection != null) {
            serviceConnection.ignoreConnectionStatusEvents();
            serviceConnection.unbind();
        }
        if (serviceConnection2 != null) {
            serviceConnection2.ignoreConnectionStatusEvents();
            serviceConnection2.unbind();
        }
        if (hotwordDetectionServiceIdentity != null) {
            this.mScheduledExecutorService.execute(new HotwordDetectionConnection$$ExternalSyntheticLambda7(hotwordDetectionServiceIdentity.getIsolatedUid(), 0));
        }
    }

    public final void runForEachDetectorSessionLocked(Consumer consumer) {
        for (int i = 0; i < this.mDetectorSessions.size(); i++) {
            consumer.accept((DetectorSession) this.mDetectorSessions.valueAt(i));
        }
    }

    public final void safelyShutdownHotwordDetectionOnVoiceActivationDisabledLocked() {
        Slog.v("HotwordDetectionConnection", "safelyShutdownHotwordDetectionOnVoiceActivationDisabled");
        try {
            ScheduledFuture scheduledFuture = this.mDebugHotwordLoggingTimeoutFuture;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                this.mDebugHotwordLoggingTimeoutFuture = null;
            }
            this.mRemoteExceptionListener = null;
            runForEachDetectorSessionLocked(new HotwordDetectionConnection$$ExternalSyntheticLambda1(1));
            this.mDetectorSessions.delete(1);
            this.mDetectorSessions.delete(2);
            this.mDebugHotwordLogging = false;
            ServiceConnection serviceConnection = this.mRemoteHotwordDetectionService;
            if (serviceConnection != null) {
                serviceConnection.unbind();
                this.mRemoteHotwordDetectionService = null;
            }
            resetDetectionProcessIdentityIfEmptyLocked();
            ScheduledFuture scheduledFuture2 = this.mCancellationTaskFuture;
            if (scheduledFuture2 != null) {
                scheduledFuture2.cancel(true);
            }
            IBinder iBinder = this.mAudioFlinger;
            if (iBinder != null) {
                iBinder.unlinkToDeath(this.mAudioServerDeathRecipient, 0);
                this.mAudioFlinger = null;
            }
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("Swallowing error while shutting down hotword detection.Error message: "), "HotwordDetectionConnection");
        }
    }

    public final void updateStateLocked(final PersistableBundle persistableBundle, final SharedMemory sharedMemory, IBinder iBinder) {
        DetectorSession detectorSessionByTokenLocked = getDetectorSessionByTokenLocked(iBinder);
        if (detectorSessionByTokenLocked == null) {
            Slog.v("HotwordDetectionConnection", "Not found the detector by token");
            return;
        }
        Instant instant = this.mLastRestartInstant;
        if (detectorSessionByTokenLocked.getDetectorType() != 3) {
            HotwordMetricsLogger.writeDetectorEvent(detectorSessionByTokenLocked.getDetectorType(), 8, detectorSessionByTokenLocked.mVoiceInteractionServiceUid);
        }
        if (detectorSessionByTokenLocked.mUpdateStateAfterStartFinished.get() || !Instant.now().minus((TemporalAmount) DetectorSession.MAX_UPDATE_TIMEOUT_DURATION).isBefore(instant)) {
            detectorSessionByTokenLocked.mRemoteDetectionService.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda5
                public final void runNoResult(Object obj) {
                    ((ISandboxedDetectionService) obj).updateState(persistableBundle, sharedMemory, (IRemoteCallback) null);
                }
            });
            return;
        }
        Slog.v("DetectorSession", "call updateStateAfterProcessStartLocked");
        if (detectorSessionByTokenLocked.mRemoteDetectionService.postAsync(new DetectorSession$$ExternalSyntheticLambda1(detectorSessionByTokenLocked, persistableBundle, sharedMemory)).whenComplete(new DetectorSession$$ExternalSyntheticLambda2(detectorSessionByTokenLocked)) == null) {
            Slog.w("DetectorSession", "Failed to create AndroidFuture");
        }
    }
}
