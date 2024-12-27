package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class AppLaunchEstimateInfo implements Parcelable {
    public static final Parcelable.Creator<AppLaunchEstimateInfo> CREATOR =
            new Parcelable.Creator<AppLaunchEstimateInfo>() { // from class:
                // android.app.usage.AppLaunchEstimateInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AppLaunchEstimateInfo createFromParcel(Parcel source) {
                    return new AppLaunchEstimateInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AppLaunchEstimateInfo[] newArray(int size) {
                    return new AppLaunchEstimateInfo[size];
                }
            };
    public final long estimatedLaunchTime;
    public final String packageName;

    private AppLaunchEstimateInfo(Parcel in) {
        this.packageName = in.readString();
        this.estimatedLaunchTime = in.readLong();
    }

    public AppLaunchEstimateInfo(String packageName, long estimatedLaunchTime) {
        this.packageName = packageName;
        this.estimatedLaunchTime = estimatedLaunchTime;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeLong(this.estimatedLaunchTime);
    }
}
