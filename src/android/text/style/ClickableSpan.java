package android.text.style;

import android.text.TextPaint;
import android.view.View;

/* loaded from: classes4.dex */
public abstract class ClickableSpan extends CharacterStyle implements UpdateAppearance {
    private static int sIdCounter;
    private int mId;

    public abstract void onClick(View view);

    public ClickableSpan() {
        int i = sIdCounter;
        sIdCounter = i + 1;
        this.mId = i;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(textPaint.linkColor);
        textPaint.setUnderlineText(true);
    }

    public int getId() {
        return this.mId;
    }

    public String toString() {
        return "ClickableSpan{}";
    }
}
