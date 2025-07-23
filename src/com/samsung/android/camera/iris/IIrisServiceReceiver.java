package com.samsung.android.camera.iris;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIrisServiceReceiver extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisServiceReceiver";

    public static class Default implements IIrisServiceReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAcquired(long j, int i) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationFailed(long j) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onAuthenticationSucceeded(long j, Iris iris, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onError(long j, int i) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onIRImage(long j, byte[] bArr, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
        public void onRemoved(long j, int i, int i2) throws RemoteException {
        }
    }

    void onAcquired(long j, int i) throws RemoteException;

    void onAuthenticationFailed(long j) throws RemoteException;

    void onAuthenticationSucceeded(long j, Iris iris, byte[] bArr) throws RemoteException;

    void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException;

    void onError(long j, int i) throws RemoteException;

    void onIRImage(long j, byte[] bArr, int i, int i2) throws RemoteException;

    void onRemoved(long j, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IIrisServiceReceiver {
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 4;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onIRImage = 7;
        static final int TRANSACTION_onRemoved = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IIrisServiceReceiver.DESCRIPTOR);
        }

        public static IIrisServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIrisServiceReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIrisServiceReceiver)) {
                return (IIrisServiceReceiver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onAuthenticationFailed";
                case 5:
                    return "onError";
                case 6:
                    return "onRemoved";
                case 7:
                    return "onIRImage";
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
                parcel.enforceInterface(IIrisServiceReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIrisServiceReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(readLong, readInt, readInt2, readInt3);
                    return true;
                case 2:
                    long readLong2 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(readLong2, readInt4);
                    return true;
                case 3:
                    long readLong3 = parcel.readLong();
                    Iris iris = (Iris) parcel.readTypedObject(Iris.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(readLong3, iris, createByteArray);
                    return true;
                case 4:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAuthenticationFailed(readLong4);
                    return true;
                case 5:
                    long readLong5 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readLong5, readInt5);
                    return true;
                case 6:
                    long readLong6 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(readLong6, readInt6, readInt7);
                    return true;
                case 7:
                    long readLong7 = parcel.readLong();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onIRImage(readLong7, createByteArray2, readInt8, readInt9);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIrisServiceReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisServiceReceiver.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAcquired(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAuthenticationSucceeded(long j, Iris iris, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(iris, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAuthenticationFailed(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onError(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onRemoved(long j, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onIRImage(long j, byte[] bArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
