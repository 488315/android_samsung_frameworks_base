package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class AcknowledgeInd extends GenericPdu {
    public AcknowledgeInd(int i, byte[] bArr) throws InvalidHeaderValueException {
        setMessageType(133);
        setMmsVersion(i);
        setTransactionId(bArr);
    }

    AcknowledgeInd(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public int getReportAllowed() {
        return this.mPduHeaders.getOctet(145);
    }

    public void setReportAllowed(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 145);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
