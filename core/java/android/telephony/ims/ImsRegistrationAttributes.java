package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class ImsRegistrationAttributes implements Parcelable {
    public static final int ATTR_EPDG_OVER_CELL_INTERNET = 1;
    public static final int ATTR_REGISTRATION_TYPE_EMERGENCY = 2;
    public static final int ATTR_VIRTUAL_FOR_ANONYMOUS_EMERGENCY_CALL = 4;
    public static final Parcelable.Creator<ImsRegistrationAttributes> CREATOR =
            new Parcelable.Creator<
                    ImsRegistrationAttributes>() { // from class:
                                                   // android.telephony.ims.ImsRegistrationAttributes.1
                @Override // android.os.Parcelable.Creator
                public ImsRegistrationAttributes createFromParcel(Parcel source) {
                    return new ImsRegistrationAttributes(source);
                }

                @Override // android.os.Parcelable.Creator
                public ImsRegistrationAttributes[] newArray(int size) {
                    return new ImsRegistrationAttributes[size];
                }
            };
    private final ArrayList<String> mFeatureTags;
    private final int mImsAttributeFlags;
    private final int mRegistrationTech;
    private final SipDetails mSipDetails;
    private final int mTransportType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsAttributeFlag {}

    @SystemApi
    public static final class Builder {
        private int mAttributeFlags;
        private Set<String> mFeatureTags = Collections.emptySet();
        private final int mRegistrationTech;
        private SipDetails mSipDetails;

        public Builder(int registrationTech) {
            this.mRegistrationTech = registrationTech;
            if (registrationTech == 2) {
                this.mAttributeFlags |= 1;
            }
        }

        public Builder setFeatureTags(Set<String> tags) {
            if (tags == null) {
                throw new IllegalArgumentException("feature tag set must not be null");
            }
            this.mFeatureTags = new ArraySet(tags);
            return this;
        }

        public Builder setSipDetails(SipDetails details) {
            this.mSipDetails = details;
            return this;
        }

        public Builder setFlagRegistrationTypeEmergency() {
            this.mAttributeFlags |= 2;
            return this;
        }

        public Builder setFlagVirtualRegistrationForEmergencyCall() {
            this.mAttributeFlags |= 4;
            return this;
        }

        public ImsRegistrationAttributes build() {
            return new ImsRegistrationAttributes(
                    this.mRegistrationTech,
                    RegistrationManager.getAccessType(this.mRegistrationTech),
                    this.mAttributeFlags,
                    this.mFeatureTags,
                    this.mSipDetails);
        }
    }

    public ImsRegistrationAttributes(
            int registrationTech,
            int transportType,
            int imsAttributeFlags,
            Set<String> featureTags) {
        this.mRegistrationTech = registrationTech;
        this.mTransportType = transportType;
        this.mImsAttributeFlags = imsAttributeFlags;
        this.mFeatureTags = new ArrayList<>(featureTags);
        this.mSipDetails = null;
    }

    public ImsRegistrationAttributes(
            int registrationTech,
            int transportType,
            int imsAttributeFlags,
            Set<String> featureTags,
            SipDetails details) {
        this.mRegistrationTech = registrationTech;
        this.mTransportType = transportType;
        this.mImsAttributeFlags = imsAttributeFlags;
        this.mFeatureTags = new ArrayList<>(featureTags);
        this.mSipDetails = details;
    }

    public ImsRegistrationAttributes(Parcel source) {
        this.mRegistrationTech = source.readInt();
        this.mTransportType = source.readInt();
        this.mImsAttributeFlags = source.readInt();
        this.mFeatureTags = new ArrayList<>();
        source.readList(this.mFeatureTags, null, String.class);
        this.mSipDetails = (SipDetails) source.readParcelable(null, SipDetails.class);
    }

    @SystemApi
    public int getRegistrationTechnology() {
        return this.mRegistrationTech;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getAttributeFlags() {
        return this.mImsAttributeFlags;
    }

    public boolean getFlagRegistrationTypeEmergency() {
        return (this.mImsAttributeFlags & 2) != 0;
    }

    public boolean getFlagVirtualRegistrationForEmergencyCall() {
        return (this.mImsAttributeFlags & 4) != 0;
    }

    public Set<String> getFeatureTags() {
        if (this.mFeatureTags == null) {
            return Collections.emptySet();
        }
        return new ArraySet(this.mFeatureTags);
    }

    public SipDetails getSipDetails() {
        return this.mSipDetails;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRegistrationTech);
        dest.writeInt(this.mTransportType);
        dest.writeInt(this.mImsAttributeFlags);
        dest.writeList(this.mFeatureTags);
        dest.writeParcelable(this.mSipDetails, flags);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImsRegistrationAttributes that = (ImsRegistrationAttributes) o;
        if (this.mRegistrationTech == that.mRegistrationTech
                && this.mTransportType == that.mTransportType
                && this.mImsAttributeFlags == that.mImsAttributeFlags
                && Objects.equals(this.mFeatureTags, that.mFeatureTags)
                && Objects.equals(this.mSipDetails, that.mSipDetails)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mRegistrationTech),
                Integer.valueOf(this.mTransportType),
                Integer.valueOf(this.mImsAttributeFlags),
                this.mFeatureTags,
                this.mSipDetails);
    }

    public String toString() {
        return "ImsRegistrationAttributes { transportType= "
                + this.mTransportType
                + ", attributeFlags="
                + this.mImsAttributeFlags
                + ", featureTags=["
                + this.mFeatureTags
                + "],SipDetails="
                + this.mSipDetails
                + "}";
    }
}
