package androidx.compose.ui.draw;

import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;
import androidx.compose.ui.graphics.Shape;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BlurredEdgeTreatment {
    public static final Companion Companion = new Companion(null);
    public static final RectangleShapeKt$RectangleShape$1 Rectangle = RectangleShapeKt.RectangleShape;
    public final Shape shape;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ BlurredEdgeTreatment(Shape shape) {
        this.shape = shape;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BlurredEdgeTreatment m359boximpl(RectangleShapeKt$RectangleShape$1 rectangleShapeKt$RectangleShape$1) {
        return new BlurredEdgeTreatment(rectangleShapeKt$RectangleShape$1);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof BlurredEdgeTreatment) {
            return Intrinsics.areEqual(this.shape, ((BlurredEdgeTreatment) obj).shape);
        }
        return false;
    }

    public final int hashCode() {
        Shape shape = this.shape;
        if (shape == null) {
            return 0;
        }
        return shape.hashCode();
    }

    public final String toString() {
        return "BlurredEdgeTreatment(shape=" + this.shape + ')';
    }
}
