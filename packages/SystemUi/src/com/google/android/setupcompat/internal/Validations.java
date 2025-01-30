package com.google.android.setupcompat.internal;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Validations {
    private Validations() {
        throw new AssertionError("Should not be instantiated");
    }

    public static void assertLengthInRange(int i, int i2, String str, String str2) {
        String format = String.format("%s cannot be null.", str2);
        if (str == null) {
            throw new NullPointerException(format);
        }
        int length = str.length();
        Preconditions.checkArgument(String.format("Length of %s should be in the range [%s-%s]", str2, Integer.valueOf(i), Integer.valueOf(i2)), length <= i2 && length >= i);
    }
}
