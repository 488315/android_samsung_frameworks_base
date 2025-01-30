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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        Size size = z ? roundedCornerResDelegate.topRoundedSize : roundedCornerResDelegate.bottomRoundedSize;
        regionInterceptingFrameLayout.addView(imageView, new FrameLayout.LayoutParams(size.getWidth(), size.getHeight(), RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound2, i) | RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound1, i)));
        return imageView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
    
        if (r6 != false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005a, code lost:
    
        if (r6 != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0068, code lost:
    
        if (r6 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0076, code lost:
    
        if (r6 != false) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initView(ImageView imageView, int i, int i2) {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        boolean z = this.isTop;
        Drawable drawable = z ? roundedCornerResDelegate.topRoundedDrawable : roundedCornerResDelegate.bottomRoundedDrawable;
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageResource(z ? R.drawable.rounded_corner_top : R.drawable.rounded_corner_bottom);
        }
        List alignedBounds = getAlignedBounds();
        boolean contains = alignedBounds.contains(1);
        boolean contains2 = alignedBounds.contains(0);
        float f = -1.0f;
        float f2 = 0.0f;
        float f3 = 1.0f;
        if (i == 0) {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    if (!contains) {
                    }
                    f2 = 180.0f;
                }
            }
            f = 1.0f;
        } else if (i == 1) {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    if (!contains) {
                    }
                }
                f = 1.0f;
            }
            f3 = -1.0f;
            f = 1.0f;
        } else if (i != 3) {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    if (!contains) {
                    }
                    f = 1.0f;
                }
                f3 = -1.0f;
                f = 1.0f;
            }
            f2 = 180.0f;
            f = 1.0f;
        } else if (!contains || !contains2) {
            if (!contains || contains2) {
                if (!contains) {
                }
                f3 = -1.0f;
                f = 1.0f;
            }
            f2 = 180.0f;
            f = 1.0f;
        }
        imageView.setRotation(f2);
        imageView.setScaleX(f);
        imageView.setScaleY(f3);
        imageView.setImageTintList(ColorStateList.valueOf(i2));
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        Integer valueOf = Integer.valueOf(i);
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        roundedCornerResDelegate.updateDisplayUniqueId(str, valueOf);
        ImageView imageView = (ImageView) view;
        initView(imageView, i2, i3);
        Size size = this.isTop ? roundedCornerResDelegate.topRoundedSize : roundedCornerResDelegate.bottomRoundedSize;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = size.getWidth();
        layoutParams.height = size.getHeight();
        layoutParams.gravity = RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound2, i2) | RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound1, i2);
        view.setLayoutParams(layoutParams);
    }
}
