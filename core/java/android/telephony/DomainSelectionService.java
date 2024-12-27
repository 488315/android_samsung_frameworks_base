package android.telephony;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.Service;
import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.telephony.IDomainSelectionServiceController;
import com.android.internal.telephony.IDomainSelector;
import com.android.internal.telephony.ITransportSelectorCallback;
import com.android.internal.telephony.ITransportSelectorResultCallback;
import com.android.internal.telephony.IWwanSelectorCallback;
import com.android.internal.telephony.IWwanSelectorResultCallback;
import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.util.TelephonyUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
public abstract class DomainSelectionService extends Service {
    private static final String LOG_TAG = "DomainSelectionService";
    public static final int SCAN_TYPE_FULL_SERVICE = 2;
    public static final int SCAN_TYPE_LIMITED_SERVICE = 1;
    public static final int SCAN_TYPE_NO_PREFERENCE = 0;
    public static final int SELECTOR_TYPE_CALLING = 1;
    public static final int SELECTOR_TYPE_SMS = 2;
    public static final String SERVICE_INTERFACE = "android.telephony.DomainSelectionService";
    private Executor mExecutor;
    private final Object mExecutorLock = new Object();
    private final IBinder mDomainSelectionServiceController = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmergencyScanType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectorType {}

    public abstract void onDomainSelection(
            SelectionAttributes selectionAttributes,
            TransportSelectorCallback transportSelectorCallback);

