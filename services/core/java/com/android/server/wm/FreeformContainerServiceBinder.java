package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Slog;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class FreeformContainerServiceBinder {
  public final ActivityTaskManagerService mAtm;
  public final Intent mService = new Intent();
  public final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public final ArrayList mServiceHistory = new ArrayList();
  public boolean mIsServiceRunning = false;
  public final ServiceConnection mServiceConnection =
      new ServiceConnection() { // from class:
                                // com.android.server.wm.FreeformContainerServiceBinder.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
          if (CoreRune.SAFE_DEBUG) {
            Slog.i(FreeformContainerServiceBinder.this.TAG, "onServiceConnected: " + componentName);
          }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
          if (CoreRune.SAFE_DEBUG) {
            Slog.i(
                FreeformContainerServiceBinder.this.TAG, "onServiceDisconnected: " + componentName);
          }
          if (FreeformContainerServiceBinder.this.mIsServiceRunning) {
            FreeformContainerServiceBinder.this.unbindServiceIfNeeded("service_disconnected");
            FreeformContainerServiceBinder.this.bindServiceIfNeeded("service_disconnected");
          }
        }
      };
  public final String TAG = getClass().getSimpleName();

  public FreeformContainerServiceBinder(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtm = activityTaskManagerService;
  }

  public void setServiceComponent(String str, String str2) {
    this.mService.setComponent(new ComponentName(str, str2));
  }

  public synchronized void bindServiceIfNeeded(String str) {
    if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW
        && (this instanceof SmartPopupViewServiceBinder)
        && this.mIsServiceRunning
        && !okToBind()
        && "startUser".equals(str)) {
      Slog.d(this.TAG, "Service running but not needed for this User");
      unbindServiceIfNeeded(str);
      return;
    }
    if (!this.mIsServiceRunning && okToBind()) {
      if (bindService()) {
        this.mIsServiceRunning = true;
        Slog.i(this.TAG, "bind service success, reason=" + str);
      } else {
        Slog.w(this.TAG, "bind service failed, reason=" + str);
      }
      this.mServiceHistory.add(
          "BindService["
              + str
              + "] Success="
              + this.mIsServiceRunning
              + " | "
              + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
    }
  }

  public boolean bindService() {
    return this.mAtm.mContext.bindServiceAsUser(
        this.mService, this.mServiceConnection, 1, UserHandle.SYSTEM);
  }

  public synchronized void unbindServiceIfNeeded(String str) {
    if (this.mIsServiceRunning && okToUnbind()) {
      unbindService();
      this.mIsServiceRunning = false;
      Slog.i(this.TAG, "unbind service success, reason=" + str);
      this.mServiceHistory.add(
          "UnbindService["
              + str
              + "] | "
              + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
    }
  }

  public void unbindService() {
    this.mAtm.mContext.unbindService(this.mServiceConnection);
  }

  public void dumpLocked(PrintWriter printWriter) {
    printWriter.println("[" + this.TAG + "]");
    printWriter.println("    " + this.TAG + " Running=" + this.mIsServiceRunning);
    if (this.mServiceHistory.isEmpty()) {
      return;
    }
    printWriter.println("    * History");
    int size = this.mServiceHistory.size();
    int i = CoreRune.SAFE_DEBUG ? 100 : 5;
    int i2 = size >= i ? size - i : 0;
    while (i2 < size) {
      StringBuilder sb = new StringBuilder();
      sb.append("      #");
      int i3 = i2 + 1;
      sb.append(i3);
      sb.append(" ");
      sb.append((String) this.mServiceHistory.get(i2));
      printWriter.println(sb.toString());
      i2 = i3;
    }
  }

  public boolean isServiceRunning() {
    return this.mIsServiceRunning;
  }

  public final boolean isStandaloneOrNewDex() {
    boolean z;
    WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (globalLock) {
      try {
        int dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
        z = true;
        if (dexModeLocked != 1 && dexModeLocked != 3) {
          z = false;
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
    return z;
  }

  public boolean okToBind() {
    return !isStandaloneOrNewDex();
  }

  public boolean okToUnbind() {
    return !okToBind();
  }
}
