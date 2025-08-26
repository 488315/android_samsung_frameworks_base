package com.android.internal.accessibility.util;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

/* loaded from: classes5.dex */
public final class AccessibilityUtils {
    public static final ComponentName ACCESSIBILITY_MENU_IN_SYSTEM = new ComponentName("com.android.systemui.accessibility.accessibilitymenu", "com.android.systemui.accessibility.accessibilitymenu.AccessibilityMenuService");
    public static final String MENU_SERVICE_RELATIVE_CLASS_NAME = ".AccessibilityMenuService";
    public static final int NONE = 0;
    public static final int PARCELABLE_SPAN = 2;
    public static final int TEXT = 1;
    private static boolean isVisibleShortcutDialog = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface A11yTextChangeType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        public static final int OFF = 0;
        public static final int ON = 1;
    }

    private AccessibilityUtils() {
    }

    public static Set<ComponentName> getEnabledServicesFromSettings(Context context, int i) {
        String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, i);
        if (TextUtils.isEmpty(stringForUser)) {
            return Collections.EMPTY_SET;
        }
        HashSet hashSet = new HashSet();
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(ShortcutConstants.SERVICES_SEPARATOR);
        simpleStringSplitter.setString(stringForUser);
        Iterator<String> it = simpleStringSplitter.iterator();
        while (it.hasNext()) {
            ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(it.next());
            if (componentNameUnflattenFromString != null) {
                hashSet.add(componentNameUnflattenFromString);
            }
        }
        return hashSet;
    }

    public static void setAccessibilityServiceState(Context context, ComponentName componentName, boolean z) {
        setAccessibilityServiceState(context, componentName, z, UserHandle.myUserId());
    }

    public static void setAccessibilityServiceState(Context context, ComponentName componentName, boolean z, int i) {
        Set enabledServicesFromSettings = getEnabledServicesFromSettings(context, i);
        if (enabledServicesFromSettings.isEmpty()) {
            enabledServicesFromSettings = new ArraySet(1);
        }
        if (z) {
            enabledServicesFromSettings.add(componentName);
        } else {
            enabledServicesFromSettings.remove(componentName);
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = enabledServicesFromSettings.iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString());
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES, sb.toString(), i);
    }

    public static int getAccessibilityServiceFragmentType(AccessibilityServiceInfo accessibilityServiceInfo) {
        int i = accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
        boolean z = (accessibilityServiceInfo.flags & 256) != 0;
        if (i <= 29) {
            return 0;
        }
        return z ? 1 : 2;
    }

    public static boolean isAccessibilityServiceEnabled(Context context, String str) {
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE)).getEnabledAccessibilityServiceList(-1).iterator();
        while (it.hasNext()) {
            if (it.next().getComponentName().flattenToString().equals(str)) {
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

    public static int textOrSpanChanged(CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.equals(charSequence, charSequence2)) {
            return (((charSequence instanceof Spanned) || (charSequence2 instanceof Spanned)) && !parcelableSpansEquals(charSequence, charSequence2)) ? 2 : 0;
        }
        return 1;
    }

    private static boolean parcelableSpansEquals(CharSequence charSequence, CharSequence charSequence2) {
        Spanned spanned;
        Object[] spans = EmptyArray.OBJECT;
        Object[] spans2 = EmptyArray.OBJECT;
        Spanned spanned2 = null;
        if (charSequence instanceof Spanned) {
            spanned = (Spanned) charSequence;
            spans = spanned.getSpans(0, spanned.length(), ParcelableSpan.class);
        } else {
            spanned = null;
        }
        if (charSequence2 instanceof Spanned) {
            spanned2 = (Spanned) charSequence2;
            spans2 = spanned2.getSpans(0, spanned2.length(), ParcelableSpan.class);
        }
        if (spans.length != spans2.length) {
            return false;
        }
        for (int i = 0; i < spans.length; i++) {
            Object obj = spans[i];
            Object obj2 = spans2[i];
            if (obj.getClass() != obj2.getClass() || spanned.getSpanStart(obj) != spanned2.getSpanStart(obj2) || spanned.getSpanEnd(obj) != spanned2.getSpanEnd(obj2) || spanned.getSpanFlags(obj) != spanned2.getSpanFlags(obj2)) {
                return false;
            }
        }
        return true;
    }

    public static ComponentName getAccessibilityMenuComponentToMigrate(PackageManager packageManager, int i) {
        Set<ComponentName> setFindA11yMenuComponentNames = findA11yMenuComponentNames(packageManager, i);
        Optional<ComponentName> optionalFindFirst = setFindA11yMenuComponentNames.stream().filter(new Predicate() { // from class: com.android.internal.accessibility.util.AccessibilityUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AccessibilityUtils.lambda$getAccessibilityMenuComponentToMigrate$0((ComponentName) obj);
            }
        }).findFirst();
        if (setFindA11yMenuComponentNames.size() == 2 && setFindA11yMenuComponentNames.contains(ACCESSIBILITY_MENU_IN_SYSTEM) && optionalFindFirst.isPresent()) {
            return optionalFindFirst.get();
        }
        return null;
    }

    static /* synthetic */ boolean lambda$getAccessibilityMenuComponentToMigrate$0(ComponentName componentName) {
        return !componentName.equals(ACCESSIBILITY_MENU_IN_SYSTEM);
    }

    private static Set<ComponentName> findA11yMenuComponentNames(PackageManager packageManager, int i) {
        ArraySet arraySet = new ArraySet();
        Iterator<ResolveInfo> it = packageManager.queryIntentServicesAsUser(new Intent(AccessibilityService.SERVICE_INTERFACE), PackageManager.ResolveInfoFlags.of(786944L), i).iterator();
        while (it.hasNext()) {
            ComponentName componentName = it.next().serviceInfo.getComponentName();
            if (componentName.getClassName().endsWith(MENU_SERVICE_RELATIVE_CLASS_NAME)) {
                arraySet.add(componentName);
            }
        }
        return arraySet;
    }

    public static ComponentName getInstalledAccessibilityServiceComponentNameByLabel(Context context, String str) {
        Iterator<AccessibilityServiceInfo> it = ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).getInstalledAccessibilityServiceList().iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().getResolveInfo().serviceInfo;
            if (str.equals(serviceInfo.loadLabel(context.getPackageManager()).toString()) && (serviceInfo.applicationInfo.isSystemApp() || serviceInfo.applicationInfo.isUpdatedSystemApp())) {
                return new ComponentName(serviceInfo.packageName, serviceInfo.name);
            }
        }
        return null;
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
        context.sendBroadcastAsUser(new Intent("com.sec.app.accessctrl.ACTION_STOP_SELF"), UserHandle.CURRENT);
    }

    public static boolean makeToastForEmergencyMode(Context context, String str, String str2) {
        if (!SemEmergencyManager.isEmergencyMode(context) || allowPerformInEmergencyMode(str)) {
            return false;
        }
        Toast.makeText(new ContextThemeWrapper(context, 16974123), context.getString(R.string.accessibility_shortcut_cannot_use_emergency_mode, str2), 0).show();
        return true;
    }

    private static boolean allowPerformInEmergencyMode(String str) {
        return str.equals("com.samsung.accessibility/com.samsung.accessibility.shortcut.AccessibilityHomepageActivityShortcut") || str.equals("com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService") || str.equals("com.android.server.accessibility.MagnificationController") || str.equals(AccessibilityShortcutController.TALKBACK_SE);
    }

    public static boolean makeToastForDexMode(Context context, String str, String str2) {
        if ((!isDesktopWindowing(context) || !disallowPerformInDexMode(str)) && (!isInDesktopWindowing(context) || !disallowPerformInDexDualMonitorDisplay(str))) {
            return false;
        }
        Toast.makeText(new ContextThemeWrapper(context, 16974123), context.getString(R.string.accessibility_shortcut_cannot_use_dex_mode, str2), 0).show();
        return true;
    }

    private static boolean disallowPerformInDexMode(String str) {
        return str.equals("com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService") || str.equals("com.android.server.accessibility.MagnificationController");
    }

    private static boolean disallowPerformInDexDualMonitorDisplay(String str) {
        return str.equals(A11yLogger.COMPONENT_NAME_RELUMINO_SHORTCUT.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString()) || str.equals(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString());
    }

    public static boolean isFoldedLargeCoverScreen() {
        return A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP && SemWindowManager.getInstance().isFolded();
    }

    public static boolean makeToastForCoverScreen(Context context, String str) {
        if (!isFoldedLargeCoverScreen() || (str != null && !disallowPerformInCoverScreen(str))) {
            return false;
        }
        Toast.makeText(new ContextThemeWrapper(context, 16974123), context.getString(R.string.accessibility_shortcut_open_phone_and_try_again), 0).show();
        return true;
    }

    public static boolean disallowPerformInCoverScreen(String str) {
        return str.equals(A11yLogger.COMPONENT_NAME_ACCESSIBILITY_HOMEPAGE_SHORTCUT.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_UNIVERSAL_SWITCH.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_MAGNIFIER_CAMERA_SHORTCUT.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_INTERACTION_CONTROL_SHORTCUT.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_VOICE_ACCESS.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_LIVE_TRANSCRIBE.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_GOOGLE_LIVE_TRANSCRIBE_SHORTCUT.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_GOOGLE_SOUND_NOTIFICATION_SHORTCUT.flattenToString());
    }

    public static boolean makeToastForFingerprint(Context context, String str, String str2) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager == null || fingerprintManager.semCanChangeDeviceColorMode() || !disallowPerformWhileFingerPrint(str)) {
            return false;
        }
        Toast.makeText(new ContextThemeWrapper(context, 16974123), context.getString(R.string.accessibility_shortcut_cannot_use_fingerprint, str2), 0).show();
        return true;
    }

    public static boolean disallowPerformWhileFingerPrint(String str) {
        return str.equals(A11yLogger.COMPONENT_NAME_COLOR_LENS_SHORTCUT.flattenToString()) || str.equals(A11yLogger.COMPONENT_NAME_COLOR_ADJUSTMENT_SHORTCUT.flattenToString()) || str.equals(AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME.flattenToString()) || str.equals(AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME.flattenToString());
    }

    public static boolean needToShowToast(Context context, String str, String str2) {
        if (isFoldedLargeCoverScreen()) {
            context = getSubDisplayContext(context);
        }
        return makeToastForEmergencyMode(context, str, str2) || makeToastForDexMode(context, str, str2) || makeToastForFingerprint(context, str, str2) || makeToastForCoverScreen(context, str);
    }

    @Deprecated
    public static boolean isDexMode(Context context) {
        return ((SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE)).getDesktopModeState().enabled == 4;
    }

    @Deprecated
    public static boolean isDexDualMonitorDisplay(Context context) {
        SemDesktopModeManager semDesktopModeManager;
        try {
            semDesktopModeManager = (SemDesktopModeManager) context.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        } catch (NullPointerException unused) {
        }
        return (semDesktopModeManager.getDesktopModeState().enabled == 4) && !(semDesktopModeManager.getDesktopModeState().getDisplayType() == 101);
    }

    public static boolean isDefaultTheme(Context context) {
        return TextUtils.isEmpty(Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage")) && TextUtils.isEmpty(Settings.System.getString(context.getContentResolver(), "current_sec_appicon_theme_package"));
    }

    public static boolean isHighContrastTheme(Context context) {
        String string = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage");
        return "com.samsung.High_contrast_theme_I".equals(string) || "com.samsung.High_contrast_theme_II".equals(string);
    }

    public static void updateProfile(Context context, String str) {
        context.sendBroadcastAsUser(new Intent("com.samsung.accessibility.action.UPDATE_PROFILE").setClassName("com.android.settings", "com.samsung.android.settings.accessibility.recommend.RecommendedForYouReceiver").putExtra("component", str), UserHandle.CURRENT);
    }

    public static boolean isSideKeySupported() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_FUNCTION_KEY_MENU");
    }

    public static boolean isSetupWizard(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 1;
    }

    public static boolean isDefaultDisplay(Context context) {
        return context.getDisplay().getDisplayId() == 0;
    }

    public static boolean isDesktopWindowing(Context context) {
        for (Display display : ((DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)).getDisplays()) {
            if ((display.getFlags() & 131072) != 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInDesktopWindowing(Context context) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = displayManager.getDisplays();
        if (displays.length > 0) {
            Display display = displays[0];
            if (display.getDisplayId() != 0 && display.getDisplayId() != -1 && (displayManager.getDisplay(display.getDisplayId()).getFlags() & 131072) != 0) {
                return true;
            }
        }
        return false;
    }

    public static Context getSubDisplayContext(Context context) {
        return context.createDisplayContext(((DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1]);
    }

    public static boolean getVisiblityShortcutDialog() {
        return isVisibleShortcutDialog;
    }

    public static void setVisibilityShortcutDialog(boolean z) {
        isVisibleShortcutDialog = z;
    }
}
