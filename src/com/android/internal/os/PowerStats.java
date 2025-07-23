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
            int readInt = parcel.readInt();
            int i = (readInt & 255) >>> PowerStats.PARCEL_FORMAT_VERSION_SHIFT;
            if (i != 2) {
                Slog.w(PowerStats.TAG, "Cannot read PowerStats from Parcel - the parcel format version has changed from " + i + " to 2");
                return null;
            }
            int i2 = (65280 & readInt) >>> PowerStats.STATS_ARRAY_LENGTH_SHIFT;
            int i3 = (16711680 & readInt) >>> PowerStats.STATE_STATS_ARRAY_LENGTH_SHIFT;
            int i4 = (readInt & (-16777216)) >>> PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT;
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            SparseArray sparseArray = new SparseArray(readInt3);
            while (readInt3 > 0) {
                sparseArray.put(parcel.readInt(), parcel.readString());
                readInt3--;
            }
            return new Descriptor(readInt2, readString, i2, sparseArray, i3, i4, parcel.readPersistableBundle());
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
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0049, code lost:
        
            if (r0.equals(com.android.internal.os.PowerStats.Descriptor.XML_TAG_DESCRIPTOR) == false) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.android.internal.os.PowerStats.Descriptor createFromXml(com.android.modules.utils.TypedXmlPullParser r15) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            /*
                android.util.SparseArray r3 = new android.util.SparseArray
                r3.<init>()
                int r0 = r15.getEventType()
                r1 = -1
                r2 = 0
                r4 = 0
                r8 = r1
                r7 = r2
                r9 = r7
                r5 = r4
                r6 = r5
                r10 = r6
                r4 = r3
                r3 = r10
            L14:
                r11 = 1
                if (r0 == r11) goto L95
                r12 = 3
                java.lang.String r13 = "descriptor"
                if (r0 != r12) goto L26
                java.lang.String r12 = r15.getName()
                boolean r12 = r12.equals(r13)
                if (r12 != 0) goto L95
            L26:
                r12 = 2
                if (r0 != r12) goto L90
                java.lang.String r0 = r15.getName()
                r0.hashCode()
                int r14 = r0.hashCode()
                switch(r14) {
                    case -1289032093: goto L4c;
                    case -748366993: goto L45;
                    case 109757585: goto L39;
                    default: goto L37;
                }
            L37:
                r11 = r8
                goto L56
            L39:
                java.lang.String r11 = "state"
                boolean r0 = r0.equals(r11)
                if (r0 != 0) goto L43
                goto L37
            L43:
                r11 = r12
                goto L56
            L45:
                boolean r0 = r0.equals(r13)
                if (r0 != 0) goto L56
                goto L37
            L4c:
                java.lang.String r11 = "extras"
                boolean r0 = r0.equals(r11)
                if (r0 != 0) goto L55
                goto L37
            L55:
                r11 = r10
            L56:
                switch(r11) {
                    case 0: goto L8c;
                    case 1: goto L6a;
                    case 2: goto L5a;
                    default: goto L59;
                }
            L59:
                goto L90
            L5a:
                java.lang.String r0 = "key"
                int r0 = r15.getAttributeInt(r9, r0)
                java.lang.String r11 = "label"
                java.lang.String r11 = r15.getAttributeValue(r9, r11)
                r4.put(r0, r11)
                goto L90
            L6a:
                java.lang.String r0 = "id"
                int r1 = r15.getAttributeInt(r9, r0)
                java.lang.String r0 = "name"
                java.lang.String r2 = r15.getAttributeValue(r9, r0)
                java.lang.String r0 = "stats-array-length"
                int r3 = r15.getAttributeInt(r9, r0)
                java.lang.String r0 = "state-stats-array-length"
                int r5 = r15.getAttributeInt(r9, r0)
                java.lang.String r0 = "uid-stats-array-length"
                int r6 = r15.getAttributeInt(r9, r0)
                goto L90
            L8c:
                android.os.PersistableBundle r7 = android.os.PersistableBundle.restoreFromXml(r15)
            L90:
                int r0 = r15.next()
                goto L14
            L95:
                if (r1 != r8) goto L98
                return r9
            L98:
                r15 = 1000(0x3e8, float:1.401E-42)
                if (r1 < r15) goto La2
                com.android.internal.os.PowerStats$Descriptor r0 = new com.android.internal.os.PowerStats$Descriptor
                r0.<init>(r1, r2, r3, r4, r5, r6, r7)
                return r0
            La2:
                r2 = r3
                r15 = 20
                if (r1 >= r15) goto Lb1
                com.android.internal.os.PowerStats$Descriptor r0 = new com.android.internal.os.PowerStats$Descriptor
                r3 = r4
                r4 = r5
                r5 = r6
                r6 = r7
                r0.<init>(r1, r2, r3, r4, r5, r6)
                return r0
            Lb1:
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                java.lang.String r0 = "Unrecognized power component: "
                r15.<init>(r0)
                r15.append(r1)
                java.lang.String r15 = r15.toString()
                java.lang.String r0 = "PowerStats"
                android.util.Slog.e(r0, r15)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.PowerStats.Descriptor.createFromXml(com.android.modules.utils.TypedXmlPullParser):com.android.internal.os.PowerStats$Descriptor");
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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
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
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    public static PowerStats readFromParcel(Parcel parcel, DescriptorRegistry descriptorRegistry) {
        int readInt = parcel.readInt();
        int dataPosition = parcel.dataPosition();
        int i = dataPosition + readInt;
        try {
            int readInt2 = parcel.readInt();
            Descriptor descriptor = descriptorRegistry.get(readInt2);
            if (descriptor == null) {
                Slog.e(TAG, "Unsupported PowerStats for power component ID: " + readInt2);
                if (i <= parcel.dataPosition()) {
                    return null;
                }
                if (i < parcel.dataSize()) {
                    parcel.setDataPosition(i);
                    return null;
                }
                throw new IndexOutOfBoundsException("PowerStats end position: " + i + " is outside the parcel bounds: " + parcel.dataSize());
            }
            PowerStats powerStats = new PowerStats(descriptor);
            powerStats.durationMs = parcel.readLong();
            long[] jArr = new long[descriptor.statsArrayLength];
            powerStats.stats = jArr;
            VARINT_PARCELER.readLongArray(parcel, jArr);
            if (descriptor.stateStatsArrayLength != 0) {
                int readInt3 = parcel.readInt();
                for (int i2 = 0; i2 < readInt3; i2++) {
                    int readInt4 = parcel.readInt();
                    long[] jArr2 = new long[descriptor.stateStatsArrayLength];
                    VARINT_PARCELER.readLongArray(parcel, jArr2);
                    powerStats.stateStats.put(readInt4, jArr2);
                }
            }
            int readInt5 = parcel.readInt();
            for (int i3 = 0; i3 < readInt5; i3++) {
                int readInt6 = parcel.readInt();
                long[] jArr3 = new long[descriptor.uidStatsArrayLength];
                VARINT_PARCELER.readLongArray(parcel, jArr3);
                powerStats.uidStats.put(readInt6, jArr3);
            }
            if (parcel.dataPosition() == i) {
                if (i <= parcel.dataPosition()) {
                    return powerStats;
                }
                if (i < parcel.dataSize()) {
                    parcel.setDataPosition(i);
                    return powerStats;
                }
                throw new IndexOutOfBoundsException("PowerStats end position: " + i + " is outside the parcel bounds: " + parcel.dataSize());
            }
            Slog.e(TAG, "Corrupted PowerStats parcel. Expected length: " + readInt + ", actual length: " + (parcel.dataPosition() - dataPosition));
            if (i <= parcel.dataPosition()) {
                return null;
            }
            if (i < parcel.dataSize()) {
                parcel.setDataPosition(i);
                return null;
            }
            throw new IndexOutOfBoundsException("PowerStats end position: " + i + " is outside the parcel bounds: " + parcel.dataSize());
        } catch (Throwable th) {
            if (i > parcel.dataPosition()) {
                if (i >= parcel.dataSize()) {
                    throw new IndexOutOfBoundsException("PowerStats end position: " + i + " is outside the parcel bounds: " + parcel.dataSize());
                }
                parcel.setDataPosition(i);
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
            String format = uidStatsFormatter.format(this.uidStats.valueAt(i2));
            if (!format.isBlank()) {
                indentingPrintWriter.print("UID ");
                indentingPrintWriter.print(UserHandle.formatUid(this.uidStats.keyAt(i2)));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print(format);
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
            for (int i = 0; i < str.length(); i = matcher.end()) {
                if (!matcher.find() || matcher.start() != i) {
                    Slog.wtf(PowerStats.TAG, "Bad power stats format '" + str + "'");
                    return null;
                }
                Section section = new Section();
                section.label = matcher.group(1);
                section.position = Integer.parseUnsignedInt(matcher.group(2));
                String group = matcher.group(GnssSignalType.CODE_TYPE_L);
                if (group != null) {
                    section.length = Integer.parseUnsignedInt(group);
                } else {
                    section.length = 1;
                }
                String group2 = matcher.group("F");
                if (group2 != null) {
                    for (int i2 = 0; i2 < group2.length(); i2++) {
                        char charAt = group2.charAt(i2);
                        if (charAt == '?') {
                            section.optional = true;
                        } else if (charAt == 'p') {
                            section.typePower = true;
                        } else {
                            Slog.e(PowerStats.TAG, "Unsupported format option '" + charAt + "' in " + str);
                        }
                    }
                }
                arrayList.add(section);
            }
            return arrayList;
        }

        private String format(List<Section> list, long[] jArr) {
            if (list == null) {
                return Arrays.toString(jArr);
            }
            StringBuilder sb = new StringBuilder();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Section section = list.get(i);
                if (section.length != 0) {
                    if (section.optional) {
                        for (int i2 = 0; i2 < section.length; i2++) {
                            if (jArr[section.position + i2] == 0) {
                            }
                        }
                    }
                    if (!sb.isEmpty()) {
                        sb.append(' ');
                    }
                    sb.append(section.label);
                    sb.append(": ");
                    if (section.length != 1) {
                        sb.append('[');
                    }
                    for (int i3 = 0; i3 < section.length; i3++) {
                        if (i3 != 0) {
                            sb.append(", ");
                        }
                        if (section.typePower) {
                            sb.append(BatteryStats.formatCharge(jArr[section.position + i3] * NANO_TO_MILLI_MULTIPLIER));
                        } else {
                            sb.append(jArr[section.position + i3]);
                        }
                    }
                    if (section.length != 1) {
                        sb.append(']');
                    }
                }
            }
            return sb.toString();
        }
    }
}
