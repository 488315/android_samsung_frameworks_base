package com.android.systemui.biometrics;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class UdfpsController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UdfpsController udfpsController = (UdfpsController) this.f$0;
                Point point = (Point) this.f$1;
                UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
                if (udfpsControllerOverlay != null) {
                    boolean z = udfpsControllerOverlay.touchExplorationEnabled;
                    int i = point.x;
                    int i2 = point.y;
                    UdfpsOverlayParams udfpsOverlayParams = udfpsControllerOverlay.overlayParams;
                    udfpsControllerOverlay.udfpsUtils.getClass();
                    if (z) {
                        String[] stringArray = udfpsControllerOverlay.context.getResources().getStringArray(R.array.udfps_accessibility_touch_hints);
                        if (stringArray.length == 4) {
                            float f = udfpsOverlayParams.scaleFactor;
                            Rect rect = udfpsOverlayParams.sensorBounds;
                            double atan2 = Math.atan2((rect.centerY() / f) - i2, i - (rect.centerX() / f));
                            if (atan2 < 0.0d) {
                                atan2 += 6.283185307179586d;
                            }
                            double length = 360.0d / stringArray.length;
                            int degrees = ((int) ((((length / 2.0d) + Math.toDegrees(atan2)) % 360.0d) / length)) % stringArray.length;
                            int i3 = udfpsOverlayParams.rotation;
                            if (i3 == 1) {
                                degrees = (degrees + 1) % stringArray.length;
                            }
                            if (i3 == 3) {
                                degrees = (degrees + 3) % stringArray.length;
                            }
                            String str = stringArray[degrees];
                            break;
                        } else {
                            Log.e("UdfpsUtils", "expected exactly 4 touch hints, got " + stringArray.length + "?");
                            break;
                        }
                    }
                } else {
                    Log.e("UdfpsController", "touch outside sensor area receivedbut serverRequest is null");
                    break;
                }
                break;
            default:
                UdfpsController.UdfpsOverlayController udfpsOverlayController = (UdfpsController.UdfpsOverlayController) this.f$0;
                String str2 = (String) this.f$1;
                UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsController.this.mOverlay;
                if (udfpsControllerOverlay2 != null) {
                    UdfpsView udfpsView = udfpsControllerOverlay2.overlayView;
                    if (!(udfpsView == null)) {
                        udfpsView.debugMessage = str2;
                        udfpsView.postInvalidate();
                        break;
                    }
                }
                break;
        }
    }
}
