package androidx.compose.foundation.text;

import androidx.compose.foundation.internal.InlineClassHelperKt;

/* loaded from: classes.dex */
public abstract class HeightInLinesModifierKt {
    public static final void validateMinMaxLines(int i, int i2) {
        if (!(i > 0 && i2 > 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("both minLines " + i + " and maxLines " + i2 + " must be greater than zero");
        }
        if (i <= i2) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("minLines " + i + " must be less than or equal to maxLines " + i2);
    }
}
