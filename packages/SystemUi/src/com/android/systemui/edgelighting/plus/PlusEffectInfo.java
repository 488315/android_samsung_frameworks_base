package com.android.systemui.edgelighting.plus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PlusEffectInfo {
    public final AccelerationRange accelerationRange;
    public final float alpha;
    public final Bitmap bitmap;
    public final int cellCount;
    public final int color;
    public final float height;
    public final int intervalTime;
    public final int lifeTime;
    public final SpeedRange speedRange;
    public final ValueRange valueRange;
    public final float width;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AccelerationRange {
        public final Pos maxPos;
        public final float maxRotation;
        public final Scale maxScale;
        public final Pos minPos;
        public final float minRotation;
        public final Scale minScale;

        public AccelerationRange(PlusEffectInfo plusEffectInfo, Bundle bundle) {
            this.minRotation = bundle.getFloat("minRotationAcceleration");
            this.maxRotation = bundle.getFloat("maxRotationAcceleration");
            this.minScale = new Scale(plusEffectInfo, bundle.getFloat("minScaleAcceleration"), bundle.getFloat("minScaleXAcceleration"), bundle.getFloat("minScaleYAcceleration"));
            this.maxScale = new Scale(plusEffectInfo, bundle.getFloat("maxScaleAcceleration"), bundle.getFloat("maxScaleXAcceleration"), bundle.getFloat("maxScaleYAcceleration"));
            this.minPos = new Pos(plusEffectInfo, bundle.getFloat("minPosAcceleration"), bundle.getFloat("minPosXAcceleration"), bundle.getFloat("minPosYAcceleration"));
            this.maxPos = new Pos(plusEffectInfo, bundle.getFloat("maxPosAcceleration"), bundle.getFloat("maxPosXAcceleration"), bundle.getFloat("maxPosYAcceleration"));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Pos {
        public final float value;

        /* renamed from: x */
        public final float f283x;

        /* renamed from: y */
        public final float f284y;

        public Pos(PlusEffectInfo plusEffectInfo, float f, float f2, float f3) {
            this.value = f;
            this.f283x = f2;
            this.f284y = f3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Scale {
        public final float value;

        /* renamed from: x */
        public final float f285x;

        /* renamed from: y */
        public final float f286y;

        public Scale(PlusEffectInfo plusEffectInfo, float f, float f2, float f3) {
            this.value = f;
            this.f285x = f2;
            this.f286y = f3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SpeedRange {
        public final Pos maxPos;
        public final float maxRotation;
        public final Scale maxScale;
        public final Pos minPos;
        public final float minRotation;
        public final Scale minScale;

        public SpeedRange(PlusEffectInfo plusEffectInfo, Bundle bundle) {
            this.minRotation = bundle.getFloat("minRotationSpeed");
            this.maxRotation = bundle.getFloat("maxRotationSpeed");
            this.minScale = new Scale(plusEffectInfo, bundle.getFloat("minScaleSpeed"), bundle.getFloat("minScaleXSpeed"), bundle.getFloat("minScaleYSpeed"));
            this.maxScale = new Scale(plusEffectInfo, bundle.getFloat("maxScaleSpeed"), bundle.getFloat("maxScaleXSpeed"), bundle.getFloat("maxScaleYSpeed"));
            this.minPos = new Pos(plusEffectInfo, bundle.getFloat("minPosSpeed"), bundle.getFloat("minPosXSpeed"), bundle.getFloat("minPosYSpeed"));
            this.maxPos = new Pos(plusEffectInfo, bundle.getFloat("maxPosSpeed"), bundle.getFloat("maxPosXSpeed"), bundle.getFloat("maxPosYSpeed"));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ValueRange {
        public final Pos maxPos;
        public final float maxRotation;
        public final Scale maxScale;
        public final Pos minPos;
        public final float minRotation;
        public final Scale minScale;

        public ValueRange(PlusEffectInfo plusEffectInfo, Bundle bundle) {
            this.minRotation = bundle.getFloat("minRotationValue");
            this.maxRotation = bundle.getFloat("maxRotationValue");
            this.minScale = new Scale(plusEffectInfo, bundle.getFloat("minScaleValue"), bundle.getFloat("minScaleXValue"), bundle.getFloat("minScaleYValue"));
            this.maxScale = new Scale(plusEffectInfo, bundle.getFloat("maxScaleValue"), bundle.getFloat("maxScaleXValue"), bundle.getFloat("maxScaleYValue"));
            this.minPos = new Pos(plusEffectInfo, bundle.getFloat("minPosValue"), bundle.getFloat("minPosXValue"), bundle.getFloat("minPosYValue"));
            this.maxPos = new Pos(plusEffectInfo, bundle.getFloat("maxPosValue"), bundle.getFloat("maxPosXValue"), bundle.getFloat("maxPosYValue"));
        }
    }

    public PlusEffectInfo(Bundle bundle) {
        byte[] byteArray = bundle.getByteArray("bitmapByteArray");
        if (byteArray != null) {
            this.bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        this.width = bundle.getFloat("width");
        this.height = bundle.getFloat("height");
        bundle.getInt("shapeType");
        this.cellCount = bundle.getInt("cellCount");
        this.intervalTime = bundle.getInt("intervalTime");
        this.lifeTime = bundle.getInt("lifeTime");
        this.color = bundle.getInt("color");
        this.alpha = bundle.getFloat("alpha");
        this.valueRange = new ValueRange(this, bundle);
        this.speedRange = new SpeedRange(this, bundle);
        this.accelerationRange = new AccelerationRange(this, bundle);
    }
}
