package android.os;


public class UpdateEngineStable {
    private static final String TAG = "UpdateEngineStable";
    private static final String UPDATE_ENGINE_STABLE_SERVICE =
            "android.os.UpdateEngineStableService";
    private IUpdateEngineStableCallback mUpdateEngineStableCallback = null;
    private final Object mUpdateEngineStableCallbackLock = new Object();
    private final IUpdateEngineStable mUpdateEngineStable =
            IUpdateEngineStable.Stub.asInterface(
                    ServiceManager.getService(UPDATE_ENGINE_STABLE_SERVICE));

    public @interface ErrorCode {}

    public UpdateEngineStable() {
        if (this.mUpdateEngineStable == null) {
            throw new IllegalStateException("Failed to find android.os.UpdateEngineStableService");
        }
    }

    public boolean bind(final UpdateEngineStableCallback callback, final Handler handler) {
        boolean bind;
        synchronized (this.mUpdateEngineStableCallbackLock) {
            this.mUpdateEngineStableCallback =
                    new IUpdateEngineStableCallback
                            .Stub() { // from class: android.os.UpdateEngineStable.1
                        @Override // android.os.IUpdateEngineStableCallback
                        public void onStatusUpdate(final int status, final float percent) {
                            if (handler != null) {
                                handler.post(
                                        new Runnable() { // from class:
                                                         // android.os.UpdateEngineStable.1.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                callback.onStatusUpdate(status, percent);
                                            }
                                        });
                            } else {
                                callback.onStatusUpdate(status, percent);
                            }
                        }

                        @Override // android.os.IUpdateEngineStableCallback
                        public void onPayloadApplicationComplete(final int errorCode) {
                            if (handler != null) {
                                handler.post(
                                        new Runnable() { // from class:
                                                         // android.os.UpdateEngineStable.1.2
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                callback.onPayloadApplicationComplete(errorCode);
                                            }
                                        });
                            } else {
                                callback.onPayloadApplicationComplete(errorCode);
                            }
                        }

                        @Override // android.os.IUpdateEngineStableCallback
                        public int getInterfaceVersion() {
                            return 2;
                        }

                        @Override // android.os.IUpdateEngineStableCallback
                        public String getInterfaceHash() {
                            return "ee2e6f0bd51391955f79f4d5eeeafc37c668cd40";
                        }
                    };
            try {
                bind = this.mUpdateEngineStable.bind(this.mUpdateEngineStableCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return bind;
    }

    public boolean bind(UpdateEngineStableCallback callback) {
        return bind(callback, null);
    }

    public void applyPayloadFd(
            ParcelFileDescriptor fd, long offset, long size, String[] headerKeyValuePairs) {
        try {
            this.mUpdateEngineStable.applyPayloadFd(fd, offset, size, headerKeyValuePairs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unbind() {
        synchronized (this.mUpdateEngineStableCallbackLock) {
            if (this.mUpdateEngineStableCallback == null) {
                return true;
            }
            try {
                boolean result = this.mUpdateEngineStable.unbind(this.mUpdateEngineStableCallback);
                this.mUpdateEngineStableCallback = null;
                return result;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
