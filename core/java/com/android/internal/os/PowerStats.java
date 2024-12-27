package com.android.internal.os;

import android.hardware.gnss.GnssSignalType;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.content.NativeLibraryHelper;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public final class PowerStats {
    private static final byte PARCEL_FORMAT_VERSION = 2;
    private static final int PARCEL_FORMAT_VERSION_MASK = 255;
    private static final int STATE_STATS_ARRAY_LENGTH_MASK = 16711680;
    private static final int STATS_ARRAY_LENGTH_MASK = 65280;
    private static final String TAG = "PowerStats";
    private static final int UID_STATS_ARRAY_LENGTH_MASK = -16777216;
    public final Descriptor descriptor;
    public long durationMs;
    public long[] stats;
    private static final BatteryStatsHistory.VarintParceler VARINT_PARCELER =
            new BatteryStatsHistory.VarintParceler();
    private static final int PARCEL_FORMAT_VERSION_SHIFT = Integer.numberOfTrailingZeros(255);
    private static final int STATS_ARRAY_LENGTH_SHIFT = Integer.numberOfTrailingZeros(65280);
    public static final int MAX_STATS_ARRAY_LENGTH = (1 << Integer.bitCount(65280)) - 1;
    private static final int STATE_STATS_ARRAY_LENGTH_SHIFT =
            Integer.numberOfTrailingZeros(16711680);
    public static final int MAX_STATE_STATS_ARRAY_LENGTH = (1 << Integer.bitCount(16711680)) - 1;
    private static final int UID_STATS_ARRAY_LENGTH_SHIFT =
            Integer.numberOfTrailingZeros(-16777216);
    public static final int MAX_UID_STATS_ARRAY_LENGTH = (1 << Integer.bitCount(-16777216)) - 1;
    public final SparseArray<long[]> stateStats = new SparseArray<>();
    public final SparseArray<long[]> uidStats = new SparseArray<>();

    public static class Descriptor {
        public static final String EXTRA_DEVICE_STATS_FORMAT = "format-device";
        public static final String EXTRA_STATE_STATS_FORMAT = "format-state";
        public static final String EXTRA_UID_STATS_FORMAT = "format-uid";
        private static final String XML_ATTR_ID = "id";
        private static final String XML_ATTR_NAME = "name";
        private static final String XML_ATTR_STATE_KEY = "key";
        private static final String XML_ATTR_STATE_LABEL = "label";
        private static final String XML_ATTR_STATE_STATS_ARRAY_LENGTH = "state-stats-array-length";
        private static final String XML_ATTR_STATS_ARRAY_LENGTH = "stats-array-length";
        private static final String XML_ATTR_UID_STATS_ARRAY_LENGTH = "uid-stats-array-length";
        public static final String XML_TAG_DESCRIPTOR = "descriptor";
        private static final String XML_TAG_EXTRAS = "extras";
        private static final String XML_TAG_STATE = "state";
        public final PersistableBundle extras;
        private PowerStatsFormatter mDeviceStatsFormatter;
        private PowerStatsFormatter mStateStatsFormatter;
        private PowerStatsFormatter mUidStatsFormatter;
        public final String name;
        public final int powerComponentId;
        public final SparseArray<String> stateLabels;
        public final int stateStatsArrayLength;
        public final int statsArrayLength;
        public final int uidStatsArrayLength;

        public Descriptor(
                int powerComponentId,
                int statsArrayLength,
                SparseArray<String> stateLabels,
                int stateStatsArrayLength,
                int uidStatsArrayLength,
                PersistableBundle extras) {
            this(
                    powerComponentId,
                    BatteryConsumer.powerComponentIdToString(powerComponentId),
                    statsArrayLength,
                    stateLabels,
                    stateStatsArrayLength,
                    uidStatsArrayLength,
                    extras);
        }

        public Descriptor(
                int customPowerComponentId,
                String name,
                int statsArrayLength,
                SparseArray<String> stateLabels,
                int stateStatsArrayLength,
                int uidStatsArrayLength,
                PersistableBundle extras) {
            if (statsArrayLength > PowerStats.MAX_STATS_ARRAY_LENGTH) {
                throw new IllegalArgumentException(
                        "statsArrayLength is too high. Max = " + PowerStats.MAX_STATS_ARRAY_LENGTH);
            }
            if (stateStatsArrayLength > PowerStats.MAX_STATE_STATS_ARRAY_LENGTH) {
                throw new IllegalArgumentException(
                        "stateStatsArrayLength is too high. Max = "
                                + PowerStats.MAX_STATE_STATS_ARRAY_LENGTH);
            }
            if (uidStatsArrayLength > PowerStats.MAX_UID_STATS_ARRAY_LENGTH) {
                throw new IllegalArgumentException(
                        "uidStatsArrayLength is too high. Max = "
                                + PowerStats.MAX_UID_STATS_ARRAY_LENGTH);
            }
            this.powerComponentId = customPowerComponentId;
            this.name = name;
            this.statsArrayLength = statsArrayLength;
            this.stateLabels = stateLabels != null ? stateLabels : new SparseArray<>();
            this.stateStatsArrayLength = stateStatsArrayLength;
            this.uidStatsArrayLength = uidStatsArrayLength;
            this.extras = extras;
        }

        public PowerStatsFormatter getDeviceStatsFormatter() {
            if (this.mDeviceStatsFormatter == null) {
                this.mDeviceStatsFormatter =
                        new PowerStatsFormatter(this.extras.getString(EXTRA_DEVICE_STATS_FORMAT));
            }
            return this.mDeviceStatsFormatter;
        }

        public PowerStatsFormatter getStateStatsFormatter() {
            if (this.mStateStatsFormatter == null) {
                this.mStateStatsFormatter =
                        new PowerStatsFormatter(this.extras.getString(EXTRA_STATE_STATS_FORMAT));
            }
            return this.mStateStatsFormatter;
        }

        public PowerStatsFormatter getUidStatsFormatter() {
            if (this.mUidStatsFormatter == null) {
                this.mUidStatsFormatter =
                        new PowerStatsFormatter(this.extras.getString(EXTRA_UID_STATS_FORMAT));
            }
            return this.mUidStatsFormatter;
        }

        public String getStateLabel(int key) {
            String label = this.stateLabels.get(key);
            if (label != null) {
                return label;
            }
            return this.name + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + Integer.toHexString(key);
        }

        public void writeSummaryToParcel(Parcel parcel) {
            int firstWord =
                    ((2 << PowerStats.PARCEL_FORMAT_VERSION_SHIFT) & 255)
                            | ((this.statsArrayLength << PowerStats.STATS_ARRAY_LENGTH_SHIFT)
                                    & 65280)
                            | ((this.stateStatsArrayLength
                                            << PowerStats.STATE_STATS_ARRAY_LENGTH_SHIFT)
                                    & 16711680)
                            | ((this.uidStatsArrayLength << PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT)
                                    & (-16777216));
            parcel.writeInt(firstWord);
            parcel.writeInt(this.powerComponentId);
            parcel.writeString(this.name);
            parcel.writeInt(this.stateLabels.size());
            int size = this.stateLabels.size();
            for (int i = 0; i < size; i++) {
                parcel.writeInt(this.stateLabels.keyAt(i));
                parcel.writeString(this.stateLabels.valueAt(i));
            }
            this.extras.writeToParcel(parcel, 0);
        }

        public static Descriptor readSummaryFromParcel(Parcel parcel) {
            int firstWord = parcel.readInt();
            int version = (firstWord & 255) >>> PowerStats.PARCEL_FORMAT_VERSION_SHIFT;
            if (version != 2) {
                Slog.w(
                        PowerStats.TAG,
                        "Cannot read PowerStats from Parcel - the parcel format version has changed"
                            + " from "
                                + version
                                + " to 2");
                return null;
            }
            int statsArrayLength = (65280 & firstWord) >>> PowerStats.STATS_ARRAY_LENGTH_SHIFT;
            int stateStatsArrayLength =
                    (16711680 & firstWord) >>> PowerStats.STATE_STATS_ARRAY_LENGTH_SHIFT;
            int uidStatsArrayLength =
                    ((-16777216) & firstWord) >>> PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT;
            int powerComponentId = parcel.readInt();
            String name = parcel.readString();
            int stateLabelCount = parcel.readInt();
            SparseArray<String> stateLabels = new SparseArray<>(stateLabelCount);
            for (int i = stateLabelCount; i > 0; i--) {
                int key = parcel.readInt();
                String label = parcel.readString();
                stateLabels.put(key, label);
            }
            PersistableBundle extras = parcel.readPersistableBundle();
            return new Descriptor(
                    powerComponentId,
                    name,
                    statsArrayLength,
                    stateLabels,
                    stateStatsArrayLength,
                    uidStatsArrayLength,
                    extras);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Descriptor)) {
                return false;
            }
            Descriptor that = (Descriptor) o;
            return this.powerComponentId == that.powerComponentId
                    && this.statsArrayLength == that.statsArrayLength
                    && this.stateLabels.contentEquals(that.stateLabels)
                    && this.stateStatsArrayLength == that.stateStatsArrayLength
                    && this.uidStatsArrayLength == that.uidStatsArrayLength
                    && Objects.equals(this.name, that.name)
                    && this.extras.size() == that.extras.size()
                    && Bundle.kindofEquals(this.extras, that.extras);
        }

        public void writeXml(TypedXmlSerializer serializer) throws IOException {
            serializer.startTag(null, XML_TAG_DESCRIPTOR);
            serializer.attributeInt(null, "id", this.powerComponentId);
            serializer.attribute(null, "name", this.name);
            serializer.attributeInt(null, XML_ATTR_STATS_ARRAY_LENGTH, this.statsArrayLength);
            serializer.attributeInt(
                    null, XML_ATTR_STATE_STATS_ARRAY_LENGTH, this.stateStatsArrayLength);
            serializer.attributeInt(
                    null, XML_ATTR_UID_STATS_ARRAY_LENGTH, this.uidStatsArrayLength);
            for (int i = this.stateLabels.size() - 1; i >= 0; i--) {
                serializer.startTag(null, "state");
                serializer.attributeInt(null, "key", this.stateLabels.keyAt(i));
                serializer.attribute(null, "label", this.stateLabels.valueAt(i));
                serializer.endTag(null, "state");
            }
            try {
                serializer.startTag(null, "extras");
                this.extras.saveToXml(serializer);
                serializer.endTag(null, "extras");
                serializer.endTag(null, XML_TAG_DESCRIPTOR);
            } catch (XmlPullParserException e) {
                throw new IOException(e);
            }
        }

        public static Descriptor createFromXml(TypedXmlPullParser parser)
                throws XmlPullParserException, IOException {
            int powerComponentId = -1;
            String name = null;
            int statsArrayLength = 0;
            SparseArray<String> stateLabels = new SparseArray<>();
            int stateStatsArrayLength = 0;
            int uidStatsArrayLength = 0;
            PersistableBundle extras = null;
            int eventType = parser.getEventType();
            while (true) {
                if (eventType != 1
                        && (eventType != 3 || !parser.getName().equals(XML_TAG_DESCRIPTOR))) {
                    if (eventType == 2) {
                        switch (parser.getName()) {
                            case "descriptor":
                                powerComponentId = parser.getAttributeInt(null, "id");
                                name = parser.getAttributeValue(null, "name");
                                statsArrayLength =
                                        parser.getAttributeInt(null, XML_ATTR_STATS_ARRAY_LENGTH);
                                int stateStatsArrayLength2 =
                                        parser.getAttributeInt(
                                                null, XML_ATTR_STATE_STATS_ARRAY_LENGTH);
                                int uidStatsArrayLength2 =
                                        parser.getAttributeInt(
                                                null, XML_ATTR_UID_STATS_ARRAY_LENGTH);
                                stateStatsArrayLength = stateStatsArrayLength2;
                                uidStatsArrayLength = uidStatsArrayLength2;
                                break;
                            case "state":
                                int value = parser.getAttributeInt(null, "key");
                                String label = parser.getAttributeValue(null, "label");
                                stateLabels.put(value, label);
                                break;
                            case "extras":
                                extras = PersistableBundle.restoreFromXml(parser);
                                break;
                        }
                    }
                    eventType = parser.next();
                }
            }
            if (powerComponentId == -1) {
                return null;
            }
            if (powerComponentId >= 1000) {
                return new Descriptor(
                        powerComponentId,
                        name,
                        statsArrayLength,
                        stateLabels,
                        stateStatsArrayLength,
                        uidStatsArrayLength,
                        extras);
            }
            if (powerComponentId < 19) {
                return new Descriptor(
                        powerComponentId,
                        statsArrayLength,
                        stateLabels,
                        stateStatsArrayLength,
                        uidStatsArrayLength,
                        extras);
            }
            Slog.e(PowerStats.TAG, "Unrecognized power component: " + powerComponentId);
            return null;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.powerComponentId));
        }

        public String toString() {
            if (this.extras != null) {
                this.extras.size();
            }
            return "PowerStats.Descriptor{powerComponentId="
                    + this.powerComponentId
                    + ", name='"
                    + this.name
                    + DateFormat.QUOTE
                    + ", statsArrayLength="
                    + this.statsArrayLength
                    + ", stateStatsArrayLength="
                    + this.stateStatsArrayLength
                    + ", stateLabels="
                    + this.stateLabels
                    + ", uidStatsArrayLength="
                    + this.uidStatsArrayLength
                    + ", extras="
                    + this.extras
                    + '}';
        }
    }

    public static class DescriptorRegistry {
        private final SparseArray<Descriptor> mDescriptors = new SparseArray<>();

        public void register(Descriptor descriptor) {
            this.mDescriptors.put(descriptor.powerComponentId, descriptor);
        }

        public Descriptor get(int powerComponentId) {
            return this.mDescriptors.get(powerComponentId);
        }
    }

    public PowerStats(Descriptor descriptor) {
        this.descriptor = descriptor;
        this.stats = new long[descriptor.statsArrayLength];
    }

    public void writeToParcel(Parcel parcel) {
        int lengthPos = parcel.dataPosition();
        parcel.writeInt(0);
        int startPos = parcel.dataPosition();
        parcel.writeInt(this.descriptor.powerComponentId);
        parcel.writeLong(this.durationMs);
        VARINT_PARCELER.writeLongArray(parcel, this.stats);
        if (this.descriptor.stateStatsArrayLength != 0) {
            parcel.writeInt(this.stateStats.size());
            for (int i = 0; i < this.stateStats.size(); i++) {
                parcel.writeInt(this.stateStats.keyAt(i));
                VARINT_PARCELER.writeLongArray(parcel, this.stateStats.valueAt(i));
            }
        }
        parcel.writeInt(this.uidStats.size());
        for (int i2 = 0; i2 < this.uidStats.size(); i2++) {
            parcel.writeInt(this.uidStats.keyAt(i2));
            VARINT_PARCELER.writeLongArray(parcel, this.uidStats.valueAt(i2));
        }
        int endPos = parcel.dataPosition();
        parcel.setDataPosition(lengthPos);
        parcel.writeInt(endPos - startPos);
        parcel.setDataPosition(endPos);
    }

    public static PowerStats readFromParcel(Parcel parcel, DescriptorRegistry registry) {
        int powerComponentId;
        int length = parcel.readInt();
        int startPos = parcel.dataPosition();
        int endPos = startPos + length;
        try {
            powerComponentId = parcel.readInt();
        } catch (Throwable th) {
            th = th;
        }
        try {
            Descriptor descriptor = registry.get(powerComponentId);
            if (descriptor == null) {
                Slog.e(TAG, "Unsupported PowerStats for power component ID: " + powerComponentId);
                if (endPos > parcel.dataPosition()) {
                    if (endPos >= parcel.dataSize()) {
                        throw new IndexOutOfBoundsException(
                                "PowerStats end position: "
                                        + endPos
                                        + " is outside the parcel bounds: "
                                        + parcel.dataSize());
                    }
                    parcel.setDataPosition(endPos);
                }
                return null;
            }
            PowerStats stats = new PowerStats(descriptor);
            stats.durationMs = parcel.readLong();
            stats.stats = new long[descriptor.statsArrayLength];
            VARINT_PARCELER.readLongArray(parcel, stats.stats);
            if (descriptor.stateStatsArrayLength != 0) {
                int count = parcel.readInt();
                for (int i = 0; i < count; i++) {
                    int state = parcel.readInt();
                    long[] stateStats = new long[descriptor.stateStatsArrayLength];
                    VARINT_PARCELER.readLongArray(parcel, stateStats);
                    stats.stateStats.put(state, stateStats);
                }
            }
            int uidCount = parcel.readInt();
            for (int i2 = 0; i2 < uidCount; i2++) {
                int uid = parcel.readInt();
                long[] uidStats = new long[descriptor.uidStatsArrayLength];
                VARINT_PARCELER.readLongArray(parcel, uidStats);
                stats.uidStats.put(uid, uidStats);
            }
            int i3 = parcel.dataPosition();
            if (i3 == endPos) {
                if (endPos > parcel.dataPosition()) {
                    if (endPos >= parcel.dataSize()) {
                        throw new IndexOutOfBoundsException(
                                "PowerStats end position: "
                                        + endPos
                                        + " is outside the parcel bounds: "
                                        + parcel.dataSize());
                    }
                    parcel.setDataPosition(endPos);
                }
                return stats;
            }
            Slog.e(
                    TAG,
                    "Corrupted PowerStats parcel. Expected length: "
                            + length
                            + ", actual length: "
                            + (parcel.dataPosition() - startPos));
            if (endPos <= parcel.dataPosition()) {
                return null;
            }
            if (endPos >= parcel.dataSize()) {
                throw new IndexOutOfBoundsException(
                        "PowerStats end position: "
                                + endPos
                                + " is outside the parcel bounds: "
                                + parcel.dataSize());
            }
            parcel.setDataPosition(endPos);
            return null;
        } catch (Throwable th2) {
            th = th2;
            if (endPos > parcel.dataPosition()) {
                if (endPos >= parcel.dataSize()) {
                    throw new IndexOutOfBoundsException(
                            "PowerStats end position: "
                                    + endPos
                                    + " is outside the parcel bounds: "
                                    + parcel.dataSize());
                }
                parcel.setDataPosition(endPos);
            }
            throw th;
        }
    }

    public String formatForBatteryHistory(String uidPrefix) {
        StringBuilder sb = new StringBuilder();
        sb.append("duration=").append(this.durationMs).append(" ").append(this.descriptor.name);
        if (this.stats.length > 0) {
            sb.append("=").append(this.descriptor.getDeviceStatsFormatter().format(this.stats));
        }
        if (this.descriptor.stateStatsArrayLength != 0) {
            PowerStatsFormatter formatter = this.descriptor.getStateStatsFormatter();
            for (int i = 0; i < this.stateStats.size(); i++) {
                sb.append(" (");
                sb.append(this.descriptor.getStateLabel(this.stateStats.keyAt(i)));
                sb.append(") ");
                sb.append(formatter.format(this.stateStats.valueAt(i)));
            }
        }
        PowerStatsFormatter uidStatsFormatter = this.descriptor.getUidStatsFormatter();
        for (int i2 = 0; i2 < this.uidStats.size(); i2++) {
            sb.append(uidPrefix)
                    .append(UserHandle.formatUid(this.uidStats.keyAt(i2)))
                    .append(": ")
                    .append(uidStatsFormatter.format(this.uidStats.valueAt(i2)));
        }
        return sb.toString();
    }

    public void dump(IndentingPrintWriter pw) {
        pw.println(this.descriptor.name + " (" + this.descriptor.powerComponentId + ')');
        pw.increaseIndent();
        pw.print("duration", Long.valueOf(this.durationMs)).println();
        if (this.descriptor.statsArrayLength != 0) {
            pw.println(this.descriptor.getDeviceStatsFormatter().format(this.stats));
        }
        if (this.descriptor.stateStatsArrayLength != 0) {
            PowerStatsFormatter formatter = this.descriptor.getStateStatsFormatter();
            for (int i = 0; i < this.stateStats.size(); i++) {
                pw.print(" (");
                pw.print(this.descriptor.getStateLabel(this.stateStats.keyAt(i)));
                pw.print(") ");
                pw.print(formatter.format(this.stateStats.valueAt(i)));
                pw.println();
            }
        }
        PowerStatsFormatter uidStatsFormatter = this.descriptor.getUidStatsFormatter();
        for (int i2 = 0; i2 < this.uidStats.size(); i2++) {
            String formattedStats = uidStatsFormatter.format(this.uidStats.valueAt(i2));
            if (!formattedStats.isBlank()) {
                pw.print("UID ");
                pw.print(UserHandle.formatUid(this.uidStats.keyAt(i2)));
                pw.print(": ");
                pw.print(formattedStats);
                pw.println();
            }
        }
        pw.decreaseIndent();
    }

    public String toString() {
        return "PowerStats: " + formatForBatteryHistory(" UID ");
    }

    public static class PowerStatsFormatter {
        private static final double NANO_TO_MILLI_MULTIPLIER = 1.0E-6d;
        private static final Pattern SECTION_PATTERN =
                Pattern.compile("([^:]+):(\\d+)(\\[(?<L>\\d+)])?(?<F>\\S*)\\s*");
        private final List<Section> mSections;

        private static class Section {
            public String label;
            public int length;
            public boolean optional;
            public int position;
            public boolean typePower;

            private Section() {}
        }

        public PowerStatsFormatter(String format) {
            this.mSections = parseFormat(format);
        }

        public String format(long[] stats) {
            return format(this.mSections, stats);
        }

        private List<Section> parseFormat(String format) {
            if (format == null || format.isBlank()) {
                return null;
            }
            ArrayList<Section> sections = new ArrayList<>();
            Matcher matcher = SECTION_PATTERN.matcher(format);
            for (int position = 0; position < format.length(); position = matcher.end()) {
                if (!matcher.find() || matcher.start() != position) {
                    Slog.wtf(PowerStats.TAG, "Bad power stats format '" + format + "'");
                    return null;
                }
                Section section = new Section();
                section.label = matcher.group(1);
                section.position = Integer.parseUnsignedInt(matcher.group(2));
                String length = matcher.group(GnssSignalType.CODE_TYPE_L);
                if (length != null) {
                    section.length = Integer.parseUnsignedInt(length);
                } else {
                    section.length = 1;
                }
                String flags = matcher.group("F");
                if (flags != null) {
                    for (int i = 0; i < flags.length(); i++) {
                        char flag = flags.charAt(i);
                        switch (flag) {
                            case '?':
                                section.optional = true;
                                break;
                            case 'p':
                                section.typePower = true;
                                break;
                            default:
                                Slog.e(
                                        PowerStats.TAG,
                                        "Unsupported format option '" + flag + "' in " + format);
                                break;
                        }
                    }
                }
                sections.add(section);
            }
            return sections;
        }

        private String format(List<Section> sections, long[] stats) {
            if (sections == null) {
                return Arrays.toString(stats);
            }
            StringBuilder sb = new StringBuilder();
            int count = sections.size();
            for (int i = 0; i < count; i++) {
                Section section = sections.get(i);
                if (section.length != 0) {
                    if (section.optional) {
                        boolean nonZero = false;
                        int offset = 0;
                        while (true) {
                            if (offset >= section.length) {
                                break;
                            }
                            if (stats[section.position + offset] == 0) {
                                offset++;
                            } else {
                                nonZero = true;
                                break;
                            }
                        }
                        if (!nonZero) {}
                    }
                    boolean nonZero2 = sb.isEmpty();
                    if (!nonZero2) {
                        sb.append(' ');
                    }
                    sb.append(section.label).append(": ");
                    if (section.length != 1) {
                        sb.append('[');
                    }
                    for (int offset2 = 0; offset2 < section.length; offset2++) {
                        if (offset2 != 0) {
                            sb.append(", ");
                        }
                        if (section.typePower) {
                            sb.append(
                                    BatteryStats.formatCharge(
                                            stats[section.position + offset2]
                                                    * NANO_TO_MILLI_MULTIPLIER));
                        } else {
                            sb.append(stats[section.position + offset2]);
                        }
                    }
                    int offset3 = section.length;
                    if (offset3 != 1) {
                        sb.append(']');
                    }
                }
            }
            return sb.toString();
        }
    }
}
