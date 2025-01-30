package android.service.voice;

import android.app.ActivityThread;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.util.Slog;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
abstract class AbstractDetector implements HotwordDetector {
  private static final boolean DEBUG = false;
  private static final String TAG = AbstractDetector.class.getSimpleName();
  private final HotwordDetector.Callback mCallback;
  private final Executor mExecutor;
  private final AtomicBoolean mIsDetectorActive;
  private final IVoiceInteractionManagerService mManagerService;
  private Consumer<AbstractDetector> mOnDestroyListener;
  protected final Object mLock = new Object();
  private final IBinder mToken = new Binder();

  abstract void initialize(PersistableBundle persistableBundle, SharedMemory sharedMemory);

  AbstractDetector(
      IVoiceInteractionManagerService managerService,
      Executor executor,
      HotwordDetector.Callback callback) {
    this.mManagerService = managerService;
    this.mCallback = callback;
    this.mExecutor =
        executor != null ? executor : new HandlerExecutor(new Handler(Looper.getMainLooper()));
    this.mIsDetectorActive = new AtomicBoolean(true);
  }

  boolean isSameToken(IBinder token) {
    return token != null && this.mToken == token;
  }

  @Override // android.service.voice.HotwordDetector
  public boolean startRecognition(
      ParcelFileDescriptor audioStream, AudioFormat audioFormat, PersistableBundle options) {
    throwIfDetectorIsNoLongerActive();
    try {
      this.mManagerService.startListeningFromExternalSource(
          audioStream,
          audioFormat,
          options,
          this.mToken,
          new BinderCallback(this.mExecutor, this.mCallback));
      return true;
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
      return true;
    }
  }

  @Override // android.service.voice.HotwordDetector
  public void updateState(PersistableBundle options, SharedMemory sharedMemory) {
    throwIfDetectorIsNoLongerActive();
    try {
      this.mManagerService.updateState(options, sharedMemory, this.mToken);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  protected void initAndVerifyDetector(
      PersistableBundle options,
      SharedMemory sharedMemory,
      IHotwordRecognitionStatusCallback callback,
      int detectorType) {
    Identity identity = new Identity();
    identity.packageName = ActivityThread.currentOpPackageName();
    try {
      this.mManagerService.initAndVerifyDetector(
          identity, options, sharedMemory, this.mToken, callback, detectorType);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  void registerOnDestroyListener(Consumer<AbstractDetector> onDestroyListener) {
    synchronized (this.mLock) {
      if (this.mOnDestroyListener != null) {
        throw new IllegalStateException("only one destroy listener can be registered");
      }
      this.mOnDestroyListener = onDestroyListener;
    }
  }

  @Override // android.service.voice.HotwordDetector
  public void destroy() {
    if (!this.mIsDetectorActive.get()) {
      return;
    }
    this.mIsDetectorActive.set(false);
    try {
      this.mManagerService.destroyDetector(this.mToken);
      synchronized (this.mLock) {
        this.mOnDestroyListener.accept(this);
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  protected void throwIfDetectorIsNoLongerActive() {
    if (!this.mIsDetectorActive.get()) {
      Slog.m115e(TAG, "attempting to use a destroyed detector which is no longer active");
      throw new IllegalStateException(
          "attempting to use a destroyed detector which is no longer active");
    }
  }

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
        final AudioFormat audioFormat, final HotwordDetectedResult hotwordDetectedResult)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              AbstractDetector.BinderCallback.this.lambda$onDetected$0(
                  audioFormat, hotwordDetectedResult);
            }
          });
    }

    @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
    public void onDetected(
        final HotwordDetectedResult hotwordDetectedResult,
        final AudioFormat audioFormat,
        ParcelFileDescriptor audioStreamIgnored) {
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              AbstractDetector.BinderCallback.this.lambda$onDetected$1(
                  audioFormat, hotwordDetectedResult);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetected$0(
        AudioFormat audioFormat, HotwordDetectedResult hotwordDetectedResult) {
      this.mCallback.onDetected(
          new AlwaysOnHotwordDetector.EventPayload.Builder()
              .setCaptureAudioFormat(audioFormat)
              .setHotwordDetectedResult(hotwordDetectedResult)
              .build());
    }

    @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
    public void onHotwordDetectionServiceFailure(
        final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
      Slog.m119v(
          AbstractDetector.TAG,
          "BinderCallback#onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              AbstractDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$3(
                  hotwordDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$3(
        final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              AbstractDetector.BinderCallback.this.lambda$onHotwordDetectionServiceFailure$2(
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
                           // android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              AbstractDetector.BinderCallback.this.lambda$onRejected$4(result);
            }
          });
    }

    @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
    public void onRejected(final HotwordRejectedResult result) {
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda3
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              AbstractDetector.BinderCallback.this.lambda$onRejected$5(result);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRejected$4(HotwordRejectedResult result) {
      this.mCallback.onRejected(
          result != null ? result : new HotwordRejectedResult.Builder().build());
    }
  }
}
