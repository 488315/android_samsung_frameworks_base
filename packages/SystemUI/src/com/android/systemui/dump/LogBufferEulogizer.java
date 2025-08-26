package com.android.systemui.dump;

import android.content.Context;
import android.util.Log;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.util.io.Files;
import com.android.systemui.util.time.SystemClock;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collection;
import kotlin.Unit;
import kotlin.io.CloseableKt;

/* loaded from: classes2.dex */
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
        BasicFileAttributes attributes;
        FileTime fileTimeLastModifiedTime;
        try {
            attributes = this.files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
        } catch (IOException unused) {
            attributes = null;
        }
        return this.systemClock.currentTimeMillis() - ((attributes == null || (fileTimeLastModifiedTime = attributes.lastModifiedTime()) == null) ? 0L : fileTimeLastModifiedTime.toMillis());
    }

    public final void record(Throwable th) {
        SystemClock systemClock = this.systemClock;
        long jUptimeMillis = systemClock.uptimeMillis();
        Log.i("BufferEulogizer", "Performing emergency dump of log buffers");
        long millisSinceLastWrite = getMillisSinceLastWrite(this.logPath);
        if (millisSinceLastWrite < this.minWriteGap) {
            Log.w("BufferEulogizer", "Cannot dump logs, last write was only " + millisSinceLastWrite + " ms ago");
            return;
        }
        long jUptimeMillis2 = 0;
        try {
            BufferedWriter bufferedWriterNewBufferedWriter = this.files.newBufferedWriter(this.logPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            try {
                PrintWriter printWriter = new PrintWriter(bufferedWriterNewBufferedWriter);
                printWriter.println(LogBufferEulogizerKt.DATE_FORMAT.format(Long.valueOf(systemClock.currentTimeMillis())));
                printWriter.println();
                printWriter.println("Dump triggered by exception:");
                th.printStackTrace(printWriter);
                Collection logBuffers = this.dumpManager.getLogBuffers();
                DumpHandler.Companion.getClass();
                DumpHandler.Companion.dumpEntries(logBuffers, printWriter);
                jUptimeMillis2 = systemClock.uptimeMillis() - jUptimeMillis;
                printWriter.println();
                printWriter.println("Buffer eulogy took " + jUptimeMillis2 + "ms");
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedWriterNewBufferedWriter, null);
            } finally {
            }
        } catch (Exception e) {
            Log.e("BufferEulogizer", "Exception while attempting to dump buffers, bailing", e);
        }
        Log.i("BufferEulogizer", "Buffer eulogy took " + jUptimeMillis2 + "ms");
    }

    public LogBufferEulogizer(Context context, DumpManager dumpManager, SystemClock systemClock, Files files) {
        this(dumpManager, systemClock, files, Paths.get(context.getFilesDir().toPath().toString(), "log_buffers.txt"), LogBufferEulogizerKt.MIN_WRITE_GAP, LogBufferEulogizerKt.MAX_AGE_TO_DUMP);
    }
}
