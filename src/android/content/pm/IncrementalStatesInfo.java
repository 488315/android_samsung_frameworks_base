package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class IncrementalStatesInfo implements Parcelable {
    public static final Parcelable.Creator<IncrementalStatesInfo> CREATOR = new Parcelable.Creator<IncrementalStatesInfo>() { // from class: android.content.pm.IncrementalStatesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncrementalStatesInfo createFromParcel(Parcel parcel) {
            return new IncrementalStatesInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncrementalStatesInfo[] newArray(int i) {
            return new IncrementalStatesInfo[i];
        }
    };
    private final boolean mIsLoading;
    private long mLoadingCompletedTime;
    private final float mProgress;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IncrementalStatesInfo(boolean z, float f, long j) {
        this.mIsLoading = z;
        this.mProgress = f;
        this.mLoadingCompletedTime = j;
    }

    private IncrementalStatesInfo(Parcel parcel) {
        this.mIsLoading = parcel.readBoolean();
        this.mProgress = parcel.readFloat();
        this.mLoadingCompletedTime = parcel.readLong();
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsLoading);
        parcel.writeFloat(this.mProgress);
        parcel.writeLong(this.mLoadingCompletedTime);
    }
}
