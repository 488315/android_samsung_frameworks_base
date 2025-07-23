package android.hardware;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRemoteDeviceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IRemoteDeviceCallback";
    public static final int ERROR_REMOTE_BUFFER = 1;
    public static final int ERROR_REMOTE_DEVICE = 0;
    public static final int ERROR_REMOTE_UNKNOWN = 2;

    public static class Default implements IRemoteDeviceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.IRemoteDeviceCallback
        public void onCaptureResult(CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.IRemoteDeviceCallback
        public void onError(int i) throws RemoteException {
        }

        @Override // android.hardware.IRemoteDeviceCallback
        public void onOrientationChanged(int i) throws RemoteException {
        }
    }

    void onCaptureResult(CameraMetadataNative cameraMetadataNative) throws RemoteException;

    void onError(int i) throws RemoteException;

    void onOrientationChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteDeviceCallback {
        static final int TRANSACTION_onCaptureResult = 1;
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onOrientationChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IRemoteDeviceCallback.DESCRIPTOR);
        }

        public static IRemoteDeviceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteDeviceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteDeviceCallback)) {
                return (IRemoteDeviceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCaptureResult";
            }
            if (i == 2) {
                return "onError";
            }
            if (i != 3) {
                return null;
            }
            return "onOrientationChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteDeviceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteDeviceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                parcel.enforceNoDataAvail();
                onCaptureResult(cameraMetadataNative);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onOrientationChanged(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteDeviceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteDeviceCallback.DESCRIPTOR;
            }

            @Override // android.hardware.IRemoteDeviceCallback
            public void onCaptureResult(CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteDeviceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDeviceCallback
            public void onError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteDeviceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDeviceCallback
            public void onOrientationChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteDeviceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
