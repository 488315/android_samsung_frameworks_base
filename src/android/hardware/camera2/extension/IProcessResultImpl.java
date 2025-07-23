package android.hardware.camera2.extension;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IProcessResultImpl extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IProcessResultImpl";

    public static class Default implements IProcessResultImpl {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureCompleted(long j, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IProcessResultImpl
        public void onCaptureProcessProgressed(int i) throws RemoteException {
        }
    }

    void onCaptureCompleted(long j, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    void onCaptureProcessProgressed(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IProcessResultImpl {
        static final int TRANSACTION_onCaptureCompleted = 1;
        static final int TRANSACTION_onCaptureProcessProgressed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IProcessResultImpl.DESCRIPTOR);
        }

        public static IProcessResultImpl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProcessResultImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProcessResultImpl)) {
                return (IProcessResultImpl) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCaptureCompleted";
            }
            if (i != 2) {
                return null;
            }
            return "onCaptureProcessProgressed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProcessResultImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProcessResultImpl.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                parcel.enforceNoDataAvail();
                onCaptureCompleted(readLong, cameraMetadataNative);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCaptureProcessProgressed(readInt);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProcessResultImpl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProcessResultImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IProcessResultImpl
            public void onCaptureCompleted(long j, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProcessResultImpl.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IProcessResultImpl
            public void onCaptureProcessProgressed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProcessResultImpl.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
