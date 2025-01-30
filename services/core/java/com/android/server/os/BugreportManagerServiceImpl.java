package com.android.server.os;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.p005os.IDumpstate;
import android.p005os.IDumpstateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.server.SystemConfig;
import com.android.server.utils.Slogf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.OptionalInt;

/* loaded from: classes2.dex */
public class BugreportManagerServiceImpl extends IDumpstate.Stub {
  public final AppOpsManager mAppOps;
  public final ArraySet mBugreportAllowlistedPackages;
  public final BugreportFileManager mBugreportFileManager;
  public final Context mContext;
  public DumpstateListener mCurrentDumpstateListener;
  public final LocalLog mFinishedBugreports;
  public final Object mLock;
  public int mNumberFinishedBugreports;
  public OptionalInt mPreDumpedDataUid;
  public final TelephonyManager mTelephonyManager;

  public final int clearBugreportFlag(int i, int i2) {
    return (~i2) & i;
  }

  class BugreportFileManager {
    public final Object mLock = new Object();
    public final ArrayMap mBugreportFiles = new ArrayMap();

    public void ensureCallerPreviouslyGeneratedFile(Pair pair, String str) {
      synchronized (this.mLock) {
        ArraySet arraySet = (ArraySet) this.mBugreportFiles.get(pair);
        if (arraySet != null && arraySet.contains(str)) {
          arraySet.remove(str);
          if (arraySet.isEmpty()) {
            this.mBugreportFiles.remove(pair);
          }
        } else {
          throw new IllegalArgumentException(
              "File "
                  + str
                  + " was not generated on behalf of calling package "
                  + ((String) pair.second));
        }
      }
    }

    public void addBugreportFileForCaller(Pair pair, String str) {
      synchronized (this.mLock) {
        if (!this.mBugreportFiles.containsKey(pair)) {
          this.mBugreportFiles.put(pair, new ArraySet());
        }
        ((ArraySet) this.mBugreportFiles.get(pair)).add(str);
      }
    }
  }

  public class Injector {
    public ArraySet mAllowlistedPackages;
    public Context mContext;

    public Injector(Context context, ArraySet arraySet) {
      this.mContext = context;
      this.mAllowlistedPackages = arraySet;
    }

    public Context getContext() {
      return this.mContext;
    }

    public ArraySet getAllowlistedPackages() {
      return this.mAllowlistedPackages;
    }
  }

  public BugreportManagerServiceImpl(Context context) {
    this(new Injector(context, SystemConfig.getInstance().getBugreportWhitelistedPackages()));
  }

  public BugreportManagerServiceImpl(Injector injector) {
    this.mLock = new Object();
    this.mPreDumpedDataUid = OptionalInt.empty();
    this.mFinishedBugreports = new LocalLog(20);
    Context context = injector.getContext();
    this.mContext = context;
    this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
    this.mTelephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
    this.mBugreportFileManager = new BugreportFileManager();
    this.mBugreportAllowlistedPackages = injector.getAllowlistedPackages();
  }

  @Override // android.p005os.IDumpstate
  public void preDumpUiData(String str) {
    enforcePermission(str, Binder.getCallingUid(), true);
    synchronized (this.mLock) {
      preDumpUiDataLocked(str);
    }
  }

