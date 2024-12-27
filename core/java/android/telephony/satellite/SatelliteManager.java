package android.telephony.satellite;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyFrameworkInitializer;

import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.IVoidConsumer;
import com.android.internal.util.FunctionalUtils;
import com.android.telephony.Rlog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SystemApi
public final class SatelliteManager {
    public static final String ACTION_SATELLITE_START_NON_EMERGENCY_SESSION =
            "android.telephony.action.ACTION_SATELLITE_START_NON_EMERGENCY_SESSION";
    public static final String ACTION_SATELLITE_SUBSCRIBER_ID_LIST_CHANGED =
            "android.telephony.action.ACTION_SATELLITE_SUBSCRIBER_ID_LIST_CHANGED";
    public static final int DATAGRAM_TYPE_CHECK_PENDING_INCOMING_SMS = 7;
    public static final int DATAGRAM_TYPE_KEEP_ALIVE = 3;
    public static final int DATAGRAM_TYPE_LAST_SOS_MESSAGE_NO_HELP_NEEDED = 5;
    public static final int DATAGRAM_TYPE_LAST_SOS_MESSAGE_STILL_NEED_HELP = 4;
    public static final int DATAGRAM_TYPE_LOCATION_SHARING = 2;
    public static final int DATAGRAM_TYPE_SMS = 6;
    public static final int DATAGRAM_TYPE_SOS_MESSAGE = 1;
    public static final int DATAGRAM_TYPE_UNKNOWN = 0;
    public static final int DEVICE_HOLD_POSITION_LANDSCAPE_LEFT = 2;
    public static final int DEVICE_HOLD_POSITION_LANDSCAPE_RIGHT = 3;
    public static final int DEVICE_HOLD_POSITION_PORTRAIT = 1;
    public static final int DEVICE_HOLD_POSITION_UNKNOWN = 0;
    public static final int DISPLAY_MODE_CLOSED = 3;
    public static final int DISPLAY_MODE_FIXED = 1;
    public static final int DISPLAY_MODE_OPENED = 2;
    public static final int DISPLAY_MODE_UNKNOWN = 0;
    public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_SOS = 1;
    public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_T911 = 2;
    public static final String KEY_DEMO_MODE_ENABLED = "demo_mode_enabled";
    public static final String KEY_DEPROVISION_SATELLITE_TOKENS = "deprovision_satellite";
    public static final String KEY_EMERGENCY_MODE_ENABLED = "emergency_mode_enabled";
    public static final String KEY_NTN_SIGNAL_STRENGTH = "ntn_signal_strength";
    public static final String KEY_PROVISION_SATELLITE_TOKENS = "provision_satellite";
    public static final String KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN =
            "request_provision_subscriber_id";
    public static final String KEY_SATELLITE_CAPABILITIES = "satellite_capabilities";
    public static final String KEY_SATELLITE_COMMUNICATION_ALLOWED =
            "satellite_communication_allowed";
    public static final String KEY_SATELLITE_ENABLED = "satellite_enabled";
    public static final String KEY_SATELLITE_NEXT_VISIBILITY = "satellite_next_visibility";
    public static final String KEY_SATELLITE_PROVISIONED = "satellite_provisioned";
    public static final String KEY_SATELLITE_SUPPORTED = "satellite_supported";
    public static final String KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID =
            "selected_nb_iot_satellite_subscription_id";
    public static final String KEY_SESSION_STATS = "session_stats";
    public static final String METADATA_SATELLITE_MANUAL_CONNECT_P2P_SUPPORT =
            "android.telephony.METADATA_SATELLITE_MANUAL_CONNECT_P2P_SUPPORT";
    public static final int NT_RADIO_TECHNOLOGY_EMTC_NTN = 3;
    public static final int NT_RADIO_TECHNOLOGY_NB_IOT_NTN = 1;
    public static final int NT_RADIO_TECHNOLOGY_NR_NTN = 2;
    public static final int NT_RADIO_TECHNOLOGY_PROPRIETARY = 4;
    public static final int NT_RADIO_TECHNOLOGY_UNKNOWN = 0;
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_ENTITLEMENT = 2;
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_GEOLOCATION = 1;
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_USER = 0;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_IDLE = 0;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_FAILED = 7;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_NONE = 6;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_SUCCESS = 5;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVING = 4;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SENDING = 1;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_FAILED = 3;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_SUCCESS = 2;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_UNKNOWN = -1;
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_WAITING_TO_CONNECT = 8;
    public static final int SATELLITE_DISALLOWED_REASON_LOCATION_DISABLED = 4;
    public static final int SATELLITE_DISALLOWED_REASON_NOT_IN_ALLOWED_REGION = 2;
    public static final int SATELLITE_DISALLOWED_REASON_NOT_PROVISIONED = 1;
    public static final int SATELLITE_DISALLOWED_REASON_NOT_SUPPORTED = 0;
    public static final int SATELLITE_DISALLOWED_REASON_UNSUPPORTED_DEFAULT_MSG_APP = 3;
    public static final int SATELLITE_MODEM_STATE_CONNECTED = 7;
    public static final int SATELLITE_MODEM_STATE_DATAGRAM_RETRYING = 3;
    public static final int SATELLITE_MODEM_STATE_DATAGRAM_TRANSFERRING = 2;
    public static final int SATELLITE_MODEM_STATE_DISABLING_SATELLITE = 9;
    public static final int SATELLITE_MODEM_STATE_ENABLING_SATELLITE = 8;
    public static final int SATELLITE_MODEM_STATE_IDLE = 0;
    public static final int SATELLITE_MODEM_STATE_LISTENING = 1;
    public static final int SATELLITE_MODEM_STATE_NOT_CONNECTED = 6;
    public static final int SATELLITE_MODEM_STATE_OFF = 4;
    public static final int SATELLITE_MODEM_STATE_UNAVAILABLE = 5;
    public static final int SATELLITE_MODEM_STATE_UNKNOWN = -1;
    public static final int SATELLITE_RESULT_ACCESS_BARRED = 16;
    public static final int SATELLITE_RESULT_DISABLE_IN_PROGRESS = 28;
    public static final int SATELLITE_RESULT_EMERGENCY_CALL_IN_PROGRESS = 27;
    public static final int SATELLITE_RESULT_ENABLE_IN_PROGRESS = 29;
    public static final int SATELLITE_RESULT_ERROR = 1;
    public static final int SATELLITE_RESULT_ILLEGAL_STATE = 23;
    public static final int SATELLITE_RESULT_INVALID_ARGUMENTS = 8;
    public static final int SATELLITE_RESULT_INVALID_MODEM_STATE = 7;
    public static final int SATELLITE_RESULT_INVALID_TELEPHONY_STATE = 6;
    public static final int SATELLITE_RESULT_LOCATION_DISABLED = 25;
    public static final int SATELLITE_RESULT_LOCATION_NOT_AVAILABLE = 26;
    public static final int SATELLITE_RESULT_MODEM_BUSY = 22;
    public static final int SATELLITE_RESULT_MODEM_ERROR = 4;
    public static final int SATELLITE_RESULT_MODEM_TIMEOUT = 24;
    public static final int SATELLITE_RESULT_NETWORK_ERROR = 5;
    public static final int SATELLITE_RESULT_NETWORK_TIMEOUT = 17;
    public static final int SATELLITE_RESULT_NOT_AUTHORIZED = 19;
    public static final int SATELLITE_RESULT_NOT_REACHABLE = 18;
    public static final int SATELLITE_RESULT_NOT_SUPPORTED = 20;
    public static final int SATELLITE_RESULT_NO_RESOURCES = 12;
    public static final int SATELLITE_RESULT_NO_VALID_SATELLITE_SUBSCRIPTION = 30;
    public static final int SATELLITE_RESULT_RADIO_NOT_AVAILABLE = 10;
    public static final int SATELLITE_RESULT_REQUEST_ABORTED = 15;
    public static final int SATELLITE_RESULT_REQUEST_FAILED = 9;
    public static final int SATELLITE_RESULT_REQUEST_IN_PROGRESS = 21;
    public static final int SATELLITE_RESULT_REQUEST_NOT_SUPPORTED = 11;
    public static final int SATELLITE_RESULT_SERVER_ERROR = 2;
    public static final int SATELLITE_RESULT_SERVICE_ERROR = 3;
    public static final int SATELLITE_RESULT_SERVICE_NOT_PROVISIONED = 13;
    public static final int SATELLITE_RESULT_SERVICE_PROVISION_IN_PROGRESS = 14;
    public static final int SATELLITE_RESULT_SUCCESS = 0;
    private static final String TAG = "SatelliteManager";
    private final Context mContext;
    private final int mSubId;
    private static final ConcurrentHashMap<SatelliteDatagramCallback, ISatelliteDatagramCallback>
            sSatelliteDatagramCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteProvisionStateCallback, ISatelliteProvisionStateCallback>
            sSatelliteProvisionStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteModemStateCallback, ISatelliteModemStateCallback>
            sSatelliteModemStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteTransmissionUpdateCallback, ISatelliteTransmissionUpdateCallback>
            sSatelliteTransmissionUpdateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<NtnSignalStrengthCallback, INtnSignalStrengthCallback>
            sNtnSignalStrengthCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteCapabilitiesCallback, ISatelliteCapabilitiesCallback>
            sSatelliteCapabilitiesCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteSupportedStateCallback, ISatelliteSupportedStateCallback>
            sSatelliteSupportedStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteCommunicationAllowedStateCallback,
                    ISatelliteCommunicationAllowedStateCallback>
            sSatelliteCommunicationAllowedStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
                    SatelliteDisallowedReasonsCallback, ISatelliteDisallowedReasonsCallback>
            sSatelliteDisallowedReasonsCallbackMap = new ConcurrentHashMap<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DatagramType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceHoldPosition {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface NTRadioTechnology {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteCommunicationRestrictionReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteDatagramTransferState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteDisallowedReason {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteModemState {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteResult {}

    public SatelliteManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    private SatelliteManager(Context context, int subId) {
        this.mContext = context;
        this.mSubId = subId;
    }

    public static class SatelliteException extends Exception {
        private final int mErrorCode;

        public SatelliteException(int errorCode) {
            this.mErrorCode = errorCode;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public void requestEnabled(
            EnableRequestAttributes attributes,
            Executor executor,
            final Consumer<Integer> resultListener) {
        Objects.requireNonNull(attributes);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony == null) {
                Rlog.e(TAG, "requestEnabled() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda16
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            } else {
                IIntegerConsumer errorCallback = new AnonymousClass1(executor, resultListener);
                telephony.requestSatelliteEnabled(
                        attributes.isEnabled(),
                        attributes.isDemoMode(),
                        attributes.isEmergencyMode(),
                        errorCallback);
            }
        } catch (RemoteException ex) {
            Rlog.e(TAG, "requestEnabled() exception: ", ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda59
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$1, reason: invalid class name */
    class AnonymousClass1 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass1(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestIsEnabled(
            Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass2(null, executor, callback);
                telephony.requestIsSatelliteEnabled(receiver);
            } else {
                loge("requestIsEnabled() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda88
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda66
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestIsEnabled() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda89
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda65
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$2, reason: invalid class name */
    class AnonymousClass2 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey("satellite_enabled")) {
                    final boolean isSatelliteEnabled = resultData.getBoolean("satellite_enabled");
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda1
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_ENABLED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda0
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda5
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestIsDemoModeEnabled(
            Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass3(null, executor, callback);
                telephony.requestIsDemoModeEnabled(receiver);
            } else {
                loge("requestIsDemoModeEnabled() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda22
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda38
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestIsDemoModeEnabled() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda23
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda2
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$3, reason: invalid class name */
    class AnonymousClass3 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_DEMO_MODE_ENABLED)) {
                    final boolean isDemoModeEnabled =
                            resultData.getBoolean(SatelliteManager.KEY_DEMO_MODE_ENABLED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda1
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_DEMO_MODE_ENABLED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda2
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestIsEmergencyModeEnabled(
            Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass4(null, executor, callback);
                telephony.requestIsEmergencyModeEnabled(receiver);
            } else {
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda26
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda92
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestIsEmergencyModeEnabled() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$4, reason: invalid class name */
    class AnonymousClass4 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_EMERGENCY_MODE_ENABLED)) {
                    final boolean isEmergencyModeEnabled =
                            resultData.getBoolean(SatelliteManager.KEY_EMERGENCY_MODE_ENABLED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda5
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_EMERGENCY_MODE_ENABLED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda3
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda4
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestIsSupported(
            Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass5(null, executor, callback);
                telephony.requestIsSatelliteSupported(receiver);
            } else {
                loge("requestIsSupported() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda43
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda85
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestIsSupported() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda44
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda58
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$5, reason: invalid class name */
    class AnonymousClass5 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_SUPPORTED)) {
                    final boolean isSatelliteSupported =
                            resultData.getBoolean(SatelliteManager.KEY_SATELLITE_SUPPORTED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda2
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_SUPPORTED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda1
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestCapabilities(
            Executor executor,
            final OutcomeReceiver<SatelliteCapabilities, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass6(null, executor, callback);
                telephony.requestSatelliteCapabilities(receiver);
            } else {
                loge("requestCapabilities() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda48
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda54
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestCapabilities() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda49
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda19
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$6, reason: invalid class name */
    class AnonymousClass6 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass6(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_CAPABILITIES)) {
                    final SatelliteCapabilities capabilities =
                            (SatelliteCapabilities)
                                    resultData.getParcelable(
                                            SatelliteManager.KEY_SATELLITE_CAPABILITIES,
                                            SatelliteCapabilities.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda5
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(r2);
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_CAPABILITIES does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda4
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void startTransmissionUpdates(
            Executor executor,
            final Consumer<Integer> resultListener,
            SatelliteTransmissionUpdateCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer errorCallback = new AnonymousClass7(executor, resultListener);
                ISatelliteTransmissionUpdateCallback internalCallback =
                        new AnonymousClass8(executor, callback);
                sSatelliteTransmissionUpdateCallbackMap.put(callback, internalCallback);
                telephony.startSatelliteTransmissionUpdates(errorCallback, internalCallback);
            } else {
                loge("startTransmissionUpdates() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda73
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("startTransmissionUpdates() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda82
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$7, reason: invalid class name */
    class AnonymousClass7 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass7(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$8, reason: invalid class name */
    class AnonymousClass8 extends ISatelliteTransmissionUpdateCallback.Stub {
        final /* synthetic */ SatelliteTransmissionUpdateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass8(
                Executor executor,
                SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteTransmissionUpdateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSatellitePositionChanged(final PointingInfo pointingInfo) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda3
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteTransmissionUpdateCallback.this
                                                    .onSatellitePositionChanged(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramStateChanged(
                final int datagramType,
                final int state,
                final int sendPendingCount,
                final int errorCode) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteTransmissionUpdateCallback.this
                                                    .onSendDatagramStateChanged(r2, r3, r4, r5);
                                        }
                                    });
                        }
                    });
            Executor executor2 = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback2 =
                    this.val$callback;
            executor2.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda2
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteTransmissionUpdateCallback.this
                                                    .onSendDatagramStateChanged(r2, r3, r4);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onReceiveDatagramStateChanged(
                final int state, final int receivePendingCount, final int errorCode) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda7
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteTransmissionUpdateCallback.this
                                                    .onReceiveDatagramStateChanged(r2, r3, r4);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramRequested(final int datagramType) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda9
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteTransmissionUpdateCallback.this
                                                    .onSendDatagramRequested(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void stopTransmissionUpdates(
            SatelliteTransmissionUpdateCallback callback,
            Executor executor,
            final Consumer<Integer> resultListener) {
        Objects.requireNonNull(callback);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        ISatelliteTransmissionUpdateCallback internalCallback =
                sSatelliteTransmissionUpdateCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    IIntegerConsumer errorCallback = new AnonymousClass9(executor, resultListener);
                    telephony.stopSatelliteTransmissionUpdates(errorCallback, internalCallback);
                } else {
                    loge("stopSatelliteTransmissionUpdates: No internal callback.");
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda35
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda25
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    r1.accept(8);
                                                }
                                            });
                                }
                            });
                }
            } else {
                loge("stopTransmissionUpdates() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda36
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda56
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("stopTransmissionUpdates() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda37
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda21
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$9, reason: invalid class name */
    class AnonymousClass9 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass9(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void provisionService(
            String token,
            byte[] provisionData,
            CancellationSignal cancellationSignal,
            Executor executor,
            final Consumer<Integer> resultListener) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        Objects.requireNonNull(provisionData);
        ICancellationSignal cancelRemote = null;
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer errorCallback = new AnonymousClass10(executor, resultListener);
                cancelRemote =
                        telephony.provisionSatelliteService(token, provisionData, errorCallback);
            } else {
                loge("provisionService() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda86
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda76
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("provisionService() RemoteException=" + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda87
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda3
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
        if (cancellationSignal != null) {
            cancellationSignal.setRemote(cancelRemote);
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$10, reason: invalid class name */
    class AnonymousClass10 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass10(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void deprovisionService(
            String token, Executor executor, final Consumer<Integer> resultListener) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer errorCallback = new AnonymousClass11(executor, resultListener);
                telephony.deprovisionSatelliteService(token, errorCallback);
            } else {
                loge("deprovisionService() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda29
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda27
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("deprovisionService() RemoteException ex=" + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda30
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda40
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$11, reason: invalid class name */
    class AnonymousClass11 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass11(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public int registerForProvisionStateChanged(
            Executor executor, SatelliteProvisionStateCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteProvisionStateCallback internalCallback =
                        new AnonymousClass12(executor, callback);
                sSatelliteProvisionStateCallbackMap.put(callback, internalCallback);
                return telephony.registerForSatelliteProvisionStateChanged(internalCallback);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForProvisionStateChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$12, reason: invalid class name */
    class AnonymousClass12 extends ISatelliteProvisionStateCallback.Stub {
        final /* synthetic */ SatelliteProvisionStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass12(
                Executor executor,
                SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteProvisionStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteProvisionStateChanged(final boolean provisioned) {
            Executor executor = this.val$executor;
            final SatelliteProvisionStateCallback satelliteProvisionStateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda2
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteProvisionStateCallback.this
                                                    .onSatelliteProvisionStateChanged(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteSubscriptionProvisionStateChanged(
                final List<SatelliteSubscriberProvisionStatus> satelliteSubscriberProvisionStatus) {
            Executor executor = this.val$executor;
            final SatelliteProvisionStateCallback satelliteProvisionStateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteProvisionStateCallback.this
                                                    .onSatelliteSubscriptionProvisionStateChanged(
                                                            r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForProvisionStateChanged(SatelliteProvisionStateCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteProvisionStateCallback internalCallback =
                sSatelliteProvisionStateCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForSatelliteProvisionStateChanged(internalCallback);
                } else {
                    loge("unregisterForProvisionStateChanged: No internal callback.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForProvisionStateChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public void requestIsProvisioned(
            Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass13(null, executor, callback);
                telephony.requestIsSatelliteProvisioned(receiver);
            } else {
                loge("requestIsProvisioned() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda50
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda67
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestIsProvisioned() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda51
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$13, reason: invalid class name */
    class AnonymousClass13 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass13(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_PROVISIONED)) {
                    final boolean isSatelliteProvisioned =
                            resultData.getBoolean(SatelliteManager.KEY_SATELLITE_PROVISIONED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda5
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_PROVISIONED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda4
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda3
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public int registerForModemStateChanged(
            Executor executor, SatelliteModemStateCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteModemStateCallback internalCallback =
                        new AnonymousClass14(executor, callback);
                sSatelliteModemStateCallbackMap.put(callback, internalCallback);
                return telephony.registerForSatelliteModemStateChanged(internalCallback);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForModemStateChanged() RemoteException:" + ex);
            ex.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$14, reason: invalid class name */
    class AnonymousClass14 extends ISatelliteModemStateCallback.Stub {
        final /* synthetic */ SatelliteModemStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass14(
                Executor executor, SatelliteModemStateCallback satelliteModemStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteModemStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(final int state) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteModemStateCallback.this
                                                    .onSatelliteModemStateChanged(r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onEmergencyModeChanged(final boolean isEmergency) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteModemStateCallback.this.onEmergencyModeChanged(
                                                    r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onRegistrationFailure(final int causeCode) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda4
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteModemStateCallback.this.onRegistrationFailure(
                                                    r2);
                                        }
                                    });
                        }
                    });
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onTerrestrialNetworkAvailableChanged(final boolean isAvailable) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda2
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteModemStateCallback.this
                                                    .onTerrestrialNetworkAvailableChanged(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForModemStateChanged(SatelliteModemStateCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteModemStateCallback internalCallback =
                sSatelliteModemStateCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForModemStateChanged(internalCallback);
                } else {
                    loge("unregisterForModemStateChanged: No internal callback.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForModemStateChanged() RemoteException:" + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public int registerForIncomingDatagram(Executor executor, SatelliteDatagramCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteDatagramCallback internalCallback =
                        new AnonymousClass15(executor, callback);
                sSatelliteDatagramCallbackMap.put(callback, internalCallback);
                return telephony.registerForIncomingDatagram(internalCallback);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForIncomingDatagram() RemoteException:" + ex);
            ex.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$15, reason: invalid class name */
    class AnonymousClass15 extends ISatelliteDatagramCallback.Stub {
        final /* synthetic */ SatelliteDatagramCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass15(Executor executor, SatelliteDatagramCallback satelliteDatagramCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteDatagramCallback;
        }

        @Override // android.telephony.satellite.ISatelliteDatagramCallback
        public void onSatelliteDatagramReceived(
                final long datagramId,
                final SatelliteDatagram datagram,
                final int pendingCount,
                final IVoidConsumer internalAck) {
            final Consumer<Void> externalAck =
                    new Consumer<
                            Void>() { // from class:
                                      // android.telephony.satellite.SatelliteManager.15.1
                        @Override // java.util.function.Consumer
                        public void accept(Void result) {
                            try {
                                internalAck.accept();
                            } catch (RemoteException e) {
                                SatelliteManager.logd(
                                        "onSatelliteDatagramReceived RemoteException: " + e);
                            }
                        }
                    };
            Executor executor = this.val$executor;
            final SatelliteDatagramCallback satelliteDatagramCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteDatagramCallback.this
                                                    .onSatelliteDatagramReceived(r2, r4, r5, r6);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForIncomingDatagram(SatelliteDatagramCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteDatagramCallback internalCallback =
                sSatelliteDatagramCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForIncomingDatagram(internalCallback);
                } else {
                    loge("unregisterForIncomingDatagram: No internal callback.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForIncomingDatagram() RemoteException:" + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public void pollPendingDatagrams(Executor executor, final Consumer<Integer> resultListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer internalCallback = new AnonymousClass16(executor, resultListener);
                telephony.pollPendingDatagrams(internalCallback);
            } else {
                loge("pollPendingDatagrams() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda74
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda17
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("pollPendingDatagrams() RemoteException:" + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda75
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda81
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$16, reason: invalid class name */
    class AnonymousClass16 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass16(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void sendDatagram(
            int datagramType,
            SatelliteDatagram datagram,
            boolean needFullScreenPointingUI,
            Executor executor,
            final Consumer<Integer> resultListener) {
        Objects.requireNonNull(datagram);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer internalCallback = new AnonymousClass17(executor, resultListener);
                telephony.sendDatagram(
                        datagramType, datagram, needFullScreenPointingUI, internalCallback);
            } else {
                loge("sendDatagram() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda45
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda39
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("sendDatagram() RemoteException:" + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda46
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda90
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$17, reason: invalid class name */
    class AnonymousClass17 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass17(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestIsCommunicationAllowedForCurrentLocation(
            Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass18(null, executor, callback);
                telephony.requestIsCommunicationAllowedForCurrentLocation(this.mSubId, receiver);
            } else {
                loge("requestIsCommunicationAllowedForCurrentLocation() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda33
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda79
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestIsCommunicationAllowedForCurrentLocation() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda34
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda83
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$18, reason: invalid class name */
    class AnonymousClass18 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass18(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED)) {
                    final boolean isSatelliteCommunicationAllowed =
                            resultData.getBoolean(
                                    SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda4
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_COMMUNICATION_ALLOWED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda3
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda5
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestTimeForNextSatelliteVisibility(
            Executor executor, final OutcomeReceiver<Duration, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass19(null, executor, callback);
                telephony.requestTimeForNextSatelliteVisibility(receiver);
            } else {
                loge("requestTimeForNextSatelliteVisibility() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda64
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestTimeForNextSatelliteVisibility() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda63
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$19, reason: invalid class name */
    class AnonymousClass19 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass19(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY)) {
                    final int nextVisibilityDuration =
                            resultData.getInt(SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda4
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Duration.ofSeconds(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_NEXT_VISIBILITY does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda5
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestSelectedNbIotSatelliteSubscriptionId(
            Executor executor, final OutcomeReceiver<Integer, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass20(null, executor, callback);
                telephony.requestSelectedNbIotSatelliteSubscriptionId(receiver);
            } else {
                loge("requestSelectedNbIotSatelliteSubscriptionId() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda71
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda91
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestSelectedNbIotSatelliteSubscriptionId() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda72
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda68
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$20, reason: invalid class name */
    class AnonymousClass20 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass20(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(
                        SatelliteManager.KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID)) {
                    final int selectedSatelliteSubscriptionId =
                            resultData.getInt(
                                    SatelliteManager.KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda3
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Integer.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge(
                        "KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda4
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda5
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void setDeviceAlignedWithSatellite(boolean isAligned) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setDeviceAlignedWithSatellite(isAligned);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("setDeviceAlignedWithSatellite() RemoteException:" + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public void requestAttachEnabledForCarrier(
            int subId,
            boolean enableSatellite,
            Executor executor,
            Consumer<Integer> resultListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(resultListener);
        if (enableSatellite) {
            removeAttachRestrictionForCarrier(subId, 0, executor, resultListener);
        } else {
            addAttachRestrictionForCarrier(subId, 0, executor, resultListener);
        }
    }

    public void requestIsAttachEnabledForCarrier(
            int subId,
            Executor executor,
            final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        final Set<Integer> restrictionReason = getAttachRestrictionReasonsForCarrier(subId);
        executor.execute(
                new Runnable() { // from class:
                                 // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda60
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver outcomeReceiver = OutcomeReceiver.this;
                        Set set = restrictionReason;
                        outcomeReceiver.onResult(Boolean.valueOf(!set.contains(0)));
                    }
                });
    }

    public void addAttachRestrictionForCarrier(
            int subId, int reason, Executor executor, final Consumer<Integer> resultListener) {
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer errorCallback = new AnonymousClass21(executor, resultListener);
                telephony.addAttachRestrictionForCarrier(subId, reason, errorCallback);
            } else {
                loge("addAttachRestrictionForCarrier() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda41
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda28
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("addAttachRestrictionForCarrier() RemoteException:" + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda42
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda57
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$21, reason: invalid class name */
    class AnonymousClass21 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass21(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void removeAttachRestrictionForCarrier(
            int subId, int reason, Executor executor, final Consumer<Integer> resultListener) {
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                IIntegerConsumer errorCallback = new AnonymousClass22(executor, resultListener);
                telephony.removeAttachRestrictionForCarrier(subId, reason, errorCallback);
            } else {
                loge("removeAttachRestrictionForCarrier() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda11
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda0
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                r1.accept(23);
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("removeAttachRestrictionForCarrier() RemoteException:" + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda53
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(23);
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$22, reason: invalid class name */
    class AnonymousClass22 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass22(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int result) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            r1.accept(Integer.valueOf(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public Set<Integer> getAttachRestrictionReasonsForCarrier(int subId) {
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int[] receivedArray = telephony.getAttachRestrictionReasonsForCarrier(subId);
                if (receivedArray.length == 0) {
                    logd("receivedArray is empty, create empty set");
                    return new HashSet();
                }
                return (Set) Arrays.stream(receivedArray).boxed().collect(Collectors.toSet());
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("getAttachRestrictionReasonsForCarrier() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return new HashSet();
        }
    }

    public List<Integer> getSatelliteDisallowedReasons() {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                int[] receivedArray = telephony.getSatelliteDisallowedReasons();
                if (receivedArray.length == 0) {
                    logd("receivedArray is empty, create empty list");
                    return new ArrayList();
                }
                return (List) Arrays.stream(receivedArray).boxed().collect(Collectors.toList());
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("getSatelliteDisallowedReasons() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return new ArrayList();
        }
    }

    public void registerForSatelliteDisallowedReasonsChanged(
            Executor executor, SatelliteDisallowedReasonsCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteDisallowedReasonsCallback internalCallback =
                        new AnonymousClass23(executor, callback);
                telephony.registerForSatelliteDisallowedReasonsChanged(internalCallback);
                sSatelliteDisallowedReasonsCallbackMap.put(callback, internalCallback);
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForSatelliteDisallowedReasonsChanged() RemoteException" + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$23, reason: invalid class name */
    class AnonymousClass23 extends ISatelliteDisallowedReasonsCallback.Stub {
        final /* synthetic */ SatelliteDisallowedReasonsCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass23(
                Executor executor,
                SatelliteDisallowedReasonsCallback satelliteDisallowedReasonsCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteDisallowedReasonsCallback;
        }

        @Override // android.telephony.satellite.ISatelliteDisallowedReasonsCallback
        public void onSatelliteDisallowedReasonsChanged(final int[] disallowedReasons) {
            Executor executor = this.val$executor;
            final SatelliteDisallowedReasonsCallback satelliteDisallowedReasonsCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$23$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$23$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteDisallowedReasonsCallback.this
                                                    .onSatelliteDisallowedReasonsChanged(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForSatelliteDisallowedReasonsChanged(
            SatelliteDisallowedReasonsCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteDisallowedReasonsCallback internalCallback =
                sSatelliteDisallowedReasonsCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForSatelliteDisallowedReasonsChanged(internalCallback);
                    return;
                } else {
                    loge("unregisterForSatelliteDisallowedReasonsChanged: No internal callback.");
                    throw new IllegalArgumentException("callback is not valid");
                }
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForSatelliteDisallowedReasonsChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public void requestNtnSignalStrength(
            Executor executor,
            final OutcomeReceiver<NtnSignalStrength, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass24(null, executor, callback);
                telephony.requestNtnSignalStrength(receiver);
            } else {
                loge("requestNtnSignalStrength() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda61
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda47
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestNtnSignalStrength() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda62
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda20
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$24, reason: invalid class name */
    class AnonymousClass24 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass24(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_NTN_SIGNAL_STRENGTH)) {
                    final NtnSignalStrength ntnSignalStrength =
                            (NtnSignalStrength)
                                    resultData.getParcelable(
                                            SatelliteManager.KEY_NTN_SIGNAL_STRENGTH,
                                            NtnSignalStrength.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda3
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(r2);
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_NTN_SIGNAL_STRENGTH does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda5
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda4
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void registerForNtnSignalStrengthChanged(
            Executor executor, NtnSignalStrengthCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                INtnSignalStrengthCallback internalCallback =
                        new AnonymousClass25(executor, callback);
                telephony.registerForNtnSignalStrengthChanged(internalCallback);
                sNtnSignalStrengthCallbackMap.put(callback, internalCallback);
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForNtnSignalStrengthChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$25, reason: invalid class name */
    class AnonymousClass25 extends INtnSignalStrengthCallback.Stub {
        final /* synthetic */ NtnSignalStrengthCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass25(Executor executor, NtnSignalStrengthCallback ntnSignalStrengthCallback) {
            this.val$executor = executor;
            this.val$callback = ntnSignalStrengthCallback;
        }

        @Override // android.telephony.satellite.INtnSignalStrengthCallback
        public void onNtnSignalStrengthChanged(final NtnSignalStrength ntnSignalStrength) {
            Executor executor = this.val$executor;
            final NtnSignalStrengthCallback ntnSignalStrengthCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$25$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$25$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            NtnSignalStrengthCallback.this
                                                    .onNtnSignalStrengthChanged(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForNtnSignalStrengthChanged(NtnSignalStrengthCallback callback) {
        Objects.requireNonNull(callback);
        INtnSignalStrengthCallback internalCallback =
                sNtnSignalStrengthCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForNtnSignalStrengthChanged(internalCallback);
                    return;
                } else {
                    loge("unregisterForNtnSignalStrengthChanged: No internal callback.");
                    throw new IllegalArgumentException("callback is not valid");
                }
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForNtnSignalStrengthChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public int registerForCapabilitiesChanged(
            Executor executor, SatelliteCapabilitiesCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteCapabilitiesCallback internalCallback =
                        new AnonymousClass26(executor, callback);
                sSatelliteCapabilitiesCallbackMap.put(callback, internalCallback);
                return telephony.registerForCapabilitiesChanged(internalCallback);
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForCapabilitiesChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$26, reason: invalid class name */
    class AnonymousClass26 extends ISatelliteCapabilitiesCallback.Stub {
        final /* synthetic */ SatelliteCapabilitiesCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass26(
                Executor executor, SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteCapabilitiesCallback;
        }

        @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
        public void onSatelliteCapabilitiesChanged(final SatelliteCapabilities capabilities) {
            Executor executor = this.val$executor;
            final SatelliteCapabilitiesCallback satelliteCapabilitiesCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteCapabilitiesCallback.this
                                                    .onSatelliteCapabilitiesChanged(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForCapabilitiesChanged(SatelliteCapabilitiesCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteCapabilitiesCallback internalCallback =
                sSatelliteCapabilitiesCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForCapabilitiesChanged(internalCallback);
                } else {
                    loge("unregisterForCapabilitiesChanged: No internal callback.");
                }
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForCapabilitiesChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public List<String> getSatellitePlmnsForCarrier(int subId) {
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                return telephony.getSatellitePlmnsForCarrier(subId);
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException ex) {
            loge("getSatellitePlmnsForCarrier() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return new ArrayList();
        }
    }

    public int registerForSupportedStateChanged(
            Executor executor, SatelliteSupportedStateCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteSupportedStateCallback internalCallback =
                        new AnonymousClass27(executor, callback);
                sSatelliteSupportedStateCallbackMap.put(callback, internalCallback);
                return telephony.registerForSatelliteSupportedStateChanged(internalCallback);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForSupportedStateChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$27, reason: invalid class name */
    class AnonymousClass27 extends ISatelliteSupportedStateCallback.Stub {
        final /* synthetic */ SatelliteSupportedStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass27(
                Executor executor,
                SatelliteSupportedStateCallback satelliteSupportedStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteSupportedStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteSupportedStateCallback
        public void onSatelliteSupportedStateChanged(final boolean supported) {
            Executor executor = this.val$executor;
            final SatelliteSupportedStateCallback satelliteSupportedStateCallback =
                    this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$27$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$27$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteSupportedStateCallback.this
                                                    .onSatelliteSupportedStateChanged(r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForSupportedStateChanged(SatelliteSupportedStateCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteSupportedStateCallback internalCallback =
                sSatelliteSupportedStateCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForSatelliteSupportedStateChanged(internalCallback);
                } else {
                    loge("unregisterForSupportedStateChanged: No internal callback.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForSupportedStateChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public int registerForCommunicationAllowedStateChanged(
            Executor executor, SatelliteCommunicationAllowedStateCallback callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ISatelliteCommunicationAllowedStateCallback internalCallback =
                        new AnonymousClass28(executor, callback);
                sSatelliteCommunicationAllowedStateCallbackMap.put(callback, internalCallback);
                return telephony.registerForCommunicationAllowedStateChanged(
                        this.mSubId, internalCallback);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("registerForCommunicationAllowedStateChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$28, reason: invalid class name */
    class AnonymousClass28 extends ISatelliteCommunicationAllowedStateCallback.Stub {
        final /* synthetic */ SatelliteCommunicationAllowedStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass28(
                Executor executor,
                SatelliteCommunicationAllowedStateCallback
                        satelliteCommunicationAllowedStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteCommunicationAllowedStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteCommunicationAllowedStateCallback
        public void onSatelliteCommunicationAllowedStateChanged(final boolean isAllowed) {
            Executor executor = this.val$executor;
            final SatelliteCommunicationAllowedStateCallback
                    satelliteCommunicationAllowedStateCallback = this.val$callback;
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$28$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$28$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            SatelliteCommunicationAllowedStateCallback.this
                                                    .onSatelliteCommunicationAllowedStateChanged(
                                                            r2);
                                        }
                                    });
                        }
                    });
        }
    }

    public void unregisterForCommunicationAllowedStateChanged(
            SatelliteCommunicationAllowedStateCallback callback) {
        Objects.requireNonNull(callback);
        ISatelliteCommunicationAllowedStateCallback internalCallback =
                sSatelliteCommunicationAllowedStateCallbackMap.remove(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                if (internalCallback != null) {
                    telephony.unregisterForCommunicationAllowedStateChanged(
                            this.mSubId, internalCallback);
                } else {
                    loge("unregisterForCommunicationAllowedStateChanged: No internal callback.");
                }
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("unregisterForCommunicationAllowedStateChanged() RemoteException: " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public void requestSessionStats(
            Executor executor,
            final OutcomeReceiver<SatelliteSessionStats, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass29(null, executor, callback);
                telephony.requestSatelliteSessionStats(this.mSubId, receiver);
            } else {
                loge("requestSessionStats() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda14
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda84
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestSessionStats() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda13
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$29, reason: invalid class name */
    class AnonymousClass29 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass29(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_SESSION_STATS)) {
                    final SatelliteSessionStats stats =
                            (SatelliteSessionStats)
                                    resultData.getParcelable(
                                            SatelliteManager.KEY_SESSION_STATS,
                                            SatelliteSessionStats.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda0
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(r2);
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_SESSION_STATS does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda5
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda4
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void requestSatelliteSubscriberProvisionStatus(
            Executor executor,
            final OutcomeReceiver<List<SatelliteSubscriberProvisionStatus>, SatelliteException>
                    callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass30(null, executor, callback);
                telephony.requestSatelliteSubscriberProvisionStatus(receiver);
            } else {
                loge("requestSatelliteSubscriberProvisionStatus() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda31
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda8
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("requestSatelliteSubscriberProvisionStatus() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda32
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda18
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$30, reason: invalid class name */
    class AnonymousClass30 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass30(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(
                        SatelliteManager.KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN)) {
                    final List<SatelliteSubscriberProvisionStatus> list =
                            resultData.getParcelableArrayList(
                                    SatelliteManager.KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN,
                                    SatelliteSubscriberProvisionStatus.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda0
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(r2);
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda1
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda5
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void provisionSatellite(
            List<SatelliteSubscriberInfo> list,
            Executor executor,
            final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass31(null, executor, callback);
                telephony.provisionSatellite(list, receiver);
            } else {
                loge("provisionSatellite() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda69
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda80
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("provisionSatellite() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda70
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda55
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$31, reason: invalid class name */
    class AnonymousClass31 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass31(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_PROVISION_SATELLITE_TOKENS)) {
                    final boolean isUpdated =
                            resultData.getBoolean(SatelliteManager.KEY_PROVISION_SATELLITE_TOKENS);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda3
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_REQUEST_PROVISION_TOKENS does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda5
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda4
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void deprovisionSatellite(
            List<SatelliteSubscriberInfo> list,
            Executor executor,
            final OutcomeReceiver<Boolean, SatelliteException> callback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(callback);
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                ResultReceiver receiver = new AnonymousClass32(null, executor, callback);
                telephony.deprovisionSatellite(list, receiver);
            } else {
                loge("deprovisionSatellite() invalid telephony");
                executor.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda77
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda52
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(
                                                                23));
                                            }
                                        });
                            }
                        });
            }
        } catch (RemoteException ex) {
            loge("deprovisionSatellite() RemoteException: " + ex);
            executor.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda78
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda24
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(23));
                                        }
                                    });
                        }
                    });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$32, reason: invalid class name */
    class AnonymousClass32 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass32(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                if (resultData.containsKey(SatelliteManager.KEY_DEPROVISION_SATELLITE_TOKENS)) {
                    final boolean isUpdated =
                            resultData.getBoolean(
                                    SatelliteManager.KEY_DEPROVISION_SATELLITE_TOKENS);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils
                                                    .ThrowingRunnable() { // from class:
                                                                          // android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda2
                                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                                public final void runOrThrow() {
                                                    OutcomeReceiver.this.onResult(
                                                            Boolean.valueOf(r2));
                                                }
                                            });
                                }
                            });
                    return;
                }
                SatelliteManager.loge("KEY_DEPROVISION_SATELLITE_TOKENS does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                Binder.withCleanCallingIdentity(
                                        new FunctionalUtils
                                                .ThrowingRunnable() { // from class:
                                                                      // android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda0
                                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                            public final void runOrThrow() {
                                                OutcomeReceiver.this.onError(
                                                        new SatelliteManager.SatelliteException(9));
                                            }
                                        });
                            }
                        });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(
                                    new FunctionalUtils
                                            .ThrowingRunnable() { // from class:
                                                                  // android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda1
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            OutcomeReceiver.this.onError(
                                                    new SatelliteManager.SatelliteException(r2));
                                        }
                                    });
                        }
                    });
        }
    }

    public void setNtnSmsSupported(boolean ntnSmsSupported) {
        try {
            ITelephony telephony = getITelephony();
            if (telephony != null) {
                telephony.setNtnSmsSupported(ntnSmsSupported);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("setNtnSmsSupported() RemoteException:" + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public boolean getNtnSmsSupported() {
        try {
            ISemTelephony semTelephony = getISemTelephony();
            if (semTelephony != null) {
                return semTelephony.getNtnSmsSupported();
            }
            loge("getNtnSmsSupported() invalid semTelephony");
            return false;
        } catch (RemoteException ex) {
            loge("getNtnSmsSupported() RemoteException: " + ex);
            return false;
        }
    }

    private static ISemTelephony getISemTelephony() {
        return ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
    }

    private static ITelephony getITelephony() {
        ITelephony binder =
                ITelephony.Stub.asInterface(
                        TelephonyFrameworkInitializer.getTelephonyServiceManager()
                                .getTelephonyServiceRegisterer()
                                .get());
        return binder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logd(String log) {
        Rlog.d(TAG, log);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(String log) {
        Rlog.e(TAG, log);
    }
}
