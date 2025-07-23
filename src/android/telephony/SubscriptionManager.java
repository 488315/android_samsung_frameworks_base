package android.telephony;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.PropertyInvalidatedCache;
import android.compat.Compatibility;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.NetworkPolicyManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Telephony;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ISub;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.util.HandlerExecutor;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class SubscriptionManager {
    public static final String ACCESS_RULES = "access_rules";
    public static final String ACCESS_RULES_FROM_CARRIER_CONFIGS = "access_rules_from_carrier_configs";
    public static final String ACTION_DEFAULT_SMS_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED";
    public static final String ACTION_DEFAULT_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED";
    public static final String ACTION_MANAGE_SUBSCRIPTION_PLANS = "android.telephony.action.MANAGE_SUBSCRIPTION_PLANS";
    public static final String ACTION_REFRESH_SUBSCRIPTION_PLANS = "android.telephony.action.REFRESH_SUBSCRIPTION_PLANS";

    @SystemApi
    public static final String ACTION_SUBSCRIPTION_PLANS_CHANGED = "android.telephony.action.SUBSCRIPTION_PLANS_CHANGED";

    @SystemApi
    public static final Uri ADVANCED_CALLING_ENABLED_CONTENT_URI;
    public static final String ALLOWED_NETWORK_TYPES = "allowed_network_types_for_reasons";
    private static final String CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY = "cache_key.telephony.subscription_manager_service";
    public static final String CARD_ID = "card_id";
    public static final String CARRIER_ID = "carrier_id";
    public static final String CARRIER_NAME = "carrier_name";
    public static final String CB_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
    public static final String CB_ALERT_SOUND_DURATION = "alert_sound_duration";
    public static final String CB_ALERT_SPEECH = "enable_alert_speech";
    public static final String CB_ALERT_VIBRATE = "enable_alert_vibrate";
    public static final String CB_AMBER_ALERT = "enable_cmas_amber_alerts";
    public static final String CB_CHANNEL_50_ALERT = "enable_channel_50_alerts";
    public static final String CB_CMAS_TEST_ALERT = "enable_cmas_test_alerts";
    public static final String CB_EMERGENCY_ALERT = "enable_emergency_alerts";
    public static final String CB_ETWS_TEST_ALERT = "enable_etws_test_alerts";
    public static final String CB_EXTREME_THREAT_ALERT = "enable_cmas_extreme_threat_alerts";
    public static final String CB_OPT_OUT_DIALOG = "show_cmas_opt_out_dialog";
    public static final String CB_SEVERE_THREAT_ALERT = "enable_cmas_severe_threat_alerts";
    public static final Uri CONTENT_URI;
    public static final String CROSS_SIM_CALLING_ENABLED = "cross_sim_calling_enabled";

    @SystemApi
    public static final Uri CROSS_SIM_ENABLED_CONTENT_URI;
    public static final int D2D_SHARING_ALL = 3;
    public static final int D2D_SHARING_ALL_CONTACTS = 1;
    public static final int D2D_SHARING_DISABLED = 0;
    public static final int D2D_SHARING_SELECTED_CONTACTS = 2;
    public static final String D2D_STATUS_SHARING = "d2d_sharing_status";
    public static final String D2D_STATUS_SHARING_SELECTED_CONTACTS = "d2d_sharing_contacts";
    public static final String DATA_ROAMING = "data_roaming";
    public static final int DATA_ROAMING_DISABLE = 0;
    public static final int DATA_ROAMING_ENABLE = 1;
    private static final boolean DBG = false;
    public static final int DEFAULT_NAME_RES = 17039374;
    public static final int DEFAULT_PHONE_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SIM_SLOT_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SUBSCRIPTION_ID = Integer.MAX_VALUE;
    public static final String DISPLAY_NAME = "display_name";
    public static final String EHPLMNS = "ehplmns";
    public static final String ENABLED_MOBILE_DATA_POLICIES = "enabled_mobile_data_policies";
    public static final String ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";
    public static final String EXTRA_SLOT_INDEX = "android.telephony.extra.SLOT_INDEX";
    public static final String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";
    public static final String GET_SIM_SPECIFIC_SETTINGS_METHOD_NAME = "getSimSpecificSettings";
    public static final String GROUP_OWNER = "group_owner";
    public static final String GROUP_UUID = "group_uuid";
    public static final String HPLMNS = "hplmns";
    public static final String HUE = "color";
    public static final String ICC_ID = "icc_id";
    public static final String IMSI = "imsi";
    public static final String IMS_RCS_UCE_ENABLED = "ims_rcs_uce_enabled";
    public static final int INVALID_PHONE_INDEX = -1;
    public static final int INVALID_SIM_SLOT_INDEX = -1;
    public static final int INVALID_SUBSCRIPTION_ID = -1;
    public static final String ISO_COUNTRY_CODE = "iso_country_code";
    public static final String IS_EMBEDDED = "is_embedded";
    public static final String IS_ONLY_NTN = "is_only_ntn";
    public static final String IS_OPPORTUNISTIC = "is_opportunistic";
    public static final String IS_REMOVABLE = "is_removable";
    public static final String IS_SATELLITE_PROVISIONED_FOR_NON_IP_DATAGRAM = "is_satellite_provisioned_for_non_ip_datagram";
    public static final String KEY_SIM_SPECIFIC_SETTINGS_DATA = "KEY_SIM_SPECIFIC_SETTINGS_DATA";
    private static final String LOG_TAG = "SubscriptionManager";
    private static final int MAX_CACHE_SIZE = 4;
    private static final int MAX_RESOURCE_CACHE_ENTRY_COUNT = 1000;
    public static final int MAX_SUBSCRIPTION_ID_VALUE = 2147483646;
    public static final String MCC = "mcc";
    public static final String MCC_STRING = "mcc_string";
    public static final int MIN_SUBSCRIPTION_ID_VALUE = 0;
    public static final String MNC = "mnc";
    public static final String MNC_STRING = "mnc_string";
    public static final String NAME_SOURCE = "name_source";
    public static final int NAME_SOURCE_CARRIER = 3;
    public static final int NAME_SOURCE_CARRIER_ID = 0;
    public static final int NAME_SOURCE_SIM_PNN = 4;
    public static final int NAME_SOURCE_SIM_SPN = 1;
    public static final int NAME_SOURCE_UNKNOWN = -1;
    public static final int NAME_SOURCE_USER_INPUT = 2;
    public static final String NR_ADVANCED_CALLING_ENABLED = "nr_advanced_calling_enabled";
    public static final String NUMBER = "number";
    public static final int PHONE_NUMBER_SOURCE_CARRIER = 2;
    public static final int PHONE_NUMBER_SOURCE_IMS = 3;
    public static final int PHONE_NUMBER_SOURCE_UICC = 1;
    public static final int PLACEHOLDER_SUBSCRIPTION_ID_BASE = -2;
    public static final String PORT_INDEX = "port_index";
    public static final String PROFILE_CLASS = "profile_class";

    @SystemApi
    @Deprecated
    public static final int PROFILE_CLASS_DEFAULT = -1;

    @SystemApi
    public static final int PROFILE_CLASS_OPERATIONAL = 2;

    @SystemApi
    public static final int PROFILE_CLASS_PROVISIONING = 1;

    @SystemApi
    public static final int PROFILE_CLASS_TESTING = 0;

    @SystemApi
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final String RESTORE_SIM_SPECIFIC_SETTINGS_DATABASE_UPDATED = "restoreSimSpecificSettingsDatabaseUpdated";
    public static final String RESTORE_SIM_SPECIFIC_SETTINGS_METHOD_NAME = "restoreSimSpecificSettings";
    public static final String SATELLITE_ATTACH_ENABLED_FOR_CARRIER = "satellite_attach_enabled_for_carrier";
    public static final String SATELLITE_ENABLED = "satellite_enabled";
    public static final String SATELLITE_ENTITLEMENT_BARRED_PLMNS = "satellite_entitlement_barred_plmns";
    public static final String SATELLITE_ENTITLEMENT_DATA_PLAN_PLMNS = "satellite_entitlement_data_plan_plmns";
    public static final String SATELLITE_ENTITLEMENT_DATA_SERVICE_POLICY = "satellite_entitlement_data_service_policy";
    public static final String SATELLITE_ENTITLEMENT_PLMNS = "satellite_entitlement_plmns";
    public static final String SATELLITE_ENTITLEMENT_SERVICE_TYPE_MAP = "satellite_entitlement_service_type_map";
    public static final String SATELLITE_ENTITLEMENT_STATUS = "satellite_entitlement_status";
    public static final String SATELLITE_ENTITLEMENT_VOICE_SERVICE_POLICY = "satellite_entitlement_voice_service_policy";
    public static final String SATELLITE_ESOS_SUPPORTED = "satellite_esos_supported";
    public static final int SEM_PROFILE_CLASS_OPERATIONAL = 2;
    public static final int SEM_PROFILE_CLASS_PROVISIONING = 1;
    public static final int SEM_PROFILE_CLASS_TESTING = 0;
    public static final int SEM_PROFILE_CLASS_UNSET = -1;
    public static final String SERVICE_CAPABILITIES = "service_capabilities";
    public static final int SERVICE_CAPABILITY_DATA = 3;
    public static final int SERVICE_CAPABILITY_DATA_BITMASK;
    public static final int SERVICE_CAPABILITY_MAX = 3;
    public static final int SERVICE_CAPABILITY_SMS = 2;
    public static final int SERVICE_CAPABILITY_SMS_BITMASK;
    public static final int SERVICE_CAPABILITY_VOICE = 1;
    public static final int SERVICE_CAPABILITY_VOICE_BITMASK;
    public static final Uri SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI;
    public static final Uri SIM_INFO_SUW_RESTORE_CONTENT_URI;
    public static final int SIM_NOT_INSERTED = -1;
    public static final String SIM_SLOT_INDEX = "sim_id";
    public static final int SLOT_INDEX_FOR_REMOTE_SIM_SUB = -1;
    public static final String SUBSCRIPTION_TYPE = "subscription_type";
    public static final int SUBSCRIPTION_TYPE_LOCAL_SIM = 0;
    public static final int SUBSCRIPTION_TYPE_REMOTE_SIM = 1;
    public static final String SUB_DEFAULT_CHANGED_ACTION = "android.intent.action.SUB_DEFAULT_CHANGED";
    public static final String TP_MESSAGE_REF = "tp_message_ref";
    public static final String TRANSFER_STATUS = "transfer_status";

    @SystemApi
    public static final int TRANSFER_STATUS_CONVERTED = 2;

    @SystemApi
    public static final int TRANSFER_STATUS_NONE = 0;

    @SystemApi
    public static final int TRANSFER_STATUS_TRANSFERRED_OUT = 1;
    public static final String UICC_APPLICATIONS_ENABLED = "uicc_applications_enabled";
    public static final String UNIQUE_KEY_SUBSCRIPTION_ID = "_id";
    public static final String USAGE_SETTING = "usage_setting";
    public static final int USAGE_SETTING_DATA_CENTRIC = 2;
    public static final int USAGE_SETTING_DEFAULT = 0;
    public static final int USAGE_SETTING_UNKNOWN = -1;
    public static final int USAGE_SETTING_VOICE_CENTRIC = 1;
    public static final String USER_HANDLE = "user_handle";
    private static final boolean VDBG = false;
    public static final String VOIMS_OPT_IN_STATUS = "voims_opt_in_status";

    @SystemApi
    public static final Uri VT_ENABLED_CONTENT_URI;
    public static final String VT_IMS_ENABLED = "vt_ims_enabled";

    @SystemApi
    public static final Uri WFC_ENABLED_CONTENT_URI;
    public static final String WFC_IMS_ENABLED = "wfc_ims_enabled";
    public static final String WFC_IMS_MODE = "wfc_ims_mode";
    public static final String WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";
    public static final String WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";

    @SystemApi
    public static final Uri WFC_MODE_CONTENT_URI;

    @SystemApi
    public static final Uri WFC_ROAMING_ENABLED_CONTENT_URI;

    @SystemApi
    public static final Uri WFC_ROAMING_MODE_CONTENT_URI;
    private static VoidPropertyInvalidatedCache<Integer> sGetActiveDataSubscriptionIdCache;
    private static VoidPropertyInvalidatedCache<Integer> sGetDefaultDataSubIdCache;
    private static IntegerPropertyInvalidatedCache<Integer> sGetDefaultSmsSubIdCacheAsUser;
    private static IntegerPropertyInvalidatedCache<Integer> sGetDefaultSubIdCacheAsUser;
    private static IntegerPropertyInvalidatedCache<Integer> sGetPhoneIdCache;
    private static IntegerPropertyInvalidatedCache<Integer> sGetSlotIndexCache;
    private static IntegerPropertyInvalidatedCache<Integer> sGetSubIdCache;
    private static final LruCache<Pair<String, Configuration>, Resources> sResourcesCache;
    private final Context mContext;
    private final boolean mIsForAllUserProfiles;

    /* JADX INFO: Access modifiers changed from: private */
    interface CallISubMethodHelper {
        int callMethod(ISub iSub) throws RemoteException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataRoamingMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceToDeviceStatusSharingPreference {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PhoneNumberSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemProfileClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimDisplayNameSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriptionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransferStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsageSetting {
    }

    public static boolean isUsableSubIdValue(int i) {
        return i >= 0 && i <= 2147483646;
    }

    public static boolean isValidSubscriptionId(int i) {
        return i > -1;
    }

    public static int serviceCapabilityToBitmask(int i) {
        return 1 << (i - 1);
    }

    static {
        Uri uri = Telephony.SimInfo.CONTENT_URI;
        CONTENT_URI = uri;
        sGetDefaultSubIdCacheAsUser = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda9
            @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
            public final Object applyOrThrow(Object obj, Object obj2) {
                return Integer.valueOf(((ISub) obj).getDefaultSubIdAsUser(((Integer) obj2).intValue()));
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        sGetDefaultDataSubIdCache = new VoidPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda10
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final Object applyOrThrow(Object obj) {
                return Integer.valueOf(((ISub) obj).getDefaultDataSubId());
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        sGetDefaultSmsSubIdCacheAsUser = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda11
            @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
            public final Object applyOrThrow(Object obj, Object obj2) {
                return Integer.valueOf(((ISub) obj).getDefaultSmsSubIdAsUser(((Integer) obj2).intValue()));
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        sGetActiveDataSubscriptionIdCache = new VoidPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda12
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final Object applyOrThrow(Object obj) {
                return Integer.valueOf(((ISub) obj).getActiveDataSubscriptionId());
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        sGetSlotIndexCache = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda13
            @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
            public final Object applyOrThrow(Object obj, Object obj2) {
                return Integer.valueOf(((ISub) obj).getSlotIndex(((Integer) obj2).intValue()));
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        sGetSubIdCache = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda14
            @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
            public final Object applyOrThrow(Object obj, Object obj2) {
                return Integer.valueOf(((ISub) obj).getSubId(((Integer) obj2).intValue()));
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        sGetPhoneIdCache = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda15
            @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
            public final Object applyOrThrow(Object obj, Object obj2) {
                return Integer.valueOf(((ISub) obj).getPhoneId(((Integer) obj2).intValue()));
            }
        }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
        WFC_ENABLED_CONTENT_URI = Uri.withAppendedPath(uri, "wfc");
        ADVANCED_CALLING_ENABLED_CONTENT_URI = Uri.withAppendedPath(uri, "advanced_calling");
        WFC_MODE_CONTENT_URI = Uri.withAppendedPath(uri, "wfc_mode");
        WFC_ROAMING_MODE_CONTENT_URI = Uri.withAppendedPath(uri, "wfc_roaming_mode");
        VT_ENABLED_CONTENT_URI = Uri.withAppendedPath(uri, "vt_enabled");
        WFC_ROAMING_ENABLED_CONTENT_URI = Uri.withAppendedPath(uri, "wfc_roaming_enabled");
        Uri withAppendedPath = Uri.withAppendedPath(uri, "backup_and_restore");
        SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI = withAppendedPath;
        SIM_INFO_SUW_RESTORE_CONTENT_URI = Uri.withAppendedPath(withAppendedPath, "suw_restore");
        CROSS_SIM_ENABLED_CONTENT_URI = Uri.withAppendedPath(uri, "cross_sim_calling_enabled");
        SERVICE_CAPABILITY_VOICE_BITMASK = serviceCapabilityToBitmask(1);
        SERVICE_CAPABILITY_SMS_BITMASK = serviceCapabilityToBitmask(2);
        SERVICE_CAPABILITY_DATA_BITMASK = serviceCapabilityToBitmask(3);
        sResourcesCache = new LruCache<>(1000);
    }

    private static class VoidPropertyInvalidatedCache<T> extends PropertyInvalidatedCache<Void, T> {
        private final String mCacheKeyProperty;
        private final T mDefaultValue;
        private final FunctionalUtils.ThrowingFunction<ISub, T> mInterfaceMethod;

        VoidPropertyInvalidatedCache(FunctionalUtils.ThrowingFunction<ISub, T> throwingFunction, String str, T t) {
            super(4, str);
            this.mInterfaceMethod = throwingFunction;
            this.mCacheKeyProperty = str;
            this.mDefaultValue = t;
        }

        @Override // android.app.PropertyInvalidatedCache
        public T recompute(Void r1) {
            try {
                return this.mInterfaceMethod.applyOrThrow(TelephonyManager.getSubscriptionService());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public T query(Void r3) {
            T t = this.mDefaultValue;
            try {
                if (TelephonyManager.getSubscriptionService() != null) {
                    return (T) super.query((VoidPropertyInvalidatedCache<T>) r3);
                }
            } catch (Exception unused) {
                com.android.telephony.Rlog.w(SubscriptionManager.LOG_TAG, "Failed to recompute cache key for " + this.mCacheKeyProperty);
            }
            return t;
        }
    }

    private static class IntegerPropertyInvalidatedCache<T> extends PropertyInvalidatedCache<Integer, T> {
        private final String mCacheKeyProperty;
        private final T mDefaultValue;
        private final FunctionalUtils.ThrowingBiFunction<ISub, Integer, T> mInterfaceMethod;

        IntegerPropertyInvalidatedCache(FunctionalUtils.ThrowingBiFunction<ISub, Integer, T> throwingBiFunction, String str, T t) {
            super(4, str);
            this.mInterfaceMethod = throwingBiFunction;
            this.mCacheKeyProperty = str;
            this.mDefaultValue = t;
        }

        @Override // android.app.PropertyInvalidatedCache
        public T recompute(Integer num) {
            try {
                return this.mInterfaceMethod.applyOrThrow(TelephonyManager.getSubscriptionService(), num);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public T query(Integer num) {
            T t = this.mDefaultValue;
            try {
                if (TelephonyManager.getSubscriptionService() != null) {
                    return (T) super.query((IntegerPropertyInvalidatedCache<T>) num);
                }
            } catch (Exception unused) {
                com.android.telephony.Rlog.w(SubscriptionManager.LOG_TAG, "Failed to recompute cache key for " + this.mCacheKeyProperty);
            }
            return t;
        }
    }

    public static Uri getUriForSubscriptionId(int i) {
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(i));
    }

    public static class OnSubscriptionsChangedListener {
        private static final long LAZY_INITIALIZE_SUBSCRIPTIONS_CHANGED_HANDLER = 278814050;
        private final Looper mCreatorLooper;

        public void onSubscriptionsChanged() {
        }

        public Looper getCreatorLooper() {
            return this.mCreatorLooper;
        }

        public OnSubscriptionsChangedListener() {
            Looper myLooper = Looper.myLooper();
            this.mCreatorLooper = myLooper;
            if (myLooper != null || Compatibility.isChangeEnabled(LAZY_INITIALIZE_SUBSCRIPTIONS_CHANGED_HANDLER)) {
                return;
            }
            throw new RuntimeException("Can't create handler inside thread " + Thread.currentThread() + " that has not called Looper.prepare()");
        }

        public OnSubscriptionsChangedListener(Looper looper) {
            Objects.requireNonNull(looper);
            this.mCreatorLooper = looper;
        }

        public void onAddListenerFailed() {
            com.android.telephony.Rlog.w(SubscriptionManager.LOG_TAG, "onAddListenerFailed not overridden");
        }

        private void log(String str) {
            com.android.telephony.Rlog.d(SubscriptionManager.LOG_TAG, str);
        }
    }

    public SubscriptionManager(Context context) {
        this(context, false);
    }

    private SubscriptionManager(Context context, boolean z) {
        this.mIsForAllUserProfiles = z;
        this.mContext = context;
    }

    private NetworkPolicyManager getNetworkPolicyManager() {
        return (NetworkPolicyManager) this.mContext.getSystemService(Context.NETWORK_POLICY_SERVICE);
    }

    @Deprecated
    public static SubscriptionManager from(Context context) {
        return (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    @Deprecated
    public void addOnSubscriptionsChangedListener(OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        if (onSubscriptionsChangedListener == null) {
            return;
        }
        Looper creatorLooper = onSubscriptionsChangedListener.getCreatorLooper();
        if (creatorLooper == null) {
            throw new RuntimeException("Can't create handler inside thread " + Thread.currentThread() + " that has not called Looper.prepare()");
        }
        addOnSubscriptionsChangedListener(new HandlerExecutor(new Handler(creatorLooper)), onSubscriptionsChangedListener);
    }

    public void addOnSubscriptionsChangedListener(Executor executor, final OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.addOnSubscriptionsChangedListener(onSubscriptionsChangedListener, executor);
            return;
        }
        loge("addOnSubscriptionsChangedListener: pkgname=" + opPackageName + " failed to be added  due to TELEPHONY_REGISTRY_SERVICE being unavailable.");
        executor.execute(new Runnable() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SubscriptionManager.OnSubscriptionsChangedListener.this.onAddListenerFailed();
            }
        });
    }

    public void removeOnSubscriptionsChangedListener(OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        if (onSubscriptionsChangedListener == null) {
            return;
        }
        Context context = this.mContext;
        if (context != null) {
            context.getOpPackageName();
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.removeOnSubscriptionsChangedListener(onSubscriptionsChangedListener);
        }
    }

    public static class OnOpportunisticSubscriptionsChangedListener {
        public void onOpportunisticSubscriptionsChanged() {
        }

        private void log(String str) {
            com.android.telephony.Rlog.d(SubscriptionManager.LOG_TAG, str);
        }
    }

    public void addOnOpportunisticSubscriptionsChangedListener(Executor executor, OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        if (executor == null || onOpportunisticSubscriptionsChangedListener == null) {
            return;
        }
        Context context = this.mContext;
        if (context != null) {
            context.getOpPackageName();
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.addOnOpportunisticSubscriptionsChangedListener(onOpportunisticSubscriptionsChangedListener, executor);
        }
    }

    public void removeOnOpportunisticSubscriptionsChangedListener(OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        Preconditions.checkNotNull(onOpportunisticSubscriptionsChangedListener, "listener cannot be null");
        Context context = this.mContext;
        if (context != null) {
            context.getOpPackageName();
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.removeOnOpportunisticSubscriptionsChangedListener(onOpportunisticSubscriptionsChangedListener);
        }
    }

    public SubscriptionInfo getActiveSubscriptionInfo(int i) {
        if (!isValidSubscriptionId(i)) {
            return null;
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubscriptionInfo(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    @SystemApi
    public SubscriptionInfo getActiveSubscriptionInfoForIcc(String str) {
        if (str == null) {
            logd("[getActiveSubscriptionInfoForIccIndex]- null iccid");
            return null;
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubscriptionInfoForIccId(str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i) {
        if (!isValidSlotIndex(i)) {
            logd("[getActiveSubscriptionInfoForSimSlotIndex]- invalid slotIndex");
            return null;
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubscriptionInfoForSimSlotIndex(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    public List<SubscriptionInfo> getAllSubscriptionInfoList() {
        List<SubscriptionInfo> list = null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getAllSubInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException unused) {
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
        List<SubscriptionInfo> list = null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getActiveSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mIsForAllUserProfiles);
            }
        } catch (RemoteException unused) {
        }
        if (list != null) {
            return (List) list.stream().filter(new Predicate() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getActiveSubscriptionInfoList$1;
                    lambda$getActiveSubscriptionInfoList$1 = SubscriptionManager.this.lambda$getActiveSubscriptionInfoList$1((SubscriptionInfo) obj);
                    return lambda$getActiveSubscriptionInfoList$1;
                }
            }).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    public List<SubscriptionInfo> getCompleteActiveSubscriptionInfoList() {
        return getActiveSubscriptionInfoList(false);
    }

    public SubscriptionManager createForAllUserProfiles() {
        return new SubscriptionManager(this.mContext, true);
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList(boolean z) {
        List<SubscriptionInfo> list = null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getActiveSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), true);
            }
        } catch (RemoteException unused) {
        }
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return z ? (List) list.stream().filter(new Predicate() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getActiveSubscriptionInfoList$2;
                lambda$getActiveSubscriptionInfoList$2 = SubscriptionManager.this.lambda$getActiveSubscriptionInfoList$2((SubscriptionInfo) obj);
                return lambda$getActiveSubscriptionInfoList$2;
            }
        }).collect(Collectors.toList()) : list;
    }

    @SystemApi
    public List<SubscriptionInfo> getAvailableSubscriptionInfoList() {
        List<SubscriptionInfo> list = null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getAvailableSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException unused) {
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public List<SubscriptionInfo> semGetAvailableSubscriptionInfoListWithSelectable(boolean z) {
        if (z) {
            return getSelectableSubscriptionInfoList();
        }
        return getAvailableSubscriptionInfoList();
    }

    public List<SubscriptionInfo> getAccessibleSubscriptionInfoList() {
        List<SubscriptionInfo> list = null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getAccessibleSubscriptionInfoList(this.mContext.getOpPackageName());
            }
        } catch (RemoteException unused) {
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    @SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh() {
        int cardIdForDefaultEuicc = TelephonyManager.from(this.mContext).getCardIdForDefaultEuicc();
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.requestEmbeddedSubscriptionInfoListRefresh(cardIdForDefaultEuicc);
            }
        } catch (RemoteException unused) {
            logd("requestEmbeddedSubscriptionInfoListFresh for card = " + cardIdForDefaultEuicc + " failed.");
        }
    }

    @SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.requestEmbeddedSubscriptionInfoListRefresh(i);
            }
        } catch (RemoteException unused) {
            logd("requestEmbeddedSubscriptionInfoListFresh for card = " + i + " failed.");
        }
    }

    public int getActiveSubscriptionInfoCount() {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubInfoCount(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mIsForAllUserProfiles);
            }
            return 0;
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public int getActiveSubscriptionInfoCountMax() {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubInfoCountMax();
            }
            return 0;
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public Uri addSubscriptionInfoRecord(String str, int i) {
        if (str == null) {
            logd("[addSubscriptionInfoRecord]- null iccId");
        }
        if (!isValidSlotIndex(i)) {
            logd("[addSubscriptionInfoRecord]- invalid slotIndex");
        }
        addSubscriptionInfoRecord(str, null, i, 0);
        return null;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void addSubscriptionInfoRecord(String str, String str2, int i, int i2) {
        if (str == null) {
            Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- ISub service is null");
                return;
            }
            int addSubInfo = subscriptionService.addSubInfo(str, str2, i, i2);
            if (addSubInfo < 0) {
                Log.e(LOG_TAG, "Adding of subscription didn't succeed: error = " + addSubInfo);
                return;
            }
            logd("successfully added new subscription");
        } catch (RemoteException unused) {
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void removeSubscriptionInfoRecord(String str, int i) {
        if (str == null) {
            Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                Log.e(LOG_TAG, "[removeSubscriptionInfoRecord]- ISub service is null");
            } else if (!subscriptionService.removeSubInfo(str, i)) {
                Log.e(LOG_TAG, "Removal of subscription didn't succeed");
            } else {
                logd("successfully removed subscription");
            }
        } catch (RemoteException unused) {
        }
    }

    public int setIconTint(final int i, final int i2) {
        return setSubscriptionPropertyHelper(i2, "setIconTint", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda4
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int iconTint;
                iconTint = iSub.setIconTint(i2, i);
                return iconTint;
            }
        });
    }

    public int setDisplayName(final String str, final int i, final int i2) {
        return setSubscriptionPropertyHelper(i, "setDisplayName", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda16
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int displayNameUsingSrc;
                displayNameUsingSrc = iSub.setDisplayNameUsingSrc(str, i, i2);
                return displayNameUsingSrc;
            }
        });
    }

    public int setDisplayNumber(final String str, final int i) {
        if (str == null) {
            logd("[setDisplayNumber]- fail");
            return -1;
        }
        return setSubscriptionPropertyHelper(i, "setDisplayNumber", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda19
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int displayNumber;
                displayNumber = iSub.setDisplayNumber(str, i);
                return displayNumber;
            }
        });
    }

    public int setDataRoaming(final int i, final int i2) {
        return setSubscriptionPropertyHelper(i2, "setDataRoaming", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda5
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int dataRoaming;
                dataRoaming = iSub.setDataRoaming(i, i2);
                return dataRoaming;
            }
        });
    }

    public static int getSlotIndex(int i) {
        return sGetSlotIndexCache.query(Integer.valueOf(i)).intValue();
    }

    @Deprecated
    public int[] getSubscriptionIds(int i) {
        if (isValidSlotIndex(i)) {
            return new int[]{getSubscriptionId(i)};
        }
        return null;
    }

    @Deprecated
    public static int[] getSubId(int i) {
        if (isValidSlotIndex(i)) {
            return new int[]{getSubscriptionId(i)};
        }
        return null;
    }

    public static int getSubscriptionId(int i) {
        if (isValidSlotIndex(i)) {
            return sGetSubIdCache.query(Integer.valueOf(i)).intValue();
        }
        return -1;
    }

    public static int getPhoneId(int i) {
        return sGetPhoneIdCache.query(Integer.valueOf(i)).intValue();
    }

    private static void logd(String str) {
        com.android.telephony.Rlog.d(LOG_TAG, str);
    }

    private static void loge(String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }

    public static int getDefaultSubscriptionId() {
        return sGetDefaultSubIdCacheAsUser.query(Integer.valueOf(Process.myUserHandle().getIdentifier())).intValue();
    }

    public static int getDefaultVoiceSubscriptionId() {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getDefaultVoiceSubIdAsUser(Process.myUserHandle().getIdentifier());
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    @SystemApi
    public void setDefaultVoiceSubscriptionId(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setDefaultVoiceSubId(i);
            }
        } catch (RemoteException unused) {
        }
    }

    public void setDefaultVoiceSubId(int i) {
        setDefaultVoiceSubscriptionId(i);
    }

    public SubscriptionInfo getDefaultVoiceSubscriptionInfo() {
        return getActiveSubscriptionInfo(getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultVoicePhoneId() {
        return getPhoneId(getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultSmsSubscriptionId() {
        return sGetDefaultSmsSubIdCacheAsUser.query(Integer.valueOf(Process.myUserHandle().getIdentifier())).intValue();
    }

    @SystemApi
    public void setDefaultSmsSubId(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setDefaultSmsSubId(i);
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static int getDefaultDataSubscriptionId() {
        return sGetDefaultDataSubIdCache.query((Void) null).intValue();
    }

    @SystemApi
    public void setDefaultDataSubId(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setDefaultDataSubId(i);
            }
        } catch (RemoteException unused) {
        }
    }

    public SubscriptionInfo getDefaultDataSubscriptionInfo() {
        return getActiveSubscriptionInfo(getDefaultDataSubscriptionId());
    }

    public static boolean isUsableSubscriptionId(int i) {
        return isUsableSubIdValue(i);
    }

    public static boolean isValidSlotIndex(int i) {
        return i >= 0 && i < TelephonyManager.getDefault().getActiveModemCount();
    }

    public static boolean isValidPhoneId(int i) {
        return i >= 0 && i < TelephonyManager.getDefault().getActiveModemCount();
    }

    public static void putPhoneIdAndSubIdExtra(Intent intent, int i) {
        putPhoneIdAndMaybeSubIdExtra(intent, i, getSubscriptionId(i));
    }

    public static void putPhoneIdAndSubIdExtra(Intent intent, int i, int i2) {
        intent.putExtra("android.telephony.extra.SLOT_INDEX", i);
        intent.putExtra("phone", i);
        putSubscriptionIdExtra(intent, i2);
    }

    public static void putPhoneIdAndMaybeSubIdExtra(Intent intent, int i, int i2) {
        if (isValidSubscriptionId(i2)) {
            putPhoneIdAndSubIdExtra(intent, i, i2);
        } else {
            intent.putExtra("phone", i);
            intent.putExtra("android.telephony.extra.SLOT_INDEX", i);
        }
    }

    @SystemApi
    public int[] getActiveSubscriptionIdList() {
        return getActiveSubscriptionIdList(true);
    }

    @SystemApi
    public int[] getCompleteActiveSubscriptionIdList() {
        return getActiveSubscriptionIdList(false);
    }

    public int[] getActiveSubscriptionIdList(boolean z) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                int[] activeSubIdList = subscriptionService.getActiveSubIdList(z);
                if (activeSubIdList != null) {
                    return activeSubIdList;
                }
            }
        } catch (RemoteException unused) {
        }
        return new int[0];
    }

    public boolean isNetworkRoaming(int i) {
        if (getPhoneId(i) < 0) {
            return false;
        }
        return TelephonyManager.getDefault().isNetworkRoaming(i);
    }

    public static void setSubscriptionProperty(int i, String str, String str2) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setSubscriptionProperty(i, str, str2);
            }
        } catch (RemoteException unused) {
        }
    }

    public static String serializeUriLists(List<Uri> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<Uri> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toString());
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.flush();
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (IOException unused) {
            logd("serializeUriLists IO exception");
            return "";
        }
    }

    private static String getStringSubscriptionProperty(Context context, int i, String str) {
        String str2 = null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                str2 = subscriptionService.getSubscriptionProperty(i, str, context.getOpPackageName(), context.getAttributionTag());
            }
        } catch (RemoteException unused) {
        }
        return TextUtils.emptyIfNull(str2);
    }

    public static boolean getBooleanSubscriptionProperty(int i, String str, boolean z, Context context) {
        String stringSubscriptionProperty = getStringSubscriptionProperty(context, i, str);
        if (!stringSubscriptionProperty.isEmpty()) {
            try {
                return Integer.parseInt(stringSubscriptionProperty) == 1;
            } catch (NumberFormatException unused) {
                logd("getBooleanSubscriptionProperty NumberFormat exception");
            }
        }
        return z;
    }

    public static int getIntegerSubscriptionProperty(int i, String str, int i2, Context context) {
        String stringSubscriptionProperty = getStringSubscriptionProperty(context, i, str);
        if (!stringSubscriptionProperty.isEmpty()) {
            try {
                return Integer.parseInt(stringSubscriptionProperty);
            } catch (NumberFormatException unused) {
                logd("getIntegerSubscriptionProperty NumberFormat exception");
            }
        }
        return i2;
    }

    public static long getLongSubscriptionProperty(int i, String str, long j, Context context) {
        String stringSubscriptionProperty = getStringSubscriptionProperty(context, i, str);
        if (!stringSubscriptionProperty.isEmpty()) {
            try {
                return Long.parseLong(stringSubscriptionProperty);
            } catch (NumberFormatException unused) {
                logd("getLongSubscriptionProperty NumberFormat exception");
            }
        }
        return j;
    }

    @SystemApi
    public static Resources getResourcesForSubId(Context context, int i) {
        return getResourcesForSubId(context, i, false);
    }

    public static Resources getResourcesForSubId(Context context, int i, boolean z) {
        Pair<String, Configuration> pair;
        Pair<String, Configuration> pair2 = null;
        if (isValidSubscriptionId(i)) {
            Configuration configuration = new Configuration(context.getResources().getConfiguration());
            if (z) {
                configuration.setLocale(Locale.ROOT);
            }
            pair = Pair.create(context.getPackageName() + ", subid=" + i, configuration);
            LruCache<Pair<String, Configuration>, Resources> lruCache = sResourcesCache;
            synchronized (lruCache) {
                Resources resources = lruCache.get(pair);
                if (resources != null) {
                    return resources;
                }
            }
        } else {
            pair = null;
        }
        SubscriptionInfo activeSubscriptionInfo = from(context).getActiveSubscriptionInfo(i);
        Configuration configuration2 = new Configuration();
        if (activeSubscriptionInfo != null) {
            configuration2.mcc = activeSubscriptionInfo.getMcc();
            configuration2.mnc = activeSubscriptionInfo.getMnc();
            if (configuration2.mnc == 0) {
                configuration2.mnc = 65535;
            } else {
                pair2 = pair;
            }
        }
        if (z) {
            configuration2.setLocale(Locale.ROOT);
        }
        Resources resources2 = context.createConfigurationContext(configuration2).getResources();
        if (pair2 == null) {
            return resources2;
        }
        LruCache<Pair<String, Configuration>, Resources> lruCache2 = sResourcesCache;
        synchronized (lruCache2) {
            lruCache2.put(pair2, resources2);
        }
        return resources2;
    }

    public boolean isActiveSubscriptionId(int i) {
        return isActiveSubId(i);
    }

    public boolean isActiveSubId(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isActiveSubId(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public List<SubscriptionPlan> getSubscriptionPlans(int i) {
        SubscriptionPlan[] subscriptionPlans = getNetworkPolicyManager().getSubscriptionPlans(i, this.mContext.getOpPackageName());
        return subscriptionPlans == null ? Collections.EMPTY_LIST : Arrays.asList(subscriptionPlans);
    }

    @Deprecated
    public void setSubscriptionPlans(int i, List<SubscriptionPlan> list) {
        setSubscriptionPlans(i, list, 0L);
    }

    public void setSubscriptionPlans(int i, List<SubscriptionPlan> list, long j) {
        getNetworkPolicyManager().setSubscriptionPlans(i, (SubscriptionPlan[]) list.toArray(new SubscriptionPlan[0]), j, this.mContext.getOpPackageName());
    }

    public void setSubscriptionOverrideUnmetered(int i, boolean z, long j) {
        setSubscriptionOverrideUnmetered(i, z, TelephonyManager.getAllNetworkTypes(), j);
    }

    public void setSubscriptionOverrideUnmetered(int i, boolean z, int[] iArr, long j) {
        getNetworkPolicyManager().setSubscriptionOverride(i, 1, z ? 1 : 0, iArr, j, this.mContext.getOpPackageName());
    }

    public void setSubscriptionOverrideCongested(int i, boolean z, long j) {
        setSubscriptionOverrideCongested(i, z, TelephonyManager.getAllNetworkTypes(), j);
    }

    public void setSubscriptionOverrideCongested(int i, boolean z, int[] iArr, long j) {
        getNetworkPolicyManager().setSubscriptionOverride(i, 2, z ? 2 : 0, iArr, j, this.mContext.getOpPackageName());
    }

    public boolean canManageSubscription(SubscriptionInfo subscriptionInfo) {
        return canManageSubscription(subscriptionInfo, this.mContext.getPackageName());
    }

    @SystemApi
    public boolean canManageSubscription(SubscriptionInfo subscriptionInfo, String str) {
        if (Flags.hsumPackageManager()) {
            return canManageSubscriptionAsUser(subscriptionInfo, str, this.mContext.getUser());
        }
        if (subscriptionInfo != null && subscriptionInfo.getAccessRules() != null && str != null) {
            try {
                PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(str, 134217728);
                Iterator<UiccAccessRule> it = subscriptionInfo.getAccessRules().iterator();
                while (it.hasNext()) {
                    if (it.next().getCarrierPrivilegeStatus(packageInfo) == 1) {
                        return true;
                    }
                }
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
                logd("Unknown package: " + str);
            }
        }
        return false;
    }

    public boolean canManageSubscriptionAsUser(SubscriptionInfo subscriptionInfo, String str, UserHandle userHandle) {
        PackageManager packageManager;
        if (subscriptionInfo != null && subscriptionInfo.getAccessRules() != null && str != null) {
            if (this.mContext.getUser().equals(userHandle)) {
                packageManager = this.mContext.getPackageManager();
            } else {
                packageManager = this.mContext.createContextAsUser(userHandle, 0).getPackageManager();
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 134217728);
                Iterator<UiccAccessRule> it = subscriptionInfo.getAccessRules().iterator();
                while (it.hasNext()) {
                    if (it.next().getCarrierPrivilegeStatus(packageInfo) == 1) {
                        return true;
                    }
                }
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
                logd("Unknown package: " + str);
            }
        }
        return false;
    }

    @SystemApi
    public void setPreferredDataSubscriptionId(int i, boolean z, Executor executor, Consumer<Integer> consumer) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                throw new IllegalStateException("subscription manager service is null.");
            }
            subscriptionService.setPreferredDataSubscriptionId(i, z, new AnonymousClass1(this, executor, consumer));
        } catch (RemoteException e) {
            loge("setPreferredDataSubscriptionId RemoteException=" + e);
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.SubscriptionManager$1, reason: invalid class name */
    class AnonymousClass1 extends ISetOpportunisticDataCallback.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(SubscriptionManager subscriptionManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(final int i) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.SubscriptionManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(i));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public int semGetPreferredDataSubscriptionId() {
        return getPreferredDataSubscriptionId();
    }

    public int getPreferredDataSubscriptionId() {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getPreferredDataSubscriptionId();
            }
            return Integer.MAX_VALUE;
        } catch (RemoteException unused) {
            return Integer.MAX_VALUE;
        }
    }

    public List<SubscriptionInfo> getOpportunisticSubscriptions() {
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        Context context2 = this.mContext;
        List<SubscriptionInfo> list = null;
        String attributionTag = context2 != null ? context2.getAttributionTag() : null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getOpportunisticSubscriptions(opPackageName, attributionTag);
            }
        } catch (RemoteException unused) {
        }
        return list == null ? new ArrayList() : list;
    }

    @Deprecated
    public void switchToSubscription(int i, PendingIntent pendingIntent) {
        Preconditions.checkNotNull(pendingIntent, "callbackIntent cannot be null");
        new EuiccManager(this.mContext).switchToSubscription(i, pendingIntent);
    }

    public boolean setOpportunistic(final boolean z, final int i) {
        return setSubscriptionPropertyHelper(i, "setOpportunistic", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda18
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int lambda$setOpportunistic$7;
                lambda$setOpportunistic$7 = SubscriptionManager.this.lambda$setOpportunistic$7(z, i, iSub);
                return lambda$setOpportunistic$7;
            }
        }) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setOpportunistic$7(boolean z, int i, ISub iSub) throws RemoteException {
        return iSub.setOpportunistic(z, i, this.mContext.getOpPackageName());
    }

    public ParcelUuid createSubscriptionGroup(List<Integer> list) {
        Preconditions.checkNotNull(list, "can't create group for null subId list");
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        int[] array = list.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.createSubscriptionGroup(array, opPackageName);
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("createSubscriptionGroup RemoteException " + e);
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    public void addSubscriptionsIntoGroup(List<Integer> list, ParcelUuid parcelUuid) {
        Preconditions.checkNotNull(list, "subIdList can't be null.");
        Preconditions.checkNotNull(parcelUuid, "groupUuid can't be null.");
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        int[] array = list.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda20
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.addSubscriptionsIntoGroup(array, parcelUuid, opPackageName);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("addSubscriptionsIntoGroup RemoteException " + e);
            e.rethrowAsRuntimeException();
        }
    }

    private boolean isSystemProcess() {
        return UserHandle.isSameApp(Process.myUid(), 1000);
    }

    public void removeSubscriptionsFromGroup(List<Integer> list, ParcelUuid parcelUuid) {
        Preconditions.checkNotNull(list, "subIdList can't be null.");
        Preconditions.checkNotNull(parcelUuid, "groupUuid can't be null.");
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        int[] array = list.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.removeSubscriptionsFromGroup(array, parcelUuid, opPackageName);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException e) {
            loge("removeSubscriptionsFromGroup RemoteException " + e);
            e.rethrowAsRuntimeException();
        }
    }

    public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid parcelUuid) {
        Preconditions.checkNotNull(parcelUuid, "groupUuid can't be null");
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        Context context2 = this.mContext;
        List<SubscriptionInfo> list = null;
        String attributionTag = context2 != null ? context2.getAttributionTag() : null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getSubscriptionsInGroup(parcelUuid, opPackageName, attributionTag);
            } else if (!isSystemProcess()) {
                throw new IllegalStateException("telephony service is null.");
            }
        } catch (RemoteException e) {
            loge("removeSubscriptionsFromGroup RemoteException " + e);
            if (!isSystemProcess()) {
                e.rethrowAsRuntimeException();
            }
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    /* renamed from: isSubscriptionVisible, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean lambda$getActiveSubscriptionInfoList$2(SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo == null) {
            return false;
        }
        return subscriptionInfo.getGroupUuid() == null || !subscriptionInfo.isOpportunistic() || TelephonyManager.from(this.mContext).hasCarrierPrivileges(subscriptionInfo.getSubscriptionId()) || canManageSubscription(subscriptionInfo);
    }

    public List<SubscriptionInfo> getSelectableSubscriptionInfoList() {
        List<SubscriptionInfo> availableSubscriptionInfoList = getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
            if (subscriptionInfo.getGroupUuid() == null || !subscriptionInfo.isOpportunistic()) {
                ParcelUuid groupUuid = subscriptionInfo.getGroupUuid();
                if (groupUuid == null) {
                    arrayList.add(subscriptionInfo);
                } else if (!hashMap.containsKey(groupUuid) || (((SubscriptionInfo) hashMap.get(groupUuid)).getSimSlotIndex() == -1 && subscriptionInfo.getSimSlotIndex() != -1)) {
                    arrayList.remove(hashMap.get(groupUuid));
                    arrayList.add(subscriptionInfo);
                    hashMap.put(groupUuid, subscriptionInfo);
                }
            }
        }
        return arrayList;
    }

    @SystemApi
    public boolean setSubscriptionEnabled(int i, boolean z) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return true;
            }
            subscriptionService.setUiccApplicationsEnabled(z, i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean semSetSubscriptionEnabled(int i, boolean z) {
        return setSubscriptionEnabled(i, z);
    }

    @SystemApi
    public void setUiccApplicationsEnabled(int i, boolean z) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setUiccApplicationsEnabled(z, i);
            }
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    public boolean canDisablePhysicalSubscription() {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.canDisablePhysicalSubscription();
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    @SystemApi
    public boolean isSubscriptionEnabled(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isSubscriptionEnabled(i);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean semIsSubscriptionEnabled(int i) {
        return isSubscriptionEnabled(i);
    }

    public void setDeviceToDeviceStatusSharingPreference(final int i, final int i2) {
        setSubscriptionPropertyHelper(i, "setDeviceToDeviceSharingStatus", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda1
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int deviceToDeviceStatusSharing;
                deviceToDeviceStatusSharing = iSub.setDeviceToDeviceStatusSharing(i2, i);
                return deviceToDeviceStatusSharing;
            }
        });
    }

    public int getDeviceToDeviceStatusSharingPreference(int i) {
        return getIntegerSubscriptionProperty(i, "d2d_sharing_status", 0, this.mContext);
    }

    public void setDeviceToDeviceStatusSharingContacts(final int i, final List<Uri> list) {
        serializeUriLists(list);
        setSubscriptionPropertyHelper(i, "setDeviceToDeviceSharingStatus", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda8
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int deviceToDeviceStatusSharingContacts;
                deviceToDeviceStatusSharingContacts = iSub.setDeviceToDeviceStatusSharingContacts(SubscriptionManager.serializeUriLists(list), i);
                return deviceToDeviceStatusSharingContacts;
            }
        });
    }

    public List<Uri> getDeviceToDeviceStatusSharingContacts(int i) {
        String stringSubscriptionProperty = getStringSubscriptionProperty(this.mContext, i, "d2d_sharing_contacts");
        if (stringSubscriptionProperty != null) {
            try {
                List list = (List) ArrayList.class.cast(new ObjectInputStream(new ByteArrayInputStream(Base64.decode(stringSubscriptionProperty, 0))).readObject());
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Uri.parse((String) it.next()));
                }
                return arrayList;
            } catch (IOException unused) {
                logd("getDeviceToDeviceStatusSharingContacts IO exception");
            } catch (ClassNotFoundException unused2) {
                logd("getDeviceToDeviceStatusSharingContacts ClassNotFound exception");
            }
        }
        return new ArrayList();
    }

    @SystemApi
    public int getEnabledSubscriptionId(int i) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getEnabledSubscriptionId(i);
            }
            return -1;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    private int setSubscriptionPropertyHelper(int i, String str, CallISubMethodHelper callISubMethodHelper) {
        if (!isValidSubscriptionId(i)) {
            logd(NavigationBarInflaterView.SIZE_MOD_START + str + "]- fail");
            return -1;
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return callISubMethodHelper.callMethod(subscriptionService);
            }
            return 0;
        } catch (RemoteException unused) {
            return 0;
        }
    }

    public static int getActiveDataSubscriptionId() {
        return sGetActiveDataSubscriptionIdCache.query((Void) null).intValue();
    }

    public static void putSubscriptionIdExtra(Intent intent, int i) {
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        intent.putExtra(PhoneConstants.SUBSCRIPTION_KEY, i);
    }

    public static void invalidateSubscriptionManagerServiceCaches() {
        PropertyInvalidatedCache.invalidateCache(CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY);
    }

    public static void disableCaching() {
        sGetDefaultSubIdCacheAsUser.disableLocal();
        sGetDefaultDataSubIdCache.disableLocal();
        sGetActiveDataSubscriptionIdCache.disableLocal();
        sGetDefaultSmsSubIdCacheAsUser.disableLocal();
        sGetSlotIndexCache.disableLocal();
        sGetSubIdCache.disableLocal();
        sGetPhoneIdCache.disableLocal();
    }

    public static void clearCaches() {
        sGetDefaultSubIdCacheAsUser.clear();
        sGetDefaultDataSubIdCache.clear();
        sGetActiveDataSubscriptionIdCache.clear();
        sGetDefaultSmsSubIdCacheAsUser.clear();
        sGetSlotIndexCache.clear();
        sGetSubIdCache.clear();
        sGetPhoneIdCache.clear();
    }

    @SystemApi
    public byte[] getAllSimSpecificSettingsForBackup() {
        return this.mContext.getContentResolver().call(SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI, GET_SIM_SPECIFIC_SETTINGS_METHOD_NAME, (String) null, (Bundle) null).getByteArray(KEY_SIM_SPECIFIC_SETTINGS_DATA);
    }

    @SystemApi
    public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.restoreAllSimSpecificSettingsFromBackup(bArr);
                return;
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException e) {
            if (isSystemProcess()) {
                return;
            }
            e.rethrowAsRuntimeException();
        }
    }

    public String getPhoneNumber(int i, int i2) {
        if (i == Integer.MAX_VALUE) {
            i = getDefaultSubscriptionId();
        }
        if (i2 != 1 && i2 != 2 && i2 != 3) {
            throw new IllegalArgumentException("invalid source " + i2);
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getPhoneNumber(i, i2, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getPhoneNumber(int i) {
        if (i == Integer.MAX_VALUE) {
            i = getDefaultSubscriptionId();
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getPhoneNumberFromFirstAvailableSource(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setCarrierPhoneNumber(int i, String str) {
        if (i == Integer.MAX_VALUE) {
            i = getDefaultSubscriptionId();
        }
        int i2 = i;
        if (str == null) {
            throw new NullPointerException("invalid number null");
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setPhoneNumber(i2, 2, str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    void setUsageSetting(final int i, final int i2) {
        setSubscriptionPropertyHelper(i, "setUsageSetting", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda3
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int lambda$setUsageSetting$13;
                lambda$setUsageSetting$13 = SubscriptionManager.this.lambda$setUsageSetting$13(i2, i, iSub);
                return lambda$setUsageSetting$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setUsageSetting$13(int i, int i2, ISub iSub) throws RemoteException {
        return iSub.setUsageSetting(i, i2, this.mContext.getOpPackageName());
    }

    public static String phoneNumberSourceToString(int i) {
        if (i == 1) {
            return "UICC";
        }
        if (i == 2) {
            return "CARRIER";
        }
        if (i == 3) {
            return "IMS";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String displayNameSourceToString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "CARRIER_ID";
        }
        if (i == 1) {
            return "SIM_SPN";
        }
        if (i == 2) {
            return "USER_INPUT";
        }
        if (i == 3) {
            return "CARRIER";
        }
        if (i == 4) {
            return "SIM_PNN";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String subscriptionTypeToString(int i) {
        if (i == 0) {
            return "LOCAL_SIM";
        }
        if (i == 1) {
            return "REMOTE_SIM";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String usageSettingToString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "DEFAULT";
        }
        if (i == 1) {
            return "VOICE_CENTRIC";
        }
        if (i == 2) {
            return "DATA_CENTRIC";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public void setGroupOwner(int i, String str) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setGroupOwner(i, str);
                return;
            }
            throw new IllegalStateException("[setGroupOwner]: subscription service unavailable");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void setSubscriptionUserHandle(int i, UserHandle userHandle) {
        if (!isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("[setSubscriptionUserHandle]: Invalid subscriptionId: " + i);
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setSubscriptionUserHandle(userHandle, i);
                return;
            }
            throw new IllegalStateException("[setSubscriptionUserHandle]: subscription service unavailable");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public UserHandle getSubscriptionUserHandle(int i) {
        if (!isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("[getSubscriptionUserHandle]: Invalid subscriptionId: " + i);
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getSubscriptionUserHandle(i);
            }
            Log.e(LOG_TAG, "[getSubscriptionUserHandle]: subscription service unavailable");
            return null;
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    public boolean isSubscriptionAssociatedWithUser(int i, UserHandle userHandle) {
        if (!isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("[isSubscriptionAssociatedWithUser]: Invalid subscriptionId: " + i);
        }
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isSubscriptionAssociatedWithUser(i, userHandle);
            }
            Log.e(LOG_TAG, "[isSubscriptionAssociatedWithUser]: subscription service unavailable");
            return false;
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isSubscriptionAssociatedWithUser(int i) {
        if (!isValidSubscriptionId(i)) {
            throw new IllegalArgumentException("[isSubscriptionAssociatedWithCallingUser]: Invalid subscriptionId: " + i);
        }
        Context context = this.mContext;
        String opPackageName = context != null ? context.getOpPackageName() : "<unknown>";
        Context context2 = this.mContext;
        String attributionTag = context2 != null ? context2.getAttributionTag() : null;
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isSubscriptionAssociatedWithCallingUser(i, opPackageName, attributionTag);
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public List<SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(UserHandle userHandle) {
        ISub subscriptionService;
        try {
            subscriptionService = TelephonyManager.getSubscriptionService();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        if (subscriptionService != null) {
            return subscriptionService.getSubscriptionInfoListAssociatedWithUser(userHandle);
        }
        Log.e(LOG_TAG, "[getSubscriptionInfoListAssociatedWithUser]: subscription service unavailable");
        return new ArrayList();
    }

    public static int getAllServiceCapabilityBitmasks() {
        return SERVICE_CAPABILITY_VOICE_BITMASK | SERVICE_CAPABILITY_SMS_BITMASK | SERVICE_CAPABILITY_DATA_BITMASK;
    }

    public static Set<Integer> getServiceCapabilitiesSet(int i) {
        HashSet hashSet = new HashSet();
        for (int i2 = 1; i2 <= 3; i2++) {
            int serviceCapabilityToBitmask = serviceCapabilityToBitmask(i2);
            if ((i & serviceCapabilityToBitmask) == serviceCapabilityToBitmask) {
                hashSet.add(Integer.valueOf(i2));
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    @SystemApi
    public void setTransferStatus(int i, int i2) {
        try {
            ISub subscriptionService = TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setTransferStatus(i, i2);
            }
        } catch (RemoteException e) {
            logd("setTransferStatus for subId = " + i + " failed.");
            throw e.rethrowFromSystemServer();
        }
    }
}
