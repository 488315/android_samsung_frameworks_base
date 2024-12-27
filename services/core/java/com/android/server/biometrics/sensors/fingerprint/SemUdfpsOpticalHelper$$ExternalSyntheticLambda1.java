package com.android.server.biometrics.sensors.fingerprint;

import android.os.RemoteException;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.biometrics.Utils;
import com.android.server.display.color.ColorDisplayService;

import com.samsung.android.displaysolution.ISemDisplaySolutionManager;

import java.io.File;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemUdfpsOpticalHelper$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemUdfpsOpticalHelper f$0;

    public /* synthetic */ SemUdfpsOpticalHelper$$ExternalSyntheticLambda1(
            SemUdfpsOpticalHelper semUdfpsOpticalHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = semUdfpsOpticalHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SemUdfpsOpticalHelper semUdfpsOpticalHelper = this.f$0;
        switch (i) {
            case 0:
                if (semUdfpsOpticalHelper.mDisplayAdjManager == null) {
                    semUdfpsOpticalHelper.mDisplayAdjManager =
                            new SemUdfpsOpticalHelper.DisplayAdjustmentManager();
                }
                SemUdfpsOpticalHelper.DisplayAdjustmentManager displayAdjustmentManager =
                        semUdfpsOpticalHelper.mDisplayAdjManager;
                displayAdjustmentManager.getClass();
                synchronized (SemUdfpsOpticalHelper.DisplayAdjustmentManager.class) {
                    try {
                        if (Utils.DEBUG) {
                            Slog.d(
                                    "FingerprintService",
                                    "DisplayAdjustmentManager.disable: start = "
                                            + displayAdjustmentManager.mIsDisabled);
                        }
                    } catch (Exception e) {
                        Slog.w(
                                "FingerprintService",
                                "DisplayAdjustmentManager.disable: " + e.getMessage());
                    } finally {
                    }
                    if (displayAdjustmentManager.mIsDisabled) {
                        return;
                    }
                    displayAdjustmentManager.mIsDisabled = true;
                    if (displayAdjustmentManager.mColorDisplayServiceInternal == null) {
                        displayAdjustmentManager.mColorDisplayServiceInternal =
                                (ColorDisplayService.ColorDisplayServiceInternal)
                                        LocalServices.getService(
                                                ColorDisplayService.ColorDisplayServiceInternal
                                                        .class);
                    }
                    ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal =
                            displayAdjustmentManager.mColorDisplayServiceInternal;
                    if (colorDisplayServiceInternal != null) {
                        colorDisplayServiceInternal.onOpticalUdfpsStarted();
                    }
                    return;
                }
            case 1:
                SemUdfpsOpticalHelper.DisplayAdjustmentManager displayAdjustmentManager2 =
                        semUdfpsOpticalHelper.mDisplayAdjManager;
                if (displayAdjustmentManager2 != null) {
                    synchronized (SemUdfpsOpticalHelper.DisplayAdjustmentManager.class) {
                        try {
                            if (Utils.DEBUG) {
                                Slog.d(
                                        "FingerprintService",
                                        "DisplayAdjustmentManager.restore: start = "
                                                + displayAdjustmentManager2.mIsDisabled);
                            }
                        } catch (Exception e2) {
                            Slog.w(
                                    "FingerprintService",
                                    "DisplayAdjustmentManager.restore: " + e2.getMessage());
                        } finally {
                        }
                        if (displayAdjustmentManager2.mIsDisabled) {
                            if (displayAdjustmentManager2.mColorDisplayServiceInternal == null) {
                                displayAdjustmentManager2.mColorDisplayServiceInternal =
                                        (ColorDisplayService.ColorDisplayServiceInternal)
                                                LocalServices.getService(
                                                        ColorDisplayService
                                                                .ColorDisplayServiceInternal.class);
                            }
                            ColorDisplayService.ColorDisplayServiceInternal
                                    colorDisplayServiceInternal2 =
                                            displayAdjustmentManager2.mColorDisplayServiceInternal;
                            if (colorDisplayServiceInternal2 != null) {
                                colorDisplayServiceInternal2.onOpticalUdfpsStopped();
                            }
                            displayAdjustmentManager2.mIsDisabled = false;
                        }
                    }
                    semUdfpsOpticalHelper.mDisplayAdjManager = null;
                    return;
                }
                return;
            case 2:
                semUdfpsOpticalHelper.getClass();
                try {
                    semUdfpsOpticalHelper.mSysFsProvider.getClass();
                    byte[] readFile = Utils.readFile(new File("sys/class/lcd/panel/window_type"));
                    semUdfpsOpticalHelper.mDisplayPanelType =
                            (readFile == null || readFile.length == 0)
                                    ? "No file"
                                    : new String(readFile, StandardCharsets.UTF_8).trim();
                    return;
                } catch (UnsupportedOperationException e3) {
                    Slog.e("FingerprintService", "getDisplayPanelType : ", e3);
                    return;
                }
            default:
                ISemDisplaySolutionManager displaySolutionManager =
                        semUdfpsOpticalHelper.getDisplaySolutionManager();
                if (displaySolutionManager != null) {
                    try {
                        semUdfpsOpticalHelper.mMaxBrightness =
                                displaySolutionManager.getFingerPrintBacklightValue(
                                        semUdfpsOpticalHelper.mNits);
                    } catch (RemoteException e4) {
                        Slog.w("FingerprintService", "writeMaxBrightnessInfo: " + e4.getMessage());
                    }
                }
                if (semUdfpsOpticalHelper.mMaxBrightness
                        <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    Slog.w(
                            "FingerprintService",
                            "writeMaxBrightnessInfo: use default value, "
                                    + semUdfpsOpticalHelper.mMaxBrightness);
                    semUdfpsOpticalHelper.mMaxBrightness = 319.0f;
                }
                byte[] bytes =
                        Integer.toString((int) semUdfpsOpticalHelper.mMaxBrightness)
                                .getBytes(StandardCharsets.UTF_8);
                semUdfpsOpticalHelper.mSysFsProvider.getClass();
                Utils.writeFile(new File("/sys/class/lcd/panel/mask_brightness"), bytes);
                return;
        }
    }
}
