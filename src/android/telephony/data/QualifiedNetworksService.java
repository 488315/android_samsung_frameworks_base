package android.telephony.data;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.AccessNetworkConstants;
import android.telephony.data.IQualifiedNetworksService;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlags;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.FeatureFlagsImpl;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.util.FunctionalUtils;
import com.android.telephony.Rlog;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

@SystemApi
/* loaded from: classes4.dex */
public abstract class QualifiedNetworksService extends Service {
    private static final int QNS_APN_THROTTLE_STATUS_CHANGED = 5;
    private static final int QNS_CREATE_NETWORK_AVAILABILITY_PROVIDER = 1;
    private static final int QNS_EMERGENCY_DATA_NETWORK_PREFERRED_TRANSPORT_CHANGED = 6;
    private static final int QNS_RECONNECT_QUALIFIED_NETWORK = 8;
    private static final int QNS_REMOVE_ALL_NETWORK_AVAILABILITY_PROVIDERS = 3;
    private static final int QNS_REMOVE_NETWORK_AVAILABILITY_PROVIDER = 2;
    private static final int QNS_REQUEST_NETWORK_VALIDATION = 7;
    private static final int QNS_UPDATE_HANDOVER_ENABLED = 9;
    private static final int QNS_UPDATE_QUALIFIED_NETWORKS = 4;
    public static final String QUALIFIED_NETWORKS_SERVICE_INTERFACE = "android.telephony.data.QualifiedNetworksService";
    private static final String TAG = "QualifiedNetworksService";
    private static final FeatureFlags sFeatureFlag = new FeatureFlagsImpl();
    private final QualifiedNetworksServiceHandler mHandler;
    private final HandlerThread mHandlerThread;
    private final SparseArray<NetworkAvailabilityProvider> mProviders = new SparseArray<>();
    public final IQualifiedNetworksServiceWrapper mBinder = new IQualifiedNetworksServiceWrapper();

    public abstract NetworkAvailabilityProvider onCreateNetworkAvailabilityProvider(int i);

