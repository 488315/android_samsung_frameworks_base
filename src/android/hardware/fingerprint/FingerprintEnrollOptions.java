package android.hardware.fingerprint;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class FingerprintEnrollOptions implements Parcelable {
    public static final Parcelable.Creator<FingerprintEnrollOptions> CREATOR = new Parcelable.Creator<FingerprintEnrollOptions>() { // from class: android.hardware.fingerprint.FingerprintEnrollOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintEnrollOptions[] newArray(int i) {
            return new FingerprintEnrollOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintEnrollOptions createFromParcel(Parcel parcel) {
            return new FingerprintEnrollOptions(parcel);
        }
    };
    public static final int ENROLL_REASON_RE_ENROLL_NOTIFICATION = 1;
    public static final int ENROLL_REASON_SETTINGS = 2;
    public static final int ENROLL_REASON_SUW = 3;
    public static final int ENROLL_REASON_UNKNOWN = 0;
    private final int mEnrollReason;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnrollReason {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEnrollReason() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String enrollReasonToString(int i) {
        if (i == 0) {
            return "ENROLL_REASON_UNKNOWN";
        }
        if (i == 1) {
            return "ENROLL_REASON_RE_ENROLL_NOTIFICATION";
        }
        if (i == 2) {
            return "ENROLL_REASON_SETTINGS";
        }
        if (i == 3) {
            return "ENROLL_REASON_SUW";
        }
        return Integer.toHexString(i);
    }

    FingerprintEnrollOptions(int i) {
        this.mEnrollReason = i;
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            return;
        }
        throw new IllegalArgumentException("enrollReason was " + i + " but must be one of: ENROLL_REASON_UNKNOWN(0), ENROLL_REASON_RE_ENROLL_NOTIFICATION(1), ENROLL_REASON_SETTINGS(2), ENROLL_REASON_SUW(3)");
    }

    public int getEnrollReason() {
        return this.mEnrollReason;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mEnrollReason == ((FingerprintEnrollOptions) obj).mEnrollReason;
    }

    public int hashCode() {
        return 31 + this.mEnrollReason;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mEnrollReason);
    }

    protected FingerprintEnrollOptions(Parcel parcel) {
        int i = parcel.readInt();
        this.mEnrollReason = i;
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            return;
        }
        throw new IllegalArgumentException("enrollReason was " + i + " but must be one of: ENROLL_REASON_UNKNOWN(0), ENROLL_REASON_RE_ENROLL_NOTIFICATION(1), ENROLL_REASON_SETTINGS(2), ENROLL_REASON_SUW(3)");
    }

    public static class Builder {
        private long mBuilderFieldsSet = 0;
        private int mEnrollReason;

        public Builder setEnrollReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mEnrollReason = i;
            return this;
        }

        public FingerprintEnrollOptions build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 2;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mEnrollReason = FingerprintEnrollOptions.defaultEnrollReason();
            }
            return new FingerprintEnrollOptions(this.mEnrollReason);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
