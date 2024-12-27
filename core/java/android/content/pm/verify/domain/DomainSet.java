package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.util.Objects;
import java.util.Set;

public class DomainSet implements Parcelable {
    public static final Parcelable.Creator<DomainSet> CREATOR =
            new Parcelable.Creator<
                    DomainSet>() { // from class: android.content.pm.verify.domain.DomainSet.1
                @Override // android.os.Parcelable.Creator
                public DomainSet[] newArray(int size) {
                    return new DomainSet[size];
                }

                @Override // android.os.Parcelable.Creator
                public DomainSet createFromParcel(Parcel in) {
                    return new DomainSet(in);
                }
            };
    private final Set<String> mDomains;

    private void parcelDomains(Parcel dest, int flags) {
        DomainVerificationUtils.writeHostSet(dest, this.mDomains);
    }

    private Set<String> unparcelDomains(Parcel in) {
        return DomainVerificationUtils.readHostSet(in);
    }

    public DomainSet(Set<String> domains) {
        this.mDomains = domains;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mDomains);
    }

    public Set<String> getDomains() {
        return this.mDomains;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DomainSet that = (DomainSet) o;
        return Objects.equals(this.mDomains, that.mDomains);
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mDomains);
        return _hash;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        parcelDomains(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected DomainSet(Parcel in) {
        Set<String> domains = unparcelDomains(in);
        this.mDomains = domains;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mDomains);
    }

    @Deprecated
    private void __metadata() {}
}
