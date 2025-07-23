package android.os.logcat;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class SecurityLogEvent implements Parcelable {
    public static final Parcelable.Creator<SecurityLogEvent> CREATOR = new Parcelable.Creator<SecurityLogEvent>() { // from class: android.os.logcat.SecurityLogEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecurityLogEvent createFromParcel(Parcel parcel) {
            return new SecurityLogEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecurityLogEvent[] newArray(int i) {
            return new SecurityLogEvent[i];
        }
    };
    private final List<Object> mParams;
    private int mTag;

    @Retention(RetentionPolicy.SOURCE)
    @interface DataType {
        public static final int FLOAT = 2;
        public static final int INT = 0;
        public static final int LONG = 1;
        public static final int STRING = 3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SecurityLogEvent() {
        this.mParams = new ArrayList();
    }

    public void setTag(int i) {
        this.mTag = i;
    }

    public int getTag() {
        return this.mTag;
    }

    public void addParam(Object obj) {
        this.mParams.add(obj);
    }

    public List<Object> getParams() {
        return this.mParams;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTag);
        parcel.writeInt(this.mParams.size());
        for (Object obj : this.mParams) {
            Objects.requireNonNull(obj);
            int m = SecurityLogEvent$$ExternalSyntheticTypeSwitch1.m(obj, 0, SecurityLogEvent$$ExternalSyntheticTypeSwitch1.getSwitchCases());
            if (m == 0) {
                parcel.writeInt(0);
                parcel.writeInt(((Integer) obj).intValue());
            } else if (m == 1) {
                parcel.writeInt(1);
                parcel.writeLong(((Long) obj).longValue());
            } else if (m == 2) {
                parcel.writeInt(2);
                parcel.writeFloat(((Float) obj).floatValue());
            } else if (m == 3) {
                parcel.writeInt(3);
                parcel.writeString16((String) obj);
            }
        }
    }

    private void readFromParcel(Parcel parcel) {
        this.mTag = parcel.readInt();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parcel.readInt();
            if (readInt2 == 0) {
                this.mParams.add(Integer.valueOf(parcel.readInt()));
            } else if (readInt2 == 1) {
                this.mParams.add(Long.valueOf(parcel.readLong()));
            } else if (readInt2 == 2) {
                this.mParams.add(Float.valueOf(parcel.readFloat()));
            } else if (readInt2 == 3) {
                this.mParams.add(parcel.readString16());
            }
        }
    }

    private SecurityLogEvent(Parcel parcel) {
        this.mParams = new ArrayList();
        readFromParcel(parcel);
    }

    public String toString() {
        return "SecurityLogEvent{tag=" + this.mTag + ", params=" + Arrays.toString(this.mParams.toArray()) + "}";
    }
}
