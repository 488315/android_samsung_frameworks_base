package android.app.usage;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseIntArray;

/* loaded from: classes.dex */
public final class UsageStats implements Parcelable {
    public static final Parcelable.Creator<UsageStats> CREATOR = new Parcelable.Creator<UsageStats>() { // from class: android.app.usage.UsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsageStats createFromParcel(Parcel parcel) {
            UsageStats usageStats = new UsageStats();
            usageStats.mPackageName = parcel.readString();
            usageStats.mBeginTimeStamp = parcel.readLong();
            usageStats.mEndTimeStamp = parcel.readLong();
            usageStats.mLastTimeUsed = parcel.readLong();
            usageStats.mLastTimeVisible = parcel.readLong();
            usageStats.mLastTimeComponentUsed = parcel.readLong();
            usageStats.mLastTimeForegroundServiceUsed = parcel.readLong();
            usageStats.mTotalTimeInForeground = parcel.readLong();
            usageStats.mTotalTimeVisible = parcel.readLong();
            usageStats.mTotalTimeForegroundServiceUsed = parcel.readLong();
            usageStats.mLaunchCount = parcel.readInt();
            usageStats.mAppLaunchCount = parcel.readInt();
            usageStats.mLastEvent = parcel.readInt();
            Bundle bundle = parcel.readBundle();
            if (bundle != null) {
                usageStats.mChooserCounts = new ArrayMap<>();
                for (String str : bundle.keySet()) {
                    if (!usageStats.mChooserCounts.containsKey(str)) {
                        usageStats.mChooserCounts.put(str, new ArrayMap<>());
                    }
                    Bundle bundle2 = bundle.getBundle(str);
                    if (bundle2 != null) {
                        for (String str2 : bundle2.keySet()) {
                            int i = bundle2.getInt(str2);
                            if (i > 0) {
                                usageStats.mChooserCounts.get(str).put(str2, Integer.valueOf(i));
                            }
                        }
                    }
                }
            }
            readSparseIntArray(parcel, usageStats.mActivities);
            readBundleToEventMap(parcel.readBundle(), usageStats.mForegroundServices);
            return usageStats;
        }

