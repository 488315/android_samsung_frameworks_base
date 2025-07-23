package com.android.systemui.edgelighting.effect.view;

import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MorphView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MorphView f$0;
    public final /* synthetic */ SpringAnimation f$1;

    public /* synthetic */ MorphView$$ExternalSyntheticLambda0(MorphView morphView, SpringAnimation springAnimation, int i) {
        this.$r8$classId = i;
        this.f$0 = morphView;
        this.f$1 = springAnimation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MorphView morphView = this.f$0;
                SpringAnimation springAnimation = this.f$1;
                int i = MorphView.$r8$clinit;
                morphView.getClass();
                springAnimation.start();
                NotificationEffect.AnonymousClass1 anonymousClass1 = morphView.mPopupListener;
                if (anonymousClass1 != null) {
                    NotificationEffect.this.finishToastPopupAnimation();
                    break;
                }
                break;
            default:
                MorphView morphView2 = this.f$0;
                SpringAnimation springAnimation2 = this.f$1;
                int i2 = MorphView.$r8$clinit;
                morphView2.getClass();
                springAnimation2.start();
                morphView2.setAlpha(0.0f);
                NotificationEffect.AnonymousClass1 anonymousClass12 = morphView2.mPopupListener;
                if (anonymousClass12 != null) {
                    NotificationEffect.this.startToastPopupAnimation();
                    break;
                }
                break;
        }
    }
}
