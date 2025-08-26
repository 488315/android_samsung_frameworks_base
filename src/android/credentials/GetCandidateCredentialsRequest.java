package android.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class GetCandidateCredentialsRequest implements Parcelable {
    public static final Parcelable.Creator<GetCandidateCredentialsRequest> CREATOR = new Parcelable.Creator<GetCandidateCredentialsRequest>() { // from class: android.credentials.GetCandidateCredentialsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCandidateCredentialsRequest[] newArray(int i) {
            return new GetCandidateCredentialsRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCandidateCredentialsRequest createFromParcel(Parcel parcel) {
            return new GetCandidateCredentialsRequest(parcel);
        }
    };
    private final List<CredentialOption> mCredentialOptions;
    private final Bundle mData;
    private String mOrigin;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<CredentialOption> getCredentialOptions() {
        return this.mCredentialOptions;
    }

    public Bundle getData() {
        return this.mData;
    }

    public String getOrigin() {
        return this.mOrigin;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mCredentialOptions, i);
        parcel.writeBundle(this.mData);
        parcel.writeString8(this.mOrigin);
    }

    public String toString() {
        return "GetCandidateCredentialsRequest {credentialOption=" + this.mCredentialOptions + ", data=" + this.mData + ", origin=" + this.mOrigin + "}";
    }

    private GetCandidateCredentialsRequest(List<CredentialOption> list, Bundle bundle, String str) {
        Preconditions.checkCollectionNotEmpty(list, "credentialOptions");
        Preconditions.checkCollectionElementsNotNull(list, "credentialOptions");
        this.mCredentialOptions = list;
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
        this.mOrigin = str;
    }

    private GetCandidateCredentialsRequest(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        Bundle bundle = parcel.readBundle();
        this.mData = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
        this.mOrigin = parcel.readString8();
    }
}
