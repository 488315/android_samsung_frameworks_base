package com.android.keyguard;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PinShapeHintingView extends LinearLayout {
    public final int mColor;
    public final int mDotDiameter;
    public final int mPinLength;
    public int mPosition;

    public PinShapeHintingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColor = Utils.getColorAttr(R.^attr-private.magnifierStyle, getContext()).getDefaultColor();
        this.mPosition = 0;
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
