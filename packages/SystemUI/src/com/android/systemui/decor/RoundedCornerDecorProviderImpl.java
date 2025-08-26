package com.android.systemui.decor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Size;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.RegionInterceptingFrameLayout;
import java.util.List;

/* loaded from: classes2.dex */
public final class RoundedCornerDecorProviderImpl extends CornerDecorProvider {
    public final int alignedBound1;
    public final int alignedBound2;
    public final boolean isTop = getAlignedBounds().contains(1);
    public final RoundedCornerResDelegate roundedCornerResDelegate;
    public final int viewId;

    public RoundedCornerDecorProviderImpl(int i, int i2, int i3, RoundedCornerResDelegate roundedCornerResDelegate) {
        this.viewId = i;
        this.alignedBound1 = i2;
        this.alignedBound2 = i3;
        this.roundedCornerResDelegate = roundedCornerResDelegate;
    }

    @Override // com.android.systemui.decor.CornerDecorProvider
    public final int getAlignedBound1() {
        return this.alignedBound1;
    }

    @Override // com.android.systemui.decor.CornerDecorProvider
    public final int getAlignedBound2() {
        return this.alignedBound2;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final View inflateView(Context context, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i, int i2) {
        ImageView imageView = new ImageView(context);
        imageView.setId(this.viewId);
        initView(imageView, i, i2);
        boolean z = this.isTop;
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        Size topRoundedSize = z ? roundedCornerResDelegate.getTopRoundedSize() : roundedCornerResDelegate.getBottomRoundedSize();
        regionInterceptingFrameLayout.addView(imageView, new FrameLayout.LayoutParams(topRoundedSize.getWidth(), topRoundedSize.getHeight(), RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound2, i) | RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound1, i)));
        return imageView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
    
        if (r6 != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
    
        if (r6 != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0084, code lost:
    
        if (r6 != false) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initView(ImageView imageView, int i, int i2) {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        boolean z = this.isTop;
        Drawable topRoundedDrawable = z ? roundedCornerResDelegate.getTopRoundedDrawable() : roundedCornerResDelegate.getBottomRoundedDrawable();
        if (topRoundedDrawable != null) {
            imageView.setImageDrawable(topRoundedDrawable);
        } else {
            imageView.setImageResource(z ? R.drawable.rounded_corner_top : R.drawable.rounded_corner_bottom);
        }
        List alignedBounds = getAlignedBounds();
        boolean zContains = alignedBounds.contains(1);
        boolean zContains2 = alignedBounds.contains(0);
        float f = 180.0f;
        float f2 = -1.0f;
        float f3 = 1.0f;
        if (i == 0) {
            if (!zContains || !zContains2) {
                if (!zContains || zContains2) {
                    if (!zContains) {
                    }
                }
                f = 0.0f;
            }
            f2 = 1.0f;
        } else if (i == 1) {
            if (!zContains || !zContains2) {
                if (zContains && !zContains2) {
                    f = 0.0f;
                } else if (zContains || !zContains2) {
                }
                f2 = 1.0f;
            }
            f = 0.0f;
        } else if (i != 3) {
            if (!zContains || !zContains2) {
                if (!zContains || zContains2) {
                    if (!zContains) {
                    }
                    f = 0.0f;
                } else {
                    f3 = -1.0f;
                    f2 = 1.0f;
                }
                f = 0.0f;
            }
            f2 = 1.0f;
        } else {
            if (!zContains || !zContains2) {
                if (!zContains || zContains2) {
                    if (!zContains) {
                    }
                    f3 = -1.0f;
                    f2 = 1.0f;
                }
                f2 = 1.0f;
            }
            f = 0.0f;
        }
        imageView.setRotation(f);
        imageView.setScaleX(f2);
        imageView.setScaleY(f3);
        imageView.setImageTintList(ColorStateList.valueOf(i2));
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        Integer numValueOf = Integer.valueOf(i);
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        roundedCornerResDelegate.updateDisplayUniqueId(str, numValueOf);
        ImageView imageView = (ImageView) view;
        initView(imageView, i2, i3);
        Size topRoundedSize = this.isTop ? roundedCornerResDelegate.getTopRoundedSize() : roundedCornerResDelegate.getBottomRoundedSize();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = topRoundedSize.getWidth();
        layoutParams.height = topRoundedSize.getHeight();
        layoutParams.gravity = RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound2, i2) | RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound1, i2);
        view.setLayoutParams(layoutParams);
    }
}
