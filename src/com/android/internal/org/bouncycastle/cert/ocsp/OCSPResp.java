package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Exception;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import com.android.internal.org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.ocsp.OCSPResponse;
import com.android.internal.org.bouncycastle.asn1.ocsp.ResponseBytes;
import com.android.internal.org.bouncycastle.cert.CertIOException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class OCSPResp {
    public static final int INTERNAL_ERROR = 2;
    public static final int MALFORMED_REQUEST = 1;
    public static final int SIG_REQUIRED = 5;
    public static final int SUCCESSFUL = 0;
    public static final int TRY_LATER = 3;
    public static final int UNAUTHORIZED = 6;
    private OCSPResponse resp;

    public OCSPResp(OCSPResponse oCSPResponse) {
        this.resp = oCSPResponse;
    }

    public OCSPResp(byte[] bArr) throws IOException {
        this(new ByteArrayInputStream(bArr));
    }

    public OCSPResp(InputStream inputStream) throws IOException {
        this(new ASN1InputStream(inputStream));
    }

    private OCSPResp(ASN1InputStream aSN1InputStream) throws IOException {
        try {
            OCSPResponse oCSPResponse = OCSPResponse.getInstance(aSN1InputStream.readObject());
            this.resp = oCSPResponse;
            if (oCSPResponse == null) {
                throw new CertIOException("malformed response: no response data found");
            }
        } catch (ASN1Exception e) {
            throw new CertIOException("malformed response: " + e.getMessage(), e);
        } catch (ClassCastException e2) {
            throw new CertIOException("malformed response: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new CertIOException("malformed response: " + e3.getMessage(), e3);
        }
    }

    public int getStatus() {
        return this.resp.getResponseStatus().getIntValue();
    }

    public Object getResponseObject() throws OCSPException {
        ResponseBytes responseBytes = this.resp.getResponseBytes();
        if (responseBytes == null) {
            return null;
        }
        if (responseBytes.getResponseType().equals((ASN1Primitive) OCSPObjectIdentifiers.id_pkix_ocsp_basic)) {
            try {
                return new BasicOCSPResp(BasicOCSPResponse.getInstance(ASN1Primitive.fromByteArray(responseBytes.getResponse().getOctets())));
            } catch (Exception e) {
                throw new OCSPException("problem decoding object: " + e, e);
            }
        }
        return responseBytes.getResponse();
    }

    public byte[] getEncoded() throws IOException {
        return this.resp.getEncoded();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OCSPResp) {
            return this.resp.equals(((OCSPResp) obj).resp);
        }
        return false;
    }

    public int hashCode() {
        return this.resp.hashCode();
    }

    public OCSPResponse toASN1Structure() {
        return this.resp;
    }
}
