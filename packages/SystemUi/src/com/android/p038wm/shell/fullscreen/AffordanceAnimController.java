package com.android.p038wm.shell.fullscreen;

import android.R;
import android.animation.Keyframe;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.util.FloatProperty;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import com.android.internal.policy.ScreenDecorationsUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AffordanceAnimController {
    public final Animation mAnimation;
    public ValueAnimator mAnimator;
    public final Context mDisplayContext;
    public final float mRadius;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final Transformation mTmpTransformation = new Transformation();
    public final float[] mTmpFloat9 = new float[9];
    public final Rect mBounds = new Rect();
    public final float[][] mAnimSpecArray = {new float[]{0.0f, -4.5f, 3.375f, -2.25f, 1.125f, 0.0f}, new float[]{0.0f, -3.15f, 1.1025f, -0.735f, 0.3675f, 0.0f}, new float[]{0.0f, 4.5f, -3.375f, 2.25f, -1.125f, 0.0f}, new float[]{0.0f, 3.15f, -1.1025f, 0.735f, -0.3675f, 0.0f}};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimTarget implements ValueAnimator.AnimatorUpdateListener {

        /* renamed from: X */
        public static final C39981 f450X = new FloatProperty("x") { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController.AnimTarget.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((AnimTarget) obj).f452x);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                ((AnimTarget) obj).f452x = f;
            }
        };

        /* renamed from: Y */
        public static final C39992 f451Y = new FloatProperty("y") { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController.AnimTarget.2
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((AnimTarget) obj).f453y);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                ((AnimTarget) obj).f453y = f;
            }
        };
        public final SurfaceControl mLeash;
        public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

        /* renamed from: x */
        public float f452x;

        /* renamed from: y */
        public float f453y;

        public AnimTarget(SurfaceControl surfaceControl) {
            this.mLeash = surfaceControl;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            Slog.d("AffordanceAnimController", "(" + this.f452x + ", " + this.f453y + ") playTime=" + valueAnimator.getCurrentPlayTime());
            this.mTransaction.setPosition(this.mLeash, this.f452x, this.f453y);
            this.mTransaction.apply();
        }
    }

    public AffordanceAnimController(Context context, int i) {
        Context createDisplayContext = context.createDisplayContext(((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i));
        this.mDisplayContext = createDisplayContext;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.voice_activity_open_enter);
        this.mRadius = ScreenDecorationsUtils.getWindowCornerRadius(createDisplayContext);
    }

    public final Keyframe[] getKeyFrames(float f, boolean z, boolean z2) {
        float[] fArr = this.mAnimSpecArray[(z ? 2 : 0) + (z2 ? 1 : 0)];
        Keyframe[] keyframeArr = new Keyframe[fArr.length];
        int length = fArr.length;
        if (length == 0) {
            return null;
        }
        if (length == 1) {
            keyframeArr[0] = Keyframe.ofFloat(0.0f, fArr[0] * f);
        } else {
            for (int i = 0; i < length; i++) {
                keyframeArr[i] = Keyframe.ofFloat(i / (length - 1), fArr[i] * f);
            }
        }
        return keyframeArr;
    }
}
