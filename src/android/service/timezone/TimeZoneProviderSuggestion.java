package android.service.timezone;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class TimeZoneProviderSuggestion implements Parcelable {
    public static final Parcelable.Creator<TimeZoneProviderSuggestion> CREATOR = new Parcelable.Creator<TimeZoneProviderSuggestion>() { // from class: android.service.timezone.TimeZoneProviderSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneProviderSuggestion createFromParcel(Parcel parcel) {
            return new TimeZoneProviderSuggestion(parcel.readArrayList(null, String.class), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneProviderSuggestion[] newArray(int i) {
            return new TimeZoneProviderSuggestion[i];
        }
    };
    private final long mElapsedRealtimeMillis;
    private final List<String> mTimeZoneIds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeZoneProviderSuggestion(List<String> list, long j) {
        this.mTimeZoneIds = immutableList(list);
        this.mElapsedRealtimeMillis = j;
    }

    public long getElapsedRealtimeMillis() {
        return this.mElapsedRealtimeMillis;
    }

    public List<String> getTimeZoneIds() {
        return this.mTimeZoneIds;
    }

    public String toString() {
        return "TimeZoneProviderSuggestion{mTimeZoneIds=" + this.mTimeZoneIds + ", mElapsedRealtimeMillis=" + this.mElapsedRealtimeMillis + NavigationBarInflaterView.KEY_CODE_START + Duration.ofMillis(this.mElapsedRealtimeMillis) + ")}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.mTimeZoneIds);
        parcel.writeLong(this.mElapsedRealtimeMillis);
    }

    public boolean isEquivalentTo(TimeZoneProviderSuggestion timeZoneProviderSuggestion) {
        if (this == timeZoneProviderSuggestion) {
            return true;
        }
        if (timeZoneProviderSuggestion == null) {
            return false;
        }
        return this.mTimeZoneIds.equals(timeZoneProviderSuggestion.mTimeZoneIds);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneProviderSuggestion timeZoneProviderSuggestion = (TimeZoneProviderSuggestion) obj;
            if (this.mElapsedRealtimeMillis == timeZoneProviderSuggestion.mElapsedRealtimeMillis && this.mTimeZoneIds.equals(timeZoneProviderSuggestion.mTimeZoneIds)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mTimeZoneIds, Long.valueOf(this.mElapsedRealtimeMillis));
    }

    public static final class Builder {
        private List<String> mTimeZoneIds = Collections.EMPTY_LIST;
        private long mElapsedRealtimeMillis = SystemClock.elapsedRealtime();

        public Builder setTimeZoneIds(List<String> list) {
            this.mTimeZoneIds = (List) Objects.requireNonNull(list);
            return this;
        }

        public Builder setElapsedRealtimeMillis(long j) {
            this.mElapsedRealtimeMillis = j;
            return this;
        }

        public TimeZoneProviderSuggestion build() {
            return new TimeZoneProviderSuggestion(this.mTimeZoneIds, this.mElapsedRealtimeMillis);
        }
    }

    private static List<String> immutableList(List<String> list) {
        Objects.requireNonNull(list);
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList(new ArrayList(list));
    }
}
