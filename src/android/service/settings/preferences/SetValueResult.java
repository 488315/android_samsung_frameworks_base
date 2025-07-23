package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class SetValueResult implements Parcelable {
    public static final Parcelable.Creator<SetValueResult> CREATOR = new Parcelable.Creator<SetValueResult>() { // from class: android.service.settings.preferences.SetValueResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetValueResult createFromParcel(Parcel parcel) {
            return new SetValueResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetValueResult[] newArray(int i) {
            return new SetValueResult[i];
        }
    };
    public static final int RESULT_DISABLED = 2;
    public static final int RESULT_DISALLOW = 7;
    public static final int RESULT_INTERNAL_ERROR = 9;
    public static final int RESULT_INVALID_REQUEST = 8;
    public static final int RESULT_OK = 0;
    public static final int RESULT_REQUIRE_APP_PERMISSION = 5;
    public static final int RESULT_REQUIRE_USER_CONSENT = 6;
    public static final int RESULT_RESTRICTED = 3;
    public static final int RESULT_UNAVAILABLE = 4;
    public static final int RESULT_UNSUPPORTED = 1;
    private final int mResultCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    private SetValueResult(Builder builder) {
        this.mResultCode = builder.mResultCode;
    }

    private SetValueResult(Parcel parcel) {
        this.mResultCode = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
    }

    public static final class Builder {
        private final int mResultCode;

        public Builder(int i) {
            this.mResultCode = i;
        }

        public SetValueResult build() {
            return new SetValueResult(this);
        }
    }
}
