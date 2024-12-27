package com.android.systemui.media.audiovisseekbar.renderer;

import android.graphics.RectF;
import android.view.View;
import com.android.systemui.media.audiovisseekbar.config.AudioVisSeekBarConfig;
import com.android.systemui.media.audiovisseekbar.config.RendererConfig;
import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class BaseRenderer {
    public final AudioVisSeekBarConfig config;
    public float thumbX;
    public final View view;
    public final RectF bounds = new RectF();
    public float motionActivity = 1.0f;

    public BaseRenderer(View view, AudioVisSeekBarConfig audioVisSeekBarConfig) {
        this.view = view;
        this.config = audioVisSeekBarConfig;
    }

    public final float getCenterY() {
        float height = this.view.getHeight();
        RendererConfig.INSTANCE.getClass();
        return height - DimensionUtilsKt.dpToPx(8.0f);
    }

    public void onLayout(RectF rectF) {
        this.bounds.set(rectF);
    }

    public void onThumbLocationChanged(float f) {
        this.thumbX = f;
        this.bounds.right = f;
        this.view.invalidate();
    }
}
