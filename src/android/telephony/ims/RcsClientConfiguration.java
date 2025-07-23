package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class RcsClientConfiguration implements Parcelable {
    public static final Parcelable.Creator<RcsClientConfiguration> CREATOR = new Parcelable.Creator<RcsClientConfiguration>() { // from class: android.telephony.ims.RcsClientConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsClientConfiguration createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            Boolean.valueOf(readBoolean).getClass();
            return new RcsClientConfiguration(readString, readString2, readString3, readString4, readBoolean);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsClientConfiguration[] newArray(int i) {
            return new RcsClientConfiguration[i];
        }
    };
    public static final String RCS_PROFILE_1_0 = "UP_1.0";
    public static final String RCS_PROFILE_2_3 = "UP_2.3";
    public static final String RCS_PROFILE_2_4 = "UP_2.4";
    private String mClientVendor;
    private String mClientVersion;
    private boolean mRcsEnabledByUser;
    private String mRcsProfile;
    private String mRcsVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StringRcsProfile {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public RcsClientConfiguration(String str, String str2, String str3, String str4) {
        this(str, str2, str3, str4, true);
    }

    public RcsClientConfiguration(String str, String str2, String str3, String str4, boolean z) {
        this.mRcsVersion = str;
        this.mRcsProfile = str2;
        this.mClientVendor = str3;
        this.mClientVersion = str4;
        this.mRcsEnabledByUser = z;
    }

    public String getRcsVersion() {
        return this.mRcsVersion;
    }

    public String getRcsProfile() {
        return this.mRcsProfile;
    }

    public String getClientVendor() {
        return this.mClientVendor;
    }

    public String getClientVersion() {
        return this.mClientVersion;
    }

    public boolean isRcsEnabledByUser() {
        return this.mRcsEnabledByUser;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mRcsVersion);
        parcel.writeString(this.mRcsProfile);
        parcel.writeString(this.mClientVendor);
        parcel.writeString(this.mClientVersion);
        parcel.writeBoolean(this.mRcsEnabledByUser);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RcsClientConfiguration)) {
            return false;
        }
        RcsClientConfiguration rcsClientConfiguration = (RcsClientConfiguration) obj;
        return this.mRcsVersion.equals(rcsClientConfiguration.mRcsVersion) && this.mRcsProfile.equals(rcsClientConfiguration.mRcsProfile) && this.mClientVendor.equals(rcsClientConfiguration.mClientVendor) && this.mClientVersion.equals(rcsClientConfiguration.mClientVersion) && this.mRcsEnabledByUser == rcsClientConfiguration.mRcsEnabledByUser;
    }

    public int hashCode() {
        return Objects.hash(this.mRcsVersion, this.mRcsProfile, this.mClientVendor, this.mClientVersion, Boolean.valueOf(this.mRcsEnabledByUser));
    }
}
