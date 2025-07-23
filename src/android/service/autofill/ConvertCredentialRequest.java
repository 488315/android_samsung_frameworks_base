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
        public ConvertCredentialRequest[] newArray(int i) {
            return new ConvertCredentialRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConvertCredentialRequest createFromParcel(Parcel parcel) {
            return new ConvertCredentialRequest(parcel);
        }
    };
    private final Bundle mClientState;
    private final GetCredentialResponse mGetCredentialResponse;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ConvertCredentialRequest(GetCredentialResponse getCredentialResponse, Bundle bundle) {
        this.mGetCredentialResponse = getCredentialResponse;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) getCredentialResponse);
        this.mClientState = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mGetCredentialResponse, i);
        parcel.writeBundle(this.mClientState);
    }

    ConvertCredentialRequest(Parcel parcel) {
        GetCredentialResponse getCredentialResponse = (GetCredentialResponse) parcel.readTypedObject(GetCredentialResponse.CREATOR);
        Bundle readBundle = parcel.readBundle();
        this.mGetCredentialResponse = getCredentialResponse;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) getCredentialResponse);
        this.mClientState = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
    }
}
