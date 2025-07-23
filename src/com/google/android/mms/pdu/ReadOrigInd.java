package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class ReadOrigInd extends GenericPdu {
    public ReadOrigInd() throws InvalidHeaderValueException {
        setMessageType(136);
    }

    ReadOrigInd(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    public long getDate() {
        return this.mPduHeaders.getLongInteger(133);
    }

    public void setDate(long j) {
        this.mPduHeaders.setLongInteger(j, 133);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public EncodedStringValue getFrom() {
        return this.mPduHeaders.getEncodedStringValue(137);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public void setFrom(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 137);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public int getReadStatus() {
        return this.mPduHeaders.getOctet(155);
    }

    public void setReadStatus(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 155);
    }

    public EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void setTo(EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }
}
