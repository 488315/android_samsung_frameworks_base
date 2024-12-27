package com.android.internal.org.bouncycastle.math.field;

public interface PolynomialExtensionField extends ExtensionField {
    Polynomial getMinimalPolynomial();
}
