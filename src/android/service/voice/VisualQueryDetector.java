package android.service.voice;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.service.voice.IVisualQueryDetectionVoiceInteractionCallback;
import android.service.voice.VisualQueryDetector;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FunctionalUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public class VisualQueryDetector {
    private static final boolean DEBUG = false;
    private static final int SETTINGS_DISABLE_BIT = 0;
    private static final int SETTINGS_ENABLE_BIT = 1;
    private static final String TAG = "VisualQueryDetector";
    private final String mAttributionTag;
    private final Callback mCallback;
    private final Context mContext;
    private final Executor mExecutor;
    private final IVoiceInteractionManagerService mManagerService;
    private AccessibilityDetectionEnabledListenerWrapper mActiveAccessibilityListenerWrapper = null;
    private final VisualQueryDetectorInitializationDelegate mInitializationDelegate = new VisualQueryDetectorInitializationDelegate();

    VisualQueryDetector(IVoiceInteractionManagerService iVoiceInteractionManagerService, Executor executor, Callback callback, Context context, String str) {
        this.mManagerService = iVoiceInteractionManagerService;
        this.mCallback = callback;
        this.mExecutor = executor;
        this.mContext = context;
        this.mAttributionTag = str;
    }

    void initialize(PersistableBundle persistableBundle, SharedMemory sharedMemory) {
        this.mInitializationDelegate.initialize(persistableBundle, sharedMemory);
    }

    public void updateState(PersistableBundle persistableBundle, SharedMemory sharedMemory) {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.updateState(persistableBundle, sharedMemory);
        }
    }

    public boolean startRecognition() {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.startRecognition();
            try {
                try {
                    this.mManagerService.startPerceiving(new BinderCallback(this.mExecutor, this.mCallback, this.mInitializationDelegate.getLock()));
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            } catch (SecurityException e2) {
                Slog.e(TAG, "startRecognition failed: " + e2);
                return false;
            }
        }
        return true;
    }

    public boolean stopRecognition() {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.stopRecognition();
            try {
                this.mManagerService.stopPerceiving();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public void destroy() {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.destroy();
        }
    }

    @SystemApi
    public boolean isAccessibilityDetectionEnabled() {
        boolean accessibilityDetectionEnabled;
        Slog.d(TAG, "Fetching accessibility setting");
        synchronized (this.mInitializationDelegate.getLock()) {
            try {
                try {
                    accessibilityDetectionEnabled = this.mManagerService.getAccessibilityDetectionEnabled();
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return accessibilityDetectionEnabled;
    }

    @SystemApi
    public void setAccessibilityDetectionEnabledListener(Consumer<Boolean> consumer) {
        String str = TAG;
        Slog.d(str, "Registering Accessibility settings listener.");
        synchronized (this.mInitializationDelegate.getLock()) {
            try {
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (this.mActiveAccessibilityListenerWrapper != null) {
                Slog.e(str, "Fail to register accessibility setting listener: already registered and not unregistered.");
                throw new IllegalStateException("Cannot register listener with listeners already set.");
            }
            AccessibilityDetectionEnabledListenerWrapper accessibilityDetectionEnabledListenerWrapper = new AccessibilityDetectionEnabledListenerWrapper(this, consumer);
            this.mActiveAccessibilityListenerWrapper = accessibilityDetectionEnabledListenerWrapper;
            this.mManagerService.registerAccessibilityDetectionSettingsListener(accessibilityDetectionEnabledListenerWrapper);
        }
    }

    @SystemApi
    public void clearAccessibilityDetectionEnabledListener() {
        AccessibilityDetectionEnabledListenerWrapper accessibilityDetectionEnabledListenerWrapper;
        String str = TAG;
        Slog.d(str, "Unregistering Accessibility settings listener.");
        synchronized (this.mInitializationDelegate.getLock()) {
            try {
                accessibilityDetectionEnabledListenerWrapper = this.mActiveAccessibilityListenerWrapper;
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (accessibilityDetectionEnabledListenerWrapper == null) {
                Slog.e(str, "Not able to remove the listener: listener does not exist.");
                throw new IllegalStateException("Cannot clear listener since it is not set.");
            }
            this.mManagerService.unregisterAccessibilityDetectionSettingsListener(accessibilityDetectionEnabledListenerWrapper);
            this.mActiveAccessibilityListenerWrapper = null;
        }
    }

    private final class AccessibilityDetectionEnabledListenerWrapper extends IVoiceInteractionAccessibilitySettingsListener.Stub {
        private Consumer<Boolean> mListener;

        AccessibilityDetectionEnabledListenerWrapper(VisualQueryDetector visualQueryDetector, Consumer<Boolean> consumer) {
            this.mListener = consumer;
        }

        @Override // com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener
        public void onAccessibilityDetectionChanged(boolean z) {
            this.mListener.accept(Boolean.valueOf(z));
        }
    }

    public void dump(String str, PrintWriter printWriter) {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.dump(str, printWriter);
        }
    }

    public HotwordDetector getInitializationDelegate() {
        return this.mInitializationDelegate;
    }

    void registerOnDestroyListener(Consumer<AbstractDetector> consumer) {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.registerOnDestroyListener(consumer);
        }
    }

    public interface Callback {
        void onFailure(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure);

        void onQueryDetected(String str);

        void onQueryFinished();

        void onQueryRejected();

        void onUnknownFailure(String str);

        void onVisualQueryDetectionServiceInitialized(int i);

        void onVisualQueryDetectionServiceRestarted();

        default void onQueryDetected(VisualQueryDetectedResult visualQueryDetectedResult) {
            throw new UnsupportedOperationException("This emthod must be implemented for use.");
        }
    }

    private class VisualQueryDetectorInitializationDelegate extends AbstractDetector {
        @Override // android.service.voice.HotwordDetector
        public void dump(String str, PrintWriter printWriter) {
        }

        @Override // android.service.voice.HotwordDetector
        public boolean isUsingSandboxedDetectionService() {
            return true;
        }

        @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
        public final boolean startRecognition(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle) {
            return false;
        }

        VisualQueryDetectorInitializationDelegate() {
            super(VisualQueryDetector.this.mManagerService, VisualQueryDetector.this.mExecutor, null);
        }

        @Override // android.service.voice.AbstractDetector
        void initialize(PersistableBundle persistableBundle, SharedMemory sharedMemory) {
            initAndVerifyDetector(persistableBundle, sharedMemory, new InitializationStateListener(VisualQueryDetector.this.mExecutor, VisualQueryDetector.this.mCallback, VisualQueryDetector.this.mContext), 3, VisualQueryDetector.this.mAttributionTag);
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

        /* JADX INFO: Access modifiers changed from: private */
        public Object getLock() {
            return this.mLock;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BinderCallback extends IVisualQueryDetectionVoiceInteractionCallback.Stub {
        private final Callback mCallback;
        private final Executor mExecutor;
        private final Object mLock;

        BinderCallback(Executor executor, Callback callback, Object obj) {
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mLock = obj;
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryDetected(final String str) {
            Slog.v(VisualQueryDetector.TAG, "BinderCallback#onQueryDetected");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.BinderCallback.this.lambda$onQueryDetected$1(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryDetected$1(final String str) throws Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        VisualQueryDetector.BinderCallback.this.lambda$onQueryDetected$0(str);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryDetected$0(String str) {
            this.mCallback.onQueryDetected(str);
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onResultDetected(final VisualQueryDetectedResult visualQueryDetectedResult) {
            Slog.v(VisualQueryDetector.TAG, "BinderCallback#onResultDetected");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.BinderCallback.this.lambda$onResultDetected$3(visualQueryDetectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResultDetected$3(final VisualQueryDetectedResult visualQueryDetectedResult) throws Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        VisualQueryDetector.BinderCallback.this.lambda$onResultDetected$2(visualQueryDetectedResult);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResultDetected$2(VisualQueryDetectedResult visualQueryDetectedResult) {
            this.mCallback.onQueryDetected(visualQueryDetectedResult);
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryFinished() {
            Slog.v(VisualQueryDetector.TAG, "BinderCallback#onQueryFinished");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.BinderCallback.this.lambda$onQueryFinished$5();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryFinished$5() throws Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        VisualQueryDetector.BinderCallback.this.lambda$onQueryFinished$4();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryFinished$4() {
            this.mCallback.onQueryFinished();
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryRejected() {
            Slog.v(VisualQueryDetector.TAG, "BinderCallback#onQueryRejected");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.BinderCallback.this.lambda$onQueryRejected$7();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryRejected$7() throws Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        VisualQueryDetector.BinderCallback.this.lambda$onQueryRejected$6();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryRejected$6() {
            this.mCallback.onQueryRejected();
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onVisualQueryDetectionServiceFailure(final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
            Slog.v(VisualQueryDetector.TAG, "BinderCallback#onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.BinderCallback.this.lambda$onVisualQueryDetectionServiceFailure$9(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$9(final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    VisualQueryDetector.BinderCallback.this.lambda$onVisualQueryDetectionServiceFailure$8(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$8(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
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
        private final Context mContext;
        private final Executor mExecutor;

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, HotwordDetectedResult hotwordDetectedResult) {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(HotwordDetectedResult hotwordDetectedResult) {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() throws RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException {
        }

        InitializationStateListener(Executor executor, Callback callback, Context context) {
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mContext = context;
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(final int i) {
            Slog.v(VisualQueryDetector.TAG, "onStatusReported");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onStatusReported$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusReported$1(final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onStatusReported$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusReported$0(int i) {
            this.mCallback.onVisualQueryDetectionServiceInitialized(i);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() throws RemoteException {
            Slog.v(VisualQueryDetector.TAG, "onProcessRestarted()");
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onProcessRestarted$3();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessRestarted$3() throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda6
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
        public void onHotwordDetectionServiceFailure(HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
            Slog.w(VisualQueryDetector.TAG, "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException {
            Slog.v(VisualQueryDetector.TAG, "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onVisualQueryDetectionServiceFailure$5(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$5(final VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onVisualQueryDetectionServiceFailure$4(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$4(VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
            if (visualQueryDetectionServiceFailure != null) {
                this.mCallback.onFailure(visualQueryDetectionServiceFailure);
            } else {
                this.mCallback.onUnknownFailure("Error data is null");
            }
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) {
            Slog.wtf(VisualQueryDetector.TAG, "Unexpected STFailure in VisualQueryDetector" + soundTriggerFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(final String str) throws RemoteException {
            Slog.v(VisualQueryDetector.TAG, "onUnknownFailure: " + str);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onUnknownFailure$7(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnknownFailure$7(final String str) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onUnknownFailure$6(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnknownFailure$6(String str) {
            Callback callback = this.mCallback;
            if (TextUtils.isEmpty(str)) {
                str = "Error data is null";
            }
            callback.onUnknownFailure(str);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(final String str, final AndroidFuture androidFuture) throws RemoteException {
            Slog.v(VisualQueryDetector.TAG, "BinderCallback#onOpenFile " + str);
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda8
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onOpenFile$9(str, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpenFile$9(final String str, final AndroidFuture androidFuture) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VisualQueryDetector.InitializationStateListener.this.lambda$onOpenFile$8(str, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpenFile$8(String str, AndroidFuture androidFuture) {
            Slog.v(VisualQueryDetector.TAG, "onOpenFile: " + str + "under internal app storage.");
            File file = new File(this.mContext.getFilesDir(), str);
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = ParcelFileDescriptor.open(file, 268435456);
                Slog.d(VisualQueryDetector.TAG, "Successfully opened a file with ParcelFileDescriptor.");
            } catch (FileNotFoundException unused) {
                Slog.e(VisualQueryDetector.TAG, "Cannot open file. No ParcelFileDescriptor returned.");
            } finally {
                androidFuture.complete(parcelFileDescriptor);
            }
        }
    }
}
