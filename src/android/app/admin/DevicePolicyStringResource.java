package android.app.admin;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class DevicePolicyStringResource implements Parcelable {
    public static final Parcelable.Creator<DevicePolicyStringResource> CREATOR = new Parcelable.Creator<DevicePolicyStringResource>() { // from class: android.app.admin.DevicePolicyStringResource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePolicyStringResource createFromParcel(Parcel parcel) {
            return new DevicePolicyStringResource(parcel.readString(), parcel.readInt(), (ParcelableResource) parcel.readTypedObject(ParcelableResource.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePolicyStringResource[] newArray(int i) {
            return new DevicePolicyStringResource[i];
        }
    };
    private ParcelableResource mResource;
    private final int mResourceIdInCallingPackage;
    private final String mStringId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DevicePolicyStringResource(Context context, String str, int i) {
        this(str, i, new ParcelableResource(context, i, 2));
    }

    private DevicePolicyStringResource(String str, int i, ParcelableResource parcelableResource) {
        Objects.requireNonNull(str, "stringId must be provided.");
        Objects.requireNonNull(parcelableResource, "ParcelableResource must be provided.");
        this.mStringId = str;
        this.mResourceIdInCallingPackage = i;
        this.mResource = parcelableResource;
    }

    public String getStringId() {
        return this.mStringId;
    }

    public int getResourceIdInCallingPackage() {
        return this.mResourceIdInCallingPackage;
    }

    public ParcelableResource getResource() {
        return this.mResource;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DevicePolicyStringResource devicePolicyStringResource = (DevicePolicyStringResource) obj;
            if (this.mStringId == devicePolicyStringResource.mStringId && this.mResourceIdInCallingPackage == devicePolicyStringResource.mResourceIdInCallingPackage && this.mResource.equals(devicePolicyStringResource.mResource)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mStringId, Integer.valueOf(this.mResourceIdInCallingPackage), this.mResource);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mStringId);
        parcel.writeInt(this.mResourceIdInCallingPackage);
        parcel.writeTypedObject(this.mResource, i);
    }
}
