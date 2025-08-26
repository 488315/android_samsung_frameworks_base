package android.telephony;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import com.android.internal.telephony.IPhoneStateListener;
import com.android.internal.util.FunctionalUtils;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Deprecated
/* loaded from: classes4.dex */
public class PhoneStateListener {
    private static final boolean DBG = false;

    @Deprecated
    public static final int LISTEN_ACTIVE_DATA_SUBSCRIPTION_ID_CHANGE = 4194304;

    @Deprecated
    public static final int LISTEN_BARRING_INFO = Integer.MIN_VALUE;

    @SystemApi
    @Deprecated
    public static final int LISTEN_CALL_ATTRIBUTES_CHANGED = 67108864;

    @Deprecated
    public static final int LISTEN_CALL_DISCONNECT_CAUSES = 33554432;

    @Deprecated
    public static final int LISTEN_CALL_FORWARDING_INDICATOR = 8;

    @Deprecated
    public static final int LISTEN_CALL_STATE = 32;

    @Deprecated
    public static final int LISTEN_CARRIER_NETWORK_CHANGE = 65536;

    @Deprecated
    public static final int LISTEN_CELL_INFO = 1024;

    @Deprecated
    public static final int LISTEN_CELL_LOCATION = 16;

    @Deprecated
    public static final int LISTEN_DATA_ACTIVATION_STATE = 262144;

    @Deprecated
    public static final int LISTEN_DATA_ACTIVITY = 128;

    @Deprecated
    public static final int LISTEN_DATA_CONNECTION_REAL_TIME_INFO = 8192;

    @Deprecated
    public static final int LISTEN_DATA_CONNECTION_STATE = 64;

    @Deprecated
    public static final int LISTEN_DISPLAY_INFO_CHANGED = 1048576;

    @Deprecated
    public static final int LISTEN_EMERGENCY_NUMBER_LIST = 16777216;

    @Deprecated
    public static final int LISTEN_IMS_CALL_DISCONNECT_CAUSES = 134217728;

    @Deprecated
    public static final int LISTEN_MESSAGE_WAITING_INDICATOR = 4;
    public static final int LISTEN_NONE = 0;

    @Deprecated
    public static final int LISTEN_OEM_HOOK_RAW_EVENT = 32768;

    @SystemApi
    @Deprecated
    public static final int LISTEN_OUTGOING_EMERGENCY_CALL = 268435456;

    @SystemApi
    @Deprecated
    public static final int LISTEN_OUTGOING_EMERGENCY_SMS = 536870912;

    @Deprecated
    public static final int LISTEN_PHONE_CAPABILITY_CHANGE = 2097152;

    @SystemApi
    @Deprecated
    public static final int LISTEN_PRECISE_CALL_STATE = 2048;

    @Deprecated
    public static final int LISTEN_PRECISE_DATA_CONNECTION_STATE = 4096;

    @SystemApi
    @Deprecated
    public static final int LISTEN_RADIO_POWER_STATE_CHANGED = 8388608;

    @Deprecated
    public static final int LISTEN_REGISTRATION_FAILURE = 1073741824;

    @Deprecated
    public static final int LISTEN_SERVICE_STATE = 1;

    @Deprecated
    public static final int LISTEN_SIGNAL_STRENGTH = 2;

    @Deprecated
    public static final int LISTEN_SIGNAL_STRENGTHS = 256;

    @SystemApi
    @Deprecated
    public static final int LISTEN_SRVCC_STATE_CHANGED = 16384;

    @Deprecated
    public static final int LISTEN_USER_MOBILE_DATA_STATE = 524288;

    @SystemApi
    @Deprecated
    public static final int LISTEN_VOICE_ACTIVATION_STATE = 131072;
    private static final String LOG_TAG = "PhoneStateListener";
    public final IPhoneStateListener callback;
    protected Integer mSubId;

    @Deprecated
    public void onActiveDataSubscriptionIdChanged(int i) {
    }

    @Deprecated
    public void onBarringInfoChanged(BarringInfo barringInfo) {
    }

    @SystemApi
    @Deprecated
    public void onCallAttributesChanged(CallAttributes callAttributes) {
    }

    @Deprecated
    public void onCallDisconnectCauseChanged(int i, int i2) {
    }

    @Deprecated
    public void onCallForwardingIndicatorChanged(boolean z) {
    }

    @Deprecated
    public void onCallStateChanged(int i, String str) {
    }

    @Deprecated
    public void onCarrierNetworkChange(boolean z) {
    }

    @Deprecated
    public void onCellInfoChanged(List<CellInfo> list) {
    }

    @Deprecated
    public void onCellLocationChanged(CellLocation cellLocation) {
    }

    @Deprecated
    public void onDataActivationStateChanged(int i) {
    }

