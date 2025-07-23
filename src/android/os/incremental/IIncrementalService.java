package android.os.incremental;

import android.content.pm.DataLoaderParamsParcel;
import android.content.pm.IDataLoaderStatusListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.incremental.IStorageHealthListener;
import android.os.incremental.IStorageLoadingProgressListener;

/* loaded from: classes3.dex */
public interface IIncrementalService extends IInterface {
    public static final int BIND_PERMANENT = 1;
    public static final int BIND_TEMPORARY = 0;
    public static final int CREATE_MODE_CREATE = 4;
    public static final int CREATE_MODE_OPEN_EXISTING = 8;
    public static final int CREATE_MODE_PERMANENT_BIND = 2;
    public static final int CREATE_MODE_TEMPORARY_BIND = 1;
    public static final String DESCRIPTOR = "android.os.incremental.IIncrementalService";
    public static final String METRICS_DATA_LOADER_BIND_DELAY_MILLIS = "dataLoaderBindDelayMillis";
    public static final String METRICS_DATA_LOADER_STATUS_CODE = "dataLoaderStatusCode";
    public static final String METRICS_LAST_READ_ERROR_NUMBER = "lastReadErrorNo";
    public static final String METRICS_LAST_READ_ERROR_UID = "lastReadErrorUid";
    public static final String METRICS_MILLIS_SINCE_LAST_DATA_LOADER_BIND = "millisSinceLastDataLoaderBind";
    public static final String METRICS_MILLIS_SINCE_LAST_READ_ERROR = "millisSinceLastReadError";
    public static final String METRICS_MILLIS_SINCE_OLDEST_PENDING_READ = "millisSinceOldestPendingRead";
    public static final String METRICS_READ_LOGS_ENABLED = "readLogsEnabled";
    public static final String METRICS_STORAGE_HEALTH_STATUS_CODE = "storageHealthStatusCode";
    public static final String METRICS_TOTAL_DELAYED_READS = "totalDelayedReads";
    public static final String METRICS_TOTAL_DELAYED_READS_MILLIS = "totalDelayedReadsMillis";
    public static final String METRICS_TOTAL_FAILED_READS = "totalFailedReads";

