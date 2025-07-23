package android.app.smartspace;

import android.annotation.SystemApi;
import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.ISmartspaceManager;
import android.app.smartspace.SmartspaceSession;
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
/* loaded from: classes.dex */
public final class SmartspaceSession implements AutoCloseable {
    private static final boolean DEBUG = false;
    private static final String TAG = "SmartspaceSession";
    private final ISmartspaceManager mInterface;
    private final SmartspaceSessionId mSessionId;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private final ArrayMap<OnTargetsAvailableListener, CallbackWrapper> mRegisteredCallbacks = new ArrayMap<>();

    public interface OnTargetsAvailableListener {
        void onTargetsAvailable(List<SmartspaceTarget> list);
    }

    SmartspaceSession(Context context, SmartspaceConfig smartspaceConfig) {
        ISmartspaceManager asInterface = ISmartspaceManager.Stub.asInterface(ServiceManager.getService(Context.SMARTSPACE_SERVICE));
        this.mInterface = asInterface;
        SmartspaceSessionId smartspaceSessionId = new SmartspaceSessionId(context.getPackageName() + ":" + UUID.randomUUID().toString(), context.getUser());
        this.mSessionId = smartspaceSessionId;
        try {
            asInterface.createSmartspaceSession(smartspaceConfig, smartspaceSessionId, getToken());
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to create Smartspace session", e);
            e.rethrowFromSystemServer();
        }
        this.mCloseGuard.open("SmartspaceSession.close");
    }

    public void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        try {
            this.mInterface.notifySmartspaceEvent(this.mSessionId, smartspaceTargetEvent);
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

    public void addOnTargetsAvailableListener(Executor executor, final OnTargetsAvailableListener onTargetsAvailableListener) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        if (this.mRegisteredCallbacks.containsKey(onTargetsAvailableListener)) {
            return;
        }
        try {
            Objects.requireNonNull(onTargetsAvailableListener);
            CallbackWrapper callbackWrapper = new CallbackWrapper(executor, new Consumer() { // from class: android.app.smartspace.SmartspaceSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SmartspaceSession.OnTargetsAvailableListener.this.onTargetsAvailable((List) obj);
                }
            });
            this.mRegisteredCallbacks.put(onTargetsAvailableListener, callbackWrapper);
            this.mInterface.registerSmartspaceUpdates(this.mSessionId, callbackWrapper);
            this.mInterface.requestSmartspaceUpdate(this.mSessionId);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to register for smartspace updates", e);
            e.rethrowAsRuntimeException();
        }
    }

    public void removeOnTargetsAvailableListener(OnTargetsAvailableListener onTargetsAvailableListener) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("This client has already been destroyed.");
        }
        if (this.mRegisteredCallbacks.containsKey(onTargetsAvailableListener)) {
            try {
                this.mInterface.unregisterSmartspaceUpdates(this.mSessionId, this.mRegisteredCallbacks.remove(onTargetsAvailableListener));
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to unregister for smartspace updates", e);
                e.rethrowAsRuntimeException();
            }
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
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                destroy();
            }
        } finally {
            try {
                super.finalize();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            destroy();
            finalize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static class CallbackWrapper extends ISmartspaceCallback.Stub {
        private final Consumer<List<SmartspaceTarget>> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor executor, Consumer<List<SmartspaceTarget>> consumer) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.app.smartspace.ISmartspaceCallback
        public void onResult(final ParceledListSlice parceledListSlice) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.app.smartspace.SmartspaceSession$CallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SmartspaceSession.CallbackWrapper.this.lambda$onResult$0(parceledListSlice);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(ParceledListSlice parceledListSlice) {
            this.mCallback.accept(parceledListSlice.getList());
        }
    }

    private static class Token {
        static final IBinder sBinder = new Binder(SmartspaceSession.TAG);

        private Token() {
        }
    }

    private static IBinder getToken() {
        return Token.sBinder;
    }
}
