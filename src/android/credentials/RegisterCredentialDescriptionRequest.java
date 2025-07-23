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
public final class RegisterCredentialDescriptionRequest implements Parcelable {
    public static final Parcelable.Creator<RegisterCredentialDescriptionRequest> CREATOR = new Parcelable.Creator<RegisterCredentialDescriptionRequest>() { // from class: android.credentials.RegisterCredentialDescriptionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegisterCredentialDescriptionRequest createFromParcel(Parcel parcel) {
            return new RegisterCredentialDescriptionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegisterCredentialDescriptionRequest[] newArray(int i) {
            return new RegisterCredentialDescriptionRequest[i];
        }
    };
    private final List<CredentialDescription> mCredentialDescriptions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RegisterCredentialDescriptionRequest(CredentialDescription credentialDescription) {
        this.mCredentialDescriptions = Arrays.asList((CredentialDescription) Objects.requireNonNull(credentialDescription));
    }

    public RegisterCredentialDescriptionRequest(Set<CredentialDescription> set) {
        this.mCredentialDescriptions = new ArrayList((Collection) Objects.requireNonNull(set));
    }

    private RegisterCredentialDescriptionRequest(Parcel parcel) {
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
