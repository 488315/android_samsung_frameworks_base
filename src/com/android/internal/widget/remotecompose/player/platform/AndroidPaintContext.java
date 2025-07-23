package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.Platform;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes6.dex */
public class AndroidPaintContext extends PaintContext {
    private Paint.FontMetrics mCachedFontMetrics;
    PaintChanges mCachedPaintChanges;
    Canvas mCanvas;
    RenderNode mNode;
    Paint mPaint;
    List<Paint> mPaintList;
    Canvas mPreviousCanvas;
    Rect mTmpRect;

    public AndroidPaintContext(RemoteContext remoteContext, Canvas canvas) {
        super(remoteContext);
        this.mPaint = new Paint();
        this.mPaintList = new ArrayList();
        this.mTmpRect = new Rect();
        this.mNode = null;
        this.mPreviousCanvas = null;
        this.mCachedPaintChanges = new PaintChanges() { // from class: com.android.internal.widget.remotecompose.player.platform.AndroidPaintContext.1
            Shader.TileMode[] mTileModes = {Shader.TileMode.CLAMP, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR};

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setTextSize(float f) {
                AndroidPaintContext.this.mPaint.setTextSize(f);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setTypeFace(int i, int i2, boolean z) {
                if (i == 0) {
                    if (i2 == 400 && !z) {
                        AndroidPaintContext.this.mPaint.setTypeface(Typeface.DEFAULT);
                        return;
                    } else {
                        AndroidPaintContext.this.mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, i2, z));
                        return;
                    }
                }
                if (i == 1) {
                    if (i2 == 400 && !z) {
                        AndroidPaintContext.this.mPaint.setTypeface(Typeface.SANS_SERIF);
                        return;
                    } else {
                        AndroidPaintContext.this.mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, i2, z));
                        return;
                    }
                }
                if (i == 2) {
                    if (i2 == 400 && !z) {
                        AndroidPaintContext.this.mPaint.setTypeface(Typeface.SERIF);
                        return;
                    } else {
                        AndroidPaintContext.this.mPaint.setTypeface(Typeface.create(Typeface.SERIF, i2, z));
                        return;
                    }
                }
                if (i != 3) {
                    return;
                }
                if (i2 == 400 && !z) {
                    AndroidPaintContext.this.mPaint.setTypeface(Typeface.MONOSPACE);
                } else {
                    AndroidPaintContext.this.mPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, i2, z));
                }
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setStrokeWidth(float f) {
                AndroidPaintContext.this.mPaint.setStrokeWidth(f);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setColor(int i) {
                AndroidPaintContext.this.mPaint.setColor(i);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setStrokeCap(int i) {
                AndroidPaintContext.this.mPaint.setStrokeCap(Paint.Cap.values()[i]);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setStyle(int i) {
                AndroidPaintContext.this.mPaint.setStyle(Paint.Style.values()[i]);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setShader(int i) {
                if (i == 0) {
                    AndroidPaintContext.this.mPaint.setShader(null);
                    return;
                }
                ShaderData shaderData = AndroidPaintContext.this.getShaderData(i);
                if (shaderData == null) {
                    return;
                }
                RuntimeShader runtimeShader = new RuntimeShader(AndroidPaintContext.this.getText(shaderData.getShaderTextId()));
                for (String str : shaderData.getUniformFloatNames()) {
                    runtimeShader.setFloatUniform(str, shaderData.getUniformFloats(str));
                }
                for (String str2 : shaderData.getUniformIntegerNames()) {
                    runtimeShader.setIntUniform(str2, shaderData.getUniformInts(str2));
                }
                for (String str3 : shaderData.getUniformBitmapNames()) {
                    runtimeShader.setInputShader(str3, new BitmapShader((Bitmap) ((AndroidRemoteContext) AndroidPaintContext.this.mContext).mRemoteComposeState.getFromId(shaderData.getUniformBitmapId(str3)), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                }
                AndroidPaintContext.this.mPaint.setShader(runtimeShader);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setImageFilterQuality(int i) {
                Utils.log(" quality =" + i);
                AndroidPaintContext.this.mPaint.setFilterBitmap(i == 1);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setBlendMode(int i) {
                AndroidPaintContext.this.mPaint.setBlendMode(AndroidPaintContext.origamiToBlendMode(i));
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setAlpha(float f) {
                AndroidPaintContext.this.mPaint.setAlpha((int) (f * 255.0f));
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setStrokeMiter(float f) {
                AndroidPaintContext.this.mPaint.setStrokeMiter(f);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setStrokeJoin(int i) {
                AndroidPaintContext.this.mPaint.setStrokeJoin(Paint.Join.values()[i]);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setFilterBitmap(boolean z) {
                AndroidPaintContext.this.mPaint.setFilterBitmap(z);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setAntiAlias(boolean z) {
                AndroidPaintContext.this.mPaint.setAntiAlias(z);
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void clear(long j) {
                if ((j & 8192) != 0) {
                    AndroidPaintContext.this.mPaint.setColorFilter(null);
                }
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setLinearGradient(int[] iArr, float[] fArr, float f, float f2, float f3, float f4, int i) {
                AndroidPaintContext.this.mPaint.setShader(new LinearGradient(f, f2, f3, f4, iArr, fArr, this.mTileModes[i]));
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setRadialGradient(int[] iArr, float[] fArr, float f, float f2, float f3, int i) {
                AndroidPaintContext.this.mPaint.setShader(new RadialGradient(f, f2, f3, iArr, fArr, this.mTileModes[i]));
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setSweepGradient(int[] iArr, float[] fArr, float f, float f2) {
                AndroidPaintContext.this.mPaint.setShader(new SweepGradient(f, f2, iArr, fArr));
            }

            @Override // com.android.internal.widget.remotecompose.core.operations.paint.PaintChanges
            public void setColorFilter(int i, int i2) {
                PorterDuff.Mode origamiToPorterDuffMode = AndroidPaintContext.origamiToPorterDuffMode(i2);
                if (origamiToPorterDuffMode != null) {
                    AndroidPaintContext.this.mPaint.setColorFilter(new PorterDuffColorFilter(i, origamiToPorterDuffMode));
                }
            }
        };
        this.mCanvas = canvas;
    }

    public Canvas getCanvas() {
        return this.mCanvas;
    }

    public void setCanvas(Canvas canvas) {
        this.mCanvas = canvas;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void save() {
        this.mCanvas.save();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void saveLayer(float f, float f2, float f3, float f4) {
        this.mCanvas.saveLayer(f, f2, f + f3, f2 + f4, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void restore() {
        this.mCanvas.restore();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawBitmap(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        AndroidRemoteContext androidRemoteContext = (AndroidRemoteContext) this.mContext;
        if (androidRemoteContext.mRemoteComposeState.containsId(i)) {
            this.mCanvas.drawBitmap((Bitmap) androidRemoteContext.mRemoteComposeState.getFromId(i), new Rect(i2, i3, i4, i5), new Rect(i6, i7, i8, i9), this.mPaint);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void scale(float f, float f2) {
        this.mCanvas.scale(f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void startGraphicsLayer(int i, int i2) {
        RenderNode renderNode = new RenderNode("layer");
        this.mNode = renderNode;
        renderNode.setPosition(0, 0, i, i2);
        this.mPreviousCanvas = this.mCanvas;
        this.mCanvas = this.mNode.beginRecording();
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void setGraphicsLayer(HashMap<Integer, Object> hashMap) {
        if (this.mNode == null) {
            return;
        }
        boolean z = false;
        boolean z2 = false;
        for (Integer num : hashMap.keySet()) {
            Object obj = hashMap.get(num);
            switch (num.intValue()) {
                case 0:
                    this.mNode.setScaleX(((Float) obj).floatValue());
                    break;
                case 1:
                    this.mNode.setScaleY(((Float) obj).floatValue());
                    break;
                case 2:
                    this.mNode.setRotationX(((Float) obj).floatValue());
                    break;
                case 3:
                    this.mNode.setRotationY(((Float) obj).floatValue());
                    break;
                case 4:
                    this.mNode.setRotationZ(((Float) obj).floatValue());
                    break;
                case 5:
                    this.mNode.setPivotX(((Float) obj).floatValue() * this.mNode.getWidth());
                    break;
                case 6:
                    this.mNode.setPivotY(((Float) obj).floatValue() * this.mNode.getWidth());
                    break;
                case 7:
                    this.mNode.setTranslationX(((Float) obj).floatValue());
                    break;
                case 8:
                    this.mNode.setTranslationY(((Float) obj).floatValue());
                    break;
                case 9:
                    this.mNode.setTranslationZ(((Float) obj).floatValue());
                    break;
                case 10:
                    this.mNode.setElevation(((Float) obj).floatValue());
                    break;
                case 11:
                    this.mNode.setAlpha(((Float) obj).floatValue());
                    break;
                case 12:
                    this.mNode.setCameraDistance(((Float) obj).floatValue());
                    break;
                case 14:
                    this.mNode.setSpotShadowColor(((Integer) obj).intValue());
                    break;
                case 15:
                    this.mNode.setAmbientShadowColor(((Integer) obj).intValue());
                    break;
                case 16:
                    if (((Integer) obj).intValue() != 0) {
                        z2 = true;
                        break;
                    } else {
                        z2 = false;
                        break;
                    }
                case 20:
                    z = true;
                    break;
            }
        }
        if (z) {
            Outline outline = new Outline();
            outline.setAlpha(1.0f);
            Object obj2 = hashMap.get(20);
            if (obj2 != null) {
                Object obj3 = hashMap.get(21);
                int intValue = ((Integer) obj2).intValue();
                if (intValue == 0) {
                    outline.setRect(0, 0, this.mNode.getWidth(), this.mNode.getHeight());
                } else if (intValue == 1) {
                    if (obj3 != null) {
                        outline.setRoundRect(new Rect(0, 0, this.mNode.getWidth(), this.mNode.getHeight()), ((Float) obj3).floatValue());
                    } else {
                        outline.setRect(0, 0, this.mNode.getWidth(), this.mNode.getHeight());
                    }
                } else if (intValue == 2) {
                    outline.setRoundRect(new Rect(0, 0, this.mNode.getWidth(), this.mNode.getHeight()), Math.min(this.mNode.getWidth(), this.mNode.getHeight()) / 2.0f);
                }
            }
            this.mNode.setOutline(outline);
        }
        if (z2) {
            Object obj4 = hashMap.get(17);
            float floatValue = obj4 != null ? ((Float) obj4).floatValue() : 0.0f;
            Object obj5 = hashMap.get(18);
            float floatValue2 = obj5 != null ? ((Float) obj5).floatValue() : 0.0f;
            Object obj6 = hashMap.get(19);
            int intValue2 = obj6 != null ? ((Integer) obj6).intValue() : 0;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            if (intValue2 == 0) {
                tileMode = Shader.TileMode.CLAMP;
            } else if (intValue2 == 1) {
                tileMode = Shader.TileMode.REPEAT;
            } else if (intValue2 == 2) {
                tileMode = Shader.TileMode.MIRROR;
            } else if (intValue2 == 3) {
                tileMode = Shader.TileMode.DECAL;
            }
            this.mNode.setRenderEffect(RenderEffect.createBlurEffect(floatValue, floatValue2, tileMode));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void endGraphicsLayer() {
        this.mNode.endRecording();
        Canvas canvas = this.mPreviousCanvas;
        this.mCanvas = canvas;
        if (canvas.isHardwareAccelerated()) {
            this.mCanvas.enableZ();
            this.mCanvas.drawRenderNode(this.mNode);
            this.mCanvas.disableZ();
        }
        this.mNode = null;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void translate(float f, float f2) {
        this.mCanvas.translate(f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawArc(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mCanvas.drawArc(f, f2, f3, f4, f5, f6, false, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawSector(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mCanvas.drawArc(f, f2, f3, f4, f5, f6, true, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawBitmap(int i, float f, float f2, float f3, float f4) {
        AndroidRemoteContext androidRemoteContext = (AndroidRemoteContext) this.mContext;
        if (androidRemoteContext.mRemoteComposeState.containsId(i)) {
            Bitmap bitmap = (Bitmap) androidRemoteContext.mRemoteComposeState.getFromId(i);
            this.mCanvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(f, f2, f3, f4), this.mPaint);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawCircle(float f, float f2, float f3) {
        this.mCanvas.drawCircle(f, f2, f3, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawLine(float f, float f2, float f3, float f4) {
        this.mCanvas.drawLine(f, f2, f3, f4, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawOval(float f, float f2, float f3, float f4) {
        this.mCanvas.drawOval(f, f2, f3, f4, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawPath(int i, float f, float f2) {
        this.mCanvas.drawPath(getPath(i, f, f2), this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawRect(float f, float f2, float f3, float f4) {
        this.mCanvas.drawRect(f, f2, f3, f4, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void savePaint() {
        this.mPaintList.add(new Paint(this.mPaint));
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void restorePaint() {
        this.mPaint = this.mPaintList.remove(r0.size() - 1);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void replacePaint(PaintBundle paintBundle) {
        this.mPaint.reset();
        applyPaint(paintBundle);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawRoundRect(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mCanvas.drawRoundRect(f, f2, f3, f4, f5, f6, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawTextOnPath(int i, int i2, float f, float f2) {
        this.mCanvas.drawTextOnPath(getText(i), getPath(i2, 0.0f, 1.0f), f, f2, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void getTextBounds(int i, int i2, int i3, int i4, float[] fArr) {
        String text = getText(i);
        if (i3 == -1 || i3 > text.length()) {
            i3 = text.length();
        }
        if (this.mCachedFontMetrics == null) {
            this.mCachedFontMetrics = this.mPaint.getFontMetrics();
        }
        this.mPaint.getFontMetrics(this.mCachedFontMetrics);
        this.mPaint.getTextBounds(text, i2, i3, this.mTmpRect);
        if ((i4 & 4) != 0) {
            fArr[0] = 0.0f;
            fArr[2] = this.mPaint.measureText(text, i2, i3);
        } else {
            fArr[0] = this.mTmpRect.left;
            if ((i4 & 1) != 0) {
                fArr[2] = this.mPaint.measureText(text, i2, i3) - this.mTmpRect.left;
            } else {
                fArr[2] = this.mTmpRect.right;
            }
        }
        if ((i4 & 2) != 0) {
            fArr[1] = Math.round(this.mCachedFontMetrics.ascent);
            fArr[3] = Math.round(this.mCachedFontMetrics.descent);
        } else {
            fArr[1] = this.mTmpRect.top;
            fArr[3] = this.mTmpRect.bottom;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0053  */
    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.internal.widget.remotecompose.core.Platform.ComputedTextLayout layoutComplexText(int r1, int r2, int r3, int r4, int r5, int r6, float r7, int r8) {
        /*
            r0 = this;
            java.lang.String r1 = r0.getText(r1)
            if (r1 != 0) goto L8
            r0 = 0
            return r0
        L8:
            r8 = -1
            if (r3 == r8) goto L11
            int r8 = r1.length()
            if (r3 <= r8) goto L15
        L11:
            int r3 = r1.length()
        L15:
            android.text.TextPaint r8 = new android.text.TextPaint
            r8.<init>()
            android.graphics.Paint r0 = r0.mPaint
            r8.set(r0)
            int r0 = (int) r7
            android.text.StaticLayout$Builder r0 = android.text.StaticLayout.Builder.obtain(r1, r2, r3, r8, r0)
            r1 = 2
            r2 = 3
            if (r4 == r1) goto L39
            if (r4 == r2) goto L33
            r1 = 6
            if (r4 == r1) goto L39
            android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_NORMAL
            r0.setAlignment(r1)
            goto L3e
        L33:
            android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_CENTER
            r0.setAlignment(r1)
            goto L3e
        L39:
            android.text.Layout$Alignment r1 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            r0.setAlignment(r1)
        L3e:
            if (r5 == r2) goto L53
            r1 = 4
            if (r5 == r1) goto L4d
            r1 = 5
            if (r5 == r1) goto L47
            goto L58
        L47:
            android.text.TextUtils$TruncateAt r1 = android.text.TextUtils.TruncateAt.MIDDLE
            r0.setEllipsize(r1)
            goto L58
        L4d:
            android.text.TextUtils$TruncateAt r1 = android.text.TextUtils.TruncateAt.START
            r0.setEllipsize(r1)
            goto L58
        L53:
            android.text.TextUtils$TruncateAt r1 = android.text.TextUtils.TruncateAt.END
            r0.setEllipsize(r1)
        L58:
            r0.setMaxLines(r6)
            r1 = 0
            r0.setIncludePad(r1)
            android.text.StaticLayout r0 = r0.build()
            com.android.internal.widget.remotecompose.player.platform.AndroidComputedTextLayout r1 = new com.android.internal.widget.remotecompose.player.platform.AndroidComputedTextLayout
            int r2 = r0.getWidth()
            float r2 = (float) r2
            int r3 = r0.getHeight()
            float r3 = (float) r3
            r1.<init>(r0, r2, r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.remotecompose.player.platform.AndroidPaintContext.layoutComplexText(int, int, int, int, int, int, float, int):com.android.internal.widget.remotecompose.core.Platform$ComputedTextLayout");
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawTextRun(int i, int i2, int i3, int i4, int i5, float f, float f2, boolean z) {
        String text = getText(i);
        if (text == null) {
            return;
        }
        if (i3 == -1) {
            if (i2 != 0) {
                text = text.substring(i2);
            }
        } else if (i3 > text.length()) {
            text = text.substring(i2);
        } else {
            text = text.substring(i2, i3);
        }
        this.mCanvas.drawText(text, f, f2, this.mPaint);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawComplexText(Platform.ComputedTextLayout computedTextLayout) {
        if (computedTextLayout == null) {
            return;
        }
        ((AndroidComputedTextLayout) computedTextLayout).get().draw(this.mCanvas);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawTweenPath(int i, int i2, float f, float f2, float f3) {
        this.mCanvas.drawPath(getPath(i, i2, f, f2, f3), this.mPaint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PorterDuff.Mode origamiToPorterDuffMode(int i) {
        if (i == 24) {
            return PorterDuff.Mode.MULTIPLY;
        }
        if (i != 30) {
            switch (i) {
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
                default:
                    switch (i) {
                        case 14:
                            return PorterDuff.Mode.SCREEN;
                        case 15:
                            return PorterDuff.Mode.OVERLAY;
                        case 16:
                            return PorterDuff.Mode.DARKEN;
                        case 17:
                            return PorterDuff.Mode.LIGHTEN;
                        default:
                            return PorterDuff.Mode.SRC_OVER;
                    }
            }
        }
        return PorterDuff.Mode.ADD;
    }

    public static BlendMode origamiToBlendMode(int i) {
        switch (i) {
            case 0:
                return BlendMode.CLEAR;
            case 1:
                return BlendMode.SRC;
            case 2:
                return BlendMode.DST;
            case 3:
                return BlendMode.SRC_OVER;
            case 4:
                return BlendMode.DST_OVER;
            case 5:
                return BlendMode.SRC_IN;
            case 6:
                return BlendMode.DST_IN;
            case 7:
                return BlendMode.SRC_OUT;
            case 8:
                return BlendMode.DST_OUT;
            case 9:
                return BlendMode.SRC_ATOP;
            case 10:
                return BlendMode.DST_ATOP;
            case 11:
                return BlendMode.XOR;
            case 12:
                return BlendMode.PLUS;
            case 13:
                return BlendMode.MODULATE;
            case 14:
                return BlendMode.SCREEN;
            case 15:
                return BlendMode.OVERLAY;
            case 16:
                return BlendMode.DARKEN;
            case 17:
                return BlendMode.LIGHTEN;
            case 18:
                return BlendMode.COLOR_DODGE;
            case 19:
                return BlendMode.COLOR_BURN;
            case 20:
                return BlendMode.HARD_LIGHT;
            case 21:
                return BlendMode.SOFT_LIGHT;
            case 22:
                return BlendMode.DIFFERENCE;
            case 23:
                return BlendMode.EXCLUSION;
            case 24:
                return BlendMode.MULTIPLY;
            case 25:
                return BlendMode.HUE;
            case 26:
                return BlendMode.SATURATION;
            case 27:
                return BlendMode.COLOR;
            case 28:
                return BlendMode.LUMINOSITY;
            default:
                return null;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void applyPaint(PaintBundle paintBundle) {
        paintBundle.applyPaintChange(this, this.mCachedPaintChanges);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixScale(float f, float f2, float f3, float f4) {
        if (Float.isNaN(f3)) {
            this.mCanvas.scale(f, f2);
        } else {
            this.mCanvas.scale(f, f2, f3, f4);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixTranslate(float f, float f2) {
        this.mCanvas.translate(f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixSkew(float f, float f2) {
        this.mCanvas.skew(f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void matrixRotate(float f, float f2, float f3) {
        if (Float.isNaN(f2)) {
            this.mCanvas.rotate(f);
        } else {
            this.mCanvas.rotate(f, f2, f3);
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
    public void clipRect(float f, float f2, float f3, float f4) {
        this.mCanvas.clipRect(f, f2, f3, f4);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void roundedClipRect(float f, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        path.addRoundRect(0.0f, 0.0f, f, f2, new float[]{f3, f3, f4, f4, f6, f6, f5, f5}, Path.Direction.CW);
        this.mCanvas.clipPath(path);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void clipPath(int i, int i2) {
        Path path = getPath(i, 0.0f, 1.0f);
        if (i2 == 1) {
            this.mCanvas.clipOutPath(path);
        } else {
            this.mCanvas.clipPath(path);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void tweenPath(int i, int i2, int i3, float f) {
        ((AndroidRemoteContext) this.mContext).mRemoteComposeState.putPathData(i, getPathArray(i2, i3, f));
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void combinePath(int i, int i2, int i3, byte b) {
        Path path = getPath(i2, 0.0f, 1.0f);
        Path path2 = getPath(i3, 0.0f, 1.0f);
        Path.Op[] opArr = {Path.Op.DIFFERENCE, Path.Op.INTERSECT, Path.Op.REVERSE_DIFFERENCE, Path.Op.UNION, Path.Op.XOR};
        Path path3 = new Path(path);
        path3.op(path2, opArr[b]);
        ((AndroidRemoteContext) this.mContext).mRemoteComposeState.putPath(i, path3);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void reset() {
        this.mPaint.reset();
    }

    private Path getPath(int i, int i2, float f, float f2, float f3) {
        return getPath(getPathArray(i, i2, f), f2, f3);
    }

    private float[] getPathArray(int i, int i2, float f) {
        AndroidRemoteContext androidRemoteContext = (AndroidRemoteContext) this.mContext;
        if (f == 0.0f) {
            return androidRemoteContext.mRemoteComposeState.getPathData(i);
        }
        if (f == 1.0f) {
            return androidRemoteContext.mRemoteComposeState.getPathData(i2);
        }
        float[] pathData = androidRemoteContext.mRemoteComposeState.getPathData(i);
        float[] pathData2 = androidRemoteContext.mRemoteComposeState.getPathData(i2);
        int length = pathData2.length;
        float[] fArr = new float[length];
        for (int i3 = 0; i3 < length; i3++) {
            if (Float.isNaN(pathData[i3]) || Float.isNaN(pathData2[i3])) {
                fArr[i3] = pathData[i3];
            } else {
                float f2 = pathData2[i3];
                float f3 = pathData[i3];
                fArr[i3] = ((f2 - f3) * f) + f3;
            }
        }
        return fArr;
    }

    private Path getPath(float[] fArr, float f, float f2) {
        Path path = new Path();
        FloatsToPath.genPath(path, fArr, f, f2);
        return path;
    }

    private Path getPath(int i, float f, float f2) {
        AndroidRemoteContext androidRemoteContext = (AndroidRemoteContext) this.mContext;
        Path path = (Path) androidRemoteContext.mRemoteComposeState.getPath(i);
        if (path != null) {
            return path;
        }
        Path path2 = new Path();
        float[] pathData = androidRemoteContext.mRemoteComposeState.getPathData(i);
        if (pathData != null) {
            FloatsToPath.genPath(path2, pathData, f, f2);
            androidRemoteContext.mRemoteComposeState.putPath(i, path2);
        }
        return path2;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public String getText(int i) {
        return (String) this.mContext.mRemoteComposeState.getFromId(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ShaderData getShaderData(int i) {
        return (ShaderData) this.mContext.mRemoteComposeState.getFromId(i);
    }
}
