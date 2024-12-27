package com.att.iqi.lib.metrics.mm;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class MM07 extends Metric {
    public static final byte IQ_SDP_MEDIA_APPLICATION = 4;
    public static final byte IQ_SDP_MEDIA_AUDIO = 1;
    public static final byte IQ_SDP_MEDIA_CONTROL = 6;
    public static final byte IQ_SDP_MEDIA_DATA = 7;
    public static final byte IQ_SDP_MEDIA_MESSAGE = 5;
    public static final byte IQ_SDP_MEDIA_TEXT = 3;
    public static final byte IQ_SDP_MEDIA_UNKNOWN = 0;
    public static final byte IQ_SDP_MEDIA_VIDEO = 2;
    private final HashMap mMediaStats;
    public static final Metric.ID ID = new Metric.ID("MM07");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.mm.MM07.1
                @Override // android.os.Parcelable.Creator
                public MM07 createFromParcel(Parcel parcel) {
                    return new MM07(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public MM07[] newArray(int i) {
                    return new MM07[i];
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class RtpStats {
        private int m_dwDuration;
        private int m_dwPacketsDrop;
        private int m_dwPacketsLate;
        private int m_dwPacketsRcvd;
        private final byte m_ucFormat;
        private final byte m_ucMediaType;

        private RtpStats(byte b, byte b2) {
            this.m_ucMediaType = b;
            this.m_ucFormat = b2;
            this.m_dwDuration = 0;
            this.m_dwPacketsRcvd = 0;
            this.m_dwPacketsDrop = 0;
            this.m_dwPacketsLate = 0;
        }

        public /* synthetic */ RtpStats(byte b, byte b2, int i) {
            this(b, b2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
            byteBuffer.put(this.m_ucMediaType);
            byteBuffer.put(this.m_ucFormat);
            byteBuffer.putInt(this.m_dwDuration);
            byteBuffer.putInt(this.m_dwPacketsRcvd);
            byteBuffer.putInt(this.m_dwPacketsDrop);
            byteBuffer.putInt(this.m_dwPacketsLate);
        }

        public void set(int i, int i2, int i3, int i4) {
            this.m_dwDuration = i;
            this.m_dwPacketsRcvd = i2;
            this.m_dwPacketsDrop = i3;
            this.m_dwPacketsLate = i4;
        }
    }

    public MM07() {
        this.mMediaStats = new HashMap();
        reset();
    }

    public MM07(Parcel parcel) {
        super(parcel);
        this.mMediaStats = new HashMap();
        if (parcel.readInt() >= 1) {
            int readInt = parcel.readInt();
            int i = 0;
            for (int i2 = 0; i2 < readInt; i2++) {
                short readInt2 = (short) parcel.readInt();
                RtpStats rtpStats = new RtpStats(parcel.readByte(), parcel.readByte(), i);
                rtpStats.m_dwDuration = parcel.readInt();
                rtpStats.m_dwPacketsRcvd = parcel.readInt();
                rtpStats.m_dwPacketsDrop = parcel.readInt();
                rtpStats.m_dwPacketsLate = parcel.readInt();
                this.mMediaStats.put(Short.valueOf(readInt2), rtpStats);
            }
        }
    }

    public int getDrop(short s) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats != null) {
            return rtpStats.m_dwPacketsDrop;
        }
        return 0;
    }

    public int getDuration(short s) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats != null) {
            return rtpStats.m_dwDuration;
        }
        return 0;
    }

    public byte getFormat(short s) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats != null) {
            return rtpStats.m_ucFormat;
        }
        return (byte) 0;
    }

    public int getLate(short s) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats != null) {
            return rtpStats.m_dwPacketsLate;
        }
        return 0;
    }

    public byte getMediaType(short s) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats != null) {
            return rtpStats.m_ucMediaType;
        }
        return (byte) 0;
    }

    public int getRcvd(short s) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats != null) {
            return rtpStats.m_dwPacketsRcvd;
        }
        return 0;
    }

    public void reset() {
        this.mMediaStats.clear();
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putShort((short) this.mMediaStats.size());
        for (Map.Entry entry : this.mMediaStats.entrySet()) {
            byteBuffer.putShort(((Short) entry.getKey()).shortValue());
            ((RtpStats) entry.getValue()).serialize(byteBuffer);
        }
        return byteBuffer.position();
    }

    public void setRtpStats(short s, byte b, byte b2, int i, int i2, int i3, int i4) {
        RtpStats rtpStats = (RtpStats) this.mMediaStats.get(Short.valueOf(s));
        if (rtpStats == null) {
            rtpStats = new RtpStats(b, b2, 0);
            this.mMediaStats.put(Short.valueOf(s), rtpStats);
        }
        rtpStats.set(i, i2, i3, i4);
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        int size = this.mMediaStats.size();
        parcel.writeInt(size);
        if (size > 0) {
            for (Map.Entry entry : this.mMediaStats.entrySet()) {
                parcel.writeInt(((Short) entry.getKey()).shortValue());
                RtpStats rtpStats = (RtpStats) entry.getValue();
                parcel.writeByte(rtpStats.m_ucMediaType);
                parcel.writeByte(rtpStats.m_ucFormat);
                parcel.writeInt(rtpStats.m_dwDuration);
                parcel.writeInt(rtpStats.m_dwPacketsRcvd);
                parcel.writeInt(rtpStats.m_dwPacketsDrop);
                parcel.writeInt(rtpStats.m_dwPacketsLate);
            }
        }
    }
}
