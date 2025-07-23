package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.impl.CallbackProxies;
import android.os.Binder;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class CallbackProxies {

    public static class SessionStateCallbackProxy extends CameraCaptureSession.StateCallback {
        private final CameraCaptureSession.StateCallback mCallback;
        private final Executor mExecutor;

        public SessionStateCallbackProxy(Executor executor, CameraCaptureSession.StateCallback stateCallback) {
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor, "executor must not be null");
            this.mCallback = (CameraCaptureSession.StateCallback) Preconditions.checkNotNull(stateCallback, "callback must not be null");
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(final CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onConfigured$0(cameraCaptureSession);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigured$0(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onConfigured(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(final CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onConfigureFailed$1(cameraCaptureSession);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigureFailed$1(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onConfigureFailed(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onReady(final CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onReady$2(cameraCaptureSession);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReady$2(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onActive(final CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onActive$3(cameraCaptureSession);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActive$3(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onCaptureQueueEmpty(final CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onCaptureQueueEmpty$4(cameraCaptureSession);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureQueueEmpty$4(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(final CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onClosed$5(cameraCaptureSession);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClosed$5(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onSurfacePrepared(final CameraCaptureSession cameraCaptureSession, final Surface surface) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CallbackProxies.SessionStateCallbackProxy.this.lambda$onSurfacePrepared$6(cameraCaptureSession, surface);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfacePrepared$6(CameraCaptureSession cameraCaptureSession, Surface surface) {
            this.mCallback.onSurfacePrepared(cameraCaptureSession, surface);
        }
    }

    private CallbackProxies() {
        throw new AssertionError();
    }
}
