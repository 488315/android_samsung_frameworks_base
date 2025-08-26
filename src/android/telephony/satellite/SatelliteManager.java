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
import android.telephony.TelephonyRegistryManager;
import android.telephony.satellite.INtnSignalStrengthCallback;
import android.telephony.satellite.ISatelliteCapabilitiesCallback;
import android.telephony.satellite.ISatelliteCommunicationAccessStateCallback;
import android.telephony.satellite.ISatelliteDatagramCallback;
import android.telephony.satellite.ISatelliteDisallowedReasonsCallback;
import android.telephony.satellite.ISatelliteModemStateCallback;
import android.telephony.satellite.ISatelliteProvisionStateCallback;
import android.telephony.satellite.ISatelliteTransmissionUpdateCallback;
import android.telephony.satellite.ISelectedNbIotSatelliteSubscriptionCallback;
import android.telephony.satellite.SatelliteManager;
import com.android.internal.telephony.IBooleanConsumer;
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

/* loaded from: classes4.dex */
public final class SatelliteManager {

    @SystemApi
    public static final String ACTION_SATELLITE_START_NON_EMERGENCY_SESSION = "android.telephony.satellite.action.SATELLITE_START_NON_EMERGENCY_SESSION";

    @SystemApi
    public static final String ACTION_SATELLITE_SUBSCRIBER_ID_LIST_CHANGED = "android.telephony.satellite.action.SATELLITE_SUBSCRIBER_ID_LIST_CHANGED";

    @SystemApi
    public static final int DATAGRAM_TYPE_CHECK_PENDING_INCOMING_SMS = 7;

    @SystemApi
    public static final int DATAGRAM_TYPE_KEEP_ALIVE = 3;

    @SystemApi
    public static final int DATAGRAM_TYPE_LAST_SOS_MESSAGE_NO_HELP_NEEDED = 5;

    @SystemApi
    public static final int DATAGRAM_TYPE_LAST_SOS_MESSAGE_STILL_NEED_HELP = 4;

    @SystemApi
    public static final int DATAGRAM_TYPE_LOCATION_SHARING = 2;

    @SystemApi
    public static final int DATAGRAM_TYPE_SMS = 6;

    @SystemApi
    public static final int DATAGRAM_TYPE_SOS_MESSAGE = 1;

    @SystemApi
    public static final int DATAGRAM_TYPE_UNKNOWN = 0;

    @SystemApi
    public static final int DEVICE_HOLD_POSITION_LANDSCAPE_LEFT = 2;

    @SystemApi
    public static final int DEVICE_HOLD_POSITION_LANDSCAPE_RIGHT = 3;

    @SystemApi
    public static final int DEVICE_HOLD_POSITION_PORTRAIT = 1;

    @SystemApi
    public static final int DEVICE_HOLD_POSITION_UNKNOWN = 0;

    @SystemApi
    public static final int DISPLAY_MODE_CLOSED = 3;

    @SystemApi
    public static final int DISPLAY_MODE_FIXED = 1;

    @SystemApi
    public static final int DISPLAY_MODE_OPENED = 2;

    @SystemApi
    public static final int DISPLAY_MODE_UNKNOWN = 0;

    @SystemApi
    public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_SOS = 1;

    @SystemApi
    public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_T911 = 2;
    public static final String KEY_DEMO_MODE_ENABLED = "demo_mode_enabled";
    public static final String KEY_DEPROVISION_SATELLITE_TOKENS = "deprovision_satellite";
    public static final String KEY_EMERGENCY_MODE_ENABLED = "emergency_mode_enabled";
    public static final String KEY_NTN_SIGNAL_STRENGTH = "ntn_signal_strength";
    public static final String KEY_PROVISION_SATELLITE_TOKENS = "provision_satellite";
    public static final String KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN = "request_provision_subscriber_id";
    public static final String KEY_SATELLITE_ACCESS_CONFIGURATION = "satellite_access_configuration";
    public static final String KEY_SATELLITE_CAPABILITIES = "satellite_capabilities";
    public static final String KEY_SATELLITE_COMMUNICATION_ALLOWED = "satellite_communication_allowed";
    public static final String KEY_SATELLITE_DISPLAY_NAME = "satellite_display_name";
    public static final String KEY_SATELLITE_ENABLED = "satellite_enabled";
    public static final String KEY_SATELLITE_NEXT_VISIBILITY = "satellite_next_visibility";
    public static final String KEY_SATELLITE_PROVISIONED = "satellite_provisioned";
    public static final String KEY_SATELLITE_SUPPORTED = "satellite_supported";
    public static final String KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID = "selected_nb_iot_satellite_subscription_id";
    public static final String KEY_SESSION_STATS = "session_stats";
    public static final String KEY_SESSION_STATS_V2 = "session_stats_v2";
    public static final String METADATA_SATELLITE_MANUAL_CONNECT_P2P_SUPPORT = "android.telephony.METADATA_SATELLITE_MANUAL_CONNECT_P2P_SUPPORT";

    @SystemApi
    public static final int NT_RADIO_TECHNOLOGY_EMTC_NTN = 3;

    @SystemApi
    public static final int NT_RADIO_TECHNOLOGY_NB_IOT_NTN = 1;

    @SystemApi
    public static final int NT_RADIO_TECHNOLOGY_NR_NTN = 2;

    @SystemApi
    public static final int NT_RADIO_TECHNOLOGY_PROPRIETARY = 4;

    @SystemApi
    public static final int NT_RADIO_TECHNOLOGY_UNKNOWN = 0;
    public static final String PROPERTY_SATELLITE_DATA_OPTIMIZED = "android.telephony.PROPERTY_SATELLITE_DATA_OPTIMIZED";

    @SystemApi
    public static final String PROPERTY_SATELLITE_MANUAL_CONNECT_P2P_SUPPORT = "android.telephony.satellite.PROPERTY_SATELLITE_MANUAL_CONNECT_P2P_SUPPORT";

    @SystemApi
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_ENTITLEMENT = 2;

    @SystemApi
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_GEOLOCATION = 1;

    @SystemApi
    public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_USER = 0;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_IDLE = 0;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_FAILED = 7;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_NONE = 6;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_SUCCESS = 5;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVING = 4;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SENDING = 1;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_FAILED = 3;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_SUCCESS = 2;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_UNKNOWN = -1;

    @SystemApi
    public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_WAITING_TO_CONNECT = 8;

    @SystemApi
    public static final int SATELLITE_DATA_SUPPORT_CONSTRAINED = 1;

    @SystemApi
    public static final int SATELLITE_DATA_SUPPORT_RESTRICTED = 0;

