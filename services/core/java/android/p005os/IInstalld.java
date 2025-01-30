package android.p005os;

import android.p005os.storage.CrateMetadata;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
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

        @Override // android.p005os.IInstalld
        public void cleanupInvalidPackageDirs(String str, int i, int i2) {
        }

        @Override // android.p005os.IInstalld
        public void clearAppData(String str, String str2, int i, int i2, long j) {
        }

        @Override // android.p005os.IInstalld
        public void clearAppProfiles(String str, String str2) {
        }

        @Override // android.p005os.IInstalld
        public boolean compileLayouts(String str, String str2, String str3, int i) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void compressFile(String str, boolean z) {
        }

        @Override // android.p005os.IInstalld
        public void controlDexOptBlocking(boolean z) {
        }

        @Override // android.p005os.IInstalld
        public boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean copyKnoxCancel(String str, long j) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3) {
            return 0;
        }

        @Override // android.p005os.IInstalld
        public boolean copySystemProfile(String str, int i, String str2, String str3) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public boolean createEncAppData(String str, int i, int i2, int i3) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void createOatDir(String str, String str2, String str3) {
        }

        @Override // android.p005os.IInstalld
        public boolean createProfileSnapshot(int i, String str, String str2, String str3) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void createUserData(String str, int i, int i2, int i3) {
        }

        @Override // android.p005os.IInstalld
        public boolean deleteKnoxFile(String str) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public long deleteOdex(String str, String str2, String str3, String str4) {
            return 0L;
        }

        @Override // android.p005os.IInstalld
        public void deleteReferenceProfile(String str, String str2) {
        }

        @Override // android.p005os.IInstalld
        public void destroyAppData(String str, String str2, int i, int i2, long j) {
        }

        @Override // android.p005os.IInstalld
        public void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3) {
        }

        @Override // android.p005os.IInstalld
        public void destroyAppProfiles(String str) {
        }

        @Override // android.p005os.IInstalld
        public void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) {
        }

        @Override // android.p005os.IInstalld
        public void destroyProfileSnapshot(String str, String str2) {
        }

        @Override // android.p005os.IInstalld
        public void destroyUserData(String str, int i, int i2) {
        }

        @Override // android.p005os.IInstalld
        public boolean dexopt(String str, int i, String str2, String str3, int i2, String str4, int i3, String str5, String str6, String str7, String str8, boolean z, int i4, String str9, String str10, String str11) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean dumpProfiles(int i, String str, String str2, String str3, boolean z) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void fixupAppData(String str, int i) {
        }

        @Override // android.p005os.IInstalld
        public void freeCache(String str, long j, int i) {
        }

        @Override // android.p005os.IInstalld
        public CrateMetadata[] getAppCrates(String str, String[] strArr, int i) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public long[] getAppSize(String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public boolean getCompressedStats(String str, long[] jArr) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean getDualDARLockstate() {
            return false;
        }

        @Override // android.p005os.IInstalld
        public long[] getExternalSize(String str, int i, int i2, int[] iArr) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public long[] getKnoxFileInfo(String str) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public boolean getKnoxScanDir(String str, long j, List list) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public int getOdexVisibility(String str, String str2, String str3, String str4) {
            return 0;
        }

        @Override // android.p005os.IInstalld
        public CrateMetadata[] getUserCrates(String str, int i) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public long[] getUserSize(String str, int i, int i2, int[] iArr) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public boolean hasDualDARPolicy(String str) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean hasDualDARPolicyRecursively(String str, List list) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2) {
            return null;
        }

        @Override // android.p005os.IInstalld
        public void invalidateMounts() {
        }

        @Override // android.p005os.IInstalld
        public boolean isQuotaSupported(String str) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void linkFile(String str, String str2, String str3, String str4) {
        }

        @Override // android.p005os.IInstalld
        public void linkNativeLibraryDirectory(String str, String str2, String str3, int i) {
        }

        @Override // android.p005os.IInstalld
        public int mergeProfiles(int i, String str, String str2) {
            return 0;
        }

        @Override // android.p005os.IInstalld
        public void migrateAppData(String str, String str2, int i, int i2) {
        }

        @Override // android.p005os.IInstalld
        public void migrateLegacyObbData() {
        }

        @Override // android.p005os.IInstalld
        public boolean migrateSdpDb(String str, int i) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void moveAb(String str, String str2, String str3, String str4) {
        }

        @Override // android.p005os.IInstalld
        public void moveCompleteApp(String str, String str2, String str3, int i, String str4, int i2, String str5) {
        }

        @Override // android.p005os.IInstalld
        public void onPrivateVolumeRemoved(String str) {
        }

        @Override // android.p005os.IInstalld
        public boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) {
        }

        @Override // android.p005os.IInstalld
        public boolean reconcileSecondaryDexFile(String str, String str2, int i, String[] strArr, String str3, int i2) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean removeEncPkgDir(int i, String str) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean removeEncUserDir(int i) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean removeNotTargetedPreloadApksIfNeeded() {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4) {
        }

        @Override // android.p005os.IInstalld
        public void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3) {
        }

        @Override // android.p005os.IInstalld
        public void rmPackageDir(String str, String str2) {
        }

        @Override // android.p005os.IInstalld
        public void rmdex(String str, String str2) {
        }

        @Override // android.p005os.IInstalld
        public void scanApkStats(String str, int i) {
        }

        @Override // android.p005os.IInstalld
        public void setAppQuota(String str, int i, int i2, long j) {
        }

        @Override // android.p005os.IInstalld
        public boolean setDualDARPolicyDir(int i, int i2, String str) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean setDualDARPolicyDirRecursively(int i, int i2, String str) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public boolean setEviction(int i, boolean z) {
            return false;
        }

        @Override // android.p005os.IInstalld
        public void setFirstBoot() {
        }

        @Override // android.p005os.IInstalld
        public long snapshotAppData(String str, String str2, int i, int i2, int i3) {
            return 0L;
        }

        @Override // android.p005os.IInstalld
        public void tryMountDataMirror(String str) {
        }
    }

    void cleanupInvalidPackageDirs(String str, int i, int i2);

    void clearAppData(String str, String str2, int i, int i2, long j);

    void clearAppProfiles(String str, String str2);

    boolean compileLayouts(String str, String str2, String str3, int i);

    void compressFile(String str, boolean z);

    void controlDexOptBlocking(boolean z);

    boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3);

    boolean copyKnoxCancel(String str, long j);

    int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3);

    boolean copySystemProfile(String str, int i, String str2, String str3);

    CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs);

    CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr);

    boolean createEncAppData(String str, int i, int i2, int i3);

    void createOatDir(String str, String str2, String str3);

    boolean createProfileSnapshot(int i, String str, String str2, String str3);

    void createUserData(String str, int i, int i2, int i3);

    boolean deleteKnoxFile(String str);

    long deleteOdex(String str, String str2, String str3, String str4);

    void deleteReferenceProfile(String str, String str2);

    void destroyAppData(String str, String str2, int i, int i2, long j);

    void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3);

    void destroyAppProfiles(String str);

    void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr);

    void destroyProfileSnapshot(String str, String str2);

    void destroyUserData(String str, int i, int i2);

    boolean dexopt(String str, int i, String str2, String str3, int i2, String str4, int i3, String str5, String str6, String str7, String str8, boolean z, int i4, String str9, String str10, String str11);

    boolean dumpProfiles(int i, String str, String str2, String str3, boolean z);

    void fixupAppData(String str, int i);

    void freeCache(String str, long j, int i);

    CrateMetadata[] getAppCrates(String str, String[] strArr, int i);

    long[] getAppSize(String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2);

    boolean getCompressedStats(String str, long[] jArr);

    boolean getDualDARLockstate();

    long[] getExternalSize(String str, int i, int i2, int[] iArr);

    long[] getKnoxFileInfo(String str);

    boolean getKnoxScanDir(String str, long j, List list);

    int getOdexVisibility(String str, String str2, String str3, String str4);

    CrateMetadata[] getUserCrates(String str, int i);

    long[] getUserSize(String str, int i, int i2, int[] iArr);

    boolean hasDualDARPolicy(String str);

    boolean hasDualDARPolicyRecursively(String str, List list);

    byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2);

    void invalidateMounts();

    boolean isQuotaSupported(String str);

    void linkFile(String str, String str2, String str3, String str4);

    void linkNativeLibraryDirectory(String str, String str2, String str3, int i);

    int mergeProfiles(int i, String str, String str2);

    void migrateAppData(String str, String str2, int i, int i2);

    void migrateLegacyObbData();

    boolean migrateSdpDb(String str, int i);

    void moveAb(String str, String str2, String str3, String str4);

    void moveCompleteApp(String str, String str2, String str3, int i, String str4, int i2, String str5);

    void onPrivateVolumeRemoved(String str);

    boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4);

    void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs);

    boolean reconcileSecondaryDexFile(String str, String str2, int i, String[] strArr, String str3, int i2);

    boolean removeEncPkgDir(int i, String str);

    boolean removeEncUserDir(int i);

    boolean removeNotTargetedPreloadApksIfNeeded();

    void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4);

    void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3);

    void rmPackageDir(String str, String str2);

    void rmdex(String str, String str2);

    void scanApkStats(String str, int i);

    void setAppQuota(String str, int i, int i2, long j);

    boolean setDualDARPolicyDir(int i, int i2, String str);

    boolean setDualDARPolicyDirRecursively(int i, int i2, String str);

    boolean setEviction(int i, boolean z);

    void setFirstBoot();

    long snapshotAppData(String str, String str2, int i, int i2, int i3);

    void tryMountDataMirror(String str);

    public abstract class Stub extends Binder implements IInstalld {
        public static final String DESCRIPTOR = "android.os.IInstalld";
        public static final int TRANSACTION_cleanupInvalidPackageDirs = 50;
        public static final int TRANSACTION_clearAppData = 9;
        public static final int TRANSACTION_clearAppProfiles = 26;
        public static final int TRANSACTION_compileLayouts = 21;
        public static final int TRANSACTION_compressFile = 69;
        public static final int TRANSACTION_controlDexOptBlocking = 20;
        public static final int TRANSACTION_copyKnoxAppData = 53;
        public static final int TRANSACTION_copyKnoxCancel = 55;
        public static final int TRANSACTION_copyKnoxChunks = 54;
        public static final int TRANSACTION_copySystemProfile = 25;
        public static final int TRANSACTION_createAppData = 4;
        public static final int TRANSACTION_createAppDataBatched = 5;
        public static final int TRANSACTION_createEncAppData = 59;
        public static final int TRANSACTION_createOatDir = 34;
        public static final int TRANSACTION_createProfileSnapshot = 29;
        public static final int TRANSACTION_createUserData = 1;
        public static final int TRANSACTION_deleteKnoxFile = 58;
        public static final int TRANSACTION_deleteOdex = 37;
        public static final int TRANSACTION_deleteReferenceProfile = 28;
        public static final int TRANSACTION_destroyAppData = 10;
        public static final int TRANSACTION_destroyAppDataSnapshot = 45;
        public static final int TRANSACTION_destroyAppProfiles = 27;
        public static final int TRANSACTION_destroyCeSnapshotsNotSpecified = 46;
        public static final int TRANSACTION_destroyProfileSnapshot = 30;
        public static final int TRANSACTION_destroyUserData = 2;
        public static final int TRANSACTION_dexopt = 19;
        public static final int TRANSACTION_dumpProfiles = 24;
        public static final int TRANSACTION_fixupAppData = 11;
        public static final int TRANSACTION_freeCache = 32;
        public static final int TRANSACTION_getAppCrates = 15;
        public static final int TRANSACTION_getAppSize = 12;
        public static final int TRANSACTION_getCompressedStats = 70;
        public static final int TRANSACTION_getDualDARLockstate = 68;
        public static final int TRANSACTION_getExternalSize = 14;
        public static final int TRANSACTION_getKnoxFileInfo = 56;
        public static final int TRANSACTION_getKnoxScanDir = 57;
        public static final int TRANSACTION_getOdexVisibility = 51;
        public static final int TRANSACTION_getUserCrates = 16;
        public static final int TRANSACTION_getUserSize = 13;
        public static final int TRANSACTION_hasDualDARPolicy = 66;
        public static final int TRANSACTION_hasDualDARPolicyRecursively = 67;
        public static final int TRANSACTION_hashSecondaryDexFile = 39;
        public static final int TRANSACTION_invalidateMounts = 40;
        public static final int TRANSACTION_isQuotaSupported = 41;
        public static final int TRANSACTION_linkFile = 35;
        public static final int TRANSACTION_linkNativeLibraryDirectory = 33;
        public static final int TRANSACTION_mergeProfiles = 23;
        public static final int TRANSACTION_migrateAppData = 8;
        public static final int TRANSACTION_migrateLegacyObbData = 49;
        public static final int TRANSACTION_migrateSdpDb = 62;
        public static final int TRANSACTION_moveAb = 36;
        public static final int TRANSACTION_moveCompleteApp = 18;
        public static final int TRANSACTION_onPrivateVolumeRemoved = 48;
        public static final int TRANSACTION_prepareAppProfile = 42;
        public static final int TRANSACTION_reconcileSdkData = 6;
        public static final int TRANSACTION_reconcileSecondaryDexFile = 38;
        public static final int TRANSACTION_removeEncPkgDir = 60;
        public static final int TRANSACTION_removeEncUserDir = 61;
        public static final int TRANSACTION_removeNotTargetedPreloadApksIfNeeded = 52;
        public static final int TRANSACTION_restoreAppDataSnapshot = 44;
        public static final int TRANSACTION_restoreconAppData = 7;
        public static final int TRANSACTION_rmPackageDir = 31;
        public static final int TRANSACTION_rmdex = 22;
        public static final int TRANSACTION_scanApkStats = 71;
        public static final int TRANSACTION_setAppQuota = 17;
        public static final int TRANSACTION_setDualDARPolicyDir = 64;
        public static final int TRANSACTION_setDualDARPolicyDirRecursively = 65;
        public static final int TRANSACTION_setEviction = 63;
        public static final int TRANSACTION_setFirstBoot = 3;
        public static final int TRANSACTION_snapshotAppData = 43;
        public static final int TRANSACTION_tryMountDataMirror = 47;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
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
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    CreateAppDataArgs createAppDataArgs = (CreateAppDataArgs) parcel.readTypedObject(CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    CreateAppDataResult createAppData = createAppData(createAppDataArgs);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAppData, 1);
                    return true;
                case 5:
                    CreateAppDataArgs[] createAppDataArgsArr = (CreateAppDataArgs[]) parcel.createTypedArray(CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    CreateAppDataResult[] createAppDataBatched = createAppDataBatched(createAppDataArgsArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(createAppDataBatched, 1);
                    return true;
                case 6:
                    ReconcileSdkDataArgs reconcileSdkDataArgs = (ReconcileSdkDataArgs) parcel.readTypedObject(ReconcileSdkDataArgs.CREATOR);
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
                    restoreconAppData(readString3, readString4, readInt6, readInt7, readInt8, readString5);
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
                    long[] appSize = getAppSize(readString13, createStringArray, readInt16, readInt17, readInt18, createLongArray, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(appSize);
                    return true;
                case 13:
                    String readString14 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] userSize = getUserSize(readString14, readInt19, readInt20, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(userSize);
                    return true;
                case 14:
                    String readString15 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] externalSize = getExternalSize(readString15, readInt21, readInt22, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(externalSize);
                    return true;
                case 15:
                    String readString16 = parcel.readString();
                    String[] createStringArray3 = parcel.createStringArray();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CrateMetadata[] appCrates = getAppCrates(readString16, createStringArray3, readInt23);
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
                    moveCompleteApp(readString19, readString20, readString21, readInt27, readString22, readInt28, readString23);
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
                    boolean dexopt = dexopt(readString24, readInt29, readString25, readString26, readInt30, readString27, readInt31, readString28, readString29, readString30, readString31, readBoolean, readInt32, readString32, readString33, readString34);
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
                    String readString37 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean compileLayouts = compileLayouts(readString35, readString36, readString37, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(compileLayouts);
                    return true;
                case 22:
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmdex(readString38, readString39);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt34 = parcel.readInt();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mergeProfiles = mergeProfiles(readInt34, readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeInt(mergeProfiles);
                    return true;
                case 24:
                    int readInt35 = parcel.readInt();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dumpProfiles = dumpProfiles(readInt35, readString42, readString43, readString44, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dumpProfiles);
                    return true;
                case 25:
                    String readString45 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    String readString46 = parcel.readString();
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean copySystemProfile = copySystemProfile(readString45, readInt36, readString46, readString47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copySystemProfile);
                    return true;
                case 26:
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppProfiles(readString48, readString49);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyAppProfiles(readString50);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteReferenceProfile(readString51, readString52);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt37 = parcel.readInt();
                    String readString53 = parcel.readString();
                    String readString54 = parcel.readString();
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean createProfileSnapshot = createProfileSnapshot(readInt37, readString53, readString54, readString55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createProfileSnapshot);
                    return true;
                case 30:
                    String readString56 = parcel.readString();
                    String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyProfileSnapshot(readString56, readString57);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmPackageDir(readString58, readString59);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String readString60 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    freeCache(readString60, readLong4, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString61 = parcel.readString();
                    String readString62 = parcel.readString();
                    String readString63 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    linkNativeLibraryDirectory(readString61, readString62, readString63, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString64 = parcel.readString();
                    String readString65 = parcel.readString();
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createOatDir(readString64, readString65, readString66);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String readString67 = parcel.readString();
                    String readString68 = parcel.readString();
                    String readString69 = parcel.readString();
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    linkFile(readString67, readString68, readString69, readString70);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String readString71 = parcel.readString();
                    String readString72 = parcel.readString();
                    String readString73 = parcel.readString();
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveAb(readString71, readString72, readString73, readString74);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString75 = parcel.readString();
                    String readString76 = parcel.readString();
                    String readString77 = parcel.readString();
                    String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long deleteOdex = deleteOdex(readString75, readString76, readString77, readString78);
                    parcel2.writeNoException();
                    parcel2.writeLong(deleteOdex);
                    return true;
                case 38:
                    String readString79 = parcel.readString();
                    String readString80 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    String[] createStringArray4 = parcel.createStringArray();
                    String readString81 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reconcileSecondaryDexFile = reconcileSecondaryDexFile(readString79, readString80, readInt40, createStringArray4, readString81, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reconcileSecondaryDexFile);
                    return true;
                case 39:
                    String readString82 = parcel.readString();
                    String readString83 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    String readString84 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] hashSecondaryDexFile = hashSecondaryDexFile(readString82, readString83, readInt42, readString84, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(hashSecondaryDexFile);
                    return true;
                case 40:
                    invalidateMounts();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString85 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isQuotaSupported = isQuotaSupported(readString85);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuotaSupported);
                    return true;
                case 42:
                    String readString86 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    String readString87 = parcel.readString();
                    String readString88 = parcel.readString();
                    String readString89 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean prepareAppProfile = prepareAppProfile(readString86, readInt44, readInt45, readString87, readString88, readString89);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(prepareAppProfile);
                    return true;
                case 43:
                    String readString90 = parcel.readString();
                    String readString91 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long snapshotAppData = snapshotAppData(readString90, readString91, readInt46, readInt47, readInt48);
                    parcel2.writeNoException();
                    parcel2.writeLong(snapshotAppData);
                    return true;
                case 44:
                    String readString92 = parcel.readString();
                    String readString93 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    String readString94 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAppDataSnapshot(readString92, readString93, readInt49, readString94, readInt50, readInt51, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String readString95 = parcel.readString();
                    String readString96 = parcel.readString();
                    int readInt53 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyAppDataSnapshot(readString95, readString96, readInt53, readLong5, readInt54, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String readString97 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    destroyCeSnapshotsNotSpecified(readString97, readInt56, createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    String readString98 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    tryMountDataMirror(readString98);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String readString99 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPrivateVolumeRemoved(readString99);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    migrateLegacyObbData();
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString100 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupInvalidPackageDirs(readString100, readInt57, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    String readString101 = parcel.readString();
                    String readString102 = parcel.readString();
                    String readString103 = parcel.readString();
                    String readString104 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int odexVisibility = getOdexVisibility(readString101, readString102, readString103, readString104);
                    parcel2.writeNoException();
                    parcel2.writeInt(odexVisibility);
                    return true;
                case 52:
                    boolean removeNotTargetedPreloadApksIfNeeded = removeNotTargetedPreloadApksIfNeeded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeNotTargetedPreloadApksIfNeeded);
                    return true;
                case 53:
                    String readString105 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    String readString106 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean copyKnoxAppData = copyKnoxAppData(readString105, readInt59, readString106, readInt60, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copyKnoxAppData);
                    return true;
                case 54:
                    String readString107 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    String readString108 = parcel.readString();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int copyKnoxChunks = copyKnoxChunks(readString107, readInt62, readString108, readInt63, readInt64, readLong6, readLong7, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyKnoxChunks);
                    return true;
                case 55:
                    String readString109 = parcel.readString();
                    long readLong9 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean copyKnoxCancel = copyKnoxCancel(readString109, readLong9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copyKnoxCancel);
                    return true;
                case 56:
                    String readString110 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long[] knoxFileInfo = getKnoxFileInfo(readString110);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(knoxFileInfo);
                    return true;
                case 57:
                    String readString111 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    ArrayList arrayList = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean knoxScanDir = getKnoxScanDir(readString111, readLong10, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxScanDir);
                    parcel2.writeStringList(arrayList);
                    return true;
                case 58:
                    String readString112 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteKnoxFile = deleteKnoxFile(readString112);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteKnoxFile);
                    return true;
                case 59:
                    String readString113 = parcel.readString();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean createEncAppData = createEncAppData(readString113, readInt65, readInt66, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createEncAppData);
                    return true;
                case 60:
                    int readInt68 = parcel.readInt();
                    String readString114 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeEncPkgDir = removeEncPkgDir(readInt68, readString114);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeEncPkgDir);
                    return true;
                case 61:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeEncUserDir = removeEncUserDir(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeEncUserDir);
                    return true;
                case 62:
                    String readString115 = parcel.readString();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean migrateSdpDb = migrateSdpDb(readString115, readInt70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(migrateSdpDb);
                    return true;
                case 63:
                    int readInt71 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean eviction = setEviction(readInt71, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(eviction);
                    return true;
                case 64:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    String readString116 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyDir = setDualDARPolicyDir(readInt72, readInt73, readString116);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyDir);
                    return true;
                case 65:
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    String readString117 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyDirRecursively = setDualDARPolicyDirRecursively(readInt74, readInt75, readString117);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyDirRecursively);
                    return true;
                case 66:
                    String readString118 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDualDARPolicy = hasDualDARPolicy(readString118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDualDARPolicy);
                    return true;
                case 67:
                    String readString119 = parcel.readString();
                    ArrayList arrayList2 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    boolean hasDualDARPolicyRecursively = hasDualDARPolicyRecursively(readString119, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDualDARPolicyRecursively);
                    parcel2.writeStringList(arrayList2);
                    return true;
                case 68:
                    boolean dualDARLockstate = getDualDARLockstate();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARLockstate);
                    return true;
                case 69:
                    String readString120 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    compressFile(readString120, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    String readString121 = parcel.readString();
                    int readInt76 = parcel.readInt();
                    long[] jArr = readInt76 < 0 ? null : new long[readInt76];
                    parcel.enforceNoDataAvail();
                    boolean compressedStats = getCompressedStats(readString121, jArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(compressedStats);
                    parcel2.writeLongArray(jArr);
                    return true;
                case 71:
                    String readString122 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scanApkStats(readString122, readInt77);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        public class Proxy implements IInstalld {
            public IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.p005os.IInstalld
            public void createUserData(String str, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void destroyUserData(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void setFirstBoot() {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(createAppDataArgs, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CreateAppDataResult) obtain2.readTypedObject(CreateAppDataResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(createAppDataArgsArr, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CreateAppDataResult[]) obtain2.createTypedArray(CreateAppDataResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void migrateAppData(String str, String str2, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void clearAppData(String str, String str2, int i, int i2, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void destroyAppData(String str, String str2, int i, int i2, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void fixupAppData(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public long[] getAppSize(String str, String[] strArr, int i, int i2, int i3, long[] jArr, String[] strArr2) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public long[] getUserSize(String str, int i, int i2, int[] iArr) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public long[] getExternalSize(String str, int i, int i2, int[] iArr) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public CrateMetadata[] getAppCrates(String str, String[] strArr, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public CrateMetadata[] getUserCrates(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void setAppQuota(String str, int i, int i2, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void moveCompleteApp(String str, String str2, String str3, int i, String str4, int i2, String str5) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public boolean dexopt(String str, int i, String str2, String str3, int i2, String str4, int i3, String str5, String str6, String str7, String str8, boolean z, int i4, String str9, String str10, String str11) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void controlDexOptBlocking(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public boolean compileLayouts(String str, String str2, String str3, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void rmdex(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public int mergeProfiles(int i, String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean dumpProfiles(int i, String str, String str2, String str3, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean copySystemProfile(String str, int i, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void clearAppProfiles(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void destroyAppProfiles(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void deleteReferenceProfile(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean createProfileSnapshot(int i, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void destroyProfileSnapshot(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void rmPackageDir(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void freeCache(String str, long j, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void linkNativeLibraryDirectory(String str, String str2, String str3, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void createOatDir(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void linkFile(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void moveAb(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public long deleteOdex(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean reconcileSecondaryDexFile(String str, String str2, int i, String[] strArr, String str3, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void invalidateMounts() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean isQuotaSupported(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean prepareAppProfile(String str, int i, int i2, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public long snapshotAppData(String str, String str2, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void restoreAppDataSnapshot(String str, String str2, int i, String str3, int i2, int i3, int i4) {
                Parcel obtain = Parcel.obtain(asBinder());
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
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void destroyAppDataSnapshot(String str, String str2, int i, long j, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void destroyCeSnapshotsNotSpecified(String str, int i, int[] iArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void tryMountDataMirror(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public void onPrivateVolumeRemoved(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void migrateLegacyObbData() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void cleanupInvalidPackageDirs(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public int getOdexVisibility(String str, String str2, String str3, String str4) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean removeNotTargetedPreloadApksIfNeeded() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public int copyKnoxChunks(String str, int i, String str2, int i2, int i3, long j, long j2, long j3) {
                Parcel obtain = Parcel.obtain(asBinder());
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
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean copyKnoxCancel(String str, long j) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public long[] getKnoxFileInfo(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean getKnoxScanDir(String str, long j, List list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readStringList(list);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean deleteKnoxFile(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean createEncAppData(String str, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean removeEncPkgDir(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean removeEncUserDir(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean migrateSdpDb(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean setEviction(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean setDualDARPolicyDir(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean setDualDARPolicyDirRecursively(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
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

            @Override // android.p005os.IInstalld
            public boolean hasDualDARPolicy(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean hasDualDARPolicyRecursively(String str, List list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readStringList(list);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean getDualDARLockstate() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void compressFile(String str, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public boolean getCompressedStats(String str, long[] jArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(jArr.length);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readLongArray(jArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.p005os.IInstalld
            public void scanApkStats(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
