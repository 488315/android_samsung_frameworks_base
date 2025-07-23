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
import android.telephony.DomainSelectionService;
import android.telephony.ims.ImsReasonInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.IDomainSelectionServiceController;
import com.android.internal.telephony.IDomainSelector;
import com.android.internal.telephony.ITransportSelectorCallback;
import com.android.internal.telephony.ITransportSelectorResultCallback;
import com.android.internal.telephony.IWwanSelectorCallback;
import com.android.internal.telephony.IWwanSelectorResultCallback;
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
/* loaded from: classes4.dex */
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
    public @interface EmergencyScanType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectorType {
    }

    public void onBarringInfoUpdated(int i, int i2, BarringInfo barringInfo) {
    }

    public abstract void onDomainSelection(SelectionAttributes selectionAttributes, TransportSelectorCallback transportSelectorCallback);

    public void onServiceStateUpdated(int i, int i2, ServiceState serviceState) {
    }

    public static final class SelectionAttributes implements Parcelable {
        public static final Parcelable.Creator<SelectionAttributes> CREATOR = new Parcelable.Creator<SelectionAttributes>() { // from class: android.telephony.DomainSelectionService.SelectionAttributes.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionAttributes createFromParcel(Parcel parcel) {
                return new SelectionAttributes(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionAttributes[] newArray(int i) {
                return new SelectionAttributes[i];
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private SelectionAttributes(int i, int i2, String str, Uri uri, int i3, boolean z, boolean z2, boolean z3, boolean z4, ImsReasonInfo imsReasonInfo, int i4, EmergencyRegistrationResult emergencyRegistrationResult) {
            this.mSlotIndex = i;
            this.mSubId = i2;
            this.mCallId = str;
            this.mAddress = uri;
            this.mSelectorType = i3;
            this.mIsVideoCall = z;
            this.mIsEmergency = z2;
            this.mIsTestEmergencyNumber = z3;
            this.mIsExitedFromAirplaneMode = z4;
            this.mImsReasonInfo = imsReasonInfo;
            this.mCause = i4;
            this.mEmergencyRegistrationResult = emergencyRegistrationResult;
        }

        public SelectionAttributes(SelectionAttributes selectionAttributes) {
            this.mSlotIndex = selectionAttributes.mSlotIndex;
            this.mSubId = selectionAttributes.mSubId;
            this.mCallId = selectionAttributes.mCallId;
            this.mAddress = selectionAttributes.mAddress;
            this.mSelectorType = selectionAttributes.mSelectorType;
            this.mIsEmergency = selectionAttributes.mIsEmergency;
            this.mIsTestEmergencyNumber = selectionAttributes.mIsTestEmergencyNumber;
            this.mIsExitedFromAirplaneMode = selectionAttributes.mIsExitedFromAirplaneMode;
            this.mImsReasonInfo = selectionAttributes.mImsReasonInfo;
            this.mCause = selectionAttributes.mCause;
            this.mEmergencyRegistrationResult = selectionAttributes.mEmergencyRegistrationResult;
        }

        private SelectionAttributes(Parcel parcel) {
            readFromParcel(parcel);
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
            return "{ slotIndex=" + this.mSlotIndex + ", subId=" + this.mSubId + ", callId=" + this.mCallId + ", address=" + com.android.telephony.Rlog.pii(Build.IS_DEBUGGABLE, this.mAddress) + ", type=" + this.mSelectorType + ", videoCall=" + this.mIsVideoCall + ", emergency=" + this.mIsEmergency + ", isTest=" + this.mIsTestEmergencyNumber + ", airplaneMode=" + this.mIsExitedFromAirplaneMode + ", reasonInfo=" + this.mImsReasonInfo + ", cause=" + this.mCause + ", regResult=" + this.mEmergencyRegistrationResult + " }";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                SelectionAttributes selectionAttributes = (SelectionAttributes) obj;
                if (this.mSlotIndex == selectionAttributes.mSlotIndex && this.mSubId == selectionAttributes.mSubId && TextUtils.equals(this.mCallId, selectionAttributes.mCallId) && equalsHandlesNulls(this.mAddress, selectionAttributes.mAddress) && this.mSelectorType == selectionAttributes.mSelectorType && this.mIsVideoCall == selectionAttributes.mIsVideoCall && this.mIsEmergency == selectionAttributes.mIsEmergency && this.mIsTestEmergencyNumber == selectionAttributes.mIsTestEmergencyNumber && this.mIsExitedFromAirplaneMode == selectionAttributes.mIsExitedFromAirplaneMode && equalsHandlesNulls(this.mImsReasonInfo, selectionAttributes.mImsReasonInfo) && this.mCause == selectionAttributes.mCause && equalsHandlesNulls(this.mEmergencyRegistrationResult, selectionAttributes.mEmergencyRegistrationResult)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mCallId, this.mAddress, this.mImsReasonInfo, Boolean.valueOf(this.mIsVideoCall), Boolean.valueOf(this.mIsEmergency), Boolean.valueOf(this.mIsTestEmergencyNumber), Boolean.valueOf(this.mIsExitedFromAirplaneMode), this.mEmergencyRegistrationResult, Integer.valueOf(this.mSlotIndex), Integer.valueOf(this.mSubId), Integer.valueOf(this.mSelectorType), Integer.valueOf(this.mCause));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mSlotIndex);
            parcel.writeInt(this.mSubId);
            parcel.writeString8(this.mCallId);
            parcel.writeParcelable(this.mAddress, 0);
            parcel.writeInt(this.mSelectorType);
            parcel.writeBoolean(this.mIsVideoCall);
            parcel.writeBoolean(this.mIsEmergency);
            parcel.writeBoolean(this.mIsTestEmergencyNumber);
            parcel.writeBoolean(this.mIsExitedFromAirplaneMode);
            parcel.writeParcelable(this.mImsReasonInfo, 0);
            parcel.writeInt(this.mCause);
            parcel.writeParcelable(this.mEmergencyRegistrationResult, 0);
        }

        private void readFromParcel(Parcel parcel) {
            this.mSlotIndex = parcel.readInt();
            this.mSubId = parcel.readInt();
            this.mCallId = parcel.readString8();
            this.mAddress = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
            this.mSelectorType = parcel.readInt();
            this.mIsVideoCall = parcel.readBoolean();
            this.mIsEmergency = parcel.readBoolean();
            this.mIsTestEmergencyNumber = parcel.readBoolean();
            this.mIsExitedFromAirplaneMode = parcel.readBoolean();
            this.mImsReasonInfo = (ImsReasonInfo) parcel.readParcelable(ImsReasonInfo.class.getClassLoader(), ImsReasonInfo.class);
            this.mCause = parcel.readInt();
            this.mEmergencyRegistrationResult = (EmergencyRegistrationResult) parcel.readParcelable(EmergencyRegistrationResult.class.getClassLoader(), EmergencyRegistrationResult.class);
        }

        private static boolean equalsHandlesNulls(Object obj, Object obj2) {
            if (obj == null) {
                return obj2 == null;
            }
            return obj.equals(obj2);
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

            public Builder(int i, int i2, int i3) {
                this.mSlotIndex = i;
                this.mSubId = i2;
                this.mSelectorType = i3;
            }

            public Builder setCallId(String str) {
                this.mCallId = str;
                return this;
            }

            public Builder setAddress(Uri uri) {
                this.mAddress = uri;
                return this;
            }

            public Builder setVideoCall(boolean z) {
                this.mIsVideoCall = z;
                return this;
            }

            public Builder setEmergency(boolean z) {
                this.mIsEmergency = z;
                return this;
            }

            public Builder setTestEmergencyNumber(boolean z) {
                this.mIsTestEmergencyNumber = z;
                return this;
            }

            public Builder setExitedFromAirplaneMode(boolean z) {
                this.mIsExitedFromAirplaneMode = z;
                return this;
            }

            public Builder setPsDisconnectCause(ImsReasonInfo imsReasonInfo) {
                this.mImsReasonInfo = imsReasonInfo;
                return this;
            }

            public Builder setCsDisconnectCause(int i) {
                this.mCause = i;
                return this;
            }

            public Builder setEmergencyRegistrationResult(EmergencyRegistrationResult emergencyRegistrationResult) {
                this.mEmergencyRegistrationResult = emergencyRegistrationResult;
                return this;
            }

            public SelectionAttributes build() {
                return new SelectionAttributes(this.mSlotIndex, this.mSubId, this.mCallId, this.mAddress, this.mSelectorType, this.mIsVideoCall, this.mIsEmergency, this.mIsTestEmergencyNumber, this.mIsExitedFromAirplaneMode, this.mImsReasonInfo, this.mCause, this.mEmergencyRegistrationResult);
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

        TransportSelectorCallbackWrapper(ITransportSelectorCallback iTransportSelectorCallback, Executor executor) {
            this.mCallback = iTransportSelectorCallback;
            this.mExecutor = executor;
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onCreated(DomainSelector domainSelector) {
            try {
                DomainSelectorWrapper domainSelectorWrapper = DomainSelectionService.this.new DomainSelectorWrapper(domainSelector, this.mExecutor);
                this.mSelectorWrapper = domainSelectorWrapper;
                this.mCallback.onCreated(domainSelectorWrapper.getCallbackBinder());
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onCreated e=" + e);
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onWlanSelected(boolean z) {
            try {
                this.mCallback.onWlanSelected(z);
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onWlanSelected e=" + e);
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onWwanSelected(final Consumer<WwanSelectorCallback> consumer) {
            try {
                ITransportSelectorResultCallbackAdapter iTransportSelectorResultCallbackAdapter = new ITransportSelectorResultCallbackAdapter(consumer, this.mExecutor);
                this.mResultCallback = iTransportSelectorResultCallbackAdapter;
                this.mCallback.onWwanSelectedAsync(iTransportSelectorResultCallbackAdapter);
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onWwanSelected e=" + e);
                DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new Runnable() { // from class: android.telephony.DomainSelectionService$TransportSelectorCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(null);
                    }
                }, TAG, "onWwanSelectedAsync-Exception");
            }
        }

        @Override // android.telephony.TransportSelectorCallback
        public void onSelectionTerminated(int i) {
            try {
                this.mCallback.onSelectionTerminated(i);
                this.mSelectorWrapper = null;
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onSelectionTerminated e=" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class ITransportSelectorResultCallbackAdapter extends ITransportSelectorResultCallback.Stub {
            private final Consumer<WwanSelectorCallback> mConsumer;
            private final Executor mExecutor;

            ITransportSelectorResultCallbackAdapter(Consumer<WwanSelectorCallback> consumer, Executor executor) {
                this.mConsumer = consumer;
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.ITransportSelectorResultCallback
            public void onCompleted(IWwanSelectorCallback iWwanSelectorCallback) {
                if (this.mConsumer == null) {
                    return;
                }
                final WwanSelectorCallbackWrapper wwanSelectorCallbackWrapper = DomainSelectionService.this.new WwanSelectorCallbackWrapper(iWwanSelectorCallback, this.mExecutor);
                DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new Runnable() { // from class: android.telephony.DomainSelectionService$TransportSelectorCallbackWrapper$ITransportSelectorResultCallbackAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DomainSelectionService.TransportSelectorCallbackWrapper.ITransportSelectorResultCallbackAdapter.this.lambda$onCompleted$0(wwanSelectorCallbackWrapper);
                    }
                }, TransportSelectorCallbackWrapper.TAG, "onWwanSelectedAsync-Completed");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCompleted$0(WwanSelectorCallback wwanSelectorCallback) {
                this.mConsumer.accept(wwanSelectorCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DomainSelectorWrapper {
        private static final String TAG = "DomainSelectorWrapper";
        private IDomainSelector mCallbackBinder;

        DomainSelectorWrapper(DomainSelector domainSelector, Executor executor) {
            this.mCallbackBinder = new IDomainSelectorAdapter(domainSelector, executor);
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
            public void reselectDomain(final SelectionAttributes selectionAttributes) {
                final DomainSelector domainSelector = this.mDomainSelectorWeakRef.get();
                if (domainSelector == null) {
                    return;
                }
                DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new Runnable() { // from class: android.telephony.DomainSelectionService$DomainSelectorWrapper$IDomainSelectorAdapter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DomainSelector.this.reselectDomain(selectionAttributes);
                    }
                }, DomainSelectorWrapper.TAG, "reselectDomain");
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void finishSelection() {
                final DomainSelector domainSelector = this.mDomainSelectorWeakRef.get();
                if (domainSelector == null) {
                    return;
                }
                DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new Runnable() { // from class: android.telephony.DomainSelectionService$DomainSelectorWrapper$IDomainSelectorAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DomainSelector.this.finishSelection();
                    }
                }, DomainSelectorWrapper.TAG, "finishSelection");
            }
        }

        public IDomainSelector getCallbackBinder() {
            return this.mCallbackBinder;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class WwanSelectorCallbackWrapper implements WwanSelectorCallback, CancellationSignal.OnCancelListener {
        private static final String TAG = "WwanSelectorCallbackWrapper";
        private final IWwanSelectorCallback mCallback;
        private final Executor mExecutor;
        private IWwanSelectorResultCallbackAdapter mResultCallback;

        WwanSelectorCallbackWrapper(IWwanSelectorCallback iWwanSelectorCallback, Executor executor) {
            this.mCallback = iWwanSelectorCallback;
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
        public void onRequestEmergencyNetworkScan(List<Integer> list, int i, boolean z, CancellationSignal cancellationSignal, Consumer<EmergencyRegistrationResult> consumer) {
            if (cancellationSignal != null) {
                try {
                    cancellationSignal.setOnCancelListener(this);
                } catch (Exception e) {
                    com.android.telephony.Rlog.e(TAG, "onRequestEmergencyNetworkScan e=" + e);
                    return;
                }
            }
            this.mResultCallback = new IWwanSelectorResultCallbackAdapter(consumer, this.mExecutor);
            this.mCallback.onRequestEmergencyNetworkScan(list.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray(), i, z, this.mResultCallback);
        }

        @Override // android.telephony.WwanSelectorCallback
        public void onDomainSelected(int i, boolean z) {
            try {
                this.mCallback.onDomainSelected(i, z);
            } catch (Exception e) {
                com.android.telephony.Rlog.e(TAG, "onDomainSelected e=" + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class IWwanSelectorResultCallbackAdapter extends IWwanSelectorResultCallback.Stub {
            private final Consumer<EmergencyRegistrationResult> mConsumer;
            private final Executor mExecutor;

            IWwanSelectorResultCallbackAdapter(Consumer<EmergencyRegistrationResult> consumer, Executor executor) {
                this.mConsumer = consumer;
                this.mExecutor = executor;
            }

            @Override // com.android.internal.telephony.IWwanSelectorResultCallback
            public void onComplete(final EmergencyRegistrationResult emergencyRegistrationResult) {
                if (this.mConsumer == null) {
                    return;
                }
                DomainSelectionService.this.executeMethodAsyncNoException(this.mExecutor, new Runnable() { // from class: android.telephony.DomainSelectionService$WwanSelectorCallbackWrapper$IWwanSelectorResultCallbackAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DomainSelectionService.WwanSelectorCallbackWrapper.IWwanSelectorResultCallbackAdapter.this.lambda$onComplete$0(emergencyRegistrationResult);
                    }
                }, WwanSelectorCallbackWrapper.TAG, "onScanComplete");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onComplete$0(EmergencyRegistrationResult emergencyRegistrationResult) {
                this.mConsumer.accept(emergencyRegistrationResult);
            }
        }
    }

    /* renamed from: android.telephony.DomainSelectionService$1, reason: invalid class name */
    class AnonymousClass1 extends IDomainSelectionServiceController.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void selectDomain(final SelectionAttributes selectionAttributes, final ITransportSelectorCallback iTransportSelectorCallback) throws RemoteException {
            DomainSelectionService.executeMethodAsync(DomainSelectionService.this.getCachedExecutor(), new Runnable() { // from class: android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DomainSelectionService.AnonymousClass1.this.lambda$selectDomain$0(selectionAttributes, iTransportSelectorCallback);
                }
            }, DomainSelectionService.LOG_TAG, "onDomainSelection");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$selectDomain$0(SelectionAttributes selectionAttributes, ITransportSelectorCallback iTransportSelectorCallback) {
            DomainSelectionService domainSelectionService = DomainSelectionService.this;
            DomainSelectionService domainSelectionService2 = DomainSelectionService.this;
            domainSelectionService.onDomainSelection(selectionAttributes, domainSelectionService2.new TransportSelectorCallbackWrapper(iTransportSelectorCallback, domainSelectionService2.getCachedExecutor()));
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateServiceState(final int i, final int i2, final ServiceState serviceState) {
            DomainSelectionService domainSelectionService = DomainSelectionService.this;
            domainSelectionService.executeMethodAsyncNoException(domainSelectionService.getCachedExecutor(), new Runnable() { // from class: android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DomainSelectionService.AnonymousClass1.this.lambda$updateServiceState$1(i, i2, serviceState);
                }
            }, DomainSelectionService.LOG_TAG, "onServiceStateUpdated");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateServiceState$1(int i, int i2, ServiceState serviceState) {
            DomainSelectionService.this.onServiceStateUpdated(i, i2, serviceState);
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateBarringInfo(final int i, final int i2, final BarringInfo barringInfo) {
            DomainSelectionService domainSelectionService = DomainSelectionService.this;
            domainSelectionService.executeMethodAsyncNoException(domainSelectionService.getCachedExecutor(), new Runnable() { // from class: android.telephony.DomainSelectionService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    DomainSelectionService.AnonymousClass1.this.lambda$updateBarringInfo$2(i, i2, barringInfo);
                }
            }, DomainSelectionService.LOG_TAG, "onBarringInfoUpdated");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateBarringInfo$2(int i, int i2, BarringInfo barringInfo) {
            DomainSelectionService.this.onBarringInfoUpdated(i, i2, barringInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void executeMethodAsync(Executor executor, final Runnable runnable, String str, String str2) throws RemoteException {
        try {
            CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.DomainSelectionService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyUtils.runWithCleanCallingIdentity(runnable);
                }
            }, executor).join();
        } catch (CancellationException | CompletionException e) {
            com.android.telephony.Rlog.w(str, "Binder - " + str2 + " exception: " + e.getMessage());
            throw new RemoteException(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeMethodAsyncNoException(Executor executor, final Runnable runnable, String str, String str2) {
        try {
            CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.DomainSelectionService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyUtils.runWithCleanCallingIdentity(runnable);
                }
            }, executor);
        } catch (CancellationException | CompletionException e) {
            com.android.telephony.Rlog.w(str, "Binder - " + str2 + " exception: " + e.getMessage());
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
        Executor executor;
        synchronized (this.mExecutorLock) {
            if (this.mExecutor == null) {
                Executor createExecutor = getCreateExecutor();
                if (createExecutor == null) {
                    createExecutor = new PendingIntent$$ExternalSyntheticLambda0();
                }
                this.mExecutor = createExecutor;
            }
            executor = this.mExecutor;
        }
        return executor;
    }

    public static String getDomainName(int i) {
        return NetworkRegistrationInfo.domainToString(i);
    }
}
