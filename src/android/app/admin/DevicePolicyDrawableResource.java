package android.app.admin;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class DevicePolicyDrawableResource implements Parcelable {
    public static final Parcelable.Creator<DevicePolicyDrawableResource> CREATOR = new Parcelable.Creator<DevicePolicyDrawableResource>() { // from class: android.app.admin.DevicePolicyDrawableResource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePolicyDrawableResource createFromParcel(Parcel parcel) {
            return new DevicePolicyDrawableResource(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), (ParcelableResource) parcel.readTypedObject(ParcelableResource.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePolicyDrawableResource[] newArray(int i) {
            return new DevicePolicyDrawableResource[i];
        }
    };
    private final String mDrawableId;
    private final String mDrawableSource;
    private final String mDrawableStyle;
    private ParcelableResource mResource;
    private final int mResourceIdInCallingPackage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DevicePolicyDrawableResource(Context context, String str, String str2, String str3, int i) {
        this(str, str2, str3, i, new ParcelableResource(context, i, 1));
    }

    private DevicePolicyDrawableResource(String str, String str2, String str3, int i, ParcelableResource parcelableResource) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(str3);
        Objects.requireNonNull(parcelableResource);
        this.mDrawableId = str;
        this.mDrawableStyle = str2;
        this.mDrawableSource = str3;
        this.mResourceIdInCallingPackage = i;
        this.mResource = parcelableResource;
    }

    public DevicePolicyDrawableResource(Context context, String str, String str2, int i) {
        this(context, str, str2, DevicePolicyResources.UNDEFINED, i);
    }

    public String getDrawableId() {
        return this.mDrawableId;
    }

    public String getDrawableStyle() {
        return this.mDrawableStyle;
    }

    public String getDrawableSource() {
        return this.mDrawableSource;
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
            DevicePolicyDrawableResource devicePolicyDrawableResource = (DevicePolicyDrawableResource) obj;
            if (this.mDrawableId.equals(devicePolicyDrawableResource.mDrawableId) && this.mDrawableStyle.equals(devicePolicyDrawableResource.mDrawableStyle) && this.mDrawableSource.equals(devicePolicyDrawableResource.mDrawableSource) && this.mResourceIdInCallingPackage == devicePolicyDrawableResource.mResourceIdInCallingPackage && this.mResource.equals(devicePolicyDrawableResource.mResource)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mDrawableId, this.mDrawableStyle, this.mDrawableSource, Integer.valueOf(this.mResourceIdInCallingPackage), this.mResource);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDrawableId);
        parcel.writeString(this.mDrawableStyle);
        parcel.writeString(this.mDrawableSource);
        parcel.writeInt(this.mResourceIdInCallingPackage);
        parcel.writeTypedObject(this.mResource, i);
    }
}
