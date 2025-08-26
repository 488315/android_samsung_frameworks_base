package android.os;

import android.os.storage.CrateMetadata;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
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

    public static class Default implements IInstalld {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IInstalld
        public void cleanupInvalidPackageDirs(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void clearAppData(String str, String str2, int i, int i2, long j) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void clearAppProfiles(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void compressFile(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void controlDexOptBlocking(boolean z) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean copyKnoxCancel(String str, long j) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean copySystemProfile(String str, int i, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean createEncAppData(String str, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void createOatDir(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean createProfileSnapshot(int i, String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void createUserData(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean deleteKnoxFile(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public long deleteOdex(String str, String str2, String str3, String str4) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public void deleteReferenceProfile(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppData(String str, String str2, int i, int i2, long j) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppProfiles(String str) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyProfileSnapshot(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyUserData(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean dexopt(String str, int i, String str2, String str3, int i2, String str4, int i3, String str5, String str6, String str7, String str8, boolean z, int i4, String str9, String str10, String str11) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean dumpProfiles(int i, String str, String str2, String str3, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int enableFsverity(IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public void fixupAppData(String str, int i) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void freeCache(String str, long j, int i) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public CrateMetadata[] getAppCrates(String str, String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getAppSize(String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2) throws RemoteException {
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
        public long[] getExternalSize(String str, int i, int i2, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getKnoxFileInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean getKnoxScanDir(String str, long j, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int getOdexVisibility(String str, String str2, String str3, String str4) throws RemoteException {
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
        public boolean hasDualDARPolicyRecursively(String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean installSpegCacheToDalvikCache(String str, int i, int i2, String str2) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void invalidateMounts() throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean isQuotaSupported(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void linkFile(String str, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void linkNativeLibraryDirectory(String str, String str2, String str3, int i) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public int mergeProfiles(int i, String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public void migrateAppData(String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void migrateLegacyObbData() throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean migrateSdpDb(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void moveAb(String str, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void moveCompleteApp(String str, String str2, String str3, int i, String str4, int i2, String str5) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void onPrivateVolumeRemoved(String str) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean reconcileSecondaryDexFile(String str, String str2, int i, String[] strArr, String str3, int i2) throws RemoteException {
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
        public void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void rmPackageDir(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void rmdex(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void scanApkStats(String str, int i) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void setAppQuota(String str, int i, int i2, long j) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean setDualDARPolicyDir(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setDualDARPolicyDirRecursively(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setEviction(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void setFirstBoot() throws RemoteException {
        }

        @Override // android.os.IInstalld
        public long snapshotAppData(String str, String str2, int i, int i2, int i3) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public void tryMountDataMirror(String str) throws RemoteException {
        }
    }

    void cleanupInvalidPackageDirs(String str, int i, int i2) throws RemoteException;

    void clearAppData(String str, String str2, int i, int i2, long j) throws RemoteException;

    void clearAppProfiles(String str, String str2) throws RemoteException;

    void compressFile(String str, boolean z) throws RemoteException;

    void controlDexOptBlocking(boolean z) throws RemoteException;

    boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) throws RemoteException;

    boolean copyKnoxCancel(String str, long j) throws RemoteException;

    int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3) throws RemoteException;

    boolean copySystemProfile(String str, int i, String str2, String str3) throws RemoteException;

    CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) throws RemoteException;

    CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr) throws RemoteException;

    boolean createEncAppData(String str, int i, int i2, int i3) throws RemoteException;

    IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    void createOatDir(String str, String str2, String str3) throws RemoteException;

    boolean createProfileSnapshot(int i, String str, String str2, String str3) throws RemoteException;

    void createUserData(String str, int i, int i2, int i3) throws RemoteException;

    boolean deleteKnoxFile(String str) throws RemoteException;

    long deleteOdex(String str, String str2, String str3, String str4) throws RemoteException;

    void deleteReferenceProfile(String str, String str2) throws RemoteException;

    void destroyAppData(String str, String str2, int i, int i2, long j) throws RemoteException;

    void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3) throws RemoteException;

    void destroyAppProfiles(String str) throws RemoteException;

    void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) throws RemoteException;

    void destroyProfileSnapshot(String str, String str2) throws RemoteException;

    void destroyUserData(String str, int i, int i2) throws RemoteException;

    boolean dexopt(String str, int i, String str2, String str3, int i2, String str4, int i3, String str5, String str6, String str7, String str8, boolean z, int i4, String str9, String str10, String str11) throws RemoteException;

    boolean dumpProfiles(int i, String str, String str2, String str3, boolean z) throws RemoteException;

    int enableFsverity(IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException;

    void fixupAppData(String str, int i) throws RemoteException;

    void freeCache(String str, long j, int i) throws RemoteException;

    CrateMetadata[] getAppCrates(String str, String[] strArr, int i) throws RemoteException;

    long[] getAppSize(String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2) throws RemoteException;

    boolean getCompressedStats(String str, long[] jArr) throws RemoteException;

    boolean getDualDARLockstate() throws RemoteException;

    long[] getExternalSize(String str, int i, int i2, int[] iArr) throws RemoteException;

    long[] getKnoxFileInfo(String str) throws RemoteException;

    boolean getKnoxScanDir(String str, long j, List<String> list) throws RemoteException;

    int getOdexVisibility(String str, String str2, String str3, String str4) throws RemoteException;

    CrateMetadata[] getUserCrates(String str, int i) throws RemoteException;

    long[] getUserSize(String str, int i, int i2, int[] iArr) throws RemoteException;

    boolean hasDualDARPolicy(String str) throws RemoteException;

    boolean hasDualDARPolicyRecursively(String str, List<String> list) throws RemoteException;

    byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2) throws RemoteException;

    boolean installSpegCacheToDalvikCache(String str, int i, int i2, String str2) throws RemoteException;

    void invalidateMounts() throws RemoteException;

    boolean isQuotaSupported(String str) throws RemoteException;

    void linkFile(String str, String str2, String str3, String str4) throws RemoteException;

    void linkNativeLibraryDirectory(String str, String str2, String str3, int i) throws RemoteException;

    int mergeProfiles(int i, String str, String str2) throws RemoteException;

    void migrateAppData(String str, String str2, int i, int i2) throws RemoteException;

    void migrateLegacyObbData() throws RemoteException;

    boolean migrateSdpDb(String str, int i) throws RemoteException;

    void moveAb(String str, String str2, String str3, String str4) throws RemoteException;

    void moveCompleteApp(String str, String str2, String str3, int i, String str4, int i2, String str5) throws RemoteException;

    void onPrivateVolumeRemoved(String str) throws RemoteException;

    boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4) throws RemoteException;

    void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) throws RemoteException;

    boolean reconcileSecondaryDexFile(String str, String str2, int i, String[] strArr, String str3, int i2) throws RemoteException;

    boolean removeEncPkgDir(int i, String str) throws RemoteException;

    boolean removeEncUserDir(int i) throws RemoteException;

    boolean removeNotTargetedPreloadApksIfNeeded() throws RemoteException;

    void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4) throws RemoteException;

    void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3) throws RemoteException;

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

    public static abstract class Stub extends Binder implements IInstalld {
        public static final String DESCRIPTOR = "android.os.IInstalld";
        static final int TRANSACTION_cleanupInvalidPackageDirs = 49;
        static final int TRANSACTION_clearAppData = 9;
        static final int TRANSACTION_clearAppProfiles = 25;
        static final int TRANSACTION_compressFile = 71;
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
        static final int TRANSACTION_getCompressedStats = 72;
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
        static final int TRANSACTION_installSpegCacheToDalvikCache = 70;
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
        static final int TRANSACTION_scanApkStats = 73;
        static final int TRANSACTION_setAppQuota = 17;
        static final int TRANSACTION_setDualDARPolicyDir = 65;
        static final int TRANSACTION_setDualDARPolicyDirRecursively = 66;
        static final int TRANSACTION_setEviction = 64;
        static final int TRANSACTION_setFirstBoot = 3;
        static final int TRANSACTION_snapshotAppData = 42;
        static final int TRANSACTION_tryMountDataMirror = 46;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 72;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstalld asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInstalld)) {
                return (IInstalld) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createUserData";
                case 2:
                    return "destroyUserData";
                case 3:
                    return "setFirstBoot";
                case 4:
                    return "createAppData";
                case 5:
                    return "createAppDataBatched";
                case 6:
                    return "reconcileSdkData";
                case 7:
                    return "restoreconAppData";
                case 8:
                    return "migrateAppData";
                case 9:
                    return "clearAppData";
                case 10:
                    return "destroyAppData";
                case 11:
                    return "fixupAppData";
                case 12:
                    return "getAppSize";
                case 13:
                    return "getUserSize";
                case 14:
                    return "getExternalSize";
                case 15:
                    return "getAppCrates";
                case 16:
                    return "getUserCrates";
                case 17:
                    return "setAppQuota";
                case 18:
                    return "moveCompleteApp";
                case 19:
                    return "dexopt";
                case 20:
                    return "controlDexOptBlocking";
                case 21:
                    return "rmdex";
                case 22:
                    return "mergeProfiles";
                case 23:
                    return "dumpProfiles";
                case 24:
                    return "copySystemProfile";
                case 25:
                    return "clearAppProfiles";
                case 26:
                    return "destroyAppProfiles";
                case 27:
                    return "deleteReferenceProfile";
                case 28:
                    return "createProfileSnapshot";
                case 29:
                    return "destroyProfileSnapshot";
                case 30:
                    return "rmPackageDir";
                case 31:
                    return "freeCache";
                case 32:
                    return "linkNativeLibraryDirectory";
                case 33:
                    return "createOatDir";
                case 34:
                    return "linkFile";
                case 35:
                    return "moveAb";
                case 36:
                    return "deleteOdex";
                case 37:
                    return "reconcileSecondaryDexFile";
                case 38:
                    return "hashSecondaryDexFile";
                case 39:
                    return "invalidateMounts";
                case 40:
                    return "isQuotaSupported";
                case 41:
                    return "prepareAppProfile";
                case 42:
                    return "snapshotAppData";
                case 43:
                    return "restoreAppDataSnapshot";
                case 44:
                    return "destroyAppDataSnapshot";
                case 45:
                    return "destroyCeSnapshotsNotSpecified";
                case 46:
                    return "tryMountDataMirror";
                case 47:
                    return "onPrivateVolumeRemoved";
                case 48:
                    return "migrateLegacyObbData";
                case 49:
                    return "cleanupInvalidPackageDirs";
                case 50:
                    return "getOdexVisibility";
                case 51:
                    return "removeNotTargetedPreloadApksIfNeeded";
                case 52:
                    return "createFsveritySetupAuthToken";
                case 53:
                    return "enableFsverity";
                case 54:
                    return "copyKnoxAppData";
                case 55:
                    return "copyKnoxChunks";
                case 56:
                    return "copyKnoxCancel";
                case 57:
                    return "getKnoxFileInfo";
                case 58:
                    return "getKnoxScanDir";
                case 59:
                    return "deleteKnoxFile";
                case 60:
                    return "createEncAppData";
                case 61:
                    return "removeEncPkgDir";
                case 62:
                    return "removeEncUserDir";
                case 63:
                    return "migrateSdpDb";
                case 64:
                    return "setEviction";
                case 65:
                    return "setDualDARPolicyDir";
                case 66:
                    return "setDualDARPolicyDirRecursively";
                case 67:
                    return "hasDualDARPolicy";
                case 68:
                    return "hasDualDARPolicyRecursively";
                case 69:
                    return "getDualDARLockstate";
                case 70:
                    return "installSpegCacheToDalvikCache";
                case 71:
                    return "compressFile";
                case 72:
                    return "getCompressedStats";
                case 73:
                    return "scanApkStats";
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createUserData(string, i3, i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserData(string2, i6, i7);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    setFirstBoot();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    CreateAppDataArgs createAppDataArgs = (CreateAppDataArgs) parcel.readTypedObject(CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    CreateAppDataResult createAppDataResultCreateAppData = createAppData(createAppDataArgs);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAppDataResultCreateAppData, 1);
                    return true;
                case 5:
                    CreateAppDataArgs[] createAppDataArgsArr = (CreateAppDataArgs[]) parcel.createTypedArray(CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    CreateAppDataResult[] createAppDataResultArrCreateAppDataBatched = createAppDataBatched(createAppDataArgsArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(createAppDataResultArrCreateAppDataBatched, 1);
                    return true;
                case 6:
                    ReconcileSdkDataArgs reconcileSdkDataArgs = (ReconcileSdkDataArgs) parcel.readTypedObject(ReconcileSdkDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    reconcileSdkData(reconcileSdkDataArgs);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreconAppData(string3, string4, i8, i9, i10, string5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    migrateAppData(string6, string7, i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    clearAppData(string8, string9, i13, i14, j);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    destroyAppData(string10, string11, i15, i16, j2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string12 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    fixupAppData(string12, i17);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string13 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    long[] jArrCreateLongArray = parcel.createLongArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    long[] appSize = getAppSize(string13, strArrCreateStringArray, i18, i19, i20, jArrCreateLongArray, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(appSize);
                    return true;
                case 13:
                    String string14 = parcel.readString();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] userSize = getUserSize(string14, i21, i22, iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(userSize);
                    return true;
                case 14:
                    String string15 = parcel.readString();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] externalSize = getExternalSize(string15, i23, i24, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(externalSize);
                    return true;
                case 15:
                    String string16 = parcel.readString();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CrateMetadata[] appCrates = getAppCrates(string16, strArrCreateStringArray3, i25);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(appCrates, 1);
                    return true;
                case 16:
                    String string17 = parcel.readString();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CrateMetadata[] userCrates = getUserCrates(string17, i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(userCrates, 1);
                    return true;
                case 17:
                    String string18 = parcel.readString();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAppQuota(string18, i27, i28, j3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    int i29 = parcel.readInt();
                    String string22 = parcel.readString();
                    int i30 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveCompleteApp(string19, string20, string21, i29, string22, i30, string23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string24 = parcel.readString();
                    int i31 = parcel.readInt();
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    int i32 = parcel.readInt();
                    String string27 = parcel.readString();
                    int i33 = parcel.readInt();
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i34 = parcel.readInt();
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDexopt = dexopt(string24, i31, string25, string26, i32, string27, i33, string28, string29, string30, string31, z, i34, string32, string33, string34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDexopt);
                    return true;
                case 20:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    controlDexOptBlocking(z2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmdex(string35, string36);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i35 = parcel.readInt();
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMergeProfiles = mergeProfiles(i35, string37, string38);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMergeProfiles);
                    return true;
                case 23:
                    int i36 = parcel.readInt();
                    String string39 = parcel.readString();
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zDumpProfiles = dumpProfiles(i36, string39, string40, string41, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDumpProfiles);
                    return true;
                case 24:
                    String string42 = parcel.readString();
                    int i37 = parcel.readInt();
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCopySystemProfile = copySystemProfile(string42, i37, string43, string44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCopySystemProfile);
                    return true;
                case 25:
                    String string45 = parcel.readString();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppProfiles(string45, string46);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyAppProfiles(string47);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String string48 = parcel.readString();
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteReferenceProfile(string48, string49);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i38 = parcel.readInt();
                    String string50 = parcel.readString();
                    String string51 = parcel.readString();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCreateProfileSnapshot = createProfileSnapshot(i38, string50, string51, string52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCreateProfileSnapshot);
                    return true;
                case 29:
                    String string53 = parcel.readString();
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyProfileSnapshot(string53, string54);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string55 = parcel.readString();
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmPackageDir(string55, string56);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String string57 = parcel.readString();
                    long j4 = parcel.readLong();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    freeCache(string57, j4, i39);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String string58 = parcel.readString();
                    String string59 = parcel.readString();
                    String string60 = parcel.readString();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    linkNativeLibraryDirectory(string58, string59, string60, i40);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String string61 = parcel.readString();
                    String string62 = parcel.readString();
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createOatDir(string61, string62, string63);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string64 = parcel.readString();
                    String string65 = parcel.readString();
                    String string66 = parcel.readString();
                    String string67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    linkFile(string64, string65, string66, string67);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String string68 = parcel.readString();
                    String string69 = parcel.readString();
                    String string70 = parcel.readString();
                    String string71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveAb(string68, string69, string70, string71);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String string72 = parcel.readString();
                    String string73 = parcel.readString();
                    String string74 = parcel.readString();
                    String string75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jDeleteOdex = deleteOdex(string72, string73, string74, string75);
                    parcel2.writeNoException();
                    parcel2.writeLong(jDeleteOdex);
                    return true;
                case 37:
                    String string76 = parcel.readString();
                    String string77 = parcel.readString();
                    int i41 = parcel.readInt();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    String string78 = parcel.readString();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zReconcileSecondaryDexFile = reconcileSecondaryDexFile(string76, string77, i41, strArrCreateStringArray4, string78, i42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReconcileSecondaryDexFile);
                    return true;
                case 38:
                    String string79 = parcel.readString();
                    String string80 = parcel.readString();
                    int i43 = parcel.readInt();
                    String string81 = parcel.readString();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] bArrHashSecondaryDexFile = hashSecondaryDexFile(string79, string80, i43, string81, i44);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHashSecondaryDexFile);
                    return true;
                case 39:
                    invalidateMounts();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String string82 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsQuotaSupported = isQuotaSupported(string82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsQuotaSupported);
                    return true;
                case 41:
                    String string83 = parcel.readString();
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    String string84 = parcel.readString();
                    String string85 = parcel.readString();
                    String string86 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zPrepareAppProfile = prepareAppProfile(string83, i45, i46, string84, string85, string86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPrepareAppProfile);
                    return true;
                case 42:
                    String string87 = parcel.readString();
                    String string88 = parcel.readString();
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jSnapshotAppData = snapshotAppData(string87, string88, i47, i48, i49);
                    parcel2.writeNoException();
                    parcel2.writeLong(jSnapshotAppData);
                    return true;
                case 43:
                    String string89 = parcel.readString();
                    String string90 = parcel.readString();
                    int i50 = parcel.readInt();
                    String string91 = parcel.readString();
                    int i51 = parcel.readInt();
                    int i52 = parcel.readInt();
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAppDataSnapshot(string89, string90, i50, string91, i51, i52, i53);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    String string92 = parcel.readString();
                    String string93 = parcel.readString();
                    int i54 = parcel.readInt();
                    long j5 = parcel.readLong();
                    int i55 = parcel.readInt();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyAppDataSnapshot(string92, string93, i54, j5, i55, i56);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String string94 = parcel.readString();
                    int i57 = parcel.readInt();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    destroyCeSnapshotsNotSpecified(string94, i57, iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String string95 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    tryMountDataMirror(string95);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String string96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPrivateVolumeRemoved(string96);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    migrateLegacyObbData();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string97 = parcel.readString();
                    int i58 = parcel.readInt();
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupInvalidPackageDirs(string97, i58, i59);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String string98 = parcel.readString();
                    String string99 = parcel.readString();
                    String string100 = parcel.readString();
                    String string101 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int odexVisibility = getOdexVisibility(string98, string99, string100, string101);
                    parcel2.writeNoException();
                    parcel2.writeInt(odexVisibility);
                    return true;
                case 51:
                    boolean zRemoveNotTargetedPreloadApksIfNeeded = removeNotTargetedPreloadApksIfNeeded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveNotTargetedPreloadApksIfNeeded);
                    return true;
                case 52:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IFsveritySetupAuthToken iFsveritySetupAuthTokenCreateFsveritySetupAuthToken = createFsveritySetupAuthToken(parcelFileDescriptor, i60);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iFsveritySetupAuthTokenCreateFsveritySetupAuthToken);
                    return true;
                case 53:
                    IFsveritySetupAuthToken iFsveritySetupAuthTokenAsInterface = IFsveritySetupAuthToken.Stub.asInterface(parcel.readStrongBinder());
                    String string102 = parcel.readString();
                    String string103 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iEnableFsverity = enableFsverity(iFsveritySetupAuthTokenAsInterface, string102, string103);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnableFsverity);
                    return true;
                case 54:
                    String string104 = parcel.readString();
                    int i61 = parcel.readInt();
                    String string105 = parcel.readString();
                    int i62 = parcel.readInt();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCopyKnoxAppData = copyKnoxAppData(string104, i61, string105, i62, i63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCopyKnoxAppData);
                    return true;
                case 55:
                    String string106 = parcel.readString();
                    int i64 = parcel.readInt();
                    String string107 = parcel.readString();
                    int i65 = parcel.readInt();
                    int i66 = parcel.readInt();
                    long j6 = parcel.readLong();
                    long j7 = parcel.readLong();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int iCopyKnoxChunks = copyKnoxChunks(string106, i64, string107, i65, i66, j6, j7, j8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCopyKnoxChunks);
                    return true;
                case 56:
                    String string108 = parcel.readString();
                    long j9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zCopyKnoxCancel = copyKnoxCancel(string108, j9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCopyKnoxCancel);
                    return true;
                case 57:
                    String string109 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long[] knoxFileInfo = getKnoxFileInfo(string109);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(knoxFileInfo);
                    return true;
                case 58:
                    String string110 = parcel.readString();
                    long j10 = parcel.readLong();
                    ArrayList arrayList = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean knoxScanDir = getKnoxScanDir(string110, j10, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxScanDir);
                    parcel2.writeStringList(arrayList);
                    return true;
                case 59:
                    String string111 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteKnoxFile = deleteKnoxFile(string111);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteKnoxFile);
                    return true;
                case 60:
                    String string112 = parcel.readString();
                    int i67 = parcel.readInt();
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCreateEncAppData = createEncAppData(string112, i67, i68, i69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCreateEncAppData);
                    return true;
                case 61:
                    int i70 = parcel.readInt();
                    String string113 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveEncPkgDir = removeEncPkgDir(i70, string113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveEncPkgDir);
                    return true;
                case 62:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveEncUserDir = removeEncUserDir(i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveEncUserDir);
                    return true;
                case 63:
                    String string114 = parcel.readString();
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMigrateSdpDb = migrateSdpDb(string114, i72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMigrateSdpDb);
                    return true;
                case 64:
                    int i73 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean eviction = setEviction(i73, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(eviction);
                    return true;
                case 65:
                    int i74 = parcel.readInt();
                    int i75 = parcel.readInt();
                    String string115 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyDir = setDualDARPolicyDir(i74, i75, string115);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyDir);
                    return true;
                case 66:
                    int i76 = parcel.readInt();
                    int i77 = parcel.readInt();
                    String string116 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyDirRecursively = setDualDARPolicyDirRecursively(i76, i77, string116);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyDirRecursively);
                    return true;
                case 67:
                    String string117 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasDualDARPolicy = hasDualDARPolicy(string117);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDualDARPolicy);
                    return true;
                case 68:
                    String string118 = parcel.readString();
                    ArrayList arrayList2 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zHasDualDARPolicyRecursively = hasDualDARPolicyRecursively(string118, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDualDARPolicyRecursively);
                    parcel2.writeStringList(arrayList2);
                    return true;
                case 69:
                    boolean dualDARLockstate = getDualDARLockstate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARLockstate);
                    return true;
                case 70:
                    String string119 = parcel.readString();
                    int i78 = parcel.readInt();
                    int i79 = parcel.readInt();
                    String string120 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zInstallSpegCacheToDalvikCache = installSpegCacheToDalvikCache(string119, i78, i79, string120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInstallSpegCacheToDalvikCache);
                    return true;
                case 71:
                    String string121 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    compressFile(string121, z5);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String string122 = parcel.readString();
                    int i80 = parcel.readInt();
                    if (i80 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i80);
                    }
                    long[] jArr = i80 < 0 ? null : new long[i80];
                    parcel.enforceNoDataAvail();
                    boolean compressedStats = getCompressedStats(string122, jArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(compressedStats);
                    parcel2.writeLongArray(jArr);
                    return true;
                case 73:
                    String string123 = parcel.readString();
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scanApkStats(string123, i81);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInstalld {
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

            @Override // android.os.IInstalld
            public void createUserData(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyUserData(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void setFirstBoot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(createAppDataArgs, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CreateAppDataResult) parcelObtain2.readTypedObject(CreateAppDataResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(createAppDataArgsArr, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CreateAppDataResult[]) parcelObtain2.createTypedArray(CreateAppDataResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(reconcileSdkDataArgs, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void migrateAppData(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void clearAppData(String str, String str2, int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppData(String str, String str2, int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void fixupAppData(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getAppSize(String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLongArray(jArr);
                    parcelObtain.writeStringArray(strArr2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getUserSize(String str, int i, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getExternalSize(String str, int i, int i2, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CrateMetadata[] getAppCrates(String str, String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CrateMetadata[]) parcelObtain2.createTypedArray(CrateMetadata.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CrateMetadata[] getUserCrates(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CrateMetadata[]) parcelObtain2.createTypedArray(CrateMetadata.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void setAppQuota(String str, int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void moveCompleteApp(String str, String str2, String str3, int i, String str4, int i2, String str5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str5);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean dexopt(String str, int i, String str2, String str3, int i2, String str4, int i3, String str5, String str6, String str7, String str8, boolean z, int i4, String str9, String str10, String str11) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str9);
                    parcelObtain.writeString(str10);
                    parcelObtain.writeString(str11);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void controlDexOptBlocking(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void rmdex(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int mergeProfiles(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean dumpProfiles(int i, String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean copySystemProfile(String str, int i, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void clearAppProfiles(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppProfiles(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void deleteReferenceProfile(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean createProfileSnapshot(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyProfileSnapshot(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void rmPackageDir(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void freeCache(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void linkNativeLibraryDirectory(String str, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void createOatDir(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void linkFile(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void moveAb(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long deleteOdex(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean reconcileSecondaryDexFile(String str, String str2, int i, String[] strArr, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void invalidateMounts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean isQuotaSupported(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long snapshotAppData(String str, String str2, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void tryMountDataMirror(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void onPrivateVolumeRemoved(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void migrateLegacyObbData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void cleanupInvalidPackageDirs(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int getOdexVisibility(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean removeNotTargetedPreloadApksIfNeeded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IFsveritySetupAuthToken.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int enableFsverity(IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFsveritySetupAuthToken);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean copyKnoxCancel(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getKnoxFileInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean getKnoxScanDir(String str, long j, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readStringList(list);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean deleteKnoxFile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean createEncAppData(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean removeEncPkgDir(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean removeEncUserDir(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean migrateSdpDb(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean setEviction(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean setDualDARPolicyDir(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean setDualDARPolicyDirRecursively(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean hasDualDARPolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean hasDualDARPolicyRecursively(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readStringList(list);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean getDualDARLockstate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean installSpegCacheToDalvikCache(String str, int i, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void compressFile(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean getCompressedStats(String str, long[] jArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(jArr.length);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z = parcelObtain2.readBoolean();
                    parcelObtain2.readLongArray(jArr);
                    return z;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void scanApkStats(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public interface IFsveritySetupAuthToken extends IInterface {
        public static final String DESCRIPTOR = "android.os.IInstalld.IFsveritySetupAuthToken";

        public static class Default implements IFsveritySetupAuthToken {
            @Override // android.os.IInterface
            public IBinder asBinder() {
                return null;
            }
        }

        public static abstract class Stub extends Binder implements IFsveritySetupAuthToken {
            public static String getDefaultTransactionName(int i) {
                return null;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            @Override // android.os.Binder
            public int getMaxTransactionId() {
                return 0;
            }

            public Stub() {
                attachInterface(this, IFsveritySetupAuthToken.DESCRIPTOR);
            }

            public static IFsveritySetupAuthToken asInterface(IBinder iBinder) {
                if (iBinder == null) {
                    return null;
                }
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFsveritySetupAuthToken.DESCRIPTOR);
                if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFsveritySetupAuthToken)) {
                    return (IFsveritySetupAuthToken) iInterfaceQueryLocalInterface;
                }
                return new Proxy(iBinder);
            }

            @Override // android.os.Binder
            public String getTransactionName(int i) {
                return getDefaultTransactionName(i);
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                if (i == 1598968902) {
                    parcel2.writeString(IFsveritySetupAuthToken.DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }

            private static class Proxy implements IFsveritySetupAuthToken {
                private IBinder mRemote;

                Proxy(IBinder iBinder) {
                    this.mRemote = iBinder;
                }

                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return this.mRemote;
                }

                public String getInterfaceDescriptor() {
                    return IFsveritySetupAuthToken.DESCRIPTOR;
                }
            }
        }
    }
}
