package com.android.systemui.keyguard;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda4;
import com.android.systemui.util.LogUtil;
import java.util.function.LongConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardFastBioUnlockController$onFrameRequest$1 extends FunctionReferenceImpl implements Function0 {
    public KeyguardFastBioUnlockController$onFrameRequest$1(Object obj) {
        super(0, obj, KeyguardFastBioUnlockController.class, "onFrameCommit", "onFrameCommit()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final KeyguardFastBioUnlockController keyguardFastBioUnlockController = (KeyguardFastBioUnlockController) this.receiver;
        KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
        keyguardFastBioUnlockController.getClass();
        KeyguardFastBioUnlockController.logD("onFrameCommit");
        if (keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
            keyguardFastBioUnlockController.setMode(keyguardFastBioUnlockController.getMode() | KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT);
            LogUtil.lapTime(10000, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameCommit$1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    String strM;
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardFastBioUnlockController;
                    long j2 = keyguardFastBioUnlockController2.startKeyguardExitAnimationTime - keyguardFastBioUnlockController2.goingAwayTime;
                    long jNanoTime = System.nanoTime();
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = keyguardFastBioUnlockController;
                    long j3 = (jNanoTime - keyguardFastBioUnlockController3.waitStartTime) / 1000000;
                    if ((keyguardFastBioUnlockController3.isFastUnlockMode() || (keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted)) && j2 > 0) {
                        long j4 = j2 / 1000000;
                        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("foreground is shown / vis=", j3, "ms, goingAway=");
                        sbM.append(j4);
                        sbM.append("ms, keyguard=");
                        sbM.append(j - j4);
                        sbM.append("ms, end=");
                        strM = MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j, "ms", sbM);
                    } else {
                        strM = keyguardFastBioUnlockController.needsBlankScreen ? MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j, "ms", SnapshotStateObserver$$ExternalSyntheticOutline0.m("foreground is shown / blankScreen, vis=", j3, "ms, end=")) : MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j, "ms", SnapshotStateObserver$$ExternalSyntheticOutline0.m("foreground is shown / vis=", j3, "ms, end="));
                    }
                    Log.d("BioUnlock", strM);
                }
            });
            ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFastBioUnlockController.viewMediatorHelperLazy.get())).onForegroundShown();
            keyguardFastBioUnlockController.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameCommit$2
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardFastBioUnlockController;
                    KeyguardFastBioUnlockController.Companion companion2 = KeyguardFastBioUnlockController.Companion;
                    CentralSurfacesImpl$$ExternalSyntheticLambda4 centralSurfacesImpl$$ExternalSyntheticLambda4 = keyguardFastBioUnlockController2.scrimUpdater;
                    if (centralSurfacesImpl$$ExternalSyntheticLambda4 != null && keyguardFastBioUnlockController2.scrimVisibility != 0) {
                        centralSurfacesImpl$$ExternalSyntheticLambda4.run();
                    }
                    keyguardFastBioUnlockController.runPendingRunnable();
                    KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams = keyguardFastBioUnlockController.delayedActionParams;
                    if (delayedActionParams != null && !delayedActionParams.isDiscard) {
                        Handler handler = delayedActionParams.handler;
                        KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 = delayedActionParams.runnableWrapper;
                        if (handler.hasCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1) && delayedActionParams.atTime - SystemClock.uptimeMillis() > 10) {
                            if (handler.hasCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1)) {
                                handler.removeCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1);
                            }
                            delayedActionParams.start(false);
                        }
                    }
                    if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
                        final KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = keyguardFastBioUnlockController;
                        if (keyguardFastBioUnlockController3.needsBlankScreen) {
                            keyguardFastBioUnlockController3.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameCommit$2.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    keyguardFastBioUnlockController3.reset();
                                }
                            });
                        }
                    }
                }
            });
        }
        return Unit.INSTANCE;
    }
}
