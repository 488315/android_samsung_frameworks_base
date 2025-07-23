package android.telecom;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class QueryLocationException extends RuntimeException implements Parcelable {
    public static final Parcelable.Creator<QueryLocationException> CREATOR = new Parcelable.Creator<QueryLocationException>() { // from class: android.telecom.QueryLocationException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QueryLocationException createFromParcel(Parcel parcel) {
            return new QueryLocationException(parcel.readString8(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QueryLocationException[] newArray(int i) {
            return new QueryLocationException[i];
        }
    };
    public static final int ERROR_NOT_ALLOWED_FOR_NON_EMERGENCY_CONNECTIONS = 4;
    public static final int ERROR_NOT_PERMITTED = 3;
    public static final int ERROR_PREVIOUS_REQUEST_EXISTS = 2;
    public static final int ERROR_REQUEST_TIME_OUT = 1;
    public static final int ERROR_SERVICE_UNAVAILABLE = 5;
    public static final int ERROR_UNSPECIFIED = 6;
    public static final String QUERY_LOCATION_ERROR = "QueryLocationErrorKey";
    private int mCode;
    private final String mMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface QueryLocationErrorCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mMessage);
        parcel.writeInt(this.mCode);
    }

    public QueryLocationException(String str) {
        super(getMessage(str, 6));
        this.mCode = 6;
        this.mMessage = str;
    }

    public QueryLocationException(String str, int i) {
        super(getMessage(str, i));
        this.mCode = i;
        this.mMessage = str;
    }

    public QueryLocationException(String str, int i, Throwable th) {
        super(getMessage(str, i), th);
        this.mCode = i;
        this.mMessage = str;
    }

    public int getCode() {
        return this.mCode;
    }

    private static String getMessage(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            return str + " (code: " + i + NavigationBarInflaterView.KEY_CODE_END;
        }
        return "code: " + i;
    }
}
