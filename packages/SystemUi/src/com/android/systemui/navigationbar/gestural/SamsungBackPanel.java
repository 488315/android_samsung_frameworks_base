package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.MathUtils;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungBackPanel extends BackPanel {
    public int arrowDarkColor;
    public int arrowLightColor;
    public int backPanelDarkColor;
    public int backPanelLightColor;
    public final LatencyTracker latencyTracker;
    public final int maxArrowAlpha;
    public final int maxBGAlpha;
    public final NavBarStateManager navBarStateManager;

    public SamsungBackPanel(Context context, LatencyTracker latencyTracker, NavBarStore navBarStore) {
        super(context, latencyTracker);
        this.latencyTracker = latencyTracker;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
        this.backPanelDarkColor = context.getColor(R.color.navbar_backpanel_background_dark);
        this.backPanelLightColor = context.getColor(R.color.navbar_backpanel_background_light);
        this.arrowDarkColor = context.getColor(R.color.navbar_backpanel_arrow_dark);
        this.arrowLightColor = context.getColor(R.color.navbar_backpanel_arrow_light);
        this.maxArrowAlpha = 179;
        this.maxBGAlpha = 128;
        Paint paint = this.arrowPaint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // com.android.systemui.navigationbar.gestural.BackPanel, android.view.View
    public final void onDraw(Canvas canvas) {
        float f = this.backgroundEdgeCornerRadius.pos;
        float f2 = this.backgroundFarCornerRadius.pos;
        float f3 = 2;
        float f4 = this.backgroundHeight.pos / f3;
        int width = getWidth();
        float f5 = this.backgroundWidth.pos;
        float f6 = this.scalePivotX.pos;
        canvas.save();
        if (!this.isLeftPanel) {
            canvas.scale(-1.0f, 1.0f, width / 2.0f, 0.0f);
        }
        canvas.translate(this.horizontalTranslation.pos, (getHeight() * 0.5f) + this.verticalTranslation.pos);
        float f7 = this.scale.pos;
        canvas.scale(f7, f7, f6, 0.0f);
        RectF rectF = this.arrowBackgroundRect;
        rectF.left = 0.0f;
        rectF.top = -f4;
        rectF.right = f5;
        rectF.bottom = f4;
        Path m165x9c3c3adc = BackPanel.m165x9c3c3adc(rectF, f, f2, f2, f);
        Paint paint = this.arrowBackgroundPaint;
        paint.setAlpha((int) (this.maxBGAlpha * this.backgroundAlpha.pos));
        Unit unit = Unit.INSTANCE;
        canvas.drawPath(m165x9c3c3adc, paint);
        float f8 = this.arrowLength.pos;
        float f9 = this.arrowHeight.pos;
        canvas.translate((f5 - f8) / f3, 0.0f);
        if (!(this.arrowsPointLeft ^ this.isLeftPanel)) {
            canvas.scale(-1.0f, 1.0f, 0.0f, 0.0f);
            canvas.translate(-f8, 0.0f);
        }
        Path m166x4f834c4e = m166x4f834c4e(f8, f9);
        Paint paint2 = this.arrowPaint;
        paint2.setAlpha((int) (MathUtils.min(this.arrowAlpha.pos, this.backgroundAlpha.pos) * this.maxArrowAlpha));
        canvas.drawPath(m166x4f834c4e, paint2);
        canvas.restore();
        if (this.trackingBackArrowLatency) {
            this.latencyTracker.onActionEnd(15);
            this.trackingBackArrowLatency = false;
        }
    }

    @Override // com.android.systemui.navigationbar.gestural.BackPanel
    /* renamed from: updateArrowPaint$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo167x4d69e1c4(float f) {
        this.arrowPaint.setStrokeWidth(getContext().getResources().getDimensionPixelSize(R.dimen.backpanel_arrow_thickness));
        boolean z = this.navBarStateManager.states.darkMode;
        this.arrowPaint.setColor(z ? this.arrowDarkColor : this.arrowLightColor);
        this.arrowBackgroundPaint.setColor(z ? this.backPanelDarkColor : this.backPanelLightColor);
    }

    @Override // com.android.systemui.navigationbar.gestural.BackPanel
    /* renamed from: updateBackPanelColor$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo168x7d33eef5(int i, int i2, int i3, int i4) {
        if (i == 0) {
            i = getContext().getColor(R.color.navbar_backpanel_arrow_dark);
        }
        this.arrowDarkColor = i;
        if (i2 == 0) {
            i2 = getContext().getColor(R.color.navbar_backpanel_arrow_light);
        }
        this.arrowLightColor = i2;
        if (i3 == 0) {
            i3 = getContext().getColor(R.color.navbar_backpanel_background_dark);
        }
        this.backPanelDarkColor = i3;
        if (i4 == 0) {
            i4 = getContext().getColor(R.color.navbar_backpanel_background_light);
        }
        this.backPanelLightColor = i4;
    }
}
