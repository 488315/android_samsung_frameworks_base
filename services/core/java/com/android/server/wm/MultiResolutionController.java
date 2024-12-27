package com.android.server.wm;

import android.content.Context;
import android.graphics.Point;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.MultiResolutionChangeRequestInfo;

public final class MultiResolutionController {
    public final Context mContext;
    public String mDisplaySizeDensityChangedReason;
    public final WindowManagerService mService;
    public final Point mTmpDisplaySize = new Point();

    public MultiResolutionController(Context context, WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mContext = context;
    }

    public final int getForcedDisplayDensity() {
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

    public final void getForcedDisplaySize(Point point) {
        int indexOf;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                String string =
                        Settings.Global.getString(
                                this.mContext.getContentResolver(), "display_size_forced");
                if (!TextUtils.isEmpty(string)
                        && (indexOf = string.indexOf(44)) > 0
                        && string.lastIndexOf(44) == indexOf) {
                    try {
                        point.set(
                                Integer.parseInt(string.substring(0, indexOf)),
                                Integer.parseInt(string.substring(indexOf + 1)));
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    } catch (NumberFormatException e) {
                        Slog.e("MultiResolutionController", "NumberFormatException ", e);
                    }
                }
                DisplayContent defaultDisplayContentLocked =
                        this.mService.getDefaultDisplayContentLocked();
                if (defaultDisplayContentLocked == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    point.set(
                            defaultDisplayContentLocked.mInitialDisplayWidth,
                            defaultDisplayContentLocked.mInitialDisplayHeight);
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void setForcedDisplaySizeDensityInner(
            MultiResolutionChangeRequestInfo multiResolutionChangeRequestInfo) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent =
                        this.mService.mRoot.getDisplayContent(
                                multiResolutionChangeRequestInfo.getDisplayId());
                if (displayContent != null) {
                    boolean z =
                            multiResolutionChangeRequestInfo.getSaveToSettings()
                                    && multiResolutionChangeRequestInfo.getWidth() >= 0
                                    && multiResolutionChangeRequestInfo.getHeight() >= 0;
                    boolean z2 =
                            multiResolutionChangeRequestInfo.getSaveToSettings()
                                    && multiResolutionChangeRequestInfo.getDensity() >= 0;
                    int width = multiResolutionChangeRequestInfo.getWidth();
                    int i = displayContent.mBaseDisplayWidth;
                    if (width < 0) {
                        width = i;
                    }
                    int height = multiResolutionChangeRequestInfo.getHeight();
                    int i2 = height < 0 ? displayContent.mBaseDisplayHeight : height;
                    int density = multiResolutionChangeRequestInfo.getDensity();
                    int i3 = density < 0 ? displayContent.mBaseDisplayDensity : density;
                    if (multiResolutionChangeRequestInfo.getWidth() < 0) {
                        multiResolutionChangeRequestInfo.getHeight();
                    }
                    displayContent.setForcedSizeDensity(
                            width,
                            i2,
                            i3,
                            z,
                            multiResolutionChangeRequestInfo.getForcedHideCutout(),
                            z2);
                    if (CoreRune.FW_FLEXIBLE_TABLE_MODE) {
                        if (Math.abs((Math.max(width, i2) / Math.min(width, i2)) - 1.7777778f)
                                <= 0.001f) {
                            this.mService.setTableModeEnabled(false);
                        } else {
                            this.mService.setTableModeEnabled(true);
                        }
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateDefaultDisplaySizeDensityChangedReason(String str) {
        this.mDisplaySizeDensityChangedReason = str;
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " [");
        m.append(Build.VERSION.INCREMENTAL);
        m.append("]");
        WindowManagerServiceExt.logCriticalInfo(m.toString(), null);
    }

    public final void updateDisplaySizeDensityChangedReason(
            int i, int i2, int i3, String str, int i4, int i5, boolean z) {
        String str2;
        StringBuilder sb = new StringBuilder("Pid=");
        int callingPid = Binder.getCallingPid();
        sb.append(callingPid);
        sb.append(", ProcessName=");
        WindowManagerServiceExt windowManagerServiceExt = this.mService.mExt;
        WindowManagerGlobalLock windowManagerGlobalLock =
                windowManagerServiceExt.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowProcessController process =
                        windowManagerServiceExt.mService.mAtmService.mProcessMap.getProcess(
                                callingPid);
                str2 = process != null ? process.mName : null;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        sb.append(str2);
        sb.append(", displayId=");
        sb.append(i);
        if (i2 != -1) {
            sb.append(", UserId=");
            sb.append(i2);
        }
        if (i3 != -1 && i4 != -1) {
            AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                    i3, i4, ", Size=", "x", sb);
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
}
