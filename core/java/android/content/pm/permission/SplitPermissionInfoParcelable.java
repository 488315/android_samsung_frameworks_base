package android.content.pm.permission;

import android.annotation.IntRange;
import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SplitPermissionInfoParcelable implements Parcelable {
    public static final Parcelable.Creator<SplitPermissionInfoParcelable> CREATOR =
            new Parcelable.Creator<SplitPermissionInfoParcelable>() { // from class:
                // android.content.pm.permission.SplitPermissionInfoParcelable.1
                @Override // android.os.Parcelable.Creator
                public SplitPermissionInfoParcelable[] newArray(int size) {
                    return new SplitPermissionInfoParcelable[size];
                }

                @Override // android.os.Parcelable.Creator
                public SplitPermissionInfoParcelable createFromParcel(Parcel in) {
                    return new SplitPermissionInfoParcelable(in);
                }
            };
    private final List<String> mNewPermissions;
    private final String mSplitPermission;
    private final int mTargetSdk;

    private void onConstructed() {
        Preconditions.checkCollectionElementsNotNull(this.mNewPermissions, "newPermissions");
    }

    public SplitPermissionInfoParcelable(
            String splitPermission, List<String> newPermissions, int targetSdk) {
        this.mSplitPermission = splitPermission;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSplitPermission);
        this.mNewPermissions = newPermissions;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mNewPermissions);
        this.mTargetSdk = targetSdk;
        AnnotationValidations.validate(
                (Class<IntRange>) IntRange.class, (IntRange) null, this.mTargetSdk, "from", 0L);
        onConstructed();
    }

    public String getSplitPermission() {
        return this.mSplitPermission;
    }

    public List<String> getNewPermissions() {
        return this.mNewPermissions;
    }

    public int getTargetSdk() {
        return this.mTargetSdk;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SplitPermissionInfoParcelable that = (SplitPermissionInfoParcelable) o;
        if (Objects.equals(this.mSplitPermission, that.mSplitPermission)
                && Objects.equals(this.mNewPermissions, that.mNewPermissions)
                && this.mTargetSdk == that.mTargetSdk) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mSplitPermission);
        return (((_hash * 31) + Objects.hashCode(this.mNewPermissions)) * 31) + this.mTargetSdk;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSplitPermission);
        dest.writeStringList(this.mNewPermissions);
        dest.writeInt(this.mTargetSdk);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected SplitPermissionInfoParcelable(Parcel in) {
        String splitPermission = in.readString();
        List<String> newPermissions = new ArrayList<>();
        in.readStringList(newPermissions);
        int targetSdk = in.readInt();
        this.mSplitPermission = splitPermission;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSplitPermission);
        this.mNewPermissions = newPermissions;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mNewPermissions);
        this.mTargetSdk = targetSdk;
        AnnotationValidations.validate(
                (Class<IntRange>) IntRange.class, (IntRange) null, this.mTargetSdk, "from", 0L);
        onConstructed();
    }

    @Deprecated
    private void __metadata() {}
}
