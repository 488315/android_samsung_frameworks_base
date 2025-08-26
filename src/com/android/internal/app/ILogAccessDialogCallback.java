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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILogAccessDialogCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILogAccessDialogCallback)) {
                return (ILogAccessDialogCallback) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                approveAccessForClient(i3, string);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                declineAccessForClient(i4, string2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILogAccessDialogCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.ILogAccessDialogCallback
            public void declineAccessForClient(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILogAccessDialogCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
