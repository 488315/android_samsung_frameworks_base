package com.android.systemui.dump;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import com.android.systemui.util.p036io.Files;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import kotlin.Unit;
import kotlin.p055io.CloseableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LogBufferEulogizer {
    public final DumpManager dumpManager;
    public final Files files;
    public final Path logPath;
    public final long maxLogAgeToDump;
    public final long minWriteGap;
    public final SystemClock systemClock;

    public LogBufferEulogizer(DumpManager dumpManager, SystemClock systemClock, Files files, Path path, long j, long j2) {
        this.dumpManager = dumpManager;
        this.systemClock = systemClock;
        this.files = files;
        this.logPath = path;
        this.minWriteGap = j;
        this.maxLogAgeToDump = j2;
    }

    public final long getMillisSinceLastWrite(Path path) {
        BasicFileAttributes basicFileAttributes;
        FileTime lastModifiedTime;
        try {
            this.files.getClass();
            basicFileAttributes = java.nio.file.Files.readAttributes(path, (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
        } catch (IOException unused) {
            basicFileAttributes = null;
        }
        ((SystemClockImpl) this.systemClock).getClass();
        return System.currentTimeMillis() - ((basicFileAttributes == null || (lastModifiedTime = basicFileAttributes.lastModifiedTime()) == null) ? 0L : lastModifiedTime.toMillis());
    }

    public final void record(Exception exc) {
        SystemClock systemClock = this.systemClock;
        ((SystemClockImpl) systemClock).getClass();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        Log.i("BufferEulogizer", "Performing emergency dump of log buffers");
        Path path = this.logPath;
        long millisSinceLastWrite = getMillisSinceLastWrite(path);
        if (millisSinceLastWrite < this.minWriteGap) {
            Log.w("BufferEulogizer", "Cannot dump logs, last write was only " + millisSinceLastWrite + " ms ago");
            return;
        }
        long j = 0;
        try {
            Files files = this.files;
            OpenOption[] openOptionArr = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
            files.getClass();
            BufferedWriter newBufferedWriter = java.nio.file.Files.newBufferedWriter(path, StandardCharsets.UTF_8, openOptionArr);
            try {
                PrintWriter printWriter = new PrintWriter(newBufferedWriter);
                SimpleDateFormat simpleDateFormat = LogBufferEulogizerKt.DATE_FORMAT;
                ((SystemClockImpl) systemClock).getClass();
                printWriter.println(simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
                printWriter.println();
                printWriter.println("Dump triggered by exception:");
                exc.printStackTrace(printWriter);
                this.dumpManager.dumpBuffers(printWriter, 0);
                ((SystemClockImpl) systemClock).getClass();
                j = android.os.SystemClock.uptimeMillis() - uptimeMillis;
                printWriter.println();
                printWriter.println("Buffer eulogy took " + j + "ms");
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(newBufferedWriter, null);
            } finally {
            }
        } catch (Exception e) {
            Log.e("BufferEulogizer", "Exception while attempting to dump buffers, bailing", e);
        }
        Log.i("BufferEulogizer", "Buffer eulogy took " + j + "ms");
    }

    public LogBufferEulogizer(Context context, DumpManager dumpManager, SystemClock systemClock, Files files) {
        this(dumpManager, systemClock, files, Paths.get(context.getFilesDir().toPath().toString(), "log_buffers.txt"), LogBufferEulogizerKt.MIN_WRITE_GAP, LogBufferEulogizerKt.MAX_AGE_TO_DUMP);
    }
}
