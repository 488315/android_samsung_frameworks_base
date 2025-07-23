package android.view;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes4.dex */
public final class WindowAnimationFrameStats extends FrameStats implements Parcelable {
    public static final Parcelable.Creator<WindowAnimationFrameStats> CREATOR = new Parcelable.Creator<WindowAnimationFrameStats>() { // from class: android.view.WindowAnimationFrameStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowAnimationFrameStats createFromParcel(Parcel parcel) {
            return new WindowAnimationFrameStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowAnimationFrameStats[] newArray(int i) {
            return new WindowAnimationFrameStats[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WindowAnimationFrameStats() {
    }

    public void init(long j, long[] jArr) {
        this.mRefreshPeriodNano = j;
        this.mFramesPresentedTimeNano = jArr;
    }

    private WindowAnimationFrameStats(Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mRefreshPeriodNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WindowAnimationFrameStats[");
        sb.append("frameCount:" + getFrameCount());
        sb.append(", fromTimeNano:" + getStartTimeNano());
        sb.append(", toTimeNano:" + getEndTimeNano());
        sb.append(']');
        return sb.toString();
    }
}
