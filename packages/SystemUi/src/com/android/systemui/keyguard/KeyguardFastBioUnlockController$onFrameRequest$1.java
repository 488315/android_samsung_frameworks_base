package com.android.systemui.keyguard;

import android.os.Handler;
import android.os.SystemClock;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.util.LogUtil;
import java.util.function.LongConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        if (keyguardFastBioUnlockController.isEnabled()) {
            keyguardFastBioUnlockController.setMode(keyguardFastBioUnlockController.getMode() | KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT);
            LogUtil.internalLapTime(10000, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameCommit$1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    String sb;
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = KeyguardFastBioUnlockController.this;
                    long j2 = keyguardFastBioUnlockController2.startKeyguardExitAnimationTime - keyguardFastBioUnlockController2.goingAwayTime;
                    long nanoTime = System.nanoTime();
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController3 = KeyguardFastBioUnlockController.this;
                    long j3 = (nanoTime - keyguardFastBioUnlockController3.waitStartTime) / 1000000;
                    if ((keyguardFastBioUnlockController3.isFastUnlockMode() || (KeyguardFastBioUnlockController.this.isFastWakeAndUnlockMode() && KeyguardFastBioUnlockController.this.isInvisibleAfterGoingAwayTransStarted)) && j2 > 0) {
                        long j4 = j2 / 1000000;
                        StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("foreground is shown / vis=", j3, "ms, goingAway=");
                        m17m.append(j4);
                        m17m.append("ms, keyguard=");
                        m17m.append(j - j4);
                        m17m.append("ms, end=");
                        m17m.append(j);
                        m17m.append("ms");
                        sb = m17m.toString();
                    } else if (KeyguardFastBioUnlockController.this.needsBlankScreen) {
                        StringBuilder m17m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("foreground is shown / blankScreen, vis=", j3, "ms, end=");
                        m17m2.append(j);
                        m17m2.append("ms");
                        sb = m17m2.toString();
                    } else {
                        StringBuilder m17m3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("foreground is shown / vis=", j3, "ms, end=");
                        m17m3.append(j);
                        m17m3.append("ms");
                        sb = m17m3.toString();
                    }
                    Log.m138d("BioUnlock", sb);
                }
            }, null, null, new Object[0]);
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = (KeyguardViewMediatorHelperImpl) ((KeyguardViewMediatorHelper) keyguardFastBioUnlockController.viewMediatorHelperLazy.get());
            Log.m138d("BioUnlock", "onForegroundShown hasDrawnCb=" + (keyguardViewMediatorHelperImpl.drawnCallback != null));
            IKeyguardDrawnCallback iKeyguardDrawnCallback = keyguardViewMediatorHelperImpl.drawnCallback;
            if (iKeyguardDrawnCallback != null) {
                synchronized (keyguardViewMediatorHelperImpl.getLock()) {
                    keyguardViewMediatorHelperImpl.notifyDrawn(iKeyguardDrawnCallback);
                    keyguardViewMediatorHelperImpl.drawnCallback = null;
                    Unit unit = Unit.INSTANCE;
                }
            }
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
                    if (delayedActionParams != null) {
                        boolean z = delayedActionParams.isDiscard;
                        Handler handler = delayedActionParams.handler;
                        RunnableC1452xbe71cbf runnableC1452xbe71cbf = delayedActionParams.runnableWrapper;
                        if (!z && handler.hasCallbacks(runnableC1452xbe71cbf) && delayedActionParams.atTime - SystemClock.uptimeMillis() > 10) {
                            if (handler.hasCallbacks(runnableC1452xbe71cbf)) {
                                handler.removeCallbacks(runnableC1452xbe71cbf);
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
