package com.att.iqi.lib.metrics.sp;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class SPTX extends Metric {
    private int m_dwCSeq;
    private int m_dwTransId;
    private String m_strMessage;
    public static final Metric.ID ID = new Metric.ID("SPTX");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.sp.SPTX.1
                @Override // android.os.Parcelable.Creator
                public SPTX createFromParcel(Parcel parcel) {
                    return new SPTX(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public SPTX[] newArray(int i) {
                    return new SPTX[i];
                }
            };

    public SPTX() {
        reset();
    }

    public SPTX(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_dwTransId = parcel.readInt();
            this.m_dwCSeq = parcel.readInt();
            this.m_strMessage = parcel.readString();
        }
    }

    public int getCSeq() {
        return this.m_dwCSeq;
    }

    public String getMessage() {
        return this.m_strMessage;
    }

    public int getTransId() {
        return this.m_dwTransId;
    }

    public void reset() {
        this.m_dwTransId = 0;
        this.m_dwCSeq = 0;
        this.m_strMessage = "";
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.m_dwTransId);
        byteBuffer.putInt(this.m_dwCSeq);
        String str = this.m_strMessage;
        int length = str == null ? 0 : str.length();
        byteBuffer.putInt(length);
        if (length > 0) {
            byteBuffer.put(this.m_strMessage.getBytes(StandardCharsets.US_ASCII));
        }
        return byteBuffer.position();
    }

    public SPTX setCSeq(int i) {
        this.m_dwCSeq = i;
        return this;
    }

    public SPTX setMessage(String str) {
        this.m_strMessage = str;
        return this;
    }

    public SPTX setTransId(int i) {
        this.m_dwTransId = i;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.m_dwTransId);
        parcel.writeInt(this.m_dwCSeq);
        parcel.writeString(this.m_strMessage);
    }
}
