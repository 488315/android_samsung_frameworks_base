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

    public static class Default implements IInstalld {
        @Override // android.os.IInstalld
        public void createUserData(String uuid, int userId, int userSerial, int flags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyUserData(String uuid, int userId, int flags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void setFirstBoot() throws RemoteException {
        }

        @Override // android.os.IInstalld
        public CreateAppDataResult createAppData(CreateAppDataArgs args) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] args) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void reconcileSdkData(ReconcileSdkDataArgs args) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void restoreconAppData(String uuid, String packageName, int userId, int flags, int appId, String seInfo) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void migrateAppData(String uuid, String packageName, int userId, int flags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void clearAppData(String uuid, String packageName, int userId, int flags, long ceDataInode) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppData(String uuid, String packageName, int userId, int flags, long ceDataInode) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void fixupAppData(String uuid, int flags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public long[] getAppSize(String uuid, String[] packageNames, int userId, int flags, int appId, long[] ceDataInodes, String[] codePaths) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getUserSize(String uuid, int userId, int flags, int[] appIds) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getExternalSize(String uuid, int userId, int flags, int[] appIds) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public CrateMetadata[] getAppCrates(String uuid, String[] packageNames, int userId) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public CrateMetadata[] getUserCrates(String uuid, int userId) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void setAppQuota(String uuid, int userId, int appId, long cacheQuota) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void moveCompleteApp(String fromUuid, String toUuid, String packageName, int appId, String seInfo, int targetSdkVersion, String fromCodePath) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean dexopt(String apkPath, int uid, String packageName, String instructionSet, int dexoptNeeded, String outputPath, int dexFlags, String compilerFilter, String uuid, String sharedLibraries, String seInfo, boolean downgrade, int targetSdkVersion, String profileName, String dexMetadataPath, String compilationReason) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void controlDexOptBlocking(boolean block) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void rmdex(String codePath, String instructionSet) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public int mergeProfiles(int uid, String packageName, String profileName) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean dumpProfiles(int uid, String packageName, String profileName, String codePath, boolean dumpClassesAndMethods) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean copySystemProfile(String systemProfile, int uid, String packageName, String profileName) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void clearAppProfiles(String packageName, String profileName) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppProfiles(String packageName) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void deleteReferenceProfile(String packageName, String profileName) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean createProfileSnapshot(int appId, String packageName, String profileName, String classpath) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void destroyProfileSnapshot(String packageName, String profileName) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void rmPackageDir(String packageName, String packageDir) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void freeCache(String uuid, long targetFreeBytes, int flags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void linkNativeLibraryDirectory(String uuid, String packageName, String nativeLibPath32, int userId) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void createOatDir(String packageName, String oatDir, String instructionSet) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void linkFile(String packageName, String relativePath, String fromBase, String toBase) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void moveAb(String packageName, String apkPath, String instructionSet, String outputPath) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public long deleteOdex(String packageName, String apkPath, String instructionSet, String outputPath) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public boolean reconcileSecondaryDexFile(String dexPath, String pkgName, int uid, String[] isas, String volume_uuid, int storage_flag) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public byte[] hashSecondaryDexFile(String dexPath, String pkgName, int uid, String volumeUuid, int storageFlag) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void invalidateMounts() throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean isQuotaSupported(String uuid) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean prepareAppProfile(String packageName, int userId, int appId, String profileName, String codePath, String dexMetadata) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public long snapshotAppData(String uuid, String packageName, int userId, int snapshotId, int storageFlags) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public void restoreAppDataSnapshot(String uuid, String packageName, int appId, String seInfo, int user, int snapshotId, int storageflags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppDataSnapshot(String uuid, String packageName, int userId, long ceSnapshotInode, int snapshotId, int storageFlags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyCeSnapshotsNotSpecified(String uuid, int userId, int[] retainSnapshotIds) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void tryMountDataMirror(String volumeUuid) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void onPrivateVolumeRemoved(String volumeUuid) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void migrateLegacyObbData() throws RemoteException {
        }

        @Override // android.os.IInstalld
        public void cleanupInvalidPackageDirs(String uuid, int userId, int flags) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public int getOdexVisibility(String packageName, String apkPath, String instructionSet, String outputPath) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean removeNotTargetedPreloadApksIfNeeded() throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor authFd, int uid) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public int enableFsverity(IFsveritySetupAuthToken authToken, String filePath, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean copyKnoxAppData(String srcPath, int srcId, String dstPath, int dstId, int flags) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public int copyKnoxChunks(String srcPath, int srcId, String dstPath, int dstId, int copyFlags, long fileOffset, long fileLength, long sessionId) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean copyKnoxCancel(String dstPath, long sessionId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public long[] getKnoxFileInfo(String dstPath) throws RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public boolean getKnoxScanDir(String dstPath, long sessionId, List<String> output) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean deleteKnoxFile(String dstPath) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean createEncAppData(String packageName, int userId, int appId, int targetSdkVersion) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean removeEncPkgDir(int userId, String pkgName) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean removeEncUserDir(int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean migrateSdpDb(String dbPath, int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setEviction(int userId, boolean evict) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setDualDARPolicyDir(int userId, int flags, String path) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean setDualDARPolicyDirRecursively(int userId, int flags, String path) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean hasDualDARPolicy(String path) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean hasDualDARPolicyRecursively(String path, List<String> notAppliedPaths) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean getDualDARLockstate() throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void compressFile(String packageDir, boolean isCompress) throws RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean getCompressedStats(String packageDir, long[] sizeInfo) throws RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void scanApkStats(String packageDir, int scanFlags) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInstalld {
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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstalld asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IInstalld)) {
                return (IInstalld) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "compressFile";
                case 71:
                    return "getCompressedStats";
                case 72:
                    return "scanApkStats";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            long[] _arg1;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    createUserData(_arg0, _arg12, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg13 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    destroyUserData(_arg02, _arg13, _arg22);
                    reply.writeNoException();
                    return true;
                case 3:
                    setFirstBoot();
                    reply.writeNoException();
                    return true;
                case 4:
                    CreateAppDataArgs _arg03 = (CreateAppDataArgs) data.readTypedObject(CreateAppDataArgs.CREATOR);
                    data.enforceNoDataAvail();
                    CreateAppDataResult _result = createAppData(_arg03);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 5:
                    CreateAppDataArgs[] _arg04 = (CreateAppDataArgs[]) data.createTypedArray(CreateAppDataArgs.CREATOR);
                    data.enforceNoDataAvail();
                    CreateAppDataResult[] _result2 = createAppDataBatched(_arg04);
                    reply.writeNoException();
                    reply.writeTypedArray(_result2, 1);
                    return true;
                case 6:
                    ReconcileSdkDataArgs _arg05 = (ReconcileSdkDataArgs) data.readTypedObject(ReconcileSdkDataArgs.CREATOR);
                    data.enforceNoDataAvail();
                    reconcileSdkData(_arg05);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    String _arg14 = data.readString();
                    int _arg23 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    String _arg5 = data.readString();
                    data.enforceNoDataAvail();
                    restoreconAppData(_arg06, _arg14, _arg23, _arg32, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    String _arg15 = data.readString();
                    int _arg24 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    migrateAppData(_arg07, _arg15, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    String _arg16 = data.readString();
                    int _arg25 = data.readInt();
                    int _arg34 = data.readInt();
                    long _arg42 = data.readLong();
                    data.enforceNoDataAvail();
                    clearAppData(_arg08, _arg16, _arg25, _arg34, _arg42);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    String _arg17 = data.readString();
                    int _arg26 = data.readInt();
                    int _arg35 = data.readInt();
                    long _arg43 = data.readLong();
                    data.enforceNoDataAvail();
                    destroyAppData(_arg09, _arg17, _arg26, _arg35, _arg43);
                    reply.writeNoException();
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    fixupAppData(_arg010, _arg18);
                    reply.writeNoException();
                    return true;
                case 12:
                    String _arg011 = data.readString();
                    String[] _arg19 = data.createStringArray();
                    int _arg27 = data.readInt();
                    int _arg36 = data.readInt();
                    int _arg44 = data.readInt();
                    long[] _arg52 = data.createLongArray();
                    String[] _arg6 = data.createStringArray();
                    data.enforceNoDataAvail();
                    long[] _result3 = getAppSize(_arg011, _arg19, _arg27, _arg36, _arg44, _arg52, _arg6);
                    reply.writeNoException();
                    reply.writeLongArray(_result3);
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    int _arg110 = data.readInt();
                    int _arg28 = data.readInt();
                    int[] _arg37 = data.createIntArray();
                    data.enforceNoDataAvail();
                    long[] _result4 = getUserSize(_arg012, _arg110, _arg28, _arg37);
                    reply.writeNoException();
                    reply.writeLongArray(_result4);
                    return true;
                case 14:
                    String _arg013 = data.readString();
                    int _arg111 = data.readInt();
                    int _arg29 = data.readInt();
                    int[] _arg38 = data.createIntArray();
                    data.enforceNoDataAvail();
                    long[] _result5 = getExternalSize(_arg013, _arg111, _arg29, _arg38);
                    reply.writeNoException();
                    reply.writeLongArray(_result5);
                    return true;
                case 15:
                    String _arg014 = data.readString();
                    String[] _arg112 = data.createStringArray();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    CrateMetadata[] _result6 = getAppCrates(_arg014, _arg112, _arg210);
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                case 16:
                    String _arg015 = data.readString();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    CrateMetadata[] _result7 = getUserCrates(_arg015, _arg113);
                    reply.writeNoException();
                    reply.writeTypedArray(_result7, 1);
                    return true;
                case 17:
                    String _arg016 = data.readString();
                    int _arg114 = data.readInt();
                    int _arg211 = data.readInt();
                    long _arg39 = data.readLong();
                    data.enforceNoDataAvail();
                    setAppQuota(_arg016, _arg114, _arg211, _arg39);
                    reply.writeNoException();
                    return true;
                case 18:
                    String _arg017 = data.readString();
                    String _arg115 = data.readString();
                    String _arg212 = data.readString();
                    int _arg310 = data.readInt();
                    String _arg45 = data.readString();
                    int _arg53 = data.readInt();
                    String _arg62 = data.readString();
                    data.enforceNoDataAvail();
                    moveCompleteApp(_arg017, _arg115, _arg212, _arg310, _arg45, _arg53, _arg62);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg018 = data.readString();
                    int _arg116 = data.readInt();
                    String _arg213 = data.readString();
                    String _arg311 = data.readString();
                    int _arg46 = data.readInt();
                    String _arg54 = data.readString();
                    int _arg63 = data.readInt();
                    String _arg7 = data.readString();
                    String _arg8 = data.readString();
                    String _arg9 = data.readString();
                    String _arg10 = data.readString();
                    boolean _arg11 = data.readBoolean();
                    int _arg122 = data.readInt();
                    String _arg132 = data.readString();
                    String _arg142 = data.readString();
                    String _arg152 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result8 = dexopt(_arg018, _arg116, _arg213, _arg311, _arg46, _arg54, _arg63, _arg7, _arg8, _arg9, _arg10, _arg11, _arg122, _arg132, _arg142, _arg152);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 20:
                    boolean _arg019 = data.readBoolean();
                    data.enforceNoDataAvail();
                    controlDexOptBlocking(_arg019);
                    reply.writeNoException();
                    return true;
                case 21:
                    String _arg020 = data.readString();
                    String _arg117 = data.readString();
                    data.enforceNoDataAvail();
                    rmdex(_arg020, _arg117);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg021 = data.readInt();
                    String _arg118 = data.readString();
                    String _arg214 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = mergeProfiles(_arg021, _arg118, _arg214);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 23:
                    int _arg022 = data.readInt();
                    String _arg119 = data.readString();
                    String _arg215 = data.readString();
                    String _arg312 = data.readString();
                    boolean _arg47 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result10 = dumpProfiles(_arg022, _arg119, _arg215, _arg312, _arg47);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 24:
                    String _arg023 = data.readString();
                    int _arg120 = data.readInt();
                    String _arg216 = data.readString();
                    String _arg313 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result11 = copySystemProfile(_arg023, _arg120, _arg216, _arg313);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 25:
                    String _arg024 = data.readString();
                    String _arg121 = data.readString();
                    data.enforceNoDataAvail();
                    clearAppProfiles(_arg024, _arg121);
                    reply.writeNoException();
                    return true;
                case 26:
                    String _arg025 = data.readString();
                    data.enforceNoDataAvail();
                    destroyAppProfiles(_arg025);
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg026 = data.readString();
                    String _arg123 = data.readString();
                    data.enforceNoDataAvail();
                    deleteReferenceProfile(_arg026, _arg123);
                    reply.writeNoException();
                    return true;
                case 28:
                    int _arg027 = data.readInt();
                    String _arg124 = data.readString();
                    String _arg217 = data.readString();
                    String _arg314 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result12 = createProfileSnapshot(_arg027, _arg124, _arg217, _arg314);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 29:
                    String _arg028 = data.readString();
                    String _arg125 = data.readString();
                    data.enforceNoDataAvail();
                    destroyProfileSnapshot(_arg028, _arg125);
                    reply.writeNoException();
                    return true;
                case 30:
                    String _arg029 = data.readString();
                    String _arg126 = data.readString();
                    data.enforceNoDataAvail();
                    rmPackageDir(_arg029, _arg126);
                    reply.writeNoException();
                    return true;
                case 31:
                    String _arg030 = data.readString();
                    long _arg127 = data.readLong();
                    int _arg218 = data.readInt();
                    data.enforceNoDataAvail();
                    freeCache(_arg030, _arg127, _arg218);
                    reply.writeNoException();
                    return true;
                case 32:
                    String _arg031 = data.readString();
                    String _arg128 = data.readString();
                    String _arg219 = data.readString();
                    int _arg315 = data.readInt();
                    data.enforceNoDataAvail();
                    linkNativeLibraryDirectory(_arg031, _arg128, _arg219, _arg315);
                    reply.writeNoException();
                    return true;
                case 33:
                    String _arg032 = data.readString();
                    String _arg129 = data.readString();
                    String _arg220 = data.readString();
                    data.enforceNoDataAvail();
                    createOatDir(_arg032, _arg129, _arg220);
                    reply.writeNoException();
                    return true;
                case 34:
                    String _arg033 = data.readString();
                    String _arg130 = data.readString();
                    String _arg221 = data.readString();
                    String _arg316 = data.readString();
                    data.enforceNoDataAvail();
                    linkFile(_arg033, _arg130, _arg221, _arg316);
                    reply.writeNoException();
                    return true;
                case 35:
                    String _arg034 = data.readString();
                    String _arg131 = data.readString();
                    String _arg222 = data.readString();
                    String _arg317 = data.readString();
                    data.enforceNoDataAvail();
                    moveAb(_arg034, _arg131, _arg222, _arg317);
                    reply.writeNoException();
                    return true;
                case 36:
                    String _arg035 = data.readString();
                    String _arg133 = data.readString();
                    String _arg223 = data.readString();
                    String _arg318 = data.readString();
                    data.enforceNoDataAvail();
                    long _result13 = deleteOdex(_arg035, _arg133, _arg223, _arg318);
                    reply.writeNoException();
                    reply.writeLong(_result13);
                    return true;
                case 37:
                    String _arg036 = data.readString();
                    String _arg134 = data.readString();
                    int _arg224 = data.readInt();
                    String[] _arg319 = data.createStringArray();
                    String _arg48 = data.readString();
                    int _arg55 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = reconcileSecondaryDexFile(_arg036, _arg134, _arg224, _arg319, _arg48, _arg55);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 38:
                    String _arg037 = data.readString();
                    String _arg135 = data.readString();
                    int _arg225 = data.readInt();
                    String _arg320 = data.readString();
                    int _arg49 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result15 = hashSecondaryDexFile(_arg037, _arg135, _arg225, _arg320, _arg49);
                    reply.writeNoException();
                    reply.writeByteArray(_result15);
                    return true;
                case 39:
                    invalidateMounts();
                    reply.writeNoException();
                    return true;
                case 40:
                    String _arg038 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result16 = isQuotaSupported(_arg038);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 41:
                    String _arg039 = data.readString();
                    int _arg136 = data.readInt();
                    int _arg226 = data.readInt();
                    String _arg321 = data.readString();
                    String _arg410 = data.readString();
                    String _arg56 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result17 = prepareAppProfile(_arg039, _arg136, _arg226, _arg321, _arg410, _arg56);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 42:
                    String _arg040 = data.readString();
                    String _arg137 = data.readString();
                    int _arg227 = data.readInt();
                    int _arg322 = data.readInt();
                    int _arg411 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result18 = snapshotAppData(_arg040, _arg137, _arg227, _arg322, _arg411);
                    reply.writeNoException();
                    reply.writeLong(_result18);
                    return true;
                case 43:
                    String _arg041 = data.readString();
                    String _arg138 = data.readString();
                    int _arg228 = data.readInt();
                    String _arg323 = data.readString();
                    int _arg412 = data.readInt();
                    int _arg57 = data.readInt();
                    int _arg64 = data.readInt();
                    data.enforceNoDataAvail();
                    restoreAppDataSnapshot(_arg041, _arg138, _arg228, _arg323, _arg412, _arg57, _arg64);
                    reply.writeNoException();
                    return true;
                case 44:
                    String _arg042 = data.readString();
                    String _arg139 = data.readString();
                    int _arg229 = data.readInt();
                    long _arg324 = data.readLong();
                    int _arg413 = data.readInt();
                    int _arg58 = data.readInt();
                    data.enforceNoDataAvail();
                    destroyAppDataSnapshot(_arg042, _arg139, _arg229, _arg324, _arg413, _arg58);
                    reply.writeNoException();
                    return true;
                case 45:
                    String _arg043 = data.readString();
                    int _arg140 = data.readInt();
                    int[] _arg230 = data.createIntArray();
                    data.enforceNoDataAvail();
                    destroyCeSnapshotsNotSpecified(_arg043, _arg140, _arg230);
                    reply.writeNoException();
                    return true;
                case 46:
                    String _arg044 = data.readString();
                    data.enforceNoDataAvail();
                    tryMountDataMirror(_arg044);
                    reply.writeNoException();
                    return true;
                case 47:
                    String _arg045 = data.readString();
                    data.enforceNoDataAvail();
                    onPrivateVolumeRemoved(_arg045);
                    reply.writeNoException();
                    return true;
                case 48:
                    migrateLegacyObbData();
                    reply.writeNoException();
                    return true;
                case 49:
                    String _arg046 = data.readString();
                    int _arg141 = data.readInt();
                    int _arg231 = data.readInt();
                    data.enforceNoDataAvail();
                    cleanupInvalidPackageDirs(_arg046, _arg141, _arg231);
                    reply.writeNoException();
                    return true;
                case 50:
                    String _arg047 = data.readString();
                    String _arg143 = data.readString();
                    String _arg232 = data.readString();
                    String _arg325 = data.readString();
                    data.enforceNoDataAvail();
                    int _result19 = getOdexVisibility(_arg047, _arg143, _arg232, _arg325);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 51:
                    boolean _result20 = removeNotTargetedPreloadApksIfNeeded();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 52:
                    ParcelFileDescriptor _arg048 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int _arg144 = data.readInt();
                    data.enforceNoDataAvail();
                    IFsveritySetupAuthToken _result21 = createFsveritySetupAuthToken(_arg048, _arg144);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result21);
                    return true;
                case 53:
                    IFsveritySetupAuthToken _arg049 = IFsveritySetupAuthToken.Stub.asInterface(data.readStrongBinder());
                    String _arg145 = data.readString();
                    String _arg233 = data.readString();
                    data.enforceNoDataAvail();
                    int _result22 = enableFsverity(_arg049, _arg145, _arg233);
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 54:
                    String _arg050 = data.readString();
                    int _arg146 = data.readInt();
                    String _arg234 = data.readString();
                    int _arg326 = data.readInt();
                    int _arg414 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result23 = copyKnoxAppData(_arg050, _arg146, _arg234, _arg326, _arg414);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 55:
                    String _arg051 = data.readString();
                    int _arg147 = data.readInt();
                    String _arg235 = data.readString();
                    int _arg327 = data.readInt();
                    int _arg415 = data.readInt();
                    long _arg59 = data.readLong();
                    long _arg65 = data.readLong();
                    long _arg72 = data.readLong();
                    data.enforceNoDataAvail();
                    int _result24 = copyKnoxChunks(_arg051, _arg147, _arg235, _arg327, _arg415, _arg59, _arg65, _arg72);
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case 56:
                    String _arg052 = data.readString();
                    long _arg148 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result25 = copyKnoxCancel(_arg052, _arg148);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 57:
                    String _arg053 = data.readString();
                    data.enforceNoDataAvail();
                    long[] _result26 = getKnoxFileInfo(_arg053);
                    reply.writeNoException();
                    reply.writeLongArray(_result26);
                    return true;
                case 58:
                    String _arg054 = data.readString();
                    long _arg149 = data.readLong();
                    List<String> _arg236 = new ArrayList<>();
                    data.enforceNoDataAvail();
                    boolean _result27 = getKnoxScanDir(_arg054, _arg149, _arg236);
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    reply.writeStringList(_arg236);
                    return true;
                case 59:
                    String _arg055 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result28 = deleteKnoxFile(_arg055);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 60:
                    String _arg056 = data.readString();
                    int _arg150 = data.readInt();
                    int _arg237 = data.readInt();
                    int _arg328 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = createEncAppData(_arg056, _arg150, _arg237, _arg328);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 61:
                    int _arg057 = data.readInt();
                    String _arg151 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result30 = removeEncPkgDir(_arg057, _arg151);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 62:
                    int _arg058 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result31 = removeEncUserDir(_arg058);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 63:
                    String _arg059 = data.readString();
                    int _arg153 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result32 = migrateSdpDb(_arg059, _arg153);
                    reply.writeNoException();
                    reply.writeBoolean(_result32);
                    return true;
                case 64:
                    int _arg060 = data.readInt();
                    boolean _arg154 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result33 = setEviction(_arg060, _arg154);
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 65:
                    int _arg061 = data.readInt();
                    int _arg155 = data.readInt();
                    String _arg238 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result34 = setDualDARPolicyDir(_arg061, _arg155, _arg238);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 66:
                    int _arg062 = data.readInt();
                    int _arg156 = data.readInt();
                    String _arg239 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result35 = setDualDARPolicyDirRecursively(_arg062, _arg156, _arg239);
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 67:
                    String _arg063 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result36 = hasDualDARPolicy(_arg063);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 68:
                    String _arg064 = data.readString();
                    List<String> _arg157 = new ArrayList<>();
                    data.enforceNoDataAvail();
                    boolean _result37 = hasDualDARPolicyRecursively(_arg064, _arg157);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    reply.writeStringList(_arg157);
                    return true;
                case 69:
                    boolean _result38 = getDualDARLockstate();
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 70:
                    String _arg065 = data.readString();
                    boolean _arg158 = data.readBoolean();
                    data.enforceNoDataAvail();
                    compressFile(_arg065, _arg158);
                    reply.writeNoException();
                    return true;
                case 71:
                    String _arg066 = data.readString();
                    int _arg1_length = data.readInt();
                    if (_arg1_length < 0) {
                        _arg1 = null;
                    } else {
                        _arg1 = new long[_arg1_length];
                    }
                    data.enforceNoDataAvail();
                    boolean _result39 = getCompressedStats(_arg066, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    reply.writeLongArray(_arg1);
                    return true;
                case 72:
                    String _arg067 = data.readString();
                    int _arg159 = data.readInt();
                    data.enforceNoDataAvail();
                    scanApkStats(_arg067, _arg159);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInstalld {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IInstalld
            public void createUserData(String uuid, int userId, int userSerial, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(userSerial);
                    _data.writeInt(flags);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyUserData(String uuid, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void setFirstBoot() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CreateAppDataResult createAppData(CreateAppDataArgs args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    CreateAppDataResult _result = (CreateAppDataResult) _reply.readTypedObject(CreateAppDataResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(args, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    CreateAppDataResult[] _result = (CreateAppDataResult[]) _reply.createTypedArray(CreateAppDataResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void reconcileSdkData(ReconcileSdkDataArgs args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void restoreconAppData(String uuid, String packageName, int userId, int flags, int appId, String seInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeInt(appId);
                    _data.writeString(seInfo);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void migrateAppData(String uuid, String packageName, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void clearAppData(String uuid, String packageName, int userId, int flags, long ceDataInode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeLong(ceDataInode);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppData(String uuid, String packageName, int userId, int flags, long ceDataInode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeLong(ceDataInode);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void fixupAppData(String uuid, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(flags);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getAppSize(String uuid, String[] packageNames, int userId, int flags, int appId, long[] ceDataInodes, String[] codePaths) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeInt(appId);
                    _data.writeLongArray(ceDataInodes);
                    _data.writeStringArray(codePaths);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getUserSize(String uuid, int userId, int flags, int[] appIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeIntArray(appIds);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getExternalSize(String uuid, int userId, int flags, int[] appIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeIntArray(appIds);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CrateMetadata[] getAppCrates(String uuid, String[] packageNames, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeStringArray(packageNames);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    CrateMetadata[] _result = (CrateMetadata[]) _reply.createTypedArray(CrateMetadata.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public CrateMetadata[] getUserCrates(String uuid, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    CrateMetadata[] _result = (CrateMetadata[]) _reply.createTypedArray(CrateMetadata.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void setAppQuota(String uuid, int userId, int appId, long cacheQuota) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(appId);
                    _data.writeLong(cacheQuota);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void moveCompleteApp(String fromUuid, String toUuid, String packageName, int appId, String seInfo, int targetSdkVersion, String fromCodePath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromUuid);
                    _data.writeString(toUuid);
                    _data.writeString(packageName);
                    _data.writeInt(appId);
                    _data.writeString(seInfo);
                    _data.writeInt(targetSdkVersion);
                    _data.writeString(fromCodePath);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean dexopt(String apkPath, int uid, String packageName, String instructionSet, int dexoptNeeded, String outputPath, int dexFlags, String compilerFilter, String uuid, String sharedLibraries, String seInfo, boolean downgrade, int targetSdkVersion, String profileName, String dexMetadataPath, String compilationReason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(apkPath);
                    _data.writeInt(uid);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(packageName);
                    try {
                        _data.writeString(instructionSet);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(dexoptNeeded);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(outputPath);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(dexFlags);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(compilerFilter);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(uuid);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(sharedLibraries);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(seInfo);
                    try {
                        _data.writeBoolean(downgrade);
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(targetSdkVersion);
                        _data.writeString(profileName);
                        _data.writeString(dexMetadataPath);
                        _data.writeString(compilationReason);
                        this.mRemote.transact(19, _data, _reply, 0);
                        _reply.readException();
                        boolean _result = _reply.readBoolean();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.os.IInstalld
            public void controlDexOptBlocking(boolean block) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(block);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void rmdex(String codePath, String instructionSet) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(codePath);
                    _data.writeString(instructionSet);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int mergeProfiles(int uid, String packageName, String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean dumpProfiles(int uid, String packageName, String profileName, String codePath, boolean dumpClassesAndMethods) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    _data.writeString(codePath);
                    _data.writeBoolean(dumpClassesAndMethods);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean copySystemProfile(String systemProfile, int uid, String packageName, String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(systemProfile);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void clearAppProfiles(String packageName, String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppProfiles(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void deleteReferenceProfile(String packageName, String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean createProfileSnapshot(int appId, String packageName, String profileName, String classpath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(appId);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    _data.writeString(classpath);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyProfileSnapshot(String packageName, String profileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(profileName);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void rmPackageDir(String packageName, String packageDir) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(packageDir);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void freeCache(String uuid, long targetFreeBytes, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeLong(targetFreeBytes);
                    _data.writeInt(flags);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void linkNativeLibraryDirectory(String uuid, String packageName, String nativeLibPath32, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeString(nativeLibPath32);
                    _data.writeInt(userId);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void createOatDir(String packageName, String oatDir, String instructionSet) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(oatDir);
                    _data.writeString(instructionSet);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void linkFile(String packageName, String relativePath, String fromBase, String toBase) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(relativePath);
                    _data.writeString(fromBase);
                    _data.writeString(toBase);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void moveAb(String packageName, String apkPath, String instructionSet, String outputPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(apkPath);
                    _data.writeString(instructionSet);
                    _data.writeString(outputPath);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long deleteOdex(String packageName, String apkPath, String instructionSet, String outputPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(apkPath);
                    _data.writeString(instructionSet);
                    _data.writeString(outputPath);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean reconcileSecondaryDexFile(String dexPath, String pkgName, int uid, String[] isas, String volume_uuid, int storage_flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dexPath);
                    _data.writeString(pkgName);
                    _data.writeInt(uid);
                    _data.writeStringArray(isas);
                    _data.writeString(volume_uuid);
                    _data.writeInt(storage_flag);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public byte[] hashSecondaryDexFile(String dexPath, String pkgName, int uid, String volumeUuid, int storageFlag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dexPath);
                    _data.writeString(pkgName);
                    _data.writeInt(uid);
                    _data.writeString(volumeUuid);
                    _data.writeInt(storageFlag);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void invalidateMounts() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean isQuotaSupported(String uuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean prepareAppProfile(String packageName, int userId, int appId, String profileName, String codePath, String dexMetadata) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(appId);
                    _data.writeString(profileName);
                    _data.writeString(codePath);
                    _data.writeString(dexMetadata);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long snapshotAppData(String uuid, String packageName, int userId, int snapshotId, int storageFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(snapshotId);
                    _data.writeInt(storageFlags);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void restoreAppDataSnapshot(String uuid, String packageName, int appId, String seInfo, int user, int snapshotId, int storageflags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(appId);
                    _data.writeString(seInfo);
                    _data.writeInt(user);
                    _data.writeInt(snapshotId);
                    _data.writeInt(storageflags);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppDataSnapshot(String uuid, String packageName, int userId, long ceSnapshotInode, int snapshotId, int storageFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeLong(ceSnapshotInode);
                    _data.writeInt(snapshotId);
                    _data.writeInt(storageFlags);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyCeSnapshotsNotSpecified(String uuid, int userId, int[] retainSnapshotIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeIntArray(retainSnapshotIds);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void tryMountDataMirror(String volumeUuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void onPrivateVolumeRemoved(String volumeUuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void migrateLegacyObbData() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void cleanupInvalidPackageDirs(String uuid, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int getOdexVisibility(String packageName, String apkPath, String instructionSet, String outputPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(apkPath);
                    _data.writeString(instructionSet);
                    _data.writeString(outputPath);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean removeNotTargetedPreloadApksIfNeeded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor authFd, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(authFd, 0);
                    _data.writeInt(uid);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    IFsveritySetupAuthToken _result = IFsveritySetupAuthToken.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int enableFsverity(IFsveritySetupAuthToken authToken, String filePath, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(authToken);
                    _data.writeString(filePath);
                    _data.writeString(packageName);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean copyKnoxAppData(String srcPath, int srcId, String dstPath, int dstId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(srcPath);
                    _data.writeInt(srcId);
                    _data.writeString(dstPath);
                    _data.writeInt(dstId);
                    _data.writeInt(flags);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int copyKnoxChunks(String srcPath, int srcId, String dstPath, int dstId, int copyFlags, long fileOffset, long fileLength, long sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(srcPath);
                    try {
                        _data.writeInt(srcId);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(dstPath);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(dstId);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
                try {
                    _data.writeInt(copyFlags);
                    try {
                        _data.writeLong(fileOffset);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(fileLength);
                        try {
                            _data.writeLong(sessionId);
                        } catch (Throwable th6) {
                            th = th6;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(55, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.os.IInstalld
            public boolean copyKnoxCancel(String dstPath, long sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dstPath);
                    _data.writeLong(sessionId);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getKnoxFileInfo(String dstPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dstPath);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean getKnoxScanDir(String dstPath, long sessionId, List<String> output) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dstPath);
                    _data.writeLong(sessionId);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    _reply.readStringList(output);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean deleteKnoxFile(String dstPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dstPath);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean createEncAppData(String packageName, int userId, int appId, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(appId);
                    _data.writeInt(targetSdkVersion);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean removeEncPkgDir(int userId, String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(pkgName);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean removeEncUserDir(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean migrateSdpDb(String dbPath, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dbPath);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean setEviction(int userId, boolean evict) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeBoolean(evict);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean setDualDARPolicyDir(int userId, int flags, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeString(path);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean setDualDARPolicyDirRecursively(int userId, int flags, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    _data.writeString(path);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean hasDualDARPolicy(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean hasDualDARPolicyRecursively(String path, List<String> notAppliedPaths) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    _reply.readStringList(notAppliedPaths);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean getDualDARLockstate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void compressFile(String packageDir, boolean isCompress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageDir);
                    _data.writeBoolean(isCompress);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean getCompressedStats(String packageDir, long[] sizeInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageDir);
                    _data.writeInt(sizeInfo.length);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    _reply.readLongArray(sizeInfo);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void scanApkStats(String packageDir, int scanFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageDir);
                    _data.writeInt(scanFlags);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 71;
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
            public Stub() {
                attachInterface(this, IFsveritySetupAuthToken.DESCRIPTOR);
            }

            public static IFsveritySetupAuthToken asInterface(IBinder obj) {
                if (obj == null) {
                    return null;
                }
                IInterface iin = obj.queryLocalInterface(IFsveritySetupAuthToken.DESCRIPTOR);
                if (iin != null && (iin instanceof IFsveritySetupAuthToken)) {
                    return (IFsveritySetupAuthToken) iin;
                }
                return new Proxy(obj);
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            public static String getDefaultTransactionName(int transactionCode) {
                return null;
            }

            @Override // android.os.Binder
            public String getTransactionName(int transactionCode) {
                return getDefaultTransactionName(transactionCode);
            }

            @Override // android.os.Binder
            public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                if (code == 1598968902) {
                    reply.writeString(IFsveritySetupAuthToken.DESCRIPTOR);
                    return true;
                }
                return super.onTransact(code, data, reply, flags);
            }

            private static class Proxy implements IFsveritySetupAuthToken {
                private IBinder mRemote;

                Proxy(IBinder remote) {
                    this.mRemote = remote;
                }

                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return this.mRemote;
                }

                public String getInterfaceDescriptor() {
                    return IFsveritySetupAuthToken.DESCRIPTOR;
                }
            }

            @Override // android.os.Binder
            public int getMaxTransactionId() {
                return 0;
            }
        }
    }
}
