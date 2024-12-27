package com.android.internal.util;

import android.os.FileUtils;
import android.util.Pair;

import libcore.io.IoUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.ToLongFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileRotator {
    private static final boolean LOGD = false;
    private static final String SUFFIX_BACKUP = ".backup";
    private static final String SUFFIX_NO_BACKUP = ".no_backup";
    private static final String TAG = "FileRotator";
    private final File mBasePath;
    private final long mDeleteAgeMillis;
    private final String mPrefix;
    private final long mRotateAgeMillis;

    public interface Reader {
        void read(InputStream inputStream) throws IOException;
    }

    public interface Rewriter extends Reader, Writer {
        void reset();

        boolean shouldWrite();
    }

    public interface Writer {
        void write(OutputStream outputStream) throws IOException;
    }

    public FileRotator(File basePath, String prefix, long rotateAgeMillis, long deleteAgeMillis) {
        this.mBasePath = (File) Objects.requireNonNull(basePath);
        this.mPrefix = (String) Objects.requireNonNull(prefix);
        this.mRotateAgeMillis = rotateAgeMillis;
        this.mDeleteAgeMillis = deleteAgeMillis;
        this.mBasePath.mkdirs();
        for (String name : this.mBasePath.list()) {
            if (name.startsWith(this.mPrefix)) {
                if (name.endsWith(SUFFIX_BACKUP)) {
                    File backupFile = new File(this.mBasePath, name);
                    File file =
                            new File(
                                    this.mBasePath,
                                    name.substring(0, name.length() - SUFFIX_BACKUP.length()));
                    backupFile.renameTo(file);
                } else if (name.endsWith(SUFFIX_NO_BACKUP)) {
                    File noBackupFile = new File(this.mBasePath, name);
                    File file2 =
                            new File(
                                    this.mBasePath,
                                    name.substring(0, name.length() - SUFFIX_NO_BACKUP.length()));
                    noBackupFile.delete();
                    file2.delete();
                }
            }
        }
    }

    public void deleteAll() {
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name)) {
                new File(this.mBasePath, name).delete();
            }
        }
    }

    public void dumpAll(OutputStream os) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(os);
        try {
            FileInfo info = new FileInfo(this.mPrefix);
            for (String name : this.mBasePath.list()) {
                if (info.parse(name)) {
                    ZipEntry entry = new ZipEntry(name);
                    zos.putNextEntry(entry);
                    File file = new File(this.mBasePath, name);
                    FileInputStream is = new FileInputStream(file);
                    try {
                        FileUtils.copy(is, zos);
                        IoUtils.closeQuietly(is);
                        zos.closeEntry();
                    } finally {
                    }
                }
            }
        } finally {
            IoUtils.closeQuietly(zos);
        }
    }

    public void rewriteActive(Rewriter rewriter, long currentTimeMillis) throws IOException {
        String activeName = getActiveName(currentTimeMillis);
        rewriteSingle(rewriter, activeName);
    }

    @Deprecated
    public void combineActive(final Reader reader, final Writer writer, long currentTimeMillis)
            throws IOException {
        rewriteActive(
                new Rewriter() { // from class: com.android.internal.util.FileRotator.1
                    @Override // com.android.internal.util.FileRotator.Rewriter
                    public void reset() {}

                    @Override // com.android.internal.util.FileRotator.Reader
                    public void read(InputStream in) throws IOException {
                        reader.read(in);
                    }

                    @Override // com.android.internal.util.FileRotator.Rewriter
                    public boolean shouldWrite() {
                        return true;
                    }

                    @Override // com.android.internal.util.FileRotator.Writer
                    public void write(OutputStream out) throws IOException {
                        writer.write(out);
                    }
                },
                currentTimeMillis);
    }

    public void rewriteAll(Rewriter rewriter) throws IOException {
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name)) {
                rewriteSingle(rewriter, name);
            }
        }
    }

    private void rewriteSingle(Rewriter rewriter, String name) throws IOException {
        File file = new File(this.mBasePath, name);
        rewriter.reset();
        if (file.exists()) {
            readFile(file, rewriter);
            if (rewriter.shouldWrite()) {
                File backupFile = new File(this.mBasePath, name + SUFFIX_BACKUP);
                file.renameTo(backupFile);
                try {
                    writeFile(file, rewriter);
                    backupFile.delete();
                    return;
                } catch (Throwable t) {
                    file.delete();
                    backupFile.renameTo(file);
                    throw rethrowAsIoException(t);
                }
            }
            return;
        }
        File backupFile2 = new File(this.mBasePath, name + SUFFIX_NO_BACKUP);
        backupFile2.createNewFile();
        try {
            writeFile(file, rewriter);
            backupFile2.delete();
        } catch (Throwable t2) {
            file.delete();
            backupFile2.delete();
            throw rethrowAsIoException(t2);
        }
    }

    public void rewriteSingle(Rewriter rewriter, long startTimeMillis, long endTimeMillis)
            throws IOException {
        FileInfo info = new FileInfo(this.mPrefix);
        info.startMillis = startTimeMillis;
        info.endMillis = endTimeMillis;
        rewriteSingle(rewriter, info.build());
    }

    public void readMatching(Reader reader, long matchStartMillis, long matchEndMillis)
            throws IOException {
        FileInfo info = new FileInfo(this.mPrefix);
        TreeSet<Pair<Long, String>> readSet =
                new TreeSet<>(
                        (Comparator<? super Pair<Long, String>>)
                                Comparator.comparingLong(
                                        new ToLongFunction() { // from class:
                                                               // com.android.internal.util.FileRotator$$ExternalSyntheticLambda0
                                            @Override // java.util.function.ToLongFunction
                                            public final long applyAsLong(Object obj) {
                                                long longValue;
                                                longValue = ((Long) ((Pair) obj).first).longValue();
                                                return longValue;
                                            }
                                        }));
        for (String name : this.mBasePath.list()) {
            if (info.parse(name)
                    && info.startMillis <= matchEndMillis
                    && matchStartMillis <= info.endMillis) {
                readSet.add(new Pair<>(Long.valueOf(info.startMillis), name));
            }
        }
        Iterator<Pair<Long, String>> it = readSet.iterator();
        while (it.hasNext()) {
            Pair<Long, String> pair = it.next();
            File file = new File(this.mBasePath, pair.second);
            readFile(file, reader);
        }
    }

    private String getActiveName(long currentTimeMillis) {
        String oldestActiveName = null;
        long oldestActiveStart = Long.MAX_VALUE;
        FileInfo info = new FileInfo(this.mPrefix);
        for (String name : this.mBasePath.list()) {
            if (info.parse(name)
                    && info.isActive()
                    && info.startMillis < currentTimeMillis
                    && info.startMillis < oldestActiveStart) {
                oldestActiveName = name;
                oldestActiveStart = info.startMillis;
            }
        }
        if (oldestActiveName != null) {
            return oldestActiveName;
        }
        info.startMillis = currentTimeMillis;
        info.endMillis = Long.MAX_VALUE;
        return info.build();
    }

    public void maybeRotate(long currentTimeMillis) {
        long rotateBefore = currentTimeMillis - this.mRotateAgeMillis;
        long deleteBefore = currentTimeMillis - this.mDeleteAgeMillis;
        FileInfo info = new FileInfo(this.mPrefix);
        String[] baseFiles = this.mBasePath.list();
        if (baseFiles == null) {
            return;
        }
        for (String name : baseFiles) {
            if (info.parse(name)) {
                if (info.isActive()) {
                    if (info.startMillis <= rotateBefore) {
                        info.endMillis = currentTimeMillis;
                        File file = new File(this.mBasePath, name);
                        File destFile = new File(this.mBasePath, info.build());
                        file.renameTo(destFile);
                    }
                } else if (info.endMillis <= deleteBefore) {
                    File file2 = new File(this.mBasePath, name);
                    file2.delete();
                }
            }
        }
    }

    private static void readFile(File file, Reader reader) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try {
            reader.read(bis);
        } finally {
            IoUtils.closeQuietly(bis);
        }
    }

    private static void writeFile(File file, Writer writer) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try {
            writer.write(bos);
            bos.flush();
        } finally {
            try {
                fos.getFD().sync();
            } catch (IOException e) {
            }
            IoUtils.closeQuietly(bos);
        }
    }

    private static IOException rethrowAsIoException(Throwable t) throws IOException {
        if (t instanceof IOException) {
            throw ((IOException) t);
        }
        throw new IOException(t.getMessage(), t);
    }

    private static class FileInfo {
        public long endMillis;
        public final String prefix;
        public long startMillis;

        public FileInfo(String prefix) {
            this.prefix = (String) Objects.requireNonNull(prefix);
        }

        public boolean parse(String name) {
            this.endMillis = -1L;
            this.startMillis = -1L;
            int dotIndex = name.lastIndexOf(46);
            int dashIndex = name.lastIndexOf(45);
            if (dotIndex == -1
                    || dashIndex == -1
                    || !this.prefix.equals(name.substring(0, dotIndex))) {
                return false;
            }
            try {
                this.startMillis = Long.parseLong(name.substring(dotIndex + 1, dashIndex));
                if (name.length() - dashIndex == 1) {
                    this.endMillis = Long.MAX_VALUE;
                } else {
                    this.endMillis = Long.parseLong(name.substring(dashIndex + 1));
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public String build() {
            StringBuilder name = new StringBuilder();
            name.append(this.prefix).append('.').append(this.startMillis).append('-');
            if (this.endMillis != Long.MAX_VALUE) {
                name.append(this.endMillis);
            }
            return name.toString();
        }

        public boolean isActive() {
            return this.endMillis == Long.MAX_VALUE;
        }
    }
}
