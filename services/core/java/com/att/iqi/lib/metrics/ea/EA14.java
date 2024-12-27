package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class EA14 extends Metric {
    private final int lSerialNumber;
    private final int lServiceCategory;
    public static final Metric.ID ID = new Metric.ID("EA14");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.ea.EA14.1
                @Override // android.os.Parcelable.Creator
                public EA14 createFromParcel(Parcel parcel) {
                    return new EA14(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public EA14[] newArray(int i) {
                    return new EA14[i];
                }
            };

    public EA14(int i, int i2) {
        this.lServiceCategory = i;
        this.lSerialNumber = i2;
    }

    public EA14(Parcel parcel) {
        super(parcel);
        parcel.readInt();
        this.lServiceCategory = parcel.readInt();
        this.lSerialNumber = parcel.readInt();
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.lServiceCategory);
        byteBuffer.putInt(this.lSerialNumber);
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.lServiceCategory);
        parcel.writeInt(this.lSerialNumber);
    }
}
