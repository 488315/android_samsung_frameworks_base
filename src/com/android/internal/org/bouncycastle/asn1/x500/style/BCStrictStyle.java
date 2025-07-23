package com.android.internal.org.bouncycastle.asn1.x500.style;

import com.android.internal.org.bouncycastle.asn1.x500.RDN;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle;

/* loaded from: classes5.dex */
public class BCStrictStyle extends BCStyle {
    public static final X500NameStyle INSTANCE = new BCStrictStyle();

    @Override // com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle, com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public boolean areEqual(X500Name x500Name, X500Name x500Name2) {
        if (x500Name.size() != x500Name2.size()) {
            return false;
        }
        RDN[] rDNs = x500Name.getRDNs();
        RDN[] rDNs2 = x500Name2.getRDNs();
        for (int i = 0; i != rDNs.length; i++) {
            if (!rdnAreEqual(rDNs[i], rDNs2[i])) {
                return false;
            }
        }
        return true;
    }
}
