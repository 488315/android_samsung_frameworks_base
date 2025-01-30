package com.samsung.android.mocca;

import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.util.Log;
import com.samsung.android.mocca.IMoccaEventListener;
import com.samsung.android.mocca.SemMdContextManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes5.dex */
public class SemMdContextManager {
    public static final String CONTEXT_TYPE_ALL = "all-context type";
    public static final String CONTEXT_TYPE_CAR_CRASH = "ccd";
    private static final String TAG = "SemMdContextManager";
    private HashMap<AvailabilityCallback, MoccaListenerTransport> mAvailabilityCallbacks = new HashMap<>();
    private HashMap<ContextEventCallback, MoccaListenerTransport> mContextEventCallbacks = new HashMap<>();
    private IMoccaService mService;

    public interface AvailabilityCallback {
        void onContextAvailable(String str);

        void onContextUnavailable(String str);
    }

    public interface ContextEventCallback {
        void onContextChanged(SemMdContextEvent semMdContextEvent);

        void onContextStopped(String str);
    }

    public SemMdContextManager(IMoccaService service) {
        this.mService = service;
    }

    public List<String> getSupportedTypes() {
        IMoccaService iMoccaService = this.mService;
        if (iMoccaService == null) {
            Log.m96e(TAG, "SemMdContextService is not supported");
            return Collections.EMPTY_LIST;
        }
        try {
            List<String> supportedTypes = iMoccaService.getSupportedTypes();
            return supportedTypes == null ? Collections.EMPTY_LIST : supportedTypes;
        } catch (RemoteException e) {
            Log.m97e(TAG, "getSupportedTypes : RemoteException :" + e.getMessage(), e);
            return Collections.EMPTY_LIST;
        }
    }

