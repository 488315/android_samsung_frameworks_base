package com.android.server.display.mode;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Temperature;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SkinThermalStatusObserver extends IThermalEventListener.Stub
        implements DisplayManager.DisplayListener {
    public final Handler mHandler;
    public final DisplayModeDirector.Injector mInjector;
    public boolean mLoggingEnabled;
    public final VotesStorage mVotesStorage;
    public final Object mThermalObserverLock = new Object();
    public int mStatus = 0;
    public final SparseArray mThermalThrottlingByDisplay = new SparseArray();

    public SkinThermalStatusObserver(
            DisplayModeDirector.Injector injector, VotesStorage votesStorage, Handler handler) {
        this.mInjector = injector;
        this.mVotesStorage = votesStorage;
        this.mHandler = handler;
    }

    public static SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange(
            int i, SparseArray sparseArray) {
        SurfaceControl.RefreshRateRange refreshRateRange = null;
        while (i >= 0) {
            refreshRateRange = (SurfaceControl.RefreshRateRange) sparseArray.get(i);
            if (refreshRateRange != null) {
                break;
            }
            i--;
        }
        return refreshRateRange;
    }

    public final void dumpLocked(PrintWriter printWriter) {
        int i;
        SparseArray clone;
        synchronized (this.mThermalObserverLock) {
            i = this.mStatus;
            clone = this.mThermalThrottlingByDisplay.clone();
        }
        printWriter.println("  SkinThermalStatusObserver:");
        printWriter.println("    mStatus: " + i);
        printWriter.println("    mThermalThrottlingByDisplay: " + clone);
    }

    public final void notifyThrottling(Temperature temperature) {
        int status = temperature.getStatus();
        synchronized (this.mThermalObserverLock) {
            try {
                if (this.mStatus == status) {
                    return;
                }
                this.mStatus = status;
                this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i;
                                SparseArray clone;
                                SkinThermalStatusObserver skinThermalStatusObserver =
                                        SkinThermalStatusObserver.this;
                                synchronized (skinThermalStatusObserver.mThermalObserverLock) {
                                    i = skinThermalStatusObserver.mStatus;
                                    clone =
                                            skinThermalStatusObserver.mThermalThrottlingByDisplay
                                                    .clone();
                                }
                                if (skinThermalStatusObserver.mLoggingEnabled) {
                                    Slog.d(
                                            "SkinThermalStatusObserver",
                                            "Updating votes for status=" + i + ", map=" + clone);
                                }
                                int size = clone.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    skinThermalStatusObserver.reportThrottlingIfNeeded(
                                            clone.keyAt(i2), i, (SparseArray) clone.valueAt(i2));
                                }
                            }
                        });
                if (this.mLoggingEnabled) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            status,
                            "New thermal throttling status , current thermal status = ",
                            "SkinThermalStatusObserver");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void observe() {
        ((DisplayModeDirector.RealInjector) this.mInjector).getClass();
        IThermalService asInterface =
                IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
        if (asInterface == null) {
            Slog.w(
                    "DisplayModeDirector",
                    "Could not observe thermal status. Service not available");
            return;
        }
        try {
            asInterface.registerThermalEventListenerWithType(this, 3);
            ((DisplayModeDirector.RealInjector) this.mInjector)
                    .getDisplayManager()
                    .registerDisplayListener(this, this.mHandler, 7L);
            DisplayInfo displayInfo = new DisplayInfo();
            Display[] displays =
                    ((DisplayModeDirector.RealInjector) this.mInjector)
                            .getDisplayManager()
                            .getDisplays(
                                    "android.hardware.display.category.ALL_INCLUDING_DISABLED");
            int length = displays.length;
            SparseArray sparseArray = new SparseArray(length);
            for (Display display : displays) {
                int displayId = display.getDisplayId();
                display.getDisplayInfo(displayInfo);
                sparseArray.put(displayId, displayInfo.thermalRefreshRateThrottling);
            }
            synchronized (this.mThermalObserverLock) {
                for (int i = 0; i < length; i++) {
                    try {
                        this.mThermalThrottlingByDisplay.put(
                                sparseArray.keyAt(i), (SparseArray) sparseArray.valueAt(i));
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            if (this.mLoggingEnabled) {
                Slog.d("SkinThermalStatusObserver", "Display initial info:" + sparseArray);
            }
        } catch (RemoteException e) {
            Slog.e("DisplayModeDirector", "Failed to register thermal status listener", e);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
        updateThermalRefreshRateThrottling(i);
        if (this.mLoggingEnabled) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Display added:", "SkinThermalStatusObserver");
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        updateThermalRefreshRateThrottling(i);
        if (this.mLoggingEnabled) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Display changed:", "SkinThermalStatusObserver");
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
        synchronized (this.mThermalObserverLock) {
            this.mThermalThrottlingByDisplay.remove(i);
            this.mHandler.post(new SkinThermalStatusObserver$$ExternalSyntheticLambda0(this, i, 1));
        }
        if (this.mLoggingEnabled) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "Display removed and voted: displayId=", "SkinThermalStatusObserver");
        }
    }

    public final void reportThrottlingIfNeeded(int i, int i2, SparseArray sparseArray) {
        RefreshRateVote.RenderVote renderVote;
        if (i2 == -1) {
            return;
        }
        if (sparseArray.size() == 0) {
            renderVote =
                    i2 >= 4
                            ? new RefreshRateVote.RenderVote(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE, 60.0f)
                            : null;
            this.mVotesStorage.updateVote(i, 22, renderVote);
            if (this.mLoggingEnabled) {
                Slog.d(
                        "SkinThermalStatusObserver",
                        "Voted(fallback): vote=" + renderVote + ", display =" + i);
                return;
            }
            return;
        }
        SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange =
                findBestMatchingRefreshRateRange(i2, sparseArray);
        renderVote =
                findBestMatchingRefreshRateRange != null
                        ? new RefreshRateVote.RenderVote(
                                findBestMatchingRefreshRateRange.min,
                                findBestMatchingRefreshRateRange.max)
                        : null;
        this.mVotesStorage.updateVote(i, 22, renderVote);
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Voted: vote=" + renderVote + ", display =" + i);
        }
    }

    public final void updateThermalRefreshRateThrottling(int i) {
        DisplayInfo displayInfo = new DisplayInfo();
        Display display =
                ((DisplayModeDirector.RealInjector) this.mInjector)
                        .getDisplayManager()
                        .getDisplay(i);
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        SparseArray sparseArray = displayInfo.thermalRefreshRateThrottling;
        synchronized (this.mThermalObserverLock) {
            this.mThermalThrottlingByDisplay.put(i, sparseArray);
            this.mHandler.post(new SkinThermalStatusObserver$$ExternalSyntheticLambda0(this, i, 0));
        }
        if (this.mLoggingEnabled) {
            Slog.d(
                    "SkinThermalStatusObserver",
                    "Thermal throttling updated: display=" + i + ", map=" + sparseArray);
        }
    }
}
