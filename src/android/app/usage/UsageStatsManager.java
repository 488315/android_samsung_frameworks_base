package android.app.usage;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.PersistableBundle;
import android.os.PowerWhitelistManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class UsageStatsManager {
    public static final String EXTRA_EVENT_ACTION = "android.app.usage.extra.EVENT_ACTION";
    public static final String EXTRA_EVENT_CATEGORY = "android.app.usage.extra.EVENT_CATEGORY";

    @SystemApi
    public static final String EXTRA_OBSERVER_ID = "android.app.usage.extra.OBSERVER_ID";

    @SystemApi
    public static final String EXTRA_TIME_LIMIT = "android.app.usage.extra.TIME_LIMIT";

    @SystemApi
    public static final String EXTRA_TIME_USED = "android.app.usage.extra.TIME_USED";
    public static final int INTERVAL_BEST = 4;
    public static final int INTERVAL_COUNT = 4;
    public static final int INTERVAL_DAILY = 0;
    public static final int INTERVAL_MONTHLY = 2;
    public static final int INTERVAL_WEEKLY = 1;
    public static final int INTERVAL_YEARLY = 3;
    public static final int REASON_MAIN_DEFAULT = 256;
    public static final int REASON_MAIN_FORCED_BY_MARS = 1792;
    public static final int REASON_MAIN_FORCED_BY_SYSTEM = 1536;
    public static final int REASON_MAIN_FORCED_BY_USER = 1024;
    public static final int REASON_MAIN_MASK = 65280;
    public static final int REASON_MAIN_PREDICTED = 1280;
    public static final int REASON_MAIN_TIMEOUT = 512;
    public static final int REASON_MAIN_USAGE = 768;
    public static final int REASON_SUB_DEFAULT_APP_RESTORED = 2;
    public static final int REASON_SUB_DEFAULT_APP_UPDATE = 1;
    public static final int REASON_SUB_DEFAULT_UNDEFINED = 0;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_ABUSE = 2;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_BACKGROUND_RESOURCE_USAGE = 1;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_BUGGY = 4;
    public static final int REASON_SUB_FORCED_SYSTEM_FLAG_UNDEFINED = 0;
    public static final int REASON_SUB_FORCED_USER_FLAG_INTERACTION = 2;
    public static final int REASON_SUB_MASK = 255;
    public static final int REASON_SUB_PREDICTED_RESTORED = 1;
    public static final int REASON_SUB_USAGE_ACTIVE_TIMEOUT = 7;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_SCHEDULED_DOZE = 12;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_SCHEDULED_NON_DOZE = 11;
    public static final int REASON_SUB_USAGE_EXEMPTED_SYNC_START = 13;
    public static final int REASON_SUB_USAGE_FOREGROUND_SERVICE_START = 15;
    public static final int REASON_SUB_USAGE_MOVE_TO_BACKGROUND = 5;
    public static final int REASON_SUB_USAGE_MOVE_TO_FOREGROUND = 4;
    public static final int REASON_SUB_USAGE_NOTIFICATION_SEEN = 2;
    public static final int REASON_SUB_USAGE_SLICE_PINNED = 9;
    public static final int REASON_SUB_USAGE_SLICE_PINNED_PRIV = 10;
    public static final int REASON_SUB_USAGE_SYNC_ADAPTER = 8;
    public static final int REASON_SUB_USAGE_SYSTEM_INTERACTION = 1;
    public static final int REASON_SUB_USAGE_SYSTEM_UPDATE = 6;
    public static final int REASON_SUB_USAGE_UNEXEMPTED_SYNC_SCHEDULED = 14;
    public static final int REASON_SUB_USAGE_USER_INTERACTION = 3;
    public static final int STANDBY_BUCKET_ACTIVE = 10;

    @SystemApi
    public static final int STANDBY_BUCKET_EXEMPTED = 5;
    public static final int STANDBY_BUCKET_FREQUENT = 30;

    @SystemApi
    public static final int STANDBY_BUCKET_NEVER = 50;
    public static final int STANDBY_BUCKET_RARE = 40;
    public static final int STANDBY_BUCKET_RESTRICTED = 45;
    public static final int STANDBY_BUCKET_WORKING_SET = 20;

    @SystemApi
    public static final int USAGE_SOURCE_CURRENT_ACTIVITY = 2;

    @SystemApi
    public static final int USAGE_SOURCE_TASK_ROOT_ACTIVITY = 1;
    private static final UsageEvents sEmptyResults = new UsageEvents();
    private final Context mContext;
    private final IUsageStatsManager mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForcedReasons {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StandbyBuckets {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsageSource {
    }

    public UsageStatsManager(Context context, IUsageStatsManager iUsageStatsManager) {
        this.mContext = context;
        this.mService = iUsageStatsManager;
    }

    public List<UsageStats> queryUsageStats(int i, long j, long j2) {
        try {
            ParceledListSlice parceledListSliceQueryUsageStats = this.mService.queryUsageStats(i, j, j2, this.mContext.getOpPackageName(), this.mContext.getUserId());
            if (parceledListSliceQueryUsageStats != null) {
                return parceledListSliceQueryUsageStats.getList();
            }
        } catch (RemoteException unused) {
        }
        return Collections.EMPTY_LIST;
    }

    public List<ConfigurationStats> queryConfigurations(int i, long j, long j2) {
        try {
            ParceledListSlice parceledListSliceQueryConfigurationStats = this.mService.queryConfigurationStats(i, j, j2, this.mContext.getOpPackageName());
            if (parceledListSliceQueryConfigurationStats != null) {
                return parceledListSliceQueryConfigurationStats.getList();
            }
        } catch (RemoteException unused) {
        }
        return Collections.EMPTY_LIST;
    }

    public List<EventStats> queryEventStats(int i, long j, long j2) {
        try {
            ParceledListSlice parceledListSliceQueryEventStats = this.mService.queryEventStats(i, j, j2, this.mContext.getOpPackageName());
            if (parceledListSliceQueryEventStats != null) {
                return parceledListSliceQueryEventStats.getList();
            }
        } catch (RemoteException unused) {
        }
        return Collections.EMPTY_LIST;
    }

    public UsageEvents queryEvents(long j, long j2) {
        try {
            UsageEvents usageEventsQueryEvents = this.mService.queryEvents(j, j2, this.mContext.getOpPackageName());
            if (usageEventsQueryEvents != null) {
                return usageEventsQueryEvents;
            }
        } catch (RemoteException unused) {
        }
        return sEmptyResults;
    }

    public UsageEvents queryEvents(UsageEventsQuery usageEventsQuery) {
        try {
            UsageEvents usageEventsQueryEventsWithFilter = this.mService.queryEventsWithFilter(usageEventsQuery, this.mContext.getOpPackageName());
            if (usageEventsQueryEventsWithFilter != null) {
                return usageEventsQueryEventsWithFilter;
            }
        } catch (RemoteException unused) {
        }
        return sEmptyResults;
    }

    public UsageEvents queryEventsForSelf(long j, long j2) {
        try {
            UsageEvents usageEventsQueryEventsForPackage = this.mService.queryEventsForPackage(j, j2, this.mContext.getOpPackageName());
            if (usageEventsQueryEventsForPackage != null) {
                return usageEventsQueryEventsForPackage;
            }
        } catch (RemoteException unused) {
        }
        return sEmptyResults;
    }

    public Map<String, UsageStats> queryAndAggregateUsageStats(long j, long j2) {
        List<UsageStats> listQueryUsageStats = queryUsageStats(4, j, j2);
        if (listQueryUsageStats.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        ArrayMap arrayMap = new ArrayMap();
        int size = listQueryUsageStats.size();
        for (int i = 0; i < size; i++) {
            UsageStats usageStats = listQueryUsageStats.get(i);
            UsageStats usageStats2 = (UsageStats) arrayMap.get(usageStats.getPackageName());
            if (usageStats2 == null) {
                arrayMap.put(usageStats.mPackageName, usageStats);
            } else {
                usageStats2.add(usageStats);
            }
        }
        return arrayMap;
    }

    public boolean isAppStandbyEnabled() {
        try {
            return this.mService.isAppStandbyEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAppInactive(String str) {
        try {
            return this.mService.isAppInactive(str, this.mContext.getUserId(), this.mContext.getOpPackageName());
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setAppInactive(String str, boolean z) {
        try {
            this.mService.setAppInactive(str, z, this.mContext.getUserId());
        } catch (RemoteException unused) {
        }
    }

    public int getAppStandbyBucket() {
        try {
            return this.mService.getAppStandbyBucket(this.mContext.getOpPackageName(), this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException unused) {
            return 10;
        }
    }

    @SystemApi
    public int getAppStandbyBucket(String str) {
        try {
            return this.mService.getAppStandbyBucket(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException unused) {
            return 10;
        }
    }

    @SystemApi
    public void setAppStandbyBucket(String str, int i) {
        try {
            this.mService.setAppStandbyBucket(str, i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Map<String, Integer> getAppStandbyBuckets() {
        try {
            List list = this.mService.getAppStandbyBuckets(this.mContext.getOpPackageName(), this.mContext.getUserId()).getList();
            ArrayMap arrayMap = new ArrayMap();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                AppStandbyInfo appStandbyInfo = (AppStandbyInfo) list.get(i);
                arrayMap.put(appStandbyInfo.mPackageName, Integer.valueOf(appStandbyInfo.mStandbyBucket));
            }
            return arrayMap;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAppStandbyBuckets(Map<String, Integer> map) {
        if (map == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            arrayList.add(new AppStandbyInfo(entry.getKey(), entry.getValue().intValue()));
        }
        try {
            this.mService.setAppStandbyBuckets(new ParceledListSlice(arrayList), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAppMinStandbyBucket(String str) {
        try {
            return this.mService.getAppMinStandbyBucket(str, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setEstimatedLaunchTimeMillis(String str, long j) {
        if (str == null) {
            throw new NullPointerException("package name cannot be null");
        }
        if (j <= 0) {
            throw new IllegalArgumentException("estimated launch time must be positive");
        }
        try {
            this.mService.setEstimatedLaunchTime(str, j, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setEstimatedLaunchTimesMillis(Map<String, Long> map) {
        if (map == null) {
            throw new NullPointerException("estimatedLaunchTimesMillis cannot be null");
        }
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                throw new NullPointerException("package name cannot be null");
            }
            Long value = entry.getValue();
            if (value == null || value.longValue() <= 0) {
                throw new IllegalArgumentException("estimated launch time must be positive");
            }
            arrayList.add(new AppLaunchEstimateInfo(key, value.longValue()));
        }
        try {
            this.mService.setEstimatedLaunchTimes(new ParceledListSlice(arrayList), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerAppUsageObserver(int i, String[] strArr, long j, TimeUnit timeUnit, PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageObserver(i, strArr, timeUnit.toMillis(j), pendingIntent, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAppUsageObserver(int i) {
        try {
            this.mService.unregisterAppUsageObserver(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerUsageSessionObserver(int i, String[] strArr, Duration duration, Duration duration2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        try {
            this.mService.registerUsageSessionObserver(i, strArr, duration.toMillis(), duration2.toMillis(), pendingIntent, pendingIntent2, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterUsageSessionObserver(int i) {
        try {
            this.mService.unregisterUsageSessionObserver(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerAppUsageLimitObserver(int i, String[] strArr, Duration duration, Duration duration2, PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageLimitObserver(i, strArr, duration.toMillis(), duration2.toMillis(), pendingIntent, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAppUsageLimitObserver(int i) {
        try {
            this.mService.unregisterAppUsageLimitObserver(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserInteraction(String str, int i) {
        try {
            this.mService.reportUserInteraction(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserInteraction(String str, int i, PersistableBundle persistableBundle) {
        try {
            this.mService.reportUserInteractionWithBundle(str, i, persistableBundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void reportUsageStart(Activity activity, String str) {
        try {
            this.mService.reportUsageStart(activity.getActivityToken(), str, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void reportUsageStart(Activity activity, String str, long j) {
        try {
            this.mService.reportPastUsageStart(activity.getActivityToken(), str, j, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void reportUsageStop(Activity activity, String str) {
        try {
            this.mService.reportUsageStop(activity.getActivityToken(), str, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getUsageSource() {
        try {
            return this.mService.getUsageSource();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceUsageSourceSettingRead() {
        try {
            this.mService.forceUsageSourceSettingRead();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String reasonToString(int i) {
        int i2 = i & 255;
        StringBuilder sb = new StringBuilder();
        int i3 = i & 65280;
        if (i3 == 256) {
            sb.append(XmlTags.ATTR_DESCRIPTION);
            if (i2 == 1) {
                sb.append("-au");
            } else if (i2 == 2) {
                sb.append("-ar");
            }
        } else if (i3 == 512) {
            sb.append("t");
        } else if (i3 == 768) {
            sb.append(XmlTags.ATTR_UID);
            switch (i2) {
                case 1:
                    sb.append("-si");
                    break;
                case 2:
                    sb.append("-ns");
                    break;
                case 3:
                    sb.append("-ui");
                    break;
                case 4:
                    sb.append("-mf");
                    break;
                case 5:
                    sb.append("-mb");
                    break;
                case 6:
                    sb.append("-su");
                    break;
                case 7:
                    sb.append("-at");
                    break;
                case 8:
                    sb.append("-sa");
                    break;
                case 9:
                    sb.append("-lp");
                    break;
                case 10:
                    sb.append("-lv");
                    break;
                case 11:
                    sb.append("-en");
                    break;
                case 12:
                    sb.append("-ed");
                    break;
                case 13:
                    sb.append("-es");
                    break;
                case 14:
                    sb.append("-uss");
                    break;
                case 15:
                    sb.append("-fss");
                    break;
            }
        } else if (i3 == 1024) {
            sb.append(FullBackup.FILES_TREE_TOKEN);
            if (i2 > 0) {
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                sb.append(Integer.toBinaryString(i2));
            }
        } else if (i3 == 1280) {
            sb.append("p");
            if (i2 == 1) {
                sb.append("-r");
            }
        } else if (i3 == 1536) {
            sb.append(XmlTags.TAG_SESSION);
            if (i2 > 0) {
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                sb.append(Integer.toBinaryString(i2));
            }
        }
        return sb.toString();
    }

    public static String usageSourceToString(int i) {
        if (i == 1) {
            return "TASK_ROOT_ACTIVITY";
        }
        if (i == 2) {
            return "CURRENT_ACTIVITY";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String standbyBucketToString(int i) {
        if (i == 5) {
            return "EXEMPTED";
        }
        if (i == 10) {
            return "ACTIVE";
        }
        if (i == 20) {
            return "WORKING_SET";
        }
        if (i == 30) {
            return "FREQUENT";
        }
        if (i == 40) {
            return "RARE";
        }
        if (i == 45) {
            return "RESTRICTED";
        }
        if (i == 50) {
            return "NEVER";
        }
        return String.valueOf(i);
    }

    @SystemApi
    @Deprecated
    public void whitelistAppTemporarily(String str, long j, UserHandle userHandle) {
        ((PowerWhitelistManager) this.mContext.getSystemService(PowerWhitelistManager.class)).whitelistAppTemporarily(str, j);
    }

    @SystemApi
    public void onCarrierPrivilegedAppsChanged() {
        try {
            this.mService.onCarrierPrivilegedAppsChanged();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) {
        try {
            this.mService.reportChooserSelection(str, i, str2, strArr, str3);
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    public long getLastTimeAnyComponentUsed(String str) {
        try {
            return this.mService.getLastTimeAnyComponentUsed(str, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<BroadcastResponseStats> queryBroadcastResponseStats(String str, long j) {
        try {
            return this.mService.queryBroadcastResponseStats(str, j, this.mContext.getOpPackageName(), this.mContext.getUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void clearBroadcastResponseStats(String str, long j) {
        try {
            this.mService.clearBroadcastResponseStats(str, j, this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearBroadcastEvents() {
        try {
            this.mService.clearBroadcastEvents(this.mContext.getOpPackageName(), this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPackageExemptedFromBroadcastResponseStats(String str) {
        try {
            return this.mService.isPackageExemptedFromBroadcastResponseStats(str, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getAppStandbyConstant(String str) {
        try {
            return this.mService.getAppStandbyConstant(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRegisterAppUsageObserver(int i, String[] strArr, long j, TimeUnit timeUnit, PendingIntent pendingIntent) {
        try {
            this.mService.registerAppUsageObserver(i, strArr, timeUnit.toMillis(j), pendingIntent, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semUnregisterAppUsageObserver(int i) {
        try {
            this.mService.unregisterAppUsageObserver(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteUsageStats() {
        try {
            this.mService.deleteUsageStats();
        } catch (RemoteException unused) {
        }
    }

    public void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) {
        try {
            this.mService.registerUsageStatsWatcher(iUsageStatsWatcher);
        } catch (RemoteException unused) {
        }
    }

    public void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher, List<ComponentName> list) {
        try {
            this.mService.registerUsageStatsWatcherWithComponent(iUsageStatsWatcher, list);
        } catch (RemoteException unused) {
        }
    }

    public void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) {
        try {
            this.mService.unregisterUsageStatsWatcher(iUsageStatsWatcher);
        } catch (RemoteException unused) {
        }
    }
}
