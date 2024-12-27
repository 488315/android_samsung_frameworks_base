package android.window;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class WindowContextInfo implements Parcelable {
    public static final Parcelable.Creator<WindowContextInfo> CREATOR =
            new Parcelable.Creator<
                    WindowContextInfo>() { // from class: android.window.WindowContextInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowContextInfo createFromParcel(Parcel in) {
                    return new WindowContextInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public WindowContextInfo[] newArray(int size) {
                    return new WindowContextInfo[size];
                }
            };
    private final Configuration mConfiguration;
    private final int mDisplayId;

    public WindowContextInfo(Configuration configuration, int displayId) {
        this.mConfiguration = (Configuration) Objects.requireNonNull(configuration);
        this.mDisplayId = displayId;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mConfiguration, flags);
        dest.writeInt(this.mDisplayId);
    }

    private WindowContextInfo(Parcel in) {
        this.mConfiguration = (Configuration) in.readTypedObject(Configuration.CREATOR);
        this.mDisplayId = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WindowContextInfo other = (WindowContextInfo) o;
        if (Objects.equals(this.mConfiguration, other.mConfiguration)
                && this.mDisplayId == other.mDisplayId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (17 * 31) + Objects.hashCode(this.mConfiguration);
        return (result * 31) + this.mDisplayId;
    }

    public String toString() {
        return "WindowContextInfo{config="
                + this.mConfiguration
                + ", displayId="
                + this.mDisplayId
                + "}";
    }
}
