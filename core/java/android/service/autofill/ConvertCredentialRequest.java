package android.service.autofill;

import android.annotation.NonNull;
import android.credentials.GetCredentialResponse;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes3.dex */
public final class ConvertCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<ConvertCredentialRequest> CREATOR = new Parcelable.Creator<ConvertCredentialRequest>() { // from class: android.service.autofill.ConvertCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConvertCredentialRequest[] newArray(int size) {
            return new ConvertCredentialRequest[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConvertCredentialRequest createFromParcel(Parcel in) {
            return new ConvertCredentialRequest(in);
        }
    };
    private final Bundle mClientState;
    private final GetCredentialResponse mGetCredentialResponse;

    public ConvertCredentialRequest(GetCredentialResponse getCredentialResponse, Bundle clientState) {
        this.mGetCredentialResponse = getCredentialResponse;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mGetCredentialResponse);
        this.mClientState = clientState;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mClientState);
    }

    public GetCredentialResponse getGetCredentialResponse() {
        return this.mGetCredentialResponse;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public String toString() {
        return "ConvertCredentialRequest { getCredentialResponse = " + this.mGetCredentialResponse + ", clientState = " + this.mClientState + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mGetCredentialResponse, flags);
        dest.writeBundle(this.mClientState);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ConvertCredentialRequest(Parcel in) {
        GetCredentialResponse getCredentialResponse = (GetCredentialResponse) in.readTypedObject(GetCredentialResponse.CREATOR);
        Bundle clientState = in.readBundle();
        this.mGetCredentialResponse = getCredentialResponse;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mGetCredentialResponse);
        this.mClientState = clientState;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mClientState);
    }

    @Deprecated
    private void __metadata() {
    }
}
