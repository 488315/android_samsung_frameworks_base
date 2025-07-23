package com.samsung.android.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
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

    public static class Default implements ICameraServiceWorker {
        @Override // com.samsung.android.camera.ICameraServiceWorker
        public IBinder acquireRequestInjector() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public boolean getDeviceInjectorOverride(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public int getDeviceOrientationForDeviceInjector(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void notifyCameraSessionEvent(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void notifyCameraState(String str, int i, int i2, String str2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void pingForUpdate() throws RemoteException {
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public String queryPackageName(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void setDeviceOrientationListener(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.camera.ICameraServiceWorker
        public void storeLoggingData(int i, String str) throws RemoteException {
        }
    }

    IBinder acquireRequestInjector() throws RemoteException;

    boolean getDeviceInjectorOverride(String str, int i) throws RemoteException;

    int getDeviceOrientationForDeviceInjector(String str, int i) throws RemoteException;

    void notifyCameraSessionEvent(int i, String str) throws RemoteException;

    void notifyCameraState(String str, int i, int i2, String str2, int i3) throws RemoteException;

    void pingForUpdate() throws RemoteException;

    String queryPackageName(int i, int i2) throws RemoteException;

    void setDeviceOrientationListener(boolean z) throws RemoteException;

    void storeLoggingData(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraServiceWorker {
        static final int TRANSACTION_acquireRequestInjector = 4;
        static final int TRANSACTION_getDeviceInjectorOverride = 8;
        static final int TRANSACTION_getDeviceOrientationForDeviceInjector = 7;
        static final int TRANSACTION_notifyCameraSessionEvent = 5;
        static final int TRANSACTION_notifyCameraState = 2;
        static final int TRANSACTION_pingForUpdate = 1;
        static final int TRANSACTION_queryPackageName = 3;
        static final int TRANSACTION_setDeviceOrientationListener = 6;
        static final int TRANSACTION_storeLoggingData = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ICameraServiceWorker.DESCRIPTOR);
        }

        public static ICameraServiceWorker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICameraServiceWorker.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICameraServiceWorker)) {
                return (ICameraServiceWorker) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICameraServiceWorker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICameraServiceWorker.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    pingForUpdate();
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCameraState(readString, readInt, readInt2, readString2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String queryPackageName = queryPackageName(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeString(queryPackageName);
                    return true;
                case 4:
                    IBinder acquireRequestInjector = acquireRequestInjector();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(acquireRequestInjector);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCameraSessionEvent(readInt6, readString3);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientationListener(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString4 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceOrientationForDeviceInjector = getDeviceOrientationForDeviceInjector(readString4, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceOrientationForDeviceInjector);
                    return true;
                case 8:
                    String readString5 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deviceInjectorOverride = getDeviceInjectorOverride(readString5, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceInjectorOverride);
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    storeLoggingData(readInt9, readString6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraServiceWorker {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void notifyCameraState(String str, int i, int i2, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public String queryPackageName(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public IBinder acquireRequestInjector() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void notifyCameraSessionEvent(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void setDeviceOrientationListener(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public int getDeviceOrientationForDeviceInjector(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public boolean getDeviceInjectorOverride(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void storeLoggingData(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICameraServiceWorker.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
