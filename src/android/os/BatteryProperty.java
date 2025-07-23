package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class BatteryProperty implements Parcelable {
    public static final Parcelable.Creator<BatteryProperty> CREATOR = new Parcelable.Creator<BatteryProperty>() { // from class: android.os.BatteryProperty.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryProperty createFromParcel(Parcel parcel) {
            return new BatteryProperty(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryProperty[] newArray(int i) {
            return new BatteryProperty[i];
        }
    };
    private long mValueLong;
    private String mValueString;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BatteryProperty() {
        this.mValueLong = Long.MIN_VALUE;
        this.mValueString = null;
    }

    public long getLong() {
        return this.mValueLong;
    }

    public String getString() {
        return this.mValueString;
    }

    public void setLong(long j) {
        this.mValueLong = j;
    }

    public void setString(String str) {
        this.mValueString = str;
    }

    private BatteryProperty(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mValueLong = parcel.readLong();
        this.mValueString = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mValueLong);
        parcel.writeString8(this.mValueString);
    }
}
