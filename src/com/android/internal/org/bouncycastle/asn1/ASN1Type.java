package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes5.dex */
abstract class ASN1Type {
    final Class javaClass;

    public final boolean equals(Object obj) {
        return this == obj;
    }

    ASN1Type(Class cls) {
        this.javaClass = cls;
    }

    final Class getJavaClass() {
        return this.javaClass;
    }

    public final int hashCode() {
        return super.hashCode();
    }
}
