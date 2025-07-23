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
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageShutdownObserver;
import android.provider.Telephony;
import com.android.internal.os.AppFuseMount;
import com.samsung.android.media.AudioParameter;

/* loaded from: classes3.dex */
public interface IStorageManager extends IInterface {

    public static class Default implements IStorageManager {
        @Override // android.os.storage.IStorageManager
        public void abortChanges(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void abortIdleMaintenance() throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void allocateBytes(String str, long j, int i, String str2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void commitChanges() throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean cpFileAtData(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public int createPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void createUserStorageKeys(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int destroyPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int destroySecureContainer(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void destroyUserStorage(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void destroyUserStorageKeys(int i) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void disableAppDataIsolation(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int encryptExternalStorage(boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int finalizeSecureContainer(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void finishMediaUpdate() throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void fixupAppDir(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void forgetAllVolumes() throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void forgetVolume(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void format(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void formatBySecApp(String str, String str2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public long getAllocatableBytes(String str, int i, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public long getCacheQuotaBytes(String str, int i) throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public long getCacheSizeBytes(String str, int i) throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public String getCloudMediaProvider() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public DiskInfo[] getDisks() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public int getExternalStorageMountMode(int i, String str) throws RemoteException {
            return 0;
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
        public PendingIntent getManageSpaceActivityIntent(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String getMountedObbPath(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String getPassStorage() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String getPrimaryStorageUuid() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String getSecureContainerFilesystemPath(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String[] getSecureContainerList() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String getSecureContainerPath(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public long getUsedF2fsFileNode() throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public int getUsedSpaceSecureContainer(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public VolumeRecord[] getVolumeRecords(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public String getVolumeState(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public VolumeInfo[] getVolumes(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isCeStorageUnlocked(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isObbMounted(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isPassSupport() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isPassUnlocked() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isSecureContainerMounted(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isSensitive(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public long lastMaintenance() throws RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public void lockCeStorage(int i) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int lockPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void mkdirs(String str, String str2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void mount(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void mountBySecApp(String str, String str2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void mountObb(String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public boolean mountSdpMediaStorageCmd(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int mountVolume(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public boolean mvFileAtData(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean needsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void notifyAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void notifyAppIoResumed(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void partitionMixed(String str, int i) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void partitionPrivate(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void partitionPublic(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void prepareUserStorage(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int renameSecureContainer(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int reserveDataBlocks(long j) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public int resizeSecureContainer(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void runIdleMaintenance() throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void runMaintenance() throws RemoteException {
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
        public void setCeStorageProtection(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setCloudMediaProvider(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setDebugFlags(int i, int i2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean setSdpPolicyCmd(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public boolean setSensitive(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void setVolumeNickname(String str, String str2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setVolumeUserFlags(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean shrinkDataDdp(long j) throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void startCheckpoint(int i) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean supportsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public int trimSecureContainer(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void unlockCeStorage(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int unlockPassStorage() throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void unmount(String str) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unmountBySecApp(String str, String str2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int unmountSecureContainer(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public void unmountVolume(String str, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void waitForAsecScan() throws RemoteException {
        }
    }

    void abortChanges(String str, boolean z) throws RemoteException;

    void abortIdleMaintenance() throws RemoteException;

    void allocateBytes(String str, long j, int i, String str2) throws RemoteException;

    void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void commitChanges() throws RemoteException;

    boolean cpFileAtData(String str, String str2) throws RemoteException;

    int createPassStorage() throws RemoteException;

    int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException;

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

    boolean isPassSupport() throws RemoteException;

    boolean isPassUnlocked() throws RemoteException;

    boolean isSecureContainerMounted(String str) throws RemoteException;

    boolean isSensitive(String str) throws RemoteException;

    long lastMaintenance() throws RemoteException;

    void lockCeStorage(int i) throws RemoteException;

    int lockPassStorage() throws RemoteException;

    void mkdirs(String str, String str2) throws RemoteException;

    void mount(String str) throws RemoteException;

    void mountBySecApp(String str, String str2) throws RemoteException;

    void mountObb(String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) throws RemoteException;

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

    void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

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

    void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) throws RemoteException;

    int unmountSecureContainer(String str, boolean z) throws RemoteException;

    void unmountVolume(String str, boolean z, boolean z2) throws RemoteException;

    void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    void waitForAsecScan() throws RemoteException;

    public static abstract class Stub extends Binder implements IStorageManager {
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
        static final int TRANSACTION_isPassSupport = 230;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 261;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IStorageManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStorageManager)) {
                return (IStorageManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerListener";
            }
            if (i == 2) {
                return "unregisterListener";
            }
            if (i == 6) {
                return "mountVolume";
            }
            if (i == 7) {
                return "unmountVolume";
            }
            if (i == 42) {
                return "lastMaintenance";
            }
            if (i != 43) {
                switch (i) {
                    case 20:
                        return UsbManager.USB_FUNCTION_SHUTDOWN;
                    case 30:
                        return "getVolumeList";
                    case 35:
                        return "mkdirs";
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
                    case 230:
                        return "isPassSupport";
                    case 261:
                        return "shrinkDataDdp";
                    case 262:
                        return "reserveDataBlocks";
                    default:
                        switch (i) {
                            case 22:
                                return "mountObb";
                            case 23:
                                return "unmountObb";
                            case 24:
                                return "isObbMounted";
                            case 25:
                                return "getMountedObbPath";
                            default:
                                switch (i) {
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
                                    default:
                                        switch (i) {
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
                                            default:
                                                switch (i) {
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
                                                    default:
                                                        switch (i) {
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
                                                            default:
                                                                switch (i) {
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
                                                                    default:
                                                                        return null;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
            }
            return "runMaintenance";
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
            if (i == 1) {
                IStorageEventListener asInterface = IStorageEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IStorageEventListener asInterface2 = IStorageEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 6) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                int mountVolume = mountVolume(readString);
                parcel2.writeNoException();
                parcel2.writeInt(mountVolume);
            } else if (i == 7) {
                String readString2 = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                unmountVolume(readString2, readBoolean, readBoolean2);
                parcel2.writeNoException();
            } else if (i == 42) {
                long lastMaintenance = lastMaintenance();
                parcel2.writeNoException();
                parcel2.writeLong(lastMaintenance);
            } else if (i != 43) {
                switch (i) {
                    case 20:
                        IStorageShutdownObserver asInterface3 = IStorageShutdownObserver.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        shutdown(asInterface3);
                        parcel2.writeNoException();
                        break;
                    case 30:
                        int readInt = parcel.readInt();
                        String readString3 = parcel.readString();
                        int readInt2 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        StorageVolume[] volumeList = getVolumeList(readInt, readString3, readInt2);
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(volumeList, 1);
                        break;
                    case 35:
                        String readString4 = parcel.readString();
                        String readString5 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        mkdirs(readString4, readString5);
                        parcel2.writeNoException();
                        break;
                    case 45:
                        DiskInfo[] disks = getDisks();
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(disks, 1);
                        break;
                    case 46:
                        int readInt3 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        VolumeInfo[] volumes = getVolumes(readInt3);
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(volumes, 1);
                        break;
                    case 47:
                        int readInt4 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        VolumeRecord[] volumeRecords = getVolumeRecords(readInt4);
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(volumeRecords, 1);
                        break;
                    case 48:
                        String readString6 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        mount(readString6);
                        parcel2.writeNoException();
                        break;
                    case 49:
                        String readString7 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        unmount(readString7);
                        parcel2.writeNoException();
                        break;
                    case 50:
                        String readString8 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        format(readString8);
                        parcel2.writeNoException();
                        break;
                    case 51:
                        String readString9 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        partitionPublic(readString9);
                        parcel2.writeNoException();
                        break;
                    case 52:
                        String readString10 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        partitionPrivate(readString10);
                        parcel2.writeNoException();
                        break;
                    case 53:
                        String readString11 = parcel.readString();
                        int readInt5 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        partitionMixed(readString11, readInt5);
                        parcel2.writeNoException();
                        break;
                    case 54:
                        String readString12 = parcel.readString();
                        String readString13 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        setVolumeNickname(readString12, readString13);
                        parcel2.writeNoException();
                        break;
                    case 55:
                        String readString14 = parcel.readString();
                        int readInt6 = parcel.readInt();
                        int readInt7 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        setVolumeUserFlags(readString14, readInt6, readInt7);
                        parcel2.writeNoException();
                        break;
                    case 56:
                        String readString15 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        forgetVolume(readString15);
                        parcel2.writeNoException();
                        break;
                    case 57:
                        forgetAllVolumes();
                        parcel2.writeNoException();
                        break;
                    case 58:
                        String primaryStorageUuid = getPrimaryStorageUuid();
                        parcel2.writeNoException();
                        parcel2.writeString(primaryStorageUuid);
                        break;
                    case 59:
                        String readString16 = parcel.readString();
                        IPackageMoveObserver asInterface4 = IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        setPrimaryStorageUuid(readString16, asInterface4);
                        parcel2.writeNoException();
                        break;
                    case 60:
                        String readString17 = parcel.readString();
                        IVoldTaskListener asInterface5 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        benchmark(readString17, asInterface5);
                        parcel2.writeNoException();
                        break;
                    case 61:
                        int readInt8 = parcel.readInt();
                        int readInt9 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        setDebugFlags(readInt8, readInt9);
                        parcel2.writeNoException();
                        break;
                    case 62:
                        int readInt10 = parcel.readInt();
                        boolean readBoolean3 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        createUserStorageKeys(readInt10, readBoolean3);
                        parcel2.writeNoException();
                        break;
                    case 63:
                        int readInt11 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        destroyUserStorageKeys(readInt11);
                        parcel2.writeNoException();
                        break;
                    case 64:
                        int readInt12 = parcel.readInt();
                        byte[] createByteArray = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        unlockCeStorage(readInt12, createByteArray);
                        parcel2.writeNoException();
                        break;
                    case 65:
                        int readInt13 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        lockCeStorage(readInt13);
                        parcel2.writeNoException();
                        break;
                    case 66:
                        int readInt14 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        boolean isCeStorageUnlocked = isCeStorageUnlocked(readInt14);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(isCeStorageUnlocked);
                        break;
                    case 67:
                        String readString18 = parcel.readString();
                        int readInt15 = parcel.readInt();
                        int readInt16 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        prepareUserStorage(readString18, readInt15, readInt16);
                        parcel2.writeNoException();
                        break;
                    case 68:
                        String readString19 = parcel.readString();
                        int readInt17 = parcel.readInt();
                        int readInt18 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        destroyUserStorage(readString19, readInt17, readInt18);
                        parcel2.writeNoException();
                        break;
                    case 71:
                        int readInt19 = parcel.readInt();
                        byte[] createByteArray2 = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        setCeStorageProtection(readInt19, createByteArray2);
                        parcel2.writeNoException();
                        break;
                    case 202:
                        String readString20 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        String volumeState = getVolumeState(readString20);
                        parcel2.writeNoException();
                        parcel2.writeString(volumeState);
                        break;
                    case 203:
                        String readString21 = parcel.readString();
                        int readInt20 = parcel.readInt();
                        String readString22 = parcel.readString();
                        String readString23 = parcel.readString();
                        int readInt21 = parcel.readInt();
                        boolean readBoolean4 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int createSecureContainer = createSecureContainer(readString21, readInt20, readString22, readString23, readInt21, readBoolean4);
                        parcel2.writeNoException();
                        parcel2.writeInt(createSecureContainer);
                        break;
                    case 204:
                        String readString24 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int finalizeSecureContainer = finalizeSecureContainer(readString24);
                        parcel2.writeNoException();
                        parcel2.writeInt(finalizeSecureContainer);
                        break;
                    case 205:
                        String readString25 = parcel.readString();
                        boolean readBoolean5 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int destroySecureContainer = destroySecureContainer(readString25, readBoolean5);
                        parcel2.writeNoException();
                        parcel2.writeInt(destroySecureContainer);
                        break;
                    case 206:
                        String readString26 = parcel.readString();
                        String readString27 = parcel.readString();
                        int readInt22 = parcel.readInt();
                        boolean readBoolean6 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int mountSecureContainer = mountSecureContainer(readString26, readString27, readInt22, readBoolean6);
                        parcel2.writeNoException();
                        parcel2.writeInt(mountSecureContainer);
                        break;
                    case 207:
                        String readString28 = parcel.readString();
                        boolean readBoolean7 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int unmountSecureContainer = unmountSecureContainer(readString28, readBoolean7);
                        parcel2.writeNoException();
                        parcel2.writeInt(unmountSecureContainer);
                        break;
                    case 208:
                        String readString29 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        boolean isSecureContainerMounted = isSecureContainerMounted(readString29);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(isSecureContainerMounted);
                        break;
                    case 209:
                        String readString30 = parcel.readString();
                        String readString31 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int renameSecureContainer = renameSecureContainer(readString30, readString31);
                        parcel2.writeNoException();
                        parcel2.writeInt(renameSecureContainer);
                        break;
                    case 210:
                        String readString32 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        String secureContainerPath = getSecureContainerPath(readString32);
                        parcel2.writeNoException();
                        parcel2.writeString(secureContainerPath);
                        break;
                    case 211:
                        String[] secureContainerList = getSecureContainerList();
                        parcel2.writeNoException();
                        parcel2.writeStringArray(secureContainerList);
                        break;
                    case 212:
                        finishMediaUpdate();
                        parcel2.writeNoException();
                        break;
                    case 213:
                        String readString33 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        String secureContainerFilesystemPath = getSecureContainerFilesystemPath(readString33);
                        parcel2.writeNoException();
                        parcel2.writeString(secureContainerFilesystemPath);
                        break;
                    case 214:
                        String readString34 = parcel.readString();
                        int readInt23 = parcel.readInt();
                        String readString35 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int fixPermissionsSecureContainer = fixPermissionsSecureContainer(readString34, readInt23, readString35);
                        parcel2.writeNoException();
                        parcel2.writeInt(fixPermissionsSecureContainer);
                        break;
                    case 215:
                        String readString36 = parcel.readString();
                        int readInt24 = parcel.readInt();
                        String readString37 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int resizeSecureContainer = resizeSecureContainer(readString36, readInt24, readString37);
                        parcel2.writeNoException();
                        parcel2.writeInt(resizeSecureContainer);
                        break;
                    case 216:
                        waitForAsecScan();
                        parcel2.writeNoException();
                        break;
                    case 217:
                        String readString38 = parcel.readString();
                        int readInt25 = parcel.readInt();
                        String readString39 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int trimSecureContainer = trimSecureContainer(readString38, readInt25, readString39);
                        parcel2.writeNoException();
                        parcel2.writeInt(trimSecureContainer);
                        break;
                    case 218:
                        String readString40 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int usedSpaceSecureContainer = getUsedSpaceSecureContainer(readString40);
                        parcel2.writeNoException();
                        parcel2.writeInt(usedSpaceSecureContainer);
                        break;
                    case 223:
                        int createPassStorage = createPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(createPassStorage);
                        break;
                    case 224:
                        int destroyPassStorage = destroyPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(destroyPassStorage);
                        break;
                    case 225:
                        int unlockPassStorage = unlockPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(unlockPassStorage);
                        break;
                    case 226:
                        int lockPassStorage = lockPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(lockPassStorage);
                        break;
                    case 227:
                        String passStorage = getPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeString(passStorage);
                        break;
                    case 229:
                        boolean isPassUnlocked = isPassUnlocked();
                        parcel2.writeNoException();
                        parcel2.writeBoolean(isPassUnlocked);
                        break;
                    case 230:
                        boolean isPassSupport = isPassSupport();
                        parcel2.writeNoException();
                        parcel2.writeBoolean(isPassSupport);
                        break;
                    case 261:
                        long readLong = parcel.readLong();
                        parcel.enforceNoDataAvail();
                        boolean shrinkDataDdp = shrinkDataDdp(readLong);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(shrinkDataDdp);
                        break;
                    case 262:
                        long readLong2 = parcel.readLong();
                        parcel.enforceNoDataAvail();
                        int reserveDataBlocks = reserveDataBlocks(readLong2);
                        parcel2.writeNoException();
                        parcel2.writeInt(reserveDataBlocks);
                        break;
                    default:
                        switch (i) {
                            case 22:
                                String readString41 = parcel.readString();
                                String readString42 = parcel.readString();
                                IObbActionListener asInterface6 = IObbActionListener.Stub.asInterface(parcel.readStrongBinder());
                                int readInt26 = parcel.readInt();
                                ObbInfo obbInfo = (ObbInfo) parcel.readTypedObject(ObbInfo.CREATOR);
                                parcel.enforceNoDataAvail();
                                mountObb(readString41, readString42, asInterface6, readInt26, obbInfo);
                                parcel2.writeNoException();
                                break;
                            case 23:
                                String readString43 = parcel.readString();
                                boolean readBoolean8 = parcel.readBoolean();
                                IObbActionListener asInterface7 = IObbActionListener.Stub.asInterface(parcel.readStrongBinder());
                                int readInt27 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                unmountObb(readString43, readBoolean8, asInterface7, readInt27);
                                parcel2.writeNoException();
                                break;
                            case 24:
                                String readString44 = parcel.readString();
                                parcel.enforceNoDataAvail();
                                boolean isObbMounted = isObbMounted(readString44);
                                parcel2.writeNoException();
                                parcel2.writeBoolean(isObbMounted);
                                break;
                            case 25:
                                String readString45 = parcel.readString();
                                parcel.enforceNoDataAvail();
                                String mountedObbPath = getMountedObbPath(readString45);
                                parcel2.writeNoException();
                                parcel2.writeString(mountedObbPath);
                                break;
                            default:
                                switch (i) {
                                    case 73:
                                        int readInt28 = parcel.readInt();
                                        IVoldTaskListener asInterface8 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                                        parcel.enforceNoDataAvail();
                                        fstrim(readInt28, asInterface8);
                                        parcel2.writeNoException();
                                        break;
                                    case 74:
                                        AppFuseMount mountProxyFileDescriptorBridge = mountProxyFileDescriptorBridge();
                                        parcel2.writeNoException();
                                        parcel2.writeTypedObject(mountProxyFileDescriptorBridge, 1);
                                        break;
                                    case 75:
                                        int readInt29 = parcel.readInt();
                                        int readInt30 = parcel.readInt();
                                        int readInt31 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        ParcelFileDescriptor openProxyFileDescriptor = openProxyFileDescriptor(readInt29, readInt30, readInt31);
                                        parcel2.writeNoException();
                                        parcel2.writeTypedObject(openProxyFileDescriptor, 1);
                                        break;
                                    case 76:
                                        String readString46 = parcel.readString();
                                        int readInt32 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        long cacheQuotaBytes = getCacheQuotaBytes(readString46, readInt32);
                                        parcel2.writeNoException();
                                        parcel2.writeLong(cacheQuotaBytes);
                                        break;
                                    case 77:
                                        String readString47 = parcel.readString();
                                        int readInt33 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        long cacheSizeBytes = getCacheSizeBytes(readString47, readInt33);
                                        parcel2.writeNoException();
                                        parcel2.writeLong(cacheSizeBytes);
                                        break;
                                    case 78:
                                        String readString48 = parcel.readString();
                                        int readInt34 = parcel.readInt();
                                        String readString49 = parcel.readString();
                                        parcel.enforceNoDataAvail();
                                        long allocatableBytes = getAllocatableBytes(readString48, readInt34, readString49);
                                        parcel2.writeNoException();
                                        parcel2.writeLong(allocatableBytes);
                                        break;
                                    case 79:
                                        String readString50 = parcel.readString();
                                        long readLong3 = parcel.readLong();
                                        int readInt35 = parcel.readInt();
                                        String readString51 = parcel.readString();
                                        parcel.enforceNoDataAvail();
                                        allocateBytes(readString50, readLong3, readInt35, readString51);
                                        parcel2.writeNoException();
                                        break;
                                    case 80:
                                        runIdleMaintenance();
                                        parcel2.writeNoException();
                                        break;
                                    case 81:
                                        abortIdleMaintenance();
                                        parcel2.writeNoException();
                                        break;
                                    default:
                                        switch (i) {
                                            case 84:
                                                commitChanges();
                                                parcel2.writeNoException();
                                                break;
                                            case 85:
                                                boolean supportsCheckpoint = supportsCheckpoint();
                                                parcel2.writeNoException();
                                                parcel2.writeBoolean(supportsCheckpoint);
                                                break;
                                            case 86:
                                                int readInt36 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                startCheckpoint(readInt36);
                                                parcel2.writeNoException();
                                                break;
                                            case 87:
                                                boolean needsCheckpoint = needsCheckpoint();
                                                parcel2.writeNoException();
                                                parcel2.writeBoolean(needsCheckpoint);
                                                break;
                                            case 88:
                                                String readString52 = parcel.readString();
                                                boolean readBoolean9 = parcel.readBoolean();
                                                parcel.enforceNoDataAvail();
                                                abortChanges(readString52, readBoolean9);
                                                parcel2.writeNoException();
                                                break;
                                            default:
                                                switch (i) {
                                                    case 90:
                                                        String readString53 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        fixupAppDir(readString53);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 91:
                                                        String readString54 = parcel.readString();
                                                        int readInt37 = parcel.readInt();
                                                        int readInt38 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        disableAppDataIsolation(readString54, readInt37, readInt38);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 92:
                                                        String readString55 = parcel.readString();
                                                        int readInt39 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        PendingIntent manageSpaceActivityIntent = getManageSpaceActivityIntent(readString55, readInt39);
                                                        parcel2.writeNoException();
                                                        parcel2.writeTypedObject(manageSpaceActivityIntent, 1);
                                                        break;
                                                    case 93:
                                                        String readString56 = parcel.readString();
                                                        int readInt40 = parcel.readInt();
                                                        int readInt41 = parcel.readInt();
                                                        int readInt42 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        notifyAppIoBlocked(readString56, readInt40, readInt41, readInt42);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 94:
                                                        String readString57 = parcel.readString();
                                                        int readInt43 = parcel.readInt();
                                                        int readInt44 = parcel.readInt();
                                                        int readInt45 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        notifyAppIoResumed(readString57, readInt43, readInt44, readInt45);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 95:
                                                        int readInt46 = parcel.readInt();
                                                        String readString58 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        int externalStorageMountMode = getExternalStorageMountMode(readInt46, readString58);
                                                        parcel2.writeNoException();
                                                        parcel2.writeInt(externalStorageMountMode);
                                                        break;
                                                    case 96:
                                                        String readString59 = parcel.readString();
                                                        int readInt47 = parcel.readInt();
                                                        int readInt48 = parcel.readInt();
                                                        int readInt49 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean isAppIoBlocked = isAppIoBlocked(readString59, readInt47, readInt48, readInt49);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(isAppIoBlocked);
                                                        break;
                                                    case 97:
                                                        String readString60 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        setCloudMediaProvider(readString60);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 98:
                                                        String cloudMediaProvider = getCloudMediaProvider();
                                                        parcel2.writeNoException();
                                                        parcel2.writeString(cloudMediaProvider);
                                                        break;
                                                    case 99:
                                                        long internalStorageBlockDeviceSize = getInternalStorageBlockDeviceSize();
                                                        parcel2.writeNoException();
                                                        parcel2.writeLong(internalStorageBlockDeviceSize);
                                                        break;
                                                    case 100:
                                                        int internalStorageRemainingLifetime = getInternalStorageRemainingLifetime();
                                                        parcel2.writeNoException();
                                                        parcel2.writeInt(internalStorageRemainingLifetime);
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 111:
                                                                int readInt50 = parcel.readInt();
                                                                String readString61 = parcel.readString();
                                                                parcel.enforceNoDataAvail();
                                                                boolean sensitive = setSensitive(readInt50, readString61);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(sensitive);
                                                                break;
                                                            case 112:
                                                                String readString62 = parcel.readString();
                                                                parcel.enforceNoDataAvail();
                                                                boolean isSensitive = isSensitive(readString62);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(isSensitive);
                                                                break;
                                                            case 113:
                                                                int readInt51 = parcel.readInt();
                                                                parcel.enforceNoDataAvail();
                                                                boolean mountSdpMediaStorageCmd = mountSdpMediaStorageCmd(readInt51);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(mountSdpMediaStorageCmd);
                                                                break;
                                                            case 114:
                                                                int readInt52 = parcel.readInt();
                                                                parcel.enforceNoDataAvail();
                                                                boolean sdpPolicyCmd = setSdpPolicyCmd(readInt52);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(sdpPolicyCmd);
                                                                break;
                                                            case 115:
                                                                int readInt53 = parcel.readInt();
                                                                String readString63 = parcel.readString();
                                                                parcel.enforceNoDataAvail();
                                                                boolean sdpPolicyToPathCmd = setSdpPolicyToPathCmd(readInt53, readString63);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(sdpPolicyToPathCmd);
                                                                break;
                                                            case 116:
                                                                int readInt54 = parcel.readInt();
                                                                int readInt55 = parcel.readInt();
                                                                parcel.enforceNoDataAvail();
                                                                boolean dualDARPolicyCmd = setDualDARPolicyCmd(readInt54, readInt55);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(dualDARPolicyCmd);
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 151:
                                                                        int semGetExternalSdCardHealthState = semGetExternalSdCardHealthState();
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeInt(semGetExternalSdCardHealthState);
                                                                        break;
                                                                    case 152:
                                                                        String semGetExternalSdCardId = semGetExternalSdCardId();
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeString(semGetExternalSdCardId);
                                                                        break;
                                                                    case 153:
                                                                        long usedF2fsFileNode = getUsedF2fsFileNode();
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeLong(usedF2fsFileNode);
                                                                        break;
                                                                    case 154:
                                                                        String readString64 = parcel.readString();
                                                                        String readString65 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        boolean mvFileAtData = mvFileAtData(readString64, readString65);
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeBoolean(mvFileAtData);
                                                                        break;
                                                                    case 155:
                                                                        String readString66 = parcel.readString();
                                                                        String readString67 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        boolean cpFileAtData = cpFileAtData(readString66, readString67);
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeBoolean(cpFileAtData);
                                                                        break;
                                                                    case 156:
                                                                        String readString68 = parcel.readString();
                                                                        String readString69 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        mountBySecApp(readString68, readString69);
                                                                        parcel2.writeNoException();
                                                                        break;
                                                                    case 157:
                                                                        String readString70 = parcel.readString();
                                                                        String readString71 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        unmountBySecApp(readString70, readString71);
                                                                        parcel2.writeNoException();
                                                                        break;
                                                                    case 158:
                                                                        String readString72 = parcel.readString();
                                                                        String readString73 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        formatBySecApp(readString72, readString73);
                                                                        parcel2.writeNoException();
                                                                        break;
                                                                    case 159:
                                                                        boolean readBoolean10 = parcel.readBoolean();
                                                                        parcel.enforceNoDataAvail();
                                                                        int encryptExternalStorage = encryptExternalStorage(readBoolean10);
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeInt(encryptExternalStorage);
                                                                        break;
                                                                    default:
                                                                        return super.onTransact(i, parcel, parcel2, i2);
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
            } else {
                runMaintenance();
                parcel2.writeNoException();
            }
            return true;
        }

        private static class Proxy implements IStorageManager {
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

            @Override // android.os.storage.IStorageManager
            public void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStorageEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStorageEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int mountVolume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountVolume(String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStorageShutdownObserver);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountObb(String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iObbActionListener);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(obbInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iObbActionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isObbMounted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getMountedObbPath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StorageVolume[]) obtain2.createTypedArray(StorageVolume.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mkdirs(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long lastMaintenance() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runMaintenance() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public DiskInfo[] getDisks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DiskInfo[]) obtain2.createTypedArray(DiskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public VolumeInfo[] getVolumes(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VolumeInfo[]) obtain2.createTypedArray(VolumeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public VolumeRecord[] getVolumeRecords(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VolumeRecord[]) obtain2.createTypedArray(VolumeRecord.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mount(String str) throws RemoteException {
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

            @Override // android.os.storage.IStorageManager
            public void unmount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void format(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPublic(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPrivate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionMixed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeNickname(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeUserFlags(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetVolume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetAllVolumes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getPrimaryStorageUuid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setDebugFlags(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void createUserStorageKeys(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorageKeys(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unlockCeStorage(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void lockCeStorage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isCeStorageUnlocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void prepareUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCeStorageProtection(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppFuseMount) obtain2.readTypedObject(AppFuseMount.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheQuotaBytes(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheSizeBytes(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getAllocatableBytes(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void allocateBytes(String str, long j, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runIdleMaintenance() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortIdleMaintenance() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void commitChanges() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean supportsCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void startCheckpoint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean needsCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortChanges(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fixupAppDir(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void disableAppDataIsolation(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public PendingIntent getManageSpaceActivityIntent(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoResumed(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getExternalStorageMountMode(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCloudMediaProvider(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getCloudMediaProvider() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getInternalStorageBlockDeviceSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getInternalStorageRemainingLifetime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSensitive(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isSensitive(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean mountSdpMediaStorageCmd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSdpPolicyCmd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int semGetExternalSdCardHealthState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String semGetExternalSdCardId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getUsedF2fsFileNode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean mvFileAtData(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean cpFileAtData(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountBySecApp(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountBySecApp(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void formatBySecApp(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int encryptExternalStorage(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getVolumeState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int finalizeSecureContainer(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int destroySecureContainer(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int unmountSecureContainer(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isSecureContainerMounted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int renameSecureContainer(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getSecureContainerPath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String[] getSecureContainerList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void finishMediaUpdate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getSecureContainerFilesystemPath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int resizeSecureContainer(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void waitForAsecScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int trimSecureContainer(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getUsedSpaceSecureContainer(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int createPassStorage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int destroyPassStorage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int unlockPassStorage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int lockPassStorage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getPassStorage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isPassUnlocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isPassSupport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean shrinkDataDdp(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int reserveDataBlocks(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void shutdown_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SHUTDOWN, getCallingPid(), getCallingUid());
        }

        protected void runMaintenance_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void mount_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void unmount_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void format_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionPublic_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionPrivate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionMixed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setVolumeNickname_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setVolumeUserFlags_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void forgetVolume_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void forgetAllVolumes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setPrimaryStorageUuid_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void benchmark_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setDebugFlags_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void createUserStorageKeys_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void destroyUserStorageKeys_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unlockCeStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void lockCeStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareUserStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void destroyUserStorage_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setCeStorageProtection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void fstrim_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void needsCheckpoint_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void getExternalStorageMountMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_MEDIA_STORAGE, getCallingPid(), getCallingUid());
        }
    }
}
