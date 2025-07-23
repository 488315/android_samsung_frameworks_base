package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAuthenticateServerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IAuthenticateServerCallback";

    public static class Default implements IAuthenticateServerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
        public void onComplete(int i, byte[] bArr) throws RemoteException {
        }
    }

    void onComplete(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IAuthenticateServerCallback {
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
            attachInterface(this, IAuthenticateServerCallback.DESCRIPTOR);
        }

        public static IAuthenticateServerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthenticateServerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAuthenticateServerCallback)) {
                return (IAuthenticateServerCallback) queryLocalInterface;
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
                parcel.enforceInterface(IAuthenticateServerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthenticateServerCallback.DESCRIPTOR);
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

        private static class Proxy implements IAuthenticateServerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthenticateServerCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IAuthenticateServerCallback
            public void onComplete(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAuthenticateServerCallback.DESCRIPTOR);
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
