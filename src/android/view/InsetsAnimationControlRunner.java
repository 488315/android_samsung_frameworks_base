package android.view;

import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsAnimationControlRunner;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.inputmethod.ImeTracker;

/* loaded from: classes4.dex */
public interface InsetsAnimationControlRunner {
    void cancel();

    void dumpDebug(ProtoOutputStream protoOutputStream, long j);

    WindowInsetsAnimation getAnimation();

    int getAnimationType();

    int getControllingTypes();

    ImeTracker.Token getStatsToken();

    SurfaceParamsApplier getSurfaceParamsApplier();

    int getTypes();

    boolean isCancelRequested();

    void notifyControlRevoked(int i);

    void updateLayoutInsetsDuringAnimation(int i);

    void updateSurfacePosition(SparseArray<InsetsSourceControl> sparseArray);

    boolean willUpdateSurface();

    default boolean controlsType(int i) {
        return (getTypes() & i) != 0;
    }

    public interface SurfaceParamsApplier {
        public static final SurfaceParamsApplier DEFAULT = new SurfaceParamsApplier() { // from class: android.view.InsetsAnimationControlRunner$SurfaceParamsApplier$$ExternalSyntheticLambda0
            @Override // android.view.InsetsAnimationControlRunner.SurfaceParamsApplier
            public final void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams[] surfaceParamsArr) {
                InsetsAnimationControlRunner.SurfaceParamsApplier.lambda$static$0(surfaceParamsArr);
            }
        };

        void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr);

        static /* synthetic */ void lambda$static$0(SyncRtSurfaceTransactionApplier.SurfaceParams[] surfaceParamsArr) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
                SyncRtSurfaceTransactionApplier.applyParams(transaction, surfaceParamsArr[length], new float[9]);
            }
            transaction.apply();
            transaction.close();
        }
    }
}
