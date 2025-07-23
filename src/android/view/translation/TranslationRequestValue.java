package android.view.translation;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TranslationRequestValue implements Parcelable {
    public static final Parcelable.Creator<TranslationRequestValue> CREATOR = new Parcelable.Creator<TranslationRequestValue>() { // from class: android.view.translation.TranslationRequestValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationRequestValue[] newArray(int i) {
            return new TranslationRequestValue[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationRequestValue createFromParcel(Parcel parcel) {
            return new TranslationRequestValue(parcel);
        }
    };
    private final CharSequence mText;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static TranslationRequestValue forText(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text should not be null");
        return new TranslationRequestValue(charSequence);
    }

    public CharSequence getText() {
        return this.mText;
    }

    public TranslationRequestValue(CharSequence charSequence) {
        this.mText = charSequence;
    }

    public String toString() {
        return "TranslationRequestValue { text = " + ((Object) this.mText) + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mText, ((TranslationRequestValue) obj).mText);
    }

    public int hashCode() {
        return 31 + Objects.hashCode(this.mText);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mText != null ? (byte) 1 : (byte) 0);
        CharSequence charSequence = this.mText;
        if (charSequence != null) {
            parcel.writeCharSequence(charSequence);
        }
    }

    TranslationRequestValue(Parcel parcel) {
        this.mText = (parcel.readByte() & 1) == 0 ? null : parcel.readCharSequence();
    }
}
