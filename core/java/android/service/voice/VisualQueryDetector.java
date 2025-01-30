package android.service.voice;

import android.annotation.SystemApi;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.util.FunctionalUtils;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public class VisualQueryDetector {
  private static final boolean DEBUG = false;
  private static final String TAG = VisualQueryDetector.class.getSimpleName();
  private final Callback mCallback;
  private final Executor mExecutor;
  private final VisualQueryDetectorInitializationDelegate mInitializationDelegate =
      new VisualQueryDetectorInitializationDelegate();
  private final IVoiceInteractionManagerService mManagerService;

  public interface Callback {
    void onFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure);

    void onQueryDetected(String str);

    void onQueryFinished();

    void onQueryRejected();

    void onUnknownFailure(String str);

    void onVisualQueryDetectionServiceInitialized(int i);

    void onVisualQueryDetectionServiceRestarted();
  }

  VisualQueryDetector(
      IVoiceInteractionManagerService managerService, Executor executor, Callback callback) {
    this.mManagerService = managerService;
    this.mCallback = callback;
    this.mExecutor = executor;
  }

  void initialize(PersistableBundle options, SharedMemory sharedMemory) {
    this.mInitializationDelegate.initialize(options, sharedMemory);
  }

  public void updateState(PersistableBundle options, SharedMemory sharedMemory) {
    this.mInitializationDelegate.updateState(options, sharedMemory);
  }

  public boolean startRecognition() {
    this.mInitializationDelegate.startRecognition();
    try {
      this.mManagerService.startPerceiving(new BinderCallback(this.mExecutor, this.mCallback));
      return true;
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
      return true;
    } catch (SecurityException e2) {
      Slog.m115e(TAG, "startRecognition failed: " + e2);
      return false;
    }
  }

  public boolean stopRecognition() {
    this.mInitializationDelegate.startRecognition();
    try {
      this.mManagerService.stopPerceiving();
      return true;
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
      return true;
    }
  }

  public void destroy() {
    this.mInitializationDelegate.destroy();
  }

  public void dump(String prefix, PrintWriter pw) {}

  public HotwordDetector getInitializationDelegate() {
    return this.mInitializationDelegate;
  }

  void registerOnDestroyListener(Consumer<AbstractDetector> onDestroyListener) {
    this.mInitializationDelegate.registerOnDestroyListener(onDestroyListener);
  }

  private class VisualQueryDetectorInitializationDelegate extends AbstractDetector {
    VisualQueryDetectorInitializationDelegate() {
      super(VisualQueryDetector.this.mManagerService, VisualQueryDetector.this.mExecutor, null);
    }

    @Override // android.service.voice.AbstractDetector
    void initialize(PersistableBundle options, SharedMemory sharedMemory) {
      initAndVerifyDetector(
          options,
          sharedMemory,
          new InitializationStateListener(
              VisualQueryDetector.this.mExecutor, VisualQueryDetector.this.mCallback),
          3);
    }

    @Override // android.service.voice.HotwordDetector
    public boolean stopRecognition() {
      throwIfDetectorIsNoLongerActive();
      return true;
    }

    @Override // android.service.voice.HotwordDetector
    public boolean startRecognition() {
      throwIfDetectorIsNoLongerActive();
      return true;
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public final boolean startRecognition(
        ParcelFileDescriptor audioStream, AudioFormat audioFormat, PersistableBundle options) {
      return false;
    }

    @Override // android.service.voice.HotwordDetector
    public boolean isUsingSandboxedDetectionService() {
      return true;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static class BinderCallback extends IVisualQueryDetectionVoiceInteractionCallback.Stub {
    private final Callback mCallback;
    private final Executor mExecutor;

    BinderCallback(Executor executor, Callback callback) {
      this.mExecutor = executor;
      this.mCallback = callback;
    }

    @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
    public void onQueryDetected(final String partialQuery) {
      Slog.m119v(VisualQueryDetector.TAG, "BinderCallback#onQueryDetected");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda3
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.BinderCallback.this.lambda$onQueryDetected$1(partialQuery);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryDetected$1(final String partialQuery)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.BinderCallback.this.lambda$onQueryDetected$0(partialQuery);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryDetected$0(String partialQuery) {
      this.mCallback.onQueryDetected(partialQuery);
    }

    @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
    public void onQueryFinished() {
      Slog.m119v(VisualQueryDetector.TAG, "BinderCallback#onQueryFinished");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda7
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.BinderCallback.this.lambda$onQueryFinished$3();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryFinished$3() throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.BinderCallback.this.lambda$onQueryFinished$2();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryFinished$2() {
      this.mCallback.onQueryFinished();
    }

    @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
    public void onQueryRejected() {
      Slog.m119v(VisualQueryDetector.TAG, "BinderCallback#onQueryRejected");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.BinderCallback.this.lambda$onQueryRejected$5();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryRejected$5() throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.BinderCallback.this.lambda$onQueryRejected$4();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onQueryRejected$4() {
      this.mCallback.onQueryRejected();
    }

    @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
    public void onVisualQueryDetectionServiceFailure(
        final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
      Slog.m119v(
          VisualQueryDetector.TAG,
          "BinderCallback#onVisualQueryDetectionServiceFailure: "
              + visualQueryDetectionServiceFailure);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.BinderCallback.this.lambda$onVisualQueryDetectionServiceFailure$7(
                  visualQueryDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$7(
        final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.BinderCallback.this.lambda$onVisualQueryDetectionServiceFailure$6(
                  visualQueryDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$6(
        VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
      if (visualQueryDetectionServiceFailure != null) {
        this.mCallback.onFailure(visualQueryDetectionServiceFailure);
      } else {
        this.mCallback.onUnknownFailure("Error data is null");
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static class InitializationStateListener extends IHotwordRecognitionStatusCallback.Stub {
    private final Callback mCallback;
    private final Executor mExecutor;

    InitializationStateListener(Executor executor, Callback callback) {
      this.mExecutor = executor;
      this.mCallback = callback;
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
    public void onRecognitionPaused() throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRecognitionResumed() throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onStatusReported(final int status) {
      Slog.m119v(VisualQueryDetector.TAG, "onStatusReported");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.InitializationStateListener.this.lambda$onStatusReported$1(
                  status);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStatusReported$1(final int status) throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.InitializationStateListener.this.lambda$onStatusReported$0(
                  status);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStatusReported$0(int status) {
      this.mCallback.onVisualQueryDetectionServiceInitialized(status);
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onProcessRestarted() throws RemoteException {
      Slog.m119v(VisualQueryDetector.TAG, "onProcessRestarted()");
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda4
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.InitializationStateListener.this.lambda$onProcessRestarted$3();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProcessRestarted$3() throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.InitializationStateListener.this.lambda$onProcessRestarted$2();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProcessRestarted$2() {
      this.mCallback.onVisualQueryDetectionServiceRestarted();
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onHotwordDetectionServiceFailure(
        HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
      Slog.m121w(
          VisualQueryDetector.TAG,
          "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onVisualQueryDetectionServiceFailure(
        final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
        throws RemoteException {
      Slog.m119v(
          VisualQueryDetector.TAG,
          "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda6
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.InitializationStateListener.this
                  .lambda$onVisualQueryDetectionServiceFailure$5(
                      visualQueryDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$5(
        final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.InitializationStateListener.this
                  .lambda$onVisualQueryDetectionServiceFailure$4(
                      visualQueryDetectionServiceFailure);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$4(
        VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
      if (visualQueryDetectionServiceFailure != null) {
        this.mCallback.onFailure(visualQueryDetectionServiceFailure);
      } else {
        this.mCallback.onUnknownFailure("Error data is null");
      }
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) {
      Slog.wtf(
          VisualQueryDetector.TAG,
          "Unexpected STFailure in VisualQueryDetector" + soundTriggerFailure);
    }

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onUnknownFailure(final String errorMessage) throws RemoteException {
      Slog.m119v(VisualQueryDetector.TAG, "onUnknownFailure: " + errorMessage);
      Binder.withCleanCallingIdentity(
          new FunctionalUtils
              .ThrowingRunnable() { // from class:
                                    // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
              VisualQueryDetector.InitializationStateListener.this.lambda$onUnknownFailure$7(
                  errorMessage);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnknownFailure$7(final String errorMessage)
        throws Exception {
      this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              VisualQueryDetector.InitializationStateListener.this.lambda$onUnknownFailure$6(
                  errorMessage);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnknownFailure$6(String errorMessage) {
      this.mCallback.onUnknownFailure(
          !TextUtils.isEmpty(errorMessage) ? errorMessage : "Error data is null");
    }
  }
}
