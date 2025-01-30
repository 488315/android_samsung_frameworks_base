package com.android.server.wm.utils;

import android.graphics.Matrix;
import com.android.server.display.DisplayPowerController2;

/* loaded from: classes3.dex */
public abstract class CoordinateTransforms {
    public static void transformPhysicalToLogicalCoordinates(int i, int i2, int i3, Matrix matrix) {
        if (i == 0) {
            matrix.reset();
            return;
        }
        if (i == 1) {
            matrix.setRotate(270.0f);
            matrix.postTranslate(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, i2);
            return;
        }
        if (i == 2) {
            matrix.setRotate(180.0f);
            matrix.postTranslate(i2, i3);
        } else if (i == 3) {
            matrix.setRotate(90.0f);
            matrix.postTranslate(i3, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } else {
            throw new IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static void transformLogicalToPhysicalCoordinates(int i, int i2, int i3, Matrix matrix) {
        if (i == 0) {
            matrix.reset();
            return;
        }
        if (i == 1) {
            matrix.setRotate(90.0f);
            matrix.preTranslate(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -i2);
            return;
        }
        if (i == 2) {
            matrix.setRotate(180.0f);
            matrix.preTranslate(-i2, -i3);
        } else if (i == 3) {
            matrix.setRotate(270.0f);
            matrix.preTranslate(-i3, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } else {
            throw new IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static void computeRotationMatrix(int i, int i2, int i3, Matrix matrix) {
        if (i == 0) {
            matrix.reset();
            return;
        }
        if (i == 1) {
            matrix.setRotate(90.0f);
            matrix.postTranslate(i3, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        } else if (i == 2) {
            matrix.setRotate(180.0f);
            matrix.postTranslate(i2, i3);
        } else {
            if (i != 3) {
                return;
            }
            matrix.setRotate(270.0f);
            matrix.postTranslate(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, i2);
        }
    }
}
