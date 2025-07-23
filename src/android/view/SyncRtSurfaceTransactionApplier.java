package android.view;

import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.View;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class SyncRtSurfaceTransactionApplier {
    public static final int FLAG_ALL = -1;
    public static final int FLAG_ALPHA = 1;
    public static final int FLAG_BACKGROUND_BLUR_RADIUS = 32;
    public static final int FLAG_CORNER_RADIUS = 16;
    public static final int FLAG_EARLY_WAKEUP_END = 512;
    public static final int FLAG_EARLY_WAKEUP_START = 256;
    public static final int FLAG_LAYER = 8;
    public static final int FLAG_MATRIX = 2;
    public static final int FLAG_OPAQUE = 1024;
    public static final int FLAG_TRANSACTION = 128;
    public static final int FLAG_VISIBILITY = 64;
    public static final int FLAG_WINDOW_CROP = 4;
    private SurfaceControl mTargetSc;
    private final ViewRootImpl mTargetViewRootImpl;
    private final float[] mTmpFloat9 = new float[9];

    public SyncRtSurfaceTransactionApplier(View view) {
        this.mTargetViewRootImpl = view != null ? view.getViewRootImpl() : null;
    }

    public void scheduleApply(SurfaceParams... surfaceParamsArr) {
        ViewRootImpl viewRootImpl = this.mTargetViewRootImpl;
        if (viewRootImpl == null) {
            return;
        }
        this.mTargetSc = viewRootImpl.getSurfaceControl();
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        applyParams(transaction, surfaceParamsArr);
        this.mTargetViewRootImpl.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: android.view.SyncRtSurfaceTransactionApplier$$ExternalSyntheticLambda0
            @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
            public final void onFrameDraw(long j) {
                SyncRtSurfaceTransactionApplier.this.lambda$scheduleApply$0(transaction, j);
            }
        });
        this.mTargetViewRootImpl.getView().invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleApply$0(SurfaceControl.Transaction transaction, long j) {
        SurfaceControl surfaceControl = this.mTargetSc;
        if (surfaceControl != null && surfaceControl.isValid()) {
            applyTransaction(transaction, j);
        }
        transaction.close();
    }

    void applyParams(SurfaceControl.Transaction transaction, SurfaceParams... surfaceParamsArr) {
        for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
            SurfaceParams surfaceParams = surfaceParamsArr[length];
            SurfaceControl surfaceControl = surfaceParams.surface;
            applyParams(transaction, surfaceParams, this.mTmpFloat9);
        }
    }

    void applyTransaction(SurfaceControl.Transaction transaction, long j) {
        ViewRootImpl viewRootImpl = this.mTargetViewRootImpl;
        if (viewRootImpl != null) {
            viewRootImpl.mergeWithNextTransaction(transaction, j);
        } else {
            transaction.apply();
        }
    }

    public static void applyParams(SurfaceControl.Transaction transaction, SurfaceParams surfaceParams, float[] fArr) {
        if ((surfaceParams.flags & 128) != 0) {
            transaction.merge(surfaceParams.mergeTransaction);
        }
        if ((surfaceParams.flags & 2) != 0) {
            transaction.setMatrix(surfaceParams.surface, surfaceParams.matrix, fArr);
        }
        if ((surfaceParams.flags & 4) != 0) {
            transaction.setWindowCrop(surfaceParams.surface, surfaceParams.windowCrop);
        }
        if ((surfaceParams.flags & 1) != 0) {
            transaction.setAlpha(surfaceParams.surface, surfaceParams.alpha);
        }
        if ((surfaceParams.flags & 8) != 0) {
            transaction.setLayer(surfaceParams.surface, surfaceParams.layer);
        }
        if ((surfaceParams.flags & 16) != 0) {
            transaction.setCornerRadius(surfaceParams.surface, surfaceParams.cornerRadius);
        }
        if ((surfaceParams.flags & 32) != 0) {
            transaction.setBackgroundBlurRadius(surfaceParams.surface, surfaceParams.backgroundBlurRadius);
        }
        if ((surfaceParams.flags & 64) != 0) {
            if (surfaceParams.visible) {
                transaction.show(surfaceParams.surface);
            } else {
                transaction.hide(surfaceParams.surface);
            }
        }
        if ((surfaceParams.flags & 256) != 0) {
            transaction.setEarlyWakeupStart();
        }
        if ((surfaceParams.flags & 512) != 0) {
            transaction.setEarlyWakeupEnd();
        }
        if ((surfaceParams.flags & 1024) != 0) {
            transaction.setOpaque(surfaceParams.surface, surfaceParams.opaque);
        }
    }

    public static void create(final View view, final Consumer<SyncRtSurfaceTransactionApplier> consumer) {
        if (view == null) {
            consumer.accept(null);
        } else if (view.getViewRootImpl() != null) {
            consumer.accept(new SyncRtSurfaceTransactionApplier(view));
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: android.view.SyncRtSurfaceTransactionApplier.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view2) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view2) {
                    View.this.removeOnAttachStateChangeListener(this);
                    consumer.accept(new SyncRtSurfaceTransactionApplier(View.this));
                }
            });
        }
    }

    public static class SurfaceParams {
        public final float alpha;
        public final int backgroundBlurRadius;
        public final float cornerRadius;
        private final int flags;
        public final int layer;
        public final Matrix matrix;
        public final SurfaceControl.Transaction mergeTransaction;
        public final boolean opaque;
        public final SurfaceControl surface;
        public final boolean visible;
        public final Rect windowCrop;

        public static class Builder {
            float alpha;
            int backgroundBlurRadius;
            float cornerRadius;
            int flags;
            int layer;
            Matrix matrix;
            SurfaceControl.Transaction mergeTransaction;
            boolean opaque;
            final SurfaceControl surface;
            boolean visible;
            Rect windowCrop;

            public Builder(SurfaceControl surfaceControl) {
                this.surface = surfaceControl;
            }

            public Builder withAlpha(float f) {
                this.alpha = f;
                this.flags |= 1;
                return this;
            }

            public Builder withMatrix(Matrix matrix) {
                this.matrix = new Matrix(matrix);
                this.flags |= 2;
                return this;
            }

            public Builder withWindowCrop(Rect rect) {
                this.windowCrop = new Rect(rect);
                this.flags |= 4;
                return this;
            }

            public Builder withLayer(int i) {
                this.layer = i;
                this.flags |= 8;
                return this;
            }

            public Builder withCornerRadius(float f) {
                this.cornerRadius = f;
                this.flags |= 16;
                return this;
            }

            public Builder withBackgroundBlur(int i) {
                this.backgroundBlurRadius = i;
                this.flags |= 32;
                return this;
            }

            public Builder withVisibility(boolean z) {
                this.visible = z;
                this.flags |= 64;
                return this;
            }

            public Builder withMergeTransaction(SurfaceControl.Transaction transaction) {
                this.mergeTransaction = transaction;
                this.flags |= 128;
                return this;
            }

            public Builder withEarlyWakeupStart() {
                this.flags |= 256;
                return this;
            }

            public Builder withEarlyWakeupEnd() {
                this.flags |= 512;
                return this;
            }

            public Builder withOpaque(boolean z) {
                this.opaque = z;
                this.flags |= 1024;
                return this;
            }

            public SurfaceParams build() {
                return new SurfaceParams(this.surface, this.flags, this.alpha, this.matrix, this.windowCrop, this.layer, this.cornerRadius, this.backgroundBlurRadius, this.visible, this.mergeTransaction, this.opaque);
            }
        }

        private SurfaceParams(SurfaceControl surfaceControl, int i, float f, Matrix matrix, Rect rect, int i2, float f2, int i3, boolean z, SurfaceControl.Transaction transaction, boolean z2) {
            this.flags = i;
            this.surface = surfaceControl;
            this.alpha = f;
            this.matrix = matrix;
            this.windowCrop = rect;
            this.layer = i2;
            this.cornerRadius = f2;
            this.backgroundBlurRadius = i3;
            this.visible = z;
            this.mergeTransaction = transaction;
            this.opaque = z2;
        }
    }
}
