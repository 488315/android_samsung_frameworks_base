package androidx.compose.ui.text.android;

import android.text.Layout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class StaticLayoutParams {
    public final Layout.Alignment alignment;
    public final int breakStrategy;
    public final TextUtils.TruncateAt ellipsize;
    public final int ellipsizedWidth;
    public final int end;
    public final int hyphenationFrequency;
    public final boolean includePadding;
    public final int justificationMode;
    public final int[] leftIndents;
    public final int lineBreakStyle;
    public final int lineBreakWordStyle;
    public final float lineSpacingExtra;
    public final float lineSpacingMultiplier;
    public final int maxLines;
    public final TextPaint paint;
    public final int[] rightIndents;
    public final int start;
    public final CharSequence text;
    public final TextDirectionHeuristic textDir;
    public final boolean useFallbackLineSpacing;
    public final int width;

    public StaticLayoutParams(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i4, TextUtils.TruncateAt truncateAt, int i5, float f, float f2, int i6, boolean z, boolean z2, int i7, int i8, int i9, int i10, int[] iArr, int[] iArr2) {
        this.text = charSequence;
        this.start = i;
        this.end = i2;
        this.paint = textPaint;
        this.width = i3;
        this.textDir = textDirectionHeuristic;
        this.alignment = alignment;
        this.maxLines = i4;
        this.ellipsize = truncateAt;
        this.ellipsizedWidth = i5;
        this.lineSpacingMultiplier = f;
        this.lineSpacingExtra = f2;
        this.justificationMode = i6;
        this.includePadding = z;
        this.useFallbackLineSpacing = z2;
        this.breakStrategy = i7;
        this.lineBreakStyle = i8;
        this.lineBreakWordStyle = i9;
        this.hyphenationFrequency = i10;
        this.leftIndents = iArr;
        this.rightIndents = iArr2;
        if (!(i >= 0 && i <= i2)) {
            InlineClassHelperKt.throwIllegalArgumentException("invalid start value");
        }
        if (!(i2 >= 0 && i2 <= charSequence.length())) {
            InlineClassHelperKt.throwIllegalArgumentException("invalid end value");
        }
        if (!(i4 >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("invalid maxLines value");
        }
        if (!(i3 >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("invalid width value");
        }
        if (!(i5 >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("invalid ellipsizedWidth value");
        }
        if (f >= 0.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("invalid lineSpacingMultiplier value");
    }

    public /* synthetic */ StaticLayoutParams(CharSequence charSequence, int i, int i2, TextPaint textPaint, int i3, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i4, TextUtils.TruncateAt truncateAt, int i5, float f, float f2, int i6, boolean z, boolean z2, int i7, int i8, int i9, int i10, int[] iArr, int[] iArr2, int i11, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, (i11 & 2) != 0 ? 0 : i, i2, textPaint, i3, textDirectionHeuristic, alignment, i4, truncateAt, i5, f, f2, i6, z, z2, i7, i8, i9, i10, iArr, iArr2);
    }
}
