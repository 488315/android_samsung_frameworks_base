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
            Bundle readBundle = parcel.readBundle();
            if (readBundle != null) {
                usageStats.mChooserCounts = new ArrayMap<>();
                for (String str : readBundle.keySet()) {
                    if (!usageStats.mChooserCounts.containsKey(str)) {
                        usageStats.mChooserCounts.put(str, new ArrayMap<>());
                    }
                    Bundle bundle = readBundle.getBundle(str);
                    if (bundle != null) {
                        for (String str2 : bundle.keySet()) {
                            int i = bundle.getInt(str2);
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
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
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
        this.mActivities = usageStats.mActivities.m5532clone();
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
            int keyAt = sparseIntArray2.keyAt(i);
            int valueAt = sparseIntArray2.valueAt(i);
            int indexOfKey = sparseIntArray.indexOfKey(keyAt);
            if (indexOfKey >= 0) {
                sparseIntArray.put(keyAt, Math.max(sparseIntArray.valueAt(indexOfKey), valueAt));
            } else {
                sparseIntArray.put(keyAt, valueAt);
            }
        }
    }

    private void mergeEventMap(ArrayMap<String, Integer> arrayMap, ArrayMap<String, Integer> arrayMap2) {
        int size = arrayMap2.size();
        for (int i = 0; i < size; i++) {
            String keyAt = arrayMap2.keyAt(i);
            Integer valueAt = arrayMap2.valueAt(i);
            if (arrayMap.containsKey(keyAt)) {
                arrayMap.put(keyAt, Integer.valueOf(Math.max(arrayMap.get(keyAt).intValue(), valueAt.intValue())));
            } else {
                arrayMap.put(keyAt, valueAt);
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
                String keyAt = usageStats.mChooserCounts.keyAt(i);
                ArrayMap<String, Integer> valueAt = usageStats.mChooserCounts.valueAt(i);
                if (!this.mChooserCounts.containsKey(keyAt) || this.mChooserCounts.get(keyAt) == null) {
                    this.mChooserCounts.put(keyAt, valueAt);
                } else {
                    int size2 = valueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        String keyAt2 = valueAt.keyAt(i2);
                        this.mChooserCounts.get(keyAt).put(keyAt2, Integer.valueOf(this.mChooserCounts.get(keyAt).getOrDefault(keyAt2, 0).intValue() + valueAt.valueAt(i2).intValue()));
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
            int valueAt = this.mActivities.valueAt(i);
            if (valueAt == 1 || valueAt == 2) {
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
            int indexOfKey = this.mActivities.indexOfKey(i2);
            if (indexOfKey >= 0) {
                int valueAt = this.mActivities.valueAt(indexOfKey);
                if (valueAt == 1) {
                    incrementTimeUsed(j);
                    incrementTimeVisible(j);
                } else if (valueAt == 2) {
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
        int intValue;
        if (i == 20 || i == 19) {
            Integer num = this.mForegroundServices.get(str);
            if (num != null && ((intValue = num.intValue()) == 19 || intValue == 21)) {
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
    /* JADX WARN: Removed duplicated region for block: B:15:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update(java.lang.String r8, long r9, int r11, int r12) {
        /*
            r7 = this;
            r0 = 1
            if (r11 == r0) goto L34
            r1 = 2
            if (r11 == r1) goto L34
            r1 = 3
            if (r11 == r1) goto L70
            r1 = 7
            if (r11 == r1) goto L57
            r1 = 31
            if (r11 == r1) goto L54
            switch(r11) {
                case 19: goto L50;
                case 20: goto L50;
                case 21: goto L44;
                case 22: goto L3a;
                case 23: goto L34;
                case 24: goto L34;
                case 25: goto L18;
                case 26: goto L18;
                default: goto L13;
            }
        L13:
            r1 = r7
            r3 = r9
            r5 = r11
            goto L86
        L18:
            boolean r8 = r7.hasForegroundActivity()
            if (r8 == 0) goto L21
            r7.incrementTimeUsed(r9)
        L21:
            boolean r8 = r7.hasVisibleActivity()
            if (r8 == 0) goto L2a
            r7.incrementTimeVisible(r9)
        L2a:
            boolean r8 = r7.anyForegroundServiceStarted()
            if (r8 == 0) goto L13
            r7.incrementServiceTimeUsed(r9)
            goto L13
        L34:
            r1 = r7
            r2 = r8
            r3 = r9
            r5 = r11
            r6 = r12
            goto L83
        L3a:
            boolean r8 = r7.anyForegroundServiceStarted()
            if (r8 == 0) goto L13
            r7.incrementServiceTimeUsed(r9)
            goto L13
        L44:
            r7.mLastTimeForegroundServiceUsed = r9
            android.util.ArrayMap<java.lang.String, java.lang.Integer> r12 = r7.mForegroundServices
            java.lang.Integer r1 = java.lang.Integer.valueOf(r11)
            r12.put(r8, r1)
            goto L13
        L50:
            r7.updateForegroundService(r8, r9, r11)
            goto L13
        L54:
            r7.mLastTimeComponentUsed = r9
            goto L13
        L57:
            boolean r8 = r7.hasForegroundActivity()
            if (r8 == 0) goto L61
            r7.incrementTimeUsed(r9)
            goto L63
        L61:
            r7.mLastTimeUsed = r9
        L63:
            boolean r8 = r7.hasVisibleActivity()
            if (r8 == 0) goto L6d
            r7.incrementTimeVisible(r9)
            goto L13
        L6d:
            r7.mLastTimeVisible = r9
            goto L13
        L70:
            boolean r8 = r7.hasForegroundActivity()
            if (r8 == 0) goto L79
            r7.incrementTimeUsed(r9)
        L79:
            boolean r8 = r7.hasVisibleActivity()
            if (r8 == 0) goto L13
            r7.incrementTimeVisible(r9)
            goto L13
        L83:
            r1.updateActivity(r2, r3, r5, r6)
        L86:
            r1.mEndTimeStamp = r3
            if (r5 != r0) goto L8f
            int r7 = r1.mLaunchCount
            int r7 = r7 + r0
            r1.mLaunchCount = r7
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.usage.UsageStats.update(java.lang.String, long, int, int):void");
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
                String keyAt = this.mChooserCounts.keyAt(i2);
                ArrayMap<String, Integer> valueAt = this.mChooserCounts.valueAt(i2);
                Bundle bundle2 = new Bundle();
                int size2 = valueAt.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    bundle2.putInt(valueAt.keyAt(i3), valueAt.valueAt(i3).intValue());
                }
                bundle.putBundle(keyAt, bundle2);
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
