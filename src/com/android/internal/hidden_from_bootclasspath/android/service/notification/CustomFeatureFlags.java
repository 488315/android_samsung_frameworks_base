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
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_CALLSTYLE_CALLBACK_API, Flags.FLAG_NOTIFICATION_CLASSIFICATION, Flags.FLAG_NOTIFICATION_CONVERSATION_CHANNEL_MANAGEMENT, Flags.FLAG_NOTIFICATION_FORCE_GROUPING, Flags.FLAG_NOTIFICATION_REGROUP_ON_CLASSIFICATION, Flags.FLAG_NOTIFICATION_SILENT_FLAG, Flags.FLAG_RANKING_UPDATE_ASHMEM, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean callstyleCallbackApi() {
        return getValue(Flags.FLAG_CALLSTYLE_CALLBACK_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).callstyleCallbackApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean notificationClassification() {
        return getValue(Flags.FLAG_NOTIFICATION_CLASSIFICATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationClassification();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean notificationConversationChannelManagement() {
        return getValue(Flags.FLAG_NOTIFICATION_CONVERSATION_CHANNEL_MANAGEMENT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationConversationChannelManagement();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean notificationForceGrouping() {
        return getValue(Flags.FLAG_NOTIFICATION_FORCE_GROUPING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationForceGrouping();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean notificationRegroupOnClassification() {
        return getValue(Flags.FLAG_NOTIFICATION_REGROUP_ON_CLASSIFICATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationRegroupOnClassification();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean notificationSilentFlag() {
        return getValue(Flags.FLAG_NOTIFICATION_SILENT_FLAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notificationSilentFlag();
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
        return getValue(Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).redactSensitiveNotificationsBigTextStyle();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return getValue(Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.service.notification.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).redactSensitiveNotificationsFromUntrustedListeners();
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
        return Arrays.asList(Flags.FLAG_CALLSTYLE_CALLBACK_API, Flags.FLAG_NOTIFICATION_CLASSIFICATION, Flags.FLAG_NOTIFICATION_CONVERSATION_CHANNEL_MANAGEMENT, Flags.FLAG_NOTIFICATION_FORCE_GROUPING, Flags.FLAG_NOTIFICATION_REGROUP_ON_CLASSIFICATION, Flags.FLAG_NOTIFICATION_SILENT_FLAG, Flags.FLAG_RANKING_UPDATE_ASHMEM, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_BIG_TEXT_STYLE, Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS);
    }
}
