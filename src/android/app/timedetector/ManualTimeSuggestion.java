package android.app.timedetector;

import android.app.time.UnixEpochTime;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ManualTimeSuggestion implements Parcelable {
    public static final Parcelable.Creator<ManualTimeSuggestion> CREATOR = new Parcelable.Creator<ManualTimeSuggestion>() { // from class: android.app.timedetector.ManualTimeSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManualTimeSuggestion createFromParcel(Parcel parcel) {
            return new ManualTimeSuggestion(TimeSuggestionHelper.handleCreateFromParcel(ManualTimeSuggestion.class, parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManualTimeSuggestion[] newArray(int i) {
            return new ManualTimeSuggestion[i];
        }
    };
    private final TimeSuggestionHelper mTimeSuggestionHelper;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ManualTimeSuggestion(UnixEpochTime unixEpochTime) {
        this.mTimeSuggestionHelper = new TimeSuggestionHelper(ManualTimeSuggestion.class, unixEpochTime);
    }

    private ManualTimeSuggestion(TimeSuggestionHelper timeSuggestionHelper) {
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
        return this.mTimeSuggestionHelper.handleEquals(((ManualTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }

    public static ManualTimeSuggestion parseCommandLineArg(ShellCommand shellCommand) throws IllegalArgumentException {
        return new ManualTimeSuggestion(TimeSuggestionHelper.handleParseCommandLineArg(ManualTimeSuggestion.class, shellCommand));
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        TimeSuggestionHelper.handlePrintCommandLineOpts(printWriter, "Manual", ManualTimeSuggestion.class);
    }
}
