package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAppOpsActiveCallback extends IInterface {

    public static class Default implements IAppOpsActiveCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsActiveCallback
        public void opActiveChanged(int i, int i2, String str, String str2, int i3, boolean z, int i4, int i5) throws RemoteException {
        }
    }

    void opActiveChanged(int i, int i2, String str, String str2, int i3, boolean z, int i4, int i5) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppOpsActiveCallback {
        public static final String DESCRIPTOR = "com.android.internal.app.IAppOpsActiveCallback";
        static final int TRANSACTION_opActiveChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppOpsActiveCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppOpsActiveCallback)) {
                return (IAppOpsActiveCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "opActiveChanged";
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
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt3 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                opActiveChanged(readInt, readInt2, readString, readString2, readInt3, readBoolean, readInt4, readInt5);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppOpsActiveCallback {
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

            @Override // com.android.internal.app.IAppOpsActiveCallback
            public void opActiveChanged(int i, int i2, String str, String str2, int i3, boolean z, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
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
