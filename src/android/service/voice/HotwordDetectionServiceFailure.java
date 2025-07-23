package android.service.voice;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.samsung.android.lock.LsConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class HotwordDetectionServiceFailure implements Parcelable {
    public static final Parcelable.Creator<HotwordDetectionServiceFailure> CREATOR = new Parcelable.Creator<HotwordDetectionServiceFailure>() { // from class: android.service.voice.HotwordDetectionServiceFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectionServiceFailure[] newArray(int i) {
            return new HotwordDetectionServiceFailure[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordDetectionServiceFailure createFromParcel(Parcel parcel) {
            return new HotwordDetectionServiceFailure(parcel.readInt(), parcel.readString8());
        }
    };
    public static final int ERROR_CODE_BINDING_DIED = 2;
    public static final int ERROR_CODE_BIND_FAILURE = 1;
    public static final int ERROR_CODE_COPY_AUDIO_DATA_FAILURE = 3;
    public static final int ERROR_CODE_DETECT_TIMEOUT = 4;
    public static final int ERROR_CODE_ON_DETECTED_SECURITY_EXCEPTION = 5;
    public static final int ERROR_CODE_ON_DETECTED_STREAM_COPY_FAILURE = 6;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 7;
    public static final int ERROR_CODE_SHUTDOWN_HDS_ON_VOICE_ACTIVATION_OP_DISABLED = 10;
    public static final int ERROR_CODE_UNKNOWN = 0;
    private int mErrorCode;
    private String mErrorMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HotwordDetectionServiceErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public HotwordDetectionServiceFailure(int i, String str) {
        this.mErrorCode = 0;
        this.mErrorMessage = LsConstants.TAG_UNKNOWN;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("errorMessage is empty or null.");
        }
        this.mErrorCode = i;
        this.mErrorMessage = str;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getSuggestedAction() {
        int i = this.mErrorCode;
        if (i == 1 || i == 2) {
            return 3;
        }
        if (i == 4 || i == 5 || i == 6) {
            return 4;
        }
        return i != 7 ? 1 : 3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mErrorCode);
        parcel.writeString8(this.mErrorMessage);
    }

    public String toString() {
        return "HotwordDetectionServiceFailure { errorCode = " + this.mErrorCode + ", errorMessage = " + this.mErrorMessage + " }";
    }
}
