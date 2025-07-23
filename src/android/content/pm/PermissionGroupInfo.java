package android.content.pm;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class PermissionGroupInfo extends PackageItemInfo implements Parcelable {
    public static final Parcelable.Creator<PermissionGroupInfo> CREATOR = new Parcelable.Creator<PermissionGroupInfo>() { // from class: android.content.pm.PermissionGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionGroupInfo createFromParcel(Parcel parcel) {
            return new PermissionGroupInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionGroupInfo[] newArray(int i) {
            return new PermissionGroupInfo[i];
        }
    };
    public static final int FLAG_PERSONAL_INFO = 1;

    @SystemApi
    public final int backgroundRequestDetailResourceId;

    @SystemApi
    public final int backgroundRequestResourceId;
    public int descriptionRes;
    public int flags;
    public CharSequence nonLocalizedDescription;
    public int priority;

    @SystemApi
    public final int requestDetailResourceId;

    @SystemApi
    public int requestRes;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PermissionGroupInfo(int i, int i2, int i3) {
        this.requestDetailResourceId = i;
        this.backgroundRequestResourceId = i2;
        this.backgroundRequestDetailResourceId = i3;
    }

    @Deprecated
    public PermissionGroupInfo() {
        this(0, 0, 0);
    }

    @Deprecated
    public PermissionGroupInfo(PermissionGroupInfo permissionGroupInfo) {
        super(permissionGroupInfo);
        this.descriptionRes = permissionGroupInfo.descriptionRes;
        this.requestRes = permissionGroupInfo.requestRes;
        this.requestDetailResourceId = permissionGroupInfo.requestDetailResourceId;
        this.backgroundRequestResourceId = permissionGroupInfo.backgroundRequestResourceId;
        this.backgroundRequestDetailResourceId = permissionGroupInfo.backgroundRequestDetailResourceId;
        this.nonLocalizedDescription = permissionGroupInfo.nonLocalizedDescription;
        this.flags = permissionGroupInfo.flags;
        this.priority = permissionGroupInfo.priority;
    }

    public CharSequence loadDescription(PackageManager packageManager) {
        CharSequence text;
        CharSequence charSequence = this.nonLocalizedDescription;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.descriptionRes == 0 || (text = packageManager.getText(this.packageName, this.descriptionRes, null)) == null) {
            return null;
        }
        return text;
    }

    public String toString() {
        return "PermissionGroupInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + " flgs=0x" + Integer.toHexString(this.flags) + "}";
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.requestRes);
        parcel.writeInt(this.requestDetailResourceId);
        parcel.writeInt(this.backgroundRequestResourceId);
        parcel.writeInt(this.backgroundRequestDetailResourceId);
        TextUtils.writeToParcel(this.nonLocalizedDescription, parcel, i);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.priority);
    }

    private PermissionGroupInfo(Parcel parcel) {
        super(parcel);
        this.descriptionRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.requestDetailResourceId = parcel.readInt();
        this.backgroundRequestResourceId = parcel.readInt();
        this.backgroundRequestDetailResourceId = parcel.readInt();
        this.nonLocalizedDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.flags = parcel.readInt();
        this.priority = parcel.readInt();
    }
}
