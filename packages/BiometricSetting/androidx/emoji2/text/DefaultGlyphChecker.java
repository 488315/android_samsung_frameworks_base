package androidx.emoji2.text;

import android.text.TextPaint;
import androidx.core.graphics.PaintCompat;
import androidx.emoji2.text.EmojiCompat;

/* loaded from: classes.dex */
final class DefaultGlyphChecker implements EmojiCompat.GlyphChecker {
    private static final ThreadLocal<StringBuilder> sStringBuilder = new ThreadLocal<>();
    private final TextPaint mTextPaint;

    DefaultGlyphChecker() {
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setTextSize(10.0f);
    }

    public final boolean hasGlyph(int i, int i2, CharSequence charSequence) {
        ThreadLocal<StringBuilder> threadLocal = sStringBuilder;
        if (threadLocal.get() == null) {
            threadLocal.set(new StringBuilder());
        }
        StringBuilder sb = threadLocal.get();
        sb.setLength(0);
        while (i < i2) {
            sb.append(charSequence.charAt(i));
            i++;
        }
        TextPaint textPaint = this.mTextPaint;
        String sb2 = sb.toString();
        int i3 = PaintCompat.$r8$clinit;
        return textPaint.hasGlyph(sb2);
    }
}
