package com.android.p038wm.shell.common.magnetictarget;

import android.graphics.PointF;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.p038wm.shell.animation.PhysicsAnimator;
import com.android.p038wm.shell.common.magnetictarget.MagnetizedObject;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public /* synthetic */ class MagnetizedObject$animateStuckToTarget$1 extends FunctionReferenceImpl implements Function5 {
    public MagnetizedObject$animateStuckToTarget$1(Object obj) {
        super(5, obj, MagnetizedObject.class, "animateStuckToTargetInternal", "animateStuckToTargetInternal(Lcom/android/wm/shell/common/magnetictarget/MagnetizedObject$MagneticTarget;FFZLkotlin/jvm/functions/Function0;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        MagnetizedObject.MagneticTarget magneticTarget = (MagnetizedObject.MagneticTarget) obj;
        float floatValue = ((Number) obj2).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        Function0 function0 = (Function0) obj5;
        MagnetizedObject magnetizedObject = (MagnetizedObject) this.receiver;
        int i = MagnetizedObject.$r8$clinit;
        magnetizedObject.getClass();
        magneticTarget.getClass();
        magneticTarget.targetView.post(new MagnetizedObject$MagneticTarget$updateLocationOnScreen$1(magneticTarget));
        Object obj6 = magnetizedObject.underlyingObject;
        magnetizedObject.getLocationOnScreen(obj6, magnetizedObject.objectLocationOnScreen);
        PointF pointF = magneticTarget.centerOnScreen;
        float width = (pointF.x - (magnetizedObject.getWidth(obj6) / 2.0f)) - r1[0];
        float height = (pointF.y - (magnetizedObject.getHeight(obj6) / 2.0f)) - r1[1];
        PhysicsAnimator.SpringConfig springConfig = booleanValue ? magnetizedObject.flungIntoTargetSpringConfig : magnetizedObject.springConfig;
        FloatPropertyCompat floatPropertyCompat = magnetizedObject.xProperty;
        FloatPropertyCompat floatPropertyCompat2 = magnetizedObject.yProperty;
        PhysicsAnimator physicsAnimator = magnetizedObject.animator;
        physicsAnimator.cancel(floatPropertyCompat, floatPropertyCompat2);
        physicsAnimator.spring(floatPropertyCompat, floatPropertyCompat.getValue(obj6) + width, floatValue, springConfig);
        physicsAnimator.spring(floatPropertyCompat2, floatPropertyCompat2.getValue(obj6) + height, floatValue2, springConfig);
        if (function0 != null) {
            physicsAnimator.endActions.addAll(ArraysKt___ArraysKt.filterNotNull(new Function0[]{function0}));
        }
        physicsAnimator.start();
        return Unit.INSTANCE;
    }
}
