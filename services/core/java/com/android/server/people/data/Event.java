package com.android.server.people.data;

import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.Slog;
import android.util.proto.ProtoInputStream;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Event {
    public static final Set SHARE_EVENT_TYPES;
    public final int mDurationSeconds;
    public final long mTimestamp;
    public final int mType;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public int mDurationSeconds;
        public long mTimestamp;
        public int mType;

        public Builder(long j, int i) {
            this.mTimestamp = j;
            this.mType = i;
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        SHARE_EVENT_TYPES = arraySet2;
        ArraySet arraySet3 = new ArraySet();
        ArraySet arraySet4 = new ArraySet();
        ArraySet arraySet5 = new ArraySet();
        arraySet.add(2);
        arraySet.add(3);
        arraySet2.add(4);
        arraySet2.add(5);
        arraySet2.add(6);
        arraySet2.add(7);
        arraySet3.add(9);
        arraySet3.add(8);
        arraySet4.add(11);
        arraySet4.add(10);
        arraySet4.add(12);
        arraySet5.add(1);
        arraySet5.add(13);
        arraySet5.addAll((Collection) arraySet);
        arraySet5.addAll((Collection) arraySet2);
        arraySet5.addAll((Collection) arraySet3);
        arraySet5.addAll((Collection) arraySet4);
    }

    public Event(long j, int i) {
        this.mTimestamp = j;
        this.mType = i;
        this.mDurationSeconds = 0;
    }

    public Event(Builder builder) {
        this.mTimestamp = builder.mTimestamp;
        this.mType = builder.mType;
        this.mDurationSeconds = builder.mDurationSeconds;
    }

    public static Event readFromProto(ProtoInputStream protoInputStream) {
        Builder builder = new Builder();
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                builder.mType = protoInputStream.readInt(1120986464257L);
            } else if (fieldNumber == 2) {
                builder.mTimestamp = protoInputStream.readLong(1112396529666L);
            } else if (fieldNumber != 3) {
                Slog.w(
                        "Event",
                        "Could not read undefined field: " + protoInputStream.getFieldNumber());
            } else {
                builder.mDurationSeconds = protoInputStream.readInt(1120986464259L);
            }
        }
        return new Event(builder);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event event = (Event) obj;
        return this.mTimestamp == event.mTimestamp
                && this.mType == event.mType
                && this.mDurationSeconds == event.mDurationSeconds;
    }

    public final int hashCode() {
        return Objects.hash(
                Long.valueOf(this.mTimestamp),
                Integer.valueOf(this.mType),
                Integer.valueOf(this.mDurationSeconds));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Event {timestamp=");
        sb.append(DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mTimestamp));
        sb.append(", type=");
        sb.append(this.mType);
        int i = this.mDurationSeconds;
        if (i > 0) {
            sb.append(", durationSeconds=");
            sb.append(i);
        }
        sb.append("}");
        return sb.toString();
    }
}
