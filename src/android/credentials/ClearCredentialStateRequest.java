package android.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ClearCredentialStateRequest implements Parcelable {
    public static final Parcelable.Creator<ClearCredentialStateRequest> CREATOR = new Parcelable.Creator<ClearCredentialStateRequest>() { // from class: android.credentials.ClearCredentialStateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClearCredentialStateRequest[] newArray(int i) {
            return new ClearCredentialStateRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClearCredentialStateRequest createFromParcel(Parcel parcel) {
            return new ClearCredentialStateRequest(parcel);
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
        return "ClearCredentialStateRequest {data=" + this.mData + "}";
    }

    public ClearCredentialStateRequest(Bundle bundle) {
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
    }

    private ClearCredentialStateRequest(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.mData = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
    }
}
