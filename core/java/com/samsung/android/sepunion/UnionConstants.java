package com.samsung.android.sepunion;

import android.util.ArrayMap;

import com.samsung.android.feature.SemFloatingFeature;

public final class UnionConstants {
    public static final String BUNDLE_KEY_COMPONENT = "component";
    public static final String BUNDLE_KEY_COMPONENT_LIST = "component_list";
    public static final String BUNDLE_KEY_PACKAGE_LIST = "package_list";
    public static final String BUNDLE_KEY_PACKAGE_NAME = "package_name";
    public static final String BUNDLE_KEY_PACKAGE_STATE = "package_state";
    public static final String BUNDLE_KEY_RESUMED = "is_resumed";
    public static final String CUSTOM_EVENT_ACTIVITY_STATE = "monitor_activity_state";
    public static final String CUSTOM_EVENT_CALL_STATE = "monitor_call_state";
    public static final String CUSTOM_EVENT_PACKAGE_STATE = "monitor_package_state";
    public static final String EXTRA_KEY_ACTION_ORIGIN = "action_origin";
    public static final String EXTRA_KEY_CALL_STATE = "call_state";
    public static final String EXTRA_KEY_COMPONENT_NAME = "component";
    public static final String EXTRA_KEY_IS_RESUMED = "is_resumed";
    public static final String EXTRA_KEY_NOTIFY_FOR_DESCENDANTS = "notify_for_descendants";
    public static final String EXTRA_KEY_PACKAGE_NAME = "package_name";
    public static final String EXTRA_KEY_PACKAGE_STATE = "package_state";
    public static final String EXTRA_KEY_PHONE_NUMBER = "phone_number";
    public static final String EXTRA_KEY_URI = "uri";
    public static final int FLAG_CHECK_CONDITION_NONE = 0;
    public static final int FLAG_CHECK_CONDITION_PACKAGE_NAME = 1;
    public static final int FLAG_CHECK_CONDITION_PERMISSION = 2;
    public static final String LOG_TAG_COVER = "CoverManager_";
    public static final int MASK_FLAG_CHECK_CONDITION = 3;
    public static final String PACKAGE_STATE_ADDED = "package_added";
    public static final String PACKAGE_STATE_MODIFIED = "package_modified";
    public static final String PACKAGE_STATE_REMOVED = "package_removed";
    public static final String PATH_LOG_FILE = "/data/log/sepunion/";
    public static final String SERVICE_COVER = "cover";
    public static final String SERVICE_DEVICE_INFO = "semeventdelegator";
    public static final String SERVICE_DUMP = "semcustomdump";
    public static final String SERVICE_ENGMODE = "EngmodeService";
    public static final String SERVICE_EXCLUSIVE_TASK = "exclusivetask";
    public static final String SERVICE_FRIENDS = "friends";
    public static final String SERVICE_FW_BR_RECEIVER_AGENT = "brreceiveragent";
    public static final String SERVICE_GALAXY_REGISTRY = "galaxyregistry";
    public static final String SERVICE_GOOD_CATCH = "goodcatch";
    public static final String SERVICE_HERMES = "HermesService";
    public static final String SERVICE_ONE_HAND = "onehand";
    public static final String SERVICE_PLUGIN = "plugin";
    public static final String SERVICE_QRNG = "QRNGService";
    public static final String SERVICE_SHORTCUT = "execute";
    public static final String SERVICE_SMART_MANAGER = "SmartManagerService";
    public static final String SERVICE_SMMT_OBSERVER = "SMMTObserver";
    public static final int SERVICE_START_AUTO = 0;
    public static final int SERVICE_START_MANUAL = 1;
    public static final String SERVICE_TIPS = "tips";
    public static final String SERVICE_VDC_OBSERVER = "VDCObserver";
    public static final int TYPE_ALL = 0;
    public static final int TYPE_CUSTOM_EVENT = 3;
    public static final int TYPE_INTENT_ACTION = 2;
    public static final int TYPE_URI = 1;
    public static final ArrayMap<String, String> sClassPathForManager = new ArrayMap<>();
    public static final ArrayMap<String, String> sClassPathForService;
    public static final ArrayMap<String, Integer> sServiceStartType;

