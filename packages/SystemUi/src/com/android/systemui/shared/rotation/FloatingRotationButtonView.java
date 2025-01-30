package com.android.systemui.shared.rotation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FloatingRotationButtonView extends ImageView {
    public final Configuration mLastConfiguration;
    public final Paint mOvalBgPaint;
    public KeyButtonRipple mRipple;

    public FloatingRotationButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        KeyButtonRipple keyButtonRipple;
        int updateFrom = this.mLastConfiguration.updateFrom(configuration);
        if (((updateFrom & 1024) == 0 && (updateFrom & 4096) == 0) || (keyButtonRipple = this.mRipple) == null) {
            return;
        }
        keyButtonRipple.mMaxWidth = keyButtonRipple.mTargetView.getContext().getResources().getDimensionPixelSize(keyButtonRipple.mMaxWidthResource);
        keyButtonRipple.invalidateSelf();
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
