package androidx.compose.ui.graphics.drawscope;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.PathEffect;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Stroke extends DrawStyle {
    public static final Companion Companion = new Companion(null);
    public final int cap;
    public final int join;
    public final float miter;
    public final PathEffect pathEffect;
    public final float width;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
    }

    public /* synthetic */ Stroke(float f, float f2, int i, int i2, PathEffect pathEffect, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, i, i2, pathEffect);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Stroke)) {
            return false;
        }
        Stroke stroke = (Stroke) obj;
        if (this.width == stroke.width && this.miter == stroke.miter) {
            int i = stroke.cap;
            StrokeCap.Companion companion = StrokeCap.Companion;
            if (this.cap == i) {
                int i2 = stroke.join;
                StrokeJoin.Companion companion2 = StrokeJoin.Companion;
                return this.join == i2 && Intrinsics.areEqual(this.pathEffect, stroke.pathEffect);
            }
        }
        return false;
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.miter, Float.hashCode(this.width) * 31, 31);
        StrokeCap.Companion companion = StrokeCap.Companion;
        int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.cap, m, 31);
        StrokeJoin.Companion companion2 = StrokeJoin.Companion;
        int m3 = ReorderTile$$ExternalSyntheticOutline0.m(this.join, m2, 31);
        PathEffect pathEffect = this.pathEffect;
        return m3 + (pathEffect != null ? pathEffect.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Stroke(width=");
        sb.append(this.width);
        sb.append(", miter=");
        sb.append(this.miter);
        sb.append(", cap=");
        String str = C2paManifestList.UNKNOWN_VALUE;
        int i = this.cap;
        sb.append((Object) (i == 0 ? "Butt" : i == StrokeCap.Round ? "Round" : i == StrokeCap.Square ? "Square" : C2paManifestList.UNKNOWN_VALUE));
        sb.append(", join=");
        int i2 = this.join;
        if (i2 == 0) {
            str = "Miter";
        } else if (i2 == StrokeJoin.Round) {
            str = "Round";
        } else if (i2 == StrokeJoin.Bevel) {
            str = "Bevel";
        }
        sb.append((Object) str);
        sb.append(", pathEffect=");
        sb.append(this.pathEffect);
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ Stroke(float f, float f2, int i, int i2, PathEffect pathEffect, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0.0f : f, (i3 & 2) != 0 ? 4.0f : f2, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2, (i3 & 16) != 0 ? null : pathEffect, null);
    }

    private Stroke(float f, float f2, int i, int i2, PathEffect pathEffect) {
        super(null);
        this.width = f;
        this.miter = f2;
        this.cap = i;
        this.join = i2;
        this.pathEffect = pathEffect;
    }
}
