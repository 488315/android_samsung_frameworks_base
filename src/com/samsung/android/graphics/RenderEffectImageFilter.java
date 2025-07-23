package com.samsung.android.graphics;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.util.Log;
import android.view.SemBlurInfo;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* loaded from: classes6.dex */
public final class RenderEffectImageFilter {
    private static final String TAG = "RenderEffectImageFilter";
    private final ShaderAssembler assembler;
    private RenderNode renderNode;

    public RenderEffectImageFilter() {
        Log.d(TAG, "Use RenderEffectImageFilter");
        this.assembler = new ShaderAssembler();
        this.renderNode = new RenderNode("RenderEffectImageFilterNode");
    }

    public RenderNode getRenderNode(Bitmap bitmap) {
        if (this.renderNode.hasDisplayList()) {
            this.renderNode.discardDisplayList();
        }
        this.renderNode.setPosition(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.renderNode.beginRecording(bitmap.getWidth(), bitmap.getHeight()).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        this.renderNode.endRecording();
        this.renderNode.setRenderEffect(build());
        return this.renderNode;
    }

    public final void setBlurRadius(float f) {
        this.assembler.setParam(0, clamp(f, 0.0f, 1000.0f));
    }

    public final void setProportionalSaturation(float f) {
        this.assembler.setParam(1, clamp(f, -1.0f, 1.0f));
    }

    public final void setCurveLevel(float f) {
        this.assembler.setParam(2, clamp(f, -100.0f, 100.0f));
    }

    public final void setCurveMaxX(float f) {
        this.assembler.setParam(3, clamp(f, 0.0f, 255.0f));
    }

    public final void setCurveMinX(float f) {
        this.assembler.setParam(4, clamp(f, 0.0f, 255.0f));
    }

    public final void setCurveMaxY(float f) {
        this.assembler.setParam(5, clamp(f, 0.0f, 255.0f));
    }

    public final void setCurveMinY(float f) {
        this.assembler.setParam(6, clamp(f, 0.0f, 255.0f));
    }

    public final void setDither(boolean z) {
        this.assembler.setParam(7, z ? 1.0f : 0.0f);
    }

    public final void setBlurPreset(int i) {
        float[] blurPresetAttrs = SemBlurInfo.Builder.getBlurPresetAttrs(i);
        if (blurPresetAttrs == null) {
            Log.w(TAG, "BlurPresetAttrs was null");
            return;
        }
        if (blurPresetAttrs.length != 7) {
            Log.w(TAG, "BlurPreset size is a mismatch with RenderEffectImageFilter!");
            return;
        }
        setBlurRadius(blurPresetAttrs[0]);
        setProportionalSaturation(blurPresetAttrs[1]);
        setCurveLevel(blurPresetAttrs[2]);
        setCurveMinX(blurPresetAttrs[3]);
        setCurveMaxX(blurPresetAttrs[4]);
        setCurveMinY(blurPresetAttrs[5]);
        setCurveMaxY(blurPresetAttrs[6]);
    }

    public final void setSize(int i, int i2) {
        this.assembler.setSize(i, i2);
    }

    public RenderEffect build() {
        this.assembler.updateParams();
        return this.assembler.getRenderEffect();
    }

    public final void clear() {
        this.assembler.clear();
    }

    public String toString() {
        String shaderCode = this.assembler.getShaderCode();
        Log.d(TAG, shaderCode);
        return shaderCode;
    }

    public final void printParams() {
        this.assembler.printParams();
    }

    private static float clamp(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }
}
