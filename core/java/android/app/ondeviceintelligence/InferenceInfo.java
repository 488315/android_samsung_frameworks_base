package android.app.ondeviceintelligence;

import android.os.Parcel;
import android.os.Parcelable;

public class InferenceInfo implements Parcelable {
    public static final Parcelable.Creator<InferenceInfo> CREATOR =
            new Parcelable.Creator<InferenceInfo>() { // from class:
                // android.app.ondeviceintelligence.InferenceInfo.1
                @Override // android.os.Parcelable.Creator
                public InferenceInfo[] newArray(int size) {
                    return new InferenceInfo[size];
                }

                @Override // android.os.Parcelable.Creator
                public InferenceInfo createFromParcel(Parcel in) {
                    return new InferenceInfo(in);
                }
            };
    private final long endTimeMs;
    private final long startTimeMs;
    private final long suspendedTimeMs;
    private final int uid;

    public InferenceInfo(int uid, long startTimeMs, long endTimeMs, long suspendedTimeMs) {
        this.uid = uid;
        this.startTimeMs = startTimeMs;
        this.endTimeMs = endTimeMs;
        this.suspendedTimeMs = suspendedTimeMs;
    }

    protected InferenceInfo(Parcel in) {
        this.uid = in.readInt();
        this.startTimeMs = in.readLong();
        this.endTimeMs = in.readLong();
        this.suspendedTimeMs = in.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeLong(this.startTimeMs);
        dest.writeLong(this.endTimeMs);
        dest.writeLong(this.suspendedTimeMs);
    }

    public int getUid() {
        return this.uid;
    }

    public long getStartTimeMs() {
        return this.startTimeMs;
    }

    public long getEndTimeMs() {
        return this.endTimeMs;
    }

    public long getSuspendedTimeMs() {
        return this.suspendedTimeMs;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private long endTimeMs;
        private long startTimeMs;
        private long suspendedTimeMs;
        private int uid;

        public Builder setUid(int uid) {
            this.uid = uid;
            return this;
        }

        public Builder setStartTimeMs(long startTimeMs) {
            this.startTimeMs = startTimeMs;
            return this;
        }

        public Builder setEndTimeMs(long endTimeMs) {
            this.endTimeMs = endTimeMs;
            return this;
        }

        public Builder setSuspendedTimeMs(long suspendedTimeMs) {
            this.suspendedTimeMs = suspendedTimeMs;
            return this;
        }

        public InferenceInfo build() {
            return new InferenceInfo(
                    this.uid, this.startTimeMs, this.endTimeMs, this.suspendedTimeMs);
        }
    }
}
