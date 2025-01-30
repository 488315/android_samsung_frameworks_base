package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
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
import android.p009os.BadParcelableException;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.SystemClock;
import android.p009os.SystemProperties;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.provider.Settings;
import android.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.ContextThemeWrapper;
import android.widget.RemoteViews;
import com.android.internal.C4337R;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.MessagingMessage;
import com.samsung.android.ims.options.SemCapabilities;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PEOPLE_LIST = "android.people.list";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PICTURE_CONTENT_DESCRIPTION = "android.pictureContentDescription";
    public static final String EXTRA_PICTURE_ICON = "android.pictureIcon";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REDUCED_IMAGES = "android.reduced.images";
    public static final String EXTRA_REMOTE_INPUT_DRAFT = "android.remoteInputDraft";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_REMOTE_INPUT_HISTORY_ITEMS = "android.remoteInputHistoryItems";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED = "android.showBigPictureWhenCollapsed";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_REMOTE_INPUT_SPINNER = "android.remoteInputSpinner";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";

    @Deprecated
    public static final String EXTRA_SMALL_ICON = "android.icon";

    @SystemApi
    public static final String EXTRA_SUBSTITUTE_APP_NAME = "android.substName";
    public static final String EXTRA_SUB_TEXT = "android.subText";
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
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_NO_DISMISS = 8192;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;

    @Deprecated
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int FLAG_USER_INITIATED_JOB = 32768;
    public static final int FOREGROUND_SERVICE_DEFAULT = 0;
    public static final int FOREGROUND_SERVICE_DEFERRED = 2;
    public static final int FOREGROUND_SERVICE_IMMEDIATE = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    public static final int MAX_ACTION_BUTTONS = 3;
    private static final int MAX_CHARSEQUENCE_LENGTH = 1024;
    private static final float MAX_LARGE_ICON_ASPECT_RATIO = 1.7777778f;
    private static final int MAX_REPLY_HISTORY = 5;
    private static final List<Class<? extends Style>> PLATFORM_STYLE_CLASSES;

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
    private long creationTime;

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

    static {
        ArraySet<Integer> arraySet = new ArraySet<>();
        STANDARD_LAYOUTS = arraySet;
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_base));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_heads_up_base));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_big_base));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_big_picture));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_big_text));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_inbox));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_messaging));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_big_messaging));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_conversation));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_media));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_big_media));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_call));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_material_big_call));
        arraySet.add(Integer.valueOf(C4337R.layout.notification_template_header));
        AUDIO_ATTRIBUTES_DEFAULT = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
        PLATFORM_STYLE_CLASSES = Arrays.asList(BigTextStyle.class, BigPictureStyle.class, InboxStyle.class, MediaStyle.class, DecoratedCustomViewStyle.class, DecoratedMediaCustomViewStyle.class, MessagingStyle.class, CallStyle.class);
        CREATOR = new Parcelable.Creator<Notification>() { // from class: android.app.Notification.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Notification createFromParcel(Parcel parcel) {
                return new Notification(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Notification[] newArray(int size) {
                return new Notification[size];
            }
        };
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
            public Action createFromParcel(Parcel in) {
                return new Action(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Action[] newArray(int size) {
                return new Action[size];
            }
        };
        private static final String EXTRA_DATA_ONLY_INPUTS = "android.extra.DATA_ONLY_INPUTS";
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

        private Action(Parcel in) {
            this.mAllowGeneratedReplies = true;
            if (in.readInt() != 0) {
                Icon createFromParcel = Icon.CREATOR.createFromParcel(in);
                this.mIcon = createFromParcel;
                if (createFromParcel.getType() == 2) {
                    this.icon = this.mIcon.getResId();
                }
            }
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            if (in.readInt() == 1) {
                this.actionIntent = PendingIntent.CREATOR.createFromParcel(in);
            }
            this.mExtras = Bundle.setDefusable(in.readBundle(), true);
            this.mRemoteInputs = (RemoteInput[]) in.createTypedArray(RemoteInput.CREATOR);
            this.mAllowGeneratedReplies = in.readInt() == 1;
            this.mSemanticAction = in.readInt();
            this.mIsContextual = in.readInt() == 1;
            this.mAuthenticationRequired = in.readInt() == 1;
        }

        @Deprecated
        public Action(int icon, CharSequence title, PendingIntent intent) {
            this(Icon.createWithResource("", icon), title, intent, new Bundle(), null, true, 0, false, false);
        }

        private Action(Icon icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, boolean allowGeneratedReplies, int semanticAction, boolean isContextual, boolean requireAuth) {
            this.mAllowGeneratedReplies = true;
            this.mIcon = icon;
            if (icon != null && icon.getType() == 2) {
                this.icon = icon.getResId();
            }
            this.title = title;
            this.actionIntent = intent;
            this.mExtras = extras != null ? extras : new Bundle();
            this.mRemoteInputs = remoteInputs;
            this.mAllowGeneratedReplies = allowGeneratedReplies;
            this.mSemanticAction = semanticAction;
            this.mIsContextual = isContextual;
            this.mAuthenticationRequired = requireAuth;
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
            public Builder(int icon, CharSequence title, PendingIntent intent) {
                this(Icon.createWithResource("", icon), title, intent);
            }

            public Builder(Icon icon, CharSequence title, PendingIntent intent) {
                this(icon, title, intent, new Bundle(), null, true, 0, false);
            }

            public Builder(Action action) {
                this(action.getIcon(), action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.isAuthenticationRequired());
            }

            private Builder(Icon icon, CharSequence title, PendingIntent intent, Bundle extras, RemoteInput[] remoteInputs, boolean allowGeneratedReplies, int semanticAction, boolean authRequired) {
                this.mAllowGeneratedReplies = true;
                this.mIcon = icon;
                this.mTitle = title;
                this.mIntent = intent;
                this.mExtras = extras;
                if (remoteInputs != null) {
                    ArrayList<RemoteInput> arrayList = new ArrayList<>(remoteInputs.length);
                    this.mRemoteInputs = arrayList;
                    Collections.addAll(arrayList, remoteInputs);
                }
                this.mAllowGeneratedReplies = allowGeneratedReplies;
                this.mSemanticAction = semanticAction;
                this.mAuthenticationRequired = authRequired;
            }

            public Builder addExtras(Bundle extras) {
                if (extras != null) {
                    this.mExtras.putAll(extras);
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

            public Builder setAllowGeneratedReplies(boolean allowGeneratedReplies) {
                this.mAllowGeneratedReplies = allowGeneratedReplies;
                return this;
            }

            public Builder setSemanticAction(int semanticAction) {
                this.mSemanticAction = semanticAction;
                return this;
            }

            public Builder setContextual(boolean isContextual) {
                this.mIsContextual = isContextual;
                return this;
            }

            public Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public Builder setAuthenticationRequired(boolean authenticationRequired) {
                this.mAuthenticationRequired = authenticationRequired;
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
                ArrayList<RemoteInput> dataOnlyInputs = new ArrayList<>();
                RemoteInput[] previousDataInputs = (RemoteInput[]) Notification.getParcelableArrayFromBundle(this.mExtras, Action.EXTRA_DATA_ONLY_INPUTS, RemoteInput.class);
                if (previousDataInputs != null) {
                    for (RemoteInput remoteInput : previousDataInputs) {
                        dataOnlyInputs.add(remoteInput);
                    }
                }
                List<RemoteInput> textInputs = new ArrayList<>();
                ArrayList<RemoteInput> arrayList = this.mRemoteInputs;
                if (arrayList != null) {
                    Iterator<RemoteInput> it = arrayList.iterator();
                    while (it.hasNext()) {
                        RemoteInput input = it.next();
                        if (input.isDataOnly()) {
                            dataOnlyInputs.add(input);
                        } else {
                            textInputs.add(input);
                        }
                    }
                }
                if (!dataOnlyInputs.isEmpty()) {
                    RemoteInput[] dataInputsArr = (RemoteInput[]) dataOnlyInputs.toArray(new RemoteInput[dataOnlyInputs.size()]);
                    this.mExtras.putParcelableArray(Action.EXTRA_DATA_ONLY_INPUTS, dataInputsArr);
                }
                RemoteInput[] textInputsArr = textInputs.isEmpty() ? null : (RemoteInput[]) textInputs.toArray(new RemoteInput[textInputs.size()]);
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, textInputsArr, this.mAllowGeneratedReplies, this.mSemanticAction, this.mIsContextual, this.mAuthenticationRequired);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(Consumer<Uri> visitor) {
            Notification.visitIconUri(visitor, getIcon());
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Action m870clone() {
            return new Action(getIcon(), this.title, this.actionIntent, this.mExtras == null ? new Bundle() : new Bundle(this.mExtras), getRemoteInputs(), getAllowGeneratedReplies(), getSemanticAction(), isContextual(), isAuthenticationRequired());
        }

        @Override // android.p009os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p009os.Parcelable
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
                Bundle wearableBundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (wearableBundle != null) {
                    this.mFlags = wearableBundle.getInt("flags", 1);
                    this.mInProgressLabel = wearableBundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = wearableBundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = wearableBundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            @Override // android.app.Notification.Action.Extender
            public Builder extend(Builder builder) {
                Bundle wearableBundle = new Bundle();
                int i = this.mFlags;
                if (i != 1) {
                    wearableBundle.putInt("flags", i);
                }
                CharSequence charSequence = this.mInProgressLabel;
                if (charSequence != null) {
                    wearableBundle.putCharSequence(KEY_IN_PROGRESS_LABEL, charSequence);
                }
                CharSequence charSequence2 = this.mConfirmLabel;
                if (charSequence2 != null) {
                    wearableBundle.putCharSequence(KEY_CONFIRM_LABEL, charSequence2);
                }
                CharSequence charSequence3 = this.mCancelLabel;
                if (charSequence3 != null) {
                    wearableBundle.putCharSequence(KEY_CANCEL_LABEL, charSequence3);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
                return builder;
            }

            /* renamed from: clone, reason: merged with bridge method [inline-methods] */
            public WearableExtender m871clone() {
                WearableExtender that = new WearableExtender();
                that.mFlags = this.mFlags;
                that.mInProgressLabel = this.mInProgressLabel;
                that.mConfirmLabel = this.mConfirmLabel;
                that.mCancelLabel = this.mCancelLabel;
                return that;
            }

            public WearableExtender setAvailableOffline(boolean availableOffline) {
                setFlag(1, availableOffline);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int mask, boolean value) {
                if (value) {
                    this.mFlags |= mask;
                } else {
                    this.mFlags &= ~mask;
                }
            }

            @Deprecated
            public WearableExtender setInProgressLabel(CharSequence label) {
                this.mInProgressLabel = label;
                return this;
            }

            @Deprecated
            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            @Deprecated
            public WearableExtender setConfirmLabel(CharSequence label) {
                this.mConfirmLabel = label;
                return this;
            }

            @Deprecated
            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            @Deprecated
            public WearableExtender setCancelLabel(CharSequence label) {
                this.mCancelLabel = label;
                return this;
            }

            @Deprecated
            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public WearableExtender setHintLaunchesActivity(boolean hintLaunchesActivity) {
                setFlag(2, hintLaunchesActivity);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public WearableExtender setHintDisplayActionInline(boolean hintDisplayInline) {
                setFlag(4, hintDisplayInline);
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
        this.creationTime = System.currentTimeMillis();
        this.priority = 0;
    }

    public Notification(Context context, int icon, CharSequence tickerText, long when, CharSequence contentTitle, CharSequence contentText, Intent contentIntent) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        new Builder(context).setWhen(when).setSmallIcon(icon).setTicker(tickerText).setContentTitle(contentTitle).setContentText(contentText).setContentIntent(PendingIntent.getActivity(context, 0, contentIntent, 33554432)).buildInto(this);
    }

    @Deprecated
    public Notification(int icon, CharSequence tickerText, long when) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        this.icon = icon;
        this.tickerText = tickerText;
        this.when = when;
        this.creationTime = System.currentTimeMillis();
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
        parcel.setClassCookie(PendingIntent.class, this.mAllowlistToken);
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
    public Notification m866clone() {
        Notification that = new Notification();
        cloneInto(that, true);
        return that;
    }

    public void cloneInto(Notification that, boolean heavy) {
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        Icon icon;
        RemoteViews remoteViews3;
        RemoteViews remoteViews4;
        that.mAllowlistToken = this.mAllowlistToken;
        that.when = this.when;
        that.creationTime = this.creationTime;
        that.mSmallIcon = this.mSmallIcon;
        that.number = this.number;
        that.contentIntent = this.contentIntent;
        that.deleteIntent = this.deleteIntent;
        that.fullScreenIntent = this.fullScreenIntent;
        CharSequence charSequence = this.tickerText;
        if (charSequence != null) {
            that.tickerText = charSequence.toString();
        }
        if (heavy && (remoteViews4 = this.tickerView) != null) {
            that.tickerView = remoteViews4.mo897clone();
        }
        if (heavy && (remoteViews3 = this.contentView) != null) {
            that.contentView = remoteViews3.mo897clone();
        }
        if (heavy && (icon = this.mLargeIcon) != null) {
            that.mLargeIcon = icon;
        }
        that.iconLevel = this.iconLevel;
        that.sound = this.sound;
        that.audioStreamType = this.audioStreamType;
        AudioAttributes audioAttributes = this.audioAttributes;
        if (audioAttributes != null) {
            that.audioAttributes = new AudioAttributes.Builder(audioAttributes).build();
        }
        long[] vibrate = this.vibrate;
        if (vibrate != null) {
            int N = vibrate.length;
            long[] vib = new long[N];
            that.vibrate = vib;
            System.arraycopy(vibrate, 0, vib, 0, N);
        }
        that.ledARGB = this.ledARGB;
        that.ledOnMS = this.ledOnMS;
        that.ledOffMS = this.ledOffMS;
        that.defaults = this.defaults;
        that.flags = this.flags;
        that.priority = this.priority;
        that.category = this.category;
        that.mGroupKey = this.mGroupKey;
        that.mSortKey = this.mSortKey;
        if (this.extras != null) {
            try {
                Bundle bundle = new Bundle(this.extras);
                that.extras = bundle;
                bundle.size();
            } catch (BadParcelableException e) {
                Log.m97e(TAG, "could not unparcel extras from notification: " + this, e);
                that.extras = null;
            }
        }
        if (!ArrayUtils.isEmpty(this.allPendingIntents)) {
            that.allPendingIntents = new ArraySet<>((ArraySet) this.allPendingIntents);
        }
        Action[] actionArr = this.actions;
        if (actionArr != null) {
            that.actions = new Action[actionArr.length];
            int i = 0;
            while (true) {
                Action[] actionArr2 = this.actions;
                if (i >= actionArr2.length) {
                    break;
                }
                Action action = actionArr2[i];
                if (action != null) {
                    that.actions[i] = action.m870clone();
                }
                i++;
            }
        }
        if (heavy && (remoteViews2 = this.bigContentView) != null) {
            that.bigContentView = remoteViews2.mo897clone();
        }
        if (heavy && (remoteViews = this.headsUpContentView) != null) {
            that.headsUpContentView = remoteViews.mo897clone();
        }
        that.visibility = this.visibility;
        if (this.publicVersion != null) {
            Notification notification = new Notification();
            that.publicVersion = notification;
            this.publicVersion.cloneInto(notification, heavy);
        }
        that.color = this.color;
        that.mChannelId = this.mChannelId;
        that.mTimeout = this.mTimeout;
        that.mShortcutId = this.mShortcutId;
        that.mLocusId = this.mLocusId;
        that.mBadgeIcon = this.mBadgeIcon;
        that.mSettingsText = this.mSettingsText;
        that.mGroupAlertBehavior = this.mGroupAlertBehavior;
        that.mFgsDeferBehavior = this.mFgsDeferBehavior;
        that.mBubbleMetadata = this.mBubbleMetadata;
        that.mAllowSystemGeneratedContextualActions = this.mAllowSystemGeneratedContextualActions;
        if (!heavy) {
            that.lightenPayload();
        }
        that.semMissedCount = this.semMissedCount;
        that.semFlags = this.semFlags;
        that.semPriority = this.semPriority;
        ComponentName componentName = this.semBadgeTarget;
        if (componentName != null) {
            that.semBadgeTarget = componentName.m1270clone();
        }
        that.parcelDataSize = this.parcelDataSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void visitIconUri(Consumer<Uri> visitor, Icon icon) {
        if (icon == null) {
            return;
        }
        int iconType = icon.getType();
        if (iconType == 4 || iconType == 6) {
            visitor.accept(icon.getUri());
        }
    }

    public void visitUris(Consumer<Uri> visitor) {
        Notification notification = this.publicVersion;
        if (notification != null) {
            notification.visitUris(visitor);
        }
        visitor.accept(this.sound);
        RemoteViews remoteViews = this.tickerView;
        if (remoteViews != null) {
            remoteViews.visitUris(visitor);
        }
        RemoteViews remoteViews2 = this.contentView;
        if (remoteViews2 != null) {
            remoteViews2.visitUris(visitor);
        }
        RemoteViews remoteViews3 = this.bigContentView;
        if (remoteViews3 != null) {
            remoteViews3.visitUris(visitor);
        }
        RemoteViews remoteViews4 = this.headsUpContentView;
        if (remoteViews4 != null) {
            remoteViews4.visitUris(visitor);
        }
        visitIconUri(visitor, this.mSmallIcon);
        visitIconUri(visitor, this.mLargeIcon);
        Action[] actionArr = this.actions;
        if (actionArr != null) {
            for (Action action : actionArr) {
                action.visitUris(visitor);
            }
        }
        Bundle bundle = this.extras;
        if (bundle != null) {
            visitIconUri(visitor, (Icon) bundle.getParcelable(EXTRA_LARGE_ICON_BIG, Icon.class));
            visitIconUri(visitor, (Icon) this.extras.getParcelable(EXTRA_PICTURE_ICON, Icon.class));
            Object audioContentsUri = this.extras.get(EXTRA_AUDIO_CONTENTS_URI);
            if (audioContentsUri instanceof Uri) {
                visitor.accept((Uri) audioContentsUri);
            } else if (audioContentsUri instanceof String) {
                visitor.accept(Uri.parse((String) audioContentsUri));
            }
            if (this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI)) {
                visitor.accept(Uri.parse(this.extras.getString(EXTRA_BACKGROUND_IMAGE_URI)));
            }
            ArrayList<Person> people = this.extras.getParcelableArrayList(EXTRA_PEOPLE_LIST, Person.class);
            if (people != null && !people.isEmpty()) {
                Iterator<Person> it = people.iterator();
                while (it.hasNext()) {
                    Person p = it.next();
                    p.visitUris(visitor);
                }
            }
            RemoteInputHistoryItem[] history = (RemoteInputHistoryItem[]) this.extras.getParcelableArray(EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class);
            if (history != null) {
                for (RemoteInputHistoryItem item : history) {
                    if (item.getUri() != null) {
                        visitor.accept(item.getUri());
                    }
                }
            }
            Person person = (Person) this.extras.getParcelable(EXTRA_MESSAGING_PERSON, Person.class);
            if (person != null) {
                person.visitUris(visitor);
            }
            Parcelable[] messages = (Parcelable[]) this.extras.getParcelableArray(EXTRA_MESSAGES, Parcelable.class);
            if (!ArrayUtils.isEmpty(messages)) {
                for (MessagingStyle.Message message : MessagingStyle.Message.getMessagesFromBundleArray(messages)) {
                    message.visitUris(visitor);
                }
            }
            Parcelable[] historic = (Parcelable[]) this.extras.getParcelableArray(EXTRA_HISTORIC_MESSAGES, Parcelable.class);
            if (!ArrayUtils.isEmpty(historic)) {
                for (MessagingStyle.Message message2 : MessagingStyle.Message.getMessagesFromBundleArray(historic)) {
                    message2.visitUris(visitor);
                }
            }
            visitIconUri(visitor, (Icon) this.extras.getParcelable(EXTRA_CONVERSATION_ICON, Icon.class));
            Person callPerson = (Person) this.extras.getParcelable(EXTRA_CALL_PERSON, Person.class);
            if (callPerson != null) {
                callPerson.visitUris(visitor);
            }
            visitIconUri(visitor, (Icon) this.extras.getParcelable(EXTRA_VERIFICATION_ICON, Icon.class));
        }
        BubbleMetadata bubbleMetadata = this.mBubbleMetadata;
        if (bubbleMetadata != null) {
            visitIconUri(visitor, bubbleMetadata.getIcon());
        }
        Bundle bundle2 = this.extras;
        if (bundle2 != null && bundle2.containsKey("android.wearable.EXTENSIONS")) {
            WearableExtender extender = new WearableExtender(this);
            extender.visitUris(visitor);
        }
    }

    public final void lightenPayload() {
        Object obj;
        this.tickerView = null;
        this.contentView = null;
        this.bigContentView = null;
        this.headsUpContentView = null;
        this.mLargeIcon = null;
        Bundle bundle = this.extras;
        if (bundle != null && !bundle.isEmpty()) {
            Set<String> keyset = this.extras.keySet();
            int N = keyset.size();
            String[] keys = (String[]) keyset.toArray(new String[N]);
            for (int i = 0; i < N; i++) {
                String key = keys[i];
                if (!"android.tv.EXTENSIONS".equals(key) && (obj = this.extras.get(key)) != null && ((obj instanceof Parcelable) || (obj instanceof Parcelable[]) || (obj instanceof SparseArray) || (obj instanceof ArrayList))) {
                    this.extras.remove(key);
                }
            }
        }
    }

    public static CharSequence safeCharSequence(CharSequence cs) {
        if (cs == null) {
            return cs;
        }
        if (cs.length() > 1024) {
            cs = cs.subSequence(0, 1024);
        }
        if (cs instanceof Parcelable) {
            Log.m96e(TAG, "warning: " + cs.getClass().getCanonicalName() + " instance is a custom Parcelable and not allowed in Notification");
            return cs.toString();
        }
        return removeTextSizeSpans(cs);
    }

    private static CharSequence removeTextSizeSpans(CharSequence charSequence) {
        Object resultSpan;
        if (charSequence instanceof Spanned) {
            Spanned ss = (Spanned) charSequence;
            Object[] spans = ss.getSpans(0, ss.length(), Object.class);
            SpannableStringBuilder builder = new SpannableStringBuilder(ss.toString());
            for (Object span : spans) {
                Object resultSpan2 = span;
                if (resultSpan2 instanceof CharacterStyle) {
                    resultSpan2 = ((CharacterStyle) span).getUnderlying();
                }
                if (!(resultSpan2 instanceof TextAppearanceSpan)) {
                    if (!(resultSpan2 instanceof RelativeSizeSpan) && !(resultSpan2 instanceof AbsoluteSizeSpan)) {
                        resultSpan = span;
                    }
                } else {
                    TextAppearanceSpan originalSpan = (TextAppearanceSpan) resultSpan2;
                    resultSpan = new TextAppearanceSpan(originalSpan.getFamily(), originalSpan.getTextStyle(), -1, originalSpan.getTextColor(), originalSpan.getLinkTextColor());
                }
                builder.setSpan(resultSpan, ss.getSpanStart(span), ss.getSpanEnd(span), ss.getSpanFlags(span));
            }
            return builder;
        }
        return charSequence;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(final Parcel parcel, int flags) {
        boolean collectPendingIntents = this.allPendingIntents == null;
        if (collectPendingIntents) {
            PendingIntent.setOnMarshaledListener(new PendingIntent.OnMarshaledListener() { // from class: android.app.Notification$$ExternalSyntheticLambda0
                @Override // android.app.PendingIntent.OnMarshaledListener
                public final void onMarshaled(PendingIntent pendingIntent, Parcel parcel2, int i) {
                    Notification.this.lambda$writeToParcel$0(parcel, pendingIntent, parcel2, i);
                }
            });
        }
        try {
            writeToParcelImpl(parcel, flags);
            synchronized (this) {
                parcel.writeArraySet(this.allPendingIntents);
            }
        } finally {
            if (collectPendingIntents) {
                PendingIntent.setOnMarshaledListener(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeToParcel$0(Parcel parcel, PendingIntent intent, Parcel out, int outFlags) {
        if (parcel == out) {
            synchronized (this) {
                if (this.allPendingIntents == null) {
                    this.allPendingIntents = new ArraySet<>();
                }
                this.allPendingIntents.add(intent);
            }
        }
    }

    private void writeToParcelImpl(Parcel parcel, int flags) {
        Bitmap bitmap;
        int i;
        parcel.writeInt(1);
        parcel.writeStrongBinder(this.mAllowlistToken);
        parcel.writeLong(this.when);
        parcel.writeLong(this.creationTime);
        if (this.mSmallIcon == null && (i = this.icon) != 0) {
            this.mSmallIcon = Icon.createWithResource("", i);
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
            TextUtils.writeToParcel(this.tickerText, parcel, flags);
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
            TextUtils.writeToParcel(this.mSettingsText, parcel, flags);
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

    public static boolean areActionsVisiblyDifferent(Notification first, Notification second) {
        Action[] firstAs = first.actions;
        Action[] secondAs = second.actions;
        if ((firstAs == null && secondAs != null) || (firstAs != null && secondAs == null)) {
            return true;
        }
        if (firstAs != null && secondAs != null) {
            if (firstAs.length != secondAs.length) {
                return true;
            }
            for (int i = 0; i < firstAs.length; i++) {
                if (!Objects.equals(String.valueOf(firstAs[i].title), String.valueOf(secondAs[i].title))) {
                    return true;
                }
                RemoteInput[] firstRs = firstAs[i].getRemoteInputs();
                RemoteInput[] secondRs = secondAs[i].getRemoteInputs();
                if (firstRs == null) {
                    firstRs = new RemoteInput[0];
                }
                if (secondRs == null) {
                    secondRs = new RemoteInput[0];
                }
                if (firstRs.length != secondRs.length) {
                    return true;
                }
                for (int j = 0; j < firstRs.length; j++) {
                    if (!Objects.equals(String.valueOf(firstRs[j].getLabel()), String.valueOf(secondRs[j].getLabel()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean areIconsDifferent(Notification first, Notification second) {
        return areIconsMaybeDifferent(first.getSmallIcon(), second.getSmallIcon()) || areIconsMaybeDifferent(first.getLargeIcon(), second.getLargeIcon());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areIconsMaybeDifferent(Icon a, Icon b) {
        if (a == b) {
            return false;
        }
        if (a == null || b == null) {
            return true;
        }
        if (a.sameAs(b)) {
            return false;
        }
        int aType = a.getType();
        if (aType != b.getType()) {
            return true;
        }
        if (aType != 1 && aType != 5) {
            return true;
        }
        Bitmap aBitmap = a.getBitmap();
        Bitmap bBitmap = b.getBitmap();
        if (aBitmap.getWidth() == bBitmap.getWidth() && aBitmap.getHeight() == bBitmap.getHeight() && aBitmap.getConfig() == bBitmap.getConfig() && aBitmap.getGenerationId() == bBitmap.getGenerationId()) {
            return false;
        }
        return true;
    }

    public static boolean areStyledNotificationsVisiblyDifferent(Builder first, Builder second) {
        if (first.getStyle() == null) {
            return second.getStyle() != null;
        }
        if (second.getStyle() == null) {
            return true;
        }
        return first.getStyle().areNotificationsVisiblyDifferent(second.getStyle());
    }

    public static boolean areRemoteViewsChanged(Builder first, Builder second) {
        return !Objects.equals(Boolean.valueOf(first.usesStandardHeader()), Boolean.valueOf(second.usesStandardHeader())) || areRemoteViewsChanged(first.f20mN.contentView, second.f20mN.contentView) || areRemoteViewsChanged(first.f20mN.bigContentView, second.f20mN.bigContentView) || areRemoteViewsChanged(first.f20mN.headsUpContentView, second.f20mN.headsUpContentView);
    }

    private static boolean areRemoteViewsChanged(RemoteViews first, RemoteViews second) {
        if (first == null && second == null) {
            return false;
        }
        if ((first != null || second == null) && ((first == null || second != null) && Objects.equals(Integer.valueOf(first.getLayoutId()), Integer.valueOf(second.getLayoutId())) && Objects.equals(Integer.valueOf(first.getSequenceNumber()), Integer.valueOf(second.getSequenceNumber())))) {
            return false;
        }
        return true;
    }

    private void fixDuplicateExtras() {
        if (this.extras != null) {
            fixDuplicateExtra(this.mLargeIcon, EXTRA_LARGE_ICON);
        }
    }

    private void fixDuplicateExtra(Parcelable original, String extraName) {
        if (original != null && this.extras.getParcelable(extraName, Parcelable.class) != null) {
            this.extras.putParcelable(extraName, original);
        }
    }

    @Deprecated
    public void setLatestEventInfo(Context context, CharSequence contentTitle, CharSequence contentText, PendingIntent contentIntent) {
        if (context.getApplicationInfo().targetSdkVersion > 22) {
            Log.m97e(TAG, "setLatestEventInfo() is deprecated and you should feel deprecated.", new Throwable());
        }
        if (context.getApplicationInfo().targetSdkVersion < 24) {
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        }
        Builder builder = new Builder(context, this);
        if (contentTitle != null) {
            builder.setContentTitle(contentTitle);
        }
        if (contentText != null) {
            builder.setContentText(contentText);
        }
        builder.setContentIntent(contentIntent);
        builder.build();
    }

    public void clearAllowlistToken() {
        this.mAllowlistToken = null;
        Notification notification = this.publicVersion;
        if (notification != null) {
            notification.clearAllowlistToken();
        }
    }

    public static void addFieldsFromContext(Context context, Notification notification) {
        addFieldsFromContext(context.getApplicationInfo(), notification);
    }

    public static void addFieldsFromContext(ApplicationInfo ai, Notification notification) {
        notification.extras.putParcelable(EXTRA_BUILDER_APPLICATION_INFO, ai);
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, getRedatedString(getChannelId()));
        proto.write(1133871366146L, this.tickerText != null);
        proto.write(1120986464259L, this.flags);
        proto.write(1120986464260L, this.color);
        proto.write(1138166333445L, this.category);
        proto.write(1138166333446L, this.mGroupKey);
        proto.write(1138166333447L, this.mSortKey);
        Action[] actionArr = this.actions;
        if (actionArr != null) {
            proto.write(1120986464264L, actionArr.length);
        }
        int i = this.visibility;
        if (i >= -1 && i <= 1) {
            proto.write(1159641169929L, i);
        }
        Notification notification = this.publicVersion;
        if (notification != null) {
            notification.dumpDebug(proto, 1146756268042L);
        }
        proto.end(token);
    }

    private String getRedatedString(String str) {
        if (isMatchPrivatePattern(str)) {
            return (String) TextUtils.trimToLengthWithEllipsis(str, 6);
        }
        return str;
    }

    private boolean isMatchPrivatePattern(String pattern) {
        if (pattern == null) {
            return false;
        }
        if (Patterns.PHONE.matcher(pattern).matches() || Patterns.WEB_URL.matcher(pattern).matches()) {
            return true;
        }
        boolean atFound = false;
        boolean commaFound = false;
        int i = 0;
        while (true) {
            if (i < pattern.length()) {
                if (pattern.charAt(i) == '@') {
                    atFound = true;
                }
                if (!atFound || pattern.charAt(i) != '.') {
                    i++;
                } else {
                    commaFound = true;
                    break;
                }
            } else {
                break;
            }
        }
        return commaFound;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Notification(channel=");
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
            sb.append(SemCapabilities.FEATURE_TAG_NULL);
        }
        sb.append(" vibrate=");
        if ((this.defaults & 2) != 0) {
            sb.append("default");
        } else {
            long[] jArr = this.vibrate;
            if (jArr != null) {
                int N = jArr.length - 1;
                sb.append(NavigationBarInflaterView.SIZE_MOD_START);
                for (int i = 0; i < N; i++) {
                    sb.append(this.vibrate[i]);
                    sb.append(',');
                }
                if (N != -1) {
                    sb.append(this.vibrate[N]);
                }
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            } else {
                sb.append(SemCapabilities.FEATURE_TAG_NULL);
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
                sb.append(SemCapabilities.FEATURE_TAG_NULL);
            }
        }
        if (this.tickerText != null) {
            sb.append(" tick");
        }
        sb.append(" defaults=0x");
        sb.append(Integer.toHexString(this.defaults));
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.flags));
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

    public static String visibilityToString(int vis) {
        switch (vis) {
            case -1:
                return "SECRET";
            case 0:
                return "PRIVATE";
            case 1:
                return "PUBLIC";
            default:
                return "UNKNOWN(" + String.valueOf(vis) + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String priorityToString(int pri) {
        switch (pri) {
            case -2:
                return "MIN";
            case -1:
                return "LOW";
            case 0:
                return "DEFAULT";
            case 1:
                return "HIGH";
            case 2:
                return "MAX";
            default:
                return "UNKNOWN(" + String.valueOf(pri) + NavigationBarInflaterView.KEY_CODE_END;
        }
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

    public BubbleMetadata getBubbleMetadata() {
        return this.mBubbleMetadata;
    }

    public void setBubbleMetadata(BubbleMetadata data) {
        this.mBubbleMetadata = data;
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

    public Pair<RemoteInput, Action> findRemoteInputActionPair(boolean requiresFreeform) {
        Action[] actionArr = this.actions;
        if (actionArr == null) {
            return null;
        }
        for (Action action : actionArr) {
            if (action.getRemoteInputs() != null) {
                RemoteInput resultRemoteInput = null;
                for (RemoteInput remoteInput : action.getRemoteInputs()) {
                    if (remoteInput.getAllowFreeFormInput() || !requiresFreeform) {
                        resultRemoteInput = remoteInput;
                    }
                }
                if (resultRemoteInput != null) {
                    return Pair.create(resultRemoteInput, action);
                }
            }
        }
        return null;
    }

    public List<Action> getContextualActions() {
        if (this.actions == null) {
            return Collections.emptyList();
        }
        List<Action> contextualActions = new ArrayList<>();
        for (Action action : this.actions) {
            if (action.isContextual()) {
                contextualActions.add(action);
            }
        }
        return contextualActions;
    }

    public static class Builder {
        public static final String EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.bigViewActionCount";
        public static final String EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.contentViewActionCount";
        public static final String EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.hudViewActionCount";
        private static final int LIGHTNESS_TEXT_DIFFERENCE_DARK = -10;
        private static final int LIGHTNESS_TEXT_DIFFERENCE_LIGHT = 20;
        private static final boolean USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY = SystemProperties.getBoolean("notifications.only_title", true);
        private ArrayList<Action> mActions;
        private ContrastColorUtil mColorUtil;
        Colors mColors;
        private Context mContext;
        private boolean mInNightMode;
        private boolean mIsLegacy;
        private boolean mIsLegacyInitialized;

        /* renamed from: mN */
        private Notification f20mN;
        StandardTemplateParams mParams;
        private ArrayList<Person> mPersonList;
        private Style mStyle;
        private boolean mTintActionButtons;
        private Bundle mUserExtras;

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(Context context, String channelId) {
            this(context, (Notification) null);
            this.f20mN.mChannelId = channelId;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        @Deprecated
        public Builder(Context context) {
            this(context, (Notification) null);
        }

        public Builder(Context context, Notification toAdopt) {
            ArrayList<Person> people;
            this.mUserExtras = new Bundle();
            this.mActions = new ArrayList<>(3);
            this.mPersonList = new ArrayList<>();
            this.mParams = new StandardTemplateParams();
            this.mColors = new Colors();
            this.mContext = context;
            Resources res = context.getResources();
            this.mTintActionButtons = res.getBoolean(C4337R.bool.config_tintNotificationActionButtons);
            if (res.getBoolean(C4337R.bool.config_enableNightMode)) {
                Configuration currentConfig = res.getConfiguration();
                this.mInNightMode = (currentConfig.uiMode & 48) == 32;
            }
            if (toAdopt == null) {
                this.f20mN = new Notification();
                if (context.getApplicationInfo().targetSdkVersion < 24) {
                    this.f20mN.extras.putBoolean(Notification.EXTRA_SHOW_WHEN, true);
                }
                this.f20mN.priority = 0;
                this.f20mN.visibility = 0;
                return;
            }
            this.f20mN = toAdopt;
            if (toAdopt.actions != null) {
                Collections.addAll(this.mActions, this.f20mN.actions);
            }
            if (this.f20mN.extras.containsKey(Notification.EXTRA_PEOPLE_LIST) && (people = this.f20mN.extras.getParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, Person.class)) != null && !people.isEmpty()) {
                this.mPersonList.addAll(people);
            }
            if (this.f20mN.getSmallIcon() == null && this.f20mN.icon != 0) {
                setSmallIcon(this.f20mN.icon);
            }
            if (this.f20mN.getLargeIcon() == null && this.f20mN.largeIcon != null) {
                setLargeIcon(this.f20mN.largeIcon);
            }
            String templateClass = this.f20mN.extras.getString(Notification.EXTRA_TEMPLATE);
            if (!TextUtils.isEmpty(templateClass)) {
                Class<? extends Style> styleClass = Notification.getNotificationStyleClass(templateClass);
                if (styleClass == null) {
                    Log.m94d(Notification.TAG, "Unknown style class: " + templateClass);
                    return;
                }
                try {
                    Constructor<? extends Style> ctor = styleClass.getDeclaredConstructor(new Class[0]);
                    ctor.setAccessible(true);
                    Style style = ctor.newInstance(new Object[0]);
                    style.restoreFromExtras(this.f20mN.extras);
                    if (style != null) {
                        setStyle(style);
                    }
                } catch (Throwable t) {
                    Log.m97e(Notification.TAG, "Could not create Style", t);
                }
            }
        }

        private ContrastColorUtil getColorUtil() {
            if (this.mColorUtil == null) {
                this.mColorUtil = ContrastColorUtil.getInstance(this.mContext);
            }
            return this.mColorUtil;
        }

        public Builder setShortcutId(String shortcutId) {
            this.f20mN.mShortcutId = shortcutId;
            return this;
        }

        public Builder setLocusId(LocusId locusId) {
            this.f20mN.mLocusId = locusId;
            return this;
        }

        public Builder setBadgeIconType(int icon) {
            this.f20mN.mBadgeIcon = icon;
            return this;
        }

        public Builder setGroupAlertBehavior(int groupAlertBehavior) {
            this.f20mN.mGroupAlertBehavior = groupAlertBehavior;
            return this;
        }

        public Builder setBubbleMetadata(BubbleMetadata data) {
            this.f20mN.mBubbleMetadata = data;
            return this;
        }

        @Deprecated
        public Builder setChannel(String channelId) {
            this.f20mN.mChannelId = channelId;
            return this;
        }

        public Builder setChannelId(String channelId) {
            this.f20mN.mChannelId = channelId;
            return this;
        }

        @Deprecated
        public Builder setTimeout(long durationMs) {
            this.f20mN.mTimeout = durationMs;
            return this;
        }

        public Builder setTimeoutAfter(long durationMs) {
            this.f20mN.mTimeout = durationMs;
            return this;
        }

        public Builder setWhen(long when) {
            this.f20mN.when = when;
            return this;
        }

        public Builder setShowWhen(boolean show) {
            this.f20mN.extras.putBoolean(Notification.EXTRA_SHOW_WHEN, show);
            return this;
        }

        public Builder setUsesChronometer(boolean b) {
            this.f20mN.extras.putBoolean(Notification.EXTRA_SHOW_CHRONOMETER, b);
            return this;
        }

        public Builder setChronometerCountDown(boolean countDown) {
            this.f20mN.extras.putBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN, countDown);
            return this;
        }

        public Builder setSmallIcon(int icon) {
            Icon icon2;
            if (icon != 0) {
                icon2 = Icon.createWithResource(this.mContext, icon);
            } else {
                icon2 = null;
            }
            return setSmallIcon(icon2);
        }

        public Builder setSmallIcon(int icon, int level) {
            this.f20mN.iconLevel = level;
            return setSmallIcon(icon);
        }

        public Builder setSmallIcon(Icon icon) {
            this.f20mN.setSmallIcon(icon);
            if (icon != null && icon.getType() == 2) {
                this.f20mN.icon = icon.getResId();
            }
            return this;
        }

        public Builder setContentTitle(CharSequence title) {
            this.f20mN.extras.putCharSequence(Notification.EXTRA_TITLE, Notification.safeCharSequence(title));
            return this;
        }

        public Builder setContentText(CharSequence text) {
            this.f20mN.extras.putCharSequence(Notification.EXTRA_TEXT, Notification.safeCharSequence(text));
            return this;
        }

        public Builder setSubText(CharSequence text) {
            this.f20mN.extras.putCharSequence(Notification.EXTRA_SUB_TEXT, Notification.safeCharSequence(text));
            return this;
        }

        public Builder setSettingsText(CharSequence text) {
            this.f20mN.mSettingsText = Notification.safeCharSequence(text);
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] text) {
            if (text == null) {
                this.f20mN.extras.putCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY, null);
            } else {
                int itemCount = Math.min(5, text.length);
                CharSequence[] safe = new CharSequence[itemCount];
                RemoteInputHistoryItem[] items = new RemoteInputHistoryItem[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    safe[i] = Notification.safeCharSequence(text[i]);
                    items[i] = new RemoteInputHistoryItem(text[i]);
                }
                this.f20mN.extras.putCharSequenceArray(Notification.EXTRA_REMOTE_INPUT_HISTORY, safe);
                this.f20mN.extras.putParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, items);
            }
            return this;
        }

        public Builder setRemoteInputHistory(RemoteInputHistoryItem[] items) {
            if (items == null) {
                this.f20mN.extras.putParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, null);
            } else {
                int itemCount = Math.min(5, items.length);
                RemoteInputHistoryItem[] history = new RemoteInputHistoryItem[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    history[i] = items[i];
                }
                this.f20mN.extras.putParcelableArray(Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, history);
            }
            return this;
        }

        public Builder setShowRemoteInputSpinner(boolean showSpinner) {
            this.f20mN.extras.putBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, showSpinner);
            return this;
        }

        public Builder setHideSmartReplies(boolean hideSmartReplies) {
            this.f20mN.extras.putBoolean(Notification.EXTRA_HIDE_SMART_REPLIES, hideSmartReplies);
            return this;
        }

        public Builder setNumber(int number) {
            this.f20mN.number = number;
            return this;
        }

        @Deprecated
        public Builder setContentInfo(CharSequence info) {
            this.f20mN.extras.putCharSequence(Notification.EXTRA_INFO_TEXT, Notification.safeCharSequence(info));
            return this;
        }

        public Builder setProgress(int max, int progress, boolean indeterminate) {
            this.f20mN.extras.putInt(Notification.EXTRA_PROGRESS, progress);
            this.f20mN.extras.putInt(Notification.EXTRA_PROGRESS_MAX, max);
            this.f20mN.extras.putBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE, indeterminate);
            return this;
        }

        @Deprecated
        public Builder setContent(RemoteViews views) {
            return setCustomContentView(views);
        }

        public Builder setCustomContentView(RemoteViews contentView) {
            this.f20mN.contentView = contentView;
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews contentView) {
            this.f20mN.bigContentView = contentView;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews contentView) {
            this.f20mN.headsUpContentView = contentView;
            return this;
        }

        public Builder setContentIntent(PendingIntent intent) {
            this.f20mN.contentIntent = intent;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent intent) {
            this.f20mN.deleteIntent = intent;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent intent, boolean highPriority) {
            this.f20mN.fullScreenIntent = intent;
            setFlag(128, highPriority);
            return this;
        }

        public Builder setTicker(CharSequence tickerText) {
            this.f20mN.tickerText = Notification.safeCharSequence(tickerText);
            return this;
        }

        @Deprecated
        public Builder setTicker(CharSequence tickerText, RemoteViews views) {
            setTicker(tickerText);
            return this;
        }

        public Builder setLargeIcon(Bitmap b) {
            return setLargeIcon(b != null ? Icon.createWithBitmap(b) : null);
        }

        public Builder setLargeIcon(Icon icon) {
            this.f20mN.mLargeIcon = icon;
            this.f20mN.extras.putParcelable(Notification.EXTRA_LARGE_ICON, icon);
            return this;
        }

        @Deprecated
        public Builder setSound(Uri sound) {
            this.f20mN.sound = sound;
            this.f20mN.audioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri sound, int streamType) {
            PlayerBase.deprecateStreamTypeForPlayback(streamType, Notification.TAG, "setSound()");
            this.f20mN.sound = sound;
            this.f20mN.audioStreamType = streamType;
            return this;
        }

        @Deprecated
        public Builder setSound(Uri sound, AudioAttributes audioAttributes) {
            this.f20mN.sound = sound;
            this.f20mN.audioAttributes = audioAttributes;
            return this;
        }

        @Deprecated
        public Builder setVibrate(long[] pattern) {
            this.f20mN.vibrate = pattern;
            return this;
        }

        @Deprecated
        public Builder setLights(int argb, int onMs, int offMs) {
            this.f20mN.ledARGB = argb;
            this.f20mN.ledOnMS = onMs;
            this.f20mN.ledOffMS = offMs;
            if (onMs != 0 || offMs != 0) {
                this.f20mN.flags |= 1;
            }
            return this;
        }

        public Builder setOngoing(boolean ongoing) {
            setFlag(2, ongoing);
            return this;
        }

        public Builder setColorized(boolean colorize) {
            this.f20mN.extras.putBoolean(Notification.EXTRA_COLORIZED, colorize);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
            setFlag(8, onlyAlertOnce);
            return this;
        }

        public Builder setForegroundServiceBehavior(int behavior) {
            this.f20mN.mFgsDeferBehavior = behavior;
            return this;
        }

        public Builder setAutoCancel(boolean autoCancel) {
            setFlag(16, autoCancel);
            return this;
        }

        public Builder setLocalOnly(boolean localOnly) {
            setFlag(256, localOnly);
            return this;
        }

        @Deprecated
        public Builder setDefaults(int defaults) {
            this.f20mN.defaults = defaults;
            return this;
        }

        @Deprecated
        public Builder setPriority(int pri) {
            this.f20mN.priority = pri;
            return this;
        }

        public Builder setCategory(String category) {
            this.f20mN.category = category;
            return this;
        }

        public Builder addPerson(String uri) {
            addPerson(new Person.Builder().setUri(uri).build());
            return this;
        }

        public Builder addPerson(Person person) {
            this.mPersonList.add(person);
            return this;
        }

        public Builder setGroup(String groupKey) {
            this.f20mN.mGroupKey = groupKey;
            return this;
        }

        public Builder setGroupSummary(boolean isGroupSummary) {
            setFlag(512, isGroupSummary);
            return this;
        }

        public Builder setSortKey(String sortKey) {
            this.f20mN.mSortKey = sortKey;
            return this;
        }

        public Builder semSetCapsule(Bundle bundle) {
            this.f20mN.extras.putBundle(Notification.SEM_EXTRA_CAPSULE, bundle);
            return this;
        }

        public Builder addExtras(Bundle extras) {
            if (extras != null) {
                this.mUserExtras.putAll(extras);
            }
            return this;
        }

        public Builder setExtras(Bundle extras) {
            if (extras != null) {
                this.mUserExtras = extras;
            }
            return this;
        }

        public Bundle getExtras() {
            return this.mUserExtras;
        }

        @Deprecated
        public Builder addAction(int icon, CharSequence title, PendingIntent intent) {
            this.mActions.add(new Action(icon, Notification.safeCharSequence(title), intent));
            return this;
        }

        public Builder addAction(Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        public Builder setActions(Action... actions) {
            this.mActions.clear();
            for (int i = 0; i < actions.length; i++) {
                if (actions[i] != null) {
                    this.mActions.add(actions[i]);
                }
            }
            return this;
        }

        public Builder setStyle(Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (style == null) {
                    this.f20mN.extras.remove(Notification.EXTRA_TEMPLATE);
                } else {
                    style.setBuilder(this);
                    this.f20mN.extras.putString(Notification.EXTRA_TEMPLATE, style.getClass().getName());
                }
            }
            return this;
        }

        public Style getStyle() {
            return this.mStyle;
        }

        public Builder setVisibility(int visibility) {
            this.f20mN.visibility = visibility;
            return this;
        }

        public Builder setPublicVersion(Notification n) {
            if (n != null) {
                this.f20mN.publicVersion = new Notification();
                n.cloneInto(this.f20mN.publicVersion, true);
            } else {
                this.f20mN.publicVersion = null;
            }
            return this;
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        public Builder setFlag(int mask, boolean value) {
            if (value) {
                this.f20mN.flags |= mask;
            } else {
                this.f20mN.flags &= ~mask;
            }
            return this;
        }

        public Builder setColor(int argb) {
            this.f20mN.color = argb;
            sanitizeColor();
            return this;
        }

        private void bindPhishingAlertIcon(RemoteViews contentView, StandardTemplateParams p) {
            contentView.setDrawableTint(C4337R.id.phishing_alert, false, getColors(p).getErrorColor(), PorterDuff.Mode.SRC_ATOP);
        }

        private Drawable getProfileBadgeDrawable() {
            if (this.mContext.getUserId() == 0) {
                return null;
            }
            DevicePolicyManager dpm = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
            return dpm.getResources().getDrawable(getUpdatableProfileBadgeId(), DevicePolicyResources.Drawables.Style.SOLID_COLORED, DevicePolicyResources.Drawables.Source.NOTIFICATION, new Supplier() { // from class: android.app.Notification$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    Drawable defaultProfileBadgeDrawable;
                    defaultProfileBadgeDrawable = Notification.Builder.this.getDefaultProfileBadgeDrawable();
                    return defaultProfileBadgeDrawable;
                }
            });
        }

        private String getUpdatableProfileBadgeId() {
            return ((UserManager) this.mContext.getSystemService(UserManager.class)).isManagedProfile() ? DevicePolicyResources.Drawables.WORK_PROFILE_ICON : DevicePolicyResources.UNDEFINED;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Drawable getDefaultProfileBadgeDrawable() {
            return this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(this.mContext.getUserId()), 0);
        }

        private Bitmap getProfileBadge() {
            Drawable badge = getProfileBadgeDrawable();
            if (badge == null) {
                return null;
            }
            int size = this.mContext.getResources().getDimensionPixelSize(C4337R.dimen.notification_badge_size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            badge.setBounds(0, 0, size, size);
            badge.draw(canvas);
            return bitmap;
        }

        private void bindProfileBadge(RemoteViews contentView, StandardTemplateParams p) {
            Bitmap profileBadge = getProfileBadge();
            if (profileBadge != null) {
                contentView.setImageViewBitmap(C4337R.id.profile_badge, profileBadge);
                contentView.setViewVisibility(C4337R.id.profile_badge, 0);
                if (isBackgroundColorized(p)) {
                    contentView.setDrawableTint(C4337R.id.profile_badge, false, getPrimaryTextColor(p), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }

        private void bindAlertedIcon(RemoteViews contentView, StandardTemplateParams p) {
            contentView.setDrawableTint(C4337R.id.alerted_icon, false, getColors(p).getSecondaryTextColor(), PorterDuff.Mode.SRC_IN);
        }

        public boolean usesStandardHeader() {
            if (this.f20mN.mUsesStandardHeader) {
                return true;
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 24 && this.f20mN.contentView == null && this.f20mN.bigContentView == null) {
                return true;
            }
            boolean contentViewUsesHeader = this.f20mN.contentView == null || Notification.STANDARD_LAYOUTS.contains(Integer.valueOf(this.f20mN.contentView.getLayoutId()));
            boolean bigContentViewUsesHeader = this.f20mN.bigContentView == null || Notification.STANDARD_LAYOUTS.contains(Integer.valueOf(this.f20mN.bigContentView.getLayoutId()));
            return contentViewUsesHeader && bigContentViewUsesHeader;
        }

        private void resetStandardTemplate(RemoteViews contentView) {
            resetNotificationHeader(contentView);
            contentView.setViewVisibility(C4337R.id.right_icon, 8);
            contentView.setViewVisibility(16908310, 8);
            contentView.setTextViewText(16908310, null);
            contentView.setViewVisibility(C4337R.id.text, 8);
            contentView.setTextViewText(C4337R.id.text, null);
        }

        private void resetNotificationHeader(RemoteViews contentView) {
            contentView.setBoolean(C4337R.id.expand_button, "setExpanded", false);
            contentView.setViewVisibility(C4337R.id.app_name_text, 8);
            contentView.setTextViewText(C4337R.id.app_name_text, null);
            contentView.setViewVisibility(C4337R.id.chronometer, 8);
            contentView.setViewVisibility(C4337R.id.header_text, 8);
            contentView.setTextViewText(C4337R.id.header_text, null);
            contentView.setViewVisibility(C4337R.id.header_text_secondary, 8);
            contentView.setTextViewText(C4337R.id.header_text_secondary, null);
            contentView.setViewVisibility(C4337R.id.header_text_divider, 8);
            contentView.setViewVisibility(C4337R.id.header_text_secondary_divider, 8);
            contentView.setViewVisibility(C4337R.id.time_divider, 8);
            contentView.setViewVisibility(C4337R.id.time, 8);
            contentView.setImageViewIcon(C4337R.id.profile_badge, null);
            contentView.setViewVisibility(C4337R.id.profile_badge, 8);
            this.f20mN.mUsesStandardHeader = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public RemoteViews applyStandardTemplate(int resId, StandardTemplateParams p, TemplateBindResult result) {
            boolean rightIcon;
            p.headerless(resId == getBaseLayoutResource() || resId == getHeadsUpBaseLayoutResource() || resId == getMessagingLayoutResource() || resId == 17367256 || resId == 17367251 || resId == 17367260);
            RemoteViews contentView = new BuilderRemoteViews(this.mContext.getApplicationInfo(), resId);
            resetStandardTemplate(contentView);
            Bundle ex = this.f20mN.extras;
            updateBackgroundColor(contentView, p);
            bindNotificationHeader(contentView, p);
            bindLargeIconAndApplyMargin(contentView, p, result);
            if (resId == getBigBaseLayoutResource() || resId == getBigTextLayoutResource()) {
                if (p.mHideRightIcon) {
                    rightIcon = false;
                } else {
                    rightIcon = (p.mPromotedPicture == null && this.f20mN.mLargeIcon == null) ? false : true;
                }
                if (!rightIcon) {
                    Resources resources = this.mContext.getResources();
                    float density = resources.getDisplayMetrics().density;
                    float contentMarginDp = resources.getDimension(C4337R.dimen.notification_content_margin_end) / density;
                    contentView.setViewLayoutMargin(p.mTextViewId, 5, contentMarginDp, 1);
                }
            }
            boolean showProgress = handleProgressBar(contentView, ex, p);
            boolean hasSecondLine = showProgress;
            if (p.hasTitle()) {
                contentView.setViewVisibility(p.mTitleViewId, 0);
                contentView.setTextViewText(p.mTitleViewId, ensureColorSpanContrast(p.mTitle, p));
                setTextViewColorPrimary(contentView, p.mTitleViewId, p);
            } else if (p.mTitleViewId != 16908310) {
                contentView.setViewVisibility(p.mTitleViewId, 8);
                contentView.setTextViewText(p.mTitleViewId, null);
            }
            if (p.mText != null && p.mText.length() != 0 && (!showProgress || p.mAllowTextWithProgress)) {
                contentView.setViewVisibility(p.mTextViewId, 0);
                contentView.setTextViewText(p.mTextViewId, ensureColorSpanContrast(p.mText, p));
                setTextViewColorSecondary(contentView, p.mTextViewId, p);
                hasSecondLine = true;
            } else if (p.mTextViewId != 16909857) {
                contentView.setViewVisibility(p.mTextViewId, 8);
                contentView.setTextViewText(p.mTextViewId, null);
            }
            setHeaderlessVerticalMargins(this.mContext, contentView, p, hasSecondLine);
            return contentView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setHeaderlessVerticalMargins(Context context, RemoteViews contentView, StandardTemplateParams p, boolean hasSecondLine) {
            int marginDimen;
            if (!p.mHeaderless) {
                int marginDimen2 = Notification.getFontScaledMarginHeight(context, C4337R.dimen.notification_content_margin_top);
                contentView.setViewLayoutMargin(C4337R.id.notification_main_column, 1, marginDimen2, 0);
                return;
            }
            if (hasSecondLine) {
                marginDimen = C4337R.dimen.notification_headerless_margin_twoline;
            } else {
                marginDimen = C4337R.dimen.notification_headerless_margin_oneline;
            }
            contentView.setViewLayoutMarginDimen(C4337R.id.notification_headerless_view_column, 1, marginDimen);
            contentView.setViewLayoutMarginDimen(C4337R.id.notification_headerless_view_column, 3, marginDimen);
        }

        private static void setHeaderlessVerticalMargins(RemoteViews contentView, StandardTemplateParams p, boolean hasSecondLine) {
            int marginDimen;
            if (!p.mHeaderless) {
                return;
            }
            if (hasSecondLine) {
                marginDimen = C4337R.dimen.notification_headerless_margin_twoline;
            } else {
                marginDimen = C4337R.dimen.notification_headerless_margin_oneline;
            }
            contentView.setViewLayoutMarginDimen(C4337R.id.notification_headerless_view_column, 1, marginDimen);
            contentView.setViewLayoutMarginDimen(C4337R.id.notification_headerless_view_column, 3, marginDimen);
        }

        private CharSequence processTextSpans(CharSequence text) {
            return text;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTextViewColorPrimary(RemoteViews contentView, int id, StandardTemplateParams p) {
            contentView.setTextColor(id, getPrimaryTextColor(p));
        }

        public int getPrimaryTextColor(StandardTemplateParams p) {
            return getColors(p).getPrimaryTextColor();
        }

        public int getSecondaryTextColor(StandardTemplateParams p) {
            return getColors(p).getSecondaryTextColor();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTextViewColorSecondary(RemoteViews contentView, int id, StandardTemplateParams p) {
            contentView.setTextColor(id, getSecondaryTextColor(p));
        }

        public int getThirdTextColor(StandardTemplateParams p) {
            return getColors(p).getThirdTextColor();
        }

        private void setTextViewColorThird(RemoteViews contentView, int id, StandardTemplateParams p) {
            contentView.setTextColor(id, getThirdTextColor(p));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Colors getColors(StandardTemplateParams p) {
            this.mColors.resolvePalette(this.mContext, this.f20mN.color, isBackgroundColorized(p), this.mInNightMode);
            return this.mColors;
        }

        private void updateBackgroundColor(RemoteViews contentView, StandardTemplateParams p) {
            if (isBackgroundColorized(p)) {
                contentView.setInt(C4337R.id.status_bar_latest_event_content, "setBackgroundColor", getBackgroundColor(p));
            } else {
                contentView.setInt(C4337R.id.status_bar_latest_event_content, "setBackgroundResource", 0);
            }
        }

        private boolean handleProgressBar(RemoteViews contentView, Bundle ex, StandardTemplateParams p) {
            int max = ex.getInt(Notification.EXTRA_PROGRESS_MAX, 0);
            int progress = ex.getInt(Notification.EXTRA_PROGRESS, 0);
            boolean ind = ex.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE);
            if (!p.mHideProgress && (max != 0 || ind)) {
                contentView.setViewVisibility(16908301, 0);
                contentView.setProgressBar(16908301, max, progress, ind);
                contentView.setProgressBackgroundTintList(16908301, this.mContext.getColorStateList(C4337R.color.notification_progress_background_color));
                ColorStateList progressTint = ColorStateList.valueOf(getPrimaryAccentColor(p));
                contentView.setProgressTintList(16908301, progressTint);
                contentView.setProgressIndeterminateTintList(16908301, progressTint);
                return true;
            }
            contentView.setViewVisibility(16908301, 8);
            return false;
        }

        private void bindLargeIconAndApplyMargin(RemoteViews remoteViews, StandardTemplateParams standardTemplateParams, TemplateBindResult templateBindResult) {
            if (templateBindResult == null) {
                templateBindResult = new TemplateBindResult();
            }
            bindLargeIcon(remoteViews, standardTemplateParams, templateBindResult);
            if (!standardTemplateParams.mHeaderless) {
                templateBindResult.mHeadingExtraMarginSet.applyToView(remoteViews, C4337R.id.notification_header);
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, 16908310);
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, standardTemplateParams.mTextViewId);
                remoteViews.setInt(standardTemplateParams.mTextViewId, "setNumIndentLines", !standardTemplateParams.hasTitle() ? 1 : 0);
            }
        }

        private void calculateRightIconDimens(Icon rightIcon, boolean isPromotedPicture, TemplateBindResult result) {
            float viewWidthDp;
            Drawable drawable;
            int iconWidth;
            int iconHeight;
            Resources resources = this.mContext.getResources();
            float density = resources.getDisplayMetrics().density;
            float iconMarginDp = resources.getDimension(C4337R.dimen.notification_right_icon_content_margin) / density;
            float contentMarginDp = resources.getDimension(C4337R.dimen.notification_content_margin_end) / density;
            float expanderSizeDp = (resources.getDimension(C4337R.dimen.notification_header_expand_icon_size) / density) - contentMarginDp;
            float viewHeightDp = resources.getDimension(C4337R.dimen.notification_right_icon_size) / density;
            if (rightIcon != null && ((isPromotedPicture || this.mContext.getApplicationInfo().targetSdkVersion >= 31) && (drawable = rightIcon.loadDrawable(this.mContext)) != null && (iconWidth = drawable.getIntrinsicWidth()) > (iconHeight = drawable.getIntrinsicHeight()) && iconHeight > 0)) {
                float maxViewWidthDp = 1.7777778f * viewHeightDp;
                float viewWidthDp2 = Math.min((iconWidth * viewHeightDp) / iconHeight, maxViewWidthDp);
                viewWidthDp = viewWidthDp2;
            } else {
                viewWidthDp = viewHeightDp;
            }
            float extraMarginEndDpIfVisible = viewWidthDp + iconMarginDp;
            result.setRightIconState(rightIcon != null, viewWidthDp, viewHeightDp, extraMarginEndDpIfVisible, expanderSizeDp);
        }

        private void bindLargeIcon(RemoteViews contentView, StandardTemplateParams p, TemplateBindResult result) {
            Icon rightIcon;
            if (this.f20mN.mLargeIcon == null && this.f20mN.largeIcon != null) {
                Notification notification = this.f20mN;
                notification.mLargeIcon = Icon.createWithBitmap(notification.largeIcon);
            }
            Icon leftIcon = p.mHideLeftIcon ? null : this.f20mN.mLargeIcon;
            if (p.mHideRightIcon) {
                rightIcon = null;
            } else {
                rightIcon = p.mPromotedPicture != null ? p.mPromotedPicture : this.f20mN.mLargeIcon;
            }
            if (leftIcon != rightIcon || leftIcon == null) {
                contentView.setImageViewIcon(C4337R.id.left_icon, leftIcon);
                contentView.setIntTag(C4337R.id.left_icon, C4337R.id.tag_uses_right_icon_drawable, 0);
            } else {
                contentView.setIntTag(C4337R.id.left_icon, C4337R.id.tag_uses_right_icon_drawable, 1);
            }
            boolean isPromotedPicture = p.mPromotedPicture != null;
            calculateRightIconDimens(rightIcon, isPromotedPicture, result);
            if (rightIcon != null) {
                contentView.setViewLayoutWidth(C4337R.id.right_icon, result.mRightIconWidthDp, 1);
                contentView.setViewLayoutHeight(C4337R.id.right_icon, result.mRightIconHeightDp, 1);
                contentView.setViewVisibility(C4337R.id.right_icon, 0);
                if (rightIcon.getType() == 4 || (contentView.getLayoutId() == getBigPictureLayoutResource() && this.f20mN.mLargeIcon != null && p.mViewType == StandardTemplateParams.VIEW_TYPE_BIG)) {
                    int rightIconSize = this.mContext.getResources().getDimensionPixelSize(C4337R.dimen.notification_right_icon_size);
                    contentView.setInt(C4337R.id.right_icon, "setMaxDrawableWidth", rightIconSize);
                    contentView.setInt(C4337R.id.right_icon, "setMaxDrawableHeight", rightIconSize);
                }
                contentView.setImageViewIcon(C4337R.id.right_icon, rightIcon);
                contentView.setIntTag(C4337R.id.right_icon, C4337R.id.tag_keep_when_showing_left_icon, isPromotedPicture ? 1 : 0);
                processLargeLegacyIcon(rightIcon, contentView, p);
                return;
            }
            contentView.setImageViewIcon(C4337R.id.right_icon, null);
            contentView.setIntTag(C4337R.id.right_icon, C4337R.id.tag_keep_when_showing_left_icon, 0);
        }

        private void bindNotificationHeader(RemoteViews contentView, StandardTemplateParams p) {
            if (p.mViewType != StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED) {
                bindSmallIcon(contentView, p);
            }
            boolean hasTextToLeft = bindHeaderAppName(contentView, p, false);
            boolean hasTextToLeft2 = hasTextToLeft | bindHeaderTextSecondary(contentView, p, hasTextToLeft);
            boolean hasTextToLeft3 = hasTextToLeft2 | bindHeaderText(contentView, p, hasTextToLeft2);
            if (!hasTextToLeft3) {
                hasTextToLeft3 |= bindHeaderAppName(contentView, p, true);
            }
            bindHeaderChronometerAndTime(contentView, p, hasTextToLeft3);
            bindPhishingAlertIcon(contentView, p);
            bindProfileBadge(contentView, p);
            bindAlertedIcon(contentView, p);
            bindExpandButton(contentView, p);
            if (p.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER) {
                int marginDimen = Notification.getFontScaledHeight(this.mContext, C4337R.dimen.notification_group_icon_margin_top);
                int shadowMarginDimen = this.mContext.getResources().getDimensionPixelSize(C4337R.dimen.notification_group_icon_shadow_margin_top);
                contentView.setViewLayoutMargin(C4337R.id.left_icon, 1, marginDimen, 0);
                contentView.setViewLayoutMargin(16908294, 1, marginDimen, 0);
                contentView.setViewLayoutMargin(C4337R.id.group_icon_shadow, 1, marginDimen + shadowMarginDimen, 0);
            }
            this.f20mN.mUsesStandardHeader = true;
        }

        private void bindExpandButton(RemoteViews contentView, StandardTemplateParams p) {
            int bgColor = getBackgroundColor(p);
            int pillColor = Colors.flattenAlpha(getColors(p).getProtectionColor(), bgColor);
            Colors.flattenAlpha(getPrimaryTextColor(p), pillColor);
            contentView.setInt(C4337R.id.expand_button, "setDefaultTextColor", isBackgroundColorized(p) ? getPrimaryTextColor(p) : getThirdTextColor(p));
            contentView.setInt(C4337R.id.expand_button, "setDefaultPillColor", pillColor);
            if (p.mHighlightExpander) {
                pillColor = Colors.flattenAlpha(getColors(p).getTertiaryAccentColor(), bgColor);
                Colors.flattenAlpha(getColors(p).getOnAccentTextColor(), pillColor);
            }
            contentView.setInt(C4337R.id.expand_button, "setHighlightTextColor", getSecondaryTextColor(p));
            contentView.setInt(C4337R.id.expand_button, "setHighlightPillColor", pillColor);
            if (p.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER || p.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED) {
                int marginDimen = Notification.getFontScaledMarginHeight(this.mContext, C4337R.dimen.notification_content_margin);
                contentView.setViewLayoutMargin(C4337R.id.expand_button, 1, marginDimen, 0);
            } else if (p.mViewType != StandardTemplateParams.VIEW_TYPE_MINIMIZED) {
                int marginDimen2 = Notification.getFontScaledHeight(this.mContext, C4337R.dimen.notification_content_margin);
                contentView.setViewLayoutMargin(C4337R.id.expand_button, 1, marginDimen2, 0);
            }
        }

        private void bindHeaderChronometerAndTime(RemoteViews contentView, StandardTemplateParams p, boolean hasTextToLeft) {
            if (!p.mHideTime && showsTimeOrChronometer()) {
                if (hasTextToLeft) {
                    contentView.setViewVisibility(C4337R.id.time_divider, 8);
                    setTextViewColorSecondary(contentView, C4337R.id.time_divider, p);
                }
                if (this.f20mN.extras.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER)) {
                    contentView.setViewVisibility(C4337R.id.chronometer, 0);
                    contentView.setLong(C4337R.id.chronometer, "setBase", this.f20mN.when + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                    contentView.setBoolean(C4337R.id.chronometer, "setStarted", true);
                    boolean countsDown = this.f20mN.extras.getBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN);
                    contentView.setChronometerCountDown(C4337R.id.chronometer, countsDown);
                    setTextViewColorThird(contentView, C4337R.id.chronometer, p);
                    return;
                }
                contentView.setViewVisibility(C4337R.id.time, 0);
                contentView.setLong(C4337R.id.time, "setTime", this.f20mN.when);
                setTextViewColorThird(contentView, C4337R.id.time, p);
                return;
            }
            contentView.setLong(C4337R.id.time, "setTime", this.f20mN.when != 0 ? this.f20mN.when : this.f20mN.creationTime);
            setTextViewColorThird(contentView, C4337R.id.time, p);
        }

        private boolean bindHeaderText(RemoteViews contentView, StandardTemplateParams p, boolean hasTextToLeft) {
            Style style;
            if (p.mHideSubText) {
                return false;
            }
            CharSequence headerText = p.mSubText;
            if (headerText == null && (style = this.mStyle) != null && style.mSummaryTextSet && this.mStyle.hasSummaryInHeader()) {
                headerText = this.mStyle.mSummaryText;
            }
            if (headerText == null && this.mContext.getApplicationInfo().targetSdkVersion < 24 && this.f20mN.extras.getCharSequence(Notification.EXTRA_INFO_TEXT) != null) {
                headerText = this.f20mN.extras.getCharSequence(Notification.EXTRA_INFO_TEXT);
            }
            if (TextUtils.isEmpty(headerText)) {
                return false;
            }
            contentView.setTextViewText(C4337R.id.header_text, ensureColorSpanContrast(processLegacyText(headerText), p));
            setTextViewColorThird(contentView, C4337R.id.header_text, p);
            contentView.setViewVisibility(C4337R.id.header_text, 0);
            if (hasTextToLeft) {
                contentView.setViewVisibility(C4337R.id.header_text_divider, 8);
                setTextViewColorSecondary(contentView, C4337R.id.header_text_divider, p);
                return true;
            }
            contentView.setViewLayoutMarginDimen(C4337R.id.header_text, 4, 0);
            return true;
        }

        private boolean bindHeaderTextSecondary(RemoteViews contentView, StandardTemplateParams p, boolean hasTextToLeft) {
            if (p.mHideSubText || TextUtils.isEmpty(p.mHeaderTextSecondary)) {
                return false;
            }
            contentView.setTextViewText(C4337R.id.header_text_secondary, ensureColorSpanContrast(processLegacyText(p.mHeaderTextSecondary), p));
            setTextViewColorThird(contentView, C4337R.id.header_text_secondary, p);
            contentView.setViewVisibility(C4337R.id.header_text_secondary, 0);
            if (hasTextToLeft) {
                contentView.setViewVisibility(C4337R.id.header_text_secondary_divider, 8);
                setTextViewColorSecondary(contentView, C4337R.id.header_text_secondary_divider, p);
                return true;
            }
            return true;
        }

        public String loadHeaderAppName() {
            CharSequence name = null;
            PackageManager pm = this.mContext.getPackageManager();
            if (this.f20mN.extras.containsKey(Notification.EXTRA_SUBSTITUTE_APP_NAME)) {
                String pkg = this.mContext.getPackageName();
                String subName = this.f20mN.extras.getString(Notification.EXTRA_SUBSTITUTE_APP_NAME);
                if (pm.checkPermission(Manifest.permission.SUBSTITUTE_NOTIFICATION_APP_NAME, pkg) != 0) {
                    Log.m102w(Notification.TAG, "warning: pkg " + pkg + " attempting to substitute app name '" + subName + "' without holding perm " + Manifest.permission.SUBSTITUTE_NOTIFICATION_APP_NAME);
                } else {
                    name = subName;
                }
            }
            if (TextUtils.isEmpty(name)) {
                name = pm.getApplicationLabel(this.mContext.getApplicationInfo());
            }
            if (TextUtils.isEmpty(name)) {
                return null;
            }
            return String.valueOf(name);
        }

        private boolean bindHeaderAppName(RemoteViews contentView, StandardTemplateParams p, boolean force) {
            if (p.mViewType == StandardTemplateParams.VIEW_TYPE_MINIMIZED && !force) {
                return false;
            }
            if (p.mHeaderless && p.hasTitle()) {
                return true;
            }
            if (p.mHideAppName) {
                return p.hasTitle();
            }
            contentView.setViewVisibility(C4337R.id.app_name_text, 0);
            contentView.setTextViewText(C4337R.id.app_name_text, loadHeaderAppName());
            contentView.setTextColor(C4337R.id.app_name_text, getThirdTextColor(p));
            return true;
        }

        private boolean isBackgroundColorized(StandardTemplateParams p) {
            return p.allowColorization && this.f20mN.isColorized();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isCallActionColorCustomizable() {
            return this.f20mN.isColorized() && this.mContext.getResources().getBoolean(C4337R.bool.config_callNotificationActionColorsRequireColorized);
        }

        private void bindSmallIcon(RemoteViews contentView, StandardTemplateParams p) {
            if (this.f20mN.mSmallIcon == null && this.f20mN.icon != 0) {
                Notification notification = this.f20mN;
                notification.mSmallIcon = Icon.createWithResource(this.mContext, notification.icon);
            }
            contentView.setImageViewIcon(16908294, this.f20mN.mSmallIcon);
            contentView.setInt(16908294, "setImageLevel", this.f20mN.iconLevel);
            processSmallIconColor(this.f20mN.mSmallIcon, contentView, p);
        }

        private boolean showsTimeOrChronometer() {
            return this.f20mN.showsTime() || this.f20mN.showsChronometer();
        }

        private void resetStandardTemplateWithActions(RemoteViews big) {
            big.setViewVisibility(C4337R.id.actions, 8);
            big.removeAllViews(C4337R.id.actions);
            big.setViewVisibility(C4337R.id.notification_material_reply_container, 8);
            big.setTextViewText(C4337R.id.notification_material_reply_text_1, null);
            big.setViewVisibility(C4337R.id.notification_material_reply_text_1_container, 8);
            big.setViewVisibility(C4337R.id.notification_material_reply_progress, 8);
            big.setViewVisibility(C4337R.id.notification_material_reply_text_2, 8);
            big.setTextViewText(C4337R.id.notification_material_reply_text_2, null);
            big.setViewVisibility(C4337R.id.notification_material_reply_text_3, 8);
            big.setTextViewText(C4337R.id.notification_material_reply_text_3, null);
            big.setViewLayoutMarginDimen(C4337R.id.notification_action_list_margin_target, 3, C4337R.dimen.notification_content_margin);
        }

        private void bindSnoozeAction(RemoteViews big, StandardTemplateParams p) {
            boolean hideSnoozeButton = this.f20mN.isFgsOrUij() || this.f20mN.fullScreenIntent != null || isBackgroundColorized(p) || p.mViewType != StandardTemplateParams.VIEW_TYPE_BIG;
            big.setBoolean(C4337R.id.snooze_button, "setEnabled", !hideSnoozeButton);
            if (hideSnoozeButton) {
                big.setViewVisibility(C4337R.id.snooze_button, 8);
            }
            boolean snoozeEnabled = (hideSnoozeButton || this.mContext.getContentResolver() == null || !isSnoozeSettingEnabled()) ? false : true;
            if (snoozeEnabled) {
                big.setViewLayoutMarginDimen(C4337R.id.notification_action_list_margin_target, 3, 0);
            }
        }

        private boolean isSnoozeSettingEnabled() {
            try {
                return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), Settings.Secure.SHOW_NOTIFICATION_SNOOZE, 0, -2) == 1;
            } catch (SecurityException e) {
                return false;
            }
        }

        private List<Action> getNonContextualActions() {
            if (this.mActions == null) {
                return Collections.emptyList();
            }
            List<Action> standardActions = new ArrayList<>();
            Iterator<Action> it = this.mActions.iterator();
            while (it.hasNext()) {
                Action action = it.next();
                if (!action.isContextual()) {
                    standardActions.add(action);
                }
            }
            return standardActions;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public RemoteViews applyStandardTemplateWithActions(int layoutId, StandardTemplateParams p, TemplateBindResult result) {
            int i;
            RemoteViews big = applyStandardTemplate(layoutId, p, result);
            resetStandardTemplateWithActions(big);
            bindSnoozeAction(big, p);
            ColorStateList actionColor = ColorStateList.valueOf(getStandardActionColor(p));
            big.setColorStateList(C4337R.id.snooze_button, "setImageTintList", actionColor);
            big.setColorStateList(C4337R.id.bubble_button, "setImageTintList", actionColor);
            boolean validRemoteInput = false;
            List<Action> nonContextualActions = getNonContextualActions();
            int numActions = Math.min(nonContextualActions.size(), 3);
            boolean emphasizedMode = p.mCallStyleActions;
            if (p.mCallStyleActions) {
                big.setViewPadding(C4337R.id.actions, 0, 0, 0, 0);
                big.setInt(C4337R.id.actions, "setCollapsibleIndentDimen", C4337R.dimen.call_notification_collapsible_indent);
            }
            big.setBoolean(C4337R.id.actions, "setEmphasizedMode", emphasizedMode);
            if (numActions > 0 && !p.mHideActions) {
                big.setViewVisibility(C4337R.id.actions_container, 0);
                big.setViewVisibility(C4337R.id.actions, 0);
                big.setViewLayoutMarginDimen(C4337R.id.notification_action_list_margin_target, 3, 0);
                for (int i2 = 0; i2 < numActions; i2++) {
                    Action action = nonContextualActions.get(i2);
                    boolean actionHasValidInput = hasValidRemoteInput(action);
                    validRemoteInput |= actionHasValidInput;
                    RemoteViews button = generateActionButton(action, emphasizedMode, p);
                    if (emphasizedMode && i2 > 0) {
                        button.setViewLayoutMarginDimen(C4337R.id.action0, 4, 0);
                    }
                    big.addView(C4337R.id.actions, button);
                }
                i = 8;
            } else {
                i = 8;
                big.setViewVisibility(C4337R.id.actions_container, 8);
            }
            RemoteInputHistoryItem[] replyText = (RemoteInputHistoryItem[]) Notification.getParcelableArrayFromBundle(this.f20mN.extras, Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class);
            if (validRemoteInput && replyText != null && replyText.length > 0 && !TextUtils.isEmpty(replyText[0].getText()) && p.maxRemoteInputHistory > 0) {
                boolean showSpinner = this.f20mN.extras.getBoolean(Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER);
                big.setViewVisibility(C4337R.id.notification_material_reply_container, 0);
                big.setViewVisibility(C4337R.id.notification_material_reply_text_1_container, 0);
                big.setTextViewText(C4337R.id.notification_material_reply_text_1, ensureColorSpanContrast(replyText[0].getText(), p));
                setTextViewColorSecondary(big, C4337R.id.notification_material_reply_text_1, p);
                if (showSpinner) {
                    i = 0;
                }
                big.setViewVisibility(C4337R.id.notification_material_reply_progress, i);
                big.setProgressIndeterminateTintList(C4337R.id.notification_material_reply_progress, ColorStateList.valueOf(getPrimaryAccentColor(p)));
                if (replyText.length > 1 && !TextUtils.isEmpty(replyText[1].getText()) && p.maxRemoteInputHistory > 1) {
                    big.setViewVisibility(C4337R.id.notification_material_reply_text_2, 0);
                    big.setTextViewText(C4337R.id.notification_material_reply_text_2, ensureColorSpanContrast(replyText[1].getText(), p));
                    setTextViewColorSecondary(big, C4337R.id.notification_material_reply_text_2, p);
                    if (replyText.length > 2 && !TextUtils.isEmpty(replyText[2].getText()) && p.maxRemoteInputHistory > 2) {
                        big.setViewVisibility(C4337R.id.notification_material_reply_text_3, 0);
                        big.setTextViewText(C4337R.id.notification_material_reply_text_3, ensureColorSpanContrast(replyText[2].getText(), p));
                        setTextViewColorSecondary(big, C4337R.id.notification_material_reply_text_3, p);
                    }
                }
            }
            return big;
        }

        private boolean hasValidRemoteInput(Action action) {
            RemoteInput[] remoteInputs;
            if (TextUtils.isEmpty(action.title) || action.actionIntent == null || (remoteInputs = action.getRemoteInputs()) == null) {
                return false;
            }
            for (RemoteInput r : remoteInputs) {
                CharSequence[] choices = r.getChoices();
                if (r.getAllowFreeFormInput()) {
                    return true;
                }
                if (choices != null && choices.length != 0) {
                    return true;
                }
            }
            return false;
        }

        public RemoteViews createContentView() {
            return createContentView(false);
        }

        private boolean fullyCustomViewRequiresDecoration(boolean fromStyle) {
            return !(fromStyle && Notification.PLATFORM_STYLE_CLASSES.contains(this.mStyle.getClass())) && this.mContext.getApplicationInfo().targetSdkVersion >= 31;
        }

        private RemoteViews minimallyDecoratedContentView(RemoteViews customContent) {
            StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).decorationType(1).fillTextsFrom(this);
            TemplateBindResult result = new TemplateBindResult();
            RemoteViews standard = applyStandardTemplate(getBaseLayoutResource(), p, result);
            Notification.buildCustomContentIntoTemplate(this.mContext, standard, customContent, p, result);
            return standard;
        }

        private RemoteViews minimallyDecoratedBigContentView(RemoteViews customContent) {
            StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).decorationType(1).fillTextsFrom(this);
            TemplateBindResult result = new TemplateBindResult();
            RemoteViews standard = applyStandardTemplateWithActions(getBigBaseLayoutResource(), p, result);
            Notification.buildCustomContentIntoTemplate(this.mContext, standard, customContent, p, result);
            makeHeaderExpanded(standard);
            return standard;
        }

        private RemoteViews minimallyDecoratedHeadsUpContentView(RemoteViews customContent) {
            StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).decorationType(1).fillTextsFrom(this);
            TemplateBindResult result = new TemplateBindResult();
            RemoteViews standard = applyStandardTemplateWithActions(getHeadsUpBaseLayoutResource(), p, result);
            Notification.buildCustomContentIntoTemplate(this.mContext, standard, customContent, p, result);
            return standard;
        }

        public RemoteViews createContentView(boolean increasedHeight) {
            RemoteViews styleView;
            if (useExistingRemoteView(this.f20mN.contentView)) {
                return fullyCustomViewRequiresDecoration(false) ? minimallyDecoratedContentView(this.f20mN.contentView) : this.f20mN.contentView;
            }
            Style style = this.mStyle;
            if (style != null && (styleView = style.makeContentView(increasedHeight)) != null) {
                return fullyCustomViewRequiresDecoration(true) ? minimallyDecoratedContentView(styleView) : styleView;
            }
            StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).fillTextsFrom(this);
            return applyStandardTemplate(getBaseLayoutResource(), p, null);
        }

        private boolean useExistingRemoteView(RemoteViews customContent) {
            if (customContent == null || styleDisplaysCustomViewInline()) {
                return false;
            }
            if (fullyCustomViewRequiresDecoration(false) && Notification.STANDARD_LAYOUTS.contains(Integer.valueOf(customContent.getLayoutId()))) {
                Log.m102w(Notification.TAG, "For apps targeting S, a custom content view that is a modified version of any standard layout is disallowed.");
                return false;
            }
            return true;
        }

        public RemoteViews createBigContentView() {
            RemoteViews result = null;
            if (useExistingRemoteView(this.f20mN.bigContentView)) {
                return fullyCustomViewRequiresDecoration(false) ? minimallyDecoratedBigContentView(this.f20mN.bigContentView) : this.f20mN.bigContentView;
            }
            Style style = this.mStyle;
            if (style != null) {
                result = style.makeBigContentView();
                if (fullyCustomViewRequiresDecoration(true)) {
                    result = minimallyDecoratedBigContentView(result);
                }
            }
            if (result == null && bigContentViewRequired()) {
                StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).allowTextWithProgress(true).fillTextsFrom(this);
                result = applyStandardTemplateWithActions(getBigBaseLayoutResource(), p, null);
            }
            makeHeaderExpanded(result);
            return result;
        }

        private boolean bigContentViewRequired() {
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 31) {
                return true;
            }
            boolean exempt = this.f20mN.contentView != null && this.f20mN.bigContentView == null && this.mStyle == null && this.mActions.size() == 0;
            return !exempt;
        }

        public RemoteViews makeNotificationGroupHeader() {
            return makeNotificationHeader(this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_GROUP_HEADER).fillTextsFrom(this));
        }

        public RemoteViews makeNotificationGroupHeaderExpanded() {
            return makeNotificationHeader(this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED).fillTextsFrom(this));
        }

        private RemoteViews makeNotificationHeader(StandardTemplateParams p) {
            RemoteViews header;
            p.disallowColorization();
            if (p.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER) {
                header = new BuilderRemoteViews(this.mContext.getApplicationInfo(), C4337R.layout.notification_template_group_header);
            } else if (p.mViewType == StandardTemplateParams.VIEW_TYPE_GROUP_HEADER_EXPANDED) {
                header = new BuilderRemoteViews(this.mContext.getApplicationInfo(), C4337R.layout.notification_template_group_header_expanded);
            } else if (p.mViewType == StandardTemplateParams.VIEW_TYPE_MINIMIZED || p.mViewType == StandardTemplateParams.VIEW_TYPE_PUBLIC) {
                header = new BuilderRemoteViews(this.mContext.getApplicationInfo(), C4337R.layout.notification_template_header_minimized);
            } else {
                header = new BuilderRemoteViews(this.mContext.getApplicationInfo(), C4337R.layout.notification_template_header);
            }
            resetNotificationHeader(header);
            bindNotificationHeader(header, p);
            return header;
        }

        public RemoteViews makeAmbientNotification() {
            RemoteViews headsUpContentView = createHeadsUpContentView(false);
            if (headsUpContentView != null) {
                return headsUpContentView;
            }
            return createContentView();
        }

        public static void makeHeaderExpanded(RemoteViews result) {
            if (result != null) {
                result.setBoolean(C4337R.id.expand_button, "setExpanded", true);
            }
        }

        public RemoteViews createHeadsUpContentView(boolean increasedHeight) {
            if (useExistingRemoteView(this.f20mN.headsUpContentView)) {
                if (fullyCustomViewRequiresDecoration(false)) {
                    return minimallyDecoratedHeadsUpContentView(this.f20mN.headsUpContentView);
                }
                return this.f20mN.headsUpContentView;
            }
            Style style = this.mStyle;
            if (style != null) {
                RemoteViews styleView = style.makeHeadsUpContentView(increasedHeight);
                if (styleView != null) {
                    return fullyCustomViewRequiresDecoration(true) ? minimallyDecoratedHeadsUpContentView(styleView) : styleView;
                }
            } else if (this.mActions.size() == 0) {
                return null;
            }
            StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this).setMaxRemoteInputHistory(1);
            return applyStandardTemplateWithActions(getHeadsUpBaseLayoutResource(), p, null);
        }

        public RemoteViews createHeadsUpContentView() {
            return createHeadsUpContentView(false);
        }

        public RemoteViews makePublicContentView(boolean isLowPriority) {
            return makePublicContentView(isLowPriority, false);
        }

        public RemoteViews makePublicContentView(boolean isLowPriority, boolean ignorePublic) {
            if (this.f20mN.publicVersion != null && !ignorePublic) {
                Builder builder = recoverBuilder(this.mContext, this.f20mN.publicVersion);
                return builder.createContentView();
            }
            Bundle savedBundle = this.f20mN.extras;
            Style style = this.mStyle;
            this.mStyle = null;
            Icon largeIcon = this.f20mN.mLargeIcon;
            this.f20mN.mLargeIcon = null;
            Bitmap largeIconLegacy = this.f20mN.largeIcon;
            this.f20mN.largeIcon = null;
            ArrayList<Action> actions = this.mActions;
            this.mActions = new ArrayList<>();
            Bundle publicExtras = new Bundle();
            publicExtras.putBoolean(Notification.EXTRA_SHOW_WHEN, savedBundle.getBoolean(Notification.EXTRA_SHOW_WHEN));
            publicExtras.putBoolean(Notification.EXTRA_SHOW_CHRONOMETER, savedBundle.getBoolean(Notification.EXTRA_SHOW_CHRONOMETER));
            publicExtras.putBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN, savedBundle.getBoolean(Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
            String appName = savedBundle.getString(Notification.EXTRA_SUBSTITUTE_APP_NAME);
            if (appName != null) {
                publicExtras.putString(Notification.EXTRA_SUBSTITUTE_APP_NAME, appName);
            }
            this.f20mN.extras = publicExtras;
            StandardTemplateParams params = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_PUBLIC).fillTextsFrom(this);
            if (isLowPriority) {
                params.highlightExpander(false);
            }
            RemoteViews view = makeNotificationHeader(params);
            view.setBoolean(C4337R.id.notification_header, "setExpandOnlyOnButton", true);
            view.setBoolean(C4337R.id.notification_header, "styleTextAsTitle", true);
            this.f20mN.extras = savedBundle;
            this.f20mN.mLargeIcon = largeIcon;
            this.f20mN.largeIcon = largeIconLegacy;
            this.mActions = actions;
            this.mStyle = style;
            return view;
        }

        public RemoteViews makeLowPriorityContentView(boolean useRegularSubtext) {
            StandardTemplateParams p = this.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_MINIMIZED).highlightExpander(false).fillTextsFrom(this);
            if (!useRegularSubtext || TextUtils.isEmpty(p.mSubText)) {
                p.summaryText(createSummaryText());
            }
            RemoteViews header = makeNotificationHeader(p);
            header.setBoolean(C4337R.id.notification_header, "setAcceptAllTouches", true);
            header.setBoolean(C4337R.id.notification_header, "styleTextAsTitle", true);
            return header;
        }

        private CharSequence createSummaryText() {
            CharSequence titleText = this.f20mN.extras.getCharSequence(Notification.EXTRA_TITLE);
            if (USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY) {
                return titleText;
            }
            SpannableStringBuilder summary = new SpannableStringBuilder();
            if (titleText == null) {
                titleText = this.f20mN.extras.getCharSequence(Notification.EXTRA_TITLE_BIG);
            }
            BidiFormatter bidi = BidiFormatter.getInstance();
            if (titleText != null) {
                summary.append(bidi.unicodeWrap(titleText));
            }
            CharSequence contentText = this.f20mN.extras.getCharSequence(Notification.EXTRA_TEXT);
            if (titleText != null && contentText != null) {
                summary.append(bidi.unicodeWrap(this.mContext.getText(C4337R.string.notification_header_divider_symbol_with_spaces)));
            }
            if (contentText != null) {
                summary.append(bidi.unicodeWrap(contentText));
            }
            return summary;
        }

        private RemoteViews generateActionButton(Action action, boolean emphasizedMode, StandardTemplateParams p) {
            CharSequence title;
            boolean tombstone = action.actionIntent == null;
            RemoteViews button = new BuilderRemoteViews(this.mContext.getApplicationInfo(), getActionButtonLayoutResource(emphasizedMode, tombstone));
            if (!tombstone) {
                button.setOnClickPendingIntent(C4337R.id.action0, action.actionIntent);
            }
            button.setContentDescription(C4337R.id.action0, action.title);
            if (action.mRemoteInputs != null) {
                button.setRemoteInputs(C4337R.id.action0, action.mRemoteInputs);
            }
            if (!emphasizedMode) {
                button.setTextViewText(C4337R.id.action0, ensureColorSpanContrast(action.title, p));
                button.setTextColor(C4337R.id.action0, getStandardActionColor(p));
            } else {
                CharSequence title2 = action.title;
                int buttonFillColor = getColors(p).getSecondaryAccentColor();
                if (tombstone) {
                    Context context = this.mContext;
                    buttonFillColor = setAlphaComponentByFloatDimen(context, ContrastColorUtil.resolveSecondaryColor(context, getColors(p).getBackgroundColor(), this.mInNightMode), C4337R.dimen.notification_action_disabled_container_alpha);
                }
                if (isLegacy()) {
                    title = ContrastColorUtil.clearColorSpans(title2);
                } else {
                    Integer fullLengthColor = getFullLengthSpanColor(title2);
                    if (fullLengthColor != null) {
                        int notifBackgroundColor = getColors(p).getBackgroundColor();
                        buttonFillColor = ensureButtonFillContrast(fullLengthColor.intValue(), notifBackgroundColor);
                    }
                    title = ContrastColorUtil.ensureColorSpanContrast(title2, buttonFillColor);
                }
                button.setTextViewText(C4337R.id.action0, ensureColorSpanContrast(title, p));
                int textColor = ContrastColorUtil.resolvePrimaryColor(this.mContext, buttonFillColor, this.mInNightMode);
                if (tombstone) {
                    Context context2 = this.mContext;
                    textColor = setAlphaComponentByFloatDimen(context2, ContrastColorUtil.resolveSecondaryColor(context2, getColors(p).getBackgroundColor(), this.mInNightMode), C4337R.dimen.notification_action_disabled_content_alpha);
                }
                button.setTextColor(C4337R.id.action0, textColor);
                int rippleColor = (16777215 & textColor) | Enums.AUDIO_FORMAT_DTS_UHD_P2;
                button.setColorStateList(C4337R.id.action0, "setRippleColor", ColorStateList.valueOf(rippleColor));
                button.setColorStateList(C4337R.id.action0, "setButtonBackground", ColorStateList.valueOf(buttonFillColor));
                if (p.mCallStyleActions) {
                    boolean priority = action.getExtras().getBoolean("key_action_priority");
                    button.setBoolean(C4337R.id.action0, "setIsPriority", priority);
                    int minWidthDimen = priority ? C4337R.dimen.call_notification_system_action_min_width : 0;
                    button.setIntDimen(C4337R.id.action0, "setMinimumWidth", minWidthDimen);
                }
            }
            int actionIndex = this.mActions.indexOf(action);
            if (actionIndex != -1) {
                button.setIntTag(C4337R.id.action0, C4337R.id.notification_action_index_tag, actionIndex);
            }
            return button;
        }

        private int getActionButtonLayoutResource(boolean emphasizedMode, boolean tombstone) {
            return emphasizedMode ? tombstone ? getEmphasizedTombstoneActionLayoutResource() : getEmphasizedActionLayoutResource() : tombstone ? getActionTombstoneLayoutResource() : getActionLayoutResource();
        }

        private static int setAlphaComponentByFloatDimen(Context context, int color, int alphaDimenResId) {
            TypedValue alphaValue = new TypedValue();
            context.getResources().getValue(alphaDimenResId, alphaValue, true);
            return ColorUtils.setAlphaComponent(color, Math.round(alphaValue.getFloat() * 255.0f));
        }

        public static Integer getFullLengthSpanColor(CharSequence charSequence) {
            Integer result = null;
            if (charSequence instanceof Spanned) {
                Spanned ss = (Spanned) charSequence;
                Object[] spans = ss.getSpans(0, ss.length(), Object.class);
                for (Object span : spans) {
                    int spanStart = ss.getSpanStart(span);
                    int spanEnd = ss.getSpanEnd(span);
                    boolean fullLength = spanEnd - spanStart == charSequence.length();
                    if (fullLength) {
                        if (span instanceof TextAppearanceSpan) {
                            TextAppearanceSpan originalSpan = (TextAppearanceSpan) span;
                            ColorStateList textColor = originalSpan.getTextColor();
                            if (textColor != null) {
                                result = Integer.valueOf(textColor.getDefaultColor());
                            }
                        } else if (span instanceof ForegroundColorSpan) {
                            ForegroundColorSpan originalSpan2 = (ForegroundColorSpan) span;
                            result = Integer.valueOf(originalSpan2.getForegroundColor());
                        }
                    }
                }
            }
            return result;
        }

        public CharSequence ensureColorSpanContrast(CharSequence charSequence, StandardTemplateParams p) {
            return ContrastColorUtil.ensureColorSpanContrast(charSequence, getBackgroundColor(p));
        }

        public static boolean isColorDark(int color) {
            return ContrastColorUtil.calculateLuminance(color) <= 0.17912878474d;
        }

        public static int ensureButtonFillContrast(int color, int bg) {
            if (isColorDark(bg)) {
                return ContrastColorUtil.findContrastColorAgainstDark(color, bg, true, 1.3d);
            }
            return ContrastColorUtil.findContrastColor(color, bg, true, 1.3d);
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
            boolean isAlreadyLightText = isLegacy() || textColorsNeedInversion();
            if (isAlreadyLightText) {
                return getColorUtil().invertCharSequenceColors(charSequence);
            }
            return charSequence;
        }

        private void processSmallIconColor(Icon smallIcon, RemoteViews contentView, StandardTemplateParams p) {
            boolean colorable = getColorUtil().isGrayscaleIcon(this.mContext, smallIcon);
            int color = getSmallIconColor(p);
            contentView.setInt(16908294, "setBackgroundColor", getBackgroundColor(p));
            contentView.setInt(16908294, "setOriginalIconColor", colorable ? color : 1);
            contentView.setBoolean(16908294, "updateColorizedIconTint", isBackgroundColorized(p));
        }

        private void processLargeLegacyIcon(Icon largeIcon, RemoteViews contentView, StandardTemplateParams p) {
            if (largeIcon != null && isLegacy() && getColorUtil().isGrayscaleIcon(this.mContext, largeIcon)) {
                int color = getSmallIconColor(p);
                contentView.setInt(16908294, "setOriginalIconColor", color);
            }
        }

        private void sanitizeColor() {
            if (this.f20mN.color != 0) {
                this.f20mN.color |= -16777216;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getStandardActionColor(StandardTemplateParams p) {
            return (this.mTintActionButtons || isBackgroundColorized(p)) ? getPrimaryAccentColor(p) : getSecondaryTextColor(p);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getSmallIconColor(StandardTemplateParams p) {
            return getColors(p).getContrastColor();
        }

        private int getPrimaryAccentColor(StandardTemplateParams p) {
            return getColors(p).getPrimaryAccentColor();
        }

        public Notification buildUnstyled() {
            if (this.mActions.size() > 0) {
                this.f20mN.actions = new Action[this.mActions.size()];
                this.mActions.toArray(this.f20mN.actions);
            }
            if (!this.mPersonList.isEmpty()) {
                this.f20mN.extras.putParcelableArrayList(Notification.EXTRA_PEOPLE_LIST, this.mPersonList);
            }
            if (this.f20mN.bigContentView != null || this.f20mN.contentView != null || this.f20mN.headsUpContentView != null) {
                this.f20mN.extras.putBoolean(Notification.EXTRA_CONTAINS_CUSTOM_VIEW, true);
            }
            return this.f20mN;
        }

        public static Builder recoverBuilder(Context context, Notification n) {
            Context builderContext;
            ApplicationInfo applicationInfo = (ApplicationInfo) n.extras.getParcelable(Notification.EXTRA_BUILDER_APPLICATION_INFO, ApplicationInfo.class);
            if (applicationInfo != null) {
                try {
                    builderContext = context.createApplicationContext(applicationInfo, 4);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.m96e(Notification.TAG, "ApplicationInfo " + applicationInfo + " not found");
                    builderContext = context;
                }
            } else {
                builderContext = context;
            }
            return new Builder(builderContext, n);
        }

        public Builder setAllowSystemGeneratedContextualActions(boolean allowed) {
            this.f20mN.mAllowSystemGeneratedContextualActions = allowed;
            return this;
        }

        @Deprecated
        public Notification getNotification() {
            return build();
        }

        public Notification build() {
            if (this.f20mN.mShortcutId != null && this.f20mN.mBubbleMetadata != null && this.f20mN.mBubbleMetadata.getShortcutId() != null && !this.f20mN.mShortcutId.equals(this.f20mN.mBubbleMetadata.getShortcutId())) {
                throw new IllegalArgumentException("Notification and BubbleMetadata shortcut id's don't match, notification: " + this.f20mN.mShortcutId + " vs bubble: " + this.f20mN.mBubbleMetadata.getShortcutId());
            }
            Bundle bundle = this.mUserExtras;
            if (bundle != null) {
                Bundle saveExtras = (Bundle) bundle.clone();
                if (SystemProperties.getBoolean("persist.sysui.notification.builder_extras_override", false)) {
                    this.f20mN.extras.putAll(saveExtras);
                } else {
                    saveExtras.putAll(this.f20mN.extras);
                    this.f20mN.extras = saveExtras;
                }
            }
            this.f20mN.creationTime = System.currentTimeMillis();
            Notification.addFieldsFromContext(this.mContext, this.f20mN);
            buildUnstyled();
            Style style = this.mStyle;
            if (style != null) {
                style.reduceImageSizes(this.mContext);
                this.mStyle.purgeResources();
                this.mStyle.validate(this.mContext);
                this.mStyle.buildStyled(this.f20mN);
            }
            this.f20mN.reduceImageSizes(this.mContext);
            if (this.mContext.getApplicationInfo().targetSdkVersion < 24 && !styleDisplaysCustomViewInline()) {
                RemoteViews newContentView = this.f20mN.contentView;
                RemoteViews newBigContentView = this.f20mN.bigContentView;
                RemoteViews newHeadsUpContentView = this.f20mN.headsUpContentView;
                if (newContentView == null) {
                    newContentView = createContentView();
                    this.f20mN.extras.putInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, newContentView.getSequenceNumber());
                }
                if (newBigContentView == null && (newBigContentView = createBigContentView()) != null) {
                    this.f20mN.extras.putInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, newBigContentView.getSequenceNumber());
                }
                if (newHeadsUpContentView == null && (newHeadsUpContentView = createHeadsUpContentView()) != null) {
                    this.f20mN.extras.putInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, newHeadsUpContentView.getSequenceNumber());
                }
                this.f20mN.contentView = newContentView;
                this.f20mN.bigContentView = newBigContentView;
                this.f20mN.headsUpContentView = newHeadsUpContentView;
            }
            if ((this.f20mN.defaults & 4) != 0) {
                this.f20mN.flags |= 1;
            }
            this.f20mN.allPendingIntents = null;
            return this.f20mN;
        }

        private boolean styleDisplaysCustomViewInline() {
            Style style = this.mStyle;
            return style != null && style.displayCustomViewInline();
        }

        public Notification buildInto(Notification n) {
            build().cloneInto(n, true);
            return n;
        }

        public static Notification maybeCloneStrippedForDelivery(Notification n) {
            String templateClass = n.extras.getString(Notification.EXTRA_TEMPLATE);
            if (!TextUtils.isEmpty(templateClass) && Notification.getNotificationStyleClass(templateClass) == null) {
                return n;
            }
            boolean stripContentView = (n.contentView instanceof BuilderRemoteViews) && n.extras.getInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, -1) == n.contentView.getSequenceNumber();
            boolean stripBigContentView = (n.bigContentView instanceof BuilderRemoteViews) && n.extras.getInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, -1) == n.bigContentView.getSequenceNumber();
            boolean stripHeadsUpContentView = (n.headsUpContentView instanceof BuilderRemoteViews) && n.extras.getInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, -1) == n.headsUpContentView.getSequenceNumber();
            if (!stripContentView && !stripBigContentView && !stripHeadsUpContentView) {
                return n;
            }
            Notification clone = n.m866clone();
            if (stripContentView) {
                clone.contentView = null;
                clone.extras.remove(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT);
            }
            if (stripBigContentView) {
                clone.bigContentView = null;
                clone.extras.remove(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT);
            }
            if (stripHeadsUpContentView) {
                clone.headsUpContentView = null;
                clone.extras.remove(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT);
            }
            return clone;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBaseLayoutResource() {
            return C4337R.layout.notification_template_material_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getHeadsUpBaseLayoutResource() {
            return C4337R.layout.notification_template_material_heads_up_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigBaseLayoutResource() {
            return C4337R.layout.notification_template_material_big_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigPictureLayoutResource() {
            return C4337R.layout.notification_template_material_big_picture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigTextLayoutResource() {
            return C4337R.layout.notification_template_material_big_text;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getInboxLayoutResource() {
            return C4337R.layout.notification_template_material_inbox;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getMessagingLayoutResource() {
            return C4337R.layout.notification_template_material_messaging;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigMessagingLayoutResource() {
            return C4337R.layout.notification_template_material_big_messaging;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getConversationLayoutResource() {
            return C4337R.layout.notification_template_material_conversation;
        }

        private int getActionLayoutResource() {
            return C4337R.layout.notification_material_action;
        }

        private int getEmphasizedActionLayoutResource() {
            return C4337R.layout.notification_material_action_emphasized;
        }

        private int getEmphasizedTombstoneActionLayoutResource() {
            return C4337R.layout.notification_material_action_emphasized_tombstone;
        }

        private int getActionTombstoneLayoutResource() {
            return C4337R.layout.notification_material_action_tombstone;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBackgroundColor(StandardTemplateParams p) {
            return getColors(p).getBackgroundColor();
        }

        private boolean textColorsNeedInversion() {
            int targetSdkVersion;
            Style style = this.mStyle;
            return style != null && MediaStyle.class.equals(style.getClass()) && (targetSdkVersion = this.mContext.getApplicationInfo().targetSdkVersion) > 23 && targetSdkVersion < 26;
        }

        public CharSequence getHeadsUpStatusBarText(boolean publicMode) {
            Style style = this.mStyle;
            if (style != null && !publicMode) {
                CharSequence text = style.getHeadsUpStatusBarText();
                if (!TextUtils.isEmpty(text)) {
                    return text;
                }
            }
            return loadHeaderAppName();
        }

        public boolean usesTemplate() {
            return (this.f20mN.contentView == null && this.f20mN.headsUpContentView == null && this.f20mN.bigContentView == null) || styleDisplaysCustomViewInline();
        }
    }

    void reduceImageSizes(Context context) {
        int i;
        if (this.extras.getBoolean(EXTRA_REDUCED_IMAGES)) {
            return;
        }
        boolean isLowRam = ActivityManager.isLowRamDeviceStatic();
        Icon icon = this.mSmallIcon;
        if (icon != null && (icon.getType() == 1 || this.mSmallIcon.getType() == 5)) {
            Resources resources = context.getResources();
            int maxSize = resources.getDimensionPixelSize(isLowRam ? C4337R.dimen.notification_small_icon_size_low_ram : C4337R.dimen.notification_small_icon_size);
            this.mSmallIcon.scaleDownIfNecessary(maxSize, maxSize);
        }
        if (this.mLargeIcon != null || this.largeIcon != null) {
            Resources resources2 = context.getResources();
            getNotificationStyle();
            if (isLowRam) {
                i = C4337R.dimen.notification_right_icon_size_low_ram;
            } else {
                i = C4337R.dimen.notification_right_icon_size;
            }
            int maxSize2 = resources2.getDimensionPixelSize(i);
            Icon icon2 = this.mLargeIcon;
            if (icon2 != null) {
                icon2.scaleDownIfNecessary(maxSize2, maxSize2);
            }
            Bitmap bitmap = this.largeIcon;
            if (bitmap != null) {
                this.largeIcon = Icon.scaleDownIfNecessary(bitmap, maxSize2, maxSize2);
            }
        }
        reduceImageSizesForRemoteView(this.contentView, context, isLowRam);
        reduceImageSizesForRemoteView(this.headsUpContentView, context, isLowRam);
        reduceImageSizesForRemoteView(this.bigContentView, context, isLowRam);
        this.extras.putBoolean(EXTRA_REDUCED_IMAGES, true);
    }

    private void reduceImageSizesForRemoteView(RemoteViews remoteView, Context context, boolean isLowRam) {
        int i;
        int i2;
        if (remoteView != null) {
            Resources resources = context.getResources();
            if (isLowRam) {
                i = C4337R.dimen.notification_custom_view_max_image_width_low_ram;
            } else {
                i = C4337R.dimen.notification_custom_view_max_image_width;
            }
            int maxWidth = resources.getDimensionPixelSize(i);
            if (isLowRam) {
                i2 = C4337R.dimen.notification_custom_view_max_image_height_low_ram;
            } else {
                i2 = C4337R.dimen.notification_custom_view_max_image_height;
            }
            int maxHeight = resources.getDimensionPixelSize(i2);
            remoteView.reduceImageSizes(maxWidth, maxHeight);
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
        return isMediaNotification() || "call".equals(this.category) || CATEGORY_NAVIGATION.equals(this.category) || ((actionArr = this.actions) != null && actionArr.length > 0);
    }

    public boolean isForegroundDisplayForceDeferred() {
        return 2 == this.mFgsDeferBehavior;
    }

    public Class<? extends Style> getNotificationStyle() {
        String templateClass = this.extras.getString(EXTRA_TEMPLATE);
        if (!TextUtils.isEmpty(templateClass)) {
            return getNotificationStyleClass(templateClass);
        }
        return null;
    }

    public boolean isStyle(Class<? extends Style> styleClass) {
        String templateClass = this.extras.getString(EXTRA_TEMPLATE);
        return Objects.equals(templateClass, styleClass.getName());
    }

    public boolean isColorized() {
        return this.extras.getBoolean(EXTRA_COLORIZED) && (hasColorizedPermission() || isFgsOrUij());
    }

    public boolean hasColorizedPermission() {
        return (this.flags & 2048) != 0;
    }

    public boolean isMediaNotification() {
        Class<? extends Style> style = getNotificationStyle();
        boolean isMediaStyle = MediaStyle.class.equals(style) || DecoratedMediaCustomViewStyle.class.equals(style);
        boolean hasMediaSession = this.extras.getParcelable(EXTRA_MEDIA_SESSION, MediaSession.Token.class) != null;
        return isMediaStyle && hasMediaSession;
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

    public boolean showsTime() {
        return this.when != 0 && this.extras.getBoolean(EXTRA_SHOW_WHEN);
    }

    public boolean showsChronometer() {
        return this.when != 0 && this.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
    }

    public boolean hasImage() {
        Bundle bundle;
        if (!isStyle(MessagingStyle.class) || (bundle = this.extras) == null) {
            return hasLargeIcon() || this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI);
        }
        Parcelable[] messages = (Parcelable[]) bundle.getParcelableArray(EXTRA_MESSAGES, Parcelable.class);
        if (!ArrayUtils.isEmpty(messages)) {
            for (MessagingStyle.Message m : MessagingStyle.Message.getMessagesFromBundleArray(messages)) {
                if (m.getDataUri() != null && m.getDataMimeType() != null && m.getDataMimeType().startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @SystemApi
    public static Class<? extends Style> getNotificationStyleClass(String templateClass) {
        for (Class<? extends Style> innerClass : PLATFORM_STYLE_CLASSES) {
            if (templateClass.equals(innerClass.getName())) {
                return innerClass;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void buildCustomContentIntoTemplate(Context context, RemoteViews template, RemoteViews customContent, StandardTemplateParams p, TemplateBindResult result) {
        int childIndex = -1;
        if (customContent != null) {
            RemoteViews customContent2 = customContent.mo897clone();
            if (p.mHeaderless) {
                template.removeFromParent(C4337R.id.notification_top_line);
                Builder.setHeaderlessVerticalMargins(context, template, p, true);
            } else {
                Resources resources = context.getResources();
                result.mTitleMarginSet.applyToView(template, C4337R.id.notification_main_column, resources.getDimension(C4337R.dimen.notification_content_margin_end) / resources.getDisplayMetrics().density);
            }
            template.removeAllViewsExceptId(C4337R.id.notification_main_column, 16908301);
            template.addView(C4337R.id.notification_main_column, customContent2, 0);
            template.addFlags(1);
            childIndex = 0;
        }
        template.setIntTag(C4337R.id.notification_main_column, C4337R.id.notification_custom_view_index_tag, childIndex);
    }

    public static abstract class Style {
        static final int MAX_REMOTE_INPUT_HISTORY_LINES = 3;
        private CharSequence mBigContentTitle;
        protected Builder mBuilder;
        protected CharSequence mSummaryText = null;
        protected boolean mSummaryTextSet = false;

        public abstract boolean areNotificationsVisiblyDifferent(Style style);

        protected void internalSetBigContentTitle(CharSequence title) {
            this.mBigContentTitle = title;
        }

        protected void internalSetSummaryText(CharSequence cs) {
            this.mSummaryText = cs;
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

        protected RemoteViews getStandardView(int layoutId) {
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_UNSPECIFIED).fillTextsFrom(this.mBuilder);
            return getStandardView(layoutId, p, null);
        }

        protected RemoteViews getStandardView(int layoutId, StandardTemplateParams p, TemplateBindResult result) {
            checkBuilder();
            CharSequence charSequence = this.mBigContentTitle;
            if (charSequence != null) {
                p.mTitle = charSequence;
            }
            return this.mBuilder.applyStandardTemplateWithActions(layoutId, p, result);
        }

        public RemoteViews makeContentView(boolean increasedHeight) {
            return null;
        }

        public RemoteViews makeBigContentView() {
            return null;
        }

        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            return null;
        }

        public void addExtras(Bundle extras) {
            if (this.mSummaryTextSet) {
                extras.putCharSequence(Notification.EXTRA_SUMMARY_TEXT, this.mSummaryText);
            }
            CharSequence charSequence = this.mBigContentTitle;
            if (charSequence != null) {
                extras.putCharSequence(Notification.EXTRA_TITLE_BIG, charSequence);
            }
            extras.putString(Notification.EXTRA_TEMPLATE, getClass().getName());
        }

        protected void restoreFromExtras(Bundle extras) {
            if (extras.containsKey(Notification.EXTRA_SUMMARY_TEXT)) {
                this.mSummaryText = extras.getCharSequence(Notification.EXTRA_SUMMARY_TEXT);
                this.mSummaryTextSet = true;
            }
            if (extras.containsKey(Notification.EXTRA_TITLE_BIG)) {
                this.mBigContentTitle = extras.getCharSequence(Notification.EXTRA_TITLE_BIG);
            }
        }

        public Notification buildStyled(Notification wip) {
            addExtras(wip.extras);
            return wip;
        }

        public void purgeResources() {
        }

        public Notification build() {
            checkBuilder();
            return this.mBuilder.build();
        }

        public boolean hasSummaryInHeader() {
            return true;
        }

        public boolean displayCustomViewInline() {
            return false;
        }

        public void reduceImageSizes(Context context) {
        }

        public void validate(Context context) {
        }

        public CharSequence getHeadsUpStatusBarText() {
            return null;
        }
    }

    public static class BigPictureStyle extends Style {
        public static final int MIN_ASHMEM_BITMAP_SIZE = 131072;
        private Icon mBigLargeIcon;
        private boolean mBigLargeIconSet = false;
        private CharSequence mPictureContentDescription;
        private Icon mPictureIcon;
        private boolean mShowBigPictureWhenCollapsed;

        public BigPictureStyle() {
        }

        @Deprecated
        public BigPictureStyle(Builder builder) {
            setBuilder(builder);
        }

        public BigPictureStyle setBigContentTitle(CharSequence title) {
            internalSetBigContentTitle(Notification.safeCharSequence(title));
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence cs) {
            internalSetSummaryText(Notification.safeCharSequence(cs));
            return this;
        }

        public BigPictureStyle setContentDescription(CharSequence contentDescription) {
            this.mPictureContentDescription = contentDescription;
            return this;
        }

        public Icon getBigPicture() {
            Icon icon = this.mPictureIcon;
            if (icon != null) {
                return icon;
            }
            return null;
        }

        public BigPictureStyle bigPicture(Bitmap b) {
            this.mPictureIcon = b == null ? null : Icon.createWithBitmap(b);
            return this;
        }

        public BigPictureStyle bigPicture(Icon icon) {
            this.mPictureIcon = icon;
            return this;
        }

        public BigPictureStyle showBigPictureWhenCollapsed(boolean show) {
            this.mShowBigPictureWhenCollapsed = show;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap b) {
            return bigLargeIcon(b != null ? Icon.createWithBitmap(b) : null);
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
            int i;
            int i2;
            int i3;
            super.reduceImageSizes(context);
            Resources resources = context.getResources();
            boolean isLowRam = ActivityManager.isLowRamDeviceStatic();
            if (this.mPictureIcon != null) {
                if (isLowRam) {
                    i2 = C4337R.dimen.notification_big_picture_max_height_low_ram;
                } else {
                    i2 = C4337R.dimen.notification_big_picture_max_height;
                }
                int maxPictureHeight = resources.getDimensionPixelSize(i2);
                if (isLowRam) {
                    i3 = C4337R.dimen.notification_big_picture_max_width_low_ram;
                } else {
                    i3 = C4337R.dimen.notification_big_picture_max_width;
                }
                int maxPictureWidth = resources.getDimensionPixelSize(i3);
                if (this.mPictureIcon.getType() == 1 || this.mPictureIcon.getType() == 5) {
                    this.mPictureIcon = Icon.createWithBitmap(Notification.scaleDownIfNecessaryForBigPicture(this.mPictureIcon.getBitmap(), maxPictureWidth, maxPictureHeight));
                }
            }
            if (this.mBigLargeIcon != null) {
                if (isLowRam) {
                    i = C4337R.dimen.notification_right_icon_size_low_ram;
                } else {
                    i = C4337R.dimen.notification_right_icon_size;
                }
                int rightIconSize = resources.getDimensionPixelSize(i);
                this.mBigLargeIcon.scaleDownIfNecessary(rightIconSize, rightIconSize);
            }
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            if (this.mPictureIcon == null || !this.mShowBigPictureWhenCollapsed) {
                return super.makeContentView(increasedHeight);
            }
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).fillTextsFrom(this.mBuilder).promotedPicture(this.mPictureIcon);
            return getStandardView(this.mBuilder.getBaseLayoutResource(), p, null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            if (this.mPictureIcon == null || !this.mShowBigPictureWhenCollapsed) {
                return super.makeHeadsUpContentView(increasedHeight);
            }
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this.mBuilder).promotedPicture(this.mPictureIcon);
            return getStandardView(this.mBuilder.getHeadsUpBaseLayoutResource(), p, null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            Icon oldLargeIcon = null;
            Bitmap largeIconLegacy = null;
            if (this.mBigLargeIconSet) {
                oldLargeIcon = this.mBuilder.f20mN.mLargeIcon;
                this.mBuilder.f20mN.mLargeIcon = this.mBigLargeIcon;
                largeIconLegacy = this.mBuilder.f20mN.largeIcon;
                this.mBuilder.f20mN.largeIcon = null;
            }
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).fillTextsFrom(this.mBuilder);
            RemoteViews contentView = getStandardView(this.mBuilder.getBigPictureLayoutResource(), p, null);
            if (this.mSummaryTextSet) {
                contentView.setTextViewText(C4337R.id.text, this.mBuilder.ensureColorSpanContrast(this.mBuilder.processLegacyText(this.mSummaryText), p));
                this.mBuilder.setTextViewColorSecondary(contentView, C4337R.id.text, p);
                contentView.setViewVisibility(C4337R.id.text, 0);
            }
            if (this.mBigLargeIconSet) {
                this.mBuilder.f20mN.mLargeIcon = oldLargeIcon;
                this.mBuilder.f20mN.largeIcon = largeIconLegacy;
            }
            contentView.setImageViewIcon(C4337R.id.big_picture, this.mPictureIcon);
            CharSequence charSequence = this.mPictureContentDescription;
            if (charSequence != null) {
                contentView.setContentDescription(C4337R.id.big_picture, charSequence);
            }
            return contentView;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle extras) {
            super.addExtras(extras);
            if (this.mBigLargeIconSet) {
                extras.putParcelable(Notification.EXTRA_LARGE_ICON_BIG, this.mBigLargeIcon);
            }
            CharSequence charSequence = this.mPictureContentDescription;
            if (charSequence != null) {
                extras.putCharSequence(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION, charSequence);
            }
            extras.putBoolean(Notification.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED, this.mShowBigPictureWhenCollapsed);
            Icon icon = this.mPictureIcon;
            if (icon != null && icon.getType() == 1) {
                extras.putParcelable(Notification.EXTRA_PICTURE, this.mPictureIcon.getBitmap());
                extras.putParcelable(Notification.EXTRA_PICTURE_ICON, null);
            } else {
                extras.putParcelable(Notification.EXTRA_PICTURE, null);
                extras.putParcelable(Notification.EXTRA_PICTURE_ICON, this.mPictureIcon);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle extras) {
            super.restoreFromExtras(extras);
            if (extras.containsKey(Notification.EXTRA_LARGE_ICON_BIG)) {
                this.mBigLargeIconSet = true;
                this.mBigLargeIcon = (Icon) extras.getParcelable(Notification.EXTRA_LARGE_ICON_BIG, Icon.class);
            }
            if (extras.containsKey(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION)) {
                this.mPictureContentDescription = extras.getCharSequence(Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION);
            }
            this.mShowBigPictureWhenCollapsed = extras.getBoolean(Notification.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED);
            this.mPictureIcon = getPictureIcon(extras);
        }

        public static Icon getPictureIcon(Bundle extras) {
            if (extras == null) {
                return null;
            }
            Bitmap bitmapPicture = (Bitmap) extras.getParcelable(Notification.EXTRA_PICTURE, Bitmap.class);
            if (bitmapPicture != null) {
                return Icon.createWithBitmap(bitmapPicture);
            }
            return (Icon) extras.getParcelable(Notification.EXTRA_PICTURE_ICON, Icon.class);
        }

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            BigPictureStyle otherS = (BigPictureStyle) other;
            return Notification.areIconsMaybeDifferent(getBigPicture(), otherS.getBigPicture());
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

        public BigTextStyle setBigContentTitle(CharSequence title) {
            internalSetBigContentTitle(Notification.safeCharSequence(title));
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence cs) {
            internalSetSummaryText(Notification.safeCharSequence(cs));
            return this;
        }

        public BigTextStyle bigText(CharSequence cs) {
            this.mBigText = Notification.safeCharSequence(cs);
            return this;
        }

        public CharSequence getBigText() {
            return this.mBigText;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle extras) {
            super.addExtras(extras);
            extras.putCharSequence(Notification.EXTRA_BIG_TEXT, this.mBigText);
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle extras) {
            super.restoreFromExtras(extras);
            this.mBigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            if (increasedHeight) {
                ArrayList<Action> originalActions = this.mBuilder.mActions;
                this.mBuilder.mActions = new ArrayList();
                RemoteViews remoteViews = makeBigContentView();
                this.mBuilder.mActions = originalActions;
                return remoteViews;
            }
            return super.makeContentView(increasedHeight);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            if (increasedHeight && this.mBuilder.mActions.size() > 0) {
                return makeBigContentView();
            }
            return super.makeHeadsUpContentView(increasedHeight);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).allowTextWithProgress(true).textViewId(C4337R.id.big_text).fillTextsFrom(this.mBuilder);
            CharSequence bigTextText = this.mBuilder.processLegacyText(this.mBigText);
            if (!TextUtils.isEmpty(bigTextText)) {
                p.text(bigTextText);
            }
            return getStandardView(this.mBuilder.getBigTextLayoutResource(), p, null);
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            BigTextStyle newS = (BigTextStyle) other;
            return true ^ Objects.equals(String.valueOf(getBigText()), String.valueOf(newS.getBigText()));
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

        public MessagingStyle(CharSequence userDisplayName) {
            this(new Person.Builder().setName(userDisplayName).build());
        }

        public MessagingStyle(Person user) {
            this.mMessages = new ArrayList();
            this.mHistoricMessages = new ArrayList();
            this.mConversationType = 0;
            this.mUser = user;
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
            CharSequence conversationTitle;
            if (!TextUtils.isEmpty(((Style) this).mBigContentTitle)) {
                conversationTitle = ((Style) this).mBigContentTitle;
            } else {
                conversationTitle = this.mConversationTitle;
            }
            if (this.mConversationType == 0 && !TextUtils.isEmpty(conversationTitle) && !hasOnlyWhiteSpaceSenders()) {
                return conversationTitle;
            }
            return null;
        }

        public Person getUser() {
            return this.mUser;
        }

        public CharSequence getUserDisplayName() {
            return this.mUser.getName();
        }

        public MessagingStyle setConversationTitle(CharSequence conversationTitle) {
            this.mConversationTitle = conversationTitle;
            return this;
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public MessagingStyle setShortcutIcon(Icon conversationIcon) {
            this.mShortcutIcon = conversationIcon;
            return this;
        }

        public Icon getShortcutIcon() {
            return this.mShortcutIcon;
        }

        public MessagingStyle setConversationType(int conversationType) {
            this.mConversationType = conversationType;
            return this;
        }

        public int getConversationType() {
            return this.mConversationType;
        }

        public int getUnreadMessageCount() {
            return this.mUnreadMessageCount;
        }

        public MessagingStyle setUnreadMessageCount(int unreadMessageCount) {
            this.mUnreadMessageCount = unreadMessageCount;
            return this;
        }

        public MessagingStyle addMessage(CharSequence text, long timestamp, CharSequence sender) {
            return addMessage(text, timestamp, sender == null ? null : new Person.Builder().setName(sender).build());
        }

        public MessagingStyle addMessage(CharSequence text, long timestamp, Person sender) {
            return addMessage(new Message(text, timestamp, sender));
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

        public MessagingStyle setGroupConversation(boolean isGroupConversation) {
            this.mIsGroupConversation = isGroupConversation;
            return this;
        }

        public boolean isGroupConversation() {
            if (this.mBuilder == null || this.mBuilder.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                return this.mIsGroupConversation;
            }
            return this.mConversationTitle != null;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle extras) {
            super.addExtras(extras);
            addExtras(extras, false, 0);
        }

        public void addExtras(Bundle extras, boolean ensureContrast, int backgroundColor) {
            Person person = this.mUser;
            if (person != null) {
                extras.putCharSequence(Notification.EXTRA_SELF_DISPLAY_NAME, person.getName());
                extras.putParcelable(Notification.EXTRA_MESSAGING_PERSON, this.mUser);
            }
            CharSequence charSequence = this.mConversationTitle;
            if (charSequence != null) {
                extras.putCharSequence(Notification.EXTRA_CONVERSATION_TITLE, charSequence);
            }
            if (!this.mMessages.isEmpty()) {
                extras.putParcelableArray(Notification.EXTRA_MESSAGES, getBundleArrayForMessages(this.mMessages, ensureContrast, backgroundColor));
            }
            if (!this.mHistoricMessages.isEmpty()) {
                extras.putParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES, getBundleArrayForMessages(this.mHistoricMessages, ensureContrast, backgroundColor));
            }
            Icon icon = this.mShortcutIcon;
            if (icon != null) {
                extras.putParcelable(Notification.EXTRA_CONVERSATION_ICON, icon);
            }
            extras.putInt(Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT, this.mUnreadMessageCount);
            fixTitleAndTextExtras(extras);
            extras.putBoolean(Notification.EXTRA_IS_GROUP_CONVERSATION, this.mIsGroupConversation);
        }

        private static Bundle[] getBundleArrayForMessages(List<Message> messages, boolean ensureContrast, int backgroundColor) {
            Bundle[] bundles = new Bundle[messages.size()];
            int N = messages.size();
            for (int i = 0; i < N; i++) {
                Message m = messages.get(i);
                if (ensureContrast) {
                    m.ensureColorContrast(backgroundColor);
                }
                bundles[i] = m.toBundle();
            }
            return bundles;
        }

        private void fixTitleAndTextExtras(Bundle extras) {
            CharSequence title;
            Message m = findLatestIncomingMessage();
            CharSequence sender = null;
            CharSequence text = m == null ? null : m.mText;
            if (m != null) {
                sender = ((m.mSender == null || TextUtils.isEmpty(m.mSender.getName())) ? this.mUser : m.mSender).getName();
            }
            if (!TextUtils.isEmpty(this.mConversationTitle)) {
                if (!TextUtils.isEmpty(sender) && !this.mConversationTitle.equals(sender)) {
                    BidiFormatter bidi = BidiFormatter.getInstance();
                    title = this.mBuilder.mContext.getString(C4337R.string.notification_messaging_title_template, bidi.unicodeWrap(this.mConversationTitle), bidi.unicodeWrap(sender));
                } else {
                    title = this.mConversationTitle;
                }
            } else {
                title = sender;
            }
            if (title != null) {
                extras.putCharSequence(Notification.EXTRA_TITLE, title);
            }
            if (text != null) {
                extras.putCharSequence(Notification.EXTRA_TEXT, text);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle extras) {
            super.restoreFromExtras(extras);
            Person user = (Person) extras.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
            if (user == null) {
                CharSequence displayName = extras.getCharSequence(Notification.EXTRA_SELF_DISPLAY_NAME);
                this.mUser = new Person.Builder().setName(displayName).build();
            } else {
                this.mUser = user;
            }
            this.mConversationTitle = extras.getCharSequence(Notification.EXTRA_CONVERSATION_TITLE);
            Parcelable[] messages = (Parcelable[]) extras.getParcelableArray(Notification.EXTRA_MESSAGES, Parcelable.class);
            this.mMessages = Message.getMessagesFromBundleArray(messages);
            Parcelable[] histMessages = (Parcelable[]) extras.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES, Parcelable.class);
            this.mHistoricMessages = Message.getMessagesFromBundleArray(histMessages);
            this.mIsGroupConversation = extras.getBoolean(Notification.EXTRA_IS_GROUP_CONVERSATION);
            this.mUnreadMessageCount = extras.getInt(Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT);
            this.mShortcutIcon = (Icon) extras.getParcelable(Notification.EXTRA_CONVERSATION_ICON, Icon.class);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            ArrayList<Action> originalActions = this.mBuilder.mActions;
            try {
                this.mBuilder.mActions = new ArrayList();
                return makeMessagingView(StandardTemplateParams.VIEW_TYPE_NORMAL);
            } finally {
                this.mBuilder.mActions = originalActions;
            }
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            CharSequence name;
            CharSequence name2;
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            MessagingStyle newS = (MessagingStyle) other;
            List<Message> oldMs = getMessages();
            List<Message> newMs = newS.getMessages();
            if (oldMs == null || newMs == null) {
                newMs = new ArrayList();
            }
            int n = oldMs.size();
            if (n != newMs.size()) {
                return true;
            }
            for (int i = 0; i < n; i++) {
                Message oldM = oldMs.get(i);
                Message newM = newMs.get(i);
                if (!Objects.equals(String.valueOf(oldM.getText()), String.valueOf(newM.getText())) || !Objects.equals(oldM.getDataUri(), newM.getDataUri())) {
                    return true;
                }
                if (oldM.getSenderPerson() == null) {
                    name = oldM.getSender();
                } else {
                    name = oldM.getSenderPerson().getName();
                }
                String oldSender = String.valueOf(name);
                if (newM.getSenderPerson() == null) {
                    name2 = newM.getSender();
                } else {
                    name2 = newM.getSenderPerson().getName();
                }
                String newSender = String.valueOf(name2);
                if (!Objects.equals(oldSender, newSender)) {
                    return true;
                }
                String oldKey = oldM.getSenderPerson() == null ? null : oldM.getSenderPerson().getKey();
                String newKey = newM.getSenderPerson() != null ? newM.getSenderPerson().getKey() : null;
                if (!Objects.equals(oldKey, newKey)) {
                    return true;
                }
            }
            return false;
        }

        private Message findLatestIncomingMessage() {
            return findLatestIncomingMessage(this.mMessages);
        }

        public static Message findLatestIncomingMessage(List<Message> messages) {
            for (int i = messages.size() - 1; i >= 0; i--) {
                Message m = messages.get(i);
                if (m.mSender != null && !TextUtils.isEmpty(m.mSender.getName())) {
                    return m;
                }
            }
            if (!messages.isEmpty()) {
                return messages.get(messages.size() - 1);
            }
            return null;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            return makeMessagingView(StandardTemplateParams.VIEW_TYPE_BIG);
        }

        private RemoteViews makeMessagingView(int viewType) {
            CharSequence conversationTitle;
            boolean isOneToOne;
            int bigMessagingLayoutResource;
            boolean isCollapsed = viewType != StandardTemplateParams.VIEW_TYPE_BIG;
            boolean hideRightIcons = viewType != StandardTemplateParams.VIEW_TYPE_NORMAL;
            int i = this.mConversationType;
            boolean isConversationLayout = i != 0;
            boolean isImportantConversation = i == 2;
            boolean isHeaderless = !isConversationLayout && isCollapsed;
            boolean isBigMessageLayout = (isConversationLayout || isCollapsed) ? false : true;
            if (!TextUtils.isEmpty(((Style) this).mBigContentTitle)) {
                conversationTitle = ((Style) this).mBigContentTitle;
            } else {
                conversationTitle = this.mConversationTitle;
            }
            boolean atLeastP = this.mBuilder.mContext.getApplicationInfo().targetSdkVersion >= 28;
            CharSequence nameReplacement = null;
            if (!atLeastP) {
                isOneToOne = TextUtils.isEmpty(conversationTitle);
                if (hasOnlyWhiteSpaceSenders()) {
                    isOneToOne = true;
                    nameReplacement = conversationTitle;
                    conversationTitle = null;
                }
            } else {
                boolean isOneToOne2 = isGroupConversation();
                isOneToOne = !isOneToOne2;
            }
            if (isHeaderless && isOneToOne && TextUtils.isEmpty(conversationTitle)) {
                conversationTitle = getOtherPersonName();
            }
            Icon largeIcon = this.mBuilder.f20mN.mLargeIcon;
            TemplateBindResult bindResult = new TemplateBindResult();
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(viewType).highlightExpander(isConversationLayout).hideProgress(true).title(isHeaderless ? conversationTitle : null).text(null).hideAppName(isHeaderless || !isBigMessageLayout).hideLeftIcon(isOneToOne).hideRightIcon(hideRightIcons || isOneToOne).headerTextSecondary(isHeaderless ? null : conversationTitle);
            Builder builder = this.mBuilder;
            if (isConversationLayout) {
                bigMessagingLayoutResource = this.mBuilder.getConversationLayoutResource();
            } else if (isCollapsed) {
                bigMessagingLayoutResource = this.mBuilder.getMessagingLayoutResource();
            } else {
                bigMessagingLayoutResource = this.mBuilder.getBigMessagingLayoutResource();
            }
            RemoteViews contentView = builder.applyStandardTemplateWithActions(bigMessagingLayoutResource, p, bindResult);
            if (isConversationLayout) {
                this.mBuilder.setTextViewColorPrimary(contentView, C4337R.id.conversation_text, p);
                contentView.setViewVisibility(C4337R.id.app_name_divider, 8);
                this.mBuilder.setTextViewColorSecondary(contentView, C4337R.id.app_name_divider, p);
            }
            addExtras(this.mBuilder.f20mN.extras, true, this.mBuilder.getBackgroundColor(p));
            contentView.setInt(C4337R.id.status_bar_latest_event_content, "setLayoutColor", this.mBuilder.getSmallIconColor(p));
            contentView.setInt(C4337R.id.status_bar_latest_event_content, "setSenderTextColor", this.mBuilder.getPrimaryTextColor(p));
            contentView.setInt(C4337R.id.status_bar_latest_event_content, "setMessageTextColor", this.mBuilder.getSecondaryTextColor(p));
            contentView.setInt(C4337R.id.status_bar_latest_event_content, "setNotificationBackgroundColor", this.mBuilder.getBackgroundColor(p));
            contentView.setBoolean(C4337R.id.status_bar_latest_event_content, "setIsCollapsed", isCollapsed);
            contentView.setIcon(C4337R.id.status_bar_latest_event_content, "setAvatarReplacement", this.mBuilder.f20mN.mLargeIcon);
            contentView.setCharSequence(C4337R.id.status_bar_latest_event_content, "setNameReplacement", nameReplacement);
            contentView.setBoolean(C4337R.id.status_bar_latest_event_content, "setIsOneToOne", isOneToOne);
            contentView.setCharSequence(C4337R.id.status_bar_latest_event_content, "setConversationTitle", conversationTitle);
            if (isConversationLayout) {
                contentView.setIcon(C4337R.id.status_bar_latest_event_content, "setShortcutIcon", this.mShortcutIcon);
                contentView.setBoolean(C4337R.id.status_bar_latest_event_content, "setIsImportantConversation", isImportantConversation);
            }
            if (isHeaderless || isCollapsed) {
                contentView.setInt(C4337R.id.notification_messaging, "setMaxDisplayedLines", 2);
            }
            contentView.setIcon(C4337R.id.status_bar_latest_event_content, "setLargeIcon", largeIcon);
            contentView.setBundle(C4337R.id.status_bar_latest_event_content, "setData", this.mBuilder.f20mN.extras);
            return contentView;
        }

        private CharSequence getKey(Person person) {
            if (person == null) {
                return null;
            }
            return person.getKey() == null ? person.getName() : person.getKey();
        }

        private CharSequence getOtherPersonName() {
            CharSequence userKey = getKey(this.mUser);
            for (int i = this.mMessages.size() - 1; i >= 0; i--) {
                Person sender = this.mMessages.get(i).getSenderPerson();
                if (sender != null && !TextUtils.equals(userKey, getKey(sender))) {
                    return sender.getName();
                }
            }
            return null;
        }

        private boolean hasOnlyWhiteSpaceSenders() {
            for (int i = 0; i < this.mMessages.size(); i++) {
                Message m = this.mMessages.get(i);
                Person sender = m.getSenderPerson();
                if (sender != null && !isWhiteSpace(sender.getName())) {
                    return false;
                }
            }
            return true;
        }

        private boolean isWhiteSpace(CharSequence sender) {
            if (TextUtils.isEmpty(sender) || sender.toString().matches("^\\s*$")) {
                return true;
            }
            for (int i = 0; i < sender.length(); i++) {
                char c = sender.charAt(i);
                if (c != 8203) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            RemoteViews remoteViews = makeMessagingView(StandardTemplateParams.VIEW_TYPE_HEADS_UP);
            remoteViews.setInt(C4337R.id.notification_messaging, "setMaxDisplayedLines", 2);
            return remoteViews;
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(Context context) {
            super.reduceImageSizes(context);
            Resources resources = context.getResources();
            boolean isLowRam = ActivityManager.isLowRamDeviceStatic();
            if (this.mShortcutIcon != null) {
                int maxSize = resources.getDimensionPixelSize(isLowRam ? C4337R.dimen.notification_small_icon_size_low_ram : C4337R.dimen.notification_small_icon_size);
                this.mShortcutIcon.scaleDownIfNecessary(maxSize, maxSize);
            }
            int maxAvatarSize = resources.getDimensionPixelSize(isLowRam ? C4337R.dimen.notification_person_icon_max_size_low_ram : C4337R.dimen.notification_person_icon_max_size);
            Person person = this.mUser;
            if (person != null && person.getIcon() != null) {
                this.mUser.getIcon().scaleDownIfNecessary(maxAvatarSize, maxAvatarSize);
            }
            reduceMessagesIconSizes(this.mMessages, maxAvatarSize);
            reduceMessagesIconSizes(this.mHistoricMessages, maxAvatarSize);
        }

        private static void reduceMessagesIconSizes(List<Message> messages, int maxSize) {
            Icon icon;
            if (messages == null) {
                return;
            }
            for (Message message : messages) {
                Person sender = message.mSender;
                if (sender != null && (icon = sender.getIcon()) != null) {
                    icon.scaleDownIfNecessary(maxSize, maxSize);
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

            public Message(CharSequence text, long timestamp, CharSequence sender) {
                this(text, timestamp, sender == null ? null : new Person.Builder().setName(sender).build());
            }

            public Message(CharSequence text, long timestamp, Person sender) {
                this(text, timestamp, sender, false);
            }

            public Message(CharSequence text, long timestamp, Person sender, boolean remoteInputHistory) {
                this.mExtras = new Bundle();
                this.mText = Notification.safeCharSequence(text);
                this.mTimestamp = timestamp;
                this.mSender = sender;
                this.mRemoteInputHistory = remoteInputHistory;
            }

            public Message setData(String dataMimeType, Uri dataUri) {
                this.mDataMimeType = dataMimeType;
                this.mDataUri = dataUri;
                return this;
            }

            public void ensureColorContrast(int backgroundColor) {
                this.mText = ContrastColorUtil.ensureColorSpanContrast(this.mText, backgroundColor);
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

            public void visitUris(Consumer<Uri> visitor) {
                visitor.accept(getDataUri());
                Person person = this.mSender;
                if (person != null) {
                    person.visitUris(visitor);
                }
            }

            public static List<Message> getMessagesFromBundleArray(Parcelable[] bundles) {
                Message message;
                if (bundles == null) {
                    return new ArrayList();
                }
                List<Message> messages = new ArrayList<>(bundles.length);
                for (int i = 0; i < bundles.length; i++) {
                    if ((bundles[i] instanceof Bundle) && (message = getMessageFromBundle((Bundle) bundles[i])) != null) {
                        messages.add(message);
                    }
                }
                return messages;
            }

            public static Message getMessageFromBundle(Bundle bundle) {
                Person senderPerson;
                CharSequence senderName;
                try {
                    if (bundle.containsKey("text") && bundle.containsKey("time")) {
                        Person senderPerson2 = (Person) bundle.getParcelable(KEY_SENDER_PERSON, Person.class);
                        if (senderPerson2 == null && (senderName = bundle.getCharSequence("sender")) != null) {
                            senderPerson = new Person.Builder().setName(senderName).build();
                        } else {
                            senderPerson = senderPerson2;
                        }
                        Message message = new Message(bundle.getCharSequence("text"), bundle.getLong("time"), senderPerson, bundle.getBoolean(KEY_REMOTE_INPUT_HISTORY, false));
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
                    Log.m97e(Notification.TAG, "could not unparcel extras from message notification", e);
                    return null;
                } catch (ClassCastException e2) {
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

        public InboxStyle setBigContentTitle(CharSequence title) {
            internalSetBigContentTitle(Notification.safeCharSequence(title));
            return this;
        }

        public InboxStyle setSummaryText(CharSequence cs) {
            internalSetSummaryText(Notification.safeCharSequence(cs));
            return this;
        }

        public InboxStyle addLine(CharSequence cs) {
            this.mTexts.add(Notification.safeCharSequence(cs));
            return this;
        }

        public ArrayList<CharSequence> getLines() {
            return this.mTexts;
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle extras) {
            super.addExtras(extras);
            CharSequence[] a = new CharSequence[this.mTexts.size()];
            extras.putCharSequenceArray(Notification.EXTRA_TEXT_LINES, (CharSequence[]) this.mTexts.toArray(a));
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle extras) {
            super.restoreFromExtras(extras);
            this.mTexts.clear();
            if (extras.containsKey(Notification.EXTRA_TEXT_LINES)) {
                Collections.addAll(this.mTexts, extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES));
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00c3  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00ff  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x010d  */
        @Override // android.app.Notification.Style
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public RemoteViews makeBigContentView() {
            int totalNumRows;
            boolean first;
            int onlyViewId;
            int overflow;
            CharSequence str;
            int maxRows;
            int onlyViewId2;
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).fillTextsFrom(this.mBuilder).text(null);
            TemplateBindResult result = new TemplateBindResult();
            RemoteViews contentView = getStandardView(this.mBuilder.getInboxLayoutResource(), p, result);
            int[] rowIds = {C4337R.id.inbox_text0, C4337R.id.inbox_text1, C4337R.id.inbox_text2, C4337R.id.inbox_text3, C4337R.id.inbox_text4, C4337R.id.inbox_text5, C4337R.id.inbox_text6};
            for (int rowId : rowIds) {
                contentView.setViewVisibility(rowId, 8);
            }
            int topPadding = this.mBuilder.mContext.getResources().getDimensionPixelSize(C4337R.dimen.notification_inbox_item_top_padding);
            int maxRows2 = rowIds.length;
            if (this.mBuilder.mActions.size() > 0) {
                maxRows2--;
            }
            RemoteInputHistoryItem[] remoteInputHistory = (RemoteInputHistoryItem[]) Notification.getParcelableArrayFromBundle(this.mBuilder.f20mN.extras, Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, RemoteInputHistoryItem.class);
            if (remoteInputHistory != null && remoteInputHistory.length > 1) {
                int numRemoteInputs = Math.min(remoteInputHistory.length, 3);
                int totalNumRows2 = (this.mTexts.size() + numRemoteInputs) - 1;
                if (totalNumRows2 > maxRows2) {
                    int overflow2 = totalNumRows2 - maxRows2;
                    if (this.mTexts.size() > maxRows2) {
                        totalNumRows = 0;
                        first = true;
                        onlyViewId = 0;
                        overflow = maxRows2 - overflow2;
                    } else {
                        totalNumRows = overflow2;
                        first = true;
                        onlyViewId = 0;
                        overflow = maxRows2;
                    }
                    while (totalNumRows < this.mTexts.size() && totalNumRows < overflow) {
                        str = this.mTexts.get(totalNumRows);
                        if (TextUtils.isEmpty(str)) {
                            contentView.setViewVisibility(rowIds[totalNumRows], 0);
                            contentView.setTextViewText(rowIds[totalNumRows], this.mBuilder.ensureColorSpanContrast(this.mBuilder.processLegacyText(str), p));
                            this.mBuilder.setTextViewColorSecondary(contentView, rowIds[totalNumRows], p);
                            maxRows = overflow;
                            contentView.setViewPadding(rowIds[totalNumRows], 0, topPadding, 0, 0);
                            if (first) {
                                onlyViewId2 = rowIds[totalNumRows];
                            } else {
                                onlyViewId2 = 0;
                            }
                            onlyViewId = onlyViewId2;
                            first = false;
                        } else {
                            maxRows = overflow;
                        }
                        totalNumRows++;
                        overflow = maxRows;
                    }
                    if (onlyViewId != 0) {
                        int topPadding2 = this.mBuilder.mContext.getResources().getDimensionPixelSize(C4337R.dimen.notification_text_margin_top);
                        contentView.setViewPadding(onlyViewId, 0, topPadding2, 0, 0);
                    }
                    return contentView;
                }
            }
            totalNumRows = 0;
            first = true;
            onlyViewId = 0;
            overflow = maxRows2;
            while (totalNumRows < this.mTexts.size()) {
                str = this.mTexts.get(totalNumRows);
                if (TextUtils.isEmpty(str)) {
                }
                totalNumRows++;
                overflow = maxRows;
            }
            if (onlyViewId != 0) {
            }
            return contentView;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            InboxStyle newS = (InboxStyle) other;
            ArrayList<CharSequence> myLines = getLines();
            ArrayList<CharSequence> newLines = newS.getLines();
            int n = myLines.size();
            if (n != newLines.size()) {
                return true;
            }
            for (int i = 0; i < n; i++) {
                if (!Objects.equals(String.valueOf(myLines.get(i)), String.valueOf(newLines.get(i)))) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class MediaStyle extends Style {
        static final int MAX_MEDIA_BUTTONS = 5;
        static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
        private static final int[] MEDIA_BUTTON_IDS = {C4337R.id.action0, C4337R.id.action1, C4337R.id.action2, C4337R.id.action3, C4337R.id.action4};
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

        public MediaStyle setShowActionsInCompactView(int... actions) {
            this.mActionsToShowInCompact = actions;
            return this;
        }

        public MediaStyle setMediaSession(MediaSession.Token token) {
            this.mToken = token;
            return this;
        }

        public MediaStyle setRemotePlaybackInfo(CharSequence deviceName, int iconResource, PendingIntent chipIntent) {
            this.mDeviceName = deviceName;
            this.mDeviceIcon = iconResource;
            this.mDeviceIntent = chipIntent;
            return this;
        }

        @Override // android.app.Notification.Style
        public Notification buildStyled(Notification wip) {
            super.buildStyled(wip);
            if (wip.category == null) {
                wip.category = Notification.CATEGORY_TRANSPORT;
            }
            return wip;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            return makeMediaContentView(null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            return makeMediaBigContentView(null);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            return makeMediaContentView(null);
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle extras) {
            super.addExtras(extras);
            MediaSession.Token token = this.mToken;
            if (token != null) {
                extras.putParcelable(Notification.EXTRA_MEDIA_SESSION, token);
            }
            int[] iArr = this.mActionsToShowInCompact;
            if (iArr != null) {
                extras.putIntArray(Notification.EXTRA_COMPACT_ACTIONS, iArr);
            }
            CharSequence charSequence = this.mDeviceName;
            if (charSequence != null) {
                extras.putCharSequence(Notification.EXTRA_MEDIA_REMOTE_DEVICE, charSequence);
            }
            int i = this.mDeviceIcon;
            if (i > 0) {
                extras.putInt(Notification.EXTRA_MEDIA_REMOTE_ICON, i);
            }
            PendingIntent pendingIntent = this.mDeviceIntent;
            if (pendingIntent != null) {
                extras.putParcelable(Notification.EXTRA_MEDIA_REMOTE_INTENT, pendingIntent);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle extras) {
            super.restoreFromExtras(extras);
            if (extras.containsKey(Notification.EXTRA_MEDIA_SESSION)) {
                this.mToken = (MediaSession.Token) extras.getParcelable(Notification.EXTRA_MEDIA_SESSION, MediaSession.Token.class);
            }
            if (extras.containsKey(Notification.EXTRA_COMPACT_ACTIONS)) {
                this.mActionsToShowInCompact = extras.getIntArray(Notification.EXTRA_COMPACT_ACTIONS);
            }
            if (extras.containsKey(Notification.EXTRA_MEDIA_REMOTE_DEVICE)) {
                this.mDeviceName = extras.getCharSequence(Notification.EXTRA_MEDIA_REMOTE_DEVICE);
            }
            if (extras.containsKey(Notification.EXTRA_MEDIA_REMOTE_ICON)) {
                this.mDeviceIcon = extras.getInt(Notification.EXTRA_MEDIA_REMOTE_ICON);
            }
            if (extras.containsKey(Notification.EXTRA_MEDIA_REMOTE_INTENT)) {
                this.mDeviceIntent = (PendingIntent) extras.getParcelable(Notification.EXTRA_MEDIA_REMOTE_INTENT, PendingIntent.class);
            }
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            return false;
        }

        private void bindMediaActionButton(RemoteViews container, int buttonId, Action action, StandardTemplateParams p) {
            boolean tombstone = action.actionIntent == null;
            container.setViewVisibility(buttonId, 0);
            container.setImageViewIcon(buttonId, action.getIcon());
            int tintColor = this.mBuilder.getStandardActionColor(p);
            container.setDrawableTint(buttonId, false, tintColor, PorterDuff.Mode.SRC_ATOP);
            int rippleAlpha = this.mBuilder.getColors(p).getRippleAlpha();
            int rippleColor = Color.argb(rippleAlpha, Color.red(tintColor), Color.green(tintColor), Color.blue(tintColor));
            container.setRippleDrawableColor(buttonId, ColorStateList.valueOf(rippleColor));
            if (!tombstone) {
                container.setOnClickPendingIntent(buttonId, action.actionIntent);
            }
            container.setContentDescription(buttonId, action.title);
        }

        protected RemoteViews makeMediaContentView(RemoteViews customContent) {
            int numActions = this.mBuilder.mActions.size();
            int[] iArr = this.mActionsToShowInCompact;
            int numActionsToShow = Math.min(iArr == null ? 0 : iArr.length, 3);
            if (numActionsToShow > numActions) {
                throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(numActions), Integer.valueOf(numActions - 1)));
            }
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).hideTime(numActionsToShow > 1).hideSubText(numActionsToShow > 1).hideLeftIcon(false).hideRightIcon(numActionsToShow > 0).hideProgress(true).fillTextsFrom(this.mBuilder);
            TemplateBindResult result = new TemplateBindResult();
            RemoteViews template = this.mBuilder.applyStandardTemplate(C4337R.layout.notification_template_material_media, p, null);
            for (int i = 0; i < 3; i++) {
                if (i < numActionsToShow) {
                    Action action = (Action) this.mBuilder.mActions.get(this.mActionsToShowInCompact[i]);
                    bindMediaActionButton(template, MEDIA_BUTTON_IDS[i], action, p);
                } else {
                    template.setViewVisibility(MEDIA_BUTTON_IDS[i], 8);
                }
            }
            boolean hasActions = numActionsToShow != 0;
            template.setViewVisibility(C4337R.id.media_actions, hasActions ? 0 : 8);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, template, customContent, p, result);
            return template;
        }

        protected RemoteViews makeMediaBigContentView(RemoteViews customContent) {
            int actionCount = Math.min(this.mBuilder.mActions.size(), 5);
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).hideProgress(true).fillTextsFrom(this.mBuilder);
            TemplateBindResult result = new TemplateBindResult();
            RemoteViews template = this.mBuilder.applyStandardTemplate(C4337R.layout.notification_template_material_big_media, p, result);
            for (int i = 0; i < 5; i++) {
                if (i < actionCount) {
                    bindMediaActionButton(template, MEDIA_BUTTON_IDS[i], (Action) this.mBuilder.mActions.get(i), p);
                } else {
                    template.setViewVisibility(MEDIA_BUTTON_IDS[i], 8);
                }
            }
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, template, customContent, p, result);
            return template;
        }
    }

    public static class CallStyle extends Style {
        public static final int CALL_TYPE_INCOMING = 1;
        public static final int CALL_TYPE_ONGOING = 2;
        public static final int CALL_TYPE_SCREENING = 3;
        public static final int CALL_TYPE_UNKNOWN = 0;
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

        CallStyle() {
        }

        public static CallStyle forIncomingCall(Person person, PendingIntent declineIntent, PendingIntent answerIntent) {
            return new CallStyle(1, person, null, (PendingIntent) Objects.requireNonNull(declineIntent, "declineIntent is required"), (PendingIntent) Objects.requireNonNull(answerIntent, "answerIntent is required"));
        }

        public static CallStyle forOngoingCall(Person person, PendingIntent hangUpIntent) {
            return new CallStyle(2, person, (PendingIntent) Objects.requireNonNull(hangUpIntent, "hangUpIntent is required"), null, null);
        }

        public static CallStyle forScreeningCall(Person person, PendingIntent hangUpIntent, PendingIntent answerIntent) {
            return new CallStyle(3, person, (PendingIntent) Objects.requireNonNull(hangUpIntent, "hangUpIntent is required"), null, (PendingIntent) Objects.requireNonNull(answerIntent, "answerIntent is required"));
        }

        private CallStyle(int callType, Person person, PendingIntent hangUpIntent, PendingIntent declineIntent, PendingIntent answerIntent) {
            if (person == null || TextUtils.isEmpty(person.getName())) {
                throw new IllegalArgumentException("person must have a non-empty a name");
            }
            this.mCallType = callType;
            this.mPerson = person;
            this.mAnswerIntent = answerIntent;
            this.mDeclineIntent = declineIntent;
            this.mHangUpIntent = hangUpIntent;
        }

        public CallStyle setIsVideo(boolean isVideo) {
            this.mIsVideo = isVideo;
            return this;
        }

        public CallStyle setVerificationIcon(Icon verificationIcon) {
            this.mVerificationIcon = verificationIcon;
            return this;
        }

        public CallStyle setVerificationText(CharSequence verificationText) {
            this.mVerificationText = Notification.safeCharSequence(verificationText);
            return this;
        }

        public CallStyle setAnswerButtonColorHint(int color) {
            this.mAnswerButtonColor = Integer.valueOf(color);
            return this;
        }

        public CallStyle setDeclineButtonColorHint(int color) {
            this.mDeclineButtonColor = Integer.valueOf(color);
            return this;
        }

        @Override // android.app.Notification.Style
        public Notification buildStyled(Notification wip) {
            Notification wip2 = super.buildStyled(wip);
            this.mBuilder.mActions = getActionsListWithSystemActions();
            wip2.actions = new Action[this.mBuilder.mActions.size()];
            this.mBuilder.mActions.toArray(wip2.actions);
            return wip2;
        }

        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return false;
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
            int i;
            super.reduceImageSizes(context);
            if (this.mVerificationIcon != null) {
                Resources resources = context.getResources();
                if (ActivityManager.isLowRamDeviceStatic()) {
                    i = C4337R.dimen.notification_right_icon_size_low_ram;
                } else {
                    i = C4337R.dimen.notification_right_icon_size;
                }
                int rightIconSize = resources.getDimensionPixelSize(i);
                this.mVerificationIcon.scaleDownIfNecessary(rightIconSize, rightIconSize);
            }
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            return makeCallLayout(StandardTemplateParams.VIEW_TYPE_NORMAL);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            return makeCallLayout(StandardTemplateParams.VIEW_TYPE_HEADS_UP);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            return makeCallLayout(StandardTemplateParams.VIEW_TYPE_BIG);
        }

        private Action makeNegativeAction() {
            PendingIntent pendingIntent = this.mDeclineIntent;
            if (pendingIntent == null) {
                return makeAction(C4337R.drawable.ic_call_decline, C4337R.string.call_notification_hang_up_action, this.mDeclineButtonColor, C4337R.color.call_notification_decline_color, this.mHangUpIntent);
            }
            return makeAction(C4337R.drawable.ic_call_decline, C4337R.string.call_notification_decline_action, this.mDeclineButtonColor, C4337R.color.call_notification_decline_color, pendingIntent);
        }

        private Action makeAnswerAction() {
            PendingIntent pendingIntent = this.mAnswerIntent;
            if (pendingIntent == null) {
                return null;
            }
            boolean z = this.mIsVideo;
            return makeAction(z ? C4337R.drawable.ic_call_answer_video : C4337R.drawable.ic_call_answer, z ? 17039970 : 17039969, this.mAnswerButtonColor, C4337R.color.call_notification_answer_color, pendingIntent);
        }

        private Action makeAction(int icon, int title, Integer colorInt, int defaultColorRes, PendingIntent intent) {
            if (colorInt == null || !this.mBuilder.isCallActionColorCustomizable()) {
                colorInt = Integer.valueOf(this.mBuilder.mContext.getColor(defaultColorRes));
            }
            Action action = new Action.Builder(Icon.createWithResource("", icon), new SpannableStringBuilder().append(this.mBuilder.mContext.getString(title), new ForegroundColorSpan(colorInt.intValue()), 18), intent).build();
            action.getExtras().putBoolean(KEY_ACTION_PRIORITY, true);
            return action;
        }

        private boolean isActionAddedByCallStyle(Action action) {
            return action != null && action.getExtras().getBoolean(KEY_ACTION_PRIORITY);
        }

        public ArrayList<Action> getActionsListWithSystemActions() {
            Action firstAction = makeNegativeAction();
            Action lastAction = makeAnswerAction();
            ArrayList<Action> resultActions = new ArrayList<>(3);
            resultActions.add(firstAction);
            int nonContextualActionSlotsRemaining = 3 - 1;
            if (this.mBuilder.mActions != null) {
                Iterator it = this.mBuilder.mActions.iterator();
                while (it.hasNext()) {
                    Action action = (Action) it.next();
                    if (action.isContextual()) {
                        resultActions.add(action);
                    } else if (!isActionAddedByCallStyle(action)) {
                        resultActions.add(action);
                        nonContextualActionSlotsRemaining--;
                    }
                    if (lastAction != null && nonContextualActionSlotsRemaining == 1) {
                        resultActions.add(lastAction);
                        nonContextualActionSlotsRemaining--;
                    }
                }
            }
            if (lastAction != null && nonContextualActionSlotsRemaining >= 1) {
                resultActions.add(lastAction);
            }
            return resultActions;
        }

        private RemoteViews makeCallLayout(int viewType) {
            RemoteViews contentView;
            boolean isCollapsed = viewType == StandardTemplateParams.VIEW_TYPE_NORMAL;
            Bundle extras = this.mBuilder.f20mN.extras;
            Person person = this.mPerson;
            CharSequence title = person != null ? person.getName() : null;
            CharSequence text = this.mBuilder.processLegacyText(extras.getCharSequence(Notification.EXTRA_TEXT));
            if (text == null) {
                text = getDefaultText();
            }
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(viewType).callStyleActions(true).allowTextWithProgress(true).hideLeftIcon(true).hideRightIcon(true).hideAppName(isCollapsed).titleViewId(C4337R.id.conversation_text).title(title).text(text).summaryText(this.mBuilder.processLegacyText(this.mVerificationText));
            this.mBuilder.mActions = getActionsListWithSystemActions();
            if (isCollapsed) {
                contentView = this.mBuilder.applyStandardTemplate(C4337R.layout.notification_template_material_call, p, null);
            } else {
                contentView = this.mBuilder.applyStandardTemplateWithActions(C4337R.layout.notification_template_material_big_call, p, null);
            }
            if (!p.mHideAppName) {
                this.mBuilder.setTextViewColorSecondary(contentView, C4337R.id.app_name_divider, p);
                contentView.setViewVisibility(C4337R.id.app_name_divider, 8);
            }
            bindCallerVerification(contentView, p);
            contentView.setInt(C4337R.id.status_bar_latest_event_content, "setLayoutColor", this.mBuilder.getSmallIconColor(p));
            contentView.setInt(C4337R.id.status_bar_latest_event_content, "setNotificationBackgroundColor", this.mBuilder.getBackgroundColor(p));
            contentView.setIcon(C4337R.id.status_bar_latest_event_content, "setLargeIcon", this.mBuilder.f20mN.mLargeIcon);
            contentView.setBundle(C4337R.id.status_bar_latest_event_content, "setData", this.mBuilder.f20mN.extras);
            return contentView;
        }

        private void bindCallerVerification(RemoteViews contentView, StandardTemplateParams p) {
            String iconContentDescription = null;
            boolean showDivider = true;
            Icon icon = this.mVerificationIcon;
            if (icon != null) {
                contentView.setImageViewIcon(C4337R.id.verification_icon, icon);
                contentView.setDrawableTint(C4337R.id.verification_icon, false, this.mBuilder.getSecondaryTextColor(p), PorterDuff.Mode.SRC_ATOP);
                contentView.setViewVisibility(C4337R.id.verification_icon, 0);
                iconContentDescription = this.mBuilder.mContext.getString(C4337R.string.notification_verified_content_description);
                showDivider = false;
            } else {
                contentView.setViewVisibility(C4337R.id.verification_icon, 8);
            }
            if (!TextUtils.isEmpty(this.mVerificationText)) {
                contentView.setTextViewText(C4337R.id.verification_text, this.mVerificationText);
                this.mBuilder.setTextViewColorSecondary(contentView, C4337R.id.verification_text, p);
                contentView.setViewVisibility(C4337R.id.verification_text, 0);
                iconContentDescription = null;
            } else {
                contentView.setViewVisibility(C4337R.id.verification_text, 8);
                showDivider = false;
            }
            contentView.setContentDescription(C4337R.id.verification_icon, iconContentDescription);
            if (showDivider) {
                contentView.setViewVisibility(C4337R.id.verification_divider, 8);
                this.mBuilder.setTextViewColorSecondary(contentView, C4337R.id.verification_divider, p);
            } else {
                contentView.setViewVisibility(C4337R.id.verification_divider, 8);
            }
        }

        private String getDefaultText() {
            switch (this.mCallType) {
                case 1:
                    return this.mBuilder.mContext.getString(C4337R.string.call_notification_incoming_text);
                case 2:
                    return this.mBuilder.mContext.getString(C4337R.string.call_notification_ongoing_text);
                case 3:
                    return this.mBuilder.mContext.getString(C4337R.string.call_notification_screening_text);
                default:
                    return null;
            }
        }

        @Override // android.app.Notification.Style
        public void addExtras(Bundle extras) {
            super.addExtras(extras);
            extras.putInt(Notification.EXTRA_CALL_TYPE, this.mCallType);
            extras.putBoolean(Notification.EXTRA_CALL_IS_VIDEO, this.mIsVideo);
            extras.putParcelable(Notification.EXTRA_CALL_PERSON, this.mPerson);
            Icon icon = this.mVerificationIcon;
            if (icon != null) {
                extras.putParcelable(Notification.EXTRA_VERIFICATION_ICON, icon);
            }
            CharSequence charSequence = this.mVerificationText;
            if (charSequence != null) {
                extras.putCharSequence(Notification.EXTRA_VERIFICATION_TEXT, charSequence);
            }
            PendingIntent pendingIntent = this.mAnswerIntent;
            if (pendingIntent != null) {
                extras.putParcelable(Notification.EXTRA_ANSWER_INTENT, pendingIntent);
            }
            PendingIntent pendingIntent2 = this.mDeclineIntent;
            if (pendingIntent2 != null) {
                extras.putParcelable(Notification.EXTRA_DECLINE_INTENT, pendingIntent2);
            }
            PendingIntent pendingIntent3 = this.mHangUpIntent;
            if (pendingIntent3 != null) {
                extras.putParcelable(Notification.EXTRA_HANG_UP_INTENT, pendingIntent3);
            }
            Integer num = this.mAnswerButtonColor;
            if (num != null) {
                extras.putInt(Notification.EXTRA_ANSWER_COLOR, num.intValue());
            }
            Integer num2 = this.mDeclineButtonColor;
            if (num2 != null) {
                extras.putInt(Notification.EXTRA_DECLINE_COLOR, num2.intValue());
            }
            fixTitleAndTextExtras(extras);
        }

        private void fixTitleAndTextExtras(Bundle extras) {
            Person person = this.mPerson;
            CharSequence sender = person != null ? person.getName() : null;
            if (sender != null) {
                extras.putCharSequence(Notification.EXTRA_TITLE, sender);
            }
            if (extras.getCharSequence(Notification.EXTRA_TEXT) == null) {
                extras.putCharSequence(Notification.EXTRA_TEXT, getDefaultText());
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(Bundle extras) {
            super.restoreFromExtras(extras);
            this.mCallType = extras.getInt(Notification.EXTRA_CALL_TYPE);
            this.mIsVideo = extras.getBoolean(Notification.EXTRA_CALL_IS_VIDEO);
            this.mPerson = (Person) extras.getParcelable(Notification.EXTRA_CALL_PERSON, Person.class);
            this.mVerificationIcon = (Icon) extras.getParcelable(Notification.EXTRA_VERIFICATION_ICON, Icon.class);
            this.mVerificationText = extras.getCharSequence(Notification.EXTRA_VERIFICATION_TEXT);
            this.mAnswerIntent = (PendingIntent) extras.getParcelable(Notification.EXTRA_ANSWER_INTENT, PendingIntent.class);
            this.mDeclineIntent = (PendingIntent) extras.getParcelable(Notification.EXTRA_DECLINE_INTENT, PendingIntent.class);
            this.mHangUpIntent = (PendingIntent) extras.getParcelable(Notification.EXTRA_HANG_UP_INTENT, PendingIntent.class);
            this.mAnswerButtonColor = extras.containsKey(Notification.EXTRA_ANSWER_COLOR) ? Integer.valueOf(extras.getInt(Notification.EXTRA_ANSWER_COLOR)) : null;
            this.mDeclineButtonColor = extras.containsKey(Notification.EXTRA_DECLINE_COLOR) ? Integer.valueOf(extras.getInt(Notification.EXTRA_DECLINE_COLOR)) : null;
        }

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            CallStyle otherS = (CallStyle) other;
            return (Objects.equals(Integer.valueOf(this.mCallType), Integer.valueOf(otherS.mCallType)) && Objects.equals(this.mPerson, otherS.mPerson) && Objects.equals(this.mVerificationText, otherS.mVerificationText)) ? false : true;
        }
    }

    public static class DecoratedCustomViewStyle extends Style {
        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            return makeStandardTemplateWithCustomContent(this.mBuilder.f20mN.contentView);
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            return makeDecoratedBigContentView();
        }

        @Override // android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            return makeDecoratedHeadsUpContentView();
        }

        private RemoteViews makeDecoratedHeadsUpContentView() {
            RemoteViews headsUpContentView;
            if (this.mBuilder.f20mN.headsUpContentView == null) {
                headsUpContentView = this.mBuilder.f20mN.contentView;
            } else {
                headsUpContentView = this.mBuilder.f20mN.headsUpContentView;
            }
            if (headsUpContentView == null) {
                return null;
            }
            if (this.mBuilder.mActions.size() == 0) {
                return makeStandardTemplateWithCustomContent(headsUpContentView);
            }
            TemplateBindResult result = new TemplateBindResult();
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_HEADS_UP).decorationType(2).fillTextsFrom(this.mBuilder);
            RemoteViews remoteViews = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getHeadsUpBaseLayoutResource(), p, result);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, remoteViews, headsUpContentView, p, result);
            return remoteViews;
        }

        private RemoteViews makeStandardTemplateWithCustomContent(RemoteViews customContent) {
            if (customContent == null) {
                return null;
            }
            TemplateBindResult result = new TemplateBindResult();
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_NORMAL).decorationType(2).fillTextsFrom(this.mBuilder);
            RemoteViews remoteViews = this.mBuilder.applyStandardTemplate(this.mBuilder.getBaseLayoutResource(), p, result);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, remoteViews, customContent, p, result);
            return remoteViews;
        }

        private RemoteViews makeDecoratedBigContentView() {
            RemoteViews bigContentView;
            if (this.mBuilder.f20mN.bigContentView == null) {
                bigContentView = this.mBuilder.f20mN.contentView;
            } else {
                bigContentView = this.mBuilder.f20mN.bigContentView;
            }
            if (bigContentView == null) {
                return null;
            }
            TemplateBindResult result = new TemplateBindResult();
            StandardTemplateParams p = this.mBuilder.mParams.reset().viewType(StandardTemplateParams.VIEW_TYPE_BIG).decorationType(2).fillTextsFrom(this.mBuilder);
            RemoteViews remoteViews = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getBigBaseLayoutResource(), p, result);
            Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, remoteViews, bigContentView, p, result);
            return remoteViews;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            return false;
        }
    }

    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public RemoteViews makeContentView(boolean increasedHeight) {
            return makeMediaContentView(this.mBuilder.f20mN.contentView);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public RemoteViews makeBigContentView() {
            RemoteViews customContent;
            if (this.mBuilder.f20mN.bigContentView != null) {
                customContent = this.mBuilder.f20mN.bigContentView;
            } else {
                customContent = this.mBuilder.f20mN.contentView;
            }
            return makeMediaBigContentView(customContent);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public RemoteViews makeHeadsUpContentView(boolean increasedHeight) {
            RemoteViews customContent;
            if (this.mBuilder.f20mN.headsUpContentView != null) {
                customContent = this.mBuilder.f20mN.headsUpContentView;
            } else {
                customContent = this.mBuilder.f20mN.contentView;
            }
            return makeMediaBigContentView(customContent);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(Style other) {
            if (other == null || getClass() != other.getClass()) {
                return true;
            }
            return false;
        }
    }

    public static final class BubbleMetadata implements Parcelable {
        public static final Parcelable.Creator<BubbleMetadata> CREATOR = new Parcelable.Creator<BubbleMetadata>() { // from class: android.app.Notification.BubbleMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BubbleMetadata createFromParcel(Parcel source) {
                return new BubbleMetadata(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BubbleMetadata[] newArray(int size) {
                return new BubbleMetadata[size];
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

        private BubbleMetadata(PendingIntent expandIntent, PendingIntent deleteIntent, Icon icon, int height, int heightResId, String shortcutId) {
            this.mPendingIntent = expandIntent;
            this.mIcon = icon;
            this.mDesiredHeight = height;
            this.mDesiredHeightResId = heightResId;
            this.mDeleteIntent = deleteIntent;
            this.mShortcutId = shortcutId;
        }

        private BubbleMetadata(Parcel in) {
            if (in.readInt() != 0) {
                this.mPendingIntent = PendingIntent.CREATOR.createFromParcel(in);
            }
            if (in.readInt() != 0) {
                this.mIcon = Icon.CREATOR.createFromParcel(in);
            }
            this.mDesiredHeight = in.readInt();
            this.mFlags = in.readInt();
            if (in.readInt() != 0) {
                this.mDeleteIntent = PendingIntent.CREATOR.createFromParcel(in);
            }
            this.mDesiredHeightResId = in.readInt();
            if (in.readInt() != 0) {
                this.mShortcutId = in.readString8();
            }
        }

        public String getShortcutId() {
            return this.mShortcutId;
        }

        public PendingIntent getIntent() {
            return this.mPendingIntent;
        }

        @Deprecated
        public PendingIntent getBubbleIntent() {
            return this.mPendingIntent;
        }

        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        @Deprecated
        public Icon getBubbleIcon() {
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

        public void setSuppressNotification(boolean suppressed) {
            if (suppressed) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
        }

        public void setSuppressBubble(boolean suppressed) {
            if (suppressed) {
                this.mFlags |= 8;
            } else {
                this.mFlags &= -9;
            }
        }

        public void setFlags(int flags) {
            this.mFlags = flags;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Override // android.p009os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p009os.Parcelable
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
            if (!TextUtils.isEmpty(this.mShortcutId)) {
                parcel.writeString8(this.mShortcutId);
            }
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

            public Builder(String shortcutId) {
                if (TextUtils.isEmpty(shortcutId)) {
                    throw new NullPointerException("Bubble requires a non-null shortcut id");
                }
                this.mShortcutId = shortcutId;
            }

            public Builder(PendingIntent intent, Icon icon) {
                if (intent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
                if (icon == null) {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
                if (icon.getType() != 6 && icon.getType() != 4) {
                    Log.m102w(Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mPendingIntent = intent;
                this.mIcon = icon;
            }

            @Deprecated
            public Builder createShortcutBubble(String shortcutId) {
                if (!TextUtils.isEmpty(shortcutId)) {
                    this.mPendingIntent = null;
                    this.mIcon = null;
                }
                this.mShortcutId = shortcutId;
                return this;
            }

            @Deprecated
            public Builder createIntentBubble(PendingIntent intent, Icon icon) {
                if (intent == null) {
                    throw new IllegalArgumentException("Bubble requires non-null pending intent");
                }
                if (icon == null) {
                    throw new IllegalArgumentException("Bubbles require non-null icon");
                }
                if (icon.getType() != 6 && icon.getType() != 4) {
                    Log.m102w(Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mShortcutId = null;
                this.mPendingIntent = intent;
                this.mIcon = icon;
                return this;
            }

            public Builder setIntent(PendingIntent intent) {
                if (this.mShortcutId != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set a PendingIntent. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (intent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
                this.mPendingIntent = intent;
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
                    Log.m102w(Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mIcon = icon;
                return this;
            }

            public Builder setDesiredHeight(int height) {
                this.mDesiredHeight = Math.max(height, 0);
                this.mDesiredHeightResId = 0;
                return this;
            }

            public Builder setDesiredHeightResId(int heightResId) {
                this.mDesiredHeightResId = heightResId;
                this.mDesiredHeight = 0;
                return this;
            }

            public Builder setAutoExpandBubble(boolean shouldExpand) {
                setFlag(1, shouldExpand);
                return this;
            }

            public Builder setSuppressNotification(boolean shouldSuppressNotif) {
                setFlag(2, shouldSuppressNotif);
                return this;
            }

            public Builder setSuppressableBubble(boolean suppressBubble) {
                setFlag(4, suppressBubble);
                return this;
            }

            public Builder setDeleteIntent(PendingIntent deleteIntent) {
                this.mDeleteIntent = deleteIntent;
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
                BubbleMetadata data = new BubbleMetadata(this.mPendingIntent, this.mDeleteIntent, this.mIcon, this.mDesiredHeight, this.mDesiredHeightResId, this.mShortcutId);
                data.setFlags(this.mFlags);
                return data;
            }

            public Builder setFlag(int mask, boolean value) {
                if (value) {
                    this.mFlags |= mask;
                } else {
                    this.mFlags &= ~mask;
                }
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

        public WearableExtender(Notification notif) {
            this.mActions = new ArrayList<>();
            this.mFlags = 1;
            this.mPages = new ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
            Bundle wearableBundle = notif.extras.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (wearableBundle != null) {
                List<Action> actions = wearableBundle.getParcelableArrayList("actions", Action.class);
                if (actions != null) {
                    this.mActions.addAll(actions);
                }
                this.mFlags = wearableBundle.getInt("flags", 1);
                this.mDisplayIntent = (PendingIntent) wearableBundle.getParcelable(KEY_DISPLAY_INTENT, PendingIntent.class);
                Notification[] pages = (Notification[]) Notification.getParcelableArrayFromBundle(wearableBundle, KEY_PAGES, Notification.class);
                if (pages != null) {
                    Collections.addAll(this.mPages, pages);
                }
                this.mBackground = (Bitmap) wearableBundle.getParcelable("background", Bitmap.class);
                this.mContentIcon = wearableBundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = wearableBundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = wearableBundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = wearableBundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = wearableBundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = wearableBundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = wearableBundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = wearableBundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = wearableBundle.getString(KEY_BRIDGE_TAG);
            }
        }

        @Override // android.app.Notification.Extender
        public Builder extend(Builder builder) {
            Bundle wearableBundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                wearableBundle.putParcelableArrayList("actions", this.mActions);
            }
            int i = this.mFlags;
            if (i != 1) {
                wearableBundle.putInt("flags", i);
            }
            PendingIntent pendingIntent = this.mDisplayIntent;
            if (pendingIntent != null) {
                wearableBundle.putParcelable(KEY_DISPLAY_INTENT, pendingIntent);
            }
            if (!this.mPages.isEmpty()) {
                ArrayList<Notification> arrayList = this.mPages;
                wearableBundle.putParcelableArray(KEY_PAGES, (Parcelable[]) arrayList.toArray(new Notification[arrayList.size()]));
            }
            Bitmap bitmap = this.mBackground;
            if (bitmap != null) {
                wearableBundle.putParcelable("background", bitmap);
            }
            int i2 = this.mContentIcon;
            if (i2 != 0) {
                wearableBundle.putInt(KEY_CONTENT_ICON, i2);
            }
            int i3 = this.mContentIconGravity;
            if (i3 != 8388613) {
                wearableBundle.putInt(KEY_CONTENT_ICON_GRAVITY, i3);
            }
            int i4 = this.mContentActionIndex;
            if (i4 != -1) {
                wearableBundle.putInt(KEY_CONTENT_ACTION_INDEX, i4);
            }
            int i5 = this.mCustomSizePreset;
            if (i5 != 0) {
                wearableBundle.putInt(KEY_CUSTOM_SIZE_PRESET, i5);
            }
            int i6 = this.mCustomContentHeight;
            if (i6 != 0) {
                wearableBundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, i6);
            }
            int i7 = this.mGravity;
            if (i7 != 80) {
                wearableBundle.putInt(KEY_GRAVITY, i7);
            }
            int i8 = this.mHintScreenTimeout;
            if (i8 != 0) {
                wearableBundle.putInt(KEY_HINT_SCREEN_TIMEOUT, i8);
            }
            String str = this.mDismissalId;
            if (str != null) {
                wearableBundle.putString(KEY_DISMISSAL_ID, str);
            }
            String str2 = this.mBridgeTag;
            if (str2 != null) {
                wearableBundle.putString(KEY_BRIDGE_TAG, str2);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, wearableBundle);
            return builder;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public WearableExtender m903clone() {
            WearableExtender that = new WearableExtender();
            that.mActions = new ArrayList<>(this.mActions);
            that.mFlags = this.mFlags;
            that.mDisplayIntent = this.mDisplayIntent;
            that.mPages = new ArrayList<>(this.mPages);
            that.mBackground = this.mBackground;
            that.mContentIcon = this.mContentIcon;
            that.mContentIconGravity = this.mContentIconGravity;
            that.mContentActionIndex = this.mContentActionIndex;
            that.mCustomSizePreset = this.mCustomSizePreset;
            that.mCustomContentHeight = this.mCustomContentHeight;
            that.mGravity = this.mGravity;
            that.mHintScreenTimeout = this.mHintScreenTimeout;
            that.mDismissalId = this.mDismissalId;
            that.mBridgeTag = this.mBridgeTag;
            return that;
        }

        public WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> actions) {
            this.mActions.addAll(actions);
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
        public WearableExtender setDisplayIntent(PendingIntent intent) {
            this.mDisplayIntent = intent;
            return this;
        }

        @Deprecated
        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        @Deprecated
        public WearableExtender addPage(Notification page) {
            this.mPages.add(page);
            return this;
        }

        @Deprecated
        public WearableExtender addPages(List<Notification> pages) {
            this.mPages.addAll(pages);
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
        public WearableExtender setBackground(Bitmap background) {
            this.mBackground = background;
            return this;
        }

        @Deprecated
        public Bitmap getBackground() {
            return this.mBackground;
        }

        @Deprecated
        public WearableExtender setContentIcon(int icon) {
            this.mContentIcon = icon;
            return this;
        }

        @Deprecated
        public int getContentIcon() {
            return this.mContentIcon;
        }

        @Deprecated
        public WearableExtender setContentIconGravity(int contentIconGravity) {
            this.mContentIconGravity = contentIconGravity;
            return this;
        }

        @Deprecated
        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public WearableExtender setContentAction(int actionIndex) {
            this.mContentActionIndex = actionIndex;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        @Deprecated
        public WearableExtender setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

        @Deprecated
        public int getGravity() {
            return this.mGravity;
        }

        @Deprecated
        public WearableExtender setCustomSizePreset(int sizePreset) {
            this.mCustomSizePreset = sizePreset;
            return this;
        }

        @Deprecated
        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        @Deprecated
        public WearableExtender setCustomContentHeight(int height) {
            this.mCustomContentHeight = height;
            return this;
        }

        @Deprecated
        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public WearableExtender setStartScrollBottom(boolean startScrollBottom) {
            setFlag(8, startScrollBottom);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean contentIntentAvailableOffline) {
            setFlag(1, contentIntentAvailableOffline);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        @Deprecated
        public WearableExtender setHintHideIcon(boolean hintHideIcon) {
            setFlag(2, hintHideIcon);
            return this;
        }

        @Deprecated
        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        @Deprecated
        public WearableExtender setHintShowBackgroundOnly(boolean hintShowBackgroundOnly) {
            setFlag(4, hintShowBackgroundOnly);
            return this;
        }

        @Deprecated
        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        @Deprecated
        public WearableExtender setHintAvoidBackgroundClipping(boolean hintAvoidBackgroundClipping) {
            setFlag(16, hintAvoidBackgroundClipping);
            return this;
        }

        @Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        @Deprecated
        public WearableExtender setHintScreenTimeout(int timeout) {
            this.mHintScreenTimeout = timeout;
            return this;
        }

        @Deprecated
        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        @Deprecated
        public WearableExtender setHintAmbientBigPicture(boolean hintAmbientBigPicture) {
            setFlag(32, hintAmbientBigPicture);
            return this;
        }

        @Deprecated
        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean hintContentIntentLaunchesActivity) {
            setFlag(64, hintContentIntentLaunchesActivity);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public WearableExtender setDismissalId(String dismissalId) {
            this.mDismissalId = dismissalId;
            return this;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public WearableExtender setBridgeTag(String bridgeTag) {
            this.mBridgeTag = bridgeTag;
            return this;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int mask, boolean value) {
            if (value) {
                this.mFlags |= mask;
            } else {
                this.mFlags &= ~mask;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(Consumer<Uri> visitor) {
            Iterator<Action> it = this.mActions.iterator();
            while (it.hasNext()) {
                Action action = it.next();
                action.visitUris(visitor);
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

        public CarExtender(Notification notif) {
            this.mColor = 0;
            Bundle carBundle = notif.extras == null ? null : notif.extras.getBundle(EXTRA_CAR_EXTENDER);
            if (carBundle != null) {
                this.mLargeIcon = (Bitmap) carBundle.getParcelable(EXTRA_LARGE_ICON, Bitmap.class);
                this.mColor = carBundle.getInt(EXTRA_COLOR, 0);
                Bundle b = carBundle.getBundle(EXTRA_CONVERSATION);
                this.mUnreadConversation = UnreadConversation.getUnreadConversationFromBundle(b);
            }
        }

        @Override // android.app.Notification.Extender
        public Builder extend(Builder builder) {
            Bundle carExtensions = new Bundle();
            Bitmap bitmap = this.mLargeIcon;
            if (bitmap != null) {
                carExtensions.putParcelable(EXTRA_LARGE_ICON, bitmap);
            }
            int i = this.mColor;
            if (i != 0) {
                carExtensions.putInt(EXTRA_COLOR, i);
            }
            UnreadConversation unreadConversation = this.mUnreadConversation;
            if (unreadConversation != null) {
                Bundle b = unreadConversation.getBundleForUnreadConversation();
                carExtensions.putBundle(EXTRA_CONVERSATION, b);
            }
            Bundle b2 = builder.getExtras();
            b2.putBundle(EXTRA_CAR_EXTENDER, carExtensions);
            return builder;
        }

        public CarExtender setColor(int color) {
            this.mColor = color;
            return this;
        }

        public int getColor() {
            return this.mColor;
        }

        public CarExtender setLargeIcon(Bitmap largeIcon) {
            this.mLargeIcon = largeIcon;
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

            UnreadConversation(String[] messages, RemoteInput remoteInput, PendingIntent replyPendingIntent, PendingIntent readPendingIntent, String[] participants, long latestTimestamp) {
                this.mMessages = messages;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = readPendingIntent;
                this.mReplyPendingIntent = replyPendingIntent;
                this.mParticipants = participants;
                this.mLatestTimestamp = latestTimestamp;
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
                Bundle b = new Bundle();
                String author = null;
                String[] strArr = this.mParticipants;
                if (strArr != null && strArr.length > 1) {
                    author = strArr[0];
                }
                Parcelable[] messages = new Parcelable[this.mMessages.length];
                for (int i = 0; i < messages.length; i++) {
                    Bundle m = new Bundle();
                    m.putString("text", this.mMessages[i]);
                    m.putString("author", author);
                    messages[i] = m;
                }
                b.putParcelableArray(KEY_MESSAGES, messages);
                Parcelable parcelable = this.mRemoteInput;
                if (parcelable != null) {
                    b.putParcelable(KEY_REMOTE_INPUT, parcelable);
                }
                b.putParcelable(KEY_ON_REPLY, this.mReplyPendingIntent);
                b.putParcelable(KEY_ON_READ, this.mReadPendingIntent);
                b.putStringArray(KEY_PARTICIPANTS, this.mParticipants);
                b.putLong("timestamp", this.mLatestTimestamp);
                return b;
            }

            static UnreadConversation getUnreadConversationFromBundle(Bundle b) {
                if (b == null) {
                    return null;
                }
                Parcelable[] parcelableMessages = (Parcelable[]) b.getParcelableArray(KEY_MESSAGES, Parcelable.class);
                String[] messages = null;
                if (parcelableMessages != null) {
                    String[] tmp = new String[parcelableMessages.length];
                    boolean success = true;
                    int i = 0;
                    while (true) {
                        if (i >= tmp.length) {
                            break;
                        }
                        if (!(parcelableMessages[i] instanceof Bundle)) {
                            success = false;
                            break;
                        }
                        tmp[i] = ((Bundle) parcelableMessages[i]).getString("text");
                        if (tmp[i] != null) {
                            i++;
                        } else {
                            success = false;
                            break;
                        }
                    }
                    if (!success) {
                        return null;
                    }
                    messages = tmp;
                }
                PendingIntent onRead = (PendingIntent) b.getParcelable(KEY_ON_READ, PendingIntent.class);
                PendingIntent onReply = (PendingIntent) b.getParcelable(KEY_ON_REPLY, PendingIntent.class);
                RemoteInput remoteInput = (RemoteInput) b.getParcelable(KEY_REMOTE_INPUT, RemoteInput.class);
                String[] participants = b.getStringArray(KEY_PARTICIPANTS);
                if (participants == null || participants.length != 1) {
                    return null;
                }
                return new UnreadConversation(messages, remoteInput, onReply, onRead, participants, b.getLong("timestamp"));
            }
        }

        public static class Builder {
            private long mLatestTimestamp;
            private final List<String> mMessages = new ArrayList();
            private final String mParticipant;
            private PendingIntent mReadPendingIntent;
            private RemoteInput mRemoteInput;
            private PendingIntent mReplyPendingIntent;

            public Builder(String name) {
                this.mParticipant = name;
            }

            public Builder addMessage(String message) {
                this.mMessages.add(message);
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

            public Builder setLatestTimestamp(long timestamp) {
                this.mLatestTimestamp = timestamp;
                return this;
            }

            public UnreadConversation build() {
                List<String> list = this.mMessages;
                String[] messages = (String[]) list.toArray(new String[list.size()]);
                String[] participants = {this.mParticipant};
                return new UnreadConversation(messages, this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, participants, this.mLatestTimestamp);
            }
        }
    }

    @SystemApi
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

        public TvExtender(Notification notif) {
            Bundle bundle = notif.extras == null ? null : notif.extras.getBundle(EXTRA_TV_EXTENDER);
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

        public TvExtender setChannel(String channelId) {
            this.mChannelId = channelId;
            return this;
        }

        public TvExtender setChannelId(String channelId) {
            this.mChannelId = channelId;
            return this;
        }

        @Deprecated
        public String getChannel() {
            return this.mChannelId;
        }

        public String getChannelId() {
            return this.mChannelId;
        }

        public TvExtender setContentIntent(PendingIntent intent) {
            this.mContentIntent = intent;
            return this;
        }

        public PendingIntent getContentIntent() {
            return this.mContentIntent;
        }

        public TvExtender setDeleteIntent(PendingIntent intent) {
            this.mDeleteIntent = intent;
            return this;
        }

        public PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public TvExtender setSuppressShowOverApps(boolean suppress) {
            this.mSuppressShowOverApps = suppress;
            return this;
        }

        public boolean getSuppressShowOverApps() {
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
        public BuilderRemoteViews(Parcel parcel) {
            super(parcel);
        }

        public BuilderRemoteViews(ApplicationInfo appInfo, int layoutId) {
            super(appInfo, layoutId);
        }

        @Override // android.widget.RemoteViews
        /* renamed from: clone */
        public BuilderRemoteViews mo897clone() {
            Parcel p = Parcel.obtain();
            writeToParcel(p, 0);
            p.setDataPosition(0);
            BuilderRemoteViews brv = new BuilderRemoteViews(p);
            p.recycle();
            return brv;
        }

        @Override // android.widget.RemoteViews
        protected boolean shouldUseStaticFilter() {
            return true;
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

        public void setRightIconState(boolean visible, float widthDp, float heightDp, float marginEndDpIfVisible, float expanderSizeDp) {
            this.mRightIconVisible = visible;
            this.mRightIconWidthDp = widthDp;
            this.mRightIconHeightDp = heightDp;
            this.mHeadingExtraMarginSet.setValues(0.0f, marginEndDpIfVisible);
            this.mHeadingFullMarginSet.setValues(expanderSizeDp, marginEndDpIfVisible + expanderSizeDp);
            this.mTitleMarginSet.setValues(0.0f, marginEndDpIfVisible + expanderSizeDp);
        }

        private class MarginSet {
            private float mValueIfGone;
            private float mValueIfVisible;

            private MarginSet() {
            }

            public void setValues(float valueIfGone, float valueIfVisible) {
                this.mValueIfGone = valueIfGone;
                this.mValueIfVisible = valueIfVisible;
            }

            public void applyToView(RemoteViews views, int viewId) {
                applyToView(views, viewId, 0.0f);
            }

            public void applyToView(RemoteViews views, int viewId, float extraMarginDp) {
                float marginEndDp = getDpValue() + extraMarginDp;
                if (viewId == 16909393) {
                    views.setFloat(C4337R.id.notification_header, "setTopLineExtraMarginEndDp", marginEndDp);
                } else if (viewId != 16909857 && viewId != 16908842) {
                    views.setViewLayoutMargin(viewId, 5, marginEndDp, 1);
                } else {
                    if (this.mValueIfGone != 0.0f) {
                        throw new RuntimeException("Programming error: `text` and `big_text` use ImageFloatingTextView which can either show a margin or not; thus mValueIfGone must be 0, but it was " + this.mValueIfGone);
                    }
                    views.setFloat(viewId, "setImageEndMarginDp", this.mValueIfVisible);
                    views.setBoolean(viewId, "setHasImage", TemplateBindResult.this.mRightIconVisible);
                    views.setViewLayoutMargin(viewId, 5, extraMarginDp, 1);
                }
                if (TemplateBindResult.this.mRightIconVisible) {
                    views.setIntTag(viewId, C4337R.id.tag_margin_end_when_icon_visible, TypedValue.createComplexDimension(this.mValueIfVisible + extraMarginDp, 1));
                    views.setIntTag(viewId, C4337R.id.tag_margin_end_when_icon_gone, TypedValue.createComplexDimension(this.mValueIfGone + extraMarginDp, 1));
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
        public static int VIEW_TYPE_UNSPECIFIED = 0;
        public static int VIEW_TYPE_NORMAL = 1;
        public static int VIEW_TYPE_BIG = 2;
        public static int VIEW_TYPE_HEADS_UP = 3;
        public static int VIEW_TYPE_MINIMIZED = 4;
        public static int VIEW_TYPE_PUBLIC = 5;
        public static int VIEW_TYPE_GROUP_HEADER = 6;
        public static int VIEW_TYPE_GROUP_HEADER_EXPANDED = 7;

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
            this.mTextViewId = C4337R.id.text;
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

        final StandardTemplateParams viewType(int viewType) {
            this.mViewType = viewType;
            return this;
        }

        public StandardTemplateParams headerless(boolean headerless) {
            this.mHeaderless = headerless;
            return this;
        }

        public StandardTemplateParams hideAppName(boolean hideAppName) {
            this.mHideAppName = hideAppName;
            return this;
        }

        public StandardTemplateParams hideSubText(boolean hideSubText) {
            this.mHideSubText = hideSubText;
            return this;
        }

        public StandardTemplateParams hideTime(boolean hideTime) {
            this.mHideTime = hideTime;
            return this;
        }

        final StandardTemplateParams hideActions(boolean hideActions) {
            this.mHideActions = hideActions;
            return this;
        }

        final StandardTemplateParams hideProgress(boolean hideProgress) {
            this.mHideProgress = hideProgress;
            return this;
        }

        final StandardTemplateParams hideTitle(boolean hideTitle) {
            this.mHideTitle = hideTitle;
            return this;
        }

        final StandardTemplateParams callStyleActions(boolean callStyleActions) {
            this.mCallStyleActions = callStyleActions;
            return this;
        }

        final StandardTemplateParams allowTextWithProgress(boolean allowTextWithProgress) {
            this.mAllowTextWithProgress = allowTextWithProgress;
            return this;
        }

        final StandardTemplateParams hideSnoozeButton(boolean hideSnoozeButton) {
            this.mHideSnoozeButton = hideSnoozeButton;
            return this;
        }

        final StandardTemplateParams promotedPicture(Icon promotedPicture) {
            this.mPromotedPicture = promotedPicture;
            return this;
        }

        public StandardTemplateParams titleViewId(int titleViewId) {
            this.mTitleViewId = titleViewId;
            return this;
        }

        public StandardTemplateParams textViewId(int textViewId) {
            this.mTextViewId = textViewId;
            return this;
        }

        final StandardTemplateParams title(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        final StandardTemplateParams text(CharSequence text) {
            this.mText = text;
            return this;
        }

        final StandardTemplateParams summaryText(CharSequence text) {
            this.mSubText = text;
            return this;
        }

        final StandardTemplateParams headerTextSecondary(CharSequence text) {
            this.mHeaderTextSecondary = text;
            return this;
        }

        final StandardTemplateParams hideLeftIcon(boolean hideLeftIcon) {
            this.mHideLeftIcon = hideLeftIcon;
            return this;
        }

        final StandardTemplateParams hideRightIcon(boolean hideRightIcon) {
            this.mHideRightIcon = hideRightIcon;
            return this;
        }

        final StandardTemplateParams disallowColorization() {
            this.allowColorization = false;
            return this;
        }

        final StandardTemplateParams highlightExpander(boolean highlight) {
            this.mHighlightExpander = highlight;
            return this;
        }

        final StandardTemplateParams fillTextsFrom(Builder b) {
            Bundle extras = b.f20mN.extras;
            this.mTitle = b.processLegacyText(extras.getCharSequence(Notification.EXTRA_TITLE));
            this.mText = b.processLegacyText(extras.getCharSequence(Notification.EXTRA_TEXT));
            this.mSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
            return this;
        }

        public StandardTemplateParams setMaxRemoteInputHistory(int maxRemoteInputHistory) {
            this.maxRemoteInputHistory = maxRemoteInputHistory;
            return this;
        }

        public StandardTemplateParams decorationType(int decorationType) {
            boolean hideOtherFields = true;
            hideTitle(true);
            if (decorationType > 1) {
                hideOtherFields = false;
            }
            hideLeftIcon(false);
            hideRightIcon(hideOtherFields);
            hideProgress(hideOtherFields);
            hideActions(hideOtherFields);
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
        private int mOnAccentTextColor = 1;
        private int mErrorColor = 1;
        private int mContrastColor = 1;
        private int mRippleAlpha = 51;

        private static TypedArray obtainDayNightAttributes(Context ctx, int[] attrs) {
            if (ctx.getTheme() == null) {
                return null;
            }
            Resources.Theme theme = new ContextThemeWrapper(ctx, 16974563).getTheme();
            return theme.obtainStyledAttributes(attrs);
        }

        private static int getColor(TypedArray ta, int index, int defValue) {
            return ta == null ? defValue : ta.getColor(index, defValue);
        }

        public void resolvePalette(Context ctx, int rawColor, boolean isColorized, boolean nightMode) {
            TypedArray ta;
            if (this.mPaletteIsForRawColor == rawColor && this.mPaletteIsForColorized == isColorized && this.mPaletteIsForNightMode == nightMode) {
                return;
            }
            this.mPaletteIsForRawColor = rawColor;
            this.mPaletteIsForColorized = isColorized;
            this.mPaletteIsForNightMode = nightMode;
            if (isColorized) {
                if (rawColor == 0) {
                    int[] attrs = {C4337R.attr.colorAccentSecondary};
                    ta = obtainDayNightAttributes(ctx, attrs);
                    try {
                        this.mBackgroundColor = getColor(ta, 0, -1);
                        if (ta != null) {
                            ta.close();
                        }
                    } finally {
                    }
                } else {
                    this.mBackgroundColor = rawColor;
                }
                this.mProtectionColor = 1;
                this.mPrimaryTextColor = ContrastColorUtil.findAlphaToMeetContrast(ContrastColorUtil.resolvePrimaryColor(ctx, this.mBackgroundColor, nightMode), this.mBackgroundColor, 4.5d);
                int findAlphaToMeetContrast = ContrastColorUtil.findAlphaToMeetContrast(ContrastColorUtil.resolveSecondaryColor(ctx, this.mBackgroundColor, nightMode), this.mBackgroundColor, 4.5d);
                this.mSecondaryTextColor = findAlphaToMeetContrast;
                this.mThirdTextColor = findAlphaToMeetContrast;
                int i = this.mPrimaryTextColor;
                this.mContrastColor = i;
                this.mPrimaryAccentColor = i;
                this.mSecondaryAccentColor = findAlphaToMeetContrast;
                this.mTertiaryAccentColor = flattenAlpha(i, this.mBackgroundColor);
                this.mOnAccentTextColor = this.mBackgroundColor;
                this.mErrorColor = this.mPrimaryTextColor;
                this.mRippleAlpha = 51;
            } else {
                int[] attrs2 = {C4337R.attr.colorSurface, 16844002, 16842806, 16842808, 16843829, C4337R.attr.colorAccentSecondary, C4337R.attr.colorAccentTertiary, C4337R.attr.textColorOnAccent, 16844099, 16843820};
                ta = obtainDayNightAttributes(ctx, attrs2);
                try {
                    this.mBackgroundColor = getColor(ta, 0, nightMode ? -16777216 : -1);
                    this.mProtectionColor = getColor(ta, 1, 1);
                    this.mPrimaryTextColor = getColor(ta, 2, 1);
                    this.mSecondaryTextColor = getColor(ta, 3, 1);
                    this.mPrimaryAccentColor = getColor(ta, 4, 1);
                    this.mSecondaryAccentColor = getColor(ta, 5, 1);
                    this.mTertiaryAccentColor = getColor(ta, 6, 1);
                    this.mOnAccentTextColor = getColor(ta, 7, 1);
                    this.mErrorColor = getColor(ta, 8, 1);
                    this.mRippleAlpha = Color.alpha(getColor(ta, 9, 872415231));
                    if (ta != null) {
                        ta.close();
                    }
                    this.mContrastColor = calculateContrastColor(ctx, rawColor, this.mPrimaryAccentColor, this.mBackgroundColor, nightMode);
                    int resolvePrimaryColor = ContrastColorUtil.resolvePrimaryColor(ctx, 0, nightMode);
                    this.mPrimaryTextColor = resolvePrimaryColor;
                    this.mPrimaryAccentColor = resolvePrimaryColor;
                    int resolveSecondaryColor = ContrastColorUtil.resolveSecondaryColor(ctx, 0, nightMode);
                    this.mSecondaryTextColor = resolveSecondaryColor;
                    this.mSecondaryAccentColor = resolveSecondaryColor;
                    this.mThirdTextColor = ContrastColorUtil.resolveThirdColor(ctx, 0, nightMode);
                    if (this.mTertiaryAccentColor == 1) {
                        this.mTertiaryAccentColor = this.mContrastColor;
                    }
                    if (this.mOnAccentTextColor == 1) {
                        this.mOnAccentTextColor = ColorUtils.setAlphaComponent(ContrastColorUtil.resolvePrimaryColor(ctx, this.mTertiaryAccentColor, nightMode), 255);
                    }
                    if (this.mErrorColor == 1) {
                        this.mErrorColor = this.mPrimaryTextColor;
                    }
                } finally {
                }
            }
            if (this.mProtectionColor == 1) {
                this.mProtectionColor = ColorUtils.blendARGB(this.mPrimaryTextColor, this.mBackgroundColor, 0.8f);
            }
        }

        private static int calculateContrastColor(Context ctx, int rawColor, int accentColor, int backgroundColor, boolean nightMode) {
            int color;
            if (rawColor == 0) {
                color = accentColor;
                if (color == 1) {
                    color = ContrastColorUtil.resolveDefaultColor(ctx, backgroundColor, nightMode);
                }
            } else {
                color = ContrastColorUtil.resolveContrastColor(ctx, rawColor, backgroundColor, nightMode);
            }
            return flattenAlpha(color, backgroundColor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int flattenAlpha(int color, int background) {
            return Color.alpha(color) == 255 ? color : ContrastColorUtil.compositeColors(color, background);
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

        public int getOnAccentTextColor() {
            return this.mOnAccentTextColor;
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
    public static int getFontScaledMarginHeight(Context context, int dimenId) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(dimenId);
        float factor = context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density;
        float factor2 = ((factor - 1.0f) / 2.0f) + 1.0f;
        float factor3 = dimensionPixelSize;
        return (int) (factor3 * factor2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getFontScaledHeight(Context context, int dimenId) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(dimenId);
        float factor = context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density;
        return (int) (dimensionPixelSize * factor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap scaleDownIfNecessaryForBigPicture(Bitmap bitmap, int maxWidth, int maxHeight) {
        float factor;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        if (bitmapWidth > maxWidth && bitmapHeight > maxHeight) {
            boolean isTargetHeight = bitmapWidth > bitmapHeight;
            if (isTargetHeight) {
                factor = maxHeight / bitmapHeight;
            } else {
                float factor2 = maxWidth;
                factor = factor2 / bitmapWidth;
            }
            float factor3 = bitmapWidth;
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, Math.max(1, (int) (factor3 * factor)), Math.max(1, (int) (bitmapHeight * factor)), true);
            Log.m94d(TAG, "bigpicture scaling before[" + bitmapWidth + " : " + bitmapHeight + "]  after[" + bitmap2.getWidth() + " : " + bitmap2.getHeight() + NavigationBarInflaterView.SIZE_MOD_END);
            return bitmap2;
        }
        return bitmap;
    }
}