    public static final class SelectionAttributes implements Parcelable {
        public static final Parcelable.Creator<SelectionAttributes> CREATOR =
                new Parcelable.Creator<
                        SelectionAttributes>() { // from class:
                                                 // android.telephony.DomainSelectionService.SelectionAttributes.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SelectionAttributes createFromParcel(Parcel in) {
                        return new SelectionAttributes(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SelectionAttributes[] newArray(int size) {
                        return new SelectionAttributes[size];
                    }
                };
        private static final String TAG = "SelectionAttributes";
        private Uri mAddress;
        private String mCallId;
        private int mCause;
        private EmergencyRegistrationResult mEmergencyRegistrationResult;
        private ImsReasonInfo mImsReasonInfo;
        private boolean mIsEmergency;
        private boolean mIsExitedFromAirplaneMode;
        private boolean mIsTestEmergencyNumber;
        private boolean mIsVideoCall;
        private int mSelectorType;
        private int mSlotIndex;
        private int mSubId;

        private SelectionAttributes(
                int slotIndex,
                int subscriptionId,
                String callId,
                Uri address,
                int selectorType,
                boolean video,
                boolean emergency,
                boolean isTest,
                boolean exited,
                ImsReasonInfo imsReasonInfo,
                int cause,
                EmergencyRegistrationResult regResult) {
            this.mSlotIndex = slotIndex;
            this.mSubId = subscriptionId;
            this.mCallId = callId;
            this.mAddress = address;
            this.mSelectorType = selectorType;
            this.mIsVideoCall = video;
            this.mIsEmergency = emergency;
            this.mIsTestEmergencyNumber = isTest;
            this.mIsExitedFromAirplaneMode = exited;
            this.mImsReasonInfo = imsReasonInfo;
            this.mCause = cause;
            this.mEmergencyRegistrationResult = regResult;
        }

        public SelectionAttributes(SelectionAttributes s) {
            this.mSlotIndex = s.mSlotIndex;
            this.mSubId = s.mSubId;
            this.mCallId = s.mCallId;
            this.mAddress = s.mAddress;
            this.mSelectorType = s.mSelectorType;
            this.mIsEmergency = s.mIsEmergency;
            this.mIsTestEmergencyNumber = s.mIsTestEmergencyNumber;
            this.mIsExitedFromAirplaneMode = s.mIsExitedFromAirplaneMode;
            this.mImsReasonInfo = s.mImsReasonInfo;
            this.mCause = s.mCause;
            this.mEmergencyRegistrationResult = s.mEmergencyRegistrationResult;
        }

        private SelectionAttributes(Parcel in) {
            readFromParcel(in);
        }

        public int getSlotIndex() {
            return this.mSlotIndex;
        }

        public int getSubscriptionId() {
            return this.mSubId;
        }

        public String getCallId() {
            return this.mCallId;
        }

        public Uri getAddress() {
            return this.mAddress;
        }

        public int getSelectorType() {
            return this.mSelectorType;
        }

        public boolean isVideoCall() {
            return this.mIsVideoCall;
        }

        public boolean isEmergency() {
            return this.mIsEmergency;
        }

        public boolean isTestEmergencyNumber() {
            return this.mIsTestEmergencyNumber;
        }

        public boolean isExitedFromAirplaneMode() {
            return this.mIsExitedFromAirplaneMode;
        }

        public ImsReasonInfo getPsDisconnectCause() {
            return this.mImsReasonInfo;
        }

        public int getCsDisconnectCause() {
            return this.mCause;
        }

        public EmergencyRegistrationResult getEmergencyRegistrationResult() {
            return this.mEmergencyRegistrationResult;
        }

        public String toString() {
            return "{ slotIndex="
                    + this.mSlotIndex
                    + ", subId="
                    + this.mSubId
                    + ", callId="
                    + this.mCallId
                    + ", address="
                    + ((SemTelephonyUtils.SHIP_BUILD || !Build.IS_DEBUGGABLE)
                            ? "***"
                            : this.mAddress)
                    + ", type="
                    + this.mSelectorType
                    + ", videoCall="
                    + this.mIsVideoCall
                    + ", emergency="
                    + this.mIsEmergency
                    + ", isTest="
                    + this.mIsTestEmergencyNumber
                    + ", airplaneMode="
                    + this.mIsExitedFromAirplaneMode
                    + ", reasonInfo="
                    + this.mImsReasonInfo
                    + ", cause="
                    + this.mCause
                    + ", regResult="
                    + this.mEmergencyRegistrationResult
                    + " }";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SelectionAttributes that = (SelectionAttributes) o;
            if (this.mSlotIndex == that.mSlotIndex
                    && this.mSubId == that.mSubId
                    && TextUtils.equals(this.mCallId, that.mCallId)
                    && equalsHandlesNulls(this.mAddress, that.mAddress)
                    && this.mSelectorType == that.mSelectorType
                    && this.mIsVideoCall == that.mIsVideoCall
                    && this.mIsEmergency == that.mIsEmergency
                    && this.mIsTestEmergencyNumber == that.mIsTestEmergencyNumber
                    && this.mIsExitedFromAirplaneMode == that.mIsExitedFromAirplaneMode
                    && equalsHandlesNulls(this.mImsReasonInfo, that.mImsReasonInfo)
                    && this.mCause == that.mCause
                    && equalsHandlesNulls(
                            this.mEmergencyRegistrationResult, that.mEmergencyRegistrationResult)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(
                    this.mCallId,
                    this.mAddress,
                    this.mImsReasonInfo,
                    Boolean.valueOf(this.mIsVideoCall),
                    Boolean.valueOf(this.mIsEmergency),
                    Boolean.valueOf(this.mIsTestEmergencyNumber),
                    Boolean.valueOf(this.mIsExitedFromAirplaneMode),
                    this.mEmergencyRegistrationResult,
                    Integer.valueOf(this.mSlotIndex),
                    Integer.valueOf(this.mSubId),
                    Integer.valueOf(this.mSelectorType),
                    Integer.valueOf(this.mCause));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.mSlotIndex);
            out.writeInt(this.mSubId);
            out.writeString8(this.mCallId);
            out.writeParcelable(this.mAddress, 0);
            out.writeInt(this.mSelectorType);
            out.writeBoolean(this.mIsVideoCall);
            out.writeBoolean(this.mIsEmergency);
            out.writeBoolean(this.mIsTestEmergencyNumber);
            out.writeBoolean(this.mIsExitedFromAirplaneMode);
            out.writeParcelable(this.mImsReasonInfo, 0);
            out.writeInt(this.mCause);
            out.writeParcelable(this.mEmergencyRegistrationResult, 0);
        }

        private void readFromParcel(Parcel in) {
            this.mSlotIndex = in.readInt();
            this.mSubId = in.readInt();
            this.mCallId = in.readString8();
            this.mAddress = (Uri) in.readParcelable(Uri.class.getClassLoader(), Uri.class);
            this.mSelectorType = in.readInt();
            this.mIsVideoCall = in.readBoolean();
            this.mIsEmergency = in.readBoolean();
            this.mIsTestEmergencyNumber = in.readBoolean();
            this.mIsExitedFromAirplaneMode = in.readBoolean();
            this.mImsReasonInfo =
                    (ImsReasonInfo)
                            in.readParcelable(
                                    ImsReasonInfo.class.getClassLoader(), ImsReasonInfo.class);
            this.mCause = in.readInt();
            this.mEmergencyRegistrationResult =
                    (EmergencyRegistrationResult)
                            in.readParcelable(
                                    EmergencyRegistrationResult.class.getClassLoader(),
                                    EmergencyRegistrationResult.class);
        }

        private static boolean equalsHandlesNulls(Object a, Object b) {
            return a == null ? b == null : a.equals(b);
        }

        public static final class Builder {
            private Uri mAddress;
            private String mCallId;
            private int mCause;
            private EmergencyRegistrationResult mEmergencyRegistrationResult;
            private ImsReasonInfo mImsReasonInfo;
            private boolean mIsEmergency;
            private boolean mIsExitedFromAirplaneMode;
            private boolean mIsTestEmergencyNumber;
            private boolean mIsVideoCall;
            private final int mSelectorType;
            private final int mSlotIndex;
            private final int mSubId;

            public Builder(int slotIndex, int subscriptionId, int selectorType) {
                this.mSlotIndex = slotIndex;
                this.mSubId = subscriptionId;
                this.mSelectorType = selectorType;
            }

            public Builder setCallId(String callId) {
                this.mCallId = callId;
                return this;
            }

            public Builder setAddress(Uri address) {
                this.mAddress = address;
                return this;
            }

            public Builder setVideoCall(boolean isVideo) {
                this.mIsVideoCall = isVideo;
                return this;
            }

            public Builder setEmergency(boolean isEmergency) {
                this.mIsEmergency = isEmergency;
                return this;
            }

            public Builder setTestEmergencyNumber(boolean isTest) {
                this.mIsTestEmergencyNumber = isTest;
                return this;
            }

            public Builder setExitedFromAirplaneMode(boolean exited) {
                this.mIsExitedFromAirplaneMode = exited;
                return this;
            }

            public Builder setPsDisconnectCause(ImsReasonInfo info) {
                this.mImsReasonInfo = info;
                return this;
            }

            public Builder setCsDisconnectCause(int cause) {
                this.mCause = cause;
                return this;
            }

            public Builder setEmergencyRegistrationResult(EmergencyRegistrationResult regResult) {
                this.mEmergencyRegistrationResult = regResult;
                return this;
            }

            public SelectionAttributes build() {
                return new SelectionAttributes(
                        this.mSlotIndex,
                        this.mSubId,
                        this.mCallId,
                        this.mAddress,
                        this.mSelectorType,
                        this.mIsVideoCall,
                        this.mIsEmergency,
                        this.mIsTestEmergencyNumber,
                        this.mIsExitedFromAirplaneMode,
                        this.mImsReasonInfo,
                        this.mCause,
                        this.mEmergencyRegistrationResult);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TransportSelectorCallbackWrapper implements TransportSelectorCallback {
        private static final String TAG = "TransportSelectorCallbackWrapper";
        private final ITransportSelectorCallback mCallback;
        private final Executor mExecutor;
        private ITransportSelectorResultCallbackAdapter mResultCallback;
        private DomainSelectorWrapper mSelectorWrapper;

        TransportSelectorCallbackWrapper(ITransportSelectorCallback cb, Executor executor) {
            this.mCallback = cb;
            this.mExecutor = executor;
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onCreated(DomainSelector selector) {
            try {
                this.mSelectorWrapper =
                        DomainSelectionService.this
                        .new DomainSelectorWrapper(selector, this.mExecutor);
                this.mCallback.onCreated(this.mSelectorWrapper.getCallbackBinder());
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onCreated e=" + e);
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onWlanSelected(boolean useEmergencyPdn) {
            try {
                this.mCallback.onWlanSelected(useEmergencyPdn);
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onWlanSelected e=" + e);
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onWwanSelected(final Consumer<WwanSelectorCallback> consumer) {
            try {
                this.mResultCallback =
                        new ITransportSelectorResultCallbackAdapter(consumer, this.mExecutor);
                this.mCallback.onWwanSelectedAsync(this.mResultCallback);
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onWwanSelected e=" + e);
                DomainSelectionService.this.executeMethodAsyncNoException(
                        this.mExecutor,
                        new Runnable() { // from class:
                                         // android.telephony.DomainSelectionService$TransportSelectorCallbackWrapper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(null);
                            }
                        },
                        TAG,
                        "onWwanSelectedAsync-Exception");
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onSelectionTerminated(int cause) {
            try {
                this.mCallback.onSelectionTerminated(cause);
                this.mSelectorWrapper = null;
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onSelectionTerminated e=" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ITransportSelectorResultCallbackAdapter
                extends ITransportSelectorResultCallback.Stub {
            private final Consumer<WwanSelectorCallback> mConsumer;
            private final Executor mExecutor;

            ITransportSelectorResultCallbackAdapter(
                    Consumer<WwanSelectorCallback> consumer, Executor executor) {
                this.mConsumer = consumer;
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.ITransportSelectorResultCallback
            public void onCompleted(IWwanSelectorCallback cb) {
                if (this.mConsumer == null) {
                    return;
                }
                final WwanSelectorCallback callback =
                        DomainSelectionService.this
                        .new WwanSelectorCallbackWrapper(cb, this.mExecutor);
                DomainSelectionService.this.executeMethodAsyncNoException(
                        this.mExecutor,
                        new Runnable() { // from class:
                                         // android.telephony.DomainSelectionService$TransportSelectorCallbackWrapper$ITransportSelectorResultCallbackAdapter$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DomainSelectionService.TransportSelectorCallbackWrapper
                                        .ITransportSelectorResultCallbackAdapter.this
                                        .lambda$onCompleted$0(callback);
                            }
                        },
                        TransportSelectorCallbackWrapper.TAG,
                        "onWwanSelectedAsync-Completed");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCompleted$0(WwanSelectorCallback callback) {
                this.mConsumer.accept(callback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DomainSelectorWrapper {
        private static final String TAG = "DomainSelectorWrapper";
        private IDomainSelector mCallbackBinder;

        DomainSelectorWrapper(DomainSelector cb, Executor executor) {
            this.mCallbackBinder = new IDomainSelectorAdapter(cb, executor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        class IDomainSelectorAdapter extends IDomainSelector.Stub {
            private final WeakReference<DomainSelector> mDomainSelectorWeakRef;
            private final Executor mExecutor;

            IDomainSelectorAdapter(DomainSelector domainSelector, Executor executor) {
                this.mDomainSelectorWeakRef = new WeakReference<>(domainSelector);
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void reselectDomain(final SelectionAttributes attr) {
                final DomainSelector domainSelector = this.mDomainSelectorWeakRef.get();
                if (domainSelector == null) {
                    return;
                }
                DomainSelectionService.this.executeMethodAsyncNoException(
                        this.mExecutor,
                        new Runnable() { // from class:
                                         // android.telephony.DomainSelectionService$DomainSelectorWrapper$IDomainSelectorAdapter$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DomainSelector.this.reselectDomain(attr);
                            }
                        },
                        DomainSelectorWrapper.TAG,
                        "reselectDomain");
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void finishSelection() {
                final DomainSelector domainSelector = this.mDomainSelectorWeakRef.get();
                if (domainSelector == null) {
                    return;
                }
                DomainSelectionService.this.executeMethodAsyncNoException(
                        this.mExecutor,
                        new Runnable() { // from class:
                                         // android.telephony.DomainSelectionService$DomainSelectorWrapper$IDomainSelectorAdapter$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DomainSelector.this.finishSelection();
                            }
                        },
                        DomainSelectorWrapper.TAG,
                        "finishSelection");
            }
        }

        public IDomainSelector getCallbackBinder() {
            return this.mCallbackBinder;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class WwanSelectorCallbackWrapper
            implements WwanSelectorCallback, CancellationSignal.OnCancelListener {
        private static final String TAG = "WwanSelectorCallbackWrapper";
        private final IWwanSelectorCallback mCallback;
        private final Executor mExecutor;
        private IWwanSelectorResultCallbackAdapter mResultCallback;

        WwanSelectorCallbackWrapper(IWwanSelectorCallback cb, Executor executor) {
            this.mCallback = cb;
            this.mExecutor = executor;
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            try {
                this.mCallback.onCancel();
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onCancel e=" + e);
            }
        }

        @Override // android.telephony.WwanSelectorCallback
        public void onRequestEmergencyNetworkScan(
                List<Integer> preferredNetworks,
                int scanType,
                boolean resetScan,
                CancellationSignal signal,
                Consumer<EmergencyRegistrationResult> consumer) {
            if (signal != null) {
                try {
                    signal.setOnCancelListener(this);
                } catch (Exception e) {
                    com.android.telephony.Rlog.e(TAG, "onRequestEmergencyNetworkScan e=" + e);
                    return;
                }
            }
            this.mResultCallback = new IWwanSelectorResultCallbackAdapter(consumer, this.mExecutor);
            this.mCallback.onRequestEmergencyNetworkScan(
                    preferredNetworks.stream()
                            .mapToInt(
                                    new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2())
                            .toArray(),
                    scanType,
                    resetScan,
                    this.mResultCallback);
        }

        @Override // android.telephony.WwanSelectorCallback
        public void onDomainSelected(int domain, boolean useEmergencyPdn) {
            try {
                this.mCallback.onDomainSelected(domain, useEmergencyPdn);
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onDomainSelected e=" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class IWwanSelectorResultCallbackAdapter extends IWwanSelectorResultCallback.Stub {
            private final Consumer<EmergencyRegistrationResult> mConsumer;
            private final Executor mExecutor;

            IWwanSelectorResultCallbackAdapter(
                    Consumer<EmergencyRegistrationResult> consumer, Executor executor) {
                this.mConsumer = consumer;
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.IWwanSelectorResultCallback
            public void onComplete(final EmergencyRegistrationResult result) {
                if (this.mConsumer == null) {
                    return;
                }
                DomainSelectionService.this.executeMethodAsyncNoException(
                        this.mExecutor,
                        new Runnable() { // from class:
                                         // android.telephony.DomainSelectionService$WwanSelectorCallbackWrapper$IWwanSelectorResultCallbackAdapter$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DomainSelectionService.WwanSelectorCallbackWrapper
                                        .IWwanSelectorResultCallbackAdapter.this
                                        .lambda$onComplete$0(result);
                            }
                        },
                        WwanSelectorCallbackWrapper.TAG,
                        "onScanComplete");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onComplete$0(EmergencyRegistrationResult result) {
                this.mConsumer.accept(result);
            }
        }
    }

    public void onServiceStateUpdated(
            int slotIndex, int subscriptionId, ServiceState serviceState) {}

    public void onBarringInfoUpdated(int slotIndex, int subscriptionId, BarringInfo info) {}

    /* renamed from: android.telephony.DomainSelectionService$1, reason: invalid class name */
    class AnonymousClass1 extends IDomainSelectionServiceController.Stub {
        AnonymousClass1() {}

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void selectDomain(
                final SelectionAttributes attr, final ITransportSelectorCallback callback)
                throws RemoteException {
            DomainSelectionService.executeMethodAsync(
                    DomainSelectionService.this.getCachedExecutor(),
                    new Runnable() { // from class:
                                     // android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DomainSelectionService.AnonymousClass1.this.lambda$selectDomain$0(
                                    attr, callback);
                        }
                    },
                    DomainSelectionService.LOG_TAG,
                    "onDomainSelection");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$selectDomain$0(
                SelectionAttributes attr, ITransportSelectorCallback callback) {
            DomainSelectionService.this.onDomainSelection(
                    attr,
                    DomainSelectionService.this
                    .new TransportSelectorCallbackWrapper(
                            callback, DomainSelectionService.this.getCachedExecutor()));
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateServiceState(
                final int slotIndex, final int subscriptionId, final ServiceState serviceState) {
            DomainSelectionService.this.executeMethodAsyncNoException(
                    DomainSelectionService.this.getCachedExecutor(),
                    new Runnable() { // from class:
                                     // android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DomainSelectionService.AnonymousClass1.this.lambda$updateServiceState$1(
                                    slotIndex, subscriptionId, serviceState);
                        }
                    },
                    DomainSelectionService.LOG_TAG,
                    "onServiceStateUpdated");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateServiceState$1(
                int slotIndex, int subscriptionId, ServiceState serviceState) {
            DomainSelectionService.this.onServiceStateUpdated(
                    slotIndex, subscriptionId, serviceState);
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateBarringInfo(
                final int slotIndex, final int subscriptionId, final BarringInfo info) {
            DomainSelectionService.this.executeMethodAsyncNoException(
                    DomainSelectionService.this.getCachedExecutor(),
                    new Runnable() { // from class:
                                     // android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            DomainSelectionService.AnonymousClass1.this.lambda$updateBarringInfo$2(
                                    slotIndex, subscriptionId, info);
                        }
                    },
                    DomainSelectionService.LOG_TAG,
                    "onBarringInfoUpdated");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateBarringInfo$2(
                int slotIndex, int subscriptionId, BarringInfo info) {
            DomainSelectionService.this.onBarringInfoUpdated(slotIndex, subscriptionId, info);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void executeMethodAsync(
            Executor executor, final Runnable r, String tag, String errorLogName)
            throws RemoteException {
        try {
            CompletableFuture.runAsync(
                            new Runnable() { // from class:
                                             // android.telephony.DomainSelectionService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TelephonyUtils.runWithCleanCallingIdentity(r);
                                }
                            },
                            executor)
                    .join();
        } catch (CancellationException | CompletionException e) {
            com.android.telephony.Rlog.w(
                    tag, "Binder - " + errorLogName + " exception: " + e.getMessage());
            throw new RemoteException(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeMethodAsyncNoException(
            Executor executor, final Runnable r, String tag, String errorLogName) {
        try {
            CompletableFuture.runAsync(
                    new Runnable() { // from class:
                                     // android.telephony.DomainSelectionService$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TelephonyUtils.runWithCleanCallingIdentity(r);
                        }
                    },
                    executor);
        } catch (CancellationException | CompletionException e) {
            com.android.telephony.Rlog.w(
                    tag, "Binder - " + errorLogName + " exception: " + e.getMessage());
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (intent == null || !SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        Log.i(LOG_TAG, "DomainSelectionService Bound.");
        return this.mDomainSelectionServiceController;
    }

    public Executor getCreateExecutor() {
        return new PendingIntent$$ExternalSyntheticLambda0();
    }

    public final Executor getCachedExecutor() {
        Executor e;
        synchronized (this.mExecutorLock) {
            if (this.mExecutor == null) {
                Executor e2 = getCreateExecutor();
                this.mExecutor = e2 != null ? e2 : new PendingIntent$$ExternalSyntheticLambda0();
            }
            e = this.mExecutor;
        }
        return e;
    }

    public static String getDomainName(int domain) {
        return NetworkRegistrationInfo.domainToString(domain);
    }
}
