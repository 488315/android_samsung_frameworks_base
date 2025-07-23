package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class WindowContentFrameStats extends FrameStats implements Parcelable {
    public static final Parcelable.Creator<WindowContentFrameStats> CREATOR = new Parcelable.Creator<WindowContentFrameStats>() { // from class: android.view.WindowContentFrameStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContentFrameStats createFromParcel(Parcel parcel) {
            return new WindowContentFrameStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContentFrameStats[] newArray(int i) {
            return new WindowContentFrameStats[i];
        }
    };
    private long[] mFramesPostedTimeNano;
    private long[] mFramesReadyTimeNano;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WindowContentFrameStats() {
    }

    public void init(long j, long[] jArr, long[] jArr2, long[] jArr3) {
        this.mRefreshPeriodNano = j;
        this.mFramesPostedTimeNano = jArr;
        this.mFramesPresentedTimeNano = jArr2;
        this.mFramesReadyTimeNano = jArr3;
    }

    private WindowContentFrameStats(Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPostedTimeNano = parcel.createLongArray();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
        this.mFramesReadyTimeNano = parcel.createLongArray();
    }

    public long getFramePostedTimeNano(int i) {
        long[] jArr = this.mFramesPostedTimeNano;
        if (jArr == null) {
            throw new IndexOutOfBoundsException();
        }
        return jArr[i];
    }

    public long getFrameReadyTimeNano(int i) {
        long[] jArr = this.mFramesReadyTimeNano;
        if (jArr == null) {
            throw new IndexOutOfBoundsException();
        }
        return jArr[i];
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mRefreshPeriodNano);
        parcel.writeLongArray(this.mFramesPostedTimeNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
        parcel.writeLongArray(this.mFramesReadyTimeNano);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WindowContentFrameStats[");
        sb.append("frameCount:" + getFrameCount());
        sb.append(", fromTimeNano:" + getStartTimeNano());
        sb.append(", toTimeNano:" + getEndTimeNano());
        sb.append(']');
        return sb.toString();
    }
}
