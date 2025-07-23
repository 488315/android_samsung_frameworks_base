package android.service.voice;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class HotwordRejectedResult implements Parcelable {
    public static final int CONFIDENCE_LEVEL_HIGH = 3;
    public static final int CONFIDENCE_LEVEL_LOW = 1;
    public static final int CONFIDENCE_LEVEL_MEDIUM = 2;
    public static final int CONFIDENCE_LEVEL_NONE = 0;
    public static final int CONFIDENCE_LEVEL_VERY_HIGH = 4;
    public static final Parcelable.Creator<HotwordRejectedResult> CREATOR = new Parcelable.Creator<HotwordRejectedResult>() { // from class: android.service.voice.HotwordRejectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordRejectedResult[] newArray(int i) {
            return new HotwordRejectedResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordRejectedResult createFromParcel(Parcel parcel) {
            return new HotwordRejectedResult(parcel);
        }
    };
    private final int mConfidenceLevel;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfidenceLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface HotwordConfidenceLevelValue {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String confidenceLevelToString(int i) {
        if (i == 0) {
            return "CONFIDENCE_LEVEL_NONE";
        }
        if (i == 1) {
            return "CONFIDENCE_LEVEL_LOW";
        }
        if (i == 2) {
            return "CONFIDENCE_LEVEL_MEDIUM";
        }
        if (i == 3) {
            return "CONFIDENCE_LEVEL_HIGH";
        }
        if (i == 4) {
            return "CONFIDENCE_LEVEL_VERY_HIGH";
        }
        return Integer.toHexString(i);
    }

    HotwordRejectedResult(int i) {
        this.mConfidenceLevel = i;
        AnnotationValidations.validate((Class<? extends Annotation>) HotwordConfidenceLevelValue.class, (Annotation) null, i);
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public String toString() {
        return "HotwordRejectedResult { confidenceLevel = " + this.mConfidenceLevel + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mConfidenceLevel == ((HotwordRejectedResult) obj).mConfidenceLevel;
    }

    public int hashCode() {
        return 31 + this.mConfidenceLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mConfidenceLevel);
    }

    HotwordRejectedResult(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mConfidenceLevel = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) HotwordConfidenceLevelValue.class, (Annotation) null, readInt);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;

        public Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mConfidenceLevel = i;
            return this;
        }

        public HotwordRejectedResult build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 2;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mConfidenceLevel = HotwordRejectedResult.defaultConfidenceLevel();
            }
            return new HotwordRejectedResult(this.mConfidenceLevel);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
