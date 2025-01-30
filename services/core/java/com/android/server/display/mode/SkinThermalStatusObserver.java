package com.android.server.display.mode;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.Temperature;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.internal.os.BackgroundThread;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.mode.DisplayModeDirector;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class SkinThermalStatusObserver extends IThermalEventListener.Stub implements DisplayManager.DisplayListener {
    public final Handler mHandler;
    public final DisplayModeDirector.Injector mInjector;
    public boolean mLoggingEnabled;
    public int mStatus;
    public final Object mThermalObserverLock;
    public final SparseArray mThermalThrottlingByDisplay;
    public final VotesStorage mVotesStorage;

    public SkinThermalStatusObserver(DisplayModeDirector.Injector injector, VotesStorage votesStorage) {
        this(injector, votesStorage, BackgroundThread.getHandler());
    }

    public SkinThermalStatusObserver(DisplayModeDirector.Injector injector, VotesStorage votesStorage, Handler handler) {
        this.mThermalObserverLock = new Object();
        this.mStatus = -1;
        this.mThermalThrottlingByDisplay = new SparseArray();
        this.mInjector = injector;
        this.mVotesStorage = votesStorage;
        this.mHandler = handler;
    }

    public void observe() {
        if (this.mInjector.registerThermalServiceListener(this)) {
            this.mInjector.registerDisplayListener(this, this.mHandler, 7L);
            populateInitialDisplayInfo();
        }
    }

    public void setLoggingEnabled(boolean z) {
        this.mLoggingEnabled = z;
    }

    public void notifyThrottling(Temperature temperature) {
        int status = temperature.getStatus();
        synchronized (this.mThermalObserverLock) {
            if (this.mStatus == status) {
                return;
            }
            this.mStatus = status;
            this.mHandler.post(new Runnable() { // from class: com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SkinThermalStatusObserver.this.updateVotes();
                }
            });
            if (this.mLoggingEnabled) {
                Slog.d("SkinThermalStatusObserver", "New thermal throttling status , current thermal status = " + status);
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
        updateThermalRefreshRateThrottling(i);
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Display added:" + i);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(final int i) {
        synchronized (this.mThermalObserverLock) {
            this.mThermalThrottlingByDisplay.remove(i);
            this.mHandler.post(new Runnable() { // from class: com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SkinThermalStatusObserver.this.lambda$onDisplayRemoved$0(i);
                }
            });
        }
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Display removed and voted: displayId=" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayRemoved$0(int i) {
        this.mVotesStorage.updateVote(i, 16, null);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        updateThermalRefreshRateThrottling(i);
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Display changed:" + i);
        }
    }

    public final void populateInitialDisplayInfo() {
        DisplayInfo displayInfo = new DisplayInfo();
        Display[] displays = this.mInjector.getDisplays();
        int length = displays.length;
        SparseArray sparseArray = new SparseArray(length);
        for (Display display : displays) {
            int displayId = display.getDisplayId();
            display.getDisplayInfo(displayInfo);
            sparseArray.put(displayId, displayInfo.thermalRefreshRateThrottling);
        }
        synchronized (this.mThermalObserverLock) {
            for (int i = 0; i < length; i++) {
                this.mThermalThrottlingByDisplay.put(sparseArray.keyAt(i), (SparseArray) sparseArray.valueAt(i));
            }
        }
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Display initial info:" + sparseArray);
        }
    }

    public final void updateThermalRefreshRateThrottling(final int i) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mInjector.getDisplayInfo(i, displayInfo);
        SparseArray sparseArray = displayInfo.thermalRefreshRateThrottling;
        synchronized (this.mThermalObserverLock) {
            this.mThermalThrottlingByDisplay.put(i, sparseArray);
            this.mHandler.post(new Runnable() { // from class: com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SkinThermalStatusObserver.this.lambda$updateThermalRefreshRateThrottling$1(i);
                }
            });
        }
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Thermal throttling updated: display=" + i + ", map=" + sparseArray);
        }
    }

    public final void updateVotes() {
        int i;
        SparseArray clone;
        synchronized (this.mThermalObserverLock) {
            i = this.mStatus;
            clone = this.mThermalThrottlingByDisplay.clone();
        }
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Updating votes for status=" + i + ", map=" + clone);
        }
        int size = clone.size();
        for (int i2 = 0; i2 < size; i2++) {
            reportThrottlingIfNeeded(clone.keyAt(i2), i, (SparseArray) clone.valueAt(i2));
        }
    }

    /* renamed from: updateVoteForDisplay, reason: merged with bridge method [inline-methods] */
    public final void lambda$updateThermalRefreshRateThrottling$1(int i) {
        int i2;
        SparseArray sparseArray;
        synchronized (this.mThermalObserverLock) {
            i2 = this.mStatus;
            sparseArray = (SparseArray) this.mThermalThrottlingByDisplay.get(i);
        }
        if (sparseArray == null) {
            Slog.d("SkinThermalStatusObserver", "Updating votes, display already removed, display=" + i);
            return;
        }
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Updating votes for status=" + i2 + ", display =" + i + ", map=" + sparseArray);
        }
        reportThrottlingIfNeeded(i, i2, sparseArray);
    }

    public final void reportThrottlingIfNeeded(int i, int i2, SparseArray sparseArray) {
        if (i2 == -1) {
            return;
        }
        if (sparseArray.size() == 0) {
            fallbackReportThrottlingIfNeeded(i, i2);
            return;
        }
        SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange = findBestMatchingRefreshRateRange(i2, sparseArray);
        Vote forRenderFrameRates = findBestMatchingRefreshRateRange != null ? Vote.forRenderFrameRates(findBestMatchingRefreshRateRange.min, findBestMatchingRefreshRateRange.max) : null;
        this.mVotesStorage.updateVote(i, 16, forRenderFrameRates);
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Voted: vote=" + forRenderFrameRates + ", display =" + i);
        }
    }

    public final SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange(int i, SparseArray sparseArray) {
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

    public final void fallbackReportThrottlingIfNeeded(int i, int i2) {
        Vote forRenderFrameRates = i2 >= 4 ? Vote.forRenderFrameRates(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 60.0f) : null;
        this.mVotesStorage.updateVote(i, 16, forRenderFrameRates);
        if (this.mLoggingEnabled) {
            Slog.d("SkinThermalStatusObserver", "Voted(fallback): vote=" + forRenderFrameRates + ", display =" + i);
        }
    }

    public void dumpLocked(PrintWriter printWriter) {
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
}
