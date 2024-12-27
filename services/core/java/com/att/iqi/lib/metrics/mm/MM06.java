package com.att.iqi.lib.metrics.mm;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class MM06 extends Metric {
    public static final byte IQ_SIP_ORIGINATED = 0;
    public static final byte IQ_SIP_TERMINATED = 1;
    private static final short RESPONSE_CODE_MASK = Short.MAX_VALUE;
    private static final int TERMINATION_DIRECTION_SHIFT = 15;
    private short m_shResult;
    private String m_szCallId;
    public static final Metric.ID ID = new Metric.ID("MM06");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.mm.MM06.1
                @Override // android.os.Parcelable.Creator
                public MM06 createFromParcel(Parcel parcel) {
                    return new MM06(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public MM06[] newArray(int i) {
                    return new MM06[i];
                }
            };

    public MM06() {
        reset();
    }

    public MM06(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_shResult = (short) parcel.readInt();
            this.m_szCallId = parcel.readString();
        }
    }

    public String getCallId() {
        return this.m_szCallId;
    }

    public short getResponseCode() {
        return (short) (this.m_shResult & RESPONSE_CODE_MASK);
    }

    public byte getTerminationDirection() {
        return (byte) (this.m_shResult >> 15);
    }

    public void reset() {
        this.m_shResult = (short) 0;
        this.m_szCallId = "";
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putShort(this.m_shResult);
        stringOut(byteBuffer, this.m_szCallId);
        return byteBuffer.position();
    }

    public MM06 setCallId(String str) {
        this.m_szCallId = str;
        return this;
    }

    public MM06 setResponseCode(short s) {
        this.m_shResult = (short) ((s & RESPONSE_CODE_MASK) | (this.m_shResult & Short.MIN_VALUE));
        return this;
    }

    public MM06 setTerminationDirection(byte b) {
        this.m_shResult = (short) ((b << 15) | (this.m_shResult & RESPONSE_CODE_MASK));
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_shResult);
        parcel.writeString(this.m_szCallId);
    }
}