    public static class Default implements IIncrementalService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean configureNativeBinaries(int i, String str, String str2, String str3, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public int createLinkedStorage(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int createStorage(String str, DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int deleteBindMount(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public void deleteStorage(int i) throws RemoteException {
        }

        @Override // android.os.incremental.IIncrementalService
        public void disallowReadLogs(int i) throws RemoteException {
        }

        @Override // android.os.incremental.IIncrementalService
        public float getLoadingProgress(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.os.incremental.IIncrementalService
        public byte[] getMetadataById(int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.os.incremental.IIncrementalService
        public byte[] getMetadataByPath(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.os.incremental.IIncrementalService
        public PersistableBundle getMetrics(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.incremental.IIncrementalService
        public int isFileFullyLoaded(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int isFullyLoaded(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeBindMount(int i, String str, String str2, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeDirectories(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeDirectory(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeFile(int i, String str, int i2, IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeFileFromRange(int i, String str, String str2, long j, long j2) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public int makeLink(int i, String str, int i2, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public void onInstallationComplete(int i) throws RemoteException {
        }

        @Override // android.os.incremental.IIncrementalService
        public int openStorage(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean registerLoadingProgressListener(int i, IStorageLoadingProgressListener iStorageLoadingProgressListener) throws RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean startLoading(int i, DataLoaderParamsParcel dataLoaderParamsParcel, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, PerUidReadTimeouts[] perUidReadTimeoutsArr) throws RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public int unlink(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean unregisterLoadingProgressListener(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.incremental.IIncrementalService
        public boolean waitForNativeBinariesExtraction(int i) throws RemoteException {
            return false;
        }
    }

    boolean configureNativeBinaries(int i, String str, String str2, String str3, boolean z) throws RemoteException;

    int createLinkedStorage(String str, int i, int i2) throws RemoteException;

    int createStorage(String str, DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws RemoteException;

    int deleteBindMount(int i, String str) throws RemoteException;

    void deleteStorage(int i) throws RemoteException;

    void disallowReadLogs(int i) throws RemoteException;

    float getLoadingProgress(int i) throws RemoteException;

    byte[] getMetadataById(int i, byte[] bArr) throws RemoteException;

    byte[] getMetadataByPath(int i, String str) throws RemoteException;

    PersistableBundle getMetrics(int i) throws RemoteException;

    int isFileFullyLoaded(int i, String str) throws RemoteException;

    int isFullyLoaded(int i) throws RemoteException;

    int makeBindMount(int i, String str, String str2, int i2) throws RemoteException;

    int makeDirectories(int i, String str) throws RemoteException;

    int makeDirectory(int i, String str) throws RemoteException;

    int makeFile(int i, String str, int i2, IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws RemoteException;

    int makeFileFromRange(int i, String str, String str2, long j, long j2) throws RemoteException;

    int makeLink(int i, String str, int i2, String str2) throws RemoteException;

    void onInstallationComplete(int i) throws RemoteException;

    int openStorage(String str) throws RemoteException;

    boolean registerLoadingProgressListener(int i, IStorageLoadingProgressListener iStorageLoadingProgressListener) throws RemoteException;

    boolean startLoading(int i, DataLoaderParamsParcel dataLoaderParamsParcel, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, PerUidReadTimeouts[] perUidReadTimeoutsArr) throws RemoteException;

    int unlink(int i, String str) throws RemoteException;

    boolean unregisterLoadingProgressListener(int i) throws RemoteException;

    boolean waitForNativeBinariesExtraction(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IIncrementalService {
        static final int TRANSACTION_configureNativeBinaries = 21;
        static final int TRANSACTION_createLinkedStorage = 3;
        static final int TRANSACTION_createStorage = 2;
        static final int TRANSACTION_deleteBindMount = 7;
        static final int TRANSACTION_deleteStorage = 19;
        static final int TRANSACTION_disallowReadLogs = 20;
        static final int TRANSACTION_getLoadingProgress = 16;
        static final int TRANSACTION_getMetadataById = 18;
        static final int TRANSACTION_getMetadataByPath = 17;
        static final int TRANSACTION_getMetrics = 25;
        static final int TRANSACTION_isFileFullyLoaded = 14;
        static final int TRANSACTION_isFullyLoaded = 15;
        static final int TRANSACTION_makeBindMount = 6;
        static final int TRANSACTION_makeDirectories = 9;
        static final int TRANSACTION_makeDirectory = 8;
        static final int TRANSACTION_makeFile = 10;
        static final int TRANSACTION_makeFileFromRange = 11;
        static final int TRANSACTION_makeLink = 12;
        static final int TRANSACTION_onInstallationComplete = 5;
        static final int TRANSACTION_openStorage = 1;
        static final int TRANSACTION_registerLoadingProgressListener = 23;
        static final int TRANSACTION_startLoading = 4;
        static final int TRANSACTION_unlink = 13;
        static final int TRANSACTION_unregisterLoadingProgressListener = 24;
        static final int TRANSACTION_waitForNativeBinariesExtraction = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }

        public Stub() {
            attachInterface(this, IIncrementalService.DESCRIPTOR);
        }

        public static IIncrementalService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIncrementalService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncrementalService)) {
                return (IIncrementalService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openStorage";
                case 2:
                    return "createStorage";
                case 3:
                    return "createLinkedStorage";
                case 4:
                    return "startLoading";
                case 5:
                    return "onInstallationComplete";
                case 6:
                    return "makeBindMount";
                case 7:
                    return "deleteBindMount";
                case 8:
                    return "makeDirectory";
                case 9:
                    return "makeDirectories";
                case 10:
                    return "makeFile";
                case 11:
                    return "makeFileFromRange";
                case 12:
                    return "makeLink";
                case 13:
                    return "unlink";
                case 14:
                    return "isFileFullyLoaded";
                case 15:
                    return "isFullyLoaded";
                case 16:
                    return "getLoadingProgress";
                case 17:
                    return "getMetadataByPath";
                case 18:
                    return "getMetadataById";
                case 19:
                    return "deleteStorage";
                case 20:
                    return "disallowReadLogs";
                case 21:
                    return "configureNativeBinaries";
                case 22:
                    return "waitForNativeBinariesExtraction";
                case 23:
                    return "registerLoadingProgressListener";
                case 24:
                    return "unregisterLoadingProgressListener";
                case 25:
                    return "getMetrics";
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
                parcel.enforceInterface(IIncrementalService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIncrementalService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int openStorage = openStorage(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(openStorage);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    DataLoaderParamsParcel dataLoaderParamsParcel = (DataLoaderParamsParcel) parcel.readTypedObject(DataLoaderParamsParcel.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createStorage = createStorage(readString2, dataLoaderParamsParcel, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(createStorage);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createLinkedStorage = createLinkedStorage(readString3, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createLinkedStorage);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    DataLoaderParamsParcel dataLoaderParamsParcel2 = (DataLoaderParamsParcel) parcel.readTypedObject(DataLoaderParamsParcel.CREATOR);
                    IDataLoaderStatusListener asInterface = IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    StorageHealthCheckParams storageHealthCheckParams = (StorageHealthCheckParams) parcel.readTypedObject(StorageHealthCheckParams.CREATOR);
                    IStorageHealthListener asInterface2 = IStorageHealthListener.Stub.asInterface(parcel.readStrongBinder());
                    PerUidReadTimeouts[] perUidReadTimeoutsArr = (PerUidReadTimeouts[]) parcel.createTypedArray(PerUidReadTimeouts.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startLoading = startLoading(readInt4, dataLoaderParamsParcel2, asInterface, storageHealthCheckParams, asInterface2, perUidReadTimeoutsArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startLoading);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInstallationComplete(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int makeBindMount = makeBindMount(readInt6, readString4, readString5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeBindMount);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int deleteBindMount = deleteBindMount(readInt8, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(deleteBindMount);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int makeDirectory = makeDirectory(readInt9, readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeDirectory);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int makeDirectories = makeDirectories(readInt10, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeDirectories);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    String readString9 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    IncrementalNewFileParams incrementalNewFileParams = (IncrementalNewFileParams) parcel.readTypedObject(IncrementalNewFileParams.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int makeFile = makeFile(readInt11, readString9, readInt12, incrementalNewFileParams, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeFile);
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int makeFileFromRange = makeFileFromRange(readInt13, readString10, readString11, readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeFileFromRange);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    String readString12 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int makeLink = makeLink(readInt14, readString12, readInt15, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(makeLink);
                    return true;
                case 13:
                    int readInt16 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int unlink = unlink(readInt16, readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlink);
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isFileFullyLoaded = isFileFullyLoaded(readInt17, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(isFileFullyLoaded);
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isFullyLoaded = isFullyLoaded(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(isFullyLoaded);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float loadingProgress = getLoadingProgress(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeFloat(loadingProgress);
                    return true;
                case 17:
                    int readInt20 = parcel.readInt();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] metadataByPath = getMetadataByPath(readInt20, readString16);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(metadataByPath);
                    return true;
                case 18:
                    int readInt21 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] metadataById = getMetadataById(readInt21, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(metadataById);
                    return true;
                case 19:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStorage(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disallowReadLogs(readInt23);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt24 = parcel.readInt();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean configureNativeBinaries = configureNativeBinaries(readInt24, readString17, readString18, readString19, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(configureNativeBinaries);
                    return true;
                case 22:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean waitForNativeBinariesExtraction = waitForNativeBinariesExtraction(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(waitForNativeBinariesExtraction);
                    return true;
                case 23:
                    int readInt26 = parcel.readInt();
                    IStorageLoadingProgressListener asInterface3 = IStorageLoadingProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerLoadingProgressListener = registerLoadingProgressListener(readInt26, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerLoadingProgressListener);
                    return true;
                case 24:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean unregisterLoadingProgressListener = unregisterLoadingProgressListener(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterLoadingProgressListener);
                    return true;
                case 25:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PersistableBundle metrics = getMetrics(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(metrics, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIncrementalService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncrementalService.DESCRIPTOR;
            }

            @Override // android.os.incremental.IIncrementalService
            public int openStorage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int createStorage(String str, DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int createLinkedStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean startLoading(int i, DataLoaderParamsParcel dataLoaderParamsParcel, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, PerUidReadTimeouts[] perUidReadTimeoutsArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeStrongInterface(iDataLoaderStatusListener);
                    obtain.writeTypedObject(storageHealthCheckParams, 0);
                    obtain.writeStrongInterface(iStorageHealthListener);
                    obtain.writeTypedArray(perUidReadTimeoutsArr, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void onInstallationComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeBindMount(int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int deleteBindMount(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeDirectory(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeDirectories(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeFile(int i, String str, int i2, IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(incrementalNewFileParams, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeFileFromRange(int i, String str, String str2, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeLink(int i, String str, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int unlink(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int isFileFullyLoaded(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int isFullyLoaded(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public float getLoadingProgress(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public byte[] getMetadataByPath(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public byte[] getMetadataById(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void deleteStorage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void disallowReadLogs(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean configureNativeBinaries(int i, String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean waitForNativeBinariesExtraction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean registerLoadingProgressListener(int i, IStorageLoadingProgressListener iStorageLoadingProgressListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iStorageLoadingProgressListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean unregisterLoadingProgressListener(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public PersistableBundle getMetrics(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PersistableBundle) obtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
