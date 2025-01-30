package android.p005os;

import android.os.incremental.IncrementalFileSystemControlParcel;
import java.io.FileDescriptor;

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

  public class Default implements IVold {
    @Override // android.p005os.IVold
    public void abortChanges(String str, boolean z) {}

    @Override // android.p005os.IVold
    public void abortFuse() {}

    @Override // android.p005os.IVold
    public void abortIdleMaint(IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public void addAppIds(String[] strArr, int[] iArr) {}

    @Override // android.p005os.IVold
    public void addSandboxIds(int[] iArr, String[] strArr) {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.p005os.IVold
    public void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) {}

    @Override // android.p005os.IVold
    public void asecDestroy(String str, boolean z) {}

    @Override // android.p005os.IVold
    public void asecFinalize(String str) {}

    @Override // android.p005os.IVold
    public void asecFixperms(String str, int i, String str2) {}

    @Override // android.p005os.IVold
    public String asecFsPath(String str) {
      return null;
    }

    @Override // android.p005os.IVold
    public int asecGetUsedSpace(String str) {
      return 0;
    }

    @Override // android.p005os.IVold
    public String[] asecList() {
      return null;
    }

    @Override // android.p005os.IVold
    public void asecMount(String str, String str2, int i, boolean z) {}

    @Override // android.p005os.IVold
    public String asecPath(String str) {
      return null;
    }

    @Override // android.p005os.IVold
    public void asecRename(String str, String str2) {}

    @Override // android.p005os.IVold
    public void asecResize(String str, int i, String str2) {}

    @Override // android.p005os.IVold
    public void asecTrim(String str, int i, String str2) {}

    @Override // android.p005os.IVold
    public void asecUnmount(String str, boolean z) {}

    @Override // android.p005os.IVold
    public void benchmark(String str, IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public void bindMount(String str, String str2) {}

    @Override // android.p005os.IVold
    public void commitChanges() {}

    @Override // android.p005os.IVold
    public void cpFileAtData(
        String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public String createObb(String str, int i) {
      return null;
    }

    @Override // android.p005os.IVold
    public String createStubVolume(
        String str, String str2, String str3, String str4, String str5, int i) {
      return null;
    }

    @Override // android.p005os.IVold
    public void createUserKey(int i, int i2, boolean z) {}

    @Override // android.p005os.IVold
    public void destroyDsuMetadataKey(String str) {}

    @Override // android.p005os.IVold
    public void destroyObb(String str) {}

    @Override // android.p005os.IVold
    public void destroySandboxForApp(String str, String str2, int i) {}

    @Override // android.p005os.IVold
    public void destroyStubVolume(String str) {}

    @Override // android.p005os.IVold
    public void destroyUserKey(int i) {}

    @Override // android.p005os.IVold
    public void destroyUserStorage(String str, int i, int i2) {}

    @Override // android.p005os.IVold
    public void earlyBootEnded() {}

    @Override // android.p005os.IVold
    public void encryptFstab(String str, String str2, boolean z, String str3, String str4) {}

    @Override // android.p005os.IVold
    public void ensureAppDirsCreated(String[] strArr, int i) {}

    @Override // android.p005os.IVold
    public void fbeEnable() {}

    @Override // android.p005os.IVold
    public void fixupAppDir(String str, int i) {}

    @Override // android.p005os.IVold
    public void forgetPartition(String str, String str2) {}

    @Override // android.p005os.IVold
    public void format(String str, String str2) {}

    @Override // android.p005os.IVold
    public void fstrim(int i, IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public int getStorageLifeTime() {
      return 0;
    }

    @Override // android.p005os.IVold
    public int[] getUnlockedUsers() {
      return null;
    }

    @Override // android.p005os.IVold
    public long getUsedF2fsFileNode() {
      return 0L;
    }

    @Override // android.p005os.IVold
    public int getWriteAmount() {
      return 0;
    }

    @Override // android.p005os.IVold
    public boolean incFsEnabled() {
      return false;
    }

    @Override // android.p005os.IVold
    public void initUser0() {}

    @Override // android.p005os.IVold
    public boolean isCheckpointing() {
      return false;
    }

    @Override // android.p005os.IVold
    public boolean isSensitive(String str) {
      return false;
    }

    @Override // android.p005os.IVold
    public void lockUserKey(int i) {}

    @Override // android.p005os.IVold
    public void markBootAttempt() {}

    @Override // android.p005os.IVold
    public void monitor() {}

    @Override // android.p005os.IVold
    public void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) {}

    @Override // android.p005os.IVold
    public FileDescriptor mountAppFuse(int i, int i2) {
      return null;
    }

    @Override // android.p005os.IVold
    public void mountFstab(String str, String str2, String str3) {}

    @Override // android.p005os.IVold
    public IncrementalFileSystemControlParcel mountIncFs(
        String str, String str2, int i, String str3) {
      return null;
    }

    @Override // android.p005os.IVold
    public boolean mountSdpMediaStorageCmd(int i) {
      return false;
    }

    @Override // android.p005os.IVold
    public void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public void mvFileAtData(
        String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public boolean needsCheckpoint() {
      return false;
    }

    @Override // android.p005os.IVold
    public boolean needsRollback() {
      return false;
    }

    @Override // android.p005os.IVold
    public void onSecureKeyguardStateChanged(boolean z) {}

    @Override // android.p005os.IVold
    public void onUserAdded(int i, int i2, int i3) {}

    @Override // android.p005os.IVold
    public void onUserRemoved(int i) {}

    @Override // android.p005os.IVold
    public void onUserStarted(int i) {}

    @Override // android.p005os.IVold
    public void onUserStopped(int i) {}

    @Override // android.p005os.IVold
    public FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) {
      return null;
    }

    @Override // android.p005os.IVold
    public void partition(String str, int i, int i2) {}

    @Override // android.p005os.IVold
    public void prepareCheckpoint() {}

    @Override // android.p005os.IVold
    public void prepareSandboxForApp(String str, int i, String str2, int i2) {}

    @Override // android.p005os.IVold
    public void prepareUserStorage(String str, int i, int i2, int i3) {}

    @Override // android.p005os.IVold
    public void refreshLatestWrite() {}

    @Override // android.p005os.IVold
    public void remountAppStorageDirs(int i, int i2, String[] strArr) {}

    @Override // android.p005os.IVold
    public void remountUid(int i, int i2) {}

    @Override // android.p005os.IVold
    public int reserveDataBlocks(long j) {
      return 0;
    }

    @Override // android.p005os.IVold
    public void reset() {}

    @Override // android.p005os.IVold
    public void resetCheckpoint() {}

    @Override // android.p005os.IVold
    public void restoreCheckpoint(String str) {}

    @Override // android.p005os.IVold
    public void restoreCheckpointPart(String str, int i) {}

    @Override // android.p005os.IVold
    public void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) {}

    @Override // android.p005os.IVold
    public void sdeEnable(
        String str,
        int i,
        int i2,
        boolean z,
        IVoldTaskListener iVoldTaskListener,
        IVoldMountCallback iVoldMountCallback) {}

    @Override // android.p005os.IVold
    public void sdeMoveMountHidden(
        String str, int i, int i2, IVoldMountCallback iVoldMountCallback) {}

    @Override // android.p005os.IVold
    public void setDebugForExternal(String str) {}

    @Override // android.p005os.IVold
    public boolean setDualDARPolicyCmd(int i, int i2) {
      return false;
    }

    @Override // android.p005os.IVold
    public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) {}

    @Override // android.p005os.IVold
    public void setIncFsMountOptions(
        IncrementalFileSystemControlParcel incrementalFileSystemControlParcel,
        boolean z,
        boolean z2,
        String str) {}

    @Override // android.p005os.IVold
    public void setListener(IVoldListener iVoldListener) {}

    @Override // android.p005os.IVold
    public void setMpUidForFileSystem(int i) {}

    @Override // android.p005os.IVold
    public boolean setSdpPolicyCmd(int i) {
      return false;
    }

    @Override // android.p005os.IVold
    public boolean setSdpPolicyToPathCmd(int i, String str) {
      return false;
    }

    @Override // android.p005os.IVold
    public boolean setSensitive(int i, String str) {
      return false;
    }

    @Override // android.p005os.IVold
    public void setStorageBindingSeed(byte[] bArr) {}

    @Override // android.p005os.IVold
    public void setUserKeyProtection(int i, String str) {}

    @Override // android.p005os.IVold
    public void setupAppDir(String str, int i) {}

    @Override // android.p005os.IVold
    public boolean shrinkDataDdp(long j) {
      return false;
    }

    @Override // android.p005os.IVold
    public void shutdown() {}

    @Override // android.p005os.IVold
    public void startCheckpoint(int i) {}

    @Override // android.p005os.IVold
    public boolean supportsBlockCheckpoint() {
      return false;
    }

    @Override // android.p005os.IVold
    public boolean supportsCheckpoint() {
      return false;
    }

    @Override // android.p005os.IVold
    public boolean supportsFileCheckpoint() {
      return false;
    }

    @Override // android.p005os.IVold
    public void unlockUserKey(int i, int i2, String str, String str2) {}

    @Override // android.p005os.IVold
    public void unmount(String str) {}

    @Override // android.p005os.IVold
    public void unmountAppFuse(int i, int i2) {}

    @Override // android.p005os.IVold
    public void unmountAppStorageDirs(int i, int i2, String[] strArr) {}

    @Override // android.p005os.IVold
    public void unmountIncFs(String str) {}
  }

  void abortChanges(String str, boolean z);

  void abortFuse();

  void abortIdleMaint(IVoldTaskListener iVoldTaskListener);

  void addAppIds(String[] strArr, int[] iArr);

  void addSandboxIds(int[] iArr, String[] strArr);

  void asecCreate(String str, int i, String str2, String str3, int i2, boolean z);

  void asecDestroy(String str, boolean z);

  void asecFinalize(String str);

  void asecFixperms(String str, int i, String str2);

  String asecFsPath(String str);

  int asecGetUsedSpace(String str);

  String[] asecList();

  void asecMount(String str, String str2, int i, boolean z);

  String asecPath(String str);

  void asecRename(String str, String str2);

  void asecResize(String str, int i, String str2);

  void asecTrim(String str, int i, String str2);

  void asecUnmount(String str, boolean z);

  void benchmark(String str, IVoldTaskListener iVoldTaskListener);

  void bindMount(String str, String str2);

  void commitChanges();

  void cpFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener);

  String createObb(String str, int i);

  String createStubVolume(String str, String str2, String str3, String str4, String str5, int i);

  void createUserKey(int i, int i2, boolean z);

  void destroyDsuMetadataKey(String str);

  void destroyObb(String str);

  void destroySandboxForApp(String str, String str2, int i);

  void destroyStubVolume(String str);

  void destroyUserKey(int i);

  void destroyUserStorage(String str, int i, int i2);

  void earlyBootEnded();

  void encryptFstab(String str, String str2, boolean z, String str3, String str4);

  void ensureAppDirsCreated(String[] strArr, int i);

  void fbeEnable();

  void fixupAppDir(String str, int i);

  void forgetPartition(String str, String str2);

  void format(String str, String str2);

  void fstrim(int i, IVoldTaskListener iVoldTaskListener);

  int getStorageLifeTime();

  int[] getUnlockedUsers();

  long getUsedF2fsFileNode();

  int getWriteAmount();

  boolean incFsEnabled();

  void initUser0();

  boolean isCheckpointing();

  boolean isSensitive(String str);

  void lockUserKey(int i);

  void markBootAttempt();

  void monitor();

  void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback);

  FileDescriptor mountAppFuse(int i, int i2);

  void mountFstab(String str, String str2, String str3);

  IncrementalFileSystemControlParcel mountIncFs(String str, String str2, int i, String str3);

  boolean mountSdpMediaStorageCmd(int i);

  void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener);

  void mvFileAtData(String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener);

  boolean needsCheckpoint();

  boolean needsRollback();

  void onSecureKeyguardStateChanged(boolean z);

  void onUserAdded(int i, int i2, int i3);

  void onUserRemoved(int i);

  void onUserStarted(int i);

  void onUserStopped(int i);

  FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4);

  void partition(String str, int i, int i2);

  void prepareCheckpoint();

  void prepareSandboxForApp(String str, int i, String str2, int i2);

  void prepareUserStorage(String str, int i, int i2, int i3);

  void refreshLatestWrite();

  void remountAppStorageDirs(int i, int i2, String[] strArr);

  void remountUid(int i, int i2);

  int reserveDataBlocks(long j);

  void reset();

  void resetCheckpoint();

  void restoreCheckpoint(String str);

  void restoreCheckpointPart(String str, int i);

  void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener);

  void sdeEnable(
      String str,
      int i,
      int i2,
      boolean z,
      IVoldTaskListener iVoldTaskListener,
      IVoldMountCallback iVoldMountCallback);

  void sdeMoveMountHidden(String str, int i, int i2, IVoldMountCallback iVoldMountCallback);

  void setDebugForExternal(String str);

  boolean setDualDARPolicyCmd(int i, int i2);

  void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5);

  void setIncFsMountOptions(
      IncrementalFileSystemControlParcel incrementalFileSystemControlParcel,
      boolean z,
      boolean z2,
      String str);

  void setListener(IVoldListener iVoldListener);

  void setMpUidForFileSystem(int i);

  boolean setSdpPolicyCmd(int i);

  boolean setSdpPolicyToPathCmd(int i, String str);

  boolean setSensitive(int i, String str);

  void setStorageBindingSeed(byte[] bArr);

  void setUserKeyProtection(int i, String str);

  void setupAppDir(String str, int i);

  boolean shrinkDataDdp(long j);

  void shutdown();

  void startCheckpoint(int i);

  boolean supportsBlockCheckpoint();

  boolean supportsCheckpoint();

  boolean supportsFileCheckpoint();

  void unlockUserKey(int i, int i2, String str, String str2);

  void unmount(String str);

  void unmountAppFuse(int i, int i2);

  void unmountAppStorageDirs(int i, int i2, String[] strArr);

  void unmountIncFs(String str);

  public abstract class Stub extends Binder implements IVold {
    public static final String DESCRIPTOR = "android.os.IVold";
    public static final int TRANSACTION_abortChanges = 59;
    public static final int TRANSACTION_abortFuse = 2;
    public static final int TRANSACTION_abortIdleMaint = 30;
    public static final int TRANSACTION_addAppIds = 10;
    public static final int TRANSACTION_addSandboxIds = 11;
    public static final int TRANSACTION_asecCreate = 83;
    public static final int TRANSACTION_asecDestroy = 87;
    public static final int TRANSACTION_asecFinalize = 85;
    public static final int TRANSACTION_asecFixperms = 86;
    public static final int TRANSACTION_asecFsPath = 92;
    public static final int TRANSACTION_asecGetUsedSpace = 95;
    public static final int TRANSACTION_asecList = 93;
    public static final int TRANSACTION_asecMount = 88;
    public static final int TRANSACTION_asecPath = 91;
    public static final int TRANSACTION_asecRename = 90;
    public static final int TRANSACTION_asecResize = 84;
    public static final int TRANSACTION_asecTrim = 94;
    public static final int TRANSACTION_asecUnmount = 89;
    public static final int TRANSACTION_benchmark = 18;
    public static final int TRANSACTION_bindMount = 77;
    public static final int TRANSACTION_commitChanges = 60;
    public static final int TRANSACTION_cpFileAtData = 81;
    public static final int TRANSACTION_createObb = 26;
    public static final int TRANSACTION_createStubVolume = 70;
    public static final int TRANSACTION_createUserKey = 44;
    public static final int TRANSACTION_destroyDsuMetadataKey = 78;
    public static final int TRANSACTION_destroyObb = 27;
    public static final int TRANSACTION_destroySandboxForApp = 54;
    public static final int TRANSACTION_destroyStubVolume = 71;
    public static final int TRANSACTION_destroyUserKey = 45;
    public static final int TRANSACTION_destroyUserStorage = 52;
    public static final int TRANSACTION_earlyBootEnded = 69;
    public static final int TRANSACTION_encryptFstab = 42;
    public static final int TRANSACTION_ensureAppDirsCreated = 25;
    public static final int TRANSACTION_fbeEnable = 39;
    public static final int TRANSACTION_fixupAppDir = 24;
    public static final int TRANSACTION_forgetPartition = 14;
    public static final int TRANSACTION_format = 17;
    public static final int TRANSACTION_fstrim = 28;
    public static final int TRANSACTION_getStorageLifeTime = 31;
    public static final int TRANSACTION_getUnlockedUsers = 47;
    public static final int TRANSACTION_getUsedF2fsFileNode = 79;
    public static final int TRANSACTION_getWriteAmount = 34;
    public static final int TRANSACTION_incFsEnabled = 73;
    public static final int TRANSACTION_initUser0 = 40;
    public static final int TRANSACTION_isCheckpointing = 58;
    public static final int TRANSACTION_isSensitive = 97;
    public static final int TRANSACTION_lockUserKey = 49;
    public static final int TRANSACTION_markBootAttempt = 64;
    public static final int TRANSACTION_monitor = 3;
    public static final int TRANSACTION_mount = 15;
    public static final int TRANSACTION_mountAppFuse = 35;
    public static final int TRANSACTION_mountFstab = 41;
    public static final int TRANSACTION_mountIncFs = 74;
    public static final int TRANSACTION_mountSdpMediaStorageCmd = 98;
    public static final int TRANSACTION_moveStorage = 19;
    public static final int TRANSACTION_mvFileAtData = 80;
    public static final int TRANSACTION_needsCheckpoint = 56;
    public static final int TRANSACTION_needsRollback = 57;
    public static final int TRANSACTION_onSecureKeyguardStateChanged = 12;
    public static final int TRANSACTION_onUserAdded = 6;
    public static final int TRANSACTION_onUserRemoved = 7;
    public static final int TRANSACTION_onUserStarted = 8;
    public static final int TRANSACTION_onUserStopped = 9;
    public static final int TRANSACTION_openAppFuseFile = 72;
    public static final int TRANSACTION_partition = 13;
    public static final int TRANSACTION_prepareCheckpoint = 61;
    public static final int TRANSACTION_prepareSandboxForApp = 53;
    public static final int TRANSACTION_prepareUserStorage = 51;
    public static final int TRANSACTION_refreshLatestWrite = 33;
    public static final int TRANSACTION_remountAppStorageDirs = 21;
    public static final int TRANSACTION_remountUid = 20;
    public static final int TRANSACTION_reserveDataBlocks = 103;
    public static final int TRANSACTION_reset = 4;
    public static final int TRANSACTION_resetCheckpoint = 68;
    public static final int TRANSACTION_restoreCheckpoint = 62;
    public static final int TRANSACTION_restoreCheckpointPart = 63;
    public static final int TRANSACTION_runIdleMaint = 29;
    public static final int TRANSACTION_sdeEnable = 37;
    public static final int TRANSACTION_sdeMoveMountHidden = 38;
    public static final int TRANSACTION_setDebugForExternal = 50;
    public static final int TRANSACTION_setDualDARPolicyCmd = 101;
    public static final int TRANSACTION_setGCUrgentPace = 32;
    public static final int TRANSACTION_setIncFsMountOptions = 76;
    public static final int TRANSACTION_setListener = 1;
    public static final int TRANSACTION_setMpUidForFileSystem = 82;
    public static final int TRANSACTION_setSdpPolicyCmd = 99;
    public static final int TRANSACTION_setSdpPolicyToPathCmd = 100;
    public static final int TRANSACTION_setSensitive = 96;
    public static final int TRANSACTION_setStorageBindingSeed = 43;
    public static final int TRANSACTION_setUserKeyProtection = 46;
    public static final int TRANSACTION_setupAppDir = 23;
    public static final int TRANSACTION_shrinkDataDdp = 102;
    public static final int TRANSACTION_shutdown = 5;
    public static final int TRANSACTION_startCheckpoint = 55;
    public static final int TRANSACTION_supportsBlockCheckpoint = 66;
    public static final int TRANSACTION_supportsCheckpoint = 65;
    public static final int TRANSACTION_supportsFileCheckpoint = 67;
    public static final int TRANSACTION_unlockUserKey = 48;
    public static final int TRANSACTION_unmount = 16;
    public static final int TRANSACTION_unmountAppFuse = 36;
    public static final int TRANSACTION_unmountAppStorageDirs = 22;
    public static final int TRANSACTION_unmountIncFs = 75;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
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
          IVoldMountCallback asInterface2 =
              IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
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
          IVoldTaskListener asInterface3 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          benchmark(readString8, asInterface3);
          parcel2.writeNoException();
          return true;
        case 19:
          String readString9 = parcel.readString();
          String readString10 = parcel.readString();
          IVoldTaskListener asInterface4 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
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
          IVoldTaskListener asInterface5 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          fstrim(readInt21, asInterface5);
          parcel2.writeNoException();
          return true;
        case 29:
          boolean readBoolean2 = parcel.readBoolean();
          IVoldTaskListener asInterface6 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          runIdleMaint(readBoolean2, asInterface6);
          parcel2.writeNoException();
          return true;
        case 30:
          IVoldTaskListener asInterface7 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
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
          setGCUrgentPace(
              readInt22, readInt23, readFloat, readFloat2, readInt24, readInt25, readInt26);
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
          IVoldTaskListener asInterface8 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
          IVoldMountCallback asInterface9 =
              IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          sdeEnable(readString15, readInt31, readInt32, readBoolean3, asInterface8, asInterface9);
          parcel2.writeNoException();
          return true;
        case 38:
          String readString16 = parcel.readString();
          int readInt33 = parcel.readInt();
          int readInt34 = parcel.readInt();
          IVoldMountCallback asInterface10 =
              IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          sdeMoveMountHidden(readString16, readInt33, readInt34, asInterface10);
          parcel2.writeNoException();
          return true;
        case 39:
          fbeEnable();
          parcel2.writeNoException();
          return true;
        case 40:
          initUser0();
          parcel2.writeNoException();
          return true;
        case 41:
          String readString17 = parcel.readString();
          String readString18 = parcel.readString();
          String readString19 = parcel.readString();
          parcel.enforceNoDataAvail();
          mountFstab(readString17, readString18, readString19);
          parcel2.writeNoException();
          return true;
        case 42:
          String readString20 = parcel.readString();
          String readString21 = parcel.readString();
          boolean readBoolean4 = parcel.readBoolean();
          String readString22 = parcel.readString();
          String readString23 = parcel.readString();
          parcel.enforceNoDataAvail();
          encryptFstab(readString20, readString21, readBoolean4, readString22, readString23);
          parcel2.writeNoException();
          return true;
        case 43:
          byte[] createByteArray = parcel.createByteArray();
          parcel.enforceNoDataAvail();
          setStorageBindingSeed(createByteArray);
          parcel2.writeNoException();
          return true;
        case 44:
          int readInt35 = parcel.readInt();
          int readInt36 = parcel.readInt();
          boolean readBoolean5 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          createUserKey(readInt35, readInt36, readBoolean5);
          parcel2.writeNoException();
          return true;
        case 45:
          int readInt37 = parcel.readInt();
          parcel.enforceNoDataAvail();
          destroyUserKey(readInt37);
          parcel2.writeNoException();
          return true;
        case 46:
          int readInt38 = parcel.readInt();
          String readString24 = parcel.readString();
          parcel.enforceNoDataAvail();
          setUserKeyProtection(readInt38, readString24);
          parcel2.writeNoException();
          return true;
        case 47:
          int[] unlockedUsers = getUnlockedUsers();
          parcel2.writeNoException();
          parcel2.writeIntArray(unlockedUsers);
          return true;
        case 48:
          int readInt39 = parcel.readInt();
          int readInt40 = parcel.readInt();
          String readString25 = parcel.readString();
          String readString26 = parcel.readString();
          parcel.enforceNoDataAvail();
          unlockUserKey(readInt39, readInt40, readString25, readString26);
          parcel2.writeNoException();
          return true;
        case 49:
          int readInt41 = parcel.readInt();
          parcel.enforceNoDataAvail();
          lockUserKey(readInt41);
          parcel2.writeNoException();
          return true;
        case 50:
          String readString27 = parcel.readString();
          parcel.enforceNoDataAvail();
          setDebugForExternal(readString27);
          parcel2.writeNoException();
          return true;
        case 51:
          String readString28 = parcel.readString();
          int readInt42 = parcel.readInt();
          int readInt43 = parcel.readInt();
          int readInt44 = parcel.readInt();
          parcel.enforceNoDataAvail();
          prepareUserStorage(readString28, readInt42, readInt43, readInt44);
          parcel2.writeNoException();
          return true;
        case 52:
          String readString29 = parcel.readString();
          int readInt45 = parcel.readInt();
          int readInt46 = parcel.readInt();
          parcel.enforceNoDataAvail();
          destroyUserStorage(readString29, readInt45, readInt46);
          parcel2.writeNoException();
          return true;
        case 53:
          String readString30 = parcel.readString();
          int readInt47 = parcel.readInt();
          String readString31 = parcel.readString();
          int readInt48 = parcel.readInt();
          parcel.enforceNoDataAvail();
          prepareSandboxForApp(readString30, readInt47, readString31, readInt48);
          parcel2.writeNoException();
          return true;
        case 54:
          String readString32 = parcel.readString();
          String readString33 = parcel.readString();
          int readInt49 = parcel.readInt();
          parcel.enforceNoDataAvail();
          destroySandboxForApp(readString32, readString33, readInt49);
          parcel2.writeNoException();
          return true;
        case 55:
          int readInt50 = parcel.readInt();
          parcel.enforceNoDataAvail();
          startCheckpoint(readInt50);
          parcel2.writeNoException();
          return true;
        case 56:
          boolean needsCheckpoint = needsCheckpoint();
          parcel2.writeNoException();
          parcel2.writeBoolean(needsCheckpoint);
          return true;
        case 57:
          boolean needsRollback = needsRollback();
          parcel2.writeNoException();
          parcel2.writeBoolean(needsRollback);
          return true;
        case 58:
          boolean isCheckpointing = isCheckpointing();
          parcel2.writeNoException();
          parcel2.writeBoolean(isCheckpointing);
          return true;
        case 59:
          String readString34 = parcel.readString();
          boolean readBoolean6 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          abortChanges(readString34, readBoolean6);
          parcel2.writeNoException();
          return true;
        case 60:
          commitChanges();
          parcel2.writeNoException();
          return true;
        case 61:
          prepareCheckpoint();
          parcel2.writeNoException();
          return true;
        case 62:
          String readString35 = parcel.readString();
          parcel.enforceNoDataAvail();
          restoreCheckpoint(readString35);
          parcel2.writeNoException();
          return true;
        case 63:
          String readString36 = parcel.readString();
          int readInt51 = parcel.readInt();
          parcel.enforceNoDataAvail();
          restoreCheckpointPart(readString36, readInt51);
          parcel2.writeNoException();
          return true;
        case 64:
          markBootAttempt();
          parcel2.writeNoException();
          return true;
        case 65:
          boolean supportsCheckpoint = supportsCheckpoint();
          parcel2.writeNoException();
          parcel2.writeBoolean(supportsCheckpoint);
          return true;
        case 66:
          boolean supportsBlockCheckpoint = supportsBlockCheckpoint();
          parcel2.writeNoException();
          parcel2.writeBoolean(supportsBlockCheckpoint);
          return true;
        case 67:
          boolean supportsFileCheckpoint = supportsFileCheckpoint();
          parcel2.writeNoException();
          parcel2.writeBoolean(supportsFileCheckpoint);
          return true;
        case 68:
          resetCheckpoint();
          parcel2.writeNoException();
          return true;
        case 69:
          earlyBootEnded();
          parcel2.writeNoException();
          return true;
        case 70:
          String readString37 = parcel.readString();
          String readString38 = parcel.readString();
          String readString39 = parcel.readString();
          String readString40 = parcel.readString();
          String readString41 = parcel.readString();
          int readInt52 = parcel.readInt();
          parcel.enforceNoDataAvail();
          String createStubVolume =
              createStubVolume(
                  readString37, readString38, readString39, readString40, readString41, readInt52);
          parcel2.writeNoException();
          parcel2.writeString(createStubVolume);
          return true;
        case 71:
          String readString42 = parcel.readString();
          parcel.enforceNoDataAvail();
          destroyStubVolume(readString42);
          parcel2.writeNoException();
          return true;
        case 72:
          int readInt53 = parcel.readInt();
          int readInt54 = parcel.readInt();
          int readInt55 = parcel.readInt();
          int readInt56 = parcel.readInt();
          parcel.enforceNoDataAvail();
          FileDescriptor openAppFuseFile =
              openAppFuseFile(readInt53, readInt54, readInt55, readInt56);
          parcel2.writeNoException();
          parcel2.writeRawFileDescriptor(openAppFuseFile);
          return true;
        case 73:
          boolean incFsEnabled = incFsEnabled();
          parcel2.writeNoException();
          parcel2.writeBoolean(incFsEnabled);
          return true;
        case 74:
          String readString43 = parcel.readString();
          String readString44 = parcel.readString();
          int readInt57 = parcel.readInt();
          String readString45 = parcel.readString();
          parcel.enforceNoDataAvail();
          IncrementalFileSystemControlParcel mountIncFs =
              mountIncFs(readString43, readString44, readInt57, readString45);
          parcel2.writeNoException();
          parcel2.writeTypedObject(mountIncFs, 1);
          return true;
        case 75:
          String readString46 = parcel.readString();
          parcel.enforceNoDataAvail();
          unmountIncFs(readString46);
          parcel2.writeNoException();
          return true;
        case 76:
          IncrementalFileSystemControlParcel incrementalFileSystemControlParcel =
              (IncrementalFileSystemControlParcel)
                  parcel.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
          boolean readBoolean7 = parcel.readBoolean();
          boolean readBoolean8 = parcel.readBoolean();
          String readString47 = parcel.readString();
          parcel.enforceNoDataAvail();
          setIncFsMountOptions(
              incrementalFileSystemControlParcel, readBoolean7, readBoolean8, readString47);
          parcel2.writeNoException();
          return true;
        case 77:
          String readString48 = parcel.readString();
          String readString49 = parcel.readString();
          parcel.enforceNoDataAvail();
          bindMount(readString48, readString49);
          parcel2.writeNoException();
          return true;
        case 78:
          String readString50 = parcel.readString();
          parcel.enforceNoDataAvail();
          destroyDsuMetadataKey(readString50);
          parcel2.writeNoException();
          return true;
        case 79:
          long usedF2fsFileNode = getUsedF2fsFileNode();
          parcel2.writeNoException();
          parcel2.writeLong(usedF2fsFileNode);
          return true;
        case 80:
          String readString51 = parcel.readString();
          String readString52 = parcel.readString();
          int readInt58 = parcel.readInt();
          int readInt59 = parcel.readInt();
          IVoldTaskListener asInterface11 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          mvFileAtData(readString51, readString52, readInt58, readInt59, asInterface11);
          parcel2.writeNoException();
          return true;
        case 81:
          String readString53 = parcel.readString();
          String readString54 = parcel.readString();
          int readInt60 = parcel.readInt();
          int readInt61 = parcel.readInt();
          IVoldTaskListener asInterface12 =
              IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
          parcel.enforceNoDataAvail();
          cpFileAtData(readString53, readString54, readInt60, readInt61, asInterface12);
          parcel2.writeNoException();
          return true;
        case 82:
          int readInt62 = parcel.readInt();
          parcel.enforceNoDataAvail();
          setMpUidForFileSystem(readInt62);
          parcel2.writeNoException();
          return true;
        case 83:
          String readString55 = parcel.readString();
          int readInt63 = parcel.readInt();
          String readString56 = parcel.readString();
          String readString57 = parcel.readString();
          int readInt64 = parcel.readInt();
          boolean readBoolean9 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          asecCreate(readString55, readInt63, readString56, readString57, readInt64, readBoolean9);
          parcel2.writeNoException();
          return true;
        case 84:
          String readString58 = parcel.readString();
          int readInt65 = parcel.readInt();
          String readString59 = parcel.readString();
          parcel.enforceNoDataAvail();
          asecResize(readString58, readInt65, readString59);
          parcel2.writeNoException();
          return true;
        case 85:
          String readString60 = parcel.readString();
          parcel.enforceNoDataAvail();
          asecFinalize(readString60);
          parcel2.writeNoException();
          return true;
        case 86:
          String readString61 = parcel.readString();
          int readInt66 = parcel.readInt();
          String readString62 = parcel.readString();
          parcel.enforceNoDataAvail();
          asecFixperms(readString61, readInt66, readString62);
          parcel2.writeNoException();
          return true;
        case 87:
          String readString63 = parcel.readString();
          boolean readBoolean10 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          asecDestroy(readString63, readBoolean10);
          parcel2.writeNoException();
          return true;
        case 88:
          String readString64 = parcel.readString();
          String readString65 = parcel.readString();
          int readInt67 = parcel.readInt();
          boolean readBoolean11 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          asecMount(readString64, readString65, readInt67, readBoolean11);
          parcel2.writeNoException();
          return true;
        case 89:
          String readString66 = parcel.readString();
          boolean readBoolean12 = parcel.readBoolean();
          parcel.enforceNoDataAvail();
          asecUnmount(readString66, readBoolean12);
          parcel2.writeNoException();
          return true;
        case 90:
          String readString67 = parcel.readString();
          String readString68 = parcel.readString();
          parcel.enforceNoDataAvail();
          asecRename(readString67, readString68);
          parcel2.writeNoException();
          return true;
        case 91:
          String readString69 = parcel.readString();
          parcel.enforceNoDataAvail();
          String asecPath = asecPath(readString69);
          parcel2.writeNoException();
          parcel2.writeString(asecPath);
          return true;
        case 92:
          String readString70 = parcel.readString();
          parcel.enforceNoDataAvail();
          String asecFsPath = asecFsPath(readString70);
          parcel2.writeNoException();
          parcel2.writeString(asecFsPath);
          return true;
        case 93:
          String[] asecList = asecList();
          parcel2.writeNoException();
          parcel2.writeStringArray(asecList);
          return true;
        case 94:
          String readString71 = parcel.readString();
          int readInt68 = parcel.readInt();
          String readString72 = parcel.readString();
          parcel.enforceNoDataAvail();
          asecTrim(readString71, readInt68, readString72);
          parcel2.writeNoException();
          return true;
        case 95:
          String readString73 = parcel.readString();
          parcel.enforceNoDataAvail();
          int asecGetUsedSpace = asecGetUsedSpace(readString73);
          parcel2.writeNoException();
          parcel2.writeInt(asecGetUsedSpace);
          return true;
        case 96:
          int readInt69 = parcel.readInt();
          String readString74 = parcel.readString();
          parcel.enforceNoDataAvail();
          boolean sensitive = setSensitive(readInt69, readString74);
          parcel2.writeNoException();
          parcel2.writeBoolean(sensitive);
          return true;
        case 97:
          String readString75 = parcel.readString();
          parcel.enforceNoDataAvail();
          boolean isSensitive = isSensitive(readString75);
          parcel2.writeNoException();
          parcel2.writeBoolean(isSensitive);
          return true;
        case 98:
          int readInt70 = parcel.readInt();
          parcel.enforceNoDataAvail();
          boolean mountSdpMediaStorageCmd = mountSdpMediaStorageCmd(readInt70);
          parcel2.writeNoException();
          parcel2.writeBoolean(mountSdpMediaStorageCmd);
          return true;
        case 99:
          int readInt71 = parcel.readInt();
          parcel.enforceNoDataAvail();
          boolean sdpPolicyCmd = setSdpPolicyCmd(readInt71);
          parcel2.writeNoException();
          parcel2.writeBoolean(sdpPolicyCmd);
          return true;
        case 100:
          int readInt72 = parcel.readInt();
          String readString76 = parcel.readString();
          parcel.enforceNoDataAvail();
          boolean sdpPolicyToPathCmd = setSdpPolicyToPathCmd(readInt72, readString76);
          parcel2.writeNoException();
          parcel2.writeBoolean(sdpPolicyToPathCmd);
          return true;
        case 101:
          int readInt73 = parcel.readInt();
          int readInt74 = parcel.readInt();
          parcel.enforceNoDataAvail();
          boolean dualDARPolicyCmd = setDualDARPolicyCmd(readInt73, readInt74);
          parcel2.writeNoException();
          parcel2.writeBoolean(dualDARPolicyCmd);
          return true;
        case 102:
          long readLong = parcel.readLong();
          parcel.enforceNoDataAvail();
          boolean shrinkDataDdp = shrinkDataDdp(readLong);
          parcel2.writeNoException();
          parcel2.writeBoolean(shrinkDataDdp);
          return true;
        case 103:
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

    public class Proxy implements IVold {
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

      @Override // android.p005os.IVold
      public void setListener(IVoldListener iVoldListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeStrongInterface(iVoldListener);
          this.mRemote.transact(1, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void abortFuse() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void monitor() {
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

      @Override // android.p005os.IVold
      public void reset() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(4, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void shutdown() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(5, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void onUserAdded(int i, int i2, int i3) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeInt(i3);
          this.mRemote.transact(6, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void onUserRemoved(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(7, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void onUserStarted(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(8, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void onUserStopped(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(9, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void addAppIds(String[] strArr, int[] iArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeStringArray(strArr);
          obtain.writeIntArray(iArr);
          this.mRemote.transact(10, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void addSandboxIds(int[] iArr, String[] strArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeIntArray(iArr);
          obtain.writeStringArray(strArr);
          this.mRemote.transact(11, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void onSecureKeyguardStateChanged(boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeBoolean(z);
          this.mRemote.transact(12, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void partition(String str, int i, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          this.mRemote.transact(13, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void forgetPartition(String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(14, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void mount(String str, int i, int i2, IVoldMountCallback iVoldMountCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeStrongInterface(iVoldMountCallback);
          this.mRemote.transact(15, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void unmount(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(16, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void format(String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(17, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void benchmark(String str, IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(18, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void moveStorage(String str, String str2, IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(19, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void remountUid(int i, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          this.mRemote.transact(20, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void remountAppStorageDirs(int i, int i2, String[] strArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeStringArray(strArr);
          this.mRemote.transact(21, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void unmountAppStorageDirs(int i, int i2, String[] strArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeStringArray(strArr);
          this.mRemote.transact(22, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setupAppDir(String str, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          this.mRemote.transact(23, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void fixupAppDir(String str, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          this.mRemote.transact(24, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void ensureAppDirsCreated(String[] strArr, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeStringArray(strArr);
          obtain.writeInt(i);
          this.mRemote.transact(25, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public String createObb(String str, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          this.mRemote.transact(26, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void destroyObb(String str) {
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

      @Override // android.p005os.IVold
      public void fstrim(int i, IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(28, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void runIdleMaint(boolean z, IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeBoolean(z);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(29, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void abortIdleMaint(IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(30, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public int getStorageLifeTime() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(31, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) {
        Parcel obtain = Parcel.obtain(asBinder());
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
          this.mRemote.transact(32, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void refreshLatestWrite() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(33, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public int getWriteAmount() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(34, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public FileDescriptor mountAppFuse(int i, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          this.mRemote.transact(35, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readRawFileDescriptor();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void unmountAppFuse(int i, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          this.mRemote.transact(36, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void sdeEnable(
          String str,
          int i,
          int i2,
          boolean z,
          IVoldTaskListener iVoldTaskListener,
          IVoldMountCallback iVoldMountCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeBoolean(z);
          obtain.writeStrongInterface(iVoldTaskListener);
          obtain.writeStrongInterface(iVoldMountCallback);
          this.mRemote.transact(37, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void sdeMoveMountHidden(
          String str, int i, int i2, IVoldMountCallback iVoldMountCallback) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeStrongInterface(iVoldMountCallback);
          this.mRemote.transact(38, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void fbeEnable() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(39, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void initUser0() {
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

      @Override // android.p005os.IVold
      public void mountFstab(String str, String str2, String str3) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeString(str3);
          this.mRemote.transact(41, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void encryptFstab(String str, String str2, boolean z, String str3, String str4) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeBoolean(z);
          obtain.writeString(str3);
          obtain.writeString(str4);
          this.mRemote.transact(42, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setStorageBindingSeed(byte[] bArr) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeByteArray(bArr);
          this.mRemote.transact(43, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void createUserKey(int i, int i2, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeBoolean(z);
          this.mRemote.transact(44, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void destroyUserKey(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(45, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setUserKeyProtection(int i, String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeString(str);
          this.mRemote.transact(46, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public int[] getUnlockedUsers() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(47, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.createIntArray();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void unlockUserKey(int i, int i2, String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(48, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void lockUserKey(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(49, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setDebugForExternal(String str) {
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

      @Override // android.p005os.IVold
      public void prepareUserStorage(String str, int i, int i2, int i3) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeInt(i3);
          this.mRemote.transact(51, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void destroyUserStorage(String str, int i, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          this.mRemote.transact(52, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void prepareSandboxForApp(String str, int i, String str2, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeString(str2);
          obtain.writeInt(i2);
          this.mRemote.transact(53, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void destroySandboxForApp(String str, String str2, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          this.mRemote.transact(54, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void startCheckpoint(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(55, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean needsCheckpoint() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(56, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean needsRollback() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(57, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean isCheckpointing() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(58, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void abortChanges(String str, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeBoolean(z);
          this.mRemote.transact(59, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void commitChanges() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(60, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void prepareCheckpoint() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(61, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void restoreCheckpoint(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(62, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void restoreCheckpointPart(String str, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          this.mRemote.transact(63, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void markBootAttempt() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(64, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean supportsCheckpoint() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(65, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean supportsBlockCheckpoint() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(66, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean supportsFileCheckpoint() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(67, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void resetCheckpoint() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(68, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void earlyBootEnded() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(69, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public String createStubVolume(
          String str, String str2, String str3, String str4, String str5, int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeString(str3);
          obtain.writeString(str4);
          obtain.writeString(str5);
          obtain.writeInt(i);
          this.mRemote.transact(70, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void destroyStubVolume(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(71, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeInt(i3);
          obtain.writeInt(i4);
          this.mRemote.transact(72, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readRawFileDescriptor();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean incFsEnabled() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(73, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public IncrementalFileSystemControlParcel mountIncFs(
          String str, String str2, int i, String str3) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          obtain.writeString(str3);
          this.mRemote.transact(74, obtain, obtain2, 0);
          obtain2.readException();
          return (IncrementalFileSystemControlParcel)
              obtain2.readTypedObject(IncrementalFileSystemControlParcel.CREATOR);
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void unmountIncFs(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(75, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setIncFsMountOptions(
          IncrementalFileSystemControlParcel incrementalFileSystemControlParcel,
          boolean z,
          boolean z2,
          String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeTypedObject(incrementalFileSystemControlParcel, 0);
          obtain.writeBoolean(z);
          obtain.writeBoolean(z2);
          obtain.writeString(str);
          this.mRemote.transact(76, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void bindMount(String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(77, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void destroyDsuMetadataKey(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(78, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public long getUsedF2fsFileNode() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(79, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readLong();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void mvFileAtData(
          String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(80, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void cpFileAtData(
          String str, String str2, int i, int i2, IVoldTaskListener iVoldTaskListener) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          obtain.writeStrongInterface(iVoldTaskListener);
          this.mRemote.transact(81, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void setMpUidForFileSystem(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(82, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecCreate(String str, int i, String str2, String str3, int i2, boolean z) {
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
          this.mRemote.transact(83, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecResize(String str, int i, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeString(str2);
          this.mRemote.transact(84, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecFinalize(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(85, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecFixperms(String str, int i, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeString(str2);
          this.mRemote.transact(86, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecDestroy(String str, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeBoolean(z);
          this.mRemote.transact(87, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecMount(String str, String str2, int i, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          obtain.writeBoolean(z);
          this.mRemote.transact(88, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecUnmount(String str, boolean z) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeBoolean(z);
          this.mRemote.transact(89, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecRename(String str, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          this.mRemote.transact(90, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public String asecPath(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(91, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public String asecFsPath(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(92, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readString();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public String[] asecList() {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(93, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.createStringArray();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public void asecTrim(String str, int i, String str2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeInt(i);
          obtain.writeString(str2);
          this.mRemote.transact(94, obtain, obtain2, 0);
          obtain2.readException();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public int asecGetUsedSpace(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(95, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readInt();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean setSensitive(int i, String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeString(str);
          this.mRemote.transact(96, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean isSensitive(String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeString(str);
          this.mRemote.transact(97, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean mountSdpMediaStorageCmd(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(98, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean setSdpPolicyCmd(int i) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          this.mRemote.transact(99, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean setSdpPolicyToPathCmd(int i, String str) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeString(str);
          this.mRemote.transact(100, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean setDualDARPolicyCmd(int i, int i2) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          this.mRemote.transact(101, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public boolean shrinkDataDdp(long j) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeLong(j);
          this.mRemote.transact(102, obtain, obtain2, 0);
          obtain2.readException();
          return obtain2.readBoolean();
        } finally {
          obtain2.recycle();
          obtain.recycle();
        }
      }

      @Override // android.p005os.IVold
      public int reserveDataBlocks(long j) {
        Parcel obtain = Parcel.obtain(asBinder());
        Parcel obtain2 = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(Stub.DESCRIPTOR);
          obtain.writeLong(j);
          this.mRemote.transact(103, obtain, obtain2, 0);
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
