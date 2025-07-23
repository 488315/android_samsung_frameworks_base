package android.window;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes5.dex */
public class WindowContextInfo implements Parcelable {
    public static final Parcelable.Creator<WindowContextInfo> CREATOR = new Parcelable.Creator<WindowContextInfo>() { // from class: android.window.WindowContextInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextInfo createFromParcel(Parcel parcel) {
            return new WindowContextInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContextInfo[] newArray(int i) {
            return new WindowContextInfo[i];
        }
    };
    private final Configuration mConfiguration;
    private final int mDisplayId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WindowContextInfo(Configuration configuration, int i) {
        this.mConfiguration = (Configuration) Objects.requireNonNull(configuration);
        this.mDisplayId = i;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mConfiguration, i);
        parcel.writeInt(this.mDisplayId);
    }

    private WindowContextInfo(Parcel parcel) {
        this.mConfiguration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
        this.mDisplayId = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            WindowContextInfo windowContextInfo = (WindowContextInfo) obj;
            if (Objects.equals(this.mConfiguration, windowContextInfo.mConfiguration) && this.mDisplayId == windowContextInfo.mDisplayId) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((527 + Objects.hashCode(this.mConfiguration)) * 31) + this.mDisplayId;
    }

    public String toString() {
        return "WindowContextInfo{config=" + this.mConfiguration + ", displayId=" + this.mDisplayId + "}";
    }
}
