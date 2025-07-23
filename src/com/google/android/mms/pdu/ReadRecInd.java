package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class ReadRecInd extends GenericPdu {
    public ReadRecInd(EncodedStringValue encodedStringValue, byte[] bArr, int i, int i2, EncodedStringValue[] encodedStringValueArr) throws InvalidHeaderValueException {
        setMessageType(135);
        setFrom(encodedStringValue);
        setMessageId(bArr);
        setMmsVersion(i);
        setTo(encodedStringValueArr);
        setReadStatus(i2);
    }

    ReadRecInd(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public long getDate() {
        return this.mPduHeaders.getLongInteger(133);
    }

    public void setDate(long j) {
        this.mPduHeaders.setLongInteger(j, 133);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void setTo(EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }

    public int getReadStatus() {
        return this.mPduHeaders.getOctet(155);
    }

    public void setReadStatus(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 155);
    }
}
