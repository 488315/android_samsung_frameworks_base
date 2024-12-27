package com.android.server.voiceinteraction;

import android.app.AppOpsManager;
import android.app.compat.CompatChanges;
import android.attention.AttentionManagerInternal;
import android.content.Context;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.media.permission.PermissionUtil;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.service.voice.HotwordDetectedResult;
import android.service.voice.HotwordDetectionServiceFailure;
import android.service.voice.HotwordDetector;
import android.service.voice.HotwordRejectedResult;
import android.service.voice.IDspHotwordDetectionCallback;
import android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback;
import android.service.voice.ISandboxedDetectionService;
import android.service.voice.VisualQueryDetectionServiceFailure;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.hidden_from_bootclasspath.android.app.wearable.Flags;
import com.android.internal.infra.ServiceConnector;
import com.android.server.LocalServices;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.voiceinteraction.HotwordDetectionConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DetectorSession {
    public static final Duration MAX_UPDATE_TIMEOUT_DURATION = Duration.ofMillis(30000);
    public final AppOpsManager mAppOpsManager;
    public final AttentionManagerInternal mAttentionManagerInternal;
    public final IHotwordRecognitionStatusCallback mCallback;
    public final Context mContext;
    public ParcelFileDescriptor mCurrentAudioSink;
    public boolean mDebugHotwordLogging;
    public boolean mDestroyed;
    public final HotwordAudioStreamCopier mHotwordAudioStreamCopier;
    public boolean mInitialized;
    public final Object mLock;
    public boolean mPerformingExternalSourceHotwordDetection;
    public final DetectorSession$$ExternalSyntheticLambda0 mProximityCallbackInternal;
    public double mProximityMeters;
    public HotwordDetectionConnection.ServiceConnection mRemoteDetectionService;
    public VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 mRemoteExceptionListener;
    public final ScheduledExecutorService mScheduledExecutorService;
    public final IBinder mToken;
    public final int mUserId;
    public final int mVoiceInteractionServiceUid;
    public final Identity mVoiceInteractorIdentity;
    public final Executor mAudioCopyExecutor = Executors.newCachedThreadPool();
    public final AtomicBoolean mUpdateStateAfterStartFinished = new AtomicBoolean(false);

    /* JADX WARN: Type inference failed for: r5v0, types: [android.attention.AttentionManagerInternal$ProximityUpdateCallbackInternal, com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda0] */
    public DetectorSession(HotwordDetectionConnection.ServiceConnection serviceConnection, Object obj, Context context, IBinder iBinder, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, Identity identity, ScheduledExecutorService scheduledExecutorService, boolean z, VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1, int i2) {
        this.mAttentionManagerInternal = null;
        ?? r5 = new AttentionManagerInternal.ProximityUpdateCallbackInternal() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda0
            public final void onProximityUpdate(double d) {
                DetectorSession detectorSession = DetectorSession.this;
                synchronized (detectorSession.mLock) {
                    detectorSession.mProximityMeters = d;
                }
            }
        };
        this.mProximityCallbackInternal = r5;
        this.mDebugHotwordLogging = false;
        this.mProximityMeters = -1.0d;
        this.mInitialized = false;
        this.mDestroyed = false;
        this.mRemoteExceptionListener = voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1;
        this.mRemoteDetectionService = serviceConnection;
        this.mLock = obj;
        this.mContext = context;
        this.mToken = iBinder;
        this.mUserId = i2;
        this.mCallback = iHotwordRecognitionStatusCallback;
        this.mVoiceInteractionServiceUid = i;
        this.mVoiceInteractorIdentity = identity;
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAppOpsManager = appOpsManager;
        if (getDetectorType() != 3) {
            this.mHotwordAudioStreamCopier = new HotwordAudioStreamCopier(appOpsManager, getDetectorType(), identity.uid, identity.packageName, identity.attributionTag);
        } else {
            this.mHotwordAudioStreamCopier = null;
        }
        this.mScheduledExecutorService = scheduledExecutorService;
        this.mDebugHotwordLogging = z;
        AttentionManagerInternal attentionManagerInternal = (AttentionManagerInternal) LocalServices.getService(AttentionManagerInternal.class);
        this.mAttentionManagerInternal = attentionManagerInternal;
        if (attentionManagerInternal == null || !attentionManagerInternal.isProximitySupported()) {
            return;
        }
        attentionManagerInternal.onStartProximityUpdates((AttentionManagerInternal.ProximityUpdateCallbackInternal) r5);
    }

    public static void enforcePermissionForDataDelivery(Context context, Identity identity, String str, String str2) {
        if (PermissionUtil.checkPermissionForDataDelivery(context, identity, str, str2) != 0) {
            throw new SecurityException(TextUtils.formatSimple("Failed to obtain permission %s for identity %s", new Object[]{str, identity}));
        }
    }

    public final void closeExternalAudioStreamLocked(String str) {
        if (this.mCurrentAudioSink != null) {
            Slog.i("DetectorSession", "Closing external audio stream to hotword detector: ".concat(str));
            try {
                this.mCurrentAudioSink.close();
            } catch (IOException unused) {
            }
            this.mCurrentAudioSink = null;
        }
    }

    public final void destroyLocked() {
        this.mDestroyed = true;
        this.mDebugHotwordLogging = false;
        this.mRemoteDetectionService = null;
        this.mRemoteExceptionListener = null;
        AttentionManagerInternal attentionManagerInternal = this.mAttentionManagerInternal;
        if (attentionManagerInternal != null) {
            attentionManagerInternal.onStopProximityUpdates(this.mProximityCallbackInternal);
        }
    }

    public void dumpLocked(PrintWriter printWriter) {
        printWriter.print("    ");
        printWriter.print("mCallback=");
        printWriter.println(this.mCallback);
        printWriter.print("    ");
        printWriter.print("mUpdateStateAfterStartFinished=");
        printWriter.println(this.mUpdateStateAfterStartFinished);
        printWriter.print("    ");
        printWriter.print("mInitialized=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, "    ", "mDestroyed=", this.mInitialized);
        printWriter.println(this.mDestroyed);
        printWriter.print("    ");
        printWriter.print("DetectorType=");
        printWriter.println(HotwordDetector.detectorTypeToString(getDetectorType()));
        printWriter.print("    ");
        printWriter.print("mPerformingExternalSourceHotwordDetection=");
        printWriter.println(this.mPerformingExternalSourceHotwordDetection);
    }

    public final void enforceExtraKeyphraseIdNotLeaked(HotwordDetectedResult hotwordDetectedResult, SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
        if (CompatChanges.isChangeEnabled(215066299L, this.mVoiceInteractionServiceUid)) {
            for (SoundTrigger.KeyphraseRecognitionExtra keyphraseRecognitionExtra : keyphraseRecognitionEvent.keyphraseExtras) {
                if (keyphraseRecognitionExtra.getKeyphraseId() == hotwordDetectedResult.getHotwordPhraseId()) {
                    return;
                }
            }
            throw new SecurityException("Ignoring #onDetected due to trusted service sharing a keyphrase ID which the DSP did not detect");
        }
    }

    public final int getDetectorType() {
        if (this instanceof DspTrustedHotwordDetectorSession) {
            return 1;
        }
        if (this instanceof SoftwareTrustedHotwordDetectorSession) {
            return 2;
        }
        if (this instanceof VisualQueryDetectorSession) {
            return 3;
        }
        Slog.v("DetectorSession", "Unexpected detector type");
        return -1;
    }

    public final void handleExternalSourceHotwordDetectionLocked(ParcelFileDescriptor parcelFileDescriptor, final AudioFormat audioFormat, final PersistableBundle persistableBundle, final IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback, final boolean z, final boolean z2) {
        Pair pair;
        if (this.mPerformingExternalSourceHotwordDetection) {
            Slog.i("DetectorSession", "Hotword validation is already in progress for external source.");
            return;
        }
        final ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        try {
            ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            pair = Pair.create(createPipe[0], createPipe[1]);
        } catch (IOException e) {
            Slog.e("DetectorSession", "Failed to create audio stream pipe", e);
            pair = null;
        }
        if (pair == null) {
            return;
        }
        final ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) pair.second;
        final ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) pair.first;
        this.mCurrentAudioSink = parcelFileDescriptor2;
        this.mPerformingExternalSourceHotwordDetection = true;
        this.mAudioCopyExecutor.execute(new Runnable() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DetectorSession detectorSession = DetectorSession.this;
                InputStream inputStream = autoCloseInputStream;
                ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptor2;
                IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback2 = iMicrophoneHotwordDetectionVoiceInteractionCallback;
                detectorSession.getClass();
                try {
                    try {
                        try {
                            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor4);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int read = inputStream.read(bArr, 0, 1024);
                                    if (read < 0) {
                                        Slog.i("DetectorSession", "Reached end of stream for external hotword");
                                        autoCloseOutputStream.close();
                                        inputStream.close();
                                        synchronized (detectorSession.mLock) {
                                            detectorSession.mPerformingExternalSourceHotwordDetection = false;
                                            detectorSession.closeExternalAudioStreamLocked("start external source");
                                        }
                                        return;
                                    }
                                    autoCloseOutputStream.write(bArr, 0, read);
                                }
                            } finally {
                            }
                        } catch (Throwable th) {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        synchronized (detectorSession.mLock) {
                            detectorSession.mPerformingExternalSourceHotwordDetection = false;
                            detectorSession.closeExternalAudioStreamLocked("start external source");
                            throw th3;
                        }
                    }
                } catch (IOException e2) {
                    Slog.w("DetectorSession", "Failed supplying audio data to validator", e2);
                    try {
                        iMicrophoneHotwordDetectionVoiceInteractionCallback2.onHotwordDetectionServiceFailure(new HotwordDetectionServiceFailure(3, "Copy audio data failure for external source detection."));
                    } catch (RemoteException e3) {
                        Slog.w("DetectorSession", "Failed to report onHotwordDetectionServiceFailure status: " + e3);
                        if (detectorSession.getDetectorType() != 3) {
                            HotwordMetricsLogger.writeDetectorEvent(detectorSession.getDetectorType(), 15, detectorSession.mVoiceInteractionServiceUid);
                        }
                        detectorSession.notifyOnDetectorRemoteException();
                    }
                    synchronized (detectorSession.mLock) {
                        detectorSession.mPerformingExternalSourceHotwordDetection = false;
                        detectorSession.closeExternalAudioStreamLocked("start external source");
                    }
                }
            }
        });
        this.mRemoteDetectionService.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.DetectorSession$$ExternalSyntheticLambda4
            public final void runNoResult(Object obj) {
                final DetectorSession detectorSession = DetectorSession.this;
                PersistableBundle persistableBundle2 = persistableBundle;
                final boolean z3 = z;
                ParcelFileDescriptor parcelFileDescriptor4 = parcelFileDescriptor3;
                AudioFormat audioFormat2 = audioFormat;
                final ParcelFileDescriptor parcelFileDescriptor5 = parcelFileDescriptor2;
                final InputStream inputStream = autoCloseInputStream;
                final IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback2 = iMicrophoneHotwordDetectionVoiceInteractionCallback;
                final boolean z4 = z2;
                ISandboxedDetectionService iSandboxedDetectionService = (ISandboxedDetectionService) obj;
                detectorSession.getClass();
                if (Flags.enableHotwordWearableSensingApi()) {
                    if (persistableBundle2 == null) {
                        persistableBundle2 = new PersistableBundle();
                    }
                    persistableBundle2.putBoolean("android.service.voice.HotwordDetectionService.KEY_SYSTEM_WILL_CLOSE_AUDIO_STREAM_AFTER_CALLBACK", z3);
                }
                iSandboxedDetectionService.detectFromMicrophoneSource(parcelFileDescriptor4, 2, audioFormat2, persistableBundle2, new IDspHotwordDetectionCallback.Stub() { // from class: com.android.server.voiceinteraction.DetectorSession.3
                    public final void onDetected(HotwordDetectedResult hotwordDetectedResult) {
                        synchronized (DetectorSession.this.mLock) {
                            try {
                                DetectorSession detectorSession2 = DetectorSession.this;
                                detectorSession2.mPerformingExternalSourceHotwordDetection = false;
                                HotwordMetricsLogger.writeDetectorEvent(detectorSession2.getDetectorType(), 11, DetectorSession.this.mVoiceInteractionServiceUid);
                                if (z3) {
                                    DetectorSession.this.mScheduledExecutorService.schedule(new DetectorSession$3$$ExternalSyntheticLambda0(parcelFileDescriptor5, inputStream, 0), 2000L, TimeUnit.MILLISECONDS);
                                }
                                if (z4) {
                                    try {
                                        DetectorSession detectorSession3 = DetectorSession.this;
                                        detectorSession3.getClass();
                                        Binder.withCleanCallingIdentity(new DetectorSession$$ExternalSyntheticLambda6(detectorSession3));
                                    } catch (SecurityException e2) {
                                        Slog.w("DetectorSession", "Ignoring #onDetected due to a SecurityException", e2);
                                        HotwordMetricsLogger.writeDetectorEvent(DetectorSession.this.getDetectorType(), 13, DetectorSession.this.mVoiceInteractionServiceUid);
                                        try {
                                            iMicrophoneHotwordDetectionVoiceInteractionCallback2.onHotwordDetectionServiceFailure(new HotwordDetectionServiceFailure(5, "Security exception occurs in #onDetected method"));
                                            return;
                                        } catch (RemoteException e3) {
                                            DetectorSession.this.notifyOnDetectorRemoteException();
                                            throw e3;
                                        }
                                    }
                                }
                                try {
                                    HotwordDetectedResult startCopyingAudioStreams = DetectorSession.this.mHotwordAudioStreamCopier.startCopyingAudioStreams(hotwordDetectedResult, z4);
                                    try {
                                        iMicrophoneHotwordDetectionVoiceInteractionCallback2.onDetected(startCopyingAudioStreams, (AudioFormat) null, (ParcelFileDescriptor) null);
                                        Slog.i("DetectorSession", "Egressed " + HotwordDetectedResult.getUsageSize(startCopyingAudioStreams) + " bits from hotword trusted process");
                                        if (DetectorSession.this.mDebugHotwordLogging) {
                                            Slog.i("DetectorSession", "Egressed detected result: " + startCopyingAudioStreams);
                                        }
                                    } catch (RemoteException e4) {
                                        DetectorSession.this.notifyOnDetectorRemoteException();
                                        throw e4;
                                    }
                                } catch (IOException e5) {
                                    Slog.w("DetectorSession", "Ignoring #onDetected due to a IOException", e5);
                                    try {
                                        iMicrophoneHotwordDetectionVoiceInteractionCallback2.onHotwordDetectionServiceFailure(new HotwordDetectionServiceFailure(6, "Copy audio stream failure."));
                                    } catch (RemoteException e6) {
                                        DetectorSession.this.notifyOnDetectorRemoteException();
                                        throw e6;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    public final void onRejected(HotwordRejectedResult hotwordRejectedResult) {
                        synchronized (DetectorSession.this.mLock) {
                            try {
                                DetectorSession detectorSession2 = DetectorSession.this;
                                detectorSession2.mPerformingExternalSourceHotwordDetection = false;
                                HotwordMetricsLogger.writeDetectorEvent(detectorSession2.getDetectorType(), 12, DetectorSession.this.mVoiceInteractionServiceUid);
                                DetectorSession.this.mScheduledExecutorService.schedule(new DetectorSession$3$$ExternalSyntheticLambda0(parcelFileDescriptor5, inputStream, 1), 2000L, TimeUnit.MILLISECONDS);
                                try {
                                    iMicrophoneHotwordDetectionVoiceInteractionCallback2.onRejected(hotwordRejectedResult);
                                    if (hotwordRejectedResult != null) {
                                        Slog.i("DetectorSession", "Egressed 'hotword rejected result' from hotword trusted process");
                                        if (DetectorSession.this.mDebugHotwordLogging) {
                                            Slog.i("DetectorSession", "Egressed detected result: " + hotwordRejectedResult);
                                        }
                                    }
                                } catch (RemoteException e2) {
                                    DetectorSession.this.notifyOnDetectorRemoteException();
                                    throw e2;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                try {
                    parcelFileDescriptor4.close();
                } catch (IOException unused) {
                }
            }
        });
        HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 10, this.mVoiceInteractionServiceUid);
    }

    public abstract void informRestartProcessLocked();

    public final boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public final void notifyOnDetectorRemoteException() {
        Slog.d("DetectorSession", "notifyOnDetectorRemoteException: mRemoteExceptionListener=" + this.mRemoteExceptionListener);
        VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 = this.mRemoteExceptionListener;
        if (voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1 != null) {
            IBinder iBinder = this.mToken;
            int detectorType = getDetectorType();
            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = voiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1.f$0;
            voiceInteractionManagerServiceImpl.getClass();
            try {
                voiceInteractionManagerServiceImpl.mService.detectorRemoteExceptionOccurred(iBinder, detectorType);
            } catch (RemoteException unused) {
                Slog.w("VoiceInteractionServiceManager", "Fail to notify client detector remote exception occurred.");
            }
        }
    }

    public final void reportErrorGetRemoteException() {
        if (getDetectorType() != 3) {
            HotwordMetricsLogger.writeDetectorEvent(getDetectorType(), 15, this.mVoiceInteractionServiceUid);
        }
        notifyOnDetectorRemoteException();
    }

    public final void reportErrorLocked(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
        try {
            this.mCallback.onHotwordDetectionServiceFailure(hotwordDetectionServiceFailure);
        } catch (RemoteException e) {
            Slog.w("DetectorSession", "Failed to call onHotwordDetectionServiceFailure: " + e);
            reportErrorGetRemoteException();
        }
    }

    public final void reportErrorLocked(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
        try {
            this.mCallback.onVisualQueryDetectionServiceFailure(visualQueryDetectionServiceFailure);
        } catch (RemoteException e) {
            Slog.w("DetectorSession", "Failed to call onVisualQueryDetectionServiceFailure: " + e);
            reportErrorGetRemoteException();
        }
    }

    public final void reportErrorLocked(String str) {
        try {
            this.mCallback.onUnknownFailure(str);
        } catch (RemoteException e) {
            Slog.w("DetectorSession", "Failed to call onUnknownFailure: " + e);
            reportErrorGetRemoteException();
        }
    }

    public final void saveProximityValueToBundle(HotwordDetectedResult hotwordDetectedResult) {
        synchronized (this.mLock) {
            if (hotwordDetectedResult != null) {
                try {
                    double d = this.mProximityMeters;
                    if (d != -1.0d) {
                        hotwordDetectedResult.setProximity(d);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void startListeningFromExternalSourceLocked(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        handleExternalSourceHotwordDetectionLocked(parcelFileDescriptor, audioFormat, persistableBundle, iMicrophoneHotwordDetectionVoiceInteractionCallback, true, true);
    }
}
