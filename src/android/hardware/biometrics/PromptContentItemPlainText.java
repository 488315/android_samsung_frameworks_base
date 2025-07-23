package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class PromptContentItemPlainText implements PromptContentItemParcelable {
    public static final Parcelable.Creator<PromptContentItemPlainText> CREATOR = new Parcelable.Creator<PromptContentItemPlainText>() { // from class: android.hardware.biometrics.PromptContentItemPlainText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentItemPlainText createFromParcel(Parcel parcel) {
            return new PromptContentItemPlainText(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentItemPlainText[] newArray(int i) {
            return new PromptContentItemPlainText[i];
        }
    };
    private final String mText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PromptContentItemPlainText(String str) {
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
