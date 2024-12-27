package com.android.server.notification;

import android.R;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Person;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.media.AudioAttributes;
import android.metrics.LogMaker;
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.Adjustment;
import android.service.notification.NotificationListenerService;
import android.service.notification.NotificationStats;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.widget.RemoteViews;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.EventLogTags;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;

import dalvik.annotation.optimization.NeverCompile;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NotificationRecord {
    public boolean isCanceled;
    public boolean isUpdate;
    public String mAdjustmentIssuer;
    public final List mAdjustments;
    public boolean mAllowBubble;
    public boolean mAppDemotedFromConvo;
    public AudioAttributes mAttributes;
    public int mAuthoritativeRank;
    public NotificationChannel mChannel;
    public float mContactAffinity;
    public final Context mContext;
    public long mCreationTimeMs;
    public boolean mEditChoicesBeforeSending;
    public boolean mFlagBubbleRemoved;
    public String mGlobalSortKey;
    public ArraySet mGrantableUris;
    public boolean mHasSeenSmartReplies;
    public boolean mHasSentValidMsg;
    public boolean mHidden;
    public int mImportance;
    public boolean mImportanceFixed;
    public boolean mIntercept;
    public boolean mInterceptSet;
    public long mInterruptionTimeMs;
    public boolean mIsAlertLimited;
    public boolean mIsAppImportanceLocked;
    public boolean mIsInterruptive;
    public boolean mIsNotConversationOverride;
    public KeyguardManager mKeyguardManager;
    public long mLastAudiblyAlertedMs;
    public long mLastIntrusive;
    public final Light mLight;
    public int mNumberOfSmartActionsAdded;
    public int mNumberOfSmartRepliesAdded;
    public final int mOriginalFlags;
    public int mPackagePriority;
    public int mPackageVisibility;
    public ArrayList mPeopleOverride;
    public ArraySet mPhoneNumbers;
    public boolean mPkgAllowedAsConvo;
    public boolean mPostSilently;
    public final PowerManager mPowerManager;
    public final boolean mPreChannelsNotification;
    public long mRankingTimeMs;
    public boolean mRecentlyIntrusive;
    public boolean mRecordedInterruption;
    public ShortcutInfo mShortcutInfo;
    public boolean mShowBadge;
    public ArrayList mSmartReplies;
    public ArrayList mSnoozeCriteria;
    public Uri mSound;
    public final NotificationStats mStats;
    public boolean mSuggestionsGeneratedByAssistant;
    public ArrayList mSystemGeneratedSmartActions;
    public final int mTargetSdkVersion;
    public boolean mTextChanged;
    public final UriGrantsManagerInternal mUgmInternal;
    final long mUpdateTimeMs;
    public int mUserSentiment;
    public VibrationEffect mVibration;
    public long mVisibleSinceMs;
    public IBinder permissionOwner;
    public final StatusBarNotification sbn;
    public final NotificationUsageStats.SingleNotificationStats stats;
    public int mSystemImportance = -1000;
    public int mAssistantImportance = -1000;
    public float mRankingScore = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public int mCriticality = 2;
    public int mImportanceExplanationCode = 0;
    public int mInitialImportanceExplanationCode = 0;
    public int mSuppressedVisualEffects = 0;
    public boolean mPendingLogUpdate = false;
    public int mProposedImportance = -1000;
    public boolean mSensitiveContent = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class Light {
        public final int color;
        public final int offMs;
        public final int onMs;

        public Light(int i, int i2, int i3) {
            this.color = i;
            this.onMs = i2;
            this.offMs = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || Light.class != obj.getClass()) {
                return false;
            }
            Light light = (Light) obj;
            return this.color == light.color
                    && this.onMs == light.onMs
                    && this.offMs == light.offMs;
        }

        public final int hashCode() {
            return (((this.color * 31) + this.onMs) * 31) + this.offMs;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Light{color=");
            sb.append(this.color);
            sb.append(", onMs=");
            sb.append(this.onMs);
            sb.append(", offMs=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.offMs, '}');
        }
    }

    static {
        Log.isLoggable("NotificationRecord", 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0222 A[Catch: all -> 0x024b, TRY_ENTER, TryCatch #1 {all -> 0x024b, blocks: (B:47:0x0216, B:54:0x0222, B:56:0x0234, B:58:0x0238, B:61:0x0247), top: B:46:0x0216 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NotificationRecord(
            android.content.Context r11,
            android.service.notification.StatusBarNotification r12,
            android.app.NotificationChannel r13) {
        /*
            Method dump skipped, instructions count: 596
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.NotificationRecord.<init>(android.content.Context,"
                    + " android.service.notification.StatusBarNotification,"
                    + " android.app.NotificationChannel):void");
    }

    public static void dumpNotification(
            PrintWriter printWriter, String str, Notification notification, boolean z) {
        if (notification == null) {
            printWriter.println(str + KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
            return;
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "fullscreenIntent=");
        m.append(notification.fullScreenIntent);
        printWriter.println(m.toString());
        printWriter.println(str + "contentIntent=" + notification.contentIntent);
        printWriter.println(str + "deleteIntent=" + notification.deleteIntent);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("number=");
        StringBuilder m2 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        sb, notification.number, printWriter, str, "groupAlertBehavior=");
        m2.append(notification.getGroupAlertBehavior());
        printWriter.println(m2.toString());
        printWriter.println(str + "when=" + notification.when + "/" + notification.getWhen());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("tickerText=");
        printWriter.print(sb2.toString());
        if (TextUtils.isEmpty(notification.tickerText)) {
            printWriter.println("null");
        } else {
            String charSequence = notification.tickerText.toString();
            if (z) {
                printWriter.print(charSequence.length() > 16 ? charSequence.substring(0, 8) : "");
                printWriter.println("...");
            } else {
                printWriter.println(charSequence);
            }
        }
        StringBuilder m3 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        Preconditions$$ExternalSyntheticOutline0.m(str, "vis="),
                        notification.visibility,
                        printWriter,
                        str,
                        "contentView=");
        m3.append(formatRemoteViews(notification.contentView));
        printWriter.println(m3.toString());
        printWriter.println(
                str + "bigContentView=" + formatRemoteViews(notification.bigContentView));
        printWriter.println(
                str + "headsUpContentView=" + formatRemoteViews(notification.headsUpContentView));
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(String.format("color=0x%08x", Integer.valueOf(notification.color)));
        printWriter.println(sb3.toString());
        printWriter.println(str + "timeout=" + Duration.ofMillis(notification.getTimeoutAfter()));
        notification.writeToParcel(Parcel.obtain(), 0);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append("parcelDataSize=");
        StringBuilder m4 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        sb4,
                        notification.parcelDataSize,
                        printWriter,
                        str,
                        "mAllowSystemGeneratedContextualActions=");
        m4.append(notification.getAllowSystemGeneratedContextualActions());
        printWriter.println(m4.toString());
        Notification.Action[] actionArr = notification.actions;
        if (actionArr != null && actionArr.length > 0) {
            printWriter.println(str + "actions={");
            int length = notification.actions.length;
            for (int i = 0; i < length; i++) {
                Notification.Action action = notification.actions[i];
                if (action != null) {
                    Integer valueOf = Integer.valueOf(i);
                    CharSequence charSequence2 = action.title;
                    PendingIntent pendingIntent = action.actionIntent;
                    printWriter.println(
                            String.format(
                                    "%s    [%d] \"%s\" -> %s",
                                    str,
                                    valueOf,
                                    charSequence2,
                                    pendingIntent == null ? "null" : pendingIntent.toString()));
                }
            }
            printWriter.println(str + "  }");
        }
        Bundle bundle = notification.extras;
        if (bundle == null || bundle.size() <= 0) {
            return;
        }
        printWriter.println(str + "extras={");
        for (String str2 : notification.extras.keySet()) {
            printWriter.print(str + "    " + str2 + "=");
            Object obj = notification.extras.get(str2);
            if (obj == null) {
                printWriter.println("null");
            } else {
                printWriter.print(obj.getClass().getSimpleName());
                if (z && (obj instanceof CharSequence)) {
                    if (str2 != null) {
                        switch (str2) {
                        }
                        printWriter.println();
                    }
                    printWriter.print(
                            String.format(
                                    " [length=%d]",
                                    Integer.valueOf(((CharSequence) obj).length())));
                    printWriter.println();
                }
                if (obj instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) obj;
                    printWriter.print(
                            String.format(
                                    " (%dx%d)",
                                    Integer.valueOf(bitmap.getWidth()),
                                    Integer.valueOf(bitmap.getHeight())));
                } else if (obj.getClass().isArray()) {
                    int length2 = Array.getLength(obj);
                    printWriter.print(" (" + length2 + ")");
                    if (!z) {
                        for (int i2 = 0; i2 < length2; i2++) {
                            printWriter.println();
                            printWriter.print(
                                    String.format(
                                            "%s      [%d] %s",
                                            str,
                                            Integer.valueOf(i2),
                                            String.valueOf(Array.get(obj, i2))));
                        }
                    }
                } else {
                    printWriter.print(" (" + String.valueOf(obj) + ")");
                }
                printWriter.println();
            }
        }
        printWriter.println(str + "}");
    }

    public static String formatRemoteViews(RemoteViews remoteViews) {
        return remoteViews == null
                ? "null"
                : String.format(
                        "%s/0x%08x (%d bytes): %s",
                        remoteViews.getPackage(),
                        Integer.valueOf(remoteViews.getLayoutId()),
                        Integer.valueOf(remoteViews.estimateMemoryUsage()),
                        remoteViews.toString());
    }

    public final void applyAdjustments() {
        System.currentTimeMillis();
        synchronized (this.mAdjustments) {
            try {
                Iterator it = ((ArrayList) this.mAdjustments).iterator();
                while (it.hasNext()) {
                    Adjustment adjustment = (Adjustment) it.next();
                    Bundle signals = adjustment.getSignals();
                    if (signals.containsKey("key_people")) {
                        ArrayList<String> stringArrayList =
                                adjustment.getSignals().getStringArrayList("key_people");
                        this.mPeopleOverride = stringArrayList;
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(), "key_people", stringArrayList.toString());
                    }
                    if (signals.containsKey("key_snooze_criteria")) {
                        ArrayList parcelableArrayList =
                                adjustment
                                        .getSignals()
                                        .getParcelableArrayList(
                                                "key_snooze_criteria", SnoozeCriterion.class);
                        this.mSnoozeCriteria = parcelableArrayList;
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_snooze_criteria",
                                parcelableArrayList.toString());
                    }
                    if (signals.containsKey("key_group_key")) {
                        String string = adjustment.getSignals().getString("key_group_key");
                        this.sbn.setOverrideGroupKey(string);
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(), "key_group_key", string);
                    }
                    if (signals.containsKey("key_user_sentiment")
                            && !this.mIsAppImportanceLocked
                            && (this.mChannel.getUserLockedFields() & 4) == 0) {
                        this.mUserSentiment =
                                adjustment.getSignals().getInt("key_user_sentiment", 0);
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_user_sentiment",
                                Integer.toString(this.mUserSentiment));
                    }
                    if (signals.containsKey("key_contextual_actions")) {
                        this.mSystemGeneratedSmartActions =
                                signals.getParcelableArrayList(
                                        "key_contextual_actions", Notification.Action.class);
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_contextual_actions",
                                this.mSystemGeneratedSmartActions.toString());
                    }
                    if (signals.containsKey("key_text_replies")) {
                        this.mSmartReplies = signals.getCharSequenceArrayList("key_text_replies");
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_text_replies",
                                this.mSmartReplies.toString());
                    }
                    if (signals.containsKey("key_importance")) {
                        int min = Math.min(4, Math.max(-1000, signals.getInt("key_importance")));
                        this.mAssistantImportance = min;
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(), "key_importance", Integer.toString(min));
                    }
                    if (signals.containsKey("key_ranking_score")) {
                        this.mRankingScore = signals.getFloat("key_ranking_score");
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_ranking_score",
                                Float.toString(this.mRankingScore));
                    }
                    if (signals.containsKey("key_not_conversation")) {
                        this.mIsNotConversationOverride =
                                signals.getBoolean("key_not_conversation");
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_not_conversation",
                                Boolean.toString(this.mIsNotConversationOverride));
                    }
                    if (signals.containsKey("key_importance_proposal")) {
                        this.mProposedImportance = signals.getInt("key_importance_proposal");
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_importance_proposal",
                                Integer.toString(this.mProposedImportance));
                    }
                    if (signals.containsKey("key_sensitive_content")) {
                        this.mSensitiveContent = signals.getBoolean("key_sensitive_content");
                        EventLogTags.writeNotificationAdjusted(
                                this.sbn.getKey(),
                                "key_sensitive_content",
                                Boolean.toString(this.mSensitiveContent));
                    }
                    if (!signals.isEmpty() && adjustment.getIssuer() != null) {
                        this.mAdjustmentIssuer = adjustment.getIssuer();
                    }
                }
                ((ArrayList) this.mAdjustments).clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void calculateImportance() {
        int i;
        this.mImportance = calculateInitialImportance();
        this.mImportanceExplanationCode = this.mInitialImportanceExplanationCode;
        if (!this.mChannel.hasUserSetImportance()
                && (i = this.mAssistantImportance) != -1000
                && !this.mImportanceFixed) {
            this.mImportance = i;
            this.mImportanceExplanationCode = 3;
        }
        int i2 = this.mSystemImportance;
        if (i2 != -1000) {
            this.mImportance = i2;
            this.mImportanceExplanationCode = 4;
        }
    }

    public final int calculateInitialImportance() {
        Notification notification = this.sbn.getNotification();
        int importance = this.mChannel.getImportance();
        boolean z = true;
        this.mInitialImportanceExplanationCode = this.mChannel.hasUserSetImportance() ? 2 : 1;
        if ((notification.flags & 128) != 0) {
            notification.priority = 2;
        }
        int i = notification.priority;
        if (i < -2) {
            i = -2;
        } else if (i > 2) {
            i = 2;
        }
        notification.priority = i;
        int i2 = i != -2 ? i != -1 ? (i == 0 || !(i == 1 || i == 2)) ? 3 : 4 : 2 : 1;
        NotificationUsageStats.SingleNotificationStats singleNotificationStats = this.stats;
        singleNotificationStats.requestedImportance = i2;
        if (this.mSound == null && this.mVibration == null) {
            z = false;
        }
        singleNotificationStats.isNoisy = z;
        if (this.mPreChannelsNotification
                && (importance == -1000 || !this.mChannel.hasUserSetImportance())) {
            boolean z2 = singleNotificationStats.isNoisy;
            int i3 = (z2 || i2 <= 2) ? i2 : 2;
            importance = notification.fullScreenIntent != null ? 4 : (!z2 || i3 >= 3) ? i3 : 3;
            this.mInitialImportanceExplanationCode = 5;
        }
        singleNotificationStats.naturalImportance = importance;
        return importance;
    }

    public final long calculateRankingTimeMs(long j) {
        Notification notification = this.sbn.getNotification();
        if (!android.app.Flags.sortSectionByTime()) {
            long j2 = notification.when;
            if (j2 != 0 && j2 <= this.sbn.getPostTime()) {
                return notification.when;
            }
        } else if (notification.hasAppProvidedWhen()
                && notification.getWhen() <= this.sbn.getPostTime()) {
            return notification.getWhen();
        }
        return j > 0 ? j : this.sbn.getPostTime();
    }

    public final void calculateUserSentiment() {
        if ((this.mChannel.getUserLockedFields() & 4) != 0 || this.mIsAppImportanceLocked) {
            this.mUserSentiment = 1;
        }
    }

    public final VibrationEffect calculateVibration() {
        VibrationEffect createPwleWaveformVibration;
        VibrationEffect vibrationEffect;
        VibrationEffect createPwleWaveformVibration2;
        Context context = this.mContext;
        Vibrator vibrator = (Vibrator) context.getSystemService(Vibrator.class);
        Resources resources = context.getResources();
        long[] jArr = VibratorHelper.DEFAULT_VIBRATE_PATTERN;
        long[] longArray =
                VibratorHelper.getLongArray(
                        resources, R.array.config_smallAreaDetectionAllowlist, jArr);
        VibratorHelper.getLongArray(context.getResources(), 17236266, jArr);
        float[] floatArray =
                VibratorHelper.getFloatArray(
                        context.getResources(), R.array.config_sms_enabled_locking_shift_tables);
        VibratorHelper.getFloatArray(context.getResources(), 17236267);
        context.getResources().getInteger(R.integer.config_dreamOpenAnimationDuration);
        Notification notification = this.sbn.getNotification();
        boolean z = (notification.flags & 4) != 0;
        if (this.mPreChannelsNotification && (this.mChannel.getUserLockedFields() & 16) == 0) {
            return (notification.defaults & 2) != 0
                    ? (!vibrator.hasFrequencyControl()
                                    || (createPwleWaveformVibration2 =
                                                    VibratorHelper.createPwleWaveformVibration(
                                                            floatArray, z))
                                            == null)
                            ? VibratorHelper.createWaveformVibration(longArray, z)
                            : createPwleWaveformVibration2
                    : VibratorHelper.createWaveformVibration(notification.vibrate, z);
        }
        NotificationChannel notificationChannel = this.mChannel;
        if (!notificationChannel.shouldVibrate()) {
            return null;
        }
        if (android.app.Flags.notificationChannelVibrationEffectApi()
                && (vibrationEffect = notificationChannel.getVibrationEffect()) != null
                && vibrator.areVibrationFeaturesSupported(vibrationEffect)) {
            return vibrationEffect.applyRepeatingIndefinitely(z, 0);
        }
        long[] vibrationPattern = notificationChannel.getVibrationPattern();
        return vibrationPattern == null
                ? (!vibrator.hasFrequencyControl()
                                || (createPwleWaveformVibration =
                                                VibratorHelper.createPwleWaveformVibration(
                                                        floatArray, z))
                                        == null)
                        ? VibratorHelper.createWaveformVibration(longArray, z)
                        : createPwleWaveformVibration
                : VibratorHelper.createWaveformVibration(vibrationPattern, z);
    }

    public final void copyRankingInformation(NotificationRecord notificationRecord) {
        this.mContactAffinity = notificationRecord.mContactAffinity;
        this.mRecentlyIntrusive = notificationRecord.mRecentlyIntrusive;
        this.mPackagePriority = notificationRecord.mPackagePriority;
        this.mPackageVisibility = notificationRecord.mPackageVisibility;
        this.mIntercept = notificationRecord.mIntercept;
        this.mHidden = notificationRecord.mHidden;
        this.mRankingTimeMs = calculateRankingTimeMs(notificationRecord.mRankingTimeMs);
        this.mCreationTimeMs = notificationRecord.mCreationTimeMs;
        this.mVisibleSinceMs = notificationRecord.mVisibleSinceMs;
        if (notificationRecord.sbn.getOverrideGroupKey() == null || this.sbn.isAppGroup()) {
            return;
        }
        this.sbn.setOverrideGroupKey(notificationRecord.sbn.getOverrideGroupKey());
    }

    public final void dump(int i, ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1138166333441L, this.sbn.getKey());
        protoOutputStream.write(1159641169922L, i);
        NotificationChannel notificationChannel = this.mChannel;
        if (notificationChannel != null) {
            protoOutputStream.write(1138166333444L, notificationChannel.getId());
        }
        protoOutputStream.write(1133871366152L, this.mLight != null);
        protoOutputStream.write(1133871366151L, this.mVibration != null);
        protoOutputStream.write(1120986464259L, this.sbn.getNotification().flags);
        protoOutputStream.write(1138166333449L, this.sbn.getGroupKey());
        protoOutputStream.write(1172526071818L, this.mImportance);
        Uri uri = this.mSound;
        if (uri != null) {
            protoOutputStream.write(1138166333445L, uri.toString());
        }
        AudioAttributes audioAttributes = this.mAttributes;
        if (audioAttributes != null) {
            audioAttributes.dumpDebug(protoOutputStream, 1146756268038L);
        }
        protoOutputStream.write(1138166333451L, this.sbn.getPackageName());
        protoOutputStream.write(1138166333452L, this.sbn.getOpPkg());
        protoOutputStream.end(start);
    }

    @NeverCompile
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        Notification notification = this.sbn.getNotification();
        printWriter.println(str + this);
        String concat = str.concat("  ");
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(concat, "uid=");
        m.append(this.sbn.getUid());
        m.append(" userId=");
        m.append(this.sbn.getUserId());
        printWriter.println(m.toString());
        printWriter.println(concat + "opPkg=" + this.sbn.getOpPkg());
        printWriter.println(concat + "icon=" + notification.getSmallIcon());
        printWriter.println(concat + "flags=" + Notification.flagsToString(notification.flags));
        printWriter.println(
                concat + "originalFlags=" + Notification.flagsToString(this.mOriginalFlags));
        StringBuilder sb = new StringBuilder();
        sb.append(concat);
        sb.append("pri=");
        StringBuilder m2 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        sb, notification.priority, printWriter, concat, "key=");
        m2.append(this.sbn.getKey());
        printWriter.println(m2.toString());
        printWriter.println(concat + "seen=" + this.mStats.hasSeen());
        printWriter.println(concat + "groupKey=" + this.sbn.getGroupKey());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(concat);
        sb2.append("notification=");
        printWriter.println(sb2.toString());
        dumpNotification(
                printWriter,
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), concat, concat),
                notification,
                z);
        printWriter.println(concat + "publicNotification=");
        dumpNotification(printWriter, concat + concat, notification.publicVersion, z);
        printWriter.println(concat + "stats=" + this.stats.toString());
        printWriter.println(concat + "mContactAffinity=" + this.mContactAffinity);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(concat);
        sb3.append("mRecentlyIntrusive=");
        StringBuilder m3 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                        sb3,
                                        this.mRecentlyIntrusive,
                                        printWriter,
                                        concat,
                                        "mPackagePriority="),
                                this.mPackagePriority,
                                printWriter,
                                concat,
                                "mPackageVisibility="),
                        this.mPackageVisibility,
                        printWriter,
                        concat,
                        "mSystemImportance=");
        m3.append(NotificationListenerService.Ranking.importanceToString(this.mSystemImportance));
        printWriter.println(m3.toString());
        printWriter.println(
                concat
                        + "mAsstImportance="
                        + NotificationListenerService.Ranking.importanceToString(
                                this.mAssistantImportance));
        printWriter.println(
                concat
                        + "mImportance="
                        + NotificationListenerService.Ranking.importanceToString(this.mImportance));
        printWriter.println(
                concat + "mImportanceExplanation=" + ((Object) getImportanceExplanation()));
        printWriter.println(
                concat
                        + "mProposedImportance="
                        + NotificationListenerService.Ranking.importanceToString(
                                this.mProposedImportance));
        StringBuilder sb4 = new StringBuilder();
        sb4.append(concat);
        sb4.append("mIsAppImportanceLocked=");
        StringBuilder m4 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                .m(
                                                        sb4,
                                                        this.mIsAppImportanceLocked,
                                                        printWriter,
                                                        concat,
                                                        "mSensitiveContent="),
                                        this.mSensitiveContent,
                                        printWriter,
                                        concat,
                                        "mIntercept="),
                                this.mIntercept,
                                printWriter,
                                concat,
                                "mHidden=="),
                        this.mHidden,
                        printWriter,
                        concat,
                        "mGlobalSortKey=");
        m4.append(this.mGlobalSortKey);
        printWriter.println(m4.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ");
        StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(concat, "mRankingTimeMs=");
        m5.append(this.mRankingTimeMs);
        m5.append("(");
        m5.append(simpleDateFormat.format(new Date(this.mRankingTimeMs)));
        m5.append(")");
        printWriter.println(m5.toString());
        printWriter.println(
                concat
                        + "mCreationTimeMs="
                        + this.mCreationTimeMs
                        + "("
                        + simpleDateFormat.format(new Date(this.mCreationTimeMs))
                        + ")");
        printWriter.println(
                concat
                        + "mVisibleSinceMs="
                        + this.mVisibleSinceMs
                        + "("
                        + simpleDateFormat.format(new Date(this.mVisibleSinceMs))
                        + ")");
        printWriter.println(
                concat
                        + "mUpdateTimeMs="
                        + this.mUpdateTimeMs
                        + "("
                        + simpleDateFormat.format(new Date(this.mUpdateTimeMs))
                        + ")");
        printWriter.println(
                concat
                        + "mInterruptionTimeMs="
                        + this.mInterruptionTimeMs
                        + "("
                        + simpleDateFormat.format(new Date(this.mInterruptionTimeMs))
                        + ")");
        StringBuilder sb5 = new StringBuilder();
        sb5.append(concat);
        sb5.append("mSuppressedVisualEffects= ");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                sb5, this.mSuppressedVisualEffects, printWriter);
        if (this.mPreChannelsNotification) {
            StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(concat, "defaults=");
            m6.append(Notification.defaultsToString(notification.defaults));
            printWriter.println(m6.toString());
            printWriter.println(concat + "n.sound=" + notification.sound);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(concat);
            sb6.append("n.audioStreamType=");
            StringBuilder m7 =
                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                            sb6,
                            notification.audioStreamType,
                            printWriter,
                            concat,
                            "n.audioAttributes=");
            m7.append(notification.audioAttributes);
            printWriter.println(m7.toString());
            printWriter.println(
                    concat
                            + String.format(
                                    "  led=0x%08x onMs=%d offMs=%d",
                                    Integer.valueOf(notification.ledARGB),
                                    Integer.valueOf(notification.ledOnMS),
                                    Integer.valueOf(notification.ledOffMS)));
            printWriter.println(concat + "vibrate=" + Arrays.toString(notification.vibrate));
        }
        StringBuilder m8 = Preconditions$$ExternalSyntheticOutline0.m(concat, "mSound= ");
        m8.append(this.mSound);
        printWriter.println(m8.toString());
        printWriter.println(concat + "mVibration= " + this.mVibration);
        printWriter.println(concat + "mAttributes= " + this.mAttributes);
        printWriter.println(concat + "mLight= " + this.mLight);
        StringBuilder sb7 = new StringBuilder();
        sb7.append(concat);
        sb7.append("mShowBadge=");
        StringBuilder m9 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        sb7, this.mShowBadge, printWriter, concat, "mColorized=");
        m9.append(notification.isColorized());
        printWriter.println(m9.toString());
        StringBuilder sb8 = new StringBuilder();
        sb8.append(concat);
        sb8.append("mAllowBubble=");
        StringBuilder m10 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        sb8, this.mAllowBubble, printWriter, concat, "isBubble=");
        m10.append(notification.isBubbleNotification());
        printWriter.println(m10.toString());
        StringBuilder sb9 = new StringBuilder();
        sb9.append(concat);
        sb9.append("mIsInterruptive=");
        StringBuilder m11 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        sb9,
                        this.mIsInterruptive,
                        printWriter,
                        concat,
                        "effectiveNotificationChannel=");
        m11.append(this.mChannel);
        printWriter.println(m11.toString());
        if (this.mPeopleOverride != null) {
            StringBuilder m12 =
                    Preconditions$$ExternalSyntheticOutline0.m(concat, "overridePeople= ");
            m12.append(TextUtils.join(",", this.mPeopleOverride));
            printWriter.println(m12.toString());
        }
        if (this.mSnoozeCriteria != null) {
            StringBuilder m13 =
                    Preconditions$$ExternalSyntheticOutline0.m(concat, "snoozeCriteria=");
            m13.append(TextUtils.join(",", this.mSnoozeCriteria));
            printWriter.println(m13.toString());
        }
        StringBuilder m14 = Preconditions$$ExternalSyntheticOutline0.m(concat, "mAdjustments=");
        m14.append(this.mAdjustments);
        printWriter.println(m14.toString());
        StringBuilder sb10 = new StringBuilder();
        sb10.append(concat);
        sb10.append("shortcut=");
        sb10.append(notification.getShortcutId());
        sb10.append(" found valid? ");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                sb10,
                                this.mShortcutInfo != null,
                                printWriter,
                                concat,
                                "mUserVisOverride="),
                        this.mPackageVisibility,
                        printWriter,
                        concat,
                        "mSuggestionsGeneratedByAssistant="),
                this.mSuggestionsGeneratedByAssistant,
                printWriter);
        ArrayList arrayList = this.mSystemGeneratedSmartActions;
        int size = arrayList != null ? arrayList.size() : 0;
        if (size != 0) {
            printWriter.println(concat + "mSystemGeneratedSmartActions=");
            for (int i = 0; i < size; i++) {
                Notification.Action action =
                        (Notification.Action) this.mSystemGeneratedSmartActions.get(i);
                Icon icon = action.getIcon();
                String valueOf = String.valueOf(icon);
                Integer valueOf2 = Integer.valueOf(i);
                CharSequence charSequence = action.title;
                String str2 = "null";
                if (icon == null) {
                    valueOf = "null";
                }
                PendingIntent pendingIntent = action.actionIntent;
                if (pendingIntent != null) {
                    str2 = pendingIntent.toString();
                }
                printWriter.println(
                        String.format(
                                "%s    [%d] \"%s\" \"%s\"-> %s",
                                concat, valueOf2, charSequence, valueOf, str2));
            }
        }
        ArrayList arrayList2 = this.mSmartReplies;
        int size2 = arrayList2 != null ? arrayList2.size() : 0;
        if (size2 != 0) {
            printWriter.println(concat + "mSmartReplies=");
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.println(
                        String.format(
                                "%s      [%d] %s",
                                concat,
                                Integer.valueOf(i2),
                                ((CharSequence) this.mSmartReplies.get(i2)).toString()));
            }
        }
    }

    public final int getExposureMs(long j) {
        long j2 = this.mVisibleSinceMs;
        if (j2 == 0) {
            return 0;
        }
        return (int) (j - j2);
    }

    public final int getFlags() {
        return this.sbn.getNotification().flags;
    }

    public final CharSequence getImportanceExplanation() {
        int i = this.mImportanceExplanationCode;
        if (i == 1) {
            return "app";
        }
        if (i == 2) {
            return "user";
        }
        if (i == 3) {
            return "asst";
        }
        if (i == 4) {
            return "system";
        }
        if (i != 5) {
            return null;
        }
        return "app";
    }

    public final String getKey() {
        return this.sbn.getKey();
    }

    public final LogMaker getLogMaker() {
        return getLogMaker(System.currentTimeMillis());
    }

    public final LogMaker getLogMaker(long j) {
        LogMaker addTaggedData =
                this.sbn
                        .getLogMaker()
                        .addTaggedData(858, Integer.valueOf(this.mImportance))
                        .addTaggedData(793, Integer.valueOf((int) (j - this.mCreationTimeMs)))
                        .addTaggedData(795, Integer.valueOf((int) (j - this.mUpdateTimeMs)))
                        .addTaggedData(794, Integer.valueOf(getExposureMs(j)))
                        .addTaggedData(
                                NetworkConstants.ETHER_MTU,
                                Integer.valueOf((int) (j - this.mInterruptionTimeMs)));
        int i = this.mImportanceExplanationCode;
        if (i != 0) {
            addTaggedData.addTaggedData(1688, Integer.valueOf(i));
            int i2 = this.mImportanceExplanationCode;
            if (i2 == 3 || i2 == 4) {
                NotificationUsageStats.SingleNotificationStats singleNotificationStats = this.stats;
                if (singleNotificationStats.naturalImportance != -1000) {
                    addTaggedData.addTaggedData(
                            1690, Integer.valueOf(this.mInitialImportanceExplanationCode));
                    addTaggedData.addTaggedData(
                            1689, Integer.valueOf(singleNotificationStats.naturalImportance));
                }
            }
        }
        int i3 = this.mAssistantImportance;
        if (i3 != -1000) {
            addTaggedData.addTaggedData(1691, Integer.valueOf(i3));
        }
        String str = this.mAdjustmentIssuer;
        if (str != null) {
            addTaggedData.addTaggedData(1742, Integer.valueOf(str.hashCode()));
        }
        return addTaggedData;
    }

    public final int getNotificationType() {
        if (isConversation()) {
            return 1;
        }
        return this.mImportance >= 3 ? 2 : 4;
    }

    public final StatusBarNotification getSbn() {
        return this.sbn;
    }

    public final boolean isCategory(String str) {
        return Objects.equals(this.sbn.getNotification().category, str);
    }

    public final boolean isConversation() {
        Notification notification = this.sbn.getNotification();
        if (this.mChannel.isDemoted()
                || this.mAppDemotedFromConvo
                || this.mIsNotConversationOverride) {
            return false;
        }
        boolean isStyle = notification.isStyle(Notification.MessagingStyle.class);
        int i = this.mTargetSdkVersion;
        if (!isStyle) {
            return this.mPkgAllowedAsConvo
                    && i < 30
                    && "msg".equals(this.sbn.getNotification().category);
        }
        if (i >= 30 && notification.isStyle(Notification.MessagingStyle.class)) {
            ShortcutInfo shortcutInfo = this.mShortcutInfo;
            if (shortcutInfo != null) {
                Person[] persons = shortcutInfo.getPersons();
                if (persons != null && persons.length != 0) {
                    for (Person person : persons) {
                        if (person.isBot()) {}
                    }
                }
            }
            return false;
        }
        return (this.mHasSentValidMsg && this.mShortcutInfo == null) ? false : true;
    }

    public final void resetRankingTime() {
        if (android.app.Flags.sortSectionByTime()) {
            this.mRankingTimeMs = calculateRankingTimeMs(this.sbn.getPostTime());
        }
    }

    public final void setInterruptive(boolean z) {
        this.mIsInterruptive = z;
        long currentTimeMillis = System.currentTimeMillis();
        this.mInterruptionTimeMs = z ? currentTimeMillis : this.mInterruptionTimeMs;
        if (z) {
            MetricsLogger.action(
                    getLogMaker()
                            .setCategory(1501)
                            .setType(1)
                            .addTaggedData(
                                    NetworkConstants.ETHER_MTU,
                                    Integer.valueOf(
                                            (int) (currentTimeMillis - this.mInterruptionTimeMs))));
            MetricsLogger.histogram(
                    this.mContext,
                    "note_interruptive",
                    (int) (currentTimeMillis - this.mInterruptionTimeMs));
        }
    }

    public final void setVisibility(
            boolean z, int i, int i2, NotificationRecordLogger notificationRecordLogger) {
        long currentTimeMillis = System.currentTimeMillis();
        this.mVisibleSinceMs = z ? currentTimeMillis : this.mVisibleSinceMs;
        this.stats.onVisibilityChanged(z);
        MetricsLogger.action(
                getLogMaker(currentTimeMillis)
                        .setCategory(128)
                        .setType(z ? 1 : 2)
                        .addTaggedData(798, Integer.valueOf(i))
                        .addTaggedData(1395, Integer.valueOf(i2)));
        if (z) {
            this.mStats.setSeen();
            if (this.mTextChanged) {
                setInterruptive(true);
            }
            MetricsLogger.histogram(
                    this.mContext,
                    "note_freshness",
                    (int) (currentTimeMillis - this.mUpdateTimeMs));
        }
        EventLog.writeEvent(
                27531,
                this.sbn.getKey(),
                Integer.valueOf(z ? 1 : 0),
                Integer.valueOf((int) (currentTimeMillis - this.mCreationTimeMs)),
                Integer.valueOf((int) (currentTimeMillis - this.mUpdateTimeMs)),
                0,
                Integer.valueOf(i));
        notificationRecordLogger.getClass();
        ((NotificationRecordLoggerImpl) notificationRecordLogger)
                .log(
                        z
                                ? NotificationRecordLogger.NotificationEvent.NOTIFICATION_OPEN
                                : NotificationRecordLogger.NotificationEvent.NOTIFICATION_CLOSE,
                        this);
    }

    public final String toString() {
        return String.format(
                "NotificationRecord(0x%08x: pkg=%s user=%s id=%d tag=%s importance=%d key=%s: %s)",
                Integer.valueOf(System.identityHashCode(this)),
                this.sbn.getPackageName(),
                this.sbn.getUser(),
                Integer.valueOf(this.sbn.getId()),
                this.sbn.getTag(),
                Integer.valueOf(this.mImportance),
                this.sbn.getKey(),
                this.sbn.getNotification());
    }

    public final void updateNotificationChannel(NotificationChannel notificationChannel) {
        if (notificationChannel != null) {
            this.mChannel = notificationChannel;
            calculateImportance();
            calculateUserSentiment();
            this.mVibration = calculateVibration();
            if (android.app.Flags.restrictAudioAttributesCall()
                    || android.app.Flags.restrictAudioAttributesAlarm()
                    || android.app.Flags.restrictAudioAttributesMedia()) {
                if (notificationChannel.getAudioAttributes() != null) {
                    this.mAttributes = notificationChannel.getAudioAttributes();
                } else {
                    this.mAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
                }
            }
        }
    }

    public final void visitGrantableUri(Uri uri, boolean z, boolean z2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        ArraySet arraySet = this.mGrantableUris;
        if (arraySet == null || !arraySet.contains(uri)) {
            int uid = this.sbn.getUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    ((UriGrantsManagerService.LocalService) this.mUgmInternal)
                            .checkGrantUriPermission(
                                    uid,
                                    null,
                                    ContentProvider.getUriWithoutUserId(uri),
                                    1,
                                    ContentProvider.getUserIdFromUri(
                                            uri, UserHandle.getUserId(uid)));
                    if (this.mGrantableUris == null) {
                        this.mGrantableUris = new ArraySet();
                    }
                    this.mGrantableUris.add(uri);
                } catch (SecurityException e) {
                    if (!z) {
                        if (z2) {
                            this.mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
                            Log.w(
                                    "NotificationRecord",
                                    "Replacing " + uri + " from " + uid + ": " + e.getMessage());
                        } else {
                            if (this.mTargetSdkVersion >= 28) {
                                throw e;
                            }
                            Log.w(
                                    "NotificationRecord",
                                    "Ignoring " + uri + " from " + uid + ": " + e.getMessage());
                        }
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }
}
