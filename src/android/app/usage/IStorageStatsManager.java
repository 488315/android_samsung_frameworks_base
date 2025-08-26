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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStorageStatsManager)) {
                return (IStorageStatsManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsQuotaSupported = isQuotaSupported(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsQuotaSupported);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsReservedSupported = isReservedSupported(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsReservedSupported);
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long totalBytes = getTotalBytes(string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalBytes);
                    return true;
                case 4:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long freeBytes = getFreeBytes(string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeLong(freeBytes);
                    return true;
                case 5:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long cacheBytes = getCacheBytes(string9, string10);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheBytes);
                    return true;
                case 6:
                    String string11 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long cacheQuotaBytes = getCacheQuotaBytes(string11, i3, string12);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheQuotaBytes);
                    return true;
                case 7:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StorageStats storageStatsQueryStatsForPackage = queryStatsForPackage(string13, string14, i4, string15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(storageStatsQueryStatsForPackage, 1);
                    return true;
                case 8:
                    String string16 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StorageStats storageStatsQueryArtManagedStats = queryArtManagedStats(string16, i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(storageStatsQueryArtManagedStats, 1);
                    return true;
                case 9:
                    String string17 = parcel.readString();
                    int i7 = parcel.readInt();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StorageStats storageStatsQueryStatsForUid = queryStatsForUid(string17, i7, string18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(storageStatsQueryStatsForUid, 1);
                    return true;
                case 10:
                    String string19 = parcel.readString();
                    int i8 = parcel.readInt();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StorageStats storageStatsQueryStatsForUser = queryStatsForUser(string19, i8, string20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(storageStatsQueryStatsForUser, 1);
                    return true;
                case 11:
                    String string21 = parcel.readString();
                    int i9 = parcel.readInt();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ExternalStorageStats externalStorageStatsQueryExternalStatsForUser = queryExternalStatsForUser(string21, i9, string22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(externalStorageStatsQueryExternalStatsForUser, 1);
                    return true;
                case 12:
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    int i10 = parcel.readInt();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryCratesForPackage = queryCratesForPackage(string23, string24, i10, string25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryCratesForPackage, 1);
                    return true;
                case 13:
                    String string26 = parcel.readString();
                    int i11 = parcel.readInt();
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryCratesForUid = queryCratesForUid(string26, i11, string27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryCratesForUid, 1);
                    return true;
                case 14:
                    String string28 = parcel.readString();
                    int i12 = parcel.readInt();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryCratesForUser = queryCratesForUser(string28, i12, string29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryCratesForUser, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public boolean isReservedSupported(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getTotalBytes(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getFreeBytes(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getCacheBytes(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public long getCacheQuotaBytes(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryStatsForPackage(String str, String str2, int i, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StorageStats) parcelObtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryArtManagedStats(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StorageStats) parcelObtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryStatsForUid(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StorageStats) parcelObtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public StorageStats queryStatsForUser(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StorageStats) parcelObtain2.readTypedObject(StorageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ExternalStorageStats queryExternalStatsForUser(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ExternalStorageStats) parcelObtain2.readTypedObject(ExternalStorageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ParceledListSlice queryCratesForPackage(String str, String str2, int i, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ParceledListSlice queryCratesForUid(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IStorageStatsManager
            public ParceledListSlice queryCratesForUser(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
