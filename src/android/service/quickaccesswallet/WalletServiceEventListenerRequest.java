package android.service.quickaccesswallet;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class WalletServiceEventListenerRequest implements Parcelable {
    public static final Parcelable.Creator<WalletServiceEventListenerRequest> CREATOR = new Parcelable.Creator<WalletServiceEventListenerRequest>() { // from class: android.service.quickaccesswallet.WalletServiceEventListenerRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WalletServiceEventListenerRequest createFromParcel(Parcel parcel) {
            return WalletServiceEventListenerRequest.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WalletServiceEventListenerRequest[] newArray(int i) {
            return new WalletServiceEventListenerRequest[i];
        }
    };
    private final String mListenerId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WalletServiceEventListenerRequest(String str) {
        this.mListenerId = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mListenerId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WalletServiceEventListenerRequest readFromParcel(Parcel parcel) {
        return new WalletServiceEventListenerRequest(parcel.readString());
    }

    public String getListenerId() {
        return this.mListenerId;
    }
}
