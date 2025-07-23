package android.hardware.camera2.extension;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICaptureCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.ICaptureCallback";

    public static class Default implements ICaptureCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureCompleted(long j, int i, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureFailed(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessFailed(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessProgressed(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessStarted(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceAborted(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceCompleted(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureStarted(int i, long j) throws RemoteException {
        }
    }

    void onCaptureCompleted(long j, int i, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    void onCaptureFailed(int i) throws RemoteException;

    void onCaptureProcessFailed(int i, int i2) throws RemoteException;

    void onCaptureProcessProgressed(int i) throws RemoteException;

    void onCaptureProcessStarted(int i) throws RemoteException;

    void onCaptureSequenceAborted(int i) throws RemoteException;

    void onCaptureSequenceCompleted(int i) throws RemoteException;

    void onCaptureStarted(int i, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ICaptureCallback {
        static final int TRANSACTION_onCaptureCompleted = 6;
        static final int TRANSACTION_onCaptureFailed = 3;
        static final int TRANSACTION_onCaptureProcessFailed = 8;
        static final int TRANSACTION_onCaptureProcessProgressed = 7;
        static final int TRANSACTION_onCaptureProcessStarted = 2;
        static final int TRANSACTION_onCaptureSequenceAborted = 5;
        static final int TRANSACTION_onCaptureSequenceCompleted = 4;
        static final int TRANSACTION_onCaptureStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, ICaptureCallback.DESCRIPTOR);
        }

        public static ICaptureCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICaptureCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICaptureCallback)) {
                return (ICaptureCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCaptureStarted";
                case 2:
                    return "onCaptureProcessStarted";
                case 3:
                    return "onCaptureFailed";
                case 4:
                    return "onCaptureSequenceCompleted";
                case 5:
                    return "onCaptureSequenceAborted";
                case 6:
                    return "onCaptureCompleted";
                case 7:
                    return "onCaptureProcessProgressed";
                case 8:
                    return "onCaptureProcessFailed";
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
                parcel.enforceInterface(ICaptureCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICaptureCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureStarted(readInt, readLong);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureProcessStarted(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureFailed(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureSequenceCompleted(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureSequenceAborted(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    long readLong2 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureCompleted(readLong2, readInt6, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureProcessProgressed(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureProcessFailed(readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICaptureCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICaptureCallback.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureStarted(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureProcessStarted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureFailed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureSequenceCompleted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureSequenceAborted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureCompleted(long j, int i, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureProcessProgressed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ICaptureCallback
            public void onCaptureProcessFailed(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICaptureCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
