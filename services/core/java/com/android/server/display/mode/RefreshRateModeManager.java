package com.android.server.display.mode;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Slog;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayManagerService;

import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RefreshRateModeManager {
    public static final HashMap mControllerByToken = new HashMap();
    public Context mContext;
    public AnonymousClass1 mDisplayStateListener;
    public AtomicReference mDisplayToken;
    public Handler mHandler;
    public Object mLock;
    public ModeSettingsObserver mModeSettingsObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.mode.RefreshRateModeManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements DisplayManagerInternal.DisplayStateListener {
        public AnonymousClass1() {}

        public final void onFinish(int i, int i2, int i3) {
            if (i3 == 1 || i3 == 2) {
                final boolean z = i == 2;
                RefreshRateModeManager.this.getController().getClass();
                Boolean bool =
                        (Boolean) RefreshRateController.mIsDisplayPowerModeOnByDisplayType.get(i3);
                if (bool == null || z != bool.booleanValue()) {
                    Slog.d(
                            "RefreshRateModeManager",
                            "DisplayStateListener#onFinish, isPowerModeOn="
                                    + z
                                    + ", displayType="
                                    + i3);
                    RefreshRateModeManager.this.getController().getClass();
                    RefreshRateController.mIsDisplayPowerModeOnByDisplayType.put(
                            i3, Boolean.valueOf(z));
                    RefreshRateModeManager.this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.display.mode.RefreshRateModeManager$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RefreshRateModeManager.AnonymousClass1 anonymousClass1 =
                                            RefreshRateModeManager.AnonymousClass1.this;
                                    boolean z2 = z;
                                    synchronized (RefreshRateModeManager.this.mLock) {
                                        RefreshRateController controller =
                                                RefreshRateModeManager.this.getController();
                                        if (z2) {
                                            controller.onBrightnessChangedLocked();
                                        } else {
                                            controller.onDisplayStateOffLocked();
                                        }
                                    }
                                }
                            });
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeSettingsObserver extends ContentObserver {
        public final Context mContext;
        public final Uri mRefreshRateModeSetting;
        public final Uri mSubRefreshRateModeSetting;

        public ModeSettingsObserver(
                Context context,
                DisplayManagerService.DisplayManagerHandler displayManagerHandler) {
            super(displayManagerHandler);
            this.mRefreshRateModeSetting = Settings.Secure.getUriFor("refresh_rate_mode");
            this.mSubRefreshRateModeSetting = Settings.Secure.getUriFor("refresh_rate_mode_cover");
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            synchronized (RefreshRateModeManager.this.mLock) {
                try {
                    if (!this.mRefreshRateModeSetting.equals(uri)) {
                        if (CoreRune.FW_VRR_FOR_SUB_DISPLAY
                                && this.mSubRefreshRateModeSetting.equals(uri)) {}
                    }
                    RefreshRateModeManager.this.getController().updateRefreshRateModeLocked(false);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static void addControllerByDisplayToken(IBinder iBinder) {
        HashMap hashMap = mControllerByToken;
        synchronized (hashMap) {
            try {
                if (!hashMap.containsKey(iBinder)) {
                    hashMap.put(iBinder, RefreshRateController.makeController(iBinder));
                    Slog.d("RefreshRateModeManager", "display - " + iBinder + " registered");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpLocked(final PrintWriter printWriter) {
        printWriter.println("  RefreshRateModeManager");
        printWriter.println("    mRefreshRateModeByDisplayToken");
        mControllerByToken.forEach(
                new BiConsumer() { // from class:
                                   // com.android.server.display.mode.RefreshRateModeManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        PrintWriter printWriter2 = printWriter;
                        RefreshRateController refreshRateController = (RefreshRateController) obj2;
                        refreshRateController.getClass();
                        printWriter2.println(
                                "      DisplayToken: " + refreshRateController.mDisplayToken);
                        boolean z = CoreRune.FW_VRR_FOR_SUB_DISPLAY;
                        boolean z2 = refreshRateController.mIsSubScreen;
                        if (z) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("        PrimaryDisplay: "),
                                    !z2,
                                    printWriter2);
                        }
                        printWriter2.println(
                                "        ControllerType: "
                                        .concat(refreshRateController.getControllerType()));
                        printWriter2.println(
                                "        RefreshRateMode: "
                                        + Settings.Secure.refreshRateModeToString(
                                                refreshRateController.mRefreshRateMode.get()));
                        printWriter2.println(
                                "        ReportedRefreshRateMode: "
                                        + Settings.Secure.refreshRateModeToString(
                                                refreshRateController.mReportedRefreshRateMode
                                                        .get()));
                        if (refreshRateController.mDisplayToken != null) {
                            RefreshRateConfig.dump(printWriter2, "        ", z2);
                        }
                    }
                });
        boolean z = CoreRune.FW_VRR_SYSTEM_HISTORY;
        if (z) {
            printWriter.println();
            getController().getClass();
            printWriter.println("DisplayModeDirector History");
            RefreshRateController.mDisplayModeDirectorHistory.dump(printWriter);
            printWriter.println("RefreshRateToken History");
            RefreshRateTokenController refreshRateTokenController =
                    RefreshRateController.mRefreshRateTokenController;
            synchronized (refreshRateTokenController.mLock) {
                try {
                    printWriter.println(
                            "  RefreshRateTokens:" + refreshRateTokenController.mRefreshRateTokens);
                    if (z) {
                        refreshRateTokenController.mRefreshRateTokenHistory.dump(printWriter);
                    }
                } finally {
                }
            }
        }
    }

    public final RefreshRateController getController() {
        RefreshRateController refreshRateController;
        IBinder iBinder = (IBinder) this.mDisplayToken.get();
        HashMap hashMap = mControllerByToken;
        synchronized (hashMap) {
            refreshRateController = (RefreshRateController) hashMap.get(iBinder);
        }
        return refreshRateController;
    }

    public final void onLightSensorChanged(float f) {
        getController().getClass();
        if (RefreshRateController.getAmbientLuxZone(
                                ((Float)
                                                RefreshRateController.mAmbientLux.getAndSet(
                                                        Float.valueOf(f)))
                                        .floatValue())
                        != RefreshRateController.getAmbientLuxZone(f)
                || f <= -1.0f) {
            this.mHandler.post(new RefreshRateModeManager$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public final void updateDefaultDisplayToken() {
        DisplayAddress displayAddress;
        RefreshRateController refreshRateController;
        DisplayInfo displayInfo = DisplayManagerGlobal.getInstance().getDisplayInfo(0);
        if (displayInfo == null || (displayAddress = displayInfo.address) == null) {
            return;
        }
        IBinder displayToken = SurfaceControl.getDisplayToken(displayAddress);
        addControllerByDisplayToken(displayToken);
        IBinder iBinder = (IBinder) this.mDisplayToken.getAndSet(displayToken);
        boolean z = iBinder != displayToken;
        Slog.d(
                "RefreshRateModeManager",
                "updateDefaultDisplayToken: " + this.mDisplayToken + ", isChanged: " + z);
        if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && z) {
            synchronized (this.mLock) {
                HashMap hashMap = mControllerByToken;
                synchronized (hashMap) {
                    refreshRateController = (RefreshRateController) hashMap.get(iBinder);
                }
                refreshRateController.updateDefaultDisplayOrOffDisplayLocked();
                getController().updateRefreshRateModeLocked(z);
                getController().onBrightnessChangedLocked();
                getController().getClass();
                RefreshRateController.updateRefreshRateMaxLimitTokenLocked();
            }
        }
    }
}
