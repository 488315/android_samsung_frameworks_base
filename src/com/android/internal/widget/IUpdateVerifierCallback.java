package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IUpdateVerifierCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IUpdateVerifierCallback";

    public static class Default implements IUpdateVerifierCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.IUpdateVerifierCallback
        public void onReceiveSaGuid(String str) throws RemoteException {
        }
    }

    void onReceiveSaGuid(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IUpdateVerifierCallback {
        static final int TRANSACTION_onReceiveSaGuid = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUpdateVerifierCallback.DESCRIPTOR);
        }

        public static IUpdateVerifierCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUpdateVerifierCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUpdateVerifierCallback)) {
                return (IUpdateVerifierCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onReceiveSaGuid";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUpdateVerifierCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUpdateVerifierCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onReceiveSaGuid(readString);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUpdateVerifierCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUpdateVerifierCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IUpdateVerifierCallback
            public void onReceiveSaGuid(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUpdateVerifierCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
