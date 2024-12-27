package com.att.iqi.lib.metrics.rp;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class RP02 extends Metric {
    public static final byte IQ_RTP_BADSSRC = 4;
    public static final byte IQ_RTP_DROP = 2;
    public static final byte IQ_RTP_LATE = 1;
    public static final byte IQ_RTP_OK = 0;
    public static final byte IQ_RTP_RESETSEQ = 3;
    private int m_dwSourceId;
    private int m_dwTimestamp;
    private byte m_ucFlags;
    private byte m_ucPayloadType;
    private byte m_ucPktStatus;
    private short m_wByteCount;
    private short m_wDstPort;
    private short m_wSequenceNum;
    public static final Metric.ID ID = new Metric.ID("RP02");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.rp.RP02.1
                @Override // android.os.Parcelable.Creator
                public RP02 createFromParcel(Parcel parcel) {
                    return new RP02(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public RP02[] newArray(int i) {
                    return new RP02[i];
                }
            };

    public RP02() {
        reset();
    }

    public RP02(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_wByteCount = (short) parcel.readInt();
            this.m_wDstPort = (short) parcel.readInt();
            this.m_ucPktStatus = parcel.readByte();
            this.m_ucFlags = parcel.readByte();
            this.m_ucPayloadType = parcel.readByte();
            this.m_wSequenceNum = (short) parcel.readInt();
            this.m_dwTimestamp = parcel.readInt();
            this.m_dwSourceId = parcel.readInt();
        }
    }

    public short getByteCount() {
        return this.m_wByteCount;
    }

    public short getDstPort() {
        return this.m_wDstPort;
    }

    public byte getFlags() {
        return this.m_ucFlags;
    }

    public byte getPayloadType() {
        return this.m_ucPayloadType;
    }

    public byte getPktStatus() {
        return this.m_ucPktStatus;
    }

    public short getSequenceNum() {
        return this.m_wSequenceNum;
    }

    public int getSourceId() {
        return this.m_dwSourceId;
    }

    public int getTimestamp() {
        return this.m_dwTimestamp;
    }

    public void reset() {
        this.m_wByteCount = (short) 0;
        this.m_wDstPort = (short) 0;
        this.m_ucPktStatus = (byte) 0;
        this.m_ucFlags = (byte) 0;
        this.m_ucPayloadType = (byte) 0;
        this.m_wSequenceNum = (short) 0;
        this.m_dwTimestamp = 0;
        this.m_dwSourceId = 0;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putShort(this.m_wByteCount);
        byteBuffer.putShort(this.m_wDstPort);
        byteBuffer.put(this.m_ucPktStatus);
        byteBuffer.put(this.m_ucFlags);
        byteBuffer.put(this.m_ucPayloadType);
        byteBuffer.putShort(this.m_wSequenceNum);
        byteBuffer.putInt(this.m_dwTimestamp);
        byteBuffer.putInt(this.m_dwSourceId);
        return byteBuffer.position();
    }

    public RP02 setByteCount(short s) {
        this.m_wByteCount = s;
        return this;
    }

    public RP02 setDstPort(short s) {
        this.m_wDstPort = s;
        return this;
    }

    public RP02 setFlags(byte b) {
        this.m_ucFlags = b;
        return this;
    }

    public RP02 setPayloadType(byte b) {
        this.m_ucPayloadType = b;
        return this;
    }

    public RP02 setPktStatus(byte b) {
        this.m_ucPktStatus = b;
        return this;
    }

    public RP02 setSequenceNum(short s) {
        this.m_wSequenceNum = s;
        return this;
    }

    public RP02 setSourceId(int i) {
        this.m_dwSourceId = i;
        return this;
    }

    public RP02 setTimestamp(int i) {
        this.m_dwTimestamp = i;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_wByteCount);
        parcel.writeInt(this.m_wDstPort);
        parcel.writeByte(this.m_ucPktStatus);
        parcel.writeByte(this.m_ucFlags);
        parcel.writeByte(this.m_ucPayloadType);
        parcel.writeInt(this.m_wSequenceNum);
        parcel.writeInt(this.m_dwTimestamp);
        parcel.writeInt(this.m_dwSourceId);
    }
}
