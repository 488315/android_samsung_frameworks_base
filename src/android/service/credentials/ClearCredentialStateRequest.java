package android.service.credentials;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ClearCredentialStateRequest implements Parcelable {
    public static final Parcelable.Creator<ClearCredentialStateRequest> CREATOR = new Parcelable.Creator<ClearCredentialStateRequest>() { // from class: android.service.credentials.ClearCredentialStateRequest.1
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
    private final CallingAppInfo mCallingAppInfo;
    private final Bundle mData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle getData() {
        return this.mData;
    }

    public CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeBundle(this.mData);
    }

    public String toString() {
        return "ClearCredentialStateRequest {callingAppInfo=" + this.mCallingAppInfo.toString() + " }, {data= " + this.mData + "}";
    }

    public ClearCredentialStateRequest(CallingAppInfo callingAppInfo, Bundle bundle) {
        this.mCallingAppInfo = (CallingAppInfo) Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
    }

    private ClearCredentialStateRequest(Parcel parcel) {
        this.mCallingAppInfo = (CallingAppInfo) parcel.readTypedObject(CallingAppInfo.CREATOR);
        Bundle readBundle = parcel.readBundle();
        this.mData = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
    }
}
