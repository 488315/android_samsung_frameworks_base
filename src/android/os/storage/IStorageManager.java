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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStorageManager)) {
                return (IStorageManager) iInterfaceQueryLocalInterface;
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
                IStorageEventListener iStorageEventListenerAsInterface = IStorageEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerListener(iStorageEventListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IStorageEventListener iStorageEventListenerAsInterface2 = IStorageEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterListener(iStorageEventListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 6) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                int iMountVolume = mountVolume(string);
                parcel2.writeNoException();
                parcel2.writeInt(iMountVolume);
            } else if (i == 7) {
                String string2 = parcel.readString();
                boolean z = parcel.readBoolean();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                unmountVolume(string2, z, z2);
                parcel2.writeNoException();
            } else if (i == 42) {
                long jLastMaintenance = lastMaintenance();
                parcel2.writeNoException();
                parcel2.writeLong(jLastMaintenance);
            } else if (i != 43) {
                switch (i) {
                    case 20:
                        IStorageShutdownObserver iStorageShutdownObserverAsInterface = IStorageShutdownObserver.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        shutdown(iStorageShutdownObserverAsInterface);
                        parcel2.writeNoException();
                        break;
                    case 30:
                        int i3 = parcel.readInt();
                        String string3 = parcel.readString();
                        int i4 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        StorageVolume[] volumeList = getVolumeList(i3, string3, i4);
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(volumeList, 1);
                        break;
                    case 35:
                        String string4 = parcel.readString();
                        String string5 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        mkdirs(string4, string5);
                        parcel2.writeNoException();
                        break;
                    case 45:
                        DiskInfo[] disks = getDisks();
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(disks, 1);
                        break;
                    case 46:
                        int i5 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        VolumeInfo[] volumes = getVolumes(i5);
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(volumes, 1);
                        break;
                    case 47:
                        int i6 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        VolumeRecord[] volumeRecords = getVolumeRecords(i6);
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(volumeRecords, 1);
                        break;
                    case 48:
                        String string6 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        mount(string6);
                        parcel2.writeNoException();
                        break;
                    case 49:
                        String string7 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        unmount(string7);
                        parcel2.writeNoException();
                        break;
                    case 50:
                        String string8 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        format(string8);
                        parcel2.writeNoException();
                        break;
                    case 51:
                        String string9 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        partitionPublic(string9);
                        parcel2.writeNoException();
                        break;
                    case 52:
                        String string10 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        partitionPrivate(string10);
                        parcel2.writeNoException();
                        break;
                    case 53:
                        String string11 = parcel.readString();
                        int i7 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        partitionMixed(string11, i7);
                        parcel2.writeNoException();
                        break;
                    case 54:
                        String string12 = parcel.readString();
                        String string13 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        setVolumeNickname(string12, string13);
                        parcel2.writeNoException();
                        break;
                    case 55:
                        String string14 = parcel.readString();
                        int i8 = parcel.readInt();
                        int i9 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        setVolumeUserFlags(string14, i8, i9);
                        parcel2.writeNoException();
                        break;
                    case 56:
                        String string15 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        forgetVolume(string15);
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
                        String string16 = parcel.readString();
                        IPackageMoveObserver iPackageMoveObserverAsInterface = IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        setPrimaryStorageUuid(string16, iPackageMoveObserverAsInterface);
                        parcel2.writeNoException();
                        break;
                    case 60:
                        String string17 = parcel.readString();
                        IVoldTaskListener iVoldTaskListenerAsInterface = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                        parcel.enforceNoDataAvail();
                        benchmark(string17, iVoldTaskListenerAsInterface);
                        parcel2.writeNoException();
                        break;
                    case 61:
                        int i10 = parcel.readInt();
                        int i11 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        setDebugFlags(i10, i11);
                        parcel2.writeNoException();
                        break;
                    case 62:
                        int i12 = parcel.readInt();
                        boolean z3 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        createUserStorageKeys(i12, z3);
                        parcel2.writeNoException();
                        break;
                    case 63:
                        int i13 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        destroyUserStorageKeys(i13);
                        parcel2.writeNoException();
                        break;
                    case 64:
                        int i14 = parcel.readInt();
                        byte[] bArrCreateByteArray = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        unlockCeStorage(i14, bArrCreateByteArray);
                        parcel2.writeNoException();
                        break;
                    case 65:
                        int i15 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        lockCeStorage(i15);
                        parcel2.writeNoException();
                        break;
                    case 66:
                        int i16 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        boolean zIsCeStorageUnlocked = isCeStorageUnlocked(i16);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(zIsCeStorageUnlocked);
                        break;
                    case 67:
                        String string18 = parcel.readString();
                        int i17 = parcel.readInt();
                        int i18 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        prepareUserStorage(string18, i17, i18);
                        parcel2.writeNoException();
                        break;
                    case 68:
                        String string19 = parcel.readString();
                        int i19 = parcel.readInt();
                        int i20 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        destroyUserStorage(string19, i19, i20);
                        parcel2.writeNoException();
                        break;
                    case 71:
                        int i21 = parcel.readInt();
                        byte[] bArrCreateByteArray2 = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        setCeStorageProtection(i21, bArrCreateByteArray2);
                        parcel2.writeNoException();
                        break;
                    case 202:
                        String string20 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        String volumeState = getVolumeState(string20);
                        parcel2.writeNoException();
                        parcel2.writeString(volumeState);
                        break;
                    case 203:
                        String string21 = parcel.readString();
                        int i22 = parcel.readInt();
                        String string22 = parcel.readString();
                        String string23 = parcel.readString();
                        int i23 = parcel.readInt();
                        boolean z4 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int iCreateSecureContainer = createSecureContainer(string21, i22, string22, string23, i23, z4);
                        parcel2.writeNoException();
                        parcel2.writeInt(iCreateSecureContainer);
                        break;
                    case 204:
                        String string24 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int iFinalizeSecureContainer = finalizeSecureContainer(string24);
                        parcel2.writeNoException();
                        parcel2.writeInt(iFinalizeSecureContainer);
                        break;
                    case 205:
                        String string25 = parcel.readString();
                        boolean z5 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int iDestroySecureContainer = destroySecureContainer(string25, z5);
                        parcel2.writeNoException();
                        parcel2.writeInt(iDestroySecureContainer);
                        break;
                    case 206:
                        String string26 = parcel.readString();
                        String string27 = parcel.readString();
                        int i24 = parcel.readInt();
                        boolean z6 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int iMountSecureContainer = mountSecureContainer(string26, string27, i24, z6);
                        parcel2.writeNoException();
                        parcel2.writeInt(iMountSecureContainer);
                        break;
                    case 207:
                        String string28 = parcel.readString();
                        boolean z7 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        int iUnmountSecureContainer = unmountSecureContainer(string28, z7);
                        parcel2.writeNoException();
                        parcel2.writeInt(iUnmountSecureContainer);
                        break;
                    case 208:
                        String string29 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        boolean zIsSecureContainerMounted = isSecureContainerMounted(string29);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(zIsSecureContainerMounted);
                        break;
                    case 209:
                        String string30 = parcel.readString();
                        String string31 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int iRenameSecureContainer = renameSecureContainer(string30, string31);
                        parcel2.writeNoException();
                        parcel2.writeInt(iRenameSecureContainer);
                        break;
                    case 210:
                        String string32 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        String secureContainerPath = getSecureContainerPath(string32);
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
                        String string33 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        String secureContainerFilesystemPath = getSecureContainerFilesystemPath(string33);
                        parcel2.writeNoException();
                        parcel2.writeString(secureContainerFilesystemPath);
                        break;
                    case 214:
                        String string34 = parcel.readString();
                        int i25 = parcel.readInt();
                        String string35 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int iFixPermissionsSecureContainer = fixPermissionsSecureContainer(string34, i25, string35);
                        parcel2.writeNoException();
                        parcel2.writeInt(iFixPermissionsSecureContainer);
                        break;
                    case 215:
                        String string36 = parcel.readString();
                        int i26 = parcel.readInt();
                        String string37 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int iResizeSecureContainer = resizeSecureContainer(string36, i26, string37);
                        parcel2.writeNoException();
                        parcel2.writeInt(iResizeSecureContainer);
                        break;
                    case 216:
                        waitForAsecScan();
                        parcel2.writeNoException();
                        break;
                    case 217:
                        String string38 = parcel.readString();
                        int i27 = parcel.readInt();
                        String string39 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int iTrimSecureContainer = trimSecureContainer(string38, i27, string39);
                        parcel2.writeNoException();
                        parcel2.writeInt(iTrimSecureContainer);
                        break;
                    case 218:
                        String string40 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        int usedSpaceSecureContainer = getUsedSpaceSecureContainer(string40);
                        parcel2.writeNoException();
                        parcel2.writeInt(usedSpaceSecureContainer);
                        break;
                    case 223:
                        int iCreatePassStorage = createPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(iCreatePassStorage);
                        break;
                    case 224:
                        int iDestroyPassStorage = destroyPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(iDestroyPassStorage);
                        break;
                    case 225:
                        int iUnlockPassStorage = unlockPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(iUnlockPassStorage);
                        break;
                    case 226:
                        int iLockPassStorage = lockPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeInt(iLockPassStorage);
                        break;
                    case 227:
                        String passStorage = getPassStorage();
                        parcel2.writeNoException();
                        parcel2.writeString(passStorage);
                        break;
                    case 229:
                        boolean zIsPassUnlocked = isPassUnlocked();
                        parcel2.writeNoException();
                        parcel2.writeBoolean(zIsPassUnlocked);
                        break;
                    case 230:
                        boolean zIsPassSupport = isPassSupport();
                        parcel2.writeNoException();
                        parcel2.writeBoolean(zIsPassSupport);
                        break;
                    case 261:
                        long j = parcel.readLong();
                        parcel.enforceNoDataAvail();
                        boolean zShrinkDataDdp = shrinkDataDdp(j);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(zShrinkDataDdp);
                        break;
                    case 262:
                        long j2 = parcel.readLong();
                        parcel.enforceNoDataAvail();
                        int iReserveDataBlocks = reserveDataBlocks(j2);
                        parcel2.writeNoException();
                        parcel2.writeInt(iReserveDataBlocks);
                        break;
                    default:
                        switch (i) {
                            case 22:
                                String string41 = parcel.readString();
                                String string42 = parcel.readString();
                                IObbActionListener iObbActionListenerAsInterface = IObbActionListener.Stub.asInterface(parcel.readStrongBinder());
                                int i28 = parcel.readInt();
                                ObbInfo obbInfo = (ObbInfo) parcel.readTypedObject(ObbInfo.CREATOR);
                                parcel.enforceNoDataAvail();
                                mountObb(string41, string42, iObbActionListenerAsInterface, i28, obbInfo);
                                parcel2.writeNoException();
                                break;
                            case 23:
                                String string43 = parcel.readString();
                                boolean z8 = parcel.readBoolean();
                                IObbActionListener iObbActionListenerAsInterface2 = IObbActionListener.Stub.asInterface(parcel.readStrongBinder());
                                int i29 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                unmountObb(string43, z8, iObbActionListenerAsInterface2, i29);
                                parcel2.writeNoException();
                                break;
                            case 24:
                                String string44 = parcel.readString();
                                parcel.enforceNoDataAvail();
                                boolean zIsObbMounted = isObbMounted(string44);
                                parcel2.writeNoException();
                                parcel2.writeBoolean(zIsObbMounted);
                                break;
                            case 25:
                                String string45 = parcel.readString();
                                parcel.enforceNoDataAvail();
                                String mountedObbPath = getMountedObbPath(string45);
                                parcel2.writeNoException();
                                parcel2.writeString(mountedObbPath);
                                break;
                            default:
                                switch (i) {
                                    case 73:
                                        int i30 = parcel.readInt();
                                        IVoldTaskListener iVoldTaskListenerAsInterface2 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                                        parcel.enforceNoDataAvail();
                                        fstrim(i30, iVoldTaskListenerAsInterface2);
                                        parcel2.writeNoException();
                                        break;
                                    case 74:
                                        AppFuseMount appFuseMountMountProxyFileDescriptorBridge = mountProxyFileDescriptorBridge();
                                        parcel2.writeNoException();
                                        parcel2.writeTypedObject(appFuseMountMountProxyFileDescriptorBridge, 1);
                                        break;
                                    case 75:
                                        int i31 = parcel.readInt();
                                        int i32 = parcel.readInt();
                                        int i33 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        ParcelFileDescriptor parcelFileDescriptorOpenProxyFileDescriptor = openProxyFileDescriptor(i31, i32, i33);
                                        parcel2.writeNoException();
                                        parcel2.writeTypedObject(parcelFileDescriptorOpenProxyFileDescriptor, 1);
                                        break;
                                    case 76:
                                        String string46 = parcel.readString();
                                        int i34 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        long cacheQuotaBytes = getCacheQuotaBytes(string46, i34);
                                        parcel2.writeNoException();
                                        parcel2.writeLong(cacheQuotaBytes);
                                        break;
                                    case 77:
                                        String string47 = parcel.readString();
                                        int i35 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        long cacheSizeBytes = getCacheSizeBytes(string47, i35);
                                        parcel2.writeNoException();
                                        parcel2.writeLong(cacheSizeBytes);
                                        break;
                                    case 78:
                                        String string48 = parcel.readString();
                                        int i36 = parcel.readInt();
                                        String string49 = parcel.readString();
                                        parcel.enforceNoDataAvail();
                                        long allocatableBytes = getAllocatableBytes(string48, i36, string49);
                                        parcel2.writeNoException();
                                        parcel2.writeLong(allocatableBytes);
                                        break;
                                    case 79:
                                        String string50 = parcel.readString();
                                        long j3 = parcel.readLong();
                                        int i37 = parcel.readInt();
                                        String string51 = parcel.readString();
                                        parcel.enforceNoDataAvail();
                                        allocateBytes(string50, j3, i37, string51);
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
                                                boolean zSupportsCheckpoint = supportsCheckpoint();
                                                parcel2.writeNoException();
                                                parcel2.writeBoolean(zSupportsCheckpoint);
                                                break;
                                            case 86:
                                                int i38 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                startCheckpoint(i38);
                                                parcel2.writeNoException();
                                                break;
                                            case 87:
                                                boolean zNeedsCheckpoint = needsCheckpoint();
                                                parcel2.writeNoException();
                                                parcel2.writeBoolean(zNeedsCheckpoint);
                                                break;
                                            case 88:
                                                String string52 = parcel.readString();
                                                boolean z9 = parcel.readBoolean();
                                                parcel.enforceNoDataAvail();
                                                abortChanges(string52, z9);
                                                parcel2.writeNoException();
                                                break;
                                            default:
                                                switch (i) {
                                                    case 90:
                                                        String string53 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        fixupAppDir(string53);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 91:
                                                        String string54 = parcel.readString();
                                                        int i39 = parcel.readInt();
                                                        int i40 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        disableAppDataIsolation(string54, i39, i40);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 92:
                                                        String string55 = parcel.readString();
                                                        int i41 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        PendingIntent manageSpaceActivityIntent = getManageSpaceActivityIntent(string55, i41);
                                                        parcel2.writeNoException();
                                                        parcel2.writeTypedObject(manageSpaceActivityIntent, 1);
                                                        break;
                                                    case 93:
                                                        String string56 = parcel.readString();
                                                        int i42 = parcel.readInt();
                                                        int i43 = parcel.readInt();
                                                        int i44 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        notifyAppIoBlocked(string56, i42, i43, i44);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 94:
                                                        String string57 = parcel.readString();
                                                        int i45 = parcel.readInt();
                                                        int i46 = parcel.readInt();
                                                        int i47 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        notifyAppIoResumed(string57, i45, i46, i47);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 95:
                                                        int i48 = parcel.readInt();
                                                        String string58 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        int externalStorageMountMode = getExternalStorageMountMode(i48, string58);
                                                        parcel2.writeNoException();
                                                        parcel2.writeInt(externalStorageMountMode);
                                                        break;
                                                    case 96:
                                                        String string59 = parcel.readString();
                                                        int i49 = parcel.readInt();
                                                        int i50 = parcel.readInt();
                                                        int i51 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean zIsAppIoBlocked = isAppIoBlocked(string59, i49, i50, i51);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(zIsAppIoBlocked);
                                                        break;
                                                    case 97:
                                                        String string60 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        setCloudMediaProvider(string60);
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
                                                                int i52 = parcel.readInt();
                                                                String string61 = parcel.readString();
                                                                parcel.enforceNoDataAvail();
                                                                boolean sensitive = setSensitive(i52, string61);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(sensitive);
                                                                break;
                                                            case 112:
                                                                String string62 = parcel.readString();
                                                                parcel.enforceNoDataAvail();
                                                                boolean zIsSensitive = isSensitive(string62);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(zIsSensitive);
                                                                break;
                                                            case 113:
                                                                int i53 = parcel.readInt();
                                                                parcel.enforceNoDataAvail();
                                                                boolean zMountSdpMediaStorageCmd = mountSdpMediaStorageCmd(i53);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(zMountSdpMediaStorageCmd);
                                                                break;
                                                            case 114:
                                                                int i54 = parcel.readInt();
                                                                parcel.enforceNoDataAvail();
                                                                boolean sdpPolicyCmd = setSdpPolicyCmd(i54);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(sdpPolicyCmd);
                                                                break;
                                                            case 115:
                                                                int i55 = parcel.readInt();
                                                                String string63 = parcel.readString();
                                                                parcel.enforceNoDataAvail();
                                                                boolean sdpPolicyToPathCmd = setSdpPolicyToPathCmd(i55, string63);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(sdpPolicyToPathCmd);
                                                                break;
                                                            case 116:
                                                                int i56 = parcel.readInt();
                                                                int i57 = parcel.readInt();
                                                                parcel.enforceNoDataAvail();
                                                                boolean dualDARPolicyCmd = setDualDARPolicyCmd(i56, i57);
                                                                parcel2.writeNoException();
                                                                parcel2.writeBoolean(dualDARPolicyCmd);
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 151:
                                                                        int iSemGetExternalSdCardHealthState = semGetExternalSdCardHealthState();
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeInt(iSemGetExternalSdCardHealthState);
                                                                        break;
                                                                    case 152:
                                                                        String strSemGetExternalSdCardId = semGetExternalSdCardId();
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeString(strSemGetExternalSdCardId);
                                                                        break;
                                                                    case 153:
                                                                        long usedF2fsFileNode = getUsedF2fsFileNode();
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeLong(usedF2fsFileNode);
                                                                        break;
                                                                    case 154:
                                                                        String string64 = parcel.readString();
                                                                        String string65 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        boolean zMvFileAtData = mvFileAtData(string64, string65);
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeBoolean(zMvFileAtData);
                                                                        break;
                                                                    case 155:
                                                                        String string66 = parcel.readString();
                                                                        String string67 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        boolean zCpFileAtData = cpFileAtData(string66, string67);
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeBoolean(zCpFileAtData);
                                                                        break;
                                                                    case 156:
                                                                        String string68 = parcel.readString();
                                                                        String string69 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        mountBySecApp(string68, string69);
                                                                        parcel2.writeNoException();
                                                                        break;
                                                                    case 157:
                                                                        String string70 = parcel.readString();
                                                                        String string71 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        unmountBySecApp(string70, string71);
                                                                        parcel2.writeNoException();
                                                                        break;
                                                                    case 158:
                                                                        String string72 = parcel.readString();
                                                                        String string73 = parcel.readString();
                                                                        parcel.enforceNoDataAvail();
                                                                        formatBySecApp(string72, string73);
                                                                        parcel2.writeNoException();
                                                                        break;
                                                                    case 159:
                                                                        boolean z10 = parcel.readBoolean();
                                                                        parcel.enforceNoDataAvail();
                                                                        int iEncryptExternalStorage = encryptExternalStorage(z10);
                                                                        parcel2.writeNoException();
                                                                        parcel2.writeInt(iEncryptExternalStorage);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStorageEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStorageEventListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int mountVolume(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountVolume(String str, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStorageShutdownObserver);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountObb(String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iObbActionListener);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(obbInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iObbActionListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isObbMounted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getMountedObbPath(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StorageVolume[]) parcelObtain2.createTypedArray(StorageVolume.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mkdirs(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long lastMaintenance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runMaintenance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public DiskInfo[] getDisks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DiskInfo[]) parcelObtain2.createTypedArray(DiskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public VolumeInfo[] getVolumes(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VolumeInfo[]) parcelObtain2.createTypedArray(VolumeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public VolumeRecord[] getVolumeRecords(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VolumeRecord[]) parcelObtain2.createTypedArray(VolumeRecord.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void format(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPublic(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPrivate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionMixed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeNickname(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeUserFlags(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetVolume(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetAllVolumes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getPrimaryStorageUuid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setDebugFlags(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void createUserStorageKeys(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorageKeys(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unlockCeStorage(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void lockCeStorage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isCeStorageUnlocked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void prepareUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCeStorageProtection(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AppFuseMount) parcelObtain2.readTypedObject(AppFuseMount.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheQuotaBytes(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheSizeBytes(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getAllocatableBytes(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void allocateBytes(String str, long j, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runIdleMaintenance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortIdleMaintenance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void commitChanges() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean supportsCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void startCheckpoint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean needsCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortChanges(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fixupAppDir(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void disableAppDataIsolation(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public PendingIntent getManageSpaceActivityIntent(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PendingIntent) parcelObtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoResumed(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getExternalStorageMountMode(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isAppIoBlocked(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCloudMediaProvider(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getCloudMediaProvider() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getInternalStorageBlockDeviceSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getInternalStorageRemainingLifetime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSensitive(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isSensitive(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean mountSdpMediaStorageCmd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSdpPolicyCmd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int semGetExternalSdCardHealthState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String semGetExternalSdCardId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getUsedF2fsFileNode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean mvFileAtData(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean cpFileAtData(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountBySecApp(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountBySecApp(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void formatBySecApp(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int encryptExternalStorage(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getVolumeState(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int finalizeSecureContainer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int destroySecureContainer(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int unmountSecureContainer(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isSecureContainerMounted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int renameSecureContainer(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getSecureContainerPath(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String[] getSecureContainerList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void finishMediaUpdate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getSecureContainerFilesystemPath(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int resizeSecureContainer(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void waitForAsecScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int trimSecureContainer(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getUsedSpaceSecureContainer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int createPassStorage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int destroyPassStorage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int unlockPassStorage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int lockPassStorage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public String getPassStorage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(227, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isPassUnlocked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isPassSupport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean shrinkDataDdp(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(261, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int reserveDataBlocks(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(262, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
