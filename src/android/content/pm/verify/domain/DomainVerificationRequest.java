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
/* loaded from: classes.dex */
public final class DomainVerificationRequest implements Parcelable {
    public static final Parcelable.Creator<DomainVerificationRequest> CREATOR;
    static Parcelling<Set<String>> sParcellingForPackageNames;
    private final Set<String> mPackageNames;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void parcelPackageNames(Parcel parcel, int i) {
        DomainVerificationUtils.writeHostSet(parcel, this.mPackageNames);
    }

    private Set<String> unparcelPackageNames(Parcel parcel) {
        return DomainVerificationUtils.readHostSet(parcel);
    }

    public DomainVerificationRequest(Set<String> set) {
        this.mPackageNames = set;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) set);
    }

    public Set<String> getPackageNames() {
        return this.mPackageNames;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mPackageNames, ((DomainVerificationRequest) obj).mPackageNames);
    }

    public int hashCode() {
        return 31 + Objects.hashCode(this.mPackageNames);
    }

    static {
        Parcelling<Set<String>> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForStringSet.class);
        sParcellingForPackageNames = parcelling;
        if (parcelling == null) {
            sParcellingForPackageNames = Parcelling.Cache.put(new Parcelling.BuiltIn.ForStringSet());
        }
        CREATOR = new Parcelable.Creator<DomainVerificationRequest>() { // from class: android.content.pm.verify.domain.DomainVerificationRequest.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DomainVerificationRequest[] newArray(int i) {
                return new DomainVerificationRequest[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DomainVerificationRequest createFromParcel(Parcel parcel) {
                return new DomainVerificationRequest(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcelPackageNames(parcel, i);
    }

    DomainVerificationRequest(Parcel parcel) {
        Set<String> unparcelPackageNames = unparcelPackageNames(parcel);
        this.mPackageNames = unparcelPackageNames;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) unparcelPackageNames);
    }
}
