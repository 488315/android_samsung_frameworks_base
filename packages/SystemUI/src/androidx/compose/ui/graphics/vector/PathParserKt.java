package androidx.compose.ui.graphics.vector;

import android.graphics.Path;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.vector.PathNode;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PathParserKt {
    public static final void drawArc(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, boolean z, boolean z2) {
        double d8;
        double d9;
        double d10 = d5;
        double d11 = (d7 / 180) * 3.141592653589793d;
        double cos = Math.cos(d11);
        double sin = Math.sin(d11);
        double d12 = ((d2 * sin) + (d * cos)) / d10;
        double d13 = ((d2 * cos) + ((-d) * sin)) / d6;
        double d14 = ((d4 * sin) + (d3 * cos)) / d10;
        double d15 = ((d4 * cos) + ((-d3) * sin)) / d6;
        double d16 = d12 - d14;
        double d17 = d13 - d15;
        double d18 = 2;
        double d19 = (d12 + d14) / d18;
        double d20 = (d13 + d15) / d18;
        double d21 = (d17 * d17) + (d16 * d16);
        if (d21 == 0.0d) {
            return;
        }
        double d22 = (1.0d / d21) - 0.25d;
        if (d22 < 0.0d) {
            double sqrt = (float) (Math.sqrt(d21) / 1.99999d);
            drawArc(path, d, d2, d3, d4, d10 * sqrt, d6 * sqrt, d7, z, z2);
            return;
        }
        double sqrt2 = Math.sqrt(d22);
        double d23 = d16 * sqrt2;
        double d24 = sqrt2 * d17;
        if (z == z2) {
            d8 = d19 - d24;
            d9 = d20 + d23;
        } else {
            d8 = d19 + d24;
            d9 = d20 - d23;
        }
        double atan2 = Math.atan2(d13 - d9, d12 - d8);
        double atan22 = Math.atan2(d15 - d9, d14 - d8) - atan2;
        if (z2 != (atan22 >= 0.0d)) {
            atan22 = atan22 > 0.0d ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
        }
        double d25 = d8 * d10;
        double d26 = d9 * d6;
        double d27 = (d25 * cos) - (d26 * sin);
        double d28 = (d26 * cos) + (d25 * sin);
        double d29 = 4;
        int ceil = (int) Math.ceil(Math.abs((atan22 * d29) / 3.141592653589793d));
        double cos2 = Math.cos(d11);
        double sin2 = Math.sin(d11);
        double cos3 = Math.cos(atan2);
        double sin3 = Math.sin(atan2);
        double d30 = atan22;
        double d31 = -d10;
        double d32 = d31 * cos2;
        double d33 = d6 * sin2;
        double d34 = (d32 * sin3) - (d33 * cos3);
        double d35 = d31 * sin2;
        double d36 = d6 * cos2;
        double d37 = (cos3 * d36) + (sin3 * d35);
        double d38 = d30 / ceil;
        int i = 0;
        double d39 = d34;
        double d40 = d37;
        double d41 = d2;
        double d42 = atan2;
        double d43 = d;
        while (i < ceil) {
            double d44 = d42 + d38;
            double sin4 = Math.sin(d44);
            double cos4 = Math.cos(d44);
            int i2 = i;
            double d45 = (((d10 * cos2) * cos4) + d27) - (d33 * sin4);
            int i3 = ceil;
            double d46 = (d36 * sin4) + (d10 * sin2 * cos4) + d28;
            double d47 = (d32 * sin4) - (d33 * cos4);
            double d48 = (cos4 * d36) + (sin4 * d35);
            double d49 = d44 - d42;
            double tan = Math.tan(d49 / d18);
            double sqrt3 = ((Math.sqrt(((3.0d * tan) * tan) + d29) - 1) * Math.sin(d49)) / 3;
            ((AndroidPath) path).internalPath.cubicTo((float) ((d39 * sqrt3) + d43), (float) ((d40 * sqrt3) + d41), (float) (d45 - (sqrt3 * d47)), (float) (d46 - (sqrt3 * d48)), (float) d45, (float) d46);
            sin2 = sin2;
            d43 = d45;
            i = i2 + 1;
            d27 = d27;
            d29 = d29;
            d42 = d44;
            d40 = d48;
            d39 = d47;
            d41 = d46;
            ceil = i3;
            d10 = d5;
        }
    }

    public static final AndroidPath toPath(List list, Path path) {
        int i;
        int i2;
        int i3;
        float f;
        PathNode pathNode;
        PathNode pathNode2;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        List list2 = list;
        AndroidPath androidPath = (AndroidPath) path;
        int i4 = 0;
        if (androidPath.internalPath.getFillType() == Path.FillType.EVEN_ODD) {
            PathFillType.Companion.getClass();
            i = PathFillType.EvenOdd;
        } else {
            PathFillType.Companion.getClass();
            i = 0;
        }
        androidPath.internalPath.rewind();
        androidPath.m444setFillTypeoQ8Xj4U(i);
        PathNode pathNode3 = list2.isEmpty() ? PathNode.Close.INSTANCE : (PathNode) list2.get(0);
        int size = list2.size();
        float f12 = 0.0f;
        float f13 = 0.0f;
        float f14 = 0.0f;
        float f15 = 0.0f;
        float f16 = 0.0f;
        float f17 = 0.0f;
        float f18 = 0.0f;
        while (i4 < size) {
            PathNode pathNode4 = (PathNode) list2.get(i4);
            if (pathNode4 instanceof PathNode.Close) {
                androidPath.internalPath.close();
                i2 = size;
                i3 = i4;
                f = f12;
                pathNode2 = pathNode4;
                f13 = f17;
                f15 = f13;
                f14 = f18;
            } else {
                if (pathNode4 instanceof PathNode.RelativeMoveTo) {
                    PathNode.RelativeMoveTo relativeMoveTo = (PathNode.RelativeMoveTo) pathNode4;
                    float f19 = relativeMoveTo.dx;
                    f13 += f19;
                    float f20 = relativeMoveTo.dy;
                    f14 += f20;
                    androidPath.internalPath.rMoveTo(f19, f20);
                    i2 = size;
                    i3 = i4;
                    f = f12;
                    f17 = f13;
                    f18 = f14;
                } else if (pathNode4 instanceof PathNode.MoveTo) {
                    PathNode.MoveTo moveTo = (PathNode.MoveTo) pathNode4;
                    f13 = moveTo.x;
                    android.graphics.Path path2 = androidPath.internalPath;
                    float f21 = moveTo.y;
                    path2.moveTo(f13, f21);
                    f14 = f21;
                    f18 = f14;
                    i2 = size;
                    i3 = i4;
                    f = f12;
                    f17 = f13;
                } else {
                    if (pathNode4 instanceof PathNode.RelativeLineTo) {
                        PathNode.RelativeLineTo relativeLineTo = (PathNode.RelativeLineTo) pathNode4;
                        float f22 = relativeLineTo.dx;
                        android.graphics.Path path3 = androidPath.internalPath;
                        float f23 = relativeLineTo.dy;
                        path3.rLineTo(f22, f23);
                        f13 += relativeLineTo.dx;
                        f14 += f23;
                    } else {
                        if (pathNode4 instanceof PathNode.LineTo) {
                            PathNode.LineTo lineTo = (PathNode.LineTo) pathNode4;
                            float f24 = lineTo.x;
                            android.graphics.Path path4 = androidPath.internalPath;
                            float f25 = lineTo.y;
                            path4.lineTo(f24, f25);
                            f13 = lineTo.x;
                            i2 = size;
                            i3 = i4;
                            f = f12;
                            pathNode2 = pathNode4;
                            f14 = f25;
                        } else if (pathNode4 instanceof PathNode.RelativeHorizontalTo) {
                            PathNode.RelativeHorizontalTo relativeHorizontalTo = (PathNode.RelativeHorizontalTo) pathNode4;
                            androidPath.internalPath.rLineTo(relativeHorizontalTo.dx, f12);
                            f13 += relativeHorizontalTo.dx;
                        } else if (pathNode4 instanceof PathNode.HorizontalTo) {
                            PathNode.HorizontalTo horizontalTo = (PathNode.HorizontalTo) pathNode4;
                            androidPath.internalPath.lineTo(horizontalTo.x, f14);
                            f13 = horizontalTo.x;
                        } else {
                            if (pathNode4 instanceof PathNode.RelativeVerticalTo) {
                                PathNode.RelativeVerticalTo relativeVerticalTo = (PathNode.RelativeVerticalTo) pathNode4;
                                androidPath.internalPath.rLineTo(f12, relativeVerticalTo.dy);
                                f11 = relativeVerticalTo.dy;
                            } else if (pathNode4 instanceof PathNode.VerticalTo) {
                                PathNode.VerticalTo verticalTo = (PathNode.VerticalTo) pathNode4;
                                androidPath.internalPath.lineTo(f13, verticalTo.y);
                                f14 = verticalTo.y;
                            } else if (pathNode4 instanceof PathNode.RelativeCurveTo) {
                                PathNode.RelativeCurveTo relativeCurveTo = (PathNode.RelativeCurveTo) pathNode4;
                                androidPath.internalPath.rCubicTo(relativeCurveTo.dx1, relativeCurveTo.dy1, relativeCurveTo.dx2, relativeCurveTo.dy2, relativeCurveTo.dx3, relativeCurveTo.dy3);
                                f15 = relativeCurveTo.dx2 + f13;
                                f16 = relativeCurveTo.dy2 + f14;
                                f13 += relativeCurveTo.dx3;
                                f11 = relativeCurveTo.dy3;
                            } else {
                                if (pathNode4 instanceof PathNode.CurveTo) {
                                    PathNode.CurveTo curveTo = (PathNode.CurveTo) pathNode4;
                                    androidPath.internalPath.cubicTo(curveTo.x1, curveTo.y1, curveTo.x2, curveTo.y2, curveTo.x3, curveTo.y3);
                                    f5 = curveTo.x2;
                                    f6 = curveTo.y2;
                                    f7 = curveTo.x3;
                                    f8 = curveTo.y3;
                                } else if (pathNode4 instanceof PathNode.RelativeReflectiveCurveTo) {
                                    if (pathNode3.isCurve) {
                                        f9 = f13 - f15;
                                        f10 = f14 - f16;
                                    } else {
                                        f9 = f12;
                                        f10 = f9;
                                    }
                                    PathNode.RelativeReflectiveCurveTo relativeReflectiveCurveTo = (PathNode.RelativeReflectiveCurveTo) pathNode4;
                                    androidPath.internalPath.rCubicTo(f9, f10, relativeReflectiveCurveTo.dx1, relativeReflectiveCurveTo.dy1, relativeReflectiveCurveTo.dx2, relativeReflectiveCurveTo.dy2);
                                    f15 = relativeReflectiveCurveTo.dx1 + f13;
                                    f16 = relativeReflectiveCurveTo.dy1 + f14;
                                    f13 += relativeReflectiveCurveTo.dx2;
                                    f11 = relativeReflectiveCurveTo.dy2;
                                } else if (pathNode4 instanceof PathNode.ReflectiveCurveTo) {
                                    if (pathNode3.isCurve) {
                                        float f26 = 2;
                                        f13 = (f13 * f26) - f15;
                                        f14 = (f26 * f14) - f16;
                                    }
                                    PathNode.ReflectiveCurveTo reflectiveCurveTo = (PathNode.ReflectiveCurveTo) pathNode4;
                                    androidPath.internalPath.cubicTo(f13, f14, reflectiveCurveTo.x1, reflectiveCurveTo.y1, reflectiveCurveTo.x2, reflectiveCurveTo.y2);
                                    f5 = reflectiveCurveTo.x1;
                                    f6 = reflectiveCurveTo.y1;
                                    f7 = reflectiveCurveTo.x2;
                                    f8 = reflectiveCurveTo.y2;
                                } else if (pathNode4 instanceof PathNode.RelativeQuadTo) {
                                    PathNode.RelativeQuadTo relativeQuadTo = (PathNode.RelativeQuadTo) pathNode4;
                                    float f27 = relativeQuadTo.dx1;
                                    android.graphics.Path path5 = androidPath.internalPath;
                                    float f28 = relativeQuadTo.dy1;
                                    float f29 = relativeQuadTo.dx2;
                                    float f30 = relativeQuadTo.dy2;
                                    path5.rQuadTo(f27, f28, f29, f30);
                                    float f31 = relativeQuadTo.dx1 + f13;
                                    float f32 = f28 + f14;
                                    f13 += f29;
                                    f14 += f30;
                                    f15 = f31;
                                    i2 = size;
                                    i3 = i4;
                                    f = f12;
                                    pathNode2 = pathNode4;
                                    f16 = f32;
                                } else {
                                    if (pathNode4 instanceof PathNode.QuadTo) {
                                        PathNode.QuadTo quadTo = (PathNode.QuadTo) pathNode4;
                                        float f33 = quadTo.x1;
                                        android.graphics.Path path6 = androidPath.internalPath;
                                        float f34 = quadTo.y1;
                                        float f35 = quadTo.x2;
                                        float f36 = quadTo.y2;
                                        path6.quadTo(f33, f34, f35, f36);
                                        f4 = quadTo.x1;
                                        i2 = size;
                                        i3 = i4;
                                        f = f12;
                                        f13 = f35;
                                        pathNode2 = pathNode4;
                                        f14 = f36;
                                        f16 = f34;
                                    } else if (pathNode4 instanceof PathNode.RelativeReflectiveQuadTo) {
                                        if (pathNode3.isQuad) {
                                            f2 = f13 - f15;
                                            f3 = f14 - f16;
                                        } else {
                                            f2 = f12;
                                            f3 = f2;
                                        }
                                        PathNode.RelativeReflectiveQuadTo relativeReflectiveQuadTo = (PathNode.RelativeReflectiveQuadTo) pathNode4;
                                        float f37 = relativeReflectiveQuadTo.dx;
                                        android.graphics.Path path7 = androidPath.internalPath;
                                        float f38 = relativeReflectiveQuadTo.dy;
                                        path7.rQuadTo(f2, f3, f37, f38);
                                        f4 = f2 + f13;
                                        float f39 = f3 + f14;
                                        f13 += relativeReflectiveQuadTo.dx;
                                        f14 += f38;
                                        i2 = size;
                                        i3 = i4;
                                        f = f12;
                                        f16 = f39;
                                        pathNode2 = pathNode4;
                                    } else if (pathNode4 instanceof PathNode.ReflectiveQuadTo) {
                                        if (pathNode3.isQuad) {
                                            float f40 = 2;
                                            f13 = (f13 * f40) - f15;
                                            f14 = (f40 * f14) - f16;
                                        }
                                        PathNode.ReflectiveQuadTo reflectiveQuadTo = (PathNode.ReflectiveQuadTo) pathNode4;
                                        float f41 = reflectiveQuadTo.x;
                                        android.graphics.Path path8 = androidPath.internalPath;
                                        float f42 = reflectiveQuadTo.y;
                                        path8.quadTo(f13, f14, f41, f42);
                                        i2 = size;
                                        i3 = i4;
                                        f = f12;
                                        f15 = f13;
                                        f16 = f14;
                                        pathNode2 = pathNode4;
                                        f14 = f42;
                                        f13 = reflectiveQuadTo.x;
                                    } else {
                                        if (pathNode4 instanceof PathNode.RelativeArcTo) {
                                            PathNode.RelativeArcTo relativeArcTo = (PathNode.RelativeArcTo) pathNode4;
                                            float f43 = relativeArcTo.arcStartDx + f13;
                                            float f44 = relativeArcTo.arcStartDy + f14;
                                            i2 = size;
                                            androidPath = androidPath;
                                            pathNode = pathNode4;
                                            f = 0.0f;
                                            i3 = i4;
                                            drawArc(androidPath, f13, f14, f43, f44, relativeArcTo.horizontalEllipseRadius, relativeArcTo.verticalEllipseRadius, relativeArcTo.theta, relativeArcTo.isMoreThanHalf, relativeArcTo.isPositiveArc);
                                            f13 = f43;
                                            f15 = f13;
                                            f14 = f44;
                                            f16 = f14;
                                        } else {
                                            i2 = size;
                                            i3 = i4;
                                            f = f12;
                                            pathNode = pathNode4;
                                            if (pathNode instanceof PathNode.ArcTo) {
                                                double d = f13;
                                                double d2 = f14;
                                                PathNode.ArcTo arcTo = (PathNode.ArcTo) pathNode;
                                                double d3 = arcTo.arcStartX;
                                                float f45 = arcTo.arcStartY;
                                                pathNode2 = pathNode;
                                                androidPath = androidPath;
                                                drawArc(androidPath, d, d2, d3, f45, arcTo.horizontalEllipseRadius, arcTo.verticalEllipseRadius, arcTo.theta, arcTo.isMoreThanHalf, arcTo.isPositiveArc);
                                                f13 = arcTo.arcStartX;
                                                f15 = f13;
                                                f14 = f45;
                                            }
                                        }
                                        pathNode2 = pathNode;
                                    }
                                    f15 = f4;
                                }
                                float f46 = f7;
                                f15 = f5;
                                f13 = f46;
                                i2 = size;
                                i3 = i4;
                                f = f12;
                                f16 = f6;
                                pathNode2 = pathNode4;
                                f14 = f8;
                            }
                            f14 += f11;
                        }
                        i4 = i3 + 1;
                        list2 = list;
                        size = i2;
                        f12 = f;
                        pathNode3 = pathNode2;
                    }
                    i2 = size;
                    i3 = i4;
                    f = f12;
                }
                pathNode2 = pathNode4;
                i4 = i3 + 1;
                list2 = list;
                size = i2;
                f12 = f;
                pathNode3 = pathNode2;
            }
            f16 = f14;
            i4 = i3 + 1;
            list2 = list;
            size = i2;
            f12 = f;
            pathNode3 = pathNode2;
        }
        return androidPath;
    }
}
