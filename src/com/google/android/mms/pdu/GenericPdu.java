package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class GenericPdu {
    PduHeaders mPduHeaders;

    public GenericPdu() {
        this.mPduHeaders = null;
        this.mPduHeaders = new PduHeaders();
    }

    GenericPdu(PduHeaders pduHeaders) {
        this.mPduHeaders = pduHeaders;
    }

    PduHeaders getPduHeaders() {
        return this.mPduHeaders;
    }

    public int getMessageType() {
        return this.mPduHeaders.getOctet(140);
    }

    public void setMessageType(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 140);
    }

    public int getMmsVersion() {
        return this.mPduHeaders.getOctet(141);
    }

    public void setMmsVersion(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 141);
    }

    public EncodedStringValue getFrom() {
        return this.mPduHeaders.getEncodedStringValue(137);
    }

    public void setFrom(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 137);
    }

    public EncodedStringValue getSubject() {
        return this.mPduHeaders.getEncodedStringValue(150);
    }
}
