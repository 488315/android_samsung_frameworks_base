package android.service.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AutomaticZenRule;
import android.app.Flags;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Contacts;
import android.provider.Settings;
import android.service.notification.ZenDeviceEffects;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.NtpTrustedTime;
import android.util.PluralsMessageFormatter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.samsung.android.knox.analytics.database.Contract;
import com.sec.android.iaft.SmLib_IafdConstant;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class ZenModeConfig implements Parcelable {
    private static final String ALLOW_ATT_ALARMS = "alarms";
    private static final String ALLOW_ATT_APP_BYPASS_DND_LIST = "appBypassDndList";
    private static final String ALLOW_ATT_CALLS = "calls";
    private static final String ALLOW_ATT_CALLS_FROM = "callsFrom";
    private static final String ALLOW_ATT_CHANNELS = "priorityChannelsAllowed";
    private static final String ALLOW_ATT_CONV = "convos";
    private static final String ALLOW_ATT_CONV_FROM = "convosFrom";
    private static final String ALLOW_ATT_EVENTS = "events";
    private static final String ALLOW_ATT_EXCEPTION_CONTACTS = "exceptionContacts";
    private static final String ALLOW_ATT_FROM = "from";
    private static final String ALLOW_ATT_MEDIA = "media";
    private static final String ALLOW_ATT_MESSAGES = "messages";
    private static final String ALLOW_ATT_MESSAGES_FROM = "messagesFrom";
    private static final String ALLOW_ATT_REMINDERS = "reminders";
    private static final String ALLOW_ATT_REPEAT_CALLERS = "repeatCallers";
    private static final String ALLOW_ATT_SCREEN_OFF = "visualScreenOff";
    private static final String ALLOW_ATT_SCREEN_ON = "visualScreenOn";
    private static final String ALLOW_ATT_SYSTEM = "system";
    private static final String ALLOW_TAG = "allow";
    private static final String ATT_SELECTED_APPS_ALLOWED = "appBypassDndFlag";
    private static final String ATT_SELECTED_CONTACTS_ALLOWED = "exceptionContactsFlag";
    private static final String AUTOMATIC_DELETED_TAG = "deleted";
    private static final String AUTOMATIC_TAG = "automatic";
    private static final String CONDITION_ATT_FLAGS = "flags";
    private static final String CONDITION_ATT_ICON = "icon";
    private static final String CONDITION_ATT_ID = "id";
    private static final String CONDITION_ATT_LINE1 = "line1";
    private static final String CONDITION_ATT_LINE2 = "line2";
    private static final String CONDITION_ATT_SOURCE = "source";
    private static final String CONDITION_ATT_STATE = "state";
    private static final String CONDITION_ATT_SUMMARY = "summary";
    public static final String COUNTDOWN_PATH = "countdown";
    private static final int DAY_MINUTES = 1440;
    private static final boolean DEFAULT_ALLOW_ALARMS = true;
    private static final boolean DEFAULT_ALLOW_CALLS = true;
    private static final boolean DEFAULT_ALLOW_CONV = true;
    private static final int DEFAULT_ALLOW_CONV_FROM = 2;
    private static final boolean DEFAULT_ALLOW_EVENTS = false;
    private static final boolean DEFAULT_ALLOW_MEDIA = true;
    private static final boolean DEFAULT_ALLOW_MESSAGES = true;
    private static final boolean DEFAULT_ALLOW_PRIORITY_CHANNELS = true;
    private static final boolean DEFAULT_ALLOW_REMINDERS = false;
    private static final boolean DEFAULT_ALLOW_REPEAT_CALLERS = true;
    private static final boolean DEFAULT_ALLOW_SYSTEM = false;
    private static final int DEFAULT_CALLS_SOURCE = 2;
    private static final boolean DEFAULT_CHANNELS_BYPASSING_DND = false;
    private static final int DEFAULT_SELECTED_APPS_ALLOWED = 0;
    private static final int DEFAULT_SELECTED_CONTACTS_ALLOWED = 0;
    private static final int DEFAULT_SOURCE = 2;
    private static final int DEFAULT_SUPPRESSED_VISUAL_EFFECTS = 157;
    private static final String DEVICE_EFFECT_DIM_WALLPAPER = "zdeDimWallpaper";
    private static final String DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS = "zdeDisableAutoBrightness";
    private static final String DEVICE_EFFECT_DISABLE_TAP_TO_WAKE = "zdeDisableTapToWake";
    private static final String DEVICE_EFFECT_DISABLE_TILT_TO_WAKE = "zdeDisableTiltToWake";
    private static final String DEVICE_EFFECT_DISABLE_TOUCH = "zdeDisableTouch";
    private static final String DEVICE_EFFECT_DISPLAY_GRAYSCALE = "zdeDisplayGrayscale";
    private static final String DEVICE_EFFECT_EXTRAS = "zdeExtraEffects";
    private static final String DEVICE_EFFECT_MAXIMIZE_DOZE = "zdeMaximizeDoze";
    private static final String DEVICE_EFFECT_MINIMIZE_RADIO_USAGE = "zdeMinimizeRadioUsage";
    private static final String DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY = "zdeSuppressAmbientDisplay";
    private static final String DEVICE_EFFECT_USER_MODIFIED_FIELDS = "zdeUserModifiedFields";
    private static final String DEVICE_EFFECT_USE_NIGHT_MODE = "zdeUseNightMode";
    private static final String DISALLOW_ATT_VISUAL_EFFECTS = "visualEffects";
    private static final String DISALLOW_TAG = "disallow";
    public static final String EVENT_PATH = "event";
    public static final String IS_ALARM_PATH = "alarm";
    private static final String ITEM_SEPARATOR = ",";
    private static final String ITEM_SEPARATOR_ESCAPE = "\\";
    public static final String MANUAL_RULE_ID = "MANUAL_RULE";
    private static final String MANUAL_TAG = "manual";
    public static final int MAX_SOURCE = 2;
    private static final int MINUTES_MS = 60000;
    private static final String POLICY_USER_MODIFIED_FIELDS = "policyUserModifiedFields";
    private static final String RULE_ATT_ALLOW_MANUAL = "userInvokable";
    private static final String RULE_ATT_COMPONENT = "component";
    private static final String RULE_ATT_CONDITION_ID = "conditionId";
    private static final String RULE_ATT_CONFIG_ACTIVITY = "configActivity";
    private static final String RULE_ATT_CREATION_TIME = "creationTime";
    private static final String RULE_ATT_DELETION_INSTANT = "deletionInstant";
    private static final String RULE_ATT_ENABLED = "enabled";
    private static final String RULE_ATT_ENABLER = "enabler";
    private static final String RULE_ATT_ICON = "rule_icon";
    private static final String RULE_ATT_ID = "ruleId";
    private static final String RULE_ATT_MODIFIED = "modified";
    private static final String RULE_ATT_NAME = "name";
    private static final String RULE_ATT_PKG = "pkg";
    private static final String RULE_ATT_SNOOZING = "snoozing";
    private static final String RULE_ATT_TRIGGER_DESC = "triggerDesc";
    private static final String RULE_ATT_TYPE = "type";
    private static final String RULE_ATT_USER_MODIFIED_FIELDS = "userModifiedFields";
    private static final String RULE_ATT_ZEN = "zen";
    public static final String SCHEDULE_PATH = "schedule";
    private static final int SECONDS_MS = 1000;
    private static final String SHOW_ATT_AMBIENT = "showAmbient";
    private static final String SHOW_ATT_BADGES = "showBadges";
    private static final String SHOW_ATT_FULL_SCREEN_INTENT = "showFullScreenIntent";
    private static final String SHOW_ATT_LIGHTS = "showLights";
    private static final String SHOW_ATT_NOTIFICATION_LIST = "showNotificationList";
    private static final String SHOW_ATT_PEEK = "shoePeek";
    private static final String SHOW_ATT_STATUS_BAR_ICONS = "showStatusBarIcons";
    public static final int SOURCE_ANYONE = 0;
    public static final int SOURCE_CONTACT = 1;
    public static final int SOURCE_STAR = 2;
    private static final String STATE_ATT_CHANNELS_BYPASSING_DND = "areChannelsBypassingDnd";
    private static final String STATE_TAG = "state";
    public static final String SYSTEM_AUTHORITY = "android";
    private static final String TAG = "ZenModeConfig";
    public static final String TW_SCHEDULED_DEFAULT_RULE_ID = "SCHEDULED_DEFAULT_RULE";
    public static final int UPDATE_ORIGIN_APP = 4;
    public static final int UPDATE_ORIGIN_INIT = 1;
    public static final int UPDATE_ORIGIN_INIT_USER = 2;
    public static final int UPDATE_ORIGIN_RESTORE_BACKUP = 6;
    public static final int UPDATE_ORIGIN_SYSTEM_OR_SYSTEMUI = 5;
    public static final int UPDATE_ORIGIN_UNKNOWN = 0;
    public static final int UPDATE_ORIGIN_USER = 3;
    private static final int XML_VERSION = 10;
    public static final int XML_VERSION_MODES_API = 11;
    public static final int XML_VERSION_ZEN_UPGRADE = 8;
    private static final String ZEN_ATT_USER = "user";
    private static final String ZEN_ATT_VERSION = "version";
    private static final String ZEN_POLICY_TAG = "zen_policy";
    public static final String ZEN_TAG = "zen";
    private static final int ZERO_VALUE_MS = 10000;
    public boolean allowAlarms;
    public List<String> allowAppBypassDndList;
    public boolean allowCalls;
    public int allowCallsFrom;
    public boolean allowConversations;
    public int allowConversationsFrom;
    public boolean allowEvents;
    public List<String> allowExceptionContacts;
    public boolean allowMedia;
    public boolean allowMessages;
    public int allowMessagesFrom;
    public boolean allowPriorityChannels;
    public boolean allowReminders;
    public boolean allowRepeatCallers;
    public boolean allowSystem;
    public int appBypassDndFlag;
    public boolean areChannelsBypassingDnd;
    public ArrayMap<String, ZenRule> automaticRules;
    public final ArrayMap<String, ZenRule> deletedRules;
    public int exceptionContactsFlag;
    public ZenRule manualRule;
    public int suppressedVisualEffects;
    public int user;
    public int version;
    public static final String EVERY_NIGHT_DEFAULT_RULE_ID = "EVERY_NIGHT_DEFAULT_RULE";
    public static final String EVENTS_DEFAULT_RULE_ID = "EVENTS_DEFAULT_RULE";
    public static final List<String> DEFAULT_RULE_IDS = Arrays.asList(EVERY_NIGHT_DEFAULT_RULE_ID, EVENTS_DEFAULT_RULE_ID);
    public static final int[] ALL_DAYS = {1, 2, 3, 4, 5, 6, 7};
    public static final int[] MINUTE_BUCKETS = generateMinuteBuckets();
    public static final Object ZenConfigLock = new Object();
    private static final Pattern ITEM_SPLITTER_REGEX = Pattern.compile("(?<!\\\\),");
    public static final Parcelable.Creator<ZenModeConfig> CREATOR = new Parcelable.Creator<ZenModeConfig>() { // from class: android.service.notification.ZenModeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenModeConfig createFromParcel(Parcel source) {
            return new ZenModeConfig(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenModeConfig[] newArray(int size) {
            return new ZenModeConfig[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigChangeOrigin {
    }

    public ZenModeConfig() {
        this.allowAlarms = true;
        this.allowMedia = true;
        this.allowSystem = false;
        this.allowCalls = true;
        this.allowRepeatCallers = true;
        this.allowMessages = true;
        this.allowReminders = false;
        this.allowEvents = false;
        this.allowCallsFrom = 2;
        this.allowMessagesFrom = 2;
        this.allowConversations = true;
        this.allowConversationsFrom = 2;
        this.exceptionContactsFlag = 0;
        this.appBypassDndFlag = 0;
        this.user = 0;
        this.suppressedVisualEffects = 157;
        this.areChannelsBypassingDnd = false;
        this.allowPriorityChannels = true;
        this.automaticRules = new ArrayMap<>();
        this.deletedRules = new ArrayMap<>();
        if (Flags.modesUi()) {
            ensureManualZenRule();
        }
    }

    public ZenModeConfig(Parcel source) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        this.allowAlarms = true;
        this.allowMedia = true;
        this.allowSystem = false;
        this.allowCalls = true;
        this.allowRepeatCallers = true;
        this.allowMessages = true;
        this.allowReminders = false;
        this.allowEvents = false;
        this.allowCallsFrom = 2;
        this.allowMessagesFrom = 2;
        this.allowConversations = true;
        this.allowConversationsFrom = 2;
        this.exceptionContactsFlag = 0;
        this.appBypassDndFlag = 0;
        this.user = 0;
        this.suppressedVisualEffects = 157;
        this.areChannelsBypassingDnd = false;
        this.allowPriorityChannels = true;
        this.automaticRules = new ArrayMap<>();
        this.deletedRules = new ArrayMap<>();
        if (!Flags.modesUi()) {
            if (source.readInt() == 1) {
                z4 = true;
            } else {
                z4 = false;
            }
            this.allowCalls = z4;
            if (source.readInt() == 1) {
                z5 = true;
            } else {
                z5 = false;
            }
            this.allowRepeatCallers = z5;
            if (source.readInt() == 1) {
                z6 = true;
            } else {
                z6 = false;
            }
            this.allowMessages = z6;
            if (source.readInt() == 1) {
                z7 = true;
            } else {
                z7 = false;
            }
            this.allowReminders = z7;
            if (source.readInt() == 1) {
                z8 = true;
            } else {
                z8 = false;
            }
            this.allowEvents = z8;
            this.allowCallsFrom = source.readInt();
            this.allowMessagesFrom = source.readInt();
        }
        this.user = source.readInt();
        this.manualRule = (ZenRule) source.readParcelable(null, ZenRule.class);
        readRulesFromParcel(this.automaticRules, source);
        if (Flags.modesApi()) {
            readRulesFromParcel(this.deletedRules, source);
        }
        if (!Flags.modesUi()) {
            if (source.readInt() == 1) {
                z = true;
            } else {
                z = false;
            }
            this.allowAlarms = z;
            if (source.readInt() == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.allowMedia = z2;
            if (source.readInt() == 1) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.allowSystem = z3;
            this.suppressedVisualEffects = source.readInt();
        }
        this.areChannelsBypassingDnd = source.readInt() == 1;
        this.exceptionContactsFlag = source.readInt();
        this.allowExceptionContacts = source.createStringArrayList();
        this.appBypassDndFlag = source.readInt();
        this.allowAppBypassDndList = source.createStringArrayList();
        if (!Flags.modesUi()) {
            this.allowConversations = source.readBoolean();
            this.allowConversationsFrom = source.readInt();
            if (Flags.modesApi()) {
                this.allowPriorityChannels = source.readBoolean();
            }
        }
    }

    public static ZenPolicy getDefaultZenPolicy() {
        ZenPolicy policy = new ZenPolicy.Builder().allowAlarms(true).allowMedia(true).allowSystem(false).allowCalls(3).allowMessages(3).allowReminders(false).allowEvents(false).allowRepeatCallers(true).allowConversations(2).showAllVisualEffects().showVisualEffect(0, false).showVisualEffect(1, false).showVisualEffect(2, false).showVisualEffect(5, false).allowPriorityChannels(true).build();
        return policy;
    }

    public static ZenModeConfig getDefaultConfig() {
        ZenModeConfig config = new ZenModeConfig();
        EventInfo eventInfo = new EventInfo();
        eventInfo.reply = 1;
        ZenRule events = new ZenRule();
        events.id = EVENTS_DEFAULT_RULE_ID;
        events.conditionId = toEventConditionId(eventInfo);
        events.component = ComponentName.unflattenFromString("android/com.android.server.notification.EventConditionProvider");
        events.enabled = false;
        events.zenMode = 1;
        events.pkg = "android";
        config.automaticRules.put(EVENTS_DEFAULT_RULE_ID, events);
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.days = new int[]{1, 2, 3, 4, 5, 6, 7};
        scheduleInfo.startHour = 22;
        scheduleInfo.endHour = 7;
        scheduleInfo.exitAtAlarm = true;
        ZenRule sleeping = new ZenRule();
        sleeping.id = EVERY_NIGHT_DEFAULT_RULE_ID;
        sleeping.conditionId = toScheduleConditionId(scheduleInfo);
        sleeping.component = ComponentName.unflattenFromString("android/com.android.server.notification.ScheduleConditionProvider");
        sleeping.enabled = false;
        sleeping.zenMode = 1;
        sleeping.pkg = "android";
        config.automaticRules.put(EVERY_NIGHT_DEFAULT_RULE_ID, sleeping);
        return config;
    }

    void ensureManualZenRule() {
        if (this.manualRule == null) {
            ZenRule newRule = new ZenRule();
            newRule.type = 0;
            newRule.enabled = true;
            newRule.conditionId = Uri.EMPTY;
            newRule.allowManualInvocation = true;
            newRule.zenPolicy = getDefaultZenPolicy();
            newRule.pkg = "android";
            this.manualRule = newRule;
        }
    }

    private static void readRulesFromParcel(ArrayMap<String, ZenRule> ruleMap, Parcel source) {
        int len = source.readInt();
        if (len > 0) {
            String[] ids = new String[len];
            ZenRule[] rules = new ZenRule[len];
            source.readStringArray(ids);
            source.readTypedArray(rules, ZenRule.CREATOR);
            for (int i = 0; i < len; i++) {
                ruleMap.put(ids[i], rules[i]);
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (!Flags.modesUi()) {
            parcel.writeInt(this.allowCalls ? 1 : 0);
            parcel.writeInt(this.allowRepeatCallers ? 1 : 0);
            parcel.writeInt(this.allowMessages ? 1 : 0);
            parcel.writeInt(this.allowReminders ? 1 : 0);
            parcel.writeInt(this.allowEvents ? 1 : 0);
            parcel.writeInt(this.allowCallsFrom);
            parcel.writeInt(this.allowMessagesFrom);
        }
        parcel.writeInt(this.user);
        parcel.writeParcelable(this.manualRule, 0);
        writeRulesToParcel(this.automaticRules, parcel);
        if (Flags.modesApi()) {
            writeRulesToParcel(this.deletedRules, parcel);
        }
        if (!Flags.modesUi()) {
            parcel.writeInt(this.allowAlarms ? 1 : 0);
            parcel.writeInt(this.allowMedia ? 1 : 0);
            parcel.writeInt(this.allowSystem ? 1 : 0);
            parcel.writeInt(this.suppressedVisualEffects);
        }
        parcel.writeInt(this.areChannelsBypassingDnd ? 1 : 0);
        parcel.writeInt(this.exceptionContactsFlag);
        parcel.writeStringList(this.allowExceptionContacts);
        parcel.writeInt(this.appBypassDndFlag);
        synchronized (ZenConfigLock) {
            parcel.writeStringList(this.allowAppBypassDndList);
        }
        if (!Flags.modesUi()) {
            parcel.writeBoolean(this.allowConversations);
            parcel.writeInt(this.allowConversationsFrom);
            if (Flags.modesApi()) {
                parcel.writeBoolean(this.allowPriorityChannels);
            }
        }
    }

    private static void writeRulesToParcel(ArrayMap<String, ZenRule> ruleMap, Parcel dest) {
        if (!ruleMap.isEmpty()) {
            int len = ruleMap.size();
            String[] ids = new String[len];
            ZenRule[] rules = new ZenRule[len];
            for (int i = 0; i < len; i++) {
                ids[i] = ruleMap.keyAt(i);
                rules[i] = ruleMap.valueAt(i);
            }
            dest.writeInt(len);
            dest.writeStringArray(ids);
            dest.writeTypedArray(rules, 0);
            return;
        }
        dest.writeInt(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(ZenModeConfig.class.getSimpleName()).append('[').append("user=").append(this.user);
        if (!Flags.modesUi()) {
            sb.append(",allowAlarms=").append(this.allowAlarms).append(",allowMedia=").append(this.allowMedia).append(",allowSystem=").append(this.allowSystem).append(",allowReminders=").append(this.allowReminders).append(",allowEvents=").append(this.allowEvents).append(",allowCalls=").append(this.allowCalls).append(",allowRepeatCallers=").append(this.allowRepeatCallers).append(",allowMessages=").append(this.allowMessages).append(",allowConversations=").append(this.allowConversations).append(",allowCallsFrom=").append(sourceToString(this.allowCallsFrom)).append(",allowMessagesFrom=").append(sourceToString(this.allowMessagesFrom)).append(",allowConvFrom=").append(ZenPolicy.conversationTypeToString(this.allowConversationsFrom)).append(",exceptionContactsFlag=").append(NotificationManager.Policy.exceptionContactsFlagToString(this.exceptionContactsFlag)).append(",allowExceptionContacts=").append(this.allowExceptionContacts).append(",appBypassDndFlag=").append(NotificationManager.Policy.appBypassDndFlagToString(this.appBypassDndFlag)).append(",allowAppBypassDndList=").append(this.allowAppBypassDndList).append("\nsuppressedVisualEffects=").append(this.suppressedVisualEffects);
        }
        if (Flags.modesApi()) {
            sb.append("\nhasPriorityChannels=").append(this.areChannelsBypassingDnd);
            sb.append(",allowPriorityChannels=").append(this.allowPriorityChannels);
        } else {
            sb.append("\nareChannelsBypassingDnd=").append(this.areChannelsBypassingDnd);
        }
        sb.append(",\nautomaticRules=").append(rulesToString(this.automaticRules));
        sb.append(",\nmanualRule=").append(this.manualRule);
        if (Flags.modesApi()) {
            sb.append(",\ndeletedRules=").append(rulesToString(this.deletedRules));
        }
        return sb.append(']').toString();
    }

    public boolean isAllowPriorityChannels() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowPriorityChannels;
    }

    public void setAllowPriorityChannels(boolean allowPriorityChannels) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowPriorityChannels = allowPriorityChannels;
    }

    public int getSuppressedVisualEffects() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.suppressedVisualEffects;
    }

    public void setSuppressedVisualEffects(int suppressedVisualEffects) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.suppressedVisualEffects = suppressedVisualEffects;
    }

    public int getAllowConversationsFrom() {
        if (Flags.modesUi()) {
            return this.manualRule.zenPolicy.getPriorityConversationSenders();
        }
        return this.allowConversationsFrom;
    }

    public void setAllowConversationsFrom(int allowConversationsFrom) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowConversationsFrom = allowConversationsFrom;
    }

    public void setAllowConversations(boolean allowConversations) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowConversations = allowConversations;
    }

    public boolean isAllowConversations() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowConversations;
    }

    public int getAllowMessagesFrom() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowMessagesFrom;
    }

    public void setAllowMessagesFrom(int allowMessagesFrom) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowMessagesFrom = allowMessagesFrom;
    }

    public void setAllowMessages(boolean allowMessages) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowMessages = allowMessages;
    }

    public int getAllowCallsFrom() {
        if (Flags.modesUi()) {
            return ZenAdapters.peopleTypeToPrioritySenders(this.manualRule.zenPolicy.getPriorityCallSenders(), 2);
        }
        return this.allowCallsFrom;
    }

    public void setAllowCallsFrom(int allowCallsFrom) {
        if (Flags.modesUi()) {
            this.manualRule.zenPolicy = new ZenPolicy.Builder(this.manualRule.zenPolicy).allowCalls(ZenAdapters.prioritySendersToPeopleType(allowCallsFrom)).build();
        } else {
            this.allowCallsFrom = allowCallsFrom;
        }
    }

    public void setAllowCalls(boolean allowCalls) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowCalls = allowCalls;
    }

    public boolean isAllowEvents() {
        if (Flags.modesUi()) {
            return this.manualRule.zenPolicy.isCategoryAllowed(1, false);
        }
        return this.allowEvents;
    }

    public void setAllowEvents(boolean allowEvents) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowEvents = allowEvents;
    }

    public boolean isAllowReminders() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowReminders;
    }

    public void setAllowReminders(boolean allowReminders) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowReminders = allowReminders;
    }

    public boolean isAllowMessages() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowMessages;
    }

    public boolean isAllowRepeatCallers() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowRepeatCallers;
    }

    public void setAllowRepeatCallers(boolean allowRepeatCallers) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowRepeatCallers = allowRepeatCallers;
    }

    public boolean isAllowSystem() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowSystem;
    }

    public void setAllowSystem(boolean allowSystem) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowSystem = allowSystem;
    }

    public boolean isAllowMedia() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowMedia;
    }

    public void setAllowMedia(boolean allowMedia) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowMedia = allowMedia;
    }

    public boolean isAllowAlarms() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowAlarms;
    }

    public void setAllowAlarms(boolean allowAlarms) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowAlarms = allowAlarms;
    }

    public boolean isAllowCalls() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowCalls;
    }

    private static String rulesToString(ArrayMap<String, ZenRule> ruleList) {
        if (ruleList.isEmpty()) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(ruleList.size() * 28);
        buffer.append("{\n");
        for (int i = 0; i < ruleList.size(); i++) {
            if (i > 0) {
                buffer.append(",\n");
            }
            Object value = ruleList.valueAt(i);
            buffer.append(value);
        }
        buffer.append('}');
        return buffer.toString();
    }

    public boolean isValid() {
        if (!isValidManualRule(this.manualRule)) {
            return false;
        }
        int N = this.automaticRules.size();
        for (int i = 0; i < N; i++) {
            if (!isValidAutomaticRule(this.automaticRules.valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidManualRule(ZenRule rule) {
        return rule == null || (Settings.Global.isValidZenMode(rule.zenMode) && sameCondition(rule));
    }

    private static boolean isValidAutomaticRule(ZenRule rule) {
        return (rule == null || TextUtils.isEmpty(rule.name) || !Settings.Global.isValidZenMode(rule.zenMode) || rule.conditionId == null || !sameCondition(rule)) ? false : true;
    }

    private static boolean sameCondition(ZenRule rule) {
        if (rule == null) {
            return false;
        }
        return rule.conditionId == null ? rule.condition == null : rule.condition == null || rule.conditionId.equals(rule.condition.id);
    }

    private static int[] generateMinuteBuckets() {
        int[] buckets = new int[15];
        buckets[0] = 15;
        buckets[1] = 30;
        buckets[2] = 45;
        for (int i = 1; i <= 12; i++) {
            buckets[i + 2] = i * 60;
        }
        return buckets;
    }

    public static String sourceToString(int source) {
        switch (source) {
            case 0:
                return "anyone";
            case 1:
                return Contacts.AUTHORITY;
            case 2:
                return "stars";
            default:
                return "UNKNOWN";
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof ZenModeConfig)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        ZenModeConfig other = (ZenModeConfig) o;
        boolean eq = other.allowAlarms == this.allowAlarms && other.allowMedia == this.allowMedia && other.allowSystem == this.allowSystem && other.allowCalls == this.allowCalls && other.allowRepeatCallers == this.allowRepeatCallers && other.allowMessages == this.allowMessages && other.allowCallsFrom == this.allowCallsFrom && other.allowMessagesFrom == this.allowMessagesFrom && other.allowReminders == this.allowReminders && other.allowEvents == this.allowEvents && other.user == this.user && Objects.equals(other.automaticRules, this.automaticRules) && Objects.equals(other.manualRule, this.manualRule) && other.suppressedVisualEffects == this.suppressedVisualEffects && other.areChannelsBypassingDnd == this.areChannelsBypassingDnd && other.allowConversations == this.allowConversations && other.allowConversationsFrom == this.allowConversationsFrom && other.exceptionContactsFlag == this.exceptionContactsFlag && other.allowExceptionContacts != null && other.allowExceptionContacts.equals(this.allowExceptionContacts) && other.appBypassDndFlag == this.appBypassDndFlag && other.allowAppBypassDndList != null && other.allowAppBypassDndList.equals(this.allowAppBypassDndList);
        if (Flags.modesApi()) {
            return eq && Objects.equals(other.deletedRules, this.deletedRules) && other.allowPriorityChannels == this.allowPriorityChannels;
        }
        return eq;
    }

    public int hashCode() {
        String exceptionContactsString = (this.allowExceptionContacts == null || this.allowExceptionContacts.isEmpty()) ? new String() : joinStrings(",", this.allowExceptionContacts);
        String appBypassDndListString = (this.allowAppBypassDndList == null || this.allowAppBypassDndList.isEmpty()) ? new String() : joinStrings(",", this.allowAppBypassDndList);
        if (Flags.modesApi()) {
            return Objects.hash(Boolean.valueOf(this.allowAlarms), Boolean.valueOf(this.allowMedia), Boolean.valueOf(this.allowSystem), Boolean.valueOf(this.allowCalls), Boolean.valueOf(this.allowRepeatCallers), Boolean.valueOf(this.allowMessages), Integer.valueOf(this.allowCallsFrom), Integer.valueOf(this.allowMessagesFrom), Boolean.valueOf(this.allowReminders), Boolean.valueOf(this.allowEvents), Integer.valueOf(this.user), this.automaticRules, this.manualRule, Integer.valueOf(this.suppressedVisualEffects), Boolean.valueOf(this.areChannelsBypassingDnd), Boolean.valueOf(this.allowConversations), Integer.valueOf(this.allowConversationsFrom), exceptionContactsString, appBypassDndListString, Boolean.valueOf(this.allowPriorityChannels));
        }
        return Objects.hash(Boolean.valueOf(this.allowAlarms), Boolean.valueOf(this.allowMedia), Boolean.valueOf(this.allowSystem), Boolean.valueOf(this.allowCalls), Boolean.valueOf(this.allowRepeatCallers), Boolean.valueOf(this.allowMessages), Integer.valueOf(this.allowCallsFrom), Integer.valueOf(this.allowMessagesFrom), Boolean.valueOf(this.allowReminders), Boolean.valueOf(this.allowEvents), Integer.valueOf(this.user), this.automaticRules, this.manualRule, Integer.valueOf(this.suppressedVisualEffects), Boolean.valueOf(this.areChannelsBypassingDnd), Boolean.valueOf(this.allowConversations), Integer.valueOf(this.allowConversationsFrom), Integer.valueOf(this.exceptionContactsFlag), exceptionContactsString, Integer.valueOf(this.appBypassDndFlag), appBypassDndListString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String toDayList(int[] days) {
        if (days == null || days.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < days.length; i++) {
            if (i > 0) {
                sb.append('.');
            }
            sb.append(days[i]);
        }
        return sb.toString();
    }

    private static int[] tryParseDayList(String dayList, String sep) {
        if (dayList == null) {
            return null;
        }
        String[] tokens = dayList.split(sep);
        if (tokens.length == 0) {
            return null;
        }
        int[] rt = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            int day = tryParseInt(tokens[i], -1);
            if (day == -1) {
                return null;
            }
            rt[i] = day;
        }
        return rt;
    }

    private static int tryParseInt(String value, int defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    private static long tryParseLong(String value, long defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    private static Long tryParseLong(String value, Long defValue) {
        if (TextUtils.isEmpty(value)) {
            return defValue;
        }
        try {
            return Long.valueOf(Long.parseLong(value));
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public static int getCurrentXmlVersion() {
        return Flags.modesApi() ? 11 : 10;
    }

    public static ZenModeConfig readXml(TypedXmlPullParser parser) throws XmlPullParserException, IOException {
        String str = null;
        int i = 2;
        if (parser.getEventType() != 2 || !"zen".equals(parser.getName())) {
            return null;
        }
        ZenModeConfig rt = new ZenModeConfig();
        rt.version = safeInt(parser, "version", getCurrentXmlVersion());
        rt.user = safeInt(parser, "user", rt.user);
        boolean readSuppressedEffects = false;
        boolean readManualRule = false;
        while (true) {
            int type = parser.next();
            if (type != 1) {
                String tag = parser.getName();
                if (type == 3 && "zen".equals(tag)) {
                    if (Flags.modesUi() && !readManualRule) {
                        rt.manualRule.zenPolicy = rt.toZenPolicy();
                    }
                    return rt;
                }
                if (type == i) {
                    if (ALLOW_TAG.equals(tag)) {
                        rt.allowCalls = safeBoolean(parser, ALLOW_ATT_CALLS, true);
                        rt.allowRepeatCallers = safeBoolean(parser, ALLOW_ATT_REPEAT_CALLERS, true);
                        rt.allowMessages = safeBoolean(parser, ALLOW_ATT_MESSAGES, true);
                        rt.allowReminders = safeBoolean(parser, ALLOW_ATT_REMINDERS, false);
                        rt.allowConversations = safeBoolean(parser, ALLOW_ATT_CONV, true);
                        rt.allowEvents = safeBoolean(parser, "events", false);
                        int from = safeInt(parser, ALLOW_ATT_FROM, -1);
                        int callsFrom = safeInt(parser, ALLOW_ATT_CALLS_FROM, -1);
                        int messagesFrom = safeInt(parser, ALLOW_ATT_MESSAGES_FROM, -1);
                        rt.allowConversationsFrom = safeInt(parser, ALLOW_ATT_CONV_FROM, i);
                        rt.exceptionContactsFlag = safeInt(parser, ATT_SELECTED_CONTACTS_ALLOWED, -1);
                        String exceptionContactString = parser.getAttributeValue(str, ALLOW_ATT_EXCEPTION_CONTACTS);
                        if (exceptionContactString != null && !exceptionContactString.isEmpty()) {
                            rt.allowExceptionContacts = Arrays.asList(exceptionContactString.split(","));
                        }
                        rt.appBypassDndFlag = safeInt(parser, ATT_SELECTED_APPS_ALLOWED, -1);
                        String appBypassDndListString = parser.getAttributeValue(str, ALLOW_ATT_APP_BYPASS_DND_LIST);
                        synchronized (ZenConfigLock) {
                            if (appBypassDndListString != null) {
                                if (!appBypassDndListString.isEmpty()) {
                                    rt.allowAppBypassDndList = Arrays.asList(appBypassDndListString.split(","));
                                }
                            }
                        }
                        if (isValidSource(callsFrom) && isValidSource(messagesFrom)) {
                            rt.allowCallsFrom = callsFrom;
                            rt.allowMessagesFrom = messagesFrom;
                        } else if (isValidSource(from)) {
                            Slog.i(TAG, "Migrating existing shared 'from': " + sourceToString(from));
                            rt.allowCallsFrom = from;
                            rt.allowMessagesFrom = from;
                        } else {
                            rt.allowCallsFrom = i;
                            rt.allowMessagesFrom = i;
                        }
                        rt.allowAlarms = safeBoolean(parser, ALLOW_ATT_ALARMS, true);
                        rt.allowMedia = safeBoolean(parser, ALLOW_ATT_MEDIA, true);
                        rt.allowSystem = safeBoolean(parser, "system", false);
                        if (Flags.modesApi()) {
                            rt.allowPriorityChannels = safeBoolean(parser, ALLOW_ATT_CHANNELS, true);
                        }
                        Boolean allowWhenScreenOff = unsafeBoolean(parser, ALLOW_ATT_SCREEN_OFF);
                        Boolean allowWhenScreenOn = unsafeBoolean(parser, ALLOW_ATT_SCREEN_ON);
                        if (allowWhenScreenOff != null || allowWhenScreenOn != null) {
                            readSuppressedEffects = true;
                            rt.suppressedVisualEffects = 0;
                        }
                        if (allowWhenScreenOff != null && !allowWhenScreenOff.booleanValue()) {
                            rt.suppressedVisualEffects |= 140;
                        }
                        if (allowWhenScreenOn != null && !allowWhenScreenOn.booleanValue()) {
                            rt.suppressedVisualEffects |= 16;
                        }
                        if (readSuppressedEffects) {
                            Slog.d(TAG, "Migrated visual effects to " + rt.suppressedVisualEffects);
                        }
                        str = null;
                        i = 2;
                    } else if (DISALLOW_TAG.equals(tag) && !readSuppressedEffects) {
                        rt.suppressedVisualEffects = safeInt(parser, DISALLOW_ATT_VISUAL_EFFECTS, 157);
                        str = null;
                    } else if ("manual".equals(tag)) {
                        rt.manualRule = readRuleXml(parser);
                        if (rt.manualRule == null) {
                            str = null;
                        } else {
                            readManualRule = true;
                            str = null;
                            i = 2;
                        }
                    } else if (AUTOMATIC_TAG.equals(tag) || (Flags.modesApi() && "deleted".equals(tag))) {
                        str = null;
                        String id = parser.getAttributeValue(null, RULE_ATT_ID);
                        ZenRule automaticRule = readRuleXml(parser);
                        if (id != null && automaticRule != null) {
                            automaticRule.id = id;
                            if (Flags.modesApi() && "deleted".equals(tag)) {
                                String deletedRuleKey = deletedRuleKey(automaticRule);
                                if (deletedRuleKey != null) {
                                    rt.deletedRules.put(deletedRuleKey, automaticRule);
                                }
                            } else if (AUTOMATIC_TAG.equals(tag)) {
                                rt.automaticRules.put(id, automaticRule);
                            }
                        }
                    } else if ("state".equals(tag)) {
                        rt.areChannelsBypassingDnd = safeBoolean(parser, "areChannelsBypassingDnd", false);
                        str = null;
                    } else {
                        str = null;
                    }
                }
                i = 2;
            } else {
                throw new IllegalStateException("Failed to reach END_DOCUMENT");
            }
        }
    }

    public static String deletedRuleKey(ZenRule rule) {
        if (rule.pkg != null && rule.conditionId != null) {
            return rule.pkg + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + rule.conditionId.toString();
        }
        return null;
    }

    public void writeXml(TypedXmlSerializer out, Integer version, boolean forBackup) throws IOException {
        int xmlVersion = getCurrentXmlVersion();
        out.startTag(null, "zen");
        out.attribute(null, "version", version == null ? Integer.toString(xmlVersion) : Integer.toString(version.intValue()));
        out.attributeInt(null, "user", this.user);
        out.startTag(null, ALLOW_TAG);
        out.attributeBoolean(null, ALLOW_ATT_CALLS, this.allowCalls);
        out.attributeBoolean(null, ALLOW_ATT_REPEAT_CALLERS, this.allowRepeatCallers);
        out.attributeBoolean(null, ALLOW_ATT_MESSAGES, this.allowMessages);
        out.attributeBoolean(null, ALLOW_ATT_REMINDERS, this.allowReminders);
        out.attributeBoolean(null, "events", this.allowEvents);
        out.attributeInt(null, ALLOW_ATT_CALLS_FROM, this.allowCallsFrom);
        out.attributeInt(null, ALLOW_ATT_MESSAGES_FROM, this.allowMessagesFrom);
        out.attributeBoolean(null, ALLOW_ATT_ALARMS, this.allowAlarms);
        out.attributeBoolean(null, ALLOW_ATT_MEDIA, this.allowMedia);
        out.attributeBoolean(null, "system", this.allowSystem);
        out.attributeBoolean(null, ALLOW_ATT_CONV, this.allowConversations);
        out.attributeInt(null, ALLOW_ATT_CONV_FROM, this.allowConversationsFrom);
        out.attributeInt(null, ATT_SELECTED_CONTACTS_ALLOWED, this.exceptionContactsFlag);
        if (this.allowExceptionContacts != null && !this.allowExceptionContacts.isEmpty()) {
            String exceptionContactsString = joinStrings(",", this.allowExceptionContacts);
            out.attribute(null, ALLOW_ATT_EXCEPTION_CONTACTS, exceptionContactsString);
        }
        out.attributeInt(null, ATT_SELECTED_APPS_ALLOWED, this.appBypassDndFlag);
        if (this.allowAppBypassDndList != null && !this.allowAppBypassDndList.isEmpty()) {
            String appBypassDndListString = joinStrings(",", this.allowAppBypassDndList);
            out.attribute(null, ALLOW_ATT_APP_BYPASS_DND_LIST, appBypassDndListString);
        }
        if (Flags.modesApi()) {
            out.attributeBoolean(null, ALLOW_ATT_CHANNELS, this.allowPriorityChannels);
        }
        out.endTag(null, ALLOW_TAG);
        out.startTag(null, DISALLOW_TAG);
        out.attributeInt(null, DISALLOW_ATT_VISUAL_EFFECTS, this.suppressedVisualEffects);
        out.endTag(null, DISALLOW_TAG);
        if (this.manualRule != null) {
            out.startTag(null, "manual");
            writeRuleXml(this.manualRule, out);
            out.endTag(null, "manual");
        }
        int N = this.automaticRules.size();
        for (int i = 0; i < N; i++) {
            String id = this.automaticRules.keyAt(i);
            ZenRule automaticRule = this.automaticRules.valueAt(i);
            out.startTag(null, AUTOMATIC_TAG);
            out.attribute(null, RULE_ATT_ID, id);
            writeRuleXml(automaticRule, out);
            out.endTag(null, AUTOMATIC_TAG);
        }
        if (Flags.modesApi() && !forBackup) {
            for (int i2 = 0; i2 < this.deletedRules.size(); i2++) {
                ZenRule deletedRule = this.deletedRules.valueAt(i2);
                out.startTag(null, "deleted");
                out.attribute(null, RULE_ATT_ID, deletedRule.id);
                writeRuleXml(deletedRule, out);
                out.endTag(null, "deleted");
            }
        }
        out.startTag(null, "state");
        out.attributeBoolean(null, "areChannelsBypassingDnd", this.areChannelsBypassingDnd);
        out.endTag(null, "state");
        out.endTag(null, "zen");
    }

    public static ZenRule readRuleXml(TypedXmlPullParser parser) {
        ZenRule rt = new ZenRule();
        rt.enabled = safeBoolean(parser, "enabled", true);
        rt.name = parser.getAttributeValue(null, "name");
        String zen = parser.getAttributeValue(null, "zen");
        rt.zenMode = tryParseZenMode(zen, -1);
        if (rt.zenMode == -1) {
            Slog.w(TAG, "Bad zen mode in rule xml:" + zen);
            return null;
        }
        rt.conditionId = safeUri(parser, "conditionId");
        rt.component = safeComponentName(parser, "component");
        rt.configurationActivity = safeComponentName(parser, RULE_ATT_CONFIG_ACTIVITY);
        rt.pkg = XmlUtils.readStringAttribute(parser, "pkg");
        if (rt.pkg == null) {
            rt.pkg = rt.component != null ? rt.component.getPackageName() : null;
        }
        rt.creationTime = safeLong(parser, "creationTime", 0L);
        rt.enabler = parser.getAttributeValue(null, "enabler");
        rt.condition = readConditionXml(parser);
        if (!Flags.modesApi() && rt.zenMode != 1 && Condition.isValidId(rt.conditionId, "android")) {
            Slog.i(TAG, "Updating zenMode of automatic rule " + rt.name);
            rt.zenMode = 1;
        }
        rt.modified = safeBoolean(parser, "modified", false);
        rt.zenPolicy = readZenPolicyXml(parser);
        if (Flags.modesApi()) {
            rt.zenDeviceEffects = readZenDeviceEffectsXml(parser);
            rt.allowManualInvocation = safeBoolean(parser, RULE_ATT_ALLOW_MANUAL, false);
            rt.iconResName = parser.getAttributeValue(null, RULE_ATT_ICON);
            rt.triggerDescription = parser.getAttributeValue(null, RULE_ATT_TRIGGER_DESC);
            rt.type = safeInt(parser, "type", -1);
            rt.userModifiedFields = safeInt(parser, RULE_ATT_USER_MODIFIED_FIELDS, 0);
            rt.zenPolicyUserModifiedFields = safeInt(parser, POLICY_USER_MODIFIED_FIELDS, 0);
            rt.zenDeviceEffectsUserModifiedFields = safeInt(parser, DEVICE_EFFECT_USER_MODIFIED_FIELDS, 0);
            Long deletionInstant = tryParseLong(parser.getAttributeValue(null, RULE_ATT_DELETION_INSTANT), (Long) null);
            if (deletionInstant != null) {
                rt.deletionInstant = Instant.ofEpochMilli(deletionInstant.longValue());
            }
        }
        return rt;
    }

    public static void writeRuleXml(ZenRule rule, TypedXmlSerializer out) throws IOException {
        out.attributeBoolean(null, "enabled", rule.enabled);
        if (rule.name != null) {
            out.attribute(null, "name", rule.name);
        }
        out.attributeInt(null, "zen", rule.zenMode);
        if (rule.pkg != null) {
            out.attribute(null, "pkg", rule.pkg);
        }
        if (rule.component != null) {
            out.attribute(null, "component", rule.component.flattenToString());
        }
        if (rule.configurationActivity != null) {
            out.attribute(null, RULE_ATT_CONFIG_ACTIVITY, rule.configurationActivity.flattenToString());
        }
        if (rule.conditionId != null) {
            out.attribute(null, "conditionId", rule.conditionId.toString());
        }
        out.attributeLong(null, "creationTime", rule.creationTime);
        if (rule.enabler != null) {
            out.attribute(null, "enabler", rule.enabler);
        }
        if (rule.condition != null) {
            writeConditionXml(rule.condition, out);
        }
        if (rule.zenPolicy != null) {
            writeZenPolicyXml(rule.zenPolicy, out);
        }
        if (Flags.modesApi() && rule.zenDeviceEffects != null) {
            writeZenDeviceEffectsXml(rule.zenDeviceEffects, out);
        }
        out.attributeBoolean(null, "modified", rule.modified);
        if (Flags.modesApi()) {
            out.attributeBoolean(null, RULE_ATT_ALLOW_MANUAL, rule.allowManualInvocation);
            if (rule.iconResName != null) {
                out.attribute(null, RULE_ATT_ICON, rule.iconResName);
            }
            if (rule.triggerDescription != null) {
                out.attribute(null, RULE_ATT_TRIGGER_DESC, rule.triggerDescription);
            }
            out.attributeInt(null, "type", rule.type);
            out.attributeInt(null, RULE_ATT_USER_MODIFIED_FIELDS, rule.userModifiedFields);
            out.attributeInt(null, POLICY_USER_MODIFIED_FIELDS, rule.zenPolicyUserModifiedFields);
            out.attributeInt(null, DEVICE_EFFECT_USER_MODIFIED_FIELDS, rule.zenDeviceEffectsUserModifiedFields);
            if (rule.deletionInstant != null) {
                out.attributeLong(null, RULE_ATT_DELETION_INSTANT, rule.deletionInstant.toEpochMilli());
            }
        }
    }

    public static Condition readConditionXml(TypedXmlPullParser parser) {
        Uri id = safeUri(parser, "id");
        if (id != null) {
            String summary = parser.getAttributeValue(null, "summary");
            String line1 = parser.getAttributeValue(null, CONDITION_ATT_LINE1);
            String line2 = parser.getAttributeValue(null, CONDITION_ATT_LINE2);
            int icon = safeInt(parser, "icon", -1);
            int state = safeInt(parser, "state", -1);
            int flags = safeInt(parser, "flags", -1);
            try {
                if (Flags.modesApi()) {
                    int source = safeInt(parser, "source", 0);
                    return new Condition(id, summary, line1, line2, icon, state, source, flags);
                }
                return new Condition(id, summary, line1, line2, icon, state, flags);
            } catch (IllegalArgumentException e) {
                Slog.w(TAG, "Unable to read condition xml", e);
                return null;
            }
        }
        return null;
    }

    public static void writeConditionXml(Condition c, TypedXmlSerializer out) throws IOException {
        out.attribute(null, "id", c.id.toString());
        out.attribute(null, "summary", c.summary);
        out.attribute(null, CONDITION_ATT_LINE1, c.line1);
        out.attribute(null, CONDITION_ATT_LINE2, c.line2);
        out.attributeInt(null, "icon", c.icon);
        out.attributeInt(null, "state", c.state);
        if (Flags.modesApi()) {
            out.attributeInt(null, "source", c.source);
        }
        out.attributeInt(null, "flags", c.flags);
    }

    public static ZenPolicy readZenPolicyXml(TypedXmlPullParser parser) {
        int channels;
        boolean policySet = false;
        ZenPolicy.Builder builder = new ZenPolicy.Builder();
        int calls = safeInt(parser, ALLOW_ATT_CALLS_FROM, 0);
        int messages = safeInt(parser, ALLOW_ATT_MESSAGES_FROM, 0);
        int repeatCallers = safeInt(parser, ALLOW_ATT_REPEAT_CALLERS, 0);
        int conversations = safeInt(parser, ALLOW_ATT_CONV_FROM, 0);
        int alarms = safeInt(parser, ALLOW_ATT_ALARMS, 0);
        int media = safeInt(parser, ALLOW_ATT_MEDIA, 0);
        int system = safeInt(parser, "system", 0);
        int events = safeInt(parser, "events", 0);
        int reminders = safeInt(parser, ALLOW_ATT_REMINDERS, 0);
        if (Flags.modesApi() && (channels = safeInt(parser, ALLOW_ATT_CHANNELS, 0)) != 0) {
            builder.allowPriorityChannels(channels == 1);
            policySet = true;
        }
        if (calls != 0) {
            builder.allowCalls(calls);
            policySet = true;
        }
        if (messages != 0) {
            builder.allowMessages(messages);
            policySet = true;
        }
        if (repeatCallers != 0) {
            builder.allowRepeatCallers(repeatCallers == 1);
            policySet = true;
        }
        if (conversations != 0) {
            builder.allowConversations(conversations);
            policySet = true;
        }
        if (alarms != 0) {
            builder.allowAlarms(alarms == 1);
            policySet = true;
        }
        if (media != 0) {
            builder.allowMedia(media == 1);
            policySet = true;
        }
        if (system != 0) {
            builder.allowSystem(system == 1);
            policySet = true;
        }
        if (events != 0) {
            builder.allowEvents(events == 1);
            policySet = true;
        }
        if (reminders != 0) {
            builder.allowReminders(reminders == 1);
            policySet = true;
        }
        int fullScreenIntent = safeInt(parser, SHOW_ATT_FULL_SCREEN_INTENT, 0);
        int lights = safeInt(parser, SHOW_ATT_LIGHTS, 0);
        int peek = safeInt(parser, SHOW_ATT_PEEK, 0);
        boolean policySet2 = policySet;
        int statusBar = safeInt(parser, SHOW_ATT_STATUS_BAR_ICONS, 0);
        int badges = safeInt(parser, SHOW_ATT_BADGES, 0);
        int ambient = safeInt(parser, SHOW_ATT_AMBIENT, 0);
        int notificationList = safeInt(parser, SHOW_ATT_NOTIFICATION_LIST, 0);
        if (fullScreenIntent != 0) {
            builder.showFullScreenIntent(fullScreenIntent == 1);
            policySet2 = true;
        }
        if (lights != 0) {
            builder.showLights(lights == 1);
            policySet2 = true;
        }
        if (peek != 0) {
            builder.showPeeking(peek == 1);
            policySet2 = true;
        }
        if (statusBar != 0) {
            builder.showStatusBarIcons(statusBar == 1);
            policySet2 = true;
        }
        if (badges != 0) {
            builder.showBadges(badges == 1);
            policySet2 = true;
        }
        if (ambient != 0) {
            builder.showInAmbientDisplay(ambient == 1);
            policySet2 = true;
        }
        if (notificationList != 0) {
            builder.showInNotificationList(notificationList == 1);
            policySet2 = true;
        }
        if (policySet2) {
            return builder.build();
        }
        return null;
    }

    public static void writeZenPolicyXml(ZenPolicy policy, TypedXmlSerializer out) throws IOException {
        writeZenPolicyState(ALLOW_ATT_CALLS_FROM, policy.getPriorityCallSenders(), out);
        writeZenPolicyState(ALLOW_ATT_MESSAGES_FROM, policy.getPriorityMessageSenders(), out);
        writeZenPolicyState(ALLOW_ATT_REPEAT_CALLERS, policy.getPriorityCategoryRepeatCallers(), out);
        writeZenPolicyState(ALLOW_ATT_CONV_FROM, policy.getPriorityConversationSenders(), out);
        writeZenPolicyState(ALLOW_ATT_ALARMS, policy.getPriorityCategoryAlarms(), out);
        writeZenPolicyState(ALLOW_ATT_MEDIA, policy.getPriorityCategoryMedia(), out);
        writeZenPolicyState("system", policy.getPriorityCategorySystem(), out);
        writeZenPolicyState(ALLOW_ATT_REMINDERS, policy.getPriorityCategoryReminders(), out);
        writeZenPolicyState("events", policy.getPriorityCategoryEvents(), out);
        writeZenPolicyState(SHOW_ATT_FULL_SCREEN_INTENT, policy.getVisualEffectFullScreenIntent(), out);
        writeZenPolicyState(SHOW_ATT_LIGHTS, policy.getVisualEffectLights(), out);
        writeZenPolicyState(SHOW_ATT_PEEK, policy.getVisualEffectPeek(), out);
        writeZenPolicyState(SHOW_ATT_STATUS_BAR_ICONS, policy.getVisualEffectStatusBar(), out);
        writeZenPolicyState(SHOW_ATT_BADGES, policy.getVisualEffectBadge(), out);
        writeZenPolicyState(SHOW_ATT_AMBIENT, policy.getVisualEffectAmbient(), out);
        writeZenPolicyState(SHOW_ATT_NOTIFICATION_LIST, policy.getVisualEffectNotificationList(), out);
        if (Flags.modesApi()) {
            writeZenPolicyState(ALLOW_ATT_CHANNELS, policy.getPriorityChannelsAllowed(), out);
        }
    }

    private static void writeZenPolicyState(String attr, int val, TypedXmlSerializer out) throws IOException {
        if (Objects.equals(attr, ALLOW_ATT_CALLS_FROM) || Objects.equals(attr, ALLOW_ATT_MESSAGES_FROM)) {
            if (val != 0) {
                out.attributeInt(null, attr, val);
            }
        } else if (Objects.equals(attr, ALLOW_ATT_CONV_FROM)) {
            if (val != 0) {
                out.attributeInt(null, attr, val);
            }
        } else if (Flags.modesApi() && Objects.equals(attr, ALLOW_ATT_CHANNELS)) {
            if (val != 0) {
                out.attributeInt(null, attr, val);
            }
        } else if (val != 0) {
            out.attributeInt(null, attr, val);
        }
    }

    private static ZenDeviceEffects readZenDeviceEffectsXml(TypedXmlPullParser parser) {
        ZenDeviceEffects deviceEffects = new ZenDeviceEffects.Builder().setShouldDisplayGrayscale(safeBoolean(parser, DEVICE_EFFECT_DISPLAY_GRAYSCALE, false)).setShouldSuppressAmbientDisplay(safeBoolean(parser, DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY, false)).setShouldDimWallpaper(safeBoolean(parser, DEVICE_EFFECT_DIM_WALLPAPER, false)).setShouldUseNightMode(safeBoolean(parser, DEVICE_EFFECT_USE_NIGHT_MODE, false)).setShouldDisableAutoBrightness(safeBoolean(parser, DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS, false)).setShouldDisableTapToWake(safeBoolean(parser, DEVICE_EFFECT_DISABLE_TAP_TO_WAKE, false)).setShouldDisableTiltToWake(safeBoolean(parser, DEVICE_EFFECT_DISABLE_TILT_TO_WAKE, false)).setShouldDisableTouch(safeBoolean(parser, DEVICE_EFFECT_DISABLE_TOUCH, false)).setShouldMinimizeRadioUsage(safeBoolean(parser, DEVICE_EFFECT_MINIMIZE_RADIO_USAGE, false)).setShouldMaximizeDoze(safeBoolean(parser, DEVICE_EFFECT_MAXIMIZE_DOZE, false)).setExtraEffects(safeStringSet(parser, DEVICE_EFFECT_EXTRAS)).build();
        if (deviceEffects.hasEffects()) {
            return deviceEffects;
        }
        return null;
    }

    private static void writeZenDeviceEffectsXml(ZenDeviceEffects deviceEffects, TypedXmlSerializer out) throws IOException {
        writeBooleanIfTrue(out, DEVICE_EFFECT_DISPLAY_GRAYSCALE, deviceEffects.shouldDisplayGrayscale());
        writeBooleanIfTrue(out, DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY, deviceEffects.shouldSuppressAmbientDisplay());
        writeBooleanIfTrue(out, DEVICE_EFFECT_DIM_WALLPAPER, deviceEffects.shouldDimWallpaper());
        writeBooleanIfTrue(out, DEVICE_EFFECT_USE_NIGHT_MODE, deviceEffects.shouldUseNightMode());
        writeBooleanIfTrue(out, DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS, deviceEffects.shouldDisableAutoBrightness());
        writeBooleanIfTrue(out, DEVICE_EFFECT_DISABLE_TAP_TO_WAKE, deviceEffects.shouldDisableTapToWake());
        writeBooleanIfTrue(out, DEVICE_EFFECT_DISABLE_TILT_TO_WAKE, deviceEffects.shouldDisableTiltToWake());
        writeBooleanIfTrue(out, DEVICE_EFFECT_DISABLE_TOUCH, deviceEffects.shouldDisableTouch());
        writeBooleanIfTrue(out, DEVICE_EFFECT_MINIMIZE_RADIO_USAGE, deviceEffects.shouldMinimizeRadioUsage());
        writeBooleanIfTrue(out, DEVICE_EFFECT_MAXIMIZE_DOZE, deviceEffects.shouldMaximizeDoze());
        writeStringSet(out, DEVICE_EFFECT_EXTRAS, deviceEffects.getExtraEffects());
    }

    private static void writeBooleanIfTrue(TypedXmlSerializer out, String att, boolean value) throws IOException {
        if (value) {
            out.attributeBoolean(null, att, true);
        }
    }

    private static void writeStringSet(TypedXmlSerializer out, String att, Set<String> values) throws IOException {
        if (values.isEmpty()) {
            return;
        }
        List<String> escapedItems = new ArrayList<>();
        for (String item : values) {
            escapedItems.add(item.replace(ITEM_SEPARATOR_ESCAPE, "\\\\").replace(",", "\\,"));
        }
        String serialized = String.join(",", escapedItems);
        out.attribute(null, att, serialized);
    }

    public static boolean isValidHour(int val) {
        return val >= 0 && val < 24;
    }

    public static boolean isValidMinute(int val) {
        return val >= 0 && val < 60;
    }

    private static boolean isValidSource(int source) {
        return source >= 0 && source <= 2;
    }

    private static Boolean unsafeBoolean(TypedXmlPullParser parser, String att) {
        try {
            return Boolean.valueOf(parser.getAttributeBoolean(null, att));
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean safeBoolean(TypedXmlPullParser parser, String att, boolean defValue) {
        return parser.getAttributeBoolean(null, att, defValue);
    }

    private static boolean safeBoolean(String val, boolean defValue) {
        return TextUtils.isEmpty(val) ? defValue : Boolean.parseBoolean(val);
    }

    private static int safeInt(TypedXmlPullParser parser, String att, int defValue) {
        return parser.getAttributeInt(null, att, defValue);
    }

    private static ComponentName safeComponentName(TypedXmlPullParser parser, String att) {
        String val = parser.getAttributeValue(null, att);
        if (TextUtils.isEmpty(val)) {
            return null;
        }
        return ComponentName.unflattenFromString(val);
    }

    private static Uri safeUri(TypedXmlPullParser parser, String att) {
        String val = parser.getAttributeValue(null, att);
        if (val == null) {
            return null;
        }
        return Uri.parse(val);
    }

    private static long safeLong(TypedXmlPullParser parser, String att, long defValue) {
        String val = parser.getAttributeValue(null, att);
        return tryParseLong(val, defValue);
    }

    private static Set<String> safeStringSet(TypedXmlPullParser parser, String att) {
        Set<String> values = new HashSet<>();
        String serialized = parser.getAttributeValue(null, att);
        if (!TextUtils.isEmpty(serialized)) {
            String[] escapedItems = ITEM_SPLITTER_REGEX.split(serialized);
            for (String escapedItem : escapedItems) {
                values.add(escapedItem.replace("\\\\", ITEM_SEPARATOR_ESCAPE).replace("\\,", ","));
            }
        }
        return values;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ZenModeConfig copy() {
        Parcel parcel = Parcel.obtain();
        try {
            writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            return new ZenModeConfig(parcel);
        } finally {
            parcel.recycle();
        }
    }

    public ZenPolicy getZenPolicy() {
        return Flags.modesUi() ? this.manualRule.zenPolicy : toZenPolicy();
    }

    ZenPolicy toZenPolicy() {
        int i;
        int i2;
        ZenPolicy.Builder builder = new ZenPolicy.Builder();
        if (this.allowCalls) {
            i = ZenAdapters.prioritySendersToPeopleType(this.allowCallsFrom);
        } else {
            i = 4;
        }
        ZenPolicy.Builder allowRepeatCallers = builder.allowCalls(i).allowRepeatCallers(this.allowRepeatCallers);
        if (this.allowMessages) {
            i2 = ZenAdapters.prioritySendersToPeopleType(this.allowMessagesFrom);
        } else {
            i2 = 4;
        }
        ZenPolicy.Builder builder2 = allowRepeatCallers.allowMessages(i2).allowReminders(this.allowReminders).allowEvents(this.allowEvents).allowAlarms(this.allowAlarms).allowMedia(this.allowMedia).allowSystem(this.allowSystem).allowConversations(this.allowConversations ? this.allowConversationsFrom : 3);
        if (this.suppressedVisualEffects == 0) {
            builder2.showAllVisualEffects();
        } else {
            builder2.showFullScreenIntent((this.suppressedVisualEffects & 4) == 0);
            builder2.showLights((this.suppressedVisualEffects & 8) == 0);
            builder2.showPeeking((this.suppressedVisualEffects & 16) == 0);
            builder2.showStatusBarIcons((this.suppressedVisualEffects & 32) == 0);
            builder2.showBadges((this.suppressedVisualEffects & 64) == 0);
            builder2.showInAmbientDisplay((this.suppressedVisualEffects & 128) == 0);
            builder2.showInNotificationList((this.suppressedVisualEffects & 256) == 0);
        }
        builder2.setAppBypassDndFlag(this.appBypassDndFlag);
        builder2.setExceptionContactsFlag(this.exceptionContactsFlag);
        builder2.allowExceptionContacts(joinStrings(",", this.allowExceptionContacts));
        builder2.allowAppsToBypassDnd(joinStrings(NavigationBarInflaterView.GRAVITY_SEPARATOR, this.allowAppBypassDndList));
        if (Flags.modesApi()) {
            builder2.allowPriorityChannels(this.allowPriorityChannels);
        }
        return builder2.build();
    }

    public NotificationManager.Policy toNotificationPolicy(ZenPolicy zenPolicy) {
        int conversationSenders;
        int state;
        NotificationManager.Policy defaultPolicy = toNotificationPolicy();
        int priorityCategories = 0;
        int suppressedVisualEffects = 0;
        int callSenders = defaultPolicy.priorityCallSenders;
        int messageSenders = defaultPolicy.priorityMessageSenders;
        int conversationSenders2 = defaultPolicy.priorityConversationSenders;
        if (zenPolicy.isCategoryAllowed(0, isPriorityCategoryEnabled(1, defaultPolicy))) {
            priorityCategories = 0 | 1;
        }
        if (zenPolicy.isCategoryAllowed(1, isPriorityCategoryEnabled(2, defaultPolicy))) {
            priorityCategories |= 2;
        }
        if (zenPolicy.isCategoryAllowed(2, isPriorityCategoryEnabled(4, defaultPolicy))) {
            priorityCategories |= 4;
            messageSenders = ZenAdapters.peopleTypeToPrioritySenders(zenPolicy.getPriorityMessageSenders(), messageSenders);
        }
        if (zenPolicy.isCategoryAllowed(8, isPriorityCategoryEnabled(256, defaultPolicy))) {
            priorityCategories |= 256;
            conversationSenders = ZenAdapters.zenPolicyConversationSendersToNotificationPolicy(zenPolicy.getPriorityConversationSenders(), conversationSenders2);
        } else {
            conversationSenders = 3;
        }
        if (zenPolicy.isCategoryAllowed(3, isPriorityCategoryEnabled(8, defaultPolicy))) {
            priorityCategories |= 8;
            callSenders = ZenAdapters.peopleTypeToPrioritySenders(zenPolicy.getPriorityCallSenders(), callSenders);
        }
        if (zenPolicy.isCategoryAllowed(4, isPriorityCategoryEnabled(16, defaultPolicy))) {
            priorityCategories |= 16;
        }
        if (zenPolicy.isCategoryAllowed(5, isPriorityCategoryEnabled(32, defaultPolicy))) {
            priorityCategories |= 32;
        }
        if (zenPolicy.isCategoryAllowed(6, isPriorityCategoryEnabled(64, defaultPolicy))) {
            priorityCategories |= 64;
        }
        if (zenPolicy.isCategoryAllowed(7, isPriorityCategoryEnabled(128, defaultPolicy))) {
            priorityCategories |= 128;
        }
        boolean suppressFullScreenIntent = !zenPolicy.isVisualEffectAllowed(0, isVisualEffectAllowed(4, defaultPolicy));
        boolean suppressLights = !zenPolicy.isVisualEffectAllowed(1, isVisualEffectAllowed(8, defaultPolicy));
        boolean suppressAmbient = !zenPolicy.isVisualEffectAllowed(5, isVisualEffectAllowed(128, defaultPolicy));
        if (suppressFullScreenIntent && suppressLights && suppressAmbient) {
            suppressedVisualEffects = 0 | 1;
        }
        if (suppressFullScreenIntent) {
            suppressedVisualEffects |= 4;
        }
        if (suppressLights) {
            suppressedVisualEffects |= 8;
        }
        if (!zenPolicy.isVisualEffectAllowed(2, isVisualEffectAllowed(16, defaultPolicy))) {
            suppressedVisualEffects = suppressedVisualEffects | 16 | 2;
        }
        if (!zenPolicy.isVisualEffectAllowed(3, isVisualEffectAllowed(32, defaultPolicy))) {
            suppressedVisualEffects |= 32;
        }
        if (!zenPolicy.isVisualEffectAllowed(4, isVisualEffectAllowed(64, defaultPolicy))) {
            suppressedVisualEffects |= 64;
        }
        if (suppressAmbient) {
            suppressedVisualEffects |= 128;
        }
        if (!zenPolicy.isVisualEffectAllowed(6, isVisualEffectAllowed(256, defaultPolicy))) {
            suppressedVisualEffects |= 256;
        }
        List<String> consolidatedContacts = new ArrayList<>();
        if (zenPolicy.isContactsOverridden()) {
            Iterator<String> it = zenPolicy.getExceptionContacts().iterator();
            while (it.hasNext()) {
                String contactInfo = it.next();
                if (!consolidatedContacts.contains(contactInfo)) {
                    consolidatedContacts.add(contactInfo);
                }
            }
        } else {
            consolidatedContacts.addAll(defaultPolicy.getExceptionContacts());
        }
        List<String> appBypassDndList = new ArrayList<>();
        if (zenPolicy.isAppBypassDndOverridden()) {
            Iterator<String> it2 = zenPolicy.getAppsToBypassDnd().iterator();
            while (it2.hasNext()) {
                String appBypassDndInfo = it2.next();
                if (!appBypassDndList.contains(appBypassDndInfo)) {
                    appBypassDndList.add(appBypassDndInfo);
                }
            }
        } else {
            appBypassDndList.addAll(defaultPolicy.getAppBypassDndList());
        }
        int ruleExceptionContactsFlag = zenPolicy.getExceptionContactsFlag();
        int consolidatedExceptionContactsFlag = ruleExceptionContactsFlag == -1 ? defaultPolicy.exceptionContactsFlag : ruleExceptionContactsFlag;
        int ruleAppBypassDndFlag = zenPolicy.getAppBypassDndFlag();
        int consolidatedAppBypassDndFlag = ruleAppBypassDndFlag == -1 ? defaultPolicy.appBypassDndFlag : ruleAppBypassDndFlag;
        int state2 = defaultPolicy.state;
        if (!Flags.modesApi()) {
            state = state2;
        } else {
            int state3 = NotificationManager.Policy.policyState(defaultPolicy.hasPriorityChannels(), ZenPolicy.stateToBoolean(zenPolicy.getPriorityChannelsAllowed(), true));
            state = state3;
        }
        return new NotificationManager.Policy(priorityCategories, callSenders, messageSenders, suppressedVisualEffects, state, conversationSenders, consolidatedExceptionContactsFlag, consolidatedContacts, consolidatedAppBypassDndFlag, appBypassDndList);
    }

    private boolean isPriorityCategoryEnabled(int categoryType, NotificationManager.Policy policy) {
        return (policy.priorityCategories & categoryType) != 0;
    }

    private boolean isVisualEffectAllowed(int visualEffect, NotificationManager.Policy policy) {
        return (policy.suppressedVisualEffects & visualEffect) == 0;
    }

    private boolean isVisualEffectAllowed(int suppressedVisualEffects, int visualEffect) {
        return (suppressedVisualEffects & visualEffect) == 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r5v3, types: [int] */
    /* JADX WARN: Type inference failed for: r5v5, types: [int] */
    public NotificationManager.Policy toNotificationPolicy() {
        int i;
        int sourceToPrioritySenders;
        int sourceToPrioritySenders2;
        int zenPolicyConversationSendersToNotificationPolicy;
        boolean z;
        int i2 = 0;
        if (Flags.modesUi()) {
            i = this.manualRule.zenPolicy.isCategoryAllowed(1, false) ? 0 | 2 : 0;
            if (this.manualRule.zenPolicy.isCategoryAllowed(0, false)) {
                i |= 1;
            }
            if (this.manualRule.zenPolicy.isCategoryAllowed(4, false)) {
                i |= 16;
            }
            if (this.manualRule.zenPolicy.isCategoryAllowed(5, false)) {
                i |= 32;
            }
            if (this.manualRule.zenPolicy.isCategoryAllowed(6, false)) {
                i |= 64;
            }
            if (this.manualRule.zenPolicy.isCategoryAllowed(7, false)) {
                i |= 128;
            }
            if (this.manualRule.zenPolicy.getPriorityCategoryConversations() == 1) {
                i |= 256;
            }
            zenPolicyConversationSendersToNotificationPolicy = ZenAdapters.zenPolicyConversationSendersToNotificationPolicy(this.manualRule.zenPolicy.getPriorityConversationSenders(), 3);
            if (this.manualRule.zenPolicy.getPriorityCategoryCalls() == 1) {
                i |= 8;
            }
            sourceToPrioritySenders = ZenAdapters.peopleTypeToPrioritySenders(this.manualRule.zenPolicy.getPriorityCallSenders(), 2);
            if (this.manualRule.zenPolicy.getPriorityCategoryMessages() == 1) {
                i |= 4;
            }
            sourceToPrioritySenders2 = ZenAdapters.peopleTypeToPrioritySenders(this.manualRule.zenPolicy.getPriorityMessageSenders(), 2);
            ?? policyState = NotificationManager.Policy.policyState(this.areChannelsBypassingDnd, this.manualRule.zenPolicy.getPriorityChannelsAllowed() != 2);
            boolean z2 = !this.manualRule.zenPolicy.isVisualEffectAllowed(0, isVisualEffectAllowed(157, 0));
            boolean z3 = !this.manualRule.zenPolicy.isVisualEffectAllowed(1, isVisualEffectAllowed(157, 1));
            boolean isVisualEffectAllowed = true ^ this.manualRule.zenPolicy.isVisualEffectAllowed(5, isVisualEffectAllowed(157, 5));
            if (z2 && z3 && isVisualEffectAllowed) {
                i2 = 0 | 1;
            }
            if (z2) {
                i2 |= 4;
            }
            if (z3) {
                i2 |= 8;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(2, isVisualEffectAllowed(157, 2))) {
                i2 = i2 | 16 | 2;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(3, isVisualEffectAllowed(157, 3))) {
                i2 |= 32;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(4, isVisualEffectAllowed(157, 4))) {
                i2 |= 64;
            }
            if (isVisualEffectAllowed) {
                i2 |= 128;
            }
            z = policyState;
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(6, isVisualEffectAllowed(157, 6))) {
                i2 |= 256;
                z = policyState;
            }
        } else {
            i = isAllowConversations() ? 0 | 256 : 0;
            if (isAllowCalls()) {
                i |= 8;
            }
            if (isAllowMessages()) {
                i |= 4;
            }
            if (isAllowEvents()) {
                i |= 2;
            }
            if (isAllowReminders()) {
                i |= 1;
            }
            if (isAllowRepeatCallers()) {
                i |= 16;
            }
            if (isAllowAlarms()) {
                i |= 32;
            }
            if (isAllowMedia()) {
                i |= 64;
            }
            if (isAllowSystem()) {
                i |= 128;
            }
            sourceToPrioritySenders = sourceToPrioritySenders(getAllowCallsFrom(), 1);
            sourceToPrioritySenders2 = sourceToPrioritySenders(getAllowMessagesFrom(), 1);
            zenPolicyConversationSendersToNotificationPolicy = ZenAdapters.zenPolicyConversationSendersToNotificationPolicy(getAllowConversationsFrom(), 2);
            boolean z4 = this.areChannelsBypassingDnd;
            if (Flags.modesApi()) {
                z4 = NotificationManager.Policy.policyState(this.areChannelsBypassingDnd, this.allowPriorityChannels);
            }
            i2 = getSuppressedVisualEffects();
            z = z4;
        }
        return new NotificationManager.Policy(i, sourceToPrioritySenders, sourceToPrioritySenders2, i2, z, zenPolicyConversationSendersToNotificationPolicy, this.exceptionContactsFlag, this.allowExceptionContacts, this.appBypassDndFlag, this.allowAppBypassDndList);
    }

    public static ScheduleCalendar toScheduleCalendar(Uri conditionId) {
        ScheduleInfo schedule = tryParseScheduleConditionId(conditionId);
        if (schedule == null || schedule.days == null || schedule.days.length == 0) {
            return null;
        }
        ScheduleCalendar sc = new ScheduleCalendar();
        sc.setSchedule(schedule);
        sc.setTimeZone(TimeZone.getDefault());
        return sc;
    }

    private static int sourceToPrioritySenders(int source, int def) {
        switch (source) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return def;
        }
    }

    private static int normalizePrioritySenders(int prioritySenders, int def) {
        if (prioritySenders != 1 && prioritySenders != 2 && prioritySenders != 0) {
            return def;
        }
        return prioritySenders;
    }

    private static int normalizeConversationSenders(boolean allowed, int senders, int def) {
        if (!allowed) {
            return 3;
        }
        if (senders != 1 && senders != 2 && senders != 3) {
            return def;
        }
        return senders;
    }

    public void applyNotificationPolicy(NotificationManager.Policy policy) {
        if (policy == null) {
            return;
        }
        if (Flags.modesUi()) {
            this.manualRule.zenPolicy = ZenAdapters.notificationPolicyToZenPolicy(policy);
        } else {
            setAllowAlarms((policy.priorityCategories & 32) != 0);
            this.allowMedia = (policy.priorityCategories & 64) != 0;
            this.allowSystem = (policy.priorityCategories & 128) != 0;
            this.allowEvents = (policy.priorityCategories & 2) != 0;
            this.allowReminders = (policy.priorityCategories & 1) != 0;
            this.allowCalls = (policy.priorityCategories & 8) != 0;
            this.allowMessages = (policy.priorityCategories & 4) != 0;
            this.allowRepeatCallers = (policy.priorityCategories & 16) != 0;
            this.allowCallsFrom = normalizePrioritySenders(policy.priorityCallSenders, this.allowCallsFrom);
            this.allowMessagesFrom = normalizePrioritySenders(policy.priorityMessageSenders, this.allowMessagesFrom);
            if (policy.suppressedVisualEffects != -1) {
                this.suppressedVisualEffects = policy.suppressedVisualEffects;
            }
            this.allowConversations = (policy.priorityCategories & 256) != 0;
            this.allowConversationsFrom = normalizeConversationSenders(this.allowConversations, policy.priorityConversationSenders, this.allowConversationsFrom);
            if (policy.state != -1 && Flags.modesApi()) {
                setAllowPriorityChannels(policy.allowPriorityChannels());
            }
        }
        this.exceptionContactsFlag = policy.exceptionContactsFlag;
        if (policy.getExceptionContacts() != null) {
            this.allowExceptionContacts = policy.getExceptionContacts();
        }
        this.appBypassDndFlag = policy.appBypassDndFlag;
        if (policy.getAppBypassDndList() != null) {
            synchronized (ZenConfigLock) {
                this.allowAppBypassDndList = policy.getAppBypassDndList();
            }
        }
        if (policy.state != -1) {
            this.areChannelsBypassingDnd = (policy.state & 1) != 0;
        }
    }

    public static Condition toTimeCondition(Context context, int minutesFromNow, int userHandle) {
        return toTimeCondition(context, minutesFromNow, userHandle, false);
    }

    public static Condition toTimeCondition(Context context, int minutesFromNow, int userHandle, boolean shortVersion) {
        long now = System.currentTimeMillis();
        long millis = minutesFromNow == 0 ? JobInfo.MIN_BACKOFF_MILLIS : 60000 * minutesFromNow;
        return toTimeCondition(context, now + millis, minutesFromNow, userHandle, shortVersion);
    }

    public static Condition toTimeCondition(Context context, long time, int minutes, int userHandle, boolean shortVersion) {
        String line2;
        String line1;
        String summary;
        Object formattedTime = getFormattedTime(context, time, isToday(time), userHandle);
        Resources res = context.getResources();
        Map<String, Object> arguments = new HashMap<>();
        if (minutes < 60) {
            int summaryResId = shortVersion ? R.string.zen_mode_duration_minutes_summary_short : R.string.zen_mode_duration_minutes_summary;
            arguments.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf(minutes));
            arguments.put("formattedTime", formattedTime);
            summary = PluralsMessageFormatter.format(res, arguments, summaryResId);
            if (!shortVersion) {
                line1 = res.getQuantityString(R.plurals.zen_mode_duration_time_minutes, minutes, Integer.valueOf(minutes));
            } else {
                line1 = PluralsMessageFormatter.format(res, arguments, R.string.zen_mode_duration_minutes_short);
            }
            line2 = res.getString(R.string.zen_mode_until, formattedTime);
        } else if (minutes < 1440) {
            int num = Math.round(minutes / 60.0f);
            int summaryResId2 = shortVersion ? R.string.zen_mode_duration_hours_summary_short : R.string.zen_mode_duration_hours_summary;
            arguments.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf(num));
            arguments.put("formattedTime", formattedTime);
            summary = PluralsMessageFormatter.format(res, arguments, summaryResId2);
            if (shortVersion) {
                line1 = PluralsMessageFormatter.format(res, arguments, R.string.zen_mode_duration_hours_short);
            } else {
                line1 = res.getQuantityString(R.plurals.zen_mode_duration_time_hours, num, Integer.valueOf(num));
            }
            line2 = res.getString(R.string.zen_mode_until, formattedTime);
        } else {
            String string = res.getString(R.string.zen_mode_until_next_day, formattedTime);
            line2 = string;
            line1 = string;
            summary = string;
        }
        Uri id = toCountdownConditionId(time, false);
        return new Condition(id, summary, line1, line2, 0, 1, 1);
    }

    public static Condition toNextAlarmCondition(Context context, long alarm, int userHandle) {
        boolean isSameDay = isToday(alarm);
        CharSequence formattedTime = getFormattedTime(context, alarm, isSameDay, userHandle);
        Resources res = context.getResources();
        String line1 = res.getString(R.string.zen_mode_until, formattedTime);
        Uri id = toCountdownConditionId(alarm, true);
        return new Condition(id, "", line1, "", 0, 1, 1);
    }

    public static CharSequence getFormattedTime(Context context, long time, boolean isSameDay, int userHandle) {
        String skeleton = (!isSameDay ? "EEE " : "") + (DateFormat.is24HourFormat(context, userHandle) ? "Hm" : "hma");
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton);
        return DateFormat.format(pattern, time);
    }

    public static boolean isToday(long time) {
        GregorianCalendar now = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();
        endTime.setTimeInMillis(time);
        if (now.get(1) == endTime.get(1) && now.get(2) == endTime.get(2) && now.get(5) == endTime.get(5)) {
            return true;
        }
        return false;
    }

    public static Uri toCountdownConditionId(long time, boolean alarm) {
        return new Uri.Builder().scheme("condition").authority("android").appendPath(COUNTDOWN_PATH).appendPath(Long.toString(time)).appendPath("alarm").appendPath(Boolean.toString(alarm)).build();
    }

    public static long tryParseCountdownConditionId(Uri conditionId) {
        if (!Condition.isValidId(conditionId, "android") || conditionId.getPathSegments().size() < 2 || !COUNTDOWN_PATH.equals(conditionId.getPathSegments().get(0))) {
            return 0L;
        }
        try {
            return Long.parseLong(conditionId.getPathSegments().get(1));
        } catch (RuntimeException e) {
            Slog.w(TAG, "Error parsing countdown condition: " + conditionId, e);
            return 0L;
        }
    }

    public static boolean isValidCountdownConditionId(Uri conditionId) {
        return tryParseCountdownConditionId(conditionId) != 0;
    }

    public static boolean isValidCountdownToAlarmConditionId(Uri conditionId) {
        if (tryParseCountdownConditionId(conditionId) == 0 || conditionId.getPathSegments().size() < 4 || !"alarm".equals(conditionId.getPathSegments().get(2))) {
            return false;
        }
        try {
            return Boolean.parseBoolean(conditionId.getPathSegments().get(3));
        } catch (RuntimeException e) {
            Slog.w(TAG, "Error parsing countdown alarm condition: " + conditionId, e);
            return false;
        }
    }

    public static Uri toScheduleConditionId(ScheduleInfo schedule) {
        return new Uri.Builder().scheme("condition").authority("android").appendPath(SCHEDULE_PATH).appendQueryParameter("days", toDayList(schedule.days)).appendQueryParameter("start", schedule.startHour + MediaMetrics.SEPARATOR + schedule.startMinute).appendQueryParameter("end", schedule.endHour + MediaMetrics.SEPARATOR + schedule.endMinute).appendQueryParameter("exitAtAlarm", String.valueOf(schedule.exitAtAlarm)).build();
    }

    public static boolean isValidScheduleConditionId(Uri conditionId) {
        try {
            ScheduleInfo info = tryParseScheduleConditionId(conditionId);
            if (info == null || info.days == null || info.days.length == 0) {
                return false;
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return false;
        }
    }

    public static boolean isValidScheduleConditionId(Uri conditionId, boolean allowNever) {
        try {
            ScheduleInfo info = tryParseScheduleConditionId(conditionId);
            if (info != null) {
                if (allowNever) {
                    return true;
                }
                if (info.days != null && info.days.length != 0) {
                    return true;
                }
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return false;
        }
    }

    public static ScheduleInfo tryParseScheduleConditionId(Uri conditionId) {
        boolean isSchedule = conditionId != null && "condition".equals(conditionId.getScheme()) && "android".equals(conditionId.getAuthority()) && conditionId.getPathSegments().size() == 1 && SCHEDULE_PATH.equals(conditionId.getPathSegments().get(0));
        if (!isSchedule) {
            return null;
        }
        int[] start = tryParseHourAndMinute(conditionId.getQueryParameter("start"));
        int[] end = tryParseHourAndMinute(conditionId.getQueryParameter("end"));
        if (start == null || end == null) {
            return null;
        }
        ScheduleInfo rt = new ScheduleInfo();
        rt.days = tryParseDayList(conditionId.getQueryParameter("days"), "\\.");
        rt.startHour = start[0];
        rt.startMinute = start[1];
        rt.endHour = end[0];
        rt.endMinute = end[1];
        rt.exitAtAlarm = safeBoolean(conditionId.getQueryParameter("exitAtAlarm"), false);
        return rt;
    }

    public static ComponentName getScheduleConditionProvider() {
        return new ComponentName("android", "ScheduleConditionProvider");
    }

    public static class ScheduleInfo {
        public int[] days;
        public int endHour;
        public int endMinute;
        public boolean exitAtAlarm;
        public long nextAlarm;
        public int startHour;
        public int startMinute;

        public int hashCode() {
            return 0;
        }

        public boolean equals(Object o) {
            if (!(o instanceof ScheduleInfo)) {
                return false;
            }
            ScheduleInfo other = (ScheduleInfo) o;
            return ZenModeConfig.toDayList(this.days).equals(ZenModeConfig.toDayList(other.days)) && this.startHour == other.startHour && this.startMinute == other.startMinute && this.endHour == other.endHour && this.endMinute == other.endMinute && this.exitAtAlarm == other.exitAtAlarm;
        }

        public ScheduleInfo copy() {
            ScheduleInfo rt = new ScheduleInfo();
            if (this.days != null) {
                rt.days = new int[this.days.length];
                System.arraycopy(this.days, 0, rt.days, 0, this.days.length);
            }
            rt.startHour = this.startHour;
            rt.startMinute = this.startMinute;
            rt.endHour = this.endHour;
            rt.endMinute = this.endMinute;
            rt.exitAtAlarm = this.exitAtAlarm;
            rt.nextAlarm = this.nextAlarm;
            return rt;
        }

        public String toString() {
            return "ScheduleInfo{days=" + Arrays.toString(this.days) + ", startHour=" + this.startHour + ", startMinute=" + this.startMinute + ", endHour=" + this.endHour + ", endMinute=" + this.endMinute + ", exitAtAlarm=" + this.exitAtAlarm + ", nextAlarm=" + ts(this.nextAlarm) + '}';
        }

        protected static String ts(long time) {
            return new Date(time) + " (" + time + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static Uri toEventConditionId(EventInfo event) {
        return new Uri.Builder().scheme("condition").authority("android").appendPath("event").appendQueryParameter(SmLib_IafdConstant.KEY_USER_ID, Long.toString(event.userId)).appendQueryParameter("calendar", event.calName != null ? event.calName : "").appendQueryParameter("calendarId", event.calendarId != null ? event.calendarId.toString() : "").appendQueryParameter("reply", Integer.toString(event.reply)).build();
    }

    public static boolean isValidEventConditionId(Uri conditionId) {
        return tryParseEventConditionId(conditionId) != null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0034, code lost:
    
        if ("event".equals(r6.getPathSegments().get(0)) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.service.notification.ZenModeConfig.EventInfo tryParseEventConditionId(android.net.Uri r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L37
            java.lang.String r1 = r6.getScheme()
            java.lang.String r2 = "condition"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L37
            java.lang.String r1 = r6.getAuthority()
            java.lang.String r2 = "android"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L37
            java.util.List r1 = r6.getPathSegments()
            int r1 = r1.size()
            r2 = 1
            if (r1 != r2) goto L37
            java.util.List r1 = r6.getPathSegments()
            java.lang.Object r1 = r1.get(r0)
            java.lang.String r3 = "event"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L37
            goto L38
        L37:
            r2 = r0
        L38:
            r1 = r2
            r2 = 0
            if (r1 != 0) goto L3d
            return r2
        L3d:
            android.service.notification.ZenModeConfig$EventInfo r3 = new android.service.notification.ZenModeConfig$EventInfo
            r3.<init>()
            java.lang.String r4 = "userId"
            java.lang.String r4 = r6.getQueryParameter(r4)
            r5 = -10000(0xffffffffffffd8f0, float:NaN)
            int r4 = tryParseInt(r4, r5)
            r3.userId = r4
            java.lang.String r4 = "calendar"
            java.lang.String r4 = r6.getQueryParameter(r4)
            r3.calName = r4
            java.lang.String r4 = r3.calName
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L63
            r3.calName = r2
        L63:
            java.lang.String r4 = "calendarId"
            java.lang.String r4 = r6.getQueryParameter(r4)
            java.lang.Long r2 = tryParseLong(r4, r2)
            r3.calendarId = r2
            java.lang.String r2 = "reply"
            java.lang.String r2 = r6.getQueryParameter(r2)
            int r0 = tryParseInt(r2, r0)
            r3.reply = r0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.service.notification.ZenModeConfig.tryParseEventConditionId(android.net.Uri):android.service.notification.ZenModeConfig$EventInfo");
    }

    public static ComponentName getEventConditionProvider() {
        return new ComponentName("android", "EventConditionProvider");
    }

    public static class EventInfo {
        public static final int REPLY_ANY_EXCEPT_NO = 0;
        public static final int REPLY_YES = 2;
        public static final int REPLY_YES_OR_MAYBE = 1;
        public String calName;
        public Long calendarId;
        public int reply;
        public int userId = -10000;

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.userId), this.calName, this.calendarId, Integer.valueOf(this.reply));
        }

        public boolean equals(Object o) {
            if (!(o instanceof EventInfo)) {
                return false;
            }
            EventInfo other = (EventInfo) o;
            return this.userId == other.userId && Objects.equals(this.calName, other.calName) && this.reply == other.reply && Objects.equals(this.calendarId, other.calendarId);
        }

        public EventInfo copy() {
            EventInfo rt = new EventInfo();
            rt.userId = this.userId;
            rt.calName = this.calName;
            rt.reply = this.reply;
            rt.calendarId = this.calendarId;
            return rt;
        }

        public static int resolveUserId(int userId) {
            return userId == -10000 ? ActivityManager.getCurrentUser() : userId;
        }
    }

    private static int[] tryParseHourAndMinute(String value) {
        int i;
        if (TextUtils.isEmpty(value) || (i = value.indexOf(46)) < 1 || i >= value.length() - 1) {
            return null;
        }
        int hour = tryParseInt(value.substring(0, i), -1);
        int minute = tryParseInt(value.substring(i + 1), -1);
        if (isValidHour(hour) && isValidMinute(minute)) {
            return new int[]{hour, minute};
        }
        return null;
    }

    private static int tryParseZenMode(String value, int defValue) {
        int rt = tryParseInt(value, defValue);
        return Settings.Global.isValidZenMode(rt) ? rt : defValue;
    }

    public static String newRuleId() {
        return UUID.randomUUID().toString().replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, "");
    }

    public static String getOwnerCaption(Context context, String owner) {
        CharSequence seq;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(owner, 0);
            if (info != null && (seq = info.loadLabel(pm)) != null) {
                String str = seq.toString().trim();
                if (str.length() > 0) {
                    return str;
                }
                return "";
            }
            return "";
        } catch (Throwable e) {
            Slog.w(TAG, "Error loading owner caption", e);
            return "";
        }
    }

    public boolean isManualActive() {
        return !Flags.modesUi() ? this.manualRule != null : this.manualRule != null && this.manualRule.isAutomaticActive();
    }

    public static class ZenRule implements Parcelable {
        public static final Parcelable.Creator<ZenRule> CREATOR = new Parcelable.Creator<ZenRule>() { // from class: android.service.notification.ZenModeConfig.ZenRule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ZenRule createFromParcel(Parcel source) {
                return new ZenRule(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ZenRule[] newArray(int size) {
                return new ZenRule[size];
            }
        };
        public boolean allowManualInvocation;
        public ComponentName component;
        public Condition condition;
        public Uri conditionId;
        public ComponentName configurationActivity;
        public long creationTime;
        public Instant deletionInstant;
        public boolean enabled;
        public String enabler;
        public String iconResName;
        public String id;
        public boolean modified;
        public String name;
        public String pkg;
        public boolean snoozing;
        public String triggerDescription;
        public int type;
        public int userModifiedFields;
        public ZenDeviceEffects zenDeviceEffects;
        public int zenDeviceEffectsUserModifiedFields;
        public int zenMode;
        public ZenPolicy zenPolicy;
        public int zenPolicyUserModifiedFields;

        public ZenRule() {
            this.type = -1;
        }

        public ZenRule(Parcel source) {
            this.type = -1;
            this.enabled = source.readInt() == 1;
            this.snoozing = source.readInt() == 1;
            if (source.readInt() == 1) {
                this.name = source.readString();
            }
            this.zenMode = source.readInt();
            this.conditionId = (Uri) source.readParcelable(null, Uri.class);
            this.condition = (Condition) source.readParcelable(null, Condition.class);
            this.component = (ComponentName) source.readParcelable(null, ComponentName.class);
            this.configurationActivity = (ComponentName) source.readParcelable(null, ComponentName.class);
            if (source.readInt() == 1) {
                this.id = source.readString();
            }
            this.creationTime = source.readLong();
            if (source.readInt() == 1) {
                this.enabler = source.readString();
            }
            this.zenPolicy = (ZenPolicy) source.readParcelable(null, ZenPolicy.class);
            if (Flags.modesApi()) {
                this.zenDeviceEffects = (ZenDeviceEffects) source.readParcelable(null, ZenDeviceEffects.class);
            }
            this.modified = source.readInt() == 1;
            this.pkg = source.readString();
            if (Flags.modesApi()) {
                this.allowManualInvocation = source.readBoolean();
                this.iconResName = source.readString();
                this.triggerDescription = source.readString();
                this.type = source.readInt();
                this.userModifiedFields = source.readInt();
                this.zenPolicyUserModifiedFields = source.readInt();
                this.zenDeviceEffectsUserModifiedFields = source.readInt();
                if (source.readInt() == 1) {
                    this.deletionInstant = Instant.ofEpochMilli(source.readLong());
                }
            }
        }

        public boolean canBeUpdatedByApp() {
            return this.userModifiedFields == 0 && this.zenPolicyUserModifiedFields == 0 && this.zenDeviceEffectsUserModifiedFields == 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.enabled ? 1 : 0);
            parcel.writeInt(this.snoozing ? 1 : 0);
            if (this.name != null) {
                parcel.writeInt(1);
                parcel.writeString(this.name);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeInt(this.zenMode);
            parcel.writeParcelable(this.conditionId, 0);
            parcel.writeParcelable(this.condition, 0);
            parcel.writeParcelable(this.component, 0);
            parcel.writeParcelable(this.configurationActivity, 0);
            if (this.id != null) {
                parcel.writeInt(1);
                parcel.writeString(this.id);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeLong(this.creationTime);
            if (this.enabler != null) {
                parcel.writeInt(1);
                parcel.writeString(this.enabler);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeParcelable(this.zenPolicy, 0);
            if (Flags.modesApi()) {
                parcel.writeParcelable(this.zenDeviceEffects, 0);
            }
            parcel.writeInt(this.modified ? 1 : 0);
            parcel.writeString(this.pkg);
            if (Flags.modesApi()) {
                parcel.writeBoolean(this.allowManualInvocation);
                parcel.writeString(this.iconResName);
                parcel.writeString(this.triggerDescription);
                parcel.writeInt(this.type);
                parcel.writeInt(this.userModifiedFields);
                parcel.writeInt(this.zenPolicyUserModifiedFields);
                parcel.writeInt(this.zenDeviceEffectsUserModifiedFields);
                if (this.deletionInstant != null) {
                    parcel.writeInt(1);
                    parcel.writeLong(this.deletionInstant.toEpochMilli());
                } else {
                    parcel.writeInt(0);
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(ZenRule.class.getSimpleName()).append('[').append("id=").append(this.id).append(",state=").append(this.condition == null ? "STATE_FALSE" : Condition.stateToString(this.condition.state)).append(",enabled=").append(String.valueOf(this.enabled).toUpperCase()).append(",snoozing=").append(this.snoozing).append(",name=").append(this.name).append(",zenMode=").append(Settings.Global.zenModeToString(this.zenMode)).append(",conditionId=").append(this.conditionId).append(",pkg=").append(this.pkg).append(",component=").append(this.component).append(",configActivity=").append(this.configurationActivity).append(",creationTime=").append(this.creationTime).append(",enabler=").append(this.enabler).append(",zenPolicy=").append(this.zenPolicy).append(",modified=").append(this.modified).append(",condition=").append(this.condition);
            if (Flags.modesApi()) {
                sb.append(",deviceEffects=").append(this.zenDeviceEffects).append(",allowManualInvocation=").append(this.allowManualInvocation).append(",iconResName=").append(this.iconResName).append(",triggerDescription=").append(this.triggerDescription).append(",type=").append(this.type);
                if (this.userModifiedFields != 0) {
                    sb.append(",userModifiedFields=").append(AutomaticZenRule.fieldsToString(this.userModifiedFields));
                }
                if (this.zenPolicyUserModifiedFields != 0) {
                    sb.append(",zenPolicyUserModifiedFields=").append(ZenPolicy.fieldsToString(this.zenPolicyUserModifiedFields));
                }
                if (this.zenDeviceEffectsUserModifiedFields != 0) {
                    sb.append(",zenDeviceEffectsUserModifiedFields=").append(ZenDeviceEffects.fieldsToString(this.zenDeviceEffectsUserModifiedFields));
                }
                if (this.deletionInstant != null) {
                    sb.append(",deletionInstant=").append(this.deletionInstant);
                }
            }
            return sb.append(']').toString();
        }

        public void dumpDebug(ProtoOutputStream proto, long fieldId) {
            long token = proto.start(fieldId);
            proto.write(1138166333441L, this.id);
            proto.write(1138166333442L, this.name);
            proto.write(1112396529667L, this.creationTime);
            proto.write(1133871366148L, this.enabled);
            proto.write(1138166333445L, this.enabler);
            proto.write(1133871366150L, this.snoozing);
            proto.write(1159641169927L, this.zenMode);
            if (this.conditionId != null) {
                proto.write(1138166333448L, this.conditionId.toString());
            }
            if (this.condition != null) {
                this.condition.dumpDebug(proto, 1146756268041L);
            }
            if (this.component != null) {
                this.component.dumpDebug(proto, 1146756268042L);
            }
            if (this.zenPolicy != null) {
                this.zenPolicy.dumpDebug(proto, 1146756268043L);
            }
            proto.write(1133871366156L, this.modified);
            proto.end(token);
        }

        public boolean equals(Object o) {
            if (!(o instanceof ZenRule)) {
                return false;
            }
            if (o == this) {
                return true;
            }
            ZenRule other = (ZenRule) o;
            boolean finalEquals = other.enabled == this.enabled && other.snoozing == this.snoozing && Objects.equals(other.name, this.name) && other.zenMode == this.zenMode && Objects.equals(other.conditionId, this.conditionId) && Objects.equals(other.condition, this.condition) && Objects.equals(other.component, this.component) && Objects.equals(other.configurationActivity, this.configurationActivity) && Objects.equals(other.id, this.id) && Objects.equals(other.enabler, this.enabler) && Objects.equals(other.zenPolicy, this.zenPolicy) && Objects.equals(other.pkg, this.pkg) && other.modified == this.modified;
            if (Flags.modesApi()) {
                return finalEquals && Objects.equals(other.zenDeviceEffects, this.zenDeviceEffects) && other.allowManualInvocation == this.allowManualInvocation && Objects.equals(other.iconResName, this.iconResName) && Objects.equals(other.triggerDescription, this.triggerDescription) && other.type == this.type && other.userModifiedFields == this.userModifiedFields && other.zenPolicyUserModifiedFields == this.zenPolicyUserModifiedFields && other.zenDeviceEffectsUserModifiedFields == this.zenDeviceEffectsUserModifiedFields && Objects.equals(other.deletionInstant, this.deletionInstant);
            }
            return finalEquals;
        }

        public int hashCode() {
            if (Flags.modesApi()) {
                return Objects.hash(Boolean.valueOf(this.enabled), Boolean.valueOf(this.snoozing), this.name, Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, this.zenDeviceEffects, Boolean.valueOf(this.modified), Boolean.valueOf(this.allowManualInvocation), this.iconResName, this.triggerDescription, Integer.valueOf(this.type), Integer.valueOf(this.userModifiedFields), Integer.valueOf(this.zenPolicyUserModifiedFields), Integer.valueOf(this.zenDeviceEffectsUserModifiedFields), this.deletionInstant);
            }
            return Objects.hash(Boolean.valueOf(this.enabled), Boolean.valueOf(this.snoozing), this.name, Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, Boolean.valueOf(this.modified));
        }

        public ZenRule copy() {
            Parcel parcel = Parcel.obtain();
            try {
                writeToParcel(parcel, 0);
                parcel.setDataPosition(0);
                return new ZenRule(parcel);
            } finally {
                parcel.recycle();
            }
        }

        public boolean isAutomaticActive() {
            return this.enabled && !this.snoozing && getPkg() != null && isTrueOrUnknown();
        }

        public String getPkg() {
            if (!TextUtils.isEmpty(this.pkg)) {
                return this.pkg;
            }
            if (this.component != null) {
                return this.component.getPackageName();
            }
            if (this.configurationActivity != null) {
                return this.configurationActivity.getPackageName();
            }
            return null;
        }

        public boolean isTrueOrUnknown() {
            return this.condition != null && (this.condition.state == 1 || this.condition.state == 2);
        }
    }

    public static boolean areAllPriorityOnlyRingerSoundsMuted(NotificationManager.Policy policy) {
        boolean allowReminders = (policy.priorityCategories & 1) != 0;
        boolean allowCalls = (policy.priorityCategories & 8) != 0;
        boolean allowMessages = (policy.priorityCategories & 4) != 0;
        boolean allowEvents = (policy.priorityCategories & 2) != 0;
        boolean allowRepeatCallers = (policy.priorityCategories & 16) != 0;
        boolean allowConversations = (policy.priorityCategories & 256) != 0;
        boolean areChannelsBypassingDnd = (policy.state & 1) != 0;
        if (Flags.modesApi()) {
            areChannelsBypassingDnd = policy.hasPriorityChannels() && policy.allowPriorityChannels();
        }
        boolean areChannelsBypassingDnd2 = areChannelsBypassingDnd || policy.appBypassDndFlag == 1;
        boolean allowSystem = (policy.priorityCategories & 128) != 0;
        boolean isExceptionContactsAllowed = !(policy.exceptionContactsFlag != 0 || policy.getExceptionContacts() == null || policy.getExceptionContacts().isEmpty()) || (policy.exceptionContactsFlag == 1 && policy.getExceptionContacts() != null);
        return (allowReminders || allowCalls || allowMessages || allowEvents || allowRepeatCallers || areChannelsBypassingDnd2 || allowSystem || allowConversations || isExceptionContactsAllowed) ? false : true;
    }

    public static boolean areAllZenBehaviorSoundsMuted(NotificationManager.Policy policy) {
        boolean allowAlarms = (policy.priorityCategories & 32) != 0;
        boolean allowMedia = (policy.priorityCategories & 64) != 0;
        return (allowAlarms || allowMedia || !areAllPriorityOnlyRingerSoundsMuted(policy)) ? false : true;
    }

    public static boolean isZenOverridingRinger(int zen, NotificationManager.Policy consolidatedPolicy) {
        if (zen == 2 || zen == 3) {
            return true;
        }
        return zen == 1 && areAllPriorityOnlyRingerSoundsMuted(consolidatedPolicy);
    }

    public static boolean areAllPriorityOnlyRingerSoundsMuted(ZenModeConfig config) {
        if (Flags.modesUi()) {
            ZenPolicy policy = config.manualRule.zenPolicy;
            return (policy.isCategoryAllowed(0, false) || policy.isCategoryAllowed(3, false) || policy.isCategoryAllowed(2, false) || policy.isCategoryAllowed(1, false) || policy.isCategoryAllowed(4, false) || policy.isCategoryAllowed(7, false) || (config.areChannelsBypassingDnd && policy.getPriorityChannelsAllowed() == 1)) ? false : true;
        }
        boolean areChannelsBypassingDnd = config.areChannelsBypassingDnd;
        if (Flags.modesApi()) {
            areChannelsBypassingDnd = config.areChannelsBypassingDnd && config.isAllowPriorityChannels();
        }
        return (config.isAllowReminders() || config.isAllowCalls() || config.isAllowMessages() || config.isAllowEvents() || config.isAllowRepeatCallers() || areChannelsBypassingDnd || config.isAllowSystem() || config.allowExceptionContacts == null || !config.allowExceptionContacts.isEmpty()) ? false : true;
    }

    public static boolean areAllZenBehaviorSoundsMuted(ZenModeConfig config) {
        if (!Flags.modesUi()) {
            return (config.isAllowAlarms() || config.isAllowMedia() || !areAllPriorityOnlyRingerSoundsMuted(config)) ? false : true;
        }
        ZenPolicy policy = config.manualRule.zenPolicy;
        return (policy.isCategoryAllowed(5, false) || policy.isCategoryAllowed(6, false) || !areAllPriorityOnlyRingerSoundsMuted(config)) ? false : true;
    }

    public static String getDescription(Context context, boolean zenOn, ZenModeConfig config, boolean describeForeverCondition) {
        if (!zenOn || config == null) {
            return null;
        }
        String secondaryText = "";
        long latestEndTime = -1;
        if (config.manualRule != null) {
            Uri id = config.manualRule.conditionId;
            if (config.manualRule.enabler != null) {
                String appName = getOwnerCaption(context, config.manualRule.enabler);
                if (!appName.isEmpty()) {
                    secondaryText = appName;
                }
            } else {
                if (id == null) {
                    if (!describeForeverCondition) {
                        return null;
                    }
                    return context.getString(R.string.zen_mode_forever);
                }
                latestEndTime = tryParseCountdownConditionId(id);
                if (latestEndTime > 0) {
                    CharSequence formattedTime = getFormattedTime(context, latestEndTime, isToday(latestEndTime), context.getUserId());
                    secondaryText = context.getString(R.string.zen_mode_until, formattedTime);
                }
            }
        }
        for (ZenRule automaticRule : config.automaticRules.values()) {
            if (automaticRule.isAutomaticActive()) {
                if (isValidEventConditionId(automaticRule.conditionId) || isValidScheduleConditionId(automaticRule.conditionId)) {
                    long endTime = parseAutomaticRuleEndTime(context, automaticRule.conditionId);
                    if (endTime > latestEndTime) {
                        latestEndTime = endTime;
                        secondaryText = automaticRule.name;
                    }
                } else {
                    return automaticRule.name;
                }
            }
        }
        if (secondaryText.equals("")) {
            return null;
        }
        return secondaryText;
    }

    private static long parseAutomaticRuleEndTime(Context context, Uri id) {
        if (isValidEventConditionId(id)) {
            return Long.MAX_VALUE;
        }
        if (isValidScheduleConditionId(id)) {
            ScheduleCalendar schedule = toScheduleCalendar(id);
            long endTimeMs = schedule.getNextChangeTime(System.currentTimeMillis());
            if (schedule.exitAtAlarm()) {
                long nextAlarm = getNextAlarm(context);
                schedule.maybeSetNextAlarm(System.currentTimeMillis(), nextAlarm);
                if (schedule.shouldExitForAlarm(endTimeMs)) {
                    return nextAlarm;
                }
            }
            return endTimeMs;
        }
        return -1L;
    }

    private static long getNextAlarm(Context context) {
        AlarmManager alarms = (AlarmManager) context.getSystemService("alarm");
        AlarmManager.AlarmClockInfo info = alarms.getNextAlarmClock(context.getUserId());
        if (info != null) {
            return info.getTriggerTime();
        }
        return 0L;
    }

    public static String joinStrings(String separator, List<String> strings) {
        String sb;
        synchronized (ZenConfigLock) {
            StringBuilder sb2 = new StringBuilder();
            if (strings != null && !strings.isEmpty()) {
                for (String s : strings) {
                    if (strings.indexOf(s) != 0) {
                        sb2.append(separator);
                    }
                    sb2.append(s);
                }
            }
            sb = sb2.toString();
        }
        return sb;
    }
}
