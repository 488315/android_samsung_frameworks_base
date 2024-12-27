package com.android.systemui.keyguard;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.util.LogUtil;
import java.util.function.LongConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                    String m;
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = KeyguardFastBioUnlockController.this;
                    long j2 = keyguardFastBioUnlockController2.startKeyguardExitAnimationTime - keyguardFastBioUnlockController2.goingAwayTime;
                    long nanoTime = System.nanoTime();
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = KeyguardFastBioUnlockController.this;
                    long j3 = (nanoTime - keyguardFastBioUnlockController3.waitStartTime) / 1000000;
                    if ((keyguardFastBioUnlockController3.isFastUnlockMode() || (KeyguardFastBioUnlockController.this.isFastWakeAndUnlockMode() && KeyguardFastBioUnlockController.this.isInvisibleAfterGoingAwayTransStarted)) && j2 > 0) {
                        long j4 = j2 / 1000000;
                        StringBuilder m2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("foreground is shown / vis=", j3, "ms, goingAway=");
                        m2.append(j4);
                        m2.append("ms, keyguard=");
                        m2.append(j - j4);
                        m2.append("ms, end=");
                        m = MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j, "ms", m2);
                    } else {
                        m = KeyguardFastBioUnlockController.this.needsBlankScreen ? MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j, "ms", SnapshotStateObserver$$ExternalSyntheticOutline0.m("foreground is shown / blankScreen, vis=", j3, "ms, end=")) : MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j, "ms", SnapshotStateObserver$$ExternalSyntheticOutline0.m("foreground is shown / vis=", j3, "ms, end="));
                    }
                    Log.d("BioUnlock", m);
                }
            });
            ((KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFastBioUnlockController.viewMediatorHelperLazy.get())).onForegroundShown();
            keyguardFastBioUnlockController.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameCommit$2
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = KeyguardFastBioUnlockController.this;
                    KeyguardFastBioUnlockController.Companion companion2 = KeyguardFastBioUnlockController.Companion;
                    Runnable runnable = keyguardFastBioUnlockController2.scrimUpdater;
                    if (runnable != null && keyguardFastBioUnlockController2.scrimVisibility != 0) {
                        runnable.run();
                    }
                    KeyguardFastBioUnlockController.this.runPendingRunnable();
                    KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams = KeyguardFastBioUnlockController.this.delayedActionParams;
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
                    if (KeyguardFastBioUnlockController.this.isFastWakeAndUnlockMode()) {
                        final KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = KeyguardFastBioUnlockController.this;
                        if (keyguardFastBioUnlockController3.needsBlankScreen) {
                            keyguardFastBioUnlockController3.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameCommit$2.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    KeyguardFastBioUnlockController.this.reset();
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
