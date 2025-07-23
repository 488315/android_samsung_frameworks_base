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
public final class VisualQueryDetectionServiceFailure implements Parcelable {
    public static final Parcelable.Creator<VisualQueryDetectionServiceFailure> CREATOR = new Parcelable.Creator<VisualQueryDetectionServiceFailure>() { // from class: android.service.voice.VisualQueryDetectionServiceFailure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualQueryDetectionServiceFailure[] newArray(int i) {
            return new VisualQueryDetectionServiceFailure[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualQueryDetectionServiceFailure createFromParcel(Parcel parcel) {
            return new VisualQueryDetectionServiceFailure(parcel.readInt(), parcel.readString8());
        }
    };
    public static final int ERROR_CODE_BINDING_DIED = 2;
    public static final int ERROR_CODE_BIND_FAILURE = 1;
    public static final int ERROR_CODE_ILLEGAL_ATTENTION_STATE = 3;
    public static final int ERROR_CODE_ILLEGAL_STREAMING_STATE = 4;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 5;
    public static final int ERROR_CODE_UNKNOWN = 0;
    private int mErrorCode;
    private String mErrorMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VisualQueryDetectionServiceErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VisualQueryDetectionServiceFailure(int i, String str) {
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
        if (i != 1 && i != 2 && i != 3) {
            if (i == 4) {
                return 4;
            }
            if (i != 5) {
                return 1;
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
        return "VisualQueryDetectionServiceFailure { errorCode = " + this.mErrorCode + ", errorMessage = " + this.mErrorMessage + " }";
    }
}
