package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;

import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges;

/* loaded from: classes5.dex */
public class AndroidPaintContext extends PaintContext {
    Canvas mCanvas;
    Paint mPaint;
    Rect mTmpRect;

    public AndroidPaintContext(RemoteContext context, Canvas canvas) {
        super(context);
        this.mPaint = new Paint();
        this.mTmpRect = new Rect();
        this.mCanvas = canvas;
    }

    public Canvas getCanvas() {
        return this.mCanvas;
    }

    public void setCanvas(Canvas canvas) {
        this.mCanvas = canvas;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawBitmap(
            int imageId,
            int srcLeft,
            int srcTop,
            int srcRight,
            int srcBottom,
            int dstLeft,
            int dstTop,
            int dstRight,
            int dstBottom,
            int cdId) {
        AndroidRemoteContext androidContext = (AndroidRemoteContext) this.mContext;
        if (androidContext.mRemoteComposeState.containsId(imageId)) {
            Bitmap bitmap = (Bitmap) androidContext.mRemoteComposeState.getFromId(imageId);
            this.mCanvas.drawBitmap(
                    bitmap,
                    new Rect(srcLeft, srcTop, srcRight, srcBottom),
                    new Rect(dstLeft, dstTop, dstRight, dstBottom),
                    this.mPaint);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void scale(float scaleX, float scaleY) {
        this.mCanvas.scale(scaleX, scaleY);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void translate(float translateX, float translateY) {
        this.mCanvas.translate(translateX, translateY);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawArc(
            float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        this.mCanvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, true, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawBitmap(int id, float left, float top, float right, float bottom) {
        AndroidRemoteContext androidContext = (AndroidRemoteContext) this.mContext;
        if (androidContext.mRemoteComposeState.containsId(id)) {
            Bitmap bitmap = (Bitmap) androidContext.mRemoteComposeState.getFromId(id);
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF dst = new RectF(left, top, right, bottom);
            this.mCanvas.drawBitmap(bitmap, src, dst, this.mPaint);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawCircle(float centerX, float centerY, float radius) {
        this.mCanvas.drawCircle(centerX, centerY, radius, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawLine(float x1, float y1, float x2, float y2) {
        this.mCanvas.drawLine(x1, y1, x2, y2, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawOval(float left, float top, float right, float bottom) {
        this.mCanvas.drawOval(left, top, right, bottom, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawPath(int id, float start, float end) {
        this.mCanvas.drawPath(getPath(id, start, end), this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawRect(float left, float top, float right, float bottom) {
        this.mCanvas.drawRect(left, top, right, bottom, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawRoundRect(
            float left, float top, float right, float bottom, float radiusX, float radiusY) {
        this.mCanvas.drawRoundRect(left, top, right, bottom, radiusX, radiusY, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawTextOnPath(int textId, int pathId, float hOffset, float vOffset) {
        this.mCanvas.drawTextOnPath(
                getText(textId), getPath(pathId, 0.0f, 1.0f), hOffset, vOffset, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void getTextBounds(int textId, int start, int end, boolean monospace, float[] bounds) {
        String str = getText(textId);
        if (end == -1) {
            end = str.length();
        }
        this.mPaint.getTextBounds(str, start, end, this.mTmpRect);
        bounds[0] = this.mTmpRect.left;
        bounds[1] = this.mTmpRect.top;
        bounds[2] =
                monospace
                        ? this.mPaint.measureText(str, start, end) - this.mTmpRect.left
                        : this.mTmpRect.right;
        bounds[3] = this.mTmpRect.bottom;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawTextRun(
            int textID,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            float x,
            float y,
            boolean rtl) {
        String textToPaint = getText(textID);
        if (end == -1) {
            if (start != 0) {
                textToPaint = textToPaint.substring(start);
            }
        } else {
            textToPaint = textToPaint.substring(start, end);
        }
        this.mCanvas.drawText(textToPaint, x, y, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawTweenPath(int path1Id, int path2Id, float tween, float start, float end) {
        this.mCanvas.drawPath(getPath(path1Id, path2Id, tween, start, end), this.mPaint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PorterDuff.Mode origamiToPorterDuffMode(int mode) {
        switch (mode) {
            case 0:
                return PorterDuff.Mode.CLEAR;
            case 1:
                return PorterDuff.Mode.SRC;
            case 2:
                return PorterDuff.Mode.DST;
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 4:
                return PorterDuff.Mode.DST_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 6:
                return PorterDuff.Mode.DST_IN;
            case 7:
                return PorterDuff.Mode.SRC_OUT;
            case 8:
                return PorterDuff.Mode.DST_OUT;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 10:
                return PorterDuff.Mode.DST_ATOP;
            case 11:
                return PorterDuff.Mode.XOR;
            case 12:
            case 13:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
                return PorterDuff.Mode.SRC_OVER;
            case 14:
                return PorterDuff.Mode.SCREEN;
            case 15:
                return PorterDuff.Mode.OVERLAY;
            case 16:
                return PorterDuff.Mode.DARKEN;
            case 17:
                return PorterDuff.Mode.LIGHTEN;
            case 24:
                return PorterDuff.Mode.MULTIPLY;
            case 30:
                return PorterDuff.Mode.ADD;
        }
    }

    public static BlendMode origamiToBlendMode(int mode) {
        switch (mode) {
        }
        return null;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void applyPaint(PaintBundle mPaintData) {
        mPaintData.applyPaintChange(
                this,
                new PaintChanges() { // from class:
                                     // com.android.internal.widget.remotecompose.player.platform.AndroidPaintContext.1
                    Shader.TileMode[] mTileModes = {
                        Shader.TileMode.CLAMP, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR
                    };

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setTextSize(float size) {
                        AndroidPaintContext.this.mPaint.setTextSize(size);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setTypeFace(int fontType, int weight, boolean italic) {
                        int[] iArr = {0, 1, 2, 3};
                        switch (fontType) {
                            case 0:
                                if (weight == 400 && !italic) {
                                    AndroidPaintContext.this.mPaint.setTypeface(Typeface.DEFAULT);
                                    break;
                                } else {
                                    AndroidPaintContext.this.mPaint.setTypeface(
                                            Typeface.create(Typeface.DEFAULT, weight, italic));
                                    break;
                                }
                            case 1:
                                if (weight == 400 && !italic) {
                                    AndroidPaintContext.this.mPaint.setTypeface(
                                            Typeface.SANS_SERIF);
                                    break;
                                } else {
                                    AndroidPaintContext.this.mPaint.setTypeface(
                                            Typeface.create(Typeface.SANS_SERIF, weight, italic));
                                    break;
                                }
                            case 2:
                                if (weight == 400 && !italic) {
                                    AndroidPaintContext.this.mPaint.setTypeface(Typeface.SERIF);
                                    break;
                                } else {
                                    AndroidPaintContext.this.mPaint.setTypeface(
                                            Typeface.create(Typeface.SERIF, weight, italic));
                                    break;
                                }
                            case 3:
                                if (weight == 400 && !italic) {
                                    AndroidPaintContext.this.mPaint.setTypeface(Typeface.MONOSPACE);
                                    break;
                                } else {
                                    AndroidPaintContext.this.mPaint.setTypeface(
                                            Typeface.create(Typeface.MONOSPACE, weight, italic));
                                    break;
                                }
                        }
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setStrokeWidth(float width) {
                        AndroidPaintContext.this.mPaint.setStrokeWidth(width);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setColor(int color) {
                        AndroidPaintContext.this.mPaint.setColor(color);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setStrokeCap(int cap) {
                        AndroidPaintContext.this.mPaint.setStrokeCap(Paint.Cap.values()[cap]);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setStyle(int style) {
                        AndroidPaintContext.this.mPaint.setStyle(Paint.Style.values()[style]);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setShader(int shaderId) {
                        if (shaderId == 0) {
                            AndroidPaintContext.this.mPaint.setShader(null);
                            return;
                        }
                        ShaderData data = AndroidPaintContext.this.getShaderData(shaderId);
                        RuntimeShader shader =
                                new RuntimeShader(
                                        AndroidPaintContext.this.getText(data.getShaderTextId()));
                        String[] names = data.getUniformFloatNames();
                        for (String name : names) {
                            float[] val = data.getUniformFloats(name);
                            shader.setFloatUniform(name, val);
                        }
                        String[] names2 = data.getUniformIntegerNames();
                        for (String name2 : names2) {
                            int[] val2 = data.getUniformInts(name2);
                            shader.setIntUniform(name2, val2);
                        }
                        for (String str : data.getUniformBitmapNames()) {
                            data.getUniformBitmapId(str);
                        }
                        AndroidPaintContext.this.mPaint.setShader(shader);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setImageFilterQuality(int quality) {
                        Utils.log(" quality =" + quality);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setBlendMode(int mode) {
                        AndroidPaintContext.this.mPaint.setBlendMode(
                                AndroidPaintContext.origamiToBlendMode(mode));
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setAlpha(float a) {
                        AndroidPaintContext.this.mPaint.setAlpha((int) (255.0f * a));
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setStrokeMiter(float miter) {
                        AndroidPaintContext.this.mPaint.setStrokeMiter(miter);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setStrokeJoin(int join) {
                        AndroidPaintContext.this.mPaint.setStrokeJoin(Paint.Join.values()[join]);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setFilterBitmap(boolean filter) {
                        AndroidPaintContext.this.mPaint.setFilterBitmap(filter);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setAntiAlias(boolean aa) {
                        AndroidPaintContext.this.mPaint.setAntiAlias(aa);
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void clear(long mask) {}

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setLinearGradient(
                            int[] colors,
                            float[] stops,
                            float startX,
                            float startY,
                            float endX,
                            float endY,
                            int tileMode) {
                        AndroidPaintContext.this.mPaint.setShader(
                                new LinearGradient(
                                        startX,
                                        startY,
                                        endX,
                                        endY,
                                        colors,
                                        stops,
                                        this.mTileModes[tileMode]));
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setRadialGradient(
                            int[] colors,
                            float[] stops,
                            float centerX,
                            float centerY,
                            float radius,
                            int tileMode) {
                        AndroidPaintContext.this.mPaint.setShader(
                                new RadialGradient(
                                        centerX,
                                        centerY,
                                        radius,
                                        colors,
                                        stops,
                                        this.mTileModes[tileMode]));
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setSweepGradient(
                            int[] colors, float[] stops, float centerX, float centerY) {
                        AndroidPaintContext.this.mPaint.setShader(
                                new SweepGradient(centerX, centerY, colors, stops));
                    }

                    @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
                    public void setColorFilter(int color, int mode) {
                        PorterDuff.Mode pmode = AndroidPaintContext.origamiToPorterDuffMode(mode);
                        if (pmode != null) {
                            AndroidPaintContext.this.mPaint.setColorFilter(
                                    new PorterDuffColorFilter(color, pmode));
                        }
                    }
                });
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixScale(float scaleX, float scaleY, float centerX, float centerY) {
        if (Float.isNaN(centerX)) {
            this.mCanvas.scale(scaleX, scaleY);
        } else {
            this.mCanvas.scale(scaleX, scaleY, centerX, centerY);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixTranslate(float translateX, float translateY) {
        this.mCanvas.translate(translateX, translateY);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixSkew(float skewX, float skewY) {
        this.mCanvas.skew(skewX, skewY);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixRotate(float rotate, float pivotX, float pivotY) {
        if (Float.isNaN(pivotX)) {
            this.mCanvas.rotate(rotate);
        } else {
            this.mCanvas.rotate(rotate, pivotX, pivotY);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixSave() {
        this.mCanvas.save();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixRestore() {
        this.mCanvas.restore();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void clipRect(float left, float top, float right, float bottom) {
        this.mCanvas.clipRect(left, top, right, bottom);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void clipPath(int pathId, int regionOp) {
        Path path = getPath(pathId, 0.0f, 1.0f);
        if (regionOp == 1) {
            this.mCanvas.clipOutPath(path);
        } else {
            this.mCanvas.clipPath(path);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void reset() {
        this.mPaint.reset();
    }

    private Path getPath(int path1Id, int path2Id, float tween, float start, float end) {
        if (tween == 0.0f) {
            return getPath(path1Id, start, end);
        }
        if (tween == 1.0f) {
            return getPath(path2Id, start, end);
        }
        AndroidRemoteContext androidContext = (AndroidRemoteContext) this.mContext;
        float[] data1 = (float[]) androidContext.mRemoteComposeState.getFromId(path1Id);
        float[] data2 = (float[]) androidContext.mRemoteComposeState.getFromId(path2Id);
        float[] tmp = new float[data2.length];
        for (int i = 0; i < tmp.length; i++) {
            if (Float.isNaN(data1[i]) || Float.isNaN(data2[i])) {
                tmp[i] = data1[i];
            } else {
                tmp[i] = ((data2[i] - data1[i]) * tween) + data1[i];
            }
        }
        Path path = new Path();
        FloatsToPath.genPath(path, tmp, start, end);
        return path;
    }

    private Path getPath(int id, float start, float end) {
        AndroidRemoteContext androidContext = (AndroidRemoteContext) this.mContext;
        Path path = new Path();
        if (androidContext.mRemoteComposeState.containsId(id)) {
            float[] data = (float[]) androidContext.mRemoteComposeState.getFromId(id);
            FloatsToPath.genPath(path, data, start, end);
        }
        return path;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getText(int id) {
        return (String) this.mContext.mRemoteComposeState.getFromId(id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ShaderData getShaderData(int id) {
        return (ShaderData) this.mContext.mRemoteComposeState.getFromId(id);
    }
}
