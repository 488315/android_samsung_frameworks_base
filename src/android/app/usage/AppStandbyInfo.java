package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class AppStandbyInfo implements Parcelable {
    public static final Parcelable.Creator<AppStandbyInfo> CREATOR = new Parcelable.Creator<AppStandbyInfo>() { // from class: android.app.usage.AppStandbyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppStandbyInfo createFromParcel(Parcel parcel) {
            return new AppStandbyInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppStandbyInfo[] newArray(int i) {
            return new AppStandbyInfo[i];
        }
    };
    public String mPackageName;
    public int mStandbyBucket;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AppStandbyInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mStandbyBucket = parcel.readInt();
    }

    public AppStandbyInfo(String str, int i) {
        this.mPackageName = str;
        this.mStandbyBucket = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mStandbyBucket);
    }
}
