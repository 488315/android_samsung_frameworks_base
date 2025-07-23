package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class SendConf extends GenericPdu {
    public SendConf() throws InvalidHeaderValueException {
        setMessageType(129);
    }

    SendConf(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public int getResponseStatus() {
        return this.mPduHeaders.getOctet(146);
    }

    public void setResponseStatus(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 146);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }

    public EncodedStringValue getResponseText() {
        return this.mPduHeaders.getEncodedStringValue(147);
    }
}
