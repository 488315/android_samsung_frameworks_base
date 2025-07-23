package android.app.usage;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IStorageStatsManager extends IInterface {

    public static class Default implements IStorageStatsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getCacheBytes(String str, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getCacheQuotaBytes(String str, int i, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getFreeBytes(String str, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public long getTotalBytes(String str, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IStorageStatsManager
        public boolean isQuotaSupported(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IStorageStatsManager
        public boolean isReservedSupported(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IStorageStatsManager
        public StorageStats queryArtManagedStats(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public ParceledListSlice queryCratesForPackage(String str, String str2, int i, String str3) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public ParceledListSlice queryCratesForUid(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public ParceledListSlice queryCratesForUser(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public ExternalStorageStats queryExternalStatsForUser(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public StorageStats queryStatsForPackage(String str, String str2, int i, String str3) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public StorageStats queryStatsForUid(String str, int i, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IStorageStatsManager
        public StorageStats queryStatsForUser(String str, int i, String str2) throws RemoteException {
            return null;
        }
    }

    long getCacheBytes(String str, String str2) throws RemoteException;

    long getCacheQuotaBytes(String str, int i, String str2) throws RemoteException;

    long getFreeBytes(String str, String str2) throws RemoteException;

    long getTotalBytes(String str, String str2) throws RemoteException;

    boolean isQuotaSupported(String str, String str2) throws RemoteException;

    boolean isReservedSupported(String str, String str2) throws RemoteException;

    StorageStats queryArtManagedStats(String str, int i, int i2) throws RemoteException;

    ParceledListSlice queryCratesForPackage(String str, String str2, int i, String str3) throws RemoteException;

    ParceledListSlice queryCratesForUid(String str, int i, String str2) throws RemoteException;

    ParceledListSlice queryCratesForUser(String str, int i, String str2) throws RemoteException;

    ExternalStorageStats queryExternalStatsForUser(String str, int i, String str2) throws RemoteException;

    StorageStats queryStatsForPackage(String str, String str2, int i, String str3) throws RemoteException;

    StorageStats queryStatsForUid(String str, int i, String str2) throws RemoteException;

    StorageStats queryStatsForUser(String str, int i, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IStorageStatsManager {
        public static final String DESCRIPTOR = "android.app.usage.IStorageStatsManager";
        static final int TRANSACTION_getCacheBytes = 5;
        static final int TRANSACTION_getCacheQuotaBytes = 6;
        static final int TRANSACTION_getFreeBytes = 4;
        static final int TRANSACTION_getTotalBytes = 3;
        static final int TRANSACTION_isQuotaSupported = 1;
        static final int TRANSACTION_isReservedSupported = 2;
        static final int TRANSACTION_queryArtManagedStats = 8;
        static final int TRANSACTION_queryCratesForPackage = 12;
        static final int TRANSACTION_queryCratesForUid = 13;
        static final int TRANSACTION_queryCratesForUser = 14;
        static final int TRANSACTION_queryExternalStatsForUser = 11;
        static final int TRANSACTION_queryStatsForPackage = 7;
        static final int TRANSACTION_queryStatsForUid = 9;
        static final int TRANSACTION_queryStatsForUser = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStorageStatsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStorageStatsManager)) {
                return (IStorageStatsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isQuotaSupported";
                case 2:
                    return "isReservedSupported";
                case 3:
                    return "getTotalBytes";
                case 4:
                    return "getFreeBytes";
                case 5:
                    return "getCacheBytes";
                case 6:
                    return "getCacheQuotaBytes";
                case 7:
                    return "queryStatsForPackage";
                case 8:
                    return "queryArtManagedStats";
                case 9:
                    return "queryStatsForUid";
                case 10:
                    return "queryStatsForUser";
                case 11:
                    return "queryExternalStatsForUser";
                case 12:
                    return "queryCratesForPackage";
                case 13:
                    return "queryCratesForUid";
                case 14:
                    return "queryCratesForUser";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isQuotaSupported = isQuotaSupported(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuotaSupported);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isReservedSupported = isReservedSupported(readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReservedSupported);
                    return true;
                case 3:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long totalBytes = getTotalBytes(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalBytes);
                    return true;
                case 4:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long freeBytes = getFreeBytes(readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(freeBytes);
                    return true;
                case 5:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long cacheBytes = getCacheBytes(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheBytes);
                    return true;
                case 6:
                    String readString11 = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long cacheQuotaBytes = getCacheQuotaBytes(readString11, readInt, readString12);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheQuotaBytes);
                    return true;
                case 7:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StorageStats queryStatsForPackage = queryStatsForPackage(readString13, readString14, readInt2, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryStatsForPackage, 1);
                    return true;
                case 8:
                    String readString16 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StorageStats queryArtManagedStats = queryArtManagedStats(readString16, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryArtManagedStats, 1);
                    return true;
                case 9:
                    String readString17 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StorageStats queryStatsForUid = queryStatsForUid(readString17, readInt5, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryStatsForUid, 1);
                    return true;
                case 10:
                    String readString19 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StorageStats queryStatsForUser = queryStatsForUser(readString19, readInt6, readString20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryStatsForUser, 1);
                    return true;
                case 11:
                    String readString21 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ExternalStorageStats queryExternalStatsForUser = queryExternalStatsForUser(readString21, readInt7, readString22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryExternalStatsForUser, 1);
                    return true;
                case 12:
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryCratesForPackage = queryCratesForPackage(readString23, readString24, readInt8, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryCratesForPackage, 1);
                    return true;
                case 13:
                    String readString26 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryCratesForUid = queryCratesForUid(readString26, readInt9, readString27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryCratesForUid, 1);
                    return true;
                case 14:
                    String readString28 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryCratesForUser = queryCratesForUser(readString28, readInt10, readString29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryCratesForUser, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IStorageStatsManager {
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

            @Override // android.app.usage.IStorageStatsManager
            public boolean isQuotaSupported(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public boolean isReservedSupported(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getTotalBytes(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getFreeBytes(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getCacheBytes(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getCacheQuotaBytes(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryStatsForPackage(String str, String str2, int i, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StorageStats) obtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryArtManagedStats(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StorageStats) obtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryStatsForUid(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StorageStats) obtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryStatsForUser(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StorageStats) obtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ExternalStorageStats queryExternalStatsForUser(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ExternalStorageStats) obtain2.readTypedObject(ExternalStorageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ParceledListSlice queryCratesForPackage(String str, String str2, int i, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ParceledListSlice queryCratesForUid(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ParceledListSlice queryCratesForUser(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
