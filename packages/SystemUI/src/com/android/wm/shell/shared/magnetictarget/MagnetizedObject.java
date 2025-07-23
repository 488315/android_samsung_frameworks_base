package com.android.wm.shell.shared.magnetictarget;

import android.content.Context;
import android.graphics.PointF;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.VelocityTracker;
import android.view.View;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import java.util.ArrayList;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class MagnetizedObject {
    public static final Companion Companion = new Companion(null);
    public Function5 animateStuckToTarget;
    public final PhysicsAnimator animator;
    public final ArrayList associatedTargets;
    public boolean flingToTargetEnabled;
    public float flingToTargetMinVelocity;
    public float flingToTargetWidthPercent;
    public final float flingUnstuckFromTargetMinVelocity;
    public final PhysicsAnimator.SpringConfig flungIntoTargetSpringConfig;
    public final boolean hapticsEnabled;
    public MagnetListener magnetListener;
    public boolean movedBeyondSlop;
    public final int[] objectLocationOnScreen;
    public final PhysicsAnimator.SpringConfig springConfig;
    public final float stickToTargetMaxXVelocity;
    public MagneticTarget targetObjectIsStuckTo;
    public final PointF touchDown;
    public int touchSlop;
    public final Object underlyingObject;
    public final VelocityTracker velocityTracker;
    public final VibrationAttributes vibrationAttributes;
    public final Vibrator vibrator;
    public final FloatPropertyCompat xProperty;
    public final FloatPropertyCompat yProperty;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface MagnetListener {
        void onReleasedInTarget(MagnetizedObject magnetizedObject);

        void onStuckToTarget(MagnetizedObject magnetizedObject);

        void onUnstuckFromTarget(MagneticTarget magneticTarget, MagnetizedObject magnetizedObject, float f, float f2, boolean z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MagneticTarget {
        public int magneticFieldRadiusPx;
        public int screenVerticalOffset;
        public final View targetView;
        public final PointF centerOnScreen = new PointF();
        public final int[] tempLoc = new int[2];

        public MagneticTarget(View view, int i) {
            this.targetView = view;
            this.magneticFieldRadiusPx = i;
        }

        public final void updateLocationOnScreen() {
            this.targetView.post(new Runnable() { // from class: com.android.wm.shell.shared.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1
                @Override // java.lang.Runnable
                public final void run() {
                    MagnetizedObject.MagneticTarget magneticTarget = MagnetizedObject.MagneticTarget.this;
                    magneticTarget.targetView.getLocationOnScreen(magneticTarget.tempLoc);
                    PointF pointF = MagnetizedObject.MagneticTarget.this.centerOnScreen;
                    float width = ((r0.targetView.getWidth() / 2.0f) + r0.tempLoc[0]) - MagnetizedObject.MagneticTarget.this.targetView.getTranslationX();
                    MagnetizedObject.MagneticTarget magneticTarget2 = MagnetizedObject.MagneticTarget.this;
                    pointF.set(width, ((magneticTarget2.targetView.getHeight() / 2.0f) + magneticTarget2.tempLoc[1]) - MagnetizedObject.MagneticTarget.this.targetView.getTranslationY());
                }
            });
        }
    }

    public MagnetizedObject(Context context, Object obj, FloatPropertyCompat floatPropertyCompat, FloatPropertyCompat floatPropertyCompat2) {
        this.underlyingObject = obj;
        this.xProperty = floatPropertyCompat;
        this.yProperty = floatPropertyCompat2;
        PhysicsAnimator.Companion.getClass();
        this.animator = PhysicsAnimator.Companion.getInstance(obj);
        this.objectLocationOnScreen = new int[2];
        this.associatedTargets = new ArrayList();
        this.velocityTracker = VelocityTracker.obtain();
        this.vibrator = (Vibrator) context.getSystemService("vibrator");
        this.vibrationAttributes = VibrationAttributes.createForUsage(18);
        this.touchDown = new PointF();
        this.animateStuckToTarget = new MagnetizedObject$animateStuckToTarget$1(this);
        this.flingToTargetEnabled = true;
        this.flingToTargetWidthPercent = 3.0f;
        this.flingToTargetMinVelocity = 4000.0f;
        this.flingUnstuckFromTargetMinVelocity = 4000.0f;
        this.stickToTargetMaxXVelocity = 2000.0f;
        this.hapticsEnabled = true;
        PhysicsAnimator.SpringConfig springConfig = new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
        this.springConfig = springConfig;
        this.flungIntoTargetSpringConfig = springConfig;
    }

    public final void cancelAnimations$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
        this.animator.cancel(this.xProperty, this.yProperty);
    }

    public abstract float getHeight(Object obj);

    public abstract void getLocationOnScreen(Object obj, int[] iArr);

    public final boolean getObjectStuckToTarget() {
        return this.targetObjectIsStuckTo != null;
    }

    public abstract float getWidth(Object obj);

    /* JADX WARN: Removed duplicated region for block: B:76:0x021a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean maybeConsumeMotionEvent(android.view.MotionEvent r25) {
        /*
            Method dump skipped, instructions count: 631
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.magnetictarget.MagnetizedObject.maybeConsumeMotionEvent(android.view.MotionEvent):boolean");
    }

    public final void vibrateIfEnabled(int i) {
        if (this.hapticsEnabled) {
            this.vibrator.vibrate(VibrationEffect.createPredefined(i), this.vibrationAttributes);
        }
    }
}
