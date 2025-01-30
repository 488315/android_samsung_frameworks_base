package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapePath;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShapeAppearancePathProvider {
    public final ShapePath[] cornerPaths = new ShapePath[4];
    public final Matrix[] cornerTransforms = new Matrix[4];
    public final Matrix[] edgeTransforms = new Matrix[4];
    public final PointF pointF = new PointF();
    public final Path overlappedEdgePath = new Path();
    public final Path boundsPath = new Path();
    public final ShapePath shapePath = new ShapePath();
    public final float[] scratch = new float[2];
    public final float[] scratch2 = new float[2];
    public final Path edgePath = new Path();
    public final Path cornerPath = new Path();
    public final boolean edgeIntersectionCheckEnabled = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Lazy {
        public static final ShapeAppearancePathProvider INSTANCE = new ShapeAppearancePathProvider();

        private Lazy() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PathListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShapeAppearancePathSpec {
        public final RectF bounds;
        public final float interpolation;
        public final Path path;
        public final PathListener pathListener;
        public final ShapeAppearanceModel shapeAppearanceModel;

        public ShapeAppearancePathSpec(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, PathListener pathListener, Path path) {
            this.pathListener = pathListener;
            this.shapeAppearanceModel = shapeAppearanceModel;
            this.interpolation = f;
            this.bounds = rectF;
            this.path = path;
        }
    }

    public ShapeAppearancePathProvider() {
        for (int i = 0; i < 4; i++) {
            this.cornerPaths[i] = new ShapePath();
            this.cornerTransforms[i] = new Matrix();
            this.edgeTransforms[i] = new Matrix();
        }
    }

    public final void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, MaterialShapeDrawable.C43471 c43471, Path path) {
        int i;
        char c;
        Matrix[] matrixArr;
        float[] fArr;
        Matrix[] matrixArr2;
        ShapePath[] shapePathArr;
        float f2;
        RectF rectF2;
        ShapeAppearanceModel shapeAppearanceModel2;
        ShapeAppearancePathSpec shapeAppearancePathSpec;
        PathListener pathListener;
        Path path2;
        Path path3;
        char c2;
        ShapeAppearancePathProvider shapeAppearancePathProvider = this;
        path.rewind();
        Path path4 = shapeAppearancePathProvider.overlappedEdgePath;
        path4.rewind();
        Path path5 = shapeAppearancePathProvider.boundsPath;
        path5.rewind();
        path5.addRect(rectF, Path.Direction.CW);
        ShapeAppearancePathSpec shapeAppearancePathSpec2 = new ShapeAppearancePathSpec(shapeAppearanceModel, f, rectF, c43471, path);
        int i2 = 0;
        while (true) {
            i = 4;
            c = 1;
            matrixArr = shapeAppearancePathProvider.edgeTransforms;
            fArr = shapeAppearancePathProvider.scratch;
            matrixArr2 = shapeAppearancePathProvider.cornerTransforms;
            shapePathArr = shapeAppearancePathProvider.cornerPaths;
            f2 = shapeAppearancePathSpec2.interpolation;
            rectF2 = shapeAppearancePathSpec2.bounds;
            shapeAppearanceModel2 = shapeAppearancePathSpec2.shapeAppearanceModel;
            if (i2 >= 4) {
                break;
            }
            CornerSize cornerSize = i2 != 1 ? i2 != 2 ? i2 != 3 ? shapeAppearanceModel2.topRightCornerSize : shapeAppearanceModel2.topLeftCornerSize : shapeAppearanceModel2.bottomLeftCornerSize : shapeAppearanceModel2.bottomRightCornerSize;
            CornerTreatment cornerTreatment = i2 != 1 ? i2 != 2 ? i2 != 3 ? shapeAppearanceModel2.topRightCorner : shapeAppearanceModel2.topLeftCorner : shapeAppearanceModel2.bottomLeftCorner : shapeAppearanceModel2.bottomRightCorner;
            ShapePath shapePath = shapePathArr[i2];
            cornerTreatment.getClass();
            cornerTreatment.getCornerPath(f2, cornerSize.getCornerSize(rectF2), shapePath);
            int i3 = i2 + 1;
            float f3 = i3 * 90;
            matrixArr2[i2].reset();
            PointF pointF = shapeAppearancePathProvider.pointF;
            if (i2 == 1) {
                pointF.set(rectF2.right, rectF2.bottom);
            } else if (i2 == 2) {
                pointF.set(rectF2.left, rectF2.bottom);
            } else if (i2 != 3) {
                pointF.set(rectF2.right, rectF2.top);
            } else {
                pointF.set(rectF2.left, rectF2.top);
            }
            matrixArr2[i2].setTranslate(pointF.x, pointF.y);
            matrixArr2[i2].preRotate(f3);
            ShapePath shapePath2 = shapePathArr[i2];
            fArr[0] = shapePath2.endX;
            fArr[1] = shapePath2.endY;
            matrixArr2[i2].mapPoints(fArr);
            matrixArr[i2].reset();
            matrixArr[i2].setTranslate(fArr[0], fArr[1]);
            matrixArr[i2].preRotate(f3);
            i2 = i3;
        }
        char c3 = 0;
        int i4 = 0;
        while (i4 < i) {
            ShapePath shapePath3 = shapePathArr[i4];
            fArr[c3] = shapePath3.startX;
            fArr[c] = shapePath3.startY;
            matrixArr2[i4].mapPoints(fArr);
            Path path6 = shapeAppearancePathSpec2.path;
            if (i4 == 0) {
                path6.moveTo(fArr[c3], fArr[c]);
            } else {
                path6.lineTo(fArr[c3], fArr[c]);
            }
            shapePathArr[i4].applyToPath(matrixArr2[i4], path6);
            PathListener pathListener2 = shapeAppearancePathSpec2.pathListener;
            if (pathListener2 != null) {
                ShapePath shapePath4 = shapePathArr[i4];
                Matrix matrix = matrixArr2[i4];
                shapeAppearancePathSpec = shapeAppearancePathSpec2;
                MaterialShapeDrawable materialShapeDrawable = MaterialShapeDrawable.this;
                pathListener = pathListener2;
                BitSet bitSet = materialShapeDrawable.containsIncompatibleShadowOp;
                shapePath4.getClass();
                path2 = path6;
                bitSet.set(i4, false);
                ShapePath.ShadowCompatOperation[] shadowCompatOperationArr = materialShapeDrawable.cornerShadowOperation;
                shapePath4.addConnectingShadowIfNecessary(shapePath4.endShadowAngle);
                shadowCompatOperationArr[i4] = new ShapePath.ShadowCompatOperation(shapePath4, new ArrayList(shapePath4.shadowCompatOperations), new Matrix(matrix)) { // from class: com.google.android.material.shape.ShapePath.1
                    public final /* synthetic */ List val$operations;
                    public final /* synthetic */ Matrix val$transformCopy;

                    public C43501(ShapePath shapePath42, List list, Matrix matrix2) {
                        this.val$operations = list;
                        this.val$transformCopy = matrix2;
                    }

                    @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
                    public final void draw(Matrix matrix2, ShadowRenderer shadowRenderer, int i5, Canvas canvas) {
                        Iterator it = this.val$operations.iterator();
                        while (it.hasNext()) {
                            ((ShadowCompatOperation) it.next()).draw(this.val$transformCopy, shadowRenderer, i5, canvas);
                        }
                    }
                };
            } else {
                shapeAppearancePathSpec = shapeAppearancePathSpec2;
                pathListener = pathListener2;
                path2 = path6;
            }
            int i5 = i4 + 1;
            int i6 = i5 % 4;
            ShapePath shapePath5 = shapePathArr[i4];
            fArr[0] = shapePath5.endX;
            fArr[1] = shapePath5.endY;
            matrixArr2[i4].mapPoints(fArr);
            ShapePath shapePath6 = shapePathArr[i6];
            float f4 = shapePath6.startX;
            float[] fArr2 = shapeAppearancePathProvider.scratch2;
            fArr2[0] = f4;
            fArr2[1] = shapePath6.startY;
            matrixArr2[i6].mapPoints(fArr2);
            Path path7 = path4;
            Path path8 = path5;
            float max = Math.max(((float) Math.hypot(fArr[0] - fArr2[0], fArr[1] - fArr2[1])) - 0.001f, 0.0f);
            ShapePath shapePath7 = shapePathArr[i4];
            fArr[0] = shapePath7.endX;
            fArr[1] = shapePath7.endY;
            matrixArr2[i4].mapPoints(fArr);
            float abs = (i4 == 1 || i4 == 3) ? Math.abs(rectF2.centerX() - fArr[0]) : Math.abs(rectF2.centerY() - fArr[1]);
            ShapePath shapePath8 = shapeAppearancePathProvider.shapePath;
            shapePath8.reset(0.0f, 0.0f, 270.0f, 0.0f);
            EdgeTreatment edgeTreatment = i4 != 1 ? i4 != 2 ? i4 != 3 ? shapeAppearanceModel2.rightEdge : shapeAppearanceModel2.topEdge : shapeAppearanceModel2.leftEdge : shapeAppearanceModel2.bottomEdge;
            edgeTreatment.getEdgePath(max, abs, f2, shapePath8);
            Path path9 = shapeAppearancePathProvider.edgePath;
            path9.reset();
            shapePath8.applyToPath(matrixArr[i4], path9);
            if (shapeAppearancePathProvider.edgeIntersectionCheckEnabled && (edgeTreatment.forceIntersection() || shapeAppearancePathProvider.pathOverlapsCorner(path9, i4) || shapeAppearancePathProvider.pathOverlapsCorner(path9, i6))) {
                path3 = path8;
                path9.op(path9, path3, Path.Op.DIFFERENCE);
                fArr[0] = shapePath8.startX;
                fArr[1] = shapePath8.startY;
                matrixArr[i4].mapPoints(fArr);
                path7.moveTo(fArr[0], fArr[1]);
                shapePath8.applyToPath(matrixArr[i4], path7);
            } else {
                path3 = path8;
                shapePath8.applyToPath(matrixArr[i4], path2);
            }
            if (pathListener != null) {
                Matrix matrix2 = matrixArr[i4];
                MaterialShapeDrawable materialShapeDrawable2 = MaterialShapeDrawable.this;
                c2 = 0;
                materialShapeDrawable2.containsIncompatibleShadowOp.set(i4 + 4, false);
                ShapePath.ShadowCompatOperation[] shadowCompatOperationArr2 = materialShapeDrawable2.edgeShadowOperation;
                shapePath8.addConnectingShadowIfNecessary(shapePath8.endShadowAngle);
                shadowCompatOperationArr2[i4] = new ShapePath.ShadowCompatOperation(shapePath8, new ArrayList(shapePath8.shadowCompatOperations), new Matrix(matrix2)) { // from class: com.google.android.material.shape.ShapePath.1
                    public final /* synthetic */ List val$operations;
                    public final /* synthetic */ Matrix val$transformCopy;

                    public C43501(ShapePath shapePath82, List list, Matrix matrix22) {
                        this.val$operations = list;
                        this.val$transformCopy = matrix22;
                    }

                    @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
                    public final void draw(Matrix matrix22, ShadowRenderer shadowRenderer, int i52, Canvas canvas) {
                        Iterator it = this.val$operations.iterator();
                        while (it.hasNext()) {
                            ((ShadowCompatOperation) it.next()).draw(this.val$transformCopy, shadowRenderer, i52, canvas);
                        }
                    }
                };
            } else {
                c2 = 0;
            }
            c3 = c2;
            path5 = path3;
            path4 = path7;
            shapeAppearancePathSpec2 = shapeAppearancePathSpec;
            i4 = i5;
            i = 4;
            c = 1;
            shapeAppearancePathProvider = this;
        }
        Path path10 = path4;
        path.close();
        path10.close();
        if (path10.isEmpty()) {
            return;
        }
        path.op(path10, Path.Op.UNION);
    }

    public final boolean pathOverlapsCorner(Path path, int i) {
        Path path2 = this.cornerPath;
        path2.reset();
        this.cornerPaths[i].applyToPath(this.cornerTransforms[i], path2);
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        path2.computeBounds(rectF, true);
        path.op(path2, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        if (rectF.isEmpty()) {
            return rectF.width() > 1.0f && rectF.height() > 1.0f;
        }
        return true;
    }
}
