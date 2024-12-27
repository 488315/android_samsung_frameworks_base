package android.credentials;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.credentials.CredentialProviderService;
import android.view.autofill.AutofillId;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes.dex */
public final class GetCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<GetCredentialResponse> CREATOR = new Parcelable.Creator<GetCredentialResponse>() { // from class: android.credentials.GetCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialResponse[] newArray(int size) {
            return new GetCredentialResponse[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCredentialResponse createFromParcel(Parcel in) {
            return new GetCredentialResponse(in);
        }
    };
    private final Credential mCredential;

    public Credential getCredential() {
        return this.mCredential;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mCredential, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "GetCredentialResponse {credential=" + this.mCredential + "}";
    }

    public AutofillId getAutofillId() {
        return (AutofillId) this.mCredential.getData().getParcelable(CredentialProviderService.EXTRA_AUTOFILL_ID, AutofillId.class);
    }

    public GetCredentialResponse(Credential credential) {
        this.mCredential = (Credential) Objects.requireNonNull(credential, "credential must not be null");
    }

    private GetCredentialResponse(Parcel in) {
        Credential credential = (Credential) in.readTypedObject(Credential.CREATOR);
        this.mCredential = credential;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCredential);
    }
}
