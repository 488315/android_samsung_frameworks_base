package android.app.appfunctions;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AppFunctionException extends Exception implements Parcelable {
    public static final Parcelable.Creator<AppFunctionException> CREATOR =
            new Parcelable.Creator<AppFunctionException>() { // from class:
                // android.app.appfunctions.AppFunctionException.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AppFunctionException createFromParcel(Parcel in) {
                    return new AppFunctionException(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AppFunctionException[] newArray(int size) {
                    return new AppFunctionException[size];
                }
            };
    public static final int ERROR_APP_UNKNOWN_ERROR = 3000;
    public static final int ERROR_CANCELLED = 2001;
    public static final int ERROR_CATEGORY_APP = 3;
    public static final int ERROR_CATEGORY_REQUEST_ERROR = 1;
    public static final int ERROR_CATEGORY_SYSTEM = 2;
    public static final int ERROR_CATEGORY_UNKNOWN = 0;
    public static final int ERROR_DENIED = 1000;
    public static final int ERROR_DISABLED = 1002;
    public static final int ERROR_FUNCTION_NOT_FOUND = 1003;
    public static final int ERROR_INVALID_ARGUMENT = 1001;
    public static final int ERROR_SYSTEM_ERROR = 2000;
    private final int mErrorCode;
    private final String mErrorMessage;
    private final Bundle mExtras;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCategory {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {}

    public AppFunctionException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, Bundle.EMPTY);
    }

    public AppFunctionException(int errorCode, String errorMessage, Bundle extras) {
        super(errorMessage);
        this.mErrorCode = errorCode;
        this.mErrorMessage = errorMessage;
        this.mExtras = (Bundle) Objects.requireNonNull(extras);
    }

    private AppFunctionException(Parcel in) {
        this.mErrorCode = in.readInt();
        this.mErrorMessage = in.readString8();
        this.mExtras = (Bundle) Objects.requireNonNull(in.readBundle(getClass().getClassLoader()));
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getErrorCategory() {
        if (this.mErrorCode >= 1000 && this.mErrorCode < 2000) {
            return 1;
        }
        if (this.mErrorCode >= 2000 && this.mErrorCode < 3000) {
            return 2;
        }
        if (this.mErrorCode >= 3000 && this.mErrorCode < 4000) {
            return 3;
        }
        return 0;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mErrorCode);
        dest.writeString8(this.mErrorMessage);
        dest.writeBundle(this.mExtras);
    }
}
