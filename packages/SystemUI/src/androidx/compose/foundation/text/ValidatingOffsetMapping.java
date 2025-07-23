package androidx.compose.foundation.text;

import androidx.compose.ui.text.input.OffsetMapping;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ValidatingOffsetMapping implements OffsetMapping {
    public final OffsetMapping delegate;
    public final int originalLength;
    public final int transformedLength;

    public ValidatingOffsetMapping(OffsetMapping offsetMapping, int i, int i2) {
        this.delegate = offsetMapping;
        this.originalLength = i;
        this.transformedLength = i2;
    }

    @Override // androidx.compose.ui.text.input.OffsetMapping
    public final int originalToTransformed(int i) {
        int originalToTransformed = this.delegate.originalToTransformed(i);
        if (i >= 0 && i <= this.originalLength) {
            ValidatingOffsetMappingKt.validateOriginalToTransformed(originalToTransformed, this.transformedLength, i);
        }
        return originalToTransformed;
    }

    @Override // androidx.compose.ui.text.input.OffsetMapping
    public final int transformedToOriginal(int i) {
        int transformedToOriginal = this.delegate.transformedToOriginal(i);
        if (i >= 0 && i <= this.transformedLength) {
            ValidatingOffsetMappingKt.validateTransformedToOriginal(transformedToOriginal, this.originalLength, i);
        }
        return transformedToOriginal;
    }
}
