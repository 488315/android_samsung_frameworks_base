package android.telephony.data;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.net.LinkProperties;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.data.IDataService;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.util.FunctionalUtils;
import com.android.telephony.Rlog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes4.dex */
public abstract class DataService extends Service {
    private static final int DATA_SERVICE_CREATE_DATA_SERVICE_PROVIDER = 1;
    private static final int DATA_SERVICE_INDICATION_APN_UNTHROTTLED = 16;
    private static final int DATA_SERVICE_INDICATION_DATA_CALL_LIST_CHANGED = 11;
    private static final int DATA_SERVICE_REMOVE_ALL_DATA_SERVICE_PROVIDERS = 3;
    private static final int DATA_SERVICE_REMOVE_DATA_SERVICE_PROVIDER = 2;
    private static final int DATA_SERVICE_REQUEST_CANCEL_HANDOVER = 13;
    private static final int DATA_SERVICE_REQUEST_DEACTIVATE_DATA_CALL = 5;
    private static final int DATA_SERVICE_REQUEST_REGISTER_APN_UNTHROTTLED = 14;
    private static final int DATA_SERVICE_REQUEST_REGISTER_DATA_CALL_LIST_CHANGED = 9;
    private static final int DATA_SERVICE_REQUEST_REQUEST_DATA_CALL_LIST = 8;
    private static final int DATA_SERVICE_REQUEST_SETUP_DATA_CALL = 4;
    private static final int DATA_SERVICE_REQUEST_SET_DATA_PROFILE = 7;
    private static final int DATA_SERVICE_REQUEST_SET_INITIAL_ATTACH_APN = 6;
    private static final int DATA_SERVICE_REQUEST_START_HANDOVER = 12;
    private static final int DATA_SERVICE_REQUEST_UNREGISTER_APN_UNTHROTTLED = 15;
    private static final int DATA_SERVICE_REQUEST_UNREGISTER_DATA_CALL_LIST_CHANGED = 10;
    private static final int DATA_SERVICE_REQUEST_VALIDATION = 17;
    public static final int REQUEST_REASON_APN_CHANGE = 5;
    public static final int REQUEST_REASON_HANDOVER = 3;
    public static final int REQUEST_REASON_NORMAL = 1;
    public static final int REQUEST_REASON_PDP_RESET = 4;
    public static final int REQUEST_REASON_SHUTDOWN = 2;
    public static final int REQUEST_REASON_UNKNOWN = 0;
    public static final String SERVICE_INTERFACE = "android.telephony.data.DataService";
    private static final String TAG = "DataService";
    private final DataServiceHandler mHandler;
    private final Executor mHandlerExecutor;
    private final HandlerThread mHandlerThread;
    private final SparseArray<DataServiceProvider> mServiceMap = new SparseArray<>();
    public final IDataServiceWrapper mBinder = new IDataServiceWrapper();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeactivateDataReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetupDataReason {
    }

    public abstract DataServiceProvider onCreateDataServiceProvider(int i);

    public abstract class DataServiceProvider implements AutoCloseable {
        private final int mSlotIndex;
        private final List<IDataServiceCallback> mDataCallListChangedCallbacks = new ArrayList();
        private final List<IDataServiceCallback> mApnUnthrottledCallbacks = new ArrayList();

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public DataServiceProvider(int i) {
            this.mSlotIndex = i;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public void setupDataCall(int i, DataProfile dataProfile, boolean z, boolean z2, int i2, LinkProperties linkProperties, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetupDataCallComplete(1, null);
            }
        }

        public void setupDataCall(int i, DataProfile dataProfile, boolean z, boolean z2, int i2, LinkProperties linkProperties, int i3, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, DataServiceCallback dataServiceCallback) {
            setupDataCall(i, dataProfile, z, z2, i2, linkProperties, dataServiceCallback);
        }

        public void deactivateDataCall(int i, int i2, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onDeactivateDataCallComplete(1);
            }
        }

