package androidx.compose.ui.text.platform;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

/* loaded from: classes.dex */
public abstract class AndroidParagraphHelper_androidKt {
    public static final AndroidParagraphHelper_androidKt$NoopSpan$1 NoopSpan = new CharacterStyle() { // from class: androidx.compose.ui.text.platform.AndroidParagraphHelper_androidKt$NoopSpan$1
        @Override // android.text.style.CharacterStyle
        public final void updateDrawState(TextPaint textPaint) {
        }
    };
}
