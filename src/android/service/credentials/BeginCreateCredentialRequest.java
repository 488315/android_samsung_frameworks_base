package android.service.credentials;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class BeginCreateCredentialRequest implements Parcelable {
    public static final Parcelable.Creator<BeginCreateCredentialRequest> CREATOR = new Parcelable.Creator<BeginCreateCredentialRequest>() { // from class: android.service.credentials.BeginCreateCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialRequest createFromParcel(Parcel parcel) {
            return new BeginCreateCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialRequest[] newArray(int i) {
            return new BeginCreateCredentialRequest[i];
        }
    };
    private final CallingAppInfo mCallingAppInfo;
    private final Bundle mData;
    private final String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BeginCreateCredentialRequest(String str, Bundle bundle, CallingAppInfo callingAppInfo) {
        this.mType = (String) Preconditions.checkStringNotEmpty(str, "type must not be null or empty");
        Bundle bundle2 = new Bundle();
        bundle2.putAll(bundle);
        this.mData = bundle2;
        this.mCallingAppInfo = callingAppInfo;
    }

    public BeginCreateCredentialRequest(String str, Bundle bundle) {
        this(str, bundle, null);
    }

    private BeginCreateCredentialRequest(Parcel parcel) {
        this.mCallingAppInfo = (CallingAppInfo) parcel.readTypedObject(CallingAppInfo.CREATOR);
        this.mType = parcel.readString8();
        this.mData = parcel.readBundle(Bundle.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mData);
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
