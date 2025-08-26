package android.service.notification;

import android.app.Flags;
import android.os.Environment;
import android.service.notification.ZenModeConfig;
import android.telecom.Logging.Session;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class ZenModeDiff {
    public static final int ADDED = 1;
    public static final int NONE = 0;
    public static final int REMOVED = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExistenceChange {
    }

    public static class FieldDiff<T> {
        private final BaseDiff mDetailedDiff;
        private final T mFrom;
        private final T mTo;

        public FieldDiff(T t, T t2) {
            this.mFrom = t;
            this.mTo = t2;
            this.mDetailedDiff = null;
        }

        public FieldDiff(T t, T t2, BaseDiff baseDiff) {
            this.mFrom = t;
            this.mTo = t2;
            this.mDetailedDiff = baseDiff;
        }

        public T from() {
            return this.mFrom;
        }

        public T to() {
            return this.mTo;
        }

        public String toString() {
            BaseDiff baseDiff = this.mDetailedDiff;
            if (baseDiff != null) {
                return baseDiff.toString();
            }
            return this.mFrom + Session.SUBSESSION_SEPARATION_CHAR + this.mTo;
        }

        public boolean hasDiff() {
            BaseDiff baseDiff = this.mDetailedDiff;
            if (baseDiff != null) {
                return baseDiff.hasDiff();
            }
            return !Objects.equals(this.mFrom, this.mTo);
        }
    }

    private static abstract class BaseDiff {
        private int mExists;
        private LinkedHashMap<String, FieldDiff> mFields = new LinkedHashMap<>();

        public abstract boolean hasDiff();

        public abstract String toString();

        BaseDiff(Object obj, Object obj2) {
            this.mExists = 0;
            if (obj == null) {
                if (obj2 != null) {
                    this.mExists = 1;
                }
            } else if (obj2 == null) {
                this.mExists = 2;
            }
        }

        final void addField(String str, FieldDiff fieldDiff) {
            this.mFields.put(str, fieldDiff);
        }

        public final boolean wasAdded() {
            return this.mExists == 1;
        }

        public final boolean wasRemoved() {
            return this.mExists == 2;
        }

        public final boolean hasExistenceChange() {
            return this.mExists != 0;
        }

        public final boolean hasFieldDiffs() {
            return this.mFields.size() > 0;
        }

        public final FieldDiff getDiffForField(String str) {
            return this.mFields.getOrDefault(str, null);
        }

        public final Set<String> fieldNamesWithDiff() {
            return this.mFields.keySet();
        }
    }

    public static class ConfigDiff extends BaseDiff {
        public static final String FIELD_ALLOW_ALARMS = "allowAlarms";
        public static final String FIELD_ALLOW_CALLS = "allowCalls";
        public static final String FIELD_ALLOW_CONVERSATIONS = "allowConversations";
        public static final String FIELD_ALLOW_CONVERSATIONS_FROM = "allowConversationsFrom";
        public static final String FIELD_ALLOW_EVENTS = "allowEvents";
        public static final String FIELD_ALLOW_MEDIA = "allowMedia";
        public static final String FIELD_ALLOW_MESSAGES = "allowMessages";
        public static final String FIELD_ALLOW_PRIORITY_CHANNELS = "allowPriorityChannels";
        public static final String FIELD_ALLOW_REMINDERS = "allowReminders";
        public static final String FIELD_ALLOW_REPEAT_CALLERS = "allowRepeatCallers";
        public static final String FIELD_ALLOW_SYSTEM = "allowSystem";
        public static final String FIELD_HAS_PRIORITY_CHANNELS = "hasPriorityChannels";
        public static final String FIELD_SUPPRESSED_VISUAL_EFFECTS = "suppressedVisualEffects";
        public static final String FIELD_USER = "user";
        private final ArrayMap<String, RuleDiff> mAutomaticRulesDiff;
        private RuleDiff mManualRuleDiff;
        public static final String FIELD_ALLOW_CALLS_FROM = "allowCallsFrom";
        public static final String FIELD_ALLOW_MESSAGES_FROM = "allowMessagesFrom";
        private static final Set<String> PEOPLE_TYPE_FIELDS = Set.of(FIELD_ALLOW_CALLS_FROM, FIELD_ALLOW_MESSAGES_FROM);

        public ConfigDiff(ZenModeConfig zenModeConfig, ZenModeConfig zenModeConfig2) {
            super(zenModeConfig, zenModeConfig2);
            this.mAutomaticRulesDiff = new ArrayMap<>();
            if ((zenModeConfig == null && zenModeConfig2 == null) || hasExistenceChange()) {
                return;
            }
            if (zenModeConfig.user != zenModeConfig2.user) {
                addField("user", new FieldDiff(Integer.valueOf(zenModeConfig.user), Integer.valueOf(zenModeConfig2.user)));
            }
            if (zenModeConfig.allowAlarms != zenModeConfig2.allowAlarms) {
                addField(FIELD_ALLOW_ALARMS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowAlarms), Boolean.valueOf(zenModeConfig2.allowAlarms)));
            }
            if (zenModeConfig.allowMedia != zenModeConfig2.allowMedia) {
                addField(FIELD_ALLOW_MEDIA, new FieldDiff(Boolean.valueOf(zenModeConfig.allowMedia), Boolean.valueOf(zenModeConfig2.allowMedia)));
            }
            if (zenModeConfig.allowSystem != zenModeConfig2.allowSystem) {
                addField(FIELD_ALLOW_SYSTEM, new FieldDiff(Boolean.valueOf(zenModeConfig.allowSystem), Boolean.valueOf(zenModeConfig2.allowSystem)));
            }
            if (zenModeConfig.allowCalls != zenModeConfig2.allowCalls) {
                addField(FIELD_ALLOW_CALLS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowCalls), Boolean.valueOf(zenModeConfig2.allowCalls)));
            }
            if (zenModeConfig.allowReminders != zenModeConfig2.allowReminders) {
                addField(FIELD_ALLOW_REMINDERS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowReminders), Boolean.valueOf(zenModeConfig2.allowReminders)));
            }
            if (zenModeConfig.allowEvents != zenModeConfig2.allowEvents) {
                addField(FIELD_ALLOW_EVENTS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowEvents), Boolean.valueOf(zenModeConfig2.allowEvents)));
            }
            if (zenModeConfig.allowRepeatCallers != zenModeConfig2.allowRepeatCallers) {
                addField(FIELD_ALLOW_REPEAT_CALLERS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowRepeatCallers), Boolean.valueOf(zenModeConfig2.allowRepeatCallers)));
            }
            if (zenModeConfig.allowMessages != zenModeConfig2.allowMessages) {
                addField(FIELD_ALLOW_MESSAGES, new FieldDiff(Boolean.valueOf(zenModeConfig.allowMessages), Boolean.valueOf(zenModeConfig2.allowMessages)));
            }
            if (zenModeConfig.allowConversations != zenModeConfig2.allowConversations) {
                addField(FIELD_ALLOW_CONVERSATIONS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowConversations), Boolean.valueOf(zenModeConfig2.allowConversations)));
            }
            if (zenModeConfig.allowCallsFrom != zenModeConfig2.allowCallsFrom) {
                addField(FIELD_ALLOW_CALLS_FROM, new FieldDiff(Integer.valueOf(zenModeConfig.allowCallsFrom), Integer.valueOf(zenModeConfig2.allowCallsFrom)));
            }
            if (zenModeConfig.allowMessagesFrom != zenModeConfig2.allowMessagesFrom) {
                addField(FIELD_ALLOW_MESSAGES_FROM, new FieldDiff(Integer.valueOf(zenModeConfig.allowMessagesFrom), Integer.valueOf(zenModeConfig2.allowMessagesFrom)));
            }
            if (zenModeConfig.allowConversationsFrom != zenModeConfig2.allowConversationsFrom) {
                addField(FIELD_ALLOW_CONVERSATIONS_FROM, new FieldDiff(Integer.valueOf(zenModeConfig.allowConversationsFrom), Integer.valueOf(zenModeConfig2.allowConversationsFrom)));
            }
            if (zenModeConfig.suppressedVisualEffects != zenModeConfig2.suppressedVisualEffects) {
                addField(FIELD_SUPPRESSED_VISUAL_EFFECTS, new FieldDiff(Integer.valueOf(zenModeConfig.suppressedVisualEffects), Integer.valueOf(zenModeConfig2.suppressedVisualEffects)));
            }
            if (zenModeConfig.hasPriorityChannels != zenModeConfig2.hasPriorityChannels) {
                addField(FIELD_HAS_PRIORITY_CHANNELS, new FieldDiff(Boolean.valueOf(zenModeConfig.hasPriorityChannels), Boolean.valueOf(zenModeConfig2.hasPriorityChannels)));
            }
            if (zenModeConfig.allowPriorityChannels != zenModeConfig2.allowPriorityChannels) {
                addField(FIELD_ALLOW_PRIORITY_CHANNELS, new FieldDiff(Boolean.valueOf(zenModeConfig.allowPriorityChannels), Boolean.valueOf(zenModeConfig2.allowPriorityChannels)));
            }
            ArraySet arraySet = new ArraySet();
            addKeys(arraySet, zenModeConfig.automaticRules);
            addKeys(arraySet, zenModeConfig2.automaticRules);
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                String str = (String) arraySet.valueAt(i);
                RuleDiff ruleDiff = new RuleDiff(zenModeConfig.automaticRules != null ? zenModeConfig.automaticRules.get(str) : null, zenModeConfig2.automaticRules != null ? zenModeConfig2.automaticRules.get(str) : null);
                if (ruleDiff.hasDiff()) {
                    this.mAutomaticRulesDiff.put(str, ruleDiff);
                }
            }
            RuleDiff ruleDiff2 = new RuleDiff(zenModeConfig.manualRule, zenModeConfig2.manualRule);
            if (ruleDiff2.hasDiff()) {
                this.mManualRuleDiff = ruleDiff2;
            }
        }

        private static <T> void addKeys(ArraySet<T> arraySet, ArrayMap<T, ?> arrayMap) {
            if (arrayMap != null) {
                for (int i = 0; i < arrayMap.size(); i++) {
                    arraySet.add(arrayMap.keyAt(i));
                }
            }
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public boolean hasDiff() {
            return hasExistenceChange() || hasFieldDiffs() || this.mManualRuleDiff != null || this.mAutomaticRulesDiff.size() > 0;
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public String toString() {
            StringBuilder sb = new StringBuilder("Diff[");
            if (!hasDiff()) {
                sb.append("no changes");
            }
            if (hasExistenceChange()) {
                if (wasAdded()) {
                    sb.append("added");
                } else if (wasRemoved()) {
                    sb.append(Environment.MEDIA_REMOVED);
                }
            }
            boolean z = true;
            for (String str : fieldNamesWithDiff()) {
                FieldDiff diffForField = getDiffForField(str);
                if (diffForField != null) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",\n");
                    }
                    if (PEOPLE_TYPE_FIELDS.contains(str)) {
                        sb.append(str);
                        sb.append(":");
                        sb.append(ZenModeConfig.sourceToString(((Integer) diffForField.from()).intValue()));
                        sb.append(Session.SUBSESSION_SEPARATION_CHAR);
                        sb.append(ZenModeConfig.sourceToString(((Integer) diffForField.to()).intValue()));
                    } else if (str.equals(FIELD_ALLOW_CONVERSATIONS_FROM)) {
                        sb.append(str);
                        sb.append(":");
                        sb.append(ZenPolicy.conversationTypeToString(((Integer) diffForField.from()).intValue()));
                        sb.append(Session.SUBSESSION_SEPARATION_CHAR);
                        sb.append(ZenPolicy.conversationTypeToString(((Integer) diffForField.to()).intValue()));
                    } else {
                        sb.append(str);
                        sb.append(":");
                        sb.append(diffForField);
                    }
                }
            }
            RuleDiff ruleDiff = this.mManualRuleDiff;
            if (ruleDiff != null && ruleDiff.hasDiff()) {
                if (z) {
                    z = false;
                } else {
                    sb.append(",\n");
                }
                sb.append("manualRule:");
                sb.append(this.mManualRuleDiff);
            }
            for (String str2 : this.mAutomaticRulesDiff.keySet()) {
                RuleDiff ruleDiff2 = this.mAutomaticRulesDiff.get(str2);
                if (ruleDiff2 != null && ruleDiff2.hasDiff()) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",\n");
                    }
                    sb.append("automaticRule[");
                    sb.append(str2);
                    sb.append("]:");
                    sb.append(ruleDiff2);
                }
            }
            sb.append(']');
            return sb.toString();
        }

        public RuleDiff getManualRuleDiff() {
            return this.mManualRuleDiff;
        }

        public ArrayMap<String, RuleDiff> getAllAutomaticRuleDiffs() {
            if (this.mAutomaticRulesDiff.size() > 0) {
                return this.mAutomaticRulesDiff;
            }
            return null;
        }
    }

    public static class RuleDiff extends BaseDiff {
        public static final String FIELD_ALLOW_MANUAL = "allowManualInvocation";
        public static final String FIELD_COMPONENT = "component";
        public static final String FIELD_CONDITION = "condition";
        public static final String FIELD_CONDITION_ID = "conditionId";
        public static final String FIELD_CONDITION_OVERRIDE = "conditionOverride";
        public static final String FIELD_CONFIGURATION_ACTIVITY = "configurationActivity";
        public static final String FIELD_CREATION_TIME = "creationTime";
        public static final String FIELD_ENABLED = "enabled";
        public static final String FIELD_ENABLER = "enabler";
        public static final String FIELD_ICON_RES = "iconResName";
        public static final String FIELD_ID = "id";
        public static final String FIELD_LEGACY_SUPPRESSED_EFFECTS = "legacySuppressedEffects";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_PKG = "pkg";

        @Deprecated
        public static final String FIELD_SNOOZING = "snoozing";
        public static final String FIELD_TRIGGER_DESCRIPTION = "triggerDescription";
        public static final String FIELD_TYPE = "type";
        public static final String FIELD_ZEN_DEVICE_EFFECTS = "zenDeviceEffects";
        public static final String FIELD_ZEN_MODE = "zenMode";
        public static final String FIELD_ZEN_POLICY = "zenPolicy";
        FieldDiff<Boolean> mActiveDiff;

        public RuleDiff(ZenModeConfig.ZenRule zenRule, ZenModeConfig.ZenRule zenRule2) {
            super(zenRule, zenRule2);
            if (zenRule == null && zenRule2 == null) {
                return;
            }
            boolean zIsActive = zenRule != null ? zenRule.isActive() : false;
            boolean zIsActive2 = zenRule2 != null ? zenRule2.isActive() : false;
            if (zIsActive != zIsActive2) {
                this.mActiveDiff = new FieldDiff<>(Boolean.valueOf(zIsActive), Boolean.valueOf(zIsActive2));
            }
            if (hasExistenceChange()) {
                return;
            }
            if (zenRule.enabled != zenRule2.enabled) {
                addField("enabled", new FieldDiff(Boolean.valueOf(zenRule.enabled), Boolean.valueOf(zenRule2.enabled)));
            }
            if (Flags.modesUi()) {
                if (zenRule.conditionOverride != zenRule2.conditionOverride) {
                    addField(FIELD_CONDITION_OVERRIDE, new FieldDiff(Integer.valueOf(zenRule.conditionOverride), Integer.valueOf(zenRule2.conditionOverride)));
                }
            } else if (zenRule.snoozing != zenRule2.snoozing) {
                addField(FIELD_SNOOZING, new FieldDiff(Boolean.valueOf(zenRule.snoozing), Boolean.valueOf(zenRule2.snoozing)));
            }
            if (!Objects.equals(zenRule.name, zenRule2.name)) {
                addField("name", new FieldDiff(zenRule.name, zenRule2.name));
            }
            if (zenRule.zenMode != zenRule2.zenMode) {
                addField(FIELD_ZEN_MODE, new FieldDiff(Integer.valueOf(zenRule.zenMode), Integer.valueOf(zenRule2.zenMode)));
            }
            if (!Objects.equals(zenRule.conditionId, zenRule2.conditionId)) {
                addField(FIELD_CONDITION_ID, new FieldDiff(zenRule.conditionId, zenRule2.conditionId));
            }
            if (!Objects.equals(zenRule.condition, zenRule2.condition)) {
                addField("condition", new FieldDiff(zenRule.condition, zenRule2.condition));
            }
            if (!Objects.equals(zenRule.component, zenRule2.component)) {
                addField("component", new FieldDiff(zenRule.component, zenRule2.component));
            }
            if (!Objects.equals(zenRule.configurationActivity, zenRule2.configurationActivity)) {
                addField(FIELD_CONFIGURATION_ACTIVITY, new FieldDiff(zenRule.configurationActivity, zenRule2.configurationActivity));
            }
            if (!Objects.equals(zenRule.id, zenRule2.id)) {
                addField("id", new FieldDiff(zenRule.id, zenRule2.id));
            }
            if (zenRule.creationTime != zenRule2.creationTime) {
                addField("creationTime", new FieldDiff(Long.valueOf(zenRule.creationTime), Long.valueOf(zenRule2.creationTime)));
            }
            if (!Objects.equals(zenRule.enabler, zenRule2.enabler)) {
                addField(FIELD_ENABLER, new FieldDiff(zenRule.enabler, zenRule2.enabler));
            }
            PolicyDiff policyDiff = new PolicyDiff(zenRule.zenPolicy, zenRule2.zenPolicy);
            if (policyDiff.hasDiff()) {
                addField(FIELD_ZEN_POLICY, new FieldDiff(zenRule.zenPolicy, zenRule2.zenPolicy, policyDiff));
            }
            if (!Objects.equals(zenRule.pkg, zenRule2.pkg)) {
                addField("pkg", new FieldDiff(zenRule.pkg, zenRule2.pkg));
            }
            DeviceEffectsDiff deviceEffectsDiff = new DeviceEffectsDiff(zenRule.zenDeviceEffects, zenRule2.zenDeviceEffects);
            if (deviceEffectsDiff.hasDiff()) {
                addField(FIELD_ZEN_DEVICE_EFFECTS, new FieldDiff(zenRule.zenDeviceEffects, zenRule2.zenDeviceEffects, deviceEffectsDiff));
            }
            if (!Objects.equals(zenRule.triggerDescription, zenRule2.triggerDescription)) {
                addField(FIELD_TRIGGER_DESCRIPTION, new FieldDiff(zenRule.triggerDescription, zenRule2.triggerDescription));
            }
            if (zenRule.type != zenRule2.type) {
                addField("type", new FieldDiff(Integer.valueOf(zenRule.type), Integer.valueOf(zenRule2.type)));
            }
            if (zenRule.allowManualInvocation != zenRule2.allowManualInvocation) {
                addField(FIELD_ALLOW_MANUAL, new FieldDiff(Boolean.valueOf(zenRule.allowManualInvocation), Boolean.valueOf(zenRule2.allowManualInvocation)));
            }
            if (!Objects.equals(zenRule.iconResName, zenRule2.iconResName)) {
                addField("iconResName", new FieldDiff(zenRule.iconResName, zenRule2.iconResName));
            }
            if (!Flags.modesUi() || zenRule.legacySuppressedEffects == zenRule2.legacySuppressedEffects) {
                return;
            }
            addField(FIELD_LEGACY_SUPPRESSED_EFFECTS, new FieldDiff(Integer.valueOf(zenRule.legacySuppressedEffects), Integer.valueOf(zenRule2.legacySuppressedEffects)));
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public boolean hasDiff() {
            return hasExistenceChange() || hasFieldDiffs();
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public String toString() {
            StringBuilder sb = new StringBuilder("ZenRuleDiff{");
            if (!hasDiff()) {
                sb.append("no changes");
            }
            if (hasExistenceChange()) {
                if (wasAdded()) {
                    sb.append("added");
                } else if (wasRemoved()) {
                    sb.append(Environment.MEDIA_REMOVED);
                }
            }
            boolean z = true;
            for (String str : fieldNamesWithDiff()) {
                FieldDiff diffForField = getDiffForField(str);
                if (diffForField != null) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(str);
                    sb.append(":");
                    sb.append(diffForField.toString());
                }
            }
            if (becameActive()) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append("(->active)");
            } else if (becameInactive()) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append("(->inactive)");
            }
            sb.append("}");
            return sb.toString();
        }

        public boolean becameActive() {
            FieldDiff<Boolean> fieldDiff = this.mActiveDiff;
            return fieldDiff != null && fieldDiff.to().booleanValue();
        }

        public boolean becameInactive() {
            FieldDiff<Boolean> fieldDiff = this.mActiveDiff;
            return (fieldDiff == null || fieldDiff.to().booleanValue()) ? false : true;
        }
    }

    public static class DeviceEffectsDiff extends BaseDiff {
        public static final String FIELD_DIM_WALLPAPER = "mDimWallpaper";
        public static final String FIELD_DISABLE_AUTO_BRIGHTNESS = "mDisableAutoBrightness";
        public static final String FIELD_DISABLE_TAP_TO_WAKE = "mDisableTapToWake";
        public static final String FIELD_DISABLE_TILT_TO_WAKE = "mDisableTiltToWake";
        public static final String FIELD_DISABLE_TOUCH = "mDisableTouch";
        public static final String FIELD_EXTRA_EFFECTS = "mExtraEffects";
        public static final String FIELD_GRAYSCALE = "mGrayscale";
        public static final String FIELD_MAXIMIZE_DOZE = "mMaximizeDoze";
        public static final String FIELD_MINIMIZE_RADIO_USAGE = "mMinimizeRadioUsage";
        public static final String FIELD_NIGHT_LIGHT = "mNightLight";
        public static final String FIELD_NIGHT_MODE = "mNightMode";
        public static final String FIELD_SUPPRESS_AMBIENT_DISPLAY = "mSuppressAmbientDisplay";

        public DeviceEffectsDiff(ZenDeviceEffects zenDeviceEffects, ZenDeviceEffects zenDeviceEffects2) {
            super(zenDeviceEffects, zenDeviceEffects2);
            if ((zenDeviceEffects == null && zenDeviceEffects2 == null) || hasExistenceChange()) {
                return;
            }
            if (zenDeviceEffects.shouldDisplayGrayscale() != zenDeviceEffects2.shouldDisplayGrayscale()) {
                addField(FIELD_GRAYSCALE, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldDisplayGrayscale()), Boolean.valueOf(zenDeviceEffects2.shouldDisplayGrayscale())));
            }
            if (zenDeviceEffects.shouldSuppressAmbientDisplay() != zenDeviceEffects2.shouldSuppressAmbientDisplay()) {
                addField(FIELD_SUPPRESS_AMBIENT_DISPLAY, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldSuppressAmbientDisplay()), Boolean.valueOf(zenDeviceEffects2.shouldSuppressAmbientDisplay())));
            }
            if (zenDeviceEffects.shouldDimWallpaper() != zenDeviceEffects2.shouldDimWallpaper()) {
                addField(FIELD_DIM_WALLPAPER, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldDimWallpaper()), Boolean.valueOf(zenDeviceEffects2.shouldDimWallpaper())));
            }
            if (zenDeviceEffects.shouldUseNightMode() != zenDeviceEffects2.shouldUseNightMode()) {
                addField(FIELD_NIGHT_MODE, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldUseNightMode()), Boolean.valueOf(zenDeviceEffects2.shouldUseNightMode())));
            }
            if (zenDeviceEffects.shouldDisableAutoBrightness() != zenDeviceEffects2.shouldDisableAutoBrightness()) {
                addField(FIELD_DISABLE_AUTO_BRIGHTNESS, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldDisableAutoBrightness()), Boolean.valueOf(zenDeviceEffects2.shouldDisableAutoBrightness())));
            }
            if (zenDeviceEffects.shouldDisableTapToWake() != zenDeviceEffects2.shouldDisableTapToWake()) {
                addField(FIELD_DISABLE_TAP_TO_WAKE, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldDisableTapToWake()), Boolean.valueOf(zenDeviceEffects2.shouldDisableTapToWake())));
            }
            if (zenDeviceEffects.shouldDisableTiltToWake() != zenDeviceEffects2.shouldDisableTiltToWake()) {
                addField(FIELD_DISABLE_TILT_TO_WAKE, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldDisableTiltToWake()), Boolean.valueOf(zenDeviceEffects2.shouldDisableTiltToWake())));
            }
            if (zenDeviceEffects.shouldDisableTouch() != zenDeviceEffects2.shouldDisableTouch()) {
                addField(FIELD_DISABLE_TOUCH, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldDisableTouch()), Boolean.valueOf(zenDeviceEffects2.shouldDisableTouch())));
            }
            if (zenDeviceEffects.shouldMinimizeRadioUsage() != zenDeviceEffects2.shouldMinimizeRadioUsage()) {
                addField(FIELD_MINIMIZE_RADIO_USAGE, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldMinimizeRadioUsage()), Boolean.valueOf(zenDeviceEffects2.shouldMinimizeRadioUsage())));
            }
            if (zenDeviceEffects.shouldMaximizeDoze() != zenDeviceEffects2.shouldMaximizeDoze()) {
                addField(FIELD_MAXIMIZE_DOZE, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldMaximizeDoze()), Boolean.valueOf(zenDeviceEffects2.shouldMaximizeDoze())));
            }
            if (zenDeviceEffects.shouldUseNightLight() != zenDeviceEffects2.shouldUseNightLight()) {
                addField(FIELD_NIGHT_LIGHT, new FieldDiff(Boolean.valueOf(zenDeviceEffects.shouldUseNightLight()), Boolean.valueOf(zenDeviceEffects2.shouldUseNightLight())));
            }
            if (Objects.equals(zenDeviceEffects.getExtraEffects(), zenDeviceEffects2.getExtraEffects())) {
                return;
            }
            addField(FIELD_EXTRA_EFFECTS, new FieldDiff(zenDeviceEffects.getExtraEffects(), zenDeviceEffects2.getExtraEffects()));
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public boolean hasDiff() {
            return hasExistenceChange() || hasFieldDiffs();
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public String toString() {
            StringBuilder sb = new StringBuilder("ZenDeviceEffectsDiff{");
            if (!hasDiff()) {
                sb.append("no changes");
            }
            if (hasExistenceChange()) {
                if (wasAdded()) {
                    sb.append("added");
                } else if (wasRemoved()) {
                    sb.append(Environment.MEDIA_REMOVED);
                }
            }
            boolean z = true;
            for (String str : fieldNamesWithDiff()) {
                FieldDiff diffForField = getDiffForField(str);
                if (diffForField != null) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(str);
                    sb.append(":");
                    sb.append(diffForField);
                }
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public static class PolicyDiff extends BaseDiff {
        public static final String FIELD_ALLOW_CHANNELS = "mAllowChannels";
        public static final String FIELD_CONVERSATION_SENDERS = "mConversationSenders";
        public static final String FIELD_PRIORITY_CALLS = "mPriorityCalls";
        public static final String FIELD_PRIORITY_CATEGORY_ALARMS = "mPriorityCategories_Alarms";
        public static final String FIELD_PRIORITY_CATEGORY_CALLS = "mPriorityCategories_Calls";
        public static final String FIELD_PRIORITY_CATEGORY_CONVERSATIONS = "mPriorityCategories_Conversations";
        public static final String FIELD_PRIORITY_CATEGORY_EVENTS = "mPriorityCategories_Events";
        public static final String FIELD_PRIORITY_CATEGORY_MEDIA = "mPriorityCategories_Media";
        public static final String FIELD_PRIORITY_CATEGORY_MESSAGES = "mPriorityCategories_Messages";
        public static final String FIELD_PRIORITY_CATEGORY_REMINDERS = "mPriorityCategories_Reminders";
        public static final String FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS = "mPriorityCategories_RepeatCallers";
        public static final String FIELD_PRIORITY_CATEGORY_SYSTEM = "mPriorityCategories_System";
        public static final String FIELD_PRIORITY_MESSAGES = "mPriorityMessages";
        public static final String FIELD_VISUAL_EFFECT_AMBIENT = "mVisualEffects_Ambient";
        public static final String FIELD_VISUAL_EFFECT_BADGE = "mVisualEffects_Badge";
        public static final String FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT = "mVisualEffects_FullScreenIntent";
        public static final String FIELD_VISUAL_EFFECT_LIGHTS = "mVisualEffects_Lights";
        public static final String FIELD_VISUAL_EFFECT_NOTIFICATION_LIST = "mVisualEffects_NotificationList";
        public static final String FIELD_VISUAL_EFFECT_PEEK = "mVisualEffects_Peek";
        public static final String FIELD_VISUAL_EFFECT_STATUS_BAR = "mVisualEffects_StatusBar";

        public PolicyDiff(ZenPolicy zenPolicy, ZenPolicy zenPolicy2) {
            super(zenPolicy, zenPolicy2);
            if ((zenPolicy == null && zenPolicy2 == null) || hasExistenceChange()) {
                return;
            }
            if (zenPolicy.getPriorityCategoryReminders() != zenPolicy2.getPriorityCategoryReminders()) {
                addField(FIELD_PRIORITY_CATEGORY_REMINDERS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryReminders()), Integer.valueOf(zenPolicy2.getPriorityCategoryReminders())));
            }
            if (zenPolicy.getPriorityCategoryEvents() != zenPolicy2.getPriorityCategoryEvents()) {
                addField(FIELD_PRIORITY_CATEGORY_EVENTS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryEvents()), Integer.valueOf(zenPolicy2.getPriorityCategoryEvents())));
            }
            if (zenPolicy.getPriorityCategoryMessages() != zenPolicy2.getPriorityCategoryMessages()) {
                addField(FIELD_PRIORITY_CATEGORY_MESSAGES, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryMessages()), Integer.valueOf(zenPolicy2.getPriorityCategoryMessages())));
            }
            if (zenPolicy.getPriorityCategoryCalls() != zenPolicy2.getPriorityCategoryCalls()) {
                addField(FIELD_PRIORITY_CATEGORY_CALLS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryCalls()), Integer.valueOf(zenPolicy2.getPriorityCategoryCalls())));
            }
            if (zenPolicy.getPriorityCategoryRepeatCallers() != zenPolicy2.getPriorityCategoryRepeatCallers()) {
                addField(FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryRepeatCallers()), Integer.valueOf(zenPolicy2.getPriorityCategoryRepeatCallers())));
            }
            if (zenPolicy.getPriorityCategoryAlarms() != zenPolicy2.getPriorityCategoryAlarms()) {
                addField(FIELD_PRIORITY_CATEGORY_ALARMS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryAlarms()), Integer.valueOf(zenPolicy2.getPriorityCategoryAlarms())));
            }
            if (zenPolicy.getPriorityCategoryMedia() != zenPolicy2.getPriorityCategoryMedia()) {
                addField(FIELD_PRIORITY_CATEGORY_MEDIA, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryMedia()), Integer.valueOf(zenPolicy2.getPriorityCategoryMedia())));
            }
            if (zenPolicy.getPriorityCategorySystem() != zenPolicy2.getPriorityCategorySystem()) {
                addField(FIELD_PRIORITY_CATEGORY_SYSTEM, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategorySystem()), Integer.valueOf(zenPolicy2.getPriorityCategorySystem())));
            }
            if (zenPolicy.getPriorityCategoryConversations() != zenPolicy2.getPriorityCategoryConversations()) {
                addField(FIELD_PRIORITY_CATEGORY_CONVERSATIONS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCategoryConversations()), Integer.valueOf(zenPolicy2.getPriorityCategoryConversations())));
            }
            if (zenPolicy.getVisualEffectFullScreenIntent() != zenPolicy2.getVisualEffectFullScreenIntent()) {
                addField(FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectFullScreenIntent()), Integer.valueOf(zenPolicy2.getVisualEffectFullScreenIntent())));
            }
            if (zenPolicy.getVisualEffectLights() != zenPolicy2.getVisualEffectLights()) {
                addField(FIELD_VISUAL_EFFECT_LIGHTS, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectLights()), Integer.valueOf(zenPolicy2.getVisualEffectLights())));
            }
            if (zenPolicy.getVisualEffectPeek() != zenPolicy2.getVisualEffectPeek()) {
                addField(FIELD_VISUAL_EFFECT_PEEK, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectPeek()), Integer.valueOf(zenPolicy2.getVisualEffectPeek())));
            }
            if (zenPolicy.getVisualEffectStatusBar() != zenPolicy2.getVisualEffectStatusBar()) {
                addField(FIELD_VISUAL_EFFECT_STATUS_BAR, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectStatusBar()), Integer.valueOf(zenPolicy2.getVisualEffectStatusBar())));
            }
            if (zenPolicy.getVisualEffectBadge() != zenPolicy2.getVisualEffectBadge()) {
                addField(FIELD_VISUAL_EFFECT_BADGE, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectBadge()), Integer.valueOf(zenPolicy2.getVisualEffectBadge())));
            }
            if (zenPolicy.getVisualEffectAmbient() != zenPolicy2.getVisualEffectAmbient()) {
                addField(FIELD_VISUAL_EFFECT_AMBIENT, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectAmbient()), Integer.valueOf(zenPolicy2.getVisualEffectAmbient())));
            }
            if (zenPolicy.getVisualEffectNotificationList() != zenPolicy2.getVisualEffectNotificationList()) {
                addField(FIELD_VISUAL_EFFECT_NOTIFICATION_LIST, new FieldDiff(Integer.valueOf(zenPolicy.getVisualEffectNotificationList()), Integer.valueOf(zenPolicy2.getVisualEffectNotificationList())));
            }
            if (zenPolicy.getPriorityMessageSenders() != zenPolicy2.getPriorityMessageSenders()) {
                addField(FIELD_PRIORITY_MESSAGES, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityMessageSenders()), Integer.valueOf(zenPolicy2.getPriorityMessageSenders())));
            }
            if (zenPolicy.getPriorityCallSenders() != zenPolicy2.getPriorityCallSenders()) {
                addField(FIELD_PRIORITY_CALLS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityCallSenders()), Integer.valueOf(zenPolicy2.getPriorityCallSenders())));
            }
            if (zenPolicy.getPriorityConversationSenders() != zenPolicy2.getPriorityConversationSenders()) {
                addField(FIELD_CONVERSATION_SENDERS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityConversationSenders()), Integer.valueOf(zenPolicy2.getPriorityConversationSenders())));
            }
            if (zenPolicy.getPriorityChannelsAllowed() != zenPolicy2.getPriorityChannelsAllowed()) {
                addField(FIELD_ALLOW_CHANNELS, new FieldDiff(Integer.valueOf(zenPolicy.getPriorityChannelsAllowed()), Integer.valueOf(zenPolicy2.getPriorityChannelsAllowed())));
            }
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public boolean hasDiff() {
            return hasExistenceChange() || hasFieldDiffs();
        }

        @Override // android.service.notification.ZenModeDiff.BaseDiff
        public String toString() {
            StringBuilder sb = new StringBuilder("ZenPolicyDiff{");
            if (!hasDiff()) {
                sb.append("no changes");
            }
            if (hasExistenceChange()) {
                if (wasAdded()) {
                    sb.append("added");
                } else if (wasRemoved()) {
                    sb.append(Environment.MEDIA_REMOVED);
                }
            }
            boolean z = true;
            for (String str : fieldNamesWithDiff()) {
                FieldDiff diffForField = getDiffForField(str);
                if (diffForField != null) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(str);
                    sb.append(":");
                    sb.append(diffForField);
                }
            }
            sb.append("}");
            return sb.toString();
        }
    }
}
