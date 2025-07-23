package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public final class CorrectionInfo implements Parcelable {
    public static final Parcelable.Creator<CorrectionInfo> CREATOR = new Parcelable.Creator<CorrectionInfo>() { // from class: android.view.inputmethod.CorrectionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CorrectionInfo createFromParcel(Parcel parcel) {
            return new CorrectionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CorrectionInfo[] newArray(int i) {
            return new CorrectionInfo[i];
        }
    };
    private final CharSequence mNewText;
    private final int mOffset;
    private final CharSequence mOldText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CorrectionInfo(int i, CharSequence charSequence, CharSequence charSequence2) {
        this.mOffset = i;
        this.mOldText = charSequence;
        this.mNewText = charSequence2;
    }

    private CorrectionInfo(Parcel parcel) {
        this.mOffset = parcel.readInt();
        this.mOldText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mNewText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public int getOffset() {
        return this.mOffset;
    }

    public CharSequence getOldText() {
        return this.mOldText;
    }

    public CharSequence getNewText() {
        return this.mNewText;
    }

    public String toString() {
        return "CorrectionInfo{#" + this.mOffset + " \"" + ((Object) this.mOldText) + "\" -> \"" + ((Object) this.mNewText) + "\"}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mOffset);
        TextUtils.writeToParcel(this.mOldText, parcel, i);
        TextUtils.writeToParcel(this.mNewText, parcel, i);
    }
}
