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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIrisServiceReceiver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIrisServiceReceiver)) {
                return (IIrisServiceReceiver) iInterfaceQueryLocalInterface;
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
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(j, i3, i4, i5);
                    return true;
                case 2:
                    long j2 = parcel.readLong();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(j2, i6);
                    return true;
                case 3:
                    long j3 = parcel.readLong();
                    Iris iris = (Iris) parcel.readTypedObject(Iris.CREATOR);
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(j3, iris, bArrCreateByteArray);
                    return true;
                case 4:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAuthenticationFailed(j4);
                    return true;
                case 5:
                    long j5 = parcel.readLong();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(j5, i7);
                    return true;
                case 6:
                    long j6 = parcel.readLong();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(j6, i8, i9);
                    return true;
                case 7:
                    long j7 = parcel.readLong();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onIRImage(j7, bArrCreateByteArray2, i10, i11);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAcquired(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAuthenticationSucceeded(long j, Iris iris, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(iris, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onAuthenticationFailed(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onError(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onRemoved(long j, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceReceiver
            public void onIRImage(long j, byte[] bArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIrisServiceReceiver.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
