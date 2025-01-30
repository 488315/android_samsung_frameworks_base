package com.samsung.android.nexus.particle.emitter.layer;

import android.graphics.Canvas;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.layer.NexusLayerParams;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.particle.BaseParticleLayer;
import com.samsung.android.nexus.particle.emitter.Particle;
import com.samsung.android.nexus.particle.emitter.World;
import com.samsung.android.nexus.particle.emitter.texture.ParticleTexture;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EmitterParticleLayer extends BaseParticleLayer {
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
        long currentTimeMillis = System.currentTimeMillis();
        World world = this.mWorld;
        world.step(currentTimeMillis);
        world.draw(canvas);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDestroy() {
        World world = this.mWorld;
        world.getClass();
        Log.m262i("World", "stop: ");
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
                ParticleTexture particleTexture = particle.mParticleTexture;
                if (particleTexture != null) {
                    particleTexture.onRelease();
                    particle.mParticleTexture = null;
                }
                particle = particle.next;
            }
            Particle.sPool.put(worldParticleLinkedList);
        }
        world.mRootEmitter.destroy();
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
        boolean booleanValue = bool.booleanValue();
        World world = this.mWorld;
        if (booleanValue) {
            world.resume();
        } else {
            if (world.mIsPaused) {
                return;
            }
            Log.m262i("World", "pause: ");
            world.mIsPaused = true;
            world.mPausedTime = System.currentTimeMillis();
        }
    }

    @Override // com.samsung.android.nexus.particle.BaseParticleLayer
    public final void prepareToDraw() {
        System.currentTimeMillis();
    }
}
