package androidx.compose.ui.draw;

import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;
import androidx.compose.ui.graphics.Shape;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class BlurredEdgeTreatment {
    public static final Companion Companion = new Companion(null);
    public static final RectangleShapeKt$RectangleShape$1 Rectangle = RectangleShapeKt.RectangleShape;
    public final Shape shape;

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
    public static final /* synthetic */ BlurredEdgeTreatment m360boximpl(RectangleShapeKt$RectangleShape$1 rectangleShapeKt$RectangleShape$1) {
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
