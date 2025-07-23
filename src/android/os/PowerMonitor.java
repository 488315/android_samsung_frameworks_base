package android.os;

import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class PowerMonitor implements Parcelable {
    public static final Parcelable.Creator<PowerMonitor> CREATOR = new Parcelable.Creator<PowerMonitor>() { // from class: android.os.PowerMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerMonitor createFromParcel(Parcel parcel) {
            return new PowerMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerMonitor[] newArray(int i) {
            return new PowerMonitor[i];
        }
    };
    public static final int POWER_MONITOR_TYPE_CONSUMER = 0;
    public static final int POWER_MONITOR_TYPE_MEASUREMENT = 1;
    public final int index;
    private final String mName;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerMonitorType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PowerMonitor(int i, int i2, String str) {
        this.index = i;
        this.mType = i2;
        this.mName = str;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    private PowerMonitor(Parcel parcel) {
        this.index = parcel.readInt();
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.index);
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
    }
}
