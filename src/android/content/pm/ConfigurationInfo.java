package android.content.pm;

import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ConfigurationInfo implements Parcelable {
    public static final Parcelable.Creator<ConfigurationInfo> CREATOR = new Parcelable.Creator<ConfigurationInfo>() { // from class: android.content.pm.ConfigurationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationInfo createFromParcel(Parcel parcel) {
            return new ConfigurationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationInfo[] newArray(int i) {
            return new ConfigurationInfo[i];
        }
    };
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public static final int INPUT_FEATURE_FIVE_WAY_NAV = 2;
    public static final int INPUT_FEATURE_HARD_KEYBOARD = 1;
    public int reqGlEsVersion;
    public int reqInputFeatures;
    public int reqKeyboardType;
    public int reqNavigation;
    public int reqTouchScreen;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConfigurationInfo() {
        this.reqInputFeatures = 0;
    }

    public ConfigurationInfo(ConfigurationInfo configurationInfo) {
        this.reqInputFeatures = 0;
        this.reqTouchScreen = configurationInfo.reqTouchScreen;
        this.reqKeyboardType = configurationInfo.reqKeyboardType;
        this.reqNavigation = configurationInfo.reqNavigation;
        this.reqInputFeatures = configurationInfo.reqInputFeatures;
        this.reqGlEsVersion = configurationInfo.reqGlEsVersion;
    }

    public String toString() {
        return "ConfigurationInfo{" + Integer.toHexString(System.identityHashCode(this)) + " touchscreen = " + this.reqTouchScreen + " inputMethod = " + this.reqKeyboardType + " navigation = " + this.reqNavigation + " reqInputFeatures = " + this.reqInputFeatures + " reqGlEsVersion = " + this.reqGlEsVersion + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.reqTouchScreen);
        parcel.writeInt(this.reqKeyboardType);
        parcel.writeInt(this.reqNavigation);
        parcel.writeInt(this.reqInputFeatures);
        parcel.writeInt(this.reqGlEsVersion);
    }

    private ConfigurationInfo(Parcel parcel) {
        this.reqInputFeatures = 0;
        this.reqTouchScreen = parcel.readInt();
        this.reqKeyboardType = parcel.readInt();
        this.reqNavigation = parcel.readInt();
        this.reqInputFeatures = parcel.readInt();
        this.reqGlEsVersion = parcel.readInt();
    }

    public String getGlEsVersion() {
        int i = this.reqGlEsVersion;
        return String.valueOf(((-65536) & i) >> 16) + MediaMetrics.SEPARATOR + String.valueOf(i & 65535);
    }
}
