package com.android.wm.shell.shared.magnetictarget;

import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class MagnetizedObject$animateStuckToTarget$1 extends FunctionReferenceImpl implements Function5 {
    public MagnetizedObject$animateStuckToTarget$1(Object obj) {
        super(5, obj, MagnetizedObject.class, "animateStuckToTargetInternal", "animateStuckToTargetInternal(Lcom/android/wm/shell/shared/magnetictarget/MagnetizedObject$MagneticTarget;FFZLkotlin/jvm/functions/Function0;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        MagnetizedObject.MagneticTarget magneticTarget = (MagnetizedObject.MagneticTarget) obj;
        float fFloatValue = ((Number) obj2).floatValue();
        float fFloatValue2 = ((Number) obj3).floatValue();
        boolean zBooleanValue = ((Boolean) obj4).booleanValue();
        Function0 function0 = (Function0) obj5;
        MagnetizedObject magnetizedObject = (MagnetizedObject) this.receiver;
        MagnetizedObject.Companion companion = MagnetizedObject.Companion;
        magnetizedObject.getClass();
        magneticTarget.updateLocationOnScreen();
        Object obj6 = magnetizedObject.underlyingObject;
        magnetizedObject.getLocationOnScreen(obj6, magnetizedObject.objectLocationOnScreen);
        float width = (magneticTarget.centerOnScreen.x - (magnetizedObject.getWidth(obj6) / 2.0f)) - r1[0];
        float height = (magneticTarget.centerOnScreen.y - (magnetizedObject.getHeight(obj6) / 2.0f)) - r1[1];
        PhysicsAnimator.SpringConfig springConfig = zBooleanValue ? magnetizedObject.flungIntoTargetSpringConfig : magnetizedObject.springConfig;
        magnetizedObject.cancelAnimations$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
        FloatPropertyCompat floatPropertyCompat = magnetizedObject.xProperty;
        float value = floatPropertyCompat.getValue(obj6) + width;
        PhysicsAnimator physicsAnimator = magnetizedObject.animator;
        physicsAnimator.spring(floatPropertyCompat, value, fFloatValue, springConfig);
        FloatPropertyCompat floatPropertyCompat2 = magnetizedObject.yProperty;
        physicsAnimator.spring(floatPropertyCompat2, floatPropertyCompat2.getValue(obj6) + height, fFloatValue2, springConfig);
        if (function0 != null) {
            physicsAnimator.withEndActions(function0);
        }
        physicsAnimator.start();
        return Unit.INSTANCE;
    }
}
