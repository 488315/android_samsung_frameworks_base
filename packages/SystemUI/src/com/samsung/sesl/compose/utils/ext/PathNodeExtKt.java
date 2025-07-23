package com.samsung.sesl.compose.utils.ext;

import android.graphics.Matrix;
import androidx.compose.ui.graphics.vector.PathNode;
import com.samsung.sesl.compose.utils.MathHelperKt;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class PathNodeExtKt {
    public static final PathNode transform(PathNode pathNode, Matrix matrix) {
        if (pathNode instanceof PathNode.Close) {
            return pathNode;
        }
        if (pathNode instanceof PathNode.RelativeMoveTo) {
            PathNode.RelativeMoveTo relativeMoveTo = (PathNode.RelativeMoveTo) pathNode;
            float[] fArr = {relativeMoveTo.dx, relativeMoveTo.dy};
            matrix.mapPoints(fArr);
            return new PathNode.RelativeMoveTo(fArr[0], fArr[1]);
        }
        if (pathNode instanceof PathNode.MoveTo) {
            PathNode.MoveTo moveTo = (PathNode.MoveTo) pathNode;
            float[] fArr2 = {moveTo.x, moveTo.y};
            matrix.mapPoints(fArr2);
            return new PathNode.RelativeMoveTo(fArr2[0], fArr2[1]);
        }
        if (pathNode instanceof PathNode.RelativeLineTo) {
            PathNode.RelativeLineTo relativeLineTo = (PathNode.RelativeLineTo) pathNode;
            float[] fArr3 = {relativeLineTo.dx, relativeLineTo.dy};
            matrix.mapPoints(fArr3);
            return new PathNode.RelativeMoveTo(fArr3[0], fArr3[1]);
        }
        if (pathNode instanceof PathNode.LineTo) {
            PathNode.LineTo lineTo = (PathNode.LineTo) pathNode;
            float[] fArr4 = {lineTo.x, lineTo.y};
            matrix.mapPoints(fArr4);
            return new PathNode.LineTo(fArr4[0], fArr4[1]);
        }
        if (pathNode instanceof PathNode.RelativeHorizontalTo) {
            float[] fArr5 = {((PathNode.RelativeHorizontalTo) pathNode).dx, 0.0f};
            matrix.mapPoints(fArr5);
            return new PathNode.RelativeLineTo(fArr5[0], fArr5[1]);
        }
        if (pathNode instanceof PathNode.HorizontalTo) {
            float[] fArr6 = {((PathNode.HorizontalTo) pathNode).x, 0.0f};
            matrix.mapPoints(fArr6);
            return new PathNode.LineTo(fArr6[0], fArr6[1]);
        }
        if (pathNode instanceof PathNode.RelativeVerticalTo) {
            float[] fArr7 = {0.0f, ((PathNode.RelativeVerticalTo) pathNode).dy};
            matrix.mapPoints(fArr7);
            return new PathNode.RelativeLineTo(fArr7[0], fArr7[1]);
        }
        if (pathNode instanceof PathNode.VerticalTo) {
            float[] fArr8 = {0.0f, ((PathNode.VerticalTo) pathNode).y};
            matrix.mapPoints(fArr8);
            return new PathNode.LineTo(fArr8[0], fArr8[1]);
        }
        if (pathNode instanceof PathNode.RelativeCurveTo) {
            PathNode.RelativeCurveTo relativeCurveTo = (PathNode.RelativeCurveTo) pathNode;
            float[] fArr9 = {relativeCurveTo.dx1, relativeCurveTo.dy1, relativeCurveTo.dx2, relativeCurveTo.dy2, relativeCurveTo.dx3, relativeCurveTo.dy3};
            matrix.mapPoints(fArr9);
            return new PathNode.RelativeCurveTo(fArr9[0], fArr9[1], fArr9[2], fArr9[3], fArr9[4], fArr9[5]);
        }
        if (pathNode instanceof PathNode.CurveTo) {
            PathNode.CurveTo curveTo = (PathNode.CurveTo) pathNode;
            float[] fArr10 = {curveTo.x1, curveTo.y1, curveTo.x2, curveTo.y2, curveTo.x3, curveTo.y3};
            matrix.mapPoints(fArr10);
            return new PathNode.CurveTo(fArr10[0], fArr10[1], fArr10[2], fArr10[3], fArr10[4], fArr10[5]);
        }
        if (pathNode instanceof PathNode.RelativeReflectiveCurveTo) {
            PathNode.RelativeReflectiveCurveTo relativeReflectiveCurveTo = (PathNode.RelativeReflectiveCurveTo) pathNode;
            float[] fArr11 = {relativeReflectiveCurveTo.dx1, relativeReflectiveCurveTo.dy1, relativeReflectiveCurveTo.dx2, relativeReflectiveCurveTo.dy2};
            matrix.mapPoints(fArr11);
            return new PathNode.RelativeReflectiveCurveTo(fArr11[0], fArr11[1], fArr11[2], fArr11[3]);
        }
        if (pathNode instanceof PathNode.ReflectiveCurveTo) {
            PathNode.ReflectiveCurveTo reflectiveCurveTo = (PathNode.ReflectiveCurveTo) pathNode;
            float[] fArr12 = {reflectiveCurveTo.x1, reflectiveCurveTo.y1, reflectiveCurveTo.x2, reflectiveCurveTo.y2};
            matrix.mapPoints(fArr12);
            return new PathNode.ReflectiveCurveTo(fArr12[0], fArr12[1], fArr12[2], fArr12[3]);
        }
        if (pathNode instanceof PathNode.RelativeQuadTo) {
            PathNode.RelativeQuadTo relativeQuadTo = (PathNode.RelativeQuadTo) pathNode;
            float[] fArr13 = {relativeQuadTo.dx1, relativeQuadTo.dy1, relativeQuadTo.dx2, relativeQuadTo.dy2};
            matrix.mapPoints(fArr13);
            return new PathNode.RelativeQuadTo(fArr13[0], fArr13[1], fArr13[2], fArr13[3]);
        }
        if (pathNode instanceof PathNode.QuadTo) {
            PathNode.QuadTo quadTo = (PathNode.QuadTo) pathNode;
            float[] fArr14 = {quadTo.x1, quadTo.y1, quadTo.x2, quadTo.y2};
            matrix.mapPoints(fArr14);
            return new PathNode.QuadTo(fArr14[0], fArr14[1], fArr14[2], fArr14[3]);
        }
        if (pathNode instanceof PathNode.ReflectiveQuadTo) {
            PathNode.ReflectiveQuadTo reflectiveQuadTo = (PathNode.ReflectiveQuadTo) pathNode;
            float[] fArr15 = {reflectiveQuadTo.x, reflectiveQuadTo.y};
            matrix.mapPoints(fArr15);
            return new PathNode.ReflectiveQuadTo(fArr15[0], fArr15[1]);
        }
        if (pathNode instanceof PathNode.RelativeReflectiveQuadTo) {
            PathNode.RelativeReflectiveQuadTo relativeReflectiveQuadTo = (PathNode.RelativeReflectiveQuadTo) pathNode;
            float[] fArr16 = {relativeReflectiveQuadTo.dx, relativeReflectiveQuadTo.dy};
            matrix.mapPoints(fArr16);
            return new PathNode.RelativeReflectiveQuadTo(fArr16[0], fArr16[1]);
        }
        if (pathNode instanceof PathNode.RelativeArcTo) {
            PathNode.RelativeArcTo relativeArcTo = (PathNode.RelativeArcTo) pathNode;
            float[] fArr17 = {relativeArcTo.arcStartDx, relativeArcTo.arcStartDy};
            matrix.mapPoints(fArr17);
            return new PathNode.RelativeArcTo(relativeArcTo.horizontalEllipseRadius, relativeArcTo.verticalEllipseRadius, relativeArcTo.theta, relativeArcTo.isMoreThanHalf, relativeArcTo.isPositiveArc, fArr17[0], fArr17[1]);
        }
        if (!(pathNode instanceof PathNode.ArcTo)) {
            throw new NoWhenBranchMatchedException();
        }
        PathNode.ArcTo arcTo = (PathNode.ArcTo) pathNode;
        float[] fArr18 = {arcTo.arcStartX, arcTo.arcStartY};
        matrix.mapPoints(fArr18);
        return new PathNode.ArcTo(arcTo.horizontalEllipseRadius, arcTo.verticalEllipseRadius, arcTo.theta, arcTo.isMoreThanHalf, arcTo.isPositiveArc, fArr18[0], fArr18[1]);
    }

    public static final PathNode transition(PathNode pathNode, PathNode pathNode2, float f) {
        pathNode.getClass();
        pathNode2.getClass();
        if ((pathNode instanceof PathNode.Close) && (pathNode2 instanceof PathNode.Close)) {
            return PathNode.Close.INSTANCE;
        }
        if ((pathNode instanceof PathNode.RelativeMoveTo) && (pathNode2 instanceof PathNode.RelativeMoveTo)) {
            PathNode.RelativeMoveTo relativeMoveTo = (PathNode.RelativeMoveTo) pathNode;
            PathNode.RelativeMoveTo relativeMoveTo2 = (PathNode.RelativeMoveTo) pathNode2;
            return new PathNode.RelativeMoveTo(MathHelperKt.lerp(relativeMoveTo.dx, relativeMoveTo2.dx, f), MathHelperKt.lerp(relativeMoveTo.dy, relativeMoveTo2.dy, f));
        }
        if ((pathNode instanceof PathNode.MoveTo) && (pathNode2 instanceof PathNode.MoveTo)) {
            PathNode.MoveTo moveTo = (PathNode.MoveTo) pathNode;
            PathNode.MoveTo moveTo2 = (PathNode.MoveTo) pathNode2;
            return new PathNode.MoveTo(MathHelperKt.lerp(moveTo.x, moveTo2.x, f), MathHelperKt.lerp(moveTo.y, moveTo2.y, f));
        }
        if ((pathNode instanceof PathNode.RelativeLineTo) && (pathNode2 instanceof PathNode.RelativeLineTo)) {
            PathNode.RelativeLineTo relativeLineTo = (PathNode.RelativeLineTo) pathNode;
            PathNode.RelativeLineTo relativeLineTo2 = (PathNode.RelativeLineTo) pathNode2;
            return new PathNode.RelativeLineTo(MathHelperKt.lerp(relativeLineTo.dx, relativeLineTo2.dx, f), MathHelperKt.lerp(relativeLineTo.dy, relativeLineTo2.dy, f));
        }
        if ((pathNode instanceof PathNode.LineTo) && (pathNode2 instanceof PathNode.LineTo)) {
            PathNode.LineTo lineTo = (PathNode.LineTo) pathNode;
            PathNode.LineTo lineTo2 = (PathNode.LineTo) pathNode2;
            return new PathNode.LineTo(MathHelperKt.lerp(lineTo.x, lineTo2.x, f), MathHelperKt.lerp(lineTo.y, lineTo2.y, f));
        }
        if ((pathNode instanceof PathNode.RelativeHorizontalTo) && (pathNode2 instanceof PathNode.RelativeHorizontalTo)) {
            return new PathNode.RelativeHorizontalTo(MathHelperKt.lerp(((PathNode.RelativeHorizontalTo) pathNode).dx, ((PathNode.RelativeHorizontalTo) pathNode2).dx, f));
        }
        if ((pathNode instanceof PathNode.HorizontalTo) && (pathNode2 instanceof PathNode.HorizontalTo)) {
            return new PathNode.RelativeHorizontalTo(MathHelperKt.lerp(((PathNode.HorizontalTo) pathNode).x, ((PathNode.HorizontalTo) pathNode2).x, f));
        }
        if ((pathNode instanceof PathNode.RelativeVerticalTo) && (pathNode2 instanceof PathNode.RelativeVerticalTo)) {
            return new PathNode.RelativeVerticalTo(MathHelperKt.lerp(((PathNode.RelativeVerticalTo) pathNode).dy, ((PathNode.RelativeVerticalTo) pathNode2).dy, f));
        }
        if ((pathNode instanceof PathNode.VerticalTo) && (pathNode2 instanceof PathNode.VerticalTo)) {
            return new PathNode.VerticalTo(MathHelperKt.lerp(((PathNode.VerticalTo) pathNode).y, ((PathNode.VerticalTo) pathNode2).y, f));
        }
        if ((pathNode instanceof PathNode.RelativeCurveTo) && (pathNode2 instanceof PathNode.RelativeCurveTo)) {
            PathNode.RelativeCurveTo relativeCurveTo = (PathNode.RelativeCurveTo) pathNode;
            PathNode.RelativeCurveTo relativeCurveTo2 = (PathNode.RelativeCurveTo) pathNode2;
            return new PathNode.RelativeCurveTo(MathHelperKt.lerp(relativeCurveTo.dx1, relativeCurveTo2.dx1, f), MathHelperKt.lerp(relativeCurveTo.dy1, relativeCurveTo2.dy1, f), MathHelperKt.lerp(relativeCurveTo.dx2, relativeCurveTo2.dx2, f), MathHelperKt.lerp(relativeCurveTo.dy2, relativeCurveTo2.dy2, f), MathHelperKt.lerp(relativeCurveTo.dx3, relativeCurveTo2.dx3, f), MathHelperKt.lerp(relativeCurveTo.dy3, relativeCurveTo2.dy3, f));
        }
        if ((pathNode instanceof PathNode.CurveTo) && (pathNode2 instanceof PathNode.CurveTo)) {
            PathNode.CurveTo curveTo = (PathNode.CurveTo) pathNode;
            PathNode.CurveTo curveTo2 = (PathNode.CurveTo) pathNode2;
            return new PathNode.CurveTo(MathHelperKt.lerp(curveTo.x1, curveTo2.x1, f), MathHelperKt.lerp(curveTo.y1, curveTo2.y1, f), MathHelperKt.lerp(curveTo.x2, curveTo2.x2, f), MathHelperKt.lerp(curveTo.y2, curveTo2.y2, f), MathHelperKt.lerp(curveTo.x3, curveTo2.x3, f), MathHelperKt.lerp(curveTo.y3, curveTo2.y3, f));
        }
        if ((pathNode instanceof PathNode.RelativeReflectiveCurveTo) && (pathNode2 instanceof PathNode.RelativeReflectiveCurveTo)) {
            PathNode.RelativeReflectiveCurveTo relativeReflectiveCurveTo = (PathNode.RelativeReflectiveCurveTo) pathNode;
            PathNode.RelativeReflectiveCurveTo relativeReflectiveCurveTo2 = (PathNode.RelativeReflectiveCurveTo) pathNode2;
            return new PathNode.RelativeReflectiveCurveTo(MathHelperKt.lerp(relativeReflectiveCurveTo.dx1, relativeReflectiveCurveTo2.dx1, f), MathHelperKt.lerp(relativeReflectiveCurveTo.dy1, relativeReflectiveCurveTo2.dy1, f), MathHelperKt.lerp(relativeReflectiveCurveTo.dx2, relativeReflectiveCurveTo2.dx2, f), MathHelperKt.lerp(relativeReflectiveCurveTo.dy2, relativeReflectiveCurveTo2.dy2, f));
        }
        if ((pathNode instanceof PathNode.ReflectiveCurveTo) && (pathNode2 instanceof PathNode.ReflectiveCurveTo)) {
            PathNode.ReflectiveCurveTo reflectiveCurveTo = (PathNode.ReflectiveCurveTo) pathNode;
            PathNode.ReflectiveCurveTo reflectiveCurveTo2 = (PathNode.ReflectiveCurveTo) pathNode2;
            return new PathNode.ReflectiveCurveTo(MathHelperKt.lerp(reflectiveCurveTo.x1, reflectiveCurveTo2.x1, f), MathHelperKt.lerp(reflectiveCurveTo.y1, reflectiveCurveTo2.y1, f), MathHelperKt.lerp(reflectiveCurveTo.x2, reflectiveCurveTo2.x2, f), MathHelperKt.lerp(reflectiveCurveTo.y2, reflectiveCurveTo2.y2, f));
        }
        if ((pathNode instanceof PathNode.RelativeQuadTo) && (pathNode2 instanceof PathNode.RelativeQuadTo)) {
            PathNode.RelativeQuadTo relativeQuadTo = (PathNode.RelativeQuadTo) pathNode;
            PathNode.RelativeQuadTo relativeQuadTo2 = (PathNode.RelativeQuadTo) pathNode2;
            return new PathNode.RelativeQuadTo(MathHelperKt.lerp(relativeQuadTo.dx1, relativeQuadTo2.dx1, f), MathHelperKt.lerp(relativeQuadTo.dy1, relativeQuadTo2.dy1, f), MathHelperKt.lerp(relativeQuadTo.dx2, relativeQuadTo2.dx2, f), MathHelperKt.lerp(relativeQuadTo.dy2, relativeQuadTo2.dy2, f));
        }
        if ((pathNode instanceof PathNode.QuadTo) && (pathNode2 instanceof PathNode.QuadTo)) {
            PathNode.QuadTo quadTo = (PathNode.QuadTo) pathNode;
            PathNode.QuadTo quadTo2 = (PathNode.QuadTo) pathNode2;
            return new PathNode.QuadTo(MathHelperKt.lerp(quadTo.x1, quadTo2.x1, f), MathHelperKt.lerp(quadTo.y1, quadTo2.y1, f), MathHelperKt.lerp(quadTo.x2, quadTo2.x2, f), MathHelperKt.lerp(quadTo.y2, quadTo2.y2, f));
        }
        if ((pathNode instanceof PathNode.RelativeReflectiveQuadTo) && (pathNode2 instanceof PathNode.RelativeReflectiveQuadTo)) {
            PathNode.RelativeReflectiveQuadTo relativeReflectiveQuadTo = (PathNode.RelativeReflectiveQuadTo) pathNode;
            PathNode.RelativeReflectiveQuadTo relativeReflectiveQuadTo2 = (PathNode.RelativeReflectiveQuadTo) pathNode2;
            return new PathNode.RelativeReflectiveQuadTo(MathHelperKt.lerp(relativeReflectiveQuadTo.dx, relativeReflectiveQuadTo2.dx, f), MathHelperKt.lerp(relativeReflectiveQuadTo.dy, relativeReflectiveQuadTo2.dy, f));
        }
        if ((pathNode instanceof PathNode.ReflectiveQuadTo) && (pathNode2 instanceof PathNode.ReflectiveQuadTo)) {
            PathNode.ReflectiveQuadTo reflectiveQuadTo = (PathNode.ReflectiveQuadTo) pathNode;
            PathNode.ReflectiveQuadTo reflectiveQuadTo2 = (PathNode.ReflectiveQuadTo) pathNode2;
            return new PathNode.ReflectiveQuadTo(MathHelperKt.lerp(reflectiveQuadTo.x, reflectiveQuadTo2.x, f), MathHelperKt.lerp(reflectiveQuadTo.y, reflectiveQuadTo2.y, f));
        }
        if ((pathNode instanceof PathNode.RelativeArcTo) && (pathNode2 instanceof PathNode.RelativeArcTo)) {
            PathNode.RelativeArcTo relativeArcTo = (PathNode.RelativeArcTo) pathNode;
            PathNode.RelativeArcTo relativeArcTo2 = (PathNode.RelativeArcTo) pathNode2;
            double d = f;
            return new PathNode.RelativeArcTo(MathHelperKt.lerp(relativeArcTo.horizontalEllipseRadius, relativeArcTo2.horizontalEllipseRadius, f), MathHelperKt.lerp(relativeArcTo.verticalEllipseRadius, relativeArcTo2.verticalEllipseRadius, f), MathHelperKt.lerp(relativeArcTo.theta, relativeArcTo2.theta, f), d < 0.5d ? relativeArcTo.isMoreThanHalf : relativeArcTo2.isMoreThanHalf, d < 0.5d ? relativeArcTo.isPositiveArc : relativeArcTo2.isPositiveArc, MathHelperKt.lerp(relativeArcTo.arcStartDx, relativeArcTo2.arcStartDx, f), MathHelperKt.lerp(relativeArcTo.arcStartDy, relativeArcTo2.arcStartDy, f));
        }
        if ((pathNode instanceof PathNode.ArcTo) && (pathNode2 instanceof PathNode.ArcTo)) {
            PathNode.ArcTo arcTo = (PathNode.ArcTo) pathNode;
            PathNode.ArcTo arcTo2 = (PathNode.ArcTo) pathNode2;
            double d2 = f;
            return new PathNode.RelativeArcTo(MathHelperKt.lerp(arcTo.horizontalEllipseRadius, arcTo2.horizontalEllipseRadius, f), MathHelperKt.lerp(arcTo.verticalEllipseRadius, arcTo2.verticalEllipseRadius, f), MathHelperKt.lerp(arcTo.theta, arcTo2.theta, f), d2 < 0.5d ? arcTo.isMoreThanHalf : arcTo2.isMoreThanHalf, d2 < 0.5d ? arcTo.isPositiveArc : arcTo2.isPositiveArc, MathHelperKt.lerp(arcTo.arcStartX, arcTo2.arcStartX, f), MathHelperKt.lerp(arcTo.arcStartY, arcTo2.arcStartY, f));
        }
        throw new UnsupportedOperationException("pathNodeType is different between " + pathNode + " and " + pathNode2);
    }
}
