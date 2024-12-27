package com.android.systemui.edgelighting.plus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.android.systemui.edgelighting.plus.PlusEffectInfo;
import com.samsung.android.nexus.base.utils.range.FloatRangeable;
import com.samsung.android.nexus.base.utils.range.IntRangeable;
import com.samsung.android.nexus.base.utils.range.LongRangeable;
import com.samsung.android.nexus.particle.emitter.EmissionRule;
import com.samsung.android.nexus.particle.emitter.Emitter;
import com.samsung.android.nexus.particle.emitter.Emitter$$ExternalSyntheticLambda0;
import com.samsung.android.nexus.particle.emitter.FactorRangeableList;
import com.samsung.android.nexus.particle.emitter.FactorType;
import com.samsung.android.nexus.particle.emitter.ParticleRule;
import com.samsung.android.nexus.particle.emitter.World;
import com.samsung.android.nexus.particle.emitter.layer.EmitterParticleLayer;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;
import com.samsung.android.nexus.particle.emitter.view.ParticleEmitterView;
import java.util.ArrayList;

public final class EdgeLightingPlusEffectView extends ParticleEmitterView {
    public final Emitter emitter;
    public boolean isLoaded;
    public boolean isUsedAI;
    public boolean isUsedAppIcon;
    public boolean isUsedEffectColor;
    public NotificationELPlusEffect$$ExternalSyntheticLambda0 listener;
    public Bitmap mAppIcon;
    public final Context mContext;

    public EdgeLightingPlusEffectView(Context context) {
        super(context);
        this.isLoaded = false;
        this.isUsedAppIcon = false;
        this.isUsedAI = false;
        this.isUsedEffectColor = false;
        this.mContext = context;
        this.emitter = new Emitter(context, new EmissionRule(), new ParticleRule());
        setBackgroundColor(Color.argb(0, 0, 0, 0));
    }

