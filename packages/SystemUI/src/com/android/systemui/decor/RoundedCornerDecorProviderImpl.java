package com.android.systemui.decor;

import android.content.Context;
import android.util.Size;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.RegionInterceptingFrameLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0056, code lost:
    
        if (r6 != false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0065, code lost:
    
        if (r6 != false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0073, code lost:
    
        if (r6 != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0084, code lost:
    
        if (r6 != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initView(android.widget.ImageView r7, int r8, int r9) {
        /*
            r6 = this;
            com.android.systemui.decor.RoundedCornerResDelegate r0 = r6.roundedCornerResDelegate
            boolean r1 = r6.isTop
            if (r1 == 0) goto Lb
            android.graphics.drawable.Drawable r0 = r0.getTopRoundedDrawable()
            goto Lf
        Lb:
            android.graphics.drawable.Drawable r0 = r0.getBottomRoundedDrawable()
        Lf:
            if (r0 == 0) goto L15
            r7.setImageDrawable(r0)
            goto L21
        L15:
            if (r1 == 0) goto L1b
            r0 = 2131234999(0x7f0810b7, float:1.808618E38)
            goto L1e
        L1b:
            r0 = 2131234996(0x7f0810b4, float:1.8086174E38)
        L1e:
            r7.setImageResource(r0)
        L21:
            java.util.List r6 = r6.getAlignedBounds()
            r0 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            boolean r1 = r6.contains(r1)
            r2 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            boolean r6 = r6.contains(r2)
            r2 = 1127481344(0x43340000, float:180.0)
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            r4 = 0
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L76
            if (r8 == r0) goto L68
            r0 = 3
            if (r8 == r0) goto L59
            if (r1 == 0) goto L4b
            if (r6 == 0) goto L4b
        L49:
            r3 = r5
            goto L87
        L4b:
            if (r1 == 0) goto L54
            if (r6 != 0) goto L54
        L4f:
            r2 = r5
            r5 = r3
            r3 = r2
        L52:
            r2 = r4
            goto L87
        L54:
            if (r1 != 0) goto L7b
            if (r6 == 0) goto L7b
        L58:
            goto L52
        L59:
            if (r1 == 0) goto L5e
            if (r6 == 0) goto L5e
            goto L58
        L5e:
            if (r1 == 0) goto L63
            if (r6 != 0) goto L63
            goto L49
        L63:
            if (r1 != 0) goto L4f
            if (r6 != 0) goto L7b
            goto L4f
        L68:
            if (r1 == 0) goto L6d
            if (r6 == 0) goto L6d
            goto L4f
        L6d:
            if (r1 == 0) goto L71
            if (r6 == 0) goto L7b
        L71:
            if (r1 != 0) goto L52
            if (r6 == 0) goto L52
            goto L49
        L76:
            if (r1 == 0) goto L7d
            if (r6 != 0) goto L7b
            goto L7d
        L7b:
            r2 = r4
            goto L49
        L7d:
            if (r1 == 0) goto L82
            if (r6 != 0) goto L82
            goto L58
        L82:
            if (r1 != 0) goto L49
            if (r6 == 0) goto L49
            goto L4f
        L87:
            r7.setRotation(r2)
            r7.setScaleX(r3)
            r7.setScaleY(r5)
            android.content.res.ColorStateList r6 = android.content.res.ColorStateList.valueOf(r9)
            r7.setImageTintList(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.decor.RoundedCornerDecorProviderImpl.initView(android.widget.ImageView, int, int):void");
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        Integer valueOf = Integer.valueOf(i);
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        roundedCornerResDelegate.updateDisplayUniqueId(str, valueOf);
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
