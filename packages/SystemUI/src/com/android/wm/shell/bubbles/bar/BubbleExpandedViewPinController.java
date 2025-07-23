package com.android.wm.shell.bubbles.bar;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.shared.bubbles.BaseBubblePinController;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import kotlin.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleExpandedViewPinController extends BaseBubblePinController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FrameLayout container;
    public final Context context;
    public View dropTargetView;
    public final Lazy exclRectHeight$delegate;
    public final Lazy exclRectWidth$delegate;
    public final BubblePositioner positioner;
    public final Lazy tempRect$delegate;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BubbleExpandedViewPinController(android.content.Context r3, android.widget.FrameLayout r4, final com.android.wm.shell.bubbles.BubblePositioner r5) {
        /*
            r2 = this;
            com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2 r0 = new com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2
            r1 = 2
            r0.<init>()
            r2.<init>(r0)
            r2.context = r3
            r2.container = r4
            r2.positioner = r5
            kotlin.LazyThreadSafetyMode r3 = kotlin.LazyThreadSafetyMode.NONE
            com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda1 r4 = new com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda1
            r4.<init>()
            kotlin.Lazy r3 = kotlin.LazyKt__LazyJVMKt.lazy(r3, r4)
            r2.tempRect$delegate = r3
            com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2 r3 = new com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2
            r4 = 0
            r3.<init>()
            kotlin.Lazy r3 = kotlin.LazyKt__LazyJVMKt.lazy(r3)
            r2.exclRectWidth$delegate = r3
            com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2 r3 = new com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2
            r4 = 1
            r3.<init>()
            kotlin.Lazy r3 = kotlin.LazyKt__LazyJVMKt.lazy(r3)
            r2.exclRectHeight$delegate = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController.<init>(android.content.Context, android.widget.FrameLayout, com.android.wm.shell.bubbles.BubblePositioner):void");
    }

    public final View getDropTargetView() {
        return this.dropTargetView;
    }

    @Override // com.android.wm.shell.shared.bubbles.BaseBubblePinController
    public final void removeDropTargetView(View view) {
        this.container.removeView(view);
        this.dropTargetView = null;
    }

    @Override // com.android.wm.shell.shared.bubbles.BaseBubblePinController
    public final void updateLocation(BubbleBarLocation bubbleBarLocation) {
        View view = this.dropTargetView;
        if (view == null) {
            return;
        }
        boolean isOnLeft = bubbleBarLocation.isOnLeft(view.isLayoutRtl());
        Lazy lazy = this.tempRect$delegate;
        this.positioner.getBubbleBarExpandedViewBounds((Rect) lazy.getValue(), isOnLeft, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.width = ((Rect) lazy.getValue()).width();
        layoutParams2.height = ((Rect) lazy.getValue()).height();
        view.setLayoutParams(layoutParams2);
        view.setX(((Rect) lazy.getValue()).left);
        view.setY(((Rect) lazy.getValue()).top);
    }
}
