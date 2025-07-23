package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
    public final OnFoldStateChangeListener listener;
    public Boolean wasFolded;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnFoldStateChangeListener {
    }

    public FoldStateListener(Context context, OnFoldStateChangeListener onFoldStateChangeListener) {
        this.listener = onFoldStateChangeListener;
        context.getResources().getIntArray(R.array.special_locale_codes);
        context.getResources().getIntArray(R.array.config_toastCrossUserPackages);
    }

    public final void onDeviceStateChanged(DeviceState deviceState) {
        boolean hasProperty = deviceState.hasProperty(11);
        boolean hasProperty2 = deviceState.hasProperty(13);
        if (Intrinsics.areEqual(this.wasFolded, Boolean.valueOf(hasProperty))) {
            return;
        }
        this.wasFolded = Boolean.valueOf(hasProperty);
        CentralSurfacesImpl$$ExternalSyntheticLambda23 centralSurfacesImpl$$ExternalSyntheticLambda23 = (CentralSurfacesImpl$$ExternalSyntheticLambda23) this.listener;
        centralSurfacesImpl$$ExternalSyntheticLambda23.getClass();
        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda23.f$0;
        Trace.beginSection("CentralSurfaces#onFoldedStateChanged");
        centralSurfacesImpl.mIsFolded = hasProperty;
        centralSurfacesImpl.mSecLightRevealScrimHelper.isFolded = hasProperty;
        ShadeController shadeController = centralSurfacesImpl.mShadeController;
        boolean isShadeFullyOpen = shadeController.isShadeFullyOpen();
        boolean isExpandingOrCollapsing = shadeController.isExpandingOrCollapsing();
        if (isShadeFullyOpen && !hasProperty2 && centralSurfacesImpl.mState == 0) {
            ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).setLeaveOpenOnKeyguardHide(true);
        }
        if (centralSurfacesImpl.mState != 0 && (isShadeFullyOpen || isExpandingOrCollapsing)) {
            centralSurfacesImpl.mCloseQsBeforeScreenOff = true;
        }
        Trace.endSection();
    }
}
