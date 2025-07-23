package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.os.Bundle;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return charSequence == null ? charSequence : charSequence.length() > 5120 ? charSequence.subSequence(0, 5120) : charSequence;
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
        Notification build = notificationCompatBuilder.mBuilder.build();
        if (notificationCompat$Style != null) {
            notificationCompat$Builder.mStyle.getClass();
        }
        if (notificationCompat$Style != null && (bundle = build.extras) != null) {
            notificationCompat$Style.addCompatExtras(bundle);
        }
        return build;
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x0577  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x059d  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x05c3 A[LOOP:5: B:132:0x05c1->B:133:0x05c3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x05dd  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x05f7  */
    /* JADX WARN: Removed duplicated region for block: B:146:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x04da  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x015b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x01a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NotificationCompat$Builder(android.content.Context r33, android.app.Notification r34) {
        /*
            Method dump skipped, instructions count: 1588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat$Builder.<init>(android.content.Context, android.app.Notification):void");
    }
}
