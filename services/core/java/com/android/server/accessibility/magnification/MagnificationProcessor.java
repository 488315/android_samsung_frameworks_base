package com.android.server.accessibility.magnification;

import android.accessibilityservice.MagnificationConfig;
import android.graphics.PointF;
import android.graphics.Region;
import android.util.Slog;
import android.view.Display;
import android.view.accessibility.MagnificationAnimationCallback;
import com.android.server.accessibility.magnification.MagnificationConnectionManager;
import com.android.server.accessibility.magnification.MagnificationController;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MagnificationProcessor {
    public final MagnificationController mController;

    public MagnificationProcessor(MagnificationController magnificationController) {
        this.mController = magnificationController;
    }

    public final void dump(PrintWriter printWriter, ArrayList arrayList) {
        int i;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            int displayId = ((Display) arrayList.get(i2)).getDisplayId();
            MagnificationConfig magnificationConfig = getMagnificationConfig(displayId);
            printWriter.println("Magnifier on display#" + displayId);
            printWriter.append((CharSequence) ("    " + magnificationConfig)).println();
            Region region = new Region();
            getCurrentMagnificationRegion(displayId, region, true);
            if (!region.isEmpty()) {
                printWriter.append("    Magnification region=").append((CharSequence) region.toString()).println();
            }
            StringBuilder sb = new StringBuilder("    IdOfLastServiceToMagnify=");
            if (magnificationConfig.getMode() == 1) {
                i = this.mController.getFullScreenMagnificationController().getIdOfLastServiceToMagnify(displayId);
            } else {
                MagnificationConnectionManager magnificationConnectionManager = this.mController.getMagnificationConnectionManager();
                synchronized (magnificationConnectionManager.mLock) {
                    try {
                        MagnificationConnectionManager.WindowMagnifier windowMagnifier = (MagnificationConnectionManager.WindowMagnifier) magnificationConnectionManager.mWindowMagnifiers.get(displayId);
                        i = windowMagnifier != null ? windowMagnifier.mIdOfLastServiceToControl : -1;
                    } finally {
                    }
                }
            }
            sb.append(i);
            printWriter.append((CharSequence) sb.toString()).println();
            if (magnificationConfig.getMode() == 2) {
                printWriter.append((CharSequence) ("    TrackingTypingFocusEnabled=" + this.mController.getMagnificationConnectionManager().isTrackingTypingFocusEnabled(displayId))).println();
            }
        }
        printWriter.append((CharSequence) ("    SupportWindowMagnification=" + this.mController.mSupportWindowMagnification)).println();
        printWriter.append((CharSequence) ("    WindowMagnificationConnectionState=" + MagnificationConnectionManager.connectionStateToString(this.mController.getMagnificationConnectionManager().mConnectionState))).println();
    }

    public final int getControllingMode(int i) {
        int i2;
        if (this.mController.isActivated(i, 2)) {
            return 2;
        }
        if (this.mController.isActivated(i, 1)) {
            return 1;
        }
        MagnificationController magnificationController = this.mController;
        synchronized (magnificationController.mLock) {
            i2 = magnificationController.mLastMagnificationActivatedModeArray.get(i, 1);
        }
        return i2 == 2 ? 2 : 1;
    }

    public final void getCurrentMagnificationRegion(int i, Region region, boolean z) {
        int controllingMode = getControllingMode(i);
        if (controllingMode == 1) {
            getFullscreenMagnificationRegion(i, region, z);
            return;
        }
        if (controllingMode == 2) {
            MagnificationConnectionManager magnificationConnectionManager = this.mController.getMagnificationConnectionManager();
            synchronized (magnificationConnectionManager.mLock) {
                try {
                    MagnificationConnectionManager.WindowMagnifier windowMagnifier = (MagnificationConnectionManager.WindowMagnifier) magnificationConnectionManager.mWindowMagnifiers.get(i);
                    if (windowMagnifier != null && windowMagnifier.mEnabled) {
                        region.set(windowMagnifier.mSourceBounds);
                    }
                    region.setEmpty();
                } finally {
                }
            }
        }
    }

    public final void getFullscreenMagnificationRegion(int i, Region region, boolean z) {
        boolean z2;
        MagnificationController magnificationController = this.mController;
        if (magnificationController.getFullScreenMagnificationController().isRegistered(i) || !z) {
            z2 = false;
        } else {
            magnificationController.getFullScreenMagnificationController().register(i);
            z2 = true;
        }
        try {
            magnificationController.getFullScreenMagnificationController().getMagnificationRegion(i, region);
        } finally {
            if (z2) {
                unregister(i);
            }
        }
    }

    public final MagnificationConfig getMagnificationConfig(int i) {
        int controllingMode = getControllingMode(i);
        MagnificationConfig.Builder builder = new MagnificationConfig.Builder();
        MagnificationController magnificationController = this.mController;
        if (controllingMode == 1) {
            FullScreenMagnificationController fullScreenMagnificationController = magnificationController.getFullScreenMagnificationController();
            builder.setMode(controllingMode).setActivated(magnificationController.isActivated(i, 1)).setScale(fullScreenMagnificationController.getScale(i)).setCenterX(fullScreenMagnificationController.getCenterX(i)).setCenterY(fullScreenMagnificationController.getCenterY(i));
        } else if (controllingMode == 2) {
            MagnificationConnectionManager magnificationConnectionManager = magnificationController.getMagnificationConnectionManager();
            builder.setMode(controllingMode).setActivated(magnificationController.isActivated(i, 2)).setScale(magnificationConnectionManager.getScale(i)).setCenterX(magnificationConnectionManager.getCenterX(i)).setCenterY(magnificationConnectionManager.getCenterY(i));
        } else {
            builder.setActivated(false);
        }
        return builder.build();
    }

    public final void resetAllIfNeeded(int i) {
        int i2;
        FullScreenMagnificationController fullScreenMagnificationController = this.mController.getFullScreenMagnificationController();
        synchronized (fullScreenMagnificationController.mLock) {
            for (int i3 = 0; i3 < fullScreenMagnificationController.mDisplays.size(); i3++) {
                try {
                    fullScreenMagnificationController.resetIfNeeded(fullScreenMagnificationController.mDisplays.keyAt(i3), i);
                } finally {
                }
            }
        }
        MagnificationConnectionManager magnificationConnectionManager = this.mController.getMagnificationConnectionManager();
        synchronized (magnificationConnectionManager.mLock) {
            for (i2 = 0; i2 < magnificationConnectionManager.mWindowMagnifiers.size(); i2++) {
                try {
                    MagnificationConnectionManager.WindowMagnifier windowMagnifier = (MagnificationConnectionManager.WindowMagnifier) magnificationConnectionManager.mWindowMagnifiers.valueAt(i2);
                    if (windowMagnifier != null && windowMagnifier.mEnabled && i == windowMagnifier.mIdOfLastServiceToControl) {
                        windowMagnifier.disableWindowMagnificationInternal(null);
                    }
                } finally {
                }
            }
        }
    }

    public final boolean setMagnificationConfig(final int i, MagnificationConfig magnificationConfig, boolean z, int i2) {
        final int mode;
        int controllingMode = getControllingMode(i);
        if (magnificationConfig.getMode() != 0) {
            if (controllingMode == magnificationConfig.getMode()) {
                MagnificationController magnificationController = this.mController;
                synchronized (magnificationController.mLock) {
                    try {
                        if (((MagnificationController.DisableMagnificationCallback) magnificationController.mMagnificationEndRunnableSparseArray.get(i)) != null) {
                        }
                    } finally {
                    }
                }
            }
            final MagnificationController magnificationController2 = this.mController;
            if (MagnificationController.SEC_DEBUG) {
                magnificationController2.getClass();
                Slog.d("MagnificationController", "transitionMagnificationConfigMode displayId = " + i + ", config = " + magnificationConfig);
            }
            synchronized (magnificationController2.mLock) {
                try {
                    mode = magnificationConfig.getMode();
                    boolean isActivated = magnificationConfig.isActivated();
                    PointF currentMagnificationCenterLocked = magnificationController2.getCurrentMagnificationCenterLocked(i, mode);
                    PointF pointF = new PointF(magnificationConfig.getCenterX(), magnificationConfig.getCenterY());
                    if (currentMagnificationCenterLocked != null) {
                        pointF.set(Float.isNaN(magnificationConfig.getCenterX()) ? currentMagnificationCenterLocked.x : magnificationConfig.getCenterX(), Float.isNaN(magnificationConfig.getCenterY()) ? currentMagnificationCenterLocked.y : magnificationConfig.getCenterY());
                    }
                    MagnificationController.DisableMagnificationCallback disableMagnificationCallback = (MagnificationController.DisableMagnificationCallback) magnificationController2.mMagnificationEndRunnableSparseArray.get(i);
                    if (disableMagnificationCallback != null) {
                        Slog.w("MagnificationController", "Discard previous animation request");
                        disableMagnificationCallback.mExpired = true;
                        MagnificationController.this.setDisableMagnificationCallbackLocked(disableMagnificationCallback.mDisplayId, null);
                    }
                    FullScreenMagnificationController fullScreenMagnificationController = magnificationController2.getFullScreenMagnificationController();
                    MagnificationConnectionManager magnificationConnectionManager = magnificationController2.getMagnificationConnectionManager();
                    float scale = Float.isNaN(magnificationConfig.getScale()) ? mode == 2 ? magnificationController2.getFullScreenMagnificationController().getScale(i) : magnificationController2.getMagnificationConnectionManager().getScale(i) : magnificationConfig.getScale();
                    magnificationController2.setTransitionState(Integer.valueOf(i), Integer.valueOf(mode));
                    MagnificationAnimationCallback magnificationAnimationCallback = z ? new MagnificationAnimationCallback() { // from class: com.android.server.accessibility.magnification.MagnificationController$$ExternalSyntheticLambda1
                        public final void onResult(boolean z2) {
                            MagnificationController magnificationController3 = MagnificationController.this;
                            magnificationController3.mAms.changeMagnificationMode(i, mode);
                        }
                    } : null;
                    if (mode == 2) {
                        fullScreenMagnificationController.getClass();
                        fullScreenMagnificationController.reset(i, (MagnificationAnimationCallback) null);
                        if (isActivated) {
                            magnificationConnectionManager.enableWindowMagnification(i, scale, pointF.x, pointF.y, magnificationAnimationCallback, 0, i2);
                        } else {
                            magnificationConnectionManager.disableWindowMagnification(i, false);
                        }
                    } else if (mode == 1) {
                        magnificationConnectionManager.disableWindowMagnification(i, false, null);
                        if (isActivated) {
                            if (!fullScreenMagnificationController.isRegistered(i)) {
                                fullScreenMagnificationController.register(i);
                            }
                            fullScreenMagnificationController.setScaleAndCenter(scale, pointF.x, pointF.y, i, i2, magnificationAnimationCallback);
                        } else if (fullScreenMagnificationController.isRegistered(i)) {
                            fullScreenMagnificationController.reset(i, (MagnificationAnimationCallback) null);
                        }
                    }
                    if (!z) {
                        magnificationController2.mAms.changeMagnificationMode(i, mode);
                    }
                    magnificationController2.setTransitionState(Integer.valueOf(i), null);
                } catch (Throwable th) {
                    if (!z) {
                        magnificationController2.mAms.changeMagnificationMode(i, mode);
                    }
                    magnificationController2.setTransitionState(Integer.valueOf(i), null);
                    throw th;
                } finally {
                }
            }
            return true;
        }
        int mode2 = magnificationConfig.getMode();
        if (mode2 == 0) {
            mode2 = getControllingMode(i);
        }
        boolean isActivated2 = magnificationConfig.isActivated();
        if (mode2 != 1) {
            if (mode2 != 2) {
                return false;
            }
            if (isActivated2) {
                return this.mController.getMagnificationConnectionManager().enableWindowMagnification(i, magnificationConfig.getScale(), magnificationConfig.getCenterX(), magnificationConfig.getCenterY(), z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null, 0, i2);
            }
            return this.mController.getMagnificationConnectionManager().disableWindowMagnification(i, false, MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
        }
        if (!isActivated2) {
            return this.mController.getFullScreenMagnificationController().reset(i, z);
        }
        float scale2 = magnificationConfig.getScale();
        float centerX = magnificationConfig.getCenterX();
        float centerY = magnificationConfig.getCenterY();
        if (!this.mController.getFullScreenMagnificationController().isRegistered(i)) {
            this.mController.getFullScreenMagnificationController().register(i);
        }
        return this.mController.getFullScreenMagnificationController().setScaleAndCenter(i, scale2, centerX, centerY, z, i2);
    }

    public final void unregister(int i) {
        FullScreenMagnificationController fullScreenMagnificationController = this.mController.getFullScreenMagnificationController();
        synchronized (fullScreenMagnificationController.mLock) {
            fullScreenMagnificationController.unregisterLocked(i, false);
        }
    }
}
