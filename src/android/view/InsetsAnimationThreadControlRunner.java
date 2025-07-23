package android.view;

import android.content.res.CompatibilityInfo;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsAnimationControlRunner;
import android.view.InsetsAnimationThreadControlRunner;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.inputmethod.ImeTracker;
import java.util.Objects;

/* loaded from: classes4.dex */
public class InsetsAnimationThreadControlRunner implements InsetsAnimationControlRunner {
    private static final String TAG = "InsetsAnimThreadRunner";
    private final InsetsAnimationControlCallbacks mCallbacks;
    private boolean mCancelRequested;
    private final InsetsAnimationControlImpl mControl;
    private final Handler mMainThreadHandler;
    private final InsetsAnimationControlCallbacks mOuterCallbacks;
    private final InsetsAnimationControlRunner.SurfaceParamsApplier mSurfaceParamsApplier;

    /* renamed from: android.view.InsetsAnimationThreadControlRunner$1, reason: invalid class name */
    class AnonymousClass1 implements InsetsAnimationControlCallbacks {
        @Override // android.view.InsetsAnimationControlCallbacks
        public <T extends InsetsAnimationControlRunner & InternalInsetsAnimationController> void startAnimation(T t, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, int i, WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        }

        AnonymousClass1() {
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void scheduleApplyChangeInsets(InsetsAnimationControlRunner insetsAnimationControlRunner) {
            synchronized (InsetsAnimationThreadControlRunner.this.mControl) {
                InsetsAnimationThreadControlRunner.this.mControl.applyChangeInsets(null);
            }
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void notifyFinished(InsetsAnimationControlRunner insetsAnimationControlRunner, final boolean z) {
            Trace.asyncTraceEnd(8L, "InsetsAsyncAnimation: " + WindowInsets.Type.toString(insetsAnimationControlRunner.getTypes()), insetsAnimationControlRunner.getTypes());
            InsetsController.releaseControls(InsetsAnimationThreadControlRunner.this.mControl.getControls());
            InsetsAnimationThreadControlRunner.this.mMainThreadHandler.post(new Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    InsetsAnimationThreadControlRunner.AnonymousClass1.this.lambda$notifyFinished$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyFinished$0(boolean z) {
            InsetsAnimationThreadControlRunner.this.mOuterCallbacks.notifyFinished(InsetsAnimationThreadControlRunner.this, z);
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void releaseSurfaceControlFromRt(SurfaceControl surfaceControl) {
            if (InsetsController.DEBUG) {
                Log.d(InsetsAnimationThreadControlRunner.TAG, "releaseSurfaceControlFromRt");
            }
            surfaceControl.release();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$reportPerceptible$1(int i, boolean z) {
            InsetsAnimationThreadControlRunner.this.mOuterCallbacks.reportPerceptible(i, z);
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void reportPerceptible(final int i, final boolean z) {
            InsetsAnimationThreadControlRunner.this.mMainThreadHandler.post(new Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InsetsAnimationThreadControlRunner.AnonymousClass1.this.lambda$reportPerceptible$1(i, z);
                }
            });
        }
    }

    public InsetsAnimationThreadControlRunner(SparseArray<InsetsSourceControl> sparseArray, Rect rect, InsetsState insetsState, final WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, final int i, InsetsAnimationControlCallbacks insetsAnimationControlCallbacks, InsetsAnimationSpec insetsAnimationSpec, int i2, int i3, CompatibilityInfo.Translator translator, Handler handler, ImeTracker.Token token) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mCallbacks = anonymousClass1;
        InsetsAnimationControlRunner.SurfaceParamsApplier surfaceParamsApplier = new InsetsAnimationControlRunner.SurfaceParamsApplier(this) { // from class: android.view.InsetsAnimationThreadControlRunner.2
            private final float[] mTmpFloat9 = new float[9];

            @Override // android.view.InsetsAnimationControlRunner.SurfaceParamsApplier
            public void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
                    SyncRtSurfaceTransactionApplier.applyParams(transaction, surfaceParamsArr[length], this.mTmpFloat9);
                }
                transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
                transaction.apply();
                transaction.close();
            }
        };
        this.mSurfaceParamsApplier = surfaceParamsApplier;
        this.mMainThreadHandler = handler;
        this.mOuterCallbacks = insetsAnimationControlCallbacks;
        this.mControl = new InsetsAnimationControlImpl(sparseArray, rect, insetsState, windowInsetsAnimationControlListener, i, anonymousClass1, surfaceParamsApplier, insetsAnimationSpec, i2, i3, translator, token);
        InsetsAnimationThread.getHandler().post(new Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                InsetsAnimationThreadControlRunner.this.lambda$new$0(i, windowInsetsAnimationControlListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        if (this.mControl.isCancelled()) {
            return;
        }
        Trace.asyncTraceBegin(8L, "InsetsAsyncAnimation: " + WindowInsets.Type.toString(i), i);
        windowInsetsAnimationControlListener.onReady(this.mControl, i);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        this.mControl.dumpDebug(protoOutputStream, j);
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
    public void notifyControlRevoked(int i) {
        this.mControl.notifyControlRevoked(i);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(SparseArray<InsetsSourceControl> sparseArray) {
        synchronized (this.mControl) {
            this.mControl.updateSurfacePosition(sparseArray);
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public boolean willUpdateSurface() {
        boolean willUpdateSurface;
        synchronized (this.mControl) {
            willUpdateSurface = this.mControl.willUpdateSurface();
        }
        return willUpdateSurface;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        if (InsetsController.DEBUG) {
            Log.d(TAG, "cancel, caller=" + Debug.getCallers(5));
        }
        this.mCancelRequested = true;
        Handler handler = InsetsAnimationThread.getHandler();
        final InsetsAnimationControlImpl insetsAnimationControlImpl = this.mControl;
        Objects.requireNonNull(insetsAnimationControlImpl);
        handler.post(new Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda2
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
    public InsetsAnimationControlRunner.SurfaceParamsApplier getSurfaceParamsApplier() {
        return this.mSurfaceParamsApplier;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateLayoutInsetsDuringAnimation(final int i) {
        InsetsAnimationThread.getHandler().post(new Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                InsetsAnimationThreadControlRunner.this.lambda$updateLayoutInsetsDuringAnimation$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateLayoutInsetsDuringAnimation$1(int i) {
        this.mControl.updateLayoutInsetsDuringAnimation(i);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public boolean isCancelRequested() {
        return this.mCancelRequested;
    }
}
