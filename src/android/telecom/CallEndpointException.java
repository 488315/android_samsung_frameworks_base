package android.telecom;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class CallEndpointException extends RuntimeException implements Parcelable {
    public static final String CHANGE_ERROR = "ChangeErrorKey";
    public static final Parcelable.Creator<CallEndpointException> CREATOR = new Parcelable.Creator<CallEndpointException>() { // from class: android.telecom.CallEndpointException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallEndpointException createFromParcel(Parcel parcel) {
            return new CallEndpointException(parcel.readString8(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallEndpointException[] newArray(int i) {
            return new CallEndpointException[i];
        }
    };
    public static final int ERROR_ANOTHER_REQUEST = 3;
    public static final int ERROR_ENDPOINT_DOES_NOT_EXIST = 1;
    public static final int ERROR_REQUEST_TIME_OUT = 2;
    public static final int ERROR_UNSPECIFIED = 4;
    private int mCode;
    private final String mMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallEndpointErrorCode {
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

    public CallEndpointException(String str, int i) {
        super(getMessage(str, i));
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
