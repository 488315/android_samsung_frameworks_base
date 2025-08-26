package com.android.wm.shell.shared.magnetictarget;

import android.content.Context;
import android.graphics.PointF;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface MagnetListener {
        void onReleasedInTarget(MagnetizedObject magnetizedObject);

        void onStuckToTarget(MagnetizedObject magnetizedObject);

        void onUnstuckFromTarget(MagneticTarget magneticTarget, MagnetizedObject magnetizedObject, float f, float f2, boolean z);
    }

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
                    MagnetizedObject.MagneticTarget magneticTarget = this.this$0;
                    magneticTarget.targetView.getLocationOnScreen(magneticTarget.tempLoc);
                    PointF pointF = this.this$0.centerOnScreen;
                    float width = ((r0.targetView.getWidth() / 2.0f) + r0.tempLoc[0]) - this.this$0.targetView.getTranslationX();
                    MagnetizedObject.MagneticTarget magneticTarget2 = this.this$0;
                    pointF.set(width, ((magneticTarget2.targetView.getHeight() / 2.0f) + magneticTarget2.tempLoc[1]) - this.this$0.targetView.getTranslationY());
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

    /* JADX WARN: Removed duplicated region for block: B:110:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x00e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0214  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean maybeConsumeMotionEvent(MotionEvent motionEvent) {
        int size;
        int i;
        Object obj;
        MagneticTarget magneticTarget;
        boolean z;
        float xVelocity;
        MagnetListener magnetListener;
        int i2;
        int i3;
        int i4;
        boolean z2;
        Object obj2;
        boolean z3 = false;
        if (this.associatedTargets.size() != 0) {
            if (motionEvent.getAction() == 0) {
                ArrayList arrayList = this.associatedTargets;
                int size2 = arrayList.size();
                int i5 = 0;
                while (i5 < size2) {
                    Object obj3 = arrayList.get(i5);
                    i5++;
                    ((MagneticTarget) obj3).updateLocationOnScreen();
                }
                if (this.associatedTargets.size() > 0) {
                    this.touchSlop = ViewConfiguration.get(((MagneticTarget) this.associatedTargets.get(0)).targetView.getContext()).getScaledTouchSlop();
                }
                this.velocityTracker.clear();
                this.targetObjectIsStuckTo = null;
                this.touchDown.set(motionEvent.getRawX(), motionEvent.getRawY());
                this.movedBeyondSlop = false;
            }
            float rawX = motionEvent.getRawX() - motionEvent.getX();
            float rawY = motionEvent.getRawY() - motionEvent.getY();
            motionEvent.offsetLocation(rawX, rawY);
            this.velocityTracker.addMovement(motionEvent);
            motionEvent.offsetLocation(-rawX, -rawY);
            if (this.movedBeyondSlop) {
                ArrayList arrayList2 = this.associatedTargets;
                size = arrayList2.size();
                i = 0;
                while (true) {
                    if (i < size) {
                        obj = null;
                        break;
                    }
                    obj = arrayList2.get(i);
                    i++;
                    MagneticTarget magneticTarget2 = (MagneticTarget) obj;
                    if (((float) Math.hypot(motionEvent.getRawX() - magneticTarget2.centerOnScreen.x, motionEvent.getRawY() - (magneticTarget2.centerOnScreen.y + magneticTarget2.screenVerticalOffset))) < magneticTarget2.magneticFieldRadiusPx) {
                        break;
                    }
                }
                magneticTarget = (MagneticTarget) obj;
                z = getObjectStuckToTarget() && magneticTarget != null;
                boolean z4 = (getObjectStuckToTarget() || magneticTarget == null || Intrinsics.areEqual(this.targetObjectIsStuckTo, magneticTarget)) ? false : true;
                if (!z || z4) {
                    this.velocityTracker.computeCurrentVelocity(1000);
                    xVelocity = this.velocityTracker.getXVelocity();
                    float yVelocity = this.velocityTracker.getYVelocity();
                    if (z || Math.abs(xVelocity) <= this.stickToTargetMaxXVelocity) {
                        this.targetObjectIsStuckTo = magneticTarget;
                        cancelAnimations$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
                        magnetListener = this.magnetListener;
                        if (magnetListener == null) {
                            magnetListener = null;
                        }
                        magneticTarget.getClass();
                        magnetListener.onStuckToTarget(this);
                        i2 = 5;
                        i3 = 2;
                        i4 = 1000;
                        this.animateStuckToTarget.invoke(magneticTarget, Float.valueOf(xVelocity), Float.valueOf(yVelocity), Boolean.FALSE, null);
                        vibrateIfEnabled(5);
                    }
                } else {
                    if (magneticTarget == null && getObjectStuckToTarget()) {
                        this.velocityTracker.computeCurrentVelocity(1000);
                        cancelAnimations$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
                        MagnetListener magnetListener2 = this.magnetListener;
                        if (magnetListener2 == null) {
                            magnetListener2 = null;
                        }
                        MagneticTarget magneticTarget3 = this.targetObjectIsStuckTo;
                        magneticTarget3.getClass();
                        magnetListener2.onUnstuckFromTarget(magneticTarget3, this, this.velocityTracker.getXVelocity(), this.velocityTracker.getYVelocity(), false);
                        this.targetObjectIsStuckTo = null;
                        vibrateIfEnabled(2);
                    }
                    i2 = 5;
                    i3 = 2;
                    i4 = 1000;
                }
                if (motionEvent.getAction() == 1) {
                    return getObjectStuckToTarget();
                }
                this.velocityTracker.computeCurrentVelocity(i4);
                float xVelocity2 = this.velocityTracker.getXVelocity();
                float yVelocity2 = this.velocityTracker.getYVelocity();
                cancelAnimations$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
                if (getObjectStuckToTarget()) {
                    if ((-yVelocity2) > this.flingUnstuckFromTargetMinVelocity) {
                        MagnetListener magnetListener3 = this.magnetListener;
                        if (magnetListener3 == null) {
                            magnetListener3 = null;
                        }
                        MagneticTarget magneticTarget4 = this.targetObjectIsStuckTo;
                        magneticTarget4.getClass();
                        magnetListener3.onUnstuckFromTarget(magneticTarget4, this, xVelocity2, yVelocity2, true);
                    } else {
                        MagnetListener magnetListener4 = this.magnetListener;
                        if (magnetListener4 == null) {
                            magnetListener4 = null;
                        }
                        this.targetObjectIsStuckTo.getClass();
                        magnetListener4.onReleasedInTarget(this);
                        vibrateIfEnabled(i2);
                    }
                    this.targetObjectIsStuckTo = null;
                    return true;
                }
                ArrayList arrayList3 = this.associatedTargets;
                int size3 = arrayList3.size();
                int i6 = 0;
                while (true) {
                    if (i6 >= size3) {
                        z2 = z3;
                        obj2 = null;
                        break;
                    }
                    obj2 = arrayList3.get(i6);
                    i6++;
                    MagneticTarget magneticTarget5 = (MagneticTarget) obj2;
                    float rawX2 = motionEvent.getRawX();
                    float rawY2 = motionEvent.getRawY();
                    if (this.flingToTargetEnabled) {
                        float f = magneticTarget5.centerOnScreen.y;
                        z2 = z3;
                        int i7 = magneticTarget5.screenVerticalOffset;
                        if (rawY2 < i7 + f) {
                            if (yVelocity2 > this.flingToTargetMinVelocity) {
                                if (xVelocity2 != 0.0f) {
                                    float f2 = yVelocity2 / xVelocity2;
                                    rawX2 = ((f + i7) - (rawY2 - (rawX2 * f2))) / f2;
                                }
                                float width = magneticTarget5.targetView.getWidth() * this.flingToTargetWidthPercent;
                                float f3 = magneticTarget5.centerOnScreen.x;
                                float f4 = width / i3;
                                if (rawX2 > f3 - f4 && rawX2 < f3 + f4) {
                                    break;
                                }
                            } else {
                                continue;
                            }
                        } else if (yVelocity2 >= this.flingToTargetMinVelocity) {
                            continue;
                        }
                    } else {
                        z2 = z3;
                    }
                    z3 = z2;
                }
                final MagneticTarget magneticTarget6 = (MagneticTarget) obj2;
                if (magneticTarget6 == null) {
                    return z2;
                }
                MagnetListener magnetListener5 = this.magnetListener;
                (magnetListener5 != null ? magnetListener5 : null).onStuckToTarget(this);
                this.targetObjectIsStuckTo = magneticTarget6;
                this.animateStuckToTarget.invoke(magneticTarget6, Float.valueOf(xVelocity2), Float.valueOf(yVelocity2), Boolean.TRUE, new Function0() { // from class: com.android.wm.shell.shared.magnetictarget.MagnetizedObject$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        MagnetizedObject magnetizedObject = this.f$0;
                        MagnetizedObject.MagnetListener magnetListener6 = magnetizedObject.magnetListener;
                        if (magnetListener6 == null) {
                            magnetListener6 = null;
                        }
                        magnetListener6.onReleasedInTarget(magnetizedObject);
                        magnetizedObject.targetObjectIsStuckTo = null;
                        magnetizedObject.vibrateIfEnabled(5);
                        return Unit.INSTANCE;
                    }
                });
                return true;
            }
            if (((float) Math.hypot(motionEvent.getRawX() - this.touchDown.x, motionEvent.getRawY() - this.touchDown.y)) > this.touchSlop) {
                this.movedBeyondSlop = true;
                ArrayList arrayList22 = this.associatedTargets;
                size = arrayList22.size();
                i = 0;
                while (true) {
                    if (i < size) {
                    }
                }
                magneticTarget = (MagneticTarget) obj;
                if (getObjectStuckToTarget()) {
                    if (getObjectStuckToTarget()) {
                        if (z) {
                        }
                        this.velocityTracker.computeCurrentVelocity(1000);
                        xVelocity = this.velocityTracker.getXVelocity();
                        float yVelocity3 = this.velocityTracker.getYVelocity();
                        if (z) {
                        }
                        this.targetObjectIsStuckTo = magneticTarget;
                        cancelAnimations$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
                        magnetListener = this.magnetListener;
                        if (magnetListener == null) {
                        }
                        magneticTarget.getClass();
                        magnetListener.onStuckToTarget(this);
                        i2 = 5;
                        i3 = 2;
                        i4 = 1000;
                        this.animateStuckToTarget.invoke(magneticTarget, Float.valueOf(xVelocity), Float.valueOf(yVelocity3), Boolean.FALSE, null);
                        vibrateIfEnabled(5);
                        if (motionEvent.getAction() == 1) {
                        }
                    }
                }
            }
        }
        return false;
    }

    public final void vibrateIfEnabled(int i) {
        if (this.hapticsEnabled) {
            this.vibrator.vibrate(VibrationEffect.createPredefined(i), this.vibrationAttributes);
        }
    }
}
