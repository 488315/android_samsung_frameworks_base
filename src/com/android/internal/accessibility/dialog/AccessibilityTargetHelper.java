package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.R;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.os.RoSystemProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public final class AccessibilityTargetHelper {
    private AccessibilityTargetHelper() {
    }

    public static List<AccessibilityTarget> getTargets(Context context, int i) {
        List<AccessibilityTarget> installedTargets = getInstalledTargets(context, i);
        List<String> accessibilityShortcutTargets = ((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE)).getAccessibilityShortcutTargets(i);
        ArrayList arrayList = new ArrayList();
        for (String str : accessibilityShortcutTargets) {
            for (AccessibilityTarget accessibilityTarget : installedTargets) {
                if (!"com.android.server.accessibility.MagnificationController".contentEquals(str) && ComponentName.unflattenFromString(str).equals(ComponentName.unflattenFromString(accessibilityTarget.getId()))) {
                    arrayList.add(accessibilityTarget);
                } else if (str.contentEquals(accessibilityTarget.getId())) {
                    arrayList.add(accessibilityTarget);
                }
            }
        }
        return arrayList;
    }

    public static List<AccessibilityTarget> getInstalledTargets(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getAccessibilityServiceTargets(context, i));
        arrayList.addAll(getAccessibilityActivityTargets(context, i));
        arrayList.addAll(getAllowListingFeatureTargets(context, i));
        return arrayList;
    }

    private static List<AccessibilityTarget> getAccessibilityServiceTargets(Context context, int i) {
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = ((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE)).getInstalledAccessibilityServiceList();
        if (installedAccessibilityServiceList == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(installedAccessibilityServiceList.size());
        for (AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
            if (isValidServiceTarget(accessibilityServiceInfo, i)) {
                arrayList.add(createAccessibilityServiceTarget(context, i, accessibilityServiceInfo));
            }
        }
        return arrayList;
    }

    public static boolean isValidServiceTarget(AccessibilityServiceInfo accessibilityServiceInfo, int i) {
        return accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion > 29 || ((accessibilityServiceInfo.flags & 256) != 0) || i != 1;
    }

    private static List<AccessibilityTarget> getAccessibilityActivityTargets(Context context, int i) {
        List<AccessibilityShortcutInfo> installedAccessibilityShortcutListAsUser = ((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE)).getInstalledAccessibilityShortcutListAsUser(context, ActivityManager.getCurrentUser());
        if (installedAccessibilityShortcutListAsUser == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(installedAccessibilityShortcutListAsUser.size());
        Iterator<AccessibilityShortcutInfo> it = installedAccessibilityShortcutListAsUser.iterator();
        while (it.hasNext()) {
            arrayList.add(new AccessibilityActivityTarget(context, i, it.next()));
        }
        return arrayList;
    }

    private static List<AccessibilityTarget> getAllowListingFeatureTargets(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = context.getApplicationInfo().uid;
        arrayList.add(new InvisibleToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, "com.android.server.accessibility.MagnificationController"), "com.android.server.accessibility.MagnificationController", i2, context.getString(R.string.accessibility_magnification_chooser_text), context.getDrawable(R.drawable.ic_accessibility_magnification), Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_NAVBAR_ENABLED));
        arrayList.add(new ToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString(), i2, context.getString(R.string.color_correction_feature_name), context.getDrawable(R.drawable.ic_accessibility_color_correction), Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED));
        arrayList.add(new ToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString(), i2, context.getString(R.string.color_inversion_feature_name), context.getDrawable(R.drawable.ic_accessibility_color_inversion), Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED));
        arrayList.add(new ToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, AccessibilityShortcutController.AUTOCLICK_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.AUTOCLICK_COMPONENT_NAME.flattenToString(), i2, context.getString(R.string.autoclick_feature_name), context.getDrawable(R.drawable.ic_accessibility_autoclick), Settings.Secure.ACCESSIBILITY_AUTOCLICK_ENABLED));
        if (RoSystemProperties.SUPPORT_ONE_HANDED_MODE) {
            arrayList.add(new ToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.flattenToString(), i2, context.getString(R.string.one_handed_mode_feature_name), context.getDrawable(R.drawable.ic_accessibility_one_handed), Settings.Secure.ONE_HANDED_MODE_ACTIVATED));
        }
        arrayList.add(new ToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString(), i2, context.getString(R.string.reduce_bright_colors_feature_name), context.getDrawable(R.drawable.ic_accessibility_reduce_bright_colors), Settings.Secure.REDUCE_BRIGHT_COLORS_ACTIVATED));
        arrayList.add(new InvisibleToggleAllowListingFeatureTarget(context, i, ShortcutUtils.isShortcutContained(context, i, AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString(), i2, context.getString(R.string.hearing_aids_feature_name), context.getDrawable(R.drawable.ic_accessibility_hearing_aid), null));
        return arrayList;
    }

    private static AccessibilityTarget createAccessibilityServiceTarget(Context context, int i, AccessibilityServiceInfo accessibilityServiceInfo) {
        int accessibilityServiceFragmentType = AccessibilityUtils.getAccessibilityServiceFragmentType(accessibilityServiceInfo);
        if (accessibilityServiceFragmentType == 0) {
            return new VolumeShortcutToggleAccessibilityServiceTarget(context, i, accessibilityServiceInfo);
        }
        if (accessibilityServiceFragmentType == 1) {
            return new InvisibleToggleAccessibilityServiceTarget(context, i, accessibilityServiceInfo);
        }
        if (accessibilityServiceFragmentType == 2) {
            return new ToggleAccessibilityServiceTarget(context, i, accessibilityServiceInfo);
        }
        throw new IllegalStateException("Unexpected fragment type");
    }

    public static boolean isAccessibilityTargetAllowed(Context context, String str, int i) {
        return ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).isAccessibilityTargetAllowed(str, i, UserHandle.myUserId());
    }

    public static boolean sendRestrictedDialogIntent(Context context, String str, int i) {
        return ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).sendRestrictedDialogIntent(str, i, UserHandle.myUserId());
    }
}
