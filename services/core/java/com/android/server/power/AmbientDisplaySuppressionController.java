package com.android.server.power;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Pair;

import com.android.internal.statusbar.IStatusBarService;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class AmbientDisplaySuppressionController {
    public final PowerManagerService.AnonymousClass1 mCallback;
    public IStatusBarService mStatusBarService;
    public final Set mSuppressionTokens = Collections.synchronizedSet(new ArraySet());

    public AmbientDisplaySuppressionController(
            PowerManagerService.AnonymousClass1 anonymousClass1) {
        this.mCallback = anonymousClass1;
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "AmbientDisplaySuppressionController:",
                        " ambientDisplaySuppressed=");
        m$1.append(isSuppressed());
        printWriter.println(m$1.toString());
        printWriter.println(" mSuppressionTokens=" + this.mSuppressionTokens);
    }

    public final List getSuppressionTokens(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSuppressionTokens) {
            try {
                for (Pair pair : this.mSuppressionTokens) {
                    if (((Integer) pair.second).intValue() == i) {
                        arrayList.add((String) pair.first);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean isSuppressed() {
        return !this.mSuppressionTokens.isEmpty();
    }

    public final void suppress(int i, String str, boolean z) {
        IStatusBarService iStatusBarService;
        Objects.requireNonNull(str);
        Pair create = Pair.create(str, Integer.valueOf(i));
        boolean isSuppressed = isSuppressed();
        if (z) {
            this.mSuppressionTokens.add(create);
        } else {
            this.mSuppressionTokens.remove(create);
        }
        boolean isSuppressed2 = isSuppressed();
        if (isSuppressed2 != isSuppressed) {
            PowerManagerService.AnonymousClass1 anonymousClass1 = this.mCallback;
            synchronized (PowerManagerService.this.mLock) {
                PowerManagerService powerManagerService = PowerManagerService.this;
                if (powerManagerService.mDreamsDisabledByAmbientModeSuppressionConfig) {
                    if (!isSuppressed2
                            && powerManagerService.mIsPowered
                            && powerManagerService.mDreamsSupportedConfig
                            && powerManagerService.mDreamsEnabledSetting
                            && ((powerManagerService.mDreamsActivateOnSleepSetting
                                            || (powerManagerService.mDreamsActivateOnDockSetting
                                                    && powerManagerService.mDockState != 0))
                                    && powerManagerService.isItBedTimeYetLocked(
                                            (PowerGroup)
                                                    powerManagerService.mPowerGroups.get(0)))) {
                        powerManagerService.napInternal(1000, SystemClock.uptimeMillis(), true);
                    } else if (isSuppressed2) {
                        powerManagerService.mDirty |= 32;
                        powerManagerService.updatePowerStateLocked();
                    }
                }
            }
        }
        try {
            synchronized (this.mSuppressionTokens) {
                try {
                    synchronized (this) {
                        if (this.mStatusBarService == null) {
                            this.mStatusBarService =
                                    IStatusBarService.Stub.asInterface(
                                            ServiceManager.getService("statusbar"));
                        }
                        iStatusBarService = this.mStatusBarService;
                    }
                    iStatusBarService.suppressAmbientDisplay(isSuppressed2);
                } catch (Throwable th) {
                    throw th;
                } finally {
                }
            }
        } catch (RemoteException e) {
            android.util.Slog.e(
                    "AmbientDisplaySuppressionController", "Failed to suppress ambient display", e);
        }
    }
}
