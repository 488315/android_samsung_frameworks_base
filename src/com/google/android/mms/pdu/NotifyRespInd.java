package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class NotifyRespInd extends GenericPdu {
    public NotifyRespInd(int i, byte[] bArr, int i2) throws InvalidHeaderValueException {
        setMessageType(131);
        setMmsVersion(i);
        setTransactionId(bArr);
        setStatus(i2);
    }

    NotifyRespInd(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public int getReportAllowed() {
        return this.mPduHeaders.getOctet(145);
    }

    public void setReportAllowed(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 145);
    }

    public void setStatus(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 149);
    }

    public int getStatus() {
        return this.mPduHeaders.getOctet(149);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }
}
