package com.android.server.powerstats;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.proto.ProtoInputStream;

import com.android.internal.util.FileRotator;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerStatsDataStorage {
    public final File mDataStorageDir;
    public final String mDataStorageFilename;
    public final FileRotator mFileRotator;
    public final ReentrantLock mLock = new ReentrantLock();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DataElement {
        public byte[] mData;

        /* renamed from: -$$Nest$mtoByteArray, reason: not valid java name */
        public static byte[] m849$$Nest$mtoByteArray(DataElement dataElement) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteBuffer allocate = ByteBuffer.allocate(4);
            byte[] bArr = dataElement.mData;
            byteArrayOutputStream.write(allocate.putInt(bArr.length).array());
            byteArrayOutputStream.write(bArr);
            return byteArrayOutputStream.toByteArray();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DataReader implements FileRotator.Reader {
        public PowerStatsLogger.AnonymousClass1 mCallback;

        public final void read(InputStream inputStream) {
            while (inputStream.available() > 0) {
                byte[] bArr = new byte[4];
                int read = inputStream.read(bArr);
                if (read != 4) {
                    throw new IOException(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    read, "Did not read 4 bytes (", ")"));
                }
                int i = ByteBuffer.wrap(bArr).getInt();
                if (i <= 0 || i >= 32768) {
                    throw new IOException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i, "DataElement size is invalid: "));
                }
                byte[] bArr2 = new byte[i];
                int read2 = inputStream.read(bArr2);
                if (read2 != i) {
                    throw new IOException(
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i, read2, "Invalid bytes read, expected: ", ", actual: "));
                }
                PowerStatsLogger.AnonymousClass1 anonymousClass1 = this.mCallback;
                switch (anonymousClass1.$r8$classId) {
                    case 0:
                        try {
                            new ProtoInputStream(new ByteArrayInputStream(bArr2));
                            ProtoStreamUtils$ChannelUtils.packProtoMessage(
                                    ProtoStreamUtils$ChannelUtils.m851unpackProtoMessage(bArr2),
                                    anonymousClass1.val$pos);
                            break;
                        } catch (IOException e) {
                            int i2 = PowerStatsLogger.$r8$clinit;
                            Slog.e(
                                    "PowerStatsLogger",
                                    "Failed to write energy meter data to incident report.",
                                    e);
                            break;
                        }
                    case 1:
                        try {
                            new ProtoInputStream(new ByteArrayInputStream(bArr2));
                            ProtoStreamUtils$ChannelUtils.packProtoMessage(
                                    ProtoStreamUtils$ChannelUtils.unpackProtoMessage(bArr2),
                                    anonymousClass1.val$pos,
                                    true);
                            break;
                        } catch (IOException e2) {
                            int i3 = PowerStatsLogger.$r8$clinit;
                            Slog.e(
                                    "PowerStatsLogger",
                                    "Failed to write energy model data to incident report.",
                                    e2);
                            break;
                        }
                    default:
                        try {
                            new ProtoInputStream(new ByteArrayInputStream(bArr2));
                            ProtoStreamUtils$ChannelUtils.packProtoMessage(
                                    ProtoStreamUtils$ChannelUtils.m852unpackProtoMessage(bArr2),
                                    anonymousClass1.val$pos);
                            break;
                        } catch (IOException e3) {
                            int i4 = PowerStatsLogger.$r8$clinit;
                            Slog.e(
                                    "PowerStatsLogger",
                                    "Failed to write residency data to incident report.",
                                    e3);
                            break;
                        }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DataRewriter implements FileRotator.Rewriter {
        public byte[] mActiveFileData;
        public byte[] mNewData;

        public final void read(InputStream inputStream) {
            byte[] bArr = new byte[inputStream.available()];
            this.mActiveFileData = bArr;
            inputStream.read(bArr);
        }

        public final void reset() {}

        public final boolean shouldWrite() {
            return true;
        }

        public final void write(OutputStream outputStream) {
            outputStream.write(this.mActiveFileData);
            outputStream.write(this.mNewData);
        }
    }

    public PowerStatsDataStorage(File file, String str) {
        this.mDataStorageDir = file;
        this.mDataStorageFilename = str;
        if (!file.exists() && !file.mkdirs()) {
            Slog.wtf("PowerStatsDataStorage", "mDataStorageDir does not exist: " + file.getPath());
            this.mFileRotator = null;
            return;
        }
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i]
                            .getName()
                            .startsWith(
                                    this.mDataStorageFilename.substring(
                                            0, this.mDataStorageFilename.lastIndexOf(46)))
                    && !listFiles[i].getName().startsWith(this.mDataStorageFilename)) {
                listFiles[i].delete();
            }
        }
        this.mFileRotator =
                new FileRotator(
                        this.mDataStorageDir,
                        this.mDataStorageFilename,
                        BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS,
                        172800000L);
    }

    public final void deleteLogs() {
        String str = this.mDataStorageFilename;
        this.mLock.lock();
        try {
            File[] listFiles = this.mDataStorageDir.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].getName().startsWith(str.substring(0, str.lastIndexOf(46)))) {
                    listFiles[i].delete();
                }
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        String str = this.mDataStorageFilename;
        this.mLock.lock();
        try {
            String substring = str.substring(0, str.lastIndexOf(46));
            File[] listFiles = this.mDataStorageDir.listFiles();
            int i = 0;
            int i2 = 0;
            long j = Long.MAX_VALUE;
            for (int i3 = 0; i3 < listFiles.length; i3++) {
                File file = listFiles[i3];
                String name = file.getName();
                if (listFiles[i3].getName().startsWith(substring)) {
                    int i4 = i + 1;
                    int length = (int) (i2 + file.length());
                    try {
                        long parseLong =
                                Long.parseLong(
                                        name.substring(
                                                name.lastIndexOf(46) + 1, name.lastIndexOf(45)));
                        if (parseLong < j) {
                            j = parseLong;
                        }
                    } catch (NumberFormatException e) {
                        Slog.e(
                                "PowerStatsDataStorage",
                                "Failed to extract start time from file : " + name,
                                e);
                    }
                    i2 = length;
                    i = i4;
                }
            }
            if (j != Long.MAX_VALUE) {
                indentingPrintWriter.println("Earliest data time : " + new Date(j));
            } else {
                indentingPrintWriter.println("Failed to parse earliest data time!!!");
            }
            indentingPrintWriter.println("# files : " + i);
            indentingPrintWriter.println("Total data size (B) : " + i2);
        } finally {
            this.mLock.unlock();
        }
    }

    public final void read(PowerStatsLogger.AnonymousClass1 anonymousClass1) {
        this.mLock.lock();
        try {
            FileRotator fileRotator = this.mFileRotator;
            DataReader dataReader = new DataReader();
            dataReader.mCallback = anonymousClass1;
            fileRotator.readMatching(dataReader, Long.MIN_VALUE, Long.MAX_VALUE);
        } finally {
            this.mLock.unlock();
        }
    }

    public final void write(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        this.mLock.lock();
        try {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                DataElement dataElement = new DataElement();
                dataElement.mData = bArr;
                FileRotator fileRotator = this.mFileRotator;
                byte[] m849$$Nest$mtoByteArray = DataElement.m849$$Nest$mtoByteArray(dataElement);
                DataRewriter dataRewriter = new DataRewriter();
                dataRewriter.mActiveFileData = new byte[0];
                dataRewriter.mNewData = m849$$Nest$mtoByteArray;
                fileRotator.rewriteActive(dataRewriter, currentTimeMillis);
                this.mFileRotator.maybeRotate(currentTimeMillis);
            } catch (IOException e) {
                Slog.e("PowerStatsDataStorage", "Failed to write to on-device storage: " + e);
            }
        } finally {
            this.mLock.unlock();
        }
    }
}
