package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat$Action;
import androidx.core.app.NotificationCompat$BubbleMetadata;
import androidx.core.app.RemoteInput;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class NotificationCompat$Builder {
    public final ArrayList mActions;
    public final boolean mAllowSystemGeneratedContextualActions;
    public final int mBadgeIcon;
    public final NotificationCompat$BubbleMetadata mBubbleMetadata;
    public final String mCategory;
    public final String mChannelId;
    public final int mColor;
    public final boolean mColorized;
    public final boolean mColorizedSet;
    public final CharSequence mContentInfo;
    public PendingIntent mContentIntent;
    public CharSequence mContentText;
    public CharSequence mContentTitle;
    public final Context mContext;
    public Bundle mExtras;
    public final PendingIntent mFullScreenIntent;
    public final String mGroupKey;
    public final boolean mGroupSummary;
    public final ArrayList mInvisibleActions;
    public final IconCompat mLargeIcon;
    public boolean mLocalOnly;
    public final LocusIdCompat mLocusId;
    public final Notification mNotification;
    public final int mNumber;
    public final ArrayList mPeople;
    public final ArrayList mPersonList;
    public int mPriority;
    public final int mProgress;
    public final boolean mProgressIndeterminate;
    public final int mProgressMax;
    public final Notification mPublicVersion;
    public final CharSequence mSettingsText;
    public final String mShortcutId;
    public final boolean mShowWhen;
    public final Object mSmallIcon;
    public final String mSortKey;
    public NotificationCompat$Style mStyle;
    public final CharSequence mSubText;
    public final long mTimeout;
    public final boolean mUseChronometer;
    public final int mVisibility;

    public class Api21Impl {
        private Api21Impl() {
        }

        public static AudioAttributes build(AudioAttributes.Builder builder) {
            return builder.build();
        }

        public static AudioAttributes.Builder createBuilder() {
            return new AudioAttributes.Builder();
        }

        public static AudioAttributes.Builder setContentType(AudioAttributes.Builder builder, int i) {
            return builder.setContentType(i);
        }

        public static AudioAttributes.Builder setLegacyStreamType(AudioAttributes.Builder builder, int i) {
            return builder.setLegacyStreamType(i);
        }
    }

    public class Api23Impl {
        private Api23Impl() {
        }

        public static Icon getLargeIcon(Notification notification2) {
            return notification2.getLargeIcon();
        }

        public static Icon getSmallIcon(Notification notification2) {
            return notification2.getSmallIcon();
        }
    }

    public NotificationCompat$Builder(Context context, String str) {
        this.mActions = new ArrayList();
        this.mPersonList = new ArrayList();
        this.mInvisibleActions = new ArrayList();
        this.mShowWhen = true;
        this.mLocalOnly = false;
        this.mColor = 0;
        this.mVisibility = 0;
        this.mBadgeIcon = 0;
        Notification notification2 = new Notification();
        this.mNotification = notification2;
        this.mContext = context;
        this.mChannelId = str;
        notification2.when = System.currentTimeMillis();
        notification2.audioStreamType = -1;
        this.mPriority = 0;
        this.mPeople = new ArrayList();
        this.mAllowSystemGeneratedContextualActions = true;
    }

    public static CharSequence limitCharSequenceLength(CharSequence charSequence) {
        return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
    }

    public final void addExtras(Bundle bundle) {
        if (bundle != null) {
            Bundle bundle2 = this.mExtras;
            if (bundle2 == null) {
                this.mExtras = new Bundle(bundle);
            } else {
                bundle2.putAll(bundle);
            }
        }
    }

    public final Notification build() {
        Bundle bundle;
        NotificationCompatBuilder notificationCompatBuilder = new NotificationCompatBuilder(this);
        NotificationCompat$Builder notificationCompat$Builder = notificationCompatBuilder.mBuilderCompat;
        NotificationCompat$Style notificationCompat$Style = notificationCompat$Builder.mStyle;
        if (notificationCompat$Style != null) {
            notificationCompat$Style.apply(notificationCompatBuilder);
        }
        Notification notificationBuild = notificationCompatBuilder.mBuilder.build();
        if (notificationCompat$Style != null) {
            notificationCompat$Builder.mStyle.getClass();
        }
        if (notificationCompat$Style != null && (bundle = notificationBuild.extras) != null) {
            notificationCompat$Style.addCompatExtras(bundle);
        }
        return notificationBuild;
    }

    public final void setFlag(int i, boolean z) {
        if (z) {
            Notification notification2 = this.mNotification;
            notification2.flags = i | notification2.flags;
        } else {
            Notification notification3 = this.mNotification;
            notification3.flags = (~i) & notification3.flags;
        }
    }

    @Deprecated
    public NotificationCompat$Builder(Context context) {
        this(context, (String) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0014  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x014a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NotificationCompat$Builder(Context context, Notification notification2) {
        NotificationCompat$Style notificationCompat$MessagingStyle;
        LocusIdCompat locusIdCompat;
        Bundle bundle;
        String str;
        NotificationCompat$Builder notificationCompat$Builder;
        Bundle bundle2;
        Bundle[] bundleArr;
        Bundle[] bundleArr2;
        NotificationCompat$Action.Builder builder;
        int i;
        this(context, notification2.getChannelId());
        Bundle bundle3 = notification2.extras;
        if (bundle3 != null) {
            String string = bundle3.getString("androidx.core.app.extra.COMPAT_TEMPLATE");
            if (string != null) {
                switch (string) {
                    case "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle":
                        notificationCompat$MessagingStyle = new NotificationCompat$Style() { // from class: androidx.core.app.NotificationCompat$DecoratedCustomViewStyle

                            public class Api24Impl {
                                private Api24Impl() {
                                }

                                public static Notification.Style createDecoratedCustomViewStyle() {
                                    return new Notification.DecoratedCustomViewStyle();
                                }
                            }

                            @Override // androidx.core.app.NotificationCompat$Style
                            public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
                                notificationCompatBuilder.mBuilder.setStyle(Api24Impl.createDecoratedCustomViewStyle());
                            }

                            @Override // androidx.core.app.NotificationCompat$Style
                            public final String getClassName() {
                                return "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";
                            }
                        };
                        break;
                    case "androidx.core.app.NotificationCompat$BigPictureStyle":
                        notificationCompat$MessagingStyle = new NotificationCompat$BigPictureStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$CallStyle":
                        notificationCompat$MessagingStyle = new NotificationCompat$CallStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$InboxStyle":
                        notificationCompat$MessagingStyle = new NotificationCompat$InboxStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$BigTextStyle":
                        notificationCompat$MessagingStyle = new NotificationCompat$BigTextStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$MessagingStyle":
                        notificationCompat$MessagingStyle = new NotificationCompat$MessagingStyle();
                        break;
                    default:
                        notificationCompat$MessagingStyle = null;
                        break;
                }
                if (notificationCompat$MessagingStyle == null) {
                    if (!bundle3.containsKey("android.selfDisplayName") && !bundle3.containsKey("android.messagingStyleUser")) {
                        if (!bundle3.containsKey("android.picture") && !bundle3.containsKey("android.pictureIcon")) {
                            if (bundle3.containsKey("android.bigText")) {
                                notificationCompat$MessagingStyle = new NotificationCompat$BigTextStyle();
                            } else if (bundle3.containsKey("android.textLines")) {
                                notificationCompat$MessagingStyle = new NotificationCompat$InboxStyle();
                            } else if (bundle3.containsKey("android.callType")) {
                                notificationCompat$MessagingStyle = new NotificationCompat$CallStyle();
                            } else {
                                String string2 = bundle3.getString("android.template");
                                if (string2 != null) {
                                    if (string2.equals(Notification.BigPictureStyle.class.getName())) {
                                        notificationCompat$MessagingStyle = new NotificationCompat$BigPictureStyle();
                                    } else if (string2.equals(Notification.BigTextStyle.class.getName())) {
                                        notificationCompat$MessagingStyle = new NotificationCompat$BigTextStyle();
                                    } else if (string2.equals(Notification.InboxStyle.class.getName())) {
                                        notificationCompat$MessagingStyle = new NotificationCompat$InboxStyle();
                                    } else if (string2.equals(Notification.MessagingStyle.class.getName())) {
                                        notificationCompat$MessagingStyle = new NotificationCompat$MessagingStyle();
                                    } else {
                                        notificationCompat$MessagingStyle = string2.equals(Notification.DecoratedCustomViewStyle.class.getName()) ? new NotificationCompat$Style() { // from class: androidx.core.app.NotificationCompat$DecoratedCustomViewStyle

                                            public class Api24Impl {
                                                private Api24Impl() {
                                                }

                                                public static Notification.Style createDecoratedCustomViewStyle() {
                                                    return new Notification.DecoratedCustomViewStyle();
                                                }
                                            }

                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
                                                notificationCompatBuilder.mBuilder.setStyle(Api24Impl.createDecoratedCustomViewStyle());
                                            }

                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final String getClassName() {
                                                return "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";
                                            }
                                        } : null;
                                    }
                                }
                            }
                        } else {
                            notificationCompat$MessagingStyle = new NotificationCompat$BigPictureStyle();
                        }
                    } else {
                        notificationCompat$MessagingStyle = new NotificationCompat$MessagingStyle();
                    }
                }
                if (notificationCompat$MessagingStyle == null) {
                    notificationCompat$MessagingStyle = null;
                } else {
                    try {
                        notificationCompat$MessagingStyle.restoreFromCompatExtras(bundle3);
                    } catch (ClassCastException unused) {
                    }
                }
            }
        }
        this.mContentTitle = limitCharSequenceLength(notification2.extras.getCharSequence("android.title"));
        this.mContentText = limitCharSequenceLength(notification2.extras.getCharSequence("android.text"));
        this.mContentInfo = limitCharSequenceLength(notification2.extras.getCharSequence("android.infoText"));
        this.mSubText = limitCharSequenceLength(notification2.extras.getCharSequence("android.subText"));
        this.mSettingsText = limitCharSequenceLength(notification2.getSettingsText());
        if (this.mStyle != notificationCompat$MessagingStyle) {
            this.mStyle = notificationCompat$MessagingStyle;
            if (notificationCompat$MessagingStyle != null) {
                notificationCompat$MessagingStyle.setBuilder(this);
            }
        }
        this.mGroupKey = notification2.getGroup();
        this.mGroupSummary = (notification2.flags & 512) != 0;
        LocusId locusId = notification2.getLocusId();
        if (locusId == null) {
            locusIdCompat = null;
        } else {
            String id = locusId.getId();
            if (!TextUtils.isEmpty(id)) {
                locusIdCompat = new LocusIdCompat(id);
            } else {
                throw new IllegalArgumentException("id cannot be empty");
            }
        }
        this.mLocusId = locusIdCompat;
        this.mNotification.when = notification2.when;
        this.mShowWhen = notification2.extras.getBoolean("android.showWhen");
        this.mUseChronometer = notification2.extras.getBoolean("android.showChronometer");
        setFlag(16, (notification2.flags & 16) != 0);
        setFlag(8, (notification2.flags & 8) != 0);
        setFlag(2, (notification2.flags & 2) != 0);
        this.mLocalOnly = (notification2.flags & 256) != 0;
        Bitmap bitmap = notification2.largeIcon;
        this.mLargeIcon = bitmap == null ? null : IconCompat.createWithBitmap(bitmap);
        this.mBadgeIcon = notification2.getBadgeIconType();
        this.mCategory = notification2.category;
        Notification.BubbleMetadata bubbleMetadata = notification2.getBubbleMetadata();
        this.mBubbleMetadata = bubbleMetadata == null ? null : NotificationCompat$BubbleMetadata.Api30Impl.fromPlatform(bubbleMetadata);
        this.mNumber = notification2.number;
        this.mNotification.tickerText = limitCharSequenceLength(notification2.tickerText);
        this.mContentIntent = notification2.contentIntent;
        this.mNotification.deleteIntent = notification2.deleteIntent;
        PendingIntent pendingIntent = notification2.fullScreenIntent;
        boolean z = (notification2.flags & 128) != 0;
        this.mFullScreenIntent = pendingIntent;
        setFlag(128, z);
        Uri uri = notification2.sound;
        int i2 = notification2.audioStreamType;
        Notification notification3 = this.mNotification;
        notification3.sound = uri;
        notification3.audioStreamType = i2;
        this.mNotification.audioAttributes = Api21Impl.build(Api21Impl.setLegacyStreamType(Api21Impl.setContentType(Api21Impl.createBuilder(), 4), i2));
        long[] jArr = notification2.vibrate;
        Notification notification4 = this.mNotification;
        notification4.vibrate = jArr;
        int i3 = notification2.ledARGB;
        int i4 = notification2.ledOnMS;
        int i5 = notification2.ledOffMS;
        notification4.ledARGB = i3;
        notification4.ledOnMS = i4;
        notification4.ledOffMS = i5;
        int i6 = ((i4 == 0 || i5 == 0) ? 0 : 1) | (notification4.flags & (-2));
        notification4.flags = i6;
        int i7 = notification2.defaults;
        notification4.defaults = i7;
        if ((i7 & 4) != 0) {
            notification4.flags = i6 | 1;
        }
        this.mPriority = notification2.priority;
        this.mColor = notification2.color;
        this.mVisibility = notification2.visibility;
        this.mPublicVersion = notification2.publicVersion;
        this.mSortKey = notification2.getSortKey();
        this.mTimeout = notification2.getTimeoutAfter();
        this.mShortcutId = notification2.getShortcutId();
        int i8 = bundle3.getInt("android.progressMax");
        int i9 = bundle3.getInt("android.progress");
        boolean z2 = bundle3.getBoolean("android.progressIndeterminate");
        this.mProgressMax = i8;
        this.mProgress = i9;
        this.mProgressIndeterminate = z2;
        this.mAllowSystemGeneratedContextualActions = notification2.getAllowSystemGeneratedContextualActions();
        int i10 = notification2.icon;
        int i11 = notification2.iconLevel;
        Notification notification5 = this.mNotification;
        notification5.icon = i10;
        notification5.iconLevel = i11;
        if (notification2.extras == null) {
            bundle = null;
            notificationCompat$Builder = this;
            str = "invisible_actions";
        } else {
            NotificationCompat$Style notificationCompat$Style = notificationCompat$MessagingStyle;
            bundle = new Bundle(notification2.extras);
            bundle.remove("android.title");
            bundle.remove("android.text");
            bundle.remove("android.infoText");
            bundle.remove("android.subText");
            bundle.remove("android.intent.extra.CHANNEL_ID");
            bundle.remove("android.intent.extra.CHANNEL_GROUP_ID");
            bundle.remove("android.showWhen");
            bundle.remove("android.progress");
            bundle.remove("android.progressMax");
            bundle.remove("android.progressIndeterminate");
            bundle.remove("android.chronometerCountDown");
            bundle.remove("android.colorized");
            bundle.remove("android.people.list");
            bundle.remove("android.people");
            bundle.remove("android.support.sortKey");
            bundle.remove("android.support.groupKey");
            bundle.remove("android.support.isGroupSummary");
            bundle.remove("android.support.localOnly");
            bundle.remove("android.support.actionExtras");
            Bundle bundle4 = bundle.getBundle("android.car.EXTENSIONS");
            if (bundle4 != null) {
                Bundle bundle5 = new Bundle(bundle4);
                str = "invisible_actions";
                bundle5.remove(str);
                bundle.putBundle("android.car.EXTENSIONS", bundle5);
            } else {
                str = "invisible_actions";
            }
            if (notificationCompat$Style != null) {
                notificationCompat$Style.clearCompatExtraKeys(bundle);
            }
            notificationCompat$Builder = this;
        }
        notificationCompat$Builder.addExtras(bundle);
        notificationCompat$Builder.mSmallIcon = Api23Impl.getSmallIcon(notification2);
        Icon largeIcon = Api23Impl.getLargeIcon(notification2);
        if (largeIcon != null) {
            PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
            notificationCompat$Builder.mLargeIcon = IconCompat.Api23Impl.createFromIconInner(largeIcon);
        }
        Notification.Action[] actionArr = notification2.actions;
        if (actionArr != null && actionArr.length != 0) {
            int length = actionArr.length;
            int i12 = 0;
            while (i12 < length) {
                Notification.Action action = actionArr[i12];
                if (NotificationCompat$Action.Builder.Api23Impl.getIcon(action) != null) {
                    Icon icon = NotificationCompat$Action.Builder.Api23Impl.getIcon(action);
                    PorterDuff.Mode mode2 = IconCompat.DEFAULT_TINT_MODE;
                    builder = new NotificationCompat$Action.Builder((icon.getType() == 2 && icon.getResId() == 0) ? null : IconCompat.Api23Impl.createFromIconInner(icon), action.title, action.actionIntent);
                } else {
                    builder = new NotificationCompat$Action.Builder(action.icon, action.title, action.actionIntent);
                }
                android.app.RemoteInput[] remoteInputs = NotificationCompat$Action.Builder.Api20Impl.getRemoteInputs(action);
                if (remoteInputs != null && remoteInputs.length != 0) {
                    int length2 = remoteInputs.length;
                    int i13 = 0;
                    while (i13 < length2) {
                        android.app.RemoteInput remoteInput = remoteInputs[i13];
                        Notification.Action[] actionArr2 = actionArr;
                        int i14 = length;
                        RemoteInput.Builder builder2 = new RemoteInput.Builder(remoteInput.getResultKey());
                        builder2.mLabel = remoteInput.getLabel();
                        builder2.mChoices = remoteInput.getChoices();
                        builder2.mAllowFreeFormTextInput = remoteInput.getAllowFreeFormInput();
                        Bundle extras = remoteInput.getExtras();
                        if (extras != null) {
                            i = i12;
                            builder2.mExtras.putAll(extras);
                        } else {
                            i = i12;
                        }
                        if (remoteInput.getAllowedDataTypes() != null) {
                            for (Iterator<String> it = r8.iterator(); it.hasNext(); it = it) {
                                ((HashSet) builder2.mAllowedDataTypes).add(it.next());
                            }
                        }
                        RemoteInput remoteInput2 = new RemoteInput(builder2.mResultKey, builder2.mLabel, builder2.mChoices, builder2.mAllowFreeFormTextInput, remoteInput.getEditChoicesBeforeSending(), builder2.mExtras, builder2.mAllowedDataTypes);
                        if (builder.mRemoteInputs == null) {
                            builder.mRemoteInputs = new ArrayList();
                        }
                        builder.mRemoteInputs.add(remoteInput2);
                        i13++;
                        actionArr = actionArr2;
                        length = i14;
                        i12 = i;
                    }
                }
                Notification.Action[] actionArr3 = actionArr;
                int i15 = length;
                int i16 = i12;
                builder.mAllowGeneratedReplies = NotificationCompat$Action.Builder.Api24Impl.getAllowGeneratedReplies(action);
                builder.mSemanticAction = NotificationCompat$Action.Builder.Api28Impl.getSemanticAction(action);
                builder.mIsContextual = NotificationCompat$Action.Builder.Api29Impl.isContextual(action);
                builder.mAuthenticationRequired = NotificationCompat$Action.Builder.Api31Impl.isAuthenticationRequired(action);
                Bundle extras2 = NotificationCompat$Action.Builder.Api20Impl.getExtras(action);
                if (extras2 != null) {
                    builder.mExtras.putAll(extras2);
                }
                notificationCompat$Builder.mActions.add(builder.build());
                i12 = i16 + 1;
                actionArr = actionArr3;
                length = i15;
            }
        }
        ArrayList arrayList = new ArrayList();
        Bundle bundle6 = notification2.extras.getBundle("android.car.EXTENSIONS");
        if (bundle6 != null && (bundle2 = bundle6.getBundle(str)) != null) {
            for (int i17 = 0; i17 < bundle2.size(); i17++) {
                Bundle bundle7 = bundle2.getBundle(Integer.toString(i17));
                Bundle bundle8 = bundle7.getBundle("extras");
                boolean z3 = bundle8 != null ? bundle8.getBoolean("android.support.allowGeneratedReplies", false) : false;
                int i18 = bundle7.getInt("icon");
                CharSequence charSequence = bundle7.getCharSequence(UniversalCredentialUtil.AGENT_TITLE);
                PendingIntent pendingIntent2 = (PendingIntent) bundle7.getParcelable("actionIntent");
                Bundle bundle9 = bundle7.getBundle("extras");
                Parcelable[] parcelableArray = bundle7.getParcelableArray("remoteInputs");
                if (!(parcelableArray instanceof Bundle[]) && parcelableArray != null) {
                    bundleArr = (Bundle[]) Arrays.copyOf(parcelableArray, parcelableArray.length, Bundle[].class);
                    bundle7.putParcelableArray("remoteInputs", bundleArr);
                } else {
                    bundleArr = (Bundle[]) parcelableArray;
                }
                RemoteInput[] remoteInputArrFromBundleArray = NotificationCompatJellybean.fromBundleArray(bundleArr);
                Parcelable[] parcelableArray2 = bundle7.getParcelableArray("dataOnlyRemoteInputs");
                if (!(parcelableArray2 instanceof Bundle[]) && parcelableArray2 != null) {
                    bundleArr2 = (Bundle[]) Arrays.copyOf(parcelableArray2, parcelableArray2.length, Bundle[].class);
                    bundle7.putParcelableArray("dataOnlyRemoteInputs", bundleArr2);
                } else {
                    bundleArr2 = (Bundle[]) parcelableArray2;
                }
                arrayList.add(new NotificationCompat$Action(i18, charSequence, pendingIntent2, bundle9, remoteInputArrFromBundleArray, NotificationCompatJellybean.fromBundleArray(bundleArr2), z3, bundle7.getInt("semanticAction"), bundle7.getBoolean("showsUserInterface"), false, false));
            }
        }
        int i19 = 0;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            int i20 = 0;
            while (i20 < size) {
                Object obj = arrayList.get(i20);
                i20++;
                NotificationCompat$Action notificationCompat$Action = (NotificationCompat$Action) obj;
                if (notificationCompat$Action != null) {
                    notificationCompat$Builder.mInvisibleActions.add(notificationCompat$Action);
                }
            }
        }
        String[] stringArray = notification2.extras.getStringArray("android.people");
        if (stringArray != null && stringArray.length != 0) {
            for (String str2 : stringArray) {
                if (str2 != null && !str2.isEmpty()) {
                    notificationCompat$Builder.mPeople.add(str2);
                }
            }
        }
        ArrayList parcelableArrayList = notification2.extras.getParcelableArrayList("android.people.list");
        if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
            int size2 = parcelableArrayList.size();
            while (i19 < size2) {
                Object obj2 = parcelableArrayList.get(i19);
                i19++;
                notificationCompat$Builder.mPersonList.add(Person.fromAndroidPerson((android.app.Person) obj2));
            }
        }
        if (bundle3.containsKey("android.chronometerCountDown")) {
            boolean z4 = bundle3.getBoolean("android.chronometerCountDown");
            if (notificationCompat$Builder.mExtras == null) {
                notificationCompat$Builder.mExtras = new Bundle();
            }
            notificationCompat$Builder.mExtras.putBoolean("android.chronometerCountDown", z4);
        }
        if (bundle3.containsKey("android.colorized")) {
            notificationCompat$Builder.mColorized = bundle3.getBoolean("android.colorized");
            notificationCompat$Builder.mColorizedSet = true;
        }
    }
}
