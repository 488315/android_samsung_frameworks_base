package com.android.server.pm;

import android.app.AppGlobals;
import android.app.admin.flags.Flags;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BundleUtils;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import com.google.android.collect.Sets;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UserRestrictionsUtils {
    public static final Set DEFAULT_ENABLED_FOR_MANAGED_PROFILES;
    public static final Set DEPRECATED_USER_RESTRICTIONS;
    public static final Set DEVICE_OWNER_ONLY_RESTRICTIONS;
    public static final Set FINANCED_DEVICE_OWNER_RESTRICTIONS;
    public static final Set GLOBAL_RESTRICTIONS;
    public static final Set IMMUTABLE_BY_OWNERS;
    public static final Set MAIN_USER_ONLY_RESTRICTIONS;
    public static final Set NON_PERSIST_USER_RESTRICTIONS;
    public static final Set PROFILE_GLOBAL_RESTRICTIONS;
    public static final Set PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_GLOBAL_RESTRICTIONS;
    public static final Set PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_LOCAL_RESTRICTIONS;
    public static final Set PROFILE_OWNER_ORGANIZATION_OWNED_PROFILE_RESTRICTIONS;
    public static final Set USER_RESTRICTIONS;

    static {
        ArraySet newArraySet =
                Sets.newArraySet(
                        new String[] {
                            "no_config_wifi",
                            "no_config_locale",
                            "no_modify_accounts",
                            "no_install_apps",
                            "no_uninstall_apps",
                            "no_share_location",
                            "no_install_unknown_sources",
                            "no_install_unknown_sources_globally",
                            "no_config_bluetooth",
                            "no_bluetooth",
                            "no_bluetooth_sharing",
                            "no_usb_file_transfer",
                            "no_config_credentials",
                            "no_remove_user",
                            "no_remove_managed_profile",
                            "no_debugging_features",
                            "no_config_vpn",
                            "no_config_date_time",
                            "no_config_tethering",
                            "no_network_reset",
                            "no_factory_reset",
                            "no_add_user",
                            "no_add_managed_profile",
                            "no_add_clone_profile",
                            "no_add_private_profile",
                            "ensure_verify_apps",
                            "no_config_cell_broadcasts",
                            "no_config_mobile_networks",
                            "no_control_apps",
                            "no_physical_media",
                            "no_unmute_microphone",
                            "no_adjust_volume",
                            "no_outgoing_calls",
                            "no_sms",
                            "no_fun",
                            "no_create_windows",
                            "no_system_error_dialogs",
                            "no_cross_profile_copy_paste",
                            "no_outgoing_beam",
                            "no_wallpaper",
                            "no_safe_boot",
                            "allow_parent_profile_app_linking",
                            "no_record_audio",
                            "no_camera",
                            "no_run_in_background",
                            "no_data_roaming",
                            "no_set_user_icon",
                            "no_set_wallpaper",
                            "no_oem_unlock",
                            "disallow_unmute_device",
                            "no_autofill",
                            "no_content_capture",
                            "no_content_suggestions",
                            "no_user_switch",
                            "no_unified_password",
                            "no_config_location",
                            "no_airplane_mode",
                            "no_config_brightness",
                            "no_sharing_into_profile",
                            "no_ambient_display",
                            "no_config_screen_timeout",
                            "no_printing",
                            "disallow_config_private_dns",
                            "disallow_microphone_toggle",
                            "no_non_market_app_by_knox",
                            "disallow_camera_toggle",
                            "no_change_wifi_state",
                            "no_wifi_tethering",
                            "no_grant_admin",
                            "no_sharing_admin_configured_wifi",
                            "no_wifi_direct",
                            "no_add_wifi_config",
                            "no_cellular_2g",
                            "no_ultra_wideband_radio",
                            "disallow_config_default_apps",
                            "no_near_field_communication_radio",
                            "no_sim_globally",
                            "no_assist_content",
                            "no_thread_network",
                            "no_change_near_field_communication_radio"
                        });
        Preconditions.checkState(newArraySet.size() == 80);
        USER_RESTRICTIONS = newArraySet;
        DEPRECATED_USER_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {"no_add_managed_profile", "no_remove_managed_profile"});
        NON_PERSIST_USER_RESTRICTIONS = Sets.newArraySet(new String[] {"no_record_audio"});
        MAIN_USER_ONLY_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "no_bluetooth",
                            "no_usb_file_transfer",
                            "no_config_tethering",
                            "no_network_reset",
                            "no_factory_reset",
                            "no_add_user",
                            "no_config_cell_broadcasts",
                            "no_config_mobile_networks",
                            "no_physical_media",
                            "no_sms",
                            "no_fun",
                            "no_safe_boot",
                            "no_create_windows",
                            "no_data_roaming",
                            "no_airplane_mode"
                        });
        DEVICE_OWNER_ONLY_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "no_user_switch",
                            "disallow_config_private_dns",
                            "disallow_microphone_toggle",
                            "disallow_camera_toggle",
                            "no_change_wifi_state",
                            "no_wifi_tethering",
                            "no_wifi_direct",
                            "no_add_wifi_config",
                            "no_cellular_2g",
                            "no_ultra_wideband_radio",
                            "no_near_field_communication_radio",
                            "no_thread_network",
                            "no_change_near_field_communication_radio"
                        });
        IMMUTABLE_BY_OWNERS =
                Sets.newArraySet(new String[] {"no_record_audio", "no_wallpaper", "no_oem_unlock"});
        GLOBAL_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "no_adjust_volume",
                            "no_bluetooth_sharing",
                            "no_config_date_time",
                            "no_system_error_dialogs",
                            "no_run_in_background",
                            "no_unmute_microphone",
                            "disallow_unmute_device",
                            "no_camera",
                            "no_assist_content",
                            "disallow_config_default_apps"
                        });
        PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_GLOBAL_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "no_airplane_mode",
                            "no_config_date_time",
                            "disallow_config_private_dns",
                            "no_change_wifi_state",
                            "no_debugging_features",
                            "no_wifi_tethering",
                            "no_wifi_direct",
                            "no_add_wifi_config",
                            "no_cellular_2g",
                            "no_ultra_wideband_radio",
                            "no_near_field_communication_radio",
                            "no_thread_network",
                            "no_change_near_field_communication_radio"
                        });
        PROFILE_OWNER_ORGANIZATION_OWNED_PROFILE_RESTRICTIONS =
                Sets.newArraySet(new String[] {"no_sim_globally"});
        PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_LOCAL_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "no_config_bluetooth",
                            "no_config_location",
                            "no_config_wifi",
                            "no_content_capture",
                            "no_content_suggestions",
                            "no_debugging_features",
                            "no_share_location",
                            "no_outgoing_calls",
                            "no_camera",
                            "no_bluetooth",
                            "no_bluetooth_sharing",
                            "no_config_cell_broadcasts",
                            "no_config_mobile_networks",
                            "no_config_tethering",
                            "no_data_roaming",
                            "no_safe_boot",
                            "no_sms",
                            "no_usb_file_transfer",
                            "no_physical_media",
                            "no_unmute_microphone",
                            "disallow_config_default_apps",
                            "no_add_private_profile",
                            "no_config_brightness",
                            "no_config_screen_timeout"
                        });
        DEFAULT_ENABLED_FOR_MANAGED_PROFILES =
                Sets.newArraySet(new String[] {"no_bluetooth_sharing"});
        PROFILE_GLOBAL_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "ensure_verify_apps",
                            "no_airplane_mode",
                            "no_install_unknown_sources_globally",
                            "no_sim_globally"
                        });
        FINANCED_DEVICE_OWNER_RESTRICTIONS =
                Sets.newArraySet(
                        new String[] {
                            "no_add_user",
                            "no_debugging_features",
                            "no_install_unknown_sources",
                            "no_safe_boot",
                            "no_config_date_time",
                            "no_outgoing_calls"
                        });
    }

    public static boolean areEqual(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (BundleUtils.isEmpty(bundle)) {
            return BundleUtils.isEmpty(bundle2);
        }
        if (BundleUtils.isEmpty(bundle2)) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (bundle.getBoolean(str) != bundle2.getBoolean(str)) {
                return false;
            }
        }
        for (String str2 : bundle2.keySet()) {
            if (bundle.getBoolean(str2) != bundle2.getBoolean(str2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean canProfileOwnerChange(String str, boolean z, boolean z2) {
        if (!Flags.esimManagementEnabled()) {
            return (((ArraySet) IMMUTABLE_BY_OWNERS).contains(str)
                            || ((ArraySet) DEVICE_OWNER_ONLY_RESTRICTIONS).contains(str)
                            || (!z && ((ArraySet) MAIN_USER_ONLY_RESTRICTIONS).contains(str)))
                    ? false
                    : true;
        }
        if (((ArraySet) IMMUTABLE_BY_OWNERS).contains(str)
                || ((ArraySet) DEVICE_OWNER_ONLY_RESTRICTIONS).contains(str)) {
            return false;
        }
        if (z || !((ArraySet) MAIN_USER_ONLY_RESTRICTIONS).contains(str)) {
            return z2
                    || !((ArraySet) PROFILE_OWNER_ORGANIZATION_OWNED_PROFILE_RESTRICTIONS)
                            .contains(str);
        }
        return false;
    }

    public static void dumpRestrictions(PrintWriter printWriter, String str, Bundle bundle) {
        if (bundle == null) {
            printWriter.println(str + "null");
            return;
        }
        boolean z = true;
        for (String str2 : bundle.keySet()) {
            if (bundle.getBoolean(str2, false)) {
                printWriter.println(str + str2);
                z = false;
            }
        }
        if (z) {
            printWriter.println(str + "none");
        }
    }

    public static boolean isGlobal(int i, String str) {
        return (i == 0
                        && (((ArraySet) MAIN_USER_ONLY_RESTRICTIONS).contains(str)
                                || ((ArraySet) GLOBAL_RESTRICTIONS).contains(str)))
                || (i == 2
                        && ((ArraySet) PROFILE_OWNER_ORGANIZATION_OWNED_PARENT_GLOBAL_RESTRICTIONS)
                                .contains(str))
                || ((ArraySet) PROFILE_GLOBAL_RESTRICTIONS).contains(str)
                || ((ArraySet) DEVICE_OWNER_ONLY_RESTRICTIONS).contains(str);
    }

    public static boolean isValidRestriction(String str) {
        String[] strArr;
        if (((ArraySet) USER_RESTRICTIONS).contains(str)) {
            return true;
        }
        int callingUid = Binder.getCallingUid();
        try {
            strArr = AppGlobals.getPackageManager().getPackagesForUid(callingUid);
        } catch (RemoteException unused) {
            strArr = null;
        }
        StringBuilder sb = new StringBuilder("Unknown restriction queried by uid ");
        sb.append(callingUid);
        if (strArr != null && strArr.length > 0) {
            sb.append(" (");
            sb.append(strArr[0]);
            if (strArr.length > 1) {
                sb.append(" et al");
            }
            sb.append(")");
        }
        sb.append(": ");
        sb.append(str);
        if (str != null) {
            if (!UserHandle.isCore(callingUid)) {
                if (strArr != null) {
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    for (String str2 : strArr) {
                        try {
                            ApplicationInfo applicationInfo =
                                    packageManager.getApplicationInfo(
                                            str2, 794624L, UserHandle.getUserId(callingUid));
                            if (applicationInfo != null && applicationInfo.isSystemApp()) {}
                        } catch (RemoteException unused2) {
                        }
                    }
                }
            }
            Slog.wtf("UserRestrictionsUtils", sb.toString());
            return false;
        }
        Slog.e("UserRestrictionsUtils", sb.toString());
        return false;
    }

    public static void merge(Bundle bundle, Bundle bundle2) {
        Objects.requireNonNull(bundle);
        Preconditions.checkArgument(bundle != bundle2);
        if (bundle2 == null) {
            return;
        }
        for (String str : bundle2.keySet()) {
            if (bundle2.getBoolean(str, false)) {
                bundle.putBoolean(str, true);
            }
        }
    }

    public static Bundle readRestrictions(TypedXmlPullParser typedXmlPullParser) {
        Bundle bundle = new Bundle();
        readRestrictions(typedXmlPullParser, bundle);
        return bundle;
    }

    public static void readRestrictions(TypedXmlPullParser typedXmlPullParser, Bundle bundle) {
        bundle.clear();
        Iterator it = ((ArraySet) USER_RESTRICTIONS).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (typedXmlPullParser.getAttributeBoolean((String) null, str, false)) {
                bundle.putBoolean(str, true);
            }
        }
    }

    public static boolean restrictionsChanged(Bundle bundle, Bundle bundle2, String... strArr) {
        if (strArr.length == 0) {
            return areEqual(bundle, bundle2);
        }
        for (String str : strArr) {
            if (bundle.getBoolean(str, false) != bundle2.getBoolean(str, false)) {
                return true;
            }
        }
        return false;
    }

    public static void writeRestrictions(
            TypedXmlSerializer typedXmlSerializer, Bundle bundle, String str) {
        if (bundle == null) {
            return;
        }
        typedXmlSerializer.startTag((String) null, str);
        for (String str2 : bundle.keySet()) {
            if (!((ArraySet) NON_PERSIST_USER_RESTRICTIONS).contains(str2)) {
                if (!((ArraySet) USER_RESTRICTIONS).contains(str2)) {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            "Unknown user restriction detected: ", str2, "UserRestrictionsUtils");
                } else if (bundle.getBoolean(str2)) {
                    typedXmlSerializer.attributeBoolean((String) null, str2, true);
                }
            }
        }
        typedXmlSerializer.endTag((String) null, str);
    }
}
