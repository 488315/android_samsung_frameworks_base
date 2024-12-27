package com.android.server.display;

import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.display.mode.RefreshRateModeManager;
import com.android.server.power.Slog;

import com.samsung.android.rune.CoreRune;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayManagerService.DesiredDisplayModeSpecsObserver f$0;

    public /* synthetic */
    DisplayManagerService$DesiredDisplayModeSpecsObserver$$ExternalSyntheticLambda0(
            DisplayManagerService.DesiredDisplayModeSpecsObserver desiredDisplayModeSpecsObserver,
            int i) {
        this.$r8$classId = i;
        this.f$0 = desiredDisplayModeSpecsObserver;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        DisplayManagerService.DesiredDisplayModeSpecsObserver desiredDisplayModeSpecsObserver =
                this.f$0;
        LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
        desiredDisplayModeSpecsObserver.getClass();
        switch (i) {
            case 0:
                int i2 = logicalDisplay.mDisplayId;
                DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs =
                        DisplayManagerService.this.mDisplayModeDirector.getDesiredDisplayModeSpecs(
                                i2);
                DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs2 =
                        logicalDisplay.mDesiredDisplayModeSpecs;
                if (DisplayManagerService.DEBUG) {
                    Slog.i(
                            "DisplayManagerService",
                            "Comparing display specs: "
                                    + desiredDisplayModeSpecs
                                    + ", existing: "
                                    + desiredDisplayModeSpecs2);
                }
                if (desiredDisplayModeSpecs.equals(desiredDisplayModeSpecs2)) {
                    return;
                }
                logicalDisplay.mDesiredDisplayModeSpecs = desiredDisplayModeSpecs;
                desiredDisplayModeSpecsObserver.mChanged = true;
                if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                    RefreshRateModeManager refreshRateModeManager =
                            DisplayManagerService.this.mDisplayModeDirector.mRefreshRateModeManager;
                    if (i2 != 0) {
                        refreshRateModeManager.getClass();
                        return;
                    }
                    synchronized (refreshRateModeManager.mLock) {
                        refreshRateModeManager
                                .getController()
                                .logCurrentStateLocked(desiredDisplayModeSpecs);
                    }
                    return;
                }
                return;
            default:
                if (logicalDisplay.getDisplayInfoLocked().type == 1) {
                    DisplayManagerService.this.handleLogicalDisplayChangedLocked(logicalDisplay);
                    return;
                }
                return;
        }
    }
}
