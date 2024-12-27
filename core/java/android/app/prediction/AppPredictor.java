package android.app.prediction;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;

import dalvik.system.CloseGuard;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@SystemApi
public final class AppPredictor {
    private static final String TAG = AppPredictor.class.getSimpleName();
    private final IPredictionManager mPredictionManager;
    private final AppPredictionSessionId mSessionId;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private final ArrayMap<Callback, CallbackWrapper> mRegisteredCallbacks = new ArrayMap<>();

    public interface Callback {
        void onTargetsAvailable(List<AppTarget> list);
    }

    AppPredictor(Context context, AppPredictionContext predictionContext) {
        IBinder b = ServiceManager.getService(Context.APP_PREDICTION_SERVICE);
        this.mPredictionManager = IPredictionManager.Stub.asInterface(b);
        this.mSessionId =
                new AppPredictionSessionId(
                        context.getPackageName() + ":" + UUID.randomUUID(), context.getUserId());
        try {
            this.mPredictionManager.createPredictionSession(
                    predictionContext, this.mSessionId, getToken());
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to create predictor", e);
            e.rethrowAsRuntimeException();
        }
        this.mCloseGuard.open("AppPredictor.close");
    }

    public void notifyAppTargetEvent(AppTargetEvent event) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.notifyAppTargetEvent(this.mSessionId, event);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notify app target event", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void notifyLaunchLocationShown(String launchLocation, List<AppTargetId> targetIds) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.notifyLaunchLocationShown(
                    this.mSessionId, launchLocation, new ParceledListSlice(targetIds));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notify location shown event", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void registerPredictionUpdates(Executor callbackExecutor, Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            registerPredictionUpdatesLocked(callbackExecutor, callback);
        }
    }

    private void registerPredictionUpdatesLocked(
            Executor callbackExecutor, final Callback callback) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        if (this.mRegisteredCallbacks.containsKey(callback)) {
            return;
        }
        try {
            Objects.requireNonNull(callback);
            CallbackWrapper callbackWrapper =
                    new CallbackWrapper(
                            callbackExecutor,
                            new Consumer() { // from class:
                                // android.app.prediction.AppPredictor$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    AppPredictor.Callback.this.onTargetsAvailable((List) obj);
                                }
                            });
            this.mPredictionManager.registerPredictionUpdates(this.mSessionId, callbackWrapper);
            this.mRegisteredCallbacks.put(callback, callbackWrapper);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to register for prediction updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void unregisterPredictionUpdates(Callback callback) {
        synchronized (this.mRegisteredCallbacks) {
            unregisterPredictionUpdatesLocked(callback);
        }
    }

    private void unregisterPredictionUpdatesLocked(Callback callback) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        if (!this.mRegisteredCallbacks.containsKey(callback)) {
            return;
        }
        try {
            CallbackWrapper callbackWrapper = this.mRegisteredCallbacks.remove(callback);
            this.mPredictionManager.unregisterPredictionUpdates(this.mSessionId, callbackWrapper);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to unregister for prediction updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestPredictionUpdate() {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.requestPredictionUpdate(this.mSessionId);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to request prediction update", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void sortTargets(
            List<AppTarget> targets,
            Executor callbackExecutor,
            Consumer<List<AppTarget>> callback) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.sortAppTargets(
                    this.mSessionId,
                    new ParceledListSlice(targets),
                    new CallbackWrapper(callbackExecutor, callback));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sort targets", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestServiceFeatures(Executor callbackExecutor, Consumer<Bundle> callback) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mPredictionManager.requestServiceFeatures(
                    this.mSessionId, new RemoteCallbackWrapper(callbackExecutor, callback));
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to request service feature info", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            synchronized (this.mRegisteredCallbacks) {
                destroySessionLocked();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    private void destroySessionLocked() {
        try {
            this.mPredictionManager.onDestroyPredictionSession(this.mSessionId);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notify app target event", e);
            e.rethrowAsRuntimeException();
        }
        this.mRegisteredCallbacks.clear();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
        } finally {
            super.finalize();
        }
    }

    public AppPredictionSessionId getSessionId() {
        return this.mSessionId;
    }

    static class CallbackWrapper extends IPredictionCallback.Stub {
        private final Consumer<List<AppTarget>> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor callbackExecutor, Consumer<List<AppTarget>> callback) {
            this.mCallback = callback;
            this.mExecutor = callbackExecutor;
        }

        @Override // android.app.prediction.IPredictionCallback
        public void onResult(final ParceledListSlice result) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.app.prediction.AppPredictor$CallbackWrapper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppPredictor.CallbackWrapper.this.lambda$onResult$0(result);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(ParceledListSlice result) {
            this.mCallback.accept(result.getList());
        }
    }

    static class RemoteCallbackWrapper extends IRemoteCallback.Stub {
        private final Consumer<Bundle> mCallback;
        private final Executor mExecutor;

        RemoteCallbackWrapper(Executor callbackExecutor, Consumer<Bundle> callback) {
            this.mExecutor = callbackExecutor;
            this.mCallback = callback;
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final Bundle result) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.app.prediction.AppPredictor$RemoteCallbackWrapper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppPredictor.RemoteCallbackWrapper.this.lambda$sendResult$0(result);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$0(Bundle result) {
            this.mCallback.accept(result);
        }
    }

    private static class Token {
        static final IBinder sBinder = new Binder(AppPredictor.TAG);

        private Token() {}
    }

    private static IBinder getToken() {
        return Token.sBinder;
    }
}
