package com.samsung.android.nexus.particle.emitter.layer;

import android.graphics.Canvas;
import android.os.SystemClock;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.layer.NexusLayerParams;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.particle.BaseParticleLayer;
import com.samsung.android.nexus.particle.emitter.Particle;
import com.samsung.android.nexus.particle.emitter.World;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapParticleTexture;

/* loaded from: classes4.dex */
public class EmitterParticleLayer extends BaseParticleLayer {
    public final World mWorld;

    public EmitterParticleLayer(LayerContainer layerContainer, NexusLayerParams nexusLayerParams) {
        super(nexusLayerParams);
        World world = new World(layerContainer);
        this.mWorld = world;
        world.setSize(nexusLayerParams.mWidth, nexusLayerParams.mHeight);
        world.start();
        world.resume();
    }

    @Override // com.samsung.android.nexus.particle.BaseParticleLayer
    public final void drawOnCanvas(Canvas canvas) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        World world = this.mWorld;
        world.step(jElapsedRealtime);
        world.draw(canvas);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDestroy() {
        World world = this.mWorld;
        world.getClass();
        Log.i("World", "stop: ");
        world.mIsRunning = false;
        world.mTotalPausedTime = 0L;
        world.mPausedTime = 0L;
        World.WorldParticleLinkedList worldParticleLinkedList = world.mWorldParticleLinkedList;
        int i = worldParticleLinkedList.size;
        if (i > 0) {
            Particle particle = worldParticleLinkedList.head;
            for (int i2 = 0; i2 < i; i2++) {
                particle.mEnable = false;
                particle.mStartTime = 0L;
                particle.mEndTime = 0L;
                particle.mEmitterSchedules.clear();
                BitmapParticleTexture bitmapParticleTexture = particle.mParticleTexture;
                if (bitmapParticleTexture != null) {
                    bitmapParticleTexture.onRelease();
                    particle.mParticleTexture = null;
                }
                particle.release();
                particle = particle.next;
            }
            Particle.sPool.put(worldParticleLinkedList);
        }
        world.mRootEmitter.destroy();
        world.mRootParticle.release();
        Particle.ParticleLinkedListPool particleLinkedListPool = Particle.sPool;
        particleLinkedListPool.getClass();
        Log.i("Particle", "clear: ");
        Particle particle2 = particleLinkedListPool.head;
        while (particle2 != null) {
            Particle particle3 = particle2.next;
            particle2.release();
            particle2.next = null;
            particle2 = particle3;
        }
        particleLinkedListPool.head = null;
        particleLinkedListPool.tail = null;
        particleLinkedListPool.size = 0;
        particleLinkedListPool.createSize = 0;
        BitmapCache.sLruCache.evictAll();
    }

    @Override // com.samsung.android.nexus.particle.BaseParticleLayer, com.samsung.android.nexus.base.layer.BaseLayer
    public final void onLayerParamsChanged(NexusLayerParams nexusLayerParams) {
        this.mNeedToInit = true;
        World world = this.mWorld;
        if (world != null) {
            world.setSize(nexusLayerParams.mWidth, nexusLayerParams.mHeight);
            world.start();
            world.resume();
        }
    }

    @Override // com.samsung.android.nexus.particle.BaseParticleLayer, com.samsung.android.nexus.base.layer.BaseLayer
    public final void onVisibilityChanged(Boolean bool) {
        boolean zBooleanValue = bool.booleanValue();
        World world = this.mWorld;
        if (zBooleanValue) {
            world.resume();
        } else {
            if (world.mIsPaused) {
                return;
            }
            Log.i("World", "pause: ");
            world.mIsPaused = true;
            world.mPausedTime = SystemClock.elapsedRealtime();
        }
    }

    @Override // com.samsung.android.nexus.particle.BaseParticleLayer
    public final void prepareToDraw() {
        SystemClock.elapsedRealtime();
    }
}
