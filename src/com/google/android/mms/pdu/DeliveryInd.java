package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class DeliveryInd extends GenericPdu {
    public DeliveryInd() throws InvalidHeaderValueException {
        setMessageType(134);
    }

    DeliveryInd(PduHeaders pduHeaders) {
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

    public int getStatus() {
        return this.mPduHeaders.getOctet(149);
    }

    public void setStatus(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 149);
    }

    public EncodedStringValue[] getTo() {
        return this.mPduHeaders.getEncodedStringValues(151);
    }

    public void setTo(EncodedStringValue[] encodedStringValueArr) {
        this.mPduHeaders.setEncodedStringValues(encodedStringValueArr, 151);
    }
}
