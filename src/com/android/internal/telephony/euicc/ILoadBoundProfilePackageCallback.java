package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ILoadBoundProfilePackageCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback";

    public static class Default implements ILoadBoundProfilePackageCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback
        public void onComplete(int i, byte[] bArr) throws RemoteException {
        }
    }

    void onComplete(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ILoadBoundProfilePackageCallback {
        static final int TRANSACTION_onComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ILoadBoundProfilePackageCallback.DESCRIPTOR);
        }

        public static ILoadBoundProfilePackageCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILoadBoundProfilePackageCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILoadBoundProfilePackageCallback)) {
                return (ILoadBoundProfilePackageCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILoadBoundProfilePackageCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILoadBoundProfilePackageCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onComplete(readInt, createByteArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ILoadBoundProfilePackageCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILoadBoundProfilePackageCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback
            public void onComplete(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ILoadBoundProfilePackageCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
