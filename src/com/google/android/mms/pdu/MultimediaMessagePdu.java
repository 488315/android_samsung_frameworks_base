package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class MultimediaMessagePdu extends GenericPdu {
    private PduBody mMessageBody;

    public MultimediaMessagePdu() {
    }

    public MultimediaMessagePdu(PduHeaders pduHeaders, PduBody pduBody) {
        super(pduHeaders);
        this.mMessageBody = pduBody;
    }

    MultimediaMessagePdu(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public PduBody getBody() {
        return this.mMessageBody;
    }

    public void setBody(PduBody pduBody) {
        this.mMessageBody = pduBody;
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public EncodedStringValue getSubject() {
        return this.mPduHeaders.getEncodedStringValue(150);
    }

    public void setSubject(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 150);
    }

    public EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void addTo(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 151);
    }

    public int getPriority() {
        return this.mPduHeaders.getOctet(143);
    }

    public void setPriority(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 143);
    }

    public long getDate() {
        return this.mPduHeaders.getLongInteger(133);
    }

    public void setDate(long j) {
        this.mPduHeaders.setLongInteger(j, 133);
    }
}
