package android.credentials.selection;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class FailureResult {
    public static final int ERROR_CODE_CANCELED_AND_LAUNCHED_SETTINGS = 2;
    public static final int ERROR_CODE_DIALOG_CANCELED_BY_USER = 1;
    public static final int ERROR_CODE_UI_FAILURE = 0;
    private final int mErrorCode;
    private final String mErrorMessage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {}

    public static void sendFailureResult(
            ResultReceiver resultReceiver, FailureResult failureResult) {
        FailureDialogResult result = failureResult.toFailureDialogResult();
        Bundle resultData = new Bundle();
        FailureDialogResult.addToBundle(result, resultData);
        resultReceiver.send(failureResult.errorCodeToResultCode(), resultData);
    }

    public FailureResult(int errorCode, String errorMessage) {
        this.mErrorCode = errorCode;
        this.mErrorMessage = errorMessage;
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
        switch (this.mErrorCode) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                return 3;
        }
    }
}
