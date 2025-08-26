package android.app.timezonedetector;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ShellCommand;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TelephonyTimeZoneSuggestion implements Parcelable {
    public static final Parcelable.Creator<TelephonyTimeZoneSuggestion> CREATOR = new Parcelable.Creator<TelephonyTimeZoneSuggestion>() { // from class: android.app.timezonedetector.TelephonyTimeZoneSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyTimeZoneSuggestion createFromParcel(Parcel parcel) {
            return TelephonyTimeZoneSuggestion.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyTimeZoneSuggestion[] newArray(int i) {
            return new TelephonyTimeZoneSuggestion[i];
        }
    };
    public static final int MATCH_TYPE_EMULATOR_ZONE_ID = 4;
    public static final int MATCH_TYPE_NA = 0;
    public static final int MATCH_TYPE_NETWORK_COUNTRY_AND_OFFSET = 3;
    public static final int MATCH_TYPE_NETWORK_COUNTRY_ONLY = 2;
    public static final int MATCH_TYPE_TEST_NETWORK_OFFSET_ONLY = 5;
    public static final int QUALITY_MULTIPLE_ZONES_WITH_DIFFERENT_OFFSETS = 3;
    public static final int QUALITY_MULTIPLE_ZONES_WITH_SAME_OFFSET = 2;
    public static final int QUALITY_NA = 0;
    public static final int QUALITY_SINGLE_ZONE = 1;
    public String mCountryIso;
    private List<String> mDebugInfo;
    private final int mMatchType;
    private final int mQuality;
    private final int mSlotIndex;
    private final String mZoneId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MatchType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Quality {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static TelephonyTimeZoneSuggestion createEmptySuggestion(int i, String str) {
        return new Builder(i).addDebugInfo(str).build();
    }

    private TelephonyTimeZoneSuggestion(Builder builder) {
        this.mCountryIso = "";
        this.mSlotIndex = builder.mSlotIndex;
        this.mZoneId = builder.mZoneId;
        this.mMatchType = builder.mMatchType;
        this.mQuality = builder.mQuality;
        this.mDebugInfo = builder.mDebugInfo != null ? new ArrayList(builder.mDebugInfo) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TelephonyTimeZoneSuggestion createFromParcel(Parcel parcel) {
        TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestionBuild = new Builder(parcel.readInt()).setZoneId(parcel.readString()).setMatchType(parcel.readInt()).setQuality(parcel.readInt()).build();
        ArrayList arrayList = parcel.readArrayList(TelephonyTimeZoneSuggestion.class.getClassLoader(), String.class);
        if (arrayList != null) {
            telephonyTimeZoneSuggestionBuild.addDebugInfo(arrayList);
        }
        telephonyTimeZoneSuggestionBuild.mCountryIso = parcel.readString();
        return telephonyTimeZoneSuggestionBuild;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSlotIndex);
        parcel.writeString(this.mZoneId);
        parcel.writeInt(this.mMatchType);
        parcel.writeInt(this.mQuality);
        parcel.writeList(this.mDebugInfo);
        parcel.writeString(this.mCountryIso);
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public String getZoneId() {
        return this.mZoneId;
    }

    public int getMatchType() {
        return this.mMatchType;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public List<String> getDebugInfo() {
        List<String> list = this.mDebugInfo;
        return list == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(list);
    }

    public void addDebugInfo(String str) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList();
        }
        this.mDebugInfo.add(str);
    }

    public void addDebugInfo(List<String> list) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList(list.size());
        }
        this.mDebugInfo.addAll(list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion = (TelephonyTimeZoneSuggestion) obj;
            if (this.mSlotIndex == telephonyTimeZoneSuggestion.mSlotIndex && this.mMatchType == telephonyTimeZoneSuggestion.mMatchType && this.mQuality == telephonyTimeZoneSuggestion.mQuality && this.mCountryIso == telephonyTimeZoneSuggestion.mCountryIso && Objects.equals(this.mZoneId, telephonyTimeZoneSuggestion.mZoneId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mSlotIndex), this.mZoneId, Integer.valueOf(this.mMatchType), Integer.valueOf(this.mQuality), this.mCountryIso);
    }

    public String toString() {
        return "TelephonyTimeZoneSuggestion{mSlotIndex=" + this.mSlotIndex + ", mZoneId='" + this.mZoneId + "', mMatchType=" + this.mMatchType + ", mQuality=" + this.mQuality + ", mDebugInfo=" + this.mDebugInfo + ", mCountryIso=" + this.mCountryIso + '}';
    }

    public static final class Builder {
        private List<String> mDebugInfo;
        private int mMatchType;
        private int mQuality;
        private final int mSlotIndex;
        private String mZoneId;

        public Builder(int i) {
            this.mSlotIndex = i;
        }

        public Builder setZoneId(String str) {
            this.mZoneId = str;
            return this;
        }

        public Builder setMatchType(int i) {
            this.mMatchType = i;
            return this;
        }

        public Builder setQuality(int i) {
            this.mQuality = i;
            return this;
        }

        public Builder addDebugInfo(String str) {
            if (this.mDebugInfo == null) {
                this.mDebugInfo = new ArrayList();
            }
            this.mDebugInfo.add(str);
            return this;
        }

        void validate() {
            int i = this.mQuality;
            int i2 = this.mMatchType;
            if (this.mZoneId == null) {
                if (i == 0 && i2 == 0) {
                    return;
                }
                throw new RuntimeException("Invalid quality or match type for null zone ID. quality=" + i + ", matchType=" + i2);
            }
            boolean z = i == 1 || i == 2 || i == 3;
            boolean z2 = i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5;
            if (z && z2) {
                return;
            }
            throw new RuntimeException("Invalid quality or match type with zone ID. quality=" + i + ", matchType=" + i2);
        }

        public TelephonyTimeZoneSuggestion build() {
            validate();
            return new TelephonyTimeZoneSuggestion(this);
        }
    }

    public static TelephonyTimeZoneSuggestion parseCommandLineArg(ShellCommand shellCommand) throws IllegalArgumentException {
        Integer numValueOf = null;
        String nextArgRequired = null;
        Integer numValueOf2 = null;
        Integer numValueOf3 = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (numValueOf == null) {
                    throw new IllegalArgumentException("No slotIndex specified.");
                }
                Builder builder = new Builder(numValueOf.intValue());
                if (!TextUtils.isEmpty(nextArgRequired) && !Session.SESSION_SEPARATION_CHAR_CHILD.equals(nextArgRequired)) {
                    builder.setZoneId(nextArgRequired);
                }
                if (numValueOf2 != null) {
                    builder.setQuality(numValueOf2.intValue());
                }
                if (numValueOf3 != null) {
                    builder.setMatchType(numValueOf3.intValue());
                }
                builder.addDebugInfo("Command line injection");
                return builder.build();
            }
            nextArg.hashCode();
            switch (nextArg) {
                case "--match_type":
                    numValueOf3 = Integer.valueOf(parseMatchTypeCommandLineArg(shellCommand.getNextArgRequired()));
                    break;
                case "--zone_id":
                    nextArgRequired = shellCommand.getNextArgRequired();
                    break;
                case "--quality":
                    numValueOf2 = Integer.valueOf(parseQualityCommandLineArg(shellCommand.getNextArgRequired()));
                    break;
                case "--slot_index":
                    numValueOf = Integer.valueOf(Integer.parseInt(shellCommand.getNextArgRequired()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option: " + nextArg);
            }
        }
    }

    private static int parseQualityCommandLineArg(String str) {
        str.hashCode();
        switch (str) {
            case "single":
                return 1;
            case "multiple_same":
                return 2;
            case "multiple_different":
                return 3;
            default:
                throw new IllegalArgumentException("Unrecognized quality: " + str);
        }
    }

    private static int parseMatchTypeCommandLineArg(String str) {
        str.hashCode();
        switch (str) {
            case "country_with_offset":
                return 3;
            case "test_network":
                return 5;
            case "country":
                return 2;
            case "emulator":
                return 4;
            default:
                throw new IllegalArgumentException("Unrecognized match_type: " + str);
        }
    }

    public static void printCommandLineOpts(PrintWriter printWriter) {
        printWriter.println("Telephony suggestion options:");
        printWriter.println("  --slot_index <number>");
        printWriter.println("  To withdraw a previous suggestion:");
        printWriter.println("    [--zone_id \"_\"]");
        printWriter.println("  To make a new suggestion:");
        printWriter.println("    --zone_id <Olson ID>");
        printWriter.println("    --quality <single|multiple_same|multiple_different>");
        printWriter.println("    --match_type <emulator|country_with_offset|country|test_network>");
        printWriter.println();
        printWriter.println("See " + TelephonyTimeZoneSuggestion.class.getName() + " for more information");
    }
}
