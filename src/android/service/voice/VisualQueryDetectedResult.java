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
/* loaded from: classes3.dex */
public final class VisualQueryDetectedResult implements Parcelable {
    public static final Parcelable.Creator<VisualQueryDetectedResult> CREATOR = new Parcelable.Creator<VisualQueryDetectedResult>() { // from class: android.service.voice.VisualQueryDetectedResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualQueryDetectedResult[] newArray(int i) {
            return new VisualQueryDetectedResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualQueryDetectedResult createFromParcel(Parcel parcel) {
            return new VisualQueryDetectedResult(parcel);
        }
    };
    private final byte[] mAccessibilityDetectionData;
    private final String mPartialQuery;
    private final int mSpeakerId;

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] defaultAccessibilityDetectionData() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSpeakerId() {
        return 0;
    }

    public static int getMaxSpeakerId() {
        return 15;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultPartialQuery() {
        return "";
    }

    private void onConstructed() {
        Preconditions.checkArgumentInRange(this.mSpeakerId, 0, getMaxSpeakerId(), "speakerId");
    }

    public Builder buildUpon() {
        return new Builder().setPartialQuery(this.mPartialQuery).setSpeakerId(this.mSpeakerId).setAccessibilityDetectionData(this.mAccessibilityDetectionData);
    }

    VisualQueryDetectedResult(String str, int i, byte[] bArr) {
        this.mPartialQuery = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mSpeakerId = i;
        this.mAccessibilityDetectionData = bArr;
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
        return "VisualQueryDetectedResult { partialQuery = " + this.mPartialQuery + ", speakerId = " + this.mSpeakerId + ", accessibilityDetectionData = " + Arrays.toString(this.mAccessibilityDetectionData) + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VisualQueryDetectedResult visualQueryDetectedResult = (VisualQueryDetectedResult) obj;
            if (Objects.equals(this.mPartialQuery, visualQueryDetectedResult.mPartialQuery) && this.mSpeakerId == visualQueryDetectedResult.mSpeakerId && Arrays.equals(this.mAccessibilityDetectionData, visualQueryDetectedResult.mAccessibilityDetectionData)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mPartialQuery) + 31) * 31) + this.mSpeakerId) * 31) + Arrays.hashCode(this.mAccessibilityDetectionData);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPartialQuery);
        parcel.writeInt(this.mSpeakerId);
        parcel.writeByteArray(this.mAccessibilityDetectionData);
    }

    VisualQueryDetectedResult(Parcel parcel) {
        String string = parcel.readString();
        int i = parcel.readInt();
        byte[] bArrCreateByteArray = parcel.createByteArray();
        this.mPartialQuery = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mSpeakerId = i;
        this.mAccessibilityDetectionData = bArrCreateByteArray;
        onConstructed();
    }

    public static final class Builder {
        private byte[] mAccessibilityDetectionData;
        private long mBuilderFieldsSet = 0;
        private String mPartialQuery;
        private int mSpeakerId;

        public Builder setPartialQuery(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mPartialQuery = str;
            return this;
        }

        public Builder setSpeakerId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSpeakerId = i;
            return this;
        }

        public Builder setAccessibilityDetectionData(byte... bArr) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAccessibilityDetectionData = bArr;
            return this;
        }

        public VisualQueryDetectedResult build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 8;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mPartialQuery = VisualQueryDetectedResult.defaultPartialQuery();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSpeakerId = VisualQueryDetectedResult.defaultSpeakerId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mAccessibilityDetectionData = VisualQueryDetectedResult.defaultAccessibilityDetectionData();
            }
            return new VisualQueryDetectedResult(this.mPartialQuery, this.mSpeakerId, this.mAccessibilityDetectionData);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
