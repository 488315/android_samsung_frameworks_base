package com.android.settingslib.animation;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.keyguard.KeyguardInputView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppearAnimationUtils {
    public boolean mAppearing;
    public final float mDelayScale;
    public final long mDuration;
    public final Interpolator mInterpolator;
    public final AppearAnimationProperties mProperties;
    public RowTranslationScaler mRowTranslationScaler;
    public final float mStartTranslation;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppearAnimationProperties {
        public long[][] delays;
        public int maxDelayColIndex;
        public int maxDelayRowIndex;

        public AppearAnimationProperties(AppearAnimationUtils appearAnimationUtils) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RowTranslationScaler {
    }

    public AppearAnimationUtils(Context context) {
        this(context, 220L, 1.0f, 1.0f, AnimationUtils.loadInterpolator(context, R.interpolator.linear_out_slow_in));
    }

    public static void startTranslationYAnimation(final View view, long j, long j2, final float f, Interpolator interpolator, KeyguardInputView.C06871 c06871) {
        RenderNodeAnimator ofFloat;
        if (view.isHardwareAccelerated()) {
            ofFloat = new RenderNodeAnimator(1, f);
            ofFloat.setTarget(view);
        } else {
            ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, view.getTranslationY(), f);
        }
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(j2);
        ofFloat.setStartDelay(j);
        if (c06871 != null) {
            ofFloat.addListener(c06871);
        }
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.settingslib.animation.AppearAnimationUtils.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                view.setTranslationY(f);
            }
        });
        ofFloat.start();
    }

    public long calculateDelay(int i, int i2) {
        return (long) ((((Math.pow(i, 0.4d) + 0.4d) * i2 * 20.0d) + (i * 40)) * this.mDelayScale);
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.use(jadx.core.dex.instructions.args.RegisterArg)" because "ssaVar" is null
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:493)
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:496)
        */
    public final void startAnimation2d(java.lang.Object[][] r20, java.lang.Runnable r21, com.android.keyguard.KeyguardPatternView r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            com.android.settingslib.animation.AppearAnimationUtils$AppearAnimationProperties r2 = r0.mProperties
            r3 = -1
            r2.maxDelayColIndex = r3
            r2.maxDelayRowIndex = r3
            int r4 = r1.length
            long[][] r4 = new long[r4][]
            r2.delays = r4
            r4 = 0
            r5 = -1
            r7 = r4
        L14:
            int r8 = r1.length
            if (r7 >= r8) goto L43
            r8 = r1[r7]
            long[][] r9 = r2.delays
            int r10 = r8.length
            long[] r10 = new long[r10]
            r9[r7] = r10
            r9 = r4
        L21:
            int r10 = r8.length
            if (r9 >= r10) goto L40
            long r10 = r0.calculateDelay(r7, r9)
            long[][] r12 = r2.delays
            r12 = r12[r7]
            r12[r9] = r10
            r12 = r1[r7]
            r12 = r12[r9]
            if (r12 == 0) goto L3d
            int r12 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r12 <= 0) goto L3d
            r2.maxDelayColIndex = r9
            r2.maxDelayRowIndex = r7
            r5 = r10
        L3d:
            int r9 = r9 + 1
            goto L21
        L40:
            int r7 = r7 + 1
            goto L14
        L43:
            int r5 = r2.maxDelayRowIndex
            if (r5 == r3) goto L9e
            int r5 = r2.maxDelayColIndex
            if (r5 != r3) goto L4c
            goto L9e
        L4c:
            r3 = r4
        L4d:
            long[][] r5 = r2.delays
            int r6 = r5.length
            if (r3 >= r6) goto La1
            r6 = r5[r3]
            com.android.settingslib.animation.AppearAnimationUtils$RowTranslationScaler r7 = r0.mRowTranslationScaler
            if (r7 == 0) goto L66
            int r5 = r5.length
            int r7 = r5 - r3
            double r7 = (double) r7
            r9 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r7 = java.lang.Math.pow(r7, r9)
            double r9 = (double) r5
            double r7 = r7 / r9
            float r5 = (float) r7
            goto L68
        L66:
            r5 = 1065353216(0x3f800000, float:1.0)
        L68:
            float r7 = r0.mStartTranslation
            float r5 = r5 * r7
            r7 = r4
        L6c:
            int r8 = r6.length
            if (r7 >= r8) goto L9b
            r11 = r6[r7]
            int r8 = r2.maxDelayRowIndex
            if (r8 != r3) goto L7c
            int r8 = r2.maxDelayColIndex
            if (r8 != r7) goto L7c
            r18 = r21
            goto L7f
        L7c:
            r8 = 0
            r18 = r8
        L7f:
            r8 = r1[r3]
            r10 = r8[r7]
            long r13 = r0.mDuration
            boolean r8 = r0.mAppearing
            if (r8 == 0) goto L8b
            r15 = r5
            goto L8d
        L8b:
            float r9 = -r5
            r15 = r9
        L8d:
            android.view.animation.Interpolator r9 = r0.mInterpolator
            r17 = r9
            r9 = r22
            r16 = r8
            r9.createAnimation(r10, r11, r13, r15, r16, r17, r18)
            int r7 = r7 + 1
            goto L6c
        L9b:
            int r3 = r3 + 1
            goto L4d
        L9e:
            r21.run()
        La1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.animation.AppearAnimationUtils.startAnimation2d(java.lang.Object[][], java.lang.Runnable, com.android.keyguard.KeyguardPatternView):void");
    }

    public final void createAnimation(View view, long j, long j2, float f, boolean z, Interpolator interpolator, Runnable runnable) {
        createAnimation(view, j, j2, f, z, interpolator, runnable, null);
    }

    public AppearAnimationUtils(Context context, long j, float f, float f2, Interpolator interpolator) {
        this.mProperties = new AppearAnimationProperties(this);
        this.mInterpolator = interpolator;
        this.mStartTranslation = context.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.appear_y_translation_start) * f;
        this.mDelayScale = f2;
        this.mDuration = j;
        this.mAppearing = true;
    }

    public final void createAnimation(final View view, long j, long j2, float f, boolean z, Interpolator interpolator, final Runnable runnable, KeyguardInputView.C06871 c06871) {
        RenderNodeAnimator ofFloat;
        if (view != null) {
            final float f2 = z ? 1.0f : 0.0f;
            float f3 = z ? 0.0f : f;
            view.setAlpha(1.0f - f2);
            view.setTranslationY(f - f3);
            if (view.isHardwareAccelerated()) {
                ofFloat = new RenderNodeAnimator(11, f2);
                ofFloat.setTarget(view);
            } else {
                ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), f2);
            }
            ofFloat.setInterpolator(interpolator);
            ofFloat.setDuration(j2);
            ofFloat.setStartDelay(j);
            if (view.hasOverlappingRendering()) {
                view.setLayerType(2, null);
                ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.settingslib.animation.AppearAnimationUtils.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        view.setLayerType(0, null);
                    }
                });
            }
            ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.settingslib.animation.AppearAnimationUtils.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setAlpha(f2);
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            ofFloat.start();
            startTranslationYAnimation(view, j, j2, f3, interpolator, c06871);
        }
    }
}
