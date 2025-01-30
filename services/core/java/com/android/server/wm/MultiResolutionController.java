package com.android.server.wm;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Point;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.MultiResolutionChangeRequestInfo;
import java.io.PrintWriter;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class MultiResolutionController {
  public Context mContext;
  public String mDisplaySizeDensityChangedReason;
  public String mReasonForMigrationDensity;
  public WindowManagerService mService;
  public final Point mTmpDisplaySize = new Point();
  public final String DENSITY_MIGRATION_FOR_TAB_S9_ULTRA = "density_migration_for_tabs_nine_ultra";

  public int getValidValue(int i, int i2) {
    return i < 0 ? i2 : i;
  }

  public MultiResolutionController(WindowManagerService windowManagerService, Context context) {
    this.mService = windowManagerService;
    this.mContext = context;
    if (CoreRune.FW_MIGRATION_DENSITY_FOR_TAB_S9_ULTRA) {
      if (Settings.Secure.getInt(
              context.getContentResolver(), "density_migration_for_tabs_nine_ultra", 0)
          == 1) {
        return;
      }
      migrationDensityForTabS9Ultra();
    }
  }

  public void getForcedDisplaySize(Point point) {
    int indexOf;
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        String string =
            Settings.Global.getString(this.mContext.getContentResolver(), "display_size_forced");
        if (string != null
            && string.length() > 0
            && (indexOf = string.indexOf(44)) > 0
            && string.lastIndexOf(44) == indexOf) {
          try {
            point.set(
                Integer.parseInt(string.substring(0, indexOf)),
                Integer.parseInt(string.substring(indexOf + 1)));
            WindowManagerService.resetPriorityAfterLockedSection();
            return;
          } catch (NumberFormatException e) {
            Slog.e("MultiResolutionController", "NumberFormatException", e);
          }
        }
        DisplayContent defaultDisplayContentLocked = this.mService.getDefaultDisplayContentLocked();
        point.set(
            defaultDisplayContentLocked.mInitialDisplayWidth,
            defaultDisplayContentLocked.mInitialDisplayHeight);
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public int getForcedDisplayDensity() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        String stringForUser =
            Settings.Secure.getStringForUser(
                this.mContext.getContentResolver(), "display_density_forced", 0);
        if (stringForUser != null && stringForUser.length() > 0) {
          try {
            int parseInt = Integer.parseInt(stringForUser);
            WindowManagerService.resetPriorityAfterLockedSection();
            return parseInt;
          } catch (NumberFormatException e) {
            Slog.e("MultiResolutionController", "NumberFormatException", e);
          }
        }
        int i = this.mService.getDefaultDisplayContentLocked().mInitialDisplayDensity;
        WindowManagerService.resetPriorityAfterLockedSection();
        return i;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void clearForcedDisplaySizeDensity(int i) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS")
        != 0) {
      throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
    }
    if (i != 0) {
      throw new IllegalArgumentException("input illegalArgument(displayId=" + i + ")");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
          if (displayContent != null) {
            getForcedDisplaySize(this.mTmpDisplaySize);
            Point point = this.mTmpDisplaySize;
            displayContent.setForcedSizeDensity(
                point.x, point.y, getForcedDisplayDensity(), false, false, 0);
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      updateDisplaySizeDensityChangedReason(i, -1, -1, -1, -1, false, null);
    }
  }

  public void setForcedDisplaySizeDensity(
      MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) {
    checkMultiResolutionChangePermission(multiResolutionChangeRequestInfo);
    int width = multiResolutionChangeRequestInfo.getWidth();
    int height = multiResolutionChangeRequestInfo.getHeight();
    int density = multiResolutionChangeRequestInfo.getDensity();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      setForcedDisplaySizeDensityInner(multiResolutionChangeRequestInfo);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      updateDisplaySizeDensityChangedReason(
          multiResolutionChangeRequestInfo.getDisplayId(),
          -1,
          width,
          height,
          density,
          multiResolutionChangeRequestInfo.getSaveToSettings(),
          multiResolutionChangeRequestInfo.getCallerInfo());
    }
  }

  public void setForcedDisplaySizeDensityInner(
      MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        DisplayContent displayContent =
            this.mService.mRoot.getDisplayContent(multiResolutionChangeRequestInfo.getDisplayId());
        if (displayContent != null) {
          boolean z =
              multiResolutionChangeRequestInfo.getSaveToSettings()
                  && multiResolutionChangeRequestInfo.getWidth() >= 0
                  && multiResolutionChangeRequestInfo.getHeight() >= 0;
          boolean z2 =
              multiResolutionChangeRequestInfo.getSaveToSettings()
                  && multiResolutionChangeRequestInfo.getDensity() >= 0;
          int validValue =
              getValidValue(
                  multiResolutionChangeRequestInfo.getWidth(), displayContent.mBaseDisplayWidth);
          int validValue2 =
              getValidValue(
                  multiResolutionChangeRequestInfo.getHeight(), displayContent.mBaseDisplayHeight);
          int validValue3 =
              getValidValue(
                  multiResolutionChangeRequestInfo.getDensity(),
                  displayContent.mBaseDisplayDensity);
          if (multiResolutionChangeRequestInfo.getWidth() < 0) {
            multiResolutionChangeRequestInfo.getHeight();
          }
          displayContent.setForcedSizeDensity(
              validValue,
              validValue2,
              validValue3,
              z,
              z2,
              multiResolutionChangeRequestInfo.getForcedHideCutout());
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public String getDisplaySizeDensityChangedReason() {
    return this.mDisplaySizeDensityChangedReason;
  }

  public void updateDisplaySizeDensityChangedReason(
      int i, int i2, int i3, int i4, int i5, boolean z, String str) {
    StringBuilder sb = new StringBuilder();
    int callingPid = Binder.getCallingPid();
    sb.append("Pid=");
    sb.append(callingPid);
    sb.append(", ProcessName=");
    sb.append(this.mService.mExt.getProcessName(callingPid));
    sb.append(", displayId=");
    sb.append(i);
    if (i2 != -1) {
      sb.append(", UserId=");
      sb.append(i2);
    }
    if (i3 != -1 && i4 != -1) {
      sb.append(", Size=");
      sb.append(i3);
      sb.append("x");
      sb.append(i4);
    }
    if (i5 != -1) {
      sb.append(", Density=");
      sb.append(i5);
    }
    if (z) {
      sb.append(", saveToSettings=true");
    }
    if (str != null) {
      sb.append(", info=");
      sb.append(str);
    }
    sb.append(", caller=");
    sb.append(Debug.getCallers(5));
    String sb2 = sb.toString();
    if (i == 0) {
      updateDefaultDisplaySizeDensityChangedReason(sb2);
    }
    Slog.i("MultiResolutionController", "updateDisplaySizeDensityChangedReason: " + sb2);
  }

  public void checkMultiResolutionChangePermission(
      MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) {
    if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS")
        != 0) {
      throw new SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
    }
    if (multiResolutionChangeRequestInfo.getDisplayId() == 0) {
      return;
    }
    throw new IllegalArgumentException(
        "input illegalArgument(displayId=" + multiResolutionChangeRequestInfo.getDisplayId() + ")");
  }

  public void updateDefaultDisplaySizeDensityChangedReason(String str) {
    this.mDisplaySizeDensityChangedReason = str;
    WindowManagerServiceExt.logCriticalInfo(str + " [" + Build.VERSION.INCREMENTAL + "]");
  }

  public void dumpLocked(String str, PrintWriter printWriter) {
    if (this.mDisplaySizeDensityChangedReason != null) {
      printWriter.print(str);
      printWriter.print("mDisplaySizeDensityChangedReason: ");
      printWriter.println(this.mDisplaySizeDensityChangedReason);
      printWriter.println();
    }
    if (!CoreRune.FW_MIGRATION_DENSITY_FOR_TAB_S9_ULTRA
        || TextUtils.isEmpty(this.mReasonForMigrationDensity)) {
      return;
    }
    printWriter.print(str);
    printWriter.println("mReasonForMigrationDensity=" + this.mReasonForMigrationDensity);
  }

  public final boolean hasSupportDensity(int i, int[] iArr) {
    for (int i2 : iArr) {
      if (i2 == i) {
        return true;
      }
    }
    return false;
  }

  public final void migrationDensityForTabS9Ultra() {
    int[] intArray = this.mContext.getResources().getIntArray(17236455);
    Slog.d("MultiResolutionController", "densityValues=" + Arrays.toString(intArray));
    if (intArray.length <= 0) {
      Slog.d("MultiResolutionController", "No need migration density. density array is empty");
      this.mReasonForMigrationDensity = "No need migration density. density array is empty";
      return;
    }
    ContentResolver contentResolver = this.mContext.getContentResolver();
    String stringForUser =
        Settings.Secure.getStringForUser(contentResolver, "display_density_forced", 0);
    if (TextUtils.isEmpty(stringForUser)) {
      Slog.d("MultiResolutionController", "Density is not set from user. Use lcd_density.");
      this.mReasonForMigrationDensity = "Density is not set from user. " + stringForUser;
      return;
    }
    int parseInt = Integer.parseInt(stringForUser);
    if (hasSupportDensity(parseInt, intArray)) {
      Slog.d("MultiResolutionController", "Density value is normal. currentDensity=" + parseInt);
      this.mReasonForMigrationDensity = "Density value is normal. " + parseInt;
      return;
    }
    try {
      int i = intArray[0];
      int abs = Math.abs(parseInt - i);
      for (int i2 = 1; i2 < intArray.length; i2++) {
        int abs2 = Math.abs(parseInt - intArray[i2]);
        if (abs > abs2) {
          i = intArray[i2];
          abs = abs2;
        }
      }
      Settings.Secure.putStringForUser(
          contentResolver, "display_density_forced", Integer.toString(i), 0);
      Slog.d("MultiResolutionController", "Set density to " + i);
      this.mReasonForMigrationDensity = "Set density to " + i;
      Settings.Secure.putInt(contentResolver, "density_migration_for_tabs_nine_ultra", 1);
    } catch (NumberFormatException unused) {
      Slog.d("MultiResolutionController", "parseInt API occurs NumberFormatException now.");
    }
  }
}
