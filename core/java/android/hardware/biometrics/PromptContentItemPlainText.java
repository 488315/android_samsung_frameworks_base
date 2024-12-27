package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class PromptContentItemPlainText implements PromptContentItemParcelable {
    public static final Parcelable.Creator<PromptContentItemPlainText> CREATOR = new Parcelable.Creator<PromptContentItemPlainText>() { // from class: android.hardware.biometrics.PromptContentItemPlainText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentItemPlainText createFromParcel(Parcel in) {
            return new PromptContentItemPlainText(in.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentItemPlainText[] newArray(int size) {
            return new PromptContentItemPlainText[size];
        }
    };
    private final String mText;

    public PromptContentItemPlainText(String text) {
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
