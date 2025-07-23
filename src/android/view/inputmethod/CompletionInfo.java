package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public final class CompletionInfo implements Parcelable {
    public static final Parcelable.Creator<CompletionInfo> CREATOR = new Parcelable.Creator<CompletionInfo>() { // from class: android.view.inputmethod.CompletionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompletionInfo createFromParcel(Parcel parcel) {
            return new CompletionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompletionInfo[] newArray(int i) {
            return new CompletionInfo[i];
        }
    };
    private final long mId;
    private final CharSequence mLabel;
    private final int mPosition;
    private final CharSequence mText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CompletionInfo(long j, int i, CharSequence charSequence) {
        this.mId = j;
        this.mPosition = i;
        this.mText = charSequence;
        this.mLabel = null;
    }

    public CompletionInfo(long j, int i, CharSequence charSequence, CharSequence charSequence2) {
        this.mId = j;
        this.mPosition = i;
        this.mText = charSequence;
        this.mLabel = charSequence2;
    }

    private CompletionInfo(Parcel parcel) {
        this.mId = parcel.readLong();
        this.mPosition = parcel.readInt();
        this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public long getId() {
        return this.mId;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public String toString() {
        return "CompletionInfo{#" + this.mPosition + " \"" + ((Object) this.mText) + "\" id=" + this.mId + " label=" + ((Object) this.mLabel) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        parcel.writeInt(this.mPosition);
        TextUtils.writeToParcel(this.mText, parcel, i);
        TextUtils.writeToParcel(this.mLabel, parcel, i);
    }
}
