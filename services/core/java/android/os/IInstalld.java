package android.os;

import android.os.storage.CrateMetadata;

import java.util.ArrayList;
import java.util.List;

public interface IInstalld extends IInterface {
    public static final int FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES = 131072;
    public static final int FLAG_CLEAR_CACHE_ONLY = 16;
    public static final int FLAG_CLEAR_CODE_CACHE_ONLY = 32;
    public static final int FLAG_FORCE = 8192;
    public static final int FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES = 2048;
    public static final int FLAG_FREE_CACHE_NOOP = 1024;
    public static final int FLAG_FREE_CACHE_V2 = 256;
    public static final int FLAG_FREE_CACHE_V2_DEFY_QUOTA = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FLAG_STORAGE_SDK = 8;
    public static final int FLAG_USE_QUOTA = 4096;

    public class Default implements IInstalld {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IInstalld
        public void cleanupInvalidPackageDirs(String str, int i, int i2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void clearAppData(String str, String str2, int i, int i2, long j)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void clearAppProfiles(String str, String str2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void compressFile(String str, boolean z) throws RemoteException {}

        @Override // android.os.IInstalld
        public void controlDexOptBlocking(boolean z) throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean copyKnoxCancel(String str, long j) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int copyKnoxChunks(
                String str, int i, String str2, int i2, int i3, long j, long j2, long j3)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean copySystemProfile(String str, int i, String str2, String str3)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean createEncAppData(String str, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public IFsveritySetupAuthToken createFsveritySetupAuthToken(
                ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void createOatDir(String str, String str2, String str3) throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean createProfileSnapshot(int i, String str, String str2, String str3)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void createUserData(String str, int i, int i2, int i3) throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean deleteKnoxFile(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public long deleteOdex(String str, String str2, String str3, String str4)
                throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public void deleteReferenceProfile(String str, String str2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void destroyAppData(String str, String str2, int i, int i2, long j)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void destroyAppProfiles(String str) throws RemoteException {}

        @Override // android.os.IInstalld
        public void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void destroyProfileSnapshot(String str, String str2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void destroyUserData(String str, int i, int i2) throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean dexopt(
                String str,
                int i,
                String str2,
                String str3,
                int i2,
                String str4,
                int i3,
                String str5,
                String str6,
                String str7,
                String str8,
                boolean z,
                int i4,
                String str9,
                String str10,
                String str11)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean dumpProfiles(int i, String str, String str2, String str3, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int enableFsverity(
                IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public void fixupAppData(String str, int i) throws RemoteException {}

        @Override // android.os.IInstalld
        public void freeCache(String str, long j, int i) throws RemoteException {}

        @Override // android.os.IInstalld
        public CrateMetadata[] getAppCrates(String str, String[] strArr, int i)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getAppSize(
                String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean getCompressedStats(String str, long[] jArr) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean getDualDARLockstate() throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public long[] getExternalSize(String str, int i, int i2, int[] iArr)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getKnoxFileInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean getKnoxScanDir(String str, long j, List list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int getOdexVisibility(String str, String str2, String str3, String str4)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public CrateMetadata[] getUserCrates(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getUserSize(String str, int i, int i2, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean hasDualDARPolicy(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean hasDualDARPolicyRecursively(String str, List list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void invalidateMounts() throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean isQuotaSupported(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void linkFile(String str, String str2, String str3, String str4)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void linkNativeLibraryDirectory(String str, String str2, String str3, int i)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public int mergeProfiles(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public void migrateAppData(String str, String str2, int i, int i2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void migrateLegacyObbData() throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean migrateSdpDb(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void moveAb(String str, String str2, String str3, String str4)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void moveCompleteApp(
                String str, String str2, String str3, int i, String str4, int i2, String str5)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void onPrivateVolumeRemoved(String str) throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean prepareAppProfile(
                String str, int i, int i2, String str2, String str3, String str4)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean reconcileSecondaryDexFile(
                String str, String str2, int i, String[] strArr, String str3, int i2)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean removeEncPkgDir(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean removeEncUserDir(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean removeNotTargetedPreloadApksIfNeeded() throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void restoreAppDataSnapshot(
                String str, String str2, int i, String str3, int i2, int i3, int i4)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3)
                throws RemoteException {}

        @Override // android.os.IInstalld
        public void rmPackageDir(String str, String str2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void rmdex(String str, String str2) throws RemoteException {}

        @Override // android.os.IInstalld
        public void scanApkStats(String str, int i) throws RemoteException {}

        @Override // android.os.IInstalld
        public void setAppQuota(String str, int i, int i2, long j) throws RemoteException {}

        @Override // android.os.IInstalld
        public boolean setDualDARPolicyDir(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setDualDARPolicyDirRecursively(int i, int i2, String str)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setEviction(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void setFirstBoot() throws RemoteException {}

        @Override // android.os.IInstalld
        public long snapshotAppData(String str, String str2, int i, int i2, int i3)
                throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public void tryMountDataMirror(String str) throws RemoteException {}
    }

    public interface IFsveritySetupAuthToken extends IInterface {
        public static final String DESCRIPTOR = "android.os.IInstalld.IFsveritySetupAuthToken";

        public class Default implements IFsveritySetupAuthToken {
            @Override // android.os.IInterface
            public IBinder asBinder() {
                return null;
            }
        }

        public abstract class Stub extends Binder implements IFsveritySetupAuthToken {

            public final class Proxy implements IFsveritySetupAuthToken {
                public IBinder mRemote;

                @Override // android.os.IInterface
                public final IBinder asBinder() {
                    return this.mRemote;
                }
            }

            public Stub() {
                attachInterface(this, IFsveritySetupAuthToken.DESCRIPTOR);
            }

            public static IFsveritySetupAuthToken asInterface(IBinder iBinder) {
                if (iBinder == null) {
                    return null;
                }
                IInterface queryLocalInterface =
                        iBinder.queryLocalInterface(IFsveritySetupAuthToken.DESCRIPTOR);
                if (queryLocalInterface != null
                        && (queryLocalInterface instanceof IFsveritySetupAuthToken)) {
                    return (IFsveritySetupAuthToken) queryLocalInterface;
                }
                Proxy proxy = new Proxy();
                proxy.mRemote = iBinder;
                return proxy;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                    throws RemoteException {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString(IFsveritySetupAuthToken.DESCRIPTOR);
                return true;
            }
        }
    }

    public abstract class Stub extends Binder implements IInstalld {
        public static final String DESCRIPTOR = "android.os.IInstalld";
        static final int TRANSACTION_cleanupInvalidPackageDirs = 49;
        static final int TRANSACTION_clearAppData = 9;
        static final int TRANSACTION_clearAppProfiles = 25;
        static final int TRANSACTION_compressFile = 70;
        static final int TRANSACTION_controlDexOptBlocking = 20;
        static final int TRANSACTION_copyKnoxAppData = 54;
        static final int TRANSACTION_copyKnoxCancel = 56;
        static final int TRANSACTION_copyKnoxChunks = 55;
        static final int TRANSACTION_copySystemProfile = 24;
        static final int TRANSACTION_createAppData = 4;
        static final int TRANSACTION_createAppDataBatched = 5;
        static final int TRANSACTION_createEncAppData = 60;
        static final int TRANSACTION_createFsveritySetupAuthToken = 52;
        static final int TRANSACTION_createOatDir = 33;
        static final int TRANSACTION_createProfileSnapshot = 28;
        static final int TRANSACTION_createUserData = 1;
        static final int TRANSACTION_deleteKnoxFile = 59;
        static final int TRANSACTION_deleteOdex = 36;
        static final int TRANSACTION_deleteReferenceProfile = 27;
        static final int TRANSACTION_destroyAppData = 10;
        static final int TRANSACTION_destroyAppDataSnapshot = 44;
        static final int TRANSACTION_destroyAppProfiles = 26;
        static final int TRANSACTION_destroyCeSnapshotsNotSpecified = 45;
        static final int TRANSACTION_destroyProfileSnapshot = 29;
        static final int TRANSACTION_destroyUserData = 2;
        static final int TRANSACTION_dexopt = 19;
        static final int TRANSACTION_dumpProfiles = 23;
        static final int TRANSACTION_enableFsverity = 53;
        static final int TRANSACTION_fixupAppData = 11;
        static final int TRANSACTION_freeCache = 31;
        static final int TRANSACTION_getAppCrates = 15;
        static final int TRANSACTION_getAppSize = 12;
        static final int TRANSACTION_getCompressedStats = 71;
        static final int TRANSACTION_getDualDARLockstate = 69;
        static final int TRANSACTION_getExternalSize = 14;
        static final int TRANSACTION_getKnoxFileInfo = 57;
        static final int TRANSACTION_getKnoxScanDir = 58;
        static final int TRANSACTION_getOdexVisibility = 50;
        static final int TRANSACTION_getUserCrates = 16;
        static final int TRANSACTION_getUserSize = 13;
        static final int TRANSACTION_hasDualDARPolicy = 67;
        static final int TRANSACTION_hasDualDARPolicyRecursively = 68;
        static final int TRANSACTION_hashSecondaryDexFile = 38;
        static final int TRANSACTION_invalidateMounts = 39;
        static final int TRANSACTION_isQuotaSupported = 40;
        static final int TRANSACTION_linkFile = 34;
        static final int TRANSACTION_linkNativeLibraryDirectory = 32;
        static final int TRANSACTION_mergeProfiles = 22;
        static final int TRANSACTION_migrateAppData = 8;
        static final int TRANSACTION_migrateLegacyObbData = 48;
        static final int TRANSACTION_migrateSdpDb = 63;
        static final int TRANSACTION_moveAb = 35;
        static final int TRANSACTION_moveCompleteApp = 18;
        static final int TRANSACTION_onPrivateVolumeRemoved = 47;
        static final int TRANSACTION_prepareAppProfile = 41;
        static final int TRANSACTION_reconcileSdkData = 6;
        static final int TRANSACTION_reconcileSecondaryDexFile = 37;
        static final int TRANSACTION_removeEncPkgDir = 61;
        static final int TRANSACTION_removeEncUserDir = 62;
        static final int TRANSACTION_removeNotTargetedPreloadApksIfNeeded = 51;
        static final int TRANSACTION_restoreAppDataSnapshot = 43;
        static final int TRANSACTION_restoreconAppData = 7;
        static final int TRANSACTION_rmPackageDir = 30;
        static final int TRANSACTION_rmdex = 21;
        static final int TRANSACTION_scanApkStats = 72;
        static final int TRANSACTION_setAppQuota = 17;
        static final int TRANSACTION_setDualDARPolicyDir = 65;
        static final int TRANSACTION_setDualDARPolicyDirRecursively = 66;
        static final int TRANSACTION_setEviction = 64;
        static final int TRANSACTION_setFirstBoot = 3;
        static final int TRANSACTION_snapshotAppData = 42;
        static final int TRANSACTION_tryMountDataMirror = 46;

        public final class Proxy implements IInstalld {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.os.IInstalld
            public final void cleanupInvalidPackageDirs(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void clearAppData(String str, String str2, int i, int i2, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void clearAppProfiles(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void compressFile(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void controlDexOptBlocking(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean copyKnoxCancel(String str, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final int copyKnoxChunks(
                    String str, int i, String str2, int i2, int i3, long j, long j2, long j3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean copySystemProfile(String str, int i, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(createAppDataArgs, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CreateAppDataResult)
                            obtain2.readTypedObject(CreateAppDataResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final CreateAppDataResult[] createAppDataBatched(
                    CreateAppDataArgs[] createAppDataArgsArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(createAppDataArgsArr, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CreateAppDataResult[])
                            obtain2.createTypedArray(CreateAppDataResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean createEncAppData(String str, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final IFsveritySetupAuthToken createFsveritySetupAuthToken(
                    ParcelFileDescriptor parcelFileDescriptor, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return IFsveritySetupAuthToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void createOatDir(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean createProfileSnapshot(
                    int i, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void createUserData(String str, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean deleteKnoxFile(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final long deleteOdex(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void deleteReferenceProfile(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void destroyAppData(String str, String str2, int i, int i2, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void destroyAppDataSnapshot(
                    String str, String str2, int i, long j, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void destroyAppProfiles(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void destroyProfileSnapshot(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void destroyUserData(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean dexopt(
                    String str,
                    int i,
                    String str2,
                    String str3,
                    int i2,
                    String str4,
                    int i3,
                    String str5,
                    String str6,
                    String str7,
                    String str8,
                    boolean z,
                    int i4,
                    String str9,
                    String str10,
                    String str11) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    obtain.writeInt(i3);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeString(str8);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    obtain.writeString(str9);
                    obtain.writeString(str10);
                    obtain.writeString(str11);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean dumpProfiles(
                    int i, String str, String str2, String str3, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final int enableFsverity(
                    IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFsveritySetupAuthToken);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void fixupAppData(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void freeCache(String str, long j, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final CrateMetadata[] getAppCrates(String str, String[] strArr, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CrateMetadata[]) obtain2.createTypedArray(CrateMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final long[] getAppSize(
                    String str,
                    String[] strArr,
                    int i,
                    int i2,
                    int i3,
                    long[] jArr,
                    String[] strArr2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLongArray(jArr);
                    obtain.writeStringArray(strArr2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean getCompressedStats(String str, long[] jArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean getDualDARLockstate() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final long[] getExternalSize(String str, int i, int i2, int[] iArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final long[] getKnoxFileInfo(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean getKnoxScanDir(String str, long j, List list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readStringList(list);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final int getOdexVisibility(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final CrateMetadata[] getUserCrates(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CrateMetadata[]) obtain2.createTypedArray(CrateMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final long[] getUserSize(String str, int i, int i2, int[] iArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean hasDualDARPolicy(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean hasDualDARPolicyRecursively(String str, List list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readStringList(list);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final byte[] hashSecondaryDexFile(
                    String str, String str2, int i, String str3, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void invalidateMounts() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean isQuotaSupported(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void linkFile(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void linkNativeLibraryDirectory(
                    String str, String str2, String str3, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final int mergeProfiles(int i, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void migrateAppData(String str, String str2, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void migrateLegacyObbData() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean migrateSdpDb(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void moveAb(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void moveCompleteApp(
                    String str, String str2, String str3, int i, String str4, int i2, String str5) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    obtain.writeString(str5);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void onPrivateVolumeRemoved(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean prepareAppProfile(
                    String str, int i, int i2, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(reconcileSdkDataArgs, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean reconcileSecondaryDexFile(
                    String str, String str2, int i, String[] strArr, String str3, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean removeEncPkgDir(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean removeEncUserDir(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean removeNotTargetedPreloadApksIfNeeded() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void restoreAppDataSnapshot(
                    String str, String str2, int i, String str3, int i2, int i3, int i4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void restoreconAppData(
                    String str, String str2, int i, int i2, int i3, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void rmPackageDir(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void rmdex(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void scanApkStats(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void setAppQuota(String str, int i, int i2, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean setDualDARPolicyDir(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean setDualDARPolicyDirRecursively(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final boolean setEviction(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void setFirstBoot() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final long snapshotAppData(String str, String str2, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public final void tryMountDataMirror(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstalld asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInstalld)) {
                return (IInstalld) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createUserData(readString, readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserData(readString2, readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    setFirstBoot();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    CreateAppDataArgs createAppDataArgs =
                            (CreateAppDataArgs) parcel.readTypedObject(CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    CreateAppDataResult createAppData = createAppData(createAppDataArgs);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAppData, 1);
                    return true;
                case 5:
                    CreateAppDataArgs[] createAppDataArgsArr =
                            (CreateAppDataArgs[])
                                    parcel.createTypedArray(CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    CreateAppDataResult[] createAppDataBatched =
                            createAppDataBatched(createAppDataArgsArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(createAppDataBatched, 1);
                    return true;
                case 6:
                    ReconcileSdkDataArgs reconcileSdkDataArgs =
                            (ReconcileSdkDataArgs)
                                    parcel.readTypedObject(ReconcileSdkDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    reconcileSdkData(reconcileSdkDataArgs);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreconAppData(
                            readString3, readString4, readInt6, readInt7, readInt8, readString5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    migrateAppData(readString6, readString7, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    clearAppData(readString8, readString9, readInt11, readInt12, readLong);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    destroyAppData(readString10, readString11, readInt13, readInt14, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString12 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    fixupAppData(readString12, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString13 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    long[] createLongArray = parcel.createLongArray();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    long[] appSize =
                            getAppSize(
                                    readString13,
                                    createStringArray,
                                    readInt16,
                                    readInt17,
                                    readInt18,
                                    createLongArray,
                                    createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(appSize);
                    return true;
                case 13:
                    String readString14 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] userSize =
                            getUserSize(readString14, readInt19, readInt20, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(userSize);
                    return true;
                case 14:
                    String readString15 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] externalSize =
                            getExternalSize(readString15, readInt21, readInt22, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(externalSize);
                    return true;
                case 15:
                    String readString16 = parcel.readString();
                    String[] createStringArray3 = parcel.createStringArray();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CrateMetadata[] appCrates =
                            getAppCrates(readString16, createStringArray3, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(appCrates, 1);
                    return true;
                case 16:
                    String readString17 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CrateMetadata[] userCrates = getUserCrates(readString17, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(userCrates, 1);
                    return true;
                case 17:
                    String readString18 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAppQuota(readString18, readInt25, readInt26, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    String readString22 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveCompleteApp(
                            readString19,
                            readString20,
                            readString21,
                            readInt27,
                            readString22,
                            readInt28,
                            readString23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString24 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    String readString27 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt32 = parcel.readInt();
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dexopt =
                            dexopt(
                                    readString24,
                                    readInt29,
                                    readString25,
                                    readString26,
                                    readInt30,
                                    readString27,
                                    readInt31,
                                    readString28,
                                    readString29,
                                    readString30,
                                    readString31,
                                    readBoolean,
                                    readInt32,
                                    readString32,
                                    readString33,
                                    readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dexopt);
                    return true;
                case 20:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    controlDexOptBlocking(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmdex(readString35, readString36);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt33 = parcel.readInt();
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mergeProfiles = mergeProfiles(readInt33, readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeInt(mergeProfiles);
                    return true;
                case 23:
                    int readInt34 = parcel.readInt();
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dumpProfiles =
                            dumpProfiles(
                                    readInt34,
                                    readString39,
                                    readString40,
                                    readString41,
                                    readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dumpProfiles);
                    return true;
                case 24:
                    String readString42 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean copySystemProfile =
                            copySystemProfile(readString42, readInt35, readString43, readString44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copySystemProfile);
                    return true;
                case 25:
                    String readString45 = parcel.readString();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppProfiles(readString45, readString46);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyAppProfiles(readString47);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteReferenceProfile(readString48, readString49);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt36 = parcel.readInt();
                    String readString50 = parcel.readString();
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean createProfileSnapshot =
                            createProfileSnapshot(
                                    readInt36, readString50, readString51, readString52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createProfileSnapshot);
                    return true;
                case 29:
                    String readString53 = parcel.readString();
                    String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyProfileSnapshot(readString53, readString54);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmPackageDir(readString55, readString56);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString57 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    freeCache(readString57, readLong4, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    String readString60 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    linkNativeLibraryDirectory(readString58, readString59, readString60, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString61 = parcel.readString();
                    String readString62 = parcel.readString();
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createOatDir(readString61, readString62, readString63);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString64 = parcel.readString();
                    String readString65 = parcel.readString();
                    String readString66 = parcel.readString();
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    linkFile(readString64, readString65, readString66, readString67);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String readString68 = parcel.readString();
                    String readString69 = parcel.readString();
                    String readString70 = parcel.readString();
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveAb(readString68, readString69, readString70, readString71);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String readString72 = parcel.readString();
                    String readString73 = parcel.readString();
                    String readString74 = parcel.readString();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long deleteOdex =
                            deleteOdex(readString72, readString73, readString74, readString75);
                    parcel2.writeNoException();
                    parcel2.writeLong(deleteOdex);
                    return true;
                case 37:
                    String readString76 = parcel.readString();
                    String readString77 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    String[] createStringArray4 = parcel.createStringArray();
                    String readString78 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reconcileSecondaryDexFile =
                            reconcileSecondaryDexFile(
                                    readString76,
                                    readString77,
                                    readInt39,
                                    createStringArray4,
                                    readString78,
                                    readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reconcileSecondaryDexFile);
                    return true;
                case 38:
                    String readString79 = parcel.readString();
                    String readString80 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    String readString81 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] hashSecondaryDexFile =
                            hashSecondaryDexFile(
                                    readString79, readString80, readInt41, readString81, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(hashSecondaryDexFile);
                    return true;
                case 39:
                    invalidateMounts();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String readString82 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isQuotaSupported = isQuotaSupported(readString82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuotaSupported);
                    return true;
                case 41:
                    String readString83 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    String readString84 = parcel.readString();
                    String readString85 = parcel.readString();
                    String readString86 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean prepareAppProfile =
                            prepareAppProfile(
                                    readString83,
                                    readInt43,
                                    readInt44,
                                    readString84,
                                    readString85,
                                    readString86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(prepareAppProfile);
                    return true;
                case 42:
                    String readString87 = parcel.readString();
                    String readString88 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long snapshotAppData =
                            snapshotAppData(
                                    readString87, readString88, readInt45, readInt46, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeLong(snapshotAppData);
                    return true;
                case 43:
                    String readString89 = parcel.readString();
                    String readString90 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    String readString91 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAppDataSnapshot(
                            readString89,
                            readString90,
                            readInt48,
                            readString91,
                            readInt49,
                            readInt50,
                            readInt51);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    String readString92 = parcel.readString();
                    String readString93 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyAppDataSnapshot(
                            readString92, readString93, readInt52, readLong5, readInt53, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String readString94 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    destroyCeSnapshotsNotSpecified(readString94, readInt55, createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String readString95 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    tryMountDataMirror(readString95);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String readString96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPrivateVolumeRemoved(readString96);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    migrateLegacyObbData();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String readString97 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupInvalidPackageDirs(readString97, readInt56, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString98 = parcel.readString();
                    String readString99 = parcel.readString();
                    String readString100 = parcel.readString();
                    String readString101 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int odexVisibility =
                            getOdexVisibility(
                                    readString98, readString99, readString100, readString101);
                    parcel2.writeNoException();
                    parcel2.writeInt(odexVisibility);
                    return true;
                case 51:
                    boolean removeNotTargetedPreloadApksIfNeeded =
                            removeNotTargetedPreloadApksIfNeeded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeNotTargetedPreloadApksIfNeeded);
                    return true;
                case 52:
                    ParcelFileDescriptor parcelFileDescriptor =
                            (ParcelFileDescriptor)
                                    parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IFsveritySetupAuthToken createFsveritySetupAuthToken =
                            createFsveritySetupAuthToken(parcelFileDescriptor, readInt58);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createFsveritySetupAuthToken);
                    return true;
                case 53:
                    IFsveritySetupAuthToken asInterface =
                            IFsveritySetupAuthToken.Stub.asInterface(parcel.readStrongBinder());
                    String readString102 = parcel.readString();
                    String readString103 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int enableFsverity = enableFsverity(asInterface, readString102, readString103);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableFsverity);
                    return true;
                case 54:
                    String readString104 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    String readString105 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean copyKnoxAppData =
                            copyKnoxAppData(
                                    readString104, readInt59, readString105, readInt60, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copyKnoxAppData);
                    return true;
                case 55:
                    String readString106 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    String readString107 = parcel.readString();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int copyKnoxChunks =
                            copyKnoxChunks(
                                    readString106,
                                    readInt62,
                                    readString107,
                                    readInt63,
                                    readInt64,
                                    readLong6,
                                    readLong7,
                                    readLong8);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyKnoxChunks);
                    return true;
                case 56:
                    String readString108 = parcel.readString();
                    long readLong9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean copyKnoxCancel = copyKnoxCancel(readString108, readLong9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copyKnoxCancel);
                    return true;
                case 57:
                    String readString109 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long[] knoxFileInfo = getKnoxFileInfo(readString109);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(knoxFileInfo);
                    return true;
                case 58:
                    String readString110 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    ArrayList arrayList = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean knoxScanDir = getKnoxScanDir(readString110, readLong10, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxScanDir);
                    parcel2.writeStringList(arrayList);
                    return true;
                case 59:
                    String readString111 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteKnoxFile = deleteKnoxFile(readString111);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteKnoxFile);
                    return true;
                case 60:
                    String readString112 = parcel.readString();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean createEncAppData =
                            createEncAppData(readString112, readInt65, readInt66, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createEncAppData);
                    return true;
                case 61:
                    int readInt68 = parcel.readInt();
                    String readString113 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeEncPkgDir = removeEncPkgDir(readInt68, readString113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeEncPkgDir);
                    return true;
                case 62:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeEncUserDir = removeEncUserDir(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeEncUserDir);
                    return true;
                case 63:
                    String readString114 = parcel.readString();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean migrateSdpDb = migrateSdpDb(readString114, readInt70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(migrateSdpDb);
                    return true;
                case 64:
                    int readInt71 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean eviction = setEviction(readInt71, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(eviction);
                    return true;
                case 65:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    String readString115 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyDir =
                            setDualDARPolicyDir(readInt72, readInt73, readString115);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyDir);
                    return true;
                case 66:
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    String readString116 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyDirRecursively =
                            setDualDARPolicyDirRecursively(readInt74, readInt75, readString116);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyDirRecursively);
                    return true;
                case 67:
                    String readString117 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDualDARPolicy = hasDualDARPolicy(readString117);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDualDARPolicy);
                    return true;
                case 68:
                    String readString118 = parcel.readString();
                    ArrayList arrayList2 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean hasDualDARPolicyRecursively =
                            hasDualDARPolicyRecursively(readString118, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDualDARPolicyRecursively);
                    parcel2.writeStringList(arrayList2);
                    return true;
                case 69:
                    boolean dualDARLockstate = getDualDARLockstate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARLockstate);
                    return true;
                case 70:
                    String readString119 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    compressFile(readString119, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String readString120 = parcel.readString();
                    int readInt76 = parcel.readInt();
                    long[] jArr = readInt76 < 0 ? null : new long[readInt76];
                    parcel.enforceNoDataAvail();
                    boolean compressedStats = getCompressedStats(readString120, jArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(compressedStats);
                    parcel2.writeLongArray(jArr);
                    return true;
                case 72:
                    String readString121 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scanApkStats(readString121, readInt77);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void cleanupInvalidPackageDirs(String str, int i, int i2) throws RemoteException;

    void clearAppData(String str, String str2, int i, int i2, long j) throws RemoteException;

    void clearAppProfiles(String str, String str2) throws RemoteException;

    void compressFile(String str, boolean z) throws RemoteException;

    void controlDexOptBlocking(boolean z) throws RemoteException;

    boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) throws RemoteException;

    boolean copyKnoxCancel(String str, long j) throws RemoteException;

    int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3)
            throws RemoteException;

    boolean copySystemProfile(String str, int i, String str2, String str3) throws RemoteException;

    CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) throws RemoteException;

    CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr)
            throws RemoteException;

    boolean createEncAppData(String str, int i, int i2, int i3) throws RemoteException;

    IFsveritySetupAuthToken createFsveritySetupAuthToken(
            ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    void createOatDir(String str, String str2, String str3) throws RemoteException;

    boolean createProfileSnapshot(int i, String str, String str2, String str3)
            throws RemoteException;

    void createUserData(String str, int i, int i2, int i3) throws RemoteException;

    boolean deleteKnoxFile(String str) throws RemoteException;

    long deleteOdex(String str, String str2, String str3, String str4) throws RemoteException;

    void deleteReferenceProfile(String str, String str2) throws RemoteException;

    void destroyAppData(String str, String str2, int i, int i2, long j) throws RemoteException;

    void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3)
            throws RemoteException;

    void destroyAppProfiles(String str) throws RemoteException;

    void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) throws RemoteException;

    void destroyProfileSnapshot(String str, String str2) throws RemoteException;

    void destroyUserData(String str, int i, int i2) throws RemoteException;

    boolean dexopt(
            String str,
            int i,
            String str2,
            String str3,
            int i2,
            String str4,
            int i3,
            String str5,
            String str6,
            String str7,
            String str8,
            boolean z,
            int i4,
            String str9,
            String str10,
            String str11)
            throws RemoteException;

    boolean dumpProfiles(int i, String str, String str2, String str3, boolean z)
            throws RemoteException;

    int enableFsverity(IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2)
            throws RemoteException;

    void fixupAppData(String str, int i) throws RemoteException;

    void freeCache(String str, long j, int i) throws RemoteException;

    CrateMetadata[] getAppCrates(String str, String[] strArr, int i) throws RemoteException;

    long[] getAppSize(
            String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2)
            throws RemoteException;

    boolean getCompressedStats(String str, long[] jArr) throws RemoteException;

    boolean getDualDARLockstate() throws RemoteException;

    long[] getExternalSize(String str, int i, int i2, int[] iArr) throws RemoteException;

    long[] getKnoxFileInfo(String str) throws RemoteException;

    boolean getKnoxScanDir(String str, long j, List list) throws RemoteException;

    int getOdexVisibility(String str, String str2, String str3, String str4) throws RemoteException;

    CrateMetadata[] getUserCrates(String str, int i) throws RemoteException;

    long[] getUserSize(String str, int i, int i2, int[] iArr) throws RemoteException;

    boolean hasDualDARPolicy(String str) throws RemoteException;

    boolean hasDualDARPolicyRecursively(String str, List list) throws RemoteException;

    byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2)
            throws RemoteException;

    void invalidateMounts() throws RemoteException;

    boolean isQuotaSupported(String str) throws RemoteException;

    void linkFile(String str, String str2, String str3, String str4) throws RemoteException;

    void linkNativeLibraryDirectory(String str, String str2, String str3, int i)
            throws RemoteException;

    int mergeProfiles(int i, String str, String str2) throws RemoteException;

    void migrateAppData(String str, String str2, int i, int i2) throws RemoteException;

    void migrateLegacyObbData() throws RemoteException;

    boolean migrateSdpDb(String str, int i) throws RemoteException;

    void moveAb(String str, String str2, String str3, String str4) throws RemoteException;

    void moveCompleteApp(
            String str, String str2, String str3, int i, String str4, int i2, String str5)
            throws RemoteException;

    void onPrivateVolumeRemoved(String str) throws RemoteException;

    boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4)
            throws RemoteException;

    void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) throws RemoteException;

    boolean reconcileSecondaryDexFile(
            String str, String str2, int i, String[] strArr, String str3, int i2)
            throws RemoteException;

    boolean removeEncPkgDir(int i, String str) throws RemoteException;

    boolean removeEncUserDir(int i) throws RemoteException;

    boolean removeNotTargetedPreloadApksIfNeeded() throws RemoteException;

    void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4)
            throws RemoteException;

    void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3)
            throws RemoteException;

    void rmPackageDir(String str, String str2) throws RemoteException;

    void rmdex(String str, String str2) throws RemoteException;

    void scanApkStats(String str, int i) throws RemoteException;

    void setAppQuota(String str, int i, int i2, long j) throws RemoteException;

    boolean setDualDARPolicyDir(int i, int i2, String str) throws RemoteException;

    boolean setDualDARPolicyDirRecursively(int i, int i2, String str) throws RemoteException;

    boolean setEviction(int i, boolean z) throws RemoteException;

    void setFirstBoot() throws RemoteException;

    long snapshotAppData(String str, String str2, int i, int i2, int i3) throws RemoteException;

    void tryMountDataMirror(String str) throws RemoteException;
}
