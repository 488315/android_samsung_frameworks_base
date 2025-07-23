package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class PKCS7ProcessableObject implements CMSTypedData {
    private final ASN1Encodable structure;
    private final ASN1ObjectIdentifier type;

    public PKCS7ProcessableObject(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.type = aSN1ObjectIdentifier;
        this.structure = aSN1Encodable;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
    public ASN1ObjectIdentifier getContentType() {
        return this.type;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public void write(OutputStream outputStream) throws IOException, CMSException {
        ASN1Encodable aSN1Encodable = this.structure;
        if (aSN1Encodable instanceof ASN1Sequence) {
            Iterator<ASN1Encodable> it = ASN1Sequence.getInstance(aSN1Encodable).iterator();
            while (it.hasNext()) {
                outputStream.write(it.next().toASN1Primitive().getEncoded(ASN1Encoding.DER));
            }
        } else {
            byte[] encoded = aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
            int i = 1;
            while ((encoded[i] & 255) > 127) {
                i++;
            }
            int i2 = i + 1;
            outputStream.write(encoded, i2, encoded.length - i2);
        }
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public Object getContent() {
        return this.structure;
    }
}
