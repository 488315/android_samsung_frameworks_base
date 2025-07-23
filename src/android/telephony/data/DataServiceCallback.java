package android.telephony.data;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import com.android.telephony.Rlog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public class DataServiceCallback {
    private static final boolean DBG = true;
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_TEMPORARILY_UNAVAILABLE = 5;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "DataServiceCallback";
    private final IDataServiceCallback mCallback;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public DataServiceCallback(IDataServiceCallback iDataServiceCallback) {
        this.mCallback = iDataServiceCallback;
    }

    public void onSetupDataCallComplete(int i, DataCallResponse dataCallResponse) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onSetupDataCallComplete");
                this.mCallback.onSetupDataCallComplete(i, dataCallResponse);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onSetupDataCallComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onSetupDataCallComplete: callback is null!");
    }

    public void onDeactivateDataCallComplete(int i) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onDeactivateDataCallComplete");
                this.mCallback.onDeactivateDataCallComplete(i);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onDeactivateDataCallComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onDeactivateDataCallComplete: callback is null!");
    }

    public void onSetInitialAttachApnComplete(int i) {
        IDataServiceCallback iDataServiceCallback = this.mCallback;
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onSetInitialAttachApnComplete(i);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onSetInitialAttachApnComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onSetInitialAttachApnComplete: callback is null!");
    }

    public void onSetDataProfileComplete(int i) {
        IDataServiceCallback iDataServiceCallback = this.mCallback;
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onSetDataProfileComplete(i);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onSetDataProfileComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onSetDataProfileComplete: callback is null!");
    }

    public void onRequestDataCallListComplete(int i, List<DataCallResponse> list) {
        IDataServiceCallback iDataServiceCallback = this.mCallback;
        if (iDataServiceCallback != null) {
            try {
                iDataServiceCallback.onRequestDataCallListComplete(i, list);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onRequestDataCallListComplete on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onRequestDataCallListComplete: callback is null!");
    }

    public void onDataCallListChanged(List<DataCallResponse> list) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onDataCallListChanged");
                this.mCallback.onDataCallListChanged(list);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onDataCallListChanged on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onDataCallListChanged: callback is null!");
    }

    public void onHandoverStarted(int i) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onHandoverStarted");
                this.mCallback.onHandoverStarted(i);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onHandoverStarted on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onHandoverStarted: callback is null!");
    }

    public void onHandoverCancelled(int i) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onHandoverCancelled");
                this.mCallback.onHandoverCancelled(i);
                return;
            } catch (RemoteException unused) {
                Rlog.e(TAG, "Failed to onHandoverCancelled on the remote");
                return;
            }
        }
        Rlog.e(TAG, "onHandoverCancelled: callback is null!");
    }

    public static String resultCodeToString(int i) {
        if (i == 0) {
            return "RESULT_SUCCESS";
        }
        if (i == 1) {
            return "RESULT_ERROR_UNSUPPORTED";
        }
        if (i == 2) {
            return "RESULT_ERROR_INVALID_ARG";
        }
        if (i == 3) {
            return "RESULT_ERROR_BUSY";
        }
        if (i == 4) {
            return "RESULT_ERROR_ILLEGAL_STATE";
        }
        if (i == 5) {
            return "RESULT_ERROR_TEMPORARILY_UNAVAILABLE";
        }
        return "Unknown(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public void onApnUnthrottled(String str) {
        if (this.mCallback != null) {
            try {
                Rlog.d(TAG, "onApnUnthrottled");
                this.mCallback.onApnUnthrottled(str);
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
