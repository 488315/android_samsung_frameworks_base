package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class TextAttribute implements Parcelable {
    public static final Parcelable.Creator<TextAttribute> CREATOR = new Parcelable.Creator<TextAttribute>() { // from class: android.view.inputmethod.TextAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextAttribute createFromParcel(Parcel parcel) {
            return new TextAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextAttribute[] newArray(int i) {
            return new TextAttribute[i];
        }
    };
    private final PersistableBundle mExtras;
    private final List<String> mTextConversionSuggestions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TextAttribute(Builder builder) {
        this.mTextConversionSuggestions = builder.mTextConversionSuggestions;
        this.mExtras = builder.mExtras;
    }

    private TextAttribute(Parcel parcel) {
        this.mTextConversionSuggestions = parcel.createStringArrayList();
        this.mExtras = parcel.readPersistableBundle();
    }

    public List<String> getTextConversionSuggestions() {
        return this.mTextConversionSuggestions;
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public static final class Builder {
        private List<String> mTextConversionSuggestions = new ArrayList();
        private PersistableBundle mExtras = new PersistableBundle();

        public Builder setTextConversionSuggestions(List<String> list) {
            this.mTextConversionSuggestions = Collections.unmodifiableList(list);
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public TextAttribute build() {
            return new TextAttribute(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.mTextConversionSuggestions);
        parcel.writePersistableBundle(this.mExtras);
    }
}
