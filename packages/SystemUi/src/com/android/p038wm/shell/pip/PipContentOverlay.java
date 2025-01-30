package com.android.p038wm.shell.pip;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.window.TaskSnapshot;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class PipContentOverlay {
    public SurfaceControl mLeash;

    public abstract void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

    public void detach(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(6, new StringBuilder("[PipContentOverlay] detach caller="), "PipTaskOrganizer");
        transaction.remove(this.mLeash);
        transaction.apply();
    }

    public abstract void onAnimationEnd(SurfaceControl.Transaction transaction);

    public abstract void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipAppIconOverlay extends PipContentOverlay {
        public final Rect mAppBounds;
        public Bitmap mBitmap;
        public final int mOverlayHalfSize;
        public final Matrix mTmpTransform = new Matrix();
        public final float[] mTmpFloat9 = new float[9];

        public PipAppIconOverlay(Context context, Rect rect, Rect rect2, Drawable drawable, int i) {
            int min = Math.min((int) TypedValue.applyDimension(1, 72.0f, context.getResources().getDisplayMetrics()), i);
            int width = rect.width();
            int height = rect.height();
            int max = Math.max(Math.max(width, height), Math.max(rect2.width(), rect2.height())) + 1;
            int i2 = max >> 1;
            this.mOverlayHalfSize = i2;
            this.mAppBounds = new Rect(0, 0, width, height);
            this.mBitmap = Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(this.mBitmap);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorBackground});
            try {
                int color = obtainStyledAttributes.getColor(0, 0);
                canvas.drawRGB(Color.red(color), Color.green(color), Color.blue(color));
                obtainStyledAttributes.recycle();
                int i3 = min / 2;
                int i4 = i2 - i3;
                int i5 = i3 + i2;
                drawable.setBounds(new Rect(i4, i4, i5, i5));
                drawable.draw(canvas);
                this.mBitmap = this.mBitmap.copy(Bitmap.Config.HARDWARE, false);
                this.mLeash = new SurfaceControl.Builder(new SurfaceSession()).setCallsite("PipContentOverlay$PipAppIconOverlay").setName("PipContentOverlay").build();
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            transaction.show(this.mLeash);
            transaction.setLayer(this.mLeash, Integer.MAX_VALUE);
            transaction.setBuffer(this.mLeash, this.mBitmap.getHardwareBuffer());
            transaction.setAlpha(this.mLeash, 0.0f);
            transaction.reparent(this.mLeash, surfaceControl);
            transaction.apply();
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void detach(SurfaceControl.Transaction transaction) {
            super.detach(transaction);
            Bitmap bitmap = this.mBitmap;
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            this.mBitmap.recycle();
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
            Matrix matrix = this.mTmpTransform;
            matrix.reset();
            Rect rect2 = this.mAppBounds;
            int centerX = rect2.centerX();
            int centerY = rect2.centerY();
            int i = this.mOverlayHalfSize;
            matrix.setTranslate(centerX - i, centerY - i);
            matrix.postScale(rect2.width() / rect.width(), rect2.height() / rect.height(), centerX, centerY);
            transaction.setMatrix(this.mLeash, matrix, this.mTmpFloat9).setAlpha(this.mLeash, f < 0.5f ? 0.0f : 2.0f * (f - 0.5f));
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void onAnimationEnd(SurfaceControl.Transaction transaction) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipColorOverlay extends PipContentOverlay {
        public final Context mContext;

        public PipColorOverlay(Context context) {
            this.mContext = context;
            this.mLeash = new SurfaceControl.Builder(new SurfaceSession()).setCallsite("PipContentOverlay$PipColorOverlay").setName("PipContentOverlay").setColorLayer().build();
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(6, new StringBuilder("[PipColorOverlay] attached caller="), "PipTaskOrganizer");
            transaction.show(this.mLeash);
            transaction.setLayer(this.mLeash, Integer.MAX_VALUE);
            SurfaceControl surfaceControl2 = this.mLeash;
            TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{R.attr.colorBackground});
            try {
                int color = obtainStyledAttributes.getColor(0, 0);
                float[] fArr = {Color.red(color) / 255.0f, Color.green(color) / 255.0f, Color.blue(color) / 255.0f};
                obtainStyledAttributes.recycle();
                transaction.setColor(surfaceControl2, fArr);
                transaction.setAlpha(this.mLeash, 0.0f);
                transaction.reparent(this.mLeash, surfaceControl);
                transaction.apply();
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
            transaction.setAlpha(this.mLeash, f < 0.5f ? 0.0f : 2.0f * (f - 0.5f));
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void onAnimationEnd(SurfaceControl.Transaction transaction) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipSnapshotOverlay extends PipContentOverlay {
        public final TaskSnapshot mSnapshot;
        public final Rect mSourceRectHint;

        public PipSnapshotOverlay(TaskSnapshot taskSnapshot, Rect rect) {
            this.mSnapshot = taskSnapshot;
            this.mSourceRectHint = new Rect(rect);
            this.mLeash = new SurfaceControl.Builder(new SurfaceSession()).setCallsite("PipContentOverlay$PipSnapshotOverlay").setName("PipContentOverlay").build();
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(6, new StringBuilder("[PipSnapshotOverlay] attached caller="), "PipTaskOrganizer");
            TaskSnapshot taskSnapshot = this.mSnapshot;
            float width = taskSnapshot.getTaskSize().x / taskSnapshot.getHardwareBuffer().getWidth();
            float height = taskSnapshot.getTaskSize().y / taskSnapshot.getHardwareBuffer().getHeight();
            transaction.show(this.mLeash);
            transaction.setLayer(this.mLeash, Integer.MAX_VALUE);
            transaction.setBuffer(this.mLeash, taskSnapshot.getHardwareBuffer());
            SurfaceControl surfaceControl2 = this.mLeash;
            Rect rect = this.mSourceRectHint;
            transaction.setPosition(surfaceControl2, -rect.left, -rect.top);
            transaction.setScale(this.mLeash, width, height);
            transaction.reparent(this.mLeash, surfaceControl);
            transaction.apply();
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void onAnimationEnd(SurfaceControl.Transaction transaction) {
            transaction.remove(this.mLeash);
        }

        @Override // com.android.p038wm.shell.pip.PipContentOverlay
        public final void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
        }
    }
}
