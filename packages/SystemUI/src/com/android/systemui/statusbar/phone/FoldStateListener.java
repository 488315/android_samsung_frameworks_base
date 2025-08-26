package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class FoldStateListener implements DeviceStateManager.DeviceStateCallback {
    public final OnFoldStateChangeListener listener;
    public Boolean wasFolded;

    public interface OnFoldStateChangeListener {
    }

    public FoldStateListener(Context context, OnFoldStateChangeListener onFoldStateChangeListener) throws Resources.NotFoundException {
        this.listener = onFoldStateChangeListener;
        context.getResources().getIntArray(R.array.special_locale_names);
        context.getResources().getIntArray(R.array.config_trustedAccessibilityServices);
    }

    public final void onDeviceStateChanged(DeviceState deviceState) {
        boolean zHasProperty = deviceState.hasProperty(11);
        boolean zHasProperty2 = deviceState.hasProperty(13);
        if (Intrinsics.areEqual(this.wasFolded, Boolean.valueOf(zHasProperty))) {
            return;
        }
        this.wasFolded = Boolean.valueOf(zHasProperty);
        CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda24 = (CentralSurfacesImpl$$ExternalSyntheticLambda24) this.listener;
        centralSurfacesImpl$$ExternalSyntheticLambda24.getClass();
        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda24.f$0;
        Trace.beginSection("CentralSurfaces#onFoldedStateChanged");
        centralSurfacesImpl.mIsFolded = zHasProperty;
        centralSurfacesImpl.mSecLightRevealScrimHelper.isFolded = zHasProperty;
        ShadeController shadeController = centralSurfacesImpl.mShadeController;
        boolean zIsShadeFullyOpen = shadeController.isShadeFullyOpen();
        boolean zIsExpandingOrCollapsing = shadeController.isExpandingOrCollapsing();
        if (zIsShadeFullyOpen && !zHasProperty2 && centralSurfacesImpl.mState == 0) {
            ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).setLeaveOpenOnKeyguardHide(true);
        }
        if (centralSurfacesImpl.mState != 0 && (zIsShadeFullyOpen || zIsExpandingOrCollapsing)) {
            centralSurfacesImpl.mCloseQsBeforeScreenOff = true;
        }
        Trace.endSection();
    }
}
