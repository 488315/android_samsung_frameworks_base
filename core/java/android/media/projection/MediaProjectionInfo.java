package android.media.projection;

import android.app.ActivityOptions;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaProjectionInfo implements Parcelable {
    public static final Parcelable.Creator<MediaProjectionInfo> CREATOR =
            new Parcelable.Creator<MediaProjectionInfo>() { // from class:
                // android.media.projection.MediaProjectionInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MediaProjectionInfo createFromParcel(Parcel in) {
                    return new MediaProjectionInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MediaProjectionInfo[] newArray(int size) {
                    return new MediaProjectionInfo[size];
                }
            };
    private final ActivityOptions.LaunchCookie mLaunchCookie;
    private final String mPackageName;
    private final UserHandle mUserHandle;

    public MediaProjectionInfo(
            String packageName, UserHandle handle, ActivityOptions.LaunchCookie launchCookie) {
        this.mPackageName = packageName;
        this.mUserHandle = handle;
        this.mLaunchCookie = launchCookie;
    }

    public MediaProjectionInfo(Parcel in) {
        this.mPackageName = in.readString();
        this.mUserHandle = UserHandle.readFromParcel(in);
        this.mLaunchCookie = ActivityOptions.LaunchCookie.readFromParcel(in);
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

    public boolean equals(Object o) {
        if (!(o instanceof MediaProjectionInfo)) {
            return false;
        }
        MediaProjectionInfo other = (MediaProjectionInfo) o;
        return Objects.equals(other.mPackageName, this.mPackageName)
                && Objects.equals(other.mUserHandle, this.mUserHandle)
                && Objects.equals(other.mLaunchCookie, this.mLaunchCookie);
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mUserHandle);
    }

    public String toString() {
        return "MediaProjectionInfo{mPackageName="
                + this.mPackageName
                + ", mUserHandle="
                + this.mUserHandle
                + ", mLaunchCookie="
                + this.mLaunchCookie
                + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mPackageName);
        UserHandle.writeToParcel(this.mUserHandle, out);
        ActivityOptions.LaunchCookie.writeToParcel(this.mLaunchCookie, out);
    }
}
