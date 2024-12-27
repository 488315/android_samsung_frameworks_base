package com.android.internal.hidden_from_bootclasspath.android.service.notification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CALLSTYLE_CALLBACK_API, Flags.FLAG_RANKING_UPDATE_ASHMEM, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean callstyleCallbackApi() {
        return getValue(Flags.FLAG_CALLSTYLE_CALLBACK_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callstyleCallbackApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean rankingUpdateAshmem() {
        return getValue(Flags.FLAG_RANKING_UPDATE_ASHMEM, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rankingUpdateAshmem();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsBigTextStyle() {
        return getValue(Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).redactSensitiveNotificationsBigTextStyle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return getValue(Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).redactSensitiveNotificationsFromUntrustedListeners();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_CALLSTYLE_CALLBACK_API, Flags.FLAG_RANKING_UPDATE_ASHMEM, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS);
    }
}
