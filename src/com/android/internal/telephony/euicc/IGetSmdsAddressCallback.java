package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IGetSmdsAddressCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetSmdsAddressCallback";

    public static class Default implements IGetSmdsAddressCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IGetSmdsAddressCallback
        public void onComplete(int i, String str) throws RemoteException {
        }
    }

    void onComplete(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetSmdsAddressCallback {
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
            attachInterface(this, IGetSmdsAddressCallback.DESCRIPTOR);
        }

        public static IGetSmdsAddressCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGetSmdsAddressCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGetSmdsAddressCallback)) {
                return (IGetSmdsAddressCallback) queryLocalInterface;
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
                parcel.enforceInterface(IGetSmdsAddressCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetSmdsAddressCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onComplete(readInt, readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGetSmdsAddressCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetSmdsAddressCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IGetSmdsAddressCallback
            public void onComplete(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetSmdsAddressCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
