package androidx.compose.ui.graphics.colorspace;

import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.colorspace.ColorModel;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ColorSpace {
    public final int id;
    public final long model;
    public final String name;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ ColorSpace(String str, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, j, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColorSpace colorSpace = (ColorSpace) obj;
        if (this.id == colorSpace.id && Intrinsics.areEqual(this.name, colorSpace.name)) {
            return ColorModel.m508equalsimpl0(this.model, colorSpace.model);
        }
        return false;
    }

    public abstract float[] fromXyz(float[] fArr);

    public abstract float getMaxValue(int i);

    public abstract float getMinValue(int i);

    public int hashCode() {
        int hashCode = this.name.hashCode() * 31;
        ColorModel.Companion companion = ColorModel.Companion;
        return MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.model) + this.id;
    }

    public boolean isSrgb() {
        return false;
    }

    public final String toString() {
        return this.name + " (id=" + this.id + ", model=" + ((Object) ColorModel.m509toStringimpl(this.model)) + ')';
    }

    public long toXy$ui_graphics_release(float f, float f2, float f3) {
        float[] xyz = toXyz(new float[]{f, f2, f3});
        return (Float.floatToRawIntBits(xyz[0]) << 32) | (4294967295L & Float.floatToRawIntBits(xyz[1]));
    }

    public abstract float[] toXyz(float[] fArr);

    public float toZ$ui_graphics_release(float f, float f2, float f3) {
        return toXyz(new float[]{f, f2, f3})[2];
    }

    /* renamed from: xyzaToColor-JlNiLsg$ui_graphics_release, reason: not valid java name */
    public long mo510xyzaToColorJlNiLsg$ui_graphics_release(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        ColorModel.Companion companion = ColorModel.Companion;
        float[] fArr = new float[(int) (this.model >> 32)];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        float[] fromXyz = fromXyz(fArr);
        return ColorKt.Color(fromXyz[0], fromXyz[1], fromXyz[2], f4, colorSpace);
    }

    public /* synthetic */ ColorSpace(String str, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, j);
    }

    private ColorSpace(String str, long j, int i) {
        this.name = str;
        this.model = j;
        this.id = i;
        if (str.length() == 0) {
            throw new IllegalArgumentException("The name of a color space cannot be null and must contain at least 1 character");
        }
        if (i < -1 || i > 63) {
            throw new IllegalArgumentException("The id must be between -1 and 63");
        }
    }

    private ColorSpace(String str, long j) {
        this(str, j, -1, null);
    }
}
