package android.gsi;

import android.gsi.IGsiServiceCallback;
import android.gsi.IImageService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IGsiService extends IInterface {
    public static final String DESCRIPTOR = "android.gsi.IGsiService";
    public static final int INSTALL_ERROR_FILE_SYSTEM_CLUTTERED = 3;
    public static final int INSTALL_ERROR_GENERIC = 1;
    public static final int INSTALL_ERROR_NO_SPACE = 2;
    public static final int INSTALL_OK = 0;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_NO_OPERATION = 0;
    public static final int STATUS_WORKING = 1;

    public static class Default implements IGsiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.gsi.IGsiService
        public boolean cancelGsiInstall() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public int closeInstall() throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int closePartition() throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public boolean commitGsiChunkFromAshmem(long j) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean commitGsiChunkFromStream(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public int createPartition(String str, long j, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public boolean disableGsi() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public String dumpDeviceMapperDevices() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int enableGsi(boolean z, String str) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback) throws RemoteException {
        }

        @Override // android.gsi.IGsiService
        public String getActiveDsuSlot() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public GsiProgress getInstallProgress() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public List<String> getInstalledDsuSlots() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public String getInstalledGsiImageDir() throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiEnabled() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiInstallInProgress() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiInstalled() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiRunning() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public IImageService openImageService(String str) throws RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int openInstall(String str) throws RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public boolean removeGsi() throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback) throws RemoteException {
        }

        @Override // android.gsi.IGsiService
        public boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public long suggestScratchSize() throws RemoteException {
            return 0L;
        }

        @Override // android.gsi.IGsiService
        public int zeroPartition(String str) throws RemoteException {
            return 0;
        }
    }

    boolean cancelGsiInstall() throws RemoteException;

    int closeInstall() throws RemoteException;

    int closePartition() throws RemoteException;

    boolean commitGsiChunkFromAshmem(long j) throws RemoteException;

    boolean commitGsiChunkFromStream(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException;

    int createPartition(String str, long j, boolean z) throws RemoteException;

    boolean disableGsi() throws RemoteException;

    String dumpDeviceMapperDevices() throws RemoteException;

    int enableGsi(boolean z, String str) throws RemoteException;

    void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback) throws RemoteException;

    String getActiveDsuSlot() throws RemoteException;

    int getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException;

    GsiProgress getInstallProgress() throws RemoteException;

    List<String> getInstalledDsuSlots() throws RemoteException;

    String getInstalledGsiImageDir() throws RemoteException;

    boolean isGsiEnabled() throws RemoteException;

    boolean isGsiInstallInProgress() throws RemoteException;

    boolean isGsiInstalled() throws RemoteException;

    boolean isGsiRunning() throws RemoteException;

    IImageService openImageService(String str) throws RemoteException;

    int openInstall(String str) throws RemoteException;

    boolean removeGsi() throws RemoteException;

    void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback) throws RemoteException;

    boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException;

    long suggestScratchSize() throws RemoteException;

    int zeroPartition(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IGsiService {
        static final int TRANSACTION_cancelGsiInstall = 8;
        static final int TRANSACTION_closeInstall = 19;
        static final int TRANSACTION_closePartition = 21;
        static final int TRANSACTION_commitGsiChunkFromAshmem = 4;
        static final int TRANSACTION_commitGsiChunkFromStream = 1;
        static final int TRANSACTION_createPartition = 20;
        static final int TRANSACTION_disableGsi = 12;
        static final int TRANSACTION_dumpDeviceMapperDevices = 24;
        static final int TRANSACTION_enableGsi = 5;
        static final int TRANSACTION_enableGsiAsync = 6;
        static final int TRANSACTION_getActiveDsuSlot = 15;
        static final int TRANSACTION_getAvbPublicKey = 25;
        static final int TRANSACTION_getInstallProgress = 2;
        static final int TRANSACTION_getInstalledDsuSlots = 17;
        static final int TRANSACTION_getInstalledGsiImageDir = 16;
        static final int TRANSACTION_isGsiEnabled = 7;
        static final int TRANSACTION_isGsiInstallInProgress = 9;
        static final int TRANSACTION_isGsiInstalled = 13;
        static final int TRANSACTION_isGsiRunning = 14;
        static final int TRANSACTION_openImageService = 23;
        static final int TRANSACTION_openInstall = 18;
        static final int TRANSACTION_removeGsi = 10;
        static final int TRANSACTION_removeGsiAsync = 11;
        static final int TRANSACTION_setGsiAshmem = 3;
        static final int TRANSACTION_suggestScratchSize = 26;
        static final int TRANSACTION_zeroPartition = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 25;
        }

        public Stub() {
            attachInterface(this, IGsiService.DESCRIPTOR);
        }

        public static IGsiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGsiService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGsiService)) {
                return (IGsiService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "commitGsiChunkFromStream";
                case 2:
                    return "getInstallProgress";
                case 3:
                    return "setGsiAshmem";
                case 4:
                    return "commitGsiChunkFromAshmem";
                case 5:
                    return "enableGsi";
                case 6:
                    return "enableGsiAsync";
                case 7:
                    return "isGsiEnabled";
                case 8:
                    return "cancelGsiInstall";
                case 9:
                    return "isGsiInstallInProgress";
                case 10:
                    return "removeGsi";
                case 11:
                    return "removeGsiAsync";
                case 12:
                    return "disableGsi";
                case 13:
                    return "isGsiInstalled";
                case 14:
                    return "isGsiRunning";
                case 15:
                    return "getActiveDsuSlot";
                case 16:
                    return "getInstalledGsiImageDir";
                case 17:
                    return "getInstalledDsuSlots";
                case 18:
                    return "openInstall";
                case 19:
                    return "closeInstall";
                case 20:
                    return "createPartition";
                case 21:
                    return "closePartition";
                case 22:
                    return "zeroPartition";
                case 23:
                    return "openImageService";
                case 24:
                    return "dumpDeviceMapperDevices";
                case 25:
                    return "getAvbPublicKey";
                case 26:
                    return "suggestScratchSize";
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
                parcel.enforceInterface(IGsiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGsiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zCommitGsiChunkFromStream = commitGsiChunkFromStream(parcelFileDescriptor, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCommitGsiChunkFromStream);
                    return true;
                case 2:
                    GsiProgress installProgress = getInstallProgress();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installProgress, 1);
                    return true;
                case 3:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean gsiAshmem = setGsiAshmem(parcelFileDescriptor2, j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gsiAshmem);
                    return true;
                case 4:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zCommitGsiChunkFromAshmem = commitGsiChunkFromAshmem(j3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCommitGsiChunkFromAshmem);
                    return true;
                case 5:
                    boolean z = parcel.readBoolean();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iEnableGsi = enableGsi(z, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnableGsi);
                    return true;
                case 6:
                    boolean z2 = parcel.readBoolean();
                    String string2 = parcel.readString();
                    IGsiServiceCallback iGsiServiceCallbackAsInterface = IGsiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableGsiAsync(z2, string2, iGsiServiceCallbackAsInterface);
                    return true;
                case 7:
                    boolean zIsGsiEnabled = isGsiEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGsiEnabled);
                    return true;
                case 8:
                    boolean zCancelGsiInstall = cancelGsiInstall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCancelGsiInstall);
                    return true;
                case 9:
                    boolean zIsGsiInstallInProgress = isGsiInstallInProgress();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGsiInstallInProgress);
                    return true;
                case 10:
                    boolean zRemoveGsi = removeGsi();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveGsi);
                    return true;
                case 11:
                    IGsiServiceCallback iGsiServiceCallbackAsInterface2 = IGsiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGsiAsync(iGsiServiceCallbackAsInterface2);
                    return true;
                case 12:
                    boolean zDisableGsi = disableGsi();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableGsi);
                    return true;
                case 13:
                    boolean zIsGsiInstalled = isGsiInstalled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGsiInstalled);
                    return true;
                case 14:
                    boolean zIsGsiRunning = isGsiRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGsiRunning);
                    return true;
                case 15:
                    String activeDsuSlot = getActiveDsuSlot();
                    parcel2.writeNoException();
                    parcel2.writeString(activeDsuSlot);
                    return true;
                case 16:
                    String installedGsiImageDir = getInstalledGsiImageDir();
                    parcel2.writeNoException();
                    parcel2.writeString(installedGsiImageDir);
                    return true;
                case 17:
                    List<String> installedDsuSlots = getInstalledDsuSlots();
                    parcel2.writeNoException();
                    parcel2.writeStringList(installedDsuSlots);
                    return true;
                case 18:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iOpenInstall = openInstall(string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpenInstall);
                    return true;
                case 19:
                    int iCloseInstall = closeInstall();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCloseInstall);
                    return true;
                case 20:
                    String string4 = parcel.readString();
                    long j4 = parcel.readLong();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iCreatePartition = createPartition(string4, j4, z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreatePartition);
                    return true;
                case 21:
                    int iClosePartition = closePartition();
                    parcel2.writeNoException();
                    parcel2.writeInt(iClosePartition);
                    return true;
                case 22:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iZeroPartition = zeroPartition(string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iZeroPartition);
                    return true;
                case 23:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IImageService iImageServiceOpenImageService = openImageService(string6);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImageServiceOpenImageService);
                    return true;
                case 24:
                    String strDumpDeviceMapperDevices = dumpDeviceMapperDevices();
                    parcel2.writeNoException();
                    parcel2.writeString(strDumpDeviceMapperDevices);
                    return true;
                case 25:
                    AvbPublicKey avbPublicKey = new AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    int avbPublicKey2 = getAvbPublicKey(avbPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeInt(avbPublicKey2);
                    parcel2.writeTypedObject(avbPublicKey, 1);
                    return true;
                case 26:
                    long jSuggestScratchSize = suggestScratchSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(jSuggestScratchSize);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGsiService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGsiService.DESCRIPTOR;
            }

            @Override // android.gsi.IGsiService
            public boolean commitGsiChunkFromStream(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public GsiProgress getInstallProgress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GsiProgress) parcelObtain2.readTypedObject(GsiProgress.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean setGsiAshmem(ParcelFileDescriptor parcelFileDescriptor, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean commitGsiChunkFromAshmem(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int enableGsi(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public void enableGsiAsync(boolean z, String str, IGsiServiceCallback iGsiServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iGsiServiceCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean cancelGsiInstall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiInstallInProgress() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean removeGsi() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public void removeGsiAsync(IGsiServiceCallback iGsiServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGsiServiceCallback);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean disableGsi() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiInstalled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public String getActiveDsuSlot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public String getInstalledGsiImageDir() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public List<String> getInstalledDsuSlots() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int openInstall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int closeInstall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int createPartition(String str, long j, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int closePartition() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int zeroPartition(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public IImageService openImageService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImageService.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public String dumpDeviceMapperDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int getAvbPublicKey(AvbPublicKey avbPublicKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(parcelObtain2);
                    }
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public long suggestScratchSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGsiService.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
