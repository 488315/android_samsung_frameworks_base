package com.android.server.display.state;

import android.hardware.Sensor;
import android.hardware.display.DisplayManagerInternal;
import android.util.Pair;
import android.view.Display;

import com.android.server.display.DisplayPowerProximityStateController;
import com.android.server.display.WakelockController;
import com.android.server.display.WakelockController$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayStateController {
    public final int mDisplayId;
    public final DisplayPowerProximityStateController mDisplayPowerProximityStateController;
    public boolean mPerformScreenOffTransition = false;
    public int mDozeStateOverride = 0;
    public int mDozeStateOverrideReason = 0;

    public DisplayStateController(
            DisplayPowerProximityStateController displayPowerProximityStateController, int i) {
        this.mDisplayId = i;
        this.mDisplayPowerProximityStateController = displayPowerProximityStateController;
    }

    public final void setPerformScreenOffTransition() {
        this.mPerformScreenOffTransition = true;
    }

    public final boolean shouldPerformScreenOffTransition() {
        return this.mPerformScreenOffTransition;
    }

    public final Pair updateDisplayState(
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z, boolean z2) {
        int i;
        int i2;
        this.mPerformScreenOffTransition = false;
        int i3 = displayPowerRequest.policy;
        if (i3 != 0) {
            if (i3 != 1) {
                i = 2;
            } else {
                i = this.mDozeStateOverride;
                if (i != 0) {
                    i2 = this.mDozeStateOverrideReason;
                } else {
                    i = displayPowerRequest.dozeScreenState;
                    if (i != 0) {
                        i2 = displayPowerRequest.dozeScreenStateReason;
                    } else {
                        i = 3;
                    }
                }
            }
            i2 = 1;
        } else {
            this.mPerformScreenOffTransition = true;
            i = 1;
            i2 = 1;
        }
        DisplayPowerProximityStateController displayPowerProximityStateController =
                this.mDisplayPowerProximityStateController;
        displayPowerProximityStateController.mSkipRampBecauseOfProximityChangeToNegative = false;
        displayPowerProximityStateController.mSensorPositiveDebounceDelay =
                displayPowerRequest.proximityPositiveDebounce;
        displayPowerProximityStateController.mSensorNegativeDebounceDelay =
                displayPowerRequest.proximityNegativeDebounce;
        int i4 = displayPowerRequest.coverType;
        displayPowerProximityStateController.mIsViewTypeCover =
                i4 == 8 || i4 == 15 || i4 == 16 || i4 == 17;
        Sensor sensor = displayPowerProximityStateController.mProximitySensor;
        DisplayPowerProximityStateController.DisplayPowerProximityStateHandler
                displayPowerProximityStateHandler = displayPowerProximityStateController.mHandler;
        WakelockController wakelockController =
                displayPowerProximityStateController.mWakelockController;
        if (sensor != null) {
            if (displayPowerRequest.useProximitySensor
                    && displayPowerProximityStateController.isProximitySensorValidState(
                            displayPowerRequest)) {
                displayPowerProximityStateController.setProximitySensorEnabled(true);
                if (!displayPowerProximityStateController.mScreenOffBecauseOfProximity
                        && displayPowerProximityStateController.mProximity == 1
                        && !displayPowerProximityStateController.mIgnoreProximityUntilChanged) {
                    displayPowerProximityStateController.mScreenOffBecauseOfProximity = true;
                    wakelockController.acquireWakelock(1);
                    displayPowerProximityStateHandler.post(
                            new WakelockController$$ExternalSyntheticLambda0(
                                    wakelockController, 2));
                }
            } else if (displayPowerProximityStateController.mWaitingForNegativeProximity
                    && displayPowerProximityStateController.mScreenOffBecauseOfProximity
                    && displayPowerProximityStateController.mProximity == 1
                    && i != 1
                    && displayPowerProximityStateController.isProximitySensorValidState(
                            displayPowerRequest)) {
                displayPowerProximityStateController.setProximitySensorEnabled(true);
            } else {
                displayPowerProximityStateController.setProximitySensorEnabled(false);
                displayPowerProximityStateController.mWaitingForNegativeProximity = false;
            }
            if (displayPowerProximityStateController.mScreenOffBecauseOfProximity
                    && (displayPowerProximityStateController.mProximity != 1
                            || displayPowerProximityStateController.mIgnoreProximityUntilChanged)) {
                displayPowerProximityStateController.mScreenOffBecauseOfProximity = false;
                displayPowerProximityStateController.mSkipRampBecauseOfProximityChangeToNegative =
                        true;
                wakelockController.acquireWakelock(2);
                displayPowerProximityStateHandler.post(
                        new WakelockController$$ExternalSyntheticLambda0(wakelockController, 1));
            }
        } else {
            displayPowerProximityStateController.setProximitySensorEnabled(false);
            displayPowerProximityStateController.mWaitingForNegativeProximity = false;
            displayPowerProximityStateController.mIgnoreProximityUntilChanged = false;
            if (displayPowerProximityStateController.mScreenOffBecauseOfProximity) {
                displayPowerProximityStateController.mScreenOffBecauseOfProximity = false;
                displayPowerProximityStateController.mSkipRampBecauseOfProximityChangeToNegative =
                        true;
                wakelockController.acquireWakelock(2);
                displayPowerProximityStateHandler.post(
                        new WakelockController$$ExternalSyntheticLambda0(wakelockController, 1));
            }
        }
        if (!z || z2 || displayPowerProximityStateController.mScreenOffBecauseOfProximity) {
            i = 1;
        }
        int i5 = (this.mDisplayId != 4 || displayPowerRequest.coverClosed) ? i : 1;
        if (Display.isOffState(i5) && displayPowerRequest.lastGoToSleepReason == 13) {
            this.mPerformScreenOffTransition = false;
        }
        return new Pair(Integer.valueOf(i5), Integer.valueOf(i2));
    }
}
