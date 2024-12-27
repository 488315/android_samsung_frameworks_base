package android.os;

import android.os.IVoldListener;
import android.os.IVoldMountCallback;
import android.os.IVoldTaskListener;
import android.os.incremental.IncrementalFileSystemControlParcel;
import java.io.FileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IVold {
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
        public void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr) throws RemoteException {
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
        public boolean isPassUnlocked(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isSensitive(String str) throws RemoteException {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IVold {
        public static final String DESCRIPTOR = "android.os.IVold";
        static final int TRANSACTION_abortChanges = 63;
        static final int TRANSACTION_abortFuse = 2;
        static final int TRANSACTION_abortIdleMaint = 30;
        static final int TRANSACTION_addAppIds = 10;
        static final int TRANSACTION_addSandboxIds = 11;
        static final int TRANSACTION_asecCreate = 90;
        static final int TRANSACTION_asecDestroy = 94;
        static final int TRANSACTION_asecFinalize = 92;
        static final int TRANSACTION_asecFixperms = 93;
        static final int TRANSACTION_asecFsPath = 99;
        static final int TRANSACTION_asecGetUsedSpace = 102;
        static final int TRANSACTION_asecList = 100;
        static final int TRANSACTION_asecMount = 95;
        static final int TRANSACTION_asecPath = 98;
        static final int TRANSACTION_asecRename = 97;
        static final int TRANSACTION_asecResize = 91;
        static final int TRANSACTION_asecTrim = 101;
        static final int TRANSACTION_asecUnmount = 96;
        static final int TRANSACTION_benchmark = 18;
        static final int TRANSACTION_bindMount = 81;
        static final int TRANSACTION_commitChanges = 64;
        static final int TRANSACTION_cpFileAtData = 88;
        static final int TRANSACTION_createObb = 26;
        static final int TRANSACTION_createPassStorage = 46;
        static final int TRANSACTION_createStubVolume = 74;
        static final int TRANSACTION_createUserStorageKeys = 43;
        static final int TRANSACTION_destroyDsuMetadataKey = 82;
        static final int TRANSACTION_destroyObb = 27;
        static final int TRANSACTION_destroyPassStorage = 47;
        static final int TRANSACTION_destroySandboxForApp = 58;
        static final int TRANSACTION_destroyStubVolume = 75;
        static final int TRANSACTION_destroyUserStorage = 56;
        static final int TRANSACTION_destroyUserStorageKeys = 44;
        static final int TRANSACTION_earlyBootEnded = 73;
        static final int TRANSACTION_encryptFstab = 41;
        static final int TRANSACTION_ensureAppDirsCreated = 25;
        static final int TRANSACTION_fbeEnable = 38;
        static final int TRANSACTION_fixupAppDir = 24;
        static final int TRANSACTION_forgetPartition = 14;
        static final int TRANSACTION_format = 17;
        static final int TRANSACTION_fstrim = 28;
        static final int TRANSACTION_getPassStorage = 50;
        static final int TRANSACTION_getStorageLifeTime = 31;
        static final int TRANSACTION_getStorageRemainingLifetime = 84;
        static final int TRANSACTION_getStorageSize = 83;
        static final int TRANSACTION_getUnlockedUsers = 52;
        static final int TRANSACTION_getUsedF2fsFileNode = 86;
        static final int TRANSACTION_getWriteAmount = 34;
        static final int TRANSACTION_incFsEnabled = 77;
        static final int TRANSACTION_initUser0 = 39;
        static final int TRANSACTION_isCheckpointing = 62;
        static final int TRANSACTION_isPassUnlocked = 51;
        static final int TRANSACTION_isSensitive = 104;
        static final int TRANSACTION_lockCeStorage = 54;
        static final int TRANSACTION_lockPassStorage = 48;
        static final int TRANSACTION_markBootAttempt = 68;
        static final int TRANSACTION_monitor = 3;
        static final int TRANSACTION_mount = 15;
        static final int TRANSACTION_mountAppFuse = 35;
        static final int TRANSACTION_mountFstab = 40;
        static final int TRANSACTION_mountIncFs = 78;
        static final int TRANSACTION_mountSdpMediaStorageCmd = 105;
        static final int TRANSACTION_moveStorage = 19;
        static final int TRANSACTION_mvFileAtData = 87;
        static final int TRANSACTION_needsCheckpoint = 60;
        static final int TRANSACTION_needsRollback = 61;
        static final int TRANSACTION_onSecureKeyguardStateChanged = 12;
        static final int TRANSACTION_onUserAdded = 6;
        static final int TRANSACTION_onUserRemoved = 7;
        static final int TRANSACTION_onUserStarted = 8;
        static final int TRANSACTION_onUserStopped = 9;
        static final int TRANSACTION_openAppFuseFile = 76;
        static final int TRANSACTION_partition = 13;
        static final int TRANSACTION_prepareCheckpoint = 65;
        static final int TRANSACTION_prepareSandboxForApp = 57;
        static final int TRANSACTION_prepareUserStorage = 55;
        static final int TRANSACTION_refreshLatestWrite = 33;
        static final int TRANSACTION_remountAppStorageDirs = 21;
        static final int TRANSACTION_remountUid = 20;
        static final int TRANSACTION_reserveDataBlocks = 110;
        static final int TRANSACTION_reset = 4;
        static final int TRANSACTION_resetCheckpoint = 72;
        static final int TRANSACTION_restoreCheckpoint = 66;
        static final int TRANSACTION_restoreCheckpointPart = 67;
        static final int TRANSACTION_runIdleDefrag = 85;
        static final int TRANSACTION_runIdleMaint = 29;
        static final int TRANSACTION_sdeEnable = 37;
        static final int TRANSACTION_setCeStorageProtection = 45;
        static final int TRANSACTION_setDualDARPolicyCmd = 108;
        static final int TRANSACTION_setGCUrgentPace = 32;
        static final int TRANSACTION_setIncFsMountOptions = 80;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setMpUidForFileSystem = 89;
        static final int TRANSACTION_setSdpPolicyCmd = 106;
        static final int TRANSACTION_setSdpPolicyToPathCmd = 107;
        static final int TRANSACTION_setSensitive = 103;
        static final int TRANSACTION_setStorageBindingSeed = 42;
        static final int TRANSACTION_setupAppDir = 23;
        static final int TRANSACTION_shrinkDataDdp = 109;
        static final int TRANSACTION_shutdown = 5;
        static final int TRANSACTION_startCheckpoint = 59;
        static final int TRANSACTION_supportsBlockCheckpoint = 70;
        static final int TRANSACTION_supportsCheckpoint = 69;
        static final int TRANSACTION_supportsFileCheckpoint = 71;
        static final int TRANSACTION_unlockCeStorage = 53;
        static final int TRANSACTION_unlockPassStorage = 49;
        static final int TRANSACTION_unmount = 16;
        static final int TRANSACTION_unmountAppFuse = 36;
        static final int TRANSACTION_unmountAppStorageDirs = 22;
        static final int TRANSACTION_unmountIncFs = 79;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IVold {
            public IBinder mRemote;

            @Override // android.os.IVold
            public final void abortChanges(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(63, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void abortFuse() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void abortIdleMaint(IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void addAppIds(String[] strArr, int[] iArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void addSandboxIds(int[] iArr, String[] strArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.os.IVold
            public final void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
                    this.mRemote.transact(90, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecDestroy(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(94, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecFinalize(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecFixperms(String str, int i, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(93, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final String asecFsPath(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final int asecGetUsedSpace(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(102, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final String[] asecList() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecMount(String str, String str2, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(95, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final String asecPath(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(98, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecRename(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(97, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecResize(String str, int i, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(91, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecTrim(String str, int i, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(101, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void asecUnmount(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(96, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void benchmark(String str, IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void bindMount(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(81, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void commitChanges() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(88, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final String createObb(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int createPassStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(46, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final String createStubVolume(String str, String str2, String str3, String str4, String str5, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
                    this.mRemote.transact(74, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void createUserStorageKeys(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void destroyDsuMetadataKey(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(82, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void destroyObb(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int destroyPassStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void destroySandboxForApp(String str, String str2, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void destroyStubVolume(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void destroyUserStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(56, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void destroyUserStorageKeys(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void earlyBootEnded() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
                    this.mRemote.transact(41, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void ensureAppDirsCreated(String[] strArr, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void fbeEnable() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void fixupAppDir(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void forgetPartition(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void format(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void fstrim(int i, IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final String getPassStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final int getStorageLifeTime() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int getStorageRemainingLifetime() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final long getStorageSize() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final int[] getUnlockedUsers() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final long getUsedF2fsFileNode() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(86, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final int getWriteAmount() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean incFsEnabled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void initUser0() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isCheckpointing() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean isPassUnlocked(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean isSensitive(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(104, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void lockCeStorage(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final int lockPassStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void markBootAttempt() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void monitor() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final FileDescriptor mountAppFuse(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void mountFstab(String str, String str2, boolean z, String[] strArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final IncrementalFileSystemControlParcel mountIncFs(String str, String str2, int i, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(78, obtain, obtain2, 32);
                    obtain2.readException();
                    return (IncrementalFileSystemControlParcel) obtain2.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean mountSdpMediaStorageCmd(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void mvFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(87, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean needsCheckpoint() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean needsRollback() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void onSecureKeyguardStateChanged(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void onUserAdded(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void onUserRemoved(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void onUserStarted(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void onUserStopped(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(76, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void partition(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void prepareCheckpoint() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void prepareSandboxForApp(String str, int i, String str2, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void prepareUserStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void refreshLatestWrite() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void remountAppStorageDirs(int i, int i2, String[] strArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void remountUid(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int reserveDataBlocks(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(110, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void reset() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void resetCheckpoint() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void restoreCheckpoint(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void restoreCheckpointPart(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void runIdleDefrag(IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(85, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void sdeEnable(String str, int i, int i2, boolean z, IVoldTaskListener iVoldTaskListener, IVoldMountCallback iVoldMountCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setCeStorageProtection(int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean setDualDARPolicyCmd(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(108, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setIncFsMountOptions(IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incrementalFileSystemControlParcel, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    this.mRemote.transact(80, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void setListener(IVoldListener iVoldListener) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setMpUidForFileSystem(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean setSdpPolicyCmd(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean setSdpPolicyToPathCmd(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(107, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean setSensitive(int i, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(103, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void setStorageBindingSeed(byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setupAppDir(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean shrinkDataDdp(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(109, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void shutdown() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void startCheckpoint(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean supportsBlockCheckpoint() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean supportsCheckpoint() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final boolean supportsFileCheckpoint() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(71, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final void unlockCeStorage(int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(53, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public final int unlockPassStorage(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void unmount(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void unmountAppFuse(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void unmountAppStorageDirs(int i, int i2, String[] strArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void unmountIncFs(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            Proxy proxy = new Proxy();
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
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
                    parcel.enforceNoDataAvail();
                    encryptFstab(readString18, readString19, readBoolean5, readString20, readBoolean6, createStringArray7);
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
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createPassStorage = createPassStorage(readString21, readInt36, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPassStorage);
                    return true;
                case 47:
                    String readString22 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int destroyPassStorage = destroyPassStorage(readString22, readInt38, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeInt(destroyPassStorage);
                    return true;
                case 48:
                    String readString23 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockPassStorage = lockPassStorage(readString23, readInt40, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockPassStorage);
                    return true;
                case 49:
                    String readString24 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int unlockPassStorage = unlockPassStorage(readString24, readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockPassStorage);
                    return true;
                case 50:
                    String readString25 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String passStorage = getPassStorage(readString25, readInt44, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeString(passStorage);
                    return true;
                case 51:
                    String readString26 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPassUnlocked = isPassUnlocked(readString26, readInt46, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPassUnlocked);
                    return true;
                case 52:
                    int[] unlockedUsers = getUnlockedUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(unlockedUsers);
                    return true;
                case 53:
                    int readInt48 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    unlockCeStorage(readInt48, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    lockCeStorage(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    String readString27 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareUserStorage(readString27, readInt50, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String readString28 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorage(readString28, readInt52, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    String readString29 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    String readString30 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareSandboxForApp(readString29, readInt54, readString30, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySandboxForApp(readString31, readString32, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCheckpoint(readInt57);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    boolean needsCheckpoint = needsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsCheckpoint);
                    return true;
                case 61:
                    boolean needsRollback = needsRollback();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsRollback);
                    return true;
                case 62:
                    boolean isCheckpointing = isCheckpointing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCheckpointing);
                    return true;
                case 63:
                    String readString33 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    abortChanges(readString33, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    commitChanges();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    prepareCheckpoint();
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreCheckpoint(readString34);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    String readString35 = parcel.readString();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreCheckpointPart(readString35, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    markBootAttempt();
                    parcel2.writeNoException();
                    return true;
                case 69:
                    boolean supportsCheckpoint = supportsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsCheckpoint);
                    return true;
                case 70:
                    boolean supportsBlockCheckpoint = supportsBlockCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsBlockCheckpoint);
                    return true;
                case 71:
                    boolean supportsFileCheckpoint = supportsFileCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsFileCheckpoint);
                    return true;
                case 72:
                    resetCheckpoint();
                    parcel2.writeNoException();
                    return true;
                case 73:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 74:
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String createStubVolume = createStubVolume(readString36, readString37, readString38, readString39, readString40, readInt59);
                    parcel2.writeNoException();
                    parcel2.writeString(createStubVolume);
                    return true;
                case 75:
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyStubVolume(readString41);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor openAppFuseFile = openAppFuseFile(readInt60, readInt61, readInt62, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(openAppFuseFile);
                    return true;
                case 77:
                    boolean incFsEnabled = incFsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incFsEnabled);
                    return true;
                case 78:
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IncrementalFileSystemControlParcel mountIncFs = mountIncFs(readString42, readString43, readInt64, readString44);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mountIncFs, 1);
                    return true;
                case 79:
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmountIncFs(readString45);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    IncrementalFileSystemControlParcel incrementalFileSystemControlParcel = (IncrementalFileSystemControlParcel) parcel.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIncFsMountOptions(incrementalFileSystemControlParcel, readBoolean9, readBoolean10, readString46);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    bindMount(readString47, readString48);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyDsuMetadataKey(readString49);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    long storageSize = getStorageSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(storageSize);
                    return true;
                case 84:
                    int storageRemainingLifetime = getStorageRemainingLifetime();
                    parcel2.writeNoException();
                    parcel2.writeInt(storageRemainingLifetime);
                    return true;
                case 85:
                    IVoldTaskListener asInterface10 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    runIdleDefrag(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    long usedF2fsFileNode = getUsedF2fsFileNode();
                    parcel2.writeNoException();
                    parcel2.writeLong(usedF2fsFileNode);
                    return true;
                case 87:
                    String readString50 = parcel.readString();
                    String readString51 = parcel.readString();
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    IVoldTaskListener asInterface11 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mvFileAtData(readString50, readString51, readInt65, readInt66, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    String readString52 = parcel.readString();
                    String readString53 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    IVoldTaskListener asInterface12 = IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cpFileAtData(readString52, readString53, readInt67, readInt68, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMpUidForFileSystem(readInt69);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    String readString54 = parcel.readString();
                    int readInt70 = parcel.readInt();
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    int readInt71 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecCreate(readString54, readInt70, readString55, readString56, readInt71, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    String readString57 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    String readString58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecResize(readString57, readInt72, readString58);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecFinalize(readString59);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    String readString60 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecFixperms(readString60, readInt73, readString61);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String readString62 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecDestroy(readString62, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    String readString63 = parcel.readString();
                    String readString64 = parcel.readString();
                    int readInt74 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecMount(readString63, readString64, readInt74, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    String readString65 = parcel.readString();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    asecUnmount(readString65, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    String readString66 = parcel.readString();
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecRename(readString66, readString67);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String asecPath = asecPath(readString68);
                    parcel2.writeNoException();
                    parcel2.writeString(asecPath);
                    return true;
                case 99:
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String asecFsPath = asecFsPath(readString69);
                    parcel2.writeNoException();
                    parcel2.writeString(asecFsPath);
                    return true;
                case 100:
                    String[] asecList = asecList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(asecList);
                    return true;
                case 101:
                    String readString70 = parcel.readString();
                    int readInt75 = parcel.readInt();
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    asecTrim(readString70, readInt75, readString71);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int asecGetUsedSpace = asecGetUsedSpace(readString72);
                    parcel2.writeNoException();
                    parcel2.writeInt(asecGetUsedSpace);
                    return true;
                case 103:
                    int readInt76 = parcel.readInt();
                    String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sensitive = setSensitive(readInt76, readString73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sensitive);
                    return true;
                case 104:
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSensitive = isSensitive(readString74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSensitive);
                    return true;
                case 105:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean mountSdpMediaStorageCmd = mountSdpMediaStorageCmd(readInt77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mountSdpMediaStorageCmd);
                    return true;
                case 106:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sdpPolicyCmd = setSdpPolicyCmd(readInt78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdpPolicyCmd);
                    return true;
                case 107:
                    int readInt79 = parcel.readInt();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sdpPolicyToPathCmd = setSdpPolicyToPathCmd(readInt79, readString75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sdpPolicyToPathCmd);
                    return true;
                case 108:
                    int readInt80 = parcel.readInt();
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dualDARPolicyCmd = setDualDARPolicyCmd(readInt80, readInt81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dualDARPolicyCmd);
                    return true;
                case 109:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean shrinkDataDdp = shrinkDataDdp(readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shrinkDataDdp);
                    return true;
                case 110:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int reserveDataBlocks = reserveDataBlocks(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeInt(reserveDataBlocks);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
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

    void encryptFstab(String str, String str2, boolean z, String str3, boolean z2, String[] strArr) throws RemoteException;

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

    boolean incFsEnabled() throws RemoteException;

    void initUser0() throws RemoteException;

    boolean isCheckpointing() throws RemoteException;

    boolean isPassUnlocked(String str, int i, int i2) throws RemoteException;

    boolean isSensitive(String str) throws RemoteException;

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
}
