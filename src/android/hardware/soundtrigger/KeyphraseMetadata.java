package android.hardware.soundtrigger;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import com.android.internal.util.AnnotationValidations;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class KeyphraseMetadata implements Parcelable {
    public static final Parcelable.Creator<KeyphraseMetadata> CREATOR = new Parcelable.Creator<KeyphraseMetadata>() { // from class: android.hardware.soundtrigger.KeyphraseMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyphraseMetadata[] newArray(int i) {
            return new KeyphraseMetadata[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyphraseMetadata createFromParcel(Parcel parcel) {
            return new KeyphraseMetadata(parcel);
        }
    };
    private final int mId;
    private final String mKeyphrase;
    private final int mRecognitionModeFlags;
    private final ArraySet<Locale> mSupportedLocales;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeyphraseMetadata(int i, String str, Set<Locale> set, int i2) {
        this.mId = i;
        this.mKeyphrase = str;
        this.mSupportedLocales = new ArraySet<>(set);
        this.mRecognitionModeFlags = i2;
    }

    public int getId() {
        return this.mId;
    }

    public String getKeyphrase() {
        return this.mKeyphrase;
    }

    public Set<Locale> getSupportedLocales() {
        return this.mSupportedLocales;
    }

    public int getRecognitionModeFlags() {
        return this.mRecognitionModeFlags;
    }

    public boolean supportsPhrase(String str) {
        return getKeyphrase().isEmpty() || getKeyphrase().equalsIgnoreCase(str);
    }

    public boolean supportsLocale(Locale locale) {
        return getSupportedLocales().isEmpty() || getSupportedLocales().contains(locale);
    }

    public String toString() {
        return "KeyphraseMetadata { id = " + this.mId + ", keyphrase = " + this.mKeyphrase + ", supportedLocales = " + this.mSupportedLocales + ", recognitionModeFlags = " + this.mRecognitionModeFlags + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            KeyphraseMetadata keyphraseMetadata = (KeyphraseMetadata) obj;
            if (this.mId == keyphraseMetadata.mId && Objects.equals(this.mKeyphrase, keyphraseMetadata.mKeyphrase) && Objects.equals(this.mSupportedLocales, keyphraseMetadata.mSupportedLocales) && this.mRecognitionModeFlags == keyphraseMetadata.mRecognitionModeFlags) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mId + 31) * 31) + Objects.hashCode(this.mKeyphrase)) * 31) + Objects.hashCode(this.mSupportedLocales)) * 31) + this.mRecognitionModeFlags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mKeyphrase);
        parcel.writeArraySet(this.mSupportedLocales);
        parcel.writeInt(this.mRecognitionModeFlags);
    }

    KeyphraseMetadata(Parcel parcel) {
        int readInt = parcel.readInt();
        String readString = parcel.readString();
        ArraySet readArraySet = parcel.readArraySet(null);
        int readInt2 = parcel.readInt();
        this.mId = readInt;
        this.mKeyphrase = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
        this.mSupportedLocales = readArraySet;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readArraySet);
        this.mRecognitionModeFlags = readInt2;
    }
}
