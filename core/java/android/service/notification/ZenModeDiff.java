package android.service.notification;

import android.os.Environment;
import android.telecom.Logging.Session;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class ZenModeDiff {
  public static final int ADDED = 1;
  public static final int NONE = 0;
  public static final int REMOVED = 2;

  @Retention(RetentionPolicy.SOURCE)
  public @interface ExistenceChange {}

  public static class FieldDiff<T> {
    private final T mFrom;
    private final T mTo;

    public FieldDiff(T from, T to) {
      this.mFrom = from;
      this.mTo = to;
    }

    public T from() {
      return this.mFrom;
    }

    /* renamed from: to */
    public T m66to() {
      return this.mTo;
    }

    public String toString() {
      return this.mFrom + Session.SUBSESSION_SEPARATION_CHAR + this.mTo;
    }

    public boolean hasDiff() {
      return !Objects.equals(this.mFrom, this.mTo);
    }
  }

  private abstract static class BaseDiff {
    private int mExists;
    private ArrayMap<String, FieldDiff> mFields = new ArrayMap<>();

    public abstract boolean hasDiff();

    public abstract String toString();

    BaseDiff(Object from, Object to) {
      this.mExists = 0;
      if (from == null) {
        if (to != null) {
          this.mExists = 1;
        }
      } else if (to == null) {
        this.mExists = 2;
      }
    }

    final void addField(String name, FieldDiff diff) {
      this.mFields.put(name, diff);
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

    public final FieldDiff getDiffForField(String name) {
      return this.mFields.getOrDefault(name, null);
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
    public static final String FIELD_ALLOW_REMINDERS = "allowReminders";
    public static final String FIELD_ALLOW_REPEAT_CALLERS = "allowRepeatCallers";
    public static final String FIELD_ALLOW_SYSTEM = "allowSystem";
    public static final String FIELD_ARE_CHANNELS_BYPASSING_DND = "areChannelsBypassingDnd";
    public static final String FIELD_SUPPRESSED_VISUAL_EFFECTS = "suppressedVisualEffects";
    public static final String FIELD_USER = "user";
    private final ArrayMap<String, RuleDiff> mAutomaticRulesDiff;
    private RuleDiff mManualRuleDiff;
    public static final String FIELD_ALLOW_CALLS_FROM = "allowCallsFrom";
    public static final String FIELD_ALLOW_MESSAGES_FROM = "allowMessagesFrom";
    private static final Set<String> PEOPLE_TYPE_FIELDS =
        Set.of(FIELD_ALLOW_CALLS_FROM, FIELD_ALLOW_MESSAGES_FROM);

    public ConfigDiff(ZenModeConfig from, ZenModeConfig to) {
      super(from, to);
      this.mAutomaticRulesDiff = new ArrayMap<>();
      if ((from == null && to == null) || hasExistenceChange()) {
        return;
      }
      if (from.user != to.user) {
        addField("user", new FieldDiff(Integer.valueOf(from.user), Integer.valueOf(to.user)));
      }
      if (from.allowAlarms != to.allowAlarms) {
        addField(
            FIELD_ALLOW_ALARMS,
            new FieldDiff(Boolean.valueOf(from.allowAlarms), Boolean.valueOf(to.allowAlarms)));
      }
      if (from.allowMedia != to.allowMedia) {
        addField(
            FIELD_ALLOW_MEDIA,
            new FieldDiff(Boolean.valueOf(from.allowMedia), Boolean.valueOf(to.allowMedia)));
      }
      if (from.allowSystem != to.allowSystem) {
        addField(
            FIELD_ALLOW_SYSTEM,
            new FieldDiff(Boolean.valueOf(from.allowSystem), Boolean.valueOf(to.allowSystem)));
      }
      if (from.allowCalls != to.allowCalls) {
        addField(
            FIELD_ALLOW_CALLS,
            new FieldDiff(Boolean.valueOf(from.allowCalls), Boolean.valueOf(to.allowCalls)));
      }
      if (from.allowReminders != to.allowReminders) {
        addField(
            FIELD_ALLOW_REMINDERS,
            new FieldDiff(
                Boolean.valueOf(from.allowReminders), Boolean.valueOf(to.allowReminders)));
      }
      if (from.allowEvents != to.allowEvents) {
        addField(
            FIELD_ALLOW_EVENTS,
            new FieldDiff(Boolean.valueOf(from.allowEvents), Boolean.valueOf(to.allowEvents)));
      }
      if (from.allowRepeatCallers != to.allowRepeatCallers) {
        addField(
            FIELD_ALLOW_REPEAT_CALLERS,
            new FieldDiff(
                Boolean.valueOf(from.allowRepeatCallers), Boolean.valueOf(to.allowRepeatCallers)));
      }
      if (from.allowMessages != to.allowMessages) {
        addField(
            FIELD_ALLOW_MESSAGES,
            new FieldDiff(Boolean.valueOf(from.allowMessages), Boolean.valueOf(to.allowMessages)));
      }
      if (from.allowConversations != to.allowConversations) {
        addField(
            FIELD_ALLOW_CONVERSATIONS,
            new FieldDiff(
                Boolean.valueOf(from.allowConversations), Boolean.valueOf(to.allowConversations)));
      }
      if (from.allowCallsFrom != to.allowCallsFrom) {
        addField(
            FIELD_ALLOW_CALLS_FROM,
            new FieldDiff(
                Integer.valueOf(from.allowCallsFrom), Integer.valueOf(to.allowCallsFrom)));
      }
      if (from.allowMessagesFrom != to.allowMessagesFrom) {
        addField(
            FIELD_ALLOW_MESSAGES_FROM,
            new FieldDiff(
                Integer.valueOf(from.allowMessagesFrom), Integer.valueOf(to.allowMessagesFrom)));
      }
      if (from.allowConversationsFrom != to.allowConversationsFrom) {
        addField(
            FIELD_ALLOW_CONVERSATIONS_FROM,
            new FieldDiff(
                Integer.valueOf(from.allowConversationsFrom),
                Integer.valueOf(to.allowConversationsFrom)));
      }
      if (from.suppressedVisualEffects != to.suppressedVisualEffects) {
        addField(
            FIELD_SUPPRESSED_VISUAL_EFFECTS,
            new FieldDiff(
                Integer.valueOf(from.suppressedVisualEffects),
                Integer.valueOf(to.suppressedVisualEffects)));
      }
      if (from.areChannelsBypassingDnd != to.areChannelsBypassingDnd) {
        addField(
            FIELD_ARE_CHANNELS_BYPASSING_DND,
            new FieldDiff(
                Boolean.valueOf(from.areChannelsBypassingDnd),
                Boolean.valueOf(to.areChannelsBypassingDnd)));
      }
      ArraySet<String> allRules = new ArraySet<>();
      addKeys(allRules, from.automaticRules);
      addKeys(allRules, to.automaticRules);
      int num = allRules.size();
      for (int i = 0; i < num; i++) {
        String rule = allRules.valueAt(i);
        ZenModeConfig.ZenRule fromRule =
            from.automaticRules != null ? from.automaticRules.get(rule) : null;
        ZenModeConfig.ZenRule toRule =
            to.automaticRules != null ? to.automaticRules.get(rule) : null;
        RuleDiff ruleDiff = new RuleDiff(fromRule, toRule);
        if (ruleDiff.hasDiff()) {
          this.mAutomaticRulesDiff.put(rule, ruleDiff);
        }
      }
      RuleDiff manualRuleDiff = new RuleDiff(from.manualRule, to.manualRule);
      if (manualRuleDiff.hasDiff()) {
        this.mManualRuleDiff = manualRuleDiff;
      }
    }

    private static <T> void addKeys(ArraySet<T> set, ArrayMap<T, ?> map) {
      if (map != null) {
        for (int i = 0; i < map.size(); i++) {
          set.add(map.keyAt(i));
        }
      }
    }

    @Override // android.service.notification.ZenModeDiff.BaseDiff
    public boolean hasDiff() {
      return hasExistenceChange()
          || hasFieldDiffs()
          || this.mManualRuleDiff != null
          || this.mAutomaticRulesDiff.size() > 0;
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
      boolean first = true;
      for (String key : fieldNamesWithDiff()) {
        FieldDiff diff = getDiffForField(key);
        if (diff != null) {
          if (first) {
            first = false;
          } else {
            sb.append(",\n");
          }
          if (PEOPLE_TYPE_FIELDS.contains(key)) {
            sb.append(key);
            sb.append(":");
            sb.append(ZenModeConfig.sourceToString(((Integer) diff.from()).intValue()));
            sb.append(Session.SUBSESSION_SEPARATION_CHAR);
            sb.append(ZenModeConfig.sourceToString(((Integer) diff.m66to()).intValue()));
          } else if (key.equals(FIELD_ALLOW_CONVERSATIONS_FROM)) {
            sb.append(key);
            sb.append(":");
            sb.append(ZenPolicy.conversationTypeToString(((Integer) diff.from()).intValue()));
            sb.append(Session.SUBSESSION_SEPARATION_CHAR);
            sb.append(ZenPolicy.conversationTypeToString(((Integer) diff.m66to()).intValue()));
          } else {
            sb.append(key);
            sb.append(":");
            sb.append(diff);
          }
        }
      }
      RuleDiff ruleDiff = this.mManualRuleDiff;
      if (ruleDiff != null && ruleDiff.hasDiff()) {
        if (first) {
          first = false;
        } else {
          sb.append(",\n");
        }
        sb.append("manualRule:");
        sb.append(this.mManualRuleDiff);
      }
      for (String rule : this.mAutomaticRulesDiff.keySet()) {
        RuleDiff diff2 = this.mAutomaticRulesDiff.get(rule);
        if (diff2 != null && diff2.hasDiff()) {
          if (first) {
            first = false;
          } else {
            sb.append(",\n");
          }
          sb.append("automaticRule[");
          sb.append(rule);
          sb.append("]:");
          sb.append(diff2);
        }
      }
      return sb.append(']').toString();
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
    public static final String FIELD_COMPONENT = "component";
    public static final String FIELD_CONDITION = "condition";
    public static final String FIELD_CONDITION_ID = "conditionId";
    public static final String FIELD_CONFIGURATION_ACTIVITY = "configurationActivity";
    public static final String FIELD_CREATION_TIME = "creationTime";
    public static final String FIELD_ENABLED = "enabled";
    public static final String FIELD_ENABLER = "enabler";
    public static final String FIELD_ID = "id";
    public static final String FIELD_MODIFIED = "modified";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PKG = "pkg";
    public static final String FIELD_SNOOZING = "snoozing";
    public static final String FIELD_ZEN_MODE = "zenMode";
    public static final String FIELD_ZEN_POLICY = "zenPolicy";
    FieldDiff<Boolean> mActiveDiff;

    public RuleDiff(ZenModeConfig.ZenRule from, ZenModeConfig.ZenRule to) {
      super(from, to);
      if (from == null && to == null) {
        return;
      }
      boolean fromActive = from != null ? from.isAutomaticActive() : false;
      boolean toActive = to != null ? to.isAutomaticActive() : false;
      if (fromActive != toActive) {
        this.mActiveDiff = new FieldDiff<>(Boolean.valueOf(fromActive), Boolean.valueOf(toActive));
      }
      if (hasExistenceChange()) {
        return;
      }
      if (from.enabled != to.enabled) {
        addField(
            "enabled", new FieldDiff(Boolean.valueOf(from.enabled), Boolean.valueOf(to.enabled)));
      }
      if (from.snoozing != to.snoozing) {
        addField(
            FIELD_SNOOZING,
            new FieldDiff(Boolean.valueOf(from.snoozing), Boolean.valueOf(to.snoozing)));
      }
      if (!Objects.equals(from.name, to.name)) {
        addField("name", new FieldDiff(from.name, to.name));
      }
      if (from.zenMode != to.zenMode) {
        addField(
            FIELD_ZEN_MODE,
            new FieldDiff(Integer.valueOf(from.zenMode), Integer.valueOf(to.zenMode)));
      }
      if (!Objects.equals(from.conditionId, to.conditionId)) {
        addField(FIELD_CONDITION_ID, new FieldDiff(from.conditionId, to.conditionId));
      }
      if (!Objects.equals(from.condition, to.condition)) {
        addField("condition", new FieldDiff(from.condition, to.condition));
      }
      if (!Objects.equals(from.component, to.component)) {
        addField("component", new FieldDiff(from.component, to.component));
      }
      if (!Objects.equals(from.configurationActivity, to.configurationActivity)) {
        addField(
            FIELD_CONFIGURATION_ACTIVITY,
            new FieldDiff(from.configurationActivity, to.configurationActivity));
      }
      if (!Objects.equals(from.f482id, to.f482id)) {
        addField("id", new FieldDiff(from.f482id, to.f482id));
      }
      if (from.creationTime != to.creationTime) {
        addField(
            "creationTime",
            new FieldDiff(Long.valueOf(from.creationTime), Long.valueOf(to.creationTime)));
      }
      if (!Objects.equals(from.enabler, to.enabler)) {
        addField(FIELD_ENABLER, new FieldDiff(from.enabler, to.enabler));
      }
      if (!Objects.equals(from.zenPolicy, to.zenPolicy)) {
        addField(FIELD_ZEN_POLICY, new FieldDiff(from.zenPolicy, to.zenPolicy));
      }
      if (from.modified != to.modified) {
        addField(
            "modified",
            new FieldDiff(Boolean.valueOf(from.modified), Boolean.valueOf(to.modified)));
      }
      if (!Objects.equals(from.pkg, to.pkg)) {
        addField("pkg", new FieldDiff(from.pkg, to.pkg));
      }
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
      boolean first = true;
      for (String key : fieldNamesWithDiff()) {
        FieldDiff diff = getDiffForField(key);
        if (diff != null) {
          if (first) {
            first = false;
          } else {
            sb.append(", ");
          }
          sb.append(key);
          sb.append(":");
          sb.append(diff);
        }
      }
      if (becameActive()) {
        if (!first) {
          sb.append(", ");
        }
        sb.append("(->active)");
      } else if (becameInactive()) {
        if (!first) {
          sb.append(", ");
        }
        sb.append("(->inactive)");
      }
      return sb.append("}").toString();
    }

    public boolean becameActive() {
      FieldDiff<Boolean> fieldDiff = this.mActiveDiff;
      return fieldDiff != null && fieldDiff.m66to().booleanValue();
    }

    public boolean becameInactive() {
      FieldDiff<Boolean> fieldDiff = this.mActiveDiff;
      return (fieldDiff == null || fieldDiff.m66to().booleanValue()) ? false : true;
    }
  }
}
