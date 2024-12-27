package com.android.server.people.data;

import android.text.format.DateFormat;
import android.util.Range;
import android.util.Slog;
import android.util.proto.ProtoInputStream;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Function;

public final class EventIndex {
    public static final EventIndex EMPTY = new EventIndex();
    public static final List TIME_SLOT_FACTORIES;
    public final long[] mEventBitmaps;
    public final Injector mInjector;
    public long mLastUpdatedTime;
    public final Object mLock;

    class Injector {}

    static {
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        final int i4 = 3;
        TIME_SLOT_FACTORIES =
                Collections.unmodifiableList(
                        Arrays.asList(
                                new Function() { // from class:
                                                 // com.android.server.people.data.EventIndex$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Long l = (Long) obj;
                                        switch (i) {
                                            case 0:
                                                LocalDateTime truncatedTo =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.DAYS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo.plusDays(1L))));
                                            case 1:
                                                LocalDateTime minusHours =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS)
                                                                .minusHours(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getHour()
                                                                                % 4);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours.plusHours(4L))));
                                            case 2:
                                                LocalDateTime truncatedTo2 =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2.plusHours(
                                                                                1L))));
                                            default:
                                                LocalDateTime minusMinutes =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.MINUTES)
                                                                .minusMinutes(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getMinute()
                                                                                % 2);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes.plusMinutes(
                                                                                2L))));
                                        }
                                    }
                                },
                                new Function() { // from class:
                                                 // com.android.server.people.data.EventIndex$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Long l = (Long) obj;
                                        switch (i2) {
                                            case 0:
                                                LocalDateTime truncatedTo =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.DAYS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo.plusDays(1L))));
                                            case 1:
                                                LocalDateTime minusHours =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS)
                                                                .minusHours(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getHour()
                                                                                % 4);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours.plusHours(4L))));
                                            case 2:
                                                LocalDateTime truncatedTo2 =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2.plusHours(
                                                                                1L))));
                                            default:
                                                LocalDateTime minusMinutes =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.MINUTES)
                                                                .minusMinutes(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getMinute()
                                                                                % 2);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes.plusMinutes(
                                                                                2L))));
                                        }
                                    }
                                },
                                new Function() { // from class:
                                                 // com.android.server.people.data.EventIndex$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Long l = (Long) obj;
                                        switch (i3) {
                                            case 0:
                                                LocalDateTime truncatedTo =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.DAYS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo.plusDays(1L))));
                                            case 1:
                                                LocalDateTime minusHours =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS)
                                                                .minusHours(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getHour()
                                                                                % 4);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours.plusHours(4L))));
                                            case 2:
                                                LocalDateTime truncatedTo2 =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2.plusHours(
                                                                                1L))));
                                            default:
                                                LocalDateTime minusMinutes =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.MINUTES)
                                                                .minusMinutes(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getMinute()
                                                                                % 2);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes.plusMinutes(
                                                                                2L))));
                                        }
                                    }
                                },
                                new Function() { // from class:
                                                 // com.android.server.people.data.EventIndex$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        Long l = (Long) obj;
                                        switch (i4) {
                                            case 0:
                                                LocalDateTime truncatedTo =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.DAYS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo.plusDays(1L))));
                                            case 1:
                                                LocalDateTime minusHours =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS)
                                                                .minusHours(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getHour()
                                                                                % 4);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusHours.plusHours(4L))));
                                            case 2:
                                                LocalDateTime truncatedTo2 =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.HOURS);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        truncatedTo2.plusHours(
                                                                                1L))));
                                            default:
                                                LocalDateTime minusMinutes =
                                                        EventIndex.toLocalDateTime(l.longValue())
                                                                .truncatedTo(ChronoUnit.MINUTES)
                                                                .minusMinutes(
                                                                        EventIndex.toLocalDateTime(
                                                                                                r2)
                                                                                        .getMinute()
                                                                                % 2);
                                                return Range.create(
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes)),
                                                        Long.valueOf(
                                                                EventIndex.toEpochMilli(
                                                                        minusMinutes.plusMinutes(
                                                                                2L))));
                                        }
                                    }
                                }));
    }

    public EventIndex() {
        this(new Injector());
    }

    public EventIndex(Injector injector) {
        this(injector, new long[] {0, 0, 0, 0}, System.currentTimeMillis());
        injector.getClass();
    }

    public EventIndex(Injector injector, long[] jArr, long j) {
        this.mLock = new Object();
        this.mInjector = injector;
        this.mEventBitmaps = Arrays.copyOf(jArr, 4);
        this.mLastUpdatedTime = j;
    }

    public EventIndex(EventIndex eventIndex) {
        this(eventIndex.mInjector, eventIndex.mEventBitmaps, eventIndex.mLastUpdatedTime);
    }

    public static EventIndex combine(EventIndex eventIndex, EventIndex eventIndex2) {
        long j = eventIndex.mLastUpdatedTime;
        long j2 = eventIndex2.mLastUpdatedTime;
        EventIndex eventIndex3 = j < j2 ? eventIndex : eventIndex2;
        if (j < j2) {
            eventIndex = eventIndex2;
        }
        EventIndex eventIndex4 = new EventIndex(eventIndex3);
        eventIndex4.updateEventBitmaps(eventIndex.mLastUpdatedTime);
        for (int i = 0; i < 4; i++) {
            long[] jArr = eventIndex4.mEventBitmaps;
            jArr[i] = jArr[i] | eventIndex.mEventBitmaps[i];
        }
        return eventIndex4;
    }

    public static List combineTimeSlotLists(List list, List list2) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < list.size()) {
            ArrayList arrayList2 = (ArrayList) list2;
            if (i2 >= arrayList2.size()) {
                break;
            }
            Range range = (Range) list.get(i);
            Range range2 = (Range) arrayList2.get(i2);
            if (range.contains(range2)) {
                arrayList.add(range2);
                i++;
            } else if (((Long) range.getLower()).longValue()
                    < ((Long) range2.getLower()).longValue()) {
                arrayList.add(range2);
            } else {
                arrayList.add(range);
                i++;
            }
            i2++;
        }
        if (i < list.size()) {
            arrayList.addAll(list.subList(i, list.size()));
        } else {
            ArrayList arrayList3 = (ArrayList) list2;
            if (i2 < arrayList3.size()) {
                arrayList.addAll(arrayList3.subList(i2, arrayList3.size()));
            }
        }
        return arrayList;
    }

    public static int diffTimeSlots(int i, long j, long j2) {
        Function function = (Function) TIME_SLOT_FACTORIES.get(i);
        Range range = (Range) function.apply(Long.valueOf(j));
        return (int)
                ((((Long) ((Range) function.apply(Long.valueOf(j2))).getLower()).longValue()
                                - ((Long) range.getLower()).longValue())
                        / getDuration(range));
    }

    public static long getDuration(Range range) {
        return ((Long) range.getUpper()).longValue() - ((Long) range.getLower()).longValue();
    }

    public static EventIndex readFromProto(ProtoInputStream protoInputStream) {
        long[] jArr = new long[4];
        int i = 0;
        long j = 0;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                jArr[i] = protoInputStream.readLong(2211908157441L);
                i++;
            } else if (fieldNumber != 2) {
                Slog.e(
                        "EventIndex",
                        "Could not read undefined field: " + protoInputStream.getFieldNumber());
            } else {
                j = protoInputStream.readLong(1112396529666L);
            }
        }
        return new EventIndex(new Injector(), jArr, j);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long j) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(j), TimeZone.getDefault().toZoneId());
    }

    public final void addEvent(long j) {
        if (EMPTY == this) {
            throw new IllegalStateException("EMPTY instance is immutable");
        }
        synchronized (this.mLock) {
            try {
                this.mInjector.getClass();
                long currentTimeMillis = System.currentTimeMillis();
                updateEventBitmaps(currentTimeMillis);
                for (int i = 0; i < 4; i++) {
                    int diffTimeSlots = diffTimeSlots(i, j, currentTimeMillis);
                    if (diffTimeSlots < 64) {
                        long[] jArr = this.mEventBitmaps;
                        jArr[i] = jArr[i] | (1 << diffTimeSlots);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EventIndex)) {
            return false;
        }
        EventIndex eventIndex = (EventIndex) obj;
        return this.mLastUpdatedTime == eventIndex.mLastUpdatedTime
                && Arrays.equals(this.mEventBitmaps, eventIndex.mEventBitmaps);
    }

    public final List getActiveTimeSlots() {
        List arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < 4; i++) {
                try {
                    arrayList = combineTimeSlotLists(arrayList, getActiveTimeSlotsForType(i));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public final List getActiveTimeSlotsForType(int i) {
        long j = this.mEventBitmaps[i];
        Range range =
                (Range)
                        ((Function) TIME_SLOT_FACTORIES.get(i))
                                .apply(Long.valueOf(this.mLastUpdatedTime));
        long longValue = ((Long) range.getLower()).longValue();
        long duration = getDuration(range);
        ArrayList arrayList = new ArrayList();
        while (j != 0) {
            int numberOfTrailingZeros = Long.numberOfTrailingZeros(j);
            if (numberOfTrailingZeros > 0) {
                longValue -= numberOfTrailingZeros * duration;
                j >>>= numberOfTrailingZeros;
            }
            if (j != 0) {
                arrayList.add(
                        Range.create(Long.valueOf(longValue), Long.valueOf(longValue + duration)));
                longValue -= duration;
                j >>>= 1;
            }
        }
        return arrayList;
    }

    public final int hashCode() {
        return Objects.hash(
                Long.valueOf(this.mLastUpdatedTime),
                Integer.valueOf(Arrays.hashCode(this.mEventBitmaps)));
    }

    public final boolean isEmpty() {
        synchronized (this.mLock) {
            for (int i = 0; i < 4; i++) {
                try {
                    if (this.mEventBitmaps[i] != 0) {
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EventIndex {perDayEventBitmap=0b");
        long[] jArr = this.mEventBitmaps;
        sb.append(Long.toBinaryString(jArr[0]));
        sb.append(", perFourHoursEventBitmap=0b");
        sb.append(Long.toBinaryString(jArr[1]));
        sb.append(", perHourEventBitmap=0b");
        sb.append(Long.toBinaryString(jArr[2]));
        sb.append(", perTwoMinutesEventBitmap=0b");
        sb.append(Long.toBinaryString(jArr[3]));
        sb.append(", lastUpdatedTime=");
        sb.append(DateFormat.format("yyyy-MM-dd HH:mm:ss", this.mLastUpdatedTime));
        sb.append("}");
        return sb.toString();
    }

    public final void updateEventBitmaps(long j) {
        int i = 0;
        while (true) {
            long[] jArr = this.mEventBitmaps;
            if (i >= 4) {
                long j2 = jArr[0] << 1;
                jArr[0] = j2;
                jArr[0] = j2 >>> 1;
                this.mLastUpdatedTime = j;
                return;
            }
            int diffTimeSlots = diffTimeSlots(i, this.mLastUpdatedTime, j);
            if (diffTimeSlots < 64) {
                jArr[i] = jArr[i] << diffTimeSlots;
            } else {
                jArr[i] = 0;
            }
            i++;
        }
    }
}
