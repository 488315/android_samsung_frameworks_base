package android.service.quickaccesswallet;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes3.dex */
public final class SelectWalletCardRequest implements Parcelable {
    public static final Parcelable.Creator<SelectWalletCardRequest> CREATOR = new Parcelable.Creator<SelectWalletCardRequest>() { // from class: android.service.quickaccesswallet.SelectWalletCardRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectWalletCardRequest createFromParcel(Parcel source) {
            String cardId = source.readString();
            return new SelectWalletCardRequest(cardId);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectWalletCardRequest[] newArray(int size) {
            return new SelectWalletCardRequest[size];
        }
    };
    private final String mCardId;

    public SelectWalletCardRequest(String cardId) {
        this.mCardId = cardId;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCardId);
    }

    public String getCardId() {
        return this.mCardId;
    }
}
