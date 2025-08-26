package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.os.RemoteException;
import android.telephony.SmsMessage;
import android.telephony.ims.aidl.IImsSmsListener;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public class ImsSmsImplBase {
    public static final int DELIVER_STATUS_ERROR_GENERIC = 2;
    public static final int DELIVER_STATUS_ERROR_NO_MEMORY = 3;
    public static final int DELIVER_STATUS_ERROR_REQUEST_NOT_SUPPORTED = 4;
    public static final int DELIVER_STATUS_OK = 1;
    private static final String LOG_TAG = "SmsImplBase";
    public static final int RESULT_NO_NETWORK_ERROR = -1;
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_ERROR_FALLBACK = 4;
    public static final int SEND_STATUS_ERROR_RETRY = 3;
    public static final int SEND_STATUS_OK = 1;
    public static final int STATUS_REPORT_STATUS_ERROR = 2;
    public static final int STATUS_REPORT_STATUS_OK = 1;
    private Executor mExecutor;
    private IImsSmsListener mListener;
    private final Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeliverStatusResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SendStatusResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusReportResult {
    }

    public void onMemoryAvailable(int i) {
    }

    public void onReady() {
    }

    public void setRetryCount(int i, int i2) throws RuntimeException {
    }

    public void setSmsc(String str) throws RuntimeException {
    }

    public ImsSmsImplBase() {
    }

    public ImsSmsImplBase(Executor executor) {
        this.mExecutor = executor;
    }

    public void registerSmsListener(IImsSmsListener iImsSmsListener) {
        synchronized (this.mLock) {
            this.mListener = iImsSmsListener;
        }
    }

    public void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) {
        try {
            onSendSmsResult(i, i2, 2, 1);
        } catch (RuntimeException e) {
            Log.e(LOG_TAG, "Can not send sms: " + e.getMessage());
        }
    }

    public void acknowledgeSms(int i, int i2, int i3) {
        Log.e(LOG_TAG, "acknowledgeSms() not implemented.");
    }

    public void acknowledgeSms(int i, int i2, int i3, byte[] bArr) {
        Log.e(LOG_TAG, "acknowledgeSms() not implemented. acknowledgeSms(int, int, int) called.");
        acknowledgeSms(i, i2, i3);
    }

    public void acknowledgeSmsReport(int i, int i2, int i3) {
        Log.e(LOG_TAG, "acknowledgeSmsReport() not implemented.");
    }

    public final void onSmsReceived(int i, String str, byte[] bArr) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSmsReceived(i, str, bArr);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Can not deliver sms: " + e.getMessage());
            SmsMessage smsMessageCreateFromPdu = SmsMessage.createFromPdu(bArr, str);
            if (smsMessageCreateFromPdu != null && smsMessageCreateFromPdu.mWrappedSmsMessage != null) {
                acknowledgeSms(i, smsMessageCreateFromPdu.mWrappedSmsMessage.mMessageRef, 2);
            } else {
                Log.w(LOG_TAG, "onSmsReceived: Invalid pdu entered.");
                acknowledgeSms(i, 0, 2);
            }
        }
    }

    public final void onSendSmsResultSuccess(int i, int i2) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResult(i, i2, 1, 0, -1);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public final void onSendSmsResult(int i, int i2, int i3, int i4) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResult(i, i2, i3, i4, -1);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void onSendSmsResultError(int i, int i2, int i3, int i4, int i5) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResult(i, i2, i3, i4, i5);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void onSendSmsResultIncludeErrClass(int i, int i2, int i3, int i4, int i5, int i6) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSendSmsResponse(i, i2, i3, i4, i5, i6);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void onReceiveSmsDeliveryReportAck(int i, int i2) {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onReceiveSmsDeliveryReportAck(i, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void onMemoryAvailableResult(int i, int i2, int i3) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onMemoryAvailableResult(i, i2, i3);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public final void onSmsStatusReportReceived(int i, int i2, String str, byte[] bArr) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSmsStatusReportReceived(i, str, bArr);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Can not process sms status report: " + e.getMessage());
            acknowledgeSmsReport(i, i2, 2);
        }
    }

    public final void onSmsStatusReportReceived(int i, String str, byte[] bArr) throws RuntimeException {
        IImsSmsListener iImsSmsListener;
        synchronized (this.mLock) {
            iImsSmsListener = this.mListener;
        }
        if (iImsSmsListener == null) {
            throw new RuntimeException("Feature not ready.");
        }
        try {
            iImsSmsListener.onSmsStatusReportReceived(i, str, bArr);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Can not process sms status report: " + e.getMessage());
            SmsMessage smsMessageCreateFromPdu = SmsMessage.createFromPdu(bArr, str);
            if (smsMessageCreateFromPdu != null && smsMessageCreateFromPdu.mWrappedSmsMessage != null) {
                acknowledgeSmsReport(i, smsMessageCreateFromPdu.mWrappedSmsMessage.mMessageRef, 2);
            } else {
                Log.w(LOG_TAG, "onSmsStatusReportReceived: Invalid pdu entered.");
                acknowledgeSmsReport(i, 0, 2);
            }
        }
    }

    public String getSmsFormat() {
        return "3gpp";
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }

    public Executor getExecutor() {
        Executor executor = this.mExecutor;
        return executor != null ? executor : new PendingIntent$$ExternalSyntheticLambda0();
    }
}
