package android.service.contentcapture;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class FlushMetrics implements Parcelable {
    public static final Parcelable.Creator<FlushMetrics> CREATOR = new Parcelable.Creator<FlushMetrics>() { // from class: android.service.contentcapture.FlushMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FlushMetrics createFromParcel(Parcel parcel) {
            FlushMetrics flushMetrics = new FlushMetrics();
            flushMetrics.sessionStarted = parcel.readInt();
            flushMetrics.sessionFinished = parcel.readInt();
            flushMetrics.viewAppearedCount = parcel.readInt();
            flushMetrics.viewDisappearedCount = parcel.readInt();
            flushMetrics.viewTextChangedCount = parcel.readInt();
            return flushMetrics;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FlushMetrics[] newArray(int i) {
            return new FlushMetrics[i];
        }
    };
    public int sessionFinished;
    public int sessionStarted;
    public int viewAppearedCount;
    public int viewDisappearedCount;
    public int viewTextChangedCount;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void reset() {
        this.viewAppearedCount = 0;
        this.viewDisappearedCount = 0;
        this.viewTextChangedCount = 0;
        this.sessionStarted = 0;
        this.sessionFinished = 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.sessionStarted);
        parcel.writeInt(this.sessionFinished);
        parcel.writeInt(this.viewAppearedCount);
        parcel.writeInt(this.viewDisappearedCount);
        parcel.writeInt(this.viewTextChangedCount);
    }
}
