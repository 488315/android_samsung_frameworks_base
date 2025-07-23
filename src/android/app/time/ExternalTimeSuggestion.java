package android.app.time;

import android.annotation.SystemApi;
import android.app.timedetector.TimeSuggestionHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class ExternalTimeSuggestion implements Parcelable {
    public static final Parcelable.Creator<ExternalTimeSuggestion> CREATOR = new Parcelable.Creator<ExternalTimeSuggestion>() { // from class: android.app.time.ExternalTimeSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExternalTimeSuggestion createFromParcel(Parcel parcel) {
            return new ExternalTimeSuggestion(TimeSuggestionHelper.handleCreateFromParcel(ExternalTimeSuggestion.class, parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExternalTimeSuggestion[] newArray(int i) {
            return new ExternalTimeSuggestion[i];
        }
    };
    private final TimeSuggestionHelper mTimeSuggestionHelper;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExternalTimeSuggestion(long j, long j2) {
        this.mTimeSuggestionHelper = new TimeSuggestionHelper(ExternalTimeSuggestion.class, new UnixEpochTime(j, j2));
    }

    private ExternalTimeSuggestion(TimeSuggestionHelper timeSuggestionHelper) {
        this.mTimeSuggestionHelper = (TimeSuggestionHelper) Objects.requireNonNull(timeSuggestionHelper);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mTimeSuggestionHelper.handleWriteToParcel(parcel, i);
    }

    public UnixEpochTime getUnixEpochTime() {
        return this.mTimeSuggestionHelper.getUnixEpochTime();
    }

    public List<String> getDebugInfo() {
        return this.mTimeSuggestionHelper.getDebugInfo();
    }

    public void addDebugInfo(String... strArr) {
        this.mTimeSuggestionHelper.addDebugInfo(strArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mTimeSuggestionHelper.handleEquals(((ExternalTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }

    public static ExternalTimeSuggestion parseCommandLineArg(ShellCommand shellCommand) throws IllegalArgumentException {
        return new ExternalTimeSuggestion(TimeSuggestionHelper.handleParseCommandLineArg(ExternalTimeSuggestion.class, shellCommand));
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        TimeSuggestionHelper.handlePrintCommandLineOpts(printWriter, "External", ExternalTimeSuggestion.class);
    }
}
