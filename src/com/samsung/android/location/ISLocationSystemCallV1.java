package com.samsung.android.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISLocationSystemCallV1 extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.location.ISLocationSystemCallV1";

    public static class Default implements ISLocationSystemCallV1 {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.location.ISLocationSystemCallV1
        public boolean isProviderEnabledForUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.location.ISLocationSystemCallV1
        public boolean isUidForeground(int i) throws RemoteException {
            return false;
        }
    }

    boolean isProviderEnabledForUser(String str, int i) throws RemoteException;

    boolean isUidForeground(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISLocationSystemCallV1 {
        static final int TRANSACTION_isProviderEnabledForUser = 1;
        static final int TRANSACTION_isUidForeground = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISLocationSystemCallV1.DESCRIPTOR);
        }

        public static ISLocationSystemCallV1 asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISLocationSystemCallV1.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISLocationSystemCallV1)) {
                return (ISLocationSystemCallV1) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "isProviderEnabledForUser";
            }
            if (i != 2) {
                return null;
            }
            return "isUidForeground";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISLocationSystemCallV1.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISLocationSystemCallV1.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isProviderEnabledForUser = isProviderEnabledForUser(readString, readInt);
                parcel2.writeNoException();
                parcel2.writeBoolean(isProviderEnabledForUser);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isUidForeground = isUidForeground(readInt2);
                parcel2.writeNoException();
                parcel2.writeBoolean(isUidForeground);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISLocationSystemCallV1 {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISLocationSystemCallV1.DESCRIPTOR;
            }

            @Override // com.samsung.android.location.ISLocationSystemCallV1
            public boolean isProviderEnabledForUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationSystemCallV1.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.location.ISLocationSystemCallV1
            public boolean isUidForeground(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISLocationSystemCallV1.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
