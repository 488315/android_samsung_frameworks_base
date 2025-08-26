package android.service.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AutomaticZenRule;
import android.app.Flags;
import android.app.NotificationManager;
import android.app.backup.BackupRestoreEventLogger;
import android.app.backup.NotificationLoggingConstants;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
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
    private static final boolean DEFAULT_HAS_PRIORITY_CHANNELS = false;
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
    private static final String DEVICE_EFFECT_USE_NIGHT_LIGHT = "zdeUseNightLight";
    private static final String DEVICE_EFFECT_USE_NIGHT_MODE = "zdeUseNightMode";
    private static final String DISALLOW_ATT_VISUAL_EFFECTS = "visualEffects";
    private static final String DISALLOW_TAG = "disallow";
    public static final String EVENTS_OBSOLETE_RULE_ID = "EVENTS_DEFAULT_RULE";
    public static final String EVENT_PATH = "event";
    public static final String EVERY_NIGHT_DEFAULT_RULE_ID = "EVERY_NIGHT_DEFAULT_RULE";
    private static final String IMPLICIT_RULE_ID_PREFIX = "implicit_";
    public static final String IS_ALARM_PATH = "alarm";
    private static final String ITEM_SEPARATOR = ",";
    private static final String ITEM_SEPARATOR_ESCAPE = "\\";
    private static final int LEGACY_SUPPRESSED_EFFECTS = 3;
    public static final String MANUAL_RULE_ID = "MANUAL_RULE";
    private static final String MANUAL_TAG = "manual";
    private static final int MAX_SOURCE = 2;
    private static final int MINUTES_MS = 60000;
    public static final int ORIGIN_APP = 4;
    public static final int ORIGIN_INIT = 1;
    public static final int ORIGIN_INIT_USER = 2;
    public static final int ORIGIN_RESTORE_BACKUP = 6;
    public static final int ORIGIN_SYSTEM = 5;
    public static final int ORIGIN_UNKNOWN = 0;
    public static final int ORIGIN_USER_IN_APP = 7;
    public static final int ORIGIN_USER_IN_SYSTEMUI = 3;
    private static final String POLICY_USER_MODIFIED_FIELDS = "policyUserModifiedFields";
    private static final String RULE_ATT_ALLOW_MANUAL = "userInvokable";
    private static final String RULE_ATT_COMPONENT = "component";
    private static final String RULE_ATT_CONDITION_ID = "conditionId";
    private static final String RULE_ATT_CONDITION_OVERRIDE = "conditionOverride";
    private static final String RULE_ATT_CONFIG_ACTIVITY = "configActivity";
    private static final String RULE_ATT_CREATION_TIME = "creationTime";
    private static final String RULE_ATT_DELETION_INSTANT = "deletionInstant";
    private static final String RULE_ATT_DISABLED_ORIGIN = "disabledOrigin";
    private static final String RULE_ATT_ENABLED = "enabled";
    private static final String RULE_ATT_ENABLER = "enabler";
    private static final String RULE_ATT_ICON = "rule_icon";
    private static final String RULE_ATT_ID = "ruleId";
    private static final String RULE_ATT_LAST_ACTIVATION = "lastActivation";
    private static final String RULE_ATT_LEGACY_SUPPRESSED_EFFECTS = "legacySuppressedEffects";
    private static final String RULE_ATT_NAME = "name";
    private static final String RULE_ATT_PKG = "pkg";
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
    private static final String STATE_HAS_PRIORITY_CHANNELS = "areChannelsBypassingDnd";
    private static final String STATE_TAG = "state";
    public static final String SYSTEM_AUTHORITY = "android";
    private static final String TAG = "ZenModeConfig";
    public static final String TW_SCHEDULED_DEFAULT_RULE_ID = "SCHEDULED_DEFAULT_RULE";
    public static final int XML_VERSION_MODES_API = 11;
    public static final int XML_VERSION_MODES_UI = 12;
    public static final int XML_VERSION_ZEN_UPGRADE = 8;
    private static final String ZEN_ATT_USER = "user";
    private static final String ZEN_ATT_VERSION = "version";
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
    public ArrayMap<String, ZenRule> automaticRules;
    public final ArrayMap<String, ZenRule> deletedRules;
    public int exceptionContactsFlag;
    public boolean hasPriorityChannels;
    public ZenRule manualRule;
    public int suppressedVisualEffects;
    public int user;
    public int version;
    public static final int[] ALL_DAYS = {1, 2, 3, 4, 5, 6, 7};
    public static final int[] MINUTE_BUCKETS = generateMinuteBuckets();
    public static final Object ZenConfigLock = new Object();
    private static final Pattern ITEM_SPLITTER_REGEX = Pattern.compile("(?<!\\\\),");
    public static final Parcelable.Creator<ZenModeConfig> CREATOR = new Parcelable.Creator<ZenModeConfig>() { // from class: android.service.notification.ZenModeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenModeConfig createFromParcel(Parcel parcel) {
            return new ZenModeConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenModeConfig[] newArray(int i) {
            return new ZenModeConfig[i];
        }
    };
    public static final String CUSTOM_MANUAL_PATH = "custom_manual";
    private static final Uri CUSTOM_MANUAL_CONDITION_ID = new Uri.Builder().scheme("condition").authority("android").appendPath(CUSTOM_MANUAL_PATH).build();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigOrigin {
    }

    public static boolean isValidHour(int i) {
        return i >= 0 && i < 24;
    }

    public static boolean isValidMinute(int i) {
        return i >= 0 && i < 60;
    }

    private static boolean isValidSource(int i) {
        return i >= 0 && i <= 2;
    }

    private boolean isVisualEffectAllowed(int i, int i2) {
        return (i & i2) == 0;
    }

    private static int normalizeConversationSenders(boolean z, int i, int i2) {
        if (z) {
            return (i == 1 || i == 2 || i == 3) ? i : i2;
        }
        return 3;
    }

    private static int normalizePrioritySenders(int i, int i2) {
        return (i == 1 || i == 2 || i == 0) ? i : i2;
    }

    private static int sourceToPrioritySenders(int i, int i2) {
        if (i == 0) {
            return 0;
        }
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                return i2;
            }
        }
        return i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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
        this.hasPriorityChannels = false;
        this.allowPriorityChannels = true;
        this.automaticRules = new ArrayMap<>();
        this.deletedRules = new ArrayMap<>();
        if (Flags.modesUi()) {
            ensureManualZenRule();
        }
    }

    public ZenModeConfig(Parcel parcel) {
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
        this.hasPriorityChannels = false;
        this.allowPriorityChannels = true;
        this.automaticRules = new ArrayMap<>();
        ArrayMap<String, ZenRule> arrayMap = new ArrayMap<>();
        this.deletedRules = arrayMap;
        if (!Flags.modesUi()) {
            this.allowCalls = parcel.readInt() == 1;
            this.allowRepeatCallers = parcel.readInt() == 1;
            this.allowMessages = parcel.readInt() == 1;
            this.allowReminders = parcel.readInt() == 1;
            this.allowEvents = parcel.readInt() == 1;
            this.allowCallsFrom = parcel.readInt();
            this.allowMessagesFrom = parcel.readInt();
        }
        this.user = parcel.readInt();
        this.manualRule = (ZenRule) parcel.readParcelable(null, ZenRule.class);
        readRulesFromParcel(this.automaticRules, parcel);
        readRulesFromParcel(arrayMap, parcel);
        if (!Flags.modesUi()) {
            this.allowAlarms = parcel.readInt() == 1;
            this.allowMedia = parcel.readInt() == 1;
            this.allowSystem = parcel.readInt() == 1;
            this.suppressedVisualEffects = parcel.readInt();
        }
        this.hasPriorityChannels = parcel.readInt() == 1;
        if (!Flags.modesUi()) {
            this.allowConversations = parcel.readBoolean();
            this.allowConversationsFrom = parcel.readInt();
            this.allowPriorityChannels = parcel.readBoolean();
        }
        this.exceptionContactsFlag = parcel.readInt();
        this.allowExceptionContacts = parcel.createStringArrayList();
        this.appBypassDndFlag = parcel.readInt();
        this.allowAppBypassDndList = parcel.createStringArrayList();
    }

    public static ZenPolicy getDefaultZenPolicy() {
        return new ZenPolicy.Builder().allowAlarms(true).allowMedia(true).allowSystem(false).allowCalls(3).allowMessages(3).allowReminders(false).allowEvents(false).allowRepeatCallers(true).allowConversations(2).showAllVisualEffects().showVisualEffect(0, false).showVisualEffect(1, false).showVisualEffect(2, false).showVisualEffect(5, false).allowPriorityChannels(true).build();
    }

    public static ZenModeConfig getDefaultConfig() {
        ZenModeConfig zenModeConfig = new ZenModeConfig();
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.days = new int[]{1, 2, 3, 4, 5, 6, 7};
        scheduleInfo.startHour = 22;
        scheduleInfo.endHour = 7;
        scheduleInfo.exitAtAlarm = true;
        ZenRule zenRule = new ZenRule();
        zenRule.id = EVERY_NIGHT_DEFAULT_RULE_ID;
        zenRule.conditionId = toScheduleConditionId(scheduleInfo);
        zenRule.component = ComponentName.unflattenFromString("android/com.android.server.notification.ScheduleConditionProvider");
        zenRule.enabled = false;
        zenRule.zenMode = 1;
        zenRule.pkg = "android";
        zenModeConfig.automaticRules.put(EVERY_NIGHT_DEFAULT_RULE_ID, zenRule);
        return zenModeConfig;
    }

    public static List<String> getDefaultRuleIds() {
        if (Flags.modesUi()) {
            return List.of(EVERY_NIGHT_DEFAULT_RULE_ID);
        }
        return List.of(EVERY_NIGHT_DEFAULT_RULE_ID, EVENTS_OBSOLETE_RULE_ID);
    }

    void ensureManualZenRule() {
        if (this.manualRule == null) {
            ZenRule zenRule = new ZenRule();
            zenRule.type = 0;
            zenRule.enabled = true;
            zenRule.conditionId = Uri.EMPTY;
            zenRule.allowManualInvocation = true;
            zenRule.zenPolicy = getDefaultZenPolicy();
            zenRule.pkg = "android";
            this.manualRule = zenRule;
        }
    }

    private static void readRulesFromParcel(ArrayMap<String, ZenRule> arrayMap, Parcel parcel) {
        int i = parcel.readInt();
        if (i > 0) {
            String[] strArr = new String[i];
            parcel.readString8Array(strArr);
            ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readParcelable(ZenRule.class.getClassLoader(), ParceledListSlice.class);
            List list = parceledListSlice != null ? parceledListSlice.getList() : new ArrayList();
            if (list.size() != i) {
                Slog.wtf(TAG, String.format("Unexpected parceled rules count (%s != %s), throwing them out", Integer.valueOf(list.size()), Integer.valueOf(i)));
                i = 0;
            }
            for (int i2 = 0; i2 < i; i2++) {
                arrayMap.put(strArr[i2], (ZenRule) list.get(i2));
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
        writeRulesToParcel(this.automaticRules, parcel, i);
        writeRulesToParcel(this.deletedRules, parcel, i);
        if (!Flags.modesUi()) {
            parcel.writeInt(this.allowAlarms ? 1 : 0);
            parcel.writeInt(this.allowMedia ? 1 : 0);
            parcel.writeInt(this.allowSystem ? 1 : 0);
            parcel.writeInt(this.suppressedVisualEffects);
        }
        parcel.writeInt(this.hasPriorityChannels ? 1 : 0);
        if (!Flags.modesUi()) {
            parcel.writeBoolean(this.allowConversations);
            parcel.writeInt(this.allowConversationsFrom);
            parcel.writeBoolean(this.allowPriorityChannels);
        }
        parcel.writeInt(this.exceptionContactsFlag);
        parcel.writeStringList(this.allowExceptionContacts);
        parcel.writeInt(this.appBypassDndFlag);
        synchronized (ZenConfigLock) {
            parcel.writeStringList(this.allowAppBypassDndList);
        }
    }

    private static void writeRulesToParcel(ArrayMap<String, ZenRule> arrayMap, Parcel parcel, int i) {
        if (!arrayMap.isEmpty()) {
            int size = arrayMap.size();
            String[] strArr = new String[size];
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = arrayMap.keyAt(i2);
                arrayList.add(arrayMap.valueAt(i2));
            }
            parcel.writeInt(size);
            parcel.writeString8Array(strArr);
            parcel.writeParcelable(new ParceledListSlice(arrayList), i);
            return;
        }
        parcel.writeInt(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ZenModeConfig[user=");
        sb.append(this.user);
        if (!Flags.modesUi()) {
            sb.append(",allowAlarms=");
            sb.append(this.allowAlarms);
            sb.append(",allowMedia=");
            sb.append(this.allowMedia);
            sb.append(",allowSystem=");
            sb.append(this.allowSystem);
            sb.append(",allowReminders=");
            sb.append(this.allowReminders);
            sb.append(",allowEvents=");
            sb.append(this.allowEvents);
            sb.append(",allowCalls=");
            sb.append(this.allowCalls);
            sb.append(",allowRepeatCallers=");
            sb.append(this.allowRepeatCallers);
            sb.append(",allowMessages=");
            sb.append(this.allowMessages);
            sb.append(",allowConversations=");
            sb.append(this.allowConversations);
            sb.append(",allowCallsFrom=");
            sb.append(sourceToString(this.allowCallsFrom));
            sb.append(",allowMessagesFrom=");
            sb.append(sourceToString(this.allowMessagesFrom));
            sb.append(",allowConvFrom=");
            sb.append(ZenPolicy.conversationTypeToString(this.allowConversationsFrom));
            sb.append(",exceptionContactsFlag=");
            sb.append(NotificationManager.Policy.exceptionContactsFlagToString(this.exceptionContactsFlag));
            sb.append(",allowExceptionContacts=");
            sb.append(this.allowExceptionContacts);
            sb.append(",appBypassDndFlag=");
            sb.append(NotificationManager.Policy.appBypassDndFlagToString(this.appBypassDndFlag));
            sb.append(",allowAppBypassDndList=");
            sb.append(this.allowAppBypassDndList);
            sb.append("\nsuppressedVisualEffects=");
            sb.append(this.suppressedVisualEffects);
        }
        sb.append("\nhasPriorityChannels=");
        sb.append(this.hasPriorityChannels);
        sb.append(",allowPriorityChannels=");
        sb.append(this.allowPriorityChannels);
        sb.append(",\nautomaticRules=");
        sb.append(rulesToString(this.automaticRules));
        sb.append(",\nmanualRule=");
        sb.append(this.manualRule);
        sb.append(",\ndeletedRules=");
        sb.append(rulesToString(this.deletedRules));
        sb.append(']');
        return sb.toString();
    }

    public boolean isAllowPriorityChannels() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowPriorityChannels;
    }

    public void setAllowPriorityChannels(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowPriorityChannels = z;
    }

    public int getSuppressedVisualEffects() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.suppressedVisualEffects;
    }

    public void setSuppressedVisualEffects(int i) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.suppressedVisualEffects = i;
    }

    public int getAllowConversationsFrom() {
        if (Flags.modesUi()) {
            return this.manualRule.zenPolicy.getPriorityConversationSenders();
        }
        return this.allowConversationsFrom;
    }

    public void setAllowConversationsFrom(int i) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowConversationsFrom = i;
    }

    public void setAllowConversations(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowConversations = z;
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

    public void setAllowMessagesFrom(int i) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowMessagesFrom = i;
    }

    public void setAllowMessages(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowMessages = z;
    }

    public int getAllowCallsFrom() {
        if (Flags.modesUi()) {
            return ZenAdapters.peopleTypeToPrioritySenders(this.manualRule.zenPolicy.getPriorityCallSenders(), 2);
        }
        return this.allowCallsFrom;
    }

    public void setAllowCallsFrom(int i) {
        if (Flags.modesUi()) {
            this.manualRule.zenPolicy = new ZenPolicy.Builder(this.manualRule.zenPolicy).allowCalls(ZenAdapters.prioritySendersToPeopleType(i)).build();
        } else {
            this.allowCallsFrom = i;
        }
    }

    public void setAllowCalls(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowCalls = z;
    }

    public boolean isAllowEvents() {
        if (Flags.modesUi()) {
            return this.manualRule.zenPolicy.isCategoryAllowed(1, false);
        }
        return this.allowEvents;
    }

    public void setAllowEvents(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowEvents = z;
    }

    public boolean isAllowReminders() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowReminders;
    }

    public void setAllowReminders(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowReminders = z;
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

    public void setAllowRepeatCallers(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowRepeatCallers = z;
    }

    public boolean isAllowSystem() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowSystem;
    }

    public void setAllowSystem(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowSystem = z;
    }

    public boolean isAllowMedia() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowMedia;
    }

    public void setAllowMedia(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowMedia = z;
    }

    public boolean isAllowAlarms() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowAlarms;
    }

    public void setAllowAlarms(boolean z) {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        this.allowAlarms = z;
    }

    public boolean isAllowCalls() {
        if (Flags.modesUi()) {
            throw new IllegalStateException("can't be used with modesUI flag");
        }
        return this.allowCalls;
    }

    private static String rulesToString(ArrayMap<String, ZenRule> arrayMap) {
        if (arrayMap.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(arrayMap.size() * 28);
        sb.append("{\n");
        for (int i = 0; i < arrayMap.size(); i++) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(arrayMap.valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean isValid() {
        if (!isValidManualRule(this.manualRule)) {
            return false;
        }
        int size = this.automaticRules.size();
        for (int i = 0; i < size; i++) {
            if (!isValidAutomaticRule(this.automaticRules.valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidManualRule(ZenRule zenRule) {
        if (zenRule != null) {
            return Settings.Global.isValidZenMode(zenRule.zenMode) && sameCondition(zenRule);
        }
        return true;
    }

    private static boolean isValidAutomaticRule(ZenRule zenRule) {
        return (zenRule == null || TextUtils.isEmpty(zenRule.name) || !Settings.Global.isValidZenMode(zenRule.zenMode) || zenRule.conditionId == null || !sameCondition(zenRule)) ? false : true;
    }

    private static boolean sameCondition(ZenRule zenRule) {
        if (zenRule == null) {
            return false;
        }
        return zenRule.conditionId == null ? zenRule.condition == null : zenRule.condition == null || zenRule.conditionId.equals(zenRule.condition.id);
    }

    private static int[] generateMinuteBuckets() {
        int[] iArr = new int[15];
        iArr[0] = 15;
        iArr[1] = 30;
        iArr[2] = 45;
        for (int i = 1; i <= 12; i++) {
            iArr[i + 2] = i * 60;
        }
        return iArr;
    }

    public static String sourceToString(int i) {
        if (i == 0) {
            return "anyone";
        }
        if (i == 1) {
            return Contacts.AUTHORITY;
        }
        if (i == 2) {
            return "stars";
        }
        return "UNKNOWN";
    }

    public boolean equals(Object obj) {
        List<String> list;
        List<String> list2;
        if (!(obj instanceof ZenModeConfig)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ZenModeConfig zenModeConfig = (ZenModeConfig) obj;
        return zenModeConfig.allowAlarms == this.allowAlarms && zenModeConfig.allowMedia == this.allowMedia && zenModeConfig.allowSystem == this.allowSystem && zenModeConfig.allowCalls == this.allowCalls && zenModeConfig.allowRepeatCallers == this.allowRepeatCallers && zenModeConfig.allowMessages == this.allowMessages && zenModeConfig.allowCallsFrom == this.allowCallsFrom && zenModeConfig.allowMessagesFrom == this.allowMessagesFrom && zenModeConfig.allowReminders == this.allowReminders && zenModeConfig.allowEvents == this.allowEvents && zenModeConfig.user == this.user && Objects.equals(zenModeConfig.automaticRules, this.automaticRules) && Objects.equals(zenModeConfig.manualRule, this.manualRule) && zenModeConfig.suppressedVisualEffects == this.suppressedVisualEffects && zenModeConfig.hasPriorityChannels == this.hasPriorityChannels && zenModeConfig.allowConversations == this.allowConversations && zenModeConfig.allowConversationsFrom == this.allowConversationsFrom && Objects.equals(zenModeConfig.deletedRules, this.deletedRules) && zenModeConfig.allowPriorityChannels == this.allowPriorityChannels && zenModeConfig.exceptionContactsFlag == this.exceptionContactsFlag && (list = zenModeConfig.allowExceptionContacts) != null && list.equals(this.allowExceptionContacts) && zenModeConfig.appBypassDndFlag == this.appBypassDndFlag && (list2 = zenModeConfig.allowAppBypassDndList) != null && list2.equals(this.allowAppBypassDndList);
    }

    public int hashCode() {
        List<String> list = this.allowExceptionContacts;
        String str = (list == null || list.isEmpty()) ? new String() : joinStrings(",", this.allowExceptionContacts);
        List<String> list2 = this.allowAppBypassDndList;
        return Objects.hash(Boolean.valueOf(this.allowAlarms), Boolean.valueOf(this.allowMedia), Boolean.valueOf(this.allowSystem), Boolean.valueOf(this.allowCalls), Boolean.valueOf(this.allowRepeatCallers), Boolean.valueOf(this.allowMessages), Integer.valueOf(this.allowCallsFrom), Integer.valueOf(this.allowMessagesFrom), Boolean.valueOf(this.allowReminders), Boolean.valueOf(this.allowEvents), Integer.valueOf(this.user), this.automaticRules, this.manualRule, Integer.valueOf(this.suppressedVisualEffects), Boolean.valueOf(this.hasPriorityChannels), Boolean.valueOf(this.allowConversations), Integer.valueOf(this.allowConversationsFrom), Boolean.valueOf(this.allowPriorityChannels), Integer.valueOf(this.exceptionContactsFlag), str, Integer.valueOf(this.appBypassDndFlag), (list2 == null || list2.isEmpty()) ? new String() : joinStrings(",", this.allowAppBypassDndList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String toDayList(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append('.');
            }
            sb.append(iArr[i]);
        }
        return sb.toString();
    }

    private static int[] tryParseDayList(String str, String str2) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(str2);
        if (strArrSplit.length == 0) {
            return null;
        }
        int[] iArr = new int[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            int iTryParseInt = tryParseInt(strArrSplit[i], -1);
            if (iTryParseInt == -1) {
                return null;
            }
            iArr[i] = iTryParseInt;
        }
        return iArr;
    }

    private static int tryParseInt(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    private static long tryParseLong(String str, long j) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    private static Long tryParseLong(String str, Long l) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.valueOf(Long.parseLong(str));
            } catch (NumberFormatException unused) {
            }
        }
        return l;
    }

    public static int getCurrentXmlVersion() {
        return Flags.modesUi() ? 12 : 11;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x017b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ZenModeConfig readXml(TypedXmlPullParser typedXmlPullParser, BackupRestoreEventLogger backupRestoreEventLogger) throws XmlPullParserException, IOException {
        Boolean boolUnsafeBoolean;
        Boolean boolUnsafeBoolean2;
        int i = 2;
        if (typedXmlPullParser.getEventType() != 2 || !"zen".equals(typedXmlPullParser.getName())) {
            return null;
        }
        ZenModeConfig zenModeConfig = new ZenModeConfig();
        zenModeConfig.version = safeInt(typedXmlPullParser, "version", getCurrentXmlVersion());
        zenModeConfig.user = safeInt(typedXmlPullParser, "user", zenModeConfig.user);
        boolean z = false;
        boolean z2 = false;
        int i2 = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                String name = typedXmlPullParser.getName();
                if (next == i) {
                    if (ALLOW_TAG.equals(name)) {
                        zenModeConfig.allowCalls = safeBoolean(typedXmlPullParser, ALLOW_ATT_CALLS, true);
                        zenModeConfig.allowRepeatCallers = safeBoolean(typedXmlPullParser, ALLOW_ATT_REPEAT_CALLERS, true);
                        zenModeConfig.allowMessages = safeBoolean(typedXmlPullParser, ALLOW_ATT_MESSAGES, true);
                        zenModeConfig.allowReminders = safeBoolean(typedXmlPullParser, ALLOW_ATT_REMINDERS, z);
                        zenModeConfig.allowConversations = safeBoolean(typedXmlPullParser, ALLOW_ATT_CONV, true);
                        zenModeConfig.allowEvents = safeBoolean(typedXmlPullParser, "events", z);
                        int iSafeInt = safeInt(typedXmlPullParser, ALLOW_ATT_FROM, -1);
                        int iSafeInt2 = safeInt(typedXmlPullParser, ALLOW_ATT_CALLS_FROM, -1);
                        int iSafeInt3 = safeInt(typedXmlPullParser, ALLOW_ATT_MESSAGES_FROM, -1);
                        zenModeConfig.allowConversationsFrom = safeInt(typedXmlPullParser, ALLOW_ATT_CONV_FROM, i);
                        zenModeConfig.exceptionContactsFlag = safeInt(typedXmlPullParser, ATT_SELECTED_CONTACTS_ALLOWED, -1);
                        String attributeValue = typedXmlPullParser.getAttributeValue(null, ALLOW_ATT_EXCEPTION_CONTACTS);
                        if (attributeValue != null && !attributeValue.isEmpty()) {
                            zenModeConfig.allowExceptionContacts = Arrays.asList(attributeValue.split(","));
                        }
                        zenModeConfig.appBypassDndFlag = safeInt(typedXmlPullParser, ATT_SELECTED_APPS_ALLOWED, -1);
                        String attributeValue2 = typedXmlPullParser.getAttributeValue(null, ALLOW_ATT_APP_BYPASS_DND_LIST);
                        synchronized (ZenConfigLock) {
                            if (attributeValue2 != null) {
                                if (!attributeValue2.isEmpty()) {
                                    zenModeConfig.allowAppBypassDndList = Arrays.asList(attributeValue2.split(","));
                                }
                            }
                        }
                        if (isValidSource(iSafeInt2) && isValidSource(iSafeInt3)) {
                            zenModeConfig.allowCallsFrom = iSafeInt2;
                            zenModeConfig.allowMessagesFrom = iSafeInt3;
                        } else if (isValidSource(iSafeInt)) {
                            Slog.i(TAG, "Migrating existing shared 'from': " + sourceToString(iSafeInt));
                            zenModeConfig.allowCallsFrom = iSafeInt;
                            zenModeConfig.allowMessagesFrom = iSafeInt;
                        } else {
                            i = 2;
                            zenModeConfig.allowCallsFrom = 2;
                            zenModeConfig.allowMessagesFrom = 2;
                            zenModeConfig.allowAlarms = safeBoolean(typedXmlPullParser, ALLOW_ATT_ALARMS, true);
                            zenModeConfig.allowMedia = safeBoolean(typedXmlPullParser, "media", true);
                            zenModeConfig.allowSystem = safeBoolean(typedXmlPullParser, "system", false);
                            zenModeConfig.allowPriorityChannels = safeBoolean(typedXmlPullParser, ALLOW_ATT_CHANNELS, true);
                            boolUnsafeBoolean = unsafeBoolean(typedXmlPullParser, ALLOW_ATT_SCREEN_OFF);
                            boolUnsafeBoolean2 = unsafeBoolean(typedXmlPullParser, ALLOW_ATT_SCREEN_ON);
                            if (boolUnsafeBoolean == null || boolUnsafeBoolean2 != null) {
                                zenModeConfig.suppressedVisualEffects = 0;
                                z2 = true;
                            }
                            if (boolUnsafeBoolean != null && !boolUnsafeBoolean.booleanValue()) {
                                zenModeConfig.suppressedVisualEffects |= 140;
                            }
                            if (boolUnsafeBoolean2 != null && !boolUnsafeBoolean2.booleanValue()) {
                                zenModeConfig.suppressedVisualEffects |= 16;
                            }
                            if (z2) {
                                Slog.d(TAG, "Migrated visual effects to " + zenModeConfig.suppressedVisualEffects);
                            }
                        }
                        i = 2;
                        zenModeConfig.allowAlarms = safeBoolean(typedXmlPullParser, ALLOW_ATT_ALARMS, true);
                        zenModeConfig.allowMedia = safeBoolean(typedXmlPullParser, "media", true);
                        zenModeConfig.allowSystem = safeBoolean(typedXmlPullParser, "system", false);
                        zenModeConfig.allowPriorityChannels = safeBoolean(typedXmlPullParser, ALLOW_ATT_CHANNELS, true);
                        boolUnsafeBoolean = unsafeBoolean(typedXmlPullParser, ALLOW_ATT_SCREEN_OFF);
                        boolUnsafeBoolean2 = unsafeBoolean(typedXmlPullParser, ALLOW_ATT_SCREEN_ON);
                        if (boolUnsafeBoolean == null) {
                            zenModeConfig.suppressedVisualEffects = 0;
                            z2 = true;
                            if (boolUnsafeBoolean != null) {
                                zenModeConfig.suppressedVisualEffects |= 140;
                            }
                            if (boolUnsafeBoolean2 != null) {
                                zenModeConfig.suppressedVisualEffects |= 16;
                            }
                            if (z2) {
                            }
                        }
                    } else if (DISALLOW_TAG.equals(name) && !z2) {
                        zenModeConfig.suppressedVisualEffects = safeInt(typedXmlPullParser, DISALLOW_ATT_VISUAL_EFFECTS, 157);
                    } else if ("manual".equals(name)) {
                        ZenRule ruleXml = readRuleXml(typedXmlPullParser);
                        zenModeConfig.manualRule = ruleXml;
                        ruleXml.enabled = true;
                        if (zenModeConfig.manualRule.zenPolicy == null) {
                            z3 = true;
                            z4 = true;
                        } else {
                            i2++;
                            z3 = true;
                        }
                    } else if (AUTOMATIC_TAG.equals(name) || "deleted".equals(name)) {
                        String attributeValue3 = typedXmlPullParser.getAttributeValue(null, RULE_ATT_ID);
                        if (attributeValue3 != null) {
                            ZenRule ruleXml2 = readRuleXml(typedXmlPullParser);
                            ruleXml2.id = attributeValue3;
                            if ("deleted".equals(name)) {
                                String strDeletedRuleKey = deletedRuleKey(ruleXml2);
                                if (strDeletedRuleKey != null) {
                                    zenModeConfig.deletedRules.put(strDeletedRuleKey, ruleXml2);
                                }
                            } else if (AUTOMATIC_TAG.equals(name)) {
                                zenModeConfig.automaticRules.put(attributeValue3, ruleXml2);
                                i2++;
                            }
                        }
                    } else if ("state".equals(name)) {
                        zenModeConfig.hasPriorityChannels = safeBoolean(typedXmlPullParser, STATE_HAS_PRIORITY_CHANNELS, false);
                    }
                }
                if (next == 3 && "zen".equals(name)) {
                    if (Flags.modesUi() && (!z3 || z4)) {
                        zenModeConfig.manualRule.zenPolicy = zenModeConfig.toZenPolicy();
                        if (z4) {
                            zenModeConfig.manualRule.pkg = "android";
                            zenModeConfig.manualRule.type = 0;
                            if (zenModeConfig.manualRule.conditionId == null) {
                                zenModeConfig.manualRule.conditionId = Uri.EMPTY;
                            }
                            zenModeConfig.manualRule.condition = new Condition(zenModeConfig.manualRule.conditionId, "", 1);
                            i2++;
                        }
                    }
                    if (!Flags.modesUi()) {
                        i2++;
                    }
                    if (backupRestoreEventLogger != null) {
                        backupRestoreEventLogger.logItemsRestored(NotificationLoggingConstants.DATA_TYPE_ZEN_RULES, i2);
                    }
                    return zenModeConfig;
                }
                z = false;
            } else {
                throw new IllegalStateException("Failed to reach END_DOCUMENT");
            }
        }
    }

    public static String deletedRuleKey(ZenRule zenRule) {
        if (zenRule.pkg == null || zenRule.conditionId == null) {
            return null;
        }
        return zenRule.pkg + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + zenRule.conditionId.toString();
    }

    public void writeXml(TypedXmlSerializer typedXmlSerializer, Integer num, boolean z, BackupRestoreEventLogger backupRestoreEventLogger) throws IOException {
        int currentXmlVersion = getCurrentXmlVersion();
        typedXmlSerializer.startTag(null, "zen");
        typedXmlSerializer.attribute(null, "version", num == null ? Integer.toString(currentXmlVersion) : Integer.toString(num.intValue()));
        typedXmlSerializer.attributeInt(null, "user", this.user);
        typedXmlSerializer.startTag(null, ALLOW_TAG);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_CALLS, this.allowCalls);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_REPEAT_CALLERS, this.allowRepeatCallers);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_MESSAGES, this.allowMessages);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_REMINDERS, this.allowReminders);
        typedXmlSerializer.attributeBoolean(null, "events", this.allowEvents);
        typedXmlSerializer.attributeInt(null, ALLOW_ATT_CALLS_FROM, this.allowCallsFrom);
        typedXmlSerializer.attributeInt(null, ALLOW_ATT_MESSAGES_FROM, this.allowMessagesFrom);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_ALARMS, this.allowAlarms);
        typedXmlSerializer.attributeBoolean(null, "media", this.allowMedia);
        typedXmlSerializer.attributeBoolean(null, "system", this.allowSystem);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_CONV, this.allowConversations);
        typedXmlSerializer.attributeInt(null, ALLOW_ATT_CONV_FROM, this.allowConversationsFrom);
        typedXmlSerializer.attributeBoolean(null, ALLOW_ATT_CHANNELS, this.allowPriorityChannels);
        typedXmlSerializer.attributeInt(null, ATT_SELECTED_CONTACTS_ALLOWED, this.exceptionContactsFlag);
        List<String> list = this.allowExceptionContacts;
        if (list != null && !list.isEmpty()) {
            typedXmlSerializer.attribute(null, ALLOW_ATT_EXCEPTION_CONTACTS, joinStrings(",", this.allowExceptionContacts));
        }
        typedXmlSerializer.attributeInt(null, ATT_SELECTED_APPS_ALLOWED, this.appBypassDndFlag);
        List<String> list2 = this.allowAppBypassDndList;
        if (list2 != null && !list2.isEmpty()) {
            typedXmlSerializer.attribute(null, ALLOW_ATT_APP_BYPASS_DND_LIST, joinStrings(",", this.allowAppBypassDndList));
        }
        typedXmlSerializer.endTag(null, ALLOW_TAG);
        typedXmlSerializer.startTag(null, DISALLOW_TAG);
        typedXmlSerializer.attributeInt(null, DISALLOW_ATT_VISUAL_EFFECTS, this.suppressedVisualEffects);
        typedXmlSerializer.endTag(null, DISALLOW_TAG);
        if (this.manualRule != null) {
            typedXmlSerializer.startTag(null, "manual");
            writeRuleXml(this.manualRule, typedXmlSerializer, z);
            typedXmlSerializer.endTag(null, "manual");
        }
        int size = this.automaticRules.size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            String strKeyAt = this.automaticRules.keyAt(i2);
            ZenRule zenRuleValueAt = this.automaticRules.valueAt(i2);
            typedXmlSerializer.startTag(null, AUTOMATIC_TAG);
            typedXmlSerializer.attribute(null, RULE_ATT_ID, strKeyAt);
            writeRuleXml(zenRuleValueAt, typedXmlSerializer, z);
            typedXmlSerializer.endTag(null, AUTOMATIC_TAG);
            i++;
        }
        if (!z) {
            for (int i3 = 0; i3 < this.deletedRules.size(); i3++) {
                ZenRule zenRuleValueAt2 = this.deletedRules.valueAt(i3);
                typedXmlSerializer.startTag(null, "deleted");
                typedXmlSerializer.attribute(null, RULE_ATT_ID, zenRuleValueAt2.id);
                writeRuleXml(zenRuleValueAt2, typedXmlSerializer, z);
                typedXmlSerializer.endTag(null, "deleted");
            }
        }
        typedXmlSerializer.startTag(null, "state");
        typedXmlSerializer.attributeBoolean(null, STATE_HAS_PRIORITY_CHANNELS, this.hasPriorityChannels);
        typedXmlSerializer.endTag(null, "state");
        typedXmlSerializer.endTag(null, "zen");
        if (backupRestoreEventLogger != null) {
            backupRestoreEventLogger.logItemsBackedUp(NotificationLoggingConstants.DATA_TYPE_ZEN_RULES, i);
        }
    }

    public static ZenRule readRuleXml(TypedXmlPullParser typedXmlPullParser) {
        ZenRule zenRule = new ZenRule();
        zenRule.enabled = safeBoolean(typedXmlPullParser, "enabled", true);
        zenRule.name = typedXmlPullParser.getAttributeValue(null, "name");
        zenRule.zenMode = tryParseZenMode(typedXmlPullParser.getAttributeValue(null, "zen"), 1);
        zenRule.conditionId = safeUri(typedXmlPullParser, "conditionId");
        zenRule.component = safeComponentName(typedXmlPullParser, "component");
        zenRule.configurationActivity = safeComponentName(typedXmlPullParser, RULE_ATT_CONFIG_ACTIVITY);
        zenRule.pkg = XmlUtils.readStringAttribute(typedXmlPullParser, "pkg");
        if (zenRule.pkg == null) {
            zenRule.pkg = zenRule.component != null ? zenRule.component.getPackageName() : null;
        }
        zenRule.creationTime = safeLong(typedXmlPullParser, "creationTime", 0L);
        zenRule.enabler = typedXmlPullParser.getAttributeValue(null, "enabler");
        zenRule.condition = readConditionXml(typedXmlPullParser);
        zenRule.zenPolicy = readZenPolicyXml(typedXmlPullParser);
        zenRule.zenDeviceEffects = readZenDeviceEffectsXml(typedXmlPullParser);
        zenRule.allowManualInvocation = safeBoolean(typedXmlPullParser, RULE_ATT_ALLOW_MANUAL, false);
        zenRule.iconResName = typedXmlPullParser.getAttributeValue(null, RULE_ATT_ICON);
        zenRule.triggerDescription = typedXmlPullParser.getAttributeValue(null, RULE_ATT_TRIGGER_DESC);
        zenRule.type = safeInt(typedXmlPullParser, "type", -1);
        zenRule.userModifiedFields = safeInt(typedXmlPullParser, RULE_ATT_USER_MODIFIED_FIELDS, 0);
        zenRule.zenPolicyUserModifiedFields = safeInt(typedXmlPullParser, POLICY_USER_MODIFIED_FIELDS, 0);
        zenRule.zenDeviceEffectsUserModifiedFields = safeInt(typedXmlPullParser, DEVICE_EFFECT_USER_MODIFIED_FIELDS, 0);
        zenRule.deletionInstant = safeInstant(typedXmlPullParser, RULE_ATT_DELETION_INSTANT, null);
        if (Flags.modesUi()) {
            zenRule.disabledOrigin = safeInt(typedXmlPullParser, RULE_ATT_DISABLED_ORIGIN, 0);
            zenRule.legacySuppressedEffects = safeInt(typedXmlPullParser, "legacySuppressedEffects", 0);
            zenRule.conditionOverride = safeInt(typedXmlPullParser, "conditionOverride", 0);
            if (Flags.modesCleanupImplicit()) {
                zenRule.lastActivation = safeInstant(typedXmlPullParser, RULE_ATT_LAST_ACTIVATION, null);
            }
        }
        return zenRule;
    }

    public static void writeRuleXml(ZenRule zenRule, TypedXmlSerializer typedXmlSerializer, boolean z) throws IOException {
        typedXmlSerializer.attributeBoolean(null, "enabled", zenRule.enabled);
        if (zenRule.name != null) {
            typedXmlSerializer.attribute(null, "name", zenRule.name);
        }
        typedXmlSerializer.attributeInt(null, "zen", zenRule.zenMode);
        if (zenRule.pkg != null) {
            typedXmlSerializer.attribute(null, "pkg", zenRule.pkg);
        }
        if (zenRule.component != null) {
            typedXmlSerializer.attribute(null, "component", zenRule.component.flattenToString());
        }
        if (zenRule.configurationActivity != null) {
            typedXmlSerializer.attribute(null, RULE_ATT_CONFIG_ACTIVITY, zenRule.configurationActivity.flattenToString());
        }
        if (zenRule.conditionId != null) {
            typedXmlSerializer.attribute(null, "conditionId", zenRule.conditionId.toString());
        }
        typedXmlSerializer.attributeLong(null, "creationTime", zenRule.creationTime);
        if (zenRule.enabler != null) {
            typedXmlSerializer.attribute(null, "enabler", zenRule.enabler);
        }
        if (zenRule.condition != null) {
            writeConditionXml(zenRule.condition, typedXmlSerializer);
        }
        if (zenRule.zenPolicy != null) {
            writeZenPolicyXml(zenRule.zenPolicy, typedXmlSerializer);
        }
        if (zenRule.zenDeviceEffects != null) {
            writeZenDeviceEffectsXml(zenRule.zenDeviceEffects, typedXmlSerializer);
        }
        typedXmlSerializer.attributeBoolean(null, RULE_ATT_ALLOW_MANUAL, zenRule.allowManualInvocation);
        if (zenRule.iconResName != null) {
            typedXmlSerializer.attribute(null, RULE_ATT_ICON, zenRule.iconResName);
        }
        if (zenRule.triggerDescription != null) {
            typedXmlSerializer.attribute(null, RULE_ATT_TRIGGER_DESC, zenRule.triggerDescription);
        }
        typedXmlSerializer.attributeInt(null, "type", zenRule.type);
        typedXmlSerializer.attributeInt(null, RULE_ATT_USER_MODIFIED_FIELDS, zenRule.userModifiedFields);
        typedXmlSerializer.attributeInt(null, POLICY_USER_MODIFIED_FIELDS, zenRule.zenPolicyUserModifiedFields);
        typedXmlSerializer.attributeInt(null, DEVICE_EFFECT_USER_MODIFIED_FIELDS, zenRule.zenDeviceEffectsUserModifiedFields);
        writeXmlAttributeInstant(typedXmlSerializer, RULE_ATT_DELETION_INSTANT, zenRule.deletionInstant);
        if (Flags.modesUi()) {
            typedXmlSerializer.attributeInt(null, RULE_ATT_DISABLED_ORIGIN, zenRule.disabledOrigin);
            typedXmlSerializer.attributeInt(null, "legacySuppressedEffects", zenRule.legacySuppressedEffects);
            if (zenRule.conditionOverride == 1 && !z) {
                typedXmlSerializer.attributeInt(null, "conditionOverride", zenRule.conditionOverride);
            }
            if (Flags.modesCleanupImplicit()) {
                writeXmlAttributeInstant(typedXmlSerializer, RULE_ATT_LAST_ACTIVATION, zenRule.lastActivation);
            }
        }
    }

    private static void writeXmlAttributeInstant(TypedXmlSerializer typedXmlSerializer, String str, Instant instant) throws IOException {
        if (instant != null) {
            typedXmlSerializer.attributeLong(null, str, instant.toEpochMilli());
        }
    }

    public static Condition readConditionXml(TypedXmlPullParser typedXmlPullParser) {
        Uri uriSafeUri = safeUri(typedXmlPullParser, "id");
        if (uriSafeUri == null) {
            return null;
        }
        try {
            return new Condition(uriSafeUri, typedXmlPullParser.getAttributeValue(null, "summary"), typedXmlPullParser.getAttributeValue(null, CONDITION_ATT_LINE1), typedXmlPullParser.getAttributeValue(null, CONDITION_ATT_LINE2), safeInt(typedXmlPullParser, "icon", -1), safeInt(typedXmlPullParser, "state", -1), safeInt(typedXmlPullParser, "source", 0), safeInt(typedXmlPullParser, "flags", -1));
        } catch (IllegalArgumentException e) {
            Slog.w(TAG, "Unable to read condition xml", e);
            return null;
        }
    }

    public static void writeConditionXml(Condition condition, TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, "id", condition.id.toString());
        typedXmlSerializer.attribute(null, "summary", condition.summary);
        typedXmlSerializer.attribute(null, CONDITION_ATT_LINE1, condition.line1);
        typedXmlSerializer.attribute(null, CONDITION_ATT_LINE2, condition.line2);
        typedXmlSerializer.attributeInt(null, "icon", condition.icon);
        typedXmlSerializer.attributeInt(null, "state", condition.state);
        typedXmlSerializer.attributeInt(null, "source", condition.source);
        typedXmlSerializer.attributeInt(null, "flags", condition.flags);
    }

    public static ZenPolicy readZenPolicyXml(TypedXmlPullParser typedXmlPullParser) {
        boolean z;
        ZenPolicy.Builder builder = new ZenPolicy.Builder();
        int iSafeInt = safeInt(typedXmlPullParser, ALLOW_ATT_CALLS_FROM, 0);
        int iSafeInt2 = safeInt(typedXmlPullParser, ALLOW_ATT_MESSAGES_FROM, 0);
        int iSafeInt3 = safeInt(typedXmlPullParser, ALLOW_ATT_REPEAT_CALLERS, 0);
        int iSafeInt4 = safeInt(typedXmlPullParser, ALLOW_ATT_CONV_FROM, 0);
        int iSafeInt5 = safeInt(typedXmlPullParser, ALLOW_ATT_ALARMS, 0);
        int iSafeInt6 = safeInt(typedXmlPullParser, "media", 0);
        int iSafeInt7 = safeInt(typedXmlPullParser, "system", 0);
        int iSafeInt8 = safeInt(typedXmlPullParser, "events", 0);
        int iSafeInt9 = safeInt(typedXmlPullParser, ALLOW_ATT_REMINDERS, 0);
        int iSafeInt10 = safeInt(typedXmlPullParser, ALLOW_ATT_CHANNELS, 0);
        boolean z2 = true;
        if (iSafeInt10 != 0) {
            builder.allowPriorityChannels(iSafeInt10 == 1);
            z = true;
        } else {
            z = false;
        }
        if (iSafeInt != 0) {
            builder.allowCalls(iSafeInt);
            z = true;
        }
        if (iSafeInt2 != 0) {
            builder.allowMessages(iSafeInt2);
            z = true;
        }
        if (iSafeInt3 != 0) {
            builder.allowRepeatCallers(iSafeInt3 == 1);
            z = true;
        }
        if (iSafeInt4 != 0) {
            builder.allowConversations(iSafeInt4);
            z = true;
        }
        if (iSafeInt5 != 0) {
            builder.allowAlarms(iSafeInt5 == 1);
            z = true;
        }
        if (iSafeInt6 != 0) {
            builder.allowMedia(iSafeInt6 == 1);
            z = true;
        }
        if (iSafeInt7 != 0) {
            builder.allowSystem(iSafeInt7 == 1);
            z = true;
        }
        if (iSafeInt8 != 0) {
            builder.allowEvents(iSafeInt8 == 1);
            z = true;
        }
        if (iSafeInt9 != 0) {
            builder.allowReminders(iSafeInt9 == 1);
            z = true;
        }
        int iSafeInt11 = safeInt(typedXmlPullParser, SHOW_ATT_FULL_SCREEN_INTENT, 0);
        int iSafeInt12 = safeInt(typedXmlPullParser, SHOW_ATT_LIGHTS, 0);
        int iSafeInt13 = safeInt(typedXmlPullParser, SHOW_ATT_PEEK, 0);
        int iSafeInt14 = safeInt(typedXmlPullParser, SHOW_ATT_STATUS_BAR_ICONS, 0);
        int iSafeInt15 = safeInt(typedXmlPullParser, SHOW_ATT_BADGES, 0);
        int iSafeInt16 = safeInt(typedXmlPullParser, SHOW_ATT_AMBIENT, 0);
        int iSafeInt17 = safeInt(typedXmlPullParser, SHOW_ATT_NOTIFICATION_LIST, 0);
        if (iSafeInt11 != 0) {
            builder.showFullScreenIntent(iSafeInt11 == 1);
            z = true;
        }
        if (iSafeInt12 != 0) {
            builder.showLights(iSafeInt12 == 1);
            z = true;
        }
        if (iSafeInt13 != 0) {
            builder.showPeeking(iSafeInt13 == 1);
            z = true;
        }
        if (iSafeInt14 != 0) {
            builder.showStatusBarIcons(iSafeInt14 == 1);
            z = true;
        }
        if (iSafeInt15 != 0) {
            builder.showBadges(iSafeInt15 == 1);
            z = true;
        }
        if (iSafeInt16 != 0) {
            builder.showInAmbientDisplay(iSafeInt16 == 1);
            z = true;
        }
        if (iSafeInt17 != 0) {
            builder.showInNotificationList(iSafeInt17 == 1);
        } else {
            z2 = z;
        }
        if (z2) {
            return builder.build();
        }
        return null;
    }

    public static void writeZenPolicyXml(ZenPolicy zenPolicy, TypedXmlSerializer typedXmlSerializer) throws IOException {
        writeZenPolicyState(ALLOW_ATT_CALLS_FROM, zenPolicy.getPriorityCallSenders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_MESSAGES_FROM, zenPolicy.getPriorityMessageSenders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_REPEAT_CALLERS, zenPolicy.getPriorityCategoryRepeatCallers(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_CONV_FROM, zenPolicy.getPriorityConversationSenders(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_ALARMS, zenPolicy.getPriorityCategoryAlarms(), typedXmlSerializer);
        writeZenPolicyState("media", zenPolicy.getPriorityCategoryMedia(), typedXmlSerializer);
        writeZenPolicyState("system", zenPolicy.getPriorityCategorySystem(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_REMINDERS, zenPolicy.getPriorityCategoryReminders(), typedXmlSerializer);
        writeZenPolicyState("events", zenPolicy.getPriorityCategoryEvents(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_FULL_SCREEN_INTENT, zenPolicy.getVisualEffectFullScreenIntent(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_LIGHTS, zenPolicy.getVisualEffectLights(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_PEEK, zenPolicy.getVisualEffectPeek(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_STATUS_BAR_ICONS, zenPolicy.getVisualEffectStatusBar(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_BADGES, zenPolicy.getVisualEffectBadge(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_AMBIENT, zenPolicy.getVisualEffectAmbient(), typedXmlSerializer);
        writeZenPolicyState(SHOW_ATT_NOTIFICATION_LIST, zenPolicy.getVisualEffectNotificationList(), typedXmlSerializer);
        writeZenPolicyState(ALLOW_ATT_CHANNELS, zenPolicy.getPriorityChannelsAllowed(), typedXmlSerializer);
    }

    private static void writeZenPolicyState(String str, int i, TypedXmlSerializer typedXmlSerializer) throws IOException {
        if (Objects.equals(str, ALLOW_ATT_CALLS_FROM) || Objects.equals(str, ALLOW_ATT_MESSAGES_FROM)) {
            if (i != 0) {
                typedXmlSerializer.attributeInt(null, str, i);
            }
        } else if (Objects.equals(str, ALLOW_ATT_CONV_FROM)) {
            if (i != 0) {
                typedXmlSerializer.attributeInt(null, str, i);
            }
        } else if (Objects.equals(str, ALLOW_ATT_CHANNELS)) {
            if (i != 0) {
                typedXmlSerializer.attributeInt(null, str, i);
            }
        } else if (i != 0) {
            typedXmlSerializer.attributeInt(null, str, i);
        }
    }

    private static ZenDeviceEffects readZenDeviceEffectsXml(TypedXmlPullParser typedXmlPullParser) {
        ZenDeviceEffects zenDeviceEffectsBuild = new ZenDeviceEffects.Builder().setShouldDisplayGrayscale(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISPLAY_GRAYSCALE, false)).setShouldSuppressAmbientDisplay(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY, false)).setShouldDimWallpaper(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DIM_WALLPAPER, false)).setShouldUseNightMode(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_USE_NIGHT_MODE, false)).setShouldDisableAutoBrightness(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS, false)).setShouldDisableTapToWake(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_TAP_TO_WAKE, false)).setShouldDisableTiltToWake(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_TILT_TO_WAKE, false)).setShouldDisableTouch(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_DISABLE_TOUCH, false)).setShouldMinimizeRadioUsage(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_MINIMIZE_RADIO_USAGE, false)).setShouldMaximizeDoze(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_MAXIMIZE_DOZE, false)).setShouldUseNightLight(safeBoolean(typedXmlPullParser, DEVICE_EFFECT_USE_NIGHT_LIGHT, false)).setExtraEffects(safeStringSet(typedXmlPullParser, DEVICE_EFFECT_EXTRAS)).build();
        if (zenDeviceEffectsBuild.hasEffects()) {
            return zenDeviceEffectsBuild;
        }
        return null;
    }

    private static void writeZenDeviceEffectsXml(ZenDeviceEffects zenDeviceEffects, TypedXmlSerializer typedXmlSerializer) throws IOException {
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISPLAY_GRAYSCALE, zenDeviceEffects.shouldDisplayGrayscale());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_SUPPRESS_AMBIENT_DISPLAY, zenDeviceEffects.shouldSuppressAmbientDisplay());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DIM_WALLPAPER, zenDeviceEffects.shouldDimWallpaper());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_USE_NIGHT_MODE, zenDeviceEffects.shouldUseNightMode());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_AUTO_BRIGHTNESS, zenDeviceEffects.shouldDisableAutoBrightness());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_TAP_TO_WAKE, zenDeviceEffects.shouldDisableTapToWake());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_TILT_TO_WAKE, zenDeviceEffects.shouldDisableTiltToWake());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_DISABLE_TOUCH, zenDeviceEffects.shouldDisableTouch());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_MINIMIZE_RADIO_USAGE, zenDeviceEffects.shouldMinimizeRadioUsage());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_MAXIMIZE_DOZE, zenDeviceEffects.shouldMaximizeDoze());
        writeBooleanIfTrue(typedXmlSerializer, DEVICE_EFFECT_USE_NIGHT_LIGHT, zenDeviceEffects.shouldUseNightLight());
        writeStringSet(typedXmlSerializer, DEVICE_EFFECT_EXTRAS, zenDeviceEffects.getExtraEffects());
    }

    private static void writeBooleanIfTrue(TypedXmlSerializer typedXmlSerializer, String str, boolean z) throws IOException {
        if (z) {
            typedXmlSerializer.attributeBoolean(null, str, true);
        }
    }

    private static void writeStringSet(TypedXmlSerializer typedXmlSerializer, String str, Set<String> set) throws IOException {
        if (set.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().replace(ITEM_SEPARATOR_ESCAPE, "\\\\").replace(",", "\\,"));
        }
        typedXmlSerializer.attribute(null, str, String.join(",", arrayList));
    }

    private static Boolean unsafeBoolean(TypedXmlPullParser typedXmlPullParser, String str) {
        try {
            return Boolean.valueOf(typedXmlPullParser.getAttributeBoolean(null, str));
        } catch (Exception unused) {
            return null;
        }
    }

    private static boolean safeBoolean(TypedXmlPullParser typedXmlPullParser, String str, boolean z) {
        return typedXmlPullParser.getAttributeBoolean(null, str, z);
    }

    private static boolean safeBoolean(String str, boolean z) {
        return TextUtils.isEmpty(str) ? z : Boolean.parseBoolean(str);
    }

    private static int safeInt(TypedXmlPullParser typedXmlPullParser, String str, int i) {
        return typedXmlPullParser.getAttributeInt(null, str, i);
    }

    private static ComponentName safeComponentName(TypedXmlPullParser typedXmlPullParser, String str) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return null;
        }
        return ComponentName.unflattenFromString(attributeValue);
    }

    private static Uri safeUri(TypedXmlPullParser typedXmlPullParser, String str) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return null;
        }
        return Uri.parse(attributeValue);
    }

    private static long safeLong(TypedXmlPullParser typedXmlPullParser, String str, long j) {
        return tryParseLong(typedXmlPullParser.getAttributeValue(null, str), j);
    }

    private static Set<String> safeStringSet(TypedXmlPullParser typedXmlPullParser, String str) {
        HashSet hashSet = new HashSet();
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (!TextUtils.isEmpty(attributeValue)) {
            for (String str2 : ITEM_SPLITTER_REGEX.split(attributeValue)) {
                hashSet.add(str2.replace("\\\\", ITEM_SEPARATOR_ESCAPE).replace("\\,", ","));
            }
        }
        return hashSet;
    }

    private static Instant safeInstant(TypedXmlPullParser typedXmlPullParser, String str, Instant instant) {
        Long lTryParseLong;
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        return (TextUtils.isEmpty(attributeValue) || (lTryParseLong = tryParseLong(attributeValue, (Long) null)) == null) ? instant : Instant.ofEpochMilli(lTryParseLong.longValue());
    }

    public ZenModeConfig copy() {
        Parcel parcelObtain = Parcel.obtain();
        try {
            writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            return new ZenModeConfig(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }

    public ZenPolicy getZenPolicy() {
        return Flags.modesUi() ? this.manualRule.zenPolicy : toZenPolicy();
    }

    ZenPolicy toZenPolicy() {
        ZenPolicy.Builder builderAllowConversations = new ZenPolicy.Builder().allowCalls(this.allowCalls ? ZenAdapters.prioritySendersToPeopleType(this.allowCallsFrom) : 4).allowRepeatCallers(this.allowRepeatCallers).allowMessages(this.allowMessages ? ZenAdapters.prioritySendersToPeopleType(this.allowMessagesFrom) : 4).allowReminders(this.allowReminders).allowEvents(this.allowEvents).allowAlarms(this.allowAlarms).allowMedia(this.allowMedia).allowSystem(this.allowSystem).allowConversations(this.allowConversations ? this.allowConversationsFrom : 3);
        int i = this.suppressedVisualEffects;
        if (i == 0) {
            builderAllowConversations.showAllVisualEffects();
        } else {
            builderAllowConversations.showFullScreenIntent((i & 4) == 0);
            builderAllowConversations.showLights((this.suppressedVisualEffects & 8) == 0);
            builderAllowConversations.showPeeking((this.suppressedVisualEffects & 16) == 0);
            builderAllowConversations.showStatusBarIcons((this.suppressedVisualEffects & 32) == 0);
            builderAllowConversations.showBadges((this.suppressedVisualEffects & 64) == 0);
            builderAllowConversations.showInAmbientDisplay((this.suppressedVisualEffects & 128) == 0);
            builderAllowConversations.showInNotificationList((this.suppressedVisualEffects & 256) == 0);
        }
        builderAllowConversations.allowPriorityChannels(this.allowPriorityChannels);
        builderAllowConversations.setAppBypassDndFlag(this.appBypassDndFlag);
        builderAllowConversations.setExceptionContactsFlag(this.exceptionContactsFlag);
        builderAllowConversations.allowExceptionContacts(joinStrings(",", this.allowExceptionContacts));
        builderAllowConversations.allowAppsToBypassDnd(joinStrings(NavigationBarInflaterView.GRAVITY_SEPARATOR, this.allowAppBypassDndList));
        return builderAllowConversations.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v3, types: [int] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    public NotificationManager.Policy toNotificationPolicy(ZenPolicy zenPolicy) {
        int iZenPolicyConversationSendersToNotificationPolicy;
        boolean z;
        NotificationManager.Policy notificationPolicy = toNotificationPolicy();
        int iPeopleTypeToPrioritySenders = notificationPolicy.priorityCallSenders;
        int iPeopleTypeToPrioritySenders2 = notificationPolicy.priorityMessageSenders;
        int i = notificationPolicy.priorityConversationSenders;
        int i2 = 0;
        boolean zIsCategoryAllowed = zenPolicy.isCategoryAllowed(0, isPriorityCategoryEnabled(1, notificationPolicy));
        boolean z2 = zIsCategoryAllowed;
        if (zenPolicy.isCategoryAllowed(1, isPriorityCategoryEnabled(2, notificationPolicy))) {
            z2 = (zIsCategoryAllowed ? 1 : 0) | 2;
        }
        boolean z3 = z2;
        if (zenPolicy.isCategoryAllowed(2, isPriorityCategoryEnabled(4, notificationPolicy))) {
            ?? r7 = (z2 ? 1 : 0) | 4;
            iPeopleTypeToPrioritySenders2 = ZenAdapters.peopleTypeToPrioritySenders(zenPolicy.getPriorityMessageSenders(), iPeopleTypeToPrioritySenders2);
            z3 = r7;
        }
        int i3 = iPeopleTypeToPrioritySenders2;
        if (zenPolicy.isCategoryAllowed(8, isPriorityCategoryEnabled(256, notificationPolicy))) {
            ?? r72 = (z3 ? 1 : 0) | 256;
            iZenPolicyConversationSendersToNotificationPolicy = ZenAdapters.zenPolicyConversationSendersToNotificationPolicy(zenPolicy.getPriorityConversationSenders(), i);
            z = r72;
        } else {
            iZenPolicyConversationSendersToNotificationPolicy = 3;
            z = z3;
        }
        boolean z4 = z;
        if (zenPolicy.isCategoryAllowed(3, isPriorityCategoryEnabled(8, notificationPolicy))) {
            ?? r73 = (z ? 1 : 0) | '\b';
            iPeopleTypeToPrioritySenders = ZenAdapters.peopleTypeToPrioritySenders(zenPolicy.getPriorityCallSenders(), iPeopleTypeToPrioritySenders);
            z4 = r73;
        }
        boolean z5 = z4;
        if (zenPolicy.isCategoryAllowed(4, isPriorityCategoryEnabled(16, notificationPolicy))) {
            z5 = (z4 ? 1 : 0) | 16;
        }
        boolean z6 = z5;
        if (zenPolicy.isCategoryAllowed(5, isPriorityCategoryEnabled(32, notificationPolicy))) {
            z6 = (z5 ? 1 : 0) | ' ';
        }
        boolean z7 = z6;
        if (zenPolicy.isCategoryAllowed(6, isPriorityCategoryEnabled(64, notificationPolicy))) {
            z7 = (z6 ? 1 : 0) | '@';
        }
        boolean z8 = z7;
        if (zenPolicy.isCategoryAllowed(7, isPriorityCategoryEnabled(128, notificationPolicy))) {
            z8 = (z7 ? 1 : 0) | 128;
        }
        boolean zIsVisualEffectAllowed = zenPolicy.isVisualEffectAllowed(0, isVisualEffectAllowed(4, notificationPolicy));
        boolean zIsVisualEffectAllowed2 = zenPolicy.isVisualEffectAllowed(1, isVisualEffectAllowed(8, notificationPolicy));
        boolean zIsVisualEffectAllowed3 = zenPolicy.isVisualEffectAllowed(5, isVisualEffectAllowed(128, notificationPolicy));
        if (!zIsVisualEffectAllowed && !zIsVisualEffectAllowed2 && !zIsVisualEffectAllowed3) {
            i2 = 1;
        }
        if (!zIsVisualEffectAllowed) {
            i2 |= 4;
        }
        if (!zIsVisualEffectAllowed2) {
            i2 |= 8;
        }
        if (!zenPolicy.isVisualEffectAllowed(2, isVisualEffectAllowed(16, notificationPolicy))) {
            i2 |= 18;
        }
        if (!zenPolicy.isVisualEffectAllowed(3, isVisualEffectAllowed(32, notificationPolicy))) {
            i2 |= 32;
        }
        if (!zenPolicy.isVisualEffectAllowed(4, isVisualEffectAllowed(64, notificationPolicy))) {
            i2 |= 64;
        }
        if (!zIsVisualEffectAllowed3) {
            i2 |= 128;
        }
        if (!zenPolicy.isVisualEffectAllowed(6, isVisualEffectAllowed(256, notificationPolicy))) {
            i2 |= 256;
        }
        int i4 = i2;
        int iPolicyState = NotificationManager.Policy.policyState(notificationPolicy.hasPriorityChannels(), ZenPolicy.stateToBoolean(zenPolicy.getPriorityChannelsAllowed(), true));
        ArrayList arrayList = new ArrayList();
        if (zenPolicy.isContactsOverridden()) {
            Iterator<String> it = zenPolicy.getExceptionContacts().iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!arrayList.contains(next)) {
                    arrayList.add(next);
                }
            }
        } else {
            arrayList.addAll(notificationPolicy.getExceptionContacts());
        }
        ArrayList arrayList2 = new ArrayList();
        if (zenPolicy.isAppBypassDndOverridden()) {
            Iterator<String> it2 = zenPolicy.getAppsToBypassDnd().iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (!arrayList2.contains(next2)) {
                    arrayList2.add(next2);
                }
            }
        } else {
            arrayList2.addAll(notificationPolicy.getAppBypassDndList());
        }
        int exceptionContactsFlag = zenPolicy.getExceptionContactsFlag();
        if (exceptionContactsFlag == -1) {
            exceptionContactsFlag = notificationPolicy.exceptionContactsFlag;
        }
        int i5 = exceptionContactsFlag;
        int appBypassDndFlag = zenPolicy.getAppBypassDndFlag();
        if (appBypassDndFlag == -1) {
            appBypassDndFlag = notificationPolicy.appBypassDndFlag;
        }
        return new NotificationManager.Policy(z8, iPeopleTypeToPrioritySenders, i3, i4, iPolicyState, iZenPolicyConversationSendersToNotificationPolicy, i5, arrayList, appBypassDndFlag, arrayList2);
    }

    private boolean isPriorityCategoryEnabled(int i, NotificationManager.Policy policy) {
        return (policy.priorityCategories & i) != 0;
    }

    private boolean isVisualEffectAllowed(int i, NotificationManager.Policy policy) {
        return (policy.suppressedVisualEffects & i) == 0;
    }

    public NotificationManager.Policy toNotificationPolicy() {
        int i;
        int iSourceToPrioritySenders;
        int iSourceToPrioritySenders2;
        int iZenPolicyConversationSendersToNotificationPolicy;
        int iPolicyState;
        int suppressedVisualEffects;
        if (Flags.modesUi()) {
            i = this.manualRule.zenPolicy.isCategoryAllowed(1, false) ? 2 : 0;
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
            iZenPolicyConversationSendersToNotificationPolicy = ZenAdapters.zenPolicyConversationSendersToNotificationPolicy(this.manualRule.zenPolicy.getPriorityConversationSenders(), 3);
            if (this.manualRule.zenPolicy.getPriorityCategoryCalls() == 1) {
                i |= 8;
            }
            iSourceToPrioritySenders = ZenAdapters.peopleTypeToPrioritySenders(this.manualRule.zenPolicy.getPriorityCallSenders(), 2);
            if (this.manualRule.zenPolicy.getPriorityCategoryMessages() == 1) {
                i |= 4;
            }
            iSourceToPrioritySenders2 = ZenAdapters.peopleTypeToPrioritySenders(this.manualRule.zenPolicy.getPriorityMessageSenders(), 2);
            iPolicyState = NotificationManager.Policy.policyState(this.hasPriorityChannels, this.manualRule.zenPolicy.getPriorityChannelsAllowed() != 2);
            boolean zIsVisualEffectAllowed = this.manualRule.zenPolicy.isVisualEffectAllowed(0, isVisualEffectAllowed(157, 0));
            boolean zIsVisualEffectAllowed2 = this.manualRule.zenPolicy.isVisualEffectAllowed(1, isVisualEffectAllowed(157, 1));
            boolean zIsVisualEffectAllowed3 = this.manualRule.zenPolicy.isVisualEffectAllowed(5, isVisualEffectAllowed(157, 5));
            int i2 = (zIsVisualEffectAllowed || zIsVisualEffectAllowed2 || zIsVisualEffectAllowed3) ? 0 : 1;
            if (!zIsVisualEffectAllowed) {
                i2 |= 4;
            }
            if (!zIsVisualEffectAllowed2) {
                i2 |= 8;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(2, isVisualEffectAllowed(157, 2))) {
                i2 |= 18;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(3, isVisualEffectAllowed(157, 3))) {
                i2 |= 32;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(4, isVisualEffectAllowed(157, 4))) {
                i2 |= 64;
            }
            int i3 = i2;
            if (!zIsVisualEffectAllowed3) {
                i3 |= 128;
            }
            if (!this.manualRule.zenPolicy.isVisualEffectAllowed(6, isVisualEffectAllowed(157, 6))) {
                i3 |= 256;
            }
            suppressedVisualEffects = (i3 & (-4)) | (this.manualRule.legacySuppressedEffects & 3);
        } else {
            int i4 = isAllowConversations() ? 256 : 0;
            if (isAllowCalls()) {
                i4 |= 8;
            }
            if (isAllowMessages()) {
                i4 |= 4;
            }
            if (isAllowEvents()) {
                i4 |= 2;
            }
            if (isAllowReminders()) {
                i4 |= 1;
            }
            if (isAllowRepeatCallers()) {
                i4 |= 16;
            }
            if (isAllowAlarms()) {
                i4 |= 32;
            }
            if (isAllowMedia()) {
                i4 |= 64;
            }
            i = isAllowSystem() ? i4 | 128 : i4;
            iSourceToPrioritySenders = sourceToPrioritySenders(getAllowCallsFrom(), 1);
            iSourceToPrioritySenders2 = sourceToPrioritySenders(getAllowMessagesFrom(), 1);
            iZenPolicyConversationSendersToNotificationPolicy = ZenAdapters.zenPolicyConversationSendersToNotificationPolicy(getAllowConversationsFrom(), 2);
            iPolicyState = NotificationManager.Policy.policyState(this.hasPriorityChannels, this.allowPriorityChannels);
            suppressedVisualEffects = getSuppressedVisualEffects();
        }
        return new NotificationManager.Policy(i, iSourceToPrioritySenders, iSourceToPrioritySenders2, suppressedVisualEffects, iPolicyState, iZenPolicyConversationSendersToNotificationPolicy, this.exceptionContactsFlag, this.allowExceptionContacts, this.appBypassDndFlag, this.allowAppBypassDndList);
    }

    public static ScheduleCalendar toScheduleCalendar(Uri uri) {
        ScheduleInfo scheduleInfoTryParseScheduleConditionId = tryParseScheduleConditionId(uri);
        if (scheduleInfoTryParseScheduleConditionId == null || scheduleInfoTryParseScheduleConditionId.days == null || scheduleInfoTryParseScheduleConditionId.days.length == 0) {
            return null;
        }
        ScheduleCalendar scheduleCalendar = new ScheduleCalendar();
        scheduleCalendar.setSchedule(scheduleInfoTryParseScheduleConditionId);
        scheduleCalendar.setTimeZone(TimeZone.getDefault());
        return scheduleCalendar;
    }

    public void applyNotificationPolicy(NotificationManager.Policy policy) {
        if (policy == null) {
            return;
        }
        if (Flags.modesUi()) {
            this.manualRule.zenPolicy = ZenAdapters.notificationPolicyToZenPolicy(policy);
            this.manualRule.legacySuppressedEffects = policy.suppressedVisualEffects & 3;
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
            boolean z = (policy.priorityCategories & 256) != 0;
            this.allowConversations = z;
            this.allowConversationsFrom = normalizeConversationSenders(z, policy.priorityConversationSenders, this.allowConversationsFrom);
            if (policy.state != -1) {
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
            this.hasPriorityChannels = (policy.state & 1) != 0;
        }
    }

    public static Condition toTimeCondition(Context context, int i, int i2) {
        return toTimeCondition(context, i, i2, false);
    }

    public static Condition toTimeCondition(Context context, int i, int i2, boolean z) {
        return toTimeCondition(context, System.currentTimeMillis() + (i == 0 ? JobInfo.MIN_BACKOFF_MILLIS : 60000 * i), i, i2, z);
    }

    public static Condition toTimeCondition(Context context, long j, int i, int i2, boolean z) throws Resources.NotFoundException {
        String string;
        String str;
        String str2;
        String str3;
        String quantityString;
        String string2;
        CharSequence formattedTime = getFormattedTime(context, j, isToday(j), i2);
        Resources resources = context.getResources();
        HashMap map = new HashMap();
        if (i < 60) {
            int i3 = z ? R.string.zen_mode_duration_minutes_summary_short : R.string.zen_mode_duration_minutes_summary;
            map.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf(i));
            map.put("formattedTime", formattedTime);
            str3 = PluralsMessageFormatter.format(resources, map, i3);
            if (z) {
                quantityString = PluralsMessageFormatter.format(resources, map, R.string.zen_mode_duration_minutes_short);
            } else {
                quantityString = resources.getQuantityString(R.plurals.zen_mode_duration_time_minutes, i, Integer.valueOf(i));
            }
            string2 = resources.getString(R.string.zen_mode_until, formattedTime);
        } else if (i < 1440) {
            int iRound = Math.round(i / 60.0f);
            int i4 = z ? R.string.zen_mode_duration_hours_summary_short : R.string.zen_mode_duration_hours_summary;
            map.put(Contract.Events.Projection.COUNT_ONLY, Integer.valueOf(iRound));
            map.put("formattedTime", formattedTime);
            str3 = PluralsMessageFormatter.format(resources, map, i4);
            if (z) {
                quantityString = PluralsMessageFormatter.format(resources, map, R.string.zen_mode_duration_hours_short);
            } else {
                quantityString = resources.getQuantityString(R.plurals.zen_mode_duration_time_hours, iRound, Integer.valueOf(iRound));
            }
            string2 = resources.getString(R.string.zen_mode_until, formattedTime);
        } else {
            string = resources.getString(R.string.zen_mode_until_next_day, formattedTime);
            str = string;
            str2 = str;
            return new Condition(toCountdownConditionId(j, false), string, str, str2, 0, 1, 1);
        }
        str2 = string2;
        str = quantityString;
        string = str3;
        return new Condition(toCountdownConditionId(j, false), string, str, str2, 0, 1, 1);
    }

    public static Condition toNextAlarmCondition(Context context, long j, int i) throws Resources.NotFoundException {
        return new Condition(toCountdownConditionId(j, true), "", context.getResources().getString(R.string.zen_mode_until, getFormattedTime(context, j, isToday(j), i)), "", 0, 1, 1);
    }

    public static CharSequence getFormattedTime(Context context, long j, boolean z, int i) {
        return DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), (!z ? "EEE " : "").concat(DateFormat.is24HourFormat(context, i) ? "Hm" : "hma")), j);
    }

    public static boolean isToday(long j) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(j);
        return gregorianCalendar.get(1) == gregorianCalendar2.get(1) && gregorianCalendar.get(2) == gregorianCalendar2.get(2) && gregorianCalendar.get(5) == gregorianCalendar2.get(5);
    }

    public static Uri toCountdownConditionId(long j, boolean z) {
        return new Uri.Builder().scheme("condition").authority("android").appendPath(COUNTDOWN_PATH).appendPath(Long.toString(j)).appendPath("alarm").appendPath(Boolean.toString(z)).build();
    }

    public static long tryParseCountdownConditionId(Uri uri) {
        if (Condition.isValidId(uri, "android") && uri.getPathSegments().size() >= 2 && COUNTDOWN_PATH.equals(uri.getPathSegments().get(0))) {
            try {
                return Long.parseLong(uri.getPathSegments().get(1));
            } catch (RuntimeException e) {
                Slog.w(TAG, "Error parsing countdown condition: " + uri, e);
            }
        }
        return 0L;
    }

    public static boolean isValidCountdownConditionId(Uri uri) {
        return tryParseCountdownConditionId(uri) != 0;
    }

    public static boolean isValidCountdownToAlarmConditionId(Uri uri) {
        if (tryParseCountdownConditionId(uri) != 0 && uri.getPathSegments().size() >= 4 && "alarm".equals(uri.getPathSegments().get(2))) {
            try {
                return Boolean.parseBoolean(uri.getPathSegments().get(3));
            } catch (RuntimeException e) {
                Slog.w(TAG, "Error parsing countdown alarm condition: " + uri, e);
            }
        }
        return false;
    }

    public static Uri toScheduleConditionId(ScheduleInfo scheduleInfo) {
        return new Uri.Builder().scheme("condition").authority("android").appendPath(SCHEDULE_PATH).appendQueryParameter("days", toDayList(scheduleInfo.days)).appendQueryParameter("start", scheduleInfo.startHour + MediaMetrics.SEPARATOR + scheduleInfo.startMinute).appendQueryParameter("end", scheduleInfo.endHour + MediaMetrics.SEPARATOR + scheduleInfo.endMinute).appendQueryParameter("exitAtAlarm", String.valueOf(scheduleInfo.exitAtAlarm)).build();
    }

    public static boolean isValidScheduleConditionId(Uri uri) {
        ScheduleInfo scheduleInfoTryParseScheduleConditionId;
        try {
            scheduleInfoTryParseScheduleConditionId = tryParseScheduleConditionId(uri);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException unused) {
        }
        return (scheduleInfoTryParseScheduleConditionId == null || scheduleInfoTryParseScheduleConditionId.days == null || scheduleInfoTryParseScheduleConditionId.days.length == 0) ? false : true;
    }

    public static boolean isValidScheduleConditionId(Uri uri, boolean z) {
        try {
            ScheduleInfo scheduleInfoTryParseScheduleConditionId = tryParseScheduleConditionId(uri);
            if (scheduleInfoTryParseScheduleConditionId != null) {
                if (z) {
                    return true;
                }
                if (scheduleInfoTryParseScheduleConditionId.days != null && scheduleInfoTryParseScheduleConditionId.days.length != 0) {
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException unused) {
        }
        return false;
    }

    public static ScheduleInfo tryParseScheduleConditionId(Uri uri) {
        if (uri == null || !"condition".equals(uri.getScheme()) || !"android".equals(uri.getAuthority()) || uri.getPathSegments().size() != 1 || !SCHEDULE_PATH.equals(uri.getPathSegments().get(0))) {
            return null;
        }
        int[] iArrTryParseHourAndMinute = tryParseHourAndMinute(uri.getQueryParameter("start"));
        int[] iArrTryParseHourAndMinute2 = tryParseHourAndMinute(uri.getQueryParameter("end"));
        if (iArrTryParseHourAndMinute == null || iArrTryParseHourAndMinute2 == null) {
            return null;
        }
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.days = tryParseDayList(uri.getQueryParameter("days"), "\\.");
        scheduleInfo.startHour = iArrTryParseHourAndMinute[0];
        scheduleInfo.startMinute = iArrTryParseHourAndMinute[1];
        scheduleInfo.endHour = iArrTryParseHourAndMinute2[0];
        scheduleInfo.endMinute = iArrTryParseHourAndMinute2[1];
        scheduleInfo.exitAtAlarm = safeBoolean(uri.getQueryParameter("exitAtAlarm"), false);
        return scheduleInfo;
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

        public boolean equals(Object obj) {
            if (!(obj instanceof ScheduleInfo)) {
                return false;
            }
            ScheduleInfo scheduleInfo = (ScheduleInfo) obj;
            return ZenModeConfig.toDayList(this.days).equals(ZenModeConfig.toDayList(scheduleInfo.days)) && this.startHour == scheduleInfo.startHour && this.startMinute == scheduleInfo.startMinute && this.endHour == scheduleInfo.endHour && this.endMinute == scheduleInfo.endMinute && this.exitAtAlarm == scheduleInfo.exitAtAlarm;
        }

        public ScheduleInfo copy() {
            ScheduleInfo scheduleInfo = new ScheduleInfo();
            int[] iArr = this.days;
            if (iArr != null) {
                int[] iArr2 = new int[iArr.length];
                scheduleInfo.days = iArr2;
                int[] iArr3 = this.days;
                System.arraycopy(iArr3, 0, iArr2, 0, iArr3.length);
            }
            scheduleInfo.startHour = this.startHour;
            scheduleInfo.startMinute = this.startMinute;
            scheduleInfo.endHour = this.endHour;
            scheduleInfo.endMinute = this.endMinute;
            scheduleInfo.exitAtAlarm = this.exitAtAlarm;
            scheduleInfo.nextAlarm = this.nextAlarm;
            return scheduleInfo;
        }

        public String toString() {
            return "ScheduleInfo{days=" + Arrays.toString(this.days) + ", startHour=" + this.startHour + ", startMinute=" + this.startMinute + ", endHour=" + this.endHour + ", endMinute=" + this.endMinute + ", exitAtAlarm=" + this.exitAtAlarm + ", nextAlarm=" + ts(this.nextAlarm) + '}';
        }

        protected static String ts(long j) {
            return new Date(j) + " (" + j + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static Uri toEventConditionId(EventInfo eventInfo) {
        return new Uri.Builder().scheme("condition").authority("android").appendPath("event").appendQueryParameter(SmLib_IafdConstant.KEY_USER_ID, Long.toString(eventInfo.userId)).appendQueryParameter("calendar", eventInfo.calName != null ? eventInfo.calName : "").appendQueryParameter("calendarId", eventInfo.calendarId != null ? eventInfo.calendarId.toString() : "").appendQueryParameter("reply", Integer.toString(eventInfo.reply)).build();
    }

    public static boolean isValidEventConditionId(Uri uri) {
        return tryParseEventConditionId(uri) != null;
    }

    public static EventInfo tryParseEventConditionId(Uri uri) {
        if (uri == null || !"condition".equals(uri.getScheme()) || !"android".equals(uri.getAuthority()) || uri.getPathSegments().size() != 1 || !"event".equals(uri.getPathSegments().get(0))) {
            return null;
        }
        EventInfo eventInfo = new EventInfo();
        eventInfo.userId = tryParseInt(uri.getQueryParameter(SmLib_IafdConstant.KEY_USER_ID), -10000);
        eventInfo.calName = uri.getQueryParameter("calendar");
        if (TextUtils.isEmpty(eventInfo.calName)) {
            eventInfo.calName = null;
        }
        eventInfo.calendarId = tryParseLong(uri.getQueryParameter("calendarId"), (Long) null);
        eventInfo.reply = tryParseInt(uri.getQueryParameter("reply"), 0);
        return eventInfo;
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

        public boolean equals(Object obj) {
            if (!(obj instanceof EventInfo)) {
                return false;
            }
            EventInfo eventInfo = (EventInfo) obj;
            return this.userId == eventInfo.userId && Objects.equals(this.calName, eventInfo.calName) && this.reply == eventInfo.reply && Objects.equals(this.calendarId, eventInfo.calendarId);
        }

        public EventInfo copy() {
            EventInfo eventInfo = new EventInfo();
            eventInfo.userId = this.userId;
            eventInfo.calName = this.calName;
            eventInfo.reply = this.reply;
            eventInfo.calendarId = this.calendarId;
            return eventInfo;
        }

        public static int resolveUserId(int i) {
            return i == -10000 ? ActivityManager.getCurrentUser() : i;
        }
    }

    public static Uri toCustomManualConditionId() {
        return CUSTOM_MANUAL_CONDITION_ID;
    }

    public static boolean isValidCustomManualConditionId(Uri uri) {
        return CUSTOM_MANUAL_CONDITION_ID.equals(uri);
    }

    public static ComponentName getCustomManualConditionProvider() {
        return new ComponentName("android", "CustomManualConditionProvider");
    }

    public static String implicitRuleId(String str) {
        return IMPLICIT_RULE_ID_PREFIX + str;
    }

    public static boolean isImplicitRuleId(String str) {
        return str != null && str.startsWith(IMPLICIT_RULE_ID_PREFIX);
    }

    private static int[] tryParseHourAndMinute(String str) {
        int iIndexOf;
        if (!TextUtils.isEmpty(str) && (iIndexOf = str.indexOf(46)) >= 1 && iIndexOf < str.length() - 1) {
            int iTryParseInt = tryParseInt(str.substring(0, iIndexOf), -1);
            int iTryParseInt2 = tryParseInt(str.substring(iIndexOf + 1), -1);
            if (isValidHour(iTryParseInt) && isValidMinute(iTryParseInt2)) {
                return new int[]{iTryParseInt, iTryParseInt2};
            }
        }
        return null;
    }

    private static int tryParseZenMode(String str, int i) {
        int iTryParseInt = tryParseInt(str, i);
        return Settings.Global.isValidZenMode(iTryParseInt) ? iTryParseInt : i;
    }

    public static String newRuleId() {
        return UUID.randomUUID().toString().replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, "");
    }

    public static String getOwnerCaption(Context context, String str) {
        CharSequence charSequenceLoadLabel;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            if (applicationInfo != null && (charSequenceLoadLabel = applicationInfo.loadLabel(packageManager)) != null) {
                String strTrim = charSequenceLoadLabel.toString().trim();
                return strTrim.length() > 0 ? strTrim : "";
            }
            return "";
        } catch (Throwable th) {
            Slog.w(TAG, "Error loading owner caption", th);
            return "";
        }
    }

    public boolean isManualActive() {
        if (!Flags.modesUi()) {
            return this.manualRule != null;
        }
        ZenRule zenRule = this.manualRule;
        return zenRule != null && zenRule.isActive();
    }

    public static class ZenRule implements Parcelable {
        public static final Parcelable.Creator<ZenRule> CREATOR = new Parcelable.Creator<ZenRule>() { // from class: android.service.notification.ZenModeConfig.ZenRule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ZenRule createFromParcel(Parcel parcel) {
                return new ZenRule(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ZenRule[] newArray(int i) {
                return new ZenRule[i];
            }
        };
        public static final int OVERRIDE_ACTIVATE = 1;
        public static final int OVERRIDE_DEACTIVATE = 2;
        public static final int OVERRIDE_NONE = 0;
        public boolean allowManualInvocation;
        public ComponentName component;
        public Condition condition;
        public Uri conditionId;
        int conditionOverride;
        public ComponentName configurationActivity;
        public long creationTime;
        public Instant deletionInstant;
        public int disabledOrigin;
        public boolean enabled;
        public String enabler;
        public String iconResName;
        public String id;
        public Instant lastActivation;
        int legacySuppressedEffects;
        public String name;
        public String pkg;

        @Deprecated
        public boolean snoozing;
        public String triggerDescription;
        public int type;
        public int userModifiedFields;
        public ZenDeviceEffects zenDeviceEffects;
        public int zenDeviceEffectsUserModifiedFields;
        public int zenMode;
        public ZenPolicy zenPolicy;
        public int zenPolicyUserModifiedFields;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ConditionOverride {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ZenRule() {
            this.type = -1;
            this.disabledOrigin = 0;
            this.conditionOverride = 0;
        }

        public ZenRule(Parcel parcel) {
            this.type = -1;
            this.disabledOrigin = 0;
            this.conditionOverride = 0;
            this.enabled = parcel.readInt() == 1;
            this.snoozing = parcel.readInt() == 1;
            if (parcel.readInt() == 1) {
                this.name = parcel.readString8();
            }
            this.zenMode = parcel.readInt();
            this.conditionId = (Uri) parcel.readParcelable(null, Uri.class);
            this.condition = (Condition) parcel.readParcelable(null, Condition.class);
            this.component = (ComponentName) parcel.readParcelable(null, ComponentName.class);
            this.configurationActivity = (ComponentName) parcel.readParcelable(null, ComponentName.class);
            if (parcel.readInt() == 1) {
                this.id = parcel.readString8();
            }
            this.creationTime = parcel.readLong();
            if (parcel.readInt() == 1) {
                this.enabler = parcel.readString8();
            }
            this.zenPolicy = (ZenPolicy) parcel.readParcelable(null, ZenPolicy.class);
            this.zenDeviceEffects = (ZenDeviceEffects) parcel.readParcelable(null, ZenDeviceEffects.class);
            this.pkg = parcel.readString8();
            this.allowManualInvocation = parcel.readBoolean();
            this.iconResName = parcel.readString8();
            this.triggerDescription = parcel.readString8();
            this.type = parcel.readInt();
            this.userModifiedFields = parcel.readInt();
            this.zenPolicyUserModifiedFields = parcel.readInt();
            this.zenDeviceEffectsUserModifiedFields = parcel.readInt();
            if (parcel.readInt() == 1) {
                this.deletionInstant = Instant.ofEpochMilli(parcel.readLong());
            }
            if (Flags.modesUi()) {
                this.disabledOrigin = parcel.readInt();
                this.legacySuppressedEffects = parcel.readInt();
                this.conditionOverride = parcel.readInt();
                if (Flags.modesCleanupImplicit() && parcel.readInt() == 1) {
                    this.lastActivation = Instant.ofEpochMilli(parcel.readLong());
                }
            }
        }

        public boolean isUserModified() {
            return (this.userModifiedFields == 0 && this.zenPolicyUserModifiedFields == 0 && this.zenDeviceEffectsUserModifiedFields == 0) ? false : true;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.enabled ? 1 : 0);
            parcel.writeInt(this.snoozing ? 1 : 0);
            if (this.name != null) {
                parcel.writeInt(1);
                parcel.writeString8(this.name);
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
                parcel.writeString8(this.id);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeLong(this.creationTime);
            if (this.enabler != null) {
                parcel.writeInt(1);
                parcel.writeString8(this.enabler);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeParcelable(this.zenPolicy, 0);
            parcel.writeParcelable(this.zenDeviceEffects, 0);
            parcel.writeString8(this.pkg);
            parcel.writeBoolean(this.allowManualInvocation);
            parcel.writeString8(this.iconResName);
            parcel.writeString8(this.triggerDescription);
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
            if (Flags.modesUi()) {
                parcel.writeInt(this.disabledOrigin);
                parcel.writeInt(this.legacySuppressedEffects);
                parcel.writeInt(this.conditionOverride);
                if (Flags.modesCleanupImplicit()) {
                    if (this.lastActivation != null) {
                        parcel.writeInt(1);
                        parcel.writeLong(this.lastActivation.toEpochMilli());
                    } else {
                        parcel.writeInt(0);
                    }
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(ZenRule.class.getSimpleName());
            sb.append("[id=");
            sb.append(this.id);
            sb.append(",state=");
            Condition condition = this.condition;
            sb.append(condition == null ? "STATE_FALSE" : Condition.stateToString(condition.state));
            sb.append(",enabled=");
            sb.append(String.valueOf(this.enabled).toUpperCase());
            if (Flags.modesUi()) {
                sb.append(",conditionOverride=");
                sb.append(conditionOverrideToString(this.conditionOverride));
            } else {
                sb.append(",snoozing=");
                sb.append(this.snoozing);
            }
            sb.append(",name=");
            sb.append(this.name);
            sb.append(",zenMode=");
            sb.append(Settings.Global.zenModeToString(this.zenMode));
            sb.append(",conditionId=");
            sb.append(this.conditionId);
            sb.append(",pkg=");
            sb.append(this.pkg);
            sb.append(",component=");
            sb.append(this.component);
            sb.append(",configActivity=");
            sb.append(this.configurationActivity);
            sb.append(",creationTime=");
            sb.append(this.creationTime);
            sb.append(",enabler=");
            sb.append(this.enabler);
            sb.append(",zenPolicy=");
            sb.append(this.zenPolicy);
            sb.append(",condition=");
            sb.append(this.condition);
            sb.append(",deviceEffects=");
            sb.append(this.zenDeviceEffects);
            sb.append(",allowManualInvocation=");
            sb.append(this.allowManualInvocation);
            sb.append(",iconResName=");
            sb.append(this.iconResName);
            sb.append(",triggerDescription=");
            sb.append(this.triggerDescription);
            sb.append(",type=");
            sb.append(this.type);
            if (this.userModifiedFields != 0) {
                sb.append(",userModifiedFields=");
                sb.append(AutomaticZenRule.fieldsToString(this.userModifiedFields));
            }
            if (this.zenPolicyUserModifiedFields != 0) {
                sb.append(",zenPolicyUserModifiedFields=");
                sb.append(ZenPolicy.fieldsToString(this.zenPolicyUserModifiedFields));
            }
            if (this.zenDeviceEffectsUserModifiedFields != 0) {
                sb.append(",zenDeviceEffectsUserModifiedFields=");
                sb.append(ZenDeviceEffects.fieldsToString(this.zenDeviceEffectsUserModifiedFields));
            }
            if (this.deletionInstant != null) {
                sb.append(",deletionInstant=");
                sb.append(this.deletionInstant);
            }
            if (Flags.modesUi()) {
                sb.append(",disabledOrigin=");
                sb.append(this.disabledOrigin);
                sb.append(",legacySuppressedEffects=");
                sb.append(this.legacySuppressedEffects);
                if (Flags.modesCleanupImplicit()) {
                    sb.append(",lastActivation=");
                    sb.append(this.lastActivation);
                }
            }
            sb.append(']');
            return sb.toString();
        }

        private static String conditionOverrideToString(int i) {
            if (i == 0) {
                return "OVERRIDE_NONE";
            }
            if (i == 1) {
                return "OVERRIDE_ACTIVATE";
            }
            if (i == 2) {
                return "OVERRIDE_DEACTIVATE";
            }
            return "UNKNOWN";
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long jStart = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.id);
            protoOutputStream.write(1138166333442L, this.name);
            protoOutputStream.write(1112396529667L, this.creationTime);
            protoOutputStream.write(1133871366148L, this.enabled);
            protoOutputStream.write(1138166333445L, this.enabler);
            if (Flags.modesUi()) {
                protoOutputStream.write(1133871366150L, this.conditionOverride == 2);
            } else {
                protoOutputStream.write(1133871366150L, this.snoozing);
            }
            protoOutputStream.write(1159641169927L, this.zenMode);
            Uri uri = this.conditionId;
            if (uri != null) {
                protoOutputStream.write(1138166333448L, uri.toString());
            }
            Condition condition = this.condition;
            if (condition != null) {
                condition.dumpDebug(protoOutputStream, 1146756268041L);
            }
            ComponentName componentName = this.component;
            if (componentName != null) {
                componentName.dumpDebug(protoOutputStream, 1146756268042L);
            }
            ZenPolicy zenPolicy = this.zenPolicy;
            if (zenPolicy != null) {
                zenPolicy.dumpDebug(protoOutputStream, 1146756268043L);
            }
            protoOutputStream.end(jStart);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ZenRule)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            ZenRule zenRule = (ZenRule) obj;
            boolean z = zenRule.enabled == this.enabled && zenRule.snoozing == this.snoozing && Objects.equals(zenRule.name, this.name) && zenRule.zenMode == this.zenMode && Objects.equals(zenRule.conditionId, this.conditionId) && Objects.equals(zenRule.condition, this.condition) && Objects.equals(zenRule.component, this.component) && Objects.equals(zenRule.configurationActivity, this.configurationActivity) && Objects.equals(zenRule.id, this.id) && Objects.equals(zenRule.enabler, this.enabler) && Objects.equals(zenRule.zenPolicy, this.zenPolicy) && Objects.equals(zenRule.pkg, this.pkg) && Objects.equals(zenRule.zenDeviceEffects, this.zenDeviceEffects) && zenRule.allowManualInvocation == this.allowManualInvocation && Objects.equals(zenRule.iconResName, this.iconResName) && Objects.equals(zenRule.triggerDescription, this.triggerDescription) && zenRule.type == this.type && zenRule.userModifiedFields == this.userModifiedFields && zenRule.zenPolicyUserModifiedFields == this.zenPolicyUserModifiedFields && zenRule.zenDeviceEffectsUserModifiedFields == this.zenDeviceEffectsUserModifiedFields && Objects.equals(zenRule.deletionInstant, this.deletionInstant);
            if (Flags.modesUi()) {
                z = z && zenRule.disabledOrigin == this.disabledOrigin && zenRule.legacySuppressedEffects == this.legacySuppressedEffects && zenRule.conditionOverride == this.conditionOverride;
                if (Flags.modesCleanupImplicit()) {
                    return z && Objects.equals(zenRule.lastActivation, this.lastActivation);
                }
            }
            return z;
        }

        public int hashCode() {
            if (Flags.modesUi()) {
                if (Flags.modesCleanupImplicit()) {
                    return Objects.hash(Boolean.valueOf(this.enabled), Boolean.valueOf(this.snoozing), this.name, Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, this.zenDeviceEffects, Boolean.valueOf(this.allowManualInvocation), this.iconResName, this.triggerDescription, Integer.valueOf(this.type), Integer.valueOf(this.userModifiedFields), Integer.valueOf(this.zenPolicyUserModifiedFields), Integer.valueOf(this.zenDeviceEffectsUserModifiedFields), this.deletionInstant, Integer.valueOf(this.disabledOrigin), Integer.valueOf(this.legacySuppressedEffects), Integer.valueOf(this.conditionOverride), this.lastActivation);
                }
                return Objects.hash(Boolean.valueOf(this.enabled), Boolean.valueOf(this.snoozing), this.name, Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, this.zenDeviceEffects, Boolean.valueOf(this.allowManualInvocation), this.iconResName, this.triggerDescription, Integer.valueOf(this.type), Integer.valueOf(this.userModifiedFields), Integer.valueOf(this.zenPolicyUserModifiedFields), Integer.valueOf(this.zenDeviceEffectsUserModifiedFields), this.deletionInstant, Integer.valueOf(this.disabledOrigin), Integer.valueOf(this.legacySuppressedEffects), Integer.valueOf(this.conditionOverride));
            }
            return Objects.hash(Boolean.valueOf(this.enabled), Boolean.valueOf(this.snoozing), this.name, Integer.valueOf(this.zenMode), this.conditionId, this.condition, this.component, this.configurationActivity, this.pkg, this.id, this.enabler, this.zenPolicy, this.zenDeviceEffects, Boolean.valueOf(this.allowManualInvocation), this.iconResName, this.triggerDescription, Integer.valueOf(this.type), Integer.valueOf(this.userModifiedFields), Integer.valueOf(this.zenPolicyUserModifiedFields), Integer.valueOf(this.zenDeviceEffectsUserModifiedFields), this.deletionInstant);
        }

        public ZenRule copy() {
            Parcel parcelObtain = Parcel.obtain();
            try {
                writeToParcel(parcelObtain, 0);
                parcelObtain.setDataPosition(0);
                return new ZenRule(parcelObtain);
            } finally {
                parcelObtain.recycle();
            }
        }

        public boolean isActive() {
            if (!Flags.modesUi()) {
                return this.enabled && !this.snoozing && getPkg() != null && isTrueOrUnknown();
            }
            if (!this.enabled || getPkg() == null) {
                return false;
            }
            int i = this.conditionOverride;
            if (i == 1) {
                return true;
            }
            if (i == 2) {
                return false;
            }
            return isTrueOrUnknown();
        }

        public int getConditionOverride() {
            if (Flags.modesUi()) {
                return this.conditionOverride;
            }
            return this.snoozing ? 2 : 0;
        }

        public void setConditionOverride(int i) {
            if (Flags.modesUi()) {
                this.conditionOverride = i;
                return;
            }
            if (i == 1) {
                Slog.wtf(ZenModeConfig.TAG, "Shouldn't set OVERRIDE_ACTIVATE if MODES_UI is off");
            } else if (i == 2) {
                this.snoozing = true;
            } else if (i == 0) {
                this.snoozing = false;
            }
        }

        public void resetConditionOverride() {
            setConditionOverride(0);
        }

        public void reconsiderConditionOverride() {
            if (Flags.modesUi()) {
                if (this.conditionOverride == 1 && isTrueOrUnknown()) {
                    resetConditionOverride();
                    return;
                } else {
                    if (this.conditionOverride != 2 || isTrueOrUnknown()) {
                        return;
                    }
                    resetConditionOverride();
                    return;
                }
            }
            if (!this.snoozing || isTrueOrUnknown()) {
                return;
            }
            this.snoozing = false;
        }

        public boolean isAutomaticActive() {
            return this.enabled && !this.snoozing && getPkg() != null && isTrueOrUnknown();
        }

        public String getPkg() {
            if (!TextUtils.isEmpty(this.pkg)) {
                return this.pkg;
            }
            ComponentName componentName = this.component;
            if (componentName != null) {
                return componentName.getPackageName();
            }
            ComponentName componentName2 = this.configurationActivity;
            if (componentName2 != null) {
                return componentName2.getPackageName();
            }
            return null;
        }

        public boolean isTrueOrUnknown() {
            Condition condition = this.condition;
            if (condition != null) {
                return condition.state == 1 || this.condition.state == 2;
            }
            return false;
        }
    }

    public static boolean areAllPriorityOnlyRingerSoundsMuted(NotificationManager.Policy policy) {
        return (((policy.priorityCategories & 1) != 0) || ((policy.priorityCategories & 8) != 0) || ((policy.priorityCategories & 4) != 0) || ((policy.priorityCategories & 2) != 0) || ((policy.priorityCategories & 16) != 0) || ((policy.hasPriorityChannels() && policy.allowPriorityChannels()) || policy.appBypassDndFlag == 1) || ((policy.priorityCategories & 128) != 0) || ((policy.priorityCategories & 256) != 0) || (((policy.exceptionContactsFlag == -1 || policy.exceptionContactsFlag == 0) && policy.getExceptionContacts() != null && !policy.getExceptionContacts().isEmpty()) || (policy.exceptionContactsFlag == 1 && policy.getExceptionContacts() != null))) ? false : true;
    }

    public static boolean areAllZenBehaviorSoundsMuted(NotificationManager.Policy policy) {
        return (((policy.priorityCategories & 32) != 0) || ((policy.priorityCategories & 64) != 0) || !areAllPriorityOnlyRingerSoundsMuted(policy)) ? false : true;
    }

    public static boolean isZenOverridingRinger(int i, NotificationManager.Policy policy) {
        return i == 2 || i == 3 || (i == 1 && areAllPriorityOnlyRingerSoundsMuted(policy));
    }

    public static boolean areAllPriorityOnlyRingerSoundsMuted(ZenModeConfig zenModeConfig) {
        List<String> list;
        if (!Flags.modesUi()) {
            return (zenModeConfig.isAllowReminders() || zenModeConfig.isAllowCalls() || zenModeConfig.isAllowMessages() || zenModeConfig.isAllowEvents() || zenModeConfig.isAllowRepeatCallers() || (zenModeConfig.hasPriorityChannels && zenModeConfig.isAllowPriorityChannels()) || zenModeConfig.isAllowSystem() || (list = zenModeConfig.allowExceptionContacts) == null || !list.isEmpty()) ? false : true;
        }
        ZenPolicy zenPolicy = zenModeConfig.manualRule.zenPolicy;
        return (zenPolicy.isCategoryAllowed(0, false) || zenPolicy.isCategoryAllowed(3, false) || zenPolicy.isCategoryAllowed(2, false) || zenPolicy.isCategoryAllowed(1, false) || zenPolicy.isCategoryAllowed(4, false) || zenPolicy.isCategoryAllowed(7, false) || (zenModeConfig.hasPriorityChannels && zenPolicy.getPriorityChannelsAllowed() == 1)) ? false : true;
    }

    public static boolean areAllZenBehaviorSoundsMuted(ZenModeConfig zenModeConfig) {
        if (!Flags.modesUi()) {
            return (zenModeConfig.isAllowAlarms() || zenModeConfig.isAllowMedia() || !areAllPriorityOnlyRingerSoundsMuted(zenModeConfig)) ? false : true;
        }
        ZenPolicy zenPolicy = zenModeConfig.manualRule.zenPolicy;
        return (zenPolicy.isCategoryAllowed(5, false) || zenPolicy.isCategoryAllowed(6, false) || !areAllPriorityOnlyRingerSoundsMuted(zenModeConfig)) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0062 A[PHI: r2
      0x0062: PHI (r2v1 long) = (r2v0 long), (r2v8 long), (r2v0 long) binds: [B:7:0x000f, B:18:0x003d, B:11:0x0027] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a7 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getDescription(Context context, boolean z, ZenModeConfig zenModeConfig, boolean z2) {
        String string;
        if (z && zenModeConfig != null) {
            long jTryParseCountdownConditionId = -1;
            if (zenModeConfig.isManualActive()) {
                Uri uri = zenModeConfig.manualRule.conditionId;
                if (zenModeConfig.manualRule.enabler != null) {
                    string = getOwnerCaption(context, zenModeConfig.manualRule.enabler);
                    if (string.isEmpty()) {
                        string = "";
                    }
                    for (ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                        if (zenRule.isActive()) {
                            if (isValidEventConditionId(zenRule.conditionId) || isValidScheduleConditionId(zenRule.conditionId)) {
                                long automaticRuleEndTime = parseAutomaticRuleEndTime(context, zenRule.conditionId);
                                if (automaticRuleEndTime > jTryParseCountdownConditionId) {
                                    string = zenRule.name;
                                    jTryParseCountdownConditionId = automaticRuleEndTime;
                                }
                            } else {
                                return zenRule.name;
                            }
                        }
                    }
                    if (string.equals("")) {
                        return string;
                    }
                } else {
                    if (uri == null || Uri.EMPTY.equals(uri)) {
                        if (z2) {
                            return context.getString(R.string.zen_mode_forever);
                        }
                        return null;
                    }
                    jTryParseCountdownConditionId = tryParseCountdownConditionId(uri);
                    if (jTryParseCountdownConditionId > 0) {
                        string = context.getString(R.string.zen_mode_until, getFormattedTime(context, jTryParseCountdownConditionId, isToday(jTryParseCountdownConditionId), context.getUserId()));
                    }
                    while (r9.hasNext()) {
                    }
                    if (string.equals("")) {
                    }
                }
            }
        }
        return null;
    }

    private static long parseAutomaticRuleEndTime(Context context, Uri uri) {
        if (isValidEventConditionId(uri)) {
            return Long.MAX_VALUE;
        }
        if (!isValidScheduleConditionId(uri)) {
            return -1L;
        }
        ScheduleCalendar scheduleCalendar = toScheduleCalendar(uri);
        long nextChangeTime = scheduleCalendar.getNextChangeTime(System.currentTimeMillis());
        if (scheduleCalendar.exitAtAlarm()) {
            long nextAlarm = getNextAlarm(context);
            scheduleCalendar.maybeSetNextAlarm(System.currentTimeMillis(), nextAlarm);
            if (scheduleCalendar.shouldExitForAlarm(nextChangeTime)) {
                return nextAlarm;
            }
        }
        return nextChangeTime;
    }

    private static long getNextAlarm(Context context) {
        AlarmManager.AlarmClockInfo nextAlarmClock = ((AlarmManager) context.getSystemService("alarm")).getNextAlarmClock(context.getUserId());
        if (nextAlarmClock != null) {
            return nextAlarmClock.getTriggerTime();
        }
        return 0L;
    }

    public static String joinStrings(String str, List<String> list) {
        String string;
        synchronized (ZenConfigLock) {
            StringBuilder sb = new StringBuilder();
            if (list != null && !list.isEmpty()) {
                for (String str2 : list) {
                    if (list.indexOf(str2) != 0) {
                        sb.append(str);
                    }
                    sb.append(str2);
                }
            }
            string = sb.toString();
        }
        return string;
    }
}
