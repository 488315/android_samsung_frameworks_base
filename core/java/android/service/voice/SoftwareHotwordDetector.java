package android.service.voice;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.util.FunctionalUtils;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
class SoftwareHotwordDetector extends AbstractDetector {
  private static final boolean DEBUG = false;
  private static final String TAG = SoftwareHotwordDetector.class.getSimpleName();
  private final AudioFormat mAudioFormat;
  private final HotwordDetector.Callback mCallback;
  private final Executor mExecutor;
  private final IVoiceInteractionManagerService mManagerService;

  SoftwareHotwordDetector(
      IVoiceInteractionManagerService managerService,
      AudioFormat audioFormat,
      Executor executor,
      HotwordDetector.Callback callback) {
    super(managerService, executor, callback);
    this.mManagerService = managerService;
    this.mAudioFormat = audioFormat;
    this.mCallback = callback;
    this.mExecutor =
        executor != null ? executor : new HandlerExecutor(new Handler(Looper.getMainLooper()));
  }

  @Override // android.service.voice.AbstractDetector
  void initialize(PersistableBundle options, SharedMemory sharedMemory) {
    initAndVerifyDetector(
        options, sharedMemory, new InitializationStateListener(this.mExecutor, this.mCallback), 2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onDetectorRemoteException$1() throws Exception {
    this.mExecutor.execute(
        new Runnable() { // from class:
                         // android.service.voice.SoftwareHotwordDetector$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SoftwareHotwordDetector.this.lambda$onDetectorRemoteException$0();
          }
        });
  }

  void onDetectorRemoteException() {
    Binder.withCleanCallingIdentity(
        new FunctionalUtils
            .ThrowingRunnable() { // from class:
                                  // android.service.voice.SoftwareHotwordDetector$$ExternalSyntheticLambda0
          @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
          public final void runOrThrow() {
            SoftwareHotwordDetector.this.lambda$onDetectorRemoteException$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onDetectorRemoteException$0() {
    this.mCallback.onFailure(
        new HotwordDetectionServiceFailure(7, "Detector remote exception occurs"));
  }

  @Override // android.service.voice.HotwordDetector
  public boolean startRecognition() {
    throwIfDetectorIsNoLongerActive();
    maybeCloseExistingSession();
    try {
      this.mManagerService.startListeningFromMic(
          this.mAudioFormat, new BinderCallback(this.mExecutor, this.mCallback));
      return true;
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
      return true;
    } catch (SecurityException e2) {
      Slog.m115e(TAG, "startRecognition failed: " + e2);
      return false;
    }
  }

  @Override // android.service.voice.HotwordDetector
  public boolean stopRecognition() {
    throwIfDetectorIsNoLongerActive();
    try {
      this.mManagerService.stopListeningFromMic();
      return true;
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
      return true;
    }
  }

  @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
  public void destroy() {
    try {
      stopRecognition();
    } catch (Exception e) {
      Log.m99i(TAG, "failed to stopRecognition in destroy", e);
    }
    maybeCloseExistingSession();
    super.destroy();
  }

  @Override // android.service.voice.HotwordDetector
  public boolean isUsingSandboxedDetectionService() {
    return true;
  }

  private void maybeCloseExistingSession() {}

  /* JADX INFO: Access modifiers changed from: private */
  static class BinderCallback extends IMicrophoneHotwordDetectionVoiceInteractionCallback.Stub {
    private final HotwordDetector.Callback mCallback;
    private final Executor mExecutor;

    BinderCallback(Executor executor, HotwordDetector.Callback callback) {
      this.mCallback = callback;
      this.mExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetected$1(
        final AudioFormat audioFormat,
        final ParcelFileDescriptor audioStream,
        final HotwordDetectedResult hotwordDetectedResult)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.BinderCallback.this.lambda$onDetected$0(
                  audioFormat, audioStream, hotwordDetectedResult);
            }
          });
    }

    @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
    public void onDetected(
        final HotwordDetectedResult hotwordDetectedResult,
        final AudioFormat audioFormat,
        final ParcelFileDescriptor audioStream) {
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.BinderCallback.this.lambda$onDetected$1(
                  audioFormat, audioStream, hotwordDetectedResult);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetected$0(
        AudioFormat audioFormat,
        ParcelFileDescriptor audioStream,
        HotwordDetectedResult hotwordDetectedResult) {
      this.mCallback.onDetected(
          new AlwaysOnHotwordDetector.EventPayload.Builder()
              .setCaptureAudioFormat(audioFormat)
              .setAudioStream(audioStream)
              .setHotwordDetectedResult(hotwordDetectedResult)
              .build());
    }

    @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
    public void onHotwordDetectionServiceFailure(
        final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
      Slog.m119v(
          SoftwareHotwordDetector.TAG,
          "BinderCallback#onHotwordDetectionServiceFailure:" + hotwordDetectionServiceFailure);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$3(
                  hotwordDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$3(
        final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$2(
                  hotwordDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$2(
        HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
      if (hotwordDetectionServiceFailure != null) {
        this.mCallback.onFailure(hotwordDetectionServiceFailure);
      } else {
        this.mCallback.onUnknownFailure("Error data is null");
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRejected$5(final HotwordRejectedResult result)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.BinderCallback.this.lambda$onRejected$4(result);
            }
          });
    }

    @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
    public void onRejected(final HotwordRejectedResult result) {
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$BinderCallback$$ExternalSyntheticLambda5
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.BinderCallback.this.lambda$onRejected$5(result);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRejected$4(HotwordRejectedResult result) {
      this.mCallback.onRejected(
          result != null ? result : new HotwordRejectedResult.Builder().build());
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static class InitializationStateListener extends IHotwordRecognitionStatusCallback.Stub {
    private final HotwordDetector.Callback mCallback;
    private final Executor mExecutor;

    InitializationStateListener(Executor executor, HotwordDetector.Callback callback) {
      this.mCallback = callback;
      this.mExecutor = executor;
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onKeyphraseDetected(
        SoundTrigger.KeyphraseRecognitionEvent recognitionEvent, HotwordDetectedResult result) {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent recognitionEvent)
        throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRejected(HotwordRejectedResult result) throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onHotwordDetectionServiceFailure(
        final HotwordDetectionServiceFailure hotwordDetectionServiceFailure)
        throws RemoteException {
      Slog.m119v(
          SoftwareHotwordDetector.TAG,
          "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda6
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.InitializationStateListener.this
                  .lambda$onHotwordDetectionServiceFailure$1(hotwordDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$1(
        final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.InitializationStateListener.this
                  .lambda$onHotwordDetectionServiceFailure$0(hotwordDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$0(
        HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
      if (hotwordDetectionServiceFailure != null) {
        this.mCallback.onFailure(hotwordDetectionServiceFailure);
      } else {
        this.mCallback.onUnknownFailure("Error data is null");
      }
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onVisualQueryDetectionServiceFailure(
        VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
        throws RemoteException {
      Slog.m121w(
          SoftwareHotwordDetector.TAG,
          "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onSoundTriggerFailure(SoundTriggerFailure onSoundTriggerFailure)
        throws RemoteException {
      Slog.wtf(
          SoftwareHotwordDetector.TAG,
          "Unexpected STFailure in software detector: " + onSoundTriggerFailure);
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onUnknownFailure(final String errorMessage) throws RemoteException {
      Slog.m119v(SoftwareHotwordDetector.TAG, "onUnknownFailure: " + errorMessage);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda5
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.InitializationStateListener.this.lambda$onUnknownFailure$3(
                  errorMessage);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnknownFailure$3(final String errorMessage)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.InitializationStateListener.this.lambda$onUnknownFailure$2(
                  errorMessage);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnknownFailure$2(String errorMessage) {
      this.mCallback.onUnknownFailure(
          !TextUtils.isEmpty(errorMessage) ? errorMessage : "Error data is null");
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRecognitionPaused() throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRecognitionResumed() throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onStatusReported(final int status) {
      Slog.m119v(SoftwareHotwordDetector.TAG, "onStatusReported");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.InitializationStateListener.this.lambda$onStatusReported$5(
                  status);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStatusReported$5(final int status) throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.InitializationStateListener.this.lambda$onStatusReported$4(
                  status);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStatusReported$4(int status) {
      this.mCallback.onHotwordDetectionServiceInitialized(status);
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onProcessRestarted() throws RemoteException {
      Slog.m119v(SoftwareHotwordDetector.TAG, "onProcessRestarted()");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              SoftwareHotwordDetector.InitializationStateListener.this
                  .lambda$onProcessRestarted$7();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProcessRestarted$7() throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.SoftwareHotwordDetector$InitializationStateListener$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              SoftwareHotwordDetector.InitializationStateListener.this
                  .lambda$onProcessRestarted$6();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProcessRestarted$6() {
      this.mCallback.onHotwordDetectionServiceRestarted();
    }
  }

  @Override // android.service.voice.HotwordDetector
  public void dump(String prefix, PrintWriter pw) {}
}
