package com.android.systemui.screenshot;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class OverlayActionChip extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageView mIconView;
    public boolean mIsPending;
    public TextView mTextView;

    public OverlayActionChip(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        ImageView imageView = (ImageView) findViewById(R.id.overlay_action_chip_icon);
        Objects.requireNonNull(imageView);
        this.mIconView = imageView;
        TextView textView = (TextView) findViewById(R.id.overlay_action_chip_text);
        Objects.requireNonNull(textView);
        this.mTextView = textView;
        updatePadding(textView.getText().length() > 0);
    }

    public final void setIcon(Icon icon, boolean z) {
        this.mIconView.setImageIcon(icon);
        if (z) {
            return;
        }
        this.mIconView.setImageTintList(null);
    }

    @Override // android.view.View
    public final void setPressed(boolean z) {
        super.setPressed(this.mIsPending || z);
    }

    public final void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
        updatePadding(charSequence.length() > 0);
    }

    public final void updatePadding(boolean z) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
        if (z) {
            int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_padding_start);
            int dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_spacing);
            int dimensionPixelSize3 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_padding_end);
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(dimensionPixelSize2);
            layoutParams2.setMarginEnd(dimensionPixelSize3);
        } else {
            int dimensionPixelSize4 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_icon_only_padding_horizontal);
            layoutParams.setMarginStart(dimensionPixelSize4);
            layoutParams.setMarginEnd(dimensionPixelSize4);
        }
        this.mIconView.setLayoutParams(layoutParams);
        this.mTextView.setLayoutParams(layoutParams2);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsPending = false;
    }
}
