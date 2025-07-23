package android.view.translation;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TranslationResponseValue implements Parcelable {
    public static final Parcelable.Creator<TranslationResponseValue> CREATOR = new Parcelable.Creator<TranslationResponseValue>() { // from class: android.view.translation.TranslationResponseValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationResponseValue[] newArray(int i) {
            return new TranslationResponseValue[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationResponseValue createFromParcel(Parcel parcel) {
            return new TranslationResponseValue(parcel);
        }
    };
    public static final String EXTRA_DEFINITIONS = "android.view.translation.extra.DEFINITIONS";
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_SUCCESS = 0;
    private final Bundle mExtras;
    private final int mStatusCode;
    private final CharSequence mText;
    private final CharSequence mTransliteration;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence defaultText() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence defaultTransliteration() {
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static TranslationResponseValue forError() {
        return new TranslationResponseValue(1, null, Bundle.EMPTY, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle defaultExtras() {
        return Bundle.EMPTY;
    }

    private boolean extrasEquals(Bundle bundle) {
        if (Objects.equals(this.mExtras, bundle)) {
            return true;
        }
        return this.mExtras.isEmpty() && bundle.isEmpty();
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }
    }

    public static String statusToString(int i) {
        if (i == 0) {
            return "STATUS_SUCCESS";
        }
        if (i == 1) {
            return "STATUS_ERROR";
        }
        return Integer.toHexString(i);
    }

    TranslationResponseValue(int i, CharSequence charSequence, Bundle bundle, CharSequence charSequence2) {
        this.mStatusCode = i;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("statusCode was " + i + " but must be one of: STATUS_SUCCESS(0), STATUS_ERROR(1)");
        }
        this.mText = charSequence;
        this.mExtras = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
        this.mTransliteration = charSequence2;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getTransliteration() {
        return this.mTransliteration;
    }

    public String toString() {
        return "TranslationResponseValue { statusCode = " + statusToString(this.mStatusCode) + ", text = " + ((Object) this.mText) + ", extras = " + this.mExtras + ", transliteration = " + ((Object) this.mTransliteration) + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TranslationResponseValue translationResponseValue = (TranslationResponseValue) obj;
            if (this.mStatusCode == translationResponseValue.mStatusCode && Objects.equals(this.mText, translationResponseValue.mText) && extrasEquals(translationResponseValue.mExtras) && Objects.equals(this.mTransliteration, translationResponseValue.mTransliteration)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mStatusCode + 31) * 31) + Objects.hashCode(this.mText)) * 31) + Objects.hashCode(this.mExtras)) * 31) + Objects.hashCode(this.mTransliteration);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mText != null ? (byte) 2 : (byte) 0;
        if (this.mTransliteration != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mStatusCode);
        CharSequence charSequence = this.mText;
        if (charSequence != null) {
            parcel.writeCharSequence(charSequence);
        }
        parcel.writeBundle(this.mExtras);
        CharSequence charSequence2 = this.mTransliteration;
        if (charSequence2 != null) {
            parcel.writeCharSequence(charSequence2);
        }
    }

    TranslationResponseValue(Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        CharSequence readCharSequence = (readByte & 2) == 0 ? null : parcel.readCharSequence();
        Bundle readBundle = parcel.readBundle();
        CharSequence readCharSequence2 = (readByte & 8) == 0 ? null : parcel.readCharSequence();
        this.mStatusCode = readInt;
        if (readInt != 0 && readInt != 1) {
            throw new IllegalArgumentException("statusCode was " + readInt + " but must be one of: STATUS_SUCCESS(0), STATUS_ERROR(1)");
        }
        this.mText = readCharSequence;
        this.mExtras = readBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readBundle);
        this.mTransliteration = readCharSequence2;
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private Bundle mExtras;
        private int mStatusCode;
        private CharSequence mText;
        private CharSequence mTransliteration;

        public Builder(int i) {
            this.mStatusCode = i;
            if (i == 0 || i == 1) {
                return;
            }
            throw new IllegalArgumentException("statusCode was " + this.mStatusCode + " but must be one of: STATUS_SUCCESS(0), STATUS_ERROR(1)");
        }

        public Builder setText(CharSequence charSequence) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mText = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mExtras = bundle;
            return this;
        }

        public Builder setTransliteration(CharSequence charSequence) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mTransliteration = charSequence;
            return this;
        }

        public TranslationResponseValue build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 2) == 0) {
                this.mText = TranslationResponseValue.defaultText();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mExtras = TranslationResponseValue.defaultExtras();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mTransliteration = TranslationResponseValue.defaultTransliteration();
            }
            return new TranslationResponseValue(this.mStatusCode, this.mText, this.mExtras, this.mTransliteration);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
