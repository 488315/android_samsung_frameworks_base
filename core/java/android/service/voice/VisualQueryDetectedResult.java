package android.service.voice;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;

import java.util.Arrays;
import java.util.Objects;

@SystemApi
public final class VisualQueryDetectedResult implements Parcelable {
    public static final Parcelable.Creator<VisualQueryDetectedResult> CREATOR =
            new Parcelable.Creator<
                    VisualQueryDetectedResult>() { // from class:
                                                   // android.service.voice.VisualQueryDetectedResult.1
                @Override // android.os.Parcelable.Creator
                public VisualQueryDetectedResult[] newArray(int size) {
                    return new VisualQueryDetectedResult[size];
                }

                @Override // android.os.Parcelable.Creator
                public VisualQueryDetectedResult createFromParcel(Parcel in) {
                    return new VisualQueryDetectedResult(in);
                }
            };
    private final byte[] mAccessibilityDetectionData;
    private final String mPartialQuery;
    private final int mSpeakerId;

    public static String defaultPartialQuery() {
        return "";
    }

    public static int defaultSpeakerId() {
        return 0;
    }

    public static int getMaxSpeakerId() {
        return 15;
    }

    public static byte[] defaultAccessibilityDetectionData() {
        return null;
    }

    private void onConstructed() {
        Preconditions.checkArgumentInRange(this.mSpeakerId, 0, getMaxSpeakerId(), "speakerId");
    }

    public Builder buildUpon() {
        return new Builder()
                .setPartialQuery(this.mPartialQuery)
                .setSpeakerId(this.mSpeakerId)
                .setAccessibilityDetectionData(this.mAccessibilityDetectionData);
    }

    VisualQueryDetectedResult(
            String partialQuery, int speakerId, byte[] accessibilityDetectionData) {
        this.mPartialQuery = partialQuery;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPartialQuery);
        this.mSpeakerId = speakerId;
        this.mAccessibilityDetectionData = accessibilityDetectionData;
        onConstructed();
    }

    public String getPartialQuery() {
        return this.mPartialQuery;
    }

    public int getSpeakerId() {
        return this.mSpeakerId;
    }

    public byte[] getAccessibilityDetectionData() {
        return this.mAccessibilityDetectionData;
    }

    public String toString() {
        return "VisualQueryDetectedResult { partialQuery = "
                + this.mPartialQuery
                + ", speakerId = "
                + this.mSpeakerId
                + ", accessibilityDetectionData = "
                + Arrays.toString(this.mAccessibilityDetectionData)
                + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisualQueryDetectedResult that = (VisualQueryDetectedResult) o;
        if (Objects.equals(this.mPartialQuery, that.mPartialQuery)
                && this.mSpeakerId == that.mSpeakerId
                && Arrays.equals(
                        this.mAccessibilityDetectionData, that.mAccessibilityDetectionData)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mPartialQuery);
        return (((_hash * 31) + this.mSpeakerId) * 31)
                + Arrays.hashCode(this.mAccessibilityDetectionData);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPartialQuery);
        dest.writeInt(this.mSpeakerId);
        dest.writeByteArray(this.mAccessibilityDetectionData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisualQueryDetectedResult(Parcel in) {
        String partialQuery = in.readString();
        int speakerId = in.readInt();
        byte[] accessibilityDetectionData = in.createByteArray();
        this.mPartialQuery = partialQuery;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPartialQuery);
        this.mSpeakerId = speakerId;
        this.mAccessibilityDetectionData = accessibilityDetectionData;
        onConstructed();
    }

    public static final class Builder {
        private byte[] mAccessibilityDetectionData;
        private long mBuilderFieldsSet = 0;
        private String mPartialQuery;
        private int mSpeakerId;

        public Builder setPartialQuery(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mPartialQuery = value;
            return this;
        }

        public Builder setSpeakerId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSpeakerId = value;
            return this;
        }

        public Builder setAccessibilityDetectionData(byte... value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAccessibilityDetectionData = value;
            return this;
        }

        public VisualQueryDetectedResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mPartialQuery = VisualQueryDetectedResult.defaultPartialQuery();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSpeakerId = VisualQueryDetectedResult.defaultSpeakerId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mAccessibilityDetectionData =
                        VisualQueryDetectedResult.defaultAccessibilityDetectionData();
            }
            VisualQueryDetectedResult o =
                    new VisualQueryDetectedResult(
                            this.mPartialQuery, this.mSpeakerId, this.mAccessibilityDetectionData);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
