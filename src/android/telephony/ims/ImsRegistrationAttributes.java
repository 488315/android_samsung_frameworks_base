package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class ImsRegistrationAttributes implements Parcelable {
    public static final int ATTR_EPDG_OVER_CELL_INTERNET = 1;
    public static final int ATTR_REGISTRATION_TYPE_EMERGENCY = 2;
    public static final int ATTR_VIRTUAL_FOR_ANONYMOUS_EMERGENCY_CALL = 4;
    public static final Parcelable.Creator<ImsRegistrationAttributes> CREATOR = new Parcelable.Creator<ImsRegistrationAttributes>() { // from class: android.telephony.ims.ImsRegistrationAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsRegistrationAttributes createFromParcel(Parcel parcel) {
            return new ImsRegistrationAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsRegistrationAttributes[] newArray(int i) {
            return new ImsRegistrationAttributes[i];
        }
    };
    private final ArrayList<String> mFeatureTags;
    private final int mImsAttributeFlags;
    private final int mRegistrationTech;
    private final SipDetails mSipDetails;
    private final int mTransportType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsAttributeFlag {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public static final class Builder {
        private int mAttributeFlags;
        private Set<String> mFeatureTags = Collections.EMPTY_SET;
        private final int mRegistrationTech;
        private SipDetails mSipDetails;

        public Builder(int i) {
            this.mRegistrationTech = i;
            if (i == 2) {
                this.mAttributeFlags |= 1;
            }
        }

        public Builder setFeatureTags(Set<String> set) {
            if (set == null) {
                throw new IllegalArgumentException("feature tag set must not be null");
            }
            this.mFeatureTags = new ArraySet(set);
            return this;
        }

        public Builder setSipDetails(SipDetails sipDetails) {
            this.mSipDetails = sipDetails;
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
            int i = this.mRegistrationTech;
            return new ImsRegistrationAttributes(i, RegistrationManager.getAccessType(i), this.mAttributeFlags, this.mFeatureTags, this.mSipDetails);
        }
    }

    public ImsRegistrationAttributes(int i, int i2, int i3, Set<String> set) {
        this.mRegistrationTech = i;
        this.mTransportType = i2;
        this.mImsAttributeFlags = i3;
        this.mFeatureTags = new ArrayList<>(set);
        this.mSipDetails = null;
    }

    public ImsRegistrationAttributes(int i, int i2, int i3, Set<String> set, SipDetails sipDetails) {
        this.mRegistrationTech = i;
        this.mTransportType = i2;
        this.mImsAttributeFlags = i3;
        this.mFeatureTags = new ArrayList<>(set);
        this.mSipDetails = sipDetails;
    }

    public ImsRegistrationAttributes(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mRegistrationTech = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mImsAttributeFlags = parcel.readInt();
        ArrayList<String> arrayList = new ArrayList<>();
        this.mFeatureTags = arrayList;
        parcel.readList(arrayList, null, String.class);
        this.mSipDetails = (SipDetails) parcel.readParcelable(null, SipDetails.class);
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
            return Collections.EMPTY_SET;
        }
        return new ArraySet(this.mFeatureTags);
    }

    public SipDetails getSipDetails() {
        return this.mSipDetails;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRegistrationTech);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mImsAttributeFlags);
        parcel.writeList(this.mFeatureTags);
        parcel.writeParcelable(this.mSipDetails, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ImsRegistrationAttributes imsRegistrationAttributes = (ImsRegistrationAttributes) obj;
            if (this.mRegistrationTech == imsRegistrationAttributes.mRegistrationTech && this.mTransportType == imsRegistrationAttributes.mTransportType && this.mImsAttributeFlags == imsRegistrationAttributes.mImsAttributeFlags && Objects.equals(this.mFeatureTags, imsRegistrationAttributes.mFeatureTags) && Objects.equals(this.mSipDetails, imsRegistrationAttributes.mSipDetails)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRegistrationTech), Integer.valueOf(this.mTransportType), Integer.valueOf(this.mImsAttributeFlags), this.mFeatureTags, this.mSipDetails);
    }

    public String toString() {
        return "ImsRegistrationAttributes { transportType= " + this.mTransportType + ", attributeFlags=" + this.mImsAttributeFlags + ", featureTags=[" + this.mFeatureTags + "],SipDetails=" + this.mSipDetails + "}";
    }
}