  @Override // android.p005os.IDumpstate
  public void startBugreport(
      int i,
      String str,
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      int i2,
      int i3,
      IDumpstateListener iDumpstateListener,
      boolean z) {
    Objects.requireNonNull(str);
    Objects.requireNonNull(fileDescriptor);
    Objects.requireNonNull(iDumpstateListener);
    validateBugreportMode(i2);
    validateBugreportFlags(i3);
    int callingUid = Binder.getCallingUid();
    enforcePermission(str, callingUid, i2 == 4);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      ensureUserCanTakeBugReport(i2);
      Binder.restoreCallingIdentity(clearCallingIdentity);
      Slogf.m96i(
          "BugreportManagerService",
          "Starting bugreport for %s / %d",
          str,
          Integer.valueOf(callingUid));
      synchronized (this.mLock) {
        startBugreportLocked(
            callingUid, str, fileDescriptor, fileDescriptor2, i2, i3, iDumpstateListener, z);
      }
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  @Override // android.p005os.IDumpstate
  public void cancelBugreport(int i, String str) {
    int callingUid = Binder.getCallingUid();
    enforcePermission(str, callingUid, true);
    Slogf.m96i(
        "BugreportManagerService",
        "Cancelling bugreport for %s / %d",
        str,
        Integer.valueOf(callingUid));
    synchronized (this.mLock) {
      IDumpstate dumpstateBinderServiceLocked = getDumpstateBinderServiceLocked();
      if (dumpstateBinderServiceLocked == null) {
        Slog.w(
            "BugreportManagerService", "cancelBugreport: Could not find native dumpstate service");
        return;
      }
      try {
        dumpstateBinderServiceLocked.cancelBugreport(callingUid, str);
      } catch (RemoteException e) {
        Slog.e("BugreportManagerService", "RemoteException in cancelBugreport", e);
      }
      stopDumpstateBinderServiceLocked();
    }
  }

  @Override // android.p005os.IDumpstate
  public void retrieveBugreport(
      int i,
      String str,
      FileDescriptor fileDescriptor,
      String str2,
      IDumpstateListener iDumpstateListener) {
    int callingUid = Binder.getCallingUid();
    enforcePermission(str, callingUid, false);
    Slogf.m96i(
        "BugreportManagerService",
        "Retrieving bugreport for %s / %d",
        str,
        Integer.valueOf(callingUid));
    try {
      this.mBugreportFileManager.ensureCallerPreviouslyGeneratedFile(
          new Pair(Integer.valueOf(callingUid), str), str2);
      synchronized (this.mLock) {
        if (isDumpstateBinderServiceRunningLocked()) {
          Slog.w(
              "BugreportManagerService",
              "'dumpstate' is already running. Cannot retrieve a bugreport while another one is"
                  + " currently in progress.");
          reportError(iDumpstateListener, 5);
          return;
        }
        IDumpstate startAndGetDumpstateBinderServiceLocked =
            startAndGetDumpstateBinderServiceLocked();
        if (startAndGetDumpstateBinderServiceLocked == null) {
          Slog.w("BugreportManagerService", "Unable to get bugreport service");
          reportError(iDumpstateListener, 2);
          return;
        }
        DumpstateListener dumpstateListener =
            new DumpstateListener(
                iDumpstateListener,
                startAndGetDumpstateBinderServiceLocked,
                new Pair(Integer.valueOf(callingUid), str),
                true);
        setCurrentDumpstateListenerLocked(dumpstateListener);
        try {
          startAndGetDumpstateBinderServiceLocked.retrieveBugreport(
              callingUid, str, fileDescriptor, str2, dumpstateListener);
        } catch (RemoteException e) {
          Slog.e("BugreportManagerService", "RemoteException in retrieveBugreport", e);
        }
      }
    } catch (IllegalArgumentException e2) {
      Slog.e("BugreportManagerService", e2.getMessage());
      reportError(iDumpstateListener, 6);
    }
  }

  public final void setCurrentDumpstateListenerLocked(DumpstateListener dumpstateListener) {
    DumpstateListener dumpstateListener2 = this.mCurrentDumpstateListener;
    if (dumpstateListener2 != null) {
      Slogf.m105w(
          "BugreportManagerService",
          "setCurrentDumpstateListenerLocked(%s): called when mCurrentDumpstateListener is already"
              + " set (%s)",
          dumpstateListener,
          dumpstateListener2);
    }
    this.mCurrentDumpstateListener = dumpstateListener;
  }

  public final void validateBugreportMode(int i) {
    if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 20) {
      return;
    }
    Slog.w("BugreportManagerService", "Unknown bugreport mode: " + i);
    throw new IllegalArgumentException("Unknown bugreport mode: " + i);
  }

  public final void validateBugreportFlags(int i) {
    int clearBugreportFlag = clearBugreportFlag(i, 3);
    if (clearBugreportFlag == 0) {
      return;
    }
    Slog.w("BugreportManagerService", "Unknown bugreport flags: " + clearBugreportFlag);
    throw new IllegalArgumentException("Unknown bugreport flags: " + clearBugreportFlag);
  }

