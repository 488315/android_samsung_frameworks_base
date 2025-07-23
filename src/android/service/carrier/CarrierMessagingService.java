package android.service.carrier;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.carrier.ICarrierMessagingService;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class CarrierMessagingService extends Service {
    public static final int DOWNLOAD_STATUS_ERROR = 2;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_CONFIGURATION_ERROR = 606;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_DATA_DISABLED = 610;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_HTTP_FAILURE = 603;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_INACTIVE_SUBSCRIPTION = 609;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_INVALID_APN = 601;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_INVALID_SUBSCRIPTION_ID = 608;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_IO_ERROR = 604;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_MMS_DISABLED_BY_CARRIER = 611;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_NO_DATA_NETWORK = 607;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_RETRY = 605;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_UNABLE_CONNECT_MMS = 602;
    public static final int DOWNLOAD_STATUS_MMS_ERROR_UNSPECIFIED = 600;
    public static final int DOWNLOAD_STATUS_OK = 0;
    public static final int DOWNLOAD_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final int RECEIVE_OPTIONS_DEFAULT = 0;
    public static final int RECEIVE_OPTIONS_DROP = 1;
    public static final int RECEIVE_OPTIONS_SKIP_NOTIFY_WHEN_CREDENTIAL_PROTECTED_STORAGE_UNAVAILABLE = 2;
    public static final int SEND_FLAG_REQUEST_DELIVERY_STATUS = 1;
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_MMS_ERROR_CONFIGURATION_ERROR = 406;
    public static final int SEND_STATUS_MMS_ERROR_DATA_DISABLED = 410;
    public static final int SEND_STATUS_MMS_ERROR_HTTP_FAILURE = 403;
    public static final int SEND_STATUS_MMS_ERROR_INACTIVE_SUBSCRIPTION = 409;
    public static final int SEND_STATUS_MMS_ERROR_INVALID_APN = 401;
    public static final int SEND_STATUS_MMS_ERROR_INVALID_SUBSCRIPTION_ID = 408;
    public static final int SEND_STATUS_MMS_ERROR_IO_ERROR = 404;
    public static final int SEND_STATUS_MMS_ERROR_MMS_DISABLED_BY_CARRIER = 411;
    public static final int SEND_STATUS_MMS_ERROR_NO_DATA_NETWORK = 407;
    public static final int SEND_STATUS_MMS_ERROR_RETRY = 405;
    public static final int SEND_STATUS_MMS_ERROR_UNABLE_CONNECT_MMS = 402;
    public static final int SEND_STATUS_MMS_ERROR_UNSPECIFIED = 400;
    public static final int SEND_STATUS_OK = 0;
    public static final int SEND_STATUS_RESULT_CANCELLED = 215;
    public static final int SEND_STATUS_RESULT_ENCODING_ERROR = 212;
    public static final int SEND_STATUS_RESULT_ERROR_FDN_CHECK_FAILURE = 204;
    public static final int SEND_STATUS_RESULT_ERROR_GENERIC_FAILURE = 200;
    public static final int SEND_STATUS_RESULT_ERROR_LIMIT_EXCEEDED = 203;
    public static final int SEND_STATUS_RESULT_ERROR_NO_SERVICE = 202;
    public static final int SEND_STATUS_RESULT_ERROR_NULL_PDU = 201;
    public static final int SEND_STATUS_RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED = 206;
    public static final int SEND_STATUS_RESULT_ERROR_SHORT_CODE_NOT_ALLOWED = 205;
    public static final int SEND_STATUS_RESULT_INVALID_ARGUMENTS = 208;
    public static final int SEND_STATUS_RESULT_INVALID_SMSC_ADDRESS = 213;
    public static final int SEND_STATUS_RESULT_INVALID_SMS_FORMAT = 210;
    public static final int SEND_STATUS_RESULT_INVALID_STATE = 209;
    public static final int SEND_STATUS_RESULT_NETWORK_ERROR = 211;
    public static final int SEND_STATUS_RESULT_NETWORK_REJECT = 207;
    public static final int SEND_STATUS_RESULT_OPERATION_NOT_ALLOWED = 214;
    public static final int SEND_STATUS_RESULT_REQUEST_NOT_SUPPORTED = 216;
    public static final int SEND_STATUS_RESULT_SMS_BLOCKED_DURING_EMERGENCY = 217;
    public static final int SEND_STATUS_RESULT_SMS_SEND_RETRY_FAILED = 218;
    public static final int SEND_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final String SERVICE_INTERFACE = "android.service.carrier.CarrierMessagingService";
    private final ICarrierMessagingWrapper mWrapper = new ICarrierMessagingWrapper();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterCompleteResult {
    }

    public interface ResultCallback<T> {
        void onReceiveResult(T t) throws RemoteException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SendRequest {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SendResult {
    }

    @Deprecated
    public void onFilterSms(MessagePdu messagePdu, String str, int i, int i2, ResultCallback<Boolean> resultCallback) {
        try {
            resultCallback.onReceiveResult(true);
        } catch (RemoteException unused) {
        }
    }

    public void onReceiveTextSms(MessagePdu messagePdu, String str, int i, int i2, final ResultCallback<Integer> resultCallback) {
        onFilterSms(messagePdu, str, i, i2, new ResultCallback<Boolean>(this) { // from class: android.service.carrier.CarrierMessagingService.1
            @Override // android.service.carrier.CarrierMessagingService.ResultCallback
            public void onReceiveResult(Boolean bool) throws RemoteException {
                resultCallback.onReceiveResult(Integer.valueOf(bool.booleanValue() ? 0 : 3));
            }
        });
    }

    @Deprecated
    public void onSendTextSms(String str, int i, String str2, ResultCallback<SendSmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new SendSmsResult(1, 0));
        } catch (RemoteException unused) {
        }
    }

    public void onSendTextSms(String str, int i, String str2, int i2, ResultCallback<SendSmsResult> resultCallback) {
        onSendTextSms(str, i, str2, resultCallback);
    }

    @Deprecated
    public void onSendDataSms(byte[] bArr, int i, String str, int i2, ResultCallback<SendSmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new SendSmsResult(1, 0));
        } catch (RemoteException unused) {
        }
    }

    public void onSendDataSms(byte[] bArr, int i, String str, int i2, int i3, ResultCallback<SendSmsResult> resultCallback) {
        onSendDataSms(bArr, i, str, i2, resultCallback);
    }

    @Deprecated
    public void onSendMultipartTextSms(List<String> list, int i, String str, ResultCallback<SendMultipartSmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new SendMultipartSmsResult(1, null));
        } catch (RemoteException unused) {
        }
    }

    public void onSendMultipartTextSms(List<String> list, int i, String str, int i2, ResultCallback<SendMultipartSmsResult> resultCallback) {
        onSendMultipartTextSms(list, i, str, resultCallback);
    }

    public void onSendMms(Uri uri, int i, Uri uri2, ResultCallback<SendMmsResult> resultCallback) {
        try {
            resultCallback.onReceiveResult(new SendMmsResult(1, null));
        } catch (RemoteException unused) {
        }
    }

    public void onDownloadMms(Uri uri, int i, Uri uri2, ResultCallback<Integer> resultCallback) {
        try {
            resultCallback.onReceiveResult(1);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mWrapper;
        }
        return null;
    }

    public static final class SendMmsResult {
        private byte[] mSendConfPdu;
        private int mSendStatus;

        public SendMmsResult(int i, byte[] bArr) {
            this.mSendStatus = i;
            this.mSendConfPdu = bArr;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }

        public byte[] getSendConfPdu() {
            return this.mSendConfPdu;
        }
    }

    public static final class SendSmsResult {
        private final int mMessageRef;
        private final int mSendStatus;

        public SendSmsResult(int i, int i2) {
            this.mSendStatus = i;
            this.mMessageRef = i2;
        }

        public int getMessageRef() {
            return this.mMessageRef;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

    public static final class SendMultipartSmsResult {
        private final int[] mMessageRefs;
        private final int mSendStatus;

        public SendMultipartSmsResult(int i, int[] iArr) {
            this.mSendStatus = i;
            this.mMessageRefs = iArr;
        }

        public int[] getMessageRefs() {
            return this.mMessageRefs;
        }

        public int getSendStatus() {
            return this.mSendStatus;
        }
    }

    private class ICarrierMessagingWrapper extends ICarrierMessagingService.Stub {
        private ICarrierMessagingWrapper() {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void filterSms(MessagePdu messagePdu, String str, int i, int i2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onReceiveTextSms(messagePdu, str, i, i2, new ResultCallback<Integer>(this) { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.1
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(Integer num) throws RemoteException {
                    iCarrierMessagingCallback.onFilterComplete(num.intValue());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendTextSms(String str, int i, String str2, int i2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendTextSms(str, i, str2, i2, new ResultCallback<SendSmsResult>(this) { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.2
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(SendSmsResult sendSmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendSmsComplete(sendSmsResult.getSendStatus(), sendSmsResult.getMessageRef());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendDataSms(byte[] bArr, int i, String str, int i2, int i3, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendDataSms(bArr, i, str, i2, i3, new ResultCallback<SendSmsResult>(this) { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.3
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(SendSmsResult sendSmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendSmsComplete(sendSmsResult.getSendStatus(), sendSmsResult.getMessageRef());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMultipartTextSms(List<String> list, int i, String str, int i2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendMultipartTextSms(list, i, str, i2, new ResultCallback<SendMultipartSmsResult>(this) { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.4
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(SendMultipartSmsResult sendMultipartSmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendMultipartSmsComplete(sendMultipartSmsResult.getSendStatus(), sendMultipartSmsResult.getMessageRefs());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMms(Uri uri, int i, Uri uri2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onSendMms(uri, i, uri2, new ResultCallback<SendMmsResult>(this) { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.5
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(SendMmsResult sendMmsResult) throws RemoteException {
                    iCarrierMessagingCallback.onSendMmsComplete(sendMmsResult.getSendStatus(), sendMmsResult.getSendConfPdu());
                }
            });
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void downloadMms(Uri uri, int i, Uri uri2, final ICarrierMessagingCallback iCarrierMessagingCallback) {
            CarrierMessagingService.this.onDownloadMms(uri, i, uri2, new ResultCallback<Integer>(this) { // from class: android.service.carrier.CarrierMessagingService.ICarrierMessagingWrapper.6
                @Override // android.service.carrier.CarrierMessagingService.ResultCallback
                public void onReceiveResult(Integer num) throws RemoteException {
                    iCarrierMessagingCallback.onDownloadMmsComplete(num.intValue());
                }
            });
        }
    }
}
