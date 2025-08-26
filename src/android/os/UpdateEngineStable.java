package android.os;

import android.os.IUpdateEngineStable;
import android.os.IUpdateEngineStableCallback;

/* loaded from: classes3.dex */
public class UpdateEngineStable {
    private static final String TAG = "UpdateEngineStable";
    private static final String UPDATE_ENGINE_STABLE_SERVICE = "android.os.UpdateEngineStableService";
    private final IUpdateEngineStable mUpdateEngineStable;
    private IUpdateEngineStableCallback mUpdateEngineStableCallback = null;
    private final Object mUpdateEngineStableCallbackLock = new Object();

    public @interface ErrorCode {
    }

    public UpdateEngineStable() {
        IUpdateEngineStable iUpdateEngineStableAsInterface = IUpdateEngineStable.Stub.asInterface(ServiceManager.getService(UPDATE_ENGINE_STABLE_SERVICE));
        this.mUpdateEngineStable = iUpdateEngineStableAsInterface;
        if (iUpdateEngineStableAsInterface == null) {
            throw new IllegalStateException("Failed to find android.os.UpdateEngineStableService");
        }
    }

    public boolean bind(final UpdateEngineStableCallback updateEngineStableCallback, final Handler handler) {
        boolean zBind;
        synchronized (this.mUpdateEngineStableCallbackLock) {
            IUpdateEngineStableCallback.Stub stub = new IUpdateEngineStableCallback.Stub(this) { // from class: android.os.UpdateEngineStable.1
                @Override // android.os.IUpdateEngineStableCallback
                public int getInterfaceVersion() {
                    return 2;
                }

                @Override // android.os.IUpdateEngineStableCallback
                public void onStatusUpdate(final int i, final float f) {
                    Handler handler2 = handler;
                    if (handler2 != null) {
                        handler2.post(new Runnable() { // from class: android.os.UpdateEngineStable.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateEngineStableCallback.onStatusUpdate(i, f);
                            }
                        });
                    } else {
                        updateEngineStableCallback.onStatusUpdate(i, f);
                    }
                }

                @Override // android.os.IUpdateEngineStableCallback
                public void onPayloadApplicationComplete(final int i) {
                    Handler handler2 = handler;
                    if (handler2 != null) {
                        handler2.post(new Runnable() { // from class: android.os.UpdateEngineStable.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                updateEngineStableCallback.onPayloadApplicationComplete(i);
                            }
                        });
                    } else {
                        updateEngineStableCallback.onPayloadApplicationComplete(i);
                    }
                }

                @Override // android.os.IUpdateEngineStableCallback
                public String getInterfaceHash() {
                    return "ee2e6f0bd51391955f79f4d5eeeafc37c668cd40";
                }
            };
            this.mUpdateEngineStableCallback = stub;
            try {
                zBind = this.mUpdateEngineStable.bind(stub);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return zBind;
    }

    public boolean bind(UpdateEngineStableCallback updateEngineStableCallback) {
        return bind(updateEngineStableCallback, null);
    }

    public void applyPayloadFd(ParcelFileDescriptor parcelFileDescriptor, long j, long j2, String[] strArr) {
        try {
            this.mUpdateEngineStable.applyPayloadFd(parcelFileDescriptor, j, j2, strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unbind() {
        synchronized (this.mUpdateEngineStableCallbackLock) {
            IUpdateEngineStableCallback iUpdateEngineStableCallback = this.mUpdateEngineStableCallback;
            if (iUpdateEngineStableCallback == null) {
                return true;
            }
            try {
                boolean zUnbind = this.mUpdateEngineStable.unbind(iUpdateEngineStableCallback);
                this.mUpdateEngineStableCallback = null;
                return zUnbind;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
