package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class ExtractedText implements Parcelable {
    public static final Parcelable.Creator<ExtractedText> CREATOR = new Parcelable.Creator<ExtractedText>() { // from class: android.view.inputmethod.ExtractedText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtractedText createFromParcel(Parcel parcel) {
            ExtractedText extractedText = new ExtractedText();
            extractedText.text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            extractedText.startOffset = parcel.readInt();
            extractedText.partialStartOffset = parcel.readInt();
            extractedText.partialEndOffset = parcel.readInt();
            extractedText.selectionStart = parcel.readInt();
            extractedText.selectionEnd = parcel.readInt();
            extractedText.flags = parcel.readInt();
            extractedText.hint = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            return extractedText;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtractedText[] newArray(int i) {
            return new ExtractedText[i];
        }
    };
    public static final int FLAG_SELECTING = 2;
    public static final int FLAG_SINGLE_LINE = 1;
    public int flags;
    public CharSequence hint;
    public int partialEndOffset;
    public int partialStartOffset;
    public int selectionEnd;
    public int selectionStart;
    public int startOffset;
    public CharSequence text;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.text, parcel, i);
        parcel.writeInt(this.startOffset);
        parcel.writeInt(this.partialStartOffset);
        parcel.writeInt(this.partialEndOffset);
        parcel.writeInt(this.selectionStart);
        parcel.writeInt(this.selectionEnd);
        parcel.writeInt(this.flags);
        TextUtils.writeToParcel(this.hint, parcel, i);
    }
}
