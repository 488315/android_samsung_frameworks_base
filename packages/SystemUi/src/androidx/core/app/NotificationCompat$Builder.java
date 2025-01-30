package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.graphics.Bitmap;
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
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationCompat$Builder {
    public final ArrayList mActions;
    public boolean mAllowSystemGeneratedContextualActions;
    public int mBadgeIcon;
    public NotificationCompat$BubbleMetadata mBubbleMetadata;
    public String mCategory;
    public final String mChannelId;
    public int mColor;
    public boolean mColorized;
    public boolean mColorizedSet;
    public CharSequence mContentInfo;
    public PendingIntent mContentIntent;
    public CharSequence mContentText;
    public CharSequence mContentTitle;
    public final Context mContext;
    public Bundle mExtras;
    public PendingIntent mFullScreenIntent;
    public String mGroupKey;
    public boolean mGroupSummary;
    public final ArrayList mInvisibleActions;
    public Bitmap mLargeIcon;
    public boolean mLocalOnly;
    public LocusIdCompat mLocusId;
    public final Notification mNotification;
    public int mNumber;
    public final ArrayList mPeople;
    public final ArrayList mPersonList;
    public int mPriority;
    public int mProgress;
    public boolean mProgressIndeterminate;
    public int mProgressMax;
    public Notification mPublicVersion;
    public CharSequence mSettingsText;
    public String mShortcutId;
    public boolean mShowWhen;
    public final Icon mSmallIcon;
    public String mSortKey;
    public NotificationCompat$Style mStyle;
    public CharSequence mSubText;
    public long mTimeout;
    public boolean mUseChronometer;
    public int mVisibility;

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
        return charSequence == null ? charSequence : charSequence.length() > 5120 ? charSequence.subSequence(0, 5120) : charSequence;
    }

    public final void setFlag(int i, boolean z) {
        Notification notification2 = this.mNotification;
        if (z) {
            notification2.flags = i | notification2.flags;
        } else {
            notification2.flags = (~i) & notification2.flags;
        }
    }

    public final void setStyle(NotificationCompat$Style notificationCompat$Style) {
        if (this.mStyle != notificationCompat$Style) {
            this.mStyle = notificationCompat$Style;
            if (notificationCompat$Style != null) {
                notificationCompat$Style.setBuilder(this);
            }
        }
    }

    @Deprecated
    public NotificationCompat$Builder(Context context) {
        this(context, (String) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0557  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x05f9  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0620  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x064c A[LOOP:7: B:245:0x0646->B:247:0x064c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0666  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0682  */
    /* JADX WARN: Removed duplicated region for block: B:259:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x02f0  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0139 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x028c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NotificationCompat$Builder(Context context, Notification notification2) {
        this(context, notification2.getChannelId());
        NotificationCompat$Style notificationCompat$Style;
        LocusId locusId;
        LocusIdCompat locusIdCompat;
        int i;
        Bundle bundle;
        String str;
        Notification.Action[] actionArr;
        String str2;
        String str3;
        ArrayList arrayList;
        Bundle bundle2;
        String[] stringArray;
        ArrayList parcelableArrayList;
        Iterator it;
        Bundle bundle3;
        int i2;
        Bundle[] bundleArr;
        Bundle[] bundleArr2;
        int length;
        int i3;
        NotificationCompat$Action.Builder builder;
        CharSequence[] charSequenceArr;
        Set set;
        Bundle bundle4 = notification2.extras;
        if (bundle4 != null) {
            String string = bundle4.getString("androidx.core.app.extra.COMPAT_TEMPLATE");
            if (string != null) {
                switch (string) {
                    case "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle":
                        notificationCompat$Style = new NotificationCompat$Style() { // from class: androidx.core.app.NotificationCompat$DecoratedCustomViewStyle
                            @Override // androidx.core.app.NotificationCompat$Style
                            public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
                                notificationCompatBuilder.mBuilder.setStyle(new Notification.DecoratedCustomViewStyle());
                            }

                            @Override // androidx.core.app.NotificationCompat$Style
                            public final String getClassName() {
                                return "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";
                            }

                            @Override // androidx.core.app.NotificationCompat$Style
                            public final void makeBigContentView() {
                            }

                            @Override // androidx.core.app.NotificationCompat$Style
                            public final void makeContentView() {
                            }

                            @Override // androidx.core.app.NotificationCompat$Style
                            public final void makeHeadsUpContentView() {
                            }
                        };
                        break;
                    case "androidx.core.app.NotificationCompat$BigPictureStyle":
                        notificationCompat$Style = new NotificationCompat$BigPictureStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$InboxStyle":
                        notificationCompat$Style = new NotificationCompat$InboxStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$BigTextStyle":
                        notificationCompat$Style = new NotificationCompat$BigTextStyle();
                        break;
                    case "androidx.core.app.NotificationCompat$MessagingStyle":
                        notificationCompat$Style = new NotificationCompat$MessagingStyle();
                        break;
                }
                if (notificationCompat$Style == null) {
                    if (!bundle4.containsKey("android.selfDisplayName") && !bundle4.containsKey("android.messagingStyleUser")) {
                        if (!bundle4.containsKey("android.picture") && !bundle4.containsKey("android.pictureIcon")) {
                            if (bundle4.containsKey("android.bigText")) {
                                notificationCompat$Style = new NotificationCompat$BigTextStyle();
                            } else if (bundle4.containsKey("android.textLines")) {
                                notificationCompat$Style = new NotificationCompat$InboxStyle();
                            } else {
                                String string2 = bundle4.getString("android.template");
                                if (string2 != null) {
                                    if (string2.equals(Notification.BigPictureStyle.class.getName())) {
                                        notificationCompat$Style = new NotificationCompat$BigPictureStyle();
                                    } else if (string2.equals(Notification.BigTextStyle.class.getName())) {
                                        notificationCompat$Style = new NotificationCompat$BigTextStyle();
                                    } else if (string2.equals(Notification.InboxStyle.class.getName())) {
                                        notificationCompat$Style = new NotificationCompat$InboxStyle();
                                    } else if (string2.equals(Notification.MessagingStyle.class.getName())) {
                                        notificationCompat$Style = new NotificationCompat$MessagingStyle();
                                    } else if (string2.equals(Notification.DecoratedCustomViewStyle.class.getName())) {
                                        notificationCompat$Style = new NotificationCompat$Style() { // from class: androidx.core.app.NotificationCompat$DecoratedCustomViewStyle
                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
                                                notificationCompatBuilder.mBuilder.setStyle(new Notification.DecoratedCustomViewStyle());
                                            }

                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final String getClassName() {
                                                return "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";
                                            }

                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final void makeBigContentView() {
                                            }

                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final void makeContentView() {
                                            }

                                            @Override // androidx.core.app.NotificationCompat$Style
                                            public final void makeHeadsUpContentView() {
                                            }
                                        };
                                    }
                                }
                                notificationCompat$Style = null;
                            }
                        } else {
                            notificationCompat$Style = new NotificationCompat$BigPictureStyle();
                        }
                    } else {
                        notificationCompat$Style = new NotificationCompat$MessagingStyle();
                    }
                }
                if (notificationCompat$Style != null) {
                    try {
                        notificationCompat$Style.restoreFromCompatExtras(bundle4);
                    } catch (ClassCastException unused) {
                    }
                    this.mContentTitle = limitCharSequenceLength(notification2.extras.getCharSequence("android.title"));
                    this.mContentText = limitCharSequenceLength(notification2.extras.getCharSequence("android.text"));
                    this.mContentInfo = limitCharSequenceLength(notification2.extras.getCharSequence("android.infoText"));
                    this.mSubText = limitCharSequenceLength(notification2.extras.getCharSequence("android.subText"));
                    this.mSettingsText = limitCharSequenceLength(notification2.getSettingsText());
                    if (this.mStyle != notificationCompat$Style) {
                        this.mStyle = notificationCompat$Style;
                        if (notificationCompat$Style != null) {
                            notificationCompat$Style.setBuilder(this);
                        }
                    }
                    this.mContentIntent = notification2.contentIntent;
                    this.mGroupKey = notification2.getGroup();
                    this.mGroupSummary = (notification2.flags & 512) == 0;
                    locusId = notification2.getLocusId();
                    if (locusId != null) {
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
                    setFlag(16, (notification2.flags & 16) == 0);
                    setFlag(8, (notification2.flags & 8) == 0);
                    setFlag(2, (notification2.flags & 2) == 0);
                    this.mLocalOnly = (notification2.flags & 256) == 0;
                    this.mLargeIcon = notification2.largeIcon;
                    this.mBadgeIcon = notification2.getBadgeIconType();
                    this.mCategory = notification2.category;
                    Notification.BubbleMetadata bubbleMetadata = notification2.getBubbleMetadata();
                    this.mBubbleMetadata = bubbleMetadata != null ? null : NotificationCompat$BubbleMetadata.Api30Impl.fromPlatform(bubbleMetadata);
                    this.mNumber = notification2.number;
                    this.mNotification.tickerText = limitCharSequenceLength(notification2.tickerText);
                    this.mContentIntent = notification2.contentIntent;
                    this.mNotification.deleteIntent = notification2.deleteIntent;
                    PendingIntent pendingIntent = notification2.fullScreenIntent;
                    boolean z = (notification2.flags & 128) == 0;
                    this.mFullScreenIntent = pendingIntent;
                    setFlag(128, z);
                    Uri uri = notification2.sound;
                    int i4 = notification2.audioStreamType;
                    Notification notification3 = this.mNotification;
                    notification3.sound = uri;
                    notification3.audioStreamType = i4;
                    notification3.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(i4).build();
                    long[] jArr = notification2.vibrate;
                    Notification notification4 = this.mNotification;
                    notification4.vibrate = jArr;
                    int i5 = notification2.ledARGB;
                    int i6 = notification2.ledOnMS;
                    int i7 = notification2.ledOffMS;
                    notification4.ledARGB = i5;
                    notification4.ledOnMS = i6;
                    notification4.ledOffMS = i7;
                    int i8 = ((i6 != 0 || i7 == 0) ? 0 : 1) | (notification4.flags & (-2));
                    notification4.flags = i8;
                    i = notification2.defaults;
                    notification4.defaults = i;
                    if ((4 & i) != 0) {
                        notification4.flags = i8 | 1;
                    }
                    this.mPriority = notification2.priority;
                    this.mColor = notification2.color;
                    this.mVisibility = notification2.visibility;
                    this.mPublicVersion = notification2.publicVersion;
                    this.mSortKey = notification2.getSortKey();
                    this.mTimeout = notification2.getTimeoutAfter();
                    this.mShortcutId = notification2.getShortcutId();
                    int i9 = bundle4.getInt("android.progressMax");
                    int i10 = bundle4.getInt("android.progress");
                    boolean z2 = bundle4.getBoolean("android.progressIndeterminate");
                    this.mProgressMax = i9;
                    this.mProgress = i10;
                    this.mProgressIndeterminate = z2;
                    this.mAllowSystemGeneratedContextualActions = notification2.getAllowSystemGeneratedContextualActions();
                    int i11 = notification2.icon;
                    int i12 = notification2.iconLevel;
                    Notification notification5 = this.mNotification;
                    notification5.icon = i11;
                    notification5.iconLevel = i12;
                    String str4 = "android.people.list";
                    NotificationCompat$Style notificationCompat$Style2 = notificationCompat$Style;
                    String str5 = "android.chronometerCountDown";
                    if (notification2.extras != null) {
                        str = "invisible_actions";
                        bundle = null;
                    } else {
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
                        Bundle bundle5 = bundle.getBundle("android.car.EXTENSIONS");
                        if (bundle5 != null) {
                            Bundle bundle6 = new Bundle(bundle5);
                            str = "invisible_actions";
                            bundle6.remove(str);
                            bundle.putBundle("android.car.EXTENSIONS", bundle6);
                        } else {
                            str = "invisible_actions";
                        }
                        if (notificationCompat$Style2 != null) {
                            notificationCompat$Style2.clearCompatExtraKeys(bundle);
                        }
                    }
                    String str6 = "android.colorized";
                    if (bundle != null) {
                        Bundle bundle7 = this.mExtras;
                        if (bundle7 == null) {
                            this.mExtras = new Bundle(bundle);
                        } else {
                            bundle7.putAll(bundle);
                        }
                    }
                    this.mSmallIcon = notification2.getSmallIcon();
                    actionArr = notification2.actions;
                    if (actionArr != null && actionArr.length != 0) {
                        length = actionArr.length;
                        i3 = 0;
                        while (i3 < length) {
                            Notification.Action action = actionArr[i3];
                            if (action.getIcon() != null) {
                                builder = new NotificationCompat$Action.Builder(IconCompat.createFromIcon(action.getIcon()), action.title, action.actionIntent);
                            } else {
                                builder = new NotificationCompat$Action.Builder(action.icon, action.title, action.actionIntent);
                            }
                            android.app.RemoteInput[] remoteInputs = action.getRemoteInputs();
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
                                    android.app.RemoteInput[] remoteInputArr = remoteInputs;
                                    if (extras != null) {
                                        builder2.mExtras.putAll(extras);
                                    }
                                    Set<String> allowedDataTypes = remoteInput.getAllowedDataTypes();
                                    Set set2 = builder2.mAllowedDataTypes;
                                    if (allowedDataTypes != null) {
                                        Iterator<String> it2 = allowedDataTypes.iterator();
                                        while (it2.hasNext()) {
                                            ((HashSet) set2).add(it2.next());
                                            it2 = it2;
                                            length2 = length2;
                                        }
                                    }
                                    int i15 = length2;
                                    int editChoicesBeforeSending = remoteInput.getEditChoicesBeforeSending();
                                    String str7 = str6;
                                    String str8 = str5;
                                    String str9 = str4;
                                    RemoteInput remoteInput2 = new RemoteInput(builder2.mResultKey, builder2.mLabel, builder2.mChoices, builder2.mAllowFreeFormTextInput, editChoicesBeforeSending, builder2.mExtras, set2);
                                    if (builder.mRemoteInputs == null) {
                                        builder.mRemoteInputs = new ArrayList();
                                    }
                                    builder.mRemoteInputs.add(remoteInput2);
                                    i13++;
                                    str6 = str7;
                                    actionArr = actionArr2;
                                    length = i14;
                                    remoteInputs = remoteInputArr;
                                    length2 = i15;
                                    str5 = str8;
                                    str4 = str9;
                                }
                            }
                            String str10 = str4;
                            Notification.Action[] actionArr3 = actionArr;
                            String str11 = str6;
                            int i16 = length;
                            String str12 = str5;
                            builder.mAllowGeneratedReplies = action.getAllowGeneratedReplies();
                            builder.mSemanticAction = action.getSemanticAction();
                            builder.mIsContextual = action.isContextual();
                            builder.mAuthenticationRequired = action.isAuthenticationRequired();
                            if (!builder.mIsContextual || builder.mIntent != null) {
                                ArrayList arrayList2 = new ArrayList();
                                ArrayList arrayList3 = new ArrayList();
                                ArrayList arrayList4 = builder.mRemoteInputs;
                                if (arrayList4 != null) {
                                    Iterator it3 = arrayList4.iterator();
                                    while (it3.hasNext()) {
                                        RemoteInput remoteInput3 = (RemoteInput) it3.next();
                                        if ((remoteInput3.mAllowFreeFormTextInput || ((charSequenceArr = remoteInput3.mChoices) != null && charSequenceArr.length != 0) || (set = remoteInput3.mAllowedDataTypes) == null || set.isEmpty()) ? false : true) {
                                            arrayList2.add(remoteInput3);
                                        } else {
                                            arrayList3.add(remoteInput3);
                                        }
                                    }
                                }
                                this.mActions.add(new NotificationCompat$Action(builder.mIcon, builder.mTitle, builder.mIntent, builder.mExtras, arrayList3.isEmpty() ? null : (RemoteInput[]) arrayList3.toArray(new RemoteInput[arrayList3.size()]), arrayList2.isEmpty() ? null : (RemoteInput[]) arrayList2.toArray(new RemoteInput[arrayList2.size()]), builder.mAllowGeneratedReplies, builder.mSemanticAction, builder.mShowsUserInterface, builder.mIsContextual, builder.mAuthenticationRequired));
                                i3++;
                                str6 = str11;
                                actionArr = actionArr3;
                                length = i16;
                                str5 = str12;
                                str4 = str10;
                            } else {
                                throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
                            }
                        }
                    }
                    String str13 = str4;
                    str2 = str6;
                    str3 = str5;
                    arrayList = new ArrayList();
                    bundle2 = notification2.extras.getBundle("android.car.EXTENSIONS");
                    if (bundle2 != null && (bundle3 = bundle2.getBundle(str)) != null) {
                        for (i2 = 0; i2 < bundle3.size(); i2++) {
                            Bundle bundle8 = bundle3.getBundle(Integer.toString(i2));
                            Object obj = NotificationCompatJellybean.sExtrasLock;
                            Bundle bundle9 = bundle8.getBundle("extras");
                            boolean z3 = bundle9 != null ? bundle9.getBoolean("android.support.allowGeneratedReplies", false) : false;
                            int i17 = bundle8.getInt("icon");
                            CharSequence charSequence = bundle8.getCharSequence(UniversalCredentialUtil.AGENT_TITLE);
                            PendingIntent pendingIntent2 = (PendingIntent) bundle8.getParcelable("actionIntent");
                            Bundle bundle10 = bundle8.getBundle("extras");
                            Parcelable[] parcelableArray = bundle8.getParcelableArray("remoteInputs");
                            if (!(parcelableArray instanceof Bundle[]) && parcelableArray != null) {
                                bundleArr = (Bundle[]) Arrays.copyOf(parcelableArray, parcelableArray.length, Bundle[].class);
                                bundle8.putParcelableArray("remoteInputs", bundleArr);
                            } else {
                                bundleArr = (Bundle[]) parcelableArray;
                            }
                            RemoteInput[] fromBundleArray = NotificationCompatJellybean.fromBundleArray(bundleArr);
                            Parcelable[] parcelableArray2 = bundle8.getParcelableArray("dataOnlyRemoteInputs");
                            if (!(parcelableArray2 instanceof Bundle[]) && parcelableArray2 != null) {
                                bundleArr2 = (Bundle[]) Arrays.copyOf(parcelableArray2, parcelableArray2.length, Bundle[].class);
                                bundle8.putParcelableArray("dataOnlyRemoteInputs", bundleArr2);
                            } else {
                                bundleArr2 = (Bundle[]) parcelableArray2;
                            }
                            arrayList.add(new NotificationCompat$Action(i17, charSequence, pendingIntent2, bundle10, fromBundleArray, NotificationCompatJellybean.fromBundleArray(bundleArr2), z3, bundle8.getInt("semanticAction"), bundle8.getBoolean("showsUserInterface"), false, false));
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            NotificationCompat$Action notificationCompat$Action = (NotificationCompat$Action) it4.next();
                            if (notificationCompat$Action != null) {
                                this.mInvisibleActions.add(notificationCompat$Action);
                            }
                        }
                    }
                    stringArray = notification2.extras.getStringArray("android.people");
                    if (stringArray != null && stringArray.length != 0) {
                        for (String str14 : stringArray) {
                            if (str14 != null && !str14.isEmpty()) {
                                this.mPeople.add(str14);
                            }
                        }
                    }
                    parcelableArrayList = notification2.extras.getParcelableArrayList(str13);
                    if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                        it = parcelableArrayList.iterator();
                        while (it.hasNext()) {
                            this.mPersonList.add(Person.fromAndroidPerson((android.app.Person) it.next()));
                        }
                    }
                    if (bundle4.containsKey(str3)) {
                        boolean z4 = bundle4.getBoolean(str3);
                        if (this.mExtras == null) {
                            this.mExtras = new Bundle();
                        }
                        this.mExtras.putBoolean(str3, z4);
                    }
                    if (bundle4.containsKey(str2)) {
                        return;
                    }
                    this.mColorized = bundle4.getBoolean(str2);
                    this.mColorizedSet = true;
                    return;
                }
            }
            notificationCompat$Style = null;
            if (notificationCompat$Style == null) {
            }
            if (notificationCompat$Style != null) {
            }
        }
        notificationCompat$Style = null;
        this.mContentTitle = limitCharSequenceLength(notification2.extras.getCharSequence("android.title"));
        this.mContentText = limitCharSequenceLength(notification2.extras.getCharSequence("android.text"));
        this.mContentInfo = limitCharSequenceLength(notification2.extras.getCharSequence("android.infoText"));
        this.mSubText = limitCharSequenceLength(notification2.extras.getCharSequence("android.subText"));
        this.mSettingsText = limitCharSequenceLength(notification2.getSettingsText());
        if (this.mStyle != notificationCompat$Style) {
        }
        this.mContentIntent = notification2.contentIntent;
        this.mGroupKey = notification2.getGroup();
        this.mGroupSummary = (notification2.flags & 512) == 0;
        locusId = notification2.getLocusId();
        if (locusId != null) {
        }
        this.mLocusId = locusIdCompat;
        this.mNotification.when = notification2.when;
        this.mShowWhen = notification2.extras.getBoolean("android.showWhen");
        this.mUseChronometer = notification2.extras.getBoolean("android.showChronometer");
        setFlag(16, (notification2.flags & 16) == 0);
        setFlag(8, (notification2.flags & 8) == 0);
        setFlag(2, (notification2.flags & 2) == 0);
        this.mLocalOnly = (notification2.flags & 256) == 0;
        this.mLargeIcon = notification2.largeIcon;
        this.mBadgeIcon = notification2.getBadgeIconType();
        this.mCategory = notification2.category;
        Notification.BubbleMetadata bubbleMetadata2 = notification2.getBubbleMetadata();
        this.mBubbleMetadata = bubbleMetadata2 != null ? null : NotificationCompat$BubbleMetadata.Api30Impl.fromPlatform(bubbleMetadata2);
        this.mNumber = notification2.number;
        this.mNotification.tickerText = limitCharSequenceLength(notification2.tickerText);
        this.mContentIntent = notification2.contentIntent;
        this.mNotification.deleteIntent = notification2.deleteIntent;
        PendingIntent pendingIntent3 = notification2.fullScreenIntent;
        if ((notification2.flags & 128) == 0) {
        }
        this.mFullScreenIntent = pendingIntent3;
        setFlag(128, z);
        Uri uri2 = notification2.sound;
        int i42 = notification2.audioStreamType;
        Notification notification32 = this.mNotification;
        notification32.sound = uri2;
        notification32.audioStreamType = i42;
        notification32.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(i42).build();
        long[] jArr2 = notification2.vibrate;
        Notification notification42 = this.mNotification;
        notification42.vibrate = jArr2;
        int i52 = notification2.ledARGB;
        int i62 = notification2.ledOnMS;
        int i72 = notification2.ledOffMS;
        notification42.ledARGB = i52;
        notification42.ledOnMS = i62;
        notification42.ledOffMS = i72;
        int i82 = ((i62 != 0 || i72 == 0) ? 0 : 1) | (notification42.flags & (-2));
        notification42.flags = i82;
        i = notification2.defaults;
        notification42.defaults = i;
        if ((4 & i) != 0) {
        }
        this.mPriority = notification2.priority;
        this.mColor = notification2.color;
        this.mVisibility = notification2.visibility;
        this.mPublicVersion = notification2.publicVersion;
        this.mSortKey = notification2.getSortKey();
        this.mTimeout = notification2.getTimeoutAfter();
        this.mShortcutId = notification2.getShortcutId();
        int i92 = bundle4.getInt("android.progressMax");
        int i102 = bundle4.getInt("android.progress");
        boolean z22 = bundle4.getBoolean("android.progressIndeterminate");
        this.mProgressMax = i92;
        this.mProgress = i102;
        this.mProgressIndeterminate = z22;
        this.mAllowSystemGeneratedContextualActions = notification2.getAllowSystemGeneratedContextualActions();
        int i112 = notification2.icon;
        int i122 = notification2.iconLevel;
        Notification notification52 = this.mNotification;
        notification52.icon = i112;
        notification52.iconLevel = i122;
        String str42 = "android.people.list";
        NotificationCompat$Style notificationCompat$Style22 = notificationCompat$Style;
        String str52 = "android.chronometerCountDown";
        if (notification2.extras != null) {
        }
        String str62 = "android.colorized";
        if (bundle != null) {
        }
        this.mSmallIcon = notification2.getSmallIcon();
        actionArr = notification2.actions;
        if (actionArr != null) {
            length = actionArr.length;
            i3 = 0;
            while (i3 < length) {
            }
        }
        String str132 = str42;
        str2 = str62;
        str3 = str52;
        arrayList = new ArrayList();
        bundle2 = notification2.extras.getBundle("android.car.EXTENSIONS");
        if (bundle2 != null) {
            while (i2 < bundle3.size()) {
            }
        }
        if (!arrayList.isEmpty()) {
        }
        stringArray = notification2.extras.getStringArray("android.people");
        if (stringArray != null) {
            while (r4 < r3) {
            }
        }
        parcelableArrayList = notification2.extras.getParcelableArrayList(str132);
        if (parcelableArrayList != null) {
            it = parcelableArrayList.iterator();
            while (it.hasNext()) {
            }
        }
        if (bundle4.containsKey(str3)) {
        }
        if (bundle4.containsKey(str2)) {
        }
    }
}
