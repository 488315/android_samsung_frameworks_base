package com.android.internal.os;

import android.hardware.gnss.GnssSignalType;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.BatteryStatsHistory;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParserException;

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
    private static final BatteryStatsHistory.VarintParceler VARINT_PARCELER = new BatteryStatsHistory.VarintParceler();
    private static final int PARCEL_FORMAT_VERSION_SHIFT = Integer.numberOfTrailingZeros(255);
    private static final int STATS_ARRAY_LENGTH_SHIFT = Integer.numberOfTrailingZeros(65280);
    public static final int MAX_STATS_ARRAY_LENGTH = (1 << Integer.bitCount(65280)) - 1;
    private static final int STATE_STATS_ARRAY_LENGTH_SHIFT = Integer.numberOfTrailingZeros(16711680);
    public static final int MAX_STATE_STATS_ARRAY_LENGTH = (1 << Integer.bitCount(16711680)) - 1;
    private static final int UID_STATS_ARRAY_LENGTH_SHIFT = Integer.numberOfTrailingZeros(-16777216);
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

        public Descriptor(int i, int i2, SparseArray<String> sparseArray, int i3, int i4, PersistableBundle persistableBundle) {
            this(i, BatteryConsumer.powerComponentIdToString(i), i2, sparseArray, i3, i4, persistableBundle);
        }

        public Descriptor(int i, String str, int i2, SparseArray<String> sparseArray, int i3, int i4, PersistableBundle persistableBundle) {
            if (i2 > PowerStats.MAX_STATS_ARRAY_LENGTH) {
                throw new IllegalArgumentException("statsArrayLength is too high. Max = " + PowerStats.MAX_STATS_ARRAY_LENGTH);
            }
            if (i3 > PowerStats.MAX_STATE_STATS_ARRAY_LENGTH) {
                throw new IllegalArgumentException("stateStatsArrayLength is too high. Max = " + PowerStats.MAX_STATE_STATS_ARRAY_LENGTH);
            }
            if (i4 > PowerStats.MAX_UID_STATS_ARRAY_LENGTH) {
                throw new IllegalArgumentException("uidStatsArrayLength is too high. Max = " + PowerStats.MAX_UID_STATS_ARRAY_LENGTH);
            }
            this.powerComponentId = i;
            this.name = str;
            this.statsArrayLength = i2;
            this.stateLabels = sparseArray == null ? new SparseArray<>() : sparseArray;
            this.stateStatsArrayLength = i3;
            this.uidStatsArrayLength = i4;
            this.extras = persistableBundle;
        }

        public PowerStatsFormatter getDeviceStatsFormatter() {
            if (this.mDeviceStatsFormatter == null) {
                this.mDeviceStatsFormatter = new PowerStatsFormatter(this.extras.getString(EXTRA_DEVICE_STATS_FORMAT));
            }
            return this.mDeviceStatsFormatter;
        }

        public PowerStatsFormatter getStateStatsFormatter() {
            if (this.mStateStatsFormatter == null) {
                this.mStateStatsFormatter = new PowerStatsFormatter(this.extras.getString(EXTRA_STATE_STATS_FORMAT));
            }
            return this.mStateStatsFormatter;
        }

        public PowerStatsFormatter getUidStatsFormatter() {
            if (this.mUidStatsFormatter == null) {
                this.mUidStatsFormatter = new PowerStatsFormatter(this.extras.getString(EXTRA_UID_STATS_FORMAT));
            }
            return this.mUidStatsFormatter;
        }

        public String getStateLabel(int i) {
            String str = this.stateLabels.get(i);
            if (str != null) {
                return str;
            }
            return this.name + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + Integer.toHexString(i);
        }

        public void writeSummaryToParcel(Parcel parcel) {
            parcel.writeInt(((2 << PowerStats.PARCEL_FORMAT_VERSION_SHIFT) & 255) | ((this.statsArrayLength << PowerStats.STATS_ARRAY_LENGTH_SHIFT) & 65280) | ((this.stateStatsArrayLength << PowerStats.STATE_STATS_ARRAY_LENGTH_SHIFT) & 16711680) | ((this.uidStatsArrayLength << PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT) & (-16777216)));
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
            int i = parcel.readInt();
            int i2 = (i & 255) >>> PowerStats.PARCEL_FORMAT_VERSION_SHIFT;
            if (i2 != 2) {
                Slog.w(PowerStats.TAG, "Cannot read PowerStats from Parcel - the parcel format version has changed from " + i2 + " to 2");
                return null;
            }
            int i3 = (65280 & i) >>> PowerStats.STATS_ARRAY_LENGTH_SHIFT;
            int i4 = (16711680 & i) >>> PowerStats.STATE_STATS_ARRAY_LENGTH_SHIFT;
            int i5 = (i & (-16777216)) >>> PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT;
            int i6 = parcel.readInt();
            String string = parcel.readString();
            int i7 = parcel.readInt();
            SparseArray sparseArray = new SparseArray(i7);
            while (i7 > 0) {
                sparseArray.put(parcel.readInt(), parcel.readString());
                i7--;
            }
            return new Descriptor(i6, string, i3, sparseArray, i4, i5, parcel.readPersistableBundle());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Descriptor)) {
                return false;
            }
            Descriptor descriptor = (Descriptor) obj;
            if (this.powerComponentId != descriptor.powerComponentId || this.statsArrayLength != descriptor.statsArrayLength || !this.stateLabels.contentEquals(descriptor.stateLabels) || this.stateStatsArrayLength != descriptor.stateStatsArrayLength || this.uidStatsArrayLength != descriptor.uidStatsArrayLength || !Objects.equals(this.name, descriptor.name) || this.extras.size() != descriptor.extras.size()) {
                return false;
            }
            if (Bundle.kindofEquals(this.extras, descriptor.extras)) {
                return true;
            }
            for (String str : this.extras.keySet()) {
                if (!Objects.deepEquals(this.extras.get(str), descriptor.extras.get(str))) {
                    return false;
                }
            }
            return true;
        }

        public void writeXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
            typedXmlSerializer.startTag(null, XML_TAG_DESCRIPTOR);
            typedXmlSerializer.attributeInt(null, "id", this.powerComponentId);
            typedXmlSerializer.attribute(null, "name", this.name);
            typedXmlSerializer.attributeInt(null, XML_ATTR_STATS_ARRAY_LENGTH, this.statsArrayLength);
            typedXmlSerializer.attributeInt(null, XML_ATTR_STATE_STATS_ARRAY_LENGTH, this.stateStatsArrayLength);
            typedXmlSerializer.attributeInt(null, XML_ATTR_UID_STATS_ARRAY_LENGTH, this.uidStatsArrayLength);
            for (int size = this.stateLabels.size() - 1; size >= 0; size--) {
                typedXmlSerializer.startTag(null, "state");
                typedXmlSerializer.attributeInt(null, "key", this.stateLabels.keyAt(size));
                typedXmlSerializer.attribute(null, "label", this.stateLabels.valueAt(size));
                typedXmlSerializer.endTag(null, "state");
            }
            try {
                typedXmlSerializer.startTag(null, "extras");
                this.extras.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag(null, "extras");
                typedXmlSerializer.endTag(null, XML_TAG_DESCRIPTOR);
            } catch (XmlPullParserException e) {
                throw new IOException(e);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static Descriptor createFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
            SparseArray sparseArray = new SparseArray();
            int eventType = typedXmlPullParser.getEventType();
            int attributeInt = -1;
            String attributeValue = null;
            PersistableBundle persistableBundleRestoreFromXml = null;
            int attributeInt2 = 0;
            int attributeInt3 = 0;
            int attributeInt4 = 0;
            while (true) {
                boolean z = true;
                if (eventType != 1 && (eventType != 3 || !typedXmlPullParser.getName().equals(XML_TAG_DESCRIPTOR))) {
                    if (eventType == 2) {
                        String name = typedXmlPullParser.getName();
                        name.hashCode();
                        switch (name.hashCode()) {
                            case -1289032093:
                                if (!name.equals("extras")) {
                                    z = -1;
                                    break;
                                } else {
                                    z = false;
                                    break;
                                }
                            case -748366993:
                                if (!name.equals(XML_TAG_DESCRIPTOR)) {
                                }
                                break;
                            case 109757585:
                                if (name.equals("state")) {
                                    z = 2;
                                    break;
                                }
                                break;
                        }
                        switch (z) {
                            case false:
                                persistableBundleRestoreFromXml = PersistableBundle.restoreFromXml(typedXmlPullParser);
                                break;
                            case true:
                                attributeInt = typedXmlPullParser.getAttributeInt(null, "id");
                                attributeValue = typedXmlPullParser.getAttributeValue(null, "name");
                                attributeInt4 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_STATS_ARRAY_LENGTH);
                                attributeInt2 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_STATE_STATS_ARRAY_LENGTH);
                                attributeInt3 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_UID_STATS_ARRAY_LENGTH);
                                break;
                            case true:
                                sparseArray.put(typedXmlPullParser.getAttributeInt(null, "key"), typedXmlPullParser.getAttributeValue(null, "label"));
                                break;
                        }
                    }
                    eventType = typedXmlPullParser.next();
                }
            }
            if (attributeInt == -1) {
                return null;
            }
            if (attributeInt >= 1000) {
                return new Descriptor(attributeInt, attributeValue, attributeInt4, sparseArray, attributeInt2, attributeInt3, persistableBundleRestoreFromXml);
            }
            int i = attributeInt4;
            if (attributeInt < 20) {
                return new Descriptor(attributeInt, i, sparseArray, attributeInt2, attributeInt3, persistableBundleRestoreFromXml);
            }
            Slog.e(PowerStats.TAG, "Unrecognized power component: " + attributeInt);
            return null;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.powerComponentId));
        }

        public String toString() {
            PersistableBundle persistableBundle = this.extras;
            if (persistableBundle != null) {
                persistableBundle.size();
            }
            return "PowerStats.Descriptor{powerComponentId=" + this.powerComponentId + ", name='" + this.name + "', statsArrayLength=" + this.statsArrayLength + ", stateStatsArrayLength=" + this.stateStatsArrayLength + ", stateLabels=" + this.stateLabels + ", uidStatsArrayLength=" + this.uidStatsArrayLength + ", extras=" + this.extras + '}';
        }
    }

    public static class DescriptorRegistry {
        private final SparseArray<Descriptor> mDescriptors = new SparseArray<>();

        public void register(Descriptor descriptor) {
            this.mDescriptors.put(descriptor.powerComponentId, descriptor);
        }

        public Descriptor get(int i) {
            return this.mDescriptors.get(i);
        }
    }

    public PowerStats(Descriptor descriptor) {
        this.descriptor = descriptor;
        this.stats = new long[descriptor.statsArrayLength];
    }

    public void writeToParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int iDataPosition2 = parcel.dataPosition();
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
        int iDataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition3 - iDataPosition2);
        parcel.setDataPosition(iDataPosition3);
    }

    public static PowerStats readFromParcel(Parcel parcel, DescriptorRegistry descriptorRegistry) {
        int i = parcel.readInt();
        int iDataPosition = parcel.dataPosition();
        int i2 = iDataPosition + i;
        try {
            int i3 = parcel.readInt();
            Descriptor descriptor = descriptorRegistry.get(i3);
            if (descriptor == null) {
                Slog.e(TAG, "Unsupported PowerStats for power component ID: " + i3);
                if (i2 <= parcel.dataPosition()) {
                    return null;
                }
                if (i2 < parcel.dataSize()) {
                    parcel.setDataPosition(i2);
                    return null;
                }
                throw new IndexOutOfBoundsException("PowerStats end position: " + i2 + " is outside the parcel bounds: " + parcel.dataSize());
            }
            PowerStats powerStats = new PowerStats(descriptor);
            powerStats.durationMs = parcel.readLong();
            long[] jArr = new long[descriptor.statsArrayLength];
            powerStats.stats = jArr;
            VARINT_PARCELER.readLongArray(parcel, jArr);
            if (descriptor.stateStatsArrayLength != 0) {
                int i4 = parcel.readInt();
                for (int i5 = 0; i5 < i4; i5++) {
                    int i6 = parcel.readInt();
                    long[] jArr2 = new long[descriptor.stateStatsArrayLength];
                    VARINT_PARCELER.readLongArray(parcel, jArr2);
                    powerStats.stateStats.put(i6, jArr2);
                }
            }
            int i7 = parcel.readInt();
            for (int i8 = 0; i8 < i7; i8++) {
                int i9 = parcel.readInt();
                long[] jArr3 = new long[descriptor.uidStatsArrayLength];
                VARINT_PARCELER.readLongArray(parcel, jArr3);
                powerStats.uidStats.put(i9, jArr3);
            }
            if (parcel.dataPosition() == i2) {
                if (i2 <= parcel.dataPosition()) {
                    return powerStats;
                }
                if (i2 < parcel.dataSize()) {
                    parcel.setDataPosition(i2);
                    return powerStats;
                }
                throw new IndexOutOfBoundsException("PowerStats end position: " + i2 + " is outside the parcel bounds: " + parcel.dataSize());
            }
            Slog.e(TAG, "Corrupted PowerStats parcel. Expected length: " + i + ", actual length: " + (parcel.dataPosition() - iDataPosition));
            if (i2 <= parcel.dataPosition()) {
                return null;
            }
            if (i2 < parcel.dataSize()) {
                parcel.setDataPosition(i2);
                return null;
            }
            throw new IndexOutOfBoundsException("PowerStats end position: " + i2 + " is outside the parcel bounds: " + parcel.dataSize());
        } catch (Throwable th) {
            if (i2 > parcel.dataPosition()) {
                if (i2 >= parcel.dataSize()) {
                    throw new IndexOutOfBoundsException("PowerStats end position: " + i2 + " is outside the parcel bounds: " + parcel.dataSize());
                }
                parcel.setDataPosition(i2);
            }
            throw th;
        }
    }

    public String formatForBatteryHistory(String str) {
        StringBuilder sb = new StringBuilder("duration=");
        sb.append(this.durationMs);
        sb.append(" ");
        sb.append(this.descriptor.name);
        if (this.stats.length > 0) {
            sb.append("=");
            sb.append(this.descriptor.getDeviceStatsFormatter().format(this.stats));
        }
        if (this.descriptor.stateStatsArrayLength != 0) {
            PowerStatsFormatter stateStatsFormatter = this.descriptor.getStateStatsFormatter();
            for (int i = 0; i < this.stateStats.size(); i++) {
                sb.append(" (");
                sb.append(this.descriptor.getStateLabel(this.stateStats.keyAt(i)));
                sb.append(") ");
                sb.append(stateStatsFormatter.format(this.stateStats.valueAt(i)));
            }
        }
        PowerStatsFormatter uidStatsFormatter = this.descriptor.getUidStatsFormatter();
        for (int i2 = 0; i2 < this.uidStats.size(); i2++) {
            sb.append(str);
            sb.append(UserHandle.formatUid(this.uidStats.keyAt(i2)));
            sb.append(": ");
            sb.append(uidStatsFormatter.format(this.uidStats.valueAt(i2)));
        }
        return sb.toString();
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println(this.descriptor.name + " (" + this.descriptor.powerComponentId + ')');
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("duration", Long.valueOf(this.durationMs)).println();
        if (this.descriptor.statsArrayLength != 0) {
            indentingPrintWriter.println(this.descriptor.getDeviceStatsFormatter().format(this.stats));
        }
        if (this.descriptor.stateStatsArrayLength != 0) {
            PowerStatsFormatter stateStatsFormatter = this.descriptor.getStateStatsFormatter();
            for (int i = 0; i < this.stateStats.size(); i++) {
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(this.descriptor.getStateLabel(this.stateStats.keyAt(i)));
                indentingPrintWriter.print(") ");
                indentingPrintWriter.print(stateStatsFormatter.format(this.stateStats.valueAt(i)));
                indentingPrintWriter.println();
            }
        }
        PowerStatsFormatter uidStatsFormatter = this.descriptor.getUidStatsFormatter();
        for (int i2 = 0; i2 < this.uidStats.size(); i2++) {
            String str = uidStatsFormatter.format(this.uidStats.valueAt(i2));
            if (!str.isBlank()) {
                indentingPrintWriter.print("UID ");
                indentingPrintWriter.print(UserHandle.formatUid(this.uidStats.keyAt(i2)));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print(str);
                indentingPrintWriter.println();
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public String toString() {
        return "PowerStats: " + formatForBatteryHistory(" UID ");
    }

    public static class PowerStatsFormatter {
        private static final double NANO_TO_MILLI_MULTIPLIER = 1.0E-6d;
        private static final Pattern SECTION_PATTERN = Pattern.compile("([^:]+):(\\d+)(\\[(?<L>\\d+)])?(?<F>\\S*)\\s*");
        private final List<Section> mSections;

        private static class Section {
            public String label;
            public int length;
            public boolean optional;
            public int position;
            public boolean typePower;

            private Section() {
            }
        }

        public PowerStatsFormatter(String str) {
            this.mSections = parseFormat(str);
        }

        public String format(long[] jArr) {
            return format(this.mSections, jArr);
        }

        private List<Section> parseFormat(String str) {
            if (str == null || str.isBlank()) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Matcher matcher = SECTION_PATTERN.matcher(str);
            for (int iEnd = 0; iEnd < str.length(); iEnd = matcher.end()) {
                if (!matcher.find() || matcher.start() != iEnd) {
                    Slog.wtf(PowerStats.TAG, "Bad power stats format '" + str + "'");
                    return null;
                }
                Section section = new Section();
                section.label = matcher.group(1);
                section.position = Integer.parseUnsignedInt(matcher.group(2));
                String strGroup = matcher.group(GnssSignalType.CODE_TYPE_L);
                if (strGroup != null) {
                    section.length = Integer.parseUnsignedInt(strGroup);
                } else {
                    section.length = 1;
                }
                String strGroup2 = matcher.group("F");
                if (strGroup2 != null) {
                    for (int i = 0; i < strGroup2.length(); i++) {
                        char cCharAt = strGroup2.charAt(i);
                        if (cCharAt == '?') {
                            section.optional = true;
                        } else if (cCharAt == 'p') {
                            section.typePower = true;
                        } else {
                            Slog.e(PowerStats.TAG, "Unsupported format option '" + cCharAt + "' in " + str);
                        }
                    }
                }
                arrayList.add(section);
            }
            return arrayList;
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x003e  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0052  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x008a  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x008f A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private String format(List<Section> list, long[] jArr) {
            int i;
            if (list == null) {
                return Arrays.toString(jArr);
            }
            StringBuilder sb = new StringBuilder();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Section section = list.get(i2);
                if (section.length != 0) {
                    if (section.optional) {
                        for (int i3 = 0; i3 < section.length; i3++) {
                            if (jArr[section.position + i3] != 0) {
                                if (!sb.isEmpty()) {
                                    sb.append(' ');
                                }
                                sb.append(section.label);
                                sb.append(": ");
                                if (section.length != 1) {
                                    sb.append('[');
                                }
                                for (i = 0; i < section.length; i++) {
                                    if (i != 0) {
                                        sb.append(", ");
                                    }
                                    if (section.typePower) {
                                        sb.append(BatteryStats.formatCharge(jArr[section.position + i] * NANO_TO_MILLI_MULTIPLIER));
                                    } else {
                                        sb.append(jArr[section.position + i]);
                                    }
                                }
                                if (section.length == 1) {
                                    sb.append(']');
                                }
                            }
                        }
                    } else {
                        if (!sb.isEmpty()) {
                        }
                        sb.append(section.label);
                        sb.append(": ");
                        if (section.length != 1) {
                        }
                        while (i < section.length) {
                        }
                        if (section.length == 1) {
                        }
                    }
                }
            }
            return sb.toString();
        }
    }
}
