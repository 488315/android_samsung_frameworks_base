package com.android.server.accessibility.magnification;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FullScreenMagnificationController$$ExternalSyntheticLambda4
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    /* JADX WARN: Code restructure failed: missing block: B:62:0x005c, code lost:

       r8 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x009a, code lost:

       throw r8;
    */
    @Override // java.util.function.BiConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void accept(java.lang.Object r9, java.lang.Object r10) {
        /*
            r8 = this;
            int r8 = r8.$r8$classId
            switch(r8) {
                case 0: goto L9b;
                case 1: goto L49;
                default: goto L5;
            }
        L5:
            com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification r9 = (com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) r9
            android.graphics.Region r10 = (android.graphics.Region) r10
            com.android.server.accessibility.magnification.FullScreenMagnificationController r8 = r9.this$0
            java.lang.Object r8 = r8.mLock
            monitor-enter(r8)
            boolean r0 = r9.mRegistered     // Catch: java.lang.Throwable -> L14
            if (r0 != 0) goto L16
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L14
            goto L46
        L14:
            r9 = move-exception
            goto L47
        L16:
            android.graphics.Region r0 = r9.mMagnificationRegion     // Catch: java.lang.Throwable -> L14
            boolean r0 = r0.equals(r10)     // Catch: java.lang.Throwable -> L14
            if (r0 != 0) goto L42
            android.graphics.Region r0 = r9.mMagnificationRegion     // Catch: java.lang.Throwable -> L14
            r0.set(r10)     // Catch: java.lang.Throwable -> L14
            android.graphics.Region r0 = r9.mMagnificationRegion     // Catch: java.lang.Throwable -> L14
            android.graphics.Rect r1 = r9.mMagnificationBounds     // Catch: java.lang.Throwable -> L14
            r0.getBounds(r1)     // Catch: java.lang.Throwable -> L14
            r9.refreshThumbnail()     // Catch: java.lang.Throwable -> L14
            android.view.MagnificationSpec r0 = r9.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L14
            float r1 = r0.offsetX     // Catch: java.lang.Throwable -> L14
            float r0 = r0.offsetY     // Catch: java.lang.Throwable -> L14
            boolean r0 = r9.updateCurrentSpecWithOffsetsLocked(r1, r0)     // Catch: java.lang.Throwable -> L14
            if (r0 == 0) goto L3f
            android.view.MagnificationSpec r0 = r9.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L14
            r1 = 0
            r9.sendSpecToAnimation(r0, r1)     // Catch: java.lang.Throwable -> L14
        L3f:
            r9.onMagnificationChangedLocked()     // Catch: java.lang.Throwable -> L14
        L42:
            r10.recycle()     // Catch: java.lang.Throwable -> L14
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L14
        L46:
            return
        L47:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L14
            throw r9
        L49:
            com.android.server.accessibility.magnification.FullScreenMagnificationController r9 = (com.android.server.accessibility.magnification.FullScreenMagnificationController) r9
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r8 = r10.intValue()
            java.lang.Object r0 = r9.mLock
            monitor-enter(r0)
            boolean r10 = r9.isActivated(r8)     // Catch: java.lang.Throwable -> L5c
            if (r10 != 0) goto L5e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
            goto L98
        L5c:
            r8 = move-exception
            goto L99
        L5e:
            boolean r10 = r9.mAlwaysOnMagnificationEnabled     // Catch: java.lang.Throwable -> L5c
            r1 = 1
            if (r10 == 0) goto L8c
            java.lang.Object r10 = r9.mLock     // Catch: java.lang.Throwable -> L5c
            monitor-enter(r10)     // Catch: java.lang.Throwable -> L5c
            android.util.SparseArray r9 = r9.mDisplays     // Catch: java.lang.Throwable -> L86
            java.lang.Object r8 = r9.get(r8)     // Catch: java.lang.Throwable -> L86
            com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification r8 = (com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) r8     // Catch: java.lang.Throwable -> L86
            if (r8 == 0) goto L88
            boolean r9 = r8.mMagnificationActivated     // Catch: java.lang.Throwable -> L86
            if (r9 != 0) goto L75
            goto L88
        L75:
            android.view.accessibility.MagnificationAnimationCallback r6 = android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK     // Catch: java.lang.Throwable -> L86
            r7 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            r4 = 2143289344(0x7fc00000, float:NaN)
            r5 = 2143289344(0x7fc00000, float:NaN)
            r2 = r8
            r2.setScaleAndCenter(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L86
            r8.mZoomedOutFromService = r1     // Catch: java.lang.Throwable -> L86
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L86
            goto L97
        L86:
            r8 = move-exception
            goto L8a
        L88:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L86
            goto L97
        L8a:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L86
            throw r8     // Catch: java.lang.Throwable -> L5c
        L8c:
            boolean r10 = com.android.internal.accessibility.util.AccessibilityUtils.getVisiblityShortcutDialog()     // Catch: java.lang.Throwable -> L5c
            if (r10 == 0) goto L94
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
            goto L98
        L94:
            r9.reset(r8, r1)     // Catch: java.lang.Throwable -> L5c
        L97:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
        L98:
            return
        L99:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5c
            throw r8
        L9b:
            com.android.server.accessibility.magnification.FullScreenMagnificationController r9 = (com.android.server.accessibility.magnification.FullScreenMagnificationController) r9
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r8 = r10.booleanValue()
            r9.resetAllIfNeeded(r8)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda4.accept(java.lang.Object,"
                    + " java.lang.Object):void");
    }
}
