package androidx.compose.ui.text.platform;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidParagraphHelper_androidKt {
    public static final AndroidParagraphHelper_androidKt$NoopSpan$1 NoopSpan = new CharacterStyle() { // from class: androidx.compose.ui.text.platform.AndroidParagraphHelper_androidKt$NoopSpan$1
        @Override // android.text.style.CharacterStyle
        public final void updateDrawState(TextPaint textPaint) {
        }
    };
}
