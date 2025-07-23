package android.companion;

import android.annotation.NonNull;
import android.annotation.UserIdInt;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AssociationRequest implements Parcelable {
    public static final Parcelable.Creator<AssociationRequest> CREATOR = new Parcelable.Creator<AssociationRequest>() { // from class: android.companion.AssociationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssociationRequest[] newArray(int i) {
            return new AssociationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssociationRequest createFromParcel(Parcel parcel) {
            return new AssociationRequest(parcel);
        }
    };
    public static final String DEVICE_PROFILE_APP_STREAMING = "android.app.role.COMPANION_DEVICE_APP_STREAMING";
    public static final String DEVICE_PROFILE_AUTOMOTIVE_PROJECTION = "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION";
    public static final String DEVICE_PROFILE_COMPUTER = "android.app.role.COMPANION_DEVICE_COMPUTER";
    public static final String DEVICE_PROFILE_GLASSES = "android.app.role.COMPANION_DEVICE_GLASSES";
    public static final String DEVICE_PROFILE_NEARBY_DEVICE_STREAMING = "android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING";
    public static final String DEVICE_PROFILE_VIRTUAL_DEVICE = "android.app.role.COMPANION_DEVICE_VIRTUAL_DEVICE";
    public static final String DEVICE_PROFILE_WATCH = "android.app.role.COMPANION_DEVICE_WATCH";
    public static final String DEVICE_PROFILE_WEARABLE_SENSING = "android.companion.COMPANION_DEVICE_WEARABLE_SENSING";
    private AssociatedDevice mAssociatedDevice;
    private final long mCreationTime;
    private final List<DeviceFilter<?>> mDeviceFilters;
    private Icon mDeviceIcon;
    private final String mDeviceProfile;
    private String mDeviceProfilePrivilegesDescription;
    private CharSequence mDisplayName;
    private final boolean mForceConfirmation;
    private String mPackageName;
    private final boolean mSelfManaged;
    private final boolean mSingleDevice;
    private boolean mSkipPrompt;
    private final boolean mSkipRoleGrant;
    private int mUserId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceProfile {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AssociationRequest(boolean z, List<DeviceFilter<?>> list, String str, CharSequence charSequence, boolean z2, boolean z3, boolean z4, Icon icon) {
        this.mSingleDevice = z;
        this.mDeviceFilters = (List) Objects.requireNonNull(list);
        this.mDeviceProfile = str;
        this.mDisplayName = charSequence;
        this.mSelfManaged = z2;
        this.mForceConfirmation = z3;
        this.mSkipRoleGrant = z4;
        this.mCreationTime = System.currentTimeMillis();
        this.mDeviceIcon = icon;
    }

    public String getDeviceProfile() {
        return this.mDeviceProfile;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public boolean isSelfManaged() {
        return this.mSelfManaged;
    }

    public boolean isForceConfirmation() {
        return this.mForceConfirmation;
    }

    public boolean isSkipRoleGrant() {
        return this.mSkipRoleGrant;
    }

    public boolean isSingleDevice() {
        return this.mSingleDevice;
    }

    public Icon getDeviceIcon() {
        return this.mDeviceIcon;
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public void setDeviceProfilePrivilegesDescription(String str) {
        this.mDeviceProfilePrivilegesDescription = str;
    }

    public void setSkipPrompt(boolean z) {
        this.mSkipPrompt = z;
    }

    public void setDisplayName(CharSequence charSequence) {
        this.mDisplayName = charSequence;
    }

    public void setAssociatedDevice(AssociatedDevice associatedDevice) {
        this.mAssociatedDevice = associatedDevice;
    }

    public void setDeviceIcon(Icon icon) {
        this.mDeviceIcon = icon;
    }

    public List<DeviceFilter<?>> getDeviceFilters() {
        return this.mDeviceFilters;
    }

    public static final class Builder extends OneTimeUseBuilder<AssociationRequest> {
        private String mDeviceProfile;
        private CharSequence mDisplayName;
        private boolean mSingleDevice = false;
        private ArrayList<DeviceFilter<?>> mDeviceFilters = null;
        private boolean mSelfManaged = false;
        private boolean mForceConfirmation = false;
        private boolean mSkipRoleGrant = false;
        private Icon mDeviceIcon = null;

        public Builder setSingleDevice(boolean z) {
            checkNotUsed();
            this.mSingleDevice = z;
            return this;
        }

        public Builder addDeviceFilter(DeviceFilter<?> deviceFilter) {
            checkNotUsed();
            if (deviceFilter != null) {
                this.mDeviceFilters = ArrayUtils.add(this.mDeviceFilters, deviceFilter);
            }
            return this;
        }

        public Builder setDeviceProfile(String str) {
            checkNotUsed();
            this.mDeviceProfile = str;
            return this;
        }

        public Builder setDisplayName(CharSequence charSequence) {
            checkNotUsed();
            this.mDisplayName = (CharSequence) Objects.requireNonNull(charSequence);
            return this;
        }

        public Builder setSelfManaged(boolean z) {
            checkNotUsed();
            this.mSelfManaged = z;
            return this;
        }

        public Builder setForceConfirmation(boolean z) {
            checkNotUsed();
            this.mForceConfirmation = z;
            return this;
        }

        public Builder setSkipRoleGrant(boolean z) {
            checkNotUsed();
            this.mSkipRoleGrant = z;
            return this;
        }

        public Builder setDeviceIcon(Icon icon) {
            checkNotUsed();
            this.mDeviceIcon = (Icon) Objects.requireNonNull(icon);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public AssociationRequest build() {
            markUsed();
            if (this.mSelfManaged && this.mDisplayName == null) {
                throw new IllegalStateException("Request for a self-managed association MUST provide the display name of the device");
            }
            return new AssociationRequest(this.mSingleDevice, CollectionUtils.emptyIfNull(this.mDeviceFilters), this.mDeviceProfile, this.mDisplayName, this.mSelfManaged, this.mForceConfirmation, this.mSkipRoleGrant, this.mDeviceIcon);
        }
    }

    public AssociatedDevice getAssociatedDevice() {
        return this.mAssociatedDevice;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public String getDeviceProfilePrivilegesDescription() {
        return this.mDeviceProfilePrivilegesDescription;
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }

    public boolean isSkipPrompt() {
        return this.mSkipPrompt;
    }

    public String toString() {
        return "AssociationRequest { singleDevice = " + this.mSingleDevice + ", deviceFilters = " + this.mDeviceFilters + ", deviceProfile = " + this.mDeviceProfile + ", displayName = " + ((Object) this.mDisplayName) + ", associatedDevice = " + this.mAssociatedDevice + ", selfManaged = " + this.mSelfManaged + ", forceConfirmation = " + this.mForceConfirmation + ", skipRoleGrant = " + this.mSkipRoleGrant + ", packageName = " + this.mPackageName + ", userId = " + this.mUserId + ", deviceProfilePrivilegesDescription = " + this.mDeviceProfilePrivilegesDescription + ", creationTime = " + this.mCreationTime + ", skipPrompt = " + this.mSkipPrompt + " }";
    }

    public boolean equals(Object obj) {
        Icon icon;
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AssociationRequest associationRequest = (AssociationRequest) obj;
            if (this.mSingleDevice == associationRequest.mSingleDevice && Objects.equals(this.mDeviceFilters, associationRequest.mDeviceFilters) && Objects.equals(this.mDeviceProfile, associationRequest.mDeviceProfile) && Objects.equals(this.mDisplayName, associationRequest.mDisplayName) && Objects.equals(this.mAssociatedDevice, associationRequest.mAssociatedDevice) && this.mSelfManaged == associationRequest.mSelfManaged && this.mForceConfirmation == associationRequest.mForceConfirmation && this.mSkipRoleGrant == associationRequest.mSkipRoleGrant && Objects.equals(this.mPackageName, associationRequest.mPackageName) && this.mUserId == associationRequest.mUserId && Objects.equals(this.mDeviceProfilePrivilegesDescription, associationRequest.mDeviceProfilePrivilegesDescription) && this.mCreationTime == associationRequest.mCreationTime && this.mSkipPrompt == associationRequest.mSkipPrompt && ((icon = this.mDeviceIcon) != null ? icon.sameAs(associationRequest.mDeviceIcon) : associationRequest.mDeviceIcon == null)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((Boolean.hashCode(this.mSingleDevice) + 31) * 31) + Objects.hashCode(this.mDeviceFilters)) * 31) + Objects.hashCode(this.mDeviceProfile)) * 31) + Objects.hashCode(this.mDisplayName)) * 31) + Objects.hashCode(this.mAssociatedDevice)) * 31) + Boolean.hashCode(this.mSelfManaged)) * 31) + Boolean.hashCode(this.mForceConfirmation)) * 31) + Boolean.hashCode(this.mSkipRoleGrant)) * 31) + Objects.hashCode(this.mPackageName)) * 31) + this.mUserId) * 31) + Objects.hashCode(this.mDeviceProfilePrivilegesDescription)) * 31) + Long.hashCode(this.mCreationTime)) * 31) + Boolean.hashCode(this.mSkipPrompt)) * 31) + Objects.hashCode(this.mDeviceIcon);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        boolean z = this.mSingleDevice;
        int i2 = z;
        if (this.mSelfManaged) {
            i2 = (z ? 1 : 0) | 2;
        }
        int i3 = i2;
        if (this.mForceConfirmation) {
            i3 = (i2 == true ? 1 : 0) | 4;
        }
        int i4 = i3;
        if (this.mSkipPrompt) {
            i4 = (i3 == true ? 1 : 0) | 8;
        }
        int i5 = i4;
        if (this.mDeviceProfile != null) {
            i5 = (i4 == true ? 1 : 0) | 16;
        }
        int i6 = i5;
        if (this.mDisplayName != null) {
            i6 = (i5 == true ? 1 : 0) | 32;
        }
        int i7 = i6;
        if (this.mAssociatedDevice != null) {
            i7 = (i6 == true ? 1 : 0) | 64;
        }
        int i8 = i7;
        if (this.mPackageName != null) {
            i8 = (i7 == true ? 1 : 0) | 128;
        }
        int i9 = i8;
        if (this.mDeviceProfilePrivilegesDescription != null) {
            i9 = (i8 == true ? 1 : 0) | 256;
        }
        int i10 = i9;
        if (this.mSkipRoleGrant) {
            i10 = (i9 == true ? 1 : 0) | 512;
        }
        parcel.writeInt(i10);
        parcel.writeParcelableList(this.mDeviceFilters, i);
        String str = this.mDeviceProfile;
        if (str != null) {
            parcel.writeString(str);
        }
        CharSequence charSequence = this.mDisplayName;
        if (charSequence != null) {
            parcel.writeCharSequence(charSequence);
        }
        AssociatedDevice associatedDevice = this.mAssociatedDevice;
        if (associatedDevice != null) {
            parcel.writeTypedObject(associatedDevice, i);
        }
        String str2 = this.mPackageName;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeInt(this.mUserId);
        String str3 = this.mDeviceProfilePrivilegesDescription;
        if (str3 != null) {
            parcel.writeString8(str3);
        }
        parcel.writeLong(this.mCreationTime);
        if (this.mDeviceIcon != null) {
            parcel.writeInt(1);
            this.mDeviceIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }

    AssociationRequest(Parcel parcel) {
        int readInt = parcel.readInt();
        boolean z = (readInt & 1) != 0;
        boolean z2 = (readInt & 2) != 0;
        boolean z3 = (readInt & 4) != 0;
        boolean z4 = (readInt & 8) != 0;
        boolean z5 = (readInt & 512) != 0;
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, DeviceFilter.class.getClassLoader(), DeviceFilter.class);
        String readString = (readInt & 16) == 0 ? null : parcel.readString();
        CharSequence readCharSequence = (readInt & 32) == 0 ? null : parcel.readCharSequence();
        AssociatedDevice associatedDevice = (readInt & 64) == 0 ? null : (AssociatedDevice) parcel.readTypedObject(AssociatedDevice.CREATOR);
        String readString2 = (readInt & 128) == 0 ? null : parcel.readString();
        int readInt2 = parcel.readInt();
        String readString8 = (readInt & 256) == 0 ? null : parcel.readString8();
        long readLong = parcel.readLong();
        this.mSingleDevice = z;
        this.mDeviceFilters = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mDeviceProfile = readString;
        this.mDisplayName = readCharSequence;
        this.mAssociatedDevice = associatedDevice;
        this.mSelfManaged = z2;
        this.mForceConfirmation = z3;
        this.mSkipRoleGrant = z5;
        this.mPackageName = readString2;
        this.mUserId = readInt2;
        AnnotationValidations.validate((Class<UserIdInt>) UserIdInt.class, (UserIdInt) null, readInt2);
        this.mDeviceProfilePrivilegesDescription = readString8;
        this.mCreationTime = readLong;
        this.mSkipPrompt = z4;
        if (parcel.readInt() == 1) {
            this.mDeviceIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mDeviceIcon = null;
        }
    }
}
