package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import com.google.android.material.shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShapePath {
    public float currentShadowAngle;
    public float endShadowAngle;
    public float endX;
    public float endY;
    public final List operations = new ArrayList();
    public final List shadowCompatOperations = new ArrayList();
    public float startX;
    public float startY;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ArcShadowOperation extends ShadowCompatOperation {
        public final PathArcOperation operation;

        public ArcShadowOperation(PathArcOperation pathArcOperation) {
            this.operation = pathArcOperation;
        }

        @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
        public final void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas) {
            PathArcOperation pathArcOperation = this.operation;
            float f = pathArcOperation.startAngle;
            float f2 = pathArcOperation.sweepAngle;
            RectF rectF = new RectF(pathArcOperation.left, pathArcOperation.top, pathArcOperation.right, pathArcOperation.bottom);
            shadowRenderer.getClass();
            boolean z = f2 < 0.0f;
            Path path = shadowRenderer.scratch;
            int[] iArr = ShadowRenderer.cornerColors;
            if (z) {
                iArr[0] = 0;
                iArr[1] = shadowRenderer.shadowEndColor;
                iArr[2] = shadowRenderer.shadowMiddleColor;
                iArr[3] = shadowRenderer.shadowStartColor;
            } else {
                path.rewind();
                path.moveTo(rectF.centerX(), rectF.centerY());
                path.arcTo(rectF, f, f2);
                path.close();
                float f3 = -i;
                rectF.inset(f3, f3);
                iArr[0] = 0;
                iArr[1] = shadowRenderer.shadowStartColor;
                iArr[2] = shadowRenderer.shadowMiddleColor;
                iArr[3] = shadowRenderer.shadowEndColor;
            }
            float width = rectF.width() / 2.0f;
            if (width <= 0.0f) {
                return;
            }
            float f4 = 1.0f - (i / width);
            float[] fArr = ShadowRenderer.cornerPositions;
            fArr[1] = f4;
            fArr[2] = ((1.0f - f4) / 2.0f) + f4;
            RadialGradient radialGradient = new RadialGradient(rectF.centerX(), rectF.centerY(), width, iArr, fArr, Shader.TileMode.CLAMP);
            Paint paint = shadowRenderer.cornerShadowPaint;
            paint.setShader(radialGradient);
            canvas.save();
            canvas.concat(matrix);
            canvas.scale(1.0f, rectF.height() / rectF.width());
            if (!z) {
                canvas.clipPath(path, Region.Op.DIFFERENCE);
                canvas.drawPath(path, shadowRenderer.transparentPaint);
            }
            canvas.drawArc(rectF, f, f2, true, paint);
            canvas.restore();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LineShadowOperation extends ShadowCompatOperation {
        public final PathLineOperation operation;
        public final float startX;
        public final float startY;

        public LineShadowOperation(PathLineOperation pathLineOperation, float f, float f2) {
            this.operation = pathLineOperation;
            this.startX = f;
            this.startY = f2;
        }

        @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
        public final void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas) {
            PathLineOperation pathLineOperation = this.operation;
            float f = pathLineOperation.f462y;
            float f2 = this.startY;
            float f3 = pathLineOperation.f461x;
            float f4 = this.startX;
            RectF rectF = new RectF(0.0f, 0.0f, (float) Math.hypot(f - f2, f3 - f4), 0.0f);
            Matrix matrix2 = this.renderMatrix;
            matrix2.set(matrix);
            matrix2.preTranslate(f4, f2);
            matrix2.preRotate(getAngle());
            shadowRenderer.getClass();
            rectF.bottom += i;
            rectF.offset(0.0f, -i);
            int[] iArr = ShadowRenderer.edgeColors;
            iArr[0] = shadowRenderer.shadowEndColor;
            iArr[1] = shadowRenderer.shadowMiddleColor;
            iArr[2] = shadowRenderer.shadowStartColor;
            Paint paint = shadowRenderer.edgeShadowPaint;
            float f5 = rectF.left;
            paint.setShader(new LinearGradient(f5, rectF.top, f5, rectF.bottom, iArr, ShadowRenderer.edgePositions, Shader.TileMode.CLAMP));
            canvas.save();
            canvas.concat(matrix2);
            canvas.drawRect(rectF, paint);
            canvas.restore();
        }

        public final float getAngle() {
            PathLineOperation pathLineOperation = this.operation;
            return (float) Math.toDegrees(Math.atan((pathLineOperation.f462y - this.startY) / (pathLineOperation.f461x - this.startX)));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PathArcOperation extends PathOperation {
        public static final RectF rectF = new RectF();
        public float bottom;
        public float left;
        public float right;
        public float startAngle;
        public float sweepAngle;
        public float top;

        public PathArcOperation(float f, float f2, float f3, float f4) {
            this.left = f;
            this.top = f2;
            this.right = f3;
            this.bottom = f4;
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public final void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            RectF rectF2 = rectF;
            rectF2.set(this.left, this.top, this.right, this.bottom);
            path.arcTo(rectF2, this.startAngle, this.sweepAngle, false);
            path.transform(matrix);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PathLineOperation extends PathOperation {

        /* renamed from: x */
        public float f461x;

        /* renamed from: y */
        public float f462y;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public final void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.f461x, this.f462y);
            path.transform(matrix);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class PathOperation {
        public final Matrix matrix = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ShadowCompatOperation {
        public static final Matrix IDENTITY_MATRIX = new Matrix();
        public final Matrix renderMatrix = new Matrix();

        public abstract void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas);
    }

    public ShapePath() {
        reset(0.0f, 0.0f, 270.0f, 0.0f);
    }

    public final void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        PathArcOperation pathArcOperation = new PathArcOperation(f, f2, f3, f4);
        pathArcOperation.startAngle = f5;
        pathArcOperation.sweepAngle = f6;
        ((ArrayList) this.operations).add(pathArcOperation);
        ArcShadowOperation arcShadowOperation = new ArcShadowOperation(pathArcOperation);
        float f7 = f5 + f6;
        boolean z = f6 < 0.0f;
        if (z) {
            f5 = (f5 + 180.0f) % 360.0f;
        }
        float f8 = z ? (180.0f + f7) % 360.0f : f7;
        addConnectingShadowIfNecessary(f5);
        ((ArrayList) this.shadowCompatOperations).add(arcShadowOperation);
        this.currentShadowAngle = f8;
        double d = f7;
        this.endX = (((f3 - f) / 2.0f) * ((float) Math.cos(Math.toRadians(d)))) + ((f + f3) * 0.5f);
        this.endY = (((f4 - f2) / 2.0f) * ((float) Math.sin(Math.toRadians(d)))) + ((f2 + f4) * 0.5f);
    }

    public final void addConnectingShadowIfNecessary(float f) {
        float f2 = this.currentShadowAngle;
        if (f2 == f) {
            return;
        }
        float f3 = ((f - f2) + 360.0f) % 360.0f;
        if (f3 > 180.0f) {
            return;
        }
        float f4 = this.endX;
        float f5 = this.endY;
        PathArcOperation pathArcOperation = new PathArcOperation(f4, f5, f4, f5);
        pathArcOperation.startAngle = this.currentShadowAngle;
        pathArcOperation.sweepAngle = f3;
        this.shadowCompatOperations.add(new ArcShadowOperation(pathArcOperation));
        this.currentShadowAngle = f;
    }

    public final void applyToPath(Matrix matrix, Path path) {
        List list = this.operations;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((PathOperation) list.get(i)).applyToPath(matrix, path);
        }
    }

    public final void lineTo(float f, float f2) {
        PathLineOperation pathLineOperation = new PathLineOperation();
        pathLineOperation.f461x = f;
        pathLineOperation.f462y = f2;
        ((ArrayList) this.operations).add(pathLineOperation);
        LineShadowOperation lineShadowOperation = new LineShadowOperation(pathLineOperation, this.endX, this.endY);
        float angle = lineShadowOperation.getAngle() + 270.0f;
        float angle2 = lineShadowOperation.getAngle() + 270.0f;
        addConnectingShadowIfNecessary(angle);
        ((ArrayList) this.shadowCompatOperations).add(lineShadowOperation);
        this.currentShadowAngle = angle2;
        this.endX = f;
        this.endY = f2;
    }

    public final void reset(float f, float f2, float f3, float f4) {
        this.startX = f;
        this.startY = f2;
        this.endX = f;
        this.endY = f2;
        this.currentShadowAngle = f3;
        this.endShadowAngle = (f3 + f4) % 360.0f;
        ((ArrayList) this.operations).clear();
        ((ArrayList) this.shadowCompatOperations).clear();
    }

    public ShapePath(float f, float f2) {
        reset(f, f2, 270.0f, 0.0f);
    }
}
