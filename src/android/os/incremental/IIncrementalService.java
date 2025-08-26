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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIncrementalService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIncrementalService)) {
                return (IIncrementalService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iOpenStorage = openStorage(string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpenStorage);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    DataLoaderParamsParcel dataLoaderParamsParcel = (DataLoaderParamsParcel) parcel.readTypedObject(DataLoaderParamsParcel.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCreateStorage = createStorage(string2, dataLoaderParamsParcel, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateStorage);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCreateLinkedStorage = createLinkedStorage(string3, i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateLinkedStorage);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    DataLoaderParamsParcel dataLoaderParamsParcel2 = (DataLoaderParamsParcel) parcel.readTypedObject(DataLoaderParamsParcel.CREATOR);
                    IDataLoaderStatusListener iDataLoaderStatusListenerAsInterface = IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    StorageHealthCheckParams storageHealthCheckParams = (StorageHealthCheckParams) parcel.readTypedObject(StorageHealthCheckParams.CREATOR);
                    IStorageHealthListener iStorageHealthListenerAsInterface = IStorageHealthListener.Stub.asInterface(parcel.readStrongBinder());
                    PerUidReadTimeouts[] perUidReadTimeoutsArr = (PerUidReadTimeouts[]) parcel.createTypedArray(PerUidReadTimeouts.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStartLoading = startLoading(i6, dataLoaderParamsParcel2, iDataLoaderStatusListenerAsInterface, storageHealthCheckParams, iStorageHealthListenerAsInterface, perUidReadTimeoutsArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartLoading);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInstallationComplete(i7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iMakeBindMount = makeBindMount(i8, string4, string5, i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeBindMount);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iDeleteBindMount = deleteBindMount(i10, string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDeleteBindMount);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMakeDirectory = makeDirectory(i11, string7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeDirectory);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMakeDirectories = makeDirectories(i12, string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeDirectories);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    String string9 = parcel.readString();
                    int i14 = parcel.readInt();
                    IncrementalNewFileParams incrementalNewFileParams = (IncrementalNewFileParams) parcel.readTypedObject(IncrementalNewFileParams.CREATOR);
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iMakeFile = makeFile(i13, string9, i14, incrementalNewFileParams, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeFile);
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int iMakeFileFromRange = makeFileFromRange(i15, string10, string11, j, j2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeFileFromRange);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    String string12 = parcel.readString();
                    int i17 = parcel.readInt();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMakeLink = makeLink(i16, string12, i17, string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMakeLink);
                    return true;
                case 13:
                    int i18 = parcel.readInt();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iUnlink = unlink(i18, string14);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnlink);
                    return true;
                case 14:
                    int i19 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iIsFileFullyLoaded = isFileFullyLoaded(i19, string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsFileFullyLoaded);
                    return true;
                case 15:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iIsFullyLoaded = isFullyLoaded(i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsFullyLoaded);
                    return true;
                case 16:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float loadingProgress = getLoadingProgress(i21);
                    parcel2.writeNoException();
                    parcel2.writeFloat(loadingProgress);
                    return true;
                case 17:
                    int i22 = parcel.readInt();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] metadataByPath = getMetadataByPath(i22, string16);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(metadataByPath);
                    return true;
                case 18:
                    int i23 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] metadataById = getMetadataById(i23, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(metadataById);
                    return true;
                case 19:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStorage(i24);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disallowReadLogs(i25);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i26 = parcel.readInt();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zConfigureNativeBinaries = configureNativeBinaries(i26, string17, string18, string19, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConfigureNativeBinaries);
                    return true;
                case 22:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zWaitForNativeBinariesExtraction = waitForNativeBinariesExtraction(i27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWaitForNativeBinariesExtraction);
                    return true;
                case 23:
                    int i28 = parcel.readInt();
                    IStorageLoadingProgressListener iStorageLoadingProgressListenerAsInterface = IStorageLoadingProgressListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterLoadingProgressListener = registerLoadingProgressListener(i28, iStorageLoadingProgressListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterLoadingProgressListener);
                    return true;
                case 24:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterLoadingProgressListener = unregisterLoadingProgressListener(i29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterLoadingProgressListener);
                    return true;
                case 25:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PersistableBundle metrics = getMetrics(i30);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int createStorage(String str, DataLoaderParamsParcel dataLoaderParamsParcel, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int createLinkedStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean startLoading(int i, DataLoaderParamsParcel dataLoaderParamsParcel, IDataLoaderStatusListener iDataLoaderStatusListener, StorageHealthCheckParams storageHealthCheckParams, IStorageHealthListener iStorageHealthListener, PerUidReadTimeouts[] perUidReadTimeoutsArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    parcelObtain.writeStrongInterface(iDataLoaderStatusListener);
                    parcelObtain.writeTypedObject(storageHealthCheckParams, 0);
                    parcelObtain.writeStrongInterface(iStorageHealthListener);
                    parcelObtain.writeTypedArray(perUidReadTimeoutsArr, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void onInstallationComplete(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeBindMount(int i, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int deleteBindMount(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeDirectory(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeDirectories(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeFile(int i, String str, int i2, IncrementalNewFileParams incrementalNewFileParams, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(incrementalNewFileParams, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeFileFromRange(int i, String str, String str2, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int makeLink(int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int unlink(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int isFileFullyLoaded(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public int isFullyLoaded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public float getLoadingProgress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public byte[] getMetadataByPath(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public byte[] getMetadataById(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void deleteStorage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public void disallowReadLogs(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean configureNativeBinaries(int i, String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean waitForNativeBinariesExtraction(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean registerLoadingProgressListener(int i, IStorageLoadingProgressListener iStorageLoadingProgressListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iStorageLoadingProgressListener);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public boolean unregisterLoadingProgressListener(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.incremental.IIncrementalService
            public PersistableBundle getMetrics(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIncrementalService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PersistableBundle) parcelObtain2.readTypedObject(PersistableBundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
