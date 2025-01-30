package com.android.internal.accessibility.util;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.hardware.fingerprint.FingerprintManager;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.ContextThemeWrapper;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.internal.C4337R;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

/* loaded from: classes4.dex */
public final class AccessibilityUtils {
    public static final String MENU_SERVICE_RELATIVE_CLASS_NAME = ".AccessibilityMenuService";
    public static final int NONE = 0;
    public static final int PARCELABLE_SPAN = 2;
    public static final int TEXT = 1;
    private static boolean isVisibleShortcutDialog = false;
    public static final ComponentName ACCESSIBILITY_MENU_IN_SYSTEM = new ComponentName("com.android.systemui.accessibility.accessibilitymenu", "com.android.systemui.accessibility.accessibilitymenu.AccessibilityMenuService");

    @Retention(RetentionPolicy.SOURCE)
    public @interface A11yTextChangeType {
    }

    private AccessibilityUtils() {
    }

    public static Set<ComponentName> getEnabledServicesFromSettings(Context context, int userId) {
        String enabledServicesSetting = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, userId);
        if (TextUtils.isEmpty(enabledServicesSetting)) {
            return Collections.emptySet();
        }
        Set<ComponentName> enabledServices = new HashSet<>();
        TextUtils.StringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(ShortcutConstants.SERVICES_SEPARATOR);
        colonSplitter.setString(enabledServicesSetting);
        for (String componentNameString : colonSplitter) {
            ComponentName enabledService = ComponentName.unflattenFromString(componentNameString);
            if (enabledService != null) {
                enabledServices.add(enabledService);
            }
        }
        return enabledServices;
    }

    public static void setAccessibilityServiceState(Context context, ComponentName componentName, boolean enabled) {
        setAccessibilityServiceState(context, componentName, enabled, UserHandle.myUserId());
    }

    public static void setAccessibilityServiceState(Context context, ComponentName componentName, boolean enabled, int userId) {
        Set<ComponentName> enabledServices = getEnabledServicesFromSettings(context, userId);
        if (enabledServices.isEmpty()) {
            enabledServices = new ArraySet(1);
        }
        if (enabled) {
            enabledServices.add(componentName);
        } else {
            enabledServices.remove(componentName);
        }
        StringBuilder enabledServicesBuilder = new StringBuilder();
        for (ComponentName enabledService : enabledServices) {
            enabledServicesBuilder.append(enabledService.flattenToString());
            enabledServicesBuilder.append(ShortcutConstants.SERVICES_SEPARATOR);
        }
        int enabledServicesBuilderLength = enabledServicesBuilder.length();
        if (enabledServicesBuilderLength > 0) {
            enabledServicesBuilder.deleteCharAt(enabledServicesBuilderLength - 1);
        }
        Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, enabledServicesBuilder.toString(), userId);
    }

    public static int getAccessibilityServiceFragmentType(AccessibilityServiceInfo accessibilityServiceInfo) {
        int targetSdk = accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
        boolean requestA11yButton = (accessibilityServiceInfo.flags & 256) != 0;
        if (targetSdk <= 29) {
            return 0;
        }
        return requestA11yButton ? 1 : 2;
    }

    public static boolean isAccessibilityServiceEnabled(Context context, String componentId) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(-1);
        for (AccessibilityServiceInfo info : enabledServices) {
            String id = info.getComponentName().flattenToString();
            if (id.equals(componentId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean interceptHeadsetHookForActiveCall(Context context) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(TelecomManager.class);
        int callState = telecomManager != null ? telecomManager.getCallState() : 0;
        if (callState == 1) {
            telecomManager.acceptRingingCall();
            return true;
        }
        if (callState != 2) {
            return false;
        }
        telecomManager.endCall();
        return true;
    }

    public static boolean isUserSetupCompleted(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0, -2) != 0;
    }

    public static int textOrSpanChanged(CharSequence before, CharSequence after) {
        if (!TextUtils.equals(before, after)) {
            return 1;
        }
        if (((before instanceof Spanned) || (after instanceof Spanned)) && !parcelableSpansEquals(before, after)) {
            return 2;
        }
        return 0;
    }

    private static boolean parcelableSpansEquals(CharSequence before, CharSequence after) {
        Object[] spansA = EmptyArray.OBJECT;
        Object[] spansB = EmptyArray.OBJECT;
        Spanned a = null;
        Spanned b = null;
        if (before instanceof Spanned) {
            a = (Spanned) before;
            spansA = a.getSpans(0, a.length(), ParcelableSpan.class);
        }
        if (after instanceof Spanned) {
            b = (Spanned) after;
            spansB = b.getSpans(0, b.length(), ParcelableSpan.class);
        }
        if (spansA.length != spansB.length) {
            return false;
        }
        for (int i = 0; i < spansA.length; i++) {
            Object thisSpan = spansA[i];
            Object otherSpan = spansB[i];
            if (thisSpan.getClass() != otherSpan.getClass() || a.getSpanStart(thisSpan) != b.getSpanStart(otherSpan) || a.getSpanEnd(thisSpan) != b.getSpanEnd(otherSpan) || a.getSpanFlags(thisSpan) != b.getSpanFlags(otherSpan)) {
                return false;
            }
        }
        return true;
    }

    public static ComponentName getAccessibilityMenuComponentToMigrate(PackageManager packageManager, int userId) {
        Set<ComponentName> menuComponentNames = findA11yMenuComponentNames(packageManager, userId);
        Optional<ComponentName> menuOutsideSystem = menuComponentNames.stream().filter(new Predicate() { // from class: com.android.internal.accessibility.util.AccessibilityUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AccessibilityUtils.lambda$getAccessibilityMenuComponentToMigrate$0((ComponentName) obj);
            }
        }).findFirst();
        boolean shouldMigrateToMenuInSystem = menuComponentNames.size() == 2 && menuComponentNames.contains(ACCESSIBILITY_MENU_IN_SYSTEM) && menuOutsideSystem.isPresent();
        if (shouldMigrateToMenuInSystem) {
            return menuOutsideSystem.get();
        }
        return null;
    }

    static /* synthetic */ boolean lambda$getAccessibilityMenuComponentToMigrate$0(ComponentName name) {
        return !name.equals(ACCESSIBILITY_MENU_IN_SYSTEM);
    }

    private static Set<ComponentName> findA11yMenuComponentNames(PackageManager packageManager, int userId) {
        Set<ComponentName> result = new ArraySet<>();
        PackageManager.ResolveInfoFlags flags = PackageManager.ResolveInfoFlags.m12of(786944L);
        for (ResolveInfo resolveInfo : packageManager.queryIntentServicesAsUser(new Intent(AccessibilityService.SERVICE_INTERFACE), flags, userId)) {
            ComponentName componentName = resolveInfo.serviceInfo.getComponentName();
            if (componentName.getClassName().endsWith(MENU_SERVICE_RELATIVE_CLASS_NAME)) {
                result.add(componentName);
            }
        }
        return result;
    }

    public static boolean isAccessControlEnabled(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.SEM_ACCESS_CONTROL_ENABLED, 0, -2) != 0;
    }

    public static void turnOffAccessControl(Context context) {
        try {
            ActivityTaskManager.getService().stopSystemLockTaskMode();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.SEM_ACCESS_CONTROL_ENABLED, 0, -2);
        Intent intent = new Intent("com.sec.app.accessctrl.ACTION_STOP_SELF");
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }

    public static boolean makeToastForEmergencyMode(Context context, String targetName, String targetLabel) {
        if (!SemEmergencyManager.isEmergencyMode(context) || allowPerformInEmergencyMode(targetName)) {
            return false;
        }
        String message = context.getString(C4337R.string.accessibility_shortcut_cannot_use_emergency_mode, targetLabel);
        Toast.makeText(new ContextThemeWrapper(context, 16974123), message, 0).show();
        return true;
    }

    private static boolean allowPerformInEmergencyMode(String targetName) {
        return targetName.equals("com.samsung.accessibility/com.samsung.accessibility.shortcut.AccessibilityHomepageActivityShortcut") || targetName.equals("com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService") || targetName.equals("com.android.server.accessibility.MagnificationController") || targetName.equals(AccessibilityShortcutController.TALKBACK_SE);
    }

    public static boolean makeToastForDexMode(Context context, String targetName, String targetLabel) {
        if ((!isDexMode(context) || !disallowPerformInDexMode(targetName)) && (!isDexDualMonitorDisplay(context) || !disallowPerformInDexDualMonitorDisplay(targetName))) {
            return false;
        }
        String message = context.getString(C4337R.string.accessibility_shortcut_cannot_use_dex_mode, targetLabel);
        Toast.makeText(new ContextThemeWrapper(context, 16974123), message, 0).show();
        return true;
    }

    private static boolean disallowPerformInDexMode(String targetName) {
        return targetName.equals("com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService") || targetName.equals("com.android.server.accessibility.MagnificationController");
    }

    private static boolean disallowPerformInDexDualMonitorDisplay(String targetName) {
        return targetName.equals(A11yLogger.COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString()) || targetName.equals(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString());
    }

    public static boolean isFoldedLargeCoverScreen() {
        return A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP && SemWindowManager.getInstance().isFolded();
    }

    public static boolean makeToastForCoverScreen(Context context, String targetName) {
        if (!isFoldedLargeCoverScreen() || (targetName != null && !disallowPerformInCoverScreen(targetName))) {
            return false;
        }
        String message = context.getString(C4337R.string.accessibility_shortcut_open_phone_and_try_again);
        Toast.makeText(new ContextThemeWrapper(context, 16974123), message, 0).show();
        return true;
    }

    public static boolean disallowPerformInCoverScreen(String targetName) {
        return targetName.equals(A11yLogger.COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_UNIVERSAL_SWITCH.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_VOICE_ACCESS.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_LIVE_TRANSCRIBE.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT.flattenToString());
    }

    public static boolean makeToastForFingerprint(Context context, String targetName, String targetLabel) {
        FingerprintManager fpm = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        if (fpm.semCanChangeDeviceColorMode() || !disallowPerformWhileFingerPrint(targetName)) {
            return false;
        }
        String message = context.getString(C4337R.string.accessibility_shortcut_cannot_use_fingerprint, targetLabel);
        Toast.makeText(new ContextThemeWrapper(context, 16974123), message, 0).show();
        return true;
    }

    public static boolean disallowPerformWhileFingerPrint(String targetName) {
        return targetName.equals(A11yLogger.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString()) || targetName.equals(A11yLogger.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT.flattenToString()) || targetName.equals(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString()) || targetName.equals(AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString());
    }

    public static boolean needToShowToast(Context context, String targetName, String targetLabel) {
        return makeToastForEmergencyMode(context, targetName, targetLabel) || makeToastForDexMode(context, targetName, targetLabel) || makeToastForFingerprint(context, targetName, targetLabel) || makeToastForCoverScreen(context, targetName);
    }

    public static boolean isDexMode(Context context) {
        try {
            SemDesktopModeManager desktopModeManager = (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
            return desktopModeManager.getDesktopModeState().enabled == 4;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isDexDualMonitorDisplay(Context context) {
        try {
            SemDesktopModeManager desktopModeManager = (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
            SemDesktopModeState desktopModeState = desktopModeManager.getDesktopModeState();
            boolean isDexMode = desktopModeManager.getDesktopModeState().enabled == 4;
            boolean isDesktopStandaloneMode = desktopModeState.getDisplayType() == 101;
            return isDexMode && !isDesktopStandaloneMode;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static void updateProfile(Context context, String targetName) {
        context.sendBroadcastAsUser(new Intent("com.samsung.accessibility.action.UPDATE_PROFILE").setClassName("com.android.settings", "com.samsung.android.settings.accessibility.recommend.RecommendedForYouReceiver").putExtra("component", targetName), UserHandle.CURRENT);
    }

    public static boolean isSideKeySupported() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_FUNCTION_KEY_MENU");
    }

    public static boolean isSetupWizard(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 1;
    }

    public static boolean getVisiblityShortcutDialog() {
        return isVisibleShortcutDialog;
    }

    public static void setVisibilityShortcutDialog(boolean visible) {
        isVisibleShortcutDialog = visible;
    }
}
