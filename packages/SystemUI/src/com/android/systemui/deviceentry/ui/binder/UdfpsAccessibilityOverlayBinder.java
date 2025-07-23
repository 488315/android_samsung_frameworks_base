package com.android.systemui.deviceentry.ui.binder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
                View findViewById;
                UdfpsAccessibilityOverlayViewModel udfpsAccessibilityOverlayViewModel2 = UdfpsAccessibilityOverlayViewModel.this;
                view.getClass();
                motionEvent.getClass();
                udfpsAccessibilityOverlayViewModel2.getClass();
                String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) udfpsAccessibilityOverlayViewModel2.udfpsOverlayParams.$$delegate_0.getValue();
                int pointerId = motionEvent.getPointerId(0);
                UdfpsUtils udfpsUtils = udfpsAccessibilityOverlayViewModel2.udfpsUtils;
                udfpsUtils.getClass();
                Point touchInNativeCoordinates = UdfpsUtils.getTouchInNativeCoordinates(pointerId, motionEvent, udfpsOverlayParams, false);
                if (UdfpsUtils.isWithinSensorArea(motionEvent.getPointerId(0), motionEvent, udfpsOverlayParams, false)) {
                    View rootView = view.getRootView();
                    if (rootView != null && (findViewById = rootView.findViewById(R.id.keyguard_bottom_shortcut_area)) != null) {
                        int i = udfpsOverlayParams.rotation;
                        final TextView textView = (TextView) findViewById.findViewById((i == 1 || i == 3) ? R.id.keyguard_indication_text : R.id.keyguard_upper_fingerprint_indication);
                        if (textView != null) {
                            textView.postDelayed(new Runnable() { // from class: com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel$onHoverEvent$1$1$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TextView textView2 = textView;
                                    textView2.announceForAccessibility(textView2.getText());
                                }
                            }, 500L);
                        }
                    }
                } else {
                    Context context = view.getContext();
                    int i2 = touchInNativeCoordinates.x;
                    int i3 = touchInNativeCoordinates.y;
                    udfpsUtils.getClass();
                    String onTouchOutsideOfSensorArea = UdfpsUtils.onTouchOutsideOfSensorArea(true, context, i2, i3, udfpsOverlayParams, false);
                    if (onTouchOutsideOfSensorArea != null) {
                        Resources resources = view.getContext().getResources();
                        String[] strArr = {resources.getString(R.string.accessibility_control_move_left), resources.getString(R.string.accessibility_control_move_down), resources.getString(R.string.accessibility_control_move_right), resources.getString(R.string.accessibility_control_move_up)};
                        view.announceForAccessibility(StringsKt__StringsKt.contains(onTouchOutsideOfSensorArea, "left", false) ? strArr[0] : StringsKt__StringsKt.contains(onTouchOutsideOfSensorArea, "down", false) ? strArr[1] : StringsKt__StringsKt.contains(onTouchOutsideOfSensorArea, "right", false) ? strArr[2] : StringsKt__StringsKt.contains(onTouchOutsideOfSensorArea, "up", false) ? strArr[3] : "");
                        return false;
                    }
                }
                return false;
            }
        });
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsAccessibilityOverlay, EmptyCoroutineContext.INSTANCE, new UdfpsAccessibilityOverlayBinder$bind$2(udfpsAccessibilityOverlayViewModel, udfpsAccessibilityOverlay, null));
    }
}
