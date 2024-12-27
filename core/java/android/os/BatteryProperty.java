package android.os;


/* loaded from: classes3.dex */
public class BatteryProperty implements Parcelable {
    public static final Parcelable.Creator<BatteryProperty> CREATOR =
            new Parcelable.Creator<BatteryProperty>() { // from class: android.os.BatteryProperty.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BatteryProperty createFromParcel(Parcel p) {
                    return new BatteryProperty(p);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BatteryProperty[] newArray(int size) {
                    return new BatteryProperty[size];
                }
            };
    private long mValueLong;
    private String mValueString;

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

    public void setLong(long val) {
        this.mValueLong = val;
    }

    public void setString(String val) {
        this.mValueString = val;
    }

    private BatteryProperty(Parcel p) {
        readFromParcel(p);
    }

    public void readFromParcel(Parcel p) {
        this.mValueLong = p.readLong();
        this.mValueString = p.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel p, int flags) {
        p.writeLong(this.mValueLong);
        p.writeString8(this.mValueString);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
