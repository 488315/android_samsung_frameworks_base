package com.samsung.android.nexus.base.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLES20;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.nexus.base.DrawRequester;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.utils.Log;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LayerContainer extends BaseLayer {
    public final List mEffectLayer = new ArrayList();
    public boolean mIsReadyToCreate = false;

    public LayerContainer(Context context, Object obj) {
        Log.i("LayerContainer", "LayerContainer() : create LayerContainer");
        setNexusContext(new NexusContext(context));
        getNexusContext().mAnimatorCore.mDrawRequester = new DrawRequester(obj);
    }

    public final void addLayer(BaseLayer baseLayer) {
        baseLayer.setNexusContext(getNexusContext());
        if (this.mIsReadyToCreate) {
            baseLayer.onCreate();
        }
        ((ArrayList) this.mEffectLayer).add(baseLayer);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void draw() {
        GLES20.glClear(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        onDraw();
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onCreate() {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseLayer) obj).onCreate();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDestroy() {
        Log.i("LayerContainer", "destroy()");
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseLayer) obj).onDestroy();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDraw() {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseLayer) obj).onDraw();
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onSizeChanged(int i, int i2) {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((BaseLayer) obj).onSizeChanged(i, i2);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onTapEvent(int i, int i2, long j) {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((BaseLayer) obj).onTapEvent(i, i2, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onTouchCancelEvent(int i, int i2, long j) {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((BaseLayer) obj).onTouchCancelEvent(i, i2, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onTouchEvent(int i, int i2, long j) {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((BaseLayer) obj).onTouchEvent(i, i2, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onVisibilityChanged(Boolean bool) {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseLayer) obj).onVisibilityChanged(bool);
        }
    }

    public final void removeAllLayers() {
        int i = 0;
        setRenderMode(0);
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseLayer) obj).onDestroy();
        }
        ((ArrayList) this.mEffectLayer).clear();
    }

    public final void setRenderMode(int i) {
        Log.i("LayerContainer", "setRenderMode() : " + i);
        NexusContext nexusContext = getNexusContext();
        nexusContext.getClass();
        Log.i("NexusContext", "setRenderMode() : " + i);
        AnimatorCore animatorCore = nexusContext.mAnimatorCore;
        animatorCore.getClass();
        Log.i("AnimatorCore", "setRenderMode() : " + i);
        animatorCore.mRenderMode = i;
        animatorCore.startAnimator();
    }

    public final void tapCommand(int i, int i2, int i3, long j) {
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "topCommand() : ", " , ", " , ");
        m.append(i3);
        m.append(" , ");
        m.append(j);
        Log.i("LayerContainer", m.toString());
        if (i == 0) {
            onTouchEvent(i2, i3, j);
        } else if (i == 1) {
            onTouchCancelEvent(i2, i3, j);
        } else {
            if (i != 2) {
                return;
            }
            onTapEvent(i2, i3, j);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onDraw(Canvas canvas) {
        ArrayList arrayList = (ArrayList) this.mEffectLayer;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseLayer) obj).onDraw(canvas);
        }
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void draw(Canvas canvas) {
        onDraw(canvas);
    }

    @Override // com.samsung.android.nexus.base.layer.BaseLayer
    public final void onLayerParamsChanged(NexusLayerParams nexusLayerParams) {
    }
}
