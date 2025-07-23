package com.android.internal.org.bouncycastle.asn1.nist;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class KMACwithSHAKE256_params extends ASN1Object {
    private static final int DEF_LENGTH = 512;
    private static final byte[] EMPTY_STRING = new byte[0];
    private final byte[] customizationString;
    private final int outputLength;

    public KMACwithSHAKE256_params(int i) {
        this.outputLength = i;
        this.customizationString = EMPTY_STRING;
    }

    public KMACwithSHAKE256_params(int i, byte[] bArr) {
        this.outputLength = i;
        this.customizationString = Arrays.clone(bArr);
    }

    public static KMACwithSHAKE256_params getInstance(Object obj) {
        if (obj instanceof KMACwithSHAKE256_params) {
            return (KMACwithSHAKE256_params) obj;
        }
        if (obj != null) {
            return new KMACwithSHAKE256_params(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private KMACwithSHAKE256_params(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 2) {
            throw new IllegalArgumentException("sequence size greater than 2");
        }
        if (aSN1Sequence.size() == 2) {
            this.outputLength = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).intValueExact();
            this.customizationString = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
        } else {
            if (aSN1Sequence.size() == 1) {
                if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
                    this.outputLength = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).intValueExact();
                    this.customizationString = EMPTY_STRING;
                    return;
                } else {
                    this.outputLength = 512;
                    this.customizationString = Arrays.clone(ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets());
                    return;
                }
            }
            this.outputLength = 512;
            this.customizationString = EMPTY_STRING;
        }
    }

    public int getOutputLength() {
        return this.outputLength;
    }

    public byte[] getCustomizationString() {
        return Arrays.clone(this.customizationString);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.outputLength != 512) {
            aSN1EncodableVector.add(new ASN1Integer(this.outputLength));
        }
        if (this.customizationString.length != 0) {
            aSN1EncodableVector.add(new DEROctetString(getCustomizationString()));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
