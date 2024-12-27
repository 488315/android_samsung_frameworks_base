package android.service.voice;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class VisualQueryDetectionServiceFailure implements Parcelable {
    public static final Parcelable.Creator<VisualQueryDetectionServiceFailure> CREATOR =
            new Parcelable.Creator<
                    VisualQueryDetectionServiceFailure>() { // from class:
                                                            // android.service.voice.VisualQueryDetectionServiceFailure.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VisualQueryDetectionServiceFailure[] newArray(int size) {
                    return new VisualQueryDetectionServiceFailure[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VisualQueryDetectionServiceFailure createFromParcel(Parcel in) {
                    return new VisualQueryDetectionServiceFailure(in.readInt(), in.readString8());
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
    public @interface VisualQueryDetectionServiceErrorCode {}

    public VisualQueryDetectionServiceFailure(int errorCode, String errorMessage) {
        this.mErrorCode = 0;
        this.mErrorMessage = "Unknown";
        if (TextUtils.isEmpty(errorMessage)) {
            throw new IllegalArgumentException("errorMessage is empty or null.");
        }
        this.mErrorCode = errorCode;
        this.mErrorMessage = errorMessage;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getSuggestedAction() {
        switch (this.mErrorCode) {
            case 1:
            case 2:
            case 3:
            case 5:
                return 3;
            case 4:
                return 4;
            default:
                return 1;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mErrorCode);
        dest.writeString8(this.mErrorMessage);
    }

    public String toString() {
        return "VisualQueryDetectionServiceFailure { errorCode = "
                + this.mErrorCode
                + ", errorMessage = "
                + this.mErrorMessage
                + " }";
    }
}
