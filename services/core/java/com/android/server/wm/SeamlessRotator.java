package com.android.server.wm;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SeamlessRotator {
    public final boolean mApplyFixedTransformHint;
    public final int mFixedTransformHint;
    public final float[] mFloat9;
    public final int mNewRotation;
    public final int mOldRotation;
    public final Matrix mTransform;

    public SeamlessRotator(int i, int i2, DisplayInfo displayInfo, boolean z) {
        Matrix matrix = new Matrix();
        this.mTransform = matrix;
        this.mFloat9 = new float[9];
        this.mOldRotation = i;
        this.mNewRotation = i2;
        this.mApplyFixedTransformHint = z;
        this.mFixedTransformHint = i;
        int i3 = displayInfo.rotation;
        boolean z2 = i3 == 1 || i3 == 3;
        int i4 = z2 ? displayInfo.logicalWidth : displayInfo.logicalHeight;
        int i5 = z2 ? displayInfo.logicalHeight : displayInfo.logicalWidth;
        Matrix matrix2 = new Matrix();
        if (i == 0) {
            matrix.reset();
        } else if (i == 1) {
            matrix.setRotate(90.0f);
            matrix.preTranslate(FullScreenMagnificationGestureHandler.MAX_SCALE, -i5);
        } else if (i == 2) {
            matrix.setRotate(180.0f);
            matrix.preTranslate(-i5, -i4);
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown rotation: "));
            }
            matrix.setRotate(270.0f);
            matrix.preTranslate(-i4, FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
        if (i2 == 0) {
            matrix2.reset();
        } else if (i2 == 1) {
            matrix2.setRotate(270.0f);
            matrix2.postTranslate(FullScreenMagnificationGestureHandler.MAX_SCALE, i5);
        } else if (i2 == 2) {
            matrix2.setRotate(180.0f);
            matrix2.postTranslate(i5, i4);
        } else {
            if (i2 != 3) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Unknown rotation: "));
            }
            matrix2.setRotate(90.0f);
            matrix2.postTranslate(i4, FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
        matrix.postConcat(matrix2);
    }

    public final void finish(SurfaceControl.Transaction transaction, WindowContainer windowContainer) {
        SurfaceControl surfaceControl = windowContainer.mSurfaceControl;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        transaction.setMatrix(windowContainer.mSurfaceControl, Matrix.IDENTITY_MATRIX, this.mFloat9);
        SurfaceControl surfaceControl2 = windowContainer.mSurfaceControl;
        Point point = windowContainer.mLastSurfacePosition;
        transaction.setPosition(surfaceControl2, point.x, point.y);
        if (this.mApplyFixedTransformHint) {
            transaction.unsetFixedTransformHint(windowContainer.mSurfaceControl);
        }
    }

    public final String toString() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.print("{old=");
        printWriter.print(this.mOldRotation);
        printWriter.print(", new=");
        printWriter.print(this.mNewRotation);
        printWriter.print("}");
        return "ForcedSeamlessRotator" + stringWriter.toString();
    }

    public final void unrotate(SurfaceControl.Transaction transaction, WindowContainer windowContainer) {
        transaction.setMatrix(windowContainer.getSurfaceControl(), this.mTransform, this.mFloat9);
        Point point = windowContainer.mLastSurfacePosition;
        float[] fArr = {point.x, point.y};
        this.mTransform.mapPoints(fArr);
        transaction.setPosition(windowContainer.getSurfaceControl(), fArr[0], fArr[1]);
        if (this.mApplyFixedTransformHint) {
            transaction.setFixedTransformHint(windowContainer.mSurfaceControl, this.mFixedTransformHint);
        }
    }
}
