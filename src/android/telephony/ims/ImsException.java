package android.telephony.ims;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class ImsException extends Exception {
    public static final int CODE_ERROR_INVALID_SUBSCRIPTION = 3;
    public static final int CODE_ERROR_SERVICE_UNAVAILABLE = 1;
    public static final int CODE_ERROR_UNSPECIFIED = 0;
    public static final int CODE_ERROR_UNSUPPORTED_OPERATION = 2;
    private int mCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsErrorCode {
    }

    @SystemApi
    public ImsException(String str) {
        super(getMessage(str, 0));
        this.mCode = 0;
    }

    @SystemApi
    public ImsException(String str, int i) {
        super(getMessage(str, i));
        this.mCode = i;
    }

    @SystemApi
    public ImsException(String str, int i, Throwable th) {
        super(getMessage(str, i), th);
        this.mCode = i;
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
