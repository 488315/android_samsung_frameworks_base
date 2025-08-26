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
        if (!Intrinsics.areEqual(this.image, bitmapPainter.image) || !IntOffset.m851equalsimpl0(this.srcOffset, bitmapPainter.srcOffset) || !IntSize.m863equalsimpl0(this.srcSize, bitmapPainter.srcSize)) {
            return false;
        }
        int i = this.filterQuality;
        int i2 = bitmapPainter.filterQuality;
        FilterQuality.Companion companion = FilterQuality.Companion;
        return i == i2;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc, reason: not valid java name */
    public final long mo563getIntrinsicSizeNHjbRc() {
        return IntSizeKt.m866toSizeozmzZPI(this.size);
    }

    public final int hashCode() {
        int iHashCode = this.image.hashCode() * 31;
        IntOffset.Companion companion = IntOffset.Companion;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(iHashCode, 31, this.srcOffset);
        IntSize.Companion companion2 = IntSize.Companion;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.srcSize);
        int i = this.filterQuality;
        FilterQuality.Companion companion3 = FilterQuality.Companion;
        return Integer.hashCode(i) + iM2;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        IntSize.Companion companion = IntSize.Companion;
        DrawScope.m535drawImageAZ2fEMs$default(drawScope, this.image, this.srcOffset, this.srcSize, (Math.round(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (drawScope.mo547getSizeNHjbRc() & 4294967295L))) & 4294967295L), this.alpha, this.colorFilter, this.filterQuality, 328);
    }

    public final String toString() {
        return "BitmapPainter(image=" + this.image + ", srcOffset=" + ((Object) IntOffset.m854toStringimpl(this.srcOffset)) + ", srcSize=" + ((Object) IntSize.m864toStringimpl(this.srcSize)) + ", filterQuality=" + ((Object) FilterQuality.m474toStringimpl(this.filterQuality)) + ')';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public BitmapPainter(ImageBitmap imageBitmap, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            IntOffset.Companion.getClass();
            j = 0;
        }
        long j3 = j;
        if ((i & 4) != 0) {
            j2 = (((AndroidImageBitmap) imageBitmap).bitmap.getWidth() << 32) | (((AndroidImageBitmap) imageBitmap).bitmap.getHeight() & 4294967295L);
            IntSize.Companion companion = IntSize.Companion;
        }
        this(imageBitmap, j3, j2, null);
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
