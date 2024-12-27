package com.android.internal.org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DERExternal extends ASN1External {
    public DERExternal(ASN1EncodableVector vector) {
        super(vector);
    }

    public DERExternal(
            ASN1ObjectIdentifier directReference,
            ASN1Integer indirectReference,
            ASN1Primitive dataValueDescriptor,
            DERTaggedObject externalData) {
        this(
                directReference,
                indirectReference,
                dataValueDescriptor,
                externalData.getTagNo(),
                externalData.toASN1Primitive());
    }

    public DERExternal(
            ASN1ObjectIdentifier directReference,
            ASN1Integer indirectReference,
            ASN1Primitive dataValueDescriptor,
            int encoding,
            ASN1Primitive externalData) {
        super(directReference, indirectReference, dataValueDescriptor, encoding, externalData);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External,
              // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        return getEncoded().length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (this.directReference != null) {
            baos.write(this.directReference.getEncoded(ASN1Encoding.DER));
        }
        if (this.indirectReference != null) {
            baos.write(this.indirectReference.getEncoded(ASN1Encoding.DER));
        }
        if (this.dataValueDescriptor != null) {
            baos.write(this.dataValueDescriptor.getEncoded(ASN1Encoding.DER));
        }
        DERTaggedObject obj = new DERTaggedObject(true, this.encoding, this.externalContent);
        baos.write(obj.getEncoded(ASN1Encoding.DER));
        out.writeEncoded(withTag, 32, 8, baos.toByteArray());
    }
}
