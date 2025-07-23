package android.app.timedetector;

import android.app.time.UnixEpochTime;
import android.os.Parcel;
import android.os.ShellCommand;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TimeSuggestionHelper {
    private ArrayList<String> mDebugInfo;
    private final Class<?> mHelpedClass;
    private final UnixEpochTime mUnixEpochTime;

    public TimeSuggestionHelper(Class<?> cls, UnixEpochTime unixEpochTime) {
        this.mHelpedClass = (Class) Objects.requireNonNull(cls);
        this.mUnixEpochTime = (UnixEpochTime) Objects.requireNonNull(unixEpochTime);
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

    public void addDebugInfo(String... strArr) {
        addDebugInfo(Arrays.asList(strArr));
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return handleEquals((TimeSuggestionHelper) obj);
    }

    public boolean handleEquals(TimeSuggestionHelper timeSuggestionHelper) {
        return Objects.equals(this.mHelpedClass, timeSuggestionHelper.mHelpedClass) && Objects.equals(this.mUnixEpochTime, timeSuggestionHelper.mUnixEpochTime);
    }

    public int hashCode() {
        return Objects.hash(this.mUnixEpochTime);
    }

    public String handleToString() {
        return this.mHelpedClass.getSimpleName() + "{mUnixEpochTime=" + this.mUnixEpochTime + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static TimeSuggestionHelper handleCreateFromParcel(Class<?> cls, Parcel parcel) {
        TimeSuggestionHelper timeSuggestionHelper = new TimeSuggestionHelper(cls, (UnixEpochTime) parcel.readParcelable(null, UnixEpochTime.class));
        timeSuggestionHelper.mDebugInfo = parcel.readArrayList(null, String.class);
        return timeSuggestionHelper;
    }

    public void handleWriteToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mUnixEpochTime, 0);
        parcel.writeList(this.mDebugInfo);
    }

    public static TimeSuggestionHelper handleParseCommandLineArg(Class<?> cls, ShellCommand shellCommand) throws IllegalArgumentException {
        Long l = null;
        Long l2 = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (l == null) {
                    throw new IllegalArgumentException("No referenceTimeMillis specified.");
                }
                if (l2 == null) {
                    throw new IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                TimeSuggestionHelper timeSuggestionHelper = new TimeSuggestionHelper(cls, new UnixEpochTime(l.longValue(), l2.longValue()));
                timeSuggestionHelper.addDebugInfo("Command line injection");
                return timeSuggestionHelper;
            }
            nextArg.hashCode();
            switch (nextArg) {
                case "--reference_time":
                case "--elapsed_realtime":
                    l = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--unix_epoch_time":
                    l2 = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    public static void handlePrintCommandLineOpts(PrintWriter printWriter, String str, Class<?> cls) {
        printWriter.printf("%s suggestion options:\n", str);
        printWriter.println("  --elapsed_realtime <elapsed realtime millis> - the elapsed realtime millis when unix epoch time was read");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println();
        printWriter.println("See " + cls.getName() + " for more information");
    }
}
