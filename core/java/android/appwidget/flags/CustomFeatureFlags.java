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
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_DRAW_DATA_PARCEL,
                            Flags.FLAG_GENERATED_PREVIEWS,
                            Flags.FLAG_REMOTE_ADAPTER_CONVERSION,
                            Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH,
                            Flags.FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT,
                            Flags.FLAG_THROTTLE_WIDGET_UPDATES,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean drawDataParcel() {
        return getValue(
                Flags.FLAG_DRAW_DATA_PARCEL,
                new Predicate() { // from class:
                    // android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).drawDataParcel();
                    }
                });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean generatedPreviews() {
        return getValue(
                Flags.FLAG_GENERATED_PREVIEWS,
                new Predicate() { // from class:
                    // android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).generatedPreviews();
                    }
                });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteAdapterConversion() {
        return getValue(
                Flags.FLAG_REMOTE_ADAPTER_CONVERSION,
                new Predicate() { // from class:
                    // android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).remoteAdapterConversion();
                    }
                });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean removeAppWidgetServiceIoFromCriticalPath() {
        return getValue(
                Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH,
                new Predicate() { // from class:
                    // android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).removeAppWidgetServiceIoFromCriticalPath();
                    }
                });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean supportResumeRestoreAfterReboot() {
        return getValue(
                Flags.FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT,
                new Predicate() { // from class:
                    // android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).supportResumeRestoreAfterReboot();
                    }
                });
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean throttleWidgetUpdates() {
        return getValue(
                Flags.FLAG_THROTTLE_WIDGET_UPDATES,
                new Predicate() { // from class:
                    // android.appwidget.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).throttleWidgetUpdates();
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
        return Arrays.asList(
                Flags.FLAG_DRAW_DATA_PARCEL,
                Flags.FLAG_GENERATED_PREVIEWS,
                Flags.FLAG_REMOTE_ADAPTER_CONVERSION,
                Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH,
                Flags.FLAG_SUPPORT_RESUME_RESTORE_AFTER_REBOOT,
                Flags.FLAG_THROTTLE_WIDGET_UPDATES);
    }
}