        private void readSparseIntArray(Parcel parcel, SparseIntArray sparseIntArray) {
            int i = parcel.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                sparseIntArray.put(parcel.readInt(), parcel.readInt());
            }
        }

        private void readBundleToEventMap(Bundle bundle, ArrayMap<String, Integer> arrayMap) {
            if (bundle != null) {
                for (String str : bundle.keySet()) {
                    arrayMap.put(str, Integer.valueOf(bundle.getInt(str)));
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsageStats[] newArray(int i) {
            return new UsageStats[i];
        }
    };
    public SparseIntArray mActivities;
    public int mAppLaunchCount;
    public long mBeginTimeStamp;
    public ArrayMap<String, ArrayMap<String, Integer>> mChooserCounts;
    public SparseArray<SparseIntArray> mChooserCountsObfuscated;
    public long mEndTimeStamp;
    public ArrayMap<String, Integer> mForegroundServices;

    @Deprecated
    public int mLastEvent;
    public long mLastTimeComponentUsed;
    public long mLastTimeForegroundServiceUsed;
    public long mLastTimeUsed;
    public long mLastTimeVisible;
    public int mLaunchCount;
    public String mPackageName;
    public int mPackageToken;
    public long mTotalTimeForegroundServiceUsed;
    public long mTotalTimeInForeground;
    public long mTotalTimeVisible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UsageStats() {
        this.mPackageToken = -1;
        this.mActivities = new SparseIntArray();
        this.mForegroundServices = new ArrayMap<>();
        this.mChooserCounts = new ArrayMap<>();
        this.mChooserCountsObfuscated = new SparseArray<>();
    }

    public UsageStats(UsageStats usageStats) {
        this.mPackageToken = -1;
        this.mActivities = new SparseIntArray();
        this.mForegroundServices = new ArrayMap<>();
        this.mChooserCounts = new ArrayMap<>();
        this.mChooserCountsObfuscated = new SparseArray<>();
        this.mPackageName = usageStats.mPackageName;
        this.mBeginTimeStamp = usageStats.mBeginTimeStamp;
        this.mEndTimeStamp = usageStats.mEndTimeStamp;
        this.mLastTimeUsed = usageStats.mLastTimeUsed;
        this.mLastTimeVisible = usageStats.mLastTimeVisible;
        this.mLastTimeComponentUsed = usageStats.mLastTimeComponentUsed;
        this.mLastTimeForegroundServiceUsed = usageStats.mLastTimeForegroundServiceUsed;
        this.mTotalTimeInForeground = usageStats.mTotalTimeInForeground;
        this.mTotalTimeVisible = usageStats.mTotalTimeVisible;
        this.mTotalTimeForegroundServiceUsed = usageStats.mTotalTimeForegroundServiceUsed;
        this.mLaunchCount = usageStats.mLaunchCount;
        this.mAppLaunchCount = usageStats.mAppLaunchCount;
        this.mLastEvent = usageStats.mLastEvent;
        this.mActivities = usageStats.mActivities.m5539clone();
        this.mForegroundServices = new ArrayMap<>(usageStats.mForegroundServices);
        this.mChooserCounts = new ArrayMap<>(usageStats.mChooserCounts);
    }

    public UsageStats getObfuscatedForInstantApp() {
        UsageStats usageStats = new UsageStats(this);
        usageStats.mPackageName = UsageEvents.INSTANT_APP_PACKAGE_NAME;
        return usageStats;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastTimeUsed() {
        return this.mLastTimeUsed;
    }

    public long getLastTimeVisible() {
        return this.mLastTimeVisible;
    }

    public long getTotalTimeInForeground() {
        return this.mTotalTimeInForeground;
    }

    public long getTotalTimeVisible() {
        return this.mTotalTimeVisible;
    }

    public long getLastTimeForegroundServiceUsed() {
        return this.mLastTimeForegroundServiceUsed;
    }

    public long getTotalTimeForegroundServiceUsed() {
        return this.mTotalTimeForegroundServiceUsed;
    }

    @SystemApi
    public long getLastTimeAnyComponentUsed() {
        return this.mLastTimeComponentUsed;
    }

    public long getLastTimePackageUsed() {
        return Math.max(this.mLastTimeUsed, Math.max(this.mLastTimeVisible, Math.max(this.mLastTimeForegroundServiceUsed, this.mLastTimeComponentUsed)));
    }

    @SystemApi
    public int getAppLaunchCount() {
        return this.mAppLaunchCount;
    }

    private void mergeEventMap(SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2) {
        int size = sparseIntArray2.size();
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseIntArray2.keyAt(i);
            int iValueAt = sparseIntArray2.valueAt(i);
            int iIndexOfKey = sparseIntArray.indexOfKey(iKeyAt);
            if (iIndexOfKey >= 0) {
                sparseIntArray.put(iKeyAt, Math.max(sparseIntArray.valueAt(iIndexOfKey), iValueAt));
            } else {
                sparseIntArray.put(iKeyAt, iValueAt);
            }
        }
    }

    private void mergeEventMap(ArrayMap<String, Integer> arrayMap, ArrayMap<String, Integer> arrayMap2) {
        int size = arrayMap2.size();
        for (int i = 0; i < size; i++) {
            String strKeyAt = arrayMap2.keyAt(i);
            Integer numValueAt = arrayMap2.valueAt(i);
            if (arrayMap.containsKey(strKeyAt)) {
                arrayMap.put(strKeyAt, Integer.valueOf(Math.max(arrayMap.get(strKeyAt).intValue(), numValueAt.intValue())));
            } else {
                arrayMap.put(strKeyAt, numValueAt);
            }
        }
    }

    public void add(UsageStats usageStats) {
        if (!this.mPackageName.equals(usageStats.mPackageName)) {
            throw new IllegalArgumentException("Can't merge UsageStats for package '" + this.mPackageName + "' with UsageStats for package '" + usageStats.mPackageName + "'.");
        }
        if (usageStats.mBeginTimeStamp > this.mBeginTimeStamp) {
            mergeEventMap(this.mActivities, usageStats.mActivities);
            mergeEventMap(this.mForegroundServices, usageStats.mForegroundServices);
            this.mLastTimeUsed = Math.max(this.mLastTimeUsed, usageStats.mLastTimeUsed);
            this.mLastTimeVisible = Math.max(this.mLastTimeVisible, usageStats.mLastTimeVisible);
            this.mLastTimeComponentUsed = Math.max(this.mLastTimeComponentUsed, usageStats.mLastTimeComponentUsed);
            this.mLastTimeForegroundServiceUsed = Math.max(this.mLastTimeForegroundServiceUsed, usageStats.mLastTimeForegroundServiceUsed);
        }
        this.mBeginTimeStamp = Math.min(this.mBeginTimeStamp, usageStats.mBeginTimeStamp);
        this.mEndTimeStamp = Math.max(this.mEndTimeStamp, usageStats.mEndTimeStamp);
        this.mTotalTimeInForeground += usageStats.mTotalTimeInForeground;
        this.mTotalTimeVisible += usageStats.mTotalTimeVisible;
        this.mTotalTimeForegroundServiceUsed += usageStats.mTotalTimeForegroundServiceUsed;
        this.mLaunchCount += usageStats.mLaunchCount;
        this.mAppLaunchCount += usageStats.mAppLaunchCount;
        if (this.mChooserCounts == null) {
            this.mChooserCounts = usageStats.mChooserCounts;
            return;
        }
        ArrayMap<String, ArrayMap<String, Integer>> arrayMap = usageStats.mChooserCounts;
        if (arrayMap != null) {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                String strKeyAt = usageStats.mChooserCounts.keyAt(i);
                ArrayMap<String, Integer> arrayMapValueAt = usageStats.mChooserCounts.valueAt(i);
                if (!this.mChooserCounts.containsKey(strKeyAt) || this.mChooserCounts.get(strKeyAt) == null) {
                    this.mChooserCounts.put(strKeyAt, arrayMapValueAt);
                } else {
                    int size2 = arrayMapValueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        String strKeyAt2 = arrayMapValueAt.keyAt(i2);
                        this.mChooserCounts.get(strKeyAt).put(strKeyAt2, Integer.valueOf(this.mChooserCounts.get(strKeyAt).getOrDefault(strKeyAt2, 0).intValue() + arrayMapValueAt.valueAt(i2).intValue()));
                    }
                }
            }
        }
    }

    private boolean hasForegroundActivity() {
        int size = this.mActivities.size();
        for (int i = 0; i < size; i++) {
            if (this.mActivities.valueAt(i) == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVisibleActivity() {
        int size = this.mActivities.size();
        for (int i = 0; i < size; i++) {
            int iValueAt = this.mActivities.valueAt(i);
            if (iValueAt == 1 || iValueAt == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean anyForegroundServiceStarted() {
        return !this.mForegroundServices.isEmpty();
    }

    private void incrementTimeUsed(long j) {
        long j2 = this.mLastTimeUsed;
        if (j > j2) {
            this.mTotalTimeInForeground += j - j2;
            this.mLastTimeUsed = j;
        }
    }

    private void incrementTimeVisible(long j) {
        long j2 = this.mLastTimeVisible;
        if (j > j2) {
            this.mTotalTimeVisible += j - j2;
            this.mLastTimeVisible = j;
        }
    }

    private void incrementServiceTimeUsed(long j) {
        long j2 = this.mLastTimeForegroundServiceUsed;
        if (j > j2) {
            this.mTotalTimeForegroundServiceUsed += j - j2;
            this.mLastTimeForegroundServiceUsed = j;
        }
    }

    private void updateActivity(String str, long j, int i, int i2) {
        if (i == 1 || i == 2 || i == 23 || i == 24) {
            int iIndexOfKey = this.mActivities.indexOfKey(i2);
            if (iIndexOfKey >= 0) {
                int iValueAt = this.mActivities.valueAt(iIndexOfKey);
                if (iValueAt == 1) {
                    incrementTimeUsed(j);
                    incrementTimeVisible(j);
                } else if (iValueAt == 2) {
                    incrementTimeVisible(j);
                }
            }
            if (i == 1) {
                if (!hasVisibleActivity()) {
                    this.mLastTimeUsed = j;
                    this.mLastTimeVisible = j;
                } else if (!hasForegroundActivity()) {
                    this.mLastTimeUsed = j;
                }
                this.mActivities.put(i2, i);
                return;
            }
            if (i == 2) {
                if (!hasVisibleActivity()) {
                    this.mLastTimeVisible = j;
                }
                this.mActivities.put(i2, i);
            } else if (i == 23 || i == 24) {
                this.mActivities.delete(i2);
            }
        }
    }

    private void updateForegroundService(String str, long j, int i) {
        int iIntValue;
        if (i == 20 || i == 19) {
            Integer num = this.mForegroundServices.get(str);
            if (num != null && ((iIntValue = num.intValue()) == 19 || iIntValue == 21)) {
                incrementServiceTimeUsed(j);
            }
            if (i != 19) {
                if (i != 20) {
                    return;
                }
                this.mForegroundServices.remove(str);
            } else {
                if (!anyForegroundServiceStarted()) {
                    this.mLastTimeForegroundServiceUsed = j;
                }
                this.mForegroundServices.put(str, Integer.valueOf(i));
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void update(String str, long j, int i, int i2) {
        UsageStats usageStats;
        long j2;
        int i3;
        if (i == 1 || i == 2) {
            usageStats = this;
            j2 = j;
            i3 = i;
            usageStats.updateActivity(str, j2, i3, i2);
        } else {
            if (i == 3) {
                if (hasForegroundActivity()) {
                    incrementTimeUsed(j);
                }
                if (hasVisibleActivity()) {
                    incrementTimeVisible(j);
                }
            } else if (i == 7) {
                if (hasForegroundActivity()) {
                    incrementTimeUsed(j);
                } else {
                    this.mLastTimeUsed = j;
                }
                if (hasVisibleActivity()) {
                    incrementTimeVisible(j);
                } else {
                    this.mLastTimeVisible = j;
                }
            } else if (i != 31) {
                switch (i) {
                    case 19:
                    case 20:
                        updateForegroundService(str, j, i);
                        break;
                    case 21:
                        this.mLastTimeForegroundServiceUsed = j;
                        this.mForegroundServices.put(str, Integer.valueOf(i));
                        break;
                    case 22:
                        if (anyForegroundServiceStarted()) {
                            incrementServiceTimeUsed(j);
                            break;
                        }
                        break;
                    case 25:
                    case 26:
                        if (hasForegroundActivity()) {
                            incrementTimeUsed(j);
                        }
                        if (hasVisibleActivity()) {
                            incrementTimeVisible(j);
                        }
                        if (anyForegroundServiceStarted()) {
                            incrementServiceTimeUsed(j);
                            break;
                        }
                        break;
                }
            } else {
                this.mLastTimeComponentUsed = j;
            }
            usageStats = this;
            j2 = j;
            i3 = i;
        }
        usageStats.mEndTimeStamp = j2;
        if (i3 == 1) {
            usageStats.mLaunchCount++;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastTimeUsed);
        parcel.writeLong(this.mLastTimeVisible);
        parcel.writeLong(this.mLastTimeComponentUsed);
        parcel.writeLong(this.mLastTimeForegroundServiceUsed);
        parcel.writeLong(this.mTotalTimeInForeground);
        parcel.writeLong(this.mTotalTimeVisible);
        parcel.writeLong(this.mTotalTimeForegroundServiceUsed);
        parcel.writeInt(this.mLaunchCount);
        parcel.writeInt(this.mAppLaunchCount);
        parcel.writeInt(this.mLastEvent);
        Bundle bundle = new Bundle();
        ArrayMap<String, ArrayMap<String, Integer>> arrayMap = this.mChooserCounts;
        if (arrayMap != null) {
            int size = arrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                String strKeyAt = this.mChooserCounts.keyAt(i2);
                ArrayMap<String, Integer> arrayMapValueAt = this.mChooserCounts.valueAt(i2);
                Bundle bundle2 = new Bundle();
                int size2 = arrayMapValueAt.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    bundle2.putInt(arrayMapValueAt.keyAt(i3), arrayMapValueAt.valueAt(i3).intValue());
                }
                bundle.putBundle(strKeyAt, bundle2);
            }
        }
        parcel.writeBundle(bundle);
        writeSparseIntArray(parcel, this.mActivities);
        parcel.writeBundle(eventMapToBundle(this.mForegroundServices));
    }

    private void writeSparseIntArray(Parcel parcel, SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeInt(sparseIntArray.keyAt(i));
            parcel.writeInt(sparseIntArray.valueAt(i));
        }
    }

    private Bundle eventMapToBundle(ArrayMap<String, Integer> arrayMap) {
        Bundle bundle = new Bundle();
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            bundle.putInt(arrayMap.keyAt(i), arrayMap.valueAt(i).intValue());
        }
        return bundle;
    }

    public static final class Builder {
        private final UsageStats mUsageStats = new UsageStats();

        public UsageStats build() {
            return this.mUsageStats;
        }

        public Builder setPackageName(String str) {
            this.mUsageStats.mPackageName = str;
            return this;
        }

        public Builder setFirstTimeStamp(long j) {
            this.mUsageStats.mBeginTimeStamp = j;
            return this;
        }

        public Builder setLastTimeStamp(long j) {
            this.mUsageStats.mEndTimeStamp = j;
            return this;
        }

        public Builder setTotalTimeInForeground(long j) {
            this.mUsageStats.mTotalTimeInForeground = j;
            return this;
        }

        public Builder setLastTimeUsed(long j) {
            this.mUsageStats.mLastTimeUsed = j;
            return this;
        }
    }
}
