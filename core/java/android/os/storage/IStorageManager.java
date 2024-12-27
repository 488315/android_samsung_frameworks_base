package android.os.storage;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.pm.IPackageMoveObserver;
import android.content.res.ObbInfo;
import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IVoldTaskListener;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.provider.Telephony;

import com.android.internal.os.AppFuseMount;

import com.samsung.android.media.AudioParameter;

public interface IStorageManager extends IInterface {
    void abortChanges(String str, boolean z) throws RemoteException;

    void abortIdleMaintenance() throws RemoteException;

    void allocateBytes(String str, long j, int i, String str2) throws RemoteException;

    void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void commitChanges() throws RemoteException;

    boolean cpFileAtData(String str, String str2) throws RemoteException;

    int createPassStorage() throws RemoteException;

    int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z)
            throws RemoteException;

    void createUserStorageKeys(int i, boolean z) throws RemoteException;

    int destroyPassStorage() throws RemoteException;

    int destroySecureContainer(String str, boolean z) throws RemoteException;

    void destroyUserStorage(String str, int i, int i2) throws RemoteException;

    void destroyUserStorageKeys(int i) throws RemoteException;

    void disableAppDataIsolation(String str, int i, int i2) throws RemoteException;

    int encryptExternalStorage(boolean z) throws RemoteException;

    int finalizeSecureContainer(String str) throws RemoteException;

    void finishMediaUpdate() throws RemoteException;

    int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException;

    void fixupAppDir(String str) throws RemoteException;

    void forgetAllVolumes() throws RemoteException;

    void forgetVolume(String str) throws RemoteException;

    void format(String str) throws RemoteException;

    void formatBySecApp(String str, String str2) throws RemoteException;

    void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    long getAllocatableBytes(String str, int i, String str2) throws RemoteException;

    long getCacheQuotaBytes(String str, int i) throws RemoteException;

    long getCacheSizeBytes(String str, int i) throws RemoteException;

    String getCloudMediaProvider() throws RemoteException;

    DiskInfo[] getDisks() throws RemoteException;

    int getExternalStorageMountMode(int i, String str) throws RemoteException;

    long getInternalStorageBlockDeviceSize() throws RemoteException;

    int getInternalStorageRemainingLifetime() throws RemoteException;

    PendingIntent getManageSpaceActivityIntent(String str, int i) throws RemoteException;

    String getMountedObbPath(String str) throws RemoteException;

    String getPassStorage() throws RemoteException;

    String getPrimaryStorageUuid() throws RemoteException;

    String getSecureContainerFilesystemPath(String str) throws RemoteException;

    String[] getSecureContainerList() throws RemoteException;

    String getSecureContainerPath(String str) throws RemoteException;

    long getUsedF2fsFileNode() throws RemoteException;

    int getUsedSpaceSecureContainer(String str) throws RemoteException;

    StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException;

    VolumeRecord[] getVolumeRecords(int i) throws RemoteException;

    String getVolumeState(String str) throws RemoteException;

    VolumeInfo[] getVolumes(int i) throws RemoteException;

    boolean isAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException;

    boolean isCeStorageUnlocked(int i) throws RemoteException;

    boolean isObbMounted(String str) throws RemoteException;

    boolean isPassUnlocked() throws RemoteException;

    boolean isSecureContainerMounted(String str) throws RemoteException;

    boolean isSensitive(String str) throws RemoteException;

    long lastMaintenance() throws RemoteException;

    void lockCeStorage(int i) throws RemoteException;

    int lockPassStorage() throws RemoteException;

    void mkdirs(String str, String str2) throws RemoteException;

    void mount(String str) throws RemoteException;

    void mountBySecApp(String str, String str2) throws RemoteException;

    void mountObb(
            String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo)
            throws RemoteException;

    AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException;

    boolean mountSdpMediaStorageCmd(int i) throws RemoteException;

    int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException;

    int mountVolume(String str) throws RemoteException;

    boolean mvFileAtData(String str, String str2) throws RemoteException;

    boolean needsCheckpoint() throws RemoteException;

    void notifyAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException;

    void notifyAppIoResumed(String str, int i, int i2, int i3) throws RemoteException;

    ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws RemoteException;

    void partitionMixed(String str, int i) throws RemoteException;

    void partitionPrivate(String str) throws RemoteException;

    void partitionPublic(String str) throws RemoteException;

    void prepareUserStorage(String str, int i, int i2) throws RemoteException;

    void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    int renameSecureContainer(String str, String str2) throws RemoteException;

    int reserveDataBlocks(long j) throws RemoteException;

    int resizeSecureContainer(String str, int i, String str2) throws RemoteException;

    void runIdleMaintenance() throws RemoteException;

    void runMaintenance() throws RemoteException;

    int semGetExternalSdCardHealthState() throws RemoteException;

    String semGetExternalSdCardId() throws RemoteException;

    void setCeStorageProtection(int i, byte[] bArr) throws RemoteException;

    void setCloudMediaProvider(String str) throws RemoteException;

    void setDebugFlags(int i, int i2) throws RemoteException;

    boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException;

    void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver)
            throws RemoteException;

    boolean setSdpPolicyCmd(int i) throws RemoteException;

    boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException;

    boolean setSensitive(int i, String str) throws RemoteException;

    void setVolumeNickname(String str, String str2) throws RemoteException;

    void setVolumeUserFlags(String str, int i, int i2) throws RemoteException;

    boolean shrinkDataDdp(long j) throws RemoteException;

    void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException;

    void startCheckpoint(int i) throws RemoteException;

    boolean supportsCheckpoint() throws RemoteException;

    int trimSecureContainer(String str, int i, String str2) throws RemoteException;

    void unlockCeStorage(int i, byte[] bArr) throws RemoteException;

    int unlockPassStorage() throws RemoteException;

    void unmount(String str) throws RemoteException;

    void unmountBySecApp(String str, String str2) throws RemoteException;

    void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i)
            throws RemoteException;

    int unmountSecureContainer(String str, boolean z) throws RemoteException;

    void unmountVolume(String str, boolean z, boolean z2) throws RemoteException;

    void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    void waitForAsecScan() throws RemoteException;

    public static class Default implements IStorageManager {
        @Override // android.os.storage.IStorageManager
        public void registerListener(IStorageEventListener listener) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void unregisterListener(IStorageEventListener listener) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public int mountVolume(String mountPoint) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void unmountVolume(String mountPoint, boolean force, boolean removeEncryption)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void shutdown(IStorageShutdownObserver observer) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void mountObb(
                String rawPath,
                String canonicalPath,
                IObbActionListener token,
                int nonce,
                ObbInfo obbInfo)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void unmountObb(String rawPath, boolean force, IObbActionListener token, int nonce)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public boolean isObbMounted(String rawPath) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public String getMountedObbPath(String rawPath) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public StorageVolume[] getVolumeList(int userId, String callingPackage, int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void mkdirs(String callingPkg, String path) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public long lastMaintenance() throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public void runMaintenance() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public DiskInfo[] getDisks() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public VolumeInfo[] getVolumes(int flags) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public VolumeRecord[] getVolumeRecords(int flags) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void mount(String volId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void unmount(String volId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void format(String volId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void partitionPublic(String diskId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void partitionPrivate(String diskId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void partitionMixed(String diskId, int ratio) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void setVolumeNickname(String fsUuid, String nickname) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void setVolumeUserFlags(String fsUuid, int flags, int mask) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void forgetVolume(String fsUuid) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void forgetAllVolumes() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public String getPrimaryStorageUuid() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void setPrimaryStorageUuid(String volumeUuid, IPackageMoveObserver callback)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void setDebugFlags(int flags, int mask) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void createUserStorageKeys(int userId, boolean ephemeral) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void destroyUserStorageKeys(int userId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void unlockCeStorage(int userId, byte[] secret) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void lockCeStorage(int userId) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public boolean isCeStorageUnlocked(int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void prepareUserStorage(String volumeUuid, int userId, int flags)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void destroyUserStorage(String volumeUuid, int userId, int flags)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void setCeStorageProtection(int userId, byte[] secret) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void fstrim(int flags, IVoldTaskListener listener) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public ParcelFileDescriptor openProxyFileDescriptor(int mountPointId, int fileId, int mode)
                throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public long getCacheQuotaBytes(String volumeUuid, int uid) throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public long getCacheSizeBytes(String volumeUuid, int uid) throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public long getAllocatableBytes(String volumeUuid, int flags, String callingPackage)
                throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public void allocateBytes(String volumeUuid, long bytes, int flags, String callingPackage)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void runIdleMaintenance() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void abortIdleMaintenance() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void commitChanges() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public boolean supportsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void startCheckpoint(int numTries) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public boolean needsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void abortChanges(String message, boolean retry) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void fixupAppDir(String path) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void disableAppDataIsolation(String pkgName, int pid, int userId)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public PendingIntent getManageSpaceActivityIntent(String packageName, int requestCode)
                throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void notifyAppIoBlocked(String volumeUuid, int uid, int tid, int reason)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void notifyAppIoResumed(String volumeUuid, int uid, int tid, int reason)
                throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public int getExternalStorageMountMode(int uid, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isAppIoBlocked(String volumeUuid, int uid, int tid, int reason)
                throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void setCloudMediaProvider(String authority) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public String getCloudMediaProvider() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public long getInternalStorageBlockDeviceSize() throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public int getInternalStorageRemainingLifetime() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public boolean setSensitive(int engineId, String path) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isSensitive(String path) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean mountSdpMediaStorageCmd(int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean setSdpPolicyCmd(int userId) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean setSdpPolicyToPathCmd(int userId, String path) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean setDualDARPolicyCmd(int userId, int flags) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public int semGetExternalSdCardHealthState() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public String semGetExternalSdCardId() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public long getUsedF2fsFileNode() throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public boolean mvFileAtData(String from, String to) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean cpFileAtData(String from, String to) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void mountBySecApp(String volId, String callerPackage) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void unmountBySecApp(String volId, String callerPackage) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public void formatBySecApp(String volId, String callerPackage) throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public int encryptExternalStorage(boolean state) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public String getVolumeState(String mountPoint) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public int createSecureContainer(
                String id, int sizeMb, String fstype, String key, int ownerUid, boolean external)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int finalizeSecureContainer(String id) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int destroySecureContainer(String id, boolean force) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int mountSecureContainer(String id, String key, int ownerUid, boolean readOnly)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int unmountSecureContainer(String id, boolean force) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isSecureContainerMounted(String id) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public int renameSecureContainer(String oldId, String newId) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public String getSecureContainerPath(String id) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String[] getSecureContainerList() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void finishMediaUpdate() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public String getSecureContainerFilesystemPath(String cid) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public int fixPermissionsSecureContainer(String id, int gid, String filename)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int resizeSecureContainer(String id, int sizeMb, String key) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void waitForAsecScan() throws RemoteException {}

        @Override // android.os.storage.IStorageManager
        public int trimSecureContainer(String id, int sizeMb, String key) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int getUsedSpaceSecureContainer(String id) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int createPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int destroyPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int unlockPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int lockPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public String getPassStorage() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isPassUnlocked() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean shrinkDataDdp(long superUsedSectors) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public int reserveDataBlocks(long superUsedSectors) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IStorageManager {
        public static final String DESCRIPTOR = "android.os.storage.IStorageManager";
        static final int TRANSACTION_abortChanges = 88;
        static final int TRANSACTION_abortIdleMaintenance = 81;
        static final int TRANSACTION_allocateBytes = 79;
        static final int TRANSACTION_benchmark = 60;
        static final int TRANSACTION_commitChanges = 84;
        static final int TRANSACTION_cpFileAtData = 155;
        static final int TRANSACTION_createPassStorage = 223;
        static final int TRANSACTION_createSecureContainer = 203;
        static final int TRANSACTION_createUserStorageKeys = 62;
        static final int TRANSACTION_destroyPassStorage = 224;
        static final int TRANSACTION_destroySecureContainer = 205;
        static final int TRANSACTION_destroyUserStorage = 68;
        static final int TRANSACTION_destroyUserStorageKeys = 63;
        static final int TRANSACTION_disableAppDataIsolation = 91;
        static final int TRANSACTION_encryptExternalStorage = 159;
        static final int TRANSACTION_finalizeSecureContainer = 204;
        static final int TRANSACTION_finishMediaUpdate = 212;
        static final int TRANSACTION_fixPermissionsSecureContainer = 214;
        static final int TRANSACTION_fixupAppDir = 90;
        static final int TRANSACTION_forgetAllVolumes = 57;
        static final int TRANSACTION_forgetVolume = 56;
        static final int TRANSACTION_format = 50;
        static final int TRANSACTION_formatBySecApp = 158;
        static final int TRANSACTION_fstrim = 73;
        static final int TRANSACTION_getAllocatableBytes = 78;
        static final int TRANSACTION_getCacheQuotaBytes = 76;
        static final int TRANSACTION_getCacheSizeBytes = 77;
        static final int TRANSACTION_getCloudMediaProvider = 98;
        static final int TRANSACTION_getDisks = 45;
        static final int TRANSACTION_getExternalStorageMountMode = 95;
        static final int TRANSACTION_getInternalStorageBlockDeviceSize = 99;
        static final int TRANSACTION_getInternalStorageRemainingLifetime = 100;
        static final int TRANSACTION_getManageSpaceActivityIntent = 92;
        static final int TRANSACTION_getMountedObbPath = 25;
        static final int TRANSACTION_getPassStorage = 227;
        static final int TRANSACTION_getPrimaryStorageUuid = 58;
        static final int TRANSACTION_getSecureContainerFilesystemPath = 213;
        static final int TRANSACTION_getSecureContainerList = 211;
        static final int TRANSACTION_getSecureContainerPath = 210;
        static final int TRANSACTION_getUsedF2fsFileNode = 153;
        static final int TRANSACTION_getUsedSpaceSecureContainer = 218;
        static final int TRANSACTION_getVolumeList = 30;
        static final int TRANSACTION_getVolumeRecords = 47;
        static final int TRANSACTION_getVolumeState = 202;
        static final int TRANSACTION_getVolumes = 46;
        static final int TRANSACTION_isAppIoBlocked = 96;
        static final int TRANSACTION_isCeStorageUnlocked = 66;
        static final int TRANSACTION_isObbMounted = 24;
        static final int TRANSACTION_isPassUnlocked = 229;
        static final int TRANSACTION_isSecureContainerMounted = 208;
        static final int TRANSACTION_isSensitive = 112;
        static final int TRANSACTION_lastMaintenance = 42;
        static final int TRANSACTION_lockCeStorage = 65;
        static final int TRANSACTION_lockPassStorage = 226;
        static final int TRANSACTION_mkdirs = 35;
        static final int TRANSACTION_mount = 48;
        static final int TRANSACTION_mountBySecApp = 156;
        static final int TRANSACTION_mountObb = 22;
        static final int TRANSACTION_mountProxyFileDescriptorBridge = 74;
        static final int TRANSACTION_mountSdpMediaStorageCmd = 113;
        static final int TRANSACTION_mountSecureContainer = 206;
        static final int TRANSACTION_mountVolume = 6;
        static final int TRANSACTION_mvFileAtData = 154;
        static final int TRANSACTION_needsCheckpoint = 87;
        static final int TRANSACTION_notifyAppIoBlocked = 93;
        static final int TRANSACTION_notifyAppIoResumed = 94;
        static final int TRANSACTION_openProxyFileDescriptor = 75;
        static final int TRANSACTION_partitionMixed = 53;
        static final int TRANSACTION_partitionPrivate = 52;
        static final int TRANSACTION_partitionPublic = 51;
        static final int TRANSACTION_prepareUserStorage = 67;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_renameSecureContainer = 209;
        static final int TRANSACTION_reserveDataBlocks = 262;
        static final int TRANSACTION_resizeSecureContainer = 215;
        static final int TRANSACTION_runIdleMaintenance = 80;
        static final int TRANSACTION_runMaintenance = 43;
        static final int TRANSACTION_semGetExternalSdCardHealthState = 151;
        static final int TRANSACTION_semGetExternalSdCardId = 152;
        static final int TRANSACTION_setCeStorageProtection = 71;
        static final int TRANSACTION_setCloudMediaProvider = 97;
        static final int TRANSACTION_setDebugFlags = 61;
        static final int TRANSACTION_setDualDARPolicyCmd = 116;
        static final int TRANSACTION_setPrimaryStorageUuid = 59;
        static final int TRANSACTION_setSdpPolicyCmd = 114;
        static final int TRANSACTION_setSdpPolicyToPathCmd = 115;
        static final int TRANSACTION_setSensitive = 111;
        static final int TRANSACTION_setVolumeNickname = 54;
        static final int TRANSACTION_setVolumeUserFlags = 55;
        static final int TRANSACTION_shrinkDataDdp = 261;
        static final int TRANSACTION_shutdown = 20;
        static final int TRANSACTION_startCheckpoint = 86;
        static final int TRANSACTION_supportsCheckpoint = 85;
        static final int TRANSACTION_trimSecureContainer = 217;
        static final int TRANSACTION_unlockCeStorage = 64;
        static final int TRANSACTION_unlockPassStorage = 225;
        static final int TRANSACTION_unmount = 49;
        static final int TRANSACTION_unmountBySecApp = 157;
        static final int TRANSACTION_unmountObb = 23;
        static final int TRANSACTION_unmountSecureContainer = 207;
        static final int TRANSACTION_unmountVolume = 7;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_waitForAsecScan = 216;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IStorageManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IStorageManager)) {
                return (IStorageManager) iin;
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
                    return "registerListener";
                case 2:
                    return "unregisterListener";
                case 6:
                    return "mountVolume";
                case 7:
                    return "unmountVolume";
                case 20:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 22:
                    return "mountObb";
                case 23:
                    return "unmountObb";
                case 24:
                    return "isObbMounted";
                case 25:
                    return "getMountedObbPath";
                case 30:
                    return "getVolumeList";
                case 35:
                    return "mkdirs";
                case 42:
                    return "lastMaintenance";
                case 43:
                    return "runMaintenance";
                case 45:
                    return "getDisks";
                case 46:
                    return "getVolumes";
                case 47:
                    return "getVolumeRecords";
                case 48:
                    return AudioParameter.VALUE_MOUNT;
                case 49:
                    return AudioParameter.VALUE_UNMOUNT;
                case 50:
                    return Telephony.CellBroadcasts.MESSAGE_FORMAT;
                case 51:
                    return "partitionPublic";
                case 52:
                    return "partitionPrivate";
                case 53:
                    return "partitionMixed";
                case 54:
                    return "setVolumeNickname";
                case 55:
                    return "setVolumeUserFlags";
                case 56:
                    return "forgetVolume";
                case 57:
                    return "forgetAllVolumes";
                case 58:
                    return "getPrimaryStorageUuid";
                case 59:
                    return "setPrimaryStorageUuid";
                case 60:
                    return "benchmark";
                case 61:
                    return "setDebugFlags";
                case 62:
                    return "createUserStorageKeys";
                case 63:
                    return "destroyUserStorageKeys";
                case 64:
                    return "unlockCeStorage";
                case 65:
                    return "lockCeStorage";
                case 66:
                    return "isCeStorageUnlocked";
                case 67:
                    return "prepareUserStorage";
                case 68:
                    return "destroyUserStorage";
                case 71:
                    return "setCeStorageProtection";
                case 73:
                    return "fstrim";
                case 74:
                    return "mountProxyFileDescriptorBridge";
                case 75:
                    return "openProxyFileDescriptor";
                case 76:
                    return "getCacheQuotaBytes";
                case 77:
                    return "getCacheSizeBytes";
                case 78:
                    return "getAllocatableBytes";
                case 79:
                    return "allocateBytes";
                case 80:
                    return "runIdleMaintenance";
                case 81:
                    return "abortIdleMaintenance";
                case 84:
                    return "commitChanges";
                case 85:
                    return "supportsCheckpoint";
                case 86:
                    return "startCheckpoint";
                case 87:
                    return "needsCheckpoint";
                case 88:
                    return "abortChanges";
                case 90:
                    return "fixupAppDir";
                case 91:
                    return "disableAppDataIsolation";
                case 92:
                    return "getManageSpaceActivityIntent";
                case 93:
                    return "notifyAppIoBlocked";
                case 94:
                    return "notifyAppIoResumed";
                case 95:
                    return "getExternalStorageMountMode";
                case 96:
                    return "isAppIoBlocked";
                case 97:
                    return "setCloudMediaProvider";
                case 98:
                    return "getCloudMediaProvider";
                case 99:
                    return "getInternalStorageBlockDeviceSize";
                case 100:
                    return "getInternalStorageRemainingLifetime";
                case 111:
                    return "setSensitive";
                case 112:
                    return "isSensitive";
                case 113:
                    return "mountSdpMediaStorageCmd";
                case 114:
                    return "setSdpPolicyCmd";
                case 115:
                    return "setSdpPolicyToPathCmd";
                case 116:
                    return "setDualDARPolicyCmd";
                case 151:
                    return "semGetExternalSdCardHealthState";
                case 152:
                    return "semGetExternalSdCardId";
                case 153:
                    return "getUsedF2fsFileNode";
                case 154:
                    return "mvFileAtData";
                case 155:
                    return "cpFileAtData";
                case 156:
                    return "mountBySecApp";
                case 157:
                    return "unmountBySecApp";
                case 158:
                    return "formatBySecApp";
                case 159:
                    return "encryptExternalStorage";
                case 202:
                    return "getVolumeState";
                case 203:
                    return "createSecureContainer";
                case 204:
                    return "finalizeSecureContainer";
                case 205:
                    return "destroySecureContainer";
                case 206:
                    return "mountSecureContainer";
                case 207:
                    return "unmountSecureContainer";
                case 208:
                    return "isSecureContainerMounted";
                case 209:
                    return "renameSecureContainer";
                case 210:
                    return "getSecureContainerPath";
                case 211:
                    return "getSecureContainerList";
                case 212:
                    return "finishMediaUpdate";
                case 213:
                    return "getSecureContainerFilesystemPath";
                case 214:
                    return "fixPermissionsSecureContainer";
                case 215:
                    return "resizeSecureContainer";
                case 216:
                    return "waitForAsecScan";
                case 217:
                    return "trimSecureContainer";
                case 218:
                    return "getUsedSpaceSecureContainer";
                case 223:
                    return "createPassStorage";
                case 224:
                    return "destroyPassStorage";
                case 225:
                    return "unlockPassStorage";
                case 226:
                    return "lockPassStorage";
                case 227:
                    return "getPassStorage";
                case 229:
                    return "isPassUnlocked";
                case 261:
                    return "shrinkDataDdp";
                case 262:
                    return "reserveDataBlocks";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IStorageEventListener _arg0 =
                            IStorageEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerListener(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    IStorageEventListener _arg02 =
                            IStorageEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 6:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    int _result = mountVolume(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 7:
                    String _arg04 = data.readString();
                    boolean _arg1 = data.readBoolean();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    unmountVolume(_arg04, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 20:
                    IStorageShutdownObserver _arg05 =
                            IStorageShutdownObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    shutdown(_arg05);
                    reply.writeNoException();
                    return true;
                case 22:
                    String _arg06 = data.readString();
                    String _arg12 = data.readString();
                    IObbActionListener _arg22 =
                            IObbActionListener.Stub.asInterface(data.readStrongBinder());
                    int _arg3 = data.readInt();
                    ObbInfo _arg4 = (ObbInfo) data.readTypedObject(ObbInfo.CREATOR);
                    data.enforceNoDataAvail();
                    mountObb(_arg06, _arg12, _arg22, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 23:
                    String _arg07 = data.readString();
                    boolean _arg13 = data.readBoolean();
                    IObbActionListener _arg23 =
                            IObbActionListener.Stub.asInterface(data.readStrongBinder());
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    unmountObb(_arg07, _arg13, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 24:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = isObbMounted(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 25:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    String _result3 = getMountedObbPath(_arg09);
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 30:
                    int _arg010 = data.readInt();
                    String _arg14 = data.readString();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    StorageVolume[] _result4 = getVolumeList(_arg010, _arg14, _arg24);
                    reply.writeNoException();
                    reply.writeTypedArray(_result4, 1);
                    return true;
                case 35:
                    String _arg011 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    mkdirs(_arg011, _arg15);
                    reply.writeNoException();
                    return true;
                case 42:
                    long _result5 = lastMaintenance();
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 43:
                    runMaintenance();
                    reply.writeNoException();
                    return true;
                case 45:
                    DiskInfo[] _result6 = getDisks();
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                case 46:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    VolumeInfo[] _result7 = getVolumes(_arg012);
                    reply.writeNoException();
                    reply.writeTypedArray(_result7, 1);
                    return true;
                case 47:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    VolumeRecord[] _result8 = getVolumeRecords(_arg013);
                    reply.writeNoException();
                    reply.writeTypedArray(_result8, 1);
                    return true;
                case 48:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    mount(_arg014);
                    reply.writeNoException();
                    return true;
                case 49:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    unmount(_arg015);
                    reply.writeNoException();
                    return true;
                case 50:
                    String _arg016 = data.readString();
                    data.enforceNoDataAvail();
                    format(_arg016);
                    reply.writeNoException();
                    return true;
                case 51:
                    String _arg017 = data.readString();
                    data.enforceNoDataAvail();
                    partitionPublic(_arg017);
                    reply.writeNoException();
                    return true;
                case 52:
                    String _arg018 = data.readString();
                    data.enforceNoDataAvail();
                    partitionPrivate(_arg018);
                    reply.writeNoException();
                    return true;
                case 53:
                    String _arg019 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    partitionMixed(_arg019, _arg16);
                    reply.writeNoException();
                    return true;
                case 54:
                    String _arg020 = data.readString();
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    setVolumeNickname(_arg020, _arg17);
                    reply.writeNoException();
                    return true;
                case 55:
                    String _arg021 = data.readString();
                    int _arg18 = data.readInt();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    setVolumeUserFlags(_arg021, _arg18, _arg25);
                    reply.writeNoException();
                    return true;
                case 56:
                    String _arg022 = data.readString();
                    data.enforceNoDataAvail();
                    forgetVolume(_arg022);
                    reply.writeNoException();
                    return true;
                case 57:
                    forgetAllVolumes();
                    reply.writeNoException();
                    return true;
                case 58:
                    String _result9 = getPrimaryStorageUuid();
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 59:
                    String _arg023 = data.readString();
                    IPackageMoveObserver _arg19 =
                            IPackageMoveObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setPrimaryStorageUuid(_arg023, _arg19);
                    reply.writeNoException();
                    return true;
                case 60:
                    String _arg024 = data.readString();
                    IVoldTaskListener _arg110 =
                            IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    benchmark(_arg024, _arg110);
                    reply.writeNoException();
                    return true;
                case 61:
                    int _arg025 = data.readInt();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    setDebugFlags(_arg025, _arg111);
                    reply.writeNoException();
                    return true;
                case 62:
                    int _arg026 = data.readInt();
                    boolean _arg112 = data.readBoolean();
                    data.enforceNoDataAvail();
                    createUserStorageKeys(_arg026, _arg112);
                    reply.writeNoException();
                    return true;
                case 63:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    destroyUserStorageKeys(_arg027);
                    reply.writeNoException();
                    return true;
                case 64:
                    int _arg028 = data.readInt();
                    byte[] _arg113 = data.createByteArray();
                    data.enforceNoDataAvail();
                    unlockCeStorage(_arg028, _arg113);
                    reply.writeNoException();
                    return true;
                case 65:
                    int _arg029 = data.readInt();
                    data.enforceNoDataAvail();
                    lockCeStorage(_arg029);
                    reply.writeNoException();
                    return true;
                case 66:
                    int _arg030 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = isCeStorageUnlocked(_arg030);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 67:
                    String _arg031 = data.readString();
                    int _arg114 = data.readInt();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    prepareUserStorage(_arg031, _arg114, _arg26);
                    reply.writeNoException();
                    return true;
                case 68:
                    String _arg032 = data.readString();
                    int _arg115 = data.readInt();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    destroyUserStorage(_arg032, _arg115, _arg27);
                    reply.writeNoException();
                    return true;
                case 71:
                    int _arg033 = data.readInt();
                    byte[] _arg116 = data.createByteArray();
                    data.enforceNoDataAvail();
                    setCeStorageProtection(_arg033, _arg116);
                    reply.writeNoException();
                    return true;
                case 73:
                    int _arg034 = data.readInt();
                    IVoldTaskListener _arg117 =
                            IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    fstrim(_arg034, _arg117);
                    reply.writeNoException();
                    return true;
                case 74:
                    AppFuseMount _result11 = mountProxyFileDescriptorBridge();
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 75:
                    int _arg035 = data.readInt();
                    int _arg118 = data.readInt();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result12 =
                            openProxyFileDescriptor(_arg035, _arg118, _arg28);
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 76:
                    String _arg036 = data.readString();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result13 = getCacheQuotaBytes(_arg036, _arg119);
                    reply.writeNoException();
                    reply.writeLong(_result13);
                    return true;
                case 77:
                    String _arg037 = data.readString();
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result14 = getCacheSizeBytes(_arg037, _arg120);
                    reply.writeNoException();
                    reply.writeLong(_result14);
                    return true;
                case 78:
                    String _arg038 = data.readString();
                    int _arg121 = data.readInt();
                    String _arg29 = data.readString();
                    data.enforceNoDataAvail();
                    long _result15 = getAllocatableBytes(_arg038, _arg121, _arg29);
                    reply.writeNoException();
                    reply.writeLong(_result15);
                    return true;
                case 79:
                    String _arg039 = data.readString();
                    long _arg122 = data.readLong();
                    int _arg210 = data.readInt();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    allocateBytes(_arg039, _arg122, _arg210, _arg33);
                    reply.writeNoException();
                    return true;
                case 80:
                    runIdleMaintenance();
                    reply.writeNoException();
                    return true;
                case 81:
                    abortIdleMaintenance();
                    reply.writeNoException();
                    return true;
                case 84:
                    commitChanges();
                    reply.writeNoException();
                    return true;
                case 85:
                    boolean _result16 = supportsCheckpoint();
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 86:
                    int _arg040 = data.readInt();
                    data.enforceNoDataAvail();
                    startCheckpoint(_arg040);
                    reply.writeNoException();
                    return true;
                case 87:
                    boolean _result17 = needsCheckpoint();
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 88:
                    String _arg041 = data.readString();
                    boolean _arg123 = data.readBoolean();
                    data.enforceNoDataAvail();
                    abortChanges(_arg041, _arg123);
                    reply.writeNoException();
                    return true;
                case 90:
                    String _arg042 = data.readString();
                    data.enforceNoDataAvail();
                    fixupAppDir(_arg042);
                    reply.writeNoException();
                    return true;
                case 91:
                    String _arg043 = data.readString();
                    int _arg124 = data.readInt();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    disableAppDataIsolation(_arg043, _arg124, _arg211);
                    reply.writeNoException();
                    return true;
                case 92:
                    String _arg044 = data.readString();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    PendingIntent _result18 = getManageSpaceActivityIntent(_arg044, _arg125);
                    reply.writeNoException();
                    reply.writeTypedObject(_result18, 1);
                    return true;
                case 93:
                    String _arg045 = data.readString();
                    int _arg126 = data.readInt();
                    int _arg212 = data.readInt();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyAppIoBlocked(_arg045, _arg126, _arg212, _arg34);
                    reply.writeNoException();
                    return true;
                case 94:
                    String _arg046 = data.readString();
                    int _arg127 = data.readInt();
                    int _arg213 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyAppIoResumed(_arg046, _arg127, _arg213, _arg35);
                    reply.writeNoException();
                    return true;
                case 95:
                    int _arg047 = data.readInt();
                    String _arg128 = data.readString();
                    data.enforceNoDataAvail();
                    int _result19 = getExternalStorageMountMode(_arg047, _arg128);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 96:
                    String _arg048 = data.readString();
                    int _arg129 = data.readInt();
                    int _arg214 = data.readInt();
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result20 = isAppIoBlocked(_arg048, _arg129, _arg214, _arg36);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 97:
                    String _arg049 = data.readString();
                    data.enforceNoDataAvail();
                    setCloudMediaProvider(_arg049);
                    reply.writeNoException();
                    return true;
                case 98:
                    String _result21 = getCloudMediaProvider();
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 99:
                    long _result22 = getInternalStorageBlockDeviceSize();
                    reply.writeNoException();
                    reply.writeLong(_result22);
                    return true;
                case 100:
                    int _result23 = getInternalStorageRemainingLifetime();
                    reply.writeNoException();
                    reply.writeInt(_result23);
                    return true;
                case 111:
                    int _arg050 = data.readInt();
                    String _arg130 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result24 = setSensitive(_arg050, _arg130);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 112:
                    String _arg051 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result25 = isSensitive(_arg051);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 113:
                    int _arg052 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result26 = mountSdpMediaStorageCmd(_arg052);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 114:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result27 = setSdpPolicyCmd(_arg053);
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    return true;
                case 115:
                    int _arg054 = data.readInt();
                    String _arg131 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result28 = setSdpPolicyToPathCmd(_arg054, _arg131);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 116:
                    int _arg055 = data.readInt();
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = setDualDARPolicyCmd(_arg055, _arg132);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 151:
                    int _result30 = semGetExternalSdCardHealthState();
                    reply.writeNoException();
                    reply.writeInt(_result30);
                    return true;
                case 152:
                    String _result31 = semGetExternalSdCardId();
                    reply.writeNoException();
                    reply.writeString(_result31);
                    return true;
                case 153:
                    long _result32 = getUsedF2fsFileNode();
                    reply.writeNoException();
                    reply.writeLong(_result32);
                    return true;
                case 154:
                    String _arg056 = data.readString();
                    String _arg133 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result33 = mvFileAtData(_arg056, _arg133);
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 155:
                    String _arg057 = data.readString();
                    String _arg134 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result34 = cpFileAtData(_arg057, _arg134);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 156:
                    String _arg058 = data.readString();
                    String _arg135 = data.readString();
                    data.enforceNoDataAvail();
                    mountBySecApp(_arg058, _arg135);
                    reply.writeNoException();
                    return true;
                case 157:
                    String _arg059 = data.readString();
                    String _arg136 = data.readString();
                    data.enforceNoDataAvail();
                    unmountBySecApp(_arg059, _arg136);
                    reply.writeNoException();
                    return true;
                case 158:
                    String _arg060 = data.readString();
                    String _arg137 = data.readString();
                    data.enforceNoDataAvail();
                    formatBySecApp(_arg060, _arg137);
                    reply.writeNoException();
                    return true;
                case 159:
                    boolean _arg061 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result35 = encryptExternalStorage(_arg061);
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 202:
                    String _arg062 = data.readString();
                    data.enforceNoDataAvail();
                    String _result36 = getVolumeState(_arg062);
                    reply.writeNoException();
                    reply.writeString(_result36);
                    return true;
                case 203:
                    String _arg063 = data.readString();
                    int _arg138 = data.readInt();
                    String _arg215 = data.readString();
                    String _arg37 = data.readString();
                    int _arg42 = data.readInt();
                    boolean _arg5 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result37 =
                            createSecureContainer(_arg063, _arg138, _arg215, _arg37, _arg42, _arg5);
                    reply.writeNoException();
                    reply.writeInt(_result37);
                    return true;
                case 204:
                    String _arg064 = data.readString();
                    data.enforceNoDataAvail();
                    int _result38 = finalizeSecureContainer(_arg064);
                    reply.writeNoException();
                    reply.writeInt(_result38);
                    return true;
                case 205:
                    String _arg065 = data.readString();
                    boolean _arg139 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result39 = destroySecureContainer(_arg065, _arg139);
                    reply.writeNoException();
                    reply.writeInt(_result39);
                    return true;
                case 206:
                    String _arg066 = data.readString();
                    String _arg140 = data.readString();
                    int _arg216 = data.readInt();
                    boolean _arg38 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result40 = mountSecureContainer(_arg066, _arg140, _arg216, _arg38);
                    reply.writeNoException();
                    reply.writeInt(_result40);
                    return true;
                case 207:
                    String _arg067 = data.readString();
                    boolean _arg141 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result41 = unmountSecureContainer(_arg067, _arg141);
                    reply.writeNoException();
                    reply.writeInt(_result41);
                    return true;
                case 208:
                    String _arg068 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result42 = isSecureContainerMounted(_arg068);
                    reply.writeNoException();
                    reply.writeBoolean(_result42);
                    return true;
                case 209:
                    String _arg069 = data.readString();
                    String _arg142 = data.readString();
                    data.enforceNoDataAvail();
                    int _result43 = renameSecureContainer(_arg069, _arg142);
                    reply.writeNoException();
                    reply.writeInt(_result43);
                    return true;
                case 210:
                    String _arg070 = data.readString();
                    data.enforceNoDataAvail();
                    String _result44 = getSecureContainerPath(_arg070);
                    reply.writeNoException();
                    reply.writeString(_result44);
                    return true;
                case 211:
                    String[] _result45 = getSecureContainerList();
                    reply.writeNoException();
                    reply.writeStringArray(_result45);
                    return true;
                case 212:
                    finishMediaUpdate();
                    reply.writeNoException();
                    return true;
                case 213:
                    String _arg071 = data.readString();
                    data.enforceNoDataAvail();
                    String _result46 = getSecureContainerFilesystemPath(_arg071);
                    reply.writeNoException();
                    reply.writeString(_result46);
                    return true;
                case 214:
                    String _arg072 = data.readString();
                    int _arg143 = data.readInt();
                    String _arg217 = data.readString();
                    data.enforceNoDataAvail();
                    int _result47 = fixPermissionsSecureContainer(_arg072, _arg143, _arg217);
                    reply.writeNoException();
                    reply.writeInt(_result47);
                    return true;
                case 215:
                    String _arg073 = data.readString();
                    int _arg144 = data.readInt();
                    String _arg218 = data.readString();
                    data.enforceNoDataAvail();
                    int _result48 = resizeSecureContainer(_arg073, _arg144, _arg218);
                    reply.writeNoException();
                    reply.writeInt(_result48);
                    return true;
                case 216:
                    waitForAsecScan();
                    reply.writeNoException();
                    return true;
                case 217:
                    String _arg074 = data.readString();
                    int _arg145 = data.readInt();
                    String _arg219 = data.readString();
                    data.enforceNoDataAvail();
                    int _result49 = trimSecureContainer(_arg074, _arg145, _arg219);
                    reply.writeNoException();
                    reply.writeInt(_result49);
                    return true;
                case 218:
                    String _arg075 = data.readString();
                    data.enforceNoDataAvail();
                    int _result50 = getUsedSpaceSecureContainer(_arg075);
                    reply.writeNoException();
                    reply.writeInt(_result50);
                    return true;
                case 223:
                    int _result51 = createPassStorage();
                    reply.writeNoException();
                    reply.writeInt(_result51);
                    return true;
                case 224:
                    int _result52 = destroyPassStorage();
                    reply.writeNoException();
                    reply.writeInt(_result52);
                    return true;
                case 225:
                    int _result53 = unlockPassStorage();
                    reply.writeNoException();
                    reply.writeInt(_result53);
                    return true;
                case 226:
                    int _result54 = lockPassStorage();
                    reply.writeNoException();
                    reply.writeInt(_result54);
                    return true;
                case 227:
                    String _result55 = getPassStorage();
                    reply.writeNoException();
                    reply.writeString(_result55);
                    return true;
                case 229:
                    boolean _result56 = isPassUnlocked();
                    reply.writeNoException();
                    reply.writeBoolean(_result56);
                    return true;
                case 261:
                    long _arg076 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result57 = shrinkDataDdp(_arg076);
                    reply.writeNoException();
                    reply.writeBoolean(_result57);
                    return true;
                case 262:
                    long _arg077 = data.readLong();
                    data.enforceNoDataAvail();
                    int _result58 = reserveDataBlocks(_arg077);
                    reply.writeNoException();
                    reply.writeInt(_result58);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStorageManager {
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

            @Override // android.os.storage.IStorageManager
            public void registerListener(IStorageEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unregisterListener(IStorageEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int mountVolume(String mountPoint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mountPoint);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountVolume(String mountPoint, boolean force, boolean removeEncryption)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mountPoint);
                    _data.writeBoolean(force);
                    _data.writeBoolean(removeEncryption);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void shutdown(IStorageShutdownObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountObb(
                    String rawPath,
                    String canonicalPath,
                    IObbActionListener token,
                    int nonce,
                    ObbInfo obbInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    _data.writeString(canonicalPath);
                    _data.writeStrongInterface(token);
                    _data.writeInt(nonce);
                    _data.writeTypedObject(obbInfo, 0);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountObb(
                    String rawPath, boolean force, IObbActionListener token, int nonce)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    _data.writeBoolean(force);
                    _data.writeStrongInterface(token);
                    _data.writeInt(nonce);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isObbMounted(String rawPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getMountedObbPath(String rawPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public StorageVolume[] getVolumeList(int userId, String callingPackage, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    _data.writeInt(flags);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    StorageVolume[] _result =
                            (StorageVolume[]) _reply.createTypedArray(StorageVolume.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mkdirs(String callingPkg, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(path);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long lastMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public DiskInfo[] getDisks() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    DiskInfo[] _result = (DiskInfo[]) _reply.createTypedArray(DiskInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public VolumeInfo[] getVolumes(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    VolumeInfo[] _result =
                            (VolumeInfo[]) _reply.createTypedArray(VolumeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public VolumeRecord[] getVolumeRecords(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    VolumeRecord[] _result =
                            (VolumeRecord[]) _reply.createTypedArray(VolumeRecord.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void format(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPublic(String diskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPrivate(String diskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionMixed(String diskId, int ratio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    _data.writeInt(ratio);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeNickname(String fsUuid, String nickname) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    _data.writeString(nickname);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeUserFlags(String fsUuid, int flags, int mask)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    _data.writeInt(flags);
                    _data.writeInt(mask);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetVolume(String fsUuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetAllVolumes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getPrimaryStorageUuid() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setPrimaryStorageUuid(String volumeUuid, IPackageMoveObserver callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setDebugFlags(int flags, int mask) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(mask);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void createUserStorageKeys(int userId, boolean ephemeral)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeBoolean(ephemeral);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorageKeys(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unlockCeStorage(int userId, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(secret);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void lockCeStorage(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isCeStorageUnlocked(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void prepareUserStorage(String volumeUuid, int userId, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorage(String volumeUuid, int userId, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCeStorageProtection(int userId, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(secret);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fstrim(int flags, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    AppFuseMount _result =
                            (AppFuseMount) _reply.readTypedObject(AppFuseMount.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public ParcelFileDescriptor openProxyFileDescriptor(
                    int mountPointId, int fileId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mountPointId);
                    _data.writeInt(fileId);
                    _data.writeInt(mode);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheQuotaBytes(String volumeUuid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheSizeBytes(String volumeUuid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getAllocatableBytes(String volumeUuid, int flags, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void allocateBytes(
                    String volumeUuid, long bytes, int flags, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(bytes);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void commitChanges() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean supportsCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void startCheckpoint(int numTries) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(numTries);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean needsCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortChanges(String message, boolean retry) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    _data.writeBoolean(retry);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fixupAppDir(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void disableAppDataIsolation(String pkgName, int pid, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(pid);
                    _data.writeInt(userId);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public PendingIntent getManageSpaceActivityIntent(String packageName, int requestCode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(requestCode);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    PendingIntent _result =
                            (PendingIntent) _reply.readTypedObject(PendingIntent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoBlocked(String volumeUuid, int uid, int tid, int reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    _data.writeInt(tid);
                    _data.writeInt(reason);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoResumed(String volumeUuid, int uid, int tid, int reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    _data.writeInt(tid);
                    _data.writeInt(reason);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getExternalStorageMountMode(int uid, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isAppIoBlocked(String volumeUuid, int uid, int tid, int reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    _data.writeInt(tid);
                    _data.writeInt(reason);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCloudMediaProvider(String authority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(authority);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getCloudMediaProvider() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getInternalStorageBlockDeviceSize() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getInternalStorageRemainingLifetime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSensitive(int engineId, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(engineId);
                    _data.writeString(path);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isSensitive(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean mountSdpMediaStorageCmd(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSdpPolicyCmd(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(114, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSdpPolicyToPathCmd(int userId, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(path);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setDualDARPolicyCmd(int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(116, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int semGetExternalSdCardHealthState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(151, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String semGetExternalSdCardId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(152, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getUsedF2fsFileNode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(153, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean mvFileAtData(String from, String to) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(from);
                    _data.writeString(to);
                    this.mRemote.transact(154, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean cpFileAtData(String from, String to) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(from);
                    _data.writeString(to);
                    this.mRemote.transact(155, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountBySecApp(String volId, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(156, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountBySecApp(String volId, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(157, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void formatBySecApp(String volId, String callerPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeString(callerPackage);
                    this.mRemote.transact(158, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int encryptExternalStorage(boolean state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(state);
                    this.mRemote.transact(159, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getVolumeState(String mountPoint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(mountPoint);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int createSecureContainer(
                    String id,
                    int sizeMb,
                    String fstype,
                    String key,
                    int ownerUid,
                    boolean external)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(sizeMb);
                    _data.writeString(fstype);
                    _data.writeString(key);
                    _data.writeInt(ownerUid);
                    _data.writeBoolean(external);
                    this.mRemote.transact(203, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int finalizeSecureContainer(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(204, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int destroySecureContainer(String id, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeBoolean(force);
                    this.mRemote.transact(205, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int mountSecureContainer(String id, String key, int ownerUid, boolean readOnly)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(key);
                    _data.writeInt(ownerUid);
                    _data.writeBoolean(readOnly);
                    this.mRemote.transact(206, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int unmountSecureContainer(String id, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeBoolean(force);
                    this.mRemote.transact(207, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isSecureContainerMounted(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(208, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int renameSecureContainer(String oldId, String newId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(oldId);
                    _data.writeString(newId);
                    this.mRemote.transact(209, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getSecureContainerPath(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(210, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String[] getSecureContainerList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(211, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void finishMediaUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(212, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getSecureContainerFilesystemPath(String cid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cid);
                    this.mRemote.transact(213, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int fixPermissionsSecureContainer(String id, int gid, String filename)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(gid);
                    _data.writeString(filename);
                    this.mRemote.transact(214, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int resizeSecureContainer(String id, int sizeMb, String key)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(sizeMb);
                    _data.writeString(key);
                    this.mRemote.transact(215, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void waitForAsecScan() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(216, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int trimSecureContainer(String id, int sizeMb, String key)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(sizeMb);
                    _data.writeString(key);
                    this.mRemote.transact(217, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getUsedSpaceSecureContainer(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(218, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int createPassStorage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int destroyPassStorage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(224, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int unlockPassStorage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(225, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int lockPassStorage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(226, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getPassStorage() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(227, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isPassUnlocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(229, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean shrinkDataDdp(long superUsedSectors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(superUsedSectors);
                    this.mRemote.transact(261, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int reserveDataBlocks(long superUsedSectors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(superUsedSectors);
                    this.mRemote.transact(262, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void shutdown_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.SHUTDOWN, getCallingPid(), getCallingUid());
        }

        protected void runMaintenance_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void mount_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void unmount_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void format_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionPublic_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionPrivate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionMixed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setVolumeNickname_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void setVolumeUserFlags_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void forgetVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void forgetAllVolumes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void setPrimaryStorageUuid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void benchmark_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setDebugFlags_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void createUserStorageKeys_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void destroyUserStorageKeys_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unlockCeStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void lockCeStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareUserStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void destroyUserStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setCeStorageProtection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void fstrim_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void needsCheckpoint_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void getExternalStorageMountMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_MEDIA_STORAGE, getCallingPid(), getCallingUid());
        }

        protected void getInternalStorageRemainingLifetime_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.READ_PRIVILEGED_PHONE_STATE,
                    getCallingPid(),
                    getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 261;
        }
    }
}
