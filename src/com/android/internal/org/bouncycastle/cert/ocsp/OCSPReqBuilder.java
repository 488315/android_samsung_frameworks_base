package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.DERBitString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.ocsp.OCSPRequest;
import com.android.internal.org.bouncycastle.asn1.ocsp.Request;
import com.android.internal.org.bouncycastle.asn1.ocsp.Signature;
import com.android.internal.org.bouncycastle.asn1.ocsp.TBSRequest;
import com.android.internal.org.bouncycastle.asn1.x500.X500Name;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import com.android.internal.org.bouncycastle.asn1.x509.GeneralName;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.operator.ContentSigner;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class OCSPReqBuilder {
    private List list = new ArrayList();
    private GeneralName requestorName = null;
    private Extensions requestExtensions = null;

    private static class RequestObject {
        CertificateID certId;
        Extensions extensions;

        public RequestObject(CertificateID certificateID, Extensions extensions) {
            this.certId = certificateID;
            this.extensions = extensions;
        }

        public Request toRequest() throws Exception {
            return new Request(this.certId.toASN1Primitive(), this.extensions);
        }
    }

    public OCSPReqBuilder addRequest(CertificateID certificateID) {
        this.list.add(new RequestObject(certificateID, null));
        return this;
    }

    public OCSPReqBuilder addRequest(CertificateID certificateID, Extensions extensions) {
        this.list.add(new RequestObject(certificateID, extensions));
        return this;
    }

    public OCSPReqBuilder setRequestorName(X500Name x500Name) {
        this.requestorName = new GeneralName(4, x500Name);
        return this;
    }

    public OCSPReqBuilder setRequestorName(GeneralName generalName) {
        this.requestorName = generalName;
        return this;
    }

    public OCSPReqBuilder setRequestExtensions(Extensions extensions) {
        this.requestExtensions = extensions;
        return this;
    }

    private OCSPReq generateRequest(ContentSigner contentSigner, X509CertificateHolder[] x509CertificateHolderArr) throws OCSPException {
        Signature signature;
        Iterator it = this.list.iterator();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (it.hasNext()) {
            try {
                aSN1EncodableVector.add(((RequestObject) it.next()).toRequest());
            } catch (Exception e) {
                throw new OCSPException("exception creating Request", e);
            }
        }
        TBSRequest tBSRequest = new TBSRequest(this.requestorName, new DERSequence(aSN1EncodableVector), this.requestExtensions);
        if (contentSigner == null) {
            signature = null;
        } else {
            if (this.requestorName == null) {
                throw new OCSPException("requestorName must be specified if request is signed.");
            }
            try {
                OutputStream outputStream = contentSigner.getOutputStream();
                outputStream.write(tBSRequest.getEncoded(ASN1Encoding.DER));
                outputStream.close();
                DERBitString dERBitString = new DERBitString(contentSigner.getSignature());
                AlgorithmIdentifier algorithmIdentifier = contentSigner.getAlgorithmIdentifier();
                if (x509CertificateHolderArr != null && x509CertificateHolderArr.length > 0) {
                    ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
                    for (int i = 0; i != x509CertificateHolderArr.length; i++) {
                        aSN1EncodableVector2.add(x509CertificateHolderArr[i].toASN1Structure());
                    }
                    signature = new Signature(algorithmIdentifier, dERBitString, new DERSequence(aSN1EncodableVector2));
                } else {
                    signature = new Signature(algorithmIdentifier, dERBitString);
                }
            } catch (Exception e2) {
                throw new OCSPException("exception processing TBSRequest: " + e2, e2);
            }
        }
        return new OCSPReq(new OCSPRequest(tBSRequest, signature));
    }

    public OCSPReq build() throws OCSPException {
        return generateRequest(null, null);
    }

    public OCSPReq build(ContentSigner contentSigner, X509CertificateHolder[] x509CertificateHolderArr) throws OCSPException, IllegalArgumentException {
        if (contentSigner == null) {
            throw new IllegalArgumentException("no signer specified");
        }
        return generateRequest(contentSigner, x509CertificateHolderArr);
    }
}
