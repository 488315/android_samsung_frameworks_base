package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class AppLaunchEstimateInfo implements Parcelable {
    public static final Parcelable.Creator<AppLaunchEstimateInfo> CREATOR = new Parcelable.Creator<AppLaunchEstimateInfo>() { // from class: android.app.usage.AppLaunchEstimateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppLaunchEstimateInfo createFromParcel(Parcel parcel) {
            return new AppLaunchEstimateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppLaunchEstimateInfo[] newArray(int i) {
            return new AppLaunchEstimateInfo[i];
        }
    };
    public final long estimatedLaunchTime;
    public final String packageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AppLaunchEstimateInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.estimatedLaunchTime = parcel.readLong();
    }

    public AppLaunchEstimateInfo(String str, long j) {
        this.packageName = str;
        this.estimatedLaunchTime = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeLong(this.estimatedLaunchTime);
    }
}
