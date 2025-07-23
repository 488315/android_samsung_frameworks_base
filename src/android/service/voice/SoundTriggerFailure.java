package android.service.voice;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class SoundTriggerFailure implements Parcelable {
    public static final Parcelable.Creator<SoundTriggerFailure> CREATOR = new Parcelable.Creator<SoundTriggerFailure>() { // from class: android.service.voice.SoundTriggerFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerFailure[] newArray(int i) {
            return new SoundTriggerFailure[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerFailure createFromParcel(Parcel parcel) {
            return new SoundTriggerFailure(parcel.readInt(), parcel.readString8());
        }
    };
    public static final int ERROR_CODE_MODULE_DIED = 1;
    public static final int ERROR_CODE_RECOGNITION_RESUME_FAILED = 2;
    public static final int ERROR_CODE_UNEXPECTED_PREEMPTION = 3;
    public static final int ERROR_CODE_UNKNOWN = 0;
    private final int mErrorCode;
    private final String mErrorMessage;
    private final int mSuggestedAction;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SoundTriggerErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SoundTriggerFailure(int i, String str) {
        this(i, str, getSuggestedActionBasedOnErrorCode(i));
    }

    public SoundTriggerFailure(int i, String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("errorMessage is empty or null.");
        }
        if (i == 0 || i == 1 || i == 2 || i == 3) {
            this.mErrorCode = i;
            if (i2 != getSuggestedActionBasedOnErrorCode(i) && i != 0) {
                throw new IllegalArgumentException("Invalid suggested next action: errorCode=" + i + ", suggestedAction=" + i2);
            }
            this.mErrorMessage = str;
            this.mSuggestedAction = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid ErrorCode: " + i);
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getSuggestedAction() {
        return this.mSuggestedAction;
    }

    private static int getSuggestedActionBasedOnErrorCode(int i) {
        if (i != 0 && i != 1) {
            if (i == 2) {
                return 4;
            }
            if (i != 3) {
                throw new AssertionError("Unexpected error code");
            }
        }
        return 3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mErrorCode);
        parcel.writeString8(this.mErrorMessage);
    }

    public String toString() {
        return "SoundTriggerFailure { errorCode = " + this.mErrorCode + ", errorMessage = " + this.mErrorMessage + ", suggestedNextAction = " + this.mSuggestedAction + " }";
    }
}
