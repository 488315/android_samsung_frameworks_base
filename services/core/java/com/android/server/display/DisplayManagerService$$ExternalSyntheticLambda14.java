package com.android.server.display;

import android.hardware.display.BrightnessConfiguration;

import com.samsung.android.rune.CoreRune;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda14
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda14(
            DisplayManagerService displayManagerService, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = displayManagerService;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DisplayPowerControllerInterface displayPowerControllerInterface;
        DisplayPowerControllerInterface displayPowerControllerInterface2;
        switch (this.$r8$classId) {
            case 0:
                DisplayManagerService displayManagerService = this.f$0;
                int i = this.f$1;
                LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                displayManagerService.getClass();
                if (i != logicalDisplay.mRefreshRateMode) {
                    logicalDisplay.mRefreshRateMode = i;
                    if (CoreRune.FW_VRR_DISCRETE) {
                        logicalDisplay.mInfo.set(null);
                        displayManagerService.handleLogicalDisplayChangedLocked(logicalDisplay);
                        break;
                    }
                }
                break;
            case 1:
                DisplayManagerService displayManagerService2 = this.f$0;
                int i2 = this.f$1;
                LogicalDisplay logicalDisplay2 = (LogicalDisplay) obj;
                displayManagerService2.getClass();
                BrightnessConfiguration brightnessConfigForDisplayWithPdsFallbackLocked =
                        displayManagerService2.getBrightnessConfigForDisplayWithPdsFallbackLocked(
                                i2, logicalDisplay2.mPrimaryDisplayDevice.mUniqueId);
                if (brightnessConfigForDisplayWithPdsFallbackLocked != null
                        && (displayPowerControllerInterface =
                                        (DisplayPowerControllerInterface)
                                                displayManagerService2.mDisplayPowerControllers.get(
                                                        logicalDisplay2.mDisplayId))
                                != null) {
                    ((DisplayPowerController) displayPowerControllerInterface)
                            .setBrightnessConfiguration(
                                    brightnessConfigForDisplayWithPdsFallbackLocked, false);
                    break;
                }
                break;
            default:
                DisplayManagerService displayManagerService3 = this.f$0;
                int i3 = this.f$1;
                LogicalDisplay logicalDisplay3 = (LogicalDisplay) obj;
                displayManagerService3.getClass();
                if (logicalDisplay3.mLeadDisplayId == i3
                        && (displayPowerControllerInterface2 =
                                        (DisplayPowerControllerInterface)
                                                displayManagerService3.mDisplayPowerControllers.get(
                                                        logicalDisplay3.mDisplayId))
                                != null) {
                    displayManagerService3.updateDisplayPowerControllerLeaderLocked(
                            displayPowerControllerInterface2, i3);
                    break;
                }
                break;
        }
    }
}