    @SystemApi
    public static final int SATELLITE_DATA_SUPPORT_UNCONSTRAINED = 2;

    @SystemApi
    public static final int SATELLITE_DATA_SUPPORT_UNKNOWN = -1;
    public static final int SATELLITE_DISALLOWED_REASON_LOCATION_DISABLED = 4;
    public static final int SATELLITE_DISALLOWED_REASON_NOT_IN_ALLOWED_REGION = 2;
    public static final int SATELLITE_DISALLOWED_REASON_NOT_PROVISIONED = 1;
    public static final int SATELLITE_DISALLOWED_REASON_NOT_SUPPORTED = 0;
    public static final int SATELLITE_DISALLOWED_REASON_UNSUPPORTED_DEFAULT_MSG_APP = 3;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_CONNECTED = 7;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_DATAGRAM_RETRYING = 3;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_DATAGRAM_TRANSFERRING = 2;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_DISABLING_SATELLITE = 9;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_ENABLING_SATELLITE = 8;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_IDLE = 0;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_LISTENING = 1;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_NOT_CONNECTED = 6;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_OFF = 4;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_UNAVAILABLE = 5;

    @SystemApi
    public static final int SATELLITE_MODEM_STATE_UNKNOWN = -1;

    @SystemApi
    public static final int SATELLITE_RESULT_ACCESS_BARRED = 16;

    @SystemApi
    public static final int SATELLITE_RESULT_DISABLE_IN_PROGRESS = 28;

    @SystemApi
    public static final int SATELLITE_RESULT_EMERGENCY_CALL_IN_PROGRESS = 27;

    @SystemApi
    public static final int SATELLITE_RESULT_ENABLE_IN_PROGRESS = 29;

    @SystemApi
    public static final int SATELLITE_RESULT_ERROR = 1;

    @SystemApi
    public static final int SATELLITE_RESULT_ILLEGAL_STATE = 23;

    @SystemApi
    public static final int SATELLITE_RESULT_INVALID_ARGUMENTS = 8;

    @SystemApi
    public static final int SATELLITE_RESULT_INVALID_MODEM_STATE = 7;

    @SystemApi
    public static final int SATELLITE_RESULT_INVALID_TELEPHONY_STATE = 6;

    @SystemApi
    public static final int SATELLITE_RESULT_LOCATION_DISABLED = 25;

    @SystemApi
    public static final int SATELLITE_RESULT_LOCATION_NOT_AVAILABLE = 26;

    @SystemApi
    public static final int SATELLITE_RESULT_MODEM_BUSY = 22;

    @SystemApi
    public static final int SATELLITE_RESULT_MODEM_ERROR = 4;

    @SystemApi
    public static final int SATELLITE_RESULT_MODEM_TIMEOUT = 24;

    @SystemApi
    public static final int SATELLITE_RESULT_NETWORK_ERROR = 5;

    @SystemApi
    public static final int SATELLITE_RESULT_NETWORK_TIMEOUT = 17;

    @SystemApi
    public static final int SATELLITE_RESULT_NOT_AUTHORIZED = 19;

    @SystemApi
    public static final int SATELLITE_RESULT_NOT_REACHABLE = 18;

    @SystemApi
    public static final int SATELLITE_RESULT_NOT_SUPPORTED = 20;

    @SystemApi
    public static final int SATELLITE_RESULT_NO_RESOURCES = 12;

    @SystemApi
    public static final int SATELLITE_RESULT_NO_VALID_SATELLITE_SUBSCRIPTION = 30;

    @SystemApi
    public static final int SATELLITE_RESULT_RADIO_NOT_AVAILABLE = 10;

    @SystemApi
    public static final int SATELLITE_RESULT_REQUEST_ABORTED = 15;

    @SystemApi
    public static final int SATELLITE_RESULT_REQUEST_FAILED = 9;

    @SystemApi
    public static final int SATELLITE_RESULT_REQUEST_IN_PROGRESS = 21;

    @SystemApi
    public static final int SATELLITE_RESULT_REQUEST_NOT_SUPPORTED = 11;

    @SystemApi
    public static final int SATELLITE_RESULT_SERVER_ERROR = 2;

    @SystemApi
    public static final int SATELLITE_RESULT_SERVICE_ERROR = 3;

    @SystemApi
    public static final int SATELLITE_RESULT_SERVICE_NOT_PROVISIONED = 13;

    @SystemApi
    public static final int SATELLITE_RESULT_SERVICE_PROVISION_IN_PROGRESS = 14;

