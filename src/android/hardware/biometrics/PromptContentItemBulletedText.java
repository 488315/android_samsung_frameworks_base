package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class PromptContentItemBulletedText implements PromptContentItemParcelable {
    public static final Parcelable.Creator<PromptContentItemBulletedText> CREATOR = new Parcelable.Creator<PromptContentItemBulletedText>() { // from class: android.hardware.biometrics.PromptContentItemBulletedText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentItemBulletedText createFromParcel(Parcel parcel) {
            return new PromptContentItemBulletedText(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentItemBulletedText[] newArray(int i) {
            return new PromptContentItemBulletedText[i];
        }
    };
    private final String mText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PromptContentItemBulletedText(String str) {
        this.mText = str;
    }

    public String getText() {
        return this.mText;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mText);
    }
}
