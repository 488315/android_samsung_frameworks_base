package com.android.systemui.decor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.RegionInterceptingFrameLayout;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class CoverRoundedCornerDecorProviderImpl extends DecorProvider {
    public final List alignedBounds = Collections.singletonList(1);
    public final int viewId;

    public CoverRoundedCornerDecorProviderImpl(int i) {
        this.viewId = i;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final List getAlignedBounds() {
        return this.alignedBounds;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final View inflateView(Context context, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i, int i2) {
        ImageView imageView = new ImageView(context);
        imageView.setId(this.viewId);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageResource(R.drawable.rounded_corner_cover);
        CoverRoundedCornerDecorProviderImplKt.access$setRotation(imageView, i);
        imageView.setImageTintList(ColorStateList.valueOf(i2));
        regionInterceptingFrameLayout.addView(imageView, new FrameLayout.LayoutParams(-1, -1, 17));
        return imageView;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            CoverRoundedCornerDecorProviderImplKt.access$setRotation(imageView, i2);
            imageView.setImageTintList(ColorStateList.valueOf(i3));
        }
    }
}
