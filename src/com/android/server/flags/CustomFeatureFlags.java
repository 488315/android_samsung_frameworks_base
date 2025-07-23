package com.android.server.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CERTPININSTALLER_REMOVAL, Flags.FLAG_CONSOLIDATE_BATTERY_CHANGE_EVENTS, Flags.FLAG_DATETIME_NOTIFICATIONS, Flags.FLAG_DISABLE_SYSTEM_COMPACTION, Flags.FLAG_EARLY_DATA_MANAGER_INIT, Flags.FLAG_ENABLE_ODP_FEATURE_GUARD, Flags.FLAG_MODIFIER_SHORTCUT_MANAGER_MULTIUSER, Flags.FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT, Flags.FLAG_OPTIONAL_BACKGROUND_INSTALL_CONTROL, Flags.FLAG_PIN_GLOBAL_QUOTA, Flags.FLAG_PIN_WEBVIEW, Flags.FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY, Flags.FLAG_RATE_LIMIT_BATTERY_CHANGED_BROADCAST, Flags.FLAG_TRACE_BATTERY_CHANGED_BROADCAST_EVENT, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean certpininstallerRemoval() {
        return getValue(Flags.FLAG_CERTPININSTALLER_REMOVAL, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).certpininstallerRemoval();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean consolidateBatteryChangeEvents() {
        return getValue(Flags.FLAG_CONSOLIDATE_BATTERY_CHANGE_EVENTS, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).consolidateBatteryChangeEvents();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean datetimeNotifications() {
        return getValue(Flags.FLAG_DATETIME_NOTIFICATIONS, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).datetimeNotifications();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean disableSystemCompaction() {
        return getValue(Flags.FLAG_DISABLE_SYSTEM_COMPACTION, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableSystemCompaction();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean earlyDataManagerInit() {
        return getValue(Flags.FLAG_EARLY_DATA_MANAGER_INIT, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).earlyDataManagerInit();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean enableOdpFeatureGuard() {
        return getValue(Flags.FLAG_ENABLE_ODP_FEATURE_GUARD, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOdpFeatureGuard();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean modifierShortcutManagerMultiuser() {
        return getValue(Flags.FLAG_MODIFIER_SHORTCUT_MANAGER_MULTIUSER, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modifierShortcutManagerMultiuser();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean newBugreportKeyboardShortcut() {
        return getValue(Flags.FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).newBugreportKeyboardShortcut();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean optionalBackgroundInstallControl() {
        return getValue(Flags.FLAG_OPTIONAL_BACKGROUND_INSTALL_CONTROL, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).optionalBackgroundInstallControl();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pinGlobalQuota() {
        return getValue(Flags.FLAG_PIN_GLOBAL_QUOTA, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).pinGlobalQuota();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pinWebview() {
        return getValue(Flags.FLAG_PIN_WEBVIEW, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).pinWebview();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean pkgTargetedBatteryChangedNotSticky() {
        return getValue(Flags.FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).pkgTargetedBatteryChangedNotSticky();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean rateLimitBatteryChangedBroadcast() {
        return getValue(Flags.FLAG_RATE_LIMIT_BATTERY_CHANGED_BROADCAST, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitBatteryChangedBroadcast();
            }
        });
    }

    @Override // com.android.server.flags.FeatureFlags
    public boolean traceBatteryChangedBroadcastEvent() {
        return getValue(Flags.FLAG_TRACE_BATTERY_CHANGED_BROADCAST_EVENT, new Predicate() { // from class: com.android.server.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).traceBatteryChangedBroadcastEvent();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_CERTPININSTALLER_REMOVAL, Flags.FLAG_CONSOLIDATE_BATTERY_CHANGE_EVENTS, Flags.FLAG_DATETIME_NOTIFICATIONS, Flags.FLAG_DISABLE_SYSTEM_COMPACTION, Flags.FLAG_EARLY_DATA_MANAGER_INIT, Flags.FLAG_ENABLE_ODP_FEATURE_GUARD, Flags.FLAG_MODIFIER_SHORTCUT_MANAGER_MULTIUSER, Flags.FLAG_NEW_BUGREPORT_KEYBOARD_SHORTCUT, Flags.FLAG_OPTIONAL_BACKGROUND_INSTALL_CONTROL, Flags.FLAG_PIN_GLOBAL_QUOTA, Flags.FLAG_PIN_WEBVIEW, Flags.FLAG_PKG_TARGETED_BATTERY_CHANGED_NOT_STICKY, Flags.FLAG_RATE_LIMIT_BATTERY_CHANGED_BROADCAST, Flags.FLAG_TRACE_BATTERY_CHANGED_BROADCAST_EVENT);
    }
}
