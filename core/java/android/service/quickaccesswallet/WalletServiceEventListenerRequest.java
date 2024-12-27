package android.service.quickaccesswallet;

import android.os.Parcel;
import android.os.Parcelable;

public final class WalletServiceEventListenerRequest implements Parcelable {
    public static final Parcelable.Creator<WalletServiceEventListenerRequest> CREATOR =
            new Parcelable.Creator<
                    WalletServiceEventListenerRequest>() { // from class:
                                                           // android.service.quickaccesswallet.WalletServiceEventListenerRequest.1
                @Override // android.os.Parcelable.Creator
                public WalletServiceEventListenerRequest createFromParcel(Parcel source) {
                    return WalletServiceEventListenerRequest.readFromParcel(source);
                }

                @Override // android.os.Parcelable.Creator
                public WalletServiceEventListenerRequest[] newArray(int size) {
                    return new WalletServiceEventListenerRequest[size];
                }
            };
    private final String mListenerId;

    public WalletServiceEventListenerRequest(String listenerKey) {
        this.mListenerId = listenerKey;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mListenerId);
    }

    public static WalletServiceEventListenerRequest readFromParcel(Parcel source) {
        String listenerId = source.readString();
        return new WalletServiceEventListenerRequest(listenerId);
    }

    public String getListenerId() {
        return this.mListenerId;
    }
}
