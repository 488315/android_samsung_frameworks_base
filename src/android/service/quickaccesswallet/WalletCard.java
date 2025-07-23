package android.service.quickaccesswallet;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class WalletCard implements Parcelable {
    public static final int CARD_TYPE_NON_PAYMENT = 2;
    public static final int CARD_TYPE_PAYMENT = 1;
    public static final int CARD_TYPE_UNKNOWN = 0;
    public static final Parcelable.Creator<WalletCard> CREATOR = new Parcelable.Creator<WalletCard>() { // from class: android.service.quickaccesswallet.WalletCard.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WalletCard createFromParcel(Parcel parcel) {
            return WalletCard.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WalletCard[] newArray(int i) {
            return new WalletCard[i];
        }
    };
    private final Icon mCardIcon;
    private final String mCardId;
    private final Icon mCardImage;
    private final CharSequence mCardLabel;
    private List<Location> mCardLocations;
    private final int mCardType;
    private final CharSequence mContentDescription;
    private final Icon mNonPaymentCardSecondaryImage;
    private final PendingIntent mPendingIntent;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CardType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private WalletCard(Builder builder) {
        this.mCardId = builder.mCardId;
        this.mCardType = builder.mCardType;
        this.mCardImage = builder.mCardImage;
        this.mContentDescription = builder.mContentDescription;
        this.mPendingIntent = builder.mPendingIntent;
        this.mCardIcon = builder.mCardIcon;
        this.mCardLabel = builder.mCardLabel;
        this.mNonPaymentCardSecondaryImage = builder.mNonPaymentCardSecondaryImage;
        this.mCardLocations = builder.mCardLocations;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCardId);
        parcel.writeInt(this.mCardType);
        this.mCardImage.writeToParcel(parcel, i);
        TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
        writeIconIfNonNull(this.mCardIcon, parcel, i);
        TextUtils.writeToParcel(this.mCardLabel, parcel, i);
        writeIconIfNonNull(this.mNonPaymentCardSecondaryImage, parcel, i);
        parcel.writeTypedList(this.mCardLocations, i);
    }

    private void writeIconIfNonNull(Icon icon, Parcel parcel, int i) {
        if (icon == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            icon.writeToParcel(parcel, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WalletCard readFromParcel(Parcel parcel) {
        String readString = parcel.readString();
        int readInt = parcel.readInt();
        Icon createFromParcel = Icon.CREATOR.createFromParcel(parcel);
        CharSequence createFromParcel2 = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        PendingIntent readPendingIntentOrNullFromParcel = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        Icon createFromParcel3 = parcel.readByte() == 0 ? null : Icon.CREATOR.createFromParcel(parcel);
        CharSequence createFromParcel4 = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        Icon createFromParcel5 = parcel.readByte() != 0 ? Icon.CREATOR.createFromParcel(parcel) : null;
        Builder cardLabel = new Builder(readString, readInt, createFromParcel, createFromParcel2, readPendingIntentOrNullFromParcel).setCardIcon(createFromParcel3).setCardLabel(createFromParcel4);
        if (readInt == 2) {
            cardLabel.setNonPaymentCardSecondaryImage(createFromParcel5);
        }
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, Location.CREATOR);
        cardLabel.setCardLocations(arrayList);
        return cardLabel.build();
    }

    public String getCardId() {
        return this.mCardId;
    }

    public int getCardType() {
        return this.mCardType;
    }

    public Icon getCardImage() {
        return this.mCardImage;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public Icon getCardIcon() {
        return this.mCardIcon;
    }

    public CharSequence getCardLabel() {
        return this.mCardLabel;
    }

    public Icon getNonPaymentCardSecondaryImage() {
        return this.mNonPaymentCardSecondaryImage;
    }

    public List<Location> getCardLocations() {
        return this.mCardLocations;
    }

    public void removeCardLocations() {
        this.mCardLocations = new ArrayList();
    }

    public static final class Builder {
        private Icon mCardIcon;
        private String mCardId;
        private Icon mCardImage;
        private CharSequence mCardLabel;
        private List<Location> mCardLocations;
        private int mCardType;
        private CharSequence mContentDescription;
        private Icon mNonPaymentCardSecondaryImage;
        private PendingIntent mPendingIntent;

        public Builder(String str, int i, Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mCardLocations = new ArrayList();
            this.mCardId = str;
            this.mCardType = i;
            this.mCardImage = icon;
            this.mContentDescription = charSequence;
            this.mPendingIntent = pendingIntent;
        }

        public Builder(String str, Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
            this(str, 0, icon, charSequence, pendingIntent);
        }

        public Builder setCardIcon(Icon icon) {
            this.mCardIcon = icon;
            return this;
        }

        public Builder setCardLabel(CharSequence charSequence) {
            this.mCardLabel = charSequence;
            return this;
        }

        public Builder setNonPaymentCardSecondaryImage(Icon icon) {
            Preconditions.checkState(this.mCardType == 2, "This field can only be set on non-payment cards");
            this.mNonPaymentCardSecondaryImage = icon;
            return this;
        }

        public Builder setCardLocations(List<Location> list) {
            Preconditions.checkCollectionElementsNotNull(list, "cardLocations");
            this.mCardLocations = list;
            return this;
        }

        public WalletCard build() {
            return new WalletCard(this);
        }
    }
}
