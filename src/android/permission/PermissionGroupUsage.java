package android.permission;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class PermissionGroupUsage implements Parcelable {
    public static final Parcelable.Creator<PermissionGroupUsage> CREATOR = new Parcelable.Creator<PermissionGroupUsage>() { // from class: android.permission.PermissionGroupUsage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionGroupUsage[] newArray(int i) {
            return new PermissionGroupUsage[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionGroupUsage createFromParcel(Parcel parcel) {
            return new PermissionGroupUsage(parcel);
        }
    };
    private final boolean mActive;
    private final CharSequence mAttributionLabel;
    private final CharSequence mAttributionTag;
    private final long mLastAccessTimeMillis;
    private final String mPackageName;
    private final String mPermissionGroupName;
    private final String mPersistentDeviceId;
    private final boolean mPhoneCall;
    private final CharSequence mProxyLabel;
    private final int mUid;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PermissionGroupUsage(String str, int i, long j, String str2, boolean z, boolean z2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, String str3) {
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mUid = i;
        this.mLastAccessTimeMillis = j;
        this.mPermissionGroupName = str2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str2);
        this.mActive = z;
        this.mPhoneCall = z2;
        this.mAttributionTag = charSequence;
        this.mAttributionLabel = charSequence2;
        this.mProxyLabel = charSequence3;
        this.mPersistentDeviceId = str3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str3);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getLastAccessTimeMillis() {
        return this.mLastAccessTimeMillis;
    }

    public String getPermissionGroupName() {
        return this.mPermissionGroupName;
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean isPhoneCall() {
        return this.mPhoneCall;
    }

    public CharSequence getAttributionTag() {
        return this.mAttributionTag;
    }

    public CharSequence getAttributionLabel() {
        return this.mAttributionLabel;
    }

    public CharSequence getProxyLabel() {
        return this.mProxyLabel;
    }

    public String getPersistentDeviceId() {
        return this.mPersistentDeviceId;
    }

    public String toString() {
        return "PermissionGroupUsage { packageName = " + this.mPackageName + ", uid = " + this.mUid + ", lastAccessTimeMillis = " + this.mLastAccessTimeMillis + ", permissionGroupName = " + this.mPermissionGroupName + ", active = " + this.mActive + ", phoneCall = " + this.mPhoneCall + ", attributionTag = " + ((Object) this.mAttributionTag) + ", attributionLabel = " + ((Object) this.mAttributionLabel) + ", proxyLabel = " + ((Object) this.mProxyLabel) + ", persistentDeviceId = " + this.mPersistentDeviceId + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PermissionGroupUsage permissionGroupUsage = (PermissionGroupUsage) obj;
            if (Objects.equals(this.mPackageName, permissionGroupUsage.mPackageName) && this.mUid == permissionGroupUsage.mUid && this.mLastAccessTimeMillis == permissionGroupUsage.mLastAccessTimeMillis && Objects.equals(this.mPermissionGroupName, permissionGroupUsage.mPermissionGroupName) && this.mActive == permissionGroupUsage.mActive && this.mPhoneCall == permissionGroupUsage.mPhoneCall && Objects.equals(this.mAttributionTag, permissionGroupUsage.mAttributionTag) && Objects.equals(this.mAttributionLabel, permissionGroupUsage.mAttributionLabel) && Objects.equals(this.mProxyLabel, permissionGroupUsage.mProxyLabel) && Objects.equals(this.mPersistentDeviceId, permissionGroupUsage.mPersistentDeviceId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((Objects.hashCode(this.mPackageName) + 31) * 31) + this.mUid) * 31) + Long.hashCode(this.mLastAccessTimeMillis)) * 31) + Objects.hashCode(this.mPermissionGroupName)) * 31) + Boolean.hashCode(this.mActive)) * 31) + Boolean.hashCode(this.mPhoneCall)) * 31) + Objects.hashCode(this.mAttributionTag)) * 31) + Objects.hashCode(this.mAttributionLabel)) * 31) + Objects.hashCode(this.mProxyLabel)) * 31) + Objects.hashCode(this.mPersistentDeviceId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.mActive ? 16 : 0;
        if (this.mPhoneCall) {
            i2 |= 32;
        }
        if (this.mAttributionTag != null) {
            i2 |= 64;
        }
        if (this.mAttributionLabel != null) {
            i2 |= 128;
        }
        if (this.mProxyLabel != null) {
            i2 |= 256;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mLastAccessTimeMillis);
        parcel.writeString(this.mPermissionGroupName);
        CharSequence charSequence = this.mAttributionTag;
        if (charSequence != null) {
            parcel.writeCharSequence(charSequence);
        }
        CharSequence charSequence2 = this.mAttributionLabel;
        if (charSequence2 != null) {
            parcel.writeCharSequence(charSequence2);
        }
        CharSequence charSequence3 = this.mProxyLabel;
        if (charSequence3 != null) {
            parcel.writeCharSequence(charSequence3);
        }
        parcel.writeString(this.mPersistentDeviceId);
    }

    PermissionGroupUsage(Parcel parcel) {
        int i = parcel.readInt();
        boolean z = (i & 16) != 0;
        boolean z2 = (i & 32) != 0;
        String string = parcel.readString();
        int i2 = parcel.readInt();
        long j = parcel.readLong();
        String string2 = parcel.readString();
        CharSequence charSequence = (i & 64) == 0 ? null : parcel.readCharSequence();
        CharSequence charSequence2 = (i & 128) == 0 ? null : parcel.readCharSequence();
        CharSequence charSequence3 = (i & 256) == 0 ? null : parcel.readCharSequence();
        String string3 = parcel.readString();
        this.mPackageName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mUid = i2;
        this.mLastAccessTimeMillis = j;
        this.mPermissionGroupName = string2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string2);
        this.mActive = z;
        this.mPhoneCall = z2;
        this.mAttributionTag = charSequence;
        this.mAttributionLabel = charSequence2;
        this.mProxyLabel = charSequence3;
        this.mPersistentDeviceId = string3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string3);
    }
}
