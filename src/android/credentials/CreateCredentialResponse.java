package android.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes.dex */
public final class CreateCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<CreateCredentialResponse> CREATOR = new Parcelable.Creator<CreateCredentialResponse>() { // from class: android.credentials.CreateCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialResponse[] newArray(int i) {
            return new CreateCredentialResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialResponse createFromParcel(Parcel parcel) {
            return new CreateCredentialResponse(parcel);
        }
    };
    private final Bundle mData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mData);
    }

    public String toString() {
        return "CreateCredentialResponse {data=" + this.mData + "}";
    }

    public CreateCredentialResponse(Bundle bundle) {
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
    }

    private CreateCredentialResponse(Parcel parcel) {
        Bundle readBundle = parcel.readBundle();
        this.mData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
    }
}
