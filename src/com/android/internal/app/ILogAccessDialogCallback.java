package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ILogAccessDialogCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.ILogAccessDialogCallback";

    public static class Default implements ILogAccessDialogCallback {
        @Override // com.android.internal.app.ILogAccessDialogCallback
        public void approveAccessForClient(int i, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.ILogAccessDialogCallback
        public void declineAccessForClient(int i, String str) throws RemoteException {
        }
    }

    void approveAccessForClient(int i, String str) throws RemoteException;

    void declineAccessForClient(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ILogAccessDialogCallback {
        static final int TRANSACTION_approveAccessForClient = 1;
        static final int TRANSACTION_declineAccessForClient = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ILogAccessDialogCallback.DESCRIPTOR);
        }

        public static ILogAccessDialogCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILogAccessDialogCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILogAccessDialogCallback)) {
                return (ILogAccessDialogCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "approveAccessForClient";
            }
            if (i != 2) {
                return null;
            }
            return "declineAccessForClient";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILogAccessDialogCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILogAccessDialogCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                approveAccessForClient(readInt, readString);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                declineAccessForClient(readInt2, readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILogAccessDialogCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILogAccessDialogCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.ILogAccessDialogCallback
            public void approveAccessForClient(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ILogAccessDialogCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.ILogAccessDialogCallback
            public void declineAccessForClient(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ILogAccessDialogCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
