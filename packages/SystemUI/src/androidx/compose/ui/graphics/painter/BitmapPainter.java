package androidx.compose.ui.graphics.painter;

import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.FilterQuality;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BitmapPainter extends Painter {
    public float alpha;
    public ColorFilter colorFilter;
    public int filterQuality;
    public final ImageBitmap image;
    public final long size;
    public final long srcOffset;
    public final long srcSize;

    public /* synthetic */ BitmapPainter(ImageBitmap imageBitmap, long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(imageBitmap, j, j2);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyAlpha(float f) {
        this.alpha = f;
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BitmapPainter)) {
            return false;
        }
        BitmapPainter bitmapPainter = (BitmapPainter) obj;
        if (!Intrinsics.areEqual(this.image, bitmapPainter.image) || !IntOffset.m849equalsimpl0(this.srcOffset, bitmapPainter.srcOffset) || !IntSize.m861equalsimpl0(this.srcSize, bitmapPainter.srcSize)) {
            return false;
        }
        int i = this.filterQuality;
        int i2 = bitmapPainter.filterQuality;
        FilterQuality.Companion companion = FilterQuality.Companion;
        return i == i2;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc, reason: not valid java name */
    public final long mo561getIntrinsicSizeNHjbRc() {
        return IntSizeKt.m864toSizeozmzZPI(this.size);
    }

    public final int hashCode() {
        int hashCode = this.image.hashCode() * 31;
        IntOffset.Companion companion = IntOffset.Companion;
        int m = MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.srcOffset);
        IntSize.Companion companion2 = IntSize.Companion;
        int m2 = MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.srcSize);
        int i = this.filterQuality;
        FilterQuality.Companion companion3 = FilterQuality.Companion;
        return Integer.hashCode(i) + m2;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        IntSize.Companion companion = IntSize.Companion;
        DrawScope.m533drawImageAZ2fEMs$default(drawScope, this.image, this.srcOffset, this.srcSize, (Math.round(Float.intBitsToFloat((int) (drawScope.mo545getSizeNHjbRc() >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (drawScope.mo545getSizeNHjbRc() & 4294967295L))) & 4294967295L), this.alpha, this.colorFilter, this.filterQuality, 328);
    }

    public final String toString() {
        return "BitmapPainter(image=" + this.image + ", srcOffset=" + ((Object) IntOffset.m852toStringimpl(this.srcOffset)) + ", srcSize=" + ((Object) IntSize.m862toStringimpl(this.srcSize)) + ", filterQuality=" + ((Object) FilterQuality.m472toStringimpl(this.filterQuality)) + ')';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BitmapPainter(androidx.compose.ui.graphics.ImageBitmap r8, long r9, long r11, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 2
            if (r14 == 0) goto Lb
            androidx.compose.ui.unit.IntOffset$Companion r9 = androidx.compose.ui.unit.IntOffset.Companion
            r9.getClass()
            r9 = 0
        Lb:
            r2 = r9
            r9 = r13 & 4
            if (r9 == 0) goto L30
            r9 = r8
            androidx.compose.ui.graphics.AndroidImageBitmap r9 = (androidx.compose.ui.graphics.AndroidImageBitmap) r9
            android.graphics.Bitmap r9 = r9.bitmap
            int r9 = r9.getWidth()
            r10 = r8
            androidx.compose.ui.graphics.AndroidImageBitmap r10 = (androidx.compose.ui.graphics.AndroidImageBitmap) r10
            android.graphics.Bitmap r10 = r10.bitmap
            int r10 = r10.getHeight()
            long r11 = (long) r9
            r9 = 32
            long r11 = r11 << r9
            long r9 = (long) r10
            r13 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r9 = r9 & r13
            long r11 = r11 | r9
            androidx.compose.ui.unit.IntSize$Companion r9 = androidx.compose.ui.unit.IntSize.Companion
        L30:
            r4 = r11
            r6 = 0
            r0 = r7
            r1 = r8
            r0.<init>(r1, r2, r4, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.painter.BitmapPainter.<init>(androidx.compose.ui.graphics.ImageBitmap, long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private BitmapPainter(ImageBitmap imageBitmap, long j, long j2) {
        int i;
        int i2;
        this.image = imageBitmap;
        this.srcOffset = j;
        this.srcSize = j2;
        FilterQuality.Companion.getClass();
        this.filterQuality = FilterQuality.Low;
        IntOffset.Companion companion = IntOffset.Companion;
        if (((int) (j >> 32)) >= 0 && ((int) (j & 4294967295L)) >= 0 && (i = (int) (j2 >> 32)) >= 0 && (i2 = (int) (j2 & 4294967295L)) >= 0) {
            AndroidImageBitmap androidImageBitmap = (AndroidImageBitmap) imageBitmap;
            if (i <= androidImageBitmap.bitmap.getWidth() && i2 <= androidImageBitmap.bitmap.getHeight()) {
                this.size = j2;
                this.alpha = 1.0f;
                return;
            }
        }
        throw new IllegalArgumentException("Failed requirement.");
    }
}
