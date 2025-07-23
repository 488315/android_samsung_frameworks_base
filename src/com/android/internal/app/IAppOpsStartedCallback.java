package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAppOpsStartedCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IAppOpsStartedCallback";

    public static class Default implements IAppOpsStartedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IAppOpsStartedCallback
        public void opStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException {
        }
    }

    void opStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppOpsStartedCallback {
        static final int TRANSACTION_opStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAppOpsStartedCallback.DESCRIPTOR);
        }

        public static IAppOpsStartedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppOpsStartedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppOpsStartedCallback)) {
                return (IAppOpsStartedCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "opStarted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppOpsStartedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppOpsStartedCallback.DESCRIPTOR);
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
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                int readInt8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                opStarted(readInt, readInt2, readString, readString2, readInt3, readInt4, readInt5, readInt6, readInt7, readInt8);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppOpsStartedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppOpsStartedCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsStartedCallback
            public void opStarted(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAppOpsStartedCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeInt(i8);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
