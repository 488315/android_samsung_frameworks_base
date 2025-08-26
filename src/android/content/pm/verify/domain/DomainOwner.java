package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class DomainOwner implements Parcelable {
    public static final Parcelable.Creator<DomainOwner> CREATOR = new Parcelable.Creator<DomainOwner>() { // from class: android.content.pm.verify.domain.DomainOwner.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainOwner[] newArray(int i) {
            return new DomainOwner[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainOwner createFromParcel(Parcel parcel) {
            return new DomainOwner(parcel);
        }
    };
    private final boolean mOverrideable;
    private final String mPackageName;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DomainOwner(String str, boolean z) {
        this.mPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mOverrideable = z;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public boolean isOverrideable() {
        return this.mOverrideable;
    }

    public String toString() {
        return "DomainOwner { packageName = " + this.mPackageName + ", overrideable = " + this.mOverrideable + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            DomainOwner domainOwner = (DomainOwner) obj;
            if (Objects.equals(this.mPackageName, domainOwner.mPackageName) && this.mOverrideable == domainOwner.mOverrideable) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mPackageName) + 31) * 31) + Boolean.hashCode(this.mOverrideable);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mOverrideable ? (byte) 2 : (byte) 0);
        parcel.writeString(this.mPackageName);
    }

    DomainOwner(Parcel parcel) {
        boolean z = (parcel.readByte() & 2) != 0;
        String string = parcel.readString();
        this.mPackageName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mOverrideable = z;
    }
}
