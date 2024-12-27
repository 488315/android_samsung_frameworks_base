package android.os;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PowerMonitor implements Parcelable {
    public static final Parcelable.Creator<PowerMonitor> CREATOR =
            new Parcelable.Creator<PowerMonitor>() { // from class: android.os.PowerMonitor.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PowerMonitor createFromParcel(Parcel in) {
                    return new PowerMonitor(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PowerMonitor[] newArray(int size) {
                    return new PowerMonitor[size];
                }
            };
    public static final int POWER_MONITOR_TYPE_CONSUMER = 0;
    public static final int POWER_MONITOR_TYPE_MEASUREMENT = 1;
    public final int index;
    private final String mName;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerMonitorType {}

    public PowerMonitor(int index, int type, String name) {
        this.index = index;
        this.mType = type;
        this.mName = name;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    private PowerMonitor(Parcel in) {
        this.index = in.readInt();
        this.mType = in.readInt();
        this.mName = in.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeInt(this.mType);
        dest.writeString8(this.mName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