    public abstract class NetworkAvailabilityProvider implements AutoCloseable {
        private IQualifiedNetworksServiceCallback mCallback;
        private SparseArray<int[]> mQualifiedNetworkTypesList = new SparseArray<>();
        private final int mSlotIndex;

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public NetworkAvailabilityProvider(int i) {
            this.mSlotIndex = i;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForQualifiedNetworkTypesChanged(IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            this.mCallback = iQualifiedNetworksServiceCallback;
            if (iQualifiedNetworksServiceCallback != null) {
                for (int i = 0; i < this.mQualifiedNetworkTypesList.size(); i++) {
                    try {
                        this.mCallback.onQualifiedNetworkTypesChanged(this.mQualifiedNetworkTypesList.keyAt(i), this.mQualifiedNetworkTypesList.valueAt(i));
                    } catch (RemoteException e) {
                        QualifiedNetworksService.this.loge("Failed to call onQualifiedNetworksChanged. " + e);
                    }
                }
            }
        }

        public final void updateQualifiedNetworkTypes(int i, List<Integer> list) {
            QualifiedNetworksService.this.mHandler.obtainMessage(4, this.mSlotIndex, i, list.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((Integer) obj).intValue();
                }
            }).toArray()).sendToTarget();
        }

        public final void reconnectQualifiedNetworkType(int i, int i2) {
            QualifiedNetworksService.this.mHandler.obtainMessage(8, this.mSlotIndex, i, Integer.valueOf(i2)).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUpdateQualifiedNetworkTypes(int i, int[] iArr) {
            this.mQualifiedNetworkTypesList.put(i, iArr);
            IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback = this.mCallback;
            if (iQualifiedNetworksServiceCallback != null) {
                try {
                    iQualifiedNetworksServiceCallback.onQualifiedNetworkTypesChanged(i, iArr);
                } catch (RemoteException e) {
                    QualifiedNetworksService.this.loge("Failed to call onQualifiedNetworksChanged. " + e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onReconnectQualifiedNetworkType(int i, int i2) {
            IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback = this.mCallback;
            if (iQualifiedNetworksServiceCallback != null) {
                try {
                    iQualifiedNetworksServiceCallback.onReconnectQualifiedNetworkType(i, i2);
                } catch (RemoteException e) {
                    QualifiedNetworksService.this.loge("Failed to call onReconnectQualifiedNetworkType. " + e);
                }
            }
        }

        public void reportThrottleStatusChanged(List<ThrottleStatus> list) {
            Log.d(QualifiedNetworksService.TAG, "reportThrottleStatusChanged: statuses size=" + list.size());
        }

        public void reportEmergencyDataNetworkPreferredTransportChanged(int i) {
            Log.d(QualifiedNetworksService.TAG, "reportEmergencyDataNetworkPreferredTransportChanged: " + AccessNetworkConstants.transportTypeToString(i));
        }

        public void requestNetworkValidation(int i, Executor executor, Consumer<Integer> consumer) {
            Objects.requireNonNull(executor, "executor cannot be null");
            Objects.requireNonNull(consumer, "resultCodeCallback cannot be null");
            QualifiedNetworksService.this.mHandler.obtainMessage(7, this.mSlotIndex, 0, new NetworkValidationRequestData(i, new AnonymousClass1(this, executor, consumer))).sendToTarget();
        }

        /* renamed from: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$1, reason: invalid class name */
        class AnonymousClass1 extends IIntegerConsumer.Stub {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ Consumer val$resultCodeCallback;

            AnonymousClass1(NetworkAvailabilityProvider networkAvailabilityProvider, Executor executor, Consumer consumer) {
                this.val$executor = executor;
                this.val$resultCodeCallback = consumer;
            }

            @Override // com.android.internal.telephony.IIntegerConsumer
            public void accept(final int i) {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$resultCodeCallback;
                executor.execute(new Runnable() { // from class: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(i));
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRequestNetworkValidation(NetworkValidationRequestData networkValidationRequestData) {
            try {
                QualifiedNetworksService.this.log("onRequestNetworkValidation");
                this.mCallback.onNetworkValidationRequested(networkValidationRequestData.mNetworkCapability, networkValidationRequestData.mCallback);
            } catch (RemoteException | NullPointerException e) {
                QualifiedNetworksService.this.loge("Failed to call onRequestNetworkValidation. " + e);
                IIntegerConsumer iIntegerConsumer = networkValidationRequestData.mCallback;
                Objects.requireNonNull(iIntegerConsumer);
                FunctionalUtils.ignoreRemoteException(new DataService$DataServiceHandler$$ExternalSyntheticLambda0(iIntegerConsumer)).accept(1);
            }
        }

        public final void updateHandoverEnabled(int i) {
            QualifiedNetworksService.this.mHandler.obtainMessage(9, this.mSlotIndex, i).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUpdateHandoverEnabled(int i) {
            IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback = this.mCallback;
            if (iQualifiedNetworksServiceCallback != null) {
                try {
                    iQualifiedNetworksServiceCallback.onHandoverEnabledChanged(i);
                } catch (RemoteException e) {
                    QualifiedNetworksService.this.loge("Failed to call onHandoverEnabledChanged. " + e);
                }
            }
        }
    }

    private class QualifiedNetworksServiceHandler extends Handler {
        QualifiedNetworksServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.arg1;
            NetworkAvailabilityProvider networkAvailabilityProvider = (NetworkAvailabilityProvider) QualifiedNetworksService.this.mProviders.get(i);
            switch (message.what) {
                case 1:
                    if (QualifiedNetworksService.this.mProviders.get(i) != null) {
                        QualifiedNetworksService.this.loge("Network availability provider for slot " + i + " already existed.");
                        break;
                    } else {
                        NetworkAvailabilityProvider networkAvailabilityProviderOnCreateNetworkAvailabilityProvider = QualifiedNetworksService.this.onCreateNetworkAvailabilityProvider(i);
                        if (networkAvailabilityProviderOnCreateNetworkAvailabilityProvider != null) {
                            QualifiedNetworksService.this.mProviders.put(i, networkAvailabilityProviderOnCreateNetworkAvailabilityProvider);
                            networkAvailabilityProviderOnCreateNetworkAvailabilityProvider.registerForQualifiedNetworkTypesChanged((IQualifiedNetworksServiceCallback) message.obj);
                            break;
                        } else {
                            QualifiedNetworksService.this.loge("Failed to create network availability provider. slot index = " + i);
                            break;
                        }
                    }
                case 2:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.close();
                        QualifiedNetworksService.this.mProviders.remove(i);
                        break;
                    }
                    break;
                case 3:
                    for (int i2 = 0; i2 < QualifiedNetworksService.this.mProviders.size(); i2++) {
                        NetworkAvailabilityProvider networkAvailabilityProvider2 = (NetworkAvailabilityProvider) QualifiedNetworksService.this.mProviders.get(i2);
                        if (networkAvailabilityProvider2 != null) {
                            networkAvailabilityProvider2.close();
                        }
                    }
                    QualifiedNetworksService.this.mProviders.clear();
                    break;
                case 4:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onUpdateQualifiedNetworkTypes(message.arg2, (int[]) message.obj);
                        break;
                    }
                    break;
                case 5:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.reportThrottleStatusChanged((List) message.obj);
                        break;
                    }
                    break;
                case 6:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.reportEmergencyDataNetworkPreferredTransportChanged(message.arg2);
                        break;
                    }
                    break;
                case 7:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onRequestNetworkValidation((NetworkValidationRequestData) message.obj);
                        break;
                    }
                    break;
                case 8:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onReconnectQualifiedNetworkType(message.arg2, ((Integer) message.obj).intValue());
                        break;
                    }
                    break;
                case 9:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onUpdateHandoverEnabled(message.arg2);
                        break;
                    }
                    break;
            }
        }
    }

    public QualifiedNetworksService() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new QualifiedNetworksServiceHandler(handlerThread.getLooper());
        log("Qualified networks service created");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent == null || !QUALIFIED_NETWORKS_SERVICE_INTERFACE.equals(intent.getAction())) {
            loge("Unexpected intent " + intent);
            return null;
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        this.mHandler.obtainMessage(3).sendToTarget();
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quit();
    }

    private class IQualifiedNetworksServiceWrapper extends IQualifiedNetworksService.Stub {
        private IQualifiedNetworksServiceWrapper() {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(int i, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            QualifiedNetworksService.this.mHandler.obtainMessage(1, i, 0, iQualifiedNetworksServiceCallback).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int i) {
            QualifiedNetworksService.this.mHandler.obtainMessage(2, i, 0).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportThrottleStatusChanged(int i, List<ThrottleStatus> list) {
            QualifiedNetworksService.this.mHandler.obtainMessage(5, i, 0, list).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) {
            QualifiedNetworksService.this.mHandler.obtainMessage(6, i, i2).sendToTarget();
        }
    }

    private static final class NetworkValidationRequestData {
        final IIntegerConsumer mCallback;
        final int mNetworkCapability;

        private NetworkValidationRequestData(int i, IIntegerConsumer iIntegerConsumer) {
            this.mNetworkCapability = i;
            this.mCallback = iIntegerConsumer;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        Rlog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loge(String str) {
        Rlog.e(TAG, str);
    }
}
