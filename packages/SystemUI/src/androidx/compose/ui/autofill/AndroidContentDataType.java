package androidx.compose.ui.autofill;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidContentDataType implements ContentDataType {
    public final int androidAutofillType;

    private /* synthetic */ AndroidContentDataType(int i) {
        this.androidAutofillType = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ AndroidContentDataType m355boximpl(int i) {
        return new AndroidContentDataType(i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof AndroidContentDataType) {
            return this.androidAutofillType == ((AndroidContentDataType) obj).androidAutofillType;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.androidAutofillType);
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("AndroidContentDataType(androidAutofillType="), this.androidAutofillType, ')');
    }
}
