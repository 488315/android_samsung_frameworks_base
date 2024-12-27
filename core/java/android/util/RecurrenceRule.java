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

public class RecurrenceRule implements Parcelable {
    private static final int VERSION_INIT = 0;
    public final ZonedDateTime end;
    public final Period period;
    public final ZonedDateTime start;
    private static final String TAG = "RecurrenceRule";
    private static final boolean LOGD = Log.isLoggable(TAG, 3);
    public static Clock sClock = Clock.systemDefaultZone();
    public static final Parcelable.Creator<RecurrenceRule> CREATOR =
            new Parcelable.Creator<RecurrenceRule>() { // from class: android.util.RecurrenceRule.1
                @Override // android.os.Parcelable.Creator
                public RecurrenceRule createFromParcel(Parcel source) {
                    return new RecurrenceRule(source);
                }

                @Override // android.os.Parcelable.Creator
                public RecurrenceRule[] newArray(int size) {
                    return new RecurrenceRule[size];
                }
            };

    public RecurrenceRule(ZonedDateTime start, ZonedDateTime end, Period period) {
        this.start = start;
        this.end = end;
        this.period = period;
    }

    @Deprecated
    public static RecurrenceRule buildNever() {
        return new RecurrenceRule(null, null, null);
    }

    @Deprecated
    public static RecurrenceRule buildRecurringMonthly(int dayOfMonth, ZoneId zone) {
        ZonedDateTime start =
                ZonedDateTime.of(
                        ZonedDateTime.now(sClock)
                                .withZoneSameInstant(zone)
                                .toLocalDate()
                                .minusYears(1L)
                                .withMonth(1)
                                .withDayOfMonth(dayOfMonth),
                        LocalTime.MIDNIGHT,
                        zone);
        return new RecurrenceRule(start, null, Period.ofMonths(1));
    }

    private RecurrenceRule(Parcel source) {
        this.start = convertZonedDateTime(source.readString());
        this.end = convertZonedDateTime(source.readString());
        this.period = convertPeriod(source.readString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(convertZonedDateTime(this.start));
        dest.writeString(convertZonedDateTime(this.end));
        dest.writeString(convertPeriod(this.period));
    }

    public RecurrenceRule(DataInputStream in) throws IOException {
        int version = in.readInt();
        switch (version) {
            case 0:
                this.start = convertZonedDateTime(BackupUtils.readString(in));
                this.end = convertZonedDateTime(BackupUtils.readString(in));
                this.period = convertPeriod(BackupUtils.readString(in));
                return;
            default:
                throw new ProtocolException("Unknown version " + version);
        }
    }

    public void writeToStream(DataOutputStream out) throws IOException {
        out.writeInt(0);
        BackupUtils.writeString(out, convertZonedDateTime(this.start));
        BackupUtils.writeString(out, convertZonedDateTime(this.end));
        BackupUtils.writeString(out, convertPeriod(this.period));
    }

    public String toString() {
        return "RecurrenceRule{start="
                + this.start
                + " end="
                + this.end
                + " period="
                + this.period
                + "}";
    }

    public int hashCode() {
        return Objects.hash(this.start, this.end, this.period);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RecurrenceRule)) {
            return false;
        }
        RecurrenceRule other = (RecurrenceRule) obj;
        return Objects.equals(this.start, other.start)
                && Objects.equals(this.end, other.end)
                && Objects.equals(this.period, other.period);
    }

    public boolean isRecurring() {
        return this.period != null;
    }

    @Deprecated
    public boolean isMonthly() {
        return this.start != null
                && this.period != null
                && this.period.getYears() == 0
                && this.period.getMonths() == 1
                && this.period.getDays() == 0;
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
            this.hasNext =
                    (RecurrenceRule.this.start == null || RecurrenceRule.this.end == null)
                            ? false
                            : true;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

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

        public RecurringIterator() {
            ZonedDateTime withZoneSameInstant =
                    RecurrenceRule.this.end != null
                            ? RecurrenceRule.this.end
                            : ZonedDateTime.now(RecurrenceRule.sClock)
                                    .withZoneSameInstant(RecurrenceRule.this.start.getZone());
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
            this.cycleStart =
                    roundBoundaryTime(
                            RecurrenceRule.this.start.plus(
                                    (TemporalAmount)
                                            RecurrenceRule.this.period.multipliedBy(this.i)));
            this.cycleEnd =
                    roundBoundaryTime(
                            RecurrenceRule.this.start.plus(
                                    (TemporalAmount)
                                            RecurrenceRule.this.period.multipliedBy(this.i + 1)));
        }

        private ZonedDateTime roundBoundaryTime(ZonedDateTime boundary) {
            if (RecurrenceRule.this.isMonthly()
                    && boundary.getDayOfMonth() < RecurrenceRule.this.start.getDayOfMonth()) {
                return ZonedDateTime.of(
                        boundary.toLocalDate(), LocalTime.MAX, RecurrenceRule.this.start.getZone());
            }
            return boundary;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cycleStart.toEpochSecond() >= RecurrenceRule.this.start.toEpochSecond();
        }

        @Override // java.util.Iterator
        public Range<ZonedDateTime> next() {
            if (RecurrenceRule.LOGD) {
                Log.d(
                        RecurrenceRule.TAG,
                        "Cycle " + this.i + " from " + this.cycleStart + " to " + this.cycleEnd);
            }
            Range<ZonedDateTime> r = new Range<>(this.cycleStart, this.cycleEnd);
            this.i--;
            updateCycle();
            return r;
        }
    }

    public static String convertZonedDateTime(ZonedDateTime time) {
        if (time != null) {
            return time.toString();
        }
        return null;
    }

    public static ZonedDateTime convertZonedDateTime(String time) {
        if (time != null) {
            return ZonedDateTime.parse(time);
        }
        return null;
    }

    public static String convertPeriod(Period period) {
        if (period != null) {
            return period.toString();
        }
        return null;
    }

    public static Period convertPeriod(String period) {
        if (period != null) {
            return Period.parse(period);
        }
        return null;
    }
}
