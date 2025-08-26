package android.service.euicc;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.euicc.IEuiccService;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.SecureKeyConst;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SystemApi
/* loaded from: classes3.dex */
public abstract class EuiccService extends Service {
    public static final String ACTION_BIND_CARRIER_PROVISIONING_SERVICE = "android.service.euicc.action.BIND_CARRIER_PROVISIONING_SERVICE";
    public static final String ACTION_CONVERT_TO_EMBEDDED_SUBSCRIPTION = "android.service.euicc.action.CONVERT_TO_EMBEDDED_SUBSCRIPTION";
    public static final String ACTION_DELETE_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.DELETE_SUBSCRIPTION_PRIVILEGED";
    public static final String ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS = "android.service.euicc.action.MANAGE_EMBEDDED_SUBSCRIPTIONS";
    public static final String ACTION_PROVISION_EMBEDDED_SUBSCRIPTION = "android.service.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION";
    public static final String ACTION_RENAME_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.RENAME_SUBSCRIPTION_PRIVILEGED";

    @Deprecated
    public static final String ACTION_RESOLVE_CONFIRMATION_CODE = "android.service.euicc.action.RESOLVE_CONFIRMATION_CODE";
    public static final String ACTION_RESOLVE_DEACTIVATE_SIM = "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM";
    public static final String ACTION_RESOLVE_NO_EUICC_TARGET = "android.service.euicc.action.RESOLVE_NO_EUICC_TARGET";
    public static final String ACTION_RESOLVE_NO_PRIVILEGES = "android.service.euicc.action.RESOLVE_NO_PRIVILEGES";
    public static final String ACTION_RESOLVE_RESOLVABLE_ERRORS = "android.service.euicc.action.RESOLVE_RESOLVABLE_ERRORS";
    public static final String ACTION_RESOLVE_UPDATE_VERSION_AVAILABLE = "android.service.euicc.action.RESOLVE_UPDATE_VERSION_AVAILABLE";
    public static final String ACTION_START_CARRIER_ACTIVATION = "android.service.euicc.action.START_CARRIER_ACTIVATION";
    public static final String ACTION_START_EUICC_ACTIVATION = "android.service.euicc.action.START_EUICC_ACTIVATION";
    public static final String ACTION_TOGGLE_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.TOGGLE_SUBSCRIPTION_PRIVILEGED";
    public static final String ACTION_TRANSFER_EMBEDDED_SUBSCRIPTIONS = "android.service.euicc.action.TRANSFER_EMBEDDED_SUBSCRIPTIONS";
    public static final String CATEGORY_EUICC_UI = "android.service.euicc.category.EUICC_UI";
    public static final String EUICC_SERVICE_INTERFACE = "android.service.euicc.EuiccService";
    public static final String EXTRA_PACKAGE_NAME = "android.service.euicc.extra.PACKAGE_NAME";
    public static final String EXTRA_RESOLUTION_ALLOW_POLICY_RULES = "android.service.euicc.extra.RESOLUTION_ALLOW_POLICY_RULES";
    public static final String EXTRA_RESOLUTION_CALLING_PACKAGE = "android.service.euicc.extra.RESOLUTION_CALLING_PACKAGE";
    public static final String EXTRA_RESOLUTION_CARD_ID = "android.service.euicc.extra.RESOLUTION_CARD_ID";
    public static final String EXTRA_RESOLUTION_CONFIRMATION_CODE = "android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE";
    public static final String EXTRA_RESOLUTION_CONFIRMATION_CODE_RETRIED = "android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE_RETRIED";
    public static final String EXTRA_RESOLUTION_CONSENT = "android.service.euicc.extra.RESOLUTION_CONSENT";
    public static final String EXTRA_RESOLUTION_PORT_INDEX = "android.service.euicc.extra.RESOLUTION_PORT_INDEX";
    public static final String EXTRA_RESOLUTION_SUBSCRIPTION_ID = "android.service.euicc.extra.RESOLUTION_SUBSCRIPTION_ID";
    public static final String EXTRA_RESOLUTION_USE_PORT_INDEX = "android.service.euicc.extra.RESOLUTION_USE_PORT_INDEX";
    public static final String EXTRA_RESOLVABLE_ERRORS = "android.service.euicc.extra.RESOLVABLE_ERRORS";
    public static final int RESOLVABLE_ERROR_CONFIRMATION_CODE = 1;
    public static final int RESOLVABLE_ERROR_NETWORK_LOCK_STATE_LOCKED = 512;
    public static final int RESOLVABLE_ERROR_NETWORK_LOCK_STATE_UNKNOWN = 256;
    public static final int RESOLVABLE_ERROR_NO_PRIVILEGED = 1024;
    public static final int RESOLVABLE_ERROR_POLICY_RULES = 2;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_MUST_DEACTIVATE_SIM = -1;

