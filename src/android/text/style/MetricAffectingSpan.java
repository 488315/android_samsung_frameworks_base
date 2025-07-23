package android.text.style;

import android.text.TextPaint;

/* loaded from: classes4.dex */
public abstract class MetricAffectingSpan extends CharacterStyle implements UpdateLayout {
    @Override // android.text.style.CharacterStyle
    public MetricAffectingSpan getUnderlying() {
        return this;
    }

    public abstract void updateMeasureState(TextPaint textPaint);

    static class Passthrough extends MetricAffectingSpan {
        private MetricAffectingSpan mStyle;

        Passthrough(MetricAffectingSpan metricAffectingSpan) {
            this.mStyle = metricAffectingSpan;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            this.mStyle.updateDrawState(textPaint);
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            this.mStyle.updateMeasureState(textPaint);
        }

        @Override // android.text.style.MetricAffectingSpan, android.text.style.CharacterStyle
        public MetricAffectingSpan getUnderlying() {
            return this.mStyle.getUnderlying();
        }
    }
}
