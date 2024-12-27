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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            basicFileAttributes = this.files.readAttributes(path, BasicFileAttributes.class, new LinkOption[0]);
        } catch (IOException unused) {
            basicFileAttributes = null;
        }
        return this.systemClock.currentTimeMillis() - ((basicFileAttributes == null || (lastModifiedTime = basicFileAttributes.lastModifiedTime()) == null) ? 0L : lastModifiedTime.toMillis());
    }

    public final void record(Throwable th) {
        SystemClock systemClock = this.systemClock;
        long uptimeMillis = systemClock.uptimeMillis();
        Log.i("BufferEulogizer", "Performing emergency dump of log buffers");
        long millisSinceLastWrite = getMillisSinceLastWrite(this.logPath);
        if (millisSinceLastWrite < this.minWriteGap) {
            Log.w("BufferEulogizer", "Cannot dump logs, last write was only " + millisSinceLastWrite + " ms ago");
            return;
        }
        long j = 0;
        try {
            BufferedWriter newBufferedWriter = this.files.newBufferedWriter(this.logPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            try {
                PrintWriter printWriter = new PrintWriter(newBufferedWriter);
                printWriter.println(LogBufferEulogizerKt.DATE_FORMAT.format(Long.valueOf(systemClock.currentTimeMillis())));
                printWriter.println();
                printWriter.println("Dump triggered by exception:");
                th.printStackTrace(printWriter);
                Collection logBuffers = this.dumpManager.getLogBuffers();
                DumpHandler.Companion.getClass();
                DumpHandler.Companion.dumpEntries(logBuffers, printWriter);
                j = systemClock.uptimeMillis() - uptimeMillis;
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