    @SystemApi
    public static final int SATELLITE_RESULT_SUCCESS = 0;
    private static final String TAG = "SatelliteManager";
    private final Context mContext;
    private final int mSubId;
    private TelephonyRegistryManager mTelephonyRegistryMgr;
    private static final ConcurrentHashMap<SatelliteDatagramCallback, ISatelliteDatagramCallback> sSatelliteDatagramCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SatelliteProvisionStateCallback, ISatelliteProvisionStateCallback> sSatelliteProvisionStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SatelliteModemStateCallback, ISatelliteModemStateCallback> sSatelliteModemStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SatelliteTransmissionUpdateCallback, ISatelliteTransmissionUpdateCallback> sSatelliteTransmissionUpdateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<NtnSignalStrengthCallback, INtnSignalStrengthCallback> sNtnSignalStrengthCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SatelliteCapabilitiesCallback, ISatelliteCapabilitiesCallback> sSatelliteCapabilitiesCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Consumer<Boolean>, IBooleanConsumer> sSatelliteSupportedStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SatelliteCommunicationAccessStateCallback, ISatelliteCommunicationAccessStateCallback> sSatelliteCommunicationAccessStateCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SatelliteDisallowedReasonsCallback, ISatelliteDisallowedReasonsCallback> sSatelliteDisallowedReasonsCallbackMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<SelectedNbIotSatelliteSubscriptionCallback, ISelectedNbIotSatelliteSubscriptionCallback> sSelectedNbIotSatelliteSubscriptionCallbackMap = new ConcurrentHashMap<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DatagramType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceHoldPosition {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NTRadioTechnology {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteCommunicationRestrictionReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteDataSupportMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteDatagramTransferState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteDisallowedReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteModemState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteResult {
    }

    public SatelliteManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    private SatelliteManager(Context context, int i) {
        this.mContext = context;
        this.mSubId = i;
    }

    @SystemApi
    public static class SatelliteException extends Exception {
        private final int mErrorCode;

        public SatelliteException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public void registerStateChangeListener(Executor executor, SatelliteStateChangeListener satelliteStateChangeListener) {
        Context context = this.mContext;
        if (context == null) {
            throw new IllegalStateException("Telephony service is null");
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) context.getSystemService(TelephonyRegistryManager.class);
        this.mTelephonyRegistryMgr = telephonyRegistryManager;
        if (telephonyRegistryManager == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        telephonyRegistryManager.addSatelliteStateChangeListener(executor, satelliteStateChangeListener);
    }

    public void unregisterStateChangeListener(SatelliteStateChangeListener satelliteStateChangeListener) {
        Context context = this.mContext;
        if (context == null) {
            throw new IllegalStateException("Telephony service is null");
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) context.getSystemService(TelephonyRegistryManager.class);
        this.mTelephonyRegistryMgr = telephonyRegistryManager;
        if (telephonyRegistryManager == null) {
            throw new IllegalStateException("Telephony registry service is null");
        }
        telephonyRegistryManager.removeSatelliteStateChangeListener(satelliteStateChangeListener);
    }

    @SystemApi
    public void requestEnabled(EnableRequestAttributes enableRequestAttributes, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(enableRequestAttributes);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                Rlog.e(TAG, "requestEnabled() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda17
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            } else {
                iTelephony.requestSatelliteEnabled(enableRequestAttributes.isEnabled(), enableRequestAttributes.isDemoMode(), enableRequestAttributes.isEmergencyMode(), new AnonymousClass1(this, executor, consumer));
            }
        } catch (RemoteException e) {
            Rlog.e(TAG, "requestEnabled() exception: ", e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda70
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
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

        AnonymousClass1(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestIsEnabled(Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsSatelliteEnabled(new AnonymousClass2(this, null, executor, outcomeReceiver));
            } else {
                loge("requestIsEnabled() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda97
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda77
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestIsEnabled() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda98
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda76
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass2(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey("satellite_enabled")) {
                    final boolean z = bundle.getBoolean("satellite_enabled");
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Boolean.valueOf(z));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_ENABLED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda0
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestIsDemoModeEnabled(Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsDemoModeEnabled(new AnonymousClass3(this, null, executor, outcomeReceiver));
            } else {
                loge("requestIsDemoModeEnabled() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda45
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestIsDemoModeEnabled() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass3(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_DEMO_MODE_ENABLED)) {
                    final boolean z = bundle.getBoolean(SatelliteManager.KEY_DEMO_MODE_ENABLED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Boolean.valueOf(z));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_DEMO_MODE_ENABLED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda2
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestIsEmergencyModeEnabled(Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsEmergencyModeEnabled(new AnonymousClass4(this, null, executor, outcomeReceiver));
            } else {
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda28
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda100
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestIsEmergencyModeEnabled() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$4, reason: invalid class name */
    class AnonymousClass4 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_EMERGENCY_MODE_ENABLED)) {
                    final boolean z = bundle.getBoolean(SatelliteManager.KEY_EMERGENCY_MODE_ENABLED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Boolean.valueOf(z));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_EMERGENCY_MODE_ENABLED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda3
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestIsSupported(Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsSatelliteSupported(new AnonymousClass5(this, null, executor, outcomeReceiver));
            } else {
                loge("requestIsSupported() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda52
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda93
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestIsSupported() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda69
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass5(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_SUPPORTED)) {
                    final boolean z = bundle.getBoolean(SatelliteManager.KEY_SATELLITE_SUPPORTED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda2
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Boolean.valueOf(z));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_SUPPORTED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda1
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestCapabilities(Executor executor, final OutcomeReceiver<SatelliteCapabilities, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSatelliteCapabilities(new AnonymousClass6(this, null, executor, outcomeReceiver));
            } else {
                loge("requestCapabilities() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda59
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda65
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestCapabilities() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda20
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass6(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_CAPABILITIES)) {
                    final SatelliteCapabilities satelliteCapabilities = (SatelliteCapabilities) bundle.getParcelable(SatelliteManager.KEY_SATELLITE_CAPABILITIES, SatelliteCapabilities.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(satelliteCapabilities);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_CAPABILITIES does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda4
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void startTransmissionUpdates(Executor executor, final Consumer<Integer> consumer, SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(satelliteTransmissionUpdateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass7 anonymousClass7 = new AnonymousClass7(this, executor, consumer);
                AnonymousClass8 anonymousClass8 = new AnonymousClass8(this, executor, satelliteTransmissionUpdateCallback);
                sSatelliteTransmissionUpdateCallbackMap.put(satelliteTransmissionUpdateCallback, anonymousClass8);
                iTelephony.startSatelliteTransmissionUpdates(anonymousClass7, anonymousClass8);
                return;
            }
            loge("startTransmissionUpdates() invalid telephony");
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda83
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
                        }
                    });
                }
            });
        } catch (RemoteException e) {
            loge("startTransmissionUpdates() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda91
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
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

        AnonymousClass7(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
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

        AnonymousClass8(SatelliteManager satelliteManager, Executor executor, SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteTransmissionUpdateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSatellitePositionChanged(final PointingInfo pointingInfo) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteTransmissionUpdateCallback.onSatellitePositionChanged(pointingInfo);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramStateChanged(final int i, final int i2, final int i3, final int i4) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteTransmissionUpdateCallback.onSendDatagramStateChanged(i, i, i, i);
                        }
                    });
                }
            });
            Executor executor2 = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback2 = this.val$callback;
            executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteTransmissionUpdateCallback.onSendDatagramStateChanged(i, i, i);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onReceiveDatagramStateChanged(final int i, final int i2, final int i3) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda7
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteTransmissionUpdateCallback.onReceiveDatagramStateChanged(i, i, i);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
        public void onSendDatagramRequested(final int i) {
            Executor executor = this.val$executor;
            final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda9
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteTransmissionUpdateCallback.onSendDatagramRequested(i);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void stopTransmissionUpdates(SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(satelliteTransmissionUpdateCallback);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallbackRemove = sSatelliteTransmissionUpdateCallbackMap.remove(satelliteTransmissionUpdateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                loge("stopTransmissionUpdates() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda43
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda68
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            } else if (iSatelliteTransmissionUpdateCallbackRemove != null) {
                iTelephony.stopSatelliteTransmissionUpdates(new AnonymousClass9(this, executor, consumer), iSatelliteTransmissionUpdateCallbackRemove);
            } else {
                loge("stopSatelliteTransmissionUpdates: No internal callback.");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda42
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda27
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(8);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("stopTransmissionUpdates() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda22
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
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

        AnonymousClass9(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void provisionService(String str, byte[] bArr, CancellationSignal cancellationSignal, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(bArr);
        ICancellationSignal iCancellationSignalProvisionSatelliteService = null;
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iCancellationSignalProvisionSatelliteService = iTelephony.provisionSatelliteService(str, bArr, new AnonymousClass10(this, executor, consumer));
            } else {
                loge("provisionService() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda95
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda86
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("provisionService() RemoteException=" + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda96
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
                        }
                    });
                }
            });
        }
        if (cancellationSignal != null) {
            cancellationSignal.setRemote(iCancellationSignalProvisionSatelliteService);
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$10, reason: invalid class name */
    class AnonymousClass10 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass10(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void deprovisionService(String str, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.deprovisionSatelliteService(str, new AnonymousClass11(this, executor, consumer));
            } else {
                loge("deprovisionService() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda34
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda31
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("deprovisionService() RemoteException ex=" + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda49
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
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

        AnonymousClass11(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public int registerForProvisionStateChanged(Executor executor, SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(satelliteProvisionStateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass12 anonymousClass12 = new AnonymousClass12(this, executor, satelliteProvisionStateCallback);
                sSatelliteProvisionStateCallbackMap.put(satelliteProvisionStateCallback, anonymousClass12);
                return iTelephony.registerForSatelliteProvisionStateChanged(anonymousClass12);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForProvisionStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$12, reason: invalid class name */
    class AnonymousClass12 extends ISatelliteProvisionStateCallback.Stub {
        final /* synthetic */ SatelliteProvisionStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass12(SatelliteManager satelliteManager, Executor executor, SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteProvisionStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteProvisionStateChanged(final boolean z) {
            Executor executor = this.val$executor;
            final SatelliteProvisionStateCallback satelliteProvisionStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteProvisionStateCallback.onSatelliteProvisionStateChanged(z);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
        public void onSatelliteSubscriptionProvisionStateChanged(final List<SatelliteSubscriberProvisionStatus> list) {
            Executor executor = this.val$executor;
            final SatelliteProvisionStateCallback satelliteProvisionStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteProvisionStateCallback.onSatelliteSubscriptionProvisionStateChanged(list);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForProvisionStateChanged(SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
        Objects.requireNonNull(satelliteProvisionStateCallback);
        ISatelliteProvisionStateCallback iSatelliteProvisionStateCallbackRemove = sSatelliteProvisionStateCallbackMap.remove(satelliteProvisionStateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            if (iSatelliteProvisionStateCallbackRemove != null) {
                iTelephony.unregisterForSatelliteProvisionStateChanged(iSatelliteProvisionStateCallbackRemove);
            } else {
                loge("unregisterForProvisionStateChanged: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForProvisionStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void requestIsProvisioned(Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsSatelliteProvisioned(new AnonymousClass13(this, null, executor, outcomeReceiver));
            } else {
                loge("requestIsProvisioned() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda61
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda78
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestIsProvisioned() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda62
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass13(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_PROVISIONED)) {
                    final boolean z = bundle.getBoolean(SatelliteManager.KEY_SATELLITE_PROVISIONED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Boolean.valueOf(z));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_PROVISIONED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda4
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public int registerForModemStateChanged(Executor executor, SatelliteModemStateCallback satelliteModemStateCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(satelliteModemStateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass14 anonymousClass14 = new AnonymousClass14(this, executor, satelliteModemStateCallback);
                sSatelliteModemStateCallbackMap.put(satelliteModemStateCallback, anonymousClass14);
                return iTelephony.registerForSatelliteModemStateChanged(anonymousClass14);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForModemStateChanged() RemoteException:" + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$14, reason: invalid class name */
    class AnonymousClass14 extends ISatelliteModemStateCallback.Stub {
        final /* synthetic */ SatelliteModemStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass14(SatelliteManager satelliteManager, Executor executor, SatelliteModemStateCallback satelliteModemStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteModemStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(final int i) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteModemStateCallback.onSatelliteModemStateChanged(i);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onEmergencyModeChanged(final boolean z) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteModemStateCallback.onEmergencyModeChanged(z);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onRegistrationFailure(final int i) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteModemStateCallback.onRegistrationFailure(i);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onTerrestrialNetworkAvailableChanged(final boolean z) {
            Executor executor = this.val$executor;
            final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteModemStateCallback.onTerrestrialNetworkAvailableChanged(z);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForModemStateChanged(SatelliteModemStateCallback satelliteModemStateCallback) {
        Objects.requireNonNull(satelliteModemStateCallback);
        ISatelliteModemStateCallback iSatelliteModemStateCallbackRemove = sSatelliteModemStateCallbackMap.remove(satelliteModemStateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            if (iSatelliteModemStateCallbackRemove != null) {
                iTelephony.unregisterForModemStateChanged(iSatelliteModemStateCallbackRemove);
            } else {
                loge("unregisterForModemStateChanged: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForModemStateChanged() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public int registerForIncomingDatagram(Executor executor, SatelliteDatagramCallback satelliteDatagramCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(satelliteDatagramCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass15 anonymousClass15 = new AnonymousClass15(this, executor, satelliteDatagramCallback);
                sSatelliteDatagramCallbackMap.put(satelliteDatagramCallback, anonymousClass15);
                return iTelephony.registerForIncomingDatagram(anonymousClass15);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForIncomingDatagram() RemoteException:" + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$15, reason: invalid class name */
    class AnonymousClass15 extends ISatelliteDatagramCallback.Stub {
        final /* synthetic */ SatelliteDatagramCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass15(SatelliteManager satelliteManager, Executor executor, SatelliteDatagramCallback satelliteDatagramCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteDatagramCallback;
        }

        @Override // android.telephony.satellite.ISatelliteDatagramCallback
        public void onSatelliteDatagramReceived(final long j, final SatelliteDatagram satelliteDatagram, final int i, final IVoidConsumer iVoidConsumer) {
            final Consumer<Void> consumer = new Consumer<Void>(this) { // from class: android.telephony.satellite.SatelliteManager.15.1
                @Override // java.util.function.Consumer
                public void accept(Void r2) {
                    try {
                        iVoidConsumer.accept();
                    } catch (RemoteException e) {
                        SatelliteManager.logd("onSatelliteDatagramReceived RemoteException: " + e);
                    }
                }
            };
            Executor executor = this.val$executor;
            final SatelliteDatagramCallback satelliteDatagramCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteDatagramCallback.onSatelliteDatagramReceived(j, satelliteDatagram, i, consumer);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForIncomingDatagram(SatelliteDatagramCallback satelliteDatagramCallback) {
        Objects.requireNonNull(satelliteDatagramCallback);
        ISatelliteDatagramCallback iSatelliteDatagramCallbackRemove = sSatelliteDatagramCallbackMap.remove(satelliteDatagramCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            if (iSatelliteDatagramCallbackRemove != null) {
                iTelephony.unregisterForIncomingDatagram(iSatelliteDatagramCallbackRemove);
            } else {
                loge("unregisterForIncomingDatagram: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForIncomingDatagram() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void pollPendingDatagrams(Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.pollPendingDatagrams(new AnonymousClass16(this, executor, consumer));
            } else {
                loge("pollPendingDatagrams() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda84
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda18
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("pollPendingDatagrams() RemoteException:" + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda85
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda90
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
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

        AnonymousClass16(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void sendDatagram(int i, SatelliteDatagram satelliteDatagram, boolean z, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(satelliteDatagram);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.sendDatagram(i, satelliteDatagram, z, new AnonymousClass17(this, executor, consumer));
            } else {
                loge("sendDatagram() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda55
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda46
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("sendDatagram() RemoteException:" + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda99
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
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

        AnonymousClass17(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestIsCommunicationAllowedForCurrentLocation(Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestIsCommunicationAllowedForCurrentLocation(this.mSubId, new AnonymousClass18(this, null, executor, outcomeReceiver));
            } else {
                loge("requestIsCommunicationAllowedForCurrentLocation() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda40
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda89
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestIsCommunicationAllowedForCurrentLocation() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda92
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass18(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED)) {
                    final boolean z = bundle.getBoolean(SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Boolean.valueOf(z));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_COMMUNICATION_ALLOWED does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda3
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestSatelliteAccessConfigurationForCurrentLocation(Executor executor, final OutcomeReceiver<SatelliteAccessConfiguration, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSatelliteAccessConfigurationForCurrentLocation(new AnonymousClass19(this, null, executor, outcomeReceiver));
            } else {
                loge("requestSatelliteAccessConfigurationForCurrentLocation() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda39
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestSatelliteAccessConfigurationForCurrentLocation() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda33
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass19(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_ACCESS_CONFIGURATION)) {
                    final SatelliteAccessConfiguration satelliteAccessConfiguration = (SatelliteAccessConfiguration) bundle.getParcelable(SatelliteManager.KEY_SATELLITE_ACCESS_CONFIGURATION, SatelliteAccessConfiguration.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(satelliteAccessConfiguration);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_ACCESS_CONFIGURATION does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda5
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestTimeForNextSatelliteVisibility(Executor executor, final OutcomeReceiver<Duration, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestTimeForNextSatelliteVisibility(new AnonymousClass20(this, null, executor, outcomeReceiver));
            } else {
                loge("requestTimeForNextSatelliteVisibility() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda19
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestTimeForNextSatelliteVisibility() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda29
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass20(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY)) {
                    final int i2 = bundle.getInt(SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda3
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Duration.ofSeconds(i));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_NEXT_VISIBILITY does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda4
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestSelectedNbIotSatelliteSubscriptionId(Executor executor, final OutcomeReceiver<Integer, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSelectedNbIotSatelliteSubscriptionId(new AnonymousClass21(this, null, executor, outcomeReceiver));
            } else {
                loge("requestSelectedNbIotSatelliteSubscriptionId() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda81
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda94
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestSelectedNbIotSatelliteSubscriptionId() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda82
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda48
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$21, reason: invalid class name */
    class AnonymousClass21 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass21(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID)) {
                    final int i2 = bundle.getInt(SatelliteManager.KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(Integer.valueOf(i));
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SELECTED_NB_IOT_SATELLITE_SUBSCRIPTION_ID does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda3
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$21$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public int registerForSelectedNbIotSatelliteSubscriptionChanged(Executor executor, SelectedNbIotSatelliteSubscriptionCallback selectedNbIotSatelliteSubscriptionCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(selectedNbIotSatelliteSubscriptionCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass22 anonymousClass22 = new AnonymousClass22(this, executor, selectedNbIotSatelliteSubscriptionCallback);
                sSelectedNbIotSatelliteSubscriptionCallbackMap.put(selectedNbIotSatelliteSubscriptionCallback, anonymousClass22);
                return iTelephony.registerForSelectedNbIotSatelliteSubscriptionChanged(anonymousClass22);
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForSelectedNbIotSatelliteSubscriptionChanged() RemoteException: " + e);
            e.rethrowFromSystemServer();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$22, reason: invalid class name */
    class AnonymousClass22 extends ISelectedNbIotSatelliteSubscriptionCallback.Stub {
        final /* synthetic */ SelectedNbIotSatelliteSubscriptionCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass22(SatelliteManager satelliteManager, Executor executor, SelectedNbIotSatelliteSubscriptionCallback selectedNbIotSatelliteSubscriptionCallback) {
            this.val$executor = executor;
            this.val$callback = selectedNbIotSatelliteSubscriptionCallback;
        }

        @Override // android.telephony.satellite.ISelectedNbIotSatelliteSubscriptionCallback
        public void onSelectedNbIotSatelliteSubscriptionChanged(final int i) {
            Executor executor = this.val$executor;
            final SelectedNbIotSatelliteSubscriptionCallback selectedNbIotSatelliteSubscriptionCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$22$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            selectedNbIotSatelliteSubscriptionCallback.onSelectedNbIotSatelliteSubscriptionChanged(i);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForSelectedNbIotSatelliteSubscriptionChanged(SelectedNbIotSatelliteSubscriptionCallback selectedNbIotSatelliteSubscriptionCallback) {
        Objects.requireNonNull(selectedNbIotSatelliteSubscriptionCallback);
        ISelectedNbIotSatelliteSubscriptionCallback iSelectedNbIotSatelliteSubscriptionCallbackRemove = sSelectedNbIotSatelliteSubscriptionCallbackMap.remove(selectedNbIotSatelliteSubscriptionCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("Telephony service is null.");
            }
            if (iSelectedNbIotSatelliteSubscriptionCallbackRemove != null) {
                iTelephony.unregisterForSelectedNbIotSatelliteSubscriptionChanged(iSelectedNbIotSatelliteSubscriptionCallbackRemove);
            } else {
                loge("unregisterForSelectedNbIotSatelliteSubscriptionChanged: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForSelectedNbIotSatelliteSubscriptionChanged() RemoteException: " + e);
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDeviceAlignedWithSatellite(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDeviceAlignedWithSatellite(z);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("setDeviceAlignedWithSatellite() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void requestAttachEnabledForCarrier(int i, boolean z, Executor executor, Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        if (z) {
            removeAttachRestrictionForCarrier(i, 0, executor, consumer);
        } else {
            addAttachRestrictionForCarrier(i, 0, executor, consumer);
        }
    }

    @SystemApi
    public void requestIsAttachEnabledForCarrier(int i, Executor executor, final OutcomeReceiver<Boolean, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        final Set<Integer> attachRestrictionReasonsForCarrier = getAttachRestrictionReasonsForCarrier(i);
        executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda71
            @Override // java.lang.Runnable
            public final void run() {
                OutcomeReceiver outcomeReceiver2 = outcomeReceiver;
                Set set = attachRestrictionReasonsForCarrier;
                outcomeReceiver2.onResult(Boolean.valueOf(!set.contains(0)));
            }
        });
    }

    @SystemApi
    public void addAttachRestrictionForCarrier(int i, int i2, Executor executor, final Consumer<Integer> consumer) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.addAttachRestrictionForCarrier(i, i2, new AnonymousClass23(this, executor, consumer));
            } else {
                loge("addAttachRestrictionForCarrier() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda50
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda16
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("addAttachRestrictionForCarrier() RemoteException:" + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda30
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$23, reason: invalid class name */
    class AnonymousClass23 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass23(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$23$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$23$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void removeAttachRestrictionForCarrier(int i, int i2, Executor executor, final Consumer<Integer> consumer) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.removeAttachRestrictionForCarrier(i, i2, new AnonymousClass24(this, executor, consumer));
            } else {
                loge("removeAttachRestrictionForCarrier() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda64
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(23);
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("removeAttachRestrictionForCarrier() RemoteException:" + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda57
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(23);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$24, reason: invalid class name */
    class AnonymousClass24 extends IIntegerConsumer.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$resultListener;

        AnonymousClass24(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$resultListener;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$24$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public Set<Integer> getAttachRestrictionReasonsForCarrier(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int[] attachRestrictionReasonsForCarrier = iTelephony.getAttachRestrictionReasonsForCarrier(i);
                if (attachRestrictionReasonsForCarrier.length == 0) {
                    logd("receivedArray is empty, create empty set");
                    return new HashSet();
                }
                return (Set) Arrays.stream(attachRestrictionReasonsForCarrier).boxed().collect(Collectors.toSet());
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("getAttachRestrictionReasonsForCarrier() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return new HashSet();
        }
    }

    @SystemApi
    public int[] getSatelliteDisallowedReasons() {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSatelliteDisallowedReasons();
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("getSatelliteDisallowedReasons() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return new int[0];
        }
    }

    @SystemApi
    public void registerForSatelliteDisallowedReasonsChanged(Executor executor, SatelliteDisallowedReasonsCallback satelliteDisallowedReasonsCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(satelliteDisallowedReasonsCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass25 anonymousClass25 = new AnonymousClass25(this, executor, satelliteDisallowedReasonsCallback);
                iTelephony.registerForSatelliteDisallowedReasonsChanged(anonymousClass25);
                sSatelliteDisallowedReasonsCallbackMap.put(satelliteDisallowedReasonsCallback, anonymousClass25);
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForSatelliteDisallowedReasonsChanged() RemoteException" + e);
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$25, reason: invalid class name */
    class AnonymousClass25 extends ISatelliteDisallowedReasonsCallback.Stub {
        final /* synthetic */ SatelliteDisallowedReasonsCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass25(SatelliteManager satelliteManager, Executor executor, SatelliteDisallowedReasonsCallback satelliteDisallowedReasonsCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteDisallowedReasonsCallback;
        }

        @Override // android.telephony.satellite.ISatelliteDisallowedReasonsCallback
        public void onSatelliteDisallowedReasonsChanged(final int[] iArr) {
            Executor executor = this.val$executor;
            final SatelliteDisallowedReasonsCallback satelliteDisallowedReasonsCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$25$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$25$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteDisallowedReasonsCallback.onSatelliteDisallowedReasonsChanged(iArr);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForSatelliteDisallowedReasonsChanged(SatelliteDisallowedReasonsCallback satelliteDisallowedReasonsCallback) {
        Objects.requireNonNull(satelliteDisallowedReasonsCallback);
        ISatelliteDisallowedReasonsCallback iSatelliteDisallowedReasonsCallbackRemove = sSatelliteDisallowedReasonsCallbackMap.remove(satelliteDisallowedReasonsCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("Telephony service is null.");
            }
            if (iSatelliteDisallowedReasonsCallbackRemove != null) {
                iTelephony.unregisterForSatelliteDisallowedReasonsChanged(iSatelliteDisallowedReasonsCallbackRemove);
            } else {
                loge("unregisterForSatelliteDisallowedReasonsChanged: No internal callback.");
                throw new IllegalArgumentException("callback is not valid");
            }
        } catch (RemoteException e) {
            loge("unregisterForSatelliteDisallowedReasonsChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public void requestNtnSignalStrength(Executor executor, final OutcomeReceiver<NtnSignalStrength, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestNtnSignalStrength(new AnonymousClass26(this, null, executor, outcomeReceiver));
            } else {
                loge("requestNtnSignalStrength() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda72
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda21
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestNtnSignalStrength() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda73
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda23
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$26, reason: invalid class name */
    class AnonymousClass26 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass26(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_NTN_SIGNAL_STRENGTH)) {
                    final NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) bundle.getParcelable(SatelliteManager.KEY_NTN_SIGNAL_STRENGTH, NtnSignalStrength.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(ntnSignalStrength);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_NTN_SIGNAL_STRENGTH does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda1
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$26$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void registerForNtnSignalStrengthChanged(Executor executor, NtnSignalStrengthCallback ntnSignalStrengthCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(ntnSignalStrengthCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass27 anonymousClass27 = new AnonymousClass27(this, executor, ntnSignalStrengthCallback);
                iTelephony.registerForNtnSignalStrengthChanged(anonymousClass27);
                sNtnSignalStrengthCallbackMap.put(ntnSignalStrengthCallback, anonymousClass27);
                return;
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForNtnSignalStrengthChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$27, reason: invalid class name */
    class AnonymousClass27 extends INtnSignalStrengthCallback.Stub {
        final /* synthetic */ NtnSignalStrengthCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass27(SatelliteManager satelliteManager, Executor executor, NtnSignalStrengthCallback ntnSignalStrengthCallback) {
            this.val$executor = executor;
            this.val$callback = ntnSignalStrengthCallback;
        }

        @Override // android.telephony.satellite.INtnSignalStrengthCallback
        public void onNtnSignalStrengthChanged(final NtnSignalStrength ntnSignalStrength) {
            Executor executor = this.val$executor;
            final NtnSignalStrengthCallback ntnSignalStrengthCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$27$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$27$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            ntnSignalStrengthCallback.onNtnSignalStrengthChanged(ntnSignalStrength);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForNtnSignalStrengthChanged(NtnSignalStrengthCallback ntnSignalStrengthCallback) {
        Objects.requireNonNull(ntnSignalStrengthCallback);
        INtnSignalStrengthCallback iNtnSignalStrengthCallbackRemove = sNtnSignalStrengthCallbackMap.remove(ntnSignalStrengthCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("Telephony service is null.");
            }
            if (iNtnSignalStrengthCallbackRemove != null) {
                iTelephony.unregisterForNtnSignalStrengthChanged(iNtnSignalStrengthCallbackRemove);
            } else {
                loge("unregisterForNtnSignalStrengthChanged: No internal callback.");
                throw new IllegalArgumentException("callback is not valid");
            }
        } catch (RemoteException e) {
            loge("unregisterForNtnSignalStrengthChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public int registerForCapabilitiesChanged(Executor executor, SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(satelliteCapabilitiesCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass28 anonymousClass28 = new AnonymousClass28(this, executor, satelliteCapabilitiesCallback);
                sSatelliteCapabilitiesCallbackMap.put(satelliteCapabilitiesCallback, anonymousClass28);
                return iTelephony.registerForCapabilitiesChanged(anonymousClass28);
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForCapabilitiesChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$28, reason: invalid class name */
    class AnonymousClass28 extends ISatelliteCapabilitiesCallback.Stub {
        final /* synthetic */ SatelliteCapabilitiesCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass28(SatelliteManager satelliteManager, Executor executor, SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteCapabilitiesCallback;
        }

        @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
        public void onSatelliteCapabilitiesChanged(final SatelliteCapabilities satelliteCapabilities) {
            Executor executor = this.val$executor;
            final SatelliteCapabilitiesCallback satelliteCapabilitiesCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$28$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$28$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteCapabilitiesCallback.onSatelliteCapabilitiesChanged(satelliteCapabilities);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForCapabilitiesChanged(SatelliteCapabilitiesCallback satelliteCapabilitiesCallback) {
        Objects.requireNonNull(satelliteCapabilitiesCallback);
        ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallbackRemove = sSatelliteCapabilitiesCallbackMap.remove(satelliteCapabilitiesCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("Telephony service is null.");
            }
            if (iSatelliteCapabilitiesCallbackRemove != null) {
                iTelephony.unregisterForCapabilitiesChanged(iSatelliteCapabilitiesCallbackRemove);
            } else {
                loge("unregisterForCapabilitiesChanged: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForCapabilitiesChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public List<String> getSatellitePlmnsForCarrier(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSatellitePlmnsForCarrier(i);
            }
            throw new IllegalStateException("Telephony service is null.");
        } catch (RemoteException e) {
            loge("getSatellitePlmnsForCarrier() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return new ArrayList();
        }
    }

    @SystemApi
    public int registerForSupportedStateChanged(Executor executor, Consumer<Boolean> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass29 anonymousClass29 = new AnonymousClass29(this, executor, consumer);
                sSatelliteSupportedStateCallbackMap.put(consumer, anonymousClass29);
                return iTelephony.registerForSatelliteSupportedStateChanged(anonymousClass29);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForSupportedStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$29, reason: invalid class name */
    class AnonymousClass29 extends IBooleanConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass29(SatelliteManager satelliteManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IBooleanConsumer
        public void accept(final boolean z) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$29$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Boolean.valueOf(z));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForSupportedStateChanged(Consumer<Boolean> consumer) {
        Objects.requireNonNull(consumer);
        IBooleanConsumer iBooleanConsumerRemove = sSatelliteSupportedStateCallbackMap.remove(consumer);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            if (iBooleanConsumerRemove != null) {
                iTelephony.unregisterForSatelliteSupportedStateChanged(iBooleanConsumerRemove);
            } else {
                loge("unregisterForSupportedStateChanged: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForSupportedStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public int registerForCommunicationAccessStateChanged(Executor executor, SatelliteCommunicationAccessStateCallback satelliteCommunicationAccessStateCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(satelliteCommunicationAccessStateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                AnonymousClass30 anonymousClass30 = new AnonymousClass30(this, executor, satelliteCommunicationAccessStateCallback);
                sSatelliteCommunicationAccessStateCallbackMap.put(satelliteCommunicationAccessStateCallback, anonymousClass30);
                return iTelephony.registerForCommunicationAccessStateChanged(this.mSubId, anonymousClass30);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("registerForCommunicationAccessStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
            return 9;
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$30, reason: invalid class name */
    class AnonymousClass30 extends ISatelliteCommunicationAccessStateCallback.Stub {
        final /* synthetic */ SatelliteCommunicationAccessStateCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass30(SatelliteManager satelliteManager, Executor executor, SatelliteCommunicationAccessStateCallback satelliteCommunicationAccessStateCallback) {
            this.val$executor = executor;
            this.val$callback = satelliteCommunicationAccessStateCallback;
        }

        @Override // android.telephony.satellite.ISatelliteCommunicationAccessStateCallback
        public void onAccessAllowedStateChanged(final boolean z) {
            Executor executor = this.val$executor;
            final SatelliteCommunicationAccessStateCallback satelliteCommunicationAccessStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteCommunicationAccessStateCallback.onAccessAllowedStateChanged(z);
                        }
                    });
                }
            });
        }

        @Override // android.telephony.satellite.ISatelliteCommunicationAccessStateCallback
        public void onAccessConfigurationChanged(final SatelliteAccessConfiguration satelliteAccessConfiguration) {
            Executor executor = this.val$executor;
            final SatelliteCommunicationAccessStateCallback satelliteCommunicationAccessStateCallback = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$30$$ExternalSyntheticLambda2
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            satelliteCommunicationAccessStateCallback.onAccessConfigurationChanged(satelliteAccessConfiguration);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void unregisterForCommunicationAccessStateChanged(SatelliteCommunicationAccessStateCallback satelliteCommunicationAccessStateCallback) {
        Objects.requireNonNull(satelliteCommunicationAccessStateCallback);
        ISatelliteCommunicationAccessStateCallback iSatelliteCommunicationAccessStateCallbackRemove = sSatelliteCommunicationAccessStateCallbackMap.remove(satelliteCommunicationAccessStateCallback);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new IllegalStateException("telephony service is null.");
            }
            if (iSatelliteCommunicationAccessStateCallbackRemove != null) {
                iTelephony.unregisterForCommunicationAccessStateChanged(this.mSubId, iSatelliteCommunicationAccessStateCallbackRemove);
            } else {
                loge("unregisterForCommunicationAccessStateChanged: No internal callback.");
            }
        } catch (RemoteException e) {
            loge("unregisterForCommunicationAccessStateChanged() RemoteException: " + e);
            e.rethrowAsRuntimeException();
        }
    }

    public void requestSessionStats(Executor executor, final OutcomeReceiver<SatelliteSessionStats, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSatelliteSessionStats(this.mSubId, new AnonymousClass31(this, null, executor, outcomeReceiver));
            } else {
                loge("requestSessionStats() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda63
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestSessionStats() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda47
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass31(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SESSION_STATS)) {
                    final SatelliteSessionStats satelliteSessionStats = (SatelliteSessionStats) bundle.getParcelable(SatelliteManager.KEY_SESSION_STATS, SatelliteSessionStats.class);
                    if (bundle.containsKey(SatelliteManager.KEY_SESSION_STATS_V2)) {
                        SatelliteSessionStats satelliteSessionStats2 = (SatelliteSessionStats) bundle.getParcelable(SatelliteManager.KEY_SESSION_STATS_V2, SatelliteSessionStats.class);
                        if (satelliteSessionStats != null && satelliteSessionStats2 != null) {
                            satelliteSessionStats.setSatelliteSessionStats(satelliteSessionStats2.getSatelliteSessionStats());
                            Executor executor = this.val$executor;
                            final OutcomeReceiver outcomeReceiver = this.val$callback;
                            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda0
                                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                        public final void runOrThrow() {
                                            outcomeReceiver.onResult(satelliteSessionStats);
                                        }
                                    });
                                }
                            });
                            return;
                        }
                    } else {
                        SatelliteManager.loge("KEY_SESSION_STATS_V2 does not exist.");
                    }
                } else {
                    SatelliteManager.loge("KEY_SESSION_STATS does not exist.");
                }
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda5
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$31$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void requestSatelliteSubscriberProvisionStatus(Executor executor, final OutcomeReceiver<List<SatelliteSubscriberProvisionStatus>, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSatelliteSubscriberProvisionStatus(new AnonymousClass32(this, null, executor, outcomeReceiver));
            } else {
                loge("requestSatelliteSubscriberProvisionStatus() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda37
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda74
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestSatelliteSubscriberProvisionStatus() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda26
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
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
        AnonymousClass32(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN)) {
                    final ArrayList parcelableArrayList = bundle.getParcelableArrayList(SatelliteManager.KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN, SatelliteSubscriberProvisionStatus.class);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda5
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(list);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_REQUEST_PROVISION_SUBSCRIBER_ID_TOKEN does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda0
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$32$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    public void requestSatelliteDisplayName(Executor executor, final OutcomeReceiver<CharSequence, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestSatelliteDisplayName(new AnonymousClass33(this, null, executor, outcomeReceiver));
            } else {
                loge("requestSatelliteDisplayName() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda66
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda36
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("requestSatelliteDisplayName() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda67
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda75
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$33, reason: invalid class name */
    class AnonymousClass33 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass33(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_SATELLITE_DISPLAY_NAME)) {
                    final String string = bundle.getString(SatelliteManager.KEY_SATELLITE_DISPLAY_NAME);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$33$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$33$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(charSequence);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_SATELLITE_DISPLAY_NAME does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$33$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$33$$ExternalSyntheticLambda5
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$33$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$33$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void provisionSatellite(List<SatelliteSubscriberInfo> list, Executor executor, final OutcomeReceiver<Void, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.provisionSatellite(list, new AnonymousClass34(this, null, executor, outcomeReceiver));
            } else {
                loge("provisionSatellite() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda79
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda32
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("provisionSatellite() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda80
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda54
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$34, reason: invalid class name */
    class AnonymousClass34 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass34(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_PROVISION_SATELLITE_TOKENS)) {
                    bundle.getBoolean(SatelliteManager.KEY_PROVISION_SATELLITE_TOKENS);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$34$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$34$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(null);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_REQUEST_PROVISION_TOKENS does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$34$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$34$$ExternalSyntheticLambda0
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$34$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$34$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void deprovisionSatellite(List<SatelliteSubscriberInfo> list, Executor executor, final OutcomeReceiver<Void, SatelliteException> outcomeReceiver) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.deprovisionSatellite(list, new AnonymousClass35(this, null, executor, outcomeReceiver));
            } else {
                loge("deprovisionSatellite() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda87
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda58
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                            }
                        });
                    }
                });
            }
        } catch (RemoteException e) {
            loge("deprovisionSatellite() RemoteException: " + e);
            executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda88
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(23));
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.satellite.SatelliteManager$35, reason: invalid class name */
    class AnonymousClass35 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass35(SatelliteManager satelliteManager, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            if (i == 0) {
                if (bundle.containsKey(SatelliteManager.KEY_DEPROVISION_SATELLITE_TOKENS)) {
                    bundle.getBoolean(SatelliteManager.KEY_DEPROVISION_SATELLITE_TOKENS);
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$35$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$35$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                                public final void runOrThrow() {
                                    outcomeReceiver.onResult(null);
                                }
                            });
                        }
                    });
                    return;
                }
                SatelliteManager.loge("KEY_DEPROVISION_SATELLITE_TOKENS does not exist.");
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$35$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$35$$ExternalSyntheticLambda5
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                outcomeReceiver.onError(new SatelliteManager.SatelliteException(9));
                            }
                        });
                    }
                });
                return;
            }
            Executor executor3 = this.val$executor;
            final OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new Runnable() { // from class: android.telephony.satellite.SatelliteManager$35$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.satellite.SatelliteManager$35$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            outcomeReceiver.onError(new SatelliteManager.SatelliteException(i));
                        }
                    });
                }
            });
        }
    }

    public void setNtnSmsSupported(boolean z) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNtnSmsSupported(z);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("setNtnSmsSupported() RemoteException:" + e);
            e.rethrowAsRuntimeException();
        }
    }

    @SystemApi
    public List<String> getSatelliteDataOptimizedApps() {
        ArrayList arrayList = new ArrayList();
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSatelliteDataOptimizedApps();
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("getSatelliteDataOptimizedApps() RemoteException:" + e);
            e.rethrowAsRuntimeException();
            return arrayList;
        }
    }

    @SystemApi
    public int getSatelliteDataSupportMode(int i) {
        try {
            ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSatelliteDataSupportMode(i);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("getSatelliteDataSupportMode() RemoteException:" + e);
            e.rethrowAsRuntimeException();
            return -1;
        }
    }

    public boolean getNtnSmsSupported() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.getNtnSmsSupported();
            }
            loge("getNtnSmsSupported() invalid semTelephony");
            return false;
        } catch (RemoteException e) {
            loge("getNtnSmsSupported() RemoteException: " + e);
            return false;
        }
    }

    private static ISemTelephony getISemTelephony() {
        return ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
    }

    private static ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logd(String str) {
        Rlog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(String str) {
        Rlog.e(TAG, str);
    }
}
