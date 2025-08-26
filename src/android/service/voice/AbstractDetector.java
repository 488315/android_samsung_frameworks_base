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
import android.service.voice.AlwaysOnHotwordDetector;
import android.service.voice.HotwordDetector;
import android.service.voice.HotwordRejectedResult;
import android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback;
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
    static final boolean IS_IDENTITY_WITH_ATTRIBUTION_TAG = false;
    private static final String TAG = "AbstractDetector";
    private final HotwordDetector.Callback mCallback;
    private final Executor mExecutor;
    private final AtomicBoolean mIsDetectorActive;
    private final IVoiceInteractionManagerService mManagerService;
    private Consumer<AbstractDetector> mOnDestroyListener;
    protected final Object mLock = new Object();
    private final IBinder mToken = new Binder();

    abstract void initialize(PersistableBundle persistableBundle, SharedMemory sharedMemory);

    AbstractDetector(IVoiceInteractionManagerService iVoiceInteractionManagerService, Executor executor, HotwordDetector.Callback callback) {
        this.mManagerService = iVoiceInteractionManagerService;
        this.mCallback = callback;
        this.mExecutor = executor == null ? new HandlerExecutor(new Handler(Looper.getMainLooper())) : executor;
        this.mIsDetectorActive = new AtomicBoolean(true);
    }

    boolean isSameToken(IBinder iBinder) {
        return iBinder != null && this.mToken == iBinder;
    }

    @Override // android.service.voice.HotwordDetector
    public boolean startRecognition(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle) {
        throwIfDetectorIsNoLongerActive();
        try {
            this.mManagerService.startListeningFromExternalSource(parcelFileDescriptor, audioFormat, persistableBundle, this.mToken, new BinderCallback(this.mExecutor, this.mCallback));
            return true;
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @Override // android.service.voice.HotwordDetector
    public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory) {
        throwIfDetectorIsNoLongerActive();
        try {
            this.mManagerService.updateState(persistableBundle, sharedMemory, this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void initAndVerifyDetector(PersistableBundle persistableBundle, SharedMemory sharedMemory, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, String str) {
        Identity identity = new Identity();
        identity.packageName = ActivityThread.currentOpPackageName();
        try {
            this.mManagerService.initAndVerifyDetector(identity, persistableBundle, sharedMemory, this.mToken, iHotwordRecognitionStatusCallback, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void registerOnDestroyListener(Consumer<AbstractDetector> consumer) {
        synchronized (this.mLock) {
            if (this.mOnDestroyListener != null) {
                throw new IllegalStateException("only one destroy listener can be registered");
            }
            this.mOnDestroyListener = consumer;
        }
    }

    @Override // android.service.voice.HotwordDetector
    public void destroy() {
        Consumer<AbstractDetector> consumer;
        if (this.mIsDetectorActive.get()) {
            this.mIsDetectorActive.set(false);
            try {
                this.mManagerService.destroyDetector(this.mToken);
                synchronized (this.mLock) {
                    consumer = this.mOnDestroyListener;
                }
                if (consumer != null) {
                    consumer.accept(this);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    protected void throwIfDetectorIsNoLongerActive() {
        if (this.mIsDetectorActive.get()) {
            return;
        }
        Slog.e(TAG, "attempting to use a destroyed detector which is no longer active");
        throw new IllegalStateException("attempting to use a destroyed detector which is no longer active");
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
        public /* synthetic */ void lambda$onDetected$1(final AudioFormat audioFormat, final HotwordDetectedResult hotwordDetectedResult) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDetected$0(audioFormat, hotwordDetectedResult);
                }
            });
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onDetected(final HotwordDetectedResult hotwordDetectedResult, final AudioFormat audioFormat, ParcelFileDescriptor parcelFileDescriptor) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onDetected$1(audioFormat, hotwordDetectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDetected$0(AudioFormat audioFormat, HotwordDetectedResult hotwordDetectedResult) {
            this.mCallback.onDetected(new AlwaysOnHotwordDetector.EventPayload.Builder().setCaptureAudioFormat(audioFormat).setHotwordDetectedResult(hotwordDetectedResult).build());
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onHotwordDetectionServiceFailure(final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            Slog.v(AbstractDetector.TAG, "BinderCallback#onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onHotwordDetectionServiceFailure$3(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$3(final HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onHotwordDetectionServiceFailure$2(hotwordDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotwordDetectionServiceFailure$2(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            if (hotwordDetectionServiceFailure != null) {
                this.mCallback.onFailure(hotwordDetectionServiceFailure);
            } else {
                this.mCallback.onUnknownFailure("Error data is null");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRejected$5(final HotwordRejectedResult hotwordRejectedResult) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onRejected$4(hotwordRejectedResult);
                }
            });
        }

        @Override // android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback
        public void onRejected(final HotwordRejectedResult hotwordRejectedResult) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AbstractDetector$BinderCallback$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onRejected$5(hotwordRejectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRejected$4(HotwordRejectedResult hotwordRejectedResult) {
            HotwordDetector.Callback callback = this.mCallback;
            if (hotwordRejectedResult == null) {
                hotwordRejectedResult = new HotwordRejectedResult.Builder().build();
            }
            callback.onRejected(hotwordRejectedResult);
        }
    }
}
