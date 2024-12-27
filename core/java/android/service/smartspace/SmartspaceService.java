package android.service.smartspace;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSessionId;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
public abstract class SmartspaceService extends Service {
    private static final boolean DEBUG = false;
    public static final String SERVICE_INTERFACE = "android.service.smartspace.SmartspaceService";
    private static final String TAG = "SmartspaceService";
    private Handler mHandler;
    private final ArrayMap<SmartspaceSessionId, ArrayList<CallbackWrapper>> mSessionCallbacks =
            new ArrayMap<>();
    private final ISmartspaceService mInterface = new AnonymousClass1();

    public abstract void notifySmartspaceEvent(
            SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent);

    public abstract void onCreateSmartspaceSession(
            SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId);

    public abstract void onDestroy(SmartspaceSessionId smartspaceSessionId);

    public abstract void onDestroySmartspaceSession(SmartspaceSessionId smartspaceSessionId);

    public abstract void onRequestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId);

    /* renamed from: android.service.smartspace.SmartspaceService$1, reason: invalid class name */
    class AnonymousClass1 extends ISmartspaceService.Stub {
        AnonymousClass1() {}

        @Override // android.service.smartspace.ISmartspaceService
        public void onCreateSmartspaceSession(
                SmartspaceConfig smartspaceConfig, SmartspaceSessionId sessionId) {
            SmartspaceService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda2
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SmartspaceService) obj)
                                            .doCreateSmartspaceSession(
                                                    (SmartspaceConfig) obj2,
                                                    (SmartspaceSessionId) obj3);
                                }
                            },
                            SmartspaceService.this,
                            smartspaceConfig,
                            sessionId));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void notifySmartspaceEvent(
                SmartspaceSessionId sessionId, SmartspaceTargetEvent event) {
            SmartspaceService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SmartspaceService) obj)
                                            .notifySmartspaceEvent(
                                                    (SmartspaceSessionId) obj2,
                                                    (SmartspaceTargetEvent) obj3);
                                }
                            },
                            SmartspaceService.this,
                            sessionId,
                            event));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void requestSmartspaceUpdate(SmartspaceSessionId sessionId) {
            SmartspaceService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((SmartspaceService) obj)
                                            .doRequestPredictionUpdate((SmartspaceSessionId) obj2);
                                }
                            },
                            SmartspaceService.this,
                            sessionId));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void registerSmartspaceUpdates(
                SmartspaceSessionId sessionId, ISmartspaceCallback callback) {
            SmartspaceService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SmartspaceService) obj)
                                            .doRegisterSmartspaceUpdates(
                                                    (SmartspaceSessionId) obj2,
                                                    (ISmartspaceCallback) obj3);
                                }
                            },
                            SmartspaceService.this,
                            sessionId,
                            callback));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void unregisterSmartspaceUpdates(
                SmartspaceSessionId sessionId, ISmartspaceCallback callback) {
            SmartspaceService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda3
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((SmartspaceService) obj)
                                            .doUnregisterSmartspaceUpdates(
                                                    (SmartspaceSessionId) obj2,
                                                    (ISmartspaceCallback) obj3);
                                }
                            },
                            SmartspaceService.this,
                            sessionId,
                            callback));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onDestroySmartspaceSession(SmartspaceSessionId sessionId) {
            SmartspaceService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                               // android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda5
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((SmartspaceService) obj).doDestroy((SmartspaceSessionId) obj2);
                                }
                            },
                            SmartspaceService.this,
                            sessionId));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        Slog.w(
                TAG,
                "Tried to bind to wrong intent (should be"
                    + " android.service.smartspace.SmartspaceService: "
                        + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreateSmartspaceSession(SmartspaceConfig config, SmartspaceSessionId sessionId) {
        this.mSessionCallbacks.put(sessionId, new ArrayList<>());
        onCreateSmartspaceSession(config, sessionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterSmartspaceUpdates(
            SmartspaceSessionId sessionId, ISmartspaceCallback callback) {
        final ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.e(TAG, "Failed to register for updates for unknown session: " + sessionId);
            return;
        }
        CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
        if (wrapper == null) {
            callbacks.add(
                    new CallbackWrapper(
                            callback,
                            new Consumer() { // from class:
                                             // android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    SmartspaceService.this.lambda$doRegisterSmartspaceUpdates$1(
                                            callbacks, (SmartspaceService.CallbackWrapper) obj);
                                }
                            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterSmartspaceUpdates$1(
            final ArrayList callbacks, final CallbackWrapper callbackWrapper) {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SmartspaceService.this.lambda$doRegisterSmartspaceUpdates$0(
                                callbacks, callbackWrapper);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterSmartspaceUpdates(
            SmartspaceSessionId sessionId, ISmartspaceCallback callback) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks == null) {
            Slog.e(TAG, "Failed to unregister for updates for unknown session: " + sessionId);
        } else {
            CallbackWrapper wrapper = findCallbackWrapper(callbacks, callback);
            lambda$doRegisterSmartspaceUpdates$0(callbacks, wrapper);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRequestPredictionUpdate(SmartspaceSessionId sessionId) {
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks != null && !callbacks.isEmpty()) {
            onRequestSmartspaceUpdate(sessionId);
        }
    }

    private CallbackWrapper findCallbackWrapper(
            ArrayList<CallbackWrapper> callbacks, ISmartspaceCallback callback) {
        for (int i = callbacks.size() - 1; i >= 0; i--) {
            if (callbacks.get(i).isCallback(callback)) {
                return callbacks.get(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterSmartspaceUpdates$0(
            ArrayList<CallbackWrapper> callbacks, CallbackWrapper wrapper) {
        if (callbacks == null || wrapper == null) {
            return;
        }
        callbacks.remove(wrapper);
        wrapper.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy(SmartspaceSessionId sessionId) {
        super.onDestroy();
        ArrayList<CallbackWrapper> callbacks = this.mSessionCallbacks.remove(sessionId);
        if (callbacks != null) {
            callbacks.forEach(
                    new Consumer() { // from class:
                                     // android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SmartspaceService.CallbackWrapper) obj).destroy();
                        }
                    });
        }
        onDestroySmartspaceSession(sessionId);
    }

    public final void updateSmartspaceTargets(
            SmartspaceSessionId sessionId, List<SmartspaceTarget> targets) {
        List<CallbackWrapper> callbacks = this.mSessionCallbacks.get(sessionId);
        if (callbacks != null) {
            for (CallbackWrapper callback : callbacks) {
                callback.accept(targets);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper
            implements Consumer<List<SmartspaceTarget>>, IBinder.DeathRecipient {
        private ISmartspaceCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(ISmartspaceCallback callback, Consumer<CallbackWrapper> onBinderDied) {
            this.mCallback = callback;
            this.mOnBinderDied = onBinderDied;
            if (this.mOnBinderDied != null) {
                try {
                    this.mCallback.asBinder().linkToDeath(this, 0);
                } catch (RemoteException e) {
                    Slog.e(SmartspaceService.TAG, "Failed to link to death: " + e);
                }
            }
        }

        public boolean isCallback(ISmartspaceCallback callback) {
            if (this.mCallback == null) {
                Slog.e(SmartspaceService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return this.mCallback.asBinder().equals(callback.asBinder());
        }

        @Override // java.util.function.Consumer
        public void accept(List<SmartspaceTarget> smartspaceTargets) {
            try {
                if (this.mCallback != null) {
                    this.mCallback.onResult(new ParceledListSlice(smartspaceTargets));
                }
            } catch (RemoteException e) {
                Slog.e(SmartspaceService.TAG, "Error sending result:" + e);
            }
        }

        public void destroy() {
            if (this.mCallback != null && this.mOnBinderDied != null) {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            destroy();
            this.mCallback = null;
            if (this.mOnBinderDied != null) {
                this.mOnBinderDied.accept(this);
            }
        }
    }
}
