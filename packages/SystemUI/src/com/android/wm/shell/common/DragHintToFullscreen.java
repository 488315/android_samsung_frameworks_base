package com.android.wm.shell.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragHintToFullscreen extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Animation mHideAnimation;
    public Animation mShowAnimation;
    public boolean mVisible;

    public DragHintToFullscreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_view_show);
        this.mShowAnimation = loadAnimation;
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.common.DragHintToFullscreen.1
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                int i = DragHintToFullscreen.$r8$clinit;
                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onAnimationEnd, mVisible="), DragHintToFullscreen.this.mVisible, "DragHintToFullscreen");
                DragHintToFullscreen dragHintToFullscreen = DragHintToFullscreen.this;
                if (dragHintToFullscreen.mVisible) {
                    dragHintToFullscreen.setVisibility(0);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        this.mHideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_view_hide);
    }
}
