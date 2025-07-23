package android.telephony;

import android.annotation.SystemApi;
import android.os.Binder;
import android.telephony.TelephonyCallback;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.util.FunctionalUtils;
import dalvik.system.VMRuntime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class TelephonyCallback {
    public static final int DEFAULT_PER_PID_REGISTRATION_LIMIT = 50;

    @SystemApi
    public static final int EVENT_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGED = 23;

    @SystemApi
    public static final int EVENT_ALLOWED_NETWORK_TYPE_LIST_CHANGED = 35;

    @SystemApi
    public static final int EVENT_ALWAYS_REPORTED_SIGNAL_STRENGTH_CHANGED = 10;

    @SystemApi
    public static final int EVENT_BARRING_INFO_CHANGED = 32;

    @SystemApi
    public static final int EVENT_CALL_ATTRIBUTES_CHANGED = 27;

    @SystemApi
    public static final int EVENT_CALL_DISCONNECT_CAUSE_CHANGED = 26;

    @SystemApi
    public static final int EVENT_CALL_FORWARDING_INDICATOR_CHANGED = 4;

    @SystemApi
    public static final int EVENT_CALL_STATE_CHANGED = 6;

    @SystemApi
    public static final int EVENT_CARRIER_NETWORK_CHANGED = 17;

    @SystemApi
    public static final int EVENT_CARRIER_ROAMING_NTN_AVAILABLE_SERVICES_CHANGED = 44;

    @SystemApi
    public static final int EVENT_CARRIER_ROAMING_NTN_ELIGIBLE_STATE_CHANGED = 43;

    @SystemApi
    public static final int EVENT_CARRIER_ROAMING_NTN_MODE_CHANGED = 42;

    @SystemApi
    public static final int EVENT_CARRIER_ROAMING_NTN_SIGNAL_STRENGTH_CHANGED = 45;

    @SystemApi
    public static final int EVENT_CELLULAR_IDENTIFIER_DISCLOSED_CHANGED = 47;

    @SystemApi
    public static final int EVENT_CELL_INFO_CHANGED = 11;

    @SystemApi
    public static final int EVENT_CELL_LOCATION_CHANGED = 5;
    public static final int EVENT_CPAI_DATA_GATHERING_NOTIFIED = 11002;
    public static final int EVENT_CPAI_DEV_APP_MESSAGE_NOTIFIED = 11003;
    public static final int EVENT_CPAI_FEATURE_INFO_NOTIFIED = 11001;
    public static final int EVENT_CPAI_MODEL_UPDATE_NOTIFIED = 11000;

    @SystemApi
    public static final int EVENT_DATA_ACTIVATION_STATE_CHANGED = 19;

    @SystemApi
    public static final int EVENT_DATA_ACTIVITY_CHANGED = 8;

    @SystemApi
    @Deprecated
    public static final int EVENT_DATA_CONNECTION_REAL_TIME_INFO_CHANGED = 14;

    @SystemApi
    public static final int EVENT_DATA_CONNECTION_STATE_CHANGED = 7;

    @SystemApi
    public static final int EVENT_DATA_ENABLED_CHANGED = 34;

    @SystemApi
    public static final int EVENT_DISPLAY_INFO_CHANGED = 21;

    @SystemApi
    public static final int EVENT_EMERGENCY_CALLBACK_MODE_CHANGED = 40;

    @SystemApi
    public static final int EVENT_EMERGENCY_NUMBER_LIST_CHANGED = 25;

    @SystemApi
    public static final int EVENT_IMS_CALL_DISCONNECT_CAUSE_CHANGED = 28;

    @SystemApi
    public static final int EVENT_LEGACY_CALL_STATE_CHANGED = 36;

    @SystemApi
    public static final int EVENT_LINK_CAPACITY_ESTIMATE_CHANGED = 37;

    @SystemApi
    public static final int EVENT_MEDIA_QUALITY_STATUS_CHANGED = 39;

    @SystemApi
    public static final int EVENT_MESSAGE_WAITING_INDICATOR_CHANGED = 3;

    @SystemApi
    public static final int EVENT_OEM_HOOK_RAW = 15;

    @SystemApi
    public static final int EVENT_OUTGOING_EMERGENCY_CALL = 29;

    @SystemApi
    public static final int EVENT_OUTGOING_EMERGENCY_SMS = 30;

    @SystemApi
    public static final int EVENT_PHONE_CAPABILITY_CHANGED = 22;

    @SystemApi
    public static final int EVENT_PHYSICAL_CHANNEL_CONFIG_CHANGED = 33;

    @SystemApi
    public static final int EVENT_PRECISE_CALL_STATE_CHANGED = 12;

    @SystemApi
    public static final int EVENT_PRECISE_DATA_CONNECTION_STATE_CHANGED = 13;

    @SystemApi
    public static final int EVENT_RADIO_POWER_STATE_CHANGED = 24;

    @SystemApi
    public static final int EVENT_REGISTRATION_FAILURE = 31;

    @SystemApi
    public static final int EVENT_SECURITY_ALGORITHMS_CHANGED = 46;
    public static final int EVENT_SEM_SATELLITE_SERVICE_STATE_CHANGED = 10000;
    public static final int EVENT_SEM_SATELLITE_SIGNAL_STRENGTH_CHANGED = 10001;

    @SystemApi
    public static final int EVENT_SERVICE_STATE_CHANGED = 1;

    @SystemApi
    public static final int EVENT_SIGNAL_STRENGTHS_CHANGED = 9;

    @SystemApi
    public static final int EVENT_SIGNAL_STRENGTH_CHANGED = 2;

    @SystemApi
    public static final int EVENT_SIMULTANEOUS_CELLULAR_CALLING_SUBSCRIPTIONS_CHANGED = 41;

    @SystemApi
    public static final int EVENT_SRVCC_STATE_CHANGED = 16;
    public static final int EVENT_TRIGGER_NOTIFY_ANBR = 38;

    @SystemApi
    public static final int EVENT_USER_MOBILE_DATA_STATE_CHANGED = 20;

    @SystemApi
    public static final int EVENT_VOICE_ACTIVATION_STATE_CHANGED = 18;
    public static final String FLAG_PER_PID_REGISTRATION_LIMIT = "phone_state_listener_per_pid_registration_limit";
    private static final String LOG_TAG = "TelephonyCallback";
    public static final long PHONE_STATE_LISTENER_LIMIT_CHANGE_ID = 150880553;
    public IPhoneStateListener callback;

    public interface ActiveDataSubscriptionIdListener {
        void onActiveDataSubscriptionIdChanged(int i);
    }

    @SystemApi
    public interface AllowedNetworkTypesListener {
        void onAllowedNetworkTypesChanged(int i, long j);
    }

    public interface BarringInfoListener {
        void onBarringInfoChanged(BarringInfo barringInfo);
    }

    public interface CallDisconnectCauseListener {
        void onCallDisconnectCauseChanged(int i, int i2);
    }

    public interface CallForwardingIndicatorListener {
        void onCallForwardingIndicatorChanged(boolean z);
    }

    public interface CallStateListener {
        void onCallStateChanged(int i);
    }

    public interface CarrierNetworkListener {
        void onCarrierNetworkChange(boolean z);
    }

    @SystemApi
    public interface CarrierRoamingNtnListener {
        default void onCarrierRoamingNtnAvailableServicesChanged(int[] iArr) {
        }

        default void onCarrierRoamingNtnEligibleStateChanged(boolean z) {
        }

        void onCarrierRoamingNtnModeChanged(boolean z);

        default void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
        }
    }

    public interface CellInfoListener {
        void onCellInfoChanged(List<CellInfo> list);
    }

    public interface CellLocationListener {
        void onCellLocationChanged(CellLocation cellLocation);
    }

    @SystemApi
    public interface CellularIdentifierDisclosedListener {
        void onCellularIdentifierDisclosedChanged(CellularIdentifierDisclosure cellularIdentifierDisclosure);
    }

    public interface CpaiDataGatheringListener {
        void onCpaiDataGatheringNotified(int i, int i2, byte[] bArr);
    }

    public interface CpaiDevAppMessageListener {
        void onCpaiDevAppMessageNotified(int i, int i2, int i3, byte[] bArr);
    }

    public interface CpaiFeatureInforListener {
        void onCpaiFeatureInfoNotified(int i, int i2);
    }

    public interface CpaiModelUpdateListener {
        void onCpaiModelUpdateNotified(int i, int i2);
    }

    public interface DataActivationStateListener {
        void onDataActivationStateChanged(int i);
    }

    public interface DataActivityListener {
        void onDataActivity(int i);
    }

    public interface DataConnectionStateListener {
        void onDataConnectionStateChanged(int i, int i2);
    }

    @SystemApi
    public interface DataEnabledListener {
        void onDataEnabledChanged(boolean z, int i);
    }

    public interface DisplayInfoListener {
        void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo);
    }

    @SystemApi
    public interface EmergencyCallbackModeListener {
        void onCallbackModeRestarted(int i, Duration duration, int i2);

        void onCallbackModeStarted(int i, Duration duration, int i2);

        void onCallbackModeStopped(int i, int i2, int i3);
    }

    public interface EmergencyNumberListListener {
        void onEmergencyNumberListChanged(Map<Integer, List<EmergencyNumber>> map);
    }

    public interface ImsCallDisconnectCauseListener {
        void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo);
    }

    @SystemApi
    public interface LinkCapacityEstimateChangedListener {
        void onLinkCapacityEstimateChanged(List<LinkCapacityEstimate> list);
    }

    @SystemApi
    public interface MediaQualityStatusChangedListener {
        void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus);
    }

    public interface MessageWaitingIndicatorListener {
        void onMessageWaitingIndicatorChanged(boolean z);
    }

    @SystemApi
    public interface OutgoingEmergencyCallListener {
        void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i);
    }

    @SystemApi
    public interface OutgoingEmergencySmsListener {
        void onOutgoingEmergencySms(EmergencyNumber emergencyNumber, int i);
    }

    @SystemApi
    public interface PhoneCapabilityListener {
        void onPhoneCapabilityChanged(PhoneCapability phoneCapability);
    }

    public interface PhysicalChannelConfigListener {
        void onPhysicalChannelConfigChanged(List<PhysicalChannelConfig> list);
    }

    @SystemApi
    public interface PreciseCallStateListener {
        void onPreciseCallStateChanged(PreciseCallState preciseCallState);
    }

    public interface PreciseDataConnectionStateListener {
        void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState);
    }

    @SystemApi
    public interface RadioPowerStateListener {
        void onRadioPowerStateChanged(int i);
    }

    public interface RegistrationFailedListener {
        void onRegistrationFailed(CellIdentity cellIdentity, String str, int i, int i2, int i3);
    }

    @SystemApi
    public interface SecurityAlgorithmsListener {
        void onSecurityAlgorithmsChanged(SecurityAlgorithmUpdate securityAlgorithmUpdate);
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public interface SemSatelliteStateListener {
        default void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) {
        }

        default void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) {
        }
    }

    public interface ServiceStateListener {
        void onServiceStateChanged(ServiceState serviceState);
    }

    public interface SignalStrengthsListener {
        void onSignalStrengthsChanged(SignalStrength signalStrength);
    }

    @SystemApi
    public interface SimultaneousCellularCallingSupportListener {
        void onSimultaneousCellularCallingSubscriptionsChanged(Set<Integer> set);
    }

    @SystemApi
    public interface SrvccStateListener {
        void onSrvccStateChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TelephonyEvent {
    }

    public interface UserMobileDataStateListener {
        void onUserMobileDataStateChanged(boolean z);
    }

    @SystemApi
    public interface VoiceActivationStateListener {
        void onVoiceActivationStateChanged(int i);
    }

    public void init(Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("TelephonyCallback Executor must be non-null");
        }
        this.callback = new IPhoneStateListenerStub(this, executor);
    }

    @SystemApi
    public interface CallAttributesListener {
        @Deprecated
        default void onCallAttributesChanged(CallAttributes callAttributes) {
            Log.w(TelephonyCallback.LOG_TAG, "onCallAttributesChanged(List<CallState>) should be overridden.");
        }

        default void onCallStatesChanged(List<CallState> list) {
            if (list.size() > 0) {
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                for (CallState callState : list) {
                    int callClassification = callState.getCallClassification();
                    if (callClassification == 0) {
                        i = callState.getCallState();
                    } else if (callClassification == 1) {
                        i2 = callState.getCallState();
                    } else if (callClassification == 2) {
                        i3 = callState.getCallState();
                    }
                }
                onCallAttributesChanged(new CallAttributes(new PreciseCallState(i, i2, i3, -1, -1), list.get(0).getNetworkType(), list.get(0).getCallQuality()));
                return;
            }
            onCallAttributesChanged(new CallAttributes(new PreciseCallState(0, 0, 0, -1, -1), 0, new CallQuality()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IPhoneStateListenerStub extends IPhoneStateListener.Stub {
        private Executor mExecutor;
        private WeakReference<TelephonyCallback> mTelephonyCallbackWeakRef;

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(int i, String str) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(byte[] bArr) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(int i) {
        }

        IPhoneStateListenerStub(TelephonyCallback telephonyCallback, Executor executor) {
            this.mTelephonyCallbackWeakRef = new WeakReference<>(telephonyCallback);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(final ServiceState serviceState) {
            final ServiceStateListener serviceStateListener = (ServiceStateListener) this.mTelephonyCallbackWeakRef.get();
            if (serviceStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda66
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onServiceStateChanged$1(serviceStateListener, serviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceStateChanged$1(final ServiceStateListener serviceStateListener, final ServiceState serviceState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda90
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.ServiceStateListener.this.onServiceStateChanged(serviceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(final boolean z) {
            final MessageWaitingIndicatorListener messageWaitingIndicatorListener = (MessageWaitingIndicatorListener) this.mTelephonyCallbackWeakRef.get();
            if (messageWaitingIndicatorListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda25
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onMessageWaitingIndicatorChanged$3(messageWaitingIndicatorListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$3(final MessageWaitingIndicatorListener messageWaitingIndicatorListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.MessageWaitingIndicatorListener.this.onMessageWaitingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(final boolean z) {
            final CallForwardingIndicatorListener callForwardingIndicatorListener = (CallForwardingIndicatorListener) this.mTelephonyCallbackWeakRef.get();
            if (callForwardingIndicatorListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda13
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallForwardingIndicatorChanged$5(callForwardingIndicatorListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallForwardingIndicatorChanged$5(final CallForwardingIndicatorListener callForwardingIndicatorListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallForwardingIndicatorListener.this.onCallForwardingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(CellIdentity cellIdentity) {
            final CellLocation empty = cellIdentity == null ? CellLocation.getEmpty() : cellIdentity.asCellLocation();
            final CellLocationListener cellLocationListener = (CellLocationListener) this.mTelephonyCallbackWeakRef.get();
            if (cellLocationListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda70
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellLocationChanged$7(cellLocationListener, empty);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellLocationChanged$7(final CellLocationListener cellLocationListener, final CellLocation cellLocation) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CellLocationListener.this.onCellLocationChanged(cellLocation);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(final int i) {
            final CallStateListener callStateListener = (CallStateListener) this.mTelephonyCallbackWeakRef.get();
            if (callStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda89
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallStateChanged$9(callStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStateChanged$9(final CallStateListener callStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda65
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallStateListener.this.onCallStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(final int i, final int i2) {
            final DataConnectionStateListener dataConnectionStateListener = (DataConnectionStateListener) this.mTelephonyCallbackWeakRef.get();
            if (dataConnectionStateListener == null) {
                return;
            }
            if (i == 4 && VMRuntime.getRuntime().getTargetSdkVersion() < 30) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda51
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$11(dataConnectionStateListener, i2);
                    }
                });
            } else {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda52
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataConnectionStateChanged$13(dataConnectionStateListener, i, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$11(final DataConnectionStateListener dataConnectionStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataConnectionStateListener.this.onDataConnectionStateChanged(2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$13(final DataConnectionStateListener dataConnectionStateListener, final int i, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataConnectionStateListener.this.onDataConnectionStateChanged(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(final int i) {
            final DataActivityListener dataActivityListener = (DataActivityListener) this.mTelephonyCallbackWeakRef.get();
            if (dataActivityListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda23
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataActivity$15(dataActivityListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivity$15(final DataActivityListener dataActivityListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataActivityListener.this.onDataActivity(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(final SignalStrength signalStrength) {
            final SignalStrengthsListener signalStrengthsListener = (SignalStrengthsListener) this.mTelephonyCallbackWeakRef.get();
            if (signalStrengthsListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda49
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSignalStrengthsChanged$17(signalStrengthsListener, signalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthsChanged$17(final SignalStrengthsListener signalStrengthsListener, final SignalStrength signalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SignalStrengthsListener.this.onSignalStrengthsChanged(signalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(final List<CellInfo> list) {
            final CellInfoListener cellInfoListener = (CellInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (cellInfoListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda73
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellInfoChanged$19(cellInfoListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellInfoChanged$19(final CellInfoListener cellInfoListener, final List list) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CellInfoListener.this.onCellInfoChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(final PreciseCallState preciseCallState) {
            final PreciseCallStateListener preciseCallStateListener = (PreciseCallStateListener) this.mTelephonyCallbackWeakRef.get();
            if (preciseCallStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda22
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPreciseCallStateChanged$21(preciseCallStateListener, preciseCallState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseCallStateChanged$21(final PreciseCallStateListener preciseCallStateListener, final PreciseCallState preciseCallState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda72
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PreciseCallStateListener.this.onPreciseCallStateChanged(preciseCallState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(final int i, final int i2) {
            final CallDisconnectCauseListener callDisconnectCauseListener = (CallDisconnectCauseListener) this.mTelephonyCallbackWeakRef.get();
            if (callDisconnectCauseListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallDisconnectCauseChanged$23(callDisconnectCauseListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallDisconnectCauseChanged$23(final CallDisconnectCauseListener callDisconnectCauseListener, final int i, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallDisconnectCauseListener.this.onCallDisconnectCauseChanged(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(final PreciseDataConnectionState preciseDataConnectionState) {
            final PreciseDataConnectionStateListener preciseDataConnectionStateListener = (PreciseDataConnectionStateListener) this.mTelephonyCallbackWeakRef.get();
            if (preciseDataConnectionStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda68
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPreciseDataConnectionStateChanged$25(preciseDataConnectionStateListener, preciseDataConnectionState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$25(final PreciseDataConnectionStateListener preciseDataConnectionStateListener, final PreciseDataConnectionState preciseDataConnectionState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PreciseDataConnectionStateListener.this.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(final int i) {
            final SrvccStateListener srvccStateListener = (SrvccStateListener) this.mTelephonyCallbackWeakRef.get();
            if (srvccStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda81
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSrvccStateChanged$27(srvccStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSrvccStateChanged$27(final SrvccStateListener srvccStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda77
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SrvccStateListener.this.onSrvccStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(final int i) {
            final VoiceActivationStateListener voiceActivationStateListener = (VoiceActivationStateListener) this.mTelephonyCallbackWeakRef.get();
            if (voiceActivationStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda17
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onVoiceActivationStateChanged$29(voiceActivationStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVoiceActivationStateChanged$29(final VoiceActivationStateListener voiceActivationStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.VoiceActivationStateListener.this.onVoiceActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(final int i) {
            final DataActivationStateListener dataActivationStateListener = (DataActivationStateListener) this.mTelephonyCallbackWeakRef.get();
            if (dataActivationStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda64
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataActivationStateChanged$31(dataActivationStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivationStateChanged$31(final DataActivationStateListener dataActivationStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda98
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataActivationStateListener.this.onDataActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(final boolean z) {
            final UserMobileDataStateListener userMobileDataStateListener = (UserMobileDataStateListener) this.mTelephonyCallbackWeakRef.get();
            if (userMobileDataStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda18
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onUserMobileDataStateChanged$33(userMobileDataStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserMobileDataStateChanged$33(final UserMobileDataStateListener userMobileDataStateListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.UserMobileDataStateListener.this.onUserMobileDataStateChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(final TelephonyDisplayInfo telephonyDisplayInfo) {
            final DisplayInfoListener displayInfoListener = (DisplayInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (displayInfoListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda50
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDisplayInfoChanged$35(displayInfoListener, telephonyDisplayInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayInfoChanged$35(final DisplayInfoListener displayInfoListener, final TelephonyDisplayInfo telephonyDisplayInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda46
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DisplayInfoListener.this.onDisplayInfoChanged(telephonyDisplayInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(final boolean z) {
            final CarrierNetworkListener carrierNetworkListener = (CarrierNetworkListener) this.mTelephonyCallbackWeakRef.get();
            if (carrierNetworkListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda84
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierNetworkChange$37(carrierNetworkListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierNetworkChange$37(final CarrierNetworkListener carrierNetworkListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierNetworkListener.this.onCarrierNetworkChange(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(final Map map) {
            final EmergencyNumberListListener emergencyNumberListListener = (EmergencyNumberListListener) this.mTelephonyCallbackWeakRef.get();
            if (emergencyNumberListListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda57
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onEmergencyNumberListChanged$39(emergencyNumberListListener, map);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyNumberListChanged$39(final EmergencyNumberListListener emergencyNumberListListener, final Map map) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda59
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyNumberListListener.this.onEmergencyNumberListChanged(map);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(final EmergencyNumber emergencyNumber, final int i) {
            final OutgoingEmergencyCallListener outgoingEmergencyCallListener = (OutgoingEmergencyCallListener) this.mTelephonyCallbackWeakRef.get();
            if (outgoingEmergencyCallListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda48
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencyCall$41(outgoingEmergencyCallListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencyCall$41(final OutgoingEmergencyCallListener outgoingEmergencyCallListener, final EmergencyNumber emergencyNumber, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda79
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.OutgoingEmergencyCallListener.this.onOutgoingEmergencyCall(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(final EmergencyNumber emergencyNumber, final int i) {
            final OutgoingEmergencySmsListener outgoingEmergencySmsListener = (OutgoingEmergencySmsListener) this.mTelephonyCallbackWeakRef.get();
            if (outgoingEmergencySmsListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda67
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onOutgoingEmergencySms$43(outgoingEmergencySmsListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencySms$43(final OutgoingEmergencySmsListener outgoingEmergencySmsListener, final EmergencyNumber emergencyNumber, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.OutgoingEmergencySmsListener.this.onOutgoingEmergencySms(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(final PhoneCapability phoneCapability) {
            final PhoneCapabilityListener phoneCapabilityListener = (PhoneCapabilityListener) this.mTelephonyCallbackWeakRef.get();
            if (phoneCapabilityListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda94
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPhoneCapabilityChanged$45(phoneCapabilityListener, phoneCapability);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhoneCapabilityChanged$45(final PhoneCapabilityListener phoneCapabilityListener, final PhoneCapability phoneCapability) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda85
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PhoneCapabilityListener.this.onPhoneCapabilityChanged(phoneCapability);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(final int i) {
            final RadioPowerStateListener radioPowerStateListener = (RadioPowerStateListener) this.mTelephonyCallbackWeakRef.get();
            if (radioPowerStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda61
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onRadioPowerStateChanged$47(radioPowerStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRadioPowerStateChanged$47(final RadioPowerStateListener radioPowerStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.RadioPowerStateListener.this.onRadioPowerStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(final List<CallState> list) {
            final CallAttributesListener callAttributesListener = (CallAttributesListener) this.mTelephonyCallbackWeakRef.get();
            if (callAttributesListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda35
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallStatesChanged$49(callAttributesListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStatesChanged$49(final CallAttributesListener callAttributesListener, final List list) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda87
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CallAttributesListener.this.onCallStatesChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(final int i) {
            final ActiveDataSubscriptionIdListener activeDataSubscriptionIdListener = (ActiveDataSubscriptionIdListener) this.mTelephonyCallbackWeakRef.get();
            if (activeDataSubscriptionIdListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda37
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onActiveDataSubIdChanged$51(activeDataSubscriptionIdListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActiveDataSubIdChanged$51(final ActiveDataSubscriptionIdListener activeDataSubscriptionIdListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda83
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.ActiveDataSubscriptionIdListener.this.onActiveDataSubscriptionIdChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(final ImsReasonInfo imsReasonInfo) {
            final ImsCallDisconnectCauseListener imsCallDisconnectCauseListener = (ImsCallDisconnectCauseListener) this.mTelephonyCallbackWeakRef.get();
            if (imsCallDisconnectCauseListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda58
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onImsCallDisconnectCauseChanged$53(imsCallDisconnectCauseListener, imsReasonInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$53(final ImsCallDisconnectCauseListener imsCallDisconnectCauseListener, final ImsReasonInfo imsReasonInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda86
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.ImsCallDisconnectCauseListener.this.onImsCallDisconnectCauseChanged(imsReasonInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(final CellIdentity cellIdentity, final String str, final int i, final int i2, final int i3) {
            final RegistrationFailedListener registrationFailedListener = (RegistrationFailedListener) this.mTelephonyCallbackWeakRef.get();
            if (registrationFailedListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda80
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onRegistrationFailed$55(registrationFailedListener, cellIdentity, str, i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRegistrationFailed$55(final RegistrationFailedListener registrationFailedListener, final CellIdentity cellIdentity, final String str, final int i, final int i2, final int i3) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.RegistrationFailedListener.this.onRegistrationFailed(cellIdentity, str, i, i2, i3);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(final BarringInfo barringInfo) {
            final BarringInfoListener barringInfoListener = (BarringInfoListener) this.mTelephonyCallbackWeakRef.get();
            if (barringInfoListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda96
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onBarringInfoChanged$57(barringInfoListener, barringInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBarringInfoChanged$57(final BarringInfoListener barringInfoListener, final BarringInfo barringInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.BarringInfoListener.this.onBarringInfoChanged(barringInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(final List<PhysicalChannelConfig> list) {
            final PhysicalChannelConfigListener physicalChannelConfigListener = (PhysicalChannelConfigListener) this.mTelephonyCallbackWeakRef.get();
            if (physicalChannelConfigListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda54
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onPhysicalChannelConfigChanged$59(physicalChannelConfigListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhysicalChannelConfigChanged$59(final PhysicalChannelConfigListener physicalChannelConfigListener, final List list) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.PhysicalChannelConfigListener.this.onPhysicalChannelConfigChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(final boolean z, final int i) {
            final DataEnabledListener dataEnabledListener = (DataEnabledListener) this.mTelephonyCallbackWeakRef.get();
            if (dataEnabledListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onDataEnabledChanged$61(dataEnabledListener, z, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataEnabledChanged$61(final DataEnabledListener dataEnabledListener, final boolean z, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.DataEnabledListener.this.onDataEnabledChanged(z, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(final int i, final long j) {
            final AllowedNetworkTypesListener allowedNetworkTypesListener = (AllowedNetworkTypesListener) this.mTelephonyCallbackWeakRef.get();
            if (allowedNetworkTypesListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda91
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onAllowedNetworkTypesChanged$63(allowedNetworkTypesListener, i, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAllowedNetworkTypesChanged$63(final AllowedNetworkTypesListener allowedNetworkTypesListener, final int i, final long j) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda71
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.AllowedNetworkTypesListener.this.onAllowedNetworkTypesChanged(i, j);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSimultaneousCallingStateChanged(final int[] iArr) {
            final SimultaneousCellularCallingSupportListener simultaneousCellularCallingSupportListener = (SimultaneousCellularCallingSupportListener) this.mTelephonyCallbackWeakRef.get();
            if (simultaneousCellularCallingSupportListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda42
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSimultaneousCallingStateChanged$65(simultaneousCellularCallingSupportListener, iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSimultaneousCallingStateChanged$65(final SimultaneousCellularCallingSupportListener simultaneousCellularCallingSupportListener, final int[] iArr) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SimultaneousCellularCallingSupportListener.this.onSimultaneousCellularCallingSubscriptionsChanged((Set) Arrays.stream(iArr).boxed().collect(Collectors.toSet()));
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(final List<LinkCapacityEstimate> list) {
            final LinkCapacityEstimateChangedListener linkCapacityEstimateChangedListener = (LinkCapacityEstimateChangedListener) this.mTelephonyCallbackWeakRef.get();
            if (linkCapacityEstimateChangedListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda99
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onLinkCapacityEstimateChanged$67(linkCapacityEstimateChangedListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLinkCapacityEstimateChanged$67(final LinkCapacityEstimateChangedListener linkCapacityEstimateChangedListener, final List list) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.LinkCapacityEstimateChangedListener.this.onLinkCapacityEstimateChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMediaQualityStatusChanged(final MediaQualityStatus mediaQualityStatus) {
            final MediaQualityStatusChangedListener mediaQualityStatusChangedListener = (MediaQualityStatusChangedListener) this.mTelephonyCallbackWeakRef.get();
            if (mediaQualityStatusChangedListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda56
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onMediaQualityStatusChanged$69(mediaQualityStatusChangedListener, mediaQualityStatus);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMediaQualityStatusChanged$69(final MediaQualityStatusChangedListener mediaQualityStatusChangedListener, final MediaQualityStatus mediaQualityStatus) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda93
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.MediaQualityStatusChangedListener.this.onMediaQualityStatusChanged(mediaQualityStatus);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallbackModeStarted(final int i, long j, final int i2) {
            if (Flags.emergencyCallbackModeNotification()) {
                final EmergencyCallbackModeListener emergencyCallbackModeListener = (EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
                Log.d(TelephonyCallback.LOG_TAG, "onCallBackModeStarted:type=" + i + ", listener=" + emergencyCallbackModeListener);
                if (emergencyCallbackModeListener == null) {
                    return;
                }
                final Duration ofMillis = Duration.ofMillis(j);
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda36
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallbackModeStarted$71(emergencyCallbackModeListener, i, ofMillis, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallbackModeStarted$71(final EmergencyCallbackModeListener emergencyCallbackModeListener, final int i, final Duration duration, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda82
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyCallbackModeListener.this.onCallbackModeStarted(i, duration, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallbackModeRestarted(final int i, long j, final int i2) {
            if (Flags.emergencyCallbackModeNotification()) {
                final EmergencyCallbackModeListener emergencyCallbackModeListener = (EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
                Log.d(TelephonyCallback.LOG_TAG, "onCallbackModeRestarted:type=" + i + ", listener=" + emergencyCallbackModeListener);
                if (emergencyCallbackModeListener == null) {
                    return;
                }
                final Duration ofMillis = Duration.ofMillis(j);
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda76
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallbackModeRestarted$73(emergencyCallbackModeListener, i, ofMillis, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallbackModeRestarted$73(final EmergencyCallbackModeListener emergencyCallbackModeListener, final int i, final Duration duration, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyCallbackModeListener.this.onCallbackModeRestarted(i, duration, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallbackModeStopped(final int i, final int i2, final int i3) {
            if (Flags.emergencyCallbackModeNotification()) {
                final EmergencyCallbackModeListener emergencyCallbackModeListener = (EmergencyCallbackModeListener) this.mTelephonyCallbackWeakRef.get();
                Log.d(TelephonyCallback.LOG_TAG, "onCallBackModeStopped:type=" + i + ", reason=" + i2 + ", listener=" + emergencyCallbackModeListener);
                if (emergencyCallbackModeListener == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda92
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCallbackModeStopped$75(emergencyCallbackModeListener, i, i2, i3);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallbackModeStopped$75(final EmergencyCallbackModeListener emergencyCallbackModeListener, final int i, final int i2, final int i3) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.EmergencyCallbackModeListener.this.onCallbackModeStopped(i, i2, i3);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnModeChanged(final boolean z) {
            final CarrierRoamingNtnListener carrierRoamingNtnListener = (CarrierRoamingNtnListener) this.mTelephonyCallbackWeakRef.get();
            if (carrierRoamingNtnListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda97
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnModeChanged$77(carrierRoamingNtnListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnModeChanged$77(final CarrierRoamingNtnListener carrierRoamingNtnListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnListener.this.onCarrierRoamingNtnModeChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnEligibleStateChanged(final boolean z) {
            final CarrierRoamingNtnListener carrierRoamingNtnListener;
            if (Flags.carrierRoamingNbIotNtn() && (carrierRoamingNtnListener = (CarrierRoamingNtnListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda88
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnEligibleStateChanged$79(carrierRoamingNtnListener, z);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnEligibleStateChanged$79(final CarrierRoamingNtnListener carrierRoamingNtnListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnListener.this.onCarrierRoamingNtnEligibleStateChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnAvailableServicesChanged(final int[] iArr) {
            final CarrierRoamingNtnListener carrierRoamingNtnListener;
            if (Flags.carrierRoamingNbIotNtn() && (carrierRoamingNtnListener = (CarrierRoamingNtnListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda43
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnAvailableServicesChanged$81(carrierRoamingNtnListener, iArr);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnAvailableServicesChanged$81(final CarrierRoamingNtnListener carrierRoamingNtnListener, final int[] iArr) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda69
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnListener.this.onCarrierRoamingNtnAvailableServicesChanged(iArr);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierRoamingNtnSignalStrengthChanged(final NtnSignalStrength ntnSignalStrength) {
            final CarrierRoamingNtnListener carrierRoamingNtnListener;
            if (Flags.carrierRoamingNbIotNtn() && (carrierRoamingNtnListener = (CarrierRoamingNtnListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda63
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCarrierRoamingNtnSignalStrengthChanged$83(carrierRoamingNtnListener, ntnSignalStrength);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierRoamingNtnSignalStrengthChanged$83(final CarrierRoamingNtnListener carrierRoamingNtnListener, final NtnSignalStrength ntnSignalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CarrierRoamingNtnListener.this.onCarrierRoamingNtnSignalStrengthChanged(ntnSignalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSecurityAlgorithmsChanged(final SecurityAlgorithmUpdate securityAlgorithmUpdate) {
            final SecurityAlgorithmsListener securityAlgorithmsListener;
            if (Flags.securityAlgorithmsUpdateIndications() && (securityAlgorithmsListener = (SecurityAlgorithmsListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda8
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSecurityAlgorithmsChanged$85(securityAlgorithmsListener, securityAlgorithmUpdate);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSecurityAlgorithmsChanged$85(final SecurityAlgorithmsListener securityAlgorithmsListener, final SecurityAlgorithmUpdate securityAlgorithmUpdate) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SecurityAlgorithmsListener.this.onSecurityAlgorithmsChanged(securityAlgorithmUpdate);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellularIdentifierDisclosedChanged(final CellularIdentifierDisclosure cellularIdentifierDisclosure) {
            final CellularIdentifierDisclosedListener cellularIdentifierDisclosedListener;
            if (Flags.cellularIdentifierDisclosureIndications() && (cellularIdentifierDisclosedListener = (CellularIdentifierDisclosedListener) this.mTelephonyCallbackWeakRef.get()) != null) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda16
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCellularIdentifierDisclosedChanged$87(cellularIdentifierDisclosedListener, cellularIdentifierDisclosure);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellularIdentifierDisclosedChanged$87(final CellularIdentifierDisclosedListener cellularIdentifierDisclosedListener, final CellularIdentifierDisclosure cellularIdentifierDisclosure) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CellularIdentifierDisclosedListener.this.onCellularIdentifierDisclosedChanged(cellularIdentifierDisclosure);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSemSatelliteServiceStateChanged(final SemSatelliteServiceState semSatelliteServiceState) {
            final SemSatelliteStateListener semSatelliteStateListener = (SemSatelliteStateListener) this.mTelephonyCallbackWeakRef.get();
            if (semSatelliteStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda20
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSemSatelliteServiceStateChanged$89(semSatelliteStateListener, semSatelliteServiceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemSatelliteServiceStateChanged$89(final SemSatelliteStateListener semSatelliteStateListener, final SemSatelliteServiceState semSatelliteServiceState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SemSatelliteStateListener.this.onSemSatelliteServiceStateChanged(semSatelliteServiceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSemSatelliteSignalStrengthChanged(final SemSatelliteSignalStrength semSatelliteSignalStrength) {
            final SemSatelliteStateListener semSatelliteStateListener = (SemSatelliteStateListener) this.mTelephonyCallbackWeakRef.get();
            if (semSatelliteStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda34
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onSemSatelliteSignalStrengthChanged$91(semSatelliteStateListener, semSatelliteSignalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemSatelliteSignalStrengthChanged$91(final SemSatelliteStateListener semSatelliteStateListener, final SemSatelliteSignalStrength semSatelliteSignalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda74
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.SemSatelliteStateListener.this.onSemSatelliteSignalStrengthChanged(semSatelliteSignalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiModelUpdateNotified(final int i, final int i2) {
            final CpaiModelUpdateListener cpaiModelUpdateListener = (CpaiModelUpdateListener) this.mTelephonyCallbackWeakRef.get();
            if (cpaiModelUpdateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda45
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCpaiModelUpdateNotified$93(cpaiModelUpdateListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCpaiModelUpdateNotified$93(final CpaiModelUpdateListener cpaiModelUpdateListener, final int i, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CpaiModelUpdateListener.this.onCpaiModelUpdateNotified(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiFeatureInfoNotified(final int i, final int i2) {
            final CpaiFeatureInforListener cpaiFeatureInforListener = (CpaiFeatureInforListener) this.mTelephonyCallbackWeakRef.get();
            if (cpaiFeatureInforListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda78
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCpaiFeatureInfoNotified$95(cpaiFeatureInforListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCpaiFeatureInfoNotified$95(final CpaiFeatureInforListener cpaiFeatureInforListener, final int i, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda75
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CpaiFeatureInforListener.this.onCpaiFeatureInfoNotified(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiDataGatheringNotified(final int i, final int i2, final byte[] bArr) {
            final CpaiDataGatheringListener cpaiDataGatheringListener = (CpaiDataGatheringListener) this.mTelephonyCallbackWeakRef.get();
            if (cpaiDataGatheringListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCpaiDataGatheringNotified$97(cpaiDataGatheringListener, i, i2, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCpaiDataGatheringNotified$97(final CpaiDataGatheringListener cpaiDataGatheringListener, final int i, final int i2, final byte[] bArr) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda95
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CpaiDataGatheringListener.this.onCpaiDataGatheringNotified(i, i2, bArr);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiDevAppMessageNotified(final int i, final int i2, final int i3, final byte[] bArr) {
            final CpaiDevAppMessageListener cpaiDevAppMessageListener = (CpaiDevAppMessageListener) this.mTelephonyCallbackWeakRef.get();
            if (cpaiDevAppMessageListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda27
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyCallback.IPhoneStateListenerStub.this.lambda$onCpaiDevAppMessageNotified$99(cpaiDevAppMessageListener, i, i2, i3, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCpaiDevAppMessageNotified$99(final CpaiDevAppMessageListener cpaiDevAppMessageListener, final int i, final int i2, final int i3, final byte[] bArr) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.TelephonyCallback$IPhoneStateListenerStub$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyCallback.CpaiDevAppMessageListener.this.onCpaiDevAppMessageNotified(i, i2, i3, bArr);
                }
            });
        }
    }
}
