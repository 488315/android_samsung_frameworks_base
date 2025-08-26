package com.android.wm.shell.shared.pip;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.view.SurfaceControl;
import android.window.TaskSnapshot;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public abstract class PipContentOverlay {
    public SurfaceControl mLeash;

    public final class PipAppIconOverlay extends PipContentOverlay {
        public final int mAppIconSizePx;
        public Bitmap mBitmap;
        public final int mOverlayHalfSize;
        public final Rect mRelativeAppBounds;
        public final Matrix mTmpTransform = new Matrix();
        public final float[] mTmpFloat9 = new float[9];

        public PipAppIconOverlay(Context context, Rect rect, Rect rect2, Drawable drawable, int i) {
            int iM = (int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, 72.0f);
            if (Math.min(iM, i) == 0) {
                this.mAppIconSizePx = iM;
            } else {
                this.mAppIconSizePx = Math.min(iM, i);
            }
            int iMax = Math.max(Math.max(rect.width(), rect.height()), Math.max(rect2.width(), rect2.height())) + 1;
            int i2 = iMax >> 1;
            this.mOverlayHalfSize = i2;
            this.mRelativeAppBounds = rect;
            this.mBitmap = Bitmap.createBitmap(iMax, iMax, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(this.mBitmap);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorBackground});
            try {
                int color = typedArrayObtainStyledAttributes.getColor(0, 0);
                canvas.drawRGB(Color.red(color), Color.green(color), Color.blue(color));
                typedArrayObtainStyledAttributes.recycle();
                int i3 = this.mAppIconSizePx / 2;
                int i4 = i2 - i3;
                int i5 = i3 + i2;
                drawable.setBounds(new Rect(i4, i4, i5, i5));
                drawable.draw(canvas);
                Bitmap bitmap = this.mBitmap;
                this.mBitmap = bitmap.copy(Bitmap.Config.HARDWARE, false);
                bitmap.recycle();
                this.mLeash = new SurfaceControl.Builder().setCallsite("PipContentOverlay$PipAppIconOverlay").setName("PipContentOverlay").build();
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        }

        @Override // com.android.wm.shell.shared.pip.PipContentOverlay
        public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(6, "PipTaskOrganizer", new StringBuilder("[PipAppIconOverlay] attached caller="));
            HardwareBuffer hardwareBuffer = this.mBitmap.getHardwareBuffer();
            transaction.show(this.mLeash);
            transaction.setLayer(this.mLeash, Integer.MAX_VALUE);
            transaction.setBuffer(this.mLeash, hardwareBuffer);
            transaction.setAlpha(this.mLeash, 0.0f);
            transaction.reparent(this.mLeash, surfaceControl);
            transaction.apply();
            this.mBitmap.recycle();
            this.mBitmap = null;
            hardwareBuffer.close();
        }

        @Override // com.android.wm.shell.shared.pip.PipContentOverlay
        public final void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
            this.mTmpTransform.reset();
            int iCenterX = this.mRelativeAppBounds.centerX();
            int iCenterY = this.mRelativeAppBounds.centerY();
            Matrix matrix = this.mTmpTransform;
            int i = this.mOverlayHalfSize;
            matrix.setTranslate(iCenterX - i, iCenterY - i);
            float fMin = Math.min(this.mRelativeAppBounds.width() / rect.width(), this.mRelativeAppBounds.height() / rect.height());
            this.mTmpTransform.postScale(fMin, fMin, iCenterX, iCenterY);
            transaction.setMatrix(this.mLeash, this.mTmpTransform, this.mTmpFloat9).setAlpha(this.mLeash, f < 0.5f ? 0.0f : 2.0f * (f - 0.5f));
        }
    }

    public final class PipColorOverlay extends PipContentOverlay {
        public final Context mContext;

        public PipColorOverlay(Context context) {
            this.mContext = context;
            this.mLeash = new SurfaceControl.Builder().setCallsite("PipContentOverlay$PipColorOverlay").setName("PipContentOverlay").setColorLayer().build();
        }

        @Override // com.android.wm.shell.shared.pip.PipContentOverlay
        public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(6, "PipTaskOrganizer", new StringBuilder("[PipColorOverlay] attached caller="));
            transaction.show(this.mLeash);
            transaction.setLayer(this.mLeash, Integer.MAX_VALUE);
            SurfaceControl surfaceControl2 = this.mLeash;
            TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{R.attr.colorBackground});
            try {
                int color = typedArrayObtainStyledAttributes.getColor(0, 0);
                float[] fArr = {Color.red(color) / 255.0f, Color.green(color) / 255.0f, Color.blue(color) / 255.0f};
                typedArrayObtainStyledAttributes.recycle();
                transaction.setColor(surfaceControl2, fArr);
                transaction.setAlpha(this.mLeash, 0.0f);
                transaction.reparent(this.mLeash, surfaceControl);
                transaction.apply();
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        }

        @Override // com.android.wm.shell.shared.pip.PipContentOverlay
        public final void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
            transaction.setAlpha(this.mLeash, f < 0.5f ? 0.0f : 2.0f * (f - 0.5f));
        }
    }

    public abstract void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

    public final void detach(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(6, "PipTaskOrganizer", new StringBuilder("[PipContentOverlay] detach caller="));
        transaction.remove(this.mLeash);
        transaction.apply();
    }

    public final class PipSnapshotOverlay extends PipContentOverlay {
        public final TaskSnapshot mSnapshot;
        public final Rect mSourceRectHint;

        public PipSnapshotOverlay(TaskSnapshot taskSnapshot, Rect rect) {
            this.mSnapshot = taskSnapshot;
            this.mSourceRectHint = new Rect(rect);
            this.mLeash = new SurfaceControl.Builder().setCallsite("PipContentOverlay$PipSnapshotOverlay").setName("PipContentOverlay").build();
        }

        @Override // com.android.wm.shell.shared.pip.PipContentOverlay
        public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(6, "PipTaskOrganizer", new StringBuilder("[PipSnapshotOverlay] attached caller="));
            transaction.show(this.mLeash);
            transaction.setLayer(this.mLeash, Integer.MAX_VALUE);
            transaction.setBuffer(this.mLeash, this.mSnapshot.getHardwareBuffer());
            SurfaceControl surfaceControl2 = this.mLeash;
            Rect rect = this.mSourceRectHint;
            transaction.setPosition(surfaceControl2, -rect.left, -rect.top);
            transaction.setScale(this.mLeash, this.mSnapshot.getTaskSize().x / this.mSnapshot.getHardwareBuffer().getWidth(), this.mSnapshot.getTaskSize().y / this.mSnapshot.getHardwareBuffer().getHeight());
            transaction.reparent(this.mLeash, surfaceControl);
            transaction.apply();
        }

        @Override // com.android.wm.shell.shared.pip.PipContentOverlay
        public final void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
        }
    }

    public void onAnimationUpdate(SurfaceControl.Transaction transaction, Rect rect, float f) {
    }
}
