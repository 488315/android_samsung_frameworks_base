package androidx.compose.foundation.layout;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Arrangement {
    public static final Arrangement$SpaceBetween$1 SpaceBetween;
    public static final Arrangement INSTANCE = new Arrangement();
    public static final Arrangement$Start$1 Start = new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Start$1
        @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
        public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
            if (layoutDirection == LayoutDirection.Ltr) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeLeftOrTop$foundation_layout(iArr, iArr2, false);
            } else {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeRightOrBottom$foundation_layout(i, iArr, iArr2, true);
            }
        }

        public final String toString() {
            return "Arrangement#Start";
        }
    };
    public static final Arrangement$End$1 End = new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$End$1
        @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
        public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
            if (layoutDirection == LayoutDirection.Ltr) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeRightOrBottom$foundation_layout(i, iArr, iArr2, false);
            } else {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeLeftOrTop$foundation_layout(iArr, iArr2, true);
            }
        }

        public final String toString() {
            return "Arrangement#End";
        }
    };
    public static final Arrangement$Top$1 Top = new Vertical() { // from class: androidx.compose.foundation.layout.Arrangement$Top$1
        @Override // androidx.compose.foundation.layout.Arrangement.Vertical
        public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
            Arrangement.INSTANCE.getClass();
            Arrangement.placeLeftOrTop$foundation_layout(iArr, iArr2, false);
        }

        public final String toString() {
            return "Arrangement#Top";
        }
    };
    public static final Arrangement$Bottom$1 Bottom = new Vertical() { // from class: androidx.compose.foundation.layout.Arrangement$Bottom$1
        @Override // androidx.compose.foundation.layout.Arrangement.Vertical
        public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
            Arrangement.INSTANCE.getClass();
            Arrangement.placeRightOrBottom$foundation_layout(i, iArr, iArr2, false);
        }

        public final String toString() {
            return "Arrangement#Bottom";
        }
    };
    public static final Arrangement$Center$1 Center = new HorizontalOrVertical() { // from class: androidx.compose.foundation.layout.Arrangement$Center$1
        public final float spacing;

        {
            Dp.Companion companion = Dp.Companion;
            this.spacing = 0;
        }

        @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
        public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
            if (layoutDirection == LayoutDirection.Ltr) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeCenter$foundation_layout(i, iArr, iArr2, false);
            } else {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeCenter$foundation_layout(i, iArr, iArr2, true);
            }
        }

        @Override // androidx.compose.foundation.layout.Arrangement.Horizontal, androidx.compose.foundation.layout.Arrangement.Vertical
        /* renamed from: getSpacing-D9Ej5fM, reason: not valid java name */
        public final float mo94getSpacingD9Ej5fM() {
            return this.spacing;
        }

        public final String toString() {
            return "Arrangement#Center";
        }

        @Override // androidx.compose.foundation.layout.Arrangement.Vertical
        public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
            Arrangement.INSTANCE.getClass();
            Arrangement.placeCenter$foundation_layout(i, iArr, iArr2, false);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Absolute {
        public static final Absolute INSTANCE = new Absolute();
        public static final Arrangement$Absolute$Left$1 Left = new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Absolute$Left$1
            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
            public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeLeftOrTop$foundation_layout(iArr, iArr2, false);
            }

            public final String toString() {
                return "AbsoluteArrangement#Left";
            }
        };
        public static final Arrangement$Absolute$Right$1 Right;

        /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.foundation.layout.Arrangement$Absolute$Left$1] */
        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.foundation.layout.Arrangement$Absolute$Right$1] */
        static {
            new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Absolute$Center$1
                @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
                public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeCenter$foundation_layout(i, iArr, iArr2, false);
                }

                public final String toString() {
                    return "AbsoluteArrangement#Center";
                }
            };
            Right = new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Absolute$Right$1
                @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
                public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeRightOrBottom$foundation_layout(i, iArr, iArr2, false);
                }

                public final String toString() {
                    return "AbsoluteArrangement#Right";
                }
            };
            new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Absolute$SpaceBetween$1
                @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
                public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceBetween$foundation_layout(i, iArr, iArr2, false);
                }

                public final String toString() {
                    return "AbsoluteArrangement#SpaceBetween";
                }
            };
            new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Absolute$SpaceEvenly$1
                @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
                public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceEvenly$foundation_layout(i, iArr, iArr2, false);
                }

                public final String toString() {
                    return "AbsoluteArrangement#SpaceEvenly";
                }
            };
            new Horizontal() { // from class: androidx.compose.foundation.layout.Arrangement$Absolute$SpaceAround$1
                @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
                public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceAround$foundation_layout(i, iArr, iArr2, false);
                }

                public final String toString() {
                    return "AbsoluteArrangement#SpaceAround";
                }
            };
        }

        private Absolute() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Horizontal {
        void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2);

        /* renamed from: getSpacing-D9Ej5fM */
        default float mo94getSpacingD9Ej5fM() {
            float f = 0;
            Dp.Companion companion = Dp.Companion;
            return f;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface HorizontalOrVertical extends Horizontal, Vertical {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SpacedAligned implements HorizontalOrVertical {
        public final Function2 alignment;
        public final boolean rtlMirror;
        public final float space;
        public final float spacing;

        public /* synthetic */ SpacedAligned(float f, boolean z, Function2 function2, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, z, function2);
        }

        @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
        public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
            int i2;
            int i3;
            if (iArr.length == 0) {
                return;
            }
            int mo51roundToPx0680j_4 = density.mo51roundToPx0680j_4(this.space);
            boolean z = this.rtlMirror && layoutDirection == LayoutDirection.Rtl;
            Arrangement arrangement = Arrangement.INSTANCE;
            if (z) {
                int length = iArr.length - 1;
                i2 = 0;
                i3 = 0;
                while (-1 < length) {
                    int i4 = iArr[length];
                    int min = Math.min(i2, i - i4);
                    iArr2[length] = min;
                    int min2 = Math.min(mo51roundToPx0680j_4, (i - min) - i4);
                    int i5 = iArr2[length] + i4 + min2;
                    length--;
                    i3 = min2;
                    i2 = i5;
                }
            } else {
                int length2 = iArr.length;
                int i6 = 0;
                i2 = 0;
                i3 = 0;
                int i7 = 0;
                while (i6 < length2) {
                    int i8 = iArr[i6];
                    int min3 = Math.min(i2, i - i8);
                    iArr2[i7] = min3;
                    int min4 = Math.min(mo51roundToPx0680j_4, (i - min3) - i8);
                    int i9 = iArr2[i7] + i8 + min4;
                    i6++;
                    i3 = min4;
                    i2 = i9;
                    i7++;
                }
            }
            int i10 = i2 - i3;
            Function2 function2 = this.alignment;
            if (function2 == null || i10 >= i) {
                return;
            }
            int intValue = ((Number) function2.invoke(Integer.valueOf(i - i10), layoutDirection)).intValue();
            int length3 = iArr2.length;
            for (int i11 = 0; i11 < length3; i11++) {
                iArr2[i11] = iArr2[i11] + intValue;
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpacedAligned)) {
                return false;
            }
            SpacedAligned spacedAligned = (SpacedAligned) obj;
            return Dp.m836equalsimpl0(this.space, spacedAligned.space) && this.rtlMirror == spacedAligned.rtlMirror && Intrinsics.areEqual(this.alignment, spacedAligned.alignment);
        }

        @Override // androidx.compose.foundation.layout.Arrangement.Horizontal, androidx.compose.foundation.layout.Arrangement.Vertical
        /* renamed from: getSpacing-D9Ej5fM */
        public final float mo94getSpacingD9Ej5fM() {
            return this.spacing;
        }

        public final int hashCode() {
            Dp.Companion companion = Dp.Companion;
            int m = TransitionData$$ExternalSyntheticOutline0.m(Float.hashCode(this.space) * 31, 31, this.rtlMirror);
            Function2 function2 = this.alignment;
            return m + (function2 == null ? 0 : function2.hashCode());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.rtlMirror ? "" : "Absolute");
            sb.append("Arrangement#spacedAligned(");
            BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.space, ", ", sb);
            sb.append(this.alignment);
            sb.append(')');
            return sb.toString();
        }

        private SpacedAligned(float f, boolean z, Function2 function2) {
            this.space = f;
            this.rtlMirror = z;
            this.alignment = function2;
            this.spacing = f;
        }

        @Override // androidx.compose.foundation.layout.Arrangement.Vertical
        public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
            arrange(density, i, iArr, LayoutDirection.Ltr, iArr2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Vertical {
        void arrange(Density density, int i, int[] iArr, int[] iArr2);

        /* renamed from: getSpacing-D9Ej5fM */
        default float mo94getSpacingD9Ej5fM() {
            float f = 0;
            Dp.Companion companion = Dp.Companion;
            return f;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.foundation.layout.Arrangement$Start$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.compose.foundation.layout.Arrangement$End$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.foundation.layout.Arrangement$Top$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.compose.foundation.layout.Arrangement$Bottom$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.compose.foundation.layout.Arrangement$Center$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [androidx.compose.foundation.layout.Arrangement$SpaceBetween$1] */
    static {
        new HorizontalOrVertical() { // from class: androidx.compose.foundation.layout.Arrangement$SpaceEvenly$1
            public final float spacing;

            {
                Dp.Companion companion = Dp.Companion;
                this.spacing = 0;
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
            public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                if (layoutDirection == LayoutDirection.Ltr) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceEvenly$foundation_layout(i, iArr, iArr2, false);
                } else {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceEvenly$foundation_layout(i, iArr, iArr2, true);
                }
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal, androidx.compose.foundation.layout.Arrangement.Vertical
            /* renamed from: getSpacing-D9Ej5fM */
            public final float mo94getSpacingD9Ej5fM() {
                return this.spacing;
            }

            public final String toString() {
                return "Arrangement#SpaceEvenly";
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Vertical
            public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeSpaceEvenly$foundation_layout(i, iArr, iArr2, false);
            }
        };
        SpaceBetween = new HorizontalOrVertical() { // from class: androidx.compose.foundation.layout.Arrangement$SpaceBetween$1
            public final float spacing;

            {
                Dp.Companion companion = Dp.Companion;
                this.spacing = 0;
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
            public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                if (layoutDirection == LayoutDirection.Ltr) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceBetween$foundation_layout(i, iArr, iArr2, false);
                } else {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceBetween$foundation_layout(i, iArr, iArr2, true);
                }
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal, androidx.compose.foundation.layout.Arrangement.Vertical
            /* renamed from: getSpacing-D9Ej5fM */
            public final float mo94getSpacingD9Ej5fM() {
                return this.spacing;
            }

            public final String toString() {
                return "Arrangement#SpaceBetween";
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Vertical
            public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeSpaceBetween$foundation_layout(i, iArr, iArr2, false);
            }
        };
        new HorizontalOrVertical() { // from class: androidx.compose.foundation.layout.Arrangement$SpaceAround$1
            public final float spacing;

            {
                Dp.Companion companion = Dp.Companion;
                this.spacing = 0;
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal
            public final void arrange(Density density, int i, int[] iArr, LayoutDirection layoutDirection, int[] iArr2) {
                if (layoutDirection == LayoutDirection.Ltr) {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceAround$foundation_layout(i, iArr, iArr2, false);
                } else {
                    Arrangement.INSTANCE.getClass();
                    Arrangement.placeSpaceAround$foundation_layout(i, iArr, iArr2, true);
                }
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Horizontal, androidx.compose.foundation.layout.Arrangement.Vertical
            /* renamed from: getSpacing-D9Ej5fM */
            public final float mo94getSpacingD9Ej5fM() {
                return this.spacing;
            }

            public final String toString() {
                return "Arrangement#SpaceAround";
            }

            @Override // androidx.compose.foundation.layout.Arrangement.Vertical
            public final void arrange(Density density, int i, int[] iArr, int[] iArr2) {
                Arrangement.INSTANCE.getClass();
                Arrangement.placeSpaceAround$foundation_layout(i, iArr, iArr2, false);
            }
        };
    }

    private Arrangement() {
    }

    public static SpacedAligned aligned(final BiasAlignment.Horizontal horizontal) {
        Dp.Companion companion = Dp.Companion;
        return new SpacedAligned(0, true, new Function2() { // from class: androidx.compose.foundation.layout.Arrangement$aligned$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(Alignment.Horizontal.this.align(0, ((Number) obj).intValue(), (LayoutDirection) obj2));
            }
        }, null);
    }

    public static void placeCenter$foundation_layout(int i, int[] iArr, int[] iArr2, boolean z) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            i3 += i4;
        }
        float f = (i - i3) / 2;
        if (!z) {
            int length = iArr.length;
            int i5 = 0;
            while (i2 < length) {
                int i6 = iArr[i2];
                iArr2[i5] = Math.round(f);
                f += i6;
                i2++;
                i5++;
            }
            return;
        }
        int length2 = iArr.length;
        while (true) {
            length2--;
            if (-1 >= length2) {
                return;
            }
            int i7 = iArr[length2];
            iArr2[length2] = Math.round(f);
            f += i7;
        }
    }

    public static void placeLeftOrTop$foundation_layout(int[] iArr, int[] iArr2, boolean z) {
        int i = 0;
        if (!z) {
            int length = iArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i < length) {
                int i4 = iArr[i];
                iArr2[i2] = i3;
                i3 += i4;
                i++;
                i2++;
            }
            return;
        }
        int length2 = iArr.length;
        while (true) {
            length2--;
            if (-1 >= length2) {
                return;
            }
            int i5 = iArr[length2];
            iArr2[length2] = i;
            i += i5;
        }
    }

    public static void placeRightOrBottom$foundation_layout(int i, int[] iArr, int[] iArr2, boolean z) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            i3 += i4;
        }
        int i5 = i - i3;
        if (!z) {
            int length = iArr.length;
            int i6 = 0;
            while (i2 < length) {
                int i7 = iArr[i2];
                iArr2[i6] = i5;
                i5 += i7;
                i2++;
                i6++;
            }
            return;
        }
        int length2 = iArr.length;
        while (true) {
            length2--;
            if (-1 >= length2) {
                return;
            }
            int i8 = iArr[length2];
            iArr2[length2] = i5;
            i5 += i8;
        }
    }

    public static void placeSpaceAround$foundation_layout(int i, int[] iArr, int[] iArr2, boolean z) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            i3 += i4;
        }
        float length = iArr.length == 0 ? 0.0f : (i - i3) / iArr.length;
        float f = length / 2;
        if (!z) {
            int length2 = iArr.length;
            int i5 = 0;
            while (i2 < length2) {
                int i6 = iArr[i2];
                iArr2[i5] = Math.round(f);
                f += i6 + length;
                i2++;
                i5++;
            }
            return;
        }
        int length3 = iArr.length;
        while (true) {
            length3--;
            if (-1 >= length3) {
                return;
            }
            int i7 = iArr[length3];
            iArr2[length3] = Math.round(f);
            f += i7 + length;
        }
    }

    public static void placeSpaceBetween$foundation_layout(int i, int[] iArr, int[] iArr2, boolean z) {
        if (iArr.length == 0) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            i3 += i4;
        }
        float max = (i - i3) / Math.max(iArr.length - 1, 1);
        float f = (z && iArr.length == 1) ? max : 0.0f;
        if (z) {
            for (int length = iArr.length - 1; -1 < length; length--) {
                int i5 = iArr[length];
                iArr2[length] = Math.round(f);
                f += i5 + max;
            }
            return;
        }
        int length2 = iArr.length;
        int i6 = 0;
        while (i2 < length2) {
            int i7 = iArr[i2];
            iArr2[i6] = Math.round(f);
            f += i7 + max;
            i2++;
            i6++;
        }
    }

    public static void placeSpaceEvenly$foundation_layout(int i, int[] iArr, int[] iArr2, boolean z) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            i3 += i4;
        }
        float length = (i - i3) / (iArr.length + 1);
        if (z) {
            float f = length;
            for (int length2 = iArr.length - 1; -1 < length2; length2--) {
                int i5 = iArr[length2];
                iArr2[length2] = Math.round(f);
                f += i5 + length;
            }
            return;
        }
        int length3 = iArr.length;
        float f2 = length;
        int i6 = 0;
        while (i2 < length3) {
            int i7 = iArr[i2];
            iArr2[i6] = Math.round(f2);
            f2 += i7 + length;
            i2++;
            i6++;
        }
    }

    /* renamed from: spacedBy-0680j_4, reason: not valid java name */
    public static SpacedAligned m91spacedBy0680j_4(float f) {
        return new SpacedAligned(f, true, new Function2() { // from class: androidx.compose.foundation.layout.Arrangement$spacedBy$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Alignment.Companion.getClass();
                return Integer.valueOf(Alignment.Companion.Start.align(0, ((Number) obj).intValue(), (LayoutDirection) obj2));
            }
        }, null);
    }

    /* renamed from: spacedBy-D5KLDUw, reason: not valid java name */
    public static SpacedAligned m92spacedByD5KLDUw(float f, final BiasAlignment.Horizontal horizontal) {
        return new SpacedAligned(f, true, new Function2() { // from class: androidx.compose.foundation.layout.Arrangement$spacedBy$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(Alignment.Horizontal.this.align(0, ((Number) obj).intValue(), (LayoutDirection) obj2));
            }
        }, null);
    }

    /* renamed from: spacedBy-D5KLDUw, reason: not valid java name */
    public static SpacedAligned m93spacedByD5KLDUw(float f, final BiasAlignment.Vertical vertical) {
        return new SpacedAligned(f, false, new Function2() { // from class: androidx.compose.foundation.layout.Arrangement$spacedBy$3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((BiasAlignment.Vertical) Alignment.Vertical.this).align(0, ((Number) obj).intValue()));
            }
        }, null);
    }
}
