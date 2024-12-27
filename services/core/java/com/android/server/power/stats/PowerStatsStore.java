package com.android.server.power.stats;

import android.os.FileUtils;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public final class PowerStatsStore {
    public final ReentrantLock mFileLock = new ReentrantLock();
    public FileLock mJvmLock;
    public final File mLockFile;
    public final long mMaxStorageBytes;
    public final PowerStatsSpan.SectionReader mSectionReader;
    public final File mStoreDir;
    public final File mSystemDir;
    public volatile List mTableOfContents;

    public final class DefaultSectionReader implements PowerStatsSpan.SectionReader {
        public final AggregatedPowerStatsConfig mAggregatedPowerStatsConfig;

        public DefaultSectionReader(AggregatedPowerStatsConfig aggregatedPowerStatsConfig) {
            this.mAggregatedPowerStatsConfig = aggregatedPowerStatsConfig;
        }
    }

    public PowerStatsStore(
            File file, long j, Handler handler, PowerStatsSpan.SectionReader sectionReader) {
        this.mSystemDir = file;
        File file2 = new File(file, "power-stats");
        this.mStoreDir = file2;
        this.mLockFile = new File(file2, ".lock");
        this.mMaxStorageBytes = j;
        this.mSectionReader = sectionReader;
        handler.post(
                new Runnable() { // from class:
                                 // com.android.server.power.stats.PowerStatsStore$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerStatsStore powerStatsStore = PowerStatsStore.this;
                        powerStatsStore.getClass();
                        File file3 = new File(powerStatsStore.mSystemDir, "battery-usage-stats");
                        if (file3.exists()) {
                            FileUtils.deleteContentsAndDir(file3);
                        }
                    }
                });
    }

    public static PowerStatsSpan createPowerStatsSpan(AggregatedPowerStats aggregatedPowerStats) {
        PowerStatsSpan.Metadata metadata;
        ArrayList arrayList = (ArrayList) aggregatedPowerStats.mClockUpdates;
        if (arrayList.isEmpty()) {
            Slog.w(
                    "PowerStatsStore",
                    "No clock updates in aggregated power stats " + aggregatedPowerStats);
            return null;
        }
        int i = 0;
        long j = ((AggregatedPowerStats.ClockUpdate) arrayList.get(0)).monotonicTime;
        PowerStatsSpan powerStatsSpan = new PowerStatsSpan(j);
        long j2 = 0;
        while (true) {
            int size = arrayList.size();
            metadata = powerStatsSpan.mMetadata;
            if (i >= size) {
                break;
            }
            AggregatedPowerStats.ClockUpdate clockUpdate =
                    (AggregatedPowerStats.ClockUpdate) arrayList.get(i);
            long j3 =
                    i == arrayList.size() + (-1)
                            ? aggregatedPowerStats.mDurationMs - j2
                            : clockUpdate.monotonicTime - j;
            ((ArrayList) metadata.mTimeFrames)
                    .add(
                            new PowerStatsSpan.TimeFrame(
                                    clockUpdate.monotonicTime, clockUpdate.currentTime, j3));
            j2 += j3;
            i++;
            j = clockUpdate.monotonicTime;
            arrayList = arrayList;
        }
        AggregatedPowerStatsSection aggregatedPowerStatsSection =
                new AggregatedPowerStatsSection(aggregatedPowerStats);
        if (!((ArrayList) metadata.mSections).contains("aggregated-power-stats")) {
            ((ArrayList) metadata.mSections).add("aggregated-power-stats");
        }
        ((ArrayList) powerStatsSpan.mSections).add(aggregatedPowerStatsSection);
        return powerStatsSpan;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Power stats store");
        indentingPrintWriter.increaseIndent();
        Iterator it = getTableOfContents().iterator();
        while (it.hasNext()) {
            PowerStatsSpan loadPowerStatsSpan =
                    loadPowerStatsSpan(((PowerStatsSpan.Metadata) it.next()).mId, new String[0]);
            if (loadPowerStatsSpan != null) {
                loadPowerStatsSpan.dump(indentingPrintWriter);
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final List getTableOfContents() {
        List list = this.mTableOfContents;
        if (list != null) {
            return list;
        }
        TypedXmlPullParser newBinaryPullParser = Xml.newBinaryPullParser();
        lockStoreDirectory();
        try {
            ArrayList arrayList = new ArrayList();
            for (File file : this.mStoreDir.listFiles()) {
                String name = file.getName();
                if (name.endsWith(".pss")) {
                    try {
                        BufferedInputStream bufferedInputStream =
                                new BufferedInputStream(new FileInputStream(file));
                        try {
                            newBinaryPullParser.setInput(
                                    bufferedInputStream, StandardCharsets.UTF_8.name());
                            PowerStatsSpan.Metadata read =
                                    PowerStatsSpan.Metadata.read(newBinaryPullParser);
                            if (read != null) {
                                arrayList.add(read);
                            } else {
                                Slog.e(
                                        "PowerStatsStore",
                                        "Removing incompatible PowerStatsSpan file: " + name);
                                file.delete();
                            }
                            bufferedInputStream.close();
                        } catch (Throwable th) {
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException | XmlPullParserException unused) {
                        Slog.wtf("PowerStatsStore", "Cannot read PowerStatsSpan file: " + name);
                    }
                }
            }
            arrayList.sort(PowerStatsSpan.Metadata.COMPARATOR);
            this.mTableOfContents = Collections.unmodifiableList(arrayList);
            return arrayList;
        } finally {
            unlockStoreDirectory();
        }
    }

    public final PowerStatsSpan loadPowerStatsSpan(long j, String... strArr) {
        TypedXmlPullParser newBinaryPullParser = Xml.newBinaryPullParser();
        lockStoreDirectory();
        try {
            File file =
                    new File(
                            this.mStoreDir,
                            String.format(Locale.ENGLISH, "%019d", Long.valueOf(j)).concat(".pss"));
            try {
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(new FileInputStream(file));
                try {
                    PowerStatsSpan read =
                            PowerStatsSpan.read(
                                    bufferedInputStream,
                                    newBinaryPullParser,
                                    this.mSectionReader,
                                    strArr);
                    bufferedInputStream.close();
                    return read;
                } catch (Throwable th) {
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | XmlPullParserException e) {
                Slog.wtf("PowerStatsStore", "Cannot read PowerStatsSpan file: " + file, e);
                unlockStoreDirectory();
                return null;
            }
        } finally {
            unlockStoreDirectory();
        }
    }

    public final void lockStoreDirectory() {
        this.mFileLock.lock();
        try {
            this.mLockFile.getParentFile().mkdirs();
            this.mLockFile.createNewFile();
            this.mJvmLock =
                    FileChannel.open(this.mLockFile.toPath(), StandardOpenOption.WRITE).lock();
        } catch (IOException e) {
            Slog.e("PowerStatsStore", "Cannot lock snapshot directory", e);
        }
    }

    public final void removeOldSpansLocked() {
        Map.Entry firstEntry;
        TreeMap treeMap = new TreeMap();
        long j = 0;
        for (File file : this.mStoreDir.listFiles()) {
            long length = file.length();
            j += length;
            if (file.getName().endsWith(".pss")) {
                treeMap.put(file, Long.valueOf(length));
            }
        }
        while (j > this.mMaxStorageBytes && (firstEntry = treeMap.firstEntry()) != null) {
            File file2 = (File) firstEntry.getKey();
            if (!file2.delete()) {
                Slog.e("PowerStatsStore", "Cannot delete power stats span " + file2);
            }
            j -= ((Long) firstEntry.getValue()).longValue();
            treeMap.remove(file2);
            this.mTableOfContents = null;
        }
    }

    public final void reset() {
        lockStoreDirectory();
        try {
            for (File file : this.mStoreDir.listFiles()) {
                if (file.getName().endsWith(".pss") && !file.delete()) {
                    Slog.e("PowerStatsStore", "Cannot delete power stats span " + file);
                }
            }
            this.mTableOfContents = List.of();
            unlockStoreDirectory();
        } catch (Throwable th) {
            unlockStoreDirectory();
            throw th;
        }
    }

    public final void storePowerStatsSpan(final PowerStatsSpan powerStatsSpan) {
        File file = new File(this.mSystemDir, "battery-usage-stats");
        if (file.exists()) {
            FileUtils.deleteContentsAndDir(file);
        }
        lockStoreDirectory();
        try {
            if (!this.mStoreDir.exists() && !this.mStoreDir.mkdirs()) {
                Slog.e("PowerStatsStore", "Could not create a directory for power stats store");
                return;
            }
            new AtomicFile(
                            new File(
                                    this.mStoreDir,
                                    String.format(
                                                    Locale.ENGLISH,
                                                    "%019d",
                                                    Long.valueOf(powerStatsSpan.mMetadata.mId))
                                            .concat(".pss")))
                    .write(
                            new Consumer() { // from class:
                                             // com.android.server.power.stats.PowerStatsStore$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    try {
                                        PowerStatsSpan.this.writeXml(
                                                (FileOutputStream) obj, Xml.newBinarySerializer());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
            this.mTableOfContents = null;
            removeOldSpansLocked();
        } finally {
            unlockStoreDirectory();
        }
    }

    public final void unlockStoreDirectory() {
        try {
            try {
                this.mJvmLock.close();
            } catch (IOException e) {
                Slog.e("PowerStatsStore", "Cannot unlock snapshot directory", e);
            }
        } finally {
            this.mFileLock.unlock();
        }
    }
}
