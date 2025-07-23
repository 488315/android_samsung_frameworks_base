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
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ShapeAppearancePathProvider {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Lazy {
        public static final ShapeAppearancePathProvider INSTANCE = new ShapeAppearancePathProvider();

        private Lazy() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PathListener {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v2 */
    /* JADX WARN: Type inference failed for: r19v3 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.util.BitSet] */
    public final void calculatePath(ShapeAppearanceModel shapeAppearanceModel, float f, RectF rectF, MaterialShapeDrawable.AnonymousClass1 anonymousClass1, Path path) {
        ShapePath[] shapePathArr;
        int i;
        int i2;
        float[] fArr;
        float f2;
        ShapeAppearanceModel shapeAppearanceModel2;
        ?? r19;
        int i3;
        char c;
        char c2;
        EdgeTreatment edgeTreatment;
        boolean z;
        path.rewind();
        this.overlappedEdgePath.rewind();
        this.boundsPath.rewind();
        this.boundsPath.addRect(rectF, Path.Direction.CW);
        ShapeAppearancePathSpec shapeAppearancePathSpec = new ShapeAppearancePathSpec(shapeAppearanceModel, f, rectF, anonymousClass1, path);
        ?? r1 = 0;
        int i4 = 0;
        while (true) {
            shapePathArr = this.cornerPaths;
            i = 1;
            fArr = this.scratch;
            f2 = shapeAppearancePathSpec.interpolation;
            shapeAppearanceModel2 = shapeAppearancePathSpec.shapeAppearanceModel;
            if (i4 >= 4) {
                break;
            }
            CornerSize cornerSize = i4 != 1 ? i4 != 2 ? i4 != 3 ? shapeAppearanceModel2.topRightCornerSize : shapeAppearanceModel2.topLeftCornerSize : shapeAppearanceModel2.bottomLeftCornerSize : shapeAppearanceModel2.bottomRightCornerSize;
            CornerTreatment cornerTreatment = i4 != 1 ? i4 != 2 ? i4 != 3 ? shapeAppearanceModel2.topRightCorner : shapeAppearanceModel2.topLeftCorner : shapeAppearanceModel2.bottomLeftCorner : shapeAppearanceModel2.bottomRightCorner;
            ShapePath shapePath = shapePathArr[i4];
            RectF rectF2 = shapeAppearancePathSpec.bounds;
            cornerTreatment.getClass();
            cornerTreatment.getCornerPath(shapePath, f2, cornerSize.getCornerSize(rectF2));
            int i5 = i4 + 1;
            float f3 = (i5 % 4) * 90;
            this.cornerTransforms[i4].reset();
            RectF rectF3 = shapeAppearancePathSpec.bounds;
            PointF pointF = this.pointF;
            if (i4 == 1) {
                pointF.set(rectF3.right, rectF3.bottom);
            } else if (i4 == 2) {
                pointF.set(rectF3.left, rectF3.bottom);
            } else if (i4 != 3) {
                pointF.set(rectF3.right, rectF3.top);
            } else {
                pointF.set(rectF3.left, rectF3.top);
            }
            Matrix matrix = this.cornerTransforms[i4];
            PointF pointF2 = this.pointF;
            matrix.setTranslate(pointF2.x, pointF2.y);
            this.cornerTransforms[i4].preRotate(f3);
            ShapePath shapePath2 = shapePathArr[i4];
            fArr[0] = shapePath2.endX;
            fArr[1] = shapePath2.endY;
            this.cornerTransforms[i4].mapPoints(fArr);
            this.edgeTransforms[i4].reset();
            this.edgeTransforms[i4].setTranslate(fArr[0], fArr[1]);
            this.edgeTransforms[i4].preRotate(f3);
            i4 = i5;
        }
        int i6 = 0;
        for (i2 = 4; i6 < i2; i2 = 4) {
            ShapePath shapePath3 = shapePathArr[i6];
            fArr[r1] = shapePath3.startX;
            fArr[i] = shapePath3.startY;
            this.cornerTransforms[i6].mapPoints(fArr);
            if (i6 == 0) {
                shapeAppearancePathSpec.path.moveTo(fArr[r1], fArr[i]);
            } else {
                shapeAppearancePathSpec.path.lineTo(fArr[r1], fArr[i]);
            }
            shapePathArr[i6].applyToPath(this.cornerTransforms[i6], shapeAppearancePathSpec.path);
            PathListener pathListener = shapeAppearancePathSpec.pathListener;
            if (pathListener != null) {
                ShapePath shapePath4 = shapePathArr[i6];
                Matrix matrix2 = this.cornerTransforms[i6];
                MaterialShapeDrawable materialShapeDrawable = MaterialShapeDrawable.this;
                ?? r6 = materialShapeDrawable.containsIncompatibleShadowOp;
                shapePath4.getClass();
                r6.set(i6, r1);
                ShapePath.ShadowCompatOperation[] shadowCompatOperationArr = materialShapeDrawable.cornerShadowOperation;
                shapePath4.addConnectingShadowIfNecessary(shapePath4.endShadowAngle);
                r19 = r1;
                shadowCompatOperationArr[i6] = new ShapePath.ShadowCompatOperation(shapePath4, new ArrayList(shapePath4.shadowCompatOperations), new Matrix(matrix2)) { // from class: com.google.android.material.shape.ShapePath.1
                    public final /* synthetic */ List val$operations;
                    public final /* synthetic */ Matrix val$transformCopy;

                    public AnonymousClass1(ShapePath shapePath42, List list, Matrix matrix3) {
                        this.val$operations = list;
                        this.val$transformCopy = matrix3;
                    }

                    @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
                    public final void draw(Matrix matrix3, ShadowRenderer shadowRenderer, int i7, Canvas canvas) {
                        Iterator it = this.val$operations.iterator();
                        while (it.hasNext()) {
                            ((ShadowCompatOperation) it.next()).draw(this.val$transformCopy, shadowRenderer, i7, canvas);
                        }
                    }
                };
            } else {
                r19 = r1;
            }
            int i7 = i6 + 1;
            int i8 = i7 % 4;
            ShapePath shapePath5 = shapePathArr[i6];
            fArr[r19] = shapePath5.endX;
            fArr[i] = shapePath5.endY;
            this.cornerTransforms[i6].mapPoints(fArr);
            ShapePath shapePath6 = shapePathArr[i8];
            float f4 = shapePath6.startX;
            float[] fArr2 = this.scratch2;
            fArr2[r19] = f4;
            fArr2[i] = shapePath6.startY;
            this.cornerTransforms[i8].mapPoints(fArr2);
            float max = Math.max(((float) Math.hypot(fArr[r19] - fArr2[r19], fArr[i] - fArr2[i])) - 0.001f, 0.0f);
            RectF rectF4 = shapeAppearancePathSpec.bounds;
            ShapePath shapePath7 = shapePathArr[i6];
            fArr[r19] = shapePath7.endX;
            fArr[i] = shapePath7.endY;
            this.cornerTransforms[i6].mapPoints(fArr);
            float abs = (i6 == i || i6 == 3) ? Math.abs(rectF4.centerX() - fArr[r19]) : Math.abs(rectF4.centerY() - fArr[i]);
            ShapePath shapePath8 = this.shapePath;
            shapePath8.reset(0.0f, 0.0f, 270.0f, 0.0f);
            if (i6 != i) {
                c = 2;
                if (i6 != 2) {
                    c2 = 3;
                    if (i6 != 3) {
                        i3 = i;
                        edgeTreatment = shapeAppearanceModel2.rightEdge;
                    } else {
                        i3 = i;
                        edgeTreatment = shapeAppearanceModel2.topEdge;
                    }
                } else {
                    i3 = i;
                    c2 = 3;
                    edgeTreatment = shapeAppearanceModel2.leftEdge;
                }
            } else {
                i3 = i;
                c = 2;
                c2 = 3;
                edgeTreatment = shapeAppearanceModel2.bottomEdge;
            }
            edgeTreatment.getEdgePath(max, abs, f2, shapePath8);
            this.edgePath.reset();
            shapePath8.applyToPath(this.edgeTransforms[i6], this.edgePath);
            if (this.edgeIntersectionCheckEnabled && (edgeTreatment.forceIntersection() || pathOverlapsCorner(this.edgePath, i6) || pathOverlapsCorner(this.edgePath, i8))) {
                Path path2 = this.edgePath;
                path2.op(path2, this.boundsPath, Path.Op.DIFFERENCE);
                fArr[r19] = shapePath8.startX;
                fArr[i3] = shapePath8.startY;
                this.edgeTransforms[i6].mapPoints(fArr);
                this.overlappedEdgePath.moveTo(fArr[r19], fArr[i3]);
                shapePath8.applyToPath(this.edgeTransforms[i6], this.overlappedEdgePath);
            } else {
                shapePath8.applyToPath(this.edgeTransforms[i6], shapeAppearancePathSpec.path);
            }
            if (pathListener != null) {
                Matrix matrix3 = this.edgeTransforms[i6];
                MaterialShapeDrawable materialShapeDrawable2 = MaterialShapeDrawable.this;
                z = r19;
                materialShapeDrawable2.containsIncompatibleShadowOp.set(i6 + 4, z);
                ShapePath.ShadowCompatOperation[] shadowCompatOperationArr2 = materialShapeDrawable2.edgeShadowOperation;
                shapePath8.addConnectingShadowIfNecessary(shapePath8.endShadowAngle);
                shadowCompatOperationArr2[i6] = new ShapePath.ShadowCompatOperation(shapePath8, new ArrayList(shapePath8.shadowCompatOperations), new Matrix(matrix3)) { // from class: com.google.android.material.shape.ShapePath.1
                    public final /* synthetic */ List val$operations;
                    public final /* synthetic */ Matrix val$transformCopy;

                    public AnonymousClass1(ShapePath shapePath82, List list, Matrix matrix32) {
                        this.val$operations = list;
                        this.val$transformCopy = matrix32;
                    }

                    @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
                    public final void draw(Matrix matrix32, ShadowRenderer shadowRenderer, int i72, Canvas canvas) {
                        Iterator it = this.val$operations.iterator();
                        while (it.hasNext()) {
                            ((ShadowCompatOperation) it.next()).draw(this.val$transformCopy, shadowRenderer, i72, canvas);
                        }
                    }
                };
            } else {
                z = r19;
            }
            i = i3;
            i6 = i7;
            r1 = z;
        }
        path.close();
        this.overlappedEdgePath.close();
        if (this.overlappedEdgePath.isEmpty()) {
            return;
        }
        path.op(this.overlappedEdgePath, Path.Op.UNION);
    }

    public final boolean pathOverlapsCorner(Path path, int i) {
        this.cornerPath.reset();
        this.cornerPaths[i].applyToPath(this.cornerTransforms[i], this.cornerPath);
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        this.cornerPath.computeBounds(rectF, true);
        path.op(this.cornerPath, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        return !rectF.isEmpty() || (rectF.width() > 1.0f && rectF.height() > 1.0f);
    }
}
