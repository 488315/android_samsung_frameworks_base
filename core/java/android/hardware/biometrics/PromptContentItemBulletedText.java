package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

public final class PromptContentItemBulletedText implements PromptContentItemParcelable {
    public static final Parcelable.Creator<PromptContentItemBulletedText> CREATOR =
            new Parcelable.Creator<PromptContentItemBulletedText>() { // from class:
                // android.hardware.biometrics.PromptContentItemBulletedText.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptContentItemBulletedText createFromParcel(Parcel in) {
                    return new PromptContentItemBulletedText(in.readString());
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptContentItemBulletedText[] newArray(int size) {
                    return new PromptContentItemBulletedText[size];
                }
            };
    private final String mText;

    public PromptContentItemBulletedText(String text) {
        this.mText = text;
    }

    public String getText() {
        return this.mText;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mText);
    }
}
