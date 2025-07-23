package com.android.internal.org.bouncycastle.cmc;

import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.cms.ContentInfo;
import com.android.internal.org.bouncycastle.cert.X509CRLHolder;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.cms.CMSException;
import com.android.internal.org.bouncycastle.cms.CMSSignedData;
import com.android.internal.org.bouncycastle.util.Encodable;
import com.android.internal.org.bouncycastle.util.Store;
import java.io.IOException;

/* loaded from: classes5.dex */
public class SimplePKIResponse implements Encodable {
    private final CMSSignedData certificateResponse;

    private static ContentInfo parseBytes(byte[] bArr) throws CMCException {
        try {
            return ContentInfo.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (Exception e) {
            throw new CMCException("malformed data: " + e.getMessage(), e);
        }
    }

    public SimplePKIResponse(byte[] bArr) throws CMCException {
        this(parseBytes(bArr));
    }

    public SimplePKIResponse(ContentInfo contentInfo) throws CMCException {
        try {
            CMSSignedData cMSSignedData = new CMSSignedData(contentInfo);
            this.certificateResponse = cMSSignedData;
            if (cMSSignedData.getSignerInfos().size() != 0) {
                throw new CMCException("malformed response: SignerInfo structures found");
            }
            if (cMSSignedData.getSignedContent() != null) {
                throw new CMCException("malformed response: Signed Content found");
            }
        } catch (CMSException e) {
            throw new CMCException("malformed response: " + e.getMessage(), e);
        }
    }

    public Store<X509CertificateHolder> getCertificates() {
        return this.certificateResponse.getCertificates();
    }

    public Store<X509CRLHolder> getCRLs() {
        return this.certificateResponse.getCRLs();
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws IOException {
        return this.certificateResponse.getEncoded();
    }
}
