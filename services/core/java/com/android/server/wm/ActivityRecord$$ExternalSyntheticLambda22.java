package com.android.server.wm;

import android.view.SurfaceControl;

import com.android.server.display.color.ColorDisplayService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda22
        implements ColorDisplayService.ColorTransformController {
    public final /* synthetic */ ActivityRecord f$0;

    public final void applyAppSaturation(final float[] fArr, final float[] fArr2) {
        final ActivityRecord activityRecord = this.f$0;
        activityRecord.mWmService.mH.post(
                new Runnable() { // from class:
                                 // com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityRecord activityRecord2 = ActivityRecord.this;
                        float[] fArr3 = fArr;
                        float[] fArr4 = fArr2;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                activityRecord2.mWmService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                if (activityRecord2.mLastAppSaturationInfo == null) {
                                    ActivityRecord.AppSaturationInfo appSaturationInfo =
                                            new ActivityRecord.AppSaturationInfo();
                                    appSaturationInfo.mMatrix = new float[9];
                                    appSaturationInfo.mTranslation = new float[3];
                                    activityRecord2.mLastAppSaturationInfo = appSaturationInfo;
                                }
                                ActivityRecord.AppSaturationInfo appSaturationInfo2 =
                                        activityRecord2.mLastAppSaturationInfo;
                                float[] fArr5 = appSaturationInfo2.mMatrix;
                                System.arraycopy(fArr3, 0, fArr5, 0, fArr5.length);
                                float[] fArr6 = appSaturationInfo2.mTranslation;
                                System.arraycopy(fArr4, 0, fArr6, 0, fArr6.length);
                                if (activityRecord2.mSurfaceControl != null
                                        && activityRecord2.mLastAppSaturationInfo != null) {
                                    SurfaceControl.Transaction pendingTransaction =
                                            activityRecord2.getPendingTransaction();
                                    SurfaceControl surfaceControl = activityRecord2.mSurfaceControl;
                                    ActivityRecord.AppSaturationInfo appSaturationInfo3 =
                                            activityRecord2.mLastAppSaturationInfo;
                                    pendingTransaction.setColorTransform(
                                            surfaceControl,
                                            appSaturationInfo3.mMatrix,
                                            appSaturationInfo3.mTranslation);
                                    activityRecord2.mWmService.scheduleAnimationLocked();
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
    }
}
