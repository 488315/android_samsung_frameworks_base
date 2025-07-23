package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.ocsp.Request;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;

/* loaded from: classes5.dex */
public class Req {
    private Request req;

    public Req(Request request) {
        this.req = request;
    }

    public CertificateID getCertID() {
        return new CertificateID(this.req.getReqCert());
    }

    public Extensions getSingleRequestExtensions() {
        return this.req.getSingleRequestExtensions();
    }
}
