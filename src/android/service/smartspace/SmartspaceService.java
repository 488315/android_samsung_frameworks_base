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
import android.service.smartspace.ISmartspaceService;
import android.service.smartspace.SmartspaceService;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class SmartspaceService extends Service {
    private static final boolean DEBUG = false;
    public static final String SERVICE_INTERFACE = "android.service.smartspace.SmartspaceService";
    private static final String TAG = "SmartspaceService";
    private Handler mHandler;
    private final ArrayMap<SmartspaceSessionId, ArrayList<CallbackWrapper>> mSessionCallbacks = new ArrayMap<>();
    private final ISmartspaceService mInterface = new AnonymousClass1();

    public abstract void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent);

    public abstract void onCreateSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId);

    public abstract void onDestroy(SmartspaceSessionId smartspaceSessionId);

    public abstract void onDestroySmartspaceSession(SmartspaceSessionId smartspaceSessionId);

    public abstract void onRequestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId);

    /* renamed from: android.service.smartspace.SmartspaceService$1, reason: invalid class name */
    class AnonymousClass1 extends ISmartspaceService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onCreateSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId) {
            SmartspaceService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SmartspaceService) obj).doCreateSmartspaceSession((SmartspaceConfig) obj2, (SmartspaceSessionId) obj3);
                }
            }, SmartspaceService.this, smartspaceConfig, smartspaceSessionId));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) {
            SmartspaceService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SmartspaceService) obj).notifySmartspaceEvent((SmartspaceSessionId) obj2, (SmartspaceTargetEvent) obj3);
                }
            }, SmartspaceService.this, smartspaceSessionId, smartspaceTargetEvent));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) {
            SmartspaceService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((SmartspaceService) obj).doRequestPredictionUpdate((SmartspaceSessionId) obj2);
                }
            }, SmartspaceService.this, smartspaceSessionId));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) {
            SmartspaceService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SmartspaceService) obj).doRegisterSmartspaceUpdates((SmartspaceSessionId) obj2, (ISmartspaceCallback) obj3);
                }
            }, SmartspaceService.this, smartspaceSessionId, iSmartspaceCallback));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) {
            SmartspaceService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((SmartspaceService) obj).doUnregisterSmartspaceUpdates((SmartspaceSessionId) obj2, (ISmartspaceCallback) obj3);
                }
            }, SmartspaceService.this, smartspaceSessionId, iSmartspaceCallback));
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onDestroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) {
            SmartspaceService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.smartspace.SmartspaceService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((SmartspaceService) obj).doDestroy((SmartspaceSessionId) obj2);
                }
            }, SmartspaceService.this, smartspaceSessionId));
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
        Slog.w(TAG, "Tried to bind to wrong intent (should be android.service.smartspace.SmartspaceService: " + intent);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreateSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId) {
        this.mSessionCallbacks.put(smartspaceSessionId, new ArrayList<>());
        onCreateSmartspaceSession(smartspaceConfig, smartspaceSessionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) {
        final ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList == null) {
            Slog.e(TAG, "Failed to register for updates for unknown session: " + smartspaceSessionId);
        } else if (findCallbackWrapper(arrayList, iSmartspaceCallback) == null) {
            arrayList.add(new CallbackWrapper(iSmartspaceCallback, new Consumer() { // from class: android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$doRegisterSmartspaceUpdates$1(arrayList, (SmartspaceService.CallbackWrapper) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doRegisterSmartspaceUpdates$1(final ArrayList arrayList, final CallbackWrapper callbackWrapper) {
        this.mHandler.post(new Runnable() { // from class: android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$doRegisterSmartspaceUpdates$0(arrayList, callbackWrapper);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList == null) {
            Slog.e(TAG, "Failed to unregister for updates for unknown session: " + smartspaceSessionId);
            return;
        }
        lambda$doRegisterSmartspaceUpdates$0(arrayList, findCallbackWrapper(arrayList, iSmartspaceCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRequestPredictionUpdate(SmartspaceSessionId smartspaceSessionId) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        onRequestSmartspaceUpdate(smartspaceSessionId);
    }

    private CallbackWrapper findCallbackWrapper(ArrayList<CallbackWrapper> arrayList, ISmartspaceCallback iSmartspaceCallback) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).isCallback(iSmartspaceCallback)) {
                return arrayList.get(size);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCallbackWrapper, reason: merged with bridge method [inline-methods] */
    public void lambda$doRegisterSmartspaceUpdates$0(ArrayList<CallbackWrapper> arrayList, CallbackWrapper callbackWrapper) {
        if (arrayList == null || callbackWrapper == null) {
            return;
        }
        arrayList.remove(callbackWrapper);
        callbackWrapper.destroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy(SmartspaceSessionId smartspaceSessionId) {
        super.onDestroy();
        ArrayList<CallbackWrapper> arrayListRemove = this.mSessionCallbacks.remove(smartspaceSessionId);
        if (arrayListRemove != null) {
            arrayListRemove.forEach(new Consumer() { // from class: android.service.smartspace.SmartspaceService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((SmartspaceService.CallbackWrapper) obj).destroy();
                }
            });
        }
        onDestroySmartspaceSession(smartspaceSessionId);
    }

    public final void updateSmartspaceTargets(SmartspaceSessionId smartspaceSessionId, List<SmartspaceTarget> list) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(smartspaceSessionId);
        if (arrayList != null) {
            Iterator<CallbackWrapper> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().accept(list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackWrapper implements Consumer<List<SmartspaceTarget>>, IBinder.DeathRecipient {
        private ISmartspaceCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(ISmartspaceCallback iSmartspaceCallback, Consumer<CallbackWrapper> consumer) {
            this.mCallback = iSmartspaceCallback;
            this.mOnBinderDied = consumer;
            if (consumer != null) {
                try {
                    iSmartspaceCallback.asBinder().linkToDeath(this, 0);
                } catch (RemoteException e) {
                    Slog.e(SmartspaceService.TAG, "Failed to link to death: " + e);
                }
            }
        }

        public boolean isCallback(ISmartspaceCallback iSmartspaceCallback) {
            ISmartspaceCallback iSmartspaceCallback2 = this.mCallback;
            if (iSmartspaceCallback2 == null) {
                Slog.e(SmartspaceService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return iSmartspaceCallback2.asBinder().equals(iSmartspaceCallback.asBinder());
        }

        @Override // java.util.function.Consumer
        public void accept(List<SmartspaceTarget> list) {
            try {
                ISmartspaceCallback iSmartspaceCallback = this.mCallback;
                if (iSmartspaceCallback != null) {
                    iSmartspaceCallback.onResult(new ParceledListSlice(list));
                }
            } catch (RemoteException e) {
                Slog.e(SmartspaceService.TAG, "Error sending result:" + e);
            }
        }

        public void destroy() {
            ISmartspaceCallback iSmartspaceCallback = this.mCallback;
            if (iSmartspaceCallback == null || this.mOnBinderDied == null) {
                return;
            }
            iSmartspaceCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            destroy();
            this.mCallback = null;
            Consumer<CallbackWrapper> consumer = this.mOnBinderDied;
            if (consumer != null) {
                consumer.accept(this);
            }
        }
    }
}
