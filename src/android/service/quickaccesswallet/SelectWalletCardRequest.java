package android.service.quickaccesswallet;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class SelectWalletCardRequest implements Parcelable {
    public static final Parcelable.Creator<SelectWalletCardRequest> CREATOR = new Parcelable.Creator<SelectWalletCardRequest>() { // from class: android.service.quickaccesswallet.SelectWalletCardRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectWalletCardRequest createFromParcel(Parcel parcel) {
            return new SelectWalletCardRequest(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectWalletCardRequest[] newArray(int i) {
            return new SelectWalletCardRequest[i];
        }
    };
    private final String mCardId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SelectWalletCardRequest(String str) {
        this.mCardId = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCardId);
    }

    public String getCardId() {
        return this.mCardId;
    }
}
