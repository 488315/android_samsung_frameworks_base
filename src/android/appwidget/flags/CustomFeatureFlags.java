package android.appwidget.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_DRAW_DATA_PARCEL, Flags.FLAG_ENGAGEMENT_METRICS, Flags.FLAG_GENERATED_PREVIEWS, Flags.FLAG_NOT_KEYGUARD_CATEGORY, Flags.FLAG_REMOTE_ADAPTER_CONVERSION, Flags.FLAG_REMOTE_DOCUMENT_SUPPORT, Flags.FLAG_REMOTE_VIEWS_PROTO, Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH, Flags.FLAG_SECURITY_POLICY_INTERACT_ACROSS_USERS, Flags.FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT, Flags.FLAG_THROTTLE_WIDGET_UPDATES, Flags.FLAG_USE_SMALLER_APP_WIDGET_SYSTEM_RADIUS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean drawDataParcel() {
        return getValue(Flags.FLAG_DRAW_DATA_PARCEL, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).drawDataParcel();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean engagementMetrics() {
        return getValue(Flags.FLAG_ENGAGEMENT_METRICS, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).engagementMetrics();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean generatedPreviews() {
        return getValue(Flags.FLAG_GENERATED_PREVIEWS, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).generatedPreviews();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean notKeyguardCategory() {
        return getValue(Flags.FLAG_NOT_KEYGUARD_CATEGORY, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).notKeyguardCategory();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteAdapterConversion() {
        return getValue(Flags.FLAG_REMOTE_ADAPTER_CONVERSION, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remoteAdapterConversion();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteDocumentSupport() {
        return getValue(Flags.FLAG_REMOTE_DOCUMENT_SUPPORT, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remoteDocumentSupport();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteViewsProto() {
        return getValue(Flags.FLAG_REMOTE_VIEWS_PROTO, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).remoteViewsProto();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean removeAppWidgetServiceIoFromCriticalPath() {
        return getValue(Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeAppWidgetServiceIoFromCriticalPath();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean securityPolicyInteractAcrossUsers() {
        return getValue(Flags.FLAG_SECURITY_POLICY_INTERACT_ACROSS_USERS, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).securityPolicyInteractAcrossUsers();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean supportResumeRestoreAfterReboot() {
        return getValue(Flags.FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).supportResumeRestoreAfterReboot();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean throttleWidgetUpdates() {
        return getValue(Flags.FLAG_THROTTLE_WIDGET_UPDATES, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).throttleWidgetUpdates();
            }
        });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean useSmallerAppWidgetSystemRadius() {
        return getValue(Flags.FLAG_USE_SMALLER_APP_WIDGET_SYSTEM_RADIUS, new Predicate() { // from class: android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useSmallerAppWidgetSystemRadius();
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
        return Arrays.asList(Flags.FLAG_DRAW_DATA_PARCEL, Flags.FLAG_ENGAGEMENT_METRICS, Flags.FLAG_GENERATED_PREVIEWS, Flags.FLAG_NOT_KEYGUARD_CATEGORY, Flags.FLAG_REMOTE_ADAPTER_CONVERSION, Flags.FLAG_REMOTE_DOCUMENT_SUPPORT, Flags.FLAG_REMOTE_VIEWS_PROTO, Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH, Flags.FLAG_SECURITY_POLICY_INTERACT_ACROSS_USERS, Flags.FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT, Flags.FLAG_THROTTLE_WIDGET_UPDATES, Flags.FLAG_USE_SMALLER_APP_WIDGET_SYSTEM_RADIUS);
    }
}
