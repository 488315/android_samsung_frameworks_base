package android.os;

import android.hardware.usb.UsbManager;
import android.os.IVoldListener;
import android.os.IVoldMountCallback;
import android.os.IVoldTaskListener;
import android.os.incremental.IncrementalFileSystemControlParcel;
import android.provider.Telephony;
import com.samsung.android.knox.analytics.database.Contract;
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

    public static class Default implements IVold {
        @Override // android.os.IVold
        public void abortChanges(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public void abortFuse() throws RemoteException {
        }

        @Override // android.os.IVold
        public void abortIdleMaint(IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public void addAppIds(String[] strArr, int[] iArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public void addSandboxIds(int[] iArr, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVold
        public void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public void asecDestroy(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public void asecFinalize(String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public void asecFixperms(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public String asecFsPath(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public int asecGetUsedSpace(String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public String[] asecList() throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void asecMount(String str, String str2, int i, boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public String asecPath(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void asecRename(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void asecResize(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void asecTrim(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void asecUnmount(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public void bindMount(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public int clearDataPassStorage(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public void commitChanges() throws RemoteException {
        }

        @Override // android.os.IVold
        public void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public String createObb(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public int createPassStorage(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public String createStubVolume(String str, String str2, String str3, String str4, String str5, int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void createUserStorageKeys(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public void destroyDsuMetadataKey(String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public void destroyObb(String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public int destroyPassStorage(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public void destroySandboxForApp(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void destroyStubVolume(String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public void destroyUserStorage(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void destroyUserStorageKeys(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void earlyBootEnded() throws RemoteException {
        }

        @Override // android.os.IVold
        public void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr, boolean[] zArr, long j) throws RemoteException {
        }

        @Override // android.os.IVold
        public void ensureAppDirsCreated(String[] strArr, int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void fbeEnable() throws RemoteException {
        }

        @Override // android.os.IVold
        public void fixupAppDir(String str, int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void forgetPartition(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void format(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public String getPassStorage(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public int getStorageLifeTime() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public int getStorageRemainingLifetime() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public long getStorageSize() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IVold
        public int[] getUnlockedUsers() throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public long getUsedF2fsFileNode() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IVold
        public int getWriteAmount() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public int getWriteBoosterBufferAvailablePercent() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public int getWriteBoosterBufferSize() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public int getWriteBoosterLifeTimeEstimate() throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public boolean incFsEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void initUser0() throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean isCheckpointing() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isClearDataExceptionsPass(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isPassClients(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isPassUnlocked(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isSensitive(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isUninstallExceptionsPass(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void lockCeStorage(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public int lockPassStorage(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public void markBootAttempt() throws RemoteException {
        }

        @Override // android.os.IVold
        public void monitor() throws RemoteException {
        }

        @Override // android.os.IVold
        public void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) throws RemoteException {
        }

        @Override // android.os.IVold
        public FileDescriptor mountAppFuse(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void mountFstab(String str, String str2, boolean z, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public IncrementalFileSystemControlParcel mountIncFs(String str, String str2, int i, String str3) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public boolean mountSdpMediaStorageCmd(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public void mvFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean needsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean needsRollback() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void onSecureKeyguardStateChanged(boolean z) throws RemoteException {
        }

        @Override // android.os.IVold
        public void onUserAdded(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IVold
        public void onUserRemoved(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void onUserStarted(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void onUserStopped(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void partition(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void prepareCheckpoint() throws RemoteException {
        }

        @Override // android.os.IVold
        public void prepareSandboxForApp(String str, int i, String str2, int i2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void prepareUserStorage(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void refreshLatestWrite() throws RemoteException {
        }

        @Override // android.os.IVold
        public void remountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public void remountUid(int i, int i2) throws RemoteException {
        }

        @Override // android.os.IVold
        public int reserveDataBlocks(long j) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public void reset() throws RemoteException {
        }

        @Override // android.os.IVold
        public void resetCheckpoint() throws RemoteException {
        }

        @Override // android.os.IVold
        public void restoreCheckpoint(String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public void restoreCheckpointPart(String str, int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public void runIdleDefrag(IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public void sdeEnable(String str, int i, int i2, boolean z, IVoldTaskListener iVoldTaskListener, IVoldMountCallback iVoldMountCallback) throws RemoteException {
        }

        @Override // android.os.IVold
        public void setCeStorageProtection(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.os.IVold
        public void setIncFsMountOptions(IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public void setListener(IVoldListener iVoldListener) throws RemoteException {
        }

        @Override // android.os.IVold
        public void setMpUidForFileSystem(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean setSdpPolicyCmd(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean setSensitive(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void setStorageBindingSeed(byte[] bArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean setWriteBoosterBufferFlush(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean setWriteBoosterBufferOn(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void setupAppDir(String str, int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean shrinkDataDdp(long j) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void shutdown() throws RemoteException {
        }

        @Override // android.os.IVold
        public void startCheckpoint(int i) throws RemoteException {
        }

        @Override // android.os.IVold
        public boolean supportsBlockCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean supportsCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean supportsFileCheckpoint() throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void unlockCeStorage(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public int unlockPassStorage(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public void unmount(String str) throws RemoteException {
        }

        @Override // android.os.IVold
        public void unmountAppFuse(int i, int i2) throws RemoteException {
        }

        @Override // android.os.IVold
        public void unmountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IVold
        public void unmountIncFs(String str) throws RemoteException {
        }
    }

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

    int clearDataPassStorage(String str, int i, int i2) throws RemoteException;

    void commitChanges() throws RemoteException;

    void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    String createObb(String str, int i) throws RemoteException;

    int createPassStorage(String str, int i, int i2) throws RemoteException;

    String createStubVolume(String str, String str2, String str3, String str4, String str5, int i) throws RemoteException;

    void createUserStorageKeys(int i, boolean z) throws RemoteException;

    void destroyDsuMetadataKey(String str) throws RemoteException;

    void destroyObb(String str) throws RemoteException;

    int destroyPassStorage(String str, int i, int i2) throws RemoteException;

    void destroySandboxForApp(String str, String str2, int i) throws RemoteException;

    void destroyStubVolume(String str) throws RemoteException;

    void destroyUserStorage(String str, int i, int i2) throws RemoteException;

    void destroyUserStorageKeys(int i) throws RemoteException;

    void earlyBootEnded() throws RemoteException;

    void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr, boolean[] zArr, long j) throws RemoteException;

    void ensureAppDirsCreated(String[] strArr, int i) throws RemoteException;

    void fbeEnable() throws RemoteException;

    void fixupAppDir(String str, int i) throws RemoteException;

    void forgetPartition(String str, String str2) throws RemoteException;

    void format(String str, String str2) throws RemoteException;

    void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    String getPassStorage(String str, int i, int i2) throws RemoteException;

    int getStorageLifeTime() throws RemoteException;

    int getStorageRemainingLifetime() throws RemoteException;

    long getStorageSize() throws RemoteException;

    int[] getUnlockedUsers() throws RemoteException;

    long getUsedF2fsFileNode() throws RemoteException;

    int getWriteAmount() throws RemoteException;

    int getWriteBoosterBufferAvailablePercent() throws RemoteException;

    int getWriteBoosterBufferSize() throws RemoteException;

    int getWriteBoosterLifeTimeEstimate() throws RemoteException;

    boolean incFsEnabled() throws RemoteException;

    void initUser0() throws RemoteException;

    boolean isCheckpointing() throws RemoteException;

    boolean isClearDataExceptionsPass(String str) throws RemoteException;

    boolean isPassClients(String str) throws RemoteException;

    boolean isPassUnlocked(String str, int i, int i2) throws RemoteException;

    boolean isSensitive(String str) throws RemoteException;

    boolean isUninstallExceptionsPass(String str) throws RemoteException;

    void lockCeStorage(int i) throws RemoteException;

    int lockPassStorage(String str, int i, int i2) throws RemoteException;

    void markBootAttempt() throws RemoteException;

    void monitor() throws RemoteException;

    void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) throws RemoteException;

    FileDescriptor mountAppFuse(int i, int i2) throws RemoteException;

    void mountFstab(String str, String str2, boolean z, String[] strArr) throws RemoteException;

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

    void prepareUserStorage(String str, int i, int i2) throws RemoteException;

    void refreshLatestWrite() throws RemoteException;

    void remountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException;

    void remountUid(int i, int i2) throws RemoteException;

    int reserveDataBlocks(long j) throws RemoteException;

    void reset() throws RemoteException;

    void resetCheckpoint() throws RemoteException;

    void restoreCheckpoint(String str) throws RemoteException;

    void restoreCheckpointPart(String str, int i) throws RemoteException;

    void runIdleDefrag(IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    void sdeEnable(String str, int i, int i2, boolean z, IVoldTaskListener iVoldTaskListener, IVoldMountCallback iVoldMountCallback) throws RemoteException;

    void setCeStorageProtection(int i, byte[] bArr) throws RemoteException;

    boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException;

    void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws RemoteException;

    void setIncFsMountOptions(IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, String str) throws RemoteException;

    void setListener(IVoldListener iVoldListener) throws RemoteException;

    void setMpUidForFileSystem(int i) throws RemoteException;

    boolean setSdpPolicyCmd(int i) throws RemoteException;

    boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException;

    boolean setSensitive(int i, String str) throws RemoteException;

    void setStorageBindingSeed(byte[] bArr) throws RemoteException;

    boolean setWriteBoosterBufferFlush(boolean z) throws RemoteException;

    boolean setWriteBoosterBufferOn(boolean z) throws RemoteException;

    void setupAppDir(String str, int i) throws RemoteException;

    boolean shrinkDataDdp(long j) throws RemoteException;

    void shutdown() throws RemoteException;

    void startCheckpoint(int i) throws RemoteException;

    boolean supportsBlockCheckpoint() throws RemoteException;

    boolean supportsCheckpoint() throws RemoteException;

    boolean supportsFileCheckpoint() throws RemoteException;

    void unlockCeStorage(int i, byte[] bArr) throws RemoteException;

    int unlockPassStorage(String str, int i, int i2) throws RemoteException;

    void unmount(String str) throws RemoteException;

    void unmountAppFuse(int i, int i2) throws RemoteException;

    void unmountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException;

    void unmountIncFs(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IVold {
        public static final String DESCRIPTOR = "android.os.IVold";
        static final int TRANSACTION_abortChanges = 67;
        static final int TRANSACTION_abortFuse = 2;
        static final int TRANSACTION_abortIdleMaint = 30;
        static final int TRANSACTION_addAppIds = 10;
        static final int TRANSACTION_addSandboxIds = 11;
        static final int TRANSACTION_asecCreate = 99;
        static final int TRANSACTION_asecDestroy = 103;
        static final int TRANSACTION_asecFinalize = 101;
        static final int TRANSACTION_asecFixperms = 102;
        static final int TRANSACTION_asecFsPath = 108;
        static final int TRANSACTION_asecGetUsedSpace = 111;
        static final int TRANSACTION_asecList = 109;
        static final int TRANSACTION_asecMount = 104;
        static final int TRANSACTION_asecPath = 107;
        static final int TRANSACTION_asecRename = 106;
        static final int TRANSACTION_asecResize = 100;
        static final int TRANSACTION_asecTrim = 110;
        static final int TRANSACTION_asecUnmount = 105;
        static final int TRANSACTION_benchmark = 18;
        static final int TRANSACTION_bindMount = 85;
        static final int TRANSACTION_clearDataPassStorage = 55;
        static final int TRANSACTION_commitChanges = 68;
        static final int TRANSACTION_cpFileAtData = 97;
        static final int TRANSACTION_createObb = 26;
        static final int TRANSACTION_createPassStorage = 49;
        static final int TRANSACTION_createStubVolume = 78;
        static final int TRANSACTION_createUserStorageKeys = 43;
        static final int TRANSACTION_destroyDsuMetadataKey = 86;
        static final int TRANSACTION_destroyObb = 27;
        static final int TRANSACTION_destroyPassStorage = 50;
        static final int TRANSACTION_destroySandboxForApp = 62;
        static final int TRANSACTION_destroyStubVolume = 79;
        static final int TRANSACTION_destroyUserStorage = 60;
        static final int TRANSACTION_destroyUserStorageKeys = 44;
        static final int TRANSACTION_earlyBootEnded = 77;
        static final int TRANSACTION_encryptFstab = 41;
        static final int TRANSACTION_ensureAppDirsCreated = 25;
        static final int TRANSACTION_fbeEnable = 38;
        static final int TRANSACTION_fixupAppDir = 24;
        static final int TRANSACTION_forgetPartition = 14;
        static final int TRANSACTION_format = 17;
        static final int TRANSACTION_fstrim = 28;
        static final int TRANSACTION_getPassStorage = 53;
        static final int TRANSACTION_getStorageLifeTime = 31;
        static final int TRANSACTION_getStorageRemainingLifetime = 88;
        static final int TRANSACTION_getStorageSize = 87;
        static final int TRANSACTION_getUnlockedUsers = 56;
        static final int TRANSACTION_getUsedF2fsFileNode = 95;
        static final int TRANSACTION_getWriteAmount = 34;
        static final int TRANSACTION_getWriteBoosterBufferAvailablePercent = 90;
        static final int TRANSACTION_getWriteBoosterBufferSize = 89;
        static final int TRANSACTION_getWriteBoosterLifeTimeEstimate = 93;
        static final int TRANSACTION_incFsEnabled = 81;
        static final int TRANSACTION_initUser0 = 39;
        static final int TRANSACTION_isCheckpointing = 66;
        static final int TRANSACTION_isClearDataExceptionsPass = 47;
        static final int TRANSACTION_isPassClients = 46;
        static final int TRANSACTION_isPassUnlocked = 54;
        static final int TRANSACTION_isSensitive = 113;
        static final int TRANSACTION_isUninstallExceptionsPass = 48;
        static final int TRANSACTION_lockCeStorage = 58;
        static final int TRANSACTION_lockPassStorage = 51;
        static final int TRANSACTION_markBootAttempt = 72;
        static final int TRANSACTION_monitor = 3;
        static final int TRANSACTION_mount = 15;
        static final int TRANSACTION_mountAppFuse = 35;
        static final int TRANSACTION_mountFstab = 40;
        static final int TRANSACTION_mountIncFs = 82;
        static final int TRANSACTION_mountSdpMediaStorageCmd = 114;
        static final int TRANSACTION_moveStorage = 19;
        static final int TRANSACTION_mvFileAtData = 96;
        static final int TRANSACTION_needsCheckpoint = 64;
        static final int TRANSACTION_needsRollback = 65;
        static final int TRANSACTION_onSecureKeyguardStateChanged = 12;
        static final int TRANSACTION_onUserAdded = 6;
        static final int TRANSACTION_onUserRemoved = 7;
        static final int TRANSACTION_onUserStarted = 8;
        static final int TRANSACTION_onUserStopped = 9;
        static final int TRANSACTION_openAppFuseFile = 80;
        static final int TRANSACTION_partition = 13;
        static final int TRANSACTION_prepareCheckpoint = 69;
        static final int TRANSACTION_prepareSandboxForApp = 61;
        static final int TRANSACTION_prepareUserStorage = 59;
        static final int TRANSACTION_refreshLatestWrite = 33;
        static final int TRANSACTION_remountAppStorageDirs = 21;
        static final int TRANSACTION_remountUid = 20;
        static final int TRANSACTION_reserveDataBlocks = 119;
        static final int TRANSACTION_reset = 4;
        static final int TRANSACTION_resetCheckpoint = 76;
        static final int TRANSACTION_restoreCheckpoint = 70;
        static final int TRANSACTION_restoreCheckpointPart = 71;
        static final int TRANSACTION_runIdleDefrag = 94;
        static final int TRANSACTION_runIdleMaint = 29;
        static final int TRANSACTION_sdeEnable = 37;
        static final int TRANSACTION_setCeStorageProtection = 45;
        static final int TRANSACTION_setDualDARPolicyCmd = 117;
        static final int TRANSACTION_setGCUrgentPace = 32;
        static final int TRANSACTION_setIncFsMountOptions = 84;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setMpUidForFileSystem = 98;
        static final int TRANSACTION_setSdpPolicyCmd = 115;
        static final int TRANSACTION_setSdpPolicyToPathCmd = 116;
        static final int TRANSACTION_setSensitive = 112;
        static final int TRANSACTION_setStorageBindingSeed = 42;
        static final int TRANSACTION_setWriteBoosterBufferFlush = 91;
        static final int TRANSACTION_setWriteBoosterBufferOn = 92;
        static final int TRANSACTION_setupAppDir = 23;
        static final int TRANSACTION_shrinkDataDdp = 118;
        static final int TRANSACTION_shutdown = 5;
        static final int TRANSACTION_startCheckpoint = 63;
        static final int TRANSACTION_supportsBlockCheckpoint = 74;
        static final int TRANSACTION_supportsCheckpoint = 73;
        static final int TRANSACTION_supportsFileCheckpoint = 75;
        static final int TRANSACTION_unlockCeStorage = 57;
        static final int TRANSACTION_unlockPassStorage = 52;
        static final int TRANSACTION_unmount = 16;
        static final int TRANSACTION_unmountAppFuse = 36;
        static final int TRANSACTION_unmountAppStorageDirs = 22;
        static final int TRANSACTION_unmountIncFs = 83;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 118;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVold asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVold)) {
                return (IVold) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setListener";
                case 2:
                    return "abortFuse";
                case 3:
                    return "monitor";
                case 4:
                    return Contract.Reset.PATH;
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
                    return "fbeEnable";
                case 39:
                    return "initUser0";
                case 40:
                    return "mountFstab";
                case 41:
                    return "encryptFstab";
                case 42:
                    return "setStorageBindingSeed";
                case 43:
                    return "createUserStorageKeys";
                case 44:
                    return "destroyUserStorageKeys";
                case 45:
                    return "setCeStorageProtection";
                case 46:
                    return "isPassClients";
                case 47:
                    return "isClearDataExceptionsPass";
                case 48:
                    return "isUninstallExceptionsPass";
                case 49:
                    return "createPassStorage";
                case 50:
                    return "destroyPassStorage";
                case 51:
                    return "lockPassStorage";
                case 52:
                    return "unlockPassStorage";
                case 53:
                    return "getPassStorage";
                case 54:
                    return "isPassUnlocked";
                case 55:
                    return "clearDataPassStorage";
                case 56:
                    return "getUnlockedUsers";
                case 57:
                    return "unlockCeStorage";
                case 58:
                    return "lockCeStorage";
                case 59:
                    return "prepareUserStorage";
                case 60:
                    return "destroyUserStorage";
                case 61:
                    return "prepareSandboxForApp";
                case 62:
                    return "destroySandboxForApp";
                case 63:
                    return "startCheckpoint";
                case 64:
                    return "needsCheckpoint";
                case 65:
                    return "needsRollback";
                case 66:
                    return "isCheckpointing";
                case 67:
                    return "abortChanges";
                case 68:
                    return "commitChanges";
                case 69:
                    return "prepareCheckpoint";
                case 70:
                    return "restoreCheckpoint";
                case 71:
                    return "restoreCheckpointPart";
                case 72:
                    return "markBootAttempt";
                case 73:
                    return "supportsCheckpoint";
                case 74:
                    return "supportsBlockCheckpoint";
                case 75:
                    return "supportsFileCheckpoint";
                case 76:
                    return "resetCheckpoint";
                case 77:
                    return "earlyBootEnded";
                case 78:
                    return "createStubVolume";
                case 79:
                    return "destroyStubVolume";
                case 80:
                    return "openAppFuseFile";
                case 81:
                    return "incFsEnabled";
                case 82:
                    return "mountIncFs";
                case 83:
                    return "unmountIncFs";
                case 84:
                    return "setIncFsMountOptions";
                case 85:
                    return "bindMount";
                case 86:
                    return "destroyDsuMetadataKey";
                case 87:
                    return "getStorageSize";
                case 88:
                    return "getStorageRemainingLifetime";
                case 89:
                    return "getWriteBoosterBufferSize";
                case 90:
                    return "getWriteBoosterBufferAvailablePercent";
                case 91:
                    return "setWriteBoosterBufferFlush";
                case 92:
                    return "setWriteBoosterBufferOn";
                case 93:
                    return "getWriteBoosterLifeTimeEstimate";
                case 94:
                    return "runIdleDefrag";
                case 95:
                    return "getUsedF2fsFileNode";
                case 96:
                    return "mvFileAtData";
                case 97:
                    return "cpFileAtData";
                case 98:
                    return "setMpUidForFileSystem";
                case 99:
                    return "asecCreate";
                case 100:
                    return "asecResize";
                case 101:
                    return "asecFinalize";
                case 102:
                    return "asecFixperms";
                case 103:
                    return "asecDestroy";
                case 104:
                    return "asecMount";
                case 105:
                    return "asecUnmount";
                case 106:
                    return "asecRename";
                case 107:
                    return "asecPath";
                case 108:
                    return "asecFsPath";
                case 109:
                    return "asecList";
                case 110:
                    return "asecTrim";
                case 111:
                    return "asecGetUsedSpace";
                case 112:
                    return "setSensitive";
                case 113:
                    return "isSensitive";
                case 114:
                    return "mountSdpMediaStorageCmd";
                case 115:
                    return "setSdpPolicyCmd";
                case 116:
                    return "setSdpPolicyToPathCmd";
                case 117:
                    return "setDualDARPolicyCmd";
                case 118:
                    return "shrinkDataDdp";
                case 119:
                    return "reserveDataBlocks";
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
                    IVoldListener iVoldListenerAsInterface = IVoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(iVoldListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    abortFuse();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    monitor();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    reset();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserAdded(i3, i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserRemoved(i6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserStarted(i7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserStopped(i8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    addAppIds(strArrCreateStringArray, iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    addSandboxIds(iArrCreateIntArray2, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSecureKeyguardStateChanged(z);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partition(string, i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetPartition(string2, string3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string4 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    IVoldMountCallback iVoldMountCallbackAsInterface = IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mount(string4, i11, i12, iVoldMountCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmount(string5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    format(string6, string7);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string8 = parcel.readString();
                    IVoldTaskListener iVoldTaskListenerAsInterface = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    benchmark(string8, iVoldTaskListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    IVoldTaskListener iVoldTaskListenerAsInterface2 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    moveStorage(string9, string10, iVoldTaskListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remountUid(i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    remountAppStorageDirs(i15, i16, strArrCreateStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    unmountAppStorageDirs(i17, i18, strArrCreateStringArray4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string11 = parcel.readString();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setupAppDir(string11, i19);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string12 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    fixupAppDir(string12, i20);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ensureAppDirsCreated(strArrCreateStringArray5, i21);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string13 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strCreateObb = createObb(string13, i22);
                    parcel2.writeNoException();
                    parcel2.writeString(strCreateObb);
                    return true;
                case 27:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyObb(string14);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i23 = parcel.readInt();
                    IVoldTaskListener iVoldTaskListenerAsInterface3 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fstrim(i23, iVoldTaskListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean z2 = parcel.readBoolean();
                    IVoldTaskListener iVoldTaskListenerAsInterface4 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    runIdleMaint(z2, iVoldTaskListenerAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IVoldTaskListener iVoldTaskListenerAsInterface5 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortIdleMaint(iVoldTaskListenerAsInterface5);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int storageLifeTime = getStorageLifeTime();
                    parcel2.writeNoException();
                    parcel2.writeInt(storageLifeTime);
                    return true;
                case 32:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    float f = parcel.readFloat();
                    float f2 = parcel.readFloat();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGCUrgentPace(i24, i25, f, f2, i26, i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    refreshLatestWrite();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int writeAmount = getWriteAmount();
                    parcel2.writeNoException();
                    parcel2.writeInt(writeAmount);
                    return true;
                case 35:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor fileDescriptorMountAppFuse = mountAppFuse(i29, i30);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(fileDescriptorMountAppFuse);
                    return true;
                case 36:
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unmountAppFuse(i31, i32);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String string15 = parcel.readString();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    IVoldTaskListener iVoldTaskListenerAsInterface6 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    IVoldMountCallback iVoldMountCallbackAsInterface2 = IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sdeEnable(string15, i33, i34, z3, iVoldTaskListenerAsInterface6, iVoldMountCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    fbeEnable();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    initUser0();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    mountFstab(string16, string17, z4, strArrCreateStringArray6);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    String string20 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    boolean[] zArrCreateBooleanArray = parcel.createBooleanArray();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    encryptFstab(string18, string19, z5, string20, z6, strArrCreateStringArray7, zArrCreateBooleanArray, j);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setStorageBindingSeed(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int i35 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createUserStorageKeys(i35, z7);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorageKeys(i36);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i37 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setCeStorageProtection(i37, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPassClients = isPassClients(string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPassClients);
                    return true;
                case 47:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsClearDataExceptionsPass = isClearDataExceptionsPass(string22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClearDataExceptionsPass);
                    return true;
                case 48:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUninstallExceptionsPass = isUninstallExceptionsPass(string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUninstallExceptionsPass);
                    return true;
                case 49:
                    String string24 = parcel.readString();
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCreatePassStorage = createPassStorage(string24, i38, i39);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreatePassStorage);
                    return true;
                case 50:
                    String string25 = parcel.readString();
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iDestroyPassStorage = destroyPassStorage(string25, i40, i41);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDestroyPassStorage);
                    return true;
                case 51:
                    String string26 = parcel.readString();
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iLockPassStorage = lockPassStorage(string26, i42, i43);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLockPassStorage);
                    return true;
                case 52:
                    String string27 = parcel.readString();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUnlockPassStorage = unlockPassStorage(string27, i44, i45);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnlockPassStorage);
                    return true;
                case 53:
                    String string28 = parcel.readString();
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String passStorage = getPassStorage(string28, i46, i47);
                    parcel2.writeNoException();
                    parcel2.writeString(passStorage);
                    return true;
                case 54:
                    String string29 = parcel.readString();
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPassUnlocked = isPassUnlocked(string29, i48, i49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPassUnlocked);
                    return true;
                case 55:
                    String string30 = parcel.readString();
                    int i50 = parcel.readInt();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iClearDataPassStorage = clearDataPassStorage(string30, i50, i51);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearDataPassStorage);
                    return true;
                case 56:
                    int[] unlockedUsers = getUnlockedUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(unlockedUsers);
                    return true;
                case 57:
                    int i52 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    unlockCeStorage(i52, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    lockCeStorage(i53);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    String string31 = parcel.readString();
                    int i54 = parcel.readInt();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareUserStorage(string31, i54, i55);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    String string32 = parcel.readString();
                    int i56 = parcel.readInt();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorage(string32, i56, i57);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    String string33 = parcel.readString();
                    int i58 = parcel.readInt();
                    String string34 = parcel.readString();
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareSandboxForApp(string33, i58, string34, i59);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySandboxForApp(string35, string36, i60);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCheckpoint(i61);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    boolean zNeedsCheckpoint = needsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNeedsCheckpoint);
                    return true;
                case 65:
                    boolean zNeedsRollback = needsRollback();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNeedsRollback);
                    return true;
                case 66:
                    boolean zIsCheckpointing = isCheckpointing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCheckpointing);
                    return true;
                case 67:
                    String string37 = parcel.readString();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    abortChanges(string37, z8);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    commitChanges();
                    parcel2.writeNoException();
                    return true;
                case 69:
                    prepareCheckpoint();
                    parcel2.writeNoException();
                    return true;
                case 70:
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreCheckpoint(string38);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String string39 = parcel.readString();
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreCheckpointPart(string39, i62);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    markBootAttempt();
                    parcel2.writeNoException();
                    return true;
                case 73:
                    boolean zSupportsCheckpoint = supportsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsCheckpoint);
                    return true;
                case 74:
                    boolean zSupportsBlockCheckpoint = supportsBlockCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsBlockCheckpoint);
                    return true;
                case 75:
                    boolean zSupportsFileCheckpoint = supportsFileCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsFileCheckpoint);
                    return true;
                case 76:
                    resetCheckpoint();
                    parcel2.writeNoException();
                    return true;
                case 77:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 78:
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strCreateStubVolume = createStubVolume(string40, string41, string42, string43, string44, i63);
                    parcel2.writeNoException();
                    parcel2.writeString(strCreateStubVolume);
                    return true;
                case 79:
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyStubVolume(string45);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor fileDescriptorOpenAppFuseFile = openAppFuseFile(i64, i65, i66, i67);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(fileDescriptorOpenAppFuseFile);
                    return true;
                case 81:
                    boolean zIncFsEnabled = incFsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIncFsEnabled);
                    return true;
                case 82:
                    String string46 = parcel.readString();
                    String string47 = parcel.readString();
                    int i68 = parcel.readInt();
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IncrementalFileSystemControlParcel incrementalFileSystemControlParcelMountIncFs = mountIncFs(string46, string47, i68, string48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(incrementalFileSystemControlParcelMountIncFs, 1);
                    return true;
                case 83:
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmountIncFs(string49);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    IncrementalFileSystemControlParcel incrementalFileSystemControlParcel = (IncrementalFileSystemControlParcel) parcel.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    boolean z10 = parcel.readBoolean();
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIncFsMountOptions(incrementalFileSystemControlParcel, z9, z10, string50);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String string51 = parcel.readString();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    bindMount(string51, string52);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String string53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyDsuMetadataKey(string53);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    long storageSize = getStorageSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(storageSize);
                    return true;
                case 88:
                    int storageRemainingLifetime = getStorageRemainingLifetime();
                    parcel2.writeNoException();
                    parcel2.writeInt(storageRemainingLifetime);
                    return true;
                case 89:
                    int writeBoosterBufferSize = getWriteBoosterBufferSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(writeBoosterBufferSize);
                    return true;
                case 90:
                    int writeBoosterBufferAvailablePercent = getWriteBoosterBufferAvailablePercent();
                    parcel2.writeNoException();
                    parcel2.writeInt(writeBoosterBufferAvailablePercent);
                    return true;
                case 91:
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean writeBoosterBufferFlush = setWriteBoosterBufferFlush(z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeBoosterBufferFlush);
                    return true;
                case 92:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean writeBoosterBufferOn = setWriteBoosterBufferOn(z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeBoosterBufferOn);
                    return true;
                case 93:
                    int writeBoosterLifeTimeEstimate = getWriteBoosterLifeTimeEstimate();
                    parcel2.writeNoException();
                    parcel2.writeInt(writeBoosterLifeTimeEstimate);
                    return true;
                case 94:
                    IVoldTaskListener iVoldTaskListenerAsInterface7 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    runIdleDefrag(iVoldTaskListenerAsInterface7);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    long usedF2fsFileNode = getUsedF2fsFileNode();
                    parcel2.writeNoException();
                    parcel2.writeLong(usedF2fsFileNode);
                    return true;
                case 96:
                    String string54 = parcel.readString();
                    String string55 = parcel.readString();
                    int i69 = parcel.readInt();
                    int i70 = parcel.readInt();
                    IVoldTaskListener iVoldTaskListenerAsInterface8 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mvFileAtData(string54, string55, i69, i70, iVoldTaskListenerAsInterface8);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String string56 = parcel.readString();
                    String string57 = parcel.readString();
                    int i71 = parcel.readInt();
                    int i72 = parcel.readInt();
                    IVoldTaskListener iVoldTaskListenerAsInterface9 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cpFileAtData(string56, string57, i71, i72, iVoldTaskListenerAsInterface9);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMpUidForFileSystem(i73);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    String string58 = parcel.readString();
                    int i74 = parcel.readInt();
                    String string59 = parcel.readString();
                    String string60 = parcel.readString();
                    int i75 = parcel.readInt();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecCreate(string58, i74, string59, string60, i75, z13);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    String string61 = parcel.readString();
                    int i76 = parcel.readInt();
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecResize(string61, i76, string62);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecFinalize(string63);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String string64 = parcel.readString();
                    int i77 = parcel.readInt();
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecFixperms(string64, i77, string65);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    String string66 = parcel.readString();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecDestroy(string66, z14);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    String string67 = parcel.readString();
                    String string68 = parcel.readString();
                    int i78 = parcel.readInt();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecMount(string67, string68, i78, z15);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    String string69 = parcel.readString();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecUnmount(string69, z16);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    String string70 = parcel.readString();
                    String string71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecRename(string70, string71);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strAsecPath = asecPath(string72);
                    parcel2.writeNoException();
                    parcel2.writeString(strAsecPath);
                    return true;
                case 108:
                    String string73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strAsecFsPath = asecFsPath(string73);
                    parcel2.writeNoException();
                    parcel2.writeString(strAsecFsPath);
                    return true;
                case 109:
                    String[] strArrAsecList = asecList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrAsecList);
                    return true;
                case 110:
                    String string74 = parcel.readString();
                    int i79 = parcel.readInt();
                    String string75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecTrim(string74, i79, string75);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    String string76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iAsecGetUsedSpace = asecGetUsedSpace(string76);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAsecGetUsedSpace);
                    return true;
                case 112:
                    int i80 = parcel.readInt();
                    String string77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sensitive = setSensitive(i80, string77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sensitive);
                    return true;
                case 113:
                    String string78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSensitive = isSensitive(string78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSensitive);
                    return true;
                case 114:
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMountSdpMediaStorageCmd = mountSdpMediaStorageCmd(i81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMountSdpMediaStorageCmd);
                    return true;
                case 115:
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sdpPolicyCmd = setSdpPolicyCmd(i82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdpPolicyCmd);
                    return true;
                case 116:
                    int i83 = parcel.readInt();
                    String string79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sdpPolicyToPathCmd = setSdpPolicyToPathCmd(i83, string79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdpPolicyToPathCmd);
                    return true;
                case 117:
                    int i84 = parcel.readInt();
                    int i85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyCmd = setDualDARPolicyCmd(i84, i85);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyCmd);
                    return true;
                case 118:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zShrinkDataDdp = shrinkDataDdp(j2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShrinkDataDdp);
                    return true;
                case 119:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int iReserveDataBlocks = reserveDataBlocks(j3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iReserveDataBlocks);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVold {
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

            @Override // android.os.IVold
            public void setListener(IVoldListener iVoldListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoldListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortFuse() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void monitor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void reset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void shutdown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserAdded(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserStarted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserStopped(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void addAppIds(String[] strArr, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void addSandboxIds(int[] iArr, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onSecureKeyguardStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void partition(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void forgetPartition(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iVoldMountCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void format(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void remountUid(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void remountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setupAppDir(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fixupAppDir(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void ensureAppDirsCreated(String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String createObb(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyObb(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortIdleMaint(IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getStorageLifeTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void refreshLatestWrite() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteAmount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public FileDescriptor mountAppFuse(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readRawFileDescriptor();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountAppFuse(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void sdeEnable(String str, int i, int i2, boolean z, IVoldTaskListener iVoldTaskListener, IVoldMountCallback iVoldMountCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    parcelObtain.writeStrongInterface(iVoldMountCallback);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fbeEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void initUser0() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mountFstab(String str, String str2, boolean z, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr, boolean[] zArr, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeBooleanArray(zArr);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setStorageBindingSeed(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void createUserStorageKeys(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyUserStorageKeys(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setCeStorageProtection(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isPassClients(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isClearDataExceptionsPass(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isUninstallExceptionsPass(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int createPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int destroyPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int lockPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int unlockPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String getPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isPassUnlocked(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int clearDataPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int[] getUnlockedUsers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unlockCeStorage(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void lockCeStorage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareSandboxForApp(String str, int i, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroySandboxForApp(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void startCheckpoint(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean needsCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean needsRollback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isCheckpointing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortChanges(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void commitChanges() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void restoreCheckpoint(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void restoreCheckpointPart(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void markBootAttempt() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsBlockCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsFileCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void resetCheckpoint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void earlyBootEnded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String createStubVolume(String str, String str2, String str3, String str4, String str5, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyStubVolume(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readRawFileDescriptor();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean incFsEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public IncrementalFileSystemControlParcel mountIncFs(String str, String str2, int i, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return (IncrementalFileSystemControlParcel) parcelObtain2.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountIncFs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setIncFsMountOptions(IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(incrementalFileSystemControlParcel, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void bindMount(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyDsuMetadataKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public long getStorageSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getStorageRemainingLifetime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteBoosterBufferSize() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteBoosterBufferAvailablePercent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setWriteBoosterBufferFlush(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setWriteBoosterBufferOn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteBoosterLifeTimeEstimate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void runIdleDefrag(IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public long getUsedF2fsFileNode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mvFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setMpUidForFileSystem(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecResize(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecFinalize(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecFixperms(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecDestroy(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecMount(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecUnmount(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecRename(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String asecPath(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String asecFsPath(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String[] asecList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecTrim(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int asecGetUsedSpace(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setSensitive(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isSensitive(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean mountSdpMediaStorageCmd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setSdpPolicyCmd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean shrinkDataDdp(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int reserveDataBlocks(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 32);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
