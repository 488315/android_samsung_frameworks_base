package com.android.internal.org.bouncycastle.asn1.x500;

import com.android.internal.org.bouncycastle.asn1.ASN1Choice;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle;

/* loaded from: classes5.dex */
public class X500Name extends ASN1Object implements ASN1Choice {
    private static X500NameStyle defaultStyle = BCStyle.INSTANCE;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private DERSequence rdnSeq;
    private RDN[] rdns;
    private X500NameStyle style;

    public X500Name(X500NameStyle x500NameStyle, X500Name x500Name) {
        this.style = x500NameStyle;
        this.rdns = x500Name.rdns;
        this.rdnSeq = x500Name.rdnSeq;
    }

    public static X500Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
    }

    public static X500Name getInstance(Object obj) {
        if (obj instanceof X500Name) {
            return (X500Name) obj;
        }
        if (obj != null) {
            return new X500Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static X500Name getInstance(X500NameStyle x500NameStyle, Object obj) {
        if (obj instanceof X500Name) {
            return new X500Name(x500NameStyle, (X500Name) obj);
        }
        if (obj != null) {
            return new X500Name(x500NameStyle, ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private X500Name(ASN1Sequence aSN1Sequence) {
        this(defaultStyle, aSN1Sequence);
    }

    private X500Name(X500NameStyle x500NameStyle, ASN1Sequence aSN1Sequence) {
        int size = aSN1Sequence.size();
        this.style = x500NameStyle;
        this.rdns = new RDN[size];
        boolean z = true;
        for (int i = 0; i < size; i++) {
            ASN1Encodable objectAt = aSN1Sequence.getObjectAt(i);
            RDN rdn = RDN.getInstance(objectAt);
            z &= rdn == objectAt;
            this.rdns[i] = rdn;
        }
        if (z) {
            this.rdnSeq = DERSequence.convert(aSN1Sequence);
        } else {
            this.rdnSeq = new DERSequence(this.rdns);
        }
    }

    public X500Name(RDN[] rdnArr) {
        this(defaultStyle, rdnArr);
    }

    public X500Name(X500NameStyle x500NameStyle, RDN[] rdnArr) {
        this.style = x500NameStyle;
        this.rdns = (RDN[]) rdnArr.clone();
        this.rdnSeq = new DERSequence(this.rdns);
    }

    public X500Name(String str) {
        this(defaultStyle, str);
    }

    public X500Name(X500NameStyle x500NameStyle, String str) {
        this(x500NameStyle.fromString(str));
        this.style = x500NameStyle;
    }

    public RDN[] getRDNs() {
        return (RDN[]) this.rdns.clone();
    }

    public ASN1ObjectIdentifier[] getAttributeTypes() {
        int length = this.rdns.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i += this.rdns[i2].size();
        }
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[i];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            i3 += this.rdns[i4].collectAttributeTypes(aSN1ObjectIdentifierArr, i3);
        }
        return aSN1ObjectIdentifierArr;
    }

    public RDN[] getRDNs(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int length = this.rdns.length;
        RDN[] rdnArr = new RDN[length];
        int i = 0;
        int i2 = 0;
        while (true) {
            RDN[] rdnArr2 = this.rdns;
            if (i == rdnArr2.length) {
                break;
            }
            RDN rdn = rdnArr2[i];
            if (rdn.containsAttributeType(aSN1ObjectIdentifier)) {
                rdnArr[i2] = rdn;
                i2++;
            }
            i++;
        }
        if (i2 >= length) {
            return rdnArr;
        }
        RDN[] rdnArr3 = new RDN[i2];
        System.arraycopy(rdnArr, 0, rdnArr3, 0, i2);
        return rdnArr3;
    }

    public int size() {
        return this.rdns.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return this.rdnSeq;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        int calculateHashCode = this.style.calculateHashCode(this);
        this.hashCodeValue = calculateHashCode;
        return calculateHashCode;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X500Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            return this.style.areEqual(this, getInstance(obj));
        } catch (Exception unused) {
            return false;
        }
    }

    public String toString() {
        return this.style.toString(this);
    }

    public static void setDefaultStyle(X500NameStyle x500NameStyle) {
        if (x500NameStyle == null) {
            throw new NullPointerException("cannot set style to null");
        }
        defaultStyle = x500NameStyle;
    }

    public static X500NameStyle getDefaultStyle() {
        return defaultStyle;
    }
}
