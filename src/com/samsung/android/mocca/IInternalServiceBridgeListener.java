package com.samsung.android.mocca;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IInternalServiceBridgeListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mocca.IInternalServiceBridgeListener";

    public static class Default implements IInternalServiceBridgeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mocca.IInternalServiceBridgeListener
        public void onUpdated(String str, Bundle bundle) throws RemoteException {
        }
    }

    void onUpdated(String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IInternalServiceBridgeListener {
        static final int TRANSACTION_onUpdated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IInternalServiceBridgeListener.DESCRIPTOR);
        }

        public static IInternalServiceBridgeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInternalServiceBridgeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInternalServiceBridgeListener)) {
                return (IInternalServiceBridgeListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInternalServiceBridgeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInternalServiceBridgeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onUpdated(readString, bundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInternalServiceBridgeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInternalServiceBridgeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridgeListener
            public void onUpdated(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInternalServiceBridgeListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
