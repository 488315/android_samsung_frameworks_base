package com.android.systemui.decor;

import android.content.Context;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final View inflateView(Context context, ViewGroup viewGroup, int i, int i2) {
        ImageView imageView = new ImageView(context);
        imageView.setId(this.viewId);
        initView(imageView, i, i2);
        boolean z = this.isTop;
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        Size topRoundedSize = z ? roundedCornerResDelegate.getTopRoundedSize() : roundedCornerResDelegate.getBottomRoundedSize();
        viewGroup.addView(imageView, new FrameLayout.LayoutParams(topRoundedSize.getWidth(), topRoundedSize.getHeight(), RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound2, i) | RoundedCornerDecorProviderImplKt.access$toLayoutGravity(this.alignedBound1, i)));
        return imageView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0056, code lost:
    
        if (r7 != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0066, code lost:
    
        if (r7 != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0074, code lost:
    
        if (r7 != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0085, code lost:
    
        if (r7 != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initView(android.widget.ImageView r8, int r9, int r10) {
        /*
            r7 = this;
            com.android.systemui.decor.RoundedCornerResDelegate r0 = r7.roundedCornerResDelegate
            boolean r1 = r7.isTop
            if (r1 == 0) goto Lb
            android.graphics.drawable.Drawable r0 = r0.getTopRoundedDrawable()
            goto Lf
        Lb:
            android.graphics.drawable.Drawable r0 = r0.getBottomRoundedDrawable()
        Lf:
            if (r0 == 0) goto L15
            r8.setImageDrawable(r0)
            goto L21
        L15:
            if (r1 == 0) goto L1b
            r0 = 2131234742(0x7f080fb6, float:1.8085658E38)
            goto L1e
        L1b:
            r0 = 2131234739(0x7f080fb3, float:1.8085652E38)
        L1e:
            r8.setImageResource(r0)
        L21:
            java.util.List r7 = r7.getAlignedBounds()
            r0 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            boolean r1 = r7.contains(r1)
            r2 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            boolean r7 = r7.contains(r2)
            r2 = 1127481344(0x43340000, float:180.0)
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            r4 = 0
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r9 == 0) goto L77
            if (r9 == r0) goto L69
            r0 = 3
            if (r9 == r0) goto L5a
            if (r1 == 0) goto L4b
            if (r7 == 0) goto L4b
        L49:
            r3 = r5
            goto L88
        L4b:
            if (r1 == 0) goto L54
            if (r7 != 0) goto L54
        L4f:
            r2 = r4
            r6 = r5
            r5 = r3
            r3 = r6
            goto L88
        L54:
            if (r1 != 0) goto L7c
            if (r7 == 0) goto L7c
        L58:
            r2 = r4
            goto L88
        L5a:
            if (r1 == 0) goto L5f
            if (r7 == 0) goto L5f
            goto L58
        L5f:
            if (r1 == 0) goto L64
            if (r7 != 0) goto L64
            goto L49
        L64:
            if (r1 != 0) goto L4f
            if (r7 != 0) goto L7c
            goto L4f
        L69:
            if (r1 == 0) goto L6e
            if (r7 == 0) goto L6e
            goto L4f
        L6e:
            if (r1 == 0) goto L72
            if (r7 == 0) goto L7c
        L72:
            if (r1 != 0) goto L58
            if (r7 == 0) goto L58
            goto L49
        L77:
            if (r1 == 0) goto L7e
            if (r7 != 0) goto L7c
            goto L7e
        L7c:
            r2 = r4
            goto L49
        L7e:
            if (r1 == 0) goto L83
            if (r7 != 0) goto L83
            goto L58
        L83:
            if (r1 != 0) goto L49
            if (r7 == 0) goto L49
            goto L4f
        L88:
            r8.setRotation(r2)
            r8.setScaleX(r3)
            r8.setScaleY(r5)
            android.content.res.ColorStateList r7 = android.content.res.ColorStateList.valueOf(r10)
            r8.setImageTintList(r7)
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
