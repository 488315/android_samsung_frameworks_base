package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class SmsRawData implements Parcelable {
    public static final Parcelable.Creator<SmsRawData> CREATOR =
            new Parcelable.Creator<
                    SmsRawData>() { // from class: com.android.internal.telephony.SmsRawData.1
                @Override // android.os.Parcelable.Creator
                public SmsRawData createFromParcel(Parcel source) {
                    int size = source.readInt();
                    byte[] data = new byte[size];
                    source.readByteArray(data);
                    return new SmsRawData(data);
                }

                @Override // android.os.Parcelable.Creator
                public SmsRawData[] newArray(int size) {
                    return new SmsRawData[size];
                }
            };
    byte[] data;

    public SmsRawData(byte[] data) {
        this.data = data;
    }

    public byte[] getBytes() {
        return this.data;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.data.length);
        dest.writeByteArray(this.data);
    }
}