    public boolean registerAvailabilityCallback(AvailabilityCallback callback, String contextType) {
        if (this.mService == null) {
            Log.m96e(TAG, "RegisterAvailabilityCallback - SemMdContextService is not supported");
            return false;
        }
        if (callback == null || contextType == null) {
            Log.m96e(TAG, "RegisterAvailabilityCallback - callback or contextType is null");
            return false;
        }
        synchronized (this.mAvailabilityCallbacks) {
            try {
                try {
                    MoccaListenerTransport transport = this.mAvailabilityCallbacks.computeIfAbsent(callback, new Function() { // from class: com.samsung.android.mocca.SemMdContextManager$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return SemMdContextManager.lambda$registerAvailabilityCallback$0((SemMdContextManager.AvailabilityCallback) obj);
                        }
                    });
                    if (CONTEXT_TYPE_ALL.equals(contextType)) {
                        return this.mService.registerContextAvailabilityListener(transport, null);
                    }
                    return this.mService.registerContextAvailabilityListener(transport, contextType);
                } catch (RemoteException | ClassCastException | NullPointerException | UnsupportedOperationException e) {
                    Log.m97e(TAG, "registerAvailabilityCallbackImpl : " + e.getMessage(), e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static /* synthetic */ MoccaListenerTransport lambda$registerAvailabilityCallback$0(AvailabilityCallback cb) {
        return new MoccaListenerTransport(cb, null);
    }

    public void unregisterAvailabilityCallback(AvailabilityCallback callback, String contextType) {
        MoccaListenerTransport transport;
        if (this.mService == null) {
            Log.m96e(TAG, "unregisterAvailabilityCallback- SemMdContextService is not supported");
            return;
        }
        if (callback == null || contextType == null) {
            Log.m96e(TAG, "UnregisterAvailabilityCallback - callback or contextType is null");
            return;
        }
        synchronized (this.mAvailabilityCallbacks) {
            try {
                transport = this.mAvailabilityCallbacks.get(callback);
            } catch (RemoteException e) {
                Log.m97e(TAG, "unregisterAvailabilityCallback : " + e.getMessage(), e);
            }
            if (transport == null) {
                return;
            }
            if (CONTEXT_TYPE_ALL.equals(contextType)) {
                this.mService.unregisterContextAvailabilityListener(transport, null);
            } else {
                this.mService.unregisterContextAvailabilityListener(transport, contextType);
            }
            if (!this.mService.hasContextAvailabilityListener(transport)) {
                this.mAvailabilityCallbacks.remove(callback);
            }
        }
    }

    public boolean registerContextEventCallback(ContextEventCallback callback, String contextType, Bundle param) {
        if (this.mService == null) {
            Log.m96e(TAG, "registerContextEventCallback- SemMdContextService is not supported");
            return false;
        }
        if (callback == null || contextType == null) {
            Log.m96e(TAG, "registerContextEventCallback - callback or contextType is null");
            return false;
        }
        if (CONTEXT_TYPE_ALL.equals(contextType)) {
            return false;
        }
        synchronized (this.mContextEventCallbacks) {
            try {
                try {
                    MoccaListenerTransport transport = this.mContextEventCallbacks.computeIfAbsent(callback, new Function() { // from class: com.samsung.android.mocca.SemMdContextManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return SemMdContextManager.lambda$registerContextEventCallback$1((SemMdContextManager.ContextEventCallback) obj);
                        }
                    });
                    this.mService.registerContextListener(transport, contextType, param != null ? new ContextParam(param) : null);
                } catch (RemoteException e) {
                    Log.m97e(TAG, "registerContextEventCallback-registerContextListener : " + e.getMessage(), e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    static /* synthetic */ MoccaListenerTransport lambda$registerContextEventCallback$1(ContextEventCallback cb) {
        return new MoccaListenerTransport(null, cb);
    }

    public void unregisterContextEventCallback(ContextEventCallback callback, String contextType) {
        MoccaListenerTransport transport;
        if (this.mService == null) {
            Log.m96e(TAG, "unregisterContextEventCallback - SemMdContextService is not supported");
            return;
        }
        if (callback == null || contextType == null) {
            Log.m96e(TAG, "unregisterContextEventCallback - callback or contextType is null");
            return;
        }
        synchronized (this.mContextEventCallbacks) {
            try {
                transport = this.mContextEventCallbacks.get(callback);
            } catch (RemoteException e) {
                Log.m97e(TAG, "unregisterContextEventCallback : " + e.getMessage(), e);
            }
            if (transport == null) {
                return;
            }
            if (CONTEXT_TYPE_ALL.equals(contextType)) {
                this.mService.unregisterContextListener(transport, null);
            } else {
                this.mService.unregisterContextListener(transport, contextType);
            }
            if (!this.mService.hasContextListener(transport)) {
                this.mContextEventCallbacks.remove(callback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MoccaListenerTransport extends IMoccaEventListener.Stub {
        private static final int MSG_CONTEXT_AVAILABLE = 3;
        private static final int MSG_CONTEXT_CHANGED = 1;
        private static final int MSG_CONTEXT_STOPPED = 2;
        private static final int MSG_CONTEXT_UNAVAILABLE = 4;
        private AvailabilityCallback mAvailabilityCallback;
        private ContextEventCallback mContextEventCallback;
        private final Handler mListenerHandler = new Handler() { // from class: com.samsung.android.mocca.SemMdContextManager.MoccaListenerTransport.1
            @Override // android.p009os.Handler
            public void handleMessage(Message msg) {
                MoccaListenerTransport.this._handleMessage(msg);
            }
        };

        MoccaListenerTransport(AvailabilityCallback availabilityCallback, ContextEventCallback contextEventCallback) {
            this.mAvailabilityCallback = availabilityCallback;
            this.mContextEventCallback = contextEventCallback;
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextChanged(ContextEvent event) {
            this.mListenerHandler.obtainMessage(1, event).sendToTarget();
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextStopped(String contextType) {
            this.mListenerHandler.obtainMessage(2, contextType).sendToTarget();
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextAvailable(String contextType) {
            this.mListenerHandler.obtainMessage(3, contextType).sendToTarget();
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextUnavailable(String contextType) {
            this.mListenerHandler.obtainMessage(4, contextType).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void _handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (this.mContextEventCallback != null) {
                        ContextEvent evt = (ContextEvent) msg.obj;
                        this.mContextEventCallback.onContextChanged(new SemMdContextEvent(evt.timestamp, evt.type, evt.data));
                        break;
                    }
                    break;
                case 2:
                    ContextEventCallback contextEventCallback = this.mContextEventCallback;
                    if (contextEventCallback != null) {
                        contextEventCallback.onContextStopped((String) msg.obj);
                        break;
                    }
                    break;
                case 3:
                    AvailabilityCallback availabilityCallback = this.mAvailabilityCallback;
                    if (availabilityCallback != null) {
                        availabilityCallback.onContextAvailable((String) msg.obj);
                        break;
                    }
                    break;
                case 4:
                    AvailabilityCallback availabilityCallback2 = this.mAvailabilityCallback;
                    if (availabilityCallback2 != null) {
                        availabilityCallback2.onContextUnavailable((String) msg.obj);
                        break;
                    }
                    break;
            }
        }
    }
}
