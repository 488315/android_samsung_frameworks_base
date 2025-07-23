package com.google.android.setupcompat.internal;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Validations {
    private Validations() {
        throw new AssertionError("Should not be instantiated");
    }

    public static void assertLengthInRange(int i, int i2, String str, String str2) {
        Preconditions.checkNotNull(str, str2.concat(" cannot be null."));
        int length = str.length();
        boolean z = length <= i2 && length >= i;
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(i, "Length of ", str2, " should be in the range [", "-");
        m888m.append(i2);
        m888m.append("]");
        Preconditions.checkArgument(m888m.toString(), z);
    }
}
