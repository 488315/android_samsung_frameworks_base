package android.view;

import android.content.res.CompatibilityInfo;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.animation.Interpolator;
import android.view.inputmethod.ImeTracker;

import java.util.Objects;

public class InsetsAnimationThreadControlRunner implements InsetsAnimationControlRunner {
    private static final String TAG = "InsetsAnimThreadRunner";
    private final InsetsAnimationControlCallbacks mCallbacks = new AnonymousClass1();
    private final InsetsAnimationControlImpl mControl;
    private final Handler mMainThreadHandler;
    private final InsetsAnimationControlCallbacks mOuterCallbacks;

    /* renamed from: android.view.InsetsAnimationThreadControlRunner$1, reason: invalid class name */
    class AnonymousClass1 implements InsetsAnimationControlCallbacks {
        private final float[] mTmpFloat9 = new float[9];

        AnonymousClass1() {}

        @Override // android.view.InsetsAnimationControlCallbacks
        public <T extends InsetsAnimationControlRunner & InternalInsetsAnimationController>
                void startAnimation(
                        T runner,
                        WindowInsetsAnimationControlListener listener,
                        int types,
                        WindowInsetsAnimation animation,
                        WindowInsetsAnimation.Bounds bounds) {}

        @Override // android.view.InsetsAnimationControlCallbacks
        public void scheduleApplyChangeInsets(InsetsAnimationControlRunner runner) {
            synchronized (InsetsAnimationThreadControlRunner.this.mControl) {
                InsetsAnimationThreadControlRunner.this.mControl.applyChangeInsets(null);
            }
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void notifyFinished(InsetsAnimationControlRunner runner, final boolean shown) {
            Trace.asyncTraceEnd(
                    8L,
                    "InsetsAsyncAnimation: " + WindowInsets.Type.toString(runner.getTypes()),
                    runner.getTypes());
            InsetsController.releaseControls(
                    InsetsAnimationThreadControlRunner.this.mControl.getControls());
            InsetsAnimationThreadControlRunner.this.mMainThreadHandler.post(
                    new Runnable() { // from class:
                                     // android.view.InsetsAnimationThreadControlRunner$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            InsetsAnimationThreadControlRunner.AnonymousClass1.this
                                    .lambda$notifyFinished$0(shown);
                        }
                    });
        }

        public /* synthetic */ void lambda$notifyFinished$0(boolean shown) {
            InsetsAnimationThreadControlRunner.this.mOuterCallbacks.notifyFinished(
                    InsetsAnimationThreadControlRunner.this, shown);
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... params) {
            if (InsetsController.DEBUG) {
                Log.d(InsetsAnimationThreadControlRunner.TAG, "applySurfaceParams");
            }
            SurfaceControl.Transaction t = new SurfaceControl.Transaction();
            for (int i = params.length - 1; i >= 0; i--) {
                SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams = params[i];
                SyncRtSurfaceTransactionApplier.applyParams(t, surfaceParams, this.mTmpFloat9);
            }
            t.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
            t.apply();
            t.close();
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void releaseSurfaceControlFromRt(SurfaceControl sc) {
            if (InsetsController.DEBUG) {
                Log.d(InsetsAnimationThreadControlRunner.TAG, "releaseSurfaceControlFromRt");
            }
            sc.release();
        }

        public /* synthetic */ void lambda$reportPerceptible$1(int types, boolean perceptible) {
            InsetsAnimationThreadControlRunner.this.mOuterCallbacks.reportPerceptible(
                    types, perceptible);
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void reportPerceptible(final int types, final boolean perceptible) {
            InsetsAnimationThreadControlRunner.this.mMainThreadHandler.post(
                    new Runnable() { // from class:
                                     // android.view.InsetsAnimationThreadControlRunner$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            InsetsAnimationThreadControlRunner.AnonymousClass1.this
                                    .lambda$reportPerceptible$1(types, perceptible);
                        }
                    });
        }
    }

    public InsetsAnimationThreadControlRunner(
            SparseArray<InsetsSourceControl> controls,
            Rect frame,
            InsetsState state,
            final WindowInsetsAnimationControlListener listener,
            final int types,
            InsetsAnimationControlCallbacks controller,
            long durationMs,
            Interpolator interpolator,
            int animationType,
            int layoutInsetsDuringAnimation,
            CompatibilityInfo.Translator translator,
            Handler mainThreadHandler,
            ImeTracker.Token statsToken) {
        this.mMainThreadHandler = mainThreadHandler;
        this.mOuterCallbacks = controller;
        this.mControl =
                new InsetsAnimationControlImpl(
                        controls,
                        frame,
                        state,
                        listener,
                        types,
                        this.mCallbacks,
                        durationMs,
                        interpolator,
                        animationType,
                        layoutInsetsDuringAnimation,
                        translator,
                        statsToken);
        InsetsAnimationThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                InsetsAnimationThreadControlRunner.this.lambda$new$0(
                                        types, listener);
                            }
                        });
    }

    public /* synthetic */ void lambda$new$0(
            int types, WindowInsetsAnimationControlListener listener) {
        if (this.mControl.isCancelled()) {
            return;
        }
        Trace.asyncTraceBegin(
                8L, "InsetsAsyncAnimation: " + WindowInsets.Type.toString(types), types);
        listener.onReady(this.mControl, types);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        this.mControl.dumpDebug(proto, fieldId);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public ImeTracker.Token getStatsToken() {
        return this.mControl.getStatsToken();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mControl.getTypes();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mControl.getControllingTypes();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int types) {
        this.mControl.notifyControlRevoked(types);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(SparseArray<InsetsSourceControl> controls) {
        synchronized (this.mControl) {
            this.mControl.updateSurfacePosition(controls);
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        Handler handler = InsetsAnimationThread.getHandler();
        final InsetsAnimationControlImpl insetsAnimationControlImpl = this.mControl;
        Objects.requireNonNull(insetsAnimationControlImpl);
        handler.post(
                new Runnable() { // from class:
                                 // android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsAnimationControlImpl.this.cancel();
                    }
                });
    }

    @Override // android.view.InsetsAnimationControlRunner
    public WindowInsetsAnimation getAnimation() {
        return this.mControl.getAnimation();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return this.mControl.getAnimationType();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateLayoutInsetsDuringAnimation(final int layoutInsetsDuringAnimation) {
        InsetsAnimationThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                InsetsAnimationThreadControlRunner.this
                                        .lambda$updateLayoutInsetsDuringAnimation$1(
                                                layoutInsetsDuringAnimation);
                            }
                        });
    }

    public /* synthetic */ void lambda$updateLayoutInsetsDuringAnimation$1(
            int layoutInsetsDuringAnimation) {
        this.mControl.updateLayoutInsetsDuringAnimation(layoutInsetsDuringAnimation);
    }
}
