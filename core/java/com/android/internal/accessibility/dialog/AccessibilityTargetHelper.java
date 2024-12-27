package com.android.internal.accessibility.dialog;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.R;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.os.RoSystemProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public final class AccessibilityTargetHelper {
    private AccessibilityTargetHelper() {
    }

    public static List<AccessibilityTarget> getTargets(Context context, int shortcutType) {
        List<AccessibilityTarget> installedTargets = getInstalledTargets(context, shortcutType);
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<String> assignedTargets = am.getAccessibilityShortcutTargets(shortcutType);
        List<AccessibilityTarget> results = new ArrayList<>();
        for (String assignedTarget : assignedTargets) {
            for (AccessibilityTarget installedTarget : installedTargets) {
                if (!"com.android.server.accessibility.MagnificationController".contentEquals(assignedTarget)) {
                    ComponentName assignedTargetComponentName = ComponentName.unflattenFromString(assignedTarget);
                    ComponentName targetComponentName = ComponentName.unflattenFromString(installedTarget.getId());
                    if (assignedTargetComponentName.equals(targetComponentName)) {
                        results.add(installedTarget);
                    }
                }
                if (assignedTarget.contentEquals(installedTarget.getId())) {
                    results.add(installedTarget);
                }
            }
        }
        return results;
    }

    public static List<AccessibilityTarget> getInstalledTargets(Context context, int shortcutType) {
        List<AccessibilityTarget> targets = new ArrayList<>();
        targets.addAll(getAccessibilityFilteredTargets(context, shortcutType));
        targets.addAll(getAllowListingFeatureTargets(context, shortcutType));
        return targets;
    }

    private static List<AccessibilityTarget> getAccessibilityFilteredTargets(Context context, int shortcutType) {
        List<AccessibilityTarget> serviceTargets = getAccessibilityServiceTargets(context, shortcutType);
        List<AccessibilityTarget> activityTargets = getAccessibilityActivityTargets(context, shortcutType);
        for (final AccessibilityTarget activityTarget : activityTargets) {
            serviceTargets.removeIf(new Predicate() { // from class: com.android.internal.accessibility.dialog.AccessibilityTargetHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean arePackageNameAndLabelTheSame;
                    arePackageNameAndLabelTheSame = AccessibilityTargetHelper.arePackageNameAndLabelTheSame((AccessibilityTarget) obj, AccessibilityTarget.this);
                    return arePackageNameAndLabelTheSame;
                }
            });
        }
        List<AccessibilityTarget> targets = new ArrayList<>();
        targets.addAll(serviceTargets);
        targets.addAll(activityTargets);
        return targets;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean arePackageNameAndLabelTheSame(AccessibilityTarget serviceTarget, AccessibilityTarget activityTarget) {
        try {
            ComponentName serviceComponentName = ComponentName.unflattenFromString(serviceTarget.getId());
            ComponentName activityComponentName = ComponentName.unflattenFromString(activityTarget.getId());
            boolean isSamePackageName = activityComponentName.getPackageName().equals(serviceComponentName.getPackageName());
            boolean isSameLabel = activityTarget.getLabel().equals(serviceTarget.getLabel());
            return isSamePackageName && isSameLabel;
        } catch (NullPointerException e) {
            Log.i("AccessibilityTargetHelper", "NullPointerException occurred : " + e);
            return false;
        }
    }

    private static List<AccessibilityTarget> getAccessibilityServiceTargets(Context context, int shortcutType) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> installedServices = am.getInstalledAccessibilityServiceList();
        if (installedServices == null) {
            return Collections.emptyList();
        }
        List<AccessibilityTarget> targets = new ArrayList<>(installedServices.size());
        try {
            for (AccessibilityServiceInfo info : installedServices) {
                int targetSdk = info.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
                boolean hasRequestAccessibilityButtonFlag = (info.flags & 256) != 0;
                if (targetSdk > 29 || hasRequestAccessibilityButtonFlag || shortcutType != 1) {
                    targets.add(createAccessibilityServiceTarget(context, shortcutType, info));
                }
            }
        } catch (ConcurrentModificationException e) {
            Log.i("AccessibilityTargetHelper", "ConcurrentModificationException occurred : " + e);
        }
        return targets;
    }

    private static List<AccessibilityTarget> getAccessibilityActivityTargets(Context context, int shortcutType) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityShortcutInfo> installedServices = am.getInstalledAccessibilityShortcutListAsUser(context, ActivityManager.getCurrentUser());
        if (installedServices == null) {
            return Collections.emptyList();
        }
        List<AccessibilityTarget> targets = new ArrayList<>(installedServices.size());
        for (AccessibilityShortcutInfo info : installedServices) {
            targets.add(new AccessibilityActivityTarget(context, shortcutType, info));
        }
        return targets;
    }

    private static List<AccessibilityTarget> getAllowListingFeatureTargets(Context context, int shortcutType) {
        List<AccessibilityTarget> targets = new ArrayList<>();
        int uid = context.getApplicationInfo().uid;
        InvisibleToggleAllowListingFeatureTarget magnification = new InvisibleToggleAllowListingFeatureTarget(context, shortcutType, ShortcutUtils.isShortcutContained(context, shortcutType, "com.android.server.accessibility.MagnificationController"), "com.android.server.accessibility.MagnificationController", uid, context.getString(R.string.accessibility_magnification_chooser_text), context.getDrawable(R.drawable.ic_accessibility_magnification), Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_NAVBAR_ENABLED);
        targets.add(magnification);
        ToggleAllowListingFeatureTarget daltonizer = new ToggleAllowListingFeatureTarget(context, shortcutType, ShortcutUtils.isShortcutContained(context, shortcutType, AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString(), uid, context.getString(R.string.color_correction_feature_name), context.getDrawable(R.drawable.ic_accessibility_color_correction), Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED);
        targets.add(daltonizer);
        ToggleAllowListingFeatureTarget colorInversion = new ToggleAllowListingFeatureTarget(context, shortcutType, ShortcutUtils.isShortcutContained(context, shortcutType, AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString(), uid, context.getString(R.string.color_inversion_feature_name), context.getDrawable(R.drawable.ic_accessibility_color_inversion), Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED);
        targets.add(colorInversion);
        if (RoSystemProperties.SUPPORT_ONE_HANDED_MODE) {
            ToggleAllowListingFeatureTarget oneHandedMode = new ToggleAllowListingFeatureTarget(context, shortcutType, ShortcutUtils.isShortcutContained(context, shortcutType, AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.flattenToString(), uid, context.getString(R.string.one_handed_mode_feature_name), context.getDrawable(R.drawable.ic_accessibility_one_handed), Settings.Secure.ONE_HANDED_MODE_ACTIVATED);
            targets.add(oneHandedMode);
        }
        ToggleAllowListingFeatureTarget reduceBrightColors = new ToggleAllowListingFeatureTarget(context, shortcutType, ShortcutUtils.isShortcutContained(context, shortcutType, AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME.flattenToString(), uid, context.getString(R.string.reduce_bright_colors_feature_name), context.getDrawable(R.drawable.ic_accessibility_reduce_bright_colors), Settings.Secure.REDUCE_BRIGHT_COLORS_ACTIVATED);
        targets.add(reduceBrightColors);
        InvisibleToggleAllowListingFeatureTarget hearingAids = new InvisibleToggleAllowListingFeatureTarget(context, shortcutType, ShortcutUtils.isShortcutContained(context, shortcutType, AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString()), AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString(), uid, context.getString(R.string.hearing_aids_feature_name), context.getDrawable(R.drawable.ic_accessibility_hearing_aid), null);
        targets.add(hearingAids);
        return targets;
    }

    private static AccessibilityTarget createAccessibilityServiceTarget(Context context, int shortcutType, AccessibilityServiceInfo info) {
        switch (AccessibilityUtils.getAccessibilityServiceFragmentType(info)) {
            case 0:
                return new VolumeShortcutToggleAccessibilityServiceTarget(context, shortcutType, info);
            case 1:
                return new InvisibleToggleAccessibilityServiceTarget(context, shortcutType, info);
            case 2:
                return new ToggleAccessibilityServiceTarget(context, shortcutType, info);
            default:
                throw new IllegalStateException("Unexpected fragment type");
        }
    }

    public static boolean isAccessibilityTargetAllowed(Context context, String packageName, int uid) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        return am.isAccessibilityTargetAllowed(packageName, uid, UserHandle.myUserId());
    }

    public static boolean sendRestrictedDialogIntent(Context context, String packageName, int uid) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        return am.sendRestrictedDialogIntent(packageName, uid, UserHandle.myUserId());
    }
}
