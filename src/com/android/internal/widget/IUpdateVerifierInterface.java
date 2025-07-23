package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.widget.IUpdateVerifierCallback;

/* loaded from: classes6.dex */
public interface IUpdateVerifierInterface extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IUpdateVerifierInterface";

    public static class Default implements IUpdateVerifierInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifier(byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifierWithType(byte[] bArr, byte[] bArr2, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifierWithWk(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws RemoteException {
        }
    }

    void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException;

    void updateVerifier(byte[] bArr, byte[] bArr2) throws RemoteException;

    void updateVerifierWithType(byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    void updateVerifierWithWk(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IUpdateVerifierInterface {
        static final int TRANSACTION_requestSaGuid = 1;
        static final int TRANSACTION_updateVerifier = 2;
        static final int TRANSACTION_updateVerifierWithType = 3;
        static final int TRANSACTION_updateVerifierWithWk = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IUpdateVerifierInterface.DESCRIPTOR);
        }

        public static IUpdateVerifierInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUpdateVerifierInterface.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUpdateVerifierInterface)) {
                return (IUpdateVerifierInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "requestSaGuid";
            }
            if (i == 2) {
                return "updateVerifier";
            }
            if (i == 3) {
                return "updateVerifierWithType";
            }
            if (i != 4) {
                return null;
            }
            return "updateVerifierWithWk";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUpdateVerifierInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUpdateVerifierInterface.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IUpdateVerifierCallback asInterface = IUpdateVerifierCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                requestSaGuid(asInterface);
            } else if (i == 2) {
                byte[] createByteArray = parcel.createByteArray();
                byte[] createByteArray2 = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                updateVerifier(createByteArray, createByteArray2);
            } else if (i == 3) {
                byte[] createByteArray3 = parcel.createByteArray();
                byte[] createByteArray4 = parcel.createByteArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                updateVerifierWithType(createByteArray3, createByteArray4, readInt);
            } else if (i == 4) {
                byte[] createByteArray5 = parcel.createByteArray();
                byte[] createByteArray6 = parcel.createByteArray();
                byte[] createByteArray7 = parcel.createByteArray();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                updateVerifierWithWk(createByteArray5, createByteArray6, createByteArray7, readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUpdateVerifierInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUpdateVerifierInterface.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    obtain.writeStrongInterface(iUpdateVerifierCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void updateVerifier(byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void updateVerifierWithType(byte[] bArr, byte[] bArr2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void updateVerifierWithWk(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