    @Deprecated
    public void onDataActivity(int i) {
    }

    @Deprecated
    public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
    }

    @Deprecated
    public void onDataConnectionStateChanged(int i) {
    }

    @Deprecated
    public void onDataConnectionStateChanged(int i, int i2) {
    }

    @Deprecated
    public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
    }

    @Deprecated
    public void onEmergencyNumberListChanged(Map<Integer, List<EmergencyNumber>> map) {
    }

    @Deprecated
    public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) {
    }

    @Deprecated
    public void onMessageWaitingIndicatorChanged(boolean z) {
    }

    @Deprecated
    public void onOemHookRawEvent(byte[] bArr) {
    }

    @SystemApi
    @Deprecated
    public void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber) {
    }

    @SystemApi
    @Deprecated
    public void onOutgoingEmergencySms(EmergencyNumber emergencyNumber) {
    }

    @Deprecated
    public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) {
    }

    @SystemApi
    @Deprecated
    public void onPreciseCallStateChanged(PreciseCallState preciseCallState) {
    }

    @Deprecated
    public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) {
    }

    @SystemApi
    @Deprecated
    public void onRadioPowerStateChanged(int i) {
    }

    @Deprecated
    public void onRegistrationFailed(CellIdentity cellIdentity, String str, int i, int i2, int i3) {
    }

    @Deprecated
    public void onServiceStateChanged(ServiceState serviceState) {
    }

    @Deprecated
    public void onSignalStrengthChanged(int i) {
    }

    @Deprecated
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
    }

    @SystemApi
    @Deprecated
    public void onSrvccStateChanged(int i) {
    }

    @Deprecated
    public void onUserMobileDataStateChanged(boolean z) {
    }

    @SystemApi
    @Deprecated
    public void onVoiceActivationStateChanged(int i) {
    }

    public PhoneStateListener() {
        this((Integer) null, Looper.myLooper());
    }

    public PhoneStateListener(Looper looper) {
        this((Integer) null, looper);
    }

    public PhoneStateListener(Integer num) {
        this(num, Looper.myLooper());
        if (num == null || VMRuntime.getRuntime().getTargetSdkVersion() < 29) {
            return;
        }
        throw new IllegalArgumentException("PhoneStateListener with subId: " + num + " is not supported, use default constructor");
    }

    public PhoneStateListener(Integer num, Looper looper) {
        this(num, new HandlerExecutor(new Handler(looper)));
        if (num == null || VMRuntime.getRuntime().getTargetSdkVersion() < 29) {
            return;
        }
        throw new IllegalArgumentException("PhoneStateListener with subId: " + num + " is not supported, use default constructor");
    }

    @Deprecated
    public PhoneStateListener(Executor executor) {
        this((Integer) null, executor);
    }

    private PhoneStateListener(Integer num, Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("PhoneStateListener Executor must be non-null");
        }
        this.mSubId = num;
        this.callback = new IPhoneStateListenerStub(this, executor);
    }

    @SystemApi
    @Deprecated
    public void onOutgoingEmergencyCall(EmergencyNumber emergencyNumber, int i) {
        onOutgoingEmergencyCall(emergencyNumber);
    }

    @SystemApi
    @Deprecated
    public void onOutgoingEmergencySms(EmergencyNumber emergencyNumber, int i) {
        onOutgoingEmergencySms(emergencyNumber);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IPhoneStateListenerStub extends IPhoneStateListener.Stub {
        private Executor mExecutor;
        private WeakReference<PhoneStateListener> mPhoneStateListenerWeakRef;

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(int i, long j) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(int i) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCallbackModeRestarted(int i, long j, int i2) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCallbackModeStarted(int i, long j, int i2) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCallbackModeStopped(int i, int i2, int i3) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCarrierRoamingNtnAvailableServicesChanged(int[] iArr) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCarrierRoamingNtnEligibleStateChanged(boolean z) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCarrierRoamingNtnModeChanged(boolean z) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onCellularIdentifierDisclosedChanged(CellularIdentifierDisclosure cellularIdentifierDisclosure) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiDataGatheringNotified(int i, int i2, byte[] bArr) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiDevAppMessageNotified(int i, int i2, int i3, byte[] bArr) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiFeatureInfoNotified(int i, int i2) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCpaiModelUpdateNotified(int i, int i2) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(boolean z, int i) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(List<LinkCapacityEstimate> list) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(List<PhysicalChannelConfig> list) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onSecurityAlgorithmsChanged(SecurityAlgorithmUpdate securityAlgorithmUpdate) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public final void onSimultaneousCallingStateChanged(int[] iArr) {
        }

        IPhoneStateListenerStub(PhoneStateListener phoneStateListener, Executor executor) {
            this.mPhoneStateListenerWeakRef = new WeakReference<>(phoneStateListener);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(final ServiceState serviceState) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda62
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onServiceStateChanged$1(phoneStateListener, serviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceStateChanged$1(final PhoneStateListener phoneStateListener, final ServiceState serviceState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onServiceStateChanged(serviceState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda17
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onSignalStrengthChanged$3(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthChanged$3(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onSignalStrengthChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(final boolean z) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda19
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onMessageWaitingIndicatorChanged$5(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageWaitingIndicatorChanged$5(final PhoneStateListener phoneStateListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onMessageWaitingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(final boolean z) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda50
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onCallForwardingIndicatorChanged$7(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallForwardingIndicatorChanged$7(final PhoneStateListener phoneStateListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCallForwardingIndicatorChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(CellIdentity cellIdentity) {
            final CellLocation empty = cellIdentity == null ? CellLocation.getEmpty() : cellIdentity.asCellLocation();
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda34
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onCellLocationChanged$9(phoneStateListener, empty);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellLocationChanged$9(final PhoneStateListener phoneStateListener, final CellLocation cellLocation) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCellLocationChanged(cellLocation);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(final int i, final String str) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda21
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onLegacyCallStateChanged$11(phoneStateListener, i, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLegacyCallStateChanged$11(final PhoneStateListener phoneStateListener, final int i, final String str) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCallStateChanged(i, str);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(final int i, final int i2) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            if (i == 4 && VMRuntime.getRuntime().getTargetSdkVersion() < 30) {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda58
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() throws Exception {
                        this.f$0.lambda$onDataConnectionStateChanged$13(phoneStateListener, i2);
                    }
                });
            } else {
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda59
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() throws Exception {
                        this.f$0.lambda$onDataConnectionStateChanged$15(phoneStateListener, i, i2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$13(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$12(phoneStateListener, i);
                }
            });
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$12(PhoneStateListener phoneStateListener, int i) {
            phoneStateListener.onDataConnectionStateChanged(2, i);
            phoneStateListener.onDataConnectionStateChanged(2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionStateChanged$15(final PhoneStateListener phoneStateListener, final int i, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda61
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$14(phoneStateListener, i, i2);
                }
            });
        }

        static /* synthetic */ void lambda$onDataConnectionStateChanged$14(PhoneStateListener phoneStateListener, int i, int i2) {
            phoneStateListener.onDataConnectionStateChanged(i, i2);
            phoneStateListener.onDataConnectionStateChanged(i);
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda28
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onDataActivity$17(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivity$17(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda57
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onDataActivity(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(final SignalStrength signalStrength) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda31
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onSignalStrengthsChanged$19(phoneStateListener, signalStrength);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSignalStrengthsChanged$19(final PhoneStateListener phoneStateListener, final SignalStrength signalStrength) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onSignalStrengthsChanged(signalStrength);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(final List<CellInfo> list) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda46
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onCellInfoChanged$21(phoneStateListener, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCellInfoChanged$21(final PhoneStateListener phoneStateListener, final List list) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCellInfoChanged(list);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(final PreciseCallState preciseCallState) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda45
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onPreciseCallStateChanged$23(phoneStateListener, preciseCallState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseCallStateChanged$23(final PhoneStateListener phoneStateListener, final PreciseCallState preciseCallState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onPreciseCallStateChanged(preciseCallState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(final int i, final int i2) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda26
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onCallDisconnectCauseChanged$25(phoneStateListener, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallDisconnectCauseChanged$25(final PhoneStateListener phoneStateListener, final int i, final int i2) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCallDisconnectCauseChanged(i, i2);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(final PreciseDataConnectionState preciseDataConnectionState) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda22
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onPreciseDataConnectionStateChanged$27(phoneStateListener, preciseDataConnectionState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreciseDataConnectionStateChanged$27(final PhoneStateListener phoneStateListener, final PreciseDataConnectionState preciseDataConnectionState) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(final DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onDataConnectionRealTimeInfoChanged$29(phoneStateListener, dataConnectionRealTimeInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataConnectionRealTimeInfoChanged$29(final PhoneStateListener phoneStateListener, final DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onDataConnectionRealTimeInfoChanged(dataConnectionRealTimeInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda35
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onSrvccStateChanged$31(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSrvccStateChanged$31(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onSrvccStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda10
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onVoiceActivationStateChanged$33(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVoiceActivationStateChanged$33(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onVoiceActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onDataActivationStateChanged$35(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDataActivationStateChanged$35(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda47
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onDataActivationStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(final boolean z) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda18
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onUserMobileDataStateChanged$37(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserMobileDataStateChanged$37(final PhoneStateListener phoneStateListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onUserMobileDataStateChanged(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(final TelephonyDisplayInfo telephonyDisplayInfo) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onDisplayInfoChanged$39(phoneStateListener, telephonyDisplayInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayInfoChanged$39(final PhoneStateListener phoneStateListener, final TelephonyDisplayInfo telephonyDisplayInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onDisplayInfoChanged(telephonyDisplayInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(final byte[] bArr) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda42
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onOemHookRawEvent$41(phoneStateListener, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOemHookRawEvent$41(final PhoneStateListener phoneStateListener, final byte[] bArr) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onOemHookRawEvent(bArr);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(final boolean z) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onCarrierNetworkChange$43(phoneStateListener, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierNetworkChange$43(final PhoneStateListener phoneStateListener, final boolean z) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCarrierNetworkChange(z);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(final Map map) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda48
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onEmergencyNumberListChanged$45(phoneStateListener, map);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyNumberListChanged$45(final PhoneStateListener phoneStateListener, final Map map) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onEmergencyNumberListChanged(map);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(final EmergencyNumber emergencyNumber, final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda54
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onOutgoingEmergencyCall$47(phoneStateListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencyCall$47(final PhoneStateListener phoneStateListener, final EmergencyNumber emergencyNumber, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onOutgoingEmergencyCall(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(final EmergencyNumber emergencyNumber, final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda13
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onOutgoingEmergencySms$49(phoneStateListener, emergencyNumber, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOutgoingEmergencySms$49(final PhoneStateListener phoneStateListener, final EmergencyNumber emergencyNumber, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onOutgoingEmergencySms(emergencyNumber, i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(final PhoneCapability phoneCapability) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda63
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onPhoneCapabilityChanged$51(phoneStateListener, phoneCapability);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPhoneCapabilityChanged$51(final PhoneStateListener phoneStateListener, final PhoneCapability phoneCapability) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onPhoneCapabilityChanged(phoneCapability);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda52
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onRadioPowerStateChanged$53(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRadioPowerStateChanged$53(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onRadioPowerStateChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(List<CallState> list) {
            final CallAttributes callAttributes;
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null || list == null) {
                return;
            }
            if (list.isEmpty()) {
                callAttributes = new CallAttributes(new PreciseCallState(0, 0, 0, -1, -1), 0, new CallQuality());
            } else {
                int callState = 0;
                int callState2 = 0;
                int callState3 = 0;
                for (CallState callState4 : list) {
                    int callClassification = callState4.getCallClassification();
                    if (callClassification == 0) {
                        callState = callState4.getCallState();
                    } else if (callClassification == 1) {
                        callState2 = callState4.getCallState();
                    } else if (callClassification == 2) {
                        callState3 = callState4.getCallState();
                    }
                }
                callAttributes = new CallAttributes(new PreciseCallState(callState, callState2, callState3, -1, -1), list.get(0).getNetworkType(), list.get(0).getCallQuality());
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda29
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onCallStatesChanged$55(phoneStateListener, callAttributes);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStatesChanged$55(final PhoneStateListener phoneStateListener, final CallAttributes callAttributes) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onCallAttributesChanged(callAttributes);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(final int i) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda32
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onActiveDataSubIdChanged$57(phoneStateListener, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActiveDataSubIdChanged$57(final PhoneStateListener phoneStateListener, final int i) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onActiveDataSubscriptionIdChanged(i);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(final ImsReasonInfo imsReasonInfo) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onImsCallDisconnectCauseChanged$59(phoneStateListener, imsReasonInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsCallDisconnectCauseChanged$59(final PhoneStateListener phoneStateListener, final ImsReasonInfo imsReasonInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onImsCallDisconnectCauseChanged(imsReasonInfo);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(final CellIdentity cellIdentity, final String str, final int i, final int i2, final int i3) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda60
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onRegistrationFailed$61(phoneStateListener, cellIdentity, str, i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRegistrationFailed$61(final PhoneStateListener phoneStateListener, final CellIdentity cellIdentity, final String str, final int i, final int i2, final int i3) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onRegistrationFailed(cellIdentity, str, i, i2, i3);
                }
            });
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(final BarringInfo barringInfo) {
            final PhoneStateListener phoneStateListener = this.mPhoneStateListenerWeakRef.get();
            if (phoneStateListener == null) {
                return;
            }
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() throws Exception {
                    this.f$0.lambda$onBarringInfoChanged$63(phoneStateListener, barringInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBarringInfoChanged$63(final PhoneStateListener phoneStateListener, final BarringInfo barringInfo) throws Exception {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.PhoneStateListener$IPhoneStateListenerStub$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    phoneStateListener.onBarringInfoChanged(barringInfo);
                }
            });
        }
    }

    private void log(String str) {
        Rlog.d(LOG_TAG, str);
    }
}
