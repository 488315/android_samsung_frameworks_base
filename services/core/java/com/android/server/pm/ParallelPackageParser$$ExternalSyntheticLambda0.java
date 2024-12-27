package com.android.server.pm;

import android.os.Trace;
import com.android.server.pm.ParallelPackageParser;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ParallelPackageParser$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ParallelPackageParser f$0;
    public final /* synthetic */ File f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ParallelPackageParser$$ExternalSyntheticLambda0(ParallelPackageParser parallelPackageParser, File file, int i) {
        this.f$0 = parallelPackageParser;
        this.f$1 = file;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ParallelPackageParser parallelPackageParser = this.f$0;
        File file = this.f$1;
        int i = this.f$2;
        parallelPackageParser.getClass();
        ParallelPackageParser.ParseResult parseResult = new ParallelPackageParser.ParseResult();
        Trace.traceBegin(262144L, "parallel parsePackage [" + file + "]");
        try {
            try {
                parseResult.scanFile = file;
                parseResult.parsedPackage = parallelPackageParser.parsePackage(file, i);
            } finally {
                try {
                    ((ArrayBlockingQueue) parallelPackageParser.mQueue).put(parseResult);
                } finally {
                }
            }
            ((ArrayBlockingQueue) parallelPackageParser.mQueue).put(parseResult);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            parallelPackageParser.mInterruptedInThread = Thread.currentThread().getName();
        }
    }
}
