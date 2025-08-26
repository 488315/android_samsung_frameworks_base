package android.telecom;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.OutcomeReceiver;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import com.android.internal.telecom.ClientTransactionalServiceRepository;
import com.android.internal.telecom.ClientTransactionalServiceWrapper;
import com.android.internal.telecom.ITelecomService;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class TelecomManager {
    public static final String ACTION_CHANGE_DEFAULT_DIALER = "android.telecom.action.CHANGE_DEFAULT_DIALER";
    public static final String ACTION_CHANGE_PHONE_ACCOUNTS = "android.telecom.action.CHANGE_PHONE_ACCOUNTS";
    public static final String ACTION_CONFIGURE_PHONE_ACCOUNT = "android.telecom.action.CONFIGURE_PHONE_ACCOUNT";

    @SystemApi
    public static final String ACTION_CURRENT_TTY_MODE_CHANGED = "android.telecom.action.CURRENT_TTY_MODE_CHANGED";
    public static final String ACTION_DEFAULT_CALL_SCREENING_APP_CHANGED = "android.telecom.action.DEFAULT_CALL_SCREENING_APP_CHANGED";
    public static final String ACTION_DEFAULT_DIALER_CHANGED = "android.telecom.action.DEFAULT_DIALER_CHANGED";
    public static final String ACTION_INCOMING_CALL = "android.telecom.action.INCOMING_CALL";
    public static final String ACTION_NEW_UNKNOWN_CALL = "android.telecom.action.NEW_UNKNOWN_CALL";
    public static final String ACTION_PHONE_ACCOUNT_REGISTERED = "android.telecom.action.PHONE_ACCOUNT_REGISTERED";
    public static final String ACTION_PHONE_ACCOUNT_UNREGISTERED = "android.telecom.action.PHONE_ACCOUNT_UNREGISTERED";
    public static final String ACTION_POST_CALL = "android.telecom.action.POST_CALL";
    public static final String ACTION_SHOW_CALL_ACCESSIBILITY_SETTINGS = "android.telecom.action.SHOW_CALL_ACCESSIBILITY_SETTINGS";
    public static final String ACTION_SHOW_CALL_SETTINGS = "android.telecom.action.SHOW_CALL_SETTINGS";
    public static final String ACTION_SHOW_MISSED_CALLS_NOTIFICATION = "android.telecom.action.SHOW_MISSED_CALLS_NOTIFICATION";
    public static final String ACTION_SHOW_RESPOND_VIA_SMS_SETTINGS = "android.telecom.action.SHOW_RESPOND_VIA_SMS_SETTINGS";
    public static final String ACTION_SHOW_SWITCH_TO_WORK_PROFILE_FOR_CALL_DIALOG = "android.telecom.action.SHOW_SWITCH_TO_WORK_PROFILE_FOR_CALL_DIALOG";

    @SystemApi
    public static final String ACTION_TTY_PREFERRED_MODE_CHANGED = "android.telecom.action.TTY_PREFERRED_MODE_CHANGED";
    public static final int AUDIO_OUTPUT_DEFAULT = 0;
    public static final int AUDIO_OUTPUT_DISABLE_SPEAKER = 1;
    public static final int AUDIO_OUTPUT_ENABLE_SPEAKER = 0;
    public static final String CALL_AUTO_DISCONNECT_MESSAGE_STRING = "Call dropped by lower layers";

    @SystemApi
    public static final int CALL_SOURCE_EMERGENCY_DIALPAD = 1;

    @SystemApi
    public static final int CALL_SOURCE_EMERGENCY_SHORTCUT = 2;

    @SystemApi
    public static final int CALL_SOURCE_UNSPECIFIED = 0;
    public static final char DTMF_CHARACTER_PAUSE = ',';
    public static final char DTMF_CHARACTER_WAIT = ';';
    public static final int DURATION_LONG = 3;
    public static final int DURATION_MEDIUM = 2;
    public static final int DURATION_SHORT = 1;
    public static final int DURATION_VERY_SHORT = 0;
    public static final long ENABLE_GET_CALL_STATE_PERMISSION_PROTECTION = 157233955;
    public static final long ENABLE_GET_PHONE_ACCOUNT_PERMISSION_PROTECTION = 183407956;
    public static final String EXTRA_CALL_ANSWERED_TIME_MILLIS = "android.telecom.extra.CALL_ANSWERED_TIME_MILLIS";
    public static final String EXTRA_CALL_AUDIO_STATE = "android.telecom.extra.CALL_AUDIO_STATE";

    @SystemApi
    public static final String EXTRA_CALL_BACK_INTENT = "android.telecom.extra.CALL_BACK_INTENT";
    public static final String EXTRA_CALL_BACK_NUMBER = "android.telecom.extra.CALL_BACK_NUMBER";
    public static final String EXTRA_CALL_CREATED_EPOCH_TIME_MILLIS = "android.telecom.extra.CALL_CREATED_EPOCH_TIME_MILLIS";
    public static final String EXTRA_CALL_CREATED_TIME_MILLIS = "android.telecom.extra.CALL_CREATED_TIME_MILLIS";
    public static final String EXTRA_CALL_DISCONNECT_CAUSE = "android.telecom.extra.CALL_DISCONNECT_CAUSE";
    public static final String EXTRA_CALL_DISCONNECT_MESSAGE = "android.telecom.extra.CALL_DISCONNECT_MESSAGE";
    public static final String EXTRA_CALL_DURATION = "android.telecom.extra.CALL_DURATION";

    @SystemApi
    public static final String EXTRA_CALL_HAS_IN_BAND_RINGTONE = "android.telecom.extra.CALL_HAS_IN_BAND_RINGTONE";
    public static final String EXTRA_CALL_LOG_URI = "android.telecom.extra.CALL_LOG_URI";
    public static final String EXTRA_CALL_NETWORK_TYPE = "android.telecom.extra.CALL_NETWORK_TYPE";

    @SystemApi
    public static final String EXTRA_CALL_SOURCE = "android.telecom.extra.CALL_SOURCE";
    public static final String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";

    @SystemApi
    public static final String EXTRA_CALL_TECHNOLOGY_TYPE = "android.telecom.extra.CALL_TECHNOLOGY_TYPE";
    public static final String EXTRA_CALL_TELECOM_ROUTING_END_TIME_MILLIS = "android.telecom.extra.CALL_TELECOM_ROUTING_END_TIME_MILLIS";
    public static final String EXTRA_CALL_TELECOM_ROUTING_START_TIME_MILLIS = "android.telecom.extra.CALL_TELECOM_ROUTING_START_TIME_MILLIS";
    public static final String EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME = "android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME";

    @SystemApi
    @Deprecated
    public static final String EXTRA_CLEAR_MISSED_CALLS_INTENT = "android.telecom.extra.CLEAR_MISSED_CALLS_INTENT";

    @SystemApi
    public static final String EXTRA_CONNECTION_SERVICE = "android.telecom.extra.CONNECTION_SERVICE";

    @SystemApi
    public static final String EXTRA_CURRENT_TTY_MODE = "android.telecom.extra.CURRENT_TTY_MODE";
    public static final String EXTRA_DEFAULT_CALL_SCREENING_APP_COMPONENT_NAME = "android.telecom.extra.DEFAULT_CALL_SCREENING_APP_COMPONENT_NAME";
    public static final String EXTRA_DISCONNECT_CAUSE = "android.telecom.extra.DISCONNECT_CAUSE";
    public static final String EXTRA_DO_NOT_LOG_CALL = "android.telecom.extra.DO_NOT_LOG_CALL";
    public static final String EXTRA_HANDLE = "android.telecom.extra.HANDLE";
    public static final String EXTRA_HANDOVER_FROM_PHONE_ACCOUNT = "android.telecom.extra.HANDOVER_FROM_PHONE_ACCOUNT";
    public static final String EXTRA_HAS_PICTURE = "android.telecom.extra.HAS_PICTURE";
    public static final String EXTRA_INCOMING_CALL_ADDRESS = "android.telecom.extra.INCOMING_CALL_ADDRESS";
    public static final String EXTRA_INCOMING_CALL_EXTRAS = "android.telecom.extra.INCOMING_CALL_EXTRAS";
    public static final String EXTRA_INCOMING_VIDEO_STATE = "android.telecom.extra.INCOMING_VIDEO_STATE";
    public static final String EXTRA_IS_DEFAULT_CALL_SCREENING_APP = "android.telecom.extra.IS_DEFAULT_CALL_SCREENING_APP";
    public static final String EXTRA_IS_HANDOVER = "android.telecom.extra.IS_HANDOVER";
    public static final String EXTRA_IS_HANDOVER_CONNECTION = "android.telecom.extra.IS_HANDOVER_CONNECTION";

    @SystemApi
    public static final String EXTRA_IS_USER_INTENT_EMERGENCY_CALL = "android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL";
    public static final String EXTRA_LOCATION = "android.telecom.extra.LOCATION";
    public static final String EXTRA_MANAGED_PROFILE_USER_ID = "android.telecom.extra.MANAGED_PROFILE_USER_ID";
    public static final String EXTRA_NEW_OUTGOING_CALL_CANCEL_TIMEOUT = "android.telecom.extra.NEW_OUTGOING_CALL_CANCEL_TIMEOUT";
    public static final String EXTRA_NOTIFICATION_COUNT = "android.telecom.extra.NOTIFICATION_COUNT";
    public static final String EXTRA_NOTIFICATION_PHONE_NUMBER = "android.telecom.extra.NOTIFICATION_PHONE_NUMBER";
    public static final String EXTRA_OUTGOING_CALL_EXTRAS = "android.telecom.extra.OUTGOING_CALL_EXTRAS";
    public static final String EXTRA_OUTGOING_PICTURE = "android.telecom.extra.OUTGOING_PICTURE";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_PICTURE_URI = "android.telecom.extra.PICTURE_URI";
    public static final String EXTRA_PRIORITY = "android.telecom.extra.PRIORITY";
    public static final String EXTRA_START_CALL_WITH_RTT = "android.telecom.extra.START_CALL_WITH_RTT";
    public static final String EXTRA_START_CALL_WITH_SPEAKERPHONE = "android.telecom.extra.START_CALL_WITH_SPEAKERPHONE";
    public static final String EXTRA_START_CALL_WITH_VIDEO_STATE = "android.telecom.extra.START_CALL_WITH_VIDEO_STATE";

    @SystemApi
    public static final String EXTRA_TTY_PREFERRED_MODE = "android.telecom.extra.TTY_PREFERRED_MODE";

    @SystemApi
    public static final String EXTRA_UNKNOWN_CALL_HANDLE = "android.telecom.extra.UNKNOWN_CALL_HANDLE";
    public static final String EXTRA_USE_ASSISTED_DIALING = "android.telecom.extra.USE_ASSISTED_DIALING";
    public static final String GATEWAY_ORIGINAL_ADDRESS = "android.telecom.extra.GATEWAY_ORIGINAL_ADDRESS";
    public static final String GATEWAY_PROVIDER_PACKAGE = "android.telecom.extra.GATEWAY_PROVIDER_PACKAGE";
    public static final long MEDIUM_CALL_TIME_MS = 120000;
    public static final String METADATA_INCLUDE_EXTERNAL_CALLS = "android.telecom.INCLUDE_EXTERNAL_CALLS";
    public static final String METADATA_INCLUDE_SELF_MANAGED_CALLS = "android.telecom.INCLUDE_SELF_MANAGED_CALLS";
    public static final String METADATA_IN_CALL_SERVICE_CAR_MODE_UI = "android.telecom.IN_CALL_SERVICE_CAR_MODE_UI";
    public static final String METADATA_IN_CALL_SERVICE_RINGING = "android.telecom.IN_CALL_SERVICE_RINGING";
    public static final String METADATA_IN_CALL_SERVICE_UI = "android.telecom.IN_CALL_SERVICE_UI";
    public static final int PRESENTATION_ALLOWED = 1;
    public static final int PRESENTATION_PAYPHONE = 4;
    public static final int PRESENTATION_RESTRICTED = 2;
    public static final int PRESENTATION_UNAVAILABLE = 5;
    public static final int PRESENTATION_UNKNOWN = 3;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 1;
    public static final String PROPERTY_VIDEOCALL_AUDIO_OUTPUT = "persist.radio.call.audio.output";
    public static final String SEM_ACTION_TTY_PREFERRED_MODE_CHANGED = "android.telecom.action.TTY_PREFERRED_MODE_CHANGED";
    public static final String SEM_EXTRA_TTY_PREFERRED_MODE = "android.telecom.intent.extra.TTY_PREFERRED";
    public static final String SEM_EXTRA_TTY_PREFERRED_MODE_R = "android.telecom.extra.TTY_PREFERRED_MODE";
    public static final int SEM_TTY_MODE_FULL = 1;
    public static final int SEM_TTY_MODE_HCO = 2;
    public static final int SEM_TTY_MODE_OFF = 0;
    public static final int SEM_TTY_MODE_VCO = 3;
    public static final long SHORT_CALL_TIME_MS = 60000;
    private static final String TAG = "TelecomManager";
    public static final int TELECOM_TRANSACTION_SUCCESS = 0;
    public static final String TRANSACTION_CALL_ID_KEY = "TelecomCallId";

    @SystemApi
    public static final int TTY_MODE_FULL = 1;

    @SystemApi
    public static final int TTY_MODE_HCO = 2;

    @SystemApi
    public static final int TTY_MODE_OFF = 0;

    @SystemApi
    public static final int TTY_MODE_VCO = 3;
    public static final long VERY_SHORT_CALL_TIME_MS = 3000;
    private static ITelecomService sTelecomService;
    private final Context mContext;
    private final ITelecomService mTelecomServiceOverride;
    private final ClientTransactionalServiceRepository mTransactionalServiceRepository;
    private static final String EMERGENCY_DIALER_PACKAGE_NAME = "com.samsung.android.emergency";
    private static final String EMERGENCY_DIALER_CLASS_NAME = ".emergencydialer.view.EmergencyDialerActivity";
    public static final ComponentName EMERGENCY_DIALER_COMPONENT = ComponentName.createRelative(EMERGENCY_DIALER_PACKAGE_NAME, EMERGENCY_DIALER_CLASS_NAME);
    private static final Object CACHE_LOCK = new Object();
    private static final DeathRecipient SERVICE_DEATH = new DeathRecipient();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Presentation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TtyMode {
    }

    public static TelecomManager from(Context context) {
        return (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
    }

    public TelecomManager(Context context) {
        this(context, null);
    }

    public TelecomManager(Context context, ITelecomService iTelecomService) {
        this.mTransactionalServiceRepository = new ClientTransactionalServiceRepository();
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null && Objects.equals(context.getAttributionTag(), applicationContext.getAttributionTag())) {
            this.mContext = applicationContext;
        } else {
            this.mContext = context;
        }
        this.mTelecomServiceOverride = iTelecomService;
    }

    public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getDefaultOutgoingPhoneAccount(str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getDefaultOutgoingPhoneAccount", e);
            return null;
        }
    }

    public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getUserSelectedOutgoingPhoneAccount(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getUserSelectedOutgoingPhoneAccount", e);
            return null;
        }
    }

    @SystemApi
    public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
            } catch (RemoteException unused) {
                android.util.Log.e(TAG, "Error calling ITelecomService#setUserSelectedOutgoingPhoneAccount");
            }
        }
    }

    public void semSetUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
    }

    public PhoneAccountHandle getSimCallManager() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getSimCallManager(SubscriptionManager.getDefaultSubscriptionId(), this.mContext.getPackageName());
        } catch (RemoteException unused) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getSimCallManager");
            return null;
        }
    }

    public PhoneAccountHandle getSimCallManagerForSubscription(int i) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getSimCallManager(i, this.mContext.getPackageName());
        } catch (RemoteException unused) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getSimCallManager");
            return null;
        }
    }

    public PhoneAccountHandle getSimCallManager(int i) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getSimCallManagerForUser(i, this.mContext.getPackageName());
        } catch (RemoteException unused) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getSimCallManagerForUser");
            return null;
        }
    }

    @SystemApi
    public PhoneAccountHandle getConnectionManager() {
        return getSimCallManager();
    }

    @SystemApi
    public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getPhoneAccountsSupportingScheme(str, this.mContext.getOpPackageName()).getList();
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getPhoneAccountsSupportingScheme", e);
            }
        }
        return new ArrayList();
    }

    public List<PhoneAccountHandle> getCallCapablePhoneAccounts() {
        return getCallCapablePhoneAccounts(false);
    }

    public List<PhoneAccountHandle> getCallCapablePhoneAccountsAcrossProfiles() {
        return getCallCapablePhoneAccountsAcrossProfiles(false);
    }

    public List<PhoneAccountHandle> getSelfManagedPhoneAccounts() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getSelfManagedPhoneAccounts(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).getList();
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getSelfManagedPhoneAccounts()", e);
            }
        }
        return new ArrayList();
    }

    public List<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getOwnSelfManagedPhoneAccounts(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).getList();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new IllegalStateException("Telecom is not available");
    }

    public List<PhoneAccount> getRegisteredPhoneAccounts() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getRegisteredPhoneAccounts(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).getList();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new IllegalStateException("Telecom is not available");
    }

    @SystemApi
    public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getCallCapablePhoneAccounts(z, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), false).getList();
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getCallCapablePhoneAccounts(" + z + NavigationBarInflaterView.KEY_CODE_END, e);
            }
        }
        return new ArrayList();
    }

    public List<PhoneAccountHandle> semGetCallCapablePhoneAccounts(boolean z) {
        return getCallCapablePhoneAccounts(z);
    }

    @SystemApi
    public List<PhoneAccountHandle> getCallCapablePhoneAccountsAcrossProfiles(boolean z) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            throw new IllegalStateException("telecom service is null.");
        }
        try {
            return telecomService.getCallCapablePhoneAccounts(z, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), true).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public List<PhoneAccountHandle> getPhoneAccountsForPackage() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getPhoneAccountsForPackage(this.mContext.getPackageName()).getList();
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getPhoneAccountsForPackage", e);
            return null;
        }
    }

    public PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getPhoneAccount(phoneAccountHandle, this.mContext.getPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getPhoneAccount", e);
            return null;
        }
    }

    @SystemApi
    public int getAllPhoneAccountsCount() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return 0;
        }
        try {
            return telecomService.getAllPhoneAccountsCount();
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccountsCount", e);
            return 0;
        }
    }

    @SystemApi
    public List<PhoneAccount> getAllPhoneAccounts() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getAllPhoneAccounts().getList();
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccounts", e);
            }
        }
        return Collections.EMPTY_LIST;
    }

    @SystemApi
    public List<PhoneAccountHandle> getAllPhoneAccountHandles() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getAllPhoneAccountHandles().getList();
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccountHandles", e);
            }
        }
        return Collections.EMPTY_LIST;
    }

    public void registerPhoneAccount(PhoneAccount phoneAccount) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.registerPhoneAccount(phoneAccount, this.mContext.getPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#registerPhoneAccount", e);
            }
        }
    }

    public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.unregisterPhoneAccount(phoneAccountHandle, this.mContext.getPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#unregisterPhoneAccount", e);
            }
        }
    }

    @SystemApi
    public void clearPhoneAccounts() {
        clearAccounts();
    }

    @SystemApi
    public void clearAccounts() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.clearAccounts(this.mContext.getPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#clearAccounts", e);
            }
        }
    }

    public void clearAccountsForPackage(String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                telecomService.clearAccounts(str);
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#clearAccountsForPackage", e);
            }
        }
    }

    @SystemApi
    public ComponentName getDefaultPhoneApp() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getDefaultPhoneApp();
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get the default phone app.", e);
            return null;
        }
    }

    public String getDefaultDialerPackage() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getDefaultDialerPackage(this.mContext.getPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get the default dialer package name.", e);
            return null;
        }
    }

    @SystemApi
    public String getDefaultDialerPackage(UserHandle userHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getDefaultDialerPackageForUser(userHandle.getIdentifier());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get the default dialer package name.", e);
            return null;
        }
    }

    @SystemApi
    @Deprecated
    public boolean setDefaultDialer(String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.setDefaultDialer(str);
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to set the default dialer.", e);
            return false;
        }
    }

    public String getSystemDialerPackage() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getSystemDialerPackage(this.mContext.getPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get the system dialer package name.", e);
            return null;
        }
    }

    public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isVoiceMailNumber(phoneAccountHandle, str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling ITelecomService#isVoiceMailNumber.", e);
            return false;
        }
    }

    public String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getVoiceMailNumber(phoneAccountHandle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling ITelecomService#hasVoiceMailNumber.", e);
            return null;
        }
    }

    public boolean hasForegroundServiceDelegation(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.hasForegroundServiceDelegation(phoneAccountHandle, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling ITelecomService#hasForegroundServiceDelegation.", e);
            return false;
        }
    }

    @Deprecated
    public String getLine1Number(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.getLine1Number(phoneAccountHandle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling ITelecomService#getLine1Number.", e);
            return null;
        }
    }

    public boolean isInCall() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isInCall(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling isInCall().", e);
            return false;
        }
    }

    public boolean hasManageOngoingCallsPermission() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.hasManageOngoingCallsPermission(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling hasManageOngoingCallsPermission().", e);
            if (this.isSystemProcess()) {
                return false;
            }
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isInManagedCall() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isInManagedCall(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException calling isInManagedCall().", e);
            return false;
        }
    }

    @SystemApi
    public int getCallState() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return 0;
        }
        try {
            return telecomService.getCallStateUsingPackage(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.d(TAG, "RemoteException calling getCallState().", e);
            return 0;
        }
    }

    @SystemApi
    public boolean isRinging() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isRinging(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get ringing state of phone app.", e);
            return false;
        }
    }

    @Deprecated
    public boolean endCall() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.endCall(this.mContext.getPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#endCall", e);
            return false;
        }
    }

    @Deprecated
    public void acceptRingingCall() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.acceptRingingCall(this.mContext.getPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#acceptRingingCall", e);
            }
        }
    }

    @Deprecated
    public void acceptRingingCall(int i) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.acceptRingingCallWithVideoState(this.mContext.getPackageName(), i);
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#acceptRingingCallWithVideoState", e);
            }
        }
    }

    public void silenceRinger() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.silenceRinger(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#silenceRinger", e);
            }
        }
    }

    public boolean isTtySupported() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isTtySupported(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get TTY supported state.", e);
            return false;
        }
    }

    @SystemApi
    public int getCurrentTtyMode() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return 0;
        }
        try {
            return telecomService.getCurrentTtyMode(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "RemoteException attempting to get the current TTY mode.", e);
            return 0;
        }
    }

    public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (bundle != null) {
                try {
                    if (bundle.getBoolean("android.telecom.extra.IS_HANDOVER") && this.mContext.getApplicationContext().getApplicationInfo().targetSdkVersion > 27) {
                        android.util.Log.e("TAG", "addNewIncomingCall failed. Use public api acceptHandover for API > O-MR1");
                        return;
                    }
                } catch (RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException adding a new incoming call: " + phoneAccountHandle, e);
                    return;
                }
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            telecomService.addNewIncomingCall(phoneAccountHandle, bundle, this.mContext.getPackageName());
        }
    }

    public void addNewIncomingConference(PhoneAccountHandle phoneAccountHandle, Bundle bundle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                } catch (RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException adding a new incoming conference: " + phoneAccountHandle, e);
                    return;
                }
            }
            telecomService.addNewIncomingConference(phoneAccountHandle, bundle, this.mContext.getPackageName());
        }
    }

    @SystemApi
    public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                } catch (RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException adding a new unknown call: " + phoneAccountHandle, e);
                    return;
                }
            }
            telecomService.addNewUnknownCall(phoneAccountHandle, bundle);
        }
    }

    public boolean handleMmi(String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.handlePinMmi(str, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#handlePinMmi", e);
            return false;
        }
    }

    public boolean handleMmi(String str, PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.handlePinMmiForPhoneAccount(phoneAccountHandle, str, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelecomService#handlePinMmi", e);
            return false;
        }
    }

    public Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null && phoneAccountHandle != null) {
            try {
                return telecomService.getAdnUriForPhoneAccount(phoneAccountHandle, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAdnUriForPhoneAccount", e);
            }
        }
        return Uri.parse("content://icc/adn");
    }

    public void cancelMissedCallsNotification() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.cancelMissedCallsNotification(this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#cancelMissedCallsNotification", e);
            }
        }
    }

    public void showInCallScreen(boolean z) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.showInCallScreen(z, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#showCallScreen", e);
            }
        }
    }

    public void placeCall(Uri uri, Bundle bundle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (uri == null) {
                android.util.Log.w(TAG, "Cannot place call to empty address.");
            }
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                } catch (RemoteException e) {
                    android.util.Log.e(TAG, "Error calling ITelecomService#placeCall", e);
                    return;
                }
            }
            telecomService.placeCall(uri, bundle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        }
    }

    public void startConference(List<Uri> list, Bundle bundle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.startConference(list, bundle, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#placeCall", e);
            }
        }
    }

    @SystemApi
    public void enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.enablePhoneAccount(phoneAccountHandle, z);
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error enablePhoneAbbount", e);
            }
        }
    }

    @SystemApi
    public TelecomAnalytics dumpAnalytics() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return null;
        }
        try {
            return telecomService.dumpCallAnalytics();
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error dumping call analytics", e);
            return null;
        }
    }

    public Intent createManageBlockedNumbersIntent() {
        ITelecomService telecomService = getTelecomService();
        Intent intentCreateManageBlockedNumbersIntent = null;
        if (telecomService != null) {
            try {
                intentCreateManageBlockedNumbersIntent = telecomService.createManageBlockedNumbersIntent(this.mContext.getPackageName());
                if (intentCreateManageBlockedNumbersIntent != null) {
                    intentCreateManageBlockedNumbersIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
                }
                return intentCreateManageBlockedNumbersIntent;
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#createManageBlockedNumbersIntent", e);
            }
        }
        return intentCreateManageBlockedNumbersIntent;
    }

    @SystemApi
    public Intent createLaunchEmergencyDialerIntent(String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            android.util.Log.w(TAG, "createLaunchEmergencyDialerIntent - Telecom service not available.");
        } else {
            try {
                Intent intentCreateLaunchEmergencyDialerIntent = telecomService.createLaunchEmergencyDialerIntent(str);
                if (intentCreateLaunchEmergencyDialerIntent != null) {
                    intentCreateLaunchEmergencyDialerIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
                }
                return intentCreateLaunchEmergencyDialerIntent;
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error createLaunchEmergencyDialerIntent", e);
            }
        }
        Intent intent = new Intent(Intent.ACTION_DIAL_EMERGENCY);
        if (!TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str)) {
            intent.setData(Uri.fromParts(PhoneAccount.SCHEME_TEL, str, null));
        }
        return intent;
    }

    public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService;
        if (phoneAccountHandle != null && (telecomService = getTelecomService()) != null) {
            try {
                return telecomService.isIncomingCallPermitted(phoneAccountHandle, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "Error isIncomingCallPermitted", e);
            }
        }
        return false;
    }

    public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isOutgoingCallPermitted(phoneAccountHandle, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            android.util.Log.e(TAG, "Error isOutgoingCallPermitted", e);
            return false;
        }
    }

    public void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.acceptHandover(uri, i, phoneAccountHandle, this.mContext.getPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "RemoteException acceptHandover: " + e);
            }
        }
    }

    @SystemApi
    public boolean isInEmergencyCall() {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isInEmergencyCall();
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "RemoteException isInEmergencyCall: " + e);
            }
        }
        return false;
    }

    @SystemApi
    public boolean isInSelfManagedCall(String str, UserHandle userHandle) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isInSelfManagedCall(str, userHandle, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "RemoteException isInSelfManagedCall: " + e);
                e.rethrowFromSystemServer();
                return false;
            }
        }
        throw new IllegalStateException("Telecom service is not present");
    }

    public void addCall(CallAttributes callAttributes, Executor executor, OutcomeReceiver<CallControl, CallException> outcomeReceiver, CallControlCallback callControlCallback, CallEventCallback callEventCallback) {
        Objects.requireNonNull(callAttributes);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(outcomeReceiver);
        Objects.requireNonNull(callControlCallback);
        Objects.requireNonNull(callEventCallback);
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                ClientTransactionalServiceWrapper clientTransactionalServiceWrapperAddNewCallForTransactionalServiceWrapper = this.mTransactionalServiceRepository.addNewCallForTransactionalServiceWrapper(callAttributes.getPhoneAccountHandle());
                telecomService.addCall(callAttributes, clientTransactionalServiceWrapperAddNewCallForTransactionalServiceWrapper.getCallEventCallback(), clientTransactionalServiceWrapperAddNewCallForTransactionalServiceWrapper.trackCall(callAttributes, executor, outcomeReceiver, callControlCallback, callEventCallback), this.mContext.getOpPackageName());
                return;
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "RemoteException addCall: " + e);
                e.rethrowFromSystemServer();
                return;
            }
        }
        throw new IllegalStateException("Telecom service is not present");
    }

    public void handleCallIntent(Intent intent, String str) {
        ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.handleCallIntent(intent, str);
            } catch (RemoteException e) {
                android.util.Log.e(TAG, "RemoteException handleCallIntent: " + e);
            }
        }
    }

    private boolean isSystemProcess() {
        return Process.myUid() == 1000;
    }

    private ITelecomService getTelecomService() {
        ITelecomService iTelecomService = this.mTelecomServiceOverride;
        if (iTelecomService != null) {
            return iTelecomService;
        }
        if (sTelecomService == null) {
            ITelecomService iTelecomServiceAsInterface = ITelecomService.Stub.asInterface(ServiceManager.getService(Context.TELECOM_SERVICE));
            synchronized (CACHE_LOCK) {
                if (sTelecomService == null && iTelecomServiceAsInterface != null) {
                    try {
                        sTelecomService = iTelecomServiceAsInterface;
                        iTelecomServiceAsInterface.asBinder().linkToDeath(SERVICE_DEATH, 0);
                    } catch (Exception unused) {
                        sTelecomService = null;
                    }
                }
            }
        }
        return sTelecomService;
    }

    private static class DeathRecipient implements IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            TelecomManager.resetServiceCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetServiceCache() {
        synchronized (CACHE_LOCK) {
            ITelecomService iTelecomService = sTelecomService;
            if (iTelecomService != null) {
                iTelecomService.asBinder().unlinkToDeath(SERVICE_DEATH, 0);
                sTelecomService = null;
            }
        }
    }
}
