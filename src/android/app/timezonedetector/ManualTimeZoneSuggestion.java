package android.app.timezonedetector;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ManualTimeZoneSuggestion implements Parcelable {
    public static final Parcelable.Creator<ManualTimeZoneSuggestion> CREATOR = new Parcelable.Creator<ManualTimeZoneSuggestion>() { // from class: android.app.timezonedetector.ManualTimeZoneSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManualTimeZoneSuggestion createFromParcel(Parcel parcel) {
            return ManualTimeZoneSuggestion.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManualTimeZoneSuggestion[] newArray(int i) {
            return new ManualTimeZoneSuggestion[i];
        }
    };
    private ArrayList<String> mDebugInfo;
    private final String mZoneId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ManualTimeZoneSuggestion(String str) {
        this.mZoneId = (String) Objects.requireNonNull(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ManualTimeZoneSuggestion createFromParcel(Parcel parcel) {
        ManualTimeZoneSuggestion manualTimeZoneSuggestion = new ManualTimeZoneSuggestion(parcel.readString());
        manualTimeZoneSuggestion.mDebugInfo = parcel.readArrayList(null, String.class);
        return manualTimeZoneSuggestion;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mZoneId);
        parcel.writeList(this.mDebugInfo);
    }

    public String getZoneId() {
        return this.mZoneId;
    }

    public List<String> getDebugInfo() {
        ArrayList<String> arrayList = this.mDebugInfo;
        return arrayList == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(arrayList);
    }

    public void addDebugInfo(String... strArr) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList<>();
        }
        this.mDebugInfo.addAll(Arrays.asList(strArr));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mZoneId, ((ManualTimeZoneSuggestion) obj).mZoneId);
    }

    public int hashCode() {
        return Objects.hash(this.mZoneId);
    }

    public String toString() {
        return "ManualTimeZoneSuggestion{mZoneId=" + this.mZoneId + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static ManualTimeZoneSuggestion parseCommandLineArg(ShellCommand shellCommand) {
        String str = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                nextArg.hashCode();
                if (nextArg.equals("--zone_id")) {
                    str = shellCommand.getNextArgRequired();
                } else {
                    throw new IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                ManualTimeZoneSuggestion manualTimeZoneSuggestion = new ManualTimeZoneSuggestion(str);
                manualTimeZoneSuggestion.addDebugInfo("Command line injection");
                return manualTimeZoneSuggestion;
            }
        }
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        printWriter.println("Manual suggestion options:");
        printWriter.println("  --zone_id <Olson ID>");
        printWriter.println();
        printWriter.println("See " + ManualTimeZoneSuggestion.class.getName() + " for more information");
    }
}
