package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewGroup;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class MessageContainerController$onScreenshotTaken$2 extends FunctionReferenceImpl implements Function0 {
    public MessageContainerController$onScreenshotTaken$2(Object obj) {
        super(0, obj, MessageContainerController.class, "animateOutMessageContainer", "animateOutMessageContainer()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final MessageContainerController messageContainerController = (MessageContainerController) this.receiver;
        if (messageContainerController.animateOut == null) {
            Animator animator = messageContainerController.getAnimator(false);
            animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.MessageContainerController$animateOutMessageContainer$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    super.onAnimationEnd(animator2);
                    ViewGroup viewGroup = MessageContainerController.this.container;
                    if (viewGroup == null) {
                        viewGroup = null;
                    }
                    viewGroup.setVisibility(8);
                    MessageContainerController.this.animateOut = null;
                }
            });
            animator.start();
            messageContainerController.animateOut = animator;
        }
        return Unit.INSTANCE;
    }
}
