package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

/* loaded from: classes6.dex */
public class RetrieveConf extends MultimediaMessagePdu {
    public RetrieveConf() throws InvalidHeaderValueException {
        setMessageType(132);
    }

    RetrieveConf(PduHeaders pduHeaders) {
        super(pduHeaders);
    }

    RetrieveConf(PduHeaders pduHeaders, PduBody pduBody) {
        super(pduHeaders, pduBody);
    }

    public EncodedStringValue[] getCc() {
        return this.mPduHeaders.getEncodedStringValues(130);
    }

    public void addCc(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.appendEncodedStringValue(encodedStringValue, 130);
    }

    public byte[] getContentType() {
        return this.mPduHeaders.getTextString(132);
    }

    public void setContentType(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 132);
    }

    public int getDeliveryReport() {
        return this.mPduHeaders.getOctet(134);
    }

    public void setDeliveryReport(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 134);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public EncodedStringValue getFrom() {
        return this.mPduHeaders.getEncodedStringValue(137);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public void setFrom(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 137);
    }

    public byte[] getMessageClass() {
        return this.mPduHeaders.getTextString(138);
    }

    public void setMessageClass(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 138);
    }

    public byte[] getMessageId() {
        return this.mPduHeaders.getTextString(139);
    }

    public void setMessageId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 139);
    }

    public int getReadReport() {
        return this.mPduHeaders.getOctet(144);
    }

    public void setReadReport(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 144);
    }

    public int getRetrieveStatus() {
        return this.mPduHeaders.getOctet(153);
    }

    public void setRetrieveStatus(int i) throws InvalidHeaderValueException {
        this.mPduHeaders.setOctet(i, 153);
    }

    public EncodedStringValue getRetrieveText() {
        return this.mPduHeaders.getEncodedStringValue(154);
    }

    public void setRetrieveText(EncodedStringValue encodedStringValue) {
        this.mPduHeaders.setEncodedStringValue(encodedStringValue, 154);
    }

    public byte[] getTransactionId() {
        return this.mPduHeaders.getTextString(152);
    }

    public void setTransactionId(byte[] bArr) {
        this.mPduHeaders.setTextString(bArr, 152);
    }

    @Override // com.google.android.mms.pdu.GenericPdu
    public int getMmsVersion() {
        return this.mPduHeaders.getOctet(141);
    }

    public byte[] getMmsId() {
        return this.mPduHeaders.getTextString(139);
    }
}
