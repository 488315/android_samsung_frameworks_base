package com.android.systemui.edgelighting.plus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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

    public final class Pos {
        public final float value;
        public final float x;
        public final float y;

        public Pos(PlusEffectInfo plusEffectInfo, float f, float f2, float f3) {
            this.value = f;
            this.x = f2;
            this.y = f3;
        }
    }

    public final class Scale {
        public final float value;
        public final float x;
        public final float y;

        public Scale(PlusEffectInfo plusEffectInfo, float f, float f2, float f3) {
            this.value = f;
            this.x = f2;
            this.y = f3;
        }
    }

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