    @Deprecated
    public static final int RESULT_NEED_CONFIRMATION_CODE = -2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_RESOLVABLE_ERRORS = -2;
    private static final String TAG = "EuiccService";
    private ThreadPoolExecutor mExecutor;
    private final IEuiccService.Stub mStubWrapper = new IEuiccServiceWrapper();

    public static abstract class OtaStatusChangedCallback {
        public abstract void onOtaStatusChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResolvableError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Result {
    }

    public abstract int onDeleteSubscription(int i, String str);

    @Deprecated
    public int onDownloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, boolean z2) {
        return Integer.MIN_VALUE;
    }

    @Deprecated
    public DownloadSubscriptionResult onDownloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, Bundle bundle) {
        return null;
    }

    @Deprecated
    public abstract int onEraseSubscriptions(int i);

    public abstract GetDefaultDownloadableSubscriptionListResult onGetDefaultDownloadableSubscriptionList(int i, boolean z);

    public abstract GetDownloadableSubscriptionMetadataResult onGetDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, boolean z);

    public abstract String onGetEid(int i);

    public abstract EuiccInfo onGetEuiccInfo(int i);

    public abstract GetEuiccProfileInfoListResult onGetEuiccProfileInfoList(int i);

    public abstract int onGetOtaStatus(int i);

    public abstract int onRetainSubscriptionsForFactoryReset(int i);

    public abstract void onStartOtaIfNecessary(int i, OtaStatusChangedCallback otaStatusChangedCallback);

    @Deprecated
    public abstract int onSwitchToSubscription(int i, String str, boolean z);

    public abstract int onUpdateSubscriptionNickname(int i, String str, String str2);

    public int encodeSmdxSubjectAndReasonCode(String str, String str2) throws NumberFormatException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("SubjectCode/ReasonCode is empty");
        }
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        if (strArrSplit.length > 3 || strArrSplit2.length > 3) {
            throw new UnsupportedOperationException("Only three nested layer is supported.");
        }
        int length = 10 << ((3 - strArrSplit.length) * 4);
        for (String str3 : strArrSplit) {
            int i = Integer.parseInt(str3);
            if (i > 15) {
                throw new UnsupportedOperationException("SubjectCode exceeds 15");
            }
            length = (length << 4) + i;
        }
        int length2 = length << ((3 - strArrSplit2.length) * 4);
        for (String str4 : strArrSplit2) {
            int i2 = Integer.parseInt(str4);
            if (i2 > 15) {
                throw new UnsupportedOperationException("ReasonCode exceeds 15");
            }
            length2 = (length2 << 4) + i2;
        }
        return length2;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory(this) { // from class: android.service.euicc.EuiccService.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "EuiccService #" + this.mCount.getAndIncrement());
            }
        });
        this.mExecutor = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mExecutor.shutdownNow();
        super.onDestroy();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mStubWrapper;
    }

    public GetDownloadableSubscriptionMetadataResult onGetDownloadableSubscriptionMetadata(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z) {
        throw new UnsupportedOperationException("LPA must override onGetDownloadableSubscriptionMetadata");
    }

    public DownloadSubscriptionResult onDownloadSubscription(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, Bundle bundle) {
        throw new UnsupportedOperationException("LPA must override onDownloadSubscription");
    }

    public int onSwitchToSubscriptionWithPort(int i, int i2, String str, boolean z) {
        throw new UnsupportedOperationException("LPA must override onSwitchToSubscriptionWithPort");
    }

    public int onEraseSubscriptions(int i, int i2) {
        throw new UnsupportedOperationException("This method must be overridden to enable the ResetOption parameter");
    }

    public long onGetAvailableMemoryInBytes(int i) {
        throw new UnsupportedOperationException("The connected LPA does not implementEuiccService#onGetAvailableMemoryInBytes(int)");
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("The connected LPA does not implement EuiccService#dump()");
    }

    public static String resultToString(int i) {
        if (i == -2) {
            return "RESOLVABLE_ERRORS";
        }
        if (i == -1) {
            return "MUST_DEACTIVATE_SIM";
        }
        if (i == 0) {
            return SecureKeyConst.AT_RESPONSE_OK;
        }
        if (i == 1) {
            return "FIRST_USER";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class IEuiccServiceWrapper extends IEuiccService.Stub {
        private IEuiccServiceWrapper() {
        }

        @Override // android.service.euicc.IEuiccService
        public void downloadSubscription(final int i, final int i2, final DownloadableSubscription downloadableSubscription, final boolean z, final boolean z2, final Bundle bundle, final IDownloadSubscriptionCallback iDownloadSubscriptionCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    DownloadSubscriptionResult downloadSubscriptionResultOnDownloadSubscription;
                    try {
                        downloadSubscriptionResultOnDownloadSubscription = EuiccService.this.onDownloadSubscription(i, i2, downloadableSubscription, z, z2, bundle);
                    } catch (AbstractMethodError | UnsupportedOperationException e) {
                        Log.w(EuiccService.TAG, "The new onDownloadSubscription(int, int, DownloadableSubscription, boolean, boolean, Bundle) is not implemented. Fall back to the old one.", e);
                        downloadSubscriptionResultOnDownloadSubscription = EuiccService.this.onDownloadSubscription(i, downloadableSubscription, z, z2, bundle);
                    }
                    try {
                        iDownloadSubscriptionCallback.onComplete(downloadSubscriptionResultOnDownloadSubscription);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getEid(final int i, final IGetEidCallback iGetEidCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetEidCallback.onSuccess(EuiccService.this.onGetEid(i));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getAvailableMemoryInBytes(final int i, final IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService$IEuiccServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getAvailableMemoryInBytes$0(i, iGetAvailableMemoryInBytesCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getAvailableMemoryInBytes$0(int i, IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) {
            String message;
            long jOnGetAvailableMemoryInBytes;
            try {
                jOnGetAvailableMemoryInBytes = EuiccService.this.onGetAvailableMemoryInBytes(i);
                message = "";
            } catch (UnsupportedOperationException e) {
                message = e.getMessage();
                jOnGetAvailableMemoryInBytes = -1;
            }
            try {
                if (!message.isEmpty()) {
                    iGetAvailableMemoryInBytesCallback.onUnsupportedOperationException(message);
                } else {
                    iGetAvailableMemoryInBytesCallback.onSuccess(jOnGetAvailableMemoryInBytes);
                }
            } catch (RemoteException unused) {
            }
        }

        @Override // android.service.euicc.IEuiccService
        public void startOtaIfNecessary(final int i, final IOtaStatusChangedCallback iOtaStatusChangedCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.3
                @Override // java.lang.Runnable
                public void run() {
                    EuiccService.this.onStartOtaIfNecessary(i, new OtaStatusChangedCallback() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.3.1
                        @Override // android.service.euicc.EuiccService.OtaStatusChangedCallback
                        public void onOtaStatusChanged(int i2) {
                            try {
                                iOtaStatusChangedCallback.onOtaStatusChanged(i2);
                            } catch (RemoteException unused) {
                            }
                        }
                    });
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getOtaStatus(final int i, final IGetOtaStatusCallback iGetOtaStatusCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetOtaStatusCallback.onSuccess(EuiccService.this.onGetOtaStatus(i));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getDownloadableSubscriptionMetadata(final int i, final int i2, final DownloadableSubscription downloadableSubscription, final boolean z, final boolean z2, final IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.5
                @Override // java.lang.Runnable
                public void run() {
                    GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResultOnGetDownloadableSubscriptionMetadata;
                    if (z) {
                        try {
                            getDownloadableSubscriptionMetadataResultOnGetDownloadableSubscriptionMetadata = EuiccService.this.onGetDownloadableSubscriptionMetadata(i, i2, downloadableSubscription, z2);
                        } catch (AbstractMethodError | UnsupportedOperationException e) {
                            Log.w(EuiccService.TAG, "The new onGetDownloadableSubscriptionMetadata(int, int, DownloadableSubscription, boolean) is not implemented. Fall back to the old one.", e);
                            getDownloadableSubscriptionMetadataResultOnGetDownloadableSubscriptionMetadata = EuiccService.this.onGetDownloadableSubscriptionMetadata(i, downloadableSubscription, z2);
                        }
                    } else {
                        getDownloadableSubscriptionMetadataResultOnGetDownloadableSubscriptionMetadata = EuiccService.this.onGetDownloadableSubscriptionMetadata(i, downloadableSubscription, z2);
                    }
                    try {
                        iGetDownloadableSubscriptionMetadataCallback.onComplete(getDownloadableSubscriptionMetadataResultOnGetDownloadableSubscriptionMetadata);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getDefaultDownloadableSubscriptionList(final int i, final boolean z, final IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetDefaultDownloadableSubscriptionListCallback.onComplete(EuiccService.this.onGetDefaultDownloadableSubscriptionList(i, z));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccProfileInfoList(final int i, final IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetEuiccProfileInfoListCallback.onComplete(EuiccService.this.onGetEuiccProfileInfoList(i));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccInfo(final int i, final IGetEuiccInfoCallback iGetEuiccInfoCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iGetEuiccInfoCallback.onSuccess(EuiccService.this.onGetEuiccInfo(i));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void deleteSubscription(final int i, final String str, final IDeleteSubscriptionCallback iDeleteSubscriptionCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iDeleteSubscriptionCallback.onComplete(EuiccService.this.onDeleteSubscription(i, str));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void switchToSubscription(final int i, final int i2, final String str, final boolean z, final ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, final boolean z2) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.10
                @Override // java.lang.Runnable
                public void run() {
                    int iOnSwitchToSubscription;
                    if (z2) {
                        iOnSwitchToSubscription = EuiccService.this.onSwitchToSubscriptionWithPort(i, i2, str, z);
                    } else {
                        iOnSwitchToSubscription = EuiccService.this.onSwitchToSubscription(i, str, z);
                    }
                    try {
                        iSwitchToSubscriptionCallback.onComplete(iOnSwitchToSubscription);
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void updateSubscriptionNickname(final int i, final String str, final String str2, final IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iUpdateSubscriptionNicknameCallback.onComplete(EuiccService.this.onUpdateSubscriptionNickname(i, str, str2));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptions(final int i, final IEraseSubscriptionsCallback iEraseSubscriptionsCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iEraseSubscriptionsCallback.onComplete(EuiccService.this.onEraseSubscriptions(i));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptionsWithOptions(final int i, final int i2, final IEraseSubscriptionsCallback iEraseSubscriptionsCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iEraseSubscriptionsCallback.onComplete(EuiccService.this.onEraseSubscriptions(i, i2));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void retainSubscriptionsForFactoryReset(final int i, final IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        iRetainSubscriptionsForFactoryResetCallback.onComplete(EuiccService.this.onRetainSubscriptionsForFactoryReset(i));
                    } catch (RemoteException unused) {
                    }
                }
            });
        }

        @Override // android.service.euicc.IEuiccService
        public void dump(final IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws RemoteException {
            EuiccService.this.mExecutor.execute(new Runnable() { // from class: android.service.euicc.EuiccService.IEuiccServiceWrapper.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        StringWriter stringWriter = new StringWriter();
                        EuiccService.this.dump(new PrintWriter(stringWriter));
                        iEuiccServiceDumpResultCallback.onComplete(stringWriter.toString());
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }
}
