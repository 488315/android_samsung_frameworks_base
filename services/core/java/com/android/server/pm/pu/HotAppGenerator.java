package com.android.server.pm.pu;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public final class HotAppGenerator {
    public static final long DAY_INTERVAL;
    public static final long HOUR_INTERVAL = TimeUnit.HOURS.toMillis(1);
    public static final long MONTH_INTERVAL;
    public static final long WEEK_INTERVAL;
    public final Context mContext;
    public final PackageManager mPm;
    public final UsageStatsManager mUsageStatsManager;

    public final class ComparablePackage implements Comparable {
        public final String name;
        public final long weight;

        public ComparablePackage(long j, String str) {
            this.name = str;
            this.weight = j;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            ComparablePackage comparablePackage = (ComparablePackage) obj;
            int compare = Long.compare(this.weight, comparablePackage.weight);
            return compare == 0 ? this.name.compareTo(comparablePackage.name) : compare;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof ComparablePackage)) {
                return false;
            }
            ComparablePackage comparablePackage = (ComparablePackage) obj;
            int compare = Long.compare(this.weight, comparablePackage.weight);
            if (compare == 0) {
                compare = this.name.compareTo(comparablePackage.name);
            }
            return compare == 0;
        }

        public final int hashCode() {
            return Objects.hash(this.name, Long.valueOf(this.weight));
        }
    }

    static {
        TimeUnit timeUnit = TimeUnit.DAYS;
        DAY_INTERVAL = timeUnit.toMillis(1L);
        WEEK_INTERVAL = timeUnit.toMillis(7L);
        MONTH_INTERVAL = timeUnit.toMillis(28L);
    }

    public HotAppGenerator(Context context) {
        this.mContext = context;
        this.mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        this.mPm = context.getPackageManager();
    }

    public static Map getInvertedOrder(TreeSet treeSet) {
        HashMap hashMap = new HashMap();
        Iterator it = treeSet.descendingSet().iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            hashMap.put(((ComparablePackage) it.next()).name, Integer.valueOf(i));
        }
        return hashMap;
    }

    public final void queryAndParseUsageStats(long j, long j2, Set set, BiConsumer biConsumer) {
        Map<String, UsageStats> queryAndAggregateUsageStats =
                this.mUsageStatsManager.queryAndAggregateUsageStats(j, j2);
        if (queryAndAggregateUsageStats == null) {
            return;
        }
        TreeSet treeSet = new TreeSet();
        TreeSet treeSet2 = new TreeSet();
        for (UsageStats usageStats : queryAndAggregateUsageStats.values()) {
            String packageName = usageStats.getPackageName();
            if (((HashSet) set).contains(packageName)) {
                treeSet.add(
                        new ComparablePackage(usageStats.getTotalTimeInForeground(), packageName));
                treeSet2.add(new ComparablePackage(usageStats.mLaunchCount, packageName));
            }
        }
        biConsumer.accept(getInvertedOrder(treeSet), getInvertedOrder(treeSet2));
    }
}
