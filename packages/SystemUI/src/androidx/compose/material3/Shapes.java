package androidx.compose.material3;

import androidx.compose.foundation.shape.CornerBasedShape;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Shapes(androidx.compose.foundation.shape.CornerBasedShape r1, androidx.compose.foundation.shape.CornerBasedShape r2, androidx.compose.foundation.shape.CornerBasedShape r3, androidx.compose.foundation.shape.CornerBasedShape r4, androidx.compose.foundation.shape.CornerBasedShape r5, androidx.compose.foundation.shape.CornerBasedShape r6, androidx.compose.foundation.shape.CornerBasedShape r7, androidx.compose.foundation.shape.CornerBasedShape r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r0 = this;
            r10 = r9 & 1
            if (r10 == 0) goto Lb
            androidx.compose.material3.ShapeDefaults r1 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r1.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r1 = androidx.compose.material3.ShapeDefaults.ExtraSmall
        Lb:
            r10 = r9 & 2
            if (r10 == 0) goto L16
            androidx.compose.material3.ShapeDefaults r2 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r2.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r2 = androidx.compose.material3.ShapeDefaults.Small
        L16:
            r10 = r9 & 4
            if (r10 == 0) goto L21
            androidx.compose.material3.ShapeDefaults r3 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r3.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r3 = androidx.compose.material3.ShapeDefaults.Medium
        L21:
            r10 = r9 & 8
            if (r10 == 0) goto L2c
            androidx.compose.material3.ShapeDefaults r4 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r4.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r4 = androidx.compose.material3.ShapeDefaults.Large
        L2c:
            r10 = r9 & 16
            if (r10 == 0) goto L37
            androidx.compose.material3.ShapeDefaults r5 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r5.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r5 = androidx.compose.material3.ShapeDefaults.ExtraLarge
        L37:
            r10 = r9 & 32
            if (r10 == 0) goto L42
            androidx.compose.material3.ShapeDefaults r6 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r6.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r6 = androidx.compose.material3.ShapeDefaults.LargeIncreased
        L42:
            r10 = r9 & 64
            if (r10 == 0) goto L4d
            androidx.compose.material3.ShapeDefaults r7 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r7.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r7 = androidx.compose.material3.ShapeDefaults.ExtraLargeIncreased
        L4d:
            r9 = r9 & 128(0x80, float:1.8E-43)
            if (r9 == 0) goto L58
            androidx.compose.material3.ShapeDefaults r8 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r8.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r8 = androidx.compose.material3.ShapeDefaults.ExtraExtraLarge
        L58:
            r9 = r7
            r10 = r8
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.Shapes.<init>(androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Shapes(androidx.compose.foundation.shape.CornerBasedShape r1, androidx.compose.foundation.shape.CornerBasedShape r2, androidx.compose.foundation.shape.CornerBasedShape r3, androidx.compose.foundation.shape.CornerBasedShape r4, androidx.compose.foundation.shape.CornerBasedShape r5, int r6, kotlin.jvm.internal.DefaultConstructorMarker r7) {
        /*
            r0 = this;
            r7 = r6 & 1
            if (r7 == 0) goto Lb
            androidx.compose.material3.ShapeDefaults r1 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r1.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r1 = androidx.compose.material3.ShapeDefaults.ExtraSmall
        Lb:
            r7 = r6 & 2
            if (r7 == 0) goto L16
            androidx.compose.material3.ShapeDefaults r2 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r2.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r2 = androidx.compose.material3.ShapeDefaults.Small
        L16:
            r7 = r6 & 4
            if (r7 == 0) goto L21
            androidx.compose.material3.ShapeDefaults r3 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r3.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r3 = androidx.compose.material3.ShapeDefaults.Medium
        L21:
            r7 = r6 & 8
            if (r7 == 0) goto L2c
            androidx.compose.material3.ShapeDefaults r4 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r4.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r4 = androidx.compose.material3.ShapeDefaults.Large
        L2c:
            r6 = r6 & 16
            if (r6 == 0) goto L37
            androidx.compose.material3.ShapeDefaults r5 = androidx.compose.material3.ShapeDefaults.INSTANCE
            r5.getClass()
            androidx.compose.foundation.shape.RoundedCornerShape r5 = androidx.compose.material3.ShapeDefaults.ExtraLarge
        L37:
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r2 = r0
            r3 = r1
            r2.<init>(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.Shapes.<init>(androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, androidx.compose.foundation.shape.CornerBasedShape, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Shapes(CornerBasedShape cornerBasedShape, CornerBasedShape cornerBasedShape2, CornerBasedShape cornerBasedShape3, CornerBasedShape cornerBasedShape4, CornerBasedShape cornerBasedShape5) {
        this(cornerBasedShape, cornerBasedShape2, cornerBasedShape3, cornerBasedShape4, cornerBasedShape5, ShapeDefaults.LargeIncreased, ShapeDefaults.ExtraLargeIncreased, ShapeDefaults.ExtraExtraLarge);
        ShapeDefaults.INSTANCE.getClass();
    }
}
