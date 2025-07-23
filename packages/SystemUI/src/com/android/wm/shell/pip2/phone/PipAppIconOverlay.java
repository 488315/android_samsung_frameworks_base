package com.android.wm.shell.pip2.phone;

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
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.wm.shell.shared.pip.PipContentOverlay;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PipAppIconOverlay extends PipContentOverlay {
    public static final String TAG = PipContentOverlay.PipAppIconOverlay.class.getSimpleName();
    public Bitmap mBitmap;
    public final int mOverlayHalfSize;
    public final Matrix mTmpTransform = new Matrix();
    public final float[] mTmpFloat9 = new float[9];

    public PipAppIconOverlay(Context context, Rect rect, Rect rect2, Drawable drawable, int i) {
        int min = Math.min((int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, 72.0f), i);
        int max = Math.max(Math.max(rect.width(), rect.height()), Math.max(rect2.width(), rect2.height())) + 1;
        int i2 = max >> 1;
        this.mOverlayHalfSize = i2;
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
            Bitmap bitmap = this.mBitmap;
            this.mBitmap = bitmap.copy(Bitmap.Config.HARDWARE, false);
            bitmap.recycle();
            this.mLeash = new SurfaceControl.Builder().setCallsite(TAG).setName("PipContentOverlay").build();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // com.android.wm.shell.shared.pip.PipContentOverlay
    public final void attach(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
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
}
