package android.service.quickaccesswallet;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public final class GetWalletCardsError implements Parcelable {
    public static final Parcelable.Creator<GetWalletCardsError> CREATOR =
            new Parcelable.Creator<
                    GetWalletCardsError>() { // from class:
                                             // android.service.quickaccesswallet.GetWalletCardsError.1
                @Override // android.os.Parcelable.Creator
                public GetWalletCardsError createFromParcel(Parcel source) {
                    return GetWalletCardsError.readFromParcel(source);
                }

                @Override // android.os.Parcelable.Creator
                public GetWalletCardsError[] newArray(int size) {
                    return new GetWalletCardsError[size];
                }
            };
    private final Icon mIcon;
    private final CharSequence mMessage;

    public GetWalletCardsError(Icon icon, CharSequence message) {
        this.mIcon = icon;
        this.mMessage = message;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.mIcon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            this.mIcon.writeToParcel(dest, flags);
        }
        TextUtils.writeToParcel(this.mMessage, dest, flags);
    }

    public static GetWalletCardsError readFromParcel(Parcel source) {
        Icon icon = source.readByte() == 0 ? null : Icon.CREATOR.createFromParcel(source);
        CharSequence message = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        return new GetWalletCardsError(icon, message);
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getMessage() {
        return this.mMessage;
    }
}
