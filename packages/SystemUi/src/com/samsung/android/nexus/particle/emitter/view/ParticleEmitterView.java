package com.samsung.android.nexus.particle.emitter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.samsung.android.nexus.base.layer.LayerContainer;
import com.samsung.android.nexus.base.layer.NexusLayerParams;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.particle.emitter.layer.EmitterParticleLayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ParticleEmitterView extends ViewGroup {
    public final Object mCustomInvalidator;
    public EmitterParticleLayer mEmitterParticleLayer;
    public LayerContainer mLayerContainer;

    public ParticleEmitterView(Context context) {
        super(context);
        this.mCustomInvalidator = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LayerContainer layerContainer = this.mLayerContainer;
        if (layerContainer != null) {
            layerContainer.onDestroy();
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        this.mLayerContainer.onDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        setEffectLayerSize(i3 - i, i4 - i2);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth <= 0 || measuredHeight <= 0) {
            return;
        }
        setEffectLayerSize(measuredWidth, measuredHeight);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mLayerContainer.tapCommand(0, (int) motionEvent.getX(), (int) motionEvent.getY(), motionEvent.getEventTime());
        } else if (action == 1) {
            this.mLayerContainer.tapCommand(2, (int) motionEvent.getX(), (int) motionEvent.getY(), motionEvent.getEventTime());
        } else if (action == 3) {
            this.mLayerContainer.tapCommand(1, (int) motionEvent.getX(), (int) motionEvent.getY(), motionEvent.getEventTime());
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        LayerContainer layerContainer = this.mLayerContainer;
        if (layerContainer != null) {
            Boolean valueOf = Boolean.valueOf(i == 0);
            Log.m262i("LayerContainer", "setVisibility() : " + valueOf);
            layerContainer.onVisibilityChanged(valueOf);
        }
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        LayerContainer layerContainer = this.mLayerContainer;
        if (layerContainer != null) {
            Boolean valueOf = Boolean.valueOf(i == 0);
            Log.m262i("LayerContainer", "setVisibility() : " + valueOf);
            layerContainer.onVisibilityChanged(valueOf);
        }
    }

    public final void setEffectLayerSize(int i, int i2) {
        NexusLayerParams nexusLayerParams = new NexusLayerParams(i, i2);
        EmitterParticleLayer emitterParticleLayer = this.mEmitterParticleLayer;
        if (emitterParticleLayer == null) {
            Object obj = this.mCustomInvalidator;
            if (obj == null) {
                obj = this;
            }
            LayerContainer layerContainer = new LayerContainer(getContext(), obj);
            this.mLayerContainer = layerContainer;
            EmitterParticleLayer emitterParticleLayer2 = new EmitterParticleLayer(layerContainer, nexusLayerParams);
            this.mEmitterParticleLayer = emitterParticleLayer2;
            this.mLayerContainer.addLayer(emitterParticleLayer2);
            this.mLayerContainer.setRenderMode(2);
        } else {
            NexusLayerParams layerParams = emitterParticleLayer.getLayerParams();
            if (layerParams != null && layerParams.mWidth == i && layerParams.mHeight == i2) {
                return;
            } else {
                this.mEmitterParticleLayer.setLayerParams(nexusLayerParams);
            }
        }
        this.mLayerContainer.setSize(i, i2);
    }

    public ParticleEmitterView(Context context, Object obj) {
        super(context);
        this.mCustomInvalidator = null;
        this.mCustomInvalidator = obj;
    }

    public ParticleEmitterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCustomInvalidator = null;
    }

    public ParticleEmitterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCustomInvalidator = null;
    }

    public ParticleEmitterView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCustomInvalidator = null;
    }
}
