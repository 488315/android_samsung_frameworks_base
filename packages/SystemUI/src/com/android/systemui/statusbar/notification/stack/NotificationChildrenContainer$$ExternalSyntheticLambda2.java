package com.android.systemui.statusbar.notification.stack;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.NotificationContentView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationChildrenContainer$$ExternalSyntheticLambda2 implements DynamicAnimation.OnAnimationEndListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationContentView f$0;

    public /* synthetic */ NotificationChildrenContainer$$ExternalSyntheticLambda2(NotificationContentView notificationContentView, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationContentView;
    }

    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        NotificationContentView notificationContentView = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationChildrenContainer.FROM_PARENT;
                SpringAnimation springAnimation = new SpringAnimation(notificationContentView, DynamicAnimation.TRANSLATION_Y);
                springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(400.0f, 0.5f);
                springAnimation.addEndListener(new NotificationChildrenContainer$$ExternalSyntheticLambda2(notificationContentView, 1));
                springAnimation.animateToFinalPosition(0.0f);
                break;
            default:
                SourceType$Companion$from$1 sourceType$Companion$from$12 = NotificationChildrenContainer.FROM_PARENT;
                notificationContentView.setTranslationY(0.0f);
                break;
        }
    }
}
