package android.speech;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class RecognitionPart implements Parcelable {
    public static final int CONFIDENCE_LEVEL_HIGH = 5;
    public static final int CONFIDENCE_LEVEL_LOW = 1;
    public static final int CONFIDENCE_LEVEL_MEDIUM = 3;
    public static final int CONFIDENCE_LEVEL_MEDIUM_HIGH = 4;
    public static final int CONFIDENCE_LEVEL_MEDIUM_LOW = 2;
    public static final int CONFIDENCE_LEVEL_UNKNOWN = 0;
    public static final Parcelable.Creator<RecognitionPart> CREATOR = new Parcelable.Creator<RecognitionPart>() { // from class: android.speech.RecognitionPart.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionPart[] newArray(int i) {
            return new RecognitionPart[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionPart createFromParcel(Parcel parcel) {
            return new RecognitionPart(parcel);
        }
    };
    private final int mConfidenceLevel;
    private final String mFormattedText;
    private final String mRawText;
    private final long mTimestampMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfidenceLevel {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultFormattedText() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long defaultTimestampMillis() {
        return 0L;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void onConstructed() {
        Preconditions.checkArgumentNonnegative(this.mTimestampMillis, "The timestamp must be non-negative.");
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public Builder setFormattedText(String str) {
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
            Builder builder = (Builder) this;
            builder.checkNotUsed();
            builder.mBuilderFieldsSet |= 2;
            builder.mFormattedText = str;
            return builder;
        }
    }

    public static String confidenceLevelToString(int i) {
        if (i == 0) {
            return "CONFIDENCE_LEVEL_UNKNOWN";
        }
        if (i == 1) {
            return "CONFIDENCE_LEVEL_LOW";
        }
        if (i == 2) {
            return "CONFIDENCE_LEVEL_MEDIUM_LOW";
        }
        if (i == 3) {
            return "CONFIDENCE_LEVEL_MEDIUM";
        }
        if (i == 4) {
            return "CONFIDENCE_LEVEL_MEDIUM_HIGH";
        }
        if (i == 5) {
            return "CONFIDENCE_LEVEL_HIGH";
        }
        return Integer.toHexString(i);
    }

    RecognitionPart(String str, String str2, long j, int i) {
        this.mRawText = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mFormattedText = str2;
        this.mTimestampMillis = j;
        this.mConfidenceLevel = i;
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException("confidenceLevel was " + i + " but must be one of: CONFIDENCE_LEVEL_UNKNOWN(0), CONFIDENCE_LEVEL_LOW(1), CONFIDENCE_LEVEL_MEDIUM_LOW(2), CONFIDENCE_LEVEL_MEDIUM(3), CONFIDENCE_LEVEL_MEDIUM_HIGH(4), CONFIDENCE_LEVEL_HIGH(5)");
        }
        onConstructed();
    }

    public String getRawText() {
        return this.mRawText;
    }

    public String getFormattedText() {
        return this.mFormattedText;
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public String toString() {
        return "RecognitionPart { rawText = " + this.mRawText + ", formattedText = " + this.mFormattedText + ", timestampMillis = " + this.mTimestampMillis + ", confidenceLevel = " + confidenceLevelToString(this.mConfidenceLevel) + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RecognitionPart recognitionPart = (RecognitionPart) obj;
            if (Objects.equals(this.mRawText, recognitionPart.mRawText) && Objects.equals(this.mFormattedText, recognitionPart.mFormattedText) && this.mTimestampMillis == recognitionPart.mTimestampMillis && this.mConfidenceLevel == recognitionPart.mConfidenceLevel) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((Objects.hashCode(this.mRawText) + 31) * 31) + Objects.hashCode(this.mFormattedText)) * 31) + Long.hashCode(this.mTimestampMillis)) * 31) + this.mConfidenceLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mFormattedText != null ? (byte) 2 : (byte) 0);
        parcel.writeString(this.mRawText);
        String str = this.mFormattedText;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeLong(this.mTimestampMillis);
        parcel.writeInt(this.mConfidenceLevel);
    }

    RecognitionPart(Parcel parcel) {
        byte readByte = parcel.readByte();
        String readString = parcel.readString();
        String readString2 = (readByte & 2) == 0 ? null : parcel.readString();
        long readLong = parcel.readLong();
        int readInt = parcel.readInt();
        this.mRawText = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
        this.mFormattedText = readString2;
        this.mTimestampMillis = readLong;
        this.mConfidenceLevel = readInt;
        if (readInt != 0 && readInt != 1 && readInt != 2 && readInt != 3 && readInt != 4 && readInt != 5) {
            throw new IllegalArgumentException("confidenceLevel was " + readInt + " but must be one of: CONFIDENCE_LEVEL_UNKNOWN(0), CONFIDENCE_LEVEL_LOW(1), CONFIDENCE_LEVEL_MEDIUM_LOW(2), CONFIDENCE_LEVEL_MEDIUM(3), CONFIDENCE_LEVEL_MEDIUM_HIGH(4), CONFIDENCE_LEVEL_HIGH(5)");
        }
        onConstructed();
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private String mFormattedText;
        private String mRawText;
        private long mTimestampMillis;

        @Override // android.speech.RecognitionPart.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setFormattedText(String str) {
            return super.setFormattedText(str);
        }

        public Builder(String str) {
            this.mRawText = str;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        }

        public Builder setRawText(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mRawText = str;
            return this;
        }

        public Builder setTimestampMillis(long j) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTimestampMillis = j;
            return this;
        }

        public Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mConfidenceLevel = i;
            return this;
        }

        public RecognitionPart build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 2) == 0) {
                this.mFormattedText = RecognitionPart.defaultFormattedText();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mTimestampMillis = RecognitionPart.defaultTimestampMillis();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mConfidenceLevel = RecognitionPart.defaultConfidenceLevel();
            }
            return new RecognitionPart(this.mRawText, this.mFormattedText, this.mTimestampMillis, this.mConfidenceLevel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
