package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/* loaded from: classes4.dex */
public class VoiceSpecificRegistrationInfo implements Parcelable {
    public static final Parcelable.Creator<VoiceSpecificRegistrationInfo> CREATOR =
            new Parcelable.Creator<
                    VoiceSpecificRegistrationInfo>() { // from class:
                                                       // android.telephony.VoiceSpecificRegistrationInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VoiceSpecificRegistrationInfo createFromParcel(Parcel source) {
                    return new VoiceSpecificRegistrationInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VoiceSpecificRegistrationInfo[] newArray(int size) {
                    return new VoiceSpecificRegistrationInfo[size];
                }
            };
    public final boolean cssSupported;
    public final int defaultRoamingIndicator;
    public final int roamingIndicator;
    public final int systemIsInPrl;

    VoiceSpecificRegistrationInfo(
            boolean cssSupported,
            int roamingIndicator,
            int systemIsInPrl,
            int defaultRoamingIndicator) {
        this.cssSupported = cssSupported;
        this.roamingIndicator = roamingIndicator;
        this.systemIsInPrl = systemIsInPrl;
        this.defaultRoamingIndicator = defaultRoamingIndicator;
    }

    VoiceSpecificRegistrationInfo(VoiceSpecificRegistrationInfo vsri) {
        this.cssSupported = vsri.cssSupported;
        this.roamingIndicator = vsri.roamingIndicator;
        this.systemIsInPrl = vsri.systemIsInPrl;
        this.defaultRoamingIndicator = vsri.defaultRoamingIndicator;
    }

    private VoiceSpecificRegistrationInfo(Parcel source) {
        this.cssSupported = source.readBoolean();
        this.roamingIndicator = source.readInt();
        this.systemIsInPrl = source.readInt();
        this.defaultRoamingIndicator = source.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.cssSupported);
        dest.writeInt(this.roamingIndicator);
        dest.writeInt(this.systemIsInPrl);
        dest.writeInt(this.defaultRoamingIndicator);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "VoiceSpecificRegistrationInfo { mCssSupported="
                + this.cssSupported
                + " mRoamingIndicator="
                + this.roamingIndicator
                + " mSystemIsInPrl="
                + this.systemIsInPrl
                + " mDefaultRoamingIndicator="
                + this.defaultRoamingIndicator
                + "}";
    }

    public int hashCode() {
        return Objects.hash(
                Boolean.valueOf(this.cssSupported),
                Integer.valueOf(this.roamingIndicator),
                Integer.valueOf(this.systemIsInPrl),
                Integer.valueOf(this.defaultRoamingIndicator));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof VoiceSpecificRegistrationInfo)) {
            return false;
        }
        VoiceSpecificRegistrationInfo other = (VoiceSpecificRegistrationInfo) o;
        if (this.cssSupported == other.cssSupported
                && this.roamingIndicator == other.roamingIndicator
                && this.systemIsInPrl == other.systemIsInPrl
                && this.defaultRoamingIndicator == other.defaultRoamingIndicator) {
            return true;
        }
        return false;
    }
}
