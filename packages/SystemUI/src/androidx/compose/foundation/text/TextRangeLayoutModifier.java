package androidx.compose.foundation.text;

import androidx.compose.ui.layout.ParentDataModifier;

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
