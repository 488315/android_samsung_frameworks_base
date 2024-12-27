package android.service.credentials;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

import java.util.Objects;

public final class CreateCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<CreateCredentialRequest> CREATOR =
            new Parcelable.Creator<
                    CreateCredentialRequest>() { // from class:
                                                 // android.service.credentials.CreateCredentialRequest.1
                @Override // android.os.Parcelable.Creator
                public CreateCredentialRequest createFromParcel(Parcel in) {
                    return new CreateCredentialRequest(in);
                }

                @Override // android.os.Parcelable.Creator
                public CreateCredentialRequest[] newArray(int size) {
                    return new CreateCredentialRequest[size];
                }
            };
    private final CallingAppInfo mCallingAppInfo;
    private final Bundle mData;
    private final String mType;

    public CreateCredentialRequest(CallingAppInfo callingAppInfo, String type, Bundle data) {
        this.mCallingAppInfo =
                (CallingAppInfo)
                        Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mType =
                (String) Preconditions.checkStringNotEmpty(type, "type must not be null or empty");
        this.mData = (Bundle) Objects.requireNonNull(data, "data must not be null");
    }

    private CreateCredentialRequest(Parcel in) {
        this.mCallingAppInfo = (CallingAppInfo) in.readTypedObject(CallingAppInfo.CREATOR);
        this.mType = in.readString8();
        this.mData = (Bundle) in.readTypedObject(Bundle.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mCallingAppInfo, flags);
        dest.writeString8(this.mType);
        dest.writeTypedObject(this.mData, flags);
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