        public void setInitialAttachApn(DataProfile dataProfile, boolean z, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetInitialAttachApnComplete(1);
            }
        }

        public void setDataProfile(List<DataProfile> list, boolean z, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetDataProfileComplete(1);
            }
        }

        public void startHandover(int i, DataServiceCallback dataServiceCallback) {
            Objects.requireNonNull(dataServiceCallback, "callback cannot be null");
            Log.d(DataService.TAG, "startHandover: " + i);
            dataServiceCallback.onHandoverStarted(1);
        }

        public void cancelHandover(int i, DataServiceCallback dataServiceCallback) {
            Objects.requireNonNull(dataServiceCallback, "callback cannot be null");
            Log.d(DataService.TAG, "cancelHandover: " + i);
            dataServiceCallback.onHandoverCancelled(1);
        }

        public void requestDataCallList(DataServiceCallback dataServiceCallback) {
            dataServiceCallback.onRequestDataCallListComplete(1, Collections.EMPTY_LIST);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForDataCallListChanged(IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mDataCallListChangedCallbacks) {
                this.mDataCallListChangedCallbacks.add(iDataServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterForDataCallListChanged(IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mDataCallListChangedCallbacks) {
                this.mDataCallListChangedCallbacks.remove(iDataServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForApnUnthrottled(IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mApnUnthrottledCallbacks) {
                this.mApnUnthrottledCallbacks.add(iDataServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterForApnUnthrottled(IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mApnUnthrottledCallbacks) {
                this.mApnUnthrottledCallbacks.remove(iDataServiceCallback);
            }
        }

        public void requestNetworkValidation(int i, Executor executor, final Consumer<Integer> consumer) {
            Objects.requireNonNull(executor, "executor cannot be null");
            Objects.requireNonNull(consumer, "resultCodeCallback cannot be null");
            Log.d(DataService.TAG, "requestNetworkValidation: " + i);
            executor.execute(new Runnable() { // from class: android.telephony.data.DataService$DataServiceProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(1);
                }
            });
        }

        public final void notifyDataCallListChanged(List<DataCallResponse> list) {
            synchronized (this.mDataCallListChangedCallbacks) {
                Iterator<IDataServiceCallback> it = this.mDataCallListChangedCallbacks.iterator();
                while (it.hasNext()) {
                    DataService.this.mHandler.obtainMessage(11, this.mSlotIndex, 0, new DataCallListChangedIndication(list, it.next())).sendToTarget();
                }
            }
        }

        public final void notifyApnUnthrottled(String str) {
            synchronized (this.mApnUnthrottledCallbacks) {
                Iterator<IDataServiceCallback> it = this.mApnUnthrottledCallbacks.iterator();
                while (it.hasNext()) {
                    DataService.this.mHandler.obtainMessage(16, this.mSlotIndex, 0, new ApnUnthrottledIndication(str, it.next())).sendToTarget();
                }
            }
        }

        public final void notifyDataProfileUnthrottled(DataProfile dataProfile) {
            synchronized (this.mApnUnthrottledCallbacks) {
                Iterator<IDataServiceCallback> it = this.mApnUnthrottledCallbacks.iterator();
                while (it.hasNext()) {
                    DataService.this.mHandler.obtainMessage(16, this.mSlotIndex, 0, new ApnUnthrottledIndication(dataProfile, it.next())).sendToTarget();
                }
            }
        }
    }

    private static final class SetupDataCallRequest {
        public final int accessNetworkType;
        public final boolean allowRoaming;
        public final IDataServiceCallback callback;
        public final DataProfile dataProfile;
        public final boolean isRoaming;
        public final LinkProperties linkProperties;
        public final boolean matchAllRuleAllowed;
        public final int pduSessionId;
        public final int reason;
        public final NetworkSliceInfo sliceInfo;
        public final TrafficDescriptor trafficDescriptor;

        SetupDataCallRequest(int i, DataProfile dataProfile, boolean z, boolean z2, int i2, LinkProperties linkProperties, int i3, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, IDataServiceCallback iDataServiceCallback) {
            this.accessNetworkType = i;
            this.dataProfile = dataProfile;
            this.isRoaming = z;
            this.allowRoaming = z2;
            this.linkProperties = linkProperties;
            this.reason = i2;
            this.pduSessionId = i3;
            this.sliceInfo = networkSliceInfo;
            this.trafficDescriptor = trafficDescriptor;
            this.matchAllRuleAllowed = z3;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class DeactivateDataCallRequest {
        public final IDataServiceCallback callback;
        public final int cid;
        public final int reason;

        DeactivateDataCallRequest(int i, int i2, IDataServiceCallback iDataServiceCallback) {
            this.cid = i;
            this.reason = i2;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class SetInitialAttachApnRequest {
        public final IDataServiceCallback callback;
        public final DataProfile dataProfile;
        public final boolean isRoaming;

        SetInitialAttachApnRequest(DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = dataProfile;
            this.isRoaming = z;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class SetDataProfileRequest {
        public final IDataServiceCallback callback;
        public final List<DataProfile> dps;
        public final boolean isRoaming;

        SetDataProfileRequest(List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) {
            this.dps = list;
            this.isRoaming = z;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class BeginCancelHandoverRequest {
        public final IDataServiceCallback callback;
        public final int cid;

        BeginCancelHandoverRequest(int i, IDataServiceCallback iDataServiceCallback) {
            this.cid = i;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class DataCallListChangedIndication {
        public final IDataServiceCallback callback;
        public final List<DataCallResponse> dataCallList;

        DataCallListChangedIndication(List<DataCallResponse> list, IDataServiceCallback iDataServiceCallback) {
            this.dataCallList = list;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class ApnUnthrottledIndication {
        public final String apn;
        public final IDataServiceCallback callback;
        public final DataProfile dataProfile;

        ApnUnthrottledIndication(String str, IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = null;
            this.apn = str;
            this.callback = iDataServiceCallback;
        }

        ApnUnthrottledIndication(DataProfile dataProfile, IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = dataProfile;
            this.apn = null;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class ValidationRequest {
        public final IIntegerConsumer callback;
        public final int cid;
        public final Executor executor;

        ValidationRequest(int i, Executor executor, IIntegerConsumer iIntegerConsumer) {
            this.cid = i;
            this.executor = executor;
            this.callback = iIntegerConsumer;
        }
    }

    private class DataServiceHandler extends Handler {
        DataServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.arg1;
            DataServiceProvider dataServiceProvider = (DataServiceProvider) DataService.this.mServiceMap.get(i);
            switch (message.what) {
                case 1:
                    DataServiceProvider onCreateDataServiceProvider = DataService.this.onCreateDataServiceProvider(message.arg1);
                    if (onCreateDataServiceProvider != null) {
                        DataService.this.mServiceMap.put(i, onCreateDataServiceProvider);
                        break;
                    }
                    break;
                case 2:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.close();
                        DataService.this.mServiceMap.remove(i);
                        break;
                    }
                    break;
                case 3:
                    for (int i2 = 0; i2 < DataService.this.mServiceMap.size(); i2++) {
                        DataServiceProvider dataServiceProvider2 = (DataServiceProvider) DataService.this.mServiceMap.get(i2);
                        if (dataServiceProvider2 != null) {
                            dataServiceProvider2.close();
                        }
                    }
                    DataService.this.mServiceMap.clear();
                    break;
                case 4:
                    if (dataServiceProvider != null) {
                        SetupDataCallRequest setupDataCallRequest = (SetupDataCallRequest) message.obj;
                        dataServiceProvider.setupDataCall(setupDataCallRequest.accessNetworkType, setupDataCallRequest.dataProfile, setupDataCallRequest.isRoaming, setupDataCallRequest.allowRoaming, setupDataCallRequest.reason, setupDataCallRequest.linkProperties, setupDataCallRequest.pduSessionId, setupDataCallRequest.sliceInfo, setupDataCallRequest.trafficDescriptor, setupDataCallRequest.matchAllRuleAllowed, setupDataCallRequest.callback != null ? new DataServiceCallback(setupDataCallRequest.callback) : null);
                        break;
                    }
                    break;
                case 5:
                    if (dataServiceProvider != null) {
                        DeactivateDataCallRequest deactivateDataCallRequest = (DeactivateDataCallRequest) message.obj;
                        dataServiceProvider.deactivateDataCall(deactivateDataCallRequest.cid, deactivateDataCallRequest.reason, deactivateDataCallRequest.callback != null ? new DataServiceCallback(deactivateDataCallRequest.callback) : null);
                        break;
                    }
                    break;
                case 6:
                    if (dataServiceProvider != null) {
                        SetInitialAttachApnRequest setInitialAttachApnRequest = (SetInitialAttachApnRequest) message.obj;
                        dataServiceProvider.setInitialAttachApn(setInitialAttachApnRequest.dataProfile, setInitialAttachApnRequest.isRoaming, setInitialAttachApnRequest.callback != null ? new DataServiceCallback(setInitialAttachApnRequest.callback) : null);
                        break;
                    }
                    break;
                case 7:
                    if (dataServiceProvider != null) {
                        SetDataProfileRequest setDataProfileRequest = (SetDataProfileRequest) message.obj;
                        dataServiceProvider.setDataProfile(setDataProfileRequest.dps, setDataProfileRequest.isRoaming, setDataProfileRequest.callback != null ? new DataServiceCallback(setDataProfileRequest.callback) : null);
                        break;
                    }
                    break;
                case 8:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.requestDataCallList(new DataServiceCallback((IDataServiceCallback) message.obj));
                        break;
                    }
                    break;
                case 9:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.registerForDataCallListChanged((IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 10:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.unregisterForDataCallListChanged((IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 11:
                    if (dataServiceProvider != null) {
                        DataCallListChangedIndication dataCallListChangedIndication = (DataCallListChangedIndication) message.obj;
                        try {
                            dataCallListChangedIndication.callback.onDataCallListChanged(dataCallListChangedIndication.dataCallList);
                            break;
                        } catch (RemoteException e) {
                            DataService.this.loge("Failed to call onDataCallListChanged. " + e);
                            return;
                        }
                    }
                    break;
                case 12:
                    if (dataServiceProvider != null) {
                        BeginCancelHandoverRequest beginCancelHandoverRequest = (BeginCancelHandoverRequest) message.obj;
                        dataServiceProvider.startHandover(beginCancelHandoverRequest.cid, beginCancelHandoverRequest.callback != null ? new DataServiceCallback(beginCancelHandoverRequest.callback) : null);
                        break;
                    }
                    break;
                case 13:
                    if (dataServiceProvider != null) {
                        BeginCancelHandoverRequest beginCancelHandoverRequest2 = (BeginCancelHandoverRequest) message.obj;
                        dataServiceProvider.cancelHandover(beginCancelHandoverRequest2.cid, beginCancelHandoverRequest2.callback != null ? new DataServiceCallback(beginCancelHandoverRequest2.callback) : null);
                        break;
                    }
                    break;
                case 14:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.registerForApnUnthrottled((IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 15:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.unregisterForApnUnthrottled((IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 16:
                    if (dataServiceProvider != null) {
                        ApnUnthrottledIndication apnUnthrottledIndication = (ApnUnthrottledIndication) message.obj;
                        try {
                            if (apnUnthrottledIndication.dataProfile != null) {
                                apnUnthrottledIndication.callback.onDataProfileUnthrottled(apnUnthrottledIndication.dataProfile);
                                break;
                            } else {
                                apnUnthrottledIndication.callback.onApnUnthrottled(apnUnthrottledIndication.apn);
                                break;
                            }
                        } catch (RemoteException e2) {
                            DataService.this.loge("Failed to call onApnUnthrottled. " + e2);
                            return;
                        }
                    }
                    break;
                case 17:
                    if (dataServiceProvider != null) {
                        ValidationRequest validationRequest = (ValidationRequest) message.obj;
                        int i3 = validationRequest.cid;
                        Executor executor = validationRequest.executor;
                        IIntegerConsumer iIntegerConsumer = validationRequest.callback;
                        Objects.requireNonNull(iIntegerConsumer);
                        dataServiceProvider.requestNetworkValidation(i3, executor, FunctionalUtils.ignoreRemoteException(new DataService$DataServiceHandler$$ExternalSyntheticLambda0(iIntegerConsumer)));
                        break;
                    }
                    break;
            }
        }
    }

    public DataService() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        DataServiceHandler dataServiceHandler = new DataServiceHandler(handlerThread.getLooper());
        this.mHandler = dataServiceHandler;
        this.mHandlerExecutor = new HandlerExecutor(dataServiceHandler);
        log("Data service created");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent == null || !SERVICE_INTERFACE.equals(intent.getAction())) {
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
        this.mHandlerThread.quitSafely();
        super.onDestroy();
    }

    private class IDataServiceWrapper extends IDataService.Stub {
        private IDataServiceWrapper() {
        }

        @Override // android.telephony.data.IDataService
        public void createDataServiceProvider(int i) {
            DataService.this.mHandler.obtainMessage(1, i, 0).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void removeDataServiceProvider(int i) {
            DataService.this.mHandler.obtainMessage(2, i, 0).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void setupDataCall(int i, int i2, DataProfile dataProfile, boolean z, boolean z2, int i3, LinkProperties linkProperties, int i4, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(4, i, 0, new SetupDataCallRequest(i2, dataProfile, z, z2, i3, linkProperties, i4, networkSliceInfo, trafficDescriptor, z3, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void deactivateDataCall(int i, int i2, int i3, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(5, i, 0, new DeactivateDataCallRequest(i2, i3, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void setInitialAttachApn(int i, DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(6, i, 0, new SetInitialAttachApnRequest(dataProfile, z, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void setDataProfile(int i, List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(7, i, 0, new SetDataProfileRequest(list, z, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void requestDataCallList(int i, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("requestDataCallList: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(8, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void registerForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("registerForDataCallListChanged: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(9, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("unregisterForDataCallListChanged: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(10, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void startHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("startHandover: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(12, i, 0, new BeginCancelHandoverRequest(i2, iDataServiceCallback)).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void cancelHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("cancelHandover: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(13, i, 0, new BeginCancelHandoverRequest(i2, iDataServiceCallback)).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void registerForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("registerForUnthrottleApn: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(14, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("uregisterForUnthrottleApn: callback is null");
            } else {
                DataService.this.mHandler.obtainMessage(15, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void requestNetworkValidation(int i, int i2, IIntegerConsumer iIntegerConsumer) {
            if (iIntegerConsumer == null) {
                DataService.this.loge("requestNetworkValidation: resultCodeCallback is null");
            } else {
                DataService.this.mHandler.obtainMessage(17, i, 0, new ValidationRequest(i2, DataService.this.mHandlerExecutor, iIntegerConsumer)).sendToTarget();
            }
        }
    }

    private void log(String str) {
        Rlog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loge(String str) {
        Rlog.e(TAG, str);
    }
}
