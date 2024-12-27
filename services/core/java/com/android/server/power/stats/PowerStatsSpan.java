package com.android.server.power.stats;

import android.os.BatteryUsageStats;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;

import com.android.internal.os.PowerStats;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;

import com.google.android.collect.Sets;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerStatsSpan {
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
    public final Metadata mMetadata;
    public final List mSections;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Metadata {
        public static final Comparator COMPARATOR =
                Comparator.comparing(new PowerStatsSpan$Metadata$$ExternalSyntheticLambda0());
        public final long mId;
        public final List mTimeFrames = new ArrayList();
        public final List mSections = new ArrayList();

        public Metadata(long j) {
            this.mId = j;
        }

        public static Metadata read(TypedXmlPullParser typedXmlPullParser) {
            int eventType = typedXmlPullParser.getEventType();
            Metadata metadata = null;
            while (eventType != 1
                    && (eventType != 3 || !typedXmlPullParser.getName().equals("metadata"))) {
                if (eventType == 2) {
                    String name = typedXmlPullParser.getName();
                    if (name.equals("metadata")) {
                        int attributeInt =
                                typedXmlPullParser.getAttributeInt((String) null, "version");
                        DateTimeFormatter dateTimeFormatter = PowerStatsSpan.DATE_FORMAT;
                        if (attributeInt != 2) {
                            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0
                                    .m(
                                            attributeInt,
                                            "Incompatible version ",
                                            "; expected 2",
                                            "PowerStatsStore");
                            return null;
                        }
                        metadata =
                                new Metadata(
                                        typedXmlPullParser.getAttributeLong((String) null, "id"));
                    } else if (metadata != null && name.equals("timeframe")) {
                        ((ArrayList) metadata.mTimeFrames)
                                .add(
                                        new TimeFrame(
                                                typedXmlPullParser.getAttributeLong(
                                                        (String) null, "monotonic"),
                                                typedXmlPullParser.getAttributeLong(
                                                        (String) null, "start"),
                                                typedXmlPullParser.getAttributeLong(
                                                        (String) null, "duration")));
                    } else if (metadata != null && name.equals("section")) {
                        String attributeValue =
                                typedXmlPullParser.getAttributeValue((String) null, "type");
                        if (!((ArrayList) metadata.mSections).contains(attributeValue)) {
                            ((ArrayList) metadata.mSections).add(attributeValue);
                        }
                    }
                }
                eventType = typedXmlPullParser.next();
            }
            return metadata;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, boolean z) {
            indentingPrintWriter.print("Span ");
            if (((ArrayList) this.mTimeFrames).size() > 0) {
                ((TimeFrame) ((ArrayList) this.mTimeFrames).get(0)).dump(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            for (int i = 1; i < ((ArrayList) this.mTimeFrames).size(); i++) {
                TimeFrame timeFrame = (TimeFrame) ((ArrayList) this.mTimeFrames).get(i);
                indentingPrintWriter.print("     ");
                timeFrame.dump(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            if (z) {
                indentingPrintWriter.increaseIndent();
                Iterator it = ((ArrayList) this.mSections).iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.print("section", (String) it.next());
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
        }

        public final String toString() {
            StringWriter stringWriter = new StringWriter();
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(stringWriter);
            indentingPrintWriter.print("id", Long.valueOf(this.mId));
            for (int i = 0; i < ((ArrayList) this.mTimeFrames).size(); i++) {
                TimeFrame timeFrame = (TimeFrame) ((ArrayList) this.mTimeFrames).get(i);
                indentingPrintWriter.print("timeframe=[");
                timeFrame.dump(indentingPrintWriter);
                indentingPrintWriter.print("] ");
            }
            Iterator it = ((ArrayList) this.mSections).iterator();
            while (it.hasNext()) {
                indentingPrintWriter.print("section", (String) it.next());
            }
            indentingPrintWriter.flush();
            return stringWriter.toString().trim();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Section {
        public final String mType;

        public Section(String str) {
            this.mType = str;
        }

        public abstract void dump(IndentingPrintWriter indentingPrintWriter);

        public abstract void write(TypedXmlSerializer typedXmlSerializer);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SectionReader {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TimeFrame {
        public final long duration;
        public final long startMonotonicTime;
        public final long startTime;

        public TimeFrame(long j, long j2, long j3) {
            this.startMonotonicTime = j;
            this.startTime = j2;
            this.duration = j3;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            StringBuilder sb = new StringBuilder();
            sb.append(PowerStatsSpan.DATE_FORMAT.format(Instant.ofEpochMilli(this.startTime)));
            sb.append(" (monotonic=");
            sb.append(this.startMonotonicTime);
            sb.append(")  duration=");
            String formatDuration = TimeUtils.formatDuration(this.duration);
            if (formatDuration.startsWith("+")) {
                sb.append(formatDuration.substring(1));
            } else {
                sb.append(formatDuration);
            }
            indentingPrintWriter.print(sb);
        }
    }

    public PowerStatsSpan(long j) {
        this(new Metadata(j));
    }

    public PowerStatsSpan(Metadata metadata) {
        this.mSections = new ArrayList();
        this.mMetadata = metadata;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:80:0x0161. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    public static PowerStatsSpan read(
            InputStream inputStream,
            TypedXmlPullParser typedXmlPullParser,
            SectionReader sectionReader,
            String... strArr) {
        String str;
        Section section;
        String str2;
        String str3;
        char c;
        AggregatedPowerStatsConfig.PowerComponent createPowerComponent;
        int i = -1;
        int i2 = 2;
        int i3 = 3;
        int i4 = 1;
        ArraySet newArraySet = Sets.newArraySet(strArr);
        boolean z = !newArraySet.isEmpty();
        typedXmlPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        Metadata read = Metadata.read(typedXmlPullParser);
        String str4 = null;
        if (read == null) {
            return null;
        }
        PowerStatsSpan powerStatsSpan = new PowerStatsSpan(read);
        int eventType = typedXmlPullParser.getEventType();
        int i5 = 0;
        int i6 = 0;
        while (eventType != i4) {
            if (i5 != 0) {
                if (eventType == i3 && typedXmlPullParser.getName().equals("section")) {
                    i6 += i;
                    if (i6 == 0) {
                        str = str4;
                        i5 = 0;
                    }
                } else if (eventType == i2 && typedXmlPullParser.getName().equals("section")) {
                    i6 += i4;
                }
                str = str4;
            } else {
                if (eventType == i2) {
                    String name = typedXmlPullParser.getName();
                    if (name.equals("section")) {
                        final String attributeValue =
                                typedXmlPullParser.getAttributeValue(str4, "type");
                        if (!z || newArraySet.contains(attributeValue)) {
                            PowerStatsStore.DefaultSectionReader defaultSectionReader =
                                    (PowerStatsStore.DefaultSectionReader) sectionReader;
                            defaultSectionReader.getClass();
                            attributeValue.getClass();
                            if (attributeValue.equals("battery-usage-stats")) {
                                str = str4;
                                section =
                                        new BatteryUsageStatsSection(
                                                BatteryUsageStats.createFromXml(
                                                        typedXmlPullParser));
                            } else if (attributeValue.equals("aggregated-power-stats")) {
                                SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                                AggregatedPowerStatsConfig aggregatedPowerStatsConfig =
                                        defaultSectionReader.mAggregatedPowerStatsConfig;
                                AggregatedPowerStats aggregatedPowerStats =
                                        new AggregatedPowerStats(
                                                aggregatedPowerStatsConfig, sparseBooleanArray);
                                int eventType2 = typedXmlPullParser.getEventType();
                                boolean z2 = false;
                                int i7 = 0;
                                while (eventType2 != i4
                                        && (eventType2 != i3
                                                || !typedXmlPullParser
                                                        .getName()
                                                        .equals("agg-power-stats"))) {
                                    if (!z2 && eventType2 == i2) {
                                        String name2 = typedXmlPullParser.getName();
                                        name2.getClass();
                                        if (name2.equals("power_component")) {
                                            if (i7 != 0) {
                                                int attributeInt =
                                                        typedXmlPullParser.getAttributeInt(
                                                                str4, "id");
                                                PowerComponentAggregatedPowerStats
                                                        powerComponentAggregatedPowerStats =
                                                                (PowerComponentAggregatedPowerStats)
                                                                        aggregatedPowerStats
                                                                                .mPowerComponentStats
                                                                                .get(attributeInt);
                                                if (powerComponentAggregatedPowerStats == null
                                                        && (createPowerComponent =
                                                                        aggregatedPowerStatsConfig
                                                                                .createPowerComponent(
                                                                                        attributeInt))
                                                                != null) {
                                                    powerComponentAggregatedPowerStats =
                                                            new PowerComponentAggregatedPowerStats(
                                                                    aggregatedPowerStats,
                                                                    createPowerComponent);
                                                    aggregatedPowerStats.mPowerComponentStats.put(
                                                            attributeInt,
                                                            powerComponentAggregatedPowerStats);
                                                }
                                                if (powerComponentAggregatedPowerStats != null) {
                                                    String name3 = typedXmlPullParser.getName();
                                                    int eventType3 =
                                                            typedXmlPullParser.getEventType();
                                                    while (eventType3 != i4
                                                            && (eventType3 != 3
                                                                    || !typedXmlPullParser
                                                                            .getName()
                                                                            .equals(name3))) {
                                                        if (eventType3 == i2) {
                                                            String name4 =
                                                                    typedXmlPullParser.getName();
                                                            name4.getClass();
                                                            str3 = name3;
                                                            switch (name4.hashCode()) {
                                                                case -2016846232:
                                                                    if (name4.equals(
                                                                            "device-stats")) {
                                                                        c = 0;
                                                                        break;
                                                                    }
                                                                    c = 65535;
                                                                    break;
                                                                case -748366993:
                                                                    if (name4.equals(
                                                                            "descriptor")) {
                                                                        c = 1;
                                                                        break;
                                                                    }
                                                                    c = 65535;
                                                                    break;
                                                                case 103033955:
                                                                    if (name4.equals(
                                                                            "state-stats")) {
                                                                        c = 2;
                                                                        break;
                                                                    }
                                                                    c = 65535;
                                                                    break;
                                                                case 1541808354:
                                                                    if (name4.equals("uid-stats")) {
                                                                        c = 3;
                                                                        break;
                                                                    }
                                                                    c = 65535;
                                                                    break;
                                                                default:
                                                                    c = 65535;
                                                                    break;
                                                            }
                                                            switch (c) {
                                                                case 0:
                                                                    str2 = null;
                                                                    if (powerComponentAggregatedPowerStats
                                                                                    .mDeviceStats
                                                                            == null) {
                                                                        powerComponentAggregatedPowerStats
                                                                                .createDeviceStats(
                                                                                        -1L);
                                                                    }
                                                                    if (!powerComponentAggregatedPowerStats
                                                                            .mDeviceStats
                                                                            .readFromXml(
                                                                                    typedXmlPullParser)) {
                                                                        z2 = true;
                                                                        break;
                                                                    } else {
                                                                        eventType3 =
                                                                                typedXmlPullParser
                                                                                        .next();
                                                                        name3 = str3;
                                                                        i2 = 2;
                                                                        i4 = 1;
                                                                    }
                                                                case 1:
                                                                    str2 = null;
                                                                    PowerStats.Descriptor
                                                                            createFromXml =
                                                                                    PowerStats
                                                                                            .Descriptor
                                                                                            .createFromXml(
                                                                                                    typedXmlPullParser);
                                                                    powerComponentAggregatedPowerStats
                                                                                    .mPowerStatsDescriptor =
                                                                            createFromXml;
                                                                    if (createFromXml == null) {
                                                                        z2 = true;
                                                                        break;
                                                                    } else {
                                                                        eventType3 =
                                                                                typedXmlPullParser
                                                                                        .next();
                                                                        name3 = str3;
                                                                        i2 = 2;
                                                                        i4 = 1;
                                                                    }
                                                                case 2:
                                                                    str2 = null;
                                                                    int attributeInt2 =
                                                                            typedXmlPullParser
                                                                                    .getAttributeInt(
                                                                                            (String)
                                                                                                    null,
                                                                                            "key");
                                                                    MultiStateStats
                                                                            multiStateStats =
                                                                                    (MultiStateStats)
                                                                                            powerComponentAggregatedPowerStats
                                                                                                    .mStateStats
                                                                                                    .get(
                                                                                                            attributeInt2);
                                                                    if (multiStateStats == null) {
                                                                        multiStateStats =
                                                                                powerComponentAggregatedPowerStats
                                                                                        .createStateStats(
                                                                                                attributeInt2);
                                                                    }
                                                                    if (!multiStateStats
                                                                            .readFromXml(
                                                                                    typedXmlPullParser)) {
                                                                        z2 = true;
                                                                        break;
                                                                    } else {
                                                                        eventType3 =
                                                                                typedXmlPullParser
                                                                                        .next();
                                                                        name3 = str3;
                                                                        i2 = 2;
                                                                        i4 = 1;
                                                                    }
                                                                case 3:
                                                                    PowerComponentAggregatedPowerStats
                                                                                    .UidStats
                                                                            uidStats =
                                                                                    powerComponentAggregatedPowerStats
                                                                                            .getUidStats(
                                                                                                    typedXmlPullParser
                                                                                                            .getAttributeInt(
                                                                                                                    (String)
                                                                                                                            null,
                                                                                                                    "uid"));
                                                                    if (uidStats.stats == null) {
                                                                        powerComponentAggregatedPowerStats
                                                                                .createUidStats(
                                                                                        uidStats,
                                                                                        -1L);
                                                                    }
                                                                    if (!uidStats.stats.readFromXml(
                                                                            typedXmlPullParser)) {
                                                                        str2 = null;
                                                                        z2 = true;
                                                                        break;
                                                                    }
                                                                default:
                                                                    eventType3 =
                                                                            typedXmlPullParser
                                                                                    .next();
                                                                    name3 = str3;
                                                                    i2 = 2;
                                                                    i4 = 1;
                                                            }
                                                            eventType2 = typedXmlPullParser.next();
                                                            str4 = str2;
                                                            i2 = 2;
                                                            i3 = 3;
                                                            i4 = 1;
                                                        } else {
                                                            str3 = name3;
                                                        }
                                                        eventType3 = typedXmlPullParser.next();
                                                        name3 = str3;
                                                        i2 = 2;
                                                        i4 = 1;
                                                    }
                                                }
                                                str2 = null;
                                                eventType2 = typedXmlPullParser.next();
                                                str4 = str2;
                                                i2 = 2;
                                                i3 = 3;
                                                i4 = 1;
                                            }
                                        } else if (name2.equals("agg-power-stats")) {
                                            i7 = i4;
                                        }
                                    }
                                    str2 = str4;
                                    eventType2 = typedXmlPullParser.next();
                                    str4 = str2;
                                    i2 = 2;
                                    i3 = 3;
                                    i4 = 1;
                                }
                                str = str4;
                                section = new AggregatedPowerStatsSection(aggregatedPowerStats);
                            } else {
                                str = str4;
                                section = str;
                            }
                            if (section == 0) {
                                if (z) {
                                    throw new XmlPullParserException(
                                            "Unsupported PowerStatsStore section type: "
                                                    .concat(attributeValue));
                                }
                                section =
                                        new Section(
                                                attributeValue) { // from class:
                                                                  // com.android.server.power.stats.PowerStatsSpan.1
                                            @Override // com.android.server.power.stats.PowerStatsSpan.Section
                                            public final void dump(
                                                    IndentingPrintWriter indentingPrintWriter) {
                                                indentingPrintWriter.println(
                                                        "Unsupported PowerStatsStore section type: "
                                                                + attributeValue);
                                            }

                                            @Override // com.android.server.power.stats.PowerStatsSpan.Section
                                            public final void write(
                                                    TypedXmlSerializer typedXmlSerializer) {}
                                        };
                            }
                            Metadata metadata = powerStatsSpan.mMetadata;
                            ArrayList arrayList = (ArrayList) metadata.mSections;
                            String str5 = section.mType;
                            if (!arrayList.contains(str5)) {
                                ((ArrayList) metadata.mSections).add(str5);
                            }
                            ((ArrayList) powerStatsSpan.mSections).add(section);
                        } else {
                            i5 = i4;
                        }
                    } else {
                        str = str4;
                        if (name.equals("metadata")) {
                            Metadata.read(typedXmlPullParser);
                        }
                    }
                }
                str = str4;
            }
            eventType = typedXmlPullParser.next();
            str4 = str;
            i = -1;
            i2 = 2;
            i3 = 3;
            i4 = 1;
        }
        return powerStatsSpan;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        this.mMetadata.dump(indentingPrintWriter, false);
        Iterator it = ((ArrayList) this.mSections).iterator();
        while (it.hasNext()) {
            Section section = (Section) it.next();
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(section.mType);
            section.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public void writeXml(OutputStream outputStream, TypedXmlSerializer typedXmlSerializer)
            throws IOException {
        typedXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        Metadata metadata = this.mMetadata;
        metadata.getClass();
        typedXmlSerializer.startTag((String) null, "metadata");
        typedXmlSerializer.attributeLong((String) null, "id", metadata.mId);
        typedXmlSerializer.attributeInt((String) null, "version", 2);
        Iterator it = ((ArrayList) metadata.mTimeFrames).iterator();
        while (it.hasNext()) {
            TimeFrame timeFrame = (TimeFrame) it.next();
            timeFrame.getClass();
            typedXmlSerializer.startTag((String) null, "timeframe");
            typedXmlSerializer.attributeLong((String) null, "start", timeFrame.startTime);
            typedXmlSerializer.attributeLong(
                    (String) null, "monotonic", timeFrame.startMonotonicTime);
            typedXmlSerializer.attributeLong((String) null, "duration", timeFrame.duration);
            typedXmlSerializer.endTag((String) null, "timeframe");
        }
        Iterator it2 = ((ArrayList) metadata.mSections).iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            typedXmlSerializer.startTag((String) null, "section");
            typedXmlSerializer.attribute((String) null, "type", str);
            typedXmlSerializer.endTag((String) null, "section");
        }
        typedXmlSerializer.endTag((String) null, "metadata");
        Iterator it3 = ((ArrayList) this.mSections).iterator();
        while (it3.hasNext()) {
            Section section = (Section) it3.next();
            typedXmlSerializer.startTag((String) null, "section");
            typedXmlSerializer.attribute((String) null, "type", section.mType);
            section.write(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "section");
        }
        typedXmlSerializer.endDocument();
    }
}
