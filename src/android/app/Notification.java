package android.app;

import android.annotation.SystemApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.PlayerBase;
import android.media.audio.Enums;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.util.ArraySet;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.util.Pair;
import android.util.Patterns;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.ContextThemeWrapper;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.NotificationBigTextNormalizer;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.NotificationProgressModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class Notification implements Parcelable {
    public static final AudioAttributes AUDIO_ATTRIBUTES_DEFAULT;
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";

    @SystemApi
    public static final String CATEGORY_CAR_EMERGENCY = "car_emergency";

    @SystemApi
    public static final String CATEGORY_CAR_INFORMATION = "car_information";

    @SystemApi
    public static final String CATEGORY_CAR_WARNING = "car_warning";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_LOCATION_SHARING = "location_sharing";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_MISSED_CALL = "missed_call";
    public static final String CATEGORY_NAVIGATION = "navigation";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_STOPWATCH = "stopwatch";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final String CATEGORY_VOICEMAIL = "voicemail";
    public static final String CATEGORY_WORKOUT = "workout";
    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_INVALID = 1;
    public static final Parcelable.Creator<Notification> CREATOR;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;

    @SystemApi
    public static final String EXTRA_ALLOW_DURING_SETUP = "android.allowDuringSetup";
    public static final String EXTRA_ANSWER_COLOR = "android.answerColor";
    public static final String EXTRA_ANSWER_INTENT = "android.answerIntent";
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_BUILDER_APPLICATION_INFO = "android.appInfo";
    public static final String EXTRA_CALL_IS_VIDEO = "android.callIsVideo";
    public static final String EXTRA_CALL_PERSON = "android.callPerson";
    public static final String EXTRA_CALL_TYPE = "android.callType";
    public static final String EXTRA_CHANNEL_GROUP_ID = "android.intent.extra.CHANNEL_GROUP_ID";
    public static final String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";
    public static final String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";
    public static final String EXTRA_COLORIZED = "android.colorized";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONTAINS_CUSTOM_VIEW = "android.contains.customView";
    public static final String EXTRA_CONVERSATION_ICON = "android.conversationIcon";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT = "android.conversationUnreadMessageCount";
    public static final String EXTRA_DECLINE_COLOR = "android.declineColor";
    public static final String EXTRA_DECLINE_INTENT = "android.declineIntent";
    public static final String EXTRA_FOREGROUND_APPS = "android.foregroundApps";
    public static final String EXTRA_HANG_UP_INTENT = "android.hangUpIntent";
    public static final String EXTRA_HIDE_SMART_REPLIES = "android.hideSmartReplies";
    public static final String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation";

    @Deprecated
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_REMOTE_DEVICE = "android.mediaRemoteDevice";
    public static final String EXTRA_MEDIA_REMOTE_ICON = "android.mediaRemoteIcon";
    public static final String EXTRA_MEDIA_REMOTE_INTENT = "android.mediaRemoteIntent";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_MESSAGING_PERSON = "android.messagingUser";
    public static final String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";
    public static final String EXTRA_ONGOING_ACTIVITY_ACTION_BG_COLOR = "android.ongoingActivityActionBgColors";
    public static final String EXTRA_ONGOING_ACTIVITY_BADGE = "android.ongoingActivityBadge";
    public static final String EXTRA_ONGOING_ACTIVITY_CARD_BACKGROUND = "android.ongoingActivityCardBackground";
    public static final String EXTRA_ONGOING_ACTIVITY_CARD_ICON = "android.ongoingActivityCardIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_CHIP_BACKGROUND = "android.ongoingActivityChipBackground";
    public static final String EXTRA_ONGOING_ACTIVITY_CHIP_ICON = "android.ongoingActivityChipIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_CHRONOMETER_BASE = "android.ongoingActivityChronometerBase";
    public static final String EXTRA_ONGOING_ACTIVITY_CHRONOMETER_COUNTDOWN = "android.ongoingActivityChronometerCountdown";
    public static final String EXTRA_ONGOING_ACTIVITY_CHRONOMETER_FORMAT = "android.ongoingActivityChronometerFormat";
    public static final String EXTRA_ONGOING_ACTIVITY_CHRONOMETER_SPEED = "android.ongoingActivityChronometerSpeed";
    public static final String EXTRA_ONGOING_ACTIVITY_CHRONOMETER_START = "android.ongoingActivityChronometerStart";
    public static final String EXTRA_ONGOING_ACTIVITY_CUSTOM_CARD_VIEW_CENTER_UI = "android.ongoingActivityCustomCardViewCenterUI";
    public static final String EXTRA_ONGOING_ACTIVITY_CUSTOM_EXPANDED_CARD_VIEW = "android.ongoingActivityCustomExpandedCardView";
    public static final String EXTRA_ONGOING_ACTIVITY_EXPANDED_CHIP_TEXT = "android.ongoingActivityExpandedChipText";
    public static final String EXTRA_ONGOING_ACTIVITY_EXPANDED_CHIP_VIEW = "android.ongoingActivityExpandedChipView";
    public static final String EXTRA_ONGOING_ACTIVITY_EXPANDED_NOW_BAR_VIEW = "android.ongoingActivityExpandedNowBarView";
    public static final String EXTRA_ONGOING_ACTIVITY_MORE_INFO = "android.ongoingActivityMoreInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_ACTION_BG_COLOR = "android.ongoingActivityNoti.actionBgColor";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_ACTION_PRIMARY_SET = "android.ongoingActivityNoti.actionPrimarySet";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_ACTION_TYPE = "android.ongoingActivityNoti.actionType";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_BG_COLOR = "android.ongoingActivityNoti.bgColor";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHIP_BG_COLOR = "android.ongoingActivityNoti.chipBgColor";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHIP_EXPANDED_TEXT = "android.ongoingActivityNoti.chipExpandedText";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHIP_EXPANDED_VIEW = "android.ongoingActivityNoti.chipExpandedView";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHIP_ICON = "android.ongoingActivityNoti.chipIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHRONOMETER_REMOTEVIEW = "android.ongoingActivityNoti.chronometerRemoteView";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHRONOMETER_REMOTEVIEW_POSITION = "android.ongoingActivityNoti.chronometerRemoteViewPosition";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_CHRONOMETER_REMOTEVIEW_TAG = "android.ongoingActivityNoti.chronometerRemoteViewTag";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_EXPANDED_REMOTEVIEW = "android.ongoingActivityNoti.expandedRemoteView";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_FIRST_ICON = "android.ongoingActivityNoti.firstIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_NOWBAR_ICON = "android.ongoingActivityNoti.nowbarIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_NOWBAR_PRIMARY_INFO = "android.ongoingActivityNoti.nowbarPrimaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_NOWBAR_REMOTEVIEW = "android.ongoingActivityNoti.nowbarRemoteView";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_NOWBAR_SECONDARY_INFO = "android.ongoingActivityNoti.nowbarSecondaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PRIMARY_INFO = "android.ongoingActivityNoti.primaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS = "android.ongoingActivityNoti.progress";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS_INDETERMINATE = "android.ongoingActivityNoti.progressIndeterminate";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS_MAX = "android.ongoingActivityNoti.progressMax";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS_SEGMENTS = "android.ongoingActivityNoti.progressSegments";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS_SEGMENT_INNER_COLOR = "android.ongoingActivityNoti.progressSegments.segmentColor";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS_SEGMENT_INNER_ICON = "android.ongoingActivityNoti.progressSegments.icon";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_PROGRESS_SEGMENT_INNER_START = "android.ongoingActivityNoti.progressSegments.segmentStart";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_SECONDARY_INFO = "android.ongoingActivityNoti.secondaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_SECOND_ICON = "android.ongoingActivityNoti.secondIcon";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_STATE = "android.ongoingActivityNoti.state";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_STYLE = "android.ongoingActivityNoti.style";
    public static final String EXTRA_ONGOING_ACTIVITY_NOTI_TERTIARY_INFO = "android.ongoingActivityNoti.tertiaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_NOW_BAR_EXPANDABLE_TYPE = "android.ongoingActivityNowBarExpandableType";
    public static final String EXTRA_ONGOING_ACTIVITY_PRIMARY_ACTION = "android.ongoingActivityPrimaryAction";
    public static final String EXTRA_ONGOING_ACTIVITY_PRIMARY_INFO = "android.ongoingActivityPrimaryInfo";
    public static final String EXTRA_ONGOING_ACTIVITY_SECONDARY_INFO = "android.ongoingActivitySecondaryInfo";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PEOPLE_LIST = "android.people.list";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PICTURE_CONTENT_DESCRIPTION = "android.pictureContentDescription";
    public static final String EXTRA_PICTURE_ICON = "android.pictureIcon";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_END_ICON = "android.progressEndIcon";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_PROGRESS_POINTS = "android.progressPoints";
    public static final String EXTRA_PROGRESS_SEGMENTS = "android.progressSegments";
    public static final String EXTRA_PROGRESS_START_ICON = "android.progressStartIcon";
    public static final String EXTRA_PROGRESS_TRACKER_ICON = "android.progressTrackerIcon";
    public static final String EXTRA_REDUCED_IMAGES = "android.reduced.images";
    public static final String EXTRA_REMOTE_INPUT_DRAFT = "android.remoteInputDraft";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_REMOTE_INPUT_HISTORY_ITEMS = "android.remoteInputHistoryItems";
    public static final String EXTRA_REQUEST_PROMOTED_ONGOING = "android.requestPromotedOngoing";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHORT_CRITICAL_TEXT = "android.shortCriticalText";
    public static final String EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED = "android.showBigPictureWhenCollapsed";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_REMOTE_INPUT_SPINNER = "android.remoteInputSpinner";
    public static final String EXTRA_SHOW_SMALL_ICON = "android.showSmallIcon";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";

    @Deprecated
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_STYLED_BY_PROGRESS = "android.styledByProgress";

    @SystemApi
    public static final String EXTRA_SUBSTITUTE_APP_NAME = "android.substName";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARIZED_CONTENT = "android.summarization";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final String EXTRA_VERIFICATION_ICON = "android.verificationIcon";
    public static final String EXTRA_VERIFICATION_TEXT = "android.verificationText";

    @SystemApi
    public static final int FLAG_AUTOGROUP_SUMMARY = 1024;
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_BUBBLE = 4096;
    public static final int FLAG_CAN_COLORIZE = 2048;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_FSI_REQUESTED_BUT_DENIED = 16384;
    public static final int FLAG_GROUP_SUMMARY = 512;

    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LIFETIME_EXTENDED_BY_DIRECT_REPLY = 65536;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_NO_DISMISS = 8192;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_PROMOTED_ONGOING = 262144;

    @Deprecated
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int FLAG_SILENT = 131072;
    public static final int FLAG_USER_INITIATED_JOB = 32768;
    public static final int FOREGROUND_SERVICE_DEFAULT = 0;
    public static final int FOREGROUND_SERVICE_DEFERRED = 2;
    public static final int FOREGROUND_SERVICE_IMMEDIATE = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;

    @Deprecated
    public static final String GROUP_KEY_SILENT = "silent";
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    public static final int MAX_ACTION_BUTTONS = 3;
    private static final int MAX_CHARSEQUENCE_LENGTH = 1024;
    private static final float MAX_LARGE_ICON_ASPECT_RATIO = 1.7777778f;
    private static final int MAX_REPLY_HISTORY = 5;
    private static final Set<Class<? extends Style>> PLATFORM_STYLE_CLASSES;

    @Deprecated
    public static final int PRIORITY_DEFAULT = 0;

    @Deprecated
    public static final int PRIORITY_HIGH = 1;

    @Deprecated
    public static final int PRIORITY_LOW = -1;

    @Deprecated
    public static final int PRIORITY_MAX = 2;

    @Deprecated
    public static final int PRIORITY_MIN = -2;
    public static final String SEM_EXTRA_CAPSULE = "sem.android.capsule";
    public static final int SEM_FLAG_BRIEF = 8192;
    public static final int SEM_FLAG_DISABLE_CALL_EDGE_LIGHTING = 4096;
    public static final int SEM_FLAG_DISABLE_EDGE_LIGHTING = 32;
    public static final int SEM_FLAG_DISABLE_HEADS_UP = 8;
    public static final int SEM_FLAG_DISABLE_SIMPLE_COVER_SCREEN_NOTIFICATION = 256;
    public static final int SEM_FLAG_HALF_BRIEF = 16384;
    public static final int SEM_FLAG_HIGHLIGHTS = 131072;
    public static final int SEM_FLAG_INSIGNIFICANT = 262144;
    public static final int SEM_FLAG_INSIGNIFICANT_BG_ACTIVITIES = 1048576;
    public static final int SEM_FLAG_INSIGNIFICANT_MINIMIZED = 2097152;
    public static final int SEM_FLAG_INSIGNIFICANT_OLD_NOTI = 4194304;
    public static final int SEM_FLAG_INSIGNIFICANT_PROMOTION = 524288;
    public static final int SEM_FLAG_REPLACED_BY_NOW_BRIEF = 8388608;
    public static final int SEM_FLAG_SKIP_ASSITANT = 32768;
    public static final int SEM_FLAG_SNOOZED = 128;
    public static final int SEM_PRIORITY_DEFAULT = 0;
    public static final int SEM_PRIORITY_HIGH = 10;
    public static final int SEM_PRIORITY_MAX = 20;
    private static final ArraySet<Integer> STANDARD_LAYOUTS;

    @Deprecated
    public static final int STREAM_DEFAULT = -1;
    private static final String TAG = "Notification";
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;
    static final long WEARABLE_EXTENDER_BACKGROUND_BLOCKED = 270551184;
    public static IBinder processAllowlistToken;
    public Action[] actions;
    public ArraySet<PendingIntent> allPendingIntents;

    @Deprecated
    public AudioAttributes audioAttributes;

    @Deprecated
    public int audioStreamType;

    @Deprecated
    public RemoteViews bigContentView;
    public String category;
    public int color;
    public PendingIntent contentIntent;

    @Deprecated
    public RemoteViews contentView;
    public long creationTime;

    @Deprecated
    public int defaults;
    public PendingIntent deleteIntent;
    public Bundle extras;
    public int flags;
    public PendingIntent fullScreenIntent;

    @Deprecated
    public RemoteViews headsUpContentView;

    @Deprecated
    public int icon;
    public int iconLevel;

    @Deprecated
    public Bitmap largeIcon;

    @Deprecated
    public int ledARGB;

    @Deprecated
    public int ledOffMS;

    @Deprecated
    public int ledOnMS;
    private boolean mAllowSystemGeneratedContextualActions;
    private IBinder mAllowlistToken;
    private int mBadgeIcon;
    private BubbleMetadata mBubbleMetadata;
    private String mChannelId;
    private int mFgsDeferBehavior;
    private int mGroupAlertBehavior;
    private String mGroupKey;
    private Icon mLargeIcon;
    private LocusId mLocusId;
    private CharSequence mSettingsText;
    private String mShortcutId;
    private Icon mSmallIcon;
    private String mSortKey;
    private long mTimeout;
    private boolean mUsesStandardHeader;
    public int number;
    public int parcelDataSize;

    @Deprecated
    public int priority;
    public Notification publicVersion;
    public ComponentName semBadgeTarget;
    public int semFlags;
    public int semMissedCount;
    public int semPriority;

    @Deprecated
    public Uri sound;
    public CharSequence tickerText;

    @Deprecated
    public RemoteViews tickerView;

    @Deprecated
    public long[] vibrate;
    public int visibility;
    public long when;

    public interface Extender {
        Builder extend(Builder builder);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupAlertBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotificationVisibilityOverride {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceNotificationPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        ArraySet<Integer> arraySet = new ArraySet<>();
        STANDARD_LAYOUTS = arraySet;
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_base));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_heads_up_base));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_big_base));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_big_picture));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_big_text));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_inbox));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_messaging));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_big_messaging));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_conversation));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_media));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_big_media));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_call));
        arraySet.add(Integer.valueOf(R.layout.notification_template_material_big_call));
        arraySet.add(Integer.valueOf(R.layout.notification_template_header));
        AUDIO_ATTRIBUTES_DEFAULT = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
        PLATFORM_STYLE_CLASSES = Set.of(BigTextStyle.class, BigPictureStyle.class, InboxStyle.class, MediaStyle.class, DecoratedCustomViewStyle.class, DecoratedMediaCustomViewStyle.class, MessagingStyle.class, CallStyle.class, OngoingActivityStyle.class);
        CREATOR = new Parcelable.Creator<Notification>() { // from class: android.app.Notification.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Notification createFromParcel(Parcel parcel) {
                return new Notification(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Notification[] newArray(int i) {
                return new Notification[i];
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlatformStyle(Style style) {
        if (style == null) {
            return false;
        }
        if (PLATFORM_STYLE_CLASSES.contains(style.getClass())) {
            return true;
        }
        return Flags.apiRichOngoing() && style.getClass() == ProgressStyle.class;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isStandardLayout(int i) {
        if (Flags.notificationsRedesignTemplates()) {
            switch (i) {
                case R.layout.notification_2025_template_collapsed_base /* 17367249 */:
                case R.layout.notification_2025_template_collapsed_call /* 17367250 */:
                case R.layout.notification_2025_template_collapsed_conversation /* 17367251 */:
                case R.layout.notification_2025_template_collapsed_media /* 17367252 */:
                case R.layout.notification_2025_template_collapsed_messaging /* 17367253 */:
                case R.layout.notification_2025_template_expanded_base /* 17367256 */:
                case R.layout.notification_2025_template_expanded_big_picture /* 17367257 */:
                case R.layout.notification_2025_template_expanded_big_text /* 17367258 */:
                case R.layout.notification_2025_template_expanded_call /* 17367259 */:
                case R.layout.notification_2025_template_expanded_conversation /* 17367260 */:
                case R.layout.notification_2025_template_expanded_inbox /* 17367261 */:
                case R.layout.notification_2025_template_expanded_media /* 17367262 */:
                case R.layout.notification_2025_template_expanded_messaging /* 17367263 */:
                case R.layout.notification_2025_template_header /* 17367265 */:
                case R.layout.notification_2025_template_heads_up_base /* 17367266 */:
                    return true;
                case R.layout.notification_2025_template_compact_heads_up_base /* 17367254 */:
                case R.layout.notification_2025_template_compact_heads_up_messaging /* 17367255 */:
                default:
                    return false;
                case R.layout.notification_2025_template_expanded_progress /* 17367264 */:
                    return Flags.apiRichOngoing();
            }
        }
        if (Flags.apiRichOngoing() && i == 17367303) {
            return true;
        }
        return STANDARD_LAYOUTS.contains(Integer.valueOf(i));
    }

    public String getGroup() {
        return this.mGroupKey;
    }

    public String getSortKey() {
        return this.mSortKey;
    }

    public static class Action implements Parcelable {
        public static final Parcelable.Creator<Action> CREATOR = new Parcelable.Creator<Action>() { // from class: android.app.Notification.Action.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Action createFromParcel(Parcel parcel) {
                return new Action(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Action[] newArray(int i) {
                return new Action[i];
            }
        };
        private static final String EXTRA_DATA_ONLY_INPUTS = "android.extra.DATA_ONLY_INPUTS";
        public static final String EXTRA_IS_ANIMATED = "android.extra.IS_ANIMATED";
        public static final int SEMANTIC_ACTION_ARCHIVE = 5;
        public static final int SEMANTIC_ACTION_CALL = 10;

        @SystemApi
        public static final int SEMANTIC_ACTION_CONVERSATION_IS_PHISHING = 12;
        public static final int SEMANTIC_ACTION_DELETE = 4;
        public static final int SEMANTIC_ACTION_MARK_AS_READ = 2;
        public static final int SEMANTIC_ACTION_MARK_AS_UNREAD = 3;

        @SystemApi
        public static final int SEMANTIC_ACTION_MARK_CONVERSATION_AS_PRIORITY = 11;
        public static final int SEMANTIC_ACTION_MUTE = 6;
        public static final int SEMANTIC_ACTION_NONE = 0;
        public static final int SEMANTIC_ACTION_REPLY = 1;
        public static final int SEMANTIC_ACTION_THUMBS_DOWN = 9;
        public static final int SEMANTIC_ACTION_THUMBS_UP = 8;
        public static final int SEMANTIC_ACTION_UNMUTE = 7;
        public PendingIntent actionIntent;

        @Deprecated
        public int icon;
        private boolean mAllowGeneratedReplies;
        private boolean mAuthenticationRequired;
        private final Bundle mExtras;
        private Icon mIcon;
        private final boolean mIsContextual;
        private final RemoteInput[] mRemoteInputs;
        private final int mSemanticAction;
        public CharSequence title;

        public interface Extender {
            Builder extend(Builder builder);
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SemanticAction {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Action(Parcel parcel) {
            this.mAllowGeneratedReplies = true;
            if (parcel.readInt() != 0) {
                Icon createFromParcel = Icon.CREATOR.createFromParcel(parcel);
                this.mIcon = createFromParcel;
                if (createFromParcel.getType() == 2) {
                    this.icon = this.mIcon.getResId();
                }
            }
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            if (parcel.readInt() == 1) {
                this.actionIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            }
            this.mExtras = Bundle.setDefusable(parcel.readBundle(), true);
            this.mRemoteInputs = (RemoteInput[]) parcel.createTypedArray(RemoteInput.CREATOR);
            this.mAllowGeneratedReplies = parcel.readInt() == 1;
            this.mSemanticAction = parcel.readInt();
            this.mIsContextual = parcel.readInt() == 1;
            this.mAuthenticationRequired = parcel.readInt() == 1;
        }

        @Deprecated
        public Action(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this(Icon.createWithResource("", i), charSequence, pendingIntent, new Bundle(), null, true, 0, false, false);
        }

        private Action(Icon icon, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z, int i, boolean z2, boolean z3) {
            this.mAllowGeneratedReplies = true;
            this.mIcon = icon;
            if (icon != null && icon.getType() == 2) {
                this.icon = icon.getResId();
            }
            this.title = charSequence;
            this.actionIntent = pendingIntent;
            this.mExtras = bundle == null ? new Bundle() : bundle;
            this.mRemoteInputs = remoteInputArr;
            this.mAllowGeneratedReplies = z;
            this.mSemanticAction = i;
            this.mIsContextual = z2;
            this.mAuthenticationRequired = z3;
        }

        public Icon getIcon() {
            int i;
            if (this.mIcon == null && (i = this.icon) != 0) {
                this.mIcon = Icon.createWithResource("", i);
            }
            return this.mIcon;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public int getSemanticAction() {
            return this.mSemanticAction;
        }

        public boolean isContextual() {
            return this.mIsContextual;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return (RemoteInput[]) Notification.getParcelableArrayFromBundle(this.mExtras, EXTRA_DATA_ONLY_INPUTS, RemoteInput.class);
        }

        public boolean isAuthenticationRequired() {
            return this.mAuthenticationRequired;
        }

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private boolean mAuthenticationRequired;
            private final Bundle mExtras;
            private final Icon mIcon;
            private final PendingIntent mIntent;
            private boolean mIsContextual;
            private ArrayList<RemoteInput> mRemoteInputs;
            private int mSemanticAction;
            private final CharSequence mTitle;

            @Deprecated
            public Builder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                this(Icon.createWithResource("", i), charSequence, pendingIntent);
            }

            public Builder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
                this(icon, charSequence, pendingIntent, new Bundle(), null, true, 0, false);
            }

            public Builder(Action action) {
                this(action.getIcon(), action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.isAuthenticationRequired());
            }

            private Builder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, boolean z, int i, boolean z2) {
                this.mAllowGeneratedReplies = true;
                this.mIcon = icon;
                this.mTitle = charSequence;
                this.mIntent = pendingIntent;
                this.mExtras = bundle;
                if (remoteInputArr != null) {
                    ArrayList<RemoteInput> arrayList = new ArrayList<>(remoteInputArr.length);
                    this.mRemoteInputs = arrayList;
                    Collections.addAll(arrayList, remoteInputArr);
                }
                this.mAllowGeneratedReplies = z;
                this.mSemanticAction = i;
                this.mAuthenticationRequired = z2;
            }

            public Builder addExtras(Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle);
                }
                return this;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList<>();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            public Builder setAllowGeneratedReplies(boolean z) {
                this.mAllowGeneratedReplies = z;
                return this;
            }

            public Builder setSemanticAction(int i) {
                this.mSemanticAction = i;
                return this;
            }

            public Builder setContextual(boolean z) {
                this.mIsContextual = z;
                return this;
            }

            public Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public Builder setAuthenticationRequired(boolean z) {
                this.mAuthenticationRequired = z;
                return this;
            }

            private void checkContextualActionNullFields() {
                if (this.mIsContextual) {
                    if (this.mIcon == null) {
                        throw new NullPointerException("Contextual Actions must contain a valid icon");
                    }
                    if (this.mIntent == null) {
                        throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
                    }
                }
            }

            public Action build() {
                checkContextualActionNullFields();
                ArrayList arrayList = new ArrayList();
                RemoteInput[] remoteInputArr = (RemoteInput[]) Notification.getParcelableArrayFromBundle(this.mExtras, Action.EXTRA_DATA_ONLY_INPUTS, RemoteInput.class);
                if (remoteInputArr != null) {
                    for (RemoteInput remoteInput : remoteInputArr) {
                        arrayList.add(remoteInput);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                ArrayList<RemoteInput> arrayList3 = this.mRemoteInputs;
                if (arrayList3 != null) {
                    Iterator<RemoteInput> it = arrayList3.iterator();
                    while (it.hasNext()) {
                        RemoteInput next = it.next();
                        if (next.isDataOnly()) {
                            arrayList.add(next);
                        } else {
                            arrayList2.add(next);
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.mExtras.putParcelableArray(Action.EXTRA_DATA_ONLY_INPUTS, (RemoteInput[]) arrayList.toArray(new RemoteInput[arrayList.size()]));
                }
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrayList2.isEmpty() ? null : (RemoteInput[]) arrayList2.toArray(new RemoteInput[arrayList2.size()]), this.mAllowGeneratedReplies, this.mSemanticAction, this.mIsContextual, this.mAuthenticationRequired);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(Consumer<Uri> consumer) {
            Notification.visitIconUri(consumer, getIcon());
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Action m425clone() {
            return new Action(getIcon(), this.title, this.actionIntent, this.mExtras == null ? new Bundle() : new Bundle(this.mExtras), getRemoteInputs(), getAllowGeneratedReplies(), getSemanticAction(), isContextual(), isAuthenticationRequired());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            Icon icon = getIcon();
            if (icon != null) {
                parcel.writeInt(1);
                icon.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            TextUtils.writeToParcel(this.title, parcel, i);
            if (this.actionIntent != null) {
                parcel.writeInt(1);
                this.actionIntent.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.mExtras);
            parcel.writeTypedArray(this.mRemoteInputs, i);
            parcel.writeInt(this.mAllowGeneratedReplies ? 1 : 0);
            parcel.writeInt(this.mSemanticAction);
            parcel.writeInt(this.mIsContextual ? 1 : 0);
            parcel.writeInt(this.mAuthenticationRequired ? 1 : 0);
        }

        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
                this.mFlags = 1;
            }

            public WearableExtender(Action action) {
                this.mFlags = 1;
                Bundle bundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (bundle != null) {
                    this.mFlags = bundle.getInt("flags", 1);
                    this.mInProgressLabel = bundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = bundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = bundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            @Override // android.app.Notification.Action.Extender
            public Builder extend(Builder builder) {
                Bundle bundle = new Bundle();
                int i = this.mFlags;
                if (i != 1) {
                    bundle.putInt("flags", i);
                }
                CharSequence charSequence = this.mInProgressLabel;
                if (charSequence != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, charSequence);
                }
                CharSequence charSequence2 = this.mConfirmLabel;
                if (charSequence2 != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, charSequence2);
                }
                CharSequence charSequence3 = this.mCancelLabel;
                if (charSequence3 != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, charSequence3);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
                return builder;
            }

            /* renamed from: clone, reason: merged with bridge method [inline-methods] */
            public WearableExtender m426clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }

            public WearableExtender setAvailableOffline(boolean z) {
                setFlag(1, z);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int i, boolean z) {
                if (z) {
                    this.mFlags = i | this.mFlags;
                } else {
                    this.mFlags = (~i) & this.mFlags;
                }
            }

            @Deprecated
            public WearableExtender setInProgressLabel(CharSequence charSequence) {
                this.mInProgressLabel = charSequence;
                return this;
            }

            @Deprecated
            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            @Deprecated
            public WearableExtender setConfirmLabel(CharSequence charSequence) {
                this.mConfirmLabel = charSequence;
                return this;
            }

            @Deprecated
            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            @Deprecated
            public WearableExtender setCancelLabel(CharSequence charSequence) {
                this.mCancelLabel = charSequence;
                return this;
            }

            @Deprecated
            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public WearableExtender setHintLaunchesActivity(boolean z) {
                setFlag(2, z);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public WearableExtender setHintDisplayActionInline(boolean z) {
                setFlag(4, z);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }
    }

    public Notification() {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        this.when = System.currentTimeMillis();
        if (Flags.sortSectionByTime()) {
            this.creationTime = this.when;
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        } else {
            this.creationTime = System.currentTimeMillis();
        }
        this.priority = 0;
    }

    public Notification(Context context, int i, CharSequence charSequence, long j, CharSequence charSequence2, CharSequence charSequence3, Intent intent) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        if (Flags.sortSectionByTime()) {
            this.creationTime = j;
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        }
        new Builder(context).setWhen(j).setSmallIcon(i).setTicker(charSequence).setContentTitle(charSequence2).setContentText(charSequence3).setContentIntent(PendingIntent.getActivity(context, 0, intent, 33554432)).buildInto(this);
    }

    @Deprecated
    public Notification(int i, CharSequence charSequence, long j) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        this.icon = i;
        this.tickerText = charSequence;
        this.when = j;
        if (Flags.sortSectionByTime()) {
            this.creationTime = j;
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        } else {
            this.creationTime = System.currentTimeMillis();
        }
    }

    public Notification(Parcel parcel) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        readFromParcelImpl(parcel);
        this.allPendingIntents = parcel.readArraySet(null);
    }

    private void readFromParcelImpl(Parcel parcel) {
        parcel.readInt();
        IBinder readStrongBinder = parcel.readStrongBinder();
        this.mAllowlistToken = readStrongBinder;
        if (readStrongBinder == null) {
            this.mAllowlistToken = processAllowlistToken;
        }
        if (!parcel.hasClassCookie(PendingIntent.class)) {
            parcel.setClassCookie(PendingIntent.class, this.mAllowlistToken);
        }
        this.when = parcel.readLong();
        this.creationTime = parcel.readLong();
        if (parcel.readInt() != 0) {
            Icon createFromParcel = Icon.CREATOR.createFromParcel(parcel);
            this.mSmallIcon = createFromParcel;
            if (createFromParcel.getType() == 2) {
                this.icon = this.mSmallIcon.getResId();
            }
        }
        this.number = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.contentIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.deleteIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.tickerText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.tickerView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.contentView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mLargeIcon = Icon.CREATOR.createFromParcel(parcel);
        }
        this.defaults = parcel.readInt();
        this.flags = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.sound = Uri.CREATOR.createFromParcel(parcel);
        }
        this.audioStreamType = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.audioAttributes = AudioAttributes.CREATOR.createFromParcel(parcel);
        }
        this.vibrate = parcel.createLongArray();
        this.ledARGB = parcel.readInt();
        this.ledOnMS = parcel.readInt();
        this.ledOffMS = parcel.readInt();
        this.iconLevel = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.fullScreenIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        this.priority = parcel.readInt();
        this.category = parcel.readString8();
        this.mGroupKey = parcel.readString8();
        this.mSortKey = parcel.readString8();
        this.actions = (Action[]) parcel.createTypedArray(Action.CREATOR);
        this.extras = Bundle.setDefusable(parcel.readBundle(), true);
        fixDuplicateExtras();
        if (parcel.readInt() != 0) {
            this.bigContentView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.headsUpContentView = RemoteViews.CREATOR.createFromParcel(parcel);
        }
        this.visibility = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.publicVersion = CREATOR.createFromParcel(parcel);
        }
        this.color = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mChannelId = parcel.readString8();
        }
        this.mTimeout = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mShortcutId = parcel.readString8();
        }
        if (parcel.readInt() != 0) {
            this.mLocusId = LocusId.CREATOR.createFromParcel(parcel);
        }
        this.mBadgeIcon = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mSettingsText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        this.mGroupAlertBehavior = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mBubbleMetadata = BubbleMetadata.CREATOR.createFromParcel(parcel);
        }
        this.mAllowSystemGeneratedContextualActions = parcel.readBoolean();
        this.mFgsDeferBehavior = parcel.readInt();
        this.semMissedCount = parcel.readInt();
        this.semFlags = parcel.readInt();
        this.semPriority = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.semBadgeTarget = ComponentName.CREATOR.createFromParcel(parcel);
        }
        this.parcelDataSize = parcel.dataSize();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Notification m421clone() {
        Notification notification = new Notification();
        cloneInto(notification, true);
        return notification;
    }

    public void cloneInto(Notification notification, boolean z) {
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        Icon icon;
        RemoteViews remoteViews3;
        RemoteViews remoteViews4;
        notification.mAllowlistToken = this.mAllowlistToken;
        notification.when = this.when;
        notification.creationTime = this.creationTime;
        notification.mSmallIcon = this.mSmallIcon;
        notification.number = this.number;
        notification.contentIntent = this.contentIntent;
        notification.deleteIntent = this.deleteIntent;
        notification.fullScreenIntent = this.fullScreenIntent;
        CharSequence charSequence = this.tickerText;
        if (charSequence != null) {
            notification.tickerText = charSequence.toString();
        }
        if (z && (remoteViews4 = this.tickerView) != null) {
            notification.tickerView = remoteViews4.mo465clone();
        }
        if (z && (remoteViews3 = this.contentView) != null) {
            notification.contentView = remoteViews3.mo465clone();
        }
        if (z && (icon = this.mLargeIcon) != null) {
            notification.mLargeIcon = icon;
        }
        notification.iconLevel = this.iconLevel;
        notification.sound = this.sound;
        notification.audioStreamType = this.audioStreamType;
        if (this.audioAttributes != null) {
            notification.audioAttributes = new AudioAttributes.Builder(this.audioAttributes).build();
        }
        long[] jArr = this.vibrate;
        int i = 0;
        if (jArr != null) {
            int length = jArr.length;
            long[] jArr2 = new long[length];
            notification.vibrate = jArr2;
            System.arraycopy(jArr, 0, jArr2, 0, length);
        }
        notification.ledARGB = this.ledARGB;
        notification.ledOnMS = this.ledOnMS;
        notification.ledOffMS = this.ledOffMS;
        notification.defaults = this.defaults;
        notification.flags = this.flags;
        notification.priority = this.priority;
        notification.category = this.category;
        notification.mGroupKey = this.mGroupKey;
        notification.mSortKey = this.mSortKey;
        if (this.extras != null) {
            try {
                Bundle bundle = new Bundle(this.extras);
                notification.extras = bundle;
                bundle.size();
            } catch (BadParcelableException e) {
                Log.e(TAG, "could not unparcel extras from notification: " + this, e);
                notification.extras = null;
            }
        }
        if (!ArrayUtils.isEmpty(this.allPendingIntents)) {
            notification.allPendingIntents = new ArraySet<>((ArraySet) this.allPendingIntents);
        }
        Action[] actionArr = this.actions;
        if (actionArr != null) {
            notification.actions = new Action[actionArr.length];
            while (true) {
                Action[] actionArr2 = this.actions;
                if (i >= actionArr2.length) {
                    break;
                }
                Action action = actionArr2[i];
                if (action != null) {
                    notification.actions[i] = action.m425clone();
                }
                i++;
            }
        }
        if (z && (remoteViews2 = this.bigContentView) != null) {
            notification.bigContentView = remoteViews2.mo465clone();
        }
        if (z && (remoteViews = this.headsUpContentView) != null) {
            notification.headsUpContentView = remoteViews.mo465clone();
        }
        notification.visibility = this.visibility;
        if (this.publicVersion != null) {
            Notification notification2 = new Notification();
            notification.publicVersion = notification2;
            this.publicVersion.cloneInto(notification2, z);
        }
        notification.color = this.color;
        notification.mChannelId = this.mChannelId;
        notification.mTimeout = this.mTimeout;
        notification.mShortcutId = this.mShortcutId;
        notification.mLocusId = this.mLocusId;
        notification.mBadgeIcon = this.mBadgeIcon;
        notification.mSettingsText = this.mSettingsText;
        notification.mGroupAlertBehavior = this.mGroupAlertBehavior;
        notification.mFgsDeferBehavior = this.mFgsDeferBehavior;
        notification.mBubbleMetadata = this.mBubbleMetadata;
        notification.mAllowSystemGeneratedContextualActions = this.mAllowSystemGeneratedContextualActions;
        if (!z) {
            notification.lightenPayload();
        }
        notification.semMissedCount = this.semMissedCount;
        notification.semFlags = this.semFlags;
        notification.semPriority = this.semPriority;
        ComponentName componentName = this.semBadgeTarget;
        if (componentName != null) {
            notification.semBadgeTarget = componentName.m926clone();
        }
        notification.parcelDataSize = this.parcelDataSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void visitIconUri(Consumer<Uri> consumer, Icon icon) {
        if (icon == null) {
            return;
        }
        int type = icon.getType();
        if (type == 4 || type == 6) {
            consumer.accept(icon.getUri());
        }
    }

    public void visitUris(Consumer<Uri> consumer) {
        Notification notification = this.publicVersion;
        if (notification != null) {
            notification.visitUris(consumer);
        }
        consumer.accept(this.sound);
        RemoteViews remoteViews = this.tickerView;
        if (remoteViews != null) {
            remoteViews.visitUris(consumer);
        }
        RemoteViews remoteViews2 = this.contentView;
        if (remoteViews2 != null) {
            remoteViews2.visitUris(consumer);
        }
        RemoteViews remoteViews3 = this.bigContentView;
        if (remoteViews3 != null) {
            remoteViews3.visitUris(consumer);
        }
        RemoteViews remoteViews4 = this.headsUpContentView;
        if (remoteViews4 != null) {
            remoteViews4.visitUris(consumer);
        }
        visitIconUri(consumer, this.mSmallIcon);
        visitIconUri(consumer, this.mLargeIcon);
        Action[] actionArr = this.actions;
        if (actionArr != null) {
            for (Action action : actionArr) {
                action.visitUris(consumer);
            }
        }
        Bundle bundle = this.extras;
        if (bundle != null) {
            visitIconUri(consumer, (Icon) bundle.getParcelable(EXTRA_LARGE_ICON_BIG, Icon.class));
            visitIconUri(consumer, (Icon) this.extras.getParcelable(EXTRA_PICTURE_ICON, Icon.class));
            Object obj = this.extras.get(EXTRA_AUDIO_CONTENTS_URI);
            if (obj instanceof Uri) {
                consumer.accept((Uri) obj);
            } else if (obj instanceof String) {
                consumer.accept(Uri.parse((String) obj));
            }
            if (this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI)) {
                consumer.accept(Uri.parse(this.extras.getString(EXTRA_BACKGROUND_IMAGE_URI)));
            }
            ArrayList parcelableArrayList = this.extras.getParcelableArrayList(EXTRA_PEOPLE_LIST, Person.class);
            if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    ((Person) it.next()).visitUris(consumer);
                }
            }
            RemoteInputHistoryItem[] remoteInputHistoryItemArr = (RemoteInputHistoryItem[]) this.extras.getParcelableArray(EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class);
            if (remoteInputHistoryItemArr != null) {
                for (RemoteInputHistoryItem remoteInputHistoryItem : remoteInputHistoryItemArr) {
                    if (remoteInputHistoryItem.getUri() != null) {
                        consumer.accept(remoteInputHistoryItem.getUri());
                    }
                }
            }
            Person person = (Person) this.extras.getParcelable(EXTRA_MESSAGING_PERSON, Person.class);
            if (person != null) {
                person.visitUris(consumer);
            }
            Parcelable[] parcelableArr = (Parcelable[]) this.extras.getParcelableArray(EXTRA_MESSAGES, Parcelable.class);
            if (!ArrayUtils.isEmpty(parcelableArr)) {
                Iterator<MessagingStyle.Message> it2 = MessagingStyle.Message.getMessagesFromBundleArray(parcelableArr).iterator();
                while (it2.hasNext()) {
                    it2.next().visitUris(consumer);
                }
            }
            Parcelable[] parcelableArr2 = (Parcelable[]) this.extras.getParcelableArray(EXTRA_HISTORIC_MESSAGES, Parcelable.class);
            if (!ArrayUtils.isEmpty(parcelableArr2)) {
                Iterator<MessagingStyle.Message> it3 = MessagingStyle.Message.getMessagesFromBundleArray(parcelableArr2).iterator();
                while (it3.hasNext()) {
                    it3.next().visitUris(consumer);
                }
            }
            visitIconUri(consumer, (Icon) this.extras.getParcelable(EXTRA_CONVERSATION_ICON, Icon.class));
            Person person2 = (Person) this.extras.getParcelable(EXTRA_CALL_PERSON, Person.class);
            if (person2 != null) {
                person2.visitUris(consumer);
            }
            visitIconUri(consumer, (Icon) this.extras.getParcelable(EXTRA_VERIFICATION_ICON, Icon.class));
            if (Flags.apiRichOngoing()) {
                visitIconUri(consumer, (Icon) this.extras.getParcelable(EXTRA_PROGRESS_TRACKER_ICON, Icon.class));
                visitIconUri(consumer, (Icon) this.extras.getParcelable(EXTRA_PROGRESS_START_ICON, Icon.class));
                visitIconUri(consumer, (Icon) this.extras.getParcelable(EXTRA_PROGRESS_END_ICON, Icon.class));
            }
        }
        BubbleMetadata bubbleMetadata = this.mBubbleMetadata;
        if (bubbleMetadata != null) {
            visitIconUri(consumer, bubbleMetadata.getIcon());
        }
        Bundle bundle2 = this.extras;
        if (bundle2 == null || !bundle2.containsKey("android.wearable.EXTENSIONS")) {
            return;
        }
        new WearableExtender(this).visitUris(consumer);
    }

    public String loadHeaderAppName(Context context) {
        CharSequence charSequence;
        Trace.beginSection("Notification#loadHeaderAppName");
        try {
            if (this.extras.containsKey(EXTRA_SUBSTITUTE_APP_NAME)) {
                charSequence = this.extras.getString(EXTRA_SUBSTITUTE_APP_NAME);
                if (!TextUtils.isEmpty(charSequence)) {
                    return charSequence.toString();
                }
            } else {
                charSequence = null;
            }
            if (context == null) {
                return null;
            }
            if (TextUtils.isEmpty(charSequence) && getApplicationInfo(context) != null) {
                charSequence = context.getPackageManager().getApplicationLabel(getApplicationInfo(context));
            }
            if (TextUtils.isEmpty(charSequence)) {
                return null;
            }
            return charSequence.toString();
        } finally {
            Trace.endSection();
        }
    }

    public boolean containsCustomViews() {
        if (this.contentView != null || this.bigContentView != null || this.headsUpContentView != null) {
            return true;
        }
        Notification notification = this.publicVersion;
        if (notification != null) {
            return (notification.contentView == null && notification.bigContentView == null && notification.headsUpContentView == null) ? false : true;
        }
        return false;
    }

    public boolean hasTitle() {
        if (this.extras == null) {
            return false;
        }
        if (isStyle(CallStyle.class)) {
            Person person = (Person) this.extras.getParcelable(EXTRA_CALL_PERSON, Person.class);
            return (person == null || TextUtils.isEmpty(person.getName())) ? false : true;
        }
        if (!TextUtils.isEmpty(this.extras.getCharSequence(EXTRA_TITLE))) {
            return true;
        }
        if (isStyle(BigTextStyle.class)) {
            return !TextUtils.isEmpty(this.extras.getCharSequence(EXTRA_TITLE_BIG));
        }
        return false;
    }

    public boolean hasPromotableStyle() {
        Class<? extends Style> notificationStyle = getNotificationStyle();
        return notificationStyle == null || BigTextStyle.class.equals(notificationStyle) || ProgressStyle.class.equals(notificationStyle);
    }

    public boolean hasPromotableCharacteristics() {
        Flags.uiRichOngoing();
        return isRequestPromotedOngoing() && isOngoingEvent() && hasTitle() && hasPromotableStyle() && !isGroupSummary() && !containsCustomViews() && !isColorizedRequested();
    }

    private boolean isOngoingCallStyle() {
        return isStyle(CallStyle.class) && this.extras.getInt(EXTRA_CALL_TYPE, 0) == 2;
    }

    private ApplicationInfo getApplicationInfo(Context context) {
        ApplicationInfo applicationInfo = this.extras.containsKey(EXTRA_BUILDER_APPLICATION_INFO) ? (ApplicationInfo) this.extras.getParcelable(EXTRA_BUILDER_APPLICATION_INFO, ApplicationInfo.class) : null;
        if (applicationInfo != null) {
            return applicationInfo;
        }
        if (context == null) {
            return null;
        }
        return context.getApplicationInfo();
    }

    public final void lightenPayload() {
        Object obj;
        this.tickerView = null;
        this.contentView = null;
        this.bigContentView = null;
        this.headsUpContentView = null;
        this.mLargeIcon = null;
        Bundle bundle = this.extras;
        if (bundle == null || bundle.isEmpty()) {
            return;
        }
        Set<String> keySet = this.extras.keySet();
        int size = keySet.size();
        String[] strArr = (String[]) keySet.toArray(new String[size]);
        for (int i = 0; i < size; i++) {
            String str = strArr[i];
            if (!"android.tv.EXTENSIONS".equals(str) && (obj = this.extras.get(str)) != null && ((obj instanceof Parcelable) || (obj instanceof Parcelable[]) || (obj instanceof SparseArray) || (obj instanceof ArrayList))) {
                this.extras.remove(str);
            }
        }
    }

    public static String safeString(String str) {
        return (str != null && str.length() > 1024) ? str.substring(0, 1024) : str;
    }

    public static CharSequence safeCharSequence(CharSequence charSequence) {
        if (charSequence == null) {
            return charSequence;
        }
        if (charSequence.length() > 1024) {
            charSequence = charSequence.subSequence(0, 1024);
        }
        if (charSequence instanceof Parcelable) {
            Log.e(TAG, "warning: " + charSequence.getClass().getCanonicalName() + " instance is a custom Parcelable and not allowed in Notification");
            return charSequence.toString();
        }
        return removeTextSizeSpans(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence stripStyling(CharSequence charSequence) {
        return charSequence == null ? charSequence : charSequence.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence normalizeBigText(CharSequence charSequence) {
        return charSequence == null ? charSequence : NotificationBigTextNormalizer.normalizeBigText(charSequence.toString());
    }

    private static CharSequence removeTextSizeSpans(CharSequence charSequence) {
        Object obj;
        if (!(charSequence instanceof Spanned)) {
            return charSequence;
        }
        Spanned spanned = (Spanned) charSequence;
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
        for (Object obj2 : spans) {
            Object underlying = obj2 instanceof CharacterStyle ? ((CharacterStyle) obj2).getUnderlying() : obj2;
            if (underlying instanceof TextAppearanceSpan) {
                TextAppearanceSpan textAppearanceSpan = (TextAppearanceSpan) underlying;
                obj = new TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), -1, textAppearanceSpan.getTextColor(), textAppearanceSpan.getLinkTextColor());
            } else {
                if (!(underlying instanceof RelativeSizeSpan) && !(underlying instanceof AbsoluteSizeSpan)) {
                    obj = obj2;
                }
            }
            spannableStringBuilder.setSpan(obj, spanned.getSpanStart(obj2), spanned.getSpanEnd(obj2), spanned.getSpanFlags(obj2));
        }
        return spannableStringBuilder;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(final Parcel parcel, int i) {
        PendingIntent.OnMarshaledListener onMarshaledListener;
        boolean z;
        if (this.allPendingIntents == null) {
            onMarshaledListener = new PendingIntent.OnMarshaledListener() { // from class: android.app.Notification$$ExternalSyntheticLambda0
                @Override // android.app.PendingIntent.OnMarshaledListener
                public final void onMarshaled(PendingIntent pendingIntent, Parcel parcel2, int i2) {
                    Notification.this.lambda$writeToParcel$0(parcel, pendingIntent, parcel2, i2);
                }
            };
            PendingIntent.addOnMarshaledListener(onMarshaledListener);
        } else {
            onMarshaledListener = null;
        }
        try {
            if (parcel.hasClassCookie(Notification.class)) {
                z = false;
            } else {
                parcel.setClassCookie(Notification.class, this.mAllowlistToken);
                z = true;
            }
            try {
                writeToParcelImpl(parcel, i);
                synchronized (this) {
                    parcel.writeArraySet(this.allPendingIntents);
                }
            } finally {
                if (z) {
                    parcel.removeClassCookie(Notification.class, this.mAllowlistToken);
                }
            }
        } finally {
            if (onMarshaledListener != null) {
                PendingIntent.removeOnMarshaledListener(onMarshaledListener);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeToParcel$0(Parcel parcel, PendingIntent pendingIntent, Parcel parcel2, int i) {
        if (parcel == parcel2) {
            synchronized (this) {
                if (this.allPendingIntents == null) {
                    this.allPendingIntents = new ArraySet<>();
                }
                this.allPendingIntents.add(pendingIntent);
            }
        }
    }

    private void writeToParcelImpl(Parcel parcel, int i) {
        Bitmap bitmap;
        int i2;
        parcel.writeInt(1);
        parcel.writeStrongBinder((IBinder) parcel.getClassCookie(Notification.class));
        parcel.writeLong(this.when);
        parcel.writeLong(this.creationTime);
        if (this.mSmallIcon == null && (i2 = this.icon) != 0) {
            this.mSmallIcon = Icon.createWithResource("", i2);
        }
        if (this.mSmallIcon != null) {
            parcel.writeInt(1);
            this.mSmallIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.number);
        if (this.contentIntent != null) {
            parcel.writeInt(1);
            this.contentIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.deleteIntent != null) {
            parcel.writeInt(1);
            this.deleteIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.tickerText != null) {
            parcel.writeInt(1);
            TextUtils.writeToParcel(this.tickerText, parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.tickerView != null) {
            parcel.writeInt(1);
            this.tickerView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.contentView != null) {
            parcel.writeInt(1);
            this.contentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mLargeIcon == null && (bitmap = this.largeIcon) != null) {
            this.mLargeIcon = Icon.createWithBitmap(bitmap);
        }
        if (this.mLargeIcon != null) {
            parcel.writeInt(1);
            this.mLargeIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.defaults);
        parcel.writeInt(this.flags);
        if (this.sound != null) {
            parcel.writeInt(1);
            this.sound.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.audioStreamType);
        if (this.audioAttributes != null) {
            parcel.writeInt(1);
            this.audioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLongArray(this.vibrate);
        parcel.writeInt(this.ledARGB);
        parcel.writeInt(this.ledOnMS);
        parcel.writeInt(this.ledOffMS);
        parcel.writeInt(this.iconLevel);
        if (this.fullScreenIntent != null) {
            parcel.writeInt(1);
            this.fullScreenIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.priority);
        parcel.writeString8(this.category);
        parcel.writeString8(this.mGroupKey);
        parcel.writeString8(this.mSortKey);
        parcel.writeTypedArray(this.actions, 0);
        parcel.writeBundle(this.extras);
        if (this.bigContentView != null) {
            parcel.writeInt(1);
            this.bigContentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.headsUpContentView != null) {
            parcel.writeInt(1);
            this.headsUpContentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.visibility);
        if (this.publicVersion != null) {
            parcel.writeInt(1);
            this.publicVersion.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.color);
        if (this.mChannelId != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mChannelId);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mTimeout);
        if (this.mShortcutId != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mShortcutId);
        } else {
            parcel.writeInt(0);
        }
        if (this.mLocusId != null) {
            parcel.writeInt(1);
            this.mLocusId.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mBadgeIcon);
        if (this.mSettingsText != null) {
            parcel.writeInt(1);
            TextUtils.writeToParcel(this.mSettingsText, parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mGroupAlertBehavior);
        if (this.mBubbleMetadata != null) {
            parcel.writeInt(1);
            this.mBubbleMetadata.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mAllowSystemGeneratedContextualActions);
        parcel.writeInt(this.mFgsDeferBehavior);
        parcel.writeInt(this.semMissedCount);
        parcel.writeInt(this.semFlags);
        parcel.writeInt(this.semPriority);
        if (this.semBadgeTarget != null) {
            parcel.writeInt(1);
            ComponentName.writeToParcel(this.semBadgeTarget, parcel);
        } else {
            parcel.writeInt(0);
        }
        this.parcelDataSize = parcel.dataSize();
    }

    public static boolean areActionsVisiblyDifferent(Notification notification, Notification notification2) {
        Action[] actionArr = notification.actions;
        Action[] actionArr2 = notification2.actions;
        if ((actionArr == null && actionArr2 != null) || (actionArr != null && actionArr2 == null)) {
            return true;
        }
        if (actionArr != null && actionArr2 != null) {
            if (actionArr.length != actionArr2.length) {
                return true;
            }
            for (int i = 0; i < actionArr.length; i++) {
                if (!Objects.equals(String.valueOf(actionArr[i].title), String.valueOf(actionArr2[i].title))) {
                    return true;
                }
                RemoteInput[] remoteInputs = actionArr[i].getRemoteInputs();
                RemoteInput[] remoteInputs2 = actionArr2[i].getRemoteInputs();
                if (remoteInputs == null) {
                    remoteInputs = new RemoteInput[0];
                }
                if (remoteInputs2 == null) {
                    remoteInputs2 = new RemoteInput[0];
                }
                if (remoteInputs.length != remoteInputs2.length) {
                    return true;
                }
                for (int i2 = 0; i2 < remoteInputs.length; i2++) {
                    if (!Objects.equals(String.valueOf(remoteInputs[i2].getLabel()), String.valueOf(remoteInputs2[i2].getLabel()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean areIconsDifferent(Notification notification, Notification notification2) {
        return areIconsMaybeDifferent(notification.getSmallIcon(), notification2.getSmallIcon()) || areIconsMaybeDifferent(notification.getLargeIcon(), notification2.getLargeIcon());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areIconsMaybeDifferent(Icon icon, Icon icon2) {
        if (icon == icon2) {
            return false;
        }
        if (icon != null && icon2 != null) {
            if (icon.sameAs(icon2)) {
                return false;
            }
            int type = icon.getType();
            if (type != icon2.getType()) {
                return true;
            }
            if (type != 1 && type != 5) {
                return true;
            }
            Bitmap bitmap = icon.getBitmap();
            Bitmap bitmap2 = icon2.getBitmap();
            if (bitmap.getWidth() == bitmap2.getWidth() && bitmap.getHeight() == bitmap2.getHeight() && bitmap.getConfig() == bitmap2.getConfig() && bitmap.getGenerationId() == bitmap2.getGenerationId()) {
                return false;
            }
        }
        return true;
    }

    public static boolean areStyledNotificationsVisiblyDifferent(Builder builder, Builder builder2) {
        if (builder.getStyle() == null) {
            return builder2.getStyle() != null;
        }
        if (builder2.getStyle() == null) {
            return true;
        }
        return builder.getStyle().areNotificationsVisiblyDifferent(builder2.getStyle());
    }

    public static boolean areRemoteViewsChanged(Builder builder, Builder builder2) {
        return !Objects.equals(Boolean.valueOf(builder.usesStandardHeader()), Boolean.valueOf(builder2.usesStandardHeader())) || areRemoteViewsChanged(builder.mN.contentView, builder2.mN.contentView) || areRemoteViewsChanged(builder.mN.bigContentView, builder2.mN.bigContentView) || areRemoteViewsChanged(builder.mN.headsUpContentView, builder2.mN.headsUpContentView);
    }

    private static boolean areRemoteViewsChanged(RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (remoteViews == null && remoteViews2 == null) {
            return false;
        }
        return (remoteViews == null && remoteViews2 != null) || !((remoteViews == null || remoteViews2 != null) && Objects.equals(Integer.valueOf(remoteViews.getLayoutId()), Integer.valueOf(remoteViews2.getLayoutId())) && Objects.equals(Integer.valueOf(remoteViews.getSequenceNumber()), Integer.valueOf(remoteViews2.getSequenceNumber())));
    }

    private void fixDuplicateExtras() {
        if (this.extras != null) {
            fixDuplicateExtra(this.mLargeIcon, EXTRA_LARGE_ICON);
        }
    }

    private void fixDuplicateExtra(Parcelable parcelable, String str) {
        if (parcelable == null || this.extras.getParcelable(str, Parcelable.class) == null) {
            return;
        }
        this.extras.putParcelable(str, parcelable);
    }

    @Deprecated
    public void setLatestEventInfo(Context context, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        if (context.getApplicationInfo().targetSdkVersion > 22) {
            Log.e(TAG, "setLatestEventInfo() is deprecated and you should feel deprecated.", new Throwable());
        }
        if (context.getApplicationInfo().targetSdkVersion < 24) {
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        }
        Builder builder = new Builder(context, this);
        if (charSequence != null) {
            builder.setContentTitle(charSequence);
        }
        if (charSequence2 != null) {
            builder.setContentText(charSequence2);
        }
        builder.setContentIntent(pendingIntent);
        builder.build();
    }

    public void overrideAllowlistToken(IBinder iBinder) {
        this.mAllowlistToken = iBinder;
        Notification notification = this.publicVersion;
        if (notification != null) {
            notification.overrideAllowlistToken(iBinder);
        }
    }

    public IBinder getAllowlistToken() {
        return this.mAllowlistToken;
    }

    public static void addFieldsFromContext(Context context, Notification notification) {
        addFieldsFromContext(context.getApplicationInfo(), notification);
    }

    public static void addFieldsFromContext(ApplicationInfo applicationInfo, Notification notification) {
        notification.extras.putParcelable(EXTRA_BUILDER_APPLICATION_INFO, applicationInfo);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, getRedatedString(getChannelId()));
        protoOutputStream.write(1133871366146L, this.tickerText != null);
        protoOutputStream.write(1120986464259L, this.flags);
        protoOutputStream.write(1120986464260L, this.color);
        protoOutputStream.write(1138166333445L, this.category);
        protoOutputStream.write(1138166333446L, this.mGroupKey);
        protoOutputStream.write(1138166333447L, this.mSortKey);
        Action[] actionArr = this.actions;
        if (actionArr != null) {
            protoOutputStream.write(1120986464264L, actionArr.length);
        }
        int i = this.visibility;
        if (i >= -1 && i <= 1) {
            protoOutputStream.write(1159641169929L, i);
        }
        Notification notification = this.publicVersion;
        if (notification != null) {
            notification.dumpDebug(protoOutputStream, 1146756268042L);
        }
        protoOutputStream.end(start);
    }

    private String getRedatedString(String str) {
        return isMatchPrivatePattern(str) ? (String) TextUtils.trimToLengthWithEllipsis(str, 6) : str;
    }

    private boolean isMatchPrivatePattern(String str) {
        if (str == null) {
            return false;
        }
        if (Patterns.PHONE.matcher(str).matches() || Patterns.WEB_URL.matcher(str).matches()) {
            return true;
        }
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '@') {
                z = true;
            }
            if (z && str.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Notification(channel=");
        sb.append(getRedatedString(getChannelId()));
        sb.append(" shortcut=");
        sb.append(getShortcutId());
        sb.append(" contentView=");
        RemoteViews remoteViews = this.contentView;
        if (remoteViews != null) {
            sb.append(remoteViews.getPackage());
            sb.append("/0x");
            sb.append(Integer.toHexString(this.contentView.getLayoutId()));
        } else {
            sb.append(PerfettoProtoLogImpl.NULL_STRING);
        }
        sb.append(" vibrate=");
        if ((this.defaults & 2) != 0) {
            sb.append("default");
        } else {
            long[] jArr = this.vibrate;
            if (jArr != null) {
                int length = jArr.length - 1;
                sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                for (int i = 0; i < length; i++) {
                    sb.append(this.vibrate[i]);
                    sb.append(',');
                }
                if (length != -1) {
                    sb.append(this.vibrate[length]);
                }
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            } else {
                sb.append(PerfettoProtoLogImpl.NULL_STRING);
            }
        }
        sb.append(" sound=");
        if ((this.defaults & 1) != 0) {
            sb.append("default");
        } else {
            Uri uri = this.sound;
            if (uri != null) {
                sb.append(uri.toString());
            } else {
                sb.append(PerfettoProtoLogImpl.NULL_STRING);
            }
        }
        if (this.tickerText != null) {
            sb.append(" tick");
        }
        sb.append(" defaults=");
        sb.append(defaultsToString(this.defaults));
        sb.append(" flags=");
        sb.append(flagsToString(this.flags));
        sb.append(String.format(" color=0x%08x", Integer.valueOf(this.color)));
        if (this.category != null) {
            sb.append(" category=");
            sb.append(this.category);
        }
        if (this.mGroupKey != null) {
            sb.append(" groupKey=");
            sb.append(this.mGroupKey);
        }
        if (this.mSortKey != null) {
            sb.append(" sortKey=");
            sb.append(this.mSortKey);
        }
        if (this.actions != null) {
            sb.append(" actions=");
            sb.append(this.actions.length);
        }
        sb.append(" vis=");
        sb.append(visibilityToString(this.visibility));
        if (this.publicVersion != null) {
            sb.append(" publicVersion=");
            sb.append(this.publicVersion.toString());
        }
        if (this.mLocusId != null) {
            sb.append(" locusId=");
            sb.append(this.mLocusId);
        }
        sb.append(" semFlags=0x");
        sb.append(Integer.toHexString(this.semFlags));
        sb.append(" semPriority=");
        sb.append(this.semPriority);
        sb.append(" semMissedCount=");
        sb.append(this.semMissedCount);
        if (this.semBadgeTarget != null) {
            sb.append(" semBadgeTarget=");
            sb.append(this.semBadgeTarget.toShortString());
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static String visibilityToString(int i) {
        if (i == -1) {
            return "SECRET";
        }
        if (i == 0) {
            return "PRIVATE";
        }
        if (i == 1) {
            return "PUBLIC";
        }
        return "UNKNOWN(" + String.valueOf(i) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String priorityToString(int i) {
        if (i == -2) {
            return "MIN";
        }
        if (i == -1) {
            return "LOW";
        }
        if (i == 0) {
            return "DEFAULT";
        }
        if (i == 1) {
            return "HIGH";
        }
        if (i == 2) {
            return "MAX";
        }
        return "UNKNOWN(" + String.valueOf(i) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String flagsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("SHOW_LIGHTS");
            i &= -2;
        }
        if ((i & 2) != 0) {
            arrayList.add("ONGOING_EVENT");
            i &= -3;
        }
        if ((i & 4) != 0) {
            arrayList.add("INSISTENT");
            i &= -5;
        }
        if ((i & 8) != 0) {
            arrayList.add("ONLY_ALERT_ONCE");
            i &= -9;
        }
        if ((i & 16) != 0) {
            arrayList.add("AUTO_CANCEL");
            i &= -17;
        }
        if ((i & 32) != 0) {
            arrayList.add("NO_CLEAR");
            i &= -33;
        }
        if ((i & 64) != 0) {
            arrayList.add(SystemNotificationChannels.FOREGROUND_SERVICE);
            i &= -65;
        }
        if ((i & 128) != 0) {
            arrayList.add("HIGH_PRIORITY");
            i &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
        if ((i & 256) != 0) {
            arrayList.add("LOCAL_ONLY");
            i &= -257;
        }
        if ((i & 512) != 0) {
            arrayList.add("GROUP_SUMMARY");
            i &= -513;
        }
        if ((i & 1024) != 0) {
            arrayList.add("AUTOGROUP_SUMMARY");
            i &= -1025;
        }
        if ((i & 2048) != 0) {
            arrayList.add("CAN_COLORIZE");
            i &= -2049;
        }
        if ((i & 4096) != 0) {
            arrayList.add("BUBBLE");
            i &= -4097;
        }
        if ((i & 8192) != 0) {
            arrayList.add("NO_DISMISS");
            i &= -8193;
        }
        if ((i & 16384) != 0) {
            arrayList.add("FSI_REQUESTED_BUT_DENIED");
            i &= -16385;
        }
        if ((32768 & i) != 0) {
            arrayList.add("USER_INITIATED_JOB");
            i &= -32769;
        }
        if (Flags.lifetimeExtensionRefactor() && (65536 & i) != 0) {
            arrayList.add("LIFETIME_EXTENDED_BY_DIRECT_REPLY");
            i &= -65537;
        }
        if (Flags.apiRichOngoing() && (262144 & i) != 0) {
            arrayList.add("PROMOTED_ONGOING");
            i &= -262145;
        }
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.notificationSilentFlag() && (131072 & i) != 0) {
            arrayList.add("SILENT");
            i &= -131073;
        }
        if (arrayList.isEmpty()) {
            return "0";
        }
        if (i != 0) {
            arrayList.add(String.format("UNKNOWN(0x%08x)", Integer.valueOf(i)));
        }
        return String.join(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, arrayList);
    }

    public static String defaultsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == -1) {
            arrayList.add("ALL");
            i = 0;
        }
        if ((i & 1) != 0) {
            arrayList.add("SOUND");
            i &= -2;
        }
        if ((i & 2) != 0) {
            arrayList.add("VIBRATE");
            i &= -3;
        }
        if ((i & 4) != 0) {
            arrayList.add("LIGHTS");
            i &= -5;
        }
        if (arrayList.isEmpty()) {
            return "0";
        }
        if (i != 0) {
            arrayList.add(String.format("UNKNOWN(0x%08x)", Integer.valueOf(i)));
        }
        return String.join(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, arrayList);
    }

    public String getShortCriticalText() {
        return this.extras.getString(EXTRA_SHORT_CRITICAL_TEXT);
    }

    public boolean isOngoingEvent() {
        return (this.flags & 2) != 0;
    }

    public boolean hasCompletedProgress() {
        return this.extras.containsKey(EXTRA_PROGRESS) && this.extras.containsKey(EXTRA_PROGRESS_MAX) && this.extras.getInt(EXTRA_PROGRESS_MAX) != 0 && this.extras.getInt(EXTRA_PROGRESS) == this.extras.getInt(EXTRA_PROGRESS_MAX);
    }

    @Deprecated
    public String getChannel() {
        return this.mChannelId;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    @Deprecated
    public long getTimeout() {
        return this.mTimeout;
    }

    public long getTimeoutAfter() {
        return this.mTimeout;
    }

    public void setTimeoutAfter(long j) {
        this.mTimeout = j;
    }

    public int getBadgeIconType() {
        return this.mBadgeIcon;
    }

    public String getShortcutId() {
        return this.mShortcutId;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public CharSequence getSettingsText() {
        return this.mSettingsText;
    }

    public int getGroupAlertBehavior() {
        return this.mGroupAlertBehavior;
    }

    public void setGroupAlertBehavior(int i) {
        this.mGroupAlertBehavior = i;
    }

    public BubbleMetadata getBubbleMetadata() {
        return this.mBubbleMetadata;
    }

    public void setBubbleMetadata(BubbleMetadata bubbleMetadata) {
        this.mBubbleMetadata = bubbleMetadata;
    }

    public boolean getAllowSystemGeneratedContextualActions() {
        return this.mAllowSystemGeneratedContextualActions;
    }

    public Icon getSmallIcon() {
        return this.mSmallIcon;
    }

    public void setSmallIcon(Icon icon) {
        this.mSmallIcon = icon;
    }

    public Icon getLargeIcon() {
        return this.mLargeIcon;
    }

    public boolean hasAppProvidedWhen() {
        long j = this.when;
        return (j == 0 || j == this.creationTime) ? false : true;
    }

    public boolean isGroupSummary() {
        return (this.mGroupKey == null || (this.flags & 512) == 0) ? false : true;
    }

    public boolean isGroupChild() {
        return this.mGroupKey != null && (this.flags & 512) == 0;
    }

    public boolean suppressAlertingDueToGrouping() {
        if (isGroupSummary() && getGroupAlertBehavior() == 2) {
            return true;
        }
        return isGroupChild() && getGroupAlertBehavior() == 1;
    }

    public Pair<RemoteInput, Action> findRemoteInputActionPair(boolean z) {
        Action[] actionArr;
        if (isPromotedOngoing() || (actionArr = this.actions) == null) {
            return null;
        }
        for (Action action : actionArr) {
            if (action.getRemoteInputs() != null) {
                RemoteInput remoteInput = null;
                for (RemoteInput remoteInput2 : action.getRemoteInputs()) {
                    if (remoteInput2.getAllowFreeFormInput() || !z) {
                        remoteInput = remoteInput2;
                    }
                }
                if (remoteInput != null) {
                    return Pair.create(remoteInput, action);
                }
            }
        }
        return null;
    }

    public List<Action> getContextualActions() {
        if (this.actions == null || isPromotedOngoing()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        for (Action action : this.actions) {
            if (action.isContextual()) {
                arrayList.add(action);
            }
        }
        return arrayList;
    }

    public void fixSilentGroup() {
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.notificationSilentFlag() && "silent".equals(this.mGroupKey)) {
            this.mGroupKey = null;
            this.flags |= 131072;
        }
    }

    public boolean isSilent() {
        return com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.notificationSilentFlag() ? (this.flags & 131072) != 0 : "silent".equals(getGroup()) && suppressAlertingDueToGrouping();
    }

    public static class Builder {
        public static final String EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.bigViewActionCount";
        public static final String EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.contentViewActionCount";
        public static final String EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.hudViewActionCount";
        private static final boolean USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY = SystemProperties.getBoolean("notifications.only_title", true);
        private ArrayList<Action> mActions;
        private ContrastColorUtil mColorUtil;
        Colors mColors;
        private Context mContext;
        private boolean mInNightMode;
        private boolean mIsLegacy;
        private boolean mIsLegacyInitialized;
        private Notification mN;
        StandardTemplateParams mParams;
        private ArrayList<Person> mPersonList;
        private Style mStyle;
        private boolean mTintActionButtons;
        private Bundle mUserExtras;

        private int getActionLayoutResource() {
            return R.layout.notification_material_action;
        }

        private int getActionTombstoneLayoutResource() {
            return R.layout.notification_material_action_tombstone;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCollapsedConversationLayoutResource() {
            return R.layout.notification_2025_template_collapsed_conversation;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getConversationLayoutResource() {
            return R.layout.notification_template_material_conversation;
        }

        private int getEmphasizedActionLayoutResource() {
            return R.layout.notification_material_action_emphasized;
        }

        private int getEmphasizedTombstoneActionLayoutResource() {
            return R.layout.notification_material_action_emphasized_tombstone;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getExpandedConversationLayoutResource() {
            return R.layout.notification_2025_template_expanded_conversation;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(Context context, String str) {
            this(context, (Notification) null);
            this.mN.mChannelId = str;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        @Deprecated
        public Builder(Context context) {
            this(context, (Notification) null);
        }

        public Builder(Context context, Notification notification) {
            ArrayList parcelableArrayList;
            this.mUserExtras = new Bundle();
            this.mActions = new ArrayList<>(3);
            this.mPersonList = new ArrayList<>();
            this.mParams = new StandardTemplateParams();
            this.mColors = new Colors();
            this.mContext = context;
            Resources resources = context.getResources();
            this.mTintActionButtons = resources.getBoolean(R.bool.config_tintNotificationActionButtons);
            if (resources.getBoolean(R.bool.config_enableNightMode)) {
                this.mInNightMode = (resources.getConfiguration().uiMode & 48) == 32;
            }
            if (notification == null) {
                this.mN = new Notification();
                if (context.getApplicationInfo().targetSdkVersion < 24) {
                    this.mN.extras.putBoolean(Notification.EXTRA_SHOW_WHEN, true);
                }
                this.mN.priority = 0;
                this.mN.visibility = 0;
                return;
            }
            this.mN = notification;
            if (notification.actions != null) {
                Collections.addAll(this.mActions, this.mN.actions);
            }
            if (this.mN.extras.containsKey(Notification.EXTRA_PEOPLE_LIST) && (parcelableArrayList = this.mN.extras.getParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, Person.class)) != null && !parcelableArrayList.isEmpty()) {
                this.mPersonList.addAll(parcelableArrayList);
            }
            if (this.mN.getSmallIcon() == null && this.mN.icon != 0) {
                setSmallIcon(this.mN.icon);
            }
            if (this.mN.getLargeIcon() == null && this.mN.largeIcon != null) {
                setLargeIcon(this.mN.largeIcon);
            }
            String string = this.mN.extras.getString(Notification.EXTRA_TEMPLATE);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            Class<? extends Style> notificationStyleClass = Notification.getNotificationStyleClass(string);
            if (notificationStyleClass == null) {
                Log.d(Notification.TAG, "Unknown style class: " + string);
                return;
            }
            try {
                Class[] clsArr = new Class[0];
                Constructor<? extends Style> declaredConstructor = notificationStyleClass.getDeclaredConstructor(null);
                declaredConstructor.setAccessible(true);
                Style newInstance = declaredConstructor.newInstance(null);
                newInstance.restoreFromExtras(this.mN.extras);
                if (newInstance != null) {
                    setStyle(newInstance);
                }
            } catch (Throwable th) {
                Log.e(Notification.TAG, "Could not create Style", th);
            }
        }

        private ContrastColorUtil getColorUtil() {
            if (this.mColorUtil == null) {
                this.mColorUtil = ContrastColorUtil.getInstance(this.mContext);
            }
            return this.mColorUtil;
        }

        public Builder setShortcutId(String str) {
            this.mN.mShortcutId = str;
            return this;
        }

        public Builder setLocusId(LocusId locusId) {
            this.mN.mLocusId = locusId;
            return this;
        }

        public Builder setBadgeIconType(int i) {
            this.mN.mBadgeIcon = i;
            return this;
        }

        public Builder setGroupAlertBehavior(int i) {
            this.mN.mGroupAlertBehavior = i;
            return this;
        }

        public Builder setBubbleMetadata(BubbleMetadata bubbleMetadata) {
            this.mN.mBubbleMetadata = bubbleMetadata;
            return this;
        }

        @Deprecated
        public Builder setChannel(String str) {
            this.mN.mChannelId = str;
            return this;
        }

        public Builder setChannelId(String str) {
            this.mN.mChannelId = str;
            return this;
        }

        @Deprecated
        public Builder setTimeout(long j) {
            this.mN.mTimeout = j;
            return this;
        }

        public Builder setTimeoutAfter(long j) {
            this.mN.mTimeout = j;
            return this;
        }

        public Builder setWhen(long j) {
            this.mN.when = j;
            return this;
        }

        public Builder setShowWhen(boolean z) {
            this.mN.extras.putBoolean(Notification.EXTRA_SHOW_WHEN, z);
            return this;
        }

        public Builder setUsesChronometer(boolean z) {
            this.mN.extras.putBoolean(Notification.EXTRA_SHOW_CHRONOMETER, z);
            return this;
        }

        public Builder setChronometerCountDown(boolean z) {
            this.mN.extras.putBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN, z);
            return this;
        }

        public Builder setSmallIcon(int i) {
            return setSmallIcon(i != 0 ? Icon.createWithResource(this.mContext, i) : null);
        }

        public Builder setSmallIcon(int i, int i2) {
            this.mN.iconLevel = i2;
            return setSmallIcon(i);
        }

        public Builder setSmallIcon(Icon icon) {
            this.mN.setSmallIcon(icon);
            if (icon != null && icon.getType() == 2) {
                this.mN.icon = icon.getResId();
            }
            return this;
        }

        public Builder setSilent(boolean z) {
            if (z) {
                if (this.mN.isGroupSummary()) {
                    setGroupAlertBehavior(2);
                } else {
                    setGroupAlertBehavior(1);
                }
                setVibrate(null);
                setSound(null);
                this.mN.defaults &= -2;
                this.mN.defaults &= -3;
                setDefaults(this.mN.defaults);
                if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.notificationSilentFlag()) {
                    this.mN.flags |= 131072;
                    return this;
                }
                if (TextUtils.isEmpty(this.mN.mGroupKey)) {
                    setGroup("silent");
                }
            }
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_TITLE, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setContentText(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_TEXT, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_SUB_TEXT, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setSettingsText(CharSequence charSequence) {
            this.mN.mSettingsText = Notification.safeCharSequence(charSequence);
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] charSequenceArr) {
            if (charSequenceArr == null) {
                this.mN.extras.putCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY, null);
                return this;
            }
            int min = Math.min(5, charSequenceArr.length);
            CharSequence[] charSequenceArr2 = new CharSequence[min];
            RemoteInputHistoryItem[] remoteInputHistoryItemArr = new RemoteInputHistoryItem[min];
            for (int i = 0; i < min; i++) {
                charSequenceArr2[i] = Notification.safeCharSequence(charSequenceArr[i]);
                remoteInputHistoryItemArr[i] = new RemoteInputHistoryItem(charSequenceArr[i]);
            }
            this.mN.extras.putCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY, charSequenceArr2);
            this.mN.extras.putParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, remoteInputHistoryItemArr);
            return this;
        }

        public Builder setRemoteInputHistory(RemoteInputHistoryItem[] remoteInputHistoryItemArr) {
            if (remoteInputHistoryItemArr == null) {
                this.mN.extras.putParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, null);
                return this;
            }
            int min = Math.min(5, remoteInputHistoryItemArr.length);
            RemoteInputHistoryItem[] remoteInputHistoryItemArr2 = new RemoteInputHistoryItem[min];
            for (int i = 0; i < min; i++) {
                remoteInputHistoryItemArr2[i] = remoteInputHistoryItemArr[i];
            }
            this.mN.extras.putParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, remoteInputHistoryItemArr2);
            return this;
        }

        public Builder setShowRemoteInputSpinner(boolean z) {
            this.mN.extras.putBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, z);
            return this;
        }

        public Builder setHideSmartReplies(boolean z) {
            this.mN.extras.putBoolean(Notification.EXTRA_HIDE_SMART_REPLIES, z);
            return this;
        }

        public Builder setNumber(int i) {
            this.mN.number = i;
            return this;
        }

        @Deprecated
        public Builder setContentInfo(CharSequence charSequence) {
            this.mN.extras.putCharSequence(Notification.EXTRA_INFO_TEXT, Notification.safeCharSequence(charSequence));
            return this;
        }

        public Builder setShortCriticalText(String str) {
            this.mN.extras.putString(Notification.EXTRA_SHORT_CRITICAL_TEXT, Notification.safeString(str));
            return this;
        }

        public Builder setProgress(int i, int i2, boolean z) {
            this.mN.extras.putInt(Notification.EXTRA_PROGRESS, i2);
            this.mN.extras.putInt(Notification.EXTRA_PROGRESS_MAX, i);
            this.mN.extras.putBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE, z);
            return this;
        }

        @Deprecated
        public Builder setContent(RemoteViews remoteViews) {
            return setCustomContentView(remoteViews);
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            this.mN.contentView = remoteViews;
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            this.mN.bigContentView = remoteViews;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.mN.headsUpContentView = remoteViews;
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            this.mN.contentIntent = pendingIntent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.mN.deleteIntent = pendingIntent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean z) {
            this.mN.fullScreenIntent = pendingIntent;
            setFlag(128, z);
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            this.mN.tickerText = Notification.safeCharSequence(charSequence);
            return this;
        }

        @Deprecated
        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            setTicker(charSequence);
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            return setLargeIcon(bitmap != null ? Icon.createWithBitmap(bitmap) : null);
        }

        public Builder setLargeIcon(Icon icon) {
            this.mN.mLargeIcon = icon;
            this.mN.extras.putParcelable(Notification.EXTRA_LARGE_ICON, icon);
            return this;
        }

        @Deprecated
        public Builder setSound(Uri uri) {
            this.mN.sound = uri;
            this.mN.audioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri uri, int i) {
            PlayerBase.deprecateStreamTypeForPlayback(i, Notification.TAG, "setSound()");
            this.mN.sound = uri;
            this.mN.audioStreamType = i;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri uri, AudioAttributes audioAttributes) {
            this.mN.sound = uri;
            this.mN.audioAttributes = audioAttributes;
            return this;
        }

        @Deprecated
        public Builder setVibrate(long[] jArr) {
            this.mN.vibrate = jArr;
            return this;
        }

        @Deprecated
        public Builder setLights(int i, int i2, int i3) {
            this.mN.ledARGB = i;
            this.mN.ledOnMS = i2;
            this.mN.ledOffMS = i3;
            if (i2 == 0 && i3 == 0) {
                return this;
            }
            this.mN.flags |= 1;
            return this;
        }

        public Builder setOngoing(boolean z) {
            setFlag(2, z);
            return this;
        }

        public Builder setRequestPromotedOngoing(boolean z) {
            getExtras().putBoolean(Notification.EXTRA_REQUEST_PROMOTED_ONGOING, z);
            return this;
        }

        public Builder setColorized(boolean z) {
            this.mN.extras.putBoolean(Notification.EXTRA_COLORIZED, z);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean z) {
            setFlag(8, z);
            return this;
        }

        public Builder setForegroundServiceBehavior(int i) {
            this.mN.mFgsDeferBehavior = i;
            return this;
        }

        public Builder setAutoCancel(boolean z) {
            setFlag(16, z);
            return this;
        }

        public Builder setLocalOnly(boolean z) {
            setFlag(256, z);
            return this;
        }

        @Deprecated
        public Builder setDefaults(int i) {
            this.mN.defaults = i;
            return this;
        }

        @Deprecated
        public Builder setPriority(int i) {
            this.mN.priority = i;
            return this;
        }

        public Builder setCategory(String str) {
            this.mN.category = str;
            return this;
        }

        public Builder addPerson(String str) {
            addPerson(new Person.Builder().setUri(str).build());
            return this;
        }

        public Builder addPerson(Person person) {
            this.mPersonList.add(person);
            return this;
        }

        public Builder setGroup(String str) {
            this.mN.mGroupKey = str;
            return this;
        }

        public Builder setGroupSummary(boolean z) {
            setFlag(512, z);
            return this;
        }

        public Builder setSortKey(String str) {
            this.mN.mSortKey = str;
            return this;
        }

        public Builder semSetCapsule(Bundle bundle) {
            this.mN.extras.putBundle(Notification.SEM_EXTRA_CAPSULE, bundle);
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.mUserExtras.putAll(bundle);
            }
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            if (bundle != null) {
                this.mUserExtras = bundle;
            }
            return this;
        }

        public Bundle getExtras() {
            return this.mUserExtras;
        }

        @Deprecated
        public Builder addAction(int i, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(new Action(i, Notification.safeCharSequence(charSequence), pendingIntent));
            return this;
        }

        public Builder addAction(Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        public Builder setActions(Action... actionArr) {
            this.mActions.clear();
            for (Action action : actionArr) {
                if (action != null) {
                    this.mActions.add(action);
                }
            }
            return this;
        }

        public Builder setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (style == null) {
                    this.mN.extras.remove(Notification.EXTRA_TEMPLATE);
                } else {
                    style.setBuilder(this);
                    this.mN.extras.putString(Notification.EXTRA_TEMPLATE, style.getClass().getName());
                    return this;
                }
            }
            return this;
        }

        public Style getStyle() {
            return this.mStyle;
        }

        public Builder setVisibility(int i) {
            this.mN.visibility = i;
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            if (notification != null) {
                this.mN.publicVersion = new Notification();
                notification.cloneInto(this.mN.publicVersion, true);
                return this;
            }
            this.mN.publicVersion = null;
            return this;
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        public Builder setFlag(int i, boolean z) {
            if (z) {
                Notification notification = this.mN;
                notification.flags = i | notification.flags;
                return this;
            }
            Notification notification2 = this.mN;
            notification2.flags = (~i) & notification2.flags;
            return this;
        }

        public Builder setColor(int i) {
            this.mN.color = i;
            sanitizeColor();
            return this;
        }

        private void bindPhishingAlertIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            remoteViews.setDrawableTint(R.id.phishing_alert, false, getColors(standardTemplateParams).getErrorColor(), PorterDuff.Mode.SRC_ATOP);
        }

        private Drawable getProfileBadgeDrawable() {
            if (this.mContext.getUserId() == 0) {
                return null;
            }
            return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getDrawable(getUpdatableProfileBadgeId(), DevicePolicyResources.Drawables.Style.SOLID_COLORED, DevicePolicyResources.Drawables.Source.NOTIFICATION, new Supplier() { // from class: android.app.Notification$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    Drawable defaultProfileBadgeDrawable;
                    defaultProfileBadgeDrawable = Notification.Builder.this.getDefaultProfileBadgeDrawable();
                    return defaultProfileBadgeDrawable;
                }
            });
        }

        private String getUpdatableProfileBadgeId() {
            return (((UserManager) this.mContext.getSystemService(UserManager.class)).isManagedProfile() || ((UserManager) this.mContext.getSystemService(UserManager.class)).isPrivateProfile()) ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON : DevicePolicyResources.UNDEFINED;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Drawable getDefaultProfileBadgeDrawable() {
            return this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(this.mContext.getUserId()), 0);
        }

        private Bitmap getProfileBadge() {
            Drawable profileBadgeDrawable = getProfileBadgeDrawable();
            if (profileBadgeDrawable == null) {
                return null;
            }
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(Flags.notificationsRedesignTemplates() ? R.dimen.notification_2025_badge_size : R.dimen.notification_badge_size);
            Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            profileBadgeDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            profileBadgeDrawable.draw(canvas);
            return createBitmap;
        }

        private void bindProfileBadge(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            Bitmap profileBadge = getProfileBadge();
            if (profileBadge != null) {
                remoteViews.setImageViewBitmap(R.id.profile_badge, profileBadge);
                remoteViews.setViewVisibility(R.id.profile_badge, 0);
                if (isBackgroundColorized(standardTemplateParams)) {
                    remoteViews.setDrawableTint(R.id.profile_badge, false, getPrimaryTextColor(standardTemplateParams), PorterDuff.Mode.SRC_ATOP);
                }
                remoteViews.setContentDescription(R.id.profile_badge, ((UserManager) this.mContext.getSystemService(UserManager.class)).getProfileAccessibilityString(this.mContext.getUserId()));
            }
        }

        private void bindAlertedIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            remoteViews.setDrawableTint(R.id.alerted_icon, false, getColors(standardTemplateParams).getSecondaryTextColor(), PorterDuff.Mode.SRC_IN);
        }

        public boolean usesStandardHeader() {
            if (this.mN.mUsesStandardHeader) {
                return true;
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 24 && this.mN.contentView == null && this.mN.bigContentView == null) {
                return true;
            }
            return (this.mN.contentView == null || Notification.isStandardLayout(this.mN.contentView.getLayoutId())) && (this.mN.bigContentView == null || Notification.isStandardLayout(this.mN.bigContentView.getLayoutId()));
        }

        private void resetStandardTemplate(RemoteViews remoteViews) {
            resetNotificationHeader(remoteViews);
            remoteViews.setViewVisibility(R.id.right_icon, 8);
            remoteViews.setViewVisibility(16908310, 8);
            remoteViews.setTextViewText(16908310, null);
            remoteViews.setViewVisibility(R.id.text, 8);
            remoteViews.setTextViewText(R.id.text, null);
        }

        private void resetNotificationHeader(RemoteViews remoteViews) {
            remoteViews.setBoolean(R.id.expand_button, "setExpanded", false);
            remoteViews.setViewVisibility(R.id.app_name_text, 8);
            remoteViews.setTextViewText(R.id.app_name_text, null);
            remoteViews.setViewVisibility(R.id.chronometer, 8);
            remoteViews.setViewVisibility(R.id.header_text, 8);
            remoteViews.setTextViewText(R.id.header_text, null);
            remoteViews.setViewVisibility(R.id.header_text_secondary, 8);
            remoteViews.setTextViewText(R.id.header_text_secondary, null);
            remoteViews.setViewVisibility(R.id.header_text_divider, 8);
            remoteViews.setViewVisibility(R.id.header_text_secondary_divider, 8);
            remoteViews.setViewVisibility(R.id.time_divider, 8);
            remoteViews.setViewVisibility(R.id.time, 8);
            remoteViews.setImageViewIcon(R.id.profile_badge, null);
            remoteViews.setViewVisibility(R.id.profile_badge, 8);
            this.mN.mUsesStandardHeader = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public RemoteViews applyStandardTemplate(int i, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            standardTemplateParams.headerless(i == getCollapsedBaseLayoutResource() || i == getHeadsUpBaseLayoutResource() || i == getCompactHeadsUpBaseLayoutResource() || i == getMessagingCompactHeadsUpLayoutResource() || i == getCollapsedMessagingLayoutResource() || i == getCollapsedMediaLayoutResource() || i == getCollapsedConversationLayoutResource() || i == 17367295 || i == 17367290 || Flags.notificationsRedesignTemplates() || i == getCollapsedMediaLayoutResource());
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), i);
            resetStandardTemplate(builderRemoteViews);
            Bundle bundle = this.mN.extras;
            updateBackgroundColor(builderRemoteViews, standardTemplateParams);
            bindNotificationHeader(builderRemoteViews, standardTemplateParams);
            bindLargeIconAndApplyMargin(builderRemoteViews, standardTemplateParams, templateBindResult);
            if ((i == getExpandedBaseLayoutResource() || i == getBigTextLayoutResource()) && (standardTemplateParams.mHideRightIcon || (standardTemplateParams.mPromotedPicture == null && this.mN.mLargeIcon == null))) {
                Resources resources = this.mContext.getResources();
                builderRemoteViews.setViewLayoutMargin(standardTemplateParams.mTextViewId, 5, resources.getDimension(R.dimen.notification_content_margin_end) / resources.getDisplayMetrics().density, 1);
            }
            boolean handleProgressBar = handleProgressBar(builderRemoteViews, bundle, standardTemplateParams);
            if (standardTemplateParams.hasTitle()) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTitleViewId, 0);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTitleViewId, ensureColorSpanContrastOrStripStyling(standardTemplateParams.mTitle, standardTemplateParams));
                setTextViewColorPrimary(builderRemoteViews, standardTemplateParams.mTitleViewId, standardTemplateParams);
            } else if (standardTemplateParams.mTitleViewId != 16908310) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTitleViewId, 8);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTitleViewId, null);
            }
            if (standardTemplateParams.mText != null && standardTemplateParams.mText.length() != 0 && (!handleProgressBar || standardTemplateParams.mAllowTextWithProgress)) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTextViewId, 0);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTextViewId, ensureColorSpanContrastOrStripStyling(standardTemplateParams.mText, standardTemplateParams));
                setTextViewColorSecondary(builderRemoteViews, standardTemplateParams.mTextViewId, standardTemplateParams);
                handleProgressBar = true;
            } else if (standardTemplateParams.mTextViewId != 16909931) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTextViewId, 8);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTextViewId, null);
            }
            updateExpanderAlignment(builderRemoteViews, standardTemplateParams, handleProgressBar);
            setHeaderlessVerticalMargins(builderRemoteViews, standardTemplateParams, handleProgressBar);
            if (Flags.notificationsRedesignTemplates() && !standardTemplateParams.mHeaderless) {
                builderRemoteViews.setViewLayoutMargin(R.id.notification_main_column, 1, getContentMarginTop(this.mContext, R.dimen.notification_2025_content_margin_top), 0);
            }
            setHeaderlessVerticalMargins(this.mContext, builderRemoteViews, standardTemplateParams, handleProgressBar);
            return builderRemoteViews;
        }

        private static void updateExpanderAlignment(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            if (Flags.notificationsRedesignTemplates() && standardTemplateParams.mHeaderless) {
                if (!z) {
                    remoteViews.setViewLayoutHeight(R.id.expand_button, -1.0f, 0);
                } else {
                    remoteViews.setViewLayoutHeight(R.id.expand_button, -2.0f, 0);
                }
            }
        }

        private static void setHeaderlessVerticalMargins(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            if (Flags.notificationsRedesignTemplates() || !standardTemplateParams.mHeaderless) {
                return;
            }
            int i = z ? R.dimen.notification_headerless_margin_twoline : R.dimen.notification_headerless_margin_oneline;
            remoteViews.setViewLayoutMarginDimen(R.id.notification_headerless_view_column, 1, i);
            remoteViews.setViewLayoutMarginDimen(R.id.notification_headerless_view_column, 3, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setHeaderlessVerticalMargins(Context context, RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            if (!standardTemplateParams.mHeaderless) {
                remoteViews.setViewLayoutMargin(R.id.notification_main_column, 1, getFontScaledMarginHeight(context, R.dimen.notification_content_margin_top), 0);
                return;
            }
            int i = z ? R.dimen.notification_headerless_margin_twoline : R.dimen.notification_headerless_margin_oneline;
            remoteViews.setViewLayoutMarginDimen(R.id.notification_headerless_view_column, 1, i);
            remoteViews.setViewLayoutMarginDimen(R.id.notification_headerless_view_column, 3, i);
        }

        private static int getFontScaledMarginHeight(Context context, int i) {
            return (int) (context.getResources().getDimensionPixelSize(i) * ((((context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density) - 1.0f) / 2.0f) + 1.0f));
        }

        private static int getFontScaledHeight(Context context, int i) {
            return (int) (context.getResources().getDimensionPixelSize(i) * (context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTextViewColorPrimary(RemoteViews remoteViews, int i, StandardTemplateParams standardTemplateParams) {
            remoteViews.setTextColor(i, getPrimaryTextColor(standardTemplateParams));
        }

        public int getPrimaryTextColor(StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getPrimaryTextColor();
        }

        public int getSecondaryTextColor(StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getSecondaryTextColor();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTextViewColorSecondary(RemoteViews remoteViews, int i, StandardTemplateParams standardTemplateParams) {
            remoteViews.setTextColor(i, getSecondaryTextColor(standardTemplateParams));
        }

        public int getThirdTextColor(StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getThirdTextColor();
        }

        private void setTextViewColorThird(RemoteViews remoteViews, int i, StandardTemplateParams standardTemplateParams) {
            remoteViews.setTextColor(i, getThirdTextColor(standardTemplateParams));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Colors getColors(StandardTemplateParams standardTemplateParams) {
            this.mColors.resolvePalette(this.mContext, this.mN.color, isBackgroundColorized(standardTemplateParams), this.mInNightMode);
            return this.mColors;
        }

        public Colors getColors(boolean z) {
            this.mColors.resolvePalette(this.mContext, this.mN.color, !z && this.mN.isColorized(), this.mInNightMode);
            return this.mColors;
        }

        private void updateHeaderBackgroundColor(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (Flags.uiRichOngoing()) {
                if (isBackgroundColorized(standardTemplateParams)) {
                    remoteViews.setInt(R.id.notification_header, "setBackgroundColor", getBackgroundColor(standardTemplateParams));
                } else {
                    remoteViews.setInt(R.id.notification_header, "setBackgroundResource", 0);
                }
            }
        }

        private void updateBackgroundColor(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (isBackgroundColorized(standardTemplateParams)) {
                remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", getBackgroundColor(standardTemplateParams));
            } else {
                remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundResource", 0);
            }
        }

        private boolean handleProgressBar(RemoteViews remoteViews, Bundle bundle, StandardTemplateParams standardTemplateParams) {
            int i = bundle.getInt(Notification.EXTRA_PROGRESS_MAX, 0);
            int i2 = bundle.getInt(Notification.EXTRA_PROGRESS, 0);
            boolean z = bundle.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE);
            if (!standardTemplateParams.mHideProgress && (i != 0 || z)) {
                remoteViews.setViewVisibility(16908301, 0);
                remoteViews.setProgressBar(16908301, i, i2, z);
                remoteViews.setProgressBackgroundTintList(16908301, this.mContext.getColorStateList(R.color.notification_progress_background_color));
                ColorStateList valueOf = ColorStateList.valueOf(getPrimaryAccentColor(standardTemplateParams));
                remoteViews.setProgressTintList(16908301, valueOf);
                remoteViews.setProgressIndeterminateTintList(16908301, valueOf);
                return true;
            }
            remoteViews.setViewVisibility(16908301, 8);
            return false;
        }

        private void bindLargeIconAndApplyMargin(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            if (templateBindResult == null) {
                templateBindResult = new TemplateBindResult();
            }
            bindLargeIcon(remoteViews, standardTemplateParams, templateBindResult);
            if (!standardTemplateParams.mHeaderless) {
                templateBindResult.mHeadingExtraMarginSet.applyToView(remoteViews, R.id.notification_header);
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, 16908310);
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, standardTemplateParams.mTextViewId);
                remoteViews.setInt(standardTemplateParams.mTextViewId, "setNumIndentLines", !standardTemplateParams.hasTitle() ? 1 : 0);
            }
            adjustExpandButtonPadding(remoteViews, templateBindResult.mRightIconVisible);
        }

        private void adjustExpandButtonPadding(RemoteViews remoteViews, boolean z) {
            if (Flags.notificationsRedesignTemplates()) {
                Resources resources = this.mContext.getResources();
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_2025_margin);
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_2025_expand_button_right_icon_spacing);
                if (z) {
                    dimensionPixelSize = dimensionPixelSize2;
                }
                remoteViews.setInt(R.id.expand_button, "setStartPadding", dimensionPixelSize);
            }
        }

        private void calculateRightIconDimens(Icon icon, boolean z, TemplateBindResult templateBindResult) {
            float dimension;
            Drawable loadDrawable;
            int intrinsicWidth;
            int intrinsicHeight;
            Resources resources = this.mContext.getResources();
            float f = resources.getDisplayMetrics().density;
            float dimension2 = resources.getDimension(Flags.notificationsRedesignTemplates() ? R.dimen.notification_2025_right_icon_content_margin : R.dimen.notification_right_icon_content_margin) / f;
            float dimension3 = resources.getDimension(R.dimen.notification_content_margin_end) / f;
            if (Flags.notificationsRedesignTemplates()) {
                dimension = resources.getDimension(R.dimen.notification_2025_right_icon_expanded_margin_end);
            } else {
                dimension = resources.getDimension(R.dimen.notification_header_expand_icon_size);
            }
            float f2 = (dimension / f) - dimension3;
            float dimension4 = resources.getDimension(R.dimen.notification_right_icon_size) / f;
            float min = (icon == null || (!z && this.mContext.getApplicationInfo().targetSdkVersion < 31) || (loadDrawable = icon.loadDrawable(this.mContext)) == null || (intrinsicWidth = loadDrawable.getIntrinsicWidth()) <= (intrinsicHeight = loadDrawable.getIntrinsicHeight()) || intrinsicHeight <= 0) ? dimension4 : Math.min((intrinsicWidth * dimension4) / intrinsicHeight, 1.7777778f * dimension4);
            templateBindResult.setRightIconState(icon != null, min, dimension4, min + dimension2, f2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r0v8, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r0v9 */
        private void bindLargeIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            Icon icon;
            if (this.mN.mLargeIcon == null && this.mN.largeIcon != null) {
                Notification notification = this.mN;
                notification.mLargeIcon = Icon.createWithBitmap(notification.largeIcon);
            }
            Icon icon2 = standardTemplateParams.mHideLeftIcon ? null : this.mN.mLargeIcon;
            if (standardTemplateParams.mHideRightIcon) {
                icon = null;
            } else {
                icon = standardTemplateParams.mPromotedPicture != null ? standardTemplateParams.mPromotedPicture : this.mN.mLargeIcon;
            }
            if (icon2 != icon || icon2 == null) {
                remoteViews.setImageViewIcon(R.id.left_icon, icon2);
                remoteViews.setIntTag(R.id.left_icon, R.id.tag_uses_right_icon_drawable, 0);
            } else {
                remoteViews.setIntTag(R.id.left_icon, R.id.tag_uses_right_icon_drawable, 1);
            }
            ?? r0 = standardTemplateParams.mPromotedPicture != null ? 1 : 0;
            calculateRightIconDimens(icon, r0, templateBindResult);
            if (icon != null) {
                remoteViews.setViewLayoutWidth(R.id.right_icon, templateBindResult.mRightIconWidthDp, 1);
                remoteViews.setViewLayoutHeight(R.id.right_icon, templateBindResult.mRightIconHeightDp, 1);
                remoteViews.setViewVisibility(R.id.right_icon, 0);
                if (icon.getType() == 4 || (remoteViews.getLayoutId() == getBigPictureLayoutResource() && this.mN.mLargeIcon != null && standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_EXPANDED)) {
                    int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_right_icon_size);
                    remoteViews.setInt(R.id.right_icon, "setMaxDrawableWidth", dimensionPixelSize);
                    remoteViews.setInt(R.id.right_icon, "setMaxDrawableHeight", dimensionPixelSize);
                }
                remoteViews.setImageViewIcon(R.id.right_icon, icon);
                remoteViews.setIntTag(R.id.right_icon, R.id.tag_keep_when_showing_left_icon, r0);
                processLargeLegacyIcon(icon, remoteViews, standardTemplateParams);
                return;
            }
            remoteViews.setImageViewIcon(R.id.right_icon, null);
            remoteViews.setIntTag(R.id.right_icon, R.id.tag_keep_when_showing_left_icon, 0);
        }

        private void bindNotificationHeader(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (standardTemplateParams.mViewType != StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED) {
                bindSmallIcon(remoteViews, standardTemplateParams);
            }
            boolean bindHeaderAppName = bindHeaderAppName(remoteViews, standardTemplateParams, false);
            boolean bindHeaderTextSecondary = bindHeaderAppName | bindHeaderTextSecondary(remoteViews, standardTemplateParams, bindHeaderAppName);
            boolean bindHeaderText = bindHeaderTextSecondary | bindHeaderText(remoteViews, standardTemplateParams, bindHeaderTextSecondary);
            if (!bindHeaderText) {
                bindHeaderText |= bindHeaderAppName(remoteViews, standardTemplateParams, true);
            }
            bindHeaderChronometerAndTime(remoteViews, standardTemplateParams, bindHeaderText);
            bindPhishingAlertIcon(remoteViews, standardTemplateParams);
            bindProfileBadge(remoteViews, standardTemplateParams);
            bindAlertedIcon(remoteViews, standardTemplateParams);
            bindExpandButton(remoteViews, standardTemplateParams);
            bindCloseButton(remoteViews, standardTemplateParams);
            if (standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER) {
                int fontScaledHeight = getFontScaledHeight(this.mContext, R.dimen.notification_group_icon_margin_top);
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_group_icon_shadow_margin_top);
                float f = fontScaledHeight;
                remoteViews.setViewLayoutMargin(R.id.left_icon, 1, f, 0);
                remoteViews.setViewLayoutMargin(16908294, 1, f, 0);
                remoteViews.setViewLayoutMargin(R.id.group_icon_shadow, 1, fontScaledHeight + dimensionPixelSize, 0);
            }
            this.mN.mUsesStandardHeader = true;
        }

        private void bindExpandButton(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            int backgroundColor = getBackgroundColor(standardTemplateParams);
            int flattenAlpha = Colors.flattenAlpha(getColors(standardTemplateParams).getProtectionColor(), backgroundColor);
            Colors.flattenAlpha(getPrimaryTextColor(standardTemplateParams), flattenAlpha);
            remoteViews.setInt(R.id.expand_button, "setDefaultTextColor", isBackgroundColorized(standardTemplateParams) ? getPrimaryTextColor(standardTemplateParams) : getThirdTextColor(standardTemplateParams));
            remoteViews.setInt(R.id.expand_button, "setDefaultPillColor", flattenAlpha);
            if (standardTemplateParams.mHighlightExpander) {
                flattenAlpha = Colors.flattenAlpha(getColors(standardTemplateParams).getTertiaryFixedDimAccentColor(), backgroundColor);
                Colors.flattenAlpha(getColors(standardTemplateParams).getOnTertiaryFixedAccentTextColor(), flattenAlpha);
            }
            remoteViews.setInt(R.id.expand_button, "setHighlightTextColor", getSecondaryTextColor(standardTemplateParams));
            remoteViews.setInt(R.id.expand_button, "setHighlightPillColor", flattenAlpha);
        }

        private void bindCloseButton(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            int flattenAlpha = Colors.flattenAlpha(getColors(standardTemplateParams).getProtectionColor(), getBackgroundColor(standardTemplateParams));
            remoteViews.setInt(R.id.close_button, "setForegroundColor", Colors.flattenAlpha(getPrimaryTextColor(standardTemplateParams), flattenAlpha));
            remoteViews.setInt(R.id.close_button, "setBackgroundColor", flattenAlpha);
        }

        private void bindHeaderChronometerAndTime(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            if (!standardTemplateParams.mHideTime && showsTimeOrChronometer()) {
                if (z) {
                    remoteViews.setViewVisibility(R.id.time_divider, 8);
                    setTextViewColorSecondary(remoteViews, R.id.time_divider, standardTemplateParams);
                }
                if (this.mN.extras.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER)) {
                    remoteViews.setViewVisibility(R.id.chronometer, 0);
                    remoteViews.setLong(R.id.chronometer, "setBase", this.mN.getWhen() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                    remoteViews.setBoolean(R.id.chronometer, "setStarted", true);
                    remoteViews.setChronometerCountDown(R.id.chronometer, this.mN.extras.getBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
                    setTextViewColorSecondary(remoteViews, R.id.chronometer, standardTemplateParams);
                    return;
                }
                remoteViews.setViewVisibility(R.id.time, 0);
                remoteViews.setLong(R.id.time, "setTime", this.mN.getWhen());
                setTextViewColorSecondary(remoteViews, R.id.time, standardTemplateParams);
                return;
            }
            remoteViews.setLong(R.id.time, "setTime", this.mN.getWhen() != 0 ? this.mN.getWhen() : this.mN.creationTime);
            setTextViewColorSecondary(remoteViews, R.id.time, standardTemplateParams);
        }

        private boolean bindHeaderText(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            Style style;
            if (standardTemplateParams.mHideSubText) {
                return false;
            }
            CharSequence charSequence = standardTemplateParams.mSubText;
            if (charSequence == null && (style = this.mStyle) != null && style.mSummaryTextSet && this.mStyle.hasSummaryInHeader()) {
                charSequence = this.mStyle.mSummaryText;
            }
            if (charSequence == null && this.mContext.getApplicationInfo().targetSdkVersion < 24 && this.mN.extras.getCharSequence(Notification.EXTRA_INFO_TEXT) != null) {
                charSequence = this.mN.extras.getCharSequence(Notification.EXTRA_INFO_TEXT);
            }
            if (TextUtils.isEmpty(charSequence)) {
                return false;
            }
            remoteViews.setTextViewText(R.id.header_text, ensureColorSpanContrastOrStripStyling(processLegacyText(charSequence), standardTemplateParams));
            setTextViewColorSecondary(remoteViews, R.id.header_text, standardTemplateParams);
            remoteViews.setViewVisibility(R.id.header_text, 0);
            if (z) {
                remoteViews.setViewVisibility(R.id.header_text_divider, 8);
                setTextViewColorSecondary(remoteViews, R.id.header_text_divider, standardTemplateParams);
                return true;
            }
            remoteViews.setViewLayoutMarginDimen(R.id.header_text, 4, 0);
            return true;
        }

        private boolean bindHeaderTextSecondary(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            if (standardTemplateParams.mHideSubText || TextUtils.isEmpty(standardTemplateParams.mHeaderTextSecondary)) {
                return false;
            }
            remoteViews.setTextViewText(R.id.header_text_secondary, ensureColorSpanContrastOrStripStyling(processLegacyText(standardTemplateParams.mHeaderTextSecondary), standardTemplateParams));
            setTextViewColorSecondary(remoteViews, R.id.header_text_secondary, standardTemplateParams);
            remoteViews.setViewVisibility(R.id.header_text_secondary, 0);
            if (!z) {
                return true;
            }
            remoteViews.setViewVisibility(R.id.header_text_secondary_divider, 8);
            setTextViewColorSecondary(remoteViews, R.id.header_text_secondary_divider, standardTemplateParams);
            return true;
        }

        public String loadHeaderAppName() {
            return this.mN.loadHeaderAppName(this.mContext);
        }

        private boolean bindHeaderAppName(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, boolean z) {
            if ((standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_MINIMIZED && !z) || standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_INSIGNIFICANT) {
                return false;
            }
            if (standardTemplateParams.mHeaderless && standardTemplateParams.hasTitle()) {
                return true;
            }
            if (standardTemplateParams.mHideAppName) {
                return standardTemplateParams.hasTitle();
            }
            remoteViews.setViewVisibility(R.id.app_name_text, 0);
            remoteViews.setTextViewText(R.id.app_name_text, loadHeaderAppName());
            remoteViews.setTextColor(R.id.app_name_text, getSecondaryTextColor(standardTemplateParams));
            return true;
        }

        private boolean isBackgroundColorized(StandardTemplateParams standardTemplateParams) {
            return standardTemplateParams.allowColorization && this.mN.isColorized();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isCallActionColorCustomizable() {
            return this.mN.isColorized() && this.mContext.getResources().getBoolean(R.bool.config_callNotificationActionColorsRequireColorized);
        }

        private void bindSmallIcon(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (this.mN.mSmallIcon == null && this.mN.icon != 0) {
                Notification notification = this.mN;
                notification.mSmallIcon = Icon.createWithResource(this.mContext, notification.icon);
            }
            remoteViews.setImageViewIcon(16908294, this.mN.mSmallIcon);
            remoteViews.setInt(16908294, "setImageLevel", this.mN.iconLevel);
            processSmallIconColor(this.mN.mSmallIcon, remoteViews, standardTemplateParams);
        }

        private boolean showsTimeOrChronometer() {
            return this.mN.showsTime() || this.mN.showsChronometer();
        }

        private void resetStandardTemplateWithActions(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.actions, 8);
            remoteViews.removeAllViews(R.id.actions);
            remoteViews.setViewVisibility(R.id.notification_material_reply_container, 8);
            remoteViews.setTextViewText(R.id.notification_material_reply_text_1, null);
            remoteViews.setViewVisibility(R.id.notification_material_reply_text_1_container, 8);
            remoteViews.setViewVisibility(R.id.notification_material_reply_progress, 8);
            remoteViews.setViewVisibility(R.id.notification_material_reply_text_2, 8);
            remoteViews.setTextViewText(R.id.notification_material_reply_text_2, null);
            remoteViews.setViewVisibility(R.id.notification_material_reply_text_3, 8);
            remoteViews.setTextViewText(R.id.notification_material_reply_text_3, null);
            if (Flags.notificationsRedesignTemplates()) {
                return;
            }
            remoteViews.setViewLayoutMarginDimen(R.id.notification_action_list_margin_target, 3, R.dimen.notification_content_margin);
        }

        private boolean bindSnoozeAction(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            boolean z = this.mN.isFgsOrUij() || this.mN.fullScreenIntent != null || isBackgroundColorized(standardTemplateParams) || standardTemplateParams.mViewType != StandardTemplateParams.VIEW_TYPE_EXPANDED;
            remoteViews.setBoolean(R.id.snooze_button, "setEnabled", !z);
            if (z) {
                remoteViews.setViewVisibility(R.id.snooze_button, 8);
            }
            boolean z2 = (z || this.mContext.getContentResolver() == null || !isSnoozeSettingEnabled()) ? false : true;
            if (!Flags.notificationsRedesignTemplates() && z2) {
                remoteViews.setViewLayoutMarginDimen(R.id.notification_action_list_margin_target, 3, 0);
            }
            return z2;
        }

        private boolean isSnoozeSettingEnabled() {
            return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.SHOW_NOTIFICATION_SNOOZE, 0, -2) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public List<Action> getNonContextualActions() {
            if (this.mActions == null) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<Action> it = this.mActions.iterator();
            while (it.hasNext()) {
                Action next = it.next();
                if (!this.mN.isPromotedOngoing() || !hasValidRemoteInput(next)) {
                    if (!next.isContextual()) {
                        arrayList.add(next);
                    }
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public RemoteViews applyStandardTemplateWithActions(int i, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            StandardTemplateParams standardTemplateParams2;
            RemoteViews remoteViews;
            Builder builder;
            boolean z;
            RemoteViews applyStandardTemplate = applyStandardTemplate(i, standardTemplateParams, templateBindResult);
            resetStandardTemplateWithActions(applyStandardTemplate);
            bindSnoozeAction(applyStandardTemplate, standardTemplateParams);
            ColorStateList valueOf = ColorStateList.valueOf(getStandardActionColor(standardTemplateParams));
            applyStandardTemplate.setColorStateList(R.id.snooze_button, "setImageTintList", valueOf);
            applyStandardTemplate.setColorStateList(R.id.bubble_button, "setImageTintList", valueOf);
            List<Action> nonContextualActions = getNonContextualActions();
            int min = Math.min(nonContextualActions.size(), 3);
            boolean z2 = standardTemplateParams.mCallStyleActions;
            if (standardTemplateParams.mCallStyleActions) {
                applyStandardTemplate.setViewPadding(R.id.actions, 0, 0, 0, 0);
                if (!Flags.notificationsRedesignTemplates()) {
                    applyStandardTemplate.setInt(R.id.actions, "setCollapsibleIndentDimen", R.dimen.call_notification_collapsible_indent);
                }
                if (Flags.evenlyDividedCallStyleActionLayout()) {
                    Log.d(Notification.TAG, "setting evenly divided mode on action list");
                    applyStandardTemplate.setBoolean(R.id.actions, "setEvenlyDividedMode", true);
                }
            }
            if (!Flags.notificationsRedesignTemplates()) {
                applyStandardTemplate.setBoolean(R.id.actions, "setEmphasizedMode", z2);
            }
            int i2 = Flags.notificationsRedesignTemplates() ? R.id.actions_container_layout : R.id.actions_container;
            if (min > 0 && !standardTemplateParams.mHideActions) {
                applyStandardTemplate.setViewVisibility(i2, 0);
                applyStandardTemplate.setViewVisibility(R.id.actions, 0);
                updateMarginsForActions(applyStandardTemplate, z2);
                standardTemplateParams2 = standardTemplateParams;
                builder = this;
                z = builder.populateActionsContainer(applyStandardTemplate, standardTemplateParams2, nonContextualActions, min, z2);
                remoteViews = applyStandardTemplate;
            } else {
                standardTemplateParams2 = standardTemplateParams;
                remoteViews = applyStandardTemplate;
                builder = this;
                remoteViews.setViewVisibility(i2, 8);
                z = false;
            }
            RemoteInputHistoryItem[] remoteInputHistoryItemArr = (RemoteInputHistoryItem[]) Notification.getParcelableArrayFromBundle(builder.mN.extras, Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class);
            if (z && remoteInputHistoryItemArr != null && remoteInputHistoryItemArr.length > 0 && !TextUtils.isEmpty(remoteInputHistoryItemArr[0].getText()) && standardTemplateParams2.maxRemoteInputHistory > 0) {
                boolean z3 = builder.mN.extras.getBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER);
                remoteViews.setViewVisibility(R.id.notification_material_reply_container, 0);
                remoteViews.setViewVisibility(R.id.notification_material_reply_text_1_container, 0);
                remoteViews.setTextViewText(R.id.notification_material_reply_text_1, builder.ensureColorSpanContrastOrStripStyling(remoteInputHistoryItemArr[0].getText(), standardTemplateParams2));
                builder.setTextViewColorSecondary(remoteViews, R.id.notification_material_reply_text_1, standardTemplateParams2);
                remoteViews.setViewVisibility(R.id.notification_material_reply_progress, z3 ? 0 : 8);
                remoteViews.setProgressIndeterminateTintList(R.id.notification_material_reply_progress, ColorStateList.valueOf(builder.getPrimaryAccentColor(standardTemplateParams2)));
                if (remoteInputHistoryItemArr.length > 1 && !TextUtils.isEmpty(remoteInputHistoryItemArr[1].getText()) && standardTemplateParams2.maxRemoteInputHistory > 1) {
                    remoteViews.setViewVisibility(R.id.notification_material_reply_text_2, 0);
                    remoteViews.setTextViewText(R.id.notification_material_reply_text_2, builder.ensureColorSpanContrastOrStripStyling(remoteInputHistoryItemArr[1].getText(), standardTemplateParams2));
                    builder.setTextViewColorSecondary(remoteViews, R.id.notification_material_reply_text_2, standardTemplateParams2);
                    if (remoteInputHistoryItemArr.length > 2 && !TextUtils.isEmpty(remoteInputHistoryItemArr[2].getText()) && standardTemplateParams2.maxRemoteInputHistory > 2) {
                        remoteViews.setViewVisibility(R.id.notification_material_reply_text_3, 0);
                        remoteViews.setTextViewText(R.id.notification_material_reply_text_3, builder.ensureColorSpanContrastOrStripStyling(remoteInputHistoryItemArr[2].getText(), standardTemplateParams2));
                        builder.setTextViewColorSecondary(remoteViews, R.id.notification_material_reply_text_3, standardTemplateParams2);
                    }
                }
            }
            return remoteViews;
        }

        private void updateMarginsForActions(RemoteViews remoteViews, boolean z) {
            if (!Flags.notificationsRedesignTemplates()) {
                remoteViews.setViewLayoutMarginDimen(R.id.notification_action_list_margin_target, 3, 0);
            } else if (z) {
                remoteViews.setViewLayoutMarginDimen(R.id.actions_container, 1, R.dimen.notification_2025_smart_reply_container_margin);
                remoteViews.setViewLayoutMarginDimen(R.id.actions_container, 3, R.dimen.notification_2025_smart_reply_container_margin);
            } else {
                remoteViews.setViewLayoutMarginDimen(R.id.actions_container, 1, 0);
                remoteViews.setViewLayoutMarginDimen(R.id.actions_container, 3, R.dimen.notification_2025_action_list_margin_bottom);
            }
        }

        private boolean populateActionsContainer(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, List<Action> list, int i, boolean z) {
            boolean z2 = false;
            for (int i2 = 0; i2 < i; i2++) {
                Action action = list.get(i2);
                z2 |= hasValidRemoteInput(action);
                RemoteViews generateActionButton = generateActionButton(action, z, standardTemplateParams);
                if (z && i2 > 0) {
                    generateActionButton.setViewLayoutMarginDimen(R.id.action0, 4, 0);
                }
                remoteViews.addView(R.id.actions, generateActionButton);
            }
            return z2;
        }

        public static int getContentMarginTop(Context context, int i) {
            Resources resources = context.getResources();
            return resources.getDimensionPixelSize(R.dimen.notification_2025_margin) + resources.getDimensionPixelSize(i) + resources.getDimensionPixelSize(R.dimen.notification_subtext_size);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasValidRemoteInput(Action action) {
            RemoteInput[] remoteInputs;
            if (TextUtils.isEmpty(action.title) || action.actionIntent == null || (remoteInputs = action.getRemoteInputs()) == null) {
                return false;
            }
            for (RemoteInput remoteInput : remoteInputs) {
                CharSequence[] choices = remoteInput.getChoices();
                if (remoteInput.getAllowFreeFormInput()) {
                    return true;
                }
                if (choices != null && choices.length != 0) {
                    return true;
                }
            }
            return false;
        }

        @Deprecated
        public RemoteViews createContentView() {
            RemoteViews makeContentView;
            if (useExistingRemoteView(this.mN.contentView)) {
                return fullyCustomViewRequiresDecoration(false) ? minimallyDecoratedContentView(this.mN.contentView) : this.mN.contentView;
            }
            Style style = this.mStyle;
            if (style == null || (makeContentView = style.makeContentView()) == null) {
                return applyStandardTemplate(getCollapsedBaseLayoutResource(), this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).fillTextsFrom(this), null);
            }
            return fullyCustomViewRequiresDecoration(true) ? minimallyDecoratedContentView(makeContentView) : makeContentView;
        }

        private boolean fullyCustomViewRequiresDecoration(boolean z) {
            return !(z && Notification.isPlatformStyle(this.mStyle)) && this.mContext.getApplicationInfo().targetSdkVersion >= 31;
        }

        private RemoteViews minimallyDecoratedContentView(RemoteViews remoteViews) {
            StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).decorationType(1).fillTextsFrom(this);
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews applyStandardTemplate = applyStandardTemplate(getCollapsedBaseLayoutResource(), fillTextsFrom, templateBindResult);
            Notification.buildCustomContentIntoTemplate(this.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }

        private RemoteViews minimallyDecoratedExpandedContentView(RemoteViews remoteViews) {
            StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).decorationType(1).fillTextsFrom(this);
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews applyStandardTemplateWithActions = applyStandardTemplateWithActions(getExpandedBaseLayoutResource(), fillTextsFrom, templateBindResult);
            Notification.buildCustomContentIntoTemplate(this.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            makeHeaderExpanded(applyStandardTemplateWithActions);
            return applyStandardTemplateWithActions;
        }

        private RemoteViews minimallyDecoratedHeadsUpContentView(RemoteViews remoteViews) {
            StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).decorationType(1).fillTextsFrom(this);
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews applyStandardTemplateWithActions = applyStandardTemplateWithActions(getHeadsUpBaseLayoutResource(), fillTextsFrom, templateBindResult);
            Notification.buildCustomContentIntoTemplate(this.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplateWithActions;
        }

        private boolean useExistingRemoteView(RemoteViews remoteViews) {
            if (remoteViews == null || styleDisplaysCustomViewInline()) {
                return false;
            }
            if (!fullyCustomViewRequiresDecoration(false) || !Notification.isStandardLayout(remoteViews.getLayoutId())) {
                return true;
            }
            Log.w(Notification.TAG, "For apps targeting S, a custom content view that is a modified version of any standard layout is disallowed.");
            return false;
        }

        @Deprecated
        public RemoteViews createBigContentView() {
            return createExpandedContentView();
        }

        private RemoteViews createExpandedContentView() {
            RemoteViews remoteViews;
            if (useExistingRemoteView(this.mN.bigContentView)) {
                if (fullyCustomViewRequiresDecoration(false)) {
                    return minimallyDecoratedExpandedContentView(this.mN.bigContentView);
                }
                return this.mN.bigContentView;
            }
            Style style = this.mStyle;
            if (style != null) {
                remoteViews = style.makeExpandedContentView();
                if (fullyCustomViewRequiresDecoration(true)) {
                    remoteViews = minimallyDecoratedExpandedContentView(remoteViews);
                }
            } else {
                remoteViews = null;
            }
            if (remoteViews == null && expandedContentViewRequired()) {
                remoteViews = applyStandardTemplateWithActions(getExpandedBaseLayoutResource(), this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).allowTextWithProgress(true).fillTextsFrom(this), null);
            }
            makeHeaderExpanded(remoteViews);
            return remoteViews;
        }

        private boolean expandedContentViewRequired() {
            boolean z = false;
            if (Flags.notificationExpansionOptional()) {
                if (this.mN.bigContentView == null && this.mStyle == null && this.mActions.size() == 0) {
                    z = true;
                }
                return !z;
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 31) {
                return true;
            }
            if (this.mN.contentView != null && this.mN.bigContentView == null && this.mStyle == null && this.mActions.size() == 0) {
                z = true;
            }
            return !z;
        }

        public RemoteViews makeNotificationGroupHeader() {
            return makeNotificationHeader(this.mParams.reset().disallowColorization().viewType(StandardTemplateParams.VIEW_TYPE_GROUP_HEADER).fillTextsFrom(this));
        }

        public RemoteViews makeNotificationGroupHeaderExpanded() {
            return makeNotificationHeader(this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED).fillTextsFrom(this));
        }

        private RemoteViews makeNotificationHeader(StandardTemplateParams standardTemplateParams) {
            BuilderRemoteViews builderRemoteViews;
            if (standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER) {
                builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), R.layout.notification_template_group_header);
            } else if (standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED) {
                builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), R.layout.notification_template_group_header_expanded);
            } else if (standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_MINIMIZED || standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_PUBLIC) {
                builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), R.layout.notification_template_header_minimized);
            } else if (standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_INSIGNIFICANT) {
                builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), R.layout.notification_template_header_insignificant);
            } else {
                builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), R.layout.notification_template_header);
            }
            resetNotificationHeader(builderRemoteViews);
            bindNotificationHeader(builderRemoteViews, standardTemplateParams);
            updateHeaderBackgroundColor(builderRemoteViews, standardTemplateParams);
            if (Flags.notificationsRedesignTemplates() && (standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_MINIMIZED || standardTemplateParams.mViewType == StandardTemplateParams.VIEW_TYPE_PUBLIC)) {
                builderRemoteViews.setBoolean(R.id.notification_header, "centerTopLine", true);
            }
            return builderRemoteViews;
        }

        public static void makeHeaderExpanded(RemoteViews remoteViews) {
            if (remoteViews != null) {
                remoteViews.setBoolean(R.id.expand_button, "setExpanded", true);
            }
        }

        public RemoteViews createCompactHeadsUpContentView() {
            RemoteViews makeCompactHeadsUpContentView;
            if (this.mN.fullScreenIntent != null) {
                return createHeadsUpContentView();
            }
            Style style = this.mStyle;
            if (style != null && (makeCompactHeadsUpContentView = style.makeCompactHeadsUpContentView()) != null) {
                return makeCompactHeadsUpContentView;
            }
            StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this);
            fillTextsFrom.headerTextSecondary(fillTextsFrom.mText).text(null).hideTime(true).summaryText("");
            return applyStandardTemplate(getCompactHeadsUpBaseLayoutResource(), fillTextsFrom, null);
        }

        @Deprecated
        public RemoteViews createHeadsUpContentView() {
            if (useExistingRemoteView(this.mN.headsUpContentView)) {
                if (fullyCustomViewRequiresDecoration(false)) {
                    return minimallyDecoratedHeadsUpContentView(this.mN.headsUpContentView);
                }
                return this.mN.headsUpContentView;
            }
            Style style = this.mStyle;
            if (style != null) {
                RemoteViews makeHeadsUpContentView = style.makeHeadsUpContentView();
                if (makeHeadsUpContentView != null) {
                    return fullyCustomViewRequiresDecoration(true) ? minimallyDecoratedHeadsUpContentView(makeHeadsUpContentView) : makeHeadsUpContentView;
                }
            } else if (this.mActions.size() == 0) {
                return null;
            }
            return applyStandardTemplateWithActions(getHeadsUpBaseLayoutResource(), this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this).setMaxRemoteInputHistory(1), null);
        }

        public RemoteViews makePublicContentView(boolean z) {
            return makePublicContentView(z, false);
        }

        public RemoteViews makePublicContentView(boolean z, boolean z2) {
            if (this.mN.publicVersion != null && !z2) {
                Builder recoverBuilder = recoverBuilder(this.mContext, this.mN.publicVersion);
                Style style = this.mStyle;
                if (style instanceof MessagingStyle) {
                    MessagingStyle messagingStyle = (MessagingStyle) style;
                    Style style2 = recoverBuilder.mStyle;
                    if (style2 instanceof MessagingStyle) {
                        ((MessagingStyle) style2).mConversationType = messagingStyle.mConversationType;
                    }
                }
                return recoverBuilder.createContentView();
            }
            Bundle bundle = this.mN.extras;
            Style style3 = this.mStyle;
            this.mStyle = null;
            Icon icon = this.mN.mLargeIcon;
            this.mN.mLargeIcon = null;
            Bitmap bitmap = this.mN.largeIcon;
            this.mN.largeIcon = null;
            ArrayList<Action> arrayList = this.mActions;
            this.mActions = new ArrayList<>();
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean(Notification.EXTRA_SHOW_WHEN, bundle.getBoolean(Notification.EXTRA_SHOW_WHEN));
            bundle2.putBoolean(Notification.EXTRA_SHOW_CHRONOMETER, bundle.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER));
            bundle2.putBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN, bundle.getBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
            if (this.mN.isPromotedOngoing()) {
                bundle2.putBoolean(Notification.EXTRA_COLORIZED, bundle.getBoolean(Notification.EXTRA_COLORIZED));
            }
            String string = bundle.getString(Notification.EXTRA_SUBSTITUTE_APP_NAME);
            if (string != null) {
                bundle2.putString(Notification.EXTRA_SUBSTITUTE_APP_NAME, string);
            }
            this.mN.extras = bundle2;
            StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_PUBLIC).fillTextsFrom(this);
            if (z) {
                fillTextsFrom.highlightExpander(false);
            }
            if (!this.mN.isPromotedOngoing()) {
                fillTextsFrom.disallowColorization();
            }
            RemoteViews makeNotificationHeader = makeNotificationHeader(fillTextsFrom);
            makeNotificationHeader.setBoolean(R.id.notification_header, "setExpandOnlyOnButton", true);
            makeNotificationHeader.setBoolean(R.id.notification_header, "styleTextAsTitle", true);
            this.mN.extras = bundle;
            this.mN.mLargeIcon = icon;
            this.mN.largeIcon = bitmap;
            this.mActions = arrayList;
            this.mStyle = style3;
            return makeNotificationHeader;
        }

        public RemoteViews makeLowPriorityContentView(boolean z) {
            StandardTemplateParams fillTextsFrom = this.mParams.reset().disallowColorization().viewType(StandardTemplateParams.VIEW_TYPE_MINIMIZED).highlightExpander(false).fillTextsFrom(this);
            if (!z || TextUtils.isEmpty(fillTextsFrom.mSubText)) {
                fillTextsFrom.summaryText(createSummaryText());
            }
            RemoteViews makeNotificationHeader = makeNotificationHeader(fillTextsFrom);
            makeNotificationHeader.setBoolean(R.id.notification_header, "setAcceptAllTouches", true);
            makeNotificationHeader.setBoolean(R.id.notification_header, "styleTextAsTitle", true);
            return makeNotificationHeader;
        }

        public RemoteViews makeInsignificantView(boolean z) {
            StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_INSIGNIFICANT).highlightExpander(false).fillTextsFrom(this);
            if (!z || TextUtils.isEmpty(fillTextsFrom.mSubText)) {
                fillTextsFrom.summaryText(createSummaryText());
            }
            RemoteViews makeNotificationHeader = makeNotificationHeader(fillTextsFrom);
            makeNotificationHeader.setBoolean(R.id.notification_header, "setAcceptAllTouches", true);
            makeNotificationHeader.setBoolean(R.id.notification_header, "styleTextAsTitle", true);
            makeNotificationHeader.setTextViewText(fillTextsFrom.mTextViewId, ensureColorSpanContrastOrStripStyling(fillTextsFrom.mText, fillTextsFrom));
            setTextViewColorSecondary(makeNotificationHeader, fillTextsFrom.mTextViewId, fillTextsFrom);
            makeNotificationHeader.setViewVisibility(R.id.text, 0);
            return makeNotificationHeader;
        }

        private CharSequence createSummaryText() {
            CharSequence charSequence = this.mN.extras.getCharSequence(Notification.EXTRA_TITLE);
            if (USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY) {
                return charSequence;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            if (charSequence == null) {
                charSequence = this.mN.extras.getCharSequence(Notification.EXTRA_TITLE_BIG);
            }
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            if (charSequence != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence));
            }
            CharSequence charSequence2 = this.mN.extras.getCharSequence(Notification.EXTRA_TEXT);
            if (charSequence != null && charSequence2 != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(this.mContext.getText(R.string.notification_header_divider_symbol_with_spaces)));
            }
            if (charSequence2 != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence2));
            }
            return spannableStringBuilder;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public RemoteViews generateActionButton(Action action, boolean z, StandardTemplateParams standardTemplateParams) {
            Integer fullLengthSpanColor;
            boolean z2 = action.actionIntent == null;
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews(this.mContext.getApplicationInfo(), getActionButtonLayoutResource(z, z2));
            if (!z2) {
                builderRemoteViews.setOnClickPendingIntent(R.id.action0, action.actionIntent);
            }
            builderRemoteViews.setContentDescription(R.id.action0, action.title);
            if (action.mRemoteInputs != null) {
                builderRemoteViews.setRemoteInputs(R.id.action0, action.mRemoteInputs);
            }
            if (z) {
                CharSequence charSequence = action.title;
                int secondaryAccentColor = getColors(standardTemplateParams).getSecondaryAccentColor();
                if (z2) {
                    Context context = this.mContext;
                    secondaryAccentColor = setAlphaComponentByFloatDimen(context, ContrastColorUtil.resolveSecondaryColor(context, getColors(standardTemplateParams).getBackgroundColor(), this.mInNightMode), R.dimen.notification_action_disabled_container_alpha);
                }
                if (Flags.cleanUpSpansAndNewLines()) {
                    if (!isLegacy() && (fullLengthSpanColor = getFullLengthSpanColor(charSequence)) != null) {
                        secondaryAccentColor = ensureButtonFillContrast(fullLengthSpanColor.intValue(), getColors(standardTemplateParams).getBackgroundColor());
                    }
                } else if (isLegacy()) {
                    charSequence = ContrastColorUtil.clearColorSpans(charSequence);
                } else {
                    Integer fullLengthSpanColor2 = getFullLengthSpanColor(charSequence);
                    if (fullLengthSpanColor2 != null) {
                        secondaryAccentColor = ensureButtonFillContrast(fullLengthSpanColor2.intValue(), getColors(standardTemplateParams).getBackgroundColor());
                    }
                    charSequence = ContrastColorUtil.ensureColorSpanContrast(charSequence, secondaryAccentColor);
                }
                CharSequence ensureColorSpanContrastOrStripStyling = ensureColorSpanContrastOrStripStyling(charSequence, standardTemplateParams);
                if (standardTemplateParams.mCallStyleActions && Flags.evenlyDividedCallStyleActionLayout()) {
                    Log.d(Notification.TAG, "new action layout enabled, gluing instead of setting text");
                    builderRemoteViews.setCharSequence(R.id.action0, "glueLabel", ensureColorSpanContrastOrStripStyling);
                } else {
                    builderRemoteViews.setTextViewText(R.id.action0, ensureColorSpanContrastOrStripStyling);
                }
                int resolvePrimaryColor = ContrastColorUtil.resolvePrimaryColor(this.mContext, secondaryAccentColor, this.mInNightMode);
                if (z2) {
                    Context context2 = this.mContext;
                    resolvePrimaryColor = setAlphaComponentByFloatDimen(context2, ContrastColorUtil.resolveSecondaryColor(context2, getColors(standardTemplateParams).getBackgroundColor(), this.mInNightMode), R.dimen.notification_action_disabled_content_alpha);
                }
                builderRemoteViews.setTextColor(R.id.action0, resolvePrimaryColor);
                builderRemoteViews.setColorStateList(R.id.action0, "setRippleColor", ColorStateList.valueOf((resolvePrimaryColor & 16777215) | Enums.AUDIO_FORMAT_DTS_UHD_P2));
                builderRemoteViews.setColorStateList(R.id.action0, "setButtonBackground", ColorStateList.valueOf(secondaryAccentColor));
                if (standardTemplateParams.mCallStyleActions) {
                    boolean z3 = action.getExtras().getBoolean("key_action_priority");
                    builderRemoteViews.setBoolean(R.id.action0, "setIsPriority", z3);
                    builderRemoteViews.setIntDimen(R.id.action0, "setMinimumWidth", z3 ? R.dimen.call_notification_system_action_min_width : 0);
                }
            } else {
                builderRemoteViews.setTextViewText(R.id.action0, ensureColorSpanContrastOrStripStyling(action.title, standardTemplateParams));
                builderRemoteViews.setTextColor(R.id.action0, getStandardActionColor(standardTemplateParams));
            }
            int indexOf = this.mActions.indexOf(action);
            if (indexOf != -1) {
                builderRemoteViews.setIntTag(R.id.action0, R.id.notification_action_index_tag, indexOf);
            }
            return builderRemoteViews;
        }

        private int getActionButtonLayoutResource(boolean z, boolean z2) {
            if (z) {
                if (z2) {
                    return getEmphasizedTombstoneActionLayoutResource();
                }
                return getEmphasizedActionLayoutResource();
            }
            if (z2) {
                return getActionTombstoneLayoutResource();
            }
            return getActionLayoutResource();
        }

        private static int setAlphaComponentByFloatDimen(Context context, int i, int i2) {
            TypedValue typedValue = new TypedValue();
            context.getResources().getValue(i2, typedValue, true);
            return ColorUtils.setAlphaComponent(i, Math.round(typedValue.getFloat() * 255.0f));
        }

        public static Integer getFullLengthSpanColor(CharSequence charSequence) {
            Integer num = null;
            if (charSequence instanceof Spanned) {
                Spanned spanned = (Spanned) charSequence;
                for (Object obj : spanned.getSpans(0, spanned.length(), Object.class)) {
                    if (spanned.getSpanEnd(obj) - spanned.getSpanStart(obj) == charSequence.length()) {
                        if (obj instanceof TextAppearanceSpan) {
                            ColorStateList textColor = ((TextAppearanceSpan) obj).getTextColor();
                            if (textColor != null) {
                                num = Integer.valueOf(textColor.getDefaultColor());
                            }
                        } else if (obj instanceof ForegroundColorSpan) {
                            num = Integer.valueOf(((ForegroundColorSpan) obj).getForegroundColor());
                        }
                    }
                }
            }
            return num;
        }

        public CharSequence ensureColorSpanContrastOrStripStyling(CharSequence charSequence, StandardTemplateParams standardTemplateParams) {
            return ensureColorSpanContrastOrStripStyling(charSequence, getBackgroundColor(standardTemplateParams));
        }

        public CharSequence ensureColorSpanContrastOrStripStyling(CharSequence charSequence, int i) {
            if (!this.mN.isPromotedOngoing() && Flags.cleanUpSpansAndNewLines()) {
                return Notification.stripStyling(charSequence);
            }
            return ContrastColorUtil.ensureColorSpanContrast(charSequence, i);
        }

        public CharSequence ensureColorSpanContrast(CharSequence charSequence, StandardTemplateParams standardTemplateParams) {
            return ContrastColorUtil.ensureColorSpanContrast(charSequence, getBackgroundColor(standardTemplateParams));
        }

        public static boolean isColorDark(int i) {
            return ContrastColorUtil.calculateLuminance(i) <= 0.17912878474d;
        }

        public static int ensureButtonFillContrast(int i, int i2) {
            return ensureColorContrast(i, i2, 1.3d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int ensureColorContrast(int i, int i2, double d) {
            if (isColorDark(i2)) {
                return ContrastColorUtil.findContrastColorAgainstDark(i, i2, true, d);
            }
            return ContrastColorUtil.findContrastColor(i, i2, true, d);
        }

        private boolean isLegacy() {
            if (!this.mIsLegacyInitialized) {
                this.mIsLegacy = this.mContext.getApplicationInfo().targetSdkVersion < 21;
                this.mIsLegacyInitialized = true;
            }
            return this.mIsLegacy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence processLegacyText(CharSequence charSequence) {
            return (isLegacy() || textColorsNeedInversion()) ? getColorUtil().invertCharSequenceColors(charSequence) : charSequence;
        }

        private void processSmallIconColor(Icon icon, RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            boolean isGrayscaleIcon = getColorUtil().isGrayscaleIcon(this.mContext, icon);
            int smallIconColor = getSmallIconColor(standardTemplateParams);
            if (standardTemplateParams.mViewType != StandardTemplateParams.VIEW_TYPE_INSIGNIFICANT) {
                remoteViews.setInt(16908294, "setBackgroundColor", getBackgroundColor(standardTemplateParams));
            }
            if (!isGrayscaleIcon) {
                smallIconColor = 1;
            }
            remoteViews.setInt(16908294, "setOriginalIconColor", smallIconColor);
            remoteViews.setBoolean(16908294, "updateColorizedIconTint", isBackgroundColorized(standardTemplateParams));
        }

        private void processLargeLegacyIcon(Icon icon, RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            if (icon != null && isLegacy() && getColorUtil().isGrayscaleIcon(this.mContext, icon)) {
                remoteViews.setInt(16908294, "setOriginalIconColor", getSmallIconColor(standardTemplateParams));
            }
        }

        private void sanitizeColor() {
            if (this.mN.color != 0) {
                this.mN.color |= -16777216;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getStandardActionColor(StandardTemplateParams standardTemplateParams) {
            return (this.mTintActionButtons || isBackgroundColorized(standardTemplateParams)) ? getPrimaryAccentColor(standardTemplateParams) : getSecondaryTextColor(standardTemplateParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getSmallIconColor(StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getContrastColor();
        }

        public int getSmallIconColor(boolean z) {
            return getColors(z).getContrastColor();
        }

        public int getBackgroundColor(boolean z) {
            return getColors(z).getBackgroundColor();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getPrimaryAccentColor(StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getPrimaryAccentColor();
        }

        public Notification buildUnstyled() {
            if (this.mActions.size() > 0) {
                this.mN.actions = new Action[this.mActions.size()];
                this.mActions.toArray(this.mN.actions);
            }
            if (!this.mPersonList.isEmpty()) {
                this.mN.extras.putParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, this.mPersonList);
            }
            if (this.mN.bigContentView != null || this.mN.contentView != null || this.mN.headsUpContentView != null) {
                this.mN.extras.putBoolean(Notification.EXTRA_CONTAINS_CUSTOM_VIEW, true);
            }
            return this.mN;
        }

        public static Builder recoverBuilder(Context context, Notification notification) {
            Trace.beginSection("Notification.Builder#recoverBuilder");
            try {
                ApplicationInfo applicationInfo = (ApplicationInfo) notification.extras.getParcelable(Notification.EXTRA_BUILDER_APPLICATION_INFO, ApplicationInfo.class);
                if (applicationInfo != null) {
                    try {
                        context = context.createApplicationContext(applicationInfo, 4);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.e(Notification.TAG, "ApplicationInfo " + applicationInfo + " not found");
                    }
                }
                return new Builder(context, notification);
            } finally {
                Trace.endSection();
            }
        }

        public Builder setAllowSystemGeneratedContextualActions(boolean z) {
            this.mN.mAllowSystemGeneratedContextualActions = z;
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            if (this.mN.mShortcutId != null && this.mN.mBubbleMetadata != null && this.mN.mBubbleMetadata.getShortcutId() != null && !this.mN.mShortcutId.equals(this.mN.mBubbleMetadata.getShortcutId())) {
                throw new IllegalArgumentException("Notification and BubbleMetadata shortcut id's don't match, notification: " + this.mN.mShortcutId + " vs bubble: " + this.mN.mBubbleMetadata.getShortcutId());
            }
            Bundle bundle = this.mUserExtras;
            if (bundle != null) {
                this.mN.extras.putAll((Bundle) bundle.clone());
            }
            if (!Flags.sortSectionByTime()) {
                this.mN.creationTime = System.currentTimeMillis();
            }
            Notification.addFieldsFromContext(this.mContext, this.mN);
            buildUnstyled();
            Style style = this.mStyle;
            if (style != null) {
                style.reduceImageSizes(this.mContext);
                this.mStyle.purgeResources();
                this.mStyle.validate(this.mContext);
                this.mStyle.buildStyled(this.mN);
            }
            this.mN.reduceImageSizes(this.mContext);
            if (this.mContext.getApplicationInfo().targetSdkVersion < 24 && !styleDisplaysCustomViewInline()) {
                RemoteViews remoteViews = this.mN.contentView;
                RemoteViews remoteViews2 = this.mN.bigContentView;
                RemoteViews remoteViews3 = this.mN.headsUpContentView;
                if (remoteViews == null) {
                    remoteViews = createContentView();
                    this.mN.extras.putInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, remoteViews.getSequenceNumber());
                }
                if (remoteViews2 == null && (remoteViews2 = createBigContentView()) != null) {
                    this.mN.extras.putInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, remoteViews2.getSequenceNumber());
                }
                if (remoteViews3 == null && (remoteViews3 = createHeadsUpContentView()) != null) {
                    this.mN.extras.putInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, remoteViews3.getSequenceNumber());
                }
                this.mN.contentView = remoteViews;
                this.mN.bigContentView = remoteViews2;
                this.mN.headsUpContentView = remoteViews3;
            }
            if ((this.mN.defaults & 4) != 0) {
                this.mN.flags |= 1;
            }
            this.mN.allPendingIntents = null;
            return this.mN;
        }

        private boolean styleDisplaysCustomViewInline() {
            Style style = this.mStyle;
            return style != null && style.displayCustomViewInline();
        }

        public Notification buildInto(Notification notification) {
            build().cloneInto(notification, true);
            return notification;
        }

        public static Notification maybeCloneStrippedForDelivery(Notification notification) {
            String string = notification.extras.getString(Notification.EXTRA_TEMPLATE);
            if (TextUtils.isEmpty(string) || Notification.getNotificationStyleClass(string) != null) {
                boolean z = (notification.contentView instanceof BuilderRemoteViews) && notification.extras.getInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, -1) == notification.contentView.getSequenceNumber();
                boolean z2 = (notification.bigContentView instanceof BuilderRemoteViews) && notification.extras.getInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, -1) == notification.bigContentView.getSequenceNumber();
                boolean z3 = (notification.headsUpContentView instanceof BuilderRemoteViews) && notification.extras.getInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, -1) == notification.headsUpContentView.getSequenceNumber();
                if (z || z2 || z3) {
                    Notification m421clone = notification.m421clone();
                    if (z) {
                        m421clone.contentView = null;
                        m421clone.extras.remove(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT);
                    }
                    if (z2) {
                        m421clone.bigContentView = null;
                        m421clone.extras.remove(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT);
                    }
                    if (z3) {
                        m421clone.headsUpContentView = null;
                        m421clone.extras.remove(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT);
                    }
                    return m421clone;
                }
            }
            return notification;
        }

        private int getHeaderLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_header : R.layout.notification_template_header;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCollapsedBaseLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_collapsed_base : R.layout.notification_template_material_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getHeadsUpBaseLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_heads_up_base : R.layout.notification_template_material_heads_up_base;
        }

        private int getCompactHeadsUpBaseLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_compact_heads_up_base : R.layout.notification_template_material_compact_heads_up_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getMessagingCompactHeadsUpLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_compact_heads_up_messaging : R.layout.notification_template_material_messaging_compact_heads_up;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getExpandedBaseLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_base : R.layout.notification_template_material_big_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigPictureLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_big_picture : R.layout.notification_template_material_big_picture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigTextLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_big_text : R.layout.notification_template_material_big_text;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getInboxLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_inbox : R.layout.notification_template_material_inbox;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCollapsedMessagingLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_collapsed_messaging : R.layout.notification_template_material_messaging;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getExpandedMessagingLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_messaging : R.layout.notification_template_material_big_messaging;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCollapsedMediaLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_collapsed_media : R.layout.notification_template_material_media;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getExpandedMediaLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_media : R.layout.notification_template_material_big_media;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getCollapsedCallLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_collapsed_call : R.layout.notification_template_material_call;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getExpandedCallLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_call : R.layout.notification_template_material_big_call;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getProgressLayoutResource() {
            return Flags.notificationsRedesignTemplates() ? R.layout.notification_2025_template_expanded_progress : R.layout.notification_template_material_progress;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBackgroundColor(StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getBackgroundColor();
        }

        private boolean textColorsNeedInversion() {
            int i;
            Style style = this.mStyle;
            return style != null && MediaStyle.class.equals(style.getClass()) && (i = this.mContext.getApplicationInfo().targetSdkVersion) > 23 && i < 26;
        }

        public CharSequence getHeadsUpStatusBarText(boolean z) {
            Style style = this.mStyle;
            if (style != null && !z) {
                CharSequence headsUpStatusBarText = style.getHeadsUpStatusBarText();
                if (!TextUtils.isEmpty(headsUpStatusBarText)) {
                    return headsUpStatusBarText;
                }
            }
            return loadHeaderAppName();
        }

        public boolean usesTemplate() {
            return (this.mN.contentView == null && this.mN.headsUpContentView == null && this.mN.bigContentView == null) || styleDisplaysCustomViewInline();
        }
    }

    void reduceImageSizes(Context context) {
        if (this.extras.getBoolean(EXTRA_REDUCED_IMAGES)) {
            return;
        }
        boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
        Icon icon = this.mSmallIcon;
        if (icon != null && (icon.getType() == 1 || this.mSmallIcon.getType() == 5)) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_small_icon_size_low_ram : R.dimen.notification_small_icon_size);
            this.mSmallIcon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
        }
        if (this.mLargeIcon != null || this.largeIcon != null) {
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_right_icon_size_low_ram : R.dimen.notification_right_icon_size);
            Icon icon2 = this.mLargeIcon;
            if (icon2 != null) {
                icon2.scaleDownIfNecessary(dimensionPixelSize2, dimensionPixelSize2);
            }
            Bitmap bitmap = this.largeIcon;
            if (bitmap != null) {
                this.largeIcon = Icon.scaleDownIfNecessary(bitmap, dimensionPixelSize2, dimensionPixelSize2);
            }
        }
        reduceImageSizesForRemoteView(this.contentView, context, isLowRamDeviceStatic);
        reduceImageSizesForRemoteView(this.headsUpContentView, context, isLowRamDeviceStatic);
        reduceImageSizesForRemoteView(this.bigContentView, context, isLowRamDeviceStatic);
        this.extras.putBoolean(EXTRA_REDUCED_IMAGES, true);
    }

    private void reduceImageSizesForRemoteView(RemoteViews remoteViews, Context context, boolean z) {
        if (remoteViews != null) {
            Resources resources = context.getResources();
            remoteViews.reduceImageSizes(resources.getDimensionPixelSize(z ? R.dimen.notification_custom_view_max_image_width_low_ram : R.dimen.notification_custom_view_max_image_width), resources.getDimensionPixelSize(z ? R.dimen.notification_custom_view_max_image_height_low_ram : R.dimen.notification_custom_view_max_image_height));
        }
    }

    public boolean isForegroundService() {
        return (this.flags & 64) != 0;
    }

    public boolean isUserInitiatedJob() {
        return (this.flags & 32768) != 0;
    }

    public boolean isFgsOrUij() {
        return isForegroundService() || isUserInitiatedJob();
    }

    public boolean shouldShowForegroundImmediately() {
        Action[] actionArr;
        int i = this.mFgsDeferBehavior;
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return false;
        }
        return isMediaNotification() || "call".equals(this.category) || "navigation".equals(this.category) || ((actionArr = this.actions) != null && actionArr.length > 0);
    }

    public boolean isForegroundDisplayForceDeferred() {
        return 2 == this.mFgsDeferBehavior;
    }

    public Class<? extends Style> getNotificationStyle() {
        String string = this.extras.getString(EXTRA_TEMPLATE);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return getNotificationStyleClass(string);
    }

    public boolean isStyle(Class<? extends Style> cls) {
        return Objects.equals(this.extras.getString(EXTRA_TEMPLATE), cls.getName());
    }

    public boolean isColorized() {
        if (isColorizedRequested()) {
            return hasColorizedPermission() || isFgsOrUij() || isPromotedOngoing();
        }
        return false;
    }

    private boolean isColorizedRequested() {
        return this.extras.getBoolean(EXTRA_COLORIZED);
    }

    public boolean hasColorizedPermission() {
        return (this.flags & 2048) != 0;
    }

    public boolean isPromotedOngoing() {
        return Flags.uiRichOngoing() && (this.flags & 262144) != 0;
    }

    public boolean isRequestPromotedOngoing() {
        return this.extras.getBoolean(EXTRA_REQUEST_PROMOTED_ONGOING, false);
    }

    public boolean isMediaNotification() {
        Class<? extends Style> notificationStyle = getNotificationStyle();
        return (MediaStyle.class.equals(notificationStyle) || DecoratedMediaCustomViewStyle.class.equals(notificationStyle)) && (this.extras.getParcelable(EXTRA_MEDIA_SESSION, MediaSession.Token.class) != null);
    }

    public Boolean isCustomNotification() {
        if (this.contentView == null && this.bigContentView == null && this.headsUpContentView == null) {
            return false;
        }
        return true;
    }

    public boolean isBubbleNotification() {
        return (this.flags & 4096) != 0;
    }

    private boolean hasLargeIcon() {
        return (this.mLargeIcon == null && this.largeIcon == null) ? false : true;
    }

    public long getWhen() {
        if (Flags.sortSectionByTime() && this.when == 0) {
            return this.creationTime;
        }
        return this.when;
    }

    public boolean showsTime() {
        if (Flags.sortSectionByTime()) {
            return this.extras.getBoolean(EXTRA_SHOW_WHEN);
        }
        return this.when != 0 && this.extras.getBoolean(EXTRA_SHOW_WHEN);
    }

    public boolean showsChronometer() {
        if (Flags.sortSectionByTime()) {
            return this.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
        }
        return this.when != 0 && this.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
    }

    public boolean hasImage() {
        Bundle bundle;
        if (!isStyle(MessagingStyle.class) || (bundle = this.extras) == null) {
            return hasLargeIcon() || this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI);
        }
        Parcelable[] parcelableArr = (Parcelable[]) bundle.getParcelableArray(EXTRA_MESSAGES, Parcelable.class);
        if (ArrayUtils.isEmpty(parcelableArr)) {
            return false;
        }
        for (MessagingStyle.Message message : MessagingStyle.Message.getMessagesFromBundleArray(parcelableArr)) {
            if (message.getDataUri() != null && message.getDataMimeType() != null && message.getDataMimeType().startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                return true;
            }
        }
        return false;
    }

    @SystemApi
    public static Class<? extends Style> getNotificationStyleClass(String str) {
        for (Class<? extends Style> cls : PLATFORM_STYLE_CLASSES) {
            if (str.equals(cls.getName())) {
                return cls;
            }
        }
        if (Flags.apiRichOngoing() && str.equals(ProgressStyle.class.getName())) {
            return ProgressStyle.class;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void buildCustomContentIntoTemplate(Context context, RemoteViews remoteViews, RemoteViews remoteViews2, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
        int i;
        if (remoteViews2 != null) {
            RemoteViews mo465clone = remoteViews2.mo465clone();
            if (standardTemplateParams.mHeaderless) {
                remoteViews.removeFromParent(R.id.notification_top_line);
                Builder.setHeaderlessVerticalMargins(context, remoteViews, standardTemplateParams, true);
            } else {
                Resources resources = context.getResources();
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, R.id.notification_main_column, resources.getDimension(R.dimen.notification_content_margin_end) / resources.getDisplayMetrics().density);
            }
            remoteViews.removeAllViewsExceptId(R.id.notification_main_column, 16908301);
            i = 0;
            remoteViews.addView(R.id.notification_main_column, mo465clone, 0);
            remoteViews.addFlags(1);
        } else {
            i = -1;
        }
        remoteViews.setIntTag(R.id.notification_main_column, R.id.notification_custom_view_index_tag, i);
    }

    public static abstract class Style {
        static final int MAX_REMOTE_INPUT_HISTORY_LINES = 3;
        private CharSequence mBigContentTitle;
        protected Builder mBuilder;
        protected CharSequence mSummaryText = null;
        protected boolean mSummaryTextSet = false;

        public abstract boolean areNotificationsVisiblyDifferent(Style style);

        public boolean displayCustomViewInline() {
            return false;
        }

        public CharSequence getHeadsUpStatusBarText() {
            return null;
        }

        public boolean hasSummaryInHeader() {
            return true;
        }

        public RemoteViews makeCompactHeadsUpContentView() {
            return null;
        }

        public RemoteViews makeContentView() {
            return null;
        }

        public RemoteViews makeExpandedContentView() {
            return null;
        }

        public RemoteViews makeHeadsUpContentView() {
            return null;
        }

        public void purgeResources() {
        }

        public void reduceImageSizes(Context context) {
        }

        public void validate(Context context) {
        }

        @Deprecated
        public Style() {
        }

        protected void internalSetBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = charSequence;
        }

        protected void internalSetSummaryText(CharSequence charSequence) {
            this.mSummaryText = charSequence;
            this.mSummaryTextSet = true;
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (builder != null) {
                    builder.setStyle(this);
                }
            }
        }

        protected void checkBuilder() {
            if (this.mBuilder == null) {
                throw new IllegalArgumentException("Style requires a valid Builder object");
            }
        }

        protected RemoteViews getStandardView(int i) {
            return getStandardView(i, this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_UNSPECIFIED).fillTextsFrom(this.mBuilder), null);
        }

        protected RemoteViews getStandardView(int i, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            checkBuilder();
            CharSequence charSequence = this.mBigContentTitle;
            if (charSequence != null) {
                standardTemplateParams.mTitle = charSequence;
            }
            return this.mBuilder.applyStandardTemplateWithActions(i, standardTemplateParams, templateBindResult);
        }

        public void addExtras(Bundle bundle) {
            if (this.mSummaryTextSet) {
                bundle.putCharSequence(Notification.EXTRA_SUMMARY_TEXT, this.mSummaryText);
            }
            CharSequence charSequence = this.mBigContentTitle;
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_TITLE_BIG, charSequence);
            }
            bundle.putString(Notification.EXTRA_TEMPLATE, getClass().getName());
        }

        protected void restoreFromExtras(Bundle bundle) {
            if (bundle.containsKey(Notification.EXTRA_SUMMARY_TEXT)) {
                this.mSummaryText = bundle.getCharSequence(Notification.EXTRA_SUMMARY_TEXT);
                this.mSummaryTextSet = true;
            }
            if (bundle.containsKey(Notification.EXTRA_TITLE_BIG)) {
                this.mBigContentTitle = bundle.getCharSequence(Notification.EXTRA_TITLE_BIG);
            }
        }

        public Notification buildStyled(Notification notification) {
            addExtras(notification.extras);
            return notification;
        }

        public Notification build() {
            checkBuilder();
            return this.mBuilder.build();
        }
    }

    public static class BigPictureStyle extends Style {
        public static final int MIN_ASHMEM_BITMAP_SIZE = 131072;
        private Icon mBigLargeIcon;
        private boolean mBigLargeIconSet = false;
        private CharSequence mPictureContentDescription;
        private Icon mPictureIcon;
        private boolean mShowBigPictureWhenCollapsed;

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        public BigPictureStyle() {
        }

        @Deprecated
        public BigPictureStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            internalSetBigContentTitle(Notification.safeCharSequence(charSequence));
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            internalSetSummaryText(Notification.safeCharSequence(charSequence));
            return this;
        }

        public BigPictureStyle setContentDescription(CharSequence charSequence) {
            this.mPictureContentDescription = charSequence;
            return this;
        }

        public Icon getBigPicture() {
            Icon icon = this.mPictureIcon;
            if (icon != null) {
                return icon;
            }
            return null;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            this.mPictureIcon = bitmap == null ? null : Icon.createWithBitmap(bitmap);
            return this;
        }

        public BigPictureStyle bigPicture(Icon icon) {
            this.mPictureIcon = icon;
            return this;
        }

        public BigPictureStyle showBigPictureWhenCollapsed(boolean z) {
            this.mShowBigPictureWhenCollapsed = z;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap bitmap) {
            return bigLargeIcon(bitmap != null ? Icon.createWithBitmap(bitmap) : null);
        }

        public BigPictureStyle bigLargeIcon(Icon icon) {
            this.mBigLargeIconSet = true;
            this.mBigLargeIcon = icon;
            return this;
        }

        @Override // android.app.Notification.Style
        public void purgeResources() {
            super.purgeResources();
            Icon icon = this.mPictureIcon;
            if (icon != null) {
                icon.convertToAshmem();
            }
            Icon icon2 = this.mBigLargeIcon;
            if (icon2 != null) {
                icon2.convertToAshmem();
            }
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(Context context) {
            super.reduceImageSizes(context);
            Resources resources = context.getResources();
            boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
            if (this.mPictureIcon != null) {
                int dimensionPixelSize = resources.getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_big_picture_max_height_low_ram : R.dimen.notification_big_picture_max_height);
                int dimensionPixelSize2 = resources.getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_big_picture_max_width_low_ram : R.dimen.notification_big_picture_max_width);
                if (this.mPictureIcon.getType() == 1 || this.mPictureIcon.getType() == 5) {
                    this.mPictureIcon = Icon.createWithBitmap(Notification.scaleDownIfNecessaryForBigPicture(this.mPictureIcon.getBitmap(), dimensionPixelSize2, dimensionPixelSize));
                }
            }
            if (this.mBigLargeIcon != null) {
                int dimensionPixelSize3 = resources.getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_right_icon_size_low_ram : R.dimen.notification_right_icon_size);
                this.mBigLargeIcon.scaleDownIfNecessary(dimensionPixelSize3, dimensionPixelSize3);
            }
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView() {
            if (this.mPictureIcon == null || !this.mShowBigPictureWhenCollapsed) {
                return super.makeContentView();
            }
            return getStandardView(this.mBuilder.getCollapsedBaseLayoutResource(), this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).fillTextsFrom(this.mBuilder).promotedPicture(this.mPictureIcon), null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            if (this.mPictureIcon == null || !this.mShowBigPictureWhenCollapsed) {
                return super.makeHeadsUpContentView();
            }
            return getStandardView(this.mBuilder.getHeadsUpBaseLayoutResource(), this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this.mBuilder).promotedPicture(this.mPictureIcon), null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            Icon icon;
            Bitmap bitmap;
            if (this.mBigLargeIconSet) {
                icon = this.mBuilder.mN.mLargeIcon;
                this.mBuilder.mN.mLargeIcon = this.mBigLargeIcon;
                bitmap = this.mBuilder.mN.largeIcon;
                this.mBuilder.mN.largeIcon = null;
            } else {
                icon = null;
                bitmap = null;
            }
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).fillTextsFrom(this.mBuilder);
            RemoteViews standardView = getStandardView(this.mBuilder.getBigPictureLayoutResource(), fillTextsFrom, null);
            if (this.mSummaryTextSet) {
                standardView.setTextViewText(R.id.text, this.mBuilder.ensureColorSpanContrastOrStripStyling(this.mBuilder.processLegacyText(this.mSummaryText), fillTextsFrom));
                this.mBuilder.setTextViewColorSecondary(standardView, R.id.text, fillTextsFrom);
                standardView.setViewVisibility(R.id.text, 0);
            }
            if (this.mBigLargeIconSet) {
                this.mBuilder.mN.mLargeIcon = icon;
                this.mBuilder.mN.largeIcon = bitmap;
            }
            standardView.setImageViewIcon(R.id.big_picture, this.mPictureIcon);
            CharSequence charSequence = this.mPictureContentDescription;
            if (charSequence != null) {
                standardView.setContentDescription(R.id.big_picture, charSequence);
            }
            return standardView;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            if (this.mBigLargeIconSet) {
                bundle.putParcelable(Notification.EXTRA_LARGE_ICON_BIG, this.mBigLargeIcon);
            }
            CharSequence charSequence = this.mPictureContentDescription;
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION, charSequence);
            }
            bundle.putBoolean(Notification.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED, this.mShowBigPictureWhenCollapsed);
            Icon icon = this.mPictureIcon;
            if (icon == null) {
                bundle.remove(Notification.EXTRA_PICTURE_ICON);
                bundle.remove(Notification.EXTRA_PICTURE);
            } else if (icon.getType() == 1) {
                bundle.putParcelable(Notification.EXTRA_PICTURE, this.mPictureIcon.getBitmap());
                bundle.putParcelable(Notification.EXTRA_PICTURE_ICON, null);
            } else {
                bundle.putParcelable(Notification.EXTRA_PICTURE, null);
                bundle.putParcelable(Notification.EXTRA_PICTURE_ICON, this.mPictureIcon);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            if (bundle.containsKey(Notification.EXTRA_LARGE_ICON_BIG)) {
                this.mBigLargeIconSet = true;
                this.mBigLargeIcon = (Icon) bundle.getParcelable(Notification.EXTRA_LARGE_ICON_BIG, Icon.class);
            }
            if (bundle.containsKey(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION)) {
                this.mPictureContentDescription = bundle.getCharSequence(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION);
            }
            this.mShowBigPictureWhenCollapsed = bundle.getBoolean(Notification.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED);
            this.mPictureIcon = getPictureIcon(bundle);
        }

        public static Icon getPictureIcon(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            Bitmap bitmap = (Bitmap) bundle.getParcelable(Notification.EXTRA_PICTURE, Bitmap.class);
            if (bitmap != null) {
                return Icon.createWithBitmap(bitmap);
            }
            return (Icon) bundle.getParcelable(Notification.EXTRA_PICTURE_ICON, Icon.class);
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return Notification.areIconsMaybeDifferent(getBigPicture(), ((BigPictureStyle) style).getBigPicture());
        }
    }

    public static class BigTextStyle extends Style {
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        @Deprecated
        public BigTextStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            internalSetBigContentTitle(Notification.safeCharSequence(charSequence));
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            internalSetSummaryText(Notification.safeCharSequence(charSequence));
            return this;
        }

        public BigTextStyle bigText(CharSequence charSequence) {
            this.mBigText = Notification.safeCharSequence(charSequence);
            return this;
        }

        public CharSequence getBigText() {
            return this.mBigText;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            bundle.putCharSequence(Notification.EXTRA_BIG_TEXT, this.mBigText);
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mBigText = bundle.getCharSequence(Notification.EXTRA_BIG_TEXT);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).allowTextWithProgress(true).textViewId(R.id.big_text).fillTextsFrom(this.mBuilder);
            CharSequence processLegacyText = this.mBuilder.processLegacyText(this.mBigText);
            if (!this.mBuilder.mN.isPromotedOngoing() && Flags.cleanUpSpansAndNewLines()) {
                processLegacyText = Notification.normalizeBigText(Notification.stripStyling(processLegacyText));
            }
            if (!TextUtils.isEmpty(processLegacyText)) {
                fillTextsFrom.text(processLegacyText);
            }
            return getStandardView(this.mBuilder.getBigTextLayoutResource(), fillTextsFrom, null);
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return !Objects.equals(String.valueOf(getBigText()), String.valueOf(((BigTextStyle) style).getBigText()));
        }
    }

    public static class MessagingStyle extends Style {
        public static final int CONVERSATION_TYPE_IMPORTANT = 2;
        public static final int CONVERSATION_TYPE_LEGACY = 0;
        public static final int CONVERSATION_TYPE_NORMAL = 1;
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        int mConversationType;
        List<Message> mHistoricMessages;
        boolean mIsGroupConversation;
        List<Message> mMessages;
        Icon mShortcutIcon;
        int mUnreadMessageCount;
        Person mUser;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ConversationType {
        }

        MessagingStyle() {
            this.mMessages = new ArrayList();
            this.mHistoricMessages = new ArrayList();
            this.mConversationType = 0;
        }

        public MessagingStyle(CharSequence charSequence) {
            this(new Person.Builder().setName(charSequence).build());
        }

        public MessagingStyle(Person person) {
            this.mMessages = new ArrayList();
            this.mHistoricMessages = new ArrayList();
            this.mConversationType = 0;
            this.mUser = person;
        }

        @Override // android.app.Notification.Style
        public void validate(Context context) {
            super.validate(context);
            if (context.getApplicationInfo().targetSdkVersion >= 28) {
                Person person = this.mUser;
                if (person == null || person.getName() == null) {
                    throw new RuntimeException("User must be valid and have a name.");
                }
            }
        }

        @Override // android.app.Notification.Style
        public CharSequence getHeadsUpStatusBarText() {
            CharSequence charSequence;
            if (!TextUtils.isEmpty(((Style) this).mBigContentTitle)) {
                charSequence = ((Style) this).mBigContentTitle;
            } else {
                charSequence = this.mConversationTitle;
            }
            if (this.mConversationType != 0 || TextUtils.isEmpty(charSequence) || hasOnlyWhiteSpaceSenders()) {
                return null;
            }
            return charSequence;
        }

        public Person getUser() {
            return this.mUser;
        }

        public CharSequence getUserDisplayName() {
            return this.mUser.getName();
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public MessagingStyle setShortcutIcon(Icon icon) {
            this.mShortcutIcon = icon;
            return this;
        }

        public Icon getShortcutIcon() {
            return this.mShortcutIcon;
        }

        public MessagingStyle setConversationType(int i) {
            this.mConversationType = i;
            return this;
        }

        public int getConversationType() {
            return this.mConversationType;
        }

        public int getUnreadMessageCount() {
            return this.mUnreadMessageCount;
        }

        public MessagingStyle setUnreadMessageCount(int i) {
            this.mUnreadMessageCount = i;
            return this;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long j, CharSequence charSequence2) {
            return addMessage(charSequence, j, charSequence2 == null ? null : new Person.Builder().setName(charSequence2).build());
        }

        public MessagingStyle addMessage(CharSequence charSequence, long j, Person person) {
            return addMessage(new Message(charSequence, j, person));
        }

        public MessagingStyle addMessage(Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addHistoricMessage(Message message) {
            this.mHistoricMessages.add(message);
            if (this.mHistoricMessages.size() > 25) {
                this.mHistoricMessages.remove(0);
            }
            return this;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public List<Message> getHistoricMessages() {
            return this.mHistoricMessages;
        }

        public MessagingStyle setGroupConversation(boolean z) {
            this.mIsGroupConversation = z;
            return this;
        }

        public boolean isGroupConversation() {
            if (this.mBuilder == null || this.mBuilder.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                return this.mIsGroupConversation;
            }
            return this.mConversationTitle != null;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            addExtras(bundle, false, 0);
        }

        public void addExtras(Bundle bundle, boolean z, int i) {
            Person person = this.mUser;
            if (person != null) {
                bundle.putCharSequence(Notification.EXTRA_SELF_DISPLAY_NAME, person.getName());
                bundle.putParcelable(Notification.EXTRA_MESSAGING_PERSON, this.mUser);
            }
            CharSequence charSequence = this.mConversationTitle;
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_CONVERSATION_TITLE, charSequence);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(Notification.EXTRA_MESSAGES, getBundleArrayForMessages(this.mMessages, z, i));
            }
            if (!this.mHistoricMessages.isEmpty()) {
                bundle.putParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES, getBundleArrayForMessages(this.mHistoricMessages, z, i));
            }
            Icon icon = this.mShortcutIcon;
            if (icon != null) {
                bundle.putParcelable(Notification.EXTRA_CONVERSATION_ICON, icon);
            }
            bundle.putInt(Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT, this.mUnreadMessageCount);
            fixTitleAndTextExtras(bundle);
            bundle.putBoolean(Notification.EXTRA_IS_GROUP_CONVERSATION, this.mIsGroupConversation);
        }

        private static Bundle[] getBundleArrayForMessages(List<Message> list, boolean z, int i) {
            Bundle[] bundleArr = new Bundle[list.size()];
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Message message = list.get(i2);
                if (z) {
                    message.ensureColorContrastOrStripStyling(i);
                }
                bundleArr[i2] = message.toBundle();
            }
            return bundleArr;
        }

        private void fixTitleAndTextExtras(Bundle bundle) {
            Message findLatestIncomingMessage = findLatestIncomingMessage();
            CharSequence charSequence = null;
            CharSequence charSequence2 = findLatestIncomingMessage == null ? null : findLatestIncomingMessage.mText;
            if (findLatestIncomingMessage != null) {
                charSequence = ((findLatestIncomingMessage.mSender == null || TextUtils.isEmpty(findLatestIncomingMessage.mSender.getName())) ? this.mUser : findLatestIncomingMessage.mSender).getName();
            }
            if (!TextUtils.isEmpty(this.mConversationTitle)) {
                if (!TextUtils.isEmpty(charSequence) && !this.mConversationTitle.equals(charSequence)) {
                    BidiFormatter bidiFormatter = BidiFormatter.getInstance();
                    charSequence = this.mBuilder.mContext.getString(R.string.notification_messaging_title_template, bidiFormatter.unicodeWrap(this.mConversationTitle), bidiFormatter.unicodeWrap(charSequence));
                } else {
                    charSequence = this.mConversationTitle;
                }
            }
            if (Flags.cleanUpSpansAndNewLines()) {
                charSequence = Notification.stripStyling(charSequence);
            }
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_TITLE, charSequence);
            }
            if (charSequence2 != null) {
                bundle.putCharSequence(Notification.EXTRA_TEXT, charSequence2);
            }
        }

        private void fixTitleAndTextForCompactMessaging(StandardTemplateParams standardTemplateParams) {
            CharSequence name;
            Message findLatestIncomingMessage = findLatestIncomingMessage();
            CharSequence charSequence = findLatestIncomingMessage == null ? null : findLatestIncomingMessage.mText;
            if (findLatestIncomingMessage == null) {
                name = null;
            } else {
                name = ((findLatestIncomingMessage.mSender == null || TextUtils.isEmpty(findLatestIncomingMessage.mSender.getName())) ? this.mUser : findLatestIncomingMessage.mSender).getName();
            }
            CharSequence charSequence2 = this.mIsGroupConversation ? this.mConversationTitle : null;
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            if (name != null) {
                name = this.mBuilder.mContext.getString(R.string.notification_messaging_title_template, bidiFormatter.unicodeWrap(name), "");
            } else if (charSequence2 != null) {
                charSequence2 = this.mBuilder.mContext.getString(R.string.notification_messaging_title_template, bidiFormatter.unicodeWrap(charSequence2), "");
            }
            if (Flags.cleanUpSpansAndNewLines()) {
                charSequence2 = Notification.stripStyling(charSequence2);
                name = Notification.stripStyling(name);
            }
            boolean z = showConversationTitle() && charSequence2 != null;
            if (!z) {
                charSequence2 = name;
            }
            standardTemplateParams.title(charSequence2);
            if (z) {
                standardTemplateParams.headerTextSecondary(name);
                standardTemplateParams.summaryText(charSequence);
            } else {
                standardTemplateParams.headerTextSecondary(charSequence);
                standardTemplateParams.summaryText(null);
            }
        }

        private boolean showConversationTitle() {
            return SystemProperties.getBoolean("persist.compact_heads_up_notification.show_conversation_title_for_group", false);
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            Person person = (Person) bundle.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
            if (person == null) {
                this.mUser = new Person.Builder().setName(bundle.getCharSequence(Notification.EXTRA_SELF_DISPLAY_NAME)).build();
            } else {
                this.mUser = person;
            }
            this.mConversationTitle = bundle.getCharSequence(Notification.EXTRA_CONVERSATION_TITLE);
            this.mMessages = Message.getMessagesFromBundleArray((Parcelable[]) bundle.getParcelableArray(Notification.EXTRA_MESSAGES, Parcelable.class));
            this.mHistoricMessages = Message.getMessagesFromBundleArray((Parcelable[]) bundle.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES, Parcelable.class));
            this.mIsGroupConversation = bundle.getBoolean(Notification.EXTRA_IS_GROUP_CONVERSATION);
            this.mUnreadMessageCount = bundle.getInt(Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT);
            this.mShortcutIcon = (Icon) bundle.getParcelable(Notification.EXTRA_CONVERSATION_ICON, Icon.class);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView() {
            ArrayList arrayList = this.mBuilder.mActions;
            try {
                this.mBuilder.mActions = new ArrayList();
                return makeMessagingView(StandardTemplateParams.VIEW_TYPE_NORMAL);
            } finally {
                this.mBuilder.mActions = arrayList;
            }
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            CharSequence name;
            CharSequence name2;
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            List<Message> messages = getMessages();
            List<Message> messages2 = ((MessagingStyle) style).getMessages();
            if (messages == null || messages2 == null) {
                messages2 = new ArrayList<>();
            }
            int size = messages.size();
            if (size != messages2.size()) {
                return true;
            }
            for (int i = 0; i < size; i++) {
                Message message = messages.get(i);
                Message message2 = messages2.get(i);
                if (!Objects.equals(String.valueOf(message.getText()), String.valueOf(message2.getText())) || !Objects.equals(message.getDataUri(), message2.getDataUri())) {
                    return true;
                }
                if (message.getSenderPerson() == null) {
                    name = message.getSender();
                } else {
                    name = message.getSenderPerson().getName();
                }
                String valueOf = String.valueOf(name);
                if (message2.getSenderPerson() == null) {
                    name2 = message2.getSender();
                } else {
                    name2 = message2.getSenderPerson().getName();
                }
                if (!Objects.equals(valueOf, String.valueOf(name2))) {
                    return true;
                }
                if (!Objects.equals(message.getSenderPerson() == null ? null : message.getSenderPerson().getKey(), message2.getSenderPerson() != null ? message2.getSenderPerson().getKey() : null)) {
                    return true;
                }
            }
            return false;
        }

        private Message findLatestIncomingMessage() {
            return findLatestIncomingMessage(this.mMessages);
        }

        public static Message findLatestIncomingMessage(List<Message> list) {
            for (int size = list.size() - 1; size >= 0; size--) {
                Message message = list.get(size);
                if (message.mSender != null && !TextUtils.isEmpty(message.mSender.getName())) {
                    return message;
                }
            }
            if (list.isEmpty()) {
                return null;
            }
            return list.get(list.size() - 1);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            return makeMessagingView(StandardTemplateParams.VIEW_TYPE_EXPANDED);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00c5  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0192  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x00e9  */
        /* JADX WARN: Type inference failed for: r13v1, types: [android.app.Notification-IA] */
        /* JADX WARN: Type inference failed for: r13v2, types: [java.lang.CharSequence] */
        /* JADX WARN: Type inference failed for: r13v3 */
        /* JADX WARN: Type inference failed for: r3v11, types: [android.app.Notification$StandardTemplateParams] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private android.widget.RemoteViews makeMessagingView(int r17) {
            /*
                Method dump skipped, instructions count: 485
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.Notification.MessagingStyle.makeMessagingView(int):android.widget.RemoteViews");
        }

        private int getMessagingLayoutResource(boolean z, boolean z2) {
            if (!Flags.notificationsRedesignTemplates()) {
                if (z) {
                    return this.mBuilder.getConversationLayoutResource();
                }
                if (z2) {
                    return this.mBuilder.getCollapsedMessagingLayoutResource();
                }
                return this.mBuilder.getExpandedMessagingLayoutResource();
            }
            if (z) {
                if (z2) {
                    return this.mBuilder.getCollapsedConversationLayoutResource();
                }
                return this.mBuilder.getExpandedConversationLayoutResource();
            }
            if (z2) {
                return this.mBuilder.getCollapsedMessagingLayoutResource();
            }
            return this.mBuilder.getExpandedMessagingLayoutResource();
        }

        private CharSequence getKey(Person person) {
            if (person == null) {
                return null;
            }
            return person.getKey() == null ? person.getName() : person.getKey();
        }

        private CharSequence getOtherPersonName() {
            CharSequence key = getKey(this.mUser);
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                Person senderPerson = this.mMessages.get(size).getSenderPerson();
                if (senderPerson != null && !TextUtils.equals(key, getKey(senderPerson))) {
                    return senderPerson.getName();
                }
            }
            if (Flags.notificationsRedesignTemplates()) {
                return this.mUser.getName();
            }
            return null;
        }

        private boolean hasOnlyWhiteSpaceSenders() {
            for (int i = 0; i < this.mMessages.size(); i++) {
                Person senderPerson = this.mMessages.get(i).getSenderPerson();
                if (senderPerson != null && !isWhiteSpace(senderPerson.getName())) {
                    return false;
                }
            }
            return true;
        }

        private boolean isWhiteSpace(CharSequence charSequence) {
            if (TextUtils.isEmpty(charSequence) || charSequence.toString().matches("^\\s*$")) {
                return true;
            }
            for (int i = 0; i < charSequence.length(); i++) {
                if (charSequence.charAt(i) != 8203) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            RemoteViews makeMessagingView = makeMessagingView(StandardTemplateParams.VIEW_TYPE_HEADS_UP);
            makeMessagingView.setInt(R.id.notification_messaging, "setMaxDisplayedLines", 2);
            return makeMessagingView;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeCompactHeadsUpContentView() {
            Icon icon;
            Action action;
            Message findLatestIncomingMessage;
            Person person;
            boolean z = this.mConversationType != 0;
            if (z) {
                icon = this.mShortcutIcon;
                if (icon == null && !this.mIsGroupConversation && (findLatestIncomingMessage = findLatestIncomingMessage()) != null && (person = findLatestIncomingMessage.mSender) != null) {
                    icon = person.getIcon();
                }
                if (Flags.compactHeadsUpNotificationReply()) {
                    List nonContextualActions = this.mBuilder.getNonContextualActions();
                    for (int i = 0; i < nonContextualActions.size(); i++) {
                        action = (Action) nonContextualActions.get(i);
                        if (this.mBuilder.hasValidRemoteInput(action)) {
                            break;
                        }
                    }
                }
                action = null;
            } else {
                icon = null;
                action = null;
            }
            StandardTemplateParams hideTime = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).highlightExpander(z).fillTextsFrom(this.mBuilder).hideTime(true);
            fixTitleAndTextForCompactMessaging(hideTime);
            RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(this.mBuilder.getMessagingCompactHeadsUpLayoutResource(), hideTime, new TemplateBindResult());
            applyStandardTemplate.setViewVisibility(R.id.header_text_secondary_divider, 8);
            applyStandardTemplate.setViewVisibility(R.id.header_text_divider, 8);
            if (icon != null) {
                applyStandardTemplate.setViewVisibility(16908294, 8);
                applyStandardTemplate.setViewVisibility(R.id.conversation_face_pile, 8);
                applyStandardTemplate.setViewVisibility(R.id.conversation_icon, 0);
                applyStandardTemplate.setImageViewIcon(R.id.conversation_icon, icon);
            } else if (this.mIsGroupConversation) {
                applyStandardTemplate.setViewVisibility(16908294, 8);
                applyStandardTemplate.setViewVisibility(R.id.conversation_icon, 8);
                applyStandardTemplate.setInt(R.id.status_bar_latest_event_content, "setNotificationBackgroundColor", this.mBuilder.getBackgroundColor(hideTime));
                applyStandardTemplate.setInt(R.id.status_bar_latest_event_content, "setLayoutColor", this.mBuilder.getSmallIconColor(hideTime));
                applyStandardTemplate.setBundle(R.id.status_bar_latest_event_content, "setGroupFacePile", this.mBuilder.mN.extras);
            }
            if (action != null) {
                applyStandardTemplate.setViewVisibility(R.id.reply_action_container, 0);
                RemoteViews generateActionButton = this.mBuilder.generateActionButton(action, false, hideTime);
                generateActionButton.setInt(R.id.action0, "setBackgroundResource", 0);
                generateActionButton.setTextViewText(R.id.action0, this.mBuilder.mContext.getString(R.string.notification_compact_heads_up_reply));
                applyStandardTemplate.addView(R.id.reply_action_container, generateActionButton);
                return applyStandardTemplate;
            }
            applyStandardTemplate.setViewVisibility(R.id.reply_action_container, 8);
            return applyStandardTemplate;
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(Context context) {
            super.reduceImageSizes(context);
            Resources resources = context.getResources();
            boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
            if (this.mShortcutIcon != null) {
                int dimensionPixelSize = resources.getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_small_icon_size_low_ram : R.dimen.notification_small_icon_size);
                this.mShortcutIcon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
            }
            int dimensionPixelSize2 = resources.getDimensionPixelSize(isLowRamDeviceStatic ? R.dimen.notification_person_icon_max_size_low_ram : R.dimen.notification_person_icon_max_size);
            Person person = this.mUser;
            if (person != null && person.getIcon() != null) {
                this.mUser.getIcon().scaleDownIfNecessary(dimensionPixelSize2, dimensionPixelSize2);
            }
            reduceMessagesIconSizes(this.mMessages, dimensionPixelSize2);
            reduceMessagesIconSizes(this.mHistoricMessages, dimensionPixelSize2);
        }

        private static void reduceMessagesIconSizes(List<Message> list, int i) {
            Icon icon;
            if (list == null) {
                return;
            }
            Iterator<Message> it = list.iterator();
            while (it.hasNext()) {
                Person person = it.next().mSender;
                if (person != null && (icon = person.getIcon()) != null) {
                    icon.scaleDownIfNecessary(i, i);
                }
            }
        }

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_REMOTE_INPUT_HISTORY = "remote_input_history";
            static final String KEY_SENDER = "sender";
            static final String KEY_SENDER_PERSON = "sender_person";
            public static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private Bundle mExtras;
            private final boolean mRemoteInputHistory;
            private final Person mSender;
            private CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence charSequence, long j, CharSequence charSequence2) {
                this(charSequence, j, charSequence2 == null ? null : new Person.Builder().setName(charSequence2).build());
            }

            public Message(CharSequence charSequence, long j, Person person) {
                this(charSequence, j, person, false);
            }

            public Message(CharSequence charSequence, long j, Person person, boolean z) {
                this.mExtras = new Bundle();
                this.mText = Notification.safeCharSequence(charSequence);
                this.mTimestamp = j;
                this.mSender = person;
                this.mRemoteInputHistory = z;
            }

            public Message setData(String str, Uri uri) {
                this.mDataMimeType = str;
                this.mDataUri = uri;
                return this;
            }

            public void ensureColorContrastOrStripStyling(int i) {
                if (Flags.cleanUpSpansAndNewLines()) {
                    this.mText = stripNonStyleSpans(this.mText);
                } else {
                    ensureColorContrast(i);
                }
            }

            private CharSequence stripNonStyleSpans(CharSequence charSequence) {
                Object obj;
                if (!(charSequence instanceof Spanned)) {
                    return charSequence;
                }
                Spanned spanned = (Spanned) charSequence;
                Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned.toString());
                for (Object obj2 : spans) {
                    if ((obj2 instanceof StyleSpan) || (obj2 instanceof StrikethroughSpan) || (obj2 instanceof UnderlineSpan)) {
                        obj = obj2;
                    } else if (obj2 instanceof TextAppearanceSpan) {
                        obj = new TextAppearanceSpan(null, ((TextAppearanceSpan) obj2).getTextStyle(), -1, null, null);
                    }
                    spannableStringBuilder.setSpan(obj, spanned.getSpanStart(obj2), spanned.getSpanEnd(obj2), spanned.getSpanFlags(obj2));
                }
                return spannableStringBuilder;
            }

            public void ensureColorContrast(int i) {
                this.mText = ContrastColorUtil.ensureColorSpanContrast(this.mText, i);
            }

            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public CharSequence getSender() {
                Person person = this.mSender;
                if (person == null) {
                    return null;
                }
                return person.getName();
            }

            public Person getSenderPerson() {
                return this.mSender;
            }

            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            public Uri getDataUri() {
                return this.mDataUri;
            }

            public boolean isRemoteInputHistory() {
                return this.mRemoteInputHistory;
            }

            public Bundle toBundle() {
                Bundle bundle = new Bundle();
                CharSequence charSequence = this.mText;
                if (charSequence != null) {
                    bundle.putCharSequence("text", charSequence);
                }
                bundle.putLong("time", this.mTimestamp);
                Person person = this.mSender;
                if (person != null) {
                    bundle.putCharSequence("sender", Notification.safeCharSequence(person.getName()));
                    bundle.putParcelable(KEY_SENDER_PERSON, this.mSender);
                }
                String str = this.mDataMimeType;
                if (str != null) {
                    bundle.putString("type", str);
                }
                Uri uri = this.mDataUri;
                if (uri != null) {
                    bundle.putParcelable("uri", uri);
                }
                Bundle bundle2 = this.mExtras;
                if (bundle2 != null) {
                    bundle.putBundle("extras", bundle2);
                }
                boolean z = this.mRemoteInputHistory;
                if (z) {
                    bundle.putBoolean(KEY_REMOTE_INPUT_HISTORY, z);
                }
                return bundle;
            }

            public void visitUris(Consumer<Uri> consumer) {
                consumer.accept(getDataUri());
                Person person = this.mSender;
                if (person != null) {
                    person.visitUris(consumer);
                }
            }

            public static List<Message> getMessagesFromBundleArray(Parcelable[] parcelableArr) {
                Message messageFromBundle;
                if (parcelableArr == null) {
                    return new ArrayList();
                }
                ArrayList arrayList = new ArrayList(parcelableArr.length);
                for (Parcelable parcelable : parcelableArr) {
                    if ((parcelable instanceof Bundle) && (messageFromBundle = getMessageFromBundle((Bundle) parcelable)) != null) {
                        arrayList.add(messageFromBundle);
                    }
                }
                return arrayList;
            }

            public static Message getMessageFromBundle(Bundle bundle) {
                CharSequence charSequence;
                try {
                    if (bundle.containsKey("text") && bundle.containsKey("time")) {
                        Person person = (Person) bundle.getParcelable(KEY_SENDER_PERSON, Person.class);
                        if (person == null && (charSequence = bundle.getCharSequence("sender")) != null) {
                            person = new Person.Builder().setName(charSequence).build();
                        }
                        Message message = new Message(bundle.getCharSequence("text"), bundle.getLong("time"), person, bundle.getBoolean(KEY_REMOTE_INPUT_HISTORY, false));
                        if (bundle.containsKey("type") && bundle.containsKey("uri")) {
                            message.setData(bundle.getString("type"), (Uri) bundle.getParcelable("uri", Uri.class));
                        }
                        if (bundle.containsKey("extras")) {
                            message.getExtras().putAll(bundle.getBundle("extras"));
                        }
                        return message;
                    }
                    return null;
                } catch (BadParcelableException e) {
                    Log.e(Notification.TAG, "could not unparcel extras from message notification", e);
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }
        }
    }

    public static class InboxStyle extends Style {
        private static final int NUMBER_OF_HISTORY_ALLOWED_UNTIL_REDUCTION = 1;
        private ArrayList<CharSequence> mTexts = new ArrayList<>(5);

        public InboxStyle() {
        }

        @Deprecated
        public InboxStyle(Builder builder) {
            setBuilder(builder);
        }

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            internalSetBigContentTitle(Notification.safeCharSequence(charSequence));
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            internalSetSummaryText(Notification.safeCharSequence(charSequence));
            return this;
        }

        public InboxStyle addLine(CharSequence charSequence) {
            this.mTexts.add(Notification.safeCharSequence(charSequence));
            return this;
        }

        public ArrayList<CharSequence> getLines() {
            return this.mTexts;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            bundle.putCharSequenceArray(Notification.EXTRA_TEXT_LINES, (CharSequence[]) this.mTexts.toArray(new CharSequence[this.mTexts.size()]));
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mTexts.clear();
            if (bundle.containsKey(Notification.EXTRA_TEXT_LINES)) {
                Collections.addAll(this.mTexts, bundle.getCharSequenceArray(Notification.EXTRA_TEXT_LINES));
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x00ac  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00d8 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00dd  */
        @Override // android.app.Notification.Style
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.widget.RemoteViews makeExpandedContentView() {
            /*
                Method dump skipped, instructions count: 264
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.Notification.InboxStyle.makeExpandedContentView():android.widget.RemoteViews");
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            ArrayList<CharSequence> lines = getLines();
            ArrayList<CharSequence> lines2 = ((InboxStyle) style).getLines();
            int size = lines.size();
            if (size != lines2.size()) {
                return true;
            }
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(String.valueOf(lines.get(i)), String.valueOf(lines2.get(i)))) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class MediaStyle extends Style {
        static final int MAX_MEDIA_BUTTONS = 5;
        static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
        private static final int[] MEDIA_BUTTON_IDS = {R.id.action0, R.id.action1, R.id.action2, R.id.action3, R.id.action4};
        private int[] mActionsToShowInCompact = null;
        private int mDeviceIcon;
        private PendingIntent mDeviceIntent;
        private CharSequence mDeviceName;
        private MediaSession.Token mToken;

        public MediaStyle() {
        }

        @Deprecated
        public MediaStyle(Builder builder) {
            setBuilder(builder);
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.mActionsToShowInCompact = iArr;
            return this;
        }

        public MediaStyle setMediaSession(MediaSession.Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setRemotePlaybackInfo(CharSequence charSequence, int i, PendingIntent pendingIntent) {
            this.mDeviceName = charSequence;
            this.mDeviceIcon = i;
            this.mDeviceIntent = pendingIntent;
            return this;
        }

        @Override // android.app.Notification.Style
        public Notification buildStyled(Notification notification) {
            super.buildStyled(notification);
            if (notification.category == null) {
                notification.category = Notification.CATEGORY_TRANSPORT;
            }
            return notification;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView() {
            return makeMediaContentView(null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            return makeMediaExpandedContentView(null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            return makeMediaContentView(null);
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            MediaSession.Token token = this.mToken;
            if (token != null) {
                bundle.putParcelable(Notification.EXTRA_MEDIA_SESSION, token);
            }
            int[] iArr = this.mActionsToShowInCompact;
            if (iArr != null) {
                bundle.putIntArray(Notification.EXTRA_COMPACT_ACTIONS, iArr);
            }
            CharSequence charSequence = this.mDeviceName;
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_MEDIA_REMOTE_DEVICE, charSequence);
            }
            int i = this.mDeviceIcon;
            if (i > 0) {
                bundle.putInt(Notification.EXTRA_MEDIA_REMOTE_ICON, i);
            }
            PendingIntent pendingIntent = this.mDeviceIntent;
            if (pendingIntent != null) {
                bundle.putParcelable(Notification.EXTRA_MEDIA_REMOTE_INTENT, pendingIntent);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            if (bundle.containsKey(Notification.EXTRA_MEDIA_SESSION)) {
                this.mToken = (MediaSession.Token) bundle.getParcelable(Notification.EXTRA_MEDIA_SESSION, MediaSession.Token.class);
            }
            if (bundle.containsKey(Notification.EXTRA_COMPACT_ACTIONS)) {
                this.mActionsToShowInCompact = bundle.getIntArray(Notification.EXTRA_COMPACT_ACTIONS);
            }
            if (bundle.containsKey(Notification.EXTRA_MEDIA_REMOTE_DEVICE)) {
                this.mDeviceName = bundle.getCharSequence(Notification.EXTRA_MEDIA_REMOTE_DEVICE);
            }
            if (bundle.containsKey(Notification.EXTRA_MEDIA_REMOTE_ICON)) {
                this.mDeviceIcon = bundle.getInt(Notification.EXTRA_MEDIA_REMOTE_ICON);
            }
            if (bundle.containsKey(Notification.EXTRA_MEDIA_REMOTE_INTENT)) {
                this.mDeviceIntent = (PendingIntent) bundle.getParcelable(Notification.EXTRA_MEDIA_REMOTE_INTENT, PendingIntent.class);
            }
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            return style == null || getClass() != style.getClass();
        }

        private void bindMediaActionButton(RemoteViews remoteViews, int i, Action action, StandardTemplateParams standardTemplateParams) {
            boolean z = action.actionIntent == null;
            remoteViews.setViewVisibility(i, 0);
            remoteViews.setImageViewIcon(i, action.getIcon());
            int standardActionColor = this.mBuilder.getStandardActionColor(standardTemplateParams);
            remoteViews.setDrawableTint(i, false, standardActionColor, PorterDuff.Mode.SRC_ATOP);
            remoteViews.setRippleDrawableColor(i, ColorStateList.valueOf(Color.argb(this.mBuilder.getColors(standardTemplateParams).getRippleAlpha(), Color.red(standardActionColor), Color.green(standardActionColor), Color.blue(standardActionColor))));
            if (!z) {
                remoteViews.setOnClickPendingIntent(i, action.actionIntent);
            }
            remoteViews.setContentDescription(i, action.title);
        }

        protected RemoteViews makeMediaContentView(RemoteViews remoteViews) {
            int size = this.mBuilder.mActions.size();
            int[] iArr = this.mActionsToShowInCompact;
            int min = Math.min(iArr == null ? 0 : iArr.length, 3);
            if (min > size) {
                throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(size), Integer.valueOf(size - 1)));
            }
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).hideTime(min > 1).hideSubText(min > 1).hideLeftIcon(false).hideRightIcon(min > 0).hideProgress(true).fillTextsFrom(this.mBuilder);
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(this.mBuilder.getCollapsedMediaLayoutResource(), fillTextsFrom, null);
            for (int i = 0; i < 3; i++) {
                if (i < min) {
                    bindMediaActionButton(applyStandardTemplate, MEDIA_BUTTON_IDS[i], (Action) this.mBuilder.mActions.get(this.mActionsToShowInCompact[i]), fillTextsFrom);
                } else {
                    applyStandardTemplate.setViewVisibility(MEDIA_BUTTON_IDS[i], 8);
                }
            }
            applyStandardTemplate.setViewVisibility(R.id.media_actions, min != 0 ? 0 : 8);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }

        protected RemoteViews makeMediaExpandedContentView(RemoteViews remoteViews) {
            int min = Math.min(this.mBuilder.mActions.size(), 5);
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).hideProgress(true).fillTextsFrom(this.mBuilder);
            TemplateBindResult templateBindResult = new TemplateBindResult();
            RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(this.mBuilder.getExpandedMediaLayoutResource(), fillTextsFrom, templateBindResult);
            for (int i = 0; i < 5; i++) {
                if (i < min) {
                    bindMediaActionButton(applyStandardTemplate, MEDIA_BUTTON_IDS[i], (Action) this.mBuilder.mActions.get(i), fillTextsFrom);
                } else {
                    applyStandardTemplate.setViewVisibility(MEDIA_BUTTON_IDS[i], 8);
                }
            }
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }
    }

    public static class CallStyle extends Style {
        public static final int CALL_TYPE_INCOMING = 1;
        public static final int CALL_TYPE_ONGOING = 2;
        public static final int CALL_TYPE_SCREENING = 3;
        public static final int CALL_TYPE_UNKNOWN = 0;
        public static final boolean DEBUG_NEW_ACTION_LAYOUT = true;
        private static final String KEY_ACTION_PRIORITY = "key_action_priority";
        private Integer mAnswerButtonColor;
        private PendingIntent mAnswerIntent;
        private int mCallType;
        private Integer mDeclineButtonColor;
        private PendingIntent mDeclineIntent;
        private PendingIntent mHangUpIntent;
        private boolean mIsVideo;
        private Person mPerson;
        private Icon mVerificationIcon;
        private CharSequence mVerificationText;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallType {
        }

        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return false;
        }

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        CallStyle() {
        }

        public static CallStyle forIncomingCall(Person person, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
            return new CallStyle(1, person, null, (PendingIntent) Objects.requireNonNull(pendingIntent, "declineIntent is required"), (PendingIntent) Objects.requireNonNull(pendingIntent2, "answerIntent is required"));
        }

        public static CallStyle forOngoingCall(Person person, PendingIntent pendingIntent) {
            return new CallStyle(2, person, (PendingIntent) Objects.requireNonNull(pendingIntent, "hangUpIntent is required"), null, null);
        }

        public static CallStyle forScreeningCall(Person person, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
            return new CallStyle(3, person, (PendingIntent) Objects.requireNonNull(pendingIntent, "hangUpIntent is required"), null, (PendingIntent) Objects.requireNonNull(pendingIntent2, "answerIntent is required"));
        }

        private CallStyle(int i, Person person, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3) {
            if (person == null || TextUtils.isEmpty(person.getName())) {
                throw new IllegalArgumentException("person must have a non-empty a name");
            }
            this.mCallType = i;
            this.mPerson = person;
            this.mAnswerIntent = pendingIntent3;
            this.mDeclineIntent = pendingIntent2;
            this.mHangUpIntent = pendingIntent;
        }

        public CallStyle setIsVideo(boolean z) {
            this.mIsVideo = z;
            return this;
        }

        public CallStyle setVerificationIcon(Icon icon) {
            this.mVerificationIcon = icon;
            return this;
        }

        public CallStyle setVerificationText(CharSequence charSequence) {
            this.mVerificationText = Notification.safeCharSequence(charSequence);
            return this;
        }

        public CallStyle setAnswerButtonColorHint(int i) {
            this.mAnswerButtonColor = Integer.valueOf(i);
            return this;
        }

        public CallStyle setDeclineButtonColorHint(int i) {
            this.mDeclineButtonColor = Integer.valueOf(i);
            return this;
        }

        @Override // android.app.Notification.Style
        public Notification buildStyled(Notification notification) {
            Notification buildStyled = super.buildStyled(notification);
            this.mBuilder.mActions = getActionsListWithSystemActions();
            buildStyled.actions = new Action[this.mBuilder.mActions.size()];
            this.mBuilder.mActions.toArray(buildStyled.actions);
            return buildStyled;
        }

        @Override // android.app.Notification.Style
        public void purgeResources() {
            super.purgeResources();
            Icon icon = this.mVerificationIcon;
            if (icon != null) {
                icon.convertToAshmem();
            }
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(Context context) {
            super.reduceImageSizes(context);
            if (this.mVerificationIcon != null) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.notification_right_icon_size_low_ram : R.dimen.notification_right_icon_size);
                this.mVerificationIcon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
            }
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView() {
            return makeCallLayout(StandardTemplateParams.VIEW_TYPE_NORMAL);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            return makeCallLayout(StandardTemplateParams.VIEW_TYPE_HEADS_UP);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeCompactHeadsUpContentView() {
            return makeHeadsUpContentView();
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            return makeCallLayout(StandardTemplateParams.VIEW_TYPE_EXPANDED);
        }

        private Action makeNegativeAction() {
            PendingIntent pendingIntent = this.mDeclineIntent;
            if (pendingIntent == null) {
                return makeAction(R.drawable.ic_call_decline, R.string.call_notification_hang_up_action, this.mDeclineButtonColor, R.color.call_notification_decline_color, this.mHangUpIntent);
            }
            return makeAction(R.drawable.ic_call_decline, R.string.call_notification_decline_action, this.mDeclineButtonColor, R.color.call_notification_decline_color, pendingIntent);
        }

        private Action makeAnswerAction() {
            PendingIntent pendingIntent = this.mAnswerIntent;
            if (pendingIntent == null) {
                return null;
            }
            boolean z = this.mIsVideo;
            return makeAction(z ? R.drawable.ic_call_answer_video : R.drawable.ic_call_answer, z ? R.string.call_notification_answer_video_action : R.string.call_notification_answer_action, this.mAnswerButtonColor, R.color.call_notification_answer_color, pendingIntent);
        }

        private Action makeAction(int i, int i2, Integer num, int i3, PendingIntent pendingIntent) {
            if (num == null || !this.mBuilder.isCallActionColorCustomizable()) {
                num = Integer.valueOf(this.mBuilder.mContext.getColor(i3));
            }
            Action build = new Action.Builder(Icon.createWithResource("", i), new SpannableStringBuilder().append(this.mBuilder.mContext.getString(i2), new ForegroundColorSpan(num.intValue()), 18), pendingIntent).build();
            build.getExtras().putBoolean(KEY_ACTION_PRIORITY, true);
            return build;
        }

        private boolean isActionAddedByCallStyle(Action action) {
            return action != null && action.getExtras().getBoolean(KEY_ACTION_PRIORITY);
        }

        public ArrayList<Action> getActionsListWithSystemActions() {
            Action makeNegativeAction = makeNegativeAction();
            Action makeAnswerAction = makeAnswerAction();
            ArrayList<Action> arrayList = new ArrayList<>(3);
            arrayList.add(makeNegativeAction);
            int i = 2;
            if (this.mBuilder.mActions != null) {
                Iterator it = this.mBuilder.mActions.iterator();
                while (it.hasNext()) {
                    Action action = (Action) it.next();
                    if (action.isContextual()) {
                        arrayList.add(action);
                    } else if (!isActionAddedByCallStyle(action)) {
                        arrayList.add(action);
                        i--;
                    }
                    if (makeAnswerAction != null && i == 1) {
                        arrayList.add(makeAnswerAction);
                        i--;
                    }
                }
            }
            if (makeAnswerAction != null && i >= 1) {
                arrayList.add(makeAnswerAction);
            }
            return arrayList;
        }

        private RemoteViews makeCallLayout(int i) {
            RemoteViews applyStandardTemplateWithActions;
            boolean z = i == StandardTemplateParams.VIEW_TYPE_NORMAL;
            boolean z2 = i == StandardTemplateParams.VIEW_TYPE_HEADS_UP;
            Bundle bundle = this.mBuilder.mN.extras;
            Person person = this.mPerson;
            CharSequence name = person != null ? person.getName() : null;
            CharSequence processLegacyText = this.mBuilder.processLegacyText(bundle.getCharSequence(Notification.EXTRA_TEXT));
            if (processLegacyText == null) {
                processLegacyText = getDefaultText();
            }
            StandardTemplateParams text = this.mBuilder.mParams.reset().viewType(i).callStyleActions(true).allowTextWithProgress(true).hideLeftIcon(true).hideRightIcon(true).hideAppName(z).title(name).text(processLegacyText);
            if (!Flags.notificationsRedesignTemplates()) {
                text.titleViewId(R.id.conversation_text).summaryText(this.mBuilder.processLegacyText(this.mVerificationText));
            }
            this.mBuilder.mActions = getActionsListWithSystemActions();
            if (z) {
                applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplate(this.mBuilder.getCollapsedCallLayoutResource(), text, null);
            } else if (Flags.notificationsRedesignTemplates() && z2) {
                applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getCollapsedCallLayoutResource(), text, null);
            } else {
                applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getExpandedCallLayoutResource(), text, null);
            }
            if (!Flags.notificationsRedesignTemplates() && !text.mHideAppName) {
                this.mBuilder.setTextViewColorSecondary(applyStandardTemplateWithActions, R.id.app_name_divider, text);
                applyStandardTemplateWithActions.setViewVisibility(R.id.app_name_divider, 8);
            }
            bindCallerVerification(applyStandardTemplateWithActions, text);
            applyStandardTemplateWithActions.setInt(R.id.status_bar_latest_event_content, "setLayoutColor", this.mBuilder.getSmallIconColor(text));
            applyStandardTemplateWithActions.setInt(R.id.status_bar_latest_event_content, "setNotificationBackgroundColor", this.mBuilder.getBackgroundColor(text));
            applyStandardTemplateWithActions.setIcon(R.id.status_bar_latest_event_content, "setLargeIcon", this.mBuilder.mN.mLargeIcon);
            applyStandardTemplateWithActions.setBundle(R.id.status_bar_latest_event_content, "setData", this.mBuilder.mN.extras);
            return applyStandardTemplateWithActions;
        }

        private void bindCallerVerification(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams) {
            boolean z;
            String str;
            Icon icon = this.mVerificationIcon;
            boolean z2 = false;
            String str2 = null;
            if (icon != null) {
                remoteViews.setImageViewIcon(R.id.verification_icon, icon);
                remoteViews.setDrawableTint(R.id.verification_icon, false, this.mBuilder.getSecondaryTextColor(standardTemplateParams), PorterDuff.Mode.SRC_ATOP);
                remoteViews.setViewVisibility(R.id.verification_icon, 0);
                str = this.mBuilder.mContext.getString(R.string.notification_verified_content_description);
                z = false;
            } else {
                remoteViews.setViewVisibility(R.id.verification_icon, 8);
                z = true;
                str = null;
            }
            if (!TextUtils.isEmpty(this.mVerificationText)) {
                remoteViews.setTextViewText(R.id.verification_text, this.mVerificationText);
                this.mBuilder.setTextViewColorSecondary(remoteViews, R.id.verification_text, standardTemplateParams);
                remoteViews.setViewVisibility(R.id.verification_text, 0);
                z2 = z;
            } else {
                remoteViews.setViewVisibility(R.id.verification_text, 8);
                str2 = str;
            }
            remoteViews.setContentDescription(R.id.verification_icon, str2);
            if (z2) {
                remoteViews.setViewVisibility(R.id.verification_divider, 8);
                this.mBuilder.setTextViewColorSecondary(remoteViews, R.id.verification_divider, standardTemplateParams);
            } else {
                remoteViews.setViewVisibility(R.id.verification_divider, 8);
            }
        }

        private String getDefaultText() {
            int i = this.mCallType;
            if (i == 1) {
                return this.mBuilder.mContext.getString(R.string.call_notification_incoming_text);
            }
            if (i == 2) {
                return this.mBuilder.mContext.getString(R.string.call_notification_ongoing_text);
            }
            if (i != 3) {
                return null;
            }
            return this.mBuilder.mContext.getString(R.string.call_notification_screening_text);
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            bundle.putInt(Notification.EXTRA_CALL_TYPE, this.mCallType);
            bundle.putBoolean(Notification.EXTRA_CALL_IS_VIDEO, this.mIsVideo);
            bundle.putParcelable(Notification.EXTRA_CALL_PERSON, this.mPerson);
            Icon icon = this.mVerificationIcon;
            if (icon != null) {
                bundle.putParcelable(Notification.EXTRA_VERIFICATION_ICON, icon);
            }
            CharSequence charSequence = this.mVerificationText;
            if (charSequence != null) {
                bundle.putCharSequence(Notification.EXTRA_VERIFICATION_TEXT, charSequence);
            }
            PendingIntent pendingIntent = this.mAnswerIntent;
            if (pendingIntent != null) {
                bundle.putParcelable(Notification.EXTRA_ANSWER_INTENT, pendingIntent);
            }
            PendingIntent pendingIntent2 = this.mDeclineIntent;
            if (pendingIntent2 != null) {
                bundle.putParcelable(Notification.EXTRA_DECLINE_INTENT, pendingIntent2);
            }
            PendingIntent pendingIntent3 = this.mHangUpIntent;
            if (pendingIntent3 != null) {
                bundle.putParcelable(Notification.EXTRA_HANG_UP_INTENT, pendingIntent3);
            }
            Integer num = this.mAnswerButtonColor;
            if (num != null) {
                bundle.putInt(Notification.EXTRA_ANSWER_COLOR, num.intValue());
            }
            Integer num2 = this.mDeclineButtonColor;
            if (num2 != null) {
                bundle.putInt(Notification.EXTRA_DECLINE_COLOR, num2.intValue());
            }
            fixTitleAndTextExtras(bundle);
        }

        private void fixTitleAndTextExtras(Bundle bundle) {
            Person person = this.mPerson;
            CharSequence name = person != null ? person.getName() : null;
            if (name != null) {
                bundle.putCharSequence(Notification.EXTRA_TITLE, name);
            }
            if (bundle.getCharSequence(Notification.EXTRA_TEXT) == null) {
                bundle.putCharSequence(Notification.EXTRA_TEXT, getDefaultText());
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mCallType = bundle.getInt(Notification.EXTRA_CALL_TYPE);
            this.mIsVideo = bundle.getBoolean(Notification.EXTRA_CALL_IS_VIDEO);
            this.mPerson = (Person) bundle.getParcelable(Notification.EXTRA_CALL_PERSON, Person.class);
            this.mVerificationIcon = (Icon) bundle.getParcelable(Notification.EXTRA_VERIFICATION_ICON, Icon.class);
            this.mVerificationText = bundle.getCharSequence(Notification.EXTRA_VERIFICATION_TEXT);
            this.mAnswerIntent = (PendingIntent) bundle.getParcelable(Notification.EXTRA_ANSWER_INTENT, PendingIntent.class);
            this.mDeclineIntent = (PendingIntent) bundle.getParcelable(Notification.EXTRA_DECLINE_INTENT, PendingIntent.class);
            this.mHangUpIntent = (PendingIntent) bundle.getParcelable(Notification.EXTRA_HANG_UP_INTENT, PendingIntent.class);
            this.mAnswerButtonColor = bundle.containsKey(Notification.EXTRA_ANSWER_COLOR) ? Integer.valueOf(bundle.getInt(Notification.EXTRA_ANSWER_COLOR)) : null;
            this.mDeclineButtonColor = bundle.containsKey(Notification.EXTRA_DECLINE_COLOR) ? Integer.valueOf(bundle.getInt(Notification.EXTRA_DECLINE_COLOR)) : null;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            if (style != null && getClass() == style.getClass()) {
                CallStyle callStyle = (CallStyle) style;
                if (Objects.equals(Integer.valueOf(this.mCallType), Integer.valueOf(callStyle.mCallType)) && Objects.equals(this.mPerson, callStyle.mPerson) && Objects.equals(this.mVerificationText, callStyle.mVerificationText)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class ProgressStyle extends Style {
        private static final int DEFAULT_PROGRESS_MAX = 100;
        private static final String KEY_ELEMENT_COLOR = "colorInt";
        private static final String KEY_ELEMENT_ID = "id";
        private static final String KEY_POINT_POSITION = "position";
        private static final String KEY_SEGMENT_LENGTH = "length";
        private static final int MAX_PROGRESS_POINT_LIMIT = 4;
        private static final int MAX_PROGRESS_SEGMENT_LIMIT = 10;
        private Icon mEndIcon;
        private boolean mIndeterminate;
        private Icon mStartIcon;
        private Icon mTrackerIcon;
        private List<Segment> mProgressSegments = new ArrayList();
        private List<Point> mProgressPoints = new ArrayList();
        private int mProgress = 0;
        private boolean mIsStyledByProgress = true;

        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            if (style != null && getClass() == style.getClass()) {
                ProgressStyle progressStyle = (ProgressStyle) style;
                if (!Objects.equals(Boolean.valueOf(this.mIndeterminate), Boolean.valueOf(progressStyle.mIndeterminate))) {
                    return true;
                }
                boolean z = (this.mIndeterminate || (Objects.equals(Integer.valueOf(this.mProgress), Integer.valueOf(progressStyle.mProgress)) && Objects.equals(Boolean.valueOf(this.mIsStyledByProgress), Boolean.valueOf(progressStyle.mIsStyledByProgress)) && Objects.equals(this.mProgressSegments, progressStyle.mProgressSegments) && Objects.equals(this.mProgressPoints, progressStyle.mProgressPoints) && Objects.equals(this.mTrackerIcon, progressStyle.mTrackerIcon))) ? false : true;
                if (Objects.equals(this.mStartIcon, progressStyle.mStartIcon) && Objects.equals(this.mEndIcon, progressStyle.mEndIcon) && !z) {
                    return false;
                }
            }
            return true;
        }

        public List<Segment> getProgressSegments() {
            return this.mProgressSegments;
        }

        public ProgressStyle setProgressSegments(List<Segment> list) {
            if (this.mProgressSegments == null) {
                this.mProgressSegments = new ArrayList();
            }
            this.mProgressSegments.clear();
            Iterator<Segment> it = list.iterator();
            while (it.hasNext()) {
                addProgressSegment(it.next());
            }
            return this;
        }

        public ProgressStyle addProgressSegment(Segment segment) {
            if (this.mProgressSegments == null) {
                this.mProgressSegments = new ArrayList();
            }
            if (segment.getLength() > 0) {
                this.mProgressSegments.add(segment);
                return this;
            }
            Log.w(Notification.TAG, "Dropped the segment. The length is not a positive integer.");
            return this;
        }

        public List<Point> getProgressPoints() {
            return this.mProgressPoints;
        }

        public ProgressStyle setProgressPoints(List<Point> list) {
            if (this.mProgressPoints == null) {
                this.mProgressPoints = new ArrayList();
            }
            this.mProgressPoints.clear();
            Iterator<Point> it = list.iterator();
            while (it.hasNext()) {
                addProgressPoint(it.next());
            }
            return this;
        }

        public ProgressStyle addProgressPoint(Point point) {
            if (this.mProgressPoints == null) {
                this.mProgressPoints = new ArrayList();
            }
            if (point.getPosition() > 0) {
                this.mProgressPoints.add(point);
                if (this.mProgressPoints.size() > 4) {
                    Log.w(Notification.TAG, "Progress points limit is reached. First4 points will be rendered.");
                }
                return this;
            }
            Log.w(Notification.TAG, "Dropped the point. The position is a negative or zero integer.");
            return this;
        }

        public int getProgress() {
            return this.mProgress;
        }

        public ProgressStyle setProgress(int i) {
            this.mProgress = i;
            return this;
        }

        public int getProgressMax() {
            List<Segment> list = this.mProgressSegments;
            if (list == null || list.isEmpty()) {
                return 100;
            }
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                int length = list.get(i3).getLength();
                if (length > 0) {
                    try {
                        i2 = Math.addExact(i2, length);
                        i++;
                    } catch (ArithmeticException e) {
                        Log.e(Notification.TAG, "Notification.ProgressStyle segment total overflowed.", e);
                        return 100;
                    }
                }
            }
            if (i == 0) {
                return 100;
            }
            return i2;
        }

        public boolean isProgressIndeterminate() {
            return this.mIndeterminate;
        }

        public ProgressStyle setProgressIndeterminate(boolean z) {
            this.mIndeterminate = z;
            return this;
        }

        public boolean isStyledByProgress() {
            return this.mIsStyledByProgress;
        }

        public ProgressStyle setStyledByProgress(boolean z) {
            this.mIsStyledByProgress = z;
            return this;
        }

        public Icon getProgressTrackerIcon() {
            return this.mTrackerIcon;
        }

        public ProgressStyle setProgressTrackerIcon(Icon icon) {
            this.mTrackerIcon = icon;
            return this;
        }

        public Icon getProgressStartIcon() {
            return this.mStartIcon;
        }

        public ProgressStyle setProgressStartIcon(Icon icon) {
            this.mStartIcon = icon;
            return this;
        }

        public Icon getProgressEndIcon() {
            return this.mEndIcon;
        }

        public ProgressStyle setProgressEndIcon(Icon icon) {
            this.mEndIcon = icon;
            return this;
        }

        @Override // android.app.Notification.Style
        public void purgeResources() {
            super.purgeResources();
            Icon icon = this.mTrackerIcon;
            if (icon != null) {
                icon.convertToAshmem();
            }
            Icon icon2 = this.mStartIcon;
            if (icon2 != null) {
                icon2.convertToAshmem();
            }
            Icon icon3 = this.mEndIcon;
            if (icon3 != null) {
                icon3.convertToAshmem();
            }
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(Context context) {
            super.reduceImageSizes(context);
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_progress_icon_size);
            Icon icon = this.mStartIcon;
            if (icon != null) {
                icon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
            }
            Icon icon2 = this.mEndIcon;
            if (icon2 != null) {
                icon2.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
            }
            if (this.mTrackerIcon != null) {
                this.mTrackerIcon.scaleDownIfNecessary(resources.getDimensionPixelSize(R.dimen.notification_progress_tracker_width), resources.getDimensionPixelSize(R.dimen.notification_progress_tracker_height));
            }
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            bundle.putParcelableArrayList(Notification.EXTRA_PROGRESS_SEGMENTS, getProgressSegmentsAsBundleList(this.mProgressSegments));
            bundle.putParcelableArrayList(Notification.EXTRA_PROGRESS_POINTS, getProgressPointsAsBundleList(this.mProgressPoints));
            bundle.putInt(Notification.EXTRA_PROGRESS, this.mProgress);
            bundle.putBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE, this.mIndeterminate);
            bundle.putInt(Notification.EXTRA_PROGRESS_MAX, getProgressMax());
            bundle.putBoolean(Notification.EXTRA_STYLED_BY_PROGRESS, this.mIsStyledByProgress);
            Icon icon = this.mTrackerIcon;
            if (icon != null) {
                bundle.putParcelable(Notification.EXTRA_PROGRESS_TRACKER_ICON, icon);
            } else {
                bundle.remove(Notification.EXTRA_PROGRESS_TRACKER_ICON);
            }
            Icon icon2 = this.mStartIcon;
            if (icon2 != null) {
                bundle.putParcelable(Notification.EXTRA_PROGRESS_START_ICON, icon2);
            } else {
                bundle.remove(Notification.EXTRA_PROGRESS_START_ICON);
            }
            Icon icon3 = this.mEndIcon;
            if (icon3 != null) {
                bundle.putParcelable(Notification.EXTRA_PROGRESS_END_ICON, icon3);
            } else {
                bundle.remove(Notification.EXTRA_PROGRESS_END_ICON);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mProgressSegments = getProgressSegmentsFromBundleList(bundle.getParcelableArrayList(Notification.EXTRA_PROGRESS_SEGMENTS, Bundle.class));
            this.mProgress = bundle.getInt(Notification.EXTRA_PROGRESS, 0);
            this.mIndeterminate = bundle.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE, false);
            this.mIsStyledByProgress = bundle.getBoolean(Notification.EXTRA_STYLED_BY_PROGRESS, true);
            this.mTrackerIcon = (Icon) bundle.getParcelable(Notification.EXTRA_PROGRESS_TRACKER_ICON, Icon.class);
            this.mStartIcon = (Icon) bundle.getParcelable(Notification.EXTRA_PROGRESS_START_ICON, Icon.class);
            this.mEndIcon = (Icon) bundle.getParcelable(Notification.EXTRA_PROGRESS_END_ICON, Icon.class);
            this.mProgressPoints = getProgressPointsFromBundleList(bundle.getParcelableArrayList(Notification.EXTRA_PROGRESS_POINTS, Bundle.class));
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView() {
            return getStandardView(this.mBuilder.getCollapsedBaseLayoutResource(), this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).hideProgress(true).fillTextsFrom(this.mBuilder), null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            return getStandardView(this.mBuilder.getHeadsUpBaseLayoutResource(), this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).hideProgress(true).fillTextsFrom(this.mBuilder), null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).allowTextWithProgress(true).hideProgress(true).fillTextsFrom(this.mBuilder);
            RemoteViews standardView = getStandardView(this.mBuilder.getProgressLayoutResource(), fillTextsFrom, null);
            if (this.mStartIcon != null) {
                standardView.setViewVisibility(R.id.notification_progress_start_icon, 0);
                standardView.setImageViewIcon(R.id.notification_progress_start_icon, this.mStartIcon);
            } else {
                standardView.setViewVisibility(R.id.notification_progress_start_icon, 8);
            }
            if (this.mEndIcon != null) {
                standardView.setViewVisibility(R.id.notification_progress_end_icon, 0);
                standardView.setImageViewIcon(R.id.notification_progress_end_icon, this.mEndIcon);
            } else {
                standardView.setViewVisibility(R.id.notification_progress_end_icon, 8);
            }
            standardView.setViewVisibility(16908301, 0);
            standardView.setBundle(16908301, "setProgressModel", createProgressModel(this.mBuilder.getPrimaryAccentColor(fillTextsFrom), this.mBuilder.getColors(fillTextsFrom).getBackgroundColor()).toBundle());
            standardView.setIcon(16908301, "setProgressTrackerIcon", this.mTrackerIcon);
            return standardView;
        }

        public static ArrayList<Bundle> getProgressSegmentsAsBundleList(List<Segment> list) {
            ArrayList<Bundle> arrayList = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Segment segment = list.get(i);
                    if (segment.getLength() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("length", segment.getLength());
                        bundle.putInt("id", segment.getId());
                        bundle.putInt(KEY_ELEMENT_COLOR, segment.getColor());
                        arrayList.add(bundle);
                    }
                }
            }
            return arrayList;
        }

        public static List<Segment> getProgressSegmentsFromBundleList(List<Bundle> list) {
            ArrayList arrayList = new ArrayList();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Bundle bundle = list.get(i);
                    int i2 = bundle.getInt("length");
                    if (i2 > 0) {
                        arrayList.add(new Segment(i2).setId(bundle.getInt("id")).setColor(bundle.getInt(KEY_ELEMENT_COLOR, 0)));
                    }
                }
            }
            return arrayList;
        }

        public static ArrayList<Bundle> getProgressPointsAsBundleList(List<Point> list) {
            ArrayList<Bundle> arrayList = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Point point = list.get(i);
                    if (point.getPosition() >= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", point.getPosition());
                        bundle.putInt("id", point.getId());
                        bundle.putInt(KEY_ELEMENT_COLOR, point.getColor());
                        arrayList.add(bundle);
                    }
                }
            }
            return arrayList;
        }

        public static List<Point> getProgressPointsFromBundleList(List<Bundle> list) {
            ArrayList arrayList = new ArrayList();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Bundle bundle = list.get(i);
                    int i2 = bundle.getInt("position");
                    if (i2 >= 0) {
                        arrayList.add(new Point(i2).setId(bundle.getInt("id")).setColor(bundle.getInt(KEY_ELEMENT_COLOR, 0)));
                    }
                }
            }
            return arrayList;
        }

        public NotificationProgressModel createProgressModel(int i, int i2) {
            int i3;
            boolean z;
            int i4;
            int i5;
            int i6;
            if (this.mIndeterminate) {
                return new NotificationProgressModel(sanitizeProgressColor(!this.mProgressSegments.isEmpty() ? this.mProgressSegments.get(0).mColor : i, i2, i));
            }
            ArrayList arrayList = new ArrayList();
            Iterator<Segment> it = this.mProgressSegments.iterator();
            int i7 = 0;
            while (true) {
                i3 = 100;
                if (!it.hasNext()) {
                    break;
                }
                Segment next = it.next();
                int length = next.getLength();
                if (length > 0) {
                    try {
                        i7 = Math.addExact(i7, length);
                        arrayList.add(sanitizeSegment(next, i2, i));
                    } catch (ArithmeticException unused) {
                        arrayList.clear();
                        i7 = 100;
                    }
                }
            }
            int i8 = 1;
            if (arrayList.isEmpty()) {
                arrayList.add(sanitizeSegment(new Segment(100), i2, i));
            } else {
                if (arrayList.size() > 10) {
                    int color = ((Segment) arrayList.getFirst()).getColor();
                    int i9 = 1;
                    while (true) {
                        if (i9 >= arrayList.size()) {
                            z = true;
                            break;
                        }
                        if (((Segment) arrayList.get(i9)).getColor() != color) {
                            z = false;
                            break;
                        }
                        i9++;
                    }
                    Segment segment = new Segment(i7);
                    if (!z) {
                        color = 0;
                    }
                    segment.setColor(color);
                    arrayList.clear();
                    arrayList.add(sanitizeSegment(segment, i2, i));
                }
                i3 = i7;
            }
            ArrayList arrayList2 = new ArrayList();
            for (Point point : this.mProgressPoints) {
                int position = point.getPosition();
                if (position > 0 && position < i3) {
                    arrayList2.add(sanitizePoint(point, i2, i));
                    if (arrayList2.size() == 4) {
                        break;
                    }
                }
            }
            if (arrayList.size() <= 1) {
                i6 = 0;
                i5 = 0;
            } else {
                int color2 = ((Segment) arrayList.getFirst()).getColor();
                while (true) {
                    if (i8 >= arrayList.size()) {
                        i4 = 0;
                        break;
                    }
                    if (((Segment) arrayList.get(i8)).getColor() != color2) {
                        i4 = sanitizeProgressColor(0, i2, i);
                        break;
                    }
                    i8++;
                }
                i5 = i4;
                i6 = 0;
            }
            return new NotificationProgressModel(arrayList, arrayList2, Math.clamp(this.mProgress, i6, i3), this.mIsStyledByProgress, i5);
        }

        private Segment sanitizeSegment(Segment segment, int i, int i2) {
            return new Segment(segment.getLength()).setId(segment.getId()).setColor(sanitizeProgressColor(segment.getColor(), i, i2));
        }

        private Point sanitizePoint(Point point, int i, int i2) {
            return new Point(point.getPosition()).setId(point.getId()).setColor(sanitizeProgressColor(point.getColor(), i, i2));
        }

        public static int sanitizeProgressColor(int i, int i2, int i3) {
            if (Color.alpha(i) == 0) {
                i = i3;
            }
            return Builder.ensureColorContrast(i, i2, 3.0d);
        }

        public static final class Segment {
            private int mLength;
            private int mId = 0;
            private int mColor = 0;

            public Segment(int i) {
                this.mLength = i;
            }

            public int getLength() {
                return this.mLength;
            }

            public int getId() {
                return this.mId;
            }

            public Segment setId(int i) {
                this.mId = i;
                return this;
            }

            public int getColor() {
                return this.mColor;
            }

            public Segment setColor(int i) {
                this.mColor = i;
                return this;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj != null && getClass() == obj.getClass()) {
                    Segment segment = (Segment) obj;
                    if (this.mLength == segment.mLength && this.mId == segment.mId && this.mColor == segment.mColor) {
                        return true;
                    }
                }
                return false;
            }

            public int hashCode() {
                return Objects.hash(Integer.valueOf(this.mLength), Integer.valueOf(this.mId), Integer.valueOf(this.mColor));
            }
        }

        public static final class Point {
            private int mColor = 0;
            private int mId;
            private int mPosition;

            public Point(int i) {
                this.mPosition = i;
            }

            public int getPosition() {
                return this.mPosition;
            }

            public int getId() {
                return this.mId;
            }

            public Point setId(int i) {
                this.mId = i;
                return this;
            }

            public int getColor() {
                return this.mColor;
            }

            public Point setColor(int i) {
                this.mColor = i;
                return this;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj != null && getClass() == obj.getClass()) {
                    Point point = (Point) obj;
                    if (this.mPosition == point.mPosition && this.mId == point.mId && this.mColor == point.mColor) {
                        return true;
                    }
                }
                return false;
            }

            public int hashCode() {
                return Objects.hash(Integer.valueOf(this.mPosition), Integer.valueOf(this.mId), Integer.valueOf(this.mColor));
            }
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView() {
            return makeStandardTemplateWithCustomContent(this.mBuilder.mN.contentView);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            return makeDecoratedExpandedContentView();
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            return makeDecoratedHeadsUpContentView();
        }

        private RemoteViews makeDecoratedHeadsUpContentView() {
            RemoteViews remoteViews;
            if (this.mBuilder.mN.headsUpContentView == null) {
                remoteViews = this.mBuilder.mN.contentView;
            } else {
                remoteViews = this.mBuilder.mN.headsUpContentView;
            }
            if (remoteViews == null) {
                return null;
            }
            if (this.mBuilder.mActions.size() == 0) {
                return makeStandardTemplateWithCustomContent(remoteViews);
            }
            TemplateBindResult templateBindResult = new TemplateBindResult();
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).decorationType(2).fillTextsFrom(this.mBuilder);
            RemoteViews applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getHeadsUpBaseLayoutResource(), fillTextsFrom, templateBindResult);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplateWithActions;
        }

        private RemoteViews makeStandardTemplateWithCustomContent(RemoteViews remoteViews) {
            if (remoteViews == null) {
                return null;
            }
            TemplateBindResult templateBindResult = new TemplateBindResult();
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).decorationType(2).fillTextsFrom(this.mBuilder);
            RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(this.mBuilder.getCollapsedBaseLayoutResource(), fillTextsFrom, templateBindResult);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }

        private RemoteViews makeDecoratedExpandedContentView() {
            RemoteViews remoteViews;
            if (this.mBuilder.mN.bigContentView == null) {
                remoteViews = this.mBuilder.mN.contentView;
            } else {
                remoteViews = this.mBuilder.mN.bigContentView;
            }
            if (remoteViews == null) {
                return null;
            }
            TemplateBindResult templateBindResult = new TemplateBindResult();
            StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_EXPANDED).decorationType(2).fillTextsFrom(this.mBuilder);
            RemoteViews applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getExpandedBaseLayoutResource(), fillTextsFrom, templateBindResult);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplateWithActions;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            return style == null || getClass() != style.getClass();
        }
    }

    public static class OngoingActivityStyle extends Style {
        public static final int NOW_BAR_EXPANDABLE_TYPE_FULL = 2;
        public static final int NOW_BAR_EXPANDABLE_TYPE_HALF = 1;
        public static final int NOW_BAR_EXPANDABLE_TYPE_NONE = 0;
        private static final String TAG = "OngoingActivityStyle";
        private Icon mBadge;
        private int mCardBackground;
        private Icon mCardIcon;
        private int mChipBackground;
        private Icon mChipIcon;
        private boolean mChronometerCountDown;
        private float mChronometerSpeed;
        private boolean mChronometerStart;
        private RemoteViews mCustomCardViewCenterUI;
        private RemoteViews mCustomExpandedCardView;
        private CharSequence mExpandedChipText;
        private RemoteViews mExpandedChipView;
        private RemoteViews mExpandedNowBarView;
        private CharSequence mMoreInfo;
        private CharSequence mPrimaryInfo;
        private CharSequence mSecondaryInfo;
        private ArrayList<Action> mActions = new ArrayList<>(3);
        private ArrayList<Integer> mActionBgColors = new ArrayList<>(3);
        private int mNowBarExpandableType = 0;
        private int mPrimaryActionNum = 0;
        private Long mChronometerBase = 0L;
        private String mChronometerFormat = "";

        @Retention(RetentionPolicy.SOURCE)
        public @interface NowBarExpandableType {
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            return true;
        }

        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return false;
        }

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        public OngoingActivityStyle() {
            Log.i(TAG, "Constructor");
        }

        public OngoingActivityStyle setChipIcon(Icon icon) {
            this.mChipIcon = icon;
            printLog("setChipIcon");
            return this;
        }

        public OngoingActivityStyle setExpandedChipView(RemoteViews remoteViews) {
            this.mExpandedChipView = remoteViews;
            printLog("setExpandedChipView");
            return this;
        }

        public OngoingActivityStyle setChipBackground(int i) {
            this.mChipBackground = i;
            printLog("setChipBackground");
            return this;
        }

        public OngoingActivityStyle addAction(Action action) {
            addAction(action, null);
            return this;
        }

        public OngoingActivityStyle addAction(Action action, Integer num) {
            if (action != null) {
                this.mActions.add(action);
            }
            if (num != null) {
                this.mActionBgColors.add(num);
            } else {
                this.mActionBgColors.add(1);
            }
            printLog("addAction");
            return this;
        }

        public OngoingActivityStyle setPrimaryActionNumber(int i) {
            this.mPrimaryActionNum = i;
            printLog("setPrimaryActionNumber");
            return this;
        }

        public OngoingActivityStyle setCardIcon(Icon icon) {
            this.mCardIcon = icon;
            printLog("setCardIcon");
            return this;
        }

        public OngoingActivityStyle setBadge(Icon icon) {
            this.mBadge = icon;
            printLog("setBadge");
            return this;
        }

        public OngoingActivityStyle setPrimaryInfo(CharSequence charSequence) {
            this.mPrimaryInfo = charSequence;
            printLog("setPrimaryInfo");
            return this;
        }

        public OngoingActivityStyle setSecondaryInfo(CharSequence charSequence) {
            this.mSecondaryInfo = charSequence;
            printLog("setSecondaryInfo");
            return this;
        }

        public OngoingActivityStyle setMoreInfo(CharSequence charSequence) {
            this.mMoreInfo = charSequence;
            printLog("setMoreInfo");
            return this;
        }

        public OngoingActivityStyle setCustomCardViewCenterUI(RemoteViews remoteViews) {
            this.mCustomCardViewCenterUI = remoteViews;
            printLog("mCustomCardViewCenterUI");
            return this;
        }

        public OngoingActivityStyle setCustomExpandedCardView(RemoteViews remoteViews) {
            this.mCustomExpandedCardView = remoteViews;
            printLog("mCustomExpandedCardView");
            return this;
        }

        public OngoingActivityStyle setCardBackground(int i) {
            this.mCardBackground = i;
            printLog("setCardBackground");
            return this;
        }

        public OngoingActivityStyle setExpandedNowBarView(RemoteViews remoteViews, int i) {
            this.mExpandedNowBarView = remoteViews;
            this.mNowBarExpandableType = i;
            printLog("setExpandedNowBarView");
            return this;
        }

        public OngoingActivityStyle setExpandedChipText(CharSequence charSequence) {
            this.mExpandedChipText = charSequence;
            printLog("setExpandedChipText");
            return this;
        }

        public OngoingActivityStyle setChronometerBase(Long l) {
            this.mChronometerBase = l;
            printLog("setChronometerBase");
            return this;
        }

        public OngoingActivityStyle setChronometerFormat(String str) {
            this.mChronometerFormat = str;
            printLog("setChronometerFormat");
            return this;
        }

        public OngoingActivityStyle setChronometerCountDown(boolean z) {
            this.mChronometerCountDown = z;
            printLog("setChronometerCountDown");
            return this;
        }

        public OngoingActivityStyle setChronometerStart(boolean z) {
            this.mChronometerStart = z;
            printLog("setChronometerStart");
            return this;
        }

        public OngoingActivityStyle setChronometerSpeed(float f) {
            this.mChronometerSpeed = f;
            printLog("setChronometerSpeed");
            return this;
        }

        private void printLog(String str) {
            String str2;
            String str3 = TAG;
            if ((str + ": Icon = " + this.mChipIcon + ", mExpandedChipView = " + this.mExpandedChipView + ", mExpandedChipText = " + ((Object) this.mExpandedChipText) + ", mChipBackground = " + this.mChipBackground + ", mActions = " + this.mActions) != null) {
                str2 = Integer.toString(this.mActions.size());
            } else {
                if (("null, mActionBgColor = " + this.mActionBgColors) != null) {
                    str2 = Integer.toString(this.mActionBgColors.size());
                } else {
                    str2 = "null, mPrimaryActionNum = " + this.mPrimaryActionNum + ", mCardIcon = " + this.mCardIcon + ", mBadge = " + this.mBadge + ", mPrimaryInfo = " + ((Object) this.mPrimaryInfo) + ", mSecondaryInfo = " + ((Object) this.mSecondaryInfo) + ", mMoreInfo = " + ((Object) this.mMoreInfo) + ", mCustomCardViewCenterUI = " + this.mCustomCardViewCenterUI + ", mCustomExpandedCardView = " + this.mCustomExpandedCardView + ", mCardBackground = " + this.mCardBackground + ", mExpandedNowBarView = " + this.mExpandedNowBarView + ", mNowBarExpandableType = " + this.mNowBarExpandableType + ", mChronometerBase = " + this.mChronometerBase + ", mChronometerFormat = " + this.mChronometerFormat + ", mChronometerCountDown = " + this.mChronometerCountDown + ", mChronometerStart = " + this.mChronometerStart + ", mChronometerSpeed = " + this.mChronometerSpeed;
                }
            }
            Log.i(str3, str2);
        }

        @Override // android.app.Notification.Style
        public Notification buildStyled(Notification notification) {
            Notification buildStyled = super.buildStyled(notification);
            this.mBuilder.mActions = this.mActions;
            buildStyled.actions = new Action[this.mBuilder.mActions.size()];
            this.mBuilder.mActions.toArray(buildStyled.actions);
            return buildStyled;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle bundle) {
            super.addExtras(bundle);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CHIP_ICON, this.mChipIcon);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_EXPANDED_CHIP_VIEW, this.mExpandedChipView);
            bundle.putInt(Notification.EXTRA_ONGOING_ACTIVITY_CHIP_BACKGROUND, this.mChipBackground);
            bundle.putIntegerArrayList(Notification.EXTRA_ONGOING_ACTIVITY_ACTION_BG_COLOR, this.mActionBgColors);
            bundle.putInt(Notification.EXTRA_ONGOING_ACTIVITY_PRIMARY_ACTION, this.mPrimaryActionNum);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CARD_ICON, this.mCardIcon);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_BADGE, this.mBadge);
            bundle.putCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_PRIMARY_INFO, this.mPrimaryInfo);
            bundle.putCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_SECONDARY_INFO, this.mSecondaryInfo);
            bundle.putCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_MORE_INFO, this.mMoreInfo);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CUSTOM_CARD_VIEW_CENTER_UI, this.mCustomCardViewCenterUI);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CUSTOM_EXPANDED_CARD_VIEW, this.mCustomExpandedCardView);
            bundle.putInt(Notification.EXTRA_ONGOING_ACTIVITY_CARD_BACKGROUND, this.mCardBackground);
            bundle.putParcelable(Notification.EXTRA_ONGOING_ACTIVITY_EXPANDED_NOW_BAR_VIEW, this.mExpandedNowBarView);
            bundle.putInt(Notification.EXTRA_ONGOING_ACTIVITY_NOW_BAR_EXPANDABLE_TYPE, this.mNowBarExpandableType);
            bundle.putCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_EXPANDED_CHIP_TEXT, this.mExpandedChipText);
            bundle.putLong(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_BASE, this.mChronometerBase.longValue());
            bundle.putString(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_FORMAT, this.mChronometerFormat);
            bundle.putBoolean(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_COUNTDOWN, this.mChronometerCountDown);
            bundle.putBoolean(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_START, this.mChronometerStart);
            bundle.putFloat(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_SPEED, this.mChronometerSpeed);
            printLog("addExtras");
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mChipIcon = (Icon) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CHIP_ICON, Icon.class);
            this.mExpandedChipView = (RemoteViews) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_EXPANDED_CHIP_VIEW, RemoteViews.class);
            this.mChipBackground = bundle.getInt(Notification.EXTRA_ONGOING_ACTIVITY_CHIP_BACKGROUND);
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(Notification.EXTRA_ONGOING_ACTIVITY_ACTION_BG_COLOR);
            if (integerArrayList != null) {
                this.mActionBgColors = integerArrayList;
            }
            this.mPrimaryActionNum = bundle.getInt(Notification.EXTRA_ONGOING_ACTIVITY_PRIMARY_ACTION);
            this.mCardIcon = (Icon) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CARD_ICON, Icon.class);
            this.mBadge = (Icon) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_BADGE, Icon.class);
            this.mPrimaryInfo = bundle.getCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_PRIMARY_INFO);
            this.mSecondaryInfo = bundle.getCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_SECONDARY_INFO);
            this.mMoreInfo = bundle.getCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_MORE_INFO);
            this.mCustomCardViewCenterUI = (RemoteViews) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CUSTOM_CARD_VIEW_CENTER_UI, RemoteViews.class);
            this.mCustomExpandedCardView = (RemoteViews) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_CUSTOM_EXPANDED_CARD_VIEW, RemoteViews.class);
            this.mCardBackground = bundle.getInt(Notification.EXTRA_ONGOING_ACTIVITY_CARD_BACKGROUND);
            this.mExpandedNowBarView = (RemoteViews) bundle.getParcelable(Notification.EXTRA_ONGOING_ACTIVITY_EXPANDED_NOW_BAR_VIEW, RemoteViews.class);
            this.mNowBarExpandableType = bundle.getInt(Notification.EXTRA_ONGOING_ACTIVITY_NOW_BAR_EXPANDABLE_TYPE);
            this.mExpandedChipText = bundle.getCharSequence(Notification.EXTRA_ONGOING_ACTIVITY_EXPANDED_CHIP_TEXT);
            this.mChronometerBase = Long.valueOf(bundle.getLong(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_BASE, 0L));
            this.mChronometerFormat = bundle.getString(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_FORMAT, "");
            this.mChronometerCountDown = bundle.getBoolean(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_COUNTDOWN, false);
            this.mChronometerStart = bundle.getBoolean(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_START, false);
            this.mChronometerSpeed = bundle.getFloat(Notification.EXTRA_ONGOING_ACTIVITY_CHRONOMETER_SPEED, 1.0f);
        }
    }

    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public RemoteViews makeContentView() {
            return makeMediaContentView(this.mBuilder.mN.contentView);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public RemoteViews makeExpandedContentView() {
            RemoteViews remoteViews;
            if (this.mBuilder.mN.bigContentView != null) {
                remoteViews = this.mBuilder.mN.bigContentView;
            } else {
                remoteViews = this.mBuilder.mN.contentView;
            }
            return makeMediaExpandedContentView(remoteViews);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView() {
            RemoteViews remoteViews;
            if (this.mBuilder.mN.headsUpContentView != null) {
                remoteViews = this.mBuilder.mN.headsUpContentView;
            } else {
                remoteViews = this.mBuilder.mN.contentView;
            }
            return makeMediaExpandedContentView(remoteViews);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style style) {
            return style == null || getClass() != style.getClass();
        }
    }

    public static final class BubbleMetadata implements Parcelable {
        public static final Parcelable.Creator<BubbleMetadata> CREATOR = new Parcelable.Creator<BubbleMetadata>() { // from class: android.app.Notification.BubbleMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BubbleMetadata createFromParcel(Parcel parcel) {
                return new BubbleMetadata(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BubbleMetadata[] newArray(int i) {
                return new BubbleMetadata[i];
            }
        };
        public static final int FLAG_AUTO_EXPAND_BUBBLE = 1;
        public static final int FLAG_SUPPRESSABLE_BUBBLE = 4;
        public static final int FLAG_SUPPRESS_BUBBLE = 8;
        public static final int FLAG_SUPPRESS_NOTIFICATION = 2;
        private PendingIntent mDeleteIntent;
        private int mDesiredHeight;
        private int mDesiredHeightResId;
        private int mFlags;
        private Icon mIcon;
        private PendingIntent mPendingIntent;
        private String mShortcutId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private BubbleMetadata(PendingIntent pendingIntent, PendingIntent pendingIntent2, Icon icon, int i, int i2, String str) {
            this.mPendingIntent = pendingIntent;
            this.mIcon = icon;
            this.mDesiredHeight = i;
            this.mDesiredHeightResId = i2;
            this.mDeleteIntent = pendingIntent2;
            this.mShortcutId = str;
        }

        private BubbleMetadata(Parcel parcel) {
            if (parcel.readInt() != 0) {
                this.mPendingIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.mIcon = Icon.CREATOR.createFromParcel(parcel);
            }
            this.mDesiredHeight = parcel.readInt();
            this.mFlags = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mDeleteIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            }
            this.mDesiredHeightResId = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mShortcutId = parcel.readString8();
            }
        }

        public String getShortcutId() {
            return this.mShortcutId;
        }

        public PendingIntent getIntent() {
            return this.mPendingIntent;
        }

        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        public int getDesiredHeight() {
            return this.mDesiredHeight;
        }

        public int getDesiredHeightResId() {
            return this.mDesiredHeightResId;
        }

        public boolean getAutoExpandBubble() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isNotificationSuppressed() {
            return (this.mFlags & 2) != 0;
        }

        public boolean isBubbleSuppressable() {
            return (this.mFlags & 4) != 0;
        }

        public boolean isBubbleSuppressed() {
            return (this.mFlags & 8) != 0;
        }

        public void setSuppressNotification(boolean z) {
            if (z) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
        }

        public void setSuppressBubble(boolean z) {
            if (z) {
                this.mFlags |= 8;
            } else {
                this.mFlags &= -9;
            }
        }

        public void setFlags(int i) {
            this.mFlags = i;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mPendingIntent != null ? 1 : 0);
            PendingIntent pendingIntent = this.mPendingIntent;
            if (pendingIntent != null) {
                pendingIntent.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mIcon != null ? 1 : 0);
            Icon icon = this.mIcon;
            if (icon != null) {
                icon.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mDesiredHeight);
            parcel.writeInt(this.mFlags);
            parcel.writeInt(this.mDeleteIntent != null ? 1 : 0);
            PendingIntent pendingIntent2 = this.mDeleteIntent;
            if (pendingIntent2 != null) {
                pendingIntent2.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mDesiredHeightResId);
            parcel.writeInt(!TextUtils.isEmpty(this.mShortcutId) ? 1 : 0);
            if (TextUtils.isEmpty(this.mShortcutId)) {
                return;
            }
            parcel.writeString8(this.mShortcutId);
        }

        public static final class Builder {
            private PendingIntent mDeleteIntent;
            private int mDesiredHeight;
            private int mDesiredHeightResId;
            private int mFlags;
            private Icon mIcon;
            private PendingIntent mPendingIntent;
            private String mShortcutId;

            @Deprecated
            public Builder() {
            }

            public Builder(String str) {
                if (TextUtils.isEmpty(str)) {
                    throw new NullPointerException("Bubble requires a non-null shortcut id");
                }
                this.mShortcutId = str;
            }

            public Builder(PendingIntent pendingIntent, Icon icon) {
                if (pendingIntent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
                if (icon == null) {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
                if (icon.getType() != 6 && icon.getType() != 4) {
                    Log.w(Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mPendingIntent = pendingIntent;
                this.mIcon = icon;
            }

            public Builder setIntent(PendingIntent pendingIntent) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set a PendingIntent. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (pendingIntent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
                this.mPendingIntent = pendingIntent;
                return this;
            }

            public Builder setIcon(Icon icon) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set an Icon. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (icon == null) {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
                if (icon.getType() != 6 && icon.getType() != 4) {
                    Log.w(Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mIcon = icon;
                return this;
            }

            public Builder setDesiredHeight(int i) {
                this.mDesiredHeight = Math.max(i, 0);
                this.mDesiredHeightResId = 0;
                return this;
            }

            public Builder setDesiredHeightResId(int i) {
                this.mDesiredHeightResId = i;
                this.mDesiredHeight = 0;
                return this;
            }

            public Builder setAutoExpandBubble(boolean z) {
                setFlag(1, z);
                return this;
            }

            public Builder setSuppressNotification(boolean z) {
                setFlag(2, z);
                return this;
            }

            public Builder setSuppressableBubble(boolean z) {
                setFlag(4, z);
                return this;
            }

            public Builder setDeleteIntent(PendingIntent pendingIntent) {
                this.mDeleteIntent = pendingIntent;
                return this;
            }

            public BubbleMetadata build() {
                String str = this.mShortcutId;
                if (str == null && this.mPendingIntent == null) {
                    throw new NullPointerException("Must supply pending intent or shortcut to bubble");
                }
                if (str == null && this.mIcon == null) {
                    throw new NullPointerException("Must supply an icon or shortcut for the bubble");
                }
                BubbleMetadata bubbleMetadata = new BubbleMetadata(this.mPendingIntent, this.mDeleteIntent, this.mIcon, this.mDesiredHeight, this.mDesiredHeightResId, this.mShortcutId);
                bubbleMetadata.setFlags(this.mFlags);
                return bubbleMetadata;
            }

            public Builder setFlag(int i, boolean z) {
                if (z) {
                    this.mFlags = i | this.mFlags;
                    return this;
                }
                this.mFlags = (~i) & this.mFlags;
                return this;
            }
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";

        @Deprecated
        public static final int SCREEN_TIMEOUT_LONG = -1;

        @Deprecated
        public static final int SCREEN_TIMEOUT_SHORT = 0;

        @Deprecated
        public static final int SIZE_DEFAULT = 0;

        @Deprecated
        public static final int SIZE_FULL_SCREEN = 5;

        @Deprecated
        public static final int SIZE_LARGE = 4;

        @Deprecated
        public static final int SIZE_MEDIUM = 3;

        @Deprecated
        public static final int SIZE_SMALL = 2;

        @Deprecated
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions;
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex;
        private int mContentIcon;
        private int mContentIconGravity;
        private int mCustomContentHeight;
        private int mCustomSizePreset;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags;
        private int mGravity;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages;

        public WearableExtender() {
            this.mActions = new ArrayList<>();
            this.mFlags = 1;
            this.mPages = new ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
        }

        public WearableExtender(Notification notification) {
            this.mActions = new ArrayList<>();
            this.mFlags = 1;
            this.mPages = new ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
            Bundle bundle = notification.extras.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (bundle != null) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("actions", Action.class);
                if (parcelableArrayList != null) {
                    this.mActions.addAll(parcelableArrayList);
                }
                this.mFlags = bundle.getInt("flags", 1);
                this.mDisplayIntent = (PendingIntent) bundle.getParcelable(KEY_DISPLAY_INTENT, PendingIntent.class);
                Notification[] notificationArr = (Notification[]) Notification.getParcelableArrayFromBundle(bundle, KEY_PAGES, Notification.class);
                if (notificationArr != null) {
                    Collections.addAll(this.mPages, notificationArr);
                }
                this.mBackground = (Bitmap) bundle.getParcelable("background", Bitmap.class);
                this.mContentIcon = bundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = bundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = bundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = bundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = bundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = bundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = bundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = bundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = bundle.getString(KEY_BRIDGE_TAG);
            }
        }

        @Override // android.app.Notification.Extender
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                bundle.putParcelableArrayList("actions", this.mActions);
            }
            int i = this.mFlags;
            if (i != 1) {
                bundle.putInt("flags", i);
            }
            PendingIntent pendingIntent = this.mDisplayIntent;
            if (pendingIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, pendingIntent);
            }
            if (!this.mPages.isEmpty()) {
                ArrayList<Notification> arrayList = this.mPages;
                bundle.putParcelableArray(KEY_PAGES, (Parcelable[]) arrayList.toArray(new Notification[arrayList.size()]));
            }
            if (this.mBackground != null) {
                if (CompatChanges.isChangeEnabled(Notification.WEARABLE_EXTENDER_BACKGROUND_BLOCKED)) {
                    Log.d(Notification.TAG, "Use of background in WearableExtenders has been deprecated and will not be populated anymore.");
                } else {
                    bundle.putParcelable("background", this.mBackground);
                }
            }
            int i2 = this.mContentIcon;
            if (i2 != 0) {
                bundle.putInt(KEY_CONTENT_ICON, i2);
            }
            int i3 = this.mContentIconGravity;
            if (i3 != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, i3);
            }
            int i4 = this.mContentActionIndex;
            if (i4 != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, i4);
            }
            int i5 = this.mCustomSizePreset;
            if (i5 != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, i5);
            }
            int i6 = this.mCustomContentHeight;
            if (i6 != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, i6);
            }
            int i7 = this.mGravity;
            if (i7 != 80) {
                bundle.putInt(KEY_GRAVITY, i7);
            }
            int i8 = this.mHintScreenTimeout;
            if (i8 != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, i8);
            }
            String str = this.mDismissalId;
            if (str != null) {
                bundle.putString(KEY_DISMISSAL_ID, str);
            }
            String str2 = this.mBridgeTag;
            if (str2 != null) {
                bundle.putString(KEY_BRIDGE_TAG, str2);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public WearableExtender m472clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList<>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList<>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        public WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        @Deprecated
        public WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        @Deprecated
        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        @Deprecated
        public WearableExtender addPage(Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        @Deprecated
        public WearableExtender addPages(List<Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        @Deprecated
        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        @Deprecated
        public List<Notification> getPages() {
            return this.mPages;
        }

        @Deprecated
        public WearableExtender setBackground(Bitmap bitmap) {
            if (CompatChanges.isChangeEnabled(Notification.WEARABLE_EXTENDER_BACKGROUND_BLOCKED)) {
                Log.d(Notification.TAG, "Use of background in WearableExtenders has been deprecated and will not be populated anymore.");
                return this;
            }
            this.mBackground = bitmap;
            return this;
        }

        @Deprecated
        public Bitmap getBackground() {
            Log.w(Notification.TAG, "Use of background in WearableExtender has been removed, returning null.");
            return this.mBackground;
        }

        @Deprecated
        public WearableExtender setContentIcon(int i) {
            this.mContentIcon = i;
            return this;
        }

        @Deprecated
        public int getContentIcon() {
            return this.mContentIcon;
        }

        @Deprecated
        public WearableExtender setContentIconGravity(int i) {
            this.mContentIconGravity = i;
            return this;
        }

        @Deprecated
        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public WearableExtender setContentAction(int i) {
            this.mContentActionIndex = i;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        @Deprecated
        public WearableExtender setGravity(int i) {
            this.mGravity = i;
            return this;
        }

        @Deprecated
        public int getGravity() {
            return this.mGravity;
        }

        @Deprecated
        public WearableExtender setCustomSizePreset(int i) {
            this.mCustomSizePreset = i;
            return this;
        }

        @Deprecated
        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        @Deprecated
        public WearableExtender setCustomContentHeight(int i) {
            this.mCustomContentHeight = i;
            return this;
        }

        @Deprecated
        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public WearableExtender setStartScrollBottom(boolean z) {
            setFlag(8, z);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean z) {
            setFlag(1, z);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        @Deprecated
        public WearableExtender setHintHideIcon(boolean z) {
            setFlag(2, z);
            return this;
        }

        @Deprecated
        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        @Deprecated
        public WearableExtender setHintShowBackgroundOnly(boolean z) {
            setFlag(4, z);
            return this;
        }

        @Deprecated
        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        @Deprecated
        public WearableExtender setHintAvoidBackgroundClipping(boolean z) {
            setFlag(16, z);
            return this;
        }

        @Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        @Deprecated
        public WearableExtender setHintScreenTimeout(int i) {
            this.mHintScreenTimeout = i;
            return this;
        }

        @Deprecated
        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        @Deprecated
        public WearableExtender setHintAmbientBigPicture(boolean z) {
            setFlag(32, z);
            return this;
        }

        @Deprecated
        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean z) {
            setFlag(64, z);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public WearableExtender setDismissalId(String str) {
            this.mDismissalId = str;
            return this;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public WearableExtender setBridgeTag(String str) {
            this.mBridgeTag = str;
            return this;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int i, boolean z) {
            if (z) {
                this.mFlags = i | this.mFlags;
            } else {
                this.mFlags = (~i) & this.mFlags;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(Consumer<Uri> consumer) {
            Iterator<Action> it = this.mActions.iterator();
            while (it.hasNext()) {
                it.next().visitUris(consumer);
            }
        }
    }

    public static final class CarExtender implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public CarExtender() {
            this.mColor = 0;
        }

        public CarExtender(Notification notification) {
            this.mColor = 0;
            Bundle bundle = notification.extras == null ? null : notification.extras.getBundle(EXTRA_CAR_EXTENDER);
            if (bundle != null) {
                this.mLargeIcon = (Bitmap) bundle.getParcelable(EXTRA_LARGE_ICON, Bitmap.class);
                this.mColor = bundle.getInt(EXTRA_COLOR, 0);
                this.mUnreadConversation = UnreadConversation.getUnreadConversationFromBundle(bundle.getBundle(EXTRA_CONVERSATION));
            }
        }

        @Override // android.app.Notification.Extender
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            Bitmap bitmap = this.mLargeIcon;
            if (bitmap != null) {
                bundle.putParcelable(EXTRA_LARGE_ICON, bitmap);
            }
            int i = this.mColor;
            if (i != 0) {
                bundle.putInt(EXTRA_COLOR, i);
            }
            UnreadConversation unreadConversation = this.mUnreadConversation;
            if (unreadConversation != null) {
                bundle.putBundle(EXTRA_CONVERSATION, unreadConversation.getBundleForUnreadConversation());
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle);
            return builder;
        }

        public CarExtender setColor(int i) {
            this.mColor = i;
            return this;
        }

        public int getColor() {
            return this.mColor;
        }

        public CarExtender setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        public static class UnreadConversation {
            private static final String KEY_AUTHOR = "author";
            private static final String KEY_MESSAGES = "messages";
            static final String KEY_ON_READ = "on_read";
            static final String KEY_ON_REPLY = "on_reply";
            private static final String KEY_PARTICIPANTS = "participants";
            static final String KEY_REMOTE_INPUT = "remote_input";
            private static final String KEY_TEXT = "text";
            private static final String KEY_TIMESTAMP = "timestamp";
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            UnreadConversation(String[] strArr, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j) {
                this.mMessages = strArr;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = pendingIntent2;
                this.mReplyPendingIntent = pendingIntent;
                this.mParticipants = strArr2;
                this.mLatestTimestamp = j;
            }

            public String[] getMessages() {
                return this.mMessages;
            }

            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public String[] getParticipants() {
                return this.mParticipants;
            }

            public String getParticipant() {
                String[] strArr = this.mParticipants;
                if (strArr.length > 0) {
                    return strArr[0];
                }
                return null;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            Bundle getBundleForUnreadConversation() {
                Bundle bundle = new Bundle();
                String[] strArr = this.mParticipants;
                String str = (strArr == null || strArr.length <= 1) ? null : strArr[0];
                int length = this.mMessages.length;
                Parcelable[] parcelableArr = new Parcelable[length];
                for (int i = 0; i < length; i++) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("text", this.mMessages[i]);
                    bundle2.putString("author", str);
                    parcelableArr[i] = bundle2;
                }
                bundle.putParcelableArray(KEY_MESSAGES, parcelableArr);
                RemoteInput remoteInput = this.mRemoteInput;
                if (remoteInput != null) {
                    bundle.putParcelable(KEY_REMOTE_INPUT, remoteInput);
                }
                bundle.putParcelable(KEY_ON_REPLY, this.mReplyPendingIntent);
                bundle.putParcelable(KEY_ON_READ, this.mReadPendingIntent);
                bundle.putStringArray(KEY_PARTICIPANTS, this.mParticipants);
                bundle.putLong("timestamp", this.mLatestTimestamp);
                return bundle;
            }

            static UnreadConversation getUnreadConversationFromBundle(Bundle bundle) {
                String[] strArr;
                if (bundle == null) {
                    return null;
                }
                Parcelable[] parcelableArr = (Parcelable[]) bundle.getParcelableArray(KEY_MESSAGES, Parcelable.class);
                if (parcelableArr != null) {
                    int length = parcelableArr.length;
                    String[] strArr2 = new String[length];
                    for (int i = 0; i < length; i++) {
                        Parcelable parcelable = parcelableArr[i];
                        if (parcelable instanceof Bundle) {
                            String string = ((Bundle) parcelable).getString("text");
                            strArr2[i] = string;
                            if (string != null) {
                            }
                        }
                        return null;
                    }
                    strArr = strArr2;
                } else {
                    strArr = null;
                }
                PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(KEY_ON_READ, PendingIntent.class);
                PendingIntent pendingIntent2 = (PendingIntent) bundle.getParcelable(KEY_ON_REPLY, PendingIntent.class);
                RemoteInput remoteInput = (RemoteInput) bundle.getParcelable(KEY_REMOTE_INPUT, RemoteInput.class);
                String[] stringArray = bundle.getStringArray(KEY_PARTICIPANTS);
                if (stringArray == null || stringArray.length != 1) {
                    return null;
                }
                return new UnreadConversation(strArr, remoteInput, pendingIntent2, pendingIntent, stringArray, bundle.getLong("timestamp"));
            }
        }

        public static class Builder {
            private long mLatestTimestamp;
            private final List<String> mMessages = new ArrayList();
            private final String mParticipant;
            private PendingIntent mReadPendingIntent;
            private RemoteInput mRemoteInput;
            private PendingIntent mReplyPendingIntent;

            public Builder(String str) {
                this.mParticipant = str;
            }

            public Builder addMessage(String str) {
                this.mMessages.add(str);
                return this;
            }

            public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                this.mRemoteInput = remoteInput;
                this.mReplyPendingIntent = pendingIntent;
                return this;
            }

            public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                this.mReadPendingIntent = pendingIntent;
                return this;
            }

            public Builder setLatestTimestamp(long j) {
                this.mLatestTimestamp = j;
                return this;
            }

            public UnreadConversation build() {
                List<String> list = this.mMessages;
                return new UnreadConversation((String[]) list.toArray(new String[list.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new String[]{this.mParticipant}, this.mLatestTimestamp);
            }
        }
    }

    public static final class TvExtender implements Extender {
        private static final String EXTRA_CHANNEL_ID = "channel_id";
        static final String EXTRA_CONTENT_INTENT = "content_intent";
        static final String EXTRA_DELETE_INTENT = "delete_intent";
        private static final String EXTRA_FLAGS = "flags";
        private static final String EXTRA_SUPPRESS_SHOW_OVER_APPS = "suppressShowOverApps";
        private static final String EXTRA_TV_EXTENDER = "android.tv.EXTENSIONS";
        private static final int FLAG_AVAILABLE_ON_TV = 1;
        private static final String TAG = "TvExtender";
        private String mChannelId;
        private PendingIntent mContentIntent;
        private PendingIntent mDeleteIntent;
        private int mFlags;
        private boolean mSuppressShowOverApps;

        public TvExtender() {
            this.mFlags = 1;
        }

        public TvExtender(Notification notification) {
            Bundle bundle = notification.extras == null ? null : notification.extras.getBundle(EXTRA_TV_EXTENDER);
            if (bundle != null) {
                this.mFlags = bundle.getInt("flags");
                this.mChannelId = bundle.getString("channel_id");
                this.mSuppressShowOverApps = bundle.getBoolean(EXTRA_SUPPRESS_SHOW_OVER_APPS);
                this.mContentIntent = (PendingIntent) bundle.getParcelable(EXTRA_CONTENT_INTENT, PendingIntent.class);
                this.mDeleteIntent = (PendingIntent) bundle.getParcelable(EXTRA_DELETE_INTENT, PendingIntent.class);
            }
        }

        @Override // android.app.Notification.Extender
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            bundle.putInt("flags", this.mFlags);
            bundle.putString("channel_id", this.mChannelId);
            bundle.putBoolean(EXTRA_SUPPRESS_SHOW_OVER_APPS, this.mSuppressShowOverApps);
            PendingIntent pendingIntent = this.mContentIntent;
            if (pendingIntent != null) {
                bundle.putParcelable(EXTRA_CONTENT_INTENT, pendingIntent);
            }
            PendingIntent pendingIntent2 = this.mDeleteIntent;
            if (pendingIntent2 != null) {
                bundle.putParcelable(EXTRA_DELETE_INTENT, pendingIntent2);
            }
            builder.getExtras().putBundle(EXTRA_TV_EXTENDER, bundle);
            return builder;
        }

        public boolean isAvailableOnTv() {
            return (this.mFlags & 1) != 0;
        }

        @SystemApi
        public TvExtender setChannel(String str) {
            this.mChannelId = str;
            return this;
        }

        public TvExtender setChannelId(String str) {
            this.mChannelId = str;
            return this;
        }

        @SystemApi
        @Deprecated
        public String getChannel() {
            return this.mChannelId;
        }

        public String getChannelId() {
            return this.mChannelId;
        }

        public TvExtender setContentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public PendingIntent getContentIntent() {
            return this.mContentIntent;
        }

        public TvExtender setDeleteIntent(PendingIntent pendingIntent) {
            this.mDeleteIntent = pendingIntent;
            return this;
        }

        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public TvExtender setSuppressShowOverApps(boolean z) {
            this.mSuppressShowOverApps = z;
            return this;
        }

        @SystemApi
        public boolean getSuppressShowOverApps() {
            return this.mSuppressShowOverApps;
        }

        public boolean isSuppressShowOverApps() {
            return this.mSuppressShowOverApps;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends Parcelable> T[] getParcelableArrayFromBundle(Bundle bundle, String str, Class<T> cls) {
        T[] tArr = (T[]) ((Parcelable[]) bundle.getParcelableArray(str, Parcelable.class));
        if (Array.newInstance((Class<?>) cls, 0).getClass().isInstance(tArr) || tArr == null) {
            return tArr;
        }
        T[] tArr2 = (T[]) ((Parcelable[]) Array.newInstance((Class<?>) cls, tArr.length));
        for (int i = 0; i < tArr.length; i++) {
            tArr2[i] = tArr[i];
        }
        bundle.putParcelableArray(str, tArr2);
        return tArr2;
    }

    private static class BuilderRemoteViews extends RemoteViews {
        @Override // android.widget.RemoteViews
        protected boolean shouldUseStaticFilter() {
            return true;
        }

        public BuilderRemoteViews(Parcel parcel) {
            super(parcel);
        }

        public BuilderRemoteViews(ApplicationInfo applicationInfo, int i) {
            super(applicationInfo, i);
        }

        @Override // android.widget.RemoteViews
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public BuilderRemoteViews mo465clone() {
            Parcel obtain = Parcel.obtain();
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            BuilderRemoteViews builderRemoteViews = new BuilderRemoteViews(obtain);
            obtain.recycle();
            return builderRemoteViews;
        }
    }

    private static class TemplateBindResult {
        public final MarginSet mHeadingExtraMarginSet;
        public final MarginSet mHeadingFullMarginSet;
        float mRightIconHeightDp;
        boolean mRightIconVisible;
        float mRightIconWidthDp;
        public final MarginSet mTitleMarginSet;

        private TemplateBindResult() {
            this.mHeadingExtraMarginSet = new MarginSet();
            this.mHeadingFullMarginSet = new MarginSet();
            this.mTitleMarginSet = new MarginSet();
        }

        public void setRightIconState(boolean z, float f, float f2, float f3, float f4) {
            this.mRightIconVisible = z;
            this.mRightIconWidthDp = f;
            this.mRightIconHeightDp = f2;
            this.mHeadingExtraMarginSet.setValues(0.0f, f3);
            float f5 = f3 + f4;
            this.mHeadingFullMarginSet.setValues(f4, f5);
            this.mTitleMarginSet.setValues(0.0f, f5);
        }

        private class MarginSet {
            private float mValueIfGone;
            private float mValueIfVisible;

            private MarginSet() {
            }

            public void setValues(float f, float f2) {
                this.mValueIfGone = f;
                this.mValueIfVisible = f2;
            }

            public void applyToView(RemoteViews remoteViews, int i) {
                applyToView(remoteViews, i, 0.0f);
            }

            public void applyToView(RemoteViews remoteViews, int i, float f) {
                float dpValue = getDpValue() + f;
                if (i == 16909441) {
                    remoteViews.setFloat(R.id.notification_header, "setTopLineExtraMarginEndDp", dpValue);
                } else if (i != 16909931 && i != 16908880) {
                    remoteViews.setViewLayoutMargin(i, 5, dpValue, 1);
                } else {
                    if (this.mValueIfGone != 0.0f) {
                        throw new RuntimeException("Programming error: `text` and `big_text` use ImageFloatingTextView which can either show a margin or not; thus mValueIfGone must be 0, but it was " + this.mValueIfGone);
                    }
                    remoteViews.setFloat(i, "setImageEndMarginDp", this.mValueIfVisible);
                    remoteViews.setBoolean(i, "setHasImage", TemplateBindResult.this.mRightIconVisible);
                    remoteViews.setViewLayoutMargin(i, 5, f, 1);
                }
                if (TemplateBindResult.this.mRightIconVisible) {
                    remoteViews.setIntTag(i, R.id.tag_margin_end_when_icon_visible, TypedValue.createComplexDimension(this.mValueIfVisible + f, 1));
                    remoteViews.setIntTag(i, R.id.tag_margin_end_when_icon_gone, TypedValue.createComplexDimension(this.mValueIfGone + f, 1));
                }
            }

            public float getDpValue() {
                return TemplateBindResult.this.mRightIconVisible ? this.mValueIfVisible : this.mValueIfGone;
            }
        }
    }

    private static class StandardTemplateParams {
        public static final int DECORATION_MINIMAL = 1;
        public static final int DECORATION_PARTIAL = 2;
        public static int VIEW_TYPE_EXPANDED = 2;
        public static int VIEW_TYPE_GROUP_HEADER = 6;
        public static int VIEW_TYPE_GROUP_HEADER_EXPANDED = 7;
        public static int VIEW_TYPE_HEADS_UP = 3;
        public static int VIEW_TYPE_INSIGNIFICANT = 8;
        public static int VIEW_TYPE_MINIMIZED = 4;
        public static int VIEW_TYPE_NORMAL = 1;
        public static int VIEW_TYPE_PUBLIC = 5;
        public static int VIEW_TYPE_UNSPECIFIED;
        boolean allowColorization;
        boolean mAllowTextWithProgress;
        boolean mCallStyleActions;
        CharSequence mHeaderTextSecondary;
        boolean mHeaderless;
        boolean mHideActions;
        boolean mHideAppName;
        boolean mHideLeftIcon;
        boolean mHideProgress;
        boolean mHideRightIcon;
        boolean mHideSnoozeButton;
        boolean mHideSubText;
        boolean mHideTime;
        boolean mHideTitle;
        boolean mHighlightExpander;
        Icon mPromotedPicture;
        CharSequence mSubText;
        CharSequence mText;
        int mTextViewId;
        CharSequence mTitle;
        int mTitleViewId;
        int mViewType;
        int maxRemoteInputHistory;

        private StandardTemplateParams() {
            this.mViewType = VIEW_TYPE_UNSPECIFIED;
            this.maxRemoteInputHistory = 3;
            this.allowColorization = true;
            this.mHighlightExpander = false;
        }

        final StandardTemplateParams reset() {
            this.mViewType = VIEW_TYPE_UNSPECIFIED;
            this.mHeaderless = false;
            this.mHideAppName = false;
            this.mHideTitle = false;
            this.mHideSubText = false;
            this.mHideTime = false;
            this.mHideActions = false;
            this.mHideProgress = false;
            this.mHideSnoozeButton = false;
            this.mHideLeftIcon = false;
            this.mHideRightIcon = false;
            this.mPromotedPicture = null;
            this.mCallStyleActions = false;
            this.mAllowTextWithProgress = false;
            this.mTitleViewId = 16908310;
            this.mTextViewId = R.id.text;
            this.mTitle = null;
            this.mText = null;
            this.mSubText = null;
            this.mHeaderTextSecondary = null;
            this.maxRemoteInputHistory = 3;
            this.allowColorization = true;
            this.mHighlightExpander = false;
            return this;
        }

        final boolean hasTitle() {
            return (TextUtils.isEmpty(this.mTitle) || this.mHideTitle) ? false : true;
        }

        final StandardTemplateParams viewType(int i) {
            this.mViewType = i;
            return this;
        }

        public StandardTemplateParams headerless(boolean z) {
            this.mHeaderless = z;
            return this;
        }

        public StandardTemplateParams hideAppName(boolean z) {
            this.mHideAppName = z;
            return this;
        }

        public StandardTemplateParams hideSubText(boolean z) {
            this.mHideSubText = z;
            return this;
        }

        public StandardTemplateParams hideTime(boolean z) {
            this.mHideTime = z;
            return this;
        }

        final StandardTemplateParams hideActions(boolean z) {
            this.mHideActions = z;
            return this;
        }

        final StandardTemplateParams hideProgress(boolean z) {
            this.mHideProgress = z;
            return this;
        }

        final StandardTemplateParams hideTitle(boolean z) {
            this.mHideTitle = z;
            return this;
        }

        final StandardTemplateParams callStyleActions(boolean z) {
            this.mCallStyleActions = z;
            return this;
        }

        final StandardTemplateParams allowTextWithProgress(boolean z) {
            this.mAllowTextWithProgress = z;
            return this;
        }

        final StandardTemplateParams hideSnoozeButton(boolean z) {
            this.mHideSnoozeButton = z;
            return this;
        }

        final StandardTemplateParams promotedPicture(Icon icon) {
            this.mPromotedPicture = icon;
            return this;
        }

        public StandardTemplateParams titleViewId(int i) {
            this.mTitleViewId = i;
            return this;
        }

        public StandardTemplateParams textViewId(int i) {
            this.mTextViewId = i;
            return this;
        }

        final StandardTemplateParams title(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        final StandardTemplateParams text(CharSequence charSequence) {
            this.mText = charSequence;
            return this;
        }

        final StandardTemplateParams summaryText(CharSequence charSequence) {
            this.mSubText = charSequence;
            return this;
        }

        final StandardTemplateParams headerTextSecondary(CharSequence charSequence) {
            this.mHeaderTextSecondary = charSequence;
            return this;
        }

        final StandardTemplateParams hideLeftIcon(boolean z) {
            this.mHideLeftIcon = z;
            return this;
        }

        final StandardTemplateParams hideRightIcon(boolean z) {
            this.mHideRightIcon = z;
            return this;
        }

        final StandardTemplateParams disallowColorization() {
            this.allowColorization = false;
            return this;
        }

        final StandardTemplateParams highlightExpander(boolean z) {
            this.mHighlightExpander = z;
            return this;
        }

        final StandardTemplateParams fillTextsFrom(Builder builder) {
            Bundle bundle = builder.mN.extras;
            this.mTitle = builder.processLegacyText(bundle.getCharSequence(Notification.EXTRA_TITLE));
            this.mText = builder.processLegacyText(bundle.getCharSequence(Notification.EXTRA_TEXT));
            this.mSubText = bundle.getCharSequence(Notification.EXTRA_SUB_TEXT);
            return this;
        }

        public StandardTemplateParams setMaxRemoteInputHistory(int i) {
            this.maxRemoteInputHistory = i;
            return this;
        }

        public StandardTemplateParams decorationType(int i) {
            hideTitle(true);
            boolean z = i <= 1;
            hideLeftIcon(false);
            hideRightIcon(z);
            hideProgress(z);
            hideActions(z);
            return this;
        }
    }

    public static class Colors {
        private int mPaletteIsForRawColor = 1;
        private boolean mPaletteIsForColorized = false;
        private boolean mPaletteIsForNightMode = false;
        private int mBackgroundColor = 1;
        private int mProtectionColor = 1;
        private int mPrimaryTextColor = 1;
        private int mSecondaryTextColor = 1;
        private int mThirdTextColor = 1;
        private int mPrimaryAccentColor = 1;
        private int mSecondaryAccentColor = 1;
        private int mTertiaryAccentColor = 1;
        private int mOnTertiaryAccentTextColor = 1;
        private int mTertiaryFixedDimAccentColor = 1;
        private int mOnTertiaryFixedAccentTextColor = 1;
        private int mErrorColor = 1;
        private int mContrastColor = 1;
        private int mRippleAlpha = 51;

        private static TypedArray obtainDayNightAttributes(Context context, int[] iArr) {
            if (context.getTheme() == null) {
                return null;
            }
            return new ContextThemeWrapper(context, 16974563).getTheme().obtainStyledAttributes(iArr);
        }

        private static int getColor(TypedArray typedArray, int i, int i2) {
            return typedArray == null ? i2 : typedArray.getColor(i, i2);
        }

        public void resolvePalette(Context context, int i, boolean z, boolean z2) {
            if (this.mPaletteIsForRawColor == i && this.mPaletteIsForColorized == z && this.mPaletteIsForNightMode == z2) {
                return;
            }
            this.mPaletteIsForRawColor = i;
            this.mPaletteIsForColorized = z;
            this.mPaletteIsForNightMode = z2;
            if (z) {
                if (i == 0) {
                    this.mBackgroundColor = context.getColor(R.color.materialColorSecondary);
                } else {
                    this.mBackgroundColor = i;
                }
                if (Flags.uiRichOngoing()) {
                    boolean isColorDark = Builder.isColorDark(this.mBackgroundColor);
                    int i2 = isColorDark ? -1 : -16777216;
                    this.mPrimaryTextColor = ContrastColorUtil.ensureContrast(ColorUtils.blendARGB(this.mBackgroundColor, i2, 0.9f), this.mBackgroundColor, isColorDark, 4.5d);
                    this.mSecondaryTextColor = ContrastColorUtil.ensureContrast(ColorUtils.blendARGB(this.mBackgroundColor, i2, 0.8f), this.mBackgroundColor, isColorDark, 4.5d);
                } else {
                    this.mPrimaryTextColor = ContrastColorUtil.findAlphaToMeetContrast(ContrastColorUtil.resolvePrimaryColor(context, this.mBackgroundColor, z2), this.mBackgroundColor, 4.5d);
                    this.mSecondaryTextColor = ContrastColorUtil.findAlphaToMeetContrast(ContrastColorUtil.resolveSecondaryColor(context, this.mBackgroundColor, z2), this.mBackgroundColor, 4.5d);
                }
                int i3 = this.mPrimaryTextColor;
                this.mContrastColor = i3;
                this.mPrimaryAccentColor = i3;
                this.mSecondaryAccentColor = this.mSecondaryTextColor;
                int flattenAlpha = flattenAlpha(i3, this.mBackgroundColor);
                this.mTertiaryAccentColor = flattenAlpha;
                int i4 = this.mBackgroundColor;
                this.mOnTertiaryAccentTextColor = i4;
                this.mTertiaryFixedDimAccentColor = flattenAlpha;
                this.mOnTertiaryFixedAccentTextColor = i4;
                this.mErrorColor = this.mPrimaryTextColor;
                this.mRippleAlpha = 51;
            } else {
                this.mBackgroundColor = context.getColor(R.color.materialColorSurfaceContainerHigh);
                this.mPrimaryTextColor = context.getColor(R.color.materialColorOnSurface);
                this.mSecondaryTextColor = context.getColor(R.color.materialColorOnSurfaceVariant);
                this.mPrimaryAccentColor = context.getColor(R.color.materialColorPrimary);
                this.mSecondaryAccentColor = context.getColor(R.color.materialColorSecondary);
                this.mTertiaryAccentColor = context.getColor(R.color.materialColorTertiary);
                this.mOnTertiaryAccentTextColor = context.getColor(R.color.materialColorOnTertiary);
                this.mTertiaryFixedDimAccentColor = context.getColor(R.color.materialColorTertiaryFixedDim);
                this.mOnTertiaryFixedAccentTextColor = context.getColor(R.color.materialColorOnTertiaryFixed);
                TypedArray obtainDayNightAttributes = obtainDayNightAttributes(context, new int[]{16844099, 16843820});
                try {
                    this.mErrorColor = getColor(obtainDayNightAttributes, 0, 1);
                    this.mRippleAlpha = Color.alpha(getColor(obtainDayNightAttributes, 1, 872415231));
                    if (obtainDayNightAttributes != null) {
                        obtainDayNightAttributes.close();
                    }
                    this.mContrastColor = calculateContrastColor(context, i, this.mPrimaryAccentColor, this.mBackgroundColor, z2);
                    int resolvePrimaryColor = ContrastColorUtil.resolvePrimaryColor(context, 0, z2);
                    this.mPrimaryTextColor = resolvePrimaryColor;
                    this.mPrimaryAccentColor = resolvePrimaryColor;
                    int resolveSecondaryColor = ContrastColorUtil.resolveSecondaryColor(context, 0, z2);
                    this.mSecondaryTextColor = resolveSecondaryColor;
                    this.mSecondaryAccentColor = resolveSecondaryColor;
                    this.mThirdTextColor = ContrastColorUtil.resolveThirdColor(context, 0, z2);
                    if (this.mTertiaryAccentColor == 1) {
                        this.mTertiaryAccentColor = this.mContrastColor;
                    }
                    if (this.mOnTertiaryAccentTextColor == 1) {
                        this.mOnTertiaryAccentTextColor = ColorUtils.setAlphaComponent(ContrastColorUtil.resolvePrimaryColor(context, this.mTertiaryAccentColor, z2), 255);
                    }
                    if (this.mTertiaryFixedDimAccentColor == 1) {
                        this.mTertiaryFixedDimAccentColor = this.mContrastColor;
                    }
                    if (this.mOnTertiaryFixedAccentTextColor == 1) {
                        this.mOnTertiaryFixedAccentTextColor = ColorUtils.setAlphaComponent(ContrastColorUtil.resolvePrimaryColor(context, this.mTertiaryFixedDimAccentColor, z2), 255);
                    }
                    if (this.mErrorColor == 1) {
                        this.mErrorColor = this.mPrimaryTextColor;
                    }
                } catch (Throwable th) {
                    if (obtainDayNightAttributes != null) {
                        try {
                            obtainDayNightAttributes.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            this.mProtectionColor = ColorUtils.blendARGB(this.mPrimaryTextColor, this.mBackgroundColor, 0.9f);
        }

        private static int calculateContrastColor(Context context, int i, int i2, int i3, boolean z) {
            if (i != 0) {
                i2 = ContrastColorUtil.resolveContrastColor(context, i, i3, z);
            } else if (i2 == 1) {
                i2 = ContrastColorUtil.resolveDefaultColor(context, i3, z);
            }
            return flattenAlpha(i2, i3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int flattenAlpha(int i, int i2) {
            return Color.alpha(i) == 255 ? i : ContrastColorUtil.compositeColors(i, i2);
        }

        public int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public int getProtectionColor() {
            return this.mProtectionColor;
        }

        public int getPrimaryTextColor() {
            return this.mPrimaryTextColor;
        }

        public int getSecondaryTextColor() {
            return this.mSecondaryTextColor;
        }

        public int getThirdTextColor() {
            return this.mThirdTextColor;
        }

        public int getPrimaryAccentColor() {
            return this.mPrimaryAccentColor;
        }

        public int getSecondaryAccentColor() {
            return this.mSecondaryAccentColor;
        }

        public int getTertiaryAccentColor() {
            return this.mTertiaryAccentColor;
        }

        public int getOnTertiaryAccentTextColor() {
            return this.mOnTertiaryAccentTextColor;
        }

        public int getTertiaryFixedDimAccentColor() {
            return this.mTertiaryFixedDimAccentColor;
        }

        public int getOnTertiaryFixedAccentTextColor() {
            return this.mOnTertiaryFixedAccentTextColor;
        }

        public int getContrastColor() {
            return this.mContrastColor;
        }

        public int getErrorColor() {
            return this.mErrorColor;
        }

        public int getRippleAlpha() {
            return this.mRippleAlpha;
        }
    }

    public void semDisableEdgeLighting() {
        this.semFlags |= 32;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap scaleDownIfNecessaryForBigPicture(Bitmap bitmap, int i, int i2) {
        float f;
        float f2;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= i || height <= i2) {
            return bitmap;
        }
        if (width > height) {
            f = i2;
            f2 = height;
        } else {
            f = i;
            f2 = width;
        }
        float f3 = f / f2;
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.max(1, (int) (width * f3)), Math.max(1, (int) (f3 * height)), true);
        Log.d(TAG, "bigpicture scaling before[" + width + " : " + height + "]  after[" + createScaledBitmap.getWidth() + " : " + createScaledBitmap.getHeight() + NavigationBarInflaterView.SIZE_MOD_END);
        return createScaledBitmap;
    }
}
