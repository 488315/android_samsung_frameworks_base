package android.util;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.time.Clock;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes4.dex */
public class RecurrenceRule implements Parcelable {
    private static final int VERSION_INIT = 0;
    public final ZonedDateTime end;
    public final Period period;
    public final ZonedDateTime start;
    private static final String TAG = "RecurrenceRule";
    private static final boolean LOGD = Log.isLoggable(TAG, 3);
    public static Clock sClock = Clock.systemDefaultZone();
    public static final Parcelable.Creator<RecurrenceRule> CREATOR = new Parcelable.Creator<RecurrenceRule>() { // from class: android.util.RecurrenceRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecurrenceRule createFromParcel(Parcel parcel) {
            return new RecurrenceRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecurrenceRule[] newArray(int i) {
            return new RecurrenceRule[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RecurrenceRule(ZonedDateTime zonedDateTime, ZonedDateTime zonedDateTime2, Period period) {
        this.start = zonedDateTime;
        this.end = zonedDateTime2;
        this.period = period;
    }

    @Deprecated
    public static RecurrenceRule buildNever() {
        return new RecurrenceRule(null, null, null);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.time.ZonedDateTime] */
    @Deprecated
    public static RecurrenceRule buildRecurringMonthly(int i, ZoneId zoneId) {
        return new RecurrenceRule(ZonedDateTime.of(ZonedDateTime.now(sClock).withZoneSameInstant(zoneId).toLocalDate().minusYears(1L).withMonth(1).withDayOfMonth(i), LocalTime.MIDNIGHT, zoneId), null, Period.ofMonths(1));
    }

    private RecurrenceRule(Parcel parcel) {
        this.start = convertZonedDateTime(parcel.readString());
        this.end = convertZonedDateTime(parcel.readString());
        this.period = convertPeriod(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(convertZonedDateTime(this.start));
        parcel.writeString(convertZonedDateTime(this.end));
        parcel.writeString(convertPeriod(this.period));
    }

    public RecurrenceRule(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        if (readInt == 0) {
            this.start = convertZonedDateTime(BackupUtils.readString(dataInputStream));
            this.end = convertZonedDateTime(BackupUtils.readString(dataInputStream));
            this.period = convertPeriod(BackupUtils.readString(dataInputStream));
        } else {
            throw new ProtocolException("Unknown version " + readInt);
        }
    }

    public void writeToStream(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(0);
        BackupUtils.writeString(dataOutputStream, convertZonedDateTime(this.start));
        BackupUtils.writeString(dataOutputStream, convertZonedDateTime(this.end));
        BackupUtils.writeString(dataOutputStream, convertPeriod(this.period));
    }

    public String toString() {
        return "RecurrenceRule{start=" + this.start + " end=" + this.end + " period=" + this.period + "}";
    }

    public int hashCode() {
        return Objects.hash(this.start, this.end, this.period);
    }

    public boolean equals(Object obj) {
        if (obj instanceof RecurrenceRule) {
            RecurrenceRule recurrenceRule = (RecurrenceRule) obj;
            if (Objects.equals(this.start, recurrenceRule.start) && Objects.equals(this.end, recurrenceRule.end) && Objects.equals(this.period, recurrenceRule.period)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRecurring() {
        return this.period != null;
    }

    @Deprecated
    public boolean isMonthly() {
        Period period;
        return this.start != null && (period = this.period) != null && period.getYears() == 0 && this.period.getMonths() == 1 && this.period.getDays() == 0;
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        if (this.period != null) {
            return new RecurringIterator();
        }
        return new NonrecurringIterator();
    }

    private class NonrecurringIterator implements Iterator<Range<ZonedDateTime>> {
        boolean hasNext;

        public NonrecurringIterator() {
            this.hasNext = (RecurrenceRule.this.start == null || RecurrenceRule.this.end == null) ? false : true;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Range<ZonedDateTime> next() {
            this.hasNext = false;
            return new Range<>(RecurrenceRule.this.start, RecurrenceRule.this.end);
        }
    }

    private class RecurringIterator implements Iterator<Range<ZonedDateTime>> {
        ZonedDateTime cycleEnd;
        ZonedDateTime cycleStart;
        int i;

        /* JADX WARN: Multi-variable type inference failed */
        public RecurringIterator() {
            ZonedDateTime withZoneSameInstant = RecurrenceRule.this.end != null ? RecurrenceRule.this.end : ZonedDateTime.now(RecurrenceRule.sClock).withZoneSameInstant(RecurrenceRule.this.start.getZone());
            if (RecurrenceRule.LOGD) {
                Log.d(RecurrenceRule.TAG, "Resolving using anchor " + withZoneSameInstant);
            }
            updateCycle();
            while (withZoneSameInstant.toEpochSecond() > this.cycleEnd.toEpochSecond()) {
                this.i++;
                updateCycle();
            }
            while (withZoneSameInstant.toEpochSecond() <= this.cycleStart.toEpochSecond()) {
                this.i--;
                updateCycle();
            }
        }

        private void updateCycle() {
            this.cycleStart = roundBoundaryTime(RecurrenceRule.this.start.plus((TemporalAmount) RecurrenceRule.this.period.multipliedBy(this.i)));
            this.cycleEnd = roundBoundaryTime(RecurrenceRule.this.start.plus((TemporalAmount) RecurrenceRule.this.period.multipliedBy(this.i + 1)));
        }

        private ZonedDateTime roundBoundaryTime(ZonedDateTime zonedDateTime) {
            return (!RecurrenceRule.this.isMonthly() || zonedDateTime.getDayOfMonth() >= RecurrenceRule.this.start.getDayOfMonth()) ? zonedDateTime : ZonedDateTime.of(zonedDateTime.toLocalDate(), LocalTime.MAX, RecurrenceRule.this.start.getZone());
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cycleStart.toEpochSecond() >= RecurrenceRule.this.start.toEpochSecond();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Range<ZonedDateTime> next() {
            if (RecurrenceRule.LOGD) {
                Log.d(RecurrenceRule.TAG, "Cycle " + this.i + " from " + this.cycleStart + " to " + this.cycleEnd);
            }
            Range<ZonedDateTime> range = new Range<>(this.cycleStart, this.cycleEnd);
            this.i--;
            updateCycle();
            return range;
        }
    }

    public static String convertZonedDateTime(ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            return zonedDateTime.toString();
        }
        return null;
    }

    public static ZonedDateTime convertZonedDateTime(String str) {
        if (str != null) {
            return ZonedDateTime.parse(str);
        }
        return null;
    }

    public static String convertPeriod(Period period) {
        if (period != null) {
            return period.toString();
        }
        return null;
    }

    public static Period convertPeriod(String str) {
        if (str != null) {
            return Period.parse(str);
        }
        return null;
    }
}
