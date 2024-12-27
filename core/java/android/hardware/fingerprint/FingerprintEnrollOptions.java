package android.hardware.fingerprint;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public class FingerprintEnrollOptions implements Parcelable {
    public static final Parcelable.Creator<FingerprintEnrollOptions> CREATOR =
            new Parcelable.Creator<FingerprintEnrollOptions>() { // from class:
                // android.hardware.fingerprint.FingerprintEnrollOptions.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FingerprintEnrollOptions[] newArray(int size) {
                    return new FingerprintEnrollOptions[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FingerprintEnrollOptions createFromParcel(Parcel in) {
                    return new FingerprintEnrollOptions(in);
                }
            };
    public static final int ENROLL_REASON_RE_ENROLL_NOTIFICATION = 1;
    public static final int ENROLL_REASON_SETTINGS = 2;
    public static final int ENROLL_REASON_SUW = 3;
    public static final int ENROLL_REASON_UNKNOWN = 0;
    private final int mEnrollReason;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnrollReason {}

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEnrollReason() {
        return 0;
    }

    public static String enrollReasonToString(int value) {
        switch (value) {
            case 0:
                return "ENROLL_REASON_UNKNOWN";
            case 1:
                return "ENROLL_REASON_RE_ENROLL_NOTIFICATION";
            case 2:
                return "ENROLL_REASON_SETTINGS";
            case 3:
                return "ENROLL_REASON_SUW";
            default:
                return Integer.toHexString(value);
        }
    }

    FingerprintEnrollOptions(int enrollReason) {
        this.mEnrollReason = enrollReason;
        if (this.mEnrollReason != 0
                && this.mEnrollReason != 1
                && this.mEnrollReason != 2
                && this.mEnrollReason != 3) {
            throw new IllegalArgumentException(
                    "enrollReason was "
                            + this.mEnrollReason
                            + " but must be one of: ENROLL_REASON_UNKNOWN(0),"
                            + " ENROLL_REASON_RE_ENROLL_NOTIFICATION(1), ENROLL_REASON_SETTINGS(2),"
                            + " ENROLL_REASON_SUW(3"
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public int getEnrollReason() {
        return this.mEnrollReason;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FingerprintEnrollOptions that = (FingerprintEnrollOptions) o;
        if (this.mEnrollReason == that.mEnrollReason) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mEnrollReason;
        return _hash;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mEnrollReason);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected FingerprintEnrollOptions(Parcel in) {
        int enrollReason = in.readInt();
        this.mEnrollReason = enrollReason;
        if (this.mEnrollReason != 0
                && this.mEnrollReason != 1
                && this.mEnrollReason != 2
                && this.mEnrollReason != 3) {
            throw new IllegalArgumentException(
                    "enrollReason was "
                            + this.mEnrollReason
                            + " but must be one of: ENROLL_REASON_UNKNOWN(0),"
                            + " ENROLL_REASON_RE_ENROLL_NOTIFICATION(1), ENROLL_REASON_SETTINGS(2),"
                            + " ENROLL_REASON_SUW(3"
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public static class Builder {
        private long mBuilderFieldsSet = 0;
        private int mEnrollReason;

        public Builder setEnrollReason(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mEnrollReason = value;
            return this;
        }

        public FingerprintEnrollOptions build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mEnrollReason = FingerprintEnrollOptions.defaultEnrollReason();
            }
            FingerprintEnrollOptions o = new FingerprintEnrollOptions(this.mEnrollReason);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
