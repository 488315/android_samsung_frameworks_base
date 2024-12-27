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

public final class UsageEventsQuery implements Parcelable {
    public static final Parcelable.Creator<UsageEventsQuery> CREATOR =
            new Parcelable.Creator<
                    UsageEventsQuery>() { // from class: android.app.usage.UsageEventsQuery.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public UsageEventsQuery createFromParcel(Parcel in) {
                    return new UsageEventsQuery(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public UsageEventsQuery[] newArray(int size) {
                    return new UsageEventsQuery[size];
                }
            };
    private final long mBeginTimeMillis;
    private final long mEndTimeMillis;
    private final int[] mEventTypes;
    private final String[] mPackageNames;
    private final int mUserId;

    private UsageEventsQuery(Builder builder) {
        this.mBeginTimeMillis = builder.mBeginTimeMillis;
        this.mEndTimeMillis = builder.mEndTimeMillis;
        this.mEventTypes = ArrayUtils.convertToIntArray((ArraySet<Integer>) builder.mEventTypes);
        this.mUserId = builder.mUserId;
        this.mPackageNames =
                (String[]) builder.mPackageNames.toArray(new String[builder.mPackageNames.size()]);
    }

    private UsageEventsQuery(Parcel in) {
        this.mBeginTimeMillis = in.readLong();
        this.mEndTimeMillis = in.readLong();
        int eventTypesLength = in.readInt();
        this.mEventTypes = new int[eventTypesLength];
        in.readIntArray(this.mEventTypes);
        this.mUserId = in.readInt();
        int packageNamesLength = in.readInt();
        this.mPackageNames = new String[packageNamesLength];
        in.readStringArray(this.mPackageNames);
    }

    public long getBeginTimeMillis() {
        return this.mBeginTimeMillis;
    }

    public long getEndTimeMillis() {
        return this.mEndTimeMillis;
    }

    public int[] getEventTypes() {
        return Arrays.copyOf(this.mEventTypes, this.mEventTypes.length);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public Set<String> getPackageNames() {
        if (ArrayUtils.isEmpty(this.mPackageNames)) {
            return Collections.emptySet();
        }
        HashSet<String> pkgNameSet = new HashSet<>();
        for (String pkgName : this.mPackageNames) {
            pkgNameSet.add(pkgName);
        }
        return pkgNameSet;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mBeginTimeMillis);
        dest.writeLong(this.mEndTimeMillis);
        dest.writeInt(this.mEventTypes.length);
        dest.writeIntArray(this.mEventTypes);
        dest.writeInt(this.mUserId);
        dest.writeInt(this.mPackageNames.length);
        dest.writeStringArray(this.mPackageNames);
    }

    public static final class Builder {
        private final long mBeginTimeMillis;
        private final long mEndTimeMillis;
        private final ArraySet<Integer> mEventTypes = new ArraySet<>();
        private int mUserId = -10000;
        private final ArraySet<String> mPackageNames = new ArraySet<>();

        public Builder(long beginTimeMillis, long endTimeMillis) {
            if (beginTimeMillis < 0 || endTimeMillis < beginTimeMillis) {
                throw new IllegalArgumentException("Invalid period");
            }
            this.mBeginTimeMillis = beginTimeMillis;
            this.mEndTimeMillis = endTimeMillis;
        }

        public UsageEventsQuery build() {
            return new UsageEventsQuery(this);
        }

        public Builder setEventTypes(int... eventTypes) {
            if (eventTypes == null || eventTypes.length == 0) {
                throw new NullPointerException("eventTypes is null or empty");
            }
            this.mEventTypes.clear();
            for (int eventType : eventTypes) {
                if (eventType < 0 || eventType > 31) {
                    throw new IllegalArgumentException("Invalid usage event type: " + eventType);
                }
                this.mEventTypes.add(Integer.valueOf(eventType));
            }
            return this;
        }

        public Builder setUserId(int userId) {
            this.mUserId = userId;
            return this;
        }

        public Builder setPackageNames(String... pkgNames) {
            if (pkgNames == null || pkgNames.length == 0) {
                throw new NullPointerException("pkgNames is null or empty");
            }
            this.mPackageNames.clear();
            for (int i = 0; i < pkgNames.length; i++) {
                if (!TextUtils.isEmpty(pkgNames[i])) {
                    this.mPackageNames.add(pkgNames[i]);
                }
            }
            return this;
        }
    }
}
