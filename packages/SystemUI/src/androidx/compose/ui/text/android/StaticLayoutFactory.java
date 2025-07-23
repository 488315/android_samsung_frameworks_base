package androidx.compose.ui.text.android;

import android.graphics.text.LineBreakConfig;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StaticLayoutFactory {
    public static final StaticLayoutFactory INSTANCE = new StaticLayoutFactory();
    public static final StaticLayoutFactory23 delegate = new StaticLayoutFactory23();

    private StaticLayoutFactory() {
    }

    public static StaticLayout create(CharSequence charSequence, TextPaint textPaint, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i3, TextUtils.TruncateAt truncateAt, int i4, float f, float f2, int i5, boolean z, boolean z2, int i6, int i7, int i8, int i9, int[] iArr, int[] iArr2) {
        StaticLayoutParams staticLayoutParams = new StaticLayoutParams(charSequence, 0, i2, textPaint, i, textDirectionHeuristic, alignment, i3, truncateAt, i4, f, f2, i5, z, z2, i6, i7, i8, i9, iArr, iArr2);
        delegate.getClass();
        StaticLayout.Builder obtain = StaticLayout.Builder.obtain(staticLayoutParams.text, staticLayoutParams.start, staticLayoutParams.end, staticLayoutParams.paint, staticLayoutParams.width);
        obtain.setTextDirection(staticLayoutParams.textDir);
        obtain.setAlignment(staticLayoutParams.alignment);
        obtain.setMaxLines(staticLayoutParams.maxLines);
        obtain.setEllipsize(staticLayoutParams.ellipsize);
        obtain.setEllipsizedWidth(staticLayoutParams.ellipsizedWidth);
        obtain.setLineSpacing(staticLayoutParams.lineSpacingExtra, staticLayoutParams.lineSpacingMultiplier);
        obtain.setIncludePad(staticLayoutParams.includePadding);
        obtain.setBreakStrategy(staticLayoutParams.breakStrategy);
        obtain.setHyphenationFrequency(staticLayoutParams.hyphenationFrequency);
        obtain.setIndents(staticLayoutParams.leftIndents, staticLayoutParams.rightIndents);
        int i10 = StaticLayoutFactory26.$r8$clinit;
        obtain.setJustificationMode(staticLayoutParams.justificationMode);
        int i11 = StaticLayoutFactory28.$r8$clinit;
        obtain.setUseLineSpacingFromFallbacks(staticLayoutParams.useFallbackLineSpacing);
        int i12 = StaticLayoutFactory33.$r8$clinit;
        obtain.setLineBreakConfig(new LineBreakConfig.Builder().setLineBreakStyle(staticLayoutParams.lineBreakStyle).setLineBreakWordStyle(staticLayoutParams.lineBreakWordStyle).build());
        return obtain.build();
    }
}
