package com.android.server.display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessTracker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ BrightnessTracker f$0;

    /* JADX WARN: Removed duplicated region for block: B:12:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            com.android.server.display.BrightnessTracker r7 = r7.f$0
            r0 = 0
            r7.mWriteBrightnessTrackerStateScheduled = r0
            java.lang.Object r1 = r7.mEventsLock
            monitor-enter(r1)
            boolean r2 = r7.mEventsDirty     // Catch: java.lang.Throwable -> Lf
            r3 = 0
            if (r2 != 0) goto L12
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            goto L58
        Lf:
            r7 = move-exception
            goto L8e
        L12:
            com.android.server.display.BrightnessTracker$Injector r2 = r7.mInjector     // Catch: java.lang.Throwable -> Lf
            java.lang.String r4 = "brightness_events.xml"
            r2.getClass()     // Catch: java.lang.Throwable -> Lf
            android.util.AtomicFile r2 = new android.util.AtomicFile     // Catch: java.lang.Throwable -> Lf
            java.io.File r5 = new java.io.File     // Catch: java.lang.Throwable -> Lf
            java.io.File r6 = android.os.Environment.getDataSystemDirectory()     // Catch: java.lang.Throwable -> Lf
            r5.<init>(r6, r4)     // Catch: java.lang.Throwable -> Lf
            r2.<init>(r5)     // Catch: java.lang.Throwable -> Lf
            com.android.internal.util.RingBuffer r4 = r7.mEvents     // Catch: java.lang.Throwable -> Lf
            boolean r4 = r4.isEmpty()     // Catch: java.lang.Throwable -> Lf
            if (r4 == 0) goto L3c
            boolean r4 = r2.exists()     // Catch: java.lang.Throwable -> Lf
            if (r4 == 0) goto L39
            r2.delete()     // Catch: java.lang.Throwable -> Lf
        L39:
            r7.mEventsDirty = r0     // Catch: java.lang.Throwable -> Lf
            goto L57
        L3c:
            java.io.FileOutputStream r4 = r2.startWrite()     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L4b
            r7.writeEventsLocked(r4)     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L49
            r2.finishWrite(r4)     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L49
            r7.mEventsDirty = r0     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L49
            goto L57
        L49:
            r0 = move-exception
            goto L4d
        L4b:
            r0 = move-exception
            r4 = r3
        L4d:
            r2.failWrite(r4)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r2 = "BrightnessTracker"
            java.lang.String r4 = "Failed to write change mEvents."
            android.util.Slog.e(r2, r4, r0)     // Catch: java.lang.Throwable -> Lf
        L57:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
        L58:
            com.android.server.display.BrightnessTracker$Injector r0 = r7.mInjector
            java.lang.String r1 = "ambient_brightness_stats.xml"
            r0.getClass()
            android.util.AtomicFile r0 = new android.util.AtomicFile
            java.io.File r2 = new java.io.File
            java.io.File r4 = android.os.Environment.getDataSystemDirectory()
            r2.<init>(r4, r1)
            r0.<init>(r2)
            java.io.FileOutputStream r3 = r0.startWrite()     // Catch: java.io.IOException -> L7e
            com.android.server.display.AmbientBrightnessStatsTracker r7 = r7.mAmbientBrightnessStatsTracker     // Catch: java.io.IOException -> L7e
            monitor-enter(r7)     // Catch: java.io.IOException -> L7e
            com.android.server.display.AmbientBrightnessStatsTracker$AmbientBrightnessStats r1 = r7.mAmbientBrightnessStats     // Catch: java.lang.Throwable -> L80
            r1.writeToXML(r3)     // Catch: java.lang.Throwable -> L80
            monitor-exit(r7)     // Catch: java.io.IOException -> L7e
            r0.finishWrite(r3)     // Catch: java.io.IOException -> L7e
            goto L8d
        L7e:
            r7 = move-exception
            goto L83
        L80:
            r1 = move-exception
            monitor-exit(r7)     // Catch: java.io.IOException -> L7e
            throw r1     // Catch: java.io.IOException -> L7e
        L83:
            r0.failWrite(r3)
            java.lang.String r0 = "BrightnessTracker"
            java.lang.String r1 = "Failed to write ambient brightness stats."
            android.util.Slog.e(r0, r1, r7)
        L8d:
            return
        L8e:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.BrightnessTracker$$ExternalSyntheticLambda1.run():void");
    }
}
