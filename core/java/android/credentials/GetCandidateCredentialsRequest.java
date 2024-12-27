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

public final class GetCandidateCredentialsRequest implements Parcelable {
    public static final Parcelable.Creator<GetCandidateCredentialsRequest> CREATOR =
            new Parcelable.Creator<GetCandidateCredentialsRequest>() { // from class:
                // android.credentials.GetCandidateCredentialsRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GetCandidateCredentialsRequest[] newArray(int size) {
                    return new GetCandidateCredentialsRequest[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GetCandidateCredentialsRequest createFromParcel(Parcel in) {
                    return new GetCandidateCredentialsRequest(in);
                }
            };
    private final List<CredentialOption> mCredentialOptions;
    private final Bundle mData;
    private String mOrigin;

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mCredentialOptions, flags);
        dest.writeBundle(this.mData);
        dest.writeString8(this.mOrigin);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "GetCandidateCredentialsRequest {credentialOption="
                + this.mCredentialOptions
                + ", data="
                + this.mData
                + ", origin="
                + this.mOrigin
                + "}";
    }

    private GetCandidateCredentialsRequest(
            List<CredentialOption> credentialOptions, Bundle data, String origin) {
        Preconditions.checkCollectionNotEmpty(credentialOptions, "credentialOptions");
        Preconditions.checkCollectionElementsNotNull(credentialOptions, "credentialOptions");
        this.mCredentialOptions = credentialOptions;
        this.mData = (Bundle) Objects.requireNonNull(data, "data must not be null");
        this.mOrigin = origin;
    }

    private GetCandidateCredentialsRequest(Parcel in) {
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCredentialOptions);
        Bundle data = in.readBundle();
        this.mData = data;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mData);
        this.mOrigin = in.readString8();
    }
}
