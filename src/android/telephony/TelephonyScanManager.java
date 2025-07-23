package android.telephony;

import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.TelephonyScanManager;
import android.util.SparseArray;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public final class TelephonyScanManager {
    public static final int CALLBACK_RESTRICTED_SCAN_RESULTS = 4;
    public static final int CALLBACK_SCAN_COMPLETE = 3;
    public static final int CALLBACK_SCAN_ERROR = 2;
    public static final int CALLBACK_SCAN_RESULTS = 1;
    public static final int CALLBACK_TELEPHONY_DIED = 5;
    public static final int INVALID_SCAN_ID = -1;
    public static final String SCAN_RESULT_KEY = "scanResult";
    private static final String TAG = "TelephonyScanManager";
    private final IBinder.DeathRecipient mDeathRecipient;
    private final Handler mHandler;
    private final Looper mLooper;
    private final Messenger mMessenger;
    private final SparseArray<NetworkScanInfo> mScanInfo = new SparseArray<>();

    public static abstract class NetworkScanCallback {
        public void onComplete() {
        }

        public void onError(int i) {
        }

        public void onResults(List<CellInfo> list) {
        }
    }

    private static class NetworkScanInfo {
        private final NetworkScanCallback mCallback;
        private final Executor mExecutor;
        private final NetworkScanRequest mRequest;

        NetworkScanInfo(NetworkScanRequest networkScanRequest, Executor executor, NetworkScanCallback networkScanCallback) {
            this.mRequest = networkScanRequest;
            this.mExecutor = executor;
            this.mCallback = networkScanCallback;
        }
    }

    public TelephonyScanManager() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        this.mLooper = looper;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(looper);
        this.mHandler = anonymousClass1;
        this.mMessenger = new Messenger(anonymousClass1);
        this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.TelephonyScanManager.2
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                TelephonyScanManager.this.mHandler.obtainMessage(5).sendToTarget();
            }
        };
    }

    /* renamed from: android.telephony.TelephonyScanManager$1, reason: invalid class name */
    class AnonymousClass1 extends Handler {
        AnonymousClass1(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            NetworkScanInfo networkScanInfo;
            Preconditions.checkNotNull(message, "message cannot be null");
            int i = 0;
            if (message.what == 5) {
                synchronized (TelephonyScanManager.this.mScanInfo) {
                    while (i < TelephonyScanManager.this.mScanInfo.size()) {
                        NetworkScanInfo networkScanInfo2 = (NetworkScanInfo) TelephonyScanManager.this.mScanInfo.valueAt(i);
                        if (networkScanInfo2 != null) {
                            Executor executor = networkScanInfo2.mExecutor;
                            final NetworkScanCallback networkScanCallback = networkScanInfo2.mCallback;
                            if (executor != null && networkScanCallback != null) {
                                try {
                                    executor.execute(new Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            TelephonyScanManager.NetworkScanCallback.this.onError(3);
                                        }
                                    });
                                } catch (RejectedExecutionException unused) {
                                }
                            }
                        }
                        i++;
                    }
                    TelephonyScanManager.this.mScanInfo.clear();
                }
                return;
            }
            synchronized (TelephonyScanManager.this.mScanInfo) {
                networkScanInfo = (NetworkScanInfo) TelephonyScanManager.this.mScanInfo.get(message.arg2);
            }
            if (networkScanInfo == null) {
                com.android.telephony.Rlog.e(TelephonyScanManager.TAG, "Unexpceted message " + message.what + " as there is no NetworkScanInfo with id " + message.arg2);
                return;
            }
            final NetworkScanCallback networkScanCallback2 = networkScanInfo.mCallback;
            Executor executor2 = networkScanInfo.mExecutor;
            int i2 = message.what;
            if (i2 != 1) {
                if (i2 == 2) {
                    try {
                        final int i3 = message.arg1;
                        executor2.execute(new Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyScanManager.AnonymousClass1.lambda$handleMessage$2(i3, networkScanCallback2);
                            }
                        });
                        synchronized (TelephonyScanManager.this.mScanInfo) {
                            TelephonyScanManager.this.mScanInfo.remove(message.arg2);
                        }
                        return;
                    } catch (Exception e) {
                        com.android.telephony.Rlog.e(TelephonyScanManager.TAG, "Exception in networkscan callback onError", e);
                        return;
                    }
                }
                if (i2 == 3) {
                    try {
                        executor2.execute(new Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                TelephonyScanManager.AnonymousClass1.lambda$handleMessage$3(TelephonyScanManager.NetworkScanCallback.this);
                            }
                        });
                        synchronized (TelephonyScanManager.this.mScanInfo) {
                            TelephonyScanManager.this.mScanInfo.remove(message.arg2);
                        }
                        return;
                    } catch (Exception e2) {
                        com.android.telephony.Rlog.e(TelephonyScanManager.TAG, "Exception in networkscan callback onComplete", e2);
                        return;
                    }
                }
                if (i2 != 4) {
                    com.android.telephony.Rlog.e(TelephonyScanManager.TAG, "Unhandled message " + Integer.toHexString(message.what));
                    return;
                }
            }
            try {
                Parcelable[] parcelableArray = message.getData().getParcelableArray(TelephonyScanManager.SCAN_RESULT_KEY);
                final CellInfo[] cellInfoArr = new CellInfo[parcelableArray.length];
                while (i < parcelableArray.length) {
                    cellInfoArr[i] = (CellInfo) parcelableArray[i];
                    i++;
                }
                executor2.execute(new Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyScanManager.AnonymousClass1.lambda$handleMessage$1(cellInfoArr, networkScanCallback2);
                    }
                });
            } catch (Exception e3) {
                com.android.telephony.Rlog.e(TelephonyScanManager.TAG, "Exception in networkscan callback onResults", e3);
            }
        }

        static /* synthetic */ void lambda$handleMessage$1(CellInfo[] cellInfoArr, NetworkScanCallback networkScanCallback) {
            com.android.telephony.Rlog.d(TelephonyScanManager.TAG, "onResults: " + Arrays.toString(cellInfoArr));
            networkScanCallback.onResults(Arrays.asList(cellInfoArr));
        }

        static /* synthetic */ void lambda$handleMessage$2(int i, NetworkScanCallback networkScanCallback) {
            com.android.telephony.Rlog.d(TelephonyScanManager.TAG, "onError: " + i);
            networkScanCallback.onError(i);
        }

        static /* synthetic */ void lambda$handleMessage$3(NetworkScanCallback networkScanCallback) {
            com.android.telephony.Rlog.d(TelephonyScanManager.TAG, "onComplete");
            networkScanCallback.onComplete();
        }
    }

    public NetworkScan requestNetworkScan(int i, boolean z, NetworkScanRequest networkScanRequest, Executor executor, NetworkScanCallback networkScanCallback, String str, String str2) {
        try {
            Objects.requireNonNull(networkScanRequest, "Request was null");
            Objects.requireNonNull(networkScanCallback, "Callback was null");
            Objects.requireNonNull(executor, "Executor was null");
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            synchronized (this.mScanInfo) {
                int requestNetworkScan = iTelephony.requestNetworkScan(i, z, networkScanRequest, this.mMessenger, new Binder(), str, str2);
                if (requestNetworkScan == -1) {
                    com.android.telephony.Rlog.e(TAG, "Failed to initiate network scan");
                    return null;
                }
                iTelephony.asBinder().linkToDeath(this.mDeathRecipient, 0);
                saveScanInfo(requestNetworkScan, networkScanRequest, executor, networkScanCallback);
                return new NetworkScan(requestNetworkScan, i);
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "requestNetworkScan RemoteException", e);
            return null;
        } catch (NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "requestNetworkScan NPE", e2);
            return null;
        }
    }

    private void saveScanInfo(int i, NetworkScanRequest networkScanRequest, Executor executor, NetworkScanCallback networkScanCallback) {
        this.mScanInfo.put(i, new NetworkScanInfo(networkScanRequest, executor, networkScanCallback));
    }

    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }
}