    static {
        String str;
        String str2;
        String str3;
        sClassPathForManager.put(
                "semeventdelegator", "com.samsung.android.sepunion.SemEventDelegationManager");
        sClassPathForManager.put(SERVICE_PLUGIN, "com.samsung.android.sepunion.SemPluginManager");
        sClassPathForManager.put("execute", "com.samsung.android.app.SemExecutableManager");
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK")) {
            sClassPathForManager.put(
                    SERVICE_EXCLUSIVE_TASK, "com.samsung.android.sepunion.SemExclusiveTaskManager");
        }
        sClassPathForManager.put(
                SERVICE_ONE_HAND, "com.samsung.android.sepunion.OneHandServiceManager");
        sClassPathForManager.put(
                SERVICE_GALAXY_REGISTRY,
                "com.samsung.android.sepunion.GalaxyRegistryServiceManager");
        sClassPathForManager.put(
                SERVICE_FW_BR_RECEIVER_AGENT,
                "com.samsung.android.sepunion.BRReceiverAgentServiceManager");
        sClassPathForManager.put(
                "semcustomdump", "com.samsung.android.sepunion.SemCustomDumpManager");
        sClassPathForManager.put(
                SERVICE_HERMES, "com.samsung.android.service.HermesService.HermesServiceManager");
        sClassPathForManager.put(
                SERVICE_GOOD_CATCH, "com.samsung.android.sepunion.SemGoodCatchManager");
        sClassPathForManager.put(SERVICE_TIPS, "com.samsung.android.sepunion.TipsManager");
        sClassPathForService = new ArrayMap<>();
        sClassPathForService.put(
                "semeventdelegator", "com.android.server.sepunion.SemDeviceInfoManagerService");
        sClassPathForService.put(
                SERVICE_PLUGIN, "com.android.server.sepunion.SemPluginManagerService");
        sClassPathForService.put(
                "execute", "com.android.server.sepunion.SemShortcutManagerService");
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK")) {
            sClassPathForService.put(
                    SERVICE_EXCLUSIVE_TASK,
                    "com.android.server.sepunion.SemExclusiveTaskManagerService");
        }
        sClassPathForService.put(SERVICE_ONE_HAND, "com.android.server.sepunion.OneHandService");
        sClassPathForService.put(
                SERVICE_GALAXY_REGISTRY, "com.android.server.sepunion.GalaxyRegistryService");
        sClassPathForService.put(
                SERVICE_FW_BR_RECEIVER_AGENT, "com.android.server.sepunion.BRReceiverAgentService");
        sClassPathForService.put(SERVICE_HERMES, "com.android.server.HermesService");
        sClassPathForService.put(SERVICE_ENGMODE, "com.android.server.sepunion.EngmodeService");
        sClassPathForService.put(
                SERVICE_GOOD_CATCH, "com.android.server.sepunion.SemGoodCatchService");
        if ("com.samsung.android.sm_cn"
                .equals(
                        SemFloatingFeature.getInstance()
                                .getString(
                                        "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME"))) {
            str = "com.samsung.android.sm_cn";
            sClassPathForService.put(
                    SERVICE_SMART_MANAGER, "com.android.server.sepunion.SmartManagerService");
        } else {
            str = "com.samsung.android.sm_cn";
        }
        if (SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_VDC")
                .contains("VDCOBSERVER")) {
            str2 = "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME";
            sClassPathForService.put(
                    SERVICE_VDC_OBSERVER, "com.android.server.sepunion.VDCObserverService");
        } else {
            str2 = "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME";
        }
        if (!SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_VDC")
                .contains("SMMTOBSERVER")) {
            str3 = SERVICE_VDC_OBSERVER;
        } else {
            ArrayMap<String, String> arrayMap = sClassPathForService;
            str3 = SERVICE_VDC_OBSERVER;
            arrayMap.put(
                    SERVICE_SMMT_OBSERVER,
                    "com.android.server.sepunion.SmartMeetingObserverService");
        }
        sClassPathForService.put(SERVICE_TIPS, "com.android.server.sepunion.TipsManagerService");
        sServiceStartType = new ArrayMap<>();
        sServiceStartType.put("semeventdelegator", 0);
        sServiceStartType.put(SERVICE_PLUGIN, 0);
        sServiceStartType.put("execute", 1);
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK")) {
            sServiceStartType.put(SERVICE_EXCLUSIVE_TASK, 0);
        }
        sServiceStartType.put(SERVICE_ONE_HAND, 0);
        sServiceStartType.put(SERVICE_GALAXY_REGISTRY, 0);
        sServiceStartType.put(SERVICE_FW_BR_RECEIVER_AGENT, 0);
        sServiceStartType.put(SERVICE_HERMES, 0);
        sServiceStartType.put(SERVICE_ENGMODE, 0);
        sServiceStartType.put(SERVICE_GOOD_CATCH, 0);
        if (str.equals(SemFloatingFeature.getInstance().getString(str2))) {
            sServiceStartType.put(SERVICE_SMART_MANAGER, 0);
        }
        if (SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_VDC")
                .contains("VDCOBSERVER")) {
            sServiceStartType.put(str3, 0);
        }
        if (SemFloatingFeature.getInstance()
                .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_VDC")
                .contains("SMMTOBSERVER")) {
            sServiceStartType.put(SERVICE_SMMT_OBSERVER, 0);
        }
        sServiceStartType.put(SERVICE_TIPS, 0);
    }
}
