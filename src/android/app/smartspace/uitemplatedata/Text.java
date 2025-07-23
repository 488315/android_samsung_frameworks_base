package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.SmartspaceUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class Text implements Parcelable {
    public static final Parcelable.Creator<Text> CREATOR = new Parcelable.Creator<Text>() { // from class: android.app.smartspace.uitemplatedata.Text.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Text createFromParcel(Parcel parcel) {
            return new Text(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Text[] newArray(int i) {
            return new Text[i];
        }
    };
    private final int mMaxLines;
    private final CharSequence mText;
    private final TextUtils.TruncateAt mTruncateAtType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Text(Parcel parcel) {
        this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mTruncateAtType = TextUtils.TruncateAt.valueOf(parcel.readString());
        this.mMaxLines = parcel.readInt();
    }

    private Text(CharSequence charSequence, TextUtils.TruncateAt truncateAt, int i) {
        this.mText = charSequence;
        this.mTruncateAtType = truncateAt;
        this.mMaxLines = i;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public TextUtils.TruncateAt getTruncateAtType() {
        return this.mTruncateAtType;
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Text)) {
            return false;
        }
        Text text = (Text) obj;
        return this.mTruncateAtType == text.mTruncateAtType && SmartspaceUtils.isEqual(this.mText, text.mText) && this.mMaxLines == text.mMaxLines;
    }

    public int hashCode() {
        return Objects.hash(this.mText, this.mTruncateAtType, Integer.valueOf(this.mMaxLines));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.mText, parcel, i);
        parcel.writeString(this.mTruncateAtType.name());
        parcel.writeInt(this.mMaxLines);
    }

    public String toString() {
        return "Text{mText=" + ((Object) this.mText) + ", mTruncateAtType=" + this.mTruncateAtType + ", mMaxLines=" + this.mMaxLines + '}';
    }

    @SystemApi
    public static final class Builder {
        private final CharSequence mText;
        private TextUtils.TruncateAt mTruncateAtType = TextUtils.TruncateAt.END;
        private int mMaxLines = 1;

        public Builder(CharSequence charSequence) {
            this.mText = (CharSequence) Objects.requireNonNull(charSequence);
        }

        public Builder setTruncateAtType(TextUtils.TruncateAt truncateAt) {
            this.mTruncateAtType = (TextUtils.TruncateAt) Objects.requireNonNull(truncateAt);
            return this;
        }

        public Builder setMaxLines(int i) {
            this.mMaxLines = i;
            return this;
        }

        public Text build() {
            return new Text(this.mText, this.mTruncateAtType, this.mMaxLines);
        }
    }
}
