package com.android.server.display;

public final /* synthetic */ class AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ AdaptiveBrightnessLongtermModelBuilder f$0;

    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r5 = this;
            com.android.server.display.AdaptiveBrightnessLongtermModelBuilder r5 = r5.f$0
            r0 = 0
            r5.mWriteAdaptiveBrightnessLongtermModelBuilderStateScheduled = r0
            java.lang.Object r1 = r5.mEventsLock
            monitor-enter(r1)
            boolean r2 = r5.mEventsDirty     // Catch: java.lang.Throwable -> Lf
            r3 = 0
            if (r2 != 0) goto L12
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            goto L4e
        Lf:
            r5 = move-exception
            goto L84
        L12:
            com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$Injector r2 = r5.mInjector     // Catch: java.lang.Throwable -> Lf
            java.lang.String r4 = "brightness_events_sec.xml"
            r2.getClass()     // Catch: java.lang.Throwable -> Lf
            android.util.AtomicFile r2 = com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.Injector.getFile(r4)     // Catch: java.lang.Throwable -> Lf
            com.android.internal.util.RingBuffer r4 = r5.mEvents     // Catch: java.lang.Throwable -> Lf
            boolean r4 = r4.isEmpty()     // Catch: java.lang.Throwable -> Lf
            if (r4 == 0) goto L32
            boolean r4 = r2.exists()     // Catch: java.lang.Throwable -> Lf
            if (r4 == 0) goto L2f
            r2.delete()     // Catch: java.lang.Throwable -> Lf
        L2f:
            r5.mEventsDirty = r0     // Catch: java.lang.Throwable -> Lf
            goto L4d
        L32:
            java.io.FileOutputStream r4 = r2.startWrite()     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L41
            r5.writeEventsLocked(r4)     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L3f
            r2.finishWrite(r4)     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L3f
            r5.mEventsDirty = r0     // Catch: java.lang.Throwable -> Lf java.io.IOException -> L3f
            goto L4d
        L3f:
            r0 = move-exception
            goto L43
        L41:
            r0 = move-exception
            r4 = r3
        L43:
            r2.failWrite(r4)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r2 = "AdaptiveBrightnessLongtermModelBuilder"
            java.lang.String r4 = "Failed to write change mEvents."
            android.util.Slog.e(r2, r4, r0)     // Catch: java.lang.Throwable -> Lf
        L4d:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
        L4e:
            com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$Injector r0 = r5.mInjector
            java.lang.String r1 = "adaptive_brightness_stats_sec.xml"
            r0.getClass()
            android.util.AtomicFile r0 = com.android.server.display.AdaptiveBrightnessLongtermModelBuilder.Injector.getFile(r1)
            java.io.FileOutputStream r3 = r0.startWrite()     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
            com.android.server.display.AdaptiveBrightnessStatsTracker r5 = r5.mAdaptiveBrightnessStatsTracker     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
            monitor-enter(r5)     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
            com.android.server.display.AdaptiveBrightnessStatsTracker$AdaptiveBrightnessStats r1 = r5.mAdaptiveBrightnessStats     // Catch: java.lang.Throwable -> L6e
            r1.writeToXML(r3)     // Catch: java.lang.Throwable -> L6e
            monitor-exit(r5)     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
            r0.finishWrite(r3)     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
            goto L83
        L6a:
            r5 = move-exception
            goto L71
        L6c:
            r5 = move-exception
            goto L79
        L6e:
            r1 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
            throw r1     // Catch: java.lang.Exception -> L6a java.io.IOException -> L6c
        L71:
            java.lang.String r0 = "AdaptiveBrightnessLongtermModelBuilder"
            java.lang.String r1 = "Failed to write ambient brightness stats. Exception"
            android.util.Slog.e(r0, r1, r5)
            goto L83
        L79:
            r0.failWrite(r3)
            java.lang.String r0 = "AdaptiveBrightnessLongtermModelBuilder"
            java.lang.String r1 = "Failed to write ambient brightness stats. IOException"
            android.util.Slog.e(r0, r1, r5)
        L83:
            return
        L84:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.AdaptiveBrightnessLongtermModelBuilder$$ExternalSyntheticLambda1.run():void");
    }
}
