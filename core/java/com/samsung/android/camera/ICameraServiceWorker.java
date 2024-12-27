package com.samsung.android.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ICameraServiceWorker extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.ICameraServiceWorker";
    public static final int LOGGER_CAMERA_APPLICATION_EVENT = 6;
    public static final int LOGGER_CAMERA_EVENT = 0;
    public static final int LOGGER_DATABASE_EVENT = 7;
    public static final int LOGGER_FOLD_EVENT = 4;
    public static final int LOGGER_POST_PROCESS_EVENT = 5;
    public static final int LOGGER_REQUEST_INJECTOR_SERVICE = 2;
    public static final int LOGGER_SHAKE_EVENT_LISTENER = 1;
    public static final int LOGGER_VISION_SERVER_RECEIVER = 3;
    public static final String SERVICE_NAME = "media.camera.worker";
    public static final int THIRD_PARTY_INTENT_IMAGE_CAPTURE_MAX_RES = 4;
    public static final int THIRD_PARTY_INTENT_PRECAPTURE_TRIGGER = 3;
    public static final int THIRD_PARTY_INTENT_PREVIEW_MAX_RES = 2;
    public static final int THIRD_PARTY_INTENT_VIDEO_DUR = 6;
    public static final int THIRD_PARTY_INTENT_VIDEO_MAX_RES = 5;
    public static final int THIRD_PARTY_LENS_ID = 1;

    IBinder acquireRequestInjector() throws RemoteException;

    boolean getDeviceInjectorOverride(String str, int i) throws RemoteException;

    int getDeviceOrientationForDeviceInjector(String str, int i) throws RemoteException;

    void notifyCameraSessionEvent(int i, String str) throws RemoteException;

    void notifyCameraState(String str, int i, int i2, String str2, int i3) throws RemoteException;

    void pingForUpdate() throws RemoteException;

    String queryPackageName(int i, int i2) throws RemoteException;

    void setDeviceOrientationListener(boolean z) throws RemoteException;

    void storeLoggingData(int i, String str) throws RemoteException;

    public static class Default implements ICameraServiceWorker {
        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void pingForUpdate() throws RemoteException {}

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void notifyCameraState(
                String cameraId, int facing, int newCameraState, String clientName, int apiLevel)
                throws RemoteException {}

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public String queryPackageName(int pid, int uid) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public IBinder acquireRequestInjector() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void notifyCameraSessionEvent(int eventType, String eventDetail)
                throws RemoteException {}

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void setDeviceOrientationListener(boolean enable) throws RemoteException {}

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public int getDeviceOrientationForDeviceInjector(String packageName, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public boolean getDeviceInjectorOverride(String packageName, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void storeLoggingData(int type, String data) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICameraServiceWorker {
        static final int TRANSACTION_acquireRequestInjector = 4;
        static final int TRANSACTION_getDeviceInjectorOverride = 8;
        static final int TRANSACTION_getDeviceOrientationForDeviceInjector = 7;
        static final int TRANSACTION_notifyCameraSessionEvent = 5;
        static final int TRANSACTION_notifyCameraState = 2;
        static final int TRANSACTION_pingForUpdate = 1;
        static final int TRANSACTION_queryPackageName = 3;
        static final int TRANSACTION_setDeviceOrientationListener = 6;
        static final int TRANSACTION_storeLoggingData = 9;

        public Stub() {
            attachInterface(this, ICameraServiceWorker.DESCRIPTOR);
        }

        public static ICameraServiceWorker asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICameraServiceWorker.DESCRIPTOR);
            if (iin != null && (iin instanceof ICameraServiceWorker)) {
                return (ICameraServiceWorker) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "pingForUpdate";
                case 2:
                    return "notifyCameraState";
                case 3:
                    return "queryPackageName";
                case 4:
                    return "acquireRequestInjector";
                case 5:
                    return "notifyCameraSessionEvent";
                case 6:
                    return "setDeviceOrientationListener";
                case 7:
                    return "getDeviceOrientationForDeviceInjector";
                case 8:
                    return "getDeviceInjectorOverride";
                case 9:
                    return "storeLoggingData";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICameraServiceWorker.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICameraServiceWorker.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    pingForUpdate();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyCameraState(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result = queryPackageName(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 4:
                    IBinder _result2 = acquireRequestInjector();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result2);
                    return true;
                case 5:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    notifyCameraSessionEvent(_arg03, _arg13);
                    return true;
                case 6:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDeviceOrientationListener(_arg04);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg05 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result3 = getDeviceOrientationForDeviceInjector(_arg05, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 8:
                    String _arg06 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result4 = getDeviceInjectorOverride(_arg06, _arg15);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 9:
                    int _arg07 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    storeLoggingData(_arg07, _arg16);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICameraServiceWorker {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICameraServiceWorker.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void pingForUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void notifyCameraState(
                    String cameraId,
                    int facing,
                    int newCameraState,
                    String clientName,
                    int apiLevel)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(facing);
                    _data.writeInt(newCameraState);
                    _data.writeString(clientName);
                    _data.writeInt(apiLevel);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public String queryPackageName(int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public IBinder acquireRequestInjector() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void notifyCameraSessionEvent(int eventType, String eventDetail)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeInt(eventType);
                    _data.writeString(eventDetail);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void setDeviceOrientationListener(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public int getDeviceOrientationForDeviceInjector(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public boolean getDeviceInjectorOverride(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void storeLoggingData(int type, String data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(data);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
