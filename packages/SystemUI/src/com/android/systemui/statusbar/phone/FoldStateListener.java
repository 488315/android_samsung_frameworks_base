package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

public final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
    public final int[] foldedDeviceStates;
    public final int[] goToSleepDeviceStates;
    public final OnFoldStateChangeListener listener;
    public Boolean wasFolded;

    public interface OnFoldStateChangeListener {
    }

    public FoldStateListener(Context context, OnFoldStateChangeListener onFoldStateChangeListener) {
        this.listener = onFoldStateChangeListener;
        this.foldedDeviceStates = context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables);
        this.goToSleepDeviceStates = context.getResources().getIntArray(R.array.config_system_condition_providers);
    }

    public final void onDeviceStateChanged(DeviceState deviceState) {
        boolean contains = ArraysKt___ArraysKt.contains(deviceState.getIdentifier(), this.foldedDeviceStates);
        if (Intrinsics.areEqual(this.wasFolded, Boolean.valueOf(contains))) {
            return;
        }
        this.wasFolded = Boolean.valueOf(contains);
        boolean contains2 = ArraysKt___ArraysKt.contains(deviceState.getIdentifier(), this.goToSleepDeviceStates);
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) ((CentralSurfacesImpl$$ExternalSyntheticLambda0) this.listener).f$0;
        centralSurfacesImpl.getClass();
        Trace.beginSection("CentralSurfaces#onFoldedStateChanged");
        centralSurfacesImpl.mIsFolded = contains;
        centralSurfacesImpl.mSecLightRevealScrimHelper.isFolded = contains;
        ShadeController shadeController = centralSurfacesImpl.mShadeController;
        boolean isShadeFullyOpen = shadeController.isShadeFullyOpen();
        boolean isExpandingOrCollapsing = shadeController.isExpandingOrCollapsing();
        if (isShadeFullyOpen && !contains2 && centralSurfacesImpl.mState == 0) {
            ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).mLeaveOpenOnKeyguardHide = true;
        }
        if (centralSurfacesImpl.mState != 0 && (isShadeFullyOpen || isExpandingOrCollapsing)) {
            centralSurfacesImpl.mCloseQsBeforeScreenOff = true;
        }
        Trace.endSection();
    }
}
