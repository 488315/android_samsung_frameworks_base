package com.android.internal.compat;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IPlatformCompatNative extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.compat.IPlatformCompatNative";

    public static class Default implements IPlatformCompatNative {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompatNative
        public boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompatNative
        public boolean isChangeEnabledByUid(long j, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompatNative
        public void reportChangeByPackageName(long j, String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompatNative
        public void reportChangeByUid(long j, int i) throws RemoteException {
        }
    }

    boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException;

    boolean isChangeEnabledByUid(long j, int i) throws RemoteException;

    void reportChangeByPackageName(long j, String str, int i) throws RemoteException;

    void reportChangeByUid(long j, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IPlatformCompatNative {
        static final int TRANSACTION_isChangeEnabledByPackageName = 3;
        static final int TRANSACTION_isChangeEnabledByUid = 4;
        static final int TRANSACTION_reportChangeByPackageName = 1;
        static final int TRANSACTION_reportChangeByUid = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IPlatformCompatNative.DESCRIPTOR);
        }

        public static IPlatformCompatNative asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPlatformCompatNative.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPlatformCompatNative)) {
                return (IPlatformCompatNative) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "reportChangeByPackageName";
            }
            if (i == 2) {
                return "reportChangeByUid";
            }
            if (i == 3) {
                return "isChangeEnabledByPackageName";
            }
            if (i != 4) {
                return null;
            }
            return "isChangeEnabledByUid";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPlatformCompatNative.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPlatformCompatNative.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                reportChangeByPackageName(readLong, readString, readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                long readLong2 = parcel.readLong();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                reportChangeByUid(readLong2, readInt2);
                parcel2.writeNoException();
            } else if (i == 3) {
                long readLong3 = parcel.readLong();
                String readString2 = parcel.readString();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isChangeEnabledByPackageName = isChangeEnabledByPackageName(readLong3, readString2, readInt3);
                parcel2.writeNoException();
                parcel2.writeBoolean(isChangeEnabledByPackageName);
            } else if (i == 4) {
                long readLong4 = parcel.readLong();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean isChangeEnabledByUid = isChangeEnabledByUid(readLong4, readInt4);
                parcel2.writeNoException();
                parcel2.writeBoolean(isChangeEnabledByUid);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPlatformCompatNative {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPlatformCompatNative.DESCRIPTOR;
            }

            @Override // com.android.internal.compat.IPlatformCompatNative
            public void reportChangeByPackageName(long j, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompatNative.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompatNative
            public void reportChangeByUid(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompatNative.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompatNative
            public boolean isChangeEnabledByPackageName(long j, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompatNative.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompatNative
            public boolean isChangeEnabledByUid(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPlatformCompatNative.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
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
