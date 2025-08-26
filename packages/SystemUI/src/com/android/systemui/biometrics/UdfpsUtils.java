package com.android.systemui.biometrics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.RotationUtils;
import android.view.MotionEvent;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;

/* loaded from: classes.dex */
public class UdfpsUtils {
    public static Point getPortraitTouch(int i, MotionEvent motionEvent, UdfpsOverlayParams udfpsOverlayParams) {
        Point point = new Point((int) motionEvent.getRawX(i), (int) motionEvent.getRawY(i));
        int i2 = udfpsOverlayParams.rotation;
        if (i2 != 1 && i2 != 3) {
            return point;
        }
        RotationUtils.rotatePoint(point, RotationUtils.deltaRotation(i2, 0), udfpsOverlayParams.logicalDisplayWidth, udfpsOverlayParams.logicalDisplayHeight);
        return point;
    }

    public static Point getTouchInNativeCoordinates(int i, MotionEvent motionEvent, UdfpsOverlayParams udfpsOverlayParams, boolean z) {
        Point portraitTouch = z ? getPortraitTouch(i, motionEvent, udfpsOverlayParams) : new Point((int) motionEvent.getRawX(i), (int) motionEvent.getRawY(i));
        float f = udfpsOverlayParams.scaleFactor;
        portraitTouch.x = (int) (portraitTouch.x / f);
        portraitTouch.y = (int) (portraitTouch.y / f);
        return portraitTouch;
    }

    public static boolean isWithinSensorArea(int i, MotionEvent motionEvent, UdfpsOverlayParams udfpsOverlayParams, boolean z) {
        Point portraitTouch = z ? getPortraitTouch(i, motionEvent, udfpsOverlayParams) : new Point((int) motionEvent.getRawX(i), (int) motionEvent.getRawY(i));
        return udfpsOverlayParams.sensorBounds.contains(portraitTouch.x, portraitTouch.y);
    }

    public static String onTouchOutsideOfSensorArea(boolean z, Context context, int i, int i2, UdfpsOverlayParams udfpsOverlayParams, boolean z2) {
        if (!z) {
            return null;
        }
        Resources resources = context.getResources();
        String[] strArr = {resources.getString(R.string.udfps_accessibility_touch_hints_left), resources.getString(R.string.udfps_accessibility_touch_hints_down), resources.getString(R.string.udfps_accessibility_touch_hints_right), resources.getString(R.string.udfps_accessibility_touch_hints_up)};
        float fCenterX = udfpsOverlayParams.sensorBounds.centerX() / udfpsOverlayParams.scaleFactor;
        double dAtan2 = Math.atan2((udfpsOverlayParams.sensorBounds.centerY() / r7) - i2, i - fCenterX);
        if (dAtan2 < 0.0d) {
            dAtan2 += 6.283185307179586d;
        }
        double d = 360.0d / 4;
        int degrees = ((int) ((((d / 2.0d) + Math.toDegrees(dAtan2)) % 360.0d) / d)) % 4;
        if (z2) {
            int i3 = udfpsOverlayParams.rotation;
            if (i3 == 1) {
                degrees = (degrees + 1) % 4;
            }
            if (i3 == 3) {
                degrees = (degrees + 3) % 4;
            }
        }
        return strArr[degrees];
    }
}
