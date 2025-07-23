package androidx.compose.ui.graphics.vector;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PathNode {
    public final boolean isCurve;
    public final boolean isQuad;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ArcTo extends PathNode {
        public final float arcStartX;
        public final float arcStartY;
        public final float horizontalEllipseRadius;
        public final boolean isMoreThanHalf;
        public final boolean isPositiveArc;
        public final float theta;
        public final float verticalEllipseRadius;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public ArcTo(float r4, float r5, float r6, boolean r7, boolean r8, float r9, float r10) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.horizontalEllipseRadius = r4
                r3.verticalEllipseRadius = r5
                r3.theta = r6
                r3.isMoreThanHalf = r7
                r3.isPositiveArc = r8
                r3.arcStartX = r9
                r3.arcStartY = r10
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.ArcTo.<init>(float, float, float, boolean, boolean, float, float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ArcTo)) {
                return false;
            }
            ArcTo arcTo = (ArcTo) obj;
            return Float.compare(this.horizontalEllipseRadius, arcTo.horizontalEllipseRadius) == 0 && Float.compare(this.verticalEllipseRadius, arcTo.verticalEllipseRadius) == 0 && Float.compare(this.theta, arcTo.theta) == 0 && this.isMoreThanHalf == arcTo.isMoreThanHalf && this.isPositiveArc == arcTo.isPositiveArc && Float.compare(this.arcStartX, arcTo.arcStartX) == 0 && Float.compare(this.arcStartY, arcTo.arcStartY) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.arcStartY) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.arcStartX, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.theta, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.verticalEllipseRadius, Float.hashCode(this.horizontalEllipseRadius) * 31, 31), 31), 31, this.isMoreThanHalf), 31, this.isPositiveArc), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ArcTo(horizontalEllipseRadius=");
            sb.append(this.horizontalEllipseRadius);
            sb.append(", verticalEllipseRadius=");
            sb.append(this.verticalEllipseRadius);
            sb.append(", theta=");
            sb.append(this.theta);
            sb.append(", isMoreThanHalf=");
            sb.append(this.isMoreThanHalf);
            sb.append(", isPositiveArc=");
            sb.append(this.isPositiveArc);
            sb.append(", arcStartX=");
            sb.append(this.arcStartX);
            sb.append(", arcStartY=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.arcStartY, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Close extends PathNode {
        public static final Close INSTANCE = new Close();

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Close() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.Close.<init>():void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CurveTo extends PathNode {
        public final float x1;
        public final float x2;
        public final float x3;
        public final float y1;
        public final float y2;
        public final float y3;

        public CurveTo(float f, float f2, float f3, float f4, float f5, float f6) {
            super(true, false, 2, null);
            this.x1 = f;
            this.y1 = f2;
            this.x2 = f3;
            this.y2 = f4;
            this.x3 = f5;
            this.y3 = f6;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CurveTo)) {
                return false;
            }
            CurveTo curveTo = (CurveTo) obj;
            return Float.compare(this.x1, curveTo.x1) == 0 && Float.compare(this.y1, curveTo.y1) == 0 && Float.compare(this.x2, curveTo.x2) == 0 && Float.compare(this.y2, curveTo.y2) == 0 && Float.compare(this.x3, curveTo.x3) == 0 && Float.compare(this.y3, curveTo.y3) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y3) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.x3, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.x2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y1, Float.hashCode(this.x1) * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CurveTo(x1=");
            sb.append(this.x1);
            sb.append(", y1=");
            sb.append(this.y1);
            sb.append(", x2=");
            sb.append(this.x2);
            sb.append(", y2=");
            sb.append(this.y2);
            sb.append(", x3=");
            sb.append(this.x3);
            sb.append(", y3=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y3, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HorizontalTo extends PathNode {
        public final float x;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public HorizontalTo(float r4) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.x = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.HorizontalTo.<init>(float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof HorizontalTo) && Float.compare(this.x, ((HorizontalTo) obj).x) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.x);
        }

        public final String toString() {
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("HorizontalTo(x="), this.x, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LineTo extends PathNode {
        public final float x;
        public final float y;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public LineTo(float r4, float r5) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.x = r4
                r3.y = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.LineTo.<init>(float, float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LineTo)) {
                return false;
            }
            LineTo lineTo = (LineTo) obj;
            return Float.compare(this.x, lineTo.x) == 0 && Float.compare(this.y, lineTo.y) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LineTo(x=");
            sb.append(this.x);
            sb.append(", y=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MoveTo extends PathNode {
        public final float x;
        public final float y;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public MoveTo(float r4, float r5) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.x = r4
                r3.y = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.MoveTo.<init>(float, float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MoveTo)) {
                return false;
            }
            MoveTo moveTo = (MoveTo) obj;
            return Float.compare(this.x, moveTo.x) == 0 && Float.compare(this.y, moveTo.y) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MoveTo(x=");
            sb.append(this.x);
            sb.append(", y=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class QuadTo extends PathNode {
        public final float x1;
        public final float x2;
        public final float y1;
        public final float y2;

        public QuadTo(float f, float f2, float f3, float f4) {
            super(false, true, true ? 1 : 0, null);
            this.x1 = f;
            this.y1 = f2;
            this.x2 = f3;
            this.y2 = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof QuadTo)) {
                return false;
            }
            QuadTo quadTo = (QuadTo) obj;
            return Float.compare(this.x1, quadTo.x1) == 0 && Float.compare(this.y1, quadTo.y1) == 0 && Float.compare(this.x2, quadTo.x2) == 0 && Float.compare(this.y2, quadTo.y2) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y2) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.x2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y1, Float.hashCode(this.x1) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("QuadTo(x1=");
            sb.append(this.x1);
            sb.append(", y1=");
            sb.append(this.y1);
            sb.append(", x2=");
            sb.append(this.x2);
            sb.append(", y2=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y2, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReflectiveCurveTo extends PathNode {
        public final float x1;
        public final float x2;
        public final float y1;
        public final float y2;

        public ReflectiveCurveTo(float f, float f2, float f3, float f4) {
            super(true, false, 2, null);
            this.x1 = f;
            this.y1 = f2;
            this.x2 = f3;
            this.y2 = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReflectiveCurveTo)) {
                return false;
            }
            ReflectiveCurveTo reflectiveCurveTo = (ReflectiveCurveTo) obj;
            return Float.compare(this.x1, reflectiveCurveTo.x1) == 0 && Float.compare(this.y1, reflectiveCurveTo.y1) == 0 && Float.compare(this.x2, reflectiveCurveTo.x2) == 0 && Float.compare(this.y2, reflectiveCurveTo.y2) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y2) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.x2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.y1, Float.hashCode(this.x1) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ReflectiveCurveTo(x1=");
            sb.append(this.x1);
            sb.append(", y1=");
            sb.append(this.y1);
            sb.append(", x2=");
            sb.append(this.x2);
            sb.append(", y2=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y2, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReflectiveQuadTo extends PathNode {
        public final float x;
        public final float y;

        public ReflectiveQuadTo(float f, float f2) {
            super(false, true, true ? 1 : 0, null);
            this.x = f;
            this.y = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReflectiveQuadTo)) {
                return false;
            }
            ReflectiveQuadTo reflectiveQuadTo = (ReflectiveQuadTo) obj;
            return Float.compare(this.x, reflectiveQuadTo.x) == 0 && Float.compare(this.y, reflectiveQuadTo.y) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ReflectiveQuadTo(x=");
            sb.append(this.x);
            sb.append(", y=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeArcTo extends PathNode {
        public final float arcStartDx;
        public final float arcStartDy;
        public final float horizontalEllipseRadius;
        public final boolean isMoreThanHalf;
        public final boolean isPositiveArc;
        public final float theta;
        public final float verticalEllipseRadius;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RelativeArcTo(float r4, float r5, float r6, boolean r7, boolean r8, float r9, float r10) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.horizontalEllipseRadius = r4
                r3.verticalEllipseRadius = r5
                r3.theta = r6
                r3.isMoreThanHalf = r7
                r3.isPositiveArc = r8
                r3.arcStartDx = r9
                r3.arcStartDy = r10
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.RelativeArcTo.<init>(float, float, float, boolean, boolean, float, float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeArcTo)) {
                return false;
            }
            RelativeArcTo relativeArcTo = (RelativeArcTo) obj;
            return Float.compare(this.horizontalEllipseRadius, relativeArcTo.horizontalEllipseRadius) == 0 && Float.compare(this.verticalEllipseRadius, relativeArcTo.verticalEllipseRadius) == 0 && Float.compare(this.theta, relativeArcTo.theta) == 0 && this.isMoreThanHalf == relativeArcTo.isMoreThanHalf && this.isPositiveArc == relativeArcTo.isPositiveArc && Float.compare(this.arcStartDx, relativeArcTo.arcStartDx) == 0 && Float.compare(this.arcStartDy, relativeArcTo.arcStartDy) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.arcStartDy) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.arcStartDx, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.theta, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.verticalEllipseRadius, Float.hashCode(this.horizontalEllipseRadius) * 31, 31), 31), 31, this.isMoreThanHalf), 31, this.isPositiveArc), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeArcTo(horizontalEllipseRadius=");
            sb.append(this.horizontalEllipseRadius);
            sb.append(", verticalEllipseRadius=");
            sb.append(this.verticalEllipseRadius);
            sb.append(", theta=");
            sb.append(this.theta);
            sb.append(", isMoreThanHalf=");
            sb.append(this.isMoreThanHalf);
            sb.append(", isPositiveArc=");
            sb.append(this.isPositiveArc);
            sb.append(", arcStartDx=");
            sb.append(this.arcStartDx);
            sb.append(", arcStartDy=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.arcStartDy, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeCurveTo extends PathNode {
        public final float dx1;
        public final float dx2;
        public final float dx3;
        public final float dy1;
        public final float dy2;
        public final float dy3;

        public RelativeCurveTo(float f, float f2, float f3, float f4, float f5, float f6) {
            super(true, false, 2, null);
            this.dx1 = f;
            this.dy1 = f2;
            this.dx2 = f3;
            this.dy2 = f4;
            this.dx3 = f5;
            this.dy3 = f6;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeCurveTo)) {
                return false;
            }
            RelativeCurveTo relativeCurveTo = (RelativeCurveTo) obj;
            return Float.compare(this.dx1, relativeCurveTo.dx1) == 0 && Float.compare(this.dy1, relativeCurveTo.dy1) == 0 && Float.compare(this.dx2, relativeCurveTo.dx2) == 0 && Float.compare(this.dy2, relativeCurveTo.dy2) == 0 && Float.compare(this.dx3, relativeCurveTo.dx3) == 0 && Float.compare(this.dy3, relativeCurveTo.dy3) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy3) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dx3, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dy2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dx2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dy1, Float.hashCode(this.dx1) * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeCurveTo(dx1=");
            sb.append(this.dx1);
            sb.append(", dy1=");
            sb.append(this.dy1);
            sb.append(", dx2=");
            sb.append(this.dx2);
            sb.append(", dy2=");
            sb.append(this.dy2);
            sb.append(", dx3=");
            sb.append(this.dx3);
            sb.append(", dy3=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dy3, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeHorizontalTo extends PathNode {
        public final float dx;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RelativeHorizontalTo(float r4) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.dx = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.RelativeHorizontalTo.<init>(float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RelativeHorizontalTo) && Float.compare(this.dx, ((RelativeHorizontalTo) obj).dx) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dx);
        }

        public final String toString() {
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("RelativeHorizontalTo(dx="), this.dx, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeLineTo extends PathNode {
        public final float dx;
        public final float dy;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RelativeLineTo(float r4, float r5) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.dx = r4
                r3.dy = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.RelativeLineTo.<init>(float, float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeLineTo)) {
                return false;
            }
            RelativeLineTo relativeLineTo = (RelativeLineTo) obj;
            return Float.compare(this.dx, relativeLineTo.dx) == 0 && Float.compare(this.dy, relativeLineTo.dy) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy) + (Float.hashCode(this.dx) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeLineTo(dx=");
            sb.append(this.dx);
            sb.append(", dy=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dy, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeMoveTo extends PathNode {
        public final float dx;
        public final float dy;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RelativeMoveTo(float r4, float r5) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.dx = r4
                r3.dy = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.RelativeMoveTo.<init>(float, float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeMoveTo)) {
                return false;
            }
            RelativeMoveTo relativeMoveTo = (RelativeMoveTo) obj;
            return Float.compare(this.dx, relativeMoveTo.dx) == 0 && Float.compare(this.dy, relativeMoveTo.dy) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy) + (Float.hashCode(this.dx) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeMoveTo(dx=");
            sb.append(this.dx);
            sb.append(", dy=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dy, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeQuadTo extends PathNode {
        public final float dx1;
        public final float dx2;
        public final float dy1;
        public final float dy2;

        public RelativeQuadTo(float f, float f2, float f3, float f4) {
            super(false, true, true ? 1 : 0, null);
            this.dx1 = f;
            this.dy1 = f2;
            this.dx2 = f3;
            this.dy2 = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeQuadTo)) {
                return false;
            }
            RelativeQuadTo relativeQuadTo = (RelativeQuadTo) obj;
            return Float.compare(this.dx1, relativeQuadTo.dx1) == 0 && Float.compare(this.dy1, relativeQuadTo.dy1) == 0 && Float.compare(this.dx2, relativeQuadTo.dx2) == 0 && Float.compare(this.dy2, relativeQuadTo.dy2) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy2) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dx2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dy1, Float.hashCode(this.dx1) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeQuadTo(dx1=");
            sb.append(this.dx1);
            sb.append(", dy1=");
            sb.append(this.dy1);
            sb.append(", dx2=");
            sb.append(this.dx2);
            sb.append(", dy2=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dy2, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeReflectiveCurveTo extends PathNode {
        public final float dx1;
        public final float dx2;
        public final float dy1;
        public final float dy2;

        public RelativeReflectiveCurveTo(float f, float f2, float f3, float f4) {
            super(true, false, 2, null);
            this.dx1 = f;
            this.dy1 = f2;
            this.dx2 = f3;
            this.dy2 = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeReflectiveCurveTo)) {
                return false;
            }
            RelativeReflectiveCurveTo relativeReflectiveCurveTo = (RelativeReflectiveCurveTo) obj;
            return Float.compare(this.dx1, relativeReflectiveCurveTo.dx1) == 0 && Float.compare(this.dy1, relativeReflectiveCurveTo.dy1) == 0 && Float.compare(this.dx2, relativeReflectiveCurveTo.dx2) == 0 && Float.compare(this.dy2, relativeReflectiveCurveTo.dy2) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy2) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dx2, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.dy1, Float.hashCode(this.dx1) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeReflectiveCurveTo(dx1=");
            sb.append(this.dx1);
            sb.append(", dy1=");
            sb.append(this.dy1);
            sb.append(", dx2=");
            sb.append(this.dx2);
            sb.append(", dy2=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dy2, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeReflectiveQuadTo extends PathNode {
        public final float dx;
        public final float dy;

        public RelativeReflectiveQuadTo(float f, float f2) {
            super(false, true, true ? 1 : 0, null);
            this.dx = f;
            this.dy = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RelativeReflectiveQuadTo)) {
                return false;
            }
            RelativeReflectiveQuadTo relativeReflectiveQuadTo = (RelativeReflectiveQuadTo) obj;
            return Float.compare(this.dx, relativeReflectiveQuadTo.dx) == 0 && Float.compare(this.dy, relativeReflectiveQuadTo.dy) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy) + (Float.hashCode(this.dx) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RelativeReflectiveQuadTo(dx=");
            sb.append(this.dx);
            sb.append(", dy=");
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.dy, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RelativeVerticalTo extends PathNode {
        public final float dy;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public RelativeVerticalTo(float r4) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.dy = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.RelativeVerticalTo.<init>(float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RelativeVerticalTo) && Float.compare(this.dy, ((RelativeVerticalTo) obj).dy) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.dy);
        }

        public final String toString() {
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("RelativeVerticalTo(dy="), this.dy, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class VerticalTo extends PathNode {
        public final float y;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public VerticalTo(float r4) {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                r3.y = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.PathNode.VerticalTo.<init>(float):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof VerticalTo) && Float.compare(this.y, ((VerticalTo) obj).y) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.y);
        }

        public final String toString() {
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("VerticalTo(y="), this.y, ')');
        }
    }

    public /* synthetic */ PathNode(boolean z, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2);
    }

    private PathNode(boolean z, boolean z2) {
        this.isCurve = z;
        this.isQuad = z2;
    }

    public /* synthetic */ PathNode(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, null);
    }
}
