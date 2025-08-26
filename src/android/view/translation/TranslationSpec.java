package android.view.translation;

import android.annotation.NonNull;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TranslationSpec implements Parcelable {
    public static final Parcelable.Creator<TranslationSpec> CREATOR = new Parcelable.Creator<TranslationSpec>() { // from class: android.view.translation.TranslationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationSpec[] newArray(int i) {
            return new TranslationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TranslationSpec createFromParcel(Parcel parcel) {
            return new TranslationSpec(parcel);
        }
    };
    public static final int DATA_FORMAT_TEXT = 1;
    private final int mDataFormat;

    @Deprecated
    private final String mLanguage;
    private final ULocale mLocale;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataFormat {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    void parcelLocale(Parcel parcel, int i) throws IOException {
        parcel.writeSerializable(this.mLocale);
    }

    static ULocale unparcelLocale(Parcel parcel) {
        return (ULocale) parcel.readSerializable(ULocale.class.getClassLoader(), ULocale.class);
    }

    @Deprecated
    public TranslationSpec(String str, int i) {
        this.mLanguage = str;
        this.mDataFormat = i;
        this.mLocale = new ULocale.Builder().setLanguage(str).build();
    }

    public TranslationSpec(ULocale uLocale, int i) {
        Objects.requireNonNull(uLocale);
        this.mLanguage = uLocale.getLanguage();
        this.mLocale = uLocale;
        this.mDataFormat = i;
    }

    @Deprecated
    public String getLanguage() {
        return this.mLanguage;
    }

    public ULocale getLocale() {
        return this.mLocale;
    }

    public int getDataFormat() {
        return this.mDataFormat;
    }

    public String toString() {
        return "TranslationSpec { language = " + this.mLanguage + ", locale = " + this.mLocale + ", dataFormat = " + this.mDataFormat + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TranslationSpec translationSpec = (TranslationSpec) obj;
            if (Objects.equals(this.mLanguage, translationSpec.mLanguage) && Objects.equals(this.mLocale, translationSpec.mLocale) && this.mDataFormat == translationSpec.mDataFormat) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mLanguage) + 31) * 31) + Objects.hashCode(this.mLocale)) * 31) + this.mDataFormat;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeString(this.mLanguage);
        parcelLocale(parcel, i);
        parcel.writeInt(this.mDataFormat);
    }

    TranslationSpec(Parcel parcel) {
        String string = parcel.readString();
        ULocale uLocaleUnparcelLocale = unparcelLocale(parcel);
        int i = parcel.readInt();
        this.mLanguage = string;
        AnnotationValidations.validate((Class<? extends Annotation>) Deprecated.class, (Annotation) null, string);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mLocale = uLocaleUnparcelLocale;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) uLocaleUnparcelLocale);
        this.mDataFormat = i;
        AnnotationValidations.validate((Class<? extends Annotation>) DataFormat.class, (Annotation) null, i);
    }
}
