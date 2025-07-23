package androidx.compose.foundation.text;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.input.OffsetMapping;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.text.input.VisualTransformation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ValidatingOffsetMappingKt {
    public static final OffsetMapping ValidatingEmptyOffsetMappingIdentity;

    static {
        OffsetMapping.Companion.getClass();
        ValidatingEmptyOffsetMappingIdentity = new ValidatingOffsetMapping(OffsetMapping.Companion.Identity, 0, 0);
    }

    public static final TransformedText filterWithValidation(VisualTransformation visualTransformation, AnnotatedString annotatedString) {
        OffsetMapping offsetMapping;
        TransformedText filter = visualTransformation.filter(annotatedString);
        int length = annotatedString.text.length();
        AnnotatedString annotatedString2 = filter.text;
        int length2 = annotatedString2.text.length();
        int min = Math.min(length, 100);
        int i = 0;
        while (true) {
            offsetMapping = filter.offsetMapping;
            if (i >= min) {
                break;
            }
            validateOriginalToTransformed(offsetMapping.originalToTransformed(i), length2, i);
            i++;
        }
        validateOriginalToTransformed(offsetMapping.originalToTransformed(length), length2, length);
        int min2 = Math.min(length2, 100);
        for (int i2 = 0; i2 < min2; i2++) {
            validateTransformedToOriginal(offsetMapping.transformedToOriginal(i2), length, i2);
        }
        validateTransformedToOriginal(offsetMapping.transformedToOriginal(length2), length, length2);
        return new TransformedText(annotatedString2, new ValidatingOffsetMapping(offsetMapping, annotatedString.text.length(), annotatedString2.text.length()));
    }

    public static final void validateOriginalToTransformed(int i, int i2, int i3) {
        boolean z = false;
        if (i >= 0 && i <= i2) {
            z = true;
        }
        if (z) {
            return;
        }
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i, "OffsetMapping.originalToTransformed returned invalid mapping: ", " -> ", " is not in range of transformed text [0, ");
        m.append(i2);
        m.append(']');
        InlineClassHelperKt.throwIllegalStateException(m.toString());
    }

    public static final void validateTransformedToOriginal(int i, int i2, int i3) {
        boolean z = false;
        if (i >= 0 && i <= i2) {
            z = true;
        }
        if (z) {
            return;
        }
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i3, i, "OffsetMapping.transformedToOriginal returned invalid mapping: ", " -> ", " is not in range of original text [0, ");
        m.append(i2);
        m.append(']');
        InlineClassHelperKt.throwIllegalStateException(m.toString());
    }
}
