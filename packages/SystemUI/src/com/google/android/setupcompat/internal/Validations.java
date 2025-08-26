package com.google.android.setupcompat.internal;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public final class Validations {
    private Validations() {
        throw new AssertionError("Should not be instantiated");
    }

    public static void assertLengthInRange(int i, int i2, String str, String str2) {
        Preconditions.checkNotNull(str, str2.concat(" cannot be null."));
        int length = str.length();
        boolean z = length <= i2 && length >= i;
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i, "Length of ", str2, " should be in the range [", "-");
        sbM890m.append(i2);
        sbM890m.append("]");
        Preconditions.checkArgument(sbM890m.toString(), z);
    }
}
