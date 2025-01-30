package com.android.server.usage;

import android.app.usage.ConfigurationStats;
import android.app.usage.EventList;
import android.app.usage.EventStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoInputStream;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class IntervalStats {
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

    public final class EventTracker {
        public int count;
        public long curStartTime;
        public long duration;
        public long lastEventTime;

        public void commitTime(long j) {
            long j2 = this.curStartTime;
            if (j2 != 0) {
                this.duration += j - j2;
                this.curStartTime = 0L;
            }
        }

        public void update(long j) {
            if (this.curStartTime == 0) {
                this.count++;
            }
            commitTime(j);
            this.curStartTime = j;
            this.lastEventTime = j;
        }

        public void addToEventStats(List list, int i, long j, long j2) {
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
    }

    public UsageStats getOrCreateUsageStats(String str) {
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

    public ConfigurationStats getOrCreateConfigurationStats(Configuration configuration) {
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

    public UsageEvents.Event buildEvent(String str, String str2) {
        UsageEvents.Event event = new UsageEvents.Event();
        event.mPackage = getCachedStringRef(str);
        if (str2 != null) {
            event.mClass = getCachedStringRef(str2);
        }
        return event;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0132, code lost:
    
        r5 = r0.mEventType;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0135, code lost:
    
        if (r5 == 5) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x013b, code lost:
    
        if (r5 == 8) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x013f, code lost:
    
        if (r5 == 12) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0143, code lost:
    
        if (r5 == 30) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0148, code lost:
    
        if (r0.mLocusId != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x014a, code lost:
    
        r0.mLocusId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0166, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x014f, code lost:
    
        if (r0.mNotificationChannelId != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0151, code lost:
    
        r0.mNotificationChannelId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0156, code lost:
    
        if (r0.mShortcutId != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0158, code lost:
    
        r0.mShortcutId = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x015d, code lost:
    
        if (r0.mConfiguration != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x015f, code lost:
    
        r0.mConfiguration = new android.content.res.Configuration();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UsageEvents.Event buildEvent(ProtoInputStream protoInputStream, List list) {
        UsageEvents.Event event = new UsageEvents.Event();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    event.mPackage = getCachedStringRef(protoInputStream.readString(1138166333441L));
                    break;
                case 2:
                    event.mPackage = getCachedStringRef((String) list.get(protoInputStream.readInt(1120986464258L) - 1));
                    break;
                case 3:
                    event.mClass = getCachedStringRef(protoInputStream.readString(1138166333443L));
                    break;
                case 4:
                    event.mClass = getCachedStringRef((String) list.get(protoInputStream.readInt(1120986464260L) - 1));
                    break;
                case 5:
                    event.mTimeStamp = this.beginTime + protoInputStream.readLong(1112396529669L);
                    break;
                case 6:
                    event.mFlags = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    event.mEventType = protoInputStream.readInt(1120986464263L);
                    break;
                case 8:
                    Configuration configuration = new Configuration();
                    event.mConfiguration = configuration;
                    configuration.readFromProto(protoInputStream, 1146756268040L);
                    break;
                case 9:
                    event.mShortcutId = protoInputStream.readString(1138166333449L).intern();
                    break;
                case 11:
                    event.mBucketAndReason = protoInputStream.readInt(1120986464267L);
                    break;
                case 12:
                    event.mNotificationChannelId = protoInputStream.readString(1138166333452L);
                    break;
                case 13:
                    event.mNotificationChannelId = getCachedStringRef((String) list.get(protoInputStream.readInt(1120986464269L) - 1));
                    break;
                case 14:
                    event.mInstanceId = protoInputStream.readInt(1120986464270L);
                    break;
                case 15:
                    event.mTaskRootPackage = getCachedStringRef((String) list.get(protoInputStream.readInt(1120986464271L) - 1));
                    break;
                case 16:
                    event.mTaskRootClass = getCachedStringRef((String) list.get(protoInputStream.readInt(1120986464272L) - 1));
                    break;
                case 17:
                    event.mLocusId = getCachedStringRef((String) list.get(protoInputStream.readInt(1120986464273L) - 1));
                    break;
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

    public void updateChooserCounts(String str, String str2, String str3) {
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

    public void updateConfigurationStats(Configuration configuration, long j) {
        Configuration configuration2 = this.activeConfiguration;
        if (configuration2 != null) {
            ConfigurationStats configurationStats = (ConfigurationStats) this.configurations.get(configuration2);
            configurationStats.mTotalTimeActive += j - configurationStats.mLastTimeActive;
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

    public void incrementAppLaunchCount(String str) {
        getOrCreateUsageStats(str).mAppLaunchCount++;
    }

    public void commitTime(long j) {
        this.interactiveTracker.commitTime(j);
        this.nonInteractiveTracker.commitTime(j);
        this.keyguardShownTracker.commitTime(j);
        this.keyguardHiddenTracker.commitTime(j);
    }

    public void updateScreenInteractive(long j) {
        this.interactiveTracker.update(j);
        this.nonInteractiveTracker.commitTime(j);
    }

    public void updateScreenNonInteractive(long j) {
        this.nonInteractiveTracker.update(j);
        this.interactiveTracker.commitTime(j);
    }

    public void updateKeyguardShown(long j) {
        this.keyguardShownTracker.update(j);
        this.keyguardHiddenTracker.commitTime(j);
    }

    public void updateKeyguardHidden(long j) {
        this.keyguardHiddenTracker.update(j);
        this.keyguardShownTracker.commitTime(j);
    }

    public void addEventStatsTo(List list) {
        this.interactiveTracker.addToEventStats(list, 15, this.beginTime, this.endTime);
        this.nonInteractiveTracker.addToEventStats(list, 16, this.beginTime, this.endTime);
        this.keyguardShownTracker.addToEventStats(list, 17, this.beginTime, this.endTime);
        this.keyguardHiddenTracker.addToEventStats(list, 18, this.beginTime, this.endTime);
    }

    public final String getCachedStringRef(String str) {
        int indexOf = this.mStringCache.indexOf(str);
        if (indexOf < 0) {
            this.mStringCache.add(str);
            return str;
        }
        return (String) this.mStringCache.valueAt(indexOf);
    }

    public void upgradeIfNeeded() {
        if (this.majorVersion >= 1) {
            return;
        }
        this.majorVersion = 1;
    }

    public final boolean deobfuscateUsageStats(PackagesTokenData packagesTokenData) {
        PackagesTokenData packagesTokenData2 = packagesTokenData;
        ArraySet arraySet = new ArraySet();
        int size = this.packageStatsObfuscated.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            int keyAt = this.packageStatsObfuscated.keyAt(i);
            UsageStats usageStats = (UsageStats) this.packageStatsObfuscated.valueAt(i);
            String packageString = packagesTokenData2.getPackageString(keyAt);
            usageStats.mPackageName = packageString;
            if (packageString == null) {
                arraySet.add(Integer.valueOf(keyAt));
                z = true;
            } else {
                int size2 = usageStats.mChooserCountsObfuscated.size();
                int i2 = 0;
                while (i2 < size2) {
                    ArrayMap arrayMap = new ArrayMap();
                    String string = packagesTokenData2.getString(keyAt, usageStats.mChooserCountsObfuscated.keyAt(i2));
                    if (string != null) {
                        SparseIntArray sparseIntArray = (SparseIntArray) usageStats.mChooserCountsObfuscated.valueAt(i2);
                        int size3 = sparseIntArray.size();
                        int i3 = 0;
                        while (i3 < size3) {
                            String string2 = packagesTokenData2.getString(keyAt, sparseIntArray.keyAt(i3));
                            if (string2 != null) {
                                arrayMap.put(string2, Integer.valueOf(sparseIntArray.valueAt(i3)));
                            }
                            i3++;
                            packagesTokenData2 = packagesTokenData;
                        }
                        usageStats.mChooserCounts.put(string, arrayMap);
                    }
                    i2++;
                    packagesTokenData2 = packagesTokenData;
                }
                this.packageStats.put(usageStats.mPackageName, usageStats);
            }
            i++;
            packagesTokenData2 = packagesTokenData;
        }
        if (z) {
            Slog.d("IntervalStats", "Unable to parse usage stats packages: " + Arrays.toString(arraySet.toArray()));
        }
        return z;
    }

    public final boolean deobfuscateEvents(PackagesTokenData packagesTokenData) {
        ArraySet arraySet = new ArraySet();
        boolean z = false;
        for (int size = this.events.size() - 1; size >= 0; size--) {
            UsageEvents.Event event = this.events.get(size);
            int i = event.mPackageToken;
            String packageString = packagesTokenData.getPackageString(i);
            event.mPackage = packageString;
            if (packageString == null) {
                arraySet.add(Integer.valueOf(i));
                this.events.remove(size);
            } else {
                int i2 = event.mClassToken;
                if (i2 != -1) {
                    event.mClass = packagesTokenData.getString(i, i2);
                }
                int i3 = event.mTaskRootPackageToken;
                if (i3 != -1) {
                    event.mTaskRootPackage = packagesTokenData.getString(i, i3);
                }
                int i4 = event.mTaskRootClassToken;
                if (i4 != -1) {
                    event.mTaskRootClass = packagesTokenData.getString(i, i4);
                }
                int i5 = event.mEventType;
                if (i5 != 5) {
                    if (i5 == 8) {
                        String string = packagesTokenData.getString(i, event.mShortcutIdToken);
                        event.mShortcutId = string;
                        if (string == null) {
                            Slog.v("IntervalStats", "Unable to parse shortcut " + event.mShortcutIdToken + " for package " + i);
                            this.events.remove(size);
                        }
                    } else if (i5 == 12) {
                        String string2 = packagesTokenData.getString(i, event.mNotificationChannelIdToken);
                        event.mNotificationChannelId = string2;
                        if (string2 == null) {
                            Slog.v("IntervalStats", "Unable to parse notification channel " + event.mNotificationChannelIdToken + " for package " + i);
                            this.events.remove(size);
                        }
                    } else if (i5 == 30) {
                        String string3 = packagesTokenData.getString(i, event.mLocusIdToken);
                        event.mLocusId = string3;
                        if (string3 == null) {
                            Slog.v("IntervalStats", "Unable to parse locus " + event.mLocusIdToken + " for package " + i);
                            this.events.remove(size);
                        }
                    }
                } else if (event.mConfiguration == null) {
                    event.mConfiguration = new Configuration();
                }
            }
            z = true;
        }
        if (z) {
            Slog.d("IntervalStats", "Unable to parse event packages: " + Arrays.toString(arraySet.toArray()));
        }
        return z;
    }

    public boolean deobfuscateData(PackagesTokenData packagesTokenData) {
        return deobfuscateUsageStats(packagesTokenData) || deobfuscateEvents(packagesTokenData);
    }

    public final void obfuscateUsageStatsData(PackagesTokenData packagesTokenData) {
        int packageTokenOrAdd;
        int size = this.packageStats.size();
        for (int i = 0; i < size; i++) {
            String str = (String) this.packageStats.keyAt(i);
            UsageStats usageStats = (UsageStats) this.packageStats.valueAt(i);
            if (usageStats != null && (packageTokenOrAdd = packagesTokenData.getPackageTokenOrAdd(str, usageStats.mEndTimeStamp)) != -1) {
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
    }

    public final void obfuscateEventsData(PackagesTokenData packagesTokenData) {
        for (int size = this.events.size() - 1; size >= 0; size--) {
            UsageEvents.Event event = this.events.get(size);
            if (event != null) {
                int packageTokenOrAdd = packagesTokenData.getPackageTokenOrAdd(event.mPackage, event.mTimeStamp);
                if (packageTokenOrAdd == -1) {
                    this.events.remove(size);
                } else {
                    event.mPackageToken = packageTokenOrAdd;
                    if (!TextUtils.isEmpty(event.mClass)) {
                        event.mClassToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mClass);
                    }
                    if (!TextUtils.isEmpty(event.mTaskRootPackage)) {
                        event.mTaskRootPackageToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mTaskRootPackage);
                    }
                    if (!TextUtils.isEmpty(event.mTaskRootClass)) {
                        event.mTaskRootClassToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mTaskRootClass);
                    }
                    int i = event.mEventType;
                    if (i != 8) {
                        if (i == 12) {
                            if (!TextUtils.isEmpty(event.mNotificationChannelId)) {
                                event.mNotificationChannelIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mNotificationChannelId);
                            }
                        } else if (i == 30 && !TextUtils.isEmpty(event.mLocusId)) {
                            event.mLocusIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mLocusId);
                        }
                    } else if (!TextUtils.isEmpty(event.mShortcutId)) {
                        event.mShortcutIdToken = packagesTokenData.getTokenOrAdd(packageTokenOrAdd, event.mPackage, event.mShortcutId);
                    }
                }
            }
        }
    }

    public void obfuscateData(PackagesTokenData packagesTokenData) {
        obfuscateUsageStatsData(packagesTokenData);
        obfuscateEventsData(packagesTokenData);
    }
}
