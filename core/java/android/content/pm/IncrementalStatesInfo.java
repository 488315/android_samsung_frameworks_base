package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

public class IncrementalStatesInfo implements Parcelable {
    public static final Parcelable.Creator<IncrementalStatesInfo> CREATOR =
            new Parcelable.Creator<IncrementalStatesInfo>() { // from class:
                // android.content.pm.IncrementalStatesInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public IncrementalStatesInfo createFromParcel(Parcel source) {
                    return new IncrementalStatesInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public IncrementalStatesInfo[] newArray(int size) {
                    return new IncrementalStatesInfo[size];
                }
            };
    private final boolean mIsLoading;
    private long mLoadingCompletedTime;
    private final float mProgress;

    public IncrementalStatesInfo(boolean isLoading, float progress, long loadingCompletedTime) {
        this.mIsLoading = isLoading;
        this.mProgress = progress;
        this.mLoadingCompletedTime = loadingCompletedTime;
    }

    private IncrementalStatesInfo(Parcel source) {
        this.mIsLoading = source.readBoolean();
        this.mProgress = source.readFloat();
        this.mLoadingCompletedTime = source.readLong();
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public long getLoadingCompletedTime() {
        return this.mLoadingCompletedTime;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mIsLoading);
        dest.writeFloat(this.mProgress);
        dest.writeLong(this.mLoadingCompletedTime);
    }
}
