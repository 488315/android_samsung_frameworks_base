package android.app.smartspace;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
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
public final class SmartspaceSession implements AutoCloseable {
    private static final boolean DEBUG = false;
    private static final String TAG = SmartspaceSession.class.getSimpleName();
    private final ISmartspaceManager mInterface;
    private final SmartspaceSessionId mSessionId;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private final ArrayMap<OnTargetsAvailableListener, CallbackWrapper> mRegisteredCallbacks =
            new ArrayMap<>();

    public interface OnTargetsAvailableListener {
        void onTargetsAvailable(List<SmartspaceTarget> list);
    }

    SmartspaceSession(Context context, SmartspaceConfig smartspaceConfig) {
        IBinder b = ServiceManager.getService(Context.SMARTSPACE_SERVICE);
        this.mInterface = ISmartspaceManager.Stub.asInterface(b);
        this.mSessionId =
                new SmartspaceSessionId(
                        context.getPackageName() + ":" + UUID.randomUUID().toString(),
                        context.getUser());
        try {
            this.mInterface.createSmartspaceSession(smartspaceConfig, this.mSessionId, getToken());
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to create Smartspace session", e);
            e.rethrowFromSystemServer();
        }
        this.mCloseGuard.open("SmartspaceSession.close");
    }

    public void notifySmartspaceEvent(SmartspaceTargetEvent event) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.notifySmartspaceEvent(this.mSessionId, event);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to notify event", e);
            e.rethrowFromSystemServer();
        }
    }

    public void requestSmartspaceUpdate() {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.requestSmartspaceUpdate(this.mSessionId);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to request update.", e);
            e.rethrowFromSystemServer();
        }
    }

    public void addOnTargetsAvailableListener(
            Executor listenerExecutor, final OnTargetsAvailableListener listener) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        if (this.mRegisteredCallbacks.containsKey(listener)) {
            return;
        }
        try {
            Objects.requireNonNull(listener);
            CallbackWrapper callbackWrapper =
                    new CallbackWrapper(
                            listenerExecutor,
                            new Consumer() { // from class:
                                // android.app.smartspace.SmartspaceSession$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    SmartspaceSession.OnTargetsAvailableListener.this
                                            .onTargetsAvailable((List) obj);
                                }
                            });
            this.mRegisteredCallbacks.put(listener, callbackWrapper);
            this.mInterface.registerSmartspaceUpdates(this.mSessionId, callbackWrapper);
            this.mInterface.requestSmartspaceUpdate(this.mSessionId);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to register for smartspace updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void removeOnTargetsAvailableListener(OnTargetsAvailableListener listener) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        if (!this.mRegisteredCallbacks.containsKey(listener)) {
            return;
        }
        try {
            CallbackWrapper callbackWrapper = this.mRegisteredCallbacks.remove(listener);
            this.mInterface.unregisterSmartspaceUpdates(this.mSessionId, callbackWrapper);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to unregister for smartspace updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    private void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            try {
                this.mInterface.destroySmartspaceSession(this.mSessionId);
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to notify Smartspace target event", e);
                e.rethrowFromSystemServer();
                return;
            }
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    protected void finalize() {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
            try {
                super.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                super.finalize();
            } catch (Throwable throwable2) {
                throwable2.printStackTrace();
            }
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            destroy();
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    static class CallbackWrapper extends ISmartspaceCallback.Stub {
        private final Consumer<List<SmartspaceTarget>> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor callbackExecutor, Consumer<List<SmartspaceTarget>> callback) {
            this.mCallback = callback;
            this.mExecutor = callbackExecutor;
        }

        @Override // android.app.smartspace.ISmartspaceCallback
        public void onResult(final ParceledListSlice result) {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.app.smartspace.SmartspaceSession$CallbackWrapper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SmartspaceSession.CallbackWrapper.this.lambda$onResult$0(result);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        public /* synthetic */ void lambda$onResult$0(ParceledListSlice result) {
            this.mCallback.accept(result.getList());
        }
    }

    private static class Token {
        static final IBinder sBinder = new Binder(SmartspaceSession.TAG);

        private Token() {}
    }

    private static IBinder getToken() {
        return Token.sBinder;
    }
}
