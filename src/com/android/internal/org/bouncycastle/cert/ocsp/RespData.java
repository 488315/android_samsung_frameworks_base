package com.android.internal.org.bouncycastle.cert.ocsp;

import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ocsp.ResponseData;
import com.android.internal.org.bouncycastle.asn1.ocsp.SingleResponse;
import com.android.internal.org.bouncycastle.asn1.x509.Extensions;
import java.util.Date;

/* loaded from: classes5.dex */
public class RespData {
    private ResponseData data;

    public RespData(ResponseData responseData) {
        this.data = responseData;
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

    public Extensions getResponseExtensions() {
        return this.data.getResponseExtensions();
    }
}
