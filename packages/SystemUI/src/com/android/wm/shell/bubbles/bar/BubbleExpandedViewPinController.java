package com.android.wm.shell.bubbles.bar;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.shared.bubbles.BaseBubblePinController;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;

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
    public BubbleExpandedViewPinController(Context context, FrameLayout frameLayout, final BubblePositioner bubblePositioner) {
        final int i = 2;
        super(new Function0() { // from class: com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = bubblePositioner;
                switch (i) {
                    case 0:
                        return Float.valueOf(((BubbleExpandedViewPinController) obj).context.getResources().getDimension(R.dimen.bubble_bar_dismiss_zone_width));
                    case 1:
                        return Float.valueOf(((BubbleExpandedViewPinController) obj).context.getResources().getDimension(R.dimen.bubble_bar_dismiss_zone_height));
                    default:
                        int i2 = BubbleExpandedViewPinController.$r8$clinit;
                        Rect rect = ((BubblePositioner) obj).mPositionRect;
                        return new Point(rect.width(), rect.height());
                }
            }
        });
        this.context = context;
        this.container = frameLayout;
        this.positioner = bubblePositioner;
        this.tempRect$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new BubbleExpandedViewPinController$$ExternalSyntheticLambda1());
        final int i2 = 0;
        this.exclRectWidth$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i2) {
                    case 0:
                        return Float.valueOf(((BubbleExpandedViewPinController) obj).context.getResources().getDimension(R.dimen.bubble_bar_dismiss_zone_width));
                    case 1:
                        return Float.valueOf(((BubbleExpandedViewPinController) obj).context.getResources().getDimension(R.dimen.bubble_bar_dismiss_zone_height));
                    default:
                        int i22 = BubbleExpandedViewPinController.$r8$clinit;
                        Rect rect = ((BubblePositioner) obj).mPositionRect;
                        return new Point(rect.width(), rect.height());
                }
            }
        });
        final int i3 = 1;
        this.exclRectHeight$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.wm.shell.bubbles.bar.BubbleExpandedViewPinController$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i3) {
                    case 0:
                        return Float.valueOf(((BubbleExpandedViewPinController) obj).context.getResources().getDimension(R.dimen.bubble_bar_dismiss_zone_width));
                    case 1:
                        return Float.valueOf(((BubbleExpandedViewPinController) obj).context.getResources().getDimension(R.dimen.bubble_bar_dismiss_zone_height));
                    default:
                        int i22 = BubbleExpandedViewPinController.$r8$clinit;
                        Rect rect = ((BubblePositioner) obj).mPositionRect;
                        return new Point(rect.width(), rect.height());
                }
            }
        });
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
        boolean zIsOnLeft = bubbleBarLocation.isOnLeft(view.isLayoutRtl());
        Lazy lazy = this.tempRect$delegate;
        this.positioner.getBubbleBarExpandedViewBounds((Rect) lazy.getValue(), zIsOnLeft, false);
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
