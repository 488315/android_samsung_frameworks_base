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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVold)) {
                return (IVold) queryLocalInterface;
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
                    IVoldListener asInterface = IVoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserAdded(readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserRemoved(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserStarted(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserStopped(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String[] createStringArray = parcel.createStringArray();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    addAppIds(createStringArray, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int[] createIntArray2 = parcel.createIntArray();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    addSandboxIds(createIntArray2, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSecureKeyguardStateChanged(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partition(readString, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetPartition(readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString4 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    IVoldMountCallback asInterface2 = IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mount(readString4, readInt9, readInt10, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmount(readString5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    format(readString6, readString7);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString8 = parcel.readString();
                    IVoldTaskListener asInterface3 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    benchmark(readString8, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    IVoldTaskListener asInterface4 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    moveStorage(readString9, readString10, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remountUid(readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    remountAppStorageDirs(readInt13, readInt14, createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    unmountAppStorageDirs(readInt15, readInt16, createStringArray4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString11 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setupAppDir(readString11, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    fixupAppDir(readString12, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String[] createStringArray5 = parcel.createStringArray();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ensureAppDirsCreated(createStringArray5, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString13 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String createObb = createObb(readString13, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(createObb);
                    return true;
                case 27:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyObb(readString14);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt21 = parcel.readInt();
                    IVoldTaskListener asInterface5 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fstrim(readInt21, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean readBoolean2 = parcel.readBoolean();
                    IVoldTaskListener asInterface6 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    runIdleMaint(readBoolean2, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IVoldTaskListener asInterface7 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortIdleMaint(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int storageLifeTime = getStorageLifeTime();
                    parcel2.writeNoException();
                    parcel2.writeInt(storageLifeTime);
                    return true;
                case 32:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGCUrgentPace(readInt22, readInt23, readFloat, readFloat2, readInt24, readInt25, readInt26);
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
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor mountAppFuse = mountAppFuse(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(mountAppFuse);
                    return true;
                case 36:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unmountAppFuse(readInt29, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString15 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    IVoldTaskListener asInterface8 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    IVoldMountCallback asInterface9 = IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sdeEnable(readString15, readInt31, readInt32, readBoolean3, asInterface8, asInterface9);
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
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    String[] createStringArray6 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    mountFstab(readString16, readString17, readBoolean4, createStringArray6);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    String readString20 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    String[] createStringArray7 = parcel.createStringArray();
                    boolean[] createBooleanArray = parcel.createBooleanArray();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    encryptFstab(readString18, readString19, readBoolean5, readString20, readBoolean6, createStringArray7, createBooleanArray, readLong);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setStorageBindingSeed(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt33 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createUserStorageKeys(readInt33, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorageKeys(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt35 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setCeStorageProtection(readInt35, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPassClients = isPassClients(readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPassClients);
                    return true;
                case 47:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isClearDataExceptionsPass = isClearDataExceptionsPass(readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClearDataExceptionsPass);
                    return true;
                case 48:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUninstallExceptionsPass = isUninstallExceptionsPass(readString23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUninstallExceptionsPass);
                    return true;
                case 49:
                    String readString24 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createPassStorage = createPassStorage(readString24, readInt36, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPassStorage);
                    return true;
                case 50:
                    String readString25 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int destroyPassStorage = destroyPassStorage(readString25, readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeInt(destroyPassStorage);
                    return true;
                case 51:
                    String readString26 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockPassStorage = lockPassStorage(readString26, readInt40, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockPassStorage);
                    return true;
                case 52:
                    String readString27 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int unlockPassStorage = unlockPassStorage(readString27, readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockPassStorage);
                    return true;
                case 53:
                    String readString28 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String passStorage = getPassStorage(readString28, readInt44, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeString(passStorage);
                    return true;
                case 54:
                    String readString29 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPassUnlocked = isPassUnlocked(readString29, readInt46, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPassUnlocked);
                    return true;
                case 55:
                    String readString30 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int clearDataPassStorage = clearDataPassStorage(readString30, readInt48, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearDataPassStorage);
                    return true;
                case 56:
                    int[] unlockedUsers = getUnlockedUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(unlockedUsers);
                    return true;
                case 57:
                    int readInt50 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    unlockCeStorage(readInt50, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    lockCeStorage(readInt51);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    String readString31 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareUserStorage(readString31, readInt52, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    String readString32 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorage(readString32, readInt54, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    String readString33 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    String readString34 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareSandboxForApp(readString33, readInt56, readString34, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySandboxForApp(readString35, readString36, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCheckpoint(readInt59);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    boolean needsCheckpoint = needsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsCheckpoint);
                    return true;
                case 65:
                    boolean needsRollback = needsRollback();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsRollback);
                    return true;
                case 66:
                    boolean isCheckpointing = isCheckpointing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCheckpointing);
                    return true;
                case 67:
                    String readString37 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    abortChanges(readString37, readBoolean8);
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
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreCheckpoint(readString38);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String readString39 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreCheckpointPart(readString39, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    markBootAttempt();
                    parcel2.writeNoException();
                    return true;
                case 73:
                    boolean supportsCheckpoint = supportsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsCheckpoint);
                    return true;
                case 74:
                    boolean supportsBlockCheckpoint = supportsBlockCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsBlockCheckpoint);
                    return true;
                case 75:
                    boolean supportsFileCheckpoint = supportsFileCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsFileCheckpoint);
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
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String createStubVolume = createStubVolume(readString40, readString41, readString42, readString43, readString44, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeString(createStubVolume);
                    return true;
                case 79:
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyStubVolume(readString45);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor openAppFuseFile = openAppFuseFile(readInt62, readInt63, readInt64, readInt65);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(openAppFuseFile);
                    return true;
                case 81:
                    boolean incFsEnabled = incFsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incFsEnabled);
                    return true;
                case 82:
                    String readString46 = parcel.readString();
                    String readString47 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IncrementalFileSystemControlParcel mountIncFs = mountIncFs(readString46, readString47, readInt66, readString48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mountIncFs, 1);
                    return true;
                case 83:
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmountIncFs(readString49);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    IncrementalFileSystemControlParcel incrementalFileSystemControlParcel = (IncrementalFileSystemControlParcel) parcel.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIncFsMountOptions(incrementalFileSystemControlParcel, readBoolean9, readBoolean10, readString50);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    bindMount(readString51, readString52);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyDsuMetadataKey(readString53);
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
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean writeBoosterBufferFlush = setWriteBoosterBufferFlush(readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeBoosterBufferFlush);
                    return true;
                case 92:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean writeBoosterBufferOn = setWriteBoosterBufferOn(readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeBoosterBufferOn);
                    return true;
                case 93:
                    int writeBoosterLifeTimeEstimate = getWriteBoosterLifeTimeEstimate();
                    parcel2.writeNoException();
                    parcel2.writeInt(writeBoosterLifeTimeEstimate);
                    return true;
                case 94:
                    IVoldTaskListener asInterface10 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    runIdleDefrag(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    long usedF2fsFileNode = getUsedF2fsFileNode();
                    parcel2.writeNoException();
                    parcel2.writeLong(usedF2fsFileNode);
                    return true;
                case 96:
                    String readString54 = parcel.readString();
                    String readString55 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    IVoldTaskListener asInterface11 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mvFileAtData(readString54, readString55, readInt67, readInt68, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String readString56 = parcel.readString();
                    String readString57 = parcel.readString();
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    IVoldTaskListener asInterface12 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cpFileAtData(readString56, readString57, readInt69, readInt70, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMpUidForFileSystem(readInt71);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    String readString58 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    String readString59 = parcel.readString();
                    String readString60 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecCreate(readString58, readInt72, readString59, readString60, readInt73, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    String readString61 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecResize(readString61, readInt74, readString62);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecFinalize(readString63);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String readString64 = parcel.readString();
                    int readInt75 = parcel.readInt();
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecFixperms(readString64, readInt75, readString65);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    String readString66 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecDestroy(readString66, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    String readString67 = parcel.readString();
                    String readString68 = parcel.readString();
                    int readInt76 = parcel.readInt();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecMount(readString67, readString68, readInt76, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    String readString69 = parcel.readString();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecUnmount(readString69, readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    String readString70 = parcel.readString();
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecRename(readString70, readString71);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String asecPath = asecPath(readString72);
                    parcel2.writeNoException();
                    parcel2.writeString(asecPath);
                    return true;
                case 108:
                    String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String asecFsPath = asecFsPath(readString73);
                    parcel2.writeNoException();
                    parcel2.writeString(asecFsPath);
                    return true;
                case 109:
                    String[] asecList = asecList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(asecList);
                    return true;
                case 110:
                    String readString74 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecTrim(readString74, readInt77, readString75);
                    parcel2.writeNoException();
                    return true;
                case 111:
                    String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int asecGetUsedSpace = asecGetUsedSpace(readString76);
                    parcel2.writeNoException();
                    parcel2.writeInt(asecGetUsedSpace);
                    return true;
                case 112:
                    int readInt78 = parcel.readInt();
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sensitive = setSensitive(readInt78, readString77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sensitive);
                    return true;
                case 113:
                    String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSensitive = isSensitive(readString78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSensitive);
                    return true;
                case 114:
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean mountSdpMediaStorageCmd = mountSdpMediaStorageCmd(readInt79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mountSdpMediaStorageCmd);
                    return true;
                case 115:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sdpPolicyCmd = setSdpPolicyCmd(readInt80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdpPolicyCmd);
                    return true;
                case 116:
                    int readInt81 = parcel.readInt();
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sdpPolicyToPathCmd = setSdpPolicyToPathCmd(readInt81, readString79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdpPolicyToPathCmd);
                    return true;
                case 117:
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyCmd = setDualDARPolicyCmd(readInt82, readInt83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyCmd);
                    return true;
                case 118:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean shrinkDataDdp = shrinkDataDdp(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shrinkDataDdp);
                    return true;
                case 119:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int reserveDataBlocks = reserveDataBlocks(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeInt(reserveDataBlocks);
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
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoldListener);
                    this.mRemote.transact(1, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortFuse() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void monitor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void reset() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void shutdown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserAdded(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserRemoved(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserStarted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserStopped(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void addAppIds(String[] strArr, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(10, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void addSandboxIds(int[] iArr, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(11, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onSecureKeyguardStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void partition(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void forgetPartition(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iVoldMountCallback);
                    this.mRemote.transact(15, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void format(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(18, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(19, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void remountUid(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void remountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(21, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountAppStorageDirs(int i, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(22, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setupAppDir(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fixupAppDir(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void ensureAppDirsCreated(String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String createObb(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyObb(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(28, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(29, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortIdleMaint(IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(30, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getStorageLifeTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(32, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void refreshLatestWrite() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteAmount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public FileDescriptor mountAppFuse(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountAppFuse(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void sdeEnable(String str, int i, int i2, boolean z, IVoldTaskListener iVoldTaskListener, IVoldMountCallback iVoldMountCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    obtain.writeStrongInterface(iVoldMountCallback);
                    this.mRemote.transact(37, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fbeEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void initUser0() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mountFstab(String str, String str2, boolean z, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(40, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr, boolean[] zArr, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z2);
                    obtain.writeStringArray(strArr);
                    obtain.writeBooleanArray(zArr);
                    obtain.writeLong(j);
                    this.mRemote.transact(41, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setStorageBindingSeed(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(42, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void createUserStorageKeys(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyUserStorageKeys(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setCeStorageProtection(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(45, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isPassClients(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isClearDataExceptionsPass(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isUninstallExceptionsPass(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int createPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int destroyPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int lockPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int unlockPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(52, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String getPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(53, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isPassUnlocked(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(54, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int clearDataPassStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int[] getUnlockedUsers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unlockCeStorage(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(57, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void lockCeStorage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(59, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyUserStorage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(60, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareSandboxForApp(String str, int i, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(61, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroySandboxForApp(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void startCheckpoint(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean needsCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean needsRollback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isCheckpointing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortChanges(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void commitChanges() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void restoreCheckpoint(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(70, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void restoreCheckpointPart(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void markBootAttempt() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsBlockCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsFileCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void resetCheckpoint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void earlyBootEnded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String createStubVolume(String str, String str2, String str3, String str4, String str5, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyStubVolume(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(79, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(80, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean incFsEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public IncrementalFileSystemControlParcel mountIncFs(String str, String str2, int i, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(82, obtain, obtain2, 32);
                    obtain2.readException();
                    return (IncrementalFileSystemControlParcel) obtain2.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountIncFs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setIncFsMountOptions(IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incrementalFileSystemControlParcel, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    this.mRemote.transact(84, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void bindMount(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(85, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyDsuMetadataKey(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public long getStorageSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getStorageRemainingLifetime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteBoosterBufferSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteBoosterBufferAvailablePercent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setWriteBoosterBufferFlush(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(91, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setWriteBoosterBufferOn(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(92, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteBoosterLifeTimeEstimate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void runIdleDefrag(IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(94, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public long getUsedF2fsFileNode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mvFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(96, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(97, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setMpUidForFileSystem(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(98, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecResize(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(100, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecFinalize(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(101, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecFixperms(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(102, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecDestroy(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(103, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecMount(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecUnmount(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(105, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecRename(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(106, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String asecPath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(107, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String asecFsPath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(108, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public String[] asecList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(109, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void asecTrim(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(110, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int asecGetUsedSpace(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(111, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setSensitive(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(112, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isSensitive(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(113, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean mountSdpMediaStorageCmd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setSdpPolicyCmd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(115, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setSdpPolicyToPathCmd(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(116, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean setDualDARPolicyCmd(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(117, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean shrinkDataDdp(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(118, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int reserveDataBlocks(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(119, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
