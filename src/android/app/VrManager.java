package android.app;

import android.annotation.SystemApi;
import android.app.VrManager;
import android.content.ComponentName;
import android.os.RemoteException;
import android.service.vr.IPersistentVrStateCallbacks;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.ArrayMap;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public class VrManager {
    private Map<VrStateCallback, CallbackEntry> mCallbackMap = new ArrayMap();
    private final IVrManager mService;

    public void setVrInputMethod(ComponentName componentName) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackEntry {
        final VrStateCallback mCallback;
        final Executor mExecutor;
        final IVrStateCallbacks mStateCallback = new AnonymousClass1();
        final IPersistentVrStateCallbacks mPersistentStateCallback = new AnonymousClass2();

        /* renamed from: android.app.VrManager$CallbackEntry$1, reason: invalid class name */
        class AnonymousClass1 extends IVrStateCallbacks.Stub {
            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onVrStateChanged$0(boolean z) {
                CallbackEntry.this.mCallback.onVrStateChanged(z);
            }

            @Override // android.service.vr.IVrStateCallbacks
            public void onVrStateChanged(final boolean z) {
                CallbackEntry.this.mExecutor.execute(new Runnable() { // from class: android.app.VrManager$CallbackEntry$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VrManager.CallbackEntry.AnonymousClass1.this.lambda$onVrStateChanged$0(z);
                    }
                });
            }
        }

        /* renamed from: android.app.VrManager$CallbackEntry$2, reason: invalid class name */
        class AnonymousClass2 extends IPersistentVrStateCallbacks.Stub {
            AnonymousClass2() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onPersistentVrStateChanged$0(boolean z) {
                CallbackEntry.this.mCallback.onPersistentVrStateChanged(z);
            }

            @Override // android.service.vr.IPersistentVrStateCallbacks
            public void onPersistentVrStateChanged(final boolean z) {
                CallbackEntry.this.mExecutor.execute(new Runnable() { // from class: android.app.VrManager$CallbackEntry$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VrManager.CallbackEntry.AnonymousClass2.this.lambda$onPersistentVrStateChanged$0(z);
                    }
                });
            }
        }

        CallbackEntry(VrStateCallback vrStateCallback, Executor executor) {
            this.mCallback = vrStateCallback;
            this.mExecutor = executor;
        }
    }

    public VrManager(IVrManager iVrManager) {
        this.mService = iVrManager;
    }

    public void registerVrStateCallback(Executor executor, VrStateCallback vrStateCallback) {
        if (vrStateCallback == null || this.mCallbackMap.containsKey(vrStateCallback)) {
            return;
        }
        CallbackEntry callbackEntry = new CallbackEntry(vrStateCallback, executor);
        this.mCallbackMap.put(vrStateCallback, callbackEntry);
        try {
            this.mService.registerListener(callbackEntry.mStateCallback);
            this.mService.registerPersistentVrStateListener(callbackEntry.mPersistentStateCallback);
        } catch (RemoteException e) {
            try {
                unregisterVrStateCallback(vrStateCallback);
            } catch (Exception unused) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterVrStateCallback(VrStateCallback vrStateCallback) {
        CallbackEntry remove = this.mCallbackMap.remove(vrStateCallback);
        if (remove != null) {
            try {
                this.mService.unregisterListener(remove.mStateCallback);
            } catch (RemoteException unused) {
            }
            try {
                this.mService.unregisterPersistentVrStateListener(remove.mPersistentStateCallback);
            } catch (RemoteException unused2) {
            }
        }
    }

    public boolean isVrModeEnabled() {
        try {
            return this.mService.getVrModeState();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isPersistentVrModeEnabled() {
        try {
            return this.mService.getPersistentVrModeEnabled();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void setPersistentVrModeEnabled(boolean z) {
        try {
            this.mService.setPersistentVrModeEnabled(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties) {
        try {
            this.mService.setVr2dDisplayProperties(vr2dDisplayProperties);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setAndBindVrCompositor(ComponentName componentName) {
        try {
            this.mService.setAndBindCompositor(componentName == null ? null : componentName.flattenToString());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setStandbyEnabled(boolean z) {
        try {
            this.mService.setStandbyEnabled(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public int getVr2dDisplayId() {
        try {
            return this.mService.getVr2dDisplayId();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }
}
