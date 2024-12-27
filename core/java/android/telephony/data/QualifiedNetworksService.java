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
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.flags.FeatureFlags;
import com.android.internal.telephony.flags.FeatureFlagsImpl;
import com.android.internal.util.FunctionalUtils;
import com.android.telephony.Rlog;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

@SystemApi
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
    public static final String QUALIFIED_NETWORKS_SERVICE_INTERFACE =
            "android.telephony.data.QualifiedNetworksService";
    private static final String TAG = QualifiedNetworksService.class.getSimpleName();
    private static final FeatureFlags sFeatureFlag = new FeatureFlagsImpl();
    private final QualifiedNetworksServiceHandler mHandler;
    private final SparseArray<NetworkAvailabilityProvider> mProviders = new SparseArray<>();
    public final IQualifiedNetworksServiceWrapper mBinder = new IQualifiedNetworksServiceWrapper();
    private final HandlerThread mHandlerThread = new HandlerThread(TAG);

    public abstract NetworkAvailabilityProvider onCreateNetworkAvailabilityProvider(int i);

    public abstract class NetworkAvailabilityProvider implements AutoCloseable {
        private IQualifiedNetworksServiceCallback mCallback;
        private SparseArray<int[]> mQualifiedNetworkTypesList = new SparseArray<>();
        private final int mSlotIndex;

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public NetworkAvailabilityProvider(int slotIndex) {
            this.mSlotIndex = slotIndex;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public void registerForQualifiedNetworkTypesChanged(
                IQualifiedNetworksServiceCallback callback) {
            this.mCallback = callback;
            if (this.mCallback != null) {
                for (int i = 0; i < this.mQualifiedNetworkTypesList.size(); i++) {
                    try {
                        this.mCallback.onQualifiedNetworkTypesChanged(
                                this.mQualifiedNetworkTypesList.keyAt(i),
                                this.mQualifiedNetworkTypesList.valueAt(i));
                    } catch (RemoteException e) {
                        QualifiedNetworksService.this.loge(
                                "Failed to call onQualifiedNetworksChanged. " + e);
                    }
                }
            }
        }

        public final void updateQualifiedNetworkTypes(
                int apnTypes, List<Integer> qualifiedNetworkTypes) {
            int[] qualifiedNetworkTypesArray =
                    qualifiedNetworkTypes.stream()
                            .mapToInt(
                                    new ToIntFunction() { // from class:
                                                          // android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$$ExternalSyntheticLambda0
                                        @Override // java.util.function.ToIntFunction
                                        public final int applyAsInt(Object obj) {
                                            int intValue;
                                            intValue = ((Integer) obj).intValue();
                                            return intValue;
                                        }
                                    })
                            .toArray();
            QualifiedNetworksService.this
                    .mHandler
                    .obtainMessage(4, this.mSlotIndex, apnTypes, qualifiedNetworkTypesArray)
                    .sendToTarget();
        }

        public final void reconnectQualifiedNetworkType(int apnTypes, int qualifiedNetworkType) {
            QualifiedNetworksService.this
                    .mHandler
                    .obtainMessage(
                            8, this.mSlotIndex, apnTypes, Integer.valueOf(qualifiedNetworkType))
                    .sendToTarget();
        }

        public void onUpdateQualifiedNetworkTypes(int apnTypes, int[] qualifiedNetworkTypes) {
            this.mQualifiedNetworkTypesList.put(apnTypes, qualifiedNetworkTypes);
            if (this.mCallback != null) {
                try {
                    this.mCallback.onQualifiedNetworkTypesChanged(apnTypes, qualifiedNetworkTypes);
                } catch (RemoteException e) {
                    QualifiedNetworksService.this.loge(
                            "Failed to call onQualifiedNetworksChanged. " + e);
                }
            }
        }

        public void onReconnectQualifiedNetworkType(int apnTypes, int qualifiedNetworkType) {
            if (this.mCallback != null) {
                try {
                    this.mCallback.onReconnectQualifiedNetworkType(apnTypes, qualifiedNetworkType);
                } catch (RemoteException e) {
                    QualifiedNetworksService.this.loge(
                            "Failed to call onReconnectQualifiedNetworkType. " + e);
                }
            }
        }

        public void reportThrottleStatusChanged(List<ThrottleStatus> statuses) {
            Log.d(
                    QualifiedNetworksService.TAG,
                    "reportThrottleStatusChanged: statuses size=" + statuses.size());
        }

        public void reportEmergencyDataNetworkPreferredTransportChanged(int transportType) {
            Log.d(
                    QualifiedNetworksService.TAG,
                    "reportEmergencyDataNetworkPreferredTransportChanged: "
                            + AccessNetworkConstants.transportTypeToString(transportType));
        }

        public void requestNetworkValidation(
                int networkCapability,
                Executor executor,
                final Consumer<Integer> resultCodeCallback) {
            Objects.requireNonNull(executor, "executor cannot be null");
            Objects.requireNonNull(resultCodeCallback, "resultCodeCallback cannot be null");
            if (!QualifiedNetworksService.sFeatureFlag.networkValidation()) {
                QualifiedNetworksService.this.loge("networkValidation feature is disabled");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                resultCodeCallback.accept(1);
                            }
                        });
            } else {
                IIntegerConsumer callback = new AnonymousClass1(executor, resultCodeCallback);
                QualifiedNetworksService.this
                        .mHandler
                        .obtainMessage(
                                7,
                                this.mSlotIndex,
                                0,
                                new NetworkValidationRequestData(networkCapability, callback))
                        .sendToTarget();
            }
        }

        /* renamed from: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$1, reason: invalid class name */
        class AnonymousClass1 extends IIntegerConsumer.Stub {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ Consumer val$resultCodeCallback;

            AnonymousClass1(Executor executor, Consumer consumer) {
                this.val$executor = executor;
                this.val$resultCodeCallback = consumer;
            }

            @Override // com.android.internal.telephony.IIntegerConsumer
            public void accept(final int result) {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$resultCodeCallback;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(Integer.valueOf(result));
                            }
                        });
            }
        }

        public void onRequestNetworkValidation(NetworkValidationRequestData data) {
            try {
                QualifiedNetworksService.this.log("onRequestNetworkValidation");
                this.mCallback.onNetworkValidationRequested(
                        data.mNetworkCapability, data.mCallback);
            } catch (RemoteException | NullPointerException e) {
                QualifiedNetworksService.this.loge(
                        "Failed to call onRequestNetworkValidation. " + e);
                IIntegerConsumer iIntegerConsumer = data.mCallback;
                Objects.requireNonNull(iIntegerConsumer);
                FunctionalUtils.ignoreRemoteException(
                                new DataService$DataServiceHandler$$ExternalSyntheticLambda0(
                                        iIntegerConsumer))
                        .accept(1);
            }
        }

        public final void updateHandoverEnabled(int supportedApnTypes) {
            QualifiedNetworksService.this
                    .mHandler
                    .obtainMessage(9, this.mSlotIndex, supportedApnTypes)
                    .sendToTarget();
        }

        public void onUpdateHandoverEnabled(int supportedApnTypes) {
            if (this.mCallback != null) {
                try {
                    this.mCallback.onHandoverEnabledChanged(supportedApnTypes);
                } catch (RemoteException e) {
                    QualifiedNetworksService.this.loge(
                            "Failed to call onHandoverEnabledChanged. " + e);
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
            int slotIndex = message.arg1;
            NetworkAvailabilityProvider provider =
                    (NetworkAvailabilityProvider)
                            QualifiedNetworksService.this.mProviders.get(slotIndex);
            switch (message.what) {
                case 1:
                    if (QualifiedNetworksService.this.mProviders.get(slotIndex) != null) {
                        QualifiedNetworksService.this.loge(
                                "Network availability provider for slot "
                                        + slotIndex
                                        + " already existed.");
                        break;
                    } else {
                        NetworkAvailabilityProvider provider2 =
                                QualifiedNetworksService.this.onCreateNetworkAvailabilityProvider(
                                        slotIndex);
                        if (provider2 != null) {
                            QualifiedNetworksService.this.mProviders.put(slotIndex, provider2);
                            IQualifiedNetworksServiceCallback callback =
                                    (IQualifiedNetworksServiceCallback) message.obj;
                            provider2.registerForQualifiedNetworkTypesChanged(callback);
                            break;
                        } else {
                            QualifiedNetworksService.this.loge(
                                    "Failed to create network availability provider. slot index = "
                                            + slotIndex);
                            break;
                        }
                    }
                case 2:
                    if (provider != null) {
                        provider.close();
                        QualifiedNetworksService.this.mProviders.remove(slotIndex);
                        break;
                    }
                    break;
                case 3:
                    for (int i = 0; i < QualifiedNetworksService.this.mProviders.size(); i++) {
                        NetworkAvailabilityProvider provider3 =
                                (NetworkAvailabilityProvider)
                                        QualifiedNetworksService.this.mProviders.get(i);
                        if (provider3 != null) {
                            provider3.close();
                        }
                    }
                    QualifiedNetworksService.this.mProviders.clear();
                    break;
                case 4:
                    if (provider != null) {
                        provider.onUpdateQualifiedNetworkTypes(message.arg2, (int[]) message.obj);
                        break;
                    }
                    break;
                case 5:
                    if (provider != null) {
                        List<ThrottleStatus> statuses = (List) message.obj;
                        provider.reportThrottleStatusChanged(statuses);
                        break;
                    }
                    break;
                case 6:
                    if (provider != null) {
                        int transportType = message.arg2;
                        provider.reportEmergencyDataNetworkPreferredTransportChanged(transportType);
                        break;
                    }
                    break;
                case 7:
                    if (provider != null) {
                        provider.onRequestNetworkValidation(
                                (NetworkValidationRequestData) message.obj);
                        break;
                    }
                    break;
                case 8:
                    if (provider != null) {
                        provider.onReconnectQualifiedNetworkType(
                                message.arg2, ((Integer) message.obj).intValue());
                        break;
                    }
                    break;
                case 9:
                    if (provider != null) {
                        provider.onUpdateHandoverEnabled(message.arg2);
                        break;
                    }
                    break;
            }
        }
    }

    public QualifiedNetworksService() {
        this.mHandlerThread.start();
        this.mHandler = new QualifiedNetworksServiceHandler(this.mHandlerThread.getLooper());
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
        private IQualifiedNetworksServiceWrapper() {}

        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(
                int slotIndex, IQualifiedNetworksServiceCallback callback) {
            QualifiedNetworksService.this
                    .mHandler
                    .obtainMessage(1, slotIndex, 0, callback)
                    .sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int slotIndex) {
            QualifiedNetworksService.this.mHandler.obtainMessage(2, slotIndex, 0).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportThrottleStatusChanged(int slotIndex, List<ThrottleStatus> statuses) {
            QualifiedNetworksService.this
                    .mHandler
                    .obtainMessage(5, slotIndex, 0, statuses)
                    .sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportEmergencyDataNetworkPreferredTransportChanged(
                int slotIndex, int transportType) {
            QualifiedNetworksService.this
                    .mHandler
                    .obtainMessage(6, slotIndex, transportType)
                    .sendToTarget();
        }
    }

    private static final class NetworkValidationRequestData {
        final IIntegerConsumer mCallback;
        final int mNetworkCapability;

        private NetworkValidationRequestData(int networkCapability, IIntegerConsumer callback) {
            this.mNetworkCapability = networkCapability;
            this.mCallback = callback;
        }
    }

    public void log(String s) {
        Rlog.d(TAG, s);
    }

    public void loge(String s) {
        Rlog.e(TAG, s);
    }
}
