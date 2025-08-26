package com.android.keyguard;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.bouncer.shared.constants.PinBouncerConstants;

/* loaded from: classes.dex */
public class PinShapeHintingView extends LinearLayout {
    public final int mColor;
    public final int mDotDiameter;
    public final int mPinLength;

    public PinShapeHintingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColor = getContext().getColor(R.color.search_url_text_normal);
        new PinShapeAdapter(context);
        this.mPinLength = 6;
        this.mDotDiameter = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.password_shape_size);
        for (int i = 0; i < this.mPinLength; i++) {
            ImageView imageView = new ImageView(context, attributeSet);
            int i2 = this.mDotDiameter;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(i2, i2));
            imageView.setImageResource(PinBouncerConstants.pinDotAvd);
            if (imageView.getDrawable() != null) {
                imageView.getDrawable().setTint(this.mColor);
            }
            addView(imageView);
        }
    }
}
