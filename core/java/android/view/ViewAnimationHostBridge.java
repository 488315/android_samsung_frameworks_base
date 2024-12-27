package android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.RenderNode;

/* loaded from: classes4.dex */
public class ViewAnimationHostBridge extends AnimatorListenerAdapter implements RenderNode.AnimationHost {
    private final View mView;

    public ViewAnimationHostBridge(View view) {
        this.mView = view;
    }

    @Override // android.graphics.RenderNode.AnimationHost
    public void registerAnimatingRenderNode(RenderNode renderNode, Animator animator) {
        this.mView.mAttachInfo.mViewRootImpl.registerAnimatingRenderNode(renderNode);
        animator.addListener(this);
    }

    @Override // android.graphics.RenderNode.AnimationHost
    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator animator) {
        this.mView.mAttachInfo.mViewRootImpl.registerVectorDrawableAnimator(animator);
        animator.setThreadedRendererAnimatorListener(this);
    }

    @Override // android.graphics.RenderNode.AnimationHost
    public boolean isAttached() {
        return this.mView.mAttachInfo != null;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animation) {
        ViewRootImpl viewRoot = this.mView.getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.addThreadedRendererView(this.mView);
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animation) {
        ViewRootImpl viewRoot = this.mView.getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.removeThreadedRendererView(this.mView);
        }
    }
}