  public final void enforcePermission(String str, int i, boolean z) {
    this.mAppOps.checkPackage(i, str);
    if (this.mBugreportAllowlistedPackages.contains(str)
        && this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) {
      return;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    if (z) {
      try {
        if (this.mTelephonyManager.checkCarrierPrivilegesForPackageAnyPhone(str) == 1) {
          return;
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    Binder.restoreCallingIdentity(clearCallingIdentity);
    if (i == 1000 && SystemProperties.getBoolean("debug.dumpstate.ut", false)) {
      Slog.w(
          "BugreportManagerService",
          "temporary allow to get bugreport in systemserver only during UT");
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append(" does not hold the DUMP permission or is not bugreport-whitelisted ");
    sb.append(z ? "and does not have carrier privileges " : "");
    sb.append("to request a bugreport");
    String sb2 = sb.toString();
    Slog.w("BugreportManagerService", sb2);
    throw new SecurityException(sb2);
  }

  public final void ensureUserCanTakeBugReport(int i) {
    UserInfo userInfo;
    try {
      userInfo = ActivityManager.getService().getCurrentUser();
    } catch (RemoteException unused) {
      userInfo = null;
    }
    if (userInfo == null) {
      logAndThrow("There is no current user, so no bugreport can be requested.");
    }
    if (userInfo.isAdmin()) {
      return;
    }
    if (i == 2 && isCurrentUserAffiliated(userInfo.id)) {
      return;
    }
    logAndThrow(
        TextUtils.formatSimple(
            "Current user %s is not an admin user. Only admin users are allowed to take bugreport.",
            new Object[] {Integer.valueOf(userInfo.id)}));
  }

  public final boolean isCurrentUserAffiliated(int i) {
    DevicePolicyManager devicePolicyManager =
        (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
    int deviceOwnerUserId = devicePolicyManager.getDeviceOwnerUserId();
    if (deviceOwnerUserId == -10000) {
      return false;
    }
    int userId = UserHandle.getUserId(Binder.getCallingUid());
    Slog.i(
        "BugreportManagerService",
        "callingUid: " + userId + " deviceOwnerUid: " + deviceOwnerUserId + " currentUserId: " + i);
    if (userId != deviceOwnerUserId) {
      logAndThrow("Caller is not device owner on provisioned device.");
    }
    if (devicePolicyManager.isAffiliatedUser(i)) {
      return true;
    }
    logAndThrow("Current user is not affiliated to the device owner.");
    return true;
  }

  public final void preDumpUiDataLocked(String str) {
    this.mPreDumpedDataUid = OptionalInt.empty();
    if (isDumpstateBinderServiceRunningLocked()) {
      Slog.e(
          "BugreportManagerService",
          "'dumpstate' is already running. Cannot pre-dump data while another operation is"
              + " currently in progress.");
      return;
    }
    IDumpstate startAndGetDumpstateBinderServiceLocked = startAndGetDumpstateBinderServiceLocked();
    if (startAndGetDumpstateBinderServiceLocked == null) {
      Slog.e("BugreportManagerService", "Unable to get bugreport service");
      return;
    }
    try {
      startAndGetDumpstateBinderServiceLocked.preDumpUiData(str);
      stopDumpstateBinderServiceLocked();
      this.mPreDumpedDataUid = OptionalInt.of(Binder.getCallingUid());
    } catch (RemoteException unused) {
      stopDumpstateBinderServiceLocked();
    } catch (Throwable th) {
      stopDumpstateBinderServiceLocked();
      throw th;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
  /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
  /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void startBugreportLocked(
      int i,
      String str,
      FileDescriptor fileDescriptor,
      FileDescriptor fileDescriptor2,
      int i2,
      int i3,
      IDumpstateListener iDumpstateListener,
      boolean z) {
    IDumpstate startAndGetDumpstateBinderServiceLocked;
    int i4 = i3;
    if (isDumpstateBinderServiceRunningLocked()) {
      Slog.w(
          "BugreportManagerService",
          "'dumpstate' is already running. Cannot start a new bugreport while another operation is"
              + " currently in progress.");
      reportError(iDumpstateListener, 5);
      return;
    }
    if ((i4 & 1) != 0) {
      if (this.mPreDumpedDataUid.isEmpty()) {
        i4 = clearBugreportFlag(i4, 1);
        Slog.w(
            "BugreportManagerService",
            "Ignoring BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA. No pre-dumped data is available.");
      } else {
        if (this.mPreDumpedDataUid.getAsInt() != i) {
          i4 = clearBugreportFlag(i4, 1);
          Slog.w(
              "BugreportManagerService",
              "Ignoring BUGREPORT_FLAG_USE_PREDUMPED_UI_DATA. Data was pre-dumped by a different"
                  + " UID.");
        }
        int i5 = i4;
        boolean z2 = (i5 & 2) == 0;
        startAndGetDumpstateBinderServiceLocked = startAndGetDumpstateBinderServiceLocked();
        if (startAndGetDumpstateBinderServiceLocked != null) {
          Slog.w("BugreportManagerService", "Unable to get bugreport service");
          reportError(iDumpstateListener, 2);
          return;
        }
        DumpstateListener dumpstateListener =
            new DumpstateListener(
                iDumpstateListener,
                startAndGetDumpstateBinderServiceLocked,
                new Pair(Integer.valueOf(i), str),
                z2);
        setCurrentDumpstateListenerLocked(dumpstateListener);
        try {
          startAndGetDumpstateBinderServiceLocked.startBugreport(
              i, str, fileDescriptor, fileDescriptor2, i2, i5, dumpstateListener, z);
          return;
        } catch (RemoteException unused) {
          cancelBugreport(i, str);
          return;
        }
      }
    }
    int i52 = i4;
    if ((i52 & 2) == 0) {}
    startAndGetDumpstateBinderServiceLocked = startAndGetDumpstateBinderServiceLocked();
    if (startAndGetDumpstateBinderServiceLocked != null) {}
  }

  public final boolean isDumpstateBinderServiceRunningLocked() {
    return getDumpstateBinderServiceLocked() != null;
  }

  public final IDumpstate getDumpstateBinderServiceLocked() {
    return IDumpstate.Stub.asInterface(ServiceManager.getService("dumpstate"));
  }

  public final IDumpstate startAndGetDumpstateBinderServiceLocked() {
    SystemProperties.set("ctl.start", "bugreportd");
    IDumpstate iDumpstate = null;
    int i = 500;
    boolean z = false;
    int i2 = 0;
    while (true) {
      if (z) {
        break;
      }
      iDumpstate = getDumpstateBinderServiceLocked();
      if (iDumpstate != null) {
        Slog.i("BugreportManagerService", "Got bugreport service handle.");
        break;
      }
      SystemClock.sleep(i);
      Slog.i("BugreportManagerService", "Waiting to get dumpstate service handle (" + i2 + "ms)");
      i2 += i;
      i *= 2;
      z = ((long) i2) > 30000;
    }
    if (z) {
      Slog.w(
          "BugreportManagerService",
          "Timed out waiting to get dumpstate service handle (" + i2 + "ms)");
    }
    return iDumpstate;
  }

  public final void stopDumpstateBinderServiceLocked() {
    SystemProperties.set("ctl.stop", "bugreportd");
  }

  @Override // android.os.Binder
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (DumpUtils.checkDumpPermission(this.mContext, "BugreportManagerService", printWriter)) {
      printWriter.printf("Allow-listed packages: %s\n", this.mBugreportAllowlistedPackages);
      synchronized (this.mLock) {
        printWriter.print("Pre-dumped data UID: ");
        if (this.mPreDumpedDataUid.isEmpty()) {
          printWriter.println("none");
        } else {
          printWriter.println(this.mPreDumpedDataUid.getAsInt());
        }
        DumpstateListener dumpstateListener = this.mCurrentDumpstateListener;
        if (dumpstateListener == null) {
          printWriter.println("Not taking a bug report");
        } else {
          dumpstateListener.dump(printWriter);
        }
        int i = this.mNumberFinishedBugreports;
        if (i == 0) {
          printWriter.println("No finished bugreports");
        } else {
          Object[] objArr = new Object[3];
          objArr[0] = Integer.valueOf(i);
          int i2 = this.mNumberFinishedBugreports;
          objArr[1] = i2 > 1 ? "s" : "";
          objArr[2] = Integer.valueOf(Math.min(i2, 20));
          printWriter.printf("%d finished bugreport%s. Last %d:\n", objArr);
          this.mFinishedBugreports.dump("  ", printWriter);
        }
      }
      synchronized (this.mBugreportFileManager.mLock) {
        int size = this.mBugreportFileManager.mBugreportFiles.size();
        Object[] objArr2 = new Object[2];
        objArr2[0] = Integer.valueOf(size);
        objArr2[1] = size > 1 ? "s" : "";
        printWriter.printf("%d pending file%s", objArr2);
        if (size > 0) {
          for (int i3 = 0; i3 < size; i3++) {
            printWriter.printf(
                "  %s: %s\n",
                callerToString((Pair) this.mBugreportFileManager.mBugreportFiles.keyAt(i3)),
                (ArraySet) this.mBugreportFileManager.mBugreportFiles.valueAt(i3));
          }
        } else {
          printWriter.println();
        }
      }
    }
  }

  public static String callerToString(Pair pair) {
    if (pair == null) {
      return "N/A";
    }
    return ((String) pair.second) + "/" + pair.first;
  }

  public final void reportError(IDumpstateListener iDumpstateListener, int i) {
    try {
      iDumpstateListener.onError(i);
    } catch (RemoteException e) {
      Slog.w(
          "BugreportManagerService",
          "onError() transaction threw RemoteException: " + e.getMessage());
    }
  }

  public final void logAndThrow(String str) {
    Slog.w("BugreportManagerService", str);
    throw new IllegalArgumentException(str);
  }

  public final class DumpstateListener extends IDumpstateListener.Stub
      implements IBinder.DeathRecipient {
    public static int sNextId;
    public final Pair mCaller;
    public boolean mDone;
    public final IDumpstate mDs;
    public final int mId;
    public final IDumpstateListener mListener;
    public int mProgress;
    public final boolean mReportFinishedFile;

    public DumpstateListener(
        IDumpstateListener iDumpstateListener, IDumpstate iDumpstate, Pair pair, boolean z) {
      int i = sNextId + 1;
      sNextId = i;
      this.mId = i;
      this.mListener = iDumpstateListener;
      this.mDs = iDumpstate;
      this.mCaller = pair;
      this.mReportFinishedFile = z;
      try {
        iDumpstate.asBinder().linkToDeath(this, 0);
      } catch (RemoteException e) {
        Slog.e("BugreportManagerService", "Unable to register Death Recipient for IDumpstate", e);
      }
    }

    @Override // android.p005os.IDumpstateListener
    public void onProgress(int i) {
      this.mProgress = i;
      this.mListener.onProgress(i);
    }

    @Override // android.p005os.IDumpstateListener
    public void onError(int i) {
      Slogf.m92e("BugreportManagerService", "onError(): %d", Integer.valueOf(i));
      synchronized (BugreportManagerServiceImpl.this.mLock) {
        releaseItselfLocked();
        reportFinishedLocked("ErroCode: " + i);
      }
      this.mListener.onError(i);
    }

    @Override // android.p005os.IDumpstateListener
    public void onFinished(String str) {
      Slogf.m96i("BugreportManagerService", "onFinished(): %s", str);
      synchronized (BugreportManagerServiceImpl.this.mLock) {
        releaseItselfLocked();
        reportFinishedLocked("File: " + str);
      }
      if (this.mReportFinishedFile) {
        BugreportManagerServiceImpl.this.mBugreportFileManager.addBugreportFileForCaller(
            this.mCaller, str);
      }
      this.mListener.onFinished(str);
    }

    @Override // android.p005os.IDumpstateListener
    public void onScreenshotTaken(boolean z) {
      this.mListener.onScreenshotTaken(z);
    }

    @Override // android.p005os.IDumpstateListener
    public void onUiIntensiveBugreportDumpsFinished() {
      this.mListener.onUiIntensiveBugreportDumpsFinished();
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException unused) {
      }
      synchronized (BugreportManagerServiceImpl.this.mLock) {
        if (!this.mDone) {
          Slog.e("BugreportManagerService", "IDumpstate likely crashed. Notifying listener");
          try {
            this.mListener.onError(2);
          } catch (RemoteException unused2) {
          }
        }
      }
      this.mDs.asBinder().unlinkToDeath(this, 0);
    }

    public String toString() {
      return "DumpstateListener[id=" + this.mId + ", progress=" + this.mProgress + "]";
    }

    public final void reportFinishedLocked(String str) {
      BugreportManagerServiceImpl.this.mNumberFinishedBugreports++;
      BugreportManagerServiceImpl.this.mFinishedBugreports.log(
          "Caller: " + BugreportManagerServiceImpl.callerToString(this.mCaller) + " " + str);
    }

    public final void dump(PrintWriter printWriter) {
      printWriter.println("DumpstateListener:");
      printWriter.printf("  id: %d\n", Integer.valueOf(this.mId));
      printWriter.printf(
          "  caller: %s\n", BugreportManagerServiceImpl.callerToString(this.mCaller));
      printWriter.printf(
          "  reports finished file: %b\n", Boolean.valueOf(this.mReportFinishedFile));
      printWriter.printf("  progress: %d\n", Integer.valueOf(this.mProgress));
      printWriter.printf("  done: %b\n", Boolean.valueOf(this.mDone));
    }

    public final void releaseItselfLocked() {
      this.mDone = true;
      if (BugreportManagerServiceImpl.this.mCurrentDumpstateListener == this) {
        BugreportManagerServiceImpl.this.mCurrentDumpstateListener = null;
        return;
      }
      Slogf.m102w(
          "BugreportManagerService",
          "releaseItselfLocked(): "
              + this
              + " is finished, but current listener is "
              + BugreportManagerServiceImpl.this.mCurrentDumpstateListener);
    }
  }
}
