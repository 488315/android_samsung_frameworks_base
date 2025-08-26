package android.app.timedetector;

import android.app.time.UnixEpochTime;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TelephonyTimeSuggestion implements Parcelable {
    public static final Parcelable.Creator<TelephonyTimeSuggestion> CREATOR = new Parcelable.Creator<TelephonyTimeSuggestion>() { // from class: android.app.timedetector.TelephonyTimeSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyTimeSuggestion createFromParcel(Parcel parcel) {
            return TelephonyTimeSuggestion.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyTimeSuggestion[] newArray(int i) {
            return new TelephonyTimeSuggestion[i];
        }
    };
    private ArrayList<String> mDebugInfo;
    private final int mSlotIndex;
    private final UnixEpochTime mUnixEpochTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TelephonyTimeSuggestion(Builder builder) {
        this.mSlotIndex = builder.mSlotIndex;
        this.mUnixEpochTime = builder.mUnixEpochTime;
        this.mDebugInfo = builder.mDebugInfo != null ? new ArrayList<>(builder.mDebugInfo) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TelephonyTimeSuggestion createFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        TelephonyTimeSuggestion telephonyTimeSuggestionBuild = new Builder(i).setUnixEpochTime((UnixEpochTime) parcel.readParcelable(null, UnixEpochTime.class)).build();
        ArrayList arrayList = parcel.readArrayList(null, String.class);
        if (arrayList != null) {
            telephonyTimeSuggestionBuild.addDebugInfo(arrayList);
        }
        return telephonyTimeSuggestionBuild;
    }

    public static TelephonyTimeSuggestion parseCommandLineArg(ShellCommand shellCommand) throws IllegalArgumentException {
        Integer numValueOf = null;
        Long lValueOf = null;
        Long lValueOf2 = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (numValueOf == null) {
                    throw new IllegalArgumentException("No slotIndex specified.");
                }
                if (lValueOf == null) {
                    throw new IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (lValueOf2 == null) {
                    throw new IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                return new Builder(numValueOf.intValue()).setUnixEpochTime(new UnixEpochTime(lValueOf.longValue(), lValueOf2.longValue())).addDebugInfo("Command line injection").build();
            }
            nextArg.hashCode();
            switch (nextArg) {
                case "--reference_time":
                case "--elapsed_realtime":
                    lValueOf = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--unix_epoch_time":
                    lValueOf2 = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--slot_index":
                    numValueOf = Integer.valueOf(Integer.parseInt(shellCommand.getNextArgRequired()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        printWriter.println("Telephony suggestion options:");
        printWriter.println("  --slot_index <number>");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis>");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println();
        printWriter.println("See " + TelephonyTimeSuggestion.class.getName() + " for more information");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSlotIndex);
        parcel.writeParcelable(this.mUnixEpochTime, 0);
        parcel.writeList(this.mDebugInfo);
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public UnixEpochTime getUnixEpochTime() {
        return this.mUnixEpochTime;
    }

    public List<String> getDebugInfo() {
        ArrayList<String> arrayList = this.mDebugInfo;
        return arrayList == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(arrayList);
    }

    public void addDebugInfo(String str) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList<>();
        }
        this.mDebugInfo.add(str);
    }

    public void addDebugInfo(List<String> list) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList<>(list.size());
        }
        this.mDebugInfo.addAll(list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TelephonyTimeSuggestion telephonyTimeSuggestion = (TelephonyTimeSuggestion) obj;
            if (this.mSlotIndex == telephonyTimeSuggestion.mSlotIndex && Objects.equals(this.mUnixEpochTime, telephonyTimeSuggestion.mUnixEpochTime)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSlotIndex), this.mUnixEpochTime);
    }

    public String toString() {
        return "TelephonyTimeSuggestion{mSlotIndex='" + this.mSlotIndex + "', mUnixEpochTime=" + this.mUnixEpochTime + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static final class Builder {
        private List<String> mDebugInfo;
        private final int mSlotIndex;
        private UnixEpochTime mUnixEpochTime;

        public Builder(int i) {
            this.mSlotIndex = i;
        }

        public Builder setUnixEpochTime(UnixEpochTime unixEpochTime) {
            this.mUnixEpochTime = unixEpochTime;
            return this;
        }

        public Builder addDebugInfo(String str) {
            if (this.mDebugInfo == null) {
                this.mDebugInfo = new ArrayList();
            }
            this.mDebugInfo.add(str);
            return this;
        }

        public TelephonyTimeSuggestion build() {
            return new TelephonyTimeSuggestion(this);
        }
    }
}
