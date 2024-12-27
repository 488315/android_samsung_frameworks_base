package com.android.keyguard;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class PinShapeHintingView extends LinearLayout {
    public final int mColor;
    public final int mDotDiameter;
    public final int mPinLength;
    public final int mPosition;

    public PinShapeHintingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColor = Utils.getColorAttr(R.^attr-private.materialColorOnTertiaryFixed, getContext()).getDefaultColor();
        new PinShapeAdapter(context);
        this.mPinLength = 6;
        this.mDotDiameter = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.password_shape_size);
        for (int i = 0; i < this.mPinLength; i++) {
            ImageView imageView = new ImageView(context, attributeSet);
            int i2 = this.mDotDiameter;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(i2, i2));
            imageView.setImageResource(com.android.systemui.R.drawable.pin_dot_avd);
            if (imageView.getDrawable() != null) {
                imageView.getDrawable().setTint(this.mColor);
            }
            addView(imageView);
        }
    }
}
