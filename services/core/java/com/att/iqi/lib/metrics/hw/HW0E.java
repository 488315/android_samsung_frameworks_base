package com.att.iqi.lib.metrics.hw;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class HW0E extends Metric {
    private byte ucBatteryEvent;
    public static final Metric.ID ID = new Metric.ID("HW0E");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.hw.HW0E.1
                @Override // android.os.Parcelable.Creator
                public HW0E createFromParcel(Parcel parcel) {
                    return new HW0E(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public HW0E[] newArray(int i) {
                    return new HW0E[i];
                }
            };

    public HW0E() {}

    public HW0E(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.ucBatteryEvent = parcel.readByte();
        }
    }

    public byte getEvent() {
        return this.ucBatteryEvent;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.put(this.ucBatteryEvent);
        return byteBuffer.position();
    }

    public HW0E setEvent(byte b) {
        this.ucBatteryEvent = b;
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.ucBatteryEvent);
    }
}
