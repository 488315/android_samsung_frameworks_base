package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public final class SurroundingText implements Parcelable {
    public static final Parcelable.Creator<SurroundingText> CREATOR = new Parcelable.Creator<SurroundingText>() { // from class: android.view.inputmethod.SurroundingText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurroundingText createFromParcel(Parcel parcel) {
            CharSequence createFromParcel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (createFromParcel == null) {
                createFromParcel = "";
            }
            return new SurroundingText(createFromParcel, readInt, readInt2, readInt3);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurroundingText[] newArray(int i) {
            return new SurroundingText[i];
        }
    };
    private final int mOffset;
    private final int mSelectionEnd;
    private final int mSelectionStart;
    private final CharSequence mText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SurroundingText(CharSequence charSequence, int i, int i2, int i3) {
        this.mText = copyWithParcelableSpans(charSequence);
        this.mSelectionStart = i;
        this.mSelectionEnd = i2;
        this.mOffset = i3;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public int getSelectionStart() {
        return this.mSelectionStart;
    }

    public int getSelectionEnd() {
        return this.mSelectionEnd;
    }

    public int getOffset() {
        return this.mOffset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.mText, parcel, i);
        parcel.writeInt(this.mSelectionStart);
        parcel.writeInt(this.mSelectionEnd);
        parcel.writeInt(this.mOffset);
    }

    private static CharSequence copyWithParcelableSpans(CharSequence charSequence) {
        Parcel parcel = null;
        if (charSequence == null) {
            return null;
        }
        try {
            parcel = Parcel.obtain();
            TextUtils.writeToParcel(charSequence, parcel, 0);
            parcel.setDataPosition(0);
            return TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        } finally {
            if (parcel != null) {
                parcel.recycle();
            }
        }
    }

    public boolean isEqualTo(SurroundingText surroundingText) {
        if (surroundingText == null) {
            return false;
        }
        if (this == surroundingText) {
            return true;
        }
        return this.mSelectionStart == surroundingText.mSelectionStart && this.mSelectionEnd == surroundingText.mSelectionEnd && this.mOffset == surroundingText.mOffset && TextUtils.equals(this.mText, surroundingText.mText);
    }
}
