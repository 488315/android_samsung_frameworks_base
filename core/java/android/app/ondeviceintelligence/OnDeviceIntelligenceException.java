package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.PersistableBundle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@SystemApi
/* loaded from: classes.dex */
public class OnDeviceIntelligenceException extends Exception {
    public static final int ON_DEVICE_INTELLIGENCE_SERVICE_UNAVAILABLE = 100;
    public static final int PROCESSING_ERROR_BAD_DATA = 2;
    public static final int PROCESSING_ERROR_BAD_REQUEST = 3;
    public static final int PROCESSING_ERROR_BUSY = 9;
    public static final int PROCESSING_ERROR_CANCELLED = 7;
    public static final int PROCESSING_ERROR_COMPUTE_ERROR = 5;
    public static final int PROCESSING_ERROR_INTERNAL = 14;
    public static final int PROCESSING_ERROR_IPC_ERROR = 6;
    public static final int PROCESSING_ERROR_NOT_AVAILABLE = 8;
    public static final int PROCESSING_ERROR_REQUEST_NOT_SAFE = 4;
    public static final int PROCESSING_ERROR_REQUEST_TOO_LARGE = 12;
    public static final int PROCESSING_ERROR_RESPONSE_NOT_SAFE = 11;
    public static final int PROCESSING_ERROR_SAFETY_ERROR = 10;
    public static final int PROCESSING_ERROR_SERVICE_UNAVAILABLE = 15;
    public static final int PROCESSING_ERROR_SUSPENDED = 13;
    public static final int PROCESSING_ERROR_UNKNOWN = 1;
    public static final int PROCESSING_UPDATE_STATUS_CONNECTION_FAILED = 200;
    private final int mErrorCode;
    private final PersistableBundle mErrorParams;

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @interface OnDeviceIntelligenceError {}

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public PersistableBundle getErrorParams() {
        return this.mErrorParams;
    }

    public OnDeviceIntelligenceException(
            int errorCode, String errorMessage, PersistableBundle errorParams) {
        super(errorMessage);
        this.mErrorCode = errorCode;
        this.mErrorParams = errorParams;
    }

    public OnDeviceIntelligenceException(int errorCode, PersistableBundle errorParams) {
        this.mErrorCode = errorCode;
        this.mErrorParams = errorParams;
    }

    public OnDeviceIntelligenceException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.mErrorCode = errorCode;
        this.mErrorParams = new PersistableBundle();
    }

    public OnDeviceIntelligenceException(int errorCode) {
        this.mErrorCode = errorCode;
        this.mErrorParams = new PersistableBundle();
    }
}
