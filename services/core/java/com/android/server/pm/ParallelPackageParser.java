package com.android.server.pm;

import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.PackageParserException;
import com.android.internal.pm.parsing.pkg.ParsedPackage;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ParallelPackageParser {
    public final ExecutorService mExecutorService;
    public volatile String mInterruptedInThread;
    public final PackageParser2 mPackageParser;
    public final BlockingQueue mQueue = new ArrayBlockingQueue(30);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ParseResult {
        public ParsedPackage parsedPackage;
        public File scanFile;
        public Throwable throwable;

        public final String toString() {
            return "ParseResult{parsedPackage="
                    + this.parsedPackage
                    + ", scanFile="
                    + this.scanFile
                    + ", throwable="
                    + this.throwable
                    + '}';
        }
    }

    public ParallelPackageParser(PackageParser2 packageParser2, ExecutorService executorService) {
        this.mPackageParser = packageParser2;
        this.mExecutorService = executorService;
    }

    public ParsedPackage parsePackage(File file, int i) throws PackageManagerException {
        try {
            return this.mPackageParser.parsePackage(file, i, true);
        } catch (PackageParserException e) {
            throw new PackageManagerException(e.error, e.getMessage(), e);
        }
    }

    public final ParseResult take() {
        try {
            if (this.mInterruptedInThread == null) {
                return (ParseResult) ((ArrayBlockingQueue) this.mQueue).take();
            }
            throw new InterruptedException("Interrupted in " + this.mInterruptedInThread);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
