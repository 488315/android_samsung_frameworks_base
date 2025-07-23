package android.app.appfunctions;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AppFunctionException extends Exception implements Parcelable {
    public static final Parcelable.Creator<AppFunctionException> CREATOR = new Parcelable.Creator<AppFunctionException>() { // from class: android.app.appfunctions.AppFunctionException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppFunctionException createFromParcel(Parcel parcel) {
            return new AppFunctionException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppFunctionException[] newArray(int i) {
            return new AppFunctionException[i];
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
    public static final int ERROR_ENTERPRISE_POLICY_DISALLOWED = 2002;
    public static final int ERROR_FUNCTION_NOT_FOUND = 1003;
    public static final int ERROR_INVALID_ARGUMENT = 1001;
    public static final int ERROR_SYSTEM_ERROR = 2000;
    private final int mErrorCode;
    private final String mErrorMessage;
    private final Bundle mExtras;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppFunctionException(int i, String str) {
        this(i, str, Bundle.EMPTY);
    }

    public AppFunctionException(int i, String str, Bundle bundle) {
        super(str);
        this.mErrorCode = i;
        this.mErrorMessage = str;
        this.mExtras = (Bundle) Objects.requireNonNull(bundle);
    }

    private AppFunctionException(Parcel parcel) {
        this.mErrorCode = parcel.readInt();
        this.mErrorMessage = parcel.readString8();
        this.mExtras = (Bundle) Objects.requireNonNull(parcel.readBundle(getClass().getClassLoader()));
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getErrorCategory() {
        int i = this.mErrorCode;
        if (i >= 1000 && i < 2000) {
            return 1;
        }
        if (i < 2000 || i >= 3000) {
            return (i < 3000 || i >= 4000) ? 0 : 3;
        }
        return 2;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mErrorCode);
        parcel.writeString8(this.mErrorMessage);
        parcel.writeBundle(this.mExtras);
    }
}
