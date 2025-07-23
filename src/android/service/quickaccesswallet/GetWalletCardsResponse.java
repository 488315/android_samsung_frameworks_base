package android.service.quickaccesswallet;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class GetWalletCardsResponse implements Parcelable {
    public static final Parcelable.Creator<GetWalletCardsResponse> CREATOR = new Parcelable.Creator<GetWalletCardsResponse>() { // from class: android.service.quickaccesswallet.GetWalletCardsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetWalletCardsResponse createFromParcel(Parcel parcel) {
            return GetWalletCardsResponse.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetWalletCardsResponse[] newArray(int i) {
            return new GetWalletCardsResponse[i];
        }
    };
    private final int mSelectedIndex;
    private final List<WalletCard> mWalletCards;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GetWalletCardsResponse(List<WalletCard> list, int i) {
        this.mWalletCards = list;
        this.mSelectedIndex = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mWalletCards.size());
        parcel.writeParcelableList(this.mWalletCards, i);
        parcel.writeInt(this.mSelectedIndex);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static GetWalletCardsResponse readFromParcel(Parcel parcel) {
        return new GetWalletCardsResponse(parcel.readParcelableList(new ArrayList(parcel.readInt()), WalletCard.class.getClassLoader(), WalletCard.class), parcel.readInt());
    }

    public List<WalletCard> getWalletCards() {
        return this.mWalletCards;
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }
}
