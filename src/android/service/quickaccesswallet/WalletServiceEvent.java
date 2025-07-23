package android.service.quickaccesswallet;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class WalletServiceEvent implements Parcelable {
    public static final Parcelable.Creator<WalletServiceEvent> CREATOR = new Parcelable.Creator<WalletServiceEvent>() { // from class: android.service.quickaccesswallet.WalletServiceEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WalletServiceEvent createFromParcel(Parcel parcel) {
            return new WalletServiceEvent(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WalletServiceEvent[] newArray(int i) {
            return new WalletServiceEvent[i];
        }
    };
    public static final int TYPE_NFC_PAYMENT_STARTED = 1;
    public static final int TYPE_WALLET_CARDS_UPDATED = 2;
    private final int mEventType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WalletServiceEvent(int i) {
        this.mEventType = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
    }

    public int getEventType() {
        return this.mEventType;
    }
}
