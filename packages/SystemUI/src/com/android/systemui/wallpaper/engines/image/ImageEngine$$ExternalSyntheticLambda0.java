package com.android.systemui.wallpaper.engines.image;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ImageEngine$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageEngine f$0;

    public /* synthetic */ ImageEngine$$ExternalSyntheticLambda0(ImageEngine imageEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = imageEngine;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0038, code lost:
    
        android.util.Log.i(r7.TAG, "isFrameDrawNeeded: draw needed. which=" + r2 + ", lastDrawn=(" + r7.mLastDrawnState + "), toDraw=(" + r3 + ")");
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            int r0 = r7.$r8$classId
            com.android.systemui.wallpaper.engines.image.ImageEngine r7 = r7.f$0
            switch(r0) {
                case 0: goto L12;
                default: goto L7;
            }
        L7:
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            r1 = 0
            r7.mSurfaceHolder = r1     // Catch: java.lang.Throwable -> Lf
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            return
        Lf:
            r7 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            throw r7
        L12:
            boolean r0 = r7.mIsEngineAlive
            r1 = 1
            r0 = r0 ^ r1
            if (r0 == 0) goto L1a
            goto L9c
        L1a:
            java.lang.String r0 = "isFrameDrawNeeded: draw needed. which="
            int r2 = r7.getSourceWhich()
            com.android.systemui.wallpaper.engines.image.ImageEngine$DrawState r3 = r7.estimateDrawStateToDraw(r2)
            java.lang.Object r4 = r7.mLock
            monitor-enter(r4)
            com.android.systemui.wallpaper.engines.image.ImageEngine$DrawState r5 = r7.mLastDrawnState     // Catch: java.lang.Throwable -> L34
            if (r5 == 0) goto L36
            boolean r5 = r5.equals(r3)     // Catch: java.lang.Throwable -> L34
            if (r5 != 0) goto L32
            goto L36
        L32:
            r1 = 0
            goto L36
        L34:
            r7 = move-exception
            goto L9d
        L36:
            if (r1 == 0) goto L60
            java.lang.String r5 = r7.TAG     // Catch: java.lang.Throwable -> L34
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L34
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L34
            r6.append(r2)     // Catch: java.lang.Throwable -> L34
            java.lang.String r0 = ", lastDrawn=("
            r6.append(r0)     // Catch: java.lang.Throwable -> L34
            com.android.systemui.wallpaper.engines.image.ImageEngine$DrawState r0 = r7.mLastDrawnState     // Catch: java.lang.Throwable -> L34
            r6.append(r0)     // Catch: java.lang.Throwable -> L34
            java.lang.String r0 = "), toDraw=("
            r6.append(r0)     // Catch: java.lang.Throwable -> L34
            r6.append(r3)     // Catch: java.lang.Throwable -> L34
            java.lang.String r0 = ")"
            r6.append(r0)     // Catch: java.lang.Throwable -> L34
            java.lang.String r0 = r6.toString()     // Catch: java.lang.Throwable -> L34
            android.util.Log.i(r5, r0)     // Catch: java.lang.Throwable -> L34
        L60:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L34
            com.android.systemui.wallpaper.log.WallpaperLogger r0 = r7.mLogger
            java.lang.String r2 = r7.TAG
            java.lang.String r3 = "onConfigurationChanged: redrawNeeded="
            java.lang.String r3 = com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m(r3, r1)
            com.android.systemui.wallpaper.log.WallpaperLoggerImpl r0 = (com.android.systemui.wallpaper.log.WallpaperLoggerImpl) r0
            r0.log(r2, r3)
            if (r1 == 0) goto L9c
            int r0 = r7.getSourceWhich()
            java.lang.Object r1 = r7.mSurfaceLock
            monitor-enter(r1)
            boolean r2 = r7.isSurfaceCreated()     // Catch: java.lang.Throwable -> L88
            if (r2 != 0) goto L8a
            java.lang.String r7 = r7.TAG     // Catch: java.lang.Throwable -> L88
            java.lang.String r0 = "drawFrameSynchronized: the surface holder is invalid"
            android.util.Log.i(r7, r0)     // Catch: java.lang.Throwable -> L88
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L88
            goto L9c
        L88:
            r7 = move-exception
            goto L9a
        L8a:
            android.graphics.Rect r2 = new android.graphics.Rect     // Catch: java.lang.Throwable -> L88
            android.view.SurfaceHolder r3 = r7.mSurfaceHolder     // Catch: java.lang.Throwable -> L88
            android.graphics.Rect r3 = r3.getSurfaceFrame()     // Catch: java.lang.Throwable -> L88
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L88
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L88
            r7.drawFrameSynchronized(r0, r2)
            goto L9c
        L9a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L88
            throw r7
        L9c:
            return
        L9d:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L34
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.image.ImageEngine$$ExternalSyntheticLambda0.run():void");
    }
}
