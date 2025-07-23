package android.media.projection;

import android.app.ActivityOptions;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaProjectionInfo implements Parcelable {
    public static final Parcelable.Creator<MediaProjectionInfo> CREATOR = new Parcelable.Creator<MediaProjectionInfo>() { // from class: android.media.projection.MediaProjectionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionInfo createFromParcel(Parcel parcel) {
            return new MediaProjectionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionInfo[] newArray(int i) {
            return new MediaProjectionInfo[i];
        }
    };
    private final ActivityOptions.LaunchCookie mLaunchCookie;
    private final String mPackageName;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaProjectionInfo(String str, UserHandle userHandle, ActivityOptions.LaunchCookie launchCookie) {
        this.mPackageName = str;
        this.mUserHandle = userHandle;
        this.mLaunchCookie = launchCookie;
    }

    public MediaProjectionInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mUserHandle = UserHandle.readFromParcel(parcel);
        this.mLaunchCookie = ActivityOptions.LaunchCookie.readFromParcel(parcel);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public ActivityOptions.LaunchCookie getLaunchCookie() {
        return this.mLaunchCookie;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MediaProjectionInfo) {
            MediaProjectionInfo mediaProjectionInfo = (MediaProjectionInfo) obj;
            if (Objects.equals(mediaProjectionInfo.mPackageName, this.mPackageName) && Objects.equals(mediaProjectionInfo.mUserHandle, this.mUserHandle) && Objects.equals(mediaProjectionInfo.mLaunchCookie, this.mLaunchCookie)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mUserHandle);
    }

    public String toString() {
        return "MediaProjectionInfo{mPackageName=" + this.mPackageName + ", mUserHandle=" + this.mUserHandle + ", mLaunchCookie=" + this.mLaunchCookie + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        UserHandle.writeToParcel(this.mUserHandle, parcel);
        ActivityOptions.LaunchCookie.writeToParcel(this.mLaunchCookie, parcel);
    }
}
