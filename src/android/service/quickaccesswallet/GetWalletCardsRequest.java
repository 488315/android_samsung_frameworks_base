package android.service.quickaccesswallet;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class GetWalletCardsRequest implements Parcelable {
    public static final Parcelable.Creator<GetWalletCardsRequest> CREATOR = new Parcelable.Creator<GetWalletCardsRequest>() { // from class: android.service.quickaccesswallet.GetWalletCardsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetWalletCardsRequest createFromParcel(Parcel parcel) {
            return new GetWalletCardsRequest(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetWalletCardsRequest[] newArray(int i) {
            return new GetWalletCardsRequest[i];
        }
    };
    private final int mCardHeightPx;
    private final int mCardWidthPx;
    private final int mIconSizePx;
    private final int mMaxCards;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GetWalletCardsRequest(int i, int i2, int i3, int i4) {
        this.mCardWidthPx = i;
        this.mCardHeightPx = i2;
        this.mIconSizePx = i3;
        this.mMaxCards = i4;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCardWidthPx);
        parcel.writeInt(this.mCardHeightPx);
        parcel.writeInt(this.mIconSizePx);
        parcel.writeInt(this.mMaxCards);
    }

    public int getCardWidthPx() {
        return this.mCardWidthPx;
    }

    public int getCardHeightPx() {
        return this.mCardHeightPx;
    }

    public int getIconSizePx() {
        return this.mIconSizePx;
    }

    public int getMaxCards() {
        return this.mMaxCards;
    }
}
