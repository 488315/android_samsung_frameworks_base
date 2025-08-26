package androidx.compose.material3;

import androidx.compose.foundation.shape.CornerBasedShape;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Shapes {
    public final CornerBasedShape extraExtraLarge;
    public final CornerBasedShape extraLarge;
    public final CornerBasedShape extraLargeIncreased;
    public final CornerBasedShape extraSmall;
    public final CornerBasedShape large;
    public final CornerBasedShape largeIncreased;
    public final CornerBasedShape medium;
    public final CornerBasedShape small;

    public Shapes() {
        this(null, null, null, null, null, null, null, null, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Shapes)) {
            return false;
        }
        Shapes shapes = (Shapes) obj;
        return Intrinsics.areEqual(this.extraSmall, shapes.extraSmall) && Intrinsics.areEqual(this.small, shapes.small) && Intrinsics.areEqual(this.medium, shapes.medium) && Intrinsics.areEqual(this.large, shapes.large) && Intrinsics.areEqual(this.extraLarge, shapes.extraLarge) && Intrinsics.areEqual(this.largeIncreased, shapes.largeIncreased) && Intrinsics.areEqual(this.extraLargeIncreased, shapes.extraLargeIncreased) && Intrinsics.areEqual(this.extraExtraLarge, shapes.extraExtraLarge);
    }

    public final int hashCode() {
        return this.extraExtraLarge.hashCode() + ((this.extraLargeIncreased.hashCode() + ((this.largeIncreased.hashCode() + ((this.extraLarge.hashCode() + ((this.large.hashCode() + ((this.medium.hashCode() + ((this.small.hashCode() + (this.extraSmall.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "Shapes(extraSmall=" + this.extraSmall + ", small=" + this.small + ", medium=" + this.medium + ", large=" + this.large + ", largeIncreased=" + this.largeIncreased + ", extraLarge=" + this.extraLarge + ", extralargeIncreased=" + this.extraLargeIncreased + ", extraExtraLarge=" + this.extraExtraLarge + ')';
    }

    public Shapes(CornerBasedShape cornerBasedShape, CornerBasedShape cornerBasedShape2, CornerBasedShape cornerBasedShape3, CornerBasedShape cornerBasedShape4, CornerBasedShape cornerBasedShape5, CornerBasedShape cornerBasedShape6, CornerBasedShape cornerBasedShape7, CornerBasedShape cornerBasedShape8) {
        this.extraSmall = cornerBasedShape;
        this.small = cornerBasedShape2;
        this.medium = cornerBasedShape3;
        this.large = cornerBasedShape4;
        this.extraLarge = cornerBasedShape5;
        this.largeIncreased = cornerBasedShape6;
        this.extraLargeIncreased = cornerBasedShape7;
        this.extraExtraLarge = cornerBasedShape8;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Shapes(CornerBasedShape cornerBasedShape, CornerBasedShape cornerBasedShape2, CornerBasedShape cornerBasedShape3, CornerBasedShape cornerBasedShape4, CornerBasedShape cornerBasedShape5, CornerBasedShape cornerBasedShape6, CornerBasedShape cornerBasedShape7, CornerBasedShape cornerBasedShape8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape = ShapeDefaults.ExtraSmall;
        }
        if ((i & 2) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape2 = ShapeDefaults.Small;
        }
        if ((i & 4) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape3 = ShapeDefaults.Medium;
        }
        if ((i & 8) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape4 = ShapeDefaults.Large;
        }
        if ((i & 16) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape5 = ShapeDefaults.ExtraLarge;
        }
        if ((i & 32) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape6 = ShapeDefaults.LargeIncreased;
        }
        if ((i & 64) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape7 = ShapeDefaults.ExtraLargeIncreased;
        }
        if ((i & 128) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape8 = ShapeDefaults.ExtraExtraLarge;
        }
        CornerBasedShape cornerBasedShape9 = cornerBasedShape7;
        CornerBasedShape cornerBasedShape10 = cornerBasedShape8;
        CornerBasedShape cornerBasedShape11 = cornerBasedShape5;
        CornerBasedShape cornerBasedShape12 = cornerBasedShape6;
        this(cornerBasedShape, cornerBasedShape2, cornerBasedShape3, cornerBasedShape4, cornerBasedShape11, cornerBasedShape12, cornerBasedShape9, cornerBasedShape10);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Shapes(CornerBasedShape cornerBasedShape, CornerBasedShape cornerBasedShape2, CornerBasedShape cornerBasedShape3, CornerBasedShape cornerBasedShape4, CornerBasedShape cornerBasedShape5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape = ShapeDefaults.ExtraSmall;
        }
        if ((i & 2) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape2 = ShapeDefaults.Small;
        }
        if ((i & 4) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape3 = ShapeDefaults.Medium;
        }
        if ((i & 8) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape4 = ShapeDefaults.Large;
        }
        if ((i & 16) != 0) {
            ShapeDefaults.INSTANCE.getClass();
            cornerBasedShape5 = ShapeDefaults.ExtraLarge;
        }
        CornerBasedShape cornerBasedShape6 = cornerBasedShape5;
        CornerBasedShape cornerBasedShape7 = cornerBasedShape3;
        CornerBasedShape cornerBasedShape8 = cornerBasedShape;
        this(cornerBasedShape8, cornerBasedShape2, cornerBasedShape7, cornerBasedShape4, cornerBasedShape6);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Shapes(CornerBasedShape cornerBasedShape, CornerBasedShape cornerBasedShape2, CornerBasedShape cornerBasedShape3, CornerBasedShape cornerBasedShape4, CornerBasedShape cornerBasedShape5) {
        this(cornerBasedShape, cornerBasedShape2, cornerBasedShape3, cornerBasedShape4, cornerBasedShape5, ShapeDefaults.LargeIncreased, ShapeDefaults.ExtraLargeIncreased, ShapeDefaults.ExtraExtraLarge);
        ShapeDefaults.INSTANCE.getClass();
    }
}
