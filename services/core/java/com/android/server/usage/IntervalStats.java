package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.EventList;
import android.app.usage.EventStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IntervalStats {
    public Configuration activeConfiguration;
    public long beginTime;
    public long endTime;
    public long lastTimeSaved;
    public int majorVersion = 1;
    public int minorVersion = 1;
    public final EventTracker interactiveTracker = new EventTracker();
    public final EventTracker nonInteractiveTracker = new EventTracker();
    public final EventTracker keyguardShownTracker = new EventTracker();
    public final EventTracker keyguardHiddenTracker = new EventTracker();
    public final ArrayMap packageStats = new ArrayMap();
    public final SparseArray packageStatsObfuscated = new SparseArray();
    public final ArrayMap configurations = new ArrayMap();
    public final EventList events = new EventList();
    public final ArraySet mStringCache = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EventTracker {
        public int count;
        public long curStartTime;
        public long duration;
        public long lastEventTime;

        public final void addToEventStats(List list, int i, long j, long j2) {
            if (this.count == 0 && this.duration == 0) {
                return;
            }
            EventStats eventStats = new EventStats();
            eventStats.mEventType = i;
            eventStats.mCount = this.count;
            eventStats.mTotalTime = this.duration;
            eventStats.mLastEventTime = this.lastEventTime;
            eventStats.mBeginTimeStamp = j;
            eventStats.mEndTimeStamp = j2;
            list.add(eventStats);
        }

        public final void commitTime(long j) {
            long j2 = this.curStartTime;
            if (j2 != 0) {
                this.duration = (j - j2) + this.duration;
                this.curStartTime = 0L;
            }
        }

        public final void update(long j) {
            if (this.curStartTime == 0) {
                this.count++;
            }
            commitTime(j);
            this.curStartTime = j;
            this.lastEventTime = j;
        }
    }

    public void addEvent(UsageEvents.Event event) {
        event.mPackage = getCachedStringRef(event.mPackage);
        String str = event.mClass;
        if (str != null) {
            event.mClass = getCachedStringRef(str);
        }
        String str2 = event.mTaskRootPackage;
        if (str2 != null) {
            event.mTaskRootPackage = getCachedStringRef(str2);
        }
        String str3 = event.mTaskRootClass;
        if (str3 != null) {
            event.mTaskRootClass = getCachedStringRef(str3);
        }
        if (event.mEventType == 12) {
            event.mNotificationChannelId = getCachedStringRef(event.mNotificationChannelId);
        }
        this.events.insert(event);
        long j = event.mTimeStamp;
        if (j > this.endTime) {
            this.endTime = j;
        }
    }

    public final String getCachedStringRef(String str) {
        int indexOf = this.mStringCache.indexOf(str);
        if (indexOf >= 0) {
            return (String) this.mStringCache.valueAt(indexOf);
        }
        this.mStringCache.add(str);
        return str;
    }

    public final ConfigurationStats getOrCreateConfigurationStats(Configuration configuration) {
        ConfigurationStats configurationStats = (ConfigurationStats) this.configurations.get(configuration);
        if (configurationStats != null) {
            return configurationStats;
        }
        ConfigurationStats configurationStats2 = new ConfigurationStats();
        configurationStats2.mBeginTimeStamp = this.beginTime;
        configurationStats2.mEndTimeStamp = this.endTime;
        configurationStats2.mConfiguration = configuration;
        this.configurations.put(configuration, configurationStats2);
        return configurationStats2;
    }

    public final UsageStats getOrCreateUsageStats(String str) {
        UsageStats usageStats = (UsageStats) this.packageStats.get(str);
        if (usageStats != null) {
            return usageStats;
        }
        UsageStats usageStats2 = new UsageStats();
        String cachedStringRef = getCachedStringRef(str);
        usageStats2.mPackageName = cachedStringRef;
        usageStats2.mBeginTimeStamp = this.beginTime;
        usageStats2.mEndTimeStamp = this.endTime;
        this.packageStats.put(cachedStringRef, usageStats2);
        return usageStats2;
    }

    public final void obfuscateData(PackagesTokenData packagesTokenData) {
        int packageTokenOrAdd;
        int size = this.packageStats.size();
        for (int i = 0; i < size; i++) {
            String str = (String) this.packageStats.keyAt(i);
            UsageStats usageStats = (UsageStats) this.packageStats.valueAt(i);
            if (usageStats != null && (packageTokenOrAdd = packagesTokenData.getPackageTokenOrAdd(usageStats.mEndTimeStamp, str)) != -1) {
                usageStats.mPackageToken = packageTokenOrAdd;
                int size2 = usageStats.mChooserCounts.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str2 = (String) usageStats.mChooserCounts.keyAt(i2);
                    ArrayMap arrayMap = (ArrayMap) usageStats.mChooserCounts.valueAt(i2);
                    if (arrayMap != null) {
                        SparseIntArray sparseIntArray = new SparseIntArray();
                        int size3 = arrayMap.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            sparseIntArray.put(packagesTokenData.getTokenOrAdd(packageTokenOrAdd, str, (String) arrayMap.keyAt(i3)), ((Integer) arrayMap.valueAt(i3)).intValue());
                        }
                        usageStats.mChooserCountsObfuscated.put(packagesTokenData.getTokenOrAdd(packageTokenOrAdd, str, str2), sparseIntArray);
                    }
                }
                this.packageStatsObfuscated.put(packageTokenOrAdd, usageStats);
            }
        }
        for (int size4 = this.events.size() - 1; size4 >= 0; size4--) {
            UsageEvents.Event event = this.events.get(size4);
            if (event != null) {
                int packageTokenOrAdd2 = packagesTokenData.getPackageTokenOrAdd(event.mTimeStamp, event.mPackage);
                if (packageTokenOrAdd2 == -1) {
                    this.events.remove(size4);
                } else {
                    event.mPackageToken = packageTokenOrAdd2;
                    if (!TextUtils.isEmpty(event.mClass)) {
                        event.mClassToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, event.mClass);
                    }
                    if (!TextUtils.isEmpty(event.mTaskRootPackage)) {
                        event.mTaskRootPackageToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, event.mTaskRootPackage);
                    }
                    if (!TextUtils.isEmpty(event.mTaskRootClass)) {
                        event.mTaskRootClassToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, event.mTaskRootClass);
                    }
                    int i4 = event.mEventType;
                    if (i4 == 7) {
                        PersistableBundle persistableBundle = event.mExtras;
                        if (persistableBundle != null && persistableBundle.size() != 0) {
                            String string = event.mExtras.getString("android.app.usage.extra.EVENT_CATEGORY");
                            String string2 = event.mExtras.getString("android.app.usage.extra.EVENT_ACTION");
                            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                                UsageEvents.Event.UserInteractionEventExtrasToken userInteractionEventExtrasToken = new UsageEvents.Event.UserInteractionEventExtrasToken();
                                event.mUserInteractionExtrasToken = userInteractionEventExtrasToken;
                                userInteractionEventExtrasToken.mCategoryToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, string);
                                event.mUserInteractionExtrasToken.mActionToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, string2);
                            }
                        }
                    } else if (i4 != 8) {
                        if (i4 != 12) {
                            if (i4 == 30 && !TextUtils.isEmpty(event.mLocusId)) {
                                event.mLocusIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, event.mLocusId);
                            }
                        } else if (!TextUtils.isEmpty(event.mNotificationChannelId)) {
                            event.mNotificationChannelIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, event.mNotificationChannelId);
                        }
                    } else if (!TextUtils.isEmpty(event.mShortcutId)) {
                        event.mShortcutIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd2, event.mPackage, event.mShortcutId);
                    }
                }
            }
        }
    }

    public void update(String str, String str2, long j, int i, int i2) {
        if (i == 26 || i == 25) {
            int size = this.packageStats.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((UsageStats) this.packageStats.valueAt(i3)).update(null, j, i, i2);
            }
        } else {
            getOrCreateUsageStats(str).update(str2, j, i, i2);
        }
        if (j > this.endTime) {
            this.endTime = j;
        }
    }

    public final void updateChooserCounts(String str, String str2, String str3) {
        ArrayMap arrayMap;
        UsageStats orCreateUsageStats = getOrCreateUsageStats(str);
        if (orCreateUsageStats.mChooserCounts == null) {
            orCreateUsageStats.mChooserCounts = new ArrayMap();
        }
        int indexOfKey = orCreateUsageStats.mChooserCounts.indexOfKey(str3);
        if (indexOfKey < 0) {
            arrayMap = new ArrayMap();
            orCreateUsageStats.mChooserCounts.put(str3, arrayMap);
        } else {
            arrayMap = (ArrayMap) orCreateUsageStats.mChooserCounts.valueAt(indexOfKey);
        }
        arrayMap.put(str2, Integer.valueOf(((Integer) arrayMap.getOrDefault(str2, 0)).intValue() + 1));
    }

    public final void updateConfigurationStats(Configuration configuration, long j) {
        Configuration configuration2 = this.activeConfiguration;
        if (configuration2 != null) {
            ConfigurationStats configurationStats = (ConfigurationStats) this.configurations.get(configuration2);
            configurationStats.mTotalTimeActive = (j - configurationStats.mLastTimeActive) + configurationStats.mTotalTimeActive;
            configurationStats.mLastTimeActive = j - 1;
        }
        if (configuration != null) {
            ConfigurationStats orCreateConfigurationStats = getOrCreateConfigurationStats(configuration);
            orCreateConfigurationStats.mLastTimeActive = j;
            orCreateConfigurationStats.mActivationCount++;
            this.activeConfiguration = orCreateConfigurationStats.mConfiguration;
        }
        if (j > this.endTime) {
            this.endTime = j;
        }
    }
}
