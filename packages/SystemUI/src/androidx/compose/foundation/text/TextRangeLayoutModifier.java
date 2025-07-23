package androidx.compose.foundation.text;

import androidx.compose.ui.layout.ParentDataModifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextRangeLayoutModifier implements ParentDataModifier {
    public final TextRangeScopeMeasurePolicy measurePolicy;

    public TextRangeLayoutModifier(TextRangeScopeMeasurePolicy textRangeScopeMeasurePolicy) {
        this.measurePolicy = textRangeScopeMeasurePolicy;
    }

    @Override // androidx.compose.ui.layout.ParentDataModifier
    public final Object modifyParentData() {
        return this;
    }
}
