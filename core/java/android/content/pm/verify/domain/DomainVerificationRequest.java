package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;

import java.util.Objects;
import java.util.Set;

@SystemApi
public final class DomainVerificationRequest implements Parcelable {
    public static final Parcelable.Creator<DomainVerificationRequest> CREATOR;
    static Parcelling<Set<String>> sParcellingForPackageNames;
    private final Set<String> mPackageNames;

    private void parcelPackageNames(Parcel dest, int flags) {
        DomainVerificationUtils.writeHostSet(dest, this.mPackageNames);
    }

    private Set<String> unparcelPackageNames(Parcel in) {
        return DomainVerificationUtils.readHostSet(in);
    }

    public DomainVerificationRequest(Set<String> packageNames) {
        this.mPackageNames = packageNames;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageNames);
    }

    public Set<String> getPackageNames() {
        return this.mPackageNames;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DomainVerificationRequest that = (DomainVerificationRequest) o;
        return Objects.equals(this.mPackageNames, that.mPackageNames);
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mPackageNames);
        return _hash;
    }

    static {
        sParcellingForPackageNames = Parcelling.Cache.get(Parcelling.BuiltIn.ForStringSet.class);
        if (sParcellingForPackageNames == null) {
            sParcellingForPackageNames =
                    Parcelling.Cache.put(new Parcelling.BuiltIn.ForStringSet());
        }
        CREATOR =
                new Parcelable.Creator<DomainVerificationRequest>() { // from class:
                    // android.content.pm.verify.domain.DomainVerificationRequest.1
                    @Override // android.os.Parcelable.Creator
                    public DomainVerificationRequest[] newArray(int size) {
                        return new DomainVerificationRequest[size];
                    }

                    @Override // android.os.Parcelable.Creator
                    public DomainVerificationRequest createFromParcel(Parcel in) {
                        return new DomainVerificationRequest(in);
                    }
                };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        parcelPackageNames(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DomainVerificationRequest(Parcel in) {
        Set<String> packageNames = unparcelPackageNames(in);
        this.mPackageNames = packageNames;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageNames);
    }

    @Deprecated
    private void __metadata() {}
}
