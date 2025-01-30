package com.samsung.android.nexus.base.layer;

import android.content.Context;
import android.graphics.Canvas;
import com.samsung.android.nexus.base.context.NexusContext;
import com.samsung.android.nexus.base.utils.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class BaseLayer {
    private static final String TAG = "BaseLayer";
    private NexusContext mContext;
    private NexusLayerParams mLayerParams;

    public void draw() {
        onDraw();
    }

    public Context getAppContext() {
        NexusContext nexusContext = this.mContext;
        if (nexusContext != null) {
            return nexusContext.mContext;
        }
        Log.m262i(TAG, "Context is null.");
        return null;
    }

    public NexusLayerParams getLayerParams() {
        return this.mLayerParams;
    }

    public NexusContext getNexusContext() {
        return this.mContext;
    }

    public abstract void onCreate();

    public abstract void onDraw();

    public void onDraw(Canvas canvas) {
    }

    public abstract void onLayerParamsChanged(NexusLayerParams nexusLayerParams);

    public abstract void onVisibilityChanged(Boolean bool);

    public void setLayerParams(NexusLayerParams nexusLayerParams) {
        this.mLayerParams = nexusLayerParams;
        onLayerParamsChanged(nexusLayerParams);
    }

    public void setNexusContext(NexusContext nexusContext) {
        Log.m262i(TAG, "setNexusContext(): " + nexusContext);
        this.mContext = nexusContext;
    }

    public void draw(Canvas canvas) {
        onDraw(canvas);
    }

    public void onDestroy() {
    }

    public void onSizeChanged(int i, int i2) {
    }

    public void onTapEvent(int i, int i2, long j) {
    }

    public void onTouchCancelEvent(int i, int i2, long j) {
    }

    public void onTouchEvent(int i, int i2, long j) {
    }
}
