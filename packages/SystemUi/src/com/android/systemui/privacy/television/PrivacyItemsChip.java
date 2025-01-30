package com.android.systemui.privacy.television;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.privacy.PrivacyType;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyItemsChip extends FrameLayout {
    public final PrivacyChipDrawable mChipBackgroundDrawable;
    public final int mCollapsedIconSize;
    public final ChipConfig mConfig;
    public final int mIconMarginHorizontal;
    public final int mIconSize;
    public final List mIcons;
    public int mState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChipConfig {
        public final boolean collapseToDot;
        public final int colorRes;
        public final List privacyTypes;

        public ChipConfig(List<PrivacyType> list, int i, boolean z) {
            this.privacyTypes = list;
            this.colorRes = i;
            this.collapseToDot = z;
        }
    }

    public PrivacyItemsChip(Context context, ChipConfig chipConfig) {
        super(context);
        this.mIcons = new ArrayList();
        this.mState = 0;
        this.mConfig = chipConfig;
        setVisibility(8);
        Resources resources = context.getResources();
        this.mIconSize = resources.getDimensionPixelSize(R.dimen.privacy_chip_icon_size);
        this.mCollapsedIconSize = resources.getDimensionPixelSize(R.dimen.privacy_chip_collapsed_icon_size);
        this.mIconMarginHorizontal = resources.getDimensionPixelSize(R.dimen.privacy_chip_icon_margin_in_between);
        LayoutInflater.from(context).inflate(R.layout.tv_ongoing_privacy_chip, this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.icons_container);
        PrivacyChipDrawable privacyChipDrawable = new PrivacyChipDrawable(context, chipConfig.colorRes, chipConfig.collapseToDot);
        this.mChipBackgroundDrawable = privacyChipDrawable;
        privacyChipDrawable.setCallback(new Drawable.Callback() { // from class: com.android.systemui.privacy.television.PrivacyItemsChip.1
            @Override // android.graphics.drawable.Drawable.Callback
            public final void invalidateDrawable(Drawable drawable) {
                PrivacyItemsChip.this.invalidate();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
            }
        });
        setBackground(privacyChipDrawable);
        for (PrivacyType privacyType : chipConfig.privacyTypes) {
            ImageView imageView = new ImageView(context);
            Drawable icon = privacyType.getIcon(context);
            icon.mutate().setTint(context.getColor(R.color.privacy_icon_tint));
            imageView.setImageDrawable(icon);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            this.mIcons.add(imageView);
            int i = this.mIconSize;
            linearLayout.addView(imageView, i, i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            int i2 = this.mIconMarginHorizontal;
            layoutParams.leftMargin = i2;
            layoutParams.rightMargin = i2;
            imageView.setVisibility(8);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        PrivacyChipDrawable privacyChipDrawable = this.mChipBackgroundDrawable;
        privacyChipDrawable.getForegroundBounds(privacyChipDrawable.mTmpRectF);
        float lerp = MathUtils.lerp(privacyChipDrawable.mExpandedChipRadius, privacyChipDrawable.mCollapseToDot ? privacyChipDrawable.mCollapsedChipRadius : privacyChipDrawable.mBgRadius, privacyChipDrawable.mCollapseProgress);
        privacyChipDrawable.mPath.reset();
        privacyChipDrawable.mPath.addRoundRect(privacyChipDrawable.mTmpRectF, lerp, lerp, Path.Direction.CW);
        canvas.clipPath(privacyChipDrawable.mPath);
        super.dispatchDraw(canvas);
    }
}
