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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        Bitmap bitmap2 = plusEffectInfo.bitmap;
        if (bitmap2 == null) {
            z2 = true;
        } else {
            edgeLightingPlusEffectView2.isUsedAppIcon = bundle.getBoolean("isUsedAppIcon", false);
            edgeLightingPlusEffectView2.isUsedAI = bundle.getBoolean("isUsedAI");
            edgeLightingPlusEffectView2.isUsedEffectColor = bundle.getBoolean("isUsedEffectColor");
            EmissionRule emissionRule = edgeLightingPlusEffectView2.emitter.mEmissionRule;
            emissionRule.setShapeType();
            IntRangeable intRangeable = emissionRule.emitterCellCount;
            int i5 = plusEffectInfo.cellCount;
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
            Bitmap bitmap3 = edgeLightingPlusEffectView2.mAppIcon;
            if (bitmap3 != null) {
                bitmap2 = bitmap3;
            }
            particleRule.particleTexture = new BitmapParticleTexture(edgeLightingPlusEffectView2.mContext, new BitmapCache.DrawBitmapLoader(new EdgeLightingPlusEffectView$$ExternalSyntheticLambda0(bitmap2)));
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
            FactorType factorType8 = FactorType.WIDTH;
            float f = plusEffectInfo.width;
            factorRangeableList.setValue(factorType8, f);
            FactorType factorType9 = FactorType.HEIGHT;
            if (edgeLightingPlusEffectView2.mAppIcon == null) {
                f = plusEffectInfo.height;
            }
            factorRangeableList.setValue(factorType9, f);
            long j2 = plusEffectInfo.lifeTime;
            LongRangeable longRangeable2 = particleRule.lifeTime;
            longRangeable2.mMin = j2;
            longRangeable2.mMax = j2;
            longRangeable2.onRangeUpdated();
            FactorType factorType10 = FactorType.ALPHA;
            FloatRangeable[] floatRangeableArr = {new FloatRangeable(0.0f), new FloatRangeable(0.02f, 0.1f), new FloatRangeable(0.6f, 0.7f), new FloatRangeable(1.0f)};
            FloatRangeable floatRangeable = new FloatRangeable(0.0f);
            float f2 = plusEffectInfo.alpha;
            particleRule.setKeyFrameListRange(factorType10, floatRangeableArr, new FloatRangeable[]{floatRangeable, new FloatRangeable(0.9f * f2, 1.0f * f2), new FloatRangeable(0.6f * f2, f2 * 0.7f), new FloatRangeable(0.0f)});
            ParticleRule particleRule2 = edgeLightingPlusEffectView2.emitter.mParticleRule;
            FactorType factorType11 = FactorType.ROTATION;
            PlusEffectInfo.ValueRange valueRange = plusEffectInfo.valueRange;
            particleRule2.setValueRange(factorType11, valueRange.minRotation, valueRange.maxRotation);
            FactorType factorType12 = FactorType.POS;
            PlusEffectInfo.Pos pos = valueRange.minPos;
            float f3 = pos.value;
            PlusEffectInfo.Pos pos2 = valueRange.maxPos;
            particleRule2.setValueRange(factorType12, f3, pos2.value);
            FactorType factorType13 = FactorType.POS_X;
            particleRule2.setValueRange(factorType13, pos.f283x * edgeLightingPlusEffectView2.getWidth(), pos2.f283x * edgeLightingPlusEffectView2.getWidth());
            FactorType factorType14 = FactorType.POS_Y;
            particleRule2.setValueRange(factorType14, pos.f284y * edgeLightingPlusEffectView2.getHeight(), pos2.f284y * edgeLightingPlusEffectView2.getHeight());
            FactorType factorType15 = FactorType.SCALE;
            PlusEffectInfo.Scale scale = valueRange.minScale;
            float f4 = scale.value;
            PlusEffectInfo.Scale scale2 = valueRange.maxScale;
            particleRule2.setValueRange(factorType15, f4, scale2.value);
            FactorType factorType16 = FactorType.SCALE_X;
            particleRule2.setValueRange(factorType16, scale.f285x, scale2.f285x);
            FactorType factorType17 = FactorType.SCALE_Y;
            particleRule2.setValueRange(factorType17, scale.f286y, scale2.f286y);
            ParticleRule particleRule3 = edgeLightingPlusEffectView2.emitter.mParticleRule;
            PlusEffectInfo.SpeedRange speedRange = plusEffectInfo.speedRange;
            particleRule3.setSpeedRange(factorType11, speedRange.minRotation, speedRange.maxRotation);
            PlusEffectInfo.Pos pos3 = speedRange.minPos;
            float f5 = pos3.value;
            PlusEffectInfo.Pos pos4 = speedRange.maxPos;
            particleRule3.setSpeedRange(factorType12, f5, pos4.value);
            float f6 = pos3.f283x;
            float abs = Math.abs(f6) * f6;
            float f7 = pos4.f283x;
            particleRule3.setSpeedRange(factorType13, abs, f7 * Math.abs(f7));
            float f8 = pos3.f284y;
            float abs2 = Math.abs(f8) * f8;
            float f9 = pos4.f284y;
            particleRule3.setSpeedRange(factorType14, abs2, Math.abs(f9) * f9);
            PlusEffectInfo.Scale scale3 = speedRange.minScale;
            float f10 = scale3.value;
            PlusEffectInfo.Scale scale4 = speedRange.maxScale;
            particleRule3.setSpeedRange(factorType15, f10, scale4.value);
            particleRule3.setSpeedRange(factorType16, scale3.f285x, scale4.f285x);
            particleRule3.setSpeedRange(factorType17, scale3.f286y, scale4.f286y);
            ParticleRule particleRule4 = edgeLightingPlusEffectView2.emitter.mParticleRule;
            PlusEffectInfo.AccelerationRange accelerationRange = plusEffectInfo.accelerationRange;
            particleRule4.setAccelerationRange(factorType11, accelerationRange.minRotation, accelerationRange.maxRotation);
            PlusEffectInfo.Pos pos5 = accelerationRange.minPos;
            float f11 = pos5.value;
            PlusEffectInfo.Pos pos6 = accelerationRange.maxPos;
            particleRule4.setAccelerationRange(factorType12, f11, pos6.value);
            particleRule4.setAccelerationRange(factorType13, pos5.f283x, pos6.f283x);
            particleRule4.setAccelerationRange(factorType14, pos5.f284y, pos6.f284y);
            PlusEffectInfo.Scale scale5 = accelerationRange.minScale;
            float f12 = scale5.value;
            PlusEffectInfo.Scale scale6 = accelerationRange.maxScale;
            particleRule4.setAccelerationRange(factorType15, f12, scale6.value);
            particleRule4.setAccelerationRange(factorType16, scale5.f285x, scale6.f285x);
            particleRule4.setAccelerationRange(factorType17, scale5.f286y, scale6.f286y);
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
            ArrayList arrayList = emitter2.mEmitters;
            arrayList.add(emitter);
            int size = arrayList.size();
            StringBuilder sb = new StringBuilder();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    sb.append(((Emitter) arrayList.get(size)).hashCode());
                }
            }
            emitter2.subEmitterKey = sb.toString();
            z2 = true;
        }
        this.isLoaded = z2;
    }
}
