package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public class VoiceSpecificRegistrationInfo implements Parcelable {
    public static final Parcelable.Creator<VoiceSpecificRegistrationInfo> CREATOR = new Parcelable.Creator<VoiceSpecificRegistrationInfo>() { // from class: android.telephony.VoiceSpecificRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VoiceSpecificRegistrationInfo createFromParcel(Parcel parcel) {
            return new VoiceSpecificRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VoiceSpecificRegistrationInfo[] newArray(int i) {
            return new VoiceSpecificRegistrationInfo[i];
        }
    };
    public final boolean cssSupported;
    public final int defaultRoamingIndicator;
    public final int roamingIndicator;
    public final int systemIsInPrl;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VoiceSpecificRegistrationInfo(boolean z, int i, int i2, int i3) {
        this.cssSupported = z;
        this.roamingIndicator = i;
        this.systemIsInPrl = i2;
        this.defaultRoamingIndicator = i3;
    }

    VoiceSpecificRegistrationInfo(VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo) {
        this.cssSupported = voiceSpecificRegistrationInfo.cssSupported;
        this.roamingIndicator = voiceSpecificRegistrationInfo.roamingIndicator;
        this.systemIsInPrl = voiceSpecificRegistrationInfo.systemIsInPrl;
        this.defaultRoamingIndicator = voiceSpecificRegistrationInfo.defaultRoamingIndicator;
    }

    private VoiceSpecificRegistrationInfo(Parcel parcel) {
        this.cssSupported = parcel.readBoolean();
        this.roamingIndicator = parcel.readInt();
        this.systemIsInPrl = parcel.readInt();
        this.defaultRoamingIndicator = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.cssSupported);
        parcel.writeInt(this.roamingIndicator);
        parcel.writeInt(this.systemIsInPrl);
        parcel.writeInt(this.defaultRoamingIndicator);
    }

    public String toString() {
        return "VoiceSpecificRegistrationInfo { mCssSupported=" + this.cssSupported + " mRoamingIndicator=" + this.roamingIndicator + " mSystemIsInPrl=" + this.systemIsInPrl + " mDefaultRoamingIndicator=" + this.defaultRoamingIndicator + "}";
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.cssSupported), Integer.valueOf(this.roamingIndicator), Integer.valueOf(this.systemIsInPrl), Integer.valueOf(this.defaultRoamingIndicator));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof VoiceSpecificRegistrationInfo)) {
            VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo = (VoiceSpecificRegistrationInfo) obj;
            if (this.cssSupported == voiceSpecificRegistrationInfo.cssSupported && this.roamingIndicator == voiceSpecificRegistrationInfo.roamingIndicator && this.systemIsInPrl == voiceSpecificRegistrationInfo.systemIsInPrl && this.defaultRoamingIndicator == voiceSpecificRegistrationInfo.defaultRoamingIndicator) {
                return true;
            }
        }
        return false;
    }
}
