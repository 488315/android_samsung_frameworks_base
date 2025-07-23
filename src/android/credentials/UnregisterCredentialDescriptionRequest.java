package android.credentials;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class UnregisterCredentialDescriptionRequest implements Parcelable {
    public static final Parcelable.Creator<UnregisterCredentialDescriptionRequest> CREATOR = new Parcelable.Creator<UnregisterCredentialDescriptionRequest>() { // from class: android.credentials.UnregisterCredentialDescriptionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest createFromParcel(Parcel parcel) {
            return new UnregisterCredentialDescriptionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest[] newArray(int i) {
            return new UnregisterCredentialDescriptionRequest[i];
        }
    };
    private final List<CredentialDescription> mCredentialDescriptions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UnregisterCredentialDescriptionRequest(CredentialDescription credentialDescription) {
        this.mCredentialDescriptions = Arrays.asList((CredentialDescription) Objects.requireNonNull(credentialDescription));
    }

    public UnregisterCredentialDescriptionRequest(Set<CredentialDescription> set) {
        this.mCredentialDescriptions = new ArrayList((Collection) Objects.requireNonNull(set));
    }

    private UnregisterCredentialDescriptionRequest(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, CredentialDescription.CREATOR);
        ArrayList arrayList2 = new ArrayList();
        this.mCredentialDescriptions = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        arrayList2.addAll(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mCredentialDescriptions, i);
    }

    public Set<CredentialDescription> getCredentialDescriptions() {
        return new HashSet(this.mCredentialDescriptions);
    }
}
