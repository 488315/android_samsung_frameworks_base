package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.AttributionSource;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.UserInfo;
import android.metrics.LogMaker;
import android.os.Binder;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.Preconditions;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class PreferencesHelper implements RankingConfig {
    static final int DEFAULT_BUBBLE_PREFERENCE = 0;
    static final boolean DEFAULT_HIDE_SILENT_STATUS_BAR_ICONS = false;
    static final int NOTIFICATION_CHANNEL_COUNT_LIMIT = 5000;
    static final int NOTIFICATION_CHANNEL_GROUP_COUNT_LIMIT = 6000;
    public static final long PREF_GRACE_PERIOD_MS = Duration.ofDays(2).toMillis();
    static final String TAG_RANKING = "ranking";
    static final int UNKNOWN_UID = -10000;
    public final AppOpsManager mAppOps;
    public SparseBooleanArray mBadgingEnabled;
    public SparseBooleanArray mBubblesEnabled;
    public final Clock mClock;
    public final Context mContext;
    public boolean mCurrentUserHasChannelsBypassingDnd;
    public boolean mIsMediaNotificationFilteringEnabled;
    public SparseBooleanArray mLockScreenPrivateNotifications;
    public SparseBooleanArray mLockScreenShowNotifications;
    public final NotificationChannelLogger mNotificationChannelLogger;
    public final PermissionHelper mPermissionHelper;
    public final PermissionManager mPermissionManager;
    public final PackageManager mPm;
    public final RankingHandler mRankingHandler;
    public final boolean mShowReviewPermissionsNotification;
    public final ZenModeHelper mZenModeHelper;
    public final Object mLock = new Object();
    public final ArrayMap mPackagePreferences = new ArrayMap();
    public final ArrayMap mRestoredWithoutUids = new ArrayMap();
    public boolean mHideSilentStatusBarIcons = false;
    public final Map mOemLockedApps = new HashMap();
    public int mLockScreenPrivateNotificationsValue = -1000;
    public final HashSet mAllowList = new HashSet();
    public int mHideContentXmlVersion = 0;
    public final int mDeviceFirstApiLevel =
            SystemProperties.getInt("ro.product.first_api_level", 0);
    public final List mOngoingActivityAllowedAppList = new ArrayList();
    public final List mBlockList = new ArrayList();
    public final List mCscConfigNoneBlockableList = new ArrayList();
    public final int XML_VERSION = 4;

    public final class Delegate {
        public boolean mEnabled;
        public final String mPkg;
        public final int mUid;

        public Delegate(int i, String str, boolean z) {
            this.mPkg = str;
            this.mUid = i;
            this.mEnabled = z;
        }
    }

    public final class PackagePreferences {
        public boolean allowEdgeLighting;
        public int allowOngoingActivity;
        public boolean allowSubDisplayNoti;
        public int appLockScreenVisibility;
        public int bubblePreference;
        public ArrayMap channels;
        public long creationTime;
        public boolean defaultAppLockedImportance;
        public Delegate delegate;
        public boolean fixedImportance;
        public Map groups;
        public boolean hasSentInvalidMessage;
        public boolean hasSentValidBubble;
        public boolean hasSentValidMessage;
        public int importance;
        public boolean isAllowPopup;
        public boolean isNotificationAlertsEnabled;
        public int lockedAppFields;
        public boolean migrateToPm;
        public int muteByWearable;
        public List oemLockedChannels;
        public boolean oemLockedImportance;
        public String pkg;
        public int priority;
        public boolean reminder;
        public boolean showBadge;
        public int uid;
        public boolean userDemotedMsgApp;
        public int userId;
        public int visibility;
    }

    public PreferencesHelper(
            Context context,
            PackageManager packageManager,
            RankingHandler rankingHandler,
            ZenModeHelper zenModeHelper,
            PermissionHelper permissionHelper,
            PermissionManager permissionManager,
            NotificationChannelLogger notificationChannelLogger,
            AppOpsManager appOpsManager,
            boolean z,
            Clock clock) {
        this.mContext = context;
        this.mZenModeHelper = zenModeHelper;
        this.mRankingHandler = rankingHandler;
        this.mPermissionHelper = permissionHelper;
        this.mPermissionManager = permissionManager;
        this.mPm = packageManager;
        this.mNotificationChannelLogger = notificationChannelLogger;
        this.mAppOps = appOpsManager;
        this.mShowReviewPermissionsNotification = z;
        this.mIsMediaNotificationFilteringEnabled =
                context.getResources().getBoolean(R.bool.config_secondaryBuiltInDisplayIsRound);
        this.mClock = clock;
        updateBadgingEnabled();
        updateBubblesEnabled();
        updateMediaNotificationFilteringEnabled();
    }

    public static JSONArray dumpBansJson(
            NotificationManagerService.DumpFilter dumpFilter, ArrayMap arrayMap) {
        JSONArray jSONArray = new JSONArray();
        ArrayMap arrayMap2 = new ArrayMap();
        if (arrayMap != null) {
            for (Pair pair : arrayMap.keySet()) {
                if (!((Boolean) ((Pair) arrayMap.get(pair)).first).booleanValue()) {
                    arrayMap2.put((Integer) pair.first, (String) pair.second);
                }
            }
        }
        for (Map.Entry entry : arrayMap2.entrySet()) {
            int userId = UserHandle.getUserId(((Integer) entry.getKey()).intValue());
            String str = (String) entry.getValue();
            if (dumpFilter.matches(str)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("userId", userId);
                    jSONObject.put("packageName", str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    public static void dumpPackagePreferencesLocked(
            ProtoOutputStream protoOutputStream,
            long j,
            NotificationManagerService.DumpFilter dumpFilter,
            ArrayMap arrayMap,
            ArrayMap arrayMap2) {
        Set<Pair> keySet = arrayMap2 != null ? arrayMap2.keySet() : null;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            PackagePreferences packagePreferences = (PackagePreferences) arrayMap.valueAt(i);
            if (dumpFilter.matches(packagePreferences.pkg)) {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1138166333441L, packagePreferences.pkg);
                protoOutputStream.write(1120986464258L, packagePreferences.uid);
                Object pair =
                        new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
                if (arrayMap2 != null && keySet.contains(pair)) {
                    protoOutputStream.write(
                            1172526071811L,
                            ((Boolean) ((Pair) arrayMap2.get(pair)).first).booleanValue() ? 3 : 0);
                    keySet.remove(pair);
                }
                protoOutputStream.write(1120986464260L, packagePreferences.priority);
                protoOutputStream.write(1172526071813L, packagePreferences.visibility);
                protoOutputStream.write(1133871366150L, packagePreferences.showBadge);
                Iterator it = packagePreferences.channels.values().iterator();
                while (it.hasNext()) {
                    ((NotificationChannel) it.next()).dumpDebug(protoOutputStream, 2246267895815L);
                }
                Iterator it2 = packagePreferences.groups.values().iterator();
                while (it2.hasNext()) {
                    ((NotificationChannelGroup) it2.next())
                            .dumpDebug(protoOutputStream, 2246267895816L);
                }
                protoOutputStream.end(start);
            }
        }
        if (keySet != null) {
            for (Pair pair2 : keySet) {
                if (dumpFilter.matches((String) pair2.second)) {
                    long start2 = protoOutputStream.start(j);
                    protoOutputStream.write(1138166333441L, (String) pair2.second);
                    protoOutputStream.write(1120986464258L, ((Integer) pair2.first).intValue());
                    protoOutputStream.write(
                            1172526071811L,
                            ((Boolean) ((Pair) arrayMap2.get(pair2)).first).booleanValue() ? 3 : 0);
                    protoOutputStream.end(start2);
                }
            }
        }
    }

    public static LogMaker getChannelLog(NotificationChannel notificationChannel, String str) {
        return new LogMaker(856)
                .setType(6)
                .setPackageName(str)
                .addTaggedData(857, notificationChannel.getId())
                .addTaggedData(858, Integer.valueOf(notificationChannel.getImportance()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0021, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasUserConfiguredSettings(
            com.android.server.notification.PreferencesHelper.PackagePreferences r2) {
        /*
            android.util.ArrayMap r0 = r2.channels
            java.util.Collection r0 = r0.values()
            java.util.Iterator r0 = r0.iterator()
        La:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L1d
            java.lang.Object r1 = r0.next()
            android.app.NotificationChannel r1 = (android.app.NotificationChannel) r1
            int r1 = r1.getUserLockedFields()
            if (r1 == 0) goto La
            goto L21
        L1d:
            int r2 = r2.importance
            if (r2 != 0) goto L23
        L21:
            r2 = 1
            goto L24
        L23:
            r2 = 0
        L24:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.PreferencesHelper.hasUserConfiguredSettings(com.android.server.notification.PreferencesHelper$PackagePreferences):boolean");
    }

    public static void lockFieldsForUpdateLocked(
            NotificationChannel notificationChannel, NotificationChannel notificationChannel2) {
        if (notificationChannel.canBypassDnd() != notificationChannel2.canBypassDnd()) {
            notificationChannel2.lockFields(1);
        }
        if (notificationChannel.getLockscreenVisibility()
                != notificationChannel2.getLockscreenVisibility()) {
            notificationChannel2.lockFields(2);
        }
        if (notificationChannel.getImportance() != notificationChannel2.getImportance()) {
            notificationChannel2.lockFields(4);
        }
        if (notificationChannel.shouldShowLights() != notificationChannel2.shouldShowLights()
                || notificationChannel.getLightColor() != notificationChannel2.getLightColor()) {
            notificationChannel2.lockFields(8);
        }
        if (!Objects.equals(notificationChannel.getSound(), notificationChannel2.getSound())) {
            notificationChannel2.lockFields(32);
        }
        if (!Arrays.equals(
                        notificationChannel.getVibrationPattern(),
                        notificationChannel2.getVibrationPattern())
                || !Objects.equals(
                        notificationChannel.getVibrationEffect(),
                        notificationChannel2.getVibrationEffect())
                || notificationChannel.shouldVibrate() != notificationChannel2.shouldVibrate()) {
            notificationChannel2.lockFields(16);
        }
        if (notificationChannel.canShowBadge() != notificationChannel2.canShowBadge()) {
            notificationChannel2.lockFields(128);
        }
        if (notificationChannel.getAllowBubbles() != notificationChannel2.getAllowBubbles()) {
            notificationChannel2.lockFields(256);
        }
    }

    public static String packagePreferencesKey(int i, String str) {
        return VpnManagerService$$ExternalSyntheticOutline0.m(i, str, "|");
    }

    public static String unrestoredPackageKey(int i, String str) {
        return VpnManagerService$$ExternalSyntheticOutline0.m(i, str, "|");
    }

    public final boolean bubblesEnabled(UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        if (identifier == -1) {
            return false;
        }
        if (this.mBubblesEnabled.indexOfKey(identifier) < 0) {
            this.mBubblesEnabled.put(
                    identifier,
                    Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "notification_bubbles",
                                    1,
                                    identifier)
                            == 1);
        }
        return this.mBubblesEnabled.get(identifier, true);
    }

    public final boolean channelIsLiveLocked(
            PackagePreferences packagePreferences, NotificationChannel notificationChannel) {
        return (isGroupBlocked(
                                packagePreferences.uid,
                                packagePreferences.pkg,
                                notificationChannel.getGroup())
                        || notificationChannel.isDeleted()
                        || notificationChannel.getImportance() == 0)
                ? false
                : true;
    }

    public final boolean createDefaultChannelIfNeededLocked(PackagePreferences packagePreferences) {
        if (packagePreferences.uid == UNKNOWN_UID) {
            return false;
        }
        if (packagePreferences.channels.containsKey("miscellaneous")) {
            ((NotificationChannel) packagePreferences.channels.get("miscellaneous"))
                    .setName(this.mContext.getString(R.string.global_action_toggle_silent_mode));
            return false;
        }
        if (this.mPm.getApplicationInfoAsUser(
                                packagePreferences.pkg,
                                0,
                                UserHandle.getUserId(packagePreferences.uid))
                        .targetSdkVersion
                >= 26) {
            return false;
        }
        NotificationChannel notificationChannel =
                new NotificationChannel(
                        "miscellaneous",
                        this.mContext.getString(R.string.global_action_toggle_silent_mode),
                        packagePreferences.importance);
        notificationChannel.setBypassDnd(packagePreferences.priority == 2);
        notificationChannel.setLockscreenVisibility(packagePreferences.visibility);
        if (packagePreferences.importance != -1000) {
            notificationChannel.lockFields(4);
        }
        if (packagePreferences.priority != 0) {
            notificationChannel.lockFields(1);
        }
        if (packagePreferences.visibility != -1000) {
            notificationChannel.lockFields(2);
        }
        packagePreferences.channels.put(notificationChannel.getId(), notificationChannel);
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean createNotificationChannel(
            java.lang.String r17,
            int r18,
            android.app.NotificationChannel r19,
            boolean r20,
            int r21,
            boolean r22) {
        /*
            Method dump skipped, instructions count: 665
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.PreferencesHelper.createNotificationChannel(java.lang.String,"
                    + " int, android.app.NotificationChannel, boolean, int, boolean):boolean");
    }

    public final boolean deleteDefaultChannelIfNeededLocked(PackagePreferences packagePreferences) {
        if (!packagePreferences.channels.containsKey("miscellaneous")) {
            return false;
        }
        if (this.mPm.getApplicationInfoAsUser(
                                packagePreferences.pkg,
                                0,
                                UserHandle.getUserId(packagePreferences.uid))
                        .targetSdkVersion
                < 26) {
            return false;
        }
        ((NotificationChannel) packagePreferences.channels.get("miscellaneous")).setDeleted(true);
        return true;
    }

    public final boolean deleteNotificationChannelLocked(
            String str, int i, NotificationChannel notificationChannel) {
        if (notificationChannel.isDeleted()) {
            return false;
        }
        notificationChannel.setDeleted(true);
        notificationChannel.setDeletedTimeMs(System.currentTimeMillis());
        LogMaker channelLog = getChannelLog(notificationChannel, str);
        channelLog.setType(2);
        MetricsLogger.action(channelLog);
        NotificationChannelLogger notificationChannelLogger = this.mNotificationChannelLogger;
        notificationChannelLogger.getClass();
        NotificationChannelLogger.NotificationChannelEvent notificationChannelEvent =
                NotificationChannelLogger.NotificationChannelEvent.NOTIFICATION_CHANNEL_CREATED;
        ((NotificationChannelLoggerImpl) notificationChannelLogger)
                .logNotificationChannel(
                        notificationChannel.getConversationId() != null
                                ? NotificationChannelLogger.NotificationChannelEvent
                                        .NOTIFICATION_CHANNEL_CONVERSATION_DELETED
                                : NotificationChannelLogger.NotificationChannelEvent
                                        .NOTIFICATION_CHANNEL_DELETED,
                        notificationChannel,
                        i,
                        str,
                        NotificationChannelLogger.getLoggingImportance(
                                notificationChannel, notificationChannel.getImportance()),
                        0);
        return true;
    }

    public boolean didUserEverDemoteInvalidMsgApp(String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            z = getOrCreatePackagePreferencesLocked(i, str).userDemotedMsgApp;
        }
        return z;
    }

    public final void dump(
            PrintWriter printWriter,
            NotificationManagerService.DumpFilter dumpFilter,
            ArrayMap arrayMap) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter, "    ", "per-package config version: ");
        m.append(this.XML_VERSION);
        printWriter.println(m.toString());
        printWriter.println("PackagePreferences:");
        synchronized (this.mLock) {
            dumpPackagePreferencesLocked(
                    printWriter, dumpFilter, this.mPackagePreferences, arrayMap);
            printWriter.println("Restored without uid:");
            dumpPackagePreferencesLocked(printWriter, dumpFilter, this.mRestoredWithoutUids, null);
        }
    }

    public final JSONArray dumpChannelsJson(NotificationManagerService.DumpFilter dumpFilter) {
        JSONArray jSONArray = new JSONArray();
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mPackagePreferences.size(); i++) {
                try {
                    PackagePreferences packagePreferences =
                            (PackagePreferences) this.mPackagePreferences.valueAt(i);
                    int i2 = 0;
                    for (int i3 = 0; i3 < packagePreferences.channels.size(); i3++) {
                        if (!((NotificationChannel) packagePreferences.channels.valueAt(i3))
                                .isDeleted()) {
                            i2++;
                        }
                    }
                    arrayMap.put(packagePreferences.pkg, Integer.valueOf(i2));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        for (Map.Entry entry : arrayMap.entrySet()) {
            String str = (String) entry.getKey();
            if (dumpFilter.matches(str)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("packageName", str);
                    jSONObject.put("channelCount", entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    public final JSONObject dumpJson(
            NotificationManagerService.DumpFilter dumpFilter, ArrayMap arrayMap) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        synchronized (this.mLock) {
            try {
                jSONObject.put("noUid", this.mRestoredWithoutUids.size());
            } catch (JSONException unused) {
            }
        }
        Set<Pair> keySet = arrayMap != null ? arrayMap.keySet() : null;
        synchronized (this.mLock) {
            try {
                int size = this.mPackagePreferences.size();
                int i = 0;
                while (true) {
                    int i2 = 3;
                    if (i >= size) {
                        break;
                    }
                    PackagePreferences packagePreferences =
                            (PackagePreferences) this.mPackagePreferences.valueAt(i);
                    if (dumpFilter.matches(packagePreferences.pkg)) {
                        JSONObject jSONObject2 = new JSONObject();
                        try {
                            jSONObject2.put("userId", UserHandle.getUserId(packagePreferences.uid));
                            jSONObject2.put("packageName", packagePreferences.pkg);
                            Object pair =
                                    new Pair(
                                            Integer.valueOf(packagePreferences.uid),
                                            packagePreferences.pkg);
                            if (arrayMap != null && keySet.contains(pair)) {
                                if (!((Boolean) ((Pair) arrayMap.get(pair)).first).booleanValue()) {
                                    i2 = 0;
                                }
                                jSONObject2.put(
                                        "importance",
                                        NotificationListenerService.Ranking.importanceToString(i2));
                                keySet.remove(pair);
                            }
                            int i3 = packagePreferences.priority;
                            if (i3 != 0) {
                                jSONObject2.put("priority", Notification.priorityToString(i3));
                            }
                            int i4 = packagePreferences.visibility;
                            if (i4 != -1000) {
                                jSONObject2.put(
                                        LauncherConfigurationInternal
                                                .KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                                        Notification.visibilityToString(i4));
                            }
                            boolean z = packagePreferences.showBadge;
                            if (!z) {
                                jSONObject2.put("showBadge", Boolean.valueOf(z));
                            }
                            JSONArray jSONArray2 = new JSONArray();
                            Iterator it = packagePreferences.channels.values().iterator();
                            while (it.hasNext()) {
                                jSONArray2.put(((NotificationChannel) it.next()).toJson());
                            }
                            jSONObject2.put("channels", jSONArray2);
                            JSONArray jSONArray3 = new JSONArray();
                            Iterator it2 = packagePreferences.groups.values().iterator();
                            while (it2.hasNext()) {
                                jSONArray3.put(((NotificationChannelGroup) it2.next()).toJson());
                            }
                            jSONObject2.put("groups", jSONArray3);
                        } catch (JSONException unused2) {
                        }
                        jSONArray.put(jSONObject2);
                    }
                    i++;
                }
            } finally {
            }
        }
        if (keySet != null) {
            for (Pair pair2 : keySet) {
                if (dumpFilter.matches((String) pair2.second)) {
                    JSONObject jSONObject3 = new JSONObject();
                    try {
                        jSONObject3.put(
                                "userId", UserHandle.getUserId(((Integer) pair2.first).intValue()));
                        jSONObject3.put("packageName", pair2.second);
                        jSONObject3.put(
                                "importance",
                                NotificationListenerService.Ranking.importanceToString(
                                        ((Boolean) ((Pair) arrayMap.get(pair2)).first)
                                                        .booleanValue()
                                                ? 3
                                                : 0));
                    } catch (JSONException unused3) {
                    }
                    jSONArray.put(jSONObject3);
                }
            }
        }
        try {
            jSONObject.put("PackagePreferencess", jSONArray);
        } catch (JSONException unused4) {
        }
        return jSONObject;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpPackagePreferencesLocked(
            java.io.PrintWriter r19,
            com.android.server.notification.NotificationManagerService.DumpFilter r20,
            android.util.ArrayMap r21,
            android.util.ArrayMap r22) {
        /*
            Method dump skipped, instructions count: 701
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.PreferencesHelper.dumpPackagePreferencesLocked(java.io.PrintWriter,"
                    + " com.android.server.notification.NotificationManagerService$DumpFilter,"
                    + " android.util.ArrayMap, android.util.ArrayMap):void");
    }

    public final int getAppsBypassingDndCount(int i) {
        int i2;
        synchronized (this.mLock) {
            try {
                int size = this.mPackagePreferences.size();
                i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    PackagePreferences packagePreferences =
                            (PackagePreferences) this.mPackagePreferences.valueAt(i3);
                    if (i == UserHandle.getUserId(packagePreferences.uid)
                            && packagePreferences.importance != 0) {
                        Iterator it = packagePreferences.channels.values().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                NotificationChannel notificationChannel =
                                        (NotificationChannel) it.next();
                                if (channelIsLiveLocked(packagePreferences, notificationChannel)
                                        && notificationChannel.canBypassDnd()) {
                                    i2++;
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final NotificationChannel getConversationNotificationChannel(
            String str, int i, String str2, String str3, boolean z, boolean z2) {
        NotificationChannel notificationChannel;
        Preconditions.checkNotNull(str);
        synchronized (this.mLock) {
            try {
                PackagePreferences orCreatePackagePreferencesLocked =
                        getOrCreatePackagePreferencesLocked(i, str);
                if (str2 == null) {
                    str2 = "miscellaneous";
                }
                NotificationChannel notificationChannel2 = null;
                if (str3 != null) {
                    for (NotificationChannel notificationChannel3 :
                            orCreatePackagePreferencesLocked.channels.values()) {
                        if (str3.equals(notificationChannel3.getConversationId())
                                && str2.equals(notificationChannel3.getParentChannelId())
                                && (z2 || !notificationChannel3.isDeleted())) {
                            notificationChannel2 = notificationChannel3;
                            break;
                        }
                    }
                }
                return (notificationChannel2 != null
                                || !z
                                || (notificationChannel =
                                                (NotificationChannel)
                                                        orCreatePackagePreferencesLocked.channels
                                                                .get(str2))
                                        == null
                                || (!z2 && notificationChannel.isDeleted()))
                        ? notificationChannel2
                        : notificationChannel;
            } finally {
            }
        }
    }

    public int getFsiState(String str, int i, boolean z) {
        if (z) {
            return this.mPermissionManager.checkPermissionForPreflight(
                                    "android.permission.USE_FULL_SCREEN_INTENT",
                                    new AttributionSource.Builder(i).setPackageName(str).build())
                            == 0
                    ? 1
                    : 2;
        }
        return 0;
    }

    public final int getLockScreenNotificationVisibilityForPackage(String str, int i) {
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked == null) {
                    return -1000;
                }
                return packagePreferencesLocked.appLockScreenVisibility;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean getNotificationAlertsEnabledForPackage(String str, int i) {
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked == null) {
                    return true;
                }
                return packagePreferencesLocked.isNotificationAlertsEnabled;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final NotificationChannelGroup getNotificationChannelGroup(
            int i, String str, String str2) {
        Objects.requireNonNull(str2);
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str2);
                if (packagePreferencesLocked == null) {
                    return null;
                }
                return (NotificationChannelGroup) packagePreferencesLocked.groups.get(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final NotificationChannelGroup getNotificationChannelGroupWithChannels(
            int i, String str, String str2, boolean z) {
        int i2;
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked != null
                        && str2 != null
                        && packagePreferencesLocked.groups.containsKey(str2)) {
                    NotificationChannelGroup clone =
                            ((NotificationChannelGroup) packagePreferencesLocked.groups.get(str2))
                                    .clone();
                    clone.setChannels(new ArrayList());
                    int size = packagePreferencesLocked.channels.size();
                    while (i2 < size) {
                        NotificationChannel notificationChannel =
                                (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
                        i2 = (!z && notificationChannel.isDeleted()) ? i2 + 1 : 0;
                        if (str2.equals(notificationChannel.getGroup())) {
                            clone.addChannel(notificationChannel);
                        }
                    }
                    return clone;
                }
                return null;
            } finally {
            }
        }
    }

    public final ParceledListSlice getNotificationChannelGroups(
            int i, String str, Set set, boolean z, boolean z2, boolean z3) {
        Objects.requireNonNull(str);
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked == null) {
                    return ParceledListSlice.emptyList();
                }
                NotificationChannelGroup notificationChannelGroup =
                        new NotificationChannelGroup(null, null);
                int size = packagePreferencesLocked.channels.size();
                for (int i2 = 0; i2 < size; i2++) {
                    NotificationChannel notificationChannel =
                            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
                    if (z || !notificationChannel.isDeleted()) {
                        if (set != null && notificationChannel.getImportance() != 0) {
                            if (!((HashSet) set).contains(notificationChannel.getId())) {}
                        }
                        if (notificationChannel.getGroup() == null) {
                            notificationChannelGroup.addChannel(notificationChannel);
                        } else if (packagePreferencesLocked.groups.get(
                                        notificationChannel.getGroup())
                                != null) {
                            NotificationChannelGroup notificationChannelGroup2 =
                                    (NotificationChannelGroup)
                                            arrayMap.get(notificationChannel.getGroup());
                            if (notificationChannelGroup2 == null) {
                                notificationChannelGroup2 =
                                        ((NotificationChannelGroup)
                                                        packagePreferencesLocked.groups.get(
                                                                notificationChannel.getGroup()))
                                                .clone();
                                notificationChannelGroup2.setChannels(new ArrayList());
                                arrayMap.put(
                                        notificationChannel.getGroup(), notificationChannelGroup2);
                            }
                            notificationChannelGroup2.addChannel(notificationChannel);
                        }
                    }
                }
                if (z2 && notificationChannelGroup.getChannels().size() > 0) {
                    arrayMap.put(null, notificationChannelGroup);
                }
                if (z3) {
                    for (NotificationChannelGroup notificationChannelGroup3 :
                            packagePreferencesLocked.groups.values()) {
                        if (!arrayMap.containsKey(notificationChannelGroup3.getId())) {
                            arrayMap.put(
                                    notificationChannelGroup3.getId(), notificationChannelGroup3);
                        }
                    }
                }
                return new ParceledListSlice(new ArrayList(arrayMap.values()));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParceledListSlice getNotificationChannels(int i, String str, boolean z) {
        Objects.requireNonNull(str);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked == null) {
                    return ParceledListSlice.emptyList();
                }
                int size = packagePreferencesLocked.channels.size();
                for (int i2 = 0; i2 < size; i2++) {
                    NotificationChannel notificationChannel =
                            (NotificationChannel) packagePreferencesLocked.channels.valueAt(i2);
                    if (z || !notificationChannel.isDeleted()) {
                        arrayList.add(notificationChannel);
                    }
                }
                return new ParceledListSlice(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getOngoingActivityAllowedState(int i, String str) {
        int i2;
        synchronized (this.mLock) {
            i2 = getOrCreatePackagePreferencesLocked(i, str).allowOngoingActivity;
        }
        return i2;
    }

    public final PackagePreferences getOrCreatePackagePreferencesLocked(int i, String str) {
        return getOrCreatePackagePreferencesLocked(
                str,
                UserHandle.getUserId(i),
                i,
                -1000,
                0,
                -1000,
                true,
                0,
                this.mClock.millis(),
                true,
                false,
                -1);
    }

    public final PackagePreferences getOrCreatePackagePreferencesLocked(
            String str,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            boolean z,
            int i6,
            long j,
            boolean z2,
            boolean z3,
            int i7) {
        String packagePreferencesKey = packagePreferencesKey(i2, str);
        PackagePreferences packagePreferences =
                i2 == UNKNOWN_UID
                        ? (PackagePreferences)
                                this.mRestoredWithoutUids.get(unrestoredPackageKey(i, str))
                        : (PackagePreferences) this.mPackagePreferences.get(packagePreferencesKey);
        if (packagePreferences == null) {
            packagePreferences = new PackagePreferences();
            packagePreferences.uid = UNKNOWN_UID;
            packagePreferences.importance = -1000;
            packagePreferences.priority = 0;
            packagePreferences.visibility = -1000;
            packagePreferences.showBadge = true;
            packagePreferences.bubblePreference = 0;
            packagePreferences.lockedAppFields = 0;
            packagePreferences.defaultAppLockedImportance = false;
            packagePreferences.fixedImportance = false;
            packagePreferences.oemLockedImportance = false;
            packagePreferences.oemLockedChannels = new ArrayList();
            packagePreferences.hasSentInvalidMessage = false;
            packagePreferences.hasSentValidMessage = false;
            packagePreferences.userDemotedMsgApp = false;
            packagePreferences.hasSentValidBubble = false;
            packagePreferences.allowEdgeLighting = true;
            packagePreferences.allowSubDisplayNoti = false;
            packagePreferences.allowOngoingActivity = -1;
            packagePreferences.isNotificationAlertsEnabled = true;
            packagePreferences.muteByWearable = -1;
            packagePreferences.appLockScreenVisibility = -1000;
            packagePreferences.isAllowPopup = true;
            packagePreferences.reminder = false;
            packagePreferences.migrateToPm = false;
            packagePreferences.delegate = null;
            packagePreferences.channels = new ArrayMap();
            packagePreferences.groups = new ConcurrentHashMap();
            packagePreferences.pkg = str;
            packagePreferences.uid = i2;
            packagePreferences.importance = i3;
            packagePreferences.priority = i4;
            packagePreferences.visibility = i5;
            packagePreferences.showBadge = z;
            packagePreferences.bubblePreference = i6;
            Flags.persistIncompleteRestoreData();
            if (packagePreferences.uid == UNKNOWN_UID) {
                packagePreferences.creationTime = j;
            }
            packagePreferences.allowEdgeLighting = z2;
            packagePreferences.allowSubDisplayNoti = z3;
            List list = this.mOngoingActivityAllowedAppList;
            if (list == null || !((ArrayList) list).contains(str)) {
                packagePreferences.allowOngoingActivity = i7;
            } else {
                packagePreferences.allowOngoingActivity = 1;
            }
            packagePreferences.appLockScreenVisibility = -1000;
            packagePreferences.isAllowPopup = true;
            packagePreferences.reminder = false;
            if (NmRune.NM_SUPPORT_HIDE_CONTENT_CONVERSATION_PACKAGE
                    && this.mAllowList.contains(packagePreferences.pkg)) {
                packagePreferences.appLockScreenVisibility = 0;
            }
            try {
                createDefaultChannelIfNeededLocked(packagePreferences);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e(
                        "NotificationPrefHelper",
                        "createDefaultChannelIfNeededLocked - Exception: " + e);
            }
            if (packagePreferences.uid == UNKNOWN_UID) {
                Flags.persistIncompleteRestoreData();
                packagePreferences.userId = i;
                this.mRestoredWithoutUids.put(unrestoredPackageKey(i, str), packagePreferences);
            } else {
                this.mPackagePreferences.put(packagePreferencesKey, packagePreferences);
            }
        }
        if (packagePreferences.uid == UNKNOWN_UID) {
            Flags.persistIncompleteRestoreData();
            if (PREF_GRACE_PERIOD_MS < this.mClock.millis() - packagePreferences.creationTime) {
                this.mRestoredWithoutUids.remove(unrestoredPackageKey(i, str));
            }
        }
        return packagePreferences;
    }

    public final PackagePreferences getPackagePreferencesLocked(int i, String str) {
        return (PackagePreferences) this.mPackagePreferences.get(packagePreferencesKey(i, str));
    }

    public final int getWearableAppMuted(int i, String str) {
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked == null) {
                    return -1;
                }
                return packagePreferencesLocked.muteByWearable;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasNotificationChannelsBypassingDnd(int i, String str) {
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferences =
                        (PackagePreferences)
                                this.mPackagePreferences.get(packagePreferencesKey(i, str));
                if (packagePreferences != null) {
                    for (NotificationChannel notificationChannel :
                            packagePreferences.channels.values()) {
                        if (channelIsLiveLocked(packagePreferences, notificationChannel)
                                && notificationChannel.canBypassDnd()) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasSentInvalidMsg(String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            z = getOrCreatePackagePreferencesLocked(i, str).hasSentInvalidMessage;
        }
        return z;
    }

    public boolean hasSentValidMsg(String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            z = getOrCreatePackagePreferencesLocked(i, str).hasSentValidMessage;
        }
        return z;
    }

    public final boolean hasUserDemotedInvalidMsgApp(String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z =
                        isInInvalidMsgState(str, i)
                                ? getOrCreatePackagePreferencesLocked(i, str).userDemotedMsgApp
                                : false;
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean isDelegateAllowed(int i, int i2, String str, String str2) {
        boolean z;
        Delegate delegate;
        synchronized (this.mLock) {
            PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
            z =
                    (packagePreferencesLocked == null
                                    || (delegate = packagePreferencesLocked.delegate) == null
                                    || str2 == null
                                    || i2 == UNKNOWN_UID
                                    || !str2.equals(delegate.mPkg)
                                    || i2 != delegate.mUid
                                    || !delegate.mEnabled)
                            ? false
                            : true;
        }
        return z;
    }

    public boolean isFsiPermissionUserSet(String str, int i, int i2, int i3) {
        return (i2 == 0 || (i3 & 1) == 0) ? false : true;
    }

    public final boolean isGroupBlocked(int i, String str, String str2) {
        if (str2 == null) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                NotificationChannelGroup notificationChannelGroup =
                        (NotificationChannelGroup)
                                getOrCreatePackagePreferencesLocked(i, str).groups.get(str2);
                if (notificationChannelGroup == null) {
                    return false;
                }
                return notificationChannelGroup.isBlocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isInInvalidMsgState(String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                PackagePreferences orCreatePackagePreferencesLocked =
                        getOrCreatePackagePreferencesLocked(i, str);
                z =
                        orCreatePackagePreferencesLocked.hasSentInvalidMessage
                                && !orCreatePackagePreferencesLocked.hasSentValidMessage;
            } finally {
            }
        }
        return z;
    }

    public final void lockChannelsForOEM(String[] strArr) {
        String[] split;
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str) && (split = str.split(":")) != null && split.length > 0) {
                String str2 = split[0];
                String str3 = split.length == 2 ? split[1] : null;
                synchronized (this.mLock) {
                    try {
                        boolean z = false;
                        for (PackagePreferences packagePreferences :
                                this.mPackagePreferences.values()) {
                            if (packagePreferences.pkg.equals(str2)) {
                                if (str3 == null) {
                                    packagePreferences.oemLockedImportance = true;
                                    Iterator it = packagePreferences.channels.values().iterator();
                                    while (it.hasNext()) {
                                        ((NotificationChannel) it.next())
                                                .setImportanceLockedByOEM(true);
                                    }
                                } else {
                                    NotificationChannel notificationChannel =
                                            (NotificationChannel)
                                                    packagePreferences.channels.get(str3);
                                    if (notificationChannel != null) {
                                        notificationChannel.setImportanceLockedByOEM(true);
                                    }
                                    ((ArrayList) packagePreferences.oemLockedChannels).add(str3);
                                }
                                z = true;
                            }
                        }
                        if (!z) {
                            List list =
                                    (List)
                                            ((HashMap) this.mOemLockedApps)
                                                    .getOrDefault(str2, new ArrayList());
                            if (str3 != null) {
                                list.add(str3);
                            }
                            ((HashMap) this.mOemLockedApps).put(str2, list);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void lockChannelsForOEMwithPackagePreferences(
            PackagePreferences packagePreferences) {
        String[] split;
        if (((ArrayList) this.mCscConfigNoneBlockableList).size() == 0) {
            ((ArrayList) this.mCscConfigNoneBlockableList)
                    .addAll(Arrays.asList(this.mContext.getResources().getStringArray(17236310)));
            String string =
                    SemCscFeature.getInstance()
                            .getString("CscFeature_Setting_ConfigBlockNotiAppList");
            if (string != null && string.length() > 0) {
                ((ArrayList) this.mCscConfigNoneBlockableList)
                        .addAll(Arrays.asList(string.split(",")));
            }
        }
        if (((ArrayList) this.mCscConfigNoneBlockableList).size() > 0) {
            for (String str :
                    (String[])
                            ((ArrayList) this.mCscConfigNoneBlockableList)
                                    .toArray(
                                            new String
                                                    [((ArrayList) this.mCscConfigNoneBlockableList)
                                                            .size()])) {
                if (!TextUtils.isEmpty(str)
                        && (split = str.split(":")) != null
                        && split.length > 0) {
                    String str2 = split[0];
                    String str3 = split.length == 2 ? split[1] : null;
                    if (packagePreferences.pkg.equals(str2)) {
                        if (str3 == null) {
                            packagePreferences.oemLockedImportance = true;
                            Iterator it = packagePreferences.channels.values().iterator();
                            while (it.hasNext()) {
                                ((NotificationChannel) it.next()).setImportanceLockedByOEM(true);
                            }
                        } else {
                            NotificationChannel notificationChannel =
                                    (NotificationChannel) packagePreferences.channels.get(str3);
                            if (notificationChannel != null) {
                                notificationChannel.setImportanceLockedByOEM(true);
                            } else {
                                ((ArrayList) packagePreferences.oemLockedChannels).add(str3);
                            }
                        }
                    }
                }
            }
        }
    }

    public final void migrateNotificationPermissions(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            for (PackageInfo packageInfo :
                    this.mPm.getInstalledPackagesAsUser(
                            PackageManager.PackageInfoFlags.of(131072L),
                            ((UserInfo) it.next()).getUserHandle().getIdentifier())) {
                synchronized (this.mLock) {
                    PackagePreferences orCreatePackagePreferencesLocked =
                            getOrCreatePackagePreferencesLocked(
                                    packageInfo.applicationInfo.uid, packageInfo.packageName);
                    if (orCreatePackagePreferencesLocked.migrateToPm) {
                        int i = orCreatePackagePreferencesLocked.uid;
                        if (i != UNKNOWN_UID) {
                            try {
                                String str = orCreatePackagePreferencesLocked.pkg;
                                int userId = UserHandle.getUserId(i);
                                boolean z = orCreatePackagePreferencesLocked.importance != 0;
                                hasUserConfiguredSettings(orCreatePackagePreferencesLocked);
                                PermissionHelper permissionHelper = this.mPermissionHelper;
                                permissionHelper.getClass();
                                if (str != null
                                        && !permissionHelper.isPermissionFixed(str, userId)) {
                                    permissionHelper.setNotificationPermission(
                                            str, z, true, userId);
                                }
                            } catch (Exception e) {
                                Slog.e(
                                        "NotificationPrefHelper",
                                        "could not migrate setting for "
                                                + orCreatePackagePreferencesLocked.pkg,
                                        e);
                            }
                        }
                    }
                }
            }
        }
    }

    public final boolean onlyHasDefaultChannel(String str, int i) {
        synchronized (this.mLock) {
            try {
                PackagePreferences orCreatePackagePreferencesLocked =
                        getOrCreatePackagePreferencesLocked(i, str);
                return orCreatePackagePreferencesLocked.channels.size() == 1
                        && orCreatePackagePreferencesLocked.channels.containsKey("miscellaneous");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void permanentlyDeleteNotificationChannel(String str, int i, String str2) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked == null) {
                    return;
                }
                packagePreferencesLocked.channels.remove(str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeAllReminderSetting(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mPackagePreferences.size();
                for (int i2 = 0; i2 < size; i2++) {
                    PackagePreferences packagePreferences =
                            (PackagePreferences) this.mPackagePreferences.valueAt(i2);
                    if (UserHandle.getUserId(packagePreferences.uid) == i
                            && packagePreferences.reminder) {
                        packagePreferences.reminder = false;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resetDefaultAllowOngoingActivityExceptGivenApps(List list) {
        if (list == null) {
            Slog.d("NotificationPrefHelper", "except  app list is null");
            return;
        }
        synchronized (this.mLock) {
            try {
                int size = this.mPackagePreferences.size();
                for (int i = 0; i < size; i++) {
                    PackagePreferences packagePreferences =
                            (PackagePreferences) this.mPackagePreferences.valueAt(i);
                    if (list.contains(packagePreferences.pkg)) {
                        Slog.d(
                                "NotificationPrefHelper",
                                "resetDefaultAllowOngoingActivityExceptGivenApps - excepPackage = "
                                        + packagePreferences.pkg);
                    } else {
                        packagePreferences.allowOngoingActivity = -1;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restoreChannel(
            com.android.modules.utils.TypedXmlPullParser r7,
            boolean r8,
            com.android.server.notification.PreferencesHelper.PackagePreferences r9,
            boolean r10,
            boolean r11) {
        /*
            r6 = this;
            java.lang.String r0 = "id"
            r1 = 0
            java.lang.String r0 = r7.getAttributeValue(r1, r0)     // Catch: java.lang.Exception -> L3f
            java.lang.String r2 = "name"
            java.lang.String r2 = r7.getAttributeValue(r1, r2)     // Catch: java.lang.Exception -> L3f
            java.lang.String r3 = "importance"
            r4 = -1000(0xfffffffffffffc18, float:NaN)
            int r1 = r7.getAttributeInt(r1, r3, r4)     // Catch: java.lang.Exception -> L3f
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Exception -> L3f
            if (r3 != 0) goto Lc1
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Exception -> L3f
            if (r3 != 0) goto Lc1
            android.app.NotificationChannel r3 = new android.app.NotificationChannel     // Catch: java.lang.Exception -> L3f
            r3.<init>(r0, r2, r1)     // Catch: java.lang.Exception -> L3f
            r1 = 0
            r2 = 1
            if (r8 == 0) goto L42
            int r8 = r9.uid     // Catch: java.lang.Exception -> L3f
            r5 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r8 == r5) goto L33
            r8 = r2
            goto L34
        L33:
            r8 = r1
        L34:
            android.content.Context r6 = r6.mContext     // Catch: java.lang.Exception -> L3f
            r3.populateFromXmlForRestore(r7, r8, r6)     // Catch: java.lang.Exception -> L3f
            if (r10 == 0) goto L45
            r3.setLockscreenVisibility(r4)     // Catch: java.lang.Exception -> L3f
            goto L45
        L3f:
            r6 = move-exception
            goto Lac
        L42:
            r3.populateFromXml(r7)     // Catch: java.lang.Exception -> L3f
        L45:
            boolean r6 = r9.defaultAppLockedImportance     // Catch: java.lang.Exception -> L3f
            if (r6 != 0) goto L50
            boolean r6 = r9.fixedImportance     // Catch: java.lang.Exception -> L3f
            if (r6 == 0) goto L4e
            goto L50
        L4e:
            r6 = r1
            goto L51
        L50:
            r6 = r2
        L51:
            r3.setImportanceLockedByCriticalDeviceFunction(r6)     // Catch: java.lang.Exception -> L3f
            boolean r6 = r9.oemLockedImportance     // Catch: java.lang.Exception -> L3f
            r3.setImportanceLockedByOEM(r6)     // Catch: java.lang.Exception -> L3f
            boolean r6 = r3.isImportanceLockedByOEM()     // Catch: java.lang.Exception -> L3f
            if (r6 != 0) goto L70
            java.util.List r6 = r9.oemLockedChannels     // Catch: java.lang.Exception -> L3f
            java.lang.String r7 = r3.getId()     // Catch: java.lang.Exception -> L3f
            java.util.ArrayList r6 = (java.util.ArrayList) r6     // Catch: java.lang.Exception -> L3f
            boolean r6 = r6.contains(r7)     // Catch: java.lang.Exception -> L3f
            if (r6 == 0) goto L70
            r3.setImportanceLockedByOEM(r2)     // Catch: java.lang.Exception -> L3f
        L70:
            if (r11 == 0) goto L75
            r3.setBypassDnd(r2)     // Catch: java.lang.Exception -> L3f
        L75:
            java.lang.String r6 = r3.getConversationId()     // Catch: java.lang.Exception -> L3f
            if (r6 == 0) goto L88
            java.lang.String r6 = r3.getConversationId()     // Catch: java.lang.Exception -> L3f
            java.lang.String r7 = ":placeholder_id"
            boolean r6 = r6.contains(r7)     // Catch: java.lang.Exception -> L3f
            if (r6 == 0) goto L88
            r1 = r2
        L88:
            r6 = r1 ^ 1
            if (r6 == 0) goto Lc1
            boolean r6 = r3.isDeleted()     // Catch: java.lang.Exception -> L3f
            if (r6 != 0) goto L93
            goto La6
        L93:
            long r6 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> L3f
            r10 = 2592000000(0x9a7ec800, double:1.280618154E-314)
            long r6 = r6 - r10
            long r10 = r3.getDeletedTimeMs()     // Catch: java.lang.Exception -> L3f
            int r6 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r6 > 0) goto La6
            goto Lc1
        La6:
            android.util.ArrayMap r6 = r9.channels     // Catch: java.lang.Exception -> L3f
            r6.put(r0, r3)     // Catch: java.lang.Exception -> L3f
            goto Lc1
        Lac:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "could not restore channel for "
            r7.<init>(r8)
            java.lang.String r8 = r9.pkg
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "NotificationPrefHelper"
            android.util.Slog.w(r8, r7, r6)
        Lc1:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.PreferencesHelper.restoreChannel(com.android.modules.utils.TypedXmlPullParser,"
                    + " boolean,"
                    + " com.android.server.notification.PreferencesHelper$PackagePreferences,"
                    + " boolean, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x02d2, code lost:

       r8 = "name";
       r9 = r6;
       restoreChannel(r40, r41, r7, r31, hasNotificationChannelsBypassingDnd(r7.uid, r7.pkg));
    */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x02f3, code lost:

       if ("delegate".equals(r9) == false) goto L76;
    */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x02f5, code lost:

       r1 = r29;
       r3 = r40.getAttributeInt(r16, r1, com.android.server.notification.PreferencesHelper.UNKNOWN_UID);
       r4 = com.android.internal.util.XmlUtils.readStringAttribute(r40, r8);
       r5 = r40.getAttributeBoolean(r16, "enabled", true);
    */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0308, code lost:

       if (r3 == com.android.server.notification.PreferencesHelper.UNKNOWN_UID) goto L102;
    */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x030e, code lost:

       if (android.text.TextUtils.isEmpty(r4) != false) goto L102;
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0310, code lost:

       r6 = new com.android.server.notification.PreferencesHelper.Delegate(r3, r4, r5);
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0317, code lost:

       r7.delegate = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0316, code lost:

       r6 = r16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x02eb, code lost:

       r8 = "name";
       r9 = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x026e, code lost:

       if (r13 != false) goto L141;
    */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0270, code lost:

       android.util.Slog.w(r11, "Skipping further groups for " + r7.pkg);
       r13 = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0323, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0325, code lost:

       android.util.Slog.e(r11, "deleteDefaultChannelIfNeededLocked - Exception: " + r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x024d, code lost:

       if (r1 != 4) goto L77;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0255, code lost:

       r6 = r40.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0262, code lost:

       if ("channelGroup".equals(r6) == false) goto L136;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x026c, code lost:

       if (r7.groups.size() < 6000) goto L139;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0288, code lost:

       r1 = r40.getAttributeValue(r16, "id");
       r2 = r40.getAttributeValue(r16, "name");
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0296, code lost:

       if (android.text.TextUtils.isEmpty(r1) != false) goto L87;
    */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0298, code lost:

       r3 = new android.app.NotificationChannelGroup(r1, r2);
       r3.populateFromXml(r40);
       r7.groups.put(r1, r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x02ab, code lost:

       if ("channel".equals(r6) == false) goto L145;
    */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x02b5, code lost:

       if (r7.channels.size() < 5000) goto L147;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x02b7, code lost:

       if (r14 != false) goto L148;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x02b9, code lost:

       android.util.Slog.w(r11, "Skipping further channels for " + r7.pkg);
       r14 = r9;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void restorePackage(
            com.android.modules.utils.TypedXmlPullParser r40,
            boolean r41,
            int r42,
            java.lang.String r43,
            boolean r44,
            boolean r45,
            java.util.ArrayList r46,
            java.util.ArrayList r47) {
        /*
            Method dump skipped, instructions count: 872
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.PreferencesHelper.restorePackage(com.android.modules.utils.TypedXmlPullParser,"
                    + " boolean, int, java.lang.String, boolean, boolean, java.util.ArrayList,"
                    + " java.util.ArrayList):void");
    }

    public final void setAllowOngoingActivityState(int i, int i2, String str) {
        synchronized (this.mLock) {
            getOrCreatePackagePreferencesLocked(i, str).allowOngoingActivity = i2;
        }
    }

    public final void setAppBypassDndList(int i, boolean z, List list) {
        synchronized (this.mLock) {
            try {
                boolean z2 = true;
                if (!list.isEmpty()) {
                    int size = this.mPackagePreferences.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        PackagePreferences packagePreferences =
                                (PackagePreferences) this.mPackagePreferences.valueAt(i2);
                        for (NotificationChannel notificationChannel :
                                packagePreferences.channels.values()) {
                            if (channelIsLiveLocked(packagePreferences, notificationChannel)
                                    && notificationChannel.canBypassDnd()) {
                                notificationChannel.setBypassDnd(false);
                            }
                        }
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String[] split = ((String) it.next()).split(":");
                        if (split.length == 2) {
                            Iterator it2 =
                                    getOrCreatePackagePreferencesLocked(
                                                    Integer.parseInt(split[1]), split[0])
                                            .channels
                                            .values()
                                            .iterator();
                            while (it2.hasNext()) {
                                ((NotificationChannel) it2.next()).setBypassDnd(true);
                            }
                        }
                    }
                }
                if (getAppsBypassingDndCount(UserHandle.getUserId(i)) <= 0) {
                    z2 = false;
                }
                if (this.mCurrentUserHasChannelsBypassingDnd != z2) {
                    updateCurrentUserHasChannelsBypassingDnd(i, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setChannelsBypassingDndForMode(int i, boolean z, boolean z2) {
        boolean z3;
        if (!z) {
            if (this.mCurrentUserHasChannelsBypassingDnd) {
                this.mCurrentUserHasChannelsBypassingDnd = false;
                updateZenPolicy(i, false, z2);
                updateCurrentUserHasChannelsBypassingDnd(i, z2);
                return;
            }
            return;
        }
        if (this.mCurrentUserHasChannelsBypassingDnd) {
            return;
        }
        ArrayList appsToBypassDndForEnabledForMode =
                this.mZenModeHelper.getAppsToBypassDndForEnabledForMode();
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = new ArrayList();
                Iterator it = appsToBypassDndForEnabledForMode.iterator();
                z3 = false;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (str != null) {
                        String[] split = str.split(":");
                        if (split.length >= 2) {
                            for (int i2 = 0; i2 < this.mPackagePreferences.size(); i2++) {
                                PackagePreferences packagePreferences =
                                        (PackagePreferences) this.mPackagePreferences.valueAt(i2);
                                if (packagePreferences.pkg.equals(split[0])
                                        && UserHandle.getUserId(packagePreferences.uid)
                                                == Integer.parseInt(split[1])) {
                                    if (this.mPermissionHelper.hasPermission(
                                            packagePreferences.uid)) {
                                        z3 = true;
                                    } else {
                                        arrayList.add(str);
                                    }
                                }
                            }
                        }
                    }
                }
                appsToBypassDndForEnabledForMode.removeAll(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z3) {
            this.mCurrentUserHasChannelsBypassingDnd = true;
            updateZenPolicy(i, true, z2);
        }
    }

    public final void setHideContentAllowList(List list) {
        if (list == null) {
            return;
        }
        this.mAllowList.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            this.mAllowList.add(list.get(i).toString());
        }
        Slog.d(
                "NotificationPrefHelper",
                "setHideContentAllowList - size = " + this.mAllowList.size());
    }

    public final void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) {
        synchronized (this.mLock) {
            try {
                PackagePreferences packagePreferencesLocked = getPackagePreferencesLocked(i, str);
                if (packagePreferencesLocked != null) {
                    packagePreferencesLocked.isNotificationAlertsEnabled = z;
                    Slog.w(
                            "NotificationPrefHelper",
                            "Set a isNotificationAlertsEnabled : "
                                    + packagePreferencesLocked.isNotificationAlertsEnabled);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setOngoingActivityAppList(List list) {
        if (list == null) {
            Slog.d("NotificationPrefHelper", "ongoing activity app list is null");
            return;
        }
        ((ArrayList) this.mOngoingActivityAllowedAppList).clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((ArrayList) this.mOngoingActivityAllowedAppList)
                    .add(((String) list.get(i)).toString());
            Slog.d(
                    "NotificationPrefHelper",
                    "setOngoingActivityAppList package= " + ((String) list.get(i)));
        }
    }

    public final void setReminderEnabled(int i, String str, boolean z) {
        synchronized (this.mLock) {
            getOrCreatePackagePreferencesLocked(i, str).reminder = z;
        }
    }

    public final void setWearableAppMuted(int i, int i2, String str) {
        synchronized (this.mLock) {
            getOrCreatePackagePreferencesLocked(i, str).muteByWearable = i2;
        }
    }

    public final void syncChannelsBypassingDnd() {
        this.mCurrentUserHasChannelsBypassingDnd =
                (this.mZenModeHelper.getNotificationPolicy().state & 1) != 0;
        updateCurrentUserHasChannelsBypassingDnd(1000, true);
    }

    public final void updateBadgingEnabled() {
        if (this.mBadgingEnabled == null) {
            this.mBadgingEnabled = new SparseBooleanArray();
        }
        boolean z = false;
        for (int i = 0; i < this.mBadgingEnabled.size(); i++) {
            int keyAt = this.mBadgingEnabled.keyAt(i);
            boolean z2 = this.mBadgingEnabled.get(keyAt);
            boolean z3 = true;
            boolean z4 =
                    Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "notification_badging",
                                    1,
                                    keyAt)
                            != 0;
            this.mBadgingEnabled.put(keyAt, z4);
            if (z2 == z4) {
                z3 = false;
            }
            z |= z3;
        }
        if (z) {
            updateConfig();
        }
    }

    public final void updateBubblesEnabled() {
        if (this.mBubblesEnabled == null) {
            this.mBubblesEnabled = new SparseBooleanArray();
        }
        boolean z = false;
        for (int i = 0; i < this.mBubblesEnabled.size(); i++) {
            int keyAt = this.mBubblesEnabled.keyAt(i);
            boolean z2 = this.mBubblesEnabled.get(keyAt);
            boolean z3 = true;
            boolean z4 =
                    Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "notification_bubbles",
                                    1,
                                    keyAt)
                            == 1;
            this.mBubblesEnabled.put(keyAt, z4);
            if (z2 == z4) {
                z3 = false;
            }
            z |= z3;
        }
        if (z) {
            updateConfig();
        }
    }

    public final void updateChildrenConversationChannels(
            PackagePreferences packagePreferences,
            NotificationChannel notificationChannel,
            NotificationChannel notificationChannel2) {
        boolean z;
        if (notificationChannel.equals(notificationChannel2)
                || notificationChannel.isConversation()) {
            return;
        }
        for (NotificationChannel notificationChannel3 : packagePreferences.channels.values()) {
            if (notificationChannel3.isConversation()
                    && notificationChannel
                            .getId()
                            .equals(notificationChannel3.getParentChannelId())) {
                String str = packagePreferences.pkg;
                int i = packagePreferences.uid;
                int loggingImportance =
                        NotificationChannelLogger.getLoggingImportance(
                                notificationChannel3, notificationChannel3.getImportance());
                if ((notificationChannel3.getUserLockedFields() & 1) != 0
                        || notificationChannel.canBypassDnd()
                                == notificationChannel2.canBypassDnd()) {
                    z = false;
                } else {
                    notificationChannel3.setBypassDnd(notificationChannel2.canBypassDnd());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 2) == 0
                        && notificationChannel.getLockscreenVisibility()
                                != notificationChannel2.getLockscreenVisibility()) {
                    notificationChannel3.setLockscreenVisibility(
                            notificationChannel2.getLockscreenVisibility());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 4) == 0
                        && notificationChannel.getImportance()
                                != notificationChannel2.getImportance()) {
                    notificationChannel3.setImportance(notificationChannel2.getImportance());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 8) == 0
                        && (notificationChannel.shouldShowLights()
                                        != notificationChannel2.shouldShowLights()
                                || notificationChannel.getLightColor()
                                        != notificationChannel2.getLightColor())) {
                    notificationChannel3.enableLights(notificationChannel2.shouldShowLights());
                    notificationChannel3.setLightColor(notificationChannel2.getLightColor());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 32) == 0
                        && !Objects.equals(
                                notificationChannel.getSound(), notificationChannel2.getSound())) {
                    notificationChannel3.setSound(
                            notificationChannel2.getSound(),
                            notificationChannel2.getAudioAttributes());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 16) == 0
                        && (!Arrays.equals(
                                        notificationChannel.getVibrationPattern(),
                                        notificationChannel2.getVibrationPattern())
                                || !Objects.equals(
                                        notificationChannel.getVibrationEffect(),
                                        notificationChannel2.getVibrationEffect())
                                || notificationChannel.shouldVibrate()
                                        != notificationChannel2.shouldVibrate())) {
                    notificationChannel3.setVibrationPattern(
                            notificationChannel2.getVibrationPattern());
                    notificationChannel3.enableVibration(notificationChannel2.shouldVibrate());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 128) == 0
                        && notificationChannel.canShowBadge()
                                != notificationChannel2.canShowBadge()) {
                    notificationChannel3.setShowBadge(notificationChannel2.canShowBadge());
                    z = true;
                }
                if ((notificationChannel3.getUserLockedFields() & 256) == 0
                        && notificationChannel.getAllowBubbles()
                                != notificationChannel2.getAllowBubbles()) {
                    notificationChannel3.setAllowBubbles(notificationChannel2.getAllowBubbles());
                    z = true;
                }
                if (z) {
                    MetricsLogger.action(getChannelLog(notificationChannel3, str).setSubtype(1));
                    this.mNotificationChannelLogger.logNotificationChannelModified(
                            notificationChannel3, i, str, loggingImportance, true);
                }
            }
        }
    }

    public final void updateConfig() {
        ((NotificationManagerService.RankingHandlerWorker) this.mRankingHandler).requestSort();
    }

    public final void updateCurrentUserHasChannelsBypassingDnd(int i, boolean z) {
        ArraySet arraySet = new ArraySet();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity2 = Binder.clearCallingIdentity();
        int currentUser = ActivityManager.getCurrentUser();
        Binder.restoreCallingIdentity(clearCallingIdentity2);
        int[] profileIds = userManager.getProfileIds(currentUser, false);
        for (int i2 : profileIds) {
            arrayList.add(Integer.valueOf(i2));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        synchronized (this.mLock) {
            try {
                int size = this.mPackagePreferences.size();
                for (int i3 = 0; i3 < size; i3++) {
                    PackagePreferences packagePreferences =
                            (PackagePreferences) this.mPackagePreferences.valueAt(i3);
                    if (arrayList.contains(
                            Integer.valueOf(UserHandle.getUserId(packagePreferences.uid)))) {
                        Iterator it = packagePreferences.channels.values().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                NotificationChannel notificationChannel =
                                        (NotificationChannel) it.next();
                                if (channelIsLiveLocked(packagePreferences, notificationChannel)
                                        && notificationChannel.canBypassDnd()) {
                                    arraySet.add(
                                            new Pair(
                                                    packagePreferences.pkg,
                                                    Integer.valueOf(packagePreferences.uid)));
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            if (!this.mPermissionHelper.hasPermission(
                    ((Integer) ((Pair) arraySet.valueAt(size2)).second).intValue())) {
                arraySet.removeAt(size2);
            }
        }
        boolean z2 = arraySet.size() > 0;
        if (this.mCurrentUserHasChannelsBypassingDnd != z2) {
            this.mCurrentUserHasChannelsBypassingDnd = z2;
            updateZenPolicy(i, z2, z);
        }
    }

    public final void updateDefaultApps(int i, ArraySet arraySet, ArraySet arraySet2) {
        synchronized (this.mLock) {
            try {
                for (PackagePreferences packagePreferences : this.mPackagePreferences.values()) {
                    if (i == UserHandle.getUserId(packagePreferences.uid)
                            && arraySet != null
                            && arraySet.contains(packagePreferences.pkg)) {
                        packagePreferences.defaultAppLockedImportance = false;
                        if (!packagePreferences.fixedImportance) {
                            Iterator it = packagePreferences.channels.values().iterator();
                            while (it.hasNext()) {
                                ((NotificationChannel) it.next())
                                        .setImportanceLockedByCriticalDeviceFunction(false);
                            }
                        }
                    }
                }
                Iterator it2 = arraySet2.iterator();
                while (it2.hasNext()) {
                    Pair pair = (Pair) it2.next();
                    PackagePreferences orCreatePackagePreferencesLocked =
                            getOrCreatePackagePreferencesLocked(
                                    ((Integer) pair.second).intValue(), (String) pair.first);
                    orCreatePackagePreferencesLocked.defaultAppLockedImportance = true;
                    Iterator it3 = orCreatePackagePreferencesLocked.channels.values().iterator();
                    while (it3.hasNext()) {
                        ((NotificationChannel) it3.next())
                                .setImportanceLockedByCriticalDeviceFunction(true);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateMediaNotificationFilteringEnabled() {
        boolean z =
                Settings.Global.getInt(this.mContext.getContentResolver(), "qs_media_controls", 1)
                                > 0
                        && this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_secondaryBuiltInDisplayIsRound);
        if (z != this.mIsMediaNotificationFilteringEnabled) {
            this.mIsMediaNotificationFilteringEnabled = z;
            updateConfig();
        }
    }

    public final void updateNotificationChannel(
            String str,
            int i,
            NotificationChannel notificationChannel,
            boolean z,
            int i2,
            boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        Objects.requireNonNull(notificationChannel);
        Objects.requireNonNull(notificationChannel.getId());
        synchronized (this.mLock) {
            try {
                PackagePreferences orCreatePackagePreferencesLocked =
                        getOrCreatePackagePreferencesLocked(i, str);
                NotificationChannel notificationChannel2 =
                        (NotificationChannel)
                                orCreatePackagePreferencesLocked.channels.get(
                                        notificationChannel.getId());
                if (notificationChannel2 == null || notificationChannel2.isDeleted()) {
                    throw new IllegalArgumentException("Channel does not exist");
                }
                z3 = true;
                if (notificationChannel.getLockscreenVisibility() == 1) {
                    notificationChannel.setLockscreenVisibility(-1000);
                }
                if (z) {
                    notificationChannel.lockFields(notificationChannel2.getUserLockedFields());
                    lockFieldsForUpdateLocked(notificationChannel2, notificationChannel);
                } else {
                    notificationChannel.unlockFields(notificationChannel.getUserLockedFields());
                }
                if (notificationChannel2.isImportanceLockedByCriticalDeviceFunction()
                        && !notificationChannel2.isBlockable()
                        && notificationChannel2.getImportance() != 0) {
                    try {
                        Bundle bundle =
                                this.mPm.getApplicationInfoAsUser(str, 128, UserHandle.getUserId(i))
                                        .metaData;
                        if (bundle != null
                                && !bundle.getBoolean(
                                        "com.samsung.android.notification.blockable", false)) {
                            notificationChannel.setImportance(notificationChannel2.getImportance());
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.d(
                                "NotificationPrefHelper",
                                "NameNotFoundException pkg "
                                        + orCreatePackagePreferencesLocked.pkg);
                    }
                }
                orCreatePackagePreferencesLocked.channels.put(
                        notificationChannel.getId(), notificationChannel);
                if (onlyHasDefaultChannel(str, i)) {
                    orCreatePackagePreferencesLocked.priority =
                            notificationChannel.canBypassDnd() ? 2 : 0;
                    orCreatePackagePreferencesLocked.visibility =
                            notificationChannel.getLockscreenVisibility();
                    orCreatePackagePreferencesLocked.showBadge = notificationChannel.canShowBadge();
                    z4 = true;
                } else {
                    z4 = false;
                }
                boolean z6 = z4;
                if (!notificationChannel2.equals(notificationChannel)) {
                    MetricsLogger.action(
                            getChannelLog(notificationChannel, str).setSubtype(z ? 1 : 0));
                    this.mNotificationChannelLogger.logNotificationChannelModified(
                            notificationChannel,
                            i,
                            str,
                            NotificationChannelLogger.getLoggingImportance(
                                    notificationChannel2, notificationChannel2.getImportance()),
                            z);
                    z6 = true;
                }
                if (z
                        && SystemUiSystemPropertiesFlags.getResolver()
                                .isEnabled(
                                        SystemUiSystemPropertiesFlags.NotificationFlags
                                                .PROPAGATE_CHANNEL_UPDATES_TO_CONVERSATIONS)) {
                    updateChildrenConversationChannels(
                            orCreatePackagePreferencesLocked,
                            notificationChannel2,
                            notificationChannel);
                }
                if (notificationChannel.canBypassDnd() == this.mCurrentUserHasChannelsBypassingDnd
                        && notificationChannel2.getImportance()
                                == notificationChannel.getImportance()) {
                    z3 = false;
                    z5 = z6;
                }
                z5 = true;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z3) {
            updateCurrentUserHasChannelsBypassingDnd(i2, z2);
        }
        if (z5) {
            updateConfig();
        }
    }

    public final void updateZenPolicy(int i, boolean z, boolean z2) {
        ZenModeHelper zenModeHelper = this.mZenModeHelper;
        NotificationManager.Policy notificationPolicy = zenModeHelper.getNotificationPolicy();
        if (notificationPolicy != null) {
            zenModeHelper.setNotificationPolicy(
                    new NotificationManager.Policy(
                            notificationPolicy.priorityCategories,
                            notificationPolicy.priorityCallSenders,
                            notificationPolicy.priorityMessageSenders,
                            notificationPolicy.suppressedVisualEffects,
                            z ? 1 : 0,
                            notificationPolicy.priorityConversationSenders,
                            notificationPolicy.exceptionContactsFlag,
                            notificationPolicy.getExceptionContacts(),
                            notificationPolicy.appBypassDndFlag,
                            notificationPolicy.getAppBypassDndList()),
                    z2 ? 5 : 4,
                    i);
        } else {
            zenModeHelper.setNotificationPolicy(
                    new NotificationManager.Policy(
                            notificationPolicy.priorityCategories,
                            notificationPolicy.priorityCallSenders,
                            notificationPolicy.priorityMessageSenders,
                            notificationPolicy.suppressedVisualEffects,
                            z ? 1 : 0,
                            notificationPolicy.priorityConversationSenders),
                    z2 ? 5 : 4,
                    i);
        }
    }

    public final void writePackageXml(
            PackagePreferences packagePreferences,
            TypedXmlSerializer typedXmlSerializer,
            ArrayMap arrayMap,
            boolean z) {
        typedXmlSerializer.startTag((String) null, "package");
        typedXmlSerializer.attribute((String) null, "name", packagePreferences.pkg);
        if (arrayMap.isEmpty()) {
            int i = packagePreferences.importance;
            if (i != -1000) {
                typedXmlSerializer.attributeInt((String) null, "importance", i);
            }
        } else {
            Pair pair = new Pair(Integer.valueOf(packagePreferences.uid), packagePreferences.pkg);
            Pair pair2 = (Pair) arrayMap.get(pair);
            typedXmlSerializer.attributeInt(
                    (String) null,
                    "importance",
                    (pair2 == null || !((Boolean) pair2.first).booleanValue()) ? 0 : 3);
            arrayMap.remove(pair);
        }
        int i2 = packagePreferences.priority;
        if (i2 != 0) {
            typedXmlSerializer.attributeInt((String) null, "priority", i2);
        }
        int i3 = packagePreferences.visibility;
        if (i3 != -1000) {
            typedXmlSerializer.attributeInt(
                    (String) null,
                    LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                    i3);
        }
        int i4 = packagePreferences.bubblePreference;
        if (i4 != 0) {
            typedXmlSerializer.attributeInt((String) null, "allow_bubble", i4);
        }
        typedXmlSerializer.attributeBoolean(
                (String) null, "show_badge", packagePreferences.showBadge);
        typedXmlSerializer.attributeInt(
                (String) null, "app_user_locked_fields", packagePreferences.lockedAppFields);
        typedXmlSerializer.attributeBoolean(
                (String) null, "sent_invalid_msg", packagePreferences.hasSentInvalidMessage);
        typedXmlSerializer.attributeBoolean(
                (String) null, "sent_valid_msg", packagePreferences.hasSentValidMessage);
        typedXmlSerializer.attributeBoolean(
                (String) null, "user_demote_msg_app", packagePreferences.userDemotedMsgApp);
        typedXmlSerializer.attributeBoolean(
                (String) null, "sent_valid_bubble", packagePreferences.hasSentValidBubble);
        Flags.persistIncompleteRestoreData();
        if (packagePreferences.uid == UNKNOWN_UID) {
            typedXmlSerializer.attributeLong(
                    (String) null, "creation_time", packagePreferences.creationTime);
            typedXmlSerializer.attributeInt((String) null, "userid", packagePreferences.userId);
        }
        typedXmlSerializer.attributeBoolean(
                (String) null, "allow_edge_lighting", packagePreferences.allowEdgeLighting);
        typedXmlSerializer.attributeBoolean(
                (String) null, "allow_sub_display_noti", packagePreferences.allowSubDisplayNoti);
        typedXmlSerializer.attributeInt(
                (String) null,
                "allow_ongoing_activity_setting",
                packagePreferences.allowOngoingActivity);
        typedXmlSerializer.attributeBoolean(
                (String) null,
                "notification_alerts_enabled",
                packagePreferences.isNotificationAlertsEnabled);
        int i5 = packagePreferences.muteByWearable;
        if (i5 != -1) {
            typedXmlSerializer.attributeInt((String) null, "mute_for_wearable_app", i5);
        }
        int i6 = packagePreferences.appLockScreenVisibility;
        if (i6 != -1000) {
            typedXmlSerializer.attributeInt((String) null, "app_lockscreen_visibility", i6);
        }
        boolean z2 = packagePreferences.isAllowPopup;
        if (!z2) {
            typedXmlSerializer.attributeBoolean((String) null, "allow_popup", z2);
        }
        boolean z3 = packagePreferences.reminder;
        if (z3) {
            typedXmlSerializer.attributeBoolean((String) null, "enable_reminder", z3);
        }
        if (!z) {
            typedXmlSerializer.attributeInt((String) null, "uid", packagePreferences.uid);
        }
        if (packagePreferences.delegate != null) {
            typedXmlSerializer.startTag((String) null, "delegate");
            typedXmlSerializer.attribute((String) null, "name", packagePreferences.delegate.mPkg);
            typedXmlSerializer.attributeInt((String) null, "uid", packagePreferences.delegate.mUid);
            boolean z4 = packagePreferences.delegate.mEnabled;
            if (!z4) {
                typedXmlSerializer.attributeBoolean((String) null, "enabled", z4);
            }
            typedXmlSerializer.endTag((String) null, "delegate");
        }
        Iterator it = packagePreferences.groups.values().iterator();
        while (it.hasNext()) {
            ((NotificationChannelGroup) it.next()).writeXml(typedXmlSerializer);
        }
        for (NotificationChannel notificationChannel : packagePreferences.channels.values()) {
            if (!z) {
                notificationChannel.writeXml(typedXmlSerializer);
            } else if (!notificationChannel.isDeleted()) {
                notificationChannel.writeXmlForBackup(typedXmlSerializer, this.mContext);
            }
        }
        typedXmlSerializer.endTag((String) null, "package");
    }
}
