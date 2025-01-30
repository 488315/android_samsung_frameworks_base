package android.p009os;

import android.hardware.usb.UsbManager;
import android.p009os.IVoldListener;
import android.p009os.IVoldMountCallback;
import android.p009os.IVoldTaskListener;
import android.p009os.incremental.IncrementalFileSystemControlParcel;
import android.provider.Telephony;
import com.samsung.android.media.AudioParameter;
import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public interface IVold extends IInterface {
    public static final int DDP_NG_ENOSPC = 2;
    public static final int DDP_NG_TOOBIG = 1;
    public static final int DDP_NG_UNKNOWN = -1;
    public static final int DDP_OK = 0;
    public static final int FSTRIM_FLAG_DEEP_TRIM = 1;
    public static final int MOUNT_FLAG_PRIMARY = 1;
    public static final int MOUNT_FLAG_VISIBLE_FOR_READ = 2;
    public static final int MOUNT_FLAG_VISIBLE_FOR_WRITE = 4;
    public static final int PARTITION_TYPE_MIXED = 2;
    public static final int PARTITION_TYPE_PRIVATE = 1;
    public static final int PARTITION_TYPE_PUBLIC = 0;
    public static final int REMOUNT_MODE_ANDROID_WRITABLE = 4;
    public static final int REMOUNT_MODE_DEFAULT = 1;
    public static final int REMOUNT_MODE_INSTALLER = 2;
    public static final int REMOUNT_MODE_NONE = 0;
    public static final int REMOUNT_MODE_PASS_THROUGH = 3;
    public static final int STORAGE_FLAG_CE = 2;
    public static final int STORAGE_FLAG_DE = 1;
    public static final int VOLUME_STATE_BAD_REMOVAL = 8;
    public static final int VOLUME_STATE_CHECKING = 1;
    public static final int VOLUME_STATE_EJECTING = 5;
    public static final int VOLUME_STATE_FORMATTING = 4;
    public static final int VOLUME_STATE_MOUNTED = 2;
    public static final int VOLUME_STATE_MOUNTED_READ_ONLY = 3;
    public static final int VOLUME_STATE_REMOVED = 7;
    public static final int VOLUME_STATE_UNMOUNTABLE = 6;
    public static final int VOLUME_STATE_UNMOUNTED = 0;
    public static final int VOLUME_TYPE_ASEC = 3;
    public static final int VOLUME_TYPE_EMULATED = 2;
    public static final int VOLUME_TYPE_OBB = 4;
    public static final int VOLUME_TYPE_PRIVATE = 1;
    public static final int VOLUME_TYPE_PUBLIC = 0;
    public static final int VOLUME_TYPE_STUB = 5;

    void abortChanges(String str, boolean z) throws RemoteException;

    void abortFuse() throws RemoteException;

    void abortIdleMaint(IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void addAppIds(String[] strArr, int[] iArr) throws RemoteException;

    void addSandboxIds(int[] iArr, String[] strArr) throws RemoteException;

    void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException;

    void asecDestroy(String str, boolean z) throws RemoteException;

    void asecFinalize(String str) throws RemoteException;

    void asecFixperms(String str, int i, String str2) throws RemoteException;

    String asecFsPath(String str) throws RemoteException;

    int asecGetUsedSpace(String str) throws RemoteException;

    String[] asecList() throws RemoteException;

    void asecMount(String str, String str2, int i, boolean z) throws RemoteException;

    String asecPath(String str) throws RemoteException;

    void asecRename(String str, String str2) throws RemoteException;

    void asecResize(String str, int i, String str2) throws RemoteException;

    void asecTrim(String str, int i, String str2) throws RemoteException;

    void asecUnmount(String str, boolean z) throws RemoteException;

    void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void bindMount(String str, String str2) throws RemoteException;

    void commitChanges() throws RemoteException;

    void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    String createObb(String str, int i) throws RemoteException;

    String createStubVolume(String str, String str2, String str3, String str4, String str5, int i) throws RemoteException;

    void createUserKey(int i, int i2, boolean z) throws RemoteException;

    void destroyDsuMetadataKey(String str) throws RemoteException;

    void destroyObb(String str) throws RemoteException;

    void destroySandboxForApp(String str, String str2, int i) throws RemoteException;

    void destroyStubVolume(String str) throws RemoteException;

    void destroyUserKey(int i) throws RemoteException;

    void destroyUserStorage(String str, int i, int i2) throws RemoteException;

    void earlyBootEnded() throws RemoteException;

    void encryptFstab(String str, String str2, boolean z, String str3, String str4) throws RemoteException;

    void ensureAppDirsCreated(String[] strArr, int i) throws RemoteException;

    void fbeEnable() throws RemoteException;

    void fixupAppDir(String str, int i) throws RemoteException;

    void forgetPartition(String str, String str2) throws RemoteException;

    void format(String str, String str2) throws RemoteException;

    void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    int getStorageLifeTime() throws RemoteException;

    int[] getUnlockedUsers() throws RemoteException;

    long getUsedF2fsFileNode() throws RemoteException;

    int getWriteAmount() throws RemoteException;

    boolean incFsEnabled() throws RemoteException;

    void initUser0() throws RemoteException;

    boolean isCheckpointing() throws RemoteException;

    boolean isSensitive(String str) throws RemoteException;

    void lockUserKey(int i) throws RemoteException;

    void markBootAttempt() throws RemoteException;

    void monitor() throws RemoteException;

    void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) throws RemoteException;

    FileDescriptor mountAppFuse(int i, int i2) throws RemoteException;

    void mountFstab(String str, String str2, String str3) throws RemoteException;

    IncrementalFileSystemControlParcel mountIncFs(String str, String str2, int i, String str3) throws RemoteException;

    boolean mountSdpMediaStorageCmd(int i) throws RemoteException;

    void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void mvFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    boolean needsCheckpoint() throws RemoteException;

    boolean needsRollback() throws RemoteException;

    void onSecureKeyguardStateChanged(boolean z) throws RemoteException;

    void onUserAdded(int i, int i2, int i3) throws RemoteException;

    void onUserRemoved(int i) throws RemoteException;

    void onUserStarted(int i) throws RemoteException;

    void onUserStopped(int i) throws RemoteException;

    FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws RemoteException;

    void partition(String str, int i, int i2) throws RemoteException;

    void prepareCheckpoint() throws RemoteException;

    void prepareSandboxForApp(String str, int i, String str2, int i2) throws RemoteException;

    void prepareUserStorage(String str, int i, int i2, int i3) throws RemoteException;

    void refreshLatestWrite() throws RemoteException;

    void remountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException;

    void remountUid(int i, int i2) throws RemoteException;

    int reserveDataBlocks(long j) throws RemoteException;

    void reset() throws RemoteException;

    void resetCheckpoint() throws RemoteException;

    void restoreCheckpoint(String str) throws RemoteException;

    void restoreCheckpointPart(String str, int i) throws RemoteException;

    void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void sdeEnable(String str, int i, int i2, boolean z, IVoldTaskListener iVoldTaskListener, IVoldMountCallback iVoldMountCallback) throws RemoteException;

    void sdeMoveMountHidden(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) throws RemoteException;

    void setDebugForExternal(String str) throws RemoteException;

    boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException;

    void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws RemoteException;

    void setIncFsMountOptions(IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, String str) throws RemoteException;

    void setListener(IVoldListener iVoldListener) throws RemoteException;

    void setMpUidForFileSystem(int i) throws RemoteException;

    boolean setSdpPolicyCmd(int i) throws RemoteException;

    boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException;

    boolean setSensitive(int i, String str) throws RemoteException;

    void setStorageBindingSeed(byte[] bArr) throws RemoteException;

    void setUserKeyProtection(int i, String str) throws RemoteException;

    void setupAppDir(String str, int i) throws RemoteException;

    boolean shrinkDataDdp(long j) throws RemoteException;

    void shutdown() throws RemoteException;

    void startCheckpoint(int i) throws RemoteException;

    boolean supportsBlockCheckpoint() throws RemoteException;

    boolean supportsCheckpoint() throws RemoteException;

    boolean supportsFileCheckpoint() throws RemoteException;

    void unlockUserKey(int i, int i2, String str, String str2) throws RemoteException;

    void unmount(String str) throws RemoteException;

    void unmountAppFuse(int i, int i2) throws RemoteException;

    void unmountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException;

    void unmountIncFs(String str) throws RemoteException;

    public static class Default implements IVold {
        @Override // android.p009os.IVold
        public void setListener(IVoldListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void abortFuse() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void monitor() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void reset() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void shutdown() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void onUserAdded(int userId, int userSerial, int sharesStorageWithUserId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void onUserRemoved(int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void onUserStarted(int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void onUserStopped(int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void addAppIds(String[] packageNames, int[] appIds) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void addSandboxIds(int[] appIds, String[] sandboxIds) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void onSecureKeyguardStateChanged(boolean isShowing) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void partition(String diskId, int partitionType, int ratio) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void forgetPartition(String partGuid, String fsUuid) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void mount(String volId, int mountFlags, int mountUserId, IVoldMountCallback callback) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void unmount(String volId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void format(String volId, String fsType) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void moveStorage(String fromVolId, String toVolId, IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void remountUid(int uid, int remountMode) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void remountAppStorageDirs(int uid, int pid, String[] packageNames) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void unmountAppStorageDirs(int uid, int pid, String[] packageNames) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void setupAppDir(String path, int appUid) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void fixupAppDir(String path, int appUid) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void ensureAppDirsCreated(String[] paths, int appUid) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public String createObb(String sourcePath, int ownerGid) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public void destroyObb(String volId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void fstrim(int fstrimFlags, IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void runIdleMaint(boolean needGC, IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void abortIdleMaint(IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public int getStorageLifeTime() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IVold
        public void setGCUrgentPace(int neededSegments, int minSegmentThreshold, float dirtyReclaimRate, float reclaimWeight, int gcPeriod, int minGCSleepTime, int targetDirtyRatio) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void refreshLatestWrite() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public int getWriteAmount() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IVold
        public FileDescriptor mountAppFuse(int uid, int mountId) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public void unmountAppFuse(int uid, int mountId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void sdeEnable(String volId, int mountFlags, int mountUserId, boolean type, IVoldTaskListener listener, IVoldMountCallback callback) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void sdeMoveMountHidden(String volId, int mountFlags, int mountUserId, IVoldMountCallback callback) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void fbeEnable() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void initUser0() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void mountFstab(String blkDevice, String mountPoint, String zonedDevice) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void encryptFstab(String blkDevice, String mountPoint, boolean shouldFormat, String fsType, String zonedDevice) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void setStorageBindingSeed(byte[] seed) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void createUserKey(int userId, int userSerial, boolean ephemeral) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void destroyUserKey(int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void setUserKeyProtection(int userId, String secret) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public int[] getUnlockedUsers() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public void unlockUserKey(int userId, int userSerial, String token, String secret) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void lockUserKey(int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void setDebugForExternal(String debug) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void prepareUserStorage(String uuid, int userId, int userSerial, int storageFlags) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void destroyUserStorage(String uuid, int userId, int storageFlags) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void prepareSandboxForApp(String packageName, int appId, String sandboxId, int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void destroySandboxForApp(String packageName, String sandboxId, int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void startCheckpoint(int retry) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public boolean needsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean needsRollback() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean isCheckpointing() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public void abortChanges(String device, boolean retry) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void commitChanges() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void prepareCheckpoint() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void restoreCheckpoint(String device) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void restoreCheckpointPart(String device, int count) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void markBootAttempt() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public boolean supportsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean supportsBlockCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean supportsFileCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public void resetCheckpoint() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void earlyBootEnded() throws RemoteException {
        }

        @Override // android.p009os.IVold
        public String createStubVolume(String sourcePath, String mountPath, String fsType, String fsUuid, String fsLabel, int flags) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public void destroyStubVolume(String volId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public FileDescriptor openAppFuseFile(int uid, int mountId, int fileId, int flags) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public boolean incFsEnabled() throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public IncrementalFileSystemControlParcel mountIncFs(String backingPath, String targetDir, int flags, String sysfsName) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public void unmountIncFs(String dir) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void setIncFsMountOptions(IncrementalFileSystemControlParcel control, boolean enableReadLogs, boolean enableReadTimeouts, String sysfsName) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void bindMount(String sourceDir, String targetDir) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void destroyDsuMetadataKey(String dsuSlot) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public long getUsedF2fsFileNode() throws RemoteException {
            return 0L;
        }

        @Override // android.p009os.IVold
        public void mvFileAtData(String fromPath, String toPath, int mediaProviderUid, int callingAppUid, IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void cpFileAtData(String fromPath, String toPath, int mediaProviderUid, int callingAppUid, IVoldTaskListener listener) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void setMpUidForFileSystem(int userId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecCreate(String id, int sizeMb, String fstype, String key, int ownerUid, boolean external) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecResize(String id, int sizeMb, String key) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecFinalize(String id) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecFixperms(String id, int gid, String filename) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecDestroy(String id, boolean force) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecMount(String id, String key, int ownerUid, boolean readOnly) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecUnmount(String id, boolean force) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public void asecRename(String oldId, String newId) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public String asecPath(String id) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public String asecFsPath(String id) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public String[] asecList() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVold
        public void asecTrim(String id, int sizeMb, String key) throws RemoteException {
        }

        @Override // android.p009os.IVold
        public int asecGetUsedSpace(String id) throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IVold
        public boolean setSensitive(int engineId, String path) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean isSensitive(String path) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean mountSdpMediaStorageCmd(int userId) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean setSdpPolicyCmd(int userId) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean setSdpPolicyToPathCmd(int userId, String path) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean setDualDARPolicyCmd(int userId, int flags) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public boolean shrinkDataDdp(long superUsedSectors) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVold
        public int reserveDataBlocks(long superUsedSectors) throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVold {
        public static final String DESCRIPTOR = "android.os.IVold";
        static final int TRANSACTION_abortChanges = 59;
        static final int TRANSACTION_abortFuse = 2;
        static final int TRANSACTION_abortIdleMaint = 30;
        static final int TRANSACTION_addAppIds = 10;
        static final int TRANSACTION_addSandboxIds = 11;
        static final int TRANSACTION_asecCreate = 83;
        static final int TRANSACTION_asecDestroy = 87;
        static final int TRANSACTION_asecFinalize = 85;
        static final int TRANSACTION_asecFixperms = 86;
        static final int TRANSACTION_asecFsPath = 92;
        static final int TRANSACTION_asecGetUsedSpace = 95;
        static final int TRANSACTION_asecList = 93;
        static final int TRANSACTION_asecMount = 88;
        static final int TRANSACTION_asecPath = 91;
        static final int TRANSACTION_asecRename = 90;
        static final int TRANSACTION_asecResize = 84;
        static final int TRANSACTION_asecTrim = 94;
        static final int TRANSACTION_asecUnmount = 89;
        static final int TRANSACTION_benchmark = 18;
        static final int TRANSACTION_bindMount = 77;
        static final int TRANSACTION_commitChanges = 60;
        static final int TRANSACTION_cpFileAtData = 81;
        static final int TRANSACTION_createObb = 26;
        static final int TRANSACTION_createStubVolume = 70;
        static final int TRANSACTION_createUserKey = 44;
        static final int TRANSACTION_destroyDsuMetadataKey = 78;
        static final int TRANSACTION_destroyObb = 27;
        static final int TRANSACTION_destroySandboxForApp = 54;
        static final int TRANSACTION_destroyStubVolume = 71;
        static final int TRANSACTION_destroyUserKey = 45;
        static final int TRANSACTION_destroyUserStorage = 52;
        static final int TRANSACTION_earlyBootEnded = 69;
        static final int TRANSACTION_encryptFstab = 42;
        static final int TRANSACTION_ensureAppDirsCreated = 25;
        static final int TRANSACTION_fbeEnable = 39;
        static final int TRANSACTION_fixupAppDir = 24;
        static final int TRANSACTION_forgetPartition = 14;
        static final int TRANSACTION_format = 17;
        static final int TRANSACTION_fstrim = 28;
        static final int TRANSACTION_getStorageLifeTime = 31;
        static final int TRANSACTION_getUnlockedUsers = 47;
        static final int TRANSACTION_getUsedF2fsFileNode = 79;
        static final int TRANSACTION_getWriteAmount = 34;
        static final int TRANSACTION_incFsEnabled = 73;
        static final int TRANSACTION_initUser0 = 40;
        static final int TRANSACTION_isCheckpointing = 58;
        static final int TRANSACTION_isSensitive = 97;
        static final int TRANSACTION_lockUserKey = 49;
        static final int TRANSACTION_markBootAttempt = 64;
        static final int TRANSACTION_monitor = 3;
        static final int TRANSACTION_mount = 15;
        static final int TRANSACTION_mountAppFuse = 35;
        static final int TRANSACTION_mountFstab = 41;
        static final int TRANSACTION_mountIncFs = 74;
        static final int TRANSACTION_mountSdpMediaStorageCmd = 98;
        static final int TRANSACTION_moveStorage = 19;
        static final int TRANSACTION_mvFileAtData = 80;
        static final int TRANSACTION_needsCheckpoint = 56;
        static final int TRANSACTION_needsRollback = 57;
        static final int TRANSACTION_onSecureKeyguardStateChanged = 12;
        static final int TRANSACTION_onUserAdded = 6;
        static final int TRANSACTION_onUserRemoved = 7;
        static final int TRANSACTION_onUserStarted = 8;
        static final int TRANSACTION_onUserStopped = 9;
        static final int TRANSACTION_openAppFuseFile = 72;
        static final int TRANSACTION_partition = 13;
        static final int TRANSACTION_prepareCheckpoint = 61;
        static final int TRANSACTION_prepareSandboxForApp = 53;
        static final int TRANSACTION_prepareUserStorage = 51;
        static final int TRANSACTION_refreshLatestWrite = 33;
        static final int TRANSACTION_remountAppStorageDirs = 21;
        static final int TRANSACTION_remountUid = 20;
        static final int TRANSACTION_reserveDataBlocks = 103;
        static final int TRANSACTION_reset = 4;
        static final int TRANSACTION_resetCheckpoint = 68;
        static final int TRANSACTION_restoreCheckpoint = 62;
        static final int TRANSACTION_restoreCheckpointPart = 63;
        static final int TRANSACTION_runIdleMaint = 29;
        static final int TRANSACTION_sdeEnable = 37;
        static final int TRANSACTION_sdeMoveMountHidden = 38;
        static final int TRANSACTION_setDebugForExternal = 50;
        static final int TRANSACTION_setDualDARPolicyCmd = 101;
        static final int TRANSACTION_setGCUrgentPace = 32;
        static final int TRANSACTION_setIncFsMountOptions = 76;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setMpUidForFileSystem = 82;
        static final int TRANSACTION_setSdpPolicyCmd = 99;
        static final int TRANSACTION_setSdpPolicyToPathCmd = 100;
        static final int TRANSACTION_setSensitive = 96;
        static final int TRANSACTION_setStorageBindingSeed = 43;
        static final int TRANSACTION_setUserKeyProtection = 46;
        static final int TRANSACTION_setupAppDir = 23;
        static final int TRANSACTION_shrinkDataDdp = 102;
        static final int TRANSACTION_shutdown = 5;
        static final int TRANSACTION_startCheckpoint = 55;
        static final int TRANSACTION_supportsBlockCheckpoint = 66;
        static final int TRANSACTION_supportsCheckpoint = 65;
        static final int TRANSACTION_supportsFileCheckpoint = 67;
        static final int TRANSACTION_unlockUserKey = 48;
        static final int TRANSACTION_unmount = 16;
        static final int TRANSACTION_unmountAppFuse = 36;
        static final int TRANSACTION_unmountAppStorageDirs = 22;
        static final int TRANSACTION_unmountIncFs = 75;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVold asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVold)) {
                return (IVold) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setListener";
                case 2:
                    return "abortFuse";
                case 3:
                    return "monitor";
                case 4:
                    return "reset";
                case 5:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 6:
                    return "onUserAdded";
                case 7:
                    return "onUserRemoved";
                case 8:
                    return "onUserStarted";
                case 9:
                    return "onUserStopped";
                case 10:
                    return "addAppIds";
                case 11:
                    return "addSandboxIds";
                case 12:
                    return "onSecureKeyguardStateChanged";
                case 13:
                    return "partition";
                case 14:
                    return "forgetPartition";
                case 15:
                    return AudioParameter.VALUE_MOUNT;
                case 16:
                    return AudioParameter.VALUE_UNMOUNT;
                case 17:
                    return Telephony.CellBroadcasts.MESSAGE_FORMAT;
                case 18:
                    return "benchmark";
                case 19:
                    return "moveStorage";
                case 20:
                    return "remountUid";
                case 21:
                    return "remountAppStorageDirs";
                case 22:
                    return "unmountAppStorageDirs";
                case 23:
                    return "setupAppDir";
                case 24:
                    return "fixupAppDir";
                case 25:
                    return "ensureAppDirsCreated";
                case 26:
                    return "createObb";
                case 27:
                    return "destroyObb";
                case 28:
                    return "fstrim";
                case 29:
                    return "runIdleMaint";
                case 30:
                    return "abortIdleMaint";
                case 31:
                    return "getStorageLifeTime";
                case 32:
                    return "setGCUrgentPace";
                case 33:
                    return "refreshLatestWrite";
                case 34:
                    return "getWriteAmount";
                case 35:
                    return "mountAppFuse";
                case 36:
                    return "unmountAppFuse";
                case 37:
                    return "sdeEnable";
                case 38:
                    return "sdeMoveMountHidden";
                case 39:
                    return "fbeEnable";
                case 40:
                    return "initUser0";
                case 41:
                    return "mountFstab";
                case 42:
                    return "encryptFstab";
                case 43:
                    return "setStorageBindingSeed";
                case 44:
                    return "createUserKey";
                case 45:
                    return "destroyUserKey";
                case 46:
                    return "setUserKeyProtection";
                case 47:
                    return "getUnlockedUsers";
                case 48:
                    return "unlockUserKey";
                case 49:
                    return "lockUserKey";
                case 50:
                    return "setDebugForExternal";
                case 51:
                    return "prepareUserStorage";
                case 52:
                    return "destroyUserStorage";
                case 53:
                    return "prepareSandboxForApp";
                case 54:
                    return "destroySandboxForApp";
                case 55:
                    return "startCheckpoint";
                case 56:
                    return "needsCheckpoint";
                case 57:
                    return "needsRollback";
                case 58:
                    return "isCheckpointing";
                case 59:
                    return "abortChanges";
                case 60:
                    return "commitChanges";
                case 61:
                    return "prepareCheckpoint";
                case 62:
                    return "restoreCheckpoint";
                case 63:
                    return "restoreCheckpointPart";
                case 64:
                    return "markBootAttempt";
                case 65:
                    return "supportsCheckpoint";
                case 66:
                    return "supportsBlockCheckpoint";
                case 67:
                    return "supportsFileCheckpoint";
                case 68:
                    return "resetCheckpoint";
                case 69:
                    return "earlyBootEnded";
                case 70:
                    return "createStubVolume";
                case 71:
                    return "destroyStubVolume";
                case 72:
                    return "openAppFuseFile";
                case 73:
                    return "incFsEnabled";
                case 74:
                    return "mountIncFs";
                case 75:
                    return "unmountIncFs";
                case 76:
                    return "setIncFsMountOptions";
                case 77:
                    return "bindMount";
                case 78:
                    return "destroyDsuMetadataKey";
                case 79:
                    return "getUsedF2fsFileNode";
                case 80:
                    return "mvFileAtData";
                case 81:
                    return "cpFileAtData";
                case 82:
                    return "setMpUidForFileSystem";
                case 83:
                    return "asecCreate";
                case 84:
                    return "asecResize";
                case 85:
                    return "asecFinalize";
                case 86:
                    return "asecFixperms";
                case 87:
                    return "asecDestroy";
                case 88:
                    return "asecMount";
                case 89:
                    return "asecUnmount";
                case 90:
                    return "asecRename";
                case 91:
                    return "asecPath";
                case 92:
                    return "asecFsPath";
                case 93:
                    return "asecList";
                case 94:
                    return "asecTrim";
                case 95:
                    return "asecGetUsedSpace";
                case 96:
                    return "setSensitive";
                case 97:
                    return "isSensitive";
                case 98:
                    return "mountSdpMediaStorageCmd";
                case 99:
                    return "setSdpPolicyCmd";
                case 100:
                    return "setSdpPolicyToPathCmd";
                case 101:
                    return "setDualDARPolicyCmd";
                case 102:
                    return "shrinkDataDdp";
                case 103:
                    return "reserveDataBlocks";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            IVoldListener _arg0 = IVoldListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setListener(_arg0);
                            reply.writeNoException();
                            return true;
                        case 2:
                            abortFuse();
                            reply.writeNoException();
                            return true;
                        case 3:
                            monitor();
                            reply.writeNoException();
                            return true;
                        case 4:
                            reset();
                            reply.writeNoException();
                            return true;
                        case 5:
                            shutdown();
                            reply.writeNoException();
                            return true;
                        case 6:
                            int _arg02 = data.readInt();
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            onUserAdded(_arg02, _arg1, _arg2);
                            reply.writeNoException();
                            return true;
                        case 7:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            onUserRemoved(_arg03);
                            reply.writeNoException();
                            return true;
                        case 8:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            onUserStarted(_arg04);
                            reply.writeNoException();
                            return true;
                        case 9:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            onUserStopped(_arg05);
                            reply.writeNoException();
                            return true;
                        case 10:
                            String[] _arg06 = data.createStringArray();
                            int[] _arg12 = data.createIntArray();
                            data.enforceNoDataAvail();
                            addAppIds(_arg06, _arg12);
                            reply.writeNoException();
                            return true;
                        case 11:
                            int[] _arg07 = data.createIntArray();
                            String[] _arg13 = data.createStringArray();
                            data.enforceNoDataAvail();
                            addSandboxIds(_arg07, _arg13);
                            reply.writeNoException();
                            return true;
                        case 12:
                            boolean _arg08 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onSecureKeyguardStateChanged(_arg08);
                            reply.writeNoException();
                            return true;
                        case 13:
                            String _arg09 = data.readString();
                            int _arg14 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            partition(_arg09, _arg14, _arg22);
                            reply.writeNoException();
                            return true;
                        case 14:
                            String _arg010 = data.readString();
                            String _arg15 = data.readString();
                            data.enforceNoDataAvail();
                            forgetPartition(_arg010, _arg15);
                            reply.writeNoException();
                            return true;
                        case 15:
                            String _arg011 = data.readString();
                            int _arg16 = data.readInt();
                            int _arg23 = data.readInt();
                            IVoldMountCallback _arg3 = IVoldMountCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            mount(_arg011, _arg16, _arg23, _arg3);
                            reply.writeNoException();
                            return true;
                        case 16:
                            String _arg012 = data.readString();
                            data.enforceNoDataAvail();
                            unmount(_arg012);
                            reply.writeNoException();
                            return true;
                        case 17:
                            String _arg013 = data.readString();
                            String _arg17 = data.readString();
                            data.enforceNoDataAvail();
                            format(_arg013, _arg17);
                            reply.writeNoException();
                            return true;
                        case 18:
                            String _arg014 = data.readString();
                            IVoldTaskListener _arg18 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            benchmark(_arg014, _arg18);
                            reply.writeNoException();
                            return true;
                        case 19:
                            String _arg015 = data.readString();
                            String _arg19 = data.readString();
                            IVoldTaskListener _arg24 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            moveStorage(_arg015, _arg19, _arg24);
                            reply.writeNoException();
                            return true;
                        case 20:
                            int _arg016 = data.readInt();
                            int _arg110 = data.readInt();
                            data.enforceNoDataAvail();
                            remountUid(_arg016, _arg110);
                            reply.writeNoException();
                            return true;
                        case 21:
                            int _arg017 = data.readInt();
                            int _arg111 = data.readInt();
                            String[] _arg25 = data.createStringArray();
                            data.enforceNoDataAvail();
                            remountAppStorageDirs(_arg017, _arg111, _arg25);
                            reply.writeNoException();
                            return true;
                        case 22:
                            int _arg018 = data.readInt();
                            int _arg112 = data.readInt();
                            String[] _arg26 = data.createStringArray();
                            data.enforceNoDataAvail();
                            unmountAppStorageDirs(_arg018, _arg112, _arg26);
                            reply.writeNoException();
                            return true;
                        case 23:
                            String _arg019 = data.readString();
                            int _arg113 = data.readInt();
                            data.enforceNoDataAvail();
                            setupAppDir(_arg019, _arg113);
                            reply.writeNoException();
                            return true;
                        case 24:
                            String _arg020 = data.readString();
                            int _arg114 = data.readInt();
                            data.enforceNoDataAvail();
                            fixupAppDir(_arg020, _arg114);
                            reply.writeNoException();
                            return true;
                        case 25:
                            String[] _arg021 = data.createStringArray();
                            int _arg115 = data.readInt();
                            data.enforceNoDataAvail();
                            ensureAppDirsCreated(_arg021, _arg115);
                            reply.writeNoException();
                            return true;
                        case 26:
                            String _arg022 = data.readString();
                            int _arg116 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result = createObb(_arg022, _arg116);
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        case 27:
                            String _arg023 = data.readString();
                            data.enforceNoDataAvail();
                            destroyObb(_arg023);
                            reply.writeNoException();
                            return true;
                        case 28:
                            int _arg024 = data.readInt();
                            IVoldTaskListener _arg117 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            fstrim(_arg024, _arg117);
                            reply.writeNoException();
                            return true;
                        case 29:
                            boolean _arg025 = data.readBoolean();
                            IVoldTaskListener _arg118 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            runIdleMaint(_arg025, _arg118);
                            reply.writeNoException();
                            return true;
                        case 30:
                            IVoldTaskListener _arg026 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            abortIdleMaint(_arg026);
                            reply.writeNoException();
                            return true;
                        case 31:
                            int _result2 = getStorageLifeTime();
                            reply.writeNoException();
                            reply.writeInt(_result2);
                            return true;
                        case 32:
                            int _arg027 = data.readInt();
                            int _arg119 = data.readInt();
                            float _arg27 = data.readFloat();
                            float _arg32 = data.readFloat();
                            int _arg4 = data.readInt();
                            int _arg5 = data.readInt();
                            int _arg6 = data.readInt();
                            data.enforceNoDataAvail();
                            setGCUrgentPace(_arg027, _arg119, _arg27, _arg32, _arg4, _arg5, _arg6);
                            reply.writeNoException();
                            return true;
                        case 33:
                            refreshLatestWrite();
                            reply.writeNoException();
                            return true;
                        case 34:
                            int _result3 = getWriteAmount();
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        case 35:
                            int _arg028 = data.readInt();
                            int _arg120 = data.readInt();
                            data.enforceNoDataAvail();
                            FileDescriptor _result4 = mountAppFuse(_arg028, _arg120);
                            reply.writeNoException();
                            reply.writeRawFileDescriptor(_result4);
                            return true;
                        case 36:
                            int _arg029 = data.readInt();
                            int _arg121 = data.readInt();
                            data.enforceNoDataAvail();
                            unmountAppFuse(_arg029, _arg121);
                            reply.writeNoException();
                            return true;
                        case 37:
                            String _arg030 = data.readString();
                            int _arg122 = data.readInt();
                            int _arg28 = data.readInt();
                            boolean _arg33 = data.readBoolean();
                            IVoldTaskListener _arg42 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            IVoldMountCallback _arg52 = IVoldMountCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            sdeEnable(_arg030, _arg122, _arg28, _arg33, _arg42, _arg52);
                            reply.writeNoException();
                            return true;
                        case 38:
                            String _arg031 = data.readString();
                            int _arg123 = data.readInt();
                            int _arg29 = data.readInt();
                            IVoldMountCallback _arg34 = IVoldMountCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            sdeMoveMountHidden(_arg031, _arg123, _arg29, _arg34);
                            reply.writeNoException();
                            return true;
                        case 39:
                            fbeEnable();
                            reply.writeNoException();
                            return true;
                        case 40:
                            initUser0();
                            reply.writeNoException();
                            return true;
                        case 41:
                            String _arg032 = data.readString();
                            String _arg124 = data.readString();
                            String _arg210 = data.readString();
                            data.enforceNoDataAvail();
                            mountFstab(_arg032, _arg124, _arg210);
                            reply.writeNoException();
                            return true;
                        case 42:
                            String _arg033 = data.readString();
                            String _arg125 = data.readString();
                            boolean _arg211 = data.readBoolean();
                            String _arg35 = data.readString();
                            String _arg43 = data.readString();
                            data.enforceNoDataAvail();
                            encryptFstab(_arg033, _arg125, _arg211, _arg35, _arg43);
                            reply.writeNoException();
                            return true;
                        case 43:
                            byte[] _arg034 = data.createByteArray();
                            data.enforceNoDataAvail();
                            setStorageBindingSeed(_arg034);
                            reply.writeNoException();
                            return true;
                        case 44:
                            int _arg035 = data.readInt();
                            int _arg126 = data.readInt();
                            boolean _arg212 = data.readBoolean();
                            data.enforceNoDataAvail();
                            createUserKey(_arg035, _arg126, _arg212);
                            reply.writeNoException();
                            return true;
                        case 45:
                            int _arg036 = data.readInt();
                            data.enforceNoDataAvail();
                            destroyUserKey(_arg036);
                            reply.writeNoException();
                            return true;
                        case 46:
                            int _arg037 = data.readInt();
                            String _arg127 = data.readString();
                            data.enforceNoDataAvail();
                            setUserKeyProtection(_arg037, _arg127);
                            reply.writeNoException();
                            return true;
                        case 47:
                            int[] _result5 = getUnlockedUsers();
                            reply.writeNoException();
                            reply.writeIntArray(_result5);
                            return true;
                        case 48:
                            int _arg038 = data.readInt();
                            int _arg128 = data.readInt();
                            String _arg213 = data.readString();
                            String _arg36 = data.readString();
                            data.enforceNoDataAvail();
                            unlockUserKey(_arg038, _arg128, _arg213, _arg36);
                            reply.writeNoException();
                            return true;
                        case 49:
                            int _arg039 = data.readInt();
                            data.enforceNoDataAvail();
                            lockUserKey(_arg039);
                            reply.writeNoException();
                            return true;
                        case 50:
                            String _arg040 = data.readString();
                            data.enforceNoDataAvail();
                            setDebugForExternal(_arg040);
                            reply.writeNoException();
                            return true;
                        case 51:
                            String _arg041 = data.readString();
                            int _arg129 = data.readInt();
                            int _arg214 = data.readInt();
                            int _arg37 = data.readInt();
                            data.enforceNoDataAvail();
                            prepareUserStorage(_arg041, _arg129, _arg214, _arg37);
                            reply.writeNoException();
                            return true;
                        case 52:
                            String _arg042 = data.readString();
                            int _arg130 = data.readInt();
                            int _arg215 = data.readInt();
                            data.enforceNoDataAvail();
                            destroyUserStorage(_arg042, _arg130, _arg215);
                            reply.writeNoException();
                            return true;
                        case 53:
                            String _arg043 = data.readString();
                            int _arg131 = data.readInt();
                            String _arg216 = data.readString();
                            int _arg38 = data.readInt();
                            data.enforceNoDataAvail();
                            prepareSandboxForApp(_arg043, _arg131, _arg216, _arg38);
                            reply.writeNoException();
                            return true;
                        case 54:
                            String _arg044 = data.readString();
                            String _arg132 = data.readString();
                            int _arg217 = data.readInt();
                            data.enforceNoDataAvail();
                            destroySandboxForApp(_arg044, _arg132, _arg217);
                            reply.writeNoException();
                            return true;
                        case 55:
                            int _arg045 = data.readInt();
                            data.enforceNoDataAvail();
                            startCheckpoint(_arg045);
                            reply.writeNoException();
                            return true;
                        case 56:
                            boolean _result6 = needsCheckpoint();
                            reply.writeNoException();
                            reply.writeBoolean(_result6);
                            return true;
                        case 57:
                            boolean _result7 = needsRollback();
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 58:
                            boolean _result8 = isCheckpointing();
                            reply.writeNoException();
                            reply.writeBoolean(_result8);
                            return true;
                        case 59:
                            String _arg046 = data.readString();
                            boolean _arg133 = data.readBoolean();
                            data.enforceNoDataAvail();
                            abortChanges(_arg046, _arg133);
                            reply.writeNoException();
                            return true;
                        case 60:
                            commitChanges();
                            reply.writeNoException();
                            return true;
                        case 61:
                            prepareCheckpoint();
                            reply.writeNoException();
                            return true;
                        case 62:
                            String _arg047 = data.readString();
                            data.enforceNoDataAvail();
                            restoreCheckpoint(_arg047);
                            reply.writeNoException();
                            return true;
                        case 63:
                            String _arg048 = data.readString();
                            int _arg134 = data.readInt();
                            data.enforceNoDataAvail();
                            restoreCheckpointPart(_arg048, _arg134);
                            reply.writeNoException();
                            return true;
                        case 64:
                            markBootAttempt();
                            reply.writeNoException();
                            return true;
                        case 65:
                            boolean _result9 = supportsCheckpoint();
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 66:
                            boolean _result10 = supportsBlockCheckpoint();
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 67:
                            boolean _result11 = supportsFileCheckpoint();
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 68:
                            resetCheckpoint();
                            reply.writeNoException();
                            return true;
                        case 69:
                            earlyBootEnded();
                            reply.writeNoException();
                            return true;
                        case 70:
                            String _arg049 = data.readString();
                            String _arg135 = data.readString();
                            String _arg218 = data.readString();
                            String _arg39 = data.readString();
                            String _arg44 = data.readString();
                            int _arg53 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result12 = createStubVolume(_arg049, _arg135, _arg218, _arg39, _arg44, _arg53);
                            reply.writeNoException();
                            reply.writeString(_result12);
                            return true;
                        case 71:
                            String _arg050 = data.readString();
                            data.enforceNoDataAvail();
                            destroyStubVolume(_arg050);
                            reply.writeNoException();
                            return true;
                        case 72:
                            int _arg051 = data.readInt();
                            int _arg136 = data.readInt();
                            int _arg219 = data.readInt();
                            int _arg310 = data.readInt();
                            data.enforceNoDataAvail();
                            FileDescriptor _result13 = openAppFuseFile(_arg051, _arg136, _arg219, _arg310);
                            reply.writeNoException();
                            reply.writeRawFileDescriptor(_result13);
                            return true;
                        case 73:
                            boolean _result14 = incFsEnabled();
                            reply.writeNoException();
                            reply.writeBoolean(_result14);
                            return true;
                        case 74:
                            String _arg052 = data.readString();
                            String _arg137 = data.readString();
                            int _arg220 = data.readInt();
                            String _arg311 = data.readString();
                            data.enforceNoDataAvail();
                            IncrementalFileSystemControlParcel _result15 = mountIncFs(_arg052, _arg137, _arg220, _arg311);
                            reply.writeNoException();
                            reply.writeTypedObject(_result15, 1);
                            return true;
                        case 75:
                            String _arg053 = data.readString();
                            data.enforceNoDataAvail();
                            unmountIncFs(_arg053);
                            reply.writeNoException();
                            return true;
                        case 76:
                            IncrementalFileSystemControlParcel _arg054 = (IncrementalFileSystemControlParcel) data.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                            boolean _arg138 = data.readBoolean();
                            boolean _arg221 = data.readBoolean();
                            String _arg312 = data.readString();
                            data.enforceNoDataAvail();
                            setIncFsMountOptions(_arg054, _arg138, _arg221, _arg312);
                            reply.writeNoException();
                            return true;
                        case 77:
                            String _arg055 = data.readString();
                            String _arg139 = data.readString();
                            data.enforceNoDataAvail();
                            bindMount(_arg055, _arg139);
                            reply.writeNoException();
                            return true;
                        case 78:
                            String _arg056 = data.readString();
                            data.enforceNoDataAvail();
                            destroyDsuMetadataKey(_arg056);
                            reply.writeNoException();
                            return true;
                        case 79:
                            long _result16 = getUsedF2fsFileNode();
                            reply.writeNoException();
                            reply.writeLong(_result16);
                            return true;
                        case 80:
                            String _arg057 = data.readString();
                            String _arg140 = data.readString();
                            int _arg222 = data.readInt();
                            int _arg313 = data.readInt();
                            IVoldTaskListener _arg45 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            mvFileAtData(_arg057, _arg140, _arg222, _arg313, _arg45);
                            reply.writeNoException();
                            return true;
                        case 81:
                            String _arg058 = data.readString();
                            String _arg141 = data.readString();
                            int _arg223 = data.readInt();
                            int _arg314 = data.readInt();
                            IVoldTaskListener _arg46 = IVoldTaskListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            cpFileAtData(_arg058, _arg141, _arg223, _arg314, _arg46);
                            reply.writeNoException();
                            return true;
                        case 82:
                            int _arg059 = data.readInt();
                            data.enforceNoDataAvail();
                            setMpUidForFileSystem(_arg059);
                            reply.writeNoException();
                            return true;
                        case 83:
                            String _arg060 = data.readString();
                            int _arg142 = data.readInt();
                            String _arg224 = data.readString();
                            String _arg315 = data.readString();
                            int _arg47 = data.readInt();
                            boolean _arg54 = data.readBoolean();
                            data.enforceNoDataAvail();
                            asecCreate(_arg060, _arg142, _arg224, _arg315, _arg47, _arg54);
                            reply.writeNoException();
                            return true;
                        case 84:
                            String _arg061 = data.readString();
                            int _arg143 = data.readInt();
                            String _arg225 = data.readString();
                            data.enforceNoDataAvail();
                            asecResize(_arg061, _arg143, _arg225);
                            reply.writeNoException();
                            return true;
                        case 85:
                            String _arg062 = data.readString();
                            data.enforceNoDataAvail();
                            asecFinalize(_arg062);
                            reply.writeNoException();
                            return true;
                        case 86:
                            String _arg063 = data.readString();
                            int _arg144 = data.readInt();
                            String _arg226 = data.readString();
                            data.enforceNoDataAvail();
                            asecFixperms(_arg063, _arg144, _arg226);
                            reply.writeNoException();
                            return true;
                        case 87:
                            String _arg064 = data.readString();
                            boolean _arg145 = data.readBoolean();
                            data.enforceNoDataAvail();
                            asecDestroy(_arg064, _arg145);
                            reply.writeNoException();
                            return true;
                        case 88:
                            String _arg065 = data.readString();
                            String _arg146 = data.readString();
                            int _arg227 = data.readInt();
                            boolean _arg316 = data.readBoolean();
                            data.enforceNoDataAvail();
                            asecMount(_arg065, _arg146, _arg227, _arg316);
                            reply.writeNoException();
                            return true;
                        case 89:
                            String _arg066 = data.readString();
                            boolean _arg147 = data.readBoolean();
                            data.enforceNoDataAvail();
                            asecUnmount(_arg066, _arg147);
                            reply.writeNoException();
                            return true;
                        case 90:
                            String _arg067 = data.readString();
                            String _arg148 = data.readString();
                            data.enforceNoDataAvail();
                            asecRename(_arg067, _arg148);
                            reply.writeNoException();
                            return true;
                        case 91:
                            String _arg068 = data.readString();
                            data.enforceNoDataAvail();
                            String _result17 = asecPath(_arg068);
                            reply.writeNoException();
                            reply.writeString(_result17);
                            return true;
                        case 92:
                            String _arg069 = data.readString();
                            data.enforceNoDataAvail();
                            String _result18 = asecFsPath(_arg069);
                            reply.writeNoException();
                            reply.writeString(_result18);
                            return true;
                        case 93:
                            String[] _result19 = asecList();
                            reply.writeNoException();
                            reply.writeStringArray(_result19);
                            return true;
                        case 94:
                            String _arg070 = data.readString();
                            int _arg149 = data.readInt();
                            String _arg228 = data.readString();
                            data.enforceNoDataAvail();
                            asecTrim(_arg070, _arg149, _arg228);
                            reply.writeNoException();
                            return true;
                        case 95:
                            String _arg071 = data.readString();
                            data.enforceNoDataAvail();
                            int _result20 = asecGetUsedSpace(_arg071);
                            reply.writeNoException();
                            reply.writeInt(_result20);
                            return true;
                        case 96:
                            int _arg072 = data.readInt();
                            String _arg150 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result21 = setSensitive(_arg072, _arg150);
                            reply.writeNoException();
                            reply.writeBoolean(_result21);
                            return true;
                        case 97:
                            String _arg073 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result22 = isSensitive(_arg073);
                            reply.writeNoException();
                            reply.writeBoolean(_result22);
                            return true;
                        case 98:
                            int _arg074 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result23 = mountSdpMediaStorageCmd(_arg074);
                            reply.writeNoException();
                            reply.writeBoolean(_result23);
                            return true;
                        case 99:
                            int _arg075 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result24 = setSdpPolicyCmd(_arg075);
                            reply.writeNoException();
                            reply.writeBoolean(_result24);
                            return true;
                        case 100:
                            int _arg076 = data.readInt();
                            String _arg151 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result25 = setSdpPolicyToPathCmd(_arg076, _arg151);
                            reply.writeNoException();
                            reply.writeBoolean(_result25);
                            return true;
                        case 101:
                            int _arg077 = data.readInt();
                            int _arg152 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result26 = setDualDARPolicyCmd(_arg077, _arg152);
                            reply.writeNoException();
                            reply.writeBoolean(_result26);
                            return true;
                        case 102:
                            long _arg078 = data.readLong();
                            data.enforceNoDataAvail();
                            boolean _result27 = shrinkDataDdp(_arg078);
                            reply.writeNoException();
                            reply.writeBoolean(_result27);
                            return true;
                        case 103:
                            long _arg079 = data.readLong();
                            data.enforceNoDataAvail();
                            int _result28 = reserveDataBlocks(_arg079);
                            reply.writeNoException();
                            reply.writeInt(_result28);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IVold {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.p009os.IVold
            public void setListener(IVoldListener listener) throws RemoteException {
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

            @Override // android.p009os.IVold
            public void abortFuse() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void monitor() throws RemoteException {
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

            @Override // android.p009os.IVold
            public void reset() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void onUserAdded(int userId, int userSerial, int sharesStorageWithUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(userSerial);
                    _data.writeInt(sharesStorageWithUserId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void onUserRemoved(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void onUserStarted(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void onUserStopped(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void addAppIds(String[] packageNames, int[] appIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(packageNames);
                    _data.writeIntArray(appIds);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void addSandboxIds(int[] appIds, String[] sandboxIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(appIds);
                    _data.writeStringArray(sandboxIds);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void onSecureKeyguardStateChanged(boolean isShowing) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isShowing);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void partition(String diskId, int partitionType, int ratio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    _data.writeInt(partitionType);
                    _data.writeInt(ratio);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void forgetPartition(String partGuid, String fsUuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(partGuid);
                    _data.writeString(fsUuid);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void mount(String volId, int mountFlags, int mountUserId, IVoldMountCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeInt(mountFlags);
                    _data.writeInt(mountUserId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void unmount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void format(String volId, String fsType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeString(fsType);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void moveStorage(String fromVolId, String toVolId, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromVolId);
                    _data.writeString(toVolId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void remountUid(int uid, int remountMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(remountMode);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void remountAppStorageDirs(int uid, int pid, String[] packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeStringArray(packageNames);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void unmountAppStorageDirs(int uid, int pid, String[] packageNames) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeStringArray(packageNames);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setupAppDir(String path, int appUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(appUid);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void fixupAppDir(String path, int appUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(appUid);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void ensureAppDirsCreated(String[] paths, int appUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(paths);
                    _data.writeInt(appUid);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public String createObb(String sourcePath, int ownerGid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sourcePath);
                    _data.writeInt(ownerGid);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void destroyObb(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void fstrim(int fstrimFlags, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fstrimFlags);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void runIdleMaint(boolean needGC, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(needGC);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void abortIdleMaint(IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public int getStorageLifeTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setGCUrgentPace(int neededSegments, int minSegmentThreshold, float dirtyReclaimRate, float reclaimWeight, int gcPeriod, int minGCSleepTime, int targetDirtyRatio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(neededSegments);
                    _data.writeInt(minSegmentThreshold);
                    _data.writeFloat(dirtyReclaimRate);
                    _data.writeFloat(reclaimWeight);
                    _data.writeInt(gcPeriod);
                    _data.writeInt(minGCSleepTime);
                    _data.writeInt(targetDirtyRatio);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void refreshLatestWrite() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public int getWriteAmount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public FileDescriptor mountAppFuse(int uid, int mountId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(mountId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    FileDescriptor _result = _reply.readRawFileDescriptor();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void unmountAppFuse(int uid, int mountId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(mountId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void sdeEnable(String volId, int mountFlags, int mountUserId, boolean type, IVoldTaskListener listener, IVoldMountCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeInt(mountFlags);
                    _data.writeInt(mountUserId);
                    _data.writeBoolean(type);
                    _data.writeStrongInterface(listener);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void sdeMoveMountHidden(String volId, int mountFlags, int mountUserId, IVoldMountCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeInt(mountFlags);
                    _data.writeInt(mountUserId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void fbeEnable() throws RemoteException {
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

            @Override // android.p009os.IVold
            public void initUser0() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void mountFstab(String blkDevice, String mountPoint, String zonedDevice) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(blkDevice);
                    _data.writeString(mountPoint);
                    _data.writeString(zonedDevice);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void encryptFstab(String blkDevice, String mountPoint, boolean shouldFormat, String fsType, String zonedDevice) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(blkDevice);
                    _data.writeString(mountPoint);
                    _data.writeBoolean(shouldFormat);
                    _data.writeString(fsType);
                    _data.writeString(zonedDevice);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setStorageBindingSeed(byte[] seed) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(seed);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void createUserKey(int userId, int userSerial, boolean ephemeral) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(userSerial);
                    _data.writeBoolean(ephemeral);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void destroyUserKey(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setUserKeyProtection(int userId, String secret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(secret);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public int[] getUnlockedUsers() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void unlockUserKey(int userId, int userSerial, String token, String secret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(userSerial);
                    _data.writeString(token);
                    _data.writeString(secret);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void lockUserKey(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setDebugForExternal(String debug) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(debug);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void prepareUserStorage(String uuid, int userId, int userSerial, int storageFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(userSerial);
                    _data.writeInt(storageFlags);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void destroyUserStorage(String uuid, int userId, int storageFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uuid);
                    _data.writeInt(userId);
                    _data.writeInt(storageFlags);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void prepareSandboxForApp(String packageName, int appId, String sandboxId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(appId);
                    _data.writeString(sandboxId);
                    _data.writeInt(userId);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void destroySandboxForApp(String packageName, String sandboxId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(sandboxId);
                    _data.writeInt(userId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void startCheckpoint(int retry) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(retry);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean needsCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean needsRollback() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean isCheckpointing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void abortChanges(String device, boolean retry) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(device);
                    _data.writeBoolean(retry);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void commitChanges() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void prepareCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void restoreCheckpoint(String device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(device);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void restoreCheckpointPart(String device, int count) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(device);
                    _data.writeInt(count);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void markBootAttempt() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean supportsCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean supportsBlockCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean supportsFileCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void resetCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void earlyBootEnded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public String createStubVolume(String sourcePath, String mountPath, String fsType, String fsUuid, String fsLabel, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sourcePath);
                    _data.writeString(mountPath);
                    _data.writeString(fsType);
                    _data.writeString(fsUuid);
                    _data.writeString(fsLabel);
                    _data.writeInt(flags);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void destroyStubVolume(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public FileDescriptor openAppFuseFile(int uid, int mountId, int fileId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(mountId);
                    _data.writeInt(fileId);
                    _data.writeInt(flags);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    FileDescriptor _result = _reply.readRawFileDescriptor();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean incFsEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public IncrementalFileSystemControlParcel mountIncFs(String backingPath, String targetDir, int flags, String sysfsName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(backingPath);
                    _data.writeString(targetDir);
                    _data.writeInt(flags);
                    _data.writeString(sysfsName);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    IncrementalFileSystemControlParcel _result = (IncrementalFileSystemControlParcel) _reply.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void unmountIncFs(String dir) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dir);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setIncFsMountOptions(IncrementalFileSystemControlParcel control, boolean enableReadLogs, boolean enableReadTimeouts, String sysfsName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(control, 0);
                    _data.writeBoolean(enableReadLogs);
                    _data.writeBoolean(enableReadTimeouts);
                    _data.writeString(sysfsName);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void bindMount(String sourceDir, String targetDir) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sourceDir);
                    _data.writeString(targetDir);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void destroyDsuMetadataKey(String dsuSlot) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dsuSlot);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public long getUsedF2fsFileNode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void mvFileAtData(String fromPath, String toPath, int mediaProviderUid, int callingAppUid, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromPath);
                    _data.writeString(toPath);
                    _data.writeInt(mediaProviderUid);
                    _data.writeInt(callingAppUid);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void cpFileAtData(String fromPath, String toPath, int mediaProviderUid, int callingAppUid, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fromPath);
                    _data.writeString(toPath);
                    _data.writeInt(mediaProviderUid);
                    _data.writeInt(callingAppUid);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void setMpUidForFileSystem(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecCreate(String id, int sizeMb, String fstype, String key, int ownerUid, boolean external) throws RemoteException {
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
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecResize(String id, int sizeMb, String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(sizeMb);
                    _data.writeString(key);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecFinalize(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecFixperms(String id, int gid, String filename) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(gid);
                    _data.writeString(filename);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecDestroy(String id, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeBoolean(force);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecMount(String id, String key, int ownerUid, boolean readOnly) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(key);
                    _data.writeInt(ownerUid);
                    _data.writeBoolean(readOnly);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecUnmount(String id, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeBoolean(force);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecRename(String oldId, String newId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(oldId);
                    _data.writeString(newId);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public String asecPath(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public String asecFsPath(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public String[] asecList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public void asecTrim(String id, int sizeMb, String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeInt(sizeMb);
                    _data.writeString(key);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public int asecGetUsedSpace(String id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean setSensitive(int engineId, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(engineId);
                    _data.writeString(path);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean isSensitive(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean mountSdpMediaStorageCmd(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean setSdpPolicyCmd(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean setSdpPolicyToPathCmd(int userId, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(path);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean setDualDARPolicyCmd(int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public boolean shrinkDataDdp(long superUsedSectors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(superUsedSectors);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVold
            public int reserveDataBlocks(long superUsedSectors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(superUsedSectors);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 102;
        }
    }
}
