package com.android.wm.shell.common.magnetictarget;

import android.content.Context;
import android.graphics.PointF;
import android.os.VibrationAttributes;
import android.view.VelocityTracker;
import android.view.View;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.animation.PhysicsAnimator;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class MagnetizedObject {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PhysicsAnimator animator;
    public final ArrayList associatedTargets;
    public float flingToTargetMinVelocity;
    public float flingToTargetWidthPercent;
    public final PhysicsAnimator.SpringConfig flungIntoTargetSpringConfig;
    public final int[] objectLocationOnScreen;
    public final PhysicsAnimator.SpringConfig springConfig;
    public float stickToTargetMaxXVelocity;
    public final Object underlyingObject;
    public final FloatPropertyCompat xProperty;
    public final FloatPropertyCompat yProperty;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MagneticTarget {
        public final View targetView;
        public final PointF centerOnScreen = new PointF();
        public final int[] tempLoc = new int[2];

        public MagneticTarget(View view, int i) {
            this.targetView = view;
        }
    }

    static {
        new Companion(null);
    }

    public MagnetizedObject(Context context, Object obj, FloatPropertyCompat floatPropertyCompat, FloatPropertyCompat floatPropertyCompat2) {
        this.underlyingObject = obj;
        this.xProperty = floatPropertyCompat;
        this.yProperty = floatPropertyCompat2;
        PhysicsAnimator.Companion.getClass();
        this.animator = PhysicsAnimator.Companion.getInstance(obj);
        this.objectLocationOnScreen = new int[2];
        this.associatedTargets = new ArrayList();
        VelocityTracker.obtain();
        VibrationAttributes.createForUsage(18);
        new PointF();
        new MagnetizedObject$animateStuckToTarget$1(this);
        this.flingToTargetWidthPercent = 3.0f;
        this.flingToTargetMinVelocity = 4000.0f;
        this.stickToTargetMaxXVelocity = 2000.0f;
        PhysicsAnimator.SpringConfig springConfig = new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
        this.springConfig = springConfig;
        this.flungIntoTargetSpringConfig = springConfig;
    }

    public abstract float getHeight(Object obj);

    public abstract void getLocationOnScreen(Object obj, int[] iArr);

    public abstract float getWidth(Object obj);
}
