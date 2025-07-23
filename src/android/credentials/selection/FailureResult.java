package android.credentials.selection;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class FailureResult {
    public static final int ERROR_CODE_CANCELED_AND_LAUNCHED_SETTINGS = 2;
    public static final int ERROR_CODE_DIALOG_CANCELED_BY_USER = 1;
    public static final int ERROR_CODE_UI_FAILURE = 0;
    private final int mErrorCode;
    private final String mErrorMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    public static void sendFailureResult(ResultReceiver resultReceiver, FailureResult failureResult) {
        FailureDialogResult failureDialogResult = failureResult.toFailureDialogResult();
        Bundle bundle = new Bundle();
        FailureDialogResult.addToBundle(failureDialogResult, bundle);
        resultReceiver.send(failureResult.errorCodeToResultCode(), bundle);
    }

    public FailureResult(int i, String str) {
        this.mErrorCode = i;
        this.mErrorMessage = str;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    FailureDialogResult toFailureDialogResult() {
        return new FailureDialogResult((IBinder) null, this.mErrorMessage);
    }

    int errorCodeToResultCode() {
        int i = this.mErrorCode;
        if (i != 1) {
            return i != 2 ? 3 : 1;
        }
        return 0;
    }
}
