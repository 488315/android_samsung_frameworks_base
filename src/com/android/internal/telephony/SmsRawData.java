package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SmsRawData implements Parcelable {
    public static final Parcelable.Creator<SmsRawData> CREATOR = new Parcelable.Creator<SmsRawData>() { // from class: com.android.internal.telephony.SmsRawData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsRawData createFromParcel(Parcel parcel) {
            byte[] bArr = new byte[parcel.readInt()];
            parcel.readByteArray(bArr);
            return new SmsRawData(bArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsRawData[] newArray(int i) {
            return new SmsRawData[i];
        }
    };
    byte[] data;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmsRawData(byte[] bArr) {
        this.data = bArr;
    }

    public byte[] getBytes() {
        return this.data;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.data.length);
        parcel.writeByteArray(this.data);
    }
}
