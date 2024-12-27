package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class KeyguardInputView extends LinearLayout {
    public View mBouncerMessageView;
    public Runnable mOnFinishImeAnimationRunnable;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardInputView$1, reason: invalid class name */
    public final class AnonymousClass1 extends AnimatorListenerAdapter {
        public boolean mIsCancel;
        public final /* synthetic */ int val$cuj;

        public AnonymousClass1(int i) {
            this.val$cuj = i;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mIsCancel = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.mIsCancel) {
                InteractionJankMonitor.getInstance().cancel(this.val$cuj);
            } else {
                InteractionJankMonitor.getInstance().end(this.val$cuj);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            InteractionJankMonitor.getInstance().begin(KeyguardInputView.this, this.val$cuj);
        }
    }

    public KeyguardInputView(Context context) {
        super(context);
    }

    public abstract CharSequence getTitle();

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBouncerMessageView = findViewById(R.id.bouncer_message_view);
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        return false;
    }

    public KeyguardInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public KeyguardInputView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void startAppearAnimation() {
    }
}
