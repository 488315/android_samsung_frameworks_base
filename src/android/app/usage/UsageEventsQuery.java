package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class UsageEventsQuery implements Parcelable {
    public static final Parcelable.Creator<UsageEventsQuery> CREATOR = new Parcelable.Creator<UsageEventsQuery>() { // from class: android.app.usage.UsageEventsQuery.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsageEventsQuery createFromParcel(Parcel parcel) {
            return new UsageEventsQuery(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsageEventsQuery[] newArray(int i) {
            return new UsageEventsQuery[i];
        }
    };
    private final long mBeginTimeMillis;
    private final long mEndTimeMillis;
    private final int[] mEventTypes;
    private final String[] mPackageNames;
    private final int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UsageEventsQuery(Builder builder) {
        this.mBeginTimeMillis = builder.mBeginTimeMillis;
        this.mEndTimeMillis = builder.mEndTimeMillis;
        this.mEventTypes = ArrayUtils.convertToIntArray((ArraySet<Integer>) builder.mEventTypes);
        this.mUserId = builder.mUserId;
        this.mPackageNames = (String[]) builder.mPackageNames.toArray(new String[builder.mPackageNames.size()]);
    }

    private UsageEventsQuery(Parcel parcel) {
        this.mBeginTimeMillis = parcel.readLong();
        this.mEndTimeMillis = parcel.readLong();
        int[] iArr = new int[parcel.readInt()];
        this.mEventTypes = iArr;
        parcel.readIntArray(iArr);
        this.mUserId = parcel.readInt();
        String[] strArr = new String[parcel.readInt()];
        this.mPackageNames = strArr;
        parcel.readStringArray(strArr);
    }

    public long getBeginTimeMillis() {
        return this.mBeginTimeMillis;
    }

    public long getEndTimeMillis() {
        return this.mEndTimeMillis;
    }

    public int[] getEventTypes() {
        int[] iArr = this.mEventTypes;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public Set<String> getPackageNames() {
        if (ArrayUtils.isEmpty(this.mPackageNames)) {
            return Collections.EMPTY_SET;
        }
        HashSet hashSet = new HashSet();
        for (String str : this.mPackageNames) {
            hashSet.add(str);
        }
        return hashSet;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mBeginTimeMillis);
        parcel.writeLong(this.mEndTimeMillis);
        parcel.writeInt(this.mEventTypes.length);
        parcel.writeIntArray(this.mEventTypes);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mPackageNames.length);
        parcel.writeStringArray(this.mPackageNames);
    }

    public static final class Builder {
        private final long mBeginTimeMillis;
        private final long mEndTimeMillis;
        private final ArraySet<Integer> mEventTypes = new ArraySet<>();
        private int mUserId = -10000;
        private final ArraySet<String> mPackageNames = new ArraySet<>();

        public Builder(long j, long j2) {
            if (j < 0 || j2 < j) {
                throw new IllegalArgumentException("Invalid period");
            }
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
        }

        public UsageEventsQuery build() {
            return new UsageEventsQuery(this);
        }

        public Builder setEventTypes(int... iArr) {
            if (iArr == null || iArr.length == 0) {
                throw new NullPointerException("eventTypes is null or empty");
            }
            this.mEventTypes.clear();
            for (int i : iArr) {
                if (i < 0 || i > 31) {
                    throw new IllegalArgumentException("Invalid usage event type: " + i);
                }
                this.mEventTypes.add(Integer.valueOf(i));
            }
            return this;
        }

        public Builder setUserId(int i) {
            this.mUserId = i;
            return this;
        }

        public Builder setPackageNames(String... strArr) {
            if (strArr == null || strArr.length == 0) {
                throw new NullPointerException("pkgNames is null or empty");
            }
            this.mPackageNames.clear();
            for (int i = 0; i < strArr.length; i++) {
                if (!TextUtils.isEmpty(strArr[i])) {
                    this.mPackageNames.add(strArr[i]);
                }
            }
            return this;
        }
    }
}
