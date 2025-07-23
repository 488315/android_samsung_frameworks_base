package android.service.credentials;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class CreateCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<CreateCredentialRequest> CREATOR = new Parcelable.Creator<CreateCredentialRequest>() { // from class: android.service.credentials.CreateCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialRequest createFromParcel(Parcel parcel) {
            return new CreateCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateCredentialRequest[] newArray(int i) {
            return new CreateCredentialRequest[i];
        }
    };
    private final CallingAppInfo mCallingAppInfo;
    private final Bundle mData;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CreateCredentialRequest(CallingAppInfo callingAppInfo, String str, Bundle bundle) {
        this.mCallingAppInfo = (CallingAppInfo) Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be null or empty");
        this.mData = (Bundle) Objects.requireNonNull(bundle, "data must not be null");
    }

    private CreateCredentialRequest(Parcel parcel) {
        this.mCallingAppInfo = (CallingAppInfo) parcel.readTypedObject(CallingAppInfo.CREATOR);
        this.mType = parcel.readString8();
        this.mData = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeString8(this.mType);
        parcel.writeTypedObject(this.mData, i);
    }

    public CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public String getType() {
        return this.mType;
    }

    public Bundle getData() {
        return this.mData;
    }
}
