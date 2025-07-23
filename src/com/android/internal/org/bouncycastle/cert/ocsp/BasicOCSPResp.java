package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData;
import com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.asn1.x509.Extension;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.ContentVerifier;
import com.android.internal.org.bouncycastle.operator.ContentVerifierProvider;
import com.android.internal.org.bouncycastle.util.Encodable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public class BasicOCSPResp implements Encodable {
    private ResponseData data;
    private Extensions extensions;
    private BasicOCSPResponse resp;

    public BasicOCSPResp(BasicOCSPResponse basicOCSPResponse) {
        this.resp = basicOCSPResponse;
        this.data = basicOCSPResponse.getTbsResponseData();
        this.extensions = Extensions.getInstance(basicOCSPResponse.getTbsResponseData().getResponseExtensions());
    }

    public byte[] getTBSResponseData() {
        try {
            return this.resp.getTbsResponseData().getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            return null;
        }
    }

    public AlgorithmIdentifier getSignatureAlgorithmID() {
        return this.resp.getSignatureAlgorithm();
    }

    public int getVersion() {
        return this.data.getVersion().intValueExact() + 1;
    }

    public RespID getResponderId() {
        return new RespID(this.data.getResponderID());
    }

    public Date getProducedAt() {
        return OCSPUtils.extractDate(this.data.getProducedAt());
    }

    public SingleResp[] getResponses() {
        ASN1Sequence responses = this.data.getResponses();
        int size = responses.size();
        SingleResp[] singleRespArr = new SingleResp[size];
        for (int i = 0; i != size; i++) {
            singleRespArr[i] = new SingleResp(SingleResponse.getInstance(responses.getObjectAt(i)));
        }
        return singleRespArr;
    }

    public boolean hasExtensions() {
        return this.extensions != null;
    }

    public Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions extensions = this.extensions;
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public List getExtensionOIDs() {
        return OCSPUtils.getExtensionOIDs(this.extensions);
    }

    public Set getCriticalExtensionOIDs() {
        return OCSPUtils.getCriticalExtensionOIDs(this.extensions);
    }

    public Set getNonCriticalExtensionOIDs() {
        return OCSPUtils.getNonCriticalExtensionOIDs(this.extensions);
    }

    public ASN1ObjectIdentifier getSignatureAlgOID() {
        return this.resp.getSignatureAlgorithm().getAlgorithm();
    }

    public byte[] getSignature() {
        return this.resp.getSignature().getOctets();
    }

    public X509CertificateHolder[] getCerts() {
        if (this.resp.getCerts() != null) {
            ASN1Sequence certs = this.resp.getCerts();
            if (certs != null) {
                int size = certs.size();
                X509CertificateHolder[] x509CertificateHolderArr = new X509CertificateHolder[size];
                for (int i = 0; i != size; i++) {
                    x509CertificateHolderArr[i] = new X509CertificateHolder(Certificate.getInstance(certs.getObjectAt(i)));
                }
                return x509CertificateHolderArr;
            }
            return OCSPUtils.EMPTY_CERTS;
        }
        return OCSPUtils.EMPTY_CERTS;
    }

    public boolean isSignatureValid(ContentVerifierProvider contentVerifierProvider) throws OCSPException {
        try {
            ContentVerifier contentVerifier = contentVerifierProvider.get(this.resp.getSignatureAlgorithm());
            OutputStream outputStream = contentVerifier.getOutputStream();
            outputStream.write(this.resp.getTbsResponseData().getEncoded(ASN1Encoding.DER));
            outputStream.close();
            return contentVerifier.verify(getSignature());
        } catch (Exception e) {
            throw new OCSPException("exception processing sig: " + e, e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws IOException {
        return this.resp.getEncoded();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BasicOCSPResp) {
            return this.resp.equals(((BasicOCSPResp) obj).resp);
        }
        return false;
    }

    public int hashCode() {
        return this.resp.hashCode();
    }
}
