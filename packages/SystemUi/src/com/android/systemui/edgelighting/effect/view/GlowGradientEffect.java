package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.utils.Utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GlowGradientEffect extends FrameLayout {
    public ImageView mGlowView;
    public GradientEffectView mGradientView;
    public float mLightingAlpha;

    /* JADX WARN: Removed duplicated region for block: B:12:0x0098 A[LOOP:0: B:6:0x005e->B:12:0x0098, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x009c A[EDGE_INSN: B:13:0x009c->B:14:0x009c BREAK  A[LOOP:0: B:6:0x005e->B:12:0x0098], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public GlowGradientEffect(Context context) {
        super(context);
        Display display;
        boolean z;
        this.mLightingAlpha = 1.0f;
        this.mGradientView = new GradientEffectView(getContext());
        ImageView imageView = new ImageView(getContext());
        this.mGlowView = imageView;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mGlowView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        if (Utils.isLargeCoverFlipFolded()) {
            Display[] displays = ((DisplayManager) getContext().getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            if (displays != null) {
                String str = "getSubDisplay() : length " + displays.length;
                String str2 = Utils.TAG;
                Slog.d(str2, str);
                int length = displays.length;
                for (int i = 0; i < length; i++) {
                    display = displays[i];
                    if (display == null) {
                        Slog.i(str2, "Do not show SubScreen UI on null display");
                    } else {
                        z = true;
                        if (display.getDisplayId() == 1) {
                            Slog.i(str2, "Show SubScreen UI on this display " + display);
                            if (!z) {
                                break;
                            }
                        } else {
                            Slog.i(str2, "Do not show SubScreen UI on this display " + display);
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
            }
            display = null;
            if (display.getRotation() == 0) {
                this.mGlowView.setImageResource(R.drawable.edge_gradation_only_glow_cover);
                addView(this.mGlowView);
                addView(this.mGradientView);
                setBackgroundColor(0);
                Log.i("GLOW", "init");
            }
        }
        this.mGlowView.setImageResource(R.drawable.edge_gradation_only_glow);
        addView(this.mGlowView);
        addView(this.mGradientView);
        setBackgroundColor(0);
        Log.i("GLOW", "init");
    }

    public final void hide() {
        this.mGlowView.setAlpha(this.mLightingAlpha);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mGlowView, "alpha", 0.0f);
        ofFloat.setDuration(900L);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.GlowGradientEffect.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ImageView imageView = GlowGradientEffect.this.mGlowView;
                if (imageView != null) {
                    imageView.setImageDrawable(null);
                }
                GradientEffectView gradientEffectView = GlowGradientEffect.this.mGradientView;
                if (gradientEffectView != null) {
                    AnimatorSet animatorSet = gradientEffectView.mAnimationSet;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                    }
                    gradientEffectView.resetImageDrawable();
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
        GradientEffectView gradientEffectView = this.mGradientView;
        gradientEffectView.mContainer.setAlpha(gradientEffectView.mStrokeAlpha);
        AbsEdgeLightingMaskView.changeRingImageAlpha(gradientEffectView.mContainer, 0.0f, 900L);
    }
}
