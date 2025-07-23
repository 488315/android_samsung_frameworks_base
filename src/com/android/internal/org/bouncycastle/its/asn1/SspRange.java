package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.asn1.ASN1Null;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SspRange extends ASN1Object {
    private final BitmapSspRange bitmapSspRange;
    private final boolean isAll;
    private final SequenceOfOctetString opaque;

    private SspRange() {
        this.isAll = true;
        this.opaque = null;
        this.bitmapSspRange = null;
    }

    private SspRange(SequenceOfOctetString sequenceOfOctetString) {
        this.isAll = false;
        BitmapSspRange bitmapSspRange = null;
        if (sequenceOfOctetString.size() != 2) {
            this.opaque = sequenceOfOctetString;
            this.bitmapSspRange = null;
        } else {
            this.opaque = SequenceOfOctetString.getInstance(sequenceOfOctetString);
            try {
                bitmapSspRange = BitmapSspRange.getInstance(sequenceOfOctetString);
            } catch (IllegalArgumentException unused) {
            }
            this.bitmapSspRange = bitmapSspRange;
        }
    }

    public SspRange(BitmapSspRange bitmapSspRange) {
        this.isAll = false;
        this.bitmapSspRange = bitmapSspRange;
        this.opaque = null;
    }

    public static SspRange getInstance(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SspRange) {
            return (SspRange) obj;
        }
        if (obj instanceof ASN1Null) {
            return new SspRange();
        }
        if (obj instanceof ASN1Sequence) {
            return new SspRange(SequenceOfOctetString.getInstance(obj));
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException unused) {
                throw new IllegalArgumentException("unable to parse encoded general name");
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public boolean isAll() {
        return this.isAll;
    }

    public boolean maybeOpaque() {
        return this.opaque != null;
    }

    public BitmapSspRange getBitmapSspRange() {
        return this.bitmapSspRange;
    }

    public SequenceOfOctetString getOpaque() {
        return this.opaque;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        if (this.isAll) {
            return DERNull.INSTANCE;
        }
        BitmapSspRange bitmapSspRange = this.bitmapSspRange;
        if (bitmapSspRange != null) {
            return bitmapSspRange.toASN1Primitive();
        }
        return this.opaque.toASN1Primitive();
    }
}
