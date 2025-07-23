package android.content.pm.permission;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
@Deprecated
/* loaded from: classes.dex */
public final class RuntimePermissionPresentationInfo implements Parcelable {
    public static final Parcelable.Creator<RuntimePermissionPresentationInfo> CREATOR = new Parcelable.Creator<RuntimePermissionPresentationInfo>() { // from class: android.content.pm.permission.RuntimePermissionPresentationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimePermissionPresentationInfo createFromParcel(Parcel parcel) {
            return new RuntimePermissionPresentationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RuntimePermissionPresentationInfo[] newArray(int i) {
            return new RuntimePermissionPresentationInfo[i];
        }
    };
    private static final int FLAG_GRANTED = 1;
    private static final int FLAG_STANDARD = 2;
    private final int mFlags;
    private final CharSequence mLabel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RuntimePermissionPresentationInfo(CharSequence charSequence, boolean z, boolean z2) {
        this.mLabel = charSequence;
        this.mFlags = z2 ? (z ? 1 : 0) | 2 : z;
    }

    private RuntimePermissionPresentationInfo(Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mFlags = parcel.readInt();
    }

    public boolean isGranted() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isStandard() {
        return (this.mFlags & 2) != 0;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeInt(this.mFlags);
    }
}
