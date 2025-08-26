package com.android.systemui.shared.rotation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.shared.navigationbar.KeyButtonRipple;

/* loaded from: classes3.dex */
public class FloatingRotationButtonView extends ImageView {
    public int mDiameter;
    public final Configuration mLastConfiguration;
    public final Paint mOvalBgPaint;
    public KeyButtonRipple mRipple;

    public FloatingRotationButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        KeyButtonRipple keyButtonRipple;
        int iUpdateFrom = this.mLastConfiguration.updateFrom(configuration);
        if (((iUpdateFrom & 1024) == 0 && (iUpdateFrom & 4096) == 0) || (keyButtonRipple = this.mRipple) == null) {
            return;
        }
        keyButtonRipple.mMaxWidth = keyButtonRipple.mTargetView.getContext().getResources().getDimensionPixelSize(keyButtonRipple.mMaxWidthResource);
        keyButtonRipple.invalidateSelf();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = this.mDiameter;
        setMeasuredDimension(i3, i3);
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    public FloatingRotationButtonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOvalBgPaint = new Paint(3);
        this.mLastConfiguration = getResources().getConfiguration();
        setClickable(true);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }
}
