package android.telecom;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class CallException extends RuntimeException implements Parcelable {
    public static final int CODE_CALL_CANNOT_BE_SET_TO_ACTIVE = 4;
    public static final int CODE_CALL_IS_NOT_BEING_TRACKED = 3;
    public static final int CODE_CALL_NOT_PERMITTED_AT_PRESENT_TIME = 5;
    public static final int CODE_CANNOT_HOLD_CURRENT_ACTIVE_CALL = 2;
    public static final int CODE_ERROR_UNKNOWN = 1;
    public static final int CODE_OPERATION_TIMED_OUT = 6;
    public static final Parcelable.Creator<CallException> CREATOR = new Parcelable.Creator<CallException>() { // from class: android.telecom.CallException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallException createFromParcel(Parcel parcel) {
            return new CallException(parcel.readString8(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallException[] newArray(int i) {
            return new CallException[i];
        }
    };
    public static final String TRANSACTION_EXCEPTION_KEY = "TelecomTransactionalExceptionKey";
    private int mCode;
    private final String mMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallErrorCode {
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

    public CallException(String str, int i) {
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
