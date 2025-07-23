package android.security.keystore;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeystoreResponse implements Parcelable {
    public static final Parcelable.Creator<KeystoreResponse> CREATOR = new Parcelable.Creator<KeystoreResponse>() { // from class: android.security.keystore.KeystoreResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreResponse createFromParcel(Parcel parcel) {
            return new KeystoreResponse(parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreResponse[] newArray(int i) {
            return new KeystoreResponse[i];
        }
    };
    public final int error_code_;
    public final String error_msg_;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected KeystoreResponse(int i, String str) {
        this.error_code_ = i;
        this.error_msg_ = str;
    }

    public final int getErrorCode() {
        return this.error_code_;
    }

    public final String getErrorMessage() {
        return this.error_msg_;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.error_code_);
        parcel.writeString(this.error_msg_);
    }
}
