package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAppOpsNotedCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IAppOpsNotedCallback";

    public static class Default implements IAppOpsNotedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsNotedCallback
        public void opNoted(int i, int i2, String str, String str2, int i3, int i4, int i5) throws RemoteException {
        }
    }

    void opNoted(int i, int i2, String str, String str2, int i3, int i4, int i5) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppOpsNotedCallback {
        static final int TRANSACTION_opNoted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAppOpsNotedCallback.DESCRIPTOR);
        }

        public static IAppOpsNotedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppOpsNotedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppOpsNotedCallback)) {
                return (IAppOpsNotedCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "opNoted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppOpsNotedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppOpsNotedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                opNoted(readInt, readInt2, readString, readString2, readInt3, readInt4, readInt5);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppOpsNotedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppOpsNotedCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsNotedCallback
            public void opNoted(int i, int i2, String str, String str2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAppOpsNotedCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
