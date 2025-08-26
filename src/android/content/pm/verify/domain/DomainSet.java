package android.content.pm.verify.domain;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public class DomainSet implements Parcelable {
    public static final Parcelable.Creator<DomainSet> CREATOR = new Parcelable.Creator<DomainSet>() { // from class: android.content.pm.verify.domain.DomainSet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainSet[] newArray(int i) {
            return new DomainSet[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainSet createFromParcel(Parcel parcel) {
            return new DomainSet(parcel);
        }
    };
    private final Set<String> mDomains;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void parcelDomains(Parcel parcel, int i) {
        DomainVerificationUtils.writeHostSet(parcel, this.mDomains);
    }

    private Set<String> unparcelDomains(Parcel parcel) {
        return DomainVerificationUtils.readHostSet(parcel);
    }

    public DomainSet(Set<String> set) {
        this.mDomains = set;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) set);
    }

    public Set<String> getDomains() {
        return this.mDomains;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mDomains, ((DomainSet) obj).mDomains);
    }

    public int hashCode() {
        return 31 + Objects.hashCode(this.mDomains);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcelDomains(parcel, i);
    }

    protected DomainSet(Parcel parcel) {
        Set<String> setUnparcelDomains = unparcelDomains(parcel);
        this.mDomains = setUnparcelDomains;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) setUnparcelDomains);
    }
}
