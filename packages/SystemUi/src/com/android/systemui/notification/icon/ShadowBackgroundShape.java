package com.android.systemui.notification.icon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadowBackgroundShape extends Shape {
    public Bitmap bitmap;
    public final Context ctx;
    public final Matrix matrix = new Matrix();
    public final Drawable shadow;
    public final float shadowRadius;
    public final Drawable source;

    public ShadowBackgroundShape(Context context, Drawable drawable, float f, int i) {
        this.ctx = context;
        this.source = drawable;
        this.shadow = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{i, i});
        this.shadowRadius = f;
    }

    @Override // android.graphics.drawable.shapes.Shape
    public final void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.bitmap, this.matrix, null);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public final void onResize(float f, float f2) {
        int intrinsicWidth = this.source.getIntrinsicWidth();
        int intrinsicHeight = this.source.getIntrinsicHeight();
        this.source.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        this.shadow.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        this.matrix.setRectToRect(new RectF(0.0f, 0.0f, intrinsicWidth, intrinsicHeight), new RectF(0.0f, 0.0f, f, f2), Matrix.ScaleToFit.CENTER);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.bitmap);
        this.shadow.draw(canvas);
        canvas.saveLayer(null, paint, 31);
        this.source.draw(canvas);
        canvas.restore();
        Bitmap bitmap = this.bitmap;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        RenderScript create = RenderScript.create(this.ctx);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 2);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius(this.shadowRadius);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        create.destroy();
        this.bitmap = createBitmap;
    }
}
