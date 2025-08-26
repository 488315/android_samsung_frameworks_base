package com.android.wm.shell.bubbles;

import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController.AnonymousClass2 f$0;
    public final /* synthetic */ Rect f$1;

    public /* synthetic */ BubbleController$2$$ExternalSyntheticLambda0(BubbleController.AnonymousClass2 anonymousClass2, Rect rect, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass2;
        this.f$1 = rect;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleController.AnonymousClass2 anonymousClass2 = this.f$0;
                Rect rect = this.f$1;
                BubbleStackView bubbleStackView = BubbleController.this.mStackView;
                if (bubbleStackView != null) {
                    int i = rect.top;
                    ViewGroup.LayoutParams layoutParams = bubbleStackView.mDismissView.getLayoutParams();
                    if (layoutParams instanceof FrameLayout.LayoutParams) {
                        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                        layoutParams2.bottomMargin = i;
                        bubbleStackView.mDismissView.setLayoutParams(layoutParams2);
                    }
                    MagnetizedObject.MagneticTarget magneticTarget = bubbleStackView.mMagneticTarget;
                    magneticTarget.screenVerticalOffset = i;
                    magneticTarget.updateLocationOnScreen();
                    break;
                }
                break;
            default:
                BubbleController.AnonymousClass2 anonymousClass22 = this.f$0;
                Rect rect2 = this.f$1;
                BubbleStackView bubbleStackView2 = BubbleController.this.mStackView;
                if (bubbleStackView2 != null) {
                    int i2 = rect2.top;
                    ViewGroup.LayoutParams layoutParams3 = bubbleStackView2.mDismissView.getLayoutParams();
                    if (layoutParams3 instanceof FrameLayout.LayoutParams) {
                        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                        layoutParams4.bottomMargin = i2;
                        bubbleStackView2.mDismissView.setLayoutParams(layoutParams4);
                    }
                    MagnetizedObject.MagneticTarget magneticTarget2 = bubbleStackView2.mMagneticTarget;
                    magneticTarget2.screenVerticalOffset = i2;
                    magneticTarget2.updateLocationOnScreen();
                    break;
                }
                break;
        }
    }
}
