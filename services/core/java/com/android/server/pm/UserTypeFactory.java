package com.android.server.pm;

import android.R;
import android.content.pm.UserProperties;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.ddar.ta.TACommandRequest;
import com.android.server.pm.UserTypeDetails;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UserTypeFactory {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserTypeUpgrade {
        public final String mFromType;
        public final String mToType;
        public final int mUpToVersion;

        public UserTypeUpgrade(int i, String str, String str2) {
            this.mFromType = str;
            this.mToType = str2;
            this.mUpToVersion = i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00e0, code lost:
    
        r6 = 0;
        setIntAttribute(r9, "max-allowed-per-parent", new com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0(r3, r6));
        r6 = 1;
        setResAttribute(r9, "icon-badge", new com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0(r3, r6));
        r6 = 2;
        setResAttribute(r9, "badge-plain", new com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0(r3, r6));
        r6 = 3;
        setResAttribute(r9, "badge-no-background", new com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0(r3, r6));
        r6 = 4;
        setResAttribute(r9, "status-bar-icon", new com.android.server.pm.UserTypeFactory$$ExternalSyntheticLambda0(r3, r6));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void customizeBuilders(android.util.ArrayMap r8, android.content.res.XmlResourceParser r9) {
        /*
            Method dump skipped, instructions count: 577
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.UserTypeFactory.customizeBuilders(android.util.ArrayMap, android.content.res.XmlResourceParser):void");
    }

    public static ArrayMap getDefaultBuilders() {
        ArrayMap arrayMap = new ArrayMap();
        UserTypeDetails.Builder builder = new UserTypeDetails.Builder();
        builder.mName = "android.os.usertype.profile.MANAGED";
        builder.mBaseType = 4096;
        builder.mDefaultUserInfoPropertyFlags = 32;
        builder.mMaxAllowedPerParent = 1;
        builder.mLabels = new int[]{17042500, 17042501, 17042502};
        builder.mIconBadge = R.drawable.ic_expand_more;
        builder.mBadgePlain = R.drawable.ic_drag_handle;
        builder.mBadgeNoBackground = R.drawable.ic_eject_24dp;
        builder.mStatusBarIcon = 17304492;
        builder.mBadgeLabels = new int[]{R.string.permlab_deliverCompanionMessages, R.string.permlab_detectScreenCapture, R.string.permlab_disableKeyguard};
        builder.mBadgeColors = new int[]{R.color.system_brand_b_light, R.color.system_brand_c_light, R.color.system_brand_d_light};
        builder.mDarkThemeBadgeColors = new int[]{R.color.system_brand_c_dark, R.color.system_brand_d_dark, R.color.system_clock_hour_dark};
        builder.mAccessibilityString = R.string.app_category_game;
        Bundle bundle = new Bundle();
        bundle.putBoolean("no_wallpaper", true);
        builder.mDefaultRestrictions = bundle;
        Bundle bundle2 = new Bundle();
        bundle2.putString("managed_profile_contact_remote_search", "1");
        bundle2.putString("cross_profile_calendar_enabled", "1");
        builder.mDefaultSecureSettings = bundle2;
        DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter = DefaultCrossProfileIntentFiltersUtils.EMERGENCY_CALL_MIME;
        ArrayList arrayList = new ArrayList();
        DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter2 = DefaultCrossProfileIntentFiltersUtils.HOME;
        DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter3 = DefaultCrossProfileIntentFiltersUtils.MOBILE_NETWORK_SETTINGS;
        DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter4 = DefaultCrossProfileIntentFiltersUtils.EMERGENCY_CALL_MIME;
        DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter5 = DefaultCrossProfileIntentFiltersUtils.EMERGENCY_CALL_DATA;
        arrayList.addAll(Arrays.asList(defaultCrossProfileIntentFilter4, defaultCrossProfileIntentFilter5, DefaultCrossProfileIntentFiltersUtils.CALL_BUTTON, DefaultCrossProfileIntentFiltersUtils.SET_ALARM, DefaultCrossProfileIntentFiltersUtils.MEDIA_CAPTURE, DefaultCrossProfileIntentFiltersUtils.RECOGNIZE_SPEECH, DefaultCrossProfileIntentFiltersUtils.ACTION_PICK_RAW, DefaultCrossProfileIntentFiltersUtils.ACTION_PICK_DATA, DefaultCrossProfileIntentFiltersUtils.ACTION_PICK_IMAGES, DefaultCrossProfileIntentFiltersUtils.ACTION_PICK_IMAGES_WITH_DATA_TYPES, DefaultCrossProfileIntentFiltersUtils.OPEN_DOCUMENT, DefaultCrossProfileIntentFiltersUtils.GET_CONTENT, DefaultCrossProfileIntentFiltersUtils.USB_DEVICE_ATTACHED, DefaultCrossProfileIntentFiltersUtils.ACTION_SEND, defaultCrossProfileIntentFilter2, defaultCrossProfileIntentFilter3));
        arrayList.addAll(DefaultCrossProfileIntentFiltersUtils.getDefaultCrossProfileTelephonyIntentFilters(false));
        builder.mDefaultCrossProfileIntentFilters = arrayList;
        builder.mDefaultUserProperties = new UserProperties.Builder().setStartWithParent(true).setShowInLauncher(1).setShowInSettings(1).setShowInQuietMode(0).setShowInSharingSurfaces(1).setAuthAlwaysRequiredToDisableQuietMode(false).setCredentialShareableWithParent(true).build();
        arrayMap.put("android.os.usertype.profile.MANAGED", builder);
        UserTypeDetails.Builder builder2 = new UserTypeDetails.Builder();
        builder2.mName = "android.os.usertype.full.SYSTEM";
        builder2.mBaseType = TACommandRequest.MAX_DATA_TRANSACTION_SIZE;
        builder2.mDefaultUserInfoPropertyFlags = 16387;
        builder2.mMaxAllowed = 1;
        arrayMap.put("android.os.usertype.full.SYSTEM", builder2);
        UserTypeDetails.Builder builder3 = new UserTypeDetails.Builder();
        builder3.mName = "android.os.usertype.full.SECONDARY";
        builder3.mBaseType = 1024;
        builder3.mMaxAllowed = -1;
        Bundle bundle3 = new Bundle();
        bundle3.putBoolean("no_outgoing_calls", true);
        bundle3.putBoolean("no_sms", true);
        builder3.mDefaultRestrictions = bundle3;
        arrayMap.put("android.os.usertype.full.SECONDARY", builder3);
        int i = Resources.getSystem().getBoolean(R.bool.config_intrusiveNotificationLed) ? 256 : 0;
        UserTypeDetails.Builder builder4 = new UserTypeDetails.Builder();
        builder4.mName = "android.os.usertype.full.GUEST";
        builder4.mBaseType = 1024;
        builder4.mDefaultUserInfoPropertyFlags = i | 4;
        builder4.mMaxAllowed = 1;
        Bundle bundle4 = new Bundle();
        bundle4.putBoolean("no_outgoing_calls", true);
        bundle4.putBoolean("no_sms", true);
        bundle4.putBoolean("no_config_wifi", true);
        bundle4.putBoolean("no_install_unknown_sources", true);
        bundle4.putBoolean("no_config_credentials", true);
        builder4.mDefaultRestrictions = bundle4;
        arrayMap.put("android.os.usertype.full.GUEST", builder4);
        UserTypeDetails.Builder builder5 = new UserTypeDetails.Builder();
        builder5.mName = "android.os.usertype.full.DEMO";
        builder5.mBaseType = 1024;
        builder5.mDefaultUserInfoPropertyFlags = 512;
        builder5.mMaxAllowed = -1;
        builder5.mDefaultRestrictions = null;
        arrayMap.put("android.os.usertype.full.DEMO", builder5);
        UserTypeDetails.Builder builder6 = new UserTypeDetails.Builder();
        builder6.mName = "android.os.usertype.full.RESTRICTED";
        builder6.mBaseType = 1024;
        builder6.mDefaultUserInfoPropertyFlags = 8;
        builder6.mMaxAllowed = -1;
        builder6.mDefaultRestrictions = null;
        arrayMap.put("android.os.usertype.full.RESTRICTED", builder6);
        UserTypeDetails.Builder builder7 = new UserTypeDetails.Builder();
        builder7.mName = "android.os.usertype.system.HEADLESS";
        builder7.mBaseType = 2048;
        builder7.mDefaultUserInfoPropertyFlags = 3;
        builder7.mMaxAllowed = 1;
        arrayMap.put("android.os.usertype.system.HEADLESS", builder7);
        UserTypeDetails.Builder builder8 = new UserTypeDetails.Builder();
        builder8.mName = "android.os.usertype.profile.CLONE";
        builder8.mBaseType = 4096;
        builder8.mMaxAllowedPerParent = 1;
        builder8.mLabels = new int[]{17042496};
        builder8.mStatusBarIcon = 0;
        builder8.mAccessibilityString = R.string.app_category_accessibility;
        builder8.mDefaultRestrictions = null;
        builder8.mDefaultCrossProfileIntentFilters = Arrays.asList(DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_SEND_ACTION, DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_WEB_VIEW_ACTION, DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_PICK_INSERT_ACTION, DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_DIAL_DATA, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_MEDIA_CAPTURE, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_SEND_ACTION, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_WEB_VIEW_ACTION, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_VIEW_ACTION, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_PICK_INSERT_ACTION, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_DIAL_DATA, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_SMS_MMS, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_PHOTOPICKER_SELECTION, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_ACTION_PICK_IMAGES, DefaultCrossProfileIntentFiltersUtils.CLONE_TO_PARENT_ACTION_PICK_IMAGES_WITH_DATA_TYPES, DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_NFC_TAG_DISCOVERED, DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_NFC_TECH_DISCOVERED, DefaultCrossProfileIntentFiltersUtils.PARENT_TO_CLONE_NFC_NDEF_DISCOVERED);
        builder8.mDefaultSecureSettings = AccountManagerService$$ExternalSyntheticOutline0.m142m("user_setup_complete", "1");
        builder8.mIconBadge = R.drawable.ic_input_extract_action_search;
        builder8.mBadgePlain = R.drawable.ic_input_extract_action_search;
        builder8.mBadgeNoBackground = R.drawable.ab_bottom_solid_inverse_holo;
        builder8.mBadgeLabels = new int[]{R.string.permlab_deliverCompanionMessages};
        builder8.mBadgeColors = new int[]{R.color.datepicker_default_selected_text_color_material_dark};
        builder8.mDarkThemeBadgeColors = new int[]{R.color.datepicker_default_selected_text_color_material_dark};
        builder8.mDefaultUserProperties = new UserProperties.Builder().setStartWithParent(true).setShowInLauncher(0).setShowInSettings(0).setInheritDevicePolicy(1).setUseParentsContacts(true).setUpdateCrossProfileIntentFiltersOnOTA(true).setCrossProfileIntentFilterAccessControl(10).setCrossProfileIntentResolutionStrategy(1).setShowInQuietMode(2).setShowInSharingSurfaces(0).setMediaSharedWithParent(true).setCredentialShareableWithParent(true).setDeleteAppWithParent(true).setCrossProfileContentSharingStrategy(1).build();
        arrayMap.put("android.os.usertype.profile.CLONE", builder8);
        UserTypeDetails.Builder builder9 = new UserTypeDetails.Builder();
        builder9.mName = "android.os.usertype.profile.COMMUNAL";
        builder9.mBaseType = 4096;
        builder9.mMaxAllowed = 1;
        builder9.mEnabled = UserManager.isCommunalProfileEnabled() ? 1 : 0;
        builder9.mLabels = new int[]{17042497};
        builder9.mIconBadge = R.drawable.jog_dial_arrow_short_left_and_right;
        builder9.mBadgePlain = R.drawable.jog_dial_arrow_long_right_yellow;
        builder9.mBadgeNoBackground = R.drawable.jog_dial_arrow_short_left;
        builder9.mStatusBarIcon = R.drawable.jog_dial_arrow_long_right_yellow;
        builder9.mBadgeLabels = new int[]{R.string.permlab_deliverCompanionMessages, R.string.permlab_detectScreenCapture, R.string.permlab_disableKeyguard};
        builder9.mBadgeColors = new int[]{R.color.system_brand_b_light, R.color.system_brand_c_light, R.color.system_brand_d_light};
        builder9.mDarkThemeBadgeColors = new int[]{R.color.system_brand_c_dark, R.color.system_brand_d_dark, R.color.system_clock_hour_dark};
        Bundle bundle5 = new Bundle();
        bundle5.putBoolean("no_wallpaper", true);
        builder9.mDefaultRestrictions = bundle5;
        builder9.mDefaultSecureSettings = AccountManagerService$$ExternalSyntheticOutline0.m142m("user_setup_complete", "1");
        builder9.mDefaultUserProperties = new UserProperties.Builder().setStartWithParent(false).setShowInLauncher(1).setShowInSettings(1).setCredentialShareableWithParent(false).setAlwaysVisible(true).build();
        arrayMap.put("android.os.usertype.profile.COMMUNAL", builder9);
        UserTypeDetails.Builder builder10 = new UserTypeDetails.Builder();
        builder10.mName = "android.os.usertype.profile.PRIVATE";
        builder10.mBaseType = 4096;
        builder10.mMaxAllowedPerParent = 1;
        builder10.mEnabled = UserManager.isPrivateProfileEnabled() ? 1 : 0;
        builder10.mLabels = new int[]{17042498};
        builder10.mIconBadge = R.drawable.ic_slice_send;
        builder10.mBadgePlain = R.drawable.ic_sim_card_multi_48px_clr;
        builder10.mBadgeNoBackground = R.drawable.ic_sim_card_multi_48px_clr;
        builder10.mStatusBarIcon = 17304493;
        builder10.mBadgeLabels = new int[]{17042482};
        builder10.mBadgeColors = new int[]{R.color.black};
        builder10.mDarkThemeBadgeColors = new int[]{R.color.white};
        builder10.mAccessibilityString = R.string.app_category_image;
        builder10.mDefaultRestrictions = getDefaultPrivateProfileRestrictions();
        builder10.mDefaultCrossProfileIntentFilters = Arrays.asList(DefaultCrossProfileIntentFiltersUtils.DIAL_MIME_PRIVATE_PROFILE, DefaultCrossProfileIntentFiltersUtils.DIAL_DATA_PRIVATE_PROFILE, DefaultCrossProfileIntentFiltersUtils.DIAL_RAW_PRIVATE_PROFILE, DefaultCrossProfileIntentFiltersUtils.CALL_PRIVATE_PROFILE, DefaultCrossProfileIntentFiltersUtils.CALL_BUTTON_PRIVATE_PROFILE, defaultCrossProfileIntentFilter5, defaultCrossProfileIntentFilter4, DefaultCrossProfileIntentFiltersUtils.SMS_MMS_PRIVATE_PROFILE);
        builder10.mDefaultUserProperties = new UserProperties.Builder().setStartWithParent(true).setCredentialShareableWithParent(true).setAuthAlwaysRequiredToDisableQuietMode(true).setAllowStoppingUserWithDelayedLocking(true).setMediaSharedWithParent(false).setShowInLauncher(1).setShowInSettings(1).setShowInQuietMode(1).setShowInSharingSurfaces(1).setCrossProfileIntentFilterAccessControl(10).setInheritDevicePolicy(1).setCrossProfileContentSharingStrategy(1).setProfileApiVisibility(1).setItemsRestrictedOnHomeScreen(true).setUpdateCrossProfileIntentFiltersOnOTA(true).build();
        arrayMap.put("android.os.usertype.profile.PRIVATE", builder10);
        File file = MaintenanceModeManager.LOG_DIR;
        UserTypeDetails.Builder builder11 = new UserTypeDetails.Builder();
        builder11.mName = "com.samsung.android.os.usertype.full.MAINTENANCE_MODE";
        builder11.mBaseType = 1024;
        builder11.mMaxAllowed = 1;
        Bundle bundle6 = new Bundle();
        bundle6.putBoolean("no_outgoing_calls", false);
        bundle6.putBoolean("no_sms", true);
        builder11.mDefaultRestrictions = bundle6;
        arrayMap.put("com.samsung.android.os.usertype.full.MAINTENANCE_MODE", builder11);
        if (Build.IS_DEBUGGABLE) {
            Bundle bundle7 = new Bundle();
            bundle7.putBoolean("no_wallpaper", true);
            bundle7.putBoolean("no_fun", true);
            UserTypeDetails.Builder builder12 = new UserTypeDetails.Builder();
            builder12.mName = "android.os.usertype.profile.TEST";
            builder12.mBaseType = 4096;
            builder12.mMaxAllowedPerParent = 2;
            builder12.mLabels = new int[]{17042499, 17042499, 17042499};
            builder12.mIconBadge = R.drawable.jog_dial_arrow_short_left_and_right;
            builder12.mBadgePlain = R.drawable.jog_dial_arrow_long_right_yellow;
            builder12.mBadgeNoBackground = R.drawable.jog_dial_arrow_short_left;
            builder12.mStatusBarIcon = R.drawable.jog_dial_arrow_long_right_yellow;
            builder12.mBadgeLabels = new int[]{R.string.permlab_deliverCompanionMessages, R.string.permlab_detectScreenCapture, R.string.permlab_disableKeyguard};
            builder12.mBadgeColors = new int[]{R.color.system_brand_b_light, R.color.system_brand_c_light, R.color.system_brand_d_light};
            builder12.mDarkThemeBadgeColors = new int[]{R.color.system_brand_c_dark, R.color.system_brand_d_dark, R.color.system_clock_hour_dark};
            builder12.mDefaultRestrictions = bundle7;
            builder12.mDefaultSecureSettings = AccountManagerService$$ExternalSyntheticOutline0.m142m("user_setup_complete", "1");
            arrayMap.put("android.os.usertype.profile.TEST", builder12);
        }
        return arrayMap;
    }

    public static Bundle getDefaultPrivateProfileRestrictions() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("no_wallpaper", true);
        bundle.putBoolean("no_bluetooth_sharing", true);
        return bundle;
    }

    public static int getUserTypeVersion() {
        XmlResourceParser xml = Resources.getSystem().getXml(R.xml.global_keys);
        try {
            int userTypeVersion = getUserTypeVersion(xml);
            if (xml != null) {
                xml.close();
            }
            return userTypeVersion;
        } catch (Throwable th) {
            if (xml != null) {
                try {
                    xml.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static int getUserTypeVersion(XmlResourceParser xmlResourceParser) {
        try {
            XmlUtils.beginDocument(xmlResourceParser, "user-types");
            String attributeValue = xmlResourceParser.getAttributeValue(null, "version");
            if (attributeValue == null) {
                return 0;
            }
            try {
                return Integer.parseInt(attributeValue);
            } catch (NumberFormatException e) {
                Slog.e("UserTypeFactory", "Cannot parse value of '" + attributeValue + "' for version in " + xmlResourceParser.getPositionDescription(), e);
                throw e;
            }
        } catch (IOException | XmlPullParserException e2) {
            Slog.w("UserTypeFactory", "Cannot read user type configuration file.", e2);
            return 0;
        }
    }

    public static List parseUserUpgrades(ArrayMap arrayMap, XmlResourceParser xmlResourceParser) {
        ArrayList arrayList = new ArrayList();
        try {
            XmlUtils.beginDocument(xmlResourceParser, "user-types");
            XmlUtils.nextElement(xmlResourceParser);
            while (xmlResourceParser.getEventType() != 1) {
                if ("change-user-type".equals(xmlResourceParser.getName())) {
                    String attributeValue = xmlResourceParser.getAttributeValue(null, "from");
                    String attributeValue2 = xmlResourceParser.getAttributeValue(null, "to");
                    validateUserTypeIsProfile(arrayMap, attributeValue);
                    validateUserTypeIsProfile(arrayMap, attributeValue2);
                    try {
                        arrayList.add(new UserTypeUpgrade(Integer.parseInt(xmlResourceParser.getAttributeValue(null, "whenVersionLeq")), attributeValue, attributeValue2));
                    } catch (NumberFormatException e) {
                        Slog.e("UserTypeFactory", "Cannot parse value of whenVersionLeq in " + xmlResourceParser.getPositionDescription(), e);
                        throw e;
                    }
                } else {
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
                XmlUtils.nextElement(xmlResourceParser);
            }
        } catch (IOException | XmlPullParserException e2) {
            Slog.w("UserTypeFactory", "Cannot read user type configuration file.", e2);
        }
        return arrayList;
    }

    public static void setIntAttribute(XmlResourceParser xmlResourceParser, String str, Consumer consumer) {
        String attributeValue = xmlResourceParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return;
        }
        try {
            consumer.accept(Integer.valueOf(Integer.parseInt(attributeValue)));
        } catch (NumberFormatException e) {
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("Cannot parse value of '", attributeValue, "' for ", str, " in ");
            m.append(xmlResourceParser.getPositionDescription());
            Slog.e("UserTypeFactory", m.toString(), e);
            throw e;
        }
    }

    public static void setResAttribute(XmlResourceParser xmlResourceParser, String str, Consumer consumer) {
        if (xmlResourceParser.getAttributeValue(null, str) == null) {
            return;
        }
        consumer.accept(Integer.valueOf(xmlResourceParser.getAttributeResourceValue(null, str, 0)));
    }

    public static void setResAttributeArray(XmlResourceParser xmlResourceParser, Consumer consumer) {
        ArrayList arrayList = new ArrayList();
        int depth = xmlResourceParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            String name = xmlResourceParser.getName();
            if ("item".equals(name)) {
                int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, "res", -1);
                if (attributeResourceValue != -1) {
                    arrayList.add(Integer.valueOf(attributeResourceValue));
                }
            } else {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Skipping unknown child element ", name, " in ");
                m.append(xmlResourceParser.getPositionDescription());
                Slog.w("UserTypeFactory", m.toString());
                XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        consumer.accept(iArr);
    }

    public static void validateUserTypeIsProfile(ArrayMap arrayMap, String str) {
        UserTypeDetails.Builder builder = (UserTypeDetails.Builder) arrayMap.get(str);
        if (builder != null && builder.mBaseType != 4096) {
            throw new IllegalArgumentException(XmlUtils$$ExternalSyntheticOutline0.m("Illegal upgrade of user type ", str, " : Can only upgrade profiles user types"));
        }
    }
}