    @Override // com.samsung.android.nexus.particle.emitter.view.ParticleEmitterView, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        Drawable drawable;
        Bitmap bitmap;
        super.onLayout(z, i, i2, i3, i4);
        if (this.isLoaded) {
            return;
        }
        NotificationELPlusEffect notificationELPlusEffect = this.listener.f$0;
        if (notificationELPlusEffect.mIsUsedAppIconForEdgeLightingPlus) {
            EdgeLightingPlusEffectView edgeLightingPlusEffectView = notificationELPlusEffect.mParticleView;
            try {
                drawable = notificationELPlusEffect.getContext().getPackageManager().semGetApplicationIconForIconTray(notificationELPlusEffect.mEdgeEffectInfo.mPackageName, 1);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                drawable = null;
            }
            if (drawable != null) {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } else {
                bitmap = null;
            }
            edgeLightingPlusEffectView.mAppIcon = bitmap;
        }
        EdgeLightingPlusEffectView edgeLightingPlusEffectView2 = notificationELPlusEffect.mParticleView;
        Bundle bundle = notificationELPlusEffect.mEmitterItemInfo;
        edgeLightingPlusEffectView2.getClass();
        PlusEffectInfo plusEffectInfo = new PlusEffectInfo(bundle);
        if (plusEffectInfo.bitmap == null) {
            z2 = true;
        } else {
            edgeLightingPlusEffectView2.isUsedAppIcon = bundle.getBoolean("isUsedAppIcon", false);
            edgeLightingPlusEffectView2.isUsedAI = bundle.getBoolean("isUsedAI");
            edgeLightingPlusEffectView2.isUsedEffectColor = bundle.getBoolean("isUsedEffectColor");
            EmissionRule emissionRule = edgeLightingPlusEffectView2.emitter.mEmissionRule;
            emissionRule.setShapeType();
            int i5 = plusEffectInfo.cellCount;
            IntRangeable intRangeable = emissionRule.emitterCellCount;
            intRangeable.mMin = i5;
            intRangeable.mMax = i5;
            intRangeable.onRangeUpdated();
            if (emissionRule.pointerSize != i5) {
                emissionRule.pathTanXArray = new float[i5];
                emissionRule.pathTanYArray = new float[i5];
                emissionRule.pathPointerOffsetXArray = new float[i5];
                emissionRule.pathPointerOffsetYArray = new float[i5];
                emissionRule.pathPointerVelocitiesX = new float[i5];
                emissionRule.pathPointerVelocitiesY = new float[i5];
                emissionRule.pointerSize = i5;
            }
            int i6 = plusEffectInfo.intervalTime;
            long j = i6 * i6;
            LongRangeable longRangeable = emissionRule.intervalTime;
            longRangeable.mMin = j;
            longRangeable.mMax = j;
            longRangeable.onRangeUpdated();
            emissionRule.intervalFraction = null;
            ParticleRule particleRule = edgeLightingPlusEffectView2.emitter.mParticleRule;
            Bitmap bitmap2 = edgeLightingPlusEffectView2.mAppIcon;
            particleRule.particleTexture = bitmap2 != null ? new BitmapParticleTexture(edgeLightingPlusEffectView2.mContext, new BitmapCache.DrawBitmapLoader(new EdgeLightingPlusEffectView$$ExternalSyntheticLambda0(bitmap2))) : new BitmapParticleTexture(edgeLightingPlusEffectView2.mContext, new BitmapCache.DrawBitmapLoader(new EdgeLightingPlusEffectView$$ExternalSyntheticLambda0(plusEffectInfo.bitmap)));
            int i7 = (edgeLightingPlusEffectView2.isUsedAppIcon || edgeLightingPlusEffectView2.isUsedAI || !edgeLightingPlusEffectView2.isUsedEffectColor) ? 0 : plusEffectInfo.color;
            particleRule.colorMode = 10;
            FactorType factorType = FactorType.COLOR_ALPHA;
            float alpha = Color.alpha(i7);
            FactorRangeableList factorRangeableList = particleRule.factorRangeableList;
            factorRangeableList.setValue(factorType, alpha);
            FactorType factorType2 = FactorType.COLOR_RED;
            factorRangeableList.setValue(factorType2, Color.red(i7));
            FactorType factorType3 = FactorType.COLOR_GREEN;
            factorRangeableList.setValue(factorType3, Color.green(i7));
            FactorType factorType4 = FactorType.COLOR_BLUE;
            factorRangeableList.setValue(factorType4, Color.blue(i7));
            factorRangeableList.setSpeed(factorType);
            factorRangeableList.setSpeed(factorType2);
            factorRangeableList.setSpeed(factorType3);
            factorRangeableList.setSpeed(factorType4);
            factorRangeableList.setAcceleration(factorType);
            factorRangeableList.setAcceleration(factorType2);
            factorRangeableList.setAcceleration(factorType3);
            factorRangeableList.setAcceleration(factorType4);
            float[] fArr = new float[3];
            Color.colorToHSV(i7, fArr);
            FactorType factorType5 = FactorType.COLOR_HUE;
            factorRangeableList.setValue(factorType5, fArr[0]);
            FactorType factorType6 = FactorType.COLOR_SATURATION;
            factorRangeableList.setValue(factorType6, fArr[1]);
            FactorType factorType7 = FactorType.COLOR_VALUE;
            factorRangeableList.setValue(factorType7, fArr[2]);
            factorRangeableList.setSpeed(factorType5);
            factorRangeableList.setSpeed(factorType6);
            factorRangeableList.setSpeed(factorType7);
            factorRangeableList.setAcceleration(factorType5);
            factorRangeableList.setAcceleration(factorType6);
            factorRangeableList.setAcceleration(factorType7);
            factorRangeableList.setValue(FactorType.WIDTH, plusEffectInfo.width);
            factorRangeableList.setValue(FactorType.HEIGHT, edgeLightingPlusEffectView2.mAppIcon != null ? plusEffectInfo.width : plusEffectInfo.height);
            long j2 = plusEffectInfo.lifeTime;
            LongRangeable longRangeable2 = particleRule.lifeTime;
            longRangeable2.mMin = j2;
            longRangeable2.mMax = j2;
            longRangeable2.onRangeUpdated();
            FactorType factorType8 = FactorType.ALPHA;
            FloatRangeable[] floatRangeableArr = {new FloatRangeable(0.0f), new FloatRangeable(0.02f, 0.1f), new FloatRangeable(0.6f, 0.7f), new FloatRangeable(1.0f)};
            FloatRangeable floatRangeable = new FloatRangeable(0.0f);
            float f = plusEffectInfo.alpha;
            particleRule.setKeyFrameListRange(factorType8, floatRangeableArr, new FloatRangeable[]{floatRangeable, new FloatRangeable(0.9f * f, 1.0f * f), new FloatRangeable(0.6f * f, f * 0.7f), new FloatRangeable(0.0f)});
            ParticleRule particleRule2 = edgeLightingPlusEffectView2.emitter.mParticleRule;
            FactorType factorType9 = FactorType.ROTATION;
            PlusEffectInfo.ValueRange valueRange = plusEffectInfo.valueRange;
            particleRule2.setValueRange(factorType9, valueRange.minRotation, valueRange.maxRotation);
            FactorType factorType10 = FactorType.POS;
            PlusEffectInfo.Pos pos = valueRange.minPos;
            float f2 = pos.value;
            PlusEffectInfo.Pos pos2 = valueRange.maxPos;
            particleRule2.setValueRange(factorType10, f2, pos2.value);
            FactorType factorType11 = FactorType.POS_X;
            particleRule2.setValueRange(factorType11, pos.x * edgeLightingPlusEffectView2.getWidth(), pos2.x * edgeLightingPlusEffectView2.getWidth());
            FactorType factorType12 = FactorType.POS_Y;
            particleRule2.setValueRange(factorType12, pos.y * edgeLightingPlusEffectView2.getHeight(), pos2.y * edgeLightingPlusEffectView2.getHeight());
            FactorType factorType13 = FactorType.SCALE;
            PlusEffectInfo.Scale scale = valueRange.minScale;
            float f3 = scale.value;
            PlusEffectInfo.Scale scale2 = valueRange.maxScale;
            particleRule2.setValueRange(factorType13, f3, scale2.value);
            FactorType factorType14 = FactorType.SCALE_X;
            particleRule2.setValueRange(factorType14, scale.x, scale2.x);
            FactorType factorType15 = FactorType.SCALE_Y;
            particleRule2.setValueRange(factorType15, scale.y, scale2.y);
            ParticleRule particleRule3 = edgeLightingPlusEffectView2.emitter.mParticleRule;
            PlusEffectInfo.SpeedRange speedRange = plusEffectInfo.speedRange;
            particleRule3.setSpeedRange(factorType9, speedRange.minRotation, speedRange.maxRotation);
            PlusEffectInfo.Pos pos3 = speedRange.minPos;
            float f4 = pos3.value;
            PlusEffectInfo.Pos pos4 = speedRange.maxPos;
            particleRule3.setSpeedRange(factorType10, f4, pos4.value);
            float f5 = pos3.x;
            float abs = Math.abs(f5) * f5;
            float f6 = pos4.x;
            particleRule3.setSpeedRange(factorType11, abs, f6 * Math.abs(f6));
            float f7 = pos3.y;
            float abs2 = Math.abs(f7) * f7;
            float f8 = pos4.y;
            particleRule3.setSpeedRange(factorType12, abs2, Math.abs(f8) * f8);
            PlusEffectInfo.Scale scale3 = speedRange.minScale;
            float f9 = scale3.value;
            PlusEffectInfo.Scale scale4 = speedRange.maxScale;
            particleRule3.setSpeedRange(factorType13, f9, scale4.value);
            particleRule3.setSpeedRange(factorType14, scale3.x, scale4.x);
            particleRule3.setSpeedRange(factorType15, scale3.y, scale4.y);
            ParticleRule particleRule4 = edgeLightingPlusEffectView2.emitter.mParticleRule;
            PlusEffectInfo.AccelerationRange accelerationRange = plusEffectInfo.accelerationRange;
            particleRule4.setAccelerationRange(factorType9, accelerationRange.minRotation, accelerationRange.maxRotation);
            PlusEffectInfo.Pos pos5 = accelerationRange.minPos;
            float f10 = pos5.value;
            PlusEffectInfo.Pos pos6 = accelerationRange.maxPos;
            particleRule4.setAccelerationRange(factorType10, f10, pos6.value);
            particleRule4.setAccelerationRange(factorType11, pos5.x, pos6.x);
            particleRule4.setAccelerationRange(factorType12, pos5.y, pos6.y);
            PlusEffectInfo.Scale scale5 = accelerationRange.minScale;
            float f11 = scale5.value;
            PlusEffectInfo.Scale scale6 = accelerationRange.maxScale;
            particleRule4.setAccelerationRange(factorType13, f11, scale6.value);
            particleRule4.setAccelerationRange(factorType14, scale5.x, scale6.x);
            particleRule4.setAccelerationRange(factorType15, scale5.y, scale6.y);
            Emitter emitter = edgeLightingPlusEffectView2.emitter;
            EmitterParticleLayer emitterParticleLayer = edgeLightingPlusEffectView2.mEmitterParticleLayer;
            if (emitterParticleLayer == null) {
                throw new IllegalStateException("layer not initiated");
            }
            Emitter emitter2 = emitterParticleLayer.mWorld.mRootEmitter;
            emitter2.getClass();
            if (emitter == null) {
                throw new IllegalArgumentException("null emitter");
            }
            if (emitter.isSubEmitter) {
                throw new IllegalArgumentException("don't reuse emitter");
            }
            emitter.isSubEmitter = true;
            World world = emitter2.mWorld;
            emitter.mWorld = world;
            emitter.mEmitters.forEach(new Emitter$$ExternalSyntheticLambda0(world));
            emitter2.mEmitters.add(emitter);
            ArrayList arrayList = emitter2.mEmitters;
            int size = arrayList.size();
            StringBuilder sb = new StringBuilder();
            for (int i8 = size - 1; i8 >= 0; i8--) {
                sb.append(((Emitter) arrayList.get(i8)).hashCode());
            }
            emitter2.subEmitterKey = sb.toString();
            z2 = true;
        }
        this.isLoaded = z2;
    }
}
