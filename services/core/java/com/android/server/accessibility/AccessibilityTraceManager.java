package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityTrace;
import android.os.Binder;
import android.os.Build;
import android.util.Slog;
import com.android.server.wm.AccessibilityController;
import com.android.server.wm.WindowManagerInternal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccessibilityTraceManager implements AccessibilityTrace {
    public static AccessibilityTraceManager sInstance;
    public final WindowManagerInternal.AccessibilityControllerInternal mA11yController;
    public final Object mA11yMSLock;
    public volatile long mEnabledLoggingFlags = 0;
    public final AccessibilityManagerService mService;

    public AccessibilityTraceManager(WindowManagerInternal.AccessibilityControllerInternal accessibilityControllerInternal, AccessibilityManagerService accessibilityManagerService, Object obj) {
        this.mA11yController = accessibilityControllerInternal;
        this.mService = accessibilityManagerService;
        this.mA11yMSLock = obj;
    }

    public final int getTraceStateForAccessibilityManagerClientState() {
        int i = isA11yTracingEnabledForTypes(16L) ? 256 : 0;
        if (isA11yTracingEnabledForTypes(32L)) {
            i |= 512;
        }
        if (isA11yTracingEnabledForTypes(262144L)) {
            i |= 1024;
        }
        return isA11yTracingEnabledForTypes(16384L) ? i | 2048 : i;
    }

    public final boolean isA11yTracingEnabled() {
        return this.mEnabledLoggingFlags != 0;
    }

    public final boolean isA11yTracingEnabledForTypes(long j) {
        return (j & this.mEnabledLoggingFlags) != 0;
    }

    public final void logTrace(long j, String str, long j2, String str2, int i, long j3, int i2, StackTraceElement[] stackTraceElementArr, Set set) {
        if (isA11yTracingEnabledForTypes(j2)) {
            WindowManagerInternal.AccessibilityControllerInternal accessibilityControllerInternal = this.mA11yController;
            byte[] bytes = "".getBytes();
            Set hashSet = set == null ? new HashSet() : set;
            AccessibilityController.AccessibilityTracing accessibilityTracing = ((AccessibilityController.AccessibilityControllerInternalImpl) accessibilityControllerInternal).mTracing;
            if (accessibilityTracing.mEnabled) {
                accessibilityTracing.log(str, j2, str2, bytes, i2, stackTraceElementArr, j, String.valueOf(i), String.valueOf(j3), hashSet);
            }
        }
    }

    public final void logTrace(String str, long j) {
        logTrace(str, j, "");
    }

    public final void logTrace(String str, long j, String str2) {
        if (isA11yTracingEnabledForTypes(j)) {
            WindowManagerInternal.AccessibilityControllerInternal accessibilityControllerInternal = this.mA11yController;
            ((AccessibilityController.AccessibilityControllerInternalImpl) accessibilityControllerInternal).mTracing.logState(str, j, str2, "".getBytes(), Binder.getCallingUid(), Thread.currentThread().getStackTrace(), new HashSet(Arrays.asList("logTrace")));
        }
    }

    public final void startTrace(long j) {
        if (j == 0) {
            return;
        }
        long j2 = this.mEnabledLoggingFlags;
        this.mEnabledLoggingFlags = j;
        if ((this.mEnabledLoggingFlags & 278576) != (j2 & 278576)) {
            synchronized (this.mA11yMSLock) {
                AccessibilityManagerService accessibilityManagerService = this.mService;
                accessibilityManagerService.scheduleUpdateClientsIfNeededLocked(accessibilityManagerService.getCurrentUserState(), false);
            }
        }
        AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = (AccessibilityController.AccessibilityControllerInternalImpl) this.mA11yController;
        accessibilityControllerInternalImpl.mEnabledTracingFlags = j;
        AccessibilityController.AccessibilityTracing accessibilityTracing = accessibilityControllerInternalImpl.mTracing;
        accessibilityTracing.getClass();
        if (Build.IS_USER) {
            Slog.e("AccessibilityTracing", "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (accessibilityTracing.mLock) {
            accessibilityTracing.mEnabled = true;
            accessibilityTracing.mBuffer.resetBuffer();
        }
    }

    public final void stopTrace() {
        boolean isA11yTracingEnabled = isA11yTracingEnabled();
        long j = this.mEnabledLoggingFlags;
        this.mEnabledLoggingFlags = 0L;
        if ((this.mEnabledLoggingFlags & 278576) != (j & 278576)) {
            synchronized (this.mA11yMSLock) {
                AccessibilityManagerService accessibilityManagerService = this.mService;
                accessibilityManagerService.scheduleUpdateClientsIfNeededLocked(accessibilityManagerService.getCurrentUserState(), false);
            }
        }
        if (isA11yTracingEnabled) {
            AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = (AccessibilityController.AccessibilityControllerInternalImpl) this.mA11yController;
            AccessibilityController.AccessibilityTracing accessibilityTracing = accessibilityControllerInternalImpl.mTracing;
            accessibilityTracing.getClass();
            if (Build.IS_USER) {
                Slog.e("AccessibilityTracing", "Error: Tracing is not supported on user builds.");
            } else {
                synchronized (accessibilityTracing.mLock) {
                    try {
                        accessibilityTracing.mEnabled = false;
                        if (accessibilityTracing.mEnabled) {
                            Slog.e("AccessibilityTracing", "Error: tracing enabled while waiting for flush.");
                        } else {
                            accessibilityTracing.mHandler.sendEmptyMessage(2);
                        }
                    } finally {
                    }
                }
            }
            accessibilityControllerInternalImpl.mEnabledTracingFlags = 0L;
        }
    }
}
