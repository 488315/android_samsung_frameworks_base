package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.IImsExternalCallStateListener;

/* loaded from: classes5.dex */
public interface IImsMultiEndpoint extends IInterface {

    public static class Default implements IImsMultiEndpoint {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void requestImsExternalCallStateInfo() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void setListener(IImsExternalCallStateListener iImsExternalCallStateListener) throws RemoteException {
        }
    }

    void requestImsExternalCallStateInfo() throws RemoteException;

    void setListener(IImsExternalCallStateListener iImsExternalCallStateListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsMultiEndpoint {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsMultiEndpoint";
        static final int TRANSACTION_requestImsExternalCallStateInfo = 2;
        static final int TRANSACTION_setListener = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMultiEndpoint asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsMultiEndpoint)) {
                return (IImsMultiEndpoint) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setListener";
            }
            if (i != 2) {
                return null;
            }
            return "requestImsExternalCallStateInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IImsExternalCallStateListener asInterface = IImsExternalCallStateListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                requestImsExternalCallStateInfo();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IImsMultiEndpoint {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsMultiEndpoint
            public void setListener(IImsExternalCallStateListener iImsExternalCallStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsExternalCallStateListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMultiEndpoint
            public void requestImsExternalCallStateInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
