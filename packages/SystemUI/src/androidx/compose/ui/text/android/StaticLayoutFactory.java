package androidx.compose.ui.text.android;

import android.graphics.text.LineBreakConfig;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;

/* loaded from: classes.dex */
public final class StaticLayoutFactory {
    public static final StaticLayoutFactory INSTANCE = new StaticLayoutFactory();
    public static final StaticLayoutFactory23 delegate = new StaticLayoutFactory23();

    private StaticLayoutFactory() {
    }

    public static StaticLayout create(CharSequence charSequence, TextPaint textPaint, int i, int i2, TextDirectionHeuristic textDirectionHeuristic, Layout.Alignment alignment, int i3, TextUtils.TruncateAt truncateAt, int i4, float f, float f2, int i5, boolean z, boolean z2, int i6, int i7, int i8, int i9, int[] iArr, int[] iArr2) {
        StaticLayoutParams staticLayoutParams = new StaticLayoutParams(charSequence, 0, i2, textPaint, i, textDirectionHeuristic, alignment, i3, truncateAt, i4, f, f2, i5, z, z2, i6, i7, i8, i9, iArr, iArr2);
        delegate.getClass();
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(staticLayoutParams.text, staticLayoutParams.start, staticLayoutParams.end, staticLayoutParams.paint, staticLayoutParams.width);
        builderObtain.setTextDirection(staticLayoutParams.textDir);
        builderObtain.setAlignment(staticLayoutParams.alignment);
        builderObtain.setMaxLines(staticLayoutParams.maxLines);
        builderObtain.setEllipsize(staticLayoutParams.ellipsize);
        builderObtain.setEllipsizedWidth(staticLayoutParams.ellipsizedWidth);
        builderObtain.setLineSpacing(staticLayoutParams.lineSpacingExtra, staticLayoutParams.lineSpacingMultiplier);
        builderObtain.setIncludePad(staticLayoutParams.includePadding);
        builderObtain.setBreakStrategy(staticLayoutParams.breakStrategy);
        builderObtain.setHyphenationFrequency(staticLayoutParams.hyphenationFrequency);
        builderObtain.setIndents(staticLayoutParams.leftIndents, staticLayoutParams.rightIndents);
        int i10 = StaticLayoutFactory26.$r8$clinit;
        builderObtain.setJustificationMode(staticLayoutParams.justificationMode);
        int i11 = StaticLayoutFactory28.$r8$clinit;
        builderObtain.setUseLineSpacingFromFallbacks(staticLayoutParams.useFallbackLineSpacing);
        int i12 = StaticLayoutFactory33.$r8$clinit;
        builderObtain.setLineBreakConfig(new LineBreakConfig.Builder().setLineBreakStyle(staticLayoutParams.lineBreakStyle).setLineBreakWordStyle(staticLayoutParams.lineBreakWordStyle).build());
        return builderObtain.build();
    }
}
