package com.android.systemui.shared.rotation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.shared.navigationbar.KeyButtonRipple;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
