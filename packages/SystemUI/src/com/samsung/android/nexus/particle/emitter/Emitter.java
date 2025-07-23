package com.samsung.android.nexus.particle.emitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Emitter {
    public final EmissionRule mEmissionRule;
    public final ParticleRule mParticleRule;
    public World mWorld;
    public final ArrayList mEmitters = new ArrayList();
    public String subEmitterKey = "";
    public boolean isSubEmitter = false;

    public Emitter(Context context, EmissionRule emissionRule, ParticleRule particleRule) {
        this.mEmissionRule = emissionRule;
        this.mParticleRule = particleRule;
    }

    public final void destroy() {
        int size = this.mEmitters.size();
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(size, "destroy: size = [", "]");
        ParticleRule particleRule = this.mParticleRule;
        m.append(particleRule);
        Log.d("Nexus[Emitter]", m.toString());
        for (int i = 0; i < size; i++) {
            Emitter emitter = (Emitter) this.mEmitters.get(i);
            if (emitter != null) {
                emitter.destroy();
            }
        }
        if (particleRule != null) {
            if (particleRule.particleTexture != null) {
                Log.d("Nexus[Emitter]", "destroy:  particleTexture release()");
                particleRule.particleTexture.onRelease();
                BitmapParticleTexture bitmapParticleTexture = particleRule.particleTexture;
                Bitmap bitmap = bitmapParticleTexture.mBitmap;
                if (bitmap != null) {
                    bitmap.recycle();
                    bitmapParticleTexture.mBitmap = null;
                }
            }
            Arrays.fill(particleRule.factorRangeableList.rangeables, (Object) null);
            particleRule.factorKeyFrameList.clear();
        }
        this.mEmitters.clear();
    }

    public Emitter(Context context, World world, EmissionRule emissionRule, ParticleRule particleRule) {
        this.mWorld = world;
        this.mEmissionRule = emissionRule;
        this.mParticleRule = particleRule;
    }
}
