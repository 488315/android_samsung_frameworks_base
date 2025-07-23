package android.service.quickaccesswallet;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public final class GetWalletCardsError implements Parcelable {
    public static final Parcelable.Creator<GetWalletCardsError> CREATOR = new Parcelable.Creator<GetWalletCardsError>() { // from class: android.service.quickaccesswallet.GetWalletCardsError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetWalletCardsError createFromParcel(Parcel parcel) {
            return GetWalletCardsError.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetWalletCardsError[] newArray(int i) {
            return new GetWalletCardsError[i];
        }
    };
    private final Icon mIcon;
    private final CharSequence mMessage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GetWalletCardsError(Icon icon, CharSequence charSequence) {
        this.mIcon = icon;
        this.mMessage = charSequence;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mIcon == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            this.mIcon.writeToParcel(parcel, i);
        }
        TextUtils.writeToParcel(this.mMessage, parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static GetWalletCardsError readFromParcel(Parcel parcel) {
        return new GetWalletCardsError(parcel.readByte() == 0 ? null : Icon.CREATOR.createFromParcel(parcel), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel));
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getMessage() {
        return this.mMessage;
    }
}
