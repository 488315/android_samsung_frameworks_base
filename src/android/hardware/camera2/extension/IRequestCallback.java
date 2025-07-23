package android.hardware.camera2.extension;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.extension.IRequestCallback";

    public static class Default implements IRequestCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureBufferLost(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureCompleted(int i, ParcelTotalCaptureResult parcelTotalCaptureResult) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureFailed(int i, CaptureFailure captureFailure) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureProgressed(int i, ParcelCaptureResult parcelCaptureResult) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceAborted(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceCompleted(int i, long j) throws RemoteException {
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureStarted(int i, long j, long j2) throws RemoteException {
        }
    }

    void onCaptureBufferLost(int i, long j, int i2) throws RemoteException;

    void onCaptureCompleted(int i, ParcelTotalCaptureResult parcelTotalCaptureResult) throws RemoteException;

    void onCaptureFailed(int i, CaptureFailure captureFailure) throws RemoteException;

    void onCaptureProgressed(int i, ParcelCaptureResult parcelCaptureResult) throws RemoteException;

    void onCaptureSequenceAborted(int i) throws RemoteException;

    void onCaptureSequenceCompleted(int i, long j) throws RemoteException;

    void onCaptureStarted(int i, long j, long j2) throws RemoteException;

    public static abstract class Stub extends Binder implements IRequestCallback {
        static final int TRANSACTION_onCaptureBufferLost = 5;
        static final int TRANSACTION_onCaptureCompleted = 3;
        static final int TRANSACTION_onCaptureFailed = 4;
        static final int TRANSACTION_onCaptureProgressed = 2;
        static final int TRANSACTION_onCaptureSequenceAborted = 7;
        static final int TRANSACTION_onCaptureSequenceCompleted = 6;
        static final int TRANSACTION_onCaptureStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IRequestCallback.DESCRIPTOR);
        }

        public static IRequestCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRequestCallback)) {
                return (IRequestCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCaptureStarted";
                case 2:
                    return "onCaptureProgressed";
                case 3:
                    return "onCaptureCompleted";
                case 4:
                    return "onCaptureFailed";
                case 5:
                    return "onCaptureBufferLost";
                case 6:
                    return "onCaptureSequenceCompleted";
                case 7:
                    return "onCaptureSequenceAborted";
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
                parcel.enforceInterface(IRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRequestCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureStarted(readInt, readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    ParcelCaptureResult parcelCaptureResult = (ParcelCaptureResult) parcel.readTypedObject(ParcelCaptureResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureProgressed(readInt2, parcelCaptureResult);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    ParcelTotalCaptureResult parcelTotalCaptureResult = (ParcelTotalCaptureResult) parcel.readTypedObject(ParcelTotalCaptureResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureCompleted(readInt3, parcelTotalCaptureResult);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    CaptureFailure captureFailure = (CaptureFailure) parcel.readTypedObject(CaptureFailure.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCaptureFailed(readInt4, captureFailure);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureBufferLost(readInt5, readLong3, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureSequenceCompleted(readInt7, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCaptureSequenceAborted(readInt8);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestCallback.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureStarted(int i, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureProgressed(int i, ParcelCaptureResult parcelCaptureResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelCaptureResult, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureCompleted(int i, ParcelTotalCaptureResult parcelTotalCaptureResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelTotalCaptureResult, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureFailed(int i, CaptureFailure captureFailure) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(captureFailure, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureBufferLost(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureSequenceCompleted(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IRequestCallback
            public void onCaptureSequenceAborted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
