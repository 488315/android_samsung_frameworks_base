package android.text.style;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/* loaded from: classes4.dex */
public abstract class CharacterStyle {
    public CharacterStyle getUnderlying() {
        return this;
    }

    public abstract void updateDrawState(TextPaint textPaint);

    public static CharacterStyle wrap(CharacterStyle characterStyle) {
        if (characterStyle instanceof MetricAffectingSpan) {
            return new MetricAffectingSpan.Passthrough((MetricAffectingSpan) characterStyle);
        }
        return new Passthrough(characterStyle);
    }

    private static class Passthrough extends CharacterStyle {
        private CharacterStyle mStyle;

        public Passthrough(CharacterStyle characterStyle) {
            this.mStyle = characterStyle;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            this.mStyle.updateDrawState(textPaint);
        }

        @Override // android.text.style.CharacterStyle
        public CharacterStyle getUnderlying() {
            return this.mStyle.getUnderlying();
        }
    }
}
