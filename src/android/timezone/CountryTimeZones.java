package android.timezone;

import android.icu.util.TimeZone;
import com.android.i18n.timezone.CountryTimeZones;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CountryTimeZones {
    private final com.android.i18n.timezone.CountryTimeZones mDelegate;

    public static final class TimeZoneMapping {
        private CountryTimeZones.TimeZoneMapping mDelegate;

        TimeZoneMapping(CountryTimeZones.TimeZoneMapping timeZoneMapping) {
            this.mDelegate = (CountryTimeZones.TimeZoneMapping) Objects.requireNonNull(timeZoneMapping);
        }

        public String getTimeZoneId() {
            return this.mDelegate.getTimeZoneId();
        }

        public TimeZone getTimeZone() {
            return this.mDelegate.getTimeZone();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mDelegate.equals(((TimeZoneMapping) obj).mDelegate);
        }

        public int hashCode() {
            return this.mDelegate.hashCode();
        }

        public String toString() {
            return this.mDelegate.toString();
        }
    }

    public static final class OffsetResult {
        private final boolean mIsOnlyMatch;
        private final TimeZone mTimeZone;

        public OffsetResult(TimeZone timeZone, boolean z) {
            this.mTimeZone = (TimeZone) Objects.requireNonNull(timeZone);
            this.mIsOnlyMatch = z;
        }

        public TimeZone getTimeZone() {
            return this.mTimeZone;
        }

        public boolean isOnlyMatch() {
            return this.mIsOnlyMatch;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                OffsetResult offsetResult = (OffsetResult) obj;
                if (this.mIsOnlyMatch == offsetResult.mIsOnlyMatch && this.mTimeZone.getID().equals(offsetResult.mTimeZone.getID())) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mTimeZone, Boolean.valueOf(this.mIsOnlyMatch));
        }

        public String toString() {
            return "OffsetResult{mTimeZone(ID)=" + this.mTimeZone.getID() + ", mIsOnlyMatch=" + this.mIsOnlyMatch + '}';
        }
    }

    CountryTimeZones(com.android.i18n.timezone.CountryTimeZones countryTimeZones) {
        this.mDelegate = countryTimeZones;
    }

    public boolean matchesCountryCode(String str) {
        return this.mDelegate.matchesCountryCode(str);
    }

    public String getDefaultTimeZoneId() {
        return this.mDelegate.getDefaultTimeZoneId();
    }

    public TimeZone getDefaultTimeZone() {
        return this.mDelegate.getDefaultTimeZone();
    }

    public boolean isDefaultTimeZoneBoosted() {
        return this.mDelegate.isDefaultTimeZoneBoosted();
    }

    public boolean hasUtcZone(long j) {
        return this.mDelegate.hasUtcZone(j);
    }

    public OffsetResult lookupByOffsetWithBias(long j, TimeZone timeZone, int i, boolean z) {
        CountryTimeZones.OffsetResult offsetResultLookupByOffsetWithBias = this.mDelegate.lookupByOffsetWithBias(j, timeZone, i, z);
        if (offsetResultLookupByOffsetWithBias == null) {
            return null;
        }
        return new OffsetResult(offsetResultLookupByOffsetWithBias.getTimeZone(), offsetResultLookupByOffsetWithBias.isOnlyMatch());
    }

    public OffsetResult lookupByOffsetWithBias(long j, TimeZone timeZone, int i) {
        CountryTimeZones.OffsetResult offsetResultLookupByOffsetWithBias = this.mDelegate.lookupByOffsetWithBias(j, timeZone, i);
        if (offsetResultLookupByOffsetWithBias == null) {
            return null;
        }
        return new OffsetResult(offsetResultLookupByOffsetWithBias.getTimeZone(), offsetResultLookupByOffsetWithBias.isOnlyMatch());
    }

    public List<TimeZoneMapping> getEffectiveTimeZoneMappingsAt(long j) {
        List effectiveTimeZoneMappingsAt = this.mDelegate.getEffectiveTimeZoneMappingsAt(j);
        ArrayList arrayList = new ArrayList(effectiveTimeZoneMappingsAt.size());
        Iterator it = effectiveTimeZoneMappingsAt.iterator();
        while (it.hasNext()) {
            arrayList.add(new TimeZoneMapping((CountryTimeZones.TimeZoneMapping) it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mDelegate.equals(((CountryTimeZones) obj).mDelegate);
    }

    public int hashCode() {
        return Objects.hash(this.mDelegate);
    }

    public String toString() {
        return this.mDelegate.toString();
    }
}
