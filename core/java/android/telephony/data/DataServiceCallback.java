package android.telephony.data;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;

import com.android.telephony.Rlog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SystemApi
public class DataServiceCallback {
    private static final boolean DBG = true;
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_TEMPORARILY_UNAVAILABLE = 5;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = DataServiceCallback.class.getSimpleName();
    private final IDataServiceCallback mCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {}

    public DataServiceCallback(IDataServiceCallback callback) {
        this.mCallback = callback;
    }

    public void onSetupDataCallComplete(int result, DataCallResponse response) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onSetupDataCallComplete");
                this.mCallback.onSetupDataCallComplete(result, response);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onSetupDataCallComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onSetupDataCallComplete: callback is null!");
    }

    public void onDeactivateDataCallComplete(int result) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onDeactivateDataCallComplete");
                this.mCallback.onDeactivateDataCallComplete(result);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onDeactivateDataCallComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onDeactivateDataCallComplete: callback is null!");
    }

    public void onSetInitialAttachApnComplete(int result) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onSetInitialAttachApnComplete(result);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onSetInitialAttachApnComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onSetInitialAttachApnComplete: callback is null!");
    }

    public void onSetDataProfileComplete(int result) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onSetDataProfileComplete(result);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onSetDataProfileComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onSetDataProfileComplete: callback is null!");
    }

    public void onRequestDataCallListComplete(int result, List<DataCallResponse> dataCallList) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onRequestDataCallListComplete(result, dataCallList);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onRequestDataCallListComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onRequestDataCallListComplete: callback is null!");
    }

    public void onDataCallListChanged(List<DataCallResponse> dataCallList) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onDataCallListChanged");
                this.mCallback.onDataCallListChanged(dataCallList);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onDataCallListChanged on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onDataCallListChanged: callback is null!");
    }

    public void onHandoverStarted(int result) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onHandoverStarted");
                this.mCallback.onHandoverStarted(result);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onHandoverStarted on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onHandoverStarted: callback is null!");
    }

    public void onHandoverCancelled(int result) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onHandoverCancelled");
                this.mCallback.onHandoverCancelled(result);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "Failed to onHandoverCancelled on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onHandoverCancelled: callback is null!");
    }

    public static String resultCodeToString(int resultCode) {
        switch (resultCode) {
            case 0:
                return "RESULT_SUCCESS";
            case 1:
                return "RESULT_ERROR_UNSUPPORTED";
            case 2:
                return "RESULT_ERROR_INVALID_ARG";
            case 3:
                return "RESULT_ERROR_BUSY";
            case 4:
                return "RESULT_ERROR_ILLEGAL_STATE";
            case 5:
                return "RESULT_ERROR_TEMPORARILY_UNAVAILABLE";
            default:
                return "Unknown(" + resultCode + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void onApnUnthrottled(String apn) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onApnUnthrottled");
                this.mCallback.onApnUnthrottled(apn);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "onApnUnthrottled: remote exception", e);
                return;
            }
        }
        Rlog.e(TAG, "onApnUnthrottled: callback is null!");
    }

    public void onDataProfileUnthrottled(DataProfile dataProfile) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onDataProfileUnthrottled");
                this.mCallback.onDataProfileUnthrottled(dataProfile);
                return;
            } catch (RemoteException e) {
                Rlog.e(TAG, "onDataProfileUnthrottled: remote exception", e);
                return;
            }
        }
        Rlog.e(TAG, "onDataProfileUnthrottled: callback is null!");
    }
}
