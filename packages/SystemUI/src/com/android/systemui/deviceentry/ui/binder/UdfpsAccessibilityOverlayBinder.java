package com.android.systemui.deviceentry.ui.binder;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;

public final class UdfpsAccessibilityOverlayBinder {
    static {
        new UdfpsAccessibilityOverlayBinder();
    }

    private UdfpsAccessibilityOverlayBinder() {
    }

    public static final void bind(UdfpsAccessibilityOverlay udfpsAccessibilityOverlay, final UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel) {
        udfpsAccessibilityOverlay.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder$bind$1
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel2 = UdfpsAccessibilityOverlayViewModel.this;
                Intrinsics.checkNotNull(view);
                Intrinsics.checkNotNull(motionEvent);
                UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) udfpsAccessibilityOverlayViewModel2.udfpsOverlayParams.getValue();
                int pointerId = motionEvent.getPointerId(0);
                UdfpsUtils udfpsUtils = udfpsAccessibilityOverlayViewModel2.udfpsUtils;
                udfpsUtils.getClass();
                Point touchInNativeCoordinates = UdfpsUtils.getTouchInNativeCoordinates(pointerId, motionEvent, udfpsOverlayParams, false);
                if (!UdfpsUtils.isWithinSensorArea(motionEvent.getPointerId(0), motionEvent, udfpsOverlayParams, false)) {
                    Context context = view.getContext();
                    int i = touchInNativeCoordinates.x;
                    int i2 = touchInNativeCoordinates.y;
                    udfpsUtils.getClass();
                    String onTouchOutsideOfSensorArea = UdfpsUtils.onTouchOutsideOfSensorArea(true, context, i, i2, udfpsOverlayParams, false);
                    if (onTouchOutsideOfSensorArea != null) {
                        view.announceForAccessibility(onTouchOutsideOfSensorArea);
                    }
                }
                return false;
            }
        });
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsAccessibilityOverlay, EmptyCoroutineContext.INSTANCE, new UdfpsAccessibilityOverlayBinder$bind$2(udfpsAccessibilityOverlayViewModel, udfpsAccessibilityOverlay, null));
    }
}
