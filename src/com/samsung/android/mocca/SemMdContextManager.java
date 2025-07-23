package com.samsung.android.mocca;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.mocca.IMoccaEventListener;
import com.samsung.android.mocca.SemMdContextManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes6.dex */
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

    public SemMdContextManager(IMoccaService iMoccaService) {
        this.mService = iMoccaService;
    }

    public List<String> getSupportedTypes() {
        IMoccaService iMoccaService = this.mService;
        if (iMoccaService == null) {
            Log.e(TAG, "SemMdContextService is not supported");
            return Collections.EMPTY_LIST;
        }
        try {
            List<String> supportedTypes = iMoccaService.getSupportedTypes();
            return supportedTypes == null ? Collections.EMPTY_LIST : supportedTypes;
        } catch (RemoteException e) {
            Log.e(TAG, "getSupportedTypes : RemoteException :" + e.getMessage(), e);
            return Collections.EMPTY_LIST;
        }
    }

    public boolean registerAvailabilityCallback(AvailabilityCallback availabilityCallback, String str) {
        if (this.mService == null) {
            Log.e(TAG, "RegisterAvailabilityCallback - SemMdContextService is not supported");
            return false;
        }
        if (availabilityCallback == null || str == null) {
            Log.e(TAG, "RegisterAvailabilityCallback - callback or contextType is null");
            return false;
        }
        synchronized (this.mAvailabilityCallbacks) {
            try {
                try {
                    MoccaListenerTransport computeIfAbsent = this.mAvailabilityCallbacks.computeIfAbsent(availabilityCallback, new Function() { // from class: com.samsung.android.mocca.SemMdContextManager$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return SemMdContextManager.lambda$registerAvailabilityCallback$0((SemMdContextManager.AvailabilityCallback) obj);
                        }
                    });
                    if (CONTEXT_TYPE_ALL.equals(str)) {
                        return this.mService.registerContextAvailabilityListener(computeIfAbsent, null);
                    }
                    return this.mService.registerContextAvailabilityListener(computeIfAbsent, str);
                } catch (RemoteException | ClassCastException | NullPointerException | UnsupportedOperationException e) {
                    Log.e(TAG, "registerAvailabilityCallbackImpl : " + e.getMessage(), e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static /* synthetic */ MoccaListenerTransport lambda$registerAvailabilityCallback$0(AvailabilityCallback availabilityCallback) {
        return new MoccaListenerTransport(availabilityCallback, null);
    }

    public void unregisterAvailabilityCallback(AvailabilityCallback availabilityCallback, String str) {
        MoccaListenerTransport moccaListenerTransport;
        if (this.mService == null) {
            Log.e(TAG, "unregisterAvailabilityCallback- SemMdContextService is not supported");
            return;
        }
        if (availabilityCallback == null || str == null) {
            Log.e(TAG, "UnregisterAvailabilityCallback - callback or contextType is null");
            return;
        }
        synchronized (this.mAvailabilityCallbacks) {
            try {
                moccaListenerTransport = this.mAvailabilityCallbacks.get(availabilityCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "unregisterAvailabilityCallback : " + e.getMessage(), e);
            }
            if (moccaListenerTransport == null) {
                return;
            }
            if (CONTEXT_TYPE_ALL.equals(str)) {
                this.mService.unregisterContextAvailabilityListener(moccaListenerTransport, null);
            } else {
                this.mService.unregisterContextAvailabilityListener(moccaListenerTransport, str);
            }
            if (!this.mService.hasContextAvailabilityListener(moccaListenerTransport)) {
                this.mAvailabilityCallbacks.remove(availabilityCallback);
            }
        }
    }

    public boolean registerContextEventCallback(ContextEventCallback contextEventCallback, String str, Bundle bundle) {
        if (this.mService == null) {
            Log.e(TAG, "registerContextEventCallback- SemMdContextService is not supported");
            return false;
        }
        if (contextEventCallback == null || str == null) {
            Log.e(TAG, "registerContextEventCallback - callback or contextType is null");
            return false;
        }
        if (CONTEXT_TYPE_ALL.equals(str)) {
            return false;
        }
        synchronized (this.mContextEventCallbacks) {
            try {
                try {
                    this.mService.registerContextListener(this.mContextEventCallbacks.computeIfAbsent(contextEventCallback, new Function() { // from class: com.samsung.android.mocca.SemMdContextManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return SemMdContextManager.lambda$registerContextEventCallback$1((SemMdContextManager.ContextEventCallback) obj);
                        }
                    }), str, bundle != null ? new ContextParam(bundle) : null);
                } catch (RemoteException e) {
                    Log.e(TAG, "registerContextEventCallback-registerContextListener : " + e.getMessage(), e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    static /* synthetic */ MoccaListenerTransport lambda$registerContextEventCallback$1(ContextEventCallback contextEventCallback) {
        return new MoccaListenerTransport(null, contextEventCallback);
    }

    public void unregisterContextEventCallback(ContextEventCallback contextEventCallback, String str) {
        MoccaListenerTransport moccaListenerTransport;
        if (this.mService == null) {
            Log.e(TAG, "unregisterContextEventCallback - SemMdContextService is not supported");
            return;
        }
        if (contextEventCallback == null || str == null) {
            Log.e(TAG, "unregisterContextEventCallback - callback or contextType is null");
            return;
        }
        synchronized (this.mContextEventCallbacks) {
            try {
                moccaListenerTransport = this.mContextEventCallbacks.get(contextEventCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "unregisterContextEventCallback : " + e.getMessage(), e);
            }
            if (moccaListenerTransport == null) {
                return;
            }
            if (CONTEXT_TYPE_ALL.equals(str)) {
                this.mService.unregisterContextListener(moccaListenerTransport, null);
            } else {
                this.mService.unregisterContextListener(moccaListenerTransport, str);
            }
            if (!this.mService.hasContextListener(moccaListenerTransport)) {
                this.mContextEventCallbacks.remove(contextEventCallback);
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
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                MoccaListenerTransport.this._handleMessage(message);
            }
        };

        MoccaListenerTransport(AvailabilityCallback availabilityCallback, ContextEventCallback contextEventCallback) {
            this.mAvailabilityCallback = availabilityCallback;
            this.mContextEventCallback = contextEventCallback;
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextChanged(ContextEvent contextEvent) {
            this.mListenerHandler.obtainMessage(1, contextEvent).sendToTarget();
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextStopped(String str) {
            this.mListenerHandler.obtainMessage(2, str).sendToTarget();
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextAvailable(String str) {
            this.mListenerHandler.obtainMessage(3, str).sendToTarget();
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextUnavailable(String str) {
            this.mListenerHandler.obtainMessage(4, str).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void _handleMessage(Message message) {
            AvailabilityCallback availabilityCallback;
            int i = message.what;
            if (i == 1) {
                if (this.mContextEventCallback != null) {
                    ContextEvent contextEvent = (ContextEvent) message.obj;
                    this.mContextEventCallback.onContextChanged(new SemMdContextEvent(contextEvent.timestamp, contextEvent.type, contextEvent.data));
                    return;
                }
                return;
            }
            if (i == 2) {
                ContextEventCallback contextEventCallback = this.mContextEventCallback;
                if (contextEventCallback != null) {
                    contextEventCallback.onContextStopped((String) message.obj);
                    return;
                }
                return;
            }
            if (i != 3) {
                if (i == 4 && (availabilityCallback = this.mAvailabilityCallback) != null) {
                    availabilityCallback.onContextUnavailable((String) message.obj);
                    return;
                }
                return;
            }
            AvailabilityCallback availabilityCallback2 = this.mAvailabilityCallback;
            if (availabilityCallback2 != null) {
                availabilityCallback2.onContextAvailable((String) message.obj);
            }
        }
    }
}
